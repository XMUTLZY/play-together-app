package com.example.xmut_news;

import android.app.Application;

import org.xutils.x;

public class MyApplication extends Application {
    //xutils初始化
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
