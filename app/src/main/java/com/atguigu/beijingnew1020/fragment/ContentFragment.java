package com.atguigu.beijingnew1020.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 刘闯 on 2017/2/5.
 */

public class ContentFragment extends BaseFragment {
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;

    @Override
    public View initView() {
        View view = View.inflate(mContent, R.layout.fragment_content, null);
        ButterKnife.inject(this, view );
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        rgMain.check(R.id.rb_news);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
