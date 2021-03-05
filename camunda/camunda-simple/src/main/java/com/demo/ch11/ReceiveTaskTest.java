package com.demo.ch11;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.Execution;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-04
 */
public class ReceiveTaskTest {

    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;


    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch11/camunda.cfg.xml");
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
                .name("接收任务测试")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch11/receiveTask.bpmn")
                .addClasspathResource("com.demo/ch11/receiveTask_msg.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    /**
     * 带有消息的启动时会多插入一些表ACT_RU_EVENT_SUBSCR
     * insert into ACT_HI_PROCINST ( ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, REMOVAL_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, ROOT_PROC_INST_ID_, SUPER_CASE_INSTANCE_ID_, CASE_INST_ID_, DELETE_REASON_, TENANT_ID_, STATE_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_RU_EXECUTION ( ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, ACT_ID_, ACT_INST_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, PARENT_ID_, SUPER_EXEC_, SUPER_CASE_EXEC_, CASE_INST_ID_, SUSPENSION_STATE_, CACHED_ENT_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     * insert into ACT_RU_EVENT_SUBSCR ( ID_, EVENT_TYPE_, EVENT_NAME_, EXECUTION_ID_, PROC_INST_ID_, ACTIVITY_ID_, CONFIGURATION_, CREATED_, TENANT_ID_, REV_ ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     */
    @Test
    public void startProcessInstanceByKey() {
        runtimeService.startProcessInstanceByKey("receiveTask");
    }

    /**
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * update ACT_RU_EXECUTION set REV_ = ?, PROC_DEF_ID_ = ?, BUSINESS_KEY_ = ?, ACT_ID_ = ?, ACT_INST_ID_ = ?, IS_ACTIVE_ = ?, IS_CONCURRENT_ = ?, IS_SCOPE_ = ?, IS_EVENT_SCOPE_ = ?, PARENT_ID_ = ?, SUPER_EXEC_ = ?, SUSPENSION_STATE_ = ?, CACHED_ENT_STATE_ = ?, SEQUENCE_COUNTER_ = ?, TENANT_ID_ = ? where ID_ = ? and REV_ = ?
     * UPDATE ACT_HI_ACTINST SET EXECUTION_ID_ = ?, PROC_DEF_KEY_ = ?, PROC_DEF_ID_ = ?, ACT_ID_ = ?, ACT_NAME_ = ?, ACT_TYPE_ = ?, PARENT_ACT_INST_ID_ = ? , END_TIME_ = ? , DURATION_ = ? , ACT_INST_STATE_ = ? WHERE ID_ = ?
     */
    @Test
    public void signal() {
        //注意触发接收任务需要的参数 用的是执行实例ID，不是流程实例ID
        String executionId = "4801";
        //获取当日的销售额度，完成自己的一些业务，4501，触发实例继续往下流转
        runtimeService.signal(executionId);
    }


    /**
     * elect distinct RES.* from ACT_RU_EXECUTION RES
     * inner join ACT_RE_PROCDEF P on RES.PROC_DEF_ID_ = P.ID_ WHERE RES.ACT_ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void createExecutionQuery() {
        //注意触发接收任务需要的参数 用的是执行实例ID，不是流程实例ID
        String activityId = "Activity_0sm44rv";
        //获取当日的销售额度，完成自己的一些业务，4501，触发实例继续往下流转
        Execution execution = runtimeService.createExecutionQuery().activityId(activityId).singleResult();
        System.out.println(execution);
    }

    /**
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * delete from ACT_RU_EVENT_SUBSCR where ID_ = ? and REV_ = ?
     * update ACT_RU_EXECUTION set REV_ = ?, PROC_DEF_ID_ = ?, BUSINESS_KEY_ = ?, ACT_ID_ = ?, ACT_INST_ID_ = ?, IS_ACTIVE_ = ?, IS_CONCURRENT_ = ?, IS_SCOPE_ = ?, IS_EVENT_SCOPE_ = ?, PARENT_ID_ = ?, SUPER_EXEC_ = ?, SUSPENSION_STATE_ = ?, CACHED_ENT_STATE_ = ?, SEQUENCE_COUNTER_ = ?, TENANT_ID_ = ? where ID_ = ? and REV_ = ?
     * delete from ACT_RU_EXECUTION where ID_ = ? and REV_ = ?
     * UPDATE ACT_HI_ACTINST SET EXECUTION_ID_ = ?, PROC_DEF_KEY_ = ?, PROC_DEF_ID_ = ?, ACT_ID_ = ?, ACT_NAME_ = ?, ACT_TYPE_ = ?, PARENT_ACT_INST_ID_ = ? , END_TIME_ = ? , DURATION_ = ? , ACT_INST_STATE_ = ? WHERE ID_ = ?
     */
    @Test
    public void signalMsg() {
        String executionId = "5103";
        runtimeService.signal(executionId);
    }


    @Test
    public void messageEventReceived() {
        String executionId = "5403";
        String messageName = "newInvoice";
        runtimeService.messageEventReceived(messageName, executionId);
    }


    @Test
    public void correlateMessage() {
        String messageName = "newInvoice";
        runtimeService.correlateMessage(messageName);
    }


    /**
     * createEventSubscriptionQuery：select distinct RES.* from ACT_RU_EVENT_SUBSCR RES order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void correlateMessageAndcreateEventSubscriptionQuery() {
        String businessKey1 = "23";
        String businessKey2 = "42";
        runtimeService.startProcessInstanceByKey("receiveTask", businessKey1);
        runtimeService.startProcessInstanceByKey("receiveTask", businessKey2);

        //事件订阅表查询
        List<EventSubscription> list = runtimeService.createEventSubscriptionQuery().list();
        System.out.println("######EventSubscription：" + list.size());

        runtimeService.correlateMessage("newInvoice", "23");
        list = runtimeService.createEventSubscriptionQuery().list();
        System.out.println("######EventSubscription：" + list.size());

        runtimeService.correlateMessage("newInvoice", "42");
        list = runtimeService.createEventSubscriptionQuery().list();
        System.out.println("######EventSubscription：" + list.size());
    }

    /**
     * Cannot correlate a message with name 'newInvoice' to a single execution. 2 executions match the correlation keys
     */
    @Test
    public void correlateMessageAndcreateEventSubscriptionQuery2() {
        String businessKey1 = "23";
        String businessKey2 = "42";
        runtimeService.startProcessInstanceByKey("receiveTask", businessKey1);
        runtimeService.startProcessInstanceByKey("receiveTask", businessKey2);

        //事件订阅表查询
        List<EventSubscription> list = runtimeService.createEventSubscriptionQuery().list();
        System.out.println("######EventSubscription：" + list.size());

        runtimeService.correlateMessage("newInvoice");
        list = runtimeService.createEventSubscriptionQuery().list();
        System.out.println("######EventSubscription：" + list.size());

        runtimeService.correlateMessage("newInvoice");
        list = runtimeService.createEventSubscriptionQuery().list();
        System.out.println("######EventSubscription：" + list.size());
    }


}
