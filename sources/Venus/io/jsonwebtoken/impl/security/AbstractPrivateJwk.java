/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.impl.security.DefaultKeyPair;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.KeyPair;
import io.jsonwebtoken.security.PrivateJwk;
import io.jsonwebtoken.security.PublicJwk;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

abstract class AbstractPrivateJwk<K extends PrivateKey, L extends PublicKey, M extends PublicJwk<L>>
extends AbstractAsymmetricJwk<K>
implements PrivateJwk<K, L, M> {
    private final M publicJwk;
    private final KeyPair<L, K> keyPair;

    AbstractPrivateJwk(JwkContext<K> jwkContext, List<Parameter<?>> list, M m) {
        super(jwkContext, list);
        this.publicJwk = (PublicJwk)Assert.notNull(m, "PublicJwk instance cannot be null.");
        PublicKey publicKey = (PublicKey)Assert.notNull(m.toKey(), "PublicJwk key instance cannot be null.");
        this.context.setPublicKey(publicKey);
        this.keyPair = new DefaultKeyPair<PublicKey, PrivateKey>(publicKey, (PrivateKey)this.toKey());
    }

    @Override
    public M toPublicJwk() {
        return this.publicJwk;
    }

    @Override
    public KeyPair<L, K> toKeyPair() {
        return this.keyPair;
    }

    @Override
    protected final boolean equals(Jwk<?> jwk) {
        return jwk instanceof PrivateJwk && this.equals((PrivateJwk)jwk);
    }

    protected abstract boolean equals(PrivateJwk<?, ?, ?> var1);
}

