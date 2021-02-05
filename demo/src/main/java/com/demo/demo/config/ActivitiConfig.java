package com.demo.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author zhoupeng
 */
@Configuration
public class ActivitiConfig {
    private static final Logger logger = LoggerFactory.getLogger(ActivitiConfig.class);

    @Bean
    @ConfigurationProperties(prefix = "activiti.datasource")
    public DataSource dataSource() {
        return new HikariDataSource();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(transactionManager);
        //没有表先创建表
        configuration.setDatabaseType("mysql");
        configuration.setDatabaseSchemaUpdate("false");
        configuration.setJobExecutorActivate(true);
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        return configuration;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration configuration) {
        ProcessEngineFactoryBean processEngine = new ProcessEngineFactoryBean();
        processEngine.setProcessEngineConfiguration(configuration);
        logger.info("initial Acticiti engine");
        return processEngine;
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngineFactoryBean processEngine) throws Exception {
        return Objects.requireNonNull(processEngine.getObject()).getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngineFactoryBean processEngine) throws Exception {
        return Objects.requireNonNull(processEngine.getObject()).getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngineFactoryBean processEngine) throws Exception {
        return Objects.requireNonNull(processEngine.getObject()).getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngineFactoryBean processEngine) throws Exception {
        return Objects.requireNonNull(processEngine.getObject()).getHistoryService();
    }

}
