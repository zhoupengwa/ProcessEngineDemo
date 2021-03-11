package com.demo.ch19;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @author zhoupeng create on 2021-03-11
 */
public class PengServiceTask implements JavaDelegate {



    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        throw new ProcessEngineException("测试重试异常");
    }
}
