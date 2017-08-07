package com.xingjian.xjmtkpad.beanrequest;

import java.util.List;

/**
 * Created by thinkpad on 2017/8/6.
 */

public class RentRecordReq {
//借车时间范围 /还车时间范围
    /**
     * siteId : 59
     * cmd : 25
     * way : 1
     * sn : 0
     * data : {"type":"2","value":"2B0EF1E4","query":[{"query_type":"20","query_way":{"deduction_down":"0","deduction_up":"10"}}]}
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
         * type : 2
         * value : 2B0EF1E4
         * query : [{"query_type":"20","query_way":{"deduction_down":"0","deduction_up":"10"}}]
         */

        private String type;
        private String value;
        private List<QueryBean> query;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<QueryBean> getQuery() {
            return query;
        }

        public void setQuery(List<QueryBean> query) {
            this.query = query;
        }

        public static class QueryBean {
            /**
             * query_type : 20
             * query_way : {"deduction_down":"0","deduction_up":"10"}
             */

            private String query_type;
            private QueryWayBean query_way;

            public String getQuery_type() {
                return query_type;
            }

            public void setQuery_type(String query_type) {
                this.query_type = query_type;
            }

            public QueryWayBean getQuery_way() {
                return query_way;
            }

            public void setQuery_way(QueryWayBean query_way) {
                this.query_way = query_way;
            }

            public static class QueryWayBean {
                private String start_time;
                private String end_time;
                private String deduction_down;
                private String deduction_up;
                private String site_id;
                private String pile_id;
                private String vehicle_id;

                public String getVehicle_id() {
                    return vehicle_id;
                }

                public void setVehicle_id(String vehicle_id) {
                    this.vehicle_id = vehicle_id;
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

                public String getDeduction_down() {
                    return deduction_down;
                }

                public void setDeduction_down(String deduction_down) {
                    this.deduction_down = deduction_down;
                }

                public String getDeduction_up() {
                    return deduction_up;
                }

                public void setDeduction_up(String deduction_up) {
                    this.deduction_up = deduction_up;
                }

                public String getStart_time() {
                    return start_time;
                }

                public void setStart_time(String start_time) {
                    this.start_time = start_time;
                }

                public String getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(String end_time) {
                    this.end_time = end_time;
                }
            }
        }
    }
}
