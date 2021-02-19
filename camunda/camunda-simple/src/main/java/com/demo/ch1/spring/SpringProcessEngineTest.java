package com.demo.ch1.spring;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;

/**
 * @author zhoupeng
 */
public class SpringProcessEngineTest {

    public static void main(String[] args) {
        //直接getDefaultProcessEngine会报错，Class<?> springConfigurationHelperClass = ReflectUtil.loadClass("org.camunda.bpm.engine.test.spring.SpringConfigurationHelper");
        // 没有test包
        //ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        // System.out.println(defaultProcessEngine);

        ProcessEngineConfiguration processEngineConfigurationFromResource = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti-context.xml");
        ProcessEngine processEngine = processEngineConfigurationFromResource.buildProcessEngine();
        System.out.println(processEngine);
    }

}
