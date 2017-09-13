package com.xingjian.kav;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.posapi.PosApi;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by thinkpad on 2017/8/2.
 */

public class MyApp extends Application {
    private static Context context;
    public static ArrayList<Activity> activityList = new ArrayList<Activity>();
    public static PosApi posApi;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        posApi = PosApi.getInstance(this);
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
        SharedPreferences.Editor editor = MyApp.getEditor();
        editor.putBoolean("isLogin", false);
        editor.putString("cardId", "");
        editor.commit();
        for (Activity activity : activityList) {
            activity.finish();
        }

    }


}
