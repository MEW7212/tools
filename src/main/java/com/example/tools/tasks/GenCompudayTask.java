package com.example.tools.tasks;

import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class GenCompudayTask {
    @Autowired
    Utility utility;

    @Value("${compuday.file.path}")
    String filename;

    @Value("${compuday.edtDate}")
    String edtDate; // 異動日期

    @Value("${compuday.edtTime}")
    String edtTime; // 異動時間

    @Value("${compuday.target.path}")
    String outputFilename;

    public void run() {
        try {
            List<List<String>> data = utility.readCsv(filename);

            String sqlStatement = "INSERT INTO \"compuday\" (\"interface_id\", \"sqnc\", \"cmpdate\", \"amnt_sys\", " +
                    "\"amnt_keyin\", \"value_c_start\", \"value_c_end\", \"edtuser\", \"edtdate\", \"edttime\", " +
                    "\"edtrsn\", \"value_c_n_s\", \"value_c_n_e\", \"value_c_u_s\", \"value_c_u_e\", \"sttn_id\", " +
                    "\"obj_num_start\", \"obj_num_end\") VALUES (";
            List<String> list = new ArrayList<>();

            String interfaceId = data.get(0).get(0);
            String edtrsn = "人工補日結";
            String edtuser = "SYS"; // 異動人員帳號

            //double valueCStart = Double.valueOf(data.get(0).get(4));
            //log.info((int) valueCStart);

            for (int i = 0; i < data.size(); i++) {
                if (i == data.size() - 1) {
                    break;
                }

                String sqnc = data.get(i).get(1);
                String cmpdate = data.get(i).get(2);
                // org.apache.commons.lang3.StringUtils 的 substringBefore 可以直接取某個字母的前段
                String valueCStart = StringUtils.substringBefore(data.get(i).get(4), "."); // 本日00:00積算值
                String valueCEnd = StringUtils.substringBefore(data.get(i + 1).get(4), "."); // 隔日00:00積算值
                int amntSysKeyin = Integer.parseInt(valueCEnd) - Integer.parseInt(valueCStart); // 出水量-系統結算


                String valueCNS = "NULL";
                String valueCNE = "NULL";
                String valueCUS = "NULL";
                String valueCUE = "NULL";
                String sttnId = data.get(i).get(7);
                String objNumStart = data.get(i).get(8);
                String objNumEnd = data.get(i + 1).get(8);

                StringBuilder sb = new StringBuilder();
                sb.append("'"+interfaceId+"',");
                sb.append("'"+sqnc+"',");
                sb.append(cmpdate+",");
                sb.append(amntSysKeyin+",");
                sb.append(amntSysKeyin+",");
                sb.append(valueCStart+",");
                sb.append(valueCEnd+",");
                sb.append("'"+edtuser+"',");
                sb.append(edtDate+",");
                sb.append(edtTime+",");
                sb.append("'"+edtrsn+"',");
                sb.append(valueCNS+",");
                sb.append(valueCNE+",");
                sb.append(valueCUS+",");
                sb.append(valueCUE+",");
                sb.append("'"+sttnId+"',");
                sb.append("'"+objNumStart+"',");
                sb.append("'"+objNumEnd+"');");
                list.add(sqlStatement + sb.toString());
            }
            utility.writeStringListToFile(list, outputFilename);
            log.info("gen compuday sql success.");

        } catch (Exception e) {
            log.info("gen compuday error: ", e);
        }
    }
}
