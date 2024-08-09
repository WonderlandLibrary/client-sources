/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.Randoms;
import io.jsonwebtoken.security.SecurityBuilder;
import java.security.Provider;
import java.security.SecureRandom;

abstract class AbstractSecurityBuilder<T, B extends SecurityBuilder<T, B>>
implements SecurityBuilder<T, B> {
    protected Provider provider;
    protected SecureRandom random;

    AbstractSecurityBuilder() {
    }

    protected final B self() {
        return (B)this;
    }

    @Override
    public B provider(Provider provider) {
        this.provider = provider;
        return this.self();
    }

    @Override
    public B random(SecureRandom secureRandom) {
        this.random = secureRandom != null ? secureRandom : Randoms.secureRandom();
        return this.self();
    }
}

