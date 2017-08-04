package com.xingjian.xjmtkpad.present;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.posapi.Conversion;
import android.posapi.PosApi;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.activity.UserDataActivity;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.LoginReq;
import com.xingjian.xjmtkpad.beanrequest.NameReq;
import com.xingjian.xjmtkpad.beanrequest.TimeReq;
import com.xingjian.xjmtkpad.beanresponse.NameRes;
import com.xingjian.xjmtkpad.beanresponse.TimeRes;
import com.xingjian.xjmtkpad.inter.InterLogin;
import com.xingjian.xjmtkpad.utils.StringDialog;

/**
 * Created by thinkpad on 2017/8/2.
 */

public class PresentLogin {
    private Context context;
    private InterLogin interLogin;
    private PosApi mPosApi;
    private SocketClient client;
    private TextView tv_time, tv_temp, tv_name, tv_location, tv_show;
    private static String TAG = "haha";
    private ProgressBar progressBar;
    private Dialog dialog_wel;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //获取温度
                    mPosApi.getTemperatureHumidity();
                    break;
                case 1:
                    dialog_wel.dismiss();
                    handler.sendEmptyMessage(4);
                    break;
                case 2:
                    //can初始化
                    mPosApi.canInit(4);
                    break;
                case 3:
//                    String message = "{\"siteId\":\"59\",\"cmd\":\"05\",\"way\":\"1\",\"sn\":\"0\",\"data\":{}";
                    TimeReq req = new TimeReq();
                    req.setSiteId("59");
                    req.setCmd("05");
                    req.setWay("1");
                    req.setSn("0");
                    req.setData(new TimeReq.DataBean());
                    String s = JSON.toJSONString(req);
                    client.sendString(s);
                    break;
                case 4:
//                    String message="{\"siteId\":\"59\",\"cmd\":\"03\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\"}}";
                    NameReq req_name = new NameReq();
                    req_name.setSiteId("59");
                    req_name.setCmd("03");
                    req_name.setWay("1");
                    req_name.setSn("0");
                    NameReq.DataBean data = new NameReq.DataBean();
                    data.setAddress("济南");
                    req_name.setData(data);
                    String message = JSON.toJSONString(req_name);
                    client.sendString(message);
                    break;

            }
        }
    };

    public PresentLogin(InterLogin interLogin) {
        this.interLogin = interLogin;
        context = interLogin.getContext();
        mPosApi = interLogin.getApi();
        client = MyApp.Client;
        tv_time = interLogin.getTime();
        tv_location = interLogin.getLocation();
        tv_name = interLogin.getDevice();
        tv_temp = interLogin.getTemp();
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
//                    login(username1, password1);
                    context.startActivity(new Intent(context, UserDataActivity.class));
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

    //账号密码登录
    public void login(final String username, final String password) {
        LoginReq req = new LoginReq();
        req.setSiteId("59");
        req.setCmd("d2");
        req.setWay("1");
        req.setSn("0");
        req.setAddress("北京");
        LoginReq.DataBean data = new LoginReq.DataBean();
        data.setUsername(username);
        data.setPassword(password);
        req.setData(data);
        String s = JSON.toJSONString(req);
        client.sendString(s);
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
                //获取服务器时间
                if (message.contains("\"cmd\":\"03\"")){
                    NameRes nameRes = JSONObject.parseObject(message, NameRes.class);
                    String name = nameRes.getData().getSite_name();
                    tv_name.setText(name);
                }else  if (message.contains("\"cmd\":\"05\"")){
                    TimeRes timeRes = JSONObject.parseObject(message, TimeRes.class);
                    String time = timeRes.getData().getTime();
                    tv_time.setText(time);
                }
            }
        });
    }

    public void initView() {
        dialog_wel = new Dialog(context, R.style.LodingDialog);
        View view = View.inflate(context, R.layout.progress_dialog, null);
        tv_show = (TextView) view.findViewById(R.id.tv_show);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        dialog_wel.setContentView(view);
        dialog_wel.show();
        //设备初始化
        deviceInit();
        //温度检测
        setTemp();
        socketRegister();
    }

    private void setTemp() {
        mPosApi.setOnTDSensorChangeListener(new PosApi.OnTDSensorChangeListener() {
            @Override
            public void onChange(float tem, float hum) {
                StringBuffer sb = new StringBuffer();
                sb.append("温度为：");
                sb.append(tem);
                sb.append("°C,湿度为：");
                sb.append(hum);
                sb.append("%");
                tv_temp.setText(sb.toString());
                if (tem > 30) {
                    //开风扇
                    mPosApi.gpioControl((byte) 0x00, 0, 1);
                } else {
                    //关风扇
                    mPosApi.gpioControl((byte) 0x00, 0, 0);
                }
                handler.sendEmptyMessage(1);
            }
        });

    }

    private void setM1() {
        //扫码结果
        mPosApi.setOnM1CardEventListener(new PosApi.OnM1CardEventListener() {
            @Override
            public void onSearch(int state, String uid) {
                if (state == PosApi.COMM_STATUS_SUCCESS) {
//                    respons.append("M1 寻卡成功\n");
//                    respons.append("Card UID:"+uid+"\n");
                } else {
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
        //        mApi.m1Search(500);
//        进行扫码操作
    }

    private void setCan() {
        mPosApi.setOnCanEventListener(new PosApi.OnCanEventListener() {
            @Override
            public void onInit(int state) {
                if (state == PosApi.COMM_STATUS_SUCCESS) {
                    tv_show.setText("CAN 设置成功");
                    progressBar.setProgress(3);
                    handler.sendEmptyMessage(2);
                } else {
                    tv_show.setText("CAN 设置失败");
                    progressBar.setProgress(3);
                }
            }

            @Override
            public void onCmd(int state) {
                if (state == PosApi.COMM_STATUS_SUCCESS) {
//                    respons.append("CAN 发送成功\n");
                } else {
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
            }

            @Override
            public void onClose(int state) {
                if (state == PosApi.COMM_STATUS_SUCCESS) {
//                    respons.append("CAN 关闭成功\n");
                } else {
//                    respons.append("CAN 关闭失败\n");
                }
            }
        });
    }

    private void deviceInit() {
        //        设备初始化
        mPosApi.setOnComEventListener(new PosApi.OnCommEventListener() {
            @Override
            public void onCommState(int cmdFlag, int state, byte[] resp, int respLen) {
                switch (cmdFlag) {
                    case PosApi.POS_INIT:
                        if (state == PosApi.COMM_STATUS_SUCCESS) {
                            tv_show.setText("正在获取温度");
                            progressBar.setProgress(1);
                            handler.sendEmptyMessage(0);
                        } else if (state == PosApi.COMM_STATUS_FAILED) {
                            tv_show.setText("设备初始化失败，请重启");
                            progressBar.setProgress(1);
                        }
                        break;
                }
            }
        });
    }


}
