/*     */ package it.unimi.dsi.fastutil.bytes;
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
/*     */ public class ByteOpenCustomHashSet
/*     */   extends AbstractByteSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient byte[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected ByteHash.Strategy strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ByteOpenCustomHashSet(int expected, float f, ByteHash.Strategy strategy) {
/*  93 */     this.strategy = strategy;
/*  94 */     if (f <= 0.0F || f > 1.0F)
/*  95 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  96 */     if (expected < 0)
/*  97 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  98 */     this.f = f;
/*  99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/* 100 */     this.mask = this.n - 1;
/* 101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 102 */     this.key = new byte[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOpenCustomHashSet(int expected, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(Collection<? extends Byte> c, float f, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(Collection<? extends Byte> c, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(ByteCollection c, float f, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(ByteCollection c, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(ByteIterator i, float f, ByteHash.Strategy strategy) {
/* 193 */     this(16, f, strategy);
/* 194 */     while (i.hasNext()) {
/* 195 */       add(i.nextByte());
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
/*     */   public ByteOpenCustomHashSet(ByteIterator i, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(Iterator<?> i, float f, ByteHash.Strategy strategy) {
/* 221 */     this(ByteIterators.asByteIterator(i), f, strategy);
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
/*     */   public ByteOpenCustomHashSet(Iterator<?> i, ByteHash.Strategy strategy) {
/* 233 */     this(ByteIterators.asByteIterator(i), strategy);
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
/*     */   public ByteOpenCustomHashSet(byte[] a, int offset, int length, float f, ByteHash.Strategy strategy) {
/* 251 */     this((length < 0) ? 0 : length, f, strategy);
/* 252 */     ByteArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public ByteOpenCustomHashSet(byte[] a, int offset, int length, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(byte[] a, float f, ByteHash.Strategy strategy) {
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
/*     */   public ByteOpenCustomHashSet(byte[] a, ByteHash.Strategy strategy) {
/* 297 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteHash.Strategy strategy() {
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
/*     */   public boolean addAll(ByteCollection c) {
/* 323 */     if (this.f <= 0.5D) {
/* 324 */       ensureCapacity(c.size());
/*     */     } else {
/* 326 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 328 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Byte> c) {
/* 333 */     if (this.f <= 0.5D) {
/* 334 */       ensureCapacity(c.size());
/*     */     } else {
/* 336 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 338 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(byte k) {
/* 343 */     if (this.strategy.equals(k, (byte)0)) {
/* 344 */       if (this.containsNull)
/* 345 */         return false; 
/* 346 */       this.containsNull = true;
/* 347 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 350 */       byte[] key = this.key; int pos;
/*     */       byte curr;
/* 352 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*     */         
/* 354 */         if (this.strategy.equals(curr, k))
/* 355 */           return false; 
/* 356 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/* 357 */           if (this.strategy.equals(curr, k))
/* 358 */             return false; 
/*     */         } 
/* 360 */       }  key[pos] = k;
/*     */     } 
/* 362 */     if (this.size++ >= this.maxFill) {
/* 363 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 366 */     return true;
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
/* 379 */     byte[] key = this.key; while (true) {
/*     */       byte curr; int last;
/* 381 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 383 */         if ((curr = key[pos]) == 0) {
/* 384 */           key[last] = 0;
/*     */           return;
/*     */         } 
/* 387 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/* 388 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 390 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 392 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int pos) {
/* 396 */     this.size--;
/* 397 */     shiftKeys(pos);
/* 398 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 399 */       rehash(this.n / 2); 
/* 400 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 403 */     this.containsNull = false;
/* 404 */     this.key[this.n] = 0;
/* 405 */     this.size--;
/* 406 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 407 */       rehash(this.n / 2); 
/* 408 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(byte k) {
/* 413 */     if (this.strategy.equals(k, (byte)0)) {
/* 414 */       if (this.containsNull)
/* 415 */         return removeNullEntry(); 
/* 416 */       return false;
/*     */     } 
/*     */     
/* 419 */     byte[] key = this.key;
/*     */     byte curr;
/*     */     int pos;
/* 422 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 423 */       return false; 
/* 424 */     if (this.strategy.equals(k, curr))
/* 425 */       return removeEntry(pos); 
/*     */     while (true) {
/* 427 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 428 */         return false; 
/* 429 */       if (this.strategy.equals(k, curr)) {
/* 430 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(byte k) {
/* 436 */     if (this.strategy.equals(k, (byte)0)) {
/* 437 */       return this.containsNull;
/*     */     }
/* 439 */     byte[] key = this.key;
/*     */     byte curr;
/*     */     int pos;
/* 442 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 443 */       return false; 
/* 444 */     if (this.strategy.equals(k, curr))
/* 445 */       return true; 
/*     */     while (true) {
/* 447 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 448 */         return false; 
/* 449 */       if (this.strategy.equals(k, curr)) {
/* 450 */         return true;
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
/* 462 */     if (this.size == 0)
/*     */       return; 
/* 464 */     this.size = 0;
/* 465 */     this.containsNull = false;
/* 466 */     Arrays.fill(this.key, (byte)0);
/*     */   }
/*     */   
/*     */   public int size() {
/* 470 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 474 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ByteIterator
/*     */   {
/* 483 */     int pos = ByteOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     int last = -1;
/*     */     
/* 492 */     int c = ByteOpenCustomHashSet.this.size;
/*     */     
/* 494 */     boolean mustReturnNull = ByteOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     ByteArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 502 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public byte nextByte() {
/* 506 */       if (!hasNext())
/* 507 */         throw new NoSuchElementException(); 
/* 508 */       this.c--;
/* 509 */       if (this.mustReturnNull) {
/* 510 */         this.mustReturnNull = false;
/* 511 */         this.last = ByteOpenCustomHashSet.this.n;
/* 512 */         return ByteOpenCustomHashSet.this.key[ByteOpenCustomHashSet.this.n];
/*     */       } 
/* 514 */       byte[] key = ByteOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 516 */         if (--this.pos < 0) {
/*     */           
/* 518 */           this.last = Integer.MIN_VALUE;
/* 519 */           return this.wrapped.getByte(-this.pos - 1);
/*     */         } 
/* 521 */         if (key[this.pos] != 0) {
/* 522 */           return key[this.last = this.pos];
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
/* 536 */       byte[] key = ByteOpenCustomHashSet.this.key; while (true) {
/*     */         byte curr; int last;
/* 538 */         pos = (last = pos) + 1 & ByteOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 540 */           if ((curr = key[pos]) == 0) {
/* 541 */             key[last] = 0;
/*     */             return;
/*     */           } 
/* 544 */           int slot = HashCommon.mix(ByteOpenCustomHashSet.this.strategy.hashCode(curr)) & ByteOpenCustomHashSet.this.mask;
/* 545 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 547 */           pos = pos + 1 & ByteOpenCustomHashSet.this.mask;
/*     */         } 
/* 549 */         if (pos < last) {
/* 550 */           if (this.wrapped == null)
/* 551 */             this.wrapped = new ByteArrayList(2); 
/* 552 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 554 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 559 */       if (this.last == -1)
/* 560 */         throw new IllegalStateException(); 
/* 561 */       if (this.last == ByteOpenCustomHashSet.this.n) {
/* 562 */         ByteOpenCustomHashSet.this.containsNull = false;
/* 563 */         ByteOpenCustomHashSet.this.key[ByteOpenCustomHashSet.this.n] = 0;
/* 564 */       } else if (this.pos >= 0) {
/* 565 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 568 */         ByteOpenCustomHashSet.this.remove(this.wrapped.getByte(-this.pos - 1));
/* 569 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 572 */       ByteOpenCustomHashSet.this.size--;
/* 573 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ByteIterator iterator() {
/* 580 */     return new SetIterator();
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
/* 597 */     return trim(this.size);
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
/* 621 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 622 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 623 */       return true; 
/*     */     try {
/* 625 */       rehash(l);
/* 626 */     } catch (OutOfMemoryError cantDoIt) {
/* 627 */       return false;
/*     */     } 
/* 629 */     return true;
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
/* 645 */     byte[] key = this.key;
/* 646 */     int mask = newN - 1;
/* 647 */     byte[] newKey = new byte[newN + 1];
/* 648 */     int i = this.n;
/* 649 */     for (int j = realSize(); j-- != 0; ) {
/* 650 */       while (key[--i] == 0); int pos;
/* 651 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != 0)
/*     */       {
/* 653 */         while (newKey[pos = pos + 1 & mask] != 0); } 
/* 654 */       newKey[pos] = key[i];
/*     */     } 
/* 656 */     this.n = newN;
/* 657 */     this.mask = mask;
/* 658 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 659 */     this.key = newKey;
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
/*     */   public ByteOpenCustomHashSet clone() {
/*     */     ByteOpenCustomHashSet c;
/*     */     try {
/* 676 */       c = (ByteOpenCustomHashSet)super.clone();
/* 677 */     } catch (CloneNotSupportedException cantHappen) {
/* 678 */       throw new InternalError();
/*     */     } 
/* 680 */     c.key = (byte[])this.key.clone();
/* 681 */     c.containsNull = this.containsNull;
/* 682 */     c.strategy = this.strategy;
/* 683 */     return c;
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
/* 696 */     int h = 0;
/* 697 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 698 */       while (this.key[i] == 0)
/* 699 */         i++; 
/* 700 */       h += this.strategy.hashCode(this.key[i]);
/* 701 */       i++;
/*     */     } 
/*     */     
/* 704 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 707 */     ByteIterator i = iterator();
/* 708 */     s.defaultWriteObject();
/* 709 */     for (int j = this.size; j-- != 0;)
/* 710 */       s.writeByte(i.nextByte()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 714 */     s.defaultReadObject();
/* 715 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 716 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 717 */     this.mask = this.n - 1;
/* 718 */     byte[] key = this.key = new byte[this.n + 1];
/*     */     
/* 720 */     for (int i = this.size; i-- != 0; ) {
/* 721 */       int pos; byte k = s.readByte();
/* 722 */       if (this.strategy.equals(k, (byte)0)) {
/* 723 */         pos = this.n;
/* 724 */         this.containsNull = true;
/*     */       }
/* 726 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) {
/* 727 */         while (key[pos = pos + 1 & this.mask] != 0);
/*     */       } 
/* 729 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\bytes\ByteOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */