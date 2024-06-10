/*     */ package it.unimi.dsi.fastutil.floats;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class FloatArrayFIFOQueue
/*     */   implements FloatPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   public static final int INITIAL_CAPACITY = 4;
/*     */   protected transient float[] array;
/*     */   protected transient int length;
/*     */   protected transient int start;
/*     */   protected transient int end;
/*     */   
/*     */   public FloatArrayFIFOQueue(int capacity) {
/*  60 */     if (capacity < 0)
/*  61 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  62 */     this.array = new float[Math.max(1, capacity)];
/*  63 */     this.length = this.array.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatArrayFIFOQueue() {
/*  70 */     this(4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatComparator comparator() {
/*  79 */     return null;
/*     */   }
/*     */   
/*     */   public float dequeueFloat() {
/*  83 */     if (this.start == this.end)
/*  84 */       throw new NoSuchElementException(); 
/*  85 */     float t = this.array[this.start];
/*  86 */     if (++this.start == this.length)
/*  87 */       this.start = 0; 
/*  88 */     reduce();
/*  89 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float dequeueLastFloat() {
/*  99 */     if (this.start == this.end)
/* 100 */       throw new NoSuchElementException(); 
/* 101 */     if (this.end == 0)
/* 102 */       this.end = this.length; 
/* 103 */     float t = this.array[--this.end];
/* 104 */     reduce();
/* 105 */     return t;
/*     */   }
/*     */   
/*     */   private final void resize(int size, int newLength) {
/* 109 */     float[] newArray = new float[newLength];
/* 110 */     if (this.start >= this.end) {
/* 111 */       if (size != 0) {
/* 112 */         System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 113 */         System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
/*     */       } 
/*     */     } else {
/* 116 */       System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start);
/* 117 */     }  this.start = 0;
/* 118 */     this.end = size;
/* 119 */     this.array = newArray;
/* 120 */     this.length = newLength;
/*     */   }
/*     */   private final void expand() {
/* 123 */     resize(this.length, (int)Math.min(2147483639L, 2L * this.length));
/*     */   }
/*     */   private final void reduce() {
/* 126 */     int size = size();
/* 127 */     if (this.length > 4 && size <= this.length / 4)
/* 128 */       resize(size, this.length / 2); 
/*     */   }
/*     */   
/*     */   public void enqueue(float x) {
/* 132 */     this.array[this.end++] = x;
/* 133 */     if (this.end == this.length)
/* 134 */       this.end = 0; 
/* 135 */     if (this.end == this.start) {
/* 136 */       expand();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enqueueFirst(float x) {
/* 146 */     if (this.start == 0)
/* 147 */       this.start = this.length; 
/* 148 */     this.array[--this.start] = x;
/* 149 */     if (this.end == this.start)
/* 150 */       expand(); 
/*     */   }
/*     */   
/*     */   public float firstFloat() {
/* 154 */     if (this.start == this.end)
/* 155 */       throw new NoSuchElementException(); 
/* 156 */     return this.array[this.start];
/*     */   }
/*     */   
/*     */   public float lastFloat() {
/* 160 */     if (this.start == this.end)
/* 161 */       throw new NoSuchElementException(); 
/* 162 */     return this.array[((this.end == 0) ? this.length : this.end) - 1];
/*     */   }
/*     */   
/*     */   public void clear() {
/* 166 */     this.start = this.end = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void trim() {
/* 171 */     int size = size();
/* 172 */     float[] newArray = new float[size + 1];
/* 173 */     if (this.start <= this.end) {
/* 174 */       System.arraycopy(this.array, this.start, newArray, 0, this.end - this.start);
/*     */     } else {
/* 176 */       System.arraycopy(this.array, this.start, newArray, 0, this.length - this.start);
/* 177 */       System.arraycopy(this.array, 0, newArray, this.length - this.start, this.end);
/*     */     } 
/* 179 */     this.start = 0;
/* 180 */     this.length = (this.end = size) + 1;
/* 181 */     this.array = newArray;
/*     */   }
/*     */   
/*     */   public int size() {
/* 185 */     int apparentLength = this.end - this.start;
/* 186 */     return (apparentLength >= 0) ? apparentLength : (this.length + apparentLength);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 189 */     s.defaultWriteObject();
/* 190 */     int size = size();
/* 191 */     s.writeInt(size);
/* 192 */     for (int i = this.start; size-- != 0; ) {
/* 193 */       s.writeFloat(this.array[i++]);
/* 194 */       if (i == this.length)
/* 195 */         i = 0; 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 200 */     s.defaultReadObject();
/* 201 */     this.end = s.readInt();
/* 202 */     this.array = new float[this.length = HashCommon.nextPowerOfTwo(this.end + 1)];
/* 203 */     for (int i = 0; i < this.end; i++)
/* 204 */       this.array[i] = s.readFloat(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatArrayFIFOQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */