/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.bytes.ByteComparator;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public final class ByteArrays {
    public static final byte[] EMPTY_ARRAY = new byte[0];
    public static final byte[] DEFAULT_EMPTY_ARRAY = new byte[0];
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 1;
    private static final int RADIXSORT_NO_REC = 1024;
    private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
    protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
    public static final Hash.Strategy<byte[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private ByteArrays() {
    }

    public static byte[] forceCapacity(byte[] byArray, int n, int n2) {
        byte[] byArray2 = new byte[n];
        System.arraycopy(byArray, 0, byArray2, 0, n2);
        return byArray2;
    }

    public static byte[] ensureCapacity(byte[] byArray, int n) {
        return ByteArrays.ensureCapacity(byArray, n, byArray.length);
    }

    public static byte[] ensureCapacity(byte[] byArray, int n, int n2) {
        return n > byArray.length ? ByteArrays.forceCapacity(byArray, n, n2) : byArray;
    }

    public static byte[] grow(byte[] byArray, int n) {
        return ByteArrays.grow(byArray, n, byArray.length);
    }

    public static byte[] grow(byte[] byArray, int n, int n2) {
        if (n > byArray.length) {
            int n3 = (int)Math.max(Math.min((long)byArray.length + (long)(byArray.length >> 1), 0x7FFFFFF7L), (long)n);
            byte[] byArray2 = new byte[n3];
            System.arraycopy(byArray, 0, byArray2, 0, n2);
            return byArray2;
        }
        return byArray;
    }

    public static byte[] trim(byte[] byArray, int n) {
        if (n >= byArray.length) {
            return byArray;
        }
        byte[] byArray2 = n == 0 ? EMPTY_ARRAY : new byte[n];
        System.arraycopy(byArray, 0, byArray2, 0, n);
        return byArray2;
    }

    public static byte[] setLength(byte[] byArray, int n) {
        if (n == byArray.length) {
            return byArray;
        }
        if (n < byArray.length) {
            return ByteArrays.trim(byArray, n);
        }
        return ByteArrays.ensureCapacity(byArray, n);
    }

    public static byte[] copy(byte[] byArray, int n, int n2) {
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        byte[] byArray2 = n2 == 0 ? EMPTY_ARRAY : new byte[n2];
        System.arraycopy(byArray, n, byArray2, 0, n2);
        return byArray2;
    }

    public static byte[] copy(byte[] byArray) {
        return (byte[])byArray.clone();
    }

    @Deprecated
    public static void fill(byte[] byArray, byte by) {
        int n = byArray.length;
        while (n-- != 0) {
            byArray[n] = by;
        }
    }

    @Deprecated
    public static void fill(byte[] byArray, int n, int n2, byte by) {
        ByteArrays.ensureFromTo(byArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                byArray[n2] = by;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                byArray[i] = by;
            }
        }
    }

    @Deprecated
    public static boolean equals(byte[] byArray, byte[] byArray2) {
        int n = byArray.length;
        if (n != byArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (byArray[n] == byArray2[n]) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(byte[] byArray, int n, int n2) {
        Arrays.ensureFromTo(byArray.length, n, n2);
    }

    public static void ensureOffsetLength(byte[] byArray, int n, int n2) {
        Arrays.ensureOffsetLength(byArray.length, n, n2);
    }

    public static void ensureSameLength(byte[] byArray, byte[] byArray2) {
        if (byArray.length != byArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + byArray.length + " != " + byArray2.length);
        }
    }

    public static void swap(byte[] byArray, int n, int n2) {
        byte by = byArray[n];
        byArray[n] = byArray[n2];
        byArray[n2] = by;
    }

    public static void swap(byte[] byArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            ByteArrays.swap(byArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(byte[] byArray, int n, int n2, int n3, ByteComparator byteComparator) {
        int n4 = byteComparator.compare(byArray[n], byArray[n2]);
        int n5 = byteComparator.compare(byArray[n], byArray[n3]);
        int n6 = byteComparator.compare(byArray[n2], byArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (byteComparator.compare(byArray[n3], byArray[n4]) >= 0) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = byArray[i];
            byArray[i] = byArray[n4];
            byArray[n4] = n3;
        }
    }

    private static void insertionSort(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        int n3 = n;
        while (++n3 < n2) {
            byte by = byArray[n3];
            int n4 = n3;
            byte by2 = byArray[n4 - 1];
            while (byteComparator.compare(by, by2) < 0) {
                byArray[n4] = by2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                by2 = byArray[--n4 - 1];
            }
            byArray[n4] = by;
        }
    }

    public static void quickSort(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        int n3;
        int n4;
        int n5;
        byte by;
        int n6 = n2 - n;
        if (n6 < 16) {
            ByteArrays.selectionSort(byArray, n, n2, byteComparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            by = n6 / 8;
            n8 = ByteArrays.med3(byArray, n8, n8 + by, n8 + 2 * by, byteComparator);
            n7 = ByteArrays.med3(byArray, n7 - by, n7, n7 + by, byteComparator);
            n9 = ByteArrays.med3(byArray, n9 - 2 * by, n9 - by, n9, byteComparator);
        }
        n7 = ByteArrays.med3(byArray, n8, n7, n9, byteComparator);
        by = byArray[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = byteComparator.compare(byArray[n10], by)) <= 0) {
                if (n3 == 0) {
                    ByteArrays.swap(byArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = byteComparator.compare(byArray[n4], by)) >= 0) {
                if (n3 == 0) {
                    ByteArrays.swap(byArray, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            ByteArrays.swap(byArray, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        ByteArrays.swap(byArray, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        ByteArrays.swap(byArray, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            ByteArrays.quickSort(byArray, n, n + n3, byteComparator);
        }
        if ((n3 = n11 - n4) > 1) {
            ByteArrays.quickSort(byArray, n2 - n3, n2, byteComparator);
        }
    }

    public static void quickSort(byte[] byArray, ByteComparator byteComparator) {
        ByteArrays.quickSort(byArray, 0, byArray.length, byteComparator);
    }

    public static void parallelQuickSort(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        if (n2 - n < 8192) {
            ByteArrays.quickSort(byArray, n, n2, byteComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(byArray, n, n2, byteComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(byte[] byArray, ByteComparator byteComparator) {
        ByteArrays.parallelQuickSort(byArray, 0, byArray.length, byteComparator);
    }

    private static int med3(byte[] byArray, int n, int n2, int n3) {
        int n4 = Byte.compare(byArray[n], byArray[n2]);
        int n5 = Byte.compare(byArray[n], byArray[n3]);
        int n6 = Byte.compare(byArray[n2], byArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(byte[] byArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (byArray[n3] >= byArray[n4]) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = byArray[i];
            byArray[i] = byArray[n4];
            byArray[n4] = n3;
        }
    }

    private static void insertionSort(byte[] byArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            byte by = byArray[n3];
            int n4 = n3;
            byte by2 = byArray[n4 - 1];
            while (by < by2) {
                byArray[n4] = by2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                by2 = byArray[--n4 - 1];
            }
            byArray[n4] = by;
        }
    }

    public static void quickSort(byte[] byArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        byte by;
        int n6 = n2 - n;
        if (n6 < 16) {
            ByteArrays.selectionSort(byArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            by = n6 / 8;
            n8 = ByteArrays.med3(byArray, n8, n8 + by, n8 + 2 * by);
            n7 = ByteArrays.med3(byArray, n7 - by, n7, n7 + by);
            n9 = ByteArrays.med3(byArray, n9 - 2 * by, n9 - by, n9);
        }
        n7 = ByteArrays.med3(byArray, n8, n7, n9);
        by = byArray[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = Byte.compare(byArray[n10], by)) <= 0) {
                if (n3 == 0) {
                    ByteArrays.swap(byArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = Byte.compare(byArray[n4], by)) >= 0) {
                if (n3 == 0) {
                    ByteArrays.swap(byArray, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            ByteArrays.swap(byArray, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        ByteArrays.swap(byArray, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        ByteArrays.swap(byArray, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            ByteArrays.quickSort(byArray, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            ByteArrays.quickSort(byArray, n2 - n3, n2);
        }
    }

    public static void quickSort(byte[] byArray) {
        ByteArrays.quickSort(byArray, 0, byArray.length);
    }

    public static void parallelQuickSort(byte[] byArray, int n, int n2) {
        if (n2 - n < 8192) {
            ByteArrays.quickSort(byArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(byArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(byte[] byArray) {
        ByteArrays.parallelQuickSort(byArray, 0, byArray.length);
    }

    private static int med3Indirect(int[] nArray, byte[] byArray, int n, int n2, int n3) {
        byte by = byArray[nArray[n]];
        byte by2 = byArray[nArray[n2]];
        byte by3 = byArray[nArray[n3]];
        int n4 = Byte.compare(by, by2);
        int n5 = Byte.compare(by, by3);
        int n6 = Byte.compare(by2, by3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, byte[] byArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (byArray[n4] < byArray[n6]) {
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

    public static void quickSortIndirect(int[] nArray, byte[] byArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        byte by;
        int n6 = n2 - n;
        if (n6 < 16) {
            ByteArrays.insertionSortIndirect(nArray, byArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            by = n6 / 8;
            n8 = ByteArrays.med3Indirect(nArray, byArray, n8, n8 + by, n8 + 2 * by);
            n7 = ByteArrays.med3Indirect(nArray, byArray, n7 - by, n7, n7 + by);
            n9 = ByteArrays.med3Indirect(nArray, byArray, n9 - 2 * by, n9 - by, n9);
        }
        n7 = ByteArrays.med3Indirect(nArray, byArray, n8, n7, n9);
        by = byArray[nArray[n7]];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = Byte.compare(byArray[nArray[n10]], by)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = Byte.compare(byArray[nArray[n4]], by)) >= 0) {
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
            ByteArrays.quickSortIndirect(nArray, byArray, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            ByteArrays.quickSortIndirect(nArray, byArray, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, byte[] byArray) {
        ByteArrays.quickSortIndirect(nArray, byArray, 0, byArray.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, byte[] byArray, int n, int n2) {
        if (n2 - n < 8192) {
            ByteArrays.quickSortIndirect(nArray, byArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, byArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, byte[] byArray) {
        ByteArrays.parallelQuickSortIndirect(nArray, byArray, 0, byArray.length);
    }

    public static void stabilize(int[] nArray, byte[] byArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (byArray[nArray[i]] == byArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, byte[] byArray) {
        ByteArrays.stabilize(nArray, byArray, 0, nArray.length);
    }

    private static int med3(byte[] byArray, byte[] byArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Byte.compare(byArray[n], byArray[n2]);
        int n6 = n5 == 0 ? Byte.compare(byArray2[n], byArray2[n2]) : n5;
        n5 = Byte.compare(byArray[n], byArray[n3]);
        int n7 = n5 == 0 ? Byte.compare(byArray2[n], byArray2[n3]) : n5;
        n5 = Byte.compare(byArray[n2], byArray[n3]);
        int n8 = n4 = n5 == 0 ? Byte.compare(byArray2[n2], byArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(byte[] byArray, byte[] byArray2, int n, int n2) {
        byte by = byArray[n];
        byte by2 = byArray2[n];
        byArray[n] = byArray[n2];
        byArray2[n] = byArray2[n2];
        byArray[n2] = by;
        byArray2[n2] = by2;
    }

    private static void swap(byte[] byArray, byte[] byArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            ByteArrays.swap(byArray, byArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(byte[] byArray, byte[] byArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                int n5 = Byte.compare(byArray[n3], byArray[n4]);
                if (n5 >= 0 && (n5 != 0 || byArray2[n3] >= byArray2[n4])) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = byArray[i];
            byArray[i] = byArray[n4];
            byArray[n4] = n3;
            n3 = byArray2[i];
            byArray2[i] = byArray2[n4];
            byArray2[n4] = n3;
        }
    }

    public static void quickSort(byte[] byArray, byte[] byArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        byte by;
        int n6 = n2 - n;
        if (n6 < 16) {
            ByteArrays.selectionSort(byArray, byArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            by = n6 / 8;
            n8 = ByteArrays.med3(byArray, byArray2, n8, n8 + by, n8 + 2 * by);
            n7 = ByteArrays.med3(byArray, byArray2, n7 - by, n7, n7 + by);
            n9 = ByteArrays.med3(byArray, byArray2, n9 - 2 * by, n9 - by, n9);
        }
        n7 = ByteArrays.med3(byArray, byArray2, n8, n7, n9);
        by = byArray[n7];
        byte by2 = byArray2[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            int n12;
            if (n10 <= n4 && (n3 = (n12 = Byte.compare(byArray[n10], by)) == 0 ? Byte.compare(byArray2[n10], by2) : n12) <= 0) {
                if (n3 == 0) {
                    ByteArrays.swap(byArray, byArray2, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = (n12 = Byte.compare(byArray[n4], by)) == 0 ? Byte.compare(byArray2[n4], by2) : n12) >= 0) {
                if (n3 == 0) {
                    ByteArrays.swap(byArray, byArray2, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            ByteArrays.swap(byArray, byArray2, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        ByteArrays.swap(byArray, byArray2, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        ByteArrays.swap(byArray, byArray2, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            ByteArrays.quickSort(byArray, byArray2, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            ByteArrays.quickSort(byArray, byArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(byte[] byArray, byte[] byArray2) {
        ByteArrays.ensureSameLength(byArray, byArray2);
        ByteArrays.quickSort(byArray, byArray2, 0, byArray.length);
    }

    public static void parallelQuickSort(byte[] byArray, byte[] byArray2, int n, int n2) {
        if (n2 - n < 8192) {
            ByteArrays.quickSort(byArray, byArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(byArray, byArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(byte[] byArray, byte[] byArray2) {
        ByteArrays.ensureSameLength(byArray, byArray2);
        ByteArrays.parallelQuickSort(byArray, byArray2, 0, byArray.length);
    }

    public static void mergeSort(byte[] byArray, int n, int n2, byte[] byArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            ByteArrays.insertionSort(byArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        ByteArrays.mergeSort(byArray2, n, n4, byArray);
        ByteArrays.mergeSort(byArray2, n4, n2, byArray);
        if (byArray2[n4 - 1] <= byArray2[n4]) {
            System.arraycopy(byArray2, n, byArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            byArray[i] = n6 >= n2 || n5 < n4 && byArray2[n5] <= byArray2[n6] ? byArray2[n5++] : byArray2[n6++];
        }
    }

    public static void mergeSort(byte[] byArray, int n, int n2) {
        ByteArrays.mergeSort(byArray, n, n2, (byte[])byArray.clone());
    }

    public static void mergeSort(byte[] byArray) {
        ByteArrays.mergeSort(byArray, 0, byArray.length);
    }

    public static void mergeSort(byte[] byArray, int n, int n2, ByteComparator byteComparator, byte[] byArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            ByteArrays.insertionSort(byArray, n, n2, byteComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        ByteArrays.mergeSort(byArray2, n, n4, byteComparator, byArray);
        ByteArrays.mergeSort(byArray2, n4, n2, byteComparator, byArray);
        if (byteComparator.compare(byArray2[n4 - 1], byArray2[n4]) <= 0) {
            System.arraycopy(byArray2, n, byArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            byArray[i] = n6 >= n2 || n5 < n4 && byteComparator.compare(byArray2[n5], byArray2[n6]) <= 0 ? byArray2[n5++] : byArray2[n6++];
        }
    }

    public static void mergeSort(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
        ByteArrays.mergeSort(byArray, n, n2, byteComparator, (byte[])byArray.clone());
    }

    public static void mergeSort(byte[] byArray, ByteComparator byteComparator) {
        ByteArrays.mergeSort(byArray, 0, byArray.length, byteComparator);
    }

    public static int binarySearch(byte[] byArray, int n, int n2, byte by) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            byte by2 = byArray[n3];
            if (by2 < by) {
                n = n3 + 1;
                continue;
            }
            if (by2 > by) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int binarySearch(byte[] byArray, byte by) {
        return ByteArrays.binarySearch(byArray, 0, byArray.length, by);
    }

    public static int binarySearch(byte[] byArray, int n, int n2, byte by, ByteComparator byteComparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            byte by2 = byArray[n3];
            int n4 = byteComparator.compare(by2, by);
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

    public static int binarySearch(byte[] byArray, byte by, ByteComparator byteComparator) {
        return ByteArrays.binarySearch(byArray, 0, byArray.length, by, byteComparator);
    }

    public static void radixSort(byte[] byArray) {
        ByteArrays.radixSort(byArray, 0, byArray.length);
    }

    public static void radixSort(byte[] byArray, int n, int n2) {
        if (n2 - n < 1024) {
            ByteArrays.quickSort(byArray, n, n2);
            return;
        }
        boolean bl = false;
        boolean bl2 = true;
        int n3 = 0;
        int[] nArray = new int[1];
        int[] nArray2 = new int[1];
        int[] nArray3 = new int[1];
        nArray[n3] = n;
        nArray2[n3] = n2 - n;
        nArray3[n3++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        while (n3 > 0) {
            int n4;
            int n5 = nArray[--n3];
            int n6 = nArray2[n3];
            int n7 = nArray3[n3];
            int n8 = n7 % 1 == 0 ? 128 : 0;
            int n9 = (0 - n7 % 1) * 8;
            int n10 = n5 + n6;
            while (n10-- != n5) {
                int n11 = byArray[n10] >>> n9 & 0xFF ^ n8;
                nArray4[n11] = nArray4[n11] + 1;
            }
            n10 = -1;
            int n12 = n5;
            for (n4 = 0; n4 < 256; ++n4) {
                if (nArray4[n4] != 0) {
                    n10 = n4;
                }
                nArray5[n4] = n12 += nArray4[n4];
            }
            n4 = n5 + n6 - nArray4[n10];
            int n13 = -1;
            for (n12 = n5; n12 <= n4; n12 += nArray4[n13]) {
                byte by = byArray[n12];
                n13 = by >>> n9 & 0xFF ^ n8;
                if (n12 < n4) {
                    while (true) {
                        int n14 = n13;
                        int n15 = nArray5[n14] - 1;
                        nArray5[n14] = n15;
                        int n16 = n15;
                        if (n15 <= n12) break;
                        byte by2 = by;
                        by = byArray[n16];
                        byArray[n16] = by2;
                        n13 = by >>> n9 & 0xFF ^ n8;
                    }
                    byArray[n12] = by;
                }
                if (n7 < 0 && nArray4[n13] > 1) {
                    if (nArray4[n13] < 1024) {
                        ByteArrays.quickSort(byArray, n12, n12 + nArray4[n13]);
                    } else {
                        nArray[n3] = n12;
                        nArray2[n3] = nArray4[n13];
                        nArray3[n3++] = n7 + 1;
                    }
                }
                nArray4[n13] = 0;
            }
        }
    }

    public static void parallelRadixSort(byte[] byArray, int n, int n2) {
        if (n2 - n < 1024) {
            ByteArrays.quickSort(byArray, n, n2);
            return;
        }
        boolean bl = false;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n3 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n3, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n4 = n3;
        while (n4-- != 0) {
            executorCompletionService.submit(() -> ByteArrays.lambda$parallelRadixSort$0(atomicInteger, n3, linkedBlockingQueue, byArray));
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

    public static void parallelRadixSort(byte[] byArray) {
        ByteArrays.parallelRadixSort(byArray, 0, byArray.length);
    }

    public static void radixSortIndirect(int[] nArray, byte[] byArray, boolean bl) {
        ByteArrays.radixSortIndirect(nArray, byArray, 0, nArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, byte[] byArray, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            ByteArrays.insertionSortIndirect(nArray, byArray, n, n2);
            return;
        }
        boolean bl2 = false;
        boolean bl3 = true;
        int n3 = 0;
        int[] nArray3 = new int[1];
        int[] nArray4 = new int[1];
        int[] nArray5 = new int[1];
        nArray3[n3] = n;
        nArray4[n3] = n2 - n;
        nArray5[n3++] = 0;
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[256];
        int[] nArray8 = nArray2 = bl ? new int[nArray.length] : null;
        while (n3 > 0) {
            int n4;
            int n5;
            int n6 = nArray3[--n3];
            int n7 = nArray4[n3];
            int n8 = nArray5[n3];
            int n9 = n8 % 1 == 0 ? 128 : 0;
            int n10 = (0 - n8 % 1) * 8;
            int n11 = n6 + n7;
            while (n11-- != n6) {
                int n12 = byArray[nArray[n11]] >>> n10 & 0xFF ^ n9;
                nArray6[n12] = nArray6[n12] + 1;
            }
            n11 = -1;
            int n13 = n5 = bl ? 0 : n6;
            for (n4 = 0; n4 < 256; ++n4) {
                if (nArray6[n4] != 0) {
                    n11 = n4;
                }
                nArray7[n4] = n5 += nArray6[n4];
            }
            if (bl) {
                n4 = n6 + n7;
                while (n4-- != n6) {
                    int n14 = byArray[nArray[n4]] >>> n10 & 0xFF ^ n9;
                    int n15 = nArray7[n14] - 1;
                    nArray7[n14] = n15;
                    nArray2[n15] = nArray[n4];
                }
                System.arraycopy(nArray2, 0, nArray, n6, n7);
                n5 = n6;
                for (n4 = 0; n4 <= n11; ++n4) {
                    if (n8 < 0 && nArray6[n4] > 1) {
                        if (nArray6[n4] < 1024) {
                            ByteArrays.insertionSortIndirect(nArray, byArray, n5, n5 + nArray6[n4]);
                        } else {
                            nArray3[n3] = n5;
                            nArray4[n3] = nArray6[n4];
                            nArray5[n3++] = n8 + 1;
                        }
                    }
                    n5 += nArray6[n4];
                }
                java.util.Arrays.fill(nArray6, 0);
                continue;
            }
            n4 = n6 + n7 - nArray6[n11];
            int n16 = -1;
            for (n5 = n6; n5 <= n4; n5 += nArray6[n16]) {
                int n17 = nArray[n5];
                n16 = byArray[n17] >>> n10 & 0xFF ^ n9;
                if (n5 < n4) {
                    while (true) {
                        int n18 = n16;
                        int n19 = nArray7[n18] - 1;
                        nArray7[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n5) break;
                        int n21 = n17;
                        n17 = nArray[n20];
                        nArray[n20] = n21;
                        n16 = byArray[n17] >>> n10 & 0xFF ^ n9;
                    }
                    nArray[n5] = n17;
                }
                if (n8 < 0 && nArray6[n16] > 1) {
                    if (nArray6[n16] < 1024) {
                        ByteArrays.insertionSortIndirect(nArray, byArray, n5, n5 + nArray6[n16]);
                    } else {
                        nArray3[n3] = n5;
                        nArray4[n3] = nArray6[n16];
                        nArray5[n3++] = n8 + 1;
                    }
                }
                nArray6[n16] = 0;
            }
        }
    }

    public static void parallelRadixSortIndirect(int[] nArray, byte[] byArray, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            ByteArrays.radixSortIndirect(nArray, byArray, n, n2, bl);
            return;
        }
        boolean bl2 = false;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n3 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n3, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int[] nArray2 = bl ? new int[nArray.length] : null;
        int n4 = n3;
        while (n4-- != 0) {
            executorCompletionService.submit(() -> ByteArrays.lambda$parallelRadixSortIndirect$1(atomicInteger, n3, linkedBlockingQueue, byArray, nArray, bl, nArray2));
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

    public static void parallelRadixSortIndirect(int[] nArray, byte[] byArray, boolean bl) {
        ByteArrays.parallelRadixSortIndirect(nArray, byArray, 0, byArray.length, bl);
    }

    public static void radixSort(byte[] byArray, byte[] byArray2) {
        ByteArrays.ensureSameLength(byArray, byArray2);
        ByteArrays.radixSort(byArray, byArray2, 0, byArray.length);
    }

    public static void radixSort(byte[] byArray, byte[] byArray2, int n, int n2) {
        if (n2 - n < 1024) {
            ByteArrays.selectionSort(byArray, byArray2, n, n2);
            return;
        }
        int n3 = 2;
        boolean bl = true;
        int n4 = 256;
        int n5 = 0;
        int[] nArray = new int[256];
        int[] nArray2 = new int[256];
        int[] nArray3 = new int[256];
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
            int n10 = n9 % 1 == 0 ? 128 : 0;
            byte[] byArray3 = n9 < 1 ? byArray : byArray2;
            int n11 = (0 - n9 % 1) * 8;
            int n12 = n7 + n8;
            while (n12-- != n7) {
                int n13 = byArray3[n12] >>> n11 & 0xFF ^ n10;
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
                byte by = byArray[n14];
                byte by2 = byArray2[n14];
                n15 = byArray3[n14] >>> n11 & 0xFF ^ n10;
                if (n14 < n6) {
                    while (true) {
                        int n16 = n15;
                        int n17 = nArray5[n16] - 1;
                        nArray5[n16] = n17;
                        int n18 = n17;
                        if (n17 <= n14) break;
                        n15 = byArray3[n18] >>> n11 & 0xFF ^ n10;
                        byte by3 = by;
                        by = byArray[n18];
                        byArray[n18] = by3;
                        by3 = by2;
                        by2 = byArray2[n18];
                        byArray2[n18] = by3;
                    }
                    byArray[n14] = by;
                    byArray2[n14] = by2;
                }
                if (n9 < 1 && nArray4[n15] > 1) {
                    if (nArray4[n15] < 1024) {
                        ByteArrays.selectionSort(byArray, byArray2, n14, n14 + nArray4[n15]);
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

    public static void parallelRadixSort(byte[] byArray, byte[] byArray2, int n, int n2) {
        if (n2 - n < 1024) {
            ByteArrays.quickSort(byArray, byArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (byArray.length != byArray2.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        boolean bl = true;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(() -> ByteArrays.lambda$parallelRadixSort$2(atomicInteger, n4, linkedBlockingQueue, byArray, byArray2));
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

    public static void parallelRadixSort(byte[] byArray, byte[] byArray2) {
        ByteArrays.ensureSameLength(byArray, byArray2);
        ByteArrays.parallelRadixSort(byArray, byArray2, 0, byArray.length);
    }

    private static void insertionSortIndirect(int[] nArray, byte[] byArray, byte[] byArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (byArray[n4] < byArray[n6] || byArray[n4] == byArray[n6] && byArray2[n4] < byArray2[n6]) {
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

    public static void radixSortIndirect(int[] nArray, byte[] byArray, byte[] byArray2, boolean bl) {
        ByteArrays.ensureSameLength(byArray, byArray2);
        ByteArrays.radixSortIndirect(nArray, byArray, byArray2, 0, byArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, byte[] byArray, byte[] byArray2, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            ByteArrays.insertionSortIndirect(nArray, byArray, byArray2, n, n2);
            return;
        }
        int n3 = 2;
        boolean bl2 = true;
        int n4 = 256;
        int n5 = 0;
        int[] nArray3 = new int[256];
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
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
            int n11 = n10 % 1 == 0 ? 128 : 0;
            byte[] byArray3 = n10 < 1 ? byArray : byArray2;
            int n12 = (0 - n10 % 1) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = byArray3[nArray[n13]] >>> n12 & 0xFF ^ n11;
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
                    int n16 = byArray3[nArray[n6]] >>> n12 & 0xFF ^ n11;
                    int n17 = nArray7[n16] - 1;
                    nArray7[n16] = n17;
                    nArray2[n17] = nArray[n6];
                }
                System.arraycopy(nArray2, 0, nArray, n8, n9);
                n7 = n8;
                for (n6 = 0; n6 < 256; ++n6) {
                    if (n10 < 1 && nArray6[n6] > 1) {
                        if (nArray6[n6] < 1024) {
                            ByteArrays.insertionSortIndirect(nArray, byArray, byArray2, n7, n7 + nArray6[n6]);
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
                n18 = byArray3[n19] >>> n12 & 0xFF ^ n11;
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
                        n18 = byArray3[n19] >>> n12 & 0xFF ^ n11;
                    }
                    nArray[n7] = n19;
                }
                if (n10 < 1 && nArray6[n18] > 1) {
                    if (nArray6[n18] < 1024) {
                        ByteArrays.insertionSortIndirect(nArray, byArray, byArray2, n7, n7 + nArray6[n18]);
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

    private static void selectionSort(byte[][] byArray, int n, int n2, int n3) {
        int n4 = byArray.length;
        int n5 = n3 / 1;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7;
            int n8 = i;
            block1: for (n7 = i + 1; n7 < n2; ++n7) {
                for (n6 = n5; n6 < n4; ++n6) {
                    if (byArray[n6][n7] < byArray[n6][n8]) {
                        n8 = n7;
                        continue block1;
                    }
                    if (byArray[n6][n7] > byArray[n6][n8]) continue block1;
                }
            }
            if (n8 == i) continue;
            n7 = n4;
            while (n7-- != 0) {
                n6 = byArray[n7][i];
                byArray[n7][i] = byArray[n7][n8];
                byArray[n7][n8] = n6;
            }
        }
    }

    public static void radixSort(byte[][] byArray) {
        ByteArrays.radixSort(byArray, 0, byArray[0].length);
    }

    public static void radixSort(byte[][] byArray, int n, int n2) {
        if (n2 - n < 1024) {
            ByteArrays.selectionSort(byArray, n, n2, 0);
            return;
        }
        int n3 = byArray.length;
        int n4 = 1 * n3 - 1;
        int n5 = n3;
        int n6 = byArray[0].length;
        while (n5-- != 0) {
            if (byArray[n5].length == n6) continue;
            throw new IllegalArgumentException("The array of index " + n5 + " has not the same length of the array of index 0.");
        }
        n5 = 255 * (n3 * 1 - 1) + 1;
        n6 = 0;
        int[] nArray = new int[n5];
        int[] nArray2 = new int[n5];
        int[] nArray3 = new int[n5];
        nArray[n6] = n;
        nArray2[n6] = n2 - n;
        nArray3[n6++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        byte[] byArray2 = new byte[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray[--n6];
            int n9 = nArray2[n6];
            int n10 = nArray3[n6];
            int n11 = n10 % 1 == 0 ? 128 : 0;
            byte[] byArray3 = byArray[n10 / 1];
            int n12 = (0 - n10 % 1) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = byArray3[n13] >>> n12 & 0xFF ^ n11;
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
                    byArray2[n17] = byArray[n17][n15];
                }
                n16 = byArray3[n15] >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    block6: while (true) {
                        int n18 = n16;
                        int n19 = nArray5[n18] - 1;
                        nArray5[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n15) break;
                        n16 = byArray3[n20] >>> n12 & 0xFF ^ n11;
                        n17 = n3;
                        while (true) {
                            if (n17-- == 0) continue block6;
                            byte by = byArray2[n17];
                            byArray2[n17] = byArray[n17][n20];
                            byArray[n17][n20] = by;
                        }
                        break;
                    }
                    n17 = n3;
                    while (n17-- != 0) {
                        byArray[n17][n15] = byArray2[n17];
                    }
                }
                if (n10 < n4 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        ByteArrays.selectionSort(byArray, n15, n15 + nArray4[n16], n10 + 1);
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

    public static byte[] shuffle(byte[] byArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            byte by = byArray[n + n3];
            byArray[n + n3] = byArray[n + n4];
            byArray[n + n4] = by;
        }
        return byArray;
    }

    public static byte[] shuffle(byte[] byArray, Random random2) {
        int n = byArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            byte by = byArray[n];
            byArray[n] = byArray[n2];
            byArray[n2] = by;
        }
        return byArray;
    }

    public static byte[] reverse(byte[] byArray) {
        int n = byArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            byte by = byArray[n - n2 - 1];
            byArray[n - n2 - 1] = byArray[n2];
            byArray[n2] = by;
        }
        return byArray;
    }

    public static byte[] reverse(byte[] byArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            byte by = byArray[n + n3 - n4 - 1];
            byArray[n + n3 - n4 - 1] = byArray[n + n4];
            byArray[n + n4] = by;
        }
        return byArray;
    }

    private static Void lambda$parallelRadixSort$2(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, byte[] byArray, byte[] byArray2) throws Exception {
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
            int n7 = n6 % 1 == 0 ? 128 : 0;
            byte[] byArray3 = n6 < 1 ? byArray : byArray2;
            int n8 = (0 - n6 % 1) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = byArray3[n9] >>> n8 & 0xFF ^ n7;
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
                byte by = byArray[n11];
                byte by2 = byArray2[n11];
                n12 = byArray3[n11] >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        n12 = byArray3[n15] >>> n8 & 0xFF ^ n7;
                        byte by3 = by;
                        byte by4 = by2;
                        by = byArray[n15];
                        by2 = byArray2[n15];
                        byArray[n15] = by3;
                        byArray2[n15] = by4;
                    }
                    byArray[n11] = by;
                    byArray2[n11] = by2;
                }
                if (n6 < 1 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        ByteArrays.quickSort(byArray, byArray2, n11, n11 + nArray[n12]);
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

    private static Void lambda$parallelRadixSortIndirect$1(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, byte[] byArray, int[] nArray, boolean bl, int[] nArray2) throws Exception {
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
            int n7 = n6 % 1 == 0 ? 128 : 0;
            int n8 = (0 - n6 % 1) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = byArray[nArray[n9]] >>> n8 & 0xFF ^ n7;
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
                    int n12 = byArray[nArray[n2]] >>> n8 & 0xFF ^ n7;
                    int n13 = nArray4[n12] - 1;
                    nArray4[n12] = n13;
                    nArray2[n13] = nArray[n2];
                }
                System.arraycopy(nArray2, n4, nArray, n4, n5);
                n11 = n4;
                for (n2 = 0; n2 <= n9; ++n2) {
                    if (n6 < 0 && nArray3[n2] > 1) {
                        if (nArray3[n2] < 1024) {
                            ByteArrays.radixSortIndirect(nArray, byArray, n11, n11 + nArray3[n2], bl);
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
                    n14 = byArray[n15] >>> n8 & 0xFF ^ n7;
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
                            n14 = byArray[n15] >>> n8 & 0xFF ^ n7;
                        }
                        nArray[n11] = n15;
                    }
                    if (n6 < 0 && nArray3[n14] > 1) {
                        if (nArray3[n14] < 1024) {
                            ByteArrays.radixSortIndirect(nArray, byArray, n11, n11 + nArray3[n14], bl);
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

    private static Void lambda$parallelRadixSort$0(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, byte[] byArray) throws Exception {
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
            int n7 = n6 % 1 == 0 ? 128 : 0;
            int n8 = (0 - n6 % 1) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = byArray[n9] >>> n8 & 0xFF ^ n7;
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
                byte by = byArray[n11];
                n12 = by >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        byte by2 = by;
                        by = byArray[n15];
                        byArray[n15] = by2;
                        n12 = by >>> n8 & 0xFF ^ n7;
                    }
                    byArray[n11] = by;
                }
                if (n6 < 0 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        ByteArrays.quickSort(byArray, n11, n11 + nArray[n12]);
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

    static int access$000(byte[] byArray, int n, int n2, int n3, ByteComparator byteComparator) {
        return ByteArrays.med3(byArray, n, n2, n3, byteComparator);
    }

    static int access$100(byte[] byArray, int n, int n2, int n3) {
        return ByteArrays.med3(byArray, n, n2, n3);
    }

    static int access$200(int[] nArray, byte[] byArray, int n, int n2, int n3) {
        return ByteArrays.med3Indirect(nArray, byArray, n, n2, n3);
    }

    static int access$300(byte[] byArray, byte[] byArray2, int n, int n2, int n3) {
        return ByteArrays.med3(byArray, byArray2, n, n2, n3);
    }

    static void access$400(byte[] byArray, byte[] byArray2, int n, int n2) {
        ByteArrays.swap(byArray, byArray2, n, n2);
    }

    static void access$500(byte[] byArray, byte[] byArray2, int n, int n2, int n3) {
        ByteArrays.swap(byArray, byArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<byte[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(byte[] byArray) {
            return java.util.Arrays.hashCode(byArray);
        }

        @Override
        public boolean equals(byte[] byArray, byte[] byArray2) {
            return java.util.Arrays.equals(byArray, byArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((byte[])object, (byte[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((byte[])object);
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
        private final byte[] x;
        private final byte[] y;

        public ForkJoinQuickSort2(byte[] byArray, byte[] byArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = byArray;
            this.y = byArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            byte[] byArray = this.x;
            byte[] byArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ByteArrays.quickSort(byArray, byArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ByteArrays.access$300(byArray, byArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = ByteArrays.access$300(byArray, byArray2, n5 - n8, n5, n5 + n8);
            n7 = ByteArrays.access$300(byArray, byArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = ByteArrays.access$300(byArray, byArray2, n6, n5, n7);
            byte by = byArray[n5];
            byte by2 = byArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = Byte.compare(byArray[n9], by)) == 0 ? Byte.compare(byArray2[n9], by2) : n11) <= 0) {
                    if (n == 0) {
                        ByteArrays.access$400(byArray, byArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = Byte.compare(byArray[n2], by)) == 0 ? Byte.compare(byArray2[n2], by2) : n11) >= 0) {
                    if (n == 0) {
                        ByteArrays.access$400(byArray, byArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ByteArrays.access$400(byArray, byArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ByteArrays.access$500(byArray, byArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ByteArrays.access$500(byArray, byArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(byArray, byArray2, this.from, this.from + n8), new ForkJoinQuickSort2(byArray, byArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(byArray, byArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(byArray, byArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final byte[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, byte[] byArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = byArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            byte[] byArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ByteArrays.quickSortIndirect(this.perm, byArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ByteArrays.access$200(this.perm, byArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = ByteArrays.access$200(this.perm, byArray, n5 - n8, n5, n5 + n8);
            n7 = ByteArrays.access$200(this.perm, byArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = ByteArrays.access$200(this.perm, byArray, n6, n5, n7);
            byte by = byArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Byte.compare(byArray[this.perm[n9]], by)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Byte.compare(byArray[this.perm[n2]], by)) >= 0) {
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
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, byArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, byArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, byArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, byArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final byte[] x;

        public ForkJoinQuickSort(byte[] byArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = byArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            byte[] byArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ByteArrays.quickSort(byArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ByteArrays.access$100(byArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = ByteArrays.access$100(byArray, n5 - n8, n5, n5 + n8);
            n7 = ByteArrays.access$100(byArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = ByteArrays.access$100(byArray, n6, n5, n7);
            byte by = byArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Byte.compare(byArray[n9], by)) <= 0) {
                    if (n == 0) {
                        ByteArrays.swap(byArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Byte.compare(byArray[n2], by)) >= 0) {
                    if (n == 0) {
                        ByteArrays.swap(byArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ByteArrays.swap(byArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ByteArrays.swap(byArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ByteArrays.swap(byArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(byArray, this.from, this.from + n8), new ForkJoinQuickSort(byArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(byArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(byArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final byte[] x;
        private final ByteComparator comp;

        public ForkJoinQuickSortComp(byte[] byArray, int n, int n2, ByteComparator byteComparator) {
            this.from = n;
            this.to = n2;
            this.x = byArray;
            this.comp = byteComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            byte[] byArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ByteArrays.quickSort(byArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ByteArrays.access$000(byArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = ByteArrays.access$000(byArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = ByteArrays.access$000(byArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = ByteArrays.access$000(byArray, n6, n5, n7, this.comp);
            byte by = byArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(byArray[n9], by)) <= 0) {
                    if (n == 0) {
                        ByteArrays.swap(byArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(byArray[n2], by)) >= 0) {
                    if (n == 0) {
                        ByteArrays.swap(byArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ByteArrays.swap(byArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ByteArrays.swap(byArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ByteArrays.swap(byArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(byArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(byArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(byArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(byArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

