package com.xingjian.xjmtkpad.beanrequest;

import java.util.List;

/**
 * Created by thinkpad on 2017/8/7.
 */

public class RechargeReq {

    /**
     * siteId : 59
     * cmd : 26
     * way : 1
     * sn : 0
     * data : {"type":"2","value":"2B0EF1E4","query":[{"query_type":"1","query_way":{"start_time":"2017-06-20 16:53:30","end_time":"2017-07-10 17:53:30"}}]}
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
         * query : [{"query_type":"1","query_way":{"start_time":"2017-06-20 16:53:30","end_time":"2017-07-10 17:53:30"}}]
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
             * query_type : 1
             * query_way : {"start_time":"2017-06-20 16:53:30","end_time":"2017-07-10 17:53:30"}
             */

            private String query_type;
            private QueryWayBean query_way;
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
                /**
                 * start_time : 2017-06-20 16:53:30
                 * end_time : 2017-07-10 17:53:30
                 */

                private String start_time;
                private String end_time;
                private String customer_id;
                private String fees_down;
                private String balance_down;

                public String getBalance_down() {
                    return balance_down;
                }

                public void setBalance_down(String balance_down) {
                    this.balance_down = balance_down;
                }

                public String getBalance_up() {
                    return balance_up;
                }

                public void setBalance_up(String balance_up) {
                    this.balance_up = balance_up;
                }

                private String balance_up;

                public String getFees_down() {
                    return fees_down;
                }

                public void setFees_down(String fees_down) {
                    this.fees_down = fees_down;
                }

                public String getFees_up() {
                    return fees_up;
                }

                public void setFees_up(String fees_up) {
                    this.fees_up = fees_up;
                }

                private String fees_up;

                public String getCustomer_id() {
                    return customer_id;
                }

                public void setCustomer_id(String customer_id) {
                    this.customer_id = customer_id;
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
