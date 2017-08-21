package com.xingjian.xjmtkpad.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.LoginReq;
import com.xingjian.xjmtkpad.beanresponse.LoginRes;
import com.xingjian.xjmtkpad.utils.StringDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/14.
 */

public class StaffLoginActivity extends AppCompatActivity {
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_passwordr)
    EditText etPasswordr;

    private SocketClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        ButterKnife.bind(this);
        MyApp.addActivity(this);
        client = MyApp.Client;
        socketRegister();
    }

    //账号密码登录
    public void login(final String username, final String password) {
        LoginReq req = new LoginReq();
        req.setSiteId("59");
        req.setCmd("d2");
        req.setWay("1");
        req.setSn("0");
        req.setAddress("济南");
        LoginReq.DataBean data = new LoginReq.DataBean();
        data.setCode(username);
        data.setPassword(password);
        req.setData(data);
        String s = JSON.toJSONString(req);
        client.sendString(s);
    }

    @OnClick({R.id.btn_back, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                MyApp.exit();
                break;
            case R.id.btn_login:
                String username1 = etNumber.getText().toString();
                String password1 = etPasswordr.getText().toString();
                if (username1.isEmpty()) {
                    new StringDialog().showString(this, "请输入用户");
                } else if (password1.isEmpty()) {
                    new StringDialog().showString(this, "请输入密码");
                } else {
                    login(username1, password1);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApp.exit();
    }

    private void socketRegister() {
        client.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {
            }

            @Override
            public void onDisconnected(SocketClient client) {
                client.connect();
            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                String message = responsePacket.getMessage();
                if (message.contains("\"cmd\":\"d2\"")) {
                    LoginRes loginRes = JSONObject.parseObject(message, LoginRes.class);
                    LoginRes.DataBean data = loginRes.getData();
                    if (data.getCode().equals("1000")) {
                        boolean isLogin = MyApp.getPreference().getBoolean("isLogin", false);
                        if (!isLogin) {
                            SharedPreferences.Editor editor = MyApp.getEditor();
                            editor.putString("cardId", data.getIdCard());
                            editor.putBoolean("isLogin", true);
                            editor.commit();
                            Intent intent = new Intent(StaffLoginActivity.this, StaffAttendanceActivity.class);
                            intent.putExtra("staff", data.getIdCard());
                            startActivity(intent);
                        }
                    } else {
                        MyApp.showToast("卡的身份有误，登录失败");
                    }
                }
            }
        });
    }
}
