package com.example.tools.repository.impl;

import com.example.tools.repository.NewPositionBCTRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;

@Log4j2
@Repository
public class NewPositionBCTRepositoryI implements NewPositionBCTRepository {
    @Autowired
    @Qualifier("mdpfJdbcTemplate")
    private JdbcTemplate mdpfJdbcTemple;

    public JdbcTemplate getMdpfJdbcTemple() {
        return mdpfJdbcTemple;
    }

    public void setMdpfJdbcTemple(JdbcTemplate mdpfJdbcTemple) {
        this.mdpfJdbcTemple = mdpfJdbcTemple;
    }

    @Override
    public List<String> checkIfExistOrNot(String bct_number) {
        String sql = "SELECT bct_number FROM new_position_bct\n" +
                "WHERE bct_number = ?";

        try {
            return mdpfJdbcTemple.queryForList(sql, String.class, bct_number);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public int insert(String bct_number, String position_case, String position_batch) {
        String sql = "INSERT INTO `new_position_bct` (`bct_number`, `case`, `batch`, `create_time`) VALUES " +
                "(?, ?, ?, NOW());\n";

        try {
            return this.mdpfJdbcTemple.update(connection -> {
                final PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, bct_number);
                ps.setString(2, position_case);
                ps.setString(3, position_batch);

                return ps;
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }

    }


}
