/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;

public final class KeyPairs {
    private KeyPairs() {
    }

    private static String familyPrefix(Class<?> clazz) {
        if (RSAKey.class.isAssignableFrom(clazz)) {
            return "RSA ";
        }
        if (ECKey.class.isAssignableFrom(clazz)) {
            return "EC ";
        }
        return "";
    }

    public static <K> K getKey(KeyPair keyPair, Class<K> clazz) {
        Assert.notNull(keyPair, "KeyPair cannot be null.");
        String string = KeyPairs.familyPrefix(clazz) + "KeyPair ";
        boolean bl = PrivateKey.class.isAssignableFrom(clazz);
        Key key = bl ? keyPair.getPrivate() : keyPair.getPublic();
        return KeyPairs.assertKey(key, clazz, string);
    }

    public static <K> K assertKey(Key key, Class<K> clazz, String string) {
        String string2;
        Assert.notNull(key, "Key argument cannot be null.");
        Assert.notNull(clazz, "Class argument cannot be null.");
        String string3 = string2 = key instanceof PrivateKey ? "private" : "public";
        if (!clazz.isInstance(key)) {
            String string4 = string + string2 + " key must be an instance of " + clazz.getName() + ". Type found: " + key.getClass().getName();
            throw new IllegalArgumentException(string4);
        }
        return clazz.cast(key);
    }
}

