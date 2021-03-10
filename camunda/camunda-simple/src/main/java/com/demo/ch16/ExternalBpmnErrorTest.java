package com.demo.ch16;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-10
 */
public class ExternalBpmnErrorTest {


    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;
    private FilterService filterService;
    private ManagementService managementService;
    private ExternalTaskService externalTaskService;


    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch16/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
        filterService = processEngine.getFilterService();
        processEngine.getManagementService();

        externalTaskService = processEngine.getExternalTaskService();
    }


    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("外部任务错误边界测试")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch16/topic_bpmn_error.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void startProcessInstanceByKey() {
        runtimeService.startProcessInstanceByKey("topic");
    }


    @Test
    public void fetchAndLock() {
        List<LockedExternalTask> lockedExternalTaskList = externalTaskService.fetchAndLock(5, "peng3", true)
                .topic("topic1", 1000 * 10 * 5)
                .execute();

        for (LockedExternalTask lockedExternalTask : lockedExternalTaskList) {
            System.out.println("#########");
            System.out.println(lockedExternalTask.getPriority());
            System.out.println(lockedExternalTask.getId());
            System.out.println(lockedExternalTask.getTopicName());
            System.out.println("#########");
        }
    }

    @Test
    public void handleBpmnError() {
        String externalTaskId = "3105";
        String workerId = "peng3";
        String errorCode = "400";
        externalTaskService.handleBpmnError(externalTaskId, workerId, errorCode);
    }
}
