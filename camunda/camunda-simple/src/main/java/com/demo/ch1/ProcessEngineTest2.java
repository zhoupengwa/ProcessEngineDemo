package com.demo.ch1;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;

import java.util.Map;

/**
 * @author zhoupeng
 */
public class ProcessEngineTest2
{

    public static void main(String[] args) {
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        StandaloneInMemProcessEngineConfiguration processEngineConfiguration = (StandaloneInMemProcessEngineConfiguration) processEngine.getProcessEngineConfiguration();
        Map<Object, Object> beans = processEngineConfiguration.getBeans();
        Object testUser = beans.get("testUser");
        System.out.println(testUser);
    }
}
