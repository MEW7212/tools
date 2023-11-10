package com.example.tools.repository.impl;

import com.example.tools.model.BctIdMapping;
import com.example.tools.repository.MdpfRepository;
import com.example.tools.repository.impl.rowMapper.BctIdMappingRowMapper;
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
public class MdpfRepositoryI implements MdpfRepository {
    @Autowired
    @Qualifier("mdpfJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @Override
    public int insert1800SttnId(String sttnId) {
        try {
            String sql = "insert into `sttn_id_1800` (`sttn_id`) values (?);";
            return jdbcTemplate.update(sql, sttnId);
        } catch (DataAccessException e) {
            log.error("MdpfRepositoryI insert1800SttnId: ", e);
            return 0;
        }
    }

    @Override
    public List<BctIdMapping> getBctIdMapping() {
        try {
            String sql = "select bct_number, slot_index, station_index, object_index, water_number, enable from " +
                    "bct_id_mapping";

            return jdbcTemplate.query(sql, new BctIdMappingRowMapper());
        }catch (DataAccessException e) {
            log.error("MdpfRepositoryI getBctIdMapping error: ", e);
            return Collections.EMPTY_LIST;
        }
    }
}
