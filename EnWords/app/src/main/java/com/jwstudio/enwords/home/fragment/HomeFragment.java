package com.jwstudio.enwords.home.fragment;

import android.app.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jwstudio.enwords.R;

import com.jwstudio.enwords.study.StudyActivity;
import com.jwstudio.enwords.utils.RecordInfoState;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashSet;

import java.util.Map;

import com.jwstudio.enwords.home.view.ZWSignCalendarView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private MaterialButton btnPlan;
    private MaterialButton btnStart;
    private Activity mActivity;

    private ZWSignCalendarView calendarView;
    private TextView tvShow;
    private TextView tvToday;
    private ImageView ivPrevious;
    private ImageView ivNext;

    HashSet<String> sign = new HashSet<>(); // 存储打卡日期

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        getPunchCard();
        init(view);
        return view;
    }

    private void init(View view) {
        btnPlan = view.findViewById(R.id.btn_plan);
        btnStart = view.findViewById(R.id.btn_start);

        btnPlan.setOnClickListener(this);
        btnStart.setOnClickListener(this);

        calendarView = view.findViewById(R.id.calendarView);
        tvShow = view.findViewById(R.id.tv_calendar_show);
        tvToday = view.findViewById(R.id.tv_calendar_today);
        ivPrevious = view.findViewById(R.id.calendar_previous);
        ivNext = view.findViewById(R.id.calendar_next);

        tvShow.setOnClickListener(this);
        tvToday.setOnClickListener(this);
        ivPrevious.setOnClickListener(this);
        ivNext.setOnClickListener(this);

        calendarView.setDateListener(new ZWSignCalendarView.DateListener() {
            @Override
            public void change(int year, int month) {
                tvShow.setText(String.format("%s 年 %s 月", year, month));
            }

            @Override
            public void select(int year, int month, int day) {
                String d = "";
                Date date = null;

                // 格式化日期，如2019-01-01
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = dateFormat.parse(year + "-" + month + "-" + day);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                d = dateFormat.format(date);
                if (sign.contains(d)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", 1);
                    bundle.putString("date", d);
                    Intent intent = new Intent(mActivity, StudyActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        if (!sign.isEmpty()) {
            // 更新日历，sign存储的日期格式必须是2019-01-01
            calendarView.setSignRecords(sign);
        }
    }

    // 读取保存在本地打卡日期
    private void getPunchCard() {
        Map<String, ?> dateMap = RecordInfoState.getPunchCard(mActivity);
        if (!dateMap.isEmpty()) {
            for (Map.Entry<String, ?> entry : dateMap.entrySet()) {
                sign.add(entry.getKey());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_plan:
                setPlan();
                break;
            case R.id.btn_start:
                Intent intent = new Intent(mActivity, StudyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_calendar_today:
                // 当天
                calendarView.backCurrentMonth();
                break;
            case R.id.calendar_previous:
                // 上一月
                calendarView.showPreviousMonth();
                break;
            case R.id.calendar_next:
                // 下一月
                calendarView.showNextMonth();
                break;
            default:
                break;
        }
    }

    // 修改计划
    private void setPlan() {
        InputDialog.show(mActivity, "提示", "默认每天4个单词", "确定",
                new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog, String inputText) {
                        RecordInfoState.recordPlan(mActivity, inputText);
                        dialog.dismiss();
                    }
                }, "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }
}
