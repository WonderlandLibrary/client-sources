/*     */ package it.unimi.dsi.fastutil.bytes;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteIndirectHeaps
/*     */ {
/*     */   public static int downHeap(byte[] refArray, int[] heap, int[] inv, int size, int i, ByteComparator c) {
/*  52 */     assert i < size;
/*  53 */     int e = heap[i];
/*  54 */     byte E = refArray[e];
/*     */     
/*  56 */     if (c == null) {
/*  57 */       int child; while ((child = (i << 1) + 1) < size) {
/*  58 */         int t = heap[child];
/*  59 */         int right = child + 1;
/*  60 */         if (right < size && refArray[heap[right]] < refArray[t])
/*  61 */           t = heap[child = right]; 
/*  62 */         if (E <= refArray[t])
/*     */           break; 
/*  64 */         heap[i] = t;
/*  65 */         inv[heap[i]] = i;
/*  66 */         i = child;
/*     */       } 
/*     */     } else {
/*  69 */       int child; while ((child = (i << 1) + 1) < size) {
/*  70 */         int t = heap[child];
/*  71 */         int right = child + 1;
/*  72 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0)
/*  73 */           t = heap[child = right]; 
/*  74 */         if (c.compare(E, refArray[t]) <= 0)
/*     */           break; 
/*  76 */         heap[i] = t;
/*  77 */         inv[heap[i]] = i;
/*  78 */         i = child;
/*     */       } 
/*  80 */     }  heap[i] = e;
/*  81 */     inv[e] = i;
/*  82 */     return i;
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
/*     */   public static int upHeap(byte[] refArray, int[] heap, int[] inv, int size, int i, ByteComparator c) {
/* 107 */     assert i < size;
/* 108 */     int e = heap[i];
/* 109 */     byte E = refArray[e];
/* 110 */     if (c == null) {
/* 111 */       while (i != 0) {
/* 112 */         int parent = i - 1 >>> 1;
/* 113 */         int t = heap[parent];
/* 114 */         if (refArray[t] <= E)
/*     */           break; 
/* 116 */         heap[i] = t;
/* 117 */         inv[heap[i]] = i;
/* 118 */         i = parent;
/*     */       } 
/*     */     } else {
/* 121 */       while (i != 0) {
/* 122 */         int parent = i - 1 >>> 1;
/* 123 */         int t = heap[parent];
/* 124 */         if (c.compare(refArray[t], E) <= 0)
/*     */           break; 
/* 126 */         heap[i] = t;
/* 127 */         inv[heap[i]] = i;
/* 128 */         i = parent;
/*     */       } 
/* 130 */     }  heap[i] = e;
/* 131 */     inv[e] = i;
/* 132 */     return i;
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
/*     */   public static void makeHeap(byte[] refArray, int offset, int length, int[] heap, int[] inv, ByteComparator c) {
/* 152 */     ByteArrays.ensureOffsetLength(refArray, offset, length);
/* 153 */     if (heap.length < length) {
/* 154 */       throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")");
/*     */     }
/* 156 */     if (inv.length < refArray.length) {
/* 157 */       throw new IllegalArgumentException("The inversion array length (" + heap.length + ") is smaller than the length of the reference array (" + refArray.length + ")");
/*     */     }
/* 159 */     Arrays.fill(inv, 0, refArray.length, -1);
/* 160 */     int i = length;
/* 161 */     while (i-- != 0) {
/* 162 */       heap[i] = offset + i; inv[offset + i] = i;
/* 163 */     }  i = length >>> 1;
/* 164 */     while (i-- != 0) {
/* 165 */       downHeap(refArray, heap, inv, length, i, c);
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
/*     */   public static void makeHeap(byte[] refArray, int[] heap, int[] inv, int size, ByteComparator c) {
/* 183 */     int i = size >>> 1;
/* 184 */     while (i-- != 0)
/* 185 */       downHeap(refArray, heap, inv, size, i, c); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */