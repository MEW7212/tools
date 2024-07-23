package com.example.tools.repository.impl.rowMapper;

import com.example.tools.model.Mtrread;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MtrreadRowMapper implements RowMapper<Mtrread> {
    @Override
    public Mtrread mapRow(ResultSet rs, int i) throws SQLException {
        Mtrread obj = new Mtrread();
        obj.setInterfaceId(rs.getString("interface_id"));
        obj.setRcvtime(rs.getLong("rcvtime"));
        obj.setValue_i(rs.getDouble("value_i"));
        obj.setValue_c(rs.getDouble("value_c"));

        return obj;
    }
}
