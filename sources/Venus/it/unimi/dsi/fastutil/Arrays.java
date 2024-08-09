/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil;

import it.unimi.dsi.fastutil.Swapper;
import it.unimi.dsi.fastutil.ints.IntComparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Arrays {
    public static final int MAX_ARRAY_SIZE = 0x7FFFFFF7;
    private static final int MERGESORT_NO_REC = 16;
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;

    private Arrays() {
    }

    public static void ensureFromTo(int n, int n2, int n3) {
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + n2 + ") is negative");
        }
        if (n2 > n3) {
            throw new IllegalArgumentException("Start index (" + n2 + ") is greater than end index (" + n3 + ")");
        }
        if (n3 > n) {
            throw new ArrayIndexOutOfBoundsException("End index (" + n3 + ") is greater than array length (" + n + ")");
        }
    }

    public static void ensureOffsetLength(int n, int n2, int n3) {
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n3 < 0) {
            throw new IllegalArgumentException("Length (" + n3 + ") is negative");
        }
        if (n2 + n3 > n) {
            throw new ArrayIndexOutOfBoundsException("Last index (" + (n2 + n3) + ") is greater than array length (" + n + ")");
        }
    }

    private static void inPlaceMerge(int n, int n2, int n3, IntComparator intComparator, Swapper swapper) {
        int n4;
        int n5;
        if (n >= n2 || n2 >= n3) {
            return;
        }
        if (n3 - n == 2) {
            if (intComparator.compare(n2, n) < 0) {
                swapper.swap(n, n2);
            }
            return;
        }
        if (n2 - n > n3 - n2) {
            n5 = n + (n2 - n) / 2;
            n4 = Arrays.lowerBound(n2, n3, n5, intComparator);
        } else {
            n4 = n2 + (n3 - n2) / 2;
            n5 = Arrays.upperBound(n, n2, n4, intComparator);
        }
        int n6 = n5;
        int n7 = n2;
        int n8 = n4;
        if (n7 != n6 && n7 != n8) {
            int n9 = n6;
            int n10 = n7;
            while (n9 < --n10) {
                swapper.swap(n9++, n10);
            }
            n9 = n7;
            n10 = n8;
            while (n9 < --n10) {
                swapper.swap(n9++, n10);
            }
            n9 = n6;
            n10 = n8;
            while (n9 < --n10) {
                swapper.swap(n9++, n10);
            }
        }
        n2 = n5 + (n4 - n2);
        Arrays.inPlaceMerge(n, n5, n2, intComparator, swapper);
        Arrays.inPlaceMerge(n2, n4, n3, intComparator, swapper);
    }

    private static int lowerBound(int n, int n2, int n3, IntComparator intComparator) {
        int n4 = n2 - n;
        while (n4 > 0) {
            int n5 = n4 / 2;
            int n6 = n + n5;
            if (intComparator.compare(n6, n3) < 0) {
                n = n6 + 1;
                n4 -= n5 + 1;
                continue;
            }
            n4 = n5;
        }
        return n;
    }

    private static int upperBound(int n, int n2, int n3, IntComparator intComparator) {
        int n4 = n2 - n;
        while (n4 > 0) {
            int n5 = n4 / 2;
            int n6 = n + n5;
            if (intComparator.compare(n3, n6) < 0) {
                n4 = n5;
                continue;
            }
            n = n6 + 1;
            n4 -= n5 + 1;
        }
        return n;
    }

    private static int med3(int n, int n2, int n3, IntComparator intComparator) {
        int n4 = intComparator.compare(n, n2);
        int n5 = intComparator.compare(n, n3);
        int n6 = intComparator.compare(n2, n3);
        return n4 < 0 ? (n6 < 0 ? n2 : (n5 < 0 ? n3 : n)) : (n6 > 0 ? n2 : (n5 > 0 ? n3 : n));
    }

    public static void mergeSort(int n, int n2, IntComparator intComparator, Swapper swapper) {
        int n3 = n2 - n;
        if (n3 < 16) {
            for (int i = n; i < n2; ++i) {
                for (int j = i; j > n && intComparator.compare(j - 1, j) > 0; --j) {
                    swapper.swap(j, j - 1);
                }
            }
            return;
        }
        int n4 = n + n2 >>> 1;
        Arrays.mergeSort(n, n4, intComparator, swapper);
        Arrays.mergeSort(n4, n2, intComparator, swapper);
        if (intComparator.compare(n4 - 1, n4) <= 0) {
            return;
        }
        Arrays.inPlaceMerge(n, n4, n2, intComparator, swapper);
    }

    protected static void swap(Swapper swapper, int n, int n2, int n3) {
        int n4 = 0;
        while (n4 < n3) {
            swapper.swap(n, n2);
            ++n4;
            ++n;
            ++n2;
        }
    }

    public static void parallelQuickSort(int n, int n2, IntComparator intComparator, Swapper swapper) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new ForkJoinGenericQuickSort(n, n2, intComparator, swapper));
        forkJoinPool.shutdown();
    }

    public static void quickSort(int n, int n2, IntComparator intComparator, Swapper swapper) {
        int n3;
        int n4;
        int n5;
        int n6 = n2 - n;
        if (n6 < 16) {
            for (int i = n; i < n2; ++i) {
                for (int j = i; j > n && intComparator.compare(j - 1, j) > 0; --j) {
                    swapper.swap(j, j - 1);
                }
            }
            return;
        }
        int n7 = n + n6 / 2;
        int n8 = n;
        int n9 = n2 - 1;
        if (n6 > 128) {
            n5 = n6 / 8;
            n8 = Arrays.med3(n8, n8 + n5, n8 + 2 * n5, intComparator);
            n7 = Arrays.med3(n7 - n5, n7, n7 + n5, intComparator);
            n9 = Arrays.med3(n9 - 2 * n5, n9 - n5, n9, intComparator);
        }
        n7 = Arrays.med3(n8, n7, n9, intComparator);
        int n10 = n5 = n;
        int n11 = n4 = n2 - 1;
        while (true) {
            if (n10 <= n4 && (n3 = intComparator.compare(n10, n7)) <= 0) {
                if (n3 == 0) {
                    if (n5 == n7) {
                        n7 = n10;
                    } else if (n10 == n7) {
                        n7 = n5;
                    }
                    swapper.swap(n5++, n10);
                }
                ++n10;
                continue;
            }
            while (n4 >= n10 && (n3 = intComparator.compare(n4, n7)) >= 0) {
                if (n3 == 0) {
                    if (n4 == n7) {
                        n7 = n11;
                    } else if (n11 == n7) {
                        n7 = n4;
                    }
                    swapper.swap(n4, n11--);
                }
                --n4;
            }
            if (n10 > n4) break;
            if (n10 == n7) {
                n7 = n11;
            } else if (n4 == n7) {
                n7 = n4;
            }
            swapper.swap(n10++, n4--);
        }
        n3 = Math.min(n5 - n, n10 - n5);
        Arrays.swap(swapper, n, n10 - n3, n3);
        n3 = Math.min(n11 - n4, n2 - n11 - 1);
        Arrays.swap(swapper, n10, n2 - n3, n3);
        n3 = n10 - n5;
        if (n3 > 1) {
            Arrays.quickSort(n, n + n3, intComparator, swapper);
        }
        if ((n3 = n11 - n4) > 1) {
            Arrays.quickSort(n2 - n3, n2, intComparator, swapper);
        }
    }

    static int access$000(int n, int n2, int n3, IntComparator intComparator) {
        return Arrays.med3(n, n2, n3, intComparator);
    }

    protected static class ForkJoinGenericQuickSort
    extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final IntComparator comp;
        private final Swapper swapper;

        public ForkJoinGenericQuickSort(int n, int n2, IntComparator intComparator, Swapper swapper) {
            this.from = n;
            this.to = n2;
            this.comp = intComparator;
            this.swapper = swapper;
        }

        @Override
        protected void compute() {
            int n;
            int n2;
            int n3;
            int n4 = this.to - this.from;
            if (n4 < 8192) {
                Arrays.quickSort(this.from, this.to, this.comp, this.swapper);
                return;
            }
            int n5 = this.from + n4 / 2;
            int n6 = this.from;
            int n7 = this.to - 1;
            int n8 = n4 / 8;
            n6 = Arrays.access$000(n6, n6 + n8, n6 + 2 * n8, this.comp);
            n5 = Arrays.access$000(n5 - n8, n5, n5 + n8, this.comp);
            n7 = Arrays.access$000(n7 - 2 * n8, n7 - n8, n7, this.comp);
            n5 = Arrays.access$000(n6, n5, n7, this.comp);
            int n9 = n3 = this.from;
            int n10 = n2 = this.to - 1;
            while (true) {
                if (n9 <= n2 && (n = this.comp.compare(n9, n5)) <= 0) {
                    if (n == 0) {
                        if (n3 == n5) {
                            n5 = n9;
                        } else if (n9 == n5) {
                            n5 = n3;
                        }
                        this.swapper.swap(n3++, n9);
                    }
                    ++n9;
                    continue;
                }
                while (n2 >= n9 && (n = this.comp.compare(n2, n5)) >= 0) {
                    if (n == 0) {
                        if (n2 == n5) {
                            n5 = n10;
                        } else if (n10 == n5) {
                            n5 = n2;
                        }
                        this.swapper.swap(n2, n10--);
                    }
                    --n2;
                }
                if (n9 > n2) break;
                if (n9 == n5) {
                    n5 = n10;
                } else if (n2 == n5) {
                    n5 = n2;
                }
                this.swapper.swap(n9++, n2--);
            }
            n8 = Math.min(n3 - this.from, n9 - n3);
            Arrays.swap(this.swapper, this.from, n9 - n8, n8);
            n8 = Math.min(n10 - n2, this.to - n10 - 1);
            Arrays.swap(this.swapper, n9, this.to - n8, n8);
            n8 = n9 - n3;
            n = n10 - n2;
            if (n8 > 1 && n > 1) {
                ForkJoinGenericQuickSort.invokeAll(new ForkJoinGenericQuickSort(this.from, this.from + n8, this.comp, this.swapper), new ForkJoinGenericQuickSort(this.to - n, this.to, this.comp, this.swapper));
            } else if (n8 > 1) {
                ForkJoinGenericQuickSort.invokeAll(new ForkJoinGenericQuickSort(this.from, this.from + n8, this.comp, this.swapper));
            } else {
                ForkJoinGenericQuickSort.invokeAll(new ForkJoinGenericQuickSort(this.to - n, this.to, this.comp, this.swapper));
            }
        }
    }
}

