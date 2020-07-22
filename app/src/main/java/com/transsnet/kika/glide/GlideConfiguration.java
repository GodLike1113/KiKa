package com.transsnet.kika.glide;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.transsnet.kika.Contstant;
import com.transsnet.kika.util.AppUtil;
import com.transsnet.kika.util.MemoryUtils;

import java.io.File;


/**
 * Author:  zengfeng
 * Time  :  2020/7/16 11:19
 * Des   :  Glide相关策略；1.build.gradle中添加两行依赖；
 *             2.直接build生成GlideApp即可,
 *             3.记得加Internet权限，被坑过
 */
@GlideModule
public class GlideConfiguration extends AppGlideModule {
    public static final int DISK_CACHE_SIZE = 1024 * 1024 * 500;
    public static final String DISK_CACHE_NAME = "disk_cache_glide";

    @Override
    public void registerComponents(
            @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // Default empty impl.

    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        Log.d("vivi","applyOptions");
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int memoryCacheSize;

        int count = MemoryUtils.staticOnlyGetMemoryClass();
        if (count == 0) {
            memoryCacheSize = 15 * 1024 * 1024;
        } else {
            memoryCacheSize = maxMemory / 8;
        }
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        File cacheDir = context.getExternalFilesDir("cache");
        int diskSize = DISK_CACHE_SIZE;
        if (cacheDir != null) {
            builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), DISK_CACHE_NAME, diskSize));
        }
        if (AppUtil.getMaxMemory() < Contstant.LOW_LEVEL_MEMORY) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.format(DecodeFormat.PREFER_RGB_565);
            builder.setDefaultRequestOptions(requestOptions);
        }
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}