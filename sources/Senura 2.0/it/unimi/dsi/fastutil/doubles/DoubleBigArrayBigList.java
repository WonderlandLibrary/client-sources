/*     */ package it.unimi.dsi.fastutil.doubles;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.BigListIterator;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleBigArrayBigList
/*     */   extends AbstractDoubleBigList
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353130L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected transient double[][] a;
/*     */   protected long size;
/*     */   
/*     */   protected DoubleBigArrayBigList(double[][] a, boolean dummy) {
/*  70 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBigArrayBigList(long capacity) {
/*  80 */     if (capacity < 0L)
/*  81 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  82 */     if (capacity == 0L) {
/*  83 */       this.a = DoubleBigArrays.EMPTY_BIG_ARRAY;
/*     */     } else {
/*  85 */       this.a = DoubleBigArrays.newBigArray(capacity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBigArrayBigList() {
/*  93 */     this.a = DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBigArrayBigList(DoubleCollection c) {
/* 104 */     this(c.size());
/* 105 */     for (DoubleIterator i = c.iterator(); i.hasNext();) {
/* 106 */       add(i.nextDouble());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBigArrayBigList(DoubleBigList l) {
/* 116 */     this(l.size64());
/* 117 */     l.getElements(0L, this.a, 0L, this.size = l.size64());
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
/*     */   public DoubleBigArrayBigList(double[][] a) {
/* 134 */     this(a, 0L, BigArrays.length(a));
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
/*     */   public DoubleBigArrayBigList(double[][] a, long offset, long length) {
/* 155 */     this(length);
/* 156 */     BigArrays.copy(a, offset, this.a, 0L, length);
/* 157 */     this.size = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBigArrayBigList(Iterator<? extends Double> i) {
/* 167 */     this();
/* 168 */     while (i.hasNext()) {
/* 169 */       add(((Double)i.next()).doubleValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleBigArrayBigList(DoubleIterator i) {
/* 180 */     this();
/* 181 */     while (i.hasNext()) {
/* 182 */       add(i.nextDouble());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] elements() {
/* 190 */     return this.a;
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
/*     */   public static DoubleBigArrayBigList wrap(double[][] a, long length) {
/* 202 */     if (length > BigArrays.length(a))
/* 203 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + 
/* 204 */           BigArrays.length(a) + ")"); 
/* 205 */     DoubleBigArrayBigList l = new DoubleBigArrayBigList(a, false);
/* 206 */     l.size = length;
/* 207 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleBigArrayBigList wrap(double[][] a) {
/* 217 */     return wrap(a, BigArrays.length(a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(long capacity) {
/* 228 */     if (capacity <= this.a.length || this.a == DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
/*     */       return; 
/* 230 */     this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/* 231 */     assert this.size <= BigArrays.length(this.a);
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
/*     */   private void grow(long capacity) {
/* 243 */     long oldLength = BigArrays.length(this.a);
/* 244 */     if (capacity <= oldLength)
/*     */       return; 
/* 246 */     if (this.a != DoubleBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
/* 247 */       capacity = Math.max(oldLength + (oldLength >> 1L), capacity);
/* 248 */     } else if (capacity < 10L) {
/* 249 */       capacity = 10L;
/* 250 */     }  this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/* 251 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public void add(long index, double k) {
/* 255 */     ensureIndex(index);
/* 256 */     grow(this.size + 1L);
/* 257 */     if (index != this.size)
/* 258 */       BigArrays.copy(this.a, index, this.a, index + 1L, this.size - index); 
/* 259 */     BigArrays.set(this.a, index, k);
/* 260 */     this.size++;
/* 261 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public boolean add(double k) {
/* 265 */     grow(this.size + 1L);
/* 266 */     BigArrays.set(this.a, this.size++, k);
/* 267 */     if (!$assertionsDisabled && this.size > BigArrays.length(this.a)) throw new AssertionError(); 
/* 268 */     return true;
/*     */   }
/*     */   
/*     */   public double getDouble(long index) {
/* 272 */     if (index >= this.size) {
/* 273 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 275 */     return BigArrays.get(this.a, index);
/*     */   }
/*     */   
/*     */   public long indexOf(double k) {
/* 279 */     for (long i = 0L; i < this.size; i++) {
/* 280 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(BigArrays.get(this.a, i)))
/* 281 */         return i; 
/* 282 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public long lastIndexOf(double k) {
/* 286 */     for (long i = this.size; i-- != 0L;) {
/* 287 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(BigArrays.get(this.a, i)))
/* 288 */         return i; 
/* 289 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public double removeDouble(long index) {
/* 293 */     if (index >= this.size) {
/* 294 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 296 */     double old = BigArrays.get(this.a, index);
/* 297 */     this.size--;
/* 298 */     if (index != this.size)
/* 299 */       BigArrays.copy(this.a, index + 1L, this.a, index, this.size - index); 
/* 300 */     assert this.size <= BigArrays.length(this.a);
/* 301 */     return old;
/*     */   }
/*     */   
/*     */   public boolean rem(double k) {
/* 305 */     long index = indexOf(k);
/* 306 */     if (index == -1L)
/* 307 */       return false; 
/* 308 */     removeDouble(index);
/* 309 */     assert this.size <= BigArrays.length(this.a);
/* 310 */     return true;
/*     */   }
/*     */   
/*     */   public double set(long index, double k) {
/* 314 */     if (index >= this.size) {
/* 315 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 317 */     double old = BigArrays.get(this.a, index);
/* 318 */     BigArrays.set(this.a, index, k);
/* 319 */     return old;
/*     */   }
/*     */   
/*     */   public boolean removeAll(DoubleCollection c) {
/* 323 */     double[] s = null, d = null;
/* 324 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/* 325 */     for (i = 0L; i < this.size; i++) {
/* 326 */       if (sd == 134217728) {
/* 327 */         sd = 0;
/* 328 */         s = this.a[++ss];
/*     */       } 
/* 330 */       if (!c.contains(s[sd])) {
/* 331 */         if (dd == 134217728) {
/* 332 */           d = this.a[++ds];
/* 333 */           dd = 0;
/*     */         } 
/* 335 */         d[dd++] = s[sd];
/*     */       } 
/* 337 */       sd++;
/*     */     } 
/* 339 */     long j = BigArrays.index(ds, dd);
/* 340 */     boolean modified = (this.size != j);
/* 341 */     this.size = j;
/* 342 */     return modified;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 346 */     double[] s = null, d = null;
/* 347 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/* 348 */     for (i = 0L; i < this.size; i++) {
/* 349 */       if (sd == 134217728) {
/* 350 */         sd = 0;
/* 351 */         s = this.a[++ss];
/*     */       } 
/* 353 */       if (!c.contains(Double.valueOf(s[sd]))) {
/* 354 */         if (dd == 134217728) {
/* 355 */           d = this.a[++ds];
/* 356 */           dd = 0;
/*     */         } 
/* 358 */         d[dd++] = s[sd];
/*     */       } 
/* 360 */       sd++;
/*     */     } 
/* 362 */     long j = BigArrays.index(ds, dd);
/* 363 */     boolean modified = (this.size != j);
/* 364 */     this.size = j;
/* 365 */     return modified;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 369 */     this.size = 0L;
/* 370 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 374 */     return this.size;
/*     */   }
/*     */   
/*     */   public void size(long size) {
/* 378 */     if (size > BigArrays.length(this.a))
/* 379 */       this.a = BigArrays.forceCapacity(this.a, size, this.size); 
/* 380 */     if (size > this.size)
/* 381 */       BigArrays.fill(this.a, this.size, size, 0.0D); 
/* 382 */     this.size = size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 386 */     return (this.size == 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 394 */     trim(0L);
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
/*     */   public void trim(long n) {
/* 414 */     long arrayLength = BigArrays.length(this.a);
/* 415 */     if (n >= arrayLength || this.size == arrayLength)
/*     */       return; 
/* 417 */     this.a = BigArrays.trim(this.a, Math.max(n, this.size));
/* 418 */     assert this.size <= BigArrays.length(this.a);
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
/*     */   public void getElements(long from, double[][] a, long offset, long length) {
/* 436 */     BigArrays.copy(this.a, from, a, offset, length);
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
/*     */   public void removeElements(long from, long to) {
/* 448 */     BigArrays.ensureFromTo(this.size, from, to);
/* 449 */     BigArrays.copy(this.a, to, this.a, from, this.size - to);
/* 450 */     this.size -= to - from;
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
/*     */   public void addElements(long index, double[][] a, long offset, long length) {
/* 466 */     ensureIndex(index);
/* 467 */     BigArrays.ensureOffsetLength(a, offset, length);
/* 468 */     grow(this.size + length);
/* 469 */     BigArrays.copy(this.a, index, this.a, index + length, this.size - index);
/* 470 */     BigArrays.copy(a, offset, this.a, index, length);
/* 471 */     this.size += length;
/*     */   }
/*     */   
/*     */   public DoubleBigListIterator listIterator(final long index) {
/* 475 */     ensureIndex(index);
/* 476 */     return new DoubleBigListIterator() {
/* 477 */         long pos = index; long last = -1L;
/*     */         
/*     */         public boolean hasNext() {
/* 480 */           return (this.pos < DoubleBigArrayBigList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 484 */           return (this.pos > 0L);
/*     */         }
/*     */         
/*     */         public double nextDouble() {
/* 488 */           if (!hasNext())
/* 489 */             throw new NoSuchElementException(); 
/* 490 */           return BigArrays.get(DoubleBigArrayBigList.this.a, this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public double previousDouble() {
/* 494 */           if (!hasPrevious())
/* 495 */             throw new NoSuchElementException(); 
/* 496 */           return BigArrays.get(DoubleBigArrayBigList.this.a, this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public long nextIndex() {
/* 500 */           return this.pos;
/*     */         }
/*     */         
/*     */         public long previousIndex() {
/* 504 */           return this.pos - 1L;
/*     */         }
/*     */         
/*     */         public void add(double k) {
/* 508 */           DoubleBigArrayBigList.this.add(this.pos++, k);
/* 509 */           this.last = -1L;
/*     */         }
/*     */         
/*     */         public void set(double k) {
/* 513 */           if (this.last == -1L)
/* 514 */             throw new IllegalStateException(); 
/* 515 */           DoubleBigArrayBigList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 519 */           if (this.last == -1L)
/* 520 */             throw new IllegalStateException(); 
/* 521 */           DoubleBigArrayBigList.this.removeDouble(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 526 */           if (this.last < this.pos)
/* 527 */             this.pos--; 
/* 528 */           this.last = -1L;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public DoubleBigArrayBigList clone() {
/* 534 */     DoubleBigArrayBigList c = new DoubleBigArrayBigList(this.size);
/* 535 */     BigArrays.copy(this.a, 0L, c.a, 0L, this.size);
/* 536 */     c.size = this.size;
/* 537 */     return c;
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
/*     */   public boolean equals(DoubleBigArrayBigList l) {
/* 552 */     if (l == this)
/* 553 */       return true; 
/* 554 */     long s = size64();
/* 555 */     if (s != l.size64())
/* 556 */       return false; 
/* 557 */     double[][] a1 = this.a;
/* 558 */     double[][] a2 = l.a;
/* 559 */     while (s-- != 0L) {
/* 560 */       if (BigArrays.get(a1, s) != BigArrays.get(a2, s))
/* 561 */         return false; 
/* 562 */     }  return true;
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
/*     */   public int compareTo(DoubleBigArrayBigList l) {
/* 578 */     long s1 = size64(), s2 = l.size64();
/* 579 */     double[][] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 582 */     for (i = 0; i < s1 && i < s2; i++) {
/* 583 */       double e1 = BigArrays.get(a1, i);
/* 584 */       double e2 = BigArrays.get(a2, i); int r;
/* 585 */       if ((r = Double.compare(e1, e2)) != 0)
/* 586 */         return r; 
/*     */     } 
/* 588 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 591 */     s.defaultWriteObject();
/* 592 */     for (int i = 0; i < this.size; i++)
/* 593 */       s.writeDouble(BigArrays.get(this.a, i)); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 597 */     s.defaultReadObject();
/* 598 */     this.a = DoubleBigArrays.newBigArray(this.size);
/* 599 */     for (int i = 0; i < this.size; i++)
/* 600 */       BigArrays.set(this.a, i, s.readDouble()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */