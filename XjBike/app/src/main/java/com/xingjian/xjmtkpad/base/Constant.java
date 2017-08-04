package com.xingjian.xjmtkpad.base;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class Constant {
    //发送的心跳包
    public static String HEAD="{\"siteId\":\"59\",\"cmd\":\"heartbeat\",\"way\":\"1\",\"sn\":\"0\"}";
    //回应的心跳包
//    public static String HEADRES="{\"siteId\":\"59\",\"cmd\":\"heartbeat\",\"way\": \"2\",\"sn\":\"0\",\"heart\":30}";
    public static String HEADRES="{\"siteId\":\"59\",\"cmd\":\"heartbeat\",\"sn\":\"0\",\"heart\":30}";
//                    client.sendString(new JSONObject(reqJson.reqStr0b).toString());//骑行计费标准请求
    public String reqStr0b = "{\"siteId\":\"59\",\"cmd\":\"0b\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\",\"mode\":\"1\"}}";
//    {
//        "siteId": "1234",
//            "cmd": "21",
//            "way": "1",
//            "sn": "0",
//            "data": {
//        "type": "1",
//                "value": "1234"
//    }
//    }
//
//    //注释
//    type 类型（1-用户刷卡查询，2-用户卡ID输入查询，3-身份证号输入查询）
//    value 用户卡内部ID、用户卡ID或身份证号
//{
//    "siteId": "1234",
//        "cmd": "21",
//        "way": "2",
//        "sn": "0",
//        "data": {
//    "user_cardId": "1234",
//            "user_name": "小青",
//            "user_identity": "130129198902031613",
//            "user_phone": "15810466677",
//}
//}

    //注释
//    user_cardId 用户卡ID
//    user_name 用户姓名
//    user_identity 身份证号码
//    user_phone 手机号码

//    4.4.2 站点用户信息修改（cmd = 22）
//
//    站点->云端
//    {
//        "siteId": "1234",
//            "cmd": "22",
//            "way": "1",
//            "sn": "0",
//            "data": {
//        "type": "1",
//                "value": "1234",
//                "update_type": "1",
//                "oldval": "123456",
//                "newval": "654321"
//    }
//    }
//
//    //注释
//    type 类型（1-用户刷卡修改，2-用户卡ID输入修改，3-身份证号输入修改）
//    value 用户卡内部ID、用户卡ID或身份证号
//    update_type 修改类型（1-手机号码，2-密码）
//    oldval [旧手机号码或旧密码]
//    newval [新手机号码或新密码]
//    云端->站点
//    {
//        "siteId": "1234",
//            "cmd": "22",
//            "way": "2",
//            "sn": "0",
//            "data": {
//        "user_cardId": "1234",
//                "update_type": "1",
//                "result": "1"
//    }
//    }
//
//    //注释
//    user_cardId 用户卡ID
//    update_type 修改类型
//    result 结果（1-成功，0-失败）


}
