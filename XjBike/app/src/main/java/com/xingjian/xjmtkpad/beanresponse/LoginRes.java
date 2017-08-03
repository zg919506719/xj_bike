package com.xingjian.xjmtkpad.beanresponse;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class LoginRes {

    /**
     * siteId : 1234
     * cmd : d2
     * way : 2
     * sn : 0
     * address : 北京
     * data : {"result":"1"}
     */

    private String siteId;
    private String cmd;
    private String way;
    private String sn;
    private String address;
    private DataBean data;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * result : 1
         */

        private String result;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
