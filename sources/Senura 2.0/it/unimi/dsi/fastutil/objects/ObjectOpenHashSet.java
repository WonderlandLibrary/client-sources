/*     */ package it.unimi.dsi.fastutil.objects;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectOpenHashSet<K>
/*     */   extends AbstractObjectSet<K>
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient K[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ObjectOpenHashSet(int expected, float f) {
/*  86 */     if (f <= 0.0F || f > 1.0F)
/*  87 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  88 */     if (expected < 0)
/*  89 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  90 */     this.f = f;
/*  91 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  92 */     this.mask = this.n - 1;
/*  93 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  94 */     this.key = (K[])new Object[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(int expected) {
/* 103 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet() {
/* 111 */     this(16, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(Collection<? extends K> c, float f) {
/* 122 */     this(c.size(), f);
/* 123 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(Collection<? extends K> c) {
/* 133 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(ObjectCollection<? extends K> c, float f) {
/* 144 */     this(c.size(), f);
/* 145 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(ObjectCollection<? extends K> c) {
/* 155 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(Iterator<? extends K> i, float f) {
/* 166 */     this(16, f);
/* 167 */     while (i.hasNext()) {
/* 168 */       add(i.next());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(Iterator<? extends K> i) {
/* 178 */     this(i, 0.75F);
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
/*     */   public ObjectOpenHashSet(K[] a, int offset, int length, float f) {
/* 193 */     this((length < 0) ? 0 : length, f);
/* 194 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 195 */     for (int i = 0; i < length; i++) {
/* 196 */       add(a[offset + i]);
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
/*     */   public ObjectOpenHashSet(K[] a, int offset, int length) {
/* 210 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(K[] a, float f) {
/* 221 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashSet(K[] a) {
/* 231 */     this(a, 0.75F);
/*     */   }
/*     */   private int realSize() {
/* 234 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */   private void ensureCapacity(int capacity) {
/* 237 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 238 */     if (needed > this.n)
/* 239 */       rehash(needed); 
/*     */   }
/*     */   private void tryCapacity(long capacity) {
/* 242 */     int needed = (int)Math.min(1073741824L, 
/* 243 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 244 */     if (needed > this.n) {
/* 245 */       rehash(needed);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 250 */     if (this.f <= 0.5D) {
/* 251 */       ensureCapacity(c.size());
/*     */     } else {
/* 253 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 255 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/* 260 */     if (k == null) {
/* 261 */       if (this.containsNull)
/* 262 */         return false; 
/* 263 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 266 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 268 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/* 269 */         if (curr.equals(k))
/* 270 */           return false; 
/* 271 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/* 272 */           if (curr.equals(k))
/* 273 */             return false; 
/*     */         } 
/* 275 */       }  key[pos] = k;
/*     */     } 
/* 277 */     if (this.size++ >= this.maxFill) {
/* 278 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 281 */     return true;
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
/*     */   public K addOrGet(K k) {
/* 299 */     if (k == null) {
/* 300 */       if (this.containsNull)
/* 301 */         return this.key[this.n]; 
/* 302 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 305 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 307 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/* 308 */         if (curr.equals(k))
/* 309 */           return curr; 
/* 310 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/* 311 */           if (curr.equals(k))
/* 312 */             return curr; 
/*     */         } 
/* 314 */       }  key[pos] = k;
/*     */     } 
/* 316 */     if (this.size++ >= this.maxFill) {
/* 317 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 320 */     return k;
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
/*     */   protected final void shiftKeys(int pos) {
/* 333 */     K[] key = this.key; while (true) {
/*     */       K curr; int last;
/* 335 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 337 */         if ((curr = key[pos]) == null) {
/* 338 */           key[last] = null;
/*     */           return;
/*     */         } 
/* 341 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/* 342 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 344 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 346 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int pos) {
/* 350 */     this.size--;
/* 351 */     shiftKeys(pos);
/* 352 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 353 */       rehash(this.n / 2); 
/* 354 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 357 */     this.containsNull = false;
/* 358 */     this.key[this.n] = null;
/* 359 */     this.size--;
/* 360 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 361 */       rehash(this.n / 2); 
/* 362 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 367 */     if (k == null) {
/* 368 */       if (this.containsNull)
/* 369 */         return removeNullEntry(); 
/* 370 */       return false;
/*     */     } 
/*     */     
/* 373 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 376 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/* 377 */       return false; 
/* 378 */     if (k.equals(curr))
/* 379 */       return removeEntry(pos); 
/*     */     while (true) {
/* 381 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 382 */         return false; 
/* 383 */       if (k.equals(curr)) {
/* 384 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(Object k) {
/* 390 */     if (k == null) {
/* 391 */       return this.containsNull;
/*     */     }
/* 393 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 396 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/* 397 */       return false; 
/* 398 */     if (k.equals(curr))
/* 399 */       return true; 
/*     */     while (true) {
/* 401 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 402 */         return false; 
/* 403 */       if (k.equals(curr)) {
/* 404 */         return true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K get(Object k) {
/* 416 */     if (k == null) {
/* 417 */       return this.key[this.n];
/*     */     }
/*     */     
/* 420 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 423 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/* 424 */       return null; 
/* 425 */     if (k.equals(curr)) {
/* 426 */       return curr;
/*     */     }
/*     */     while (true) {
/* 429 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 430 */         return null; 
/* 431 */       if (k.equals(curr)) {
/* 432 */         return curr;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 444 */     if (this.size == 0)
/*     */       return; 
/* 446 */     this.size = 0;
/* 447 */     this.containsNull = false;
/* 448 */     Arrays.fill((Object[])this.key, (Object)null);
/*     */   }
/*     */   
/*     */   public int size() {
/* 452 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 456 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 465 */     int pos = ObjectOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 472 */     int last = -1;
/*     */     
/* 474 */     int c = ObjectOpenHashSet.this.size;
/*     */     
/* 476 */     boolean mustReturnNull = ObjectOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 484 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public K next() {
/* 488 */       if (!hasNext())
/* 489 */         throw new NoSuchElementException(); 
/* 490 */       this.c--;
/* 491 */       if (this.mustReturnNull) {
/* 492 */         this.mustReturnNull = false;
/* 493 */         this.last = ObjectOpenHashSet.this.n;
/* 494 */         return ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n];
/*     */       } 
/* 496 */       K[] key = ObjectOpenHashSet.this.key;
/*     */       while (true) {
/* 498 */         if (--this.pos < 0) {
/*     */           
/* 500 */           this.last = Integer.MIN_VALUE;
/* 501 */           return this.wrapped.get(-this.pos - 1);
/*     */         } 
/* 503 */         if (key[this.pos] != null) {
/* 504 */           return key[this.last = this.pos];
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final void shiftKeys(int pos) {
/* 518 */       K[] key = ObjectOpenHashSet.this.key; while (true) {
/*     */         K curr; int last;
/* 520 */         pos = (last = pos) + 1 & ObjectOpenHashSet.this.mask;
/*     */         while (true) {
/* 522 */           if ((curr = key[pos]) == null) {
/* 523 */             key[last] = null;
/*     */             return;
/*     */           } 
/* 526 */           int slot = HashCommon.mix(curr.hashCode()) & ObjectOpenHashSet.this.mask;
/* 527 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 529 */           pos = pos + 1 & ObjectOpenHashSet.this.mask;
/*     */         } 
/* 531 */         if (pos < last) {
/* 532 */           if (this.wrapped == null)
/* 533 */             this.wrapped = new ObjectArrayList<>(2); 
/* 534 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 536 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 541 */       if (this.last == -1)
/* 542 */         throw new IllegalStateException(); 
/* 543 */       if (this.last == ObjectOpenHashSet.this.n) {
/* 544 */         ObjectOpenHashSet.this.containsNull = false;
/* 545 */         ObjectOpenHashSet.this.key[ObjectOpenHashSet.this.n] = null;
/* 546 */       } else if (this.pos >= 0) {
/* 547 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 550 */         ObjectOpenHashSet.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 551 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 554 */       ObjectOpenHashSet.this.size--;
/* 555 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 562 */     return new SetIterator();
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
/*     */   public boolean trim() {
/* 579 */     return trim(this.size);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean trim(int n) {
/* 603 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 604 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 605 */       return true; 
/*     */     try {
/* 607 */       rehash(l);
/* 608 */     } catch (OutOfMemoryError cantDoIt) {
/* 609 */       return false;
/*     */     } 
/* 611 */     return true;
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
/*     */   protected void rehash(int newN) {
/* 627 */     K[] key = this.key;
/* 628 */     int mask = newN - 1;
/* 629 */     K[] newKey = (K[])new Object[newN + 1];
/* 630 */     int i = this.n;
/* 631 */     for (int j = realSize(); j-- != 0; ) {
/* 632 */       while (key[--i] == null); int pos;
/* 633 */       if (newKey[pos = HashCommon.mix(key[i].hashCode()) & mask] != null)
/* 634 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 635 */       newKey[pos] = key[i];
/*     */     } 
/* 637 */     this.n = newN;
/* 638 */     this.mask = mask;
/* 639 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 640 */     this.key = newKey;
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
/*     */   public ObjectOpenHashSet<K> clone() {
/*     */     ObjectOpenHashSet<K> c;
/*     */     try {
/* 657 */       c = (ObjectOpenHashSet<K>)super.clone();
/* 658 */     } catch (CloneNotSupportedException cantHappen) {
/* 659 */       throw new InternalError();
/*     */     } 
/* 661 */     c.key = (K[])this.key.clone();
/* 662 */     c.containsNull = this.containsNull;
/* 663 */     return c;
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
/*     */   public int hashCode() {
/* 676 */     int h = 0;
/* 677 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 678 */       while (this.key[i] == null)
/* 679 */         i++; 
/* 680 */       if (this != this.key[i])
/* 681 */         h += this.key[i].hashCode(); 
/* 682 */       i++;
/*     */     } 
/*     */     
/* 685 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 688 */     ObjectIterator<K> i = iterator();
/* 689 */     s.defaultWriteObject();
/* 690 */     for (int j = this.size; j-- != 0;)
/* 691 */       s.writeObject(i.next()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 695 */     s.defaultReadObject();
/* 696 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 697 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 698 */     this.mask = this.n - 1;
/* 699 */     K[] key = this.key = (K[])new Object[this.n + 1];
/*     */     
/* 701 */     for (int i = this.size; i-- != 0; ) {
/* 702 */       int pos; K k = (K)s.readObject();
/* 703 */       if (k == null) {
/* 704 */         pos = this.n;
/* 705 */         this.containsNull = true;
/*     */       }
/* 707 */       else if (key[pos = HashCommon.mix(k.hashCode()) & this.mask] != null) {
/* 708 */         while (key[pos = pos + 1 & this.mask] != null);
/*     */       } 
/* 710 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */