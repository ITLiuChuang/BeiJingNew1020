package com.atguigu.beijingnew1020.detailpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.base.MenuDetailBasePager;
import com.atguigu.beijingnew1020.bean.NewsCenterBean;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 刘闯 on 2017/2/7.
 */

public class NewsMenuDetailPager extends MenuDetailBasePager {
    /**
     * 新闻详情页面的数据
     */
    private final List<NewsCenterBean.DataBean.ChildrenBean> childrenData;
    @InjectView(R.id.indicator)
    TabPageIndicator indicator;

    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    @InjectView(R.id.ib_next)
    ImageButton ibNext;
    /**
     * 页签页面的集合
     */
    private ArrayList<TabDetailPager> tabDetailPagers;

    public NewsMenuDetailPager(Context mContext, NewsCenterBean.DataBean dataBean) {
        super(mContext);
        this.childrenData = dataBean.getChildren();
    }

    @Override
    public View initView() {
        //新闻详情页面的视图
        View view = View.inflate(mContext, R.layout.news_menu_detail_pager, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //准备数据-页面
        tabDetailPagers = new ArrayList<>();
        //根据有多少数据创建多少个TabDetailPager，并且把数据传入到对象中
        for (int i = 0; i < childrenData.size(); i++) {
            tabDetailPagers.add(new TabDetailPager(mContext, childrenData.get(i)));
        }
        //设置适配器
        viewpager.setAdapter(new MyPagerAdapter());
        //要在设置适配器之后
        indicator.setViewPager(viewpager);
        //监听页面的变化用TabPageIndicator
    }

    @OnClick(R.id.ib_next)
    public void onClick() {
        //切换到下一个页面
        indicator.setCurrentItem(viewpager.getCurrentItem()+1);
    }

    private class MyPagerAdapter extends PagerAdapter {
        public CharSequence getPageTitle(int position) {
            return childrenData.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            tabDetailPager.initData();
            View rootView = tabDetailPager.rootView;
            container.addView(rootView);
            return rootView;
        }
    }
}
