package com.example.tools.repository.impl;

import com.example.tools.model.DSM;
import com.example.tools.repository.MeterRead2TaipeiRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Repository
public class MeterRead2TaipeiRepositoryI implements MeterRead2TaipeiRepository {
    @Autowired
    @Qualifier("twcwmJdbcTemplate")
    private JdbcTemplate primaryJdbcTemplate;

    @Override
    public List<DSM>  getDSMByInterfaceId(String interfaceId) {
        String sqlStatement = "DECLARE @Interface VARCHAR(50)\n" +
                "\n" +
                "SET @Interface = ?\n" +
                "SELECT (CASE b.unit_id \n" +
                "\t\t\tWHEN '0D51' THEN '東區分處'\n" +
                "\t\t\tWHEN '0D52' THEN '西區分處'\n" +
                "\t\t\tWHEN '0D53' THEN '南區分處'\n" +
                "\t\t\tWHEN '0D54' THEN '北區分處'\n" +
                "\t\t\tWHEN '0D55' THEN '陽明分處'\n" +
                "\t\t\tEND ) AS 管理單位,\n" +
                "\t\t\t'DSM'+ b.locdesc AS 監測站, b.mtrname AS 傳訊點, a.value_i AS 瞬間值, a.value_c AS 積算值, a.bat_volt AS 電池電壓,\n" +
                "(SUBSTRING(convert(VARCHAR, a.rcvtime), 1, 3) + '/' + SUBSTRING(convert(VARCHAR, a.rcvtime), 4, 2) + '/' + SUBSTRING(convert(VARCHAR, a.rcvtime), 6, 2) + ' ' \n" +
                "+ SUBSTRING(convert(VARCHAR, a.rcvtime), 8, 2) + ':'+ SUBSTRING(convert(VARCHAR, a.rcvtime), 10, 2) + ':' + SUBSTRING(convert(VARCHAR, a.rcvtime), 12, 2)) AS 傳訊時間\n" +
                "FROM dbo.mtrread_l3 a\n" +
                "INNER JOIN mtrloc b ON (a.interface_id = b.interface_id AND a.sqnc = b.sqnc)\n" +
                "WHERE a.interface_id = @Interface AND a.sqnc IN ('a','b','e','7') AND rcvtime >= 1130823000000 AND " +
                "rcvtime < 1130906000000\n" +
                "-- ORDER BY a.sqnc, a.rcvtime\n" +
                "-- ;\n" +
                "UNION all\n" +
                "-- DECLARE @Interface VARCHAR(50)\n" +
                "\n" +
                "-- SET @Interface = '110208017346'\n" +
                "SELECT (CASE b.unit_id \n" +
                "\t\t\tWHEN '0D51' THEN '東區分處'\n" +
                "\t\t\tWHEN '0D52' THEN '西區分處'\n" +
                "\t\t\tWHEN '0D53' THEN '南區分處'\n" +
                "\t\t\tWHEN '0D54' THEN '北區分處'\n" +
                "\t\t\tWHEN '0D55' THEN '陽明分處'\n" +
                "\t\t\tEND ) AS 管理單位,\n" +
                "\t\t\t'DSM'+ b.locdesc AS 監測站, b.mtrname AS 傳訊點, a.value_i AS 瞬間值, a.value_c AS 積算值, a.bat_volt AS 電池電壓,\n" +
                "(SUBSTRING(convert(VARCHAR, a.rcvtime), 1, 3) + '/' + SUBSTRING(convert(VARCHAR, a.rcvtime), 4, 2) + '/' + SUBSTRING(convert(VARCHAR, a.rcvtime), 6, 2) + ' ' \n" +
                "+ SUBSTRING(convert(VARCHAR, a.rcvtime), 8, 2) + ':'+ SUBSTRING(convert(VARCHAR, a.rcvtime), 10, 2) + ':' + SUBSTRING(convert(VARCHAR, a.rcvtime), 12, 2)) AS 傳訊時間\n" +
                "FROM dbo.mtrread_l2 a\n" +
                "INNER JOIN mtrloc b ON (a.interface_id = b.interface_id AND a.sqnc = b.sqnc)\n" +
                "WHERE a.interface_id = @Interface AND a.sqnc IN ('a','b','e','7') AND rcvtime >= 1130823000000 AND " +
                "rcvtime < 1130906000000\n" +
                "ORDER BY 傳訊點, 傳訊時間\n" +
                ";";
        try {
            return primaryJdbcTemplate.query(sqlStatement,
                    new Object[] {interfaceId},
                    new DSMMapRowMapper());
        } catch (DataAccessException e) {
            log.info("getDSMdata:", e);
            return Collections.emptyList();
        }
    }



    private static void setDoubleOrNull(final PreparedStatement ps, final int index, final Double value) {
        if (value == null) {
            try {
                ps.setNull(index, 0);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                ps.setDouble(index, value);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setIntOrNull(final PreparedStatement ps, final int index, final Integer value) {
        if (value == null) {
            try {
                ps.setNull(index, 0);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                ps.setInt(index, value);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static class DSMMapRowMapper implements RowMapper<DSM>
    {
        @Override
        public DSM mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final DSM result = new DSM();
            result.setUnitID(rs.getString("管理單位"));
            result.setLocation(rs.getString("監測站"));
            result.setSqnc(rs.getString("傳訊點"));
            result.setValueI(rs.getString("瞬間值"));
            result.setValueC(rs.getString("積算值") == null ? "":rs.getString("積算值"));
            result.setBat(rs.getString("電池電壓"));
            result.setRcvtime(rs.getString("傳訊時間"));
            return result;
        }
    }
}
