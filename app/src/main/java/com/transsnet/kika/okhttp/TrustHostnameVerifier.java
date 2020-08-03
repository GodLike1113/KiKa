package com.transsnet.kika.okhttp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
/**
 * Author:  zengfeng
 * Time  :  2020/8/3 14:56
 * Des   :
 */

/**
 * Created by starwei on 18-8-1.
 */

public class TrustHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}