package com.atguigu.beijingnew1020.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.base.BaseFragment;
import com.atguigu.beijingnew1020.base.BasePager;
import com.atguigu.beijingnew1020.pager.HomePager;
import com.atguigu.beijingnew1020.pager.NewsCenterPager;
import com.atguigu.beijingnew1020.pager.SettingPager;

import java.util.ArrayList;

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

    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        View view = View.inflate(mContent, R.layout.fragment_content, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        //初始化3个页面
        initPager();

        //设置ViewPager的适配器
        setAdapter();

        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        viewpager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        viewpager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_setting:
                        viewpager.setCurrentItem(2, false);
                        break;
                }
            }
        });
        rgMain.check(R.id.rb_news);
    }

    private void setAdapter() {
        viewpager.setAdapter(new MyPagerAdapter());
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);

            View rootView = basePager.rootView;//代表不同页面的实例

            //调用initData
            basePager.initData();

            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private void initPager() {
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(mContent));//主页
        basePagers.add(new NewsCenterPager(mContent));//新闻中心
        basePagers.add(new SettingPager(mContent));//设置中心
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
