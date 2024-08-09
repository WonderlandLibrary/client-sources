/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.BigArrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.booleans.BooleanArrays;
import it.unimi.dsi.fastutil.booleans.BooleanComparator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public final class BooleanBigArrays {
    public static final boolean[][] EMPTY_BIG_ARRAY = new boolean[0][];
    public static final boolean[][] DEFAULT_EMPTY_BIG_ARRAY = new boolean[0][];
    public static final Hash.Strategy HASH_STRATEGY = new BigArrayHashStrategy(null);
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;

    private BooleanBigArrays() {
    }

    public static boolean get(boolean[][] blArray, long l) {
        return blArray[BigArrays.segment(l)][BigArrays.displacement(l)];
    }

    public static void set(boolean[][] blArray, long l, boolean bl) {
        blArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = bl;
    }

    public static void swap(boolean[][] blArray, long l, long l2) {
        boolean bl = blArray[BigArrays.segment(l)][BigArrays.displacement(l)];
        blArray[BigArrays.segment((long)l)][BigArrays.displacement((long)l)] = blArray[BigArrays.segment(l2)][BigArrays.displacement(l2)];
        blArray[BigArrays.segment((long)l2)][BigArrays.displacement((long)l2)] = bl;
    }

    public static long length(boolean[][] blArray) {
        int n = blArray.length;
        return n == 0 ? 0L : BigArrays.start(n - 1) + (long)blArray[n - 1].length;
    }

    public static void copy(boolean[][] blArray, long l, boolean[][] blArray2, long l2, long l3) {
        if (l2 <= l) {
            int n = BigArrays.segment(l);
            int n2 = BigArrays.segment(l2);
            int n3 = BigArrays.displacement(l);
            int n4 = BigArrays.displacement(l2);
            while (l3 > 0L) {
                int n5 = (int)Math.min(l3, (long)Math.min(blArray[n].length - n3, blArray2[n2].length - n4));
                System.arraycopy(blArray[n], n3, blArray2[n2], n4, n5);
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
                System.arraycopy(blArray[n], n7 - n9, blArray2[n6], n8 - n9, n9);
                n7 -= n9;
                n8 -= n9;
                l3 -= (long)n9;
            }
        }
    }

    public static void copyFromBig(boolean[][] blArray, long l, boolean[] blArray2, int n, int n2) {
        int n3 = BigArrays.segment(l);
        int n4 = BigArrays.displacement(l);
        while (n2 > 0) {
            int n5 = Math.min(blArray[n3].length - n4, n2);
            System.arraycopy(blArray[n3], n4, blArray2, n, n5);
            if ((n4 += n5) == 0x8000000) {
                n4 = 0;
                ++n3;
            }
            n += n5;
            n2 -= n5;
        }
    }

    public static void copyToBig(boolean[] blArray, int n, boolean[][] blArray2, long l, long l2) {
        int n2 = BigArrays.segment(l);
        int n3 = BigArrays.displacement(l);
        while (l2 > 0L) {
            int n4 = (int)Math.min((long)(blArray2[n2].length - n3), l2);
            System.arraycopy(blArray, n, blArray2[n2], n3, n4);
            if ((n3 += n4) == 0x8000000) {
                n3 = 0;
                ++n2;
            }
            n += n4;
            l2 -= (long)n4;
        }
    }

    public static boolean[][] newBigArray(long l) {
        if (l == 0L) {
            return EMPTY_BIG_ARRAY;
        }
        BigArrays.ensureLength(l);
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        boolean[][] blArrayArray = new boolean[n][];
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            for (int i = 0; i < n - 1; ++i) {
                blArrayArray[i] = new boolean[0x8000000];
            }
            blArrayArray[n - 1] = new boolean[n2];
        } else {
            for (int i = 0; i < n; ++i) {
                blArrayArray[i] = new boolean[0x8000000];
            }
        }
        return blArrayArray;
    }

    public static boolean[][] wrap(boolean[] blArray) {
        if (blArray.length == 0) {
            return EMPTY_BIG_ARRAY;
        }
        if (blArray.length <= 0x8000000) {
            return new boolean[][]{blArray};
        }
        boolean[][] blArray2 = BooleanBigArrays.newBigArray(blArray.length);
        for (int i = 0; i < blArray2.length; ++i) {
            System.arraycopy(blArray, (int)BigArrays.start(i), blArray2[i], 0, blArray2[i].length);
        }
        return blArray2;
    }

    public static boolean[][] ensureCapacity(boolean[][] blArray, long l) {
        return BooleanBigArrays.ensureCapacity(blArray, l, BooleanBigArrays.length(blArray));
    }

    public static boolean[][] forceCapacity(boolean[][] blArray, long l, long l2) {
        BigArrays.ensureLength(l);
        int n = blArray.length - (blArray.length == 0 || blArray.length > 0 && blArray[blArray.length - 1].length == 0x8000000 ? 0 : 1);
        int n2 = (int)(l + 0x7FFFFFFL >>> 27);
        boolean[][] blArray2 = (boolean[][])Arrays.copyOf(blArray, n2);
        int n3 = (int)(l & 0x7FFFFFFL);
        if (n3 != 0) {
            for (int i = n; i < n2 - 1; ++i) {
                blArray2[i] = new boolean[0x8000000];
            }
            blArray2[n2 - 1] = new boolean[n3];
        } else {
            for (int i = n; i < n2; ++i) {
                blArray2[i] = new boolean[0x8000000];
            }
        }
        if (l2 - (long)n * 0x8000000L > 0L) {
            BooleanBigArrays.copy(blArray, (long)n * 0x8000000L, blArray2, (long)n * 0x8000000L, l2 - (long)n * 0x8000000L);
        }
        return blArray2;
    }

    public static boolean[][] ensureCapacity(boolean[][] blArray, long l, long l2) {
        return l > BooleanBigArrays.length(blArray) ? BooleanBigArrays.forceCapacity(blArray, l, l2) : blArray;
    }

    public static boolean[][] grow(boolean[][] blArray, long l) {
        long l2 = BooleanBigArrays.length(blArray);
        return l > l2 ? BooleanBigArrays.grow(blArray, l, l2) : blArray;
    }

    public static boolean[][] grow(boolean[][] blArray, long l, long l2) {
        long l3 = BooleanBigArrays.length(blArray);
        return l > l3 ? BooleanBigArrays.ensureCapacity(blArray, Math.max(l3 + (l3 >> 1), l), l2) : blArray;
    }

    public static boolean[][] trim(boolean[][] blArray, long l) {
        BigArrays.ensureLength(l);
        long l2 = BooleanBigArrays.length(blArray);
        if (l >= l2) {
            return blArray;
        }
        int n = (int)(l + 0x7FFFFFFL >>> 27);
        boolean[][] blArray2 = (boolean[][])Arrays.copyOf(blArray, n);
        int n2 = (int)(l & 0x7FFFFFFL);
        if (n2 != 0) {
            blArray2[n - 1] = BooleanArrays.trim(blArray2[n - 1], n2);
        }
        return blArray2;
    }

    public static boolean[][] setLength(boolean[][] blArray, long l) {
        long l2 = BooleanBigArrays.length(blArray);
        if (l == l2) {
            return blArray;
        }
        if (l < l2) {
            return BooleanBigArrays.trim(blArray, l);
        }
        return BooleanBigArrays.ensureCapacity(blArray, l);
    }

    public static boolean[][] copy(boolean[][] blArray, long l, long l2) {
        BooleanBigArrays.ensureOffsetLength(blArray, l, l2);
        boolean[][] blArray2 = BooleanBigArrays.newBigArray(l2);
        BooleanBigArrays.copy(blArray, l, blArray2, 0L, l2);
        return blArray2;
    }

    public static boolean[][] copy(boolean[][] blArray) {
        boolean[][] blArray2 = (boolean[][])blArray.clone();
        int n = blArray2.length;
        while (n-- != 0) {
            blArray2[n] = (boolean[])blArray[n].clone();
        }
        return blArray2;
    }

    public static void fill(boolean[][] blArray, boolean bl) {
        int n = blArray.length;
        while (n-- != 0) {
            Arrays.fill(blArray[n], bl);
        }
    }

    public static void fill(boolean[][] blArray, long l, long l2, boolean bl) {
        long l3 = BooleanBigArrays.length(blArray);
        BigArrays.ensureFromTo(l3, l, l2);
        if (l3 == 0L) {
            return;
        }
        int n = BigArrays.segment(l);
        int n2 = BigArrays.segment(l2);
        int n3 = BigArrays.displacement(l);
        int n4 = BigArrays.displacement(l2);
        if (n == n2) {
            Arrays.fill(blArray[n], n3, n4, bl);
            return;
        }
        if (n4 != 0) {
            Arrays.fill(blArray[n2], 0, n4, bl);
        }
        while (--n2 > n) {
            Arrays.fill(blArray[n2], bl);
        }
        Arrays.fill(blArray[n], n3, 0x8000000, bl);
    }

    public static boolean equals(boolean[][] blArray, boolean[][] blArray2) {
        if (BooleanBigArrays.length(blArray) != BooleanBigArrays.length(blArray2)) {
            return true;
        }
        int n = blArray.length;
        while (n-- != 0) {
            boolean[] blArray3 = blArray[n];
            boolean[] blArray4 = blArray2[n];
            int n2 = blArray3.length;
            while (n2-- != 0) {
                if (blArray3[n2] == blArray4[n2]) continue;
                return true;
            }
        }
        return false;
    }

    public static String toString(boolean[][] blArray) {
        if (blArray == null) {
            return "null";
        }
        long l = BooleanBigArrays.length(blArray) - 1L;
        if (l == -1L) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        long l2 = 0L;
        while (true) {
            stringBuilder.append(String.valueOf(BooleanBigArrays.get(blArray, l2)));
            if (l2 == l) {
                return stringBuilder.append(']').toString();
            }
            stringBuilder.append(", ");
            ++l2;
        }
    }

    public static void ensureFromTo(boolean[][] blArray, long l, long l2) {
        BigArrays.ensureFromTo(BooleanBigArrays.length(blArray), l, l2);
    }

    public static void ensureOffsetLength(boolean[][] blArray, long l, long l2) {
        BigArrays.ensureOffsetLength(BooleanBigArrays.length(blArray), l, l2);
    }

    private static void vecSwap(boolean[][] blArray, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            BooleanBigArrays.swap(blArray, l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    private static long med3(boolean[][] blArray, long l, long l2, long l3, BooleanComparator booleanComparator) {
        int n = booleanComparator.compare(BooleanBigArrays.get(blArray, l), BooleanBigArrays.get(blArray, l2));
        int n2 = booleanComparator.compare(BooleanBigArrays.get(blArray, l), BooleanBigArrays.get(blArray, l3));
        int n3 = booleanComparator.compare(BooleanBigArrays.get(blArray, l2), BooleanBigArrays.get(blArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(boolean[][] blArray, long l, long l2, BooleanComparator booleanComparator) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (booleanComparator.compare(BooleanBigArrays.get(blArray, j), BooleanBigArrays.get(blArray, l3)) >= 0) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            BooleanBigArrays.swap(blArray, i, l3);
        }
    }

    public static void quickSort(boolean[][] blArray, long l, long l2, BooleanComparator booleanComparator) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            BooleanBigArrays.selectionSort(blArray, l, l2, booleanComparator);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = BooleanBigArrays.med3(blArray, l7, l7 + l9, l7 + 2L * l9, booleanComparator);
                l6 = BooleanBigArrays.med3(blArray, l6 - l9, l6, l6 + l9, booleanComparator);
                l8 = BooleanBigArrays.med3(blArray, l8 - 2L * l9, l8 - l9, l8, booleanComparator);
            }
            l6 = BooleanBigArrays.med3(blArray, l7, l6, l8, booleanComparator);
        }
        boolean bl = BooleanBigArrays.get(blArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = booleanComparator.compare(BooleanBigArrays.get(blArray, l10), bl)) <= 0) {
                if (n == 0) {
                    BooleanBigArrays.swap(blArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = booleanComparator.compare(BooleanBigArrays.get(blArray, l3), bl)) >= 0) {
                if (n == 0) {
                    BooleanBigArrays.swap(blArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            BooleanBigArrays.swap(blArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        BooleanBigArrays.vecSwap(blArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        BooleanBigArrays.vecSwap(blArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            BooleanBigArrays.quickSort(blArray, l, l + l13, booleanComparator);
        }
        if ((l13 = l11 - l3) > 1L) {
            BooleanBigArrays.quickSort(blArray, l12 - l13, l12, booleanComparator);
        }
    }

    private static long med3(boolean[][] blArray, long l, long l2, long l3) {
        int n = Boolean.compare(BooleanBigArrays.get(blArray, l), BooleanBigArrays.get(blArray, l2));
        int n2 = Boolean.compare(BooleanBigArrays.get(blArray, l), BooleanBigArrays.get(blArray, l3));
        int n3 = Boolean.compare(BooleanBigArrays.get(blArray, l2), BooleanBigArrays.get(blArray, l3));
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    private static void selectionSort(boolean[][] blArray, long l, long l2) {
        for (long i = l; i < l2 - 1L; ++i) {
            long l3 = i;
            for (long j = i + 1L; j < l2; ++j) {
                if (BooleanBigArrays.get(blArray, j) || !BooleanBigArrays.get(blArray, l3)) continue;
                l3 = j;
            }
            if (l3 == i) continue;
            BooleanBigArrays.swap(blArray, i, l3);
        }
    }

    public static void quickSort(boolean[][] blArray, BooleanComparator booleanComparator) {
        BooleanBigArrays.quickSort(blArray, 0L, BooleanBigArrays.length(blArray), booleanComparator);
    }

    public static void quickSort(boolean[][] blArray, long l, long l2) {
        long l3;
        long l4;
        long l5 = l2 - l;
        if (l5 < 7L) {
            BooleanBigArrays.selectionSort(blArray, l, l2);
            return;
        }
        long l6 = l + l5 / 2L;
        if (l5 > 7L) {
            long l7 = l;
            long l8 = l2 - 1L;
            if (l5 > 40L) {
                long l9 = l5 / 8L;
                l7 = BooleanBigArrays.med3(blArray, l7, l7 + l9, l7 + 2L * l9);
                l6 = BooleanBigArrays.med3(blArray, l6 - l9, l6, l6 + l9);
                l8 = BooleanBigArrays.med3(blArray, l8 - 2L * l9, l8 - l9, l8);
            }
            l6 = BooleanBigArrays.med3(blArray, l7, l6, l8);
        }
        boolean bl = BooleanBigArrays.get(blArray, l6);
        long l10 = l4 = l;
        long l11 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l10 <= l3 && (n = Boolean.compare(BooleanBigArrays.get(blArray, l10), bl)) <= 0) {
                if (n == 0) {
                    BooleanBigArrays.swap(blArray, l4++, l10);
                }
                ++l10;
                continue;
            }
            while (l3 >= l10 && (n = Boolean.compare(BooleanBigArrays.get(blArray, l3), bl)) >= 0) {
                if (n == 0) {
                    BooleanBigArrays.swap(blArray, l3, l11--);
                }
                --l3;
            }
            if (l10 > l3) break;
            BooleanBigArrays.swap(blArray, l10++, l3--);
        }
        long l12 = l2;
        long l13 = Math.min(l4 - l, l10 - l4);
        BooleanBigArrays.vecSwap(blArray, l, l10 - l13, l13);
        l13 = Math.min(l11 - l3, l12 - l11 - 1L);
        BooleanBigArrays.vecSwap(blArray, l10, l12 - l13, l13);
        l13 = l10 - l4;
        if (l13 > 1L) {
            BooleanBigArrays.quickSort(blArray, l, l + l13);
        }
        if ((l13 = l11 - l3) > 1L) {
            BooleanBigArrays.quickSort(blArray, l12 - l13, l12);
        }
    }

    public static void quickSort(boolean[][] blArray) {
        BooleanBigArrays.quickSort(blArray, 0L, BooleanBigArrays.length(blArray));
    }

    public static boolean[][] shuffle(boolean[][] blArray, long l, long l2, Random random2) {
        long l3 = l2 - l;
        while (l3-- != 0L) {
            long l4 = (random2.nextLong() & Long.MAX_VALUE) % (l3 + 1L);
            boolean bl = BooleanBigArrays.get(blArray, l + l3);
            BooleanBigArrays.set(blArray, l + l3, BooleanBigArrays.get(blArray, l + l4));
            BooleanBigArrays.set(blArray, l + l4, bl);
        }
        return blArray;
    }

    public static boolean[][] shuffle(boolean[][] blArray, Random random2) {
        long l = BooleanBigArrays.length(blArray);
        while (l-- != 0L) {
            long l2 = (random2.nextLong() & Long.MAX_VALUE) % (l + 1L);
            boolean bl = BooleanBigArrays.get(blArray, l);
            BooleanBigArrays.set(blArray, l, BooleanBigArrays.get(blArray, l2));
            BooleanBigArrays.set(blArray, l2, bl);
        }
        return blArray;
    }

    private static final class BigArrayHashStrategy
    implements Hash.Strategy<boolean[][]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private BigArrayHashStrategy() {
        }

        @Override
        public int hashCode(boolean[][] blArray) {
            return Arrays.deepHashCode((Object[])blArray);
        }

        @Override
        public boolean equals(boolean[][] blArray, boolean[][] blArray2) {
            return BooleanBigArrays.equals(blArray, blArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((boolean[][])object, (boolean[][])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((boolean[][])object);
        }

        BigArrayHashStrategy(1 var1_1) {
            this();
        }
    }
}

