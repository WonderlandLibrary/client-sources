/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

public final class ConstantTimeUtils {
    private ConstantTimeUtils() {
    }

    public static int equalsConstantTime(int n, int n2) {
        int n3 = 0xFFFFFFFF ^ (n ^ n2);
        n3 &= n3 >> 16;
        n3 &= n3 >> 8;
        n3 &= n3 >> 4;
        n3 &= n3 >> 2;
        n3 &= n3 >> 1;
        return n3 & 1;
    }

    public static int equalsConstantTime(long l, long l2) {
        long l3 = 0xFFFFFFFFFFFFFFFFL ^ (l ^ l2);
        l3 &= l3 >> 32;
        l3 &= l3 >> 16;
        l3 &= l3 >> 8;
        l3 &= l3 >> 4;
        l3 &= l3 >> 2;
        l3 &= l3 >> 1;
        return (int)(l3 & 1L);
    }

    public static int equalsConstantTime(byte[] byArray, int n, byte[] byArray2, int n2, int n3) {
        int n4 = 0;
        int n5 = n + n3;
        while (n < n5) {
            n4 |= byArray[n] ^ byArray2[n2];
            ++n;
            ++n2;
        }
        return ConstantTimeUtils.equalsConstantTime(n4, 0);
    }

    public static int equalsConstantTime(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence.length() != charSequence2.length()) {
            return 1;
        }
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            n |= charSequence.charAt(i) ^ charSequence2.charAt(i);
        }
        return ConstantTimeUtils.equalsConstantTime(n, 0);
    }
}

