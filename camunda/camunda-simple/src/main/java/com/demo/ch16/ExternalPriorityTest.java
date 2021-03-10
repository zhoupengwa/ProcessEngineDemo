package com.demo.ch16;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class ExternalPriorityTest {

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
                .name("外部任务")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch16/topic_priority.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void startProcessInstanceByKey() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("priority", 50);
        runtimeService.startProcessInstanceByKey("topic", vars);
    }


    @Test
    public void fetchAndLock() {
        int maxTasks = 5;
        String workerId = "peng3";
        boolean usePriority = true;
        int lockDuration = 1000 * 10 * 5;
        List<LockedExternalTask> lockedExternalTaskList = externalTaskService.fetchAndLock(maxTasks, workerId, usePriority)
                .topic("topic1", lockDuration)
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
    public void startProcessInstanceByKey2() {
        VariableMap variableMap = Variables.createVariables()
                .putValue("priority", 200)
                .putValue("peng1", "变量1")
                .putValue("peng2", "变量2");
        runtimeService.startProcessInstanceByKey("topic", variableMap);
    }


    @Test
    public void fetchAndLock2() {
        int maxTasks = 5;
        String workerId = "peng3";
        boolean usePriority = true;
        int lockDuration = 1000 * 10;
        List<LockedExternalTask> lockedExternalTaskList = externalTaskService.fetchAndLock(maxTasks, workerId, usePriority)
                .topic("topic1", lockDuration)
                .variables("peng1")
                .execute();

        for (LockedExternalTask lockedExternalTask : lockedExternalTaskList) {
            System.out.println("#########");
            System.out.println(lockedExternalTask.getPriority());
            System.out.println(lockedExternalTask.getId());
            System.out.println(lockedExternalTask.getTopicName());
            VariableMap variables = lockedExternalTask.getVariables();
            System.out.println(variables);
            System.out.println("#########");
        }
    }


    @Test
    public void fetchAndLock3() {
        int maxTasks = 5;
        String workerId = "peng3";
        boolean usePriority = true;
        int lockDuration = 1000 * 10;
        List<LockedExternalTask> lockedExternalTaskList = externalTaskService.fetchAndLock(maxTasks, workerId, usePriority)
                .topic("topic1", lockDuration)
                .topic("topic2", lockDuration)
                .topic("topic3", lockDuration)
                .execute();

        for (LockedExternalTask lockedExternalTask : lockedExternalTaskList) {
            System.out.println("#########");
            System.out.println(lockedExternalTask.getPriority());
            System.out.println(lockedExternalTask.getId());
            System.out.println(lockedExternalTask.getTopicName());
            VariableMap variables = lockedExternalTask.getVariables();
            System.out.println(variables);
            System.out.println("#########");
        }
    }


    @Test
    public void fetchAndLock4() {
        int maxTasks = 5;
        String workerId = "peng3";
        boolean usePriority = true;
        int lockDuration = 1000 * 10;
        List<LockedExternalTask> lockedExternalTaskList = externalTaskService.fetchAndLock(maxTasks, workerId, usePriority)
                .topic("topic3", lockDuration)
                .processDefinitionIdIn("topic:3:503")
                .execute();

        for (LockedExternalTask lockedExternalTask : lockedExternalTaskList) {
            System.out.println("#########");
            System.out.println(lockedExternalTask.getPriority());
            System.out.println(lockedExternalTask.getId());
            System.out.println(lockedExternalTask.getTopicName());
            VariableMap variables = lockedExternalTask.getVariables();
            System.out.println(variables);
            System.out.println("#########");
        }
    }

}
