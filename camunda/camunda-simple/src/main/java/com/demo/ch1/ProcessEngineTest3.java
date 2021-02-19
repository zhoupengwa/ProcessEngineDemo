package com.demo.ch1;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineInfo;
import org.camunda.bpm.engine.ProcessEngines;

import java.util.Map;

/**
 * @author zhoupeng
 */
public class ProcessEngineTest3 {
    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getProcessEngine("default");
        System.out.println("########" + processEngine);

        ProcessEngine processEngine1 = ProcessEngines.getDefaultProcessEngine();
        System.out.println("########" + processEngine1);

        ProcessEngineInfo processEngineInfo = ProcessEngines.getProcessEngineInfo("default");
        System.out.println(processEngineInfo);
        System.out.println(processEngineInfo.getName());
        System.out.println(processEngineInfo.getResourceUrl());
        System.out.println(processEngineInfo.getException());

        Map<String, ProcessEngine> processEngines = ProcessEngines.getProcessEngines();

        System.out.println(processEngines.get("default"));
    }
}
