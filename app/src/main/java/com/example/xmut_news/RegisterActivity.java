package com.example.xmut_news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.xmut_news.base.BaseActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
/*
* 用户注册界面
* */
public class RegisterActivity extends BaseActivity {

    RequestParams params;//存放请求数据
    private String phone,password,password2,sex,school;
    //注册请求发送地址
    //private String url = "http://www.myweb-api.work:8080/android_PlayAround_ssm/addUser";//阿里云服务器
    private String url = "http://jp2wmy.natappfree.cc/android_PlayAround_ssm/addUser";//本地
    @ViewInject(R.id.et_user_name)
    private EditText et_user_name;
    @ViewInject(R.id.et_psw)
    private EditText et_psw;
    @ViewInject(R.id.et_psw_again)
    private EditText et_psw_again;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        x.view().inject(RegisterActivity.this);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params = new RequestParams();
                //获取用户注册信息
                phone = et_user_name.getText().toString().trim();
                password = et_psw.getText().toString().trim();
                password2 = et_psw_again.getText().toString().trim();
                school = et_user_school.getText().toString().trim();
                //判断注册信息是否满足
                if(phone.equals("")||password.equals("")||password2.equals("")||school.equals("")||sex.equals("")){
                    Toast.makeText(RegisterActivity.this, "输入的信息不完整", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(password2)){
                    Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }
                else{
                    params.put("phone",phone);
                    params.put("password",password);
                    params.put("sex",sex);
                    params.put("school",school);
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
