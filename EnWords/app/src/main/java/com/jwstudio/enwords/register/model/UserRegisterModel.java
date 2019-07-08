package com.jwstudio.enwords.register.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.jwstudio.enwords.utils.Constant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserRegisterModel implements IUserRegister {

    private Handler handler;

    public UserRegisterModel(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void register(String account, String password) {
        Message message = Message.obtain();
        if (account.length() != 11) {
            message.what = Constant.REGISTER_ERROR_FORMAT;
            message.obj = "请输入正确格式的手机号！";
            handler.sendMessage(message);
            return;
        }

        if (password.length() < 6) {
            message.what = Constant.REGISTER_ERROR_FORMAT;
            message.obj = "密码长度要大于6";
            handler.sendMessage(message);
            return;
        }

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("account", account);
        formBody.add("password", password);

        Request request = new Request.Builder()
                .url(Constant.RESOURCE_URL + "RegisterServlet")
                .post(formBody.build())
                .build();

        client.newCall(request).enqueue(new RegisterCallback());
    }

    private class RegisterCallback implements Callback {

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            Message message = Message.obtain();
            message.what = Constant.REGISTER_FAIL;
            handler.sendMessage(message);

            Log.d(Constant.TAG, "onFailure");
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            if (response.isSuccessful()) {
                Message message = Message.obtain();

                String result = response.body().string();
                Log.d(Constant.TAG, "result:" + result);
                if ("success".equals(result)) { // 成功
                    message.what = Constant.REGISTER_SUCCESS;
                    handler.sendMessage(message);
                } else if ("exist".equals(result)) { // 账号存在
                    message.what = Constant.REGISTER_IS_EXIST;
                    handler.sendMessage(message);
                }else {
                    message.what = Constant.REGISTER_FAIL;
                    handler.sendMessage(message);
                }
            }
        }
    }

}
