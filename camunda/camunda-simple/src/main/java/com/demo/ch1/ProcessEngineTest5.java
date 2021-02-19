package com.demo.ch1;

import org.camunda.bpm.engine.ProcessEngineConfiguration;

/**
 * @author zhoupeng
 */
public class ProcessEngineTest5 {

    public static void main(String[] args) {
        String resource = "activiti1.cfg.xml";

        String beanName = "processEngineConfiguration1";

        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(resource, beanName);

        System.out.println(processEngineConfiguration);


    }
}
