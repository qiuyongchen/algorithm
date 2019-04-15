package com.iloveqyc.daemon.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ContainerNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static JsonGenerator jsonGenerator = null;
    private static final ObjectMapper objectMapper2 = new ObjectMapper();
    private static final String ERROR_TXT = "error";

    public JsonUtils() {
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException var2) {
            log.error("to json string exception", var2);
            return null;
        }
    }

    public static String objectToJson(Object object) {
        try {
            return objectMapper2.writeValueAsString(object);
        } catch (Exception var2) {
            log.error("to json exception", var2);
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            try {
                return objectMapper2.readValue(json, clazz);
            } catch (Exception var3) {
                log.error("error" + json, var3);
                return null;
            }
        }
    }

    public static String getSubString(String json, String path) {
        if (!StringUtils.isBlank(json) && !StringUtils.isBlank(path)) {
            try {
                JsonNode node = objectMapper2.readTree(json);
                JsonNode targetNode = node.at(JsonPointer.compile(path));
                return targetNode instanceof ContainerNode ? targetNode.toString() : targetNode.asText();
            } catch (Exception var4) {
                log.error("error" + json, var4);
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T jsonToObject(String json, Class<T> clazz, String path) {
        String subString = getSubString(json, path);
        return StringUtils.isNotBlank(subString) ? jsonToObject(subString, clazz) : null;
    }

    public static <T> List<T> jsonToList(String json, Class clazz) {
        if (StringUtils.isEmpty(json)) {
            return Collections.emptyList();
        } else {
            try {
                JavaType javaType = objectMapper2.getTypeFactory()
                                                 .constructParametricType(ArrayList.class, new Class[]{clazz});
                return (List) objectMapper2.readValue(json, javaType);
            } catch (IOException var3) {
                log.error("error" + var3.getMessage(), var3);
                return Collections.emptyList();
            }
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static <T> T toObject(String json, TypeReference<T> valueTypeRef) {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(json, valueTypeRef);
        } catch (IOException var3) {
            log.error("to Object exception", var3);
            return null;
        }
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static String toStr(Object model) throws IOException {
        return objectMapper.writeValueAsString(model);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static <T> T fromStr(String content, Class<T> clazz) throws IOException {
        return objectMapper.readValue(content, clazz);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static Map<String, Object> fromStrToMap(String content) throws IOException {
        return (Map) fromStr(content, Map.class);
    }

    static {
        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper2.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper2.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        objectMapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper2.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper2.setSerializationInclusion(Include.NON_NULL);
        objectMapper2.setLocale(Locale.CHINA);
    }

}