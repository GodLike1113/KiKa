package com.transsnet.kika.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:  zengfeng
 * Time  :  2018/11/26 16:51
 * Des   :  线程池操作工具类
 */
public class ThreadPoolUtil {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2,Math.min(CPU_COUNT -1 ,4));
    private static ThreadPoolUtil instance;
    private ExecutorService threadPool;

    private ThreadPoolUtil() {
        threadPool = Executors.newFixedThreadPool(CORE_POOL_SIZE);
    }


    public static synchronized ThreadPoolUtil getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolUtil.class) {
                if (instance == null) {
                    instance = new ThreadPoolUtil();
                }
            }
        }
        return instance;
    }


    public void execute(Runnable runnable) {
        try {
            if (instance != null) { //Monkey测试异常捕获
                threadPool.execute(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutDown(){
        if(instance!=null){
            threadPool.shutdown();
        }
    }

    public void shutDownNow(){
        if(instance!=null){
            threadPool.shutdownNow();
        }
    }
}
