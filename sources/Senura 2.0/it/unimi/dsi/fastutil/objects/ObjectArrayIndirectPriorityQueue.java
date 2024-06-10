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
/*     */ 
/*     */ 
/*     */ public class ObjectArrayIndirectPriorityQueue<K>
/*     */   implements IndirectPriorityQueue<K>
/*     */ {
/*     */   protected K[] refArray;
/*  40 */   protected int[] array = IntArrays.EMPTY_ARRAY;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int size;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Comparator<? super K> c;
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int capacity, Comparator<? super K> c) {
/*  62 */     if (capacity > 0)
/*  63 */       this.array = new int[capacity]; 
/*  64 */     this.refArray = refArray;
/*  65 */     this.c = c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int capacity) {
/*  76 */     this(refArray, capacity, (Comparator<? super K>)null);
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, Comparator<? super K> c) {
/*  89 */     this(refArray, refArray.length, c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray) {
/*  99 */     this(refArray, refArray.length, (Comparator<? super K>)null);
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a, int size, Comparator<? super K> c) {
/* 119 */     this(refArray, 0, c);
/* 120 */     this.array = a;
/* 121 */     this.size = size;
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a, Comparator<? super K> c) {
/* 138 */     this(refArray, a, a.length, c);
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a, int size) {
/* 154 */     this(refArray, a, size, null);
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
/*     */   public ObjectArrayIndirectPriorityQueue(K[] refArray, int[] a) {
/* 168 */     this(refArray, a, a.length);
/*     */   }
/*     */ 
/*     */   
/*     */   private int findFirst() {
/* 173 */     if (this.firstIndexValid)
/* 174 */       return this.firstIndex; 
/* 175 */     this.firstIndexValid = true;
/* 176 */     int i = this.size;
/* 177 */     int firstIndex = --i;
/* 178 */     K first = this.refArray[this.array[firstIndex]];
/* 179 */     if (this.c == null) {
/* 180 */       while (i-- != 0) {
/* 181 */         if (((Comparable<K>)this.refArray[this.array[i]]).compareTo(first) < 0)
/* 182 */           first = this.refArray[this.array[firstIndex = i]]; 
/*     */       } 
/*     */     } else {
/* 185 */       while (i-- != 0) {
/* 186 */         if (this.c.compare(this.refArray[this.array[i]], first) < 0)
/* 187 */           first = this.refArray[this.array[firstIndex = i]]; 
/*     */       } 
/* 189 */     }  return this.firstIndex = firstIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   private int findLast() {
/* 194 */     int i = this.size;
/* 195 */     int lastIndex = --i;
/* 196 */     K last = this.refArray[this.array[lastIndex]];
/* 197 */     if (this.c == null)
/* 198 */     { while (i-- != 0) {
/* 199 */         if (((Comparable<K>)last).compareTo(this.refArray[this.array[i]]) < 0)
/* 200 */           last = this.refArray[this.array[lastIndex = i]]; 
/*     */       }  }
/* 202 */     else { while (i-- != 0) {
/* 203 */         if (this.c.compare(last, this.refArray[this.array[i]]) < 0)
/* 204 */           last = this.refArray[this.array[lastIndex = i]]; 
/*     */       }  }
/* 206 */      return lastIndex;
/*     */   }
/*     */   protected final void ensureNonEmpty() {
/* 209 */     if (this.size == 0) {
/* 210 */       throw new NoSuchElementException();
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
/* 222 */     if (index < 0)
/* 223 */       throw new IndexOutOfBoundsException("Index (" + index + ") is negative"); 
/* 224 */     if (index >= this.refArray.length) {
/* 225 */       throw new IndexOutOfBoundsException("Index (" + index + ") is larger than or equal to reference array size (" + this.refArray.length + ")");
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
/* 239 */     ensureElement(x);
/* 240 */     if (this.size == this.array.length)
/* 241 */       this.array = IntArrays.grow(this.array, this.size + 1); 
/* 242 */     if (this.firstIndexValid)
/* 243 */     { if (this.c == null) {
/* 244 */         if (((Comparable<K>)this.refArray[x]).compareTo(this.refArray[this.array[this.firstIndex]]) < 0)
/* 245 */           this.firstIndex = this.size; 
/* 246 */       } else if (this.c.compare(this.refArray[x], this.refArray[this.array[this.firstIndex]]) < 0) {
/* 247 */         this.firstIndex = this.size;
/*     */       }  }
/* 249 */     else { this.firstIndexValid = false; }
/* 250 */      this.array[this.size++] = x;
/*     */   }
/*     */   
/*     */   public int dequeue() {
/* 254 */     ensureNonEmpty();
/* 255 */     int firstIndex = findFirst();
/* 256 */     int result = this.array[firstIndex];
/* 257 */     if (--this.size != 0)
/* 258 */       System.arraycopy(this.array, firstIndex + 1, this.array, firstIndex, this.size - firstIndex); 
/* 259 */     this.firstIndexValid = false;
/* 260 */     return result;
/*     */   }
/*     */   
/*     */   public int first() {
/* 264 */     ensureNonEmpty();
/* 265 */     return this.array[findFirst()];
/*     */   }
/*     */   
/*     */   public int last() {
/* 269 */     ensureNonEmpty();
/* 270 */     return this.array[findLast()];
/*     */   }
/*     */   
/*     */   public void changed() {
/* 274 */     ensureNonEmpty();
/* 275 */     this.firstIndexValid = false;
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
/* 286 */     ensureElement(index);
/* 287 */     if (index == this.firstIndex) {
/* 288 */       this.firstIndexValid = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void allChanged() {
/* 293 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public boolean remove(int index) {
/* 297 */     ensureElement(index);
/* 298 */     int[] a = this.array;
/* 299 */     int i = this.size; do {  }
/* 300 */     while (i-- != 0 && 
/* 301 */       a[i] != index);
/*     */     
/* 303 */     if (i < 0)
/* 304 */       return false; 
/* 305 */     this.firstIndexValid = false;
/* 306 */     if (--this.size != 0)
/* 307 */       System.arraycopy(a, i + 1, a, i, this.size - i); 
/* 308 */     return true;
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
/* 321 */     K top = this.refArray[this.array[findFirst()]];
/* 322 */     int i = this.size, c = 0;
/* 323 */     while (i-- != 0) {
/* 324 */       if (top.equals(this.refArray[this.array[i]]))
/* 325 */         a[c++] = this.array[i]; 
/* 326 */     }  return c;
/*     */   }
/*     */   
/*     */   public int size() {
/* 330 */     return this.size;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 334 */     this.size = 0;
/* 335 */     this.firstIndexValid = false;
/*     */   }
/*     */   
/*     */   public void trim() {
/* 339 */     this.array = IntArrays.trim(this.array, this.size);
/*     */   }
/*     */   
/*     */   public Comparator<? super K> comparator() {
/* 343 */     return this.c;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 347 */     StringBuffer s = new StringBuffer();
/* 348 */     s.append("[");
/* 349 */     for (int i = 0; i < this.size; i++) {
/* 350 */       if (i != 0)
/* 351 */         s.append(", "); 
/* 352 */       s.append(this.refArray[this.array[i]]);
/*     */     } 
/* 354 */     s.append("]");
/* 355 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayIndirectPriorityQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */