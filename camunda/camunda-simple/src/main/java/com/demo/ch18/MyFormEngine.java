package com.demo.ch18;

import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.engine.JuelFormEngine;

/**
 * @author zhoupeng create on 2021-03-10
 */
public class MyFormEngine extends JuelFormEngine {
    @Override
    public String getName() {
        return "peng";
    }

    @Override
    public Object renderStartForm(StartFormData startFormData) {
        return super.renderStartForm(startFormData);
    }

    @Override
    public Object renderTaskForm(TaskFormData taskFormData) {
        return super.renderTaskForm(taskFormData);
    }
}
