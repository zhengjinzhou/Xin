package com.zhou.xin.bean;

/**
 * Created by zhou on 2018/4/27.
 */

public class UpdateBean {

    /**
     * error : -1
     * msg : 获取版本更新成功
     * version : {"change_type":3,"down_url":"/apk/file/201803/272123125i4b.apk","id":6,"isValid":true,"note":"1;2;3;4","publish_time":{"date":27,"day":2,"hours":21,"minutes":23,"month":2,"nanos":0,"seconds":12,"time":1522156992000,"timezoneOffset":-480,"year":118},"version_id":"3.3.4"}
     */

    private String error;
    private String msg;
    private VersionBean version;

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

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public static class VersionBean {
        /**
         * change_type : 3
         * down_url : /apk/file/201803/272123125i4b.apk
         * id : 6
         * isValid : true
         * note : 1;2;3;4
         * publish_time : {"date":27,"day":2,"hours":21,"minutes":23,"month":2,"nanos":0,"seconds":12,"time":1522156992000,"timezoneOffset":-480,"year":118}
         * version_id : 3.3.4
         */

        private int change_type;
        private String down_url;
        private int id;
        private boolean isValid;
        private String note;
        private PublishTimeBean publish_time;
        private String version_id;

        public int getChange_type() {
            return change_type;
        }

        public void setChange_type(int change_type) {
            this.change_type = change_type;
        }

        public String getDown_url() {
            return down_url;
        }

        public void setDown_url(String down_url) {
            this.down_url = down_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsValid() {
            return isValid;
        }

        public void setIsValid(boolean isValid) {
            this.isValid = isValid;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public PublishTimeBean getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(PublishTimeBean publish_time) {
            this.publish_time = publish_time;
        }

        public String getVersion_id() {
            return version_id;
        }

        public void setVersion_id(String version_id) {
            this.version_id = version_id;
        }

        public static class PublishTimeBean {
            /**
             * date : 27
             * day : 2
             * hours : 21
             * minutes : 23
             * month : 2
             * nanos : 0
             * seconds : 12
             * time : 1522156992000
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
                return "PublishTimeBean{" +
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
            return "VersionBean{" +
                    "change_type=" + change_type +
                    ", down_url='" + down_url + '\'' +
                    ", id=" + id +
                    ", isValid=" + isValid +
                    ", note='" + note + '\'' +
                    ", publish_time=" + publish_time +
                    ", version_id='" + version_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpdateBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", version=" + version +
                '}';
    }
}
