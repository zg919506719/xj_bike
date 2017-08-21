package com.xingjian.xjmtkpad.activity;

import android.content.Intent;
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

    @OnClick({R.id.btn_back, R.id.user_info, R.id.card_info, R.id.search, R.id.money, R.id.borrow, R.id.returnBike, R.id.help})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                MyApp.exit();
                break;
            case R.id.user_info:
//                用户信息
                startActivity(new Intent(this,UserInfoActivity.class));
                break;
            case R.id.card_info:
//                卡片信息
                startActivity(new Intent(this,CardInfoActivity.class));
                break;
            case R.id.search:
//                租还车信息
                startActivity(new Intent(this,ReturnInfoActivity.class));
                break;
            case R.id.money:
//                充值信息
                startActivity(new Intent(this,RechargeActivity.class));
                break;
            case R.id.borrow:
//                借车
                startActivity(new Intent(this,BorrowActivity.class));
                break;
            case R.id.returnBike:
//                还车
                startActivity(new Intent(this,ReturnActivity.class));
                break;
            case R.id.help:
//                帮助
                startActivity(new Intent(this,HelpActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApp.exit();
    }
}
