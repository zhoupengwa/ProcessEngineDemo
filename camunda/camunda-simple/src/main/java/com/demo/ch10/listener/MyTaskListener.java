package com.demo.ch10.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

/**
 * @author zhoupeng
 */
public class MyTaskListener implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        System.out.println("#########");
        String eventName = delegateTask.getEventName();
        System.out.println(eventName);//create delete等
        int priority = delegateTask.getPriority();
        System.out.println(priority);
        //注意这里，如果不判断类型，则可能陷入死循环（如果配置了assignment类型的的listener）
        if (eventName.equals("create")) {
            delegateTask.setAssignee("张太公");
//            delegateTask.addCandidateUsers(Lists.newArrayList("帅哥1","帅哥2","帅哥3"));
        }
        System.out.println("#########");
    }
}
