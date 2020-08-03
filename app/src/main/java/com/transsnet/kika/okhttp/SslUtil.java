package com.transsnet.kika.okhttp;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by starwei on 18-8-1.
 */

public class SslUtil {

    public static SSLSocketFactory getSSLSocketFactory(){

        try {
            SSLContext sc = null;
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore trusted = KeyStore.getInstance("PKCS12", "BC");
            trusted.load(null, null);
            InputStream in =  new ByteArrayInputStream(CrtConstant.CRT.getBytes("UTF-8"));
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(in);
            trusted.setCertificateEntry("trust", certificate);
            in.close();

            tmf.init(trusted);

            TrustManager[] tms = tmf.getTrustManagers();

            sc = SSLContext.getInstance("TLS");
            sc.init(null, tms, new SecureRandom());
            return sc.getSocketFactory();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}
