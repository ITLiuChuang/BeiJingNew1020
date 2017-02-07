package com.atguigu.beijingnew1020.detailpager;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.base.MenuDetailBasePager;
import com.atguigu.beijingnew1020.bean.NewsCenterBean;
import com.atguigu.beijingnew1020.bean.TabDetailPagerBean;
import com.atguigu.beijingnew1020.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 刘闯 on 2017/2/7.
 */

public class TabDetailPager extends MenuDetailBasePager {
    private final NewsCenterBean.DataBean.ChildrenBean childrenBean;
    @InjectView(R.id.listview)
    ListView listview;
    private String url;

    public TabDetailPager(Context mContext, NewsCenterBean.DataBean.ChildrenBean childrenBean) {
        super(mContext);
        this.childrenBean = childrenBean;
    }


    @Override
    public View initView() {
        //图组详情页面的视图
        View view = View.inflate(mContext, R.layout.tab_detail_pager, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenBean.getUrl();
        //设置数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "请求数据成功==TabDetailPager==" + childrenBean.getTitle());
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG", "请求数据失败==TabDetailPager==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {
        TabDetailPagerBean pagerBean = new Gson().fromJson(result, TabDetailPagerBean.class);
        Log.e("TAG", "数据解析成功==TabDetailPager==" + pagerBean.getData().getNews().get(0).getTitle());
    }
}
