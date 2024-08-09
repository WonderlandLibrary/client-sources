/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractAsymmetricJwk;
import io.jsonwebtoken.impl.security.AbstractPublicJwk;
import io.jsonwebtoken.impl.security.DefaultEcPublicJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.OctetPublicJwk;
import io.jsonwebtoken.security.PublicJwk;
import java.security.PublicKey;
import java.util.List;
import java.util.Set;

public class DefaultOctetPublicJwk<T extends PublicKey>
extends AbstractPublicJwk<T>
implements OctetPublicJwk<T> {
    static final String TYPE_VALUE = "OKP";
    static final Parameter<String> CRV = DefaultEcPublicJwk.CRV;
    static final Parameter<byte[]> X = (Parameter)Parameters.bytes("x", "The public key").build();
    static final Set<Parameter<?>> PARAMS = Collections.concat(AbstractAsymmetricJwk.PARAMS, CRV, X);
    static final List<Parameter<?>> THUMBPRINT_PARAMS = Collections.of(CRV, KTY, X);

    DefaultOctetPublicJwk(JwkContext<T> jwkContext) {
        super(jwkContext, THUMBPRINT_PARAMS);
    }

    static boolean equalsPublic(ParameterReadable parameterReadable, Object object) {
        return Parameters.equals(parameterReadable, object, CRV) && Parameters.equals(parameterReadable, object, X);
    }

    @Override
    protected boolean equals(PublicJwk<?> publicJwk) {
        return publicJwk instanceof OctetPublicJwk && DefaultOctetPublicJwk.equalsPublic(this, publicJwk);
    }
}

