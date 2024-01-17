package com.example.tools.repository.impl;

import com.example.tools.repository.ChangeInterfaceIdRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Log4j2
public class ChangeInterfaceIdRepositoryI implements ChangeInterfaceIdRepository {
    @Autowired
    @Qualifier("twdumJdbcTemplate")
    private JdbcTemplate twdumJdbcTemplate;

    @Override
    public List<String> getAllTablesWithInterfaceId() {
        try {
            String sql = "select A.name from sys.objects A,sys.columns B where A.type='U' and A.object_id=B.object_id AND B.name = 'interface_id' ORDER BY A.name";

            return twdumJdbcTemplate.queryForList(sql, String.class);
        } catch (DataAccessException e) {
            log.error("TwdumDaoI getAllTablesWithInterfaceId error: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public int updateTables(String table, String oldInterfaceId, String newInterfaceId) {
        try {
            String sql = "DECLARE @origin varchar(50), @new varchar(50) " +
                    "SET @origin = ? SET @new = ? " +
                    "UPDATE " + table + " SET interface_id = @new WHERE interface_id = @origin;";
            return twdumJdbcTemplate.update(sql, new Object[]{oldInterfaceId, newInterfaceId});
        }catch (DataAccessException e) {
            log.error("TwdumDaoI updateTables error: ", e);
            return -1;
        }
    }
}
