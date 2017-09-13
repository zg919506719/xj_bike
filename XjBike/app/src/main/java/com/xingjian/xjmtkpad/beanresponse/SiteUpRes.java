package com.xingjian.xjmtkpad.beanresponse;

/**
 * Created by thinkpad on 2017/9/13.
 */

public class SiteUpRes {

    /**
     * siteId : 59
     * cmd : 07
     * way : 2
     * sn : 0
     * data : {"report_way":"0","report_interval":"0"}
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
         * report_way : 0
         * report_interval : 0
         */

        private String report_way;
        private String report_interval;

        public String getReport_way() {
            return report_way;
        }

        public void setReport_way(String report_way) {
            this.report_way = report_way;
        }

        public String getReport_interval() {
            return report_interval;
        }

        public void setReport_interval(String report_interval) {
            this.report_interval = report_interval;
        }
    }
}
