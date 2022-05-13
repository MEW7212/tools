package com.example.tools.repository.impl.rowMapper;

import com.example.tools.model.LocalAebasic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocalAebasicRowMapper implements RowMapper<LocalAebasic> {
    @Override
    public LocalAebasic mapRow(ResultSet rs, int i) throws SQLException {
        LocalAebasic obj = new LocalAebasic();
        obj.setWaterNo(rs.getString("water_no"));
        obj.setMeterNo(rs.getString("meter_no"));

        return obj;
    }
}
