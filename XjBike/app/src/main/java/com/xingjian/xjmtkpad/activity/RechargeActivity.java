package com.xingjian.xjmtkpad.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/7.
 */
//充值记录
public class RechargeActivity extends AppCompatActivity {
    @BindView(R.id.tv_cardnumbernow)
    TextView tvCardnumbernow;
    @BindView(R.id.lv_rechargerecord)
    ListView lvRechargerecord;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.end)
    Button end;

    //    private String[] queryType = {"充值时间范围", "客服网点", "充值金额范围", "充后余额范围"};
    private SimpleAdapter mSimpleAdapter;
    private SocketClient client;
    private String cardId;
    private ProgressDialog progressDialog;
    private Calendar cal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        client = MyApp.Client;
        SharedPreferences preference = MyApp.getPreference();
        cardId = preference.getString("cardId", "");
        initData();
        setSocket();
    }

    private void initData() {
        cal = Calendar.getInstance();
        progressDialog = new ProgressDialog(this);
        String text = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        end.setText(text);
        sendReq("2017-01-01 00:00:00", "2017-01-01" + " 24:00:00");
    }

    private void sendReq(String start, String end) {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        RechargeReq req = new RechargeReq();
        req.setSiteId("59");
        req.setCmd("26");
        req.setWay("1");
        req.setSn("0");
        RechargeReq.DataBean data = new RechargeReq.DataBean();
        data.setType("2");
        data.setValue(cardId);
        //充值时间范围
        ArrayList<RechargeReq.DataBean.QueryBean> query = new ArrayList<>();
        RechargeReq.DataBean.QueryBean element = new RechargeReq.DataBean.QueryBean();
        element.setQuery_type("1");
        RechargeReq.DataBean.QueryBean.QueryWayBean queryWayBean = new RechargeReq.DataBean.QueryBean.QueryWayBean();
        queryWayBean.setStart_time(start);
        queryWayBean.setEnd_time(end);
        element.setQuery_way(queryWayBean);
        query.add(element);
        data.setQuery(query);
        req.setData(data);
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
                String message = responsePacket.getMessage();
                Log.i("haha", message);
                if (message.contains("\"cmd\":\"26\"")) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    RechargeRes rechargeRes = JSONObject.parseObject(message, RechargeRes.class);
                    tvCardnumbernow.setText(rechargeRes.getData().getUser_cardId());
                    String count = rechargeRes.getData().getCount();
                    ArrayList<RechargeRes.DataBean.ListBean> list = (ArrayList<RechargeRes.DataBean.ListBean>) rechargeRes.getData().getList();
                    if (count.equals("0")) {
                        lvRechargerecord.setVisibility(View.GONE);
                    } else {
                        lvRechargerecord.setVisibility(View.VISIBLE);
                        lvRechargerecord.setAdapter(new AdapterRechargeInfo(list));
                    }
                }
            }
        });
    }

    @OnClick({R.id.start, R.id.end, R.id.btn_up, R.id.btn_back, R.id.btn_down, R.id.sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        start.setText(i + "-" + (i1 + 1) + "-" + i2);
                    }
                }, cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.end:
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        end.setText(i + "-" + (i1 + 1) + "-" + i2);
                    }
                }, cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)
                ).show();
                break;
            case R.id.btn_up:
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_down:
                break;
            case R.id.sure:
                sendReq(start.getText() + " 00:00:00", end.getText() + " 24:00:00");
                break;
        }
    }


//    private void initSpinner() {
//        spItem.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, queryType));
//        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                RechargeReq req = new RechargeReq();
//                req.setSiteId("59");
//                req.setCmd("26");
//                req.setWay("1");
//                req.setSn("0");
//                RechargeReq.DataBean data = new RechargeReq.DataBean();
//                data.setType("2");
//                data.setValue(cardId);
//                switch (i) {
//                    case 0://充值时间范围
//                        ArrayList<RechargeReq.DataBean.QueryBean> query = new ArrayList<>();
//                        RechargeReq.DataBean.QueryBean element = new RechargeReq.DataBean.QueryBean();
//                        element.setQuery_type("1");
//                        RechargeReq.DataBean.QueryBean.QueryWayBean queryWayBean = new RechargeReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean.setStart_time("2017-06-20 16:53:30");
//                        queryWayBean.setEnd_time("2017-07-10 17:53:30");
//                        element.setQuery_way(queryWayBean);
//                        query.add(element);
//                        data.setQuery(query);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                    case 1://客服网点,测试customer_id 1
//                        ArrayList<RechargeReq.DataBean.QueryBean> query1 = new ArrayList<>();
//                        RechargeReq.DataBean.QueryBean element1 = new RechargeReq.DataBean.QueryBean();
//                        element1.setQuery_type("2");
//                        RechargeReq.DataBean.QueryBean.QueryWayBean queryWayBean1 = new RechargeReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean1.setCustomer_id("1");
//                        element1.setQuery_way(queryWayBean1);
//                        query1.add(element1);
//                        data.setQuery(query1);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                    case 2://充值金额范围 0-200
//                        ArrayList<RechargeReq.DataBean.QueryBean> query2 = new ArrayList<>();
//                        RechargeReq.DataBean.QueryBean element2 = new RechargeReq.DataBean.QueryBean();
//                        element2.setQuery_type("4");
//                        RechargeReq.DataBean.QueryBean.QueryWayBean queryWayBean2 = new RechargeReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean2.setFees_down("0");
//                        queryWayBean2.setFees_up("200");
//                        element2.setQuery_way(queryWayBean2);
//                        query2.add(element2);
//                        data.setQuery(query2);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                    case 3://充后余额范围 0-200
//                        ArrayList<RechargeReq.DataBean.QueryBean> query3 = new ArrayList<>();
//                        RechargeReq.DataBean.QueryBean element3 = new RechargeReq.DataBean.QueryBean();
//                        element3.setQuery_type("8");
//                        RechargeReq.DataBean.QueryBean.QueryWayBean queryWayBean3 = new RechargeReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean3.setBalance_down("0");
//                        queryWayBean3.setBalance_up("200");
//                        element3.setQuery_way(queryWayBean3);
//                        query3.add(element3);
//                        data.setQuery(query3);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//    }
}
