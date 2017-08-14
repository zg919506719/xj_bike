package com.xingjian.xjmtkpad.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.StaffCheckReq;
import com.xingjian.xjmtkpad.beanresponse.StaffCheckRes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class StaffAttendanceActivity extends AppCompatActivity {
    @BindView(R.id.tv_admincardnumber)
    TextView tvAdmincardnumber;
    private String staff;
    private SocketClient client;
    private static String TAG = "haha";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance);
        ButterKnife.bind(this);
        MyApp.addActivity(this);
        initData();
    }

    private void initData() {
        client = MyApp.Client;
        staff = getIntent().getExtras().getString("staff");
        tvAdmincardnumber.setText(staff);
        socketRegister();
        Intent intent = new Intent(this, StaffRecordActivity.class);
        intent.putExtra("staff", staff);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApp.exit();
    }

    @OnClick({R.id.btn_home, R.id.btn_checkin, R.id.btn_checkout, R.id.btn_checkrecords})
    public void onClick(View view) {
        StaffCheckReq req = new StaffCheckReq();
        req.setSiteId("59");
        req.setCmd("27");
        req.setWay("1");
        req.setSn("0");
        switch (view.getId()) {
            case R.id.btn_home:
                MyApp.exit();
                break;
            case R.id.btn_checkin:// 签到
//                type 类型（1-员工刷卡修改，2-员工卡ID输入修改，3-员工身份证输入修改）
//                value 员工卡内部ID、员工卡ID、身份证号
//                update_type 上报类型（1-签到，2-签退）
                StaffCheckReq.DataBean dataBean = new StaffCheckReq.DataBean();
                dataBean.setType("2");
                dataBean.setValue(staff);
                dataBean.setUpdate_type("1");
                req.setData(dataBean);
                String message = JSONObject.toJSONString(req);
                Log.i(TAG, message);
                client.sendString(message);
                break;
            case R.id.btn_checkout:// 签退
                StaffCheckReq.DataBean dataBean1 = new StaffCheckReq.DataBean();
                dataBean1.setType("2");
                dataBean1.setValue(staff);
                dataBean1.setUpdate_type("2");
                req.setData(dataBean1);
                String message1 = JSONObject.toJSONString(req);
                Log.i(TAG, message1);
                client.sendString(message1);
                break;
            case R.id.btn_checkrecords:// 考勤记录
                Intent intent = new Intent(this, StaffRecordActivity.class);
                intent.putExtra("staff", staff);
                startActivity(intent);
                break;
        }
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
//                dialog.dismiss();
                String message = responsePacket.getMessage();
                Log.i(TAG, message);
                if (message.contains("\"cmd\":\"27\"")) {
                    //员工签到签退
                    StaffCheckRes res = JSONObject.parseObject(message, StaffCheckRes.class);
                    StaffCheckRes.DataBean dataBean = res.getData();
                    String staffCardId = dataBean.getStaff_cardId();//员工卡ID
                    String operateType = dataBean.getOperate_type();//操作类型（1-签到，2-签退）
                    String operateResult = dataBean.getOperate_result();//结果（1-成功，0-失败）
                    if (operateType.equals("1") && operateResult.equals("1")) {//签到成功
                        MyApp.showToast("签到成功");
                    } else if (operateType.equals("1") && operateResult.equals("0")) {//签到失败
                        MyApp.showToast("签到失败");
                    } else if (operateType.equals("2") && operateResult.equals("1")) {//签退成功
                        MyApp.showToast("签退成功");
                    } else if (operateType.equals("2") && operateResult.equals("0")) {//签退失败
                        MyApp.showToast("签退失败");
                    }
                }
            }
        });
    }
}
