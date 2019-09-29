package com.example.derongliu.androidtest.main.mvvm;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;

import com.example.derongliu.aidl.AidlActivity;
import com.example.derongliu.androidtest.camera.CameraActivity;
import com.example.derongliu.androidtest.downloadpicture.PictureActivity;
import com.example.derongliu.androidtest.internet.OKHttpActivity;
import com.example.derongliu.androidtest.internet.RetrofitActivity;
import com.example.derongliu.androidtest.rxjava.RxActivity;
import com.example.derongliu.widget.scrollmenu.ScrollMenuActivity;
import com.example.derongliu.androidtest.share.ShareActivity;
import com.example.derongliu.kt.KtActivity;
import com.example.derongliu.ndk.NDKTestActivity;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    public List<String> titleItems = new ArrayList<>();
    public List<Class> classItems = new ArrayList<>();

    public OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(Context context, int pos) {
            clickPos.setValue(titleItems.get(pos));
            Intent intent = new Intent(context, classItems.get(pos));
            if (!(context instanceof Activity)) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        }
    };
    public MutableLiveData<String> clickPos = new MutableLiveData<>();


    public MainViewModel() {
        initData();
    }

    private void initData() {
        titleItems.add("下载图片");
        titleItems.add("NDKTest");
        titleItems.add("Camera_lib");
        titleItems.add("ScrollMenu");
        titleItems.add("分享回调");
        titleItems.add("OKHttp");
        titleItems.add("Retrofit");
        titleItems.add("AIDL");
        titleItems.add("Kt");
        titleItems.add("RxJava");

        classItems.add(PictureActivity.class);
        classItems.add(NDKTestActivity.class);
        classItems.add(CameraActivity.class);
        classItems.add(ScrollMenuActivity.class);
        classItems.add(ShareActivity.class);
        classItems.add(OKHttpActivity.class);
        classItems.add(RetrofitActivity.class);
        classItems.add(AidlActivity.class);
        classItems.add(KtActivity.class);
        classItems.add(RxActivity.class);

    }


}
