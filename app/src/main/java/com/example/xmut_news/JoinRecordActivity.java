package com.example.xmut_news;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.example.xmut_news.pojo.UserJoin;
import com.example.xmut_news.utils.JoinRecyclerViewAdapter;
import com.example.xmut_news.utils.MyProperUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
* 活动申请记录情况
* */
public class JoinRecordActivity extends AppCompatActivity {

    private RadioGroup radio_group;
    private RecyclerView recyclerView1,recyclerView2,recyclerView3;
    private List<UserJoin> userJoinList = new ArrayList<>();
    private List<UserJoin> noPassList = new ArrayList<>();
    private List<UserJoin> applicationList = new ArrayList<>();
    private List<UserJoin> passList = new ArrayList<>();
    private String url1,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_record);
        getSupportActionBar().hide();
        Properties properties = MyProperUtil.getProperties(this);
        url1 = properties.getProperty("serverUrl");
        url = url1 + "getUserJoinAll";
        initData();
        //设置默认选中首页
        radio_group.check(R.id.join_record_button1);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.join_record_button1:
                        recyclerView1.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.GONE);
                        recyclerView3.setVisibility(View.GONE);
                        break;
                    case R.id.join_record_button2:
                        recyclerView1.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.VISIBLE);
                        recyclerView3.setVisibility(View.GONE);
                        break;
                    case R.id.join_record_button3:
                        recyclerView1.setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.GONE);
                        recyclerView3.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
    //准备数据
    private void initData(){
        //实例化
        recyclerView1 = findViewById(R.id.nopass);
        recyclerView2 = findViewById(R.id.application);
        recyclerView3 = findViewById(R.id.pass);
        radio_group = findViewById(R.id.radio_group);
        //获取用户申请加入的所有信息
        RequestParams params = new RequestParams();
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String phone = sp.getString("phone","");
        params.put("phone",phone);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(JoinRecordActivity.this,url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                UserJoin userJoin = new UserJoin();
                //json转化成list
                userJoinList = JSONObject.parseArray(s, UserJoin.class);
                //将list分成三种情况(未通过，申请中，已通过)
                for(int i = 0;i<userJoinList.size();i++){
                    if(userJoinList.get(i).getState().equals("申请中")){
                        userJoin = userJoinList.get(i);
                        applicationList.add(userJoin);
                    }
                    if(userJoinList.get(i).getState().equals("未通过")){
                        userJoin = userJoinList.get(i);
                        noPassList.add(userJoin);
                    }
                    if(userJoinList.get(i).getState().equals("通过")){
                        userJoin = userJoinList.get(i);
                        passList.add(userJoin);
                    }
                }
                //添加适配器
                recyclerView1.setAdapter(new JoinRecyclerViewAdapter(JoinRecordActivity.this,noPassList));
                recyclerView1.setLayoutManager(new LinearLayoutManager(JoinRecordActivity.this,LinearLayoutManager.VERTICAL,false));
                recyclerView2.setAdapter(new JoinRecyclerViewAdapter(JoinRecordActivity.this,applicationList));
                recyclerView2.setLayoutManager(new LinearLayoutManager(JoinRecordActivity.this,LinearLayoutManager.VERTICAL,false));
                recyclerView3.setAdapter(new JoinRecyclerViewAdapter(JoinRecordActivity.this,passList));
                recyclerView3.setLayoutManager(new LinearLayoutManager(JoinRecordActivity.this,LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onFailure(Throwable throwable, String s) {

            }
        });
    }
}
