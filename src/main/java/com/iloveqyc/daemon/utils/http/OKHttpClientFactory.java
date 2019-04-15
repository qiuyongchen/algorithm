package com.iloveqyc.daemon.utils.http;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OKHttpClientFactory {
    /**
     * 1.DNS缓存
     * 2.失败重连 && 自愈重试 && 自动转发
     * 3.Get响应缓存 && Http2连接复用
     * 4.持续连接5Min（避免过多3次握手）
     */
    private static okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().connectTimeout(180, TimeUnit.SECONDS)
                                                                                   .readTimeout(180, TimeUnit.SECONDS)
                                                                                   .dns(CachableDNS.dnsClient)
                                                                                   .retryOnConnectionFailure(true)
                                                                                   .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                                                                                   .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                                                                                   .build();


    static {
        // 最大维持256个连接
        client.dispatcher().setMaxRequests(256);
    }

    /**
     * 预加载client
     * @return
     */
    public static OkHttpClient getClient() {
        return client;
    }
}