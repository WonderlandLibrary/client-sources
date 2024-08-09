/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class LongBigArrays {
    public static final long[][] EMPTY_BIG_ARRAY = new long[0][];
    public static final long[][] DEFAULT_EMPTY_BIG_ARRAY = new long[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 8;

    private LongBigArrays() {
    }

    public static long get(long[][] lArray, long l) {
        return lArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(long[][] lArray, long l, long l2) {
        lArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = l2;
    }

    public static void swap(long[][] lArray, long l, long l2) {
        long l3 = lArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        lArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = lArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        lArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = l3;
    }

    public static void add(long[][] lArray, long l, long l2) {
        long[] lArray2 = lArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        lArray2[n] = lArray2[n] + l2;
    }

    public static void mul(long[][] lArray, long l, long l2) {
        long[] lArray2 = lArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        lArray2[n] = lArray2[n] * l2;
    }

    public static void incr(long[][] lArray, long l) {
        long[] lArray2 = lArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        lArray2[n] = lArray2[n] + 1L;
    }

    public static void decr(long[][] lArray, long l) {
        long[] lArray2 = lArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        lArray2[n] = lArray2[n] - 1L;
    }

    public static long length(long[][] lArray) {
        int n = lArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)lArray[n - 1].length;
    }

    public static void copy(long[][] lArray, long l, long[][] lArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(lArray[n].length - n3, lArray2[n2].length - n4));
                System.arraycopy(lArray[n], n3, lArray2[n2], n4, n5);
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
                System.arraycopy(lArray[n], n7 - n9, lArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(long[][] lArray, long l, long[] lArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(lArray[n3].length - n4, n2);
            System.arraycopy(lArray[n3], n4, lArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(long[] lArray, int n, long[][] lArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(lArray2[n2].length - n3), l2);
            System.arraycopy(lArray, n, lArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static long[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        long[][] lArrayArray = new long[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                lArrayArray[i] = new long[0x8000000];
            }
            lArrayArray[n - 1] = new long[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                lArrayArray[i] = new long[0x8000000];
            }
        }
        return lArrayArray;
    }

    public static long[][] wrap(long[] lArray) {
        if (lArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (lArray.length <= 0x8000000) {
            return new long[][]{lArray};
        }
        long[][] lArray2 = LongBigArrays.newBigArray(lArray.length);
        for (int i = 0; i < lArray2.length; ++i) {
            System.arraycopy(lArray, (int)BigArrays.start(i), lArray2[i], 0, lArray2[i].length);
        }
        return lArray2;
    }

    public static long[][] ensureCapacity(long[][] lArray, long l) {
        return LongBigArrays.ensureCapacity(lArray, l, LongBigArrays.length(lArray));
    }

    public static long[][] forceCapacity(long[][] lArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = lArray.length - (lArray.length == 0 || lArray.length > 0 && lArray[lArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        long[][] lArray2 = (long[][])Arrays.copyOf(lArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                lArray2[i] = new long[0x8000000];
            }
            lArray2[n2 - 1] = new long[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                lArray2[i] = new long[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            LongBigArrays.copy(lArray, (long)n * 0x8000000L, lArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return lArray2;
    }

    public static long[][] ensureCapacity(long[][] lArray, long l, long l2) {
        return l > LongBigArrays.length(lArray) ? LongBigArrays.forceCapacity(lArray, l, l2) : lArray;
    }

    public static long[][] grow(long[][] lArray, long l) {
        long l2 = LongBigArrays.length(lArray);
        return l > l2 ? LongBigArrays.grow(lArray, l, l2) : lArray;
    }

    public static long[][] grow(long[][] lArray, long l, long l2) {
        long l3 = LongBigArrays.length(lArray);
        return l > l3 ? LongBigArrays.ensureCapacity(lArray, Math.max(l3 + (l3 >> 1), l), l2) : lArray;
    }

    public static long[][] trim(long[][] lArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = LongBigArrays.length(lArray);
        if (l >= l2) {
            return lArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        long[][] lArray2 = (long[][])Arrays.copyOf(lArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            lArray2[n - 1] = LongArrays.trim(lArray2[n - 1], n2);
        }
        return lArray2;
    }

    public static long[][] setLength(long[][] lArray, long l) {
        long l2 = LongBigArrays.length(lArray);
        if (l == l2) {
            return lArray;
        }
        if (l < l2) {
            return LongBigArrays.trim(lArray, l);
        }
        return LongBigArrays.ensureCapacity(lArray, l);
    }

    public static long[][] copy(long[][] lArray, long l, long l2) {
        LongBigArrays.ensureOffsetLength(lArray, l, l2);
        long[][] lArray2 = LongBigArrays.newBigArray(l2);
        LongBigArrays.copy(lArray, l, lArray2, 0L, l2);
        return lArray2;
    }

    public static long[][] copy(long[][] lArray) {
        long[][] lArray2 = (long[][])lArray.clone();
        int n = lArray2.length;
        while (n-- != 0) {
            lArray2[n] = (long[])lArray[n].clone();
        }
        return lArray2;
    }

    public static void fill(long[][] lArray, long l) {
        int n = lArray.length;
        while (n-- != 0) {
            Arrays.fill(lArray[n], l);
        }
    }

    public static void fill(long[][] lArray, long l, long l2, long l3) {
        long l4 = LongBigArrays.length(lArray);
        BigArrays.ensureFromTo(l4, l, l2);
        if (l4 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(lArray[n], n3, n4, l3);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(lArray[n2], 0, n4, l3);
        }
        while (--n2 > n) {
            Arrays.fill(lArray[n2], l3);
        }
        Arrays.fill(lArray[n], n3, 0x8000000, l3);
    }

    public static boolean equals(long[][] lArray, long[][] lArray2) {
        if (LongBigArrays.length(lArray) != LongBigArrays.length(lArray2)) {
            return true;
        }
        int n = lArray.length;
        while (n-- != 0) {
            long[] lArray3 = lArray[n];
            long[] lArray4 = lArray2[n];
            int n2 = lArray3.length;
            while (n2-- != 0) {
                if (lArray3[n2] == lArray4[n2]) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(long[][] lArray) {
        if (lArray == null) {
            return "null";
        }
        long l = LongBigArrays.length(lArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(LongBigArrays.get(lArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(long[][] lArray, long l, long l2) {
        BigArrays.ensureFromTo(LongBigArrays.length(lArray), l, l2);
    }

    public static void ensureOffsetLength(long[][] lArray, long l, long l2) {
        BigArrays.ensureOffsetLength(LongBigArrays.length(lArray), l, l2);
    }

    private static void vecSwap(long[][] lArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            LongBigArrays.swap(lArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(long[][] lArray, long l, long l2, long l3, LongComparator longComparator) {
        int n = longComparator.compare(LongBigArrays.get(lArray, l), LongBigArrays.get(lArray, l2));
        int n2 = longComparator.compare(LongBigArrays.get(lArray, l), LongBigArrays.get(lArray, l3));
        int n3 = longComparator.compare(LongBigArrays.get(lArray, l2), LongBigArrays.get(lArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(long[][] lArray, long l, long l2, LongComparator longComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (longComparator.compare(LongBigArrays.get(lArray, j), LongBigArrays.get(lArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            LongBigArrays.swap(lArray, i, l3);
        }
    }

    public static void quickSort(long[][] lArray, long l, long l2, LongComparator longComparator) {
        long l3;
        long l4;
        long l5;
        long l6;
        long l7 = l2 - l;
        if (l7 < 7L) {
            LongBigArrays.selectionSort(lArray, l, l2, longComparator);
            return;
        }
        long l8 = l + l7 / 2L;
        if (l7 > 7L) {
            l6 = l;
            l5 = l2 - 1L;
            if (l7 > 40L) {
                l4 = l7 / 8L;
                l6 = LongBigArrays.med3(lArray, l6, l6 + l4, l6 + 2L * l4, longComparator);
                l8 = LongBigArrays.med3(lArray, l8 - l4, l8, l8 + l4, longComparator);
                l5 = LongBigArrays.med3(lArray, l5 - 2L * l4, l5 - l4, l5, longComparator);
            }
            l8 = LongBigArrays.med3(lArray, l6, l8, l5, longComparator);
        }
        l6 = LongBigArrays.get(lArray, l8);
        l4 = l5 = l;
        long l9 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l4 <= l3 && (n = longComparator.compare(LongBigArrays.get(lArray, l4), l6)) <= 0) {
                if (n == 0) {
                    LongBigArrays.swap(lArray, l5++, l4);
                }
                ++l4;
                continue;
            }
            while (l3 >= l4 && (n = longComparator.compare(LongBigArrays.get(lArray, l3), l6)) >= 0) {
                if (n == 0) {
                    LongBigArrays.swap(lArray, l3, l9--);
                }
                --l3;
            }
            if (l4 > l3) break;
            LongBigArrays.swap(lArray, l4++, l3--);
        }
        long l10 = l2;
        long l11 = Math.min(l5 - l, l4 - l5);
        LongBigArrays.vecSwap(lArray, l, l4 - l11, l11);
        l11 = Math.min(l9 - l3, l10 - l9 - 1L);
        LongBigArrays.vecSwap(lArray, l4, l10 - l11, l11);
        l11 = l4 - l5;
        if (l11 > 1L) {
            LongBigArrays.quickSort(lArray, l, l + l11, longComparator);
        }
        if ((l11 = l9 - l3) > 1L) {
            LongBigArrays.quickSort(lArray, l10 - l11, l10, longComparator);
        }
    }

    private static long med3(long[][] lArray, long l, long l2, long l3) {
        int n = Long.compare(LongBigArrays.get(lArray, l), LongBigArrays.get(lArray, l2));
        int n2 = Long.compare(LongBigArrays.get(lArray, l), LongBigArrays.get(lArray, l3));
        int n3 = Long.compare(LongBigArrays.get(lArray, l2), LongBigArrays.get(lArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(long[][] lArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (LongBigArrays.get(lArray, j) >= LongBigArrays.get(lArray, l3)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            LongBigArrays.swap(lArray, i, l3);
        }
    }

    public static void quickSort(long[][] lArray, LongComparator longComparator) {
        LongBigArrays.quickSort(lArray, 0L, LongBigArrays.length(lArray), longComparator);
    }

    public static void quickSort(long[][] lArray, long l, long l2) {
        long l3;
        long l4;
        long l5;
        long l6;
        long l7 = l2 - l;
        if (l7 < 7L) {
            LongBigArrays.selectionSort(lArray, l, l2);
            return;
        }
        long l8 = l + l7 / 2L;
        if (l7 > 7L) {
            l6 = l;
            l5 = l2 - 1L;
            if (l7 > 40L) {
                l4 = l7 / 8L;
                l6 = LongBigArrays.med3(lArray, l6, l6 + l4, l6 + 2L * l4);
                l8 = LongBigArrays.med3(lArray, l8 - l4, l8, l8 + l4);
                l5 = LongBigArrays.med3(lArray, l5 - 2L * l4, l5 - l4, l5);
            }
            l8 = LongBigArrays.med3(lArray, l6, l8, l5);
        }
        l6 = LongBigArrays.get(lArray, l8);
        l4 = l5 = l;
        long l9 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l4 <= l3 && (n = Long.compare(LongBigArrays.get(lArray, l4), l6)) <= 0) {
                if (n == 0) {
                    LongBigArrays.swap(lArray, l5++, l4);
                }
                ++l4;
                continue;
            }
            while (l3 >= l4 && (n = Long.compare(LongBigArrays.get(lArray, l3), l6)) >= 0) {
                if (n == 0) {
                    LongBigArrays.swap(lArray, l3, l9--);
                }
                --l3;
            }
            if (l4 > l3) break;
            LongBigArrays.swap(lArray, l4++, l3--);
        }
        long l10 = l2;
        long l11 = Math.min(l5 - l, l4 - l5);
        LongBigArrays.vecSwap(lArray, l, l4 - l11, l11);
        l11 = Math.min(l9 - l3, l10 - l9 - 1L);
        LongBigArrays.vecSwap(lArray, l4, l10 - l11, l11);
        l11 = l4 - l5;
        if (l11 > 1L) {
            LongBigArrays.quickSort(lArray, l, l + l11);
        }
        if ((l11 = l9 - l3) > 1L) {
            LongBigArrays.quickSort(lArray, l10 - l11, l10);
        }
    }

    public static void quickSort(long[][] lArray) {
        LongBigArrays.quickSort(lArray, 0L, LongBigArrays.length(lArray));
    }

    public static long binarySearch(long[][] lArray, long l, long l2, long l3) {
        --l2;
        while (l <= l2) {
            long l4 = l + l2 >>> 1;
            long l5 = LongBigArrays.get(lArray, l4);
            if (l5 < l3) {
                l = l4 + 1L;
                continue;
            }
            if (l5 > l3) {
                l2 = l4 - 1L;
                continue;
            }
            return l4;
        }
        return -(l + 1L);
    }

    public static long binarySearch(long[][] lArray, long l) {
        return LongBigArrays.binarySearch(lArray, 0L, LongBigArrays.length(lArray), l);
    }

    public static long binarySearch(long[][] lArray, long l, long l2, long l3, LongComparator longComparator) {
        --l2;
        while (l <= l2) {
            long l4 = l + l2 >>> 1;
            long l5 = LongBigArrays.get(lArray, l4);
            int n = longComparator.compare(l5, l3);
            if (n < 0) {
                l = l4 + 1L;
                continue;
            }
            if (n > 0) {
                l2 = l4 - 1L;
                continue;
            }
            return l4;
        }
        return -(l + 1L);
    }

    public static long binarySearch(long[][] lArray, long l, LongComparator longComparator) {
        return LongBigArrays.binarySearch(lArray, 0L, LongBigArrays.length(lArray), l, longComparator);
    }

    public static void radixSort(long[][] lArray) {
        LongBigArrays.radixSort(lArray, 0L, LongBigArrays.length(lArray));
    }

    public static void radixSort(long[][] lArray, long l, long l2) {
        int n = 7;
        int n2 = 1786;
        long[] lArray2 = new long[1786];
        int n3 = 0;
        long[] lArray3 = new long[1786];
        int n4 = 0;
        int[] nArray = new int[1786];
        int n5 = 0;
        lArray2[n3++] = l;
        lArray3[n4++] = l2 - l;
        nArray[n5++] = 0;
        long[] lArray4 = new long[256];
        long[] lArray5 = new long[256];
        byte[][] byArray = ByteBigArrays.newBigArray(l2 - l);
        while (n3 > 0) {
            int n6;
            int n7;
            long l3 = lArray2[--n3];
            long l4 = lArray3[--n4];
            int n8 = n7 = (n6 = nArray[--n5]) % 8 == 0 ? 128 : 0;
            if (l4 < 40L) {
                LongBigArrays.selectionSort(lArray, l3, l3 + l4);
                continue;
            }
            int n9 = (7 - n6 % 8) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(LongBigArrays.get(lArray, l3 + l5) >>> n9 & 0xFFL ^ (long)n7));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n10 = ByteBigArrays.get(byArray, l5) & 0xFF;
                lArray4[n10] = lArray4[n10] + 1L;
            }
            int n11 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray4[i] != 0L) {
                    n11 = i;
                    if (n6 < 7 && lArray4[i] > 1L) {
                        lArray2[n3++] = l6 + l3;
                        lArray3[n4++] = lArray4[i];
                        nArray[n5++] = n6 + 1;
                    }
                }
                lArray5[i] = l6 += lArray4[i];
            }
            long l7 = l4 - lArray4[n11];
            lArray4[n11] = 0L;
            int n12 = -1;
            for (long i = 0L; i < l7; i += lArray4[n12]) {
                long l8 = LongBigArrays.get(lArray, i + l3);
                n12 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n13 = n12;
                    long l9 = lArray5[n13] - 1L;
                    lArray5[n13] = l9;
                    long l10 = l9;
                    if (l9 <= i) break;
                    long l11 = l8;
                    int n14 = n12;
                    l8 = LongBigArrays.get(lArray, l10 + l3);
                    n12 = ByteBigArrays.get(byArray, l10) & 0xFF;
                    LongBigArrays.set(lArray, l10 + l3, l11);
                    ByteBigArrays.set(byArray, l10, (byte)n14);
                }
                LongBigArrays.set(lArray, i + l3, l8);
                lArray4[n12] = 0L;
            }
        }
    }

    private static void selectionSort(long[][] lArray, long[][] lArray2, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3;
            long l4 = i;
            for (l3 = i + 1L; l3 < l2; ++l3) {
                if (LongBigArrays.get(lArray, l3) >= LongBigArrays.get(lArray, l4) && (LongBigArrays.get(lArray, l3) != LongBigArrays.get(lArray, l4) || LongBigArrays.get(lArray2, l3) >= LongBigArrays.get(lArray2, l4))) continue;
                l4 = l3;
            }
            if (l4 == i) continue;
            l3 = LongBigArrays.get(lArray, i);
            LongBigArrays.set(lArray, i, LongBigArrays.get(lArray, l4));
            LongBigArrays.set(lArray, l4, l3);
            l3 = LongBigArrays.get(lArray2, i);
            LongBigArrays.set(lArray2, i, LongBigArrays.get(lArray2, l4));
            LongBigArrays.set(lArray2, l4, l3);
        }
    }

    public static void radixSort(long[][] lArray, long[][] lArray2) {
        LongBigArrays.radixSort(lArray, lArray2, 0L, LongBigArrays.length(lArray));
    }

    public static void radixSort(long[][] lArray, long[][] lArray2, long l, long l2) {
        int n = 2;
        if (LongBigArrays.length(lArray) != LongBigArrays.length(lArray2)) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n2 = 15;
        int n3 = 3826;
        long[] lArray3 = new long[3826];
        int n4 = 0;
        long[] lArray4 = new long[3826];
        int n5 = 0;
        int[] nArray = new int[3826];
        int n6 = 0;
        lArray3[n4++] = l;
        lArray4[n5++] = l2 - l;
        nArray[n6++] = 0;
        long[] lArray5 = new long[256];
        long[] lArray6 = new long[256];
        byte[][] byArray = ByteBigArrays.newBigArray(l2 - l);
        while (n4 > 0) {
            int n7;
            int n8;
            long l3 = lArray3[--n4];
            long l4 = lArray4[--n5];
            int n9 = n8 = (n7 = nArray[--n6]) % 8 == 0 ? 128 : 0;
            if (l4 < 40L) {
                LongBigArrays.selectionSort(lArray, lArray2, l3, l3 + l4);
                continue;
            }
            long[][] lArray7 = n7 < 8 ? lArray : lArray2;
            int n10 = (7 - n7 % 8) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(LongBigArrays.get(lArray7, l3 + l5) >>> n10 & 0xFFL ^ (long)n8));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n11 = ByteBigArrays.get(byArray, l5) & 0xFF;
                lArray5[n11] = lArray5[n11] + 1L;
            }
            int n12 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray5[i] != 0L) {
                    n12 = i;
                    if (n7 < 15 && lArray5[i] > 1L) {
                        lArray3[n4++] = l6 + l3;
                        lArray4[n5++] = lArray5[i];
                        nArray[n6++] = n7 + 1;
                    }
                }
                lArray6[i] = l6 += lArray5[i];
            }
            long l7 = l4 - lArray5[n12];
            lArray5[n12] = 0L;
            int n13 = -1;
            for (long i = 0L; i < l7; i += lArray5[n13]) {
                long l8 = LongBigArrays.get(lArray, i + l3);
                long l9 = LongBigArrays.get(lArray2, i + l3);
                n13 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n14 = n13;
                    long l10 = lArray6[n14] - 1L;
                    lArray6[n14] = l10;
                    long l11 = l10;
                    if (l10 <= i) break;
                    long l12 = l8;
                    int n15 = n13;
                    l8 = LongBigArrays.get(lArray, l11 + l3);
                    LongBigArrays.set(lArray, l11 + l3, l12);
                    l12 = l9;
                    l9 = LongBigArrays.get(lArray2, l11 + l3);
                    LongBigArrays.set(lArray2, l11 + l3, l12);
                    n13 = ByteBigArrays.get(byArray, l11) & 0xFF;
                    ByteBigArrays.set(byArray, l11, (byte)n15);
                }
                LongBigArrays.set(lArray, i + l3, l8);
                LongBigArrays.set(lArray2, i + l3, l9);
                lArray5[n13] = 0L;
            }
        }
    }

    public static long[][] shuffle(long[][] lArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            long l5 = LongBigArrays.get(lArray, l + l3);
            LongBigArrays.set(lArray, l + l3, LongBigArrays.get(lArray, l + l4));
            LongBigArrays.set(lArray, l + l4, l5);
        }
        return lArray;
    }

    public static long[][] shuffle(long[][] lArray, Random random2) {
        long l = LongBigArrays.length(lArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            long l3 = LongBigArrays.get(lArray, l);
            LongBigArrays.set(lArray, l, LongBigArrays.get(lArray, l2));
            LongBigArrays.set(lArray, l2, l3);
        }
        return lArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<long[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(long[][] lArray) {
            return Arrays.deepHashCode((Object[])lArray);
        }

        @Override
        public boolean equals(long[][] lArray, long[][] lArray2) {
            return LongBigArrays.equals(lArray, lArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((long[][])object, (long[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((long[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

