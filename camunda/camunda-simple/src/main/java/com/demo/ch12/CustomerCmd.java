package com.demo.ch12;

import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.query.Query;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;

import java.util.List;

/**
 * @author zhoupeng create on 2021-03-08
 */
public class CustomerCmd implements Command<List<Task>> {

    private Filter filter;

    public CustomerCmd(Filter filter) {
        this.filter = filter;
    }

    @Override
    public List<Task> execute(CommandContext commandContext) {
        Query<?, ?> query = filter.getQuery();
        TaskQuery taskQuery = (TaskQuery) query;
        List<Task> list = taskQuery.list();
        System.out.println(list);
        return list;
    }
}
