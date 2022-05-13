package com.example.tools.repository.impl;

import com.example.tools.repository.LaserSerialMapRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Log4j2
@Repository
public class LaserSerialMapRepositoryI implements LaserSerialMapRepository {
    @Autowired
    @Qualifier("duckweedJdbcTemplate")
    private JdbcTemplate duckweedJdbcTemplate;

    public JdbcTemplate getDuckweedJdbcTemplate() {
        return duckweedJdbcTemplate;
    }

    public void setDuckweedJdbcTemplate(JdbcTemplate duckweedJdbcTemplate) {
        this.duckweedJdbcTemplate = duckweedJdbcTemplate;
    }

    @Override
    public int insert(String laser, String serial, String device) {
        String sqlStatement = "INSERT INTO `laser_serial_map` (`laser_engraving_number`, `production_serial_number`, " +
                "`device_type`) VALUES (?, ?, ?);";

        try {
            return this.duckweedJdbcTemplate.update(connection -> {
                final PreparedStatement ps = connection.prepareStatement(sqlStatement);
                ps.setString(1, laser);
                ps.setString(2, serial);
                ps.setString(3, device);

                return ps;
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
