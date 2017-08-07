package com.xingjian.xjmtkpad.beanresponse;

import java.util.List;

/**
 * ***********************************************************
 * author: Dove
 * time: 2016/11/9 14:27
 * name:
 * overview:
 * usage:
 * *************************************************************
 */
public class RechargeRes {

    /**
     * 充值记录查询（cmd = 26）
     * siteId : 1234
     * cmd : 26
     * way : 2
     * sn : 0
     * data : {"user_cardId":"1234","count":"3","page":"1","prepage":"10","list":[{"recharge_time":"2016-10-20 13:29:30","recharge_cost":"100","balance":"150","customer_id":"2","staff_id":"1"},{"recharge_time":"2016-10-20 13:29:30","recharge_cost":"100","balance":"150","customer_id":"2","staff_id":"1"},{"recharge_time":"2016-10-20 13:29:30","recharge_cost":"100","balance":"150","customer_id":"2","staff_id":"1"}]}
     */

    private String siteId;
    private String cmd;
    private String way;
    private String sn;
    /**
     * user_cardId : 1234
     * count : 3
     * page : 1
     * prepage : 10
     * list : [{"recharge_time":"2016-10-20 13:29:30","recharge_cost":"100","balance":"150","customer_id":"2","staff_id":"1"},{"recharge_time":"2016-10-20 13:29:30","recharge_cost":"100","balance":"150","customer_id":"2","staff_id":"1"},{"recharge_time":"2016-10-20 13:29:30","recharge_cost":"100","balance":"150","customer_id":"2","staff_id":"1"}]
     */

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
        private String user_cardId;
        private String count;
        private String page;
        private String prepage;
        /**
         * recharge_time : 2016-10-20 13:29:30
         * recharge_cost : 100
         * balance : 150
         * customer_id : 2
         * staff_id : 1
         */

        private List<ListBean> list;

        public String getUser_cardId() {
            return user_cardId;
        }

        public void setUser_cardId(String user_cardId) {
            this.user_cardId = user_cardId;
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
            private String recharge_time;//充值时间
            private String recharge_cost;//充值费用
            private String balance;//充后余额
            private String customer_id;//客服网点ID
            private String staff_id;//工作人员ID

            public String getRecharge_time() {
                return recharge_time;
            }

            public void setRecharge_time(String recharge_time) {
                this.recharge_time = recharge_time;
            }

            public String getRecharge_cost() {
                return recharge_cost;
            }

            public void setRecharge_cost(String recharge_cost) {
                this.recharge_cost = recharge_cost;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getStaff_id() {
                return staff_id;
            }

            public void setStaff_id(String staff_id) {
                this.staff_id = staff_id;
            }
        }
    }
}
