package com.atguigu.beijingnew1020.base;

import android.content.Context;
import android.view.View;

/**
 * Created by 刘闯 on 2017/2/7.
 */

public abstract class MenuDetailBasePager {
    /**
     * 上下文
     */
    public final Context mContext;
    /**
     * 代表各个菜单详情页面的实例，视图
     */
    public View rootView;

    public MenuDetailBasePager(Context mContext) {
        this.mContext = mContext;
        rootView = initView();
    }

    /**
     * 由子类实现该方法，初始化子类的视图
     *
     * @return
     */
    public abstract View initView();

    /**
     * 由子类实现该方法，初始化子类的视图
     */
    public void initData() {

    }
}
