/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedInts;
import java.math.BigInteger;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public final class UnsignedInteger
extends Number
implements Comparable<UnsignedInteger> {
    public static final UnsignedInteger ZERO = UnsignedInteger.fromIntBits(0);
    public static final UnsignedInteger ONE = UnsignedInteger.fromIntBits(1);
    public static final UnsignedInteger MAX_VALUE = UnsignedInteger.fromIntBits(-1);
    private final int value;

    private UnsignedInteger(int n) {
        this.value = n & 0xFFFFFFFF;
    }

    public static UnsignedInteger fromIntBits(int n) {
        return new UnsignedInteger(n);
    }

    public static UnsignedInteger valueOf(long l) {
        Preconditions.checkArgument((l & 0xFFFFFFFFL) == l, "value (%s) is outside the range for an unsigned integer value", l);
        return UnsignedInteger.fromIntBits((int)l);
    }

    public static UnsignedInteger valueOf(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        Preconditions.checkArgument(bigInteger.signum() >= 0 && bigInteger.bitLength() <= 32, "value (%s) is outside the range for an unsigned integer value", (Object)bigInteger);
        return UnsignedInteger.fromIntBits(bigInteger.intValue());
    }

    public static UnsignedInteger valueOf(String string) {
        return UnsignedInteger.valueOf(string, 10);
    }

    public static UnsignedInteger valueOf(String string, int n) {
        return UnsignedInteger.fromIntBits(UnsignedInts.parseUnsignedInt(string, n));
    }

    public UnsignedInteger plus(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(this.value + Preconditions.checkNotNull(unsignedInteger).value);
    }

    public UnsignedInteger minus(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(this.value - Preconditions.checkNotNull(unsignedInteger).value);
    }

    @GwtIncompatible
    public UnsignedInteger times(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(this.value * Preconditions.checkNotNull(unsignedInteger).value);
    }

    public UnsignedInteger dividedBy(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(UnsignedInts.divide(this.value, Preconditions.checkNotNull(unsignedInteger).value));
    }

    public UnsignedInteger mod(UnsignedInteger unsignedInteger) {
        return UnsignedInteger.fromIntBits(UnsignedInts.remainder(this.value, Preconditions.checkNotNull(unsignedInteger).value));
    }

    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return UnsignedInts.toLong(this.value);
    }

    @Override
    public float floatValue() {
        return this.longValue();
    }

    @Override
    public double doubleValue() {
        return this.longValue();
    }

    public BigInteger bigIntegerValue() {
        return BigInteger.valueOf(this.longValue());
    }

    @Override
    public int compareTo(UnsignedInteger unsignedInteger) {
        Preconditions.checkNotNull(unsignedInteger);
        return UnsignedInts.compare(this.value, unsignedInteger.value);
    }

    public int hashCode() {
        return this.value;
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof UnsignedInteger) {
            UnsignedInteger unsignedInteger = (UnsignedInteger)object;
            return this.value == unsignedInteger.value;
        }
        return true;
    }

    public String toString() {
        return this.toString(10);
    }

    public String toString(int n) {
        return UnsignedInts.toString(this.value, n);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((UnsignedInteger)object);
    }
}

