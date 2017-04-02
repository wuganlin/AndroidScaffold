package com.android.scaffold.http.api;

import com.android.scaffold.http.HttpRequestHelper;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wuganlin on 2017/3/31.
 */

public class PocketMedicalApi extends HttpRequestHelper {
    public static final String BASE_URL = "https://192.168.1.50:8888/PocketMedical/";
    private static PocketMedicalApi instance;

    private PocketMedicalApi() {
        super(BASE_URL);
    }

    public static PocketMedicalApi getInstance() {
        if (instance == null) {
            synchronized (PocketMedicalApi.class) {
                if (instance == null) {
                    instance = new PocketMedicalApi();
                }
            }
        }
        return instance;
    }

    @Override
    protected List<Interceptor> onCreateInterceptors() {
        List<Interceptor> interceptors = super.onCreateInterceptors();
        interceptors.add(new AuthInterceptor());
        return interceptors;
    }

    private class AuthInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            Long unitTime = System.currentTimeMillis();
            HttpUrl.Builder authUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("token_id", "11111111")
                    .addQueryParameter("unit_time", String.valueOf(unitTime));
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authUrlBuilder.build())
                    .build();
            return chain.proceed(newRequest);
        }
    }


}
