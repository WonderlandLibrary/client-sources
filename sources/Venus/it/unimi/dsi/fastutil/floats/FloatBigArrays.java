/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class FloatBigArrays {
    public static final float[][] EMPTY_BIG_ARRAY = new float[0][];
    public static final float[][] DEFAULT_EMPTY_BIG_ARRAY = new float[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 4;

    private FloatBigArrays() {
    }

    public static float get(float[][] fArray, long l) {
        return fArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(float[][] fArray, long l, float f) {
        fArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = f;
    }

    public static void swap(float[][] fArray, long l, long l2) {
        float f = fArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        fArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = fArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        fArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = f;
    }

    public static void add(float[][] fArray, long l, float f) {
        float[] fArray2 = fArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        fArray2[n] = fArray2[n] + f;
    }

    public static void mul(float[][] fArray, long l, float f) {
        float[] fArray2 = fArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        fArray2[n] = fArray2[n] * f;
    }

    public static void incr(float[][] fArray, long l) {
        float[] fArray2 = fArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        fArray2[n] = fArray2[n] + 1.0f;
    }

    public static void decr(float[][] fArray, long l) {
        float[] fArray2 = fArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        fArray2[n] = fArray2[n] - 1.0f;
    }

    public static long length(float[][] fArray) {
        int n = fArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)fArray[n - 1].length;
    }

    public static void copy(float[][] fArray, long l, float[][] fArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(fArray[n].length - n3, fArray2[n2].length - n4));
                System.arraycopy(fArray[n], n3, fArray2[n2], n4, n5);
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
                System.arraycopy(fArray[n], n7 - n9, fArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(float[][] fArray, long l, float[] fArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(fArray[n3].length - n4, n2);
            System.arraycopy(fArray[n3], n4, fArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(float[] fArray, int n, float[][] fArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(fArray2[n2].length - n3), l2);
            System.arraycopy(fArray, n, fArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static float[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        float[][] fArrayArray = new float[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                fArrayArray[i] = new float[0x8000000];
            }
            fArrayArray[n - 1] = new float[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                fArrayArray[i] = new float[0x8000000];
            }
        }
        return fArrayArray;
    }

    public static float[][] wrap(float[] fArray) {
        if (fArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (fArray.length <= 0x8000000) {
            return new float[][]{fArray};
        }
        float[][] fArray2 = FloatBigArrays.newBigArray(fArray.length);
        for (int i = 0; i < fArray2.length; ++i) {
            System.arraycopy(fArray, (int)BigArrays.start(i), fArray2[i], 0, fArray2[i].length);
        }
        return fArray2;
    }

    public static float[][] ensureCapacity(float[][] fArray, long l) {
        return FloatBigArrays.ensureCapacity(fArray, l, FloatBigArrays.length(fArray));
    }

    public static float[][] forceCapacity(float[][] fArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = fArray.length - (fArray.length == 0 || fArray.length > 0 && fArray[fArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        float[][] fArray2 = (float[][])Arrays.copyOf(fArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                fArray2[i] = new float[0x8000000];
            }
            fArray2[n2 - 1] = new float[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                fArray2[i] = new float[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            FloatBigArrays.copy(fArray, (long)n * 0x8000000L, fArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return fArray2;
    }

    public static float[][] ensureCapacity(float[][] fArray, long l, long l2) {
        return l > FloatBigArrays.length(fArray) ? FloatBigArrays.forceCapacity(fArray, l, l2) : fArray;
    }

    public static float[][] grow(float[][] fArray, long l) {
        long l2 = FloatBigArrays.length(fArray);
        return l > l2 ? FloatBigArrays.grow(fArray, l, l2) : fArray;
    }

    public static float[][] grow(float[][] fArray, long l, long l2) {
        long l3 = FloatBigArrays.length(fArray);
        return l > l3 ? FloatBigArrays.ensureCapacity(fArray, Math.max(l3 + (l3 >> 1), l), l2) : fArray;
    }

    public static float[][] trim(float[][] fArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = FloatBigArrays.length(fArray);
        if (l >= l2) {
            return fArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        float[][] fArray2 = (float[][])Arrays.copyOf(fArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            fArray2[n - 1] = FloatArrays.trim(fArray2[n - 1], n2);
        }
        return fArray2;
    }

    public static float[][] setLength(float[][] fArray, long l) {
        long l2 = FloatBigArrays.length(fArray);
        if (l == l2) {
            return fArray;
        }
        if (l < l2) {
            return FloatBigArrays.trim(fArray, l);
        }
        return FloatBigArrays.ensureCapacity(fArray, l);
    }

    public static float[][] copy(float[][] fArray, long l, long l2) {
        FloatBigArrays.ensureOffsetLength(fArray, l, l2);
        float[][] fArray2 = FloatBigArrays.newBigArray(l2);
        FloatBigArrays.copy(fArray, l, fArray2, 0L, l2);
        return fArray2;
    }

    public static float[][] copy(float[][] fArray) {
        float[][] fArray2 = (float[][])fArray.clone();
        int n = fArray2.length;
        while (n-- != 0) {
            fArray2[n] = (float[])fArray[n].clone();
        }
        return fArray2;
    }

    public static void fill(float[][] fArray, float f) {
        int n = fArray.length;
        while (n-- != 0) {
            Arrays.fill(fArray[n], f);
        }
    }

    public static void fill(float[][] fArray, long l, long l2, float f) {
        long l3 = FloatBigArrays.length(fArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(fArray[n], n3, n4, f);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(fArray[n2], 0, n4, f);
        }
        while (--n2 > n) {
            Arrays.fill(fArray[n2], f);
        }
        Arrays.fill(fArray[n], n3, 0x8000000, f);
    }

    public static boolean equals(float[][] fArray, float[][] fArray2) {
        if (FloatBigArrays.length(fArray) != FloatBigArrays.length(fArray2)) {
            return true;
        }
        int n = fArray.length;
        while (n-- != 0) {
            float[] fArray3 = fArray[n];
            float[] fArray4 = fArray2[n];
            int n2 = fArray3.length;
            while (n2-- != 0) {
                if (Float.floatToIntBits(fArray3[n2]) == Float.floatToIntBits(fArray4[n2])) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(float[][] fArray) {
        if (fArray == null) {
            return "null";
        }
        long l = FloatBigArrays.length(fArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(FloatBigArrays.get(fArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(float[][] fArray, long l, long l2) {
        BigArrays.ensureFromTo(FloatBigArrays.length(fArray), l, l2);
    }

    public static void ensureOffsetLength(float[][] fArray, long l, long l2) {
        BigArrays.ensureOffsetLength(FloatBigArrays.length(fArray), l, l2);
    }

    private static void vecSwap(float[][] fArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            FloatBigArrays.swap(fArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(float[][] fArray, long l, long l2, long l3, FloatComparator floatComparator) {
        int n = floatComparator.compare(FloatBigArrays.get(fArray, l), FloatBigArrays.get(fArray, l2));
        int n2 = floatComparator.compare(FloatBigArrays.get(fArray, l), FloatBigArrays.get(fArray, l3));
        int n3 = floatComparator.compare(FloatBigArrays.get(fArray, l2), FloatBigArrays.get(fArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(float[][] fArray, long l, long l2, FloatComparator floatComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (floatComparator.compare(FloatBigArrays.get(fArray, j), FloatBigArrays.get(fArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            FloatBigArrays.swap(fArray, i, l3);
        }
    }

    public static void quickSort(float[][] fArray, long l, long l2, FloatComparator floatComparator) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            FloatBigArrays.selectionSort(fArray, l, l2, floatComparator);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = FloatBigArrays.med3(fArray, l7, l7 + l9, l7 + 2L * l9, floatComparator);
                l6 = FloatBigArrays.med3(fArray, l6 - l9, l6, l6 + l9, floatComparator);
                l8 = FloatBigArrays.med3(fArray, l8 - 2L * l9, l8 - l9, l8, floatComparator);
            }
            l6 = FloatBigArrays.med3(fArray, l7, l6, l8, floatComparator);
        }
        float f = FloatBigArrays.get(fArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = floatComparator.compare(FloatBigArrays.get(fArray, l10), f)) <= 0) {
                if (n == 0) {
                    FloatBigArrays.swap(fArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = floatComparator.compare(FloatBigArrays.get(fArray, l3), f)) >= 0) {
                if (n == 0) {
                    FloatBigArrays.swap(fArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            FloatBigArrays.swap(fArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        FloatBigArrays.vecSwap(fArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        FloatBigArrays.vecSwap(fArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            FloatBigArrays.quickSort(fArray, l, l + l13, floatComparator);
        }
        if ((l13 = l11 - l3) > 1L) {
            FloatBigArrays.quickSort(fArray, l12 - l13, l12, floatComparator);
        }
    }

    private static long med3(float[][] fArray, long l, long l2, long l3) {
        int n = Float.compare(FloatBigArrays.get(fArray, l), FloatBigArrays.get(fArray, l2));
        int n2 = Float.compare(FloatBigArrays.get(fArray, l), FloatBigArrays.get(fArray, l3));
        int n3 = Float.compare(FloatBigArrays.get(fArray, l2), FloatBigArrays.get(fArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(float[][] fArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (Float.compare(FloatBigArrays.get(fArray, j), FloatBigArrays.get(fArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            FloatBigArrays.swap(fArray, i, l3);
        }
    }

    public static void quickSort(float[][] fArray, FloatComparator floatComparator) {
        FloatBigArrays.quickSort(fArray, 0L, FloatBigArrays.length(fArray), floatComparator);
    }

    public static void quickSort(float[][] fArray, long l, long l2) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            FloatBigArrays.selectionSort(fArray, l, l2);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = FloatBigArrays.med3(fArray, l7, l7 + l9, l7 + 2L * l9);
                l6 = FloatBigArrays.med3(fArray, l6 - l9, l6, l6 + l9);
                l8 = FloatBigArrays.med3(fArray, l8 - 2L * l9, l8 - l9, l8);
            }
            l6 = FloatBigArrays.med3(fArray, l7, l6, l8);
        }
        float f = FloatBigArrays.get(fArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = Float.compare(FloatBigArrays.get(fArray, l10), f)) <= 0) {
                if (n == 0) {
                    FloatBigArrays.swap(fArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = Float.compare(FloatBigArrays.get(fArray, l3), f)) >= 0) {
                if (n == 0) {
                    FloatBigArrays.swap(fArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            FloatBigArrays.swap(fArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        FloatBigArrays.vecSwap(fArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        FloatBigArrays.vecSwap(fArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            FloatBigArrays.quickSort(fArray, l, l + l13);
        }
        if ((l13 = l11 - l3) > 1L) {
            FloatBigArrays.quickSort(fArray, l12 - l13, l12);
        }
    }

    public static void quickSort(float[][] fArray) {
        FloatBigArrays.quickSort(fArray, 0L, FloatBigArrays.length(fArray));
    }

    public static long binarySearch(float[][] fArray, long l, long l2, float f) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            float f2 = FloatBigArrays.get(fArray, l3);
            if (f2 < f) {
                l = l3 + 1L;
                continue;
            }
            if (f2 > f) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(float[][] fArray, float f) {
        return FloatBigArrays.binarySearch(fArray, 0L, FloatBigArrays.length(fArray), f);
    }

    public static long binarySearch(float[][] fArray, long l, long l2, float f, FloatComparator floatComparator) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            float f2 = FloatBigArrays.get(fArray, l3);
            int n = floatComparator.compare(f2, f);
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

    public static long binarySearch(float[][] fArray, float f, FloatComparator floatComparator) {
        return FloatBigArrays.binarySearch(fArray, 0L, FloatBigArrays.length(fArray), f, floatComparator);
    }

    private static final long fixFloat(float f) {
        long l = Float.floatToRawIntBits(f);
        return l >= 0L ? l : l ^ Integer.MAX_VALUE;
    }

    public static void radixSort(float[][] fArray) {
        FloatBigArrays.radixSort(fArray, 0L, FloatBigArrays.length(fArray));
    }

    public static void radixSort(float[][] fArray, long l, long l2) {
        int n = 3;
        int n2 = 766;
        long[] lArray = new long[766];
        int n3 = 0;
        long[] lArray2 = new long[766];
        int n4 = 0;
        int[] nArray = new int[766];
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
            int n8 = n7 = (n6 = nArray[--n5]) % 4 == 0 ? 128 : 0;
            if (l4 < 40L) {
                FloatBigArrays.selectionSort(fArray, l3, l3 + l4);
                continue;
            }
            int n9 = (3 - n6 % 4) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(FloatBigArrays.fixFloat(FloatBigArrays.get(fArray, l3 + l5)) >>> n9 & 0xFFL ^ (long)n7));
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
                        nArray[n5++] = n6 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n11];
            lArray3[n11] = 0L;
            int n12 = -1;
            for (long i = 0L; i < l7; i += lArray3[n12]) {
                float f = FloatBigArrays.get(fArray, i + l3);
                n12 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n13 = n12;
                    long l8 = lArray4[n13] - 1L;
                    lArray4[n13] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    float f2 = f;
                    int n14 = n12;
                    f = FloatBigArrays.get(fArray, l9 + l3);
                    n12 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    FloatBigArrays.set(fArray, l9 + l3, f2);
                    ByteBigArrays.set(byArray, l9, (byte)n14);
                }
                FloatBigArrays.set(fArray, i + l3, f);
                lArray3[n12] = 0L;
            }
        }
    }

    private static void selectionSort(float[][] fArray, float[][] fArray2, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (Float.compare(FloatBigArrays.get(fArray, j), FloatBigArrays.get(fArray, l3)) >= 0 && (Float.compare(FloatBigArrays.get(fArray, j), FloatBigArrays.get(fArray, l3)) != 0 || Float.compare(FloatBigArrays.get(fArray2, j), FloatBigArrays.get(fArray2, l3)) >= 0)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            float f = FloatBigArrays.get(fArray, i);
            FloatBigArrays.set(fArray, i, FloatBigArrays.get(fArray, l3));
            FloatBigArrays.set(fArray, l3, f);
            f = FloatBigArrays.get(fArray2, i);
            FloatBigArrays.set(fArray2, i, FloatBigArrays.get(fArray2, l3));
            FloatBigArrays.set(fArray2, l3, f);
        }
    }

    public static void radixSort(float[][] fArray, float[][] fArray2) {
        FloatBigArrays.radixSort(fArray, fArray2, 0L, FloatBigArrays.length(fArray));
    }

    public static void radixSort(float[][] fArray, float[][] fArray2, long l, long l2) {
        int n = 2;
        if (FloatBigArrays.length(fArray) != FloatBigArrays.length(fArray2)) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n2 = 7;
        int n3 = 1786;
        long[] lArray = new long[1786];
        int n4 = 0;
        long[] lArray2 = new long[1786];
        int n5 = 0;
        int[] nArray = new int[1786];
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
            int n9 = n8 = (n7 = nArray[--n6]) % 4 == 0 ? 128 : 0;
            if (l4 < 40L) {
                FloatBigArrays.selectionSort(fArray, fArray2, l3, l3 + l4);
                continue;
            }
            float[][] fArray3 = n7 < 4 ? fArray : fArray2;
            int n10 = (3 - n7 % 4) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(FloatBigArrays.fixFloat(FloatBigArrays.get(fArray3, l3 + l5)) >>> n10 & 0xFFL ^ (long)n8));
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
                        nArray[n6++] = n7 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n12];
            lArray3[n12] = 0L;
            int n13 = -1;
            for (long i = 0L; i < l7; i += lArray3[n13]) {
                float f = FloatBigArrays.get(fArray, i + l3);
                float f2 = FloatBigArrays.get(fArray2, i + l3);
                n13 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n14 = n13;
                    long l8 = lArray4[n14] - 1L;
                    lArray4[n14] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    float f3 = f;
                    int n15 = n13;
                    f = FloatBigArrays.get(fArray, l9 + l3);
                    FloatBigArrays.set(fArray, l9 + l3, f3);
                    f3 = f2;
                    f2 = FloatBigArrays.get(fArray2, l9 + l3);
                    FloatBigArrays.set(fArray2, l9 + l3, f3);
                    n13 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    ByteBigArrays.set(byArray, l9, (byte)n15);
                }
                FloatBigArrays.set(fArray, i + l3, f);
                FloatBigArrays.set(fArray2, i + l3, f2);
                lArray3[n13] = 0L;
            }
        }
    }

    public static float[][] shuffle(float[][] fArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            float f = FloatBigArrays.get(fArray, l + l3);
            FloatBigArrays.set(fArray, l + l3, FloatBigArrays.get(fArray, l + l4));
            FloatBigArrays.set(fArray, l + l4, f);
        }
        return fArray;
    }

    public static float[][] shuffle(float[][] fArray, Random random2) {
        long l = FloatBigArrays.length(fArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            float f = FloatBigArrays.get(fArray, l);
            FloatBigArrays.set(fArray, l, FloatBigArrays.get(fArray, l2));
            FloatBigArrays.set(fArray, l2, f);
        }
        return fArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<float[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(float[][] fArray) {
            return Arrays.deepHashCode((Object[])fArray);
        }

        @Override
        public boolean equals(float[][] fArray, float[][] fArray2) {
            return FloatBigArrays.equals(fArray, fArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((float[][])object, (float[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((float[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

