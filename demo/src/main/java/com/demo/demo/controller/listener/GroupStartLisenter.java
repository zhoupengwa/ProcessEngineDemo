package com.demo.demo.controller.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;

import java.util.Set;

/**
 * @author zhoupeng
 */
public class GroupStartLisenter implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("开始会签操作！！");
        Set<IdentityLink> candidates = delegateTask.getCandidates();
        for (IdentityLink candidate : candidates) {
            System.out.println("会签人参与决策：" + candidate.getUserId());
        }
    }
}
