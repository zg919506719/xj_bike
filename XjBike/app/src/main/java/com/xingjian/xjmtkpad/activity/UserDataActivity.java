package com.xingjian.xjmtkpad.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class UserDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        ButterKnife.bind(this);
        MyApp.addActivity(this);
    }

    @OnClick({R.id.btn_back, R.id.user_info, R.id.card_info, R.id.search, R.id.money, R.id.map_info, R.id.net_info, R.id.help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                MyApp.exit();
                break;
            case R.id.user_info:
//                startActivity();
                break;
            case R.id.card_info:
                break;
            case R.id.search:
                break;
            case R.id.money:
                break;
            case R.id.map_info:
                break;
            case R.id.net_info:
                break;
            case R.id.help:
                break;
        }
    }
}
