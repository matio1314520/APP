package com.matio.frameworkmodel.cache;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangjw on 2016/3/4.
 * 网络请求的工具类
 */
public class HttpUtils {


    private static ExecutorService mExecutorService;

    private static RequestCallBack mRequestCallBack;

    static {
        //创建一个定长的线程池
        mExecutorService = Executors.newFixedThreadPool(4);
    }

   static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mRequestCallBack != null) {

                mRequestCallBack.onSuccess(msg.obj.toString(),msg.what);
            }
        }
    };

    /**
     * Get
     */
    public static void requestGet(final String urlParam, final RequestCallBack callBack, final int requestCode) {

        mRequestCallBack = callBack;
        //异步
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("demo", "--------");
                String result = httpGet(urlParam);
                Message msg = handler.obtainMessage();
                msg.obj = result;
                msg.what = requestCode;
                handler.sendMessage(msg);
//                callBack.onSuccess(result);
            }
        });


    }


    public static void requestPost(final String urlParam,final Map<String,String> params, final RequestCallBack callBack) {

        mRequestCallBack = callBack;
        //异步
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("demo", "--------");
                String result = httpPost(urlParam, params,callBack);
//                callBack.onSuccess(result);
            }
        });
    }


    /**
     *Post
     * @param urlParam
     * @param params
     * @return
     */
    private static String httpPost(String urlParam,Map<String,String> params,RequestCallBack callBack) {
        InputStream is = null;
        HttpURLConnection httpURLConnection = null;
        //
        try {
            URL url = new URL(urlParam);
            httpURLConnection = (HttpURLConnection) url.openConnection();
//                  httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestMethod("POST");
            //传参数
            String paramStr = formatParams(params);
            httpURLConnection.getOutputStream().write(paramStr.getBytes());
            httpURLConnection.connect();

            //获取请求的状态码
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //请求成功
                //获得请求到的数据
                is = httpURLConnection.getInputStream();
                StringBuffer resultString = new StringBuffer();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    resultString.append(new String(buffer,0,len));
                }

                return resultString.toString();
            } else {
                callBack.onFailure("请求错误：" + httpURLConnection.getResponseCode());

            }
        } catch (IOException e) {
            e.printStackTrace();
            callBack.error(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

        }

        return null;
    }

    private static String formatParams(Map<String,String> params) {
//        kay=value&kay2=value2
        Set entrySet = params.entrySet();
        StringBuffer stringBuffer = new StringBuffer();
        for(Object obj:entrySet) {
            stringBuffer.append(obj).append("&");
        }

        return stringBuffer.substring(0,stringBuffer.length()-1);
    }


    /**
     * ----------------------------------------------------
     * @param urlParam
     */
    private static String httpGet(String urlParam) {
        InputStream is = null;
        HttpURLConnection httpURLConnection = null;
        //
        try {
            URL url = new URL(urlParam);
            httpURLConnection = (HttpURLConnection) url.openConnection();
//                  httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            //获取请求的状态码
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //请求成功
                //获得请求到的数据
                is = httpURLConnection.getInputStream();
                StringBuffer resultString = new StringBuffer();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    resultString.append(new String(buffer,0,len));
                }

//                Log.d("demo", "-->" + resultString);

                return resultString.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

        }

        return null;
    }


}
