package com.demo.demo.domain;

import java.util.Map;

/**
 * @author zhoupeng
 */
public class TaskDoParam {

    private String taskId;

    private Map<String, Object> params;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "TaskDoParam{" +
                "taskId='" + taskId + '\'' +
                ", params=" + params +
                '}';
    }
}
