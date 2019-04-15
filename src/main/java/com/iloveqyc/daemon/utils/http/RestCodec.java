package com.iloveqyc.daemon.utils.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.codec.binary.Base64;

public class RestCodec {
    private RestCodec() {
    }

    public static String decodeData(String data) {
        try {
            return URLDecoder.decode(data, "utf-8");
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    public static String encodeData(String binaryData) throws Exception {
        try {
            if (null == binaryData) {
                String str = null;
                return (String)str;
            } else {
                return URLEncoder.encode(binaryData, "utf-8");
            }
        } catch (UnsupportedEncodingException var3) {
            throw new Exception(var3.getMessage(), var3);
        }
    }

    static String encodeDataWithBase64(String binaryData) throws Exception {
        try {
            if (null == binaryData) {
                String str = null;
                return (String)str;
            } else {
                return Base64.encodeBase64String(binaryData.getBytes("utf-8"));
            }
        } catch (UnsupportedEncodingException var3) {
            throw new Exception(var3.getMessage(), var3);
        }
    }
}
