package com.demo.demo.domain;

import java.util.Map;

/**
 * @author zhoupeng
 */
public class ProcessStartParam {

    private String processKey;

    private Map<String, Object> params;

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ProcessStartParam{" +
                "processKey='" + processKey + '\'' +
                ", params=" + params +
                '}';
    }
}
