package com.example.tools.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DuckweedDataSourceConfig {
    @Bean(name = "duckweedDataSource")
    @ConfigurationProperties(prefix = "duckweed.datasource")
    public DataSource duckweedDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "duckweedJdbcTemplate")
    public JdbcTemplate duckweedJdbcTemplate(@Qualifier("duckweedDataSource") DataSource duckweedDataSource) {
        return new JdbcTemplate(duckweedDataSource);
    }
}
