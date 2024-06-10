/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteHeapSemiIndirectPriorityQueue
/*     */   implements ByteIndirectPriorityQueue
/*     */ {
/*     */   protected final byte[] refArray;
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
/*     */   protected ByteComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray, int capacity, ByteComparator c) {
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
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray, int capacity) {
/*  68 */     this(refArray, capacity, (ByteComparator)null);
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
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray, ByteComparator c) {
/*  81 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray) {
/*  91 */     this(refArray, refArray.length, (ByteComparator)null);
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
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray, int[] a, int size, ByteComparator c) {
/* 112 */     this(refArray, 0, c);
/* 113 */     this.heap = a;
/* 114 */     this.size = size;
/* 115 */     ByteSemiIndirectHeaps.makeHeap(refArray, a, size, c);
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
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray, int[] a, ByteComparator c) {
/* 134 */     this(refArray, a, a.length, c);
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
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray, int[] a, int size) {
/* 152 */     this(refArray, a, size, null);
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
/*     */   public ByteHeapSemiIndirectPriorityQueue(byte[] refArray, int[] a) {
/* 168 */     this(refArray, a, a.length);
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
/* 180 */     if (index < 0)
/* 181 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 182 */     if (index >= this.refArray.length) {
/* 183 */       throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
/*     */     }
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 188 */     ensureElement(x);
/* 189 */     if (this.size == this.heap.length)
/* 190 */       this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 191 */     this.heap[this.size++] = x;
/* 192 */     ByteSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 196 */     if (this.size == 0)
/* 197 */       throw new NoSuchElementException(); 
/* 198 */     int result = this.heap[0];
/* 199 */     this.heap[0] = this.heap[--this.size];
/* 200 */     if (this.size != 0)
/* 201 */       ByteSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c); 
/* 202 */     return result;
/*     */   }
/*     */   
/*     */   public int first() {
/* 206 */     if (this.size == 0)
/* 207 */       throw new NoSuchElementException(); 
/* 208 */     return this.heap[0];
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
/* 221 */     ByteSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 226 */     ByteSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, this.c);
/*     */   }
/*     */   
/*     */   public int size() {
/* 230 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 234 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public void trim() {
/* 238 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public ByteComparator comparator() {
/* 242 */     return this.c;
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
/* 255 */     return (this.c == null) ? 
/* 256 */       ByteSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a) : 
/* 257 */       ByteSemiIndirectHeaps.front(this.refArray, this.heap, this.size, a, this.c);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 261 */     StringBuffer s = new StringBuffer();
/* 262 */     s.append("[");
/* 263 */     for (int i = 0; i < this.size; i++) {
/* 264 */       if (i != 0)
/* 265 */         s.append(", "); 
/* 266 */       s.append(this.refArray[this.heap[i]]);
/*     */     } 
/* 268 */     s.append("]");
/* 269 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteHeapSemiIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */