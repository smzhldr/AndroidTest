package com.example.derongliu.androidtest.share;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.derongliu.androidtest.R;

import pub.devrel.easypermissions.EasyPermissions;

public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Button button = findViewById(R.id.share_pic);
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "读取相册", 100, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        Intent shareCaptionIntent = new Intent(Intent.ACTION_SEND);
                        shareCaptionIntent.setDataAndType(selectedImage, "image/*");
                        //set photo

                        shareCaptionIntent.putExtra(Intent.EXTRA_STREAM, selectedImage);

                        //set caption
                        //shareCaptionIntent.putExtra(Intent.EXTRA_TEXT, "example caption");
                        //shareCaptionIntent.putExtra(Intent.EXTRA_SUBJECT, "example caption");

                        startActivityForResult(Intent.createChooser(shareCaptionIntent, "分享"), 101);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 101:
                Toast.makeText(ShareActivity.this, "share success", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
