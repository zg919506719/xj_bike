package com.xingjian.xjmtkpad.beanrequest;

/**
 * Created by thinkpad on 2017/8/14.
 */

public class StaffCheckReq {

    /**
     * siteId : 59
     * cmd : 27
     * way : 1
     * sn : 0
     * data : {"type":"2","value":"1","update_type":"1"}
     */

    private String siteId;
    private String cmd;
    private String way;
    private String sn;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : 2
         * value : 1
         * update_type : 1
         */

        private String type;
        private String value;
        private String update_type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getUpdate_type() {
            return update_type;
        }

        public void setUpdate_type(String update_type) {
            this.update_type = update_type;
        }
    }
}
