/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultOctetPublicJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.KeyPair;
import io.jsonwebtoken.security.OctetPrivateJwk;
import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.PrivateJwk;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Set;

public class DefaultOctetPrivateJwk<T extends PrivateKey, P extends PublicKey>
extends AbstractPrivateJwk<T, P, OctetPublicJwk<P>>
implements OctetPrivateJwk<T, P> {
    static final Parameter<byte[]> D = (Parameter)Parameters.bytes("d", "The private key").setSecret(true).build();
    static final Set<Parameter<?>> PARAMS = Collections.concat(DefaultOctetPublicJwk.PARAMS, D);

    DefaultOctetPrivateJwk(JwkContext<T> jwkContext, OctetPublicJwk<P> octetPublicJwk) {
        super(jwkContext, DefaultOctetPublicJwk.THUMBPRINT_PARAMS, octetPublicJwk);
    }

    @Override
    protected boolean equals(PrivateJwk<?, ?, ?> privateJwk) {
        return privateJwk instanceof OctetPrivateJwk && DefaultOctetPublicJwk.equalsPublic(this, privateJwk) && Parameters.equals(this, privateJwk, D);
    }

    @Override
    public KeyPair toKeyPair() {
        return super.toKeyPair();
    }
}

