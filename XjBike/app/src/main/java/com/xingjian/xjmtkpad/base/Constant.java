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
}
