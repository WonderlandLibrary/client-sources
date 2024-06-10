/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteBigArrayBigList
/*     */   extends AbstractByteBigList
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353130L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected transient byte[][] a;
/*     */   protected long size;
/*     */   
/*     */   protected ByteBigArrayBigList(byte[][] a, boolean dummy) {
/*  66 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigArrayBigList(long capacity) {
/*  76 */     if (capacity < 0L)
/*  77 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  78 */     if (capacity == 0L) {
/*  79 */       this.a = ByteBigArrays.EMPTY_BIG_ARRAY;
/*     */     } else {
/*  81 */       this.a = ByteBigArrays.newBigArray(capacity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigArrayBigList() {
/*  89 */     this.a = ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigArrayBigList(ByteCollection c) {
/* 100 */     this(c.size());
/* 101 */     for (ByteIterator i = c.iterator(); i.hasNext();) {
/* 102 */       add(i.nextByte());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigArrayBigList(ByteBigList l) {
/* 112 */     this(l.size64());
/* 113 */     l.getElements(0L, this.a, 0L, this.size = l.size64());
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
/*     */   public ByteBigArrayBigList(byte[][] a) {
/* 130 */     this(a, 0L, BigArrays.length(a));
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
/*     */   public ByteBigArrayBigList(byte[][] a, long offset, long length) {
/* 151 */     this(length);
/* 152 */     BigArrays.copy(a, offset, this.a, 0L, length);
/* 153 */     this.size = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigArrayBigList(Iterator<? extends Byte> i) {
/* 163 */     this();
/* 164 */     while (i.hasNext()) {
/* 165 */       add(((Byte)i.next()).byteValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBigArrayBigList(ByteIterator i) {
/* 176 */     this();
/* 177 */     while (i.hasNext()) {
/* 178 */       add(i.nextByte());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[][] elements() {
/* 186 */     return this.a;
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
/*     */   public static ByteBigArrayBigList wrap(byte[][] a, long length) {
/* 198 */     if (length > BigArrays.length(a))
/* 199 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + 
/* 200 */           BigArrays.length(a) + ")"); 
/* 201 */     ByteBigArrayBigList l = new ByteBigArrayBigList(a, false);
/* 202 */     l.size = length;
/* 203 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBigArrayBigList wrap(byte[][] a) {
/* 213 */     return wrap(a, BigArrays.length(a));
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
/* 224 */     if (capacity <= this.a.length || this.a == ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
/*     */       return; 
/* 226 */     this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/* 227 */     assert this.size <= BigArrays.length(this.a);
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
/* 239 */     long oldLength = BigArrays.length(this.a);
/* 240 */     if (capacity <= oldLength)
/*     */       return; 
/* 242 */     if (this.a != ByteBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
/* 243 */       capacity = Math.max(oldLength + (oldLength >> 1L), capacity);
/* 244 */     } else if (capacity < 10L) {
/* 245 */       capacity = 10L;
/* 246 */     }  this.a = BigArrays.forceCapacity(this.a, capacity, this.size);
/* 247 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public void add(long index, byte k) {
/* 251 */     ensureIndex(index);
/* 252 */     grow(this.size + 1L);
/* 253 */     if (index != this.size)
/* 254 */       BigArrays.copy(this.a, index, this.a, index + 1L, this.size - index); 
/* 255 */     BigArrays.set(this.a, index, k);
/* 256 */     this.size++;
/* 257 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public boolean add(byte k) {
/* 261 */     grow(this.size + 1L);
/* 262 */     BigArrays.set(this.a, this.size++, k);
/* 263 */     if (!$assertionsDisabled && this.size > BigArrays.length(this.a)) throw new AssertionError(); 
/* 264 */     return true;
/*     */   }
/*     */   
/*     */   public byte getByte(long index) {
/* 268 */     if (index >= this.size) {
/* 269 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 271 */     return BigArrays.get(this.a, index);
/*     */   }
/*     */   
/*     */   public long indexOf(byte k) {
/* 275 */     for (long i = 0L; i < this.size; i++) {
/* 276 */       if (k == BigArrays.get(this.a, i))
/* 277 */         return i; 
/* 278 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public long lastIndexOf(byte k) {
/* 282 */     for (long i = this.size; i-- != 0L;) {
/* 283 */       if (k == BigArrays.get(this.a, i))
/* 284 */         return i; 
/* 285 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public byte removeByte(long index) {
/* 289 */     if (index >= this.size) {
/* 290 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 292 */     byte old = BigArrays.get(this.a, index);
/* 293 */     this.size--;
/* 294 */     if (index != this.size)
/* 295 */       BigArrays.copy(this.a, index + 1L, this.a, index, this.size - index); 
/* 296 */     assert this.size <= BigArrays.length(this.a);
/* 297 */     return old;
/*     */   }
/*     */   
/*     */   public boolean rem(byte k) {
/* 301 */     long index = indexOf(k);
/* 302 */     if (index == -1L)
/* 303 */       return false; 
/* 304 */     removeByte(index);
/* 305 */     assert this.size <= BigArrays.length(this.a);
/* 306 */     return true;
/*     */   }
/*     */   
/*     */   public byte set(long index, byte k) {
/* 310 */     if (index >= this.size) {
/* 311 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 313 */     byte old = BigArrays.get(this.a, index);
/* 314 */     BigArrays.set(this.a, index, k);
/* 315 */     return old;
/*     */   }
/*     */   
/*     */   public boolean removeAll(ByteCollection c) {
/* 319 */     byte[] s = null, d = null;
/* 320 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/* 321 */     for (i = 0L; i < this.size; i++) {
/* 322 */       if (sd == 134217728) {
/* 323 */         sd = 0;
/* 324 */         s = this.a[++ss];
/*     */       } 
/* 326 */       if (!c.contains(s[sd])) {
/* 327 */         if (dd == 134217728) {
/* 328 */           d = this.a[++ds];
/* 329 */           dd = 0;
/*     */         } 
/* 331 */         d[dd++] = s[sd];
/*     */       } 
/* 333 */       sd++;
/*     */     } 
/* 335 */     long j = BigArrays.index(ds, dd);
/* 336 */     boolean modified = (this.size != j);
/* 337 */     this.size = j;
/* 338 */     return modified;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 342 */     byte[] s = null, d = null;
/* 343 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/* 344 */     for (i = 0L; i < this.size; i++) {
/* 345 */       if (sd == 134217728) {
/* 346 */         sd = 0;
/* 347 */         s = this.a[++ss];
/*     */       } 
/* 349 */       if (!c.contains(Byte.valueOf(s[sd]))) {
/* 350 */         if (dd == 134217728) {
/* 351 */           d = this.a[++ds];
/* 352 */           dd = 0;
/*     */         } 
/* 354 */         d[dd++] = s[sd];
/*     */       } 
/* 356 */       sd++;
/*     */     } 
/* 358 */     long j = BigArrays.index(ds, dd);
/* 359 */     boolean modified = (this.size != j);
/* 360 */     this.size = j;
/* 361 */     return modified;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 365 */     this.size = 0L;
/* 366 */     assert this.size <= BigArrays.length(this.a);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 370 */     return this.size;
/*     */   }
/*     */   
/*     */   public void size(long size) {
/* 374 */     if (size > BigArrays.length(this.a))
/* 375 */       this.a = BigArrays.forceCapacity(this.a, size, this.size); 
/* 376 */     if (size > this.size)
/* 377 */       BigArrays.fill(this.a, this.size, size, (byte)0); 
/* 378 */     this.size = size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 382 */     return (this.size == 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 390 */     trim(0L);
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
/* 410 */     long arrayLength = BigArrays.length(this.a);
/* 411 */     if (n >= arrayLength || this.size == arrayLength)
/*     */       return; 
/* 413 */     this.a = BigArrays.trim(this.a, Math.max(n, this.size));
/* 414 */     assert this.size <= BigArrays.length(this.a);
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
/*     */   public void getElements(long from, byte[][] a, long offset, long length) {
/* 432 */     BigArrays.copy(this.a, from, a, offset, length);
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
/* 444 */     BigArrays.ensureFromTo(this.size, from, to);
/* 445 */     BigArrays.copy(this.a, to, this.a, from, this.size - to);
/* 446 */     this.size -= to - from;
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
/*     */   public void addElements(long index, byte[][] a, long offset, long length) {
/* 462 */     ensureIndex(index);
/* 463 */     BigArrays.ensureOffsetLength(a, offset, length);
/* 464 */     grow(this.size + length);
/* 465 */     BigArrays.copy(this.a, index, this.a, index + length, this.size - index);
/* 466 */     BigArrays.copy(a, offset, this.a, index, length);
/* 467 */     this.size += length;
/*     */   }
/*     */   
/*     */   public ByteBigListIterator listIterator(final long index) {
/* 471 */     ensureIndex(index);
/* 472 */     return new ByteBigListIterator() {
/* 473 */         long pos = index; long last = -1L;
/*     */         
/*     */         public boolean hasNext() {
/* 476 */           return (this.pos < ByteBigArrayBigList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 480 */           return (this.pos > 0L);
/*     */         }
/*     */         
/*     */         public byte nextByte() {
/* 484 */           if (!hasNext())
/* 485 */             throw new NoSuchElementException(); 
/* 486 */           return BigArrays.get(ByteBigArrayBigList.this.a, this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public byte previousByte() {
/* 490 */           if (!hasPrevious())
/* 491 */             throw new NoSuchElementException(); 
/* 492 */           return BigArrays.get(ByteBigArrayBigList.this.a, this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public long nextIndex() {
/* 496 */           return this.pos;
/*     */         }
/*     */         
/*     */         public long previousIndex() {
/* 500 */           return this.pos - 1L;
/*     */         }
/*     */         
/*     */         public void add(byte k) {
/* 504 */           ByteBigArrayBigList.this.add(this.pos++, k);
/* 505 */           this.last = -1L;
/*     */         }
/*     */         
/*     */         public void set(byte k) {
/* 509 */           if (this.last == -1L)
/* 510 */             throw new IllegalStateException(); 
/* 511 */           ByteBigArrayBigList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 515 */           if (this.last == -1L)
/* 516 */             throw new IllegalStateException(); 
/* 517 */           ByteBigArrayBigList.this.removeByte(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 522 */           if (this.last < this.pos)
/* 523 */             this.pos--; 
/* 524 */           this.last = -1L;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ByteBigArrayBigList clone() {
/* 530 */     ByteBigArrayBigList c = new ByteBigArrayBigList(this.size);
/* 531 */     BigArrays.copy(this.a, 0L, c.a, 0L, this.size);
/* 532 */     c.size = this.size;
/* 533 */     return c;
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
/*     */   public boolean equals(ByteBigArrayBigList l) {
/* 548 */     if (l == this)
/* 549 */       return true; 
/* 550 */     long s = size64();
/* 551 */     if (s != l.size64())
/* 552 */       return false; 
/* 553 */     byte[][] a1 = this.a;
/* 554 */     byte[][] a2 = l.a;
/* 555 */     while (s-- != 0L) {
/* 556 */       if (BigArrays.get(a1, s) != BigArrays.get(a2, s))
/* 557 */         return false; 
/* 558 */     }  return true;
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
/*     */   public int compareTo(ByteBigArrayBigList l) {
/* 574 */     long s1 = size64(), s2 = l.size64();
/* 575 */     byte[][] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 578 */     for (i = 0; i < s1 && i < s2; i++) {
/* 579 */       byte e1 = BigArrays.get(a1, i);
/* 580 */       byte e2 = BigArrays.get(a2, i); int r;
/* 581 */       if ((r = Byte.compare(e1, e2)) != 0)
/* 582 */         return r; 
/*     */     } 
/* 584 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 587 */     s.defaultWriteObject();
/* 588 */     for (int i = 0; i < this.size; i++)
/* 589 */       s.writeByte(BigArrays.get(this.a, i)); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 593 */     s.defaultReadObject();
/* 594 */     this.a = ByteBigArrays.newBigArray(this.size);
/* 595 */     for (int i = 0; i < this.size; i++)
/* 596 */       BigArrays.set(this.a, i, s.readByte()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */