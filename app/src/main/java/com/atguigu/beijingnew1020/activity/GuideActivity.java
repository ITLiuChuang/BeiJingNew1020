package com.atguigu.beijingnew1020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.utils.DensityUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {

    @InjectView(R.id.vp_main)
    ViewPager vpMain;
    @InjectView(R.id.but_start_main)
    Button butStartMain;
    @InjectView(R.id.ll_main)
    LinearLayout llMain;
    @InjectView(R.id.iv_red_point)
    ImageView ivRedPoint;

    private int[] ids = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    //间距
    private int leftMagin;

    /**
     * 间距 = 第1个点距离左边距离- 第0个点距离左边的距离
     * 红点移动的原理
     * 红点移动距离 ： 间距 = 手滑动的距离：屏幕宽 = 屏幕滑动的百分比
     * 红点移动距离 = 间距 * 屏幕滑动的百分比
     * 红点移动的坐标 = 起始坐标 + 红点移动距离
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.inject(this);
        initData();
    }

    private void initData() {
        //设置适配器
        vpMain.setAdapter(new MyPagerAdapter());

        //监听页面的改变
        vpMain.addOnPageChangeListener(new MyOnPageChangeListener());

        //根据多少个页面添加几个灰点
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10));
            if (i != 0) {
                //点之间的间距
                params.leftMargin = DensityUtil.dip2px(this, 10);
            }
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.point_normal);
            //添加的布局中
            llMain.addView(imageView);
        }

        //View的生命周期  测量--布局--绘制
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
    }

    @OnClick(R.id.but_start_main)
    public void onClick() {
        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return ids.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(ids[position]);
            //添加到viewPagre
            container.addView(imageView);
            return imageView;
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

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当页面滑动的时候回调
         *
         * @param position             当前滑动页面的位置
         * @param positionOffset       滑动的百分比
         * @param positionOffsetPixels 滑动的单位
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //红点移动距离 ： 间距 = 手滑动的距离：屏幕宽 = 屏幕滑动的百分比
            //红点移动距离 = 间距 * 屏幕滑动的百分比
            int maginLeft = (int) (leftMagin * positionOffset);
            //红点移动的坐标 = 起始坐标 + 红点移动距离
            maginLeft = position * leftMagin + (int) (leftMagin * positionOffset);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
            params.leftMargin = maginLeft;
            ivRedPoint.setLayoutParams(params);
        }

        /**
         * 当页面选中的时候回调
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
            if (position == ids.length - 1) {
                butStartMain.setVisibility(View.VISIBLE);
            } else {
                butStartMain.setVisibility(View.GONE);
            }

        }

        /**
         * 当状态发生变化时回调
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            //间距 = 第1个点距离左边距离- 第0个点距离左边的距离
            leftMagin = llMain.getChildAt(1).getLeft() - llMain.getChildAt(0).getLeft();
        }
    }
}
