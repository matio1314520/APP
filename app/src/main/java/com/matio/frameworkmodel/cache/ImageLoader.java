package com.matio.frameworkmodel.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangjw on 2016/3/8.
 */
public class ImageLoader {


    static Handler mHandler = new Handler() ;


    private static ExecutorService mExecutorService;

    static {
        mExecutorService = Executors.newFixedThreadPool(2);
    }

    public static void load(Context context,String url, ImageView imageView) {

        Bitmap cacheBitmap = AppMemoryCache.get(url);
        if (cacheBitmap == null) {
            imageView.setTag(url);
            AppDiskCache.init(context);
            mExecutorService.execute(new ImageThread(url,imageView));
        } else {
            Log.d("demo","--->memory");
            imageView.setImageBitmap(cacheBitmap);
        }

    }

    /**
     * 开启线程请求网络图片
     */
    static class ImageThread implements Runnable {


        private String urlParam;
        private ImageView imageView;

        public ImageThread(String urlParam, ImageView imageView) {
            this.urlParam = urlParam;
            imageView.setImageBitmap(null);
            this.imageView = imageView;
        }

        private void setImageView(final Bitmap bitmap) {
            if (mHandler != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (imageView != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }
                });
            }

        }

        @Override
        public void run() {

            final Bitmap cacheBitmap = AppDiskCache.get(urlParam);
            if (cacheBitmap != null) {
                AppMemoryCache.put(urlParam,cacheBitmap);
                setImageView(cacheBitmap);
            } else {
                connServerData();
            }

        }


        private void connServerData() {
            InputStream inputStream = null;
            //请求图片的代码
            try {
                final URL url = new URL(urlParam);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //请求建立链接
                httpURLConnection.connect();
                //获取网络流
                inputStream = httpURLConnection.getInputStream();
                //从流中读取数据
                int len=0;
                byte[] buffer = new byte[1024];

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                while((len = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer,0,len);
                }

                Log.d("demo","-->" );

                inputStream.close();

                //将图片流转换成Bitmap
                byte[] toByteArray = byteArrayOutputStream.toByteArray();

                BitmapFactory.Options options = new BitmapFactory.Options();
                //不会真正的加载bitmap对象到内存中，但是会返回图片的高度和宽度信息。
                options.inJustDecodeBounds = true;

                BitmapFactory.decodeByteArray(toByteArray, 0, toByteArray.length,options);

                int outHeight = options.outHeight;
                int outWidth = options.outWidth;

                //计算图片的大小，并决定压缩比例

                int ratio1 = outWidth / 200;
                int ratio2 = outHeight / 200;

                int ratio = Math.max(ratio1,ratio2);

                if (ratio < 1) {
                    options.inSampleSize = 1;
                } else {
                    options.inSampleSize = ratio;
                }


                //将获取Bitmap对象的时候，将inJustDecodeBounds重新设置为true
                options.inJustDecodeBounds = false;

                //已经是压缩过的BItmap
                final Bitmap bitmap = BitmapFactory.decodeByteArray(toByteArray, 0, toByteArray.length, options);


                AppDiskCache.put(urlParam, bitmap);

//                MemoryCacheTool.putCache(urlParam,bitmap);

                Log.d("demo", "-->bitmap=" + bitmap);

                if (bitmap != null) {
                    //运行在主线程中
                    setImageView(bitmap);
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //运行在主线程中
//                            if (imageView.getTag().equals(urlParam)) {
//                                imageView.setImageBitmap(bitmap);
//                            }
//                        }
//                    });
                }

//                imageView.set

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
