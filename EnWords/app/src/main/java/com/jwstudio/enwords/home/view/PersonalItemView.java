package com.jwstudio.enwords.home.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jwstudio.enwords.R;

public class PersonalItemView extends LinearLayout {

    private ImageView imageView;//item的图标
    private TextView textView;//item的文字
    private ImageView bottomView;
    private boolean isbootom = true;//是否显示底部的下划线


    public PersonalItemView(Context context) {
        this(context, null);
    }

    public PersonalItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PersonalItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_personal_item, this);
        //获取设置属性对象
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PersonalItemView);
        //获取控件，设置属性值
        isbootom = ta.getBoolean(R.styleable.PersonalItemView_showBottomLine, true);

        bottomView = findViewById(R.id.item_bottom);
        imageView = findViewById(R.id.item_img);
        textView = findViewById(R.id.item_text);

        textView.setText(ta.getString(R.styleable.PersonalItemView_showText));
        imageView.setBackgroundResource(ta.getResourceId(R.styleable.PersonalItemView_showLeftImg,
                R.drawable.settings));
        //回收属性对象
        ta.recycle();
        initview();

    }

    private void initview() {
        //如果isbootom为true，显示下划线，否则隐藏
        if (isbootom) {
            bottomView.setVisibility(View.VISIBLE);
        } else {
            bottomView.setVisibility(View.GONE);
        }
    }

}
