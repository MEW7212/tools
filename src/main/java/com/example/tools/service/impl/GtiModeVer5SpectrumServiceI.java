package com.example.tools.service.impl;

import com.example.tools.model.GtiModeVer5Spectrum;
import com.example.tools.repository.GtiModeVer5SpectrumRepository;
import com.example.tools.service.GtiModeVer5SpectrumService;
import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class GtiModeVer5SpectrumServiceI implements GtiModeVer5SpectrumService {
    @Autowired
    GtiModeVer5SpectrumRepository gtiModeVer5SpectrumRepository;

    @Autowired
    Utility utility;

    @Override
    public void findMeterIdOpenSpectrum() {
        try {
            List<String> listMeterId = new ArrayList<>();
            List<GtiModeVer5Spectrum> list = gtiModeVer5SpectrumRepository.findAllSpectrumData();

            for (GtiModeVer5Spectrum obj : list) {
                if (obj.getSpectrum().startsWith("401") && (!obj.getSpectrum().substring(41,42).contains("0"))) {
                    listMeterId.add(obj.getMeterId());
                }
            }

            utility.writeStringListToFile(listMeterId, "D:\\some_for_company\\firmware\\findMeterIdOpenSpectrum.txt");
            log.info("gen open spectrum file ok.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
