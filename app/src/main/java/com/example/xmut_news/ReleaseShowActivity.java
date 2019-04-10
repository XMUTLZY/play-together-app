package com.example.xmut_news;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xmut_news.pojo.UserRelease;

import java.util.ArrayList;
import java.util.List;

/*
* item详情页
* */
public class ReleaseShowActivity extends AppCompatActivity {
    private UserRelease userRelease;
    private ImageView image;
    private TextView title,name,detail,time,address,chat;
    private Button joinButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_show);
        getSupportActionBar().hide();
        initData();
    }
    public void initData(){
        Intent intent = getIntent();
        //实例化
        image = findViewById(R.id.release_image);
        title = findViewById(R.id.release_title);
        name = findViewById(R.id.release_name);
        detail = findViewById(R.id.release_detail);
        address = findViewById(R.id.release_address);
        time = findViewById(R.id.release_time);
        chat = findViewById(R.id.release_chat);
        joinButton = findViewById(R.id.release_button3);
        //获取上一个活动传来的对象
        userRelease = new UserRelease();
        userRelease = (UserRelease) intent.getSerializableExtra("userRelease");
        //根据上一个活动传来的数据设置详情内容
        Bitmap bitmap = BitmapFactory.decodeFile(userRelease.getImage());
        image.setImageBitmap(bitmap);
        title.setText(userRelease.getTitle());
        name.setText("发布者:"+userRelease.getName());
        detail.setText(userRelease.getDetail());
        address.setText("约见地址:"+userRelease.getAddress());
        chat.setText("联系方式:"+userRelease.getPhone());
        time.setText("具体时间:"+userRelease.getTime());
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
