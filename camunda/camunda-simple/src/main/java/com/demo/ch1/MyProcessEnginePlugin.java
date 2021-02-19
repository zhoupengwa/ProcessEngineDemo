package com.demo.ch1;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.history.HistoryLevel;

/**
 * @author zhoupeng
 */
public class MyProcessEnginePlugin implements ProcessEnginePlugin {
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("preInit#######");

        HistoryLevel historyLevel = processEngineConfiguration.getHistoryLevel();
        if (historyLevel.getId() != 1) {
            throw new RuntimeException("history level not 1");
        }
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        System.out.println("postInit#######");

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {
        System.out.println("postProcessEngineBuild#######");

    }
}
