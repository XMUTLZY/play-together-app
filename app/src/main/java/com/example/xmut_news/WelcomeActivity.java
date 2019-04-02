package com.example.xmut_news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.xmut_news.utils.CacheUtils;

public class WelcomeActivity extends Activity {

    //静态常量
    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_splahs_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        rl_splahs_root = (RelativeLayout) findViewById(R.id.rl_splahs_root);
        //动画
        AlphaAnimation aa = new AlphaAnimation(0,1);
        aa.setDuration(1500);//持续播放时间
        aa.setFillAfter(true);

        ScaleAnimation sa = new ScaleAnimation(0,1,0,1, ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(1500);
        sa.setFillAfter(true);

        RotateAnimation ra = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(1500);
        ra.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        //添加三个动画没有先后顺序
        set.addAnimation(ra);
        set.addAnimation(sa);
        set.addAnimation(aa);
        set.setDuration(3000);

        rl_splahs_root.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //判断是否进入主页面
                boolean isStartMain = CacheUtils.getBoolean(WelcomeActivity.this, START_MAIN);
                Intent intent;
                if(isStartMain){
                    //如果进入过主页面，则直接进入登陆页面
                    intent = new Intent(WelcomeActivity.this,MainActivity.class);
                }else{
                    //如果没有进入过主页面，则进入引导页面
                    intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                }
                startActivity(intent);
                //关闭欢迎页面
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
