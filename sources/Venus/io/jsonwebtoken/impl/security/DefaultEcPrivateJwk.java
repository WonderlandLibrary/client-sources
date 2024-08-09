/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultEcPublicJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.EcPrivateJwk;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.PrivateJwk;
import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Set;

class DefaultEcPrivateJwk
extends AbstractPrivateJwk<ECPrivateKey, ECPublicKey, EcPublicJwk>
implements EcPrivateJwk {
    static final Parameter<BigInteger> D = Parameters.secretBigInt("d", "ECC Private Key");
    static final Set<Parameter<?>> PARAMS = Collections.concat(DefaultEcPublicJwk.PARAMS, D);

    DefaultEcPrivateJwk(JwkContext<ECPrivateKey> jwkContext, EcPublicJwk ecPublicJwk) {
        super(jwkContext, DefaultEcPublicJwk.THUMBPRINT_PARAMS, ecPublicJwk);
    }

    @Override
    protected boolean equals(PrivateJwk<?, ?, ?> privateJwk) {
        return privateJwk instanceof EcPrivateJwk && DefaultEcPublicJwk.equalsPublic(this, privateJwk) && Parameters.equals(this, privateJwk, D);
    }
}

