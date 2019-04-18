package com.example.xmut_news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.xmut_news.base.BaseActivity;
import com.example.xmut_news.utils.MyProperUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Properties;
import java.util.Random;

/*
* 用户注册界面
* */
public class RegisterActivity extends BaseActivity {

    RequestParams params;//存放请求数据
    private String phone,make,password,password2,sex,school,name,major,url2 = "http://utf8.api.smschinese.cn/";
    private String url;//本地
    private String url1;
    private String code;
    @ViewInject(R.id.et_user_name)
    private EditText et_user_name;
    @ViewInject(R.id.et_make)
    private EditText et_make;
    @ViewInject(R.id.et_psw)
    private EditText et_psw;
    @ViewInject(R.id.et_psw_again)
    private EditText et_psw_again;
    @ViewInject(R.id.et_user_major)
    private EditText et_user_major;
    @ViewInject(R.id.et_user_trullyname)
    private EditText et_user_trullyname;
    @ViewInject(R.id.SexRadio)
    private RadioGroup sexRadio;
    @ViewInject(R.id.mainRegisterRdBtnFemale)
    private RadioButton mainRegisterRdBtnFemale;
    @ViewInject(R.id.mainRegisterRdBtnMale)
    private RadioButton mainRegisterRdBtnMale;
    @ViewInject(R.id.et_user_school)
    private EditText et_user_school;
    @ViewInject(R.id.btn_register)
    private Button btn_register;
    @ViewInject(R.id.btn_send)
    private Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        Properties properties = MyProperUtil.getProperties(this);
        url1 = properties.getProperty("serverUrl");
        url = url1 + "addUser";
        x.view().inject(RegisterActivity.this);
        //发送验证码监听
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //随机生成验证码数据
                int t = (int)(1000+Math.random()*(9999-1+1));
                code = Integer.toString(t);
                Log.i("code", code+"");
                //存放数据 调用sms短信api
                RequestParams params1 = new RequestParams();
                String phone = et_user_name.getText().toString().trim();
                params1.put("Uid","2410267884@qq.com");
                params1.put("Key","d41d8cd98f00b204e980");
                params1.put("smsText","验证码:"+code);
                params1.put("smsMob",phone);
                //发送数据请求到指定api
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(RegisterActivity.this,url2,params1,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(String s) {
                        if(s.equals("1")){
                            Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                        }
                        if(s.equals("-4")){
                            Toast.makeText(RegisterActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable, String s) {

                    }
                });
            }
        });
        //注册按钮监听
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = new RequestParams();
                //获取用户注册信息
                make = et_make.getText().toString().trim();
                Log.i("make", make);
                phone = et_user_name.getText().toString().trim();
                password = et_psw.getText().toString().trim();
                password2 = et_psw_again.getText().toString().trim();
                school = et_user_school.getText().toString().trim();
                name = et_user_trullyname.getText().toString().trim();
                major = et_user_major.getText().toString().trim();
                //判断注册信息是否满足
                if(phone.equals("")||password.equals("")||password2.equals("")||school.equals("")||sex.equals("")||name.equals("")||major.equals("")){
                    Toast.makeText(RegisterActivity.this, "输入的信息不完整", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(password2)){
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }
                else if(!make.equals(code)){
                    Toast.makeText(RegisterActivity.this, "短信验证码填写错误", Toast.LENGTH_SHORT).show();
                }
                else{
                    params.put("phone",phone);
                    params.put("password",password);
                    params.put("sex",sex);
                    params.put("school",school);
                    params.put("name",name);
                    params.put("major",major);
                    //注册用户
                    addUser(params);
                }
            }
        });
        //获取性别选择的结果
        sexRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = RegisterActivity.this.findViewById(sexRadio.getCheckedRadioButtonId());
                sex = rb.getText().toString();
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
        return R.layout.activity_register;
    }

    @Override
    protected void saveUserInfo(String s) {

    }

    //注册请求发送
    public void addUser(RequestParams params){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(this,url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                if(s.equals("true")){
                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Toast.makeText(RegisterActivity.this,"信息发送失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
