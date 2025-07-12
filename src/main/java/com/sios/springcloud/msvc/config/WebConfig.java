package com.sios.springcloud.msvc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class WebConfig {

    @Primary
    @Bean(name = "informawebDataSource")
    @ConfigurationProperties(prefix = "informaweb.datasource")
    public DataSource informawebDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcInformaweb")
    public JdbcTemplate informawebJdbcTemplate(
            @Qualifier("informawebDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("informawebDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}