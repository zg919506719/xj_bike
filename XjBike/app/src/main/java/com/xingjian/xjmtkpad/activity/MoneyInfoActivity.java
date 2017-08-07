package com.xingjian.xjmtkpad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xingjian.xjmtkpad.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class MoneyInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_home, R.id.btn_rechargerecord, R.id.btn_chargerecord})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                finish();
                break;
            case R.id.btn_rechargerecord:
                startActivity(new Intent(this,RechargeActivity.class));
                break;
            case R.id.btn_chargerecord:
                startActivity(new Intent(this,ChargeActivity.class));
                break;
        }
    }
}
