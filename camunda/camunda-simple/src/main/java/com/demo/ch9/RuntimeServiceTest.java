package com.demo.ch9;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng
 */
public class RuntimeServiceTest {

    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;

    private TaskService taskService;

    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch9/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
    }


    @Test
    public void deploy() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("请求流程")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch9/leave_1.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }

    /**
     * insert into ACT_HI_TASKINST ( ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, CASE_DEF_KEY_, CASE_DEF_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, ACT_INST_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TASK_DEF_KEY_, PRIORITY_, DUE_DATE_, FOLLOW_UP_DATE_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_HI_PROCINST ( ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, REMOVAL_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, ROOT_PROC_INST_ID_, SUPER_CASE_INSTANCE_ID_, CASE_INST_ID_, DELETE_REASON_, TENANT_ID_, STATE_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_HI_IDENTITYLINK ( ID_, TIMESTAMP_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, ROOT_PROC_INST_ID_, PROC_DEF_ID_, OPERATION_TYPE_, ASSIGNER_ID_, PROC_DEF_KEY_, TENANT_ID_, REMOVAL_TIME_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_RU_EXECUTION ( ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, ACT_ID_, ACT_INST_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, PARENT_ID_, SUPER_EXEC_, SUPER_CASE_EXEC_, CASE_INST_ID_, SUSPENSION_STATE_, CACHED_ENT_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     * insert into ACT_RU_TASK ( ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, PRIORITY_, CREATE_TIME_, OWNER_, ASSIGNEE_, DELEGATION_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, CASE_EXECUTION_ID_, CASE_INST_ID_, CASE_DEF_ID_, TASK_DEF_KEY_, DUE_DATE_, FOLLOW_UP_DATE_, SUSPENSION_STATE_, TENANT_ID_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     */
    @Test
    public void startProcessInstanceByKey() {
        String processInstanceByKey = "leave";
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceByKey);
        System.out.println(processInstance.getId() + "," + processInstance.getBusinessKey() + "," + processInstance.getProcessDefinitionId());
    }


    /**
     * select distinct RES.REV_, RES.ID_, RES.NAME_, RES.PARENT_TASK_ID_,
     * RES.DESCRIPTION_, RES.PRIORITY_, RES.CREATE_TIME_,
     * RES.OWNER_, RES.ASSIGNEE_, RES.DELEGATION_, RES.EXECUTION_ID_,
     * RES.PROC_INST_ID_, RES.PROC_DEF_ID_, RES.CASE_EXECUTION_ID_,
     * RES.CASE_INST_ID_, RES.CASE_DEF_ID_, RES.TASK_DEF_KEY_, RES.DUE_DATE_,
     * RES.FOLLOW_UP_DATE_, RES.SUSPENSION_STATE_,
     * RES.TENANT_ID_ from ACT_RU_TASK RES WHERE ( 1 = 1 and RES.ASSIGNEE_ = ? ) order by RES.ID_ asc LIMIT ? OFFSET ?
     * 张三(String), 2147483647(Integer), 0(Integer)
     */
    @Test
    public void createTaskQuery() {
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskAssignee("张三");
        List<Task> list = taskQuery.list();
        for (Task task : list) {
            System.out.println(task.getId());
        }
    }


    /**
     * insert into ACT_HI_TASKINST ( ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, CASE_DEF_KEY_, CASE_DEF_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, ACT_INST_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TASK_DEF_KEY_, PRIORITY_, DUE_DATE_, FOLLOW_UP_DATE_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_HI_IDENTITYLINK ( ID_, TIMESTAMP_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, ROOT_PROC_INST_ID_, PROC_DEF_ID_, OPERATION_TYPE_, ASSIGNER_ID_, PROC_DEF_KEY_, TENANT_ID_, REMOVAL_TIME_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_RU_TASK ( ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, PRIORITY_, CREATE_TIME_, OWNER_, ASSIGNEE_, DELEGATION_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, CASE_EXECUTION_ID_, CASE_INST_ID_, CASE_DEF_ID_, TASK_DEF_KEY_, DUE_DATE_, FOLLOW_UP_DATE_, SUSPENSION_STATE_, TENANT_ID_, REV_ ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1 )
     * delete from ACT_RU_TASK where ID_ = ? and REV_ = ?
     * update ACT_RU_EXECUTION set REV_ = ?, PROC_DEF_ID_ = ?, BUSINESS_KEY_ = ?, ACT_ID_ = ?, ACT_INST_ID_ = ?, IS_ACTIVE_ = ?, IS_CONCURRENT_ = ?, IS_SCOPE_ = ?, IS_EVENT_SCOPE_ = ?, PARENT_ID_ = ?, SUPER_EXEC_ = ?, SUSPENSION_STATE_ = ?, CACHED_ENT_STATE_ = ?, SEQUENCE_COUNTER_ = ?, TENANT_ID_ = ? where ID_ = ? and REV_ = ?
     * UPDATE ACT_HI_ACTINST SET EXECUTION_ID_ = ?, PROC_DEF_KEY_ = ?, PROC_DEF_ID_ = ?, ACT_ID_ = ?, ACT_NAME_ = ?, ACT_TYPE_ = ?, PARENT_ACT_INST_ID_ = ? , END_TIME_ = ? , DURATION_ = ? , ACT_INST_STATE_ = ? WHERE ID_ = ?
     * update ACT_HI_TASKINST set EXECUTION_ID_ = ?, PROC_DEF_KEY_ = ?, PROC_DEF_ID_ = ?, NAME_ = ?, PARENT_TASK_ID_ = ?, DESCRIPTION_ = ?, OWNER_ = ?, ASSIGNEE_ = ?, DELETE_REASON_ = ?, TASK_DEF_KEY_ = ?, PRIORITY_ = ?, DUE_DATE_ = ?, FOLLOW_UP_DATE_ = ?, CASE_INST_ID_ = ? , END_TIME_ = ? , DURATION_ = ? where ID_ = ?
     */
    @Test
    public void complete() {
        String taskId = "4902";
        taskService.complete(taskId);
    }


    @Test
    public void createProcessInstanceByKey() {
        String processInstanceByKey = "leave";
        String activityId = "Activity_1r8r4jn";

        ProcessInstantiationBuilder processInstantiationBuilder = runtimeService.createProcessInstanceByKey(processInstanceByKey);
        ProcessInstance processInstance = processInstantiationBuilder.businessKey("001")
                //某节点之前开始
                .startBeforeActivity(activityId)
                .execute();
        System.out.println(processInstance.getId() + "," + processInstance.getBusinessKey() + "," + processInstance.getProcessDefinitionId());

    }


    @Test
    public void createProcessInstanceByKey2() {
        String processInstanceByKey = "leave";
        String activityId = "Flow_1c279og";

        ProcessInstantiationBuilder processInstantiationBuilder = runtimeService.createProcessInstanceByKey(processInstanceByKey);
        ProcessInstance processInstance = processInstantiationBuilder.businessKey("001")
                //从连线开始触发
                .startTransition(activityId)
                .execute();
        System.out.println(processInstance.getId() + "," + processInstance.getBusinessKey() + "," + processInstance.getProcessDefinitionId());

    }


    @Test
    public void createProcessInstanceByKey3() {
        String processInstanceByKey = "leave";
        String activityId = "Activity_0zafxq7";

        ProcessInstantiationBuilder processInstantiationBuilder = runtimeService.createProcessInstanceByKey(processInstanceByKey);
        ProcessInstance processInstance = processInstantiationBuilder.businessKey("001")
                //某节点之后
                .startAfterActivity(activityId)
                .execute();
        System.out.println(processInstance.getId() + "," + processInstance.getBusinessKey() + "," + processInstance.getProcessDefinitionId());

    }

    @Test
    public void createProcessInstanceByKey4() {
        String processInstanceByKey = "leave";
        String activityId = "Activity_0zafxq7";

        //跳过监听器
        boolean skipCustomListeners = true;
        boolean skipIoMappings = true;

        ProcessInstantiationBuilder processInstantiationBuilder = runtimeService.createProcessInstanceByKey(processInstanceByKey);
        ProcessInstance processInstance = processInstantiationBuilder.businessKey("001")
                .startAfterActivity(activityId)
                .execute(skipCustomListeners, skipIoMappings);
        System.out.println(processInstance.getId() + "," + processInstance.getBusinessKey() + "," + processInstance.getProcessDefinitionId());

    }


    /**
     *
     *  select distinct RES.* from ACT_RU_EXECUTION RES inner join
     *  ACT_RE_PROCDEF P on RES.PROC_DEF_ID_ = P.ID_ WHERE
     *
     *  RES.PARENT_ID_ is null and RES.PROC_INST_ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void createProcessInstanceQuery() {
        String processInstanceId = "1";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                //结果唯一的，可以用single，如果大于1，则会报错
                .singleResult();
        if (processInstance == null) {
            System.out.println("当前实例已经结束了");
        } else {
            System.out.println("当前实例正在运转");
        }
    }
}
