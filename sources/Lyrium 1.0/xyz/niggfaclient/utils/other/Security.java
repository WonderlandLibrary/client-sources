// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

import java.util.Base64;
import java.security.Key;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;

public class Security
{
    private static SecretKeySpec keySpec;
    
    public static void genKeyAES(final String input) throws NoSuchAlgorithmException {
        byte[] key = input.getBytes(StandardCharsets.UTF_8);
        final MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        Security.keySpec = new SecretKeySpec(key, "AES");
    }
    
    public static String encryptAES(final String input, final String key) {
        try {
            genKeyAES(key);
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, Security.keySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
            return null;
        }
    }
    
    public static String hashMD5(final String input) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            final StringBuilder hexString = new StringBuilder();
            final byte[] digest;
            final byte[] byteData = digest = md.digest();
            for (final byte aByteData : digest) {
                final String hex = Integer.toHexString(0xFF & aByteData);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
