/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultMessage;
import io.jsonwebtoken.security.Request;
import java.security.Provider;
import java.security.SecureRandom;

public class DefaultRequest<T>
extends DefaultMessage<T>
implements Request<T> {
    private final Provider provider;
    private final SecureRandom secureRandom;

    public DefaultRequest(T t, Provider provider, SecureRandom secureRandom) {
        super(t);
        this.provider = provider;
        this.secureRandom = secureRandom;
    }

    @Override
    public Provider getProvider() {
        return this.provider;
    }

    @Override
    public SecureRandom getSecureRandom() {
        return this.secureRandom;
    }

    @Override
    public Object getPayload() {
        return super.getPayload();
    }
}

