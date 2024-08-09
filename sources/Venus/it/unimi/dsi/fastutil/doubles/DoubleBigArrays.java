/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.doubles.DoubleArrays;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class DoubleBigArrays {
    public static final double[][] EMPTY_BIG_ARRAY = new double[0][];
    public static final double[][] DEFAULT_EMPTY_BIG_ARRAY = new double[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 8;

    private DoubleBigArrays() {
    }

    public static double get(double[][] dArray, long l) {
        return dArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(double[][] dArray, long l, double d) {
        dArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = d;
    }

    public static void swap(double[][] dArray, long l, long l2) {
        double d = dArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        dArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = dArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        dArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = d;
    }

    public static void add(double[][] dArray, long l, double d) {
        double[] dArray2 = dArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        dArray2[n] = dArray2[n] + d;
    }

    public static void mul(double[][] dArray, long l, double d) {
        double[] dArray2 = dArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        dArray2[n] = dArray2[n] * d;
    }

    public static void incr(double[][] dArray, long l) {
        double[] dArray2 = dArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        dArray2[n] = dArray2[n] + 1.0;
    }

    public static void decr(double[][] dArray, long l) {
        double[] dArray2 = dArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        dArray2[n] = dArray2[n] - 1.0;
    }

    public static long length(double[][] dArray) {
        int n = dArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)dArray[n - 1].length;
    }

    public static void copy(double[][] dArray, long l, double[][] dArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(dArray[n].length - n3, dArray2[n2].length - n4));
                System.arraycopy(dArray[n], n3, dArray2[n2], n4, n5);
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
                System.arraycopy(dArray[n], n7 - n9, dArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(double[][] dArray, long l, double[] dArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(dArray[n3].length - n4, n2);
            System.arraycopy(dArray[n3], n4, dArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(double[] dArray, int n, double[][] dArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(dArray2[n2].length - n3), l2);
            System.arraycopy(dArray, n, dArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static double[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        double[][] dArrayArray = new double[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                dArrayArray[i] = new double[0x8000000];
            }
            dArrayArray[n - 1] = new double[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                dArrayArray[i] = new double[0x8000000];
            }
        }
        return dArrayArray;
    }

    public static double[][] wrap(double[] dArray) {
        if (dArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (dArray.length <= 0x8000000) {
            return new double[][]{dArray};
        }
        double[][] dArray2 = DoubleBigArrays.newBigArray(dArray.length);
        for (int i = 0; i < dArray2.length; ++i) {
            System.arraycopy(dArray, (int)BigArrays.start(i), dArray2[i], 0, dArray2[i].length);
        }
        return dArray2;
    }

    public static double[][] ensureCapacity(double[][] dArray, long l) {
        return DoubleBigArrays.ensureCapacity(dArray, l, DoubleBigArrays.length(dArray));
    }

    public static double[][] forceCapacity(double[][] dArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = dArray.length - (dArray.length == 0 || dArray.length > 0 && dArray[dArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        double[][] dArray2 = (double[][])Arrays.copyOf(dArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                dArray2[i] = new double[0x8000000];
            }
            dArray2[n2 - 1] = new double[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                dArray2[i] = new double[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            DoubleBigArrays.copy(dArray, (long)n * 0x8000000L, dArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return dArray2;
    }

    public static double[][] ensureCapacity(double[][] dArray, long l, long l2) {
        return l > DoubleBigArrays.length(dArray) ? DoubleBigArrays.forceCapacity(dArray, l, l2) : dArray;
    }

    public static double[][] grow(double[][] dArray, long l) {
        long l2 = DoubleBigArrays.length(dArray);
        return l > l2 ? DoubleBigArrays.grow(dArray, l, l2) : dArray;
    }

    public static double[][] grow(double[][] dArray, long l, long l2) {
        long l3 = DoubleBigArrays.length(dArray);
        return l > l3 ? DoubleBigArrays.ensureCapacity(dArray, Math.max(l3 + (l3 >> 1), l), l2) : dArray;
    }

    public static double[][] trim(double[][] dArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = DoubleBigArrays.length(dArray);
        if (l >= l2) {
            return dArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        double[][] dArray2 = (double[][])Arrays.copyOf(dArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            dArray2[n - 1] = DoubleArrays.trim(dArray2[n - 1], n2);
        }
        return dArray2;
    }

    public static double[][] setLength(double[][] dArray, long l) {
        long l2 = DoubleBigArrays.length(dArray);
        if (l == l2) {
            return dArray;
        }
        if (l < l2) {
            return DoubleBigArrays.trim(dArray, l);
        }
        return DoubleBigArrays.ensureCapacity(dArray, l);
    }

    public static double[][] copy(double[][] dArray, long l, long l2) {
        DoubleBigArrays.ensureOffsetLength(dArray, l, l2);
        double[][] dArray2 = DoubleBigArrays.newBigArray(l2);
        DoubleBigArrays.copy(dArray, l, dArray2, 0L, l2);
        return dArray2;
    }

    public static double[][] copy(double[][] dArray) {
        double[][] dArray2 = (double[][])dArray.clone();
        int n = dArray2.length;
        while (n-- != 0) {
            dArray2[n] = (double[])dArray[n].clone();
        }
        return dArray2;
    }

    public static void fill(double[][] dArray, double d) {
        int n = dArray.length;
        while (n-- != 0) {
            Arrays.fill(dArray[n], d);
        }
    }

    public static void fill(double[][] dArray, long l, long l2, double d) {
        long l3 = DoubleBigArrays.length(dArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(dArray[n], n3, n4, d);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(dArray[n2], 0, n4, d);
        }
        while (--n2 > n) {
            Arrays.fill(dArray[n2], d);
        }
        Arrays.fill(dArray[n], n3, 0x8000000, d);
    }

    public static boolean equals(double[][] dArray, double[][] dArray2) {
        if (DoubleBigArrays.length(dArray) != DoubleBigArrays.length(dArray2)) {
            return true;
        }
        int n = dArray.length;
        while (n-- != 0) {
            double[] dArray3 = dArray[n];
            double[] dArray4 = dArray2[n];
            int n2 = dArray3.length;
            while (n2-- != 0) {
                if (Double.doubleToLongBits(dArray3[n2]) == Double.doubleToLongBits(dArray4[n2])) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(double[][] dArray) {
        if (dArray == null) {
            return "null";
        }
        long l = DoubleBigArrays.length(dArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(DoubleBigArrays.get(dArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(double[][] dArray, long l, long l2) {
        BigArrays.ensureFromTo(DoubleBigArrays.length(dArray), l, l2);
    }

    public static void ensureOffsetLength(double[][] dArray, long l, long l2) {
        BigArrays.ensureOffsetLength(DoubleBigArrays.length(dArray), l, l2);
    }

    private static void vecSwap(double[][] dArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            DoubleBigArrays.swap(dArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(double[][] dArray, long l, long l2, long l3, DoubleComparator doubleComparator) {
        int n = doubleComparator.compare(DoubleBigArrays.get(dArray, l), DoubleBigArrays.get(dArray, l2));
        int n2 = doubleComparator.compare(DoubleBigArrays.get(dArray, l), DoubleBigArrays.get(dArray, l3));
        int n3 = doubleComparator.compare(DoubleBigArrays.get(dArray, l2), DoubleBigArrays.get(dArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(double[][] dArray, long l, long l2, DoubleComparator doubleComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (doubleComparator.compare(DoubleBigArrays.get(dArray, j), DoubleBigArrays.get(dArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            DoubleBigArrays.swap(dArray, i, l3);
        }
    }

    public static void quickSort(double[][] dArray, long l, long l2, DoubleComparator doubleComparator) {
        long l3;
        long l4;
        long l5;
        long l6 = l2 - l;
        if (l6 < 7L) {
            DoubleBigArrays.selectionSort(dArray, l, l2, doubleComparator);
            return;
        }
        long l7 = l + l6 / 2L;
        if (l6 > 7L) {
            long l8 = l;
            l5 = l2 - 1L;
            if (l6 > 40L) {
                l4 = l6 / 8L;
                l8 = DoubleBigArrays.med3(dArray, l8, l8 + l4, l8 + 2L * l4, doubleComparator);
                l7 = DoubleBigArrays.med3(dArray, l7 - l4, l7, l7 + l4, doubleComparator);
                l5 = DoubleBigArrays.med3(dArray, l5 - 2L * l4, l5 - l4, l5, doubleComparator);
            }
            l7 = DoubleBigArrays.med3(dArray, l8, l7, l5, doubleComparator);
        }
        double d = DoubleBigArrays.get(dArray, l7);
        l4 = l5 = l;
        long l9 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l4 <= l3 && (n = doubleComparator.compare(DoubleBigArrays.get(dArray, l4), d)) <= 0) {
                if (n == 0) {
                    DoubleBigArrays.swap(dArray, l5++, l4);
                }
                ++l4;
                continue;
            }
            while (l3 >= l4 && (n = doubleComparator.compare(DoubleBigArrays.get(dArray, l3), d)) >= 0) {
                if (n == 0) {
                    DoubleBigArrays.swap(dArray, l3, l9--);
                }
                --l3;
            }
            if (l4 > l3) break;
            DoubleBigArrays.swap(dArray, l4++, l3--);
        }
        long l10 = l2;
        long l11 = Math.min(l5 - l, l4 - l5);
        DoubleBigArrays.vecSwap(dArray, l, l4 - l11, l11);
        l11 = Math.min(l9 - l3, l10 - l9 - 1L);
        DoubleBigArrays.vecSwap(dArray, l4, l10 - l11, l11);
        l11 = l4 - l5;
        if (l11 > 1L) {
            DoubleBigArrays.quickSort(dArray, l, l + l11, doubleComparator);
        }
        if ((l11 = l9 - l3) > 1L) {
            DoubleBigArrays.quickSort(dArray, l10 - l11, l10, doubleComparator);
        }
    }

    private static long med3(double[][] dArray, long l, long l2, long l3) {
        int n = Double.compare(DoubleBigArrays.get(dArray, l), DoubleBigArrays.get(dArray, l2));
        int n2 = Double.compare(DoubleBigArrays.get(dArray, l), DoubleBigArrays.get(dArray, l3));
        int n3 = Double.compare(DoubleBigArrays.get(dArray, l2), DoubleBigArrays.get(dArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(double[][] dArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (Double.compare(DoubleBigArrays.get(dArray, j), DoubleBigArrays.get(dArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            DoubleBigArrays.swap(dArray, i, l3);
        }
    }

    public static void quickSort(double[][] dArray, DoubleComparator doubleComparator) {
        DoubleBigArrays.quickSort(dArray, 0L, DoubleBigArrays.length(dArray), doubleComparator);
    }

    public static void quickSort(double[][] dArray, long l, long l2) {
        long l3;
        long l4;
        long l5;
        long l6 = l2 - l;
        if (l6 < 7L) {
            DoubleBigArrays.selectionSort(dArray, l, l2);
            return;
        }
        long l7 = l + l6 / 2L;
        if (l6 > 7L) {
            long l8 = l;
            l5 = l2 - 1L;
            if (l6 > 40L) {
                l4 = l6 / 8L;
                l8 = DoubleBigArrays.med3(dArray, l8, l8 + l4, l8 + 2L * l4);
                l7 = DoubleBigArrays.med3(dArray, l7 - l4, l7, l7 + l4);
                l5 = DoubleBigArrays.med3(dArray, l5 - 2L * l4, l5 - l4, l5);
            }
            l7 = DoubleBigArrays.med3(dArray, l8, l7, l5);
        }
        double d = DoubleBigArrays.get(dArray, l7);
        l4 = l5 = l;
        long l9 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l4 <= l3 && (n = Double.compare(DoubleBigArrays.get(dArray, l4), d)) <= 0) {
                if (n == 0) {
                    DoubleBigArrays.swap(dArray, l5++, l4);
                }
                ++l4;
                continue;
            }
            while (l3 >= l4 && (n = Double.compare(DoubleBigArrays.get(dArray, l3), d)) >= 0) {
                if (n == 0) {
                    DoubleBigArrays.swap(dArray, l3, l9--);
                }
                --l3;
            }
            if (l4 > l3) break;
            DoubleBigArrays.swap(dArray, l4++, l3--);
        }
        long l10 = l2;
        long l11 = Math.min(l5 - l, l4 - l5);
        DoubleBigArrays.vecSwap(dArray, l, l4 - l11, l11);
        l11 = Math.min(l9 - l3, l10 - l9 - 1L);
        DoubleBigArrays.vecSwap(dArray, l4, l10 - l11, l11);
        l11 = l4 - l5;
        if (l11 > 1L) {
            DoubleBigArrays.quickSort(dArray, l, l + l11);
        }
        if ((l11 = l9 - l3) > 1L) {
            DoubleBigArrays.quickSort(dArray, l10 - l11, l10);
        }
    }

    public static void quickSort(double[][] dArray) {
        DoubleBigArrays.quickSort(dArray, 0L, DoubleBigArrays.length(dArray));
    }

    public static long binarySearch(double[][] dArray, long l, long l2, double d) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            double d2 = DoubleBigArrays.get(dArray, l3);
            if (d2 < d) {
                l = l3 + 1L;
                continue;
            }
            if (d2 > d) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(double[][] dArray, double d) {
        return DoubleBigArrays.binarySearch(dArray, 0L, DoubleBigArrays.length(dArray), d);
    }

    public static long binarySearch(double[][] dArray, long l, long l2, double d, DoubleComparator doubleComparator) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            double d2 = DoubleBigArrays.get(dArray, l3);
            int n = doubleComparator.compare(d2, d);
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

    public static long binarySearch(double[][] dArray, double d, DoubleComparator doubleComparator) {
        return DoubleBigArrays.binarySearch(dArray, 0L, DoubleBigArrays.length(dArray), d, doubleComparator);
    }

    private static final long fixDouble(double d) {
        long l = Double.doubleToRawLongBits(d);
        return l >= 0L ? l : l ^ Long.MAX_VALUE;
    }

    public static void radixSort(double[][] dArray) {
        DoubleBigArrays.radixSort(dArray, 0L, DoubleBigArrays.length(dArray));
    }

    public static void radixSort(double[][] dArray, long l, long l2) {
        int n = 7;
        int n2 = 1786;
        long[] lArray = new long[1786];
        int n3 = 0;
        long[] lArray2 = new long[1786];
        int n4 = 0;
        int[] nArray = new int[1786];
        int n5 = 0;
        lArray[n3++] = l;
        lArray2[n4++] = l2 - l;
        nArray[n5++] = 0;
        long[] lArray3 = new long[256];
        long[] lArray4 = new long[256];
        byte[][] byArray = ByteBigArrays.newBigArray(l2 - l);
        while (n3 > 0) {
            int n6;
            int n7;
            long l3 = lArray[--n3];
            long l4 = lArray2[--n4];
            int n8 = n7 = (n6 = nArray[--n5]) % 8 == 0 ? 128 : 0;
            if (l4 < 40L) {
                DoubleBigArrays.selectionSort(dArray, l3, l3 + l4);
                continue;
            }
            int n9 = (7 - n6 % 8) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(DoubleBigArrays.fixDouble(DoubleBigArrays.get(dArray, l3 + l5)) >>> n9 & 0xFFL ^ (long)n7));
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
                    if (n6 < 7 && lArray3[i] > 1L) {
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
                double d = DoubleBigArrays.get(dArray, i + l3);
                n12 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n13 = n12;
                    long l8 = lArray4[n13] - 1L;
                    lArray4[n13] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    double d2 = d;
                    int n14 = n12;
                    d = DoubleBigArrays.get(dArray, l9 + l3);
                    n12 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    DoubleBigArrays.set(dArray, l9 + l3, d2);
                    ByteBigArrays.set(byArray, l9, (byte)n14);
                }
                DoubleBigArrays.set(dArray, i + l3, d);
                lArray3[n12] = 0L;
            }
        }
    }

    private static void selectionSort(double[][] dArray, double[][] dArray2, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (Double.compare(DoubleBigArrays.get(dArray, j), DoubleBigArrays.get(dArray, l3)) >= 0 && (Double.compare(DoubleBigArrays.get(dArray, j), DoubleBigArrays.get(dArray, l3)) != 0 || Double.compare(DoubleBigArrays.get(dArray2, j), DoubleBigArrays.get(dArray2, l3)) >= 0)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            double d = DoubleBigArrays.get(dArray, i);
            DoubleBigArrays.set(dArray, i, DoubleBigArrays.get(dArray, l3));
            DoubleBigArrays.set(dArray, l3, d);
            d = DoubleBigArrays.get(dArray2, i);
            DoubleBigArrays.set(dArray2, i, DoubleBigArrays.get(dArray2, l3));
            DoubleBigArrays.set(dArray2, l3, d);
        }
    }

    public static void radixSort(double[][] dArray, double[][] dArray2) {
        DoubleBigArrays.radixSort(dArray, dArray2, 0L, DoubleBigArrays.length(dArray));
    }

    public static void radixSort(double[][] dArray, double[][] dArray2, long l, long l2) {
        int n = 2;
        if (DoubleBigArrays.length(dArray) != DoubleBigArrays.length(dArray2)) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n2 = 15;
        int n3 = 3826;
        long[] lArray = new long[3826];
        int n4 = 0;
        long[] lArray2 = new long[3826];
        int n5 = 0;
        int[] nArray = new int[3826];
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
            int n9 = n8 = (n7 = nArray[--n6]) % 8 == 0 ? 128 : 0;
            if (l4 < 40L) {
                DoubleBigArrays.selectionSort(dArray, dArray2, l3, l3 + l4);
                continue;
            }
            double[][] dArray3 = n7 < 8 ? dArray : dArray2;
            int n10 = (7 - n7 % 8) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(DoubleBigArrays.fixDouble(DoubleBigArrays.get(dArray3, l3 + l5)) >>> n10 & 0xFFL ^ (long)n8));
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
                    if (n7 < 15 && lArray3[i] > 1L) {
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
                double d = DoubleBigArrays.get(dArray, i + l3);
                double d2 = DoubleBigArrays.get(dArray2, i + l3);
                n13 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n14 = n13;
                    long l8 = lArray4[n14] - 1L;
                    lArray4[n14] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    double d3 = d;
                    int n15 = n13;
                    d = DoubleBigArrays.get(dArray, l9 + l3);
                    DoubleBigArrays.set(dArray, l9 + l3, d3);
                    d3 = d2;
                    d2 = DoubleBigArrays.get(dArray2, l9 + l3);
                    DoubleBigArrays.set(dArray2, l9 + l3, d3);
                    n13 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    ByteBigArrays.set(byArray, l9, (byte)n15);
                }
                DoubleBigArrays.set(dArray, i + l3, d);
                DoubleBigArrays.set(dArray2, i + l3, d2);
                lArray3[n13] = 0L;
            }
        }
    }

    public static double[][] shuffle(double[][] dArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            double d = DoubleBigArrays.get(dArray, l + l3);
            DoubleBigArrays.set(dArray, l + l3, DoubleBigArrays.get(dArray, l + l4));
            DoubleBigArrays.set(dArray, l + l4, d);
        }
        return dArray;
    }

    public static double[][] shuffle(double[][] dArray, Random random2) {
        long l = DoubleBigArrays.length(dArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            double d = DoubleBigArrays.get(dArray, l);
            DoubleBigArrays.set(dArray, l, DoubleBigArrays.get(dArray, l2));
            DoubleBigArrays.set(dArray, l2, d);
        }
        return dArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<double[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(double[][] dArray) {
            return Arrays.deepHashCode((Object[])dArray);
        }

        @Override
        public boolean equals(double[][] dArray, double[][] dArray2) {
            return DoubleBigArrays.equals(dArray, dArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((double[][])object, (double[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((double[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

