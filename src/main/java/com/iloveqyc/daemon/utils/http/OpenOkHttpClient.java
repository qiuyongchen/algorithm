package com.iloveqyc.daemon.utils.http;

import com.iloveqyc.daemon.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class OpenOkHttpClient {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static class Holder {
        private static OpenOkHttpClient openOkHttpClient = new OpenOkHttpClient();
    }

    public static OpenOkHttpClient getInstance() {
        return Holder.openOkHttpClient;
    }

    public HttpResponse executePost(HttpRequestParam httpRequestParam) throws IOException {

        Request postRequest = buildPostRequest(httpRequestParam);
        Call postCall = OKHttpClientFactory.getClient().newCall(postRequest);
        log.info("execute okHttp post request starting, httpRequestParam:{}", httpRequestParam);

        long requestStartTime = System.currentTimeMillis();
        Response response = postCall.execute();
        long requestEndTime = System.currentTimeMillis();

        HttpResponse httpResponse = buildPostResponse(httpRequestParam, response);
        httpResponse.setContext(httpResponse.new RespContext(requestStartTime, requestEndTime, requestEndTime - requestStartTime));
        log.info("execute okHttp post request done, httpRequestParam:{}, resp :{}",
                 httpRequestParam,
                 httpResponse);
        return httpResponse;
    }

    /**
     * 构建post请求体
     * @param requestParam post参数
     * @return post请求体
     */
    private Request buildPostRequest(HttpRequestParam requestParam) {
        if (StringUtils.isBlank(requestParam.getContentEncodeCharset()) ||
            requestParam.getContentEncodeCharset().toLowerCase().contains("charset")) {
            throw new RuntimeException("字符集编码设置有误");
        }
        requestParam.setContentEncodeCharset(requestParam.getContentEncodeCharset().toLowerCase());

        if (StringUtils.isBlank(requestParam.getContentType()) ||
            requestParam.getContentType().toLowerCase().contains("charset")) {
            throw new RuntimeException("内容类型设置有误");
        }
        requestParam.setContentType(requestParam.getContentType().toLowerCase());

        MediaType mediaType = MediaType.parse(requestParam.getContentType() +
                                              ";charset=" +
                                              requestParam.getContentEncodeCharset());
        if (mediaType == null) {
            throw new RuntimeException("ContentType设置有误");
        }

        // 构建消息头
        Headers.Builder headerBuilder = getHeaderBuilder(requestParam.getHeaderMap());
        headerBuilder.add(CONTENT_TYPE_HEADER, mediaType.toString());

        // 构建消息体
        RequestBody requestBody = null;
        if (requestParam.getContentObject() instanceof Map) {
            //noinspection unchecked
            requestBody = buildFormByParams(
                    (Map<String, Object>) requestParam.getContentObject(), mediaType.charset())
                    .build();

        } else if (requestParam.getContentObject() instanceof String) {
            String contentString = (String) requestParam.getContentObject();
            if (StringUtils.isNotBlank(contentString)) {

                // 兼容历史部分供应商encode
                if (requestParam.isNeedUrlEncodeBeforeRequest()) {
                    try {
                        contentString = RestCodec.encodeData(contentString);
                    } catch (Exception e) {
                        throw new RuntimeException("对request执行encode时出现异常" + e.getMessage());
                    }
                }

                requestBody = RequestBody.create(mediaType, contentString);
            }
        }

        Request.Builder requestBuilder = new Request.Builder()
                .url(requestParam.getRequestUrl())
                .headers(headerBuilder.build());
        if (requestBody != null) {
            requestBuilder = requestBuilder.post(requestBody);
        }
        return requestBuilder.build();
    }

    private FormBody.Builder buildFormByParams(Map<String, Object> paramMap, Charset encodeCharset) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder(encodeCharset);

        for (String k : paramMap.keySet()) {
            String destValue;
            if (paramMap.get(k) instanceof String) {
                destValue = (String) paramMap.get(k);
            } else {
                destValue = JsonUtils.objectToJson(paramMap.get(k));
            }
            if (destValue != null) {
                formBodyBuilder.add(k, destValue);
            }
        }

        return formBodyBuilder;
    }

    private static Headers.Builder getHeaderBuilder(Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder;
    }

    private HttpResponse buildPostResponse(HttpRequestParam httpRequestParam, Response response) throws IOException {
        String respContent = null;
        if (response.body() != null) {

            Charset charset = Charset.forName(httpRequestParam.getContentEncodeCharset());
            // 优先使用对方返回的编码字符集，否则使用默认字符集
            String contentTypeHeader = response.header("Content-Type");
            if (StringUtils.isNotBlank(contentTypeHeader)) {
                MediaType mediaType = MediaType.parse(contentTypeHeader);
                if (mediaType != null) {
                    charset = mediaType.charset(charset);
                }
            }

            respContent = new String(response.body().bytes(), Objects.requireNonNull(charset));
        }
        if (StringUtils.isBlank(respContent)) {
            return new HttpResponse();
        }

        // 兼容历史部分供应商decode
        if (httpRequestParam.isNeedUrlDecodeAfterResponse()) {
            respContent = RestCodec.decodeData(respContent);
        }
        return HttpResponse.builder()
                           .protocol(response.protocol().toString())
                           .code(response.code())
                           .message(response.message())
                           .headers(response.headers().toMultimap())
                           .content(respContent)
                           .build();
    }
}
