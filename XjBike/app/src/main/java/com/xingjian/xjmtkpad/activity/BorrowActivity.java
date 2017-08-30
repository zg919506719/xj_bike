package com.xingjian.xjmtkpad.activity;

import android.os.Bundle;
import android.posapi.Conversion;
import android.posapi.PosApi;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.xingjian.xjmtkpad.R;
import com.xingjian.xjmtkpad.base.MyApp;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class BorrowActivity extends AppCompatActivity {
    PosApi api;
    @BindView(R.id.tv)
    TextView tv;
    private static String TAG = "haha";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);
        ButterKnife.bind(this);
        //can初始化
        api = MyApp.posApi;
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
                String protocol = cmd.substring(4, 6);
//                5，6
//                tv.append("发送请求为:" + protocol);
                mPileID = cmd.substring(0, 2);
//                tv.append("车辆编号为：" + mPileID + "\n");
//                01 09 81 5417031501020304
                //01格式固定，表示发送方向，
                // 00成功与否，默认为0失败，01成功，
                // 81判断类型，protocol
                //6-8位以后就是校验位，
                // 9-倒数两位 是数据域  700100000代表默认时间，
                //最后的两位00也是校验位
//                tv.append("车辆请求为：" + protocol + "\n");


                switch (protocol) {
                    case "81"://0x01 锁桩ID编号请求 通了
//                        String pileInId = cmd.substring(6);
//                        5
//                        for (int i = 0; i < 100; i++) {
//                            if (pileInIdArr[i] == null || pileInIdArr[i].length() <= 0) {
//                                pileInIdArr[i] = pileInId;
//                                pileOutIdArr[i] = Integer.toHexString(i + 1).toUpperCase();
//                                if (i < 16) {
//                                    pileOutIdArr[i] = "0" + pileOutIdArr[i];
//                                }
//
//                                byte[] mCmd = Conversion.HexString2Bytes("0100C1" + pileInIdArr[i] + pileOutIdArr[i]);
//                                StringBuffer s = new StringBuffer();
//                                for (int c = 0; c < mCmd.length; c++) {
//                                    s.append(mCmd[c]);
//                                }
//                                tv.append("传过去" + s.toString() + "\n");
//                                String s1 = Conversion.Bytes2HexString(mCmd);
//                                tv.append("传过去的解析" + s1.toString() + "\n");
//                                api.canCmd(0, mCmd, mCmd.length);
//                                byte[] mCmd0B = Conversion.HexString2Bytes(pileOutIdArr[i] + "000B");//0x0B 锁桩车辆状态读取
//                                api.canCmd(0, mCmd0B, mCmd0B.length);
//                                break;
//                            }
//                        }
                        String req = cmd.substring(cmd.length() - 16, cmd.length());
                        //01代表车辆数
                        String src = "0100C1" + req + "01";
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
                        tv.append("云端回复8E:   " + "0100C302" + "\n");
                        api.canCmd(0, mCmd, mCmd.length);
                        break;

                    case "44"://0x04 锁桩上报方式设置
//                        cmd=88
//                        "report_way": "1",
//                            "report_interval": "10"
//                        report_way 上报方式（1-周期性上报 2-变化上报）
//                        report_interval 周期性上报间隔（单位分钟）
                        protocol = "0004";
                        byte[] mCmd1 = Conversion.HexString2Bytes("01000402");
                        tv.append("云端回复44:   " + "01000402" + "\n");
                        api.canCmd(0, mCmd1, mCmd1.length);
//                        mSocketClient.sendString(new JSONObject(respJson.respStr88).toString());
                        break;

                    case "85"://0x05 锁桩工作模式请求 通了
                        //mPileID是锁桩id，后面01是发送方向
                        String src4 = mPileID + "00C501";
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
//                        mReport_type = cmd.substring(6, 8);
//                        mPile_type = cmd.substring(8, 10);
//                        mPile_id = cmd.substring(10, 12);
//                        mPile_state = cmd.substring(12, 14);
//                        mPile_fault1 = cmd.substring(14, 16);
//                        mPile_fault2 = cmd.substring(16, 18);
//                        mVehicle_type = cmd.substring(18, 20);
//                        mVehicle_id = cmd.substring(20, 28);
//                        mVehicle_state = cmd.substring(28);
//                        String reqJson01 = "{\"siteId\":\"59\",\"cmd\":\"01\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"type\":\"" + mReport_type + "\",
// \"address\":\"\\u6d4e\\u5357\",\"site_state\":{\"site_state\":\"0\",\"pile_num\":\"1\",\"vehicle_num\":\"1\"}
// ,\"pile_num\":\"1\",\"piles\":[{\"pile_id\":\"" + mPile_id + "\",\"pile_state\":{\"pile_state\":\""
// + mPile_state + "\",\"fault1\":\"" + mPile_fault1 + "\",\"fault2\":\"" + mPile_fault2 + "\"},
// \"vehicle_id\":\"" + mVehicle_id + "\",\"vehicle_state\":\"" + mVehicle_state + "\"}]}}";
//                        mSocketClient.sendString(new JSONObject(reqJson01).toString());
//                            Log.i("===", "站点锁桩车辆状态上报成功");
//                        前面两位01是车桩号
                        byte[] mCmd2 = Conversion.HexString2Bytes("0100CA01");
                        api.canCmd(0, mCmd2, mCmd2.length);
                        tv.append("云端回复8A:   " + "0100CA01" + "\n");
//                            Log.i("===", "站点锁桩车辆状态上报失败");
//                            byte[] mCmd = Conversion.HexString2Bytes("0100CA02");
//                            mApi.canCmd(0, mCmd, mCmd.length);
                        break;

//                    case "4B"://0x0B 锁桩车辆状态读取
//                        mReport_type = cmd.substring(7, 8);
//                        mPile_type = cmd.substring(9, 10);
//                        mPile_id = cmd.substring(11, 12);
//                        mPile_state = cmd.substring(13, 14);
//                        mPile_fault1 = cmd.substring(15, 16);
//                        mPile_fault2 = cmd.substring(17, 18);
//                        String reqJson82 = "{\"siteId\":\"59\",\"cmd\":\"01\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"type\":\"" + mReport_type + "\",\"address\":\"\\u6d4e\\u5357\",\"site_state\":{\"site_state\":\"0\",\"pile_num\":\"1\",\"vehicle_num\":\"0\"},\"pile_num\":\"1\",\"piles\":[{\"pile_id\":\"" + "1" + "\",\"pile_state\":{\"pile_state\":\"" + mPile_state + "\",\"fault1\":\"" + mPile_fault1 + "\",\"fault2\":\"" + mPile_fault2 + "\"}}]}}";
//                            mVehicle_type = cmd.substring(18, 20);
//                            mVehicle_id = cmd.substring(20, 28);
//                            mVehicle_state = cmd.substring(28);
//                            String reqJson82 = "{\"siteId\":\"59\",\"cmd\":\"01\",\"way\":\"1\",\"sn\":\"0\",\"data\"
// :{\"type\":\"" + mReport_type + "\",\"address\":\"\\u6d4e\\u5357\",\"site_state\":{\"site_state\":\"0\",\"pile_num\":
// \"1\",\"vehicle_num\":\"1\"},\"pile_num\":\"1\",\"piles\":[{\"pile_id\":\"" + mPile_id + "\",\"pile_state\":
// {\"pile_state\":\"" + mPile_state + "\",\"fault1\":\"" + mPile_fault1 + "\",\"fault2\":\"" + mPile_fault2 + "\"},
// \"vehicle_id\":\"" + mVehicle_id + "\",\"vehicle_state\":\"" + mVehicle_state + "\"}]}}";
//                        mSocketClient.sendString(new JSONObject(reqJson82).toString());
//                        Log.i("===", "读取状态后上报  " + reqJson82);
//                        break;

                    case "4C"://0x0C 远程应急车辆锁定和解锁
//                        mSocketClient.sendString(new JSONObject(respJson.respStr8e).toString());
//                        开头01桩号 云端vehicleId 车辆id  operation 01锁定02解锁
                        String vehicleId = "1";
                        String src6 = "01000C" + vehicleId + "01";
                        byte[] mCmd3 = Conversion.HexString2Bytes(src6);
                        tv.append("云端回复4C:   " + src6 + "\n");
                        api.canCmd(0, mCmd3, mCmd3.length);
                        break;

                    case "88"://0x08 刷卡借车 cmd41
//                        mUser_cardInnerId = cmd.substring(6, 14);
//                        mUser_cardState = cmd.substring(15, 16);
//                        mBalance = cmd.substring(18, 20);
//                        mPile_id = cmd.substring(21, 22);
//                        mPile_state = cmd.substring(23, 24);
//                        mPile_fault1 = cmd.substring(25, 26);
//                        mPile_fault2 = cmd.substring(27, 28);
//                        mVehicle_id = cmd.substring(28, 36);
//                        mVehicle_state = cmd.substring(37);
//                        String reqJson41 = "{\"siteId\":\"59\",\"cmd\":\"41\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\",\"user_cardInnerId\":\"" + mUser_cardInnerId + "\",\"user_cardState\":\"" + mUser_cardState + "\",\"balance\":\"" + mBalance + "\",\"pile_id\":\"" + "1" + "\",\"pile_state\":{\"pile_state\":\"" + mPile_state + "\",\"fault1\":\"" + mPile_fault1 + "\",\"fault2\":\"" + mPile_fault2 + "\"},\"vehicle_id\":\"" + mVehicle_id + "\",\"vehicle_state\":\"" + mVehicle_state + "\"}}";
//                        mSocketClient.sendString(new JSONObject(reqJson41).toString());
//                        Log.i("===", "刷卡借车请求  " + reqJson41);

//                            Log.i("===", "允许租车");
//                        01桩号 00C8协议 170302111820开始借车时间到秒 0064余额
                        SimpleDateFormat time = new SimpleDateFormat("yyMMddHHmmss");
                        String src2 = "0100C8" + time.format(new Date())+"0064";
                        byte[] mCmd08 = Conversion.HexString2Bytes(src2);
                        api.canCmd(0, mCmd08, mCmd08.length);
                        tv.append("云端返回88:   " + src2 + "\n");
                        break;

                    case "89"://0x09 刷卡还车 cmd42
//                        mUser_cardInnerId = cmd.substring(6, 14);
//                        mUser_cardState = cmd.substring(15, 16);
//                        mBalance = cmd.substring(18, 20);
//                        mPile_id = cmd.substring(21, 22);
//                        mPile_state = cmd.substring(23, 24);
//                        mPile_fault1 = cmd.substring(25, 26);
//                        mPile_fault2 = cmd.substring(27, 28);
//                        mVehicle_id = cmd.substring(28, 36);
//                        mVehicle_state = cmd.substring(37, 38);
//                        mBorrowCar_time = cmd.substring(39);
//                        String reqJson42 = "{\"siteId\":\"59\",\"cmd\":\"42\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\",\"user_cardInnerId\":\"" + mUser_cardInnerId + "\",\"user_cardState\":\"" + mUser_cardState + "\",\"balance\":\"" + mBalance + "\",\"pile_id\":\"" + "1" + "\",\"pile_state\":{\"pile_state\":\"" + mPile_state + "\",\"fault1\":\"" + mPile_fault1 + "\",\"fault2\":\"" + mPile_fault2 + "\"},\"vehicle_id\":\"" + mVehicle_id + "\",\"vehicle_state\":\"" + mVehicle_state + "\",\"borrowCar_time\":\"" + mBorrowCar_time + "\"}}";
//                        mSocketClient.sendString(new JSONObject(reqJson42).toString());
//                        Log.i("===", "刷卡还车请求  " + reqJson42);
//                    前面的六位固定协议和桩号  0164余额 0001本次扣费 01时长
                        String src3 = "0100C9" + "0164" + "0001" + "01";
                        byte[] mCmd09 = Conversion.HexString2Bytes(src3);
                        api.canCmd(0, mCmd09, mCmd09.length);
                        tv.append("云端回复89:   " + src3 + "\n");
                        break;

                    case "8D"://0x0D 还车未刷卡 cmd43
//                        mPile_id = cmd.substring(7, 8);
//                        mPile_state = cmd.substring(9, 10);
//                        mPile_fault1 = cmd.substring(11, 12);
//                        mPile_fault2 = cmd.substring(13, 14);
//                        mVehicle_id = cmd.substring(14, 22);
//                        mVehicle_state = cmd.substring(23);
//                        String reqJson43 = "{\"siteId\":\"59\",\"cmd\":\"43\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\",\"pile_id\":\"" + "1" + "\",\"pile_state\":{\"pile_state\":\"" + mPile_state + "\",\"fault1\":\"" + mPile_fault1 + "\",\"fault2\":\"" + mPile_fault2 + "\"},\"vehicle_id\":\"" + mVehicle_id + "\",\"vehicle_state\":\"" + mVehicle_state + "\"}}";
//                        mSocketClient.sendString(new JSONObject(reqJson43).toString());
//                        Log.i("===", "还车未刷卡请求  " + reqJson43);

//                            Log.i("===", "允许还车");
  //                    前面的六位固定协议和桩号  0164余额 0001本次扣费 01时长
                        String src5 = mPileID + "00CD" + "0164" + "0001" + "01";
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
}
