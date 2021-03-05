package com.demo.ch11;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoupeng create on 2021-03-05
 */
public class ServiceTaskTest {


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
                .name("服务任务测试")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch11/serviceTask.bpmn")
                .addClasspathResource("com.demo/ch11/serviceTask_class.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }

    @Test
    public void startProcessInstanceByKey() {
        String key = "serviceTask";
        Map<String, Object> var = new HashMap<>();
        var.put("bean", new ValueBean("100"));
        runtimeService.startProcessInstanceByKey(key, var);
    }

    @Test
    public void startProcessInstanceByKeyVar() {
        String key = "serviceTask";
        Map<String, Object> var = new HashMap<>();
        var.put("var", new ValueBean("200"));
        runtimeService.startProcessInstanceByKey(key, var);
    }
}
