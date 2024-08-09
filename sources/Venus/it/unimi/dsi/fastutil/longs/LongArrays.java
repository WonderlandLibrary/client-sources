/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.longs;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.longs.LongComparator;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public final class LongArrays {
    public static final long[] EMPTY_ARRAY = new long[0];
    public static final long[] DEFAULT_EMPTY_ARRAY = new long[0];
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 8;
    private static final int RADIXSORT_NO_REC = 1024;
    private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
    protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
    public static final Hash.Strategy<long[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private LongArrays() {
    }

    public static long[] forceCapacity(long[] lArray, int n, int n2) {
        long[] lArray2 = new long[n];
        System.arraycopy(lArray, 0, lArray2, 0, n2);
        return lArray2;
    }

    public static long[] ensureCapacity(long[] lArray, int n) {
        return LongArrays.ensureCapacity(lArray, n, lArray.length);
    }

    public static long[] ensureCapacity(long[] lArray, int n, int n2) {
        return n > lArray.length ? LongArrays.forceCapacity(lArray, n, n2) : lArray;
    }

    public static long[] grow(long[] lArray, int n) {
        return LongArrays.grow(lArray, n, lArray.length);
    }

    public static long[] grow(long[] lArray, int n, int n2) {
        if (n > lArray.length) {
            int n3 = (int)Math.max(Math.min((long)lArray.length + (long)(lArray.length >> 1), 0x7FFFFFF7L), (long)n);
            long[] lArray2 = new long[n3];
            System.arraycopy(lArray, 0, lArray2, 0, n2);
            return lArray2;
        }
        return lArray;
    }

    public static long[] trim(long[] lArray, int n) {
        if (n >= lArray.length) {
            return lArray;
        }
        long[] lArray2 = n == 0 ? EMPTY_ARRAY : new long[n];
        System.arraycopy(lArray, 0, lArray2, 0, n);
        return lArray2;
    }

    public static long[] setLength(long[] lArray, int n) {
        if (n == lArray.length) {
            return lArray;
        }
        if (n < lArray.length) {
            return LongArrays.trim(lArray, n);
        }
        return LongArrays.ensureCapacity(lArray, n);
    }

    public static long[] copy(long[] lArray, int n, int n2) {
        LongArrays.ensureOffsetLength(lArray, n, n2);
        long[] lArray2 = n2 == 0 ? EMPTY_ARRAY : new long[n2];
        System.arraycopy(lArray, n, lArray2, 0, n2);
        return lArray2;
    }

    public static long[] copy(long[] lArray) {
        return (long[])lArray.clone();
    }

    @Deprecated
    public static void fill(long[] lArray, long l) {
        int n = lArray.length;
        while (n-- != 0) {
            lArray[n] = l;
        }
    }

    @Deprecated
    public static void fill(long[] lArray, int n, int n2, long l) {
        LongArrays.ensureFromTo(lArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                lArray[n2] = l;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                lArray[i] = l;
            }
        }
    }

    @Deprecated
    public static boolean equals(long[] lArray, long[] lArray2) {
        int n = lArray.length;
        if (n != lArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (lArray[n] == lArray2[n]) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(long[] lArray, int n, int n2) {
        Arrays.ensureFromTo(lArray.length, n, n2);
    }

    public static void ensureOffsetLength(long[] lArray, int n, int n2) {
        Arrays.ensureOffsetLength(lArray.length, n, n2);
    }

    public static void ensureSameLength(long[] lArray, long[] lArray2) {
        if (lArray.length != lArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + lArray.length + " != " + lArray2.length);
        }
    }

    public static void swap(long[] lArray, int n, int n2) {
        long l = lArray[n];
        lArray[n] = lArray[n2];
        lArray[n2] = l;
    }

    public static void swap(long[] lArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            LongArrays.swap(lArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(long[] lArray, int n, int n2, int n3, LongComparator longComparator) {
        int n4 = longComparator.compare(lArray[n], lArray[n2]);
        int n5 = longComparator.compare(lArray[n], lArray[n3]);
        int n6 = longComparator.compare(lArray[n2], lArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(long[] lArray, int n, int n2, LongComparator longComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (longComparator.compare(lArray[j], lArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            long l = lArray[i];
            lArray[i] = lArray[n3];
            lArray[n3] = l;
        }
    }

    private static void insertionSort(long[] lArray, int n, int n2, LongComparator longComparator) {
        int n3 = n;
        while (++n3 < n2) {
            long l = lArray[n3];
            int n4 = n3;
            long l2 = lArray[n4 - 1];
            while (longComparator.compare(l, l2) < 0) {
                lArray[n4] = l2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                l2 = lArray[--n4 - 1];
            }
            lArray[n4] = l;
        }
    }

    public static void quickSort(long[] lArray, int n, int n2, LongComparator longComparator) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            LongArrays.selectionSort(lArray, n, n2, longComparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = LongArrays.med3(lArray, n8, n8 + n10, n8 + 2 * n10, longComparator);
            n7 = LongArrays.med3(lArray, n7 - n10, n7, n7 + n10, longComparator);
            n9 = LongArrays.med3(lArray, n9 - 2 * n10, n9 - n10, n9, longComparator);
        }
        n7 = LongArrays.med3(lArray, n8, n7, n9, longComparator);
        long l = lArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = longComparator.compare(lArray[n11], l)) <= 0) {
                if (n3 == 0) {
                    LongArrays.swap(lArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = longComparator.compare(lArray[n4], l)) >= 0) {
                if (n3 == 0) {
                    LongArrays.swap(lArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            LongArrays.swap(lArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        LongArrays.swap(lArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        LongArrays.swap(lArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            LongArrays.quickSort(lArray, n, n + n3, longComparator);
        }
        if ((n3 = n12 - n4) > 1) {
            LongArrays.quickSort(lArray, n2 - n3, n2, longComparator);
        }
    }

    public static void quickSort(long[] lArray, LongComparator longComparator) {
        LongArrays.quickSort(lArray, 0, lArray.length, longComparator);
    }

    public static void parallelQuickSort(long[] lArray, int n, int n2, LongComparator longComparator) {
        if (n2 - n < 8192) {
            LongArrays.quickSort(lArray, n, n2, longComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(lArray, n, n2, longComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(long[] lArray, LongComparator longComparator) {
        LongArrays.parallelQuickSort(lArray, 0, lArray.length, longComparator);
    }

    private static int med3(long[] lArray, int n, int n2, int n3) {
        int n4 = Long.compare(lArray[n], lArray[n2]);
        int n5 = Long.compare(lArray[n], lArray[n3]);
        int n6 = Long.compare(lArray[n2], lArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(long[] lArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (lArray[j] >= lArray[n3]) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            long l = lArray[i];
            lArray[i] = lArray[n3];
            lArray[n3] = l;
        }
    }

    private static void insertionSort(long[] lArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            long l = lArray[n3];
            int n4 = n3;
            long l2 = lArray[n4 - 1];
            while (l < l2) {
                lArray[n4] = l2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                l2 = lArray[--n4 - 1];
            }
            lArray[n4] = l;
        }
    }

    public static void quickSort(long[] lArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            LongArrays.selectionSort(lArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = LongArrays.med3(lArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = LongArrays.med3(lArray, n7 - n10, n7, n7 + n10);
            n9 = LongArrays.med3(lArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = LongArrays.med3(lArray, n8, n7, n9);
        long l = lArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Long.compare(lArray[n11], l)) <= 0) {
                if (n3 == 0) {
                    LongArrays.swap(lArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Long.compare(lArray[n4], l)) >= 0) {
                if (n3 == 0) {
                    LongArrays.swap(lArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            LongArrays.swap(lArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        LongArrays.swap(lArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        LongArrays.swap(lArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            LongArrays.quickSort(lArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            LongArrays.quickSort(lArray, n2 - n3, n2);
        }
    }

    public static void quickSort(long[] lArray) {
        LongArrays.quickSort(lArray, 0, lArray.length);
    }

    public static void parallelQuickSort(long[] lArray, int n, int n2) {
        if (n2 - n < 8192) {
            LongArrays.quickSort(lArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(lArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(long[] lArray) {
        LongArrays.parallelQuickSort(lArray, 0, lArray.length);
    }

    private static int med3Indirect(int[] nArray, long[] lArray, int n, int n2, int n3) {
        long l = lArray[nArray[n]];
        long l2 = lArray[nArray[n2]];
        long l3 = lArray[nArray[n3]];
        int n4 = Long.compare(l, l2);
        int n5 = Long.compare(l, l3);
        int n6 = Long.compare(l2, l3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, long[] lArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (lArray[n4] < lArray[n6]) {
                nArray[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
                n6 = nArray[--n5 - 1];
            }
            nArray[n5] = n4;
        }
    }

    public static void quickSortIndirect(int[] nArray, long[] lArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            LongArrays.insertionSortIndirect(nArray, lArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = LongArrays.med3Indirect(nArray, lArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = LongArrays.med3Indirect(nArray, lArray, n7 - n10, n7, n7 + n10);
            n9 = LongArrays.med3Indirect(nArray, lArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = LongArrays.med3Indirect(nArray, lArray, n8, n7, n9);
        long l = lArray[nArray[n7]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Long.compare(lArray[nArray[n11]], l)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Long.compare(lArray[nArray[n4]], l)) >= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            IntArrays.swap(nArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        IntArrays.swap(nArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        IntArrays.swap(nArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            LongArrays.quickSortIndirect(nArray, lArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            LongArrays.quickSortIndirect(nArray, lArray, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, long[] lArray) {
        LongArrays.quickSortIndirect(nArray, lArray, 0, lArray.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, long[] lArray, int n, int n2) {
        if (n2 - n < 8192) {
            LongArrays.quickSortIndirect(nArray, lArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, lArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, long[] lArray) {
        LongArrays.parallelQuickSortIndirect(nArray, lArray, 0, lArray.length);
    }

    public static void stabilize(int[] nArray, long[] lArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (lArray[nArray[i]] == lArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, long[] lArray) {
        LongArrays.stabilize(nArray, lArray, 0, nArray.length);
    }

    private static int med3(long[] lArray, long[] lArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Long.compare(lArray[n], lArray[n2]);
        int n6 = n5 == 0 ? Long.compare(lArray2[n], lArray2[n2]) : n5;
        n5 = Long.compare(lArray[n], lArray[n3]);
        int n7 = n5 == 0 ? Long.compare(lArray2[n], lArray2[n3]) : n5;
        n5 = Long.compare(lArray[n2], lArray[n3]);
        int n8 = n4 = n5 == 0 ? Long.compare(lArray2[n2], lArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(long[] lArray, long[] lArray2, int n, int n2) {
        long l = lArray[n];
        long l2 = lArray2[n];
        lArray[n] = lArray[n2];
        lArray2[n] = lArray2[n2];
        lArray[n2] = l;
        lArray2[n2] = l2;
    }

    private static void swap(long[] lArray, long[] lArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            LongArrays.swap(lArray, lArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(long[] lArray, long[] lArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                int n4 = Long.compare(lArray[j], lArray[n3]);
                if (n4 >= 0 && (n4 != 0 || lArray2[j] >= lArray2[n3])) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            long l = lArray[i];
            lArray[i] = lArray[n3];
            lArray[n3] = l;
            l = lArray2[i];
            lArray2[i] = lArray2[n3];
            lArray2[n3] = l;
        }
    }

    public static void quickSort(long[] lArray, long[] lArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            LongArrays.selectionSort(lArray, lArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = LongArrays.med3(lArray, lArray2, n8, n8 + n10, n8 + 2 * n10);
            n7 = LongArrays.med3(lArray, lArray2, n7 - n10, n7, n7 + n10);
            n9 = LongArrays.med3(lArray, lArray2, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = LongArrays.med3(lArray, lArray2, n8, n7, n9);
        long l = lArray[n7];
        long l2 = lArray2[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            int n13;
            if (n11 <= n4 && (n3 = (n13 = Long.compare(lArray[n11], l)) == 0 ? Long.compare(lArray2[n11], l2) : n13) <= 0) {
                if (n3 == 0) {
                    LongArrays.swap(lArray, lArray2, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = (n13 = Long.compare(lArray[n4], l)) == 0 ? Long.compare(lArray2[n4], l2) : n13) >= 0) {
                if (n3 == 0) {
                    LongArrays.swap(lArray, lArray2, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            LongArrays.swap(lArray, lArray2, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        LongArrays.swap(lArray, lArray2, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        LongArrays.swap(lArray, lArray2, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            LongArrays.quickSort(lArray, lArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            LongArrays.quickSort(lArray, lArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(long[] lArray, long[] lArray2) {
        LongArrays.ensureSameLength(lArray, lArray2);
        LongArrays.quickSort(lArray, lArray2, 0, lArray.length);
    }

    public static void parallelQuickSort(long[] lArray, long[] lArray2, int n, int n2) {
        if (n2 - n < 8192) {
            LongArrays.quickSort(lArray, lArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(lArray, lArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(long[] lArray, long[] lArray2) {
        LongArrays.ensureSameLength(lArray, lArray2);
        LongArrays.parallelQuickSort(lArray, lArray2, 0, lArray.length);
    }

    public static void mergeSort(long[] lArray, int n, int n2, long[] lArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            LongArrays.insertionSort(lArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        LongArrays.mergeSort(lArray2, n, n4, lArray);
        LongArrays.mergeSort(lArray2, n4, n2, lArray);
        if (lArray2[n4 - 1] <= lArray2[n4]) {
            System.arraycopy(lArray2, n, lArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            lArray[i] = n6 >= n2 || n5 < n4 && lArray2[n5] <= lArray2[n6] ? lArray2[n5++] : lArray2[n6++];
        }
    }

    public static void mergeSort(long[] lArray, int n, int n2) {
        LongArrays.mergeSort(lArray, n, n2, (long[])lArray.clone());
    }

    public static void mergeSort(long[] lArray) {
        LongArrays.mergeSort(lArray, 0, lArray.length);
    }

    public static void mergeSort(long[] lArray, int n, int n2, LongComparator longComparator, long[] lArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            LongArrays.insertionSort(lArray, n, n2, longComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        LongArrays.mergeSort(lArray2, n, n4, longComparator, lArray);
        LongArrays.mergeSort(lArray2, n4, n2, longComparator, lArray);
        if (longComparator.compare(lArray2[n4 - 1], lArray2[n4]) <= 0) {
            System.arraycopy(lArray2, n, lArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            lArray[i] = n6 >= n2 || n5 < n4 && longComparator.compare(lArray2[n5], lArray2[n6]) <= 0 ? lArray2[n5++] : lArray2[n6++];
        }
    }

    public static void mergeSort(long[] lArray, int n, int n2, LongComparator longComparator) {
        LongArrays.mergeSort(lArray, n, n2, longComparator, (long[])lArray.clone());
    }

    public static void mergeSort(long[] lArray, LongComparator longComparator) {
        LongArrays.mergeSort(lArray, 0, lArray.length, longComparator);
    }

    public static int binarySearch(long[] lArray, int n, int n2, long l) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            long l2 = lArray[n3];
            if (l2 < l) {
                n = n3 + 1;
                continue;
            }
            if (l2 > l) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int binarySearch(long[] lArray, long l) {
        return LongArrays.binarySearch(lArray, 0, lArray.length, l);
    }

    public static int binarySearch(long[] lArray, int n, int n2, long l, LongComparator longComparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            long l2 = lArray[n3];
            int n4 = longComparator.compare(l2, l);
            if (n4 < 0) {
                n = n3 + 1;
                continue;
            }
            if (n4 > 0) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int binarySearch(long[] lArray, long l, LongComparator longComparator) {
        return LongArrays.binarySearch(lArray, 0, lArray.length, l, longComparator);
    }

    public static void radixSort(long[] lArray) {
        LongArrays.radixSort(lArray, 0, lArray.length);
    }

    public static void radixSort(long[] lArray, int n, int n2) {
        if (n2 - n < 1024) {
            LongArrays.quickSort(lArray, n, n2);
            return;
        }
        int n3 = 7;
        int n4 = 1786;
        int n5 = 0;
        int[] nArray = new int[1786];
        int[] nArray2 = new int[1786];
        int[] nArray3 = new int[1786];
        nArray[n5] = n;
        nArray2[n5] = n2 - n;
        nArray3[n5++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        while (n5 > 0) {
            int n6;
            int n7 = nArray[--n5];
            int n8 = nArray2[n5];
            int n9 = nArray3[n5];
            int n10 = n9 % 8 == 0 ? 128 : 0;
            int n11 = (7 - n9 % 8) * 8;
            int n12 = n7 + n8;
            while (n12-- != n7) {
                int n13 = (int)(lArray[n12] >>> n11 & 0xFFL ^ (long)n10);
                nArray4[n13] = nArray4[n13] + 1;
            }
            n12 = -1;
            int n14 = n7;
            for (n6 = 0; n6 < 256; ++n6) {
                if (nArray4[n6] != 0) {
                    n12 = n6;
                }
                nArray5[n6] = n14 += nArray4[n6];
            }
            n6 = n7 + n8 - nArray4[n12];
            int n15 = -1;
            for (n14 = n7; n14 <= n6; n14 += nArray4[n15]) {
                long l = lArray[n14];
                n15 = (int)(l >>> n11 & 0xFFL ^ (long)n10);
                if (n14 < n6) {
                    while (true) {
                        int n16 = n15;
                        int n17 = nArray5[n16] - 1;
                        nArray5[n16] = n17;
                        int n18 = n17;
                        if (n17 <= n14) break;
                        long l2 = l;
                        l = lArray[n18];
                        lArray[n18] = l2;
                        n15 = (int)(l >>> n11 & 0xFFL ^ (long)n10);
                    }
                    lArray[n14] = l;
                }
                if (n9 < 7 && nArray4[n15] > 1) {
                    if (nArray4[n15] < 1024) {
                        LongArrays.quickSort(lArray, n14, n14 + nArray4[n15]);
                    } else {
                        nArray[n5] = n14;
                        nArray2[n5] = nArray4[n15];
                        nArray3[n5++] = n9 + 1;
                    }
                }
                nArray4[n15] = 0;
            }
        }
    }

    public static void parallelRadixSort(long[] lArray, int n, int n2) {
        if (n2 - n < 1024) {
            LongArrays.quickSort(lArray, n, n2);
            return;
        }
        int n3 = 7;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(() -> LongArrays.lambda$parallelRadixSort$0(atomicInteger, n4, linkedBlockingQueue, lArray));
        }
        Throwable throwable = null;
        int n6 = n4;
        while (n6-- != 0) {
            try {
                executorCompletionService.take().get();
            } catch (Exception exception) {
                throwable = exception.getCause();
            }
        }
        executorService.shutdown();
        if (throwable != null) {
            throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
        }
    }

    public static void parallelRadixSort(long[] lArray) {
        LongArrays.parallelRadixSort(lArray, 0, lArray.length);
    }

    public static void radixSortIndirect(int[] nArray, long[] lArray, boolean bl) {
        LongArrays.radixSortIndirect(nArray, lArray, 0, nArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, long[] lArray, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            LongArrays.insertionSortIndirect(nArray, lArray, n, n2);
            return;
        }
        int n3 = 7;
        int n4 = 1786;
        int n5 = 0;
        int[] nArray3 = new int[1786];
        int[] nArray4 = new int[1786];
        int[] nArray5 = new int[1786];
        nArray3[n5] = n;
        nArray4[n5] = n2 - n;
        nArray5[n5++] = 0;
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[256];
        int[] nArray8 = nArray2 = bl ? new int[nArray.length] : null;
        while (n5 > 0) {
            int n6;
            int n7;
            int n8 = nArray3[--n5];
            int n9 = nArray4[n5];
            int n10 = nArray5[n5];
            int n11 = n10 % 8 == 0 ? 128 : 0;
            int n12 = (7 - n10 % 8) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = (int)(lArray[nArray[n13]] >>> n12 & 0xFFL ^ (long)n11);
                nArray6[n14] = nArray6[n14] + 1;
            }
            n13 = -1;
            int n15 = n7 = bl ? 0 : n8;
            for (n6 = 0; n6 < 256; ++n6) {
                if (nArray6[n6] != 0) {
                    n13 = n6;
                }
                nArray7[n6] = n7 += nArray6[n6];
            }
            if (bl) {
                n6 = n8 + n9;
                while (n6-- != n8) {
                    int n16 = (int)(lArray[nArray[n6]] >>> n12 & 0xFFL ^ (long)n11);
                    int n17 = nArray7[n16] - 1;
                    nArray7[n16] = n17;
                    nArray2[n17] = nArray[n6];
                }
                System.arraycopy(nArray2, 0, nArray, n8, n9);
                n7 = n8;
                for (n6 = 0; n6 <= n13; ++n6) {
                    if (n10 < 7 && nArray6[n6] > 1) {
                        if (nArray6[n6] < 1024) {
                            LongArrays.insertionSortIndirect(nArray, lArray, n7, n7 + nArray6[n6]);
                        } else {
                            nArray3[n5] = n7;
                            nArray4[n5] = nArray6[n6];
                            nArray5[n5++] = n10 + 1;
                        }
                    }
                    n7 += nArray6[n6];
                }
                java.util.Arrays.fill(nArray6, 0);
                continue;
            }
            n6 = n8 + n9 - nArray6[n13];
            int n18 = -1;
            for (n7 = n8; n7 <= n6; n7 += nArray6[n18]) {
                int n19 = nArray[n7];
                n18 = (int)(lArray[n19] >>> n12 & 0xFFL ^ (long)n11);
                if (n7 < n6) {
                    while (true) {
                        int n20 = n18;
                        int n21 = nArray7[n20] - 1;
                        nArray7[n20] = n21;
                        int n22 = n21;
                        if (n21 <= n7) break;
                        int n23 = n19;
                        n19 = nArray[n22];
                        nArray[n22] = n23;
                        n18 = (int)(lArray[n19] >>> n12 & 0xFFL ^ (long)n11);
                    }
                    nArray[n7] = n19;
                }
                if (n10 < 7 && nArray6[n18] > 1) {
                    if (nArray6[n18] < 1024) {
                        LongArrays.insertionSortIndirect(nArray, lArray, n7, n7 + nArray6[n18]);
                    } else {
                        nArray3[n5] = n7;
                        nArray4[n5] = nArray6[n18];
                        nArray5[n5++] = n10 + 1;
                    }
                }
                nArray6[n18] = 0;
            }
        }
    }

    public static void parallelRadixSortIndirect(int[] nArray, long[] lArray, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            LongArrays.radixSortIndirect(nArray, lArray, n, n2, bl);
            return;
        }
        int n3 = 7;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int[] nArray2 = bl ? new int[nArray.length] : null;
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(() -> LongArrays.lambda$parallelRadixSortIndirect$1(atomicInteger, n4, linkedBlockingQueue, lArray, nArray, bl, nArray2));
        }
        Throwable throwable = null;
        int n6 = n4;
        while (n6-- != 0) {
            try {
                executorCompletionService.take().get();
            } catch (Exception exception) {
                throwable = exception.getCause();
            }
        }
        executorService.shutdown();
        if (throwable != null) {
            throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
        }
    }

    public static void parallelRadixSortIndirect(int[] nArray, long[] lArray, boolean bl) {
        LongArrays.parallelRadixSortIndirect(nArray, lArray, 0, lArray.length, bl);
    }

    public static void radixSort(long[] lArray, long[] lArray2) {
        LongArrays.ensureSameLength(lArray, lArray2);
        LongArrays.radixSort(lArray, lArray2, 0, lArray.length);
    }

    public static void radixSort(long[] lArray, long[] lArray2, int n, int n2) {
        if (n2 - n < 1024) {
            LongArrays.selectionSort(lArray, lArray2, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 15;
        int n5 = 3826;
        int n6 = 0;
        int[] nArray = new int[3826];
        int[] nArray2 = new int[3826];
        int[] nArray3 = new int[3826];
        nArray[n6] = n;
        nArray2[n6] = n2 - n;
        nArray3[n6++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        while (n6 > 0) {
            int n7;
            int n8 = nArray[--n6];
            int n9 = nArray2[n6];
            int n10 = nArray3[n6];
            int n11 = n10 % 8 == 0 ? 128 : 0;
            long[] lArray3 = n10 < 8 ? lArray : lArray2;
            int n12 = (7 - n10 % 8) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = (int)(lArray3[n13] >>> n12 & 0xFFL ^ (long)n11);
                nArray4[n14] = nArray4[n14] + 1;
            }
            n13 = -1;
            int n15 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray4[n7] != 0) {
                    n13 = n7;
                }
                nArray5[n7] = n15 += nArray4[n7];
            }
            n7 = n8 + n9 - nArray4[n13];
            int n16 = -1;
            for (n15 = n8; n15 <= n7; n15 += nArray4[n16]) {
                long l = lArray[n15];
                long l2 = lArray2[n15];
                n16 = (int)(lArray3[n15] >>> n12 & 0xFFL ^ (long)n11);
                if (n15 < n7) {
                    while (true) {
                        int n17 = n16;
                        int n18 = nArray5[n17] - 1;
                        nArray5[n17] = n18;
                        int n19 = n18;
                        if (n18 <= n15) break;
                        n16 = (int)(lArray3[n19] >>> n12 & 0xFFL ^ (long)n11);
                        long l3 = l;
                        l = lArray[n19];
                        lArray[n19] = l3;
                        l3 = l2;
                        l2 = lArray2[n19];
                        lArray2[n19] = l3;
                    }
                    lArray[n15] = l;
                    lArray2[n15] = l2;
                }
                if (n10 < 15 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        LongArrays.selectionSort(lArray, lArray2, n15, n15 + nArray4[n16]);
                    } else {
                        nArray[n6] = n15;
                        nArray2[n6] = nArray4[n16];
                        nArray3[n6++] = n10 + 1;
                    }
                }
                nArray4[n16] = 0;
            }
        }
    }

    public static void parallelRadixSort(long[] lArray, long[] lArray2, int n, int n2) {
        if (n2 - n < 1024) {
            LongArrays.quickSort(lArray, lArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (lArray.length != lArray2.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n4 = 15;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n5 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n5, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n6 = n5;
        while (n6-- != 0) {
            executorCompletionService.submit(() -> LongArrays.lambda$parallelRadixSort$2(atomicInteger, n5, linkedBlockingQueue, lArray, lArray2));
        }
        Throwable throwable = null;
        int n7 = n5;
        while (n7-- != 0) {
            try {
                executorCompletionService.take().get();
            } catch (Exception exception) {
                throwable = exception.getCause();
            }
        }
        executorService.shutdown();
        if (throwable != null) {
            throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
        }
    }

    public static void parallelRadixSort(long[] lArray, long[] lArray2) {
        LongArrays.ensureSameLength(lArray, lArray2);
        LongArrays.parallelRadixSort(lArray, lArray2, 0, lArray.length);
    }

    private static void insertionSortIndirect(int[] nArray, long[] lArray, long[] lArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (lArray[n4] < lArray[n6] || lArray[n4] == lArray[n6] && lArray2[n4] < lArray2[n6]) {
                nArray[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
                n6 = nArray[--n5 - 1];
            }
            nArray[n5] = n4;
        }
    }

    public static void radixSortIndirect(int[] nArray, long[] lArray, long[] lArray2, boolean bl) {
        LongArrays.ensureSameLength(lArray, lArray2);
        LongArrays.radixSortIndirect(nArray, lArray, lArray2, 0, lArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, long[] lArray, long[] lArray2, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            LongArrays.insertionSortIndirect(nArray, lArray, lArray2, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 15;
        int n5 = 3826;
        int n6 = 0;
        int[] nArray3 = new int[3826];
        int[] nArray4 = new int[3826];
        int[] nArray5 = new int[3826];
        nArray3[n6] = n;
        nArray4[n6] = n2 - n;
        nArray5[n6++] = 0;
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[256];
        int[] nArray8 = nArray2 = bl ? new int[nArray.length] : null;
        while (n6 > 0) {
            int n7;
            int n8;
            int n9 = nArray3[--n6];
            int n10 = nArray4[n6];
            int n11 = nArray5[n6];
            int n12 = n11 % 8 == 0 ? 128 : 0;
            long[] lArray3 = n11 < 8 ? lArray : lArray2;
            int n13 = (7 - n11 % 8) * 8;
            int n14 = n9 + n10;
            while (n14-- != n9) {
                int n15 = (int)(lArray3[nArray[n14]] >>> n13 & 0xFFL ^ (long)n12);
                nArray6[n15] = nArray6[n15] + 1;
            }
            n14 = -1;
            int n16 = n8 = bl ? 0 : n9;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray6[n7] != 0) {
                    n14 = n7;
                }
                nArray7[n7] = n8 += nArray6[n7];
            }
            if (bl) {
                n7 = n9 + n10;
                while (n7-- != n9) {
                    int n17 = (int)(lArray3[nArray[n7]] >>> n13 & 0xFFL ^ (long)n12);
                    int n18 = nArray7[n17] - 1;
                    nArray7[n17] = n18;
                    nArray2[n18] = nArray[n7];
                }
                System.arraycopy(nArray2, 0, nArray, n9, n10);
                n8 = n9;
                for (n7 = 0; n7 < 256; ++n7) {
                    if (n11 < 15 && nArray6[n7] > 1) {
                        if (nArray6[n7] < 1024) {
                            LongArrays.insertionSortIndirect(nArray, lArray, lArray2, n8, n8 + nArray6[n7]);
                        } else {
                            nArray3[n6] = n8;
                            nArray4[n6] = nArray6[n7];
                            nArray5[n6++] = n11 + 1;
                        }
                    }
                    n8 += nArray6[n7];
                }
                java.util.Arrays.fill(nArray6, 0);
                continue;
            }
            n7 = n9 + n10 - nArray6[n14];
            int n19 = -1;
            for (n8 = n9; n8 <= n7; n8 += nArray6[n19]) {
                int n20 = nArray[n8];
                n19 = (int)(lArray3[n20] >>> n13 & 0xFFL ^ (long)n12);
                if (n8 < n7) {
                    while (true) {
                        int n21 = n19;
                        int n22 = nArray7[n21] - 1;
                        nArray7[n21] = n22;
                        int n23 = n22;
                        if (n22 <= n8) break;
                        int n24 = n20;
                        n20 = nArray[n23];
                        nArray[n23] = n24;
                        n19 = (int)(lArray3[n20] >>> n13 & 0xFFL ^ (long)n12);
                    }
                    nArray[n8] = n20;
                }
                if (n11 < 15 && nArray6[n19] > 1) {
                    if (nArray6[n19] < 1024) {
                        LongArrays.insertionSortIndirect(nArray, lArray, lArray2, n8, n8 + nArray6[n19]);
                    } else {
                        nArray3[n6] = n8;
                        nArray4[n6] = nArray6[n19];
                        nArray5[n6++] = n11 + 1;
                    }
                }
                nArray6[n19] = 0;
            }
        }
    }

    private static void selectionSort(long[][] lArray, int n, int n2, int n3) {
        int n4 = lArray.length;
        int n5 = n3 / 8;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7 = i;
            block1: for (n6 = i + 1; n6 < n2; ++n6) {
                for (int j = n5; j < n4; ++j) {
                    if (lArray[j][n6] < lArray[j][n7]) {
                        n7 = n6;
                        continue block1;
                    }
                    if (lArray[j][n6] > lArray[j][n7]) continue block1;
                }
            }
            if (n7 == i) continue;
            n6 = n4;
            while (n6-- != 0) {
                long l = lArray[n6][i];
                lArray[n6][i] = lArray[n6][n7];
                lArray[n6][n7] = l;
            }
        }
    }

    public static void radixSort(long[][] lArray) {
        LongArrays.radixSort(lArray, 0, lArray[0].length);
    }

    public static void radixSort(long[][] lArray, int n, int n2) {
        if (n2 - n < 1024) {
            LongArrays.selectionSort(lArray, n, n2, 0);
            return;
        }
        int n3 = lArray.length;
        int n4 = 8 * n3 - 1;
        int n5 = n3;
        int n6 = lArray[0].length;
        while (n5-- != 0) {
            if (lArray[n5].length == n6) continue;
            throw new IllegalArgumentException("The array of index " + n5 + " has not the same length of the array of index 0.");
        }
        n5 = 255 * (n3 * 8 - 1) + 1;
        n6 = 0;
        int[] nArray = new int[n5];
        int[] nArray2 = new int[n5];
        int[] nArray3 = new int[n5];
        nArray[n6] = n;
        nArray2[n6] = n2 - n;
        nArray3[n6++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        long[] lArray2 = new long[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray[--n6];
            int n9 = nArray2[n6];
            int n10 = nArray3[n6];
            int n11 = n10 % 8 == 0 ? 128 : 0;
            long[] lArray3 = lArray[n10 / 8];
            int n12 = (7 - n10 % 8) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = (int)(lArray3[n13] >>> n12 & 0xFFL ^ (long)n11);
                nArray4[n14] = nArray4[n14] + 1;
            }
            n13 = -1;
            int n15 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray4[n7] != 0) {
                    n13 = n7;
                }
                nArray5[n7] = n15 += nArray4[n7];
            }
            n7 = n8 + n9 - nArray4[n13];
            int n16 = -1;
            for (n15 = n8; n15 <= n7; n15 += nArray4[n16]) {
                int n17 = n3;
                while (n17-- != 0) {
                    lArray2[n17] = lArray[n17][n15];
                }
                n16 = (int)(lArray3[n15] >>> n12 & 0xFFL ^ (long)n11);
                if (n15 < n7) {
                    block6: while (true) {
                        int n18 = n16;
                        int n19 = nArray5[n18] - 1;
                        nArray5[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n15) break;
                        n16 = (int)(lArray3[n20] >>> n12 & 0xFFL ^ (long)n11);
                        n17 = n3;
                        while (true) {
                            if (n17-- == 0) continue block6;
                            long l = lArray2[n17];
                            lArray2[n17] = lArray[n17][n20];
                            lArray[n17][n20] = l;
                        }
                        break;
                    }
                    n17 = n3;
                    while (n17-- != 0) {
                        lArray[n17][n15] = lArray2[n17];
                    }
                }
                if (n10 < n4 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        LongArrays.selectionSort(lArray, n15, n15 + nArray4[n16], n10 + 1);
                    } else {
                        nArray[n6] = n15;
                        nArray2[n6] = nArray4[n16];
                        nArray3[n6++] = n10 + 1;
                    }
                }
                nArray4[n16] = 0;
            }
        }
    }

    public static long[] shuffle(long[] lArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            long l = lArray[n + n3];
            lArray[n + n3] = lArray[n + n4];
            lArray[n + n4] = l;
        }
        return lArray;
    }

    public static long[] shuffle(long[] lArray, Random random2) {
        int n = lArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            long l = lArray[n];
            lArray[n] = lArray[n2];
            lArray[n2] = l;
        }
        return lArray;
    }

    public static long[] reverse(long[] lArray) {
        int n = lArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            long l = lArray[n - n2 - 1];
            lArray[n - n2 - 1] = lArray[n2];
            lArray[n2] = l;
        }
        return lArray;
    }

    public static long[] reverse(long[] lArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            long l = lArray[n + n3 - n4 - 1];
            lArray[n + n3 - n4 - 1] = lArray[n + n4];
            lArray[n + n4] = l;
        }
        return lArray;
    }

    private static Void lambda$parallelRadixSort$2(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, long[] lArray, long[] lArray2) throws Exception {
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        while (true) {
            int n2;
            Segment segment;
            if (atomicInteger.get() == 0) {
                int n3 = n;
                while (n3-- != 0) {
                    linkedBlockingQueue.add(POISON_PILL);
                }
            }
            if ((segment = (Segment)linkedBlockingQueue.take()) == POISON_PILL) {
                return null;
            }
            int n4 = segment.offset;
            int n5 = segment.length;
            int n6 = segment.level;
            int n7 = n6 % 8 == 0 ? 128 : 0;
            long[] lArray3 = n6 < 8 ? lArray : lArray2;
            int n8 = (7 - n6 % 8) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = (int)(lArray3[n9] >>> n8 & 0xFFL ^ (long)n7);
                nArray[n10] = nArray[n10] + 1;
            }
            n9 = -1;
            int n11 = n4;
            for (n2 = 0; n2 < 256; ++n2) {
                if (nArray[n2] != 0) {
                    n9 = n2;
                }
                nArray2[n2] = n11 += nArray[n2];
            }
            n2 = n4 + n5 - nArray[n9];
            int n12 = -1;
            for (n11 = n4; n11 <= n2; n11 += nArray[n12]) {
                long l = lArray[n11];
                long l2 = lArray2[n11];
                n12 = (int)(lArray3[n11] >>> n8 & 0xFFL ^ (long)n7);
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        n12 = (int)(lArray3[n15] >>> n8 & 0xFFL ^ (long)n7);
                        long l3 = l;
                        long l4 = l2;
                        l = lArray[n15];
                        l2 = lArray2[n15];
                        lArray[n15] = l3;
                        lArray2[n15] = l4;
                    }
                    lArray[n11] = l;
                    lArray2[n11] = l2;
                }
                if (n6 < 15 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        LongArrays.quickSort(lArray, lArray2, n11, n11 + nArray[n12]);
                    } else {
                        atomicInteger.incrementAndGet();
                        linkedBlockingQueue.add(new Segment(n11, nArray[n12], n6 + 1));
                    }
                }
                nArray[n12] = 0;
            }
            atomicInteger.decrementAndGet();
        }
    }

    private static Void lambda$parallelRadixSortIndirect$1(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, long[] lArray, int[] nArray, boolean bl, int[] nArray2) throws Exception {
        int[] nArray3 = new int[256];
        int[] nArray4 = new int[256];
        while (true) {
            int n2;
            Segment segment;
            if (atomicInteger.get() == 0) {
                int n3 = n;
                while (n3-- != 0) {
                    linkedBlockingQueue.add(POISON_PILL);
                }
            }
            if ((segment = (Segment)linkedBlockingQueue.take()) == POISON_PILL) {
                return null;
            }
            int n4 = segment.offset;
            int n5 = segment.length;
            int n6 = segment.level;
            int n7 = n6 % 8 == 0 ? 128 : 0;
            int n8 = (7 - n6 % 8) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = (int)(lArray[nArray[n9]] >>> n8 & 0xFFL ^ (long)n7);
                nArray3[n10] = nArray3[n10] + 1;
            }
            n9 = -1;
            int n11 = n4;
            for (n2 = 0; n2 < 256; ++n2) {
                if (nArray3[n2] != 0) {
                    n9 = n2;
                }
                nArray4[n2] = n11 += nArray3[n2];
            }
            if (bl) {
                n2 = n4 + n5;
                while (n2-- != n4) {
                    int n12 = (int)(lArray[nArray[n2]] >>> n8 & 0xFFL ^ (long)n7);
                    int n13 = nArray4[n12] - 1;
                    nArray4[n12] = n13;
                    nArray2[n13] = nArray[n2];
                }
                System.arraycopy(nArray2, n4, nArray, n4, n5);
                n11 = n4;
                for (n2 = 0; n2 <= n9; ++n2) {
                    if (n6 < 7 && nArray3[n2] > 1) {
                        if (nArray3[n2] < 1024) {
                            LongArrays.radixSortIndirect(nArray, lArray, n11, n11 + nArray3[n2], bl);
                        } else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(n11, nArray3[n2], n6 + 1));
                        }
                    }
                    n11 += nArray3[n2];
                }
                java.util.Arrays.fill(nArray3, 0);
            } else {
                n2 = n4 + n5 - nArray3[n9];
                int n14 = -1;
                for (n11 = n4; n11 <= n2; n11 += nArray3[n14]) {
                    int n15 = nArray[n11];
                    n14 = (int)(lArray[n15] >>> n8 & 0xFFL ^ (long)n7);
                    if (n11 < n2) {
                        while (true) {
                            int n16 = n14;
                            int n17 = nArray4[n16] - 1;
                            nArray4[n16] = n17;
                            int n18 = n17;
                            if (n17 <= n11) break;
                            int n19 = n15;
                            n15 = nArray[n18];
                            nArray[n18] = n19;
                            n14 = (int)(lArray[n15] >>> n8 & 0xFFL ^ (long)n7);
                        }
                        nArray[n11] = n15;
                    }
                    if (n6 < 7 && nArray3[n14] > 1) {
                        if (nArray3[n14] < 1024) {
                            LongArrays.radixSortIndirect(nArray, lArray, n11, n11 + nArray3[n14], bl);
                        } else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(n11, nArray3[n14], n6 + 1));
                        }
                    }
                    nArray3[n14] = 0;
                }
            }
            atomicInteger.decrementAndGet();
        }
    }

    private static Void lambda$parallelRadixSort$0(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, long[] lArray) throws Exception {
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        while (true) {
            int n2;
            Segment segment;
            if (atomicInteger.get() == 0) {
                int n3 = n;
                while (n3-- != 0) {
                    linkedBlockingQueue.add(POISON_PILL);
                }
            }
            if ((segment = (Segment)linkedBlockingQueue.take()) == POISON_PILL) {
                return null;
            }
            int n4 = segment.offset;
            int n5 = segment.length;
            int n6 = segment.level;
            int n7 = n6 % 8 == 0 ? 128 : 0;
            int n8 = (7 - n6 % 8) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = (int)(lArray[n9] >>> n8 & 0xFFL ^ (long)n7);
                nArray[n10] = nArray[n10] + 1;
            }
            n9 = -1;
            int n11 = n4;
            for (n2 = 0; n2 < 256; ++n2) {
                if (nArray[n2] != 0) {
                    n9 = n2;
                }
                nArray2[n2] = n11 += nArray[n2];
            }
            n2 = n4 + n5 - nArray[n9];
            int n12 = -1;
            for (n11 = n4; n11 <= n2; n11 += nArray[n12]) {
                long l = lArray[n11];
                n12 = (int)(l >>> n8 & 0xFFL ^ (long)n7);
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        long l2 = l;
                        l = lArray[n15];
                        lArray[n15] = l2;
                        n12 = (int)(l >>> n8 & 0xFFL ^ (long)n7);
                    }
                    lArray[n11] = l;
                }
                if (n6 < 7 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        LongArrays.quickSort(lArray, n11, n11 + nArray[n12]);
                    } else {
                        atomicInteger.incrementAndGet();
                        linkedBlockingQueue.add(new Segment(n11, nArray[n12], n6 + 1));
                    }
                }
                nArray[n12] = 0;
            }
            atomicInteger.decrementAndGet();
        }
    }

    static int access$000(long[] lArray, int n, int n2, int n3, LongComparator longComparator) {
        return LongArrays.med3(lArray, n, n2, n3, longComparator);
    }

    static int access$100(long[] lArray, int n, int n2, int n3) {
        return LongArrays.med3(lArray, n, n2, n3);
    }

    static int access$200(int[] nArray, long[] lArray, int n, int n2, int n3) {
        return LongArrays.med3Indirect(nArray, lArray, n, n2, n3);
    }

    static int access$300(long[] lArray, long[] lArray2, int n, int n2, int n3) {
        return LongArrays.med3(lArray, lArray2, n, n2, n3);
    }

    static void access$400(long[] lArray, long[] lArray2, int n, int n2) {
        LongArrays.swap(lArray, lArray2, n, n2);
    }

    static void access$500(long[] lArray, long[] lArray2, int n, int n2, int n3) {
        LongArrays.swap(lArray, lArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<long[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(long[] lArray) {
            return java.util.Arrays.hashCode(lArray);
        }

        @Override
        public boolean equals(long[] lArray, long[] lArray2) {
            return java.util.Arrays.equals(lArray, lArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((long[])object, (long[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((long[])object);
        }

        ArrayHashStrategy(1 var1_1) {
            this();
        }
    }

    protected static final class Segment {
        protected final int offset;
        protected final int length;
        protected final int level;

        protected Segment(int n, int n2, int n3) {
            this.offset = n;
            this.length = n2;
            this.level = n3;
        }

        public String toString() {
            return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
        }
    }

    protected static class ForkJoinQuickSort2
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final long[] x;
        private final long[] y;

        public ForkJoinQuickSort2(long[] lArray, long[] lArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = lArray;
            this.y = lArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            long[] lArray = this.x;
            long[] lArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                LongArrays.quickSort(lArray, lArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = LongArrays.access$300(lArray, lArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = LongArrays.access$300(lArray, lArray2, n5 - n8, n5, n5 + n8);
            n7 = LongArrays.access$300(lArray, lArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = LongArrays.access$300(lArray, lArray2, n6, n5, n7);
            long l = lArray[n5];
            long l2 = lArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = Long.compare(lArray[n9], l)) == 0 ? Long.compare(lArray2[n9], l2) : n11) <= 0) {
                    if (n == 0) {
                        LongArrays.access$400(lArray, lArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = Long.compare(lArray[n2], l)) == 0 ? Long.compare(lArray2[n2], l2) : n11) >= 0) {
                    if (n == 0) {
                        LongArrays.access$400(lArray, lArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                LongArrays.access$400(lArray, lArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            LongArrays.access$500(lArray, lArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            LongArrays.access$500(lArray, lArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(lArray, lArray2, this.from, this.from + n8), new ForkJoinQuickSort2(lArray, lArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(lArray, lArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(lArray, lArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final long[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, long[] lArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = lArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            long[] lArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                LongArrays.quickSortIndirect(this.perm, lArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = LongArrays.access$200(this.perm, lArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = LongArrays.access$200(this.perm, lArray, n5 - n8, n5, n5 + n8);
            n7 = LongArrays.access$200(this.perm, lArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = LongArrays.access$200(this.perm, lArray, n6, n5, n7);
            long l = lArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Long.compare(lArray[this.perm[n9]], l)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Long.compare(lArray[this.perm[n2]], l)) >= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                IntArrays.swap(this.perm, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            IntArrays.swap(this.perm, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            IntArrays.swap(this.perm, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, lArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, lArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, lArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, lArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final long[] x;

        public ForkJoinQuickSort(long[] lArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = lArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            long[] lArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                LongArrays.quickSort(lArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = LongArrays.access$100(lArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = LongArrays.access$100(lArray, n5 - n8, n5, n5 + n8);
            n7 = LongArrays.access$100(lArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = LongArrays.access$100(lArray, n6, n5, n7);
            long l = lArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Long.compare(lArray[n9], l)) <= 0) {
                    if (n == 0) {
                        LongArrays.swap(lArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Long.compare(lArray[n2], l)) >= 0) {
                    if (n == 0) {
                        LongArrays.swap(lArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                LongArrays.swap(lArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            LongArrays.swap(lArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            LongArrays.swap(lArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(lArray, this.from, this.from + n8), new ForkJoinQuickSort(lArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(lArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(lArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final long[] x;
        private final LongComparator comp;

        public ForkJoinQuickSortComp(long[] lArray, int n, int n2, LongComparator longComparator) {
            this.from = n;
            this.to = n2;
            this.x = lArray;
            this.comp = longComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            long[] lArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                LongArrays.quickSort(lArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = LongArrays.access$000(lArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = LongArrays.access$000(lArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = LongArrays.access$000(lArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = LongArrays.access$000(lArray, n6, n5, n7, this.comp);
            long l = lArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(lArray[n9], l)) <= 0) {
                    if (n == 0) {
                        LongArrays.swap(lArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(lArray[n2], l)) >= 0) {
                    if (n == 0) {
                        LongArrays.swap(lArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                LongArrays.swap(lArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            LongArrays.swap(lArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            LongArrays.swap(lArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(lArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(lArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(lArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(lArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

