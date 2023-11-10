package com.example.tools.repository.impl;

import com.example.tools.repository.ChangeInterfaceIdRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class ChangeInterfaceIdRepositoryI implements ChangeInterfaceIdRepository {
    @Autowired
    @Qualifier("twdumJdbcTemplate")
    private JdbcTemplate twdumJdbcTemplate;

    @Override
    public void updateSetting(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.almcase_log SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.almcased SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mntrrel2 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrloc SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.compuday SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l1 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l2 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI updateSetting error: ", e);
        }
    }

    @Override
    public void updateL3(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_l3 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI updateL3 error: ", e);
        }
    }

    @Override
    public void update105L3(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_l3_10501 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10502 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10503 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10504 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10505 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10506 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10507 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10508 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10509 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10510 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10511 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10512 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI update105L3 error: ", e);
        }
    }

    @Override
    public void update106L3(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_l3_10601 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10602 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10603 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10604 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10605 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10606 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10607 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10608 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10609 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10610 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10611 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10612 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI update106L3 error: ", e);
        }
    }

    @Override
    public void update107L3(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_l3_10701 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10702 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10703 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10704 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10705 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10706 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10707 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10708 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10709 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10710 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10711 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10712 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI update107L3 error: ", e);
        }
    }

    @Override
    public void update108L3(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_l3_10801 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10802 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10803 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10804 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10805 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10806 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10807 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10808 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10809 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10810 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10811 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10812 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI update108L3 error: ", e);
        }
    }

    @Override
    public void update109L3(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_l3_10901 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10902 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10903 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10904 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10905 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10906 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10907 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10908 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10909 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10910 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10911 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_10912 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI update109L3 error: ", e);
        }
    }

    @Override
    public void update110L3(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_l3_11001 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11002 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11003 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11004 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11005 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11006 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11007 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11008 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11009 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11010 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11011 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11012 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11101 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11102 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11103 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11104 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11105 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l3_11106 SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI update110L3 error: ", e);
        }
    }

    @Override
    public void updateOthers(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.yearmonth_Water_Amnt SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.temp20181008 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.sianweiemail_excel_log SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.sendtime3 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l2_backup_14 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l2_backup_12 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l2_backup_09 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l2_1101214 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_l1_bkk SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrloc_vw SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrloc_log SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtralm SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.monitorlist SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mntrusr2_c SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mntrusr2 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mntrrule_sys SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mntctloc2 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.Microsoft_based SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.im_mtrmnt SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.error_value_c SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.compuday_ext3 SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.AMR_lday SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.AMR_disconnection_main SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.AMR_disconnection_dt SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI updateOthers error: ", e);
        }
    }

    @Override
    public void updateMtrreadFtr(String interfaceId, String waterId) {
        String sqlStatement = "DECLARE @origin varchar(50), @new varchar(50)\n" +
                "SET @origin = ?\n" +
                "SET @new = ?\n" +
                "UPDATE dbo.mtrread_ftr_day SET interface_id = @new WHERE interface_id = @origin\n" +
                "UPDATE dbo.mtrread_ftr SET interface_id = @new WHERE interface_id = @origin;";

        try {
            twdumJdbcTemplate.update(sqlStatement, new Object[]{interfaceId, waterId});
        } catch (DataAccessException e) {
            log.error("ChangeInterfaceIdRepositoryI updateMtrreadFtr error: ", e);
        }
    }
}
