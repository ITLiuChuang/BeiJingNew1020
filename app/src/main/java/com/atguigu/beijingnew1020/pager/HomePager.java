package com.atguigu.beijingnew1020.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.atguigu.beijingnew1020.base.BasePager;

/**
 * Created by 刘闯 on 2017/2/6.
 */

public class HomePager extends BasePager {
    public HomePager(Context mContext) {
        super(mContext);
    }

    @Override
    public void initData() {
        super.initData();
        //设置标题
        tv_title.setText("主页");
        //实例化视图
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("主页面");
        textView.setTextColor(Color.RED);

        //和父类的FragmentLayout结合
        fl_main.addView(textView);
    }
}
