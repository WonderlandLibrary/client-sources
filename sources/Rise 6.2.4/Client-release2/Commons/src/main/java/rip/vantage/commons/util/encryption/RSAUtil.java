package rip.vantage.commons.util.encryption;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static final String CIPHER_INSTANCE = "RSA/ECB/PKCS1Padding";

    public static KeyPair generateRSAKeyPair() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    public static String encrypt(String input, String pKey) {
        try {
            byte[] payload = input.getBytes();
            byte[] publicKey = pKey.getBytes();
            Key generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.ENCRYPT_MODE, generatePublic);
            byte[] byteOutput = Base64.getEncoder().encode(cipher.doFinal(payload));
            return new String(byteOutput);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String input, String pKey) {
        try {
            byte[] payload = Base64.getDecoder().decode(input);
            byte[] privateKey = pKey.getBytes();
            Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE);
            cipher.init(Cipher.DECRYPT_MODE, generatePrivate);
            byte[] byteOutput = cipher.doFinal(payload);
            return new String(byteOutput, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}