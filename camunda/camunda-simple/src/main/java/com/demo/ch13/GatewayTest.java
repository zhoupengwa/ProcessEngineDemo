package com.demo.ch13;

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
public class GatewayTest {


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
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch13/camunda.cfg.xml");
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
                .name("网关测试")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch13/gateway1.bpmn")
//                .addClasspathResource("com.demo/ch13/gateway2.bpmn")
//                .addClasspathResource("com.demo/ch13/gateway3.bpmn")
                .addClasspathResource("com.demo/ch13/gateway4.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }

    @Test
    public void startProcessInstanceByKey() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("day", 2);//gateway1传递2，走到peng1节点、gateway2传递2，走到peng1节点
        runtimeService.startProcessInstanceByKey("gateway", vars);
    }

    @Test
    public void startProcessInstanceByKey2() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("day", 4);//gateway1传递4，走到peng1节点、gateway2传递4，走到peng2节点
        runtimeService.startProcessInstanceByKey("gateway", vars);
    }

    @Test
    public void startProcessInstanceByKey3() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("day", 6);//gateway1传递6，还是走到peng1节点、gateway2传递6，走到peng2节点
        runtimeService.startProcessInstanceByKey("gateway", vars);
    }


    @Test
    public void startProcessInstanceByKey4() {
        runtimeService.startProcessInstanceByKey("gateway");
    }

    @Test
    public void complete() {
        taskService.complete("3510");
    }

    @Test
    public void startProcessInstanceByKey5() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("money", 200);//传200走到采购员，相当于排他网关
        runtimeService.startProcessInstanceByKey("gateway", vars);
    }

    @Test
    public void startProcessInstanceByKey6() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("money", 400);//传400，两个都满足，都会走，相当于并行网关了
        runtimeService.startProcessInstanceByKey("gateway", vars);
    }

}
