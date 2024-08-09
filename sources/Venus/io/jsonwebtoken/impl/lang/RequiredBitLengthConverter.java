/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RequiredBitLengthConverter
implements Converter<byte[], Object> {
    private final Converter<byte[], Object> converter;
    private final int bitLength;
    private final boolean exact;

    public RequiredBitLengthConverter(Converter<byte[], Object> converter, int n) {
        this(converter, n, true);
    }

    public RequiredBitLengthConverter(Converter<byte[], Object> converter, int n, boolean bl) {
        this.converter = Assert.notNull(converter, "Converter cannot be null.");
        this.bitLength = Assert.gt(n, 0, "bitLength must be greater than 0");
        this.exact = bl;
    }

    private byte[] assertLength(byte[] byArray) {
        long l = Bytes.bitLength(byArray);
        if (this.exact && l != (long)this.bitLength) {
            String string = "Byte array must be exactly " + Bytes.bitsMsg(this.bitLength) + ". Found " + Bytes.bitsMsg(l);
            throw new IllegalArgumentException(string);
        }
        if (l < (long)this.bitLength) {
            String string = "Byte array must be at least " + Bytes.bitsMsg(this.bitLength) + ". Found " + Bytes.bitsMsg(l);
            throw new IllegalArgumentException(string);
        }
        return byArray;
    }

    @Override
    public Object applyTo(byte[] byArray) {
        this.assertLength(byArray);
        return this.converter.applyTo(byArray);
    }

    @Override
    public byte[] applyFrom(Object object) {
        byte[] byArray = this.converter.applyFrom(object);
        return this.assertLength(byArray);
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((byte[])object);
    }
}

