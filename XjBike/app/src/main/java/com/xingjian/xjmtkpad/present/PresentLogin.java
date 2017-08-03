package com.xingjian.xjmtkpad.present;

import android.app.Dialog;
import android.content.Context;
import android.posapi.Conversion;
import android.posapi.PosApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.inter.InterLogin;
import com.xingjian.xjmtkpad.utils.StringDialog;

/**
 * Created by thinkpad on 2017/8/2.
 */

public class PresentLogin {
    private Context context;
    private InterLogin interLogin;
    private PosApi mPosApi;
    public PresentLogin(InterLogin interLogin) {
        this.interLogin = interLogin;
        context = interLogin.getContext();
    }

    public void showNoCardDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        Button login = (Button) view.findViewById(R.id.btn_login);
        Button cancle = (Button) view.findViewById(R.id.btn_cancel);
        final EditText username = (EditText) view.findViewById(R.id.et_username);
        final EditText password = (EditText) view.findViewById(R.id.et_password);
        dialog.setView(view);
        final AlertDialog show = dialog.show();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                if (username1.isEmpty()) {
                    new StringDialog().showString(context, "请输入用户");
                } else if (password1.isEmpty()) {
                    new StringDialog().showString(context, "请输入密码");
                } else {
                    login(username1, password1);
                }
                show.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });
    }

    private void login(String username, String password) {

    }

    public void initView() {
        final Dialog dialog = new Dialog(context, R.style.LodingDialog);
        View view = View.inflate(context, R.layout.progress_dialog, null);
        final TextView tv = (TextView) view.findViewById(R.id.tv_show);
        final TextView tv_can = (TextView) view.findViewById(R.id.tv_show1);
        final TextView tv_rec = (TextView) view.findViewById(R.id.tv_show2);
        dialog.setContentView(view);
        dialog.show();
        mPosApi= MyApp.posApi;
//        设备初始化
        mPosApi.setOnComEventListener(new PosApi.OnCommEventListener() {
            @Override
            public void onCommState(int cmdFlag, int state, byte[] resp, int respLen) {
                switch(cmdFlag){
                    case PosApi.POS_INIT:
                        if(state==PosApi.COMM_STATUS_SUCCESS){
                            tv.setText("设备初始化成功");
                        }else if(state==PosApi.COMM_STATUS_FAILED){
                            tv.setText("设备初始化失败，请重启");
                        }
                        break;
                }
            }
        });
//温度检测
        mPosApi.setOnTDSensorChangeListener(new PosApi.OnTDSensorChangeListener() {
            @Override
            public void onChange(float tem, float hum) {
                StringBuffer sb = new StringBuffer();
                sb.append("温度为：");
                sb.append(tem);
                sb.append("°C,湿度为：");
                sb.append(hum);
                sb.append("%");
                if (tem>30) {
                    //开风扇
                    mPosApi.gpioControl((byte) 0x00, 0, 0);
                }else {
                    //关风扇
                    mPosApi.gpioControl((byte) 0x00, 0, 1);
                }
            }
        });
//扫码结果
        mPosApi.setOnM1CardEventListener(new PosApi.OnM1CardEventListener() {
            @Override
            public void onSearch(int state, String uid) {
                if(state == PosApi.COMM_STATUS_SUCCESS){
//                    respons.append("M1 寻卡成功\n");
//                    respons.append("Card UID:"+uid+"\n");
                }else{
//                    respons.append("M1 寻卡失败\n");
                }
            }

            @Override
            public void onAuth(int i) {

            }

            @Override
            public void onRead(int i, byte[] bytes, int i1) {

            }

            @Override
            public void onWrite(int i) {

            }

            @Override
            public void onExit(int i) {

            }
        });
//can初始化
        mPosApi.canInit(4);
        mPosApi.setOnCanEventListener(new PosApi.OnCanEventListener() {
            @Override
            public void onInit(int state) {
                if(state == PosApi.COMM_STATUS_SUCCESS){
                    tv_can.setText("CAN 设置成功\n");
                }else{
                   tv_can.setText("CAN 设置失败\n");
                }
            }

            @Override
            public void onCmd(int state) {
                if(state == PosApi.COMM_STATUS_SUCCESS){
//                    respons.append("CAN 发送成功\n");
                }else{
//                    respons.append("CAN 发送失败\n");
                }
            }

            @Override
            public void onReceived(int i, byte[] resp, int i1) {
//                respons.append("接收数据:"+"\n");
//                if(resp!=null){
//                    //psam 执行成功
//                    respons.append("接收数据:"+ Conversion.Bytes2HexString(resp)+"\n");
//                }
                tv_rec.setText("接收数据:"+ Conversion.Bytes2HexString(resp)+"\n");
            }

            @Override
            public void onClose(int state) {
                if(state == PosApi.COMM_STATUS_SUCCESS){
//                    respons.append("CAN 关闭成功\n");
                }else{
//                    respons.append("CAN 关闭失败\n");
                }
            }
        });
    }
}
