package com.example.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.view.TimePickerView;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.SharedPreUtil;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends AppCompatActivity {

    private Button btn_jump;
    MyHandler handler = new MyHandler(WelcomeActivity.this);

    final Message message = new Message();
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                message.what = 1;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        thread.start();
        btn_jump = findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.what = 0;
                handler.sendMessage(message);
                boolean userIsLogin = (boolean) SharedPreUtil.getParam(WelcomeActivity.this,
                        SharedPreUtil.IS_LOGIN, false);
                if (userIsLogin) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });

    }

    private class MyHandler extends Handler {
        private WeakReference<WelcomeActivity> welcomeActivity;

        public MyHandler(WelcomeActivity welcomeActivity) {
            this.welcomeActivity = new WeakReference<WelcomeActivity>(welcomeActivity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            WelcomeActivity activity = welcomeActivity.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            boolean userIsLogin = (boolean) SharedPreUtil.getParam(WelcomeActivity.this, SharedPreUtil.IS_LOGIN, false);

            switch (msg.what) {
                case 1:
                    if (userIsLogin){
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    break;
                default:
                    thread.interrupt();
            }
        }
    }
}
