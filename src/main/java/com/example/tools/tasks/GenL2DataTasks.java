package com.example.tools.tasks;

import com.example.tools.util.Utility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class GenL2DataTasks {
    @Autowired
    Utility utility;

    public void run() {
        //getL2DataSqnc1();
        //getL2DataSqnc2();
        // 在使用DSM時要注意CSV的欄位在第幾個
        //DSMgetL2DataSqncE();
        //DSMgetL2DataSqncA();
        //DSMgetL2DataSqncB();
        DSMgetL2DataSqnc7();
    }

    private void getL2DataSqnc1() {
        try {
            String filename = "D:\\job\\excel手動補\\0302-131100102051-B109800171-東區-臺北市南港區研究院路二段１２巷５７弄２之１號總表\\0302-131100102051-B109800171-東區-臺北市南港區研究院路二段１２巷５７弄２之１號總表.csv";
            String outputFilename = "D:\\job\\excel手動補\\0302-131100102051-B109800171" +
                    "-東區-臺北市南港區研究院路二段１２巷５７弄２之１號總表\\0302-131100102051-B109800171-東區-臺北市南港區研究院路二段１２巷５７弄２之１號總表.sql";
            List<List<String>> spotInfos = utility.readCsv(filename);

            String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_c,value_i,interface_type,sgnl_type,is_past,bat_volt,lday,nday,oday,uday,hday,bday,open_amt,trn_amt,trn_suc,trn_fail,trn_ontime,run_time) values (";
            List<String> list = new ArrayList<>();
            String interfaceId = spotInfos.get(0).get(5);
            String volt = spotInfos.get(0).get(12);
            String lDay = spotInfos.get(0).get(13);
            String nDay = spotInfos.get(0).get(14);
            String oDay = spotInfos.get(0).get(15);
            String uDay = spotInfos.get(0).get(16);
            String hDay = spotInfos.get(0).get(17);
            String bDay = spotInfos.get(0).get(18);
            String openAmt = spotInfos.get(0).get(19);
            String trnAmt = spotInfos.get(0).get(20);
            String trnSuc = spotInfos.get(0).get(21);
            String trnFail = spotInfos.get(0).get(22);
            String trnOntime = spotInfos.get(0).get(23);
            String runTime = spotInfos.get(0).get(24);
            log.info(interfaceId);
            String sqnc = "1";
            String interfaceType = "GTI";
            String signalType = "g";
            String isPast = "0";

            for (int i = 1; i < spotInfos.size(); i++) {
                String rcvtime = spotInfos.get(i).get(6);
                String valueC = spotInfos.get(i).get(7);
                String valueI = spotInfos.get(i).get(8);

                StringBuilder sb = new StringBuilder();
                sb.append("'"+interfaceId+"',");
                sb.append("'"+sqnc+"',");
                sb.append(rcvtime+",");
                sb.append(valueC+",");
                sb.append(valueI+",");
                sb.append("'"+interfaceType+"',");
                sb.append("'"+signalType+"',");
                sb.append(isPast+",");
                sb.append(volt+",");
                sb.append(lDay+",");
                sb.append(nDay+",");
                sb.append(oDay+",");
                sb.append(uDay+",");
                sb.append(hDay+",");
                sb.append(bDay+",");
                sb.append(openAmt+",");
                sb.append(trnAmt+",");
                sb.append(trnSuc+",");
                sb.append(trnFail+",");
                sb.append(trnOntime+",");
                sb.append(runTime+");");
                list.add(sqlStatement + sb.toString());
            }
            utility.writeStringListToFile(list, outputFilename);
            log.info("gen sql success.");
        } catch (Exception e) {
            log.info("genL2Data error: ", e);
        }
    }

    private void getL2DataSqnc2() {
        try {
            String filename = "D:\\job\\excel手動補\\0225-131051002141-H105130019-東區-臺北市松山區敦化北路158號及166號專用表-10043\\0225-131051002141-H105130019-東區-臺北市松山區敦化北路158號及166號專用表-10043-sqnc2.csv";
            String outputFilename = "D:\\job\\excel手動補\\0225-131051002141-H105130019-東區-臺北市松山區敦化北路158號及166號專用表-10043" +
                    "\\0225-131051002141-H105130019-東區-臺北市松山區敦化北路158號及166號專用表-10043-sqnc2.sql";
            List<List<String>> spotInfos = utility.readCsv(filename);

            //String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,interface_type," +
            //        "sgnl_type,is_past,bat_volt,lday,nday,oday,uday,hday,bday,open_amt,trn_amt,trn_suc,trn_fail," +
            //        "trn_ontime,run_time) values (";
            String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,interface_type,sgnl_type,is_past,bat_volt,trn_amt,trn_suc,trn_fail,trn_ontime,run_time) values (";
            List<String> list = new ArrayList<>();
            String interfaceId = spotInfos.get(0).get(5);
            //String lDay = spotInfos.get(0).get(13);
            //String nDay = spotInfos.get(0).get(14);
            //String oDay = spotInfos.get(0).get(15);
            //String uDay = spotInfos.get(0).get(16);
            //String hDay = spotInfos.get(0).get(17);
            //String bDay = spotInfos.get(0).get(18);
            //String openAmt = spotInfos.get(0).get(19);
            String trnAmt = spotInfos.get(0).get(20);
            String trnSuc = spotInfos.get(0).get(21);
            String trnFail = spotInfos.get(0).get(22);
            String trnOntime = spotInfos.get(0).get(23);
            String runTime = spotInfos.get(0).get(24);
            log.info(interfaceId);
            String sqnc = "2";
            String interfaceType = "GTI";
            String signalType = "4";
            String isPast = "0";
            // 電壓也要改正確的
            String volt = "3.61";
            for (int i = 1; i < spotInfos.size(); i++) {
                String rcvtime = spotInfos.get(i).get(6);
                String valueI = spotInfos.get(i).get(4);

                StringBuilder sb = new StringBuilder();
                sb.append("'"+interfaceId+"',");
                sb.append("'"+sqnc+"',");
                sb.append(rcvtime+",");
                sb.append(valueI+",");
                sb.append("'"+interfaceType+"',");
                sb.append("'"+signalType+"',");
                sb.append(isPast+",");
                sb.append(volt+",");
                //sb.append(lDay+",");
                //sb.append(nDay+",");
                //sb.append(oDay+",");
                //sb.append(uDay+",");
                //sb.append(hDay+",");
                //sb.append(bDay+",");
                //sb.append(openAmt+",");
                sb.append(trnAmt+",");
                sb.append(trnSuc+",");
                sb.append(trnFail+",");
                sb.append(trnOntime+",");
                sb.append(runTime+");");
                list.add(sqlStatement + sb.toString());
            }
            utility.writeStringListToFile(list, outputFilename);
            log.info("gen sqnc2 sql success.");
        } catch (Exception e) {
            log.info("genL2Data error: ", e);
        }
    }

    private void DSMgetL2DataSqncE() {
        try {
            String filename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-e.csv";
            String outputFilename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-e.sql";
            List<List<String>> spotInfos = utility.readCsv(filename);

            //String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,interface_type," +
            //        "sgnl_type,is_past,bat_volt,lday,nday,oday,uday,hday,bday,open_amt,trn_amt,trn_suc,trn_fail," +
            //        "trn_ontime,run_time) values (";
            String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,interface_type,sgnl_type," +
                    "is_past,bat_volt,interface_ver,trn_amt,trn_suc,trn_fail,trn_ontime,run_time) values (";
            List<String> list = new ArrayList<>();
            String interfaceId = spotInfos.get(0).get(0);
            //String lDay = spotInfos.get(0).get(13);
            //String nDay = spotInfos.get(0).get(14);
            //String oDay = spotInfos.get(0).get(15);
            //String uDay = spotInfos.get(0).get(16);
            //String hDay = spotInfos.get(0).get(17);
            //String bDay = spotInfos.get(0).get(18);
            //String openAmt = spotInfos.get(0).get(19);
            String trnAmt = spotInfos.get(0).get(15);
            String trnSuc = spotInfos.get(0).get(16);
            String trnFail = spotInfos.get(0).get(17);
            String trnOntime = spotInfos.get(0).get(18);
            String runTime = spotInfos.get(0).get(19);
            log.info(interfaceId + " sqnc=e");
            String sqnc = "e";
            String interfaceType = "GTI";
            String signalType = "4";
            String isPast = "0";
            String interfaceVer = "6";
            // 電壓也要改正確的
            String volt = "3.61";
            for (int i = 1; i < spotInfos.size(); i++) {
                String rcvtime = spotInfos.get(i).get(1);
                String valueI = spotInfos.get(i).get(3);

                StringBuilder sb = new StringBuilder();
                sb.append("'"+interfaceId+"',");
                sb.append("'"+sqnc+"',");
                sb.append(rcvtime+",");
                sb.append(valueI+",");
                sb.append("'"+interfaceType+"',");
                sb.append("'"+signalType+"',");
                sb.append(isPast+",");
                sb.append(volt+",");
                sb.append(interfaceVer+",");
                //sb.append(lDay+",");
                //sb.append(nDay+",");
                //sb.append(oDay+",");
                //sb.append(uDay+",");
                //sb.append(hDay+",");
                //sb.append(bDay+",");
                //sb.append(openAmt+",");
                sb.append(trnAmt+",");
                sb.append(trnSuc+",");
                sb.append(trnFail+",");
                sb.append(trnOntime+",");
                sb.append(runTime+");");
                list.add(sqlStatement + sb.toString());
            }
            utility.writeStringListToFile(list, outputFilename);
            log.info("gen sqnc-E sql success.");
        } catch (Exception e) {
            log.info("genL2Data error: ", e);
        }
    }

    private void DSMgetL2DataSqncA() {
        try {
            String filename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-a.csv";
            String outputFilename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-a.sql";
            List<List<String>> spotInfos = utility.readCsv(filename);

            //String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,interface_type," +
            //        "sgnl_type,is_past,bat_volt,lday,nday,oday,uday,hday,bday,open_amt,trn_amt,trn_suc,trn_fail," +
            //        "trn_ontime,run_time) values (";
            String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,value_c,interface_type," +
                    "sgnl_type," +
                    "is_past,bat_volt,interface_ver,trn_amt,trn_suc,trn_fail,trn_ontime,run_time) values (";
            List<String> list = new ArrayList<>();
            String interfaceId = spotInfos.get(0).get(0);
            //String lDay = spotInfos.get(0).get(13);
            //String nDay = spotInfos.get(0).get(14);
            //String oDay = spotInfos.get(0).get(15);
            //String uDay = spotInfos.get(0).get(16);
            //String hDay = spotInfos.get(0).get(17);
            //String bDay = spotInfos.get(0).get(18);
            //String openAmt = spotInfos.get(0).get(19);
            String trnAmt = spotInfos.get(0).get(15);
            String trnSuc = spotInfos.get(0).get(16);
            String trnFail = spotInfos.get(0).get(17);
            String trnOntime = spotInfos.get(0).get(18);
            String runTime = spotInfos.get(0).get(19);
            log.info(interfaceId);
            String sqnc = "A";
            String interfaceType = "GTI";
            String signalType = "g";
            String isPast = "0";
            String interfaceVer = "6";
            // 電壓也要改正確的
            String volt = "3.61";
            for (int i = 1; i < spotInfos.size(); i++) {
                String rcvtime = spotInfos.get(i).get(1);
                String valueC = spotInfos.get(i).get(2);
                String valueI = spotInfos.get(i).get(3);

                StringBuilder sb = new StringBuilder();
                sb.append("'"+interfaceId+"',");
                sb.append("'"+sqnc+"',");
                sb.append(rcvtime+",");
                sb.append(valueI+",");
                sb.append(valueC+",");
                sb.append("'"+interfaceType+"',");
                sb.append("'"+signalType+"',");
                sb.append(isPast+",");
                sb.append(volt+",");
                sb.append(interfaceVer+",");
                //sb.append(lDay+",");
                //sb.append(nDay+",");
                //sb.append(oDay+",");
                //sb.append(uDay+",");
                //sb.append(hDay+",");
                //sb.append(bDay+",");
                //sb.append(openAmt+",");
                sb.append(trnAmt+",");
                sb.append(trnSuc+",");
                sb.append(trnFail+",");
                sb.append(trnOntime+",");
                sb.append(runTime+");");
                list.add(sqlStatement + sb.toString());
            }
            utility.writeStringListToFile(list, outputFilename);
            log.info("gen sqnc-A sql success.");
        } catch (Exception e) {
            log.info("genL2Data error: ", e);
        }
    }

    private void DSMgetL2DataSqncB() {
        try {
            String filename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-b.csv";
            String outputFilename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-b.sql";
            List<List<String>> spotInfos = utility.readCsv(filename);

            //String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,interface_type," +
            //        "sgnl_type,is_past,bat_volt,lday,nday,oday,uday,hday,bday,open_amt,trn_amt,trn_suc,trn_fail," +
            //        "trn_ontime,run_time) values (";
            String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,value_c,interface_type," +
                    "sgnl_type," +
                    "is_past,bat_volt,interface_ver,trn_amt,trn_suc,trn_fail,trn_ontime,run_time) values (";
            List<String> list = new ArrayList<>();
            String interfaceId = spotInfos.get(0).get(0);
            //String lDay = spotInfos.get(0).get(13);
            //String nDay = spotInfos.get(0).get(14);
            //String oDay = spotInfos.get(0).get(15);
            //String uDay = spotInfos.get(0).get(16);
            //String hDay = spotInfos.get(0).get(17);
            //String bDay = spotInfos.get(0).get(18);
            //String openAmt = spotInfos.get(0).get(19);
            String trnAmt = spotInfos.get(0).get(15);
            String trnSuc = spotInfos.get(0).get(16);
            String trnFail = spotInfos.get(0).get(17);
            String trnOntime = spotInfos.get(0).get(18);
            String runTime = spotInfos.get(0).get(19);
            log.info(interfaceId);
            String sqnc = "B";
            String interfaceType = "GTI";
            String signalType = "g";
            String isPast = "0";
            String interfaceVer = "6";
            // String valueI = "0";
            // 電壓也要改正確的
            String volt = "3.61";
            for (int i = 1; i < spotInfos.size(); i++) {
                String rcvtime = spotInfos.get(i).get(1);
                String valueC = spotInfos.get(i).get(2);
                String valueI = spotInfos.get(i).get(3);

                StringBuilder sb = new StringBuilder();
                sb.append("'"+interfaceId+"',");
                sb.append("'"+sqnc+"',");
                sb.append(rcvtime+",");
                sb.append(valueI+",");
                sb.append(valueC+",");
                sb.append("'"+interfaceType+"',");
                sb.append("'"+signalType+"',");
                sb.append(isPast+",");
                sb.append(volt+",");
                sb.append(interfaceVer+",");
                //sb.append(lDay+",");
                //sb.append(nDay+",");
                //sb.append(oDay+",");
                //sb.append(uDay+",");
                //sb.append(hDay+",");
                //sb.append(bDay+",");
                //sb.append(openAmt+",");
                sb.append(trnAmt+",");
                sb.append(trnSuc+",");
                sb.append(trnFail+",");
                sb.append(trnOntime+",");
                sb.append(runTime+");");
                list.add(sqlStatement + sb.toString());
            }
            utility.writeStringListToFile(list, outputFilename);
            log.info("gen sqnc-B sql success.");
        } catch (Exception e) {
            log.info("genL2Data error: ", e);
        }
    }

    private void DSMgetL2DataSqnc7() {
        try {
            String filename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-7.csv";
            String outputFilename = "D:\\job\\dsm-宗蔚哥社中街做資料提供給中興分析\\110218002179-7.sql";
            List<List<String>> spotInfos = utility.readCsv(filename);

            //String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,interface_type," +
            //        "sgnl_type,is_past,bat_volt,lday,nday,oday,uday,hday,bday,open_amt,trn_amt,trn_suc,trn_fail," +
            //        "trn_ontime,run_time) values (";
            String sqlStatement = "insert into mtrread_l2 (interface_id,sqnc,rcvtime,value_i,value_c,interface_type," +
                    "sgnl_type," +
                    "is_past,bat_volt,interface_ver,trn_amt,trn_suc,trn_fail,trn_ontime,run_time) values (";
            List<String> list = new ArrayList<>();
            String interfaceId = spotInfos.get(0).get(0);
            //String lDay = spotInfos.get(0).get(13);
            //String nDay = spotInfos.get(0).get(14);
            //String oDay = spotInfos.get(0).get(15);
            //String uDay = spotInfos.get(0).get(16);
            //String hDay = spotInfos.get(0).get(17);
            //String bDay = spotInfos.get(0).get(18);
            //String openAmt = spotInfos.get(0).get(19);
            String trnAmt = spotInfos.get(0).get(15);
            String trnSuc = spotInfos.get(0).get(16);
            String trnFail = spotInfos.get(0).get(17);
            String trnOntime = spotInfos.get(0).get(18);
            String runTime = spotInfos.get(0).get(19);
            log.info(interfaceId);
            String sqnc = "7";
            String interfaceType = "GTI";
            String signalType = "g";
            String isPast = "0";
            String interfaceVer = "6";

            // 電壓也要改正確的
            String volt = "3.61";
            for (int i = 1; i < spotInfos.size(); i++) {
                String rcvtime = spotInfos.get(i).get(1);
                String valueC = spotInfos.get(i).get(2);
                String valueI = spotInfos.get(i).get(3);

                StringBuilder sb = new StringBuilder();
                sb.append("'"+interfaceId+"',");
                sb.append("'"+sqnc+"',");
                sb.append(rcvtime+",");
                sb.append(valueI+",");
                sb.append(valueC+",");
                sb.append("'"+interfaceType+"',");
                sb.append("'"+signalType+"',");
                sb.append(isPast+",");
                sb.append(volt+",");
                sb.append(interfaceVer+",");
                //sb.append(lDay+",");
                //sb.append(nDay+",");
                //sb.append(oDay+",");
                //sb.append(uDay+",");
                //sb.append(hDay+",");
                //sb.append(bDay+",");
                //sb.append(openAmt+",");
                sb.append(trnAmt+",");
                sb.append(trnSuc+",");
                sb.append(trnFail+",");
                sb.append(trnOntime+",");
                sb.append(runTime+");");
                list.add(sqlStatement + sb.toString());
            }
            utility.writeStringListToFile(list, outputFilename);
            log.info("gen sqnc-7 sql success.");
        } catch (Exception e) {
            log.info("genL2Data error: ", e);
        }
    }
}
