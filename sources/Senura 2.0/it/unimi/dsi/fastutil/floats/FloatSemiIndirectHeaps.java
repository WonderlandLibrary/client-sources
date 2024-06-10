/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ 
/*     */ public final class FloatSemiIndirectHeaps
/*     */ {
/*     */   public static int downHeap(float[] refArray, int[] heap, int size, int i, FloatComparator c) {
/*  50 */     assert i < size;
/*  51 */     int e = heap[i];
/*  52 */     float E = refArray[e];
/*     */     
/*  54 */     if (c == null) {
/*  55 */       int child; while ((child = (i << 1) + 1) < size) {
/*  56 */         int t = heap[child];
/*  57 */         int right = child + 1;
/*  58 */         if (right < size && Float.compare(refArray[heap[right]], refArray[t]) < 0)
/*  59 */           t = heap[child = right]; 
/*  60 */         if (Float.compare(E, refArray[t]) <= 0)
/*     */           break; 
/*  62 */         heap[i] = t;
/*  63 */         i = child;
/*     */       } 
/*     */     } else {
/*  66 */       int child; while ((child = (i << 1) + 1) < size) {
/*  67 */         int t = heap[child];
/*  68 */         int right = child + 1;
/*  69 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0)
/*  70 */           t = heap[child = right]; 
/*  71 */         if (c.compare(E, refArray[t]) <= 0)
/*     */           break; 
/*  73 */         heap[i] = t;
/*  74 */         i = child;
/*     */       } 
/*  76 */     }  heap[i] = e;
/*  77 */     return i;
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
/*     */   public static int upHeap(float[] refArray, int[] heap, int size, int i, FloatComparator c) {
/*  97 */     assert i < size;
/*  98 */     int e = heap[i];
/*  99 */     float E = refArray[e];
/* 100 */     if (c == null) {
/* 101 */       while (i != 0) {
/* 102 */         int parent = i - 1 >>> 1;
/* 103 */         int t = heap[parent];
/* 104 */         if (Float.compare(refArray[t], E) <= 0)
/*     */           break; 
/* 106 */         heap[i] = t;
/* 107 */         i = parent;
/*     */       } 
/*     */     } else {
/* 110 */       while (i != 0) {
/* 111 */         int parent = i - 1 >>> 1;
/* 112 */         int t = heap[parent];
/* 113 */         if (c.compare(refArray[t], E) <= 0)
/*     */           break; 
/* 115 */         heap[i] = t;
/* 116 */         i = parent;
/*     */       } 
/* 118 */     }  heap[i] = e;
/* 119 */     return i;
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
/*     */   public static void makeHeap(float[] refArray, int offset, int length, int[] heap, FloatComparator c) {
/* 137 */     FloatArrays.ensureOffsetLength(refArray, offset, length);
/* 138 */     if (heap.length < length) {
/* 139 */       throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")");
/*     */     }
/* 141 */     int i = length;
/* 142 */     while (i-- != 0)
/* 143 */       heap[i] = offset + i; 
/* 144 */     i = length >>> 1;
/* 145 */     while (i-- != 0) {
/* 146 */       downHeap(refArray, heap, length, i, c);
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
/*     */   public static int[] makeHeap(float[] refArray, int offset, int length, FloatComparator c) {
/* 162 */     int[] heap = (length <= 0) ? IntArrays.EMPTY_ARRAY : new int[length];
/* 163 */     makeHeap(refArray, offset, length, heap, c);
/* 164 */     return heap;
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
/*     */   public static void makeHeap(float[] refArray, int[] heap, int size, FloatComparator c) {
/* 179 */     int i = size >>> 1;
/* 180 */     while (i-- != 0) {
/* 181 */       downHeap(refArray, heap, size, i, c);
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
/*     */   public static int front(float[] refArray, int[] heap, int size, int[] a) {
/* 213 */     float top = refArray[heap[0]];
/* 214 */     int j = 0;
/* 215 */     int l = 0;
/* 216 */     int r = 1;
/* 217 */     int f = 0;
/* 218 */     for (int i = 0; i < r; i++) {
/* 219 */       if (i == f) {
/* 220 */         if (l >= r)
/*     */           break; 
/* 222 */         f = (f << 1) + 1;
/* 223 */         i = l;
/* 224 */         l = -1;
/*     */       } 
/* 226 */       if (Float.compare(top, refArray[heap[i]]) == 0) {
/* 227 */         a[j++] = heap[i];
/* 228 */         if (l == -1)
/* 229 */           l = i * 2 + 1; 
/* 230 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 233 */     return j;
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
/*     */   public static int front(float[] refArray, int[] heap, int size, int[] a, FloatComparator c) {
/* 267 */     float top = refArray[heap[0]];
/* 268 */     int j = 0;
/* 269 */     int l = 0;
/* 270 */     int r = 1;
/* 271 */     int f = 0;
/* 272 */     for (int i = 0; i < r; i++) {
/* 273 */       if (i == f) {
/* 274 */         if (l >= r)
/*     */           break; 
/* 276 */         f = (f << 1) + 1;
/* 277 */         i = l;
/* 278 */         l = -1;
/*     */       } 
/* 280 */       if (c.compare(top, refArray[heap[i]]) == 0) {
/* 281 */         a[j++] = heap[i];
/* 282 */         if (l == -1)
/* 283 */           l = i * 2 + 1; 
/* 284 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 287 */     return j;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatSemiIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */