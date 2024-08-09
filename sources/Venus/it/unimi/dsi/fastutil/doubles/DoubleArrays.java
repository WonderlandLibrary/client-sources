/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.doubles;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.doubles.DoubleComparator;
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

public final class DoubleArrays {
    public static final double[] EMPTY_ARRAY = new double[0];
    public static final double[] DEFAULT_EMPTY_ARRAY = new double[0];
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
    public static final Hash.Strategy<double[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private DoubleArrays() {
    }

    public static double[] forceCapacity(double[] dArray, int n, int n2) {
        double[] dArray2 = new double[n];
        System.arraycopy(dArray, 0, dArray2, 0, n2);
        return dArray2;
    }

    public static double[] ensureCapacity(double[] dArray, int n) {
        return DoubleArrays.ensureCapacity(dArray, n, dArray.length);
    }

    public static double[] ensureCapacity(double[] dArray, int n, int n2) {
        return n > dArray.length ? DoubleArrays.forceCapacity(dArray, n, n2) : dArray;
    }

    public static double[] grow(double[] dArray, int n) {
        return DoubleArrays.grow(dArray, n, dArray.length);
    }

    public static double[] grow(double[] dArray, int n, int n2) {
        if (n > dArray.length) {
            int n3 = (int)Math.max(Math.min((long)dArray.length + (long)(dArray.length >> 1), 0x7FFFFFF7L), (long)n);
            double[] dArray2 = new double[n3];
            System.arraycopy(dArray, 0, dArray2, 0, n2);
            return dArray2;
        }
        return dArray;
    }

    public static double[] trim(double[] dArray, int n) {
        if (n >= dArray.length) {
            return dArray;
        }
        double[] dArray2 = n == 0 ? EMPTY_ARRAY : new double[n];
        System.arraycopy(dArray, 0, dArray2, 0, n);
        return dArray2;
    }

    public static double[] setLength(double[] dArray, int n) {
        if (n == dArray.length) {
            return dArray;
        }
        if (n < dArray.length) {
            return DoubleArrays.trim(dArray, n);
        }
        return DoubleArrays.ensureCapacity(dArray, n);
    }

    public static double[] copy(double[] dArray, int n, int n2) {
        DoubleArrays.ensureOffsetLength(dArray, n, n2);
        double[] dArray2 = n2 == 0 ? EMPTY_ARRAY : new double[n2];
        System.arraycopy(dArray, n, dArray2, 0, n2);
        return dArray2;
    }

    public static double[] copy(double[] dArray) {
        return (double[])dArray.clone();
    }

    @Deprecated
    public static void fill(double[] dArray, double d) {
        int n = dArray.length;
        while (n-- != 0) {
            dArray[n] = d;
        }
    }

    @Deprecated
    public static void fill(double[] dArray, int n, int n2, double d) {
        DoubleArrays.ensureFromTo(dArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                dArray[n2] = d;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                dArray[i] = d;
            }
        }
    }

    @Deprecated
    public static boolean equals(double[] dArray, double[] dArray2) {
        int n = dArray.length;
        if (n != dArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (Double.doubleToLongBits(dArray[n]) == Double.doubleToLongBits(dArray2[n])) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(double[] dArray, int n, int n2) {
        Arrays.ensureFromTo(dArray.length, n, n2);
    }

    public static void ensureOffsetLength(double[] dArray, int n, int n2) {
        Arrays.ensureOffsetLength(dArray.length, n, n2);
    }

    public static void ensureSameLength(double[] dArray, double[] dArray2) {
        if (dArray.length != dArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + dArray.length + " != " + dArray2.length);
        }
    }

    public static void swap(double[] dArray, int n, int n2) {
        double d = dArray[n];
        dArray[n] = dArray[n2];
        dArray[n2] = d;
    }

    public static void swap(double[] dArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            DoubleArrays.swap(dArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(double[] dArray, int n, int n2, int n3, DoubleComparator doubleComparator) {
        int n4 = doubleComparator.compare(dArray[n], dArray[n2]);
        int n5 = doubleComparator.compare(dArray[n], dArray[n3]);
        int n6 = doubleComparator.compare(dArray[n2], dArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (doubleComparator.compare(dArray[j], dArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            double d = dArray[i];
            dArray[i] = dArray[n3];
            dArray[n3] = d;
        }
    }

    private static void insertionSort(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
        int n3 = n;
        while (++n3 < n2) {
            double d = dArray[n3];
            int n4 = n3;
            double d2 = dArray[n4 - 1];
            while (doubleComparator.compare(d, d2) < 0) {
                dArray[n4] = d2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                d2 = dArray[--n4 - 1];
            }
            dArray[n4] = d;
        }
    }

    public static void quickSort(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            DoubleArrays.selectionSort(dArray, n, n2, doubleComparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = DoubleArrays.med3(dArray, n8, n8 + n10, n8 + 2 * n10, doubleComparator);
            n7 = DoubleArrays.med3(dArray, n7 - n10, n7, n7 + n10, doubleComparator);
            n9 = DoubleArrays.med3(dArray, n9 - 2 * n10, n9 - n10, n9, doubleComparator);
        }
        n7 = DoubleArrays.med3(dArray, n8, n7, n9, doubleComparator);
        double d = dArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = doubleComparator.compare(dArray[n11], d)) <= 0) {
                if (n3 == 0) {
                    DoubleArrays.swap(dArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = doubleComparator.compare(dArray[n4], d)) >= 0) {
                if (n3 == 0) {
                    DoubleArrays.swap(dArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            DoubleArrays.swap(dArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        DoubleArrays.swap(dArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        DoubleArrays.swap(dArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            DoubleArrays.quickSort(dArray, n, n + n3, doubleComparator);
        }
        if ((n3 = n12 - n4) > 1) {
            DoubleArrays.quickSort(dArray, n2 - n3, n2, doubleComparator);
        }
    }

    public static void quickSort(double[] dArray, DoubleComparator doubleComparator) {
        DoubleArrays.quickSort(dArray, 0, dArray.length, doubleComparator);
    }

    public static void parallelQuickSort(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
        if (n2 - n < 8192) {
            DoubleArrays.quickSort(dArray, n, n2, doubleComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(dArray, n, n2, doubleComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(double[] dArray, DoubleComparator doubleComparator) {
        DoubleArrays.parallelQuickSort(dArray, 0, dArray.length, doubleComparator);
    }

    private static int med3(double[] dArray, int n, int n2, int n3) {
        int n4 = Double.compare(dArray[n], dArray[n2]);
        int n5 = Double.compare(dArray[n], dArray[n3]);
        int n6 = Double.compare(dArray[n2], dArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(double[] dArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (Double.compare(dArray[j], dArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            double d = dArray[i];
            dArray[i] = dArray[n3];
            dArray[n3] = d;
        }
    }

    private static void insertionSort(double[] dArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            double d = dArray[n3];
            int n4 = n3;
            double d2 = dArray[n4 - 1];
            while (Double.compare(d, d2) < 0) {
                dArray[n4] = d2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                d2 = dArray[--n4 - 1];
            }
            dArray[n4] = d;
        }
    }

    public static void quickSort(double[] dArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            DoubleArrays.selectionSort(dArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = DoubleArrays.med3(dArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = DoubleArrays.med3(dArray, n7 - n10, n7, n7 + n10);
            n9 = DoubleArrays.med3(dArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = DoubleArrays.med3(dArray, n8, n7, n9);
        double d = dArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Double.compare(dArray[n11], d)) <= 0) {
                if (n3 == 0) {
                    DoubleArrays.swap(dArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Double.compare(dArray[n4], d)) >= 0) {
                if (n3 == 0) {
                    DoubleArrays.swap(dArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            DoubleArrays.swap(dArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        DoubleArrays.swap(dArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        DoubleArrays.swap(dArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            DoubleArrays.quickSort(dArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            DoubleArrays.quickSort(dArray, n2 - n3, n2);
        }
    }

    public static void quickSort(double[] dArray) {
        DoubleArrays.quickSort(dArray, 0, dArray.length);
    }

    public static void parallelQuickSort(double[] dArray, int n, int n2) {
        if (n2 - n < 8192) {
            DoubleArrays.quickSort(dArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(dArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(double[] dArray) {
        DoubleArrays.parallelQuickSort(dArray, 0, dArray.length);
    }

    private static int med3Indirect(int[] nArray, double[] dArray, int n, int n2, int n3) {
        double d = dArray[nArray[n]];
        double d2 = dArray[nArray[n2]];
        double d3 = dArray[nArray[n3]];
        int n4 = Double.compare(d, d2);
        int n5 = Double.compare(d, d3);
        int n6 = Double.compare(d2, d3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, double[] dArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (Double.compare(dArray[n4], dArray[n6]) < 0) {
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

    public static void quickSortIndirect(int[] nArray, double[] dArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            DoubleArrays.insertionSortIndirect(nArray, dArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = DoubleArrays.med3Indirect(nArray, dArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = DoubleArrays.med3Indirect(nArray, dArray, n7 - n10, n7, n7 + n10);
            n9 = DoubleArrays.med3Indirect(nArray, dArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = DoubleArrays.med3Indirect(nArray, dArray, n8, n7, n9);
        double d = dArray[nArray[n7]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Double.compare(dArray[nArray[n11]], d)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Double.compare(dArray[nArray[n4]], d)) >= 0) {
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
            DoubleArrays.quickSortIndirect(nArray, dArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            DoubleArrays.quickSortIndirect(nArray, dArray, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, double[] dArray) {
        DoubleArrays.quickSortIndirect(nArray, dArray, 0, dArray.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, double[] dArray, int n, int n2) {
        if (n2 - n < 8192) {
            DoubleArrays.quickSortIndirect(nArray, dArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, dArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, double[] dArray) {
        DoubleArrays.parallelQuickSortIndirect(nArray, dArray, 0, dArray.length);
    }

    public static void stabilize(int[] nArray, double[] dArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (dArray[nArray[i]] == dArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, double[] dArray) {
        DoubleArrays.stabilize(nArray, dArray, 0, nArray.length);
    }

    private static int med3(double[] dArray, double[] dArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Double.compare(dArray[n], dArray[n2]);
        int n6 = n5 == 0 ? Double.compare(dArray2[n], dArray2[n2]) : n5;
        n5 = Double.compare(dArray[n], dArray[n3]);
        int n7 = n5 == 0 ? Double.compare(dArray2[n], dArray2[n3]) : n5;
        n5 = Double.compare(dArray[n2], dArray[n3]);
        int n8 = n4 = n5 == 0 ? Double.compare(dArray2[n2], dArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(double[] dArray, double[] dArray2, int n, int n2) {
        double d = dArray[n];
        double d2 = dArray2[n];
        dArray[n] = dArray[n2];
        dArray2[n] = dArray2[n2];
        dArray[n2] = d;
        dArray2[n2] = d2;
    }

    private static void swap(double[] dArray, double[] dArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            DoubleArrays.swap(dArray, dArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(double[] dArray, double[] dArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                int n4 = Double.compare(dArray[j], dArray[n3]);
                if (n4 >= 0 && (n4 != 0 || Double.compare(dArray2[j], dArray2[n3]) >= 0)) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            double d = dArray[i];
            dArray[i] = dArray[n3];
            dArray[n3] = d;
            d = dArray2[i];
            dArray2[i] = dArray2[n3];
            dArray2[n3] = d;
        }
    }

    public static void quickSort(double[] dArray, double[] dArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            DoubleArrays.selectionSort(dArray, dArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = DoubleArrays.med3(dArray, dArray2, n8, n8 + n10, n8 + 2 * n10);
            n7 = DoubleArrays.med3(dArray, dArray2, n7 - n10, n7, n7 + n10);
            n9 = DoubleArrays.med3(dArray, dArray2, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = DoubleArrays.med3(dArray, dArray2, n8, n7, n9);
        double d = dArray[n7];
        double d2 = dArray2[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            int n13;
            if (n11 <= n4 && (n3 = (n13 = Double.compare(dArray[n11], d)) == 0 ? Double.compare(dArray2[n11], d2) : n13) <= 0) {
                if (n3 == 0) {
                    DoubleArrays.swap(dArray, dArray2, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = (n13 = Double.compare(dArray[n4], d)) == 0 ? Double.compare(dArray2[n4], d2) : n13) >= 0) {
                if (n3 == 0) {
                    DoubleArrays.swap(dArray, dArray2, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            DoubleArrays.swap(dArray, dArray2, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        DoubleArrays.swap(dArray, dArray2, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        DoubleArrays.swap(dArray, dArray2, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            DoubleArrays.quickSort(dArray, dArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            DoubleArrays.quickSort(dArray, dArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(double[] dArray, double[] dArray2) {
        DoubleArrays.ensureSameLength(dArray, dArray2);
        DoubleArrays.quickSort(dArray, dArray2, 0, dArray.length);
    }

    public static void parallelQuickSort(double[] dArray, double[] dArray2, int n, int n2) {
        if (n2 - n < 8192) {
            DoubleArrays.quickSort(dArray, dArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(dArray, dArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(double[] dArray, double[] dArray2) {
        DoubleArrays.ensureSameLength(dArray, dArray2);
        DoubleArrays.parallelQuickSort(dArray, dArray2, 0, dArray.length);
    }

    public static void mergeSort(double[] dArray, int n, int n2, double[] dArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            DoubleArrays.insertionSort(dArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        DoubleArrays.mergeSort(dArray2, n, n4, dArray);
        DoubleArrays.mergeSort(dArray2, n4, n2, dArray);
        if (Double.compare(dArray2[n4 - 1], dArray2[n4]) <= 0) {
            System.arraycopy(dArray2, n, dArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            dArray[i] = n6 >= n2 || n5 < n4 && Double.compare(dArray2[n5], dArray2[n6]) <= 0 ? dArray2[n5++] : dArray2[n6++];
        }
    }

    public static void mergeSort(double[] dArray, int n, int n2) {
        DoubleArrays.mergeSort(dArray, n, n2, (double[])dArray.clone());
    }

    public static void mergeSort(double[] dArray) {
        DoubleArrays.mergeSort(dArray, 0, dArray.length);
    }

    public static void mergeSort(double[] dArray, int n, int n2, DoubleComparator doubleComparator, double[] dArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            DoubleArrays.insertionSort(dArray, n, n2, doubleComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        DoubleArrays.mergeSort(dArray2, n, n4, doubleComparator, dArray);
        DoubleArrays.mergeSort(dArray2, n4, n2, doubleComparator, dArray);
        if (doubleComparator.compare(dArray2[n4 - 1], dArray2[n4]) <= 0) {
            System.arraycopy(dArray2, n, dArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            dArray[i] = n6 >= n2 || n5 < n4 && doubleComparator.compare(dArray2[n5], dArray2[n6]) <= 0 ? dArray2[n5++] : dArray2[n6++];
        }
    }

    public static void mergeSort(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
        DoubleArrays.mergeSort(dArray, n, n2, doubleComparator, (double[])dArray.clone());
    }

    public static void mergeSort(double[] dArray, DoubleComparator doubleComparator) {
        DoubleArrays.mergeSort(dArray, 0, dArray.length, doubleComparator);
    }

    public static int binarySearch(double[] dArray, int n, int n2, double d) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            double d2 = dArray[n3];
            if (d2 < d) {
                n = n3 + 1;
                continue;
            }
            if (d2 > d) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int binarySearch(double[] dArray, double d) {
        return DoubleArrays.binarySearch(dArray, 0, dArray.length, d);
    }

    public static int binarySearch(double[] dArray, int n, int n2, double d, DoubleComparator doubleComparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            double d2 = dArray[n3];
            int n4 = doubleComparator.compare(d2, d);
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

    public static int binarySearch(double[] dArray, double d, DoubleComparator doubleComparator) {
        return DoubleArrays.binarySearch(dArray, 0, dArray.length, d, doubleComparator);
    }

    private static final long fixDouble(double d) {
        long l = Double.doubleToLongBits(d);
        return l >= 0L ? l : l ^ Long.MAX_VALUE;
    }

    public static void radixSort(double[] dArray) {
        DoubleArrays.radixSort(dArray, 0, dArray.length);
    }

    public static void radixSort(double[] dArray, int n, int n2) {
        if (n2 - n < 1024) {
            DoubleArrays.quickSort(dArray, n, n2);
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
                int n13 = (int)(DoubleArrays.fixDouble(dArray[n12]) >>> n11 & 0xFFL ^ (long)n10);
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
                double d = dArray[n14];
                n15 = (int)(DoubleArrays.fixDouble(d) >>> n11 & 0xFFL ^ (long)n10);
                if (n14 < n6) {
                    while (true) {
                        int n16 = n15;
                        int n17 = nArray5[n16] - 1;
                        nArray5[n16] = n17;
                        int n18 = n17;
                        if (n17 <= n14) break;
                        double d2 = d;
                        d = dArray[n18];
                        dArray[n18] = d2;
                        n15 = (int)(DoubleArrays.fixDouble(d) >>> n11 & 0xFFL ^ (long)n10);
                    }
                    dArray[n14] = d;
                }
                if (n9 < 7 && nArray4[n15] > 1) {
                    if (nArray4[n15] < 1024) {
                        DoubleArrays.quickSort(dArray, n14, n14 + nArray4[n15]);
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

    public static void parallelRadixSort(double[] dArray, int n, int n2) {
        if (n2 - n < 1024) {
            DoubleArrays.quickSort(dArray, n, n2);
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
            executorCompletionService.submit(() -> DoubleArrays.lambda$parallelRadixSort$0(atomicInteger, n4, linkedBlockingQueue, dArray));
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

    public static void parallelRadixSort(double[] dArray) {
        DoubleArrays.parallelRadixSort(dArray, 0, dArray.length);
    }

    public static void radixSortIndirect(int[] nArray, double[] dArray, boolean bl) {
        DoubleArrays.radixSortIndirect(nArray, dArray, 0, nArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, double[] dArray, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            DoubleArrays.insertionSortIndirect(nArray, dArray, n, n2);
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
                int n14 = (int)(DoubleArrays.fixDouble(dArray[nArray[n13]]) >>> n12 & 0xFFL ^ (long)n11);
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
                    int n16 = (int)(DoubleArrays.fixDouble(dArray[nArray[n6]]) >>> n12 & 0xFFL ^ (long)n11);
                    int n17 = nArray7[n16] - 1;
                    nArray7[n16] = n17;
                    nArray2[n17] = nArray[n6];
                }
                System.arraycopy(nArray2, 0, nArray, n8, n9);
                n7 = n8;
                for (n6 = 0; n6 <= n13; ++n6) {
                    if (n10 < 7 && nArray6[n6] > 1) {
                        if (nArray6[n6] < 1024) {
                            DoubleArrays.insertionSortIndirect(nArray, dArray, n7, n7 + nArray6[n6]);
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
                n18 = (int)(DoubleArrays.fixDouble(dArray[n19]) >>> n12 & 0xFFL ^ (long)n11);
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
                        n18 = (int)(DoubleArrays.fixDouble(dArray[n19]) >>> n12 & 0xFFL ^ (long)n11);
                    }
                    nArray[n7] = n19;
                }
                if (n10 < 7 && nArray6[n18] > 1) {
                    if (nArray6[n18] < 1024) {
                        DoubleArrays.insertionSortIndirect(nArray, dArray, n7, n7 + nArray6[n18]);
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

    public static void parallelRadixSortIndirect(int[] nArray, double[] dArray, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            DoubleArrays.radixSortIndirect(nArray, dArray, n, n2, bl);
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
            executorCompletionService.submit(() -> DoubleArrays.lambda$parallelRadixSortIndirect$1(atomicInteger, n4, linkedBlockingQueue, dArray, nArray, bl, nArray2));
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

    public static void parallelRadixSortIndirect(int[] nArray, double[] dArray, boolean bl) {
        DoubleArrays.parallelRadixSortIndirect(nArray, dArray, 0, dArray.length, bl);
    }

    public static void radixSort(double[] dArray, double[] dArray2) {
        DoubleArrays.ensureSameLength(dArray, dArray2);
        DoubleArrays.radixSort(dArray, dArray2, 0, dArray.length);
    }

    public static void radixSort(double[] dArray, double[] dArray2, int n, int n2) {
        if (n2 - n < 1024) {
            DoubleArrays.selectionSort(dArray, dArray2, n, n2);
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
            double[] dArray3 = n10 < 8 ? dArray : dArray2;
            int n12 = (7 - n10 % 8) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = (int)(DoubleArrays.fixDouble(dArray3[n13]) >>> n12 & 0xFFL ^ (long)n11);
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
                double d = dArray[n15];
                double d2 = dArray2[n15];
                n16 = (int)(DoubleArrays.fixDouble(dArray3[n15]) >>> n12 & 0xFFL ^ (long)n11);
                if (n15 < n7) {
                    while (true) {
                        int n17 = n16;
                        int n18 = nArray5[n17] - 1;
                        nArray5[n17] = n18;
                        int n19 = n18;
                        if (n18 <= n15) break;
                        n16 = (int)(DoubleArrays.fixDouble(dArray3[n19]) >>> n12 & 0xFFL ^ (long)n11);
                        double d3 = d;
                        d = dArray[n19];
                        dArray[n19] = d3;
                        d3 = d2;
                        d2 = dArray2[n19];
                        dArray2[n19] = d3;
                    }
                    dArray[n15] = d;
                    dArray2[n15] = d2;
                }
                if (n10 < 15 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        DoubleArrays.selectionSort(dArray, dArray2, n15, n15 + nArray4[n16]);
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

    public static void parallelRadixSort(double[] dArray, double[] dArray2, int n, int n2) {
        if (n2 - n < 1024) {
            DoubleArrays.quickSort(dArray, dArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (dArray.length != dArray2.length) {
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
            executorCompletionService.submit(() -> DoubleArrays.lambda$parallelRadixSort$2(atomicInteger, n5, linkedBlockingQueue, dArray, dArray2));
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

    public static void parallelRadixSort(double[] dArray, double[] dArray2) {
        DoubleArrays.ensureSameLength(dArray, dArray2);
        DoubleArrays.parallelRadixSort(dArray, dArray2, 0, dArray.length);
    }

    private static void insertionSortIndirect(int[] nArray, double[] dArray, double[] dArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (Double.compare(dArray[n4], dArray[n6]) < 0 || Double.compare(dArray[n4], dArray[n6]) == 0 && Double.compare(dArray2[n4], dArray2[n6]) < 0) {
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

    public static void radixSortIndirect(int[] nArray, double[] dArray, double[] dArray2, boolean bl) {
        DoubleArrays.ensureSameLength(dArray, dArray2);
        DoubleArrays.radixSortIndirect(nArray, dArray, dArray2, 0, dArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, double[] dArray, double[] dArray2, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            DoubleArrays.insertionSortIndirect(nArray, dArray, dArray2, n, n2);
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
            double[] dArray3 = n11 < 8 ? dArray : dArray2;
            int n13 = (7 - n11 % 8) * 8;
            int n14 = n9 + n10;
            while (n14-- != n9) {
                int n15 = (int)(DoubleArrays.fixDouble(dArray3[nArray[n14]]) >>> n13 & 0xFFL ^ (long)n12);
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
                    int n17 = (int)(DoubleArrays.fixDouble(dArray3[nArray[n7]]) >>> n13 & 0xFFL ^ (long)n12);
                    int n18 = nArray7[n17] - 1;
                    nArray7[n17] = n18;
                    nArray2[n18] = nArray[n7];
                }
                System.arraycopy(nArray2, 0, nArray, n9, n10);
                n8 = n9;
                for (n7 = 0; n7 < 256; ++n7) {
                    if (n11 < 15 && nArray6[n7] > 1) {
                        if (nArray6[n7] < 1024) {
                            DoubleArrays.insertionSortIndirect(nArray, dArray, dArray2, n8, n8 + nArray6[n7]);
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
                n19 = (int)(DoubleArrays.fixDouble(dArray3[n20]) >>> n13 & 0xFFL ^ (long)n12);
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
                        n19 = (int)(DoubleArrays.fixDouble(dArray3[n20]) >>> n13 & 0xFFL ^ (long)n12);
                    }
                    nArray[n8] = n20;
                }
                if (n11 < 15 && nArray6[n19] > 1) {
                    if (nArray6[n19] < 1024) {
                        DoubleArrays.insertionSortIndirect(nArray, dArray, dArray2, n8, n8 + nArray6[n19]);
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

    private static void selectionSort(double[][] dArray, int n, int n2, int n3) {
        int n4 = dArray.length;
        int n5 = n3 / 8;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7 = i;
            block1: for (n6 = i + 1; n6 < n2; ++n6) {
                for (int j = n5; j < n4; ++j) {
                    if (dArray[j][n6] < dArray[j][n7]) {
                        n7 = n6;
                        continue block1;
                    }
                    if (dArray[j][n6] > dArray[j][n7]) continue block1;
                }
            }
            if (n7 == i) continue;
            n6 = n4;
            while (n6-- != 0) {
                double d = dArray[n6][i];
                dArray[n6][i] = dArray[n6][n7];
                dArray[n6][n7] = d;
            }
        }
    }

    public static void radixSort(double[][] dArray) {
        DoubleArrays.radixSort(dArray, 0, dArray[0].length);
    }

    public static void radixSort(double[][] dArray, int n, int n2) {
        if (n2 - n < 1024) {
            DoubleArrays.selectionSort(dArray, n, n2, 0);
            return;
        }
        int n3 = dArray.length;
        int n4 = 8 * n3 - 1;
        int n5 = n3;
        int n6 = dArray[0].length;
        while (n5-- != 0) {
            if (dArray[n5].length == n6) continue;
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
        double[] dArray2 = new double[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray[--n6];
            int n9 = nArray2[n6];
            int n10 = nArray3[n6];
            int n11 = n10 % 8 == 0 ? 128 : 0;
            double[] dArray3 = dArray[n10 / 8];
            int n12 = (7 - n10 % 8) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = (int)(DoubleArrays.fixDouble(dArray3[n13]) >>> n12 & 0xFFL ^ (long)n11);
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
                    dArray2[n17] = dArray[n17][n15];
                }
                n16 = (int)(DoubleArrays.fixDouble(dArray3[n15]) >>> n12 & 0xFFL ^ (long)n11);
                if (n15 < n7) {
                    block6: while (true) {
                        int n18 = n16;
                        int n19 = nArray5[n18] - 1;
                        nArray5[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n15) break;
                        n16 = (int)(DoubleArrays.fixDouble(dArray3[n20]) >>> n12 & 0xFFL ^ (long)n11);
                        n17 = n3;
                        while (true) {
                            if (n17-- == 0) continue block6;
                            double d = dArray2[n17];
                            dArray2[n17] = dArray[n17][n20];
                            dArray[n17][n20] = d;
                        }
                        break;
                    }
                    n17 = n3;
                    while (n17-- != 0) {
                        dArray[n17][n15] = dArray2[n17];
                    }
                }
                if (n10 < n4 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        DoubleArrays.selectionSort(dArray, n15, n15 + nArray4[n16], n10 + 1);
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

    public static double[] shuffle(double[] dArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            double d = dArray[n + n3];
            dArray[n + n3] = dArray[n + n4];
            dArray[n + n4] = d;
        }
        return dArray;
    }

    public static double[] shuffle(double[] dArray, Random random2) {
        int n = dArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            double d = dArray[n];
            dArray[n] = dArray[n2];
            dArray[n2] = d;
        }
        return dArray;
    }

    public static double[] reverse(double[] dArray) {
        int n = dArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            double d = dArray[n - n2 - 1];
            dArray[n - n2 - 1] = dArray[n2];
            dArray[n2] = d;
        }
        return dArray;
    }

    public static double[] reverse(double[] dArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            double d = dArray[n + n3 - n4 - 1];
            dArray[n + n3 - n4 - 1] = dArray[n + n4];
            dArray[n + n4] = d;
        }
        return dArray;
    }

    private static Void lambda$parallelRadixSort$2(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, double[] dArray, double[] dArray2) throws Exception {
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
            double[] dArray3 = n6 < 8 ? dArray : dArray2;
            int n8 = (7 - n6 % 8) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = (int)(DoubleArrays.fixDouble(dArray3[n9]) >>> n8 & 0xFFL ^ (long)n7);
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
                double d = dArray[n11];
                double d2 = dArray2[n11];
                n12 = (int)(DoubleArrays.fixDouble(dArray3[n11]) >>> n8 & 0xFFL ^ (long)n7);
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        n12 = (int)(DoubleArrays.fixDouble(dArray3[n15]) >>> n8 & 0xFFL ^ (long)n7);
                        double d3 = d;
                        double d4 = d2;
                        d = dArray[n15];
                        d2 = dArray2[n15];
                        dArray[n15] = d3;
                        dArray2[n15] = d4;
                    }
                    dArray[n11] = d;
                    dArray2[n11] = d2;
                }
                if (n6 < 15 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        DoubleArrays.quickSort(dArray, dArray2, n11, n11 + nArray[n12]);
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

    private static Void lambda$parallelRadixSortIndirect$1(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, double[] dArray, int[] nArray, boolean bl, int[] nArray2) throws Exception {
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
                int n10 = (int)(DoubleArrays.fixDouble(dArray[nArray[n9]]) >>> n8 & 0xFFL ^ (long)n7);
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
                    int n12 = (int)(DoubleArrays.fixDouble(dArray[nArray[n2]]) >>> n8 & 0xFFL ^ (long)n7);
                    int n13 = nArray4[n12] - 1;
                    nArray4[n12] = n13;
                    nArray2[n13] = nArray[n2];
                }
                System.arraycopy(nArray2, n4, nArray, n4, n5);
                n11 = n4;
                for (n2 = 0; n2 <= n9; ++n2) {
                    if (n6 < 7 && nArray3[n2] > 1) {
                        if (nArray3[n2] < 1024) {
                            DoubleArrays.radixSortIndirect(nArray, dArray, n11, n11 + nArray3[n2], bl);
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
                    n14 = (int)(DoubleArrays.fixDouble(dArray[n15]) >>> n8 & 0xFFL ^ (long)n7);
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
                            n14 = (int)(DoubleArrays.fixDouble(dArray[n15]) >>> n8 & 0xFFL ^ (long)n7);
                        }
                        nArray[n11] = n15;
                    }
                    if (n6 < 7 && nArray3[n14] > 1) {
                        if (nArray3[n14] < 1024) {
                            DoubleArrays.radixSortIndirect(nArray, dArray, n11, n11 + nArray3[n14], bl);
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

    private static Void lambda$parallelRadixSort$0(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, double[] dArray) throws Exception {
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
                int n10 = (int)(DoubleArrays.fixDouble(dArray[n9]) >>> n8 & 0xFFL ^ (long)n7);
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
                double d = dArray[n11];
                n12 = (int)(DoubleArrays.fixDouble(d) >>> n8 & 0xFFL ^ (long)n7);
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        double d2 = d;
                        d = dArray[n15];
                        dArray[n15] = d2;
                        n12 = (int)(DoubleArrays.fixDouble(d) >>> n8 & 0xFFL ^ (long)n7);
                    }
                    dArray[n11] = d;
                }
                if (n6 < 7 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        DoubleArrays.quickSort(dArray, n11, n11 + nArray[n12]);
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

    static int access$000(double[] dArray, int n, int n2, int n3, DoubleComparator doubleComparator) {
        return DoubleArrays.med3(dArray, n, n2, n3, doubleComparator);
    }

    static int access$100(double[] dArray, int n, int n2, int n3) {
        return DoubleArrays.med3(dArray, n, n2, n3);
    }

    static int access$200(int[] nArray, double[] dArray, int n, int n2, int n3) {
        return DoubleArrays.med3Indirect(nArray, dArray, n, n2, n3);
    }

    static int access$300(double[] dArray, double[] dArray2, int n, int n2, int n3) {
        return DoubleArrays.med3(dArray, dArray2, n, n2, n3);
    }

    static void access$400(double[] dArray, double[] dArray2, int n, int n2) {
        DoubleArrays.swap(dArray, dArray2, n, n2);
    }

    static void access$500(double[] dArray, double[] dArray2, int n, int n2, int n3) {
        DoubleArrays.swap(dArray, dArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<double[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(double[] dArray) {
            return java.util.Arrays.hashCode(dArray);
        }

        @Override
        public boolean equals(double[] dArray, double[] dArray2) {
            return java.util.Arrays.equals(dArray, dArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((double[])object, (double[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((double[])object);
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
        private final double[] x;
        private final double[] y;

        public ForkJoinQuickSort2(double[] dArray, double[] dArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = dArray;
            this.y = dArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            double[] dArray = this.x;
            double[] dArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                DoubleArrays.quickSort(dArray, dArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = DoubleArrays.access$300(dArray, dArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = DoubleArrays.access$300(dArray, dArray2, n5 - n8, n5, n5 + n8);
            n7 = DoubleArrays.access$300(dArray, dArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = DoubleArrays.access$300(dArray, dArray2, n6, n5, n7);
            double d = dArray[n5];
            double d2 = dArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = Double.compare(dArray[n9], d)) == 0 ? Double.compare(dArray2[n9], d2) : n11) <= 0) {
                    if (n == 0) {
                        DoubleArrays.access$400(dArray, dArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = Double.compare(dArray[n2], d)) == 0 ? Double.compare(dArray2[n2], d2) : n11) >= 0) {
                    if (n == 0) {
                        DoubleArrays.access$400(dArray, dArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                DoubleArrays.access$400(dArray, dArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            DoubleArrays.access$500(dArray, dArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            DoubleArrays.access$500(dArray, dArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(dArray, dArray2, this.from, this.from + n8), new ForkJoinQuickSort2(dArray, dArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(dArray, dArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(dArray, dArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final double[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, double[] dArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = dArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            double[] dArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                DoubleArrays.quickSortIndirect(this.perm, dArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = DoubleArrays.access$200(this.perm, dArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = DoubleArrays.access$200(this.perm, dArray, n5 - n8, n5, n5 + n8);
            n7 = DoubleArrays.access$200(this.perm, dArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = DoubleArrays.access$200(this.perm, dArray, n6, n5, n7);
            double d = dArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Double.compare(dArray[this.perm[n9]], d)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Double.compare(dArray[this.perm[n2]], d)) >= 0) {
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
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, dArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, dArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, dArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, dArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final double[] x;

        public ForkJoinQuickSort(double[] dArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = dArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            double[] dArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                DoubleArrays.quickSort(dArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = DoubleArrays.access$100(dArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = DoubleArrays.access$100(dArray, n5 - n8, n5, n5 + n8);
            n7 = DoubleArrays.access$100(dArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = DoubleArrays.access$100(dArray, n6, n5, n7);
            double d = dArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Double.compare(dArray[n9], d)) <= 0) {
                    if (n == 0) {
                        DoubleArrays.swap(dArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Double.compare(dArray[n2], d)) >= 0) {
                    if (n == 0) {
                        DoubleArrays.swap(dArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                DoubleArrays.swap(dArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            DoubleArrays.swap(dArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            DoubleArrays.swap(dArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(dArray, this.from, this.from + n8), new ForkJoinQuickSort(dArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(dArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(dArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final double[] x;
        private final DoubleComparator comp;

        public ForkJoinQuickSortComp(double[] dArray, int n, int n2, DoubleComparator doubleComparator) {
            this.from = n;
            this.to = n2;
            this.x = dArray;
            this.comp = doubleComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            double[] dArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                DoubleArrays.quickSort(dArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = DoubleArrays.access$000(dArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = DoubleArrays.access$000(dArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = DoubleArrays.access$000(dArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = DoubleArrays.access$000(dArray, n6, n5, n7, this.comp);
            double d = dArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(dArray[n9], d)) <= 0) {
                    if (n == 0) {
                        DoubleArrays.swap(dArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(dArray[n2], d)) >= 0) {
                    if (n == 0) {
                        DoubleArrays.swap(dArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                DoubleArrays.swap(dArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            DoubleArrays.swap(dArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            DoubleArrays.swap(dArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(dArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(dArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(dArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(dArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

