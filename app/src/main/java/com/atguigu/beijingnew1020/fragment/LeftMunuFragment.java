package com.atguigu.beijingnew1020.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.beijingnew1020.base.BaseFragment;

/**
 * Created by 刘闯 on 2017/2/5.
 */

public class LeftMunuFragment extends BaseFragment {
    private TextView textView;
    @Override
    public View initView() {
        textView = new TextView(mContent);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("左侧菜单");
    }
}
