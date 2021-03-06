package com.xingjian.xjmtkpad.present;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.posapi.PosApi;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketPacket;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.activity.StaffAttendanceActivity;
import com.xingjian.xjmtkpad.activity.UserDataActivity;
import com.xingjian.xjmtkpad.base.Constant;
import com.xingjian.xjmtkpad.base.MyApp;
import com.xingjian.xjmtkpad.beanrequest.CardLLoginReq;
import com.xingjian.xjmtkpad.beanrequest.LoginReq;
import com.xingjian.xjmtkpad.beanrequest.NameReq;
import com.xingjian.xjmtkpad.beanrequest.TimeReq;
import com.xingjian.xjmtkpad.beanresponse.CardLoginRes;
import com.xingjian.xjmtkpad.beanresponse.LoginRes;
import com.xingjian.xjmtkpad.beanresponse.NameRes;
import com.xingjian.xjmtkpad.beanresponse.TimeRes;
import com.xingjian.xjmtkpad.inter.InterLogin;
import com.xingjian.xjmtkpad.service.ServiceDevice;
import com.xingjian.xjmtkpad.utils.StringDialog;
import com.xingjian.xjmtkpad.utils.WeakHandler;
import com.xingjian.xjmtkpad.view.MyVideoView;

/**
 * Created by thinkpad on 2017/8/2.
 */

public class PresentLogin {
    private Context context;
    private InterLogin interLogin;
    private PosApi mPosApi;
    private SocketClient client;
    private TextView tv_time, tv_temp, tv_name, tv_location, tv_humidity;
    private static String TAG = "haha";
    private String cardId;
    private WeakHandler handler = new WeakHandler();
    private long time = 10000;
    private boolean isFull;
    public Dialog videoDialog;

    public PresentLogin(InterLogin interLogin) {
        this.interLogin = interLogin;
        context = interLogin.getContext();
        mPosApi = interLogin.getApi();
        client = MyApp.Client;
        tv_time = interLogin.getTime();
        tv_location = interLogin.getLocation();
        tv_name = interLogin.getDevice();
        tv_temp = interLogin.getTemp();
        tv_humidity = interLogin.getHumidity();
    }


    public void initView() {
        SharedPreferences.Editor editor = MyApp.getEditor();
        editor.putBoolean("isLogin", false);
        editor.commit();
        //设备初始化
        deviceInit();
        //温度监听
        setTemp();
//        M1初始化
        setM1();
        socketRegister();
        setSiteName();
        setTime();
        context.startService(new Intent(context, ServiceDevice.class));
//        setwelcomeDialog();
//        login("18045167739", "111111a");
        initDialog();
    }

    private void initDialog() {
        videoDialog = new Dialog(context, R.style.Dialog_Fullscreen);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_video, null);
        MyVideoView videoView = (MyVideoView) inflate.findViewById(R.id.dialog_video);
        FrameLayout frame = (FrameLayout) inflate.findViewById(R.id.frame);
        SeekBar seekBar = (SeekBar) inflate.findViewById(R.id.seek);
        videoView.setVideoPath("android.resource://com.xingjian.xjmtkpad/" + R.raw.jinchen);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoDialog.isShowing()) {
                    videoDialog.dismiss();
                    isFull = false;
                    startTask();
                }
            }
        });
        controlVoice(seekBar);
        videoDialog.setContentView(inflate);
    }


    public void showNoCardDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        Button login = (Button) view.findViewById(R.id.btn_login);
        final Button cancle = (Button) view.findViewById(R.id.btn_cancel);
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: login");
                if (show.isShowing()) {
                    show.dismiss();
                    videoDialog.show();
                }
            }
        }, Constant.AUTO_SHOW_TIME);
    }

    //账号密码登录
    public void login(final String username, final String password) {
        LoginReq req = new LoginReq();
        req.setSiteId("59");
        req.setCmd("d2");
        req.setWay("1");
        req.setSn("0");
        req.setAddress("济南");
        LoginReq.DataBean data = new LoginReq.DataBean();
        data.setCode(username);
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
                } else if (message.contains("\"cmd\":\"d3\"")) {
                    CardLoginRes cardLoginRes = JSONObject.parseObject(message, CardLoginRes.class);
                    CardLoginRes.DataBean data = cardLoginRes.getData();
                    String result = data.getCode();
//                    {"code":"1000","message":"success","id":"000001","cardType":"staff"员工,user，用户}}
//                    "code":"1004","message":"error"
                    if (result.equals("1000")) {
                        boolean isLogin = MyApp.getPreference().getBoolean("isLogin", false);
                        if (!isLogin) {
                            SharedPreferences.Editor editor = MyApp.getEditor();
                            editor.putString("cardId", data.getId());
                            editor.putBoolean("isLogin", true);
                            editor.commit();
                            if (data.getCardType().equals("staff")) {
                                Intent intent = new Intent(context, StaffAttendanceActivity.class);
                                intent.putExtra("staff", data.getId());
                                context.startActivity(intent);
                            } else if (data.getCardType().equals("user")) {
                                context.startActivity(new Intent(context, UserDataActivity.class));
                            }
                        }
                    } else {
                        MyApp.showToast("卡的身份有误，登录失败");
                    }

                } else if (message.contains("\"cmd\":\"d2\"")) {
                    LoginRes loginRes = JSONObject.parseObject(message, LoginRes.class);
                    LoginRes.DataBean data = loginRes.getData();
                    if (data.getCode().equals("1000")) {
                        boolean isLogin = MyApp.getPreference().getBoolean("isLogin", false);
                        if (!isLogin) {
                            SharedPreferences.Editor editor = MyApp.getEditor();
                            editor.putString("cardId", data.getIdCard());
                            editor.putBoolean("isLogin", true);
                            editor.commit();
                            context.startActivity(new Intent(context, UserDataActivity.class));
//                                Intent intent = new Intent(context, StaffDataActivity.class);
//                                intent.putExtra("staff", data.getIdCard());
//                                context.startActivity(intent);
                        }
                    } else {
                        MyApp.showToast("卡的身份有误，登录失败");
                    }
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
                            MyApp.showToast("设备初始化成功");
                        } else {
                            mPosApi.initPosDev("ima3511");
                        }
//                        if (state == PosApi.COMM_STATUS_FAILED) {
//                            MyApp.showToast("设备初始化失败，请重启");
//                        }
                        break;
                }
            }
        });
        mPosApi.initPosDev("ima3511");

    }

    private void setTemp() {
        mPosApi.setOnTDSensorChangeListener(new PosApi.OnTDSensorChangeListener() {
            @Override
            public void onChange(float tem, float hum) {
//                sb.append("温度为：");
//                sb.append("°C,湿度为：");
                tv_temp.setText("温度:" + tem + "°C");
                tv_humidity.setText("湿度:" + hum + "%");
                if (tem > Constant.TEM | hum > Constant.HUM) {
                    //开风扇
                    mPosApi.gpioControl((byte) 0x00, 0, 1);
                } else {
                    //关风扇
                    mPosApi.gpioControl((byte) 0x00, 0, 0);
                }

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
                    Log.i(TAG, "onSearch: " + uid);
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
                    cardId = sb.toString();
                    CardLLoginReq req = new CardLLoginReq();
                    req.setSiteId("59");
                    req.setCmd("d3");
                    req.setWay("1");
                    req.setSn("0");
                    req.setAddress("上海");
                    CardLLoginReq.DataBean bean = new CardLLoginReq.DataBean();
                    bean.setUser_cardId(cardId);
//                    员工卡测试
//                    bean.setUser_cardId("000001");
                    req.setData(bean);
                    SocketPacket socketPacket = client.sendString(JSONObject.toJSONString(req));
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

    private void setTime() {
        TimeReq req = new TimeReq();
        req.setSiteId("59");
        req.setCmd("05");
        req.setWay("1");
        req.setSn("0");
        req.setData(new TimeReq.DataBean());
        String s = JSON.toJSONString(req);
        client.sendString(s);
    }

    private void setSiteName() {
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
    }


    //    控制系统媒体音量
    private void controlVoice(final SeekBar seek) {
        final AudioManager audiomanage = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audiomanage.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seek.setMax(maxVolume);   //拖动条最高值与系统最大声匹配
        int currentVolume = audiomanage.getStreamVolume(AudioManager.STREAM_MUSIC);
        //获取当前值
        seek.setProgress(currentVolume);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() //调音监听器
        {
            public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
                audiomanage.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                int streamVolume1 = audiomanage.getStreamVolume(AudioManager.STREAM_MUSIC);
//                currentVolume = streamVolume1;  //获取当前值
                seek.setProgress(streamVolume1);
//                mVolume.setText(currentVolume*100/maxVolume + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub


            }
        });

    }


    public void startTask() {
//        stopTask
        handler.removeCallbacks(task);
        if (!isFull) {
            handler.postDelayed(task, time);
        }
//        每过1秒子线程运行
    }

    public void finishTask() {
        handler.removeCallbacks(task);
        if (videoDialog.isShowing()) {
            videoDialog.dismiss();
        }
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "run: ");
            isFull = true;
            if (!videoDialog.isShowing()) {
                videoDialog.show();
            }
        }
    };


    private void setwelcomeDialog() {
        final Dialog dialog = new Dialog(context, R.style.LodingDialog);
        View view = View.inflate(context, R.layout.progress_dialog, null);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.pb_start);
        dialog.setContentView(view);
        dialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (progressBar.getProgress() < 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(progressBar.getProgress() + 1);
                    if (progressBar.getProgress() == 100) {
                        dialog.dismiss();
                    }
                }
            }
        }.start();

    }
}
