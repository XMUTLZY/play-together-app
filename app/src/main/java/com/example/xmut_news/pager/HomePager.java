package com.example.xmut_news.pager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.xmut_news.base.BasePager;

public class HomePager extends BasePager {
    public HomePager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(context);
        textView.setText("首页");
        return textView;
    }

    @Override
    public void initData() {

    }
}
