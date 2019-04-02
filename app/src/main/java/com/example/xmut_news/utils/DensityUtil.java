package com.example.xmut_news.utils;

import android.content.Context;
/*
* dp和dip单位转换类
* */
public class DensityUtil {
    /*
    * 根据手机的分辨率从dip的单位转为px
    * */
    public static int dip2px(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    /*
    * 根据手机的分辨率从px的单位转为dip
    * */
    public static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue * scale + 0.5f);
    }
}
