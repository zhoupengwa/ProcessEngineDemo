package com.demo.ch14;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @author zhoupeng create on 2021-03-05
 */
public class PengSendTaskJavaDelegate implements JavaDelegate {

    public static boolean wasExecuted = false;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        wasExecuted = true;
    }
}
