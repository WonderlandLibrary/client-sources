/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public final class ObjectArrays {
    public static final Object[] EMPTY_ARRAY = new Object[0];
    public static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    public static final Hash.Strategy HASH_STRATEGY = new ArrayHashStrategy(null);

    private ObjectArrays() {
    }

    private static <K> K[] newArray(K[] KArray, int n) {
        Class<?> clazz = KArray.getClass();
        if (clazz == Object[].class) {
            return n == 0 ? EMPTY_ARRAY : new Object[n];
        }
        return (Object[])Array.newInstance(clazz.getComponentType(), n);
    }

    public static <K> K[] forceCapacity(K[] KArray, int n, int n2) {
        K[] KArray2 = ObjectArrays.newArray(KArray, n);
        System.arraycopy(KArray, 0, KArray2, 0, n2);
        return KArray2;
    }

    public static <K> K[] ensureCapacity(K[] KArray, int n) {
        return ObjectArrays.ensureCapacity(KArray, n, KArray.length);
    }

    public static <K> K[] ensureCapacity(K[] KArray, int n, int n2) {
        return n > KArray.length ? ObjectArrays.forceCapacity(KArray, n, n2) : KArray;
    }

    public static <K> K[] grow(K[] KArray, int n) {
        return ObjectArrays.grow(KArray, n, KArray.length);
    }

    public static <K> K[] grow(K[] KArray, int n, int n2) {
        if (n > KArray.length) {
            int n3 = (int)Math.max(Math.min((long)KArray.length + (long)(KArray.length >> 1), 0x7FFFFFF7L), (long)n);
            K[] KArray2 = ObjectArrays.newArray(KArray, n3);
            System.arraycopy(KArray, 0, KArray2, 0, n2);
            return KArray2;
        }
        return KArray;
    }

    public static <K> K[] trim(K[] KArray, int n) {
        if (n >= KArray.length) {
            return KArray;
        }
        K[] KArray2 = ObjectArrays.newArray(KArray, n);
        System.arraycopy(KArray, 0, KArray2, 0, n);
        return KArray2;
    }

    public static <K> K[] setLength(K[] KArray, int n) {
        if (n == KArray.length) {
            return KArray;
        }
        if (n < KArray.length) {
            return ObjectArrays.trim(KArray, n);
        }
        return ObjectArrays.ensureCapacity(KArray, n);
    }

    public static <K> K[] copy(K[] KArray, int n, int n2) {
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        K[] KArray2 = ObjectArrays.newArray(KArray, n2);
        System.arraycopy(KArray, n, KArray2, 0, n2);
        return KArray2;
    }

    public static <K> K[] copy(K[] KArray) {
        return (Object[])KArray.clone();
    }

    @Deprecated
    public static <K> void fill(K[] KArray, K k) {
        int n = KArray.length;
        while (n-- != 0) {
            KArray[n] = k;
        }
    }

    @Deprecated
    public static <K> void fill(K[] KArray, int n, int n2, K k) {
        ObjectArrays.ensureFromTo(KArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                KArray[n2] = k;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                KArray[i] = k;
            }
        }
    }

    @Deprecated
    public static <K> boolean equals(K[] KArray, K[] KArray2) {
        int n = KArray.length;
        if (n != KArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (Objects.equals(KArray[n], KArray2[n])) continue;
            return true;
        }
        return false;
    }

    public static <K> void ensureFromTo(K[] KArray, int n, int n2) {
        Arrays.ensureFromTo(KArray.length, n, n2);
    }

    public static <K> void ensureOffsetLength(K[] KArray, int n, int n2) {
        Arrays.ensureOffsetLength(KArray.length, n, n2);
    }

    public static <K> void ensureSameLength(K[] KArray, K[] KArray2) {
        if (KArray.length != KArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + KArray.length + " != " + KArray2.length);
        }
    }

    public static <K> void swap(K[] KArray, int n, int n2) {
        K k = KArray[n];
        KArray[n] = KArray[n2];
        KArray[n2] = k;
    }

    public static <K> void swap(K[] KArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            ObjectArrays.swap(KArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static <K> int med3(K[] KArray, int n, int n2, int n3, Comparator<K> comparator) {
        int n4 = comparator.compare(KArray[n], KArray[n2]);
        int n5 = comparator.compare(KArray[n], KArray[n3]);
        int n6 = comparator.compare(KArray[n2], KArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static <K> void selectionSort(K[] KArray, int n, int n2, Comparator<K> comparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (comparator.compare(KArray[j], KArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            K k = KArray[i];
            KArray[i] = KArray[n3];
            KArray[n3] = k;
        }
    }

    private static <K> void insertionSort(K[] KArray, int n, int n2, Comparator<K> comparator) {
        int n3 = n;
        while (++n3 < n2) {
            K k = KArray[n3];
            int n4 = n3;
            K k2 = KArray[n4 - 1];
            while (comparator.compare(k, k2) < 0) {
                KArray[n4] = k2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                k2 = KArray[--n4 - 1];
            }
            KArray[n4] = k;
        }
    }

    public static <K> void quickSort(K[] KArray, int n, int n2, Comparator<K> comparator) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            ObjectArrays.selectionSort(KArray, n, n2, comparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = ObjectArrays.med3(KArray, n8, n8 + n10, n8 + 2 * n10, comparator);
            n7 = ObjectArrays.med3(KArray, n7 - n10, n7, n7 + n10, comparator);
            n9 = ObjectArrays.med3(KArray, n9 - 2 * n10, n9 - n10, n9, comparator);
        }
        n7 = ObjectArrays.med3(KArray, n8, n7, n9, comparator);
        K k = KArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = comparator.compare(KArray[n11], k)) <= 0) {
                if (n3 == 0) {
                    ObjectArrays.swap(KArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = comparator.compare(KArray[n4], k)) >= 0) {
                if (n3 == 0) {
                    ObjectArrays.swap(KArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            ObjectArrays.swap(KArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        ObjectArrays.swap(KArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        ObjectArrays.swap(KArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            ObjectArrays.quickSort(KArray, n, n + n3, comparator);
        }
        if ((n3 = n12 - n4) > 1) {
            ObjectArrays.quickSort(KArray, n2 - n3, n2, comparator);
        }
    }

    public static <K> void quickSort(K[] KArray, Comparator<K> comparator) {
        ObjectArrays.quickSort(KArray, 0, KArray.length, comparator);
    }

    public static <K> void parallelQuickSort(K[] KArray, int n, int n2, Comparator<K> comparator) {
        if (n2 - n < 8192) {
            ObjectArrays.quickSort(KArray, n, n2, comparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp<K>(KArray, n, n2, comparator));
            forkJoinPool.shutdown();
        }
    }

    public static <K> void parallelQuickSort(K[] KArray, Comparator<K> comparator) {
        ObjectArrays.parallelQuickSort(KArray, 0, KArray.length, comparator);
    }

    private static <K> int med3(K[] KArray, int n, int n2, int n3) {
        int n4 = ((Comparable)KArray[n]).compareTo(KArray[n2]);
        int n5 = ((Comparable)KArray[n]).compareTo(KArray[n3]);
        int n6 = ((Comparable)KArray[n2]).compareTo(KArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static <K> void selectionSort(K[] KArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (((Comparable)KArray[j]).compareTo(KArray[n3]) >= 0) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            K k = KArray[i];
            KArray[i] = KArray[n3];
            KArray[n3] = k;
        }
    }

    private static <K> void insertionSort(K[] KArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            K k = KArray[n3];
            int n4 = n3;
            K k2 = KArray[n4 - 1];
            while (((Comparable)k).compareTo(k2) < 0) {
                KArray[n4] = k2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                k2 = KArray[--n4 - 1];
            }
            KArray[n4] = k;
        }
    }

    public static <K> void quickSort(K[] KArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            ObjectArrays.selectionSort(KArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = ObjectArrays.med3(KArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = ObjectArrays.med3(KArray, n7 - n10, n7, n7 + n10);
            n9 = ObjectArrays.med3(KArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = ObjectArrays.med3(KArray, n8, n7, n9);
        K k = KArray[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = ((Comparable)KArray[n11]).compareTo(k)) <= 0) {
                if (n3 == 0) {
                    ObjectArrays.swap(KArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = ((Comparable)KArray[n4]).compareTo(k)) >= 0) {
                if (n3 == 0) {
                    ObjectArrays.swap(KArray, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            ObjectArrays.swap(KArray, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        ObjectArrays.swap(KArray, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        ObjectArrays.swap(KArray, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            ObjectArrays.quickSort(KArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            ObjectArrays.quickSort(KArray, n2 - n3, n2);
        }
    }

    public static <K> void quickSort(K[] KArray) {
        ObjectArrays.quickSort(KArray, 0, KArray.length);
    }

    public static <K> void parallelQuickSort(K[] KArray, int n, int n2) {
        if (n2 - n < 8192) {
            ObjectArrays.quickSort(KArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort<K>(KArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static <K> void parallelQuickSort(K[] KArray) {
        ObjectArrays.parallelQuickSort(KArray, 0, KArray.length);
    }

    private static <K> int med3Indirect(int[] nArray, K[] KArray, int n, int n2, int n3) {
        K k = KArray[nArray[n]];
        K k2 = KArray[nArray[n2]];
        K k3 = KArray[nArray[n3]];
        int n4 = ((Comparable)k).compareTo(k2);
        int n5 = ((Comparable)k).compareTo(k3);
        int n6 = ((Comparable)k2).compareTo(k3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static <K> void insertionSortIndirect(int[] nArray, K[] KArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (((Comparable)KArray[n4]).compareTo(KArray[n6]) < 0) {
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

    public static <K> void quickSortIndirect(int[] nArray, K[] KArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            ObjectArrays.insertionSortIndirect(nArray, KArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = ObjectArrays.med3Indirect(nArray, KArray, n8, n8 + n10, n8 + 2 * n10);
            n7 = ObjectArrays.med3Indirect(nArray, KArray, n7 - n10, n7, n7 + n10);
            n9 = ObjectArrays.med3Indirect(nArray, KArray, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = ObjectArrays.med3Indirect(nArray, KArray, n8, n7, n9);
        K k = KArray[nArray[n7]];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            if (n11 <= n4 && (n3 = ((Comparable)KArray[nArray[n11]]).compareTo(k)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = ((Comparable)KArray[nArray[n4]]).compareTo(k)) >= 0) {
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
            ObjectArrays.quickSortIndirect(nArray, KArray, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            ObjectArrays.quickSortIndirect(nArray, KArray, n2 - n3, n2);
        }
    }

    public static <K> void quickSortIndirect(int[] nArray, K[] KArray) {
        ObjectArrays.quickSortIndirect(nArray, KArray, 0, KArray.length);
    }

    public static <K> void parallelQuickSortIndirect(int[] nArray, K[] KArray, int n, int n2) {
        if (n2 - n < 8192) {
            ObjectArrays.quickSortIndirect(nArray, KArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect<K>(nArray, KArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static <K> void parallelQuickSortIndirect(int[] nArray, K[] KArray) {
        ObjectArrays.parallelQuickSortIndirect(nArray, KArray, 0, KArray.length);
    }

    public static <K> void stabilize(int[] nArray, K[] KArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (KArray[nArray[i]] == KArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static <K> void stabilize(int[] nArray, K[] KArray) {
        ObjectArrays.stabilize(nArray, KArray, 0, nArray.length);
    }

    private static <K> int med3(K[] KArray, K[] KArray2, int n, int n2, int n3) {
        int n4;
        int n5 = ((Comparable)KArray[n]).compareTo(KArray[n2]);
        int n6 = n5 == 0 ? ((Comparable)KArray2[n]).compareTo(KArray2[n2]) : n5;
        n5 = ((Comparable)KArray[n]).compareTo(KArray[n3]);
        int n7 = n5 == 0 ? ((Comparable)KArray2[n]).compareTo(KArray2[n3]) : n5;
        n5 = ((Comparable)KArray[n2]).compareTo(KArray[n3]);
        int n8 = n4 = n5 == 0 ? ((Comparable)KArray2[n2]).compareTo(KArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static <K> void swap(K[] KArray, K[] KArray2, int n, int n2) {
        K k = KArray[n];
        K k2 = KArray2[n];
        KArray[n] = KArray[n2];
        KArray2[n] = KArray2[n2];
        KArray[n2] = k;
        KArray2[n2] = k2;
    }

    private static <K> void swap(K[] KArray, K[] KArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            ObjectArrays.swap(KArray, KArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static <K> void selectionSort(K[] KArray, K[] KArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                int n4 = ((Comparable)KArray[j]).compareTo(KArray[n3]);
                if (n4 >= 0 && (n4 != 0 || ((Comparable)KArray2[j]).compareTo(KArray2[n3]) >= 0)) continue;
                n3 = j;
            }
            if (n3 == i) continue;
            K k = KArray[i];
            KArray[i] = KArray[n3];
            KArray[n3] = k;
            k = KArray2[i];
            KArray2[i] = KArray2[n3];
            KArray2[n3] = k;
        }
    }

    public static <K> void quickSort(K[] KArray, K[] KArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            ObjectArrays.selectionSort(KArray, KArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            int n10 = n6 / 8;
            n8 = ObjectArrays.med3(KArray, KArray2, n8, n8 + n10, n8 + 2 * n10);
            n7 = ObjectArrays.med3(KArray, KArray2, n7 - n10, n7, n7 + n10);
            n9 = ObjectArrays.med3(KArray, KArray2, n9 - 2 * n10, n9 - n10, n9);
        }
        n7 = ObjectArrays.med3(KArray, KArray2, n8, n7, n9);
        K k = KArray[n7];
        K k2 = KArray2[n7];
        int n11 = n5 = n;
        int n12 = n4 = n2 - 1;
        while (true) {
            int n13;
            if (n11 <= n4 && (n3 = (n13 = ((Comparable)KArray[n11]).compareTo(k)) == 0 ? ((Comparable)KArray2[n11]).compareTo(k2) : n13) <= 0) {
                if (n3 == 0) {
                    ObjectArrays.swap(KArray, KArray2, n5++, n11);
                }
                ++n11;
                continue;
            }
            while (n4 >= n11 && (n3 = (n13 = ((Comparable)KArray[n4]).compareTo(k)) == 0 ? ((Comparable)KArray2[n4]).compareTo(k2) : n13) >= 0) {
                if (n3 == 0) {
                    ObjectArrays.swap(KArray, KArray2, n4, n12--);
                }
                --n4;
            }
            if (n11 > n4) break;
            ObjectArrays.swap(KArray, KArray2, n11++, n4--);
        }
        n3 = Math.min(n5 - n, n11 - n5);
        ObjectArrays.swap(KArray, KArray2, n, n11 - n3, n3);
        n3 = Math.min(n12 - n4, n2 - n12 - 1);
        ObjectArrays.swap(KArray, KArray2, n11, n2 - n3, n3);
        n3 = n11 - n5;
        if (n3 > 1) {
            ObjectArrays.quickSort(KArray, KArray2, n, n + n3);
        }
        if ((n3 = n12 - n4) > 1) {
            ObjectArrays.quickSort(KArray, KArray2, n2 - n3, n2);
        }
    }

    public static <K> void quickSort(K[] KArray, K[] KArray2) {
        ObjectArrays.ensureSameLength(KArray, KArray2);
        ObjectArrays.quickSort(KArray, KArray2, 0, KArray.length);
    }

    public static <K> void parallelQuickSort(K[] KArray, K[] KArray2, int n, int n2) {
        if (n2 - n < 8192) {
            ObjectArrays.quickSort(KArray, KArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2<K>(KArray, KArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static <K> void parallelQuickSort(K[] KArray, K[] KArray2) {
        ObjectArrays.ensureSameLength(KArray, KArray2);
        ObjectArrays.parallelQuickSort(KArray, KArray2, 0, KArray.length);
    }

    public static <K> void mergeSort(K[] KArray, int n, int n2, K[] KArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            ObjectArrays.insertionSort(KArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        ObjectArrays.mergeSort(KArray2, n, n4, KArray);
        ObjectArrays.mergeSort(KArray2, n4, n2, KArray);
        if (((Comparable)KArray2[n4 - 1]).compareTo(KArray2[n4]) <= 0) {
            System.arraycopy(KArray2, n, KArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            KArray[i] = n6 >= n2 || n5 < n4 && ((Comparable)KArray2[n5]).compareTo(KArray2[n6]) <= 0 ? KArray2[n5++] : KArray2[n6++];
        }
    }

    public static <K> void mergeSort(K[] KArray, int n, int n2) {
        ObjectArrays.mergeSort(KArray, n, n2, (Object[])KArray.clone());
    }

    public static <K> void mergeSort(K[] KArray) {
        ObjectArrays.mergeSort(KArray, 0, KArray.length);
    }

    public static <K> void mergeSort(K[] KArray, int n, int n2, Comparator<K> comparator, K[] KArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            ObjectArrays.insertionSort(KArray, n, n2, comparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        ObjectArrays.mergeSort(KArray2, n, n4, comparator, KArray);
        ObjectArrays.mergeSort(KArray2, n4, n2, comparator, KArray);
        if (comparator.compare(KArray2[n4 - 1], KArray2[n4]) <= 0) {
            System.arraycopy(KArray2, n, KArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            KArray[i] = n6 >= n2 || n5 < n4 && comparator.compare(KArray2[n5], KArray2[n6]) <= 0 ? KArray2[n5++] : KArray2[n6++];
        }
    }

    public static <K> void mergeSort(K[] KArray, int n, int n2, Comparator<K> comparator) {
        ObjectArrays.mergeSort(KArray, n, n2, comparator, (Object[])KArray.clone());
    }

    public static <K> void mergeSort(K[] KArray, Comparator<K> comparator) {
        ObjectArrays.mergeSort(KArray, 0, KArray.length, comparator);
    }

    public static <K> int binarySearch(K[] KArray, int n, int n2, K k) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            K k2 = KArray[n3];
            int n4 = ((Comparable)k2).compareTo(k);
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

    public static <K> int binarySearch(K[] KArray, K k) {
        return ObjectArrays.binarySearch(KArray, 0, KArray.length, k);
    }

    public static <K> int binarySearch(K[] KArray, int n, int n2, K k, Comparator<K> comparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            K k2 = KArray[n3];
            int n4 = comparator.compare(k2, k);
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

    public static <K> int binarySearch(K[] KArray, K k, Comparator<K> comparator) {
        return ObjectArrays.binarySearch(KArray, 0, KArray.length, k, comparator);
    }

    public static <K> K[] shuffle(K[] KArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            K k = KArray[n + n3];
            KArray[n + n3] = KArray[n + n4];
            KArray[n + n4] = k;
        }
        return KArray;
    }

    public static <K> K[] shuffle(K[] KArray, Random random2) {
        int n = KArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            K k = KArray[n];
            KArray[n] = KArray[n2];
            KArray[n2] = k;
        }
        return KArray;
    }

    public static <K> K[] reverse(K[] KArray) {
        int n = KArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            K k = KArray[n - n2 - 1];
            KArray[n - n2 - 1] = KArray[n2];
            KArray[n2] = k;
        }
        return KArray;
    }

    public static <K> K[] reverse(K[] KArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            K k = KArray[n + n3 - n4 - 1];
            KArray[n + n3 - n4 - 1] = KArray[n + n4];
            KArray[n + n4] = k;
        }
        return KArray;
    }

    static int access$000(Object[] objectArray, int n, int n2, int n3, Comparator comparator) {
        return ObjectArrays.med3(objectArray, n, n2, n3, comparator);
    }

    static int access$100(Object[] objectArray, int n, int n2, int n3) {
        return ObjectArrays.med3(objectArray, n, n2, n3);
    }

    static int access$200(int[] nArray, Object[] objectArray, int n, int n2, int n3) {
        return ObjectArrays.med3Indirect(nArray, objectArray, n, n2, n3);
    }

    static int access$300(Object[] objectArray, Object[] objectArray2, int n, int n2, int n3) {
        return ObjectArrays.med3(objectArray, objectArray2, n, n2, n3);
    }

    static void access$400(Object[] objectArray, Object[] objectArray2, int n, int n2) {
        ObjectArrays.swap(objectArray, objectArray2, n, n2);
    }

    static void access$500(Object[] objectArray, Object[] objectArray2, int n, int n2, int n3) {
        ObjectArrays.swap(objectArray, objectArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy<K>
    implements Hash.Strategy<K[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(K[] KArray) {
            return java.util.Arrays.hashCode(KArray);
        }

        @Override
        public boolean equals(K[] KArray, K[] KArray2) {
            return java.util.Arrays.equals(KArray, KArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((Object[])object, (Object[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((Object[])object);
        }

        ArrayHashStrategy(1 var1_1) {
            this();
        }
    }

    protected static class ForkJoinQuickSort2<K>
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final K[] x;
        private final K[] y;

        public ForkJoinQuickSort2(K[] KArray, K[] KArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = KArray;
            this.y = KArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            Object[] objectArray = this.x;
            Object[] objectArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ObjectArrays.quickSort(objectArray, objectArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ObjectArrays.access$300(objectArray, objectArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = ObjectArrays.access$300(objectArray, objectArray2, n5 - n8, n5, n5 + n8);
            n7 = ObjectArrays.access$300(objectArray, objectArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = ObjectArrays.access$300(objectArray, objectArray2, n6, n5, n7);
            Object object = objectArray[n5];
            Object object2 = objectArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = ((Comparable)objectArray[n9]).compareTo(object)) == 0 ? ((Comparable)objectArray2[n9]).compareTo(object2) : n11) <= 0) {
                    if (n == 0) {
                        ObjectArrays.access$400(objectArray, objectArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = ((Comparable)objectArray[n2]).compareTo(object)) == 0 ? ((Comparable)objectArray2[n2]).compareTo(object2) : n11) >= 0) {
                    if (n == 0) {
                        ObjectArrays.access$400(objectArray, objectArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ObjectArrays.access$400(objectArray, objectArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ObjectArrays.access$500(objectArray, objectArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ObjectArrays.access$500(objectArray, objectArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2<Object>(objectArray, objectArray2, this.from, this.from + n8), new ForkJoinQuickSort2<Object>(objectArray, objectArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2<Object>(objectArray, objectArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2<Object>(objectArray, objectArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect<K>
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final K[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, K[] KArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = KArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            Object[] objectArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ObjectArrays.quickSortIndirect(this.perm, objectArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ObjectArrays.access$200(this.perm, objectArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = ObjectArrays.access$200(this.perm, objectArray, n5 - n8, n5, n5 + n8);
            n7 = ObjectArrays.access$200(this.perm, objectArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = ObjectArrays.access$200(this.perm, objectArray, n6, n5, n7);
            Object object = objectArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = ((Comparable)objectArray[this.perm[n9]]).compareTo(object)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = ((Comparable)objectArray[this.perm[n2]]).compareTo(object)) >= 0) {
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
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect<Object>(this.perm, objectArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect<Object>(this.perm, objectArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect<Object>(this.perm, objectArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect<Object>(this.perm, objectArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort<K>
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final K[] x;

        public ForkJoinQuickSort(K[] KArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = KArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            Object[] objectArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ObjectArrays.quickSort(objectArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ObjectArrays.access$100(objectArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = ObjectArrays.access$100(objectArray, n5 - n8, n5, n5 + n8);
            n7 = ObjectArrays.access$100(objectArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = ObjectArrays.access$100(objectArray, n6, n5, n7);
            Object object = objectArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = ((Comparable)objectArray[n9]).compareTo(object)) <= 0) {
                    if (n == 0) {
                        ObjectArrays.swap(objectArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = ((Comparable)objectArray[n2]).compareTo(object)) >= 0) {
                    if (n == 0) {
                        ObjectArrays.swap(objectArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ObjectArrays.swap(objectArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ObjectArrays.swap(objectArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ObjectArrays.swap(objectArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort<Object>(objectArray, this.from, this.from + n8), new ForkJoinQuickSort<Object>(objectArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort<Object>(objectArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort<Object>(objectArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp<K>
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final K[] x;
        private final Comparator<K> comp;

        public ForkJoinQuickSortComp(K[] KArray, int n, int n2, Comparator<K> comparator) {
            this.from = n;
            this.to = n2;
            this.x = KArray;
            this.comp = comparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            Object[] objectArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                ObjectArrays.quickSort(objectArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = ObjectArrays.access$000(objectArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = ObjectArrays.access$000(objectArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = ObjectArrays.access$000(objectArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = ObjectArrays.access$000(objectArray, n6, n5, n7, this.comp);
            Object object = objectArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(objectArray[n9], object)) <= 0) {
                    if (n == 0) {
                        ObjectArrays.swap(objectArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(objectArray[n2], object)) >= 0) {
                    if (n == 0) {
                        ObjectArrays.swap(objectArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                ObjectArrays.swap(objectArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            ObjectArrays.swap(objectArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            ObjectArrays.swap(objectArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp<Object>(objectArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp<Object>(objectArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp<Object>(objectArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp<Object>(objectArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

