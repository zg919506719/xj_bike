package com.xingjian.xjmtkpad.base;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class Constant {
    //发送的心跳包
    public static String HEAD = "{\"siteId\":\"59\",\"cmd\":\"heartbeat\",\"way\":\"1\",\"sn\":\"0\"}";
    //回应的心跳包
    public static String HEADRES = "{\"siteId\":\"59\",\"cmd\":\"heartbeat\",\"way\": \"2\",\"sn\":\"0\",\"heart\":30}";

    public static int TEM = 35;
    public static int HUM = 80;
    public static int AUTO_SHOW_TIME = 20000;
    public static int CIRCLE_TIME = 20000;
    public static int  M1_CIRCLE_TIME = 5000;
}
