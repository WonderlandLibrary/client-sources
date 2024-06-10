/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Comparator;
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
/*     */ public final class ObjectHeaps
/*     */ {
/*     */   public static <K> int downHeap(K[] heap, int size, int i, Comparator<? super K> c) {
/*  47 */     assert i < size;
/*  48 */     K e = heap[i];
/*     */     
/*  50 */     if (c == null) {
/*  51 */       int child; while ((child = (i << 1) + 1) < size) {
/*  52 */         K t = heap[child];
/*  53 */         int right = child + 1;
/*  54 */         if (right < size && ((Comparable<K>)heap[right]).compareTo(t) < 0)
/*  55 */           t = heap[child = right]; 
/*  56 */         if (((Comparable<K>)e).compareTo(t) <= 0)
/*     */           break; 
/*  58 */         heap[i] = t;
/*  59 */         i = child;
/*     */       } 
/*     */     } else {
/*  62 */       int child; while ((child = (i << 1) + 1) < size) {
/*  63 */         K t = heap[child];
/*  64 */         int right = child + 1;
/*  65 */         if (right < size && c.compare(heap[right], t) < 0)
/*  66 */           t = heap[child = right]; 
/*  67 */         if (c.compare(e, t) <= 0)
/*     */           break; 
/*  69 */         heap[i] = t;
/*  70 */         i = child;
/*     */       } 
/*  72 */     }  heap[i] = e;
/*  73 */     return i;
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
/*     */   public static <K> int upHeap(K[] heap, int size, int i, Comparator<K> c) {
/*  91 */     assert i < size;
/*  92 */     K e = heap[i];
/*  93 */     if (c == null) {
/*  94 */       while (i != 0) {
/*  95 */         int parent = i - 1 >>> 1;
/*  96 */         K t = heap[parent];
/*  97 */         if (((Comparable<K>)t).compareTo(e) <= 0)
/*     */           break; 
/*  99 */         heap[i] = t;
/* 100 */         i = parent;
/*     */       } 
/*     */     } else {
/* 103 */       while (i != 0) {
/* 104 */         int parent = i - 1 >>> 1;
/* 105 */         K t = heap[parent];
/* 106 */         if (c.compare(t, e) <= 0)
/*     */           break; 
/* 108 */         heap[i] = t;
/* 109 */         i = parent;
/*     */       } 
/* 111 */     }  heap[i] = e;
/* 112 */     return i;
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
/*     */   public static <K> void makeHeap(K[] heap, int size, Comparator<K> c) {
/* 125 */     int i = size >>> 1;
/* 126 */     while (i-- != 0)
/* 127 */       downHeap(heap, size, i, c); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */