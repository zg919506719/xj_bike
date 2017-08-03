package com.xingjian.xjmtkpad.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.posapi.PosApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.inter.InterLogin;
import com.xingjian.xjmtkpad.present.PresentLogin;
import com.xingjian.xjmtkpad.service.ServiceSocket;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements InterLogin {
    private PresentLogin presentLogin;
    private PosApi mPosApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startService(new Intent(this, ServiceSocket.class));
        mPosApi= MyApp.posApi;
        presentLogin = new PresentLogin(this);
        presentLogin.initView();
    }


    @OnClick({R.id.btn_card_login, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_card_login:
                mPosApi.getTemperatureHumidity();
                break;
            case R.id.btn_login:
                presentLogin.showNoCardDialog();
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public PosApi getApi() {
        return mPosApi;
    }
}
