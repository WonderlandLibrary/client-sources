/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.booleans;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.booleans.BooleanComparator;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public final class BooleanArrays {
    public static final boolean[] EMPTY_ARRAY = new boolean[0];
    public static final boolean[] DEFAULT_EMPTY_ARRAY = new boolean[0];
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    public static final Hash.Strategy<boolean[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private BooleanArrays() {
    }

    public static boolean[] forceCapacity(boolean[] blArray, int n, int n2) {
        boolean[] blArray2 = new boolean[n];
        System.arraycopy(blArray, 0, blArray2, 0, n2);
        return blArray2;
    }

    public static boolean[] ensureCapacity(boolean[] blArray, int n) {
        return BooleanArrays.ensureCapacity(blArray, n, blArray.length);
    }

    public static boolean[] ensureCapacity(boolean[] blArray, int n, int n2) {
        return n > blArray.length ? BooleanArrays.forceCapacity(blArray, n, n2) : blArray;
    }

    public static boolean[] grow(boolean[] blArray, int n) {
        return BooleanArrays.grow(blArray, n, blArray.length);
    }

    public static boolean[] grow(boolean[] blArray, int n, int n2) {
        if (n > blArray.length) {
            int n3 = (int)Math.max(Math.min((long)blArray.length + (long)(blArray.length >> 1), 0x7FFFFFF7L), (long)n);
            boolean[] blArray2 = new boolean[n3];
            System.arraycopy(blArray, 0, blArray2, 0, n2);
            return blArray2;
        }
        return blArray;
    }

    public static boolean[] trim(boolean[] blArray, int n) {
        if (n >= blArray.length) {
            return blArray;
        }
        boolean[] blArray2 = n == 0 ? EMPTY_ARRAY : new boolean[n];
        System.arraycopy(blArray, 0, blArray2, 0, n);
        return blArray2;
    }

    public static boolean[] setLength(boolean[] blArray, int n) {
        if (n == blArray.length) {
            return blArray;
        }
        if (n < blArray.length) {
            return BooleanArrays.trim(blArray, n);
        }
        return BooleanArrays.ensureCapacity(blArray, n);
    }

    public static boolean[] copy(boolean[] blArray, int n, int n2) {
        BooleanArrays.ensureOffsetLength(blArray, n, n2);
        boolean[] blArray2 = n2 == 0 ? EMPTY_ARRAY : new boolean[n2];
        System.arraycopy(blArray, n, blArray2, 0, n2);
        return blArray2;
    }

    public static boolean[] copy(boolean[] blArray) {
        return (boolean[])blArray.clone();
    }

    @Deprecated
    public static void fill(boolean[] blArray, boolean bl) {
        int n = blArray.length;
        while (n-- != 0) {
            blArray[n] = bl;
        }
    }

    @Deprecated
    public static void fill(boolean[] blArray, int n, int n2, boolean bl) {
        BooleanArrays.ensureFromTo(blArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                blArray[n2] = bl;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                blArray[i] = bl;
            }
        }
    }

    @Deprecated
    public static boolean equals(boolean[] blArray, boolean[] blArray2) {
        int n = blArray.length;
        if (n != blArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (blArray[n] == blArray2[n]) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(boolean[] blArray, int n, int n2) {
        Arrays.ensureFromTo(blArray.length, n, n2);
    }

    public static void ensureOffsetLength(boolean[] blArray, int n, int n2) {
        Arrays.ensureOffsetLength(blArray.length, n, n2);
    }

    public static void ensureSameLength(boolean[] blArray, boolean[] blArray2) {
        if (blArray.length != blArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + blArray.length + " != " + blArray2.length);
        }
    }

    public static void swap(boolean[] blArray, int n, int n2) {
        boolean bl = blArray[n];
        blArray[n] = blArray[n2];
        blArray[n2] = bl;
    }

    public static void swap(boolean[] blArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            BooleanArrays.swap(blArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(boolean[] blArray, int n, int n2, int n3, BooleanComparator booleanComparator) {
        int n4 = booleanComparator.compare(blArray[n], blArray[n2]);
        int n5 = booleanComparator.compare(blArray[n], blArray[n3]);
        int n6 = booleanComparator.compare(blArray[n2], blArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(boolean[] blArray, int n, int n2, BooleanComparator booleanComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (booleanComparator.compare(blArray[n3], blArray[n4]) >= 0) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = blArray[i];
            blArray[i] = blArray[n4];
            blArray[n4] = n3;
        }
    }

    private static void insertionSort(boolean[] blArray, int n, int n2, BooleanComparator booleanComparator) {
        int n3 = n;
        while (++n3 < n2) {
            boolean bl = blArray[n3];
            int n4 = n3;
            boolean bl2 = blArray[n4 - 1];
            while (booleanComparator.compare(bl, bl2) < 0) {
                blArray[n4] = bl2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                bl2 = blArray[--n4 - 1];
            }
            blArray[n4] = bl;
        }
    }

    public static void quickSort(boolean[] blArray, int n, int n2, BooleanComparator booleanComparator) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            BooleanArrays.selectionSort(blArray, n, n2, booleanComparator);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = BooleanArrays.med3(blArray, n9, n9 + n6, n9 + 2 * n6, booleanComparator);
            n8 = BooleanArrays.med3(blArray, n8 - n6, n8, n8 + n6, booleanComparator);
            n10 = BooleanArrays.med3(blArray, n10 - 2 * n6, n10 - n6, n10, booleanComparator);
        }
        n8 = BooleanArrays.med3(blArray, n9, n8, n10, booleanComparator);
        n6 = blArray[n8];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = booleanComparator.compare(blArray[n11], n6 != 0)) <= 0) {
                if (n3 == 0) {
                    BooleanArrays.swap(blArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = booleanComparator.compare(blArray[n4], n6 != 0)) >= 0) {
                if (n3 == 0) {
                    BooleanArrays.swap(blArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            BooleanArrays.swap(blArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        BooleanArrays.swap(blArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        BooleanArrays.swap(blArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            BooleanArrays.quickSort(blArray, n, n + n3, booleanComparator);
        }
        if ((n3 = n12 - n4) > 1) {
            BooleanArrays.quickSort(blArray, n2 - n3, n2, booleanComparator);
        }
    }

    public static void quickSort(boolean[] blArray, BooleanComparator booleanComparator) {
        BooleanArrays.quickSort(blArray, 0, blArray.length, booleanComparator);
    }

    public static void parallelQuickSort(boolean[] blArray, int n, int n2, BooleanComparator booleanComparator) {
        if (n2 - n < 8192) {
            BooleanArrays.quickSort(blArray, n, n2, booleanComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(blArray, n, n2, booleanComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(boolean[] blArray, BooleanComparator booleanComparator) {
        BooleanArrays.parallelQuickSort(blArray, 0, blArray.length, booleanComparator);
    }

    private static int med3(boolean[] blArray, int n, int n2, int n3) {
        int n4 = Boolean.compare(blArray[n], blArray[n2]);
        int n5 = Boolean.compare(blArray[n], blArray[n3]);
        int n6 = Boolean.compare(blArray[n2], blArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(boolean[] blArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (blArray[n3] || !blArray[n4]) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = blArray[i];
            blArray[i] = blArray[n4];
            blArray[n4] = n3;
        }
    }

    private static void insertionSort(boolean[] blArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            boolean bl = blArray[n3];
            int n4 = n3;
            boolean bl2 = blArray[n4 - 1];
            while (!bl && bl2) {
                blArray[n4] = bl2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                bl2 = blArray[--n4 - 1];
            }
            blArray[n4] = bl;
        }
    }

    public static void quickSort(boolean[] blArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            BooleanArrays.selectionSort(blArray, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = BooleanArrays.med3(blArray, n9, n9 + n6, n9 + 2 * n6);
            n8 = BooleanArrays.med3(blArray, n8 - n6, n8, n8 + n6);
            n10 = BooleanArrays.med3(blArray, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = BooleanArrays.med3(blArray, n9, n8, n10);
        n6 = blArray[n8];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Boolean.compare(blArray[n11], n6 != 0)) <= 0) {
                if (n3 == 0) {
                    BooleanArrays.swap(blArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Boolean.compare(blArray[n4], n6 != 0)) >= 0) {
                if (n3 == 0) {
                    BooleanArrays.swap(blArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            BooleanArrays.swap(blArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        BooleanArrays.swap(blArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        BooleanArrays.swap(blArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            BooleanArrays.quickSort(blArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            BooleanArrays.quickSort(blArray, n2 - n3, n2);
        }
    }

    public static void quickSort(boolean[] blArray) {
        BooleanArrays.quickSort(blArray, 0, blArray.length);
    }

    public static void parallelQuickSort(boolean[] blArray, int n, int n2) {
        if (n2 - n < 8192) {
            BooleanArrays.quickSort(blArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(blArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(boolean[] blArray) {
        BooleanArrays.parallelQuickSort(blArray, 0, blArray.length);
    }

    private static int med3Indirect(int[] nArray, boolean[] blArray, int n, int n2, int n3) {
        boolean bl = blArray[nArray[n]];
        boolean bl2 = blArray[nArray[n2]];
        boolean bl3 = blArray[nArray[n3]];
        int n4 = Boolean.compare(bl, bl2);
        int n5 = Boolean.compare(bl, bl3);
        int n6 = Boolean.compare(bl2, bl3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, boolean[] blArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (!blArray[n4] && blArray[n6]) {
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

    public static void quickSortIndirect(int[] nArray, boolean[] blArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            BooleanArrays.insertionSortIndirect(nArray, blArray, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = BooleanArrays.med3Indirect(nArray, blArray, n9, n9 + n6, n9 + 2 * n6);
            n8 = BooleanArrays.med3Indirect(nArray, blArray, n8 - n6, n8, n8 + n6);
            n10 = BooleanArrays.med3Indirect(nArray, blArray, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = BooleanArrays.med3Indirect(nArray, blArray, n9, n8, n10);
        n6 = blArray[nArray[n8]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = Boolean.compare(blArray[nArray[n11]], n6 != 0)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = Boolean.compare(blArray[nArray[n4]], n6 != 0)) >= 0) {
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
            BooleanArrays.quickSortIndirect(nArray, blArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            BooleanArrays.quickSortIndirect(nArray, blArray, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, boolean[] blArray) {
        BooleanArrays.quickSortIndirect(nArray, blArray, 0, blArray.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, boolean[] blArray, int n, int n2) {
        if (n2 - n < 8192) {
            BooleanArrays.quickSortIndirect(nArray, blArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, blArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, boolean[] blArray) {
        BooleanArrays.parallelQuickSortIndirect(nArray, blArray, 0, blArray.length);
    }

    public static void stabilize(int[] nArray, boolean[] blArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (blArray[nArray[i]] == blArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, boolean[] blArray) {
        BooleanArrays.stabilize(nArray, blArray, 0, nArray.length);
    }

    private static int med3(boolean[] blArray, boolean[] blArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Boolean.compare(blArray[n], blArray[n2]);
        int n6 = n5 == 0 ? Boolean.compare(blArray2[n], blArray2[n2]) : n5;
        n5 = Boolean.compare(blArray[n], blArray[n3]);
        int n7 = n5 == 0 ? Boolean.compare(blArray2[n], blArray2[n3]) : n5;
        n5 = Boolean.compare(blArray[n2], blArray[n3]);
        int n8 = n4 = n5 == 0 ? Boolean.compare(blArray2[n2], blArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(boolean[] blArray, boolean[] blArray2, int n, int n2) {
        boolean bl = blArray[n];
        boolean bl2 = blArray2[n];
        blArray[n] = blArray[n2];
        blArray2[n] = blArray2[n2];
        blArray[n2] = bl;
        blArray2[n2] = bl2;
    }

    private static void swap(boolean[] blArray, boolean[] blArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            BooleanArrays.swap(blArray, blArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(boolean[] blArray, boolean[] blArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                int n5 = Boolean.compare(blArray[n3], blArray[n4]);
                if (n5 >= 0 && (n5 != 0 || blArray2[n3] || !blArray2[n4])) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = blArray[i];
            blArray[i] = blArray[n4];
            blArray[n4] = n3;
            n3 = blArray2[i];
            blArray2[i] = blArray2[n4];
            blArray2[n4] = n3;
        }
    }

    public static void quickSort(boolean[] blArray, boolean[] blArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = n2 - n;
        if (n7 < 16) {
            BooleanArrays.selectionSort(blArray, blArray2, n, n2);
            return;
        }
        int n8 = n + n7 / 2;
        int n9 = n;
        int n10 = n2 - 1;
        if (n7 > 128) {
            n6 = n7 / 8;
            n9 = BooleanArrays.med3(blArray, blArray2, n9, n9 + n6, n9 + 2 * n6);
            n8 = BooleanArrays.med3(blArray, blArray2, n8 - n6, n8, n8 + n6);
            n10 = BooleanArrays.med3(blArray, blArray2, n10 - 2 * n6, n10 - n6, n10);
        }
        n8 = BooleanArrays.med3(blArray, blArray2, n9, n8, n10);
        n6 = blArray[n8];
        boolean bl = blArray2[n8];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            int n13;
            if (n11 <= n4 && (n3 = (n13 = Boolean.compare(blArray[n11], n6 != 0)) == 0 ? Boolean.compare(blArray2[n11], bl) : n13) <= 0) {
                if (n3 == 0) {
                    BooleanArrays.swap(blArray, blArray2, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = (n13 = Boolean.compare(blArray[n4], n6 != 0)) == 0 ? Boolean.compare(blArray2[n4], bl) : n13) >= 0) {
                if (n3 == 0) {
                    BooleanArrays.swap(blArray, blArray2, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            BooleanArrays.swap(blArray, blArray2, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        BooleanArrays.swap(blArray, blArray2, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        BooleanArrays.swap(blArray, blArray2, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            BooleanArrays.quickSort(blArray, blArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            BooleanArrays.quickSort(blArray, blArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(boolean[] blArray, boolean[] blArray2) {
        BooleanArrays.ensureSameLength(blArray, blArray2);
        BooleanArrays.quickSort(blArray, blArray2, 0, blArray.length);
    }

    public static void parallelQuickSort(boolean[] blArray, boolean[] blArray2, int n, int n2) {
        if (n2 - n < 8192) {
            BooleanArrays.quickSort(blArray, blArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(blArray, blArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(boolean[] blArray, boolean[] blArray2) {
        BooleanArrays.ensureSameLength(blArray, blArray2);
        BooleanArrays.parallelQuickSort(blArray, blArray2, 0, blArray.length);
    }

    public static void mergeSort(boolean[] blArray, int n, int n2, boolean[] blArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            BooleanArrays.insertionSort(blArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        BooleanArrays.mergeSort(blArray2, n, n4, blArray);
        BooleanArrays.mergeSort(blArray2, n4, n2, blArray);
        if (!blArray2[n4 - 1] || blArray2[n4]) {
            System.arraycopy(blArray2, n, blArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            blArray[i] = n6 >= n2 || n5 < n4 && (!blArray2[n5] || blArray2[n6]) ? blArray2[n5++] : blArray2[n6++];
        }
    }

    public static void mergeSort(boolean[] blArray, int n, int n2) {
        BooleanArrays.mergeSort(blArray, n, n2, (boolean[])blArray.clone());
    }

    public static void mergeSort(boolean[] blArray) {
        BooleanArrays.mergeSort(blArray, 0, blArray.length);
    }

    public static void mergeSort(boolean[] blArray, int n, int n2, BooleanComparator booleanComparator, boolean[] blArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            BooleanArrays.insertionSort(blArray, n, n2, booleanComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        BooleanArrays.mergeSort(blArray2, n, n4, booleanComparator, blArray);
        BooleanArrays.mergeSort(blArray2, n4, n2, booleanComparator, blArray);
        if (booleanComparator.compare(blArray2[n4 - 1], blArray2[n4]) <= 0) {
            System.arraycopy(blArray2, n, blArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            blArray[i] = n6 >= n2 || n5 < n4 && booleanComparator.compare(blArray2[n5], blArray2[n6]) <= 0 ? blArray2[n5++] : blArray2[n6++];
        }
    }

    public static void mergeSort(boolean[] blArray, int n, int n2, BooleanComparator booleanComparator) {
        BooleanArrays.mergeSort(blArray, n, n2, booleanComparator, (boolean[])blArray.clone());
    }

    public static void mergeSort(boolean[] blArray, BooleanComparator booleanComparator) {
        BooleanArrays.mergeSort(blArray, 0, blArray.length, booleanComparator);
    }

    public static boolean[] shuffle(boolean[] blArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            boolean bl = blArray[n + n3];
            blArray[n + n3] = blArray[n + n4];
            blArray[n + n4] = bl;
        }
        return blArray;
    }

    public static boolean[] shuffle(boolean[] blArray, Random random2) {
        int n = blArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            boolean bl = blArray[n];
            blArray[n] = blArray[n2];
            blArray[n2] = bl;
        }
        return blArray;
    }

    public static boolean[] reverse(boolean[] blArray) {
        int n = blArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            boolean bl = blArray[n - n2 - 1];
            blArray[n - n2 - 1] = blArray[n2];
            blArray[n2] = bl;
        }
        return blArray;
    }

    public static boolean[] reverse(boolean[] blArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            boolean bl = blArray[n + n3 - n4 - 1];
            blArray[n + n3 - n4 - 1] = blArray[n + n4];
            blArray[n + n4] = bl;
        }
        return blArray;
    }

    static int access$000(boolean[] blArray, int n, int n2, int n3, BooleanComparator booleanComparator) {
        return BooleanArrays.med3(blArray, n, n2, n3, booleanComparator);
    }

    static int access$100(boolean[] blArray, int n, int n2, int n3) {
        return BooleanArrays.med3(blArray, n, n2, n3);
    }

    static int access$200(int[] nArray, boolean[] blArray, int n, int n2, int n3) {
        return BooleanArrays.med3Indirect(nArray, blArray, n, n2, n3);
    }

    static int access$300(boolean[] blArray, boolean[] blArray2, int n, int n2, int n3) {
        return BooleanArrays.med3(blArray, blArray2, n, n2, n3);
    }

    static void access$400(boolean[] blArray, boolean[] blArray2, int n, int n2) {
        BooleanArrays.swap(blArray, blArray2, n, n2);
    }

    static void access$500(boolean[] blArray, boolean[] blArray2, int n, int n2, int n3) {
        BooleanArrays.swap(blArray, blArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<boolean[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(boolean[] blArray) {
            return java.util.Arrays.hashCode(blArray);
        }

        @Override
        public boolean equals(boolean[] blArray, boolean[] blArray2) {
            return java.util.Arrays.equals(blArray, blArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((boolean[])object, (boolean[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((boolean[])object);
        }

        ArrayHashStrategy(1 var1_1) {
            this();
        }
    }

    protected static class ForkJoinQuickSort2
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final boolean[] x;
        private final boolean[] y;

        public ForkJoinQuickSort2(boolean[] blArray, boolean[] blArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = blArray;
            this.y = blArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            boolean[] blArray = this.x;
            boolean[] blArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                BooleanArrays.quickSort(blArray, blArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = BooleanArrays.access$300(blArray, blArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = BooleanArrays.access$300(blArray, blArray2, n5 - n8, n5, n5 + n8);
            n7 = BooleanArrays.access$300(blArray, blArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = BooleanArrays.access$300(blArray, blArray2, n6, n5, n7);
            boolean bl = blArray[n5];
            boolean bl2 = blArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = Boolean.compare(blArray[n9], bl)) == 0 ? Boolean.compare(blArray2[n9], bl2) : n11) <= 0) {
                    if (n == 0) {
                        BooleanArrays.access$400(blArray, blArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = Boolean.compare(blArray[n2], bl)) == 0 ? Boolean.compare(blArray2[n2], bl2) : n11) >= 0) {
                    if (n == 0) {
                        BooleanArrays.access$400(blArray, blArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                BooleanArrays.access$400(blArray, blArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            BooleanArrays.access$500(blArray, blArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            BooleanArrays.access$500(blArray, blArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(blArray, blArray2, this.from, this.from + n8), new ForkJoinQuickSort2(blArray, blArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(blArray, blArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(blArray, blArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final boolean[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, boolean[] blArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = blArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            boolean[] blArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                BooleanArrays.quickSortIndirect(this.perm, blArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = BooleanArrays.access$200(this.perm, blArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = BooleanArrays.access$200(this.perm, blArray, n5 - n8, n5, n5 + n8);
            n7 = BooleanArrays.access$200(this.perm, blArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = BooleanArrays.access$200(this.perm, blArray, n6, n5, n7);
            boolean bl = blArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Boolean.compare(blArray[this.perm[n9]], bl)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Boolean.compare(blArray[this.perm[n2]], bl)) >= 0) {
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
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, blArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, blArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, blArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, blArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final boolean[] x;

        public ForkJoinQuickSort(boolean[] blArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = blArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            boolean[] blArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                BooleanArrays.quickSort(blArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = BooleanArrays.access$100(blArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = BooleanArrays.access$100(blArray, n5 - n8, n5, n5 + n8);
            n7 = BooleanArrays.access$100(blArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = BooleanArrays.access$100(blArray, n6, n5, n7);
            boolean bl = blArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Boolean.compare(blArray[n9], bl)) <= 0) {
                    if (n == 0) {
                        BooleanArrays.swap(blArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Boolean.compare(blArray[n2], bl)) >= 0) {
                    if (n == 0) {
                        BooleanArrays.swap(blArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                BooleanArrays.swap(blArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            BooleanArrays.swap(blArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            BooleanArrays.swap(blArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(blArray, this.from, this.from + n8), new ForkJoinQuickSort(blArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(blArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(blArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final boolean[] x;
        private final BooleanComparator comp;

        public ForkJoinQuickSortComp(boolean[] blArray, int n, int n2, BooleanComparator booleanComparator) {
            this.from = n;
            this.to = n2;
            this.x = blArray;
            this.comp = booleanComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            boolean[] blArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                BooleanArrays.quickSort(blArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = BooleanArrays.access$000(blArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = BooleanArrays.access$000(blArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = BooleanArrays.access$000(blArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = BooleanArrays.access$000(blArray, n6, n5, n7, this.comp);
            boolean bl = blArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(blArray[n9], bl)) <= 0) {
                    if (n == 0) {
                        BooleanArrays.swap(blArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(blArray[n2], bl)) >= 0) {
                    if (n == 0) {
                        BooleanArrays.swap(blArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                BooleanArrays.swap(blArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            BooleanArrays.swap(blArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            BooleanArrays.swap(blArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(blArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(blArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(blArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(blArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

