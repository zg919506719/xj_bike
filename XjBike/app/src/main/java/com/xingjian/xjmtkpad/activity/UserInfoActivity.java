package com.xingjian.xjmtkpad.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.CardInfoReq;
import com.xingjian.xjmtkpad.beanrequest.TimeReq;
import com.xingjian.xjmtkpad.beanresponse.CardInfoRes;
import com.xingjian.xjmtkpad.beanresponse.NameRes;

import java.util.Iterator;
import java.util.List;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class UserInfoActivity extends AppCompatActivity {

    private SocketClient client;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        client = MyApp.Client;
        CardInfoReq req = new CardInfoReq();
//        type 类型（1-用户刷卡查询，2-用户卡ID输入查询，3-身份证号输入查询）
//        value 用户卡内部ID、用户卡ID或身份证号
        req.setSiteId("59");
        req.setCmd("23");
        req.setWay("1");
        req.setSn("0");
        CardInfoReq.DataBean data = new CardInfoReq.DataBean();
        data.setType("2");
        data.setValue("658D17E2");
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
                if (message.contains("\"cmd\":\"23\"")) {
                    CardInfoRes cardRes = JSONObject.parseObject(message, CardInfoRes.class);
                    CardInfoRes.DataBean data = cardRes.getData();
                    //注释
//                    user_cardId 用户卡ID
//                    user_name 用户姓名
//                    user_cardState 用户卡状态
//                    operate_num 操作记录次数
//                    operate_type 操作类型
//                    operates_time 操作时间
//                    operates_result 操作结果（1-成功，0-失败）
                    String user_cardId = data.getUser_cardId();
                    String user_cardState = data.getUser_cardState();
                    String user_name = data.getUser_name();
                    String count = data.getCount();
                    String page = data.getPage();
                    String prepage = data.getPrepage();
                    List<CardInfoRes.DataBean.ListBean> list = data.getList();
                    Iterator<CardInfoRes.DataBean.ListBean> iterator = list.iterator();
                    while (iterator.hasNext()){
                        CardInfoRes.DataBean.ListBean bean = iterator.next();
                    }
                }
            }
        });
    }
}
