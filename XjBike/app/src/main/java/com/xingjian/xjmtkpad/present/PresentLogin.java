package com.xingjian.xjmtkpad.present;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
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
import com.xingjian.xjmtkpad.beanrequest.RentReq;
import com.xingjian.xjmtkpad.beanrequest.TimeReq;
import com.xingjian.xjmtkpad.beanresponse.NameRes;
import com.xingjian.xjmtkpad.beanresponse.RentResHour;
import com.xingjian.xjmtkpad.beanresponse.RentRes;
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
    private TextView tv_time, tv_temp, tv_name, tv_location, tv_show, rentDay, rentHour;
    private static String TAG = "haha";
    private ProgressBar progressBar;
    private Dialog dialog_wel;

    public PresentLogin(InterLogin interLogin) {
        this.interLogin = interLogin;
        context = interLogin.getContext();
        mPosApi = interLogin.getApi();
        client = MyApp.Client;
        tv_time = interLogin.getTime();
        tv_location = interLogin.getLocation();
        tv_name = interLogin.getDevice();
        tv_temp = interLogin.getTemp();
        rentDay = interLogin.getRentDay();
        rentHour = interLogin.getRentHour();
    }

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
                    //获取时间
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
                    //获取站点名称
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
                    sendEmptyMessage(5);
                    break;
                case 5:
//                    M1初始化
                    setM1();
                    sendEmptyMessage(6);
                    break;
                case 6:
                    //注释
//                    mode 计费模式（1-时租、2-日租）
//                    cost 费用（日租：单位角；时租：阶梯次数，起始1小时-结束1小时-费用）
//                    ladder_num 阶梯次数
//                    start 起始时间
//                    end 结束时间
                    RentReq req_rent = new RentReq();
                    req_rent.setSiteId("59");
                    req_rent.setCmd("0b");
                    req_rent.setWay("1");
                    req_rent.setSn("0");
                    RentReq.DataBean data1 = new RentReq.DataBean();
                    data1.setAddress("济南");
                    data1.setMode("1");
                    req_rent.setData(data1);
                    String message1 = JSON.toJSONString(req_rent);
                    client.sendString(message1);
                    sendEmptyMessage(7);
//                    日租信息
                    break;
                case 7:
                    RentReq req_rent1 = new RentReq();
                    req_rent1.setSiteId("59");
                    req_rent1.setCmd("0b");
                    req_rent1.setWay("1");
                    req_rent1.setSn("0");
                    RentReq.DataBean data2 = new RentReq.DataBean();
                    data2.setAddress("济南");
                    data2.setMode("2");
                    req_rent1.setData(data2);
                    String message2 = JSON.toJSONString(req_rent1);
                    client.sendString(message2);
//                    时租信息
                    break;
            }
        }
    };


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
                if (message.contains("\"cmd\":\"03\"")) {
                    NameRes nameRes = JSONObject.parseObject(message, NameRes.class);
                    String name = nameRes.getData().getSite_name();
                    tv_name.setText(name);
                } else if (message.contains("\"cmd\":\"05\"")) {
                    TimeRes timeRes = JSONObject.parseObject(message, TimeRes.class);
                    String time = timeRes.getData().getTime();
                    tv_time.setText(time);
                } else if (message.contains("\"cmd\":\"0b\"")) {
                    if (message.contains("\"mode\":\"1\"")) {
                        RentRes rentRes = JSONObject.parseObject(message, RentRes.class);
                        RentRes.DataBean data = rentRes.getData();
                        String text = "日租为："+(Double.parseDouble(data.getCost()) / 10) + "元";
                        rentDay.setText(text);
                    } else if (message.contains("\"mode\":\"2\"")) {
                        RentResHour rentResHour = JSONObject.parseObject(message, RentResHour.class);
                        RentResHour.DataBean data = rentResHour.getData();
                        String text = "时租为："+(Double.parseDouble(data.getCost()) / 10) + "元";
                        rentHour.setText(text);
                    }
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
        tv_show.setText("正在获取温度");
        progressBar.setProgress(2);
        handler.sendEmptyMessage(0);
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
                    char[] chars = uid.toCharArray();
                    StringBuffer sb = new StringBuffer();
                    if (chars.length == 8) {
                        sb.append(chars[6]);
                        sb.append(chars[7]);
                        sb.append(chars[4]);
                        sb.append(chars[5]);
                        sb.append(chars[2]);
                        sb.append(chars[3]);
                        sb.append(chars[0]);
                        sb.append(chars[1]);
                    }
                    String test = "{\n" +
                            "    \"siteId\": \"59\",\n" +
                            "    \"cmd\": \"d3\",\n" +
                            "    \"way\": \"1\",\n" +
                            "    \"sn\": \"0\",\n" +
                            "    \"address\": \"济南\",\n" +
                            "    \"data\": {\n" +
                            "        \"user_cardId\": " + sb.toString() + "\n" +
                            "    }\n" +
                            "}";
                    client.sendString(test);
                    SharedPreferences.Editor editor = MyApp.getEditor();
                    editor.putString("cardId", sb.toString());
                    editor.commit();
                    context.startActivity(new Intent(context, UserDataActivity.class));
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

    }





}
