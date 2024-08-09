/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil;

public class HashCommon {
    private static final int INT_PHI = -1640531527;
    private static final int INV_INT_PHI = 340573321;
    private static final long LONG_PHI = -7046029254386353131L;
    private static final long INV_LONG_PHI = -1018231460777725123L;

    protected HashCommon() {
    }

    public static int murmurHash3(int n) {
        n ^= n >>> 16;
        n *= -2048144789;
        n ^= n >>> 13;
        n *= -1028477387;
        n ^= n >>> 16;
        return n;
    }

    public static long murmurHash3(long l) {
        l ^= l >>> 33;
        l *= -49064778989728563L;
        l ^= l >>> 33;
        l *= -4265267296055464877L;
        l ^= l >>> 33;
        return l;
    }

    public static int mix(int n) {
        int n2 = n * -1640531527;
        return n2 ^ n2 >>> 16;
    }

    public static int invMix(int n) {
        return (n ^ n >>> 16) * 340573321;
    }

    public static long mix(long l) {
        long l2 = l * -7046029254386353131L;
        l2 ^= l2 >>> 32;
        return l2 ^ l2 >>> 16;
    }

    public static long invMix(long l) {
        l ^= l >>> 32;
        l ^= l >>> 16;
        return (l ^ l >>> 32) * -1018231460777725123L;
    }

    public static int float2int(float f) {
        return Float.floatToRawIntBits(f);
    }

    public static int double2int(double d) {
        long l = Double.doubleToRawLongBits(d);
        return (int)(l ^ l >>> 32);
    }

    public static int long2int(long l) {
        return (int)(l ^ l >>> 32);
    }

    public static int nextPowerOfTwo(int n) {
        return 1 << 32 - Integer.numberOfLeadingZeros(n - 1);
    }

    public static long nextPowerOfTwo(long l) {
        return 1L << 64 - Long.numberOfLeadingZeros(l - 1L);
    }

    public static int maxFill(int n, float f) {
        return Math.min((int)Math.ceil((float)n * f), n - 1);
    }

    public static long maxFill(long l, float f) {
        return Math.min((long)Math.ceil((float)l * f), l - 1L);
    }

    public static int arraySize(int n, float f) {
        long l = Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil((float)n / f)));
        if (l > 0x40000000L) {
            throw new IllegalArgumentException("Too large (" + n + " expected elements with load factor " + f + ")");
        }
        return (int)l;
    }

    public static long bigArraySize(long l, float f) {
        return HashCommon.nextPowerOfTwo((long)Math.ceil((float)l / f));
    }
}

