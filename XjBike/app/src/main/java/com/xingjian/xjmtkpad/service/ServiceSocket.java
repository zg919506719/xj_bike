package com.xingjian.xjmtkpad.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.base.MyApp;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class ServiceSocket extends Service {
    private static String TAG="haha";
    @Override
    public void onCreate() {
        super.onCreate();
        upLoad();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void upLoad() {
        SocketClient localSocketClient = MyApp.Client;
        localSocketClient.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {

            }

            @Override
            public void onDisconnected(SocketClient client) {

            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                Log.i(TAG, responsePacket.getMessage());
            }
        });
    }
}
