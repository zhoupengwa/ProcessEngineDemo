package com.demo.ch15;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class SignalThrowAndBoundaryNoCancelTest {

    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;
    private FilterService filterService;
    private ManagementService managementService;

    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch15/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
        filterService = processEngine.getFilterService();
        processEngine.getManagementService();
    }


    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("抛出信号与非终止边界信号")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch15/signal_boundary_nocancel.bpmn")
                .addClasspathResource("com.demo/ch15/signal_throw2.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }

    @Test
    public void startProcessInstanceByKey() {
        runtimeService.startProcessInstanceByKey("signalBoundaryNoCancel");
    }

    @Test
    public void startProcessInstanceByKeyThrow() {
        runtimeService.startProcessInstanceByKey("signalThrow2");
    }

    @Test
    public void complete() {
        taskService.complete("8406");
    }
}
