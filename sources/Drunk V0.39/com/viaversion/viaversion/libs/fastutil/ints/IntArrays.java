/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
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
    static final int RADIX_SORT_MIN_THRESHOLD = 2000;
    protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
    public static final Hash.Strategy<int[]> HASH_STRATEGY = new ArrayHashStrategy();

    private IntArrays() {
    }

    public static int[] forceCapacity(int[] array, int length, int preserve) {
        int[] t = new int[length];
        System.arraycopy(array, 0, t, 0, preserve);
        return t;
    }

    public static int[] ensureCapacity(int[] array, int length) {
        return IntArrays.ensureCapacity(array, length, array.length);
    }

    public static int[] ensureCapacity(int[] array, int length, int preserve) {
        int[] nArray;
        if (length > array.length) {
            nArray = IntArrays.forceCapacity(array, length, preserve);
            return nArray;
        }
        nArray = array;
        return nArray;
    }

    public static int[] grow(int[] array, int length) {
        return IntArrays.grow(array, length, array.length);
    }

    public static int[] grow(int[] array, int length, int preserve) {
        if (length <= array.length) return array;
        int newLength = (int)Math.max(Math.min((long)array.length + (long)(array.length >> 1), 0x7FFFFFF7L), (long)length);
        int[] t = new int[newLength];
        System.arraycopy(array, 0, t, 0, preserve);
        return t;
    }

    public static int[] trim(int[] array, int length) {
        if (length >= array.length) {
            return array;
        }
        int[] t = length == 0 ? EMPTY_ARRAY : new int[length];
        System.arraycopy(array, 0, t, 0, length);
        return t;
    }

    public static int[] setLength(int[] array, int length) {
        if (length == array.length) {
            return array;
        }
        if (length >= array.length) return IntArrays.ensureCapacity(array, length);
        return IntArrays.trim(array, length);
    }

    public static int[] copy(int[] array, int offset, int length) {
        IntArrays.ensureOffsetLength(array, offset, length);
        int[] a = length == 0 ? EMPTY_ARRAY : new int[length];
        System.arraycopy(array, offset, a, 0, length);
        return a;
    }

    public static int[] copy(int[] array) {
        return (int[])array.clone();
    }

    @Deprecated
    public static void fill(int[] array, int value) {
        int i = array.length;
        while (i-- != 0) {
            array[i] = value;
        }
    }

    @Deprecated
    public static void fill(int[] array, int from, int to, int value) {
        IntArrays.ensureFromTo(array, from, to);
        if (from == 0) {
            while (to-- != 0) {
                array[to] = value;
            }
            return;
        }
        int i = from;
        while (i < to) {
            array[i] = value;
            ++i;
        }
    }

    @Deprecated
    public static boolean equals(int[] a1, int[] a2) {
        int i = a1.length;
        if (i != a2.length) {
            return false;
        }
        do {
            if (i-- == 0) return true;
        } while (a1[i] == a2[i]);
        return false;
    }

    public static void ensureFromTo(int[] a, int from, int to) {
        Arrays.ensureFromTo(a.length, from, to);
    }

    public static void ensureOffsetLength(int[] a, int offset, int length) {
        Arrays.ensureOffsetLength(a.length, offset, length);
    }

    public static void ensureSameLength(int[] a, int[] b) {
        if (a.length == b.length) return;
        throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
    }

    private static ForkJoinPool getPool() {
        ForkJoinPool forkJoinPool;
        ForkJoinPool current = ForkJoinTask.getPool();
        if (current == null) {
            forkJoinPool = ForkJoinPool.commonPool();
            return forkJoinPool;
        }
        forkJoinPool = current;
        return forkJoinPool;
    }

    public static void swap(int[] x, int a, int b) {
        int t = x[a];
        x[a] = x[b];
        x[b] = t;
    }

    public static void swap(int[] x, int a, int b, int n) {
        int i = 0;
        while (i < n) {
            IntArrays.swap(x, a, b);
            ++i;
            ++a;
            ++b;
        }
    }

    private static int med3(int[] x, int a, int b, int c, IntComparator comp) {
        int n;
        int ab = comp.compare(x[a], x[b]);
        int ac = comp.compare(x[a], x[c]);
        int bc = comp.compare(x[b], x[c]);
        if (ab < 0) {
            if (bc < 0) {
                n = b;
                return n;
            }
            if (ac < 0) {
                n = c;
                return n;
            }
            n = a;
            return n;
        }
        if (bc > 0) {
            n = b;
            return n;
        }
        if (ac > 0) {
            n = c;
            return n;
        }
        n = a;
        return n;
    }

    private static void selectionSort(int[] a, int from, int to, IntComparator comp) {
        int i = from;
        while (i < to - 1) {
            int m = i;
            for (int j = i + 1; j < to; ++j) {
                if (comp.compare(a[j], a[m]) >= 0) continue;
                m = j;
            }
            if (m != i) {
                int u = a[i];
                a[i] = a[m];
                a[m] = u;
            }
            ++i;
        }
    }

    private static void insertionSort(int[] a, int from, int to, IntComparator comp) {
        int i = from;
        while (++i < to) {
            int t = a[i];
            int j = i;
            int u = a[j - 1];
            while (comp.compare(t, u) < 0) {
                a[j] = u;
                if (from == j - 1) {
                    --j;
                    break;
                }
                u = a[--j - 1];
            }
            a[j] = t;
        }
    }

    public static void quickSort(int[] x, int from, int to, IntComparator comp) {
        int s;
        int d;
        int c;
        block9: {
            int a;
            int len = to - from;
            if (len < 16) {
                IntArrays.selectionSort(x, from, to, comp);
                return;
            }
            int m = from + len / 2;
            int l = from;
            int n = to - 1;
            if (len > 128) {
                int s2 = len / 8;
                l = IntArrays.med3(x, l, l + s2, l + 2 * s2, comp);
                m = IntArrays.med3(x, m - s2, m, m + s2, comp);
                n = IntArrays.med3(x, n - 2 * s2, n - s2, n, comp);
            }
            m = IntArrays.med3(x, l, m, n, comp);
            int v = x[m];
            int b = a = from;
            d = c = to - 1;
            while (true) {
                int comparison;
                if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(x, a++, b);
                    }
                    ++b;
                    continue;
                }
                while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(x, c, d--);
                    }
                    --c;
                }
                if (b > c) {
                    s = Math.min(a - from, b - a);
                    IntArrays.swap(x, from, b - s, s);
                    s = Math.min(d - c, to - d - 1);
                    IntArrays.swap(x, b, to - s, s);
                    s = b - a;
                    if (s > 1) {
                        break;
                    }
                    break block9;
                }
                IntArrays.swap(x, b++, c--);
            }
            IntArrays.quickSort(x, from, from + s, comp);
        }
        if ((s = d - c) <= 1) return;
        IntArrays.quickSort(x, to - s, to, comp);
    }

    public static void quickSort(int[] x, IntComparator comp) {
        IntArrays.quickSort(x, 0, x.length, comp);
    }

    public static void parallelQuickSort(int[] x, int from, int to, IntComparator comp) {
        ForkJoinPool pool = IntArrays.getPool();
        if (to - from >= 8192 && pool.getParallelism() != 1) {
            pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp));
            return;
        }
        IntArrays.quickSort(x, from, to, comp);
    }

    public static void parallelQuickSort(int[] x, IntComparator comp) {
        IntArrays.parallelQuickSort(x, 0, x.length, comp);
    }

    private static int med3(int[] x, int a, int b, int c) {
        int n;
        int ab = Integer.compare(x[a], x[b]);
        int ac = Integer.compare(x[a], x[c]);
        int bc = Integer.compare(x[b], x[c]);
        if (ab < 0) {
            if (bc < 0) {
                n = b;
                return n;
            }
            if (ac < 0) {
                n = c;
                return n;
            }
            n = a;
            return n;
        }
        if (bc > 0) {
            n = b;
            return n;
        }
        if (ac > 0) {
            n = c;
            return n;
        }
        n = a;
        return n;
    }

    private static void selectionSort(int[] a, int from, int to) {
        int i = from;
        while (i < to - 1) {
            int m = i;
            for (int j = i + 1; j < to; ++j) {
                if (a[j] >= a[m]) continue;
                m = j;
            }
            if (m != i) {
                int u = a[i];
                a[i] = a[m];
                a[m] = u;
            }
            ++i;
        }
    }

    private static void insertionSort(int[] a, int from, int to) {
        int i = from;
        while (++i < to) {
            int t = a[i];
            int j = i;
            int u = a[j - 1];
            while (t < u) {
                a[j] = u;
                if (from == j - 1) {
                    --j;
                    break;
                }
                u = a[--j - 1];
            }
            a[j] = t;
        }
    }

    public static void quickSort(int[] x, int from, int to) {
        int s;
        int d;
        int c;
        block9: {
            int a;
            int len = to - from;
            if (len < 16) {
                IntArrays.selectionSort(x, from, to);
                return;
            }
            int m = from + len / 2;
            int l = from;
            int n = to - 1;
            if (len > 128) {
                int s2 = len / 8;
                l = IntArrays.med3(x, l, l + s2, l + 2 * s2);
                m = IntArrays.med3(x, m - s2, m, m + s2);
                n = IntArrays.med3(x, n - 2 * s2, n - s2, n);
            }
            m = IntArrays.med3(x, l, m, n);
            int v = x[m];
            int b = a = from;
            d = c = to - 1;
            while (true) {
                int comparison;
                if (b <= c && (comparison = Integer.compare(x[b], v)) <= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(x, a++, b);
                    }
                    ++b;
                    continue;
                }
                while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(x, c, d--);
                    }
                    --c;
                }
                if (b > c) {
                    s = Math.min(a - from, b - a);
                    IntArrays.swap(x, from, b - s, s);
                    s = Math.min(d - c, to - d - 1);
                    IntArrays.swap(x, b, to - s, s);
                    s = b - a;
                    if (s > 1) {
                        break;
                    }
                    break block9;
                }
                IntArrays.swap(x, b++, c--);
            }
            IntArrays.quickSort(x, from, from + s);
        }
        if ((s = d - c) <= 1) return;
        IntArrays.quickSort(x, to - s, to);
    }

    public static void quickSort(int[] x) {
        IntArrays.quickSort(x, 0, x.length);
    }

    public static void parallelQuickSort(int[] x, int from, int to) {
        ForkJoinPool pool = IntArrays.getPool();
        if (to - from >= 8192 && pool.getParallelism() != 1) {
            pool.invoke(new ForkJoinQuickSort(x, from, to));
            return;
        }
        IntArrays.quickSort(x, from, to);
    }

    public static void parallelQuickSort(int[] x) {
        IntArrays.parallelQuickSort(x, 0, x.length);
    }

    private static int med3Indirect(int[] perm, int[] x, int a, int b, int c) {
        int n;
        int aa = x[perm[a]];
        int bb = x[perm[b]];
        int cc = x[perm[c]];
        int ab = Integer.compare(aa, bb);
        int ac = Integer.compare(aa, cc);
        int bc = Integer.compare(bb, cc);
        if (ab < 0) {
            if (bc < 0) {
                n = b;
                return n;
            }
            if (ac < 0) {
                n = c;
                return n;
            }
            n = a;
            return n;
        }
        if (bc > 0) {
            n = b;
            return n;
        }
        if (ac > 0) {
            n = c;
            return n;
        }
        n = a;
        return n;
    }

    private static void insertionSortIndirect(int[] perm, int[] a, int from, int to) {
        int i = from;
        while (++i < to) {
            int t = perm[i];
            int j = i;
            int u = perm[j - 1];
            while (a[t] < a[u]) {
                perm[j] = u;
                if (from == j - 1) {
                    --j;
                    break;
                }
                u = perm[--j - 1];
            }
            perm[j] = t;
        }
    }

    public static void quickSortIndirect(int[] perm, int[] x, int from, int to) {
        int s;
        int d;
        int c;
        block9: {
            int a;
            int len = to - from;
            if (len < 16) {
                IntArrays.insertionSortIndirect(perm, x, from, to);
                return;
            }
            int m = from + len / 2;
            int l = from;
            int n = to - 1;
            if (len > 128) {
                int s2 = len / 8;
                l = IntArrays.med3Indirect(perm, x, l, l + s2, l + 2 * s2);
                m = IntArrays.med3Indirect(perm, x, m - s2, m, m + s2);
                n = IntArrays.med3Indirect(perm, x, n - 2 * s2, n - s2, n);
            }
            m = IntArrays.med3Indirect(perm, x, l, m, n);
            int v = x[perm[m]];
            int b = a = from;
            d = c = to - 1;
            while (true) {
                int comparison;
                if (b <= c && (comparison = Integer.compare(x[perm[b]], v)) <= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(perm, a++, b);
                    }
                    ++b;
                    continue;
                }
                while (c >= b && (comparison = Integer.compare(x[perm[c]], v)) >= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(perm, c, d--);
                    }
                    --c;
                }
                if (b > c) {
                    s = Math.min(a - from, b - a);
                    IntArrays.swap(perm, from, b - s, s);
                    s = Math.min(d - c, to - d - 1);
                    IntArrays.swap(perm, b, to - s, s);
                    s = b - a;
                    if (s > 1) {
                        break;
                    }
                    break block9;
                }
                IntArrays.swap(perm, b++, c--);
            }
            IntArrays.quickSortIndirect(perm, x, from, from + s);
        }
        if ((s = d - c) <= 1) return;
        IntArrays.quickSortIndirect(perm, x, to - s, to);
    }

    public static void quickSortIndirect(int[] perm, int[] x) {
        IntArrays.quickSortIndirect(perm, x, 0, x.length);
    }

    public static void parallelQuickSortIndirect(int[] perm, int[] x, int from, int to) {
        ForkJoinPool pool = IntArrays.getPool();
        if (to - from >= 8192 && pool.getParallelism() != 1) {
            pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to));
            return;
        }
        IntArrays.quickSortIndirect(perm, x, from, to);
    }

    public static void parallelQuickSortIndirect(int[] perm, int[] x) {
        IntArrays.parallelQuickSortIndirect(perm, x, 0, x.length);
    }

    public static void stabilize(int[] perm, int[] x, int from, int to) {
        int curr = from;
        int i = from + 1;
        while (true) {
            if (i >= to) {
                if (to - curr <= 1) return;
                IntArrays.parallelQuickSort(perm, curr, to);
                return;
            }
            if (x[perm[i]] != x[perm[curr]]) {
                if (i - curr > 1) {
                    IntArrays.parallelQuickSort(perm, curr, i);
                }
                curr = i;
            }
            ++i;
        }
    }

    public static void stabilize(int[] perm, int[] x) {
        IntArrays.stabilize(perm, x, 0, perm.length);
    }

    private static int med3(int[] x, int[] y, int a, int b, int c) {
        int n;
        int bc;
        int t = Integer.compare(x[a], x[b]);
        int ab = t == 0 ? Integer.compare(y[a], y[b]) : t;
        t = Integer.compare(x[a], x[c]);
        int ac = t == 0 ? Integer.compare(y[a], y[c]) : t;
        t = Integer.compare(x[b], x[c]);
        int n2 = bc = t == 0 ? Integer.compare(y[b], y[c]) : t;
        if (ab < 0) {
            if (bc < 0) {
                n = b;
                return n;
            }
            if (ac < 0) {
                n = c;
                return n;
            }
            n = a;
            return n;
        }
        if (bc > 0) {
            n = b;
            return n;
        }
        if (ac > 0) {
            n = c;
            return n;
        }
        n = a;
        return n;
    }

    private static void swap(int[] x, int[] y, int a, int b) {
        int t = x[a];
        int u = y[a];
        x[a] = x[b];
        y[a] = y[b];
        x[b] = t;
        y[b] = u;
    }

    private static void swap(int[] x, int[] y, int a, int b, int n) {
        int i = 0;
        while (i < n) {
            IntArrays.swap(x, y, a, b);
            ++i;
            ++a;
            ++b;
        }
    }

    private static void selectionSort(int[] a, int[] b, int from, int to) {
        int i = from;
        while (i < to - 1) {
            int m = i;
            for (int j = i + 1; j < to; ++j) {
                int u = Integer.compare(a[j], a[m]);
                if (u >= 0 && (u != 0 || b[j] >= b[m])) continue;
                m = j;
            }
            if (m != i) {
                int t = a[i];
                a[i] = a[m];
                a[m] = t;
                t = b[i];
                b[i] = b[m];
                b[m] = t;
            }
            ++i;
        }
    }

    public static void quickSort(int[] x, int[] y, int from, int to) {
        int s;
        int d;
        int c;
        block9: {
            int a;
            int len = to - from;
            if (len < 16) {
                IntArrays.selectionSort(x, y, from, to);
                return;
            }
            int m = from + len / 2;
            int l = from;
            int n = to - 1;
            if (len > 128) {
                int s2 = len / 8;
                l = IntArrays.med3(x, y, l, l + s2, l + 2 * s2);
                m = IntArrays.med3(x, y, m - s2, m, m + s2);
                n = IntArrays.med3(x, y, n - 2 * s2, n - s2, n);
            }
            m = IntArrays.med3(x, y, l, m, n);
            int v = x[m];
            int w = y[m];
            int b = a = from;
            d = c = to - 1;
            while (true) {
                int t;
                int comparison;
                if (b <= c && (comparison = (t = Integer.compare(x[b], v)) == 0 ? Integer.compare(y[b], w) : t) <= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(x, y, a++, b);
                    }
                    ++b;
                    continue;
                }
                while (c >= b && (comparison = (t = Integer.compare(x[c], v)) == 0 ? Integer.compare(y[c], w) : t) >= 0) {
                    if (comparison == 0) {
                        IntArrays.swap(x, y, c, d--);
                    }
                    --c;
                }
                if (b > c) {
                    s = Math.min(a - from, b - a);
                    IntArrays.swap(x, y, from, b - s, s);
                    s = Math.min(d - c, to - d - 1);
                    IntArrays.swap(x, y, b, to - s, s);
                    s = b - a;
                    if (s > 1) {
                        break;
                    }
                    break block9;
                }
                IntArrays.swap(x, y, b++, c--);
            }
            IntArrays.quickSort(x, y, from, from + s);
        }
        if ((s = d - c) <= 1) return;
        IntArrays.quickSort(x, y, to - s, to);
    }

    public static void quickSort(int[] x, int[] y) {
        IntArrays.ensureSameLength(x, y);
        IntArrays.quickSort(x, y, 0, x.length);
    }

    public static void parallelQuickSort(int[] x, int[] y, int from, int to) {
        ForkJoinPool pool = IntArrays.getPool();
        if (to - from >= 8192 && pool.getParallelism() != 1) {
            pool.invoke(new ForkJoinQuickSort2(x, y, from, to));
            return;
        }
        IntArrays.quickSort(x, y, from, to);
    }

    public static void parallelQuickSort(int[] x, int[] y) {
        IntArrays.ensureSameLength(x, y);
        IntArrays.parallelQuickSort(x, y, 0, x.length);
    }

    public static void unstableSort(int[] a, int from, int to) {
        if (to - from >= 2000) {
            IntArrays.radixSort(a, from, to);
            return;
        }
        IntArrays.quickSort(a, from, to);
    }

    public static void unstableSort(int[] a) {
        IntArrays.unstableSort(a, 0, a.length);
    }

    public static void unstableSort(int[] a, int from, int to, IntComparator comp) {
        IntArrays.quickSort(a, from, to, comp);
    }

    public static void unstableSort(int[] a, IntComparator comp) {
        IntArrays.unstableSort(a, 0, a.length, comp);
    }

    public static void mergeSort(int[] a, int from, int to, int[] supp) {
        int len = to - from;
        if (len < 16) {
            IntArrays.insertionSort(a, from, to);
            return;
        }
        if (supp == null) {
            supp = java.util.Arrays.copyOf(a, to);
        }
        int mid = from + to >>> 1;
        IntArrays.mergeSort(supp, from, mid, a);
        IntArrays.mergeSort(supp, mid, to, a);
        if (supp[mid - 1] <= supp[mid]) {
            System.arraycopy(supp, from, a, from, len);
            return;
        }
        int i = from;
        int p = from;
        int q = mid;
        while (i < to) {
            a[i] = q >= to || p < mid && supp[p] <= supp[q] ? supp[p++] : supp[q++];
            ++i;
        }
    }

    public static void mergeSort(int[] a, int from, int to) {
        IntArrays.mergeSort(a, from, to, (int[])null);
    }

    public static void mergeSort(int[] a) {
        IntArrays.mergeSort(a, 0, a.length);
    }

    public static void mergeSort(int[] a, int from, int to, IntComparator comp, int[] supp) {
        int len = to - from;
        if (len < 16) {
            IntArrays.insertionSort(a, from, to, comp);
            return;
        }
        if (supp == null) {
            supp = java.util.Arrays.copyOf(a, to);
        }
        int mid = from + to >>> 1;
        IntArrays.mergeSort(supp, from, mid, comp, a);
        IntArrays.mergeSort(supp, mid, to, comp, a);
        if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
            System.arraycopy(supp, from, a, from, len);
            return;
        }
        int i = from;
        int p = from;
        int q = mid;
        while (i < to) {
            a[i] = q >= to || p < mid && comp.compare(supp[p], supp[q]) <= 0 ? supp[p++] : supp[q++];
            ++i;
        }
    }

    public static void mergeSort(int[] a, int from, int to, IntComparator comp) {
        IntArrays.mergeSort(a, from, to, comp, null);
    }

    public static void mergeSort(int[] a, IntComparator comp) {
        IntArrays.mergeSort(a, 0, a.length, comp);
    }

    public static void stableSort(int[] a, int from, int to) {
        IntArrays.unstableSort(a, from, to);
    }

    public static void stableSort(int[] a) {
        IntArrays.stableSort(a, 0, a.length);
    }

    public static void stableSort(int[] a, int from, int to, IntComparator comp) {
        IntArrays.mergeSort(a, from, to, comp);
    }

    public static void stableSort(int[] a, IntComparator comp) {
        IntArrays.stableSort(a, 0, a.length, comp);
    }

    public static int binarySearch(int[] a, int from, int to, int key) {
        --to;
        while (from <= to) {
            int mid = from + to >>> 1;
            int midVal = a[mid];
            if (midVal < key) {
                from = mid + 1;
                continue;
            }
            if (midVal <= key) return mid;
            to = mid - 1;
        }
        return -(from + 1);
    }

    public static int binarySearch(int[] a, int key) {
        return IntArrays.binarySearch(a, 0, a.length, key);
    }

    public static int binarySearch(int[] a, int from, int to, int key, IntComparator c) {
        --to;
        while (from <= to) {
            int mid = from + to >>> 1;
            int midVal = a[mid];
            int cmp = c.compare(midVal, key);
            if (cmp < 0) {
                from = mid + 1;
                continue;
            }
            if (cmp <= 0) return mid;
            to = mid - 1;
        }
        return -(from + 1);
    }

    public static int binarySearch(int[] a, int key, IntComparator c) {
        return IntArrays.binarySearch(a, 0, a.length, key, c);
    }

    public static void radixSort(int[] a) {
        IntArrays.radixSort(a, 0, a.length);
    }

    public static void radixSort(int[] a, int from, int to) {
        if (to - from < 1024) {
            IntArrays.quickSort(a, from, to);
            return;
        }
        int maxLevel = 3;
        int stackSize = 766;
        int stackPos = 0;
        int[] offsetStack = new int[766];
        int[] lengthStack = new int[766];
        int[] levelStack = new int[766];
        offsetStack[stackPos] = from;
        lengthStack[stackPos] = to - from;
        levelStack[stackPos++] = 0;
        int[] count = new int[256];
        int[] pos = new int[256];
        block0: while (stackPos > 0) {
            int first = offsetStack[--stackPos];
            int length = lengthStack[stackPos];
            int level = levelStack[stackPos];
            int signMask = level % 4 == 0 ? 128 : 0;
            int shift = (3 - level % 4) * 8;
            int i = first + length;
            while (i-- != first) {
                int n = a[i] >>> shift & 0xFF ^ signMask;
                count[n] = count[n] + 1;
            }
            int lastUsed = -1;
            int p = first;
            for (int i2 = 0; i2 < 256; ++i2) {
                if (count[i2] != 0) {
                    lastUsed = i2;
                }
                pos[i2] = p += count[i2];
            }
            int end = first + length - count[lastUsed];
            int i3 = first;
            int c = -1;
            while (true) {
                if (i3 > end) continue block0;
                int t = a[i3];
                c = t >>> shift & 0xFF ^ signMask;
                if (i3 < end) {
                    while (true) {
                        int n = c;
                        int n2 = pos[n] - 1;
                        pos[n] = n2;
                        int d = n2;
                        if (n2 <= i3) break;
                        int z = t;
                        t = a[d];
                        a[d] = z;
                        c = t >>> shift & 0xFF ^ signMask;
                    }
                    a[i3] = t;
                }
                if (level < 3 && count[c] > 1) {
                    if (count[c] < 1024) {
                        IntArrays.quickSort(a, i3, i3 + count[c]);
                    } else {
                        offsetStack[stackPos] = i3;
                        lengthStack[stackPos] = count[c];
                        levelStack[stackPos++] = level + 1;
                    }
                }
                i3 += count[c];
                count[c] = 0;
            }
            break;
        }
        return;
    }

    public static void parallelRadixSort(int[] a, int from, int to) {
        RuntimeException runtimeException;
        ForkJoinPool pool = IntArrays.getPool();
        if (to - from < 1024 || pool.getParallelism() == 1) {
            IntArrays.quickSort(a, from, to);
            return;
        }
        int maxLevel = 3;
        LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<Segment>();
        queue.add(new Segment(from, to - from, 0));
        AtomicInteger queueSize = new AtomicInteger(1);
        int numberOfThreads = pool.getParallelism();
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(pool);
        int j = numberOfThreads;
        while (j-- != 0) {
            executorCompletionService.submit(() -> {
                int[] count = new int[256];
                int[] pos = new int[256];
                while (true) {
                    Segment segment;
                    if (queueSize.get() == 0) {
                        int i = numberOfThreads;
                        while (i-- != 0) {
                            queue.add(POISON_PILL);
                        }
                    }
                    if ((segment = (Segment)queue.take()) == POISON_PILL) {
                        return null;
                    }
                    int first = segment.offset;
                    int length = segment.length;
                    int level = segment.level;
                    int signMask = level % 4 == 0 ? 128 : 0;
                    int shift = (3 - level % 4) * 8;
                    int i = first + length;
                    while (i-- != first) {
                        int n = a[i] >>> shift & 0xFF ^ signMask;
                        count[n] = count[n] + 1;
                    }
                    int lastUsed = -1;
                    int p = first;
                    for (int i2 = 0; i2 < 256; ++i2) {
                        if (count[i2] != 0) {
                            lastUsed = i2;
                        }
                        pos[i2] = p += count[i2];
                    }
                    int end = first + length - count[lastUsed];
                    int c = -1;
                    for (int i3 = first; i3 <= end; i3 += count[c]) {
                        int t = a[i3];
                        c = t >>> shift & 0xFF ^ signMask;
                        if (i3 < end) {
                            while (true) {
                                int n = c;
                                int n2 = pos[n] - 1;
                                pos[n] = n2;
                                int d = n2;
                                if (n2 <= i3) break;
                                int z = t;
                                t = a[d];
                                a[d] = z;
                                c = t >>> shift & 0xFF ^ signMask;
                            }
                            a[i3] = t;
                        }
                        if (level < 3 && count[c] > 1) {
                            if (count[c] < 1024) {
                                IntArrays.quickSort(a, i3, i3 + count[c]);
                            } else {
                                queueSize.incrementAndGet();
                                queue.add(new Segment(i3, count[c], level + 1));
                            }
                        }
                        count[c] = 0;
                    }
                    queueSize.decrementAndGet();
                }
            });
        }
        Throwable problem = null;
        int i = numberOfThreads;
        while (i-- != 0) {
            try {
                executorCompletionService.take().get();
            }
            catch (Exception e) {
                problem = e.getCause();
            }
        }
        if (problem == null) return;
        if (problem instanceof RuntimeException) {
            runtimeException = (RuntimeException)problem;
            throw runtimeException;
        }
        runtimeException = new RuntimeException(problem);
        throw runtimeException;
    }

    public static void parallelRadixSort(int[] a) {
        IntArrays.parallelRadixSort(a, 0, a.length);
    }

    public static void radixSortIndirect(int[] perm, int[] a, boolean stable) {
        IntArrays.radixSortIndirect(perm, a, 0, perm.length, stable);
    }

    /*
     * Unable to fully structure code
     */
    public static void radixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
        if (to - from < 1024) {
            IntArrays.insertionSortIndirect(perm, a, from, to);
            return;
        }
        maxLevel = 3;
        stackSize = 766;
        stackPos = 0;
        offsetStack = new int[766];
        lengthStack = new int[766];
        levelStack = new int[766];
        offsetStack[stackPos] = from;
        lengthStack[stackPos] = to - from;
        levelStack[stackPos++] = 0;
        count = new int[256];
        pos = new int[256];
        support = stable != false ? new int[perm.length] : null;
        while (true) {
            if (stackPos <= 0) return;
            first = offsetStack[--stackPos];
            length = lengthStack[stackPos];
            level = levelStack[stackPos];
            signMask = level % 4 == 0 ? 128 : 0;
            shift = (3 - level % 4) * 8;
            i = first + length;
            while (i-- != first) {
                v0 = a[perm[i]] >>> shift & 255 ^ signMask;
                count[v0] = count[v0] + 1;
            }
            lastUsed = -1;
            v1 = p = stable != false ? 0 : first;
            for (i = 0; i < 256; ++i) {
                if (count[i] != 0) {
                    lastUsed = i;
                }
                pos[i] = p += count[i];
            }
            if (stable) {
                i = first + length;
                while (i-- != first) {
                    v2 = a[perm[i]] >>> shift & 255 ^ signMask;
                    v3 = pos[v2] - 1;
                    pos[v2] = v3;
                    support[v3] = perm[i];
                }
                System.arraycopy(support, 0, perm, first, length);
                p = first;
                for (i = 0; i <= lastUsed; p += count[i], ++i) {
                    if (level >= 3 || count[i] <= 1) continue;
                    if (count[i] < 1024) {
                        IntArrays.insertionSortIndirect(perm, a, p, p + count[i]);
                        continue;
                    }
                    offsetStack[stackPos] = p;
                    lengthStack[stackPos] = count[i];
                    levelStack[stackPos++] = level + 1;
                }
                java.util.Arrays.fill(count, 0);
                continue;
            }
            end = first + length - count[lastUsed];
            i = first;
            c = -1;
            while (true) {
                if (i > end) ** break;
                t = perm[i];
                c = a[t] >>> shift & 255 ^ signMask;
                if (i < end) {
                    while (true) {
                        v4 = c;
                        v5 = pos[v4] - 1;
                        pos[v4] = v5;
                        d = v5;
                        if (v5 <= i) break;
                        z = t;
                        t = perm[d];
                        perm[d] = z;
                        c = a[t] >>> shift & 255 ^ signMask;
                    }
                    perm[i] = t;
                }
                if (level < 3 && count[c] > 1) {
                    if (count[c] < 1024) {
                        IntArrays.insertionSortIndirect(perm, a, i, i + count[c]);
                    } else {
                        offsetStack[stackPos] = i;
                        lengthStack[stackPos] = count[c];
                        levelStack[stackPos++] = level + 1;
                    }
                }
                i += count[c];
                count[c] = 0;
            }
            break;
        }
    }

    public static void parallelRadixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
        RuntimeException runtimeException;
        ForkJoinPool pool = IntArrays.getPool();
        if (to - from < 1024 || pool.getParallelism() == 1) {
            IntArrays.radixSortIndirect(perm, a, from, to, stable);
            return;
        }
        int maxLevel = 3;
        LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<Segment>();
        queue.add(new Segment(from, to - from, 0));
        AtomicInteger queueSize = new AtomicInteger(1);
        int numberOfThreads = pool.getParallelism();
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(pool);
        int[] support = stable ? new int[perm.length] : null;
        int j = numberOfThreads;
        while (j-- != 0) {
            executorCompletionService.submit(() -> {
                int[] count = new int[256];
                int[] pos = new int[256];
                while (true) {
                    int i;
                    Segment segment;
                    if (queueSize.get() == 0) {
                        int i2 = numberOfThreads;
                        while (i2-- != 0) {
                            queue.add(POISON_PILL);
                        }
                    }
                    if ((segment = (Segment)queue.take()) == POISON_PILL) {
                        return null;
                    }
                    int first = segment.offset;
                    int length = segment.length;
                    int level = segment.level;
                    int signMask = level % 4 == 0 ? 128 : 0;
                    int shift = (3 - level % 4) * 8;
                    int i3 = first + length;
                    while (i3-- != first) {
                        int n = a[perm[i3]] >>> shift & 0xFF ^ signMask;
                        count[n] = count[n] + 1;
                    }
                    int lastUsed = -1;
                    int p = first;
                    for (i = 0; i < 256; ++i) {
                        if (count[i] != 0) {
                            lastUsed = i;
                        }
                        pos[i] = p += count[i];
                    }
                    if (stable) {
                        i = first + length;
                        while (i-- != first) {
                            int n = a[perm[i]] >>> shift & 0xFF ^ signMask;
                            int n2 = pos[n] - 1;
                            pos[n] = n2;
                            support[n2] = perm[i];
                        }
                        System.arraycopy(support, first, perm, first, length);
                        p = first;
                        for (i = 0; i <= lastUsed; p += count[i], ++i) {
                            if (level >= 3 || count[i] <= 1) continue;
                            if (count[i] < 1024) {
                                IntArrays.radixSortIndirect(perm, a, p, p + count[i], stable);
                                continue;
                            }
                            queueSize.incrementAndGet();
                            queue.add(new Segment(p, count[i], level + 1));
                        }
                        java.util.Arrays.fill(count, 0);
                    } else {
                        int end = first + length - count[lastUsed];
                        int c = -1;
                        for (int i4 = first; i4 <= end; i4 += count[c]) {
                            int t = perm[i4];
                            c = a[t] >>> shift & 0xFF ^ signMask;
                            if (i4 < end) {
                                while (true) {
                                    int n = c;
                                    int n3 = pos[n] - 1;
                                    pos[n] = n3;
                                    int d = n3;
                                    if (n3 <= i4) break;
                                    int z = t;
                                    t = perm[d];
                                    perm[d] = z;
                                    c = a[t] >>> shift & 0xFF ^ signMask;
                                }
                                perm[i4] = t;
                            }
                            if (level < 3 && count[c] > 1) {
                                if (count[c] < 1024) {
                                    IntArrays.radixSortIndirect(perm, a, i4, i4 + count[c], stable);
                                } else {
                                    queueSize.incrementAndGet();
                                    queue.add(new Segment(i4, count[c], level + 1));
                                }
                            }
                            count[c] = 0;
                        }
                    }
                    queueSize.decrementAndGet();
                }
            });
        }
        Throwable problem = null;
        int i = numberOfThreads;
        while (i-- != 0) {
            try {
                executorCompletionService.take().get();
            }
            catch (Exception e) {
                problem = e.getCause();
            }
        }
        if (problem == null) return;
        if (problem instanceof RuntimeException) {
            runtimeException = (RuntimeException)problem;
            throw runtimeException;
        }
        runtimeException = new RuntimeException(problem);
        throw runtimeException;
    }

    public static void parallelRadixSortIndirect(int[] perm, int[] a, boolean stable) {
        IntArrays.parallelRadixSortIndirect(perm, a, 0, a.length, stable);
    }

    public static void radixSort(int[] a, int[] b) {
        IntArrays.ensureSameLength(a, b);
        IntArrays.radixSort(a, b, 0, a.length);
    }

    public static void radixSort(int[] a, int[] b, int from, int to) {
        if (to - from < 1024) {
            IntArrays.selectionSort(a, b, from, to);
            return;
        }
        int layers = 2;
        int maxLevel = 7;
        int stackSize = 1786;
        int stackPos = 0;
        int[] offsetStack = new int[1786];
        int[] lengthStack = new int[1786];
        int[] levelStack = new int[1786];
        offsetStack[stackPos] = from;
        lengthStack[stackPos] = to - from;
        levelStack[stackPos++] = 0;
        int[] count = new int[256];
        int[] pos = new int[256];
        block0: while (stackPos > 0) {
            int first = offsetStack[--stackPos];
            int length = lengthStack[stackPos];
            int level = levelStack[stackPos];
            int signMask = level % 4 == 0 ? 128 : 0;
            int[] k = level < 4 ? a : b;
            int shift = (3 - level % 4) * 8;
            int i = first + length;
            while (i-- != first) {
                int n = k[i] >>> shift & 0xFF ^ signMask;
                count[n] = count[n] + 1;
            }
            int lastUsed = -1;
            int p = first;
            for (int i2 = 0; i2 < 256; ++i2) {
                if (count[i2] != 0) {
                    lastUsed = i2;
                }
                pos[i2] = p += count[i2];
            }
            int end = first + length - count[lastUsed];
            int i3 = first;
            int c = -1;
            while (true) {
                if (i3 > end) continue block0;
                int t = a[i3];
                int u = b[i3];
                c = k[i3] >>> shift & 0xFF ^ signMask;
                if (i3 < end) {
                    while (true) {
                        int n = c;
                        int n2 = pos[n] - 1;
                        pos[n] = n2;
                        int d = n2;
                        if (n2 <= i3) break;
                        c = k[d] >>> shift & 0xFF ^ signMask;
                        int z = t;
                        t = a[d];
                        a[d] = z;
                        z = u;
                        u = b[d];
                        b[d] = z;
                    }
                    a[i3] = t;
                    b[i3] = u;
                }
                if (level < 7 && count[c] > 1) {
                    if (count[c] < 1024) {
                        IntArrays.selectionSort(a, b, i3, i3 + count[c]);
                    } else {
                        offsetStack[stackPos] = i3;
                        lengthStack[stackPos] = count[c];
                        levelStack[stackPos++] = level + 1;
                    }
                }
                i3 += count[c];
                count[c] = 0;
            }
            break;
        }
        return;
    }

    public static void parallelRadixSort(int[] a, int[] b, int from, int to) {
        RuntimeException runtimeException;
        ForkJoinPool pool = IntArrays.getPool();
        if (to - from < 1024 || pool.getParallelism() == 1) {
            IntArrays.quickSort(a, b, from, to);
            return;
        }
        int layers = 2;
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        int maxLevel = 7;
        LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<Segment>();
        queue.add(new Segment(from, to - from, 0));
        AtomicInteger queueSize = new AtomicInteger(1);
        int numberOfThreads = pool.getParallelism();
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(pool);
        int j = numberOfThreads;
        while (j-- != 0) {
            executorCompletionService.submit(() -> {
                int[] count = new int[256];
                int[] pos = new int[256];
                while (true) {
                    Segment segment;
                    if (queueSize.get() == 0) {
                        int i = numberOfThreads;
                        while (i-- != 0) {
                            queue.add(POISON_PILL);
                        }
                    }
                    if ((segment = (Segment)queue.take()) == POISON_PILL) {
                        return null;
                    }
                    int first = segment.offset;
                    int length = segment.length;
                    int level = segment.level;
                    int signMask = level % 4 == 0 ? 128 : 0;
                    int[] k = level < 4 ? a : b;
                    int shift = (3 - level % 4) * 8;
                    int i = first + length;
                    while (i-- != first) {
                        int n = k[i] >>> shift & 0xFF ^ signMask;
                        count[n] = count[n] + 1;
                    }
                    int lastUsed = -1;
                    int p = first;
                    for (int i2 = 0; i2 < 256; ++i2) {
                        if (count[i2] != 0) {
                            lastUsed = i2;
                        }
                        pos[i2] = p += count[i2];
                    }
                    int end = first + length - count[lastUsed];
                    int c = -1;
                    for (int i3 = first; i3 <= end; i3 += count[c]) {
                        int t = a[i3];
                        int u = b[i3];
                        c = k[i3] >>> shift & 0xFF ^ signMask;
                        if (i3 < end) {
                            while (true) {
                                int n = c;
                                int n2 = pos[n] - 1;
                                pos[n] = n2;
                                int d = n2;
                                if (n2 <= i3) break;
                                c = k[d] >>> shift & 0xFF ^ signMask;
                                int z = t;
                                int w = u;
                                t = a[d];
                                u = b[d];
                                a[d] = z;
                                b[d] = w;
                            }
                            a[i3] = t;
                            b[i3] = u;
                        }
                        if (level < 7 && count[c] > 1) {
                            if (count[c] < 1024) {
                                IntArrays.quickSort(a, b, i3, i3 + count[c]);
                            } else {
                                queueSize.incrementAndGet();
                                queue.add(new Segment(i3, count[c], level + 1));
                            }
                        }
                        count[c] = 0;
                    }
                    queueSize.decrementAndGet();
                }
            });
        }
        Throwable problem = null;
        int i = numberOfThreads;
        while (i-- != 0) {
            try {
                executorCompletionService.take().get();
            }
            catch (Exception e) {
                problem = e.getCause();
            }
        }
        if (problem == null) return;
        if (problem instanceof RuntimeException) {
            runtimeException = (RuntimeException)problem;
            throw runtimeException;
        }
        runtimeException = new RuntimeException(problem);
        throw runtimeException;
    }

    public static void parallelRadixSort(int[] a, int[] b) {
        IntArrays.ensureSameLength(a, b);
        IntArrays.parallelRadixSort(a, b, 0, a.length);
    }

    private static void insertionSortIndirect(int[] perm, int[] a, int[] b, int from, int to) {
        int i = from;
        while (++i < to) {
            int t = perm[i];
            int j = i;
            int u = perm[j - 1];
            while (a[t] < a[u] || a[t] == a[u] && b[t] < b[u]) {
                perm[j] = u;
                if (from == j - 1) {
                    --j;
                    break;
                }
                u = perm[--j - 1];
            }
            perm[j] = t;
        }
    }

    public static void radixSortIndirect(int[] perm, int[] a, int[] b, boolean stable) {
        IntArrays.ensureSameLength(a, b);
        IntArrays.radixSortIndirect(perm, a, b, 0, a.length, stable);
    }

    /*
     * Unable to fully structure code
     */
    public static void radixSortIndirect(int[] perm, int[] a, int[] b, int from, int to, boolean stable) {
        if (to - from < 1024) {
            IntArrays.insertionSortIndirect(perm, a, b, from, to);
            return;
        }
        layers = 2;
        maxLevel = 7;
        stackSize = 1786;
        stackPos = 0;
        offsetStack = new int[1786];
        lengthStack = new int[1786];
        levelStack = new int[1786];
        offsetStack[stackPos] = from;
        lengthStack[stackPos] = to - from;
        levelStack[stackPos++] = 0;
        count = new int[256];
        pos = new int[256];
        support = stable != false ? new int[perm.length] : null;
        while (true) {
            if (stackPos <= 0) return;
            first = offsetStack[--stackPos];
            length = lengthStack[stackPos];
            level = levelStack[stackPos];
            signMask = level % 4 == 0 ? 128 : 0;
            k = level < 4 ? a : b;
            shift = (3 - level % 4) * 8;
            i = first + length;
            while (i-- != first) {
                v0 = k[perm[i]] >>> shift & 255 ^ signMask;
                count[v0] = count[v0] + 1;
            }
            lastUsed = -1;
            v1 = p = stable != false ? 0 : first;
            for (i = 0; i < 256; ++i) {
                if (count[i] != 0) {
                    lastUsed = i;
                }
                pos[i] = p += count[i];
            }
            if (stable) {
                i = first + length;
                while (i-- != first) {
                    v2 = k[perm[i]] >>> shift & 255 ^ signMask;
                    v3 = pos[v2] - 1;
                    pos[v2] = v3;
                    support[v3] = perm[i];
                }
                System.arraycopy(support, 0, perm, first, length);
                p = first;
                for (i = 0; i < 256; p += count[i], ++i) {
                    if (level >= 7 || count[i] <= 1) continue;
                    if (count[i] < 1024) {
                        IntArrays.insertionSortIndirect(perm, a, b, p, p + count[i]);
                        continue;
                    }
                    offsetStack[stackPos] = p;
                    lengthStack[stackPos] = count[i];
                    levelStack[stackPos++] = level + 1;
                }
                java.util.Arrays.fill(count, 0);
                continue;
            }
            end = first + length - count[lastUsed];
            i = first;
            c = -1;
            while (true) {
                if (i > end) ** break;
                t = perm[i];
                c = k[t] >>> shift & 255 ^ signMask;
                if (i < end) {
                    while (true) {
                        v4 = c;
                        v5 = pos[v4] - 1;
                        pos[v4] = v5;
                        d = v5;
                        if (v5 <= i) break;
                        z = t;
                        t = perm[d];
                        perm[d] = z;
                        c = k[t] >>> shift & 255 ^ signMask;
                    }
                    perm[i] = t;
                }
                if (level < 7 && count[c] > 1) {
                    if (count[c] < 1024) {
                        IntArrays.insertionSortIndirect(perm, a, b, i, i + count[c]);
                    } else {
                        offsetStack[stackPos] = i;
                        lengthStack[stackPos] = count[c];
                        levelStack[stackPos++] = level + 1;
                    }
                }
                i += count[c];
                count[c] = 0;
            }
            break;
        }
    }

    private static void selectionSort(int[][] a, int from, int to, int level) {
        int layers = a.length;
        int firstLayer = level / 4;
        int i = from;
        block0: while (i < to - 1) {
            int m = i;
            int j = i + 1;
            while (true) {
                if (j < to) {
                } else {
                    if (m != i) {
                        int p = layers;
                        while (p-- != 0) {
                            int u = a[p][i];
                            a[p][i] = a[p][m];
                            a[p][m] = u;
                        }
                    }
                    ++i;
                    continue block0;
                }
                for (int p = firstLayer; p < layers; ++p) {
                    if (a[p][j] < a[p][m]) {
                        m = j;
                        break;
                    }
                    if (a[p][j] > a[p][m]) break;
                }
                ++j;
            }
            break;
        }
        return;
    }

    public static void radixSort(int[][] a) {
        IntArrays.radixSort(a, 0, a[0].length);
    }

    public static void radixSort(int[][] a, int from, int to) {
        if (to - from < 1024) {
            IntArrays.selectionSort(a, from, to, 0);
            return;
        }
        int layers = a.length;
        int maxLevel = 4 * layers - 1;
        int p = layers;
        int l = a[0].length;
        while (p-- != 0) {
            if (a[p].length == l) continue;
            throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0.");
        }
        int stackSize = 255 * (layers * 4 - 1) + 1;
        int stackPos = 0;
        int[] offsetStack = new int[stackSize];
        int[] lengthStack = new int[stackSize];
        int[] levelStack = new int[stackSize];
        offsetStack[stackPos] = from;
        lengthStack[stackPos] = to - from;
        levelStack[stackPos++] = 0;
        int[] count = new int[256];
        int[] pos = new int[256];
        int[] t = new int[layers];
        block1: while (stackPos > 0) {
            int first = offsetStack[--stackPos];
            int length = lengthStack[stackPos];
            int level = levelStack[stackPos];
            int signMask = level % 4 == 0 ? 128 : 0;
            int[] k = a[level / 4];
            int shift = (3 - level % 4) * 8;
            int i = first + length;
            while (i-- != first) {
                int n = k[i] >>> shift & 0xFF ^ signMask;
                count[n] = count[n] + 1;
            }
            int lastUsed = -1;
            int p2 = first;
            for (int i2 = 0; i2 < 256; ++i2) {
                if (count[i2] != 0) {
                    lastUsed = i2;
                }
                pos[i2] = p2 += count[i2];
            }
            int end = first + length - count[lastUsed];
            int i3 = first;
            int c = -1;
            while (true) {
                if (i3 > end) continue block1;
                int p3 = layers;
                while (p3-- != 0) {
                    t[p3] = a[p3][i3];
                }
                c = k[i3] >>> shift & 0xFF ^ signMask;
                if (i3 < end) {
                    block6: while (true) {
                        int n = c;
                        int n2 = pos[n] - 1;
                        pos[n] = n2;
                        int d = n2;
                        if (n2 <= i3) break;
                        c = k[d] >>> shift & 0xFF ^ signMask;
                        p3 = layers;
                        while (true) {
                            if (p3-- == 0) continue block6;
                            int u = t[p3];
                            t[p3] = a[p3][d];
                            a[p3][d] = u;
                        }
                        break;
                    }
                    p3 = layers;
                    while (p3-- != 0) {
                        a[p3][i3] = t[p3];
                    }
                }
                if (level < maxLevel && count[c] > 1) {
                    if (count[c] < 1024) {
                        IntArrays.selectionSort(a, i3, i3 + count[c], level + 1);
                    } else {
                        offsetStack[stackPos] = i3;
                        lengthStack[stackPos] = count[c];
                        levelStack[stackPos++] = level + 1;
                    }
                }
                i3 += count[c];
                count[c] = 0;
            }
            break;
        }
        return;
    }

    public static int[] shuffle(int[] a, int from, int to, Random random) {
        int i = to - from;
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            int t = a[from + i];
            a[from + i] = a[from + p];
            a[from + p] = t;
        }
        return a;
    }

    public static int[] shuffle(int[] a, Random random) {
        int i = a.length;
        while (i-- != 0) {
            int p = random.nextInt(i + 1);
            int t = a[i];
            a[i] = a[p];
            a[p] = t;
        }
        return a;
    }

    public static int[] reverse(int[] a) {
        int length = a.length;
        int i = length / 2;
        while (i-- != 0) {
            int t = a[length - i - 1];
            a[length - i - 1] = a[i];
            a[i] = t;
        }
        return a;
    }

    public static int[] reverse(int[] a, int from, int to) {
        int length = to - from;
        int i = length / 2;
        while (i-- != 0) {
            int t = a[from + length - i - 1];
            a[from + length - i - 1] = a[from + i];
            a[from + i] = t;
        }
        return a;
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;
        private final IntComparator comp;

        public ForkJoinQuickSortComp(int[] x, int from, int to, IntComparator comp) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.comp = comp;
        }

        @Override
        protected void compute() {
            int t;
            int s;
            int[] x;
            block10: {
                int c;
                int a;
                x = this.x;
                int len = this.to - this.from;
                if (len < 8192) {
                    IntArrays.quickSort(x, this.from, this.to, this.comp);
                    return;
                }
                int m = this.from + len / 2;
                int l = this.from;
                int n = this.to - 1;
                s = len / 8;
                l = IntArrays.med3(x, l, l + s, l + 2 * s, this.comp);
                m = IntArrays.med3(x, m - s, m, m + s, this.comp);
                n = IntArrays.med3(x, n - 2 * s, n - s, n, this.comp);
                m = IntArrays.med3(x, l, m, n, this.comp);
                int v = x[m];
                int b = a = this.from;
                int d = c = this.to - 1;
                while (true) {
                    int comparison;
                    if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(x, a++, b);
                        }
                        ++b;
                        continue;
                    }
                    while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(x, c, d--);
                        }
                        --c;
                    }
                    if (b > c) {
                        s = Math.min(a - this.from, b - a);
                        IntArrays.swap(x, this.from, b - s, s);
                        s = Math.min(d - c, this.to - d - 1);
                        IntArrays.swap(x, b, this.to - s, s);
                        s = b - a;
                        t = d - c;
                        if (s > 1) {
                            break;
                        }
                        break block10;
                    }
                    IntArrays.swap(x, b++, c--);
                }
                if (t > 1) {
                    ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp));
                    return;
                }
            }
            if (s > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp));
                return;
            }
            ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp));
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;

        public ForkJoinQuickSort(int[] x, int from, int to) {
            this.from = from;
            this.to = to;
            this.x = x;
        }

        @Override
        protected void compute() {
            int t;
            int s;
            int[] x;
            block10: {
                int c;
                int a;
                x = this.x;
                int len = this.to - this.from;
                if (len < 8192) {
                    IntArrays.quickSort(x, this.from, this.to);
                    return;
                }
                int m = this.from + len / 2;
                int l = this.from;
                int n = this.to - 1;
                s = len / 8;
                l = IntArrays.med3(x, l, l + s, l + 2 * s);
                m = IntArrays.med3(x, m - s, m, m + s);
                n = IntArrays.med3(x, n - 2 * s, n - s, n);
                m = IntArrays.med3(x, l, m, n);
                int v = x[m];
                int b = a = this.from;
                int d = c = this.to - 1;
                while (true) {
                    int comparison;
                    if (b <= c && (comparison = Integer.compare(x[b], v)) <= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(x, a++, b);
                        }
                        ++b;
                        continue;
                    }
                    while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(x, c, d--);
                        }
                        --c;
                    }
                    if (b > c) {
                        s = Math.min(a - this.from, b - a);
                        IntArrays.swap(x, this.from, b - s, s);
                        s = Math.min(d - c, this.to - d - 1);
                        IntArrays.swap(x, b, this.to - s, s);
                        s = b - a;
                        t = d - c;
                        if (s > 1) {
                            break;
                        }
                        break block10;
                    }
                    IntArrays.swap(x, b++, c--);
                }
                if (t > 1) {
                    ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to));
                    return;
                }
            }
            if (s > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s));
                return;
            }
            ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(x, this.to - t, this.to));
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final int[] x;

        public ForkJoinQuickSortIndirect(int[] perm, int[] x, int from, int to) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.perm = perm;
        }

        @Override
        protected void compute() {
            int t;
            int s;
            int[] x;
            block10: {
                int c;
                int a;
                x = this.x;
                int len = this.to - this.from;
                if (len < 8192) {
                    IntArrays.quickSortIndirect(this.perm, x, this.from, this.to);
                    return;
                }
                int m = this.from + len / 2;
                int l = this.from;
                int n = this.to - 1;
                s = len / 8;
                l = IntArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
                m = IntArrays.med3Indirect(this.perm, x, m - s, m, m + s);
                n = IntArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
                m = IntArrays.med3Indirect(this.perm, x, l, m, n);
                int v = x[this.perm[m]];
                int b = a = this.from;
                int d = c = this.to - 1;
                while (true) {
                    int comparison;
                    if (b <= c && (comparison = Integer.compare(x[this.perm[b]], v)) <= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(this.perm, a++, b);
                        }
                        ++b;
                        continue;
                    }
                    while (c >= b && (comparison = Integer.compare(x[this.perm[c]], v)) >= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(this.perm, c, d--);
                        }
                        --c;
                    }
                    if (b > c) {
                        s = Math.min(a - this.from, b - a);
                        IntArrays.swap(this.perm, this.from, b - s, s);
                        s = Math.min(d - c, this.to - d - 1);
                        IntArrays.swap(this.perm, b, this.to - s, s);
                        s = b - a;
                        t = d - c;
                        if (s > 1) {
                            break;
                        }
                        break block10;
                    }
                    IntArrays.swap(this.perm, b++, c--);
                }
                if (t > 1) {
                    ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to));
                    return;
                }
            }
            if (s > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s));
                return;
            }
            ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to));
        }
    }

    protected static class ForkJoinQuickSort2
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;
        private final int[] y;

        public ForkJoinQuickSort2(int[] x, int[] y, int from, int to) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.y = y;
        }

        @Override
        protected void compute() {
            int t;
            int s;
            int[] y;
            int[] x;
            block10: {
                int c;
                int a;
                x = this.x;
                y = this.y;
                int len = this.to - this.from;
                if (len < 8192) {
                    IntArrays.quickSort(x, y, this.from, this.to);
                    return;
                }
                int m = this.from + len / 2;
                int l = this.from;
                int n = this.to - 1;
                s = len / 8;
                l = IntArrays.med3(x, y, l, l + s, l + 2 * s);
                m = IntArrays.med3(x, y, m - s, m, m + s);
                n = IntArrays.med3(x, y, n - 2 * s, n - s, n);
                m = IntArrays.med3(x, y, l, m, n);
                int v = x[m];
                int w = y[m];
                int b = a = this.from;
                int d = c = this.to - 1;
                while (true) {
                    int t2;
                    int comparison;
                    if (b <= c && (comparison = (t2 = Integer.compare(x[b], v)) == 0 ? Integer.compare(y[b], w) : t2) <= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(x, y, a++, b);
                        }
                        ++b;
                        continue;
                    }
                    while (c >= b && (comparison = (t2 = Integer.compare(x[c], v)) == 0 ? Integer.compare(y[c], w) : t2) >= 0) {
                        if (comparison == 0) {
                            IntArrays.swap(x, y, c, d--);
                        }
                        --c;
                    }
                    if (b > c) {
                        s = Math.min(a - this.from, b - a);
                        IntArrays.swap(x, y, this.from, b - s, s);
                        s = Math.min(d - c, this.to - d - 1);
                        IntArrays.swap(x, y, b, this.to - s, s);
                        s = b - a;
                        t = d - c;
                        if (s > 1) {
                            break;
                        }
                        break block10;
                    }
                    IntArrays.swap(x, y, b++, c--);
                }
                if (t > 1) {
                    ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to));
                    return;
                }
            }
            if (s > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s));
                return;
            }
            ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(x, y, this.to - t, this.to));
        }
    }

    protected static final class Segment {
        protected final int offset;
        protected final int length;
        protected final int level;

        protected Segment(int offset, int length, int level) {
            this.offset = offset;
            this.length = length;
            this.level = level;
        }

        public String toString() {
            return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
        }
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<int[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(int[] o) {
            return java.util.Arrays.hashCode(o);
        }

        @Override
        public boolean equals(int[] a, int[] b) {
            return java.util.Arrays.equals(a, b);
        }
    }
}

