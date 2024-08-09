/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.util.CryptException;

public class CryptManager {
    public static SecretKey createNewSharedKey() throws CryptException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        } catch (Exception exception) {
            throw new CryptException(exception);
        }
    }

    public static KeyPair generateKeyPair() throws CryptException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception exception) {
            throw new CryptException(exception);
        }
    }

    public static byte[] getServerIdHash(String string, PublicKey publicKey, SecretKey secretKey) throws CryptException {
        try {
            return CryptManager.func_244731_a(string.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
        } catch (Exception exception) {
            throw new CryptException(exception);
        }
    }

    private static byte[] func_244731_a(byte[] ... byArray) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        for (byte[] byArray2 : byArray) {
            messageDigest.update(byArray2);
        }
        return messageDigest.digest();
    }

    public static PublicKey decodePublicKey(byte[] byArray) throws CryptException {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byArray);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception exception) {
            throw new CryptException(exception);
        }
    }

    public static SecretKey decryptSharedKey(PrivateKey privateKey, byte[] byArray) throws CryptException {
        byte[] byArray2 = CryptManager.decryptData(privateKey, byArray);
        try {
            return new SecretKeySpec(byArray2, "AES");
        } catch (Exception exception) {
            throw new CryptException(exception);
        }
    }

    public static byte[] encryptData(Key key, byte[] byArray) throws CryptException {
        return CryptManager.cipherOperation(1, key, byArray);
    }

    public static byte[] decryptData(Key key, byte[] byArray) throws CryptException {
        return CryptManager.cipherOperation(2, key, byArray);
    }

    private static byte[] cipherOperation(int n, Key key, byte[] byArray) throws CryptException {
        try {
            return CryptManager.createTheCipherInstance(n, key.getAlgorithm(), key).doFinal(byArray);
        } catch (Exception exception) {
            throw new CryptException(exception);
        }
    }

    private static Cipher createTheCipherInstance(int n, String string, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(string);
        cipher.init(n, key);
        return cipher;
    }

    public static Cipher createNetCipherInstance(int n, Key key) throws CryptException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(n, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        } catch (Exception exception) {
            throw new CryptException(exception);
        }
    }
}

