package com.jwstudio.enwords.register.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.jwstudio.enwords.home.MainActivity;
import com.jwstudio.enwords.register.model.IUserRegister;
import com.jwstudio.enwords.register.model.UserRegisterModel;
import com.jwstudio.enwords.register.view.IRegisterView;
import com.jwstudio.enwords.utils.Constant;
import com.jwstudio.enwords.utils.RecordInfoState;

public class RegisterPresenter {

    private MyHandler mHandler;
    private Context context;
    private IUserRegister userRegister;
    private IRegisterView registerView;
    private String account;

    public RegisterPresenter(Context context, IRegisterView registerView) {
        this.context = context;
        mHandler = new MyHandler();
        userRegister = new UserRegisterModel(mHandler);
        this.registerView = registerView;
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.REGISTER_SUCCESS:
                    registerView.registerSuccess();

                    // 初始化数据
                    RecordInfoState.logined(context, account);
                    RecordInfoState.recordPlan(context, "4");

                    context.startActivity(new Intent(context, MainActivity.class));
                    registerView.destroyActivity();
                    break;
                case Constant.REGISTER_FAIL:
                    registerView.registerFail();
                    registerView.clear();
                    break;
                case Constant.REGISTER_ERROR_FORMAT:
                    String p = (String) msg.obj;
                    registerView.updateView(p);
                    registerView.clear();
                    break;
                case Constant.REGISTER_IS_EXIST:
                    String info = "该账号已注册";
                    registerView.updateView(info);
                    break;
                default:
                    break;
            }
        }
    }

    public void doRegister(String account, String password) {
        this.account = account;

        userRegister.register(account, password);
    }

}
