/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.impl.security.AbstractPublicJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.PublicJwk;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.util.List;
import java.util.Set;

class DefaultEcPublicJwk
extends AbstractPublicJwk<ECPublicKey>
implements EcPublicJwk {
    static final String TYPE_VALUE = "EC";
    static final Parameter<String> CRV = Parameters.string("crv", "Curve");
    static final Parameter<BigInteger> X = (Parameter)Parameters.bigInt("x", "X Coordinate").build();
    static final Parameter<BigInteger> Y = (Parameter)Parameters.bigInt("y", "Y Coordinate").build();
    static final Set<Parameter<?>> PARAMS = Collections.concat(AbstractAsymmetricJwk.PARAMS, CRV, X, Y);
    static final List<Parameter<?>> THUMBPRINT_PARAMS = Collections.of(CRV, KTY, X, Y);

    DefaultEcPublicJwk(JwkContext<ECPublicKey> jwkContext) {
        super(jwkContext, THUMBPRINT_PARAMS);
    }

    static boolean equalsPublic(ParameterReadable parameterReadable, Object object) {
        return Parameters.equals(parameterReadable, object, CRV) && Parameters.equals(parameterReadable, object, X) && Parameters.equals(parameterReadable, object, Y);
    }

    @Override
    protected boolean equals(PublicJwk<?> publicJwk) {
        return publicJwk instanceof EcPublicJwk && DefaultEcPublicJwk.equalsPublic(this, publicJwk);
    }
}

