package com.zhou.xin.bean;

/**
 * Created by zhou on 2018/1/30.
 */

public class GuildBean{
    String title;
    String desc;

    public GuildBean(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}