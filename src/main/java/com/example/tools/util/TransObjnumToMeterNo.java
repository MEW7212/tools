package com.example.tools.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TransObjnumToMeterNo {
    /**
     * 將器號轉換為表號，例如：215550130005 -> E104130005
     * @param objnum 器號 長度12
     * @return 表號 如果有錯誤或是無法轉換會回傳空字串
     */
    public static String objnumToMeterNo(String objnum) {
        try {
            if (objnum == null || objnum.length() != 12) {
                return "";
            }

            String res = "";

            String caliber = getCaliberMapRev(objnum.charAt(4));

            if (caliber.equals("")) {
                return "";
            }

            res += caliber;

            int year = (2000 + Integer.valueOf(objnum.substring(1,3))) - 1911;

            if (year < 100) {
                res += "0";
            }
            res += year;

            res += objnum.substring(6);
            return res;
        } catch (Exception e) {
            log.info("objnumToMeterNo error:", e);
            return "";
        }
    }

    /**
     * 口徑轉換
     * @param c 傳入口徑
     * @return 對應口徑，對不到則產生空字串
     */
    private static String getCaliberMapRev(char c) {
        String res;
        switch (c) {
            case '0':
                res = "A";
                break;
            case '1':
                res = "B";
                break;
            case '2':
                res = "C";
                break;
            case '4':
                res = "D";
                break;
            case '5':
                res = "E";
                break;
            case '7':
                res = "F";
                break;
            case '8':
                res = "G";
                break;
            case 'A':
                res = "H";
                break;
            case 'B':
                res = "I";
                break;
            case 'C':
                res = "J";
                break;
            case 'D':
                res = "K";
                break;
            default:
                res = "";
        }

        return res;
    }
}
