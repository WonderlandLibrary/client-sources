/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.chars.CharComparator;
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

public final class CharArrays {
    public static final char[] EMPTY_ARRAY = new char[0];
    public static final char[] DEFAULT_EMPTY_ARRAY = new char[0];
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
    public static final Hash.Strategy<char[]> HASH_STRATEGY = new ArrayHashStrategy(null);

    private CharArrays() {
    }

    public static char[] forceCapacity(char[] cArray, int n, int n2) {
        char[] cArray2 = new char[n];
        System.arraycopy(cArray, 0, cArray2, 0, n2);
        return cArray2;
    }

    public static char[] ensureCapacity(char[] cArray, int n) {
        return CharArrays.ensureCapacity(cArray, n, cArray.length);
    }

    public static char[] ensureCapacity(char[] cArray, int n, int n2) {
        return n > cArray.length ? CharArrays.forceCapacity(cArray, n, n2) : cArray;
    }

    public static char[] grow(char[] cArray, int n) {
        return CharArrays.grow(cArray, n, cArray.length);
    }

    public static char[] grow(char[] cArray, int n, int n2) {
        if (n > cArray.length) {
            int n3 = (int)Math.max(Math.min((long)cArray.length + (long)(cArray.length >> 1), 0x7FFFFFF7L), (long)n);
            char[] cArray2 = new char[n3];
            System.arraycopy(cArray, 0, cArray2, 0, n2);
            return cArray2;
        }
        return cArray;
    }

    public static char[] trim(char[] cArray, int n) {
        if (n >= cArray.length) {
            return cArray;
        }
        char[] cArray2 = n == 0 ? EMPTY_ARRAY : new char[n];
        System.arraycopy(cArray, 0, cArray2, 0, n);
        return cArray2;
    }

    public static char[] setLength(char[] cArray, int n) {
        if (n == cArray.length) {
            return cArray;
        }
        if (n < cArray.length) {
            return CharArrays.trim(cArray, n);
        }
        return CharArrays.ensureCapacity(cArray, n);
    }

    public static char[] copy(char[] cArray, int n, int n2) {
        CharArrays.ensureOffsetLength(cArray, n, n2);
        char[] cArray2 = n2 == 0 ? EMPTY_ARRAY : new char[n2];
        System.arraycopy(cArray, n, cArray2, 0, n2);
        return cArray2;
    }

    public static char[] copy(char[] cArray) {
        return (char[])cArray.clone();
    }

    @Deprecated
    public static void fill(char[] cArray, char c) {
        int n = cArray.length;
        while (n-- != 0) {
            cArray[n] = c;
        }
    }

    @Deprecated
    public static void fill(char[] cArray, int n, int n2, char c) {
        CharArrays.ensureFromTo(cArray, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                cArray[n2] = c;
            }
        } else {
            for (int i = n; i < n2; ++i) {
                cArray[i] = c;
            }
        }
    }

    @Deprecated
    public static boolean equals(char[] cArray, char[] cArray2) {
        int n = cArray.length;
        if (n != cArray2.length) {
            return true;
        }
        while (n-- != 0) {
            if (cArray[n] == cArray2[n]) continue;
            return true;
        }
        return false;
    }

    public static void ensureFromTo(char[] cArray, int n, int n2) {
        Arrays.ensureFromTo(cArray.length, n, n2);
    }

    public static void ensureOffsetLength(char[] cArray, int n, int n2) {
        Arrays.ensureOffsetLength(cArray.length, n, n2);
    }

    public static void ensureSameLength(char[] cArray, char[] cArray2) {
        if (cArray.length != cArray2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + cArray.length + " != " + cArray2.length);
        }
    }

    public static void swap(char[] cArray, int n, int n2) {
        char c = cArray[n];
        cArray[n] = cArray[n2];
        cArray[n2] = c;
    }

    public static void swap(char[] cArray, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            CharArrays.swap(cArray, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static int med3(char[] cArray, int n, int n2, int n3, CharComparator charComparator) {
        int n4 = charComparator.compare(cArray[n], cArray[n2]);
        int n5 = charComparator.compare(cArray[n], cArray[n3]);
        int n6 = charComparator.compare(cArray[n2], cArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(char[] cArray, int n, int n2, CharComparator charComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (charComparator.compare(cArray[n3], cArray[n4]) >= 0) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = cArray[i];
            cArray[i] = cArray[n4];
            cArray[n4] = n3;
        }
    }

    private static void insertionSort(char[] cArray, int n, int n2, CharComparator charComparator) {
        int n3 = n;
        while (++n3 < n2) {
            char c = cArray[n3];
            int n4 = n3;
            char c2 = cArray[n4 - 1];
            while (charComparator.compare(c, c2) < 0) {
                cArray[n4] = c2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                c2 = cArray[--n4 - 1];
            }
            cArray[n4] = c;
        }
    }

    public static void quickSort(char[] cArray, int n, int n2, CharComparator charComparator) {
        int n3;
        int n4;
        int n5;
        char c;
        int n6 = n2 - n;
        if (n6 < 16) {
            CharArrays.selectionSort(cArray, n, n2, charComparator);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            c = n6 / 8;
            n8 = CharArrays.med3(cArray, n8, n8 + c, n8 + 2 * c, charComparator);
            n7 = CharArrays.med3(cArray, n7 - c, n7, n7 + c, charComparator);
            n9 = CharArrays.med3(cArray, n9 - 2 * c, n9 - c, n9, charComparator);
        }
        n7 = CharArrays.med3(cArray, n8, n7, n9, charComparator);
        c = cArray[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = charComparator.compare(cArray[n10], c)) <= 0) {
                if (n3 == 0) {
                    CharArrays.swap(cArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = charComparator.compare(cArray[n4], c)) >= 0) {
                if (n3 == 0) {
                    CharArrays.swap(cArray, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            CharArrays.swap(cArray, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        CharArrays.swap(cArray, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        CharArrays.swap(cArray, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            CharArrays.quickSort(cArray, n, n + n3, charComparator);
        }
        if ((n3 = n11 - n4) > 1) {
            CharArrays.quickSort(cArray, n2 - n3, n2, charComparator);
        }
    }

    public static void quickSort(char[] cArray, CharComparator charComparator) {
        CharArrays.quickSort(cArray, 0, cArray.length, charComparator);
    }

    public static void parallelQuickSort(char[] cArray, int n, int n2, CharComparator charComparator) {
        if (n2 - n < 8192) {
            CharArrays.quickSort(cArray, n, n2, charComparator);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortComp(cArray, n, n2, charComparator));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(char[] cArray, CharComparator charComparator) {
        CharArrays.parallelQuickSort(cArray, 0, cArray.length, charComparator);
    }

    private static int med3(char[] cArray, int n, int n2, int n3) {
        int n4 = Character.compare(cArray[n], cArray[n2]);
        int n5 = Character.compare(cArray[n], cArray[n3]);
        int n6 = Character.compare(cArray[n2], cArray[n3]);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void selectionSort(char[] cArray, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                if (cArray[n3] >= cArray[n4]) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = cArray[i];
            cArray[i] = cArray[n4];
            cArray[n4] = n3;
        }
    }

    private static void insertionSort(char[] cArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            char c = cArray[n3];
            int n4 = n3;
            char c2 = cArray[n4 - 1];
            while (c < c2) {
                cArray[n4] = c2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
                c2 = cArray[--n4 - 1];
            }
            cArray[n4] = c;
        }
    }

    public static void quickSort(char[] cArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        char c;
        int n6 = n2 - n;
        if (n6 < 16) {
            CharArrays.selectionSort(cArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            c = n6 / 8;
            n8 = CharArrays.med3(cArray, n8, n8 + c, n8 + 2 * c);
            n7 = CharArrays.med3(cArray, n7 - c, n7, n7 + c);
            n9 = CharArrays.med3(cArray, n9 - 2 * c, n9 - c, n9);
        }
        n7 = CharArrays.med3(cArray, n8, n7, n9);
        c = cArray[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = Character.compare(cArray[n10], c)) <= 0) {
                if (n3 == 0) {
                    CharArrays.swap(cArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = Character.compare(cArray[n4], c)) >= 0) {
                if (n3 == 0) {
                    CharArrays.swap(cArray, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            CharArrays.swap(cArray, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        CharArrays.swap(cArray, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        CharArrays.swap(cArray, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            CharArrays.quickSort(cArray, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            CharArrays.quickSort(cArray, n2 - n3, n2);
        }
    }

    public static void quickSort(char[] cArray) {
        CharArrays.quickSort(cArray, 0, cArray.length);
    }

    public static void parallelQuickSort(char[] cArray, int n, int n2) {
        if (n2 - n < 8192) {
            CharArrays.quickSort(cArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSort(cArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSort(char[] cArray) {
        CharArrays.parallelQuickSort(cArray, 0, cArray.length);
    }

    private static int med3Indirect(int[] nArray, char[] cArray, int n, int n2, int n3) {
        char c = cArray[nArray[n]];
        char c2 = cArray[nArray[n2]];
        char c3 = cArray[nArray[n3]];
        int n4 = Character.compare(c, c2);
        int n5 = Character.compare(c, c3);
        int n6 = Character.compare(c2, c3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    private static void insertionSortIndirect(int[] nArray, char[] cArray, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (cArray[n4] < cArray[n6]) {
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

    public static void quickSortIndirect(int[] nArray, char[] cArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        char c;
        int n6 = n2 - n;
        if (n6 < 16) {
            CharArrays.insertionSortIndirect(nArray, cArray, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            c = n6 / 8;
            n8 = CharArrays.med3Indirect(nArray, cArray, n8, n8 + c, n8 + 2 * c);
            n7 = CharArrays.med3Indirect(nArray, cArray, n7 - c, n7, n7 + c);
            n9 = CharArrays.med3Indirect(nArray, cArray, n9 - 2 * c, n9 - c, n9);
        }
        n7 = CharArrays.med3Indirect(nArray, cArray, n8, n7, n9);
        c = cArray[nArray[n7]];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = Character.compare(cArray[nArray[n10]], c)) <= 0) {
                if (n3 == 0) {
                    IntArrays.swap(nArray, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = Character.compare(cArray[nArray[n4]], c)) >= 0) {
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
            CharArrays.quickSortIndirect(nArray, cArray, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            CharArrays.quickSortIndirect(nArray, cArray, n2 - n3, n2);
        }
    }

    public static void quickSortIndirect(int[] nArray, char[] cArray) {
        CharArrays.quickSortIndirect(nArray, cArray, 0, cArray.length);
    }

    public static void parallelQuickSortIndirect(int[] nArray, char[] cArray, int n, int n2) {
        if (n2 - n < 8192) {
            CharArrays.quickSortIndirect(nArray, cArray, n, n2);
        } else {
            ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
            forkJoinPool.invoke(new ForkJoinQuickSortIndirect(nArray, cArray, n, n2));
            forkJoinPool.shutdown();
        }
    }

    public static void parallelQuickSortIndirect(int[] nArray, char[] cArray) {
        CharArrays.parallelQuickSortIndirect(nArray, cArray, 0, cArray.length);
    }

    public static void stabilize(int[] nArray, char[] cArray, int n, int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (cArray[nArray[i]] == cArray[nArray[n3]]) continue;
            if (i - n3 > 1) {
                IntArrays.parallelQuickSort(nArray, n3, i);
            }
            n3 = i;
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(nArray, n3, n2);
        }
    }

    public static void stabilize(int[] nArray, char[] cArray) {
        CharArrays.stabilize(nArray, cArray, 0, nArray.length);
    }

    private static int med3(char[] cArray, char[] cArray2, int n, int n2, int n3) {
        int n4;
        int n5 = Character.compare(cArray[n], cArray[n2]);
        int n6 = n5 == 0 ? Character.compare(cArray2[n], cArray2[n2]) : n5;
        n5 = Character.compare(cArray[n], cArray[n3]);
        int n7 = n5 == 0 ? Character.compare(cArray2[n], cArray2[n3]) : n5;
        n5 = Character.compare(cArray[n2], cArray[n3]);
        int n8 = n4 = n5 == 0 ? Character.compare(cArray2[n2], cArray2[n3]) : n5;
        return n6 < 0 ? (n4 < 0 ? n2 : (n7 < 0 ? n3 : n)) : (n4 > 0 ? n2 : (n7 > 0 ? n3 : n));
    }

    private static void swap(char[] cArray, char[] cArray2, int n, int n2) {
        char c = cArray[n];
        char c2 = cArray2[n];
        cArray[n] = cArray[n2];
        cArray2[n] = cArray2[n2];
        cArray[n2] = c;
        cArray2[n2] = c2;
    }

    private static void swap(char[] cArray, char[] cArray2, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            CharArrays.swap(cArray, cArray2, n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    private static void selectionSort(char[] cArray, char[] cArray2, int n, int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3;
            int n4 = i;
            for (n3 = i + 1; n3 < n2; ++n3) {
                int n5 = Character.compare(cArray[n3], cArray[n4]);
                if (n5 >= 0 && (n5 != 0 || cArray2[n3] >= cArray2[n4])) continue;
                n4 = n3;
            }
            if (n4 == i) continue;
            n3 = cArray[i];
            cArray[i] = cArray[n4];
            cArray[n4] = n3;
            n3 = cArray2[i];
            cArray2[i] = cArray2[n4];
            cArray2[n4] = n3;
        }
    }

    public static void quickSort(char[] cArray, char[] cArray2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        char c;
        int n6 = n2 - n;
        if (n6 < 16) {
            CharArrays.selectionSort(cArray, cArray2, n, n2);
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            c = n6 / 8;
            n8 = CharArrays.med3(cArray, cArray2, n8, n8 + c, n8 + 2 * c);
            n7 = CharArrays.med3(cArray, cArray2, n7 - c, n7, n7 + c);
            n9 = CharArrays.med3(cArray, cArray2, n9 - 2 * c, n9 - c, n9);
        }
        n7 = CharArrays.med3(cArray, cArray2, n8, n7, n9);
        c = cArray[n7];
        char c2 = cArray2[n7];
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            int n12;
            if (n10 <= n4 && (n3 = (n12 = Character.compare(cArray[n10], c)) == 0 ? Character.compare(cArray2[n10], c2) : n12) <= 0) {
                if (n3 == 0) {
                    CharArrays.swap(cArray, cArray2, n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = (n12 = Character.compare(cArray[n4], c)) == 0 ? Character.compare(cArray2[n4], c2) : n12) >= 0) {
                if (n3 == 0) {
                    CharArrays.swap(cArray, cArray2, n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            CharArrays.swap(cArray, cArray2, n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        CharArrays.swap(cArray, cArray2, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        CharArrays.swap(cArray, cArray2, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            CharArrays.quickSort(cArray, cArray2, n, n + n3);
        }
        if ((n3 = n11 - n4) > 1) {
            CharArrays.quickSort(cArray, cArray2, n2 - n3, n2);
        }
    }

    public static void quickSort(char[] cArray, char[] cArray2) {
        CharArrays.ensureSameLength(cArray, cArray2);
        CharArrays.quickSort(cArray, cArray2, 0, cArray.length);
    }

    public static void parallelQuickSort(char[] cArray, char[] cArray2, int n, int n2) {
        if (n2 - n < 8192) {
            CharArrays.quickSort(cArray, cArray2, n, n2);
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinQuickSort2(cArray, cArray2, n, n2));
        forkJoinPool.shutdown();
    }

    public static void parallelQuickSort(char[] cArray, char[] cArray2) {
        CharArrays.ensureSameLength(cArray, cArray2);
        CharArrays.parallelQuickSort(cArray, cArray2, 0, cArray.length);
    }

    public static void mergeSort(char[] cArray, int n, int n2, char[] cArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            CharArrays.insertionSort(cArray, n, n2);
            return;
        }
        int n4 = n + n2 >>> 1;
        CharArrays.mergeSort(cArray2, n, n4, cArray);
        CharArrays.mergeSort(cArray2, n4, n2, cArray);
        if (cArray2[n4 - 1] <= cArray2[n4]) {
            System.arraycopy(cArray2, n, cArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            cArray[i] = n6 >= n2 || n5 < n4 && cArray2[n5] <= cArray2[n6] ? cArray2[n5++] : cArray2[n6++];
        }
    }

    public static void mergeSort(char[] cArray, int n, int n2) {
        CharArrays.mergeSort(cArray, n, n2, (char[])cArray.clone());
    }

    public static void mergeSort(char[] cArray) {
        CharArrays.mergeSort(cArray, 0, cArray.length);
    }

    public static void mergeSort(char[] cArray, int n, int n2, CharComparator charComparator, char[] cArray2) {
        int n3 = n2 - n;
        if (n3 < 16) {
            CharArrays.insertionSort(cArray, n, n2, charComparator);
            return;
        }
        int n4 = n + n2 >>> 1;
        CharArrays.mergeSort(cArray2, n, n4, charComparator, cArray);
        CharArrays.mergeSort(cArray2, n4, n2, charComparator, cArray);
        if (charComparator.compare(cArray2[n4 - 1], cArray2[n4]) <= 0) {
            System.arraycopy(cArray2, n, cArray, n, n3);
            return;
        }
        int n5 = n;
        int n6 = n4;
        for (int i = n; i < n2; ++i) {
            cArray[i] = n6 >= n2 || n5 < n4 && charComparator.compare(cArray2[n5], cArray2[n6]) <= 0 ? cArray2[n5++] : cArray2[n6++];
        }
    }

    public static void mergeSort(char[] cArray, int n, int n2, CharComparator charComparator) {
        CharArrays.mergeSort(cArray, n, n2, charComparator, (char[])cArray.clone());
    }

    public static void mergeSort(char[] cArray, CharComparator charComparator) {
        CharArrays.mergeSort(cArray, 0, cArray.length, charComparator);
    }

    public static int binarySearch(char[] cArray, int n, int n2, char c) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            char c2 = cArray[n3];
            if (c2 < c) {
                n = n3 + 1;
                continue;
            }
            if (c2 > c) {
                n2 = n3 - 1;
                continue;
            }
            return n3;
        }
        return -(n + 1);
    }

    public static int binarySearch(char[] cArray, char c) {
        return CharArrays.binarySearch(cArray, 0, cArray.length, c);
    }

    public static int binarySearch(char[] cArray, int n, int n2, char c, CharComparator charComparator) {
        --n2;
        while (n <= n2) {
            int n3 = n + n2 >>> 1;
            char c2 = cArray[n3];
            int n4 = charComparator.compare(c2, c);
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

    public static int binarySearch(char[] cArray, char c, CharComparator charComparator) {
        return CharArrays.binarySearch(cArray, 0, cArray.length, c, charComparator);
    }

    public static void radixSort(char[] cArray) {
        CharArrays.radixSort(cArray, 0, cArray.length);
    }

    public static void radixSort(char[] cArray, int n, int n2) {
        if (n2 - n < 1024) {
            CharArrays.quickSort(cArray, n, n2);
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
            boolean bl2 = false;
            int n9 = (1 - n8 % 2) * 8;
            int n10 = n6 + n7;
            while (n10-- != n6) {
                int n11 = cArray[n10] >>> n9 & 0xFF ^ 0;
                nArray4[n11] = nArray4[n11] + 1;
            }
            n10 = -1;
            int n12 = n6;
            for (n5 = 0; n5 < 256; ++n5) {
                if (nArray4[n5] != 0) {
                    n10 = n5;
                }
                nArray5[n5] = n12 += nArray4[n5];
            }
            n5 = n6 + n7 - nArray4[n10];
            int n13 = -1;
            for (n12 = n6; n12 <= n5; n12 += nArray4[n13]) {
                char c = cArray[n12];
                n13 = c >>> n9 & 0xFF ^ 0;
                if (n12 < n5) {
                    while (true) {
                        int n14 = n13;
                        int n15 = nArray5[n14] - 1;
                        nArray5[n14] = n15;
                        int n16 = n15;
                        if (n15 <= n12) break;
                        char c2 = c;
                        c = cArray[n16];
                        cArray[n16] = c2;
                        n13 = c >>> n9 & 0xFF ^ 0;
                    }
                    cArray[n12] = c;
                }
                if (n8 < 1 && nArray4[n13] > 1) {
                    if (nArray4[n13] < 1024) {
                        CharArrays.quickSort(cArray, n12, n12 + nArray4[n13]);
                    } else {
                        nArray[n4] = n12;
                        nArray2[n4] = nArray4[n13];
                        nArray3[n4++] = n8 + 1;
                    }
                }
                nArray4[n13] = 0;
            }
        }
    }

    public static void parallelRadixSort(char[] cArray, int n, int n2) {
        if (n2 - n < 1024) {
            CharArrays.quickSort(cArray, n, n2);
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
            executorCompletionService.submit(() -> CharArrays.lambda$parallelRadixSort$0(atomicInteger, n3, linkedBlockingQueue, cArray));
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

    public static void parallelRadixSort(char[] cArray) {
        CharArrays.parallelRadixSort(cArray, 0, cArray.length);
    }

    public static void radixSortIndirect(int[] nArray, char[] cArray, boolean bl) {
        CharArrays.radixSortIndirect(nArray, cArray, 0, nArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, char[] cArray, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            CharArrays.insertionSortIndirect(nArray, cArray, n, n2);
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
            boolean bl3 = false;
            int n10 = (1 - n9 % 2) * 8;
            int n11 = n7 + n8;
            while (n11-- != n7) {
                int n12 = cArray[nArray[n11]] >>> n10 & 0xFF ^ 0;
                nArray6[n12] = nArray6[n12] + 1;
            }
            n11 = -1;
            int n13 = n6 = bl ? 0 : n7;
            for (n5 = 0; n5 < 256; ++n5) {
                if (nArray6[n5] != 0) {
                    n11 = n5;
                }
                nArray7[n5] = n6 += nArray6[n5];
            }
            if (bl) {
                n5 = n7 + n8;
                while (n5-- != n7) {
                    int n14 = cArray[nArray[n5]] >>> n10 & 0xFF ^ 0;
                    int n15 = nArray7[n14] - 1;
                    nArray7[n14] = n15;
                    nArray2[n15] = nArray[n5];
                }
                System.arraycopy(nArray2, 0, nArray, n7, n8);
                n6 = n7;
                for (n5 = 0; n5 <= n11; ++n5) {
                    if (n9 < 1 && nArray6[n5] > 1) {
                        if (nArray6[n5] < 1024) {
                            CharArrays.insertionSortIndirect(nArray, cArray, n6, n6 + nArray6[n5]);
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
            n5 = n7 + n8 - nArray6[n11];
            int n16 = -1;
            for (n6 = n7; n6 <= n5; n6 += nArray6[n16]) {
                int n17 = nArray[n6];
                n16 = cArray[n17] >>> n10 & 0xFF ^ 0;
                if (n6 < n5) {
                    while (true) {
                        int n18 = n16;
                        int n19 = nArray7[n18] - 1;
                        nArray7[n18] = n19;
                        int n20 = n19;
                        if (n19 <= n6) break;
                        int n21 = n17;
                        n17 = nArray[n20];
                        nArray[n20] = n21;
                        n16 = cArray[n17] >>> n10 & 0xFF ^ 0;
                    }
                    nArray[n6] = n17;
                }
                if (n9 < 1 && nArray6[n16] > 1) {
                    if (nArray6[n16] < 1024) {
                        CharArrays.insertionSortIndirect(nArray, cArray, n6, n6 + nArray6[n16]);
                    } else {
                        nArray3[n4] = n6;
                        nArray4[n4] = nArray6[n16];
                        nArray5[n4++] = n9 + 1;
                    }
                }
                nArray6[n16] = 0;
            }
        }
    }

    public static void parallelRadixSortIndirect(int[] nArray, char[] cArray, int n, int n2, boolean bl) {
        if (n2 - n < 1024) {
            CharArrays.radixSortIndirect(nArray, cArray, n, n2, bl);
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
            executorCompletionService.submit(() -> CharArrays.lambda$parallelRadixSortIndirect$1(atomicInteger, n3, linkedBlockingQueue, cArray, nArray, bl, nArray2));
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

    public static void parallelRadixSortIndirect(int[] nArray, char[] cArray, boolean bl) {
        CharArrays.parallelRadixSortIndirect(nArray, cArray, 0, cArray.length, bl);
    }

    public static void radixSort(char[] cArray, char[] cArray2) {
        CharArrays.ensureSameLength(cArray, cArray2);
        CharArrays.radixSort(cArray, cArray2, 0, cArray.length);
    }

    public static void radixSort(char[] cArray, char[] cArray2, int n, int n2) {
        if (n2 - n < 1024) {
            CharArrays.selectionSort(cArray, cArray2, n, n2);
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
            boolean bl = false;
            char[] cArray3 = n10 < 2 ? cArray : cArray2;
            int n11 = (1 - n10 % 2) * 8;
            int n12 = n8 + n9;
            while (n12-- != n8) {
                int n13 = cArray3[n12] >>> n11 & 0xFF ^ 0;
                nArray4[n13] = nArray4[n13] + 1;
            }
            n12 = -1;
            int n14 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray4[n7] != 0) {
                    n12 = n7;
                }
                nArray5[n7] = n14 += nArray4[n7];
            }
            n7 = n8 + n9 - nArray4[n12];
            int n15 = -1;
            for (n14 = n8; n14 <= n7; n14 += nArray4[n15]) {
                char c = cArray[n14];
                char c2 = cArray2[n14];
                n15 = cArray3[n14] >>> n11 & 0xFF ^ 0;
                if (n14 < n7) {
                    while (true) {
                        int n16 = n15;
                        int n17 = nArray5[n16] - 1;
                        nArray5[n16] = n17;
                        int n18 = n17;
                        if (n17 <= n14) break;
                        n15 = cArray3[n18] >>> n11 & 0xFF ^ 0;
                        char c3 = c;
                        c = cArray[n18];
                        cArray[n18] = c3;
                        c3 = c2;
                        c2 = cArray2[n18];
                        cArray2[n18] = c3;
                    }
                    cArray[n14] = c;
                    cArray2[n14] = c2;
                }
                if (n10 < 3 && nArray4[n15] > 1) {
                    if (nArray4[n15] < 1024) {
                        CharArrays.selectionSort(cArray, cArray2, n14, n14 + nArray4[n15]);
                    } else {
                        nArray[n6] = n14;
                        nArray2[n6] = nArray4[n15];
                        nArray3[n6++] = n10 + 1;
                    }
                }
                nArray4[n15] = 0;
            }
        }
    }

    public static void parallelRadixSort(char[] cArray, char[] cArray2, int n, int n2) {
        if (n2 - n < 1024) {
            CharArrays.quickSort(cArray, cArray2, n, n2);
            return;
        }
        int n3 = 2;
        if (cArray.length != cArray2.length) {
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
            executorCompletionService.submit(() -> CharArrays.lambda$parallelRadixSort$2(atomicInteger, n5, linkedBlockingQueue, cArray, cArray2));
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

    public static void parallelRadixSort(char[] cArray, char[] cArray2) {
        CharArrays.ensureSameLength(cArray, cArray2);
        CharArrays.parallelRadixSort(cArray, cArray2, 0, cArray.length);
    }

    private static void insertionSortIndirect(int[] nArray, char[] cArray, char[] cArray2, int n, int n2) {
        int n3 = n;
        while (++n3 < n2) {
            int n4 = nArray[n3];
            int n5 = n3;
            int n6 = nArray[n5 - 1];
            while (cArray[n4] < cArray[n6] || cArray[n4] == cArray[n6] && cArray2[n4] < cArray2[n6]) {
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

    public static void radixSortIndirect(int[] nArray, char[] cArray, char[] cArray2, boolean bl) {
        CharArrays.ensureSameLength(cArray, cArray2);
        CharArrays.radixSortIndirect(nArray, cArray, cArray2, 0, cArray.length, bl);
    }

    public static void radixSortIndirect(int[] nArray, char[] cArray, char[] cArray2, int n, int n2, boolean bl) {
        int[] nArray2;
        if (n2 - n < 1024) {
            CharArrays.insertionSortIndirect(nArray, cArray, cArray2, n, n2);
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
            boolean bl2 = false;
            char[] cArray3 = n11 < 2 ? cArray : cArray2;
            int n12 = (1 - n11 % 2) * 8;
            int n13 = n9 + n10;
            while (n13-- != n9) {
                int n14 = cArray3[nArray[n13]] >>> n12 & 0xFF ^ 0;
                nArray6[n14] = nArray6[n14] + 1;
            }
            n13 = -1;
            int n15 = n8 = bl ? 0 : n9;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray6[n7] != 0) {
                    n13 = n7;
                }
                nArray7[n7] = n8 += nArray6[n7];
            }
            if (bl) {
                n7 = n9 + n10;
                while (n7-- != n9) {
                    int n16 = cArray3[nArray[n7]] >>> n12 & 0xFF ^ 0;
                    int n17 = nArray7[n16] - 1;
                    nArray7[n16] = n17;
                    nArray2[n17] = nArray[n7];
                }
                System.arraycopy(nArray2, 0, nArray, n9, n10);
                n8 = n9;
                for (n7 = 0; n7 < 256; ++n7) {
                    if (n11 < 3 && nArray6[n7] > 1) {
                        if (nArray6[n7] < 1024) {
                            CharArrays.insertionSortIndirect(nArray, cArray, cArray2, n8, n8 + nArray6[n7]);
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
            n7 = n9 + n10 - nArray6[n13];
            int n18 = -1;
            for (n8 = n9; n8 <= n7; n8 += nArray6[n18]) {
                int n19 = nArray[n8];
                n18 = cArray3[n19] >>> n12 & 0xFF ^ 0;
                if (n8 < n7) {
                    while (true) {
                        int n20 = n18;
                        int n21 = nArray7[n20] - 1;
                        nArray7[n20] = n21;
                        int n22 = n21;
                        if (n21 <= n8) break;
                        int n23 = n19;
                        n19 = nArray[n22];
                        nArray[n22] = n23;
                        n18 = cArray3[n19] >>> n12 & 0xFF ^ 0;
                    }
                    nArray[n8] = n19;
                }
                if (n11 < 3 && nArray6[n18] > 1) {
                    if (nArray6[n18] < 1024) {
                        CharArrays.insertionSortIndirect(nArray, cArray, cArray2, n8, n8 + nArray6[n18]);
                    } else {
                        nArray3[n6] = n8;
                        nArray4[n6] = nArray6[n18];
                        nArray5[n6++] = n11 + 1;
                    }
                }
                nArray6[n18] = 0;
            }
        }
    }

    private static void selectionSort(char[][] cArray, int n, int n2, int n3) {
        int n4 = cArray.length;
        int n5 = n3 / 2;
        for (int i = n; i < n2 - 1; ++i) {
            int n6;
            int n7;
            int n8 = i;
            block1: for (n7 = i + 1; n7 < n2; ++n7) {
                for (n6 = n5; n6 < n4; ++n6) {
                    if (cArray[n6][n7] < cArray[n6][n8]) {
                        n8 = n7;
                        continue block1;
                    }
                    if (cArray[n6][n7] > cArray[n6][n8]) continue block1;
                }
            }
            if (n8 == i) continue;
            n7 = n4;
            while (n7-- != 0) {
                n6 = cArray[n7][i];
                cArray[n7][i] = cArray[n7][n8];
                cArray[n7][n8] = n6;
            }
        }
    }

    public static void radixSort(char[][] cArray) {
        CharArrays.radixSort(cArray, 0, cArray[0].length);
    }

    public static void radixSort(char[][] cArray, int n, int n2) {
        if (n2 - n < 1024) {
            CharArrays.selectionSort(cArray, n, n2, 0);
            return;
        }
        int n3 = cArray.length;
        int n4 = 2 * n3 - 1;
        int n5 = n3;
        int n6 = cArray[0].length;
        while (n5-- != 0) {
            if (cArray[n5].length == n6) continue;
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
        char[] cArray2 = new char[n3];
        while (n6 > 0) {
            int n7;
            int n8 = nArray[--n6];
            int n9 = nArray2[n6];
            int n10 = nArray3[n6];
            boolean bl = false;
            char[] cArray3 = cArray[n10 / 2];
            int n11 = (1 - n10 % 2) * 8;
            int n12 = n8 + n9;
            while (n12-- != n8) {
                int n13 = cArray3[n12] >>> n11 & 0xFF ^ 0;
                nArray4[n13] = nArray4[n13] + 1;
            }
            n12 = -1;
            int n14 = n8;
            for (n7 = 0; n7 < 256; ++n7) {
                if (nArray4[n7] != 0) {
                    n12 = n7;
                }
                nArray5[n7] = n14 += nArray4[n7];
            }
            n7 = n8 + n9 - nArray4[n12];
            int n15 = -1;
            for (n14 = n8; n14 <= n7; n14 += nArray4[n15]) {
                int n16 = n3;
                while (n16-- != 0) {
                    cArray2[n16] = cArray[n16][n14];
                }
                n15 = cArray3[n14] >>> n11 & 0xFF ^ 0;
                if (n14 < n7) {
                    block6: while (true) {
                        int n17 = n15;
                        int n18 = nArray5[n17] - 1;
                        nArray5[n17] = n18;
                        int n19 = n18;
                        if (n18 <= n14) break;
                        n15 = cArray3[n19] >>> n11 & 0xFF ^ 0;
                        n16 = n3;
                        while (true) {
                            if (n16-- == 0) continue block6;
                            char c = cArray2[n16];
                            cArray2[n16] = cArray[n16][n19];
                            cArray[n16][n19] = c;
                        }
                        break;
                    }
                    n16 = n3;
                    while (n16-- != 0) {
                        cArray[n16][n14] = cArray2[n16];
                    }
                }
                if (n10 < n4 && nArray4[n15] > 1) {
                    if (nArray4[n15] < 1024) {
                        CharArrays.selectionSort(cArray, n14, n14 + nArray4[n15], n10 + 1);
                    } else {
                        nArray[n6] = n14;
                        nArray2[n6] = nArray4[n15];
                        nArray3[n6++] = n10 + 1;
                    }
                }
                nArray4[n15] = 0;
            }
        }
    }

    public static char[] shuffle(char[] cArray, int n, int n2, Random random2) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            int n4 = random2.nextInt(n3 + 1);
            char c = cArray[n + n3];
            cArray[n + n3] = cArray[n + n4];
            cArray[n + n4] = c;
        }
        return cArray;
    }

    public static char[] shuffle(char[] cArray, Random random2) {
        int n = cArray.length;
        while (n-- != 0) {
            int n2 = random2.nextInt(n + 1);
            char c = cArray[n];
            cArray[n] = cArray[n2];
            cArray[n2] = c;
        }
        return cArray;
    }

    public static char[] reverse(char[] cArray) {
        int n = cArray.length;
        int n2 = n / 2;
        while (n2-- != 0) {
            char c = cArray[n - n2 - 1];
            cArray[n - n2 - 1] = cArray[n2];
            cArray[n2] = c;
        }
        return cArray;
    }

    public static char[] reverse(char[] cArray, int n, int n2) {
        int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            char c = cArray[n + n3 - n4 - 1];
            cArray[n + n3 - n4 - 1] = cArray[n + n4];
            cArray[n + n4] = c;
        }
        return cArray;
    }

    private static Void lambda$parallelRadixSort$2(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, char[] cArray, char[] cArray2) throws Exception {
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
            char[] cArray3 = n6 < 2 ? cArray : cArray2;
            int n8 = (1 - n6 % 2) * 8;
            int n9 = n4 + n5;
            while (n9-- != n4) {
                int n10 = cArray3[n9] >>> n8 & 0xFF ^ n7;
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
                char c = cArray[n11];
                char c2 = cArray2[n11];
                n12 = cArray3[n11] >>> n8 & 0xFF ^ n7;
                if (n11 < n2) {
                    while (true) {
                        int n13 = n12;
                        int n14 = nArray2[n13] - 1;
                        nArray2[n13] = n14;
                        int n15 = n14;
                        if (n14 <= n11) break;
                        n12 = cArray3[n15] >>> n8 & 0xFF ^ n7;
                        char c3 = c;
                        char c4 = c2;
                        c = cArray[n15];
                        c2 = cArray2[n15];
                        cArray[n15] = c3;
                        cArray2[n15] = c4;
                    }
                    cArray[n11] = c;
                    cArray2[n11] = c2;
                }
                if (n6 < 3 && nArray[n12] > 1) {
                    if (nArray[n12] < 1024) {
                        CharArrays.quickSort(cArray, cArray2, n11, n11 + nArray[n12]);
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

    private static Void lambda$parallelRadixSortIndirect$1(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, char[] cArray, int[] nArray, boolean bl, int[] nArray2) throws Exception {
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
            boolean bl2 = false;
            int n7 = (1 - n6 % 2) * 8;
            int n8 = n4 + n5;
            while (n8-- != n4) {
                int n9 = cArray[nArray[n8]] >>> n7 & 0xFF ^ 0;
                nArray3[n9] = nArray3[n9] + 1;
            }
            n8 = -1;
            int n10 = n4;
            for (n2 = 0; n2 < 256; ++n2) {
                if (nArray3[n2] != 0) {
                    n8 = n2;
                }
                nArray4[n2] = n10 += nArray3[n2];
            }
            if (bl) {
                n2 = n4 + n5;
                while (n2-- != n4) {
                    int n11 = cArray[nArray[n2]] >>> n7 & 0xFF ^ 0;
                    int n12 = nArray4[n11] - 1;
                    nArray4[n11] = n12;
                    nArray2[n12] = nArray[n2];
                }
                System.arraycopy(nArray2, n4, nArray, n4, n5);
                n10 = n4;
                for (n2 = 0; n2 <= n8; ++n2) {
                    if (n6 < 1 && nArray3[n2] > 1) {
                        if (nArray3[n2] < 1024) {
                            CharArrays.radixSortIndirect(nArray, cArray, n10, n10 + nArray3[n2], bl);
                        } else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(n10, nArray3[n2], n6 + 1));
                        }
                    }
                    n10 += nArray3[n2];
                }
                java.util.Arrays.fill(nArray3, 0);
            } else {
                n2 = n4 + n5 - nArray3[n8];
                int n13 = -1;
                for (n10 = n4; n10 <= n2; n10 += nArray3[n13]) {
                    int n14 = nArray[n10];
                    n13 = cArray[n14] >>> n7 & 0xFF ^ 0;
                    if (n10 < n2) {
                        while (true) {
                            int n15 = n13;
                            int n16 = nArray4[n15] - 1;
                            nArray4[n15] = n16;
                            int n17 = n16;
                            if (n16 <= n10) break;
                            int n18 = n14;
                            n14 = nArray[n17];
                            nArray[n17] = n18;
                            n13 = cArray[n14] >>> n7 & 0xFF ^ 0;
                        }
                        nArray[n10] = n14;
                    }
                    if (n6 < 1 && nArray3[n13] > 1) {
                        if (nArray3[n13] < 1024) {
                            CharArrays.radixSortIndirect(nArray, cArray, n10, n10 + nArray3[n13], bl);
                        } else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(n10, nArray3[n13], n6 + 1));
                        }
                    }
                    nArray3[n13] = 0;
                }
            }
            atomicInteger.decrementAndGet();
        }
    }

    private static Void lambda$parallelRadixSort$0(AtomicInteger atomicInteger, int n, LinkedBlockingQueue linkedBlockingQueue, char[] cArray) throws Exception {
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
            boolean bl = false;
            int n7 = (1 - n6 % 2) * 8;
            int n8 = n4 + n5;
            while (n8-- != n4) {
                int n9 = cArray[n8] >>> n7 & 0xFF ^ 0;
                nArray[n9] = nArray[n9] + 1;
            }
            n8 = -1;
            int n10 = n4;
            for (n2 = 0; n2 < 256; ++n2) {
                if (nArray[n2] != 0) {
                    n8 = n2;
                }
                nArray2[n2] = n10 += nArray[n2];
            }
            n2 = n4 + n5 - nArray[n8];
            int n11 = -1;
            for (n10 = n4; n10 <= n2; n10 += nArray[n11]) {
                char c = cArray[n10];
                n11 = c >>> n7 & 0xFF ^ 0;
                if (n10 < n2) {
                    while (true) {
                        int n12 = n11;
                        int n13 = nArray2[n12] - 1;
                        nArray2[n12] = n13;
                        int n14 = n13;
                        if (n13 <= n10) break;
                        char c2 = c;
                        c = cArray[n14];
                        cArray[n14] = c2;
                        n11 = c >>> n7 & 0xFF ^ 0;
                    }
                    cArray[n10] = c;
                }
                if (n6 < 1 && nArray[n11] > 1) {
                    if (nArray[n11] < 1024) {
                        CharArrays.quickSort(cArray, n10, n10 + nArray[n11]);
                    } else {
                        atomicInteger.incrementAndGet();
                        linkedBlockingQueue.add(new Segment(n10, nArray[n11], n6 + 1));
                    }
                }
                nArray[n11] = 0;
            }
            atomicInteger.decrementAndGet();
        }
    }

    static int access$000(char[] cArray, int n, int n2, int n3, CharComparator charComparator) {
        return CharArrays.med3(cArray, n, n2, n3, charComparator);
    }

    static int access$100(char[] cArray, int n, int n2, int n3) {
        return CharArrays.med3(cArray, n, n2, n3);
    }

    static int access$200(int[] nArray, char[] cArray, int n, int n2, int n3) {
        return CharArrays.med3Indirect(nArray, cArray, n, n2, n3);
    }

    static int access$300(char[] cArray, char[] cArray2, int n, int n2, int n3) {
        return CharArrays.med3(cArray, cArray2, n, n2, n3);
    }

    static void access$400(char[] cArray, char[] cArray2, int n, int n2) {
        CharArrays.swap(cArray, cArray2, n, n2);
    }

    static void access$500(char[] cArray, char[] cArray2, int n, int n2, int n3) {
        CharArrays.swap(cArray, cArray2, n, n2, n3);
    }

    private static final class ArrayHashStrategy
    implements Hash.Strategy<char[]>,
    Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        private ArrayHashStrategy() {
        }

        @Override
        public int hashCode(char[] cArray) {
            return java.util.Arrays.hashCode(cArray);
        }

        @Override
        public boolean equals(char[] cArray, char[] cArray2) {
            return java.util.Arrays.equals(cArray, cArray2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((char[])object, (char[])object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((char[])object);
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
        private final char[] x;
        private final char[] y;

        public ForkJoinQuickSort2(char[] cArray, char[] cArray2, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = cArray;
            this.y = cArray2;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            char[] cArray = this.x;
            char[] cArray2 = this.y;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                CharArrays.quickSort(cArray, cArray2, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = CharArrays.access$300(cArray, cArray2, n6, n6 + n8, n6 + 2 * n8);
            n5 = CharArrays.access$300(cArray, cArray2, n5 - n8, n5, n5 + n8);
            n7 = CharArrays.access$300(cArray, cArray2, n7 - 2 * n8, n7 - n8, n7);
            n5 = CharArrays.access$300(cArray, cArray2, n6, n5, n7);
            char c = cArray[n5];
            char c2 = cArray2[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                int n11;
                if (n9 <= n2 && (n = (n11 = Character.compare(cArray[n9], c)) == 0 ? Character.compare(cArray2[n9], c2) : n11) <= 0) {
                    if (n == 0) {
                        CharArrays.access$400(cArray, cArray2, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = (n11 = Character.compare(cArray[n2], c)) == 0 ? Character.compare(cArray2[n2], c2) : n11) >= 0) {
                    if (n == 0) {
                        CharArrays.access$400(cArray, cArray2, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                CharArrays.access$400(cArray, cArray2, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            CharArrays.access$500(cArray, cArray2, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            CharArrays.access$500(cArray, cArray2, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(cArray, cArray2, this.from, this.from + n8), new ForkJoinQuickSort2(cArray, cArray2, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(cArray, cArray2, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort2.invokeAll(new ForkJoinQuickSort2(cArray, cArray2, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortIndirect
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final char[] x;

        public ForkJoinQuickSortIndirect(int[] nArray, char[] cArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = cArray;
            this.perm = nArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            char[] cArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                CharArrays.quickSortIndirect(this.perm, cArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = CharArrays.access$200(this.perm, cArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = CharArrays.access$200(this.perm, cArray, n5 - n8, n5, n5 + n8);
            n7 = CharArrays.access$200(this.perm, cArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = CharArrays.access$200(this.perm, cArray, n6, n5, n7);
            char c = cArray[this.perm[n5]];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Character.compare(cArray[this.perm[n9]], c)) <= 0) {
                    if (n == 0) {
                        IntArrays.swap(this.perm, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Character.compare(cArray[this.perm[n2]], c)) >= 0) {
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
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, cArray, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, cArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, cArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSortIndirect.invokeAll(new ForkJoinQuickSortIndirect(this.perm, cArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final char[] x;

        public ForkJoinQuickSort(char[] cArray, int n, int n2) {
            this.from = n;
            this.to = n2;
            this.x = cArray;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            char[] cArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                CharArrays.quickSort(cArray, this.from, this.to);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = CharArrays.access$100(cArray, n6, n6 + n8, n6 + 2 * n8);
            n5 = CharArrays.access$100(cArray, n5 - n8, n5, n5 + n8);
            n7 = CharArrays.access$100(cArray, n7 - 2 * n8, n7 - n8, n7);
            n5 = CharArrays.access$100(cArray, n6, n5, n7);
            char c = cArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = Character.compare(cArray[n9], c)) <= 0) {
                    if (n == 0) {
                        CharArrays.swap(cArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = Character.compare(cArray[n2], c)) >= 0) {
                    if (n == 0) {
                        CharArrays.swap(cArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                CharArrays.swap(cArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            CharArrays.swap(cArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            CharArrays.swap(cArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(cArray, this.from, this.from + n8), new ForkJoinQuickSort(cArray, this.to - n, this.to));
            } else if (n8 > 1) {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(cArray, this.from, this.from + n8));
            } else {
                ForkJoinQuickSort.invokeAll(new ForkJoinQuickSort(cArray, this.to - n, this.to));
            }
        }
    }

    protected static class ForkJoinQuickSortComp
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final char[] x;
        private final CharComparator comp;

        public ForkJoinQuickSortComp(char[] cArray, int n, int n2, CharComparator charComparator) {
            this.from = n;
            this.to = n2;
            this.x = cArray;
            this.comp = charComparator;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            char[] cArray = this.x;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                CharArrays.quickSort(cArray, this.from, this.to, this.comp);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = CharArrays.access$000(cArray, n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = CharArrays.access$000(cArray, n5 - n8, n5, n5 + n8, this.comp);
            n7 = CharArrays.access$000(cArray, n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = CharArrays.access$000(cArray, n6, n5, n7, this.comp);
            char c = cArray[n5];
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(cArray[n9], c)) <= 0) {
                    if (n == 0) {
                        CharArrays.swap(cArray, n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(cArray[n2], c)) >= 0) {
                    if (n == 0) {
                        CharArrays.swap(cArray, n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                CharArrays.swap(cArray, n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            CharArrays.swap(cArray, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            CharArrays.swap(cArray, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(cArray, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(cArray, this.to - n, this.to, this.comp));
            } else if (n8 > 1) {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(cArray, this.from, this.from + n8, this.comp));
            } else {
                ForkJoinQuickSortComp.invokeAll(new ForkJoinQuickSortComp(cArray, this.to - n, this.to, this.comp));
            }
        }
    }
}

