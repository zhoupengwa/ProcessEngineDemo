package com.demo.ch10;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhoupeng
 */
public class TaskServiceTest {

    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;


    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch10/camunda.cfg.xml");
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
                .name("请求流程")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch10/leave_1.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }
}
