package com.example.tools.repository.impl.rowMapper;

import com.example.tools.model.ReceivePacket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceivePacketRowMapper implements RowMapper<ReceivePacket> {
    @Override
    public ReceivePacket mapRow(ResultSet rs, int i) throws SQLException {
        ReceivePacket obj = new ReceivePacket();
        obj.setMeter_id(rs.getString("meter_id"));
        obj.setPacket_content(rs.getString("packet_content"));
        obj.setReceive_time(rs.getTimestamp("receive_time"));

        return obj;
    }
}
