package com.jwstudio.enwords.register.view;

public interface IRegisterView {

    // 清空输入框数据
    void clear();

    void updateView(String info);

    void registerFail();

    void registerSuccess();

    // 销毁Activity
    void destroyActivity();

}
