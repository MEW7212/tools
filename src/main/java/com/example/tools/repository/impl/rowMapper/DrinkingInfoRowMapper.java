package com.example.tools.repository.impl.rowMapper;

import com.example.tools.model.DrinkingStationInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrinkingInfoRowMapper implements RowMapper<DrinkingStationInfo> {
    @Override
    public DrinkingStationInfo mapRow(ResultSet rs, int i) throws SQLException {
        DrinkingStationInfo obj = new DrinkingStationInfo();
        obj.setInterfaceId(rs.getString("interface_id"));
        obj.setSttnName(rs.getString("sttn_name"));
        obj.setUnitId(rs.getString("unit_id"));

        return obj;
    }
}