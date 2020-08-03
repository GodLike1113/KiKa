package com.transsnet.kika.okhttp;

import android.content.Context;


import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by starwei on 18-8-3.
 */

public class OkhtttpUtil {
    /**
     *为了安全，更改HTTPS的认证方式
     * 1、hostnameVerifier使用Okhttp默认方式
     * 2、加载自有证书失败后，使用Okhttp默认的方案
     * 注意：若后台需要指定证书文件，则采用此种构建，否则默认采取下面的默认
     */
//    public static OkHttpClient makeHttpClient(Context context,Interceptor interceptor) {
//        OkHttpClient.Builder builder;
//        builder = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .addInterceptor(
//                        new LoggerInterceptor("http", context)
//                )
//                // .hostnameVerifier(new TrustHostnameVerifier())
//                .connectTimeout(60000, TimeUnit.MILLISECONDS)
//                .readTimeout(60000, TimeUnit.MILLISECONDS)
//                .pingInterval(20, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true);
//        SSLSocketFactory sslSocketFactory = SslUtil.getSSLSocketFactory();
//        if (sslSocketFactory != null) {
//            builder.sslSocketFactory(sslSocketFactory);
//        }
//        return builder.build();
//    }

    public static OkHttpClient makeHttpClient(Context context,Interceptor interceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(
                        new LoggerInterceptor("http", context)
                )
                .sslSocketFactory(SslUtil.getSSLSocketFactory())
                .hostnameVerifier(new TrustHostnameVerifier())
                .connectTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .pingInterval(20,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }




    public static MediaType getMediaType(){
        return MediaType.parse("application/json; charset=utf-8");
    }


    public static String getEncoding(){
        return "gzip";
    }
}
