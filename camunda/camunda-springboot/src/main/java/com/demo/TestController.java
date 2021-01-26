package com.demo;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoupeng
 */
@RestController
public class TestController {

    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("/msg")
    public String msg(String processKey) {
        ProcessDefinition definition = repositoryService.getProcessDefinition(processKey);
        return definition.toString();
    }

}
