package com.xingjian.xjmtkpad.beanrequest;

/**
 * Created by thinkpad on 2017/8/9.
 */

public class CardLLoginReq {

    /**
     * siteId : 59
     * cmd : d3
     * way : 1
     * sn : 0
     * address : 济南
     * data : {"user_cardId":"2B0EF1E4"}
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
         * user_cardId : 2B0EF1E4
         */

        private String user_cardId;

        public String getUser_cardId() {
            return user_cardId;
        }

        public void setUser_cardId(String user_cardId) {
            this.user_cardId = user_cardId;
        }
    }
}
