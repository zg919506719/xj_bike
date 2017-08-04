package com.xingjian.xjmtkpad.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.posapi.PosApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.inter.InterLogin;
import com.xingjian.xjmtkpad.present.PresentLogin;
import com.xingjian.xjmtkpad.service.ServiceSocket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements InterLogin {
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.location)
    TextView location;
    private PresentLogin presentLogin;
    private PosApi mPosApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        startService(new Intent(this, ServiceSocket.class));
        mPosApi = MyApp.posApi;
        presentLogin = new PresentLogin(this);
        presentLogin.initView();
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public PosApi getApi() {
        return mPosApi;
    }

    @Override
    public TextView getTime() {
        return time;
    }

    @Override
    public TextView getTemp() {
        return temp;
    }

    @Override
    public TextView getDevice() {
        return name;
    }

    @Override
    public TextView getLocation() {
        return location;
    }

    @OnClick({R.id.net, R.id.station, R.id.btn_login, R.id.btn_card_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.net:
                startActivity(new Intent(MainActivity.this,NetActivity.class));
                break;
            case R.id.station:
                startActivity(new Intent(MainActivity.this,StationActivity.class));
                break;
            case R.id.btn_login:
                presentLogin.showNoCardDialog();
                break;
            case R.id.btn_card_login:
                mPosApi.getTemperatureHumidity();
                break;
        }
    }
}
