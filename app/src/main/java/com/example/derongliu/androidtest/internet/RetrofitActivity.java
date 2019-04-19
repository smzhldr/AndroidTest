package com.example.derongliu.androidtest.internet;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RetrofitActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TextView textView = new TextView(this);
        setContentView(textView);
        //String str = "https://gw.api.alibaba.com/openapi/param2/1/portals.open/api.generatePromotionLinks/18644?keywords=Rings&linkType=SEARCH_LINK&fields=promotionUrls&pageNum=1&trackingId=050plush&_aop_signature=4A66F846F14E8B7B8CE8EC28FE200C4CF9E7094B";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gw.api.alibaba.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Root> resultCall = apiService.getResult("Rings", "SEARCH_LINK", "promotionUrls", "1", "050plush", "4A66F846F14E8B7B8CE8EC28FE200C4CF9E7094B");
        resultCall.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                final StringBuilder builder = new StringBuilder();
                builder.append("errorCode:" + root.getResult().getErrorCode() + "\n");
                builder.append("totalResult:" + root.getResult().getResult().getTotalResults() + "\n");
                builder.append("promotionUrl:" + root.getResult().getResult().getPromotionUrl().get(0).getPromotionUrl() + "\n");
                builder.append("Url:" + root.getResult().getResult().getPromotionUrl().get(0).getUrl() + "\n");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(builder.toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });

    }


    private interface ApiService {
        @GET("openapi/param2/1/portals.open/api.generatePromotionLinks/18644")
        Call<Root> getResult(@Query("keywords") String keyWords, @Query("linkType") String linkType, @Query("fields") String fields, @Query("pageNum") String pageNum, @Query("trackingId") String trackingId, @Query("_aop_signature") String _aop_signature);
    }


    public static class Root {
        private Result result;

        public void setResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return this.result;
        }

        public class Result {
            private int errorCode;

            private Urls result;

            public void setErrorCode(int errorCode) {
                this.errorCode = errorCode;
            }

            public int getErrorCode() {
                return this.errorCode;
            }

            public void setResult(Urls result) {
                this.result = result;
            }

            public Urls getResult() {
                return this.result;
            }

            public class Urls {
                private List<PromotionUrl> promotionUrl;

                private int totalResults;

                public void setPromotionUrl(List<PromotionUrl> promotionUrl) {
                    this.promotionUrl = promotionUrl;
                }

                public List<PromotionUrl> getPromotionUrl() {
                    return this.promotionUrl;
                }

                public void setTotalResults(int totalResults) {
                    this.totalResults = totalResults;
                }

                public int getTotalResults() {
                    return this.totalResults;
                }


                public class PromotionUrl {
                    private String promotionUrl;

                    private String url;

                    public void setPromotionUrl(String promotionUrl) {
                        this.promotionUrl = promotionUrl;
                    }

                    public String getPromotionUrl() {
                        return this.promotionUrl;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public String getUrl() {
                        return this.url;
                    }
                }

            }
        }

    }
}
