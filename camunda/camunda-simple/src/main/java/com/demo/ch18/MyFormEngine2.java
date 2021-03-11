package com.demo.ch18;

import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.engine.FormEngine;

/**
 * @author zhoupeng create on 2021-03-10
 */
public class MyFormEngine2 implements FormEngine {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object renderStartForm(StartFormData startFormData) {
        return null;
    }

    @Override
    public Object renderTaskForm(TaskFormData taskFormData) {
        return null;
    }
}
