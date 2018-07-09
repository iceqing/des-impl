package me.ice.crypto.des.impl;

import me.ice.crypto.des.utils.Constants;
import me.ice.crypto.des.utils.NumberUtils;

/**
 * DES 实现加密
 * 在线DES加密 http://tool.chacuo.net/cryptdes
 *
 * @author ice
 */
public class DESCBCImpl {

    public static byte[] cbc(String plaintext, String key, String iv) {
        byte[] textResult = NumberUtils.encodeToBinary(plaintext, PaddingMode.NoPadding);
        int blockCount = (textResult.length + Constants.DES_BLOCK_SIZE - 1) / Constants.DES_BLOCK_SIZE;
        byte[] ret = new byte[blockCount * Constants.DES_BLOCK_SIZE];
        byte[] keyResult = NumberUtils.encodeToBinary(key, PaddingMode.NoPadding);
        byte[] ivResult = NumberUtils.encodeToBinary(iv, PaddingMode.NoPadding);
        if (textResult.length < Constants.DES_BLOCK_SIZE) {
            throw new ArrayIndexOutOfBoundsException("Array size wrong");
        } else {
            byte[] tempGroup = null;
            for (int i = 0; i < blockCount; i++) {
                // divide plaintext in every 64 bits blocks
                byte[] block = new byte[Constants.DES_BLOCK_SIZE];
                System.arraycopy(textResult, i * Constants.DES_BLOCK_SIZE, block, 0, Constants.DES_BLOCK_SIZE);
                // 此组的秘钥
                tempGroup = (tempGroup == null) ? ivResult : tempGroup;
                byte[] first = NumberUtils.xor(block, tempGroup);
                byte[] cipherText = DESImpl.des(first, keyResult);
                System.arraycopy(cipherText, 0, ret, Constants.DES_BLOCK_SIZE * i, Constants.DES_BLOCK_SIZE);
                tempGroup = cipherText;
            }
            return ret;
        }
    }
}