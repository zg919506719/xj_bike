package com.xingjian.xjmtkpad.beanresponse;

/**
 * Created by thinkpad on 2017/8/14.
 */

public class StaffCheckRes {

    /**
     * siteId : 59
     * cmd : 27
     * way : 2
     * sn : 0
     * data : {"staff_cardId":"5","operate_type":"1","operate_result":"1"}
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
         * staff_cardId : 5
         * operate_type : 1
         * operate_result : 1
         */

        private String staff_cardId;
        private String operate_type;
        private String operate_result;

        public String getStaff_cardId() {
            return staff_cardId;
        }

        public void setStaff_cardId(String staff_cardId) {
            this.staff_cardId = staff_cardId;
        }

        public String getOperate_type() {
            return operate_type;
        }

        public void setOperate_type(String operate_type) {
            this.operate_type = operate_type;
        }

        public String getOperate_result() {
            return operate_result;
        }

        public void setOperate_result(String operate_result) {
            this.operate_result = operate_result;
        }
    }
}
