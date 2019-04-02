package com.example.xmut_news.base;

import android.content.Context;
import android.view.View;

public abstract class BasePager {
    public View view;
    public Context context;
    public BasePager(Context context){
        //构建单曲界面展示效果，但是父类对子类未知
        //上下文环境需要给子类的initView()用
        this.context = context;
        view = initView();
    }
    public abstract View initView();
    public abstract void initData();
    //返回view getRootView
    public View getRootView(){
        return view;
    }
}
