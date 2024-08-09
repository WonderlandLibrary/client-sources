/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.Password;
import io.jsonwebtoken.security.PrivateKeyBuilder;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import io.jsonwebtoken.security.WeakKeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class Keys {
    private static final String BRIDGE_CLASSNAME = "io.jsonwebtoken.impl.security.KeysBridge";
    private static final Class<?> BRIDGE_CLASS = Classes.forName("io.jsonwebtoken.impl.security.KeysBridge");
    private static final Class<?>[] FOR_PASSWORD_ARG_TYPES = new Class[]{char[].class};
    private static final Class<?>[] SECRET_BUILDER_ARG_TYPES = new Class[]{SecretKey.class};
    private static final Class<?>[] PRIVATE_BUILDER_ARG_TYPES = new Class[]{PrivateKey.class};

    private static <T> T invokeStatic(String string, Class<?>[] classArray, Object ... objectArray) {
        return Classes.invokeStatic(BRIDGE_CLASS, string, classArray, objectArray);
    }

    private Keys() {
    }

    public static SecretKey hmacShaKeyFor(byte[] byArray) throws WeakKeyException {
        if (byArray == null) {
            throw new InvalidKeyException("SecretKey byte array cannot be null.");
        }
        int n = byArray.length * 8;
        if (n >= 512) {
            return new SecretKeySpec(byArray, "HmacSHA512");
        }
        if (n >= 384) {
            return new SecretKeySpec(byArray, "HmacSHA384");
        }
        if (n >= 256) {
            return new SecretKeySpec(byArray, "HmacSHA256");
        }
        String string = "The specified key byte array is " + n + " bits which " + "is not secure enough for any JWT HMAC-SHA algorithm.  The JWT " + "JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms MUST have a " + "size >= 256 bits (the key size must be greater than or equal to the hash " + "output size).  Consider using the Jwts.SIG.HS256.key() builder (or HS384.key() " + "or HS512.key()) to create a key guaranteed to be secure enough for your preferred HMAC-SHA " + "algorithm.  See https://tools.ietf.org/html/rfc7518#section-3.2 for more information.";
        throw new WeakKeyException(string);
    }

    @Deprecated
    public static SecretKey secretKeyFor(SignatureAlgorithm signatureAlgorithm) throws IllegalArgumentException {
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        SecureDigestAlgorithm secureDigestAlgorithm = (SecureDigestAlgorithm)Jwts.SIG.get().get(signatureAlgorithm.name());
        if (!(secureDigestAlgorithm instanceof MacAlgorithm)) {
            String string = "The " + signatureAlgorithm.name() + " algorithm does not support shared secret keys.";
            throw new IllegalArgumentException(string);
        }
        return (SecretKey)((SecretKeyBuilder)((MacAlgorithm)secureDigestAlgorithm).key()).build();
    }

    @Deprecated
    public static KeyPair keyPairFor(SignatureAlgorithm signatureAlgorithm) throws IllegalArgumentException {
        Assert.notNull(signatureAlgorithm, "SignatureAlgorithm cannot be null.");
        SecureDigestAlgorithm secureDigestAlgorithm = (SecureDigestAlgorithm)Jwts.SIG.get().get(signatureAlgorithm.name());
        if (!(secureDigestAlgorithm instanceof io.jsonwebtoken.security.SignatureAlgorithm)) {
            String string = "The " + signatureAlgorithm.name() + " algorithm does not support Key Pairs.";
            throw new IllegalArgumentException(string);
        }
        io.jsonwebtoken.security.SignatureAlgorithm signatureAlgorithm2 = (io.jsonwebtoken.security.SignatureAlgorithm)secureDigestAlgorithm;
        return (KeyPair)signatureAlgorithm2.keyPair().build();
    }

    public static Password password(char[] cArray) {
        return (Password)Keys.invokeStatic("password", FOR_PASSWORD_ARG_TYPES, new Object[]{cArray});
    }

    public static SecretKeyBuilder builder(SecretKey secretKey) {
        Assert.notNull(secretKey, "SecretKey cannot be null.");
        return (SecretKeyBuilder)Keys.invokeStatic("builder", SECRET_BUILDER_ARG_TYPES, secretKey);
    }

    public static PrivateKeyBuilder builder(PrivateKey privateKey) {
        Assert.notNull(privateKey, "PrivateKey cannot be null.");
        return (PrivateKeyBuilder)Keys.invokeStatic("builder", PRIVATE_BUILDER_ARG_TYPES, privateKey);
    }
}

