/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.IndirectPriorityQueue;
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
/*     */ 
/*     */ public class ObjectHeapSemiIndirectPriorityQueue<K>
/*     */   implements IndirectPriorityQueue<K>
/*     */ {
/*     */   protected final K[] refArray;
/*  38 */   protected int[] heap = IntArrays.EMPTY_ARRAY;
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
/*     */   protected Comparator<? super K> c;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int capacity, Comparator<? super K> c) {
/*  56 */     if (capacity > 0)
/*  57 */       this.heap = new int[capacity]; 
/*  58 */     this.refArray = refArray;
/*  59 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int capacity) {
/*  70 */     this(refArray, capacity, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, Comparator<? super K> c) {
/*  83 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray) {
/*  93 */     this(refArray, refArray.length, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a, int size, Comparator<? super K> c) {
/* 115 */     this(refArray, 0, c);
/* 116 */     this.heap = a;
/* 117 */     this.size = size;
/* 118 */     ObjectSemiIndirectHeaps.makeHeap(refArray, a, size, (Comparator)c);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a, Comparator<? super K> c) {
/* 137 */     this(refArray, a, a.length, c);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a, int size) {
/* 155 */     this(refArray, a, size, null);
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
/*     */   public ObjectHeapSemiIndirectPriorityQueue(K[] refArray, int[] a) {
/* 171 */     this(refArray, a, a.length);
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
/* 183 */     if (index < 0)
/* 184 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 185 */     if (index >= this.refArray.length) {
/* 186 */       throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
/*     */     }
/*     */   }
/*     */   
/*     */   public void enqueue(int x) {
/* 191 */     ensureElement(x);
/* 192 */     if (this.size == this.heap.length)
/* 193 */       this.heap = IntArrays.grow(this.heap, this.size + 1); 
/* 194 */     this.heap[this.size++] = x;
/* 195 */     ObjectSemiIndirectHeaps.upHeap(this.refArray, this.heap, this.size, this.size - 1, (Comparator)this.c);
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 199 */     if (this.size == 0)
/* 200 */       throw new NoSuchElementException(); 
/* 201 */     int result = this.heap[0];
/* 202 */     this.heap[0] = this.heap[--this.size];
/* 203 */     if (this.size != 0)
/* 204 */       ObjectSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, (Comparator)this.c); 
/* 205 */     return result;
/*     */   }
/*     */   
/*     */   public int first() {
/* 209 */     if (this.size == 0)
/* 210 */       throw new NoSuchElementException(); 
/* 211 */     return this.heap[0];
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
/* 224 */     ObjectSemiIndirectHeaps.downHeap(this.refArray, this.heap, this.size, 0, (Comparator)this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 229 */     ObjectSemiIndirectHeaps.makeHeap(this.refArray, this.heap, this.size, (Comparator)this.c);
/*     */   }
/*     */   
/*     */   public int size() {
/* 233 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 237 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public void trim() {
/* 241 */     this.heap = IntArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 245 */     return this.c;
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
/* 258 */     return (this.c == null) ? 
/* 259 */       ObjectSemiIndirectHeaps.<K>front(this.refArray, this.heap, this.size, a) : 
/* 260 */       ObjectSemiIndirectHeaps.<K>front(this.refArray, this.heap, this.size, a, (Comparator)this.c);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 264 */     StringBuffer s = new StringBuffer();
/* 265 */     s.append("[");
/* 266 */     for (int i = 0; i < this.size; i++) {
/* 267 */       if (i != 0)
/* 268 */         s.append(", "); 
/* 269 */       s.append(this.refArray[this.heap[i]]);
/*     */     } 
/* 271 */     s.append("]");
/* 272 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectHeapSemiIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */