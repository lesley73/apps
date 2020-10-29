package com.example.myapplication.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;

public class CustomLoginInfo extends ConstraintLayout {

    private TextView tv_login_info;
    private EditText et_login_info;

    public CustomLoginInfo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_login_info, this, true);
        tv_login_info = findViewById(R.id.tv_login_q1);
        et_login_info = findViewById(R.id.et_login_q1);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomLoginInfo, 0, 0);
        if (attributes != null){
            int textColor = attributes.getResourceId(R.styleable.CustomLoginInfo_text_color, Color.rgb(255, 255, 255));
            tv_login_info.setTextColor(getResources().getColor(textColor));

            int textSize = attributes.getResourceId(R.styleable.CustomLoginInfo_text_size, R.dimen.px50);
            tv_login_info.setTextSize(getResources().getDimension(textSize));


        }
    }
}
