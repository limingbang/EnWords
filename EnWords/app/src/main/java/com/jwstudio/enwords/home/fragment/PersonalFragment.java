package com.jwstudio.enwords.home.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.home.MainActivity;
import com.jwstudio.enwords.home.view.PersonalItemView;

public class PersonalFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;

    private PersonalItemView pInformation;
    private PersonalItemView pPlan;
    private PersonalItemView pCollect;
    private PersonalItemView pChangePassword;
    private PersonalItemView pchangePhone;
    private PersonalItemView pSetting;
    private PersonalItemView pVersion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        pInformation = v.findViewById(R.id.piv_account);
        pPlan = v.findViewById(R.id.piv_plan);
        pCollect = v.findViewById(R.id.piv_collect);
        pChangePassword = v.findViewById(R.id.piv_change_password);
        pchangePhone = v.findViewById(R.id.piv_change_phone);
        pSetting = v.findViewById(R.id.piv_setting);
        pVersion = v.findViewById(R.id.piv_version);

        pInformation.setOnClickListener(this);
        pPlan.setOnClickListener(this);
        pCollect.setOnClickListener(this);
        pChangePassword.setOnClickListener(this);
        pchangePhone.setOnClickListener(this);
        pSetting.setOnClickListener(this);
        pVersion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.piv_account:
                toast("个人信息-待完善");
                break;
            case R.id.piv_plan:
                toast("我的计划-待完善");
                break;
            case R.id.piv_collect:
                toast("我的收藏-待完善");
                break;
            case R.id.piv_change_password:
                toast("修改密码-待完善");
                break;
            case R.id.piv_change_phone:
                toast("修改手机-待完善");
                break;
            case R.id.piv_setting:
                toast("设置-待完善");
                break;
            case R.id.piv_version:
                toast("版本-待完善");
                break;
            default:
                break;
        }
    }

    private void toast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }
}
