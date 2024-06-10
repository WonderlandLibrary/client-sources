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
/*     */ public class ObjectArrayList<K>
/*     */   extends AbstractObjectList<K>
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353131L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected final boolean wrapped;
/*     */   protected transient K[] a;
/*     */   protected int size;
/*     */   
/*     */   protected ObjectArrayList(K[] a, boolean dummy) {
/*  81 */     this.a = a;
/*  82 */     this.wrapped = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayList(int capacity) {
/*  92 */     if (capacity < 0)
/*  93 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  94 */     if (capacity == 0) {
/*  95 */       this.a = (K[])ObjectArrays.EMPTY_ARRAY;
/*     */     } else {
/*  97 */       this.a = (K[])new Object[capacity];
/*  98 */     }  this.wrapped = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectArrayList() {
/* 103 */     this.a = (K[])ObjectArrays.DEFAULT_EMPTY_ARRAY;
/* 104 */     this.wrapped = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayList(Collection<? extends K> c) {
/* 113 */     this(c.size());
/* 114 */     this.size = ObjectIterators.unwrap(c.iterator(), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayList(ObjectCollection<? extends K> c) {
/* 124 */     this(c.size());
/* 125 */     this.size = ObjectIterators.unwrap(c.iterator(), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayList(ObjectList<? extends K> l) {
/* 134 */     this(l.size());
/* 135 */     l.getElements(0, (Object[])this.a, 0, this.size = l.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayList(K[] a) {
/* 144 */     this(a, 0, a.length);
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
/*     */   public ObjectArrayList(K[] a, int offset, int length) {
/* 157 */     this(length);
/* 158 */     System.arraycopy(a, offset, this.a, 0, length);
/* 159 */     this.size = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayList(Iterator<? extends K> i) {
/* 169 */     this();
/* 170 */     while (i.hasNext()) {
/* 171 */       add(i.next());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectArrayList(ObjectIterator<? extends K> i) {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K[] elements() {
/* 203 */     return this.a;
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
/*     */   public static <K> ObjectArrayList<K> wrap(K[] a, int length) {
/* 220 */     if (length > a.length) {
/* 221 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
/*     */     }
/* 223 */     ObjectArrayList<K> l = new ObjectArrayList<>(a, false);
/* 224 */     l.size = length;
/* 225 */     return l;
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
/*     */   public static <K> ObjectArrayList<K> wrap(K[] a) {
/* 240 */     return wrap(a, a.length);
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
/* 251 */     if (capacity <= this.a.length || (this.a == ObjectArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10))
/*     */       return; 
/* 253 */     if (this.wrapped) {
/* 254 */       this.a = ObjectArrays.ensureCapacity(this.a, capacity, this.size);
/*     */     }
/* 256 */     else if (capacity > this.a.length) {
/* 257 */       Object[] t = new Object[capacity];
/* 258 */       System.arraycopy(this.a, 0, t, 0, this.size);
/* 259 */       this.a = (K[])t;
/*     */     } 
/*     */     
/* 262 */     assert this.size <= this.a.length;
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
/* 274 */     if (capacity <= this.a.length)
/*     */       return; 
/* 276 */     if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
/* 277 */       capacity = (int)Math.max(
/* 278 */           Math.min(this.a.length + (this.a.length >> 1), 2147483639L), capacity);
/* 279 */     } else if (capacity < 10) {
/* 280 */       capacity = 10;
/* 281 */     }  if (this.wrapped) {
/* 282 */       this.a = ObjectArrays.forceCapacity(this.a, capacity, this.size);
/*     */     } else {
/* 284 */       Object[] t = new Object[capacity];
/* 285 */       System.arraycopy(this.a, 0, t, 0, this.size);
/* 286 */       this.a = (K[])t;
/*     */     } 
/* 288 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public void add(int index, K k) {
/* 292 */     ensureIndex(index);
/* 293 */     grow(this.size + 1);
/* 294 */     if (index != this.size)
/* 295 */       System.arraycopy(this.a, index, this.a, index + 1, this.size - index); 
/* 296 */     this.a[index] = k;
/* 297 */     this.size++;
/* 298 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public boolean add(K k) {
/* 302 */     grow(this.size + 1);
/* 303 */     this.a[this.size++] = k;
/* 304 */     assert this.size <= this.a.length;
/* 305 */     return true;
/*     */   }
/*     */   
/*     */   public K get(int index) {
/* 309 */     if (index >= this.size) {
/* 310 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 312 */     return this.a[index];
/*     */   }
/*     */   
/*     */   public int indexOf(Object k) {
/* 316 */     for (int i = 0; i < this.size; i++) {
/* 317 */       if (Objects.equals(k, this.a[i]))
/* 318 */         return i; 
/* 319 */     }  return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(Object k) {
/* 323 */     for (int i = this.size; i-- != 0;) {
/* 324 */       if (Objects.equals(k, this.a[i]))
/* 325 */         return i; 
/* 326 */     }  return -1;
/*     */   }
/*     */   
/*     */   public K remove(int index) {
/* 330 */     if (index >= this.size) {
/* 331 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 333 */     K old = this.a[index];
/* 334 */     this.size--;
/* 335 */     if (index != this.size)
/* 336 */       System.arraycopy(this.a, index + 1, this.a, index, this.size - index); 
/* 337 */     this.a[this.size] = null;
/* 338 */     assert this.size <= this.a.length;
/* 339 */     return old;
/*     */   }
/*     */   
/*     */   public boolean remove(Object k) {
/* 343 */     int index = indexOf(k);
/* 344 */     if (index == -1)
/* 345 */       return false; 
/* 346 */     remove(index);
/* 347 */     assert this.size <= this.a.length;
/* 348 */     return true;
/*     */   }
/*     */   
/*     */   public K set(int index, K k) {
/* 352 */     if (index >= this.size) {
/* 353 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 355 */     K old = this.a[index];
/* 356 */     this.a[index] = k;
/* 357 */     return old;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 361 */     Arrays.fill((Object[])this.a, 0, this.size, (Object)null);
/* 362 */     this.size = 0;
/* 363 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public int size() {
/* 367 */     return this.size;
/*     */   }
/*     */   
/*     */   public void size(int size) {
/* 371 */     if (size > this.a.length)
/* 372 */       this.a = ObjectArrays.forceCapacity(this.a, size, this.size); 
/* 373 */     if (size > this.size) {
/* 374 */       Arrays.fill((Object[])this.a, this.size, size, (Object)null);
/*     */     } else {
/* 376 */       Arrays.fill((Object[])this.a, size, this.size, (Object)null);
/* 377 */     }  this.size = size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 381 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 389 */     trim(0);
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
/* 410 */     if (n >= this.a.length || this.size == this.a.length)
/*     */       return; 
/* 412 */     K[] t = (K[])new Object[Math.max(n, this.size)];
/* 413 */     System.arraycopy(this.a, 0, t, 0, this.size);
/* 414 */     this.a = t;
/* 415 */     assert this.size <= this.a.length;
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
/* 433 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 434 */     System.arraycopy(this.a, from, a, offset, length);
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
/* 446 */     Arrays.ensureFromTo(this.size, from, to);
/* 447 */     System.arraycopy(this.a, to, this.a, from, this.size - to);
/* 448 */     this.size -= to - from;
/* 449 */     int i = to - from;
/* 450 */     while (i-- != 0) {
/* 451 */       this.a[this.size + i] = null;
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
/* 467 */     ensureIndex(index);
/* 468 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 469 */     grow(this.size + length);
/* 470 */     System.arraycopy(this.a, index, this.a, index + length, this.size - index);
/* 471 */     System.arraycopy(a, offset, this.a, index, length);
/* 472 */     this.size += length;
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
/* 488 */     ensureIndex(index);
/* 489 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 490 */     if (index + length > this.size) {
/* 491 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
/*     */     }
/* 493 */     System.arraycopy(a, offset, this.a, index, length);
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 497 */     K[] arrayOfK = this.a;
/* 498 */     int j = 0;
/* 499 */     for (int i = 0; i < this.size; i++) {
/* 500 */       if (!c.contains(arrayOfK[i]))
/* 501 */         arrayOfK[j++] = arrayOfK[i]; 
/* 502 */     }  Arrays.fill((Object[])arrayOfK, j, this.size, (Object)null);
/* 503 */     boolean modified = (this.size != j);
/* 504 */     this.size = j;
/* 505 */     return modified;
/*     */   }
/*     */   
/*     */   public ObjectListIterator<K> listIterator(final int index) {
/* 509 */     ensureIndex(index);
/* 510 */     return new ObjectListIterator<K>() {
/* 511 */         int pos = index; int last = -1;
/*     */         
/*     */         public boolean hasNext() {
/* 514 */           return (this.pos < ObjectArrayList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 518 */           return (this.pos > 0);
/*     */         }
/*     */         
/*     */         public K next() {
/* 522 */           if (!hasNext())
/* 523 */             throw new NoSuchElementException(); 
/* 524 */           return ObjectArrayList.this.a[this.last = this.pos++];
/*     */         }
/*     */         
/*     */         public K previous() {
/* 528 */           if (!hasPrevious())
/* 529 */             throw new NoSuchElementException(); 
/* 530 */           return ObjectArrayList.this.a[this.last = --this.pos];
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 534 */           return this.pos;
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 538 */           return this.pos - 1;
/*     */         }
/*     */         
/*     */         public void add(K k) {
/* 542 */           ObjectArrayList.this.add(this.pos++, k);
/* 543 */           this.last = -1;
/*     */         }
/*     */         
/*     */         public void set(K k) {
/* 547 */           if (this.last == -1)
/* 548 */             throw new IllegalStateException(); 
/* 549 */           ObjectArrayList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 553 */           if (this.last == -1)
/* 554 */             throw new IllegalStateException(); 
/* 555 */           ObjectArrayList.this.remove(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 560 */           if (this.last < this.pos)
/* 561 */             this.pos--; 
/* 562 */           this.last = -1;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void sort(Comparator<? super K> comp) {
/* 568 */     if (comp == null) {
/* 569 */       ObjectArrays.stableSort(this.a, 0, this.size);
/*     */     } else {
/* 571 */       ObjectArrays.stableSort(this.a, 0, this.size, (Comparator)comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unstableSort(Comparator<? super K> comp) {
/* 576 */     if (comp == null) {
/* 577 */       ObjectArrays.unstableSort(this.a, 0, this.size);
/*     */     } else {
/* 579 */       ObjectArrays.unstableSort(this.a, 0, this.size, (Comparator)comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ObjectArrayList<K> clone() {
/* 584 */     ObjectArrayList<K> c = new ObjectArrayList(this.size);
/* 585 */     System.arraycopy(this.a, 0, c.a, 0, this.size);
/* 586 */     c.size = this.size;
/* 587 */     return c;
/*     */   }
/*     */   private boolean valEquals(K a, K b) {
/* 590 */     return (a == null) ? ((b == null)) : a.equals(b);
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
/*     */   public boolean equals(ObjectArrayList<K> l) {
/* 605 */     if (l == this)
/* 606 */       return true; 
/* 607 */     int s = size();
/* 608 */     if (s != l.size())
/* 609 */       return false; 
/* 610 */     K[] a1 = this.a;
/* 611 */     K[] a2 = l.a;
/* 612 */     while (s-- != 0) {
/* 613 */       if (!valEquals(a1[s], a2[s]))
/* 614 */         return false; 
/* 615 */     }  return true;
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
/*     */   public int compareTo(ObjectArrayList<? extends K> l) {
/* 631 */     int s1 = size(), s2 = l.size();
/* 632 */     K[] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 635 */     for (i = 0; i < s1 && i < s2; i++) {
/* 636 */       K e1 = a1[i];
/* 637 */       K e2 = a2[i]; int r;
/* 638 */       if ((r = ((Comparable<K>)e1).compareTo(e2)) != 0)
/* 639 */         return r; 
/*     */     } 
/* 641 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 644 */     s.defaultWriteObject();
/* 645 */     for (int i = 0; i < this.size; i++)
/* 646 */       s.writeObject(this.a[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 650 */     s.defaultReadObject();
/* 651 */     this.a = (K[])new Object[this.size];
/* 652 */     for (int i = 0; i < this.size; i++)
/* 653 */       this.a[i] = (K)s.readObject(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */