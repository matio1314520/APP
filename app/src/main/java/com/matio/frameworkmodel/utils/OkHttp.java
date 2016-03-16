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
public class OkHttp<T> {

    private String mUrl;  //url 地址

    private Map<String, String> mParameter;  //post请求参数

    private Callback mCallback;  //用于回调的接口

    private Class<T> mClazz;

    private static OkHttpClient mOkHttpClient;

    private static Handler mHandler = new Handler();


    /**
     * post
     *
     * @param url
     * @param clazz
     * @param parameter
     * @param callback
     */
    public OkHttp(String url, Class clazz, Map<String, String> parameter, Callback callback) {

        this.mUrl = url;
        this.mParameter = parameter;
        this.mClazz = clazz;
        this.mCallback = callback;
    }

    /**
     * get
     *
     * @param url
     * @param clazz
     * @param callback
     */
    public OkHttp(String url, Class clazz, Callback callback) {

        this(url, clazz, null, callback);
    }


    public void onHttp() {

        mOkHttpClient.newCall(setRequest()).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response != null) {

                    final T t = JSONObject.parseObject(response.body().string(), mClazz);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            mCallback.onSuccess(t);
                        }
                    });


                }

            }
        });

    }

    private Request setRequest() {

        Request request = null;

        if (mParameter == null) {

            //get

            request = new Request.Builder()

                    .get()

                    .url(mUrl)

                    .build();

        } else {
            //post

            request = new Request.Builder().

                    post(setBody())

                    .url(mUrl)

                    .build();
        }

        return request;
    }

    private RequestBody setBody() {

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

        RequestBody body = RequestBody.create(mediaType, formatParameter(mParameter));
        return null;
    }


    private String formatParameter(Map<String, String> parameterMap) {
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


    static {

        if (mOkHttpClient == null) {

            mOkHttpClient = new OkHttpClient();
        }
    }


    public interface Callback<T> {

        void onSuccess(T t);
    }


}
