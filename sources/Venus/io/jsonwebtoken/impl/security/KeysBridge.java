/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.PasswordSpec;
import io.jsonwebtoken.impl.security.ProvidedPrivateKeyBuilder;
import io.jsonwebtoken.impl.security.ProvidedSecretKeyBuilder;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.KeySupplier;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.PrivateKeyBuilder;
import io.jsonwebtoken.security.SecretKeyBuilder;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import javax.crypto.SecretKey;

public final class KeysBridge {
    private static final String SUNPKCS11_GENERIC_SECRET_CLASSNAME = "sun.security.pkcs11.P11Key$P11SecretKey";
    private static final String SUNPKCS11_GENERIC_SECRET_ALGNAME = "Generic Secret";

    private KeysBridge() {
    }

    public static Password password(char[] cArray) {
        return new PasswordSpec(cArray);
    }

    public static SecretKeyBuilder builder(SecretKey secretKey) {
        return new ProvidedSecretKeyBuilder(secretKey);
    }

    public static PrivateKeyBuilder builder(PrivateKey privateKey) {
        return new ProvidedPrivateKeyBuilder(privateKey);
    }

    public static <K extends Key> K root(K k) {
        return k instanceof KeySupplier ? KeysBridge.root((KeySupplier)((Object)k)) : k;
    }

    public static <K extends Key> K root(KeySupplier<K> keySupplier) {
        Assert.notNull(keySupplier, "KeySupplier canot be null.");
        return (K)((Key)Assert.notNull(KeysBridge.root(keySupplier.getKey()), "KeySupplier key cannot be null."));
    }

    public static String findAlgorithm(Key key) {
        return key != null ? Strings.clean(key.getAlgorithm()) : null;
    }

    public static byte[] findEncoded(Key key) {
        Assert.notNull(key, "Key cannot be null.");
        byte[] byArray = null;
        try {
            byArray = key.getEncoded();
        } catch (Throwable throwable) {
            // empty catch block
        }
        return byArray;
    }

    public static boolean isSunPkcs11GenericSecret(Key key) {
        return key instanceof SecretKey && key.getClass().getName().equals(SUNPKCS11_GENERIC_SECRET_CLASSNAME) && SUNPKCS11_GENERIC_SECRET_ALGNAME.equals(key.getAlgorithm());
    }

    public static int findBitLength(Key key) {
        int n = -1;
        if (key instanceof SecretKey) {
            byte[] byArray;
            SecretKey secretKey = (SecretKey)key;
            if ("RAW".equals(secretKey.getFormat()) && !Bytes.isEmpty(byArray = KeysBridge.findEncoded(secretKey))) {
                n = (int)Bytes.bitLength(byArray);
                Bytes.clear(byArray);
            }
        } else if (key instanceof RSAKey) {
            RSAKey rSAKey = (RSAKey)((Object)key);
            n = rSAKey.getModulus().bitLength();
        } else if (key instanceof ECKey) {
            ECKey eCKey = (ECKey)((Object)key);
            n = eCKey.getParams().getOrder().bitLength();
        } else {
            EdwardsCurve edwardsCurve = EdwardsCurve.findByKey(key);
            if (edwardsCurve != null) {
                n = edwardsCurve.getKeyBitLength();
            }
        }
        return n;
    }

    public static byte[] getEncoded(Key key) {
        byte[] byArray;
        Assert.notNull(key, "Key cannot be null.");
        try {
            byArray = key.getEncoded();
        } catch (Throwable throwable) {
            String string = "Cannot obtain required encoded bytes from key [" + KeysBridge.toString(key) + "]: " + throwable.getMessage();
            throw new InvalidKeyException(string, throwable);
        }
        if (Bytes.isEmpty(byArray)) {
            String string = "Missing required encoded bytes for key [" + KeysBridge.toString(key) + "].";
            throw new InvalidKeyException(string);
        }
        return byArray;
    }

    public static String toString(Key key) {
        if (key == null) {
            return "null";
        }
        if (key instanceof PublicKey) {
            return key.toString();
        }
        return "class: " + key.getClass().getName() + ", algorithm: " + key.getAlgorithm() + ", format: " + key.getFormat();
    }
}

