package com.zhou.xin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhou on 2018/4/2.
 * 线下活动
 */

public class ActivityBean implements Serializable {

    /**
     * error : -3
     * activityList : [{"activityDetail":"尔特热温热","activityFee":"在线支付:￥110/人 现场支付:￥150/人","activityName":"4.15上海优质高校校友专场联谊","attendantNumber":0,"deadline":"2018-03-25 11:00:00","endTime":"2018-03-25 18:00:00","entryNumber":50,"id":1,"isFinish":1,"lastNumber":49,"miniUrl":"/upload/activity/file/mini/201804/02151146orwo.jpg","mobile":"13631789659","photoUrl":"/upload/activity/file/201804/02151146g4w2.jpg","place":"高塘石","reminder":"未亡人翁人","startTime":"2018-03-25 14:00:00"}]
     * msg : 获取成功
     */

    private String error;
    private String msg;
    private List<ActivityListBean> activityList;

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

    public List<ActivityListBean> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<ActivityListBean> activityList) {
        this.activityList = activityList;
    }

    public static class ActivityListBean {
        /**
         * activityDetail : 尔特热温热
         * activityFee : 在线支付:￥110/人 现场支付:￥150/人
         * activityName : 4.15上海优质高校校友专场联谊
         * attendantNumber : 0
         * deadline : 2018-03-25 11:00:00
         * endTime : 2018-03-25 18:00:00
         * entryNumber : 50
         * id : 1
         * isFinish : 1
         * lastNumber : 49
         * miniUrl : /upload/activity/file/mini/201804/02151146orwo.jpg
         * mobile : 13631789659
         * photoUrl : /upload/activity/file/201804/02151146g4w2.jpg
         * place : 高塘石
         * reminder : 未亡人翁人
         * startTime : 2018-03-25 14:00:00
         */

        private String activityDetail;
        private String activityFee;
        private String activityName;
        private int attendantNumber;
        private String deadline;
        private String endTime;
        private int entryNumber;
        private int id;
        private int isFinish;
        private int lastNumber;
        private String miniUrl;
        private String mobile;
        private String photoUrl;
        private String place;
        private String reminder;
        private String startTime;

        public String getActivityDetail() {
            return activityDetail;
        }

        public void setActivityDetail(String activityDetail) {
            this.activityDetail = activityDetail;
        }

        public String getActivityFee() {
            return activityFee;
        }

        public void setActivityFee(String activityFee) {
            this.activityFee = activityFee;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public int getAttendantNumber() {
            return attendantNumber;
        }

        public void setAttendantNumber(int attendantNumber) {
            this.attendantNumber = attendantNumber;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getEntryNumber() {
            return entryNumber;
        }

        public void setEntryNumber(int entryNumber) {
            this.entryNumber = entryNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsFinish() {
            return isFinish;
        }

        public void setIsFinish(int isFinish) {
            this.isFinish = isFinish;
        }

        public int getLastNumber() {
            return lastNumber;
        }

        public void setLastNumber(int lastNumber) {
            this.lastNumber = lastNumber;
        }

        public String getMiniUrl() {
            return miniUrl;
        }

        public void setMiniUrl(String miniUrl) {
            this.miniUrl = miniUrl;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getReminder() {
            return reminder;
        }

        public void setReminder(String reminder) {
            this.reminder = reminder;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        @Override
        public String toString() {
            return "ActivityListBean{" +
                    "activityDetail='" + activityDetail + '\'' +
                    ", activityFee='" + activityFee + '\'' +
                    ", activityName='" + activityName + '\'' +
                    ", attendantNumber=" + attendantNumber +
                    ", deadline='" + deadline + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", entryNumber=" + entryNumber +
                    ", id=" + id +
                    ", isFinish=" + isFinish +
                    ", lastNumber=" + lastNumber +
                    ", miniUrl='" + miniUrl + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", photoUrl='" + photoUrl + '\'' +
                    ", place='" + place + '\'' +
                    ", reminder='" + reminder + '\'' +
                    ", startTime='" + startTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ActivityBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", activityList=" + activityList +
                '}';
    }
}
