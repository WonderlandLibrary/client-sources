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
import io.jsonwebtoken.security.PublicJwk;
import io.jsonwebtoken.security.RsaPublicJwk;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Set;

class DefaultRsaPublicJwk
extends AbstractPublicJwk<RSAPublicKey>
implements RsaPublicJwk {
    static final String TYPE_VALUE = "RSA";
    static final Parameter<BigInteger> MODULUS = (Parameter)Parameters.bigInt("n", "Modulus").build();
    static final Parameter<BigInteger> PUBLIC_EXPONENT = (Parameter)Parameters.bigInt("e", "Public Exponent").build();
    static final Set<Parameter<?>> PARAMS = Collections.concat(AbstractAsymmetricJwk.PARAMS, MODULUS, PUBLIC_EXPONENT);
    static final List<Parameter<?>> THUMBPRINT_PARAMS = Collections.of(PUBLIC_EXPONENT, KTY, MODULUS);

    DefaultRsaPublicJwk(JwkContext<RSAPublicKey> jwkContext) {
        super(jwkContext, THUMBPRINT_PARAMS);
    }

    static boolean equalsPublic(ParameterReadable parameterReadable, Object object) {
        return Parameters.equals(parameterReadable, object, MODULUS) && Parameters.equals(parameterReadable, object, PUBLIC_EXPONENT);
    }

    @Override
    protected boolean equals(PublicJwk<?> publicJwk) {
        return publicJwk instanceof RsaPublicJwk && DefaultRsaPublicJwk.equalsPublic(this, publicJwk);
    }
}

