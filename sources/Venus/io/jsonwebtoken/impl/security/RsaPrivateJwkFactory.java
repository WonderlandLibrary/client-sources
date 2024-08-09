/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AbstractFamilyJwkFactory;
import io.jsonwebtoken.impl.security.DefaultJwkContext;
import io.jsonwebtoken.impl.security.DefaultRsaPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultRsaPublicJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.impl.security.RsaPublicJwkFactory;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.RsaPrivateJwk;
import io.jsonwebtoken.security.RsaPublicJwk;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAMultiPrimePrivateCrtKeySpec;
import java.security.spec.RSAOtherPrimeInfo;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class RsaPrivateJwkFactory
extends AbstractFamilyJwkFactory<RSAPrivateKey, RsaPrivateJwk> {
    private static final Set<Parameter<BigInteger>> OPTIONAL_PRIVATE_PARAMS = Collections.setOf(DefaultRsaPrivateJwk.FIRST_PRIME, DefaultRsaPrivateJwk.SECOND_PRIME, DefaultRsaPrivateJwk.FIRST_CRT_EXPONENT, DefaultRsaPrivateJwk.SECOND_CRT_EXPONENT, DefaultRsaPrivateJwk.FIRST_CRT_COEFFICIENT);
    private static final String PUBKEY_ERR_MSG = "JwkContext publicKey must be an " + RSAPublicKey.class.getName() + " instance.";
    private static final String PUB_EXPONENT_EX_MSG = "Unable to derive RSAPublicKey from RSAPrivateKey [%s]. Supported keys implement the " + RSAPrivateCrtKey.class.getName() + " or " + RSAMultiPrimePrivateCrtKey.class.getName() + " interfaces.  If the specified RSAPrivateKey cannot be one of these two, you must explicitly " + "provide an RSAPublicKey in addition to the RSAPrivateKey, as the " + "[JWA RFC, Section 6.3.2](https://www.rfc-editor.org/rfc/rfc7518.html#section-6.3.2) " + "requires public values to be present in private RSA JWKs.";

    RsaPrivateJwkFactory() {
        super("RSA", RSAPrivateKey.class, DefaultRsaPrivateJwk.PARAMS);
    }

    @Override
    protected boolean supportsKeyValues(JwkContext<?> jwkContext) {
        return super.supportsKeyValues(jwkContext) && jwkContext.containsKey(DefaultRsaPrivateJwk.PRIVATE_EXPONENT.getId());
    }

    private static BigInteger getPublicExponent(RSAPrivateKey rSAPrivateKey) {
        if (rSAPrivateKey instanceof RSAPrivateCrtKey) {
            return ((RSAPrivateCrtKey)rSAPrivateKey).getPublicExponent();
        }
        if (rSAPrivateKey instanceof RSAMultiPrimePrivateCrtKey) {
            return ((RSAMultiPrimePrivateCrtKey)rSAPrivateKey).getPublicExponent();
        }
        String string = String.format(PUB_EXPONENT_EX_MSG, KeysBridge.toString(rSAPrivateKey));
        throw new UnsupportedKeyException(string);
    }

    private RSAPublicKey derivePublic(JwkContext<RSAPrivateKey> jwkContext) {
        RSAPrivateKey rSAPrivateKey = jwkContext.getKey();
        BigInteger bigInteger = rSAPrivateKey.getModulus();
        BigInteger bigInteger2 = RsaPrivateJwkFactory.getPublicExponent(rSAPrivateKey);
        RSAPublicKeySpec rSAPublicKeySpec = new RSAPublicKeySpec(bigInteger, bigInteger2);
        return this.generateKey(jwkContext, RSAPublicKey.class, new CheckedFunction<KeyFactory, RSAPublicKey>(this, rSAPublicKeySpec, jwkContext){
            final RSAPublicKeySpec val$spec;
            final JwkContext val$ctx;
            final RsaPrivateJwkFactory this$0;
            {
                this.this$0 = rsaPrivateJwkFactory;
                this.val$spec = rSAPublicKeySpec;
                this.val$ctx = jwkContext;
            }

            @Override
            public RSAPublicKey apply(KeyFactory keyFactory) {
                try {
                    return (RSAPublicKey)keyFactory.generatePublic(this.val$spec);
                } catch (Exception exception) {
                    String string = "Unable to derive RSAPublicKey from RSAPrivateKey " + this.val$ctx + ". Cause: " + exception.getMessage();
                    throw new InvalidKeyException(string);
                }
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
    }

    @Override
    protected RsaPrivateJwk createJwkFromKey(JwkContext<RSAPrivateKey> jwkContext) {
        RSAPrivateKey rSAPrivateKey = jwkContext.getKey();
        PublicKey publicKey = jwkContext.getPublicKey();
        RSAPublicKey rSAPublicKey = publicKey != null ? Assert.isInstanceOf(RSAPublicKey.class, publicKey, PUBKEY_ERR_MSG) : this.derivePublic(jwkContext);
        boolean bl = !Strings.hasText(jwkContext.getId()) && jwkContext.getIdThumbprintAlgorithm() != null;
        JwkContext<RSAPublicKey> jwkContext2 = RsaPublicJwkFactory.INSTANCE.newContext(jwkContext, rSAPublicKey);
        RsaPublicJwk rsaPublicJwk = (RsaPublicJwk)RsaPublicJwkFactory.INSTANCE.createJwk(jwkContext2);
        jwkContext.putAll(rsaPublicJwk);
        if (bl) {
            jwkContext.setId(rsaPublicJwk.getId());
        }
        RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.PRIVATE_EXPONENT, rSAPrivateKey.getPrivateExponent());
        if (rSAPrivateKey instanceof RSAPrivateCrtKey) {
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey)rSAPrivateKey;
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.FIRST_PRIME, rSAPrivateCrtKey.getPrimeP());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.SECOND_PRIME, rSAPrivateCrtKey.getPrimeQ());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.FIRST_CRT_EXPONENT, rSAPrivateCrtKey.getPrimeExponentP());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.SECOND_CRT_EXPONENT, rSAPrivateCrtKey.getPrimeExponentQ());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.FIRST_CRT_COEFFICIENT, rSAPrivateCrtKey.getCrtCoefficient());
        } else if (rSAPrivateKey instanceof RSAMultiPrimePrivateCrtKey) {
            RSAMultiPrimePrivateCrtKey rSAMultiPrimePrivateCrtKey = (RSAMultiPrimePrivateCrtKey)rSAPrivateKey;
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.FIRST_PRIME, rSAMultiPrimePrivateCrtKey.getPrimeP());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.SECOND_PRIME, rSAMultiPrimePrivateCrtKey.getPrimeQ());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.FIRST_CRT_EXPONENT, rSAMultiPrimePrivateCrtKey.getPrimeExponentP());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.SECOND_CRT_EXPONENT, rSAMultiPrimePrivateCrtKey.getPrimeExponentQ());
            RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.FIRST_CRT_COEFFICIENT, rSAMultiPrimePrivateCrtKey.getCrtCoefficient());
            List<RSAOtherPrimeInfo> list = Arrays.asList(rSAMultiPrimePrivateCrtKey.getOtherPrimeInfo());
            if (!Collections.isEmpty(list)) {
                RsaPrivateJwkFactory.put(jwkContext, DefaultRsaPrivateJwk.OTHER_PRIMES_INFO, list);
            }
        }
        return new DefaultRsaPrivateJwk(jwkContext, rsaPublicJwk);
    }

    @Override
    protected RsaPrivateJwk createJwkFromValues(JwkContext<RSAPrivateKey> jwkContext) {
        Object object;
        Parameter<BigInteger> parameter2;
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jwkContext);
        BigInteger bigInteger = requiredParameterReader.get(DefaultRsaPrivateJwk.PRIVATE_EXPONENT);
        DefaultJwkContext defaultJwkContext = new DefaultJwkContext(DefaultRsaPublicJwk.PARAMS, jwkContext);
        Jwk jwk = RsaPublicJwkFactory.INSTANCE.createJwkFromValues((JwkContext)defaultJwkContext);
        RSAPublicKey rSAPublicKey = (RSAPublicKey)jwk.toKey();
        BigInteger bigInteger2 = rSAPublicKey.getModulus();
        BigInteger bigInteger3 = rSAPublicKey.getPublicExponent();
        boolean bl = false;
        for (Parameter<BigInteger> parameter2 : OPTIONAL_PRIVATE_PARAMS) {
            if (!jwkContext.containsKey(parameter2.getId())) continue;
            bl = true;
            break;
        }
        if (bl) {
            parameter2 = requiredParameterReader.get(DefaultRsaPrivateJwk.FIRST_PRIME);
            BigInteger bigInteger4 = requiredParameterReader.get(DefaultRsaPrivateJwk.SECOND_PRIME);
            BigInteger bigInteger5 = requiredParameterReader.get(DefaultRsaPrivateJwk.FIRST_CRT_EXPONENT);
            BigInteger bigInteger6 = requiredParameterReader.get(DefaultRsaPrivateJwk.SECOND_CRT_EXPONENT);
            BigInteger bigInteger7 = requiredParameterReader.get(DefaultRsaPrivateJwk.FIRST_CRT_COEFFICIENT);
            if (jwkContext.containsKey(DefaultRsaPrivateJwk.OTHER_PRIMES_INFO.getId())) {
                List<RSAOtherPrimeInfo> list = requiredParameterReader.get(DefaultRsaPrivateJwk.OTHER_PRIMES_INFO);
                RSAOtherPrimeInfo[] rSAOtherPrimeInfoArray = new RSAOtherPrimeInfo[Collections.size(list)];
                rSAOtherPrimeInfoArray = list.toArray(rSAOtherPrimeInfoArray);
                object = new RSAMultiPrimePrivateCrtKeySpec(bigInteger2, bigInteger3, bigInteger, (BigInteger)((Object)parameter2), bigInteger4, bigInteger5, bigInteger6, bigInteger7, rSAOtherPrimeInfoArray);
            } else {
                object = new RSAPrivateCrtKeySpec(bigInteger2, bigInteger3, bigInteger, (BigInteger)((Object)parameter2), bigInteger4, bigInteger5, bigInteger6, bigInteger7);
            }
        } else {
            object = new RSAPrivateKeySpec(bigInteger2, bigInteger);
        }
        parameter2 = this.generateFromSpec(jwkContext, (KeySpec)object);
        jwkContext.setKey((RSAPrivateKey)((Object)parameter2));
        return new DefaultRsaPrivateJwk(jwkContext, (RsaPublicJwk)jwk);
    }

    protected RSAPrivateKey generateFromSpec(JwkContext<RSAPrivateKey> jwkContext, KeySpec keySpec) {
        return this.generateKey(jwkContext, new CheckedFunction<KeyFactory, RSAPrivateKey>(this, keySpec){
            final KeySpec val$keySpec;
            final RsaPrivateJwkFactory this$0;
            {
                this.this$0 = rsaPrivateJwkFactory;
                this.val$keySpec = keySpec;
            }

            @Override
            public RSAPrivateKey apply(KeyFactory keyFactory) throws Exception {
                return (RSAPrivateKey)keyFactory.generatePrivate(this.val$keySpec);
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((KeyFactory)object);
            }
        });
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

