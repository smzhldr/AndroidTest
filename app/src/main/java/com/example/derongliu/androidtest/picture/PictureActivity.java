package com.example.derongliu.androidtest.picture;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.derongliu.androidtest.R;
import com.example.derongliu.androidtest.downloadutils.DisplayImgaeOptions;
import com.example.derongliu.androidtest.downloadutils.ImageLoader;

import java.net.MalformedURLException;
import java.net.URL;

public class PictureActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        setTitle("PictureTest");
       // if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.GET_PERMISSIONS){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 0);
       // }
        final ImageView imageView = (ImageView) findViewById(R.id.imageload_imgaeview);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.imgae_download_progressBar);
        DisplayImgaeOptions options = new DisplayImgaeOptions();
        options.cache(true);

        ImageLoader.getInstance().loadImage("http://d.5857.com/fengg_141011/001.jpg", options, new ImageLoader.ImageLoadingListener() {
            @Override
            public void onImgaeLoading(int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onImgaeLoadComplete(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });

    }
}
