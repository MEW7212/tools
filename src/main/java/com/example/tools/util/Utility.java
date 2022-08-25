package com.example.tools.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log4j2
@Component
public class Utility {
    public List<List<String>> readCsv(String filePath) {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader((new FileReader(filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
            return records;
        } catch (IOException e) {
            log.error("readCsv ", e);
            return Collections.emptyList();
        }
    }

    public void writeStringListToFile(List<String> list, String filePath) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (String ele : list) {
                bufferedWriter.write(ele);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        }catch (Exception e){
            log.info("writeStringListToFile error : ", e);
        }
    }

    public void splitFile(String path, int maxLine) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        try {
            int i = 400;
            boolean end = false;
            while (true) {
                if (end) {
                    break;
                }
                String newFile = "D:\\migration\\getFrom0701to1130\\meters\\" + i + ".txt"; // 新生成文件名
                FileOutputStream fileOutputStream = new FileOutputStream(new File(newFile));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String line = ""; // 一行一行讀取
                int m = 1;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line);

                    if (m >= maxLine) {
                        break;
                    } else {
                        bufferedWriter.write("\n");
                    }
                    m++;
                }
                if (m < maxLine) {
                    end = true;
                }
                i = i + m;
                bufferedWriter.close();
                outputStreamWriter.close();
                fileOutputStream.close();
                log.info("{} ok", i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] hexToBytes(String hex) {
        final byte[] bytes = new byte[hex.length() / 2 + 2];
        for (int i = 0; i <= (hex.length() - 2); i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }
        return bytes;
    }
}
