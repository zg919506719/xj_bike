package com.xingjian.xjmtkpad.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.posapi.PosApi;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.vilyever.socketclient.SocketClient;
import com.xingjian.xjmtkpad.base.Constant;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.TimeReq;
import com.xingjian.xjmtkpad.utils.WeakHandler;

/**
 * Created by thinkpad on 2017/8/8.
 */

public class ServiceDevice extends Service {
    private PosApi mPosApi=null;
    private SocketClient client;
    private WeakHandler handler = new WeakHandler();
    @Override
    public void onCreate() {
        super.onCreate();
        mPosApi= MyApp.posApi;
        client = MyApp.Client;
        handler.post(task);
        handler.post(M1_task);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable task=new Runnable() {
        @Override
        public void run() {
            mPosApi.getTemperatureHumidity();
            handler.postDelayed(task, Constant.CIRCLE_TIME);
        }
    };
    private Runnable M1_task=new Runnable() {
        @Override
        public void run() {
            mPosApi.m1Search(500);
            TimeReq req = new TimeReq();
            req.setSiteId("59");
            req.setCmd("05");
            req.setWay("1");
            req.setSn("0");
            req.setData(new TimeReq.DataBean());
            String s = JSON.toJSONString(req);
            client.sendString(s);
            handler.postDelayed(M1_task, Constant.M1_CIRCLE_TIME);
        }
    };
}
