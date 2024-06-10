/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.PriorityQueue;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ public class ObjectHeapPriorityQueue<K>
/*     */   implements PriorityQueue<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  33 */   protected transient K[] heap = (K[])ObjectArrays.EMPTY_ARRAY;
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
/*     */   public ObjectHeapPriorityQueue(int capacity, Comparator<? super K> c) {
/*  50 */     if (capacity > 0)
/*  51 */       this.heap = (K[])new Object[capacity]; 
/*  52 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapPriorityQueue(int capacity) {
/*  61 */     this(capacity, (Comparator<? super K>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapPriorityQueue(Comparator<? super K> c) {
/*  71 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectHeapPriorityQueue() {
/*  77 */     this(0, (Comparator<? super K>)null);
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
/*     */   public ObjectHeapPriorityQueue(K[] a, int size, Comparator<? super K> c) {
/*  96 */     this(c);
/*  97 */     this.heap = a;
/*  98 */     this.size = size;
/*  99 */     ObjectHeaps.makeHeap(a, size, (Comparator)c);
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
/*     */   public ObjectHeapPriorityQueue(K[] a, Comparator<? super K> c) {
/* 116 */     this(a, a.length, c);
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
/*     */   public ObjectHeapPriorityQueue(K[] a, int size) {
/* 132 */     this(a, size, null);
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
/*     */   public ObjectHeapPriorityQueue(K[] a) {
/* 146 */     this(a, a.length);
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
/*     */   public ObjectHeapPriorityQueue(Collection<? extends K> collection, Comparator<? super K> c) {
/* 163 */     this((K[])collection.toArray(), c);
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
/*     */   public ObjectHeapPriorityQueue(Collection<? extends K> collection) {
/* 176 */     this(collection, (Comparator<? super K>)null);
/*     */   }
/*     */   
/*     */   public void enqueue(K x) {
/* 180 */     if (this.size == this.heap.length)
/* 181 */       this.heap = ObjectArrays.grow(this.heap, this.size + 1); 
/* 182 */     this.heap[this.size++] = x;
/* 183 */     ObjectHeaps.upHeap(this.heap, this.size, this.size - 1, (Comparator)this.c);
/*     */   }
/*     */   
/*     */   public K dequeue() {
/* 187 */     if (this.size == 0)
/* 188 */       throw new NoSuchElementException(); 
/* 189 */     K result = this.heap[0];
/* 190 */     this.heap[0] = this.heap[--this.size];
/* 191 */     this.heap[this.size] = null;
/* 192 */     if (this.size != 0)
/* 193 */       ObjectHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 194 */     return result;
/*     */   }
/*     */   
/*     */   public K first() {
/* 198 */     if (this.size == 0)
/* 199 */       throw new NoSuchElementException(); 
/* 200 */     return this.heap[0];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 204 */     ObjectHeaps.downHeap(this.heap, this.size, 0, this.c);
/*     */   }
/*     */   
/*     */   public int size() {
/* 208 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 212 */     Arrays.fill((Object[])this.heap, 0, this.size, (Object)null);
/* 213 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 220 */     this.heap = ObjectArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 224 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 227 */     s.defaultWriteObject();
/* 228 */     s.writeInt(this.heap.length);
/* 229 */     for (int i = 0; i < this.size; i++)
/* 230 */       s.writeObject(this.heap[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 234 */     s.defaultReadObject();
/* 235 */     this.heap = (K[])new Object[s.readInt()];
/* 236 */     for (int i = 0; i < this.size; i++)
/* 237 */       this.heap[i] = (K)s.readObject(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */