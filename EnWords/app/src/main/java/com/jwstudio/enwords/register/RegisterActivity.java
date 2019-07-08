package com.jwstudio.enwords.register;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.register.presenter.RegisterPresenter;
import com.jwstudio.enwords.register.view.IRegisterView;
import com.jwstudio.enwords.utils.Constant;

public class RegisterActivity extends AppCompatActivity implements IRegisterView, View.OnClickListener {

    private TextInputLayout tilAccount;
    private TextInputLayout tilPassword;
    private TextInputEditText tieAccount;
    private TextInputEditText tiePassword;
    private MaterialButton btnRegister;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilAccount = findViewById(R.id.til_account);
        tilPassword = findViewById(R.id.til_password);
        tieAccount = findViewById(R.id.tie_account);
        tiePassword = findViewById(R.id.tie_password);
        btnRegister = findViewById(R.id.btn_login);
        btnRegister.setOnClickListener(this);

        presenter = new RegisterPresenter(this, this);
    }

    @Override
    public void clear() {
        tieAccount.setText("");
        tiePassword.setText("");
    }

    @Override
    public void updateView(String info) {
        tilAccount.setError(info);
    }

    @Override
    public void registerFail() {
        Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void destroyActivity() {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        String account = tieAccount.getText().toString();
        String password = tiePassword.getText().toString();
        Log.d(Constant.TAG, "account:" + account + ",password:" + password);
        presenter.doRegister(account, password);
    }
}
