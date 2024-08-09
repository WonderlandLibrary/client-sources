/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.shorts;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.shorts.ShortComparator;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public final class ShortArrays {
    public static final short[] EMPTY_ARRAY = new short[0];
    public static final short[] DEFAULT_EMPTY_ARRAY = new short[0];
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 2;
    private static final int RADIXSORT_NO_REC = 1024;
    private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
    protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
    public static final Hash.Strategy<short[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private ShortArrays() {
    }

    public static short[] forceCapacity(short[] sArray, int n, int n2) {
        short[] sArray2 = new short[n];
        System.arraycopy(sArray, 0, sArray2, 0, n2);
        return sArray2;
    }

    public static short[] ensureCapacity(short[] sArray, int n) {
        return ShortArrays.ensureCapacity(sArray, n, sArray.length);
    }

    public static short[] ensureCapacity(short[] sArray, int n, int n2) {
        return n > sArray.length ? ShortArrays.forceCapacity(sArray, n, n2) : sArray;
    }

    public static short[] grow(short[] sArray, int n) {
        return ShortArrays.grow(sArray, n, sArray.length);
    }

    public static short[] grow(short[] sArray, int n, int n2) {
        if (n > sArray.length) {
            int n3 = (int)Math.max(Math.min((long)sArray.length + (long)(sArray.length >> 1), 0x7FFFFFF7L), (long)n);
            short[] sArray2 = new short[n3];
            System.arraycopy(sArray, 0, sArray2, 0, n2);
            return sArray2;
        }
        return sArray;
    }

    public static short[] trim(short[] sArray, int n) {
        if (n >= sArray.length) {
            return sArray;
        }
        short[] sArray2 = n == 0 ? EMPTY_ARRAY : new short[n];
        System.arraycopy(sArray, 0, sArray2, 0, n);
        return sArray2;
    }

    public static short[] setLength(short[] sArray, int n) {
        if (n == sArray.length) {
            return sArray;
        }
        if (n < sArray.length) {
            return ShortArrays.trim(sArray, n);
        }
        return ShortArrays.ensureCapacity(sArray, n);
    }

    public static short[] copy(short[] sArray, int n, int n2) {
        ShortArrays.ensureOffsetLength(sArray, n, n2);
        short[] sArray2 = n2 == 0 ? EMPTY_ARRAY : new short[n2];
        System.arraycopy(sArray, n, sArray2, 0, n2);
        return sArray2;
    }

    public static short[] copy(short[] sArray) {
        return (short[])sArray.clone();
    }

    @Deprecated
    public static void fill(short[] sArray, short s) {
        int n = sArray.length;
        while (n-- != 0) {
            sArray[n] = s;
        }
    }

    @Deprecated
    public static void fill(short[] sArray, int n, int n2, short s) {
        ShortArrays.ensureFromTo(sArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                sArray[n2] = s;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                sArray[i] = s;
            }
        }
    }

    @Deprecated
    public static boolean equals(short[] sArray, short[] sArray2) {
        int n = sArray.length;
        if (n != sArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (sArray[n] == sArray2[n]) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(short[] sArray, int n, int n2) {
        Arrays.ensureFromTo(sArray.length, n, n2);
    }

    public static void ensureOffsetLength(short[] sArray, int n, int n2) {
        Arrays.ensureOffsetLength(sArray.length, n, n2);
    }

    public static void ensureSameLength(short[] sArray, short[] sArray2) {
        if (sArray.length != sArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + sArray.length + " != " + sArray2.length);
        }
    }

    public static void swap(short[] sArray, int n, int n2) {
        short s = sArray[n];
        sArray[n] = sArray[n2];
        sArray[n2] = s;
    }

    public static void swap(short[] sArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            ShortArrays.swap(sArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(short[] sArray, int n, int n2, int n3, ShortComparator shortComparator) {
        int n4 = shortComparator.compare(sArray[n], sArray[n2]);
        int n5 = shortComparator.compare(sArray[n], sArray[n3]);
        int n6 = shortComparator.compare(sArray[n2], sArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (shortComparator.compare(sArray[n3], sArray[n4]) >= 0) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = sArray[i];
            sArray[i] = sArray[n4];
            sArray[n4] = n3;
        }
    }

    private static void insertionSort(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        int n3 = n;
        while (++n3 < n2) {
            short s = sArray[n3];
            int n4 = n3;
            short s2 = sArray[n4 - 1];
            while (shortComparator.compare(s, s2) < 0) {
                sArray[n4] = s2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                s2 = sArray[--n4 - 1];
            }
            sArray[n4] = s;
        }
    }

    public static void quickSort(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        int n3;
        int n4;
        int n5;
        short s;
        int n6 = n2 - n;
        if (n6 < 16) {
            ShortArrays.selectionSort(sArray, n, n2, shortComparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            s = n6 / 8;
            n8 = ShortArrays.med3(sArray, n8, n8 + s, n8 + 2 * s, shortComparator);
            n7 = ShortArrays.med3(sArray, n7 - s, n7, n7 + s, shortComparator);
            n9 = ShortArrays.med3(sArray, n9 - 2 * s, n9 - s, n9, shortComparator);
        }
        n7 = ShortArrays.med3(sArray, n8, n7, n9, shortComparator);
        s = sArray[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = shortComparator.compare(sArray[n10], s)) <= 0) {
                if (n3 == 0) {
                    ShortArrays.swap(sArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = shortComparator.compare(sArray[n4], s)) >= 0) {
                if (n3 == 0) {
                    ShortArrays.swap(sArray, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            ShortArrays.swap(sArray, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        ShortArrays.swap(sArray, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        ShortArrays.swap(sArray, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            ShortArrays.quickSort(sArray, n, n + n3, shortComparator);
        }
        if ((n3 = n11 - n4) > 1) {
            ShortArrays.quickSort(sArray, n2 - n3, n2, shortComparator);
        }
    }

    public static void quickSort(short[] sArray, ShortComparator shortComparator) {
        ShortArrays.quickSort(sArray, 0, sArray.length, shortComparator);
    }

    public static void parallelQuickSort(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        if (n2 - n < 8192) {
            ShortArrays.quickSort(sArray, n, n2, shortComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(sArray, n, n2, shortComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(short[] sArray, ShortComparator shortComparator) {
        ShortArrays.parallelQuickSort(sArray, 0, sArray.length, shortComparator);
    }

    private static int med3(short[] sArray, int n, int n2, int n3) {
        int n4 = Short.compare(sArray[n], sArray[n2]);
        int n5 = Short.compare(sArray[n], sArray[n3]);
        int n6 = Short.compare(sArray[n2], sArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(short[] sArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (sArray[n3] >= sArray[n4]) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = sArray[i];
            sArray[i] = sArray[n4];
            sArray[n4] = n3;
        }
    }

    private static void insertionSort(short[] sArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            short s = sArray[n3];
            int n4 = n3;
            short s2 = sArray[n4 - 1];
            while (s < s2) {
                sArray[n4] = s2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                s2 = sArray[--n4 - 1];
            }
            sArray[n4] = s;
        }
    }

    public static void quickSort(short[] sArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        short s;
        int n6 = n2 - n;
        if (n6 < 16) {
            ShortArrays.selectionSort(sArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            s = n6 / 8;
            n8 = ShortArrays.med3(sArray, n8, n8 + s, n8 + 2 * s);
            n7 = ShortArrays.med3(sArray, n7 - s, n7, n7 + s);
            n9 = ShortArrays.med3(sArray, n9 - 2 * s, n9 - s, n9);
        }
        n7 = ShortArrays.med3(sArray, n8, n7, n9);
        s = sArray[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = Short.compare(sArray[n10], s)) <= 0) {
                if (n3 == 0) {
                    ShortArrays.swap(sArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = Short.compare(sArray[n4], s)) >= 0) {
                if (n3 == 0) {
                    ShortArrays.swap(sArray, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            ShortArrays.swap(sArray, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        ShortArrays.swap(sArray, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        ShortArrays.swap(sArray, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            ShortArrays.quickSort(sArray, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            ShortArrays.quickSort(sArray, n2 - n3, n2);
        }
    }

    public static void quickSort(short[] sArray) {
        ShortArrays.quickSort(sArray, 0, sArray.length);
    }

    public static void parallelQuickSort(short[] sArray, int n, int n2) {
        if (n2 - n < 8192) {
            ShortArrays.quickSort(sArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(sArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(short[] sArray) {
        ShortArrays.parallelQuickSort(sArray, 0, sArray.length);
    }

    private static int med3Indirect(int[] nArray, short[] sArray, int n, int n2, int n3) {
        short s = sArray[nArray[n]];
        short s2 = sArray[nArray[n2]];
        short s3 = sArray[nArray[n3]];
        int n4 = Short.compare(s, s2);
        int n5 = Short.compare(s, s3);
        int n6 = Short.compare(s2, s3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, short[] sArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (sArray[n4] < sArray[n6]) {
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

    public static void quickSortIndirect(int[] nArray, short[] sArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        short s;
        int n6 = n2 - n;
        if (n6 < 16) {
            ShortArrays.insertionSortIndirect(nArray, sArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            s = n6 / 8;
            n8 = ShortArrays.med3Indirect(nArray, sArray, n8, n8 + s, n8 + 2 * s);
            n7 = ShortArrays.med3Indirect(nArray, sArray, n7 - s, n7, n7 + s);
            n9 = ShortArrays.med3Indirect(nArray, sArray, n9 - 2 * s, n9 - s, n9);
        }
        n7 = ShortArrays.med3Indirect(nArray, sArray, n8, n7, n9);
        s = sArray[nArray[n7]];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = Short.compare(sArray[nArray[n10]], s)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = Short.compare(sArray[nArray[n4]], s)) >= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            IntArrays.swap(nArray, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        IntArrays.swap(nArray, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        IntArrays.swap(nArray, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            ShortArrays.quickSortIndirect(nArray, sArray, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            ShortArrays.quickSortIndirect(nArray, sArray, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, short[] sArray) {
        ShortArrays.quickSortIndirect(nArray, sArray, 0, sArray.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, short[] sArray, int n, int n2) {
        if (n2 - n < 8192) {
            ShortArrays.quickSortIndirect(nArray, sArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, sArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, short[] sArray) {
        ShortArrays.parallelQuickSortIndirect(nArray, sArray, 0, sArray.length);
    }

    public static void stabilize(int[] nArray, short[] sArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (sArray[nArray[i]] == sArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, short[] sArray) {
        ShortArrays.stabilize(nArray, sArray, 0, nArray.length);
    }

    private static int med3(short[] sArray, short[] sArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Short.compare(sArray[n], sArray[n2]);
        int n6 = n5 == 0 ? Short.compare(sArray2[n], sArray2[n2]) : n5;
        n5 = Short.compare(sArray[n], sArray[n3]);
        int n7 = n5 == 0 ? Short.compare(sArray2[n], sArray2[n3]) : n5;
        n5 = Short.compare(sArray[n2], sArray[n3]);
        int n8 = n4 = n5 == 0 ? Short.compare(sArray2[n2], sArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(short[] sArray, short[] sArray2, int n, int n2) {
        short s = sArray[n];
        short s2 = sArray2[n];
        sArray[n] = sArray[n2];
        sArray2[n] = sArray2[n2];
        sArray[n2] = s;
        sArray2[n2] = s2;
    }

    private static void swap(short[] sArray, short[] sArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            ShortArrays.swap(sArray, sArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(short[] sArray, short[] sArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                int n5 = Short.compare(sArray[n3], sArray[n4]);
                if (n5 >= 0 && (n5 != 0 || sArray2[n3] >= sArray2[n4])) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = sArray[i];
            sArray[i] = sArray[n4];
            sArray[n4] = n3;
            n3 = sArray2[i];
            sArray2[i] = sArray2[n4];
            sArray2[n4] = n3;
        }
    }

    public static void quickSort(short[] sArray, short[] sArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        short s;
        int n6 = n2 - n;
        if (n6 < 16) {
            ShortArrays.selectionSort(sArray, sArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            s = n6 / 8;
            n8 = ShortArrays.med3(sArray, sArray2, n8, n8 + s, n8 + 2 * s);
            n7 = ShortArrays.med3(sArray, sArray2, n7 - s, n7, n7 + s);
            n9 = ShortArrays.med3(sArray, sArray2, n9 - 2 * s, n9 - s, n9);
        }
        n7 = ShortArrays.med3(sArray, sArray2, n8, n7, n9);
        s = sArray[n7];
        short s2 = sArray2[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            int n12;
            if (n10 <= n4 && (n3 = (n12 = Short.compare(sArray[n10], s)) == 0 ? Short.compare(sArray2[n10], s2) : n12) <= 0) {
                if (n3 == 0) {
                    ShortArrays.swap(sArray, sArray2, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = (n12 = Short.compare(sArray[n4], s)) == 0 ? Short.compare(sArray2[n4], s2) : n12) >= 0) {
                if (n3 == 0) {
                    ShortArrays.swap(sArray, sArray2, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            ShortArrays.swap(sArray, sArray2, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        ShortArrays.swap(sArray, sArray2, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        ShortArrays.swap(sArray, sArray2, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            ShortArrays.quickSort(sArray, sArray2, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            ShortArrays.quickSort(sArray, sArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(short[] sArray, short[] sArray2) {
        ShortArrays.ensureSameLength(sArray, sArray2);
        ShortArrays.quickSort(sArray, sArray2, 0, sArray.length);
    }

    public static void parallelQuickSort(short[] sArray, short[] sArray2, int n, int n2) {
        if (n2 - n < 8192) {
            ShortArrays.quickSort(sArray, sArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(sArray, sArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(short[] sArray, short[] sArray2) {
        ShortArrays.ensureSameLength(sArray, sArray2);
        ShortArrays.parallelQuickSort(sArray, sArray2, 0, sArray.length);
    }

    public static void mergeSort(short[] sArray, int n, int n2, short[] sArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            ShortArrays.insertionSort(sArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        ShortArrays.mergeSort(sArray2, n, n4, sArray);
        ShortArrays.mergeSort(sArray2, n4, n2, sArray);
        if (sArray2[n4 - 1] <= sArray2[n4]) {
            System.arraycopy(sArray2, n, sArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            sArray[i] = n6 >= n2 || n5 < n4 && sArray2[n5] <= sArray2[n6] ? sArray2[n5++] : sArray2[n6++];
        }
    }

    public static void mergeSort(short[] sArray, int n, int n2) {
        ShortArrays.mergeSort(sArray, n, n2, (short[])sArray.clone());
    }

    public static void mergeSort(short[] sArray) {
        ShortArrays.mergeSort(sArray, 0, sArray.length);
    }

    public static void mergeSort(short[] sArray, int n, int n2, ShortComparator shortComparator, short[] sArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            ShortArrays.insertionSort(sArray, n, n2, shortComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        ShortArrays.mergeSort(sArray2, n, n4, shortComparator, sArray);
        ShortArrays.mergeSort(sArray2, n4, n2, shortComparator, sArray);
        if (shortComparator.compare(sArray2[n4 - 1], sArray2[n4]) <= 0) {
            System.arraycopy(sArray2, n, sArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            sArray[i] = n6 >= n2 || n5 < n4 && shortComparator.compare(sArray2[n5], sArray2[n6]) <= 0 ? sArray2[n5++] : sArray2[n6++];
        }
    }

    public static void mergeSort(short[] sArray, int n, int n2, ShortComparator shortComparator) {
        ShortArrays.mergeSort(sArray, n, n2, shortComparator, (short[])sArray.clone());
    }

    public static void mergeSort(short[] sArray, ShortComparator shortComparator) {
        ShortArrays.mergeSort(sArray, 0, sArray.length, shortComparator);
    }

    public static int binarySearch(short[] sArray, int n, int n2, short s) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            short s2 = sArray[n3];
            if (s2 < s) {
                n = n3 + 1;
                continue;
            }
            if (s2 > s) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int binarySearch(short[] sArray, short s) {
        return ShortArrays.binarySearch(sArray, 0, sArray.length, s);
    }

    public static int binarySearch(short[] sArray, int n, int n2, short s, ShortComparator shortComparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            short s2 = sArray[n3];
            int n4 = shortComparator.compare(s2, s);
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

    public static int binarySearch(short[] sArray, short s, ShortComparator shortComparator) {
        return ShortArrays.binarySearch(sArray, 0, sArray.length, s, shortComparator);
    }

    public static void radixSort(short[] sArray) {
        ShortArrays.radixSort(sArray, 0, sArray.length);
    }

    public static void radixSort(short[] sArray, int n, int n2) {
        if (n2 - n < 1024) {
            ShortArrays.quickSort(sArray, n, n2);
            return;
        }
        boolean bl = true;
        int n3 = 256;
        int n4 = 0;
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        int[] nArray3 = new int[256];
        nArray[n4] = n;
        nArray2[n4] = n2 - n;
        nArray3[n4++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        while (n4 > 0) {
            int n5;
            int n6 = nArray[--n4];
            int n7 = nArray2[n4];
            int n8 = nArray3[n4];
            int n9 = n8 % 2 == 0 ? 128 : 0;
            int n10 = (1 - n8 % 2) * 8;
            int n11 = n6 + n7;
            while (n11-- != n6) {
                int n12 = sArray[n11] >>> n10 & 0xFF ^ n9;
                nArray4[n12] = nArray4[n12] + 1;
            }
            n11 = -1;
            int n13 = n6;
            for (n5 = 0; n5 < 256; ++n5) {
                if (nArray4[n5] != 0) {
                    n11 = n5;
                }
                nArray5[n5] = n13 += nArray4[n5];
            }
            n5 = n6 + n7 - nArray4[n11];
            int n14 = -1;
            for (n13 = n6; n13 <= n5; n13 += nArray4[n14]) {
                short s = sArray[n13];
                n14 = s >>> n10 & 0xFF ^ n9;
                if (n13 < n5) {
                    while (true) {
                        int n15 = n14;
                        int n16 = nArray5[n15] - 1;
                        nArray5[n15] = n16;
                        int n17 = n16;
                        if (n16 <= n13) break;
                        short s2 = s;
                        s = sArray[n17];
                        sArray[n17] = s2;
                        n14 = s >>> n10 & 0xFF ^ n9;
                    }
                    sArray[n13] = s;
                }
                if (n8 < 1 && nArray4[n14] > 1) {
                    if (nArray4[n14] < 1024) {
                        ShortArrays.quickSort(sArray, n13, n13 + nArray4[n14]);
                    } else {
                        nArray[n4] = n13;
                        nArray2[n4] = nArray4[n14];
                        nArray3[n4++] = n8 + 1;
                    }
                }
                nArray4[n14] = 0;
            }
        }
    }

    public static void parallelRadixSort(short[] sArray, int n, int n2) {
        if (n2 - n < 1024) {
            ShortArrays.quickSort(sArray, n, n2);
            return;
        }
        boolean bl = true;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n3 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n3, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n4 = n3;
        while (n4-- != 0) {
            executorCompletionService.submit(() -> ShortArrays.lambda$parallelRadixSort$0(atomicInteger, n3, linkedBlockingQueue, sArray));
        }
        Throwable throwable = null;
        int n5 = n3;
        while (n5-- != 0) {
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

    public static void parallelRadixSort(short[] sArray) {
        ShortArrays.parallelRadixSort(sArray, 0, sArray.length);
    }

    public static void radixSortIndirect(int[] nArray, short[] sArray, boolean bl) {
        ShortArrays.radixSortIndirect(nArray, sArray, 0, nArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, short[] sArray, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            ShortArrays.insertionSortIndirect(nArray, sArray, n, n2);
            return;
        }
        boolean bl2 = true;
        int n3 = 256;
        int n4 = 0;
        int[] nArray3 = new int[256];
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        nArray3[n4] = n;
        nArray4[n4] = n2 - n;
        nArray5[n4++] = 0;
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[256];
        int[] nArray8 = nArray2 = bl ? new int[nArray.length] : null;
        while (n4 > 0) {
            int n5;
            int n6;
            int n7 = nArray3[--n4];
            int n8 = nArray4[n4];
            int n9 = nArray5[n4];
            int n10 = n9 % 2 == 0 ? 128 : 0;
            int n11 = (1 - n9 % 2) * 8;
            int n12 = n7 + n8;
            while (n12-- != n7) {
                int n13 = sArray[nArray[n12]] >>> n11 & 0xFF ^ n10;
                nArray6[n13] = nArray6[n13] + 1;
            }
            n12 = -1;
            int n14 = n6 = bl ? 0 : n7;
            for (n5 = 0; n5 < 256; ++n5) {
                if (nArray6[n5] != 0) {
                    n12 = n5;
                }
                nArray7[n5] = n6 += nArray6[n5];
            }
            if (bl) {
                n5 = n7 + n8;
                while (n5-- != n7) {
                    int n15 = sArray[nArray[n5]] >>> n11 & 0xFF ^ n10;
                    int n16 = nArray7[n15] - 1;
                    nArray7[n15] = n16;
                    nArray2[n16] = nArray[n5];
                }
                System.arraycopy(nArray2, 0, nArray, n7, n8);
                n6 = n7;
                for (n5 = 0; n5 <= n12; ++n5) {
                    if (n9 < 1 && nArray6[n5] > 1) {
                        if (nArray6[n5] < 1024) {
                            ShortArrays.insertionSortIndirect(nArray, sArray, n6, n6 + nArray6[n5]);
                        } else {
                            nArray3[n4] = n6;
                            nArray4[n4] = nArray6[n5];
                            nArray5[n4++] = n9 + 1;
                        }
                    }
                    n6 += nArray6[n5];
                }
                java.util.Arrays.fill(nArray6, 0);
                continue;
            }
            n5 = n7 + n8 - nArray6[n12];
            int n17 = -1;
            for (n6 = n7; n6 <= n5; n6 += nArray6[n17]) {
                int n18 = nArray[n6];
                n17 = sArray[n18] >>> n11 & 0xFF ^ n10;
                if (n6 < n5) {
                    while (true) {
                        int n19 = n17;
                        int n20 = nArray7[n19] - 1;
                        nArray7[n19] = n20;
                        int n21 = n20;
                        if (n20 <= n6) break;
                        int n22 = n18;
                        n18 = nArray[n21];
                        nArray[n21] = n22;
                        n17 = sArray[n18] >>> n11 & 0xFF ^ n10;
                    }
                    nArray[n6] = n18;
                }
                if (n9 < 1 && nArray6[n17] > 1) {
                    if (nArray6[n17] < 1024) {
                        ShortArrays.insertionSortIndirect(nArray, sArray, n6, n6 + nArray6[n17]);
                    } else {
                        nArray3[n4] = n6;
                        nArray4[n4] = nArray6[n17];
                        nArray5[n4++] = n9 + 1;
                    }
                }
                nArray6[n17] = 0;
            }
        }
    }

    public static void parallelRadixSortIndirect(int[] nArray, short[] sArray, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            ShortArrays.radixSortIndirect(nArray, sArray, n, n2, bl);
            return;
        }
        boolean bl2 = true;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n3 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n3, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int[] nArray2 = bl ? new int[nArray.length] : null;
        int n4 = n3;
        while (n4-- != 0) {
            executorCompletionService.submit(() -> ShortArrays.lambda$parallelRadixSortIndirect$1(atomicInteger, n3, linkedBlockingQueue, sArray, nArray, bl, nArray2));
        }
        Throwable throwable = null;
        int n5 = n3;
        while (n5-- != 0) {
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

    public static void parallelRadixSortIndirect(int[] nArray, short[] sArray, boolean bl) {
        ShortArrays.parallelRadixSortIndirect(nArray, sArray, 0, sArray.length, bl);
    }

    public static void radixSort(short[] sArray, short[] sArray2) {
        ShortArrays.ensureSameLength(sArray, sArray2);
        ShortArrays.radixSort(sArray, sArray2, 0, sArray.length);
    }

    public static void radixSort(short[] sArray, short[] sArray2, int n, int n2) {
        if (n2 - n < 1024) {
            ShortArrays.selectionSort(sArray, sArray2, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 3;
        int n5 = 766;
        int n6 = 0;
        int[] nArray = new int[766];
        int[] nArray2 = new int[766];
        int[] nArray3 = new int[766];
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
            int n11 = n10 % 2 == 0 ? 128 : 0;
            short[] sArray3 = n10 < 2 ? sArray : sArray2;
            int n12 = (1 - n10 % 2) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = sArray3[n13] >>> n12 & 0xFF ^ n11;
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
                short s = sArray[n15];
                short s2 = sArray2[n15];
                n16 = sArray3[n15] >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    while (true) {
                        int n17 = n16;
                        int n18 = nArray5[n17] - 1;
                        nArray5[n17] = n18;
                        int n19 = n18;
                        if (n18 <= n15) break;
                        n16 = sArray3[n19] >>> n12 & 0xFF ^ n11;
                        short s3 = s;
                        s = sArray[n19];
                        sArray[n19] = s3;
                        s3 = s2;
                        s2 = sArray2[n19];
                        sArray2[n19] = s3;
                    }
                    sArray[n15] = s;
                    sArray2[n15] = s2;
                }
                if (n10 < 3 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        ShortArrays.selectionSort(sArray, sArray2, n15, n15 + nArray4[n16]);
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

    public static void parallelRadixSort(short[] sArray, short[] sArray2, int n, int n2) {
        if (n2 - n < 1024) {
            ShortArrays.quickSort(sArray, sArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (sArray.length != sArray2.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n4 = 3;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n5 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n5, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n6 = n5;
        while (n6-- != 0) {
            executorCompletionService.submit(() -> ShortArrays.lambda$parallelRadixSort$2(atomicInteger, n5, linkedBlockingQueue, sArray, sArray2));
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

    public static void parallelRadixSort(short[] sArray, short[] sArray2) {
        ShortArrays.ensureSameLength(sArray, sArray2);
        ShortArrays.parallelRadixSort(sArray, sArray2, 0, sArray.length);
    }

    private static void insertionSortIndirect(int[] nArray, short[] sArray, short[] sArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (sArray[n4] < sArray[n6] || sArray[n4] == sArray[n6] && sArray2[n4] < sArray2[n6]) {
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

    public static void radixSortIndirect(int[] nArray, short[] sArray, short[] sArray2, boolean bl) {
        ShortArrays.ensureSameLength(sArray, sArray2);
        ShortArrays.radixSortIndirect(nArray, sArray, sArray2, 0, sArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, short[] sArray, short[] sArray2, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            ShortArrays.insertionSortIndirect(nArray, sArray, sArray2, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 3;
        int n5 = 766;
        int n6 = 0;
        int[] nArray3 = new int[766];
        int[] nArray4 = new int[766];
        int[] nArray5 = new int[766];
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
            int n12 = n11 % 2 == 0 ? 128 : 0;
            short[] sArray3 = n11 < 2 ? sArray : sArray2;
            int n13 = (1 - n11 % 2) * 8;
            int n14 = n9 + n10;
            while (n14-- != n9) {
                int n15 = sArray3[nArray[n14]] >>> n13 & 0xFF ^ n12;
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
                    int n17 = sArray3[nArray[n7]] >>> n13 & 0xFF ^ n12;
                    int n18 = nArray7[n17] - 1;
                    nArray7[n17] = n18;
                    nArray2[n18] = nArray[n7];
                }
                System.arraycopy(nArray2, 0, nArray, n9, n10);
                n8 = n9;
                for (n7 = 0; n7 < 256; ++n7) {
                    if (n11 < 3 && nArray6[n7] > 1) {
                        if (nArray6[n7] < 1024) {
                            ShortArrays.insertionSortIndirect(nArray, sArray, sArray2, n8, n8 + nArray6[n7]);
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
                n19 = sArray3[n20] >>> n13 & 0xFF ^ n12;
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
                        n19 = sArray3[n20] >>> n13 & 0xFF ^ n12;
                    }
                    nArray[n8] = n20;
                }
                if (n11 < 3 && nArray6[n19] > 1) {
                    if (nArray6[n19] < 1024) {
                        ShortArrays.insertionSortIndirect(nArray, sArray, sArray2, n8, n8 + nArray6[n19]);
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

    private static void selectionSort(short[][] sArray, int n, int n2, int n3) {
        int n4 = sArray.length;
        int n5 = n3 / 2;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7;
            int n8 = i;
            block1: for (n7 = i + 1; n7 < n2; ++n7) {
                for (n6 = n5; n6 < n4; ++n6) {
                    if (sArray[n6][n7] < sArray[n6][n8]) {
                        n8 = n7;
                        continue block1;
                    }
                    if (sArray[n6][n7] > sArray[n6][n8]) continue block1;
                }
            }
            if (n8 == i) continue;
            n7 = n4;
            while (n7-- != 0) {
                n6 = sArray[n7][i];
                sArray[n7][i] = sArray[n7][n8];
                sArray[n7][n8] = n6;
            }
        }
    }

    public static void radixSort(short[][] sArray) {
        ShortArrays.radixSort(sArray, 0, sArray[0].length);
    }

    public static void radixSort(short[][] sArray, int n, int n2) {
        if (n2 - n < 1024) {
            ShortArrays.selectionSort(sArray, n, n2, 0);
            return;
        }
        int n3 = sArray.length;
        int n4 = 2 * n3 - 1;
        int n5 = n3;
        int n6 = sArray[0].length;
        while (n5-- != 0) {
            if (sArray[n5].length == n6) continue;
            throw new IllegalArgumentException("The array of index " + n5 + " has not the same length of the array of index 0.");
        }
        n5 = 255 * (n3 * 2 - 1) + 1;
        n6 = 0;
        int[] nArray = new int[n5];
        int[] nArray2 = new int[n5];
        int[] nArray3 = new int[n5];
        nArray[n6] = n;
        nArray2[n6] = n2 - n;
        nArray3[n6++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        short[] sArray2 = new short[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray[--n6];
            int n9 = nArray2[n6];
            int n10 = nArray3[n6];
            int n11 = n10 % 2 == 0 ? 128 : 0;
            short[] sArray3 = sArray[n10 / 2];
            int n12 = (1 - n10 % 2) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = sArray3[n13] >>> n12 & 0xFF ^ n11;
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
                    sArray2[n17] = sArray[n17][n15];
                }
                n16 = sArray3[n15] >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    block6: while (true) {
                        int n18 = n16;
                        int n19 = nArray5[n18] - 1;
                        nArray5[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n15) break;
                        n16 = sArray3[n20] >>> n12 & 0xFF ^ n11;
                        n17 = n3;
                        while (true) {
                            if (n17-- == 0) continue block6;
                            short s = sArray2[n17];
                            sArray2[n17] = sArray[n17][n20];
                            sArray[n17][n20] = s;
                        }
                        break;
                    }
                    n17 = n3;
                    while (n17-- != 0) {
                        sArray[n17][n15] = sArray2[n17];
                    }
                }
                if (n10 < n4 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        ShortArrays.selectionSort(sArray, n15, n15 + nArray4[n16], n10 + 1);
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

    public static short[] shuffle(short[] sArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            short s = sArray[n + n3];
            sArray[n + n3] = sArray[n + n4];
            sArray[n + n4] = s;
        }
        return sArray;
    }

    public static short[] shuffle(short[] sArray, Random random2) {
        int n = sArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            short s = sArray[n];
            sArray[n] = sArray[n2];
            sArray[n2] = s;
        }
        return sArray;
    }

    public static short[] reverse(short[] sArray) {
        int n = sArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            short s = sArray[n - n2 - 1];
            sArray[n - n2 - 1] = sArray[n2];
            sArray[n2] = s;
        }
        return sArray;
    }

    public static short[] reverse(short[] sArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            short s = sArray[n + n3 - n4 - 1];
            sArray[n + n3 - n4 - 1] = sArray[n + n4];
            sArray[n + n4] = s;
        }
        return sArray;
    }

    private static Void lambda$parallelRadixSort$2(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, short[] sArray, short[] sArray2) throws Exception {
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
            int n7 = n6 % 2 == 0 ? 128 : 0;
            short[] sArray3 = n6 < 2 ? sArray : sArray2;
            int n8 = (1 - n6 % 2) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = sArray3[n9] >>> n8 & 0xFF ^ n7;
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
                short s = sArray[n11];
                short s2 = sArray2[n11];
                n12 = sArray3[n11] >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        n12 = sArray3[n15] >>> n8 & 0xFF ^ n7;
                        short s3 = s;
                        short s4 = s2;
                        s = sArray[n15];
                        s2 = sArray2[n15];
                        sArray[n15] = s3;
                        sArray2[n15] = s4;
                    }
                    sArray[n11] = s;
                    sArray2[n11] = s2;
                }
                if (n6 < 3 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        ShortArrays.quickSort(sArray, sArray2, n11, n11 + nArray[n12]);
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

    private static Void lambda$parallelRadixSortIndirect$1(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, short[] sArray, int[] nArray, boolean bl, int[] nArray2) throws Exception {
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
            int n7 = n6 % 2 == 0 ? 128 : 0;
            int n8 = (1 - n6 % 2) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = sArray[nArray[n9]] >>> n8 & 0xFF ^ n7;
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
                    int n12 = sArray[nArray[n2]] >>> n8 & 0xFF ^ n7;
                    int n13 = nArray4[n12] - 1;
                    nArray4[n12] = n13;
                    nArray2[n13] = nArray[n2];
                }
                System.arraycopy(nArray2, n4, nArray, n4, n5);
                n11 = n4;
                for (n2 = 0; n2 <= n9; ++n2) {
                    if (n6 < 1 && nArray3[n2] > 1) {
                        if (nArray3[n2] < 1024) {
                            ShortArrays.radixSortIndirect(nArray, sArray, n11, n11 + nArray3[n2], bl);
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
                    n14 = sArray[n15] >>> n8 & 0xFF ^ n7;
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
                            n14 = sArray[n15] >>> n8 & 0xFF ^ n7;
                        }
                        nArray[n11] = n15;
                    }
                    if (n6 < 1 && nArray3[n14] > 1) {
                        if (nArray3[n14] < 1024) {
                            ShortArrays.radixSortIndirect(nArray, sArray, n11, n11 + nArray3[n14], bl);
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

    private static Void lambda$parallelRadixSort$0(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, short[] sArray) throws Exception {
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
            int n7 = n6 % 2 == 0 ? 128 : 0;
            int n8 = (1 - n6 % 2) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = sArray[n9] >>> n8 & 0xFF ^ n7;
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
                short s = sArray[n11];
                n12 = s >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        short s2 = s;
                        s = sArray[n15];
                        sArray[n15] = s2;
                        n12 = s >>> n8 & 0xFF ^ n7;
                    }
                    sArray[n11] = s;
                }
                if (n6 < 1 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        ShortArrays.quickSort(sArray, n11, n11 + nArray[n12]);
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

    static int access$000(short[] sArray, int n, int n2, int n3, ShortComparator shortComparator) {
        return ShortArrays.med3(sArray, n, n2, n3, shortComparator);
    }

    static int access$100(short[] sArray, int n, int n2, int n3) {
        return ShortArrays.med3(sArray, n, n2, n3);
    }

    static int access$200(int[] nArray, short[] sArray, int n, int n2, int n3) {
        return ShortArrays.med3Indirect(nArray, sArray, n, n2, n3);
    }

    static int access$300(short[] sArray, short[] sArray2, int n, int n2, int n3) {
        return ShortArrays.med3(sArray, sArray2, n, n2, n3);
    }

    static void access$400(short[] sArray, short[] sArray2, int n, int n2) {
        ShortArrays.swap(sArray, sArray2, n, n2);
    }

    static void access$500(short[] sArray, short[] sArray2, int n, int n2, int n3) {
        ShortArrays.swap(sArray, sArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<short[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(short[] sArray) {
            return java.util.Arrays.hashCode(sArray);
        }

        @Override
        public boolean equals(short[] sArray, short[] sArray2) {
            return java.util.Arrays.equals(sArray, sArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((short[])object, (short[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((short[])object);
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
        private final short[] x;
        private final short[] y;

        public ForkJoinQuickSort2(short[] sArray, short[] sArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = sArray;
            this.y = sArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            short[] sArray = this.x;
            short[] sArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ShortArrays.quickSort(sArray, sArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ShortArrays.access$300(sArray, sArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = ShortArrays.access$300(sArray, sArray2, n5 - n8, n5, n5 + n8);
            n7 = ShortArrays.access$300(sArray, sArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = ShortArrays.access$300(sArray, sArray2, n6, n5, n7);
            short s = sArray[n5];
            short s2 = sArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = Short.compare(sArray[n9], s)) == 0 ? Short.compare(sArray2[n9], s2) : n11) <= 0) {
                    if (n == 0) {
                        ShortArrays.access$400(sArray, sArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = Short.compare(sArray[n2], s)) == 0 ? Short.compare(sArray2[n2], s2) : n11) >= 0) {
                    if (n == 0) {
                        ShortArrays.access$400(sArray, sArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ShortArrays.access$400(sArray, sArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ShortArrays.access$500(sArray, sArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ShortArrays.access$500(sArray, sArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(sArray, sArray2, this.from, this.from + n8), new ForkJoinQuickSort2(sArray, sArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(sArray, sArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(sArray, sArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final short[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, short[] sArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = sArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            short[] sArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ShortArrays.quickSortIndirect(this.perm, sArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ShortArrays.access$200(this.perm, sArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = ShortArrays.access$200(this.perm, sArray, n5 - n8, n5, n5 + n8);
            n7 = ShortArrays.access$200(this.perm, sArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = ShortArrays.access$200(this.perm, sArray, n6, n5, n7);
            short s = sArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Short.compare(sArray[this.perm[n9]], s)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Short.compare(sArray[this.perm[n2]], s)) >= 0) {
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
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, sArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, sArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, sArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, sArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final short[] x;

        public ForkJoinQuickSort(short[] sArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = sArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            short[] sArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ShortArrays.quickSort(sArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ShortArrays.access$100(sArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = ShortArrays.access$100(sArray, n5 - n8, n5, n5 + n8);
            n7 = ShortArrays.access$100(sArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = ShortArrays.access$100(sArray, n6, n5, n7);
            short s = sArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Short.compare(sArray[n9], s)) <= 0) {
                    if (n == 0) {
                        ShortArrays.swap(sArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Short.compare(sArray[n2], s)) >= 0) {
                    if (n == 0) {
                        ShortArrays.swap(sArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ShortArrays.swap(sArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ShortArrays.swap(sArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ShortArrays.swap(sArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(sArray, this.from, this.from + n8), new ForkJoinQuickSort(sArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(sArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(sArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final short[] x;
        private final ShortComparator comp;

        public ForkJoinQuickSortComp(short[] sArray, int n, int n2, ShortComparator shortComparator) {
            this.from = n;
            this.to = n2;
            this.x = sArray;
            this.comp = shortComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            short[] sArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ShortArrays.quickSort(sArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ShortArrays.access$000(sArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = ShortArrays.access$000(sArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = ShortArrays.access$000(sArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = ShortArrays.access$000(sArray, n6, n5, n7, this.comp);
            short s = sArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(sArray[n9], s)) <= 0) {
                    if (n == 0) {
                        ShortArrays.swap(sArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(sArray[n2], s)) >= 0) {
                    if (n == 0) {
                        ShortArrays.swap(sArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ShortArrays.swap(sArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ShortArrays.swap(sArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ShortArrays.swap(sArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(sArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(sArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(sArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(sArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

