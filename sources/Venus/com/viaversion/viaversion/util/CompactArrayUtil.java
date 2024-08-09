/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.viaversion.viaversion.util.BiIntConsumer;
import java.util.function.IntToLongFunction;

public final class CompactArrayUtil {
    private static final long[] RECIPROCAL_MULT_AND_ADD = new long[]{0xFFFFFFFFL, 0L, 0x55555555L, 0L, 0x33333333L, 0x2AAAAAAAL, 0x24924924L, 0L, 0x1C71C71CL, 0x19999999L, 390451572L, 0x15555555L, 0x13B13B13L, 306783378L, 0x11111111L, 0L, 0xF0F0F0FL, 0xE38E38EL, 226050910L, 0xCCCCCCCL, 0xC30C30CL, 195225786L, 186737708L, 0xAAAAAAAL, 171798691L, 0x9D89D89L, 159072862L, 0x9249249L, 148102320L, 0x8888888L, 138547332L, 0L, 130150524L, 0x7878787L, 0x7507507L, 0x71C71C7L, 116080197L, 113025455L, 0x6906906L, 0x6666666L, 104755299L, 0x6186186L, 99882960L, 97612893L, 0x5B05B05L, 93368854L, 91382282L, 0x5555555L, 87652393L, 85899345L, 0x5050505L, 0x4EC4EC4L, 81037118L, 79536431L, 78090314L, 0x4924924L, 75350303L, 74051160L, 72796055L, 0x4444444L, 70409299L, 69273666L, 0x4104104L, 0L};
    private static final int[] RECIPROCAL_RIGHT_SHIFT = new int[]{0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5};

    private CompactArrayUtil() {
        throw new AssertionError();
    }

    public static long[] createCompactArrayWithPadding(int n, int n2, IntToLongFunction intToLongFunction) {
        long l = (1L << n) - 1L;
        char c = (char)(64 / n);
        int n3 = c - '\u0001';
        long l2 = RECIPROCAL_MULT_AND_ADD[n3];
        long l3 = l2 != 0L ? l2 : 0x80000000L;
        int n4 = RECIPROCAL_RIGHT_SHIFT[n3];
        int n5 = (n2 + c - 1) / c;
        long[] lArray = new long[n5];
        for (int i = 0; i < n2; ++i) {
            long l4 = intToLongFunction.applyAsLong(i);
            int n6 = (int)((long)i * l3 + l2 >> 32 >> n4);
            int n7 = (i - n6 * c) * n;
            lArray[n6] = lArray[n6] & (l << n7 ^ 0xFFFFFFFFFFFFFFFFL) | (l4 & l) << n7;
        }
        return lArray;
    }

    public static void iterateCompactArrayWithPadding(int n, int n2, long[] lArray, BiIntConsumer biIntConsumer) {
        long l = (1L << n) - 1L;
        char c = (char)(64 / n);
        int n3 = c - '\u0001';
        long l2 = RECIPROCAL_MULT_AND_ADD[n3];
        long l3 = l2 != 0L ? l2 : 0x80000000L;
        int n4 = RECIPROCAL_RIGHT_SHIFT[n3];
        for (int i = 0; i < n2; ++i) {
            int n5 = (int)((long)i * l3 + l2 >> 32 >> n4);
            int n6 = (i - n5 * c) * n;
            int n7 = (int)(lArray[n5] >> n6 & l);
            biIntConsumer.consume(i, n7);
        }
    }

    public static long[] createCompactArray(int n, int n2, IntToLongFunction intToLongFunction) {
        long l = (1L << n) - 1L;
        long[] lArray = new long[(int)Math.ceil((double)(n2 * n) / 64.0)];
        for (int i = 0; i < n2; ++i) {
            long l2 = intToLongFunction.applyAsLong(i);
            int n3 = i * n;
            int n4 = n3 / 64;
            int n5 = ((i + 1) * n - 1) / 64;
            int n6 = n3 % 64;
            lArray[n4] = lArray[n4] & (l << n6 ^ 0xFFFFFFFFFFFFFFFFL) | (l2 & l) << n6;
            if (n4 == n5) continue;
            int n7 = 64 - n6;
            lArray[n5] = lArray[n5] >>> n7 << n7 | (l2 & l) >> n7;
        }
        return lArray;
    }

    public static void iterateCompactArray(int n, int n2, long[] lArray, BiIntConsumer biIntConsumer) {
        long l = (1L << n) - 1L;
        for (int i = 0; i < n2; ++i) {
            int n3;
            int n4 = i * n;
            int n5 = n4 / 64;
            int n6 = ((i + 1) * n - 1) / 64;
            int n7 = n4 % 64;
            if (n5 == n6) {
                n3 = (int)(lArray[n5] >>> n7 & l);
            } else {
                int n8 = 64 - n7;
                n3 = (int)((lArray[n5] >>> n7 | lArray[n6] << n8) & l);
            }
            biIntConsumer.consume(i, n3);
        }
    }
}

