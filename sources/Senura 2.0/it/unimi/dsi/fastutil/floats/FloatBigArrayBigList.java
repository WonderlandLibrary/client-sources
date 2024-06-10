/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public class FloatBigArrayBigList
/*     */   extends AbstractFloatBigList
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353130L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected transient float[][] a;
/*     */   protected long size;
/*     */   
/*     */   protected FloatBigArrayBigList(float[][] a, boolean dummy) {
/*  70 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBigArrayBigList(long capacity) {
/*  80 */     if (capacity < 0L)
/*  81 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  82 */     if (capacity == 0L) {
/*  83 */       this.a = FloatBigArrays.EMPTY_BIG_ARRAY;
/*     */     } else {
/*  85 */       this.a = FloatBigArrays.newBigArray(capacity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBigArrayBigList() {
/*  93 */     this.a = FloatBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBigArrayBigList(FloatCollection c) {
/* 104 */     this(c.size());
/* 105 */     for (FloatIterator i = c.iterator(); i.hasNext();) {
/* 106 */       add(i.nextFloat());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBigArrayBigList(FloatBigList l) {
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
/*     */   public FloatBigArrayBigList(float[][] a) {
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
/*     */   public FloatBigArrayBigList(float[][] a, long offset, long length) {
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
/*     */   public FloatBigArrayBigList(Iterator<? extends Float> i) {
/* 167 */     this();
/* 168 */     while (i.hasNext()) {
/* 169 */       add(((Float)i.next()).floatValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatBigArrayBigList(FloatIterator i) {
/* 180 */     this();
/* 181 */     while (i.hasNext()) {
/* 182 */       add(i.nextFloat());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] elements() {
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
/*     */   public static FloatBigArrayBigList wrap(float[][] a, long length) {
/* 202 */     if (length > BigArrays.length(a))
/* 203 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + 
/* 204 */           BigArrays.length(a) + ")"); 
/* 205 */     FloatBigArrayBigList l = new FloatBigArrayBigList(a, false);
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
/*     */   public static FloatBigArrayBigList wrap(float[][] a) {
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
/* 228 */     if (capacity <= this.a.length || this.a == FloatBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
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
/* 246 */     if (this.a != FloatBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
/* 247 */       capacity = Math.max(oldLength + (oldLength >> 1L), capacity);
/* 248 */     } else if (capacity < 10L) {
/* 249 */       capacity = 10L;
/* 250 */     }  this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/* 251 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public void add(long index, float k) {
/* 255 */     ensureIndex(index);
/* 256 */     grow(this.size + 1L);
/* 257 */     if (index != this.size)
/* 258 */       BigArrays.copy(this.a, index, this.a, index + 1L, this.size - index); 
/* 259 */     BigArrays.set(this.a, index, k);
/* 260 */     this.size++;
/* 261 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public boolean add(float k) {
/* 265 */     grow(this.size + 1L);
/* 266 */     BigArrays.set(this.a, this.size++, k);
/* 267 */     if (!$assertionsDisabled && this.size > BigArrays.length(this.a)) throw new AssertionError(); 
/* 268 */     return true;
/*     */   }
/*     */   
/*     */   public float getFloat(long index) {
/* 272 */     if (index >= this.size) {
/* 273 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 275 */     return BigArrays.get(this.a, index);
/*     */   }
/*     */   
/*     */   public long indexOf(float k) {
/* 279 */     for (long i = 0L; i < this.size; i++) {
/* 280 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(BigArrays.get(this.a, i)))
/* 281 */         return i; 
/* 282 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public long lastIndexOf(float k) {
/* 286 */     for (long i = this.size; i-- != 0L;) {
/* 287 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(BigArrays.get(this.a, i)))
/* 288 */         return i; 
/* 289 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public float removeFloat(long index) {
/* 293 */     if (index >= this.size) {
/* 294 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 296 */     float old = BigArrays.get(this.a, index);
/* 297 */     this.size--;
/* 298 */     if (index != this.size)
/* 299 */       BigArrays.copy(this.a, index + 1L, this.a, index, this.size - index); 
/* 300 */     assert this.size <= BigArrays.length(this.a);
/* 301 */     return old;
/*     */   }
/*     */   
/*     */   public boolean rem(float k) {
/* 305 */     long index = indexOf(k);
/* 306 */     if (index == -1L)
/* 307 */       return false; 
/* 308 */     removeFloat(index);
/* 309 */     assert this.size <= BigArrays.length(this.a);
/* 310 */     return true;
/*     */   }
/*     */   
/*     */   public float set(long index, float k) {
/* 314 */     if (index >= this.size) {
/* 315 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 317 */     float old = BigArrays.get(this.a, index);
/* 318 */     BigArrays.set(this.a, index, k);
/* 319 */     return old;
/*     */   }
/*     */   
/*     */   public boolean removeAll(FloatCollection c) {
/* 323 */     float[] s = null, d = null;
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
/* 346 */     float[] s = null, d = null;
/* 347 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/* 348 */     for (i = 0L; i < this.size; i++) {
/* 349 */       if (sd == 134217728) {
/* 350 */         sd = 0;
/* 351 */         s = this.a[++ss];
/*     */       } 
/* 353 */       if (!c.contains(Float.valueOf(s[sd]))) {
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
/* 381 */       BigArrays.fill(this.a, this.size, size, 0.0F); 
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
/*     */   public void getElements(long from, float[][] a, long offset, long length) {
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
/*     */   public void addElements(long index, float[][] a, long offset, long length) {
/* 466 */     ensureIndex(index);
/* 467 */     BigArrays.ensureOffsetLength(a, offset, length);
/* 468 */     grow(this.size + length);
/* 469 */     BigArrays.copy(this.a, index, this.a, index + length, this.size - index);
/* 470 */     BigArrays.copy(a, offset, this.a, index, length);
/* 471 */     this.size += length;
/*     */   }
/*     */   
/*     */   public FloatBigListIterator listIterator(final long index) {
/* 475 */     ensureIndex(index);
/* 476 */     return new FloatBigListIterator() {
/* 477 */         long pos = index; long last = -1L;
/*     */         
/*     */         public boolean hasNext() {
/* 480 */           return (this.pos < FloatBigArrayBigList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 484 */           return (this.pos > 0L);
/*     */         }
/*     */         
/*     */         public float nextFloat() {
/* 488 */           if (!hasNext())
/* 489 */             throw new NoSuchElementException(); 
/* 490 */           return BigArrays.get(FloatBigArrayBigList.this.a, this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public float previousFloat() {
/* 494 */           if (!hasPrevious())
/* 495 */             throw new NoSuchElementException(); 
/* 496 */           return BigArrays.get(FloatBigArrayBigList.this.a, this.last = --this.pos);
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
/*     */         public void add(float k) {
/* 508 */           FloatBigArrayBigList.this.add(this.pos++, k);
/* 509 */           this.last = -1L;
/*     */         }
/*     */         
/*     */         public void set(float k) {
/* 513 */           if (this.last == -1L)
/* 514 */             throw new IllegalStateException(); 
/* 515 */           FloatBigArrayBigList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 519 */           if (this.last == -1L)
/* 520 */             throw new IllegalStateException(); 
/* 521 */           FloatBigArrayBigList.this.removeFloat(this.last);
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
/*     */   public FloatBigArrayBigList clone() {
/* 534 */     FloatBigArrayBigList c = new FloatBigArrayBigList(this.size);
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
/*     */   public boolean equals(FloatBigArrayBigList l) {
/* 552 */     if (l == this)
/* 553 */       return true; 
/* 554 */     long s = size64();
/* 555 */     if (s != l.size64())
/* 556 */       return false; 
/* 557 */     float[][] a1 = this.a;
/* 558 */     float[][] a2 = l.a;
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
/*     */   public int compareTo(FloatBigArrayBigList l) {
/* 578 */     long s1 = size64(), s2 = l.size64();
/* 579 */     float[][] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 582 */     for (i = 0; i < s1 && i < s2; i++) {
/* 583 */       float e1 = BigArrays.get(a1, i);
/* 584 */       float e2 = BigArrays.get(a2, i); int r;
/* 585 */       if ((r = Float.compare(e1, e2)) != 0)
/* 586 */         return r; 
/*     */     } 
/* 588 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 591 */     s.defaultWriteObject();
/* 592 */     for (int i = 0; i < this.size; i++)
/* 593 */       s.writeFloat(BigArrays.get(this.a, i)); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 597 */     s.defaultReadObject();
/* 598 */     this.a = FloatBigArrays.newBigArray(this.size);
/* 599 */     for (int i = 0; i < this.size; i++)
/* 600 */       BigArrays.set(this.a, i, s.readFloat()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */