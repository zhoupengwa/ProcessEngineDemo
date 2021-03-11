package com.demo.ch19;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.Job;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class AsyncCatchTest {

    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;
    private FilterService filterService;
    private ManagementService managementService;
    private ExternalTaskService externalTaskService;

    private FormService formService;

    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch19/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
        filterService = processEngine.getFilterService();
        processEngine.getManagementService();
        externalTaskService = processEngine.getExternalTaskService();
        formService = processEngine.getFormService();
        managementService = processEngine.getManagementService();
    }


    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("定时器测试")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch19/async-catch.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void startProcessInstanceByKey() {
        runtimeService.startProcessInstanceByKey("async");
    }


    @Test
    public void executeJob() {
        String jobId = "8805";
        managementService.executeJob(jobId);
    }



    @Test
    public void complete() {
        taskService.complete("8309");
    }



    @Test
    public void createJobQuery() {
        List<Job> list = managementService.createJobQuery()
                //.active()
                //.processDefinitionId()
                .list();
        System.out.println(list);
    }
}
