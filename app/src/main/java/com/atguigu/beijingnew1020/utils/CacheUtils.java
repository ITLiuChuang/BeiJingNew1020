package com.atguigu.beijingnew1020.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 刘闯 on 2017/2/6.
 */

public class CacheUtils {
    /**
     * 保持参数
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 得到保存的参数
     * @param context
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
