/*      */ package it.unimi.dsi.fastutil.ints;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
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
/*      */ public final class IntArrays
/*      */ {
/*  102 */   public static final int[] EMPTY_ARRAY = new int[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   public static final int[] DEFAULT_EMPTY_ARRAY = new int[0];
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
/*      */   public static int[] forceCapacity(int[] array, int length, int preserve) {
/*  127 */     int[] t = new int[length];
/*  128 */     System.arraycopy(array, 0, t, 0, preserve);
/*  129 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] ensureCapacity(int[] array, int length) {
/*  147 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] ensureCapacity(int[] array, int length, int preserve) {
/*  165 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] grow(int[] array, int length) {
/*  186 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] grow(int[] array, int length, int preserve) {
/*  210 */     if (length > array.length) {
/*      */       
/*  212 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  213 */       int[] t = new int[newLength];
/*  214 */       System.arraycopy(array, 0, t, 0, preserve);
/*  215 */       return t;
/*      */     } 
/*  217 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] trim(int[] array, int length) {
/*  232 */     if (length >= array.length)
/*  233 */       return array; 
/*  234 */     int[] t = (length == 0) ? EMPTY_ARRAY : new int[length];
/*  235 */     System.arraycopy(array, 0, t, 0, length);
/*  236 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] setLength(int[] array, int length) {
/*  254 */     if (length == array.length)
/*  255 */       return array; 
/*  256 */     if (length < array.length)
/*  257 */       return trim(array, length); 
/*  258 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] copy(int[] array, int offset, int length) {
/*  273 */     ensureOffsetLength(array, offset, length);
/*  274 */     int[] a = (length == 0) ? EMPTY_ARRAY : new int[length];
/*  275 */     System.arraycopy(array, offset, a, 0, length);
/*  276 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] copy(int[] array) {
/*  286 */     return (int[])array.clone();
/*      */   }
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
/*      */   public static void fill(int[] array, int value) {
/*  299 */     int i = array.length;
/*  300 */     while (i-- != 0) {
/*  301 */       array[i] = value;
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
/*      */   public static void fill(int[] array, int from, int to, int value) {
/*  319 */     ensureFromTo(array, from, to);
/*  320 */     if (from == 0) {
/*  321 */       while (to-- != 0)
/*  322 */         array[to] = value; 
/*      */     } else {
/*  324 */       for (int i = from; i < to; i++) {
/*  325 */         array[i] = value;
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
/*      */   public static boolean equals(int[] a1, int[] a2) {
/*  341 */     int i = a1.length;
/*  342 */     if (i != a2.length)
/*  343 */       return false; 
/*  344 */     while (i-- != 0) {
/*  345 */       if (a1[i] != a2[i])
/*  346 */         return false; 
/*  347 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(int[] a, int from, int to) {
/*  369 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(int[] a, int offset, int length) {
/*  390 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(int[] a, int[] b) {
/*  403 */     if (a.length != b.length) {
/*  404 */       throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
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
/*      */   public static void swap(int[] x, int a, int b) {
/*  421 */     int t = x[a];
/*  422 */     x[a] = x[b];
/*  423 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[] x, int a, int b, int n) {
/*  439 */     for (int i = 0; i < n; i++, a++, b++)
/*  440 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(int[] x, int a, int b, int c, IntComparator comp) {
/*  443 */     int ab = comp.compare(x[a], x[b]);
/*  444 */     int ac = comp.compare(x[a], x[c]);
/*  445 */     int bc = comp.compare(x[b], x[c]);
/*  446 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(int[] a, int from, int to, IntComparator comp) {
/*  449 */     for (int i = from; i < to - 1; i++) {
/*  450 */       int m = i;
/*  451 */       for (int j = i + 1; j < to; j++) {
/*  452 */         if (comp.compare(a[j], a[m]) < 0)
/*  453 */           m = j; 
/*  454 */       }  if (m != i) {
/*  455 */         int u = a[i];
/*  456 */         a[i] = a[m];
/*  457 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(int[] a, int from, int to, IntComparator comp) {
/*  462 */     for (int i = from; ++i < to; ) {
/*  463 */       int t = a[i];
/*  464 */       int j = i; int u;
/*  465 */       for (u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  466 */         a[j] = u;
/*  467 */         if (from == j - 1) {
/*  468 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  472 */       a[j] = t;
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
/*      */   public static void quickSort(int[] x, int from, int to, IntComparator comp) {
/*  500 */     int len = to - from;
/*      */     
/*  502 */     if (len < 16) {
/*  503 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  507 */     int m = from + len / 2;
/*  508 */     int l = from;
/*  509 */     int n = to - 1;
/*  510 */     if (len > 128) {
/*  511 */       int i = len / 8;
/*  512 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  513 */       m = med3(x, m - i, m, m + i, comp);
/*  514 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  516 */     m = med3(x, l, m, n, comp);
/*  517 */     int v = x[m];
/*      */     
/*  519 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  522 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  523 */         if (comparison == 0)
/*  524 */           swap(x, a++, b); 
/*  525 */         b++; continue;
/*      */       } 
/*  527 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  528 */         if (comparison == 0)
/*  529 */           swap(x, c, d--); 
/*  530 */         c--;
/*      */       } 
/*  532 */       if (b > c)
/*      */         break; 
/*  534 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  538 */     int s = Math.min(a - from, b - a);
/*  539 */     swap(x, from, b - s, s);
/*  540 */     s = Math.min(d - c, to - d - 1);
/*  541 */     swap(x, b, to - s, s);
/*      */     
/*  543 */     if ((s = b - a) > 1)
/*  544 */       quickSort(x, from, from + s, comp); 
/*  545 */     if ((s = d - c) > 1) {
/*  546 */       quickSort(x, to - s, to, comp);
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
/*      */   public static void quickSort(int[] x, IntComparator comp) {
/*  569 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] x;
/*      */     private final IntComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(int[] x, int from, int to, IntComparator comp) {
/*  578 */       this.from = from;
/*  579 */       this.to = to;
/*  580 */       this.x = x;
/*  581 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  585 */       int[] x = this.x;
/*  586 */       int len = this.to - this.from;
/*  587 */       if (len < 8192) {
/*  588 */         IntArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  592 */       int m = this.from + len / 2;
/*  593 */       int l = this.from;
/*  594 */       int n = this.to - 1;
/*  595 */       int s = len / 8;
/*  596 */       l = IntArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  597 */       m = IntArrays.med3(x, m - s, m, m + s, this.comp);
/*  598 */       n = IntArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  599 */       m = IntArrays.med3(x, l, m, n, this.comp);
/*  600 */       int v = x[m];
/*      */       
/*  602 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  605 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  606 */           if (comparison == 0)
/*  607 */             IntArrays.swap(x, a++, b); 
/*  608 */           b++; continue;
/*      */         } 
/*  610 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  611 */           if (comparison == 0)
/*  612 */             IntArrays.swap(x, c, d--); 
/*  613 */           c--;
/*      */         } 
/*  615 */         if (b > c)
/*      */           break; 
/*  617 */         IntArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  621 */       s = Math.min(a - this.from, b - a);
/*  622 */       IntArrays.swap(x, this.from, b - s, s);
/*  623 */       s = Math.min(d - c, this.to - d - 1);
/*  624 */       IntArrays.swap(x, b, this.to - s, s);
/*      */       
/*  626 */       s = b - a;
/*  627 */       int t = d - c;
/*  628 */       if (s > 1 && t > 1) {
/*  629 */         invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp));
/*      */       }
/*  631 */       else if (s > 1) {
/*  632 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) });
/*      */       } else {
/*  634 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) });
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
/*      */   public static void parallelQuickSort(int[] x, int from, int to, IntComparator comp) {
/*  660 */     if (to - from < 8192) {
/*  661 */       quickSort(x, from, to, comp);
/*      */     } else {
/*  663 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  664 */       pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp));
/*  665 */       pool.shutdown();
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
/*      */   public static void parallelQuickSort(int[] x, IntComparator comp) {
/*  687 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(int[] x, int a, int b, int c) {
/*  691 */     int ab = Integer.compare(x[a], x[b]);
/*  692 */     int ac = Integer.compare(x[a], x[c]);
/*  693 */     int bc = Integer.compare(x[b], x[c]);
/*  694 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(int[] a, int from, int to) {
/*  698 */     for (int i = from; i < to - 1; i++) {
/*  699 */       int m = i;
/*  700 */       for (int j = i + 1; j < to; j++) {
/*  701 */         if (a[j] < a[m])
/*  702 */           m = j; 
/*  703 */       }  if (m != i) {
/*  704 */         int u = a[i];
/*  705 */         a[i] = a[m];
/*  706 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(int[] a, int from, int to) {
/*  712 */     for (int i = from; ++i < to; ) {
/*  713 */       int t = a[i];
/*  714 */       int j = i; int u;
/*  715 */       for (u = a[j - 1]; t < u; u = a[--j - 1]) {
/*  716 */         a[j] = u;
/*  717 */         if (from == j - 1) {
/*  718 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  722 */       a[j] = t;
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
/*      */   public static void quickSort(int[] x, int from, int to) {
/*  748 */     int len = to - from;
/*      */     
/*  750 */     if (len < 16) {
/*  751 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  755 */     int m = from + len / 2;
/*  756 */     int l = from;
/*  757 */     int n = to - 1;
/*  758 */     if (len > 128) {
/*  759 */       int i = len / 8;
/*  760 */       l = med3(x, l, l + i, l + 2 * i);
/*  761 */       m = med3(x, m - i, m, m + i);
/*  762 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  764 */     m = med3(x, l, m, n);
/*  765 */     int v = x[m];
/*      */     
/*  767 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  770 */       if (b <= c && (comparison = Integer.compare(x[b], v)) <= 0) {
/*  771 */         if (comparison == 0)
/*  772 */           swap(x, a++, b); 
/*  773 */         b++; continue;
/*      */       } 
/*  775 */       while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
/*  776 */         if (comparison == 0)
/*  777 */           swap(x, c, d--); 
/*  778 */         c--;
/*      */       } 
/*  780 */       if (b > c)
/*      */         break; 
/*  782 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  786 */     int s = Math.min(a - from, b - a);
/*  787 */     swap(x, from, b - s, s);
/*  788 */     s = Math.min(d - c, to - d - 1);
/*  789 */     swap(x, b, to - s, s);
/*      */     
/*  791 */     if ((s = b - a) > 1)
/*  792 */       quickSort(x, from, from + s); 
/*  793 */     if ((s = d - c) > 1) {
/*  794 */       quickSort(x, to - s, to);
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
/*      */   public static void quickSort(int[] x) {
/*  814 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] x;
/*      */     
/*      */     public ForkJoinQuickSort(int[] x, int from, int to) {
/*  822 */       this.from = from;
/*  823 */       this.to = to;
/*  824 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  829 */       int[] x = this.x;
/*  830 */       int len = this.to - this.from;
/*  831 */       if (len < 8192) {
/*  832 */         IntArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  836 */       int m = this.from + len / 2;
/*  837 */       int l = this.from;
/*  838 */       int n = this.to - 1;
/*  839 */       int s = len / 8;
/*  840 */       l = IntArrays.med3(x, l, l + s, l + 2 * s);
/*  841 */       m = IntArrays.med3(x, m - s, m, m + s);
/*  842 */       n = IntArrays.med3(x, n - 2 * s, n - s, n);
/*  843 */       m = IntArrays.med3(x, l, m, n);
/*  844 */       int v = x[m];
/*      */       
/*  846 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  849 */         if (b <= c && (comparison = Integer.compare(x[b], v)) <= 0) {
/*  850 */           if (comparison == 0)
/*  851 */             IntArrays.swap(x, a++, b); 
/*  852 */           b++; continue;
/*      */         } 
/*  854 */         while (c >= b && (comparison = Integer.compare(x[c], v)) >= 0) {
/*  855 */           if (comparison == 0)
/*  856 */             IntArrays.swap(x, c, d--); 
/*  857 */           c--;
/*      */         } 
/*  859 */         if (b > c)
/*      */           break; 
/*  861 */         IntArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  865 */       s = Math.min(a - this.from, b - a);
/*  866 */       IntArrays.swap(x, this.from, b - s, s);
/*  867 */       s = Math.min(d - c, this.to - d - 1);
/*  868 */       IntArrays.swap(x, b, this.to - s, s);
/*      */       
/*  870 */       s = b - a;
/*  871 */       int t = d - c;
/*  872 */       if (s > 1 && t > 1) {
/*  873 */         invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to));
/*  874 */       } else if (s > 1) {
/*  875 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) });
/*      */       } else {
/*  877 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) });
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
/*      */   public static void parallelQuickSort(int[] x, int from, int to) {
/*  901 */     if (to - from < 8192) {
/*  902 */       quickSort(x, from, to);
/*      */     } else {
/*  904 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  905 */       pool.invoke(new ForkJoinQuickSort(x, from, to));
/*  906 */       pool.shutdown();
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
/*      */   public static void parallelQuickSort(int[] x) {
/*  927 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, int[] x, int a, int b, int c) {
/*  931 */     int aa = x[perm[a]];
/*  932 */     int bb = x[perm[b]];
/*  933 */     int cc = x[perm[c]];
/*  934 */     int ab = Integer.compare(aa, bb);
/*  935 */     int ac = Integer.compare(aa, cc);
/*  936 */     int bc = Integer.compare(bb, cc);
/*  937 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, int[] a, int from, int to) {
/*  941 */     for (int i = from; ++i < to; ) {
/*  942 */       int t = perm[i];
/*  943 */       int j = i; int u;
/*  944 */       for (u = perm[j - 1]; a[t] < a[u]; u = perm[--j - 1]) {
/*  945 */         perm[j] = u;
/*  946 */         if (from == j - 1) {
/*  947 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  951 */       perm[j] = t;
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
/*      */   public static void quickSortIndirect(int[] perm, int[] x, int from, int to) {
/*  984 */     int len = to - from;
/*      */     
/*  986 */     if (len < 16) {
/*  987 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  991 */     int m = from + len / 2;
/*  992 */     int l = from;
/*  993 */     int n = to - 1;
/*  994 */     if (len > 128) {
/*  995 */       int i = len / 8;
/*  996 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/*  997 */       m = med3Indirect(perm, x, m - i, m, m + i);
/*  998 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/* 1000 */     m = med3Indirect(perm, x, l, m, n);
/* 1001 */     int v = x[perm[m]];
/*      */     
/* 1003 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1006 */       if (b <= c && (comparison = Integer.compare(x[perm[b]], v)) <= 0) {
/* 1007 */         if (comparison == 0)
/* 1008 */           swap(perm, a++, b); 
/* 1009 */         b++; continue;
/*      */       } 
/* 1011 */       while (c >= b && (comparison = Integer.compare(x[perm[c]], v)) >= 0) {
/* 1012 */         if (comparison == 0)
/* 1013 */           swap(perm, c, d--); 
/* 1014 */         c--;
/*      */       } 
/* 1016 */       if (b > c)
/*      */         break; 
/* 1018 */       swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1022 */     int s = Math.min(a - from, b - a);
/* 1023 */     swap(perm, from, b - s, s);
/* 1024 */     s = Math.min(d - c, to - d - 1);
/* 1025 */     swap(perm, b, to - s, s);
/*      */     
/* 1027 */     if ((s = b - a) > 1)
/* 1028 */       quickSortIndirect(perm, x, from, from + s); 
/* 1029 */     if ((s = d - c) > 1) {
/* 1030 */       quickSortIndirect(perm, x, to - s, to);
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
/*      */   public static void quickSortIndirect(int[] perm, int[] x) {
/* 1057 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final int[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, int[] x, int from, int to) {
/* 1066 */       this.from = from;
/* 1067 */       this.to = to;
/* 1068 */       this.x = x;
/* 1069 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1074 */       int[] x = this.x;
/* 1075 */       int len = this.to - this.from;
/* 1076 */       if (len < 8192) {
/* 1077 */         IntArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1081 */       int m = this.from + len / 2;
/* 1082 */       int l = this.from;
/* 1083 */       int n = this.to - 1;
/* 1084 */       int s = len / 8;
/* 1085 */       l = IntArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1086 */       m = IntArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1087 */       n = IntArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1088 */       m = IntArrays.med3Indirect(this.perm, x, l, m, n);
/* 1089 */       int v = x[this.perm[m]];
/*      */       
/* 1091 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1094 */         if (b <= c && (comparison = Integer.compare(x[this.perm[b]], v)) <= 0) {
/* 1095 */           if (comparison == 0)
/* 1096 */             IntArrays.swap(this.perm, a++, b); 
/* 1097 */           b++; continue;
/*      */         } 
/* 1099 */         while (c >= b && (comparison = Integer.compare(x[this.perm[c]], v)) >= 0) {
/* 1100 */           if (comparison == 0)
/* 1101 */             IntArrays.swap(this.perm, c, d--); 
/* 1102 */           c--;
/*      */         } 
/* 1104 */         if (b > c)
/*      */           break; 
/* 1106 */         IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1110 */       s = Math.min(a - this.from, b - a);
/* 1111 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1112 */       s = Math.min(d - c, this.to - d - 1);
/* 1113 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1115 */       s = b - a;
/* 1116 */       int t = d - c;
/* 1117 */       if (s > 1 && t > 1) {
/* 1118 */         invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to));
/*      */       }
/* 1120 */       else if (s > 1) {
/* 1121 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) });
/*      */       } else {
/* 1123 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) });
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, int[] x, int from, int to) {
/* 1154 */     if (to - from < 8192) {
/* 1155 */       quickSortIndirect(perm, x, from, to);
/*      */     } else {
/* 1157 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1158 */       pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to));
/* 1159 */       pool.shutdown();
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, int[] x) {
/* 1187 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stabilize(int[] perm, int[] x, int from, int to) {
/* 1220 */     int curr = from;
/* 1221 */     for (int i = from + 1; i < to; i++) {
/* 1222 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1223 */         if (i - curr > 1)
/* 1224 */           parallelQuickSort(perm, curr, i); 
/* 1225 */         curr = i;
/*      */       } 
/*      */     } 
/* 1228 */     if (to - curr > 1) {
/* 1229 */       parallelQuickSort(perm, curr, to);
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
/*      */   public static void stabilize(int[] perm, int[] x) {
/* 1258 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(int[] x, int[] y, int a, int b, int c) {
/* 1263 */     int t, ab = ((t = Integer.compare(x[a], x[b])) == 0) ? Integer.compare(y[a], y[b]) : t;
/* 1264 */     int ac = ((t = Integer.compare(x[a], x[c])) == 0) ? Integer.compare(y[a], y[c]) : t;
/* 1265 */     int bc = ((t = Integer.compare(x[b], x[c])) == 0) ? Integer.compare(y[b], y[c]) : t;
/* 1266 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(int[] x, int[] y, int a, int b) {
/* 1269 */     int t = x[a];
/* 1270 */     int u = y[a];
/* 1271 */     x[a] = x[b];
/* 1272 */     y[a] = y[b];
/* 1273 */     x[b] = t;
/* 1274 */     y[b] = u;
/*      */   }
/*      */   private static void swap(int[] x, int[] y, int a, int b, int n) {
/* 1277 */     for (int i = 0; i < n; i++, a++, b++)
/* 1278 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(int[] a, int[] b, int from, int to) {
/* 1282 */     for (int i = from; i < to - 1; i++) {
/* 1283 */       int m = i;
/* 1284 */       for (int j = i + 1; j < to; j++) {
/* 1285 */         int u; if ((u = Integer.compare(a[j], a[m])) < 0 || (u == 0 && b[j] < b[m]))
/* 1286 */           m = j; 
/* 1287 */       }  if (m != i) {
/* 1288 */         int t = a[i];
/* 1289 */         a[i] = a[m];
/* 1290 */         a[m] = t;
/* 1291 */         t = b[i];
/* 1292 */         b[i] = b[m];
/* 1293 */         b[m] = t;
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
/*      */   public static void quickSort(int[] x, int[] y, int from, int to) {
/* 1324 */     int len = to - from;
/* 1325 */     if (len < 16) {
/* 1326 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1330 */     int m = from + len / 2;
/* 1331 */     int l = from;
/* 1332 */     int n = to - 1;
/* 1333 */     if (len > 128) {
/* 1334 */       int i = len / 8;
/* 1335 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1336 */       m = med3(x, y, m - i, m, m + i);
/* 1337 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1339 */     m = med3(x, y, l, m, n);
/* 1340 */     int v = x[m], w = y[m];
/*      */     
/* 1342 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1345 */       if (b <= c) {
/*      */         int comparison; int t;
/* 1347 */         if ((comparison = ((t = Integer.compare(x[b], v)) == 0) ? Integer.compare(y[b], w) : t) <= 0) {
/* 1348 */           if (comparison == 0)
/* 1349 */             swap(x, y, a++, b); 
/* 1350 */           b++; continue;
/*      */         } 
/* 1352 */       }  while (c >= b) {
/*      */         int comparison; int t;
/* 1354 */         if ((comparison = ((t = Integer.compare(x[c], v)) == 0) ? Integer.compare(y[c], w) : t) >= 0) {
/* 1355 */           if (comparison == 0)
/* 1356 */             swap(x, y, c, d--); 
/* 1357 */           c--;
/*      */         } 
/* 1359 */       }  if (b > c)
/*      */         break; 
/* 1361 */       swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1365 */     int s = Math.min(a - from, b - a);
/* 1366 */     swap(x, y, from, b - s, s);
/* 1367 */     s = Math.min(d - c, to - d - 1);
/* 1368 */     swap(x, y, b, to - s, s);
/*      */     
/* 1370 */     if ((s = b - a) > 1)
/* 1371 */       quickSort(x, y, from, from + s); 
/* 1372 */     if ((s = d - c) > 1) {
/* 1373 */       quickSort(x, y, to - s, to);
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
/*      */   public static void quickSort(int[] x, int[] y) {
/* 1397 */     ensureSameLength(x, y);
/* 1398 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction { private static final long serialVersionUID = 1L; private final int from;
/*      */     private final int to;
/*      */     private final int[] x;
/*      */     private final int[] y;
/*      */     
/*      */     public ForkJoinQuickSort2(int[] x, int[] y, int from, int to) {
/* 1406 */       this.from = from;
/* 1407 */       this.to = to;
/* 1408 */       this.x = x;
/* 1409 */       this.y = y;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1414 */       int[] x = this.x;
/* 1415 */       int[] y = this.y;
/* 1416 */       int len = this.to - this.from;
/* 1417 */       if (len < 8192) {
/* 1418 */         IntArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1422 */       int m = this.from + len / 2;
/* 1423 */       int l = this.from;
/* 1424 */       int n = this.to - 1;
/* 1425 */       int s = len / 8;
/* 1426 */       l = IntArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1427 */       m = IntArrays.med3(x, y, m - s, m, m + s);
/* 1428 */       n = IntArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1429 */       m = IntArrays.med3(x, y, l, m, n);
/* 1430 */       int v = x[m], w = y[m];
/*      */       
/* 1432 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1435 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1437 */           if ((comparison = ((i = Integer.compare(x[b], v)) == 0) ? Integer.compare(y[b], w) : i) <= 0) {
/* 1438 */             if (comparison == 0)
/* 1439 */               IntArrays.swap(x, y, a++, b); 
/* 1440 */             b++; continue;
/*      */           } 
/* 1442 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1444 */           if ((comparison = ((i = Integer.compare(x[c], v)) == 0) ? Integer.compare(y[c], w) : i) >= 0) {
/* 1445 */             if (comparison == 0)
/* 1446 */               IntArrays.swap(x, y, c, d--); 
/* 1447 */             c--;
/*      */           } 
/* 1449 */         }  if (b > c)
/*      */           break; 
/* 1451 */         IntArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1455 */       s = Math.min(a - this.from, b - a);
/* 1456 */       IntArrays.swap(x, y, this.from, b - s, s);
/* 1457 */       s = Math.min(d - c, this.to - d - 1);
/* 1458 */       IntArrays.swap(x, y, b, this.to - s, s);
/* 1459 */       s = b - a;
/* 1460 */       int t = d - c;
/*      */       
/* 1462 */       if (s > 1 && t > 1) {
/* 1463 */         invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to));
/* 1464 */       } else if (s > 1) {
/* 1465 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) });
/*      */       } else {
/* 1467 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) });
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
/*      */   public static void parallelQuickSort(int[] x, int[] y, int from, int to) {
/* 1500 */     if (to - from < 8192)
/* 1501 */       quickSort(x, y, from, to); 
/* 1502 */     ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1503 */     pool.invoke(new ForkJoinQuickSort2(x, y, from, to));
/* 1504 */     pool.shutdown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(int[] x, int[] y) {
/* 1532 */     ensureSameLength(x, y);
/* 1533 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(int[] a, int from, int to) {
/* 1553 */     if (to - from >= 4000) {
/* 1554 */       radixSort(a, from, to);
/*      */     } else {
/* 1556 */       quickSort(a, from, to);
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
/*      */   public static void unstableSort(int[] a) {
/* 1570 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(int[] a, int from, int to, IntComparator comp) {
/* 1589 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(int[] a, IntComparator comp) {
/* 1603 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a, int from, int to, int[] supp) {
/* 1627 */     int len = to - from;
/*      */     
/* 1629 */     if (len < 16) {
/* 1630 */       insertionSort(a, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1634 */     int mid = from + to >>> 1;
/* 1635 */     mergeSort(supp, from, mid, a);
/* 1636 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1639 */     if (supp[mid - 1] <= supp[mid]) {
/* 1640 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1644 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1645 */       if (q >= to || (p < mid && supp[p] <= supp[q])) {
/* 1646 */         a[i] = supp[p++];
/*      */       } else {
/* 1648 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(int[] a, int from, int to) {
/* 1668 */     mergeSort(a, from, to, (int[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a) {
/* 1682 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a, int from, int to, IntComparator comp, int[] supp) {
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
/*      */   public static void mergeSort(int[] a, int from, int to, IntComparator comp) {
/* 1750 */     mergeSort(a, from, to, comp, (int[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(int[] a, IntComparator comp) {
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
/*      */   public static void stableSort(int[] a, int from, int to) {
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
/*      */   public static void stableSort(int[] a) {
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
/*      */   public static void stableSort(int[] a, int from, int to, IntComparator comp) {
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
/*      */   public static void stableSort(int[] a, IntComparator comp) {
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
/*      */   public static int binarySearch(int[] a, int from, int to, int key) {
/* 1883 */     to--;
/* 1884 */     while (from <= to) {
/* 1885 */       int mid = from + to >>> 1;
/* 1886 */       int midVal = a[mid];
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
/*      */   public static int binarySearch(int[] a, int key) {
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
/*      */   public static int binarySearch(int[] a, int from, int to, int key, IntComparator c) {
/* 1946 */     to--;
/* 1947 */     while (from <= to) {
/* 1948 */       int mid = from + to >>> 1;
/* 1949 */       int midVal = a[mid];
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
/*      */   public static int binarySearch(int[] a, int key, IntComparator c) {
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
/*      */   public static void radixSort(int[] a) {
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
/*      */   public static void radixSort(int[] a, int from, int to) {
/* 2042 */     if (to - from < 1024) {
/* 2043 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2046 */     int maxLevel = 3;
/* 2047 */     int stackSize = 766;
/* 2048 */     int stackPos = 0;
/* 2049 */     int[] offsetStack = new int[766];
/* 2050 */     int[] lengthStack = new int[766];
/* 2051 */     int[] levelStack = new int[766];
/* 2052 */     offsetStack[stackPos] = from;
/* 2053 */     lengthStack[stackPos] = to - from;
/* 2054 */     levelStack[stackPos++] = 0;
/* 2055 */     int[] count = new int[256];
/* 2056 */     int[] pos = new int[256];
/* 2057 */     while (stackPos > 0) {
/* 2058 */       int first = offsetStack[--stackPos];
/* 2059 */       int length = lengthStack[stackPos];
/* 2060 */       int level = levelStack[stackPos];
/* 2061 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2062 */       int shift = (3 - level % 4) * 8;
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
/* 2079 */         int t = a[k];
/* 2080 */         c = t >>> shift & 0xFF ^ signMask;
/* 2081 */         if (k < end) {
/* 2082 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > k; ) {
/* 2083 */             int z = t;
/* 2084 */             t = a[d];
/* 2085 */             a[d] = z;
/* 2086 */             c = t >>> shift & 0xFF ^ signMask;
/*      */           } 
/* 2088 */           a[k] = t;
/*      */         } 
/* 2090 */         if (level < 3 && count[c] > 1)
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
/*      */   public static void parallelRadixSort(int[] a, int from, int to) {
/* 2135 */     if (to - from < 1024) {
/* 2136 */       quickSort(a, from, to);
/*      */       return;
/*      */     } 
/* 2139 */     int maxLevel = 3;
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
/*      */               int signMask = (level % 4 == 0) ? 128 : 0;
/*      */               int shift = (3 - level % 4) * 8;
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
/*      */                 int t = a[k];
/*      */                 c = t >>> shift & 0xFF ^ signMask;
/*      */                 if (k < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > k) {
/*      */                     int z = t;
/*      */                     t = a[d];
/*      */                     a[d] = z;
/*      */                     c = t >>> shift & 0xFF ^ signMask;
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
/*      */   public static void parallelRadixSort(int[] a) {
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
/*      */   public static void radixSortIndirect(int[] perm, int[] a, boolean stable) {
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
/*      */   public static void radixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
/* 2294 */     if (to - from < 1024) {
/* 2295 */       insertionSortIndirect(perm, a, from, to);
/*      */       return;
/*      */     } 
/* 2298 */     int maxLevel = 3;
/* 2299 */     int stackSize = 766;
/* 2300 */     int stackPos = 0;
/* 2301 */     int[] offsetStack = new int[766];
/* 2302 */     int[] lengthStack = new int[766];
/* 2303 */     int[] levelStack = new int[766];
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
/* 2314 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2315 */       int shift = (3 - level % 4) * 8;
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
/* 2334 */           if (level < 3 && count[j] > 1) {
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
/* 2361 */         if (level < 3 && count[c] > 1) {
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, int[] a, int from, int to, boolean stable) {
/* 2404 */     if (to - from < 1024) {
/* 2405 */       radixSortIndirect(perm, a, from, to, stable);
/*      */       return;
/*      */     } 
/* 2408 */     int maxLevel = 3;
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
/*      */               int signMask = (level % 4 == 0) ? 128 : 0;
/*      */               int shift = (3 - level % 4) * 8;
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
/*      */   public static void parallelRadixSortIndirect(int[] perm, int[] a, boolean stable) {
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
/*      */   public static void radixSort(int[] a, int[] b) {
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
/*      */   public static void radixSort(int[] a, int[] b, int from, int to) {
/* 2580 */     if (to - from < 1024) {
/* 2581 */       selectionSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2584 */     int layers = 2;
/* 2585 */     int maxLevel = 7;
/* 2586 */     int stackSize = 1786;
/* 2587 */     int stackPos = 0;
/* 2588 */     int[] offsetStack = new int[1786];
/* 2589 */     int[] lengthStack = new int[1786];
/* 2590 */     int[] levelStack = new int[1786];
/* 2591 */     offsetStack[stackPos] = from;
/* 2592 */     lengthStack[stackPos] = to - from;
/* 2593 */     levelStack[stackPos++] = 0;
/* 2594 */     int[] count = new int[256];
/* 2595 */     int[] pos = new int[256];
/* 2596 */     while (stackPos > 0) {
/* 2597 */       int first = offsetStack[--stackPos];
/* 2598 */       int length = lengthStack[stackPos];
/* 2599 */       int level = levelStack[stackPos];
/* 2600 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2601 */       int[] k = (level < 4) ? a : b;
/* 2602 */       int shift = (3 - level % 4) * 8;
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
/* 2619 */         int t = a[m];
/* 2620 */         int u = b[m];
/* 2621 */         c = k[m] >>> shift & 0xFF ^ signMask;
/* 2622 */         if (m < end) {
/* 2623 */           int d; for (pos[c] = pos[c] - 1; (d = pos[c] - 1) > m; ) {
/* 2624 */             c = k[d] >>> shift & 0xFF ^ signMask;
/* 2625 */             int z = t;
/* 2626 */             t = a[d];
/* 2627 */             a[d] = z;
/* 2628 */             z = u;
/* 2629 */             u = b[d];
/* 2630 */             b[d] = z;
/*      */           } 
/* 2632 */           a[m] = t;
/* 2633 */           b[m] = u;
/*      */         } 
/* 2635 */         if (level < 7 && count[c] > 1) {
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
/*      */   public static void parallelRadixSort(int[] a, int[] b, int from, int to) {
/* 2677 */     if (to - from < 1024) {
/* 2678 */       quickSort(a, b, from, to);
/*      */       return;
/*      */     } 
/* 2681 */     int layers = 2;
/* 2682 */     if (a.length != b.length)
/* 2683 */       throw new IllegalArgumentException("Array size mismatch."); 
/* 2684 */     int maxLevel = 7;
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
/*      */                 return null;  int first = segment.offset; int length = segment.length; int level = segment.level; int signMask = (level % 4 == 0) ? 128 : 0; int[] k = (level < 4) ? a : b;
/*      */               int shift = (3 - level % 4) * 8;
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
/*      */                 int t = a[m];
/*      */                 int u = b[m];
/*      */                 c = k[m] >>> shift & 0xFF ^ signMask;
/*      */                 if (m < end) {
/*      */                   pos[c] = pos[c] - 1;
/*      */                   int d;
/*      */                   while ((d = pos[c] - 1) > m) {
/*      */                     c = k[d] >>> shift & 0xFF ^ signMask;
/*      */                     int z = t;
/*      */                     int w = u;
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
/*      */   public static void parallelRadixSort(int[] a, int[] b) {
/* 2786 */     ensureSameLength(a, b);
/* 2787 */     parallelRadixSort(a, b, 0, a.length);
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, int[] a, int[] b, int from, int to) {
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
/*      */   public static void radixSortIndirect(int[] perm, int[] a, int[] b, boolean stable) {
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
/*      */   public static void radixSortIndirect(int[] perm, int[] a, int[] b, int from, int to, boolean stable) {
/* 2876 */     if (to - from < 1024) {
/* 2877 */       insertionSortIndirect(perm, a, b, from, to);
/*      */       return;
/*      */     } 
/* 2880 */     int layers = 2;
/* 2881 */     int maxLevel = 7;
/* 2882 */     int stackSize = 1786;
/* 2883 */     int stackPos = 0;
/* 2884 */     int[] offsetStack = new int[1786];
/* 2885 */     int[] lengthStack = new int[1786];
/* 2886 */     int[] levelStack = new int[1786];
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
/* 2897 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 2898 */       int[] k = (level < 4) ? a : b;
/* 2899 */       int shift = (3 - level % 4) * 8;
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
/* 2918 */           if (level < 7 && count[j] > 1) {
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
/* 2945 */         if (level < 7 && count[c] > 1) {
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
/*      */   private static void selectionSort(int[][] a, int from, int to, int level) {
/* 2959 */     int layers = a.length;
/* 2960 */     int firstLayer = level / 4;
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
/* 2974 */           int u = a[p][i];
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
/*      */   public static void radixSort(int[][] a) {
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
/*      */   public static void radixSort(int[][] a, int from, int to) {
/* 3023 */     if (to - from < 1024) {
/* 3024 */       selectionSort(a, from, to, 0);
/*      */       return;
/*      */     } 
/* 3027 */     int layers = a.length;
/* 3028 */     int maxLevel = 4 * layers - 1;
/* 3029 */     for (int p = layers, l = (a[0]).length; p-- != 0;) {
/* 3030 */       if ((a[p]).length != l)
/* 3031 */         throw new IllegalArgumentException("The array of index " + p + " has not the same length of the array of index 0."); 
/*      */     } 
/* 3033 */     int stackSize = 255 * (layers * 4 - 1) + 1;
/* 3034 */     int stackPos = 0;
/* 3035 */     int[] offsetStack = new int[stackSize];
/* 3036 */     int[] lengthStack = new int[stackSize];
/* 3037 */     int[] levelStack = new int[stackSize];
/* 3038 */     offsetStack[stackPos] = from;
/* 3039 */     lengthStack[stackPos] = to - from;
/* 3040 */     levelStack[stackPos++] = 0;
/* 3041 */     int[] count = new int[256];
/* 3042 */     int[] pos = new int[256];
/* 3043 */     int[] t = new int[layers];
/* 3044 */     while (stackPos > 0) {
/* 3045 */       int first = offsetStack[--stackPos];
/* 3046 */       int length = lengthStack[stackPos];
/* 3047 */       int level = levelStack[stackPos];
/* 3048 */       int signMask = (level % 4 == 0) ? 128 : 0;
/* 3049 */       int[] k = a[level / 4];
/* 3050 */       int shift = (3 - level % 4) * 8;
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
/* 3074 */               int u = t[i1];
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
/*      */   public static int[] shuffle(int[] a, int from, int to, Random random) {
/* 3109 */     for (int i = to - from; i-- != 0; ) {
/* 3110 */       int p = random.nextInt(i + 1);
/* 3111 */       int t = a[from + i];
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
/*      */   public static int[] shuffle(int[] a, Random random) {
/* 3128 */     for (int i = a.length; i-- != 0; ) {
/* 3129 */       int p = random.nextInt(i + 1);
/* 3130 */       int t = a[i];
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
/*      */   public static int[] reverse(int[] a) {
/* 3144 */     int length = a.length;
/* 3145 */     for (int i = length / 2; i-- != 0; ) {
/* 3146 */       int t = a[length - i - 1];
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
/*      */   public static int[] reverse(int[] a, int from, int to) {
/* 3164 */     int length = to - from;
/* 3165 */     for (int i = length / 2; i-- != 0; ) {
/* 3166 */       int t = a[from + length - i - 1];
/* 3167 */       a[from + length - i - 1] = a[from + i];
/* 3168 */       a[from + i] = t;
/*      */     } 
/* 3170 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<int[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(int[] o) {
/* 3177 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(int[] a, int[] b) {
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
/* 3192 */   public static final Hash.Strategy<int[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */