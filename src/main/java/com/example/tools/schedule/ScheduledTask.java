package com.example.tools.schedule;

import com.example.tools.tasks.*;
import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;

@Log4j2
@Component
public class ScheduledTask {
    // 找出XML產生錯誤的部分
    // @Autowired XmlSearchErrorTasks xmlSearchErrorTasks;

    //產生雷雕號碼生產序號對應
    // @Autowired LaserSerialMapTasks laserSerialMapTasks;

    //@Autowired
    //RotateImageTasks rotateImageTasks;

    // excel補資料產生SQL檔案
    @Autowired
    GenL2DataTasks genL2DataTasks;

    // 得到BCT NUMBERS
    @Autowired
    GetNewBCTNumberFromNASTasks getNewBCTNumberFromNASTasks;

    @Autowired
    ModifyAndRewriteExcelTasks modifyAndRewriteExcelTasks;

    @Autowired
    SpectrumCloseTasks spectrumCloseTasks;

    @Autowired
    AddDsmPositionTask addDsmPositionTask;

    @Autowired
    Utility utility;

    private final static String Key_Point = "20120486213896";
    public final static String KEY_FORMAT = "%s_%d_%02d_%02d";

    @Scheduled(initialDelay = 1000, fixedRate = 3153600000000L)
    //@Scheduled(cron = "0/10 * * * * ?")
    public void tools() throws IOException, ParseException {
        addDsmPositionTask.run();
    }
}
