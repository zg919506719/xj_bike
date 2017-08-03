package com.xingjian.xjmtkpad.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.posapi.PosApi;
import android.widget.Toast;

import com.vilyever.socketclient.SocketClient;
import com.xingjian.xjmtkpad.utils.MySocket;

import java.util.ArrayList;

/**
 * Created by thinkpad on 2017/8/2.
 */

public class MyApp extends Application {
    private static Context context;
    private static ArrayList<Activity> activityList = new ArrayList<Activity>();
    public static SocketClient Client;
    public static PosApi posApi;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Client = new MySocket().getLocalSocketClient();
        Client.connect();
        posApi=PosApi.getInstance(this);
        posApi.initPosDev("ima3511");
    }

    public static SharedPreferences getPreference() {
        return context.getSharedPreferences("MyAPP", Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor() {
        return getPreference().edit();
    }

    public static void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 添加Activity到容器中
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }

    }




}
