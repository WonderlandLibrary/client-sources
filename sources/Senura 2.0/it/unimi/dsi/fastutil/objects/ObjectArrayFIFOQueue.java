/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.PriorityQueue;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectArrayFIFOQueue<K>
/*     */   implements PriorityQueue<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int INITIAL_CAPACITY = 4;
/*     */   protected transient K[] array;
/*     */   protected transient int length;
/*     */   protected transient int start;
/*     */   protected transient int end;
/*     */   
/*     */   public ObjectArrayFIFOQueue(int capacity) {
/*  62 */     if (capacity < 0)
/*  63 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  64 */     this.array = (K[])new Object[Math.max(1, capacity)];
/*  65 */     this.length = this.array.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayFIFOQueue() {
/*  72 */     this(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Comparator<? super K> comparator() {
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   public K dequeue() {
/*  85 */     if (this.start == this.end)
/*  86 */       throw new NoSuchElementException(); 
/*  87 */     K t = this.array[this.start];
/*  88 */     this.array[this.start] = null;
/*  89 */     if (++this.start == this.length)
/*  90 */       this.start = 0; 
/*  91 */     reduce();
/*  92 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K dequeueLast() {
/* 102 */     if (this.start == this.end)
/* 103 */       throw new NoSuchElementException(); 
/* 104 */     if (this.end == 0)
/* 105 */       this.end = this.length; 
/* 106 */     K t = this.array[--this.end];
/* 107 */     this.array[this.end] = null;
/* 108 */     reduce();
/* 109 */     return t;
/*     */   }
/*     */   
/*     */   private final void resize(int size, int newLength) {
/* 113 */     K[] newArray = (K[])new Object[newLength];
/* 114 */     if (this.start >= this.end) {
/* 115 */       if (size != 0) {
/* 116 */         System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 117 */         System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
/*     */       } 
/*     */     } else {
/* 120 */       System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start);
/* 121 */     }  this.start = 0;
/* 122 */     this.end = size;
/* 123 */     this.array = newArray;
/* 124 */     this.length = newLength;
/*     */   }
/*     */   private final void expand() {
/* 127 */     resize(this.length, (int)Math.min(2147483639L, 2L * this.length));
/*     */   }
/*     */   private final void reduce() {
/* 130 */     int size = size();
/* 131 */     if (this.length > 4 && size <= this.length / 4)
/* 132 */       resize(size, this.length / 2); 
/*     */   }
/*     */   
/*     */   public void enqueue(K x) {
/* 136 */     this.array[this.end++] = x;
/* 137 */     if (this.end == this.length)
/* 138 */       this.end = 0; 
/* 139 */     if (this.end == this.start) {
/* 140 */       expand();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enqueueFirst(K x) {
/* 150 */     if (this.start == 0)
/* 151 */       this.start = this.length; 
/* 152 */     this.array[--this.start] = x;
/* 153 */     if (this.end == this.start)
/* 154 */       expand(); 
/*     */   }
/*     */   
/*     */   public K first() {
/* 158 */     if (this.start == this.end)
/* 159 */       throw new NoSuchElementException(); 
/* 160 */     return this.array[this.start];
/*     */   }
/*     */   
/*     */   public K last() {
/* 164 */     if (this.start == this.end)
/* 165 */       throw new NoSuchElementException(); 
/* 166 */     return this.array[((this.end == 0) ? this.length : this.end) - 1];
/*     */   }
/*     */   
/*     */   public void clear() {
/* 170 */     if (this.start <= this.end) {
/* 171 */       Arrays.fill((Object[])this.array, this.start, this.end, (Object)null);
/*     */     } else {
/* 173 */       Arrays.fill((Object[])this.array, this.start, this.length, (Object)null);
/* 174 */       Arrays.fill((Object[])this.array, 0, this.end, (Object)null);
/*     */     } 
/* 176 */     this.start = this.end = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 181 */     int size = size();
/* 182 */     K[] newArray = (K[])new Object[size + 1];
/* 183 */     if (this.start <= this.end) {
/* 184 */       System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start);
/*     */     } else {
/* 186 */       System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 187 */       System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
/*     */     } 
/* 189 */     this.start = 0;
/* 190 */     this.length = (this.end = size) + 1;
/* 191 */     this.array = newArray;
/*     */   }
/*     */   
/*     */   public int size() {
/* 195 */     int apparentLength = this.end - this.start;
/* 196 */     return (apparentLength >= 0) ? apparentLength : (this.length + apparentLength);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 199 */     s.defaultWriteObject();
/* 200 */     int size = size();
/* 201 */     s.writeInt(size);
/* 202 */     for (int i = this.start; size-- != 0; ) {
/* 203 */       s.writeObject(this.array[i++]);
/* 204 */       if (i == this.length)
/* 205 */         i = 0; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 210 */     s.defaultReadObject();
/* 211 */     this.end = s.readInt();
/* 212 */     this.array = (K[])new Object[this.length = HashCommon.nextPowerOfTwo(this.end + 1)];
/* 213 */     for (int i = 0; i < this.end; i++)
/* 214 */       this.array[i] = (K)s.readObject(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayFIFOQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */