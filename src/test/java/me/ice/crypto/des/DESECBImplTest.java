package me.ice.crypto.des;

import me.ice.crypto.des.impl.DESECBImpl;
import org.junit.Test;

import java.util.Arrays;

public class DESECBImplTest {

    @Test
    public void ECB() {
            String s1 = "1";
            String s2 = "superman";
            byte[] ecb = DESECBImpl.ecb(s1, s2);
            System.out.println("Arrays.toString(ecb) = " + Arrays.toString(ecb));
            System.out.println("ecb.length = " + ecb.length);
    }
}