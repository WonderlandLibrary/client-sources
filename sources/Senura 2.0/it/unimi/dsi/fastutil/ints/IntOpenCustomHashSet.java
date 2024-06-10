/*     */ package it.unimi.dsi.fastutil.ints;
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
/*     */ 
/*     */ public class IntOpenCustomHashSet
/*     */   extends AbstractIntSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient int[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected IntHash.Strategy strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public IntOpenCustomHashSet(int expected, float f, IntHash.Strategy strategy) {
/*  93 */     this.strategy = strategy;
/*  94 */     if (f <= 0.0F || f > 1.0F)
/*  95 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  96 */     if (expected < 0)
/*  97 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  98 */     this.f = f;
/*  99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/* 100 */     this.mask = this.n - 1;
/* 101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 102 */     this.key = new int[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenCustomHashSet(int expected, IntHash.Strategy strategy) {
/* 113 */     this(expected, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntOpenCustomHashSet(IntHash.Strategy strategy) {
/* 124 */     this(16, 0.75F, strategy);
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
/*     */   public IntOpenCustomHashSet(Collection<? extends Integer> c, float f, IntHash.Strategy strategy) {
/* 138 */     this(c.size(), f, strategy);
/* 139 */     addAll(c);
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
/*     */   public IntOpenCustomHashSet(Collection<? extends Integer> c, IntHash.Strategy strategy) {
/* 152 */     this(c, 0.75F, strategy);
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
/*     */   public IntOpenCustomHashSet(IntCollection c, float f, IntHash.Strategy strategy) {
/* 166 */     this(c.size(), f, strategy);
/* 167 */     addAll(c);
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
/*     */   public IntOpenCustomHashSet(IntCollection c, IntHash.Strategy strategy) {
/* 179 */     this(c, 0.75F, strategy);
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
/*     */   public IntOpenCustomHashSet(IntIterator i, float f, IntHash.Strategy strategy) {
/* 193 */     this(16, f, strategy);
/* 194 */     while (i.hasNext()) {
/* 195 */       add(i.nextInt());
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
/*     */   public IntOpenCustomHashSet(IntIterator i, IntHash.Strategy strategy) {
/* 207 */     this(i, 0.75F, strategy);
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
/*     */   public IntOpenCustomHashSet(Iterator<?> i, float f, IntHash.Strategy strategy) {
/* 221 */     this(IntIterators.asIntIterator(i), f, strategy);
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
/*     */   public IntOpenCustomHashSet(Iterator<?> i, IntHash.Strategy strategy) {
/* 233 */     this(IntIterators.asIntIterator(i), strategy);
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
/*     */   public IntOpenCustomHashSet(int[] a, int offset, int length, float f, IntHash.Strategy strategy) {
/* 251 */     this((length < 0) ? 0 : length, f, strategy);
/* 252 */     IntArrays.ensureOffsetLength(a, offset, length);
/* 253 */     for (int i = 0; i < length; i++) {
/* 254 */       add(a[offset + i]);
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
/*     */   public IntOpenCustomHashSet(int[] a, int offset, int length, IntHash.Strategy strategy) {
/* 271 */     this(a, offset, length, 0.75F, strategy);
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
/*     */   public IntOpenCustomHashSet(int[] a, float f, IntHash.Strategy strategy) {
/* 285 */     this(a, 0, a.length, f, strategy);
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
/*     */   public IntOpenCustomHashSet(int[] a, IntHash.Strategy strategy) {
/* 297 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntHash.Strategy strategy() {
/* 305 */     return this.strategy;
/*     */   }
/*     */   private int realSize() {
/* 308 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */   private void ensureCapacity(int capacity) {
/* 311 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 312 */     if (needed > this.n)
/* 313 */       rehash(needed); 
/*     */   }
/*     */   private void tryCapacity(long capacity) {
/* 316 */     int needed = (int)Math.min(1073741824L, 
/* 317 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 318 */     if (needed > this.n)
/* 319 */       rehash(needed); 
/*     */   }
/*     */   
/*     */   public boolean addAll(IntCollection c) {
/* 323 */     if (this.f <= 0.5D) {
/* 324 */       ensureCapacity(c.size());
/*     */     } else {
/* 326 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 328 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Integer> c) {
/* 333 */     if (this.f <= 0.5D) {
/* 334 */       ensureCapacity(c.size());
/*     */     } else {
/* 336 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 338 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(int k) {
/* 343 */     if (this.strategy.equals(k, 0)) {
/* 344 */       if (this.containsNull)
/* 345 */         return false; 
/* 346 */       this.containsNull = true;
/* 347 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 350 */       int[] key = this.key;
/*     */       int pos, curr;
/* 352 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/* 353 */         if (this.strategy.equals(curr, k))
/* 354 */           return false; 
/* 355 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/* 356 */           if (this.strategy.equals(curr, k))
/* 357 */             return false; 
/*     */         } 
/* 359 */       }  key[pos] = k;
/*     */     } 
/* 361 */     if (this.size++ >= this.maxFill) {
/* 362 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 365 */     return true;
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
/* 378 */     int[] key = this.key; while (true) {
/*     */       int curr, last;
/* 380 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 382 */         if ((curr = key[pos]) == 0) {
/* 383 */           key[last] = 0;
/*     */           return;
/*     */         } 
/* 386 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/* 387 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 389 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 391 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int pos) {
/* 395 */     this.size--;
/* 396 */     shiftKeys(pos);
/* 397 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 398 */       rehash(this.n / 2); 
/* 399 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 402 */     this.containsNull = false;
/* 403 */     this.key[this.n] = 0;
/* 404 */     this.size--;
/* 405 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 406 */       rehash(this.n / 2); 
/* 407 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(int k) {
/* 412 */     if (this.strategy.equals(k, 0)) {
/* 413 */       if (this.containsNull)
/* 414 */         return removeNullEntry(); 
/* 415 */       return false;
/*     */     } 
/*     */     
/* 418 */     int[] key = this.key;
/*     */     
/*     */     int curr, pos;
/* 421 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 422 */       return false; 
/* 423 */     if (this.strategy.equals(k, curr))
/* 424 */       return removeEntry(pos); 
/*     */     while (true) {
/* 426 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 427 */         return false; 
/* 428 */       if (this.strategy.equals(k, curr)) {
/* 429 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(int k) {
/* 435 */     if (this.strategy.equals(k, 0)) {
/* 436 */       return this.containsNull;
/*     */     }
/* 438 */     int[] key = this.key;
/*     */     
/*     */     int curr, pos;
/* 441 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 442 */       return false; 
/* 443 */     if (this.strategy.equals(k, curr))
/* 444 */       return true; 
/*     */     while (true) {
/* 446 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 447 */         return false; 
/* 448 */       if (this.strategy.equals(k, curr)) {
/* 449 */         return true;
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
/* 461 */     if (this.size == 0)
/*     */       return; 
/* 463 */     this.size = 0;
/* 464 */     this.containsNull = false;
/* 465 */     Arrays.fill(this.key, 0);
/*     */   }
/*     */   
/*     */   public int size() {
/* 469 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 473 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements IntIterator
/*     */   {
/* 482 */     int pos = IntOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 489 */     int last = -1;
/*     */     
/* 491 */     int c = IntOpenCustomHashSet.this.size;
/*     */     
/* 493 */     boolean mustReturnNull = IntOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     IntArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 501 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public int nextInt() {
/* 505 */       if (!hasNext())
/* 506 */         throw new NoSuchElementException(); 
/* 507 */       this.c--;
/* 508 */       if (this.mustReturnNull) {
/* 509 */         this.mustReturnNull = false;
/* 510 */         this.last = IntOpenCustomHashSet.this.n;
/* 511 */         return IntOpenCustomHashSet.this.key[IntOpenCustomHashSet.this.n];
/*     */       } 
/* 513 */       int[] key = IntOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 515 */         if (--this.pos < 0) {
/*     */           
/* 517 */           this.last = Integer.MIN_VALUE;
/* 518 */           return this.wrapped.getInt(-this.pos - 1);
/*     */         } 
/* 520 */         if (key[this.pos] != 0) {
/* 521 */           return key[this.last = this.pos];
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
/* 535 */       int[] key = IntOpenCustomHashSet.this.key; while (true) {
/*     */         int curr, last;
/* 537 */         pos = (last = pos) + 1 & IntOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 539 */           if ((curr = key[pos]) == 0) {
/* 540 */             key[last] = 0;
/*     */             return;
/*     */           } 
/* 543 */           int slot = HashCommon.mix(IntOpenCustomHashSet.this.strategy.hashCode(curr)) & IntOpenCustomHashSet.this.mask;
/* 544 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 546 */           pos = pos + 1 & IntOpenCustomHashSet.this.mask;
/*     */         } 
/* 548 */         if (pos < last) {
/* 549 */           if (this.wrapped == null)
/* 550 */             this.wrapped = new IntArrayList(2); 
/* 551 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 553 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 558 */       if (this.last == -1)
/* 559 */         throw new IllegalStateException(); 
/* 560 */       if (this.last == IntOpenCustomHashSet.this.n) {
/* 561 */         IntOpenCustomHashSet.this.containsNull = false;
/* 562 */         IntOpenCustomHashSet.this.key[IntOpenCustomHashSet.this.n] = 0;
/* 563 */       } else if (this.pos >= 0) {
/* 564 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 567 */         IntOpenCustomHashSet.this.remove(this.wrapped.getInt(-this.pos - 1));
/* 568 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 571 */       IntOpenCustomHashSet.this.size--;
/* 572 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public IntIterator iterator() {
/* 579 */     return new SetIterator();
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
/* 596 */     return trim(this.size);
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
/* 620 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 621 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 622 */       return true; 
/*     */     try {
/* 624 */       rehash(l);
/* 625 */     } catch (OutOfMemoryError cantDoIt) {
/* 626 */       return false;
/*     */     } 
/* 628 */     return true;
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
/* 644 */     int[] key = this.key;
/* 645 */     int mask = newN - 1;
/* 646 */     int[] newKey = new int[newN + 1];
/* 647 */     int i = this.n;
/* 648 */     for (int j = realSize(); j-- != 0; ) {
/* 649 */       while (key[--i] == 0); int pos;
/* 650 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/* 651 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 652 */       newKey[pos] = key[i];
/*     */     } 
/* 654 */     this.n = newN;
/* 655 */     this.mask = mask;
/* 656 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 657 */     this.key = newKey;
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
/*     */   public IntOpenCustomHashSet clone() {
/*     */     IntOpenCustomHashSet c;
/*     */     try {
/* 674 */       c = (IntOpenCustomHashSet)super.clone();
/* 675 */     } catch (CloneNotSupportedException cantHappen) {
/* 676 */       throw new InternalError();
/*     */     } 
/* 678 */     c.key = (int[])this.key.clone();
/* 679 */     c.containsNull = this.containsNull;
/* 680 */     c.strategy = this.strategy;
/* 681 */     return c;
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
/* 694 */     int h = 0;
/* 695 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 696 */       while (this.key[i] == 0)
/* 697 */         i++; 
/* 698 */       h += this.strategy.hashCode(this.key[i]);
/* 699 */       i++;
/*     */     } 
/*     */     
/* 702 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 705 */     IntIterator i = iterator();
/* 706 */     s.defaultWriteObject();
/* 707 */     for (int j = this.size; j-- != 0;)
/* 708 */       s.writeInt(i.nextInt()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 712 */     s.defaultReadObject();
/* 713 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 714 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 715 */     this.mask = this.n - 1;
/* 716 */     int[] key = this.key = new int[this.n + 1];
/*     */     
/* 718 */     for (int i = this.size; i-- != 0; ) {
/* 719 */       int pos, k = s.readInt();
/* 720 */       if (this.strategy.equals(k, 0)) {
/* 721 */         pos = this.n;
/* 722 */         this.containsNull = true;
/*     */       }
/* 724 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) {
/* 725 */         while (key[pos = pos + 1 & this.mask] != 0);
/*     */       } 
/* 727 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */