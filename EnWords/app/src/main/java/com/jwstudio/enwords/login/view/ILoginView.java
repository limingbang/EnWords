package com.jwstudio.enwords.login.view;

public interface ILoginView {

    void loginSuccess();

    void loginFailed();

    // 账号出现错误
    void updateAccountView(String info);

    // 密码出现错误
    void updatePasswordView(String info);

    // 根据不同的类型清空输入框
    void clear(int type);

    // 信息验证不通过时
    void checkout();

    void updateView();

    // 销毁LoginActivity
    void destroyActivity();

}
