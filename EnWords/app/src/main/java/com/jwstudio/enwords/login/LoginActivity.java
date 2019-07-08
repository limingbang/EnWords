package com.jwstudio.enwords.login;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.home.MainActivity;
import com.jwstudio.enwords.login.presenter.LoginPresenter;
import com.jwstudio.enwords.login.view.ILoginView;
import com.jwstudio.enwords.register.RegisterActivity;
import com.jwstudio.enwords.utils.Constant;
import com.jwstudio.enwords.utils.RecordInfoState;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private TextInputLayout tilAccount;
    private TextInputLayout tilPassword;
    private TextInputEditText tieAccount;
    private TextInputEditText tiePassword;
    private MaterialButton btnLogin;
    private MaterialButton btnRegisterActivity;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this, this);

        String a = RecordInfoState.getAccount(this);
        // 如果已经成功登录或注册，则直接进入主界面
        if (!"account".equals(a) && a != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        init();
    }

    private void init() {
        tilAccount = findViewById(R.id.til_account);
        tilPassword = findViewById(R.id.til_password);
        tieAccount = findViewById(R.id.tie_account);
        tiePassword = findViewById(R.id.tie_password);

        btnLogin = findViewById(R.id.btn_login);
        btnRegisterActivity = findViewById(R.id.btn_register_activity);

        btnLogin.setOnClickListener(this);
        btnRegisterActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String account = tieAccount.getText().toString();
                String password = tiePassword.getText().toString();
                presenter.doLogin(account, password);
                break;
            case R.id.btn_register_activity:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAccountView(String info) {
        tilAccount.setErrorEnabled(true);
        tilAccount.setError(info);
    }

    @Override
    public void updatePasswordView(String info) {
        tilPassword.setErrorEnabled(true);
        tilPassword.setError(info);
    }

    @Override
    public void clear(int type) {
        switch (type) {
            case Constant.TYPE_ACCOUNT_NOT_EXIST:
                tieAccount.setText("");
                break;
            case Constant.TYPE_PASSWORD_ERROR:
                tiePassword.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void checkout() {
        Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView() {
        tilAccount.setErrorEnabled(false);
        tilPassword.setErrorEnabled(false);
    }

    @Override
    public void destroyActivity() {
        this.finish();
    }
}
