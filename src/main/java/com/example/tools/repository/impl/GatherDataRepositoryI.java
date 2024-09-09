package com.example.tools.repository.impl;

import com.example.tools.repository.GatherDataRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class GatherDataRepositoryI implements GatherDataRepository {
    @Autowired
    @Qualifier("twcwmJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean checkSQLConnect() {
        String sqlStatement = "SELECT TOP 1 water_num FROM aebasic";
        try {
            List<String> list = jdbcTemplate.queryForList(sqlStatement, String.class);
            if (list.isEmpty()) {
                log.info("SQL 連接成功");
            }
            return true;
        } catch (Exception e) {
            log.error("checkSQLConnect 錯誤:", e);
            return false;
        }

    }

}
