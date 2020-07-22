package com.transsnet.kika.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.Method;

/**
 * Created by cks on 18-5-28.
 */

public class MemoryUtils {

    public static boolean hasAvailMemory(Context context){
        return getAvailMemory(context) > getAppMemoryClass();
    }

    public static long getAvailMemory(Context context) {// 获取android当前可用内存大小

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        //mi.availMem; 当前系统的可用内存
        //String result= Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化  ;
        long availMem =  mi.availMem/1024/1024;
        return availMem;
    }

    public static int getAppMemoryClass(){
        return staticGetMemoryClass();
    }

    static public int staticGetMemoryClass(){
        String vmHeapSize = getProperty("dalvik.vm.heapgrowthlimit","");
        if ((!TextUtils.isEmpty(vmHeapSize))&&(!vmHeapSize.equals("unknown"))){
            return Integer.parseInt(vmHeapSize.substring(0,vmHeapSize.length()-1));
        }
        return staticGetLargeMemoryClass();
    }

    static public int staticOnlyGetMemoryClass(){
        String vmHeapSize = getProperty("dalvik.vm.heapgrowthlimit","");
        if ((!TextUtils.isEmpty(vmHeapSize))&&(!vmHeapSize.equals("unknown"))){
            return Integer.parseInt(vmHeapSize.substring(0,vmHeapSize.length()-1));
        }
        return 0;
    }

    static public int staticGetLargeMemoryClass(){
        String vmHeapSize = getProperty("dalvik.vm.heapsize","16m");
        if ((!TextUtils.isEmpty(vmHeapSize))&&(!vmHeapSize.equals("unknown"))){
            return Integer.parseInt(vmHeapSize.substring(0,vmHeapSize.length()-1));
        } else {
            vmHeapSize = "16m";
            return Integer.parseInt(vmHeapSize.substring(0,vmHeapSize.length()-1));
        }
    }

    public static String getProperty(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, "unknown"));
        } catch (Exception e) {
            LogUtils.file(key,e.getMessage());
        }
        return value;
    }
}
