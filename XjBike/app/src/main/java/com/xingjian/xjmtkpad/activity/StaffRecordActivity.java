package com.xingjian.xjmtkpad.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.adapter.AdapterRecord;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.StaffCheckReq;
import com.xingjian.xjmtkpad.beanrequest.StaffRecordReq;
import com.xingjian.xjmtkpad.beanresponse.StaffRecordRes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class StaffRecordActivity extends AppCompatActivity {
    @BindView(R.id.tv_card)
    TextView tvCard;
    @BindView(R.id.lv_rentrecord)
    ListView lvRentrecord;
    private SocketClient client;
    private String staff;
    private int page = 1;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_record);
        ButterKnife.bind(this);
        client = MyApp.Client;
        setSocket();
        initData();
    }

    private void initData() {
        progressDialog = new ProgressDialog(this);
        staff = getIntent().getExtras().getString("staff");
        tvCard.setText(staff);
        sendReq(page + "");
//        String reqJson28 = "{\"siteId\":\"59\",\"cmd\":\"28\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"type\":\"2\",\"value\":\"1234\"}}";
    }

    private void sendReq(String page) {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        StaffRecordReq req = new StaffRecordReq();
        req.setSiteId("59");
        req.setCmd("28");
        req.setWay("1");
        req.setSn("0");
        StaffRecordReq.DataBean dataBean = new StaffRecordReq.DataBean();
        dataBean.setType("2");
        dataBean.setValue(staff);
        dataBean.setPage(page);
        dataBean.setRows("10");
        req.setData(dataBean);
        client.sendString(JSONObject.toJSONString(req));
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
                String json = responsePacket.getMessage();
                Log.i("test", json);
                if (json.contains("\"cmd\":\"28\"")) {//考勤信息查询响应
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    StaffRecordRes staffRecordRes = JSONObject.parseObject(json, StaffRecordRes.class);
                    StaffRecordRes.DataBean data = staffRecordRes.getData();
                    if (data.getCount().equals("0")) {
                        MyApp.showToast("没有数据");
                    } else {
                        ArrayList<StaffRecordRes.DataBean.ListBean> list = (ArrayList<StaffRecordRes.DataBean.ListBean>) data.getList();
                        lvRentrecord.setAdapter(new AdapterRecord(list));
                    }

                }
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.btn_up, R.id.btn_back, R.id.btn_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_up:
                page--;
                sendReq(page + "");
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_down:
                page++;
                sendReq(page + "");
                break;
        }
    }
}
