package com.atguigu.beijingnew1020.detailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.beijingnew1020.base.MenuDetailBasePager;

/**
 * Created by 刘闯 on 2017/2/7.
 */

public class InteractMenuDetailPager extends MenuDetailBasePager {
    private TextView textView;

    public InteractMenuDetailPager(Context mContext) {
        super(mContext);
    }

    @Override
    public View initView() {
        //互动详情页面的视图
        textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("互动详情页面的视图");
    }
}
