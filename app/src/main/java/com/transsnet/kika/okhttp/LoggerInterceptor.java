package com.transsnet.kika.okhttp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * 说明: 网络请求Log拦截器 <br/>
 * 邮箱: yangbin5052@gmail.com <br/>
 * Create at 2017/12/22-上午9:29 by Luuren
 */
public class LoggerInterceptor implements Interceptor {

  public static final String TAG = "http";
  private String tag;
  private Context mContext;

  public LoggerInterceptor(String tag, Context context) {
    if (TextUtils.isEmpty(tag)) {
      tag = TAG;
    }
    this.tag = tag;
    this.mContext = context;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    logForRequest(request);
    Request gzipRequest = urlProcess(request);
    Response response = chain.proceed(gzipRequest);
    return logForResponse(response);
  }

  private Response logForResponse(Response response) {
    try {
      //===>response log
      Log.i(tag,"================response'log===============begin");
      Response.Builder builder = response.newBuilder();
      Response clone = builder.build();
      String url = clone.request().url().toString();
      Log.i(tag, "url : " + url);
      Log.i(tag,  "code : " + clone.code());
      Log.i(tag,  "protocol : " + clone.protocol());
      Log.i(tag,  "headers : " + clone.headers().toString());
      if (!TextUtils.isEmpty(clone.message())) {
        Log.i(tag, "message : " + clone.message());
      }

      {
        ResponseBody body = clone.body();
//        if(clone.code()==401){
//          String time =response.headers().get(Contstant.TIMESTAMP);
//          if ( !TextUtils.isEmpty(time)) {
//            long severTime =Long.valueOf(time);
//            PreferenceUtil.setServiceDiffTime(mContext,severTime - (System.currentTimeMillis()/1000));
//            Log.i(tag,"http"+"--401--"+time);
//            return response;
//          }
//        }

        if (body != null) {
          MediaType mediaType = body.contentType();
          if (mediaType != null) {

            if (isText(mediaType)) {
              String resp = body.string();
              Log.i(tag, "responseBody's content : " + resp);
              return response.newBuilder().body(ResponseBody.create(mediaType, resp)).build();
            } else {
              Log.d(tag,  "responseBody's content : " + " maybe [file part] , too large too print , ignored!");
            }
          }
        }
      }

     Log.i(tag, "================response'log===============end");
    } catch (Exception e) {
      Log.e("logForResponse",e.getMessage());
    }

    return response;
  }

  private void logForRequest(Request request) {
    try {
      String url = request.url().toString();
      Headers headers = request.headers();

      Log.i(tag,  "================request'log===========begin");
      Log.i(tag,  "method : " + request.method());
      Log.i(tag,  "url : " + url);
      Log.i(tag+"","-logForRequest--url=="+url);
      if (headers != null && headers.size() > 0) {
        Log.v(tag,"-headers=="+headers.toString());
      }
      RequestBody requestBody = request.body();
      if (requestBody != null) {
        MediaType mediaType = requestBody.contentType();
        if (mediaType != null) {
          Log.i(tag, "requestBody's contentType : " + mediaType.toString());
          if (isText(mediaType)) {
            Log.i(tag, "requestBody's content : " + bodyToString(request));
//            TcStatInterface.onEvent(tag+"","-requestBody's content==",bodyToString(request));
          } else {
            Log.i(tag, "requestBody's content : " + " maybe [file part] , too large too print , ignored!");
          }
        }
      }
      Log.i(tag, "================request'log===============end");
    } catch (Exception e) {
      Log.e(tag,"logForRequest"+e.getMessage()+"---url==="+request.url().toString());
    }
  }

  private boolean isText(MediaType mediaType) {
    if (mediaType.type() != null && mediaType.type().equals("text")) {
      return true;
    }
    if (mediaType.subtype() != null) {
      if (mediaType.subtype().equals("json") ||
          mediaType.subtype().equals("xml") ||
          mediaType.subtype().equals("html") ||
          mediaType.subtype().equals("webviewhtml")
          ) {
        return true;
      }
    }
    return false;
  }

  private String bodyToString(final Request request) {
    try {
      final Request copy = request.newBuilder().build();
      final Buffer buffer = new Buffer();
      copy.body().writeTo(buffer);
      return buffer.readUtf8();
    } catch (final IOException e) {
      return "something error when show requestBody.";
    }
  }

  private String getUrlForCrypt(String url){
    if (TextUtils.isEmpty(url) ) return null;
    int index = url.lastIndexOf("/");
    int index2;
    String newurl=url;
    if (url.contains("?")){
      index2 = url.lastIndexOf("?");
      newurl = url.substring(index+1,index2);
    } else {
      newurl = url.substring(index+1,url.length());
    }

    LogUtils.i(TAG,"newurl : "+newurl);
    return newurl;
  }


  private RequestBody gzipBody(Request request){
    String body = bodyToString (request);
    GZIPUtils.compress(body);
    MediaType mediaType = request.body().contentType();
    RequestBody gzipbBody = RequestBody.create(mediaType, GZIPUtils.compress(body));
    return gzipbBody;
  }

  private Request urlProcess(Request request){
    RequestBody requestBody = gzipBody(request);
//    String url = UrlUtil.urlProcessing(request.url().toString(),requestBody,mContext);
//    Log.i(tag,"urlProcess url: "+url);
    String url = request.url().toString();

    Request encryptRequest;
    if ("GET".equals(request.method())){
      encryptRequest = request.newBuilder()
              .url(url)
              .get().build();
    } else {
      encryptRequest = request.newBuilder()
              .url(url)
              .post(requestBody).build();
    }
    return encryptRequest;
  }
}
