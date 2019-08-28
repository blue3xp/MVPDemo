package com.example.mvpdemo;

import android.os.Bundle;

import com.mvp.databind.DataBindActivity;
import com.mvp.model.IModel;

public class MainActivity extends DataBindActivity<ViewDelegate> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }





    @Override
    protected Class<ViewDelegate> getDelegateClass() {
        return ViewDelegate.class;
    }


    @Override
    public IModel getDataBean() {
        return new JavaBean("123");
    }
}
