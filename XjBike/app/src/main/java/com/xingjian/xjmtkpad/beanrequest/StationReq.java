package com.xingjian.xjmtkpad.beanrequest;

/**
 * Created by thinkpad on 2017/8/14.
 */

public class StationReq {

    /**
     * siteId : 59
     * cmd : 11
     * way : 1
     * sn : 0
     * data : {"address":"济南"}
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
         * address : 济南
         */

        private String address;
        private String page;
        private String rows;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getRows() {
            return rows;
        }

        public void setRows(String rows) {
            this.rows = rows;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
