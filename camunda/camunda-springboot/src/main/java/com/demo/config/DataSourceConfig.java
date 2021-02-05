//package com.demo.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * @author zhoupeng
// */
//@Configuration
//@Slf4j
//public class DataSourceConfig {
//
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari")
//    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
//        log.info("initial datasource...");
//        return buildDataSource(dataSourceProperties);
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.config")
//    public DataSourceProperties dataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    private DataSource buildDataSource(DataSourceProperties dataSourceProperties) {
//        DataSourceBuilder factory = DataSourceBuilder.create(dataSourceProperties.getClassLoader())
//                .driverClassName(dataSourceProperties.getDriverClassName())
//                .url(dataSourceProperties.getUrl())
//                .username(dataSourceProperties.getUsername())
//                .password(dataSourceProperties.getPassword());
//        if (dataSourceProperties.getType() != null) {
//            factory.type(dataSourceProperties.getType());
//        }
//        return factory.build();
//    }
//
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager primaryTransactionManager(DataSource dataSource) {
//        log.info("initial transaction manager...");
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
