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

    public static byte[] encryptNonce(byte[] publicKeyBytes, byte[] nonceBytes) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey key = RSA_FACTORY.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(1, key);
        return cipher.doFinal(nonceBytes);
    }

    static {
        try {
            RSA_FACTORY = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

