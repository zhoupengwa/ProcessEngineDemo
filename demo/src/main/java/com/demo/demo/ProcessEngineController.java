package com.demo.demo;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoupeng
 */
@RestController
public class ProcessEngineController {

    @Autowired
    private SpringProcessEngineConfiguration processEngine;

}
