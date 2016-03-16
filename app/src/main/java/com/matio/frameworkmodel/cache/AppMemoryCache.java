package com.matio.frameworkmodel.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Angel on 2016/3/14.
 */
public class AppMemoryCache {

    private static LruCache<String, Bitmap> mLruCache;

    static {
        mLruCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
            //By default, the cache size is measured in the number of entries.
            // Override sizeOf(K, V) to size the cache in different units.
            // For example, this cache is limited to 4MiB of bitmaps

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }


    public static void put(String url, Bitmap bitmap) {
        if (url != null && bitmap != null) {
            mLruCache.put(url, bitmap);
        }
    }


    public static Bitmap get(String url) {
        if (url != null) {
            return mLruCache.get(url);
        }
        return null;
    }
}
