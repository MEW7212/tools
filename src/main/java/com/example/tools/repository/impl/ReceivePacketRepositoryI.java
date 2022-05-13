package com.example.tools.repository.impl;

import com.example.tools.model.ReceivePacket;
import com.example.tools.repository.ReceivePacketRepository;
import com.example.tools.repository.impl.rowMapper.ReceivePacketRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ReceivePacketRepositoryI implements ReceivePacketRepository {
    @Autowired
    @Qualifier("mdpfJdbcTemplate")
    JdbcTemplate mdpfJdbcTemplate;

    public List<ReceivePacket> findAllPacket() {
        String sql = "SELECT meter_id, packet_content, receive_time FROM receive_bct_packet";

        try {
            return mdpfJdbcTemplate.query(sql, new ReceivePacketRowMapper());
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
