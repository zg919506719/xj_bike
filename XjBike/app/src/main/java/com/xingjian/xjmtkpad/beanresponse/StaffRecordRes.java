package com.xingjian.xjmtkpad.beanresponse;

import java.util.List;

/**
 * Created by thinkpad on 2017/8/16.
 */

public class StaffRecordRes {

    /**
     * siteId : 59
     * cmd : 28
     * way : 2
     * sn : 0
     * data : {"count":"10","page":"1","rows":"10","prepage":"10","list":[{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"}]}
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
         * rows : 10
         * prepage : 10
         * list : [{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"},{"operate_type":"1","attendance_time":"2017-08-16 17:21:30.0"}]
         */

        private String count;
        private String page;
        private String rows;
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

        public String getRows() {
            return rows;
        }

        public void setRows(String rows) {
            this.rows = rows;
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
             * attendance_time : 2017-08-16 17:21:30.0
             */

            private String operate_type;
            private String attendance_time;

            public String getOperate_type() {
                return operate_type;
            }

            public void setOperate_type(String operate_type) {
                this.operate_type = operate_type;
            }

            public String getAttendance_time() {
                return attendance_time;
            }

            public void setAttendance_time(String attendance_time) {
                this.attendance_time = attendance_time;
            }
        }
    }
}
