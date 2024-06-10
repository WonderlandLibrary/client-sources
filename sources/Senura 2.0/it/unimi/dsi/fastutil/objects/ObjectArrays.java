/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Arrays;
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.Objects;
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
/*      */ public final class ObjectArrays
/*      */ {
/*   85 */   public static final Object[] EMPTY_ARRAY = new Object[0];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   public static final Object[] DEFAULT_EMPTY_ARRAY = new Object[0];
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
/*      */   
/*      */   private static <K> K[] newArray(K[] prototype, int length) {
/*  111 */     Class<?> klass = prototype.getClass();
/*  112 */     if (klass == Object[].class)
/*  113 */       return (length == 0) ? (K[])EMPTY_ARRAY : (K[])new Object[length]; 
/*  114 */     return (K[])Array.newInstance(klass.getComponentType(), length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] forceCapacity(K[] array, int length, int preserve) {
/*  131 */     K[] t = newArray(array, length);
/*  132 */     System.arraycopy(array, 0, t, 0, preserve);
/*  133 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] ensureCapacity(K[] array, int length) {
/*  151 */     return ensureCapacity(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] ensureCapacity(K[] array, int length, int preserve) {
/*  169 */     return (length > array.length) ? forceCapacity(array, length, preserve) : array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] grow(K[] array, int length) {
/*  190 */     return grow(array, length, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] grow(K[] array, int length, int preserve) {
/*  214 */     if (length > array.length) {
/*      */       
/*  216 */       int newLength = (int)Math.max(Math.min(array.length + (array.length >> 1), 2147483639L), length);
/*  217 */       K[] t = newArray(array, newLength);
/*  218 */       System.arraycopy(array, 0, t, 0, preserve);
/*  219 */       return t;
/*      */     } 
/*  221 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] trim(K[] array, int length) {
/*  236 */     if (length >= array.length)
/*  237 */       return array; 
/*  238 */     K[] t = newArray(array, length);
/*  239 */     System.arraycopy(array, 0, t, 0, length);
/*  240 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] setLength(K[] array, int length) {
/*  258 */     if (length == array.length)
/*  259 */       return array; 
/*  260 */     if (length < array.length)
/*  261 */       return trim(array, length); 
/*  262 */     return ensureCapacity(array, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] copy(K[] array, int offset, int length) {
/*  277 */     ensureOffsetLength(array, offset, length);
/*  278 */     K[] a = newArray(array, length);
/*  279 */     System.arraycopy(array, offset, a, 0, length);
/*  280 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] copy(K[] array) {
/*  290 */     return (K[])array.clone();
/*      */   }
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
/*      */   public static <K> void fill(K[] array, K value) {
/*  303 */     int i = array.length;
/*  304 */     while (i-- != 0) {
/*  305 */       array[i] = value;
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
/*      */   public static <K> void fill(K[] array, int from, int to, K value) {
/*  323 */     ensureFromTo(array, from, to);
/*  324 */     if (from == 0) {
/*  325 */       while (to-- != 0)
/*  326 */         array[to] = value; 
/*      */     } else {
/*  328 */       for (int i = from; i < to; i++) {
/*  329 */         array[i] = value;
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
/*      */   public static <K> boolean equals(K[] a1, K[] a2) {
/*  345 */     int i = a1.length;
/*  346 */     if (i != a2.length)
/*  347 */       return false; 
/*  348 */     while (i-- != 0) {
/*  349 */       if (!Objects.equals(a1[i], a2[i]))
/*  350 */         return false; 
/*  351 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureFromTo(K[] a, int from, int to) {
/*  373 */     Arrays.ensureFromTo(a.length, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureOffsetLength(K[] a, int offset, int length) {
/*  394 */     Arrays.ensureOffsetLength(a.length, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void ensureSameLength(K[] a, K[] b) {
/*  407 */     if (a.length != b.length) {
/*  408 */       throw new IllegalArgumentException("Array size mismatch: " + a.length + " != " + b.length);
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
/*      */   public static <K> void swap(K[] x, int a, int b) {
/*  425 */     K t = x[a];
/*  426 */     x[a] = x[b];
/*  427 */     x[b] = t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void swap(K[] x, int a, int b, int n) {
/*  443 */     for (int i = 0; i < n; i++, a++, b++)
/*  444 */       swap(x, a, b); 
/*      */   }
/*      */   private static <K> int med3(K[] x, int a, int b, int c, Comparator<K> comp) {
/*  447 */     int ab = comp.compare(x[a], x[b]);
/*  448 */     int ac = comp.compare(x[a], x[c]);
/*  449 */     int bc = comp.compare(x[b], x[c]);
/*  450 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static <K> void selectionSort(K[] a, int from, int to, Comparator<K> comp) {
/*  453 */     for (int i = from; i < to - 1; i++) {
/*  454 */       int m = i;
/*  455 */       for (int j = i + 1; j < to; j++) {
/*  456 */         if (comp.compare(a[j], a[m]) < 0)
/*  457 */           m = j; 
/*  458 */       }  if (m != i) {
/*  459 */         K u = a[i];
/*  460 */         a[i] = a[m];
/*  461 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static <K> void insertionSort(K[] a, int from, int to, Comparator<K> comp) {
/*  466 */     for (int i = from; ++i < to; ) {
/*  467 */       K t = a[i];
/*  468 */       int j = i;
/*  469 */       for (K u = a[j - 1]; comp.compare(t, u) < 0; u = a[--j - 1]) {
/*  470 */         a[j] = u;
/*  471 */         if (from == j - 1) {
/*  472 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  476 */       a[j] = t;
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
/*      */   public static <K> void quickSort(K[] x, int from, int to, Comparator<K> comp) {
/*  504 */     int len = to - from;
/*      */     
/*  506 */     if (len < 16) {
/*  507 */       selectionSort(x, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/*  511 */     int m = from + len / 2;
/*  512 */     int l = from;
/*  513 */     int n = to - 1;
/*  514 */     if (len > 128) {
/*  515 */       int i = len / 8;
/*  516 */       l = med3(x, l, l + i, l + 2 * i, comp);
/*  517 */       m = med3(x, m - i, m, m + i, comp);
/*  518 */       n = med3(x, n - 2 * i, n - i, n, comp);
/*      */     } 
/*  520 */     m = med3(x, l, m, n, comp);
/*  521 */     K v = x[m];
/*      */     
/*  523 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  526 */       if (b <= c && (comparison = comp.compare(x[b], v)) <= 0) {
/*  527 */         if (comparison == 0)
/*  528 */           swap(x, a++, b); 
/*  529 */         b++; continue;
/*      */       } 
/*  531 */       while (c >= b && (comparison = comp.compare(x[c], v)) >= 0) {
/*  532 */         if (comparison == 0)
/*  533 */           swap(x, c, d--); 
/*  534 */         c--;
/*      */       } 
/*  536 */       if (b > c)
/*      */         break; 
/*  538 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  542 */     int s = Math.min(a - from, b - a);
/*  543 */     swap(x, from, b - s, s);
/*  544 */     s = Math.min(d - c, to - d - 1);
/*  545 */     swap(x, b, to - s, s);
/*      */     
/*  547 */     if ((s = b - a) > 1)
/*  548 */       quickSort(x, from, from + s, comp); 
/*  549 */     if ((s = d - c) > 1) {
/*  550 */       quickSort(x, to - s, to, comp);
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
/*      */   public static <K> void quickSort(K[] x, Comparator<K> comp) {
/*  573 */     quickSort(x, 0, x.length, comp);
/*      */   }
/*      */   protected static class ForkJoinQuickSortComp<K> extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final K[] x;
/*      */     private final Comparator<K> comp;
/*      */     
/*      */     public ForkJoinQuickSortComp(K[] x, int from, int to, Comparator<K> comp) {
/*  582 */       this.from = from;
/*  583 */       this.to = to;
/*  584 */       this.x = x;
/*  585 */       this.comp = comp;
/*      */     }
/*      */     
/*      */     protected void compute() {
/*  589 */       K[] x = this.x;
/*  590 */       int len = this.to - this.from;
/*  591 */       if (len < 8192) {
/*  592 */         ObjectArrays.quickSort(x, this.from, this.to, this.comp);
/*      */         
/*      */         return;
/*      */       } 
/*  596 */       int m = this.from + len / 2;
/*  597 */       int l = this.from;
/*  598 */       int n = this.to - 1;
/*  599 */       int s = len / 8;
/*  600 */       l = ObjectArrays.med3(x, l, l + s, l + 2 * s, this.comp);
/*  601 */       m = ObjectArrays.med3(x, m - s, m, m + s, this.comp);
/*  602 */       n = ObjectArrays.med3(x, n - 2 * s, n - s, n, this.comp);
/*  603 */       m = ObjectArrays.med3(x, l, m, n, this.comp);
/*  604 */       K v = x[m];
/*      */       
/*  606 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  609 */         if (b <= c && (comparison = this.comp.compare(x[b], v)) <= 0) {
/*  610 */           if (comparison == 0)
/*  611 */             ObjectArrays.swap(x, a++, b); 
/*  612 */           b++; continue;
/*      */         } 
/*  614 */         while (c >= b && (comparison = this.comp.compare(x[c], v)) >= 0) {
/*  615 */           if (comparison == 0)
/*  616 */             ObjectArrays.swap(x, c, d--); 
/*  617 */           c--;
/*      */         } 
/*  619 */         if (b > c)
/*      */           break; 
/*  621 */         ObjectArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  625 */       s = Math.min(a - this.from, b - a);
/*  626 */       ObjectArrays.swap(x, this.from, b - s, s);
/*  627 */       s = Math.min(d - c, this.to - d - 1);
/*  628 */       ObjectArrays.swap(x, b, this.to - s, s);
/*      */       
/*  630 */       s = b - a;
/*  631 */       int t = d - c;
/*  632 */       if (s > 1 && t > 1) {
/*  633 */         invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp), new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp));
/*      */       }
/*  635 */       else if (s > 1) {
/*  636 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.from, this.from + s, this.comp) });
/*      */       } else {
/*  638 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortComp(x, this.to - t, this.to, this.comp) });
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
/*      */   public static <K> void parallelQuickSort(K[] x, int from, int to, Comparator<K> comp) {
/*  664 */     if (to - from < 8192) {
/*  665 */       quickSort(x, from, to, comp);
/*      */     } else {
/*  667 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  668 */       pool.invoke(new ForkJoinQuickSortComp<>(x, from, to, comp));
/*  669 */       pool.shutdown();
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
/*      */   public static <K> void parallelQuickSort(K[] x, Comparator<K> comp) {
/*  691 */     parallelQuickSort(x, 0, x.length, comp);
/*      */   }
/*      */   
/*      */   private static <K> int med3(K[] x, int a, int b, int c) {
/*  695 */     int ab = ((Comparable<K>)x[a]).compareTo(x[b]);
/*  696 */     int ac = ((Comparable<K>)x[a]).compareTo(x[c]);
/*  697 */     int bc = ((Comparable<K>)x[b]).compareTo(x[c]);
/*  698 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static <K> void selectionSort(K[] a, int from, int to) {
/*  702 */     for (int i = from; i < to - 1; i++) {
/*  703 */       int m = i;
/*  704 */       for (int j = i + 1; j < to; j++) {
/*  705 */         if (((Comparable<K>)a[j]).compareTo(a[m]) < 0)
/*  706 */           m = j; 
/*  707 */       }  if (m != i) {
/*  708 */         K u = a[i];
/*  709 */         a[i] = a[m];
/*  710 */         a[m] = u;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static <K> void insertionSort(K[] a, int from, int to) {
/*  716 */     for (int i = from; ++i < to; ) {
/*  717 */       K t = a[i];
/*  718 */       int j = i;
/*  719 */       for (K u = a[j - 1]; ((Comparable<K>)t).compareTo(u) < 0; u = a[--j - 1]) {
/*  720 */         a[j] = u;
/*  721 */         if (from == j - 1) {
/*  722 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  726 */       a[j] = t;
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
/*      */   public static <K> void quickSort(K[] x, int from, int to) {
/*  752 */     int len = to - from;
/*      */     
/*  754 */     if (len < 16) {
/*  755 */       selectionSort(x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  759 */     int m = from + len / 2;
/*  760 */     int l = from;
/*  761 */     int n = to - 1;
/*  762 */     if (len > 128) {
/*  763 */       int i = len / 8;
/*  764 */       l = med3(x, l, l + i, l + 2 * i);
/*  765 */       m = med3(x, m - i, m, m + i);
/*  766 */       n = med3(x, n - 2 * i, n - i, n);
/*      */     } 
/*  768 */     m = med3(x, l, m, n);
/*  769 */     K v = x[m];
/*      */     
/*  771 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/*  774 */       if (b <= c && (comparison = ((Comparable<K>)x[b]).compareTo(v)) <= 0) {
/*  775 */         if (comparison == 0)
/*  776 */           swap(x, a++, b); 
/*  777 */         b++; continue;
/*      */       } 
/*  779 */       while (c >= b && (comparison = ((Comparable<K>)x[c]).compareTo(v)) >= 0) {
/*  780 */         if (comparison == 0)
/*  781 */           swap(x, c, d--); 
/*  782 */         c--;
/*      */       } 
/*  784 */       if (b > c)
/*      */         break; 
/*  786 */       swap(x, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/*  790 */     int s = Math.min(a - from, b - a);
/*  791 */     swap(x, from, b - s, s);
/*  792 */     s = Math.min(d - c, to - d - 1);
/*  793 */     swap(x, b, to - s, s);
/*      */     
/*  795 */     if ((s = b - a) > 1)
/*  796 */       quickSort(x, from, from + s); 
/*  797 */     if ((s = d - c) > 1) {
/*  798 */       quickSort(x, to - s, to);
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
/*      */   public static <K> void quickSort(K[] x) {
/*  818 */     quickSort(x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSort<K> extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final K[] x;
/*      */     
/*      */     public ForkJoinQuickSort(K[] x, int from, int to) {
/*  826 */       this.from = from;
/*  827 */       this.to = to;
/*  828 */       this.x = x;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/*  833 */       K[] x = this.x;
/*  834 */       int len = this.to - this.from;
/*  835 */       if (len < 8192) {
/*  836 */         ObjectArrays.quickSort(x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/*  840 */       int m = this.from + len / 2;
/*  841 */       int l = this.from;
/*  842 */       int n = this.to - 1;
/*  843 */       int s = len / 8;
/*  844 */       l = ObjectArrays.med3(x, l, l + s, l + 2 * s);
/*  845 */       m = ObjectArrays.med3(x, m - s, m, m + s);
/*  846 */       n = ObjectArrays.med3(x, n - 2 * s, n - s, n);
/*  847 */       m = ObjectArrays.med3(x, l, m, n);
/*  848 */       K v = x[m];
/*      */       
/*  850 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/*  853 */         if (b <= c && (comparison = ((Comparable<K>)x[b]).compareTo(v)) <= 0) {
/*  854 */           if (comparison == 0)
/*  855 */             ObjectArrays.swap(x, a++, b); 
/*  856 */           b++; continue;
/*      */         } 
/*  858 */         while (c >= b && (comparison = ((Comparable<K>)x[c]).compareTo(v)) >= 0) {
/*  859 */           if (comparison == 0)
/*  860 */             ObjectArrays.swap(x, c, d--); 
/*  861 */           c--;
/*      */         } 
/*  863 */         if (b > c)
/*      */           break; 
/*  865 */         ObjectArrays.swap(x, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/*  869 */       s = Math.min(a - this.from, b - a);
/*  870 */       ObjectArrays.swap(x, this.from, b - s, s);
/*  871 */       s = Math.min(d - c, this.to - d - 1);
/*  872 */       ObjectArrays.swap(x, b, this.to - s, s);
/*      */       
/*  874 */       s = b - a;
/*  875 */       int t = d - c;
/*  876 */       if (s > 1 && t > 1) {
/*  877 */         invokeAll(new ForkJoinQuickSort(x, this.from, this.from + s), new ForkJoinQuickSort(x, this.to - t, this.to));
/*  878 */       } else if (s > 1) {
/*  879 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.from, this.from + s) });
/*      */       } else {
/*  881 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort(x, this.to - t, this.to) });
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
/*      */   public static <K> void parallelQuickSort(K[] x, int from, int to) {
/*  905 */     if (to - from < 8192) {
/*  906 */       quickSort(x, from, to);
/*      */     } else {
/*  908 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/*  909 */       pool.invoke(new ForkJoinQuickSort<>(x, from, to));
/*  910 */       pool.shutdown();
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
/*      */   public static <K> void parallelQuickSort(K[] x) {
/*  931 */     parallelQuickSort(x, 0, x.length);
/*      */   }
/*      */   
/*      */   private static <K> int med3Indirect(int[] perm, K[] x, int a, int b, int c) {
/*  935 */     K aa = x[perm[a]];
/*  936 */     K bb = x[perm[b]];
/*  937 */     K cc = x[perm[c]];
/*  938 */     int ab = ((Comparable<K>)aa).compareTo(bb);
/*  939 */     int ac = ((Comparable<K>)aa).compareTo(cc);
/*  940 */     int bc = ((Comparable<K>)bb).compareTo(cc);
/*  941 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   
/*      */   private static <K> void insertionSortIndirect(int[] perm, K[] a, int from, int to) {
/*  945 */     for (int i = from; ++i < to; ) {
/*  946 */       int t = perm[i];
/*  947 */       int j = i; int u;
/*  948 */       for (u = perm[j - 1]; ((Comparable<K>)a[t]).compareTo(a[u]) < 0; u = perm[--j - 1]) {
/*  949 */         perm[j] = u;
/*  950 */         if (from == j - 1) {
/*  951 */           j--;
/*      */           break;
/*      */         } 
/*      */       } 
/*  955 */       perm[j] = t;
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
/*      */   public static <K> void quickSortIndirect(int[] perm, K[] x, int from, int to) {
/*  988 */     int len = to - from;
/*      */     
/*  990 */     if (len < 16) {
/*  991 */       insertionSortIndirect(perm, x, from, to);
/*      */       
/*      */       return;
/*      */     } 
/*  995 */     int m = from + len / 2;
/*  996 */     int l = from;
/*  997 */     int n = to - 1;
/*  998 */     if (len > 128) {
/*  999 */       int i = len / 8;
/* 1000 */       l = med3Indirect(perm, x, l, l + i, l + 2 * i);
/* 1001 */       m = med3Indirect(perm, x, m - i, m, m + i);
/* 1002 */       n = med3Indirect(perm, x, n - 2 * i, n - i, n);
/*      */     } 
/* 1004 */     m = med3Indirect(perm, x, l, m, n);
/* 1005 */     K v = x[perm[m]];
/*      */     
/* 1007 */     int a = from, b = a, c = to - 1, d = c;
/*      */     while (true) {
/*      */       int comparison;
/* 1010 */       if (b <= c && (comparison = ((Comparable<K>)x[perm[b]]).compareTo(v)) <= 0) {
/* 1011 */         if (comparison == 0)
/* 1012 */           IntArrays.swap(perm, a++, b); 
/* 1013 */         b++; continue;
/*      */       } 
/* 1015 */       while (c >= b && (comparison = ((Comparable<K>)x[perm[c]]).compareTo(v)) >= 0) {
/* 1016 */         if (comparison == 0)
/* 1017 */           IntArrays.swap(perm, c, d--); 
/* 1018 */         c--;
/*      */       } 
/* 1020 */       if (b > c)
/*      */         break; 
/* 1022 */       IntArrays.swap(perm, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1026 */     int s = Math.min(a - from, b - a);
/* 1027 */     IntArrays.swap(perm, from, b - s, s);
/* 1028 */     s = Math.min(d - c, to - d - 1);
/* 1029 */     IntArrays.swap(perm, b, to - s, s);
/*      */     
/* 1031 */     if ((s = b - a) > 1)
/* 1032 */       quickSortIndirect(perm, x, from, from + s); 
/* 1033 */     if ((s = d - c) > 1) {
/* 1034 */       quickSortIndirect(perm, x, to - s, to);
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
/*      */   public static <K> void quickSortIndirect(int[] perm, K[] x) {
/* 1061 */     quickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */   protected static class ForkJoinQuickSortIndirect<K> extends RecursiveAction { private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     private final int to;
/*      */     private final int[] perm;
/*      */     private final K[] x;
/*      */     
/*      */     public ForkJoinQuickSortIndirect(int[] perm, K[] x, int from, int to) {
/* 1070 */       this.from = from;
/* 1071 */       this.to = to;
/* 1072 */       this.x = x;
/* 1073 */       this.perm = perm;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void compute() {
/* 1078 */       K[] x = this.x;
/* 1079 */       int len = this.to - this.from;
/* 1080 */       if (len < 8192) {
/* 1081 */         ObjectArrays.quickSortIndirect(this.perm, x, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1085 */       int m = this.from + len / 2;
/* 1086 */       int l = this.from;
/* 1087 */       int n = this.to - 1;
/* 1088 */       int s = len / 8;
/* 1089 */       l = ObjectArrays.med3Indirect(this.perm, x, l, l + s, l + 2 * s);
/* 1090 */       m = ObjectArrays.med3Indirect(this.perm, x, m - s, m, m + s);
/* 1091 */       n = ObjectArrays.med3Indirect(this.perm, x, n - 2 * s, n - s, n);
/* 1092 */       m = ObjectArrays.med3Indirect(this.perm, x, l, m, n);
/* 1093 */       K v = x[this.perm[m]];
/*      */       
/* 1095 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       while (true) {
/*      */         int comparison;
/* 1098 */         if (b <= c && (comparison = ((Comparable<K>)x[this.perm[b]]).compareTo(v)) <= 0) {
/* 1099 */           if (comparison == 0)
/* 1100 */             IntArrays.swap(this.perm, a++, b); 
/* 1101 */           b++; continue;
/*      */         } 
/* 1103 */         while (c >= b && (comparison = ((Comparable<K>)x[this.perm[c]]).compareTo(v)) >= 0) {
/* 1104 */           if (comparison == 0)
/* 1105 */             IntArrays.swap(this.perm, c, d--); 
/* 1106 */           c--;
/*      */         } 
/* 1108 */         if (b > c)
/*      */           break; 
/* 1110 */         IntArrays.swap(this.perm, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1114 */       s = Math.min(a - this.from, b - a);
/* 1115 */       IntArrays.swap(this.perm, this.from, b - s, s);
/* 1116 */       s = Math.min(d - c, this.to - d - 1);
/* 1117 */       IntArrays.swap(this.perm, b, this.to - s, s);
/*      */       
/* 1119 */       s = b - a;
/* 1120 */       int t = d - c;
/* 1121 */       if (s > 1 && t > 1) {
/* 1122 */         invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s), new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to));
/*      */       }
/* 1124 */       else if (s > 1) {
/* 1125 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + s) });
/*      */       } else {
/* 1127 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSortIndirect(this.perm, x, this.to - t, this.to) });
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
/*      */   public static <K> void parallelQuickSortIndirect(int[] perm, K[] x, int from, int to) {
/* 1158 */     if (to - from < 8192) {
/* 1159 */       quickSortIndirect(perm, x, from, to);
/*      */     } else {
/* 1161 */       ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1162 */       pool.invoke(new ForkJoinQuickSortIndirect<>(perm, x, from, to));
/* 1163 */       pool.shutdown();
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
/*      */   public static <K> void parallelQuickSortIndirect(int[] perm, K[] x) {
/* 1191 */     parallelQuickSortIndirect(perm, x, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stabilize(int[] perm, K[] x, int from, int to) {
/* 1224 */     int curr = from;
/* 1225 */     for (int i = from + 1; i < to; i++) {
/* 1226 */       if (x[perm[i]] != x[perm[curr]]) {
/* 1227 */         if (i - curr > 1)
/* 1228 */           IntArrays.parallelQuickSort(perm, curr, i); 
/* 1229 */         curr = i;
/*      */       } 
/*      */     } 
/* 1232 */     if (to - curr > 1) {
/* 1233 */       IntArrays.parallelQuickSort(perm, curr, to);
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
/*      */   public static <K> void stabilize(int[] perm, K[] x) {
/* 1262 */     stabilize(perm, x, 0, perm.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <K> int med3(K[] x, K[] y, int a, int b, int c) {
/* 1269 */     int t, ab = ((t = ((Comparable<K>)x[a]).compareTo(x[b])) == 0) ? ((Comparable<K>)y[a]).compareTo(y[b]) : t;
/*      */ 
/*      */     
/* 1272 */     int ac = ((t = ((Comparable<K>)x[a]).compareTo(x[c])) == 0) ? ((Comparable<K>)y[a]).compareTo(y[c]) : t;
/*      */ 
/*      */     
/* 1275 */     int bc = ((t = ((Comparable<K>)x[b]).compareTo(x[c])) == 0) ? ((Comparable<K>)y[b]).compareTo(y[c]) : t;
/* 1276 */     return (ab < 0) ? ((bc < 0) ? b : ((ac < 0) ? c : a)) : ((bc > 0) ? b : ((ac > 0) ? c : a));
/*      */   }
/*      */   private static <K> void swap(K[] x, K[] y, int a, int b) {
/* 1279 */     K t = x[a];
/* 1280 */     K u = y[a];
/* 1281 */     x[a] = x[b];
/* 1282 */     y[a] = y[b];
/* 1283 */     x[b] = t;
/* 1284 */     y[b] = u;
/*      */   }
/*      */   private static <K> void swap(K[] x, K[] y, int a, int b, int n) {
/* 1287 */     for (int i = 0; i < n; i++, a++, b++)
/* 1288 */       swap(x, y, a, b); 
/*      */   }
/*      */   
/*      */   private static <K> void selectionSort(K[] a, K[] b, int from, int to) {
/* 1292 */     for (int i = from; i < to - 1; i++) {
/* 1293 */       int m = i;
/* 1294 */       for (int j = i + 1; j < to; j++) {
/* 1295 */         int u; if ((u = ((Comparable<K>)a[j]).compareTo(a[m])) < 0 || (u == 0 && ((Comparable<K>)b[j])
/* 1296 */           .compareTo(b[m]) < 0))
/* 1297 */           m = j; 
/* 1298 */       }  if (m != i) {
/* 1299 */         K t = a[i];
/* 1300 */         a[i] = a[m];
/* 1301 */         a[m] = t;
/* 1302 */         t = b[i];
/* 1303 */         b[i] = b[m];
/* 1304 */         b[m] = t;
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
/*      */   public static <K> void quickSort(K[] x, K[] y, int from, int to) {
/* 1335 */     int len = to - from;
/* 1336 */     if (len < 16) {
/* 1337 */       selectionSort(x, y, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1341 */     int m = from + len / 2;
/* 1342 */     int l = from;
/* 1343 */     int n = to - 1;
/* 1344 */     if (len > 128) {
/* 1345 */       int i = len / 8;
/* 1346 */       l = med3(x, y, l, l + i, l + 2 * i);
/* 1347 */       m = med3(x, y, m - i, m, m + i);
/* 1348 */       n = med3(x, y, n - 2 * i, n - i, n);
/*      */     } 
/* 1350 */     m = med3(x, y, l, m, n);
/* 1351 */     K v = x[m], w = y[m];
/*      */     
/* 1353 */     int a = from, b = a, c = to - 1, d = c;
/*      */     
/*      */     while (true) {
/* 1356 */       if (b <= c) {
/*      */         int comparison; int t;
/* 1358 */         if ((comparison = ((t = ((Comparable<K>)x[b]).compareTo(v)) == 0) ? ((Comparable<K>)y[b]).compareTo(w) : t) <= 0) {
/* 1359 */           if (comparison == 0)
/* 1360 */             swap(x, y, a++, b); 
/* 1361 */           b++; continue;
/*      */         } 
/* 1363 */       }  while (c >= b) {
/*      */         int comparison; int t;
/* 1365 */         if ((comparison = ((t = ((Comparable<K>)x[c]).compareTo(v)) == 0) ? ((Comparable<K>)y[c]).compareTo(w) : t) >= 0) {
/* 1366 */           if (comparison == 0)
/* 1367 */             swap(x, y, c, d--); 
/* 1368 */           c--;
/*      */         } 
/* 1370 */       }  if (b > c)
/*      */         break; 
/* 1372 */       swap(x, y, b++, c--);
/*      */     } 
/*      */ 
/*      */     
/* 1376 */     int s = Math.min(a - from, b - a);
/* 1377 */     swap(x, y, from, b - s, s);
/* 1378 */     s = Math.min(d - c, to - d - 1);
/* 1379 */     swap(x, y, b, to - s, s);
/*      */     
/* 1381 */     if ((s = b - a) > 1)
/* 1382 */       quickSort(x, y, from, from + s); 
/* 1383 */     if ((s = d - c) > 1) {
/* 1384 */       quickSort(x, y, to - s, to);
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
/*      */   public static <K> void quickSort(K[] x, K[] y) {
/* 1408 */     ensureSameLength(x, y);
/* 1409 */     quickSort(x, y, 0, x.length);
/*      */   }
/*      */   
/*      */   protected static class ForkJoinQuickSort2<K> extends RecursiveAction {
/*      */     private static final long serialVersionUID = 1L;
/*      */     private final int from;
/*      */     
/*      */     public ForkJoinQuickSort2(K[] x, K[] y, int from, int to) {
/* 1417 */       this.from = from;
/* 1418 */       this.to = to;
/* 1419 */       this.x = x;
/* 1420 */       this.y = y;
/*      */     }
/*      */     private final int to; private final K[] x; private final K[] y;
/*      */     
/*      */     protected void compute() {
/* 1425 */       K[] x = this.x;
/* 1426 */       K[] y = this.y;
/* 1427 */       int len = this.to - this.from;
/* 1428 */       if (len < 8192) {
/* 1429 */         ObjectArrays.quickSort(x, y, this.from, this.to);
/*      */         
/*      */         return;
/*      */       } 
/* 1433 */       int m = this.from + len / 2;
/* 1434 */       int l = this.from;
/* 1435 */       int n = this.to - 1;
/* 1436 */       int s = len / 8;
/* 1437 */       l = ObjectArrays.med3(x, y, l, l + s, l + 2 * s);
/* 1438 */       m = ObjectArrays.med3(x, y, m - s, m, m + s);
/* 1439 */       n = ObjectArrays.med3(x, y, n - 2 * s, n - s, n);
/* 1440 */       m = ObjectArrays.med3(x, y, l, m, n);
/* 1441 */       K v = x[m], w = y[m];
/*      */       
/* 1443 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*      */       
/*      */       while (true) {
/* 1446 */         if (b <= c) {
/*      */           int comparison; int i;
/* 1448 */           if ((comparison = ((i = ((Comparable<K>)x[b]).compareTo(v)) == 0) ? ((Comparable<K>)y[b]).compareTo(w) : i) <= 0) {
/* 1449 */             if (comparison == 0)
/* 1450 */               ObjectArrays.swap(x, y, a++, b); 
/* 1451 */             b++; continue;
/*      */           } 
/* 1453 */         }  while (c >= b) {
/*      */           int comparison; int i;
/* 1455 */           if ((comparison = ((i = ((Comparable<K>)x[c]).compareTo(v)) == 0) ? ((Comparable<K>)y[c]).compareTo(w) : i) >= 0) {
/* 1456 */             if (comparison == 0)
/* 1457 */               ObjectArrays.swap(x, y, c, d--); 
/* 1458 */             c--;
/*      */           } 
/* 1460 */         }  if (b > c)
/*      */           break; 
/* 1462 */         ObjectArrays.swap(x, y, b++, c--);
/*      */       } 
/*      */ 
/*      */       
/* 1466 */       s = Math.min(a - this.from, b - a);
/* 1467 */       ObjectArrays.swap(x, y, this.from, b - s, s);
/* 1468 */       s = Math.min(d - c, this.to - d - 1);
/* 1469 */       ObjectArrays.swap(x, y, b, this.to - s, s);
/* 1470 */       s = b - a;
/* 1471 */       int t = d - c;
/*      */       
/* 1473 */       if (s > 1 && t > 1) {
/* 1474 */         invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + s), new ForkJoinQuickSort2(x, y, this.to - t, this.to));
/* 1475 */       } else if (s > 1) {
/* 1476 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.from, this.from + s) });
/*      */       } else {
/* 1478 */         invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinQuickSort2(x, y, this.to - t, this.to) });
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
/*      */   public static <K> void parallelQuickSort(K[] x, K[] y, int from, int to) {
/* 1511 */     if (to - from < 8192)
/* 1512 */       quickSort(x, y, from, to); 
/* 1513 */     ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 1514 */     pool.invoke(new ForkJoinQuickSort2<>(x, y, from, to));
/* 1515 */     pool.shutdown();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void parallelQuickSort(K[] x, K[] y) {
/* 1543 */     ensureSameLength(x, y);
/* 1544 */     parallelQuickSort(x, y, 0, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a, int from, int to) {
/* 1563 */     quickSort(a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a) {
/* 1576 */     unstableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a, int from, int to, Comparator<K> comp) {
/* 1595 */     quickSort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void unstableSort(K[] a, Comparator<K> comp) {
/* 1609 */     unstableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a, int from, int to, K[] supp) {
/* 1633 */     int len = to - from;
/*      */     
/* 1635 */     if (len < 16) {
/* 1636 */       insertionSort(a, from, to);
/*      */       
/*      */       return;
/*      */     } 
/* 1640 */     int mid = from + to >>> 1;
/* 1641 */     mergeSort(supp, from, mid, a);
/* 1642 */     mergeSort(supp, mid, to, a);
/*      */ 
/*      */     
/* 1645 */     if (((Comparable<K>)supp[mid - 1]).compareTo(supp[mid]) <= 0) {
/* 1646 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1650 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1651 */       if (q >= to || (p < mid && ((Comparable<K>)supp[p]).compareTo(supp[q]) <= 0)) {
/* 1652 */         a[i] = supp[p++];
/*      */       } else {
/* 1654 */         a[i] = supp[q++];
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
/*      */   public static <K> void mergeSort(K[] a, int from, int to) {
/* 1674 */     mergeSort(a, from, to, (K[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a) {
/* 1688 */     mergeSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp, K[] supp) {
/* 1713 */     int len = to - from;
/*      */     
/* 1715 */     if (len < 16) {
/* 1716 */       insertionSort(a, from, to, comp);
/*      */       
/*      */       return;
/*      */     } 
/* 1720 */     int mid = from + to >>> 1;
/* 1721 */     mergeSort(supp, from, mid, comp, a);
/* 1722 */     mergeSort(supp, mid, to, comp, a);
/*      */ 
/*      */     
/* 1725 */     if (comp.compare(supp[mid - 1], supp[mid]) <= 0) {
/* 1726 */       System.arraycopy(supp, from, a, from, len);
/*      */       
/*      */       return;
/*      */     } 
/* 1730 */     for (int i = from, p = from, q = mid; i < to; i++) {
/* 1731 */       if (q >= to || (p < mid && comp.compare(supp[p], supp[q]) <= 0)) {
/* 1732 */         a[i] = supp[p++];
/*      */       } else {
/* 1734 */         a[i] = supp[q++];
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
/*      */   public static <K> void mergeSort(K[] a, int from, int to, Comparator<K> comp) {
/* 1756 */     mergeSort(a, from, to, comp, (K[])a.clone());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void mergeSort(K[] a, Comparator<K> comp) {
/* 1773 */     mergeSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a, int from, int to) {
/* 1796 */     Arrays.sort((Object[])a, from, to);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a) {
/* 1814 */     stableSort(a, 0, a.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a, int from, int to, Comparator<K> comp) {
/* 1839 */     Arrays.sort(a, from, to, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> void stableSort(K[] a, Comparator<K> comp) {
/* 1859 */     stableSort(a, 0, a.length, comp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, int from, int to, K key) {
/* 1888 */     to--;
/* 1889 */     while (from <= to) {
/* 1890 */       int mid = from + to >>> 1;
/* 1891 */       K midVal = a[mid];
/* 1892 */       int cmp = ((Comparable<K>)midVal).compareTo(key);
/* 1893 */       if (cmp < 0) {
/* 1894 */         from = mid + 1; continue;
/* 1895 */       }  if (cmp > 0) {
/* 1896 */         to = mid - 1; continue;
/*      */       } 
/* 1898 */       return mid;
/*      */     } 
/* 1900 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, K key) {
/* 1922 */     return binarySearch(a, 0, a.length, key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, int from, int to, K key, Comparator<K> c) {
/* 1952 */     to--;
/* 1953 */     while (from <= to) {
/* 1954 */       int mid = from + to >>> 1;
/* 1955 */       K midVal = a[mid];
/* 1956 */       int cmp = c.compare(midVal, key);
/* 1957 */       if (cmp < 0) {
/* 1958 */         from = mid + 1; continue;
/* 1959 */       }  if (cmp > 0) {
/* 1960 */         to = mid - 1; continue;
/*      */       } 
/* 1962 */       return mid;
/*      */     } 
/* 1964 */     return -(from + 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> int binarySearch(K[] a, K key, Comparator<K> c) {
/* 1989 */     return binarySearch(a, 0, a.length, key, c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] shuffle(K[] a, int from, int to, Random random) {
/* 2006 */     for (int i = to - from; i-- != 0; ) {
/* 2007 */       int p = random.nextInt(i + 1);
/* 2008 */       K t = a[from + i];
/* 2009 */       a[from + i] = a[from + p];
/* 2010 */       a[from + p] = t;
/*      */     } 
/* 2012 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] shuffle(K[] a, Random random) {
/* 2025 */     for (int i = a.length; i-- != 0; ) {
/* 2026 */       int p = random.nextInt(i + 1);
/* 2027 */       K t = a[i];
/* 2028 */       a[i] = a[p];
/* 2029 */       a[p] = t;
/*      */     } 
/* 2031 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] reverse(K[] a) {
/* 2041 */     int length = a.length;
/* 2042 */     for (int i = length / 2; i-- != 0; ) {
/* 2043 */       K t = a[length - i - 1];
/* 2044 */       a[length - i - 1] = a[i];
/* 2045 */       a[i] = t;
/*      */     } 
/* 2047 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <K> K[] reverse(K[] a, int from, int to) {
/* 2061 */     int length = to - from;
/* 2062 */     for (int i = length / 2; i-- != 0; ) {
/* 2063 */       K t = a[from + length - i - 1];
/* 2064 */       a[from + length - i - 1] = a[from + i];
/* 2065 */       a[from + i] = t;
/*      */     } 
/* 2067 */     return a;
/*      */   }
/*      */   private static final class ArrayHashStrategy<K> implements Hash.Strategy<K[]>, Serializable { private static final long serialVersionUID = -7046029254386353129L;
/*      */     
/*      */     private ArrayHashStrategy() {}
/*      */     
/*      */     public int hashCode(K[] o) {
/* 2074 */       return Arrays.hashCode((Object[])o);
/*      */     }
/*      */     
/*      */     public boolean equals(K[] a, K[] b) {
/* 2078 */       return Arrays.equals((Object[])a, (Object[])b);
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
/* 2090 */   public static final Hash.Strategy HASH_STRATEGY = new ArrayHashStrategy();
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectArrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */