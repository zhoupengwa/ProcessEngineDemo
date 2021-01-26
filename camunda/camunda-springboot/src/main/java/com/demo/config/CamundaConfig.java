package com.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author zhoupeng
 */
@Configuration
@Slf4j
public class CamundaConfig {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public ProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDatabaseSchemaUpdate("false");
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setTransactionManager(transactionManager);
        return processEngineConfiguration;
    }


    @Bean
    public ProcessEngineFactoryBean processEngine(ProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngineFactoryBean processEngine = new ProcessEngineFactoryBean();
        processEngine.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        log.info("initial camunda engine...");
        return processEngine;
    }


    @Bean
    public RepositoryService repositoryService(ProcessEngineFactoryBean processEngine) {
        try {
            return processEngine.getObject().getRepositoryService();
        } catch (Exception e) {
            throw new RuntimeException("camunda repositoryService error", e);
        }
    }


}
