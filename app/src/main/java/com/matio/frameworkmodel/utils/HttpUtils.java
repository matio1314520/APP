package com.matio.frameworkmodel.utils;

import android.widget.ImageView;

import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by Angel on 2016/3/14.
 */
public class HttpUtils {

    /**
     * get String
     *
     * @param url
     * @param callback
     */
    public static void get(String url, final Callback callback) {

        x.http().get(new RequestParams(url), new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (result != null) {

                    callback.get(result);

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(org.xutils.common.Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * post string
     * @param requestParams
     * @param callback
     */
    public static void post(RequestParams requestParams, final Callback callback) {
        x.http().post(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (result != null) {

                    callback.get(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * interface for callback
     */
    public interface Callback {
        void get(String result);
    }

    /**
     * picture
     *
     * @param image
     * @param url
     * @param options
     */
    public static void setImage(ImageView image, String url, ImageOptions options) {

        x.image().bind(image, url, options);
    }
}
