package com.atguigu.beijingnew1020.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.activity.MainActivity;
import com.atguigu.beijingnew1020.base.BaseFragment;
import com.atguigu.beijingnew1020.base.BasePager;
import com.atguigu.beijingnew1020.pager.HomePager;
import com.atguigu.beijingnew1020.pager.NewsCenterPager;
import com.atguigu.beijingnew1020.pager.SettingPager;
import com.atguigu.beijingnew1020.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 刘闯 on 2017/2/5.
 */

public class ContentFragment extends BaseFragment {
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    @InjectView(R.id.viewpager)
    NoScrollViewPager viewpager;
    /**
     * 三个页面的集合
     */
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
        //设置RadioGroup状态选中的监听
        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //先默认设置不可以滑动
                MainActivity mainActivity = (MainActivity) mContent;
                mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);

                switch (checkedId) {
                    case R.id.rb_home:
                        viewpager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_news:
                        //Viewpager，切换到不同页面的方法
                        viewpager.setCurrentItem(1, false);
                        //当切换到新闻的时候,修改成可以滑动
                        mainActivity.getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case R.id.rb_setting:
                        viewpager.setCurrentItem(2, false);
                        break;
                }
            }
        });
        rgMain.check(R.id.rb_news);

        //监听页面的选中
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        basePagers.get(1).initData();//孩子的视图和父类的FrameLayout结合
    }

    /**
     * @return
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) basePagers.get(1);
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            BasePager basePager = basePagers.get(position);
            //调用initData
            basePagers.get(position).initData();//孩子的视图和父类的FrameLayout结合
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
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
            // basePager.initData();//孩子的视图和父类的FrameLayout结合

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
