/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ExecutorCompletionService;
/*      */ import java.util.concurrent.ExecutorService;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.ForkJoinPool;
/*      */ import java.util.concurrent.ForkJoinTask;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.RecursiveAction;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class LongArrays
/*      */ {
/*  103 */   public static final long[] EMPTY_ARRAY = new long[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final long[] DEFAULT_EMPTY_ARRAY = new long[0];
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 16;
/*      */   
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 8;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 2000;
/*      */   
/*      */   public static long[] forceCapacity(long[] array, int length, int preserve) {
/*  128 */     long[] t = new long[length];
/*  129 */     System.arraycopy(array, 0, t, 0, preserve);
/*  130 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] ensureCapacity(long[] array, int length) {
/*  148 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] ensureCapacity(long[] array, int length, int preserve) {
/*  166 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] grow(long[] array, int length) {
/*  187 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] grow(long[] array, int length, int preserve) {
/*  211 */     if (length > array.length) {
/*      */       
/*  213 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  214 */       long[] t = new long[newLength];
/*  215 */       System.arraycopy(array, 0, t, 0, preserve);
/*  216 */       return t;
/*      */     } 
/*  218 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] trim(long[] array, int length) {
/*  233 */     if (length >= array.length)
/*  234 */       return array; 
/*  235 */     long[] t = (length == 0) ? EMPTY_ARRAY : new long[length];
/*  236 */     System.arraycopy(array, 0, t, 0, length);
/*  237 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] setLength(long[] array, int length) {
/*  255 */     if (length == array.length)
/*  256 */       return array; 
/*  257 */     if (length < array.length)
/*  258 */       return trim(array, length); 
/*  259 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] copy(long[] array, int offset, int length) {
/*  274 */     ensureOffsetLength(array, offset, length);
/*  275 */     long[] a = (length == 0) ? EMPTY_ARRAY : new long[length];
/*  276 */     System.arraycopy(array, offset, a, 0, length);
/*  277 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] copy(long[] array) {
/*  287 */     return (long[])array.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void fill(long[] array, long value) {
/*  300 */     int i = array.length;
/*  301 */     while (i-- != 0) {
/*  302 */       array[i] = value;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void fill(long[] array, int from, int to, long value) {
/*  320 */     ensureFromTo(array, from, to);
/*  321 */     if (from == 0) {
/*  322 */       while (to-- != 0)
/*  323 */         array[to] = value; 
/*      */     } else {
/*  325 */       for (int i = from; i < to; i++) {
/*  326 */         array[i] = value;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static boolean equals(long[] a1, long[] a2) {
/*  342 */     int i = a1.length;
/*  343 */     if (i != a2.length)
/*  344 */       return false; 
/*  345 */     while (i-- != 0) {
/*  346 */       if (a1[i] != a2[i])
/*  347 */         return false; 
/*  348 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(long[] a, int from, int to) {
/*  370 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(long[] a, int offset, int length) {
/*  391 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(long[] a, long[] b) {
/*  404 */     if (a.length != b.length) {
/*  405 */       throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(long[] x, int a, int b) {
/*  422 */     long t = x[a];
/*  423 */     x[a] = x[b];
/*  424 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(long[] x, int a, int b, int n) {
/*  440 */     for (int i = 0; i < n; i++, a++, b++)
/*  441 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(long[] x, int a, int b, int c, LongComparator comp) {
/*  444 */     int ab = comp.compare(x[a], x[b]);
/*  445 */     int ac = comp.compare(x[a], x[c]);
/*  446 */     int bc = comp.compare(x[b], x[c]);
/*  447 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(long[] a, int from, int to, LongComparator comp) {
/*  450 */     for (int i = from; i < to - 1; i++) {
/*  451 */       int m = i;
/*  452 */       for (int j = i + 1; j < to; j++) {
/*  453 */         if (comp.compare(a[j], a[m]) < 0)
/*  454 */           m = j; 
/*  455 */       }  if (m != i) {
/*  456 */         long u = a[i];
/*  457 */         a[i] = a[m];
/*  458 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(long[] a, int from, int to, LongComparator comp) {
/*  463 */     for (int i = from; ++i < to; ) {
/*  464 */       long t = a[i];
/*  465 */       int j = i; long u;
/*  466 */       for (u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  467 */         a[j] = u;
/*  468 */         if (from == j - 1) {
/*  469 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  473 */       a[j] = t;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long[] x, int from, int to, LongComparator comp) {
/*  501 */     int len = to - from;
/*      */     
/*  503 */     if (len < 16) {
/*  504 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  508 */     int m = from + len / 2;
/*  509 */     int l = from;
/*  510 */     int n = to - 1;
/*  511 */     if (len > 128) {
/*  512 */       int i = len / 8;
/*  513 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  514 */       m = med3(x, m - i, m, m + i, comp);
/*  515 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  517 */     m = med3(x, l, m, n, comp);
/*  518 */     long v = x[m];
/*      */     
/*  520 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  523 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  524 */         if (comparison == 0)
/*  525 */           swap(x, a++, b); 
/*  526 */         b++; continue;
/*      */       } 
/*  528 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  529 */         if (comparison == 0)
/*  530 */           swap(x, c, d--); 
/*  531 */         c--;
/*      */       } 
/*  533 */       if (b > c)
/*      */         break; 
/*  535 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  539 */     int s = Math.min(a - from, b - a);
/*  540 */     swap(x, from, b - s, s);
/*  541 */     s = Math.min(d - c, to - d - 1);
/*  542 */     swap(x, b, to - s, s);
/*      */     
/*  544 */     if ((s = b - a) > 1)
/*  545 */       quickSort(x, from, from + s, comp); 
/*  546 */     if ((s = d - c) > 1) {
/*  547 */       quickSort(x, to - s, to, comp);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long[] x, LongComparator comp) {
/*  570 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final long[] x;
/*      */     private final LongComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(long[] x, int from, int to, LongComparator comp) {
/*  579 */       this.from = from;
/*  580 */       this.to = to;
/*  581 */       this.x = x;
/*  582 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  586 */       long[] x = this.x;
/*  587 */       int len = this.to - this.from;
/*  588 */       if (len < 8192) {
/*  589 */         LongArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  593 */       int m = this.from + len / 2;
/*  594 */       int l = this.from;
/*  595 */       int n = this.to - 1;
/*  596 */       int s = len / 8;
/*  597 */       l = LongArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  598 */       m = LongArrays.med3(x, m - s, m, m + s, this.comp);
/*  599 */       n = LongArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  600 */       m = LongArrays.med3(x, l, m, n, this.comp);
/*  601 */       long v = x[m];
/*      */       
/*  603 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  606 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  607 */           if (comparison == 0)
/*  608 */             LongArrays.swap(x, a++, b); 
/*  609 */           b++; continue;
/*      */         } 
/*  611 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  612 */           if (comparison == 0)
/*  613 */             LongArrays.swap(x, c, d--); 
/*  614 */           c--;
/*      */         } 
/*  616 */         if (b > c)
/*      */           break; 
/*  618 */         LongArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  622 */       s = Math.min(a - this.from, b - a);
/*  623 */       LongArrays.swap(x, this.from, b - s, s);
/*  624 */       s = Math.min(d - c, this.to - d - 1);
/*  625 */       LongArrays.swap(x, b, this.to - s, s);
/*      */       
/*  627 */       s = b - a;
/*  628 */       int t = d - c;
/*  629 */       if (s > 1 && t > 1) {
/*  630 */         invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp));
/*      */       }
/*  632 */       else if (s > 1) {
/*  633 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) });
/*      */       } else {
/*  635 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) });
/*      */       } 
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(long[] x, int from, int to, LongComparator comp) {
/*  661 */     if (to - from < 8192) {
/*  662 */       quickSort(x, from, to, comp);
/*      */     } else {
/*  664 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  665 */       pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp));
/*  666 */       pool.shutdown();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(long[] x, LongComparator comp) {
/*  688 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(long[] x, int a, int b, int c) {
/*  692 */     int ab = Long.compare(x[a], x[b]);
/*  693 */     int ac = Long.compare(x[a], x[c]);
/*  694 */     int bc = Long.compare(x[b], x[c]);
/*  695 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(long[] a, int from, int to) {
/*  699 */     for (int i = from; i < to - 1; i++) {
/*  700 */       int m = i;
/*  701 */       for (int j = i + 1; j < to; j++) {
/*  702 */         if (a[j] < a[m])
/*  703 */           m = j; 
/*  704 */       }  if (m != i) {
/*  705 */         long u = a[i];
/*  706 */         a[i] = a[m];
/*  707 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(long[] a, int from, int to) {
/*  713 */     for (int i = from; ++i < to; ) {
/*  714 */       long t = a[i];
/*  715 */       int j = i; long u;
/*  716 */       for (u = a[j - 1]; t < u; u = a[--j - 1]) {
/*  717 */         a[j] = u;
/*  718 */         if (from == j - 1) {
/*  719 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  723 */       a[j] = t;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long[] x, int from, int to) {
/*  749 */     int len = to - from;
/*      */     
/*  751 */     if (len < 16) {
/*  752 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  756 */     int m = from + len / 2;
/*  757 */     int l = from;
/*  758 */     int n = to - 1;
/*  759 */     if (len > 128) {
/*  760 */       int i = len / 8;
/*  761 */       l = med3(x, l, l + i, l + 2 * i);
/*  762 */       m = med3(x, m - i, m, m + i);
/*  763 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  765 */     m = med3(x, l, m, n);
/*  766 */     long v = x[m];
/*      */     
/*  768 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  771 */       if (b <= c && (comparison = Long.compare(x[b], v)) <= 0) {
/*  772 */         if (comparison == 0)
/*  773 */           swap(x, a++, b); 
/*  774 */         b++; continue;
/*      */       } 
/*  776 */       while (c >= b && (comparison = Long.compare(x[c], v)) >= 0) {
/*  777 */         if (comparison == 0)
/*  778 */           swap(x, c, d--); 
/*  779 */         c--;
/*      */       } 
/*  781 */       if (b > c)
/*      */         break; 
/*  783 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  787 */     int s = Math.min(a - from, b - a);
/*  788 */     swap(x, from, b - s, s);
/*  789 */     s = Math.min(d - c, to - d - 1);
/*  790 */     swap(x, b, to - s, s);
/*      */     
/*  792 */     if ((s = b - a) > 1)
/*  793 */       quickSort(x, from, from + s); 
/*  794 */     if ((s = d - c) > 1) {
/*  795 */       quickSort(x, to - s, to);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long[] x) {
/*  815 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final long[] x;
/*      */     
/*      */     public ForkJoinQuickSort(long[] x, int from, int to) {
/*  823 */       this.from = from;
/*  824 */       this.to = to;
/*  825 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  830 */       long[] x = this.x;
/*  831 */       int len = this.to - this.from;
/*  832 */       if (len < 8192) {
/*  833 */         LongArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  837 */       int m = this.from + len / 2;
/*  838 */       int l = this.from;
/*  839 */       int n = this.to - 1;
/*  840 */       int s = len / 8;
/*  841 */       l = LongArrays.med3(x, l, l + s, l + 2 * s);
/*  842 */       m = LongArrays.med3(x, m - s, m, m + s);
/*  843 */       n = LongArrays.med3(x, n - 2 * s, n - s, n);
/*  844 */       m = LongArrays.med3(x, l, m, n);
/*  845 */       long v = x[m];
/*      */       
/*  847 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  850 */         if (b <= c && (comparison = Long.compare(x[b], v)) <= 0) {
/*  851 */           if (comparison == 0)
/*  852 */             LongArrays.swap(x, a++, b); 
/*  853 */           b++; continue;
/*      */         } 
/*  855 */         while (c >= b && (comparison = Long.compare(x[c], v)) >= 0) {
/*  856 */           if (comparison == 0)
/*  857 */             LongArrays.swap(x, c, d--); 
/*  858 */           c--;
/*      */         } 
/*  860 */         if (b > c)
/*      */           break; 
/*  862 */         LongArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  866 */       s = Math.min(a - this.from, b - a);
/*  867 */       LongArrays.swap(x, this.from, b - s, s);
/*  868 */       s = Math.min(d - c, this.to - d - 1);
/*  869 */       LongArrays.swap(x, b, this.to - s, s);
/*      */       
/*  871 */       s = b - a;
/*  872 */       int t = d - c;
/*  873 */       if (s > 1 && t > 1) {
/*  874 */         invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to));
/*  875 */       } else if (s > 1) {
/*  876 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) });
/*      */       } else {
/*  878 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) });
/*      */       } 
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(long[] x, int from, int to) {
/*  902 */     if (to - from < 8192) {
/*  903 */       quickSort(x, from, to);
/*      */     } else {
/*  905 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  906 */       pool.invoke(new ForkJoinQuickSort(x, from, to));
/*  907 */       pool.shutdown();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(long[] x) {
/*  928 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, long[] x, int a, int b, int c) {
/*  932 */     long aa = x[perm[a]];
/*  933 */     long bb = x[perm[b]];
/*  934 */     long cc = x[perm[c]];
/*  935 */     int ab = Long.compare(aa, bb);
/*  936 */     int ac = Long.compare(aa, cc);
/*  937 */     int bc = Long.compare(bb, cc);
/*  938 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, long[] a, int from, int to) {
/*  942 */     for (int i = from; ++i < to; ) {
/*  943 */       int t = perm[i];
/*  944 */       int j = i; int u;
/*  945 */       for (u = perm[j - 1]; a[t] < a[u]; u = perm[--j - 1]) {
/*  946 */         perm[j] = u;
/*  947 */         if (from == j - 1) {
/*  948 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  952 */       perm[j] = t;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSortIndirect(int[] perm, long[] x, int from, int to) {
/*  985 */     int len = to - from;
/*      */     
/*  987 */     if (len < 16) {
/*  988 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  992 */     int m = from + len / 2;
/*  993 */     int l = from;
/*  994 */     int n = to - 1;
/*  995 */     if (len > 128) {
/*  996 */       int i = len / 8;
/*  997 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/*  998 */       m = med3Indirect(perm, x, m - i, m, m + i);
/*  999 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/* 1001 */     m = med3Indirect(perm, x, l, m, n);
/* 1002 */     long v = x[perm[m]];
/*      */     
/* 1004 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1007 */       if (b <= c && (comparison = Long.compare(x[perm[b]], v)) <= 0) {
/* 1008 */         if (comparison == 0)
/* 1009 */           IntArrays.swap(perm, a++, b); 
/* 1010 */         b++; continue;
/*      */       } 
/* 1012 */       while (c >= b && (comparison = Long.compare(x[perm[c]], v)) >= 0) {
/* 1013 */         if (comparison == 0)
/* 1014 */           IntArrays.swap(perm, c, d--); 
/* 1015 */         c--;
/*      */       } 
/* 1017 */       if (b > c)
/*      */         break; 
/* 1019 */       IntArrays.swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1023 */     int s = Math.min(a - from, b - a);
/* 1024 */     IntArrays.swap(perm, from, b - s, s);
/* 1025 */     s = Math.min(d - c, to - d - 1);
/* 1026 */     IntArrays.swap(perm, b, to - s, s);
/*      */     
/* 1028 */     if ((s = b - a) > 1)
/* 1029 */       quickSortIndirect(perm, x, from, from + s); 
/* 1030 */     if ((s = d - c) > 1) {
/* 1031 */       quickSortIndirect(perm, x, to - s, to);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSortIndirect(int[] perm, long[] x) {
/* 1058 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final long[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, long[] x, int from, int to) {
/* 1067 */       this.from = from;
/* 1068 */       this.to = to;
/* 1069 */       this.x = x;
/* 1070 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1075 */       long[] x = this.x;
/* 1076 */       int len = this.to - this.from;
/* 1077 */       if (len < 8192) {
/* 1078 */         LongArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1082 */       int m = this.from + len / 2;
/* 1083 */       int l = this.from;
/* 1084 */       int n = this.to - 1;
/* 1085 */       int s = len / 8;
/* 1086 */       l = LongArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1087 */       m = LongArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1088 */       n = LongArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1089 */       m = LongArrays.med3Indirect(this.perm, x, l, m, n);
/* 1090 */       long v = x[this.perm[m]];
/*      */       
/* 1092 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1095 */         if (b <= c && (comparison = Long.compare(x[this.perm[b]], v)) <= 0) {
/* 1096 */           if (comparison == 0)
/* 1097 */             IntArrays.swap(this.perm, a++, b); 
/* 1098 */           b++; continue;
/*      */         } 
/* 1100 */         while (c >= b && (comparison = Long.compare(x[this.perm[c]], v)) >= 0) {
/* 1101 */           if (comparison == 0)
/* 1102 */             IntArrays.swap(this.perm, c, d--); 
/* 1103 */           c--;
/*      */         } 
/* 1105 */         if (b > c)
/*      */           break; 
/* 1107 */         IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1111 */       s = Math.min(a - this.from, b - a);
/* 1112 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1113 */       s = Math.min(d - c, this.to - d - 1);
/* 1114 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1116 */       s = b - a;
/* 1117 */       int t = d - c;
/* 1118 */       if (s > 1 && t > 1) {
/* 1119 */         invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to));
/*      */       }
/* 1121 */       else if (s > 1) {
/* 1122 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) });
/*      */       } else {
/* 1124 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) });
/*      */       } 
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSortIndirect(int[] perm, long[] x, int from, int to) {
/* 1155 */     if (to - from < 8192) {
/* 1156 */       quickSortIndirect(perm, x, from, to);
/*      */     } else {
/* 1158 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1159 */       pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to));
/* 1160 */       pool.shutdown();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSortIndirect(int[] perm, long[] x) {
/* 1188 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stabilize(int[] perm, long[] x, int from, int to) {
/* 1221 */     int curr = from;
/* 1222 */     for (int i = from + 1; i < to; i++) {
/* 1223 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1224 */         if (i - curr > 1)
/* 1225 */           IntArrays.parallelQuickSort(perm, curr, i); 
/* 1226 */         curr = i;
/*      */       } 
/*      */     } 
/* 1229 */     if (to - curr > 1) {
/* 1230 */       IntArrays.parallelQuickSort(perm, curr, to);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stabilize(int[] perm, long[] x) {
/* 1259 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(long[] x, long[] y, int a, int b, int c) {
/* 1264 */     int t, ab = ((t = Long.compare(x[a], x[b])) == 0) ? Long.compare(y[a], y[b]) : t;
/* 1265 */     int ac = ((t = Long.compare(x[a], x[c])) == 0) ? Long.compare(y[a], y[c]) : t;
/* 1266 */     int bc = ((t = Long.compare(x[b], x[c])) == 0) ? Long.compare(y[b], y[c]) : t;
/* 1267 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(long[] x, long[] y, int a, int b) {
/* 1270 */     long t = x[a];
/* 1271 */     long u = y[a];
/* 1272 */     x[a] = x[b];
/* 1273 */     y[a] = y[b];
/* 1274 */     x[b] = t;
/* 1275 */     y[b] = u;
/*      */   }
/*      */   private static void swap(long[] x, long[] y, int a, int b, int n) {
/* 1278 */     for (int i = 0; i < n; i++, a++, b++)
/* 1279 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(long[] a, long[] b, int from, int to) {
/* 1283 */     for (int i = from; i < to - 1; i++) {
/* 1284 */       int m = i;
/* 1285 */       for (int j = i + 1; j < to; j++) {
/* 1286 */         int u; if ((u = Long.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m]))
/* 1287 */           m = j; 
/* 1288 */       }  if (m != i) {
/* 1289 */         long t = a[i];
/* 1290 */         a[i] = a[m];
/* 1291 */         a[m] = t;
/* 1292 */         t = b[i];
/* 1293 */         b[i] = b[m];
/* 1294 */         b[m] = t;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long[] x, long[] y, int from, int to) {
/* 1325 */     int len = to - from;
/* 1326 */     if (len < 16) {
/* 1327 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1331 */     int m = from + len / 2;
/* 1332 */     int l = from;
/* 1333 */     int n = to - 1;
/* 1334 */     if (len > 128) {
/* 1335 */       int i = len / 8;
/* 1336 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1337 */       m = med3(x, y, m - i, m, m + i);
/* 1338 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1340 */     m = med3(x, y, l, m, n);
/* 1341 */     long v = x[m], w = y[m];
/*      */     
/* 1343 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1346 */       if (b <= c) {
/* 1347 */         int comparison; int t; if ((comparison = ((t = Long.compare(x[b], v)) == 0) ? Long.compare(y[b], w) : t) <= 0) {
/* 1348 */           if (comparison == 0)
/* 1349 */             swap(x, y, a++, b); 
/* 1350 */           b++; continue;
/*      */         } 
/* 1352 */       }  while (c >= b) {
/* 1353 */         int comparison; int t; if ((comparison = ((t = Long.compare(x[c], v)) == 0) ? Long.compare(y[c], w) : t) >= 0) {
/* 1354 */           if (comparison == 0)
/* 1355 */             swap(x, y, c, d--); 
/* 1356 */           c--;
/*      */         } 
/* 1358 */       }  if (b > c)
/*      */         break; 
/* 1360 */       swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1364 */     int s = Math.min(a - from, b - a);
/* 1365 */     swap(x, y, from, b - s, s);
/* 1366 */     s = Math.min(d - c, to - d - 1);
/* 1367 */     swap(x, y, b, to - s, s);
/*      */     
/* 1369 */     if ((s = b - a) > 1)
/* 1370 */       quickSort(x, y, from, from + s); 
/* 1371 */     if ((s = d - c) > 1) {
/* 1372 */       quickSort(x, y, to - s, to);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void quickSort(long[] x, long[] y) {
/* 1396 */     ensureSameLength(x, y);
/* 1397 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L; private final int from;
/*      */     private final int to;
/*      */     private final long[] x;
/*      */     private final long[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(long[] x, long[] y, int from, int to) {
/* 1405 */       this.from = from;
/* 1406 */       this.to = to;
/* 1407 */       this.x = x;
/* 1408 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1413 */       long[] x = this.x;
/* 1414 */       long[] y = this.y;
/* 1415 */       int len = this.to - this.from;
/* 1416 */       if (len < 8192) {
/* 1417 */         LongArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1421 */       int m = this.from + len / 2;
/* 1422 */       int l = this.from;
/* 1423 */       int n = this.to - 1;
/* 1424 */       int s = len / 8;
/* 1425 */       l = LongArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1426 */       m = LongArrays.med3(x, y, m - s, m, m + s);
/* 1427 */       n = LongArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1428 */       m = LongArrays.med3(x, y, l, m, n);
/* 1429 */       long v = x[m], w = y[m];
/*      */       
/* 1431 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1434 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1436 */           if ((comparison = ((i = Long.compare(x[b], v)) == 0) ? Long.compare(y[b], w) : i) <= 0) {
/* 1437 */             if (comparison == 0)
/* 1438 */               LongArrays.swap(x, y, a++, b); 
/* 1439 */             b++; continue;
/*      */           } 
/* 1441 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1443 */           if ((comparison = ((i = Long.compare(x[c], v)) == 0) ? Long.compare(y[c], w) : i) >= 0) {
/* 1444 */             if (comparison == 0)
/* 1445 */               LongArrays.swap(x, y, c, d--); 
/* 1446 */             c--;
/*      */           } 
/* 1448 */         }  if (b > c)
/*      */           break; 
/* 1450 */         LongArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1454 */       s = Math.min(a - this.from, b - a);
/* 1455 */       LongArrays.swap(x, y, this.from, b - s, s);
/* 1456 */       s = Math.min(d - c, this.to - d - 1);
/* 1457 */       LongArrays.swap(x, y, b, this.to - s, s);
/* 1458 */       s = b - a;
/* 1459 */       int t = d - c;
/*      */       
/* 1461 */       if (s > 1 && t > 1) {
/* 1462 */         invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to));
/* 1463 */       } else if (s > 1) {
/* 1464 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) });
/*      */       } else {
/* 1466 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) });
/*      */       } 
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(long[] x, long[] y, int from, int to) {
/* 1499 */     if (to - from < 8192)
/* 1500 */       quickSort(x, y, from, to); 
/* 1501 */     ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1502 */     pool.invoke(new ForkJoinQuickSort2(x, y, from, to));
/* 1503 */     pool.shutdown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(long[] x, long[] y) {
/* 1531 */     ensureSameLength(x, y);
/* 1532 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(long[] a, int from, int to) {
/* 1552 */     if (to - from >= 2000) {
/* 1553 */       radixSort(a, from, to);
/*      */     } else {
/* 1555 */       quickSort(a, from, to);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(long[] a) {
/* 1569 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(long[] a, int from, int to, LongComparator comp) {
/* 1588 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(long[] a, LongComparator comp) {
/* 1602 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long[] a, int from, int to, long[] supp) {
/* 1626 */     int len = to - from;
/*      */     
/* 1628 */     if (len < 16) {
/* 1629 */       insertionSort(a, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1633 */     int mid = from + to >>> 1;
/* 1634 */     mergeSort(supp, from, mid, a);
/* 1635 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1638 */     if (supp[mid - 1] <= supp[mid]) {
/* 1639 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1643 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1644 */       if (q >= to || (p < mid && supp[p] <= supp[q])) {
/* 1645 */         a[i] = supp[p++];
/*      */       } else {
/* 1647 */         a[i] = supp[q++];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long[] a, int from, int to) {
/* 1667 */     mergeSort(a, from, to, (long[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long[] a) {
/* 1681 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long[] a, int from, int to, LongComparator comp, long[] supp) {
/* 1706 */     int len = to - from;
/*      */     
/* 1708 */     if (len < 16) {
/* 1709 */       insertionSort(a, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/* 1713 */     int mid = from + to >>> 1;
/* 1714 */     mergeSort(supp, from, mid, comp, a);
/* 1715 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1718 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1719 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1723 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1724 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
/* 1725 */         a[i] = supp[p++];
/*      */       } else {
/* 1727 */         a[i] = supp[q++];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long[] a, int from, int to, LongComparator comp) {
/* 1749 */     mergeSort(a, from, to, comp, (long[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(long[] a, LongComparator comp) {
/* 1766 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(long[] a, int from, int to) {
/* 1791 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(long[] a) {
/* 1809 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(long[] a, int from, int to, LongComparator comp) {
/* 1833 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(long[] a, LongComparator comp) {
/* 1853 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(long[] a, int from, int to, long key) {
/* 1882 */     to--;
/* 1883 */     while (from <= to) {
/* 1884 */       int mid = from + to >>> 1;
/* 1885 */       long midVal = a[mid];
/* 1886 */       if (midVal < key) {
/* 1887 */         from = mid + 1; continue;
/* 1888 */       }  if (midVal > key) {
/* 1889 */         to = mid - 1; continue;
/*      */       } 
/* 1891 */       return mid;
/*      */     } 
/* 1893 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(long[] a, long key) {
/* 1915 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(long[] a, int from, int to, long key, LongComparator c) {
/* 1945 */     to--;
/* 1946 */     while (from <= to) {
/* 1947 */       int mid = from + to >>> 1;
/* 1948 */       long midVal = a[mid];
/* 1949 */       int cmp = c.compare(midVal, key);
/* 1950 */       if (cmp < 0) {
/* 1951 */         from = mid + 1; continue;
/* 1952 */       }  if (cmp > 0) {
/* 1953 */         to = mid - 1; continue;
/*      */       } 
/* 1955 */       return mid;
/*      */     } 
/* 1957 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(long[] a, long key, LongComparator c) {
/* 1982 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(long[] a) {
/* 2018 */     radixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(long[] a, int from, int to) {
/* 2041 */     if (to - from < 1024) {
/* 2042 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2045 */     int maxLevel = 7;
/* 2046 */     int stackSize = 1786;
/* 2047 */     int stackPos = 0;
/* 2048 */     int[] offsetStack = new int[1786];
/* 2049 */     int[] lengthStack = new int[1786];
/* 2050 */     int[] levelStack = new int[1786];
/* 2051 */     offsetStack[stackPos] = from;
/* 2052 */     lengthStack[stackPos] = to - from;
/* 2053 */     levelStack[stackPos++] = 0;
/* 2054 */     int[] count = new int[256];
/* 2055 */     int[] pos = new int[256];
/* 2056 */     while (stackPos > 0) {
/* 2057 */       int first = offsetStack[--stackPos];
/* 2058 */       int length = lengthStack[stackPos];
/* 2059 */       int level = levelStack[stackPos];
/* 2060 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2061 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2066 */       for (int i = first + length; i-- != first;) {
/* 2067 */         count[(int)(a[i] >>> shift & 0xFFL ^ signMask)] = count[(int)(a[i] >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2069 */       int lastUsed = -1;
/* 2070 */       for (int j = 0, p = first; j < 256; j++) {
/* 2071 */         if (count[j] != 0)
/* 2072 */           lastUsed = j; 
/* 2073 */         pos[j] = p += count[j];
/*      */       } 
/* 2075 */       int end = first + length - count[lastUsed];
/*      */       
/* 2077 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2078 */         long t = a[k];
/* 2079 */         c = (int)(t >>> shift & 0xFFL ^ signMask);
/* 2080 */         if (k < end) {
/* 2081 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2082 */             long z = t;
/* 2083 */             t = a[d];
/* 2084 */             a[d] = z;
/* 2085 */             c = (int)(t >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2087 */           a[k] = t;
/*      */         } 
/* 2089 */         if (level < 7 && count[c] > 1)
/* 2090 */           if (count[c] < 1024) {
/* 2091 */             quickSort(a, k, k + count[c]);
/*      */           } else {
/* 2093 */             offsetStack[stackPos] = k;
/* 2094 */             lengthStack[stackPos] = count[c];
/* 2095 */             levelStack[stackPos++] = level + 1;
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected static final class Segment { protected final int offset; protected final int length;
/*      */     protected final int level;
/*      */     
/*      */     protected Segment(int offset, int length, int level) {
/* 2104 */       this.offset = offset;
/* 2105 */       this.length = length;
/* 2106 */       this.level = level;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2110 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */   
/* 2113 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(long[] a, int from, int to) {
/* 2134 */     if (to - from < 1024) {
/* 2135 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2138 */     int maxLevel = 7;
/* 2139 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2140 */     queue.add(new Segment(from, to - from, 0));
/* 2141 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2142 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2143 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2144 */         Executors.defaultThreadFactory());
/* 2145 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2147 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2148 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int m = numberOfThreads; while (m-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               } 
/*      */               Segment segment = queue.take();
/*      */               if (segment == POISON_PILL)
/*      */                 return null; 
/*      */               int first = segment.offset;
/*      */               int length = segment.length;
/*      */               int level = segment.level;
/*      */               int signMask = (level % 8 == 0) ? 128 : 0;
/*      */               int shift = (7 - level % 8) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[(int)(a[i] >>> shift & 0xFFL ^ signMask)] = count[(int)(a[i] >>> shift & 0xFFL ^ signMask)] + 1; 
/*      */               int lastUsed = -1;
/*      */               int j = 0;
/*      */               int p = first;
/*      */               while (j < 256) {
/*      */                 if (count[j] != 0)
/*      */                   lastUsed = j; 
/*      */                 pos[j] = p += count[j];
/*      */                 j++;
/*      */               } 
/*      */               int end = first + length - count[lastUsed];
/*      */               int k = first;
/*      */               int c = -1;
/*      */               while (k <= end) {
/*      */                 long t = a[k];
/*      */                 c = (int)(t >>> shift & 0xFFL ^ signMask);
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     long z = t;
/*      */                     t = a[d];
/*      */                     a[d] = z;
/*      */                     c = (int)(t >>> shift & 0xFFL ^ signMask);
/*      */                   } 
/*      */                   a[k] = t;
/*      */                 } 
/*      */                 if (level < 7 && count[c] > 1)
/*      */                   if (count[c] < 1024) {
/*      */                     quickSort(a, k, k + count[c]);
/*      */                   } else {
/*      */                     queueSize.incrementAndGet();
/*      */                     queue.add(new Segment(k, count[c], level + 1));
/*      */                   }  
/*      */                 k += count[c];
/*      */                 count[c] = 0;
/*      */               } 
/*      */               queueSize.decrementAndGet();
/*      */             } 
/*      */           });
/*      */     } 
/* 2205 */     Throwable problem = null;
/* 2206 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2208 */         executorCompletionService.take().get();
/* 2209 */       } catch (Exception e) {
/* 2210 */         problem = e.getCause();
/*      */       } 
/* 2212 */     }  executorService.shutdown();
/* 2213 */     if (problem != null) {
/* 2214 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(long[] a) {
/* 2232 */     parallelRadixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, long[] a, boolean stable) {
/* 2259 */     radixSortIndirect(perm, a, 0, perm.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, long[] a, int from, int to, boolean stable) {
/* 2293 */     if (to - from < 1024) {
/* 2294 */       insertionSortIndirect(perm, a, from, to);
/*      */       return;
/*      */     } 
/* 2297 */     int maxLevel = 7;
/* 2298 */     int stackSize = 1786;
/* 2299 */     int stackPos = 0;
/* 2300 */     int[] offsetStack = new int[1786];
/* 2301 */     int[] lengthStack = new int[1786];
/* 2302 */     int[] levelStack = new int[1786];
/* 2303 */     offsetStack[stackPos] = from;
/* 2304 */     lengthStack[stackPos] = to - from;
/* 2305 */     levelStack[stackPos++] = 0;
/* 2306 */     int[] count = new int[256];
/* 2307 */     int[] pos = new int[256];
/* 2308 */     int[] support = stable ? new int[perm.length] : null;
/* 2309 */     while (stackPos > 0) {
/* 2310 */       int first = offsetStack[--stackPos];
/* 2311 */       int length = lengthStack[stackPos];
/* 2312 */       int level = levelStack[stackPos];
/* 2313 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2314 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2319 */       for (int i = first + length; i-- != first;) {
/* 2320 */         count[(int)(a[perm[i]] >>> shift & 0xFFL ^ signMask)] = count[(int)(a[perm[i]] >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2322 */       int lastUsed = -1; int j, p;
/* 2323 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2324 */         if (count[j] != 0)
/* 2325 */           lastUsed = j; 
/* 2326 */         pos[j] = p += count[j];
/*      */       } 
/* 2328 */       if (stable) {
/* 2329 */         for (j = first + length; j-- != first; ) {
/* 2330 */           pos[(int)(a[perm[j]] >>> shift & 0xFFL ^ signMask)] = pos[(int)(a[perm[j]] >>> shift & 0xFFL ^ signMask)] - 1; support[pos[(int)(a[perm[j]] >>> shift & 0xFFL ^ signMask)] - 1] = perm[j];
/* 2331 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2332 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2333 */           if (level < 7 && count[j] > 1) {
/* 2334 */             if (count[j] < 1024) {
/* 2335 */               insertionSortIndirect(perm, a, p, p + count[j]);
/*      */             } else {
/* 2337 */               offsetStack[stackPos] = p;
/* 2338 */               lengthStack[stackPos] = count[j];
/* 2339 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2342 */           p += count[j];
/*      */         } 
/* 2344 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2346 */       int end = first + length - count[lastUsed];
/*      */       
/* 2348 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2349 */         int t = perm[k];
/* 2350 */         c = (int)(a[t] >>> shift & 0xFFL ^ signMask);
/* 2351 */         if (k < end) {
/* 2352 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2353 */             int z = t;
/* 2354 */             t = perm[d];
/* 2355 */             perm[d] = z;
/* 2356 */             c = (int)(a[t] >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2358 */           perm[k] = t;
/*      */         } 
/* 2360 */         if (level < 7 && count[c] > 1) {
/* 2361 */           if (count[c] < 1024) {
/* 2362 */             insertionSortIndirect(perm, a, k, k + count[c]);
/*      */           } else {
/* 2364 */             offsetStack[stackPos] = k;
/* 2365 */             lengthStack[stackPos] = count[c];
/* 2366 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSortIndirect(int[] perm, long[] a, int from, int to, boolean stable) {
/* 2403 */     if (to - from < 1024) {
/* 2404 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2407 */     int maxLevel = 7;
/* 2408 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2409 */     queue.add(new Segment(from, to - from, 0));
/* 2410 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2411 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2412 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2413 */         Executors.defaultThreadFactory());
/* 2414 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2416 */     int[] support = stable ? new int[perm.length] : null;
/* 2417 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2418 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int k = numberOfThreads; while (k-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level;
/*      */               int signMask = (level % 8 == 0) ? 128 : 0;
/*      */               int shift = (7 - level % 8) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[(int)(a[perm[i]] >>> shift & 0xFFL ^ signMask)] = count[(int)(a[perm[i]] >>> shift & 0xFFL ^ signMask)] + 1; 
/*      */               int lastUsed = -1;
/*      */               int j = 0;
/*      */               int p = first;
/*      */               while (j < 256) {
/*      */                 if (count[j] != 0)
/*      */                   lastUsed = j; 
/*      */                 pos[j] = p += count[j];
/*      */                 j++;
/*      */               } 
/*      */               if (stable) {
/*      */                 j = first + length;
/*      */                 while (j-- != first) {
/*      */                   pos[(int)(a[perm[j]] >>> shift & 0xFFL ^ signMask)] = pos[(int)(a[perm[j]] >>> shift & 0xFFL ^ signMask)] - 1;
/*      */                   support[pos[(int)(a[perm[j]] >>> shift & 0xFFL ^ signMask)] - 1] = perm[j];
/*      */                 } 
/*      */                 System.arraycopy(support, first, perm, first, length);
/*      */                 j = 0;
/*      */                 p = first;
/*      */                 while (j <= lastUsed) {
/*      */                   if (level < 7 && count[j] > 1)
/*      */                     if (count[j] < 1024) {
/*      */                       radixSortIndirect(perm, a, p, p + count[j], stable);
/*      */                     } else {
/*      */                       queueSize.incrementAndGet();
/*      */                       queue.add(new Segment(p, count[j], level + 1));
/*      */                     }  
/*      */                   p += count[j];
/*      */                   j++;
/*      */                 } 
/*      */                 Arrays.fill(count, 0);
/*      */               } else {
/*      */                 int end = first + length - count[lastUsed];
/*      */                 int k = first;
/*      */                 int c = -1;
/*      */                 while (k <= end) {
/*      */                   int t = perm[k];
/*      */                   c = (int)(a[t] >>> shift & 0xFFL ^ signMask);
/*      */                   if (k < end) {
/*      */                     pos[c] = pos[c] - 1;
/*      */                     int d;
/*      */                     while ((d = pos[c] - 1) > k) {
/*      */                       int z = t;
/*      */                       t = perm[d];
/*      */                       perm[d] = z;
/*      */                       c = (int)(a[t] >>> shift & 0xFFL ^ signMask);
/*      */                     } 
/*      */                     perm[k] = t;
/*      */                   } 
/*      */                   if (level < 7 && count[c] > 1)
/*      */                     if (count[c] < 1024) {
/*      */                       radixSortIndirect(perm, a, k, k + count[c], stable);
/*      */                     } else {
/*      */                       queueSize.incrementAndGet();
/*      */                       queue.add(new Segment(k, count[c], level + 1));
/*      */                     }  
/*      */                   k += count[c];
/*      */                   count[c] = 0;
/*      */                 } 
/*      */               } 
/*      */               queueSize.decrementAndGet();
/*      */             } 
/*      */           });
/*      */     } 
/* 2493 */     Throwable problem = null;
/* 2494 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2496 */         executorCompletionService.take().get();
/* 2497 */       } catch (Exception e) {
/* 2498 */         problem = e.getCause();
/*      */       } 
/* 2500 */     }  executorService.shutdown();
/* 2501 */     if (problem != null) {
/* 2502 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSortIndirect(int[] perm, long[] a, boolean stable) {
/* 2529 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(long[] a, long[] b) {
/* 2551 */     ensureSameLength(a, b);
/* 2552 */     radixSort(a, b, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(long[] a, long[] b, int from, int to) {
/* 2579 */     if (to - from < 1024) {
/* 2580 */       selectionSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2583 */     int layers = 2;
/* 2584 */     int maxLevel = 15;
/* 2585 */     int stackSize = 3826;
/* 2586 */     int stackPos = 0;
/* 2587 */     int[] offsetStack = new int[3826];
/* 2588 */     int[] lengthStack = new int[3826];
/* 2589 */     int[] levelStack = new int[3826];
/* 2590 */     offsetStack[stackPos] = from;
/* 2591 */     lengthStack[stackPos] = to - from;
/* 2592 */     levelStack[stackPos++] = 0;
/* 2593 */     int[] count = new int[256];
/* 2594 */     int[] pos = new int[256];
/* 2595 */     while (stackPos > 0) {
/* 2596 */       int first = offsetStack[--stackPos];
/* 2597 */       int length = lengthStack[stackPos];
/* 2598 */       int level = levelStack[stackPos];
/* 2599 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2600 */       long[] k = (level < 8) ? a : b;
/* 2601 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2606 */       for (int i = first + length; i-- != first;) {
/* 2607 */         count[(int)(k[i] >>> shift & 0xFFL ^ signMask)] = count[(int)(k[i] >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2609 */       int lastUsed = -1;
/* 2610 */       for (int j = 0, p = first; j < 256; j++) {
/* 2611 */         if (count[j] != 0)
/* 2612 */           lastUsed = j; 
/* 2613 */         pos[j] = p += count[j];
/*      */       } 
/* 2615 */       int end = first + length - count[lastUsed];
/*      */       
/* 2617 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2618 */         long t = a[m];
/* 2619 */         long u = b[m];
/* 2620 */         c = (int)(k[m] >>> shift & 0xFFL ^ signMask);
/* 2621 */         if (m < end) {
/* 2622 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2623 */             c = (int)(k[d] >>> shift & 0xFFL ^ signMask);
/* 2624 */             long z = t;
/* 2625 */             t = a[d];
/* 2626 */             a[d] = z;
/* 2627 */             z = u;
/* 2628 */             u = b[d];
/* 2629 */             b[d] = z;
/*      */           } 
/* 2631 */           a[m] = t;
/* 2632 */           b[m] = u;
/*      */         } 
/* 2634 */         if (level < 15 && count[c] > 1) {
/* 2635 */           if (count[c] < 1024) {
/* 2636 */             selectionSort(a, b, m, m + count[c]);
/*      */           } else {
/* 2638 */             offsetStack[stackPos] = m;
/* 2639 */             lengthStack[stackPos] = count[c];
/* 2640 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(long[] a, long[] b, int from, int to) {
/* 2676 */     if (to - from < 1024) {
/* 2677 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2680 */     int layers = 2;
/* 2681 */     if (a.length != b.length)
/* 2682 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 2683 */     int maxLevel = 15;
/* 2684 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2685 */     queue.add(new Segment(from, to - from, 0));
/* 2686 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2687 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2688 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2689 */         Executors.defaultThreadFactory());
/* 2690 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2692 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2693 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int n = numberOfThreads; while (n-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 8 == 0) ? 128 : 0; long[] k = (level < 8) ? a : b;
/*      */               int shift = (7 - level % 8) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[(int)(k[i] >>> shift & 0xFFL ^ signMask)] = count[(int)(k[i] >>> shift & 0xFFL ^ signMask)] + 1; 
/*      */               int lastUsed = -1;
/*      */               int j = 0;
/*      */               int p = first;
/*      */               while (j < 256) {
/*      */                 if (count[j] != 0)
/*      */                   lastUsed = j; 
/*      */                 pos[j] = p += count[j];
/*      */                 j++;
/*      */               } 
/*      */               int end = first + length - count[lastUsed];
/*      */               int m = first;
/*      */               int c = -1;
/*      */               while (m <= end) {
/*      */                 long t = a[m];
/*      */                 long u = b[m];
/*      */                 c = (int)(k[m] >>> shift & 0xFFL ^ signMask);
/*      */                 if (m < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > m) {
/*      */                     c = (int)(k[d] >>> shift & 0xFFL ^ signMask);
/*      */                     long z = t;
/*      */                     long w = u;
/*      */                     t = a[d];
/*      */                     u = b[d];
/*      */                     a[d] = z;
/*      */                     b[d] = w;
/*      */                   } 
/*      */                   a[m] = t;
/*      */                   b[m] = u;
/*      */                 } 
/*      */                 if (level < 15 && count[c] > 1)
/*      */                   if (count[c] < 1024) {
/*      */                     quickSort(a, b, m, m + count[c]);
/*      */                   } else {
/*      */                     queueSize.incrementAndGet();
/*      */                     queue.add(new Segment(m, count[c], level + 1));
/*      */                   }  
/*      */                 m += count[c];
/*      */                 count[c] = 0;
/*      */               } 
/*      */               queueSize.decrementAndGet();
/*      */             } 
/*      */           });
/*      */     } 
/* 2749 */     Throwable problem = null;
/* 2750 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2752 */         executorCompletionService.take().get();
/* 2753 */       } catch (Exception e) {
/* 2754 */         problem = e.getCause();
/*      */       } 
/* 2756 */     }  executorService.shutdown();
/* 2757 */     if (problem != null) {
/* 2758 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(long[] a, long[] b) {
/* 2785 */     ensureSameLength(a, b);
/* 2786 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, long[] a, long[] b, int from, int to) {
/* 2790 */     for (int i = from; ++i < to; ) {
/* 2791 */       int t = perm[i];
/* 2792 */       int j = i; int u;
/* 2793 */       for (u = perm[j - 1]; a[t] < a[u] || (a[t] == a[u] && b[t] < b[u]); u = perm[--j - 1]) {
/* 2794 */         perm[j] = u;
/* 2795 */         if (from == j - 1) {
/* 2796 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2800 */       perm[j] = t;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, long[] a, long[] b, boolean stable) {
/* 2834 */     ensureSameLength(a, b);
/* 2835 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, long[] a, long[] b, int from, int to, boolean stable) {
/* 2875 */     if (to - from < 1024) {
/* 2876 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2879 */     int layers = 2;
/* 2880 */     int maxLevel = 15;
/* 2881 */     int stackSize = 3826;
/* 2882 */     int stackPos = 0;
/* 2883 */     int[] offsetStack = new int[3826];
/* 2884 */     int[] lengthStack = new int[3826];
/* 2885 */     int[] levelStack = new int[3826];
/* 2886 */     offsetStack[stackPos] = from;
/* 2887 */     lengthStack[stackPos] = to - from;
/* 2888 */     levelStack[stackPos++] = 0;
/* 2889 */     int[] count = new int[256];
/* 2890 */     int[] pos = new int[256];
/* 2891 */     int[] support = stable ? new int[perm.length] : null;
/* 2892 */     while (stackPos > 0) {
/* 2893 */       int first = offsetStack[--stackPos];
/* 2894 */       int length = lengthStack[stackPos];
/* 2895 */       int level = levelStack[stackPos];
/* 2896 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2897 */       long[] k = (level < 8) ? a : b;
/* 2898 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2903 */       for (int i = first + length; i-- != first;) {
/* 2904 */         count[(int)(k[perm[i]] >>> shift & 0xFFL ^ signMask)] = count[(int)(k[perm[i]] >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2906 */       int lastUsed = -1; int j, p;
/* 2907 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2908 */         if (count[j] != 0)
/* 2909 */           lastUsed = j; 
/* 2910 */         pos[j] = p += count[j];
/*      */       } 
/* 2912 */       if (stable) {
/* 2913 */         for (j = first + length; j-- != first; ) {
/* 2914 */           pos[(int)(k[perm[j]] >>> shift & 0xFFL ^ signMask)] = pos[(int)(k[perm[j]] >>> shift & 0xFFL ^ signMask)] - 1; support[pos[(int)(k[perm[j]] >>> shift & 0xFFL ^ signMask)] - 1] = perm[j];
/* 2915 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2916 */         for (j = 0, p = first; j < 256; j++) {
/* 2917 */           if (level < 15 && count[j] > 1) {
/* 2918 */             if (count[j] < 1024) {
/* 2919 */               insertionSortIndirect(perm, a, b, p, p + count[j]);
/*      */             } else {
/* 2921 */               offsetStack[stackPos] = p;
/* 2922 */               lengthStack[stackPos] = count[j];
/* 2923 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2926 */           p += count[j];
/*      */         } 
/* 2928 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2930 */       int end = first + length - count[lastUsed];
/*      */       
/* 2932 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2933 */         int t = perm[m];
/* 2934 */         c = (int)(k[t] >>> shift & 0xFFL ^ signMask);
/* 2935 */         if (m < end) {
/* 2936 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2937 */             int z = t;
/* 2938 */             t = perm[d];
/* 2939 */             perm[d] = z;
/* 2940 */             c = (int)(k[t] >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2942 */           perm[m] = t;
/*      */         } 
/* 2944 */         if (level < 15 && count[c] > 1) {
/* 2945 */           if (count[c] < 1024) {
/* 2946 */             insertionSortIndirect(perm, a, b, m, m + count[c]);
/*      */           } else {
/* 2948 */             offsetStack[stackPos] = m;
/* 2949 */             lengthStack[stackPos] = count[c];
/* 2950 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void selectionSort(long[][] a, int from, int to, int level) {
/* 2958 */     int layers = a.length;
/* 2959 */     int firstLayer = level / 8;
/* 2960 */     for (int i = from; i < to - 1; i++) {
/* 2961 */       int m = i;
/* 2962 */       for (int j = i + 1; j < to; j++) {
/* 2963 */         for (int p = firstLayer; p < layers; p++) {
/* 2964 */           if (a[p][j] < a[p][m]) {
/* 2965 */             m = j; break;
/*      */           } 
/* 2967 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/*      */       } 
/* 2971 */       if (m != i) {
/* 2972 */         for (int p = layers; p-- != 0; ) {
/* 2973 */           long u = a[p][i];
/* 2974 */           a[p][i] = a[p][m];
/* 2975 */           a[p][m] = u;
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(long[][] a) {
/* 2998 */     radixSort(a, 0, (a[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(long[][] a, int from, int to) {
/* 3022 */     if (to - from < 1024) {
/* 3023 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 3026 */     int layers = a.length;
/* 3027 */     int maxLevel = 8 * layers - 1;
/* 3028 */     for (int p = layers, l = (a[0]).length; p-- != 0;) {
/* 3029 */       if ((a[p]).length != l)
/* 3030 */         throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0."); 
/*      */     } 
/* 3032 */     int stackSize = 255 * (layers * 8 - 1) + 1;
/* 3033 */     int stackPos = 0;
/* 3034 */     int[] offsetStack = new int[stackSize];
/* 3035 */     int[] lengthStack = new int[stackSize];
/* 3036 */     int[] levelStack = new int[stackSize];
/* 3037 */     offsetStack[stackPos] = from;
/* 3038 */     lengthStack[stackPos] = to - from;
/* 3039 */     levelStack[stackPos++] = 0;
/* 3040 */     int[] count = new int[256];
/* 3041 */     int[] pos = new int[256];
/* 3042 */     long[] t = new long[layers];
/* 3043 */     while (stackPos > 0) {
/* 3044 */       int first = offsetStack[--stackPos];
/* 3045 */       int length = lengthStack[stackPos];
/* 3046 */       int level = levelStack[stackPos];
/* 3047 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 3048 */       long[] k = a[level / 8];
/* 3049 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3054 */       for (int i = first + length; i-- != first;) {
/* 3055 */         count[(int)(k[i] >>> shift & 0xFFL ^ signMask)] = count[(int)(k[i] >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 3057 */       int lastUsed = -1;
/* 3058 */       for (int j = 0, n = first; j < 256; j++) {
/* 3059 */         if (count[j] != 0)
/* 3060 */           lastUsed = j; 
/* 3061 */         pos[j] = n += count[j];
/*      */       } 
/* 3063 */       int end = first + length - count[lastUsed];
/*      */       
/* 3065 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 3066 */         int i1; for (i1 = layers; i1-- != 0;)
/* 3067 */           t[i1] = a[i1][m]; 
/* 3068 */         c = (int)(k[m] >>> shift & 0xFFL ^ signMask);
/* 3069 */         if (m < end) {
/* 3070 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 3071 */             c = (int)(k[d] >>> shift & 0xFFL ^ signMask);
/* 3072 */             for (i1 = layers; i1-- != 0; ) {
/* 3073 */               long u = t[i1];
/* 3074 */               t[i1] = a[i1][d];
/* 3075 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 3078 */           for (i1 = layers; i1-- != 0;)
/* 3079 */             a[i1][m] = t[i1]; 
/*      */         } 
/* 3081 */         if (level < maxLevel && count[c] > 1) {
/* 3082 */           if (count[c] < 1024) {
/* 3083 */             selectionSort(a, m, m + count[c], level + 1);
/*      */           } else {
/* 3085 */             offsetStack[stackPos] = m;
/* 3086 */             lengthStack[stackPos] = count[c];
/* 3087 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] shuffle(long[] a, int from, int to, Random random) {
/* 3108 */     for (int i = to - from; i-- != 0; ) {
/* 3109 */       int p = random.nextInt(i + 1);
/* 3110 */       long t = a[from + i];
/* 3111 */       a[from + i] = a[from + p];
/* 3112 */       a[from + p] = t;
/*      */     } 
/* 3114 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] shuffle(long[] a, Random random) {
/* 3127 */     for (int i = a.length; i-- != 0; ) {
/* 3128 */       int p = random.nextInt(i + 1);
/* 3129 */       long t = a[i];
/* 3130 */       a[i] = a[p];
/* 3131 */       a[p] = t;
/*      */     } 
/* 3133 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] reverse(long[] a) {
/* 3143 */     int length = a.length;
/* 3144 */     for (int i = length / 2; i-- != 0; ) {
/* 3145 */       long t = a[length - i - 1];
/* 3146 */       a[length - i - 1] = a[i];
/* 3147 */       a[i] = t;
/*      */     } 
/* 3149 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] reverse(long[] a, int from, int to) {
/* 3163 */     int length = to - from;
/* 3164 */     for (int i = length / 2; i-- != 0; ) {
/* 3165 */       long t = a[from + length - i - 1];
/* 3166 */       a[from + length - i - 1] = a[from + i];
/* 3167 */       a[from + i] = t;
/*      */     } 
/* 3169 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<long[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(long[] o) {
/* 3176 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(long[] a, long[] b) {
/* 3180 */       return Arrays.equals(a, b);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3191 */   public static final Hash.Strategy<long[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */