/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ public class IntHeapPriorityQueue
/*     */   implements IntPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   protected transient int[] heap = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected IntComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapPriorityQueue(int capacity, IntComparator c) {
/*  48 */     if (capacity > 0)
/*  49 */       this.heap = new int[capacity]; 
/*  50 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapPriorityQueue(int capacity) {
/*  59 */     this(capacity, (IntComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapPriorityQueue(IntComparator c) {
/*  69 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHeapPriorityQueue() {
/*  75 */     this(0, (IntComparator)null);
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
/*     */   public IntHeapPriorityQueue(int[] a, int size, IntComparator c) {
/*  94 */     this(c);
/*  95 */     this.heap = a;
/*  96 */     this.size = size;
/*  97 */     IntHeaps.makeHeap(a, size, c);
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
/*     */   public IntHeapPriorityQueue(int[] a, IntComparator c) {
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
/*     */   public IntHeapPriorityQueue(int[] a, int size) {
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
/*     */   public IntHeapPriorityQueue(int[] a) {
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
/*     */   public IntHeapPriorityQueue(IntCollection collection, IntComparator c) {
/* 161 */     this(collection.toIntArray(), c);
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
/*     */   public IntHeapPriorityQueue(IntCollection collection) {
/* 175 */     this(collection, (IntComparator)null);
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
/*     */   public IntHeapPriorityQueue(Collection<? extends Integer> collection, IntComparator c) {
/* 191 */     this(collection.size(), c);
/* 192 */     Iterator<? extends Integer> iterator = collection.iterator();
/* 193 */     int size = collection.size();
/* 194 */     for (int i = 0; i < size; i++) {
/* 195 */       this.heap[i] = ((Integer)iterator.next()).intValue();
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
/*     */   public IntHeapPriorityQueue(Collection<? extends Integer> collection) {
/* 208 */     this(collection, (IntComparator)null);
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 212 */     if (this.size == this.heap.length)
/* 213 */       this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 214 */     this.heap[this.size++] = x;
/* 215 */     IntHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public int dequeueInt() {
/* 219 */     if (this.size == 0)
/* 220 */       throw new NoSuchElementException(); 
/* 221 */     int result = this.heap[0];
/* 222 */     this.heap[0] = this.heap[--this.size];
/* 223 */     if (this.size != 0)
/* 224 */       IntHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 225 */     return result;
/*     */   }
/*     */   
/*     */   public int firstInt() {
/* 229 */     if (this.size == 0)
/* 230 */       throw new NoSuchElementException(); 
/* 231 */     return this.heap[0];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 235 */     IntHeaps.downHeap(this.heap, this.size, 0, this.c);
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
/* 250 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public IntComparator comparator() {
/* 254 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 257 */     s.defaultWriteObject();
/* 258 */     s.writeInt(this.heap.length);
/* 259 */     for (int i = 0; i < this.size; i++)
/* 260 */       s.writeInt(this.heap[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 264 */     s.defaultReadObject();
/* 265 */     this.heap = new int[s.readInt()];
/* 266 */     for (int i = 0; i < this.size; i++)
/* 267 */       this.heap[i] = s.readInt(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */