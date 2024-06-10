/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
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
/*     */ public class LongHeapPriorityQueue
/*     */   implements LongPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   protected transient long[] heap = LongArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LongComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue(int capacity, LongComparator c) {
/*  48 */     if (capacity > 0)
/*  49 */       this.heap = new long[capacity]; 
/*  50 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue(int capacity) {
/*  59 */     this(capacity, (LongComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue(LongComparator c) {
/*  69 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongHeapPriorityQueue() {
/*  75 */     this(0, (LongComparator)null);
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
/*     */   public LongHeapPriorityQueue(long[] a, int size, LongComparator c) {
/*  94 */     this(c);
/*  95 */     this.heap = a;
/*  96 */     this.size = size;
/*  97 */     LongHeaps.makeHeap(a, size, c);
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
/*     */   public LongHeapPriorityQueue(long[] a, LongComparator c) {
/* 114 */     this(a, a.length, c);
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
/*     */   public LongHeapPriorityQueue(long[] a, int size) {
/* 130 */     this(a, size, null);
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
/*     */   public LongHeapPriorityQueue(long[] a) {
/* 144 */     this(a, a.length);
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
/*     */   public LongHeapPriorityQueue(LongCollection collection, LongComparator c) {
/* 161 */     this(collection.toLongArray(), c);
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
/*     */   public LongHeapPriorityQueue(LongCollection collection) {
/* 175 */     this(collection, (LongComparator)null);
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
/*     */   public LongHeapPriorityQueue(Collection<? extends Long> collection, LongComparator c) {
/* 191 */     this(collection.size(), c);
/* 192 */     Iterator<? extends Long> iterator = collection.iterator();
/* 193 */     int size = collection.size();
/* 194 */     for (int i = 0; i < size; i++) {
/* 195 */       this.heap[i] = ((Long)iterator.next()).longValue();
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
/*     */   public LongHeapPriorityQueue(Collection<? extends Long> collection) {
/* 208 */     this(collection, (LongComparator)null);
/*     */   }
/*     */   
/*     */   public void enqueue(long x) {
/* 212 */     if (this.size == this.heap.length)
/* 213 */       this.heap = LongArrays.grow(this.heap, this.size + 1); 
/* 214 */     this.heap[this.size++] = x;
/* 215 */     LongHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public long dequeueLong() {
/* 219 */     if (this.size == 0)
/* 220 */       throw new NoSuchElementException(); 
/* 221 */     long result = this.heap[0];
/* 222 */     this.heap[0] = this.heap[--this.size];
/* 223 */     if (this.size != 0)
/* 224 */       LongHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 225 */     return result;
/*     */   }
/*     */   
/*     */   public long firstLong() {
/* 229 */     if (this.size == 0)
/* 230 */       throw new NoSuchElementException(); 
/* 231 */     return this.heap[0];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 235 */     LongHeaps.downHeap(this.heap, this.size, 0, this.c);
/*     */   }
/*     */   
/*     */   public int size() {
/* 239 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 243 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 250 */     this.heap = LongArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public LongComparator comparator() {
/* 254 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 257 */     s.defaultWriteObject();
/* 258 */     s.writeInt(this.heap.length);
/* 259 */     for (int i = 0; i < this.size; i++)
/* 260 */       s.writeLong(this.heap[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 264 */     s.defaultReadObject();
/* 265 */     this.heap = new long[s.readInt()];
/* 266 */     for (int i = 0; i < this.size; i++)
/* 267 */       this.heap[i] = s.readLong(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */