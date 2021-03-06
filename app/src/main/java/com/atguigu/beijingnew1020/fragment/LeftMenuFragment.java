package com.atguigu.beijingnew1020.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.baselibrary.DensityUtil;
import com.atguigu.beijingnew1020.R;
import com.atguigu.beijingnew1020.activity.MainActivity;
import com.atguigu.beijingnew1020.base.BaseFragment;
import com.atguigu.beijingnew1020.bean.NewsCenterBean;
import com.atguigu.beijingnew1020.pager.NewsCenterPager;

import java.util.List;

/**
 * Created by 刘闯 on 2017/2/5.
 */

public class LeftMenuFragment extends BaseFragment {

    private LeftMenuFragmentAdapter adapter;

    /**
     * 左侧菜单对应的数据
     */
    private List<NewsCenterBean.DataBean> datas;


    /**
     * 点击的位置
     */
    private int prePosition = 0;
    ListView listView;

    @Override
    public View initView() {

        listView = new ListView(mContent);
        listView.setPadding(0, DensityUtil.dip2px(mContent, 40), 0, 0);

        listView.setBackgroundColor(Color.BLACK);
        //设置监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录位置的刷新适配器
                prePosition = position;
                adapter.notifyDataSetChanged();
                //2.关闭侧滑菜单
                MainActivity mainActivity = (MainActivity) mContent;
                mainActivity.getSlidingMenu().toggle();
                //3.切换到对应的详情页面
                switchPager(prePosition);
            }
        });
        return listView;
    }

    /**
     * 根据位置切换到不同的详情页面
     *
     * @param prePosition
     */
    private void switchPager(int prePosition) {
        MainActivity mainActivity = (MainActivity) mContent;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        newsCenterPager.switchPager(prePosition);
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void setData(List<NewsCenterBean.DataBean> dataBeanList) {
        this.datas = dataBeanList;

        //设置适配器
        adapter = new LeftMenuFragmentAdapter();
        if(listView != null)
        listView.setAdapter(adapter);
        switchPager(prePosition);
    }

    class LeftMenuFragmentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(mContent, R.layout.item_leftmenu, null);

            //设置内容
            textView.setText(datas.get(position).getTitle());
            if (prePosition == position) {
                //把颜色设置高亮-红色
                textView.setEnabled(true);
            } else {
                //把颜色设置默认-白色
                textView.setEnabled(false);

            }


            return textView;
        }
    }
}
