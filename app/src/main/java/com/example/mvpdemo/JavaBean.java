package com.example.mvpdemo;

import com.mvp.model.IModel;

/**
 * Created by jack06.li on 2019-08-26.
 */
public class JavaBean implements IModel {

    private String phone;

    public JavaBean(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
