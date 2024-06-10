/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ 
/*     */ 
/*     */ public final class DoubleHeaps
/*     */ {
/*     */   public static int downHeap(double[] heap, int size, int i, DoubleComparator c) {
/*  46 */     assert i < size;
/*  47 */     double e = heap[i];
/*     */     
/*  49 */     if (c == null) {
/*  50 */       int child; while ((child = (i << 1) + 1) < size) {
/*  51 */         double t = heap[child];
/*  52 */         int right = child + 1;
/*  53 */         if (right < size && Double.compare(heap[right], t) < 0)
/*  54 */           t = heap[child = right]; 
/*  55 */         if (Double.compare(e, t) <= 0)
/*     */           break; 
/*  57 */         heap[i] = t;
/*  58 */         i = child;
/*     */       } 
/*     */     } else {
/*  61 */       int child; while ((child = (i << 1) + 1) < size) {
/*  62 */         double t = heap[child];
/*  63 */         int right = child + 1;
/*  64 */         if (right < size && c.compare(heap[right], t) < 0)
/*  65 */           t = heap[child = right]; 
/*  66 */         if (c.compare(e, t) <= 0)
/*     */           break; 
/*  68 */         heap[i] = t;
/*  69 */         i = child;
/*     */       } 
/*  71 */     }  heap[i] = e;
/*  72 */     return i;
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
/*     */   public static int upHeap(double[] heap, int size, int i, DoubleComparator c) {
/*  90 */     assert i < size;
/*  91 */     double e = heap[i];
/*  92 */     if (c == null) {
/*  93 */       while (i != 0) {
/*  94 */         int parent = i - 1 >>> 1;
/*  95 */         double t = heap[parent];
/*  96 */         if (Double.compare(t, e) <= 0)
/*     */           break; 
/*  98 */         heap[i] = t;
/*  99 */         i = parent;
/*     */       } 
/*     */     } else {
/* 102 */       while (i != 0) {
/* 103 */         int parent = i - 1 >>> 1;
/* 104 */         double t = heap[parent];
/* 105 */         if (c.compare(t, e) <= 0)
/*     */           break; 
/* 107 */         heap[i] = t;
/* 108 */         i = parent;
/*     */       } 
/* 110 */     }  heap[i] = e;
/* 111 */     return i;
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
/*     */   public static void makeHeap(double[] heap, int size, DoubleComparator c) {
/* 124 */     int i = size >>> 1;
/* 125 */     while (i-- != 0)
/* 126 */       downHeap(heap, size, i, c); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */