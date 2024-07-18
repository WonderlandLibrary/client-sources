package rip.vantage.commons.util.encryption;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

// Not as secure, but I doubt anyone will bother to reverse it
public class EncryptionUtilFake2 {
    private static final String KEY = "ふんぁふぃふにかぎいかじふざ";

    public static String encrypt(String valueToEncrypt) {
        byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = valueToEncrypt.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = new byte[valueBytes.length];
        for (int i = 0; i < valueBytes.length; i++) {
            encryptedBytes[i] = (byte) (valueBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedValue) {
        byte[] keyBytes = KEY.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedValue);
        byte[] decryptedBytes = new byte[encryptedBytes.length];
        for (int i = 0; i < encryptedBytes.length; i++) {
            decryptedBytes[i] = (byte) (encryptedBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
