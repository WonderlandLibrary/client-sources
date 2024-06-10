/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrays;
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
/*     */ public class DoubleHeapSemiIndirectPriorityQueue
/*     */   implements DoubleIndirectPriorityQueue
/*     */ {
/*     */   protected final double[] refArray;
/*  36 */   protected int[] heap = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DoubleComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int capacity, DoubleComparator c) {
/*  54 */     if (capacity > 0)
/*  55 */       this.heap = new int[capacity]; 
/*  56 */     this.refArray = refArray;
/*  57 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int capacity) {
/*  68 */     this(refArray, capacity, (DoubleComparator)null);
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
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, DoubleComparator c) {
/*  81 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray) {
/*  91 */     this(refArray, refArray.length, (DoubleComparator)null);
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
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a, int size, DoubleComparator c) {
/* 113 */     this(refArray, 0, c);
/* 114 */     this.heap = a;
/* 115 */     this.size = size;
/* 116 */     DoubleSemiIndirectHeaps.makeHeap(refArray, a, size, c);
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
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a, DoubleComparator c) {
/* 135 */     this(refArray, a, a.length, c);
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
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a, int size) {
/* 153 */     this(refArray, a, size, null);
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
/*     */   public DoubleHeapSemiIndirectPriorityQueue(double[] refArray, int[] a) {
/* 169 */     this(refArray, a, a.length);
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
/*     */   protected void ensureElement(int index) {
/* 181 */     if (index < 0)
/* 182 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 183 */     if (index >= this.refArray.length) {
/* 184 */       throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
/*     */     }
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 189 */     ensureElement(x);
/* 190 */     if (this.size == this.heap.length)
/* 191 */       this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 192 */     this.heap[this.size++] = x;
/* 193 */     DoubleSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 197 */     if (this.size == 0)
/* 198 */       throw new NoSuchElementException(); 
/* 199 */     int result = this.heap[0];
/* 200 */     this.heap[0] = this.heap[--this.size];
/* 201 */     if (this.size != 0)
/* 202 */       DoubleSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c); 
/* 203 */     return result;
/*     */   }
/*     */   
/*     */   public int first() {
/* 207 */     if (this.size == 0)
/* 208 */       throw new NoSuchElementException(); 
/* 209 */     return this.heap[0];
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
/*     */   public void changed() {
/* 222 */     DoubleSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 227 */     DoubleSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
/*     */   }
/*     */   
/*     */   public int size() {
/* 231 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 235 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public void trim() {
/* 239 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public DoubleComparator comparator() {
/* 243 */     return this.c;
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
/*     */   public int front(int[] a) {
/* 256 */     return (this.c == null) ? 
/* 257 */       DoubleSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a) : 
/* 258 */       DoubleSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a, this.c);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 262 */     StringBuffer s = new StringBuffer();
/* 263 */     s.append("[");
/* 264 */     for (int i = 0; i < this.size; i++) {
/* 265 */       if (i != 0)
/* 266 */         s.append(", "); 
/* 267 */       s.append(this.refArray[this.heap[i]]);
/*     */     } 
/* 269 */     s.append("]");
/* 270 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleHeapSemiIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */