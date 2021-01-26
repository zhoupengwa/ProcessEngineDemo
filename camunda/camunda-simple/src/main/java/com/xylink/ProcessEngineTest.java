package com.xylink;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;

/**
 * camunda.installation.id	aa604847-b0c0-47ce-a8bf-acf9fe4d8896	1
 * camunda.telemetry.enabled	null	1
 * deployment.lock	0	1
 * history.cleanup.job.lock	0	1
 * historyLevel	2	1
 * installationId.lock	0	1
 * next.dbid	1	1
 * schema.history	create(fox)	1
 * schema.version	fox	1
 * startup.lock	0	1
 * telemetry.lock	0	1
 */
public class ProcessEngineTest {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println("流程名称：" + processEngine.getName());
        System.out.println("配置类：" + processEngine.getProcessEngineConfiguration());
        System.out.println("鉴权：" + processEngine.getAuthorizationService());
        System.out.println("外部任务：" + processEngine.getExternalTaskService());
        System.out.println("过滤器：" + processEngine.getFilterService());

        System.out.println(processEngine.getFormService());
        System.out.println(processEngine.getHistoryService());

        System.out.println(processEngine.getIdentityService());
        System.out.println(processEngine.getManagementService());
        System.out.println(processEngine.getRepositoryService());
        System.out.println(processEngine.getRuntimeService());
        System.out.println(processEngine.getTaskService());
    }
}
