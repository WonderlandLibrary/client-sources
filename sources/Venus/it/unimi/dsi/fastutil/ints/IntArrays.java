/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.ints;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.IntComparator;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

public final class IntArrays {
    public static final int[] EMPTY_ARRAY = new int[0];
    public static final int[] DEFAULT_EMPTY_ARRAY = new int[0];
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 4;
    private static final int RADIXSORT_NO_REC = 1024;
    private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
    protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
    public static final Hash.Strategy<int[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private IntArrays() {
    }

    public static int[] forceCapacity(int[] nArray, int n, int n2) {
        int[] nArray2 = new int[n];
        System.arraycopy(nArray, 0, nArray2, 0, n2);
        return nArray2;
    }

    public static int[] ensureCapacity(int[] nArray, int n) {
        return IntArrays.ensureCapacity(nArray, n, nArray.length);
    }

    public static int[] ensureCapacity(int[] nArray, int n, int n2) {
        return n > nArray.length ? IntArrays.forceCapacity(nArray, n, n2) : nArray;
    }

    public static int[] grow(int[] nArray, int n) {
        return IntArrays.grow(nArray, n, nArray.length);
    }

    public static int[] grow(int[] nArray, int n, int n2) {
        if (n > nArray.length) {
            int n3 = (int)Math.max(Math.min((long)nArray.length + (long)(nArray.length >> 1), 0x7FFFFFF7L), (long)n);
            int[] nArray2 = new int[n3];
            System.arraycopy(nArray, 0, nArray2, 0, n2);
            return nArray2;
        }
        return nArray;
    }

    public static int[] trim(int[] nArray, int n) {
        if (n >= nArray.length) {
            return nArray;
        }
        int[] nArray2 = n == 0 ? EMPTY_ARRAY : new int[n];
        System.arraycopy(nArray, 0, nArray2, 0, n);
        return nArray2;
    }

    public static int[] setLength(int[] nArray, int n) {
        if (n == nArray.length) {
            return nArray;
        }
        if (n < nArray.length) {
            return IntArrays.trim(nArray, n);
        }
        return IntArrays.ensureCapacity(nArray, n);
    }

    public static int[] copy(int[] nArray, int n, int n2) {
        IntArrays.ensureOffsetLength(nArray, n, n2);
        int[] nArray2 = n2 == 0 ? EMPTY_ARRAY : new int[n2];
        System.arraycopy(nArray, n, nArray2, 0, n2);
        return nArray2;
    }

    public static int[] copy(int[] nArray) {
        return (int[])nArray.clone();
    }

    @Deprecated
    public static void fill(int[] nArray, int n) {
        int n2 = nArray.length;
        while (n2-- != 0) {
            nArray[n2] = n;
        }
    }

    @Deprecated
    public static void fill(int[] nArray, int n, int n2, int n3) {
        IntArrays.ensureFromTo(nArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                nArray[n2] = n3;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                nArray[i] = n3;
            }
        }
    }

    @Deprecated
    public static boolean equals(int[] nArray, int[] nArray2) {
        int n = nArray.length;
        if (n != nArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (nArray[n] == nArray2[n]) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(int[] nArray, int n, int n2) {
        Arrays.ensureFromTo(nArray.length, n, n2);
    }

    public static void ensureOffsetLength(int[] nArray, int n, int n2) {
        Arrays.ensureOffsetLength(nArray.length, n, n2);
    }

    public static void ensureSameLength(int[] nArray, int[] nArray2) {
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + nArray.length + " != " + nArray2.length);
        }
    }

    public static void swap(int[] nArray, int n, int n2) {
        int n3 = nArray[n];
        nArray[n] = nArray[n2];
        nArray[n2] = n3;
    }

    public static void swap(int[] nArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            IntArrays.swap(nArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(int[] nArray, int n, int n2, int n3, IntComparator intComparator) {
        int n4 = intComparator.compare(nArray[n], nArray[n2]);
        int n5 = intComparator.compare(nArray[n], nArray[n3]);
        int n6 = intComparator.compare(nArray[n2], nArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(int[] nArray, int n, int n2, IntComparator intComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (intComparator.compare(nArray[n3], nArray[n4]) >= 0) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = nArray[i];
            nArray[i] = nArray[n4];
            nArray[n4] = n3;
        }
    }

    private static void insertionSort(int[] nArray, int n, int n2, IntComparator intComparator) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (intComparator.compare(n4, n6) < 0) {
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

    public static void quickSort(int[] nArray, int n, int n2, IntComparator intComparator) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IntArrays.selectionSort(nArray, n, n2, intComparator);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IntArrays.med3(nArray, n9, n9 + n6, n9 + 2 * n6, intComparator);
            n8 = IntArrays.med3(nArray, n8 - n6, n8, n8 + n6, intComparator);
            n10 = IntArrays.med3(nArray, n10 - 2 * n6, n10 - n6, n10, intComparator);
        }
        n8 = IntArrays.med3(nArray, n9, n8, n10, intComparator);
        n6 = nArray[n8];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = intComparator.compare(nArray[n11], n6)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = intComparator.compare(nArray[n4], n6)) >= 0) {
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
            IntArrays.quickSort(nArray, n, n + n3, intComparator);
        }
        if ((n3 = n12 - n4) > 1) {
            IntArrays.quickSort(nArray, n2 - n3, n2, intComparator);
        }
    }

    public static void quickSort(int[] nArray, IntComparator intComparator) {
        IntArrays.quickSort(nArray, 0, nArray.length, intComparator);
    }

    public static void parallelQuickSort(int[] nArray, int n, int n2, IntComparator intComparator) {
        if (n2 - n < 8192) {
            IntArrays.quickSort(nArray, n, n2, intComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(nArray, n, n2, intComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(int[] nArray, IntComparator intComparator) {
        IntArrays.parallelQuickSort(nArray, 0, nArray.length, intComparator);
    }

    private static int med3(int[] nArray, int n, int n2, int n3) {
        int n4 = Integer.compare(nArray[n], nArray[n2]);
        int n5 = Integer.compare(nArray[n], nArray[n3]);
        int n6 = Integer.compare(nArray[n2], nArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(int[] nArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (nArray[n3] >= nArray[n4]) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = nArray[i];
            nArray[i] = nArray[n4];
            nArray[n4] = n3;
        }
    }

    private static void insertionSort(int[] nArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (n4 < n6) {
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

    public static void quickSort(int[] nArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IntArrays.selectionSort(nArray, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IntArrays.med3(nArray, n9, n9 + n6, n9 + 2 * n6);
            n8 = IntArrays.med3(nArray, n8 - n6, n8, n8 + n6);
            n10 = IntArrays.med3(nArray, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = IntArrays.med3(nArray, n9, n8, n10);
        n6 = nArray[n8];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Integer.compare(nArray[n11], n6)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Integer.compare(nArray[n4], n6)) >= 0) {
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
            IntArrays.quickSort(nArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            IntArrays.quickSort(nArray, n2 - n3, n2);
        }
    }

    public static void quickSort(int[] nArray) {
        IntArrays.quickSort(nArray, 0, nArray.length);
    }

    public static void parallelQuickSort(int[] nArray, int n, int n2) {
        if (n2 - n < 8192) {
            IntArrays.quickSort(nArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(nArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(int[] nArray) {
        IntArrays.parallelQuickSort(nArray, 0, nArray.length);
    }

    private static int med3Indirect(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        int n4 = nArray2[nArray[n]];
        int n5 = nArray2[nArray[n2]];
        int n6 = nArray2[nArray[n3]];
        int n7 = Integer.compare(n4, n5);
        int n8 = Integer.compare(n4, n6);
        int n9 = Integer.compare(n5, n6);
        return n7 < 0 ? (n9 < 0 ? n2 : (n8 < 0 ? n3 : n)) : (n9 > 0 ? n2 : (n8 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, int[] nArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (nArray2[n4] < nArray2[n6]) {
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

    public static void quickSortIndirect(int[] nArray, int[] nArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IntArrays.insertionSortIndirect(nArray, nArray2, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IntArrays.med3Indirect(nArray, nArray2, n9, n9 + n6, n9 + 2 * n6);
            n8 = IntArrays.med3Indirect(nArray, nArray2, n8 - n6, n8, n8 + n6);
            n10 = IntArrays.med3Indirect(nArray, nArray2, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = IntArrays.med3Indirect(nArray, nArray2, n9, n8, n10);
        n6 = nArray2[nArray[n8]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Integer.compare(nArray2[nArray[n11]], n6)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Integer.compare(nArray2[nArray[n4]], n6)) >= 0) {
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
            IntArrays.quickSortIndirect(nArray, nArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            IntArrays.quickSortIndirect(nArray, nArray2, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, int[] nArray2) {
        IntArrays.quickSortIndirect(nArray, nArray2, 0, nArray2.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 8192) {
            IntArrays.quickSortIndirect(nArray, nArray2, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, nArray2, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, int[] nArray2) {
        IntArrays.parallelQuickSortIndirect(nArray, nArray2, 0, nArray2.length);
    }

    public static void stabilize(int[] nArray, int[] nArray2, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (nArray2[nArray[i]] == nArray2[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, int[] nArray2) {
        IntArrays.stabilize(nArray, nArray2, 0, nArray.length);
    }

    private static int med3(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Integer.compare(nArray[n], nArray[n2]);
        int n6 = n5 == 0 ? Integer.compare(nArray2[n], nArray2[n2]) : n5;
        n5 = Integer.compare(nArray[n], nArray[n3]);
        int n7 = n5 == 0 ? Integer.compare(nArray2[n], nArray2[n3]) : n5;
        n5 = Integer.compare(nArray[n2], nArray[n3]);
        int n8 = n4 = n5 == 0 ? Integer.compare(nArray2[n2], nArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(int[] nArray, int[] nArray2, int n, int n2) {
        int n3 = nArray[n];
        int n4 = nArray2[n];
        nArray[n] = nArray[n2];
        nArray2[n] = nArray2[n2];
        nArray[n2] = n3;
        nArray2[n2] = n4;
    }

    private static void swap(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            IntArrays.swap(nArray, nArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(int[] nArray, int[] nArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                int n5 = Integer.compare(nArray[n3], nArray[n4]);
                if (n5 >= 0 && (n5 != 0 || nArray2[n3] >= nArray2[n4])) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = nArray[i];
            nArray[i] = nArray[n4];
            nArray[n4] = n3;
            n3 = nArray2[i];
            nArray2[i] = nArray2[n4];
            nArray2[n4] = n3;
        }
    }

    public static void quickSort(int[] nArray, int[] nArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            IntArrays.selectionSort(nArray, nArray2, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = IntArrays.med3(nArray, nArray2, n9, n9 + n6, n9 + 2 * n6);
            n8 = IntArrays.med3(nArray, nArray2, n8 - n6, n8, n8 + n6);
            n10 = IntArrays.med3(nArray, nArray2, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = IntArrays.med3(nArray, nArray2, n9, n8, n10);
        n6 = nArray[n8];
        int n11 = nArray2[n8];
        int n12 = n5 = n;
        int n13 = n4 = n2 - 1;
        while (true) {
            int n14;
            if (n12 <= n4 && (n3 = (n14 = Integer.compare(nArray[n12], n6)) == 0 ? Integer.compare(nArray2[n12], n11) : n14) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, nArray2, n5++, n12);
                }
                ++n12;
                continue;
            }
            while (n4 >= n12 && (n3 = (n14 = Integer.compare(nArray[n4], n6)) == 0 ? Integer.compare(nArray2[n4], n11) : n14) >= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, nArray2, n4, n13--);
                }
                --n4;
            }
            if (n12 > n4) break;
            IntArrays.swap(nArray, nArray2, n12++, n4--);
        }
        n3 = Math.min(n5 - n, n12 - n5);
        IntArrays.swap(nArray, nArray2, n, n12 - n3, n3);
        n3 = Math.min(n13 - n4, n2 - n13 - 1);
        IntArrays.swap(nArray, nArray2, n12, n2 - n3, n3);
        n3 = n12 - n5;
        if (n3 > 1) {
            IntArrays.quickSort(nArray, nArray2, n, n + n3);
        }
        if ((n3 = n13 - n4) > 1) {
            IntArrays.quickSort(nArray, nArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(int[] nArray, int[] nArray2) {
        IntArrays.ensureSameLength(nArray, nArray2);
        IntArrays.quickSort(nArray, nArray2, 0, nArray.length);
    }

    public static void parallelQuickSort(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 8192) {
            IntArrays.quickSort(nArray, nArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(nArray, nArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(int[] nArray, int[] nArray2) {
        IntArrays.ensureSameLength(nArray, nArray2);
        IntArrays.parallelQuickSort(nArray, nArray2, 0, nArray.length);
    }

    public static void mergeSort(int[] nArray, int n, int n2, int[] nArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            IntArrays.insertionSort(nArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        IntArrays.mergeSort(nArray2, n, n4, nArray);
        IntArrays.mergeSort(nArray2, n4, n2, nArray);
        if (nArray2[n4 - 1] <= nArray2[n4]) {
            System.arraycopy(nArray2, n, nArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            nArray[i] = n6 >= n2 || n5 < n4 && nArray2[n5] <= nArray2[n6] ? nArray2[n5++] : nArray2[n6++];
        }
    }

    public static void mergeSort(int[] nArray, int n, int n2) {
        IntArrays.mergeSort(nArray, n, n2, (int[])nArray.clone());
    }

    public static void mergeSort(int[] nArray) {
        IntArrays.mergeSort(nArray, 0, nArray.length);
    }

    public static void mergeSort(int[] nArray, int n, int n2, IntComparator intComparator, int[] nArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            IntArrays.insertionSort(nArray, n, n2, intComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        IntArrays.mergeSort(nArray2, n, n4, intComparator, nArray);
        IntArrays.mergeSort(nArray2, n4, n2, intComparator, nArray);
        if (intComparator.compare(nArray2[n4 - 1], nArray2[n4]) <= 0) {
            System.arraycopy(nArray2, n, nArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            nArray[i] = n6 >= n2 || n5 < n4 && intComparator.compare(nArray2[n5], nArray2[n6]) <= 0 ? nArray2[n5++] : nArray2[n6++];
        }
    }

    public static void mergeSort(int[] nArray, int n, int n2, IntComparator intComparator) {
        IntArrays.mergeSort(nArray, n, n2, intComparator, (int[])nArray.clone());
    }

    public static void mergeSort(int[] nArray, IntComparator intComparator) {
        IntArrays.mergeSort(nArray, 0, nArray.length, intComparator);
    }

    public static int binarySearch(int[] nArray, int n, int n2, int n3) {
        --n2;
        while (n <= n2) {
            int n4 = n + n2 >>> 1;
            int n5 = nArray[n4];
            if (n5 < n3) {
                n = n4 + 1;
                continue;
            }
            if (n5 > n3) {
                n2 = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n + 1);
    }

    public static int binarySearch(int[] nArray, int n) {
        return IntArrays.binarySearch(nArray, 0, nArray.length, n);
    }

    public static int binarySearch(int[] nArray, int n, int n2, int n3, IntComparator intComparator) {
        --n2;
        while (n <= n2) {
            int n4 = n + n2 >>> 1;
            int n5 = nArray[n4];
            int n6 = intComparator.compare(n5, n3);
            if (n6 < 0) {
                n = n4 + 1;
                continue;
            }
            if (n6 > 0) {
                n2 = n4 - 1;
                continue;
            }
            return n4;
        }
        return -(n + 1);
    }

    public static int binarySearch(int[] nArray, int n, IntComparator intComparator) {
        return IntArrays.binarySearch(nArray, 0, nArray.length, n, intComparator);
    }

    public static void radixSort(int[] nArray) {
        IntArrays.radixSort(nArray, 0, nArray.length);
    }

    public static void radixSort(int[] nArray, int n, int n2) {
        if (n2 - n < 1024) {
            IntArrays.quickSort(nArray, n, n2);
            return;
        }
        int n3 = 3;
        int n4 = 766;
        int n5 = 0;
        int[] nArray2 = new int[766];
        int[] nArray3 = new int[766];
        int[] nArray4 = new int[766];
        nArray2[n5] = n;
        nArray3[n5] = n2 - n;
        nArray4[n5++] = 0;
        int[] nArray5 = new int[256];
        int[] nArray6 = new int[256];
        while (n5 > 0) {
            int n6;
            int n7 = nArray2[--n5];
            int n8 = nArray3[n5];
            int n9 = nArray4[n5];
            int n10 = n9 % 4 == 0 ? 128 : 0;
            int n11 = (3 - n9 % 4) * 8;
            int n12 = n7 + n8;
            while (n12-- != n7) {
                int n13 = nArray[n12] >>> n11 & 0xFF ^ n10;
                nArray5[n13] = nArray5[n13] + 1;
            }
            n12 = -1;
            int n14 = n7;
            for (n6 = 0; n6 < 256; ++n6) {
                if (nArray5[n6] != 0) {
                    n12 = n6;
                }
                nArray6[n6] = n14 += nArray5[n6];
            }
            n6 = n7 + n8 - nArray5[n12];
            int n15 = -1;
            for (n14 = n7; n14 <= n6; n14 += nArray5[n15]) {
                int n16 = nArray[n14];
                n15 = n16 >>> n11 & 0xFF ^ n10;
                if (n14 < n6) {
                    while (true) {
                        int n17 = n15;
                        int n18 = nArray6[n17] - 1;
                        nArray6[n17] = n18;
                        int n19 = n18;
                        if (n18 <= n14) break;
                        int n20 = n16;
                        n16 = nArray[n19];
                        nArray[n19] = n20;
                        n15 = n16 >>> n11 & 0xFF ^ n10;
                    }
                    nArray[n14] = n16;
                }
                if (n9 < 3 && nArray5[n15] > 1) {
                    if (nArray5[n15] < 1024) {
                        IntArrays.quickSort(nArray, n14, n14 + nArray5[n15]);
                    } else {
                        nArray2[n5] = n14;
                        nArray3[n5] = nArray5[n15];
                        nArray4[n5++] = n9 + 1;
                    }
                }
                nArray5[n15] = 0;
            }
        }
    }

    public static void parallelRadixSort(int[] nArray, int n, int n2) {
        if (n2 - n < 1024) {
            IntArrays.quickSort(nArray, n, n2);
            return;
        }
        int n3 = 3;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(() -> IntArrays.lambda$parallelRadixSort$0(atomicInteger, n4, linkedBlockingQueue, nArray));
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

    public static void parallelRadixSort(int[] nArray) {
        IntArrays.parallelRadixSort(nArray, 0, nArray.length);
    }

    public static void radixSortIndirect(int[] nArray, int[] nArray2, boolean bl) {
        IntArrays.radixSortIndirect(nArray, nArray2, 0, nArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, int[] nArray2, int n, int n2, boolean bl) {
        int[] nArray3;
        if (n2 - n < 1024) {
            IntArrays.insertionSortIndirect(nArray, nArray2, n, n2);
            return;
        }
        int n3 = 3;
        int n4 = 766;
        int n5 = 0;
        int[] nArray4 = new int[766];
        int[] nArray5 = new int[766];
        int[] nArray6 = new int[766];
        nArray4[n5] = n;
        nArray5[n5] = n2 - n;
        nArray6[n5++] = 0;
        int[] nArray7 = new int[256];
        int[] nArray8 = new int[256];
        int[] nArray9 = nArray3 = bl ? new int[nArray.length] : null;
        while (n5 > 0) {
            int n6;
            int n7;
            int n8 = nArray4[--n5];
            int n9 = nArray5[n5];
            int n10 = nArray6[n5];
            int n11 = n10 % 4 == 0 ? 128 : 0;
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = nArray2[nArray[n13]] >>> n12 & 0xFF ^ n11;
                nArray7[n14] = nArray7[n14] + 1;
            }
            n13 = -1;
            int n15 = n7 = bl ? 0 : n8;
            for (n6 = 0; n6 < 256; ++n6) {
                if (nArray7[n6] != 0) {
                    n13 = n6;
                }
                nArray8[n6] = n7 += nArray7[n6];
            }
            if (bl) {
                n6 = n8 + n9;
                while (n6-- != n8) {
                    int n16 = nArray2[nArray[n6]] >>> n12 & 0xFF ^ n11;
                    int n17 = nArray8[n16] - 1;
                    nArray8[n16] = n17;
                    nArray3[n17] = nArray[n6];
                }
                System.arraycopy(nArray3, 0, nArray, n8, n9);
                n7 = n8;
                for (n6 = 0; n6 <= n13; ++n6) {
                    if (n10 < 3 && nArray7[n6] > 1) {
                        if (nArray7[n6] < 1024) {
                            IntArrays.insertionSortIndirect(nArray, nArray2, n7, n7 + nArray7[n6]);
                        } else {
                            nArray4[n5] = n7;
                            nArray5[n5] = nArray7[n6];
                            nArray6[n5++] = n10 + 1;
                        }
                    }
                    n7 += nArray7[n6];
                }
                java.util.Arrays.fill(nArray7, 0);
                continue;
            }
            n6 = n8 + n9 - nArray7[n13];
            int n18 = -1;
            for (n7 = n8; n7 <= n6; n7 += nArray7[n18]) {
                int n19 = nArray[n7];
                n18 = nArray2[n19] >>> n12 & 0xFF ^ n11;
                if (n7 < n6) {
                    while (true) {
                        int n20 = n18;
                        int n21 = nArray8[n20] - 1;
                        nArray8[n20] = n21;
                        int n22 = n21;
                        if (n21 <= n7) break;
                        int n23 = n19;
                        n19 = nArray[n22];
                        nArray[n22] = n23;
                        n18 = nArray2[n19] >>> n12 & 0xFF ^ n11;
                    }
                    nArray[n7] = n19;
                }
                if (n10 < 3 && nArray7[n18] > 1) {
                    if (nArray7[n18] < 1024) {
                        IntArrays.insertionSortIndirect(nArray, nArray2, n7, n7 + nArray7[n18]);
                    } else {
                        nArray4[n5] = n7;
                        nArray5[n5] = nArray7[n18];
                        nArray6[n5++] = n10 + 1;
                    }
                }
                nArray7[n18] = 0;
            }
        }
    }

    public static void parallelRadixSortIndirect(int[] nArray, int[] nArray2, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            IntArrays.radixSortIndirect(nArray, nArray2, n, n2, bl);
            return;
        }
        int n3 = 3;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int[] nArray3 = bl ? new int[nArray.length] : null;
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(() -> IntArrays.lambda$parallelRadixSortIndirect$1(atomicInteger, n4, linkedBlockingQueue, nArray2, nArray, bl, nArray3));
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

    public static void parallelRadixSortIndirect(int[] nArray, int[] nArray2, boolean bl) {
        IntArrays.parallelRadixSortIndirect(nArray, nArray2, 0, nArray2.length, bl);
    }

    public static void radixSort(int[] nArray, int[] nArray2) {
        IntArrays.ensureSameLength(nArray, nArray2);
        IntArrays.radixSort(nArray, nArray2, 0, nArray.length);
    }

    public static void radixSort(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 1024) {
            IntArrays.selectionSort(nArray, nArray2, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 7;
        int n5 = 1786;
        int n6 = 0;
        int[] nArray3 = new int[1786];
        int[] nArray4 = new int[1786];
        int[] nArray5 = new int[1786];
        nArray3[n6] = n;
        nArray4[n6] = n2 - n;
        nArray5[n6++] = 0;
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[256];
        while (n6 > 0) {
            int n7;
            int n8 = nArray3[--n6];
            int n9 = nArray4[n6];
            int n10 = nArray5[n6];
            int n11 = n10 % 4 == 0 ? 128 : 0;
            int[] nArray8 = n10 < 4 ? nArray : nArray2;
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = nArray8[n13] >>> n12 & 0xFF ^ n11;
                nArray6[n14] = nArray6[n14] + 1;
            }
            n13 = -1;
            int n15 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray6[n7] != 0) {
                    n13 = n7;
                }
                nArray7[n7] = n15 += nArray6[n7];
            }
            n7 = n8 + n9 - nArray6[n13];
            int n16 = -1;
            for (n15 = n8; n15 <= n7; n15 += nArray6[n16]) {
                int n17 = nArray[n15];
                int n18 = nArray2[n15];
                n16 = nArray8[n15] >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    while (true) {
                        int n19 = n16;
                        int n20 = nArray7[n19] - 1;
                        nArray7[n19] = n20;
                        int n21 = n20;
                        if (n20 <= n15) break;
                        n16 = nArray8[n21] >>> n12 & 0xFF ^ n11;
                        int n22 = n17;
                        n17 = nArray[n21];
                        nArray[n21] = n22;
                        n22 = n18;
                        n18 = nArray2[n21];
                        nArray2[n21] = n22;
                    }
                    nArray[n15] = n17;
                    nArray2[n15] = n18;
                }
                if (n10 < 7 && nArray6[n16] > 1) {
                    if (nArray6[n16] < 1024) {
                        IntArrays.selectionSort(nArray, nArray2, n15, n15 + nArray6[n16]);
                    } else {
                        nArray3[n6] = n15;
                        nArray4[n6] = nArray6[n16];
                        nArray5[n6++] = n10 + 1;
                    }
                }
                nArray6[n16] = 0;
            }
        }
    }

    public static void parallelRadixSort(int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 - n < 1024) {
            IntArrays.quickSort(nArray, nArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (nArray.length != nArray2.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int n4 = 7;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n5 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n5, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int n6 = n5;
        while (n6-- != 0) {
            executorCompletionService.submit(() -> IntArrays.lambda$parallelRadixSort$2(atomicInteger, n5, linkedBlockingQueue, nArray, nArray2));
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

    public static void parallelRadixSort(int[] nArray, int[] nArray2) {
        IntArrays.ensureSameLength(nArray, nArray2);
        IntArrays.parallelRadixSort(nArray, nArray2, 0, nArray.length);
    }

    private static void insertionSortIndirect(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (nArray2[n4] < nArray2[n6] || nArray2[n4] == nArray2[n6] && nArray3[n4] < nArray3[n6]) {
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

    public static void radixSortIndirect(int[] nArray, int[] nArray2, int[] nArray3, boolean bl) {
        IntArrays.ensureSameLength(nArray2, nArray3);
        IntArrays.radixSortIndirect(nArray, nArray2, nArray3, 0, nArray2.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2, boolean bl) {
        int[] nArray4;
        if (n2 - n < 1024) {
            IntArrays.insertionSortIndirect(nArray, nArray2, nArray3, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 7;
        int n5 = 1786;
        int n6 = 0;
        int[] nArray5 = new int[1786];
        int[] nArray6 = new int[1786];
        int[] nArray7 = new int[1786];
        nArray5[n6] = n;
        nArray6[n6] = n2 - n;
        nArray7[n6++] = 0;
        int[] nArray8 = new int[256];
        int[] nArray9 = new int[256];
        int[] nArray10 = nArray4 = bl ? new int[nArray.length] : null;
        while (n6 > 0) {
            int n7;
            int n8;
            int n9 = nArray5[--n6];
            int n10 = nArray6[n6];
            int n11 = nArray7[n6];
            int n12 = n11 % 4 == 0 ? 128 : 0;
            int[] nArray11 = n11 < 4 ? nArray2 : nArray3;
            int n13 = (3 - n11 % 4) * 8;
            int n14 = n9 + n10;
            while (n14-- != n9) {
                int n15 = nArray11[nArray[n14]] >>> n13 & 0xFF ^ n12;
                nArray8[n15] = nArray8[n15] + 1;
            }
            n14 = -1;
            int n16 = n8 = bl ? 0 : n9;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray8[n7] != 0) {
                    n14 = n7;
                }
                nArray9[n7] = n8 += nArray8[n7];
            }
            if (bl) {
                n7 = n9 + n10;
                while (n7-- != n9) {
                    int n17 = nArray11[nArray[n7]] >>> n13 & 0xFF ^ n12;
                    int n18 = nArray9[n17] - 1;
                    nArray9[n17] = n18;
                    nArray4[n18] = nArray[n7];
                }
                System.arraycopy(nArray4, 0, nArray, n9, n10);
                n8 = n9;
                for (n7 = 0; n7 < 256; ++n7) {
                    if (n11 < 7 && nArray8[n7] > 1) {
                        if (nArray8[n7] < 1024) {
                            IntArrays.insertionSortIndirect(nArray, nArray2, nArray3, n8, n8 + nArray8[n7]);
                        } else {
                            nArray5[n6] = n8;
                            nArray6[n6] = nArray8[n7];
                            nArray7[n6++] = n11 + 1;
                        }
                    }
                    n8 += nArray8[n7];
                }
                java.util.Arrays.fill(nArray8, 0);
                continue;
            }
            n7 = n9 + n10 - nArray8[n14];
            int n19 = -1;
            for (n8 = n9; n8 <= n7; n8 += nArray8[n19]) {
                int n20 = nArray[n8];
                n19 = nArray11[n20] >>> n13 & 0xFF ^ n12;
                if (n8 < n7) {
                    while (true) {
                        int n21 = n19;
                        int n22 = nArray9[n21] - 1;
                        nArray9[n21] = n22;
                        int n23 = n22;
                        if (n22 <= n8) break;
                        int n24 = n20;
                        n20 = nArray[n23];
                        nArray[n23] = n24;
                        n19 = nArray11[n20] >>> n13 & 0xFF ^ n12;
                    }
                    nArray[n8] = n20;
                }
                if (n11 < 7 && nArray8[n19] > 1) {
                    if (nArray8[n19] < 1024) {
                        IntArrays.insertionSortIndirect(nArray, nArray2, nArray3, n8, n8 + nArray8[n19]);
                    } else {
                        nArray5[n6] = n8;
                        nArray6[n6] = nArray8[n19];
                        nArray7[n6++] = n11 + 1;
                    }
                }
                nArray8[n19] = 0;
            }
        }
    }

    private static void selectionSort(int[][] nArray, int n, int n2, int n3) {
        int n4 = nArray.length;
        int n5 = n3 / 4;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7;
            int n8 = i;
            block1: for (n7 = i + 1; n7 < n2; ++n7) {
                for (n6 = n5; n6 < n4; ++n6) {
                    if (nArray[n6][n7] < nArray[n6][n8]) {
                        n8 = n7;
                        continue block1;
                    }
                    if (nArray[n6][n7] > nArray[n6][n8]) continue block1;
                }
            }
            if (n8 == i) continue;
            n7 = n4;
            while (n7-- != 0) {
                n6 = nArray[n7][i];
                nArray[n7][i] = nArray[n7][n8];
                nArray[n7][n8] = n6;
            }
        }
    }

    public static void radixSort(int[][] nArray) {
        IntArrays.radixSort(nArray, 0, nArray[0].length);
    }

    public static void radixSort(int[][] nArray, int n, int n2) {
        if (n2 - n < 1024) {
            IntArrays.selectionSort(nArray, n, n2, 0);
            return;
        }
        int n3 = nArray.length;
        int n4 = 4 * n3 - 1;
        int n5 = n3;
        int n6 = nArray[0].length;
        while (n5-- != 0) {
            if (nArray[n5].length == n6) continue;
            throw new IllegalArgumentException("The array of index " + n5 + " has not the same length of the array of index 0.");
        }
        n5 = 255 * (n3 * 4 - 1) + 1;
        n6 = 0;
        int[] nArray2 = new int[n5];
        int[] nArray3 = new int[n5];
        int[] nArray4 = new int[n5];
        nArray2[n6] = n;
        nArray3[n6] = n2 - n;
        nArray4[n6++] = 0;
        int[] nArray5 = new int[256];
        int[] nArray6 = new int[256];
        int[] nArray7 = new int[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray2[--n6];
            int n9 = nArray3[n6];
            int n10 = nArray4[n6];
            int n11 = n10 % 4 == 0 ? 128 : 0;
            int[] nArray8 = nArray[n10 / 4];
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = nArray8[n13] >>> n12 & 0xFF ^ n11;
                nArray5[n14] = nArray5[n14] + 1;
            }
            n13 = -1;
            int n15 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray5[n7] != 0) {
                    n13 = n7;
                }
                nArray6[n7] = n15 += nArray5[n7];
            }
            n7 = n8 + n9 - nArray5[n13];
            int n16 = -1;
            for (n15 = n8; n15 <= n7; n15 += nArray5[n16]) {
                int n17 = n3;
                while (n17-- != 0) {
                    nArray7[n17] = nArray[n17][n15];
                }
                n16 = nArray8[n15] >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    block6: while (true) {
                        int n18 = n16;
                        int n19 = nArray6[n18] - 1;
                        nArray6[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n15) break;
                        n16 = nArray8[n20] >>> n12 & 0xFF ^ n11;
                        n17 = n3;
                        while (true) {
                            if (n17-- == 0) continue block6;
                            int n21 = nArray7[n17];
                            nArray7[n17] = nArray[n17][n20];
                            nArray[n17][n20] = n21;
                        }
                        break;
                    }
                    n17 = n3;
                    while (n17-- != 0) {
                        nArray[n17][n15] = nArray7[n17];
                    }
                }
                if (n10 < n4 && nArray5[n16] > 1) {
                    if (nArray5[n16] < 1024) {
                        IntArrays.selectionSort(nArray, n15, n15 + nArray5[n16], n10 + 1);
                    } else {
                        nArray2[n6] = n15;
                        nArray3[n6] = nArray5[n16];
                        nArray4[n6++] = n10 + 1;
                    }
                }
                nArray5[n16] = 0;
            }
        }
    }

    public static int[] shuffle(int[] nArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            int n5 = nArray[n + n3];
            nArray[n + n3] = nArray[n + n4];
            nArray[n + n4] = n5;
        }
        return nArray;
    }

    public static int[] shuffle(int[] nArray, Random random2) {
        int n = nArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            int n3 = nArray[n];
            nArray[n] = nArray[n2];
            nArray[n2] = n3;
        }
        return nArray;
    }

    public static int[] reverse(int[] nArray) {
        int n = nArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            int n3 = nArray[n - n2 - 1];
            nArray[n - n2 - 1] = nArray[n2];
            nArray[n2] = n3;
        }
        return nArray;
    }

    public static int[] reverse(int[] nArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            int n5 = nArray[n + n3 - n4 - 1];
            nArray[n + n3 - n4 - 1] = nArray[n + n4];
            nArray[n + n4] = n5;
        }
        return nArray;
    }

    private static Void lambda$parallelRadixSort$2(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, int[] nArray, int[] nArray2) throws Exception {
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
            int n7 = n6 % 4 == 0 ? 128 : 0;
            int[] nArray5 = n6 < 4 ? nArray : nArray2;
            int n8 = (3 - n6 % 4) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = nArray5[n9] >>> n8 & 0xFF ^ n7;
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
            n2 = n4 + n5 - nArray3[n9];
            int n12 = -1;
            for (n11 = n4; n11 <= n2; n11 += nArray3[n12]) {
                int n13 = nArray[n11];
                int n14 = nArray2[n11];
                n12 = nArray5[n11] >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n15 = n12;
                        int n16 = nArray4[n15] - 1;
                        nArray4[n15] = n16;
                        int n17 = n16;
                        if (n16 <= n11) break;
                        n12 = nArray5[n17] >>> n8 & 0xFF ^ n7;
                        int n18 = n13;
                        int n19 = n14;
                        n13 = nArray[n17];
                        n14 = nArray2[n17];
                        nArray[n17] = n18;
                        nArray2[n17] = n19;
                    }
                    nArray[n11] = n13;
                    nArray2[n11] = n14;
                }
                if (n6 < 7 && nArray3[n12] > 1) {
                    if (nArray3[n12] < 1024) {
                        IntArrays.quickSort(nArray, nArray2, n11, n11 + nArray3[n12]);
                    } else {
                        atomicInteger.incrementAndGet();
                        linkedBlockingQueue.add(new Segment(n11, nArray3[n12], n6 + 1));
                    }
                }
                nArray3[n12] = 0;
            }
            atomicInteger.decrementAndGet();
        }
    }

    private static Void lambda$parallelRadixSortIndirect$1(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, int[] nArray, int[] nArray2, boolean bl, int[] nArray3) throws Exception {
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
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
            int n7 = n6 % 4 == 0 ? 128 : 0;
            int n8 = (3 - n6 % 4) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = nArray[nArray2[n9]] >>> n8 & 0xFF ^ n7;
                nArray4[n10] = nArray4[n10] + 1;
            }
            n9 = -1;
            int n11 = n4;
            for (n2 = 0; n2 < 256; ++n2) {
                if (nArray4[n2] != 0) {
                    n9 = n2;
                }
                nArray5[n2] = n11 += nArray4[n2];
            }
            if (bl) {
                n2 = n4 + n5;
                while (n2-- != n4) {
                    int n12 = nArray[nArray2[n2]] >>> n8 & 0xFF ^ n7;
                    int n13 = nArray5[n12] - 1;
                    nArray5[n12] = n13;
                    nArray3[n13] = nArray2[n2];
                }
                System.arraycopy(nArray3, n4, nArray2, n4, n5);
                n11 = n4;
                for (n2 = 0; n2 <= n9; ++n2) {
                    if (n6 < 3 && nArray4[n2] > 1) {
                        if (nArray4[n2] < 1024) {
                            IntArrays.radixSortIndirect(nArray2, nArray, n11, n11 + nArray4[n2], bl);
                        } else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(n11, nArray4[n2], n6 + 1));
                        }
                    }
                    n11 += nArray4[n2];
                }
                java.util.Arrays.fill(nArray4, 0);
            } else {
                n2 = n4 + n5 - nArray4[n9];
                int n14 = -1;
                for (n11 = n4; n11 <= n2; n11 += nArray4[n14]) {
                    int n15 = nArray2[n11];
                    n14 = nArray[n15] >>> n8 & 0xFF ^ n7;
                    if (n11 < n2) {
                        while (true) {
                            int n16 = n14;
                            int n17 = nArray5[n16] - 1;
                            nArray5[n16] = n17;
                            int n18 = n17;
                            if (n17 <= n11) break;
                            int n19 = n15;
                            n15 = nArray2[n18];
                            nArray2[n18] = n19;
                            n14 = nArray[n15] >>> n8 & 0xFF ^ n7;
                        }
                        nArray2[n11] = n15;
                    }
                    if (n6 < 3 && nArray4[n14] > 1) {
                        if (nArray4[n14] < 1024) {
                            IntArrays.radixSortIndirect(nArray2, nArray, n11, n11 + nArray4[n14], bl);
                        } else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(n11, nArray4[n14], n6 + 1));
                        }
                    }
                    nArray4[n14] = 0;
                }
            }
            atomicInteger.decrementAndGet();
        }
    }

    private static Void lambda$parallelRadixSort$0(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, int[] nArray) throws Exception {
        int[] nArray2 = new int[256];
        int[] nArray3 = new int[256];
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
            int n7 = n6 % 4 == 0 ? 128 : 0;
            int n8 = (3 - n6 % 4) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = nArray[n9] >>> n8 & 0xFF ^ n7;
                nArray2[n10] = nArray2[n10] + 1;
            }
            n9 = -1;
            int n11 = n4;
            for (n2 = 0; n2 < 256; ++n2) {
                if (nArray2[n2] != 0) {
                    n9 = n2;
                }
                nArray3[n2] = n11 += nArray2[n2];
            }
            n2 = n4 + n5 - nArray2[n9];
            int n12 = -1;
            for (n11 = n4; n11 <= n2; n11 += nArray2[n12]) {
                int n13 = nArray[n11];
                n12 = n13 >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n14 = n12;
                        int n15 = nArray3[n14] - 1;
                        nArray3[n14] = n15;
                        int n16 = n15;
                        if (n15 <= n11) break;
                        int n17 = n13;
                        n13 = nArray[n16];
                        nArray[n16] = n17;
                        n12 = n13 >>> n8 & 0xFF ^ n7;
                    }
                    nArray[n11] = n13;
                }
                if (n6 < 3 && nArray2[n12] > 1) {
                    if (nArray2[n12] < 1024) {
                        IntArrays.quickSort(nArray, n11, n11 + nArray2[n12]);
                    } else {
                        atomicInteger.incrementAndGet();
                        linkedBlockingQueue.add(new Segment(n11, nArray2[n12], n6 + 1));
                    }
                }
                nArray2[n12] = 0;
            }
            atomicInteger.decrementAndGet();
        }
    }

    static int access$000(int[] nArray, int n, int n2, int n3, IntComparator intComparator) {
        return IntArrays.med3(nArray, n, n2, n3, intComparator);
    }

    static int access$100(int[] nArray, int n, int n2, int n3) {
        return IntArrays.med3(nArray, n, n2, n3);
    }

    static int access$200(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        return IntArrays.med3Indirect(nArray, nArray2, n, n2, n3);
    }

    static int access$300(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        return IntArrays.med3(nArray, nArray2, n, n2, n3);
    }

    static void access$400(int[] nArray, int[] nArray2, int n, int n2) {
        IntArrays.swap(nArray, nArray2, n, n2);
    }

    static void access$500(int[] nArray, int[] nArray2, int n, int n2, int n3) {
        IntArrays.swap(nArray, nArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<int[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(int[] nArray) {
            return java.util.Arrays.hashCode(nArray);
        }

        @Override
        public boolean equals(int[] nArray, int[] nArray2) {
            return java.util.Arrays.equals(nArray, nArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((int[])object, (int[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((int[])object);
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
        private final int[] x;
        private final int[] y;

        public ForkJoinQuickSort2(int[] nArray, int[] nArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = nArray;
            this.y = nArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            int[] nArray = this.x;
            int[] nArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                IntArrays.quickSort(nArray, nArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = IntArrays.access$300(nArray, nArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = IntArrays.access$300(nArray, nArray2, n5 - n8, n5, n5 + n8);
            n7 = IntArrays.access$300(nArray, nArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = IntArrays.access$300(nArray, nArray2, n6, n5, n7);
            int n9 = nArray[n5];
            int n10 = nArray2[n5];
            int n11 = n3 = this.from;
            int n12 = n2 = this.to - 1;
            while (true) {
                int n13;
                if (n11 <= n2 && (n = (n13 = Integer.compare(nArray[n11], n9)) == 0 ? Integer.compare(nArray2[n11], n10) : n13) <= 0) {
                    if (n == 0) {
                        IntArrays.access$400(nArray, nArray2, n3++, n11);
                    }
                    ++n11;
                    continue;
                }
                while (n2 >= n11 && (n = (n13 = Integer.compare(nArray[n2], n9)) == 0 ? Integer.compare(nArray2[n2], n10) : n13) >= 0) {
                    if (n == 0) {
                        IntArrays.access$400(nArray, nArray2, n2, n12--);
                    }
                    --n2;
                }
                if (n11 > n2) break;
                IntArrays.access$400(nArray, nArray2, n11++, n2--);
            }
            n8 = Math.min(n3 - this.from, n11 - n3);
            IntArrays.access$500(nArray, nArray2, this.from, n11 - n8, n8);
            n8 = Math.min(n12 - n2, this.to - n12 - 1);
            IntArrays.access$500(nArray, nArray2, n11, this.to - n8, n8);
            n8 = n11 - n3;
            n = n12 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(nArray, nArray2, this.from, this.from + n8), new ForkJoinQuickSort2(nArray, nArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(nArray, nArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(nArray, nArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final int[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, int[] nArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = nArray2;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            int[] nArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                IntArrays.quickSortIndirect(this.perm, nArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = IntArrays.access$200(this.perm, nArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = IntArrays.access$200(this.perm, nArray, n5 - n8, n5, n5 + n8);
            n7 = IntArrays.access$200(this.perm, nArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = IntArrays.access$200(this.perm, nArray, n6, n5, n7);
            int n9 = nArray[this.perm[n5]];
            int n10 = n3 = this.from;
            int n11 = n2 = this.to - 1;
            while (true) {
                if (n10 <= n2 && (n = Integer.compare(nArray[this.perm[n10]], n9)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n10);
                    }
                    ++n10;
                    continue;
                }
                while (n2 >= n10 && (n = Integer.compare(nArray[this.perm[n2]], n9)) >= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n2, n11--);
                    }
                    --n2;
                }
                if (n10 > n2) break;
                IntArrays.swap(this.perm, n10++, n2--);
            }
            n8 = Math.min(n3 - this.from, n10 - n3);
            IntArrays.swap(this.perm, this.from, n10 - n8, n8);
            n8 = Math.min(n11 - n2, this.to - n11 - 1);
            IntArrays.swap(this.perm, n10, this.to - n8, n8);
            n8 = n10 - n3;
            n = n11 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, nArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, nArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, nArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, nArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;

        public ForkJoinQuickSort(int[] nArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            int[] nArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                IntArrays.quickSort(nArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = IntArrays.access$100(nArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = IntArrays.access$100(nArray, n5 - n8, n5, n5 + n8);
            n7 = IntArrays.access$100(nArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = IntArrays.access$100(nArray, n6, n5, n7);
            int n9 = nArray[n5];
            int n10 = n3 = this.from;
            int n11 = n2 = this.to - 1;
            while (true) {
                if (n10 <= n2 && (n = Integer.compare(nArray[n10], n9)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(nArray, n3++, n10);
                    }
                    ++n10;
                    continue;
                }
                while (n2 >= n10 && (n = Integer.compare(nArray[n2], n9)) >= 0) {
                    if (n == 0) {
                        IntArrays.swap(nArray, n2, n11--);
                    }
                    --n2;
                }
                if (n10 > n2) break;
                IntArrays.swap(nArray, n10++, n2--);
            }
            n8 = Math.min(n3 - this.from, n10 - n3);
            IntArrays.swap(nArray, this.from, n10 - n8, n8);
            n8 = Math.min(n11 - n2, this.to - n11 - 1);
            IntArrays.swap(nArray, n10, this.to - n8, n8);
            n8 = n10 - n3;
            n = n11 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(nArray, this.from, this.from + n8), new ForkJoinQuickSort(nArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(nArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(nArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;
        private final IntComparator comp;

        public ForkJoinQuickSortComp(int[] nArray, int n, int n2, IntComparator intComparator) {
            this.from = n;
            this.to = n2;
            this.x = nArray;
            this.comp = intComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            int[] nArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                IntArrays.quickSort(nArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = IntArrays.access$000(nArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = IntArrays.access$000(nArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = IntArrays.access$000(nArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = IntArrays.access$000(nArray, n6, n5, n7, this.comp);
            int n9 = nArray[n5];
            int n10 = n3 = this.from;
            int n11 = n2 = this.to - 1;
            while (true) {
                if (n10 <= n2 && (n = this.comp.compare(nArray[n10], n9)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(nArray, n3++, n10);
                    }
                    ++n10;
                    continue;
                }
                while (n2 >= n10 && (n = this.comp.compare(nArray[n2], n9)) >= 0) {
                    if (n == 0) {
                        IntArrays.swap(nArray, n2, n11--);
                    }
                    --n2;
                }
                if (n10 > n2) break;
                IntArrays.swap(nArray, n10++, n2--);
            }
            n8 = Math.min(n3 - this.from, n10 - n3);
            IntArrays.swap(nArray, this.from, n10 - n8, n8);
            n8 = Math.min(n11 - n2, this.to - n11 - 1);
            IntArrays.swap(nArray, n10, this.to - n8, n8);
            n8 = n10 - n3;
            n = n11 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(nArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(nArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(nArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(nArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

