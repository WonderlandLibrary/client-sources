/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultRequest;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.SecureRequest;
import java.security.Key;
import java.security.Provider;
import java.security.SecureRandom;

public class DefaultSecureRequest<T, K extends Key>
extends DefaultRequest<T>
implements SecureRequest<T, K> {
    private final K KEY;

    public DefaultSecureRequest(T t, Provider provider, SecureRandom secureRandom, K k) {
        super(t, provider, secureRandom);
        this.KEY = (Key)Assert.notNull(k, "key cannot be null.");
    }

    @Override
    public K getKey() {
        return this.KEY;
    }
}

