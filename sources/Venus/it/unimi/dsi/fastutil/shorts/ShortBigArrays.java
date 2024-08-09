/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.shorts.ShortArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class ShortBigArrays {
    public static final short[][] EMPTY_BIG_ARRAY = new short[0][];
    public static final short[][] DEFAULT_EMPTY_BIG_ARRAY = new short[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 2;

    private ShortBigArrays() {
    }

    public static short get(short[][] sArray, long l) {
        return sArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(short[][] sArray, long l, short s) {
        sArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = s;
    }

    public static void swap(short[][] sArray, long l, long l2) {
        short s = sArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        sArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = sArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        sArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = s;
    }

    public static void add(short[][] sArray, long l, short s) {
        short[] sArray2 = sArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        sArray2[n] = (short)(sArray2[n] + s);
    }

    public static void mul(short[][] sArray, long l, short s) {
        short[] sArray2 = sArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        sArray2[n] = (short)(sArray2[n] * s);
    }

    public static void incr(short[][] sArray, long l) {
        short[] sArray2 = sArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        sArray2[n] = (short)(sArray2[n] + 1);
    }

    public static void decr(short[][] sArray, long l) {
        short[] sArray2 = sArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        sArray2[n] = (short)(sArray2[n] - 1);
    }

    public static long length(short[][] sArray) {
        int n = sArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)sArray[n - 1].length;
    }

    public static void copy(short[][] sArray, long l, short[][] sArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(sArray[n].length - n3, sArray2[n2].length - n4));
                System.arraycopy(sArray[n], n3, sArray2[n2], n4, n5);
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
                System.arraycopy(sArray[n], n7 - n9, sArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(short[][] sArray, long l, short[] sArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(sArray[n3].length - n4, n2);
            System.arraycopy(sArray[n3], n4, sArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(short[] sArray, int n, short[][] sArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(sArray2[n2].length - n3), l2);
            System.arraycopy(sArray, n, sArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static short[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        short[][] sArrayArray = new short[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                sArrayArray[i] = new short[0x8000000];
            }
            sArrayArray[n - 1] = new short[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                sArrayArray[i] = new short[0x8000000];
            }
        }
        return sArrayArray;
    }

    public static short[][] wrap(short[] sArray) {
        if (sArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (sArray.length <= 0x8000000) {
            return new short[][]{sArray};
        }
        short[][] sArray2 = ShortBigArrays.newBigArray(sArray.length);
        for (int i = 0; i < sArray2.length; ++i) {
            System.arraycopy(sArray, (int)BigArrays.start(i), sArray2[i], 0, sArray2[i].length);
        }
        return sArray2;
    }

    public static short[][] ensureCapacity(short[][] sArray, long l) {
        return ShortBigArrays.ensureCapacity(sArray, l, ShortBigArrays.length(sArray));
    }

    public static short[][] forceCapacity(short[][] sArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = sArray.length - (sArray.length == 0 || sArray.length > 0 && sArray[sArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        short[][] sArray2 = (short[][])Arrays.copyOf(sArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                sArray2[i] = new short[0x8000000];
            }
            sArray2[n2 - 1] = new short[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                sArray2[i] = new short[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            ShortBigArrays.copy(sArray, (long)n * 0x8000000L, sArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return sArray2;
    }

    public static short[][] ensureCapacity(short[][] sArray, long l, long l2) {
        return l > ShortBigArrays.length(sArray) ? ShortBigArrays.forceCapacity(sArray, l, l2) : sArray;
    }

    public static short[][] grow(short[][] sArray, long l) {
        long l2 = ShortBigArrays.length(sArray);
        return l > l2 ? ShortBigArrays.grow(sArray, l, l2) : sArray;
    }

    public static short[][] grow(short[][] sArray, long l, long l2) {
        long l3 = ShortBigArrays.length(sArray);
        return l > l3 ? ShortBigArrays.ensureCapacity(sArray, Math.max(l3 + (l3 >> 1), l), l2) : sArray;
    }

    public static short[][] trim(short[][] sArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = ShortBigArrays.length(sArray);
        if (l >= l2) {
            return sArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        short[][] sArray2 = (short[][])Arrays.copyOf(sArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            sArray2[n - 1] = ShortArrays.trim(sArray2[n - 1], n2);
        }
        return sArray2;
    }

    public static short[][] setLength(short[][] sArray, long l) {
        long l2 = ShortBigArrays.length(sArray);
        if (l == l2) {
            return sArray;
        }
        if (l < l2) {
            return ShortBigArrays.trim(sArray, l);
        }
        return ShortBigArrays.ensureCapacity(sArray, l);
    }

    public static short[][] copy(short[][] sArray, long l, long l2) {
        ShortBigArrays.ensureOffsetLength(sArray, l, l2);
        short[][] sArray2 = ShortBigArrays.newBigArray(l2);
        ShortBigArrays.copy(sArray, l, sArray2, 0L, l2);
        return sArray2;
    }

    public static short[][] copy(short[][] sArray) {
        short[][] sArray2 = (short[][])sArray.clone();
        int n = sArray2.length;
        while (n-- != 0) {
            sArray2[n] = (short[])sArray[n].clone();
        }
        return sArray2;
    }

    public static void fill(short[][] sArray, short s) {
        int n = sArray.length;
        while (n-- != 0) {
            Arrays.fill(sArray[n], s);
        }
    }

    public static void fill(short[][] sArray, long l, long l2, short s) {
        long l3 = ShortBigArrays.length(sArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(sArray[n], n3, n4, s);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(sArray[n2], 0, n4, s);
        }
        while (--n2 > n) {
            Arrays.fill(sArray[n2], s);
        }
        Arrays.fill(sArray[n], n3, 0x8000000, s);
    }

    public static boolean equals(short[][] sArray, short[][] sArray2) {
        if (ShortBigArrays.length(sArray) != ShortBigArrays.length(sArray2)) {
            return true;
        }
        int n = sArray.length;
        while (n-- != 0) {
            short[] sArray3 = sArray[n];
            short[] sArray4 = sArray2[n];
            int n2 = sArray3.length;
            while (n2-- != 0) {
                if (sArray3[n2] == sArray4[n2]) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(short[][] sArray) {
        if (sArray == null) {
            return "null";
        }
        long l = ShortBigArrays.length(sArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(ShortBigArrays.get(sArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(short[][] sArray, long l, long l2) {
        BigArrays.ensureFromTo(ShortBigArrays.length(sArray), l, l2);
    }

    public static void ensureOffsetLength(short[][] sArray, long l, long l2) {
        BigArrays.ensureOffsetLength(ShortBigArrays.length(sArray), l, l2);
    }

    private static void vecSwap(short[][] sArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            ShortBigArrays.swap(sArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(short[][] sArray, long l, long l2, long l3, ShortComparator shortComparator) {
        int n = shortComparator.compare(ShortBigArrays.get(sArray, l), ShortBigArrays.get(sArray, l2));
        int n2 = shortComparator.compare(ShortBigArrays.get(sArray, l), ShortBigArrays.get(sArray, l3));
        int n3 = shortComparator.compare(ShortBigArrays.get(sArray, l2), ShortBigArrays.get(sArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(short[][] sArray, long l, long l2, ShortComparator shortComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (shortComparator.compare(ShortBigArrays.get(sArray, j), ShortBigArrays.get(sArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            ShortBigArrays.swap(sArray, i, l3);
        }
    }

    public static void quickSort(short[][] sArray, long l, long l2, ShortComparator shortComparator) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            ShortBigArrays.selectionSort(sArray, l, l2, shortComparator);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = ShortBigArrays.med3(sArray, l7, l7 + l9, l7 + 2L * l9, shortComparator);
                l6 = ShortBigArrays.med3(sArray, l6 - l9, l6, l6 + l9, shortComparator);
                l8 = ShortBigArrays.med3(sArray, l8 - 2L * l9, l8 - l9, l8, shortComparator);
            }
            l6 = ShortBigArrays.med3(sArray, l7, l6, l8, shortComparator);
        }
        short s = ShortBigArrays.get(sArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = shortComparator.compare(ShortBigArrays.get(sArray, l10), s)) <= 0) {
                if (n == 0) {
                    ShortBigArrays.swap(sArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = shortComparator.compare(ShortBigArrays.get(sArray, l3), s)) >= 0) {
                if (n == 0) {
                    ShortBigArrays.swap(sArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            ShortBigArrays.swap(sArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        ShortBigArrays.vecSwap(sArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        ShortBigArrays.vecSwap(sArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            ShortBigArrays.quickSort(sArray, l, l + l13, shortComparator);
        }
        if ((l13 = l11 - l3) > 1L) {
            ShortBigArrays.quickSort(sArray, l12 - l13, l12, shortComparator);
        }
    }

    private static long med3(short[][] sArray, long l, long l2, long l3) {
        int n = Short.compare(ShortBigArrays.get(sArray, l), ShortBigArrays.get(sArray, l2));
        int n2 = Short.compare(ShortBigArrays.get(sArray, l), ShortBigArrays.get(sArray, l3));
        int n3 = Short.compare(ShortBigArrays.get(sArray, l2), ShortBigArrays.get(sArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(short[][] sArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (ShortBigArrays.get(sArray, j) >= ShortBigArrays.get(sArray, l3)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            ShortBigArrays.swap(sArray, i, l3);
        }
    }

    public static void quickSort(short[][] sArray, ShortComparator shortComparator) {
        ShortBigArrays.quickSort(sArray, 0L, ShortBigArrays.length(sArray), shortComparator);
    }

    public static void quickSort(short[][] sArray, long l, long l2) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            ShortBigArrays.selectionSort(sArray, l, l2);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = ShortBigArrays.med3(sArray, l7, l7 + l9, l7 + 2L * l9);
                l6 = ShortBigArrays.med3(sArray, l6 - l9, l6, l6 + l9);
                l8 = ShortBigArrays.med3(sArray, l8 - 2L * l9, l8 - l9, l8);
            }
            l6 = ShortBigArrays.med3(sArray, l7, l6, l8);
        }
        short s = ShortBigArrays.get(sArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = Short.compare(ShortBigArrays.get(sArray, l10), s)) <= 0) {
                if (n == 0) {
                    ShortBigArrays.swap(sArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = Short.compare(ShortBigArrays.get(sArray, l3), s)) >= 0) {
                if (n == 0) {
                    ShortBigArrays.swap(sArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            ShortBigArrays.swap(sArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        ShortBigArrays.vecSwap(sArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        ShortBigArrays.vecSwap(sArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            ShortBigArrays.quickSort(sArray, l, l + l13);
        }
        if ((l13 = l11 - l3) > 1L) {
            ShortBigArrays.quickSort(sArray, l12 - l13, l12);
        }
    }

    public static void quickSort(short[][] sArray) {
        ShortBigArrays.quickSort(sArray, 0L, ShortBigArrays.length(sArray));
    }

    public static long binarySearch(short[][] sArray, long l, long l2, short s) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            short s2 = ShortBigArrays.get(sArray, l3);
            if (s2 < s) {
                l = l3 + 1L;
                continue;
            }
            if (s2 > s) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(short[][] sArray, short s) {
        return ShortBigArrays.binarySearch(sArray, 0L, ShortBigArrays.length(sArray), s);
    }

    public static long binarySearch(short[][] sArray, long l, long l2, short s, ShortComparator shortComparator) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            short s2 = ShortBigArrays.get(sArray, l3);
            int n = shortComparator.compare(s2, s);
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

    public static long binarySearch(short[][] sArray, short s, ShortComparator shortComparator) {
        return ShortBigArrays.binarySearch(sArray, 0L, ShortBigArrays.length(sArray), s, shortComparator);
    }

    public static void radixSort(short[][] sArray) {
        ShortBigArrays.radixSort(sArray, 0L, ShortBigArrays.length(sArray));
    }

    public static void radixSort(short[][] sArray, long l, long l2) {
        boolean bl = true;
        int n = 256;
        long[] lArray = new long[256];
        int n2 = 0;
        long[] lArray2 = new long[256];
        int n3 = 0;
        int[] nArray = new int[256];
        int n4 = 0;
        lArray[n2++] = l;
        lArray2[n3++] = l2 - l;
        nArray[n4++] = 0;
        long[] lArray3 = new long[256];
        long[] lArray4 = new long[256];
        byte[][] byArray = ByteBigArrays.newBigArray(l2 - l);
        while (n2 > 0) {
            int n5;
            int n6;
            long l3 = lArray[--n2];
            long l4 = lArray2[--n3];
            int n7 = n6 = (n5 = nArray[--n4]) % 2 == 0 ? 128 : 0;
            if (l4 < 40L) {
                ShortBigArrays.selectionSort(sArray, l3, l3 + l4);
                continue;
            }
            int n8 = (1 - n5 % 2) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(ShortBigArrays.get(sArray, l3 + l5) >>> n8 & 0xFF ^ n6));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n9 = ByteBigArrays.get(byArray, l5) & 0xFF;
                lArray3[n9] = lArray3[n9] + 1L;
            }
            int n10 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray3[i] != 0L) {
                    n10 = i;
                    if (n5 < 1 && lArray3[i] > 1L) {
                        lArray[n2++] = l6 + l3;
                        lArray2[n3++] = lArray3[i];
                        nArray[n4++] = n5 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n10];
            lArray3[n10] = 0L;
            int n11 = -1;
            for (long i = 0L; i < l7; i += lArray3[n11]) {
                short s = ShortBigArrays.get(sArray, i + l3);
                n11 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n12 = n11;
                    long l8 = lArray4[n12] - 1L;
                    lArray4[n12] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    short s2 = s;
                    int n13 = n11;
                    s = ShortBigArrays.get(sArray, l9 + l3);
                    n11 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    ShortBigArrays.set(sArray, l9 + l3, s2);
                    ByteBigArrays.set(byArray, l9, (byte)n13);
                }
                ShortBigArrays.set(sArray, i + l3, s);
                lArray3[n11] = 0L;
            }
        }
    }

    private static void selectionSort(short[][] sArray, short[][] sArray2, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (ShortBigArrays.get(sArray, j) >= ShortBigArrays.get(sArray, l3) && (ShortBigArrays.get(sArray, j) != ShortBigArrays.get(sArray, l3) || ShortBigArrays.get(sArray2, j) >= ShortBigArrays.get(sArray2, l3))) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            short s = ShortBigArrays.get(sArray, i);
            ShortBigArrays.set(sArray, i, ShortBigArrays.get(sArray, l3));
            ShortBigArrays.set(sArray, l3, s);
            s = ShortBigArrays.get(sArray2, i);
            ShortBigArrays.set(sArray2, i, ShortBigArrays.get(sArray2, l3));
            ShortBigArrays.set(sArray2, l3, s);
        }
    }

    public static void radixSort(short[][] sArray, short[][] sArray2) {
        ShortBigArrays.radixSort(sArray, sArray2, 0L, ShortBigArrays.length(sArray));
    }

    public static void radixSort(short[][] sArray, short[][] sArray2, long l, long l2) {
        int n = 2;
        if (ShortBigArrays.length(sArray) != ShortBigArrays.length(sArray2)) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n2 = 3;
        int n3 = 766;
        long[] lArray = new long[766];
        int n4 = 0;
        long[] lArray2 = new long[766];
        int n5 = 0;
        int[] nArray = new int[766];
        int n6 = 0;
        lArray[n4++] = l;
        lArray2[n5++] = l2 - l;
        nArray[n6++] = 0;
        long[] lArray3 = new long[256];
        long[] lArray4 = new long[256];
        byte[][] byArray = ByteBigArrays.newBigArray(l2 - l);
        while (n4 > 0) {
            int n7;
            int n8;
            long l3 = lArray[--n4];
            long l4 = lArray2[--n5];
            int n9 = n8 = (n7 = nArray[--n6]) % 2 == 0 ? 128 : 0;
            if (l4 < 40L) {
                ShortBigArrays.selectionSort(sArray, sArray2, l3, l3 + l4);
                continue;
            }
            short[][] sArray3 = n7 < 2 ? sArray : sArray2;
            int n10 = (1 - n7 % 2) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(ShortBigArrays.get(sArray3, l3 + l5) >>> n10 & 0xFF ^ n8));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n11 = ByteBigArrays.get(byArray, l5) & 0xFF;
                lArray3[n11] = lArray3[n11] + 1L;
            }
            int n12 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray3[i] != 0L) {
                    n12 = i;
                    if (n7 < 3 && lArray3[i] > 1L) {
                        lArray[n4++] = l6 + l3;
                        lArray2[n5++] = lArray3[i];
                        nArray[n6++] = n7 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n12];
            lArray3[n12] = 0L;
            int n13 = -1;
            for (long i = 0L; i < l7; i += lArray3[n13]) {
                short s = ShortBigArrays.get(sArray, i + l3);
                short s2 = ShortBigArrays.get(sArray2, i + l3);
                n13 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n14 = n13;
                    long l8 = lArray4[n14] - 1L;
                    lArray4[n14] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    short s3 = s;
                    int n15 = n13;
                    s = ShortBigArrays.get(sArray, l9 + l3);
                    ShortBigArrays.set(sArray, l9 + l3, s3);
                    s3 = s2;
                    s2 = ShortBigArrays.get(sArray2, l9 + l3);
                    ShortBigArrays.set(sArray2, l9 + l3, s3);
                    n13 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    ByteBigArrays.set(byArray, l9, (byte)n15);
                }
                ShortBigArrays.set(sArray, i + l3, s);
                ShortBigArrays.set(sArray2, i + l3, s2);
                lArray3[n13] = 0L;
            }
        }
    }

    public static short[][] shuffle(short[][] sArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            short s = ShortBigArrays.get(sArray, l + l3);
            ShortBigArrays.set(sArray, l + l3, ShortBigArrays.get(sArray, l + l4));
            ShortBigArrays.set(sArray, l + l4, s);
        }
        return sArray;
    }

    public static short[][] shuffle(short[][] sArray, Random random2) {
        long l = ShortBigArrays.length(sArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            short s = ShortBigArrays.get(sArray, l);
            ShortBigArrays.set(sArray, l, ShortBigArrays.get(sArray, l2));
            ShortBigArrays.set(sArray, l2, s);
        }
        return sArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<short[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(short[][] sArray) {
            return Arrays.deepHashCode((Object[])sArray);
        }

        @Override
        public boolean equals(short[][] sArray, short[][] sArray2) {
            return ShortBigArrays.equals(sArray, sArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((short[][])object, (short[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((short[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

