package com.example.tools.tasks;

import io.nats.client.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Log4j2
public class JetsStreamTask {
    private Connection nc;
    private JetStream js;

    public void run() {
        try {
            Options options = new Options.Builder()
                    .server("nats://localhost:4222")
                    .maxReconnects(-1) // 設置自動重新連線的最大次數 (-1 代表無限次數)
                    .reconnectWait(Duration.ofSeconds(8)) // 設置重新連線的等待時間間隔 (會有兩秒再加上此八秒共10秒)
                    .build();
            nc = Nats.connect(options); // 建立與 NATS 伺服器之間的連接
            js = nc.jetStream(); // 獲取 JetStream 功能的入口

            if (js == null) {
                log.info("JetStream not found.");
            }

            log.info("JetStream is enabled.");

        } catch (Exception e) {
            log.error("JetStream is not enabled. Error: ", e);
        }
    }
}
