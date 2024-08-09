/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class IntBigArrays {
    public static final int[][] EMPTY_BIG_ARRAY = new int[0][];
    public static final int[][] DEFAULT_EMPTY_BIG_ARRAY = new int[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 4;

    private IntBigArrays() {
    }

    public static int get(int[][] nArray, long l) {
        return nArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(int[][] nArray, long l, int n) {
        nArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = n;
    }

    public static void swap(int[][] nArray, long l, long l2) {
        int n = nArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        nArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = nArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        nArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = n;
    }

    public static void add(int[][] nArray, long l, int n) {
        int[] nArray2 = nArray[BigArrays.segment(l)];
        int n2 = BigArrays.displacement(l);
        nArray2[n2] = nArray2[n2] + n;
    }

    public static void mul(int[][] nArray, long l, int n) {
        int[] nArray2 = nArray[BigArrays.segment(l)];
        int n2 = BigArrays.displacement(l);
        nArray2[n2] = nArray2[n2] * n;
    }

    public static void incr(int[][] nArray, long l) {
        int[] nArray2 = nArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        nArray2[n] = nArray2[n] + 1;
    }

    public static void decr(int[][] nArray, long l) {
        int[] nArray2 = nArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        nArray2[n] = nArray2[n] - 1;
    }

    public static long length(int[][] nArray) {
        int n = nArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)nArray[n - 1].length;
    }

    public static void copy(int[][] nArray, long l, int[][] nArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(nArray[n].length - n3, nArray2[n2].length - n4));
                System.arraycopy(nArray[n], n3, nArray2[n2], n4, n5);
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
                System.arraycopy(nArray[n], n7 - n9, nArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(int[][] nArray, long l, int[] nArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(nArray[n3].length - n4, n2);
            System.arraycopy(nArray[n3], n4, nArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(int[] nArray, int n, int[][] nArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(nArray2[n2].length - n3), l2);
            System.arraycopy(nArray, n, nArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static int[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        int[][] nArrayArray = new int[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                nArrayArray[i] = new int[0x8000000];
            }
            nArrayArray[n - 1] = new int[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                nArrayArray[i] = new int[0x8000000];
            }
        }
        return nArrayArray;
    }

    public static int[][] wrap(int[] nArray) {
        if (nArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (nArray.length <= 0x8000000) {
            return new int[][]{nArray};
        }
        int[][] nArray2 = IntBigArrays.newBigArray(nArray.length);
        for (int i = 0; i < nArray2.length; ++i) {
            System.arraycopy(nArray, (int)BigArrays.start(i), nArray2[i], 0, nArray2[i].length);
        }
        return nArray2;
    }

    public static int[][] ensureCapacity(int[][] nArray, long l) {
        return IntBigArrays.ensureCapacity(nArray, l, IntBigArrays.length(nArray));
    }

    public static int[][] forceCapacity(int[][] nArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = nArray.length - (nArray.length == 0 || nArray.length > 0 && nArray[nArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        int[][] nArray2 = (int[][])Arrays.copyOf(nArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                nArray2[i] = new int[0x8000000];
            }
            nArray2[n2 - 1] = new int[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                nArray2[i] = new int[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            IntBigArrays.copy(nArray, (long)n * 0x8000000L, nArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return nArray2;
    }

    public static int[][] ensureCapacity(int[][] nArray, long l, long l2) {
        return l > IntBigArrays.length(nArray) ? IntBigArrays.forceCapacity(nArray, l, l2) : nArray;
    }

    public static int[][] grow(int[][] nArray, long l) {
        long l2 = IntBigArrays.length(nArray);
        return l > l2 ? IntBigArrays.grow(nArray, l, l2) : nArray;
    }

    public static int[][] grow(int[][] nArray, long l, long l2) {
        long l3 = IntBigArrays.length(nArray);
        return l > l3 ? IntBigArrays.ensureCapacity(nArray, Math.max(l3 + (l3 >> 1), l), l2) : nArray;
    }

    public static int[][] trim(int[][] nArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = IntBigArrays.length(nArray);
        if (l >= l2) {
            return nArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        int[][] nArray2 = (int[][])Arrays.copyOf(nArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            nArray2[n - 1] = IntArrays.trim(nArray2[n - 1], n2);
        }
        return nArray2;
    }

    public static int[][] setLength(int[][] nArray, long l) {
        long l2 = IntBigArrays.length(nArray);
        if (l == l2) {
            return nArray;
        }
        if (l < l2) {
            return IntBigArrays.trim(nArray, l);
        }
        return IntBigArrays.ensureCapacity(nArray, l);
    }

    public static int[][] copy(int[][] nArray, long l, long l2) {
        IntBigArrays.ensureOffsetLength(nArray, l, l2);
        int[][] nArray2 = IntBigArrays.newBigArray(l2);
        IntBigArrays.copy(nArray, l, nArray2, 0L, l2);
        return nArray2;
    }

    public static int[][] copy(int[][] nArray) {
        int[][] nArray2 = (int[][])nArray.clone();
        int n = nArray2.length;
        while (n-- != 0) {
            nArray2[n] = (int[])nArray[n].clone();
        }
        return nArray2;
    }

    public static void fill(int[][] nArray, int n) {
        int n2 = nArray.length;
        while (n2-- != 0) {
            Arrays.fill(nArray[n2], n);
        }
    }

    public static void fill(int[][] nArray, long l, long l2, int n) {
        long l3 = IntBigArrays.length(nArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.segment(l2);
        int n4 = BigArrays.displacement(l);
        int n5 = BigArrays.displacement(l2);
        if (n2 == n3) {
            Arrays.fill(nArray[n2], n4, n5, n);
            return;
        }
        if (n5 != 0) {
            Arrays.fill(nArray[n3], 0, n5, n);
        }
        while (--n3 > n2) {
            Arrays.fill(nArray[n3], n);
        }
        Arrays.fill(nArray[n2], n4, 0x8000000, n);
    }

    public static boolean equals(int[][] nArray, int[][] nArray2) {
        if (IntBigArrays.length(nArray) != IntBigArrays.length(nArray2)) {
            return true;
        }
        int n = nArray.length;
        while (n-- != 0) {
            int[] nArray3 = nArray[n];
            int[] nArray4 = nArray2[n];
            int n2 = nArray3.length;
            while (n2-- != 0) {
                if (nArray3[n2] == nArray4[n2]) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(int[][] nArray) {
        if (nArray == null) {
            return "null";
        }
        long l = IntBigArrays.length(nArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(IntBigArrays.get(nArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(int[][] nArray, long l, long l2) {
        BigArrays.ensureFromTo(IntBigArrays.length(nArray), l, l2);
    }

    public static void ensureOffsetLength(int[][] nArray, long l, long l2) {
        BigArrays.ensureOffsetLength(IntBigArrays.length(nArray), l, l2);
    }

    private static void vecSwap(int[][] nArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            IntBigArrays.swap(nArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(int[][] nArray, long l, long l2, long l3, IntComparator intComparator) {
        int n = intComparator.compare(IntBigArrays.get(nArray, l), IntBigArrays.get(nArray, l2));
        int n2 = intComparator.compare(IntBigArrays.get(nArray, l), IntBigArrays.get(nArray, l3));
        int n3 = intComparator.compare(IntBigArrays.get(nArray, l2), IntBigArrays.get(nArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(int[][] nArray, long l, long l2, IntComparator intComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (intComparator.compare(IntBigArrays.get(nArray, j), IntBigArrays.get(nArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            IntBigArrays.swap(nArray, i, l3);
        }
    }

    public static void quickSort(int[][] nArray, long l, long l2, IntComparator intComparator) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            IntBigArrays.selectionSort(nArray, l, l2, intComparator);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = IntBigArrays.med3(nArray, l7, l7 + l9, l7 + 2L * l9, intComparator);
                l6 = IntBigArrays.med3(nArray, l6 - l9, l6, l6 + l9, intComparator);
                l8 = IntBigArrays.med3(nArray, l8 - 2L * l9, l8 - l9, l8, intComparator);
            }
            l6 = IntBigArrays.med3(nArray, l7, l6, l8, intComparator);
        }
        int n = IntBigArrays.get(nArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n2;
            if (l10 <= l3 && (n2 = intComparator.compare(IntBigArrays.get(nArray, l10), n)) <= 0) {
                if (n2 == 0) {
                    IntBigArrays.swap(nArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n2 = intComparator.compare(IntBigArrays.get(nArray, l3), n)) >= 0) {
                if (n2 == 0) {
                    IntBigArrays.swap(nArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            IntBigArrays.swap(nArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        IntBigArrays.vecSwap(nArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        IntBigArrays.vecSwap(nArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            IntBigArrays.quickSort(nArray, l, l + l13, intComparator);
        }
        if ((l13 = l11 - l3) > 1L) {
            IntBigArrays.quickSort(nArray, l12 - l13, l12, intComparator);
        }
    }

    private static long med3(int[][] nArray, long l, long l2, long l3) {
        int n = Integer.compare(IntBigArrays.get(nArray, l), IntBigArrays.get(nArray, l2));
        int n2 = Integer.compare(IntBigArrays.get(nArray, l), IntBigArrays.get(nArray, l3));
        int n3 = Integer.compare(IntBigArrays.get(nArray, l2), IntBigArrays.get(nArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(int[][] nArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (IntBigArrays.get(nArray, j) >= IntBigArrays.get(nArray, l3)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            IntBigArrays.swap(nArray, i, l3);
        }
    }

    public static void quickSort(int[][] nArray, IntComparator intComparator) {
        IntBigArrays.quickSort(nArray, 0L, IntBigArrays.length(nArray), intComparator);
    }

    public static void quickSort(int[][] nArray, long l, long l2) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            IntBigArrays.selectionSort(nArray, l, l2);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = IntBigArrays.med3(nArray, l7, l7 + l9, l7 + 2L * l9);
                l6 = IntBigArrays.med3(nArray, l6 - l9, l6, l6 + l9);
                l8 = IntBigArrays.med3(nArray, l8 - 2L * l9, l8 - l9, l8);
            }
            l6 = IntBigArrays.med3(nArray, l7, l6, l8);
        }
        int n = IntBigArrays.get(nArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n2;
            if (l10 <= l3 && (n2 = Integer.compare(IntBigArrays.get(nArray, l10), n)) <= 0) {
                if (n2 == 0) {
                    IntBigArrays.swap(nArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n2 = Integer.compare(IntBigArrays.get(nArray, l3), n)) >= 0) {
                if (n2 == 0) {
                    IntBigArrays.swap(nArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            IntBigArrays.swap(nArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        IntBigArrays.vecSwap(nArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        IntBigArrays.vecSwap(nArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            IntBigArrays.quickSort(nArray, l, l + l13);
        }
        if ((l13 = l11 - l3) > 1L) {
            IntBigArrays.quickSort(nArray, l12 - l13, l12);
        }
    }

    public static void quickSort(int[][] nArray) {
        IntBigArrays.quickSort(nArray, 0L, IntBigArrays.length(nArray));
    }

    public static long binarySearch(int[][] nArray, long l, long l2, int n) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            int n2 = IntBigArrays.get(nArray, l3);
            if (n2 < n) {
                l = l3 + 1L;
                continue;
            }
            if (n2 > n) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(int[][] nArray, int n) {
        return IntBigArrays.binarySearch(nArray, 0L, IntBigArrays.length(nArray), n);
    }

    public static long binarySearch(int[][] nArray, long l, long l2, int n, IntComparator intComparator) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            int n2 = IntBigArrays.get(nArray, l3);
            int n3 = intComparator.compare(n2, n);
            if (n3 < 0) {
                l = l3 + 1L;
                continue;
            }
            if (n3 > 0) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(int[][] nArray, int n, IntComparator intComparator) {
        return IntBigArrays.binarySearch(nArray, 0L, IntBigArrays.length(nArray), n, intComparator);
    }

    public static void radixSort(int[][] nArray) {
        IntBigArrays.radixSort(nArray, 0L, IntBigArrays.length(nArray));
    }

    public static void radixSort(int[][] nArray, long l, long l2) {
        int n = 3;
        int n2 = 766;
        long[] lArray = new long[766];
        int n3 = 0;
        long[] lArray2 = new long[766];
        int n4 = 0;
        int[] nArray2 = new int[766];
        int n5 = 0;
        lArray[n3++] = l;
        lArray2[n4++] = l2 - l;
        nArray2[n5++] = 0;
        long[] lArray3 = new long[256];
        long[] lArray4 = new long[256];
        byte[][] byArray = ByteBigArrays.newBigArray(l2 - l);
        while (n3 > 0) {
            int n6;
            int n7;
            long l3 = lArray[--n3];
            long l4 = lArray2[--n4];
            int n8 = n7 = (n6 = nArray2[--n5]) % 4 == 0 ? 128 : 0;
            if (l4 < 40L) {
                IntBigArrays.selectionSort(nArray, l3, l3 + l4);
                continue;
            }
            int n9 = (3 - n6 % 4) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(IntBigArrays.get(nArray, l3 + l5) >>> n9 & 0xFF ^ n7));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n10 = ByteBigArrays.get(byArray, l5) & 0xFF;
                lArray3[n10] = lArray3[n10] + 1L;
            }
            int n11 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray3[i] != 0L) {
                    n11 = i;
                    if (n6 < 3 && lArray3[i] > 1L) {
                        lArray[n3++] = l6 + l3;
                        lArray2[n4++] = lArray3[i];
                        nArray2[n5++] = n6 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n11];
            lArray3[n11] = 0L;
            int n12 = -1;
            for (long i = 0L; i < l7; i += lArray3[n12]) {
                int n13 = IntBigArrays.get(nArray, i + l3);
                n12 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n14 = n12;
                    long l8 = lArray4[n14] - 1L;
                    lArray4[n14] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    int n15 = n13;
                    int n16 = n12;
                    n13 = IntBigArrays.get(nArray, l9 + l3);
                    n12 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    IntBigArrays.set(nArray, l9 + l3, n15);
                    ByteBigArrays.set(byArray, l9, (byte)n16);
                }
                IntBigArrays.set(nArray, i + l3, n13);
                lArray3[n12] = 0L;
            }
        }
    }

    private static void selectionSort(int[][] nArray, int[][] nArray2, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (IntBigArrays.get(nArray, j) >= IntBigArrays.get(nArray, l3) && (IntBigArrays.get(nArray, j) != IntBigArrays.get(nArray, l3) || IntBigArrays.get(nArray2, j) >= IntBigArrays.get(nArray2, l3))) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            int n = IntBigArrays.get(nArray, i);
            IntBigArrays.set(nArray, i, IntBigArrays.get(nArray, l3));
            IntBigArrays.set(nArray, l3, n);
            n = IntBigArrays.get(nArray2, i);
            IntBigArrays.set(nArray2, i, IntBigArrays.get(nArray2, l3));
            IntBigArrays.set(nArray2, l3, n);
        }
    }

    public static void radixSort(int[][] nArray, int[][] nArray2) {
        IntBigArrays.radixSort(nArray, nArray2, 0L, IntBigArrays.length(nArray));
    }

    public static void radixSort(int[][] nArray, int[][] nArray2, long l, long l2) {
        int n = 2;
        if (IntBigArrays.length(nArray) != IntBigArrays.length(nArray2)) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n2 = 7;
        int n3 = 1786;
        long[] lArray = new long[1786];
        int n4 = 0;
        long[] lArray2 = new long[1786];
        int n5 = 0;
        int[] nArray3 = new int[1786];
        int n6 = 0;
        lArray[n4++] = l;
        lArray2[n5++] = l2 - l;
        nArray3[n6++] = 0;
        long[] lArray3 = new long[256];
        long[] lArray4 = new long[256];
        byte[][] byArray = ByteBigArrays.newBigArray(l2 - l);
        while (n4 > 0) {
            int n7;
            int n8;
            long l3 = lArray[--n4];
            long l4 = lArray2[--n5];
            int n9 = n8 = (n7 = nArray3[--n6]) % 4 == 0 ? 128 : 0;
            if (l4 < 40L) {
                IntBigArrays.selectionSort(nArray, nArray2, l3, l3 + l4);
                continue;
            }
            int[][] nArray4 = n7 < 4 ? nArray : nArray2;
            int n10 = (3 - n7 % 4) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(IntBigArrays.get(nArray4, l3 + l5) >>> n10 & 0xFF ^ n8));
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
                    if (n7 < 7 && lArray3[i] > 1L) {
                        lArray[n4++] = l6 + l3;
                        lArray2[n5++] = lArray3[i];
                        nArray3[n6++] = n7 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n12];
            lArray3[n12] = 0L;
            int n13 = -1;
            for (long i = 0L; i < l7; i += lArray3[n13]) {
                int n14 = IntBigArrays.get(nArray, i + l3);
                int n15 = IntBigArrays.get(nArray2, i + l3);
                n13 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n16 = n13;
                    long l8 = lArray4[n16] - 1L;
                    lArray4[n16] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    int n17 = n14;
                    int n18 = n13;
                    n14 = IntBigArrays.get(nArray, l9 + l3);
                    IntBigArrays.set(nArray, l9 + l3, n17);
                    n17 = n15;
                    n15 = IntBigArrays.get(nArray2, l9 + l3);
                    IntBigArrays.set(nArray2, l9 + l3, n17);
                    n13 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    ByteBigArrays.set(byArray, l9, (byte)n18);
                }
                IntBigArrays.set(nArray, i + l3, n14);
                IntBigArrays.set(nArray2, i + l3, n15);
                lArray3[n13] = 0L;
            }
        }
    }

    public static int[][] shuffle(int[][] nArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            int n = IntBigArrays.get(nArray, l + l3);
            IntBigArrays.set(nArray, l + l3, IntBigArrays.get(nArray, l + l4));
            IntBigArrays.set(nArray, l + l4, n);
        }
        return nArray;
    }

    public static int[][] shuffle(int[][] nArray, Random random2) {
        long l = IntBigArrays.length(nArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            int n = IntBigArrays.get(nArray, l);
            IntBigArrays.set(nArray, l, IntBigArrays.get(nArray, l2));
            IntBigArrays.set(nArray, l2, n);
        }
        return nArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<int[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(int[][] nArray) {
            return Arrays.deepHashCode((Object[])nArray);
        }

        @Override
        public boolean equals(int[][] nArray, int[][] nArray2) {
            return IntBigArrays.equals(nArray, nArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((int[][])object, (int[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((int[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

