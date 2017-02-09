package com.atguigu.beijingnew1020.detailpager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.activity.MainActivity;
import com.atguigu.beijingnew1020.base.MenuDetailBasePager;
import com.atguigu.beijingnew1020.bean.NewsCenterBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by 刘闯 on 2017/2/7.
 */


public class TopicMenuDetailPager extends MenuDetailBasePager {
    /**
     * 新闻详情页面的数据
     */
    private final List<NewsCenterBean.DataBean.ChildrenBean> childrenData;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.ib_next)
    ImageButton ibNext;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    /**
     * 页签页面的集合
     */
    private ArrayList<TabDetailPager> tabDetailPagers;

    public TopicMenuDetailPager(Context mContext, NewsCenterBean.DataBean dataBean) {
        super(mContext);
        this.childrenData = dataBean.getChildren();
    }

    @Override
    public View initView() {
        //新闻详情页面的视图
        View view = View.inflate(mContext, R.layout.topic_menu_detail_pager, null);
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

        tabLayout.setupWithViewPager(viewpager);
        //设置滑动的模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(childrenData.get(position).getTitle());
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.dot_focus);
        return view;
    }


    class MyPagerAdapter extends PagerAdapter {
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

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MainActivity mainActivity = (MainActivity) mContext;
            if (position == 0) {
                //北京页面可以滑动
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
            } else {
                //其他不可以滑动
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @OnClick(R.id.ib_next)
        public void onClick() {
            //切换到下一个页面
            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
        }
    }
}
