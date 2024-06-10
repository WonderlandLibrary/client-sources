/*     */ package it.unimi.dsi.fastutil;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntComparator;
/*     */ import java.util.concurrent.ForkJoinPool;
/*     */ import java.util.concurrent.ForkJoinTask;
/*     */ import java.util.concurrent.RecursiveAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Arrays
/*     */ {
/*     */   public static final int MAX_ARRAY_SIZE = 2147483639;
/*     */   private static final int MERGESORT_NO_REC = 16;
/*     */   private static final int QUICKSORT_NO_REC = 16;
/*     */   private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
/*     */   private static final int QUICKSORT_MEDIAN_OF_9 = 128;
/*     */   
/*     */   public static void ensureFromTo(int arrayLength, int from, int to) {
/*  55 */     if (from < 0) throw new ArrayIndexOutOfBoundsException("Start index (" + from + ") is negative"); 
/*  56 */     if (from > to) throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")"); 
/*  57 */     if (to > arrayLength) throw new ArrayIndexOutOfBoundsException("End index (" + to + ") is greater than array length (" + arrayLength + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void ensureOffsetLength(int arrayLength, int offset, int length) {
/*  71 */     if (offset < 0) throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative"); 
/*  72 */     if (length < 0) throw new IllegalArgumentException("Length (" + length + ") is negative"); 
/*  73 */     if (offset + length > arrayLength) throw new ArrayIndexOutOfBoundsException("Last index (" + (offset + length) + ") is greater than array length (" + arrayLength + ")");
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void inPlaceMerge(int from, int mid, int to, IntComparator comp, Swapper swapper) {
/*     */     int firstCut, secondCut;
/*  83 */     if (from >= mid || mid >= to)
/*  84 */       return;  if (to - from == 2) {
/*  85 */       if (comp.compare(mid, from) < 0) swapper.swap(from, mid);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  92 */     if (mid - from > to - mid) {
/*  93 */       firstCut = from + (mid - from) / 2;
/*  94 */       secondCut = lowerBound(mid, to, firstCut, comp);
/*     */     } else {
/*     */       
/*  97 */       secondCut = mid + (to - mid) / 2;
/*  98 */       firstCut = upperBound(from, mid, secondCut, comp);
/*     */     } 
/*     */     
/* 101 */     int first2 = firstCut;
/* 102 */     int middle2 = mid;
/* 103 */     int last2 = secondCut;
/* 104 */     if (middle2 != first2 && middle2 != last2) {
/* 105 */       int first1 = first2;
/* 106 */       int last1 = middle2;
/* 107 */       while (first1 < --last1)
/* 108 */         swapper.swap(first1++, last1); 
/* 109 */       first1 = middle2;
/* 110 */       last1 = last2;
/* 111 */       while (first1 < --last1)
/* 112 */         swapper.swap(first1++, last1); 
/* 113 */       first1 = first2;
/* 114 */       last1 = last2;
/* 115 */       while (first1 < --last1) {
/* 116 */         swapper.swap(first1++, last1);
/*     */       }
/*     */     } 
/* 119 */     mid = firstCut + secondCut - mid;
/* 120 */     inPlaceMerge(from, firstCut, mid, comp, swapper);
/* 121 */     inPlaceMerge(mid, secondCut, to, comp, swapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int lowerBound(int from, int to, int pos, IntComparator comp) {
/* 138 */     int len = to - from;
/* 139 */     while (len > 0) {
/* 140 */       int half = len / 2;
/* 141 */       int middle = from + half;
/* 142 */       if (comp.compare(middle, pos) < 0) {
/* 143 */         from = middle + 1;
/* 144 */         len -= half + 1;
/*     */         continue;
/*     */       } 
/* 147 */       len = half;
/*     */     } 
/*     */     
/* 150 */     return from;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int upperBound(int from, int mid, int pos, IntComparator comp) {
/* 168 */     int len = mid - from;
/* 169 */     while (len > 0) {
/* 170 */       int half = len / 2;
/* 171 */       int middle = from + half;
/* 172 */       if (comp.compare(pos, middle) < 0) {
/* 173 */         len = half;
/*     */         continue;
/*     */       } 
/* 176 */       from = middle + 1;
/* 177 */       len -= half + 1;
/*     */     } 
/*     */     
/* 180 */     return from;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int med3(int a, int b, int c, IntComparator comp) {
/* 187 */     int ab = comp.compare(a, b);
/* 188 */     int ac = comp.compare(a, c);
/* 189 */     int bc = comp.compare(b, c);
/* 190 */     return (ab < 0) ? (
/* 191 */       (bc < 0) ? b : ((ac < 0) ? c : a)) : (
/* 192 */       (bc > 0) ? b : ((ac > 0) ? c : a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mergeSort(int from, int to, IntComparator c, Swapper swapper) {
/* 218 */     int length = to - from;
/*     */ 
/*     */     
/* 221 */     if (length < 16) {
/* 222 */       for (int i = from; i < to; i++) {
/* 223 */         for (int j = i; j > from && c.compare(j - 1, j) > 0; j--) {
/* 224 */           swapper.swap(j, j - 1);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 231 */     int mid = from + to >>> 1;
/* 232 */     mergeSort(from, mid, c, swapper);
/* 233 */     mergeSort(mid, to, c, swapper);
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (c.compare(mid - 1, mid) <= 0) {
/*     */       return;
/*     */     }
/* 240 */     inPlaceMerge(from, mid, to, c, swapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void swap(Swapper swapper, int a, int b, int n) {
/* 251 */     for (int i = 0; i < n; ) { swapper.swap(a, b); i++; a++; b++; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected static class ForkJoinGenericQuickSort
/*     */     extends RecursiveAction
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final int from;
/*     */     private final int to;
/*     */     private final IntComparator comp;
/*     */     private final Swapper swapper;
/*     */     
/*     */     public ForkJoinGenericQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
/* 266 */       this.from = from;
/* 267 */       this.to = to;
/* 268 */       this.comp = comp;
/* 269 */       this.swapper = swapper;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void compute() {
/* 274 */       int len = this.to - this.from;
/* 275 */       if (len < 8192) {
/* 276 */         Arrays.quickSort(this.from, this.to, this.comp, this.swapper);
/*     */         
/*     */         return;
/*     */       } 
/* 280 */       int m = this.from + len / 2;
/* 281 */       int l = this.from;
/* 282 */       int n = this.to - 1;
/* 283 */       int s = len / 8;
/* 284 */       l = Arrays.med3(l, l + s, l + 2 * s, this.comp);
/* 285 */       m = Arrays.med3(m - s, m, m + s, this.comp);
/* 286 */       n = Arrays.med3(n - 2 * s, n - s, n, this.comp);
/* 287 */       m = Arrays.med3(l, m, n, this.comp);
/*     */       
/* 289 */       int a = this.from, b = a, c = this.to - 1, d = c;
/*     */       while (true) {
/*     */         int comparison;
/* 292 */         if (b <= c && (comparison = this.comp.compare(b, m)) <= 0) {
/* 293 */           if (comparison == 0) {
/*     */             
/* 295 */             if (a == m) { m = b; }
/* 296 */             else if (b == m) { m = a; }
/* 297 */              this.swapper.swap(a++, b);
/*     */           } 
/* 299 */           b++; continue;
/*     */         } 
/* 301 */         while (c >= b && (comparison = this.comp.compare(c, m)) >= 0) {
/* 302 */           if (comparison == 0) {
/*     */             
/* 304 */             if (c == m) { m = d; }
/* 305 */             else if (d == m) { m = c; }
/* 306 */              this.swapper.swap(c, d--);
/*     */           } 
/* 308 */           c--;
/*     */         } 
/* 310 */         if (b > c)
/*     */           break; 
/* 312 */         if (b == m) { m = d; }
/* 313 */         else if (c == m) { m = c; }
/* 314 */          this.swapper.swap(b++, c--);
/*     */       } 
/*     */ 
/*     */       
/* 318 */       s = Math.min(a - this.from, b - a);
/* 319 */       Arrays.swap(this.swapper, this.from, b - s, s);
/* 320 */       s = Math.min(d - c, this.to - d - 1);
/* 321 */       Arrays.swap(this.swapper, b, this.to - s, s);
/*     */ 
/*     */ 
/*     */       
/* 325 */       s = b - a;
/* 326 */       int t = d - c;
/* 327 */       if (s > 1 && t > 1) { invokeAll(new ForkJoinGenericQuickSort(this.from, this.from + s, this.comp, this.swapper), new ForkJoinGenericQuickSort(this.to - t, this.to, this.comp, this.swapper)); }
/* 328 */       else if (s > 1) { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinGenericQuickSort(this.from, this.from + s, this.comp, this.swapper) }); }
/* 329 */       else { invokeAll((ForkJoinTask<?>[])new ForkJoinTask[] { new ForkJoinGenericQuickSort(this.to - t, this.to, this.comp, this.swapper) }); }
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void parallelQuickSort(int from, int to, IntComparator comp, Swapper swapper) {
/* 349 */     ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
/* 350 */     pool.invoke(new ForkJoinGenericQuickSort(from, to, comp, swapper));
/* 351 */     pool.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void quickSort(int from, int to, IntComparator comp, Swapper swapper) {
/* 371 */     int len = to - from;
/*     */     
/* 373 */     if (len < 16) {
/* 374 */       for (int i = from; i < to; i++) {
/* 375 */         for (int j = i; j > from && comp.compare(j - 1, j) > 0; j--) {
/* 376 */           swapper.swap(j, j - 1);
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 382 */     int m = from + len / 2;
/* 383 */     int l = from;
/* 384 */     int n = to - 1;
/* 385 */     if (len > 128) {
/* 386 */       int i = len / 8;
/* 387 */       l = med3(l, l + i, l + 2 * i, comp);
/* 388 */       m = med3(m - i, m, m + i, comp);
/* 389 */       n = med3(n - 2 * i, n - i, n, comp);
/*     */     } 
/* 391 */     m = med3(l, m, n, comp);
/*     */ 
/*     */     
/* 394 */     int a = from;
/* 395 */     int b = a;
/* 396 */     int c = to - 1;
/*     */     
/* 398 */     int d = c;
/*     */     while (true) {
/*     */       int comparison;
/* 401 */       if (b <= c && (comparison = comp.compare(b, m)) <= 0) {
/* 402 */         if (comparison == 0) {
/*     */           
/* 404 */           if (a == m) { m = b; }
/* 405 */           else if (b == m) { m = a; }
/* 406 */            swapper.swap(a++, b);
/*     */         } 
/* 408 */         b++; continue;
/*     */       } 
/* 410 */       while (c >= b && (comparison = comp.compare(c, m)) >= 0) {
/* 411 */         if (comparison == 0) {
/*     */           
/* 413 */           if (c == m) { m = d; }
/* 414 */           else if (d == m) { m = c; }
/* 415 */            swapper.swap(c, d--);
/*     */         } 
/* 417 */         c--;
/*     */       } 
/* 419 */       if (b > c)
/*     */         break; 
/* 421 */       if (b == m) { m = d; }
/* 422 */       else if (c == m) { m = c; }
/* 423 */        swapper.swap(b++, c--);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 428 */     int s = Math.min(a - from, b - a);
/* 429 */     swap(swapper, from, b - s, s);
/* 430 */     s = Math.min(d - c, to - d - 1);
/* 431 */     swap(swapper, b, to - s, s);
/*     */ 
/*     */     
/* 434 */     if ((s = b - a) > 1) quickSort(from, from + s, comp, swapper); 
/* 435 */     if ((s = d - c) > 1) quickSort(to - s, to, comp, swapper); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\Arrays.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */