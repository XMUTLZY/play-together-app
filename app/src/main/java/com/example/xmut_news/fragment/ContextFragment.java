package com.example.xmut_news.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.xmut_news.LoginActivity;
import com.example.xmut_news.R;
import com.example.xmut_news.base.BaseActivity;
import com.example.xmut_news.base.BaseFragment;
import com.example.xmut_news.base.BasePager;
import com.example.xmut_news.pager.MessagePager;
import com.example.xmut_news.pager.SchoolPager;
import com.example.xmut_news.pager.AddPager;
import com.example.xmut_news.pager.HomePager;
import com.example.xmut_news.pager.UserPager;
import com.example.xmut_news.pojo.User;
import com.example.xmut_news.view.NoScrollViewPager;
import com.example.xmut_news.view.PublishDialog;

import java.util.ArrayList;
import java.util.List;

public class ContextFragment extends BaseFragment {
    private String phone,password,name,image,school,sex,major;
    private NoScrollViewPager view_pager;
    private RadioGroup radio_group;
    private Button radio_group_button3;
    private PublishDialog publishDialog;
    //存放5个页面
    private List<BasePager> pagerList = new ArrayList<BasePager>();
    @Override
    public View initView() {
        View view = null;
        //用content_fragment替换主活动,context为主活动
        view = view.inflate(context, R.layout.content_fragment,null);
        //实例化
        view_pager = view.findViewById(R.id.view_pager);
        radio_group = view.findViewById(R.id.radio_group);
        radio_group_button3 = view.findViewById(R.id.radio_group_button3);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //设置默认选中首页
        radio_group.check(R.id.radio_group_button1);
        //通过数据适配器把ViewPager填充起来
        pagerList.clear();
        pagerList.add(new HomePager(context));
        pagerList.add(new MessagePager(context));
        pagerList.add(new AddPager(context));
        pagerList.add(new SchoolPager(context));
        pagerList.add(new UserPager(context));

        view_pager.setAdapter(new MyPagerAdapter());
        //页面选中监听器
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        radio_group.check(R.id.radio_group_button1);
                        break;
                    case 1:
                        radio_group.check(R.id.radio_group_button2);
                        break;
                    case 2:
                        radio_group.check(R.id.radio_group_button3);
                        break;
                    case 3:
                        radio_group.check(R.id.radio_group_button4);
                        break;
                    case 4:
                        radio_group.check(R.id.radio_group_button5);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //按钮点击监听器
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //通过点击不同的按钮显示不同的pager
                switch (checkedId){
                    case R.id.radio_group_button1:
                        view_pager.setCurrentItem(0,false);
                        break;
                    case R.id.radio_group_button2:
                        view_pager.setCurrentItem(1,false);
                        //判断用户是否已经登录
                        isLogin();
                        break;
                    case R.id.radio_group_button3:
                        //判断用户是否已经登录
                        isLogin();
                        click();
                        break;
                    case R.id.radio_group_button4:
                        view_pager.setCurrentItem(3,false);
                        //判断用户是否已经登录
                        isLogin();
                        break;
                    case R.id.radio_group_button5:
                        view_pager.setCurrentItem(4,false);
                        break;
                }
            }
        });
    }
    //pager适配器
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagerList.size();
        }
        /*
        * 谷歌官方推荐 默认view == o 即可
        * */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
        /*
        * 添加的每一项
        * */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(pagerList.get(position).getRootView());
            return pagerList.get(position).getRootView();
        }
        /*
        * 销毁
        * */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
    private void isLogin(){
        //查看本地是否用用于的登录信息
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        phone = sp.getString("phone","");
        if(TextUtils.isEmpty(phone)){
            //本地没有保存过用户信息，给出提示：登录操作
            doLogin();
        }else{
            //已经登录过
            Toast.makeText(context,phone , Toast.LENGTH_SHORT).show();
            //获取用户数据
            User user = getUser();
            pagerList.get(4).getRootView().findViewById(R.id.user_pager_under_under).setVisibility(View.INVISIBLE);
        }
    }
    /*
    * 给出登录提示
    * */
    private void doLogin() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示")
                .setMessage("您还没有登录哦")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setCancelable(false)
                .show();
    }
    public User getUser(){
        //获取用户数据
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        User user = new User();
        password = sp.getString("password","");
        name = sp.getString("name","");
        image = sp.getString("image","");
        sex = sp.getString("sex","");
        school = sp.getString("school","");
        major = sp.getString("major","");
        user.setName(name);
        user.setPassword(password);
        user.setImage(image);
        user.setSchool(school);
        user.setSex(sex);
        user.setMajor(major);
        return user;
    }
    /*
    * 发布事件
    * */
    private void click(){
        if (publishDialog==null){
            publishDialog=new PublishDialog(context);
            publishDialog.setFabuClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "发布", Toast.LENGTH_SHORT).show();
                }
            });
            publishDialog.setHuishouClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "回收", Toast.LENGTH_SHORT).show();

                }
            });
            publishDialog.setPingguClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "评估", Toast.LENGTH_SHORT).show();

                }
            });
        }
        publishDialog.show();
    }
}
