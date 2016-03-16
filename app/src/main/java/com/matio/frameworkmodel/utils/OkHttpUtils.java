package com.matio.frameworkmodel.utils;

import android.os.Handler;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Angel on 2016/3/16.
 */
public class OkHttpUtils {

    private static OkHttpClient mOkHttpClient;

    static {

        if (mOkHttpClient == null) {

            mOkHttpClient = new OkHttpClient();
        }
    }

    private static Handler mHandler = new Handler();



    public static <T> void get(String url, final Class<T> clazz, final Callback callback) {

        Request request = new Request.Builder()

                .url(url)

                .get()

                .build();

        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //执行在工作线程中，不能直接更新UI

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //执行在工作线程中，不能直接更新UI

                if (response != null) {

                    final T t = JSONObject.parseObject(response.body().string(), clazz);

                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {

                            callback.onSuccess(t);

                        }
                    });
                }
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T t);
    }
}
