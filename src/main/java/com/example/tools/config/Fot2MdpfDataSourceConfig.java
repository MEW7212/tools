package com.example.tools.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class Fot2MdpfDataSourceConfig {

    @Bean(name = "fot2DataSource")
    @ConfigurationProperties(prefix = "fot2.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "fot2JdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("fot2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
