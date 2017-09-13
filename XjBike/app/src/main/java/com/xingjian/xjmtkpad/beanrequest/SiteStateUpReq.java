package com.xingjian.xjmtkpad.beanrequest;

import java.util.List;

/**
 * Created by thinkpad on 2017/9/13.
 */

public class SiteStateUpReq {

    /**
     * siteId : 59
     * cmd : 01
     * way : 1
     * sn : 0
     * data : {"type":"1","address":"济南","site_state":{"site_state":"0","pile_num":"1","vehicle_num":"1"},"pile_num":"1","piles":[{"pile_id":"1","pile_state":{"pile_state":"1","fault1":"1","fault2":"1"},"vehicle_id":"1","vehicle_state":"1"}]}
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
         * type : 1
         * address : 济南
         * site_state : {"site_state":"0","pile_num":"1","vehicle_num":"1"}
         * pile_num : 1
         * piles : [{"pile_id":"1","pile_state":{"pile_state":"1","fault1":"1","fault2":"1"},"vehicle_id":"1","vehicle_state":"1"}]
         */

        private String type;
        private String address;
        private SiteStateBean site_state;
        private String pile_num;
        private List<PilesBean> piles;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public SiteStateBean getSite_state() {
            return site_state;
        }

        public void setSite_state(SiteStateBean site_state) {
            this.site_state = site_state;
        }

        public String getPile_num() {
            return pile_num;
        }

        public void setPile_num(String pile_num) {
            this.pile_num = pile_num;
        }

        public List<PilesBean> getPiles() {
            return piles;
        }

        public void setPiles(List<PilesBean> piles) {
            this.piles = piles;
        }

        public static class SiteStateBean {
            /**
             * site_state : 0
             * pile_num : 1
             * vehicle_num : 1
             */

            private String site_state;
            private String pile_num;
            private String vehicle_num;

            public String getSite_state() {
                return site_state;
            }

            public void setSite_state(String site_state) {
                this.site_state = site_state;
            }

            public String getPile_num() {
                return pile_num;
            }

            public void setPile_num(String pile_num) {
                this.pile_num = pile_num;
            }

            public String getVehicle_num() {
                return vehicle_num;
            }

            public void setVehicle_num(String vehicle_num) {
                this.vehicle_num = vehicle_num;
            }
        }

        public static class PilesBean {
            /**
             * pile_id : 1
             * pile_state : {"pile_state":"1","fault1":"1","fault2":"1"}
             * vehicle_id : 1
             * vehicle_state : 1
             */

            private String pile_id;
            private PileStateBean pile_state;
            private String vehicle_id;
            private String vehicle_state;

            public String getPile_id() {
                return pile_id;
            }

            public void setPile_id(String pile_id) {
                this.pile_id = pile_id;
            }

            public PileStateBean getPile_state() {
                return pile_state;
            }

            public void setPile_state(PileStateBean pile_state) {
                this.pile_state = pile_state;
            }

            public String getVehicle_id() {
                return vehicle_id;
            }

            public void setVehicle_id(String vehicle_id) {
                this.vehicle_id = vehicle_id;
            }

            public String getVehicle_state() {
                return vehicle_state;
            }

            public void setVehicle_state(String vehicle_state) {
                this.vehicle_state = vehicle_state;
            }

            public static class PileStateBean {
                /**
                 * pile_state : 1
                 * fault1 : 1
                 * fault2 : 1
                 */

                private String pile_state;
                private String fault1;
                private String fault2;

                public String getPile_state() {
                    return pile_state;
                }

                public void setPile_state(String pile_state) {
                    this.pile_state = pile_state;
                }

                public String getFault1() {
                    return fault1;
                }

                public void setFault1(String fault1) {
                    this.fault1 = fault1;
                }

                public String getFault2() {
                    return fault2;
                }

                public void setFault2(String fault2) {
                    this.fault2 = fault2;
                }
            }
        }
    }
}
