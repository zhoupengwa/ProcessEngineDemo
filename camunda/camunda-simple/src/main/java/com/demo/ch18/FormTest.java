package com.demo.ch18;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormProperty;
import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class FormTest {

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
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch18/camunda.cfg.xml");
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
    }


    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("外置表单测试")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch18/form.bpmn")
                .addClasspathResource("com.demo/ch18/form1.bpmn")
                .addClasspathResource("com.demo/ch18/start.html")
                .addClasspathResource("com.demo/ch18/1.html")
                .addClasspathResource("com.demo/ch18/2.html")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void getStartFormKey() {
        String processDefinitionId = "form:1:5106";
        String startFormKey = formService.getStartFormKey(processDefinitionId);
        System.out.println(startFormKey);
    }

    @Test
    public void getTaskFormKey() {
        String processDefinitionId = "form:1:5106";
        String taskDefinitionKey = "Activity_0dhhxgy";
        String startFormKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);
        System.out.println(startFormKey);
    }

    @Test
    public void getTaskFormKey2() {
        String processDefinitionId = "form:1:5106";
        String taskDefinitionKey = "Activity_1c6jr4q";
        String startFormKey = formService.getTaskFormKey(processDefinitionId, taskDefinitionKey);
        System.out.println(startFormKey);
    }

    /**
     * select * from ACT_GE_BYTEARRAY where DEPLOYMENT_ID_ = ? and NAME_ = ?
     */
    @Test
    public void getRenderedStartForm() {
        String processDefinitionId = "form:3:5306";
        Object renderedStartForm = formService.getRenderedStartForm(processDefinitionId, "juel");
        System.out.println(renderedStartForm);
    }


    @Test
    public void getStartFormData() {
        String processDefinitionId = "form:3:5306";
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);
        System.out.println("############");
        System.out.println(startFormData.getDeploymentId());
        System.out.println(startFormData.getProcessDefinition());
        System.out.println(startFormData.getFormKey());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        System.out.println(formProperties);
        List<FormField> formFields = startFormData.getFormFields();
        System.out.println(formFields);
        System.out.println("############");
    }


    @Test
    public void submitStartForm() {
        String processDefinitionId = "form:3:5306";
        VariableMap variableMap = Variables.createVariables()
                .putValue("days", 10)
                .putValue("reason", "请假三天")
                .putValue("startUserId", "张三")
                .putValue("category", "年假");
        ProcessInstance processInstance = formService.submitStartForm(processDefinitionId, variableMap);
        System.out.println(processInstance);
    }


    @Test
    public void getTaskFormData() {
        Task task = taskService.createTaskQuery().taskId("5411").singleResult();
        System.out.println(task.getId());

        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        System.out.println("############");
        System.out.println(taskFormData.getDeploymentId());
        System.out.println(taskFormData.getTask());
        System.out.println(taskFormData.getFormKey());
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        System.out.println(formProperties);
        List<FormField> formFields = taskFormData.getFormFields();
        System.out.println(formFields);
        System.out.println("############");
    }


    @Test
    public void getRenderedTaskForm() {
        Object renderedTaskForm = formService.getRenderedTaskForm("5504", "juel");
        System.out.println("############");
        System.out.println(renderedTaskForm);
        System.out.println("############");
    }

    @Test
    public void submitTaskForm() {
        VariableMap variableMap = Variables.createVariables()
                .putValue("startUserId", "王五")
                .putValue("category", "年假");

        formService.submitTaskForm("5411", variableMap);
    }


}
