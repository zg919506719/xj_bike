package com.xingjian.xjmtkpad.beanrequest;

/**
 * Created by thinkpad on 2017/8/3.
 */

public class LoginReq {

    /**
     * siteId : 1234
     * cmd : d2
     * way : 1
     * sn : 0
     * address : 北京
     * data : {"username":"15810466677","password":"123456"}
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
         * username : 15810466677
         * password : 123456
         */

        private String code;
        private String password;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
