/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Converters;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.security.AbstractFamilyJwkFactory;
import io.jsonwebtoken.impl.security.ECCurve;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.math.BigInteger;
import java.security.Key;
import java.security.interfaces.ECKey;
import java.util.Set;

abstract class AbstractEcJwkFactory<K extends Key & ECKey, J extends Jwk<K>>
extends AbstractFamilyJwkFactory<K, J> {
    protected static ECCurve getCurveByJwaId(String string) {
        ECCurve eCCurve = ECCurve.findById(string);
        if (eCCurve == null) {
            String string2 = "Unrecognized JWA EC curve id '" + string + "'";
            throw new UnsupportedKeyException(string2);
        }
        return eCCurve;
    }

    static String toOctetString(int n, BigInteger bigInteger) {
        byte[] byArray = Converters.BIGINT_UBYTES.applyTo(bigInteger);
        int n2 = (int)Math.ceil((double)n / 8.0);
        if (n2 > byArray.length) {
            byte[] byArray2 = new byte[n2];
            System.arraycopy(byArray, 0, byArray2, n2 - byArray.length, byArray.length);
            byArray = byArray2;
        }
        return Encoders.BASE64URL.encode(byArray);
    }

    AbstractEcJwkFactory(Class<K> clazz, Set<Parameter<?>> set) {
        super("EC", clazz, set);
    }
}

