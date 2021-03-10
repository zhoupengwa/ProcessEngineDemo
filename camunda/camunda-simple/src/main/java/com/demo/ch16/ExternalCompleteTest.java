package com.demo.ch16;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.externaltask.ExternalTaskQuery;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.impl.util.ExceptionUtil;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class ExternalCompleteTest {

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
                .name("外部任务完成测试")
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
    public void suspendProcessInstanceById() {
        runtimeService.suspendProcessInstanceById("901");
    }

    @Test
    public void activateProcessInstanceById() {
        runtimeService.activateProcessInstanceById("901");
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
    public void complete() {
        String externalTaskId = "1504";
        String workerId = "peng3";
        externalTaskService.complete(externalTaskId, workerId);
    }

    /**
     * insert into ACT_HI_VARINST ( ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_INST_ID_, TENANT_ID_, CASE_DEF_KEY_, CASE_DEF_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, TASK_ID_, NAME_, REV_, VAR_TYPE_, CREATE_TIME_, REMOVAL_TIME_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_, STATE_ ) values ( '1501', 'topic', 'topic:4:1003', '1401', '1401', '1401', '1401', 'a', null, null, null, null, null, 'a', 0, 'integer', '2021-03-10 11:06:20.967', null, null, null, 10, '10', null, 'CREATED' );
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( 'Activity_0y8yfpp:1503', '1401', 'topic', 'topic:4:1003', '1401', '1401', '1502', 'Activity_0y8yfpp', null, null, null, '外部任务2', 'serviceTask', null, '2021-03-10 11:06:20.98', null, null, 0, 5, 'a', null );
     * insert into ACT_RU_EXECUTION ( ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, ACT_ID_, ACT_INST_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, PARENT_ID_, SUPER_EXEC_, SUPER_CASE_EXEC_, CASE_INST_ID_, SUSPENSION_STATE_, CACHED_ENT_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REV_ ) values ( '1502', null, '1401', null, 'topic:4:1003', 'Activity_0y8yfpp', 'Activity_0y8yfpp:1503', true, false, true, false, '1401', null, null, null, 1, 128, 5, 'a', 1 );
     * insert into ACT_RU_VARIABLE ( ID_, TYPE_, NAME_, PROC_INST_ID_, EXECUTION_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, TASK_ID_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_, VAR_SCOPE_, SEQUENCE_COUNTER_, IS_CONCURRENT_LOCAL_, TENANT_ID_, REV_ ) values ( '1501', 'integer', 'a', '1401', '1401', null, null, null, null, null, 10, '10', null, '1401', 1, false, 'a', 1 );
     * insert into ACT_RU_EXT_TASK ( ID_, WORKER_ID_, TOPIC_NAME_, LOCK_EXP_TIME_, RETRIES_, ERROR_MSG_, ERROR_DETAILS_ID_, SUSPENSION_STATE_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, PROC_DEF_KEY_, ACT_ID_, ACT_INST_ID_, TENANT_ID_, PRIORITY_, REV_ ) values ( '1504', null, 'topic2', null, null, null, null, 1, '1502', '1401', 'topic:4:1003', 'topic', 'Activity_0y8yfpp', 'Activity_0y8yfpp:1503', 'a', 100, 1 );
     * delete FROM ACT_RU_EXT_TASK WHERE ID_ = '1406' and REV_ = 2;
     * update ACT_RU_EXECUTION set REV_ = 2, PROC_DEF_ID_ = 'topic:4:1003', BUSINESS_KEY_ = null, ACT_ID_ = null, ACT_INST_ID_ = '1401', IS_ACTIVE_ = false, IS_CONCURRENT_ = false, IS_SCOPE_ = true, IS_EVENT_SCOPE_ = false, PARENT_ID_ = null, SUPER_EXEC_ = null, SUSPENSION_STATE_ = 1, CACHED_ENT_STATE_ = 16, SEQUENCE_COUNTER_ = 4, TENANT_ID_ = 'a' WHERE ID_ = '1401' and REV_ = 1;
     * delete FROM ACT_RU_EXECUTION WHERE ID_ = '1404' and REV_ = 1;
     * UPDATE ACT_HI_ACTINST SET EXECUTION_ID_ = '1404', PROC_DEF_KEY_ = 'topic', PROC_DEF_ID_ = 'topic:4:1003', ACT_ID_ = 'Activity_05ut8e6', ACT_NAME_ = '外部任务1', ACT_TYPE_ = 'serviceTask', PARENT_ACT_INST_ID_ = '1401' , END_TIME_ = '2021-03-10 11:06:20.974' , DURATION_ = 84974 , ACT_INST_STATE_ = 4 WHERE ID_ = 'Activity_05ut8e6:1405'
     */
    @Test
    public void complete2() {
        String externalTaskId = "1406";
        String workerId = "peng3";
        externalTaskService.complete(externalTaskId, workerId, Variables.createVariables().putValue("a", 10));
    }


    /**
     * delete from ACT_RU_EXT_TASK where ID_ = ? and REV_ = ?
     * delete from ACT_RU_VARIABLE where ID_ = ? and REV_ = ?
     * delete from ACT_RU_EXECUTION where ID_ = ? and REV_ = ?
     * UPDATE ACT_HI_ACTINST SET EXECUTION_ID_ = ?, PROC_DEF_KEY_ = ?, PROC_DEF_ID_ = ?, ACT_ID_ = ?, ACT_NAME_ = ?, ACT_TYPE_ = ?, PARENT_ACT_INST_ID_ = ? , END_TIME_ = ? , DURATION_ = ? , ACT_INST_STATE_ = ? WHERE ID_ = ?
     * update ACT_HI_PROCINST set PROC_DEF_ID_ = ?, PROC_DEF_KEY_ = ?, BUSINESS_KEY_ = ?, END_ACT_ID_ = ?, DELETE_REASON_ = ?, SUPER_PROCESS_INSTANCE_ID_ = ?, STATE_ = ? , END_TIME_ = ? , DURATION_ = ? where ID_ = ?
     */
    @Test
    public void deleteProcessInstance() {
        String processInstanceId = "1901";
        String deleteReason = "测试删除";
        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
    }


    /**
     * pdate ACT_RU_EXT_TASK SET REV_ = ?, WORKER_ID_ = ?, TOPIC_NAME_ = ?, LOCK_EXP_TIME_ = ?, RETRIES_ = ?, ERROR_MSG_ = ?, ERROR_DETAILS_ID_ = ?, EXECUTION_ID_ = ?, PROC_INST_ID_ = ?, PROC_DEF_ID_ = ?, PROC_DEF_KEY_ = ?, ACT_ID_ = ?, ACT_INST_ID_ = ?, SUSPENSION_STATE_ = ?, PRIORITY_ = ? where ID_= ? and REV_ = ?
     */
    @Test
    public void setRetries() {
        String externalTaskId = "2006";
        int retries = 4;
        externalTaskService.setRetries(externalTaskId, retries);
    }


    @Test
    public void setPriority() {
        String externalTaskId = "2006";
        int priority = 1000;
        externalTaskService.setPriority(externalTaskId, priority);
    }


    @Test
    public void updateRetries() {
        externalTaskService.updateRetries()
                .externalTaskIds("2006", "2106")
                .set(10);
    }


    @Test
    public void updateRetriesExternalTaskQuery() {
        ExternalTaskQuery externalTaskQuery = externalTaskService.createExternalTaskQuery();
        externalTaskService.updateRetries()
                .externalTaskQuery(externalTaskQuery)
                .set(100);
    }

    @Test
    public void fetchAndLock2() {
        //锁定时间变更为：当前时间+5分钟
        Date date = new Date();
        date.setMinutes(date.getMinutes() - 5);
        ClockUtil.setCurrentTime(date);
        String externalTaskId = "2006";
        String workerId = "peng3";
        long lockDuration = 1000 * 60 * 10;
        externalTaskService.extendLock(externalTaskId, workerId, lockDuration);

        //或不用ClockUtil，直接        long lockDuration = 1000 * 60 * 5;
    }


    @Test
    public void unlock() {
        String externalTaskId = "2006";
        externalTaskService.unlock(externalTaskId);
    }


    /**
     * update ACT_RU_EXT_TASK SET REV_ = ?, WORKER_ID_ = ?, TOPIC_NAME_ = ?, LOCK_EXP_TIME_ = ?, RETRIES_ = ?, ERROR_MSG_ = ?, ERROR_DETAILS_ID_ = ?, EXECUTION_ID_ = ?, PROC_INST_ID_ = ?, PROC_DEF_ID_ = ?, PROC_DEF_KEY_ = ?, ACT_ID_ = ?, ACT_INST_ID_ = ?, SUSPENSION_STATE_ = ?, PRIORITY_ = ? where ID_= ? and REV_ = ?
     */
    @Test
    public void handleFailure() {
        String externalTaskId = "2006";
        String workerId = "peng3";
        String errorMsg = "处理失败，稍后重试";
        int retries = 4;
        int retryTimeout = 1000 * 60 * 2;
        externalTaskService.handleFailure(externalTaskId, workerId, errorMsg, retries, retryTimeout);
    }


    @Test
    public void handleFailure2() {
        String exceptionStacktrace = "";
        try {
            int a = 1 / 0;

        } catch (Exception ex) {
            exceptionStacktrace = ExceptionUtil.getExceptionStacktrace(ex);
        }
        String externalTaskId = "2506";
        String workerId = "peng3";
        String errorMsg = "处理失败";
        int retries = 0;
        int retryTimeout = 1000 * 60 * 2;
        externalTaskService.handleFailure(externalTaskId, workerId, errorMsg, exceptionStacktrace, retries, retryTimeout);
    }


    @Test
    public void handleFailure3() {
        String externalTaskId = "2006";
        String externalTaskErrorDetails = externalTaskService.getExternalTaskErrorDetails(externalTaskId);
        System.out.println("#########");
        System.out.println(externalTaskErrorDetails);
    }


    @Test
    public void fetchAndLock3() {
        List<LockedExternalTask> lockedExternalTaskList = externalTaskService.fetchAndLock(5, "peng3")
                .topic("topic1", 1000 * 3)
                .execute();
        for (LockedExternalTask lockedExternalTask : lockedExternalTaskList) {
            System.out.println("#########");
            System.out.println(lockedExternalTask.getPriority());
            System.out.println(lockedExternalTask.getId());
            System.out.println(lockedExternalTask.getTopicName());
            System.out.println("#########");
        }
    }

    /**
     * select distinct RES.* from ACT_RU_INCIDENT RES order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void createIncidentQuery() {
        List<Incident> incidentList = runtimeService.createIncidentQuery().list();
        for (Incident incident : incidentList) {
            System.out.println("####");
            System.out.println(incident.getId());
            System.out.println("####");
        }
    }

    @Test
    public void createIncidentQueryComplete() {
        List<Incident> incidentList = runtimeService.createIncidentQuery().list();
        for (Incident incident : incidentList) {
            externalTaskService.complete(incident.getConfiguration(), "peng3");
        }
    }

}
