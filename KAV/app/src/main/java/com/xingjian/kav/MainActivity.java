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
                tv.append("云端开锁11:   " + "01001 " +
                        "101" + "\n");
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
        String data = "case \"8E\"://0x0E 请求时间"
                + "\n" + "case \"8A\"://0x0A 锁桩车辆状态"
                + "\n" + "case \"88\"://0x08 刷卡借车 cmd41"
                + "\n" + " case \"8D\"://0x0D 还车未刷卡 cmd43"
                + "\n" + "  case \"91\"://0x09 云端  cmd42";
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
                    case "8E"://0x0E 请求时间 通了
                        SimpleDateFormat yyMMddHHmmss = new SimpleDateFormat("yyMMddHHmmss");
                        String src1 = pileId + "00CE" + yyMMddHHmmss.format(new Date());
                        tv.append("传过去8E:   " + src1 + "\n");
                        byte[] mCmd0E = Conversion.HexString2Bytes(src1);
                        api.canCmd(0, mCmd0E, mCmd0E.length);
                        break;
                    case "8A"://0x0A 锁桩车辆状态上报
//                        01 0D8A  02 00 01 01 0000
//                            没有车辆信息
//                            01078A是协议，00锁桩类型，
//                              00锁桩状态，
//                              0000锁桩 故障原因
//                              00故障2
//                            车类型 81，车辆id 八位00000000，
//                        车状态6位
//                        后面两位01是车桩号
//                        010D8A020001010000005234E794 10
                        tv.append("已收到8A，上报云端");
                        break;
                    case "88"://0x08 刷卡借车 cmd41
//                        010D88  D0569B19
//                      20卡类型 M1 08 银行卡20，28
//                      01 锁桩状态 00没问题 01有问题 02停用
//                        锁桩故障原因两位
//	        0x01			// 	0x01-断网：表示该站点管理箱与锁桩之间通信链路不正常
//   	        0x02			// 	0x02-锁止器异常
//		    0x03			// 	0x03-读卡器异常
//            0x04			// 	0x04-用户读卡器故障：0x04
//	        0x05			// 	0x05-车辆读卡器故障：0x05
//   		    0x07			//  0x07-读FLASH异常
//		    0x09		    //	电量板故障
//                              5234E794    82车类型普通车
                        String result1 = cmd.substring(16, 18);
                        tv.append("车量状态为"+result1+ "余额充足，准备借车\n");
//                        01桩号 00C8协议  0064余额
//                      借车成功00 失败01
                        String src2 = "0100C8" + "00" + "0064";
                        byte[] mCmd08 = Conversion.HexString2Bytes(src2);
                        api.canCmd(0, mCmd08, mCmd08.length);
                        break;
                    case "8D"://0x0D 还车 cmd43
//                        01088D 01    5234E794   82
                        //                    前面的六位固定协议和桩号  0164余额 0001本次扣费
                        String result = cmd.substring(6, 8);
                        tv.append("还车结果为"+result+"返回了余额"+"\n");
                        String src5 = "0100CD" + "0164" + "0001";
                        byte[] mCmd0D = Conversion.HexString2Bytes(src5);
                        api.canCmd(0, mCmd0D, mCmd0D.length);
                        break;
                    case "91":
                        //云端借车回复 01029100 00表示成功，01表示失败
                        String state = cmd.substring(8, 10);
                        if (state.equals("00")) {
                            tv.append("借车成功\n");
                        } else {
                            tv.append("借车失败"+state+"\n");
                        }
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
