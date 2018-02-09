package com.zhou.xin.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by zhou on 2018/2/9.
 */

public class ProvinceBean implements IPickerViewData {
    private long id;
    private String name;

    public ProvinceBean(long id,String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过getPickerViewText方法获取字符串显示出来。
    @Override
    public String getPickerViewText() {
        return name;
    }
}