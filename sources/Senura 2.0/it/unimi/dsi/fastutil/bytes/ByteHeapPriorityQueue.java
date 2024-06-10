/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteHeapPriorityQueue
/*     */   implements BytePriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   protected transient byte[] heap = ByteArrays.EMPTY_ARRAY;
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
/*     */   public ByteHeapPriorityQueue(int capacity, ByteComparator c) {
/*  48 */     if (capacity > 0)
/*  49 */       this.heap = new byte[capacity]; 
/*  50 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteHeapPriorityQueue(int capacity) {
/*  59 */     this(capacity, (ByteComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteHeapPriorityQueue(ByteComparator c) {
/*  69 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteHeapPriorityQueue() {
/*  75 */     this(0, (ByteComparator)null);
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
/*     */   public ByteHeapPriorityQueue(byte[] a, int size, ByteComparator c) {
/*  94 */     this(c);
/*  95 */     this.heap = a;
/*  96 */     this.size = size;
/*  97 */     ByteHeaps.makeHeap(a, size, c);
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
/*     */   public ByteHeapPriorityQueue(byte[] a, ByteComparator c) {
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
/*     */   public ByteHeapPriorityQueue(byte[] a, int size) {
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
/*     */   public ByteHeapPriorityQueue(byte[] a) {
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
/*     */   public ByteHeapPriorityQueue(ByteCollection collection, ByteComparator c) {
/* 161 */     this(collection.toByteArray(), c);
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
/*     */   public ByteHeapPriorityQueue(ByteCollection collection) {
/* 175 */     this(collection, (ByteComparator)null);
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
/*     */   public ByteHeapPriorityQueue(Collection<? extends Byte> collection, ByteComparator c) {
/* 191 */     this(collection.size(), c);
/* 192 */     Iterator<? extends Byte> iterator = collection.iterator();
/* 193 */     int size = collection.size();
/* 194 */     for (int i = 0; i < size; i++) {
/* 195 */       this.heap[i] = ((Byte)iterator.next()).byteValue();
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
/*     */   public ByteHeapPriorityQueue(Collection<? extends Byte> collection) {
/* 208 */     this(collection, (ByteComparator)null);
/*     */   }
/*     */   
/*     */   public void enqueue(byte x) {
/* 212 */     if (this.size == this.heap.length)
/* 213 */       this.heap = ByteArrays.grow(this.heap, this.size + 1); 
/* 214 */     this.heap[this.size++] = x;
/* 215 */     ByteHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public byte dequeueByte() {
/* 219 */     if (this.size == 0)
/* 220 */       throw new NoSuchElementException(); 
/* 221 */     byte result = this.heap[0];
/* 222 */     this.heap[0] = this.heap[--this.size];
/* 223 */     if (this.size != 0)
/* 224 */       ByteHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 225 */     return result;
/*     */   }
/*     */   
/*     */   public byte firstByte() {
/* 229 */     if (this.size == 0)
/* 230 */       throw new NoSuchElementException(); 
/* 231 */     return this.heap[0];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 235 */     ByteHeaps.downHeap(this.heap, this.size, 0, this.c);
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
/* 250 */     this.heap = ByteArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public ByteComparator comparator() {
/* 254 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 257 */     s.defaultWriteObject();
/* 258 */     s.writeInt(this.heap.length);
/* 259 */     for (int i = 0; i < this.size; i++)
/* 260 */       s.writeByte(this.heap[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 264 */     s.defaultReadObject();
/* 265 */     this.heap = new byte[s.readInt()];
/* 266 */     for (int i = 0; i < this.size; i++)
/* 267 */       this.heap[i] = s.readByte(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */