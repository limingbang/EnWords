package com.jwstudio.enwords.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jwstudio.enwords.R;
import com.jwstudio.enwords.exercise.ExerciseActivity;

public class ExerciseFragment extends Fragment implements View.OnClickListener {

    private Activity mActivity;

    private MaterialCardView mcvListen;
    private MaterialCardView mcvWordUse;
    private MaterialCardView mcvListenWrite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        mcvListen = v.findViewById(R.id.mcv_listen);
        mcvWordUse = v.findViewById(R.id.mcv_word_use);
        mcvListenWrite = v.findViewById(R.id.mcv_listen_write);

        mcvListen.setOnClickListener(this);
        mcvWordUse.setOnClickListener(this);
        mcvListenWrite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mcv_listen:
                Intent intent = new Intent(mActivity,ExerciseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.mcv_word_use:
                toast("词汇运用-待完善");
                break;
            case R.id.mcv_listen_write:
                toast("听写-待完善");
                break;
            default:
                break;
        }
    }

    private void toast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }
}
