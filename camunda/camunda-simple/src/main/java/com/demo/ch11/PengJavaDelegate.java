package com.demo.ch11;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @author zhoupeng create on 2021-03-05
 */
public class PengJavaDelegate implements JavaDelegate {

    private Expression expression;
    private Expression fixed;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Object value = expression.getValue(delegateExecution);
        delegateExecution.setVariable("result", value);

        Object fixedValue = fixed.getValue(delegateExecution);
        delegateExecution.setVariable("fixedValue", fixedValue);
    }
}
