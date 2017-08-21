package com.xingjian.xjmtkpad.beanresponse;

import java.util.List;

/**
 * Created by thinkpad on 2017/8/21.
 */

public class StationRes {

    /**
     * siteId : 59
     * cmd : 11
     * way : 2
     * sn : 0
     * data : {"count":"10","page":"1","prepage":"10","list":[{"site_id":"59","site_name":"历下区政府","site_address":"济南","site_addressLongitude":"117.083021,36.672009"},{"site_id":"60","site_name":"济南大学南校区","site_address":"济南","site_addressLongitude":"117.084979,36.670905"},{"site_id":"61","site_name":"政法学院警官学院","site_address":"济南","site_addressLongitude":"117.083025,36.668358"},{"site_id":"62","site_name":"甸柳第一中学","site_address":"济南","site_addressLongitude":"117.075879,36.670019"},{"site_id":"63","site_name":"济南工程学院和平校区","site_address":"济南","site_addressLongitude":"117.075628,36.666806"},{"site_id":"64","site_name":"普利大厦","site_address":"济南","site_addressLongitude":"117.087144,36.677357"},{"site_id":"65","site_name":"友谊小学","site_address":"济南","site_addressLongitude":"117.098786,36.67245"},{"site_id":"66","site_name":"政法学院","site_address":"济南","site_addressLongitude":"117.098786,36.67245"},{"site_id":"67","site_name":"燕山小区","site_address":"济南","site_addressLongitude":"117.067462,36.666842"},{"site_id":"68","site_name":"解放路第一小学","site_address":"济南","site_addressLongitude":"117.056575,36.668752"}]}
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
         * count : 10
         * page : 1
         * prepage : 10
         * list : [{"site_id":"59","site_name":"历下区政府","site_address":"济南","site_addressLongitude":"117.083021,36.672009"},{"site_id":"60","site_name":"济南大学南校区","site_address":"济南","site_addressLongitude":"117.084979,36.670905"},{"site_id":"61","site_name":"政法学院警官学院","site_address":"济南","site_addressLongitude":"117.083025,36.668358"},{"site_id":"62","site_name":"甸柳第一中学","site_address":"济南","site_addressLongitude":"117.075879,36.670019"},{"site_id":"63","site_name":"济南工程学院和平校区","site_address":"济南","site_addressLongitude":"117.075628,36.666806"},{"site_id":"64","site_name":"普利大厦","site_address":"济南","site_addressLongitude":"117.087144,36.677357"},{"site_id":"65","site_name":"友谊小学","site_address":"济南","site_addressLongitude":"117.098786,36.67245"},{"site_id":"66","site_name":"政法学院","site_address":"济南","site_addressLongitude":"117.098786,36.67245"},{"site_id":"67","site_name":"燕山小区","site_address":"济南","site_addressLongitude":"117.067462,36.666842"},{"site_id":"68","site_name":"解放路第一小学","site_address":"济南","site_addressLongitude":"117.056575,36.668752"}]
         */

        private String count;
        private String page;
        private String prepage;
        private List<ListBean> list;

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
             * site_id : 59
             * site_name : 历下区政府
             * site_address : 济南
             * site_addressLongitude : 117.083021,36.672009
             */

            private String site_id;
            private String site_name;
            private String site_address;
            private String site_addressLongitude;

            public String getSite_id() {
                return site_id;
            }

            public void setSite_id(String site_id) {
                this.site_id = site_id;
            }

            public String getSite_name() {
                return site_name;
            }

            public void setSite_name(String site_name) {
                this.site_name = site_name;
            }

            public String getSite_address() {
                return site_address;
            }

            public void setSite_address(String site_address) {
                this.site_address = site_address;
            }

            public String getSite_addressLongitude() {
                return site_addressLongitude;
            }

            public void setSite_addressLongitude(String site_addressLongitude) {
                this.site_addressLongitude = site_addressLongitude;
            }
        }
    }
}
