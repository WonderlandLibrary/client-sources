/*      */ package it.unimi.dsi.fastutil.bytes;
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
/*      */ public final class ByteArrays
/*      */ {
/*  103 */   public static final byte[] EMPTY_ARRAY = new byte[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final byte[] DEFAULT_EMPTY_ARRAY = new byte[0];
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 16;
/*      */   
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */   
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */   private static final int DIGIT_BITS = 8;
/*      */   private static final int DIGIT_MASK = 255;
/*      */   private static final int DIGITS_PER_ELEMENT = 1;
/*      */   private static final int RADIXSORT_NO_REC = 1024;
/*      */   private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 1000;
/*      */   
/*      */   public static byte[] forceCapacity(byte[] array, int length, int preserve) {
/*  128 */     byte[] t = new byte[length];
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
/*      */   public static byte[] ensureCapacity(byte[] array, int length) {
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
/*      */   public static byte[] ensureCapacity(byte[] array, int length, int preserve) {
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
/*      */   public static byte[] grow(byte[] array, int length) {
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
/*      */   public static byte[] grow(byte[] array, int length, int preserve) {
/*  211 */     if (length > array.length) {
/*      */       
/*  213 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  214 */       byte[] t = new byte[newLength];
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
/*      */   public static byte[] trim(byte[] array, int length) {
/*  233 */     if (length >= array.length)
/*  234 */       return array; 
/*  235 */     byte[] t = (length == 0) ? EMPTY_ARRAY : new byte[length];
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
/*      */   public static byte[] setLength(byte[] array, int length) {
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
/*      */   public static byte[] copy(byte[] array, int offset, int length) {
/*  274 */     ensureOffsetLength(array, offset, length);
/*  275 */     byte[] a = (length == 0) ? EMPTY_ARRAY : new byte[length];
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
/*      */   public static byte[] copy(byte[] array) {
/*  287 */     return (byte[])array.clone();
/*      */   }
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
/*      */   public static void fill(byte[] array, byte value) {
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
/*      */   public static void fill(byte[] array, int from, int to, byte value) {
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
/*      */   public static boolean equals(byte[] a1, byte[] a2) {
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
/*      */   public static void ensureFromTo(byte[] a, int from, int to) {
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
/*      */   public static void ensureOffsetLength(byte[] a, int offset, int length) {
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
/*      */   public static void ensureSameLength(byte[] a, byte[] b) {
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
/*      */   public static void swap(byte[] x, int a, int b) {
/*  422 */     byte t = x[a];
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
/*      */   public static void swap(byte[] x, int a, int b, int n) {
/*  440 */     for (int i = 0; i < n; i++, a++, b++)
/*  441 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(byte[] x, int a, int b, int c, ByteComparator comp) {
/*  444 */     int ab = comp.compare(x[a], x[b]);
/*  445 */     int ac = comp.compare(x[a], x[c]);
/*  446 */     int bc = comp.compare(x[b], x[c]);
/*  447 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(byte[] a, int from, int to, ByteComparator comp) {
/*  450 */     for (int i = from; i < to - 1; i++) {
/*  451 */       int m = i;
/*  452 */       for (int j = i + 1; j < to; j++) {
/*  453 */         if (comp.compare(a[j], a[m]) < 0)
/*  454 */           m = j; 
/*  455 */       }  if (m != i) {
/*  456 */         byte u = a[i];
/*  457 */         a[i] = a[m];
/*  458 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(byte[] a, int from, int to, ByteComparator comp) {
/*  463 */     for (int i = from; ++i < to; ) {
/*  464 */       byte t = a[i];
/*  465 */       int j = i; byte u;
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
/*      */   public static void quickSort(byte[] x, int from, int to, ByteComparator comp) {
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
/*  518 */     byte v = x[m];
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
/*      */   public static void quickSort(byte[] x, ByteComparator comp) {
/*  570 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final byte[] x;
/*      */     private final ByteComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(byte[] x, int from, int to, ByteComparator comp) {
/*  579 */       this.from = from;
/*  580 */       this.to = to;
/*  581 */       this.x = x;
/*  582 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  586 */       byte[] x = this.x;
/*  587 */       int len = this.to - this.from;
/*  588 */       if (len < 8192) {
/*  589 */         ByteArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  593 */       int m = this.from + len / 2;
/*  594 */       int l = this.from;
/*  595 */       int n = this.to - 1;
/*  596 */       int s = len / 8;
/*  597 */       l = ByteArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  598 */       m = ByteArrays.med3(x, m - s, m, m + s, this.comp);
/*  599 */       n = ByteArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  600 */       m = ByteArrays.med3(x, l, m, n, this.comp);
/*  601 */       byte v = x[m];
/*      */       
/*  603 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  606 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  607 */           if (comparison == 0)
/*  608 */             ByteArrays.swap(x, a++, b); 
/*  609 */           b++; continue;
/*      */         } 
/*  611 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  612 */           if (comparison == 0)
/*  613 */             ByteArrays.swap(x, c, d--); 
/*  614 */           c--;
/*      */         } 
/*  616 */         if (b > c)
/*      */           break; 
/*  618 */         ByteArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  622 */       s = Math.min(a - this.from, b - a);
/*  623 */       ByteArrays.swap(x, this.from, b - s, s);
/*  624 */       s = Math.min(d - c, this.to - d - 1);
/*  625 */       ByteArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(byte[] x, int from, int to, ByteComparator comp) {
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
/*      */   public static void parallelQuickSort(byte[] x, ByteComparator comp) {
/*  688 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(byte[] x, int a, int b, int c) {
/*  692 */     int ab = Byte.compare(x[a], x[b]);
/*  693 */     int ac = Byte.compare(x[a], x[c]);
/*  694 */     int bc = Byte.compare(x[b], x[c]);
/*  695 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(byte[] a, int from, int to) {
/*  699 */     for (int i = from; i < to - 1; i++) {
/*  700 */       int m = i;
/*  701 */       for (int j = i + 1; j < to; j++) {
/*  702 */         if (a[j] < a[m])
/*  703 */           m = j; 
/*  704 */       }  if (m != i) {
/*  705 */         byte u = a[i];
/*  706 */         a[i] = a[m];
/*  707 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(byte[] a, int from, int to) {
/*  713 */     for (int i = from; ++i < to; ) {
/*  714 */       byte t = a[i];
/*  715 */       int j = i; byte u;
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
/*      */   public static void quickSort(byte[] x, int from, int to) {
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
/*  766 */     byte v = x[m];
/*      */     
/*  768 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  771 */       if (b <= c && (comparison = Byte.compare(x[b], v)) <= 0) {
/*  772 */         if (comparison == 0)
/*  773 */           swap(x, a++, b); 
/*  774 */         b++; continue;
/*      */       } 
/*  776 */       while (c >= b && (comparison = Byte.compare(x[c], v)) >= 0) {
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
/*      */   public static void quickSort(byte[] x) {
/*  815 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final byte[] x;
/*      */     
/*      */     public ForkJoinQuickSort(byte[] x, int from, int to) {
/*  823 */       this.from = from;
/*  824 */       this.to = to;
/*  825 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  830 */       byte[] x = this.x;
/*  831 */       int len = this.to - this.from;
/*  832 */       if (len < 8192) {
/*  833 */         ByteArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  837 */       int m = this.from + len / 2;
/*  838 */       int l = this.from;
/*  839 */       int n = this.to - 1;
/*  840 */       int s = len / 8;
/*  841 */       l = ByteArrays.med3(x, l, l + s, l + 2 * s);
/*  842 */       m = ByteArrays.med3(x, m - s, m, m + s);
/*  843 */       n = ByteArrays.med3(x, n - 2 * s, n - s, n);
/*  844 */       m = ByteArrays.med3(x, l, m, n);
/*  845 */       byte v = x[m];
/*      */       
/*  847 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  850 */         if (b <= c && (comparison = Byte.compare(x[b], v)) <= 0) {
/*  851 */           if (comparison == 0)
/*  852 */             ByteArrays.swap(x, a++, b); 
/*  853 */           b++; continue;
/*      */         } 
/*  855 */         while (c >= b && (comparison = Byte.compare(x[c], v)) >= 0) {
/*  856 */           if (comparison == 0)
/*  857 */             ByteArrays.swap(x, c, d--); 
/*  858 */           c--;
/*      */         } 
/*  860 */         if (b > c)
/*      */           break; 
/*  862 */         ByteArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  866 */       s = Math.min(a - this.from, b - a);
/*  867 */       ByteArrays.swap(x, this.from, b - s, s);
/*  868 */       s = Math.min(d - c, this.to - d - 1);
/*  869 */       ByteArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(byte[] x, int from, int to) {
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
/*      */   public static void parallelQuickSort(byte[] x) {
/*  928 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, byte[] x, int a, int b, int c) {
/*  932 */     byte aa = x[perm[a]];
/*  933 */     byte bb = x[perm[b]];
/*  934 */     byte cc = x[perm[c]];
/*  935 */     int ab = Byte.compare(aa, bb);
/*  936 */     int ac = Byte.compare(aa, cc);
/*  937 */     int bc = Byte.compare(bb, cc);
/*  938 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, byte[] a, int from, int to) {
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
/*      */   public static void quickSortIndirect(int[] perm, byte[] x, int from, int to) {
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
/* 1002 */     byte v = x[perm[m]];
/*      */     
/* 1004 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1007 */       if (b <= c && (comparison = Byte.compare(x[perm[b]], v)) <= 0) {
/* 1008 */         if (comparison == 0)
/* 1009 */           IntArrays.swap(perm, a++, b); 
/* 1010 */         b++; continue;
/*      */       } 
/* 1012 */       while (c >= b && (comparison = Byte.compare(x[perm[c]], v)) >= 0) {
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
/*      */   public static void quickSortIndirect(int[] perm, byte[] x) {
/* 1058 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final byte[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, byte[] x, int from, int to) {
/* 1067 */       this.from = from;
/* 1068 */       this.to = to;
/* 1069 */       this.x = x;
/* 1070 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1075 */       byte[] x = this.x;
/* 1076 */       int len = this.to - this.from;
/* 1077 */       if (len < 8192) {
/* 1078 */         ByteArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1082 */       int m = this.from + len / 2;
/* 1083 */       int l = this.from;
/* 1084 */       int n = this.to - 1;
/* 1085 */       int s = len / 8;
/* 1086 */       l = ByteArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1087 */       m = ByteArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1088 */       n = ByteArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1089 */       m = ByteArrays.med3Indirect(this.perm, x, l, m, n);
/* 1090 */       byte v = x[this.perm[m]];
/*      */       
/* 1092 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1095 */         if (b <= c && (comparison = Byte.compare(x[this.perm[b]], v)) <= 0) {
/* 1096 */           if (comparison == 0)
/* 1097 */             IntArrays.swap(this.perm, a++, b); 
/* 1098 */           b++; continue;
/*      */         } 
/* 1100 */         while (c >= b && (comparison = Byte.compare(x[this.perm[c]], v)) >= 0) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, byte[] x, int from, int to) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, byte[] x) {
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
/*      */   public static void stabilize(int[] perm, byte[] x, int from, int to) {
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
/*      */   public static void stabilize(int[] perm, byte[] x) {
/* 1259 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(byte[] x, byte[] y, int a, int b, int c) {
/* 1264 */     int t, ab = ((t = Byte.compare(x[a], x[b])) == 0) ? Byte.compare(y[a], y[b]) : t;
/* 1265 */     int ac = ((t = Byte.compare(x[a], x[c])) == 0) ? Byte.compare(y[a], y[c]) : t;
/* 1266 */     int bc = ((t = Byte.compare(x[b], x[c])) == 0) ? Byte.compare(y[b], y[c]) : t;
/* 1267 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(byte[] x, byte[] y, int a, int b) {
/* 1270 */     byte t = x[a];
/* 1271 */     byte u = y[a];
/* 1272 */     x[a] = x[b];
/* 1273 */     y[a] = y[b];
/* 1274 */     x[b] = t;
/* 1275 */     y[b] = u;
/*      */   }
/*      */   private static void swap(byte[] x, byte[] y, int a, int b, int n) {
/* 1278 */     for (int i = 0; i < n; i++, a++, b++)
/* 1279 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(byte[] a, byte[] b, int from, int to) {
/* 1283 */     for (int i = from; i < to - 1; i++) {
/* 1284 */       int m = i;
/* 1285 */       for (int j = i + 1; j < to; j++) {
/* 1286 */         int u; if ((u = Byte.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m]))
/* 1287 */           m = j; 
/* 1288 */       }  if (m != i) {
/* 1289 */         byte t = a[i];
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
/*      */   public static void quickSort(byte[] x, byte[] y, int from, int to) {
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
/* 1341 */     byte v = x[m], w = y[m];
/*      */     
/* 1343 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1346 */       if (b <= c) {
/* 1347 */         int comparison; int t; if ((comparison = ((t = Byte.compare(x[b], v)) == 0) ? Byte.compare(y[b], w) : t) <= 0) {
/* 1348 */           if (comparison == 0)
/* 1349 */             swap(x, y, a++, b); 
/* 1350 */           b++; continue;
/*      */         } 
/* 1352 */       }  while (c >= b) {
/* 1353 */         int comparison; int t; if ((comparison = ((t = Byte.compare(x[c], v)) == 0) ? Byte.compare(y[c], w) : t) >= 0) {
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
/*      */   public static void quickSort(byte[] x, byte[] y) {
/* 1396 */     ensureSameLength(x, y);
/* 1397 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L; private final int from;
/*      */     private final int to;
/*      */     private final byte[] x;
/*      */     private final byte[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(byte[] x, byte[] y, int from, int to) {
/* 1405 */       this.from = from;
/* 1406 */       this.to = to;
/* 1407 */       this.x = x;
/* 1408 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1413 */       byte[] x = this.x;
/* 1414 */       byte[] y = this.y;
/* 1415 */       int len = this.to - this.from;
/* 1416 */       if (len < 8192) {
/* 1417 */         ByteArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1421 */       int m = this.from + len / 2;
/* 1422 */       int l = this.from;
/* 1423 */       int n = this.to - 1;
/* 1424 */       int s = len / 8;
/* 1425 */       l = ByteArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1426 */       m = ByteArrays.med3(x, y, m - s, m, m + s);
/* 1427 */       n = ByteArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1428 */       m = ByteArrays.med3(x, y, l, m, n);
/* 1429 */       byte v = x[m], w = y[m];
/*      */       
/* 1431 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1434 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1436 */           if ((comparison = ((i = Byte.compare(x[b], v)) == 0) ? Byte.compare(y[b], w) : i) <= 0) {
/* 1437 */             if (comparison == 0)
/* 1438 */               ByteArrays.swap(x, y, a++, b); 
/* 1439 */             b++; continue;
/*      */           } 
/* 1441 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1443 */           if ((comparison = ((i = Byte.compare(x[c], v)) == 0) ? Byte.compare(y[c], w) : i) >= 0) {
/* 1444 */             if (comparison == 0)
/* 1445 */               ByteArrays.swap(x, y, c, d--); 
/* 1446 */             c--;
/*      */           } 
/* 1448 */         }  if (b > c)
/*      */           break; 
/* 1450 */         ByteArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1454 */       s = Math.min(a - this.from, b - a);
/* 1455 */       ByteArrays.swap(x, y, this.from, b - s, s);
/* 1456 */       s = Math.min(d - c, this.to - d - 1);
/* 1457 */       ByteArrays.swap(x, y, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(byte[] x, byte[] y, int from, int to) {
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
/*      */   public static void parallelQuickSort(byte[] x, byte[] y) {
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
/*      */   public static void unstableSort(byte[] a, int from, int to) {
/* 1552 */     Arrays.sort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(byte[] a) {
/* 1565 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(byte[] a, int from, int to, ByteComparator comp) {
/* 1584 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(byte[] a, ByteComparator comp) {
/* 1598 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a, int from, int to, byte[] supp) {
/* 1622 */     int len = to - from;
/*      */     
/* 1624 */     if (len < 16) {
/* 1625 */       insertionSort(a, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1629 */     int mid = from + to >>> 1;
/* 1630 */     mergeSort(supp, from, mid, a);
/* 1631 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1634 */     if (supp[mid - 1] <= supp[mid]) {
/* 1635 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1639 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1640 */       if (q >= to || (p < mid && supp[p] <= supp[q])) {
/* 1641 */         a[i] = supp[p++];
/*      */       } else {
/* 1643 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(byte[] a, int from, int to) {
/* 1663 */     mergeSort(a, from, to, (byte[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a) {
/* 1677 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a, int from, int to, ByteComparator comp, byte[] supp) {
/* 1702 */     int len = to - from;
/*      */     
/* 1704 */     if (len < 16) {
/* 1705 */       insertionSort(a, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/* 1709 */     int mid = from + to >>> 1;
/* 1710 */     mergeSort(supp, from, mid, comp, a);
/* 1711 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1714 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1715 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1719 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1720 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
/* 1721 */         a[i] = supp[p++];
/*      */       } else {
/* 1723 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(byte[] a, int from, int to, ByteComparator comp) {
/* 1745 */     mergeSort(a, from, to, comp, (byte[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(byte[] a, ByteComparator comp) {
/* 1762 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a, int from, int to) {
/* 1787 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a) {
/* 1805 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a, int from, int to, ByteComparator comp) {
/* 1829 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(byte[] a, ByteComparator comp) {
/* 1849 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, int from, int to, byte key) {
/* 1878 */     to--;
/* 1879 */     while (from <= to) {
/* 1880 */       int mid = from + to >>> 1;
/* 1881 */       byte midVal = a[mid];
/* 1882 */       if (midVal < key) {
/* 1883 */         from = mid + 1; continue;
/* 1884 */       }  if (midVal > key) {
/* 1885 */         to = mid - 1; continue;
/*      */       } 
/* 1887 */       return mid;
/*      */     } 
/* 1889 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, byte key) {
/* 1911 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, int from, int to, byte key, ByteComparator c) {
/* 1941 */     to--;
/* 1942 */     while (from <= to) {
/* 1943 */       int mid = from + to >>> 1;
/* 1944 */       byte midVal = a[mid];
/* 1945 */       int cmp = c.compare(midVal, key);
/* 1946 */       if (cmp < 0) {
/* 1947 */         from = mid + 1; continue;
/* 1948 */       }  if (cmp > 0) {
/* 1949 */         to = mid - 1; continue;
/*      */       } 
/* 1951 */       return mid;
/*      */     } 
/* 1953 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(byte[] a, byte key, ByteComparator c) {
/* 1978 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a) {
/* 2014 */     radixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a, int from, int to) {
/* 2037 */     if (to - from < 1024) {
/* 2038 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2041 */     int maxLevel = 0;
/* 2042 */     int stackSize = 1;
/* 2043 */     int stackPos = 0;
/* 2044 */     int[] offsetStack = new int[1];
/* 2045 */     int[] lengthStack = new int[1];
/* 2046 */     int[] levelStack = new int[1];
/* 2047 */     offsetStack[stackPos] = from;
/* 2048 */     lengthStack[stackPos] = to - from;
/* 2049 */     levelStack[stackPos++] = 0;
/* 2050 */     int[] count = new int[256];
/* 2051 */     int[] pos = new int[256];
/* 2052 */     while (stackPos > 0) {
/* 2053 */       int first = offsetStack[--stackPos];
/* 2054 */       int length = lengthStack[stackPos];
/* 2055 */       int level = levelStack[stackPos];
/* 2056 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2057 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2062 */       for (int i = first + length; i-- != first;) {
/* 2063 */         count[a[i] >>> shift & 0xFF ^ signMask] = count[a[i] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2065 */       int lastUsed = -1;
/* 2066 */       for (int j = 0, p = first; j < 256; j++) {
/* 2067 */         if (count[j] != 0)
/* 2068 */           lastUsed = j; 
/* 2069 */         pos[j] = p += count[j];
/*      */       } 
/* 2071 */       int end = first + length - count[lastUsed];
/*      */       
/* 2073 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2074 */         byte t = a[k];
/* 2075 */         c = t >>> shift & 0xFF ^ signMask;
/* 2076 */         if (k < end) {
/* 2077 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2078 */             byte z = t;
/* 2079 */             t = a[d];
/* 2080 */             a[d] = z;
/* 2081 */             c = t >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2083 */           a[k] = t;
/*      */         } 
/* 2085 */         if (level < 0 && count[c] > 1)
/* 2086 */           if (count[c] < 1024) {
/* 2087 */             quickSort(a, k, k + count[c]);
/*      */           } else {
/* 2089 */             offsetStack[stackPos] = k;
/* 2090 */             lengthStack[stackPos] = count[c];
/* 2091 */             levelStack[stackPos++] = level + 1;
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected static final class Segment { protected final int offset; protected final int length;
/*      */     protected final int level;
/*      */     
/*      */     protected Segment(int offset, int length, int level) {
/* 2100 */       this.offset = offset;
/* 2101 */       this.length = length;
/* 2102 */       this.level = level;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2106 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */   
/* 2109 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(byte[] a, int from, int to) {
/* 2130 */     if (to - from < 1024) {
/* 2131 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2134 */     int maxLevel = 0;
/* 2135 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2136 */     queue.add(new Segment(from, to - from, 0));
/* 2137 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2138 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2139 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2140 */         Executors.defaultThreadFactory());
/* 2141 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2143 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2144 */       executorCompletionService.submit(() -> {
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
/*      */               int signMask = (level % 1 == 0) ? 128 : 0;
/*      */               int shift = (0 - level % 1) * 8;
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
/*      */                 byte t = a[k];
/*      */                 c = t >>> shift & 0xFF ^ signMask;
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     byte z = t;
/*      */                     t = a[d];
/*      */                     a[d] = z;
/*      */                     c = t >>> shift & 0xFF ^ signMask;
/*      */                   } 
/*      */                   a[k] = t;
/*      */                 } 
/*      */                 if (level < 0 && count[c] > 1)
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
/* 2201 */     Throwable problem = null;
/* 2202 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2204 */         executorCompletionService.take().get();
/* 2205 */       } catch (Exception e) {
/* 2206 */         problem = e.getCause();
/*      */       } 
/* 2208 */     }  executorService.shutdown();
/* 2209 */     if (problem != null) {
/* 2210 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(byte[] a) {
/* 2228 */     parallelRadixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, boolean stable) {
/* 2255 */     radixSortIndirect(perm, a, 0, perm.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, int from, int to, boolean stable) {
/* 2289 */     if (to - from < 1024) {
/* 2290 */       insertionSortIndirect(perm, a, from, to);
/*      */       return;
/*      */     } 
/* 2293 */     int maxLevel = 0;
/* 2294 */     int stackSize = 1;
/* 2295 */     int stackPos = 0;
/* 2296 */     int[] offsetStack = new int[1];
/* 2297 */     int[] lengthStack = new int[1];
/* 2298 */     int[] levelStack = new int[1];
/* 2299 */     offsetStack[stackPos] = from;
/* 2300 */     lengthStack[stackPos] = to - from;
/* 2301 */     levelStack[stackPos++] = 0;
/* 2302 */     int[] count = new int[256];
/* 2303 */     int[] pos = new int[256];
/* 2304 */     int[] support = stable ? new int[perm.length] : null;
/* 2305 */     while (stackPos > 0) {
/* 2306 */       int first = offsetStack[--stackPos];
/* 2307 */       int length = lengthStack[stackPos];
/* 2308 */       int level = levelStack[stackPos];
/* 2309 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2310 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2315 */       for (int i = first + length; i-- != first;) {
/* 2316 */         count[a[perm[i]] >>> shift & 0xFF ^ signMask] = count[a[perm[i]] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2318 */       int lastUsed = -1; int j, p;
/* 2319 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2320 */         if (count[j] != 0)
/* 2321 */           lastUsed = j; 
/* 2322 */         pos[j] = p += count[j];
/*      */       } 
/* 2324 */       if (stable) {
/* 2325 */         for (j = first + length; j-- != first; ) {
/* 2326 */           pos[a[perm[j]] >>> shift & 0xFF ^ signMask] = pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[a[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/* 2327 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2328 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2329 */           if (level < 0 && count[j] > 1) {
/* 2330 */             if (count[j] < 1024) {
/* 2331 */               insertionSortIndirect(perm, a, p, p + count[j]);
/*      */             } else {
/* 2333 */               offsetStack[stackPos] = p;
/* 2334 */               lengthStack[stackPos] = count[j];
/* 2335 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2338 */           p += count[j];
/*      */         } 
/* 2340 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2342 */       int end = first + length - count[lastUsed];
/*      */       
/* 2344 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2345 */         int t = perm[k];
/* 2346 */         c = a[t] >>> shift & 0xFF ^ signMask;
/* 2347 */         if (k < end) {
/* 2348 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2349 */             int z = t;
/* 2350 */             t = perm[d];
/* 2351 */             perm[d] = z;
/* 2352 */             c = a[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2354 */           perm[k] = t;
/*      */         } 
/* 2356 */         if (level < 0 && count[c] > 1) {
/* 2357 */           if (count[c] < 1024) {
/* 2358 */             insertionSortIndirect(perm, a, k, k + count[c]);
/*      */           } else {
/* 2360 */             offsetStack[stackPos] = k;
/* 2361 */             lengthStack[stackPos] = count[c];
/* 2362 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, byte[] a, int from, int to, boolean stable) {
/* 2399 */     if (to - from < 1024) {
/* 2400 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2403 */     int maxLevel = 0;
/* 2404 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2405 */     queue.add(new Segment(from, to - from, 0));
/* 2406 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2407 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2408 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2409 */         Executors.defaultThreadFactory());
/* 2410 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2412 */     int[] support = stable ? new int[perm.length] : null;
/* 2413 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2414 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int k = numberOfThreads; while (k-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level;
/*      */               int signMask = (level % 1 == 0) ? 128 : 0;
/*      */               int shift = (0 - level % 1) * 8;
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
/*      */                   if (level < 0 && count[j] > 1)
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
/*      */                   if (level < 0 && count[c] > 1)
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
/* 2489 */     Throwable problem = null;
/* 2490 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2492 */         executorCompletionService.take().get();
/* 2493 */       } catch (Exception e) {
/* 2494 */         problem = e.getCause();
/*      */       } 
/* 2496 */     }  executorService.shutdown();
/* 2497 */     if (problem != null) {
/* 2498 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, byte[] a, boolean stable) {
/* 2525 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a, byte[] b) {
/* 2547 */     ensureSameLength(a, b);
/* 2548 */     radixSort(a, b, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[] a, byte[] b, int from, int to) {
/* 2575 */     if (to - from < 1024) {
/* 2576 */       selectionSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2579 */     int layers = 2;
/* 2580 */     int maxLevel = 1;
/* 2581 */     int stackSize = 256;
/* 2582 */     int stackPos = 0;
/* 2583 */     int[] offsetStack = new int[256];
/* 2584 */     int[] lengthStack = new int[256];
/* 2585 */     int[] levelStack = new int[256];
/* 2586 */     offsetStack[stackPos] = from;
/* 2587 */     lengthStack[stackPos] = to - from;
/* 2588 */     levelStack[stackPos++] = 0;
/* 2589 */     int[] count = new int[256];
/* 2590 */     int[] pos = new int[256];
/* 2591 */     while (stackPos > 0) {
/* 2592 */       int first = offsetStack[--stackPos];
/* 2593 */       int length = lengthStack[stackPos];
/* 2594 */       int level = levelStack[stackPos];
/* 2595 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2596 */       byte[] k = (level < 1) ? a : b;
/* 2597 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2602 */       for (int i = first + length; i-- != first;) {
/* 2603 */         count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2605 */       int lastUsed = -1;
/* 2606 */       for (int j = 0, p = first; j < 256; j++) {
/* 2607 */         if (count[j] != 0)
/* 2608 */           lastUsed = j; 
/* 2609 */         pos[j] = p += count[j];
/*      */       } 
/* 2611 */       int end = first + length - count[lastUsed];
/*      */       
/* 2613 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2614 */         byte t = a[m];
/* 2615 */         byte u = b[m];
/* 2616 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2617 */         if (m < end) {
/* 2618 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2619 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2620 */             byte z = t;
/* 2621 */             t = a[d];
/* 2622 */             a[d] = z;
/* 2623 */             z = u;
/* 2624 */             u = b[d];
/* 2625 */             b[d] = z;
/*      */           } 
/* 2627 */           a[m] = t;
/* 2628 */           b[m] = u;
/*      */         } 
/* 2630 */         if (level < 1 && count[c] > 1) {
/* 2631 */           if (count[c] < 1024) {
/* 2632 */             selectionSort(a, b, m, m + count[c]);
/*      */           } else {
/* 2634 */             offsetStack[stackPos] = m;
/* 2635 */             lengthStack[stackPos] = count[c];
/* 2636 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSort(byte[] a, byte[] b, int from, int to) {
/* 2672 */     if (to - from < 1024) {
/* 2673 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2676 */     int layers = 2;
/* 2677 */     if (a.length != b.length)
/* 2678 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 2679 */     int maxLevel = 1;
/* 2680 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2681 */     queue.add(new Segment(from, to - from, 0));
/* 2682 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2683 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2684 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2685 */         Executors.defaultThreadFactory());
/* 2686 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2688 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2689 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int n = numberOfThreads; while (n-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 1 == 0) ? 128 : 0; byte[] k = (level < 1) ? a : b;
/*      */               int shift = (0 - level % 1) * 8;
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
/*      */                 byte t = a[m];
/*      */                 byte u = b[m];
/*      */                 c = k[m] >>> shift & 0xFF ^ signMask;
/*      */                 if (m < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > m) {
/*      */                     c = k[d] >>> shift & 0xFF ^ signMask;
/*      */                     byte z = t;
/*      */                     byte w = u;
/*      */                     t = a[d];
/*      */                     u = b[d];
/*      */                     a[d] = z;
/*      */                     b[d] = w;
/*      */                   } 
/*      */                   a[m] = t;
/*      */                   b[m] = u;
/*      */                 } 
/*      */                 if (level < 1 && count[c] > 1)
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
/* 2745 */     Throwable problem = null;
/* 2746 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2748 */         executorCompletionService.take().get();
/* 2749 */       } catch (Exception e) {
/* 2750 */         problem = e.getCause();
/*      */       } 
/* 2752 */     }  executorService.shutdown();
/* 2753 */     if (problem != null) {
/* 2754 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(byte[] a, byte[] b) {
/* 2781 */     ensureSameLength(a, b);
/* 2782 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, byte[] a, byte[] b, int from, int to) {
/* 2786 */     for (int i = from; ++i < to; ) {
/* 2787 */       int t = perm[i];
/* 2788 */       int j = i; int u;
/* 2789 */       for (u = perm[j - 1]; a[t] < a[u] || (a[t] == a[u] && b[t] < b[u]); u = perm[--j - 1]) {
/* 2790 */         perm[j] = u;
/* 2791 */         if (from == j - 1) {
/* 2792 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2796 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, byte[] b, boolean stable) {
/* 2830 */     ensureSameLength(a, b);
/* 2831 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, byte[] a, byte[] b, int from, int to, boolean stable) {
/* 2871 */     if (to - from < 1024) {
/* 2872 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2875 */     int layers = 2;
/* 2876 */     int maxLevel = 1;
/* 2877 */     int stackSize = 256;
/* 2878 */     int stackPos = 0;
/* 2879 */     int[] offsetStack = new int[256];
/* 2880 */     int[] lengthStack = new int[256];
/* 2881 */     int[] levelStack = new int[256];
/* 2882 */     offsetStack[stackPos] = from;
/* 2883 */     lengthStack[stackPos] = to - from;
/* 2884 */     levelStack[stackPos++] = 0;
/* 2885 */     int[] count = new int[256];
/* 2886 */     int[] pos = new int[256];
/* 2887 */     int[] support = stable ? new int[perm.length] : null;
/* 2888 */     while (stackPos > 0) {
/* 2889 */       int first = offsetStack[--stackPos];
/* 2890 */       int length = lengthStack[stackPos];
/* 2891 */       int level = levelStack[stackPos];
/* 2892 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 2893 */       byte[] k = (level < 1) ? a : b;
/* 2894 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2899 */       for (int i = first + length; i-- != first;) {
/* 2900 */         count[k[perm[i]] >>> shift & 0xFF ^ signMask] = count[k[perm[i]] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 2902 */       int lastUsed = -1; int j, p;
/* 2903 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2904 */         if (count[j] != 0)
/* 2905 */           lastUsed = j; 
/* 2906 */         pos[j] = p += count[j];
/*      */       } 
/* 2908 */       if (stable) {
/* 2909 */         for (j = first + length; j-- != first; ) {
/* 2910 */           pos[k[perm[j]] >>> shift & 0xFF ^ signMask] = pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1; support[pos[k[perm[j]] >>> shift & 0xFF ^ signMask] - 1] = perm[j];
/* 2911 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2912 */         for (j = 0, p = first; j < 256; j++) {
/* 2913 */           if (level < 1 && count[j] > 1) {
/* 2914 */             if (count[j] < 1024) {
/* 2915 */               insertionSortIndirect(perm, a, b, p, p + count[j]);
/*      */             } else {
/* 2917 */               offsetStack[stackPos] = p;
/* 2918 */               lengthStack[stackPos] = count[j];
/* 2919 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2922 */           p += count[j];
/*      */         } 
/* 2924 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2926 */       int end = first + length - count[lastUsed];
/*      */       
/* 2928 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2929 */         int t = perm[m];
/* 2930 */         c = k[t] >>> shift & 0xFF ^ signMask;
/* 2931 */         if (m < end) {
/* 2932 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2933 */             int z = t;
/* 2934 */             t = perm[d];
/* 2935 */             perm[d] = z;
/* 2936 */             c = k[t] >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2938 */           perm[m] = t;
/*      */         } 
/* 2940 */         if (level < 1 && count[c] > 1) {
/* 2941 */           if (count[c] < 1024) {
/* 2942 */             insertionSortIndirect(perm, a, b, m, m + count[c]);
/*      */           } else {
/* 2944 */             offsetStack[stackPos] = m;
/* 2945 */             lengthStack[stackPos] = count[c];
/* 2946 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void selectionSort(byte[][] a, int from, int to, int level) {
/* 2954 */     int layers = a.length;
/* 2955 */     int firstLayer = level / 1;
/* 2956 */     for (int i = from; i < to - 1; i++) {
/* 2957 */       int m = i;
/* 2958 */       for (int j = i + 1; j < to; j++) {
/* 2959 */         for (int p = firstLayer; p < layers; p++) {
/* 2960 */           if (a[p][j] < a[p][m]) {
/* 2961 */             m = j; break;
/*      */           } 
/* 2963 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/*      */       } 
/* 2967 */       if (m != i) {
/* 2968 */         for (int p = layers; p-- != 0; ) {
/* 2969 */           byte u = a[p][i];
/* 2970 */           a[p][i] = a[p][m];
/* 2971 */           a[p][m] = u;
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
/*      */   public static void radixSort(byte[][] a) {
/* 2994 */     radixSort(a, 0, (a[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(byte[][] a, int from, int to) {
/* 3018 */     if (to - from < 1024) {
/* 3019 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 3022 */     int layers = a.length;
/* 3023 */     int maxLevel = 1 * layers - 1;
/* 3024 */     for (int p = layers, l = (a[0]).length; p-- != 0;) {
/* 3025 */       if ((a[p]).length != l)
/* 3026 */         throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0."); 
/*      */     } 
/* 3028 */     int stackSize = 255 * (layers * 1 - 1) + 1;
/* 3029 */     int stackPos = 0;
/* 3030 */     int[] offsetStack = new int[stackSize];
/* 3031 */     int[] lengthStack = new int[stackSize];
/* 3032 */     int[] levelStack = new int[stackSize];
/* 3033 */     offsetStack[stackPos] = from;
/* 3034 */     lengthStack[stackPos] = to - from;
/* 3035 */     levelStack[stackPos++] = 0;
/* 3036 */     int[] count = new int[256];
/* 3037 */     int[] pos = new int[256];
/* 3038 */     byte[] t = new byte[layers];
/* 3039 */     while (stackPos > 0) {
/* 3040 */       int first = offsetStack[--stackPos];
/* 3041 */       int length = lengthStack[stackPos];
/* 3042 */       int level = levelStack[stackPos];
/* 3043 */       int signMask = (level % 1 == 0) ? 128 : 0;
/* 3044 */       byte[] k = a[level / 1];
/* 3045 */       int shift = (0 - level % 1) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3050 */       for (int i = first + length; i-- != first;) {
/* 3051 */         count[k[i] >>> shift & 0xFF ^ signMask] = count[k[i] >>> shift & 0xFF ^ signMask] + 1;
/*      */       }
/* 3053 */       int lastUsed = -1;
/* 3054 */       for (int j = 0, n = first; j < 256; j++) {
/* 3055 */         if (count[j] != 0)
/* 3056 */           lastUsed = j; 
/* 3057 */         pos[j] = n += count[j];
/*      */       } 
/* 3059 */       int end = first + length - count[lastUsed];
/*      */       
/* 3061 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 3062 */         int i1; for (i1 = layers; i1-- != 0;)
/* 3063 */           t[i1] = a[i1][m]; 
/* 3064 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 3065 */         if (m < end) {
/* 3066 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 3067 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 3068 */             for (i1 = layers; i1-- != 0; ) {
/* 3069 */               byte u = t[i1];
/* 3070 */               t[i1] = a[i1][d];
/* 3071 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 3074 */           for (i1 = layers; i1-- != 0;)
/* 3075 */             a[i1][m] = t[i1]; 
/*      */         } 
/* 3077 */         if (level < maxLevel && count[c] > 1) {
/* 3078 */           if (count[c] < 1024) {
/* 3079 */             selectionSort(a, m, m + count[c], level + 1);
/*      */           } else {
/* 3081 */             offsetStack[stackPos] = m;
/* 3082 */             lengthStack[stackPos] = count[c];
/* 3083 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static byte[] shuffle(byte[] a, int from, int to, Random random) {
/* 3104 */     for (int i = to - from; i-- != 0; ) {
/* 3105 */       int p = random.nextInt(i + 1);
/* 3106 */       byte t = a[from + i];
/* 3107 */       a[from + i] = a[from + p];
/* 3108 */       a[from + p] = t;
/*      */     } 
/* 3110 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] shuffle(byte[] a, Random random) {
/* 3123 */     for (int i = a.length; i-- != 0; ) {
/* 3124 */       int p = random.nextInt(i + 1);
/* 3125 */       byte t = a[i];
/* 3126 */       a[i] = a[p];
/* 3127 */       a[p] = t;
/*      */     } 
/* 3129 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] reverse(byte[] a) {
/* 3139 */     int length = a.length;
/* 3140 */     for (int i = length / 2; i-- != 0; ) {
/* 3141 */       byte t = a[length - i - 1];
/* 3142 */       a[length - i - 1] = a[i];
/* 3143 */       a[i] = t;
/*      */     } 
/* 3145 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] reverse(byte[] a, int from, int to) {
/* 3159 */     int length = to - from;
/* 3160 */     for (int i = length / 2; i-- != 0; ) {
/* 3161 */       byte t = a[from + length - i - 1];
/* 3162 */       a[from + length - i - 1] = a[from + i];
/* 3163 */       a[from + i] = t;
/*      */     } 
/* 3165 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<byte[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(byte[] o) {
/* 3172 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(byte[] a, byte[] b) {
/* 3176 */       return Arrays.equals(a, b);
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
/* 3187 */   public static final Hash.Strategy<byte[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */