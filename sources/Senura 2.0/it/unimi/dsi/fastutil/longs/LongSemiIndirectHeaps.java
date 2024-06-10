/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
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
/*     */ 
/*     */ public final class LongSemiIndirectHeaps
/*     */ {
/*     */   public static int downHeap(long[] refArray, int[] heap, int size, int i, LongComparator c) {
/*  49 */     assert i < size;
/*  50 */     int e = heap[i];
/*  51 */     long E = refArray[e];
/*     */     
/*  53 */     if (c == null) {
/*  54 */       int child; while ((child = (i << 1) + 1) < size) {
/*  55 */         int t = heap[child];
/*  56 */         int right = child + 1;
/*  57 */         if (right < size && refArray[heap[right]] < refArray[t])
/*  58 */           t = heap[child = right]; 
/*  59 */         if (E <= refArray[t])
/*     */           break; 
/*  61 */         heap[i] = t;
/*  62 */         i = child;
/*     */       } 
/*     */     } else {
/*  65 */       int child; while ((child = (i << 1) + 1) < size) {
/*  66 */         int t = heap[child];
/*  67 */         int right = child + 1;
/*  68 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0)
/*  69 */           t = heap[child = right]; 
/*  70 */         if (c.compare(E, refArray[t]) <= 0)
/*     */           break; 
/*  72 */         heap[i] = t;
/*  73 */         i = child;
/*     */       } 
/*  75 */     }  heap[i] = e;
/*  76 */     return i;
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
/*     */   public static int upHeap(long[] refArray, int[] heap, int size, int i, LongComparator c) {
/*  96 */     assert i < size;
/*  97 */     int e = heap[i];
/*  98 */     long E = refArray[e];
/*  99 */     if (c == null) {
/* 100 */       while (i != 0) {
/* 101 */         int parent = i - 1 >>> 1;
/* 102 */         int t = heap[parent];
/* 103 */         if (refArray[t] <= E)
/*     */           break; 
/* 105 */         heap[i] = t;
/* 106 */         i = parent;
/*     */       } 
/*     */     } else {
/* 109 */       while (i != 0) {
/* 110 */         int parent = i - 1 >>> 1;
/* 111 */         int t = heap[parent];
/* 112 */         if (c.compare(refArray[t], E) <= 0)
/*     */           break; 
/* 114 */         heap[i] = t;
/* 115 */         i = parent;
/*     */       } 
/* 117 */     }  heap[i] = e;
/* 118 */     return i;
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
/*     */   public static void makeHeap(long[] refArray, int offset, int length, int[] heap, LongComparator c) {
/* 136 */     LongArrays.ensureOffsetLength(refArray, offset, length);
/* 137 */     if (heap.length < length) {
/* 138 */       throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")");
/*     */     }
/* 140 */     int i = length;
/* 141 */     while (i-- != 0)
/* 142 */       heap[i] = offset + i; 
/* 143 */     i = length >>> 1;
/* 144 */     while (i-- != 0) {
/* 145 */       downHeap(refArray, heap, length, i, c);
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
/*     */   public static int[] makeHeap(long[] refArray, int offset, int length, LongComparator c) {
/* 161 */     int[] heap = (length <= 0) ? IntArrays.EMPTY_ARRAY : new int[length];
/* 162 */     makeHeap(refArray, offset, length, heap, c);
/* 163 */     return heap;
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
/*     */   public static void makeHeap(long[] refArray, int[] heap, int size, LongComparator c) {
/* 178 */     int i = size >>> 1;
/* 179 */     while (i-- != 0) {
/* 180 */       downHeap(refArray, heap, size, i, c);
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
/*     */   public static int front(long[] refArray, int[] heap, int size, int[] a) {
/* 212 */     long top = refArray[heap[0]];
/* 213 */     int j = 0;
/* 214 */     int l = 0;
/* 215 */     int r = 1;
/* 216 */     int f = 0;
/* 217 */     for (int i = 0; i < r; i++) {
/* 218 */       if (i == f) {
/* 219 */         if (l >= r)
/*     */           break; 
/* 221 */         f = (f << 1) + 1;
/* 222 */         i = l;
/* 223 */         l = -1;
/*     */       } 
/* 225 */       if (top == refArray[heap[i]]) {
/* 226 */         a[j++] = heap[i];
/* 227 */         if (l == -1)
/* 228 */           l = i * 2 + 1; 
/* 229 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 232 */     return j;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int front(long[] refArray, int[] heap, int size, int[] a, LongComparator c) {
/* 266 */     long top = refArray[heap[0]];
/* 267 */     int j = 0;
/* 268 */     int l = 0;
/* 269 */     int r = 1;
/* 270 */     int f = 0;
/* 271 */     for (int i = 0; i < r; i++) {
/* 272 */       if (i == f) {
/* 273 */         if (l >= r)
/*     */           break; 
/* 275 */         f = (f << 1) + 1;
/* 276 */         i = l;
/* 277 */         l = -1;
/*     */       } 
/* 279 */       if (c.compare(top, refArray[heap[i]]) == 0) {
/* 280 */         a[j++] = heap[i];
/* 281 */         if (l == -1)
/* 282 */           l = i * 2 + 1; 
/* 283 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 286 */     return j;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongSemiIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */