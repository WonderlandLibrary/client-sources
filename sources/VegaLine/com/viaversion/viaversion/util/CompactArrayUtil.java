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

    public static long[] createCompactArrayWithPadding(int bitsPerEntry, int entries, IntToLongFunction valueGetter) {
        long maxEntryValue = (1L << bitsPerEntry) - 1L;
        char valuesPerLong = (char)(64 / bitsPerEntry);
        int magicIndex = valuesPerLong - '\u0001';
        long divideAdd = RECIPROCAL_MULT_AND_ADD[magicIndex];
        long divideMul = divideAdd != 0L ? divideAdd : 0x80000000L;
        int divideShift = RECIPROCAL_RIGHT_SHIFT[magicIndex];
        int size = (entries + valuesPerLong - 1) / valuesPerLong;
        long[] data = new long[size];
        for (int i = 0; i < entries; ++i) {
            long value = valueGetter.applyAsLong(i);
            int cellIndex = (int)((long)i * divideMul + divideAdd >> 32 >> divideShift);
            int bitIndex = (i - cellIndex * valuesPerLong) * bitsPerEntry;
            data[cellIndex] = data[cellIndex] & (maxEntryValue << bitIndex ^ 0xFFFFFFFFFFFFFFFFL) | (value & maxEntryValue) << bitIndex;
        }
        return data;
    }

    public static void iterateCompactArrayWithPadding(int bitsPerEntry, int entries, long[] data, BiIntConsumer consumer) {
        long maxEntryValue = (1L << bitsPerEntry) - 1L;
        char valuesPerLong = (char)(64 / bitsPerEntry);
        int magicIndex = valuesPerLong - '\u0001';
        long divideAdd = RECIPROCAL_MULT_AND_ADD[magicIndex];
        long divideMul = divideAdd != 0L ? divideAdd : 0x80000000L;
        int divideShift = RECIPROCAL_RIGHT_SHIFT[magicIndex];
        for (int i = 0; i < entries; ++i) {
            int cellIndex = (int)((long)i * divideMul + divideAdd >> 32 >> divideShift);
            int bitIndex = (i - cellIndex * valuesPerLong) * bitsPerEntry;
            int value = (int)(data[cellIndex] >> bitIndex & maxEntryValue);
            consumer.consume(i, value);
        }
    }

    public static long[] createCompactArray(int bitsPerEntry, int entries, IntToLongFunction valueGetter) {
        long maxEntryValue = (1L << bitsPerEntry) - 1L;
        long[] data = new long[(int)Math.ceil((double)(entries * bitsPerEntry) / 64.0)];
        for (int i = 0; i < entries; ++i) {
            long value = valueGetter.applyAsLong(i);
            int bitIndex = i * bitsPerEntry;
            int startIndex = bitIndex / 64;
            int endIndex = ((i + 1) * bitsPerEntry - 1) / 64;
            int startBitSubIndex = bitIndex % 64;
            data[startIndex] = data[startIndex] & (maxEntryValue << startBitSubIndex ^ 0xFFFFFFFFFFFFFFFFL) | (value & maxEntryValue) << startBitSubIndex;
            if (startIndex == endIndex) continue;
            int endBitSubIndex = 64 - startBitSubIndex;
            data[endIndex] = data[endIndex] >>> endBitSubIndex << endBitSubIndex | (value & maxEntryValue) >> endBitSubIndex;
        }
        return data;
    }

    public static void iterateCompactArray(int bitsPerEntry, int entries, long[] data, BiIntConsumer consumer) {
        long maxEntryValue = (1L << bitsPerEntry) - 1L;
        for (int i = 0; i < entries; ++i) {
            int value;
            int bitIndex = i * bitsPerEntry;
            int startIndex = bitIndex / 64;
            int endIndex = ((i + 1) * bitsPerEntry - 1) / 64;
            int startBitSubIndex = bitIndex % 64;
            if (startIndex == endIndex) {
                value = (int)(data[startIndex] >>> startBitSubIndex & maxEntryValue);
            } else {
                int endBitSubIndex = 64 - startBitSubIndex;
                value = (int)((data[startIndex] >>> startBitSubIndex | data[endIndex] << endBitSubIndex) & maxEntryValue);
            }
            consumer.consume(i, value);
        }
    }
}

