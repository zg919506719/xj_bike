package com.xingjian.xjmtkpad.beanresponse;

import java.util.List;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class UserInfoRes {

    /**
     * siteId : 59
     * cmd : 23
     * way : 2
     * sn : 0
     * data : {"user_cardId":"3","user_name":"card7","user_cardState":"0","count":"3","page":"1","prepage":"10","list":[{"operate_type":"1","operates_time":"2017-06-30 12:06:56.0","operates_result":"1"},{"operate_type":"0","operates_time":"2017-06-30 12:07:33.0","operates_result":"1"},{"operate_type":"1","operates_time":"2017-06-30 12:08:16.0","operates_result":"1"}]}
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
         * user_cardId : 3
         * user_name : card7
         * user_cardState : 0
         * count : 3
         * page : 1
         * prepage : 10
         * list : [{"operate_type":"1","operates_time":"2017-06-30 12:06:56.0","operates_result":"1"},{"operate_type":"0","operates_time":"2017-06-30 12:07:33.0","operates_result":"1"},{"operate_type":"1","operates_time":"2017-06-30 12:08:16.0","operates_result":"1"}]
         */

        private String user_cardId;
        private String user_name;
        private String user_cardState;
        private String count;
        private String page;
        private String prepage;
        private List<ListBean> list;

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

        public String getUser_cardState() {
            return user_cardState;
        }

        public void setUser_cardState(String user_cardState) {
            this.user_cardState = user_cardState;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPrepage() {
            return prepage;
        }

        public void setPrepage(String prepage) {
            this.prepage = prepage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * operate_type : 1
             * operates_time : 2017-06-30 12:06:56.0
             * operates_result : 1
             */

            private String operate_type;
            private String operates_time;
            private String operates_result;

            public String getOperate_type() {
                return operate_type;
            }

            public void setOperate_type(String operate_type) {
                this.operate_type = operate_type;
            }

            public String getOperates_time() {
                return operates_time;
            }

            public void setOperates_time(String operates_time) {
                this.operates_time = operates_time;
            }

            public String getOperates_result() {
                return operates_result;
            }

            public void setOperates_result(String operates_result) {
                this.operates_result = operates_result;
            }
        }
    }
}
