package com.example.tools.repository.impl;

import com.example.tools.model.DsmInfo;
import com.example.tools.repository.DsmInsertRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class DsmInsertRepositoryI implements DsmInsertRepository {
    @Autowired
    @Qualifier("twcwmJdbcTemplate")
    private JdbcTemplate twcwmJdbcTemplate;

    @Override
    public int insertRel1(DsmInfo dsmInfo){
        String sql = "INSERT INTO \"mntrrel1\" (\"unit_id\", \"sttn_id\") VALUES (?, ?);";
        try {
            return twcwmJdbcTemplate.update(sql,
                    dsmInfo.getUnitId(),
                    dsmInfo.getInterfaceId()
            );
        }catch (DataAccessException e) {
            log.error("DsmInsertRepositoryI insertRel1: ", e);
            return 0;
        }
    }

    @Override
    public int insertRel2(DsmInfo dsmInfo){
        String sql = "INSERT INTO \"mntrrel2\" (\"sttn_id\", \"interface_id\", \"sqnc\") VALUES (?, " +
                "?, '7');\n" +
                "INSERT INTO \"mntrrel2\" (\"sttn_id\", \"interface_id\", \"sqnc\") VALUES (?, " +
                "?, 'A');\n" +
                "INSERT INTO \"mntrrel2\" (\"sttn_id\", \"interface_id\", \"sqnc\") VALUES (?, " +
                "?, 'B');\n" +
                "INSERT INTO \"mntrrel2\" (\"sttn_id\", \"interface_id\", \"sqnc\") VALUES (?, " +
                "?, 'e');";

        try {
            return twcwmJdbcTemplate.update(sql,
                    dsmInfo.getInterfaceId(),
                    dsmInfo.getInterfaceId(),
                    dsmInfo.getInterfaceId(),
                    dsmInfo.getInterfaceId(),
                    dsmInfo.getInterfaceId(),

                    dsmInfo.getInterfaceId(),
                    dsmInfo.getInterfaceId(),
                    dsmInfo.getInterfaceId()
            );
        }catch (DataAccessException e){
            log.error("DsmInsertRepositoryI insertRel2: ", e);
            return 0;
        }
    }

    @Override
    public int insertMtrloc(DsmInfo dsmInfo){
        String sql = "INSERT INTO \"mtrloc\" (\"interface_id\", \"sqnc\", \"ctgr\", \"unit_id\", \"mtrname\", " +
                "\"locdesc\", \"lat\", \"lng\", \"brand\", \"model\", \"obj_num\", \"bore\", \"flwtype\", \"sim\", " +
                "\"isdel\", \"lst_sqnc\") VALUES (?, '7', 'g', ?, '正向反向總和', ?, ?, ?, ?, ?, ?, ?, 'e', ?, 'N', 3);\n" +
                "INSERT INTO \"mtrloc\" (\"interface_id\", \"sqnc\", \"ctgr\", \"unit_id\", \"mtrname\", \"locdesc\"," +
                " \"lat\", \"lng\", \"brand\", \"model\", \"obj_num\", \"bore\", \"flwtype\", \"sim\", \"isdel\", " +
                "\"lst_sqnc\") VALUES (?, 'A', 'g', ?, '正向流量', ?, ?, ?, ?, ?, ?, ?, 'e', ?, 'N', 1);\n" +
                "INSERT INTO \"mtrloc\" (\"interface_id\", \"sqnc\", \"ctgr\", \"unit_id\", \"mtrname\", \"locdesc\"," +
                " \"lat\", \"lng\", \"brand\", \"model\", \"obj_num\", \"bore\", \"flwtype\", \"sim\", \"isdel\", " +
                "\"lst_sqnc\") VALUES (?, 'B', 'g', ?, '反向流量', ?, ?, ?, ?, ?, ?, ?, 'e', ?, 'N', 2);\n" +
                "INSERT INTO \"mtrloc\" (\"interface_id\", \"sqnc\", \"ctgr\", \"unit_id\", \"mtrname\", \"locdesc\"," +
                " \"lat\", \"lng\", \"brand\", \"model\", \"obj_num\", \"bore\", \"flwtype\", \"sim\", \"isdel\", " +
                "\"lst_sqnc\") VALUES (?, 'e', 'g', ?, '壓力', ?, ?, ?, ?, ?, ?, ?, 'e', ?, 'N', 4);";

        try {
            return twcwmJdbcTemplate.update(sql,
                    dsmInfo.getInterfaceId(),
                    dsmInfo.getUnitId(),
                    dsmInfo.getAddress(),
                    dsmInfo.getLat(),
                    dsmInfo.getLng(),
                    dsmInfo.getBrand(),
                    dsmInfo.getModel(),
                    dsmInfo.getObjNum(),
                    dsmInfo.getBore(),
                    dsmInfo.getSim(),

                    dsmInfo.getInterfaceId(),
                    dsmInfo.getUnitId(),
                    dsmInfo.getAddress(),
                    dsmInfo.getLat(),
                    dsmInfo.getLng(),
                    dsmInfo.getBrand(),
                    dsmInfo.getModel(),
                    dsmInfo.getObjNum(),
                    dsmInfo.getBore(),
                    dsmInfo.getSim(),

                    dsmInfo.getInterfaceId(),
                    dsmInfo.getUnitId(),
                    dsmInfo.getAddress(),
                    dsmInfo.getLat(),
                    dsmInfo.getLng(),
                    dsmInfo.getBrand(),
                    dsmInfo.getModel(),
                    dsmInfo.getObjNum(),
                    dsmInfo.getBore(),
                    dsmInfo.getSim(),

                    dsmInfo.getInterfaceId(),
                    dsmInfo.getUnitId(),
                    dsmInfo.getAddress(),
                    dsmInfo.getLat(),
                    dsmInfo.getLng(),
                    dsmInfo.getBrand(),
                    dsmInfo.getModel(),
                    dsmInfo.getObjNum(),
                    dsmInfo.getBore(),
                    dsmInfo.getSim()

                    );
        }catch (DataAccessException e){
            log.error("DsmInsertRepositoryI insertMtrloc: ", e);
            return 0;
        }
    }

    @Override
    public int insertSttnloc(DsmInfo dsmInfo){
        String sql = "INSERT INTO \"sttnloc\" (\"sttn_id\", \"sttn_name\", \"unit_id\", \"addr\", \"lat\", \"lng\", " +
                "\"isdel\",\"sttn_prop\") VALUES (?, ?, ?, ?, ?, ?,'N', '1');\n";

        try {
            return twcwmJdbcTemplate.update(sql,
                    dsmInfo.getInterfaceId(),
                    dsmInfo.getName(),
                    dsmInfo.getUnitId(),
                    dsmInfo.getAddress(),
                    dsmInfo.getLat(),
                    dsmInfo.getLng()
                    );
        }catch (DataAccessException e){
            log.error("DsmInsertRepositoryI insertSttnloc: ", e);
            return 0;
        }
    }
}
