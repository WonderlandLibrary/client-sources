/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;
import java.math.BigInteger;

public class BigIntegerUBytesConverter
implements Converter<BigInteger, byte[]> {
    private static final String NEGATIVE_MSG = "JWA Base64urlUInt values MUST be >= 0 (non-negative) per the 'Base64urlUInt' definition in [JWA RFC 7518, Section 2](https://www.rfc-editor.org/rfc/rfc7518.html#section-2)";

    @Override
    public byte[] applyTo(BigInteger bigInteger) {
        int n;
        Assert.notNull(bigInteger, "BigInteger argument cannot be null.");
        if (BigInteger.ZERO.compareTo(bigInteger) > 0) {
            throw new IllegalArgumentException(NEGATIVE_MSG);
        }
        int n2 = bigInteger.bitLength();
        byte[] byArray = bigInteger.toByteArray();
        if (byArray.length == (n = Math.max(1, Bytes.length(n2)))) {
            return byArray;
        }
        byte[] byArray2 = new byte[n];
        System.arraycopy(byArray, 1, byArray2, 0, n);
        return byArray2;
    }

    @Override
    public BigInteger applyFrom(byte[] byArray) {
        Assert.notEmpty(byArray, "Byte array cannot be null or empty.");
        return new BigInteger(1, byArray);
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom((byte[])object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((BigInteger)object);
    }
}

