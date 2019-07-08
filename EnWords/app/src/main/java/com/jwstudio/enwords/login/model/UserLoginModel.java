package com.jwstudio.enwords.login.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jwstudio.enwords.utils.Constant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserLoginModel implements IUserLogin {

    private IUserLoginListener loginListener;
    private MyHandler handler;

    public UserLoginModel(IUserLoginListener loginListener) {
        this.loginListener = loginListener;
        handler = new MyHandler();
    }

    @Override
    public void login(String account, String password) {
        if (account.length() == 0 || password.length() == 0) {
            loginListener.error(Constant.TYPE_ACCOUNT_PASSWORD_ERROR_NULL);
            return;
        }

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("account", account);
        formBody.add("password", password);

        Request request = new Request.Builder()
                .url(Constant.RESOURCE_URL + "LoginServlet")
                .post(formBody.build())
                .build();

        client.newCall(request).enqueue(new LoginCallBack());
    }

    private class LoginCallBack implements Callback {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            loginListener.failed();
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if (response.isSuccessful()) {
                String result = response.body().string();
                Log.d(Constant.TAG, "result:" + result);

                // 不能再子线程更新UI
                Message message = handler.obtainMessage();
                message.obj = result;
                handler.sendMessage(message);
            }
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String result = (String) msg.obj;
            if ("success".equals(result)) { // 成功
                loginListener.success();
            } else if ("not_exist".equals(result)) { // 账号不存在
                loginListener.error(Constant.TYPE_ACCOUNT_NOT_EXIST);
            } else if ("error_password".equals(result)) { // 密码错误
                loginListener.error(Constant.TYPE_PASSWORD_ERROR);
            }
        }
    }

}
