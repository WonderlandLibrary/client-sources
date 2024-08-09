/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.DefaultOctetPublicJwk;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.OctetJwkFactory;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.OctetPublicJwk;
import java.security.PublicKey;

public class OctetPublicJwkFactory
extends OctetJwkFactory<PublicKey, OctetPublicJwk<PublicKey>> {
    static final OctetPublicJwkFactory INSTANCE = new OctetPublicJwkFactory();

    OctetPublicJwkFactory() {
        super(PublicKey.class, DefaultOctetPublicJwk.PARAMS);
    }

    @Override
    protected OctetPublicJwk<PublicKey> createJwkFromKey(JwkContext<PublicKey> jwkContext) {
        PublicKey publicKey = Assert.notNull(jwkContext.getKey(), "PublicKey cannot be null.");
        EdwardsCurve edwardsCurve = EdwardsCurve.forKey(publicKey);
        byte[] byArray = edwardsCurve.getKeyMaterial(publicKey);
        Assert.notEmpty(byArray, "Edwards PublicKey 'x' value cannot be null or empty.");
        OctetPublicJwkFactory.put(jwkContext, DefaultOctetPublicJwk.CRV, edwardsCurve.getId());
        OctetPublicJwkFactory.put(jwkContext, DefaultOctetPublicJwk.X, byArray);
        return new DefaultOctetPublicJwk<PublicKey>(jwkContext);
    }

    @Override
    protected OctetPublicJwk<PublicKey> createJwkFromValues(JwkContext<PublicKey> jwkContext) {
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jwkContext);
        EdwardsCurve edwardsCurve = OctetPublicJwkFactory.getCurve(requiredParameterReader);
        byte[] byArray = requiredParameterReader.get(DefaultOctetPublicJwk.X);
        PublicKey publicKey = edwardsCurve.toPublicKey(byArray, jwkContext.getProvider());
        jwkContext.setKey(publicKey);
        return new DefaultOctetPublicJwk<PublicKey>(jwkContext);
    }

    @Override
    protected Jwk createJwkFromValues(JwkContext jwkContext) {
        return this.createJwkFromValues(jwkContext);
    }

    @Override
    protected Jwk createJwkFromKey(JwkContext jwkContext) {
        return this.createJwkFromKey(jwkContext);
    }
}

