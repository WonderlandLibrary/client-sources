/*      */ package it.unimi.dsi.fastutil.booleans;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ForkJoinPool;
/*      */ import java.util.concurrent.ForkJoinTask;
/*      */ import java.util.concurrent.RecursiveAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class BooleanArrays
/*      */ {
/*   98 */   public static final boolean[] EMPTY_ARRAY = new boolean[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  107 */   public static final boolean[] DEFAULT_EMPTY_ARRAY = new boolean[0];
/*      */ 
/*      */   
/*      */   private static final int QUICKSORT_NO_REC = 16;
/*      */ 
/*      */   
/*      */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*      */ 
/*      */   
/*      */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*      */ 
/*      */   
/*      */   private static final int MERGESORT_NO_REC = 16;
/*      */ 
/*      */   
/*      */   public static boolean[] forceCapacity(boolean[] array, int length, int preserve) {
/*  123 */     boolean[] t = new boolean[length];
/*  124 */     System.arraycopy(array, 0, t, 0, preserve);
/*  125 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] ensureCapacity(boolean[] array, int length) {
/*  143 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] ensureCapacity(boolean[] array, int length, int preserve) {
/*  161 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] grow(boolean[] array, int length) {
/*  182 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] grow(boolean[] array, int length, int preserve) {
/*  206 */     if (length > array.length) {
/*      */       
/*  208 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  209 */       boolean[] t = new boolean[newLength];
/*  210 */       System.arraycopy(array, 0, t, 0, preserve);
/*  211 */       return t;
/*      */     } 
/*  213 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] trim(boolean[] array, int length) {
/*  228 */     if (length >= array.length)
/*  229 */       return array; 
/*  230 */     boolean[] t = (length == 0) ? EMPTY_ARRAY : new boolean[length];
/*  231 */     System.arraycopy(array, 0, t, 0, length);
/*  232 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] setLength(boolean[] array, int length) {
/*  250 */     if (length == array.length)
/*  251 */       return array; 
/*  252 */     if (length < array.length)
/*  253 */       return trim(array, length); 
/*  254 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] copy(boolean[] array, int offset, int length) {
/*  269 */     ensureOffsetLength(array, offset, length);
/*  270 */     boolean[] a = (length == 0) ? EMPTY_ARRAY : new boolean[length];
/*  271 */     System.arraycopy(array, offset, a, 0, length);
/*  272 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] copy(boolean[] array) {
/*  282 */     return (boolean[])array.clone();
/*      */   }
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
/*      */   public static void fill(boolean[] array, boolean value) {
/*  295 */     int i = array.length;
/*  296 */     while (i-- != 0) {
/*  297 */       array[i] = value;
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
/*      */   public static void fill(boolean[] array, int from, int to, boolean value) {
/*  315 */     ensureFromTo(array, from, to);
/*  316 */     if (from == 0) {
/*  317 */       while (to-- != 0)
/*  318 */         array[to] = value; 
/*      */     } else {
/*  320 */       for (int i = from; i < to; i++) {
/*  321 */         array[i] = value;
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
/*      */   public static boolean equals(boolean[] a1, boolean[] a2) {
/*  337 */     int i = a1.length;
/*  338 */     if (i != a2.length)
/*  339 */       return false; 
/*  340 */     while (i-- != 0) {
/*  341 */       if (a1[i] != a2[i])
/*  342 */         return false; 
/*  343 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureFromTo(boolean[] a, int from, int to) {
/*  365 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureOffsetLength(boolean[] a, int offset, int length) {
/*  386 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void ensureSameLength(boolean[] a, boolean[] b) {
/*  399 */     if (a.length != b.length) {
/*  400 */       throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
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
/*      */   public static void swap(boolean[] x, int a, int b) {
/*  417 */     boolean t = x[a];
/*  418 */     x[a] = x[b];
/*  419 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(boolean[] x, int a, int b, int n) {
/*  435 */     for (int i = 0; i < n; i++, a++, b++)
/*  436 */       swap(x, a, b); 
/*      */   }
/*      */   private static int med3(boolean[] x, int a, int b, int c, BooleanComparator comp) {
/*  439 */     int ab = comp.compare(x[a], x[b]);
/*  440 */     int ac = comp.compare(x[a], x[c]);
/*  441 */     int bc = comp.compare(x[b], x[c]);
/*  442 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void selectionSort(boolean[] a, int from, int to, BooleanComparator comp) {
/*  445 */     for (int i = from; i < to - 1; i++) {
/*  446 */       int m = i;
/*  447 */       for (int j = i + 1; j < to; j++) {
/*  448 */         if (comp.compare(a[j], a[m]) < 0)
/*  449 */           m = j; 
/*  450 */       }  if (m != i) {
/*  451 */         boolean u = a[i];
/*  452 */         a[i] = a[m];
/*  453 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static void insertionSort(boolean[] a, int from, int to, BooleanComparator comp) {
/*  458 */     for (int i = from; ++i < to; ) {
/*  459 */       boolean t = a[i];
/*  460 */       int j = i; boolean u;
/*  461 */       for (u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  462 */         a[j] = u;
/*  463 */         if (from == j - 1) {
/*  464 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  468 */       a[j] = t;
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
/*      */   public static void quickSort(boolean[] x, int from, int to, BooleanComparator comp) {
/*  496 */     int len = to - from;
/*      */     
/*  498 */     if (len < 16) {
/*  499 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  503 */     int m = from + len / 2;
/*  504 */     int l = from;
/*  505 */     int n = to - 1;
/*  506 */     if (len > 128) {
/*  507 */       int i = len / 8;
/*  508 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  509 */       m = med3(x, m - i, m, m + i, comp);
/*  510 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  512 */     m = med3(x, l, m, n, comp);
/*  513 */     boolean v = x[m];
/*      */     
/*  515 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  518 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  519 */         if (comparison == 0)
/*  520 */           swap(x, a++, b); 
/*  521 */         b++; continue;
/*      */       } 
/*  523 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  524 */         if (comparison == 0)
/*  525 */           swap(x, c, d--); 
/*  526 */         c--;
/*      */       } 
/*  528 */       if (b > c)
/*      */         break; 
/*  530 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  534 */     int s = Math.min(a - from, b - a);
/*  535 */     swap(x, from, b - s, s);
/*  536 */     s = Math.min(d - c, to - d - 1);
/*  537 */     swap(x, b, to - s, s);
/*      */     
/*  539 */     if ((s = b - a) > 1)
/*  540 */       quickSort(x, from, from + s, comp); 
/*  541 */     if ((s = d - c) > 1) {
/*  542 */       quickSort(x, to - s, to, comp);
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
/*      */   public static void quickSort(boolean[] x, BooleanComparator comp) {
/*  565 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final boolean[] x;
/*      */     private final BooleanComparator comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(boolean[] x, int from, int to, BooleanComparator comp) {
/*  574 */       this.from = from;
/*  575 */       this.to = to;
/*  576 */       this.x = x;
/*  577 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  581 */       boolean[] x = this.x;
/*  582 */       int len = this.to - this.from;
/*  583 */       if (len < 8192) {
/*  584 */         BooleanArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  588 */       int m = this.from + len / 2;
/*  589 */       int l = this.from;
/*  590 */       int n = this.to - 1;
/*  591 */       int s = len / 8;
/*  592 */       l = BooleanArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  593 */       m = BooleanArrays.med3(x, m - s, m, m + s, this.comp);
/*  594 */       n = BooleanArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  595 */       m = BooleanArrays.med3(x, l, m, n, this.comp);
/*  596 */       boolean v = x[m];
/*      */       
/*  598 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  601 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  602 */           if (comparison == 0)
/*  603 */             BooleanArrays.swap(x, a++, b); 
/*  604 */           b++; continue;
/*      */         } 
/*  606 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  607 */           if (comparison == 0)
/*  608 */             BooleanArrays.swap(x, c, d--); 
/*  609 */           c--;
/*      */         } 
/*  611 */         if (b > c)
/*      */           break; 
/*  613 */         BooleanArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  617 */       s = Math.min(a - this.from, b - a);
/*  618 */       BooleanArrays.swap(x, this.from, b - s, s);
/*  619 */       s = Math.min(d - c, this.to - d - 1);
/*  620 */       BooleanArrays.swap(x, b, this.to - s, s);
/*      */       
/*  622 */       s = b - a;
/*  623 */       int t = d - c;
/*  624 */       if (s > 1 && t > 1) {
/*  625 */         invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp));
/*      */       }
/*  627 */       else if (s > 1) {
/*  628 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) });
/*      */       } else {
/*  630 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) });
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
/*      */   public static void parallelQuickSort(boolean[] x, int from, int to, BooleanComparator comp) {
/*  657 */     if (to - from < 8192) {
/*  658 */       quickSort(x, from, to, comp);
/*      */     } else {
/*  660 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  661 */       pool.invoke(new ForkJoinQuickSortComp(x, from, to, comp));
/*  662 */       pool.shutdown();
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
/*      */   public static void parallelQuickSort(boolean[] x, BooleanComparator comp) {
/*  684 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static int med3(boolean[] x, int a, int b, int c) {
/*  688 */     int ab = Boolean.compare(x[a], x[b]);
/*  689 */     int ac = Boolean.compare(x[a], x[c]);
/*  690 */     int bc = Boolean.compare(x[b], x[c]);
/*  691 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void selectionSort(boolean[] a, int from, int to) {
/*  695 */     for (int i = from; i < to - 1; i++) {
/*  696 */       int m = i;
/*  697 */       for (int j = i + 1; j < to; j++) {
/*  698 */         if (!a[j] && a[m])
/*  699 */           m = j; 
/*  700 */       }  if (m != i) {
/*  701 */         boolean u = a[i];
/*  702 */         a[i] = a[m];
/*  703 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void insertionSort(boolean[] a, int from, int to) {
/*  709 */     for (int i = from; ++i < to; ) {
/*  710 */       boolean t = a[i];
/*  711 */       int j = i; boolean u;
/*  712 */       for (u = a[j - 1]; !t && u; u = a[--j - 1]) {
/*  713 */         a[j] = u;
/*  714 */         if (from == j - 1) {
/*  715 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  719 */       a[j] = t;
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
/*      */   public static void quickSort(boolean[] x, int from, int to) {
/*  745 */     int len = to - from;
/*      */     
/*  747 */     if (len < 16) {
/*  748 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  752 */     int m = from + len / 2;
/*  753 */     int l = from;
/*  754 */     int n = to - 1;
/*  755 */     if (len > 128) {
/*  756 */       int i = len / 8;
/*  757 */       l = med3(x, l, l + i, l + 2 * i);
/*  758 */       m = med3(x, m - i, m, m + i);
/*  759 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  761 */     m = med3(x, l, m, n);
/*  762 */     boolean v = x[m];
/*      */     
/*  764 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  767 */       if (b <= c && (comparison = Boolean.compare(x[b], v)) <= 0) {
/*  768 */         if (comparison == 0)
/*  769 */           swap(x, a++, b); 
/*  770 */         b++; continue;
/*      */       } 
/*  772 */       while (c >= b && (comparison = Boolean.compare(x[c], v)) >= 0) {
/*  773 */         if (comparison == 0)
/*  774 */           swap(x, c, d--); 
/*  775 */         c--;
/*      */       } 
/*  777 */       if (b > c)
/*      */         break; 
/*  779 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  783 */     int s = Math.min(a - from, b - a);
/*  784 */     swap(x, from, b - s, s);
/*  785 */     s = Math.min(d - c, to - d - 1);
/*  786 */     swap(x, b, to - s, s);
/*      */     
/*  788 */     if ((s = b - a) > 1)
/*  789 */       quickSort(x, from, from + s); 
/*  790 */     if ((s = d - c) > 1) {
/*  791 */       quickSort(x, to - s, to);
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
/*      */   public static void quickSort(boolean[] x) {
/*  811 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final boolean[] x;
/*      */     
/*      */     public ForkJoinQuickSort(boolean[] x, int from, int to) {
/*  819 */       this.from = from;
/*  820 */       this.to = to;
/*  821 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  826 */       boolean[] x = this.x;
/*  827 */       int len = this.to - this.from;
/*  828 */       if (len < 8192) {
/*  829 */         BooleanArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  833 */       int m = this.from + len / 2;
/*  834 */       int l = this.from;
/*  835 */       int n = this.to - 1;
/*  836 */       int s = len / 8;
/*  837 */       l = BooleanArrays.med3(x, l, l + s, l + 2 * s);
/*  838 */       m = BooleanArrays.med3(x, m - s, m, m + s);
/*  839 */       n = BooleanArrays.med3(x, n - 2 * s, n - s, n);
/*  840 */       m = BooleanArrays.med3(x, l, m, n);
/*  841 */       boolean v = x[m];
/*      */       
/*  843 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  846 */         if (b <= c && (comparison = Boolean.compare(x[b], v)) <= 0) {
/*  847 */           if (comparison == 0)
/*  848 */             BooleanArrays.swap(x, a++, b); 
/*  849 */           b++; continue;
/*      */         } 
/*  851 */         while (c >= b && (comparison = Boolean.compare(x[c], v)) >= 0) {
/*  852 */           if (comparison == 0)
/*  853 */             BooleanArrays.swap(x, c, d--); 
/*  854 */           c--;
/*      */         } 
/*  856 */         if (b > c)
/*      */           break; 
/*  858 */         BooleanArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  862 */       s = Math.min(a - this.from, b - a);
/*  863 */       BooleanArrays.swap(x, this.from, b - s, s);
/*  864 */       s = Math.min(d - c, this.to - d - 1);
/*  865 */       BooleanArrays.swap(x, b, this.to - s, s);
/*      */       
/*  867 */       s = b - a;
/*  868 */       int t = d - c;
/*  869 */       if (s > 1 && t > 1) {
/*  870 */         invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to));
/*  871 */       } else if (s > 1) {
/*  872 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) });
/*      */       } else {
/*  874 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) });
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
/*      */   public static void parallelQuickSort(boolean[] x, int from, int to) {
/*  898 */     if (to - from < 8192) {
/*  899 */       quickSort(x, from, to);
/*      */     } else {
/*  901 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  902 */       pool.invoke(new ForkJoinQuickSort(x, from, to));
/*  903 */       pool.shutdown();
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
/*      */   public static void parallelQuickSort(boolean[] x) {
/*  924 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static int med3Indirect(int[] perm, boolean[] x, int a, int b, int c) {
/*  928 */     boolean aa = x[perm[a]];
/*  929 */     boolean bb = x[perm[b]];
/*  930 */     boolean cc = x[perm[c]];
/*  931 */     int ab = Boolean.compare(aa, bb);
/*  932 */     int ac = Boolean.compare(aa, cc);
/*  933 */     int bc = Boolean.compare(bb, cc);
/*  934 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static void insertionSortIndirect(int[] perm, boolean[] a, int from, int to) {
/*  938 */     for (int i = from; ++i < to; ) {
/*  939 */       int t = perm[i];
/*  940 */       int j = i; int u;
/*  941 */       for (u = perm[j - 1]; !a[t] && a[u]; u = perm[--j - 1]) {
/*  942 */         perm[j] = u;
/*  943 */         if (from == j - 1) {
/*  944 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  948 */       perm[j] = t;
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
/*      */   public static void quickSortIndirect(int[] perm, boolean[] x, int from, int to) {
/*  981 */     int len = to - from;
/*      */     
/*  983 */     if (len < 16) {
/*  984 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  988 */     int m = from + len / 2;
/*  989 */     int l = from;
/*  990 */     int n = to - 1;
/*  991 */     if (len > 128) {
/*  992 */       int i = len / 8;
/*  993 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/*  994 */       m = med3Indirect(perm, x, m - i, m, m + i);
/*  995 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/*  997 */     m = med3Indirect(perm, x, l, m, n);
/*  998 */     boolean v = x[perm[m]];
/*      */     
/* 1000 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1003 */       if (b <= c && (comparison = Boolean.compare(x[perm[b]], v)) <= 0) {
/* 1004 */         if (comparison == 0)
/* 1005 */           IntArrays.swap(perm, a++, b); 
/* 1006 */         b++; continue;
/*      */       } 
/* 1008 */       while (c >= b && (comparison = Boolean.compare(x[perm[c]], v)) >= 0) {
/* 1009 */         if (comparison == 0)
/* 1010 */           IntArrays.swap(perm, c, d--); 
/* 1011 */         c--;
/*      */       } 
/* 1013 */       if (b > c)
/*      */         break; 
/* 1015 */       IntArrays.swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1019 */     int s = Math.min(a - from, b - a);
/* 1020 */     IntArrays.swap(perm, from, b - s, s);
/* 1021 */     s = Math.min(d - c, to - d - 1);
/* 1022 */     IntArrays.swap(perm, b, to - s, s);
/*      */     
/* 1024 */     if ((s = b - a) > 1)
/* 1025 */       quickSortIndirect(perm, x, from, from + s); 
/* 1026 */     if ((s = d - c) > 1) {
/* 1027 */       quickSortIndirect(perm, x, to - s, to);
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
/*      */   public static void quickSortIndirect(int[] perm, boolean[] x) {
/* 1054 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final boolean[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, boolean[] x, int from, int to) {
/* 1063 */       this.from = from;
/* 1064 */       this.to = to;
/* 1065 */       this.x = x;
/* 1066 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1071 */       boolean[] x = this.x;
/* 1072 */       int len = this.to - this.from;
/* 1073 */       if (len < 8192) {
/* 1074 */         BooleanArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1078 */       int m = this.from + len / 2;
/* 1079 */       int l = this.from;
/* 1080 */       int n = this.to - 1;
/* 1081 */       int s = len / 8;
/* 1082 */       l = BooleanArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1083 */       m = BooleanArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1084 */       n = BooleanArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1085 */       m = BooleanArrays.med3Indirect(this.perm, x, l, m, n);
/* 1086 */       boolean v = x[this.perm[m]];
/*      */       
/* 1088 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1091 */         if (b <= c && (comparison = Boolean.compare(x[this.perm[b]], v)) <= 0) {
/* 1092 */           if (comparison == 0)
/* 1093 */             IntArrays.swap(this.perm, a++, b); 
/* 1094 */           b++; continue;
/*      */         } 
/* 1096 */         while (c >= b && (comparison = Boolean.compare(x[this.perm[c]], v)) >= 0) {
/* 1097 */           if (comparison == 0)
/* 1098 */             IntArrays.swap(this.perm, c, d--); 
/* 1099 */           c--;
/*      */         } 
/* 1101 */         if (b > c)
/*      */           break; 
/* 1103 */         IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1107 */       s = Math.min(a - this.from, b - a);
/* 1108 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1109 */       s = Math.min(d - c, this.to - d - 1);
/* 1110 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1112 */       s = b - a;
/* 1113 */       int t = d - c;
/* 1114 */       if (s > 1 && t > 1) {
/* 1115 */         invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to));
/*      */       }
/* 1117 */       else if (s > 1) {
/* 1118 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) });
/*      */       } else {
/* 1120 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) });
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, boolean[] x, int from, int to) {
/* 1151 */     if (to - from < 8192) {
/* 1152 */       quickSortIndirect(perm, x, from, to);
/*      */     } else {
/* 1154 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1155 */       pool.invoke(new ForkJoinQuickSortIndirect(perm, x, from, to));
/* 1156 */       pool.shutdown();
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
/*      */   public static void parallelQuickSortIndirect(int[] perm, boolean[] x) {
/* 1184 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stabilize(int[] perm, boolean[] x, int from, int to) {
/* 1217 */     int curr = from;
/* 1218 */     for (int i = from + 1; i < to; i++) {
/* 1219 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1220 */         if (i - curr > 1)
/* 1221 */           IntArrays.parallelQuickSort(perm, curr, i); 
/* 1222 */         curr = i;
/*      */       } 
/*      */     } 
/* 1225 */     if (to - curr > 1) {
/* 1226 */       IntArrays.parallelQuickSort(perm, curr, to);
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
/*      */   public static void stabilize(int[] perm, boolean[] x) {
/* 1255 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */   
/*      */   private static int med3(boolean[] x, boolean[] y, int a, int b, int c) {
/* 1260 */     int t, ab = ((t = Boolean.compare(x[a], x[b])) == 0) ? Boolean.compare(y[a], y[b]) : t;
/* 1261 */     int ac = ((t = Boolean.compare(x[a], x[c])) == 0) ? Boolean.compare(y[a], y[c]) : t;
/* 1262 */     int bc = ((t = Boolean.compare(x[b], x[c])) == 0) ? Boolean.compare(y[b], y[c]) : t;
/* 1263 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static void swap(boolean[] x, boolean[] y, int a, int b) {
/* 1266 */     boolean t = x[a];
/* 1267 */     boolean u = y[a];
/* 1268 */     x[a] = x[b];
/* 1269 */     y[a] = y[b];
/* 1270 */     x[b] = t;
/* 1271 */     y[b] = u;
/*      */   }
/*      */   private static void swap(boolean[] x, boolean[] y, int a, int b, int n) {
/* 1274 */     for (int i = 0; i < n; i++, a++, b++)
/* 1275 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static void selectionSort(boolean[] a, boolean[] b, int from, int to) {
/* 1279 */     for (int i = from; i < to - 1; i++) {
/* 1280 */       int m = i;
/* 1281 */       for (int j = i + 1; j < to; j++) {
/* 1282 */         int u; if ((u = Boolean.compare(a[j], a[m])) < 0 || (u == 0 && !b[j] && b[m]))
/* 1283 */           m = j; 
/* 1284 */       }  if (m != i) {
/* 1285 */         boolean t = a[i];
/* 1286 */         a[i] = a[m];
/* 1287 */         a[m] = t;
/* 1288 */         t = b[i];
/* 1289 */         b[i] = b[m];
/* 1290 */         b[m] = t;
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
/*      */   public static void quickSort(boolean[] x, boolean[] y, int from, int to) {
/* 1321 */     int len = to - from;
/* 1322 */     if (len < 16) {
/* 1323 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1327 */     int m = from + len / 2;
/* 1328 */     int l = from;
/* 1329 */     int n = to - 1;
/* 1330 */     if (len > 128) {
/* 1331 */       int i = len / 8;
/* 1332 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1333 */       m = med3(x, y, m - i, m, m + i);
/* 1334 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1336 */     m = med3(x, y, l, m, n);
/* 1337 */     boolean v = x[m], w = y[m];
/*      */     
/* 1339 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1342 */       if (b <= c) {
/*      */         int comparison; int t;
/* 1344 */         if ((comparison = ((t = Boolean.compare(x[b], v)) == 0) ? Boolean.compare(y[b], w) : t) <= 0) {
/* 1345 */           if (comparison == 0)
/* 1346 */             swap(x, y, a++, b); 
/* 1347 */           b++; continue;
/*      */         } 
/* 1349 */       }  while (c >= b) {
/*      */         int comparison; int t;
/* 1351 */         if ((comparison = ((t = Boolean.compare(x[c], v)) == 0) ? Boolean.compare(y[c], w) : t) >= 0) {
/* 1352 */           if (comparison == 0)
/* 1353 */             swap(x, y, c, d--); 
/* 1354 */           c--;
/*      */         } 
/* 1356 */       }  if (b > c)
/*      */         break; 
/* 1358 */       swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1362 */     int s = Math.min(a - from, b - a);
/* 1363 */     swap(x, y, from, b - s, s);
/* 1364 */     s = Math.min(d - c, to - d - 1);
/* 1365 */     swap(x, y, b, to - s, s);
/*      */     
/* 1367 */     if ((s = b - a) > 1)
/* 1368 */       quickSort(x, y, from, from + s); 
/* 1369 */     if ((s = d - c) > 1) {
/* 1370 */       quickSort(x, y, to - s, to);
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
/*      */   public static void quickSort(boolean[] x, boolean[] y) {
/* 1394 */     ensureSameLength(x, y);
/* 1395 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2 extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     
/*      */     public ForkJoinQuickSort2(boolean[] x, boolean[] y, int from, int to) {
/* 1403 */       this.from = from;
/* 1404 */       this.to = to;
/* 1405 */       this.x = x;
/* 1406 */       this.y = y;
/*      */     }
/*      */     private final int to; private final boolean[] x; private final boolean[] y;
/*      */     
/*      */     protected void compute() {
/* 1411 */       boolean[] x = this.x;
/* 1412 */       boolean[] y = this.y;
/* 1413 */       int len = this.to - this.from;
/* 1414 */       if (len < 8192) {
/* 1415 */         BooleanArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1419 */       int m = this.from + len / 2;
/* 1420 */       int l = this.from;
/* 1421 */       int n = this.to - 1;
/* 1422 */       int s = len / 8;
/* 1423 */       l = BooleanArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1424 */       m = BooleanArrays.med3(x, y, m - s, m, m + s);
/* 1425 */       n = BooleanArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1426 */       m = BooleanArrays.med3(x, y, l, m, n);
/* 1427 */       boolean v = x[m], w = y[m];
/*      */       
/* 1429 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1432 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1434 */           if ((comparison = ((i = Boolean.compare(x[b], v)) == 0) ? Boolean.compare(y[b], w) : i) <= 0) {
/* 1435 */             if (comparison == 0)
/* 1436 */               BooleanArrays.swap(x, y, a++, b); 
/* 1437 */             b++; continue;
/*      */           } 
/* 1439 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1441 */           if ((comparison = ((i = Boolean.compare(x[c], v)) == 0) ? Boolean.compare(y[c], w) : i) >= 0) {
/* 1442 */             if (comparison == 0)
/* 1443 */               BooleanArrays.swap(x, y, c, d--); 
/* 1444 */             c--;
/*      */           } 
/* 1446 */         }  if (b > c)
/*      */           break; 
/* 1448 */         BooleanArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1452 */       s = Math.min(a - this.from, b - a);
/* 1453 */       BooleanArrays.swap(x, y, this.from, b - s, s);
/* 1454 */       s = Math.min(d - c, this.to - d - 1);
/* 1455 */       BooleanArrays.swap(x, y, b, this.to - s, s);
/* 1456 */       s = b - a;
/* 1457 */       int t = d - c;
/*      */       
/* 1459 */       if (s > 1 && t > 1) {
/* 1460 */         invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to));
/* 1461 */       } else if (s > 1) {
/* 1462 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) });
/*      */       } else {
/* 1464 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) });
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
/*      */   public static void parallelQuickSort(boolean[] x, boolean[] y, int from, int to) {
/* 1497 */     if (to - from < 8192)
/* 1498 */       quickSort(x, y, from, to); 
/* 1499 */     ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1500 */     pool.invoke(new ForkJoinQuickSort2(x, y, from, to));
/* 1501 */     pool.shutdown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parallelQuickSort(boolean[] x, boolean[] y) {
/* 1529 */     ensureSameLength(x, y);
/* 1530 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a, int from, int to) {
/* 1549 */     quickSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a) {
/* 1562 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a, int from, int to, BooleanComparator comp) {
/* 1581 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unstableSort(boolean[] a, BooleanComparator comp) {
/* 1595 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a, int from, int to, boolean[] supp) {
/* 1619 */     int len = to - from;
/*      */     
/* 1621 */     if (len < 16) {
/* 1622 */       insertionSort(a, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1626 */     int mid = from + to >>> 1;
/* 1627 */     mergeSort(supp, from, mid, a);
/* 1628 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1631 */     if (!supp[mid - 1] || supp[mid]) {
/* 1632 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1636 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1637 */       if (q >= to || (p < mid && (!supp[p] || supp[q]))) {
/* 1638 */         a[i] = supp[p++];
/*      */       } else {
/* 1640 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(boolean[] a, int from, int to) {
/* 1660 */     mergeSort(a, from, to, (boolean[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a) {
/* 1674 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a, int from, int to, BooleanComparator comp, boolean[] supp) {
/* 1700 */     int len = to - from;
/*      */     
/* 1702 */     if (len < 16) {
/* 1703 */       insertionSort(a, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/* 1707 */     int mid = from + to >>> 1;
/* 1708 */     mergeSort(supp, from, mid, comp, a);
/* 1709 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1712 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1713 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1717 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1718 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
/* 1719 */         a[i] = supp[p++];
/*      */       } else {
/* 1721 */         a[i] = supp[q++];
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
/*      */   public static void mergeSort(boolean[] a, int from, int to, BooleanComparator comp) {
/* 1743 */     mergeSort(a, from, to, comp, (boolean[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeSort(boolean[] a, BooleanComparator comp) {
/* 1760 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a, int from, int to) {
/* 1785 */     unstableSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a) {
/* 1803 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a, int from, int to, BooleanComparator comp) {
/* 1827 */     mergeSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stableSort(boolean[] a, BooleanComparator comp) {
/* 1847 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] shuffle(boolean[] a, int from, int to, Random random) {
/* 1864 */     for (int i = to - from; i-- != 0; ) {
/* 1865 */       int p = random.nextInt(i + 1);
/* 1866 */       boolean t = a[from + i];
/* 1867 */       a[from + i] = a[from + p];
/* 1868 */       a[from + p] = t;
/*      */     } 
/* 1870 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] shuffle(boolean[] a, Random random) {
/* 1883 */     for (int i = a.length; i-- != 0; ) {
/* 1884 */       int p = random.nextInt(i + 1);
/* 1885 */       boolean t = a[i];
/* 1886 */       a[i] = a[p];
/* 1887 */       a[p] = t;
/*      */     } 
/* 1889 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] reverse(boolean[] a) {
/* 1899 */     int length = a.length;
/* 1900 */     for (int i = length / 2; i-- != 0; ) {
/* 1901 */       boolean t = a[length - i - 1];
/* 1902 */       a[length - i - 1] = a[i];
/* 1903 */       a[i] = t;
/*      */     } 
/* 1905 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] reverse(boolean[] a, int from, int to) {
/* 1919 */     int length = to - from;
/* 1920 */     for (int i = length / 2; i-- != 0; ) {
/* 1921 */       boolean t = a[from + length - i - 1];
/* 1922 */       a[from + length - i - 1] = a[from + i];
/* 1923 */       a[from + i] = t;
/*      */     } 
/* 1925 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy implements Hash.Strategy<boolean[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(boolean[] o) {
/* 1932 */       return Arrays.hashCode(o);
/*      */     }
/*      */     
/*      */     public boolean equals(boolean[] a, boolean[] b) {
/* 1936 */       return Arrays.equals(a, b);
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
/* 1947 */   public static final Hash.Strategy<boolean[]> HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\booleans\BooleanArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */