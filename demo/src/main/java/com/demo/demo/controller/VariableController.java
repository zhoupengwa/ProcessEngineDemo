package com.demo.demo.controller;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoupeng
 */
@RestController
@RequestMapping("/variable")
public class VariableController {


    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;


    @PostMapping("/task/setVariable")
    public String setTaskVariable(String taskId, @RequestBody Map<String, Object> params) {
        taskService.setVariables(taskId, params);
        return "SUCCESS";
    }

    @PostMapping("/task/setVariableLocal")
    public String setVariableLocal(String taskId, @RequestBody Map<String, Object> params) {
        taskService.setVariablesLocal(taskId, params);
        return "SUCCESS";
    }


    @GetMapping("/task/getVariable")
    public String getTaskVariable(String taskId) {
        Map<String, Object> variables = taskService.getVariables(taskId);
        System.out.println(variables.toString());
        return variables.toString();
    }


    @PostMapping("/runtime/setVariable")
    public String setRuntimeVariable(String executionId, @RequestBody Map<String, Object> params) {
        runtimeService.setVariables(executionId, params);
        return "SUCCESS";
    }


    @PostMapping("/runtime/setVariableLocal")
    public String setRuntimeVariableLocal(String executionId, @RequestBody Map<String, Object> params) {
        runtimeService.setVariablesLocal(executionId, params);
        return "SUCCESS";
    }


    @GetMapping("/runtime/getVariable")
    public String getRuntimeVariable(String executionId) {
        Map<String, Object> variables = runtimeService.getVariables(executionId);
        System.out.println(variables.toString());
        return variables.toString();
    }


    /**
     * 通过runtime service 设置流程变量
     */
    public void test() {
        String executionId = "";
        String variableName = "";
        String values = "";
        runtimeService.setVariable(executionId, variableName, values);
        runtimeService.setVariableLocal(executionId, variableName, variableName);


        //设置本执行对象的变量 ，该变量的作用域只在当前的execution对象
        //可以设置多个变量  放在 Map<key,value>  Map<String,Object>
        Map<String, Object> map = new HashMap<>(1);
        runtimeService.setVariables(executionId, map);
    }


    /**
     * 通过task service 设置流程变量
     */
    public void test2() {
        String taskId = "";
        String variableName = "";
        String values = "";
        taskService.setVariable(taskId, variableName, values);

        //多个
        Map<String, Object> map = new HashMap<>(1);
        taskService.setVariables(taskId, map);
    }

    /**
     * 流程开始的时候设置变量
     */
    public void test3() {
        String processKey = "";
        Map<String, Object> map = new HashMap<>(1);
        runtimeService.startProcessInstanceByKey(processKey, map);
    }

    /**
     * 当任务完成时候，可以设置流程变量
     */
    public void test4() {
        String taskId = "";
        Map<String, Object> map = new HashMap<>(1);
        taskService.complete(taskId, map);
    }

    public void test5() {
        String executionId = "";
        String variableName = "";
        //取变量
        runtimeService.getVariable(executionId, variableName);
        //取本执行对象的某个变量
        runtimeService.getVariableLocal(executionId, variableName);
        //取当前执行对象的所有变量
        runtimeService.getVariables(executionId);
    }

    public void test6() {
        String taskId = "";
        String variableName = "";
        //取变量
        taskService.getVariable(taskId, variableName);
        //取本执行对象的某个变量
        taskService.getVariableLocal(taskId, variableName);
        //取当前执行对象的所有变量
        taskService.getVariables(taskId);
    }


}
