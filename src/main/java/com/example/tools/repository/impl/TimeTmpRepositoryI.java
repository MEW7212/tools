package com.example.tools.repository.impl;

import com.example.tools.repository.TimeTmpRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class TimeTmpRepositoryI implements TimeTmpRepository {
    @Autowired
    private JdbcTemplate mdpfJdbcTemplate;

    String sqlStatement = "SELECT meter_id FROM time_tmp WHERE receive_time > NOW()";

    @Override
    public boolean checkMySQLConnect() {
        try {
            List<String> list = mdpfJdbcTemplate.queryForList(sqlStatement, String.class);
            if (!list.isEmpty()) {
                log.info("MySQL 連接成功");
            }
            return true;
        } catch (Exception e) {
            log.error("checkMySQLConnect", e);
            return false;
        }
    }
}
