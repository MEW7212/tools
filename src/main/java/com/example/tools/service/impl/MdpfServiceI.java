package com.example.tools.service.impl;

import com.example.tools.model.BctIdMapping;
import com.example.tools.repository.MdpfRepository;
import com.example.tools.service.MdpfService;
import com.example.tools.util.TransObjnumToMeterNo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
public class MdpfServiceI implements MdpfService {
    @Autowired
    MdpfRepository mdpfRepository;

    Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void insert1800SttnId(String sttnId) {
        try {
            mdpfRepository.insert1800SttnId(sttnId);
        } catch (Exception e) {
            log.error("MdpfServiceI insert: ", e);
            return;
        }
    }

    @Override
    public void setMapFromBctIdMapping() {
        try {
            log.info("loading bct_id_mapping...");
            List<BctIdMapping> idMappings = mdpfRepository.getBctIdMapping();

            for (BctIdMapping idMapping : idMappings) {
                String meterNo = TransObjnumToMeterNo.objnumToMeterNo(idMapping.getWaterNumber());
                map.put(meterNo, idMapping.getBctNumber());
            }
            log.info("set map ok...size: {}", map.size());
        } catch (Exception e) {
            log.error("MdpfServiceI setMapFromBctIdMapping error: ", e);
        }
    }

    @Override
    public String getBctNumber(String meterNum) {
        String bctNumber = map.get(meterNum);
        if (bctNumber != null) {
            return bctNumber;
        }
        log.error("cannot find bct_number by meter_no: {}", meterNum);
        return "";
    }
}
