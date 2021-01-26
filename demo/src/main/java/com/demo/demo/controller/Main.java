package com.demo.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.demo.demo.domain.TaskDoParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoupeng
 */
public class Main {

    private static volatile JsonMapper jsonMapper;

    public static JsonMapper getInstance() {
        if (jsonMapper == null) {
            synchronized (Main.class) {
                if (jsonMapper == null) {
                    jsonMapper = new JsonMapper();
                }
            }
        }
        return jsonMapper;
    }

    public static void main(String[] args) throws JsonProcessingException {
        TaskDoParam param = new TaskDoParam();
        param.setTaskId("2504");
        Map<String, Object> map = new HashMap<>(1);
        map.put("1", 1);
        map.put("2", "22");
        param.setParams(map);
        System.out.println(getInstance().writeValueAsString(param));
    }
}
