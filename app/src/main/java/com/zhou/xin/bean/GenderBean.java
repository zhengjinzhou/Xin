package com.zhou.xin.bean;

import java.util.List;

/**
 * Created by zhou on 2018/1/15.
 * 异性信息
 */

public class GenderBean {


    /**
     * error : -1
     * memberList : [{"age":23,"autograph":"","birthday":"1995-06-12","book_others":"","books":"古龙","city":{"id":22,"name":"福州市","priority":0,"province":{"id":2,"name":"福建省","priority":2}},"constellation":"金牛座","film_others":"","films":"喜剧,惊悚","food_others":"","foods":"卤肉饭,生煎包","gender":0,"hashCode":-2147483648,"id":3,"label_others":"","labels":"健谈,局气,热血,叛逆,安静","major":{"hashCode":-2147483648,"id":6,"majorName":"研究生","priority":0},"memberId":3,"miniPhotoPath":"/upload/file/mini/201804/06133117uims.jpg","music":"R&B,金属,嘻哈","music_others":"","nickname":"飞翔","pair_id":0,"phone":"13631784759","photoPath":"/upload/file/201804/06133117jqpb.jpg","pictures":[{"miniUrl":"/upload/file/mini/201804/06133117uims.jpg","photoUrl":"/upload/file/201804/06133117jqpb.jpg","pictureId":34},{"miniUrl":"/upload/file/mini/201804/061331176jw2.jpg","photoUrl":"/upload/file/201804/06133117ev0i.jpg","pictureId":35}],"realname":"成龙","sport_others":"","sports":"健身房,拳击,跆拳道,足球","thumbs_up":0,"travel_others":"","travels":"","wechat":""},{"age":25,"autograph":"哈哈哈","birthday":"1995-06-12","book_others":"","books":"古龙","city":{"id":23,"name":"厦门市","priority":0,"province":{"id":2,"name":"福建省","priority":2}},"constellation":"狮子座","film_others":"","films":"喜剧,惊悚","food_others":"","foods":"卤肉饭,生煎包,北京烤鸭","gender":0,"hashCode":-2147483648,"id":4,"label_others":"","labels":"健谈,局气,热血,叛逆,安静","major":{"hashCode":-2147483648,"id":6,"majorName":"研究生","priority":0},"memberId":4,"miniPhotoPath":"/upload/file/mini/201804/06133143ym1s.jpg","music":"R&B,金属,嘻哈","music_others":"","nickname":"敖强","pair_id":0,"phone":"13631784659","photoPath":"/upload/file/201804/06133143xidc.jpg","pictures":[{"miniUrl":"/upload/file/mini/201804/06133143ym1s.jpg","photoUrl":"/upload/file/201804/06133143xidc.jpg","pictureId":36},{"miniUrl":"/upload/file/mini/201804/06133143464p.jpg","photoUrl":"/upload/file/201804/06133143jsfa.jpg","pictureId":37},{"miniUrl":"/upload/file/mini/201712/28162438pqfl.jpg","photoUrl":"/upload/file 04-09 10:39:08.917 10593-10760/com.zhou.xin D/LogUtil1: /201712/28162438psm7.jpg","pictureId":38}],"realname":"吴亦凡","sport_others":"","sports":"健身房,拳击,跆拳道,足球","thumbs_up":0,"travel_others":"","travels":"桂林,成都","wechat":"12563644"}]
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
         * age : 23
         * autograph :
         * birthday : 1995-06-12
         * book_others :
         * books : 古龙
         * city : {"id":22,"name":"福州市","priority":0,"province":{"id":2,"name":"福建省","priority":2}}
         * constellation : 金牛座
         * film_others :
         * films : 喜剧,惊悚
         * food_others :
         * foods : 卤肉饭,生煎包
         * gender : 0
         * hashCode : -2147483648
         * id : 3
         * label_others :
         * labels : 健谈,局气,热血,叛逆,安静
         * major : {"hashCode":-2147483648,"id":6,"majorName":"研究生","priority":0}
         * memberId : 3
         * miniPhotoPath : /upload/file/mini/201804/06133117uims.jpg
         * music : R&B,金属,嘻哈
         * music_others :
         * nickname : 飞翔
         * pair_id : 0
         * phone : 13631784759
         * photoPath : /upload/file/201804/06133117jqpb.jpg
         * pictures : [{"miniUrl":"/upload/file/mini/201804/06133117uims.jpg","photoUrl":"/upload/file/201804/06133117jqpb.jpg","pictureId":34},{"miniUrl":"/upload/file/mini/201804/061331176jw2.jpg","photoUrl":"/upload/file/201804/06133117ev0i.jpg","pictureId":35}]
         * realname : 成龙
         * sport_others :
         * sports : 健身房,拳击,跆拳道,足球
         * thumbs_up : 0
         * travel_others :
         * travels :
         * wechat :
         */

        private int age;
        private String autograph;
        private String birthday;
        private String book_others;
        private String books;
        private CityBean city;
        private String constellation;
        private String film_others;
        private String films;
        private String food_others;
        private String foods;
        private int gender;
        private int hashCode;
        private int id;
        private String label_others;
        private String labels;
        private MajorBean major;
        private int memberId;
        private String miniPhotoPath;
        private String music;
        private String music_others;
        private String nickname;
        private int pair_id;
        private String phone;
        private String photoPath;
        private String realname;
        private String sport_others;
        private String sports;
        private int thumbs_up;
        private String travel_others;
        private String travels;
        private String wechat;
        private List<PicturesBean> pictures;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBook_others() {
            return book_others;
        }

        public void setBook_others(String book_others) {
            this.book_others = book_others;
        }

        public String getBooks() {
            return books;
        }

        public void setBooks(String books) {
            this.books = books;
        }

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public String getFilm_others() {
            return film_others;
        }

        public void setFilm_others(String film_others) {
            this.film_others = film_others;
        }

        public String getFilms() {
            return films;
        }

        public void setFilms(String films) {
            this.films = films;
        }

        public String getFood_others() {
            return food_others;
        }

        public void setFood_others(String food_others) {
            this.food_others = food_others;
        }

        public String getFoods() {
            return foods;
        }

        public void setFoods(String foods) {
            this.foods = foods;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getHashCode() {
            return hashCode;
        }

        public void setHashCode(int hashCode) {
            this.hashCode = hashCode;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel_others() {
            return label_others;
        }

        public void setLabel_others(String label_others) {
            this.label_others = label_others;
        }

        public String getLabels() {
            return labels;
        }

        public void setLabels(String labels) {
            this.labels = labels;
        }

        public MajorBean getMajor() {
            return major;
        }

        public void setMajor(MajorBean major) {
            this.major = major;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getMiniPhotoPath() {
            return miniPhotoPath;
        }

        public void setMiniPhotoPath(String miniPhotoPath) {
            this.miniPhotoPath = miniPhotoPath;
        }

        public String getMusic() {
            return music;
        }

        public void setMusic(String music) {
            this.music = music;
        }

        public String getMusic_others() {
            return music_others;
        }

        public void setMusic_others(String music_others) {
            this.music_others = music_others;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getPair_id() {
            return pair_id;
        }

        public void setPair_id(int pair_id) {
            this.pair_id = pair_id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPhotoPath() {
            return photoPath;
        }

        public void setPhotoPath(String photoPath) {
            this.photoPath = photoPath;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSport_others() {
            return sport_others;
        }

        public void setSport_others(String sport_others) {
            this.sport_others = sport_others;
        }

        public String getSports() {
            return sports;
        }

        public void setSports(String sports) {
            this.sports = sports;
        }

        public int getThumbs_up() {
            return thumbs_up;
        }

        public void setThumbs_up(int thumbs_up) {
            this.thumbs_up = thumbs_up;
        }

        public String getTravel_others() {
            return travel_others;
        }

        public void setTravel_others(String travel_others) {
            this.travel_others = travel_others;
        }

        public String getTravels() {
            return travels;
        }

        public void setTravels(String travels) {
            this.travels = travels;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public List<PicturesBean> getPictures() {
            return pictures;
        }

        public void setPictures(List<PicturesBean> pictures) {
            this.pictures = pictures;
        }

        public static class CityBean {
            /**
             * id : 22
             * name : 福州市
             * priority : 0
             * province : {"id":2,"name":"福建省","priority":2}
             */

            private int id;
            private String name;
            private int priority;
            private ProvinceBean province;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public ProvinceBean getProvince() {
                return province;
            }

            public void setProvince(ProvinceBean province) {
                this.province = province;
            }

            public static class ProvinceBean {
                /**
                 * id : 2
                 * name : 福建省
                 * priority : 2
                 */

                private int id;
                private String name;
                private int priority;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getPriority() {
                    return priority;
                }

                public void setPriority(int priority) {
                    this.priority = priority;
                }
            }
        }

        public static class MajorBean {
            /**
             * hashCode : -2147483648
             * id : 6
             * majorName : 研究生
             * priority : 0
             */

            private int hashCode;
            private int id;
            private String majorName;
            private int priority;

            public int getHashCode() {
                return hashCode;
            }

            public void setHashCode(int hashCode) {
                this.hashCode = hashCode;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMajorName() {
                return majorName;
            }

            public void setMajorName(String majorName) {
                this.majorName = majorName;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }
        }

        public static class PicturesBean {
            /**
             * miniUrl : /upload/file/mini/201804/06133117uims.jpg
             * photoUrl : /upload/file/201804/06133117jqpb.jpg
             * pictureId : 34
             */

            private String miniUrl;
            private String photoUrl;
            private int pictureId;

            public String getMiniUrl() {
                return miniUrl;
            }

            public void setMiniUrl(String miniUrl) {
                this.miniUrl = miniUrl;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }

            public int getPictureId() {
                return pictureId;
            }

            public void setPictureId(int pictureId) {
                this.pictureId = pictureId;
            }

            @Override
            public String toString() {
                return "PicturesBean{" +
                        "miniUrl='" + miniUrl + '\'' +
                        ", photoUrl='" + photoUrl + '\'' +
                        ", pictureId=" + pictureId +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "MemberListBean{" +
                    "age=" + age +
                    ", autograph='" + autograph + '\'' +
                    ", birthday='" + birthday + '\'' +
                    ", book_others='" + book_others + '\'' +
                    ", books='" + books + '\'' +
                    ", city=" + city +
                    ", constellation='" + constellation + '\'' +
                    ", film_others='" + film_others + '\'' +
                    ", films='" + films + '\'' +
                    ", food_others='" + food_others + '\'' +
                    ", foods='" + foods + '\'' +
                    ", gender=" + gender +
                    ", hashCode=" + hashCode +
                    ", id=" + id +
                    ", label_others='" + label_others + '\'' +
                    ", labels='" + labels + '\'' +
                    ", major=" + major +
                    ", memberId=" + memberId +
                    ", miniPhotoPath='" + miniPhotoPath + '\'' +
                    ", music='" + music + '\'' +
                    ", music_others='" + music_others + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", pair_id=" + pair_id +
                    ", phone='" + phone + '\'' +
                    ", photoPath='" + photoPath + '\'' +
                    ", realname='" + realname + '\'' +
                    ", sport_others='" + sport_others + '\'' +
                    ", sports='" + sports + '\'' +
                    ", thumbs_up=" + thumbs_up +
                    ", travel_others='" + travel_others + '\'' +
                    ", travels='" + travels + '\'' +
                    ", wechat='" + wechat + '\'' +
                    ", pictures=" + pictures +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GenderBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", memberList=" + memberList +
                '}';
    }
}
