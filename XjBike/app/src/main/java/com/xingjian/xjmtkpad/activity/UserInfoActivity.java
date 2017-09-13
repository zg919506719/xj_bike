package com.xingjian.xjmtkpad.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.UserInfoReq;
import com.xingjian.xjmtkpad.beanresponse.UserInfoRes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.cardId)
    TextView cardId;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.identity)
    TextView identity;
    @BindView(R.id.phone)
    TextView phone;
    private SocketClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        client = MyApp.Client;
        SharedPreferences preference = MyApp.getPreference();
        String cardId = preference.getString("cardId", "");
        UserInfoReq req = new UserInfoReq();
//        type 类型（1-用户刷卡查询，2-用户卡ID输入查询，3-身份证号输入查询）
//        value 用户卡内部ID、用户卡ID或身份证号
        req.setSiteId("59");
        req.setCmd("21");
        req.setWay("1");
        req.setSn("0");
        UserInfoReq.DataBean data = new UserInfoReq.DataBean();
        data.setType("2");
        Log.i("haha", cardId);
        if (!cardId.isEmpty()) {
            data.setValue(cardId);
        }
//        data.setValue("658D17E2");
//        data.setValue("2B0EF1E4");
        req.setData(data);
        String s = JSON.toJSONString(req);
        client.sendString(s);
        socketRegister();
    }

    private void socketRegister() {
        client.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {

            }

            @Override
            public void onDisconnected(SocketClient client) {

            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                String message = responsePacket.getMessage();
                Log.i("haha", message);
                if (message.contains("\"cmd\":\"21\"")) {
                    UserInfoRes userRes = JSONObject.parseObject(message, UserInfoRes.class);
                    UserInfoRes.DataBean data = userRes.getData();
                    cardId.setText(data.getUser_cardId());
                    name.setText(data.getUser_name());
                    identity.setText(data.getUser_identity());
                    phone.setText(data.getUser_phone());
                }
            }
        });
    }

    @OnClick({R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

}
