/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectIndirectHeaps
/*     */ {
/*     */   public static <K> int downHeap(K[] refArray, int[] heap, int[] inv, int size, int i, Comparator<K> c) {
/*  53 */     assert i < size;
/*  54 */     int e = heap[i];
/*  55 */     K E = refArray[e];
/*     */     
/*  57 */     if (c == null) {
/*  58 */       int child; while ((child = (i << 1) + 1) < size) {
/*  59 */         int t = heap[child];
/*  60 */         int right = child + 1;
/*  61 */         if (right < size && ((Comparable<K>)refArray[heap[right]]).compareTo(refArray[t]) < 0)
/*  62 */           t = heap[child = right]; 
/*  63 */         if (((Comparable<K>)E).compareTo(refArray[t]) <= 0)
/*     */           break; 
/*  65 */         heap[i] = t;
/*  66 */         inv[heap[i]] = i;
/*  67 */         i = child;
/*     */       } 
/*     */     } else {
/*  70 */       int child; while ((child = (i << 1) + 1) < size) {
/*  71 */         int t = heap[child];
/*  72 */         int right = child + 1;
/*  73 */         if (right < size && c.compare(refArray[heap[right]], refArray[t]) < 0)
/*  74 */           t = heap[child = right]; 
/*  75 */         if (c.compare(E, refArray[t]) <= 0)
/*     */           break; 
/*  77 */         heap[i] = t;
/*  78 */         inv[heap[i]] = i;
/*  79 */         i = child;
/*     */       } 
/*  81 */     }  heap[i] = e;
/*  82 */     inv[e] = i;
/*  83 */     return i;
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
/*     */   public static <K> int upHeap(K[] refArray, int[] heap, int[] inv, int size, int i, Comparator<K> c) {
/* 108 */     assert i < size;
/* 109 */     int e = heap[i];
/* 110 */     K E = refArray[e];
/* 111 */     if (c == null) {
/* 112 */       while (i != 0) {
/* 113 */         int parent = i - 1 >>> 1;
/* 114 */         int t = heap[parent];
/* 115 */         if (((Comparable<K>)refArray[t]).compareTo(E) <= 0)
/*     */           break; 
/* 117 */         heap[i] = t;
/* 118 */         inv[heap[i]] = i;
/* 119 */         i = parent;
/*     */       } 
/*     */     } else {
/* 122 */       while (i != 0) {
/* 123 */         int parent = i - 1 >>> 1;
/* 124 */         int t = heap[parent];
/* 125 */         if (c.compare(refArray[t], E) <= 0)
/*     */           break; 
/* 127 */         heap[i] = t;
/* 128 */         inv[heap[i]] = i;
/* 129 */         i = parent;
/*     */       } 
/* 131 */     }  heap[i] = e;
/* 132 */     inv[e] = i;
/* 133 */     return i;
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
/*     */   public static <K> void makeHeap(K[] refArray, int offset, int length, int[] heap, int[] inv, Comparator<K> c) {
/* 153 */     ObjectArrays.ensureOffsetLength(refArray, offset, length);
/* 154 */     if (heap.length < length) {
/* 155 */       throw new IllegalArgumentException("The heap length (" + heap.length + ") is smaller than the number of elements (" + length + ")");
/*     */     }
/* 157 */     if (inv.length < refArray.length) {
/* 158 */       throw new IllegalArgumentException("The inversion array length (" + heap.length + ") is smaller than the length of the reference array (" + refArray.length + ")");
/*     */     }
/* 160 */     Arrays.fill(inv, 0, refArray.length, -1);
/* 161 */     int i = length;
/* 162 */     while (i-- != 0) {
/* 163 */       heap[i] = offset + i; inv[offset + i] = i;
/* 164 */     }  i = length >>> 1;
/* 165 */     while (i-- != 0) {
/* 166 */       downHeap(refArray, heap, inv, length, i, c);
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
/*     */   public static <K> void makeHeap(K[] refArray, int[] heap, int[] inv, int size, Comparator<K> c) {
/* 184 */     int i = size >>> 1;
/* 185 */     while (i-- != 0)
/* 186 */       downHeap(refArray, heap, inv, size, i, c); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectIndirectHeaps.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */