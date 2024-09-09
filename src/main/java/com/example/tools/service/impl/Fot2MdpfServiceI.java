package com.example.tools.service.impl;

import com.example.tools.repository.Fot2MdpfRepository;
import com.example.tools.service.Fot2MdpfService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class Fot2MdpfServiceI implements Fot2MdpfService {

    @Autowired
    Fot2MdpfRepository fot2MdpfRepository;

    @Override
    public boolean findMeter(String meter) {
        try {
            String result= fot2MdpfRepository.findMeter(meter);

            if (result == null || result == "") {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Fot2MdpfServiceI findMeter error: ", e);
            return false;
        }
    }

    @Override
    public int insert(String meter) {
        try {
            return fot2MdpfRepository.insert(meter);
        } catch (Exception e) {
            log.error("Fot2MdpfServiceI insert error: ", e);
            return 0;
        }
    }
}
