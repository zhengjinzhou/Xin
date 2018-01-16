package com.zhou.xin.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhou on 2018/1/16.
 */

public class ReportBean {

    /**
     * error : -1
     * accusationCategoryList : [{"acTypes":[{"id":1,"priority":4,"typeName":"色情低俗"},{"id":2,"priority":10,"typeName":"政治敏感"}],"categoryName":"发布色情/政治/违法/暴恐内容","id":1,"priority":5},{"acTypes":[],"categoryName":"性骚扰/约炮","id":2,"priority":10},{"acTypes":[],"categoryName":"辱骂谩骂","id":3,"priority":10},{"acTypes":[],"categoryName":"资料作假","id":4,"priority":10},{"acTypes":[],"categoryName":"诈骗/托","id":5,"priority":10},{"acTypes":[{"id":3,"priority":3,"typeName":"骚扰广告"}],"categoryName":"骚扰广告","id":6,"priority":10}]
     * msg : 获取成功
     */

    private String error;
    private String msg;
    private List<AccusationCategoryListBean> accusationCategoryList;

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

    public List<AccusationCategoryListBean> getAccusationCategoryList() {
        return accusationCategoryList;
    }

    public void setAccusationCategoryList(List<AccusationCategoryListBean> accusationCategoryList) {
        this.accusationCategoryList = accusationCategoryList;
    }

    public static class AccusationCategoryListBean {
        /**
         * acTypes : [{"id":1,"priority":4,"typeName":"色情低俗"},{"id":2,"priority":10,"typeName":"政治敏感"}]
         * categoryName : 发布色情/政治/违法/暴恐内容
         * id : 1
         * priority : 5
         */

        private String categoryName;
        private int id;
        private int priority;
        private List<AcTypesBean> acTypes;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public List<AcTypesBean> getAcTypes() {
            return acTypes;
        }

        public void setAcTypes(List<AcTypesBean> acTypes) {
            this.acTypes = acTypes;
        }

        public static class AcTypesBean {
            /**
             * id : 1
             * priority : 4
             * typeName : 色情低俗
             */

            private int id;
            private int priority;
            private String typeName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            @Override
            public String toString() {
                return "AcTypesBean{" +
                        "id=" + id +
                        ", priority=" + priority +
                        ", typeName='" + typeName + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "AccusationCategoryListBean{" +
                    "categoryName='" + categoryName + '\'' +
                    ", id=" + id +
                    ", priority=" + priority +
                    ", acTypes=" + acTypes +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ReportBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", accusationCategoryList=" + accusationCategoryList +
                '}';
    }
}
