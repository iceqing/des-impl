package me.ice.crypto.des;

import me.ice.crypto.des.impl.DESImpl;
import org.junit.Test;

import java.util.Arrays;

public class DESImplTest {

    @Test
    public void des() {
        byte[] a1 = {
                0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1,
                0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1,
                0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1,
                0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0
        };
        byte[] a2 = {
                0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1,
                0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1,
                0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1,
                0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0
        };
        System.out.println("Arrays.toString(DES()) = " + Arrays.toString(DESImpl.des(a1, a2)));
    }
}