package com.atguigu.beijingnew1020.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.beijingnew1020.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewsDetailActivity extends AppCompatActivity {

    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.ib_menu)
    ImageButton ibMenu;
    @InjectView(R.id.webview)
    WebView webview;
    @InjectView(R.id.progressbar)
    ProgressBar progressbar;

    @InjectView(R.id.ib_back)
    ImageButton ibBack;
    @InjectView(R.id.ib_textsize)
    ImageButton ibTextsize;
    @InjectView(R.id.ib_share)
    ImageButton ibShare;
    private String url;
    /**
     * 缓存
     */
    private int tempSize = 2;
    /**
     * 真实文字的大小
     */
    private int realSize = tempSize;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.inject(this);
        url = getIntent().getStringExtra("url");

        tvTitle.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        //WebView的使用
        webview.loadUrl(url);
        webSettings = webview.getSettings();
        //支持javaScript脚步语言
        webSettings.setJavaScriptEnabled(true);
        //添加缩放按钮-页面要支持
        webSettings.setBuiltInZoomControls(true);
        //双击缩放
        webSettings.setUseWideViewPort(true);
        //设置监听
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.ib_back, R.id.ib_textsize, R.id.ib_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_textsize:
                //  Toast.makeText(this, "设置文字大小", Toast.LENGTH_SHORT).show();
                showChangeTextSizeDialog();
                break;
            case R.id.ib_share:
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    /**
     * 改变字体的大小
     */
    private void showChangeTextSizeDialog() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(this);
        bulider.setTitle("设置文字的大小");
        String[] items = {"超大字体", "大字体", "正常字体", "小字体", "超小字体"};
        bulider.setSingleChoiceItems(items, realSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempSize = which;
            }
        });
        bulider.setNegativeButton("取消",null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realSize = tempSize;
                changeTextSize(realSize);
            }
        }).show();
    }

    private void changeTextSize(int realSize) {
        switch (realSize) {
            case 0 ://超大字体
                webSettings.setTextZoom(200);
                break;
            case 1 ://大字体
                webSettings.setTextZoom(150);
                break;
            case 2 ://正常字体
                webSettings.setTextZoom(100);
                break;
            case 3://小字体
                webSettings.setTextZoom(75);
                break;
            case 4 ://超小字体
                webSettings.setTextZoom(50);
                break;
        }
    }
}









