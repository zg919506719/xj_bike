package com.xingjian.kav;

import android.os.Bundle;
import android.posapi.Conversion;
import android.posapi.PosApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private PosApi api;
    private TextView tv;
    private Button btn_borrow, btn_send, btn_stop, btn_start, btn_battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        btn_borrow = (Button) findViewById(R.id.btn_borrow);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_battery = (Button) findViewById(R.id.btn_battery);
        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.append("开锁0B" + "\n");
                byte[] mCmdd1 = Conversion.HexString2Bytes("01000B");
                api.canCmd(0, mCmdd1, mCmdd1.length);
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                获取锁桩状态
                SimpleDateFormat yyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
                String src1 = "010006" + yyMMddHHmmss.format(new Date());
                tv.append("获取锁桩状态06:   " + src1 + "\n");
                byte[] mCmdet = Conversion.HexString2Bytes(src1);
                api.canCmd(0, mCmdet, mCmdet.length);
            }
        });
//        btn_stop,btn_start,btn_battery
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.append("停用03" + "\n");
                //        response = pileId + "0003" + isOpen;00启用，01停用
                byte[] mCmdd1 = Conversion.HexString2Bytes("0100C0301");
                api.canCmd(0, mCmdd1, mCmdd1.length);
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.append("启用03" + "\n");
                byte[] mCmdd1 = Conversion.HexString2Bytes("01000300");
                api.canCmd(0, mCmdd1, mCmdd1.length);
            }
        });
        //response = pileId + "0005" + isOpen 00不充，01充
        btn_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.append("充电05" + "\n");
                byte[] mCmdd1 = Conversion.HexString2Bytes("01000501");
                api.canCmd(0, mCmdd1, mCmdd1.length);
            }
        });
        api = MyApp.posApi;
        deviceInit();
        setCan();
        api.canInit(4);
        String data = "case \"01\"://0x01 锁桩状态上报和时间同步"
                + "\n" + "case \"02\"://0x02 锁桩充电申请与关闭"
                + "\n" + "case \"03\"://0x03 锁桩停启用"
                + "\n" + " case \"04\"://0x04 更新锁桩程序"
                + "\n" + "  case \"05\"://0x05 站点下发充电许可"
                + "\n" + "  case \"06\"://0x06 获取锁桩状态"
                + "\n" + "  case \"0A\"://0x0A 锁桩还车请求"
                + "\n" + "  case \"0B\"://0x0B 站点下发开锁指令，获取推车结果" +
                "\n" + "  case \"0C\"://0x0C 开锁成功与否确认\n";
        tv.setText(data);
    }

    private void setCan() {
        api.setOnCanEventListener(new PosApi.OnCanEventListener() {
            @Override
            public void onInit(int state) {
                if (state == PosApi.COMM_STATUS_SUCCESS) {
                    tv.append("CAN 设置成功\n");
                } else {
                    tv.append("CAN 设置失败\n");
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
            public void onReceived(int serialNo, byte[] resp, int length) {
//                00成功，01失败
                String cmd = null;
                String mPileID;
                if (resp != null) {
                    cmd = Conversion.Bytes2HexString(resp);
                    tv.append("接收数据:" + cmd + "\n");
                }
                //1，2位为车辆编号
                //3，4位为位数
                //5，6为协议
                String pileId = cmd.substring(0, 2);
                String protocol = cmd.substring(4, 6);
                tv.append("车辆编号为：" + pileId + ",车辆请求为：" + protocol + "\n");
                switch (protocol) {
                    case "81"://0x01 请求时间 通了
                        //    01 0D8A  02 00 01 01 0000
//                            没有车辆信息
//                            01078A是协议，00锁桩类型，
//                              00锁桩状态，
//                              0000锁桩 故障原因
//                              00故障2
//                            车类型 81，车辆id 八位00000000，
//                        车状态6位
//                        后面两位01是车桩号
//                        010D8A020001010000005234E794 10
                        SimpleDateFormat yyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
                        String src1 = pileId + "00C1" + yyMMddHHmmss.format(new Date());
                        tv.append("传过去C1:   " + src1 + "\n");
                        byte[] mCmd0E = Conversion.HexString2Bytes(src1);
                        api.canCmd(0, mCmd0E, mCmd0E.length);
                        break;
                    case "82"://0x02 锁桩充电申请与关闭a``
//                        锁桩充电 02 0984 AD06BBA4车号 00车类型 00电量 01打开充电端口
                        String bikeId = cmd.substring(6, 14);
                        String data = cmd.substring(14, 18);
                        tv.append("充电车位" + bikeId + "车型电量为" + data + "\n");
                        String isOpen = cmd.substring(18, 20);
                        String response = null;
                        if (isOpen.equals("01")) {
                            response = pileId + "00C2" + isOpen;
                        } else if (isOpen.equals("00")) {
                            response = pileId + "00C2" + isOpen;
                        }
                        tv.append("充电回复" + response + "\n");
                        byte[] mCmdC4 = Conversion.HexString2Bytes(response);
                        api.canCmd(0, mCmdC4, mCmdC4.length);
                        break;
                    case "83"://0x03 锁桩停启用结果
                        String isStart = cmd.substring(6, 8);
//                            启用结果，00成功，01失败
                        tv.append("锁桩停启用结果" + isStart + "\n");
                        break;
                    case "84"://0x04 更新锁桩程序
                        break;
                    case "85"://站点下发充电回复
//                        当前充电状态00不充，01充
                        //云端借车回复 01029100 00表示成功，01表示失败
                        String state = cmd.substring(6, 8);
                        tv.append("充电回复" + state + "\n");
                        break;
                    case "86":
                        tv.append("获取锁装状态" + "\n");
                        break;
                    case "8A":
//                        01088D 01    5234E794   82
                        tv.append("还车结果为" + "\n");
                        String src5 = pileId + "00CA" + "0001";
                        tv.append("还车回复的指令为" + src5 + "\n");
                        byte[] mCmd0D = Conversion.HexString2Bytes(src5);
                        api.canCmd(0, mCmd0D, mCmd0D.length);
                        break;
                    case "8B":
                        String state2 = cmd.substring(6, 8);
                        tv.append("推车结果" + state2 + "\n");
                        break;
                    case "8C":
//                        开锁成功与否确认，开锁成功结果00成功，01失败
                        String state3 = cmd.substring(6, 8);
                        tv.append("开锁结果" + state3 + "\n");
                        break;
                }
            }

            @Override
            public void onClose(int state) {
                if (state == PosApi.COMM_STATUS_SUCCESS) {
                    tv.append("CAN 关闭成功\n");
                } else {
                    tv.append("CAN 关闭失败\n");
                }
            }
        });
    }


    private void deviceInit() {
        //        设备初始化
        api.setOnComEventListener(new PosApi.OnCommEventListener() {
            @Override
            public void onCommState(int cmdFlag, int state, byte[] resp, int respLen) {
                switch (cmdFlag) {
                    case PosApi.POS_INIT:
                        if (state == PosApi.COMM_STATUS_SUCCESS) {
                            MyApp.showToast("设备初始化成功");
                        } else {
                            api.initPosDev("ima3511");
                        }
//                        if (state == PosApi.COMM_STATUS_FAILED) {
//                            MyApp.showToast("设备初始化失败，请重启");
//                        }
                        break;
                }
            }
        });
        api.initPosDev("ima3511");

    }
}
