package com.jwstudio.enwords.login.presenter;

import android.content.Context;
import android.content.Intent;

import com.jwstudio.enwords.home.MainActivity;
import com.jwstudio.enwords.login.model.IUserLogin;
import com.jwstudio.enwords.login.model.IUserLoginListener;
import com.jwstudio.enwords.login.model.UserLoginModel;
import com.jwstudio.enwords.login.view.ILoginView;
import com.jwstudio.enwords.utils.Constant;
import com.jwstudio.enwords.utils.RecordInfoState;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginPresenter {

    private ILoginView loginView;
    private IUserLogin loginModel;
    private Context context;
    private String accout;

    public LoginPresenter(Context context, ILoginView loginView) {
        this.context = context;
        this.loginView = loginView;
        loginModel = new UserLoginModel(new UserLoginListerImpl());
    }

    public void doLogin(String account, String password) {
        this.accout = account;
        loginView.updateView();
        loginModel.login(account, password);
    }

    private class UserLoginListerImpl implements IUserLoginListener {
        @Override
        public void success() {
            RecordInfoState.logined(context, accout);
            loginView.loginSuccess();

            // 初始化数据
            initPunchCard();
            RecordInfoState.recordPlan(context, "4"); // 默认计划

            context.startActivity(new Intent(context, MainActivity.class));
            loginView.destroyActivity();
        }

        // 将保存在远程数据库里的打卡日期缓存到本地
        public void initPunchCard() {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder body = new FormBody.Builder();
            body.add("type", "4");
            body.add("name", RecordInfoState.getAccount(context));

            Request request = new Request.Builder()
                    .url(Constant.RESOURCE_URL + "ResPathServlet")
                    .post(body.build())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String pathJson = response.body().string();

                        try {
                            JSONArray jsonArray = new JSONArray(pathJson);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (jsonObject != null) {
                                    RecordInfoState.recordPunchCard(context, jsonObject.getString("date"));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public void failed() {
            loginView.loginFailed();
        }

        @Override
        public void error(int type) {
            switch (type) {
                case Constant.TYPE_ACCOUNT_PASSWORD_ERROR_NULL:
                    loginView.checkout();
                    break;
                case Constant.TYPE_ACCOUNT_NOT_EXIST:
                    loginView.updateAccountView("账号不存在！");
                    loginView.clear(Constant.TYPE_ACCOUNT_NOT_EXIST);
                    break;
                case Constant.TYPE_PASSWORD_ERROR:
                    loginView.updatePasswordView("密码错误！");
                    loginView.clear(Constant.TYPE_PASSWORD_ERROR);
                    break;
                default:
                    break;
            }
        }
    }

}
