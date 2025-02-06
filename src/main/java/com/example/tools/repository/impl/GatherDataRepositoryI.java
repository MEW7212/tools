package com.example.tools.repository.impl;

import com.example.tools.model.DrinkingStationInfo;
import com.example.tools.repository.GatherDataRepository;
import com.example.tools.repository.impl.rowMapper.DrinkingInfoRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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

    @Override
    public List<DrinkingStationInfo> getDrinkingInfo() {
        String sqlStatement = "SELECT \n" +
                "\tb.interface_id\n" +
                "\t, a.sttn_name \n" +
                "FROM sttnloc a\n" +
                "\tINNER JOIN mntrrel2 b \n" +
                "\t\tON a.sttn_id = b.sttn_id\n" +
                "WHERE a.sttn_name LIKE '直飲台%' AND a.isdel = 'N'";
        try {
            return jdbcTemplate.query(sqlStatement, new DrinkingInfoRowMapper());
        } catch (Exception e) {
            log.error("getDrinkingInfo 錯誤: ", e);
            return Collections.emptyList();
        }
    }

}
