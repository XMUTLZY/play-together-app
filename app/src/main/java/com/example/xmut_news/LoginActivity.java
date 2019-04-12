package com.example.xmut_news;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xmut_news.pojo.User;
import com.alibaba.fastjson.JSON;
import com.example.xmut_news.base.BaseActivity;
import com.example.xmut_news.utils.MyProperUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Properties;

public class LoginActivity extends BaseActivity {
    //登录请求发送地址
    //private String url = "http://www.myweb-api.work:8080/android_PlayAround_ssm/loginCheck";//阿里云
    private String url1;
    private String url;//本地
    RequestParams params;
    @ViewInject(R.id.login_button)
    private Button login_button;
    @ViewInject(R.id.register_button)
    private Button register_button;
    @ViewInject(R.id.login_user_edittext)
    private EditText login_user_edittext;
    @ViewInject(R.id.login_password_edittext)
    private EditText login_password_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        Properties properties = MyProperUtil.getProperties(this);
        url1 = properties.getProperty("serverUrl");
        url = url1 + "loginCheck";
        x.view().inject(LoginActivity.this);
        //登录按钮点击事件
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取登录文本框的手机号和密码
                String phone = login_user_edittext.getText().toString().trim();
                String password = login_password_edittext.getText().toString().trim();
                params = new RequestParams();
                params.put("phone",phone);
                params.put("password",password);
                //判断登录信息是否为空
                if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)){
                    loginCheck(params);
                }else{
                    Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    /*
    * 保存用户登录数据
    * */
    @Override
    protected void saveUserInfo(String s) {
        //获取到json数据 s
        //将json数据转化成对象
        User user = JSON.parseObject(s, User.class);
        //利用SharedPreferences存放用户数据
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        //向SharedPreferences写入数据需要使用editor
        SharedPreferences.Editor editor = sp.edit();
        //开始写入
        editor.putString("phone",user.getPhone());
        editor.putString("password",user.getPassword());
        editor.putString("name",user.getName());
        editor.putString("sex",user.getSex());
        editor.putString("school",user.getSchool());
        editor.putString("image",user.getImage());
        editor.putString("major",user.getMajor());
        //提交
        editor.apply();
    }

    /*
    * 登录验证
    * */
    public void loginCheck(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                //保存用户数据
                saveUserInfo(s);
                //如果s不是空的，说明从后台返回来的json数据存在
                if(!s.isEmpty()){
                    //输入信息正确
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //输入信息错误
                    Toast.makeText(LoginActivity.this,"用户名或密码错误" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                Toast.makeText(LoginActivity.this, "系统出了点问题，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
