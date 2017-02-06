package com.atguigu.beijingnew1020.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 刘闯 on 2017/2/6.
 */

public class NoScrollViewPager extends ViewPager {


    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 屏蔽Viewpager的滑动功能
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
