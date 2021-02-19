package com.demo.ch1.spring;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;

/**
 * @author zhoupeng
 */
public class MySpringProcessEngineConfiguration extends SpringProcessEngineConfiguration {

    @Override
    protected void init() {
        System.out.println("##########开始init");
        super.init();
        System.out.println("##########结束init");
    }
}
