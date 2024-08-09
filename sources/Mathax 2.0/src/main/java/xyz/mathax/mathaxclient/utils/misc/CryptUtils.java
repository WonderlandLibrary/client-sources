package xyz.mathax.mathaxclient.utils.misc;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptUtils {
    public static SecretKey psk2sk(String psk, int salt) throws Exception {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(new PBEKeySpec(psk.toCharArray(), String.valueOf(salt).getBytes(), 65536, 256)).getEncoded(), "AES");
    }

    private static IvParameterSpec int2iv(int i) {
        byte[] iv = new byte[16];
        for (int j = 0; j < 16; j++) {
            iv[j] = (byte) (i >> (j * 8));
        }

        return new IvParameterSpec(iv);
    }

    public static String encryptAES(String message, SecretKey secret, int iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/OFB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret, int2iv(iv));
        return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
    }

    public static String decryptAES(String message, SecretKey secret, int iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/OFB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, int2iv(iv));
        return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
    }

    public static String encryptRSA(String message, String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, string2PublicKey(publicKey));
        return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
    }

    private static PublicKey string2PublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }
}