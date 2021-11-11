package com.firecontrol.framework.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

/**
 * @Author soldier
 * @Date 20-6-18 上午9:05
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:华为云语音呼叫服务==》Json工具类
 */
public class JsonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();

        // 设置FAIL_ON_EMPTY_BEANS属性,当序列化空对象不要抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 设置FAIL_ON_UNKNOWN_PROPERTIES属性,当JSON字符串中存在Java对象没有的属性,忽略
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转换为JsonString
     */
    public static String jsonObj2Sting(Object jsonObj) {
        String jsonString = null;

        try {
            jsonString = objectMapper.writeValueAsString(jsonObj);
        } catch (IOException e) {
            System.out.printf("pasre json Object[{}] to string failed.", jsonString);
        }

        return jsonString;
    }

    /**
     * 将JsonString转换为对象
     */
    public static <T> T jsonString2SimpleObj(String jsonString, Class<T> cls) {
        T jsonObj = null;

        try {
            jsonObj = objectMapper.readValue(jsonString, cls);
        } catch (IOException e) {
            System.out.printf("pasre json Object[{}] to string failed.", jsonString);
        }

        return jsonObj;
    }

    /**
     * 将对象转换为ObjectNode的方法
     *
     * @param object    源数据；如果为空，则返回空。
     * @return 转换后返回ObjectNode数据。
     */
    public static <T> ObjectNode convertObject2ObjectNode(T object) throws Exception {
        if (null == object) {
            return null;
        }

        ObjectNode objectNode = null;

        if (object instanceof String) {
            objectNode = convertJsonStringToObject((String) object, ObjectNode.class);
        } else {
            objectNode = convertValue(object, ObjectNode.class);
        }

        return objectNode;
    }

    /**
     * 将json字符串按类型（cls）转换为目标
     * @param jsonString 源json字符串；如果为空，则返回空
     * @param cls        目标数据类型
     */
    public static <T> T convertJsonStringToObject(String jsonString, Class<T> cls) throws Exception {
        if (StringUtil.strIsNullOrEmpty(jsonString)) {
            return null;
        }

        try {
            T object = objectMapper.readValue(jsonString, cls);
            return object;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 将从给定值转换为给定值类型的实例的方法
     */
    private static <T> T convertValue(Object fromValue, Class<T> toValueType) throws Exception {
        try {
            return objectMapper.convertValue(fromValue, toValueType);
        } catch (IllegalArgumentException e) {
            throw new Exception(e);
        }
    }
}