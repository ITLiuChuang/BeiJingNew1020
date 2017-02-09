package com.atguigu.beijingnew1020.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.atguigu.beijingnew1020.R;

import it.sephiroth.android.library.picasso.Callback;
import it.sephiroth.android.library.picasso.Picasso;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PicassoSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_sample);
        PhotoView photoView = (PhotoView) findViewById(R.id.iv_photo);

        String url = getIntent().getStringExtra("url");

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        Picasso.with(this).load(url).into(photoView, new Callback() {
            @Override
            public void onSuccess() {
                attacher.update();
            }

            @Override
            public void onError() {

            }
        });
    }
}
