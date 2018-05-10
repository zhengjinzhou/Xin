package com.zhou.xin.bean;

import java.util.List;

/**
 * Created by zhou on 2018/5/8.
 */

public class DianZanBean {

    /**
     * error : -1
     * iconList : ["/upload/file/mini/201805/01203834dasp.jpg"]
     * msg : 取消点赞成功
     */

    private String error;
    private String msg;
    private List<String> iconList;

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

    public List<String> getIconList() {
        return iconList;
    }

    public void setIconList(List<String> iconList) {
        this.iconList = iconList;
    }

    @Override
    public String toString() {
        return "DianZanBean{" +
                "error='" + error + '\'' +
                ", msg='" + msg + '\'' +
                ", iconList=" + iconList +
                '}';
    }
}
