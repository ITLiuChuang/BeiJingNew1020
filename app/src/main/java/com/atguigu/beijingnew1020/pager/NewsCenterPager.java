package com.atguigu.beijingnew1020.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.baselibrary.CacheUtils;
import com.atguigu.baselibrary.Constants;
import com.atguigu.beijingnew1020.activity.MainActivity;
import com.atguigu.beijingnew1020.base.BasePager;
import com.atguigu.beijingnew1020.base.MenuDetailBasePager;
import com.atguigu.beijingnew1020.bean.NewsCenterBean;
import com.atguigu.beijingnew1020.detailpager.InteractMenuDetailPager;
import com.atguigu.beijingnew1020.detailpager.NewsMenuDetailPager;
import com.atguigu.beijingnew1020.detailpager.PhotosMenuDetailPager;
import com.atguigu.beijingnew1020.detailpager.TopicMenuDetailPager;
import com.atguigu.beijingnew1020.fragment.LeftMenuFragment;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘闯 on 2017/2/6.
 */

public class NewsCenterPager extends BasePager {

    private ArrayList<MenuDetailBasePager> menuDetailBasePagers;
    /**
     * 左侧菜单对应的数据
     */
    private List<NewsCenterBean.DataBean> dataBeanList;

    public NewsCenterPager(Context mContext) {
        super(mContext);
    }

    @Override
    public void initData() {
        super.initData();
        //显示菜单按钮
        ib_menu.setVisibility(View.VISIBLE);
        //设置标题
        tv_title.setText("新闻");
        Log.e("TAG", "新闻页面加载数据了");

        //实例化视图
        TextView textView = new TextView(mContext);
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER);
        textView.setText("新闻中心");
        textView.setTextColor(Color.RED);

        //和父类的FragmentLayout结合
        fl_main.addView(textView);

        String saveJson = CacheUtils.getString(mContext, Constants.NEWSCENTER_PAGER_URL);
        if(!TextUtils.isEmpty(saveJson)) {
            ProcessData(saveJson);
        }
        //联网请求
        getDataFromNet();
    }

    /**
     * 联网请求数据
     */

    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(mContext,Constants.NEWSCENTER_PAGER_URL,result);
                Log.e("TAG", "resu=" + result);
                ProcessData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求失败==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG", "onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                Log.e("TAG", "onFinished==");
            }
        });
    }

    /**
     * 解析数据
     * 绑定数据
     *
     * @param json
     */
    private void ProcessData(String json) {
        //1.解析数据：手动解析（用系统的Api解析）和第三方解析json的框架（Gson,fastjson）
        NewsCenterBean centerBean = new Gson().fromJson(json, NewsCenterBean.class);
        Log.e("TAG", "json==" + json);
        dataBeanList = centerBean.getData();
        Log.e("TAG", "新闻中心解析成功=" + dataBeanList.get(0).getChildren().get(0).getTitle());
        //把新闻中心的数据传递给左侧菜单
        MainActivity mainActivity = (MainActivity) mContext;
        Log.e("TAG", "新闻");
        //得到左侧菜单
        LeftMenuFragment leftMunuFragment = mainActivity.getLeftMenuFragment();

        //2. 绑定数据
        menuDetailBasePagers = new ArrayList<>();
        menuDetailBasePagers.add(new NewsMenuDetailPager(mainActivity,dataBeanList.get(0)));//新闻详情页面
        menuDetailBasePagers.add(new TopicMenuDetailPager(mainActivity,dataBeanList.get(0)));//专题详情页面
        menuDetailBasePagers.add(new PhotosMenuDetailPager(mainActivity,dataBeanList.get(2)));//组图详情页面
        menuDetailBasePagers.add(new InteractMenuDetailPager(mainActivity));//互动详情页面

        //调用LeftMunuFragment的setData
        leftMunuFragment.setData(dataBeanList);

    }

    /**
     * 更加位置切换到不同的详情页面
     *
     * @param prePosition
     */
    public void switchPager(int prePosition) {
        //设置标题
        tv_title.setText(dataBeanList.get(prePosition).getTitle());

        final MenuDetailBasePager menuDetailBasePager = menuDetailBasePagers.get(prePosition);
        menuDetailBasePager.initData();

        //视图
        View rootView = menuDetailBasePager.rootView;
        fl_main.removeAllViews();//移除之前的
        fl_main.addView(rootView);

        if(prePosition ==2) {
         //组图
            ib_swich_list_gird.setVisibility(View.VISIBLE);
            ib_swich_list_gird.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhotosMenuDetailPager photosMenuDetailPager = (PhotosMenuDetailPager) menuDetailBasePagers.get(2);
                    photosMenuDetailPager.swichListGrid(ib_swich_list_gird);
                }
            });
        }else{
            //其他
            ib_swich_list_gird.setVisibility(View.GONE);
        }
    }
}
