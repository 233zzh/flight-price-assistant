package com.zihao;

import javax.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhangzhihao06 <zhangzhihao06@kuaishou.com>
 * Created on 2023-08-01
 */
public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static String toJSON(@Nullable Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
           e.printStackTrace();
        }
        return null;
    }
}
