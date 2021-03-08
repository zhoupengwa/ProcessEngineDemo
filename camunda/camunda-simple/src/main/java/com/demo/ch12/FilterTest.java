package com.demo.ch12;

import com.demo.ch11.ValueBean;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.impl.ServiceImpl;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoupeng create on 2021-03-05
 */
public class FilterTest {


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
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch12/camunda.cfg.xml");
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
                .name("过滤器测试")
                .source("本地测试")
                .tenantId("a")
                .addClasspathResource("com.demo/ch12/receiveTask.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }

    @Test
    public void startProcessInstanceByKey() {
        String key = "serviceTask";
        Map<String, Object> var = new HashMap<>();
        var.put("bean", new ValueBean("100"));
        runtimeService.startProcessInstanceByKey(key, var);
    }

    /**
     * insert into ACT_RU_FILTER (ID_, RESOURCE_TYPE_, NAME_, OWNER_, QUERY_, PROPERTIES_, REV_) values ( ?, ?, ?, ?, ?, ?, 1 )
     * 1201(String), Task(String), peng个人偏好(String), peng(String), java.io.StringReader@5df417a7(StringReader), java.io.StringReader@7c041b41(StringReader)
     */
    @Test
    public void saveFilter() {
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee("peng");

        Map<String, Object> map = new HashMap<>();
        map.put("peng-1", "peng1");
        map.put("peng-2", "peng2");
        map.put("peng-3", "peng3");

        Filter filter1 = filterService.newTaskFilter()
                .setName("peng个人偏好")
                .setOwner("peng")
                .setQuery(taskQuery)
                .setProperties(map);

        filterService.saveFilter(filter1);
        System.out.println(filter1.getId() + "," + filter1.getQuery() + "," + filter1.getProperties() + "," + filter1.getResourceType() + ","
                + filter1.getOwner() + "," + filter1.getName());
    }

    /**
     * select * from ACT_RU_FILTER where ID_ = ?
     */
    @Test
    public void getFilter() {
        String filterId = "1201";
        Filter filter = filterService.getFilter(filterId);
        System.out.println("########" + filter);
    }

    /**
     * insert into ACT_RU_FILTER (ID_, RESOURCE_TYPE_, NAME_, OWNER_, QUERY_, PROPERTIES_, REV_) values ( ?, ?, ?, ?, ?, ?, 1 )
     * 1301(String), Task(String), peng个人偏好(String), peng(String), java.io.StringReader@664a9613(StringReader), java.io.StringReader@5118388b(StringReader)
     */
    @Test
    public void extend() {
        String filterId = "1201";
        Filter filter1 = filterService.getFilter(filterId);

        TaskQuery taskQuery = taskService.createTaskQuery().taskName("name-1").taskCandidateUser("peng");
        Filter newFilter = filter1.extend(taskQuery);
        filterService.saveFilter(newFilter);
    }

    /**
     * delete from ACT_RU_FILTER where ID_ = ? and REV_ = ?
     */
    @Test
    public void deleteFilter() {
        String filterId = "1201";
        filterService.deleteFilter(filterId);
    }

    /**
     * select distinct RES.REV_, RES.ID_, RES.NAME_, RES.PARENT_TASK_ID_, RES.DESCRIPTION_, RES.PRIORITY_, RES.CREATE_TIME_, RES.OWNER_, RES.ASSIGNEE_, RES.DELEGATION_, RES.EXECUTION_ID_, RES.PROC_INST_ID_, RES.PROC_DEF_ID_, RES.CASE_EXECUTION_ID_, RES.CASE_INST_ID_, RES.CASE_DEF_ID_, RES.TASK_DEF_KEY_, RES.DUE_DATE_, RES.FOLLOW_UP_DATE_, RES.SUSPENSION_STATE_, RES.TENANT_ID_
     * from ACT_RU_TASK RES WHERE ( 1 = 1 and RES.ASSIGNEE_ = ? ) order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void getQuery() {
        String filterId = "1401";
        Filter filter1 = filterService.getFilter(filterId);

        //执行自定义命令类
        ServiceImpl service = (ServiceImpl) filterService;
        service.getCommandExecutor().execute(new CustomerCmd(filter1));
    }


}
