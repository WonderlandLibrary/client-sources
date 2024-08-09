/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.floats.FloatComparator;
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

public final class FloatArrays {
    public static final float[] EMPTY_ARRAY = new float[0];
    public static final float[] DEFAULT_EMPTY_ARRAY = new float[0];
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
    public static final Hash.Strategy<float[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private FloatArrays() {
    }

    public static float[] forceCapacity(float[] fArray, int n, int n2) {
        float[] fArray2 = new float[n];
        System.arraycopy(fArray, 0, fArray2, 0, n2);
        return fArray2;
    }

    public static float[] ensureCapacity(float[] fArray, int n) {
        return FloatArrays.ensureCapacity(fArray, n, fArray.length);
    }

    public static float[] ensureCapacity(float[] fArray, int n, int n2) {
        return n > fArray.length ? FloatArrays.forceCapacity(fArray, n, n2) : fArray;
    }

    public static float[] grow(float[] fArray, int n) {
        return FloatArrays.grow(fArray, n, fArray.length);
    }

    public static float[] grow(float[] fArray, int n, int n2) {
        if (n > fArray.length) {
            int n3 = (int)Math.max(Math.min((long)fArray.length + (long)(fArray.length >> 1), 0x7FFFFFF7L), (long)n);
            float[] fArray2 = new float[n3];
            System.arraycopy(fArray, 0, fArray2, 0, n2);
            return fArray2;
        }
        return fArray;
    }

    public static float[] trim(float[] fArray, int n) {
        if (n >= fArray.length) {
            return fArray;
        }
        float[] fArray2 = n == 0 ? EMPTY_ARRAY : new float[n];
        System.arraycopy(fArray, 0, fArray2, 0, n);
        return fArray2;
    }

    public static float[] setLength(float[] fArray, int n) {
        if (n == fArray.length) {
            return fArray;
        }
        if (n < fArray.length) {
            return FloatArrays.trim(fArray, n);
        }
        return FloatArrays.ensureCapacity(fArray, n);
    }

    public static float[] copy(float[] fArray, int n, int n2) {
        FloatArrays.ensureOffsetLength(fArray, n, n2);
        float[] fArray2 = n2 == 0 ? EMPTY_ARRAY : new float[n2];
        System.arraycopy(fArray, n, fArray2, 0, n2);
        return fArray2;
    }

    public static float[] copy(float[] fArray) {
        return (float[])fArray.clone();
    }

    @Deprecated
    public static void fill(float[] fArray, float f) {
        int n = fArray.length;
        while (n-- != 0) {
            fArray[n] = f;
        }
    }

    @Deprecated
    public static void fill(float[] fArray, int n, int n2, float f) {
        FloatArrays.ensureFromTo(fArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                fArray[n2] = f;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                fArray[i] = f;
            }
        }
    }

    @Deprecated
    public static boolean equals(float[] fArray, float[] fArray2) {
        int n = fArray.length;
        if (n != fArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (Float.floatToIntBits(fArray[n]) == Float.floatToIntBits(fArray2[n])) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(float[] fArray, int n, int n2) {
        Arrays.ensureFromTo(fArray.length, n, n2);
    }

    public static void ensureOffsetLength(float[] fArray, int n, int n2) {
        Arrays.ensureOffsetLength(fArray.length, n, n2);
    }

    public static void ensureSameLength(float[] fArray, float[] fArray2) {
        if (fArray.length != fArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + fArray.length + " != " + fArray2.length);
        }
    }

    public static void swap(float[] fArray, int n, int n2) {
        float f = fArray[n];
        fArray[n] = fArray[n2];
        fArray[n2] = f;
    }

    public static void swap(float[] fArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            FloatArrays.swap(fArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(float[] fArray, int n, int n2, int n3, FloatComparator floatComparator) {
        int n4 = floatComparator.compare(fArray[n], fArray[n2]);
        int n5 = floatComparator.compare(fArray[n], fArray[n3]);
        int n6 = floatComparator.compare(fArray[n2], fArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (floatComparator.compare(fArray[j], fArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            float f = fArray[i];
            fArray[i] = fArray[n3];
            fArray[n3] = f;
        }
    }

    private static void insertionSort(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        int n3 = n;
        while (++n3 < n2) {
            float f = fArray[n3];
            int n4 = n3;
            float f2 = fArray[n4 - 1];
            while (floatComparator.compare(f, f2) < 0) {
                fArray[n4] = f2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                f2 = fArray[--n4 - 1];
            }
            fArray[n4] = f;
        }
    }

    public static void quickSort(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            FloatArrays.selectionSort(fArray, n, n2, floatComparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = FloatArrays.med3(fArray, n8, n8 + n10, n8 + 2 * n10, floatComparator);
            n7 = FloatArrays.med3(fArray, n7 - n10, n7, n7 + n10, floatComparator);
            n9 = FloatArrays.med3(fArray, n9 - 2 * n10, n9 - n10, n9, floatComparator);
        }
        n7 = FloatArrays.med3(fArray, n8, n7, n9, floatComparator);
        float f = fArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = floatComparator.compare(fArray[n11], f)) <= 0) {
                if (n3 == 0) {
                    FloatArrays.swap(fArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = floatComparator.compare(fArray[n4], f)) >= 0) {
                if (n3 == 0) {
                    FloatArrays.swap(fArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            FloatArrays.swap(fArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        FloatArrays.swap(fArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        FloatArrays.swap(fArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            FloatArrays.quickSort(fArray, n, n + n3, floatComparator);
        }
        if ((n3 = n12 - n4) > 1) {
            FloatArrays.quickSort(fArray, n2 - n3, n2, floatComparator);
        }
    }

    public static void quickSort(float[] fArray, FloatComparator floatComparator) {
        FloatArrays.quickSort(fArray, 0, fArray.length, floatComparator);
    }

    public static void parallelQuickSort(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        if (n2 - n < 8192) {
            FloatArrays.quickSort(fArray, n, n2, floatComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(fArray, n, n2, floatComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(float[] fArray, FloatComparator floatComparator) {
        FloatArrays.parallelQuickSort(fArray, 0, fArray.length, floatComparator);
    }

    private static int med3(float[] fArray, int n, int n2, int n3) {
        int n4 = Float.compare(fArray[n], fArray[n2]);
        int n5 = Float.compare(fArray[n], fArray[n3]);
        int n6 = Float.compare(fArray[n2], fArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(float[] fArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (Float.compare(fArray[j], fArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            float f = fArray[i];
            fArray[i] = fArray[n3];
            fArray[n3] = f;
        }
    }

    private static void insertionSort(float[] fArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            float f = fArray[n3];
            int n4 = n3;
            float f2 = fArray[n4 - 1];
            while (Float.compare(f, f2) < 0) {
                fArray[n4] = f2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                f2 = fArray[--n4 - 1];
            }
            fArray[n4] = f;
        }
    }

    public static void quickSort(float[] fArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            FloatArrays.selectionSort(fArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = FloatArrays.med3(fArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = FloatArrays.med3(fArray, n7 - n10, n7, n7 + n10);
            n9 = FloatArrays.med3(fArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = FloatArrays.med3(fArray, n8, n7, n9);
        float f = fArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Float.compare(fArray[n11], f)) <= 0) {
                if (n3 == 0) {
                    FloatArrays.swap(fArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Float.compare(fArray[n4], f)) >= 0) {
                if (n3 == 0) {
                    FloatArrays.swap(fArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            FloatArrays.swap(fArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        FloatArrays.swap(fArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        FloatArrays.swap(fArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            FloatArrays.quickSort(fArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            FloatArrays.quickSort(fArray, n2 - n3, n2);
        }
    }

    public static void quickSort(float[] fArray) {
        FloatArrays.quickSort(fArray, 0, fArray.length);
    }

    public static void parallelQuickSort(float[] fArray, int n, int n2) {
        if (n2 - n < 8192) {
            FloatArrays.quickSort(fArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(fArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(float[] fArray) {
        FloatArrays.parallelQuickSort(fArray, 0, fArray.length);
    }

    private static int med3Indirect(int[] nArray, float[] fArray, int n, int n2, int n3) {
        float f = fArray[nArray[n]];
        float f2 = fArray[nArray[n2]];
        float f3 = fArray[nArray[n3]];
        int n4 = Float.compare(f, f2);
        int n5 = Float.compare(f, f3);
        int n6 = Float.compare(f2, f3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, float[] fArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (Float.compare(fArray[n4], fArray[n6]) < 0) {
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

    public static void quickSortIndirect(int[] nArray, float[] fArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            FloatArrays.insertionSortIndirect(nArray, fArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = FloatArrays.med3Indirect(nArray, fArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = FloatArrays.med3Indirect(nArray, fArray, n7 - n10, n7, n7 + n10);
            n9 = FloatArrays.med3Indirect(nArray, fArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = FloatArrays.med3Indirect(nArray, fArray, n8, n7, n9);
        float f = fArray[nArray[n7]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Float.compare(fArray[nArray[n11]], f)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Float.compare(fArray[nArray[n4]], f)) >= 0) {
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
            FloatArrays.quickSortIndirect(nArray, fArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            FloatArrays.quickSortIndirect(nArray, fArray, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, float[] fArray) {
        FloatArrays.quickSortIndirect(nArray, fArray, 0, fArray.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, float[] fArray, int n, int n2) {
        if (n2 - n < 8192) {
            FloatArrays.quickSortIndirect(nArray, fArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, fArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, float[] fArray) {
        FloatArrays.parallelQuickSortIndirect(nArray, fArray, 0, fArray.length);
    }

    public static void stabilize(int[] nArray, float[] fArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (fArray[nArray[i]] == fArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, float[] fArray) {
        FloatArrays.stabilize(nArray, fArray, 0, nArray.length);
    }

    private static int med3(float[] fArray, float[] fArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Float.compare(fArray[n], fArray[n2]);
        int n6 = n5 == 0 ? Float.compare(fArray2[n], fArray2[n2]) : n5;
        n5 = Float.compare(fArray[n], fArray[n3]);
        int n7 = n5 == 0 ? Float.compare(fArray2[n], fArray2[n3]) : n5;
        n5 = Float.compare(fArray[n2], fArray[n3]);
        int n8 = n4 = n5 == 0 ? Float.compare(fArray2[n2], fArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(float[] fArray, float[] fArray2, int n, int n2) {
        float f = fArray[n];
        float f2 = fArray2[n];
        fArray[n] = fArray[n2];
        fArray2[n] = fArray2[n2];
        fArray[n2] = f;
        fArray2[n2] = f2;
    }

    private static void swap(float[] fArray, float[] fArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            FloatArrays.swap(fArray, fArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(float[] fArray, float[] fArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                int n4 = Float.compare(fArray[j], fArray[n3]);
                if (n4 >= 0 && (n4 != 0 || Float.compare(fArray2[j], fArray2[n3]) >= 0)) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            float f = fArray[i];
            fArray[i] = fArray[n3];
            fArray[n3] = f;
            f = fArray2[i];
            fArray2[i] = fArray2[n3];
            fArray2[n3] = f;
        }
    }

    public static void quickSort(float[] fArray, float[] fArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            FloatArrays.selectionSort(fArray, fArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = FloatArrays.med3(fArray, fArray2, n8, n8 + n10, n8 + 2 * n10);
            n7 = FloatArrays.med3(fArray, fArray2, n7 - n10, n7, n7 + n10);
            n9 = FloatArrays.med3(fArray, fArray2, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = FloatArrays.med3(fArray, fArray2, n8, n7, n9);
        float f = fArray[n7];
        float f2 = fArray2[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            int n13;
            if (n11 <= n4 && (n3 = (n13 = Float.compare(fArray[n11], f)) == 0 ? Float.compare(fArray2[n11], f2) : n13) <= 0) {
                if (n3 == 0) {
                    FloatArrays.swap(fArray, fArray2, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = (n13 = Float.compare(fArray[n4], f)) == 0 ? Float.compare(fArray2[n4], f2) : n13) >= 0) {
                if (n3 == 0) {
                    FloatArrays.swap(fArray, fArray2, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            FloatArrays.swap(fArray, fArray2, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        FloatArrays.swap(fArray, fArray2, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        FloatArrays.swap(fArray, fArray2, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            FloatArrays.quickSort(fArray, fArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            FloatArrays.quickSort(fArray, fArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(float[] fArray, float[] fArray2) {
        FloatArrays.ensureSameLength(fArray, fArray2);
        FloatArrays.quickSort(fArray, fArray2, 0, fArray.length);
    }

    public static void parallelQuickSort(float[] fArray, float[] fArray2, int n, int n2) {
        if (n2 - n < 8192) {
            FloatArrays.quickSort(fArray, fArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(fArray, fArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(float[] fArray, float[] fArray2) {
        FloatArrays.ensureSameLength(fArray, fArray2);
        FloatArrays.parallelQuickSort(fArray, fArray2, 0, fArray.length);
    }

    public static void mergeSort(float[] fArray, int n, int n2, float[] fArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            FloatArrays.insertionSort(fArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        FloatArrays.mergeSort(fArray2, n, n4, fArray);
        FloatArrays.mergeSort(fArray2, n4, n2, fArray);
        if (Float.compare(fArray2[n4 - 1], fArray2[n4]) <= 0) {
            System.arraycopy(fArray2, n, fArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            fArray[i] = n6 >= n2 || n5 < n4 && Float.compare(fArray2[n5], fArray2[n6]) <= 0 ? fArray2[n5++] : fArray2[n6++];
        }
    }

    public static void mergeSort(float[] fArray, int n, int n2) {
        FloatArrays.mergeSort(fArray, n, n2, (float[])fArray.clone());
    }

    public static void mergeSort(float[] fArray) {
        FloatArrays.mergeSort(fArray, 0, fArray.length);
    }

    public static void mergeSort(float[] fArray, int n, int n2, FloatComparator floatComparator, float[] fArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            FloatArrays.insertionSort(fArray, n, n2, floatComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        FloatArrays.mergeSort(fArray2, n, n4, floatComparator, fArray);
        FloatArrays.mergeSort(fArray2, n4, n2, floatComparator, fArray);
        if (floatComparator.compare(fArray2[n4 - 1], fArray2[n4]) <= 0) {
            System.arraycopy(fArray2, n, fArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            fArray[i] = n6 >= n2 || n5 < n4 && floatComparator.compare(fArray2[n5], fArray2[n6]) <= 0 ? fArray2[n5++] : fArray2[n6++];
        }
    }

    public static void mergeSort(float[] fArray, int n, int n2, FloatComparator floatComparator) {
        FloatArrays.mergeSort(fArray, n, n2, floatComparator, (float[])fArray.clone());
    }

    public static void mergeSort(float[] fArray, FloatComparator floatComparator) {
        FloatArrays.mergeSort(fArray, 0, fArray.length, floatComparator);
    }

    public static int binarySearch(float[] fArray, int n, int n2, float f) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            float f2 = fArray[n3];
            if (f2 < f) {
                n = n3 + 1;
                continue;
            }
            if (f2 > f) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int binarySearch(float[] fArray, float f) {
        return FloatArrays.binarySearch(fArray, 0, fArray.length, f);
    }

    public static int binarySearch(float[] fArray, int n, int n2, float f, FloatComparator floatComparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            float f2 = fArray[n3];
            int n4 = floatComparator.compare(f2, f);
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

    public static int binarySearch(float[] fArray, float f, FloatComparator floatComparator) {
        return FloatArrays.binarySearch(fArray, 0, fArray.length, f, floatComparator);
    }

    private static final int fixFloat(float f) {
        int n = Float.floatToIntBits(f);
        return n >= 0 ? n : n ^ Integer.MAX_VALUE;
    }

    public static void radixSort(float[] fArray) {
        FloatArrays.radixSort(fArray, 0, fArray.length);
    }

    public static void radixSort(float[] fArray, int n, int n2) {
        if (n2 - n < 1024) {
            FloatArrays.quickSort(fArray, n, n2);
            return;
        }
        int n3 = 3;
        int n4 = 766;
        int n5 = 0;
        int[] nArray = new int[766];
        int[] nArray2 = new int[766];
        int[] nArray3 = new int[766];
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
            int n10 = n9 % 4 == 0 ? 128 : 0;
            int n11 = (3 - n9 % 4) * 8;
            int n12 = n7 + n8;
            while (n12-- != n7) {
                int n13 = FloatArrays.fixFloat(fArray[n12]) >>> n11 & 0xFF ^ n10;
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
                float f = fArray[n14];
                n15 = FloatArrays.fixFloat(f) >>> n11 & 0xFF ^ n10;
                if (n14 < n6) {
                    while (true) {
                        int n16 = n15;
                        int n17 = nArray5[n16] - 1;
                        nArray5[n16] = n17;
                        int n18 = n17;
                        if (n17 <= n14) break;
                        float f2 = f;
                        f = fArray[n18];
                        fArray[n18] = f2;
                        n15 = FloatArrays.fixFloat(f) >>> n11 & 0xFF ^ n10;
                    }
                    fArray[n14] = f;
                }
                if (n9 < 3 && nArray4[n15] > 1) {
                    if (nArray4[n15] < 1024) {
                        FloatArrays.quickSort(fArray, n14, n14 + nArray4[n15]);
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

    public static void parallelRadixSort(float[] fArray, int n, int n2) {
        if (n2 - n < 1024) {
            FloatArrays.quickSort(fArray, n, n2);
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
            executorCompletionService.submit(() -> FloatArrays.lambda$parallelRadixSort$0(atomicInteger, n4, linkedBlockingQueue, fArray));
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

    public static void parallelRadixSort(float[] fArray) {
        FloatArrays.parallelRadixSort(fArray, 0, fArray.length);
    }

    public static void radixSortIndirect(int[] nArray, float[] fArray, boolean bl) {
        FloatArrays.radixSortIndirect(nArray, fArray, 0, nArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, float[] fArray, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            FloatArrays.insertionSortIndirect(nArray, fArray, n, n2);
            return;
        }
        int n3 = 3;
        int n4 = 766;
        int n5 = 0;
        int[] nArray3 = new int[766];
        int[] nArray4 = new int[766];
        int[] nArray5 = new int[766];
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
            int n11 = n10 % 4 == 0 ? 128 : 0;
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = FloatArrays.fixFloat(fArray[nArray[n13]]) >>> n12 & 0xFF ^ n11;
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
                    int n16 = FloatArrays.fixFloat(fArray[nArray[n6]]) >>> n12 & 0xFF ^ n11;
                    int n17 = nArray7[n16] - 1;
                    nArray7[n16] = n17;
                    nArray2[n17] = nArray[n6];
                }
                System.arraycopy(nArray2, 0, nArray, n8, n9);
                n7 = n8;
                for (n6 = 0; n6 <= n13; ++n6) {
                    if (n10 < 3 && nArray6[n6] > 1) {
                        if (nArray6[n6] < 1024) {
                            FloatArrays.insertionSortIndirect(nArray, fArray, n7, n7 + nArray6[n6]);
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
                n18 = FloatArrays.fixFloat(fArray[n19]) >>> n12 & 0xFF ^ n11;
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
                        n18 = FloatArrays.fixFloat(fArray[n19]) >>> n12 & 0xFF ^ n11;
                    }
                    nArray[n7] = n19;
                }
                if (n10 < 3 && nArray6[n18] > 1) {
                    if (nArray6[n18] < 1024) {
                        FloatArrays.insertionSortIndirect(nArray, fArray, n7, n7 + nArray6[n18]);
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

    public static void parallelRadixSortIndirect(int[] nArray, float[] fArray, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            FloatArrays.radixSortIndirect(nArray, fArray, n, n2, bl);
            return;
        }
        int n3 = 3;
        LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int n4 = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(n4, Executors.defaultThreadFactory());
        ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<Void>(executorService);
        int[] nArray2 = bl ? new int[nArray.length] : null;
        int n5 = n4;
        while (n5-- != 0) {
            executorCompletionService.submit(() -> FloatArrays.lambda$parallelRadixSortIndirect$1(atomicInteger, n4, linkedBlockingQueue, fArray, nArray, bl, nArray2));
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

    public static void parallelRadixSortIndirect(int[] nArray, float[] fArray, boolean bl) {
        FloatArrays.parallelRadixSortIndirect(nArray, fArray, 0, fArray.length, bl);
    }

    public static void radixSort(float[] fArray, float[] fArray2) {
        FloatArrays.ensureSameLength(fArray, fArray2);
        FloatArrays.radixSort(fArray, fArray2, 0, fArray.length);
    }

    public static void radixSort(float[] fArray, float[] fArray2, int n, int n2) {
        if (n2 - n < 1024) {
            FloatArrays.selectionSort(fArray, fArray2, n, n2);
            return;
        }
        int n3 = 2;
        int n4 = 7;
        int n5 = 1786;
        int n6 = 0;
        int[] nArray = new int[1786];
        int[] nArray2 = new int[1786];
        int[] nArray3 = new int[1786];
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
            int n11 = n10 % 4 == 0 ? 128 : 0;
            float[] fArray3 = n10 < 4 ? fArray : fArray2;
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = FloatArrays.fixFloat(fArray3[n13]) >>> n12 & 0xFF ^ n11;
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
                float f = fArray[n15];
                float f2 = fArray2[n15];
                n16 = FloatArrays.fixFloat(fArray3[n15]) >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    while (true) {
                        int n17 = n16;
                        int n18 = nArray5[n17] - 1;
                        nArray5[n17] = n18;
                        int n19 = n18;
                        if (n18 <= n15) break;
                        n16 = FloatArrays.fixFloat(fArray3[n19]) >>> n12 & 0xFF ^ n11;
                        float f3 = f;
                        f = fArray[n19];
                        fArray[n19] = f3;
                        f3 = f2;
                        f2 = fArray2[n19];
                        fArray2[n19] = f3;
                    }
                    fArray[n15] = f;
                    fArray2[n15] = f2;
                }
                if (n10 < 7 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        FloatArrays.selectionSort(fArray, fArray2, n15, n15 + nArray4[n16]);
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

    public static void parallelRadixSort(float[] fArray, float[] fArray2, int n, int n2) {
        if (n2 - n < 1024) {
            FloatArrays.quickSort(fArray, fArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (fArray.length != fArray2.length) {
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
            executorCompletionService.submit(() -> FloatArrays.lambda$parallelRadixSort$2(atomicInteger, n5, linkedBlockingQueue, fArray, fArray2));
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

    public static void parallelRadixSort(float[] fArray, float[] fArray2) {
        FloatArrays.ensureSameLength(fArray, fArray2);
        FloatArrays.parallelRadixSort(fArray, fArray2, 0, fArray.length);
    }

    private static void insertionSortIndirect(int[] nArray, float[] fArray, float[] fArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (Float.compare(fArray[n4], fArray[n6]) < 0 || Float.compare(fArray[n4], fArray[n6]) == 0 && Float.compare(fArray2[n4], fArray2[n6]) < 0) {
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

    public static void radixSortIndirect(int[] nArray, float[] fArray, float[] fArray2, boolean bl) {
        FloatArrays.ensureSameLength(fArray, fArray2);
        FloatArrays.radixSortIndirect(nArray, fArray, fArray2, 0, fArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, float[] fArray, float[] fArray2, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            FloatArrays.insertionSortIndirect(nArray, fArray, fArray2, n, n2);
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
        int[] nArray8 = nArray2 = bl ? new int[nArray.length] : null;
        while (n6 > 0) {
            int n7;
            int n8;
            int n9 = nArray3[--n6];
            int n10 = nArray4[n6];
            int n11 = nArray5[n6];
            int n12 = n11 % 4 == 0 ? 128 : 0;
            float[] fArray3 = n11 < 4 ? fArray : fArray2;
            int n13 = (3 - n11 % 4) * 8;
            int n14 = n9 + n10;
            while (n14-- != n9) {
                int n15 = FloatArrays.fixFloat(fArray3[nArray[n14]]) >>> n13 & 0xFF ^ n12;
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
                    int n17 = FloatArrays.fixFloat(fArray3[nArray[n7]]) >>> n13 & 0xFF ^ n12;
                    int n18 = nArray7[n17] - 1;
                    nArray7[n17] = n18;
                    nArray2[n18] = nArray[n7];
                }
                System.arraycopy(nArray2, 0, nArray, n9, n10);
                n8 = n9;
                for (n7 = 0; n7 < 256; ++n7) {
                    if (n11 < 7 && nArray6[n7] > 1) {
                        if (nArray6[n7] < 1024) {
                            FloatArrays.insertionSortIndirect(nArray, fArray, fArray2, n8, n8 + nArray6[n7]);
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
                n19 = FloatArrays.fixFloat(fArray3[n20]) >>> n13 & 0xFF ^ n12;
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
                        n19 = FloatArrays.fixFloat(fArray3[n20]) >>> n13 & 0xFF ^ n12;
                    }
                    nArray[n8] = n20;
                }
                if (n11 < 7 && nArray6[n19] > 1) {
                    if (nArray6[n19] < 1024) {
                        FloatArrays.insertionSortIndirect(nArray, fArray, fArray2, n8, n8 + nArray6[n19]);
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

    private static void selectionSort(float[][] fArray, int n, int n2, int n3) {
        int n4 = fArray.length;
        int n5 = n3 / 4;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7 = i;
            block1: for (n6 = i + 1; n6 < n2; ++n6) {
                for (int j = n5; j < n4; ++j) {
                    if (fArray[j][n6] < fArray[j][n7]) {
                        n7 = n6;
                        continue block1;
                    }
                    if (fArray[j][n6] > fArray[j][n7]) continue block1;
                }
            }
            if (n7 == i) continue;
            n6 = n4;
            while (n6-- != 0) {
                float f = fArray[n6][i];
                fArray[n6][i] = fArray[n6][n7];
                fArray[n6][n7] = f;
            }
        }
    }

    public static void radixSort(float[][] fArray) {
        FloatArrays.radixSort(fArray, 0, fArray[0].length);
    }

    public static void radixSort(float[][] fArray, int n, int n2) {
        if (n2 - n < 1024) {
            FloatArrays.selectionSort(fArray, n, n2, 0);
            return;
        }
        int n3 = fArray.length;
        int n4 = 4 * n3 - 1;
        int n5 = n3;
        int n6 = fArray[0].length;
        while (n5-- != 0) {
            if (fArray[n5].length == n6) continue;
            throw new IllegalArgumentException("The array of index " + n5 + " has not the same length of the array of index 0.");
        }
        n5 = 255 * (n3 * 4 - 1) + 1;
        n6 = 0;
        int[] nArray = new int[n5];
        int[] nArray2 = new int[n5];
        int[] nArray3 = new int[n5];
        nArray[n6] = n;
        nArray2[n6] = n2 - n;
        nArray3[n6++] = 0;
        int[] nArray4 = new int[256];
        int[] nArray5 = new int[256];
        float[] fArray2 = new float[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray[--n6];
            int n9 = nArray2[n6];
            int n10 = nArray3[n6];
            int n11 = n10 % 4 == 0 ? 128 : 0;
            float[] fArray3 = fArray[n10 / 4];
            int n12 = (3 - n10 % 4) * 8;
            int n13 = n8 + n9;
            while (n13-- != n8) {
                int n14 = FloatArrays.fixFloat(fArray3[n13]) >>> n12 & 0xFF ^ n11;
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
                    fArray2[n17] = fArray[n17][n15];
                }
                n16 = FloatArrays.fixFloat(fArray3[n15]) >>> n12 & 0xFF ^ n11;
                if (n15 < n7) {
                    block6: while (true) {
                        int n18 = n16;
                        int n19 = nArray5[n18] - 1;
                        nArray5[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n15) break;
                        n16 = FloatArrays.fixFloat(fArray3[n20]) >>> n12 & 0xFF ^ n11;
                        n17 = n3;
                        while (true) {
                            if (n17-- == 0) continue block6;
                            float f = fArray2[n17];
                            fArray2[n17] = fArray[n17][n20];
                            fArray[n17][n20] = f;
                        }
                        break;
                    }
                    n17 = n3;
                    while (n17-- != 0) {
                        fArray[n17][n15] = fArray2[n17];
                    }
                }
                if (n10 < n4 && nArray4[n16] > 1) {
                    if (nArray4[n16] < 1024) {
                        FloatArrays.selectionSort(fArray, n15, n15 + nArray4[n16], n10 + 1);
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

    public static float[] shuffle(float[] fArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            float f = fArray[n + n3];
            fArray[n + n3] = fArray[n + n4];
            fArray[n + n4] = f;
        }
        return fArray;
    }

    public static float[] shuffle(float[] fArray, Random random2) {
        int n = fArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            float f = fArray[n];
            fArray[n] = fArray[n2];
            fArray[n2] = f;
        }
        return fArray;
    }

    public static float[] reverse(float[] fArray) {
        int n = fArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            float f = fArray[n - n2 - 1];
            fArray[n - n2 - 1] = fArray[n2];
            fArray[n2] = f;
        }
        return fArray;
    }

    public static float[] reverse(float[] fArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            float f = fArray[n + n3 - n4 - 1];
            fArray[n + n3 - n4 - 1] = fArray[n + n4];
            fArray[n + n4] = f;
        }
        return fArray;
    }

    private static Void lambda$parallelRadixSort$2(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, float[] fArray, float[] fArray2) throws Exception {
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
            int n7 = n6 % 4 == 0 ? 128 : 0;
            float[] fArray3 = n6 < 4 ? fArray : fArray2;
            int n8 = (3 - n6 % 4) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = FloatArrays.fixFloat(fArray3[n9]) >>> n8 & 0xFF ^ n7;
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
                float f = fArray[n11];
                float f2 = fArray2[n11];
                n12 = FloatArrays.fixFloat(fArray3[n11]) >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        n12 = FloatArrays.fixFloat(fArray3[n15]) >>> n8 & 0xFF ^ n7;
                        float f3 = f;
                        float f4 = f2;
                        f = fArray[n15];
                        f2 = fArray2[n15];
                        fArray[n15] = f3;
                        fArray2[n15] = f4;
                    }
                    fArray[n11] = f;
                    fArray2[n11] = f2;
                }
                if (n6 < 7 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        FloatArrays.quickSort(fArray, fArray2, n11, n11 + nArray[n12]);
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

    private static Void lambda$parallelRadixSortIndirect$1(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, float[] fArray, int[] nArray, boolean bl, int[] nArray2) throws Exception {
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
            int n8 = (3 - n6 % 4) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = FloatArrays.fixFloat(fArray[nArray[n9]]) >>> n8 & 0xFF ^ n7;
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
                    int n12 = FloatArrays.fixFloat(fArray[nArray[n2]]) >>> n8 & 0xFF ^ n7;
                    int n13 = nArray4[n12] - 1;
                    nArray4[n12] = n13;
                    nArray2[n13] = nArray[n2];
                }
                System.arraycopy(nArray2, n4, nArray, n4, n5);
                n11 = n4;
                for (n2 = 0; n2 <= n9; ++n2) {
                    if (n6 < 3 && nArray3[n2] > 1) {
                        if (nArray3[n2] < 1024) {
                            FloatArrays.radixSortIndirect(nArray, fArray, n11, n11 + nArray3[n2], bl);
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
                    n14 = FloatArrays.fixFloat(fArray[n15]) >>> n8 & 0xFF ^ n7;
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
                            n14 = FloatArrays.fixFloat(fArray[n15]) >>> n8 & 0xFF ^ n7;
                        }
                        nArray[n11] = n15;
                    }
                    if (n6 < 3 && nArray3[n14] > 1) {
                        if (nArray3[n14] < 1024) {
                            FloatArrays.radixSortIndirect(nArray, fArray, n11, n11 + nArray3[n14], bl);
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

    private static Void lambda$parallelRadixSort$0(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, float[] fArray) throws Exception {
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
            int n7 = n6 % 4 == 0 ? 128 : 0;
            int n8 = (3 - n6 % 4) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = FloatArrays.fixFloat(fArray[n9]) >>> n8 & 0xFF ^ n7;
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
                float f = fArray[n11];
                n12 = FloatArrays.fixFloat(f) >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        float f2 = f;
                        f = fArray[n15];
                        fArray[n15] = f2;
                        n12 = FloatArrays.fixFloat(f) >>> n8 & 0xFF ^ n7;
                    }
                    fArray[n11] = f;
                }
                if (n6 < 3 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        FloatArrays.quickSort(fArray, n11, n11 + nArray[n12]);
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

    static int access$000(float[] fArray, int n, int n2, int n3, FloatComparator floatComparator) {
        return FloatArrays.med3(fArray, n, n2, n3, floatComparator);
    }

    static int access$100(float[] fArray, int n, int n2, int n3) {
        return FloatArrays.med3(fArray, n, n2, n3);
    }

    static int access$200(int[] nArray, float[] fArray, int n, int n2, int n3) {
        return FloatArrays.med3Indirect(nArray, fArray, n, n2, n3);
    }

    static int access$300(float[] fArray, float[] fArray2, int n, int n2, int n3) {
        return FloatArrays.med3(fArray, fArray2, n, n2, n3);
    }

    static void access$400(float[] fArray, float[] fArray2, int n, int n2) {
        FloatArrays.swap(fArray, fArray2, n, n2);
    }

    static void access$500(float[] fArray, float[] fArray2, int n, int n2, int n3) {
        FloatArrays.swap(fArray, fArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<float[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(float[] fArray) {
            return java.util.Arrays.hashCode(fArray);
        }

        @Override
        public boolean equals(float[] fArray, float[] fArray2) {
            return java.util.Arrays.equals(fArray, fArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((float[])object, (float[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((float[])object);
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
        private final float[] x;
        private final float[] y;

        public ForkJoinQuickSort2(float[] fArray, float[] fArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = fArray;
            this.y = fArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            float[] fArray = this.x;
            float[] fArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                FloatArrays.quickSort(fArray, fArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = FloatArrays.access$300(fArray, fArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = FloatArrays.access$300(fArray, fArray2, n5 - n8, n5, n5 + n8);
            n7 = FloatArrays.access$300(fArray, fArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = FloatArrays.access$300(fArray, fArray2, n6, n5, n7);
            float f = fArray[n5];
            float f2 = fArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = Float.compare(fArray[n9], f)) == 0 ? Float.compare(fArray2[n9], f2) : n11) <= 0) {
                    if (n == 0) {
                        FloatArrays.access$400(fArray, fArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = Float.compare(fArray[n2], f)) == 0 ? Float.compare(fArray2[n2], f2) : n11) >= 0) {
                    if (n == 0) {
                        FloatArrays.access$400(fArray, fArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                FloatArrays.access$400(fArray, fArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            FloatArrays.access$500(fArray, fArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            FloatArrays.access$500(fArray, fArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(fArray, fArray2, this.from, this.from + n8), new ForkJoinQuickSort2(fArray, fArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(fArray, fArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(fArray, fArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final float[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, float[] fArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = fArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            float[] fArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                FloatArrays.quickSortIndirect(this.perm, fArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = FloatArrays.access$200(this.perm, fArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = FloatArrays.access$200(this.perm, fArray, n5 - n8, n5, n5 + n8);
            n7 = FloatArrays.access$200(this.perm, fArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = FloatArrays.access$200(this.perm, fArray, n6, n5, n7);
            float f = fArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Float.compare(fArray[this.perm[n9]], f)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Float.compare(fArray[this.perm[n2]], f)) >= 0) {
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
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, fArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, fArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, fArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, fArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final float[] x;

        public ForkJoinQuickSort(float[] fArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = fArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            float[] fArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                FloatArrays.quickSort(fArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = FloatArrays.access$100(fArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = FloatArrays.access$100(fArray, n5 - n8, n5, n5 + n8);
            n7 = FloatArrays.access$100(fArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = FloatArrays.access$100(fArray, n6, n5, n7);
            float f = fArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Float.compare(fArray[n9], f)) <= 0) {
                    if (n == 0) {
                        FloatArrays.swap(fArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Float.compare(fArray[n2], f)) >= 0) {
                    if (n == 0) {
                        FloatArrays.swap(fArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                FloatArrays.swap(fArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            FloatArrays.swap(fArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            FloatArrays.swap(fArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(fArray, this.from, this.from + n8), new ForkJoinQuickSort(fArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(fArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(fArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final float[] x;
        private final FloatComparator comp;

        public ForkJoinQuickSortComp(float[] fArray, int n, int n2, FloatComparator floatComparator) {
            this.from = n;
            this.to = n2;
            this.x = fArray;
            this.comp = floatComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            float[] fArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                FloatArrays.quickSort(fArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = FloatArrays.access$000(fArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = FloatArrays.access$000(fArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = FloatArrays.access$000(fArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = FloatArrays.access$000(fArray, n6, n5, n7, this.comp);
            float f = fArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(fArray[n9], f)) <= 0) {
                    if (n == 0) {
                        FloatArrays.swap(fArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(fArray[n2], f)) >= 0) {
                    if (n == 0) {
                        FloatArrays.swap(fArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                FloatArrays.swap(fArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            FloatArrays.swap(fArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            FloatArrays.swap(fArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(fArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(fArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(fArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(fArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

