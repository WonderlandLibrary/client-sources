/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.PublicJwk;
import java.security.PublicKey;
import java.util.List;

abstract class AbstractPublicJwk<K extends PublicKey>
extends AbstractAsymmetricJwk<K>
implements PublicJwk<K> {
    AbstractPublicJwk(JwkContext<K> jwkContext, List<Parameter<?>> list) {
        super(jwkContext, list);
    }

    @Override
    protected final boolean equals(Jwk<?> jwk) {
        return jwk instanceof PublicJwk && this.equals((PublicJwk)jwk);
    }

    @Override
    protected abstract boolean equals(PublicJwk<?> var1);
}

