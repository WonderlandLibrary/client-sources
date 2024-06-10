/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
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
/*     */ public class IntArrayIndirectPriorityQueue
/*     */   implements IntIndirectPriorityQueue
/*     */ {
/*     */   protected int[] refArray;
/*  38 */   protected int[] array = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected IntComparator c;
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int capacity, IntComparator c) {
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int capacity) {
/*  74 */     this(refArray, capacity, (IntComparator)null);
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, IntComparator c) {
/*  87 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray) {
/*  97 */     this(refArray, refArray.length, (IntComparator)null);
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a, int size, IntComparator c) {
/* 116 */     this(refArray, 0, c);
/* 117 */     this.array = a;
/* 118 */     this.size = size;
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a, IntComparator c) {
/* 135 */     this(refArray, a, a.length, c);
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a, int size) {
/* 151 */     this(refArray, a, size, null);
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
/*     */   public IntArrayIndirectPriorityQueue(int[] refArray, int[] a) {
/* 165 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 170 */     if (this.firstIndexValid)
/* 171 */       return this.firstIndex; 
/* 172 */     this.firstIndexValid = true;
/* 173 */     int i = this.size;
/* 174 */     int firstIndex = --i;
/* 175 */     int first = this.refArray[this.array[firstIndex]];
/* 176 */     if (this.c == null) {
/* 177 */       while (i-- != 0) {
/* 178 */         if (this.refArray[this.array[i]] < first)
/* 179 */           first = this.refArray[this.array[firstIndex = i]]; 
/*     */       } 
/*     */     } else {
/* 182 */       while (i-- != 0) {
/* 183 */         if (this.c.compare(this.refArray[this.array[i]], first) < 0)
/* 184 */           first = this.refArray[this.array[firstIndex = i]]; 
/*     */       } 
/* 186 */     }  return this.firstIndex = firstIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   private int findLast() {
/* 191 */     int i = this.size;
/* 192 */     int lastIndex = --i;
/* 193 */     int last = this.refArray[this.array[lastIndex]];
/* 194 */     if (this.c == null)
/* 195 */     { while (i-- != 0) {
/* 196 */         if (last < this.refArray[this.array[i]])
/* 197 */           last = this.refArray[this.array[lastIndex = i]]; 
/*     */       }  }
/* 199 */     else { while (i-- != 0) {
/* 200 */         if (this.c.compare(last, this.refArray[this.array[i]]) < 0)
/* 201 */           last = this.refArray[this.array[lastIndex = i]]; 
/*     */       }  }
/* 203 */      return lastIndex;
/*     */   }
/*     */   protected final void ensureNonEmpty() {
/* 206 */     if (this.size == 0) {
/* 207 */       throw new NoSuchElementException();
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
/* 219 */     if (index < 0)
/* 220 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 221 */     if (index >= this.refArray.length) {
/* 222 */       throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
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
/* 236 */     ensureElement(x);
/* 237 */     if (this.size == this.array.length)
/* 238 */       this.array = IntArrays.grow(this.array, this.size + 1); 
/* 239 */     if (this.firstIndexValid)
/* 240 */     { if (this.c == null) {
/* 241 */         if (this.refArray[x] < this.refArray[this.array[this.firstIndex]])
/* 242 */           this.firstIndex = this.size; 
/* 243 */       } else if (this.c.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) {
/* 244 */         this.firstIndex = this.size;
/*     */       }  }
/* 246 */     else { this.firstIndexValid = false; }
/* 247 */      this.array[this.size++] = x;
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 251 */     ensureNonEmpty();
/* 252 */     int firstIndex = findFirst();
/* 253 */     int result = this.array[firstIndex];
/* 254 */     if (--this.size != 0)
/* 255 */       System.arraycopy(this.array, firstIndex + 1, this.array, firstIndex, this.size - firstIndex); 
/* 256 */     this.firstIndexValid = false;
/* 257 */     return result;
/*     */   }
/*     */   
/*     */   public int first() {
/* 261 */     ensureNonEmpty();
/* 262 */     return this.array[findFirst()];
/*     */   }
/*     */   
/*     */   public int last() {
/* 266 */     ensureNonEmpty();
/* 267 */     return this.array[findLast()];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 271 */     ensureNonEmpty();
/* 272 */     this.firstIndexValid = false;
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
/* 283 */     ensureElement(index);
/* 284 */     if (index == this.firstIndex) {
/* 285 */       this.firstIndexValid = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void allChanged() {
/* 290 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public boolean remove(int index) {
/* 294 */     ensureElement(index);
/* 295 */     int[] a = this.array;
/* 296 */     int i = this.size; do {  }
/* 297 */     while (i-- != 0 && 
/* 298 */       a[i] != index);
/*     */     
/* 300 */     if (i < 0)
/* 301 */       return false; 
/* 302 */     this.firstIndexValid = false;
/* 303 */     if (--this.size != 0)
/* 304 */       System.arraycopy(a, i + 1, a, i, this.size - i); 
/* 305 */     return true;
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
/* 318 */     int top = this.refArray[this.array[findFirst()]];
/* 319 */     int i = this.size, c = 0;
/* 320 */     while (i-- != 0) {
/* 321 */       if (top == this.refArray[this.array[i]])
/* 322 */         a[c++] = this.array[i]; 
/* 323 */     }  return c;
/*     */   }
/*     */   
/*     */   public int size() {
/* 327 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 331 */     this.size = 0;
/* 332 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public void trim() {
/* 336 */     this.array = IntArrays.trim(this.array, this.size);
/*     */   }
/*     */   
/*     */   public IntComparator comparator() {
/* 340 */     return this.c;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 344 */     StringBuffer s = new StringBuffer();
/* 345 */     s.append("[");
/* 346 */     for (int i = 0; i < this.size; i++) {
/* 347 */       if (i != 0)
/* 348 */         s.append(", "); 
/* 349 */       s.append(this.refArray[this.array[i]]);
/*     */     } 
/* 351 */     s.append("]");
/* 352 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntArrayIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */