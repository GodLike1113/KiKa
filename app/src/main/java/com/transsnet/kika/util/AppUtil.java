package com.transsnet.kika.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by starwei on 18-7-6.
 */

public class AppUtil {

    public static String getVersionCode(Context context){
        String verCode="";
        try {
            verCode =""+ context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static String getVersionName(Context context){
        String verName= "";
        try{
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch ( Exception e){
            e.getMessage();
        }
        return  verName;
    }

    public static int getMaxMemory() {
        Runtime rt = Runtime.getRuntime();
        int maxMemory =(int) rt.maxMemory()/(1024*1024);
        return maxMemory;
    }


}
