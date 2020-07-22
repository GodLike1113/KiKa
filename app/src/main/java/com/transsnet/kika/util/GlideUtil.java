package com.transsnet.kika.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.transsnet.kika.R;


import java.io.File;
import java.security.MessageDigest;

/**
 * Created by rst on 2018/5/24.
 */

public class GlideUtil {

    public static class CustomTransformation extends BitmapTransformation {

        int targetWidth;
        public CustomTransformation(Context context,int targetWidth) {
            this(context);
            this.targetWidth = targetWidth;
        }

        public CustomTransformation(Context context){
//            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap bitmap, int outWidth, int outHeight) {
            if (bitmap.getWidth() == 0) {
                return bitmap;
            }
            double aspectRatio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
            int targetHeight;
            if(aspectRatio>1.3){
                targetHeight=(int) (targetWidth * 1.3);
            }else {
                targetHeight = (int) (targetWidth * aspectRatio);
            }
            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);
                if (result != bitmap) {
                    bitmap.recycle();
                }
                return result;
            } else {
                return bitmap;
            }
        }

//        @Override
//        public String getId() {
//            return "transformation" + " desiredWidth";
//        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    /**
     * 从URI加载图片
     */
    public static void loadPicture(Context context, String url) {
        try {
            Glide.with(context).load(new MyGlideUrl(url)).preload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 从URI加载图片
     */
    public static void loadPicture(Context context, String url, ImageView imageView) {
        try {
            Glide.with(context).load(new MyGlideUrl(url)).placeholder(R.drawable.bg_error).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从URI加载图片
     */
    public static void loadPicture(Context context, String url, ImageView imageView,boolean useCache) {
        try {
            if(useCache){
                Glide.with(context).load(new MyGlideUrl(url)).placeholder(R.drawable.bg_error).into(imageView);
            }else{
                Glide.with(context).load(new MyGlideUrl(url)).placeholder(R.drawable.bg_error).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从URI加载图片
     */
    public static void loadPicturefloat(Context context, String url, ImageView imageView,int width) {
        try {
            Glide.with(context).load(new MyGlideUrl(url)).placeholder(R.drawable.bg_error).transform(new CustomTransformation(context,width)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 从URI加载图片,有error状态默认图片
     */
    public static void loadPicture(Context context, String url, ImageView imageView, int errorResId) {
        try {
            Glide.with(context).load(new MyGlideUrl(url)).placeholder(errorResId).error(errorResId).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从资源文件加载图片,有error状态默认图片
     */
    public static void loadPicture(Context context,int resourceId, ImageView imageView) {
        try {
            Glide.with(context).load(resourceId).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从URI加载图片,有error状态默认图片
     */
    public static void loadPicture(Context context, String url, ImageView imageView, int errorResId,boolean useCache) {
        try {
            if(useCache){
                Glide.with(context).load(new MyGlideUrl(url)).placeholder(errorResId).error(errorResId).into(imageView);
            }else{
                Glide.with(context).load(new MyGlideUrl(url)).placeholder(errorResId).error(errorResId).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    } /**
     * 从File加载图片,有error状态默认图片
     */
    public static void loadPicture(Context context, File url, ImageView imageView, int errorResId) {
        try {
            Glide.with(context).load(url).placeholder(errorResId).error(errorResId).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
//     * 加载本地图片到非imageview
//     */
//    public static void loadPicture(Context context, Uri uri, ImageView imageView, Callback callback) {
//        try {
////            File file = new File(filePath);
//            Picasso.get().load(uri).placeholder(R.drawable.bg_error).memoryPolicy(NO_CACHE, NO_STORE).into(imageView, callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    /**
     *
     */
    public static void loadPicture(Context context, File file, ImageView targetView) {
        try {
//            Glide.with(context).load(file).transform(getTransformation(context,targetView)).into(targetView);
            Glide.with(context).load(file).into(targetView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadCirclePicture(Context context, String url, ImageView imageView) {
        try {
            Glide.with(context).load(new MyGlideUrl(url)).placeholder(R.drawable.bg_error).transform(getCircleTransformation(context,imageView)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Transformation getTransformation(Context context, final ImageView view) {
        return new BitmapTransformation() {
            final String ID = GlideUtil.class.getCanonicalName();
            final byte[] ID_BYTES = ID.getBytes(CHARSET);

//            @Override
//            public boolean equals(@Nullable Object obj) {
//                return obj instanceof BitmapTransformation;
//            }

            @Override
            public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
                messageDigest.update(ID_BYTES);
            }

//            @Override
//            public int hashCode() {
//                return ID.hashCode();
//            }

            @Override
            protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
                int targetWidth = view.getWidth();
                //返回原图
                if (source.getWidth() == 0 || source.getWidth() < targetWidth) {
                    return source;
                }
                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight == 0 || targetWidth == 0) {
                    return source;
                }
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

//            public String getId() {
//                return "transformation" + " desiredWidth";
//            }
        };
    }

    /**
     * 圆形图片
     */
    private static Transformation getCircleTransformation(Context context, final ImageView view) {
        return new CircleTransform(context);
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
//            super(context);
        }
        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
            int size = Math.min(source.getWidth(),
                    source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }
            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            squaredBitmap.recycle();
            return bitmap;
        }
//
//        @Override
//        public String getId() {
//            return "circle";
//        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }

    private static class MyGlideUrl extends GlideUrl {
        private String mUrl;
        public MyGlideUrl(String url) {
            super(url);
            mUrl = url;
        }

        @Override
        public String getCacheKey() {
            return getUri();
        }

        private String getUri() {
            int paramIndex = mUrl.indexOf("?");
            if(paramIndex>0){
                return mUrl.substring(0,paramIndex);
            }
            return mUrl;
        }
    }


}
