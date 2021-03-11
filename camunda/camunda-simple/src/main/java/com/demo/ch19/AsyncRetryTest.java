package com.demo.ch19;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.impl.ServiceImpl;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.Incident;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class AsyncRetryTest {

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
                .name("定时器重试测试")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch19/async_servicetask.bpmn")
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
        String jobId = "10603";
        managementService.executeJob(jobId);
    }


    @Test
    public void complete() {
        taskService.complete("10302");
    }


    @Test
    public void createIncident() {
        String incidentType = "peng";
        String executionId = "9601";
        String configuration = "peng-configuration";
        String message = "msg";
        runtimeService.createIncident(incidentType, executionId, configuration, message);
    }

    /**
     * select distinct RES.* from ACT_RU_INCIDENT RES order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void createIncidentQuery() {
        List<Incident> list = runtimeService.createIncidentQuery().list();
        System.out.println(list);
    }


    /**
     * delete from ACT_RU_INCIDENT where ID_ = ? and REV_ = ?
     * update ACT_RU_EXECUTION set REV_ = ?, PROC_DEF_ID_ = ?, BUSINESS_KEY_ = ?, ACT_ID_ = ?, ACT_INST_ID_ = ?, IS_ACTIVE_ = ?, IS_CONCURRENT_ = ?, IS_SCOPE_ = ?, IS_EVENT_SCOPE_ = ?, PARENT_ID_ = ?, SUPER_EXEC_ = ?, SUSPENSION_STATE_ = ?, CACHED_ENT_STATE_ = ?, SEQUENCE_COUNTER_ = ?, TENANT_ID_ = ? where ID_ = ? and REV_ = ?
     */
    @Test
    public void resolveIncident() {
        runtimeService.resolveIncident("10802");
    }

    @Test
    public void createIncidentQuery2() {
        Incident incident = runtimeService.createIncidentQuery().incidentId("10802").singleResult();

        ServiceImpl service = (ServiceImpl) runtimeService;
        service.getCommandExecutor().execute(new ResolveIncidentCmd(incident));
    }

}
