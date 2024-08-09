/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.lang.RequiredParameterReader;
import io.jsonwebtoken.impl.security.DefaultJwkContext;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.MalformedKeyException;
import java.math.BigInteger;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class RSAOtherPrimeInfoConverter
implements Converter<RSAOtherPrimeInfo, Object> {
    static final RSAOtherPrimeInfoConverter INSTANCE = new RSAOtherPrimeInfoConverter();
    static final Parameter<BigInteger> PRIME_FACTOR = Parameters.secretBigInt("r", "Prime Factor");
    static final Parameter<BigInteger> FACTOR_CRT_EXPONENT = Parameters.secretBigInt("d", "Factor CRT Exponent");
    static final Parameter<BigInteger> FACTOR_CRT_COEFFICIENT = Parameters.secretBigInt("t", "Factor CRT Coefficient");
    static final Set<Parameter<?>> PARAMS = Collections.setOf(PRIME_FACTOR, FACTOR_CRT_EXPONENT, FACTOR_CRT_COEFFICIENT);

    RSAOtherPrimeInfoConverter() {
    }

    @Override
    public Object applyTo(RSAOtherPrimeInfo rSAOtherPrimeInfo) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(3);
        linkedHashMap.put(PRIME_FACTOR.getId(), PRIME_FACTOR.applyTo(rSAOtherPrimeInfo.getPrime()));
        linkedHashMap.put(FACTOR_CRT_EXPONENT.getId(), FACTOR_CRT_EXPONENT.applyTo(rSAOtherPrimeInfo.getExponent()));
        linkedHashMap.put(FACTOR_CRT_COEFFICIENT.getId(), FACTOR_CRT_COEFFICIENT.applyTo(rSAOtherPrimeInfo.getCrtCoefficient()));
        return linkedHashMap;
    }

    @Override
    public RSAOtherPrimeInfo applyFrom(Object object) {
        Object object2;
        if (object == null) {
            throw new MalformedKeyException("RSA JWK 'oth' (Other Prime Info) element cannot be null.");
        }
        if (!(object instanceof Map)) {
            String string = "RSA JWK 'oth' (Other Prime Info) must contain map elements of name/value pairs. Element type found: " + object.getClass().getName();
            throw new MalformedKeyException(string);
        }
        Map map = (Map)object;
        if (Collections.isEmpty(map)) {
            throw new MalformedKeyException("RSA JWK 'oth' (Other Prime Info) element map cannot be empty.");
        }
        DefaultJwkContext<String> defaultJwkContext = new DefaultJwkContext<String>(PARAMS);
        try {
            for (Map.Entry object32 : map.entrySet()) {
                object2 = String.valueOf(object32.getKey());
                defaultJwkContext.put((String)object2, object32.getValue());
            }
        } catch (Exception exception) {
            throw new MalformedKeyException(exception.getMessage(), exception);
        }
        RequiredParameterReader requiredParameterReader = new RequiredParameterReader(defaultJwkContext);
        BigInteger bigInteger = requiredParameterReader.get(PRIME_FACTOR);
        object2 = requiredParameterReader.get(FACTOR_CRT_EXPONENT);
        BigInteger bigInteger2 = requiredParameterReader.get(FACTOR_CRT_COEFFICIENT);
        return new RSAOtherPrimeInfo(bigInteger, (BigInteger)object2, bigInteger2);
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((RSAOtherPrimeInfo)object);
    }
}

