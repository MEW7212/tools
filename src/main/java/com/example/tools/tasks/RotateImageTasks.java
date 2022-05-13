package com.example.tools.tasks;

import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class RotateImageTasks {
    @Autowired
    Utility utility;

    public void run() throws IOException {
        File folder = new File("D:\\Penghu\\testData\\picture");
        List<String> rotatePic = new ArrayList<>();
        for (File file : folder.listFiles()) {
            String filepath = folder.getAbsolutePath() + "\\" + file.getName();
            InputStream inputStream = new FileInputStream(new File(filepath));

            BufferedImage src = ImageIO.read(inputStream);

            if (filepath.contains("_0.jpg") || filepath.contains("_5.jpg")) {
                if (src.getHeight(null) > src.getWidth(null)) {
                    rotatePic.add(filepath);

                    BufferedImage des1 = Rotate(src, 90);
                    ImageIO.write(des1, "jpg", new File(filepath));
                    log.info("rotate 90 success. {}", filepath);
                }
            }

            // 順時針旋轉 90 度
            //BufferedImage des1 = Rotate(src, 90);
            //ImageIO.write(des1, "jpg", new File(filepath));
            //log.info("rotate 90 success. {}", filepath);

            // 順時針旋轉 180 度
            //BufferedImage des2 = Rotate(src, 180);
            //ImageIO.write(des2, "jpg", new File(filepath));
            //log.info("rotate 180 success. {}", filepath);

            // 順時針旋轉 270 度
            //BufferedImage des3 = Rotate(src, 270);
            //ImageIO.write(des3, "jpg", new File(filepath));
            //log.info("rotate 270 success. {}", filepath);
        }

        utility.writeStringListToFile(rotatePic, "D:\\Penghu\\testData\\needRotatePicture.txt");
        log.info("scan finished");

        /*
        String filepath = "D:\\picture\\7D765947066_3.jpg";

        InputStream inputStream = new FileInputStream(new File(filepath));

        BufferedImage src = ImageIO.read(inputStream);

        // 順時針旋轉 90 度
        BufferedImage des1 = Rotate(src, 90);
        ImageIO.write(des1, "jpg", new File("D:\\picture\\7D765947066_3.jpg"));
        log.info("rotate success.");
         */
    }

    public static BufferedImage Rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);

        // 計算旋轉後圖片的尺寸
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 進行轉換
        g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
        g2.drawImage(src, null, null);
        return res;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // 如果旋轉較度大於90度做相應轉換
        if (angel >= 90) {
            if ((angel / 90 % 2 == 1)) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2; // 半徑?
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_delta_width = Math.atan((double) src.height / src.width);
        double angel_delta_height = Math.atan((double) src.width / src.height);

        int len_delta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_delta_width));
        int len_delta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_delta_height));
        int des_width = src.width + len_delta_width * 2;
        int des_height = src.height + len_delta_height * 2;

        return new Rectangle(new Dimension(des_width, des_height));
    }
}
