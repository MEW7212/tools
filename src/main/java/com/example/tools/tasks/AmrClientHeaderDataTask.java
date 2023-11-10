package com.example.tools.tasks;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Log4j2
@Component
public class AmrClientHeaderDataTask {
    public void run() throws Exception {
        String timestamp = Instant.now().toString();
        String uuid = UUID.randomUUID().toString();
        String signature = "TPE-WATER-AMR" + timestamp + UUID.randomUUID().toString();
        log.info(timestamp);
        log.info(uuid);
        log.info(genSignatureFromPem(signature));
    }

    public String genSignatureFromPem(String str) throws Exception {
        byte[] data = str.getBytes();
        // 指定RSA算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 私鑰串使用BASE64解码
        Base64.Decoder decoder = Base64.getMimeDecoder();
        byte[] decodeBytePriKey = decoder.decode(getKeyFromPem("D:/pkcs8_rsa_private_key.pem"));
        // 產生PKCS8EncodedKeySpec，生成私鑰
        PrivateKey privatekey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeBytePriKey));
        // 使用SHA256WithRSA產生Signature
        Signature signature = Signature.getInstance("SHA256withRSA");
        // 加載私鑰
        signature.initSign(privatekey);
        // 更新待簽名的字串
        signature.update(data);
        // 簽名
        byte[] signed = signature.sign();
        // 使用BASE64編碼，作為最後的簽章
        String sign = Base64.getEncoder().encodeToString(signed);

        return sign;
    }

    public String getKeyFromPem(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }

        return str;
    }
}
