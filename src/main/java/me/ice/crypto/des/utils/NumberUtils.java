package me.ice.crypto.des.utils;

import me.ice.crypto.des.impl.PaddingMode;
import org.apache.commons.codec.binary.Hex;


/**
 * 数据转换工具类
 *
 * @author ice
 */
public class NumberUtils {

    /**
     * 将一个字符转换为二进制数据字符串
     *
     * @param superman
     */
    public static String encodeToBinaryStr(String superman) {
        byte[] bytes = superman.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            String str = String.format("%8s", Integer.toBinaryString((int) b)).replace(" ", "0");
            binary.append(str);
        }
        return binary.toString();
    }

    /**
     * 输入一个字符串
     * 输出： 一个二进制（0,1）的数组
     *
     * @param superman
     */
    public static byte[] encodeToBinary(String superman, PaddingMode paddingMode) {
        if (paddingMode == null) {
            throw new IllegalArgumentException("paddingMode is valid!");
        }

        byte[] bytes = superman.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            String str = String.format("%8s", Integer.toBinaryString((int) b)).replace(" ", "0");
            binary.append(str);
        }
        // 不足零末尾补零到64的倍数
        switch (paddingMode) {
            case ZeroPadding: {
                while (binary.length() == 0 || binary.length() % 64 != 0) {
                    binary.append("0");
                }
                break;
            }
            case NoPadding:
                break;
            case PKCS5:
                break;

            case PKCS7:
                break;
            default:
                ;
        }

        byte[] ret = new byte[binary.length()];
        int i = 0;
        for (String it : binary.toString().split("")) {
            ret[i] = Byte.parseByte(it);
            i++;
        }
        return ret;
    }

    public static String encodeHexString(String input) {
        return Hex.encodeHexString(input.getBytes());
    }

    /**
     * 转换为二进制，不够补0
     *
     * @param input
     * @return
     */
    public static String toBinaryStr(int input) {
        StringBuilder s = new StringBuilder(Integer.toBinaryString(input));
        while (s.length() < 4) {
            s.insert(0, "0");
        }
        return s.toString();
    }

    /**
     * 异或
     *
     * @param input1
     * @param intput2
     * @return
     */
    public static byte[] xor(byte[] input1, byte[] intput2) {
        byte[] output = new byte[input1.length];
        for (int i = 0; i < input1.length; i++) {
            output[i] = (byte) ((input1[i] + intput2[i]) % 2);
        }
        return output;
    }

    /**
     * 异或
     *
     * @param input1
     * @param intput2
     * @return
     */
    public static int[] xor(int[] input1, int[] intput2) {
        int[] output = new int[input1.length];
        for (int i = 0; i < input1.length; i++) {
            output[i] = ((input1[i] + intput2[i]) % 2);
        }
        return output;
    }
}
