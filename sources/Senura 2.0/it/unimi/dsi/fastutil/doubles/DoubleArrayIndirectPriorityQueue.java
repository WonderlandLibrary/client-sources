/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
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
/*     */ 
/*     */ public class DoubleArrayIndirectPriorityQueue
/*     */   implements DoubleIndirectPriorityQueue
/*     */ {
/*     */   protected double[] refArray;
/*  38 */   protected int[] array = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected DoubleComparator c;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int firstIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean firstIndexValid;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int capacity, DoubleComparator c) {
/*  60 */     if (capacity > 0)
/*  61 */       this.array = new int[capacity]; 
/*  62 */     this.refArray = refArray;
/*  63 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int capacity) {
/*  74 */     this(refArray, capacity, (DoubleComparator)null);
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, DoubleComparator c) {
/*  87 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray) {
/*  97 */     this(refArray, refArray.length, (DoubleComparator)null);
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a, int size, DoubleComparator c) {
/* 117 */     this(refArray, 0, c);
/* 118 */     this.array = a;
/* 119 */     this.size = size;
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a, DoubleComparator c) {
/* 136 */     this(refArray, a, a.length, c);
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a, int size) {
/* 152 */     this(refArray, a, size, null);
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
/*     */   public DoubleArrayIndirectPriorityQueue(double[] refArray, int[] a) {
/* 166 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 171 */     if (this.firstIndexValid)
/* 172 */       return this.firstIndex; 
/* 173 */     this.firstIndexValid = true;
/* 174 */     int i = this.size;
/* 175 */     int firstIndex = --i;
/* 176 */     double first = this.refArray[this.array[firstIndex]];
/* 177 */     if (this.c == null) {
/* 178 */       while (i-- != 0) {
/* 179 */         if (Double.compare(this.refArray[this.array[i]], first) < 0)
/* 180 */           first = this.refArray[this.array[firstIndex = i]]; 
/*     */       } 
/*     */     } else {
/* 183 */       while (i-- != 0) {
/* 184 */         if (this.c.compare(this.refArray[this.array[i]], first) < 0)
/* 185 */           first = this.refArray[this.array[firstIndex = i]]; 
/*     */       } 
/* 187 */     }  return this.firstIndex = firstIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   private int findLast() {
/* 192 */     int i = this.size;
/* 193 */     int lastIndex = --i;
/* 194 */     double last = this.refArray[this.array[lastIndex]];
/* 195 */     if (this.c == null)
/* 196 */     { while (i-- != 0) {
/* 197 */         if (Double.compare(last, this.refArray[this.array[i]]) < 0)
/* 198 */           last = this.refArray[this.array[lastIndex = i]]; 
/*     */       }  }
/* 200 */     else { while (i-- != 0) {
/* 201 */         if (this.c.compare(last, this.refArray[this.array[i]]) < 0)
/* 202 */           last = this.refArray[this.array[lastIndex = i]]; 
/*     */       }  }
/* 204 */      return lastIndex;
/*     */   }
/*     */   protected final void ensureNonEmpty() {
/* 207 */     if (this.size == 0) {
/* 208 */       throw new NoSuchElementException();
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
/*     */   protected void ensureElement(int index) {
/* 220 */     if (index < 0)
/* 221 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 222 */     if (index >= this.refArray.length) {
/* 223 */       throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
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
/*     */   
/*     */   public void enqueue(int x) {
/* 237 */     ensureElement(x);
/* 238 */     if (this.size == this.array.length)
/* 239 */       this.array = IntArrays.grow(this.array, this.size + 1); 
/* 240 */     if (this.firstIndexValid)
/* 241 */     { if (this.c == null) {
/* 242 */         if (Double.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0)
/* 243 */           this.firstIndex = this.size; 
/* 244 */       } else if (this.c.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) {
/* 245 */         this.firstIndex = this.size;
/*     */       }  }
/* 247 */     else { this.firstIndexValid = false; }
/* 248 */      this.array[this.size++] = x;
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 252 */     ensureNonEmpty();
/* 253 */     int firstIndex = findFirst();
/* 254 */     int result = this.array[firstIndex];
/* 255 */     if (--this.size != 0)
/* 256 */       System.arraycopy(this.array, firstIndex + 1, this.array, firstIndex, this.size - firstIndex); 
/* 257 */     this.firstIndexValid = false;
/* 258 */     return result;
/*     */   }
/*     */   
/*     */   public int first() {
/* 262 */     ensureNonEmpty();
/* 263 */     return this.array[findFirst()];
/*     */   }
/*     */   
/*     */   public int last() {
/* 267 */     ensureNonEmpty();
/* 268 */     return this.array[findLast()];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 272 */     ensureNonEmpty();
/* 273 */     this.firstIndexValid = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changed(int index) {
/* 284 */     ensureElement(index);
/* 285 */     if (index == this.firstIndex) {
/* 286 */       this.firstIndexValid = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void allChanged() {
/* 291 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public boolean remove(int index) {
/* 295 */     ensureElement(index);
/* 296 */     int[] a = this.array;
/* 297 */     int i = this.size; do {  }
/* 298 */     while (i-- != 0 && 
/* 299 */       a[i] != index);
/*     */     
/* 301 */     if (i < 0)
/* 302 */       return false; 
/* 303 */     this.firstIndexValid = false;
/* 304 */     if (--this.size != 0)
/* 305 */       System.arraycopy(a, i + 1, a, i, this.size - i); 
/* 306 */     return true;
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
/* 319 */     double top = this.refArray[this.array[findFirst()]];
/* 320 */     int i = this.size, c = 0;
/* 321 */     while (i-- != 0) {
/* 322 */       if (Double.doubleToLongBits(top) == Double.doubleToLongBits(this.refArray[this.array[i]]))
/* 323 */         a[c++] = this.array[i]; 
/* 324 */     }  return c;
/*     */   }
/*     */   
/*     */   public int size() {
/* 328 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 332 */     this.size = 0;
/* 333 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public void trim() {
/* 337 */     this.array = IntArrays.trim(this.array, this.size);
/*     */   }
/*     */   
/*     */   public DoubleComparator comparator() {
/* 341 */     return this.c;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 345 */     StringBuffer s = new StringBuffer();
/* 346 */     s.append("[");
/* 347 */     for (int i = 0; i < this.size; i++) {
/* 348 */       if (i != 0)
/* 349 */         s.append(", "); 
/* 350 */       s.append(this.refArray[this.array[i]]);
/*     */     } 
/* 352 */     s.append("]");
/* 353 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleArrayIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */