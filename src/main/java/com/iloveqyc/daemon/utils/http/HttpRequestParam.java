package com.iloveqyc.daemon.utils.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpRequestParam implements Serializable {

    private static final long serialVersionUID = 4431814491119855730L;

    /**
     * 连接线程池等待时长
     */
    private int connectionRequestTime = 5 * 1000;

    /**
     * 建立连接超时时长
     */
    private int connectTime = 30 * 1000;

    /**
     * 读取超时时长
     */
    private int socketTime = 60 * 1000;

    /**
     * 目标url
     */
    private String requestUrl;

    /**
     * 消息头
     */
    private Map<String, String> headerMap;

    /**
     * 必填，内容类型，如json（不要附带字符集）
     */
    private String contentType = "application/json";

    /**
     * 必填，内容编码字符集，如utf-8
     */
    private String contentEncodeCharset = "utf-8";

    /**
     * 消息体对象
     */
    private Object contentObject;

    /**
     * 对请求体执行url encode
     */
    private boolean needUrlEncodeBeforeRequest = false;

    /**
     * 对响应体执行url decode
     */
    private boolean needUrlDecodeAfterResponse = false;

    public HttpRequestParam(String requestUrl, Map<String, String> headerMap, Object contentObject) {
        this.requestUrl = requestUrl;
        this.headerMap = headerMap;
        this.contentObject = contentObject;
    }
}
