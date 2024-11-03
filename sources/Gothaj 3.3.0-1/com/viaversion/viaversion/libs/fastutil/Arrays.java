package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.IntComparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class Arrays {
   public static final int MAX_ARRAY_SIZE = 2147483639;
   private static final int MERGESORT_NO_REC = 16;
   private static final int QUICKSORT_NO_REC = 16;
   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
   private static final int QUICKSORT_MEDIAN_OF_9 = 128;

   private Arrays() {
   }

   public static void ensureFromTo(int arrayLength, int from, int to) {
      assert arrayLength >= 0;

      if (from < 0) {
         throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative");
      } else if (from > to) {
         throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
      } else if (to > arrayLength) {
         throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than array length (" + arrayLength + ")");
      }
   }

   public static void ensureOffsetLength(int arrayLength, int offset, int length) {
      assert arrayLength >= 0;

      if (offset < 0) {
         throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
      } else if (length < 0) {
         throw new IllegalArgumentException("Length (" + length + ") is negative");
      } else if (length > arrayLength - offset) {
         throw new ArrayIndexOutOfBoundsException("Last index (" + ((long)offset + (long)length) + ") is greater than array length (" + arrayLength + ")");
      }
   }

   private static void inPlaceMerge(int from, int mid, int to, IntComparator comp, Swapper swapper) {
      if (from < mid && mid < to) {
         if (to - from == 2) {
            if (comp.compare(mid, from) < 0) {
               swapper.swap(from, mid);
            }
         } else {
            int firstCut;
            int secondCut;
            if (mid - from > to - mid) {
               firstCut = from + (mid - from) / 2;
               secondCut = lowerBound(mid, to, firstCut, comp);
            } else {
               secondCut = mid + (to - mid) / 2;
               firstCut = upperBound(from, mid, secondCut, comp);
            }

            if (mid != firstCut && mid != secondCut) {
               int first1 = firstCut;
               int last1 = mid;

               while (first1 < --last1) {
                  swapper.swap(first1++, last1);
               }

               first1 = mid;
               last1 = secondCut;

               while (first1 < --last1) {
                  swapper.swap(first1++, last1);
               }

               first1 = firstCut;
               last1 = secondCut;

               while (first1 < --last1) {
                  swapper.swap(first1++, last1);
               }
            }

            mid = firstCut + (secondCut - mid);
            inPlaceMerge(from, firstCut, mid, comp, swapper);
            inPlaceMerge(mid, secondCut, to, comp, swapper);
         }
      }
   }

   private static int lowerBound(int from, int to, int pos, IntComparator comp) {
      int len = to - from;

      while (len > 0) {
         int half = len / 2;
         int middle = from + half;
         if (comp.compare(middle, pos) < 0) {
            from = middle + 1;
            len -= half + 1;
         } else {
            len = half;
         }
      }

      return from;
   }

   private static int upperBound(int from, int mid, int pos, IntComparator comp) {
      int len = mid - from;

      while (len > 0) {
         int half = len / 2;
         int middle = from + half;
         if (comp.compare(pos, middle) < 0) {
            len = half;
         } else {
            from = middle + 1;
            len -= half + 1;
         }
      }

      return from;
   }

   private static int med3(int a, int b, int c, IntComparator comp) {
      int ab = comp.compare(a, b);
      int ac = comp.compare(a, c);
      int bc = comp.compare(b, c);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static ForkJoinPool getPool() {
      ForkJoinPool current = ForkJoinTask.getPool();
      return current == null ? ForkJoinPool.commonPool() : current;
   }

   public static void mergeSort(int from, int to, IntComparator c, Swapper swapper) {
      int length = to - from;
      if (length >= 16) {
         int mid = from + to >>> 1;
         mergeSort(from, mid, c, swapper);
         mergeSort(mid, to, c, swapper);
         if (c.compare(mid - 1, mid) > 0) {
            inPlaceMerge(from, mid, to, c, swapper);
         }
      } else {
         for (int i = from; i < to; i++) {
            for (int j = i; j > from && c.compare(j - 1, j) > 0; j--) {
               swapper.swap(j, j - 1);
            }
         }
      }
   }

   protected static void swap(Swapper swapper, int a, int b, int n) {
      int i = 0;

      while (i < n) {
         swapper.swap(a, b);
         i++;
         a++;
         b++;
      }
   }

   public static void parallelQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
      ForkJoinPool pool = getPool();
      if (to - from >= 8192 && pool.getParallelism() != 1) {
         pool.invoke(new Arrays.ForkJoinGenericQuickSort(from, to, comp, swapper));
      } else {
         quickSort(from, to, comp, swapper);
      }
   }

   public static void quickSort(int from, int to, IntComparator comp, Swapper swapper) {
      int len = to - from;
      if (len < 16) {
         for (int i = from; i < to; i++) {
            for (int j = i; j > from && comp.compare(j - 1, j) > 0; j--) {
               swapper.swap(j, j - 1);
            }
         }
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3(from, from + s, from + 2 * s, comp);
            m = med3(m - s, m, m + s, comp);
            n = med3(n - 2 * s, n - s, n, comp);
         }

         m = med3(l, m, n, comp);
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while (true) {
            int comparison;
            for (; b > c || (comparison = comp.compare(b, m)) > 0; swapper.swap(b++, c--)) {
               for (; c >= b && (comparison = comp.compare(c, m)) >= 0; c--) {
                  if (comparison == 0) {
                     if (c == m) {
                        m = d;
                     } else if (d == m) {
                        m = c;
                     }

                     swapper.swap(c, d--);
                  }
               }

               if (b > c) {
                  comparison = Math.min(a - from, b - a);
                  swap(swapper, from, b - comparison, comparison);
                  comparison = Math.min(d - c, to - d - 1);
                  swap(swapper, b, to - comparison, comparison);
                  if ((comparison = b - a) > 1) {
                     quickSort(from, from + comparison, comp, swapper);
                  }

                  if ((comparison = d - c) > 1) {
                     quickSort(to - comparison, to, comp, swapper);
                  }

                  return;
               }

               if (b == m) {
                  m = d;
               } else if (c == m) {
                  m = c;
               }
            }

            if (comparison == 0) {
               if (a == m) {
                  m = b;
               } else if (b == m) {
                  m = a;
               }

               swapper.swap(a++, b);
            }

            b++;
         }
      }
   }

   protected static class ForkJoinGenericQuickSort extends RecursiveAction {
      private static final long serialVersionUID = 1L;
      private final int from;
      private final int to;
      private final IntComparator comp;
      private final Swapper swapper;

      public ForkJoinGenericQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
         this.from = from;
         this.to = to;
         this.comp = comp;
         this.swapper = swapper;
      }

      @Override
      protected void compute() {
         int len = this.to - this.from;
         if (len < 8192) {
            Arrays.quickSort(this.from, this.to, this.comp, this.swapper);
         } else {
            int m = this.from + len / 2;
            int l = this.from;
            int n = this.to - 1;
            int s = len / 8;
            l = Arrays.med3(l, l + s, l + 2 * s, this.comp);
            m = Arrays.med3(m - s, m, m + s, this.comp);
            n = Arrays.med3(n - 2 * s, n - s, n, this.comp);
            m = Arrays.med3(l, m, n, this.comp);
            int a = this.from;
            int b = a;
            int c = this.to - 1;
            int d = c;

            while (true) {
               int comparison;
               for (; b > c || (comparison = this.comp.compare(b, m)) > 0; this.swapper.swap(b++, c--)) {
                  for (; c >= b && (comparison = this.comp.compare(c, m)) >= 0; c--) {
                     if (comparison == 0) {
                        if (c == m) {
                           m = d;
                        } else if (d == m) {
                           m = c;
                        }

                        this.swapper.swap(c, d--);
                     }
                  }

                  if (b > c) {
                     s = Math.min(a - this.from, b - a);
                     Arrays.swap(this.swapper, this.from, b - s, s);
                     s = Math.min(d - c, this.to - d - 1);
                     Arrays.swap(this.swapper, b, this.to - s, s);
                     s = b - a;
                     comparison = d - c;
                     if (s > 1 && comparison > 1) {
                        invokeAll(
                           new Arrays.ForkJoinGenericQuickSort(this.from, this.from + s, this.comp, this.swapper),
                           new Arrays.ForkJoinGenericQuickSort(this.to - comparison, this.to, this.comp, this.swapper)
                        );
                     } else if (s > 1) {
                        invokeAll(new ForkJoinTask[]{new Arrays.ForkJoinGenericQuickSort(this.from, this.from + s, this.comp, this.swapper)});
                     } else {
                        invokeAll(new ForkJoinTask[]{new Arrays.ForkJoinGenericQuickSort(this.to - comparison, this.to, this.comp, this.swapper)});
                     }

                     return;
                  }

                  if (b == m) {
                     m = d;
                  } else if (c == m) {
                     m = c;
                  }
               }

               if (comparison == 0) {
                  if (a == m) {
                     m = b;
                  } else if (b == m) {
                     m = a;
                  }

                  this.swapper.swap(a++, b);
               }

               b++;
            }
         }
      }
   }
}
