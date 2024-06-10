/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public final class DoubleArrays
/*      */ {
/*  103 */   public static final double[] EMPTY_ARRAY = new double[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final double[] DEFAULT_EMPTY_ARRAY = new double[0];
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
/*      */   public static double[] forceCapacity(double[] array, int length, int preserve) {
/*  128 */     double[] t = new double[length];
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
/*      */   public static double[] ensureCapacity(double[] array, int length) {
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
/*      */   public static double[] ensureCapacity(double[] array, int length, int preserve) {
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
/*      */   public static double[] grow(double[] array, int length) {
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
/*      */   public static double[] grow(double[] array, int length, int preserve) {
/*  211 */     if (length > array.length) {
/*      */       
/*  213 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  214 */       double[] t = new double[newLength];
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
/*      */   public static double[] trim(double[] array, int length) {
/*  233 */     if (length >= array.length)
/*  234 */       return array; 
/*  235 */     double[] t = (length == 0) ? EMPTY_ARRAY : new double[length];
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
/*      */   public static double[] setLength(double[] array, int length) {
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
/*      */   public static double[] copy(double[] array, int offset, int length) {
/*  274 */     ensureOffsetLength(array, offset, length);
/*  275 */     double[] a = (length == 0) ? EMPTY_ARRAY : new double[length];
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
/*      */   public static double[] copy(double[] array) {
/*  287 */     return (double[])array.clone();
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
/*      */   public static void fill(double[] array, double value) {
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
/*      */   public static void fill(double[] array, int from, int to, double value) {
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
/*      */   public static boolean equals(double[] a1, double[] a2) {
/*  342 */     int i = a1.length;
/*  343 */     if (i != a2.length)
/*  344 */       return false; 
/*  345 */     while (i-- != 0) {
/*  346 */       if (Double.doubleToLongBits(a1[i]) != Double.doubleToLongBits(a2[i]))
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
/*      */   public static void ensureFromTo(double[] a, int from, int to) {
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
/*      */   public static void ensureOffsetLength(double[] a, int offset, int length) {
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
/*      */   public static void ensureSameLength(double[] a, double[] b) {
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
/*      */   public static void swap(double[] x, int a, int b) {
/*  422 */     double t = x[a];
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
/*      */   public static void swap(double[] x, int a, int b, int n) {
/*  440 */     for (int i = 0; i < n; i++, a++, b++)
/*  441 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(double[] x, int a, int b, int c, DoubleComparator comp) {
/*  444 */     int ab = comp.compare(x[a], x[b]);
/*  445 */     int ac = comp.compare(x[a], x[c]);
/*  446 */     int bc = comp.compare(x[b], x[c]);
/*  447 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(double[] a, int from, int to, DoubleComparator comp) {
/*  450 */     for (int i = from; i < to - 1; i++) {
/*  451 */       int m = i;
/*  452 */       for (int j = i + 1; j < to; j++) {
/*  453 */         if (comp.compare(a[j], a[m]) < 0)
/*  454 */           m = j; 
/*  455 */       }  if (m != i) {
/*  456 */         double u = a[i];
/*  457 */         a[i] = a[m];
/*  458 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(double[] a, int from, int to, DoubleComparator comp) {
/*  463 */     for (int i = from; ++i < to; ) {
/*  464 */       double t = a[i];
/*  465 */       int j = i; double u;
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
/*      */   public static void quickSort(double[] x, int from, int to, DoubleComparator comp) {
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
/*  518 */     double v = x[m];
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
/*      */   public static void quickSort(double[] x, DoubleComparator comp) {
/*  570 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final double[] x;
/*      */     private final DoubleComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(double[] x, int from, int to, DoubleComparator comp) {
/*  579 */       this.from = from;
/*  580 */       this.to = to;
/*  581 */       this.x = x;
/*  582 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  586 */       double[] x = this.x;
/*  587 */       int len = this.to - this.from;
/*  588 */       if (len < 8192) {
/*  589 */         DoubleArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  593 */       int m = this.from + len / 2;
/*  594 */       int l = this.from;
/*  595 */       int n = this.to - 1;
/*  596 */       int s = len / 8;
/*  597 */       l = DoubleArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  598 */       m = DoubleArrays.med3(x, m - s, m, m + s, this.comp);
/*  599 */       n = DoubleArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  600 */       m = DoubleArrays.med3(x, l, m, n, this.comp);
/*  601 */       double v = x[m];
/*      */       
/*  603 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  606 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  607 */           if (comparison == 0)
/*  608 */             DoubleArrays.swap(x, a++, b); 
/*  609 */           b++; continue;
/*      */         } 
/*  611 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  612 */           if (comparison == 0)
/*  613 */             DoubleArrays.swap(x, c, d--); 
/*  614 */           c--;
/*      */         } 
/*  616 */         if (b > c)
/*      */           break; 
/*  618 */         DoubleArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  622 */       s = Math.min(a - this.from, b - a);
/*  623 */       DoubleArrays.swap(x, this.from, b - s, s);
/*  624 */       s = Math.min(d - c, this.to - d - 1);
/*  625 */       DoubleArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(double[] x, int from, int to, DoubleComparator comp) {
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
/*      */   public static void parallelQuickSort(double[] x, DoubleComparator comp) {
/*  688 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(double[] x, int a, int b, int c) {
/*  692 */     int ab = Double.compare(x[a], x[b]);
/*  693 */     int ac = Double.compare(x[a], x[c]);
/*  694 */     int bc = Double.compare(x[b], x[c]);
/*  695 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(double[] a, int from, int to) {
/*  699 */     for (int i = from; i < to - 1; i++) {
/*  700 */       int m = i;
/*  701 */       for (int j = i + 1; j < to; j++) {
/*  702 */         if (Double.compare(a[j], a[m]) < 0)
/*  703 */           m = j; 
/*  704 */       }  if (m != i) {
/*  705 */         double u = a[i];
/*  706 */         a[i] = a[m];
/*  707 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(double[] a, int from, int to) {
/*  713 */     for (int i = from; ++i < to; ) {
/*  714 */       double t = a[i];
/*  715 */       int j = i; double u;
/*  716 */       for (u = a[j - 1]; Double.compare(t, u) < 0; u = a[--j - 1]) {
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
/*      */   public static void quickSort(double[] x, int from, int to) {
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
/*  766 */     double v = x[m];
/*      */     
/*  768 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  771 */       if (b <= c && (comparison = Double.compare(x[b], v)) <= 0) {
/*  772 */         if (comparison == 0)
/*  773 */           swap(x, a++, b); 
/*  774 */         b++; continue;
/*      */       } 
/*  776 */       while (c >= b && (comparison = Double.compare(x[c], v)) >= 0) {
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
/*      */   public static void quickSort(double[] x) {
/*  815 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final double[] x;
/*      */     
/*      */     public ForkJoinQuickSort(double[] x, int from, int to) {
/*  823 */       this.from = from;
/*  824 */       this.to = to;
/*  825 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  830 */       double[] x = this.x;
/*  831 */       int len = this.to - this.from;
/*  832 */       if (len < 8192) {
/*  833 */         DoubleArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  837 */       int m = this.from + len / 2;
/*  838 */       int l = this.from;
/*  839 */       int n = this.to - 1;
/*  840 */       int s = len / 8;
/*  841 */       l = DoubleArrays.med3(x, l, l + s, l + 2 * s);
/*  842 */       m = DoubleArrays.med3(x, m - s, m, m + s);
/*  843 */       n = DoubleArrays.med3(x, n - 2 * s, n - s, n);
/*  844 */       m = DoubleArrays.med3(x, l, m, n);
/*  845 */       double v = x[m];
/*      */       
/*  847 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  850 */         if (b <= c && (comparison = Double.compare(x[b], v)) <= 0) {
/*  851 */           if (comparison == 0)
/*  852 */             DoubleArrays.swap(x, a++, b); 
/*  853 */           b++; continue;
/*      */         } 
/*  855 */         while (c >= b && (comparison = Double.compare(x[c], v)) >= 0) {
/*  856 */           if (comparison == 0)
/*  857 */             DoubleArrays.swap(x, c, d--); 
/*  858 */           c--;
/*      */         } 
/*  860 */         if (b > c)
/*      */           break; 
/*  862 */         DoubleArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  866 */       s = Math.min(a - this.from, b - a);
/*  867 */       DoubleArrays.swap(x, this.from, b - s, s);
/*  868 */       s = Math.min(d - c, this.to - d - 1);
/*  869 */       DoubleArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(double[] x, int from, int to) {
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
/*      */   public static void parallelQuickSort(double[] x) {
/*  928 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, double[] x, int a, int b, int c) {
/*  932 */     double aa = x[perm[a]];
/*  933 */     double bb = x[perm[b]];
/*  934 */     double cc = x[perm[c]];
/*  935 */     int ab = Double.compare(aa, bb);
/*  936 */     int ac = Double.compare(aa, cc);
/*  937 */     int bc = Double.compare(bb, cc);
/*  938 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, double[] a, int from, int to) {
/*  942 */     for (int i = from; ++i < to; ) {
/*  943 */       int t = perm[i];
/*  944 */       int j = i; int u;
/*  945 */       for (u = perm[j - 1]; Double.compare(a[t], a[u]) < 0; u = perm[--j - 1]) {
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
/*      */   public static void quickSortIndirect(int[] perm, double[] x, int from, int to) {
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
/* 1002 */     double v = x[perm[m]];
/*      */     
/* 1004 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1007 */       if (b <= c && (comparison = Double.compare(x[perm[b]], v)) <= 0) {
/* 1008 */         if (comparison == 0)
/* 1009 */           IntArrays.swap(perm, a++, b); 
/* 1010 */         b++; continue;
/*      */       } 
/* 1012 */       while (c >= b && (comparison = Double.compare(x[perm[c]], v)) >= 0) {
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
/*      */   public static void quickSortIndirect(int[] perm, double[] x) {
/* 1058 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final double[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, double[] x, int from, int to) {
/* 1067 */       this.from = from;
/* 1068 */       this.to = to;
/* 1069 */       this.x = x;
/* 1070 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1075 */       double[] x = this.x;
/* 1076 */       int len = this.to - this.from;
/* 1077 */       if (len < 8192) {
/* 1078 */         DoubleArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1082 */       int m = this.from + len / 2;
/* 1083 */       int l = this.from;
/* 1084 */       int n = this.to - 1;
/* 1085 */       int s = len / 8;
/* 1086 */       l = DoubleArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1087 */       m = DoubleArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1088 */       n = DoubleArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1089 */       m = DoubleArrays.med3Indirect(this.perm, x, l, m, n);
/* 1090 */       double v = x[this.perm[m]];
/*      */       
/* 1092 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1095 */         if (b <= c && (comparison = Double.compare(x[this.perm[b]], v)) <= 0) {
/* 1096 */           if (comparison == 0)
/* 1097 */             IntArrays.swap(this.perm, a++, b); 
/* 1098 */           b++; continue;
/*      */         } 
/* 1100 */         while (c >= b && (comparison = Double.compare(x[this.perm[c]], v)) >= 0) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, double[] x, int from, int to) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, double[] x) {
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
/*      */   public static void stabilize(int[] perm, double[] x, int from, int to) {
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
/*      */   public static void stabilize(int[] perm, double[] x) {
/* 1259 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(double[] x, double[] y, int a, int b, int c) {
/* 1264 */     int t, ab = ((t = Double.compare(x[a], x[b])) == 0) ? Double.compare(y[a], y[b]) : t;
/* 1265 */     int ac = ((t = Double.compare(x[a], x[c])) == 0) ? Double.compare(y[a], y[c]) : t;
/* 1266 */     int bc = ((t = Double.compare(x[b], x[c])) == 0) ? Double.compare(y[b], y[c]) : t;
/* 1267 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(double[] x, double[] y, int a, int b) {
/* 1270 */     double t = x[a];
/* 1271 */     double u = y[a];
/* 1272 */     x[a] = x[b];
/* 1273 */     y[a] = y[b];
/* 1274 */     x[b] = t;
/* 1275 */     y[b] = u;
/*      */   }
/*      */   private static void swap(double[] x, double[] y, int a, int b, int n) {
/* 1278 */     for (int i = 0; i < n; i++, a++, b++)
/* 1279 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(double[] a, double[] b, int from, int to) {
/* 1283 */     for (int i = from; i < to - 1; i++) {
/* 1284 */       int m = i;
/* 1285 */       for (int j = i + 1; j < to; j++) {
/* 1286 */         int u; if ((u = Double.compare(a[j], a[m])) < 0 || (u == 0 && Double.compare(b[j], b[m]) < 0))
/* 1287 */           m = j; 
/* 1288 */       }  if (m != i) {
/* 1289 */         double t = a[i];
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
/*      */   public static void quickSort(double[] x, double[] y, int from, int to) {
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
/* 1341 */     double v = x[m], w = y[m];
/*      */     
/* 1343 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1346 */       if (b <= c) {
/*      */         int comparison; int t;
/* 1348 */         if ((comparison = ((t = Double.compare(x[b], v)) == 0) ? Double.compare(y[b], w) : t) <= 0) {
/* 1349 */           if (comparison == 0)
/* 1350 */             swap(x, y, a++, b); 
/* 1351 */           b++; continue;
/*      */         } 
/* 1353 */       }  while (c >= b) {
/*      */         int comparison; int t;
/* 1355 */         if ((comparison = ((t = Double.compare(x[c], v)) == 0) ? Double.compare(y[c], w) : t) >= 0) {
/* 1356 */           if (comparison == 0)
/* 1357 */             swap(x, y, c, d--); 
/* 1358 */           c--;
/*      */         } 
/* 1360 */       }  if (b > c)
/*      */         break; 
/* 1362 */       swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1366 */     int s = Math.min(a - from, b - a);
/* 1367 */     swap(x, y, from, b - s, s);
/* 1368 */     s = Math.min(d - c, to - d - 1);
/* 1369 */     swap(x, y, b, to - s, s);
/*      */     
/* 1371 */     if ((s = b - a) > 1)
/* 1372 */       quickSort(x, y, from, from + s); 
/* 1373 */     if ((s = d - c) > 1) {
/* 1374 */       quickSort(x, y, to - s, to);
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
/*      */   public static void quickSort(double[] x, double[] y) {
/* 1398 */     ensureSameLength(x, y);
/* 1399 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L; private final int from;
/*      */     private final int to;
/*      */     private final double[] x;
/*      */     private final double[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(double[] x, double[] y, int from, int to) {
/* 1407 */       this.from = from;
/* 1408 */       this.to = to;
/* 1409 */       this.x = x;
/* 1410 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1415 */       double[] x = this.x;
/* 1416 */       double[] y = this.y;
/* 1417 */       int len = this.to - this.from;
/* 1418 */       if (len < 8192) {
/* 1419 */         DoubleArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1423 */       int m = this.from + len / 2;
/* 1424 */       int l = this.from;
/* 1425 */       int n = this.to - 1;
/* 1426 */       int s = len / 8;
/* 1427 */       l = DoubleArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1428 */       m = DoubleArrays.med3(x, y, m - s, m, m + s);
/* 1429 */       n = DoubleArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1430 */       m = DoubleArrays.med3(x, y, l, m, n);
/* 1431 */       double v = x[m], w = y[m];
/*      */       
/* 1433 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1436 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1438 */           if ((comparison = ((i = Double.compare(x[b], v)) == 0) ? Double.compare(y[b], w) : i) <= 0) {
/* 1439 */             if (comparison == 0)
/* 1440 */               DoubleArrays.swap(x, y, a++, b); 
/* 1441 */             b++; continue;
/*      */           } 
/* 1443 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1445 */           if ((comparison = ((i = Double.compare(x[c], v)) == 0) ? Double.compare(y[c], w) : i) >= 0) {
/* 1446 */             if (comparison == 0)
/* 1447 */               DoubleArrays.swap(x, y, c, d--); 
/* 1448 */             c--;
/*      */           } 
/* 1450 */         }  if (b > c)
/*      */           break; 
/* 1452 */         DoubleArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1456 */       s = Math.min(a - this.from, b - a);
/* 1457 */       DoubleArrays.swap(x, y, this.from, b - s, s);
/* 1458 */       s = Math.min(d - c, this.to - d - 1);
/* 1459 */       DoubleArrays.swap(x, y, b, this.to - s, s);
/* 1460 */       s = b - a;
/* 1461 */       int t = d - c;
/*      */       
/* 1463 */       if (s > 1 && t > 1) {
/* 1464 */         invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to));
/* 1465 */       } else if (s > 1) {
/* 1466 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) });
/*      */       } else {
/* 1468 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) });
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
/*      */   public static void parallelQuickSort(double[] x, double[] y, int from, int to) {
/* 1501 */     if (to - from < 8192)
/* 1502 */       quickSort(x, y, from, to); 
/* 1503 */     ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1504 */     pool.invoke(new ForkJoinQuickSort2(x, y, from, to));
/* 1505 */     pool.shutdown();
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
/*      */   public static void parallelQuickSort(double[] x, double[] y) {
/* 1533 */     ensureSameLength(x, y);
/* 1534 */     parallelQuickSort(x, y, 0, x.length);
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
/*      */   public static void unstableSort(double[] a, int from, int to) {
/* 1554 */     if (to - from >= 2000) {
/* 1555 */       radixSort(a, from, to);
/*      */     } else {
/* 1557 */       quickSort(a, from, to);
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
/*      */   public static void unstableSort(double[] a) {
/* 1571 */     unstableSort(a, 0, a.length);
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
/*      */   public static void unstableSort(double[] a, int from, int to, DoubleComparator comp) {
/* 1590 */     quickSort(a, from, to, comp);
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
/*      */   public static void unstableSort(double[] a, DoubleComparator comp) {
/* 1604 */     unstableSort(a, 0, a.length, comp);
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
/*      */   public static void mergeSort(double[] a, int from, int to, double[] supp) {
/* 1628 */     int len = to - from;
/*      */     
/* 1630 */     if (len < 16) {
/* 1631 */       insertionSort(a, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1635 */     int mid = from + to >>> 1;
/* 1636 */     mergeSort(supp, from, mid, a);
/* 1637 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1640 */     if (Double.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1641 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1645 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1646 */       if (q >= to || (p < mid && Double.compare(supp[p], supp[q]) <= 0)) {
/* 1647 */         a[i] = supp[p++];
/*      */       } else {
/* 1649 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(double[] a, int from, int to) {
/* 1669 */     mergeSort(a, from, to, (double[])a.clone());
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
/*      */   public static void mergeSort(double[] a) {
/* 1683 */     mergeSort(a, 0, a.length);
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
/*      */   public static void mergeSort(double[] a, int from, int to, DoubleComparator comp, double[] supp) {
/* 1709 */     int len = to - from;
/*      */     
/* 1711 */     if (len < 16) {
/* 1712 */       insertionSort(a, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/* 1716 */     int mid = from + to >>> 1;
/* 1717 */     mergeSort(supp, from, mid, comp, a);
/* 1718 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1721 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1722 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1726 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1727 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
/* 1728 */         a[i] = supp[p++];
/*      */       } else {
/* 1730 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(double[] a, int from, int to, DoubleComparator comp) {
/* 1752 */     mergeSort(a, from, to, comp, (double[])a.clone());
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
/*      */   public static void mergeSort(double[] a, DoubleComparator comp) {
/* 1769 */     mergeSort(a, 0, a.length, comp);
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
/*      */   public static void stableSort(double[] a, int from, int to) {
/* 1794 */     mergeSort(a, from, to);
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
/*      */   public static void stableSort(double[] a) {
/* 1812 */     stableSort(a, 0, a.length);
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
/*      */   public static void stableSort(double[] a, int from, int to, DoubleComparator comp) {
/* 1836 */     mergeSort(a, from, to, comp);
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
/*      */   public static void stableSort(double[] a, DoubleComparator comp) {
/* 1856 */     stableSort(a, 0, a.length, comp);
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
/*      */   public static int binarySearch(double[] a, int from, int to, double key) {
/* 1885 */     to--;
/* 1886 */     while (from <= to) {
/* 1887 */       int mid = from + to >>> 1;
/* 1888 */       double midVal = a[mid];
/* 1889 */       if (midVal < key) {
/* 1890 */         from = mid + 1; continue;
/* 1891 */       }  if (midVal > key) {
/* 1892 */         to = mid - 1; continue;
/*      */       } 
/* 1894 */       return mid;
/*      */     } 
/* 1896 */     return -(from + 1);
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
/*      */   public static int binarySearch(double[] a, double key) {
/* 1918 */     return binarySearch(a, 0, a.length, key);
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
/*      */   public static int binarySearch(double[] a, int from, int to, double key, DoubleComparator c) {
/* 1948 */     to--;
/* 1949 */     while (from <= to) {
/* 1950 */       int mid = from + to >>> 1;
/* 1951 */       double midVal = a[mid];
/* 1952 */       int cmp = c.compare(midVal, key);
/* 1953 */       if (cmp < 0) {
/* 1954 */         from = mid + 1; continue;
/* 1955 */       }  if (cmp > 0) {
/* 1956 */         to = mid - 1; continue;
/*      */       } 
/* 1958 */       return mid;
/*      */     } 
/* 1960 */     return -(from + 1);
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
/*      */   public static int binarySearch(double[] a, double key, DoubleComparator c) {
/* 1985 */     return binarySearch(a, 0, a.length, key, c);
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
/*      */   private static final long fixDouble(double d) {
/* 2005 */     long l = Double.doubleToLongBits(d);
/* 2006 */     return (l >= 0L) ? l : (l ^ Long.MAX_VALUE);
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
/*      */   public static void radixSort(double[] a) {
/* 2025 */     radixSort(a, 0, a.length);
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
/*      */   public static void radixSort(double[] a, int from, int to) {
/* 2048 */     if (to - from < 1024) {
/* 2049 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2052 */     int maxLevel = 7;
/* 2053 */     int stackSize = 1786;
/* 2054 */     int stackPos = 0;
/* 2055 */     int[] offsetStack = new int[1786];
/* 2056 */     int[] lengthStack = new int[1786];
/* 2057 */     int[] levelStack = new int[1786];
/* 2058 */     offsetStack[stackPos] = from;
/* 2059 */     lengthStack[stackPos] = to - from;
/* 2060 */     levelStack[stackPos++] = 0;
/* 2061 */     int[] count = new int[256];
/* 2062 */     int[] pos = new int[256];
/* 2063 */     while (stackPos > 0) {
/* 2064 */       int first = offsetStack[--stackPos];
/* 2065 */       int length = lengthStack[stackPos];
/* 2066 */       int level = levelStack[stackPos];
/* 2067 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2068 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2073 */       for (int i = first + length; i-- != first;) {
/* 2074 */         count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2076 */       int lastUsed = -1;
/* 2077 */       for (int j = 0, p = first; j < 256; j++) {
/* 2078 */         if (count[j] != 0)
/* 2079 */           lastUsed = j; 
/* 2080 */         pos[j] = p += count[j];
/*      */       } 
/* 2082 */       int end = first + length - count[lastUsed];
/*      */       
/* 2084 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2085 */         double t = a[k];
/* 2086 */         c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
/* 2087 */         if (k < end) {
/* 2088 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2089 */             double z = t;
/* 2090 */             t = a[d];
/* 2091 */             a[d] = z;
/* 2092 */             c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2094 */           a[k] = t;
/*      */         } 
/* 2096 */         if (level < 7 && count[c] > 1)
/* 2097 */           if (count[c] < 1024) {
/* 2098 */             quickSort(a, k, k + count[c]);
/*      */           } else {
/* 2100 */             offsetStack[stackPos] = k;
/* 2101 */             lengthStack[stackPos] = count[c];
/* 2102 */             levelStack[stackPos++] = level + 1;
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected static final class Segment { protected final int offset; protected final int length;
/*      */     protected final int level;
/*      */     
/*      */     protected Segment(int offset, int length, int level) {
/* 2111 */       this.offset = offset;
/* 2112 */       this.length = length;
/* 2113 */       this.level = level;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2117 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */   
/* 2120 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(double[] a, int from, int to) {
/* 2141 */     if (to - from < 1024) {
/* 2142 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2145 */     int maxLevel = 7;
/* 2146 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2147 */     queue.add(new Segment(from, to - from, 0));
/* 2148 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2149 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2150 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2151 */         Executors.defaultThreadFactory());
/* 2152 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2154 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2155 */       executorCompletionService.submit(() -> {
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
/*      */                 count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[i]) >>> shift & 0xFFL ^ signMask)] + 1; 
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
/*      */                 double t = a[k];
/*      */                 c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     double z = t;
/*      */                     t = a[d];
/*      */                     a[d] = z;
/*      */                     c = (int)(fixDouble(t) >>> shift & 0xFFL ^ signMask);
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
/* 2212 */     Throwable problem = null;
/* 2213 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2215 */         executorCompletionService.take().get();
/* 2216 */       } catch (Exception e) {
/* 2217 */         problem = e.getCause();
/*      */       } 
/* 2219 */     }  executorService.shutdown();
/* 2220 */     if (problem != null) {
/* 2221 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(double[] a) {
/* 2239 */     parallelRadixSort(a, 0, a.length);
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
/*      */   public static void radixSortIndirect(int[] perm, double[] a, boolean stable) {
/* 2266 */     radixSortIndirect(perm, a, 0, perm.length, stable);
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
/*      */   public static void radixSortIndirect(int[] perm, double[] a, int from, int to, boolean stable) {
/* 2300 */     if (to - from < 1024) {
/* 2301 */       insertionSortIndirect(perm, a, from, to);
/*      */       return;
/*      */     } 
/* 2304 */     int maxLevel = 7;
/* 2305 */     int stackSize = 1786;
/* 2306 */     int stackPos = 0;
/* 2307 */     int[] offsetStack = new int[1786];
/* 2308 */     int[] lengthStack = new int[1786];
/* 2309 */     int[] levelStack = new int[1786];
/* 2310 */     offsetStack[stackPos] = from;
/* 2311 */     lengthStack[stackPos] = to - from;
/* 2312 */     levelStack[stackPos++] = 0;
/* 2313 */     int[] count = new int[256];
/* 2314 */     int[] pos = new int[256];
/* 2315 */     int[] support = stable ? new int[perm.length] : null;
/* 2316 */     while (stackPos > 0) {
/* 2317 */       int first = offsetStack[--stackPos];
/* 2318 */       int length = lengthStack[stackPos];
/* 2319 */       int level = levelStack[stackPos];
/* 2320 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2321 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2326 */       for (int i = first + length; i-- != first;) {
/* 2327 */         count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2329 */       int lastUsed = -1; int j, p;
/* 2330 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2331 */         if (count[j] != 0)
/* 2332 */           lastUsed = j; 
/* 2333 */         pos[j] = p += count[j];
/*      */       } 
/* 2335 */       if (stable) {
/* 2336 */         for (j = first + length; j-- != first; ) {
/* 2337 */           pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] = pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1; support[pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1] = perm[j];
/* 2338 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2339 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2340 */           if (level < 7 && count[j] > 1) {
/* 2341 */             if (count[j] < 1024) {
/* 2342 */               insertionSortIndirect(perm, a, p, p + count[j]);
/*      */             } else {
/* 2344 */               offsetStack[stackPos] = p;
/* 2345 */               lengthStack[stackPos] = count[j];
/* 2346 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2349 */           p += count[j];
/*      */         } 
/* 2351 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2353 */       int end = first + length - count[lastUsed];
/*      */       
/* 2355 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2356 */         int t = perm[k];
/* 2357 */         c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
/* 2358 */         if (k < end) {
/* 2359 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2360 */             int z = t;
/* 2361 */             t = perm[d];
/* 2362 */             perm[d] = z;
/* 2363 */             c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2365 */           perm[k] = t;
/*      */         } 
/* 2367 */         if (level < 7 && count[c] > 1) {
/* 2368 */           if (count[c] < 1024) {
/* 2369 */             insertionSortIndirect(perm, a, k, k + count[c]);
/*      */           } else {
/* 2371 */             offsetStack[stackPos] = k;
/* 2372 */             lengthStack[stackPos] = count[c];
/* 2373 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, double[] a, int from, int to, boolean stable) {
/* 2410 */     if (to - from < 1024) {
/* 2411 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2414 */     int maxLevel = 7;
/* 2415 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2416 */     queue.add(new Segment(from, to - from, 0));
/* 2417 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2418 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2419 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2420 */         Executors.defaultThreadFactory());
/* 2421 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2423 */     int[] support = stable ? new int[perm.length] : null;
/* 2424 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2425 */       executorCompletionService.submit(() -> {
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
/*      */                 count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(a[perm[i]]) >>> shift & 0xFFL ^ signMask)] + 1; 
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
/*      */                   pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] = pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1;
/*      */                   support[pos[(int)(fixDouble(a[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1] = perm[j];
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
/*      */                   c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
/*      */                   if (k < end) {
/*      */                     pos[c] = pos[c] - 1;
/*      */                     int d;
/*      */                     while ((d = pos[c] - 1) > k) {
/*      */                       int z = t;
/*      */                       t = perm[d];
/*      */                       perm[d] = z;
/*      */                       c = (int)(fixDouble(a[t]) >>> shift & 0xFFL ^ signMask);
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
/* 2500 */     Throwable problem = null;
/* 2501 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2503 */         executorCompletionService.take().get();
/* 2504 */       } catch (Exception e) {
/* 2505 */         problem = e.getCause();
/*      */       } 
/* 2507 */     }  executorService.shutdown();
/* 2508 */     if (problem != null) {
/* 2509 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, double[] a, boolean stable) {
/* 2536 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
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
/*      */   public static void radixSort(double[] a, double[] b) {
/* 2558 */     ensureSameLength(a, b);
/* 2559 */     radixSort(a, b, 0, a.length);
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
/*      */   public static void radixSort(double[] a, double[] b, int from, int to) {
/* 2586 */     if (to - from < 1024) {
/* 2587 */       selectionSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2590 */     int layers = 2;
/* 2591 */     int maxLevel = 15;
/* 2592 */     int stackSize = 3826;
/* 2593 */     int stackPos = 0;
/* 2594 */     int[] offsetStack = new int[3826];
/* 2595 */     int[] lengthStack = new int[3826];
/* 2596 */     int[] levelStack = new int[3826];
/* 2597 */     offsetStack[stackPos] = from;
/* 2598 */     lengthStack[stackPos] = to - from;
/* 2599 */     levelStack[stackPos++] = 0;
/* 2600 */     int[] count = new int[256];
/* 2601 */     int[] pos = new int[256];
/* 2602 */     while (stackPos > 0) {
/* 2603 */       int first = offsetStack[--stackPos];
/* 2604 */       int length = lengthStack[stackPos];
/* 2605 */       int level = levelStack[stackPos];
/* 2606 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2607 */       double[] k = (level < 8) ? a : b;
/* 2608 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2613 */       for (int i = first + length; i-- != first;) {
/* 2614 */         count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2616 */       int lastUsed = -1;
/* 2617 */       for (int j = 0, p = first; j < 256; j++) {
/* 2618 */         if (count[j] != 0)
/* 2619 */           lastUsed = j; 
/* 2620 */         pos[j] = p += count[j];
/*      */       } 
/* 2622 */       int end = first + length - count[lastUsed];
/*      */       
/* 2624 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2625 */         double t = a[m];
/* 2626 */         double u = b[m];
/* 2627 */         c = (int)(fixDouble(k[m]) >>> shift & 0xFFL ^ signMask);
/* 2628 */         if (m < end) {
/* 2629 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2630 */             c = (int)(fixDouble(k[d]) >>> shift & 0xFFL ^ signMask);
/* 2631 */             double z = t;
/* 2632 */             t = a[d];
/* 2633 */             a[d] = z;
/* 2634 */             z = u;
/* 2635 */             u = b[d];
/* 2636 */             b[d] = z;
/*      */           } 
/* 2638 */           a[m] = t;
/* 2639 */           b[m] = u;
/*      */         } 
/* 2641 */         if (level < 15 && count[c] > 1) {
/* 2642 */           if (count[c] < 1024) {
/* 2643 */             selectionSort(a, b, m, m + count[c]);
/*      */           } else {
/* 2645 */             offsetStack[stackPos] = m;
/* 2646 */             lengthStack[stackPos] = count[c];
/* 2647 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSort(double[] a, double[] b, int from, int to) {
/* 2683 */     if (to - from < 1024) {
/* 2684 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2687 */     int layers = 2;
/* 2688 */     if (a.length != b.length)
/* 2689 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 2690 */     int maxLevel = 15;
/* 2691 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2692 */     queue.add(new Segment(from, to - from, 0));
/* 2693 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2694 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2695 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2696 */         Executors.defaultThreadFactory());
/* 2697 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2699 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2700 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int n = numberOfThreads; while (n-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 8 == 0) ? 128 : 0; double[] k = (level < 8) ? a : b;
/*      */               int shift = (7 - level % 8) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] + 1; 
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
/*      */                 double t = a[m];
/*      */                 double u = b[m];
/*      */                 c = (int)(fixDouble(k[m]) >>> shift & 0xFFL ^ signMask);
/*      */                 if (m < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > m) {
/*      */                     c = (int)(fixDouble(k[d]) >>> shift & 0xFFL ^ signMask);
/*      */                     double z = t;
/*      */                     double w = u;
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
/* 2756 */     Throwable problem = null;
/* 2757 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2759 */         executorCompletionService.take().get();
/* 2760 */       } catch (Exception e) {
/* 2761 */         problem = e.getCause();
/*      */       } 
/* 2763 */     }  executorService.shutdown();
/* 2764 */     if (problem != null) {
/* 2765 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(double[] a, double[] b) {
/* 2792 */     ensureSameLength(a, b);
/* 2793 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, double[] a, double[] b, int from, int to) {
/* 2797 */     for (int i = from; ++i < to; ) {
/* 2798 */       int t = perm[i];
/* 2799 */       int j = i; int u;
/* 2800 */       for (u = perm[j - 1]; Double.compare(a[t], a[u]) < 0 || (Double.compare(a[t], a[u]) == 0 && 
/* 2801 */         Double.compare(b[t], b[u]) < 0); u = perm[--j - 1]) {
/* 2802 */         perm[j] = u;
/* 2803 */         if (from == j - 1) {
/* 2804 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2808 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, double[] a, double[] b, boolean stable) {
/* 2842 */     ensureSameLength(a, b);
/* 2843 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
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
/*      */   public static void radixSortIndirect(int[] perm, double[] a, double[] b, int from, int to, boolean stable) {
/* 2883 */     if (to - from < 1024) {
/* 2884 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2887 */     int layers = 2;
/* 2888 */     int maxLevel = 15;
/* 2889 */     int stackSize = 3826;
/* 2890 */     int stackPos = 0;
/* 2891 */     int[] offsetStack = new int[3826];
/* 2892 */     int[] lengthStack = new int[3826];
/* 2893 */     int[] levelStack = new int[3826];
/* 2894 */     offsetStack[stackPos] = from;
/* 2895 */     lengthStack[stackPos] = to - from;
/* 2896 */     levelStack[stackPos++] = 0;
/* 2897 */     int[] count = new int[256];
/* 2898 */     int[] pos = new int[256];
/* 2899 */     int[] support = stable ? new int[perm.length] : null;
/* 2900 */     while (stackPos > 0) {
/* 2901 */       int first = offsetStack[--stackPos];
/* 2902 */       int length = lengthStack[stackPos];
/* 2903 */       int level = levelStack[stackPos];
/* 2904 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 2905 */       double[] k = (level < 8) ? a : b;
/* 2906 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2911 */       for (int i = first + length; i-- != first;) {
/* 2912 */         count[(int)(fixDouble(k[perm[i]]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[perm[i]]) >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 2914 */       int lastUsed = -1; int j, p;
/* 2915 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2916 */         if (count[j] != 0)
/* 2917 */           lastUsed = j; 
/* 2918 */         pos[j] = p += count[j];
/*      */       } 
/* 2920 */       if (stable) {
/* 2921 */         for (j = first + length; j-- != first; ) {
/* 2922 */           pos[(int)(fixDouble(k[perm[j]]) >>> shift & 0xFFL ^ signMask)] = pos[(int)(fixDouble(k[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1; support[pos[(int)(fixDouble(k[perm[j]]) >>> shift & 0xFFL ^ signMask)] - 1] = perm[j];
/* 2923 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2924 */         for (j = 0, p = first; j < 256; j++) {
/* 2925 */           if (level < 15 && count[j] > 1) {
/* 2926 */             if (count[j] < 1024) {
/* 2927 */               insertionSortIndirect(perm, a, b, p, p + count[j]);
/*      */             } else {
/* 2929 */               offsetStack[stackPos] = p;
/* 2930 */               lengthStack[stackPos] = count[j];
/* 2931 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2934 */           p += count[j];
/*      */         } 
/* 2936 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2938 */       int end = first + length - count[lastUsed];
/*      */       
/* 2940 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2941 */         int t = perm[m];
/* 2942 */         c = (int)(fixDouble(k[t]) >>> shift & 0xFFL ^ signMask);
/* 2943 */         if (m < end) {
/* 2944 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2945 */             int z = t;
/* 2946 */             t = perm[d];
/* 2947 */             perm[d] = z;
/* 2948 */             c = (int)(fixDouble(k[t]) >>> shift & 0xFFL ^ signMask);
/*      */           } 
/* 2950 */           perm[m] = t;
/*      */         } 
/* 2952 */         if (level < 15 && count[c] > 1) {
/* 2953 */           if (count[c] < 1024) {
/* 2954 */             insertionSortIndirect(perm, a, b, m, m + count[c]);
/*      */           } else {
/* 2956 */             offsetStack[stackPos] = m;
/* 2957 */             lengthStack[stackPos] = count[c];
/* 2958 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void selectionSort(double[][] a, int from, int to, int level) {
/* 2966 */     int layers = a.length;
/* 2967 */     int firstLayer = level / 8;
/* 2968 */     for (int i = from; i < to - 1; i++) {
/* 2969 */       int m = i;
/* 2970 */       for (int j = i + 1; j < to; j++) {
/* 2971 */         for (int p = firstLayer; p < layers; p++) {
/* 2972 */           if (a[p][j] < a[p][m]) {
/* 2973 */             m = j; break;
/*      */           } 
/* 2975 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/*      */       } 
/* 2979 */       if (m != i) {
/* 2980 */         for (int p = layers; p-- != 0; ) {
/* 2981 */           double u = a[p][i];
/* 2982 */           a[p][i] = a[p][m];
/* 2983 */           a[p][m] = u;
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
/*      */   public static void radixSort(double[][] a) {
/* 3006 */     radixSort(a, 0, (a[0]).length);
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
/*      */   public static void radixSort(double[][] a, int from, int to) {
/* 3030 */     if (to - from < 1024) {
/* 3031 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 3034 */     int layers = a.length;
/* 3035 */     int maxLevel = 8 * layers - 1;
/* 3036 */     for (int p = layers, l = (a[0]).length; p-- != 0;) {
/* 3037 */       if ((a[p]).length != l)
/* 3038 */         throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0."); 
/*      */     } 
/* 3040 */     int stackSize = 255 * (layers * 8 - 1) + 1;
/* 3041 */     int stackPos = 0;
/* 3042 */     int[] offsetStack = new int[stackSize];
/* 3043 */     int[] lengthStack = new int[stackSize];
/* 3044 */     int[] levelStack = new int[stackSize];
/* 3045 */     offsetStack[stackPos] = from;
/* 3046 */     lengthStack[stackPos] = to - from;
/* 3047 */     levelStack[stackPos++] = 0;
/* 3048 */     int[] count = new int[256];
/* 3049 */     int[] pos = new int[256];
/* 3050 */     double[] t = new double[layers];
/* 3051 */     while (stackPos > 0) {
/* 3052 */       int first = offsetStack[--stackPos];
/* 3053 */       int length = lengthStack[stackPos];
/* 3054 */       int level = levelStack[stackPos];
/* 3055 */       int signMask = (level % 8 == 0) ? 128 : 0;
/* 3056 */       double[] k = a[level / 8];
/* 3057 */       int shift = (7 - level % 8) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3062 */       for (int i = first + length; i-- != first;) {
/* 3063 */         count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] = count[(int)(fixDouble(k[i]) >>> shift & 0xFFL ^ signMask)] + 1;
/*      */       }
/* 3065 */       int lastUsed = -1;
/* 3066 */       for (int j = 0, n = first; j < 256; j++) {
/* 3067 */         if (count[j] != 0)
/* 3068 */           lastUsed = j; 
/* 3069 */         pos[j] = n += count[j];
/*      */       } 
/* 3071 */       int end = first + length - count[lastUsed];
/*      */       
/* 3073 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 3074 */         int i1; for (i1 = layers; i1-- != 0;)
/* 3075 */           t[i1] = a[i1][m]; 
/* 3076 */         c = (int)(fixDouble(k[m]) >>> shift & 0xFFL ^ signMask);
/* 3077 */         if (m < end) {
/* 3078 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 3079 */             c = (int)(fixDouble(k[d]) >>> shift & 0xFFL ^ signMask);
/* 3080 */             for (i1 = layers; i1-- != 0; ) {
/* 3081 */               double u = t[i1];
/* 3082 */               t[i1] = a[i1][d];
/* 3083 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 3086 */           for (i1 = layers; i1-- != 0;)
/* 3087 */             a[i1][m] = t[i1]; 
/*      */         } 
/* 3089 */         if (level < maxLevel && count[c] > 1) {
/* 3090 */           if (count[c] < 1024) {
/* 3091 */             selectionSort(a, m, m + count[c], level + 1);
/*      */           } else {
/* 3093 */             offsetStack[stackPos] = m;
/* 3094 */             lengthStack[stackPos] = count[c];
/* 3095 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static double[] shuffle(double[] a, int from, int to, Random random) {
/* 3116 */     for (int i = to - from; i-- != 0; ) {
/* 3117 */       int p = random.nextInt(i + 1);
/* 3118 */       double t = a[from + i];
/* 3119 */       a[from + i] = a[from + p];
/* 3120 */       a[from + p] = t;
/*      */     } 
/* 3122 */     return a;
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
/*      */   public static double[] shuffle(double[] a, Random random) {
/* 3135 */     for (int i = a.length; i-- != 0; ) {
/* 3136 */       int p = random.nextInt(i + 1);
/* 3137 */       double t = a[i];
/* 3138 */       a[i] = a[p];
/* 3139 */       a[p] = t;
/*      */     } 
/* 3141 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] reverse(double[] a) {
/* 3151 */     int length = a.length;
/* 3152 */     for (int i = length / 2; i-- != 0; ) {
/* 3153 */       double t = a[length - i - 1];
/* 3154 */       a[length - i - 1] = a[i];
/* 3155 */       a[i] = t;
/*      */     } 
/* 3157 */     return a;
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
/*      */   public static double[] reverse(double[] a, int from, int to) {
/* 3171 */     int length = to - from;
/* 3172 */     for (int i = length / 2; i-- != 0; ) {
/* 3173 */       double t = a[from + length - i - 1];
/* 3174 */       a[from + length - i - 1] = a[from + i];
/* 3175 */       a[from + i] = t;
/*      */     } 
/* 3177 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<double[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(double[] o) {
/* 3184 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(double[] a, double[] b) {
/* 3188 */       return Arrays.equals(a, b);
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
/* 3199 */   public static final Hash.Strategy<double[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */