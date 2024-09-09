package com.example.tools.repository.impl;

import com.example.tools.repository.Fot2MdpfRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class Fot2MdpfRepositoryI implements Fot2MdpfRepository {

    @Qualifier("fot2JdbcTemplate")
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String findMeter(String meter) {
        String sql = "SELECT a.meter_num FROM fot2_construction_meters a WHERE a.meter_num = ?";

        try {
            List<String> list = jdbcTemplate.queryForList(sql, String.class, meter);
            if(list == null || list.size() == 0) {
                return null;
            } else {
                return list.get(0);
            }
        } catch (Exception e) {
            log.error("Fot2MdpfRepositoryI findMeter error: ", e);
            return "";
        }
    }

    @Override
    public int insert(String meter) {
        String sql = "INSERT INTO `fot2_construction_meters` (`meter_num`) VALUES (?);";

        int result = 0;
        try {
            result = jdbcTemplate.update(sql, meter);
        } catch (Exception e) {
            log.error("Fot2MdpfRepositoryI findMeter error: ", e);
        }

        return result;
    }
}
