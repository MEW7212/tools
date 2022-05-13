package com.example.tools.repository.impl;

import com.example.tools.model.GtiModeVer5Spectrum;
import com.example.tools.repository.GtiModeVer5SpectrumRepository;
import com.example.tools.repository.impl.rowMapper.GtiModeVer5SpectrumRowMapper;
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
public class GtiModeVer5SpectrumRepositoryI implements GtiModeVer5SpectrumRepository {
    @Autowired
    @Qualifier("mdpfJdbcTemplate")
    private JdbcTemplate mdpfJdbcTemplate;

    @Override
    public List<GtiModeVer5Spectrum> findAllSpectrumData() {
        String sqlStatement = "SELECT meter_id, spectrum FROM gti_mode_ver5_spectrum";

        try {
            return mdpfJdbcTemplate.query(sqlStatement, new GtiModeVer5SpectrumRowMapper());
        }catch (DataAccessException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
