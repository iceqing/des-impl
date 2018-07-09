package me.ice.crypto.des;

import me.ice.crypto.des.impl.DESCBCImpl;
import org.junit.Test;

import java.util.Arrays;

public class DESCBCImplTest {

    @Test
    public void cbc(){
        String s1 = "12345678901234567";
        String s2 = "superman";
        // 初始向量
        String iv = "ABCDEFGH";
        byte[] ecb = DESCBCImpl.cbc(s1, s2, iv);
        System.out.println("Arrays.toString(ecb) = " + Arrays.toString(ecb));
        System.out.println("ecb.length = " + ecb.length);
    }
}