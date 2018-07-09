package me.ice.crypto.des.demo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class DESTest {

    @Test
    public void encrypt() {
        // 待加密内容
        String str = "superman";
        // 密码，长度要是8的倍数
        String password = "superman122";
        byte[] result = DES.encrypt(str.getBytes(), password);
        System.out.println("加密后：" + new String(result));
        System.out.println("加密后：" + Hex.encodeHexString(result));
        System.out.println("加密后：" + Base64.encodeBase64String(result));

        // 直接将如上内容解密
        try {
            byte[] decryResult = DES.decrypt(result, password);
            System.out.println("解密后：" + new String(decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}