/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ import java.util.Objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectBigArrayBigList<K>
/*     */   extends AbstractObjectBigList<K>
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353131L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected final boolean wrapped;
/*     */   protected transient K[][] a;
/*     */   protected long size;
/*     */   
/*     */   protected ObjectBigArrayBigList(K[][] a, boolean dummy) {
/*  82 */     this.a = a;
/*  83 */     this.wrapped = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigArrayBigList(long capacity) {
/*  93 */     if (capacity < 0L)
/*  94 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  95 */     if (capacity == 0L) {
/*  96 */       this.a = (K[][])ObjectBigArrays.EMPTY_BIG_ARRAY;
/*     */     } else {
/*  98 */       this.a = (K[][])ObjectBigArrays.newBigArray(capacity);
/*  99 */     }  this.wrapped = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigArrayBigList() {
/* 107 */     this.a = (K[][])ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY;
/* 108 */     this.wrapped = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigArrayBigList(ObjectCollection<? extends K> c) {
/* 119 */     this(c.size());
/* 120 */     for (ObjectIterator<? extends K> i = c.iterator(); i.hasNext();) {
/* 121 */       add(i.next());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigArrayBigList(ObjectBigList<? extends K> l) {
/* 131 */     this(l.size64());
/* 132 */     l.getElements(0L, (Object[][])this.a, 0L, this.size = l.size64());
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
/*     */   public ObjectBigArrayBigList(K[][] a) {
/* 149 */     this(a, 0L, BigArrays.length((Object[][])a));
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
/*     */   public ObjectBigArrayBigList(K[][] a, long offset, long length) {
/* 170 */     this(length);
/* 171 */     BigArrays.copy((Object[][])a, offset, (Object[][])this.a, 0L, length);
/* 172 */     this.size = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigArrayBigList(Iterator<? extends K> i) {
/* 182 */     this();
/* 183 */     while (i.hasNext()) {
/* 184 */       add(i.next());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectBigArrayBigList(ObjectIterator<? extends K> i) {
/* 195 */     this();
/* 196 */     while (i.hasNext()) {
/* 197 */       add(i.next());
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
/*     */   public K[][] elements() {
/* 210 */     return this.a;
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
/*     */   public static <K> ObjectBigArrayBigList<K> wrap(K[][] a, long length) {
/* 222 */     if (length > BigArrays.length((Object[][])a))
/* 223 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + 
/* 224 */           BigArrays.length(a) + ")"); 
/* 225 */     ObjectBigArrayBigList<K> l = new ObjectBigArrayBigList<>(a, false);
/* 226 */     l.size = length;
/* 227 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K> ObjectBigArrayBigList<K> wrap(K[][] a) {
/* 237 */     return wrap(a, BigArrays.length((Object[][])a));
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
/* 248 */     if (capacity <= this.a.length || this.a == ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY)
/*     */       return; 
/* 250 */     if (this.wrapped) {
/* 251 */       this.a = (K[][])BigArrays.forceCapacity((Object[][])this.a, capacity, this.size);
/*     */     }
/* 253 */     else if (capacity > BigArrays.length((Object[][])this.a)) {
/* 254 */       Object[][] t = ObjectBigArrays.newBigArray(capacity);
/* 255 */       BigArrays.copy((Object[][])this.a, 0L, t, 0L, this.size);
/* 256 */       this.a = (K[][])t;
/*     */     } 
/*     */     
/* 259 */     assert this.size <= BigArrays.length((Object[][])this.a);
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
/* 271 */     long oldLength = BigArrays.length((Object[][])this.a);
/* 272 */     if (capacity <= oldLength)
/*     */       return; 
/* 274 */     if (this.a != ObjectBigArrays.DEFAULT_EMPTY_BIG_ARRAY) {
/* 275 */       capacity = Math.max(oldLength + (oldLength >> 1L), capacity);
/* 276 */     } else if (capacity < 10L) {
/* 277 */       capacity = 10L;
/* 278 */     }  if (this.wrapped) {
/* 279 */       this.a = (K[][])BigArrays.forceCapacity((Object[][])this.a, capacity, this.size);
/*     */     } else {
/* 281 */       Object[][] t = ObjectBigArrays.newBigArray(capacity);
/* 282 */       BigArrays.copy((Object[][])this.a, 0L, t, 0L, this.size);
/* 283 */       this.a = (K[][])t;
/*     */     } 
/* 285 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*     */   }
/*     */   
/*     */   public void add(long index, K k) {
/* 289 */     ensureIndex(index);
/* 290 */     grow(this.size + 1L);
/* 291 */     if (index != this.size)
/* 292 */       BigArrays.copy((Object[][])this.a, index, (Object[][])this.a, index + 1L, this.size - index); 
/* 293 */     BigArrays.set((Object[][])this.a, index, k);
/* 294 */     this.size++;
/* 295 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*     */   }
/*     */   
/*     */   public boolean add(K k) {
/* 299 */     grow(this.size + 1L);
/* 300 */     BigArrays.set((Object[][])this.a, this.size++, k);
/* 301 */     if (!$assertionsDisabled && this.size > BigArrays.length((Object[][])this.a)) throw new AssertionError(); 
/* 302 */     return true;
/*     */   }
/*     */   
/*     */   public K get(long index) {
/* 306 */     if (index >= this.size) {
/* 307 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 309 */     return (K)BigArrays.get((Object[][])this.a, index);
/*     */   }
/*     */   
/*     */   public long indexOf(Object k) {
/* 313 */     for (long i = 0L; i < this.size; i++) {
/* 314 */       if (Objects.equals(k, BigArrays.get((Object[][])this.a, i)))
/* 315 */         return i; 
/* 316 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public long lastIndexOf(Object k) {
/* 320 */     for (long i = this.size; i-- != 0L;) {
/* 321 */       if (Objects.equals(k, BigArrays.get((Object[][])this.a, i)))
/* 322 */         return i; 
/* 323 */     }  return -1L;
/*     */   }
/*     */   
/*     */   public K remove(long index) {
/* 327 */     if (index >= this.size) {
/* 328 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 330 */     K old = (K)BigArrays.get((Object[][])this.a, index);
/* 331 */     this.size--;
/* 332 */     if (index != this.size)
/* 333 */       BigArrays.copy((Object[][])this.a, index + 1L, (Object[][])this.a, index, this.size - index); 
/* 334 */     BigArrays.set((Object[][])this.a, this.size, null);
/* 335 */     assert this.size <= BigArrays.length((Object[][])this.a);
/* 336 */     return old;
/*     */   }
/*     */   
/*     */   public boolean remove(Object k) {
/* 340 */     long index = indexOf(k);
/* 341 */     if (index == -1L)
/* 342 */       return false; 
/* 343 */     remove(index);
/* 344 */     assert this.size <= BigArrays.length((Object[][])this.a);
/* 345 */     return true;
/*     */   }
/*     */   
/*     */   public K set(long index, K k) {
/* 349 */     if (index >= this.size) {
/* 350 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 352 */     K old = (K)BigArrays.get((Object[][])this.a, index);
/* 353 */     BigArrays.set((Object[][])this.a, index, k);
/* 354 */     return old;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 358 */     K[] s = null, d = null;
/* 359 */     int ss = -1, sd = 134217728, ds = -1, dd = 134217728; long i;
/* 360 */     for (i = 0L; i < this.size; i++) {
/* 361 */       if (sd == 134217728) {
/* 362 */         sd = 0;
/* 363 */         s = this.a[++ss];
/*     */       } 
/* 365 */       if (!c.contains(s[sd])) {
/* 366 */         if (dd == 134217728) {
/* 367 */           d = this.a[++ds];
/* 368 */           dd = 0;
/*     */         } 
/* 370 */         d[dd++] = s[sd];
/*     */       } 
/* 372 */       sd++;
/*     */     } 
/* 374 */     long j = BigArrays.index(ds, dd);
/* 375 */     BigArrays.fill((Object[][])this.a, j, this.size, null);
/* 376 */     boolean modified = (this.size != j);
/* 377 */     this.size = j;
/* 378 */     return modified;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 382 */     BigArrays.fill((Object[][])this.a, 0L, this.size, null);
/* 383 */     this.size = 0L;
/* 384 */     assert this.size <= BigArrays.length((Object[][])this.a);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 388 */     return this.size;
/*     */   }
/*     */   
/*     */   public void size(long size) {
/* 392 */     if (size > BigArrays.length((Object[][])this.a))
/* 393 */       this.a = (K[][])BigArrays.forceCapacity((Object[][])this.a, size, this.size); 
/* 394 */     if (size > this.size) {
/* 395 */       BigArrays.fill((Object[][])this.a, this.size, size, null);
/*     */     } else {
/* 397 */       BigArrays.fill((Object[][])this.a, size, this.size, null);
/* 398 */     }  this.size = size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 402 */     return (this.size == 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 410 */     trim(0L);
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
/* 430 */     long arrayLength = BigArrays.length((Object[][])this.a);
/* 431 */     if (n >= arrayLength || this.size == arrayLength)
/*     */       return; 
/* 433 */     this.a = (K[][])BigArrays.trim((Object[][])this.a, Math.max(n, this.size));
/* 434 */     assert this.size <= BigArrays.length((Object[][])this.a);
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
/*     */   public void getElements(long from, Object[][] a, long offset, long length) {
/* 452 */     BigArrays.copy((Object[][])this.a, from, a, offset, length);
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
/* 464 */     BigArrays.ensureFromTo(this.size, from, to);
/* 465 */     BigArrays.copy((Object[][])this.a, to, (Object[][])this.a, from, this.size - to);
/* 466 */     this.size -= to - from;
/* 467 */     BigArrays.fill((Object[][])this.a, this.size, this.size + to - from, null);
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
/*     */   public void addElements(long index, K[][] a, long offset, long length) {
/* 483 */     ensureIndex(index);
/* 484 */     BigArrays.ensureOffsetLength((Object[][])a, offset, length);
/* 485 */     grow(this.size + length);
/* 486 */     BigArrays.copy((Object[][])this.a, index, (Object[][])this.a, index + length, this.size - index);
/* 487 */     BigArrays.copy((Object[][])a, offset, (Object[][])this.a, index, length);
/* 488 */     this.size += length;
/*     */   }
/*     */   
/*     */   public ObjectBigListIterator<K> listIterator(final long index) {
/* 492 */     ensureIndex(index);
/* 493 */     return new ObjectBigListIterator<K>() {
/* 494 */         long pos = index; long last = -1L;
/*     */         
/*     */         public boolean hasNext() {
/* 497 */           return (this.pos < ObjectBigArrayBigList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 501 */           return (this.pos > 0L);
/*     */         }
/*     */         
/*     */         public K next() {
/* 505 */           if (!hasNext())
/* 506 */             throw new NoSuchElementException(); 
/* 507 */           return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, this.last = this.pos++);
/*     */         }
/*     */         
/*     */         public K previous() {
/* 511 */           if (!hasPrevious())
/* 512 */             throw new NoSuchElementException(); 
/* 513 */           return (K)BigArrays.get((Object[][])ObjectBigArrayBigList.this.a, this.last = --this.pos);
/*     */         }
/*     */         
/*     */         public long nextIndex() {
/* 517 */           return this.pos;
/*     */         }
/*     */         
/*     */         public long previousIndex() {
/* 521 */           return this.pos - 1L;
/*     */         }
/*     */         
/*     */         public void add(K k) {
/* 525 */           ObjectBigArrayBigList.this.add(this.pos++, k);
/* 526 */           this.last = -1L;
/*     */         }
/*     */         
/*     */         public void set(K k) {
/* 530 */           if (this.last == -1L)
/* 531 */             throw new IllegalStateException(); 
/* 532 */           ObjectBigArrayBigList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 536 */           if (this.last == -1L)
/* 537 */             throw new IllegalStateException(); 
/* 538 */           ObjectBigArrayBigList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 543 */           if (this.last < this.pos)
/* 544 */             this.pos--; 
/* 545 */           this.last = -1L;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public ObjectBigArrayBigList<K> clone() {
/* 551 */     ObjectBigArrayBigList<K> c = new ObjectBigArrayBigList(this.size);
/* 552 */     BigArrays.copy((Object[][])this.a, 0L, (Object[][])c.a, 0L, this.size);
/* 553 */     c.size = this.size;
/* 554 */     return c;
/*     */   }
/*     */   private boolean valEquals(K a, K b) {
/* 557 */     return (a == null) ? ((b == null)) : a.equals(b);
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
/*     */   public boolean equals(ObjectBigArrayBigList<K> l) {
/* 572 */     if (l == this)
/* 573 */       return true; 
/* 574 */     long s = size64();
/* 575 */     if (s != l.size64())
/* 576 */       return false; 
/* 577 */     K[][] a1 = this.a;
/* 578 */     K[][] a2 = l.a;
/* 579 */     while (s-- != 0L) {
/* 580 */       if (!valEquals((K)BigArrays.get((Object[][])a1, s), (K)BigArrays.get((Object[][])a2, s)))
/* 581 */         return false; 
/* 582 */     }  return true;
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
/*     */   public int compareTo(ObjectBigArrayBigList<? extends K> l) {
/* 598 */     long s1 = size64(), s2 = l.size64();
/* 599 */     K[][] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 602 */     for (i = 0; i < s1 && i < s2; i++) {
/* 603 */       K e1 = (K)BigArrays.get((Object[][])a1, i);
/* 604 */       K e2 = (K)BigArrays.get((Object[][])a2, i); int r;
/* 605 */       if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0)
/* 606 */         return r; 
/*     */     } 
/* 608 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 611 */     s.defaultWriteObject();
/* 612 */     for (int i = 0; i < this.size; i++)
/* 613 */       s.writeObject(BigArrays.get((Object[][])this.a, i)); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 617 */     s.defaultReadObject();
/* 618 */     this.a = (K[][])ObjectBigArrays.newBigArray(this.size);
/* 619 */     for (int i = 0; i < this.size; i++)
/* 620 */       BigArrays.set((Object[][])this.a, i, s.readObject()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectBigArrayBigList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */