package me.ice.crypto.des.utils;

import org.junit.Test;

import java.util.Arrays;

public class NumberUtilsTest {

    @Test
    public void encodeToBinaryStr() {

        String superman = "superman";
        String chars = NumberUtils.encodeHexString(superman);
        System.out.println("chars = " + chars);
        System.out.println("Arrays.toString(\"superman\".getBytes()) = " + Arrays.toString(superman.getBytes()));
        NumberUtils.encodeToBinaryStr(superman);

        //chars = encodeBinaryString(superman);
        //System.out.println("'" + superman + "' to binary: " + chars);

    }
}