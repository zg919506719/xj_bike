package com.luying.handletaskdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private TextView login;
    private long time = 2000;
    private boolean full;
    private WeakHandler handler = new WeakHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text);
        login = (TextView) findViewById(R.id.text2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTask();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        startTask();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTask();
    }

    public void startTask() {
        handler.removeCallbacks(task);
        if (full) {
            tv.setText("视频非全屏");
            full = false;
        }

        handler.postDelayed(task, time);
    }

    public void stopTask() {
        handler.removeCallbacks(task);
        if (full) {
            tv.setText("视频非全屏");
            full = false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP) {
            startTask();
        }
        return super.dispatchTouchEvent(ev);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {

            if (!full) {
                tv.setText("视频全屏中");
                full = true;
            }

        }
    };


}
