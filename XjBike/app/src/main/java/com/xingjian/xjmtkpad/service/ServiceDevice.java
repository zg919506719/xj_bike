package com.xingjian.xjmtkpad.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.posapi.PosApi;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.vilyever.socketclient.SocketClient;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.TimeReq;

/**
 * Created by thinkpad on 2017/8/8.
 */

public class ServiceDevice extends Service {
    private PosApi mPosApi=null;
    private SocketClient client;
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                super.run();
                mPosApi= MyApp.posApi;
                client = MyApp.Client;
                while (true){
                    mPosApi.m1Search(500);
                    mPosApi.getTemperatureHumidity();
                    TimeReq req = new TimeReq();
                    req.setSiteId("59");
                    req.setCmd("05");
                    req.setWay("1");
                    req.setSn("0");
                    req.setData(new TimeReq.DataBean());
                    String s = JSON.toJSONString(req);
                    client.sendString(s);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
