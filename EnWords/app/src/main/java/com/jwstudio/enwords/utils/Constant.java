package com.jwstudio.enwords.utils;

public class Constant {

    public static final String TAG = "TAG";

    // 注册成功
    public static final int REGISTER_SUCCESS = 1;

    // 注册失败
    public static final int REGISTER_FAIL = 0;

    // 账号格式错误
    public static final int REGISTER_ERROR_FORMAT = 2;

    // 账号是否存在
    public static final int REGISTER_IS_EXIST = 3;

    // 账号或密码为空
    public static final int TYPE_ACCOUNT_PASSWORD_ERROR_NULL = 0;

    // 账号格式不对
    public static final int TYPE_ACCOUNT_NOT_EXIST = 1;

    // 密码格式不对
    public static final int TYPE_PASSWORD_ERROR = 2;

    // 后台项目，保存资源的路径
    public static final String RESOURCE_URL = "http://10.0.3.2:8080/EnWords/";

    // 资源保存路径，因为8080端口被tomcat占用了，所以资源放在apache
    public static final String RESOURCE_URL_WORDS = "http://10.0.3.2:80/resource/";

    // 听力材料
    public static final String RESOURCE_URL_ARTICLE = "http://10.0.3.2:80/article/";

}
