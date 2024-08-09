/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterReadable;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.impl.security.AbstractPrivateJwk;
import io.jsonwebtoken.impl.security.DefaultRsaPublicJwk;
import io.jsonwebtoken.impl.security.JwkContext;
import io.jsonwebtoken.impl.security.RSAOtherPrimeInfoConverter;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.PrivateJwk;
import io.jsonwebtoken.security.RsaPrivateJwk;
import io.jsonwebtoken.security.RsaPublicJwk;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;
import java.util.Set;

class DefaultRsaPrivateJwk
extends AbstractPrivateJwk<RSAPrivateKey, RSAPublicKey, RsaPublicJwk>
implements RsaPrivateJwk {
    static final Parameter<BigInteger> PRIVATE_EXPONENT = Parameters.secretBigInt("d", "Private Exponent");
    static final Parameter<BigInteger> FIRST_PRIME = Parameters.secretBigInt("p", "First Prime Factor");
    static final Parameter<BigInteger> SECOND_PRIME = Parameters.secretBigInt("q", "Second Prime Factor");
    static final Parameter<BigInteger> FIRST_CRT_EXPONENT = Parameters.secretBigInt("dp", "First Factor CRT Exponent");
    static final Parameter<BigInteger> SECOND_CRT_EXPONENT = Parameters.secretBigInt("dq", "Second Factor CRT Exponent");
    static final Parameter<BigInteger> FIRST_CRT_COEFFICIENT = Parameters.secretBigInt("qi", "First CRT Coefficient");
    static final Parameter<List<RSAOtherPrimeInfo>> OTHER_PRIMES_INFO = (Parameter)Parameters.builder(RSAOtherPrimeInfo.class).setId("oth").setName("Other Primes Info").setConverter(RSAOtherPrimeInfoConverter.INSTANCE).list().build();
    static final Set<Parameter<?>> PARAMS = Collections.concat(DefaultRsaPublicJwk.PARAMS, PRIVATE_EXPONENT, FIRST_PRIME, SECOND_PRIME, FIRST_CRT_EXPONENT, SECOND_CRT_EXPONENT, FIRST_CRT_COEFFICIENT, OTHER_PRIMES_INFO);

    DefaultRsaPrivateJwk(JwkContext<RSAPrivateKey> jwkContext, RsaPublicJwk rsaPublicJwk) {
        super(jwkContext, DefaultRsaPublicJwk.THUMBPRINT_PARAMS, rsaPublicJwk);
    }

    private static boolean equals(RSAOtherPrimeInfo rSAOtherPrimeInfo, RSAOtherPrimeInfo rSAOtherPrimeInfo2) {
        if (rSAOtherPrimeInfo == rSAOtherPrimeInfo2) {
            return false;
        }
        if (rSAOtherPrimeInfo == null || rSAOtherPrimeInfo2 == null) {
            return true;
        }
        return Parameters.bytesEquals(rSAOtherPrimeInfo.getPrime(), rSAOtherPrimeInfo2.getPrime()) && Parameters.bytesEquals(rSAOtherPrimeInfo.getExponent(), rSAOtherPrimeInfo2.getExponent()) && Parameters.bytesEquals(rSAOtherPrimeInfo.getCrtCoefficient(), rSAOtherPrimeInfo2.getCrtCoefficient());
    }

    private static boolean equalsOtherPrimes(ParameterReadable parameterReadable, ParameterReadable parameterReadable2) {
        int n;
        List<RSAOtherPrimeInfo> list = parameterReadable.get(OTHER_PRIMES_INFO);
        List<RSAOtherPrimeInfo> list2 = parameterReadable2.get(OTHER_PRIMES_INFO);
        int n2 = Collections.size(list);
        if (n2 != (n = Collections.size(list2))) {
            return true;
        }
        if (n2 == 0) {
            return false;
        }
        RSAOtherPrimeInfo[] rSAOtherPrimeInfoArray = list.toArray(new RSAOtherPrimeInfo[0]);
        RSAOtherPrimeInfo[] rSAOtherPrimeInfoArray2 = list2.toArray(new RSAOtherPrimeInfo[0]);
        for (int i = 0; i < n2; ++i) {
            if (DefaultRsaPrivateJwk.equals(rSAOtherPrimeInfoArray[i], rSAOtherPrimeInfoArray2[i])) continue;
            return true;
        }
        return false;
    }

    @Override
    protected boolean equals(PrivateJwk<?, ?, ?> privateJwk) {
        return privateJwk instanceof RsaPrivateJwk && DefaultRsaPublicJwk.equalsPublic(this, privateJwk) && Parameters.equals(this, privateJwk, PRIVATE_EXPONENT) && Parameters.equals(this, privateJwk, FIRST_PRIME) && Parameters.equals(this, privateJwk, SECOND_PRIME) && Parameters.equals(this, privateJwk, FIRST_CRT_EXPONENT) && Parameters.equals(this, privateJwk, SECOND_CRT_EXPONENT) && Parameters.equals(this, privateJwk, FIRST_CRT_COEFFICIENT) && DefaultRsaPrivateJwk.equalsOtherPrimes(this, (ParameterReadable)((Object)privateJwk));
    }
}

