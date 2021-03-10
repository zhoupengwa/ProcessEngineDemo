package com.demo.ch17;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.impl.type.PrimitiveValueTypeImpl;
import org.camunda.bpm.engine.variable.value.DoubleValue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class VariableTest {

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
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch17/camunda.cfg.xml");
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
                .name("变量测试")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch17/var.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    /**
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_HI_VARINST ( ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_INST_ID_, TENANT_ID_, CASE_DEF_KEY_, CASE_DEF_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, TASK_ID_, NAME_, REV_, VAR_TYPE_, CREATE_TIME_, REMOVAL_TIME_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_, STATE_ ) values ( '3802', 'var', 'var:1:3403', '3801', '3801', '3801', '3801', 'a', null, null, null, null, null, 'var2', 0, 'string', '2021-03-10 15:23:01.259', null, null, null, null, '变量2', null, 'CREATED' );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_HI_TASKINST ( ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, CASE_DEF_KEY_, CASE_DEF_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, ACT_INST_ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, OWNER_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, DELETE_REASON_, TASK_DEF_KEY_, PRIORITY_, DUE_DATE_, FOLLOW_UP_DATE_, TENANT_ID_, REMOVAL_TIME_ ) values ( '3809', 'var', 'var:1:3403', '3801', '3801', '3801', null, null, null, null, 'Activity_0dhhxgy:3808', '请假申请', null, null, null, null, '2021-03-10 15:23:01.264', null, null, null, 'Activity_0dhhxgy', 50, null, null, 'a', null );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_HI_PROCINST ( ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_KEY_, PROC_DEF_ID_, START_TIME_, END_TIME_, REMOVAL_TIME_, DURATION_, START_USER_ID_, START_ACT_ID_, END_ACT_ID_, SUPER_PROCESS_INSTANCE_ID_, ROOT_PROC_INST_ID_, SUPER_CASE_INSTANCE_ID_, CASE_INST_ID_, DELETE_REASON_, TENANT_ID_, STATE_ ) values ( '3801', '3801', null, 'var', 'var:1:3403', '2021-03-10 15:23:01.253', null, null, null, null, 'StartEvent_1', null, null, '3801', null, null, null, 'a', 'ACTIVE' );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_HI_ACTINST ( ID_, PARENT_ACT_INST_ID_, PROC_DEF_KEY_, PROC_DEF_ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, EXECUTION_ID_, ACT_ID_, TASK_ID_, CALL_PROC_INST_ID_, CALL_CASE_INST_ID_, ACT_NAME_, ACT_TYPE_, ASSIGNEE_, START_TIME_, END_TIME_, DURATION_, ACT_INST_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REMOVAL_TIME_ ) values ( 'Activity_0dhhxgy:3808', '3801', 'var', 'var:1:3403', '3801', '3801', '3801', 'Activity_0dhhxgy', '3809', null, null, '请假申请', 'userTask', null, '2021-03-10 15:23:01.261', null, null, 0, 3, 'a', null );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_RU_EXECUTION ( ID_, ROOT_PROC_INST_ID_, PROC_INST_ID_, BUSINESS_KEY_, PROC_DEF_ID_, ACT_ID_, ACT_INST_ID_, IS_ACTIVE_, IS_CONCURRENT_, IS_SCOPE_, IS_EVENT_SCOPE_, PARENT_ID_, SUPER_EXEC_, SUPER_CASE_EXEC_, CASE_INST_ID_, SUSPENSION_STATE_, CACHED_ENT_STATE_, SEQUENCE_COUNTER_, TENANT_ID_, REV_ ) values ( '3801', '3801', '3801', null, 'var:1:3403', 'Activity_0dhhxgy', 'Activity_0dhhxgy:3808', true, false, true, false, null, null, null, null, 1, 18, 3, 'a', 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_RU_TASK ( ID_, NAME_, PARENT_TASK_ID_, DESCRIPTION_, PRIORITY_, CREATE_TIME_, OWNER_, ASSIGNEE_, DELEGATION_, EXECUTION_ID_, PROC_INST_ID_, PROC_DEF_ID_, CASE_EXECUTION_ID_, CASE_INST_ID_, CASE_DEF_ID_, TASK_DEF_KEY_, DUE_DATE_, FOLLOW_UP_DATE_, SUSPENSION_STATE_, TENANT_ID_, REV_ ) values ( '3809', '请假申请', null, null, 50, '2021-03-10 15:23:01.261', null, null, null, '3801', '3801', 'var:1:3403', null, null, null, 'Activity_0dhhxgy', null, null, 1, 'a', 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_GE_BYTEARRAY(ID_, NAME_, BYTES_, DEPLOYMENT_ID_, TENANT_ID_, TYPE_, CREATE_TIME_, ROOT_PROC_INST_ID_, REMOVAL_TIME_, REV_) values ( '3804', 'person', java.io.ByteArrayInputStream@2f3c6ac4(ByteArrayInputStream), null, null, 2, '2021-03-10 15:23:01.252', null, null, error, 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_RU_VARIABLE ( ID_, TYPE_, NAME_, PROC_INST_ID_, EXECUTION_ID_, CASE_INST_ID_, CASE_EXECUTION_ID_, TASK_ID_, BYTEARRAY_ID_, DOUBLE_, LONG_, TEXT_, TEXT2_, VAR_SCOPE_, SEQUENCE_COUNTER_, IS_CONCURRENT_LOCAL_, TENANT_ID_, REV_ ) values ( '3802', 'string', 'var2', '3801', '3801', null, null, null, null, null, null, '变量2', null, '3801', 1, false, 'a', 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     */
    @Test
    public void startProcessInstanceByKey() {
        VariableMap variableMap = Variables.createVariables()
                .putValue("var1", "变量1")
                .putValue("var2", "变量2")
                .putValue("person", new Person(18, "peng"));
        runtimeService.startProcessInstanceByKey("var", variableMap);
    }


    @Test
    public void complete() {
        VariableMap variableMap = Variables.createVariables()
                .putValue("var1", "变量11")
                .putValue("var2", "变量22")
                .putValue("person", new Person(18, "peng"));
        taskService.complete("3809", variableMap);
    }

    @Test
    public void getRuntimeServiceVariables() {
        String executionId = "3801";
        Map<String, Object> variables = runtimeService.getVariables(executionId);
        System.out.println(variables);

        Object var1 = runtimeService.getVariable(executionId, "var1");
        System.out.println("#####" + var1);

        List<String> varParams = new ArrayList<String>();
        varParams.add("var1");
        varParams.add("var2");
        Map<String, Object> variables1 = runtimeService.getVariables(executionId, varParams);
        System.out.println(variables1);

        VariableMap variablesTyped = runtimeService.getVariablesTyped(executionId);
        Object var11 = variablesTyped.get("var1");
        System.out.println("#####" + var11);

//        VariableMap variablesTyped1 = runtimeService.getVariablesTyped(executionId, false);
//        Object person = variablesTyped1.get("person");
//        System.out.println("#####" + person);

    }

    @Test
    public void getTaskServiceVariables() {
        String taskId = "3904";
        Map<String, Object> variables = taskService.getVariables(taskId);
        System.out.println(variables);


        Object var1 = taskService.getVariable(taskId, "var1");
        System.out.println("#####" + var1);

        List<String> varParams = new ArrayList<String>();
        varParams.add("var1");
        varParams.add("var2");
        Map<String, Object> variables1 = taskService.getVariables(taskId, varParams);
        System.out.println(variables1);

        VariableMap variablesTyped = taskService.getVariablesTyped(taskId);
        Object var11 = variablesTyped.get("var1");
        System.out.println("#####" + var11);

    }


    @Test
    public void runtimeServiceSetVariable() {
        String executionId = "3801";
        runtimeService.setVariable(executionId, "p1", "peng1");

        VariableMap variableMap = Variables.createVariables()
                .putValue("var1", "变量111")
                .putValue("var2", "变量222")
                .putValue("person", new Person(18, "peng"));
        runtimeService.setVariables(executionId, variableMap);
    }


    @Test
    public void taskServiceSetVariable() {
        String taskId = "3904";
        taskService.setVariable(taskId, "p1", "peng1");

        VariableMap variableMap = Variables.createVariables()
                .putValue("var1", "变量1111")
                .putValue("var2", "变量2222")
                .putValue("person", new Person(18, "peng"));
        taskService.setVariables(taskId, variableMap);
    }


    @Test
    public void createHistoricDetailQuery() {
        List<HistoricDetail> list = historyService
                .createHistoricDetailQuery()
                .processInstanceId("1701")
                .activityInstanceId("Task_11w89:1734")
                .list();
        for (HistoricDetail historicDetail : list) {
            System.out.println(historicDetail);
        }
    }

    @Test
    public void createHistoricVariableInstanceQuery() {
        List<HistoricVariableInstance> list = historyService
                .createHistoricVariableInstanceQuery()
                .list();
        for (HistoricVariableInstance historicVariableInstance : list) {
            System.out.println("###########");
            System.out.println(historicVariableInstance.getId());
            System.out.println(historicVariableInstance.getExecutionId());
            System.out.println(historicVariableInstance.getActivityInstanceId());
            System.out.println(historicVariableInstance.getName());
            System.out.println(historicVariableInstance.getValue());
            System.out.println("###########");
        }
    }


    @Test
    public void setVariable3() {
        String executionId = "3801";

        PrimitiveValueTypeImpl.DoubleTypeImpl dateType = new PrimitiveValueTypeImpl.DoubleTypeImpl();
        Map<String, Object> map = new HashMap<>();
        map.put("transient", false);
        DoubleValue doubleValue = dateType.createValue(12D, map);
        runtimeService.setVariable(executionId, "var_transient", doubleValue);

        boolean isTransient = true;
        DoubleValue doubleValue1 = Variables.doubleValue(13D, isTransient);
    }


    @Test
    public void setVariable4() {
        String executionId = "3801";

        VariableMap variableMap = Variables.createVariables()
                .putValue("a", Variables.doubleValue(11D, true))
                .putValue("b", Variables.doubleValue(11D, false))
                .putValue("c", Variables.stringValue("mm", false));
        runtimeService.setVariables(executionId, variableMap);
    }

    @Test
    public void setVariable5() {
        String executionId = "3801";
        runtimeService.setVariableLocal(executionId, "xxx", "123");
    }
}
