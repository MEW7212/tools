package com.example.tools.schedule;

import com.example.tools.tasks.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ScheduledTask {
    //@Autowired WindowsServiceTasks windowsServiceTasks;

    //@Autowired AddDsmPositionTask addDsmPositionTask;

    // @Autowired BctMappingAndAddressCorrespondTask bctMappingAndAddressCorrespondTask;

    //@Autowired GenerateBCPOutputTask generateBCPOutputTask;

    //@Autowired UsingGoogleMapApiGetLatLngTask usingGoogleMapApiGetLatLngTask;

    //@Autowired GenCompudayTask genCompudayTask;

    //@Autowired MrbEncryptDecryptTask mrbEncryptDecryptTask;

    //@Autowired Utility utility;

    //@Autowired Add1800BigUsersWaterNumTask add1800BigUsersWaterNumTask;

    @Autowired SendUdpPacketTask sendUdpPacketTask;

    //@Autowired GenL2DataTasks genL2DataTasks;

    //@Autowired CompressAndDeleteJsonFilesTask compressAndDeleteJsonFilesTask;

    //@Autowired MangoDBTask mangoDBTask;

    //@Autowired JetsStreamTask jetsStreamTask;

    //private final static String Key_Point = "20120486213896";
    //public final static String KEY_FORMAT = "%s_%d_%02d_%02d";
    //private static final String DATE_FORMAT ="yyyy/MM/dd HH:mm:ss";

    // @Autowired ChangeInterfaceIdToWaterIdTask changeInterfaceIdToWaterIdTask;
    //@Autowired CommunicationCablesImproveTask communicationCablesImproveTask;



    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    //@Scheduled(cron = "0 0 * * * ?")
    public void tools() throws Exception {
        //changeInterfaceIdToWaterIdTask.run();
        //ddDsmPositionTask.run();
        sendUdpPacketTask.run();
        //compressAndDeleteJsonFilesTask.run();
        // genL2DataTasks.run();
        //mangoDBTask.run();
        //jetsStreamTask.run();
        //communicationCablesImproveTask.run();
    }
}
