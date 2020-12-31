package com.transsnet.kika.util;

import android.util.Log;

/**
 * Author:  zengfeng
 * Time  :  2020/12/18 13:54
 * Des   :
 */
public class LogUtils {
    public static String TAG = "vivi";

    public static void v(String info) {
        Log.v(TAG, info);
    }

    public static void v(String tag, String info) {
        Log.v(tag, info);
    }

    public static void d(String info) {
        Log.d(TAG, info);
    }

    public static void d(String tag, String info) {
        Log.d(tag, info);
    }


    public static void i(String info) {
        Log.i(TAG, info);
    }

    public static void i(String tag, String info) {
        Log.i(tag, info);
    }


    public static void w(String info) {
        Log.w(TAG, info);
    }

    public static void w(String tag, String info) {
        Log.w(tag, info);
    }


    public static void e(String info) {
        Log.e(TAG, info);
    }

    public static void e(String tag, String info) {
        Log.e(tag, info);
    }
} 
