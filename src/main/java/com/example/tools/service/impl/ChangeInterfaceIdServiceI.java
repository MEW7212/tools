package com.example.tools.service.impl;

import com.example.tools.repository.ChangeInterfaceIdRepository;
import com.example.tools.service.ChangeInterfaceIdService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class ChangeInterfaceIdServiceI implements ChangeInterfaceIdService {

    @Autowired
    ChangeInterfaceIdRepository changeInterfaceIdRepository;

    @Override
    public List<String> getAllTablesWithInterfaceId() {
        try {
            return changeInterfaceIdRepository.getAllTablesWithInterfaceId();
        } catch (Exception e) {
            log.error("TwdumServiceI getAllTablesWithInterfaceId error: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void updateTables(String table, String oldInterfaceId, String newInterfaceId) {
        try {
            int effects = changeInterfaceIdRepository.updateTables(table, oldInterfaceId, newInterfaceId);
            log.info("table: {} update old interface_id: {} to new: {} successfully, effect lines: {}",
                    table, oldInterfaceId, newInterfaceId, effects);
        }catch (Exception e) {
            log.error("TwdumServiceI updateTables error: ", e);
        }
    }

    /**
     *
    @Value("${is.setting}")
    boolean isSetting;
    @Value("${is.l3}")
    boolean isL3;
    @Value("${is.105.l3}")
    boolean is105L3;
    @Value("${is.106.l3}")
    boolean is106L3;
    @Value("${is.107.l3}")
    boolean is107L3;
    @Value("${is.108.l3}")
    boolean is108L3;
    @Value("${is.109.l3}")
    boolean is109L3;
    @Value("${is.110.l3}")
    boolean is110L3;
    @Value("${is.mtrread_ftr}")
    boolean isMtrreadFtr;
    @Value("${is.others}")
    boolean isOthers;

    @Autowired
    ChangeInterfaceIdRepository changeInterfaceIdRepository;

    @Override
    public void changeInterfaceIdToWaterId(String interfaceId, String waterId) {
        if (isSetting) {
            try {
                changeInterfaceIdRepository.updateSetting(interfaceId, waterId);
                log.info("update setting ok");
            }catch (Exception e) {
                log.error("update Setting error: ", e);
            }
        }
        if (isL3) {
            try {
                changeInterfaceIdRepository.updateL3(interfaceId, waterId);
                log.info("update l3 ok");
            }catch (Exception e) {
                log.error("update L3 error: ", e);
            }
        }
        if (is105L3) {
            try {
                changeInterfaceIdRepository.update105L3(interfaceId, waterId);
                log.info("update 105L3 ok");
            }catch (Exception e) {
                log.error("update 105L3 error: ", e);
            }
        }
        if (is106L3) {
            try {
                changeInterfaceIdRepository.update106L3(interfaceId, waterId);
                log.info("update 106L3 ok");
            }catch (Exception e) {
                log.error("update 106L3 error: ", e);
            }
        }
        if (is107L3) {
            try {
                changeInterfaceIdRepository.update107L3(interfaceId, waterId);
                log.info("update 107L3 ok");
            }catch (Exception e) {
                log.error("update 107L3 error: ", e);
            }
        }
        if (is108L3) {
            try {
                changeInterfaceIdRepository.update108L3(interfaceId, waterId);
                log.info("update 108L3 ok");
            }catch (Exception e) {
                log.error("update 108L3 error: ", e);
            }
        }
        if (is109L3) {
            try {
                changeInterfaceIdRepository.update109L3(interfaceId, waterId);
                log.info("update 109L3 ok");
            }catch (Exception e) {
                log.error("update 109L3 error: ", e);
            }
        }
        if (is110L3) {
            try {
                changeInterfaceIdRepository.update110L3(interfaceId, waterId);
                log.info("update 110L3 ok");
            }catch (Exception e) {
                log.error("update 110L3 error: ", e);
            }
        }
        if (isMtrreadFtr) {
            try {
                changeInterfaceIdRepository.updateMtrreadFtr(interfaceId, waterId);
                log.info("update MtrreadFtr ok");
            }catch (Exception e) {
                log.error("update MtrreadFtr error: ", e);
            }
        }
        if (isOthers) {
            try {
                changeInterfaceIdRepository.updateOthers(interfaceId, waterId);
                log.info("update Others ok");
            }catch (Exception e) {
                log.error("update Others error: ", e);
            }
        }
    }
     */
}
