package com.example.tools.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MdpfDataSourceConfig {

    @Bean(name = "mdpfDataSource")
    @ConfigurationProperties(prefix = "mdpf.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mdpfJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("mdpfDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
