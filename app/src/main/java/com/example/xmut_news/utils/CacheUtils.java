package com.example.xmut_news.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.example.xmut_news.GuideActivity;
import com.example.xmut_news.WelcomeActivity;

//缓存软件的一些参数和数据
public class CacheUtils {
    //@param context 上下文
    public static boolean getBoolean(Context context, String key) {
        //保存参数
        SharedPreferences sp = context.getSharedPreferences("xmut",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }
    //保存软件参数
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("xmut",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
