package com.example.derongliu.androidtest.downloadpicture.downloadutils;

public class DisplayImgaeOptions {

    private boolean needCahce;

    public void cache(boolean needCahce){
        this.needCahce = needCahce;
    }

    public boolean isNeedCahce() {
        return needCahce;
    }
}
