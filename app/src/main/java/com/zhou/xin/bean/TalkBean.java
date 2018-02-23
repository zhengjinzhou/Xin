package com.zhou.xin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhou on 2018/2/6.
 */

public class TalkBean implements Serializable {

    /**
     * error : -1
     * talkList : [{"code":1,"content":"adfasdf","iconUrl":"/upload/file/mini/201802/02170944sqo4.png","id":2,"mobile":"13631789659","nickName":"你提","publish_time":"2018-02-05 11:00:39","tapTimes":0,"tpSet":[{"id":4,"miniPhotoUrl":"/upload/app/talk/file/photo/201802/05110039r7bp.jpg","photoUrl":"/upload/app/talk/file/photo/201802/05110039tn74.jpg"}],"video_mini":"","video_url":""},{"code":2,"content":"哈哈哈","iconUrl":"/upload/file/mini/201802/02170944sqo4.png","id":1,"mobile":"13631789659","nickName":"你提","publish_time":"2017-08-17 16:05:35","tapTimes":4,"tpSet":[],"video_mini":"/upload/app/talk/file/video/photo/201802/05174053z975.jpg","video_url":"/upload/app/talk/file/video/201802/05174053mvhe.mp4"}]
     * msg : 获取成功
     */


    private String error;
    private String msg;
    private List<TalkListBean> talkList;

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

    public List<TalkListBean> getTalkList() {
        return talkList;
    }

    public void setTalkList(List<TalkListBean> talkList) {
        this.talkList = talkList;
    }

    public static class TalkListBean {
        /**
         * code : 1
         * content : adfasdf
         * iconUrl : /upload/file/mini/201802/02170944sqo4.png
         * id : 2
         * mobile : 13631789659
         * nickName : 你提
         * publish_time : 2018-02-05 11:00:39
         * tapTimes : 0
         * tpSet : [{"id":4,"miniPhotoUrl":"/upload/app/talk/file/photo/201802/05110039r7bp.jpg","photoUrl":"/upload/app/talk/file/photo/201802/05110039tn74.jpg"}]
         * video_mini :
         * video_url :
         */

        private int istap;
        private int code;
        private String content;
        private String iconUrl;
        private int id;
        private String mobile;
        private String nickName;
        private String publish_time;
        private int tapTimes;
        private String video_mini;
        private String video_url;
        private List<TpSetBean> tpSet;

        public int getIstap() {
            return istap;
        }

        public void setIstap(int istap) {
            this.istap = istap;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public int getTapTimes() {
            return tapTimes;
        }

        public void setTapTimes(int tapTimes) {
            this.tapTimes = tapTimes;
        }

        public String getVideo_mini() {
            return video_mini;
        }

        public void setVideo_mini(String video_mini) {
            this.video_mini = video_mini;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public List<TpSetBean> getTpSet() {
            return tpSet;
        }

        public void setTpSet(List<TpSetBean> tpSet) {
            this.tpSet = tpSet;
        }

        public static class TpSetBean {
            /**
             * id : 4
             * miniPhotoUrl : /upload/app/talk/file/photo/201802/05110039r7bp.jpg
             * photoUrl : /upload/app/talk/file/photo/201802/05110039tn74.jpg
             */

            private int id;
            private String miniPhotoUrl;
            private String photoUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMiniPhotoUrl() {
                return miniPhotoUrl;
            }

            public void setMiniPhotoUrl(String miniPhotoUrl) {
                this.miniPhotoUrl = miniPhotoUrl;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }

            @Override
            public String toString() {
                return "TpSetBean{" +
                        "id=" + id +
                        ", miniPhotoUrl='" + miniPhotoUrl + '\'' +
                        ", photoUrl='" + photoUrl + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "TalkListBean{" +
                    "istap=" + istap +
                    ", code=" + code +
                    ", content='" + content + '\'' +
                    ", iconUrl='" + iconUrl + '\'' +
                    ", id=" + id +
                    ", mobile='" + mobile + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", publish_time='" + publish_time + '\'' +
                    ", tapTimes=" + tapTimes +
                    ", video_mini='" + video_mini + '\'' +
                    ", video_url='" + video_url + '\'' +
                    ", tpSet=" + tpSet +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TalkBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", talkList=" + talkList +
                '}';
    }
}
