package com.xingjian.xjmtkpad.beanresponse;

import java.util.List;

/**
 * ***********************************************************
 * author: Dove
 * time: 2016/11/9 14:18
 * name:
 * overview:
 * usage:
 * *************************************************************
 */
public class RentRecordsRes {

    /**
     * 站点用户租还车记录查询（cmd = 25）
     * siteId : 1234
     * cmd : 25
     * way : 2
     * sn : 0
     * data : {"user_cardId":"1234","count":"3","page":"1","prepage":"10","list":[{"borrowCar_time":"2016-10-20 13:29:30","repayCar_time":"2016-10-20 14:29:30","duration":"1","site_id":"1","pile_id":"1","user_cardId":"1234","vehicle_id":"1234","deduction":"10"},{"borrowCar_time":"2016-10-20 13:29:30","repayCar_time":"2016-10-20 14:29:30","duration":"1","site_id":"1","pile_id":"1","user_cardId":"1234","vehicle_id":"1234","deduction":"10"},{"borrowCar_time":"2016-10-20 13:29:30","repayCar_time":"2016-10-20 14:29:30","duration":"1","site_id":"1","pile_id":"1","user_cardId":"1234","vehicle_id":"1234","deduction":"10"}]}
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
     * list : [{"borrowCar_time":"2016-10-20 13:29:30","repayCar_time":"2016-10-20 14:29:30","duration":"1","site_id":"1","pile_id":"1","user_cardId":"1234","vehicle_id":"1234","deduction":"10"},{"borrowCar_time":"2016-10-20 13:29:30","repayCar_time":"2016-10-20 14:29:30","duration":"1","site_id":"1","pile_id":"1","user_cardId":"1234","vehicle_id":"1234","deduction":"10"},{"borrowCar_time":"2016-10-20 13:29:30","repayCar_time":"2016-10-20 14:29:30","duration":"1","site_id":"1","pile_id":"1","user_cardId":"1234","vehicle_id":"1234","deduction":"10"}]
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
         * borrowCar_time : 2016-10-20 13:29:30
         * repayCar_time : 2016-10-20 14:29:30
         * duration : 1
         * site_id : 1
         * pile_id : 1
         * user_cardId : 1234
         * vehicle_id : 1234
         * deduction : 10
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
            private String borrowCar_time;//借车时间
            private String repayCar_time;//还车时间
            private String duration;//时长-X小时
            private String site_id;
            private String pile_id;
            private String user_cardId;
            private String vehicle_id;
            private String deduction;//扣费

            public String getBorrowCar_time() {
                return borrowCar_time;
            }

            public void setBorrowCar_time(String borrowCar_time) {
                this.borrowCar_time = borrowCar_time;
            }

            public String getRepayCar_time() {
                return repayCar_time;
            }

            public void setRepayCar_time(String repayCar_time) {
                this.repayCar_time = repayCar_time;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getSite_id() {
                return site_id;
            }

            public void setSite_id(String site_id) {
                this.site_id = site_id;
            }

            public String getPile_id() {
                return pile_id;
            }

            public void setPile_id(String pile_id) {
                this.pile_id = pile_id;
            }

            public String getUser_cardId() {
                return user_cardId;
            }

            public void setUser_cardId(String user_cardId) {
                this.user_cardId = user_cardId;
            }

            public String getVehicle_id() {
                return vehicle_id;
            }

            public void setVehicle_id(String vehicle_id) {
                this.vehicle_id = vehicle_id;
            }

            public String getDeduction() {
                return deduction;
            }

            public void setDeduction(String deduction) {
                this.deduction = deduction;
            }
        }
    }
}
