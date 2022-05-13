package com.example.tools.repository.impl;

import com.example.tools.model.LocalAebasic;
import com.example.tools.repository.LocalAebasicRepository;
import com.example.tools.repository.impl.rowMapper.LocalAebasicRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Log4j2
@Repository
public class LocalAebasicRepositoryI implements LocalAebasicRepository {
    @Qualifier("mdpfJdbcTemplate")
    @Autowired
    private JdbcTemplate mdpfJdbcTemplate;

    @Override
    public List<LocalAebasic> findAll() {
        String sqlStatement = "SELECT water_no, meter_no FROM local_aebasic";

        try {
            return mdpfJdbcTemplate.query(sqlStatement, new LocalAebasicRowMapper());
        } catch (DataAccessException e) {
            log.error("LocalAebasicRepositoryI findAll : ", e);
            return Collections.emptyList();
        }
    }

}
