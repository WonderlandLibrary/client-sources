/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public final class CipherUtil {
    private static final KeyFactory RSA_FACTORY;

    public static byte[] encryptNonce(byte[] byArray, byte[] byArray2) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byArray);
        PublicKey publicKey = RSA_FACTORY.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(1, publicKey);
        return cipher.doFinal(byArray2);
    }

    static {
        try {
            RSA_FACTORY = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }
}

