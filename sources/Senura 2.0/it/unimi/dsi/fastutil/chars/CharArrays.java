/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ public final class CharArrays
/*      */ {
/*  103 */   public static final char[] EMPTY_ARRAY = new char[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   public static final char[] DEFAULT_EMPTY_ARRAY = new char[0];
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
/*      */   static final int RADIX_SORT_MIN_THRESHOLD = 4000;
/*      */   
/*      */   public static char[] forceCapacity(char[] array, int length, int preserve) {
/*  128 */     char[] t = new char[length];
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
/*      */   public static char[] ensureCapacity(char[] array, int length) {
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
/*      */   public static char[] ensureCapacity(char[] array, int length, int preserve) {
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
/*      */   public static char[] grow(char[] array, int length) {
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
/*      */   public static char[] grow(char[] array, int length, int preserve) {
/*  211 */     if (length > array.length) {
/*      */       
/*  213 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  214 */       char[] t = new char[newLength];
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
/*      */   public static char[] trim(char[] array, int length) {
/*  233 */     if (length >= array.length)
/*  234 */       return array; 
/*  235 */     char[] t = (length == 0) ? EMPTY_ARRAY : new char[length];
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
/*      */   public static char[] setLength(char[] array, int length) {
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
/*      */   public static char[] copy(char[] array, int offset, int length) {
/*  274 */     ensureOffsetLength(array, offset, length);
/*  275 */     char[] a = (length == 0) ? EMPTY_ARRAY : new char[length];
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
/*      */   public static char[] copy(char[] array) {
/*  287 */     return (char[])array.clone();
/*      */   }
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
/*      */   public static void fill(char[] array, char value) {
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
/*      */   public static void fill(char[] array, int from, int to, char value) {
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
/*      */   public static boolean equals(char[] a1, char[] a2) {
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
/*      */   public static void ensureFromTo(char[] a, int from, int to) {
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
/*      */   public static void ensureOffsetLength(char[] a, int offset, int length) {
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
/*      */   public static void ensureSameLength(char[] a, char[] b) {
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
/*      */   public static void swap(char[] x, int a, int b) {
/*  422 */     char t = x[a];
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
/*      */   public static void swap(char[] x, int a, int b, int n) {
/*  440 */     for (int i = 0; i < n; i++, a++, b++)
/*  441 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(char[] x, int a, int b, int c, CharComparator comp) {
/*  444 */     int ab = comp.compare(x[a], x[b]);
/*  445 */     int ac = comp.compare(x[a], x[c]);
/*  446 */     int bc = comp.compare(x[b], x[c]);
/*  447 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(char[] a, int from, int to, CharComparator comp) {
/*  450 */     for (int i = from; i < to - 1; i++) {
/*  451 */       int m = i;
/*  452 */       for (int j = i + 1; j < to; j++) {
/*  453 */         if (comp.compare(a[j], a[m]) < 0)
/*  454 */           m = j; 
/*  455 */       }  if (m != i) {
/*  456 */         char u = a[i];
/*  457 */         a[i] = a[m];
/*  458 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(char[] a, int from, int to, CharComparator comp) {
/*  463 */     for (int i = from; ++i < to; ) {
/*  464 */       char t = a[i];
/*  465 */       int j = i; char u;
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
/*      */   public static void quickSort(char[] x, int from, int to, CharComparator comp) {
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
/*  518 */     char v = x[m];
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
/*      */   public static void quickSort(char[] x, CharComparator comp) {
/*  570 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final char[] x;
/*      */     private final CharComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(char[] x, int from, int to, CharComparator comp) {
/*  579 */       this.from = from;
/*  580 */       this.to = to;
/*  581 */       this.x = x;
/*  582 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  586 */       char[] x = this.x;
/*  587 */       int len = this.to - this.from;
/*  588 */       if (len < 8192) {
/*  589 */         CharArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  593 */       int m = this.from + len / 2;
/*  594 */       int l = this.from;
/*  595 */       int n = this.to - 1;
/*  596 */       int s = len / 8;
/*  597 */       l = CharArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  598 */       m = CharArrays.med3(x, m - s, m, m + s, this.comp);
/*  599 */       n = CharArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  600 */       m = CharArrays.med3(x, l, m, n, this.comp);
/*  601 */       char v = x[m];
/*      */       
/*  603 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  606 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  607 */           if (comparison == 0)
/*  608 */             CharArrays.swap(x, a++, b); 
/*  609 */           b++; continue;
/*      */         } 
/*  611 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  612 */           if (comparison == 0)
/*  613 */             CharArrays.swap(x, c, d--); 
/*  614 */           c--;
/*      */         } 
/*  616 */         if (b > c)
/*      */           break; 
/*  618 */         CharArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  622 */       s = Math.min(a - this.from, b - a);
/*  623 */       CharArrays.swap(x, this.from, b - s, s);
/*  624 */       s = Math.min(d - c, this.to - d - 1);
/*  625 */       CharArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(char[] x, int from, int to, CharComparator comp) {
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
/*      */   public static void parallelQuickSort(char[] x, CharComparator comp) {
/*  688 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(char[] x, int a, int b, int c) {
/*  692 */     int ab = Character.compare(x[a], x[b]);
/*  693 */     int ac = Character.compare(x[a], x[c]);
/*  694 */     int bc = Character.compare(x[b], x[c]);
/*  695 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(char[] a, int from, int to) {
/*  699 */     for (int i = from; i < to - 1; i++) {
/*  700 */       int m = i;
/*  701 */       for (int j = i + 1; j < to; j++) {
/*  702 */         if (a[j] < a[m])
/*  703 */           m = j; 
/*  704 */       }  if (m != i) {
/*  705 */         char u = a[i];
/*  706 */         a[i] = a[m];
/*  707 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(char[] a, int from, int to) {
/*  713 */     for (int i = from; ++i < to; ) {
/*  714 */       char t = a[i];
/*  715 */       int j = i; char u;
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
/*      */   public static void quickSort(char[] x, int from, int to) {
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
/*  766 */     char v = x[m];
/*      */     
/*  768 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  771 */       if (b <= c && (comparison = Character.compare(x[b], v)) <= 0) {
/*  772 */         if (comparison == 0)
/*  773 */           swap(x, a++, b); 
/*  774 */         b++; continue;
/*      */       } 
/*  776 */       while (c >= b && (comparison = Character.compare(x[c], v)) >= 0) {
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
/*      */   public static void quickSort(char[] x) {
/*  815 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final char[] x;
/*      */     
/*      */     public ForkJoinQuickSort(char[] x, int from, int to) {
/*  823 */       this.from = from;
/*  824 */       this.to = to;
/*  825 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  830 */       char[] x = this.x;
/*  831 */       int len = this.to - this.from;
/*  832 */       if (len < 8192) {
/*  833 */         CharArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  837 */       int m = this.from + len / 2;
/*  838 */       int l = this.from;
/*  839 */       int n = this.to - 1;
/*  840 */       int s = len / 8;
/*  841 */       l = CharArrays.med3(x, l, l + s, l + 2 * s);
/*  842 */       m = CharArrays.med3(x, m - s, m, m + s);
/*  843 */       n = CharArrays.med3(x, n - 2 * s, n - s, n);
/*  844 */       m = CharArrays.med3(x, l, m, n);
/*  845 */       char v = x[m];
/*      */       
/*  847 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  850 */         if (b <= c && (comparison = Character.compare(x[b], v)) <= 0) {
/*  851 */           if (comparison == 0)
/*  852 */             CharArrays.swap(x, a++, b); 
/*  853 */           b++; continue;
/*      */         } 
/*  855 */         while (c >= b && (comparison = Character.compare(x[c], v)) >= 0) {
/*  856 */           if (comparison == 0)
/*  857 */             CharArrays.swap(x, c, d--); 
/*  858 */           c--;
/*      */         } 
/*  860 */         if (b > c)
/*      */           break; 
/*  862 */         CharArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  866 */       s = Math.min(a - this.from, b - a);
/*  867 */       CharArrays.swap(x, this.from, b - s, s);
/*  868 */       s = Math.min(d - c, this.to - d - 1);
/*  869 */       CharArrays.swap(x, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(char[] x, int from, int to) {
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
/*      */   public static void parallelQuickSort(char[] x) {
/*  928 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, char[] x, int a, int b, int c) {
/*  932 */     char aa = x[perm[a]];
/*  933 */     char bb = x[perm[b]];
/*  934 */     char cc = x[perm[c]];
/*  935 */     int ab = Character.compare(aa, bb);
/*  936 */     int ac = Character.compare(aa, cc);
/*  937 */     int bc = Character.compare(bb, cc);
/*  938 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, char[] a, int from, int to) {
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
/*      */   public static void quickSortIndirect(int[] perm, char[] x, int from, int to) {
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
/* 1002 */     char v = x[perm[m]];
/*      */     
/* 1004 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1007 */       if (b <= c && (comparison = Character.compare(x[perm[b]], v)) <= 0) {
/* 1008 */         if (comparison == 0)
/* 1009 */           IntArrays.swap(perm, a++, b); 
/* 1010 */         b++; continue;
/*      */       } 
/* 1012 */       while (c >= b && (comparison = Character.compare(x[perm[c]], v)) >= 0) {
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
/*      */   public static void quickSortIndirect(int[] perm, char[] x) {
/* 1058 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final char[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, char[] x, int from, int to) {
/* 1067 */       this.from = from;
/* 1068 */       this.to = to;
/* 1069 */       this.x = x;
/* 1070 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1075 */       char[] x = this.x;
/* 1076 */       int len = this.to - this.from;
/* 1077 */       if (len < 8192) {
/* 1078 */         CharArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1082 */       int m = this.from + len / 2;
/* 1083 */       int l = this.from;
/* 1084 */       int n = this.to - 1;
/* 1085 */       int s = len / 8;
/* 1086 */       l = CharArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1087 */       m = CharArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1088 */       n = CharArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1089 */       m = CharArrays.med3Indirect(this.perm, x, l, m, n);
/* 1090 */       char v = x[this.perm[m]];
/*      */       
/* 1092 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1095 */         if (b <= c && (comparison = Character.compare(x[this.perm[b]], v)) <= 0) {
/* 1096 */           if (comparison == 0)
/* 1097 */             IntArrays.swap(this.perm, a++, b); 
/* 1098 */           b++; continue;
/*      */         } 
/* 1100 */         while (c >= b && (comparison = Character.compare(x[this.perm[c]], v)) >= 0) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, char[] x, int from, int to) {
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, char[] x) {
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
/*      */   public static void stabilize(int[] perm, char[] x, int from, int to) {
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
/*      */   public static void stabilize(int[] perm, char[] x) {
/* 1259 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(char[] x, char[] y, int a, int b, int c) {
/* 1264 */     int t, ab = ((t = Character.compare(x[a], x[b])) == 0) ? Character.compare(y[a], y[b]) : t;
/* 1265 */     int ac = ((t = Character.compare(x[a], x[c])) == 0) ? Character.compare(y[a], y[c]) : t;
/* 1266 */     int bc = ((t = Character.compare(x[b], x[c])) == 0) ? Character.compare(y[b], y[c]) : t;
/* 1267 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(char[] x, char[] y, int a, int b) {
/* 1270 */     char t = x[a];
/* 1271 */     char u = y[a];
/* 1272 */     x[a] = x[b];
/* 1273 */     y[a] = y[b];
/* 1274 */     x[b] = t;
/* 1275 */     y[b] = u;
/*      */   }
/*      */   private static void swap(char[] x, char[] y, int a, int b, int n) {
/* 1278 */     for (int i = 0; i < n; i++, a++, b++)
/* 1279 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(char[] a, char[] b, int from, int to) {
/* 1283 */     for (int i = from; i < to - 1; i++) {
/* 1284 */       int m = i;
/* 1285 */       for (int j = i + 1; j < to; j++) {
/* 1286 */         int u; if ((u = Character.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m]))
/* 1287 */           m = j; 
/* 1288 */       }  if (m != i) {
/* 1289 */         char t = a[i];
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
/*      */   public static void quickSort(char[] x, char[] y, int from, int to) {
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
/* 1341 */     char v = x[m], w = y[m];
/*      */     
/* 1343 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1346 */       if (b <= c) {
/*      */         int comparison; int t;
/* 1348 */         if ((comparison = ((t = Character.compare(x[b], v)) == 0) ? Character.compare(y[b], w) : t) <= 0) {
/* 1349 */           if (comparison == 0)
/* 1350 */             swap(x, y, a++, b); 
/* 1351 */           b++; continue;
/*      */         } 
/* 1353 */       }  while (c >= b) {
/*      */         int comparison; int t;
/* 1355 */         if ((comparison = ((t = Character.compare(x[c], v)) == 0) ? Character.compare(y[c], w) : t) >= 0) {
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
/*      */   public static void quickSort(char[] x, char[] y) {
/* 1398 */     ensureSameLength(x, y);
/* 1399 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L; private final int from;
/*      */     private final int to;
/*      */     private final char[] x;
/*      */     private final char[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(char[] x, char[] y, int from, int to) {
/* 1407 */       this.from = from;
/* 1408 */       this.to = to;
/* 1409 */       this.x = x;
/* 1410 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1415 */       char[] x = this.x;
/* 1416 */       char[] y = this.y;
/* 1417 */       int len = this.to - this.from;
/* 1418 */       if (len < 8192) {
/* 1419 */         CharArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1423 */       int m = this.from + len / 2;
/* 1424 */       int l = this.from;
/* 1425 */       int n = this.to - 1;
/* 1426 */       int s = len / 8;
/* 1427 */       l = CharArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1428 */       m = CharArrays.med3(x, y, m - s, m, m + s);
/* 1429 */       n = CharArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1430 */       m = CharArrays.med3(x, y, l, m, n);
/* 1431 */       char v = x[m], w = y[m];
/*      */       
/* 1433 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1436 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1438 */           if ((comparison = ((i = Character.compare(x[b], v)) == 0) ? Character.compare(y[b], w) : i) <= 0) {
/* 1439 */             if (comparison == 0)
/* 1440 */               CharArrays.swap(x, y, a++, b); 
/* 1441 */             b++; continue;
/*      */           } 
/* 1443 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1445 */           if ((comparison = ((i = Character.compare(x[c], v)) == 0) ? Character.compare(y[c], w) : i) >= 0) {
/* 1446 */             if (comparison == 0)
/* 1447 */               CharArrays.swap(x, y, c, d--); 
/* 1448 */             c--;
/*      */           } 
/* 1450 */         }  if (b > c)
/*      */           break; 
/* 1452 */         CharArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1456 */       s = Math.min(a - this.from, b - a);
/* 1457 */       CharArrays.swap(x, y, this.from, b - s, s);
/* 1458 */       s = Math.min(d - c, this.to - d - 1);
/* 1459 */       CharArrays.swap(x, y, b, this.to - s, s);
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
/*      */   public static void parallelQuickSort(char[] x, char[] y, int from, int to) {
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
/*      */   public static void parallelQuickSort(char[] x, char[] y) {
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
/*      */   public static void unstableSort(char[] a, int from, int to) {
/* 1554 */     if (to - from >= 4000) {
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
/*      */   public static void unstableSort(char[] a) {
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
/*      */   public static void unstableSort(char[] a, int from, int to, CharComparator comp) {
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
/*      */   public static void unstableSort(char[] a, CharComparator comp) {
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
/*      */   public static void mergeSort(char[] a, int from, int to, char[] supp) {
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
/* 1640 */     if (supp[mid - 1] <= supp[mid]) {
/* 1641 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1645 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1646 */       if (q >= to || (p < mid && supp[p] <= supp[q])) {
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
/*      */   public static void mergeSort(char[] a, int from, int to) {
/* 1669 */     mergeSort(a, from, to, (char[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(char[] a) {
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
/*      */   public static void mergeSort(char[] a, int from, int to, CharComparator comp, char[] supp) {
/* 1708 */     int len = to - from;
/*      */     
/* 1710 */     if (len < 16) {
/* 1711 */       insertionSort(a, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/* 1715 */     int mid = from + to >>> 1;
/* 1716 */     mergeSort(supp, from, mid, comp, a);
/* 1717 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1720 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1721 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1725 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1726 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
/* 1727 */         a[i] = supp[p++];
/*      */       } else {
/* 1729 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(char[] a, int from, int to, CharComparator comp) {
/* 1751 */     mergeSort(a, from, to, comp, (char[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(char[] a, CharComparator comp) {
/* 1768 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(char[] a, int from, int to) {
/* 1793 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(char[] a) {
/* 1811 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(char[] a, int from, int to, CharComparator comp) {
/* 1835 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(char[] a, CharComparator comp) {
/* 1855 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(char[] a, int from, int to, char key) {
/* 1884 */     to--;
/* 1885 */     while (from <= to) {
/* 1886 */       int mid = from + to >>> 1;
/* 1887 */       char midVal = a[mid];
/* 1888 */       if (midVal < key) {
/* 1889 */         from = mid + 1; continue;
/* 1890 */       }  if (midVal > key) {
/* 1891 */         to = mid - 1; continue;
/*      */       } 
/* 1893 */       return mid;
/*      */     } 
/* 1895 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(char[] a, char key) {
/* 1917 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(char[] a, int from, int to, char key, CharComparator c) {
/* 1947 */     to--;
/* 1948 */     while (from <= to) {
/* 1949 */       int mid = from + to >>> 1;
/* 1950 */       char midVal = a[mid];
/* 1951 */       int cmp = c.compare(midVal, key);
/* 1952 */       if (cmp < 0) {
/* 1953 */         from = mid + 1; continue;
/* 1954 */       }  if (cmp > 0) {
/* 1955 */         to = mid - 1; continue;
/*      */       } 
/* 1957 */       return mid;
/*      */     } 
/* 1959 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binarySearch(char[] a, char key, CharComparator c) {
/* 1984 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(char[] a) {
/* 2020 */     radixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(char[] a, int from, int to) {
/* 2043 */     if (to - from < 1024) {
/* 2044 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2047 */     int maxLevel = 1;
/* 2048 */     int stackSize = 256;
/* 2049 */     int stackPos = 0;
/* 2050 */     int[] offsetStack = new int[256];
/* 2051 */     int[] lengthStack = new int[256];
/* 2052 */     int[] levelStack = new int[256];
/* 2053 */     offsetStack[stackPos] = from;
/* 2054 */     lengthStack[stackPos] = to - from;
/* 2055 */     levelStack[stackPos++] = 0;
/* 2056 */     int[] count = new int[256];
/* 2057 */     int[] pos = new int[256];
/* 2058 */     while (stackPos > 0) {
/* 2059 */       int first = offsetStack[--stackPos];
/* 2060 */       int length = lengthStack[stackPos];
/* 2061 */       int level = levelStack[stackPos];
/* 2062 */       int signMask = 0;
/* 2063 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2068 */       for (int i = first + length; i-- != first;) {
/* 2069 */         count[a[i] >>> shift & 0xFF ^ 0x0] = count[a[i] >>> shift & 0xFF ^ 0x0] + 1;
/*      */       }
/* 2071 */       int lastUsed = -1;
/* 2072 */       for (int j = 0, p = first; j < 256; j++) {
/* 2073 */         if (count[j] != 0)
/* 2074 */           lastUsed = j; 
/* 2075 */         pos[j] = p += count[j];
/*      */       } 
/* 2077 */       int end = first + length - count[lastUsed];
/*      */       
/* 2079 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2080 */         char t = a[k];
/* 2081 */         c = t >>> shift & 0xFF ^ 0x0;
/* 2082 */         if (k < end) {
/* 2083 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2084 */             char z = t;
/* 2085 */             t = a[d];
/* 2086 */             a[d] = z;
/* 2087 */             c = t >>> shift & 0xFF ^ 0x0;
/*      */           } 
/* 2089 */           a[k] = t;
/*      */         } 
/* 2091 */         if (level < 1 && count[c] > 1)
/* 2092 */           if (count[c] < 1024) {
/* 2093 */             quickSort(a, k, k + count[c]);
/*      */           } else {
/* 2095 */             offsetStack[stackPos] = k;
/* 2096 */             lengthStack[stackPos] = count[c];
/* 2097 */             levelStack[stackPos++] = level + 1;
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */   protected static final class Segment { protected final int offset; protected final int length;
/*      */     protected final int level;
/*      */     
/*      */     protected Segment(int offset, int length, int level) {
/* 2106 */       this.offset = offset;
/* 2107 */       this.length = length;
/* 2108 */       this.level = level;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2112 */       return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
/*      */     } }
/*      */   
/* 2115 */   protected static final Segment POISON_PILL = new Segment(-1, -1, -1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelRadixSort(char[] a, int from, int to) {
/* 2136 */     if (to - from < 1024) {
/* 2137 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2140 */     int maxLevel = 1;
/* 2141 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2142 */     queue.add(new Segment(from, to - from, 0));
/* 2143 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2144 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2145 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2146 */         Executors.defaultThreadFactory());
/* 2147 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2149 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2150 */       executorCompletionService.submit(() -> {
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
/*      */               int signMask = 0;
/*      */               int shift = (1 - level % 2) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[a[i] >>> shift & 0xFF ^ 0x0] = count[a[i] >>> shift & 0xFF ^ 0x0] + 1; 
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
/*      */                 char t = a[k];
/*      */                 c = t >>> shift & 0xFF ^ 0x0;
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     char z = t;
/*      */                     t = a[d];
/*      */                     a[d] = z;
/*      */                     c = t >>> shift & 0xFF ^ 0x0;
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
/* 2207 */     Throwable problem = null;
/* 2208 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2210 */         executorCompletionService.take().get();
/* 2211 */       } catch (Exception e) {
/* 2212 */         problem = e.getCause();
/*      */       } 
/* 2214 */     }  executorService.shutdown();
/* 2215 */     if (problem != null) {
/* 2216 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(char[] a) {
/* 2234 */     parallelRadixSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, char[] a, boolean stable) {
/* 2261 */     radixSortIndirect(perm, a, 0, perm.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, char[] a, int from, int to, boolean stable) {
/* 2295 */     if (to - from < 1024) {
/* 2296 */       insertionSortIndirect(perm, a, from, to);
/*      */       return;
/*      */     } 
/* 2299 */     int maxLevel = 1;
/* 2300 */     int stackSize = 256;
/* 2301 */     int stackPos = 0;
/* 2302 */     int[] offsetStack = new int[256];
/* 2303 */     int[] lengthStack = new int[256];
/* 2304 */     int[] levelStack = new int[256];
/* 2305 */     offsetStack[stackPos] = from;
/* 2306 */     lengthStack[stackPos] = to - from;
/* 2307 */     levelStack[stackPos++] = 0;
/* 2308 */     int[] count = new int[256];
/* 2309 */     int[] pos = new int[256];
/* 2310 */     int[] support = stable ? new int[perm.length] : null;
/* 2311 */     while (stackPos > 0) {
/* 2312 */       int first = offsetStack[--stackPos];
/* 2313 */       int length = lengthStack[stackPos];
/* 2314 */       int level = levelStack[stackPos];
/* 2315 */       int signMask = 0;
/* 2316 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2321 */       for (int i = first + length; i-- != first;) {
/* 2322 */         count[a[perm[i]] >>> shift & 0xFF ^ 0x0] = count[a[perm[i]] >>> shift & 0xFF ^ 0x0] + 1;
/*      */       }
/* 2324 */       int lastUsed = -1; int j, p;
/* 2325 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2326 */         if (count[j] != 0)
/* 2327 */           lastUsed = j; 
/* 2328 */         pos[j] = p += count[j];
/*      */       } 
/* 2330 */       if (stable) {
/* 2331 */         for (j = first + length; j-- != first; ) {
/* 2332 */           pos[a[perm[j]] >>> shift & 0xFF ^ 0x0] = pos[a[perm[j]] >>> shift & 0xFF ^ 0x0] - 1; support[pos[a[perm[j]] >>> shift & 0xFF ^ 0x0] - 1] = perm[j];
/* 2333 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2334 */         for (j = 0, p = first; j <= lastUsed; j++) {
/* 2335 */           if (level < 1 && count[j] > 1) {
/* 2336 */             if (count[j] < 1024) {
/* 2337 */               insertionSortIndirect(perm, a, p, p + count[j]);
/*      */             } else {
/* 2339 */               offsetStack[stackPos] = p;
/* 2340 */               lengthStack[stackPos] = count[j];
/* 2341 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2344 */           p += count[j];
/*      */         } 
/* 2346 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2348 */       int end = first + length - count[lastUsed];
/*      */       
/* 2350 */       for (int k = first, c = -1; k <= end; k += count[c], count[c] = 0) {
/* 2351 */         int t = perm[k];
/* 2352 */         c = a[t] >>> shift & 0xFF ^ 0x0;
/* 2353 */         if (k < end) {
/* 2354 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2355 */             int z = t;
/* 2356 */             t = perm[d];
/* 2357 */             perm[d] = z;
/* 2358 */             c = a[t] >>> shift & 0xFF ^ 0x0;
/*      */           } 
/* 2360 */           perm[k] = t;
/*      */         } 
/* 2362 */         if (level < 1 && count[c] > 1) {
/* 2363 */           if (count[c] < 1024) {
/* 2364 */             insertionSortIndirect(perm, a, k, k + count[c]);
/*      */           } else {
/* 2366 */             offsetStack[stackPos] = k;
/* 2367 */             lengthStack[stackPos] = count[c];
/* 2368 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, char[] a, int from, int to, boolean stable) {
/* 2405 */     if (to - from < 1024) {
/* 2406 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2409 */     int maxLevel = 1;
/* 2410 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2411 */     queue.add(new Segment(from, to - from, 0));
/* 2412 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2413 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2414 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2415 */         Executors.defaultThreadFactory());
/* 2416 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2418 */     int[] support = stable ? new int[perm.length] : null;
/* 2419 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2420 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int k = numberOfThreads; while (k-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level;
/*      */               int signMask = 0;
/*      */               int shift = (1 - level % 2) * 8;
/*      */               int i = first + length;
/*      */               while (i-- != first)
/*      */                 count[a[perm[i]] >>> shift & 0xFF ^ 0x0] = count[a[perm[i]] >>> shift & 0xFF ^ 0x0] + 1; 
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
/*      */                   pos[a[perm[j]] >>> shift & 0xFF ^ 0x0] = pos[a[perm[j]] >>> shift & 0xFF ^ 0x0] - 1;
/*      */                   support[pos[a[perm[j]] >>> shift & 0xFF ^ 0x0] - 1] = perm[j];
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
/*      */                   c = a[t] >>> shift & 0xFF ^ 0x0;
/*      */                   if (k < end) {
/*      */                     pos[c] = pos[c] - 1;
/*      */                     int d;
/*      */                     while ((d = pos[c] - 1) > k) {
/*      */                       int z = t;
/*      */                       t = perm[d];
/*      */                       perm[d] = z;
/*      */                       c = a[t] >>> shift & 0xFF ^ 0x0;
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
/* 2495 */     Throwable problem = null;
/* 2496 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2498 */         executorCompletionService.take().get();
/* 2499 */       } catch (Exception e) {
/* 2500 */         problem = e.getCause();
/*      */       } 
/* 2502 */     }  executorService.shutdown();
/* 2503 */     if (problem != null) {
/* 2504 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, char[] a, boolean stable) {
/* 2531 */     parallelRadixSortIndirect(perm, a, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(char[] a, char[] b) {
/* 2553 */     ensureSameLength(a, b);
/* 2554 */     radixSort(a, b, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(char[] a, char[] b, int from, int to) {
/* 2581 */     if (to - from < 1024) {
/* 2582 */       selectionSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2585 */     int layers = 2;
/* 2586 */     int maxLevel = 3;
/* 2587 */     int stackSize = 766;
/* 2588 */     int stackPos = 0;
/* 2589 */     int[] offsetStack = new int[766];
/* 2590 */     int[] lengthStack = new int[766];
/* 2591 */     int[] levelStack = new int[766];
/* 2592 */     offsetStack[stackPos] = from;
/* 2593 */     lengthStack[stackPos] = to - from;
/* 2594 */     levelStack[stackPos++] = 0;
/* 2595 */     int[] count = new int[256];
/* 2596 */     int[] pos = new int[256];
/* 2597 */     while (stackPos > 0) {
/* 2598 */       int first = offsetStack[--stackPos];
/* 2599 */       int length = lengthStack[stackPos];
/* 2600 */       int level = levelStack[stackPos];
/* 2601 */       int signMask = 0;
/* 2602 */       char[] k = (level < 2) ? a : b;
/* 2603 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2608 */       for (int i = first + length; i-- != first;) {
/* 2609 */         count[k[i] >>> shift & 0xFF ^ 0x0] = count[k[i] >>> shift & 0xFF ^ 0x0] + 1;
/*      */       }
/* 2611 */       int lastUsed = -1;
/* 2612 */       for (int j = 0, p = first; j < 256; j++) {
/* 2613 */         if (count[j] != 0)
/* 2614 */           lastUsed = j; 
/* 2615 */         pos[j] = p += count[j];
/*      */       } 
/* 2617 */       int end = first + length - count[lastUsed];
/*      */       
/* 2619 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2620 */         char t = a[m];
/* 2621 */         char u = b[m];
/* 2622 */         c = k[m] >>> shift & 0xFF ^ 0x0;
/* 2623 */         if (m < end) {
/* 2624 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2625 */             c = k[d] >>> shift & 0xFF ^ 0x0;
/* 2626 */             char z = t;
/* 2627 */             t = a[d];
/* 2628 */             a[d] = z;
/* 2629 */             z = u;
/* 2630 */             u = b[d];
/* 2631 */             b[d] = z;
/*      */           } 
/* 2633 */           a[m] = t;
/* 2634 */           b[m] = u;
/*      */         } 
/* 2636 */         if (level < 3 && count[c] > 1) {
/* 2637 */           if (count[c] < 1024) {
/* 2638 */             selectionSort(a, b, m, m + count[c]);
/*      */           } else {
/* 2640 */             offsetStack[stackPos] = m;
/* 2641 */             lengthStack[stackPos] = count[c];
/* 2642 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static void parallelRadixSort(char[] a, char[] b, int from, int to) {
/* 2678 */     if (to - from < 1024) {
/* 2679 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2682 */     int layers = 2;
/* 2683 */     if (a.length != b.length)
/* 2684 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 2685 */     int maxLevel = 3;
/* 2686 */     LinkedBlockingQueue<Segment> queue = new LinkedBlockingQueue<>();
/* 2687 */     queue.add(new Segment(from, to - from, 0));
/* 2688 */     AtomicInteger queueSize = new AtomicInteger(1);
/* 2689 */     int numberOfThreads = Runtime.getRuntime().availableProcessors();
/* 2690 */     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads, 
/* 2691 */         Executors.defaultThreadFactory());
/* 2692 */     ExecutorCompletionService<Void> executorCompletionService = new ExecutorCompletionService<>(executorService);
/*      */     
/* 2694 */     for (int j = numberOfThreads; j-- != 0;) {
/* 2695 */       executorCompletionService.submit(() -> {
/*      */             int[] count = new int[256]; int[] pos = new int[256]; while (true) {
/*      */               if (queueSize.get() == 0) {
/*      */                 int n = numberOfThreads; while (n-- != 0)
/*      */                   queue.add(POISON_PILL); 
/*      */               }  Segment segment = queue.take(); if (segment == POISON_PILL)
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 2 == 0) ? 128 : 0; char[] k = (level < 2) ? a : b;
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
/*      */                 char t = a[m];
/*      */                 char u = b[m];
/*      */                 c = k[m] >>> shift & 0xFF ^ signMask;
/*      */                 if (m < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > m) {
/*      */                     c = k[d] >>> shift & 0xFF ^ signMask;
/*      */                     char z = t;
/*      */                     char w = u;
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
/* 2751 */     Throwable problem = null;
/* 2752 */     for (int i = numberOfThreads; i-- != 0;) {
/*      */       try {
/* 2754 */         executorCompletionService.take().get();
/* 2755 */       } catch (Exception e) {
/* 2756 */         problem = e.getCause();
/*      */       } 
/* 2758 */     }  executorService.shutdown();
/* 2759 */     if (problem != null) {
/* 2760 */       throw (problem instanceof RuntimeException) ? (RuntimeException)problem : new RuntimeException(problem);
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
/*      */   public static void parallelRadixSort(char[] a, char[] b) {
/* 2787 */     ensureSameLength(a, b);
/* 2788 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, char[] a, char[] b, int from, int to) {
/* 2792 */     for (int i = from; ++i < to; ) {
/* 2793 */       int t = perm[i];
/* 2794 */       int j = i; int u;
/* 2795 */       for (u = perm[j - 1]; a[t] < a[u] || (a[t] == a[u] && b[t] < b[u]); u = perm[--j - 1]) {
/* 2796 */         perm[j] = u;
/* 2797 */         if (from == j - 1) {
/* 2798 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2802 */       perm[j] = t;
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
/*      */   public static void radixSortIndirect(int[] perm, char[] a, char[] b, boolean stable) {
/* 2836 */     ensureSameLength(a, b);
/* 2837 */     radixSortIndirect(perm, a, b, 0, a.length, stable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSortIndirect(int[] perm, char[] a, char[] b, int from, int to, boolean stable) {
/* 2877 */     if (to - from < 1024) {
/* 2878 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2881 */     int layers = 2;
/* 2882 */     int maxLevel = 3;
/* 2883 */     int stackSize = 766;
/* 2884 */     int stackPos = 0;
/* 2885 */     int[] offsetStack = new int[766];
/* 2886 */     int[] lengthStack = new int[766];
/* 2887 */     int[] levelStack = new int[766];
/* 2888 */     offsetStack[stackPos] = from;
/* 2889 */     lengthStack[stackPos] = to - from;
/* 2890 */     levelStack[stackPos++] = 0;
/* 2891 */     int[] count = new int[256];
/* 2892 */     int[] pos = new int[256];
/* 2893 */     int[] support = stable ? new int[perm.length] : null;
/* 2894 */     while (stackPos > 0) {
/* 2895 */       int first = offsetStack[--stackPos];
/* 2896 */       int length = lengthStack[stackPos];
/* 2897 */       int level = levelStack[stackPos];
/* 2898 */       int signMask = 0;
/* 2899 */       char[] k = (level < 2) ? a : b;
/* 2900 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2905 */       for (int i = first + length; i-- != first;) {
/* 2906 */         count[k[perm[i]] >>> shift & 0xFF ^ 0x0] = count[k[perm[i]] >>> shift & 0xFF ^ 0x0] + 1;
/*      */       }
/* 2908 */       int lastUsed = -1; int j, p;
/* 2909 */       for (j = 0, p = stable ? 0 : first; j < 256; j++) {
/* 2910 */         if (count[j] != 0)
/* 2911 */           lastUsed = j; 
/* 2912 */         pos[j] = p += count[j];
/*      */       } 
/* 2914 */       if (stable) {
/* 2915 */         for (j = first + length; j-- != first; ) {
/* 2916 */           pos[k[perm[j]] >>> shift & 0xFF ^ 0x0] = pos[k[perm[j]] >>> shift & 0xFF ^ 0x0] - 1; support[pos[k[perm[j]] >>> shift & 0xFF ^ 0x0] - 1] = perm[j];
/* 2917 */         }  System.arraycopy(support, 0, perm, first, length);
/* 2918 */         for (j = 0, p = first; j < 256; j++) {
/* 2919 */           if (level < 3 && count[j] > 1) {
/* 2920 */             if (count[j] < 1024) {
/* 2921 */               insertionSortIndirect(perm, a, b, p, p + count[j]);
/*      */             } else {
/* 2923 */               offsetStack[stackPos] = p;
/* 2924 */               lengthStack[stackPos] = count[j];
/* 2925 */               levelStack[stackPos++] = level + 1;
/*      */             } 
/*      */           }
/* 2928 */           p += count[j];
/*      */         } 
/* 2930 */         Arrays.fill(count, 0); continue;
/*      */       } 
/* 2932 */       int end = first + length - count[lastUsed];
/*      */       
/* 2934 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 2935 */         int t = perm[m];
/* 2936 */         c = k[t] >>> shift & 0xFF ^ 0x0;
/* 2937 */         if (m < end) {
/* 2938 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2939 */             int z = t;
/* 2940 */             t = perm[d];
/* 2941 */             perm[d] = z;
/* 2942 */             c = k[t] >>> shift & 0xFF ^ 0x0;
/*      */           } 
/* 2944 */           perm[m] = t;
/*      */         } 
/* 2946 */         if (level < 3 && count[c] > 1) {
/* 2947 */           if (count[c] < 1024) {
/* 2948 */             insertionSortIndirect(perm, a, b, m, m + count[c]);
/*      */           } else {
/* 2950 */             offsetStack[stackPos] = m;
/* 2951 */             lengthStack[stackPos] = count[c];
/* 2952 */             levelStack[stackPos++] = level + 1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void selectionSort(char[][] a, int from, int to, int level) {
/* 2960 */     int layers = a.length;
/* 2961 */     int firstLayer = level / 2;
/* 2962 */     for (int i = from; i < to - 1; i++) {
/* 2963 */       int m = i;
/* 2964 */       for (int j = i + 1; j < to; j++) {
/* 2965 */         for (int p = firstLayer; p < layers; p++) {
/* 2966 */           if (a[p][j] < a[p][m]) {
/* 2967 */             m = j; break;
/*      */           } 
/* 2969 */           if (a[p][j] > a[p][m])
/*      */             break; 
/*      */         } 
/*      */       } 
/* 2973 */       if (m != i) {
/* 2974 */         for (int p = layers; p-- != 0; ) {
/* 2975 */           char u = a[p][i];
/* 2976 */           a[p][i] = a[p][m];
/* 2977 */           a[p][m] = u;
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
/*      */   public static void radixSort(char[][] a) {
/* 3000 */     radixSort(a, 0, (a[0]).length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void radixSort(char[][] a, int from, int to) {
/* 3024 */     if (to - from < 1024) {
/* 3025 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 3028 */     int layers = a.length;
/* 3029 */     int maxLevel = 2 * layers - 1;
/* 3030 */     for (int p = layers, l = (a[0]).length; p-- != 0;) {
/* 3031 */       if ((a[p]).length != l)
/* 3032 */         throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0."); 
/*      */     } 
/* 3034 */     int stackSize = 255 * (layers * 2 - 1) + 1;
/* 3035 */     int stackPos = 0;
/* 3036 */     int[] offsetStack = new int[stackSize];
/* 3037 */     int[] lengthStack = new int[stackSize];
/* 3038 */     int[] levelStack = new int[stackSize];
/* 3039 */     offsetStack[stackPos] = from;
/* 3040 */     lengthStack[stackPos] = to - from;
/* 3041 */     levelStack[stackPos++] = 0;
/* 3042 */     int[] count = new int[256];
/* 3043 */     int[] pos = new int[256];
/* 3044 */     char[] t = new char[layers];
/* 3045 */     while (stackPos > 0) {
/* 3046 */       int first = offsetStack[--stackPos];
/* 3047 */       int length = lengthStack[stackPos];
/* 3048 */       int level = levelStack[stackPos];
/* 3049 */       int signMask = 0;
/* 3050 */       char[] k = a[level / 2];
/* 3051 */       int shift = (1 - level % 2) * 8;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3056 */       for (int i = first + length; i-- != first;) {
/* 3057 */         count[k[i] >>> shift & 0xFF ^ 0x0] = count[k[i] >>> shift & 0xFF ^ 0x0] + 1;
/*      */       }
/* 3059 */       int lastUsed = -1;
/* 3060 */       for (int j = 0, n = first; j < 256; j++) {
/* 3061 */         if (count[j] != 0)
/* 3062 */           lastUsed = j; 
/* 3063 */         pos[j] = n += count[j];
/*      */       } 
/* 3065 */       int end = first + length - count[lastUsed];
/*      */       
/* 3067 */       for (int m = first, c = -1; m <= end; m += count[c], count[c] = 0) {
/* 3068 */         int i1; for (i1 = layers; i1-- != 0;)
/* 3069 */           t[i1] = a[i1][m]; 
/* 3070 */         c = k[m] >>> shift & 0xFF ^ 0x0;
/* 3071 */         if (m < end) {
/* 3072 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 3073 */             c = k[d] >>> shift & 0xFF ^ 0x0;
/* 3074 */             for (i1 = layers; i1-- != 0; ) {
/* 3075 */               char u = t[i1];
/* 3076 */               t[i1] = a[i1][d];
/* 3077 */               a[i1][d] = u;
/*      */             } 
/*      */           } 
/* 3080 */           for (i1 = layers; i1-- != 0;)
/* 3081 */             a[i1][m] = t[i1]; 
/*      */         } 
/* 3083 */         if (level < maxLevel && count[c] > 1) {
/* 3084 */           if (count[c] < 1024) {
/* 3085 */             selectionSort(a, m, m + count[c], level + 1);
/*      */           } else {
/* 3087 */             offsetStack[stackPos] = m;
/* 3088 */             lengthStack[stackPos] = count[c];
/* 3089 */             levelStack[stackPos++] = level + 1;
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
/*      */   public static char[] shuffle(char[] a, int from, int to, Random random) {
/* 3110 */     for (int i = to - from; i-- != 0; ) {
/* 3111 */       int p = random.nextInt(i + 1);
/* 3112 */       char t = a[from + i];
/* 3113 */       a[from + i] = a[from + p];
/* 3114 */       a[from + p] = t;
/*      */     } 
/* 3116 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[] shuffle(char[] a, Random random) {
/* 3129 */     for (int i = a.length; i-- != 0; ) {
/* 3130 */       int p = random.nextInt(i + 1);
/* 3131 */       char t = a[i];
/* 3132 */       a[i] = a[p];
/* 3133 */       a[p] = t;
/*      */     } 
/* 3135 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[] reverse(char[] a) {
/* 3145 */     int length = a.length;
/* 3146 */     for (int i = length / 2; i-- != 0; ) {
/* 3147 */       char t = a[length - i - 1];
/* 3148 */       a[length - i - 1] = a[i];
/* 3149 */       a[i] = t;
/*      */     } 
/* 3151 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[] reverse(char[] a, int from, int to) {
/* 3165 */     int length = to - from;
/* 3166 */     for (int i = length / 2; i-- != 0; ) {
/* 3167 */       char t = a[from + length - i - 1];
/* 3168 */       a[from + length - i - 1] = a[from + i];
/* 3169 */       a[from + i] = t;
/*      */     } 
/* 3171 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<char[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(char[] o) {
/* 3178 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(char[] a, char[] b) {
/* 3182 */       return Arrays.equals(a, b);
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
/* 3193 */   public static final Hash.Strategy<char[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */