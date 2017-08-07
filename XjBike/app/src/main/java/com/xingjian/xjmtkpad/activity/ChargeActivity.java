package com.xingjian.xjmtkpad.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.adapter.AdapterRechargeInfo;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.RechargeReq;
import com.xingjian.xjmtkpad.beanresponse.RechargeRes;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/7.
 */
//充值记录
public class ChargeActivity extends AppCompatActivity {
    @BindView(R.id.tv_cardnumbernow)
    TextView tvCardnumbernow;
    @BindView(R.id.sp_item)
    Spinner spItem;
    @BindView(R.id.lv_rechargerecord)
    ListView lvRechargerecord;

    private String[] queryType = {"扣款时间范围", "站点ID", "扣款金额范围"};
    private SimpleAdapter mSimpleAdapter;
    private SocketClient client;
    private String cardId;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        client = MyApp.Client;
        SharedPreferences preference = MyApp.getPreference();
        cardId = preference.getString("cardId", "");
        initSpinner();
        setSocket();
    }

    private void initSpinner() {
        spItem.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, queryType));
        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String reqJson25_2 = "{\"siteId\":\"59\",\"cmd\":\"25\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"type\":\"2\",\"value\":\"2B0EF1E4\",\"query\":[{\"query_type\":\"2\",\"query_way\":{\"start_time\":\"2017-06-20 16:53:30\",\"end_time\":\"2017-07-10 17:53:30\"}}]}}";

                RechargeReq req = new RechargeReq();
                req.setSiteId("59");
                req.setCmd("26");
                req.setWay("1");
                req.setSn("0");
                RechargeReq.DataBean data = new RechargeReq.DataBean();
                data.setType("2");
                data.setValue(cardId);
                switch (i) {
                    case 0://充值时间范围
                        ArrayList<RechargeReq.DataBean.QueryBean> query = new ArrayList<>();
                        RechargeReq.DataBean.QueryBean element = new RechargeReq.DataBean.QueryBean();
                        element.setQuery_type("1");
                        RechargeReq.DataBean.QueryBean.QueryWayBean queryWayBean = new RechargeReq.DataBean.QueryBean.QueryWayBean();
                        queryWayBean.setStart_time("2017-06-20 16:53:30");
                        queryWayBean.setEnd_time("2017-07-10 17:53:30");
                        element.setQuery_way(queryWayBean);
                        query.add(element);
                        data.setQuery(query);
                        req.setData(data);
                        client.sendString(JSONObject.toJSONString(req));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSocket() {
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
                if (message.contains("\"cmd\":\"26\"")) {
                    RechargeRes rechargeRes = JSONObject.parseObject(message, RechargeRes.class);
                    tvCardnumbernow.setText(rechargeRes.getData().getUser_cardId());
                    String count = rechargeRes.getData().getCount();
                    ArrayList<RechargeRes.DataBean.ListBean> list = (ArrayList<RechargeRes.DataBean.ListBean>) rechargeRes.getData().getList();
                    if (list == null || list.isEmpty()) {
                        lvRechargerecord.setVisibility(View.GONE);
                    } else {
                        lvRechargerecord.setVisibility(View.VISIBLE);
                        lvRechargerecord.setAdapter(new AdapterRechargeInfo(list));
                    }
                }
            }
        });
    }
    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }
}
