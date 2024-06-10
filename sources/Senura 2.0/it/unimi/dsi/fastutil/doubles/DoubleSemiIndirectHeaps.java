/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public final class DoubleSemiIndirectHeaps
/*     */ {
/*     */   public static int downHeap(double[] refArray, int[] heap, int size, int i, DoubleComparator c) {
/*  50 */     assert i < size;
/*  51 */     int e = heap[i];
/*  52 */     double E = refArray[e];
/*     */     
/*  54 */     if (c == null) {
/*  55 */       int child; while ((child = (i << 1) + 1) < size) {
/*  56 */         int t = heap[child];
/*  57 */         int right = child + 1;
/*  58 */         if (right < size && Double.compare(refArray[heap[right]], refArray[t]) < 0)
/*  59 */           t = heap[child = right]; 
/*  60 */         if (Double.compare(E, refArray[t]) <= 0)
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
/*     */   
/*     */   public static int upHeap(double[] refArray, int[] heap, int size, int i, DoubleComparator c) {
/*  98 */     assert i < size;
/*  99 */     int e = heap[i];
/* 100 */     double E = refArray[e];
/* 101 */     if (c == null) {
/* 102 */       while (i != 0) {
/* 103 */         int parent = i - 1 >>> 1;
/* 104 */         int t = heap[parent];
/* 105 */         if (Double.compare(refArray[t], E) <= 0)
/*     */           break; 
/* 107 */         heap[i] = t;
/* 108 */         i = parent;
/*     */       } 
/*     */     } else {
/* 111 */       while (i != 0) {
/* 112 */         int parent = i - 1 >>> 1;
/* 113 */         int t = heap[parent];
/* 114 */         if (c.compare(refArray[t], E) <= 0)
/*     */           break; 
/* 116 */         heap[i] = t;
/* 117 */         i = parent;
/*     */       } 
/* 119 */     }  heap[i] = e;
/* 120 */     return i;
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
/*     */   public static void makeHeap(double[] refArray, int offset, int length, int[] heap, DoubleComparator c) {
/* 138 */     DoubleArrays.ensureOffsetLength(refArray, offset, length);
/* 139 */     if (heap.length < length) {
/* 140 */       throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")");
/*     */     }
/* 142 */     int i = length;
/* 143 */     while (i-- != 0)
/* 144 */       heap[i] = offset + i; 
/* 145 */     i = length >>> 1;
/* 146 */     while (i-- != 0) {
/* 147 */       downHeap(refArray, heap, length, i, c);
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
/*     */   public static int[] makeHeap(double[] refArray, int offset, int length, DoubleComparator c) {
/* 164 */     int[] heap = (length <= 0) ? IntArrays.EMPTY_ARRAY : new int[length];
/* 165 */     makeHeap(refArray, offset, length, heap, c);
/* 166 */     return heap;
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
/*     */   public static void makeHeap(double[] refArray, int[] heap, int size, DoubleComparator c) {
/* 181 */     int i = size >>> 1;
/* 182 */     while (i-- != 0) {
/* 183 */       downHeap(refArray, heap, size, i, c);
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
/*     */   public static int front(double[] refArray, int[] heap, int size, int[] a) {
/* 215 */     double top = refArray[heap[0]];
/* 216 */     int j = 0;
/* 217 */     int l = 0;
/* 218 */     int r = 1;
/* 219 */     int f = 0;
/* 220 */     for (int i = 0; i < r; i++) {
/* 221 */       if (i == f) {
/* 222 */         if (l >= r)
/*     */           break; 
/* 224 */         f = (f << 1) + 1;
/* 225 */         i = l;
/* 226 */         l = -1;
/*     */       } 
/* 228 */       if (Double.compare(top, refArray[heap[i]]) == 0) {
/* 229 */         a[j++] = heap[i];
/* 230 */         if (l == -1)
/* 231 */           l = i * 2 + 1; 
/* 232 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 235 */     return j;
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
/*     */   public static int front(double[] refArray, int[] heap, int size, int[] a, DoubleComparator c) {
/* 269 */     double top = refArray[heap[0]];
/* 270 */     int j = 0;
/* 271 */     int l = 0;
/* 272 */     int r = 1;
/* 273 */     int f = 0;
/* 274 */     for (int i = 0; i < r; i++) {
/* 275 */       if (i == f) {
/* 276 */         if (l >= r)
/*     */           break; 
/* 278 */         f = (f << 1) + 1;
/* 279 */         i = l;
/* 280 */         l = -1;
/*     */       } 
/* 282 */       if (c.compare(top, refArray[heap[i]]) == 0) {
/* 283 */         a[j++] = heap[i];
/* 284 */         if (l == -1)
/* 285 */           l = i * 2 + 1; 
/* 286 */         r = Math.min(size, i * 2 + 3);
/*     */       } 
/*     */     } 
/* 289 */     return j;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleSemiIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */