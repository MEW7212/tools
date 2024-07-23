package com.example.tools.repository.impl;

import com.example.tools.model.Mtrread;
import com.example.tools.repository.CommuicationCableDocumentRepository;
import com.example.tools.repository.impl.rowMapper.MtrreadRowMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@Log4j2
public class CommuicationCableDocumentRepositoryI implements CommuicationCableDocumentRepository {
    @Autowired
    @Qualifier("twdumJdbcTemplate")
    private JdbcTemplate twdumJdbcTemplate;

    @Value("${get.l3.from}")
    String from;
    @Value("${get.l3.to}")
    String to;

    @Override
    public List<Mtrread> getData(String interfaceId) {
        try {
            String sql = "SELECT interface_id, rcvtime, value_i, value_c FROM mtrread_l3 WHERE interface_id = " +
                    "? AND rcvtime >= ? AND rcvtime < ?\n" +
                    "UNION ALL\n" +
                    "SELECT interface_id, rcvtime, value_i, value_c FROM mtrread_l2 WHERE interface_id = " +
                    "? AND rcvtime >= ? AND rcvtime < ?\n" +
                    "ORDER BY rcvtime";

            return twdumJdbcTemplate.query(sql, new MtrreadRowMapper(), new Object[]{interfaceId, from, to,
                    interfaceId, from, to});
        } catch (Exception e) {
          log.error("CommuicationCableDocumentRepositoryI getData error: ", e);
          return Collections.emptyList();
        }
    }
}
