/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
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
/*     */ public class LongArrayPriorityQueue
/*     */   implements LongPriorityQueue, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  35 */   protected transient long[] array = LongArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected LongComparator c;
/*     */ 
/*     */ 
/*     */   
/*     */   protected transient int firstIndex;
/*     */ 
/*     */   
/*     */   protected transient boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayPriorityQueue(int capacity, LongComparator c) {
/*  55 */     if (capacity > 0)
/*  56 */       this.array = new long[capacity]; 
/*  57 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayPriorityQueue(int capacity) {
/*  66 */     this(capacity, (LongComparator)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayPriorityQueue(LongComparator c) {
/*  76 */     this(0, c);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LongArrayPriorityQueue() {
/*  82 */     this(0, (LongComparator)null);
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
/*     */   public LongArrayPriorityQueue(long[] a, int size, LongComparator c) {
/*  99 */     this(c);
/* 100 */     this.array = a;
/* 101 */     this.size = size;
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
/*     */   public LongArrayPriorityQueue(long[] a, LongComparator c) {
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
/*     */   public LongArrayPriorityQueue(long[] a, int size) {
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
/*     */   public LongArrayPriorityQueue(long[] a) {
/* 142 */     this(a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 147 */     if (this.firstIndexValid)
/* 148 */       return this.firstIndex; 
/* 149 */     this.firstIndexValid = true;
/* 150 */     int i = this.size;
/* 151 */     int firstIndex = --i;
/* 152 */     long first = this.array[firstIndex];
/* 153 */     if (this.c == null)
/* 154 */     { while (i-- != 0) {
/* 155 */         if (this.array[i] < first)
/* 156 */           first = this.array[firstIndex = i]; 
/*     */       }  }
/* 158 */     else { while (i-- != 0) {
/* 159 */         if (this.c.compare(this.array[i], first) < 0)
/* 160 */           first = this.array[firstIndex = i]; 
/*     */       }  }
/* 162 */      return this.firstIndex = firstIndex;
/*     */   }
/*     */   private void ensureNonEmpty() {
/* 165 */     if (this.size == 0) {
/* 166 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */   
/*     */   public void enqueue(long x) {
/* 171 */     if (this.size == this.array.length)
/* 172 */       this.array = LongArrays.grow(this.array, this.size + 1); 
/* 173 */     if (this.firstIndexValid)
/* 174 */     { if (this.c == null) {
/* 175 */         if (x < this.array[this.firstIndex])
/* 176 */           this.firstIndex = this.size; 
/* 177 */       } else if (this.c.compare(x, this.array[this.firstIndex]) < 0) {
/* 178 */         this.firstIndex = this.size;
/*     */       }  }
/* 180 */     else { this.firstIndexValid = false; }
/* 181 */      this.array[this.size++] = x;
/*     */   }
/*     */   
/*     */   public long dequeueLong() {
/* 185 */     ensureNonEmpty();
/* 186 */     int first = findFirst();
/* 187 */     long result = this.array[first];
/* 188 */     System.arraycopy(this.array, first + 1, this.array, first, --this.size - first);
/* 189 */     this.firstIndexValid = false;
/* 190 */     return result;
/*     */   }
/*     */   
/*     */   public long firstLong() {
/* 194 */     ensureNonEmpty();
/* 195 */     return this.array[findFirst()];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 199 */     ensureNonEmpty();
/* 200 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public int size() {
/* 204 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 208 */     this.size = 0;
/* 209 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 215 */     this.array = LongArrays.trim(this.array, this.size);
/*     */   }
/*     */   
/*     */   public LongComparator comparator() {
/* 219 */     return this.c;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 222 */     s.defaultWriteObject();
/* 223 */     s.writeInt(this.array.length);
/* 224 */     for (int i = 0; i < this.size; i++)
/* 225 */       s.writeLong(this.array[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 229 */     s.defaultReadObject();
/* 230 */     this.array = new long[s.readInt()];
/* 231 */     for (int i = 0; i < this.size; i++)
/* 232 */       this.array[i] = s.readLong(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongArrayPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */