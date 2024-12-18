/*     */ package it.unimi.dsi.fastutil.longs;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.BigArrays;
/*     */ import it.unimi.dsi.fastutil.Hash;
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.Size64;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ public class LongOpenHashBigSet
/*     */   extends AbstractLongSet
/*     */   implements Serializable, Cloneable, Hash, Size64
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient long[][] key;
/*     */   protected transient long mask;
/*     */   protected transient int segmentMask;
/*     */   protected transient int baseMask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient long n;
/*     */   protected transient long maxFill;
/*     */   protected final transient long minN;
/*     */   protected final float f;
/*     */   protected long size;
/*     */   
/*     */   private void initMasks() {
/*  86 */     this.mask = this.n - 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     this.segmentMask = (this.key[0]).length - 1;
/*  93 */     this.baseMask = this.key.length - 1;
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
/*     */   public LongOpenHashBigSet(long expected, float f) {
/* 109 */     if (f <= 0.0F || f > 1.0F)
/* 110 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 111 */     if (this.n < 0L)
/* 112 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 113 */     this.f = f;
/* 114 */     this.minN = this.n = HashCommon.bigArraySize(expected, f);
/* 115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 116 */     this.key = LongBigArrays.newBigArray(this.n);
/* 117 */     initMasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(long expected) {
/* 127 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet() {
/* 135 */     this(16L, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(Collection<? extends Long> c, float f) {
/* 146 */     this(c.size(), f);
/* 147 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(Collection<? extends Long> c) {
/* 157 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(LongCollection c, float f) {
/* 168 */     this(c.size(), f);
/* 169 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(LongCollection c) {
/* 179 */     this(c, 0.75F);
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
/*     */   public LongOpenHashBigSet(LongIterator i, float f) {
/* 192 */     this(16L, f);
/* 193 */     while (i.hasNext()) {
/* 194 */       add(i.nextLong());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(LongIterator i) {
/* 205 */     this(i, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(Iterator<?> i, float f) {
/* 216 */     this(LongIterators.asLongIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(Iterator<?> i) {
/* 226 */     this(LongIterators.asLongIterator(i));
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
/*     */   public LongOpenHashBigSet(long[] a, int offset, int length, float f) {
/* 241 */     this((length < 0) ? 0L : length, f);
/* 242 */     LongArrays.ensureOffsetLength(a, offset, length);
/* 243 */     for (int i = 0; i < length; i++) {
/* 244 */       add(a[offset + i]);
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
/*     */   public LongOpenHashBigSet(long[] a, int offset, int length) {
/* 258 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(long[] a, float f) {
/* 269 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LongOpenHashBigSet(long[] a) {
/* 279 */     this(a, 0.75F);
/*     */   }
/*     */   private long realSize() {
/* 282 */     return this.containsNull ? (this.size - 1L) : this.size;
/*     */   }
/*     */   private void ensureCapacity(long capacity) {
/* 285 */     long needed = HashCommon.bigArraySize(capacity, this.f);
/* 286 */     if (needed > this.n)
/* 287 */       rehash(needed); 
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends Long> c) {
/* 291 */     long size = (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/*     */     
/* 293 */     if (this.f <= 0.5D) {
/* 294 */       ensureCapacity(size);
/*     */     } else {
/* 296 */       ensureCapacity(size64() + size);
/* 297 */     }  return super.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean addAll(LongCollection c) {
/* 301 */     long size = (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/* 302 */     if (this.f <= 0.5D) {
/* 303 */       ensureCapacity(size);
/*     */     } else {
/* 305 */       ensureCapacity(size64() + size);
/* 306 */     }  return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(long k) {
/* 311 */     if (k == 0L) {
/* 312 */       if (this.containsNull)
/* 313 */         return false; 
/* 314 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 317 */       long[][] key = this.key;
/* 318 */       long h = HashCommon.mix(k); int displ, base;
/*     */       long curr;
/* 320 */       if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0L) {
/*     */         
/* 322 */         if (curr == k)
/* 323 */           return false;  while (true) {
/* 324 */           if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0L)
/*     */           
/* 326 */           { if (curr == k)
/* 327 */               return false;  continue; }  break;
/*     */         } 
/* 329 */       }  key[base][displ] = k;
/*     */     } 
/* 331 */     if (this.size++ >= this.maxFill) {
/* 332 */       rehash(2L * this.n);
/*     */     }
/*     */     
/* 335 */     return true;
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
/*     */   protected final void shiftKeys(long pos) {
/* 347 */     long[][] key = this.key; while (true) {
/*     */       long last;
/* 349 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 351 */         if (BigArrays.get(key, pos) == 0L) {
/* 352 */           BigArrays.set(key, last, 0L);
/*     */           return;
/*     */         } 
/* 355 */         long slot = HashCommon.mix(BigArrays.get(key, pos)) & this.mask;
/* 356 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 358 */         pos = pos + 1L & this.mask;
/*     */       } 
/* 360 */       BigArrays.set(key, last, BigArrays.get(key, pos));
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int base, int displ) {
/* 364 */     this.size--;
/* 365 */     shiftKeys(base * 134217728L + displ);
/* 366 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 367 */       rehash(this.n / 2L); 
/* 368 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 371 */     this.containsNull = false;
/* 372 */     this.size--;
/* 373 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 374 */       rehash(this.n / 2L); 
/* 375 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(long k) {
/* 379 */     if (k == 0L) {
/* 380 */       if (this.containsNull)
/* 381 */         return removeNullEntry(); 
/* 382 */       return false;
/*     */     } 
/*     */     
/* 385 */     long[][] key = this.key;
/* 386 */     long h = HashCommon.mix(k);
/*     */     long curr;
/*     */     int displ, base;
/* 389 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0L)
/*     */     {
/* 391 */       return false; } 
/* 392 */     if (curr == k)
/* 393 */       return removeEntry(base, displ); 
/*     */     while (true) {
/* 395 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0L)
/*     */       {
/* 397 */         return false; } 
/* 398 */       if (curr == k)
/* 399 */         return removeEntry(base, displ); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(long k) {
/* 404 */     if (k == 0L) {
/* 405 */       return this.containsNull;
/*     */     }
/* 407 */     long[][] key = this.key;
/* 408 */     long h = HashCommon.mix(k);
/*     */     long curr;
/*     */     int displ, base;
/* 411 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0L)
/*     */     {
/* 413 */       return false; } 
/* 414 */     if (curr == k)
/* 415 */       return true; 
/*     */     while (true) {
/* 417 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0L)
/*     */       {
/* 419 */         return false; } 
/* 420 */       if (curr == k) {
/* 421 */         return true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 437 */     if (this.size == 0L)
/*     */       return; 
/* 439 */     this.size = 0L;
/* 440 */     this.containsNull = false;
/* 441 */     BigArrays.fill(this.key, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements LongIterator
/*     */   {
/* 450 */     int base = LongOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 458 */     long last = -1L;
/*     */     
/* 460 */     long c = LongOpenHashBigSet.this.size;
/*     */     
/* 462 */     boolean mustReturnNull = LongOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     LongArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 470 */       return (this.c != 0L);
/*     */     }
/*     */     
/*     */     public long nextLong() {
/* 474 */       if (!hasNext())
/* 475 */         throw new NoSuchElementException(); 
/* 476 */       this.c--;
/* 477 */       if (this.mustReturnNull) {
/* 478 */         this.mustReturnNull = false;
/* 479 */         this.last = LongOpenHashBigSet.this.n;
/* 480 */         return 0L;
/*     */       } 
/* 482 */       long[][] key = LongOpenHashBigSet.this.key;
/*     */       while (true) {
/* 484 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 486 */           this.last = Long.MIN_VALUE;
/* 487 */           return this.wrapped.getLong(---this.base - 1);
/*     */         } 
/* 489 */         if (this.displ-- == 0)
/* 490 */           this.displ = (key[--this.base]).length - 1; 
/* 491 */         long k = key[this.base][this.displ];
/* 492 */         if (k != 0L) {
/* 493 */           this.last = this.base * 134217728L + this.displ;
/* 494 */           return k;
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
/*     */     
/*     */     private final void shiftKeys(long pos) {
/* 509 */       long[][] key = LongOpenHashBigSet.this.key; while (true) {
/*     */         long curr, last;
/* 511 */         pos = (last = pos) + 1L & LongOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 513 */           if ((curr = BigArrays.get(key, pos)) == 0L) {
/* 514 */             BigArrays.set(key, last, 0L);
/*     */             return;
/*     */           } 
/* 517 */           long slot = HashCommon.mix(curr) & LongOpenHashBigSet.this.mask;
/* 518 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 520 */           pos = pos + 1L & LongOpenHashBigSet.this.mask;
/*     */         } 
/* 522 */         if (pos < last) {
/* 523 */           if (this.wrapped == null)
/* 524 */             this.wrapped = new LongArrayList(); 
/* 525 */           this.wrapped.add(BigArrays.get(key, pos));
/*     */         } 
/* 527 */         BigArrays.set(key, last, curr);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 532 */       if (this.last == -1L)
/* 533 */         throw new IllegalStateException(); 
/* 534 */       if (this.last == LongOpenHashBigSet.this.n) {
/* 535 */         LongOpenHashBigSet.this.containsNull = false;
/* 536 */       } else if (this.base >= 0) {
/* 537 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 540 */         LongOpenHashBigSet.this.remove(this.wrapped.getLong(-this.base - 1));
/* 541 */         this.last = -1L;
/*     */         return;
/*     */       } 
/* 544 */       LongOpenHashBigSet.this.size--;
/* 545 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public LongIterator iterator() {
/* 552 */     return new SetIterator();
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
/* 569 */     return trim(this.size);
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
/*     */   public boolean trim(long n) {
/* 593 */     long l = HashCommon.bigArraySize(n, this.f);
/* 594 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 595 */       return true; 
/*     */     try {
/* 597 */       rehash(l);
/* 598 */     } catch (OutOfMemoryError cantDoIt) {
/* 599 */       return false;
/*     */     } 
/* 601 */     return true;
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
/*     */   protected void rehash(long newN) {
/* 617 */     long[][] key = this.key;
/* 618 */     long[][] newKey = LongBigArrays.newBigArray(newN);
/* 619 */     long mask = newN - 1L;
/* 620 */     int newSegmentMask = (newKey[0]).length - 1;
/* 621 */     int newBaseMask = newKey.length - 1;
/* 622 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 625 */     for (long i = realSize(); i-- != 0L; ) {
/* 626 */       while (key[base][displ] == 0L)
/* 627 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 628 */       long k = key[base][displ];
/* 629 */       long h = HashCommon.mix(k);
/*     */       int b, d;
/* 631 */       if (newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)] != 0L)
/* 632 */         while (true) { if (newKey[b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d] != 0L)
/* 633 */             continue;  break; }   newKey[b][d] = k;
/* 634 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 636 */     this.n = newN;
/* 637 */     this.key = newKey;
/* 638 */     initMasks();
/* 639 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 644 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 648 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 652 */     return (this.size == 0L);
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
/*     */   public LongOpenHashBigSet clone() {
/*     */     LongOpenHashBigSet c;
/*     */     try {
/* 669 */       c = (LongOpenHashBigSet)super.clone();
/* 670 */     } catch (CloneNotSupportedException cantHappen) {
/* 671 */       throw new InternalError();
/*     */     } 
/* 673 */     c.key = BigArrays.copy(this.key);
/* 674 */     c.containsNull = this.containsNull;
/* 675 */     return c;
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
/* 688 */     long[][] key = this.key;
/* 689 */     int h = 0, base = 0, displ = 0;
/* 690 */     for (long j = realSize(); j-- != 0L; ) {
/* 691 */       while (key[base][displ] == 0L)
/* 692 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 693 */       h += HashCommon.long2int(key[base][displ]);
/* 694 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 696 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 699 */     LongIterator i = iterator();
/* 700 */     s.defaultWriteObject();
/* 701 */     for (long j = this.size; j-- != 0L;)
/* 702 */       s.writeLong(i.nextLong()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 706 */     s.defaultReadObject();
/* 707 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 708 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 709 */     long[][] key = this.key = LongBigArrays.newBigArray(this.n);
/* 710 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 714 */     for (long i = this.size; i-- != 0L; ) {
/* 715 */       long k = s.readLong();
/* 716 */       if (k == 0L) {
/* 717 */         this.containsNull = true; continue;
/*     */       } 
/* 719 */       long h = HashCommon.mix(k); int base, displ;
/* 720 */       if (key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)] != 0L)
/*     */         while (true) {
/* 722 */           if (key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ] != 0L)
/*     */             continue;  break;
/* 724 */         }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */