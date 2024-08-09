/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.BigSwapper;
import it.unimi.dsi.fastutil.ints.IntBigArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;

public class BigArrays {
    public static final int SEGMENT_SHIFT = 27;
    public static final int SEGMENT_SIZE = 0x8000000;
    public static final int SEGMENT_MASK = 0x7FFFFFF;
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;

    protected BigArrays() {
    }

    public static int segment(long l) {
        return (int)(l >>> 27);
    }

    public static int displacement(long l) {
        return (int)(l & 0x7FFFFFFL);
    }

    public static long start(int n) {
        return (long)n << 27;
    }

    public static long index(int n, int n2) {
        return BigArrays.start(n) + (long)n2;
    }

    public static void ensureFromTo(long l, long l2, long l3) {
        if (l2 < 0L) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + l2 + ") is negative");
        }
        if (l2 > l3) {
            throw new IllegalArgumentException("Start index (" + l2 + ") is greater than end index (" + l3 + ")");
        }
        if (l3 > l) {
            throw new ArrayIndexOutOfBoundsException("End index (" + l3 + ") is greater than big-array length (" + l + ")");
        }
    }

    public static void ensureOffsetLength(long l, long l2, long l3) {
        if (l2 < 0L) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + l2 + ") is negative");
        }
        if (l3 < 0L) {
            throw new IllegalArgumentException("Length (" + l3 + ") is negative");
        }
        if (l2 + l3 > l) {
            throw new ArrayIndexOutOfBoundsException("Last index (" + (l2 + l3) + ") is greater than big-array length (" + l + ")");
        }
    }

    public static void ensureLength(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("Negative big-array size: " + l);
        }
        if (l >= 288230376017494016L) {
            throw new IllegalArgumentException("Big-array size too big: " + l);
        }
    }

    private static void inPlaceMerge(long l, long l2, long l3, LongComparator longComparator, BigSwapper bigSwapper) {
        long l4;
        long l5;
        if (l >= l2 || l2 >= l3) {
            return;
        }
        if (l3 - l == 2L) {
            if (longComparator.compare(l2, l) < 0) {
                bigSwapper.swap(l, l2);
            }
            return;
        }
        if (l2 - l > l3 - l2) {
            l5 = l + (l2 - l) / 2L;
            l4 = BigArrays.lowerBound(l2, l3, l5, longComparator);
        } else {
            l4 = l2 + (l3 - l2) / 2L;
            l5 = BigArrays.upperBound(l, l2, l4, longComparator);
        }
        long l6 = l5;
        long l7 = l2;
        long l8 = l4;
        if (l7 != l6 && l7 != l8) {
            long l9 = l6;
            long l10 = l7;
            while (l9 < --l10) {
                bigSwapper.swap(l9++, l10);
            }
            l9 = l7;
            l10 = l8;
            while (l9 < --l10) {
                bigSwapper.swap(l9++, l10);
            }
            l9 = l6;
            l10 = l8;
            while (l9 < --l10) {
                bigSwapper.swap(l9++, l10);
            }
        }
        l2 = l5 + (l4 - l2);
        BigArrays.inPlaceMerge(l, l5, l2, longComparator, bigSwapper);
        BigArrays.inPlaceMerge(l2, l4, l3, longComparator, bigSwapper);
    }

    private static long lowerBound(long l, long l2, long l3, LongComparator longComparator) {
        long l4 = l2 - l;
        while (l4 > 0L) {
            long l5 = l4 / 2L;
            long l6 = l + l5;
            if (longComparator.compare(l6, l3) < 0) {
                l = l6 + 1L;
                l4 -= l5 + 1L;
                continue;
            }
            l4 = l5;
        }
        return l;
    }

    private static long med3(long l, long l2, long l3, LongComparator longComparator) {
        int n = longComparator.compare(l, l2);
        int n2 = longComparator.compare(l, l3);
        int n3 = longComparator.compare(l2, l3);
        return n < 0 ? (n3 < 0 ? l2 : (n2 < 0 ? l3 : l)) : (n3 > 0 ? l2 : (n2 > 0 ? l3 : l));
    }

    public static void mergeSort(long l, long l2, LongComparator longComparator, BigSwapper bigSwapper) {
        long l3 = l2 - l;
        if (l3 < 7L) {
            for (long i = l; i < l2; ++i) {
                for (long j = i; j > l && longComparator.compare(j - 1L, j) > 0; --j) {
                    bigSwapper.swap(j, j - 1L);
                }
            }
            return;
        }
        long l4 = l + l2 >>> 1;
        BigArrays.mergeSort(l, l4, longComparator, bigSwapper);
        BigArrays.mergeSort(l4, l2, longComparator, bigSwapper);
        if (longComparator.compare(l4 - 1L, l4) <= 0) {
            return;
        }
        BigArrays.inPlaceMerge(l, l4, l2, longComparator, bigSwapper);
    }

    public static void quickSort(long l, long l2, LongComparator longComparator, BigSwapper bigSwapper) {
        long l3;
        long l4;
        long l5;
        long l6 = l2 - l;
        if (l6 < 7L) {
            for (long i = l; i < l2; ++i) {
                for (long j = i; j > l && longComparator.compare(j - 1L, j) > 0; --j) {
                    bigSwapper.swap(j, j - 1L);
                }
            }
            return;
        }
        long l7 = l + l6 / 2L;
        if (l6 > 7L) {
            l5 = l;
            l4 = l2 - 1L;
            if (l6 > 40L) {
                l3 = l6 / 8L;
                l5 = BigArrays.med3(l5, l5 + l3, l5 + 2L * l3, longComparator);
                l7 = BigArrays.med3(l7 - l3, l7, l7 + l3, longComparator);
                l4 = BigArrays.med3(l4 - 2L * l3, l4 - l3, l4, longComparator);
            }
            l7 = BigArrays.med3(l5, l7, l4, longComparator);
        }
        l4 = l5 = l;
        long l8 = l3 = l2 - 1L;
        while (true) {
            int n;
            if (l4 <= l3 && (n = longComparator.compare(l4, l7)) <= 0) {
                if (n == 0) {
                    if (l5 == l7) {
                        l7 = l4;
                    } else if (l4 == l7) {
                        l7 = l5;
                    }
                    bigSwapper.swap(l5++, l4);
                }
                ++l4;
                continue;
            }
            while (l3 >= l4 && (n = longComparator.compare(l3, l7)) >= 0) {
                if (n == 0) {
                    if (l3 == l7) {
                        l7 = l8;
                    } else if (l8 == l7) {
                        l7 = l3;
                    }
                    bigSwapper.swap(l3, l8--);
                }
                --l3;
            }
            if (l4 > l3) break;
            if (l4 == l7) {
                l7 = l8;
            } else if (l3 == l7) {
                l7 = l3;
            }
            bigSwapper.swap(l4++, l3--);
        }
        long l9 = l + l6;
        long l10 = Math.min(l5 - l, l4 - l5);
        BigArrays.vecSwap(bigSwapper, l, l4 - l10, l10);
        l10 = Math.min(l8 - l3, l9 - l8 - 1L);
        BigArrays.vecSwap(bigSwapper, l4, l9 - l10, l10);
        l10 = l4 - l5;
        if (l10 > 1L) {
            BigArrays.quickSort(l, l + l10, longComparator, bigSwapper);
        }
        if ((l10 = l8 - l3) > 1L) {
            BigArrays.quickSort(l9 - l10, l9, longComparator, bigSwapper);
        }
    }

    private static long upperBound(long l, long l2, long l3, LongComparator longComparator) {
        long l4 = l2 - l;
        while (l4 > 0L) {
            long l5 = l4 / 2L;
            long l6 = l + l5;
            if (longComparator.compare(l3, l6) < 0) {
                l4 = l5;
                continue;
            }
            l = l6 + 1L;
            l4 -= l5 + 1L;
        }
        return l;
    }

    private static void vecSwap(BigSwapper bigSwapper, long l, long l2, long l3) {
        int n = 0;
        while ((long)n < l3) {
            bigSwapper.swap(l, l2);
            ++n;
            ++l;
            ++l2;
        }
    }

    public static void main(String[] stringArray) {
        int[][] nArray = IntBigArrays.newBigArray(1L << Integer.parseInt(stringArray[0]));
        int n = 10;
        while (n-- != 0) {
            int n2;
            long l = -System.currentTimeMillis();
            long l2 = 0L;
            long l3 = IntBigArrays.length(nArray);
            while (l3-- != 0L) {
                l2 ^= l3 ^ (long)IntBigArrays.get(nArray, l3);
            }
            if (l2 == 0L) {
                System.err.println();
            }
            System.out.println("Single loop: " + (l + System.currentTimeMillis()) + "ms");
            l = -System.currentTimeMillis();
            long l4 = 0L;
            int n3 = nArray.length;
            while (n3-- != 0) {
                int[] nArray2 = nArray[n3];
                n2 = nArray2.length;
                while (n2-- != 0) {
                    l4 ^= (long)nArray2[n2] ^ BigArrays.index(n3, n2);
                }
            }
            if (l4 == 0L) {
                System.err.println();
            }
            if (l2 != l4) {
                throw new AssertionError();
            }
            System.out.println("Double loop: " + (l + System.currentTimeMillis()) + "ms");
            long l5 = 0L;
            l3 = IntBigArrays.length(nArray);
            n2 = nArray.length;
            while (n2-- != 0) {
                int[] nArray3 = nArray[n2];
                int n4 = nArray3.length;
                while (n4-- != 0) {
                    l4 ^= (long)nArray3[n4] ^ --l3;
                }
            }
            if (l5 == 0L) {
                System.err.println();
            }
            if (l2 != l5) {
                throw new AssertionError();
            }
            System.out.println("Double loop (with additional index): " + (l + System.currentTimeMillis()) + "ms");
        }
    }
}

