package me.ice.crypto.des.impl;

public enum PaddingMode {
    /**
     * 末尾补零
     */
    ZeroPadding,
    /**
     * 无填充
     */
    NoPadding,
    PKCS5,
    PKCS7
}
