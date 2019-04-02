package com.example.xmut_news;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xmut_news.fragment.ContextFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //初始化Fragment
        initFragment();
    }

    private void initFragment() {
        //1、得到FragmentManger
        FragmentManager fm = getSupportFragmentManager();
        //2、开启事务
        FragmentTransaction ft = fm.beginTransaction();
        //3、替换
        ft.replace(R.id.fl_main_context,new ContextFragment(),"main_content_tag");//主页
        //4、提交
        ft.commit();
    }
}
