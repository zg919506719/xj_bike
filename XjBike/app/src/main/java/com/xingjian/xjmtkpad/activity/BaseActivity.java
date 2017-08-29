package com.xingjian.xjmtkpad.activity;

import android.support.v7.app.AppCompatActivity;

import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.utils.WeakHandler;

/**
 * Created by thinkpad on 2017/8/29.
 */

public class BaseActivity extends AppCompatActivity {
    WeakHandler handler = new WeakHandler();
    Runnable task = new Runnable() {
        @Override
        public void run() {
            MyApp.exit();
        }
    };
    public static int TimeExit = 20000;

    public void startExit() {
        handler.removeCallbacks(task);
        handler.postDelayed(task, TimeExit);
    }

    public void finishRunnable() {
        handler.removeCallbacks(task);
    }
}
