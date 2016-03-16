package com.matio.frameworkmodel.utils;

import android.os.Handler;

import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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


    /**
     * get
     *
     * @param url      url地址
     * @param clazz    返回的object对象的class
     * @param callback 回调的接口
     * @param <T>
     */
    public static <T> void get(String url, final Class<T> clazz, final Callback callback, int tag) {

        Request request = new Request.Builder()

                .url(url)

                .get()

                .tag(tag)

                        //  .header("Content-Type","text/html;charset=utf-8")

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


    /**
     * post
     *
     * @param url          接口地址
     * @param parameterMap 请求参数
     * @param clazz        返回的object对象的class
     * @param callback     回调的接口
     * @param <T>
     */
    public static <T> void post(String url, Map<String, String> parameterMap, final Class<T> clazz, final Callback callback,int tag) {


        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

        RequestBody body = RequestBody.create(mediaType, formatParameter(parameterMap));

        final Request request = new Request.Builder()

                .post(body)

                .url(url)

                .tag(tag)

                .build();


        mOkHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response != null) {

                    final T t = JSONObject.parseObject(response.body().string(), clazz);



                    if (t != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                callback.onSuccess(t);
                            }
                        });
                    }
                }
            }
        });
    }


    private static String formatParameter(Map<String, String> parameterMap) {
        try {

            org.json.JSONObject jsonObject = new org.json.JSONObject();

            Set<String> keySet = parameterMap.keySet();

            for (String key : keySet) {

                jsonObject.put(key, parameterMap.get(key));
            }

            return jsonObject.toString();

        } catch (JSONException e) {

            e.printStackTrace();

        }

        return null;

    }

    /**
     * 用于回调的接口
     *
     * @param <T>
     */
    public interface Callback<T> {
        void onSuccess(T t);
    }
}
