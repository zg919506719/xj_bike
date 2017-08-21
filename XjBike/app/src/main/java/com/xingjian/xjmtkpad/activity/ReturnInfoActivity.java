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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.xingjian.xjmtkpad.adapter.AdapterRentInfo;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.RentRecordReq;
import com.xingjian.xjmtkpad.beanresponse.RentRecordsRes;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class ReturnInfoActivity extends AppCompatActivity {
    @BindView(R.id.tv_cardnumbernow)
    TextView tvCardnumbernow;
    @BindView(R.id.lv_rentrecord)
    ListView lvRentrecord;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.end)
    Button end;
    //    private String[] queryType = {"借车时间范围", "还车时间范围", "站点ID", "站点ID+锁桩ID", "车辆ID", "扣费范围"};
    private SimpleAdapter mSimpleAdapter;
    private SocketClient client;
    private String cardId;
    private ProgressDialog progressDialog;
    private Calendar cal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_info);
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
        sendReq("2017-01-01 00:00:00", text + " 24:00:00");
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
                if (message.contains("\"cmd\":\"25\"")) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    RentRecordsRes rentRecordsRes = JSONObject.parseObject(message, RentRecordsRes.class);
                    tvCardnumbernow.setText(rentRecordsRes.getData().getUser_cardId());
                    String count = rentRecordsRes.getData().getCount();
                    ArrayList<RentRecordsRes.DataBean.ListBean> list = (ArrayList<RentRecordsRes.DataBean.ListBean>) rentRecordsRes.getData().getList();
                    if (count==null) {
                        lvRentrecord.setVisibility(View.GONE);
                    } else {
                        lvRentrecord.setVisibility(View.VISIBLE);
                        lvRentrecord.setAdapter(new AdapterRentInfo(list));
                    }
                }
            }
        });
    }


    private void sendReq(String start, String end) {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        RentRecordReq req = new RentRecordReq();
        req.setSiteId("59");
        req.setCmd("25");
        req.setWay("1");
        req.setSn("0");
        RentRecordReq.DataBean data = new RentRecordReq.DataBean();
        data.setType("2");
        data.setValue(cardId);
        ArrayList<RentRecordReq.DataBean.QueryBean> query = new ArrayList<>();
        RentRecordReq.DataBean.QueryBean element = new RentRecordReq.DataBean.QueryBean();
        element.setQuery_type("1");
        RentRecordReq.DataBean.QueryBean.QueryWayBean queryWayBean = new RentRecordReq.DataBean.QueryBean.QueryWayBean();
        queryWayBean.setStart_time(start);
        queryWayBean.setEnd_time(end);
        element.setQuery_way(queryWayBean);
        query.add(element);
        data.setQuery(query);
        req.setData(data);
        client.sendString(JSONObject.toJSONString(req));
    }

//    private void initSpinner() {
//        spItem.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, queryType));
//        spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                RentRecordReq req = new RentRecordReq();
//                req.setSiteId("59");
//                req.setCmd("25");
//                req.setWay("1");
//                req.setSn("0");
//                RentRecordReq.DataBean data = new RentRecordReq.DataBean();
//                data.setType("2");
//                data.setValue(cardId);
//                switch (i) {
//                    case 0://借车时间范围
//                        ArrayList<RentRecordReq.DataBean.QueryBean> query = new ArrayList<>();
//                        RentRecordReq.DataBean.QueryBean element = new RentRecordReq.DataBean.QueryBean();
//                        element.setQuery_type("1");
//                        RentRecordReq.DataBean.QueryBean.QueryWayBean queryWayBean = new RentRecordReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean.setStart_time("2017-06-20 16:53:30");
//                        queryWayBean.setEnd_time("2017-07-10 17:53:30");
//                        element.setQuery_way(queryWayBean);
//                        query.add(element);
//                        data.setQuery(query);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                    case 1://还车时间范围
//                        ArrayList<RentRecordReq.DataBean.QueryBean> query1 = new ArrayList<>();
//                        RentRecordReq.DataBean.QueryBean element1 = new RentRecordReq.DataBean.QueryBean();
//                        element1.setQuery_type("2");
//                        RentRecordReq.DataBean.QueryBean.QueryWayBean queryWayBean1 = new RentRecordReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean1.setStart_time("2017-06-20 16:53:30");
//                        queryWayBean1.setEnd_time("2017-07-10 17:53:30");
//                        element1.setQuery_way(queryWayBean1);
//                        query1.add(element1);
//                        data.setQuery(query1);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                    case 2://站点ID 59
//                        ArrayList<RentRecordReq.DataBean.QueryBean> query2 = new ArrayList<>();
//                        RentRecordReq.DataBean.QueryBean element2 = new RentRecordReq.DataBean.QueryBean();
//                        element2.setQuery_type("4");
//                        RentRecordReq.DataBean.QueryBean.QueryWayBean queryWayBean2 = new RentRecordReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean2.setSite_id("59");
//                        element2.setQuery_way(queryWayBean2);
//                        query2.add(element2);
//                        data.setQuery(query2);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                    case 3://站点ID+锁桩ID 59 1
//                        ArrayList<RentRecordReq.DataBean.QueryBean> query3 = new ArrayList<>();
//                        RentRecordReq.DataBean.QueryBean element3 = new RentRecordReq.DataBean.QueryBean();
//                        element3.setQuery_type("8");
//                        RentRecordReq.DataBean.QueryBean.QueryWayBean queryWayBean3 = new RentRecordReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean3.setSite_id("59");
//                        queryWayBean3.setPile_id("1");
//                        element3.setQuery_way(queryWayBean3);
//                        query3.add(element3);
//                        data.setQuery(query3);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
//                        break;
//                    case 4://车辆ID 0D2A0F91
//                        ArrayList<RentRecordReq.DataBean.QueryBean> query4 = new ArrayList<>();
//                        RentRecordReq.DataBean.QueryBean element4 = new RentRecordReq.DataBean.QueryBean();
//                        element4.setQuery_type("10");
//                        RentRecordReq.DataBean.QueryBean.QueryWayBean queryWayBean4 = new RentRecordReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean4.setVehicle_id("0D2A0F91");
//                        element4.setQuery_way(queryWayBean4);
//                        query4.add(element4);
//                        data.setQuery(query4);
//                        req.setData(data);
//                        client.sendString(JSONObject.toJSONString(req));
////                        String reqJson25_10 = "{\"siteId\":\"59\"," +
////                                "\"cmd\":\"25\",\"way\":\"1\",\"sn\":\"0\"," +
////                                "\"data\":{\"type\":\"2\"," +
////                                "\"value\":\"2B0EF1E4\",\"query\":" +
////                                "[{\"query_type\":\"10\"," +
////                                "\"query_way\":{\"vehicle_id\":\"0D2A0F91\"}}]}}";
////                        client.sendString(reqJson25_10);
//                        break;
//                    case 5://扣费范围 0-10
//                        ArrayList<RentRecordReq.DataBean.QueryBean> query5 = new ArrayList<>();
//                        RentRecordReq.DataBean.QueryBean element5 = new RentRecordReq.DataBean.QueryBean();
//                        element5.setQuery_type("20");
//                        RentRecordReq.DataBean.QueryBean.QueryWayBean queryWayBean5 = new RentRecordReq.DataBean.QueryBean.QueryWayBean();
//                        queryWayBean5.setDeduction_down("0");
//                        queryWayBean5.setDeduction_up("10");
//                        element5.setQuery_way(queryWayBean5);
//                        query5.add(element5);
//                        data.setQuery(query5);
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

    @OnClick({R.id.start, R.id.end, R.id.btn_up, R.id.btn_back, R.id.btn_down,R.id.sure})
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
}
