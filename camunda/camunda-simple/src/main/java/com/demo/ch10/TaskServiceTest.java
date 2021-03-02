package com.demo.ch10;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricIdentityLinkLog;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng
 */
public class TaskServiceTest {

    private ProcessEngineConfiguration processEngineConfiguration;
    private RepositoryService repositoryService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private HistoryService historyService;
    private IdentityService identityService;


    @Before
    public void init() {
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch10/camunda.cfg.xml");
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        repositoryService = processEngine.getRepositoryService();
        runtimeService = processEngine.getRuntimeService();
        taskService = processEngine.getTaskService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
//        initGroupUser();
    }

    private void initGroupUser() {
        GroupEntity groupEntity1=new GroupEntity();
        groupEntity1.setRevision(0);
        groupEntity1.setName("普通员工");
        groupEntity1.setId("pt");
        identityService.saveGroup(groupEntity1);

        GroupEntity groupEntity2=new GroupEntity();
        groupEntity2.setRevision(0);
        groupEntity2.setName("总经理");
        groupEntity2.setId("GeneralManager");
        identityService.saveGroup(groupEntity2);

        UserEntity userEntity1=new UserEntity();
        userEntity1.setRevision(0);
        userEntity1.setId("zs");
        identityService.saveUser(userEntity1);

        UserEntity userEntity2=new UserEntity();
        userEntity2.setRevision(0);
        userEntity2.setId("ls");
        identityService.saveUser(userEntity2);

        UserEntity userEntity3=new UserEntity();
        userEntity3.setRevision(0);
        userEntity3.setId("ww");
        identityService.saveUser(userEntity3);

        identityService.createMembership("zs","GeneralManager");
        identityService.createMembership("ls","GeneralManager");
        identityService.createMembership("ww","pt");
    }


    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("请假流程")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch10/leave_1.bpmn")
//                .addClasspathResource("com.demo/ch10/leave_var.bpmn")
//                .addClasspathResource("com.demo/ch10/leave_listener.bpmn")
//                .addClasspathResource("com.demo/ch10/leave_users.bpmn")
//                .addClasspathResource("com.demo/ch10/leave_candidateUsers.bpmn")
                .addClasspathResource("com.demo/ch10/leave_candidate_group.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }

    @Test
    public void startProcessInstanceByKey() {
        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("userId", "张翠山");
//        variables.put("userId1", "帅哥1,帅哥2,帅哥3");
        runtimeService.startProcessInstanceByKey("leave", variables);
    }


    /**
     * select distinct RES.REV_, RES.ID_, RES.NAME_,
     * RES.PARENT_TASK_ID_, RES.DESCRIPTION_, RES.PRIORITY_, RES.CREATE_TIME_, RES.OWNER_,
     * RES.ASSIGNEE_, RES.DELEGATION_, RES.EXECUTION_ID_, RES.PROC_INST_ID_, RES.PROC_DEF_ID_,
     * RES.CASE_EXECUTION_ID_, RES.CASE_INST_ID_, RES.CASE_DEF_ID_, RES.TASK_DEF_KEY_, RES.DUE_DATE_,
     * RES.FOLLOW_UP_DATE_, RES.SUSPENSION_STATE_, RES.TENANT_ID_
     * from ACT_RU_TASK RES WHERE ( 1 = 1 and RES.ASSIGNEE_ = ? ) order by RES.ID_ asc LIMIT ? OFFSET ?
     * <p>
     * 张三(String), 2147483647(Integer), 0(Integer)
     */
    @Test
    public void findMyTask() {
        List<Task> taskList = taskService.createTaskQuery().taskAssignee("张三").list();
        for (Task task : taskList) {
            System.out.println("#############");
            System.out.println(task.getId());
            System.out.println(task.getCreateTime());
            System.out.println(task.getPriority());
            System.out.println(task.getExecutionId());
            System.out.println("#############");
        }
    }

    @Test
    public void completeMyTask() {
        String taskId = "6906";
        taskService.complete(taskId);
    }


    @Test
    public void setAssignee() {
        String taskId = "5503";
        String assignee = "张无极";
        taskService.setAssignee(taskId, assignee);
    }


    /**
     * select distinct RES.REV_, RES.ID_, RES.NAME_, RES.PARENT_TASK_ID_, RES.DESCRIPTION_,
     * RES.PRIORITY_, RES.CREATE_TIME_, RES.OWNER_, RES.ASSIGNEE_, RES.DELEGATION_, RES.EXECUTION_ID_,
     * RES.PROC_INST_ID_, RES.PROC_DEF_ID_, RES.CASE_EXECUTION_ID_, RES.CASE_INST_ID_, RES.CASE_DEF_ID_,
     * RES.TASK_DEF_KEY_, RES.DUE_DATE_, RES.FOLLOW_UP_DATE_, RES.SUSPENSION_STATE_, RES.TENANT_ID_
     * from ACT_RU_TASK RES
     * inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_
     * WHERE ( 1 = 1 and ( RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' and ( I.USER_ID_ = ? ) ) ) order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void findGroupTask() {
        List<Task> taskList = taskService
                .createTaskQuery()
                .taskCandidateUser("帅哥1")
                .list();
        for (Task task : taskList) {
            System.out.println("#############");
            System.out.println(task.getId());
            System.out.println(task.getCreateTime());
            System.out.println(task.getPriority());
            System.out.println(task.getExecutionId());
            System.out.println("#############");
        }
    }



    @Test
    public void getIdentityLinksForTask() {
        String taskId = "8208";
        List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : identityLinksForTask) {
            System.out.println("########");
            System.out.println(identityLink.getTaskId());
            System.out.println(identityLink.getProcessDefId());
            System.out.println(identityLink.getUserId());
            System.out.println("########");

        }
    }


    /**
     * select distinct RES.* from ACT_HI_IDENTITYLINK RES order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void createHistoricIdentityLinkLogQuery() {
        List<HistoricIdentityLinkLog> list = historyService.createHistoricIdentityLinkLogQuery()
                .list();
        for (HistoricIdentityLinkLog identityLinkLog : list) {
            System.out.println("###########");
            System.out.println(identityLinkLog.getOperationType());
            System.out.println(identityLinkLog.getUserId());
            System.out.println(identityLinkLog.getGroupId());
            System.out.println(identityLinkLog.getId());
            System.out.println("###########");
        }
    }


    /**
     * insert into ACT_HI_IDENTITYLINK ( ID_, TIMESTAMP_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, ROOT_PROC_INST_ID_, PROC_DEF_ID_, OPERATION_TYPE_, ASSIGNER_ID_, PROC_DEF_KEY_, TENANT_ID_, REMOVAL_TIME_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * update ACT_RU_TASK SET REV_ = ?, NAME_ = ?, PARENT_TASK_ID_ = ?, PRIORITY_ = ?, CREATE_TIME_ = ?, OWNER_ = ?, ASSIGNEE_ = ?, DELEGATION_ = ?, EXECUTION_ID_ = ?, PROC_DEF_ID_ = ?, CASE_EXECUTION_ID_ = ?, CASE_INST_ID_ = ?, CASE_DEF_ID_ = ?, TASK_DEF_KEY_ = ?, DESCRIPTION_ = ?, DUE_DATE_ = ?, FOLLOW_UP_DATE_ = ?, SUSPENSION_STATE_ = ?, TENANT_ID_ = ? where ID_= ? and REV_ = ?
     * UPDATE ACT_HI_ACTINST SET EXECUTION_ID_ = ?, PROC_DEF_KEY_ = ?, PROC_DEF_ID_ = ?, ACT_ID_ = ?, ACT_NAME_ = ?, ACT_TYPE_ = ?, PARENT_ACT_INST_ID_ = ? , ASSIGNEE_ = ? , TASK_ID_ = ? WHERE ID_ = ?
     * update ACT_HI_TASKINST set EXECUTION_ID_ = ?, PROC_DEF_KEY_ = ?, PROC_DEF_ID_ = ?, NAME_ = ?, PARENT_TASK_ID_ = ?, DESCRIPTION_ = ?, OWNER_ = ?, ASSIGNEE_ = ?, DELETE_REASON_ = ?, TASK_DEF_KEY_ = ?, PRIORITY_ = ?, DUE_DATE_ = ?, FOLLOW_UP_DATE_ = ?, CASE_INST_ID_ = ? where ID_ = ?
     */
    @Test
    public void claim() {
        String taskId = "9208";
        String userId = "帅哥1";
        taskService.claim(taskId, userId);
    }


    /**
     * insert into ACT_HI_IDENTITYLINK ( ID_, TIMESTAMP_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, ROOT_PROC_INST_ID_, PROC_DEF_ID_, OPERATION_TYPE_, ASSIGNER_ID_, PROC_DEF_KEY_, TENANT_ID_, REMOVAL_TIME_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * insert into ACT_RU_IDENTITYLINK (ID_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, PROC_DEF_ID_, TENANT_ID_, REV_ ) values (?, ?, ?, ?, ?, ?, ?, 1 )
     */
    @Test
    public void addCandidateUser() {
        String taskId = "9208";
        String userId = "帅哥4";
        taskService.addCandidateUser(taskId, userId);
    }


    /**
     * insert into ACT_HI_IDENTITYLINK ( ID_, TIMESTAMP_, TYPE_, USER_ID_, GROUP_ID_, TASK_ID_, ROOT_PROC_INST_ID_, PROC_DEF_ID_, OPERATION_TYPE_, ASSIGNER_ID_, PROC_DEF_KEY_, TENANT_ID_, REMOVAL_TIME_) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )
     * <p>
     * delete from ACT_RU_IDENTITYLINK where ID_ = ?
     */
    @Test
    public void deleteCandidateUser() {
        String taskId = "9208";
        String userId = "帅哥4";
        taskService.deleteCandidateUser(taskId, userId);
    }


    /**
     * SELECT DISTINCT
     * RES.REV_,RES.ID_,RES.NAME_,RES.PARENT_TASK_ID_,RES.DESCRIPTION_,RES.PRIORITY_,RES.CREATE_TIME_,RES.OWNER_,RES.ASSIGNEE_,RES.DELEGATION_,RES.EXECUTION_ID_,RES.PROC_INST_ID_,RES.PROC_DEF_ID_,RES.CASE_EXECUTION_ID_,RES.CASE_INST_ID_,RES.CASE_DEF_ID_,RES.TASK_DEF_KEY_,RES.DUE_DATE_,RES.FOLLOW_UP_DATE_,RES.SUSPENSION_STATE_,RES.TENANT_ID_
     * FROM
     * 	ACT_RU_TASK RES
     * 	INNER JOIN ACT_RU_IDENTITYLINK I ON I.TASK_ID_ = RES.ID_
     * WHERE
     * 	(
     * 		1 = 1
     * 		AND (
     * 			RES.ASSIGNEE_ IS NULL
     * 			AND I.TYPE_ = 'candidate'
     * 			AND ( I.GROUP_ID_ IN ( ? ) )
     * 		)
     * 	)
     * ORDER BY
     * 	RES.ID_ ASC
     * 	LIMIT ? OFFSET ?
     *
     */
    @Test
    public void findGroupTask2() {
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateGroup("pt")
                .list();
        for (Task task : taskList) {
            System.out.println("#############");
            System.out.println(task.getId());
            System.out.println(task.getCreateTime());
            System.out.println(task.getPriority());
            System.out.println(task.getExecutionId());
            System.out.println("#############");
        }
    }


    /**
     * select distinct RES.REV_, RES.ID_, RES.NAME_, RES.PARENT_TASK_ID_, RES.DESCRIPTION_,
     * RES.PRIORITY_, RES.CREATE_TIME_, RES.OWNER_, RES.ASSIGNEE_, RES.DELEGATION_, RES.EXECUTION_ID_,
     * RES.PROC_INST_ID_, RES.PROC_DEF_ID_, RES.CASE_EXECUTION_ID_, RES.CASE_INST_ID_, RES.CASE_DEF_ID_, RES.TASK_DEF_KEY_, RES.DUE_DATE_,
     * RES.FOLLOW_UP_DATE_, RES.SUSPENSION_STATE_, RES.TENANT_ID_
     * from ACT_RU_TASK RES inner join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_
     * WHERE ( 1 = 1 and ( RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' and ( I.USER_ID_ = ? or I.GROUP_ID_ IN ( ? ) ) ) ) order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void findGroupTask3() {
        List<Task> taskList = taskService.createTaskQuery()
                .taskCandidateUser("ww") //I.USER_ID_ = ? or I.GROUP_ID_ IN ( ? )，查询候选人为自己的，与候选组包含自己的
                .list();
        for (Task task : taskList) {
            System.out.println("#############");
            System.out.println(task.getId());
            System.out.println(task.getCreateTime());
            System.out.println(task.getPriority());
            System.out.println(task.getExecutionId());
            System.out.println("#############");
        }
    }
}
