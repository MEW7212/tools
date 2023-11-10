package com.example.tools.tasks;

import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class GenerateBCPOutputTask {
    @Value("${file.path}")
    private String filePath;

    @Value("${output.text}")
    private String outputPath;

    @Autowired
    Utility utility;

    public void run() throws IOException {

        File folder = new File(filePath);
        int i = 0;

        for (File file : folder.listFiles()) {
            i++;
            List<String> list = new ArrayList<>();

            String front = "INSERT INTO mtrread_l2 (interface_id, sqnc, rcvtime, value_i, value_c, obj_num) VALUES (";
            String bottom = ");";
            try (BufferedReader br = new BufferedReader((new FileReader(file.getAbsolutePath())))) {
                String line;
                while ((line = br.readLine()) != null) {

                    line = line.replace(front, "");
                    line = line.replace(bottom, "");
                    line = line.replace("null", "");
                    line = line.replace("'", "");
                    line = line.replace(" ", "");

                    StringBuilder sb = new StringBuilder();
                    String[] values = line.split(",");
                    sb.append(values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4]);
                    sb.append(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,," + values[5]);
                    list.add(sb.toString());
                }

            } catch (IOException e) {
                log.error("read SQL ", e);
            }

            utility.writeStringListToFile(list, outputPath + "output" + i + ".txt");
            log.info(i);
        }

        log.info("finish");
    }
}
