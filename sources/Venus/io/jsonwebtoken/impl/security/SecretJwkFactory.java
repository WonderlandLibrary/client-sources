/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.AbstractFamilyJwkFactory;
import io.jsonwebtoken.impl.security.AbstractJwk;
import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.impl.security.DefaultSecretJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.KeysBridge;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.SecretJwk;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class SecretJwkFactory
extends AbstractFamilyJwkFactory<SecretKey, SecretJwk> {
    SecretJwkFactory() {
        super("oct", SecretKey.class, DefaultSecretJwk.PARAMS);
    }

    @Override
    protected SecretJwk createJwkFromKey(JwkContext<SecretKey> jwkContext) {
        String string;
        SecretKey secretKey = Assert.notNull(jwkContext.getKey(), "JwkContext key cannot be null.");
        try {
            byte[] byArray = KeysBridge.getEncoded(secretKey);
            string = Encoders.BASE64URL.encode(byArray);
            Assert.hasText(string, "k value cannot be null or empty.");
        } catch (Throwable throwable) {
            String string2 = "Unable to encode SecretKey to JWK: " + throwable.getMessage();
            throw new InvalidKeyException(string2, throwable);
        }
        jwkContext.put((SecretKey)((Object)DefaultSecretJwk.K.getId()), string);
        return new DefaultSecretJwk(jwkContext);
    }

    private static void assertKeyBitLength(byte[] byArray, MacAlgorithm macAlgorithm) {
        long l;
        long l2 = Bytes.bitLength(byArray);
        if (l2 != (l = (long)macAlgorithm.getKeyBitLength())) {
            String string = "Secret JWK " + AbstractJwk.ALG + " value is '" + macAlgorithm.getId() + "', but the " + DefaultSecretJwk.K + " length does not equal the '" + macAlgorithm.getId() + "' length requirement of " + Bytes.bitsMsg(l) + ". This discrepancy could be the result of an algorithm " + "substitution attack or simply an erroneously constructed JWK. In either case, it is likely " + "to result in unexpected or undesired security consequences.";
            throw new MalformedKeyException(string);
        }
    }

    @Override
    protected SecretJwk createJwkFromValues(JwkContext<SecretKey> jwkContext) {
        Object object;
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(jwkContext);
        byte[] byArray = requiredParameterReader.get(DefaultSecretJwk.K);
        String string = null;
        String string2 = jwkContext.getAlgorithm();
        if (Strings.hasText(string2) && (object = (SecureDigestAlgorithm)Jwts.SIG.get().get(string2)) instanceof MacAlgorithm) {
            string = ((CryptoAlgorithm)object).getJcaName();
            Assert.hasText(string, "Algorithm jcaName cannot be null or empty.");
            SecretJwkFactory.assertKeyBitLength(byArray, (MacAlgorithm)object);
        }
        if (!Strings.hasText(string)) {
            string = jwkContext.isSigUse() ? "HmacSHA" + Bytes.bitLength(byArray) : "AES";
        }
        Assert.stateNotNull(string, "jcaName cannot be null (invariant)");
        object = new SecretKeySpec(byArray, string);
        jwkContext.setKey((SecretKey)object);
        return new DefaultSecretJwk(jwkContext);
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

