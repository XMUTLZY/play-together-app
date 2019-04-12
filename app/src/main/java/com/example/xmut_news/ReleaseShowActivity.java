package com.example.xmut_news;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xmut_news.pojo.UserRelease;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
* item详情页
* */
public class ReleaseShowActivity extends AppCompatActivity {
    private UserRelease userRelease;
    private ImageView image;
    private TextView title,name,detail,time,address,chat;
    private Button joinButton;
    private String url = "http://i29kvi.natappfree.cc/android_PlayAround_ssm/addUserJoin";
    private String url2 = "http://i29kvi.natappfree.cc/android_PlayAround_ssm/getUserJoin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_show);
        getSupportActionBar().hide();
        initData();
    }
    private void initData(){
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
        //判断用户是否对该活动提出申请
        RequestParams params = new RequestParams();
        SharedPreferences sp = getSharedPreferences("user_info",Context.MODE_PRIVATE);
        params.put("phone",sp.getString("phone",""));
        params.put("release_name",userRelease.getName());
        params.put("release_title",userRelease.getTitle());
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ReleaseShowActivity.this,url2,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                if(s.equals("true")){
                    //设置按钮样式
                    joinButton.setText("申请已提交");
                    joinButton.setEnabled(false);
                    joinButton.setBackgroundColor(Color.parseColor("#22000000"));
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Toast.makeText(ReleaseShowActivity.this, "系统出故障了！", Toast.LENGTH_SHORT).show();
            }
        });
        //设置"申请加入"按钮的监听器
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否登录过
                if(isLogin()){
                    //弹出申请加入对话框
                    joinDialog();
                }else{
                    //没有登录过则进行登录操作
                    doLogin();
                }
            }
        });
    }
    /*
    * 提示是否加入对话框
    * */
    private void joinDialog(){
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要申请加入该活动吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        //存放用户申请加入活动的信息
                        RequestParams params = new RequestParams();
                        SharedPreferences sp = getSharedPreferences("user_info",Context.MODE_PRIVATE);
                        params.put("phone",sp.getString("phone",""));
                        params.put("name",sp.getString("name",""));
                        params.put("release_title",userRelease.getTitle());
                        params.put("release_name",userRelease.getName());
                        params.put("release_time",userRelease.getTime());
                        params.put("release_address",userRelease.getAddress());
                        params.put("release_phone",userRelease.getPhone());
                        params.put("state","申请中");
                        //发送用户申请加入活动的信息到后台中存储
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post(ReleaseShowActivity.this,url,params,new AsyncHttpResponseHandler(){
                            @Override
                            public void onSuccess(String s) {
                                if(s.equals("true")){
                                    //设置按钮样式
                                    joinButton.setText("申请已提交");
                                    joinButton.setEnabled(false);
                                    joinButton.setBackgroundColor(Color.parseColor("#22000000"));
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable, String s) {
                                Toast.makeText(ReleaseShowActivity.this, "系统出故障了！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }
    /*
    * 判断用户是否登录
    * */
    private Boolean isLogin(){
        //查看本地是否用用于的登录信息
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String phone = sp.getString("phone","");
        if(TextUtils.isEmpty(phone)){
            //本地没有保存过用户信息，给出提示：登录操作
            return false;
        }else{
            //已经登录过
            return true;
        }
    }
    /*
     * 给出登录提示
     * */
    private void doLogin() {
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您还没有登录哦")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(ReleaseShowActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setCancelable(false)
                .show();
    }
}
