package me.superskidder.lune.utils.math;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: QianXia
 * @description: MD5 encode
 * @create: 2020/12/26-19:20
 */
public class MD5Utils {
    public static String encode(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new BigInteger(1, secretBytes).toString(16);
    }
}
