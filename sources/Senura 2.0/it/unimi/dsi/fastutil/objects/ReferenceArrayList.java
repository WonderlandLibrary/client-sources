/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Arrays;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceArrayList<K>
/*     */   extends AbstractReferenceList<K>
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353131L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected final boolean wrapped;
/*     */   protected transient K[] a;
/*     */   protected int size;
/*     */   
/*     */   protected ReferenceArrayList(K[] a, boolean dummy) {
/*  85 */     this.a = a;
/*  86 */     this.wrapped = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArrayList(int capacity) {
/*  96 */     if (capacity < 0)
/*  97 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  98 */     if (capacity == 0) {
/*  99 */       this.a = (K[])ObjectArrays.EMPTY_ARRAY;
/*     */     } else {
/* 101 */       this.a = (K[])new Object[capacity];
/* 102 */     }  this.wrapped = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ReferenceArrayList() {
/* 107 */     this.a = (K[])ObjectArrays.DEFAULT_EMPTY_ARRAY;
/* 108 */     this.wrapped = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArrayList(Collection<? extends K> c) {
/* 117 */     this(c.size());
/* 118 */     this.size = ObjectIterators.unwrap(c.iterator(), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArrayList(ReferenceCollection<? extends K> c) {
/* 128 */     this(c.size());
/* 129 */     this.size = ObjectIterators.unwrap(c.iterator(), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArrayList(ReferenceList<? extends K> l) {
/* 138 */     this(l.size());
/* 139 */     l.getElements(0, (Object[])this.a, 0, this.size = l.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArrayList(K[] a) {
/* 148 */     this(a, 0, a.length);
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
/*     */   public ReferenceArrayList(K[] a, int offset, int length) {
/* 161 */     this(length);
/* 162 */     System.arraycopy(a, offset, this.a, 0, length);
/* 163 */     this.size = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArrayList(Iterator<? extends K> i) {
/* 173 */     this();
/* 174 */     while (i.hasNext()) {
/* 175 */       add(i.next());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceArrayList(ObjectIterator<? extends K> i) {
/* 186 */     this();
/* 187 */     while (i.hasNext()) {
/* 188 */       add(i.next());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K[] elements() {
/* 207 */     return this.a;
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
/*     */   public static <K> ReferenceArrayList<K> wrap(K[] a, int length) {
/* 224 */     if (length > a.length) {
/* 225 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
/*     */     }
/* 227 */     ReferenceArrayList<K> l = new ReferenceArrayList<>(a, false);
/* 228 */     l.size = length;
/* 229 */     return l;
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
/*     */   public static <K> ReferenceArrayList<K> wrap(K[] a) {
/* 244 */     return wrap(a, a.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/* 255 */     if (capacity <= this.a.length || (this.a == ObjectArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10))
/*     */       return; 
/* 257 */     if (this.wrapped) {
/* 258 */       this.a = ObjectArrays.ensureCapacity(this.a, capacity, this.size);
/*     */     }
/* 260 */     else if (capacity > this.a.length) {
/* 261 */       Object[] t = new Object[capacity];
/* 262 */       System.arraycopy(this.a, 0, t, 0, this.size);
/* 263 */       this.a = (K[])t;
/*     */     } 
/*     */     
/* 266 */     assert this.size <= this.a.length;
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
/*     */   private void grow(int capacity) {
/* 278 */     if (capacity <= this.a.length)
/*     */       return; 
/* 280 */     if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
/* 281 */       capacity = (int)Math.max(
/* 282 */           Math.min(this.a.length + (this.a.length >> 1), 2147483639L), capacity);
/* 283 */     } else if (capacity < 10) {
/* 284 */       capacity = 10;
/* 285 */     }  if (this.wrapped) {
/* 286 */       this.a = ObjectArrays.forceCapacity(this.a, capacity, this.size);
/*     */     } else {
/* 288 */       Object[] t = new Object[capacity];
/* 289 */       System.arraycopy(this.a, 0, t, 0, this.size);
/* 290 */       this.a = (K[])t;
/*     */     } 
/* 292 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public void add(int index, K k) {
/* 296 */     ensureIndex(index);
/* 297 */     grow(this.size + 1);
/* 298 */     if (index != this.size)
/* 299 */       System.arraycopy(this.a, index, this.a, index + 1, this.size - index); 
/* 300 */     this.a[index] = k;
/* 301 */     this.size++;
/* 302 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public boolean add(K k) {
/* 306 */     grow(this.size + 1);
/* 307 */     this.a[this.size++] = k;
/* 308 */     assert this.size <= this.a.length;
/* 309 */     return true;
/*     */   }
/*     */   
/*     */   public K get(int index) {
/* 313 */     if (index >= this.size) {
/* 314 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 316 */     return this.a[index];
/*     */   }
/*     */   
/*     */   public int indexOf(Object k) {
/* 320 */     for (int i = 0; i < this.size; i++) {
/* 321 */       if (k == this.a[i])
/* 322 */         return i; 
/* 323 */     }  return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object k) {
/* 327 */     for (int i = this.size; i-- != 0;) {
/* 328 */       if (k == this.a[i])
/* 329 */         return i; 
/* 330 */     }  return -1;
/*     */   }
/*     */   
/*     */   public K remove(int index) {
/* 334 */     if (index >= this.size) {
/* 335 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 337 */     K old = this.a[index];
/* 338 */     this.size--;
/* 339 */     if (index != this.size)
/* 340 */       System.arraycopy(this.a, index + 1, this.a, index, this.size - index); 
/* 341 */     this.a[this.size] = null;
/* 342 */     assert this.size <= this.a.length;
/* 343 */     return old;
/*     */   }
/*     */   
/*     */   public boolean remove(Object k) {
/* 347 */     int index = indexOf(k);
/* 348 */     if (index == -1)
/* 349 */       return false; 
/* 350 */     remove(index);
/* 351 */     assert this.size <= this.a.length;
/* 352 */     return true;
/*     */   }
/*     */   
/*     */   public K set(int index, K k) {
/* 356 */     if (index >= this.size) {
/* 357 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 359 */     K old = this.a[index];
/* 360 */     this.a[index] = k;
/* 361 */     return old;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 365 */     Arrays.fill((Object[])this.a, 0, this.size, (Object)null);
/* 366 */     this.size = 0;
/* 367 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public int size() {
/* 371 */     return this.size;
/*     */   }
/*     */   
/*     */   public void size(int size) {
/* 375 */     if (size > this.a.length)
/* 376 */       this.a = ObjectArrays.forceCapacity(this.a, size, this.size); 
/* 377 */     if (size > this.size) {
/* 378 */       Arrays.fill((Object[])this.a, this.size, size, (Object)null);
/*     */     } else {
/* 380 */       Arrays.fill((Object[])this.a, size, this.size, (Object)null);
/* 381 */     }  this.size = size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 385 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 393 */     trim(0);
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
/*     */   public void trim(int n) {
/* 414 */     if (n >= this.a.length || this.size == this.a.length)
/*     */       return; 
/* 416 */     K[] t = (K[])new Object[Math.max(n, this.size)];
/* 417 */     System.arraycopy(this.a, 0, t, 0, this.size);
/* 418 */     this.a = t;
/* 419 */     assert this.size <= this.a.length;
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
/*     */   public void getElements(int from, Object[] a, int offset, int length) {
/* 437 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 438 */     System.arraycopy(this.a, from, a, offset, length);
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
/*     */   public void removeElements(int from, int to) {
/* 450 */     Arrays.ensureFromTo(this.size, from, to);
/* 451 */     System.arraycopy(this.a, to, this.a, from, this.size - to);
/* 452 */     this.size -= to - from;
/* 453 */     int i = to - from;
/* 454 */     while (i-- != 0) {
/* 455 */       this.a[this.size + i] = null;
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
/*     */ 
/*     */   
/*     */   public void addElements(int index, K[] a, int offset, int length) {
/* 471 */     ensureIndex(index);
/* 472 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 473 */     grow(this.size + length);
/* 474 */     System.arraycopy(this.a, index, this.a, index + length, this.size - index);
/* 475 */     System.arraycopy(a, offset, this.a, index, length);
/* 476 */     this.size += length;
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
/*     */   public void setElements(int index, K[] a, int offset, int length) {
/* 492 */     ensureIndex(index);
/* 493 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 494 */     if (index + length > this.size) {
/* 495 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
/*     */     }
/* 497 */     System.arraycopy(a, offset, this.a, index, length);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 501 */     K[] arrayOfK = this.a;
/* 502 */     int j = 0;
/* 503 */     for (int i = 0; i < this.size; i++) {
/* 504 */       if (!c.contains(arrayOfK[i]))
/* 505 */         arrayOfK[j++] = arrayOfK[i]; 
/* 506 */     }  Arrays.fill((Object[])arrayOfK, j, this.size, (Object)null);
/* 507 */     boolean modified = (this.size != j);
/* 508 */     this.size = j;
/* 509 */     return modified;
/*     */   }
/*     */   
/*     */   public ObjectListIterator<K> listIterator(final int index) {
/* 513 */     ensureIndex(index);
/* 514 */     return new ObjectListIterator<K>() {
/* 515 */         int pos = index; int last = -1;
/*     */         
/*     */         public boolean hasNext() {
/* 518 */           return (this.pos < ReferenceArrayList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 522 */           return (this.pos > 0);
/*     */         }
/*     */         
/*     */         public K next() {
/* 526 */           if (!hasNext())
/* 527 */             throw new NoSuchElementException(); 
/* 528 */           return ReferenceArrayList.this.a[this.last = this.pos++];
/*     */         }
/*     */         
/*     */         public K previous() {
/* 532 */           if (!hasPrevious())
/* 533 */             throw new NoSuchElementException(); 
/* 534 */           return ReferenceArrayList.this.a[this.last = --this.pos];
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 538 */           return this.pos;
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 542 */           return this.pos - 1;
/*     */         }
/*     */         
/*     */         public void add(K k) {
/* 546 */           ReferenceArrayList.this.add(this.pos++, k);
/* 547 */           this.last = -1;
/*     */         }
/*     */         
/*     */         public void set(K k) {
/* 551 */           if (this.last == -1)
/* 552 */             throw new IllegalStateException(); 
/* 553 */           ReferenceArrayList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 557 */           if (this.last == -1)
/* 558 */             throw new IllegalStateException(); 
/* 559 */           ReferenceArrayList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 564 */           if (this.last < this.pos)
/* 565 */             this.pos--; 
/* 566 */           this.last = -1;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void sort(Comparator<? super K> comp) {
/* 572 */     if (comp == null) {
/* 573 */       ObjectArrays.stableSort(this.a, 0, this.size);
/*     */     } else {
/* 575 */       ObjectArrays.stableSort(this.a, 0, this.size, (Comparator)comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unstableSort(Comparator<? super K> comp) {
/* 580 */     if (comp == null) {
/* 581 */       ObjectArrays.unstableSort(this.a, 0, this.size);
/*     */     } else {
/* 583 */       ObjectArrays.unstableSort(this.a, 0, this.size, (Comparator)comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ReferenceArrayList<K> clone() {
/* 588 */     ReferenceArrayList<K> c = new ReferenceArrayList(this.size);
/* 589 */     System.arraycopy(this.a, 0, c.a, 0, this.size);
/* 590 */     c.size = this.size;
/* 591 */     return c;
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
/*     */   public boolean equals(ReferenceArrayList<K> l) {
/* 606 */     if (l == this)
/* 607 */       return true; 
/* 608 */     int s = size();
/* 609 */     if (s != l.size())
/* 610 */       return false; 
/* 611 */     K[] a1 = this.a;
/* 612 */     K[] a2 = l.a;
/* 613 */     while (s-- != 0) {
/* 614 */       if (a1[s] != a2[s])
/* 615 */         return false; 
/* 616 */     }  return true;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 619 */     s.defaultWriteObject();
/* 620 */     for (int i = 0; i < this.size; i++)
/* 621 */       s.writeObject(this.a[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 625 */     s.defaultReadObject();
/* 626 */     this.a = (K[])new Object[this.size];
/* 627 */     for (int i = 0; i < this.size; i++)
/* 628 */       this.a[i] = (K)s.readObject(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */