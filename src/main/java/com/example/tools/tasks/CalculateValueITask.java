package com.example.tools.tasks;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Log4j2
@Component
public class CalculateValueITask {
    // 給定兩個時間，還有差異的度數，即可算出瞬間值
    private void run() throws ParseException {
        final DecimalFormat df = new DecimalFormat("##.00000");
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final long difSec =
                Math.abs((dateFormatter.parse("2022-07-30 14:25:00").getTime() -
                        dateFormatter.parse("2022-07-27 12:00:00").getTime()) / 1000L);

        //shortL = Double.valueOf(df.format((array.getAmount() - oldAmount) / difSec * 3600.0));
        Double a = Double.valueOf(df.format(66974.4327 / difSec * 3600.0));
        log.info(a);
    }
}
