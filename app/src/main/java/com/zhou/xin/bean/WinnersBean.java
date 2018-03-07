package com.zhou.xin.bean;

import java.util.List;

/**
 * Created by zhou on 2018/3/7.
 * 获取中奖列表
 */

public class WinnersBean {

    /**
     * error : -1
     * winnersList : [{"id":1,"isReceive":1,"prize":{"id":1,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},"receiveTime":"2018-03-07 15:32:07","username":"13631782148","winTime":"2018-03-07 15:32:07"},{"id":2,"isReceive":1,"prize":{"id":1,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},"receiveTime":"2018-03-07 15:53:33","username":"13631782148","winTime":"2018-03-07 15:53:33"},{"id":3,"isReceive":1,"prize":{"id":1,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},"receiveTime":"2018-03-07 16:22:38","username":"13631782148","winTime":"2018-03-07 16:22:38"}]
     * luckdraw : {"endTime":"2017-08-16 21:36:28","id":1,"luckDrawName":"五一活动","prizeSet":[{"id":1,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},{"id":2,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"积分500","prizeNumber":2},{"id":3,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"QQ会员优惠80%","prizeNumber":3},{"id":4,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"电影票两张","prizeNumber":2},{"id":5,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"省内旅游两张票","prizeNumber":2},{"id":6,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"购物券200元","prizeNumber":5},{"id":7,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"没中奖","prizeNumber":10}],"rules":"阿萨德成都市场上的第三方第三方水电费水电费第三方第三方是非得失","startTime":"2017-08-10 21:36:28"}
     * msg : 获取成功
     */

    private String error;
    private LuckdrawBean luckdraw;
    private String msg;
    private List<WinnersListBean> winnersList;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LuckdrawBean getLuckdraw() {
        return luckdraw;
    }

    public void setLuckdraw(LuckdrawBean luckdraw) {
        this.luckdraw = luckdraw;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WinnersListBean> getWinnersList() {
        return winnersList;
    }

    public void setWinnersList(List<WinnersListBean> winnersList) {
        this.winnersList = winnersList;
    }

    public static class LuckdrawBean {
        /**
         * endTime : 2017-08-16 21:36:28
         * id : 1
         * luckDrawName : 五一活动
         * prizeSet : [{"id":1,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},{"id":2,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"积分500","prizeNumber":2},{"id":3,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"QQ会员优惠80%","prizeNumber":3},{"id":4,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"电影票两张","prizeNumber":2},{"id":5,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"省内旅游两张票","prizeNumber":2},{"id":6,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"购物券200元","prizeNumber":5},{"id":7,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"没中奖","prizeNumber":10}]
         * rules : 阿萨德成都市场上的第三方第三方水电费水电费第三方第三方是非得失
         * startTime : 2017-08-10 21:36:28
         */

        private String endTime;
        private int id;
        private String luckDrawName;
        private String rules;
        private String startTime;
        private List<PrizeSetBean> prizeSet;

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLuckDrawName() {
            return luckDrawName;
        }

        public void setLuckDrawName(String luckDrawName) {
            this.luckDrawName = luckDrawName;
        }

        public String getRules() {
            return rules;
        }

        public void setRules(String rules) {
            this.rules = rules;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public List<PrizeSetBean> getPrizeSet() {
            return prizeSet;
        }

        public void setPrizeSet(List<PrizeSetBean> prizeSet) {
            this.prizeSet = prizeSet;
        }

        public static class PrizeSetBean {
            /**
             * id : 1
             * imgUrl : /upload/file/201803/03140759709m.jpg
             * mini_url : /upload/file/mini/201803/0314075963tv.jpg
             * prizeName : 王者荣耀皮肤
             * prizeNumber : 3
             */

            private int id;
            private String imgUrl;
            private String mini_url;
            private String prizeName;
            private int prizeNumber;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getMini_url() {
                return mini_url;
            }

            public void setMini_url(String mini_url) {
                this.mini_url = mini_url;
            }

            public String getPrizeName() {
                return prizeName;
            }

            public void setPrizeName(String prizeName) {
                this.prizeName = prizeName;
            }

            public int getPrizeNumber() {
                return prizeNumber;
            }

            public void setPrizeNumber(int prizeNumber) {
                this.prizeNumber = prizeNumber;
            }
        }
    }

    public static class WinnersListBean {
        /**
         * id : 1
         * isReceive : 1
         * prize : {"id":1,"imgUrl":"/upload/file/201803/03140759709m.jpg","mini_url":"/upload/file/mini/201803/0314075963tv.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3}
         * receiveTime : 2018-03-07 15:32:07
         * username : 13631782148
         * winTime : 2018-03-07 15:32:07
         */

        private int id;
        private int isReceive;
        private PrizeBean prize;
        private String receiveTime;
        private String username;
        private String winTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsReceive() {
            return isReceive;
        }

        public void setIsReceive(int isReceive) {
            this.isReceive = isReceive;
        }

        public PrizeBean getPrize() {
            return prize;
        }

        public void setPrize(PrizeBean prize) {
            this.prize = prize;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getWinTime() {
            return winTime;
        }

        public void setWinTime(String winTime) {
            this.winTime = winTime;
        }

        public static class PrizeBean {
            /**
             * id : 1
             * imgUrl : /upload/file/201803/03140759709m.jpg
             * mini_url : /upload/file/mini/201803/0314075963tv.jpg
             * prizeName : 王者荣耀皮肤
             * prizeNumber : 3
             */

            private int id;
            private String imgUrl;
            private String mini_url;
            private String prizeName;
            private int prizeNumber;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getMini_url() {
                return mini_url;
            }

            public void setMini_url(String mini_url) {
                this.mini_url = mini_url;
            }

            public String getPrizeName() {
                return prizeName;
            }

            public void setPrizeName(String prizeName) {
                this.prizeName = prizeName;
            }

            public int getPrizeNumber() {
                return prizeNumber;
            }

            public void setPrizeNumber(int prizeNumber) {
                this.prizeNumber = prizeNumber;
            }

            @Override
            public String toString() {
                return "PrizeBean{" +
                        "id=" + id +
                        ", imgUrl='" + imgUrl + '\'' +
                        ", mini_url='" + mini_url + '\'' +
                        ", prizeName='" + prizeName + '\'' +
                        ", prizeNumber=" + prizeNumber +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "WinnersListBean{" +
                    "id=" + id +
                    ", isReceive=" + isReceive +
                    ", prize=" + prize +
                    ", receiveTime='" + receiveTime + '\'' +
                    ", username='" + username + '\'' +
                    ", winTime='" + winTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WinnersBean{" +
                "error='" + error + '\'' +
                ", luckdraw=" + luckdraw +
                ", msg='" + msg + '\'' +
                ", winnersList=" + winnersList +
                '}';
    }
}
