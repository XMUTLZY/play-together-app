package com.example.xmut_news;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.example.xmut_news.pojo.UserJoin;
import com.example.xmut_news.pojo.UserRelease;
import com.example.xmut_news.utils.JoinRecyclerViewAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

/*
* 活动申请记录情况
* */
public class JoinRecordActivity extends AppCompatActivity {

    private RadioGroup radio_group;
    private RecyclerView recyclerView1,recyclerView2,recyclerView3;
    private List<UserJoin> userJoinList = new ArrayList<>();
    private String url = "http://i29kvi.natappfree.cc/android_PlayAround_ssm/getUserJoinAll";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_record);
        getSupportActionBar().hide();
        initData();
    }
    //准备数据
    private void initData(){
        //实例化
        recyclerView1 = findViewById(R.id.nopass);
        recyclerView2 = findViewById(R.id.application);
        recyclerView3 = findViewById(R.id.pass);
        //获取用户申请加入的所有信息
        RequestParams params = new RequestParams();
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String phone = sp.getString("phone","");
        params.put("phone",phone);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(JoinRecordActivity.this,url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                //json转化成list
                userJoinList = JSONObject.parseArray(s, UserJoin.class);
                //添加适配器
                recyclerView1.setAdapter(new JoinRecyclerViewAdapter(JoinRecordActivity.this,userJoinList));
                recyclerView1.setLayoutManager(new LinearLayoutManager(JoinRecordActivity.this,LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onFailure(Throwable throwable, String s) {

            }
        });
    }
}
