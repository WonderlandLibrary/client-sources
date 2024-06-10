/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public final class ShortArrays
/*      */ {
/*  103 */   public static final short[] EMPTY_ARRAY = new short[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final short[] DEFAULT_EMPTY_ARRAY = new short[0];
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 16;
/*      */   
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 2;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 1000;
/*      */   
/*      */   public static short[] forceCapacity(short[] array, int length, int preserve) {
/*  128 */     short[] t = new short[length];
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
/*      */   public static short[] ensureCapacity(short[] array, int length) {
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
/*      */   public static short[] ensureCapacity(short[] array, int length, int preserve) {
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
/*      */   public static short[] grow(short[] array, int length) {
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
/*      */   public static short[] grow(short[] array, int length, int preserve) {
/*  211 */     if (length > array.length) {
/*      */       
/*  213 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  214 */       short[] t = new short[newLength];
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
/*      */   public static short[] trim(short[] array, int length) {
/*  233 */     if (length >= array.length)
/*  234 */       return array; 
/*  235 */     short[] t = (length == 0) ? EMPTY_ARRAY : new short[length];
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
/*      */   public static short[] setLength(short[] array, int length) {
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
/*      */   public static short[] copy(short[] array, int offset, int length) {
/*  274 */     ensureOffsetLength(array, offset, length);
/*  275 */     short[] a = (length == 0) ? EMPTY_ARRAY : new short[length];
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
/*      */   public static short[] copy(short[] array) {
/*  287 */     return (short[])array.clone();
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
/*      */   public static void fill(short[] array, short value) {
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
/*      */   public static void fill(short[] array, int from, int to, short value) {
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
/*      */   public static boolean equals(short[] a1, short[] a2) {
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
/*      */   public static void ensureFromTo(short[] a, int from, int to) {
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
/*      */   public static void ensureOffsetLength(short[] a, int offset, int length) {
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
/*      */   public static void ensureSameLength(short[] a, short[] b) {
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
/*      */   public static void swap(short[] x, int a, int b) {
/*  422 */     short t = x[a];
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
/*      */   public static void swap(short[] x, int a, int b, int n) {
/*  440 */     for (int i = 0; i < n; i++, a++, b++)
/*  441 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(short[] x, int a, int b, int c, ShortComparator comp) {
/*  444 */     int ab = comp.compare(x[a], x[b]);
/*  445 */     int ac = comp.compare(x[a], x[c]);
/*  446 */     int bc = comp.compare(x[b], x[c]);
/*  447 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(short[] a, int from, int to, ShortComparator comp) {
/*  450 */     for (int i = from; i < to - 1; i++) {
/*  451 */       int m = i;
/*  452 */       for (int j = i + 1; j < to; j++) {
/*  453 */         if (comp.compare(a[j], a[m]) < 0)
/*  454 */           m = j; 
/*  455 */       }  if (m != i) {
/*  456 */         short u = a[i];
/*  457 */         a[i] = a[m];
/*  458 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(short[] a, int from, int to, ShortComparator comp) {
/*  463 */     for (int i = from; ++i < to; ) {
/*  464 */       short t = a[i];
/*  465 */       int j = i; short u;
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
/*      */   public static void quickSort(short[] x, int from, int to, ShortComparator comp) {
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
/*  518 */     short v = x[m];
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
/*      */   public static void quickSort(short[] x, ShortComparator comp) {
/*  570 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final short[] x;
/*      */     private final ShortComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(short[] x, int from, int to, ShortComparator comp) {
/*  579 */       this.from = from;
/*  580 */       this.to = to;
/*  581 */       this.x = x;
/*  582 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  586 */       short[] x = this.x;
/*  587 */       int len = this.to - this.from;
/*  588 */       if (len < 8192) {
/*  589 */         ShortArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  593 */       int m = this.from + len / 2;
/*  594 */       int l = this.from;
/*  595 */       int n = this.to - 1;
/*  596 */       int s = len / 8;
/*  597 */       l = ShortArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  598 */       m = ShortArrays.med3(x, m - s, m, m + s, this.comp);
/*  599 */       n = ShortArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  600 */       m = ShortArrays.med3(x, l, m, n, this.comp);
/*  601 */       short v = x[m];
/*      */       
/*  603 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  606 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  607 */           if (comparison == 0)
/*  608 */             ShortArrays.swap(x, a++, b); 
/*  609 */           b++; continue;
/*      */         } 
/*  611 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  612 */           if (comparison == 0)
/*  613 */             ShortArrays.swap(x, c, d--); 
/*  614 */           c--;
/*      */         } 
/*  616 */         if (b > c)
/*      */           break; 
/*  618 */         ShortArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  622 */       s = Math.min(a - this.from, b - a);
/*  623 */       ShortArrays.swap(x, this.from, b - s, s);
/*  624 */       s = Math.min(d - c, this.to - d - 1);
/*  625 */       ShortArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[] x, int from, int to, ShortComparator comp) {
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
/*      */   public static void parallelQuickSort(short[] x, ShortComparator comp) {
/*  688 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(short[] x, int a, int b, int c) {
/*  692 */     int ab = Short.compare(x[a], x[b]);
/*  693 */     int ac = Short.compare(x[a], x[c]);
/*  694 */     int bc = Short.compare(x[b], x[c]);
/*  695 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(short[] a, int from, int to) {
/*  699 */     for (int i = from; i < to - 1; i++) {
/*  700 */       int m = i;
/*  701 */       for (int j = i + 1; j < to; j++) {
/*  702 */         if (a[j] < a[m])
/*  703 */           m = j; 
/*  704 */       }  if (m != i) {
/*  705 */         short u = a[i];
/*  706 */         a[i] = a[m];
/*  707 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(short[] a, int from, int to) {
/*  713 */     for (int i = from; ++i < to; ) {
/*  714 */       short t = a[i];
/*  715 */       int j = i; short u;
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
/*      */   public static void quickSort(short[] x, int from, int to) {
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
/*  766 */     short v = x[m];
/*      */     
/*  768 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  771 */       if (b <= c && (comparison = Short.compare(x[b], v)) <= 0) {
/*  772 */         if (comparison == 0)
/*  773 */           swap(x, a++, b); 
/*  774 */         b++; continue;
/*      */       } 
/*  776 */       while (c >= b && (comparison = Short.compare(x[c], v)) >= 0) {
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
/*      */   public static void quickSort(short[] x) {
/*  815 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final short[] x;
/*      */     
/*      */     public ForkJoinQuickSort(short[] x, int from, int to) {
/*  823 */       this.from = from;
/*  824 */       this.to = to;
/*  825 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  830 */       short[] x = this.x;
/*  831 */       int len = this.to - this.from;
/*  832 */       if (len < 8192) {
/*  833 */         ShortArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  837 */       int m = this.from + len / 2;
/*  838 */       int l = this.from;
/*  839 */       int n = this.to - 1;
/*  840 */       int s = len / 8;
/*  841 */       l = ShortArrays.med3(x, l, l + s, l + 2 * s);
/*  842 */       m = ShortArrays.med3(x, m - s, m, m + s);
/*  843 */       n = ShortArrays.med3(x, n - 2 * s, n - s, n);
/*  844 */       m = ShortArrays.med3(x, l, m, n);
/*  845 */       short v = x[m];
/*      */       
/*  847 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  850 */         if (b <= c && (comparison = Short.compare(x[b], v)) <= 0) {
/*  851 */           if (comparison == 0)
/*  852 */             ShortArrays.swap(x, a++, b); 
/*  853 */           b++; continue;
/*      */         } 
/*  855 */         while (c >= b && (comparison = Short.compare(x[c], v)) >= 0) {
/*  856 */           if (comparison == 0)
/*  857 */             ShortArrays.swap(x, c, d--); 
/*  858 */           c--;
/*      */         } 
/*  860 */         if (b > c)
/*      */           break; 
/*  862 */         ShortArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  866 */       s = Math.min(a - this.from, b - a);
/*  867 */       ShortArrays.swap(x, this.from, b - s, s);
/*  868 */       s = Math.min(d - c, this.to - d - 1);
/*  869 */       ShortArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[] x, int from, int to) {
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
/*      */   public static void parallelQuickSort(short[] x) {
/*  928 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, short[] x, int a, int b, int c) {
/*  932 */     short aa = x[perm[a]];
/*  933 */     short bb = x[perm[b]];
/*  934 */     short cc = x[perm[c]];
/*  935 */     int ab = Short.compare(aa, bb);
/*  936 */     int ac = Short.compare(aa, cc);
/*  937 */     int bc = Short.compare(bb, cc);
/*  938 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, short[] a, int from, int to) {
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
/*      */   public static void quickSortIndirect(int[] perm, short[] x, int from, int to) {
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
/* 1002 */     short v = x[perm[m]];
/*      */     
/* 1004 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1007 */       if (b <= c && (comparison = Short.compare(x[perm[b]], v)) <= 0) {
/* 1008 */         if (comparison == 0)
/* 1009 */           IntArrays.swap(perm, a++, b); 
/* 1010 */         b++; continue;
/*      */       } 
/* 1012 */       while (c >= b && (comparison = Short.compare(x[perm[c]], v)) >= 0) {
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
/*      */   public static void quickSortIndirect(int[] perm, short[] x) {
/* 1058 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final short[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, short[] x, int from, int to) {
/* 1067 */       this.from = from;
/* 1068 */       this.to = to;
/* 1069 */       this.x = x;
/* 1070 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1075 */       short[] x = this.x;
/* 1076 */       int len = this.to - this.from;
/* 1077 */       if (len < 8192) {
/* 1078 */         ShortArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1082 */       int m = this.from + len / 2;
/* 1083 */       int l = this.from;
/* 1084 */       int n = this.to - 1;
/* 1085 */       int s = len / 8;
/* 1086 */       l = ShortArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1087 */       m = ShortArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1088 */       n = ShortArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1089 */       m = ShortArrays.med3Indirect(this.perm, x, l, m, n);
/* 1090 */       short v = x[this.perm[m]];
/*      */       
/* 1092 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1095 */         if (b <= c && (comparison = Short.compare(x[this.perm[b]], v)) <= 0) {
/* 1096 */           if (comparison == 0)
/* 1097 */             IntArrays.swap(this.perm, a++, b); 
/* 1098 */           b++; continue;
/*      */         } 
/* 1100 */         while (c >= b && (comparison = Short.compare(x[this.perm[c]], v)) >= 0) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, short[] x, int from, int to) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, short[] x) {
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
/*      */   public static void stabilize(int[] perm, short[] x, int from, int to) {
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
/*      */   public static void stabilize(int[] perm, short[] x) {
/* 1259 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(short[] x, short[] y, int a, int b, int c) {
/* 1264 */     int t, ab = ((t = Short.compare(x[a], x[b])) == 0) ? Short.compare(y[a], y[b]) : t;
/* 1265 */     int ac = ((t = Short.compare(x[a], x[c])) == 0) ? Short.compare(y[a], y[c]) : t;
/* 1266 */     int bc = ((t = Short.compare(x[b], x[c])) == 0) ? Short.compare(y[b], y[c]) : t;
/* 1267 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(short[] x, short[] y, int a, int b) {
/* 1270 */     short t = x[a];
/* 1271 */     short u = y[a];
/* 1272 */     x[a] = x[b];
/* 1273 */     y[a] = y[b];
/* 1274 */     x[b] = t;
/* 1275 */     y[b] = u;
/*      */   }
/*      */   private static void swap(short[] x, short[] y, int a, int b, int n) {
/* 1278 */     for (int i = 0; i < n; i++, a++, b++)
/* 1279 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(short[] a, short[] b, int from, int to) {
/* 1283 */     for (int i = from; i < to - 1; i++) {
/* 1284 */       int m = i;
/* 1285 */       for (int j = i + 1; j < to; j++) {
/* 1286 */         int u; if ((u = Short.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m]))
/* 1287 */           m = j; 
/* 1288 */       }  if (m != i) {
/* 1289 */         short t = a[i];
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
/*      */   public static void quickSort(short[] x, short[] y, int from, int to) {
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
/* 1341 */     short v = x[m], w = y[m];
/*      */     
/* 1343 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1346 */       if (b <= c) {
/* 1347 */         int comparison; int t; if ((comparison = ((t = Short.compare(x[b], v)) == 0) ? Short.compare(y[b], w) : t) <= 0) {
/* 1348 */           if (comparison == 0)
/* 1349 */             swap(x, y, a++, b); 
/* 1350 */           b++; continue;
/*      */         } 
/* 1352 */       }  while (c >= b) {
/* 1353 */         int comparison; int t; if ((comparison = ((t = Short.compare(x[c], v)) == 0) ? Short.compare(y[c], w) : t) >= 0) {
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
/*      */   public static void quickSort(short[] x, short[] y) {
/* 1396 */     ensureSameLength(x, y);
/* 1397 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L; private final int from;
/*      */     private final int to;
/*      */     private final short[] x;
/*      */     private final short[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(short[] x, short[] y, int from, int to) {
/* 1405 */       this.from = from;
/* 1406 */       this.to = to;
/* 1407 */       this.x = x;
/* 1408 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1413 */       short[] x = this.x;
/* 1414 */       short[] y = this.y;
/* 1415 */       int len = this.to - this.from;
/* 1416 */       if (len < 8192) {
/* 1417 */         ShortArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1421 */       int m = this.from + len / 2;
/* 1422 */       int l = this.from;
/* 1423 */       int n = this.to - 1;
/* 1424 */       int s = len / 8;
/* 1425 */       l = ShortArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1426 */       m = ShortArrays.med3(x, y, m - s, m, m + s);
/* 1427 */       n = ShortArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1428 */       m = ShortArrays.med3(x, y, l, m, n);
/* 1429 */       short v = x[m], w = y[m];
/*      */       
/* 1431 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1434 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1436 */           if ((comparison = ((i = Short.compare(x[b], v)) == 0) ? Short.compare(y[b], w) : i) <= 0) {
/* 1437 */             if (comparison == 0)
/* 1438 */               ShortArrays.swap(x, y, a++, b); 
/* 1439 */             b++; continue;
/*      */           } 
/* 1441 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1443 */           if ((comparison = ((i = Short.compare(x[c], v)) == 0) ? Short.compare(y[c], w) : i) >= 0) {
/* 1444 */             if (comparison == 0)
/* 1445 */               ShortArrays.swap(x, y, c, d--); 
/* 1446 */             c--;
/*      */           } 
/* 1448 */         }  if (b > c)
/*      */           break; 
/* 1450 */         ShortArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1454 */       s = Math.min(a - this.from, b - a);
/* 1455 */       ShortArrays.swap(x, y, this.from, b - s, s);
/* 1456 */       s = Math.min(d - c, this.to - d - 1);
/* 1457 */       ShortArrays.swap(x, y, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(short[] x, short[] y, int from, int to) {
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
/*      */   public static void parallelQuickSort(short[] x, short[] y) {
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
/*      */   public static void unstableSort(short[] a, int from, int to) {
/* 1552 */     if (to - from >= 1000) {
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
/*      */   public static void unstableSort(short[] a) {
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
/*      */   public static void unstableSort(short[] a, int from, int to, ShortComparator comp) {
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
/*      */   public static void unstableSort(short[] a, ShortComparator comp) {
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
/*      */   public static void mergeSort(short[] a, int from, int to, short[] supp) {
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
/*      */   public static void mergeSort(short[] a, int from, int to) {
/* 1667 */     mergeSort(a, from, to, (short[])a.clone());
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
/*      */   public static void mergeSort(short[] a) {
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
/*      */   
/*      */   public static void mergeSort(short[] a, int from, int to, ShortComparator comp, short[] supp) {
/* 1707 */     int len = to - from;
/*      */     
/* 1709 */     if (len < 16) {
/* 1710 */       insertionSort(a, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/* 1714 */     int mid = from + to >>> 1;
/* 1715 */     mergeSort(supp, from, mid, comp, a);
/* 1716 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1719 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1720 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1724 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1725 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
/* 1726 */         a[i] = supp[p++];
/*      */       } else {
/* 1728 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(short[] a, int from, int to, ShortComparator comp) {
/* 1750 */     mergeSort(a, from, to, comp, (short[])a.clone());
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
/*      */   public static void mergeSort(short[] a, ShortComparator comp) {
/* 1767 */     mergeSort(a, 0, a.length, comp);
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
/*      */   public static void stableSort(short[] a, int from, int to) {
/* 1792 */     unstableSort(a, from, to);
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
/*      */   public static void stableSort(short[] a) {
/* 1810 */     stableSort(a, 0, a.length);
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
/*      */   public static void stableSort(short[] a, int from, int to, ShortComparator comp) {
/* 1834 */     mergeSort(a, from, to, comp);
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
/*      */   public static void stableSort(short[] a, ShortComparator comp) {
/* 1854 */     stableSort(a, 0, a.length, comp);
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
/*      */   public static int binarySearch(short[] a, int from, int to, short key) {
/* 1883 */     to--;
/* 1884 */     while (from <= to) {
/* 1885 */       int mid = from + to >>> 1;
/* 1886 */       short midVal = a[mid];
/* 1887 */       if (midVal < key) {
/* 1888 */         from = mid + 1; continue;
/* 1889 */       }  if (midVal > key) {
/* 1890 */         to = mid - 1; continue;
/*      */       } 
/* 1892 */       return mid;
/*      */     } 
/* 1894 */     return -(from + 1);
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
/*      */   public static int binarySearch(short[] a, short key) {
/* 1916 */     return binarySearch(a, 0, a.length, key);
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
/*      */   public static int binarySearch(short[] a, int from, int to, short key, ShortComparator c) {
/* 1946 */     to--;
/* 1947 */     while (from <= to) {
/* 1948 */       int mid = from + to >>> 1;
/* 1949 */       short midVal = a[mid];
/* 1950 */       int cmp = c.compare(midVal, key);
/* 1951 */       if (cmp < 0) {
/* 1952 */         from = mid + 1; continue;
/* 1953 */       }  if (cmp > 0) {
/* 1954 */         to = mid - 1; continue;
/*      */       } 
/* 1956 */       return mid;
/*      */     } 
/* 1958 */     return -(from + 1);
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
/*      */   public static int binarySearch(short[] a, short key, ShortComparator c) {
/* 1983 */     return binarySearch(a, 0, a.length, key, c);
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
/*      */   public static void radixSort(short[] a) {
/* 2019 */     radixSort(a, 0, a.length);
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
/*      */   public static void radixSort(short[] a, int from, int to) {
/* 2042 */     if (to - from < 1024) {
/* 2043 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2046 */     int maxLevel = 1;
/* 2047 */     int stackSize = 256;
/* 2048 */     int stackPos = 0;
/* 2049 */     int[] offsetStack = new int[256];
/* 2050 */     int[] lengthStack = new int[256];
/* 2051 */     int[] levelStack = new int[256];
/* 2052 */     offsetStack[stackPos] = from;
/* 2053 */     lengthStack[stackPos] = to - from;
/* 2054 */     levelStack[stackPos++] = 0;
/* 2055 */     int[] count = new int[256];
/* 2056 */     int[] pos = new int[256];
/* 2057 */     while (stackPos > 0) {
/* 2058 */       int first = offsetStack[--stackPos];
/* 2059 */       int length = lengthStack[stackPos];
/* 2060 */       int level = levelStack[stackPos];
/* 2061 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2062 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2067 */       for (int i = first + length; i-- != first;) {
/* 2068 */         count[a[i] >>> shift & 0xFF ^ signMask] = count[a[i] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2070 */       int lastUsed = -1;
/* 2071 */       for (int j = 0, p = first; j < 256; j++) {
/* 2072 */         if (count[j] != 0)
/* 2073 */           lastUsed = j; 
/* 2074 */         pos[j] = p += count[j];
/*      */       } 
/* 2076 */       int end = first + length - count[lastUsed];
/*      */       
/* 2078 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2079 */         short t = a[k];
/* 2080 */         c = t >>> shift & 0xFF ^ signMask;
/* 2081 */         if (k < end) {
/* 2082 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2083 */             short z = t;
/* 2084 */             t = a[d];
/* 2085 */             a[d] = z;
/* 2086 */             c = t >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2088 */           a[k] = t;
/*      */         } 
/* 2090 */         if (level < 1 && count[c] > 1)
/* 2091 */           if (count[c] < 1024) {
/* 2092 */             quickSort(a, k, k + count[c]);
/*      */           } else {
/* 2094 */             offsetStack[stackPos] = k;
/* 2095 */             lengthStack[stackPos] = count[c];
/* 2096 */             levelStack[stackPos++] = level + 1;
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected static final class Segment { protected final int offset; protected final int length;
/*      */     protected final int level;
/*      */     
/*      */     protected Segment(int offset, int length, int level) {
/* 2105 */       this.offset = offset;
/* 2106 */       this.length = length;
/* 2107 */       this.level = level;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2111 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */   
/* 2114 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(short[] a, int from, int to) {
/* 2135 */     if (to - from < 1024) {
/* 2136 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2139 */     int maxLevel = 1;
/* 2140 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2141 */     queue.add(new Segment(from, to - from, 0));
/* 2142 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2143 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2144 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2145 */         Executors.defaultThreadFactory());
/* 2146 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2148 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2149 */       executorCompletionService.submit(() -> {
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
/*      */               int signMask = (level % 2 == 0) ? 128 : 0;
/*      */               int shift = (1 - level % 2) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[a[i] >>> shift & 0xFF ^ signMask] = count[a[i] >>> shift & 0xFF ^ signMask] + 1; 
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
/*      */                 short t = a[k];
/*      */                 c = t >>> shift & 0xFF ^ signMask;
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     short z = t;
/*      */                     t = a[d];
/*      */                     a[d] = z;
/*      */                     c = t >>> shift & 0xFF ^ signMask;
/*      */                   } 
/*      */                   a[k] = t;
/*      */                 } 
/*      */                 if (level < 1 && count[c] > 1)
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
/* 2206 */     Throwable problem = null;
/* 2207 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2209 */         executorCompletionService.take().get();
/* 2210 */       } catch (Exception e) {
/* 2211 */         problem = e.getCause();
/*      */       } 
/* 2213 */     }  executorService.shutdown();
/* 2214 */     if (problem != null) {
/* 2215 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(short[] a) {
/* 2233 */     parallelRadixSort(a, 0, a.length);
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
/*      */   public static void radixSortIndirect(int[] perm, short[] a, boolean stable) {
/* 2260 */     radixSortIndirect(perm, a, 0, perm.length, stable);
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
/*      */   public static void radixSortIndirect(int[] perm, short[] a, int from, int to, boolean stable) {
/* 2294 */     if (to - from < 1024) {
/* 2295 */       insertionSortIndirect(perm, a, from, to);
/*      */       return;
/*      */     } 
/* 2298 */     int maxLevel = 1;
/* 2299 */     int stackSize = 256;
/* 2300 */     int stackPos = 0;
/* 2301 */     int[] offsetStack = new int[256];
/* 2302 */     int[] lengthStack = new int[256];
/* 2303 */     int[] levelStack = new int[256];
/* 2304 */     offsetStack[stackPos] = from;
/* 2305 */     lengthStack[stackPos] = to - from;
/* 2306 */     levelStack[stackPos++] = 0;
/* 2307 */     int[] count = new int[256];
/* 2308 */     int[] pos = new int[256];
/* 2309 */     int[] support = stable ? new int[perm.length] : null;
/* 2310 */     while (stackPos > 0) {
/* 2311 */       int first = offsetStack[--stackPos];
/* 2312 */       int length = lengthStack[stackPos];
/* 2313 */       int level = levelStack[stackPos];
/* 2314 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2315 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2320 */       for (int i = first + length; i-- != first;) {
/* 2321 */         count[a[perm[i]] >>> shift & 0xFF ^ signMask] = count[a[perm[i]] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2323 */       int lastUsed = -1; int j, p;
/* 2324 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2325 */         if (count[j] != 0)
/* 2326 */           lastUsed = j; 
/* 2327 */         pos[j] = p += count[j];
/*      */       } 
/* 2329 */       if (stable) {
/* 2330 */         for (j = first + length; j-- != first; ) {
/* 2331 */           pos[a[perm[j]] >>> shift & 0xFF ^ signMask] = pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/* 2332 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2333 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2334 */           if (level < 1 && count[j] > 1) {
/* 2335 */             if (count[j] < 1024) {
/* 2336 */               insertionSortIndirect(perm, a, p, p + count[j]);
/*      */             } else {
/* 2338 */               offsetStack[stackPos] = p;
/* 2339 */               lengthStack[stackPos] = count[j];
/* 2340 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2343 */           p += count[j];
/*      */         } 
/* 2345 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2347 */       int end = first + length - count[lastUsed];
/*      */       
/* 2349 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2350 */         int t = perm[k];
/* 2351 */         c = a[t] >>> shift & 0xFF ^ signMask;
/* 2352 */         if (k < end) {
/* 2353 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2354 */             int z = t;
/* 2355 */             t = perm[d];
/* 2356 */             perm[d] = z;
/* 2357 */             c = a[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2359 */           perm[k] = t;
/*      */         } 
/* 2361 */         if (level < 1 && count[c] > 1) {
/* 2362 */           if (count[c] < 1024) {
/* 2363 */             insertionSortIndirect(perm, a, k, k + count[c]);
/*      */           } else {
/* 2365 */             offsetStack[stackPos] = k;
/* 2366 */             lengthStack[stackPos] = count[c];
/* 2367 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, short[] a, int from, int to, boolean stable) {
/* 2404 */     if (to - from < 1024) {
/* 2405 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2408 */     int maxLevel = 1;
/* 2409 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2410 */     queue.add(new Segment(from, to - from, 0));
/* 2411 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2412 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2413 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2414 */         Executors.defaultThreadFactory());
/* 2415 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2417 */     int[] support = stable ? new int[perm.length] : null;
/* 2418 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2419 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int k = numberOfThreads; while (k-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level;
/*      */               int signMask = (level % 2 == 0) ? 128 : 0;
/*      */               int shift = (1 - level % 2) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[a[perm[i]] >>> shift & 0xFF ^ signMask] = count[a[perm[i]] >>> shift & 0xFF ^ signMask] + 1; 
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
/*      */                   pos[a[perm[j]] >>> shift & 0xFF ^ signMask] = pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1;
/*      */                   support[pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/*      */                 } 
/*      */                 System.arraycopy(support, first, perm, first, length);
/*      */                 j = 0;
/*      */                 p = first;
/*      */                 while (j <= lastUsed) {
/*      */                   if (level < 1 && count[j] > 1)
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
/*      */                   c = a[t] >>> shift & 0xFF ^ signMask;
/*      */                   if (k < end) {
/*      */                     pos[c] = pos[c] - 1;
/*      */                     int d;
/*      */                     while ((d = pos[c] - 1) > k) {
/*      */                       int z = t;
/*      */                       t = perm[d];
/*      */                       perm[d] = z;
/*      */                       c = a[t] >>> shift & 0xFF ^ signMask;
/*      */                     } 
/*      */                     perm[k] = t;
/*      */                   } 
/*      */                   if (level < 1 && count[c] > 1)
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
/* 2494 */     Throwable problem = null;
/* 2495 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2497 */         executorCompletionService.take().get();
/* 2498 */       } catch (Exception e) {
/* 2499 */         problem = e.getCause();
/*      */       } 
/* 2501 */     }  executorService.shutdown();
/* 2502 */     if (problem != null) {
/* 2503 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, short[] a, boolean stable) {
/* 2530 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
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
/*      */   public static void radixSort(short[] a, short[] b) {
/* 2552 */     ensureSameLength(a, b);
/* 2553 */     radixSort(a, b, 0, a.length);
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
/*      */   public static void radixSort(short[] a, short[] b, int from, int to) {
/* 2580 */     if (to - from < 1024) {
/* 2581 */       selectionSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2584 */     int layers = 2;
/* 2585 */     int maxLevel = 3;
/* 2586 */     int stackSize = 766;
/* 2587 */     int stackPos = 0;
/* 2588 */     int[] offsetStack = new int[766];
/* 2589 */     int[] lengthStack = new int[766];
/* 2590 */     int[] levelStack = new int[766];
/* 2591 */     offsetStack[stackPos] = from;
/* 2592 */     lengthStack[stackPos] = to - from;
/* 2593 */     levelStack[stackPos++] = 0;
/* 2594 */     int[] count = new int[256];
/* 2595 */     int[] pos = new int[256];
/* 2596 */     while (stackPos > 0) {
/* 2597 */       int first = offsetStack[--stackPos];
/* 2598 */       int length = lengthStack[stackPos];
/* 2599 */       int level = levelStack[stackPos];
/* 2600 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2601 */       short[] k = (level < 2) ? a : b;
/* 2602 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2607 */       for (int i = first + length; i-- != first;) {
/* 2608 */         count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2610 */       int lastUsed = -1;
/* 2611 */       for (int j = 0, p = first; j < 256; j++) {
/* 2612 */         if (count[j] != 0)
/* 2613 */           lastUsed = j; 
/* 2614 */         pos[j] = p += count[j];
/*      */       } 
/* 2616 */       int end = first + length - count[lastUsed];
/*      */       
/* 2618 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2619 */         short t = a[m];
/* 2620 */         short u = b[m];
/* 2621 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2622 */         if (m < end) {
/* 2623 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2624 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2625 */             short z = t;
/* 2626 */             t = a[d];
/* 2627 */             a[d] = z;
/* 2628 */             z = u;
/* 2629 */             u = b[d];
/* 2630 */             b[d] = z;
/*      */           } 
/* 2632 */           a[m] = t;
/* 2633 */           b[m] = u;
/*      */         } 
/* 2635 */         if (level < 3 && count[c] > 1) {
/* 2636 */           if (count[c] < 1024) {
/* 2637 */             selectionSort(a, b, m, m + count[c]);
/*      */           } else {
/* 2639 */             offsetStack[stackPos] = m;
/* 2640 */             lengthStack[stackPos] = count[c];
/* 2641 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSort(short[] a, short[] b, int from, int to) {
/* 2677 */     if (to - from < 1024) {
/* 2678 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2681 */     int layers = 2;
/* 2682 */     if (a.length != b.length)
/* 2683 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 2684 */     int maxLevel = 3;
/* 2685 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2686 */     queue.add(new Segment(from, to - from, 0));
/* 2687 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2688 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2689 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2690 */         Executors.defaultThreadFactory());
/* 2691 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2693 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2694 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int n = numberOfThreads; while (n-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 2 == 0) ? 128 : 0; short[] k = (level < 2) ? a : b;
/*      */               int shift = (1 - level % 2) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1; 
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
/*      */                 short t = a[m];
/*      */                 short u = b[m];
/*      */                 c = k[m] >>> shift & 0xFF ^ signMask;
/*      */                 if (m < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > m) {
/*      */                     c = k[d] >>> shift & 0xFF ^ signMask;
/*      */                     short z = t;
/*      */                     short w = u;
/*      */                     t = a[d];
/*      */                     u = b[d];
/*      */                     a[d] = z;
/*      */                     b[d] = w;
/*      */                   } 
/*      */                   a[m] = t;
/*      */                   b[m] = u;
/*      */                 } 
/*      */                 if (level < 3 && count[c] > 1)
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
/* 2750 */     Throwable problem = null;
/* 2751 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2753 */         executorCompletionService.take().get();
/* 2754 */       } catch (Exception e) {
/* 2755 */         problem = e.getCause();
/*      */       } 
/* 2757 */     }  executorService.shutdown();
/* 2758 */     if (problem != null) {
/* 2759 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(short[] a, short[] b) {
/* 2786 */     ensureSameLength(a, b);
/* 2787 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, short[] a, short[] b, int from, int to) {
/* 2791 */     for (int i = from; ++i < to; ) {
/* 2792 */       int t = perm[i];
/* 2793 */       int j = i; int u;
/* 2794 */       for (u = perm[j - 1]; a[t] < a[u] || (a[t] == a[u] && b[t] < b[u]); u = perm[--j - 1]) {
/* 2795 */         perm[j] = u;
/* 2796 */         if (from == j - 1) {
/* 2797 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2801 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, short[] a, short[] b, boolean stable) {
/* 2835 */     ensureSameLength(a, b);
/* 2836 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
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
/*      */   public static void radixSortIndirect(int[] perm, short[] a, short[] b, int from, int to, boolean stable) {
/* 2876 */     if (to - from < 1024) {
/* 2877 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2880 */     int layers = 2;
/* 2881 */     int maxLevel = 3;
/* 2882 */     int stackSize = 766;
/* 2883 */     int stackPos = 0;
/* 2884 */     int[] offsetStack = new int[766];
/* 2885 */     int[] lengthStack = new int[766];
/* 2886 */     int[] levelStack = new int[766];
/* 2887 */     offsetStack[stackPos] = from;
/* 2888 */     lengthStack[stackPos] = to - from;
/* 2889 */     levelStack[stackPos++] = 0;
/* 2890 */     int[] count = new int[256];
/* 2891 */     int[] pos = new int[256];
/* 2892 */     int[] support = stable ? new int[perm.length] : null;
/* 2893 */     while (stackPos > 0) {
/* 2894 */       int first = offsetStack[--stackPos];
/* 2895 */       int length = lengthStack[stackPos];
/* 2896 */       int level = levelStack[stackPos];
/* 2897 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 2898 */       short[] k = (level < 2) ? a : b;
/* 2899 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2904 */       for (int i = first + length; i-- != first;) {
/* 2905 */         count[k[perm[i]] >>> shift & 0xFF ^ signMask] = count[k[perm[i]] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2907 */       int lastUsed = -1; int j, p;
/* 2908 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2909 */         if (count[j] != 0)
/* 2910 */           lastUsed = j; 
/* 2911 */         pos[j] = p += count[j];
/*      */       } 
/* 2913 */       if (stable) {
/* 2914 */         for (j = first + length; j-- != first; ) {
/* 2915 */           pos[k[perm[j]] >>> shift & 0xFF ^ signMask] = pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/* 2916 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2917 */         for (j = 0, p = first; j < 256; j++) {
/* 2918 */           if (level < 3 && count[j] > 1) {
/* 2919 */             if (count[j] < 1024) {
/* 2920 */               insertionSortIndirect(perm, a, b, p, p + count[j]);
/*      */             } else {
/* 2922 */               offsetStack[stackPos] = p;
/* 2923 */               lengthStack[stackPos] = count[j];
/* 2924 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2927 */           p += count[j];
/*      */         } 
/* 2929 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2931 */       int end = first + length - count[lastUsed];
/*      */       
/* 2933 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2934 */         int t = perm[m];
/* 2935 */         c = k[t] >>> shift & 0xFF ^ signMask;
/* 2936 */         if (m < end) {
/* 2937 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2938 */             int z = t;
/* 2939 */             t = perm[d];
/* 2940 */             perm[d] = z;
/* 2941 */             c = k[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2943 */           perm[m] = t;
/*      */         } 
/* 2945 */         if (level < 3 && count[c] > 1) {
/* 2946 */           if (count[c] < 1024) {
/* 2947 */             insertionSortIndirect(perm, a, b, m, m + count[c]);
/*      */           } else {
/* 2949 */             offsetStack[stackPos] = m;
/* 2950 */             lengthStack[stackPos] = count[c];
/* 2951 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void selectionSort(short[][] a, int from, int to, int level) {
/* 2959 */     int layers = a.length;
/* 2960 */     int firstLayer = level / 2;
/* 2961 */     for (int i = from; i < to - 1; i++) {
/* 2962 */       int m = i;
/* 2963 */       for (int j = i + 1; j < to; j++) {
/* 2964 */         for (int p = firstLayer; p < layers; p++) {
/* 2965 */           if (a[p][j] < a[p][m]) {
/* 2966 */             m = j; break;
/*      */           } 
/* 2968 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/*      */       } 
/* 2972 */       if (m != i) {
/* 2973 */         for (int p = layers; p-- != 0; ) {
/* 2974 */           short u = a[p][i];
/* 2975 */           a[p][i] = a[p][m];
/* 2976 */           a[p][m] = u;
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
/*      */   public static void radixSort(short[][] a) {
/* 2999 */     radixSort(a, 0, (a[0]).length);
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
/*      */   public static void radixSort(short[][] a, int from, int to) {
/* 3023 */     if (to - from < 1024) {
/* 3024 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 3027 */     int layers = a.length;
/* 3028 */     int maxLevel = 2 * layers - 1;
/* 3029 */     for (int p = layers, l = (a[0]).length; p-- != 0;) {
/* 3030 */       if ((a[p]).length != l)
/* 3031 */         throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0."); 
/*      */     } 
/* 3033 */     int stackSize = 255 * (layers * 2 - 1) + 1;
/* 3034 */     int stackPos = 0;
/* 3035 */     int[] offsetStack = new int[stackSize];
/* 3036 */     int[] lengthStack = new int[stackSize];
/* 3037 */     int[] levelStack = new int[stackSize];
/* 3038 */     offsetStack[stackPos] = from;
/* 3039 */     lengthStack[stackPos] = to - from;
/* 3040 */     levelStack[stackPos++] = 0;
/* 3041 */     int[] count = new int[256];
/* 3042 */     int[] pos = new int[256];
/* 3043 */     short[] t = new short[layers];
/* 3044 */     while (stackPos > 0) {
/* 3045 */       int first = offsetStack[--stackPos];
/* 3046 */       int length = lengthStack[stackPos];
/* 3047 */       int level = levelStack[stackPos];
/* 3048 */       int signMask = (level % 2 == 0) ? 128 : 0;
/* 3049 */       short[] k = a[level / 2];
/* 3050 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3055 */       for (int i = first + length; i-- != first;) {
/* 3056 */         count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 3058 */       int lastUsed = -1;
/* 3059 */       for (int j = 0, n = first; j < 256; j++) {
/* 3060 */         if (count[j] != 0)
/* 3061 */           lastUsed = j; 
/* 3062 */         pos[j] = n += count[j];
/*      */       } 
/* 3064 */       int end = first + length - count[lastUsed];
/*      */       
/* 3066 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 3067 */         int i1; for (i1 = layers; i1-- != 0;)
/* 3068 */           t[i1] = a[i1][m]; 
/* 3069 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 3070 */         if (m < end) {
/* 3071 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 3072 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 3073 */             for (i1 = layers; i1-- != 0; ) {
/* 3074 */               short u = t[i1];
/* 3075 */               t[i1] = a[i1][d];
/* 3076 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 3079 */           for (i1 = layers; i1-- != 0;)
/* 3080 */             a[i1][m] = t[i1]; 
/*      */         } 
/* 3082 */         if (level < maxLevel && count[c] > 1) {
/* 3083 */           if (count[c] < 1024) {
/* 3084 */             selectionSort(a, m, m + count[c], level + 1);
/*      */           } else {
/* 3086 */             offsetStack[stackPos] = m;
/* 3087 */             lengthStack[stackPos] = count[c];
/* 3088 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static short[] shuffle(short[] a, int from, int to, Random random) {
/* 3109 */     for (int i = to - from; i-- != 0; ) {
/* 3110 */       int p = random.nextInt(i + 1);
/* 3111 */       short t = a[from + i];
/* 3112 */       a[from + i] = a[from + p];
/* 3113 */       a[from + p] = t;
/*      */     } 
/* 3115 */     return a;
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
/*      */   public static short[] shuffle(short[] a, Random random) {
/* 3128 */     for (int i = a.length; i-- != 0; ) {
/* 3129 */       int p = random.nextInt(i + 1);
/* 3130 */       short t = a[i];
/* 3131 */       a[i] = a[p];
/* 3132 */       a[p] = t;
/*      */     } 
/* 3134 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] reverse(short[] a) {
/* 3144 */     int length = a.length;
/* 3145 */     for (int i = length / 2; i-- != 0; ) {
/* 3146 */       short t = a[length - i - 1];
/* 3147 */       a[length - i - 1] = a[i];
/* 3148 */       a[i] = t;
/*      */     } 
/* 3150 */     return a;
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
/*      */   public static short[] reverse(short[] a, int from, int to) {
/* 3164 */     int length = to - from;
/* 3165 */     for (int i = length / 2; i-- != 0; ) {
/* 3166 */       short t = a[from + length - i - 1];
/* 3167 */       a[from + length - i - 1] = a[from + i];
/* 3168 */       a[from + i] = t;
/*      */     } 
/* 3170 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<short[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(short[] o) {
/* 3177 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(short[] a, short[] b) {
/* 3181 */       return Arrays.equals(a, b);
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
/* 3192 */   public static final Hash.Strategy<short[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */