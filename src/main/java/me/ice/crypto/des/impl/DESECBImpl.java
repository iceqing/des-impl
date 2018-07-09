package me.ice.crypto.des.impl;

import me.ice.crypto.des.utils.Constants;
import me.ice.crypto.des.utils.NumberUtils;


/**
 * DES 实现加密
 * 在线DES加密 http://tool.chacuo.net/cryptdes
 *
 * @author ice
 */
public class DESECBImpl {

    /**
     * DES/ECB加密模式
     *
     * @param plaintext
     * @param key
     * @return
     */
    public static byte[] ecb(String plaintext, String key) {
        // 如果key 大于 64 那么64位以后的数据将作为无效数据
        if (key.length() > Constants.DES_BLOCK_SIZE) {
            key = key.substring(0, Constants.DES_BLOCK_SIZE);
        }
        byte[] textResult = NumberUtils.encodeToBinary(plaintext, PaddingMode.NoPadding);
        // 每一个块的大小
        int blockCount = (textResult.length + 1) / Constants.DES_BLOCK_SIZE;
        if (textResult.length < Constants.DES_BLOCK_SIZE) {
            throw new ArrayIndexOutOfBoundsException("Array size wrong, plaintext length size low than 64");
        } else {
            // divide plaintext in every 64 bits blocks
            byte[] ret = new byte[blockCount * Constants.DES_BLOCK_SIZE];
            // 分批处理每一个块
            for (int i = 0; i < blockCount; i++) {
                byte[] block = new byte[Constants.DES_BLOCK_SIZE];
                System.arraycopy(textResult, i * Constants.DES_BLOCK_SIZE, block, 0, Constants.DES_BLOCK_SIZE);
                byte[] keyResult = NumberUtils.encodeToBinary(key, PaddingMode.NoPadding);
                byte[] cipherText = DESImpl.des(block, keyResult);
                System.arraycopy(cipherText, 0, ret, Constants.DES_BLOCK_SIZE * i, Constants.DES_BLOCK_SIZE);
            }
            return ret;
        }
    }
}