package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.Arrays;
import com.viaversion.viaversion.libs.fastutil.Hash;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrays;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public final class ObjectArrays {
   public static final Object[] EMPTY_ARRAY = new Object[0];
   public static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];
   private static final int QUICKSORT_NO_REC = 16;
   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
   private static final int MERGESORT_NO_REC = 16;
   public static final Hash.Strategy HASH_STRATEGY = new ObjectArrays.ArrayHashStrategy();

   private ObjectArrays() {
   }

   private static <K> K[] newArray(K[] prototype, int length) {
      Class<?> klass = prototype.getClass();
      if (klass == Object[].class) {
         return (K[])(length == 0 ? EMPTY_ARRAY : new Object[length]);
      } else {
         return (K[])((Object[])Array.newInstance(klass.getComponentType(), length));
      }
   }

   public static <K> K[] forceCapacity(K[] array, int length, int preserve) {
      K[] t = (K[])newArray(array, length);
      System.arraycopy(array, 0, t, 0, preserve);
      return t;
   }

   public static <K> K[] ensureCapacity(K[] array, int length) {
      return (K[])ensureCapacity(array, length, array.length);
   }

   public static <K> K[] ensureCapacity(K[] array, int length, int preserve) {
      return (K[])(length > array.length ? forceCapacity(array, length, preserve) : array);
   }

   public static <K> K[] grow(K[] array, int length) {
      return (K[])grow(array, length, array.length);
   }

   public static <K> K[] grow(K[] array, int length, int preserve) {
      if (length > array.length) {
         int newLength = (int)Math.max(Math.min((long)array.length + (long)(array.length >> 1), 2147483639L), (long)length);
         K[] t = (K[])newArray(array, newLength);
         System.arraycopy(array, 0, t, 0, preserve);
         return t;
      } else {
         return array;
      }
   }

   public static <K> K[] trim(K[] array, int length) {
      if (length >= array.length) {
         return array;
      } else {
         K[] t = (K[])newArray(array, length);
         System.arraycopy(array, 0, t, 0, length);
         return t;
      }
   }

   public static <K> K[] setLength(K[] array, int length) {
      if (length == array.length) {
         return array;
      } else {
         return (K[])(length < array.length ? trim(array, length) : ensureCapacity(array, length));
      }
   }

   public static <K> K[] copy(K[] array, int offset, int length) {
      ensureOffsetLength(array, offset, length);
      K[] a = (K[])newArray(array, length);
      System.arraycopy(array, offset, a, 0, length);
      return a;
   }

   public static <K> K[] copy(K[] array) {
      return (K[])((Object[])array.clone());
   }

   @Deprecated
   public static <K> void fill(K[] array, K value) {
      int i = array.length;

      while (i-- != 0) {
         array[i] = value;
      }
   }

   @Deprecated
   public static <K> void fill(K[] array, int from, int to, K value) {
      ensureFromTo(array, from, to);
      if (from == 0) {
         while (to-- != 0) {
            array[to] = value;
         }
      } else {
         for (int i = from; i < to; i++) {
            array[i] = value;
         }
      }
   }

   @Deprecated
   public static <K> boolean equals(K[] a1, K[] a2) {
      int i = a1.length;
      if (i != a2.length) {
         return false;
      } else {
         while (i-- != 0) {
            if (!Objects.equals(a1[i], a2[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public static <K> void ensureFromTo(K[] a, int from, int to) {
      Arrays.ensureFromTo(a.length, from, to);
   }

   public static <K> void ensureOffsetLength(K[] a, int offset, int length) {
      Arrays.ensureOffsetLength(a.length, offset, length);
   }

   public static <K> void ensureSameLength(K[] a, K[] b) {
      if (a.length != b.length) {
         throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
      }
   }

   private static ForkJoinPool getPool() {
      ForkJoinPool current = ForkJoinTask.getPool();
      return current == null ? ForkJoinPool.commonPool() : current;
   }

   public static <K> void swap(K[] x, int a, int b) {
      K t = x[a];
      x[a] = x[b];
      x[b] = t;
   }

   public static <K> void swap(K[] x, int a, int b, int n) {
      int i = 0;

      while (i < n) {
         swap(x, a, b);
         i++;
         a++;
         b++;
      }
   }

   private static <K> int med3(K[] x, int a, int b, int c, Comparator<K> comp) {
      int ab = comp.compare(x[a], x[b]);
      int ac = comp.compare(x[a], x[c]);
      int bc = comp.compare(x[b], x[c]);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static <K> void selectionSort(K[] a, int from, int to, Comparator<K> comp) {
      for (int i = from; i < to - 1; i++) {
         int m = i;

         for (int j = i + 1; j < to; j++) {
            if (comp.compare(a[j], a[m]) < 0) {
               m = j;
            }
         }

         if (m != i) {
            K u = a[i];
            a[i] = a[m];
            a[m] = u;
         }
      }
   }

   private static <K> void insertionSort(K[] a, int from, int to, Comparator<K> comp) {
      int i = from;

      while (++i < to) {
         K t = a[i];
         int j = i;

         for (K u = a[i - 1]; comp.compare(t, u) < 0; u = a[j - 1]) {
            a[j] = u;
            if (from == j - 1) {
               j--;
               break;
            }

            j--;
         }

         a[j] = t;
      }
   }

   public static <K> void quickSort(K[] x, int from, int to, Comparator<K> comp) {
      int len = to - from;
      if (len < 16) {
         selectionSort(x, from, to, comp);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3(x, from, from + s, from + 2 * s, comp);
            m = med3(x, m - s, m, m + s, comp);
            n = med3(x, n - 2 * s, n - s, n, comp);
         }

         m = med3(x, l, m, n, comp);
         K v = x[m];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while (true) {
            int comparison;
            while (b > c || (comparison = comp.compare(x[b], v)) > 0) {
               for (; c >= b && (comparison = comp.compare(x[c], v)) >= 0; c--) {
                  if (comparison == 0) {
                     swap(x, c, d--);
                  }
               }

               if (b > c) {
                  comparison = Math.min(a - from, b - a);
                  swap(x, from, b - comparison, comparison);
                  comparison = Math.min(d - c, to - d - 1);
                  swap(x, b, to - comparison, comparison);
                  if ((comparison = b - a) > 1) {
                     quickSort(x, from, from + comparison, comp);
                  }

                  if ((comparison = d - c) > 1) {
                     quickSort(x, to - comparison, to, comp);
                  }

                  return;
               }

               swap(x, b++, c--);
            }

            if (comparison == 0) {
               swap(x, a++, b);
            }

            b++;
         }
      }
   }

   public static <K> void quickSort(K[] x, Comparator<K> comp) {
      quickSort(x, 0, x.length, comp);
   }

   public static <K> void parallelQuickSort(K[] x, int from, int to, Comparator<K> comp) {
      ForkJoinPool pool = getPool();
      if (to - from >= 8192 && pool.getParallelism() != 1) {
         pool.invoke(new ObjectArrays.ForkJoinQuickSortComp<>(x, from, to, comp));
      } else {
         quickSort(x, from, to, comp);
      }
   }

   public static <K> void parallelQuickSort(K[] x, Comparator<K> comp) {
      parallelQuickSort(x, 0, x.length, comp);
   }

   private static <K> int med3(K[] x, int a, int b, int c) {
      int ab = ((Comparable)x[a]).compareTo(x[b]);
      int ac = ((Comparable)x[a]).compareTo(x[c]);
      int bc = ((Comparable)x[b]).compareTo(x[c]);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static <K> void selectionSort(K[] a, int from, int to) {
      for (int i = from; i < to - 1; i++) {
         int m = i;

         for (int j = i + 1; j < to; j++) {
            if (((Comparable)a[j]).compareTo(a[m]) < 0) {
               m = j;
            }
         }

         if (m != i) {
            K u = a[i];
            a[i] = a[m];
            a[m] = u;
         }
      }
   }

   private static <K> void insertionSort(K[] a, int from, int to) {
      int i = from;

      while (++i < to) {
         K t = a[i];
         int j = i;

         for (K u = a[i - 1]; ((Comparable)t).compareTo(u) < 0; u = a[j - 1]) {
            a[j] = u;
            if (from == j - 1) {
               j--;
               break;
            }

            j--;
         }

         a[j] = t;
      }
   }

   public static <K> void quickSort(K[] x, int from, int to) {
      int len = to - from;
      if (len < 16) {
         selectionSort(x, from, to);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3(x, from, from + s, from + 2 * s);
            m = med3(x, m - s, m, m + s);
            n = med3(x, n - 2 * s, n - s, n);
         }

         m = med3(x, l, m, n);
         K v = x[m];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while (true) {
            int comparison;
            while (b > c || (comparison = ((Comparable)x[b]).compareTo(v)) > 0) {
               for (; c >= b && (comparison = ((Comparable)x[c]).compareTo(v)) >= 0; c--) {
                  if (comparison == 0) {
                     swap(x, c, d--);
                  }
               }

               if (b > c) {
                  comparison = Math.min(a - from, b - a);
                  swap(x, from, b - comparison, comparison);
                  comparison = Math.min(d - c, to - d - 1);
                  swap(x, b, to - comparison, comparison);
                  if ((comparison = b - a) > 1) {
                     quickSort(x, from, from + comparison);
                  }

                  if ((comparison = d - c) > 1) {
                     quickSort(x, to - comparison, to);
                  }

                  return;
               }

               swap(x, b++, c--);
            }

            if (comparison == 0) {
               swap(x, a++, b);
            }

            b++;
         }
      }
   }

   public static <K> void quickSort(K[] x) {
      quickSort(x, 0, x.length);
   }

   public static <K> void parallelQuickSort(K[] x, int from, int to) {
      ForkJoinPool pool = getPool();
      if (to - from >= 8192 && pool.getParallelism() != 1) {
         pool.invoke(new ObjectArrays.ForkJoinQuickSort(x, from, to));
      } else {
         quickSort(x, from, to);
      }
   }

   public static <K> void parallelQuickSort(K[] x) {
      parallelQuickSort(x, 0, x.length);
   }

   private static <K> int med3Indirect(int[] perm, K[] x, int a, int b, int c) {
      K aa = x[perm[a]];
      K bb = x[perm[b]];
      K cc = x[perm[c]];
      int ab = ((Comparable)aa).compareTo(bb);
      int ac = ((Comparable)aa).compareTo(cc);
      int bc = ((Comparable)bb).compareTo(cc);
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static <K> void insertionSortIndirect(int[] perm, K[] a, int from, int to) {
      int i = from;

      while (++i < to) {
         int t = perm[i];
         int j = i;

         for (int u = perm[i - 1]; ((Comparable)a[t]).compareTo(a[u]) < 0; u = perm[j - 1]) {
            perm[j] = u;
            if (from == j - 1) {
               j--;
               break;
            }

            j--;
         }

         perm[j] = t;
      }
   }

   public static <K> void quickSortIndirect(int[] perm, K[] x, int from, int to) {
      int len = to - from;
      if (len < 16) {
         insertionSortIndirect(perm, x, from, to);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3Indirect(perm, x, from, from + s, from + 2 * s);
            m = med3Indirect(perm, x, m - s, m, m + s);
            n = med3Indirect(perm, x, n - 2 * s, n - s, n);
         }

         m = med3Indirect(perm, x, l, m, n);
         K v = x[perm[m]];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while (true) {
            int comparison;
            while (b > c || (comparison = ((Comparable)x[perm[b]]).compareTo(v)) > 0) {
               for (; c >= b && (comparison = ((Comparable)x[perm[c]]).compareTo(v)) >= 0; c--) {
                  if (comparison == 0) {
                     IntArrays.swap(perm, c, d--);
                  }
               }

               if (b > c) {
                  comparison = Math.min(a - from, b - a);
                  IntArrays.swap(perm, from, b - comparison, comparison);
                  comparison = Math.min(d - c, to - d - 1);
                  IntArrays.swap(perm, b, to - comparison, comparison);
                  if ((comparison = b - a) > 1) {
                     quickSortIndirect(perm, x, from, from + comparison);
                  }

                  if ((comparison = d - c) > 1) {
                     quickSortIndirect(perm, x, to - comparison, to);
                  }

                  return;
               }

               IntArrays.swap(perm, b++, c--);
            }

            if (comparison == 0) {
               IntArrays.swap(perm, a++, b);
            }

            b++;
         }
      }
   }

   public static <K> void quickSortIndirect(int[] perm, K[] x) {
      quickSortIndirect(perm, x, 0, x.length);
   }

   public static <K> void parallelQuickSortIndirect(int[] perm, K[] x, int from, int to) {
      ForkJoinPool pool = getPool();
      if (to - from >= 8192 && pool.getParallelism() != 1) {
         pool.invoke(new ObjectArrays.ForkJoinQuickSortIndirect(perm, x, from, to));
      } else {
         quickSortIndirect(perm, x, from, to);
      }
   }

   public static <K> void parallelQuickSortIndirect(int[] perm, K[] x) {
      parallelQuickSortIndirect(perm, x, 0, x.length);
   }

   public static <K> void stabilize(int[] perm, K[] x, int from, int to) {
      int curr = from;

      for (int i = from + 1; i < to; i++) {
         if (x[perm[i]] != x[perm[curr]]) {
            if (i - curr > 1) {
               IntArrays.parallelQuickSort(perm, curr, i);
            }

            curr = i;
         }
      }

      if (to - curr > 1) {
         IntArrays.parallelQuickSort(perm, curr, to);
      }
   }

   public static <K> void stabilize(int[] perm, K[] x) {
      stabilize(perm, x, 0, perm.length);
   }

   private static <K> int med3(K[] x, K[] y, int a, int b, int c) {
      int t;
      int ab = (t = ((Comparable)x[a]).compareTo(x[b])) == 0 ? ((Comparable)y[a]).compareTo(y[b]) : t;
      int ac = (t = ((Comparable)x[a]).compareTo(x[c])) == 0 ? ((Comparable)y[a]).compareTo(y[c]) : t;
      int bc = (t = ((Comparable)x[b]).compareTo(x[c])) == 0 ? ((Comparable)y[b]).compareTo(y[c]) : t;
      return ab < 0 ? (bc < 0 ? b : (ac < 0 ? c : a)) : (bc > 0 ? b : (ac > 0 ? c : a));
   }

   private static <K> void swap(K[] x, K[] y, int a, int b) {
      K t = x[a];
      K u = y[a];
      x[a] = x[b];
      y[a] = y[b];
      x[b] = t;
      y[b] = u;
   }

   private static <K> void swap(K[] x, K[] y, int a, int b, int n) {
      int i = 0;

      while (i < n) {
         swap(x, y, a, b);
         i++;
         a++;
         b++;
      }
   }

   private static <K> void selectionSort(K[] a, K[] b, int from, int to) {
      for (int i = from; i < to - 1; i++) {
         int m = i;

         for (int j = i + 1; j < to; j++) {
            int u;
            if ((u = ((Comparable)a[j]).compareTo(a[m])) < 0 || u == false && ((Comparable)b[j]).compareTo(b[m]) < 0) {
               m = j;
            }
         }

         if (m != i) {
            K t = a[i];
            a[i] = a[m];
            a[m] = t;
            t = b[i];
            b[i] = b[m];
            b[m] = t;
         }
      }
   }

   public static <K> void quickSort(K[] x, K[] y, int from, int to) {
      int len = to - from;
      if (len < 16) {
         selectionSort(x, y, from, to);
      } else {
         int m = from + len / 2;
         int l = from;
         int n = to - 1;
         if (len > 128) {
            int s = len / 8;
            l = med3(x, y, from, from + s, from + 2 * s);
            m = med3(x, y, m - s, m, m + s);
            n = med3(x, y, n - 2 * s, n - s, n);
         }

         m = med3(x, y, l, m, n);
         K v = x[m];
         K w = y[m];
         int a = from;
         int b = from;
         int c = to - 1;
         int d = c;

         while (true) {
            int comparison;
            int t;
            while (b > c || (comparison = (t = ((Comparable)x[b]).compareTo(v)) == 0 ? ((Comparable)y[b]).compareTo(w) : t) > 0) {
               for (; c >= b && (comparison = (t = ((Comparable)x[c]).compareTo(v)) == 0 ? ((Comparable)y[c]).compareTo(w) : t) >= 0; c--) {
                  if (comparison == 0) {
                     swap(x, y, c, d--);
                  }
               }

               if (b > c) {
                  comparison = Math.min(a - from, b - a);
                  swap(x, y, from, b - comparison, comparison);
                  comparison = Math.min(d - c, to - d - 1);
                  swap(x, y, b, to - comparison, comparison);
                  if ((comparison = b - a) > 1) {
                     quickSort(x, y, from, from + comparison);
                  }

                  if ((comparison = d - c) > 1) {
                     quickSort(x, y, to - comparison, to);
                  }

                  return;
               }

               swap(x, y, b++, c--);
            }

            if (comparison == 0) {
               swap(x, y, a++, b);
            }

            b++;
         }
      }
   }

   public static <K> void quickSort(K[] x, K[] y) {
      ensureSameLength(x, y);
      quickSort(x, y, 0, x.length);
   }

   public static <K> void parallelQuickSort(K[] x, K[] y, int from, int to) {
      ForkJoinPool pool = getPool();
      if (to - from >= 8192 && pool.getParallelism() != 1) {
         pool.invoke(new ObjectArrays.ForkJoinQuickSort2(x, y, from, to));
      } else {
         quickSort(x, y, from, to);
      }
   }

   public static <K> void parallelQuickSort(K[] x, K[] y) {
      ensureSameLength(x, y);
      parallelQuickSort(x, y, 0, x.length);
   }

   public static <K> void unstableSort(K[] a, int from, int to) {
      quickSort(a, from, to);
   }

   public static <K> void unstableSort(K[] a) {
      unstableSort(a, 0, a.length);
   }

   public static <K> void unstableSort(K[] a, int from, int to, Comparator<K> comp) {
      quickSort(a, from, to, comp);
   }

   public static <K> void unstableSort(K[] a, Comparator<K> comp) {
      unstableSort(a, 0, a.length, comp);
   }

   public static <K> void mergeSort(K[] a, int from, int to, K[] supp) {
      int len = to - from;
      if (len < 16) {
         insertionSort(a, from, to);
      } else {
         if (supp == null) {
            supp = java.util.Arrays.copyOf(a, to);
         }

         int mid = from + to >>> 1;
         mergeSort(supp, from, mid, a);
         mergeSort(supp, mid, to, a);
         if (((Comparable)supp[mid - 1]).compareTo(supp[mid]) <= 0) {
            System.arraycopy(supp, from, a, from, len);
         } else {
            int i = from;
            int p = from;

            for (int q = mid; i < to; i++) {
               if (q < to && (p >= mid || ((Comparable)supp[p]).compareTo(supp[q]) > 0)) {
                  a[i] = supp[q++];
               } else {
                  a[i] = supp[p++];
               }
            }
         }
      }
   }

   public static <K> void mergeSort(K[] a, int from, int to) {
      mergeSort(a, from, to, (K[])((Object[])null));
   }

   public static <K> void mergeSort(K[] a) {
      mergeSort(a, 0, a.length);
   }

   public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp, K[] supp) {
      int len = to - from;
      if (len < 16) {
         insertionSort(a, from, to, comp);
      } else {
         if (supp == null) {
            supp = java.util.Arrays.copyOf(a, to);
         }

         int mid = from + to >>> 1;
         mergeSort(supp, from, mid, comp, a);
         mergeSort(supp, mid, to, comp, a);
         if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
            System.arraycopy(supp, from, a, from, len);
         } else {
            int i = from;
            int p = from;

            for (int q = mid; i < to; i++) {
               if (q < to && (p >= mid || comp.compare(supp[p], supp[q]) > 0)) {
                  a[i] = supp[q++];
               } else {
                  a[i] = supp[p++];
               }
            }
         }
      }
   }

   public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp) {
      mergeSort(a, from, to, comp, (K[])((Object[])null));
   }

   public static <K> void mergeSort(K[] a, Comparator<K> comp) {
      mergeSort(a, 0, a.length, comp);
   }

   public static <K> void stableSort(K[] a, int from, int to) {
      java.util.Arrays.sort(a, from, to);
   }

   public static <K> void stableSort(K[] a) {
      stableSort(a, 0, a.length);
   }

   public static <K> void stableSort(K[] a, int from, int to, Comparator<K> comp) {
      java.util.Arrays.sort(a, from, to, comp);
   }

   public static <K> void stableSort(K[] a, Comparator<K> comp) {
      stableSort(a, 0, a.length, comp);
   }

   public static <K> int binarySearch(K[] a, int from, int to, K key) {
      to--;

      while (from <= to) {
         int mid = from + to >>> 1;
         K midVal = a[mid];
         int cmp = ((Comparable)midVal).compareTo(key);
         if (cmp < 0) {
            from = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            to = mid - 1;
         }
      }

      return -(from + 1);
   }

   public static <K> int binarySearch(K[] a, K key) {
      return binarySearch(a, 0, a.length, key);
   }

   public static <K> int binarySearch(K[] a, int from, int to, K key, Comparator<K> c) {
      to--;

      while (from <= to) {
         int mid = from + to >>> 1;
         K midVal = a[mid];
         int cmp = c.compare(midVal, key);
         if (cmp < 0) {
            from = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            to = mid - 1;
         }
      }

      return -(from + 1);
   }

   public static <K> int binarySearch(K[] a, K key, Comparator<K> c) {
      return binarySearch(a, 0, a.length, key, c);
   }

   public static <K> K[] shuffle(K[] a, int from, int to, Random random) {
      int i = to - from;

      while (i-- != 0) {
         int p = random.nextInt(i + 1);
         K t = a[from + i];
         a[from + i] = a[from + p];
         a[from + p] = t;
      }

      return a;
   }

   public static <K> K[] shuffle(K[] a, Random random) {
      int i = a.length;

      while (i-- != 0) {
         int p = random.nextInt(i + 1);
         K t = a[i];
         a[i] = a[p];
         a[p] = t;
      }

      return a;
   }

   public static <K> K[] reverse(K[] a) {
      int length = a.length;
      int i = length / 2;

      while (i-- != 0) {
         K t = a[length - i - 1];
         a[length - i - 1] = a[i];
         a[i] = t;
      }

      return a;
   }

   public static <K> K[] reverse(K[] a, int from, int to) {
      int length = to - from;
      int i = length / 2;

      while (i-- != 0) {
         K t = a[from + length - i - 1];
         a[from + length - i - 1] = a[from + i];
         a[from + i] = t;
      }

      return a;
   }

   private static final class ArrayHashStrategy<K> implements Hash.Strategy<K[]>, Serializable {
      private static final long serialVersionUID = -7046029254386353129L;

      private ArrayHashStrategy() {
      }

      public int hashCode(K[] o) {
         return java.util.Arrays.hashCode(o);
      }

      public boolean equals(K[] a, K[] b) {
         return java.util.Arrays.equals(a, b);
      }
   }

   protected static class ForkJoinQuickSort<K> extends RecursiveAction {
      private static final long serialVersionUID = 1L;
      private final int from;
      private final int to;
      private final K[] x;

      public ForkJoinQuickSort(K[] x, int from, int to) {
         this.from = from;
         this.to = to;
         this.x = x;
      }

      @Override
      protected void compute() {
         K[] x = this.x;
         int len = this.to - this.from;
         if (len < 8192) {
            ObjectArrays.quickSort(x, this.from, this.to);
         } else {
            int m = this.from + len / 2;
            int l = this.from;
            int n = this.to - 1;
            int s = len / 8;
            l = ObjectArrays.med3(x, l, l + s, l + 2 * s);
            m = ObjectArrays.med3(x, m - s, m, m + s);
            n = ObjectArrays.med3(x, n - 2 * s, n - s, n);
            m = ObjectArrays.med3(x, l, m, n);
            K v = x[m];
            int a = this.from;
            int b = a;
            int c = this.to - 1;
            int d = c;

            while (true) {
               int comparison;
               while (b > c || (comparison = ((Comparable)x[b]).compareTo(v)) > 0) {
                  for (; c >= b && (comparison = ((Comparable)x[c]).compareTo(v)) >= 0; c--) {
                     if (comparison == 0) {
                        ObjectArrays.swap(x, c, d--);
                     }
                  }

                  if (b > c) {
                     s = Math.min(a - this.from, b - a);
                     ObjectArrays.swap(x, this.from, b - s, s);
                     s = Math.min(d - c, this.to - d - 1);
                     ObjectArrays.swap(x, b, this.to - s, s);
                     s = b - a;
                     comparison = d - c;
                     if (s > 1 && comparison > 1) {
                        invokeAll(
                           new ObjectArrays.ForkJoinQuickSort(x, this.from, this.from + s),
                           new ObjectArrays.ForkJoinQuickSort(x, this.to - comparison, this.to)
                        );
                     } else if (s > 1) {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSort(x, this.from, this.from + s)});
                     } else {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSort(x, this.to - comparison, this.to)});
                     }

                     return;
                  }

                  ObjectArrays.swap(x, b++, c--);
               }

               if (comparison == 0) {
                  ObjectArrays.swap(x, a++, b);
               }

               b++;
            }
         }
      }
   }

   protected static class ForkJoinQuickSort2<K> extends RecursiveAction {
      private static final long serialVersionUID = 1L;
      private final int from;
      private final int to;
      private final K[] x;
      private final K[] y;

      public ForkJoinQuickSort2(K[] x, K[] y, int from, int to) {
         this.from = from;
         this.to = to;
         this.x = x;
         this.y = y;
      }

      @Override
      protected void compute() {
         K[] x = this.x;
         K[] y = this.y;
         int len = this.to - this.from;
         if (len < 8192) {
            ObjectArrays.quickSort(x, y, this.from, this.to);
         } else {
            int m = this.from + len / 2;
            int l = this.from;
            int n = this.to - 1;
            int s = len / 8;
            l = ObjectArrays.med3(x, y, l, l + s, l + 2 * s);
            m = ObjectArrays.med3(x, y, m - s, m, m + s);
            n = ObjectArrays.med3(x, y, n - 2 * s, n - s, n);
            m = ObjectArrays.med3(x, y, l, m, n);
            K v = x[m];
            K w = y[m];
            int a = this.from;
            int b = a;
            int c = this.to - 1;
            int d = c;

            while (true) {
               int comparison;
               int t;
               while (b > c || (comparison = (t = ((Comparable)x[b]).compareTo(v)) == 0 ? ((Comparable)y[b]).compareTo(w) : t) > 0) {
                  for (; c >= b && (comparison = (t = ((Comparable)x[c]).compareTo(v)) == 0 ? ((Comparable)y[c]).compareTo(w) : t) >= 0; c--) {
                     if (comparison == 0) {
                        ObjectArrays.swap(x, y, c, d--);
                     }
                  }

                  if (b > c) {
                     s = Math.min(a - this.from, b - a);
                     ObjectArrays.swap(x, y, this.from, b - s, s);
                     s = Math.min(d - c, this.to - d - 1);
                     ObjectArrays.swap(x, y, b, this.to - s, s);
                     s = b - a;
                     comparison = d - c;
                     if (s > 1 && comparison > 1) {
                        invokeAll(
                           new ObjectArrays.ForkJoinQuickSort2(x, y, this.from, this.from + s),
                           new ObjectArrays.ForkJoinQuickSort2(x, y, this.to - comparison, this.to)
                        );
                     } else if (s > 1) {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSort2(x, y, this.from, this.from + s)});
                     } else {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSort2(x, y, this.to - comparison, this.to)});
                     }

                     return;
                  }

                  ObjectArrays.swap(x, y, b++, c--);
               }

               if (comparison == 0) {
                  ObjectArrays.swap(x, y, a++, b);
               }

               b++;
            }
         }
      }
   }

   protected static class ForkJoinQuickSortComp<K> extends RecursiveAction {
      private static final long serialVersionUID = 1L;
      private final int from;
      private final int to;
      private final K[] x;
      private final Comparator<K> comp;

      public ForkJoinQuickSortComp(K[] x, int from, int to, Comparator<K> comp) {
         this.from = from;
         this.to = to;
         this.x = x;
         this.comp = comp;
      }

      @Override
      protected void compute() {
         K[] x = this.x;
         int len = this.to - this.from;
         if (len < 8192) {
            ObjectArrays.quickSort(x, this.from, this.to, this.comp);
         } else {
            int m = this.from + len / 2;
            int l = this.from;
            int n = this.to - 1;
            int s = len / 8;
            l = ObjectArrays.med3(x, l, l + s, l + 2 * s, this.comp);
            m = ObjectArrays.med3(x, m - s, m, m + s, this.comp);
            n = ObjectArrays.med3(x, n - 2 * s, n - s, n, this.comp);
            m = ObjectArrays.med3(x, l, m, n, this.comp);
            K v = x[m];
            int a = this.from;
            int b = a;
            int c = this.to - 1;
            int d = c;

            while (true) {
               int comparison;
               while (b > c || (comparison = this.comp.compare(x[b], v)) > 0) {
                  for (; c >= b && (comparison = this.comp.compare(x[c], v)) >= 0; c--) {
                     if (comparison == 0) {
                        ObjectArrays.swap(x, c, d--);
                     }
                  }

                  if (b > c) {
                     s = Math.min(a - this.from, b - a);
                     ObjectArrays.swap(x, this.from, b - s, s);
                     s = Math.min(d - c, this.to - d - 1);
                     ObjectArrays.swap(x, b, this.to - s, s);
                     s = b - a;
                     comparison = d - c;
                     if (s > 1 && comparison > 1) {
                        invokeAll(
                           new ObjectArrays.ForkJoinQuickSortComp<>(x, this.from, this.from + s, this.comp),
                           new ObjectArrays.ForkJoinQuickSortComp<>(x, this.to - comparison, this.to, this.comp)
                        );
                     } else if (s > 1) {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSortComp<>(x, this.from, this.from + s, this.comp)});
                     } else {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSortComp<>(x, this.to - comparison, this.to, this.comp)});
                     }

                     return;
                  }

                  ObjectArrays.swap(x, b++, c--);
               }

               if (comparison == 0) {
                  ObjectArrays.swap(x, a++, b);
               }

               b++;
            }
         }
      }
   }

   protected static class ForkJoinQuickSortIndirect<K> extends RecursiveAction {
      private static final long serialVersionUID = 1L;
      private final int from;
      private final int to;
      private final int[] perm;
      private final K[] x;

      public ForkJoinQuickSortIndirect(int[] perm, K[] x, int from, int to) {
         this.from = from;
         this.to = to;
         this.x = x;
         this.perm = perm;
      }

      @Override
      protected void compute() {
         K[] x = this.x;
         int len = this.to - this.from;
         if (len < 8192) {
            ObjectArrays.quickSortIndirect(this.perm, x, this.from, this.to);
         } else {
            int m = this.from + len / 2;
            int l = this.from;
            int n = this.to - 1;
            int s = len / 8;
            l = ObjectArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
            m = ObjectArrays.med3Indirect(this.perm, x, m - s, m, m + s);
            n = ObjectArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
            m = ObjectArrays.med3Indirect(this.perm, x, l, m, n);
            K v = x[this.perm[m]];
            int a = this.from;
            int b = a;
            int c = this.to - 1;
            int d = c;

            while (true) {
               int comparison;
               while (b > c || (comparison = ((Comparable)x[this.perm[b]]).compareTo(v)) > 0) {
                  for (; c >= b && (comparison = ((Comparable)x[this.perm[c]]).compareTo(v)) >= 0; c--) {
                     if (comparison == 0) {
                        IntArrays.swap(this.perm, c, d--);
                     }
                  }

                  if (b > c) {
                     s = Math.min(a - this.from, b - a);
                     IntArrays.swap(this.perm, this.from, b - s, s);
                     s = Math.min(d - c, this.to - d - 1);
                     IntArrays.swap(this.perm, b, this.to - s, s);
                     s = b - a;
                     comparison = d - c;
                     if (s > 1 && comparison > 1) {
                        invokeAll(
                           new ObjectArrays.ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s),
                           new ObjectArrays.ForkJoinQuickSortIndirect(this.perm, x, this.to - comparison, this.to)
                        );
                     } else if (s > 1) {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s)});
                     } else {
                        invokeAll(new ForkJoinTask[]{new ObjectArrays.ForkJoinQuickSortIndirect(this.perm, x, this.to - comparison, this.to)});
                     }

                     return;
                  }

                  IntArrays.swap(this.perm, b++, c--);
               }

               if (comparison == 0) {
                  IntArrays.swap(this.perm, a++, b);
               }

               b++;
            }
         }
      }
   }
}
