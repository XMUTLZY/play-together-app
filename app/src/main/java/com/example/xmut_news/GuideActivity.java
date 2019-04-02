package com.example.xmut_news;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.xmut_news.utils.CacheUtils;
import com.example.xmut_news.utils.DensityUtil;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_red_point;
    private int leftmax;//两点的间距
    private int widthdpi;//两个黑点的距离
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getSupportActionBar().hide();
        viewPager = findViewById(R.id.viewpager);
        btn_start_main = findViewById(R.id.btn_start_main);
        ll_point_group = findViewById(R.id.ll_point_group);
        iv_red_point = findViewById(R.id.iv_red_point);
        //准备数据
        int[] ids = new int[]{
                R.drawable.guide1,
                R.drawable.guide2,
                R.drawable.guide3
        };
        imageViews = new ArrayList<ImageView>();
        widthdpi = DensityUtil.dip2px(this,10);
        for(int i = 0;i<ids.length;i++){
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);

            //添加到集合中
            imageViews.add(imageView);

            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi,widthdpi);
            if(i!=0){
                //设置原点之间的边距
                params.leftMargin = widthdpi;
            }
            point.setLayoutParams(params);
            //添加到线性布局中
            ll_point_group.addView(point);
        }
        //设置适配器
        viewPager.setAdapter(new PagerAdapter() {
            //返回数据的总个数
            @Override
            public int getCount() {
                return imageViews.size();
            }
            /*
            * @return 返回和当前页面有关系的值
            * @param position 要创建页面的位置
            * @param container Viewpager
            * */
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
//                ImageView imageView = imageViews.get(position);
//                //添加到容器中
//                container.addView(imageView);
//                return imageView;
                container.addView(imageViews.get(position%imageViews.size()));
                return imageViews.get(position%imageViews.size());
            }
            /*
            * 判断
            * @param view 当前创建的视图
            * @param object 上面instantiateItem返回的值
            * */
            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }
            /*
            * 销毁页面
            * @param container ViewPager
            * @param position 要销毁页面的位置
            * @param object 要销毁的页面
            * */
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
                object=null;
            }
        });
        //根据View的生命周期，当视图执行到onLayout或者onDraw的时候，视图的高和宽，边距都有了
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                //执行不止一次
                iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                //间距计算
                leftmax = ll_point_group.getChildAt(1).getLeft()-ll_point_group.getChildAt(0).getLeft();
            }
        });
        //得到屏幕滑动的百分比
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /*
            * 页面滑动回调了这个方法
            * */
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //两点间移动的距离 = 屏幕滑动百分比*间距
                //两点间滑动距离对应的坐标=原来的起始位置 + 两点间移动的距离
                int leftmargin = (int) (i*leftmax + v*leftmax);
                //params.leftMarin = 两点间滑动距离对应的坐标
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
                params.leftMargin = leftmargin;
                iv_red_point.setLayoutParams(params);
            }
            /*
            * 当页面被选中时调用这个方法
            * */
            @Override
            public void onPageSelected(int i) {
                if(i==imageViews.size()-1){
                    //最后一个页面
                    btn_start_main.setVisibility(View.VISIBLE);
                }else {
                    //其他页面
                    btn_start_main.setVisibility(View.GONE);
                }
            }
            /*
            * 页面滑动状态发生变化
            * */
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、保存进入主页面
                CacheUtils.putBoolean(GuideActivity.this,WelcomeActivity.START_MAIN,true);
                //2、跳转到主页面
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                //3、关闭引导页面
                finish();
            }
        });
    }
}
