package com.xingjian.xjmtkpad.beanresponse;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class UserInfoRes {

    /**
     * siteId : 59
     * cmd : 21
     * way : 2
     * sn : 0
     * data : {"user_cardId":"658D17E2","user_name":"card8","user_identity":"1404261993052136111","user_phone":"18515194217"}
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
         * user_cardId : 658D17E2
         * user_name : card8
         * user_identity : 1404261993052136111
         * user_phone : 18515194217
         */

        private String user_cardId;
        private String user_name;
        private String user_identity;
        private String user_phone;

        public String getUser_cardId() {
            return user_cardId;
        }

        public void setUser_cardId(String user_cardId) {
            this.user_cardId = user_cardId;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_identity() {
            return user_identity;
        }

        public void setUser_identity(String user_identity) {
            this.user_identity = user_identity;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }
    }
}
