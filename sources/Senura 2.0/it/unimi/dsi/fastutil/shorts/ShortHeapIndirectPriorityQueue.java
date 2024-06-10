/*     */ package it.unimi.dsi.fastutil.shorts;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*     */ import java.util.Arrays;
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
/*     */ public class ShortHeapIndirectPriorityQueue
/*     */   extends ShortHeapSemiIndirectPriorityQueue
/*     */ {
/*     */   protected final int[] inv;
/*     */   
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int capacity, ShortComparator c) {
/*  49 */     super(refArray, capacity, c);
/*  50 */     if (capacity > 0)
/*  51 */       this.heap = new int[capacity]; 
/*  52 */     this.c = c;
/*  53 */     this.inv = new int[refArray.length];
/*  54 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int capacity) {
/*  65 */     this(refArray, capacity, (ShortComparator)null);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, ShortComparator c) {
/*  78 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray) {
/*  88 */     this(refArray, refArray.length, (ShortComparator)null);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a, int size, ShortComparator c) {
/* 110 */     this(refArray, 0, c);
/* 111 */     this.heap = a;
/* 112 */     this.size = size;
/* 113 */     int i = size;
/* 114 */     while (i-- != 0) {
/* 115 */       if (this.inv[a[i]] != -1)
/* 116 */         throw new IllegalArgumentException("Index " + a[i] + " appears twice in the heap"); 
/* 117 */       this.inv[a[i]] = i;
/*     */     } 
/* 119 */     ShortIndirectHeaps.makeHeap(refArray, a, this.inv, size, c);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a, ShortComparator c) {
/* 138 */     this(refArray, a, a.length, c);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a, int size) {
/* 156 */     this(refArray, a, size, (ShortComparator)null);
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
/*     */   public ShortHeapIndirectPriorityQueue(short[] refArray, int[] a) {
/* 172 */     this(refArray, a, a.length);
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 176 */     if (this.inv[x] >= 0)
/* 177 */       throw new IllegalArgumentException("Index " + x + " belongs to the queue"); 
/* 178 */     if (this.size == this.heap.length)
/* 179 */       this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 180 */     this.heap[this.size] = x; this.inv[x] = this.size++;
/* 181 */     ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public boolean contains(int index) {
/* 185 */     return (this.inv[index] >= 0);
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 189 */     if (this.size == 0)
/* 190 */       throw new NoSuchElementException(); 
/* 191 */     int result = this.heap[0];
/* 192 */     if (--this.size != 0) {
/* 193 */       this.heap[0] = this.heap[this.size]; this.inv[this.heap[this.size]] = 0;
/* 194 */     }  this.inv[result] = -1;
/* 195 */     if (this.size != 0)
/* 196 */       ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c); 
/* 197 */     return result;
/*     */   }
/*     */   
/*     */   public void changed() {
/* 201 */     ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, 0, this.c);
/*     */   }
/*     */   
/*     */   public void changed(int index) {
/* 205 */     int pos = this.inv[index];
/* 206 */     if (pos < 0)
/* 207 */       throw new IllegalArgumentException("Index " + index + " does not belong to the queue"); 
/* 208 */     int newPos = ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, pos, this.c);
/* 209 */     ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 214 */     ShortIndirectHeaps.makeHeap(this.refArray, this.heap, this.inv, this.size, this.c);
/*     */   }
/*     */   
/*     */   public boolean remove(int index) {
/* 218 */     int result = this.inv[index];
/* 219 */     if (result < 0)
/* 220 */       return false; 
/* 221 */     this.inv[index] = -1;
/* 222 */     if (result < --this.size) {
/* 223 */       this.heap[result] = this.heap[this.size]; this.inv[this.heap[this.size]] = result;
/* 224 */       int newPos = ShortIndirectHeaps.upHeap(this.refArray, this.heap, this.inv, this.size, result, this.c);
/* 225 */       ShortIndirectHeaps.downHeap(this.refArray, this.heap, this.inv, this.size, newPos, this.c);
/*     */     } 
/* 227 */     return true;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 231 */     this.size = 0;
/* 232 */     Arrays.fill(this.inv, -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortHeapIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */