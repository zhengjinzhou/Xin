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
     * activityList : [{"activityDetail":"尔特热温热","activityFee":"在线支付:￥110/人 现场支付:￥150/人","activityName":"4.15上海优质高校校友专场联谊","attendantNumber":0,"deadline":{"date":25,"day":0,"hours":11,"minutes":0,"month":2,"nanos":0,"seconds":0,"time":1521946800000,"timezoneOffset":-480,"year":118},"endTime":{"date":25,"day":0,"hours":18,"minutes":0,"month":2,"nanos":0,"seconds":0,"time":1521972000000,"timezoneOffset":-480,"year":118},"entryNumber":50,"id":1,"isFinish":1,"lastNumber":50,"miniUrl":"","mobile":"13631789659","photoUrl":"","place":"高塘石","reminder":"未亡人翁人","startTime":{"date":25,"day":0,"hours":14,"minutes":0,"month":2,"nanos":0,"seconds":0,"time":1521957600000,"timezoneOffset":-480,"year":118}}]
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
         * deadline : {"date":25,"day":0,"hours":11,"minutes":0,"month":2,"nanos":0,"seconds":0,"time":1521946800000,"timezoneOffset":-480,"year":118}
         * endTime : {"date":25,"day":0,"hours":18,"minutes":0,"month":2,"nanos":0,"seconds":0,"time":1521972000000,"timezoneOffset":-480,"year":118}
         * entryNumber : 50
         * id : 1
         * isFinish : 1
         * lastNumber : 50
         * miniUrl :
         * mobile : 13631789659
         * photoUrl :
         * place : 高塘石
         * reminder : 未亡人翁人
         * startTime : {"date":25,"day":0,"hours":14,"minutes":0,"month":2,"nanos":0,"seconds":0,"time":1521957600000,"timezoneOffset":-480,"year":118}
         */

        private String activityDetail;
        private String activityFee;
        private String activityName;
        private int attendantNumber;
        private DeadlineBean deadline;
        private EndTimeBean endTime;
        private int entryNumber;
        private int id;
        private int isFinish;
        private int lastNumber;
        private String miniUrl;
        private String mobile;
        private String photoUrl;
        private String place;
        private String reminder;
        private StartTimeBean startTime;

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

        public DeadlineBean getDeadline() {
            return deadline;
        }

        public void setDeadline(DeadlineBean deadline) {
            this.deadline = deadline;
        }

        public EndTimeBean getEndTime() {
            return endTime;
        }

        public void setEndTime(EndTimeBean endTime) {
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

        public StartTimeBean getStartTime() {
            return startTime;
        }

        public void setStartTime(StartTimeBean startTime) {
            this.startTime = startTime;
        }

        public static class DeadlineBean {
            /**
             * date : 25
             * day : 0
             * hours : 11
             * minutes : 0
             * month : 2
             * nanos : 0
             * seconds : 0
             * time : 1521946800000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }

        public static class EndTimeBean {
            /**
             * date : 25
             * day : 0
             * hours : 18
             * minutes : 0
             * month : 2
             * nanos : 0
             * seconds : 0
             * time : 1521972000000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }

        public static class StartTimeBean {
            /**
             * date : 25
             * day : 0
             * hours : 14
             * minutes : 0
             * month : 2
             * nanos : 0
             * seconds : 0
             * time : 1521957600000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }

            @Override
            public String toString() {
                return "StartTimeBean{" +
                        "date=" + date +
                        ", day=" + day +
                        ", hours=" + hours +
                        ", minutes=" + minutes +
                        ", month=" + month +
                        ", nanos=" + nanos +
                        ", seconds=" + seconds +
                        ", time=" + time +
                        ", timezoneOffset=" + timezoneOffset +
                        ", year=" + year +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ActivityListBean{" +
                    "activityDetail='" + activityDetail + '\'' +
                    ", activityFee='" + activityFee + '\'' +
                    ", activityName='" + activityName + '\'' +
                    ", attendantNumber=" + attendantNumber +
                    ", deadline=" + deadline +
                    ", endTime=" + endTime +
                    ", entryNumber=" + entryNumber +
                    ", id=" + id +
                    ", isFinish=" + isFinish +
                    ", lastNumber=" + lastNumber +
                    ", miniUrl='" + miniUrl + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", photoUrl='" + photoUrl + '\'' +
                    ", place='" + place + '\'' +
                    ", reminder='" + reminder + '\'' +
                    ", startTime=" + startTime +
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
