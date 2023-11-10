package com.example.tools.tasks;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Log4j2
public class MrbEncryptDecryptTask {
    // 11 2E F8 B0 A8 BF ED 19 3A 31 F7 9C 3B EC 02 9A
    public static byte[] key_1 = { (byte) 0x11, (byte) 0x2E, (byte) 0xF8, (byte) 0xB0, (byte) 0xA8, (byte) 0xBF,
            (byte) 0xED, (byte) 0x19, (byte) 0x3A, (byte) 0x31, (byte) 0xF7, (byte) 0x9C, (byte) 0x3B, (byte) 0xEC,
            (byte) 0x02, (byte) 0x9A };
    // 3A 31 F7 9C 3B EC 02 9A 11 2E F8 B0 A8 BF ED 19
    public static byte[] key_2 = { (byte) 0x3A, (byte) 0x31, (byte) 0xF7, (byte) 0x9C, (byte) 0x3B, (byte) 0xEC,
            (byte) 0x02, (byte) 0x9A, (byte) 0x11, (byte) 0x2E, (byte) 0xF8, (byte) 0xB0, (byte) 0xA8, (byte) 0xBF,
            (byte) 0xED, (byte) 0x19 };
    // key3 = key1
    public static byte[] key_3 = key_1;

    public static final int[] key_table = { 0x8E, 0xB9, 0xF7, 0xB1, 0x51, 0xAA, 0x75, 0xD9, 0x60, 0x14, 0x3E, 0x55,
            0xB7, 0xC0, 0xB4, 0x33, 0xA6, 0x48, 0x50, 0xDC, 0x09, 0x64, 0xE9, 0x56, 0xBB, 0xD8, 0xA2, 0x2E, 0x32, 0xC4,
            0x5D, 0xAD, 0x41, 0xA1, 0x25, 0xEA, 0xC3, 0x22, 0x71, 0x4F, 0xC8, 0x81, 0x67, 0xCA, 0x49, 0x6B, 0x1B, 0xE4,
            0x37, 0x57, 0x1C, 0x0F, 0xA7, 0x69, 0x73, 0x30, 0xAE, 0xF6, 0x6D, 0xD4, 0x6E, 0x4E, 0x0A, 0x8C, 0xBC, 0x20,
            0xCC, 0x4A, 0x91, 0xEB, 0x7E, 0xDD, 0x11, 0x2C, 0x43, 0x84, 0xC7, 0xE8, 0x3D, 0x27, 0x12, 0x04, 0xCB, 0x80,
            0x5F, 0x0B, 0x17, 0x31, 0x6A, 0x6F, 0xFE, 0xD1, 0x8A, 0x1E, 0xCD, 0x54, 0x08, 0x66, 0x68, 0xBF, 0x15, 0xC6,
            0x61, 0x2A, 0x62, 0x35, 0x58, 0x2D, 0xE5, 0x3A, 0x3C, 0x59, 0x8D, 0x26, 0x7C, 0x36, 0x97, 0x06, 0xAC, 0xAF,
            0x89, 0xCE, 0xB2, 0xF0, 0x72, 0xC1, 0x46, 0x8B, 0x53, 0x29, 0xF3, 0x8F, 0x4B, 0xD7, 0xC2, 0xF2, 0x93, 0x78,
            0x5C, 0x7F, 0x3F, 0x70, 0x00, 0x83, 0xB0, 0x44, 0xEF, 0x88, 0xF5, 0xEC, 0x9E, 0x74, 0xE1, 0x9D, 0x9B, 0x9A,
            0xE0, 0x99, 0x96, 0x9F, 0xF4, 0x21, 0x1A, 0xD5, 0xA8, 0xFB, 0x10, 0x34, 0xA4, 0xBE, 0x05, 0xE7, 0x76, 0x1F,
            0x38, 0x77, 0x90, 0x03, 0x7A, 0xFC, 0x0E, 0xB6, 0xB5, 0x0C, 0xFA, 0x01, 0xF8, 0x18, 0x40, 0xCF, 0xA9, 0x63,
            0x0D, 0x7D, 0x86, 0x24, 0x1D, 0xD3, 0x65, 0x4C, 0x28, 0xD2, 0x2B, 0x52, 0x42, 0x5E, 0x79, 0xBD, 0xE2, 0x5B,
            0xC9, 0xC5, 0x3B, 0xA3, 0xF9, 0x85, 0x19, 0x07, 0xE6, 0xDB, 0x13, 0x47, 0xF1, 0xFD, 0x9C, 0x98, 0xD0, 0xEE,
            0x2F, 0x6C, 0xDA, 0xAB, 0x4D, 0x16, 0x23, 0x45, 0x95, 0xFF, 0xE3, 0x92, 0x7B, 0xDE, 0x87, 0x82, 0xA0, 0x94,
            0x39, 0x02, 0xBA, 0xD6, 0xB8, 0xA5, 0xB3, 0xDF, 0x5A, 0xED };

    private static final byte[] TABLE = new byte[key_table.length];

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    static {
        for (int i = 0; i < key_table.length; i++) {
            TABLE[i] = (byte) key_table[i];
        }
    }

    static byte tableValue(int index) {
        return index < 0 ? TABLE[0xFF + index] : TABLE[index];
    }

    public static byte[] addKey(byte[] p_text, byte[] key) {
        int p_text_len = p_text.length;
        byte[] c_text = new byte[p_text_len];
        for (int i = 0; i < p_text_len; i++) {
            c_text[i] = (byte) (p_text[i] ^ key[i % 16]);
        }
        return c_text;
    }

    public static byte[] subByte(byte[] p_text) {
        int p_text_len = p_text.length;
        byte[] c_text = new byte[p_text_len];
        for (int i = 0; i < p_text_len; i++) {
            c_text[i] = seek_s_box(p_text[i]);
        }
        return c_text;
    }

    public static byte[] swapHeadTail(byte[] p_text) {
        int p_text_len = p_text.length;
        byte[] c_text = Arrays.copyOfRange(p_text, 0, p_text_len);
        int c_text_len = c_text.length;
        c_text[0] = p_text[p_text_len - 3];
        c_text[1] = p_text[p_text_len - 2];
        c_text[2] = p_text[p_text_len - 1];
        c_text[3] = p_text[p_text_len - 4];
        c_text[c_text_len - 3] = p_text[0];
        c_text[c_text_len - 2] = p_text[1];
        c_text[c_text_len - 1] = p_text[2];
        c_text[c_text_len - 4] = p_text[3];

        return c_text;
    }

    public static byte[] mixBytes(byte[] p_text, byte enc_code) {
        int p_text_len = p_text.length;
        byte[] c_text = new byte[p_text_len];
        for (int i = 0; i < p_text_len; i++) {
            if (i != 0) {
                c_text[i] = (byte) (p_text[i] ^ c_text[i - 1]);
            } else {
                c_text[i] = (byte) (p_text[i] ^ enc_code);
            }
        }
        return c_text;
    }

    public static byte[] invMixBytes(byte[] c_text, byte enc_code) {
        int c_text_len = c_text.length;
        byte[] p_text = new byte[c_text_len];
        for (int i = 0; i < c_text_len; i++) {
            if (i != 0) {
                p_text[i] = (byte) (c_text[i] ^ c_text[i - 1]);
            } else {
                p_text[i] = (byte) (c_text[i] ^ enc_code);
            }
        }
        return p_text;
    }

    public static byte[] mixBits(byte[] p_text) {
        int p_text_len = p_text.length;
        byte[] c_text = new byte[p_text_len];
        int value;
        for (int i = 0; i < p_text_len; i++) {
            value = p_text[i];
            /*
            if (value < 0) {
                value += 0xFF;
            }
            if (value >= 128) { // 128 改成 127， p_text[i] = -128 要跑這裡
                c_text[i] = (byte) ((p_text[i] * 2) ^ 0x5B); // XOR 91
            } else { // p_text[i] = 127 要跑這裡
                c_text[i] = (byte) (p_text[i] * 2);
            }*/
            if (value < 0) {
                value += 0xFF;
                if (value >= 127) { // 128 改成 127， p_text[i] = -128 要跑這裡
                    c_text[i] = (byte) ((p_text[i] * 2) ^ 0x5B); // XOR 91
                } else { // p_text[i] = 127 要跑這裡
                    c_text[i] = (byte) (p_text[i] * 2);
                }
            } else {
                if (value >= 128) { // 128 改成 127， p_text[i] = -128 要跑這裡
                    c_text[i] = (byte) ((p_text[i] * 2) ^ 0x5B); // XOR 91
                } else { // p_text[i] = 127 要跑這裡
                    c_text[i] = (byte) (p_text[i] * 2);
                }
            }


        }
        return c_text;
    }

    public static byte[] invMixBits(byte[] p_text) {
        int p_text_len = p_text.length;
        byte[] c_text = new byte[p_text_len];
        int value;
        for (int i = 0; i < p_text_len; i++) {
            value = p_text[i];
            if (value < 0) {
                value &= 0xFF;
            }
            c_text[i] = (byte) (value / 2);
            if (value % 2 > 0) {
                c_text[i] = (byte) (c_text[i] ^ 0xAD); // XOR 173
            }
        }
        return c_text;
    }

    public static byte seek_s_box(byte key) {
        return (byte) key_table[key & 0xFF];
    }

    public static byte[] decrypt_data(byte[] source_data, byte key_code) {
        int source_data_len = source_data.length;
        byte[] result_data;
        /**
         * CCID & RSRP 不加密，位置寫死
         */
        byte[] raw_data;
        int sub;
        if (source_data[1] == 0x4B) {
            sub = 29;
            raw_data = Arrays.copyOfRange(source_data, 4, source_data_len - sub);
        } else {
            sub = 3;
            raw_data = Arrays.copyOfRange(source_data, 4, source_data_len - sub);
        }
        byte[] raw_data2 = new byte[source_data_len];


        result_data = addKey(raw_data, key_1);
        result_data = invMixBits(result_data);
        result_data = invMixBytes(result_data, key_code);
        result_data = swapHeadTail(result_data);
        result_data = subByte(result_data);
        result_data = addKey(result_data, key_2);
        result_data = invMixBits(result_data);
        result_data = invMixBytes(result_data, key_code);
        result_data = swapHeadTail(result_data);
        result_data = invMixBytes(result_data, key_code);
        result_data = subByte(result_data);
        result_data = addKey(result_data, key_3);


        raw_data2 = source_data;

        System.arraycopy(result_data, 0, raw_data2, 4, source_data_len - (4 + sub));

        log.info("decrypt raw2: " + Arrays.toString(raw_data2));
        log.info("decrypt raw2 hex: " + byteArrayToHexStr(raw_data2));
        return raw_data2;
    }

    public static byte[] encrypt_data(byte[] source_data, byte key_code) {
        int source_data_len = source_data.length;
        byte[] result_data;
        byte[] raw_data = Arrays.copyOfRange(source_data, 4, source_data_len - 3);
        byte[] raw_data2 = new byte[source_data_len];


        result_data = addKey(raw_data, key_1);
        result_data = subByte(result_data);
        result_data = mixBytes(result_data, key_code);
        result_data = swapHeadTail(result_data);
        result_data = mixBytes(result_data, key_code);
        result_data = mixBits(result_data);
        result_data = addKey(result_data, key_2);
        result_data = subByte(result_data);
        result_data = swapHeadTail(result_data);
        result_data = mixBytes(result_data, key_code);

        byte[] test = result_data;
        log.info("before mixBits test byte array: " + Arrays.toString(test));
        result_data = mixBits(result_data);
        test = result_data;
        log.info("after mixBits test byte array: " + Arrays.toString(test));
        log.info("invMixBits test byte array: " + Arrays.toString(invMixBits(test)));

        result_data = addKey(result_data, key_3);


        raw_data2 = source_data;

        //System.arraycopy(result_data, 0, raw_data2, 4, source_data_len - 7);
        System.arraycopy(result_data, 0, raw_data2, 4, result_data.length);
        return raw_data2;
    }

    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public void run() {
        byte[] before = {38, 70, 7, 37, -86, 85, -1, 63, -51, -85, -123, 119, 10, 110, 7, 2, 5, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, -127, 0, 0, 35};
        byte[] after = {38, 70, 7, 37, 108, -105, -94, -81, -43, -73, 89, -30, -70, -110, -70, -10, -37, 34, -112, 97, -91, -33, 0, 94, -118, 26, -83, -43, -29, -62, -115, -36, -119, -41, -3, -41, 35};
        log.info(byteArrayToHexStr(before));

        byte[] encrypt = encrypt_data(before, before[2]);

        log.info("encrypt: " + byteArrayToHexStr(encrypt));
        log.info("decrypt after: " + byteArrayToHexStr(decrypt_data(after, after[2])));
        log.info("decrypt encrypt: " + byteArrayToHexStr(decrypt_data(encrypt, encrypt[2])));
    }
}
