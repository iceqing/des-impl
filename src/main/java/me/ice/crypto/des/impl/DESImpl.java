/*
 * Copyright (C) 2018 Ice
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package me.ice.crypto.des.impl;

import me.ice.crypto.des.utils.Constants;
import me.ice.crypto.des.utils.NumberUtils;

/**
 * java 实现 DES 加密
 *
 * @author ice
 */
public class DESImpl {

    /**
     * store left and right half of key
     */
    private static byte[] C = new byte[28];
    private static byte[] D = new byte[28];
    /**
     * store left and right half of plaintext
     */
    private static byte[] L = new byte[Constants.DES_BLOCK_SIZE / 2];
    private static byte[] R = new byte[Constants.DES_BLOCK_SIZE / 2];


    public static byte[] des(byte[] plaintext, byte[] key) {
        if (plaintext.length == Constants.DES_BLOCK_SIZE && key.length == Constants.DES_BLOCK_SIZE) {
            // 1、IP置换 initial an array to store the first permutation of plaintext
            byte[] prePlaintext = new byte[plaintext.length];
            for (int i = 0; i < plaintext.length; i++) {
                prePlaintext[i] = plaintext[Constants.IP[i] - 1];
            }

            // 2、秘钥初始置换 reduce key from 64 to 56 use P_key table
            for (int i = 0; i < 28; i++) {
                C[i] = key[Constants.P_KEY[i] - 1];
            }
            for (int i = 28; i < 56; i++) {
                D[i - 28] = key[Constants.P_KEY[i] - 1];
            }
            // divide plaintext in left and right array
            System.arraycopy(prePlaintext, 0, L, 0, 32);
            System.arraycopy(prePlaintext, 32, R, 0, 32);

            //F函数， 进行16次计算， f_function
            for (byte i = 0; i < 16; i++) {
                // 3、右边32位扩展置换 expand from 32 to 48 bits
                byte[] expand = new byte[48];
                for (int j = 0; j < 48; j++) {
                    expand[j] = R[Constants.SEL_TABLE[j] - 1];
                }
                // 4、子秘钥压缩  56->48
                byte[] keyCompressed = compressKeySize(i);
                // 5、扩展的文本与压缩的秘钥，48位进行与操作
                byte[] sBoxInput = NumberUtils.xor(expand, keyCompressed);
                // 6、S、盒
                byte[] cal = sBox(sBoxInput);
                // 7、P盒置换
                byte[] result = pShift(cal);
                byte[] newR = NumberUtils.xor(L, result);
                L = R;
                R = newR;
            }

            byte[] plaintextFinal = new byte[Constants.DES_BLOCK_SIZE];
            System.arraycopy(R, 0, plaintextFinal, 0, R.length);
            System.arraycopy(L, 0, plaintextFinal, 32, L.length);
            byte[] cipherText = new byte[Constants.DES_BLOCK_SIZE];
            // 8、IP-1末置换
            for (int i = 0; i < plaintextFinal.length; i++) {
                cipherText[i] = plaintextFinal[Constants.FP[i] - 1];
            }
            return cipherText;
        } else {
            throw new ArrayIndexOutOfBoundsException("array size wrong");
        }
    }

    private static byte[] compressKeySize(byte iter) {
        //C and D left shift for 16 rounds
        //cd[] combine from C0D0 ~ C15D15
        byte[] cd = new byte[56];
        byte i = Constants.L_SHIFT[iter];
        byte[] temC = leftShift(C, i);
        byte[] temD = leftShift(D, i);
        //CD store the combine of temC and temD
        System.arraycopy(temC, 0, cd, 0, temC.length);
        System.arraycopy(temD, 0, cd, 28, temD.length);
        //use Second_p table to reduce to 48bits
        byte[] temK = new byte[48];
        for (int j = 0; j < temK.length; j++) {
            temK[j] = cd[Constants.SECOND_P[j] - 1];
        }
        C = temC;
        D = temD;
        return temK;
    }

    private static byte[] sBox(byte[] input) {
        byte[] output = new byte[32];
        // 共计8个S盒
        for (int i = 0; i < 8; i++) {
            int[] row = new int[2];
            int[] column = new int[4];
            // the first and the last of every 6 bits block are the Row locations
            row[0] = input[6 * i];
            row[1] = input[(6 * i) + 5];
            String s1 = row[0] + "" + row[1];
            // the 2nd to the 4th of every 6 bits block are the Column locations
            column[0] = input[(6 * i) + 1];
            column[1] = input[(6 * i) + 2];
            column[2] = input[(6 * i) + 3];
            column[3] = input[(6 * i) + 4];
            String s2 = column[0] + "" + column[1] + "" + column[2] + "" + column[3];

            int x = Integer.parseInt(s1, 2);
            int y = Integer.parseInt(s2, 2);
            int z = Constants.S[i][(x * 16) + y];
            String b = NumberUtils.toBinaryStr(z);
            for (int j = 0; j < 4; j++) {
                output[(i * 4) + j] = Byte.parseByte(b.charAt(j) + "");
            }
        }
        return output;
    }

    private static byte[] pShift(byte[] output) {
        // P盒置换
        byte[] result = new byte[32];
        for (int i = 0; i < Constants.P.length; i++) {
            result[i] = output[Constants.P[i] - 1];
        }
        return result;
    }

    private static byte[] leftShift(byte[] input, byte n) {
        byte[] temp = new byte[input.length];
        if (n == 1) {
            temp[input.length - 1] = input[0];
            for (int i = 0; i < input.length - 1; i++) {
                temp[i] = input[i + 1];
            }
        } else {
            temp[input.length - 2] = input[0];
            temp[input.length - 1] = input[1];
            for (int i = 0; i < input.length - 2; i++) {
                temp[i] = input[i + 2];
            }
        }
        return temp;
    }
}