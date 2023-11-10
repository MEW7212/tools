package com.example.tools.tasks;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

enum Action{
    Stopped, Starting, Stopping, Running;
    public String getStopped(){
        return "1";
    }
    public String getStarting(){
        return "2";
    }
    public String getStopping(){
        return "3";
    }
    public String getRunning(){
        return "4";
    }
}

@Log4j2
@Component
public class WindowsServiceTasks {
    @Value("${windows.service}")
    private String windowsService;

    // @Scheduled(initialDelay = 1 * 1000, fixedDelay = 30 * 60 * 1000) //1s, then 30min
    public void run() throws IOException {
        String serviceName = windowsService;
        ArrayList arrayList = afterWindowsService(serviceName);

        for (Object arr: arrayList){
            log.info("check service: {}", arr);
            Process p = Runtime.getRuntime().exec("sc query "+ arr);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null){

                if(line.trim().startsWith("STATE")){
                    String state = line.trim().substring(line.trim().indexOf(":")+1,line.trim().indexOf(":")+4).trim();

                    if (state.equals(Action.Stopped.getStopped())){
                        log.info("{} stopped", arr);
                        Process process = Runtime.getRuntime().exec("sc start "+ arr); //when stop, then restart
                        log.info(">>>> {} restart", arr);
                    }
                    if (state.equals(Action.Starting.getStarting())){
                        log.info("{} starting", arr);
                    }
                    if (state.equals(Action.Stopping.getStopping())){
                        log.info("{} stopping", arr);
                    }
                    if (state.equals(Action.Running.getRunning())){
                        log.info("{} running", arr);
                    }
                }
                line=reader.readLine();
            }
        }
    }

    //put windowsService into arrayList
    public ArrayList<String> afterWindowsService(String windowsService){
        String[] str = windowsService.split(",");
        ArrayList arrayList = new ArrayList();
        for(int i = 0; i< str.length ; i++){
            arrayList.add(str[i]);
        }
        return arrayList;
    }

}
