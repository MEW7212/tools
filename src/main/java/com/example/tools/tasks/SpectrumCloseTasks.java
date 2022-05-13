package com.example.tools.tasks;

import com.example.tools.service.GtiModeVer5SpectrumService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SpectrumCloseTasks {
    @Autowired
    GtiModeVer5SpectrumService gtiModeVer5SpectrumService;

    public void run() {
        gtiModeVer5SpectrumService.findMeterIdOpenSpectrum();
    }
}
