/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public class CharHeapPriorityQueue
/*     */   implements CharPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  32 */   protected transient char[] heap = CharArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharComparator c;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(int capacity, CharComparator c) {
/*  48 */     if (capacity > 0)
/*  49 */       this.heap = new char[capacity]; 
/*  50 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(int capacity) {
/*  59 */     this(capacity, (CharComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue(CharComparator c) {
/*  69 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHeapPriorityQueue() {
/*  75 */     this(0, (CharComparator)null);
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
/*     */   public CharHeapPriorityQueue(char[] a, int size, CharComparator c) {
/*  94 */     this(c);
/*  95 */     this.heap = a;
/*  96 */     this.size = size;
/*  97 */     CharHeaps.makeHeap(a, size, c);
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
/*     */   public CharHeapPriorityQueue(char[] a, CharComparator c) {
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
/*     */   public CharHeapPriorityQueue(char[] a, int size) {
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
/*     */   public CharHeapPriorityQueue(char[] a) {
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
/*     */   public CharHeapPriorityQueue(CharCollection collection, CharComparator c) {
/* 161 */     this(collection.toCharArray(), c);
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
/*     */   public CharHeapPriorityQueue(CharCollection collection) {
/* 175 */     this(collection, (CharComparator)null);
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
/*     */   public CharHeapPriorityQueue(Collection<? extends Character> collection, CharComparator c) {
/* 191 */     this(collection.size(), c);
/* 192 */     Iterator<? extends Character> iterator = collection.iterator();
/* 193 */     int size = collection.size();
/* 194 */     for (int i = 0; i < size; i++) {
/* 195 */       this.heap[i] = ((Character)iterator.next()).charValue();
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
/*     */   public CharHeapPriorityQueue(Collection<? extends Character> collection) {
/* 208 */     this(collection, (CharComparator)null);
/*     */   }
/*     */   
/*     */   public void enqueue(char x) {
/* 212 */     if (this.size == this.heap.length)
/* 213 */       this.heap = CharArrays.grow(this.heap, this.size + 1); 
/* 214 */     this.heap[this.size++] = x;
/* 215 */     CharHeaps.upHeap(this.heap, this.size, this.size - 1, this.c);
/*     */   }
/*     */   
/*     */   public char dequeueChar() {
/* 219 */     if (this.size == 0)
/* 220 */       throw new NoSuchElementException(); 
/* 221 */     char result = this.heap[0];
/* 222 */     this.heap[0] = this.heap[--this.size];
/* 223 */     if (this.size != 0)
/* 224 */       CharHeaps.downHeap(this.heap, this.size, 0, this.c); 
/* 225 */     return result;
/*     */   }
/*     */   
/*     */   public char firstChar() {
/* 229 */     if (this.size == 0)
/* 230 */       throw new NoSuchElementException(); 
/* 231 */     return this.heap[0];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 235 */     CharHeaps.downHeap(this.heap, this.size, 0, this.c);
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
/* 250 */     this.heap = CharArrays.trim(this.heap, this.size);
/*     */   }
/*     */   
/*     */   public CharComparator comparator() {
/* 254 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 257 */     s.defaultWriteObject();
/* 258 */     s.writeInt(this.heap.length);
/* 259 */     for (int i = 0; i < this.size; i++)
/* 260 */       s.writeChar(this.heap[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 264 */     s.defaultReadObject();
/* 265 */     this.heap = new char[s.readInt()];
/* 266 */     for (int i = 0; i < this.size; i++)
/* 267 */       this.heap[i] = s.readChar(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharHeapPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */