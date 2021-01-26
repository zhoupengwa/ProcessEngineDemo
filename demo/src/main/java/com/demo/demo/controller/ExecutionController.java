package com.demo.demo.controller;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoupeng
 */

@RestController
@RequestMapping("/execution")
public class ExecutionController {

    @Autowired
    private RuntimeService runtimeService;


    @PutMapping("/signal")
    public String signal(String executionId) {

        //触发向后一步信号
        runtimeService.signal(executionId);
        return "SUCCESS";
    }



}
