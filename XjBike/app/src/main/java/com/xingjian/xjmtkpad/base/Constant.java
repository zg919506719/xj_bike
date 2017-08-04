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
//       client.sendString(new JSONObject(reqJson.reqStr03).toString());//站点编号名称请求
//                    client.sendString(new JSONObject(reqJson.reqStr05).toString());//时间同步请求
//                    client.sendString(new JSONObject(reqJson.reqStr0b).toString());//骑行计费标准请求
    public String reqStr03 = "{\"siteId\":\"59\",\"cmd\":\"03\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\"}}";
    public String reqStr05 = "{\"siteId\":\"59\",\"cmd\":\"05\",\"way\":\"1\",\"sn\":\"0\",\"data\":{}}";
    public String reqStr0b = "{\"siteId\":\"59\",\"cmd\":\"0b\",\"way\":\"1\",\"sn\":\"0\",\"data\":{\"address\":\"\\u6d4e\\u5357\",\"mode\":\"1\"}}";


}
