/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AbstractFamilyJwkFactory;
import io.jsonwebtoken.impl.security.DefaultRsaPublicJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.RsaPublicJwk;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class RsaPublicJwkFactory
extends AbstractFamilyJwkFactory<RSAPublicKey, RsaPublicJwk> {
    static final RsaPublicJwkFactory INSTANCE = new RsaPublicJwkFactory();

    RsaPublicJwkFactory() {
        super("RSA", RSAPublicKey.class, DefaultRsaPublicJwk.PARAMS);
    }

    @Override
    protected RsaPublicJwk createJwkFromKey(JwkContext<RSAPublicKey> jwkContext) {
        RSAPublicKey rSAPublicKey = jwkContext.getKey();
        jwkContext.put((RSAPublicKey)((Object)DefaultRsaPublicJwk.MODULUS.getId()), DefaultRsaPublicJwk.MODULUS.applyTo(rSAPublicKey.getModulus()));
        jwkContext.put((RSAPublicKey)((Object)DefaultRsaPublicJwk.PUBLIC_EXPONENT.getId()), DefaultRsaPublicJwk.PUBLIC_EXPONENT.applyTo(rSAPublicKey.getPublicExponent()));
        return new DefaultRsaPublicJwk(jwkContext);
    }

    @Override
    protected RsaPublicJwk createJwkFromValues(JwkContext<RSAPublicKey> jwkContext) {
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jwkContext);
        BigInteger bigInteger = requiredParameterReader.get(DefaultRsaPublicJwk.MODULUS);
        BigInteger bigInteger2 = requiredParameterReader.get(DefaultRsaPublicJwk.PUBLIC_EXPONENT);
        RSAPublicKeySpec rSAPublicKeySpec = new RSAPublicKeySpec(bigInteger, bigInteger2);
        RSAPublicKey rSAPublicKey = this.generateKey(jwkContext, new CheckedFunction<KeyFactory, RSAPublicKey>(this, rSAPublicKeySpec){
            final RSAPublicKeySpec val$spec;
            final RsaPublicJwkFactory this$0;
            {
                this.this$0 = rsaPublicJwkFactory;
                this.val$spec = rSAPublicKeySpec;
            }

            @Override
            public RSAPublicKey apply(KeyFactory keyFactory) throws Exception {
                return (RSAPublicKey)keyFactory.generatePublic(this.val$spec);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
        jwkContext.setKey(rSAPublicKey);
        return new DefaultRsaPublicJwk(jwkContext);
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

