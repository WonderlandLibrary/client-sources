/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.security.AbstractFamilyJwkFactory;
import io.jsonwebtoken.impl.security.DefaultOctetPublicJwk;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.security.Key;
import java.util.Set;

public abstract class OctetJwkFactory<K extends Key, J extends Jwk<K>>
extends AbstractFamilyJwkFactory<K, J> {
    OctetJwkFactory(Class<K> clazz, Set<Parameter<?>> set) {
        super("OKP", clazz, set);
    }

    @Override
    public boolean supports(Key key) {
        return super.supports(key) && EdwardsCurve.isEdwards(key);
    }

    protected static EdwardsCurve getCurve(ParameterReadable parameterReadable) throws UnsupportedKeyException {
        Parameter<String> parameter = DefaultOctetPublicJwk.CRV;
        String string = parameterReadable.get(parameter);
        EdwardsCurve edwardsCurve = EdwardsCurve.findById(string);
        if (edwardsCurve == null) {
            String string2 = "Unrecognized OKP JWK " + parameter + " value '" + string + "'";
            throw new UnsupportedKeyException(string2);
        }
        return edwardsCurve;
    }

    @Override
    public boolean supports(JwkContext jwkContext) {
        return super.supports(jwkContext);
    }

    @Override
    public JwkContext newContext(JwkContext jwkContext, Key key) {
        return super.newContext(jwkContext, key);
    }

    @Override
    public String getId() {
        return super.getId();
    }
}

