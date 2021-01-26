package com.demo.demo.controller;

import com.demo.demo.domain.TaskDoParam;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhoupeng
 */

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;


    /**
     * 根据代理人查询当前任务的信息
     */
    @GetMapping("/queryTask")
    public String queryTask(String assignee) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (assignee != null) {
            taskQuery.taskAssignee(assignee);
        }
        //办理人的任务列表
        List<Task> list = taskQuery.list();
        if (CollectionUtils.isEmpty(list)) {
            return "Empty";
        }
        StringBuilder builder = new StringBuilder();
        for (Task task : list) {
            builder.append("\n\n任务办理人:").append(task.getAssignee());
            builder.append("\n任务ID:").append(task.getId());
            builder.append("\n任务名称:").append(task.getName());
        }
        System.out.println(builder.toString());
        return builder.toString();
    }


    /**
     * 处理任务
     */
    @PostMapping("/doTask")
    public String doTask(@RequestBody TaskDoParam param) {
        String taskId = param.getTaskId();
        Map<String, Object> params = param.getParams();
        //代理人
        taskService.complete(taskId, params);
        String result = String.format("complete task:%s,with param: %s", taskId, param);
        System.out.println(result);
        return result;
    }
}
