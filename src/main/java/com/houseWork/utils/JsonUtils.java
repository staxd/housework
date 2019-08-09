package com.houseWork.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;



public class JsonUtils {
	
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

   

    /**
     * json格式转换成对象
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("json转换对象出错,json={}", json, e);
        }
        return null;
    }

   

    /**
     * json格式转换成Map
     * 
     * @param jsonString
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMap4Json(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> maps = mapper.readValue(jsonString, Map.class);
            Iterator<String> iter = maps.keySet().iterator();
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                Object value = maps.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            log.error("json转换Map对象出错,json={}", jsonString, e);
        }
        return null;
    }
    
   

}