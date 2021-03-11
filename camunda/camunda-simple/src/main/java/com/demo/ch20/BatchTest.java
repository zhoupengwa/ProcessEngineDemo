package com.demo.ch20;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.batch.Batch;
import org.camunda.bpm.engine.history.UserOperationLogEntry;
import org.camunda.bpm.engine.migration.MigrationPlan;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.DeploymentBuilder;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author zhoupeng create on 2021-03-09
 */
public class BatchTest {

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
        processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("com.demo/ch20/camunda.cfg.xml");
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


    @Test
    public void createDeployment() {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        Deployment deployment = deploymentBuilder
                .name("批量查询测试")
                .source("本地测试")
                .tenantId("a")
//                .addClasspathResource("com.demo/ch20/batch.bpmn")
                .addClasspathResource("com.demo/ch20/batch2.bpmn")
                .deploy();

        System.out.println(deploymentBuilder);
        System.out.println(deployment);
    }


    @Test
    public void startProcessInstanceByKey() {
        for (int i = 0; i < 10; i++) {
            runtimeService.startProcessInstanceByKey("batch");
        }
    }


    /**
     * select distinct RES.*
     * FROM ACT_RU_EXECUTION RES
     * INNER JOIN ACT_RE_PROCDEF P
     * ON RES.PROC_DEF_ID_ = P.ID_
     * WHERE RES.PARENT_ID_ is null and ( RES.PROC_INST_ID_ IN ( '11317' , '11321' ) ) order by RES.ID_ asc
     * LIMIT 2147483647 OFFSET 0;
     */
    @Test
    public void createProcessInstanceQuery() {
        Set<String> processInstanceIds = new HashSet<>();
        processInstanceIds.add("11321");
        processInstanceIds.add("11317");

        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                .processInstanceIds(processInstanceIds)
                .list();
        System.out.println(list);
    }


    @Test
    public void complete() {
        taskService.complete("1602");
    }


    @Test
    public void newMigration() {
        String sourceProcessDefinitionId = "batch:1:3";
        String targetProcessDefinitionId = "batch:2:303";

//        String sourceActivityId = "Activity_08hk2ft";
//        String targetActivityId = "Activity_01oj8gn";
        String sourceActivityId = "Activity_1nicj02";
        String targetActivityId = "Activity_1nicj02";

        MigrationPlan migrationPlan = runtimeService.createMigrationPlan(sourceProcessDefinitionId, targetProcessDefinitionId)
                .mapActivities(sourceActivityId, targetActivityId)
                .build();

        runtimeService.newMigration(migrationPlan)
//                .processInstanceIds("125", "129")
                .processInstanceIds("137")
                .execute();

    }


    /**
     * insert into ACT_RU_JOBDEF ( ID_, PROC_DEF_ID_, PROC_DEF_KEY_, ACT_ID_, JOB_TYPE_, JOB_CONFIGURATION_, JOB_PRIORITY_, SUSPENSION_STATE_, TENANT_ID_, REV_ ) values ( '503', null, null, null, 'batch-seed-job', '502', null, 1, 'a', 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_GE_BYTEARRAY(ID_, NAME_, BYTES_, DEPLOYMENT_ID_, TENANT_ID_, TYPE_, CREATE_TIME_, ROOT_PROC_INST_ID_, REMOVAL_TIME_, REV_) values ( '501', null, java.io.ByteArrayInputStream@cc6460c(ByteArrayInputStream), null, null, 2, '2021-03-11 15:57:10.066', null, null, error, 1 );
     * ------------------------------------------------------------------------------------------------------------------------
     * insert into ACT_RU_JOB ( ID_, TYPE_, LOCK_OWNER_, LOCK_EXP_TIME_, EXCLUSIVE_, EXECUTION_ID_, PROCESS_INSTANCE_ID_, PROCESS_DEF_ID_, PROCESS_DEF_KEY_, RETRIES_, EXCEPTION_STACK_ID_, EXCEPTION_MSG_, DUEDATE_, HANDLER_TYPE_, HANDLER_CFG_, DEPLOYMENT_ID_, SUSPENSION_STATE_, JOB_DEF_ID_, PRIORITY_, SEQUENCE_COUNTER_, TENANT_ID_, CREATE_TIME_, REV_ ) values ('506', 'message', null, null, true, null, null, null, null, 3, null, null, null, 'batch-seed-job', '502', null, 1, '503', 0, 1, 'a', '2021-03-11 15:57:10.086', 1 );
     */
    @Test
    public void newMigrationAsync() {
        String sourceProcessDefinitionId = "batch:1:3";
        String targetProcessDefinitionId = "batch:2:303";

        String sourceActivityId = "Activity_08hk2ft";
        String targetActivityId = "Activity_01oj8gn";

        MigrationPlan migrationPlan = runtimeService.createMigrationPlan(sourceProcessDefinitionId, targetProcessDefinitionId)
                .mapActivities(sourceActivityId, targetActivityId)
                .build();

        runtimeService.newMigration(migrationPlan)
                .processInstanceIds("121")
                .executeAsync();

    }


    @Test
    public void createBatchQuery() {
        List<Batch> list = managementService.createBatchQuery()
                .list();
        for (Batch batch : list) {
            System.out.println("#########");
            System.out.println(batch.getId());
            System.out.println(batch.getType());
            System.out.println(batch.getTotalJobs());
            System.out.println("#########");
        }

    }

    /**
     * update ACT_RU_JOB set REV_ = REV_ + 1, SUSPENSION_STATE_ = 1 WHERE JOB_DEF_ID_ = '503';
     * ------------------------------------------------------------------------------------------------------------------------
     * update ACT_RU_JOBDEF set REV_ = REV_ + 1, SUSPENSION_STATE_ = 1 WHERE ID_ = '504';
     * ------------------------------------------------------------------------------------------------------------------------
     * update ACT_RU_JOB set REV_ = REV_ + 1, SUSPENSION_STATE_ = 1 WHERE JOB_DEF_ID_ = '503';
     * ------------------------------------------------------------------------------------------------------------------------
     * update ACT_RU_JOBDEF set REV_ = REV_ + 1, SUSPENSION_STATE_ = 1 WHERE ID_ = '504';
     * ------------------------------------------------------------------------------------------------------------------------
     */
    @Test
    public void activateBatchById() {
        String batchId = "802";
        managementService.activateBatchById(batchId);
    }


    @Test
    public void executeJob() {
        String jobId = "3211";
        managementService.executeJob(jobId);
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------------
     * delete FROM ACT_RU_JOB WHERE ID_ = '706' and REV_ = 1;
     * ------------------------------------------------------------------------------------------------------------------------
     * delete FROM ACT_GE_BYTEARRAY WHERE ID_ = '701' and REV_ = 1;
     * ------------------------------------------------------------------------------------------------------------------------
     * delete B FROM ACT_GE_BYTEARRAY B INNER JOIN ACT_HI_JOB_LOG J ON B.ID_ = J.JOB_EXCEPTION_STACK_ID_ and J.JOB_EXCEPTION_STACK_ID_ is not null and J.JOB_DEF_ID_ = '704';
     * ------------------------------------------------------------------------------------------------------------------------
     * delete FROM ACT_HI_JOB_LOG WHERE JOB_DEF_ID_ = '703';
     * ------------------------------------------------------------------------------------------------------------------------
     * DELETE FROM ACT_RU_JOBDEF WHERE ID_ = '703' and REV_ = 1;
     */
    @Test
    public void deleteBatch() {
        String batchId = "902";
        boolean cascade = true;
        managementService.deleteBatch(batchId, cascade);
    }


    @Test
    public void getBpmnModelInstance() {
        String processDefinitionId = "batch:2:303";
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        BpmnModelInstance clone = bpmnModelInstance.clone();

        ModelElementInstance modelElement = clone.getModelElementById("Activity_01oj8gn");

        UserTask userTask = (UserTask) modelElement;
        userTask.setName("新节点复制");
        userTask.setId("utask_333");

        repositoryService.createDeployment().addModelInstance("复制.bpmn", clone).deploy();
    }


    @Test
    public void newMigrationLog() {
        identityService.setAuthenticatedUserId("peng111");

        String sourceProcessDefinitionId = "batch:2:303";
        String targetProcessDefinitionId = "batch:3:1803";
        MigrationPlan migrationPlan = runtimeService.createMigrationPlan(sourceProcessDefinitionId, targetProcessDefinitionId)
                .mapActivities("Activity_08hk2ft", "Activity_01oj8gn")
                .build();
        runtimeService.newMigration(migrationPlan)
                .processInstanceIds("137")
                .execute();
    }

    @Test
    public void createUserOperationLogQuery() {
        List<UserOperationLogEntry> list = historyService.createUserOperationLogQuery().list();
        System.out.println(list);
    }


    @Test
    public void createModification() {
        String processDefinitionId = "batch:2:303";

        runtimeService.createModification(processDefinitionId)
                .startAfterActivity("Activity_1nicj02")
//                .startBeforeActivity()
//                .startTransition()//跳转到连线，可以触发监听器
                .processInstanceIds("121")
                .cancelAllForActivity("Activity_08hk2ft", true)//原有的实例节点ID，是否取消原有实例
                .execute();
    }


    @Test
    public void suspendAsync() {
        identityService.setAuthenticatedUserId("peng111");

        runtimeService.updateProcessInstanceSuspensionState()
                .byProcessInstanceIds(Arrays.asList("121"))
                .suspendAsync();

    }

    @Test
    public void updateProcessInstanceSuspensionState() {
        identityService.setAuthenticatedUserId("peng111");

        runtimeService.updateProcessInstanceSuspensionState()
                .byProcessInstanceIds(Arrays.asList("121"))
                .activateAsync();

    }


    @Test
    public void restartProcessInstances() {
        String processDefinitionId = "batch:3:1803";
        runtimeService.restartProcessInstances(processDefinitionId)
                .startTransition("Flow_11hhq0u")//从连线开始
                .processInstanceIds("137")
                .execute();
    }


    @Test
    public void deleteProcessInstances() {
        List<String> processInstanceIds = new ArrayList<>();
        processInstanceIds.add("2002");
        processInstanceIds.add("2101");
        runtimeService.deleteProcessInstances(processInstanceIds, "测试删除原因", true, true);
        //runtimeService.deleteProcessInstancesAsync(processInstanceIds, runtimeService.createProcessInstanceQuery(), "测试删除原因", true, true);
    }

    @Test
    public void deleteProcessInstancesAsync() {
        List<String> processInstanceIds = new ArrayList<>();
        processInstanceIds.add("2002");
        processInstanceIds.add("2101");
        runtimeService.deleteProcessInstancesAsync(processInstanceIds, runtimeService.createProcessInstanceQuery(), "测试删除原因", true, true);
    }
}
