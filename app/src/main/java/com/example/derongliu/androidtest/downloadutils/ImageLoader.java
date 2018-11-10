package com.example.derongliu.androidtest.downloadutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageLoader {

    private HandlerThread loadImageThread = new HandlerThread("loadImageThread");
    private Handler loadImageHandler;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x200:
                    if (msg.obj != null) {
                        ((ImageLoadingListener) (msg.obj)).onImgaeLoading(msg.arg1);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private static ImageLoader instance;
    private LruCache lruCache;

    private ImageLoader() {
        loadImageThread.start();
        loadImageHandler = new Handler(loadImageThread.getLooper());
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }


    public void loadImage(final String url, final ImageView imageView) {
        loadImageHandler.post(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = downLoadImgae(url, null);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public void loadImage(final String url, final ImageLoadingListener listener) {
        loadImageHandler.post(new Runnable() {
            @Override
            public void run() {
                downLoadImgae(url, listener);
            }
        });

    }

    public void loadImage(final String url, final DisplayImgaeOptions options, final ImageLoadingListener listener) {

       /* loadImageHandler.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downLoadImgae(url, listener);
                if (options.isNeedCahce() && url != null && bitmap != null) {
                    lruCache.put(url, bitmap);
                }
            }
        });*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downLoadImgae(url, listener);
                if (options.isNeedCahce() && url != null && bitmap != null) {
                    lruCache.put(url, bitmap);
                }
            }
        }).start();
    }


    public Bitmap getCacheBitmapFromMem(URL url) {
        if (lruCache == null) {
            return null;
        }
        return (Bitmap) lruCache.get(url);
    }


    public interface ImageLoadingListener {
        void onImgaeLoading(int progress);

        void onImgaeLoadComplete(Bitmap bitmap);

    }


    private Bitmap downLoadImgae(String urlStr, final ImageLoadingListener listener) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (lruCache.get(urlStr) != null) {
            return (Bitmap) lruCache.get(urlStr);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            // connection.setRequestProperty("Content-type", "imgae/jpg");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
               // File myFile = new File("saveFileAllName");
                if (listener != null) {
                    int contentLength = connection.getContentLength();
                    byte[] bytes = new byte[1024];
                    int totalReaded = 0;
                    int temp_Len;
                    while ((temp_Len = inputStream.read(bytes)) != -1) {
                        totalReaded += temp_Len;
                        final int progress = totalReaded * 100 / contentLength;
                        Message msg = Message.obtain(handler);
                        msg.what = 0x200;
                        msg.arg1 = progress;
                        msg.obj = listener;
                        handler.sendMessage(msg);

                        /*FileOutputStream fileOutputStream = new FileOutputStream(myFile);
                        fileOutputStream.write(bytes, 0, temp_Len);*/
                        }
                    }

                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onImgaeLoadComplete(bitmap);
                            }
                        });
                    }

                    connection.disconnect();
                    return bitmap;
                } else {
                    return null;
                }

            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
