/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import java.security.SecureRandom;

public final class Randoms {
    private static final SecureRandom DEFAULT_SECURE_RANDOM = new SecureRandom();

    private Randoms() {
    }

    public static SecureRandom secureRandom() {
        return DEFAULT_SECURE_RANDOM;
    }

    static {
        DEFAULT_SECURE_RANDOM.nextBytes(new byte[64]);
    }
}

