/*     */ package it.unimi.dsi.fastutil.shorts;
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
/*     */ public class ShortOpenCustomHashSet
/*     */   extends AbstractShortSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient short[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected ShortHash.Strategy strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ShortOpenCustomHashSet(int expected, float f, ShortHash.Strategy strategy) {
/*  93 */     this.strategy = strategy;
/*  94 */     if (f <= 0.0F || f > 1.0F)
/*  95 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  96 */     if (expected < 0)
/*  97 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  98 */     this.f = f;
/*  99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/* 100 */     this.mask = this.n - 1;
/* 101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 102 */     this.key = new short[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenCustomHashSet(int expected, ShortHash.Strategy strategy) {
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
/*     */   public ShortOpenCustomHashSet(ShortHash.Strategy strategy) {
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
/*     */   public ShortOpenCustomHashSet(Collection<? extends Short> c, float f, ShortHash.Strategy strategy) {
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
/*     */   public ShortOpenCustomHashSet(Collection<? extends Short> c, ShortHash.Strategy strategy) {
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
/*     */   public ShortOpenCustomHashSet(ShortCollection c, float f, ShortHash.Strategy strategy) {
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
/*     */   
/*     */   public ShortOpenCustomHashSet(ShortCollection c, ShortHash.Strategy strategy) {
/* 180 */     this(c, 0.75F, strategy);
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
/*     */   public ShortOpenCustomHashSet(ShortIterator i, float f, ShortHash.Strategy strategy) {
/* 194 */     this(16, f, strategy);
/* 195 */     while (i.hasNext()) {
/* 196 */       add(i.nextShort());
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
/*     */   public ShortOpenCustomHashSet(ShortIterator i, ShortHash.Strategy strategy) {
/* 209 */     this(i, 0.75F, strategy);
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
/*     */   public ShortOpenCustomHashSet(Iterator<?> i, float f, ShortHash.Strategy strategy) {
/* 223 */     this(ShortIterators.asShortIterator(i), f, strategy);
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
/*     */   public ShortOpenCustomHashSet(Iterator<?> i, ShortHash.Strategy strategy) {
/* 235 */     this(ShortIterators.asShortIterator(i), strategy);
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
/*     */   public ShortOpenCustomHashSet(short[] a, int offset, int length, float f, ShortHash.Strategy strategy) {
/* 253 */     this((length < 0) ? 0 : length, f, strategy);
/* 254 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 255 */     for (int i = 0; i < length; i++) {
/* 256 */       add(a[offset + i]);
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
/*     */   public ShortOpenCustomHashSet(short[] a, int offset, int length, ShortHash.Strategy strategy) {
/* 273 */     this(a, offset, length, 0.75F, strategy);
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
/*     */   public ShortOpenCustomHashSet(short[] a, float f, ShortHash.Strategy strategy) {
/* 287 */     this(a, 0, a.length, f, strategy);
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
/*     */   public ShortOpenCustomHashSet(short[] a, ShortHash.Strategy strategy) {
/* 299 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortHash.Strategy strategy() {
/* 307 */     return this.strategy;
/*     */   }
/*     */   private int realSize() {
/* 310 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */   private void ensureCapacity(int capacity) {
/* 313 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 314 */     if (needed > this.n)
/* 315 */       rehash(needed); 
/*     */   }
/*     */   private void tryCapacity(long capacity) {
/* 318 */     int needed = (int)Math.min(1073741824L, 
/* 319 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 320 */     if (needed > this.n)
/* 321 */       rehash(needed); 
/*     */   }
/*     */   
/*     */   public boolean addAll(ShortCollection c) {
/* 325 */     if (this.f <= 0.5D) {
/* 326 */       ensureCapacity(c.size());
/*     */     } else {
/* 328 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 330 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Short> c) {
/* 335 */     if (this.f <= 0.5D) {
/* 336 */       ensureCapacity(c.size());
/*     */     } else {
/* 338 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 340 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(short k) {
/* 345 */     if (this.strategy.equals(k, (short)0)) {
/* 346 */       if (this.containsNull)
/* 347 */         return false; 
/* 348 */       this.containsNull = true;
/* 349 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 352 */       short[] key = this.key; int pos;
/*     */       short curr;
/* 354 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*     */         
/* 356 */         if (this.strategy.equals(curr, k))
/* 357 */           return false; 
/* 358 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/* 359 */           if (this.strategy.equals(curr, k))
/* 360 */             return false; 
/*     */         } 
/* 362 */       }  key[pos] = k;
/*     */     } 
/* 364 */     if (this.size++ >= this.maxFill) {
/* 365 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 368 */     return true;
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
/* 381 */     short[] key = this.key; while (true) {
/*     */       short curr; int last;
/* 383 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 385 */         if ((curr = key[pos]) == 0) {
/* 386 */           key[last] = 0;
/*     */           return;
/*     */         } 
/* 389 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/* 390 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 392 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 394 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int pos) {
/* 398 */     this.size--;
/* 399 */     shiftKeys(pos);
/* 400 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 401 */       rehash(this.n / 2); 
/* 402 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 405 */     this.containsNull = false;
/* 406 */     this.key[this.n] = 0;
/* 407 */     this.size--;
/* 408 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 409 */       rehash(this.n / 2); 
/* 410 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(short k) {
/* 415 */     if (this.strategy.equals(k, (short)0)) {
/* 416 */       if (this.containsNull)
/* 417 */         return removeNullEntry(); 
/* 418 */       return false;
/*     */     } 
/*     */     
/* 421 */     short[] key = this.key;
/*     */     short curr;
/*     */     int pos;
/* 424 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 425 */       return false; 
/* 426 */     if (this.strategy.equals(k, curr))
/* 427 */       return removeEntry(pos); 
/*     */     while (true) {
/* 429 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 430 */         return false; 
/* 431 */       if (this.strategy.equals(k, curr)) {
/* 432 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(short k) {
/* 438 */     if (this.strategy.equals(k, (short)0)) {
/* 439 */       return this.containsNull;
/*     */     }
/* 441 */     short[] key = this.key;
/*     */     short curr;
/*     */     int pos;
/* 444 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 445 */       return false; 
/* 446 */     if (this.strategy.equals(k, curr))
/* 447 */       return true; 
/*     */     while (true) {
/* 449 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 450 */         return false; 
/* 451 */       if (this.strategy.equals(k, curr)) {
/* 452 */         return true;
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
/* 464 */     if (this.size == 0)
/*     */       return; 
/* 466 */     this.size = 0;
/* 467 */     this.containsNull = false;
/* 468 */     Arrays.fill(this.key, (short)0);
/*     */   }
/*     */   
/*     */   public int size() {
/* 472 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 476 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ShortIterator
/*     */   {
/* 485 */     int pos = ShortOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 492 */     int last = -1;
/*     */     
/* 494 */     int c = ShortOpenCustomHashSet.this.size;
/*     */     
/* 496 */     boolean mustReturnNull = ShortOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     ShortArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 504 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 508 */       if (!hasNext())
/* 509 */         throw new NoSuchElementException(); 
/* 510 */       this.c--;
/* 511 */       if (this.mustReturnNull) {
/* 512 */         this.mustReturnNull = false;
/* 513 */         this.last = ShortOpenCustomHashSet.this.n;
/* 514 */         return ShortOpenCustomHashSet.this.key[ShortOpenCustomHashSet.this.n];
/*     */       } 
/* 516 */       short[] key = ShortOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 518 */         if (--this.pos < 0) {
/*     */           
/* 520 */           this.last = Integer.MIN_VALUE;
/* 521 */           return this.wrapped.getShort(-this.pos - 1);
/*     */         } 
/* 523 */         if (key[this.pos] != 0) {
/* 524 */           return key[this.last = this.pos];
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
/* 538 */       short[] key = ShortOpenCustomHashSet.this.key; while (true) {
/*     */         short curr; int last;
/* 540 */         pos = (last = pos) + 1 & ShortOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 542 */           if ((curr = key[pos]) == 0) {
/* 543 */             key[last] = 0;
/*     */             return;
/*     */           } 
/* 546 */           int slot = HashCommon.mix(ShortOpenCustomHashSet.this.strategy.hashCode(curr)) & ShortOpenCustomHashSet.this.mask;
/* 547 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 549 */           pos = pos + 1 & ShortOpenCustomHashSet.this.mask;
/*     */         } 
/* 551 */         if (pos < last) {
/* 552 */           if (this.wrapped == null)
/* 553 */             this.wrapped = new ShortArrayList(2); 
/* 554 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 556 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 561 */       if (this.last == -1)
/* 562 */         throw new IllegalStateException(); 
/* 563 */       if (this.last == ShortOpenCustomHashSet.this.n) {
/* 564 */         ShortOpenCustomHashSet.this.containsNull = false;
/* 565 */         ShortOpenCustomHashSet.this.key[ShortOpenCustomHashSet.this.n] = 0;
/* 566 */       } else if (this.pos >= 0) {
/* 567 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 570 */         ShortOpenCustomHashSet.this.remove(this.wrapped.getShort(-this.pos - 1));
/* 571 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 574 */       ShortOpenCustomHashSet.this.size--;
/* 575 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ShortIterator iterator() {
/* 582 */     return new SetIterator();
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
/* 599 */     return trim(this.size);
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
/* 623 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 624 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 625 */       return true; 
/*     */     try {
/* 627 */       rehash(l);
/* 628 */     } catch (OutOfMemoryError cantDoIt) {
/* 629 */       return false;
/*     */     } 
/* 631 */     return true;
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
/* 647 */     short[] key = this.key;
/* 648 */     int mask = newN - 1;
/* 649 */     short[] newKey = new short[newN + 1];
/* 650 */     int i = this.n;
/* 651 */     for (int j = realSize(); j-- != 0; ) {
/* 652 */       while (key[--i] == 0); int pos;
/* 653 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*     */       {
/* 655 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 656 */       newKey[pos] = key[i];
/*     */     } 
/* 658 */     this.n = newN;
/* 659 */     this.mask = mask;
/* 660 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 661 */     this.key = newKey;
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
/*     */   public ShortOpenCustomHashSet clone() {
/*     */     ShortOpenCustomHashSet c;
/*     */     try {
/* 678 */       c = (ShortOpenCustomHashSet)super.clone();
/* 679 */     } catch (CloneNotSupportedException cantHappen) {
/* 680 */       throw new InternalError();
/*     */     } 
/* 682 */     c.key = (short[])this.key.clone();
/* 683 */     c.containsNull = this.containsNull;
/* 684 */     c.strategy = this.strategy;
/* 685 */     return c;
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
/* 698 */     int h = 0;
/* 699 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 700 */       while (this.key[i] == 0)
/* 701 */         i++; 
/* 702 */       h += this.strategy.hashCode(this.key[i]);
/* 703 */       i++;
/*     */     } 
/*     */     
/* 706 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 709 */     ShortIterator i = iterator();
/* 710 */     s.defaultWriteObject();
/* 711 */     for (int j = this.size; j-- != 0;)
/* 712 */       s.writeShort(i.nextShort()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 716 */     s.defaultReadObject();
/* 717 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 718 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 719 */     this.mask = this.n - 1;
/* 720 */     short[] key = this.key = new short[this.n + 1];
/*     */     
/* 722 */     for (int i = this.size; i-- != 0; ) {
/* 723 */       int pos; short k = s.readShort();
/* 724 */       if (this.strategy.equals(k, (short)0)) {
/* 725 */         pos = this.n;
/* 726 */         this.containsNull = true;
/*     */       }
/* 728 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) {
/* 729 */         while (key[pos = pos + 1 & this.mask] != 0);
/*     */       } 
/* 731 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */