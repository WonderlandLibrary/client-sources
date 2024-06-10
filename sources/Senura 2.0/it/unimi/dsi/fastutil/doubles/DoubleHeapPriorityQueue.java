/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public class DoubleHeapPriorityQueue
/*     */   implements DoublePriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   protected transient double[] heap = DoubleArrays.EMPTY_ARRAY;
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
/*     */   public DoubleHeapPriorityQueue(int capacity, DoubleComparator c) {
/*  48 */     if (capacity > 0)
/*  49 */       this.heap = new double[capacity]; 
/*  50 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleHeapPriorityQueue(int capacity) {
/*  59 */     this(capacity, (DoubleComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleHeapPriorityQueue(DoubleComparator c) {
/*  69 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleHeapPriorityQueue() {
/*  75 */     this(0, (DoubleComparator)null);
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
/*     */   public DoubleHeapPriorityQueue(double[] a, int size, DoubleComparator c) {
/*  94 */     this(c);
/*  95 */     this.heap = a;
/*  96 */     this.size = size;
/*  97 */     DoubleHeaps.makeHeap(a, size, c);
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
/*     */   public DoubleHeapPriorityQueue(double[] a, DoubleComparator c) {
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
/*     */   public DoubleHeapPriorityQueue(double[] a, int size) {
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
/*     */   public DoubleHeapPriorityQueue(double[] a) {
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
/*     */   public DoubleHeapPriorityQueue(DoubleCollection collection, DoubleComparator c) {
/* 161 */     this(collection.toDoubleArray(), c);
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
/*     */   public DoubleHeapPriorityQueue(DoubleCollection collection) {
/* 175 */     this(collection, (DoubleComparator)null);
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
/*     */   public DoubleHeapPriorityQueue(Collection<? extends Double> collection, DoubleComparator c) {
/* 191 */     this(collection.size(), c);
/* 192 */     Iterator<? extends Double> iterator = collection.iterator();
/* 193 */     int size = collection.size();
/* 194 */     for (int i = 0; i < size; i++) {
/* 195 */       this.heap[i] = ((Double)iterator.next()).doubleValue();
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
/*     */   public DoubleHeapPriorityQueue(Collection<? extends Double> collection) {
/* 208 */     this(collection, (DoubleComparator)null);
/*     */   }
/*     */   
/*     */   public void enqueue(double x) {
/* 212 */     if (this.size == this.heap.length)
/* 213 */       this.heap = DoubleArrays.grow(this.heap, this.size + 1); 
/* 214 */     this.heap[this.size++] = x;
/* 215 */     DoubleHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public double dequeueDouble() {
/* 219 */     if (this.size == 0)
/* 220 */       throw new NoSuchElementException(); 
/* 221 */     double result = this.heap[0];
/* 222 */     this.heap[0] = this.heap[--this.size];
/* 223 */     if (this.size != 0)
/* 224 */       DoubleHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 225 */     return result;
/*     */   }
/*     */   
/*     */   public double firstDouble() {
/* 229 */     if (this.size == 0)
/* 230 */       throw new NoSuchElementException(); 
/* 231 */     return this.heap[0];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 235 */     DoubleHeaps.downHeap(this.heap, this.size, 0, this.c);
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
/* 250 */     this.heap = DoubleArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public DoubleComparator comparator() {
/* 254 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 257 */     s.defaultWriteObject();
/* 258 */     s.writeInt(this.heap.length);
/* 259 */     for (int i = 0; i < this.size; i++)
/* 260 */       s.writeDouble(this.heap[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 264 */     s.defaultReadObject();
/* 265 */     this.heap = new double[s.readInt()];
/* 266 */     for (int i = 0; i < this.size; i++)
/* 267 */       this.heap[i] = s.readDouble(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */