package com.example.tools.tasks;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Component
public class CompressAndDeleteJsonFilesTask {
    @Value("${compress.source.folder}")
    String sourceFolder;

    @Value("${compress.target.folder}")
    String outputFolder;

    public void run() {

        File[] files = new File(sourceFolder).listFiles();
        log.info("find files num: {}", files.length);

        SevenZOutputFile sevenZOutput = null;
        String currentDate = null;

        try {
            for (File file : files) {
                if (!file.getName().endsWith(".json")) {
                    continue;
                }

                log.info("start do {}", file.getName());

                if (file.isFile() && file.getName().endsWith(".json")) {
                    String filename = file.getName();
                    Matcher matcher = Pattern.compile("(\\d{8})").matcher(filename);

                    if (matcher.find()) {
                        String date = matcher.group(1);

                        if (!date.equals(currentDate)) {
                            // 如果日期变了，创建新的7z文件
                            if (sevenZOutput != null) {
                                sevenZOutput.close();
                            }

                            currentDate = date;
                            String output7zFile = outputFolder + "/" + date + ".7z";
                            sevenZOutput = new SevenZOutputFile(new File(output7zFile));

                            log.info("create 7z {}", output7zFile);
                        }

                        // 将JSON文件添加到当前7z文件
                        try (FileInputStream fis = new FileInputStream(file)) {
                            SevenZArchiveEntry entry = sevenZOutput.createArchiveEntry(file, filename);
                            sevenZOutput.putArchiveEntry(entry);

                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = fis.read(buffer)) != -1) {
                                sevenZOutput.write(buffer, 0, bytesRead);
                            }

                            sevenZOutput.closeArchiveEntry();
                        }

                        // 删除原始JSON文件
                        file.delete();
                    }
                }
            }

            if (sevenZOutput != null) {
                sevenZOutput.close();
            }

            log.info("壓縮並刪除完成");
        } catch (IOException e) {
            log.error("error: ", e);
        }
    }
}
