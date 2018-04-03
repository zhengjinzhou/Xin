package com.zhou.xin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhou on 2018/4/3.
 */

public class BaomingBean implements Serializable {

    /**
     * error : -1
     * memberList : [{"age":0,"education":"小学","id":1,"memberName":"开心","url":"/upload/file/mini/201804/01145321zlkd.jpg"},{"age":0,"education":"研究生","id":2,"memberName":"啦啦","url":"/upload/file/mini/201804/011508183a3h.jpg"}]
     * msg : 获取成功
     */

    private String error;
    private String msg;
    private List<MemberListBean> memberList;

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

    public List<MemberListBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<MemberListBean> memberList) {
        this.memberList = memberList;
    }

    public static class MemberListBean {
        /**
         * age : 0
         * education : 小学
         * id : 1
         * memberName : 开心
         * url : /upload/file/mini/201804/01145321zlkd.jpg
         */

        private int age;
        private String education;
        private int id;
        private String memberName;
        private String url;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "MemberListBean{" +
                    "age=" + age +
                    ", education='" + education + '\'' +
                    ", id=" + id +
                    ", memberName='" + memberName + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaomingBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", memberList=" + memberList +
                '}';
    }
}
