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