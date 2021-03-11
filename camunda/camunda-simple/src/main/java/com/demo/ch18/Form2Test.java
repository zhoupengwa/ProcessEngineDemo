package com.demo.ch18;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormType;
import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.impl.form.type.DateFormType;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class Form2Test {

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
                .name("动态表单测试")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch18/form2.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void getStartFormData() {
        String processDefinitionId = "form2:4:6003";
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);

        List<FormField> formFields = startFormData.getFormFields();
        for (FormField formField : formFields) {
            System.out.println("############");
            System.out.println(formField.getId());
            System.out.println(formField.getValue());
            System.out.println(formField.getType());
            FormType formType = formField.getType();
            if (formType instanceof DateFormType) {
                DateFormType dateFormType = (DateFormType) formType;
                Object information = dateFormType.getInformation("datePattern");
                System.out.println("information:" + information);
            }

            if (formType instanceof EnumFormType) {
                EnumFormType enumFormType = (EnumFormType) formType;
                Object information = enumFormType.getInformation("values");
                System.out.println("information:" + information);
            }
            System.out.println(formField.getTypeName());
            System.out.println(formField.getLabel());
            System.out.println(formField.getDefaultValue());
            Map<String, String> properties = formField.getProperties();
            System.out.println(properties);
            System.out.println("############");
        }

    }

    @Test
    public void getStartFormData2() {
        String processDefinitionId = "form2:4:6003";
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);

        List<FormField> formFields = startFormData.getFormFields();
        for (FormField formField : formFields) {
            System.out.println("############");

            FormType formType = formField.getType();
            if (formType instanceof DateFormType) {
                DateFormType dateFormType = (DateFormType) formType;
                Object information = dateFormType.getInformation("datePattern");
                System.out.println("information:" + information);
            }
            if (formType instanceof EnumFormType) {
                EnumFormType enumFormType = (EnumFormType) formType;
                Object information = enumFormType.getInformation("values");
                System.out.println("information:" + information);
            }
            System.out.println("############");
        }

    }


    @Test
    public void getRenderedStartForm() {
        Object juel = formService.getRenderedTaskForm("6209", "peng");
        System.out.println(juel);
    }


}
