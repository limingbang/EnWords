package com.jwstudio.enwords.login.model;

public interface IUserLoginListener {

    void success();

    void failed();

    // 请求登录过程中出现的错误，如密码错误
    void error(int type);

}
