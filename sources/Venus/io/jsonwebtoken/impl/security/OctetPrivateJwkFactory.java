/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.DefaultJwkContext;
import io.jsonwebtoken.impl.security.DefaultOctetPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultOctetPublicJwk;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.OctetJwkFactory;
import io.jsonwebtoken.impl.security.OctetPublicJwkFactory;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.OctetPrivateJwk;
import io.jsonwebtoken.security.OctetPublicJwk;
import java.security.PrivateKey;
import java.security.PublicKey;

public class OctetPrivateJwkFactory
extends OctetJwkFactory<PrivateKey, OctetPrivateJwk<PrivateKey, PublicKey>> {
    public OctetPrivateJwkFactory() {
        super(PrivateKey.class, DefaultOctetPrivateJwk.PARAMS);
    }

    @Override
    protected boolean supportsKeyValues(JwkContext<?> jwkContext) {
        return super.supportsKeyValues(jwkContext) && jwkContext.containsKey(DefaultOctetPrivateJwk.D.getId());
    }

    @Override
    protected OctetPrivateJwk<PrivateKey, PublicKey> createJwkFromKey(JwkContext<PrivateKey> jwkContext) {
        PrivateKey privateKey = Assert.notNull(jwkContext.getKey(), "PrivateKey cannot be null.");
        EdwardsCurve edwardsCurve = EdwardsCurve.forKey(privateKey);
        PublicKey publicKey = jwkContext.getPublicKey();
        if (publicKey != null) {
            if (!edwardsCurve.equals(EdwardsCurve.forKey(publicKey))) {
                String string = "Specified Edwards Curve PublicKey does not match the specified PrivateKey's curve.";
                throw new InvalidKeyException(string);
            }
        } else {
            publicKey = EdwardsCurve.derivePublic(privateKey);
        }
        boolean bl = !Strings.hasText(jwkContext.getId()) && jwkContext.getIdThumbprintAlgorithm() != null;
        JwkContext jwkContext2 = OctetPublicJwkFactory.INSTANCE.newContext((JwkContext)jwkContext, publicKey);
        OctetPublicJwk octetPublicJwk = (OctetPublicJwk)OctetPublicJwkFactory.INSTANCE.createJwk(jwkContext2);
        jwkContext.putAll(octetPublicJwk);
        if (bl) {
            jwkContext.setId(octetPublicJwk.getId());
        }
        byte[] byArray = edwardsCurve.getKeyMaterial(privateKey);
        Assert.notEmpty(byArray, "Edwards PrivateKey 'd' value cannot be null or empty.");
        OctetPrivateJwkFactory.put(jwkContext, DefaultOctetPrivateJwk.D, byArray);
        return new DefaultOctetPrivateJwk<PrivateKey, PublicKey>(jwkContext, octetPublicJwk);
    }

    @Override
    protected OctetPrivateJwk<PrivateKey, PublicKey> createJwkFromValues(JwkContext<PrivateKey> jwkContext) {
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jwkContext);
        EdwardsCurve edwardsCurve = OctetPrivateJwkFactory.getCurve(requiredParameterReader);
        DefaultJwkContext defaultJwkContext = new DefaultJwkContext(DefaultOctetPublicJwk.PARAMS, jwkContext);
        Jwk jwk = OctetPublicJwkFactory.INSTANCE.createJwkFromValues((JwkContext)defaultJwkContext);
        byte[] byArray = requiredParameterReader.get(DefaultOctetPrivateJwk.D);
        PrivateKey privateKey = edwardsCurve.toPrivateKey(byArray, jwkContext.getProvider());
        jwkContext.setKey(privateKey);
        return new DefaultOctetPrivateJwk<PrivateKey, PublicKey>(jwkContext, (OctetPublicJwk<PublicKey>)jwk);
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

