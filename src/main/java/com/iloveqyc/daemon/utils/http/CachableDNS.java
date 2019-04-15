package com.iloveqyc.daemon.utils.http;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CachableDNS {

    private static LoadingCache<String, InetAddress> dnsCache = CacheBuilder.newBuilder()
                                                                            .expireAfterWrite(10, TimeUnit.HOURS)
                                                                            .initialCapacity(100)  //初始缓存大小
                                                                            .maximumSize(1024)     //最大缓存大小
                                                                            .recordStats().build(new CacheLoader(){
                @Override
                public Object load(Object key) throws Exception {
                    return InetAddress.getByName((String) key);
                }
            });

    public static Dns dnsClient = new Dns() {
        @Override
        public List<InetAddress> lookup(String hostname) throws UnknownHostException {
            if (hostname == null) {
                throw new UnknownHostException("hostname == null");
            }

            try {

                // 返回域名实时解析结果
                List<InetAddress> inetAddresses = SYSTEM.lookup(hostname);
                dnsCache.put(hostname, inetAddresses.get(0));
                return inetAddresses;

            } catch (Exception e) {
                try {
                    // 返回application级别的缓存
                    return Lists.newArrayList(dnsCache.get(hostname));

                } catch (ExecutionException ignored) {

                    // 若缓存有问题，则稍后重试获取ip
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored2) {
                    }

                    List<InetAddress> inetAddresses = SYSTEM.lookup(hostname);
                    dnsCache.put(hostname, inetAddresses.get(0));
                    return inetAddresses;
                }

            }

        }
    };
}
