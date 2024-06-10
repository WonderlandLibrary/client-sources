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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectOpenCustomHashSet<K>
/*     */   extends AbstractObjectSet<K>
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient K[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected Hash.Strategy<? super K> strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ObjectOpenCustomHashSet(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  92 */     this.strategy = strategy;
/*  93 */     if (f <= 0.0F || f > 1.0F)
/*  94 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  95 */     if (expected < 0)
/*  96 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  97 */     this.f = f;
/*  98 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  99 */     this.mask = this.n - 1;
/* 100 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 101 */     this.key = (K[])new Object[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(int expected, Hash.Strategy<? super K> strategy) {
/* 112 */     this(expected, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenCustomHashSet(Hash.Strategy<? super K> strategy) {
/* 123 */     this(16, 0.75F, strategy);
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
/*     */   public ObjectOpenCustomHashSet(Collection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/* 136 */     this(c.size(), f, strategy);
/* 137 */     addAll(c);
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
/*     */   public ObjectOpenCustomHashSet(Collection<? extends K> c, Hash.Strategy<? super K> strategy) {
/* 149 */     this(c, 0.75F, strategy);
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
/*     */   public ObjectOpenCustomHashSet(ObjectCollection<? extends K> c, float f, Hash.Strategy<? super K> strategy) {
/* 162 */     this(c.size(), f, strategy);
/* 163 */     addAll(c);
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
/*     */   public ObjectOpenCustomHashSet(ObjectCollection<? extends K> c, Hash.Strategy<? super K> strategy) {
/* 175 */     this(c, 0.75F, strategy);
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
/*     */   public ObjectOpenCustomHashSet(Iterator<? extends K> i, float f, Hash.Strategy<? super K> strategy) {
/* 188 */     this(16, f, strategy);
/* 189 */     while (i.hasNext()) {
/* 190 */       add(i.next());
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
/*     */   public ObjectOpenCustomHashSet(Iterator<? extends K> i, Hash.Strategy<? super K> strategy) {
/* 202 */     this(i, 0.75F, strategy);
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
/*     */   public ObjectOpenCustomHashSet(K[] a, int offset, int length, float f, Hash.Strategy<? super K> strategy) {
/* 220 */     this((length < 0) ? 0 : length, f, strategy);
/* 221 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 222 */     for (int i = 0; i < length; i++) {
/* 223 */       add(a[offset + i]);
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
/*     */   public ObjectOpenCustomHashSet(K[] a, int offset, int length, Hash.Strategy<? super K> strategy) {
/* 240 */     this(a, offset, length, 0.75F, strategy);
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
/*     */   public ObjectOpenCustomHashSet(K[] a, float f, Hash.Strategy<? super K> strategy) {
/* 253 */     this(a, 0, a.length, f, strategy);
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
/*     */   public ObjectOpenCustomHashSet(K[] a, Hash.Strategy<? super K> strategy) {
/* 265 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hash.Strategy<? super K> strategy() {
/* 273 */     return this.strategy;
/*     */   }
/*     */   private int realSize() {
/* 276 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */   private void ensureCapacity(int capacity) {
/* 279 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 280 */     if (needed > this.n)
/* 281 */       rehash(needed); 
/*     */   }
/*     */   private void tryCapacity(long capacity) {
/* 284 */     int needed = (int)Math.min(1073741824L, 
/* 285 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 286 */     if (needed > this.n) {
/* 287 */       rehash(needed);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 292 */     if (this.f <= 0.5D) {
/* 293 */       ensureCapacity(c.size());
/*     */     } else {
/* 295 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 297 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/* 302 */     if (this.strategy.equals(k, null)) {
/* 303 */       if (this.containsNull)
/* 304 */         return false; 
/* 305 */       this.containsNull = true;
/* 306 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 309 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 311 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/* 312 */         if (this.strategy.equals(curr, k))
/* 313 */           return false; 
/* 314 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/* 315 */           if (this.strategy.equals(curr, k))
/* 316 */             return false; 
/*     */         } 
/* 318 */       }  key[pos] = k;
/*     */     } 
/* 320 */     if (this.size++ >= this.maxFill) {
/* 321 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 324 */     return true;
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
/* 342 */     if (this.strategy.equals(k, null)) {
/* 343 */       if (this.containsNull)
/* 344 */         return this.key[this.n]; 
/* 345 */       this.containsNull = true;
/* 346 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 349 */       K[] key = this.key; int pos;
/*     */       K curr;
/* 351 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/* 352 */         if (this.strategy.equals(curr, k))
/* 353 */           return curr; 
/* 354 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/* 355 */           if (this.strategy.equals(curr, k))
/* 356 */             return curr; 
/*     */         } 
/* 358 */       }  key[pos] = k;
/*     */     } 
/* 360 */     if (this.size++ >= this.maxFill) {
/* 361 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 364 */     return k;
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
/* 377 */     K[] key = this.key; while (true) {
/*     */       K curr; int last;
/* 379 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 381 */         if ((curr = key[pos]) == null) {
/* 382 */           key[last] = null;
/*     */           return;
/*     */         } 
/* 385 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/* 386 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 388 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 390 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int pos) {
/* 394 */     this.size--;
/* 395 */     shiftKeys(pos);
/* 396 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 397 */       rehash(this.n / 2); 
/* 398 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 401 */     this.containsNull = false;
/* 402 */     this.key[this.n] = null;
/* 403 */     this.size--;
/* 404 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 405 */       rehash(this.n / 2); 
/* 406 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object k) {
/* 411 */     if (this.strategy.equals(k, null)) {
/* 412 */       if (this.containsNull)
/* 413 */         return removeNullEntry(); 
/* 414 */       return false;
/*     */     } 
/*     */     
/* 417 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 420 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/* 421 */       return false; 
/* 422 */     if (this.strategy.equals(k, curr))
/* 423 */       return removeEntry(pos); 
/*     */     while (true) {
/* 425 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 426 */         return false; 
/* 427 */       if (this.strategy.equals(k, curr)) {
/* 428 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(Object k) {
/* 434 */     if (this.strategy.equals(k, null)) {
/* 435 */       return this.containsNull;
/*     */     }
/* 437 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 440 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/* 441 */       return false; 
/* 442 */     if (this.strategy.equals(k, curr))
/* 443 */       return true; 
/*     */     while (true) {
/* 445 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 446 */         return false; 
/* 447 */       if (this.strategy.equals(k, curr)) {
/* 448 */         return true;
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
/* 460 */     if (this.strategy.equals(k, null)) {
/* 461 */       return this.key[this.n];
/*     */     }
/*     */     
/* 464 */     K[] key = this.key;
/*     */     K curr;
/*     */     int pos;
/* 467 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/* 468 */       return null; 
/* 469 */     if (this.strategy.equals(k, curr)) {
/* 470 */       return curr;
/*     */     }
/*     */     while (true) {
/* 473 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/* 474 */         return null; 
/* 475 */       if (this.strategy.equals(k, curr)) {
/* 476 */         return curr;
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
/* 488 */     if (this.size == 0)
/*     */       return; 
/* 490 */     this.size = 0;
/* 491 */     this.containsNull = false;
/* 492 */     Arrays.fill((Object[])this.key, (Object)null);
/*     */   }
/*     */   
/*     */   public int size() {
/* 496 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 500 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 509 */     int pos = ObjectOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 516 */     int last = -1;
/*     */     
/* 518 */     int c = ObjectOpenCustomHashSet.this.size;
/*     */     
/* 520 */     boolean mustReturnNull = ObjectOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 528 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public K next() {
/* 532 */       if (!hasNext())
/* 533 */         throw new NoSuchElementException(); 
/* 534 */       this.c--;
/* 535 */       if (this.mustReturnNull) {
/* 536 */         this.mustReturnNull = false;
/* 537 */         this.last = ObjectOpenCustomHashSet.this.n;
/* 538 */         return ObjectOpenCustomHashSet.this.key[ObjectOpenCustomHashSet.this.n];
/*     */       } 
/* 540 */       K[] key = ObjectOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 542 */         if (--this.pos < 0) {
/*     */           
/* 544 */           this.last = Integer.MIN_VALUE;
/* 545 */           return this.wrapped.get(-this.pos - 1);
/*     */         } 
/* 547 */         if (key[this.pos] != null) {
/* 548 */           return key[this.last = this.pos];
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
/* 562 */       K[] key = ObjectOpenCustomHashSet.this.key; while (true) {
/*     */         K curr; int last;
/* 564 */         pos = (last = pos) + 1 & ObjectOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 566 */           if ((curr = key[pos]) == null) {
/* 567 */             key[last] = null;
/*     */             return;
/*     */           } 
/* 570 */           int slot = HashCommon.mix(ObjectOpenCustomHashSet.this.strategy.hashCode(curr)) & ObjectOpenCustomHashSet.this.mask;
/* 571 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 573 */           pos = pos + 1 & ObjectOpenCustomHashSet.this.mask;
/*     */         } 
/* 575 */         if (pos < last) {
/* 576 */           if (this.wrapped == null)
/* 577 */             this.wrapped = new ObjectArrayList<>(2); 
/* 578 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 580 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 585 */       if (this.last == -1)
/* 586 */         throw new IllegalStateException(); 
/* 587 */       if (this.last == ObjectOpenCustomHashSet.this.n) {
/* 588 */         ObjectOpenCustomHashSet.this.containsNull = false;
/* 589 */         ObjectOpenCustomHashSet.this.key[ObjectOpenCustomHashSet.this.n] = null;
/* 590 */       } else if (this.pos >= 0) {
/* 591 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 594 */         ObjectOpenCustomHashSet.this.remove(this.wrapped.set(-this.pos - 1, null));
/* 595 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 598 */       ObjectOpenCustomHashSet.this.size--;
/* 599 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 606 */     return new SetIterator();
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
/* 623 */     return trim(this.size);
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
/* 647 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 648 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 649 */       return true; 
/*     */     try {
/* 651 */       rehash(l);
/* 652 */     } catch (OutOfMemoryError cantDoIt) {
/* 653 */       return false;
/*     */     } 
/* 655 */     return true;
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
/* 671 */     K[] key = this.key;
/* 672 */     int mask = newN - 1;
/* 673 */     K[] newKey = (K[])new Object[newN + 1];
/* 674 */     int i = this.n;
/* 675 */     for (int j = realSize(); j-- != 0; ) {
/* 676 */       while (key[--i] == null); int pos;
/* 677 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != null)
/* 678 */         while (newKey[pos = pos + 1 & mask] != null); 
/* 679 */       newKey[pos] = key[i];
/*     */     } 
/* 681 */     this.n = newN;
/* 682 */     this.mask = mask;
/* 683 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 684 */     this.key = newKey;
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
/*     */   public ObjectOpenCustomHashSet<K> clone() {
/*     */     ObjectOpenCustomHashSet<K> c;
/*     */     try {
/* 701 */       c = (ObjectOpenCustomHashSet<K>)super.clone();
/* 702 */     } catch (CloneNotSupportedException cantHappen) {
/* 703 */       throw new InternalError();
/*     */     } 
/* 705 */     c.key = (K[])this.key.clone();
/* 706 */     c.containsNull = this.containsNull;
/* 707 */     c.strategy = this.strategy;
/* 708 */     return c;
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
/* 721 */     int h = 0;
/* 722 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 723 */       while (this.key[i] == null)
/* 724 */         i++; 
/* 725 */       if (this != this.key[i])
/* 726 */         h += this.strategy.hashCode(this.key[i]); 
/* 727 */       i++;
/*     */     } 
/*     */     
/* 730 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 733 */     ObjectIterator<K> i = iterator();
/* 734 */     s.defaultWriteObject();
/* 735 */     for (int j = this.size; j-- != 0;)
/* 736 */       s.writeObject(i.next()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 740 */     s.defaultReadObject();
/* 741 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 742 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 743 */     this.mask = this.n - 1;
/* 744 */     K[] key = this.key = (K[])new Object[this.n + 1];
/*     */     
/* 746 */     for (int i = this.size; i-- != 0; ) {
/* 747 */       int pos; K k = (K)s.readObject();
/* 748 */       if (this.strategy.equals(k, null)) {
/* 749 */         pos = this.n;
/* 750 */         this.containsNull = true;
/*     */       }
/* 752 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != null) {
/* 753 */         while (key[pos = pos + 1 & this.mask] != null);
/*     */       } 
/* 755 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */