package com.xingjian.xjmtkpad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class StaffAbnormalActivity extends AppCompatActivity {
    @BindView(R.id.tv_admincardnumber)
    TextView tvAdmincardnumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_data);
        ButterKnife.bind(this);
        MyApp.addActivity(this);
        initData();
    }

    private void initData() {
        tvAdmincardnumber.setText(getIntent().getExtras().getString("staff"));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApp.exit();
    }

    @OnClick({R.id.btn_home, R.id.btn_attendance, R.id.btn_cardprocess})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                MyApp.exit();
                break;
            case R.id.btn_attendance:
                startActivity(new Intent(this, StaffAbnormalActivity.class));
                break;
            case R.id.btn_cardprocess:
                break;
        }
    }
}
