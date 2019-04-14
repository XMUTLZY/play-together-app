package com.example.xmut_news.pager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.example.xmut_news.R;
import com.example.xmut_news.base.BasePager;
import com.example.xmut_news.pojo.UserRelease;
import com.example.xmut_news.utils.MyImageLoader;
import com.example.xmut_news.utils.MyProperUtil;
import com.example.xmut_news.utils.MyRecyclerViewAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HomePager extends BasePager implements OnBannerListener {
    private String url1, url, url2;
    private Banner mBanner;
    private MyImageLoader mMyImageLoader;
    private ArrayList<Integer> imagePath;
    private ArrayList<String> imageTitle;
    private RecyclerView release_list;
    private List<UserRelease> releases_datas = new ArrayList<>();//发布信息数据

    public HomePager(Context context) {
        super(context);
        Properties properties = MyProperUtil.getProperties(context);
        url1 = properties.getProperty("serverUrl");
        url = url1 + "listUserRelease";
        url2 = url1 + "listUserJoinAll";
        RadioGroup radioGroup = getRootView().findViewById(R.id.radio_group);
        radioGroup.check(R.id.title_button1);
        initData1();
        initView1();
    }

    @Override
    public View initView() {
        view = view.inflate(context, R.layout.home_pager, null);
        release_list = getRootView().findViewById(R.id.release_list);
        return view;
    }

    /*
     * 准备数据
     * */
    @Override
    public void initData() {
    }

    private void initView1() {
        mMyImageLoader = new MyImageLoader();
        mBanner = (Banner) this.getRootView().findViewById(R.id.banner);
        //设置样式，里面有很多种样式可以自己都看看效果
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(mMyImageLoader);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //轮播图片的文字
        mBanner.setBannerTitles(imageTitle);
        //设置轮播间隔时间
        mBanner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        mBanner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载地址
        mBanner.setImages(imagePath)
                //轮播图的监听
                .setOnBannerListener(this)
                //开始调用的方法，启动轮播图。
                .start();
    }

    /**
     * 轮播图的监听
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {

    }

    private void initData1() {
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        imagePath.add(R.drawable.banner1);
        imagePath.add(R.drawable.banner2);
        imagePath.add(R.drawable.banner3);
        imageTitle.add("我是轮播1号");
        imageTitle.add("我是轮播2号");
        imageTitle.add("我是轮播3号");
        //从后台获取所有发布信息的数据
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context, url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                releases_datas = JSONObject.parseArray(s, UserRelease.class);
                //添加RecyclerView的适配器
                release_list.setAdapter(new MyRecyclerViewAdapter(getRootView().getContext(), releases_datas));
                release_list.setLayoutManager(new LinearLayoutManager(getRootView().getContext(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Toast.makeText(context, "数据请求异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
