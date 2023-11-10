package com.example.tools.tasks;

import com.example.tools.service.ChangeInterfaceIdService;
import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class ChangeInterfaceIdToWaterIdTask {
    @Value("${interfaceId.waterId.pair.path}")
    String pairPath;



    @Autowired Utility utility;

    @Autowired
    ChangeInterfaceIdService changeInterfaceIdService;

    public void run() {
        List<List<String>> pairs = utility.readCsv(pairPath);


        for (int i = 0; i < pairs.size(); i++) {
            String interfaceId = pairs.get(i).get(0);
            String waterId = pairs.get(i).get(1);

            try {
                changeInterfaceIdService.changeInterfaceIdToWaterId(interfaceId, waterId);
            } catch (Exception e) {
                log.error("changeInterfaceIdToWaterId error: ", e);
                log.error("{}: interface_id = {} change to water_id = {} failed.", i, interfaceId, waterId);
                continue;
            }


            log.info("{}: interface_id = {} change to water_id = {} successful.", i, interfaceId, waterId);
        }
        log.info("finished");
    }
}
