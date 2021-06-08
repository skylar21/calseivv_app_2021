package com.calseivv.project.util;

public class PasswordEncryptorUtil {

    private static final String SECRET_KEY = "P@ssw0rd123";
    private static final String IV = "bca9f2f7b3384d865d88994731a298c8";
    private static final String SALT = "917a4a0b5847f7ff8f9f666bb1410316";
    private static final int KEY_SIZE = 128;
    private static final int ITERATION_COUNT = 500;

    public static String encrypt(String plainText) {
        AESUtil aesUtil = new AESUtil(KEY_SIZE, ITERATION_COUNT);
        return aesUtil.encrypt(SALT, IV, SECRET_KEY, plainText);
    }

    public static String decrypt(String encryptedText) {
        AESUtil aesUtil = new AESUtil(KEY_SIZE, ITERATION_COUNT);
        return aesUtil.decrypt(SALT, IV, SECRET_KEY, encryptedText);
    }

}
