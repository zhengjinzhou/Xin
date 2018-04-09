package com.zhou.xin.bean;

import java.util.List;

/**
 * Created by zhou on 2018/4/9.
 */

public class MyWinerBean {

    /**
     * winnerList : [{"id":1,"isReceive":1,"prize":{"id":1,"imgUrl":"/upload/file/201803/072106165i2s.jpg","mini_url":"/upload/file/mini/201803/07210616asrj.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},"receiveTime":"2018-03-07 15:32:07","winTime":"2018-03-07 15:32:07"},{"id":2,"isReceive":1,"prize":{"id":1,"imgUrl":"/upload/file/201803/072106165i2s.jpg","mini_url":"/upload/file/mini/201803/07210616asrj.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},"receiveTime":"2018-03-07 15:53:33","winTime":"2018-03-07 15:53:33"},{"id":3,"isReceive":1,"prize":{"id":1,"imgUrl":"/upload/file/201803/072106165i2s.jpg","mini_url":"/upload/file/mini/201803/07210616asrj.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3},"receiveTime":"2018-03-07 16:22:38","winTime":"2018-03-07 16:22:38"},{"id":4,"isReceive":1,"prize":{"id":4,"imgUrl":"/upload/file/201803/072106165i2s.jpg","mini_url":"/upload/file/mini/201803/07210616asrj.jpg","prizeName":"电影票两张","prizeNumber":2},"receiveTime":"2018-04-09 08:48:04","winTime":"2018-04-09 08:48:04"}]
     * error : -1
     * msg : 获取成功
     */

    private String error;
    private String msg;
    private List<WinnerListBean> winnerList;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WinnerListBean> getWinnerList() {
        return winnerList;
    }

    public void setWinnerList(List<WinnerListBean> winnerList) {
        this.winnerList = winnerList;
    }

    public static class WinnerListBean {
        /**
         * id : 1
         * isReceive : 1
         * prize : {"id":1,"imgUrl":"/upload/file/201803/072106165i2s.jpg","mini_url":"/upload/file/mini/201803/07210616asrj.jpg","prizeName":"王者荣耀皮肤","prizeNumber":3}
         * receiveTime : 2018-03-07 15:32:07
         * winTime : 2018-03-07 15:32:07
         */

        private int id;
        private int isReceive;
        private PrizeBean prize;
        private String receiveTime;
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

        public String getWinTime() {
            return winTime;
        }

        public void setWinTime(String winTime) {
            this.winTime = winTime;
        }

        public static class PrizeBean {
            /**
             * id : 1
             * imgUrl : /upload/file/201803/072106165i2s.jpg
             * mini_url : /upload/file/mini/201803/07210616asrj.jpg
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
            return "WinnerListBean{" +
                    "id=" + id +
                    ", isReceive=" + isReceive +
                    ", prize=" + prize +
                    ", receiveTime='" + receiveTime + '\'' +
                    ", winTime='" + winTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MyWinerBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", winnerList=" + winnerList +
                '}';
    }
}
