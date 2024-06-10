/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public final class FloatArrays
/*      */ {
/*  103 */   public static final float[] EMPTY_ARRAY = new float[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final float[] DEFAULT_EMPTY_ARRAY = new float[0];
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 16;
/*      */   
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 4;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 4000;
/*      */   
/*      */   public static float[] forceCapacity(float[] array, int length, int preserve) {
/*  128 */     float[] t = new float[length];
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
/*      */   public static float[] ensureCapacity(float[] array, int length) {
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
/*      */   public static float[] ensureCapacity(float[] array, int length, int preserve) {
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
/*      */   public static float[] grow(float[] array, int length) {
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
/*      */   public static float[] grow(float[] array, int length, int preserve) {
/*  211 */     if (length > array.length) {
/*      */       
/*  213 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  214 */       float[] t = new float[newLength];
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
/*      */   public static float[] trim(float[] array, int length) {
/*  233 */     if (length >= array.length)
/*  234 */       return array; 
/*  235 */     float[] t = (length == 0) ? EMPTY_ARRAY : new float[length];
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
/*      */   public static float[] setLength(float[] array, int length) {
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
/*      */   public static float[] copy(float[] array, int offset, int length) {
/*  274 */     ensureOffsetLength(array, offset, length);
/*  275 */     float[] a = (length == 0) ? EMPTY_ARRAY : new float[length];
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
/*      */   public static float[] copy(float[] array) {
/*  287 */     return (float[])array.clone();
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
/*      */   public static void fill(float[] array, float value) {
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
/*      */   public static void fill(float[] array, int from, int to, float value) {
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
/*      */   public static boolean equals(float[] a1, float[] a2) {
/*  342 */     int i = a1.length;
/*  343 */     if (i != a2.length)
/*  344 */       return false; 
/*  345 */     while (i-- != 0) {
/*  346 */       if (Float.floatToIntBits(a1[i]) != Float.floatToIntBits(a2[i]))
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
/*      */   public static void ensureFromTo(float[] a, int from, int to) {
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
/*      */   public static void ensureOffsetLength(float[] a, int offset, int length) {
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
/*      */   public static void ensureSameLength(float[] a, float[] b) {
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
/*      */   public static void swap(float[] x, int a, int b) {
/*  422 */     float t = x[a];
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
/*      */   public static void swap(float[] x, int a, int b, int n) {
/*  440 */     for (int i = 0; i < n; i++, a++, b++)
/*  441 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(float[] x, int a, int b, int c, FloatComparator comp) {
/*  444 */     int ab = comp.compare(x[a], x[b]);
/*  445 */     int ac = comp.compare(x[a], x[c]);
/*  446 */     int bc = comp.compare(x[b], x[c]);
/*  447 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(float[] a, int from, int to, FloatComparator comp) {
/*  450 */     for (int i = from; i < to - 1; i++) {
/*  451 */       int m = i;
/*  452 */       for (int j = i + 1; j < to; j++) {
/*  453 */         if (comp.compare(a[j], a[m]) < 0)
/*  454 */           m = j; 
/*  455 */       }  if (m != i) {
/*  456 */         float u = a[i];
/*  457 */         a[i] = a[m];
/*  458 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(float[] a, int from, int to, FloatComparator comp) {
/*  463 */     for (int i = from; ++i < to; ) {
/*  464 */       float t = a[i];
/*  465 */       int j = i; float u;
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
/*      */   public static void quickSort(float[] x, int from, int to, FloatComparator comp) {
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
/*  518 */     float v = x[m];
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
/*      */   public static void quickSort(float[] x, FloatComparator comp) {
/*  570 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final float[] x;
/*      */     private final FloatComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(float[] x, int from, int to, FloatComparator comp) {
/*  579 */       this.from = from;
/*  580 */       this.to = to;
/*  581 */       this.x = x;
/*  582 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  586 */       float[] x = this.x;
/*  587 */       int len = this.to - this.from;
/*  588 */       if (len < 8192) {
/*  589 */         FloatArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  593 */       int m = this.from + len / 2;
/*  594 */       int l = this.from;
/*  595 */       int n = this.to - 1;
/*  596 */       int s = len / 8;
/*  597 */       l = FloatArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  598 */       m = FloatArrays.med3(x, m - s, m, m + s, this.comp);
/*  599 */       n = FloatArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  600 */       m = FloatArrays.med3(x, l, m, n, this.comp);
/*  601 */       float v = x[m];
/*      */       
/*  603 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  606 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  607 */           if (comparison == 0)
/*  608 */             FloatArrays.swap(x, a++, b); 
/*  609 */           b++; continue;
/*      */         } 
/*  611 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  612 */           if (comparison == 0)
/*  613 */             FloatArrays.swap(x, c, d--); 
/*  614 */           c--;
/*      */         } 
/*  616 */         if (b > c)
/*      */           break; 
/*  618 */         FloatArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  622 */       s = Math.min(a - this.from, b - a);
/*  623 */       FloatArrays.swap(x, this.from, b - s, s);
/*  624 */       s = Math.min(d - c, this.to - d - 1);
/*  625 */       FloatArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(float[] x, int from, int to, FloatComparator comp) {
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
/*      */   public static void parallelQuickSort(float[] x, FloatComparator comp) {
/*  688 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(float[] x, int a, int b, int c) {
/*  692 */     int ab = Float.compare(x[a], x[b]);
/*  693 */     int ac = Float.compare(x[a], x[c]);
/*  694 */     int bc = Float.compare(x[b], x[c]);
/*  695 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(float[] a, int from, int to) {
/*  699 */     for (int i = from; i < to - 1; i++) {
/*  700 */       int m = i;
/*  701 */       for (int j = i + 1; j < to; j++) {
/*  702 */         if (Float.compare(a[j], a[m]) < 0)
/*  703 */           m = j; 
/*  704 */       }  if (m != i) {
/*  705 */         float u = a[i];
/*  706 */         a[i] = a[m];
/*  707 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(float[] a, int from, int to) {
/*  713 */     for (int i = from; ++i < to; ) {
/*  714 */       float t = a[i];
/*  715 */       int j = i; float u;
/*  716 */       for (u = a[j - 1]; Float.compare(t, u) < 0; u = a[--j - 1]) {
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
/*      */   public static void quickSort(float[] x, int from, int to) {
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
/*  766 */     float v = x[m];
/*      */     
/*  768 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  771 */       if (b <= c && (comparison = Float.compare(x[b], v)) <= 0) {
/*  772 */         if (comparison == 0)
/*  773 */           swap(x, a++, b); 
/*  774 */         b++; continue;
/*      */       } 
/*  776 */       while (c >= b && (comparison = Float.compare(x[c], v)) >= 0) {
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
/*      */   public static void quickSort(float[] x) {
/*  815 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final float[] x;
/*      */     
/*      */     public ForkJoinQuickSort(float[] x, int from, int to) {
/*  823 */       this.from = from;
/*  824 */       this.to = to;
/*  825 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  830 */       float[] x = this.x;
/*  831 */       int len = this.to - this.from;
/*  832 */       if (len < 8192) {
/*  833 */         FloatArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  837 */       int m = this.from + len / 2;
/*  838 */       int l = this.from;
/*  839 */       int n = this.to - 1;
/*  840 */       int s = len / 8;
/*  841 */       l = FloatArrays.med3(x, l, l + s, l + 2 * s);
/*  842 */       m = FloatArrays.med3(x, m - s, m, m + s);
/*  843 */       n = FloatArrays.med3(x, n - 2 * s, n - s, n);
/*  844 */       m = FloatArrays.med3(x, l, m, n);
/*  845 */       float v = x[m];
/*      */       
/*  847 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  850 */         if (b <= c && (comparison = Float.compare(x[b], v)) <= 0) {
/*  851 */           if (comparison == 0)
/*  852 */             FloatArrays.swap(x, a++, b); 
/*  853 */           b++; continue;
/*      */         } 
/*  855 */         while (c >= b && (comparison = Float.compare(x[c], v)) >= 0) {
/*  856 */           if (comparison == 0)
/*  857 */             FloatArrays.swap(x, c, d--); 
/*  858 */           c--;
/*      */         } 
/*  860 */         if (b > c)
/*      */           break; 
/*  862 */         FloatArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  866 */       s = Math.min(a - this.from, b - a);
/*  867 */       FloatArrays.swap(x, this.from, b - s, s);
/*  868 */       s = Math.min(d - c, this.to - d - 1);
/*  869 */       FloatArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(float[] x, int from, int to) {
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
/*      */   public static void parallelQuickSort(float[] x) {
/*  928 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, float[] x, int a, int b, int c) {
/*  932 */     float aa = x[perm[a]];
/*  933 */     float bb = x[perm[b]];
/*  934 */     float cc = x[perm[c]];
/*  935 */     int ab = Float.compare(aa, bb);
/*  936 */     int ac = Float.compare(aa, cc);
/*  937 */     int bc = Float.compare(bb, cc);
/*  938 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, float[] a, int from, int to) {
/*  942 */     for (int i = from; ++i < to; ) {
/*  943 */       int t = perm[i];
/*  944 */       int j = i; int u;
/*  945 */       for (u = perm[j - 1]; Float.compare(a[t], a[u]) < 0; u = perm[--j - 1]) {
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
/*      */   public static void quickSortIndirect(int[] perm, float[] x, int from, int to) {
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
/* 1002 */     float v = x[perm[m]];
/*      */     
/* 1004 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1007 */       if (b <= c && (comparison = Float.compare(x[perm[b]], v)) <= 0) {
/* 1008 */         if (comparison == 0)
/* 1009 */           IntArrays.swap(perm, a++, b); 
/* 1010 */         b++; continue;
/*      */       } 
/* 1012 */       while (c >= b && (comparison = Float.compare(x[perm[c]], v)) >= 0) {
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
/*      */   public static void quickSortIndirect(int[] perm, float[] x) {
/* 1058 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final float[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, float[] x, int from, int to) {
/* 1067 */       this.from = from;
/* 1068 */       this.to = to;
/* 1069 */       this.x = x;
/* 1070 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1075 */       float[] x = this.x;
/* 1076 */       int len = this.to - this.from;
/* 1077 */       if (len < 8192) {
/* 1078 */         FloatArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1082 */       int m = this.from + len / 2;
/* 1083 */       int l = this.from;
/* 1084 */       int n = this.to - 1;
/* 1085 */       int s = len / 8;
/* 1086 */       l = FloatArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1087 */       m = FloatArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1088 */       n = FloatArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1089 */       m = FloatArrays.med3Indirect(this.perm, x, l, m, n);
/* 1090 */       float v = x[this.perm[m]];
/*      */       
/* 1092 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1095 */         if (b <= c && (comparison = Float.compare(x[this.perm[b]], v)) <= 0) {
/* 1096 */           if (comparison == 0)
/* 1097 */             IntArrays.swap(this.perm, a++, b); 
/* 1098 */           b++; continue;
/*      */         } 
/* 1100 */         while (c >= b && (comparison = Float.compare(x[this.perm[c]], v)) >= 0) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, float[] x, int from, int to) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, float[] x) {
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
/*      */   public static void stabilize(int[] perm, float[] x, int from, int to) {
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
/*      */   public static void stabilize(int[] perm, float[] x) {
/* 1259 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(float[] x, float[] y, int a, int b, int c) {
/* 1264 */     int t, ab = ((t = Float.compare(x[a], x[b])) == 0) ? Float.compare(y[a], y[b]) : t;
/* 1265 */     int ac = ((t = Float.compare(x[a], x[c])) == 0) ? Float.compare(y[a], y[c]) : t;
/* 1266 */     int bc = ((t = Float.compare(x[b], x[c])) == 0) ? Float.compare(y[b], y[c]) : t;
/* 1267 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(float[] x, float[] y, int a, int b) {
/* 1270 */     float t = x[a];
/* 1271 */     float u = y[a];
/* 1272 */     x[a] = x[b];
/* 1273 */     y[a] = y[b];
/* 1274 */     x[b] = t;
/* 1275 */     y[b] = u;
/*      */   }
/*      */   private static void swap(float[] x, float[] y, int a, int b, int n) {
/* 1278 */     for (int i = 0; i < n; i++, a++, b++)
/* 1279 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(float[] a, float[] b, int from, int to) {
/* 1283 */     for (int i = from; i < to - 1; i++) {
/* 1284 */       int m = i;
/* 1285 */       for (int j = i + 1; j < to; j++) {
/* 1286 */         int u; if ((u = Float.compare(a[j], a[m])) < 0 || (u == 0 && Float.compare(b[j], b[m]) < 0))
/* 1287 */           m = j; 
/* 1288 */       }  if (m != i) {
/* 1289 */         float t = a[i];
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
/*      */   public static void quickSort(float[] x, float[] y, int from, int to) {
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
/* 1341 */     float v = x[m], w = y[m];
/*      */     
/* 1343 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1346 */       if (b <= c) {
/* 1347 */         int comparison; int t; if ((comparison = ((t = Float.compare(x[b], v)) == 0) ? Float.compare(y[b], w) : t) <= 0) {
/* 1348 */           if (comparison == 0)
/* 1349 */             swap(x, y, a++, b); 
/* 1350 */           b++; continue;
/*      */         } 
/* 1352 */       }  while (c >= b) {
/* 1353 */         int comparison; int t; if ((comparison = ((t = Float.compare(x[c], v)) == 0) ? Float.compare(y[c], w) : t) >= 0) {
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
/*      */   public static void quickSort(float[] x, float[] y) {
/* 1396 */     ensureSameLength(x, y);
/* 1397 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L; private final int from;
/*      */     private final int to;
/*      */     private final float[] x;
/*      */     private final float[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(float[] x, float[] y, int from, int to) {
/* 1405 */       this.from = from;
/* 1406 */       this.to = to;
/* 1407 */       this.x = x;
/* 1408 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1413 */       float[] x = this.x;
/* 1414 */       float[] y = this.y;
/* 1415 */       int len = this.to - this.from;
/* 1416 */       if (len < 8192) {
/* 1417 */         FloatArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1421 */       int m = this.from + len / 2;
/* 1422 */       int l = this.from;
/* 1423 */       int n = this.to - 1;
/* 1424 */       int s = len / 8;
/* 1425 */       l = FloatArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1426 */       m = FloatArrays.med3(x, y, m - s, m, m + s);
/* 1427 */       n = FloatArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1428 */       m = FloatArrays.med3(x, y, l, m, n);
/* 1429 */       float v = x[m], w = y[m];
/*      */       
/* 1431 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1434 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1436 */           if ((comparison = ((i = Float.compare(x[b], v)) == 0) ? Float.compare(y[b], w) : i) <= 0) {
/* 1437 */             if (comparison == 0)
/* 1438 */               FloatArrays.swap(x, y, a++, b); 
/* 1439 */             b++; continue;
/*      */           } 
/* 1441 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1443 */           if ((comparison = ((i = Float.compare(x[c], v)) == 0) ? Float.compare(y[c], w) : i) >= 0) {
/* 1444 */             if (comparison == 0)
/* 1445 */               FloatArrays.swap(x, y, c, d--); 
/* 1446 */             c--;
/*      */           } 
/* 1448 */         }  if (b > c)
/*      */           break; 
/* 1450 */         FloatArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1454 */       s = Math.min(a - this.from, b - a);
/* 1455 */       FloatArrays.swap(x, y, this.from, b - s, s);
/* 1456 */       s = Math.min(d - c, this.to - d - 1);
/* 1457 */       FloatArrays.swap(x, y, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(float[] x, float[] y, int from, int to) {
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
/*      */   public static void parallelQuickSort(float[] x, float[] y) {
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
/*      */   public static void unstableSort(float[] a, int from, int to) {
/* 1552 */     if (to - from >= 4000) {
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
/*      */   public static void unstableSort(float[] a) {
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
/*      */   public static void unstableSort(float[] a, int from, int to, FloatComparator comp) {
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
/*      */   public static void unstableSort(float[] a, FloatComparator comp) {
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
/*      */   public static void mergeSort(float[] a, int from, int to, float[] supp) {
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
/* 1638 */     if (Float.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1639 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1643 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1644 */       if (q >= to || (p < mid && Float.compare(supp[p], supp[q]) <= 0)) {
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
/*      */   public static void mergeSort(float[] a, int from, int to) {
/* 1667 */     mergeSort(a, from, to, (float[])a.clone());
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
/*      */   public static void mergeSort(float[] a) {
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
/*      */   public static void mergeSort(float[] a, int from, int to, FloatComparator comp, float[] supp) {
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
/*      */   public static void mergeSort(float[] a, int from, int to, FloatComparator comp) {
/* 1750 */     mergeSort(a, from, to, comp, (float[])a.clone());
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
/*      */   public static void mergeSort(float[] a, FloatComparator comp) {
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
/*      */   public static void stableSort(float[] a, int from, int to) {
/* 1792 */     mergeSort(a, from, to);
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
/*      */   public static void stableSort(float[] a) {
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
/*      */   public static void stableSort(float[] a, int from, int to, FloatComparator comp) {
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
/*      */   public static void stableSort(float[] a, FloatComparator comp) {
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
/*      */   public static int binarySearch(float[] a, int from, int to, float key) {
/* 1883 */     to--;
/* 1884 */     while (from <= to) {
/* 1885 */       int mid = from + to >>> 1;
/* 1886 */       float midVal = a[mid];
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
/*      */   public static int binarySearch(float[] a, float key) {
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
/*      */   public static int binarySearch(float[] a, int from, int to, float key, FloatComparator c) {
/* 1946 */     to--;
/* 1947 */     while (from <= to) {
/* 1948 */       int mid = from + to >>> 1;
/* 1949 */       float midVal = a[mid];
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
/*      */   public static int binarySearch(float[] a, float key, FloatComparator c) {
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
/*      */   private static final int fixFloat(float f) {
/* 2003 */     int i = Float.floatToIntBits(f);
/* 2004 */     return (i >= 0) ? i : (i ^ Integer.MAX_VALUE);
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
/*      */   public static void radixSort(float[] a) {
/* 2023 */     radixSort(a, 0, a.length);
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
/*      */   public static void radixSort(float[] a, int from, int to) {
/* 2046 */     if (to - from < 1024) {
/* 2047 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2050 */     int maxLevel = 3;
/* 2051 */     int stackSize = 766;
/* 2052 */     int stackPos = 0;
/* 2053 */     int[] offsetStack = new int[766];
/* 2054 */     int[] lengthStack = new int[766];
/* 2055 */     int[] levelStack = new int[766];
/* 2056 */     offsetStack[stackPos] = from;
/* 2057 */     lengthStack[stackPos] = to - from;
/* 2058 */     levelStack[stackPos++] = 0;
/* 2059 */     int[] count = new int[256];
/* 2060 */     int[] pos = new int[256];
/* 2061 */     while (stackPos > 0) {
/* 2062 */       int first = offsetStack[--stackPos];
/* 2063 */       int length = lengthStack[stackPos];
/* 2064 */       int level = levelStack[stackPos];
/* 2065 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2066 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2071 */       for (int i = first + length; i-- != first;) {
/* 2072 */         count[fixFloat(a[i]) >>> shift & 0xFF ^ signMask] = count[fixFloat(a[i]) >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2074 */       int lastUsed = -1;
/* 2075 */       for (int j = 0, p = first; j < 256; j++) {
/* 2076 */         if (count[j] != 0)
/* 2077 */           lastUsed = j; 
/* 2078 */         pos[j] = p += count[j];
/*      */       } 
/* 2080 */       int end = first + length - count[lastUsed];
/*      */       
/* 2082 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2083 */         float t = a[k];
/* 2084 */         c = fixFloat(t) >>> shift & 0xFF ^ signMask;
/* 2085 */         if (k < end) {
/* 2086 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2087 */             float z = t;
/* 2088 */             t = a[d];
/* 2089 */             a[d] = z;
/* 2090 */             c = fixFloat(t) >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2092 */           a[k] = t;
/*      */         } 
/* 2094 */         if (level < 3 && count[c] > 1)
/* 2095 */           if (count[c] < 1024) {
/* 2096 */             quickSort(a, k, k + count[c]);
/*      */           } else {
/* 2098 */             offsetStack[stackPos] = k;
/* 2099 */             lengthStack[stackPos] = count[c];
/* 2100 */             levelStack[stackPos++] = level + 1;
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected static final class Segment { protected final int offset; protected final int length;
/*      */     protected final int level;
/*      */     
/*      */     protected Segment(int offset, int length, int level) {
/* 2109 */       this.offset = offset;
/* 2110 */       this.length = length;
/* 2111 */       this.level = level;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2115 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */   
/* 2118 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(float[] a, int from, int to) {
/* 2139 */     if (to - from < 1024) {
/* 2140 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2143 */     int maxLevel = 3;
/* 2144 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2145 */     queue.add(new Segment(from, to - from, 0));
/* 2146 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2147 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2148 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2149 */         Executors.defaultThreadFactory());
/* 2150 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2152 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2153 */       executorCompletionService.submit(() -> {
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
/*      */               int signMask = (level % 4 == 0) ? 128 : 0;
/*      */               int shift = (3 - level % 4) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[fixFloat(a[i]) >>> shift & 0xFF ^ signMask] = count[fixFloat(a[i]) >>> shift & 0xFF ^ signMask] + 1; 
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
/*      */                 float t = a[k];
/*      */                 c = fixFloat(t) >>> shift & 0xFF ^ signMask;
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     float z = t;
/*      */                     t = a[d];
/*      */                     a[d] = z;
/*      */                     c = fixFloat(t) >>> shift & 0xFF ^ signMask;
/*      */                   } 
/*      */                   a[k] = t;
/*      */                 } 
/*      */                 if (level < 3 && count[c] > 1)
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
/* 2210 */     Throwable problem = null;
/* 2211 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2213 */         executorCompletionService.take().get();
/* 2214 */       } catch (Exception e) {
/* 2215 */         problem = e.getCause();
/*      */       } 
/* 2217 */     }  executorService.shutdown();
/* 2218 */     if (problem != null) {
/* 2219 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(float[] a) {
/* 2237 */     parallelRadixSort(a, 0, a.length);
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
/*      */   public static void radixSortIndirect(int[] perm, float[] a, boolean stable) {
/* 2264 */     radixSortIndirect(perm, a, 0, perm.length, stable);
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
/*      */   public static void radixSortIndirect(int[] perm, float[] a, int from, int to, boolean stable) {
/* 2298 */     if (to - from < 1024) {
/* 2299 */       insertionSortIndirect(perm, a, from, to);
/*      */       return;
/*      */     } 
/* 2302 */     int maxLevel = 3;
/* 2303 */     int stackSize = 766;
/* 2304 */     int stackPos = 0;
/* 2305 */     int[] offsetStack = new int[766];
/* 2306 */     int[] lengthStack = new int[766];
/* 2307 */     int[] levelStack = new int[766];
/* 2308 */     offsetStack[stackPos] = from;
/* 2309 */     lengthStack[stackPos] = to - from;
/* 2310 */     levelStack[stackPos++] = 0;
/* 2311 */     int[] count = new int[256];
/* 2312 */     int[] pos = new int[256];
/* 2313 */     int[] support = stable ? new int[perm.length] : null;
/* 2314 */     while (stackPos > 0) {
/* 2315 */       int first = offsetStack[--stackPos];
/* 2316 */       int length = lengthStack[stackPos];
/* 2317 */       int level = levelStack[stackPos];
/* 2318 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2319 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2324 */       for (int i = first + length; i-- != first;) {
/* 2325 */         count[fixFloat(a[perm[i]]) >>> shift & 0xFF ^ signMask] = count[fixFloat(a[perm[i]]) >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2327 */       int lastUsed = -1; int j, p;
/* 2328 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2329 */         if (count[j] != 0)
/* 2330 */           lastUsed = j; 
/* 2331 */         pos[j] = p += count[j];
/*      */       } 
/* 2333 */       if (stable) {
/* 2334 */         for (j = first + length; j-- != first; ) {
/* 2335 */           pos[fixFloat(a[perm[j]]) >>> shift & 0xFF ^ signMask] = pos[fixFloat(a[perm[j]]) >>> shift & 0xFF ^ signMask] - 1; support[pos[fixFloat(a[perm[j]]) >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/* 2336 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2337 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2338 */           if (level < 3 && count[j] > 1) {
/* 2339 */             if (count[j] < 1024) {
/* 2340 */               insertionSortIndirect(perm, a, p, p + count[j]);
/*      */             } else {
/* 2342 */               offsetStack[stackPos] = p;
/* 2343 */               lengthStack[stackPos] = count[j];
/* 2344 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2347 */           p += count[j];
/*      */         } 
/* 2349 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2351 */       int end = first + length - count[lastUsed];
/*      */       
/* 2353 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2354 */         int t = perm[k];
/* 2355 */         c = fixFloat(a[t]) >>> shift & 0xFF ^ signMask;
/* 2356 */         if (k < end) {
/* 2357 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2358 */             int z = t;
/* 2359 */             t = perm[d];
/* 2360 */             perm[d] = z;
/* 2361 */             c = fixFloat(a[t]) >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2363 */           perm[k] = t;
/*      */         } 
/* 2365 */         if (level < 3 && count[c] > 1) {
/* 2366 */           if (count[c] < 1024) {
/* 2367 */             insertionSortIndirect(perm, a, k, k + count[c]);
/*      */           } else {
/* 2369 */             offsetStack[stackPos] = k;
/* 2370 */             lengthStack[stackPos] = count[c];
/* 2371 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, float[] a, int from, int to, boolean stable) {
/* 2408 */     if (to - from < 1024) {
/* 2409 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2412 */     int maxLevel = 3;
/* 2413 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2414 */     queue.add(new Segment(from, to - from, 0));
/* 2415 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2416 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2417 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2418 */         Executors.defaultThreadFactory());
/* 2419 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2421 */     int[] support = stable ? new int[perm.length] : null;
/* 2422 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2423 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int k = numberOfThreads; while (k-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level;
/*      */               int signMask = (level % 4 == 0) ? 128 : 0;
/*      */               int shift = (3 - level % 4) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[fixFloat(a[perm[i]]) >>> shift & 0xFF ^ signMask] = count[fixFloat(a[perm[i]]) >>> shift & 0xFF ^ signMask] + 1; 
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
/*      */                   pos[fixFloat(a[perm[j]]) >>> shift & 0xFF ^ signMask] = pos[fixFloat(a[perm[j]]) >>> shift & 0xFF ^ signMask] - 1;
/*      */                   support[pos[fixFloat(a[perm[j]]) >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/*      */                 } 
/*      */                 System.arraycopy(support, first, perm, first, length);
/*      */                 j = 0;
/*      */                 p = first;
/*      */                 while (j <= lastUsed) {
/*      */                   if (level < 3 && count[j] > 1)
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
/*      */                   c = fixFloat(a[t]) >>> shift & 0xFF ^ signMask;
/*      */                   if (k < end) {
/*      */                     pos[c] = pos[c] - 1;
/*      */                     int d;
/*      */                     while ((d = pos[c] - 1) > k) {
/*      */                       int z = t;
/*      */                       t = perm[d];
/*      */                       perm[d] = z;
/*      */                       c = fixFloat(a[t]) >>> shift & 0xFF ^ signMask;
/*      */                     } 
/*      */                     perm[k] = t;
/*      */                   } 
/*      */                   if (level < 3 && count[c] > 1)
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
/* 2498 */     Throwable problem = null;
/* 2499 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2501 */         executorCompletionService.take().get();
/* 2502 */       } catch (Exception e) {
/* 2503 */         problem = e.getCause();
/*      */       } 
/* 2505 */     }  executorService.shutdown();
/* 2506 */     if (problem != null) {
/* 2507 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, float[] a, boolean stable) {
/* 2534 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
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
/*      */   public static void radixSort(float[] a, float[] b) {
/* 2556 */     ensureSameLength(a, b);
/* 2557 */     radixSort(a, b, 0, a.length);
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
/*      */   public static void radixSort(float[] a, float[] b, int from, int to) {
/* 2584 */     if (to - from < 1024) {
/* 2585 */       selectionSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2588 */     int layers = 2;
/* 2589 */     int maxLevel = 7;
/* 2590 */     int stackSize = 1786;
/* 2591 */     int stackPos = 0;
/* 2592 */     int[] offsetStack = new int[1786];
/* 2593 */     int[] lengthStack = new int[1786];
/* 2594 */     int[] levelStack = new int[1786];
/* 2595 */     offsetStack[stackPos] = from;
/* 2596 */     lengthStack[stackPos] = to - from;
/* 2597 */     levelStack[stackPos++] = 0;
/* 2598 */     int[] count = new int[256];
/* 2599 */     int[] pos = new int[256];
/* 2600 */     while (stackPos > 0) {
/* 2601 */       int first = offsetStack[--stackPos];
/* 2602 */       int length = lengthStack[stackPos];
/* 2603 */       int level = levelStack[stackPos];
/* 2604 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2605 */       float[] k = (level < 4) ? a : b;
/* 2606 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2611 */       for (int i = first + length; i-- != first;) {
/* 2612 */         count[fixFloat(k[i]) >>> shift & 0xFF ^ signMask] = count[fixFloat(k[i]) >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2614 */       int lastUsed = -1;
/* 2615 */       for (int j = 0, p = first; j < 256; j++) {
/* 2616 */         if (count[j] != 0)
/* 2617 */           lastUsed = j; 
/* 2618 */         pos[j] = p += count[j];
/*      */       } 
/* 2620 */       int end = first + length - count[lastUsed];
/*      */       
/* 2622 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2623 */         float t = a[m];
/* 2624 */         float u = b[m];
/* 2625 */         c = fixFloat(k[m]) >>> shift & 0xFF ^ signMask;
/* 2626 */         if (m < end) {
/* 2627 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2628 */             c = fixFloat(k[d]) >>> shift & 0xFF ^ signMask;
/* 2629 */             float z = t;
/* 2630 */             t = a[d];
/* 2631 */             a[d] = z;
/* 2632 */             z = u;
/* 2633 */             u = b[d];
/* 2634 */             b[d] = z;
/*      */           } 
/* 2636 */           a[m] = t;
/* 2637 */           b[m] = u;
/*      */         } 
/* 2639 */         if (level < 7 && count[c] > 1) {
/* 2640 */           if (count[c] < 1024) {
/* 2641 */             selectionSort(a, b, m, m + count[c]);
/*      */           } else {
/* 2643 */             offsetStack[stackPos] = m;
/* 2644 */             lengthStack[stackPos] = count[c];
/* 2645 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSort(float[] a, float[] b, int from, int to) {
/* 2681 */     if (to - from < 1024) {
/* 2682 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2685 */     int layers = 2;
/* 2686 */     if (a.length != b.length)
/* 2687 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 2688 */     int maxLevel = 7;
/* 2689 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2690 */     queue.add(new Segment(from, to - from, 0));
/* 2691 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2692 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2693 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2694 */         Executors.defaultThreadFactory());
/* 2695 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2697 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2698 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int n = numberOfThreads; while (n-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 4 == 0) ? 128 : 0; float[] k = (level < 4) ? a : b;
/*      */               int shift = (3 - level % 4) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[fixFloat(k[i]) >>> shift & 0xFF ^ signMask] = count[fixFloat(k[i]) >>> shift & 0xFF ^ signMask] + 1; 
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
/*      */                 float t = a[m];
/*      */                 float u = b[m];
/*      */                 c = fixFloat(k[m]) >>> shift & 0xFF ^ signMask;
/*      */                 if (m < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > m) {
/*      */                     c = fixFloat(k[d]) >>> shift & 0xFF ^ signMask;
/*      */                     float z = t;
/*      */                     float w = u;
/*      */                     t = a[d];
/*      */                     u = b[d];
/*      */                     a[d] = z;
/*      */                     b[d] = w;
/*      */                   } 
/*      */                   a[m] = t;
/*      */                   b[m] = u;
/*      */                 } 
/*      */                 if (level < 7 && count[c] > 1)
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
/* 2754 */     Throwable problem = null;
/* 2755 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2757 */         executorCompletionService.take().get();
/* 2758 */       } catch (Exception e) {
/* 2759 */         problem = e.getCause();
/*      */       } 
/* 2761 */     }  executorService.shutdown();
/* 2762 */     if (problem != null) {
/* 2763 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(float[] a, float[] b) {
/* 2790 */     ensureSameLength(a, b);
/* 2791 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, float[] a, float[] b, int from, int to) {
/* 2795 */     for (int i = from; ++i < to; ) {
/* 2796 */       int t = perm[i];
/* 2797 */       int j = i;
/* 2798 */       int u = perm[j - 1]; for (; Float.compare(
/* 2799 */           a[t], a[u]) < 0 || (Float.compare(a[t], a[u]) == 0 && Float.compare(b[t], b[u]) < 0); u = perm[--j - 1]) {
/* 2800 */         perm[j] = u;
/* 2801 */         if (from == j - 1) {
/* 2802 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2806 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, float[] a, float[] b, boolean stable) {
/* 2840 */     ensureSameLength(a, b);
/* 2841 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
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
/*      */   public static void radixSortIndirect(int[] perm, float[] a, float[] b, int from, int to, boolean stable) {
/* 2881 */     if (to - from < 1024) {
/* 2882 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2885 */     int layers = 2;
/* 2886 */     int maxLevel = 7;
/* 2887 */     int stackSize = 1786;
/* 2888 */     int stackPos = 0;
/* 2889 */     int[] offsetStack = new int[1786];
/* 2890 */     int[] lengthStack = new int[1786];
/* 2891 */     int[] levelStack = new int[1786];
/* 2892 */     offsetStack[stackPos] = from;
/* 2893 */     lengthStack[stackPos] = to - from;
/* 2894 */     levelStack[stackPos++] = 0;
/* 2895 */     int[] count = new int[256];
/* 2896 */     int[] pos = new int[256];
/* 2897 */     int[] support = stable ? new int[perm.length] : null;
/* 2898 */     while (stackPos > 0) {
/* 2899 */       int first = offsetStack[--stackPos];
/* 2900 */       int length = lengthStack[stackPos];
/* 2901 */       int level = levelStack[stackPos];
/* 2902 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2903 */       float[] k = (level < 4) ? a : b;
/* 2904 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2909 */       for (int i = first + length; i-- != first;) {
/* 2910 */         count[fixFloat(k[perm[i]]) >>> shift & 0xFF ^ signMask] = count[fixFloat(k[perm[i]]) >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2912 */       int lastUsed = -1; int j, p;
/* 2913 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2914 */         if (count[j] != 0)
/* 2915 */           lastUsed = j; 
/* 2916 */         pos[j] = p += count[j];
/*      */       } 
/* 2918 */       if (stable) {
/* 2919 */         for (j = first + length; j-- != first; ) {
/* 2920 */           pos[fixFloat(k[perm[j]]) >>> shift & 0xFF ^ signMask] = pos[fixFloat(k[perm[j]]) >>> shift & 0xFF ^ signMask] - 1; support[pos[fixFloat(k[perm[j]]) >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/* 2921 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2922 */         for (j = 0, p = first; j < 256; j++) {
/* 2923 */           if (level < 7 && count[j] > 1) {
/* 2924 */             if (count[j] < 1024) {
/* 2925 */               insertionSortIndirect(perm, a, b, p, p + count[j]);
/*      */             } else {
/* 2927 */               offsetStack[stackPos] = p;
/* 2928 */               lengthStack[stackPos] = count[j];
/* 2929 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2932 */           p += count[j];
/*      */         } 
/* 2934 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2936 */       int end = first + length - count[lastUsed];
/*      */       
/* 2938 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2939 */         int t = perm[m];
/* 2940 */         c = fixFloat(k[t]) >>> shift & 0xFF ^ signMask;
/* 2941 */         if (m < end) {
/* 2942 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2943 */             int z = t;
/* 2944 */             t = perm[d];
/* 2945 */             perm[d] = z;
/* 2946 */             c = fixFloat(k[t]) >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2948 */           perm[m] = t;
/*      */         } 
/* 2950 */         if (level < 7 && count[c] > 1) {
/* 2951 */           if (count[c] < 1024) {
/* 2952 */             insertionSortIndirect(perm, a, b, m, m + count[c]);
/*      */           } else {
/* 2954 */             offsetStack[stackPos] = m;
/* 2955 */             lengthStack[stackPos] = count[c];
/* 2956 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void selectionSort(float[][] a, int from, int to, int level) {
/* 2964 */     int layers = a.length;
/* 2965 */     int firstLayer = level / 4;
/* 2966 */     for (int i = from; i < to - 1; i++) {
/* 2967 */       int m = i;
/* 2968 */       for (int j = i + 1; j < to; j++) {
/* 2969 */         for (int p = firstLayer; p < layers; p++) {
/* 2970 */           if (a[p][j] < a[p][m]) {
/* 2971 */             m = j; break;
/*      */           } 
/* 2973 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/*      */       } 
/* 2977 */       if (m != i) {
/* 2978 */         for (int p = layers; p-- != 0; ) {
/* 2979 */           float u = a[p][i];
/* 2980 */           a[p][i] = a[p][m];
/* 2981 */           a[p][m] = u;
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
/*      */   public static void radixSort(float[][] a) {
/* 3004 */     radixSort(a, 0, (a[0]).length);
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
/*      */   public static void radixSort(float[][] a, int from, int to) {
/* 3028 */     if (to - from < 1024) {
/* 3029 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 3032 */     int layers = a.length;
/* 3033 */     int maxLevel = 4 * layers - 1;
/* 3034 */     for (int p = layers, l = (a[0]).length; p-- != 0;) {
/* 3035 */       if ((a[p]).length != l)
/* 3036 */         throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0."); 
/*      */     } 
/* 3038 */     int stackSize = 255 * (layers * 4 - 1) + 1;
/* 3039 */     int stackPos = 0;
/* 3040 */     int[] offsetStack = new int[stackSize];
/* 3041 */     int[] lengthStack = new int[stackSize];
/* 3042 */     int[] levelStack = new int[stackSize];
/* 3043 */     offsetStack[stackPos] = from;
/* 3044 */     lengthStack[stackPos] = to - from;
/* 3045 */     levelStack[stackPos++] = 0;
/* 3046 */     int[] count = new int[256];
/* 3047 */     int[] pos = new int[256];
/* 3048 */     float[] t = new float[layers];
/* 3049 */     while (stackPos > 0) {
/* 3050 */       int first = offsetStack[--stackPos];
/* 3051 */       int length = lengthStack[stackPos];
/* 3052 */       int level = levelStack[stackPos];
/* 3053 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 3054 */       float[] k = a[level / 4];
/* 3055 */       int shift = (3 - level % 4) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3060 */       for (int i = first + length; i-- != first;) {
/* 3061 */         count[fixFloat(k[i]) >>> shift & 0xFF ^ signMask] = count[fixFloat(k[i]) >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 3063 */       int lastUsed = -1;
/* 3064 */       for (int j = 0, n = first; j < 256; j++) {
/* 3065 */         if (count[j] != 0)
/* 3066 */           lastUsed = j; 
/* 3067 */         pos[j] = n += count[j];
/*      */       } 
/* 3069 */       int end = first + length - count[lastUsed];
/*      */       
/* 3071 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 3072 */         int i1; for (i1 = layers; i1-- != 0;)
/* 3073 */           t[i1] = a[i1][m]; 
/* 3074 */         c = fixFloat(k[m]) >>> shift & 0xFF ^ signMask;
/* 3075 */         if (m < end) {
/* 3076 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 3077 */             c = fixFloat(k[d]) >>> shift & 0xFF ^ signMask;
/* 3078 */             for (i1 = layers; i1-- != 0; ) {
/* 3079 */               float u = t[i1];
/* 3080 */               t[i1] = a[i1][d];
/* 3081 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 3084 */           for (i1 = layers; i1-- != 0;)
/* 3085 */             a[i1][m] = t[i1]; 
/*      */         } 
/* 3087 */         if (level < maxLevel && count[c] > 1) {
/* 3088 */           if (count[c] < 1024) {
/* 3089 */             selectionSort(a, m, m + count[c], level + 1);
/*      */           } else {
/* 3091 */             offsetStack[stackPos] = m;
/* 3092 */             lengthStack[stackPos] = count[c];
/* 3093 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static float[] shuffle(float[] a, int from, int to, Random random) {
/* 3114 */     for (int i = to - from; i-- != 0; ) {
/* 3115 */       int p = random.nextInt(i + 1);
/* 3116 */       float t = a[from + i];
/* 3117 */       a[from + i] = a[from + p];
/* 3118 */       a[from + p] = t;
/*      */     } 
/* 3120 */     return a;
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
/*      */   public static float[] shuffle(float[] a, Random random) {
/* 3133 */     for (int i = a.length; i-- != 0; ) {
/* 3134 */       int p = random.nextInt(i + 1);
/* 3135 */       float t = a[i];
/* 3136 */       a[i] = a[p];
/* 3137 */       a[p] = t;
/*      */     } 
/* 3139 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] reverse(float[] a) {
/* 3149 */     int length = a.length;
/* 3150 */     for (int i = length / 2; i-- != 0; ) {
/* 3151 */       float t = a[length - i - 1];
/* 3152 */       a[length - i - 1] = a[i];
/* 3153 */       a[i] = t;
/*      */     } 
/* 3155 */     return a;
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
/*      */   public static float[] reverse(float[] a, int from, int to) {
/* 3169 */     int length = to - from;
/* 3170 */     for (int i = length / 2; i-- != 0; ) {
/* 3171 */       float t = a[from + length - i - 1];
/* 3172 */       a[from + length - i - 1] = a[from + i];
/* 3173 */       a[from + i] = t;
/*      */     } 
/* 3175 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<float[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(float[] o) {
/* 3182 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(float[] a, float[] b) {
/* 3186 */       return Arrays.equals(a, b);
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
/* 3197 */   public static final Hash.Strategy<float[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */