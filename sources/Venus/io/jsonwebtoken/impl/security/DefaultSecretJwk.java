/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.SecretJwk;
import java.util.List;
import java.util.Set;
import javax.crypto.SecretKey;

class DefaultSecretJwk
extends AbstractJwk<SecretKey>
implements SecretJwk {
    static final String TYPE_VALUE = "oct";
    static final Parameter<byte[]> K = (Parameter)Parameters.bytes("k", "Key Value").setSecret(true).build();
    static final Set<Parameter<?>> PARAMS = Collections.concat(AbstractJwk.PARAMS, K);
    static final List<Parameter<?>> THUMBPRINT_PARAMS = Collections.of(K, KTY);

    DefaultSecretJwk(JwkContext<SecretKey> jwkContext) {
        super(jwkContext, THUMBPRINT_PARAMS);
    }

    @Override
    protected boolean equals(Jwk<?> jwk) {
        return jwk instanceof SecretJwk && Parameters.equals(this, jwk, K);
    }
}

