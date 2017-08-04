package com.xingjian.xjmtkpad.beanresponse;

import java.util.List;

/**
 * Created by thinkpad on 2017/8/4.
 */

public class RentResHour {

    /**
     * siteId : 59
     * cmd : 0b
     * way : 2
     * sn : 0
     * data : {"mode":"2","ladder_num":"2","cost":"2.0","ladder":[{"start":"0","end":"1","cost":"3.0"},{"start":"1","end":"10000","cost":"3.0"}]}
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
         * mode : 2
         * ladder_num : 2
         * cost : 2.0
         * ladder : [{"start":"0","end":"1","cost":"3.0"},{"start":"1","end":"10000","cost":"3.0"}]
         */

        private String mode;
        private String ladder_num;
        private String cost;
        private List<LadderBean> ladder;

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getLadder_num() {
            return ladder_num;
        }

        public void setLadder_num(String ladder_num) {
            this.ladder_num = ladder_num;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public List<LadderBean> getLadder() {
            return ladder;
        }

        public void setLadder(List<LadderBean> ladder) {
            this.ladder = ladder;
        }

        public static class LadderBean {
            /**
             * start : 0
             * end : 1
             * cost : 3.0
             */

            private String start;
            private String end;
            private String cost;

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public String getCost() {
                return cost;
            }

            public void setCost(String cost) {
                this.cost = cost;
            }
        }
    }
}
