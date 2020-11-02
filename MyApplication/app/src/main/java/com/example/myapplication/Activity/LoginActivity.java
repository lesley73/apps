package com.example.myapplication.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.myapplication.R;
import com.example.myapplication.data.MyDataBaseHelper;
import com.example.myapplication.utils.AlbumUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    TimePickerView timePickerView = null;
    private Button btn_timeSelector;
    private Button btn_login;
    private TextView tv_timePick;
    private EditText ev_name;
    private EditText ev_phone;
    private ImageView login_head;
    private String birthday, input_name, input_phone;
    private ProgressBar progressBar;
    private MyDataBaseHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.activity_login, null);
        btn_timeSelector = findViewById(R.id.btn_time_choose);
        tv_timePick = findViewById(R.id.tv_birthday);
        ev_name = findViewById(R.id.et_login_q1);
        ev_phone = findViewById(R.id.et_login_q2);
        btn_login = findViewById(R.id.btn_login);
        login_head = findViewById(R.id.img_upload_head);
        progressBar = findViewById(R.id.process_bar);
        dbHelper = new MyDataBaseHelper(this, "UserDB.db", null, 1);
        btn_timeSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();
                timePickerView.show();
            }
        });
        String path = getCacheDir().getPath();
        String fileName = "login_head";
        File file = new File(path, fileName);
        if (file.exists()) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                login_head.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            login_head.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.login_head));
        }
        final MyHandler handler = new MyHandler(new WeakReference<LoginActivity>(this));
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void initTimePicker() {
        timePickerView = new TimePickerBuilder(
                LoginActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                birthday = getTime(date);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .build();
        Dialog mDialog = timePickerView.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            timePickerView.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    class MyHandler extends Handler {
        private WeakReference<LoginActivity> activity;

        MyHandler(WeakReference<LoginActivity> activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                            tv_timePick.setText(birthday);
                            input_name = ev_name.getText().toString();
                            input_phone = ev_phone.getText().toString();
                            if (input_name.equals("李展鸿") && input_phone.equals("15961706056") && birthday.equals("1996年04月26日")) {
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(LoginActivity.this, CollectionActivity.class);
                                startActivity(intent);
                            } else {
                                Looper.prepare();
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "不会吧不会吧，其中有一个或多个答案错了啊，不会真的有人记不住吧", Toast.LENGTH_LONG).show();
                                Looper.loop();
                                ev_name.setText("");
                                ev_phone.setText("");
                                tv_timePick.setText("");
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}
