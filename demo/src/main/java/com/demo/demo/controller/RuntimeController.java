package com.demo.demo.controller;

import com.demo.demo.domain.ProcessStartParam;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhoupeng
 */

@RestController
@RequestMapping("/runtime")
public class RuntimeController {
    @Autowired
    private RuntimeService runtimeService;


    /**
     * 执行流程
     */
    @PostMapping("/process")
    public String process(@RequestBody ProcessStartParam param) {
        Map<String, Object> params = param.getParams();
        String processKey = param.getProcessKey();
        //指定执行我们刚才部署的工作流程
        //取得流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processKey, "mykey", params);
        String result = String.format("执行对象ID:%s\n流程实例ID:%s\n流程定义ID:%s", pi.getId(), pi.getId(), pi.getProcessDefinitionId());
        System.out.println(result);
        return result;
    }

    /**
     * 获取流程实例的状态
     */
    @GetMapping("/processInstanceState")
    public String getProcessInstanceState(String processInstanceId) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();//返回数据要么是单行，要么是空，要么报错
        String result;
        if (pi != null) {
            result = "该流程实例 " + processInstanceId + " 正在运行..." + "当前活动的任务:" + pi.getActivityId();
        } else {
            result = "当前的流程实例 " + processInstanceId + " 已经结束！";
        }
        System.out.println(result);
        return result;
    }


    @PutMapping("/deleteProcessInstanceState")
    public String deleteProcessInstanceState(String processInstanceId, String reason) {
        runtimeService.deleteProcessInstance(processInstanceId, reason);
        return "SUCCESS";
    }

}
