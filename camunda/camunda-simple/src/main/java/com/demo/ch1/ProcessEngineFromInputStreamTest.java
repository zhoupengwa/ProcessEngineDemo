package com.demo.ch1;

import org.camunda.bpm.engine.ProcessEngineConfiguration;

import java.io.InputStream;

/**
 * @author zhoupeng
 */
public class ProcessEngineFromInputStreamTest {

    public static void main(String[] args) {

        String resource = "activiti1.cfg.xml";

        InputStream inputStream = ProcessEngineFromInputStreamTest.class.getClassLoader().getResourceAsStream(resource);

        String beanName = "processEngineConfiguration1";

        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream(inputStream, beanName);
        System.out.println(processEngineConfiguration);

    }
}
