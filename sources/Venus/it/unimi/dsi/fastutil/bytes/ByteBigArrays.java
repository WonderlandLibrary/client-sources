/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class ByteBigArrays {
    public static final byte[][] EMPTY_BIG_ARRAY = new byte[0][];
    public static final byte[][] DEFAULT_EMPTY_BIG_ARRAY = new byte[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 1;

    private ByteBigArrays() {
    }

    public static byte get(byte[][] byArray, long l) {
        return byArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(byte[][] byArray, long l, byte by) {
        byArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = by;
    }

    public static void swap(byte[][] byArray, long l, long l2) {
        byte by = byArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        byArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = byArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        byArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = by;
    }

    public static void add(byte[][] byArray, long l, byte by) {
        byte[] byArray2 = byArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        byArray2[n] = (byte)(byArray2[n] + by);
    }

    public static void mul(byte[][] byArray, long l, byte by) {
        byte[] byArray2 = byArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        byArray2[n] = (byte)(byArray2[n] * by);
    }

    public static void incr(byte[][] byArray, long l) {
        byte[] byArray2 = byArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        byArray2[n] = (byte)(byArray2[n] + 1);
    }

    public static void decr(byte[][] byArray, long l) {
        byte[] byArray2 = byArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        byArray2[n] = (byte)(byArray2[n] - 1);
    }

    public static long length(byte[][] byArray) {
        int n = byArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)byArray[n - 1].length;
    }

    public static void copy(byte[][] byArray, long l, byte[][] byArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(byArray[n].length - n3, byArray2[n2].length - n4));
                System.arraycopy(byArray[n], n3, byArray2[n2], n4, n5);
                if ((n3 += n5) == 0x8000000) {
                    n3 = 0;
                    ++n;
                }
                if ((n4 += n5) == 0x8000000) {
                    n4 = 0;
                    ++n2;
                }
                l3 -= (long)n5;
            }
        } else {
            int n = BigArrays.segment(l + l3);
            int n6 = BigArrays.segment(l2 + l3);
            int n7 = BigArrays.displacement(l + l3);
            int n8 = BigArrays.displacement(l2 + l3);
            while (l3 > 0L) {
                if (n7 == 0) {
                    n7 = 0x8000000;
                    --n;
                }
                if (n8 == 0) {
                    n8 = 0x8000000;
                    --n6;
                }
                int n9 = (int)Math.min(l3, (long)Math.min(n7, n8));
                System.arraycopy(byArray[n], n7 - n9, byArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(byte[][] byArray, long l, byte[] byArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(byArray[n3].length - n4, n2);
            System.arraycopy(byArray[n3], n4, byArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(byte[] byArray, int n, byte[][] byArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(byArray2[n2].length - n3), l2);
            System.arraycopy(byArray, n, byArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static byte[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        byte[][] byArrayArray = new byte[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                byArrayArray[i] = new byte[0x8000000];
            }
            byArrayArray[n - 1] = new byte[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                byArrayArray[i] = new byte[0x8000000];
            }
        }
        return byArrayArray;
    }

    public static byte[][] wrap(byte[] byArray) {
        if (byArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (byArray.length <= 0x8000000) {
            return new byte[][]{byArray};
        }
        byte[][] byArray2 = ByteBigArrays.newBigArray(byArray.length);
        for (int i = 0; i < byArray2.length; ++i) {
            System.arraycopy(byArray, (int)BigArrays.start(i), byArray2[i], 0, byArray2[i].length);
        }
        return byArray2;
    }

    public static byte[][] ensureCapacity(byte[][] byArray, long l) {
        return ByteBigArrays.ensureCapacity(byArray, l, ByteBigArrays.length(byArray));
    }

    public static byte[][] forceCapacity(byte[][] byArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = byArray.length - (byArray.length == 0 || byArray.length > 0 && byArray[byArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        byte[][] byArray2 = (byte[][])Arrays.copyOf(byArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                byArray2[i] = new byte[0x8000000];
            }
            byArray2[n2 - 1] = new byte[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                byArray2[i] = new byte[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            ByteBigArrays.copy(byArray, (long)n * 0x8000000L, byArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return byArray2;
    }

    public static byte[][] ensureCapacity(byte[][] byArray, long l, long l2) {
        return l > ByteBigArrays.length(byArray) ? ByteBigArrays.forceCapacity(byArray, l, l2) : byArray;
    }

    public static byte[][] grow(byte[][] byArray, long l) {
        long l2 = ByteBigArrays.length(byArray);
        return l > l2 ? ByteBigArrays.grow(byArray, l, l2) : byArray;
    }

    public static byte[][] grow(byte[][] byArray, long l, long l2) {
        long l3 = ByteBigArrays.length(byArray);
        return l > l3 ? ByteBigArrays.ensureCapacity(byArray, Math.max(l3 + (l3 >> 1), l), l2) : byArray;
    }

    public static byte[][] trim(byte[][] byArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = ByteBigArrays.length(byArray);
        if (l >= l2) {
            return byArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        byte[][] byArray2 = (byte[][])Arrays.copyOf(byArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            byArray2[n - 1] = ByteArrays.trim(byArray2[n - 1], n2);
        }
        return byArray2;
    }

    public static byte[][] setLength(byte[][] byArray, long l) {
        long l2 = ByteBigArrays.length(byArray);
        if (l == l2) {
            return byArray;
        }
        if (l < l2) {
            return ByteBigArrays.trim(byArray, l);
        }
        return ByteBigArrays.ensureCapacity(byArray, l);
    }

    public static byte[][] copy(byte[][] byArray, long l, long l2) {
        ByteBigArrays.ensureOffsetLength(byArray, l, l2);
        byte[][] byArray2 = ByteBigArrays.newBigArray(l2);
        ByteBigArrays.copy(byArray, l, byArray2, 0L, l2);
        return byArray2;
    }

    public static byte[][] copy(byte[][] byArray) {
        byte[][] byArray2 = (byte[][])byArray.clone();
        int n = byArray2.length;
        while (n-- != 0) {
            byArray2[n] = (byte[])byArray[n].clone();
        }
        return byArray2;
    }

    public static void fill(byte[][] byArray, byte by) {
        int n = byArray.length;
        while (n-- != 0) {
            Arrays.fill(byArray[n], by);
        }
    }

    public static void fill(byte[][] byArray, long l, long l2, byte by) {
        long l3 = ByteBigArrays.length(byArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(byArray[n], n3, n4, by);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(byArray[n2], 0, n4, by);
        }
        while (--n2 > n) {
            Arrays.fill(byArray[n2], by);
        }
        Arrays.fill(byArray[n], n3, 0x8000000, by);
    }

    public static boolean equals(byte[][] byArray, byte[][] byArray2) {
        if (ByteBigArrays.length(byArray) != ByteBigArrays.length(byArray2)) {
            return true;
        }
        int n = byArray.length;
        while (n-- != 0) {
            byte[] byArray3 = byArray[n];
            byte[] byArray4 = byArray2[n];
            int n2 = byArray3.length;
            while (n2-- != 0) {
                if (byArray3[n2] == byArray4[n2]) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(byte[][] byArray) {
        if (byArray == null) {
            return "null";
        }
        long l = ByteBigArrays.length(byArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(ByteBigArrays.get(byArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(byte[][] byArray, long l, long l2) {
        BigArrays.ensureFromTo(ByteBigArrays.length(byArray), l, l2);
    }

    public static void ensureOffsetLength(byte[][] byArray, long l, long l2) {
        BigArrays.ensureOffsetLength(ByteBigArrays.length(byArray), l, l2);
    }

    private static void vecSwap(byte[][] byArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            ByteBigArrays.swap(byArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(byte[][] byArray, long l, long l2, long l3, ByteComparator byteComparator) {
        int n = byteComparator.compare(ByteBigArrays.get(byArray, l), ByteBigArrays.get(byArray, l2));
        int n2 = byteComparator.compare(ByteBigArrays.get(byArray, l), ByteBigArrays.get(byArray, l3));
        int n3 = byteComparator.compare(ByteBigArrays.get(byArray, l2), ByteBigArrays.get(byArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(byte[][] byArray, long l, long l2, ByteComparator byteComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (byteComparator.compare(ByteBigArrays.get(byArray, j), ByteBigArrays.get(byArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            ByteBigArrays.swap(byArray, i, l3);
        }
    }

    public static void quickSort(byte[][] byArray, long l, long l2, ByteComparator byteComparator) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            ByteBigArrays.selectionSort(byArray, l, l2, byteComparator);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = ByteBigArrays.med3(byArray, l7, l7 + l9, l7 + 2L * l9, byteComparator);
                l6 = ByteBigArrays.med3(byArray, l6 - l9, l6, l6 + l9, byteComparator);
                l8 = ByteBigArrays.med3(byArray, l8 - 2L * l9, l8 - l9, l8, byteComparator);
            }
            l6 = ByteBigArrays.med3(byArray, l7, l6, l8, byteComparator);
        }
        byte by = ByteBigArrays.get(byArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = byteComparator.compare(ByteBigArrays.get(byArray, l10), by)) <= 0) {
                if (n == 0) {
                    ByteBigArrays.swap(byArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = byteComparator.compare(ByteBigArrays.get(byArray, l3), by)) >= 0) {
                if (n == 0) {
                    ByteBigArrays.swap(byArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            ByteBigArrays.swap(byArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        ByteBigArrays.vecSwap(byArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        ByteBigArrays.vecSwap(byArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            ByteBigArrays.quickSort(byArray, l, l + l13, byteComparator);
        }
        if ((l13 = l11 - l3) > 1L) {
            ByteBigArrays.quickSort(byArray, l12 - l13, l12, byteComparator);
        }
    }

    private static long med3(byte[][] byArray, long l, long l2, long l3) {
        int n = Byte.compare(ByteBigArrays.get(byArray, l), ByteBigArrays.get(byArray, l2));
        int n2 = Byte.compare(ByteBigArrays.get(byArray, l), ByteBigArrays.get(byArray, l3));
        int n3 = Byte.compare(ByteBigArrays.get(byArray, l2), ByteBigArrays.get(byArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(byte[][] byArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (ByteBigArrays.get(byArray, j) >= ByteBigArrays.get(byArray, l3)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            ByteBigArrays.swap(byArray, i, l3);
        }
    }

    public static void quickSort(byte[][] byArray, ByteComparator byteComparator) {
        ByteBigArrays.quickSort(byArray, 0L, ByteBigArrays.length(byArray), byteComparator);
    }

    public static void quickSort(byte[][] byArray, long l, long l2) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            ByteBigArrays.selectionSort(byArray, l, l2);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = ByteBigArrays.med3(byArray, l7, l7 + l9, l7 + 2L * l9);
                l6 = ByteBigArrays.med3(byArray, l6 - l9, l6, l6 + l9);
                l8 = ByteBigArrays.med3(byArray, l8 - 2L * l9, l8 - l9, l8);
            }
            l6 = ByteBigArrays.med3(byArray, l7, l6, l8);
        }
        byte by = ByteBigArrays.get(byArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = Byte.compare(ByteBigArrays.get(byArray, l10), by)) <= 0) {
                if (n == 0) {
                    ByteBigArrays.swap(byArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = Byte.compare(ByteBigArrays.get(byArray, l3), by)) >= 0) {
                if (n == 0) {
                    ByteBigArrays.swap(byArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            ByteBigArrays.swap(byArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        ByteBigArrays.vecSwap(byArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        ByteBigArrays.vecSwap(byArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            ByteBigArrays.quickSort(byArray, l, l + l13);
        }
        if ((l13 = l11 - l3) > 1L) {
            ByteBigArrays.quickSort(byArray, l12 - l13, l12);
        }
    }

    public static void quickSort(byte[][] byArray) {
        ByteBigArrays.quickSort(byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static long binarySearch(byte[][] byArray, long l, long l2, byte by) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            byte by2 = ByteBigArrays.get(byArray, l3);
            if (by2 < by) {
                l = l3 + 1L;
                continue;
            }
            if (by2 > by) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(byte[][] byArray, byte by) {
        return ByteBigArrays.binarySearch(byArray, 0L, ByteBigArrays.length(byArray), by);
    }

    public static long binarySearch(byte[][] byArray, long l, long l2, byte by, ByteComparator byteComparator) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            byte by2 = ByteBigArrays.get(byArray, l3);
            int n = byteComparator.compare(by2, by);
            if (n < 0) {
                l = l3 + 1L;
                continue;
            }
            if (n > 0) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(byte[][] byArray, byte by, ByteComparator byteComparator) {
        return ByteBigArrays.binarySearch(byArray, 0L, ByteBigArrays.length(byArray), by, byteComparator);
    }

    public static void radixSort(byte[][] byArray) {
        ByteBigArrays.radixSort(byArray, 0L, ByteBigArrays.length(byArray));
    }

    public static void radixSort(byte[][] byArray, long l, long l2) {
        boolean bl = false;
        boolean bl2 = true;
        long[] lArray = new long[1];
        int n = 0;
        long[] lArray2 = new long[1];
        int n2 = 0;
        int[] nArray = new int[1];
        int n3 = 0;
        lArray[n++] = l;
        lArray2[n2++] = l2 - l;
        nArray[n3++] = 0;
        long[] lArray3 = new long[256];
        long[] lArray4 = new long[256];
        byte[][] byArray2 = ByteBigArrays.newBigArray(l2 - l);
        while (n > 0) {
            int n4;
            int n5;
            long l3 = lArray[--n];
            long l4 = lArray2[--n2];
            int n6 = n5 = (n4 = nArray[--n3]) % 1 == 0 ? 128 : 0;
            if (l4 < 40L) {
                ByteBigArrays.selectionSort(byArray, l3, l3 + l4);
                continue;
            }
            int n7 = (0 - n4 % 1) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray2, l5, (byte)(ByteBigArrays.get(byArray, l3 + l5) >>> n7 & 0xFF ^ n5));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n8 = ByteBigArrays.get(byArray2, l5) & 0xFF;
                lArray3[n8] = lArray3[n8] + 1L;
            }
            int n9 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray3[i] != 0L) {
                    n9 = i;
                    if (n4 < 0 && lArray3[i] > 1L) {
                        lArray[n++] = l6 + l3;
                        lArray2[n2++] = lArray3[i];
                        nArray[n3++] = n4 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n9];
            lArray3[n9] = 0L;
            int n10 = -1;
            for (long i = 0L; i < l7; i += lArray3[n10]) {
                byte by = ByteBigArrays.get(byArray, i + l3);
                n10 = ByteBigArrays.get(byArray2, i) & 0xFF;
                while (true) {
                    int n11 = n10;
                    long l8 = lArray4[n11] - 1L;
                    lArray4[n11] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    byte by2 = by;
                    int n12 = n10;
                    by = ByteBigArrays.get(byArray, l9 + l3);
                    n10 = ByteBigArrays.get(byArray2, l9) & 0xFF;
                    ByteBigArrays.set(byArray, l9 + l3, by2);
                    ByteBigArrays.set(byArray2, l9, (byte)n12);
                }
                ByteBigArrays.set(byArray, i + l3, by);
                lArray3[n10] = 0L;
            }
        }
    }

    private static void selectionSort(byte[][] byArray, byte[][] byArray2, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (ByteBigArrays.get(byArray, j) >= ByteBigArrays.get(byArray, l3) && (ByteBigArrays.get(byArray, j) != ByteBigArrays.get(byArray, l3) || ByteBigArrays.get(byArray2, j) >= ByteBigArrays.get(byArray2, l3))) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            byte by = ByteBigArrays.get(byArray, i);
            ByteBigArrays.set(byArray, i, ByteBigArrays.get(byArray, l3));
            ByteBigArrays.set(byArray, l3, by);
            by = ByteBigArrays.get(byArray2, i);
            ByteBigArrays.set(byArray2, i, ByteBigArrays.get(byArray2, l3));
            ByteBigArrays.set(byArray2, l3, by);
        }
    }

    public static void radixSort(byte[][] byArray, byte[][] byArray2) {
        ByteBigArrays.radixSort(byArray, byArray2, 0L, ByteBigArrays.length(byArray));
    }

    public static void radixSort(byte[][] byArray, byte[][] byArray2, long l, long l2) {
        int n = 2;
        if (ByteBigArrays.length(byArray) != ByteBigArrays.length(byArray2)) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        boolean bl = true;
        int n2 = 256;
        long[] lArray = new long[256];
        int n3 = 0;
        long[] lArray2 = new long[256];
        int n4 = 0;
        int[] nArray = new int[256];
        int n5 = 0;
        lArray[n3++] = l;
        lArray2[n4++] = l2 - l;
        nArray[n5++] = 0;
        long[] lArray3 = new long[256];
        long[] lArray4 = new long[256];
        byte[][] byArray3 = ByteBigArrays.newBigArray(l2 - l);
        while (n3 > 0) {
            int n6;
            int n7;
            long l3 = lArray[--n3];
            long l4 = lArray2[--n4];
            int n8 = n7 = (n6 = nArray[--n5]) % 1 == 0 ? 128 : 0;
            if (l4 < 40L) {
                ByteBigArrays.selectionSort(byArray, byArray2, l3, l3 + l4);
                continue;
            }
            byte[][] byArray4 = n6 < 1 ? byArray : byArray2;
            int n9 = (0 - n6 % 1) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray3, l5, (byte)(ByteBigArrays.get(byArray4, l3 + l5) >>> n9 & 0xFF ^ n7));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n10 = ByteBigArrays.get(byArray3, l5) & 0xFF;
                lArray3[n10] = lArray3[n10] + 1L;
            }
            int n11 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray3[i] != 0L) {
                    n11 = i;
                    if (n6 < 1 && lArray3[i] > 1L) {
                        lArray[n3++] = l6 + l3;
                        lArray2[n4++] = lArray3[i];
                        nArray[n5++] = n6 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n11];
            lArray3[n11] = 0L;
            int n12 = -1;
            for (long i = 0L; i < l7; i += lArray3[n12]) {
                byte by = ByteBigArrays.get(byArray, i + l3);
                byte by2 = ByteBigArrays.get(byArray2, i + l3);
                n12 = ByteBigArrays.get(byArray3, i) & 0xFF;
                while (true) {
                    int n13 = n12;
                    long l8 = lArray4[n13] - 1L;
                    lArray4[n13] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    byte by3 = by;
                    int n14 = n12;
                    by = ByteBigArrays.get(byArray, l9 + l3);
                    ByteBigArrays.set(byArray, l9 + l3, by3);
                    by3 = by2;
                    by2 = ByteBigArrays.get(byArray2, l9 + l3);
                    ByteBigArrays.set(byArray2, l9 + l3, by3);
                    n12 = ByteBigArrays.get(byArray3, l9) & 0xFF;
                    ByteBigArrays.set(byArray3, l9, (byte)n14);
                }
                ByteBigArrays.set(byArray, i + l3, by);
                ByteBigArrays.set(byArray2, i + l3, by2);
                lArray3[n12] = 0L;
            }
        }
    }

    public static byte[][] shuffle(byte[][] byArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            byte by = ByteBigArrays.get(byArray, l + l3);
            ByteBigArrays.set(byArray, l + l3, ByteBigArrays.get(byArray, l + l4));
            ByteBigArrays.set(byArray, l + l4, by);
        }
        return byArray;
    }

    public static byte[][] shuffle(byte[][] byArray, Random random2) {
        long l = ByteBigArrays.length(byArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            byte by = ByteBigArrays.get(byArray, l);
            ByteBigArrays.set(byArray, l, ByteBigArrays.get(byArray, l2));
            ByteBigArrays.set(byArray, l2, by);
        }
        return byArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<byte[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(byte[][] byArray) {
            return Arrays.deepHashCode((Object[])byArray);
        }

        @Override
        public boolean equals(byte[][] byArray, byte[][] byArray2) {
            return ByteBigArrays.equals(byArray, byArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((byte[][])object, (byte[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((byte[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

