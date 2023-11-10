package com.example.tools.repository.impl.rowMapper;

import com.example.tools.model.BctIdMapping;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BctIdMappingRowMapper implements RowMapper<BctIdMapping> {
    @Override
    public BctIdMapping mapRow(ResultSet rs, int i) throws SQLException {
        BctIdMapping obj = new BctIdMapping();
        obj.setBctNumber(rs.getString("bct_number"));
        obj.setSlotIndex(rs.getInt("slot_index"));
        obj.setStationIndex(rs.getInt("station_index"));
        obj.setObjectIndex(rs.getInt("object_index"));
        obj.setWaterNumber(rs.getString("water_number"));
        obj.setEnable(rs.getString("enable"));

        return obj;
    }

}
