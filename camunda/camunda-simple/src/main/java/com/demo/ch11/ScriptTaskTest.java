package com.demo.ch11;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoupeng create on 2021-03-04
 */
public class ScriptTaskTest {

    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;


    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch11/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
    }


    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("脚本任务测试")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch11/ScriptTask.bpmn")
//                .addClasspathResource("com.demo/ch11/ScriptTask_var.bpmn")
//                .addClasspathResource("com.demo/ch11/scriptTask_juel.bpmn")
                .addClasspathResource("com.demo/ch11/scriptTask_external.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void startProcessInstanceByKey() {
        runtimeService.startProcessInstanceByKey("scriptTask");
    }


    @Test
    public void startProcessInstanceByKey2() {
        Map<String, Object> var = new HashMap<>();
        var.put("val", "var sum=10;execution.setVariable('var-foo',sum);");
        runtimeService.startProcessInstanceByKey("scriptTask", var);
    }


    @Test
    public void startProcessInstanceByKey3() {
        runtimeService.startProcessInstanceByKey("scriptTask");
    }


    @Test
    public void startProcessInstanceByKey4() {
        Map<String, Object> var = new HashMap<>();
        var.put("scriptPath", "com.demo/ch11/1.js");
        runtimeService.startProcessInstanceByKey("scriptTask", var);
    }
}
