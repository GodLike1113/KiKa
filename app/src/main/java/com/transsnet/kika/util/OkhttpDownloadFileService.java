package com.transsnet.kika.util;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Author:  zengfeng
 * Time  :  2020/7/7 15:49
 * Des   :
 */
public class OkhttpDownloadFileService extends Service {
//    String url = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3252521864,872614242&fm=26&gp=0.jpg";
    String url = "https://fin-apk-download.s3-eu-west-1.amazonaws.com/palmcredit-test/palmcredit-2.2.0-testing(214).apk";
    private OkHttpClient mOkHttpClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    public class Binder extends android.os.Binder {
        public OkhttpDownloadFileService getService() {
            return OkhttpDownloadFileService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                startDownloadPicture();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    public void startDownloadPicture() {
        Log.d("vivi","开始下载图片 --  start --");
        if (mOkHttpClient == null) {
            Log.d("vivi","mOkHttpClient为null");
            mOkHttpClient = new OkHttpClient();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                clear();
                Log.d("vivi","请求失败：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("vivi","请求成功"+Thread.currentThread().getName());
                if (response.code() != 200) {
                    Log.e("vivi","请求失败：" + response.code());
                    return;
                }

//                InputStream is = response.body().byteStream();
//                FileOutputStream fos = new FileOutputStream(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ad.jpg"));
//                byte[] buffer = new byte[1024];
//                int len = -1;
//                while ((len = is.read(buffer)) != -1) {
//                    fos.write(buffer, 0, len);
//                }
//                fos.close();
//                is.close();
//

                saveFile(response);
                clear();
            }

        });
    }


    public File saveFile(Response response) throws IOException {
        //生成存放文件的file
        File dir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
            if (!mkdirs) {
                //不能创建文件
            }
        }
        File file = new File(dir, "new.apk");
        //输出流
        Sink sink = Okio.sink(file);
        //输入流
        Source source = Okio.source(response.body().byteStream());
        //文件总大小
        final long totalSize = response.body().contentLength();
        //写入到本地存储空间中
        BufferedSink bufferedSink = Okio.buffer(sink);

        //写出，并且使用代理监听写出的进度。回调UI线程的接口
        bufferedSink.writeAll(new ForwardingSource(source) {
            long sum = 0;
            int oldRate = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long readSize = super.read(sink, byteCount);
                if (readSize != -1L) {
                    sum += readSize;

                    final int rate = Math.round(sum * 1F / totalSize * 100F);
                    if (oldRate != rate) {
                        Log.d("vivi","rate ="+rate+",totalSize ="+totalSize);
                        oldRate = rate;
                    }

                }
                return readSize;
            }
        });


        //刷新数据
        bufferedSink.flush();

        //关流
        Util.closeQuietly(sink);

        //关流
        Util.closeQuietly(source);

        return file;

    }

    public void clear() {
        mOkHttpClient = null;
        stopSelf();
    }
}
