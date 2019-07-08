package com.jwstudio.enwords.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class RecordInfoState {

    // 记录登录状态
    public static void logined(Context context, String account) {
        SharedPreferences sp = context.getSharedPreferences("state", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("account", account);
        editor.commit();
    }

    public static String getAccount(Context context) {
        SharedPreferences sp = context.getSharedPreferences("state", Context.MODE_PRIVATE);
        String a = sp.getString("account", "account");
        return a;
    }

    // 记录每天学习单词的个数
    public static void recordPlan(Context context, String plan) {
        SharedPreferences sp = context.getSharedPreferences("plan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("plan", plan);
        editor.commit();
    }

    public static String getPlan(Context context) {
        SharedPreferences sp = context.getSharedPreferences("plan", Context.MODE_PRIVATE);
        String plan = sp.getString("plan", "4");
        return plan;
    }

    // 记录打卡的日期
    public static void recordPunchCard(Context context, String date) {
        SharedPreferences sp = context.getSharedPreferences("date", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(date, date);
        editor.commit();
    }

    public static Map<String, ?> getPunchCard(Context context) {
        SharedPreferences sp = context.getSharedPreferences("date", Context.MODE_PRIVATE);
        return sp.getAll();
    }

}
