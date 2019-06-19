package com.lpjeremy.libmodule.http;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lpjeremy.libmodule.gson.GsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @desc:
 * @date:2019/1/27 10:25
 * @auther:lp
 * @version:1.0
 */
public class RetrofitUtil {
    private static final String HTTP_TAG = "http";

    private Retrofit mRetrofitToken, mRetrofitNoToken;


    private RetrofitUtil() {
    }

    public static RetrofitUtil getInstance() {
        return RetrofitUtilHolder.sInstance;
    }

    private static class RetrofitUtilHolder {
        private static final RetrofitUtil sInstance = new RetrofitUtil();
    }

    /**
     * 创建网络请求接口的实例
     *
     * @param clazz   接口定义
     * @param API_URL api基础地址
     * @param <T>
     * @return
     */
    public <T> T createTapeTokenRetrofit(Class<T> clazz, String API_URL) {
        if (mRetrofitToken == null) {
            return createRetrofit(API_URL, true).create(clazz);
        } else {
            return mRetrofitToken.create(clazz);
        }
    }

    /**
     * 创建网络请求接口的实例,默认加token
     *
     * @param clazz   接口定义
     * @param API_URL api基础地址
     * @param <T>
     * @return
     */
    public <T> T createNoTokenRetrofit(Class<T> clazz, String API_URL) {
        if (mRetrofitNoToken == null) {
            return createRetrofit(API_URL, false).create(clazz);
        } else {
            return mRetrofitNoToken.create(clazz);
        }
    }

    /**
     * 初始化Retrofit
     *
     * @param tapeToken 是否带token
     */
    private Retrofit createRetrofit(String API_URL, boolean tapeToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getInstance().createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_URL)
                .client(createOkHttpClient(tapeToken))
                .build();
        return retrofit;
    }


    /**
     * 创建HttpLoggingInterceptor
     * 配合com.squareup.okhttp3:logging-interceptor:3.4.1使用
     *
     * @return HttpLoggingInterceptor
     */
    private HttpLoggingInterceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(HTTP_TAG, "requestBack: " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * 创建OkHttpClient对象，并设置超时，结果打印和添加header信息等
     *
     * @return OkHttpClient
     */
    private OkHttpClient createOkHttpClient(boolean addToken) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //超时设置 单位秒
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.readTimeout(5, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(5, TimeUnit.SECONDS);
        if (addToken) {
            okHttpClient.addInterceptor(createHttpLoggingInterceptor())
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            //.header("Authorization", (TextUtils.isEmpty(getToken()) ? "No Auth " : "Bearer ") + getToken())
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header("Authorization", "Bearer " + getToken())
                                    .header("Content-Type", "application/json")
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    });
        } else {
            okHttpClient.addInterceptor(createHttpLoggingInterceptor())
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header("Content-Type", "application/json")
                                    .method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    });
        }
        return okHttpClient.build();
    }

    private String getToken() {
        return SPUtils.getInstance().getString("token");
    }

}
