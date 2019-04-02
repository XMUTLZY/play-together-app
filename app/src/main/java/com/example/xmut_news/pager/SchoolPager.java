package com.example.xmut_news.pager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.xmut_news.base.BasePager;

public class SchoolPager extends BasePager {
    public SchoolPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("校圈");
        return textView;
    }

    @Override
    public void initData() {

    }
}
