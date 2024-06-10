/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectHeapIndirectPriorityQueue<K>
/*     */   extends ObjectHeapSemiIndirectPriorityQueue<K>
/*     */ {
/*     */   protected final int[] inv;
/*     */   
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int capacity, Comparator<? super K> c) {
/*  50 */     super(refArray, capacity, c);
/*  51 */     if (capacity > 0)
/*  52 */       this.heap = new int[capacity]; 
/*  53 */     this.c = c;
/*  54 */     this.inv = new int[refArray.length];
/*  55 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int capacity) {
/*  66 */     this(refArray, capacity, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, Comparator<? super K> c) {
/*  79 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray) {
/*  89 */     this(refArray, refArray.length, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a, int size, Comparator<? super K> c) {
/* 111 */     this(refArray, 0, c);
/* 112 */     this.heap = a;
/* 113 */     this.size = size;
/* 114 */     int i = size;
/* 115 */     while (i-- != 0) {
/* 116 */       if (this.inv[a[i]] != -1)
/* 117 */         throw new IllegalArgumentException("Index " + a[i] + " appears twice in the heap"); 
/* 118 */       this.inv[a[i]] = i;
/*     */     } 
/* 120 */     ObjectIndirectHeaps.makeHeap(refArray, a, this.inv, size, (Comparator)c);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a, Comparator<? super K> c) {
/* 139 */     this(refArray, a, a.length, c);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a, int size) {
/* 157 */     this(refArray, a, size, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapIndirectPriorityQueue(K[] refArray, int[] a) {
/* 173 */     this(refArray, a, a.length);
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 177 */     if (this.inv[x] >= 0)
/* 178 */       throw new IllegalArgumentException("Index " + x + " belongs to the queue"); 
/* 179 */     if (this.size == this.heap.length)
/* 180 */       this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 181 */     this.heap[this.size] = x; this.inv[x] = this.size++;
/* 182 */     ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, (Comparator)this.c);
/*     */   }
/*     */   
/*     */   public boolean contains(int index) {
/* 186 */     return (this.inv[index] >= 0);
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 190 */     if (this.size == 0)
/* 191 */       throw new NoSuchElementException(); 
/* 192 */     int result = this.heap[0];
/* 193 */     if (--this.size != 0) {
/* 194 */       this.heap[0] = this.heap[this.size]; this.inv[this.heap[this.size]] = 0;
/* 195 */     }  this.inv[result] = -1;
/* 196 */     if (this.size != 0)
/* 197 */       ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, (Comparator)this.c); 
/* 198 */     return result;
/*     */   }
/*     */   
/*     */   public void changed() {
/* 202 */     ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, (Comparator)this.c);
/*     */   }
/*     */   
/*     */   public void changed(int index) {
/* 206 */     int pos = this.inv[index];
/* 207 */     if (pos < 0)
/* 208 */       throw new IllegalArgumentException("Index " + index + " does not belong to the queue"); 
/* 209 */     int newPos = ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, pos, (Comparator)this.c);
/* 210 */     ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 215 */     ObjectIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, (Comparator)this.c);
/*     */   }
/*     */   
/*     */   public boolean remove(int index) {
/* 219 */     int result = this.inv[index];
/* 220 */     if (result < 0)
/* 221 */       return false; 
/* 222 */     this.inv[index] = -1;
/* 223 */     if (result < --this.size) {
/* 224 */       this.heap[result] = this.heap[this.size]; this.inv[this.heap[this.size]] = result;
/* 225 */       int newPos = ObjectIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, result, (Comparator)this.c);
/* 226 */       ObjectIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, (Comparator)this.c);
/*     */     } 
/* 228 */     return true;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 232 */     this.size = 0;
/* 233 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectHeapIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */