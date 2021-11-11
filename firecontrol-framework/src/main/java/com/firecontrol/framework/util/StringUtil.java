package com.firecontrol.framework.util;

/**
 * @Author soldier
 * @Date 20-6-18 上午9:05
 * @Email:583406411@qq.com
 * @Version 1.0
 * @Description:华为云语音呼叫服务==》String工具类
 */
public class StringUtil {

    /**
     * 判断是否为空
     */
    public static boolean strIsNullOrEmpty(String s) {
        return (null == s || s.trim().length() < 1);
    }
}