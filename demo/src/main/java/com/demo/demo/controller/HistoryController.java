package com.demo.demo.controller;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhoupeng
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    /**
     * 查看历史流程实例的信息
     */
    @GetMapping("/queryHistoryProcInst")
    public String queryHistoryProcInst() {
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
        if (CollectionUtils.isEmpty(list)) {
            return "Empty";
        }
        StringBuilder builder = new StringBuilder();
        for (HistoricProcessInstance instance : list) {
            builder.append("\n\n历史流程实例id").append(instance.getId())
                    .append("\n历史流程定义的id").append(instance.getProcessDefinitionId())
                    .append("\n历史流程实例开始时间--结束时间").append(instance.getStartTime()).append("-->").append(instance.getEndTime());
        }
        System.out.println(builder.toString());
        return builder.toString();
    }


    /**
     * 查询历史实例执行任务信息
     */
    @GetMapping("/queryHistoryTask")
    public String queryHistoryTask(String processInstanceId) {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return "Empty";
        }
        StringBuilder builder = new StringBuilder();

        for (HistoricTaskInstance taskInstance : list) {
            builder.append("\n\n历史流程实例任务id:").append(taskInstance.getId());
            builder.append("\n历史流程定义的id:").append(taskInstance.getProcessDefinitionId());
            builder.append("\n历史流程实例任务名称:").append(taskInstance.getName());
            builder.append("\n历史流程实例任务处理人:").append(taskInstance.getAssignee());
        }
        String result = builder.toString();
        System.out.println(result);
        return result;
    }

}
