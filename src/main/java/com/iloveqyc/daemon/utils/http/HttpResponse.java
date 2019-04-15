package com.iloveqyc.daemon.utils.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse implements Serializable {

    private static final long serialVersionUID = -2642970421442217327L;

    /**
     * 请求协议，大部分为http/1.1
     */
    private String protocol;

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应状态码说明
     */
    private String message;

    private Map<String, List<String>> headers;

    /**
     * 响应上下文
     */
    private RespContext context;

    /**
     * 响应正文
     */
    private String content;

    @Data
    public class RespContext implements Serializable {

        private static final long serialVersionUID = 8651526292629709564L;

        /**
         * 请求开始时间戳
         */
        private long requestStartTime;

        /**
         * 得到响应时间戳
         */
        private long requestEndTime;

        /**
         * 响应耗时(ms)
         */
        private long takeTime;

        public RespContext(long requestStartTime, long requestEndTime, long takeTime) {
            this.requestStartTime = requestStartTime;
            this.requestEndTime = requestEndTime;
            this.takeTime = takeTime;
        }
    }
}
