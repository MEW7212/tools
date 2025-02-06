package com.example.tools.repository.impl;

import com.example.tools.model.DSM;
import com.example.tools.model.DrinkingStationData;
import com.example.tools.repository.MeterRead2TaipeiRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${drinking.station.start}")
    String drinkingStationStart;

    @Value("${drinking.station.end}")
    String drinkingStationEnd;

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
                "WHERE a.interface_id = @Interface AND a.sqnc IN ('a','b','e','7') AND rcvtime >= 1131229000000 AND " +
                "rcvtime < 1140105000000\n" +
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
                "WHERE a.interface_id = @Interface AND a.sqnc IN ('a','b','e','7') AND rcvtime >= 1131229000000 AND " +
                "rcvtime < 1140105000000\n" +
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

    @Override
    public List<DrinkingStationData> getDrinkingStationDataByInterfaceId(String interfaceId) {

        String sql = "DECLARE @interfaceId varchar(50)\n" +
                "DECLARE @start NUMERIC(14,0)\n" +
                "DECLARE @end NUMERIC(14,0)\n" +
                "\n" +
                "SET @interfaceId = ?\n" +
                "SET @start = ?\n" +
                "SET @end = ?\n" +
                "\n" +
                "SELECT \n" +
                "\tinterface_id AS '介面編號'\n" +
                "\t,rcvtime AS '紀錄時間'\n" +
                "\t,value_i AS '瞬間流量'\n" +
                "\t,value_c AS '累積值'\n" +
                "\t,interface_type AS '訊號源'\n" +
                "\t,dvc_sndtime AS '接收時間'\n" +
                "\t,bat_volt AS '電壓'\n" +
                "\t,com_strg AS '訊號強度'\n" +
                "\t,lday AS '漏水天數'\n" +
                "\t,nday AS '負載天數'\n" +
                "\t,oday AS '靜止天數'\n" +
                "\t,uday AS '反向天數'\n" +
                "\t,hday AS '磁干擾天數'\n" +
                "\t,bday AS '電力不足天數'\n" +
                "\t,open_amt AS '開關次數'\n" +
                "\t,value_i_n AS '正向瞬間'\n" +
                "\t,value_c_n AS '正向累積'\n" +
                "\t,value_i_u AS '反向瞬間'\n" +
                "\t,value_c_u AS '反向累積'\n" +
                "FROM dbo.mtrread_l2 \n" +
                "WHERE \n" +
                "\t1=1\n" +
                "   AND interface_id = @interfaceId  \n" +
                "\tAND rcvtime < @end \n" +
                "UNION ALL\n" +
                "SELECT \n" +
                "\tinterface_id AS '介面編號'\n" +
                "\t,rcvtime AS '紀錄時間'\n" +
                "\t,value_i AS '瞬間流量'\n" +
                "\t,value_c AS '累積值'\n" +
                "\t,interface_type AS '訊號源'\n" +
                "\t,dvc_sndtime AS '接收時間'\n" +
                "\t,bat_volt AS '電壓'\n" +
                "\t,com_strg AS '訊號強度'\n" +
                "\t,lday AS '漏水天數'\n" +
                "\t,nday AS '負載天數'\n" +
                "\t,oday AS '靜止天數'\n" +
                "\t,uday AS '反向天數'\n" +
                "\t,hday AS '磁干擾天數'\n" +
                "\t,bday AS '電力不足天數'\n" +
                "\t,open_amt AS '開關次數'\n" +
                "\t,value_i_n AS '正向瞬間'\n" +
                "\t,value_c_n AS '正向累積'\n" +
                "\t,value_i_u AS '反向瞬間'\n" +
                "\t,value_c_u AS '反向累積' \n" +
                "FROM mtrread_l3 \n" +
                "WHERE \n" +
                "\t1=1\n" +
                "\tand interface_id = @interfaceId  \n" +
                "\tAND rcvtime >= @start\n" +
                "ORDER BY rcvtime";

        try {
            return primaryJdbcTemplate.query(sql,
                    new Object[] {interfaceId, drinkingStationStart, drinkingStationEnd},
                    new DrinkingStationDataMapRowMapper());
        } catch (DataAccessException e) {
            log.error("MeterRead2TaipeiRepositoryI getDrinkingStationDataByInterfaceId error: ", e);
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

    static class DrinkingStationDataMapRowMapper implements RowMapper<DrinkingStationData>
    {
        @Override
        public DrinkingStationData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final DrinkingStationData result = new DrinkingStationData();
            result.setInterfaceId(rs.getString("介面編號"));
            result.setRcvtime(rs.getString("紀錄時間"));
            result.setValueI(rs.getString("瞬間流量"));
            result.setValueC(rs.getString("累積值"));
            result.setInterfaceType(rs.getString("訊號源"));
            result.setDvcSndTime(rs.getString("接收時間"));
            result.setBatVolt(rs.getString("電壓"));
            result.setComStrg(rs.getString("訊號強度"));
            result.setLday(rs.getString("漏水天數"));
            result.setNday(rs.getString("負載天數"));
            result.setOday(rs.getString("靜止天數"));
            result.setUday(rs.getString("反向天數"));
            result.setHday(rs.getString("磁干擾天數"));
            result.setBday(rs.getString("電力不足天數"));
            result.setOpenAmt(rs.getString("開關次數"));
            result.setValueIN(rs.getString("正向瞬間"));
            result.setValueCN(rs.getString("正向累積"));
            result.setValueIU(rs.getString("反向瞬間"));
            result.setValueCU(rs.getString("反向累積"));
            return result;
        }
    }
}
