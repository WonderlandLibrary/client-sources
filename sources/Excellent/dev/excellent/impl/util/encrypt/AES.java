package dev.excellent.impl.util.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class AES {
    private static final String ALGORITHM = "AES";
    private static final String ALGORITHM_STR = "AES/CBC/PKCS5Padding";

    private final SecretKeySpec key;
    private final IvParameterSpec ivParameterSpec;
    public String outKey;

    public AES(int hexKey, String iv) {
        outKey = getRandomString(hexKey);
        key = new SecretKeySpec(outKey.getBytes(), ALGORITHM);
        ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    }

    public String encryptData(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        return BytetoBase64(cipher.doFinal(data.getBytes()));
    }

    public String decryptData(String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        return new String(cipher.doFinal(Base64toByte(base64Data)));
    }

    public byte[] Base64toByte(String a) {
        Base64 base64 = new Base64();
        return base64.decode(a);
    }

    public String BytetoBase64(byte[] a) {
        Base64 base64 = new Base64();
        return base64.encodeToString(a);
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}