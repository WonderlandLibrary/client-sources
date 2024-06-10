/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteArrayList
/*     */   extends AbstractByteList
/*     */   implements RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7046029254386353130L;
/*     */   public static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   protected transient byte[] a;
/*     */   protected int size;
/*     */   
/*     */   protected ByteArrayList(byte[] a, boolean dummy) {
/*  66 */     this.a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayList(int capacity) {
/*  76 */     if (capacity < 0)
/*  77 */       throw new IllegalArgumentException("Initial capacity (" + capacity + ") is negative"); 
/*  78 */     if (capacity == 0) {
/*  79 */       this.a = ByteArrays.EMPTY_ARRAY;
/*     */     } else {
/*  81 */       this.a = new byte[capacity];
/*     */     } 
/*     */   }
/*     */   
/*     */   public ByteArrayList() {
/*  86 */     this.a = ByteArrays.DEFAULT_EMPTY_ARRAY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayList(Collection<? extends Byte> c) {
/*  95 */     this(c.size());
/*  96 */     this.size = ByteIterators.unwrap(ByteIterators.asByteIterator(c.iterator()), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayList(ByteCollection c) {
/* 106 */     this(c.size());
/* 107 */     this.size = ByteIterators.unwrap(c.iterator(), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayList(ByteList l) {
/* 116 */     this(l.size());
/* 117 */     l.getElements(0, this.a, 0, this.size = l.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayList(byte[] a) {
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
/*     */   public ByteArrayList(byte[] a, int offset, int length) {
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
/*     */   public ByteArrayList(Iterator<? extends Byte> i) {
/* 151 */     this();
/* 152 */     while (i.hasNext()) {
/* 153 */       add(((Byte)i.next()).byteValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayList(ByteIterator i) {
/* 164 */     this();
/* 165 */     while (i.hasNext()) {
/* 166 */       add(i.nextByte());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] elements() {
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
/*     */   public static ByteArrayList wrap(byte[] a, int length) {
/* 191 */     if (length > a.length) {
/* 192 */       throw new IllegalArgumentException("The specified length (" + length + ") is greater than the array size (" + a.length + ")");
/*     */     }
/* 194 */     ByteArrayList l = new ByteArrayList(a, false);
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
/*     */   public static ByteArrayList wrap(byte[] a) {
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
/* 222 */     if (capacity <= this.a.length || (this.a == ByteArrays.DEFAULT_EMPTY_ARRAY && capacity <= 10))
/*     */       return; 
/* 224 */     this.a = ByteArrays.ensureCapacity(this.a, capacity, this.size);
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
/* 239 */     if (this.a != ByteArrays.DEFAULT_EMPTY_ARRAY) {
/* 240 */       capacity = (int)Math.max(
/* 241 */           Math.min(this.a.length + (this.a.length >> 1), 2147483639L), capacity);
/* 242 */     } else if (capacity < 10) {
/* 243 */       capacity = 10;
/* 244 */     }  this.a = ByteArrays.forceCapacity(this.a, capacity, this.size);
/* 245 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public void add(int index, byte k) {
/* 249 */     ensureIndex(index);
/* 250 */     grow(this.size + 1);
/* 251 */     if (index != this.size)
/* 252 */       System.arraycopy(this.a, index, this.a, index + 1, this.size - index); 
/* 253 */     this.a[index] = k;
/* 254 */     this.size++;
/* 255 */     assert this.size <= this.a.length;
/*     */   }
/*     */   
/*     */   public boolean add(byte k) {
/* 259 */     grow(this.size + 1);
/* 260 */     this.a[this.size++] = k;
/* 261 */     assert this.size <= this.a.length;
/* 262 */     return true;
/*     */   }
/*     */   
/*     */   public byte getByte(int index) {
/* 266 */     if (index >= this.size) {
/* 267 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 269 */     return this.a[index];
/*     */   }
/*     */   
/*     */   public int indexOf(byte k) {
/* 273 */     for (int i = 0; i < this.size; i++) {
/* 274 */       if (k == this.a[i])
/* 275 */         return i; 
/* 276 */     }  return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(byte k) {
/* 280 */     for (int i = this.size; i-- != 0;) {
/* 281 */       if (k == this.a[i])
/* 282 */         return i; 
/* 283 */     }  return -1;
/*     */   }
/*     */   
/*     */   public byte removeByte(int index) {
/* 287 */     if (index >= this.size) {
/* 288 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 290 */     byte old = this.a[index];
/* 291 */     this.size--;
/* 292 */     if (index != this.size)
/* 293 */       System.arraycopy(this.a, index + 1, this.a, index, this.size - index); 
/* 294 */     assert this.size <= this.a.length;
/* 295 */     return old;
/*     */   }
/*     */   
/*     */   public boolean rem(byte k) {
/* 299 */     int index = indexOf(k);
/* 300 */     if (index == -1)
/* 301 */       return false; 
/* 302 */     removeByte(index);
/* 303 */     assert this.size <= this.a.length;
/* 304 */     return true;
/*     */   }
/*     */   
/*     */   public byte set(int index, byte k) {
/* 308 */     if (index >= this.size) {
/* 309 */       throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size + ")");
/*     */     }
/* 311 */     byte old = this.a[index];
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
/* 327 */       this.a = ByteArrays.forceCapacity(this.a, size, this.size); 
/* 328 */     if (size > this.size)
/* 329 */       Arrays.fill(this.a, this.size, size, (byte)0); 
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
/* 365 */     byte[] t = new byte[Math.max(n, this.size)];
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
/*     */   public void getElements(int from, byte[] a, int offset, int length) {
/* 386 */     ByteArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public void addElements(int index, byte[] a, int offset, int length) {
/* 417 */     ensureIndex(index);
/* 418 */     ByteArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public void setElements(int index, byte[] a, int offset, int length) {
/* 438 */     ensureIndex(index);
/* 439 */     ByteArrays.ensureOffsetLength(a, offset, length);
/* 440 */     if (index + length > this.size) {
/* 441 */       throw new IndexOutOfBoundsException("End index (" + (index + length) + ") is greater than list size (" + this.size + ")");
/*     */     }
/* 443 */     System.arraycopy(a, offset, this.a, index, length);
/*     */   }
/*     */   
/*     */   public byte[] toArray(byte[] a) {
/* 447 */     if (a == null || a.length < this.size)
/* 448 */       a = new byte[this.size]; 
/* 449 */     System.arraycopy(this.a, 0, a, 0, this.size);
/* 450 */     return a;
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, ByteCollection c) {
/* 454 */     ensureIndex(index);
/* 455 */     int n = c.size();
/* 456 */     if (n == 0)
/* 457 */       return false; 
/* 458 */     grow(this.size + n);
/* 459 */     if (index != this.size)
/* 460 */       System.arraycopy(this.a, index, this.a, index + n, this.size - index); 
/* 461 */     ByteIterator i = c.iterator();
/* 462 */     this.size += n;
/* 463 */     while (n-- != 0)
/* 464 */       this.a[index++] = i.nextByte(); 
/* 465 */     assert this.size <= this.a.length;
/* 466 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addAll(int index, ByteList l) {
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
/*     */   public boolean removeAll(ByteCollection c) {
/* 484 */     byte[] a = this.a;
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
/* 495 */     byte[] a = this.a;
/* 496 */     int j = 0;
/* 497 */     for (int i = 0; i < this.size; i++) {
/* 498 */       if (!c.contains(Byte.valueOf(a[i])))
/* 499 */         a[j++] = a[i]; 
/* 500 */     }  boolean modified = (this.size != j);
/* 501 */     this.size = j;
/* 502 */     return modified;
/*     */   }
/*     */   
/*     */   public ByteListIterator listIterator(final int index) {
/* 506 */     ensureIndex(index);
/* 507 */     return new ByteListIterator() {
/* 508 */         int pos = index; int last = -1;
/*     */         
/*     */         public boolean hasNext() {
/* 511 */           return (this.pos < ByteArrayList.this.size);
/*     */         }
/*     */         
/*     */         public boolean hasPrevious() {
/* 515 */           return (this.pos > 0);
/*     */         }
/*     */         
/*     */         public byte nextByte() {
/* 519 */           if (!hasNext())
/* 520 */             throw new NoSuchElementException(); 
/* 521 */           return ByteArrayList.this.a[this.last = this.pos++];
/*     */         }
/*     */         
/*     */         public byte previousByte() {
/* 525 */           if (!hasPrevious())
/* 526 */             throw new NoSuchElementException(); 
/* 527 */           return ByteArrayList.this.a[this.last = --this.pos];
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
/*     */         public void add(byte k) {
/* 539 */           ByteArrayList.this.add(this.pos++, k);
/* 540 */           this.last = -1;
/*     */         }
/*     */         
/*     */         public void set(byte k) {
/* 544 */           if (this.last == -1)
/* 545 */             throw new IllegalStateException(); 
/* 546 */           ByteArrayList.this.set(this.last, k);
/*     */         }
/*     */         
/*     */         public void remove() {
/* 550 */           if (this.last == -1)
/* 551 */             throw new IllegalStateException(); 
/* 552 */           ByteArrayList.this.removeByte(this.last);
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
/*     */   public void sort(ByteComparator comp) {
/* 565 */     if (comp == null) {
/* 566 */       ByteArrays.stableSort(this.a, 0, this.size);
/*     */     } else {
/* 568 */       ByteArrays.stableSort(this.a, 0, this.size, comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unstableSort(ByteComparator comp) {
/* 573 */     if (comp == null) {
/* 574 */       ByteArrays.unstableSort(this.a, 0, this.size);
/*     */     } else {
/* 576 */       ByteArrays.unstableSort(this.a, 0, this.size, comp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ByteArrayList clone() {
/* 581 */     ByteArrayList c = new ByteArrayList(this.size);
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
/*     */   public boolean equals(ByteArrayList l) {
/* 599 */     if (l == this)
/* 600 */       return true; 
/* 601 */     int s = size();
/* 602 */     if (s != l.size())
/* 603 */       return false; 
/* 604 */     byte[] a1 = this.a;
/* 605 */     byte[] a2 = l.a;
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
/*     */   public int compareTo(ByteArrayList l) {
/* 625 */     int s1 = size(), s2 = l.size();
/* 626 */     byte[] a1 = this.a, a2 = l.a;
/*     */     
/*     */     int i;
/* 629 */     for (i = 0; i < s1 && i < s2; i++) {
/* 630 */       byte e1 = a1[i];
/* 631 */       byte e2 = a2[i]; int r;
/* 632 */       if ((r = Byte.compare(e1, e2)) != 0)
/* 633 */         return r; 
/*     */     } 
/* 635 */     return (i < s2) ? -1 : ((i < s1) ? 1 : 0);
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 638 */     s.defaultWriteObject();
/* 639 */     for (int i = 0; i < this.size; i++)
/* 640 */       s.writeByte(this.a[i]); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 644 */     s.defaultReadObject();
/* 645 */     this.a = new byte[this.size];
/* 646 */     for (int i = 0; i < this.size; i++)
/* 647 */       this.a[i] = s.readByte(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteArrayList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */