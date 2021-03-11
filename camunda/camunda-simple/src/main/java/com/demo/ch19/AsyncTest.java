package com.demo.ch19;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.dmn.instance.Variable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class AsyncTest {

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


    /**
     * 多了插入job
     * insert into ACT_RU_JOBDEF ( ID_, PROC_DEF_ID_, PROC_DEF_KEY_, ACT_ID_, JOB_TYPE_, JOB_CONFIGURATION_, JOB_PRIORITY_, SUSPENSION_STATE_, TENANT_ID_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     */
    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("定时器测试")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch19/async.bpmn")
//                .addClasspathResource("com.demo/ch19/async1.bpmn")
//                .addClasspathResource("com.demo/ch19/async-start.bpmn")
                .addClasspathResource("com.demo/ch19/async-start2.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void startProcessInstanceByKey() {
        runtimeService.startProcessInstanceByKey("async");
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_HI_TASKINST ( ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, CASE_DEF_KEY_, CASE_DEF_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, ACT_INST_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TASK_DEF_KEY_, PRIORITY_, DUE_DATE_, FOLLOW_UP_DATE_, TENANT_ID_, REMOVAL_TIME_ ) values ( '6502', 'async', 'async:1:6303', '6401', '6401', '6401', null, null, null, null, 'Activity_08hk2ft:6501', '请假申请', null, null, null, null, '2021-03-11 10:43:42.259', null, null, null, 'Activity_08hk2ft', 50, null, null, 'a', null );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( 'Activity_08hk2ft:6501', '6401', 'async', 'async:1:6303', '6401', '6401', '6401', 'Activity_08hk2ft', '6502', null, null, '请假申请', 'userTask', null, '2021-03-11 10:43:42.246', null, null, 0, 3, 'a', null );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_RU_TASK ( ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, PRIORITY_, CREATE_TIME_, OWNER_, ASSIGNEE_, DELEGATION_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, CASE_EXECUTION_ID_, CASE_INST_ID_, CASE_DEF_ID_, TASK_DEF_KEY_, DUE_DATE_, FOLLOW_UP_DATE_, SUSPENSION_STATE_, TENANT_ID_, REV_ ) values ( '6502', '请假申请', null, null, 50, '2021-03-11 10:43:42.248', null, null, null, '6401', '6401', 'async:1:6303', null, null, null, 'Activity_08hk2ft', null, null, 1, 'a', 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     * delete FROM ACT_RU_JOB WHERE ID_ = '6403' and REV_ = 1;
     * ------------------------------------------------------------------------------------------------------------------------
     * update ACT_RU_EXECUTION set REV_ = 2, PROC_DEF_ID_ = 'async:1:6303', BUSINESS_KEY_ = null, ACT_ID_ = 'Activity_08hk2ft', ACT_INST_ID_ = 'Activity_08hk2ft:6501', IS_ACTIVE_ = true, IS_CONCURRENT_ = false, IS_SCOPE_ = true, IS_EVENT_SCOPE_ = false, PARENT_ID_ = null, SUPER_EXEC_ = null, SUSPENSION_STATE_ = 1, CACHED_ENT_STATE_ = 2, SEQUENCE_COUNTER_ = 3, TENANT_ID_ = 'a' WHERE ID_ = '6401' and REV_ = 1;
     * ------------------------------------------------------------------------------------------------------------------------
     */
    @Test
    public void executeJob() {
        String jobId = "8403";
        managementService.executeJob(jobId);
    }


    /**
     * insert into ACT_RU_JOB ( ID_, TYPE_, LOCK_OWNER_, LOCK_EXP_TIME_, EXCLUSIVE_, EXECUTION_ID_, PROCESS_INSTANCE_ID_, PROCESS_DEF_ID_, PROCESS_DEF_KEY_, RETRIES_, EXCEPTION_STACK_ID_, EXCEPTION_MSG_, DUEDATE_, HANDLER_TYPE_, HANDLER_CFG_, DEPLOYMENT_ID_, SUSPENSION_STATE_, JOB_DEF_ID_, PRIORITY_, SEQUENCE_COUNTER_, TENANT_ID_, CREATE_TIME_, REV_ ) values ('6601', 'message', null, null, true, '6401', '6401', 'async:1:6303', 'async', 3, null, null, null, 'async-continuation', 'transition-notify-listener-take$Flow_11hhq0u', '6301', 1, '6306', 50, 1, 'a', '2021-03-11 10:46:00.484', 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     * delete  FROM ACT_RU_TASK WHERE ID_ = '6502' and REV_ = 1;
     * ------------------------------------------------------------------------------------------------------------------------
     * update ACT_RU_EXECUTION set REV_ = 3, PROC_DEF_ID_ = 'async:1:6303', BUSINESS_KEY_ = null, ACT_ID_ = 'Activity_08hk2ft', ACT_INST_ID_ = null, IS_ACTIVE_ = true, IS_CONCURRENT_ = false, IS_SCOPE_ = true, IS_EVENT_SCOPE_ = false, PARENT_ID_ = null, SUPER_EXEC_ = null, SUSPENSION_STATE_ = 1, CACHED_ENT_STATE_ = 4, SEQUENCE_COUNTER_ = 4, TENANT_ID_ = 'a' WHERE ID_ = '6401' and REV_ = 2;
     * ------------------------------------------------------------------------------------------------------------------------
     * UPDATE ACT_HI_ACTINST SET EXECUTION_ID_ = '6401', PROC_DEF_KEY_ = 'async', PROC_DEF_ID_ = 'async:1:6303', ACT_ID_ = 'Activity_08hk2ft', ACT_NAME_ = '请假申请', ACT_TYPE_ = 'userTask', PARENT_ACT_INST_ID_ = '6401' , END_TIME_ = '2021-03-11 10:46:00.48' , DURATION_ = 138480 , ACT_INST_STATE_ = 4 WHERE ID_ = 'Activity_08hk2ft:6501';
     * ------------------------------------------------------------------------------------------------------------------------
     * update ACT_HI_TASKINST set EXECUTION_ID_ = '6401', PROC_DEF_KEY_ = 'async', PROC_DEF_ID_ = 'async:1:6303', NAME_ = '请假申请', PARENT_TASK_ID_ = null, DESCRIPTION_ = null, OWNER_ = null, ASSIGNEE_ = null, DELETE_REASON_ = 'completed', TASK_DEF_KEY_ = 'Activity_08hk2ft', PRIORITY_ = 50, DUE_DATE_ = null, FOLLOW_UP_DATE_ = null, CASE_INST_ID_ = null , END_TIME_ = '2021-03-11 10:46:00.473' , DURATION_ = 138473 WHERE ID_ = '6502';
     */
    @Test
    public void complete() {
        taskService.complete("8309");
    }


    /**
     * select distinct RES.* from ACT_RU_JOB RES order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void createJobQuery() {
        List<Job> list = managementService.createJobQuery()
                //.active()
                //.processDefinitionId()
                .list();
        System.out.println(list);
    }
}
