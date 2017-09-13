package com.xingjian.kav;

import android.posapi.Conversion;
import android.posapi.PosApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private PosApi api;
    private TextView tv;
    private Button btn_borrow, btn_send;
    private EditText etSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        btn_borrow = (Button) findViewById(R.id.btn_borrow);
        btn_send = (Button) findViewById(R.id.btn_send);
        etSend = (EditText) findViewById(R.id.et_send);
        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.append("云端开锁11:   " + "01001101" + "\n");
                byte[] mCmdd1 = Conversion.HexString2Bytes("01001101");
                api.canCmd(0, mCmdd1, mCmdd1.length);
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = etSend.getText().toString();
                tv.append("自定义输入的值为:   " + s + "\n");
                byte[] mCmdet = Conversion.HexString2Bytes(s);
                api.canCmd(0, mCmdet, mCmdet.length);
            }
        });
        api = MyApp.posApi;
        deviceInit();
        setCan();
        api.canInit(4);
        String data = "  case \"81\"://0x01 锁桩ID编号请求"
                + "\n" + "case \"8E\"://0x0E 请求时间"
                + "\n" + "case \"83\"://0x03 锁桩上报方式请求"
                + "\n" + " case \"44\"://0x04 锁桩上报方式设置"
                + "\n" + " case \"85\"://0x05 锁桩工作模式请求"
                + "\n" + " case \"87\"://0x07 站点id，发送的是站点id信息"
                + "\n" + "case \"8A\"://0x0A 锁桩车辆状态"
                + "\n" + "case \"4C\"://0x0C 远程应急车辆锁定和解锁"
                + "\n" + "case \"88\"://0x08 刷卡借车 cmd41"
                + "\n" + " case \"8D\"://0x0D 还车未刷卡 cmd43"
                + "\n" + "  case \"89\"://0x09 刷卡还车 cmd42";
        tv.setText(data);
    }

    private void setCan() {
        api.setOnCanEventListener(new PosApi.OnCanEventListener() {
            @Override
            public void onInit(int state) {
                if (state == PosApi.COMM_STATUS_SUCCESS) {
                    tv.append("CAN 设置成功\n");
                } else {
                    tv.append("CAN 设置失败");
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
                String cmd = null;
                String mPileID;
                if (resp != null) {
                    //psam 执行成功
                    cmd = Conversion.Bytes2HexString(resp);
                    tv.append("接收数据:" + cmd + "\n");
                }
//                01 09 81 54 170315010203  04
                //01格式固定，表示发送方向，
                // 0981判断类型，protocol
                //6-8位以后就是校验位，
                // 9-倒数两位 是数据域  700100000代表默认时间，
                //最后的两位00也是校验位
                String protocol = cmd.substring(4, 6);
                tv.append("车辆请求为：" + protocol + "\n");
//                5，6
                switch (protocol) {
                    case "81"://0x01 锁桩ID编号请求 通了
//                        01方向，03*2表示位数，81协议，54校验位，00还不知道
                        //01命名桩号
                        String src = "0103C15400" + "01";
                        byte[] mCmd0B = Conversion.HexString2Bytes(src);
                        tv.append("传过去81:   " + src + "\n");
                        api.canCmd(0, mCmd0B, mCmd0B.length);
                        break;

                    case "8E"://0x0E 请求时间 通了
                        SimpleDateFormat yyMMddHHmmss = new SimpleDateFormat("yyMMddHHmmss");
                        String src1 = "0100CE" + yyMMddHHmmss.format(new Date());
                        tv.append("传过去8E:   " + src1 + "\n");
                        byte[] mCmd0E = Conversion.HexString2Bytes(src1);
                        api.canCmd(0, mCmd0E, mCmd0E.length);
                        break;
//
                    case "83"://0x03 锁桩上报方式请求 通了
//                        mSocketClient.sendString(new JSONObject(reqJson.reqStr07).toString());//设备状态上报模式请求
//车桩编号 acmd=07对应83 返回 00C3     周期和变化01 02 变化就是车辆状态有变化 后面有周期性上报间隔时间10,15这样
                        byte[] mCmd = Conversion.HexString2Bytes("0100C302");
                        tv.append("云端回复83:   " + "0100C302" + "\n");
                        api.canCmd(0, mCmd, mCmd.length);
                        break;

                    case "85"://0x05 锁桩工作模式请求 通了
//                        01是处于在线状态02是离线状态
                        String src4 = "0100C501";
                        byte[] mCmd05 = Conversion.HexString2Bytes(src4);
                        api.canCmd(0, mCmd05, mCmd05.length);
                        tv.append("传过去85:    " + src4 + "\n");
                        break;
//
                    case "87"://0x07 站点id，发送的是站点id信息 通了
                        //前两个站点方向 00 Y/N C7协议 00校验位 59站点信息
                        byte[] mCmd07 = Conversion.HexString2Bytes("0100C70059");
                        tv.append("传过去的87:    " + "0100C70059" + "\n");
                        api.canCmd(0, mCmd07, mCmd07.length);
                        break;
//
                    case "8A"://0x0A 锁桩车辆状态上报
//                        01 078A  02 00 01 01 0000
//                            没有车辆信息
//                            01078A是协议，02上报模式
//                            00锁桩类型，01锁id，01状态，00 故障1 00故障2
//                            后面的
//                            车类型 81，车辆id 八位00000000，
//                        车状态6位
//                        后面两位01是车桩号
//                        010D8A020001010000005234E79410
                        byte[] mCmd2 = Conversion.HexString2Bytes("0100CA01");
                        api.canCmd(0, mCmd2, mCmd2.length);
                        tv.append("云端回复8A:   " + "0100CA01" + "\n");
                        break;


                    case "88"://0x08 刷卡借车 cmd41
//                        01桩号 00C8协议 170302111820开始借车时间到秒 0064余额
                        SimpleDateFormat time = new SimpleDateFormat("yyMMddHHmmss");
                        String src2 = "0100C8" + time.format(new Date()) + "0064";
                        byte[] mCmd08 = Conversion.HexString2Bytes(src2);
                        api.canCmd(0, mCmd08, mCmd08.length);
                        tv.append("云端返回88:   " + src2 + "\n");
                        break;

                    case "89"://0x09 刷卡还车 cmd42
//                    前面的六位固定协议和桩号  0164余额 0001本次扣费 01时长
                        String src3 = "0100C9" + "0164" + "0001" + "01";
                        byte[] mCmd09 = Conversion.HexString2Bytes(src3);
                        api.canCmd(0, mCmd09, mCmd09.length);
                        tv.append("云端回复89:   " + src3 + "\n");
                        break;

                    case "8D"://0x0D 还车 cmd43
                        //                    前面的六位固定协议和桩号  0164余额 0001本次扣费 01时长
                        String src5 = "0100CD" + "0164" + "0001" + "01";
                        byte[] mCmd0D = Conversion.HexString2Bytes(src5);
                        api.canCmd(0, mCmd0D, mCmd0D.length);
                        tv.append("云端回复8D:   " + src5 + "\n");

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
