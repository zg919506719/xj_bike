package com.luying.handletaskdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private TextView login;
    private long time = 1000;
    private int count;
    private boolean full;
    private WeakHandler handler = new WeakHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text);
        login = (TextView) findViewById(R.id.text2);

    }
    public void startTask(){
        handler.removeCallbacks(task);
        count = 0;
        handler.postDelayed(task, time);
    }

    public void stopFull(){
        count = 0;
        tv.setText("视频非全屏");
    }

    public void stopTask(){
        handler.removeCallbacks(task);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP){
            startTask();
        }else if (action == MotionEvent.ACTION_DOWN){
            if (full){
                stopFull();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            count ++;
            if (count == 10){

                full = true;
                tv.setText("视频全屏中");
            }
            handler.postDelayed(task, time);

        }
    };


}
