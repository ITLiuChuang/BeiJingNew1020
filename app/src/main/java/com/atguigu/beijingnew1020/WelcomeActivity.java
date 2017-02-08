package com.atguigu.beijingnew1020;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.atguigu.baselibrary.CacheUtils;
import com.atguigu.beijingnew1020.activity.GuideActivity;
import com.atguigu.beijingnew1020.activity.MainActivity;

public class WelcomeActivity extends AppCompatActivity {
    private RelativeLayout activity_welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activity_welcome = (RelativeLayout) findViewById(R.id.activity_welcome);
        setAnimation();


    }

    private void setAnimation() {
        //三个动画,旋转,渐变,缩放
        RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(2000);//持续时间
        ra.setFillAfter(true);//停留在最终状态

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(2000);//持续时间
        aa.setFillAfter(true);//停留在最终状态

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(2000);//持续时间
        sa.setFillAfter(true);//停留在最终状态


        AnimationSet set = new AnimationSet(false);
        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);

        //开始播放
        activity_welcome.startAnimation(set);

        //动画监听
        set.setAnimationListener(new MyAnimationListener());
    }

    class MyAnimationListener implements Animation.AnimationListener {
        /**
         * 动画开始时回调
         *
         * @param animation
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }

        /**
         * 动画结束时回调
         *
         * @param animation
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            boolean startMain = CacheUtils.getBoolean(WelcomeActivity.this,"start_main");
            Intent intent = null;
            if(startMain) {
                //进入主页面
                intent = new Intent(WelcomeActivity.this, MainActivity.class);

            }else {
                intent = new Intent(WelcomeActivity.this, GuideActivity.class);

            }
            //页面跳转
            startActivity(intent);
            finish();

        }

        /**
         * 动画循环播放时回调
         *
         * @param animation
         */
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
