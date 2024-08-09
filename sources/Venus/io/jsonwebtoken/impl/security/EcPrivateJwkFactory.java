/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AbstractEcJwkFactory;
import io.jsonwebtoken.impl.security.DefaultEcPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultEcPublicJwk;
import io.jsonwebtoken.impl.security.DefaultJwkContext;
import io.jsonwebtoken.impl.security.ECCurve;
import io.jsonwebtoken.impl.security.EcPublicJwkFactory;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.EcPrivateJwk;
import io.jsonwebtoken.security.EcPublicJwk;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwk;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class EcPrivateJwkFactory
extends AbstractEcJwkFactory<ECPrivateKey, EcPrivateJwk> {
    private static final String ECPUBKEY_ERR_MSG = "JwkContext publicKey must be an " + ECPublicKey.class.getName() + " instance.";
    private static final EcPublicJwkFactory PUB_FACTORY = EcPublicJwkFactory.INSTANCE;

    EcPrivateJwkFactory() {
        super(ECPrivateKey.class, DefaultEcPrivateJwk.PARAMS);
    }

    @Override
    protected boolean supportsKeyValues(JwkContext<?> jwkContext) {
        return super.supportsKeyValues(jwkContext) && jwkContext.containsKey(DefaultEcPrivateJwk.D.getId());
    }

    protected ECPublicKey derivePublic(KeyFactory keyFactory, ECPublicKeySpec eCPublicKeySpec) throws InvalidKeySpecException {
        return (ECPublicKey)keyFactory.generatePublic(eCPublicKeySpec);
    }

    protected ECPublicKey derivePublic(JwkContext<ECPrivateKey> jwkContext) {
        ECPrivateKey eCPrivateKey = jwkContext.getKey();
        return this.generateKey(jwkContext, ECPublicKey.class, new CheckedFunction<KeyFactory, ECPublicKey>(this, eCPrivateKey){
            final ECPrivateKey val$key;
            final EcPrivateJwkFactory this$0;
            {
                this.this$0 = ecPrivateJwkFactory;
                this.val$key = eCPrivateKey;
            }

            @Override
            public ECPublicKey apply(KeyFactory keyFactory) {
                try {
                    ECPublicKeySpec eCPublicKeySpec = ECCurve.publicKeySpec(this.val$key);
                    return this.this$0.derivePublic(keyFactory, eCPublicKeySpec);
                } catch (Exception exception) {
                    String string = "Unable to derive ECPublicKey from ECPrivateKey: " + exception.getMessage();
                    throw new InvalidKeyException(string, exception);
                }
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
    }

    @Override
    protected EcPrivateJwk createJwkFromKey(JwkContext<ECPrivateKey> jwkContext) {
        ECPrivateKey eCPrivateKey = jwkContext.getKey();
        PublicKey publicKey = jwkContext.getPublicKey();
        ECPublicKey eCPublicKey = publicKey != null ? Assert.isInstanceOf(ECPublicKey.class, publicKey, ECPUBKEY_ERR_MSG) : this.derivePublic(jwkContext);
        boolean bl = !Strings.hasText(jwkContext.getId()) && jwkContext.getIdThumbprintAlgorithm() != null;
        JwkContext<ECPublicKey> jwkContext2 = PUB_FACTORY.newContext(jwkContext, eCPublicKey);
        EcPublicJwk ecPublicJwk = (EcPublicJwk)PUB_FACTORY.createJwk(jwkContext2);
        jwkContext.putAll(ecPublicJwk);
        if (bl) {
            jwkContext.setId(ecPublicJwk.getId());
        }
        int n = eCPrivateKey.getParams().getCurve().getField().getFieldSize();
        String string = EcPrivateJwkFactory.toOctetString(n, eCPrivateKey.getS());
        jwkContext.put((ECPrivateKey)((Object)DefaultEcPrivateJwk.D.getId()), string);
        return new DefaultEcPrivateJwk(jwkContext, ecPublicJwk);
    }

    @Override
    protected EcPrivateJwk createJwkFromValues(JwkContext<ECPrivateKey> jwkContext) {
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jwkContext);
        String string = requiredParameterReader.get(DefaultEcPublicJwk.CRV);
        BigInteger bigInteger = requiredParameterReader.get(DefaultEcPrivateJwk.D);
        DefaultJwkContext defaultJwkContext = new DefaultJwkContext(DefaultEcPublicJwk.PARAMS, jwkContext);
        EcPublicJwk ecPublicJwk = (EcPublicJwk)EcPublicJwkFactory.INSTANCE.createJwk(defaultJwkContext);
        ECCurve eCCurve = EcPrivateJwkFactory.getCurveByJwaId(string);
        ECPrivateKeySpec eCPrivateKeySpec = new ECPrivateKeySpec(bigInteger, eCCurve.toParameterSpec());
        ECPrivateKey eCPrivateKey = this.generateKey(jwkContext, new CheckedFunction<KeyFactory, ECPrivateKey>(this, eCPrivateKeySpec){
            final ECPrivateKeySpec val$privateSpec;
            final EcPrivateJwkFactory this$0;
            {
                this.this$0 = ecPrivateJwkFactory;
                this.val$privateSpec = eCPrivateKeySpec;
            }

            @Override
            public ECPrivateKey apply(KeyFactory keyFactory) throws Exception {
                return (ECPrivateKey)keyFactory.generatePrivate(this.val$privateSpec);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
        jwkContext.setKey(eCPrivateKey);
        return new DefaultEcPrivateJwk(jwkContext, ecPublicJwk);
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

