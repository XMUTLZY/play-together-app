package com.example.xmut_news.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        initTitle();
        initData();
    }
    protected abstract void initData();
    protected abstract void initTitle();
    protected abstract int getLayoutID();
    protected abstract void saveUserInfo(String s);
}
