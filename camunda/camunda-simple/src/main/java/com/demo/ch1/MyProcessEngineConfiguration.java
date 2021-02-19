package com.demo.ch1;

import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;

/**
 * @author zhoupeng
 */
public class MyProcessEngineConfiguration extends StandaloneProcessEngineConfiguration {

    @Override
    protected void init() {
        System.out.println("开始init");
        super.init();
        System.out.println("结束init");
    }


    @Override
    public void initHistoryLevel() {
        super.initHistoryLevel();
    }
}
