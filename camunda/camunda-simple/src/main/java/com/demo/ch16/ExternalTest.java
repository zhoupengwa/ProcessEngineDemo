package com.demo.ch16;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.externaltask.ExternalTask;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class ExternalTest {

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
                .addClasspathResource("com.demo/ch16/topic.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }

    /**
     * 多了一条ACT_RU_EXT_TASK
     * insert into ACT_HI_PROCINST ( ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, REMOVAL_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, ROOT_PROC_INST_ID_, SUPER_CASE_INSTANCE_ID_, CASE_INST_ID_, DELETE_REASON_, TENANT_ID_, STATE_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_RU_EXECUTION ( ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, ACT_ID_, ACT_INST_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, PARENT_ID_, SUPER_EXEC_, SUPER_CASE_EXEC_, CASE_INST_ID_, SUSPENSION_STATE_, CACHED_ENT_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     * insert into ACT_RU_EXT_TASK ( ID_, WORKER_ID_, TOPIC_NAME_, LOCK_EXP_TIME_, RETRIES_, ERROR_MSG_, ERROR_DETAILS_ID_, SUSPENSION_STATE_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, PROC_DEF_KEY_, ACT_ID_, ACT_INST_ID_, TENANT_ID_, PRIORITY_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     */
    @Test
    public void startProcessInstanceByKey() {
        runtimeService.startProcessInstanceByKey("topic");
    }


    /**
     * select distinct RES.REV_, RES.ID_, RES.TOPIC_NAME_, RES.WORKER_ID_, RES.LOCK_EXP_TIME_, RES.RETRIES_, RES.ERROR_MSG_, RES.ERROR_DETAILS_ID_, RES.EXECUTION_ID_, RES.PROC_INST_ID_, RES.PROC_DEF_ID_, RES.PROC_DEF_KEY_, RES.ACT_ID_, RES.ACT_INST_ID_, RES.SUSPENSION_STATE_, RES.TENANT_ID_, RES.PRIORITY_, RES.BUSINESS_KEY_ from ( select RES.*, PI.BUSINESS_KEY_
     * from ACT_RU_EXT_TASK RES left join ACT_RU_EXECUTION PI on RES.PROC_INST_ID_ = PI.ID_ ) RES order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void createExternalTaskQuery() {
        List<ExternalTask> list = externalTaskService.createExternalTaskQuery().list();
        for (ExternalTask externalTask : list) {
            System.out.println("####" + externalTask);
        }
    }


    /**
     * SELECT DISTINCT
     * RES.*
     * FROM
     * (
     * SELECT
     * RES.*,
     * PI.BUSINESS_KEY_
     * FROM
     * ACT_RU_EXT_TASK RES
     * LEFT JOIN ACT_RU_EXECUTION PI ON RES.PROC_INST_ID_ = PI.ID_
     * WHERE
     * (
     * RES.LOCK_EXP_TIME_ IS NULL
     * OR RES.LOCK_EXP_TIME_ <=?
     * )
     * AND (
     * RES.SUSPENSION_STATE_ IS NULL
     * OR RES.SUSPENSION_STATE_ = 1
     * )
     * AND (
     * RES.RETRIES_ IS NULL
     * OR RES.RETRIES_ > 0
     * )
     * AND ( RES.TOPIC_NAME_ LIKE ? )
     * ) RES
     * ORDER BY
     * RES.PRIORITY_ DESC
     * LIMIT ? OFFSET ?
     *
     * update ACT_RU_EXT_TASK SET REV_ = ?, WORKER_ID_ = ?, TOPIC_NAME_ = ?, LOCK_EXP_TIME_ = ?, RETRIES_ = ?, ERROR_MSG_ = ?, ERROR_DETAILS_ID_ = ?, EXECUTION_ID_ = ?, PROC_INST_ID_ = ?, PROC_DEF_ID_ = ?, PROC_DEF_KEY_ = ?, ACT_ID_ = ?, ACT_INST_ID_ = ?, SUSPENSION_STATE_ = ?, PRIORITY_ = ?
     * where ID_= ? and REV_ = ?
     */
    @Test
    public void fetchAndLock() {
        int maxTasks = 5;
        String workerId = "peng2";
        boolean usePriority = true;
        int lockDuration = 1000 * 60 * 10;
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
}
