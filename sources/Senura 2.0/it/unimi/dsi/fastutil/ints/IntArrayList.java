/*     */ package it.unimi.dsi.fastutil.ints;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Arrays;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
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
/*     */ public class IntArrayList
/*     */   extends AbstractIntList
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353130L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected transient int[] a;
/*     */   protected int size;
/*     */   
/*     */   protected IntArrayList(int[] a, boolean dummy) {
/*  66 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayList(int capacity) {
/*  76 */     if (capacity < 0)
/*  77 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  78 */     if (capacity == 0) {
/*  79 */       this.a = IntArrays.EMPTY_ARRAY;
/*     */     } else {
/*  81 */       this.a = new int[capacity];
/*     */     } 
/*     */   }
/*     */   
/*     */   public IntArrayList() {
/*  86 */     this.a = IntArrays.DEFAULT_EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayList(Collection<? extends Integer> c) {
/*  95 */     this(c.size());
/*  96 */     this.size = IntIterators.unwrap(IntIterators.asIntIterator(c.iterator()), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayList(IntCollection c) {
/* 106 */     this(c.size());
/* 107 */     this.size = IntIterators.unwrap(c.iterator(), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayList(IntList l) {
/* 116 */     this(l.size());
/* 117 */     l.getElements(0, this.a, 0, this.size = l.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayList(int[] a) {
/* 126 */     this(a, 0, a.length);
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
/*     */   public IntArrayList(int[] a, int offset, int length) {
/* 139 */     this(length);
/* 140 */     System.arraycopy(a, offset, this.a, 0, length);
/* 141 */     this.size = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayList(Iterator<? extends Integer> i) {
/* 151 */     this();
/* 152 */     while (i.hasNext()) {
/* 153 */       add(((Integer)i.next()).intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayList(IntIterator i) {
/* 164 */     this();
/* 165 */     while (i.hasNext()) {
/* 166 */       add(i.nextInt());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] elements() {
/* 174 */     return this.a;
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
/*     */   public static IntArrayList wrap(int[] a, int length) {
/* 191 */     if (length > a.length) {
/* 192 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
/*     */     }
/* 194 */     IntArrayList l = new IntArrayList(a, false);
/* 195 */     l.size = length;
/* 196 */     return l;
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
/*     */   public static IntArrayList wrap(int[] a) {
/* 211 */     return wrap(a, a.length);
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
/* 222 */     if (capacity <= this.a.length || (this.a == IntArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10))
/*     */       return; 
/* 224 */     this.a = IntArrays.ensureCapacity(this.a, capacity, this.size);
/* 225 */     assert this.size <= this.a.length;
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
/* 237 */     if (capacity <= this.a.length)
/*     */       return; 
/* 239 */     if (this.a != IntArrays.DEFAULT_EMPTY_ARRAY) {
/* 240 */       capacity = (int)Math.max(
/* 241 */           Math.min(this.a.length + (this.a.length >> 1), 2147483639L), capacity);
/* 242 */     } else if (capacity < 10) {
/* 243 */       capacity = 10;
/* 244 */     }  this.a = IntArrays.forceCapacity(this.a, capacity, this.size);
/* 245 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public void add(int index, int k) {
/* 249 */     ensureIndex(index);
/* 250 */     grow(this.size + 1);
/* 251 */     if (index != this.size)
/* 252 */       System.arraycopy(this.a, index, this.a, index + 1, this.size - index); 
/* 253 */     this.a[index] = k;
/* 254 */     this.size++;
/* 255 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public boolean add(int k) {
/* 259 */     grow(this.size + 1);
/* 260 */     this.a[this.size++] = k;
/* 261 */     assert this.size <= this.a.length;
/* 262 */     return true;
/*     */   }
/*     */   
/*     */   public int getInt(int index) {
/* 266 */     if (index >= this.size) {
/* 267 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 269 */     return this.a[index];
/*     */   }
/*     */   
/*     */   public int indexOf(int k) {
/* 273 */     for (int i = 0; i < this.size; i++) {
/* 274 */       if (k == this.a[i])
/* 275 */         return i; 
/* 276 */     }  return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(int k) {
/* 280 */     for (int i = this.size; i-- != 0;) {
/* 281 */       if (k == this.a[i])
/* 282 */         return i; 
/* 283 */     }  return -1;
/*     */   }
/*     */   
/*     */   public int removeInt(int index) {
/* 287 */     if (index >= this.size) {
/* 288 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 290 */     int old = this.a[index];
/* 291 */     this.size--;
/* 292 */     if (index != this.size)
/* 293 */       System.arraycopy(this.a, index + 1, this.a, index, this.size - index); 
/* 294 */     assert this.size <= this.a.length;
/* 295 */     return old;
/*     */   }
/*     */   
/*     */   public boolean rem(int k) {
/* 299 */     int index = indexOf(k);
/* 300 */     if (index == -1)
/* 301 */       return false; 
/* 302 */     removeInt(index);
/* 303 */     assert this.size <= this.a.length;
/* 304 */     return true;
/*     */   }
/*     */   
/*     */   public int set(int index, int k) {
/* 308 */     if (index >= this.size) {
/* 309 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 311 */     int old = this.a[index];
/* 312 */     this.a[index] = k;
/* 313 */     return old;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 317 */     this.size = 0;
/* 318 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public int size() {
/* 322 */     return this.size;
/*     */   }
/*     */   
/*     */   public void size(int size) {
/* 326 */     if (size > this.a.length)
/* 327 */       this.a = IntArrays.forceCapacity(this.a, size, this.size); 
/* 328 */     if (size > this.size)
/* 329 */       Arrays.fill(this.a, this.size, size, 0); 
/* 330 */     this.size = size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 334 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 342 */     trim(0);
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
/* 363 */     if (n >= this.a.length || this.size == this.a.length)
/*     */       return; 
/* 365 */     int[] t = new int[Math.max(n, this.size)];
/* 366 */     System.arraycopy(this.a, 0, t, 0, this.size);
/* 367 */     this.a = t;
/* 368 */     assert this.size <= this.a.length;
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
/*     */   public void getElements(int from, int[] a, int offset, int length) {
/* 386 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 387 */     System.arraycopy(this.a, from, a, offset, length);
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
/* 399 */     Arrays.ensureFromTo(this.size, from, to);
/* 400 */     System.arraycopy(this.a, to, this.a, from, this.size - to);
/* 401 */     this.size -= to - from;
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
/*     */   public void addElements(int index, int[] a, int offset, int length) {
/* 417 */     ensureIndex(index);
/* 418 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 419 */     grow(this.size + length);
/* 420 */     System.arraycopy(this.a, index, this.a, index + length, this.size - index);
/* 421 */     System.arraycopy(a, offset, this.a, index, length);
/* 422 */     this.size += length;
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
/*     */   public void setElements(int index, int[] a, int offset, int length) {
/* 438 */     ensureIndex(index);
/* 439 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 440 */     if (index + length > this.size) {
/* 441 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
/*     */     }
/* 443 */     System.arraycopy(a, offset, this.a, index, length);
/*     */   }
/*     */   
/*     */   public int[] toArray(int[] a) {
/* 447 */     if (a == null || a.length < this.size)
/* 448 */       a = new int[this.size]; 
/* 449 */     System.arraycopy(this.a, 0, a, 0, this.size);
/* 450 */     return a;
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, IntCollection c) {
/* 454 */     ensureIndex(index);
/* 455 */     int n = c.size();
/* 456 */     if (n == 0)
/* 457 */       return false; 
/* 458 */     grow(this.size + n);
/* 459 */     if (index != this.size)
/* 460 */       System.arraycopy(this.a, index, this.a, index + n, this.size - index); 
/* 461 */     IntIterator i = c.iterator();
/* 462 */     this.size += n;
/* 463 */     while (n-- != 0)
/* 464 */       this.a[index++] = i.nextInt(); 
/* 465 */     assert this.size <= this.a.length;
/* 466 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, IntList l) {
/* 470 */     ensureIndex(index);
/* 471 */     int n = l.size();
/* 472 */     if (n == 0)
/* 473 */       return false; 
/* 474 */     grow(this.size + n);
/* 475 */     if (index != this.size)
/* 476 */       System.arraycopy(this.a, index, this.a, index + n, this.size - index); 
/* 477 */     l.getElements(0, this.a, index, n);
/* 478 */     this.size += n;
/* 479 */     assert this.size <= this.a.length;
/* 480 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removeAll(IntCollection c) {
/* 484 */     int[] a = this.a;
/* 485 */     int j = 0;
/* 486 */     for (int i = 0; i < this.size; i++) {
/* 487 */       if (!c.contains(a[i]))
/* 488 */         a[j++] = a[i]; 
/* 489 */     }  boolean modified = (this.size != j);
/* 490 */     this.size = j;
/* 491 */     return modified;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 495 */     int[] a = this.a;
/* 496 */     int j = 0;
/* 497 */     for (int i = 0; i < this.size; i++) {
/* 498 */       if (!c.contains(Integer.valueOf(a[i])))
/* 499 */         a[j++] = a[i]; 
/* 500 */     }  boolean modified = (this.size != j);
/* 501 */     this.size = j;
/* 502 */     return modified;
/*     */   }
/*     */   
/*     */   public IntListIterator listIterator(final int index) {
/* 506 */     ensureIndex(index);
/* 507 */     return new IntListIterator() {
/* 508 */         int pos = index; int last = -1;
/*     */         
/*     */         public boolean hasNext() {
/* 511 */           return (this.pos < IntArrayList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 515 */           return (this.pos > 0);
/*     */         }
/*     */         
/*     */         public int nextInt() {
/* 519 */           if (!hasNext())
/* 520 */             throw new NoSuchElementException(); 
/* 521 */           return IntArrayList.this.a[this.last = this.pos++];
/*     */         }
/*     */         
/*     */         public int previousInt() {
/* 525 */           if (!hasPrevious())
/* 526 */             throw new NoSuchElementException(); 
/* 527 */           return IntArrayList.this.a[this.last = --this.pos];
/*     */         }
/*     */         
/*     */         public int nextIndex() {
/* 531 */           return this.pos;
/*     */         }
/*     */         
/*     */         public int previousIndex() {
/* 535 */           return this.pos - 1;
/*     */         }
/*     */         
/*     */         public void add(int k) {
/* 539 */           IntArrayList.this.add(this.pos++, k);
/* 540 */           this.last = -1;
/*     */         }
/*     */         
/*     */         public void set(int k) {
/* 544 */           if (this.last == -1)
/* 545 */             throw new IllegalStateException(); 
/* 546 */           IntArrayList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 550 */           if (this.last == -1)
/* 551 */             throw new IllegalStateException(); 
/* 552 */           IntArrayList.this.removeInt(this.last);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 557 */           if (this.last < this.pos)
/* 558 */             this.pos--; 
/* 559 */           this.last = -1;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void sort(IntComparator comp) {
/* 565 */     if (comp == null) {
/* 566 */       IntArrays.stableSort(this.a, 0, this.size);
/*     */     } else {
/* 568 */       IntArrays.stableSort(this.a, 0, this.size, comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unstableSort(IntComparator comp) {
/* 573 */     if (comp == null) {
/* 574 */       IntArrays.unstableSort(this.a, 0, this.size);
/*     */     } else {
/* 576 */       IntArrays.unstableSort(this.a, 0, this.size, comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public IntArrayList clone() {
/* 581 */     IntArrayList c = new IntArrayList(this.size);
/* 582 */     System.arraycopy(this.a, 0, c.a, 0, this.size);
/* 583 */     c.size = this.size;
/* 584 */     return c;
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
/*     */   public boolean equals(IntArrayList l) {
/* 599 */     if (l == this)
/* 600 */       return true; 
/* 601 */     int s = size();
/* 602 */     if (s != l.size())
/* 603 */       return false; 
/* 604 */     int[] a1 = this.a;
/* 605 */     int[] a2 = l.a;
/* 606 */     while (s-- != 0) {
/* 607 */       if (a1[s] != a2[s])
/* 608 */         return false; 
/* 609 */     }  return true;
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
/*     */   public int compareTo(IntArrayList l) {
/* 625 */     int s1 = size(), s2 = l.size();
/* 626 */     int[] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 629 */     for (i = 0; i < s1 && i < s2; i++) {
/* 630 */       int e1 = a1[i];
/* 631 */       int e2 = a2[i]; int r;
/* 632 */       if ((r = Integer.compare(e1, e2)) != 0)
/* 633 */         return r; 
/*     */     } 
/* 635 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 638 */     s.defaultWriteObject();
/* 639 */     for (int i = 0; i < this.size; i++)
/* 640 */       s.writeInt(this.a[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 644 */     s.defaultReadObject();
/* 645 */     this.a = new int[this.size];
/* 646 */     for (int i = 0; i < this.size; i++)
/* 647 */       this.a[i] = s.readInt(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */