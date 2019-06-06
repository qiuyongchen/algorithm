package com.iloveqyc.daemon.service;

import com.iloveqyc.daemon.utils.http.HttpRequestParam;
import com.iloveqyc.daemon.utils.http.HttpResponse;
import com.iloveqyc.daemon.utils.http.OpenOkHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class DaemonService {

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() throws Exception {
        log.info("server system time: {}", new Date());

//        HttpResponse response;
//        HttpRequestParam httpRequestParam = new HttpRequestParam("http://www.baidu.com", null, null);
//        response = OpenOkHttpClient.getInstance().executePost(httpRequestParam);

    }

}
