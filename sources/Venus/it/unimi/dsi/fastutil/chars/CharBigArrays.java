/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteBigArrays;
import it.unimi.dsi.fastutil.chars.CharArrays;
import it.unimi.dsi.fastutil.chars.CharComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class CharBigArrays {
    public static final char[][] EMPTY_BIG_ARRAY = new char[0][];
    public static final char[][] DEFAULT_EMPTY_BIG_ARRAY = new char[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 2;

    private CharBigArrays() {
    }

    public static char get(char[][] cArray, long l) {
        return cArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(char[][] cArray, long l, char c) {
        cArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = c;
    }

    public static void swap(char[][] cArray, long l, long l2) {
        char c = cArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        cArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = cArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        cArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = c;
    }

    public static void add(char[][] cArray, long l, char c) {
        char[] cArray2 = cArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        cArray2[n] = (char)(cArray2[n] + c);
    }

    public static void mul(char[][] cArray, long l, char c) {
        char[] cArray2 = cArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        cArray2[n] = (char)(cArray2[n] * c);
    }

    public static void incr(char[][] cArray, long l) {
        char[] cArray2 = cArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        cArray2[n] = (char)(cArray2[n] + '\u0001');
    }

    public static void decr(char[][] cArray, long l) {
        char[] cArray2 = cArray[BigArrays.segment(l)];
        int n = BigArrays.displacement(l);
        cArray2[n] = (char)(cArray2[n] - '\u0001');
    }

    public static long length(char[][] cArray) {
        int n = cArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)cArray[n - 1].length;
    }

    public static void copy(char[][] cArray, long l, char[][] cArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(cArray[n].length - n3, cArray2[n2].length - n4));
                System.arraycopy(cArray[n], n3, cArray2[n2], n4, n5);
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
                System.arraycopy(cArray[n], n7 - n9, cArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(char[][] cArray, long l, char[] cArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(cArray[n3].length - n4, n2);
            System.arraycopy(cArray[n3], n4, cArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(char[] cArray, int n, char[][] cArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(cArray2[n2].length - n3), l2);
            System.arraycopy(cArray, n, cArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static char[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        char[][] cArrayArray = new char[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                cArrayArray[i] = new char[0x8000000];
            }
            cArrayArray[n - 1] = new char[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                cArrayArray[i] = new char[0x8000000];
            }
        }
        return cArrayArray;
    }

    public static char[][] wrap(char[] cArray) {
        if (cArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (cArray.length <= 0x8000000) {
            return new char[][]{cArray};
        }
        char[][] cArray2 = CharBigArrays.newBigArray(cArray.length);
        for (int i = 0; i < cArray2.length; ++i) {
            System.arraycopy(cArray, (int)BigArrays.start(i), cArray2[i], 0, cArray2[i].length);
        }
        return cArray2;
    }

    public static char[][] ensureCapacity(char[][] cArray, long l) {
        return CharBigArrays.ensureCapacity(cArray, l, CharBigArrays.length(cArray));
    }

    public static char[][] forceCapacity(char[][] cArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = cArray.length - (cArray.length == 0 || cArray.length > 0 && cArray[cArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        char[][] cArray2 = (char[][])Arrays.copyOf(cArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                cArray2[i] = new char[0x8000000];
            }
            cArray2[n2 - 1] = new char[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                cArray2[i] = new char[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            CharBigArrays.copy(cArray, (long)n * 0x8000000L, cArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return cArray2;
    }

    public static char[][] ensureCapacity(char[][] cArray, long l, long l2) {
        return l > CharBigArrays.length(cArray) ? CharBigArrays.forceCapacity(cArray, l, l2) : cArray;
    }

    public static char[][] grow(char[][] cArray, long l) {
        long l2 = CharBigArrays.length(cArray);
        return l > l2 ? CharBigArrays.grow(cArray, l, l2) : cArray;
    }

    public static char[][] grow(char[][] cArray, long l, long l2) {
        long l3 = CharBigArrays.length(cArray);
        return l > l3 ? CharBigArrays.ensureCapacity(cArray, Math.max(l3 + (l3 >> 1), l), l2) : cArray;
    }

    public static char[][] trim(char[][] cArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = CharBigArrays.length(cArray);
        if (l >= l2) {
            return cArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        char[][] cArray2 = (char[][])Arrays.copyOf(cArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            cArray2[n - 1] = CharArrays.trim(cArray2[n - 1], n2);
        }
        return cArray2;
    }

    public static char[][] setLength(char[][] cArray, long l) {
        long l2 = CharBigArrays.length(cArray);
        if (l == l2) {
            return cArray;
        }
        if (l < l2) {
            return CharBigArrays.trim(cArray, l);
        }
        return CharBigArrays.ensureCapacity(cArray, l);
    }

    public static char[][] copy(char[][] cArray, long l, long l2) {
        CharBigArrays.ensureOffsetLength(cArray, l, l2);
        char[][] cArray2 = CharBigArrays.newBigArray(l2);
        CharBigArrays.copy(cArray, l, cArray2, 0L, l2);
        return cArray2;
    }

    public static char[][] copy(char[][] cArray) {
        char[][] cArray2 = (char[][])cArray.clone();
        int n = cArray2.length;
        while (n-- != 0) {
            cArray2[n] = (char[])cArray[n].clone();
        }
        return cArray2;
    }

    public static void fill(char[][] cArray, char c) {
        int n = cArray.length;
        while (n-- != 0) {
            Arrays.fill(cArray[n], c);
        }
    }

    public static void fill(char[][] cArray, long l, long l2, char c) {
        long l3 = CharBigArrays.length(cArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(cArray[n], n3, n4, c);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(cArray[n2], 0, n4, c);
        }
        while (--n2 > n) {
            Arrays.fill(cArray[n2], c);
        }
        Arrays.fill(cArray[n], n3, 0x8000000, c);
    }

    public static boolean equals(char[][] cArray, char[][] cArray2) {
        if (CharBigArrays.length(cArray) != CharBigArrays.length(cArray2)) {
            return true;
        }
        int n = cArray.length;
        while (n-- != 0) {
            char[] cArray3 = cArray[n];
            char[] cArray4 = cArray2[n];
            int n2 = cArray3.length;
            while (n2-- != 0) {
                if (cArray3[n2] == cArray4[n2]) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(char[][] cArray) {
        if (cArray == null) {
            return "null";
        }
        long l = CharBigArrays.length(cArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(CharBigArrays.get(cArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(char[][] cArray, long l, long l2) {
        BigArrays.ensureFromTo(CharBigArrays.length(cArray), l, l2);
    }

    public static void ensureOffsetLength(char[][] cArray, long l, long l2) {
        BigArrays.ensureOffsetLength(CharBigArrays.length(cArray), l, l2);
    }

    private static void vecSwap(char[][] cArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            CharBigArrays.swap(cArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(char[][] cArray, long l, long l2, long l3, CharComparator charComparator) {
        int n = charComparator.compare(CharBigArrays.get(cArray, l), CharBigArrays.get(cArray, l2));
        int n2 = charComparator.compare(CharBigArrays.get(cArray, l), CharBigArrays.get(cArray, l3));
        int n3 = charComparator.compare(CharBigArrays.get(cArray, l2), CharBigArrays.get(cArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(char[][] cArray, long l, long l2, CharComparator charComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (charComparator.compare(CharBigArrays.get(cArray, j), CharBigArrays.get(cArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            CharBigArrays.swap(cArray, i, l3);
        }
    }

    public static void quickSort(char[][] cArray, long l, long l2, CharComparator charComparator) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            CharBigArrays.selectionSort(cArray, l, l2, charComparator);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = CharBigArrays.med3(cArray, l7, l7 + l9, l7 + 2L * l9, charComparator);
                l6 = CharBigArrays.med3(cArray, l6 - l9, l6, l6 + l9, charComparator);
                l8 = CharBigArrays.med3(cArray, l8 - 2L * l9, l8 - l9, l8, charComparator);
            }
            l6 = CharBigArrays.med3(cArray, l7, l6, l8, charComparator);
        }
        char c = CharBigArrays.get(cArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = charComparator.compare(CharBigArrays.get(cArray, l10), c)) <= 0) {
                if (n == 0) {
                    CharBigArrays.swap(cArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = charComparator.compare(CharBigArrays.get(cArray, l3), c)) >= 0) {
                if (n == 0) {
                    CharBigArrays.swap(cArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            CharBigArrays.swap(cArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        CharBigArrays.vecSwap(cArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        CharBigArrays.vecSwap(cArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            CharBigArrays.quickSort(cArray, l, l + l13, charComparator);
        }
        if ((l13 = l11 - l3) > 1L) {
            CharBigArrays.quickSort(cArray, l12 - l13, l12, charComparator);
        }
    }

    private static long med3(char[][] cArray, long l, long l2, long l3) {
        int n = Character.compare(CharBigArrays.get(cArray, l), CharBigArrays.get(cArray, l2));
        int n2 = Character.compare(CharBigArrays.get(cArray, l), CharBigArrays.get(cArray, l3));
        int n3 = Character.compare(CharBigArrays.get(cArray, l2), CharBigArrays.get(cArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(char[][] cArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (CharBigArrays.get(cArray, j) >= CharBigArrays.get(cArray, l3)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            CharBigArrays.swap(cArray, i, l3);
        }
    }

    public static void quickSort(char[][] cArray, CharComparator charComparator) {
        CharBigArrays.quickSort(cArray, 0L, CharBigArrays.length(cArray), charComparator);
    }

    public static void quickSort(char[][] cArray, long l, long l2) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            CharBigArrays.selectionSort(cArray, l, l2);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = CharBigArrays.med3(cArray, l7, l7 + l9, l7 + 2L * l9);
                l6 = CharBigArrays.med3(cArray, l6 - l9, l6, l6 + l9);
                l8 = CharBigArrays.med3(cArray, l8 - 2L * l9, l8 - l9, l8);
            }
            l6 = CharBigArrays.med3(cArray, l7, l6, l8);
        }
        char c = CharBigArrays.get(cArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = Character.compare(CharBigArrays.get(cArray, l10), c)) <= 0) {
                if (n == 0) {
                    CharBigArrays.swap(cArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = Character.compare(CharBigArrays.get(cArray, l3), c)) >= 0) {
                if (n == 0) {
                    CharBigArrays.swap(cArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            CharBigArrays.swap(cArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        CharBigArrays.vecSwap(cArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        CharBigArrays.vecSwap(cArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            CharBigArrays.quickSort(cArray, l, l + l13);
        }
        if ((l13 = l11 - l3) > 1L) {
            CharBigArrays.quickSort(cArray, l12 - l13, l12);
        }
    }

    public static void quickSort(char[][] cArray) {
        CharBigArrays.quickSort(cArray, 0L, CharBigArrays.length(cArray));
    }

    public static long binarySearch(char[][] cArray, long l, long l2, char c) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            char c2 = CharBigArrays.get(cArray, l3);
            if (c2 < c) {
                l = l3 + 1L;
                continue;
            }
            if (c2 > c) {
                l2 = l3 - 1L;
                continue;
            }
            return l3;
        }
        return -(l + 1L);
    }

    public static long binarySearch(char[][] cArray, char c) {
        return CharBigArrays.binarySearch(cArray, 0L, CharBigArrays.length(cArray), c);
    }

    public static long binarySearch(char[][] cArray, long l, long l2, char c, CharComparator charComparator) {
        --l2;
        while (l <= l2) {
            long l3 = l + l2 >>> 1;
            char c2 = CharBigArrays.get(cArray, l3);
            int n = charComparator.compare(c2, c);
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

    public static long binarySearch(char[][] cArray, char c, CharComparator charComparator) {
        return CharBigArrays.binarySearch(cArray, 0L, CharBigArrays.length(cArray), c, charComparator);
    }

    public static void radixSort(char[][] cArray) {
        CharBigArrays.radixSort(cArray, 0L, CharBigArrays.length(cArray));
    }

    public static void radixSort(char[][] cArray, long l, long l2) {
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
            long l3 = lArray[--n2];
            long l4 = lArray2[--n3];
            int n5 = nArray[--n4];
            boolean bl2 = false;
            if (l4 < 40L) {
                CharBigArrays.selectionSort(cArray, l3, l3 + l4);
                continue;
            }
            int n6 = (1 - n5 % 2) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(CharBigArrays.get(cArray, l3 + l5) >>> n6 & 0xFF ^ 0));
            }
            l5 = l4;
            while (l5-- != 0L) {
                int n7 = ByteBigArrays.get(byArray, l5) & 0xFF;
                lArray3[n7] = lArray3[n7] + 1L;
            }
            int n8 = -1;
            long l6 = 0L;
            for (int i = 0; i < 256; ++i) {
                if (lArray3[i] != 0L) {
                    n8 = i;
                    if (n5 < 1 && lArray3[i] > 1L) {
                        lArray[n2++] = l6 + l3;
                        lArray2[n3++] = lArray3[i];
                        nArray[n4++] = n5 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n8];
            lArray3[n8] = 0L;
            int n9 = -1;
            for (long i = 0L; i < l7; i += lArray3[n9]) {
                char c = CharBigArrays.get(cArray, i + l3);
                n9 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n10 = n9;
                    long l8 = lArray4[n10] - 1L;
                    lArray4[n10] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    char c2 = c;
                    int n11 = n9;
                    c = CharBigArrays.get(cArray, l9 + l3);
                    n9 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    CharBigArrays.set(cArray, l9 + l3, c2);
                    ByteBigArrays.set(byArray, l9, (byte)n11);
                }
                CharBigArrays.set(cArray, i + l3, c);
                lArray3[n9] = 0L;
            }
        }
    }

    private static void selectionSort(char[][] cArray, char[][] cArray2, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (CharBigArrays.get(cArray, j) >= CharBigArrays.get(cArray, l3) && (CharBigArrays.get(cArray, j) != CharBigArrays.get(cArray, l3) || CharBigArrays.get(cArray2, j) >= CharBigArrays.get(cArray2, l3))) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            char c = CharBigArrays.get(cArray, i);
            CharBigArrays.set(cArray, i, CharBigArrays.get(cArray, l3));
            CharBigArrays.set(cArray, l3, c);
            c = CharBigArrays.get(cArray2, i);
            CharBigArrays.set(cArray2, i, CharBigArrays.get(cArray2, l3));
            CharBigArrays.set(cArray2, l3, c);
        }
    }

    public static void radixSort(char[][] cArray, char[][] cArray2) {
        CharBigArrays.radixSort(cArray, cArray2, 0L, CharBigArrays.length(cArray));
    }

    public static void radixSort(char[][] cArray, char[][] cArray2, long l, long l2) {
        int n = 2;
        if (CharBigArrays.length(cArray) != CharBigArrays.length(cArray2)) {
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
            long l3 = lArray[--n4];
            long l4 = lArray2[--n5];
            int n7 = nArray[--n6];
            boolean bl = false;
            if (l4 < 40L) {
                CharBigArrays.selectionSort(cArray, cArray2, l3, l3 + l4);
                continue;
            }
            char[][] cArray3 = n7 < 2 ? cArray : cArray2;
            int n8 = (1 - n7 % 2) * 8;
            long l5 = l4;
            while (l5-- != 0L) {
                ByteBigArrays.set(byArray, l5, (byte)(CharBigArrays.get(cArray3, l3 + l5) >>> n8 & 0xFF ^ 0));
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
                    if (n7 < 3 && lArray3[i] > 1L) {
                        lArray[n4++] = l6 + l3;
                        lArray2[n5++] = lArray3[i];
                        nArray[n6++] = n7 + 1;
                    }
                }
                lArray4[i] = l6 += lArray3[i];
            }
            long l7 = l4 - lArray3[n10];
            lArray3[n10] = 0L;
            int n11 = -1;
            for (long i = 0L; i < l7; i += lArray3[n11]) {
                char c = CharBigArrays.get(cArray, i + l3);
                char c2 = CharBigArrays.get(cArray2, i + l3);
                n11 = ByteBigArrays.get(byArray, i) & 0xFF;
                while (true) {
                    int n12 = n11;
                    long l8 = lArray4[n12] - 1L;
                    lArray4[n12] = l8;
                    long l9 = l8;
                    if (l8 <= i) break;
                    char c3 = c;
                    int n13 = n11;
                    c = CharBigArrays.get(cArray, l9 + l3);
                    CharBigArrays.set(cArray, l9 + l3, c3);
                    c3 = c2;
                    c2 = CharBigArrays.get(cArray2, l9 + l3);
                    CharBigArrays.set(cArray2, l9 + l3, c3);
                    n11 = ByteBigArrays.get(byArray, l9) & 0xFF;
                    ByteBigArrays.set(byArray, l9, (byte)n13);
                }
                CharBigArrays.set(cArray, i + l3, c);
                CharBigArrays.set(cArray2, i + l3, c2);
                lArray3[n11] = 0L;
            }
        }
    }

    public static char[][] shuffle(char[][] cArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            char c = CharBigArrays.get(cArray, l + l3);
            CharBigArrays.set(cArray, l + l3, CharBigArrays.get(cArray, l + l4));
            CharBigArrays.set(cArray, l + l4, c);
        }
        return cArray;
    }

    public static char[][] shuffle(char[][] cArray, Random random2) {
        long l = CharBigArrays.length(cArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            char c = CharBigArrays.get(cArray, l);
            CharBigArrays.set(cArray, l, CharBigArrays.get(cArray, l2));
            CharBigArrays.set(cArray, l2, c);
        }
        return cArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<char[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(char[][] cArray) {
            return Arrays.deepHashCode((Object[])cArray);
        }

        @Override
        public boolean equals(char[][] cArray, char[][] cArray2) {
            return CharBigArrays.equals(cArray, cArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((char[][])object, (char[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((char[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

