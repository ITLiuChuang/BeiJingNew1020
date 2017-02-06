package com.atguigu.beijingnew1020;

import android.app.Application;

import org.xutils.x;


/**
 * Created by 刘闯 on 2017/2/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
