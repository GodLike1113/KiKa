package com.transsnet.kika.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.transsnet.kika.Contstant;
import com.transsnet.kika.R;
import com.transsnet.kika.util.AppUtil;

/**
 * Created by rst on 2018/5/24.
 */

public class GlideUtil {

    /**
     * 从URI加载图片
     */
    public static void loadPicture(Context context,String url, ImageView imageView) {
        if (AppUtil.getMaxMemory() < Contstant.LOW_LEVEL_MEMORY) {
            GlideApp.with(context.getApplicationContext()).load(new MyGlideUrl(url)).placeholder(R.drawable.bg_error).skipMemoryCache(true).into(imageView);
        } else {
            GlideApp.with(context.getApplicationContext()).load(new MyGlideUrl(url)).placeholder(R.drawable.bg_error).into(imageView);
        }
    }

    /**
     * 从URI加载图片,有error状态默认图片
     */
    public static void loadPicture(Context context,String url, ImageView imageView, int errorResId) {
        if (AppUtil.getMaxMemory() < Contstant.LOW_LEVEL_MEMORY) {
            GlideApp.with(context.getApplicationContext()).load(new MyGlideUrl(url)).placeholder(errorResId).error(errorResId).skipMemoryCache(true).into(imageView);
        } else {
            GlideApp.with(context.getApplicationContext()).load(new MyGlideUrl(url)).placeholder(errorResId).error(errorResId).into(imageView);
        }
    }

    /**
     * 加载本地图片到非imageview
     */
    public static void loadPicture(Context context,Uri uri, final ImageView imageView, final GlideLoadCallBack callback) {
        GlideApp.with(context.getApplicationContext()).load(uri).placeholder(R.drawable.bg_error).skipMemoryCache(true).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                imageView.setImageDrawable(resource);
                if (callback != null) {
                    callback.onLoadFinish();
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    /**
     * 加载drawable、mipmap的资源
     */
    public static void loadLocalPicture(Context context,int resourceId, ImageView imageView) {
        GlideApp.with(context.getApplicationContext()).load(resourceId).placeholder(R.drawable.bg_error).skipMemoryCache(true).into(imageView);
    }

    public static void invalidate(Context context) {
        clearMemory(context.getApplicationContext());
    }

    public static void preLoadPicture(Context context, String url) {
        try {
            GlideApp.with(context.getApplicationContext()).load(new MyGlideUrl(url)).preload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置Disk缓存、内存缓存的缓存key {getCacheKey}
     */
    private static class MyGlideUrl extends GlideUrl {
        private String mUrl;

        public MyGlideUrl(String url) {
            super(url);
            mUrl = url;
        }

        @Override
        public String getCacheKey() { //这个方法是指定缓存在内存中的key,若后面的key跟当前一致，则直接读取缓存显示
            return getUri();
        }

        private String getUri() {
            int paramIndex = mUrl.indexOf("?");
            if (paramIndex > 0) {
                mUrl = mUrl.substring(0, paramIndex);
            }
            return mUrl;
        }
    }


    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public interface GlideLoadCallBack {
        void onLoadFinish();
    }
}
