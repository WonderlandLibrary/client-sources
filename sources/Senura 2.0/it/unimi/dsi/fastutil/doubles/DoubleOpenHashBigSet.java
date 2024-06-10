/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public class DoubleOpenHashBigSet
/*     */   extends AbstractDoubleSet
/*     */   implements Serializable, Cloneable, Hash, Size64
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient double[][] key;
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
/*     */   public DoubleOpenHashBigSet(long expected, float f) {
/* 109 */     if (f <= 0.0F || f > 1.0F)
/* 110 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 111 */     if (this.n < 0L)
/* 112 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 113 */     this.f = f;
/* 114 */     this.minN = this.n = HashCommon.bigArraySize(expected, f);
/* 115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 116 */     this.key = DoubleBigArrays.newBigArray(this.n);
/* 117 */     initMasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(long expected) {
/* 127 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet() {
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
/*     */   public DoubleOpenHashBigSet(Collection<? extends Double> c, float f) {
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
/*     */   public DoubleOpenHashBigSet(Collection<? extends Double> c) {
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
/*     */   public DoubleOpenHashBigSet(DoubleCollection c, float f) {
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
/*     */   public DoubleOpenHashBigSet(DoubleCollection c) {
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
/*     */   public DoubleOpenHashBigSet(DoubleIterator i, float f) {
/* 192 */     this(16L, f);
/* 193 */     while (i.hasNext()) {
/* 194 */       add(i.nextDouble());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(DoubleIterator i) {
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
/*     */   public DoubleOpenHashBigSet(Iterator<?> i, float f) {
/* 216 */     this(DoubleIterators.asDoubleIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(Iterator<?> i) {
/* 226 */     this(DoubleIterators.asDoubleIterator(i));
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
/*     */   public DoubleOpenHashBigSet(double[] a, int offset, int length, float f) {
/* 241 */     this((length < 0) ? 0L : length, f);
/* 242 */     DoubleArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public DoubleOpenHashBigSet(double[] a, int offset, int length) {
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
/*     */   public DoubleOpenHashBigSet(double[] a, float f) {
/* 269 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashBigSet(double[] a) {
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
/*     */   public boolean addAll(Collection<? extends Double> c) {
/* 291 */     long size = (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/*     */     
/* 293 */     if (this.f <= 0.5D) {
/* 294 */       ensureCapacity(size);
/*     */     } else {
/* 296 */       ensureCapacity(size64() + size);
/* 297 */     }  return super.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean addAll(DoubleCollection c) {
/* 301 */     long size = (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/* 302 */     if (this.f <= 0.5D) {
/* 303 */       ensureCapacity(size);
/*     */     } else {
/* 305 */       ensureCapacity(size64() + size);
/* 306 */     }  return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(double k) {
/* 311 */     if (Double.doubleToLongBits(k) == 0L) {
/* 312 */       if (this.containsNull)
/* 313 */         return false; 
/* 314 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 317 */       double[][] key = this.key;
/* 318 */       long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */       int displ, base;
/*     */       double curr;
/* 321 */       if (Double.doubleToLongBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0L) {
/*     */         
/* 323 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/* 324 */           return false; 
/*     */         while (true) {
/* 326 */           if (Double.doubleToLongBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0L)
/*     */           
/* 328 */           { if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/* 329 */               return false;  continue; }  break;
/*     */         } 
/* 331 */       }  key[base][displ] = k;
/*     */     } 
/* 333 */     if (this.size++ >= this.maxFill) {
/* 334 */       rehash(2L * this.n);
/*     */     }
/*     */     
/* 337 */     return true;
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
/* 349 */     double[][] key = this.key; while (true) {
/*     */       long last;
/* 351 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 353 */         if (Double.doubleToLongBits(BigArrays.get(key, pos)) == 0L) {
/* 354 */           BigArrays.set(key, last, 0.0D);
/*     */           return;
/*     */         } 
/* 357 */         long slot = HashCommon.mix(Double.doubleToRawLongBits(BigArrays.get(key, pos))) & this.mask;
/* 358 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 360 */         pos = pos + 1L & this.mask;
/*     */       } 
/* 362 */       BigArrays.set(key, last, BigArrays.get(key, pos));
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int base, int displ) {
/* 366 */     this.size--;
/* 367 */     shiftKeys(base * 134217728L + displ);
/* 368 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 369 */       rehash(this.n / 2L); 
/* 370 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 373 */     this.containsNull = false;
/* 374 */     this.size--;
/* 375 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 376 */       rehash(this.n / 2L); 
/* 377 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(double k) {
/* 381 */     if (Double.doubleToLongBits(k) == 0L) {
/* 382 */       if (this.containsNull)
/* 383 */         return removeNullEntry(); 
/* 384 */       return false;
/*     */     } 
/*     */     
/* 387 */     double[][] key = this.key;
/* 388 */     long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */     double curr;
/*     */     int displ, base;
/* 391 */     if (Double.doubleToLongBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0L)
/*     */     {
/* 393 */       return false; } 
/* 394 */     if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/* 395 */       return removeEntry(base, displ); 
/*     */     while (true) {
/* 397 */       if (Double.doubleToLongBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0L)
/*     */       {
/* 399 */         return false; } 
/* 400 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/* 401 */         return removeEntry(base, displ); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(double k) {
/* 406 */     if (Double.doubleToLongBits(k) == 0L) {
/* 407 */       return this.containsNull;
/*     */     }
/* 409 */     double[][] key = this.key;
/* 410 */     long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */     double curr;
/*     */     int displ, base;
/* 413 */     if (Double.doubleToLongBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0L)
/*     */     {
/* 415 */       return false; } 
/* 416 */     if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/* 417 */       return true; 
/*     */     while (true) {
/* 419 */       if (Double.doubleToLongBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0L)
/*     */       {
/* 421 */         return false; } 
/* 422 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 423 */         return true;
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
/* 439 */     if (this.size == 0L)
/*     */       return; 
/* 441 */     this.size = 0L;
/* 442 */     this.containsNull = false;
/* 443 */     BigArrays.fill(this.key, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements DoubleIterator
/*     */   {
/* 452 */     int base = DoubleOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 460 */     long last = -1L;
/*     */     
/* 462 */     long c = DoubleOpenHashBigSet.this.size;
/*     */     
/* 464 */     boolean mustReturnNull = DoubleOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     DoubleArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 472 */       return (this.c != 0L);
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 476 */       if (!hasNext())
/* 477 */         throw new NoSuchElementException(); 
/* 478 */       this.c--;
/* 479 */       if (this.mustReturnNull) {
/* 480 */         this.mustReturnNull = false;
/* 481 */         this.last = DoubleOpenHashBigSet.this.n;
/* 482 */         return 0.0D;
/*     */       } 
/* 484 */       double[][] key = DoubleOpenHashBigSet.this.key;
/*     */       while (true) {
/* 486 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 488 */           this.last = Long.MIN_VALUE;
/* 489 */           return this.wrapped.getDouble(---this.base - 1);
/*     */         } 
/* 491 */         if (this.displ-- == 0)
/* 492 */           this.displ = (key[--this.base]).length - 1; 
/* 493 */         double k = key[this.base][this.displ];
/* 494 */         if (Double.doubleToLongBits(k) != 0L) {
/* 495 */           this.last = this.base * 134217728L + this.displ;
/* 496 */           return k;
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
/* 511 */       double[][] key = DoubleOpenHashBigSet.this.key; while (true) {
/*     */         double curr; long last;
/* 513 */         pos = (last = pos) + 1L & DoubleOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 515 */           if (Double.doubleToLongBits(curr = BigArrays.get(key, pos)) == 0L) {
/* 516 */             BigArrays.set(key, last, 0.0D);
/*     */             return;
/*     */           } 
/* 519 */           long slot = HashCommon.mix(Double.doubleToRawLongBits(curr)) & DoubleOpenHashBigSet.this.mask;
/* 520 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 522 */           pos = pos + 1L & DoubleOpenHashBigSet.this.mask;
/*     */         } 
/* 524 */         if (pos < last) {
/* 525 */           if (this.wrapped == null)
/* 526 */             this.wrapped = new DoubleArrayList(); 
/* 527 */           this.wrapped.add(BigArrays.get(key, pos));
/*     */         } 
/* 529 */         BigArrays.set(key, last, curr);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 534 */       if (this.last == -1L)
/* 535 */         throw new IllegalStateException(); 
/* 536 */       if (this.last == DoubleOpenHashBigSet.this.n) {
/* 537 */         DoubleOpenHashBigSet.this.containsNull = false;
/* 538 */       } else if (this.base >= 0) {
/* 539 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 542 */         DoubleOpenHashBigSet.this.remove(this.wrapped.getDouble(-this.base - 1));
/* 543 */         this.last = -1L;
/*     */         return;
/*     */       } 
/* 546 */       DoubleOpenHashBigSet.this.size--;
/* 547 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public DoubleIterator iterator() {
/* 554 */     return new SetIterator();
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
/* 571 */     return trim(this.size);
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
/* 595 */     long l = HashCommon.bigArraySize(n, this.f);
/* 596 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 597 */       return true; 
/*     */     try {
/* 599 */       rehash(l);
/* 600 */     } catch (OutOfMemoryError cantDoIt) {
/* 601 */       return false;
/*     */     } 
/* 603 */     return true;
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
/* 619 */     double[][] key = this.key;
/* 620 */     double[][] newKey = DoubleBigArrays.newBigArray(newN);
/* 621 */     long mask = newN - 1L;
/* 622 */     int newSegmentMask = (newKey[0]).length - 1;
/* 623 */     int newBaseMask = newKey.length - 1;
/* 624 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 627 */     for (long i = realSize(); i-- != 0L; ) {
/* 628 */       while (Double.doubleToLongBits(key[base][displ]) == 0L)
/* 629 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 630 */       double k = key[base][displ];
/* 631 */       long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */       int b, d;
/* 633 */       if (Double.doubleToLongBits(newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)]) != 0L)
/*     */         while (true)
/* 635 */         { if (Double.doubleToLongBits(newKey[
/* 636 */                 b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d]) != 0L)
/* 637 */             continue;  break; }   newKey[b][d] = k;
/* 638 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 640 */     this.n = newN;
/* 641 */     this.key = newKey;
/* 642 */     initMasks();
/* 643 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 648 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 652 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 656 */     return (this.size == 0L);
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
/*     */   public DoubleOpenHashBigSet clone() {
/*     */     DoubleOpenHashBigSet c;
/*     */     try {
/* 673 */       c = (DoubleOpenHashBigSet)super.clone();
/* 674 */     } catch (CloneNotSupportedException cantHappen) {
/* 675 */       throw new InternalError();
/*     */     } 
/* 677 */     c.key = BigArrays.copy(this.key);
/* 678 */     c.containsNull = this.containsNull;
/* 679 */     return c;
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
/* 692 */     double[][] key = this.key;
/* 693 */     int h = 0, base = 0, displ = 0;
/* 694 */     for (long j = realSize(); j-- != 0L; ) {
/* 695 */       while (Double.doubleToLongBits(key[base][displ]) == 0L)
/* 696 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 697 */       h += HashCommon.double2int(key[base][displ]);
/* 698 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 700 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 703 */     DoubleIterator i = iterator();
/* 704 */     s.defaultWriteObject();
/* 705 */     for (long j = this.size; j-- != 0L;)
/* 706 */       s.writeDouble(i.nextDouble()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 710 */     s.defaultReadObject();
/* 711 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 712 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 713 */     double[][] key = this.key = DoubleBigArrays.newBigArray(this.n);
/* 714 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 718 */     for (long i = this.size; i-- != 0L; ) {
/* 719 */       double k = s.readDouble();
/* 720 */       if (Double.doubleToLongBits(k) == 0L) {
/* 721 */         this.containsNull = true; continue;
/*     */       } 
/* 723 */       long h = HashCommon.mix(Double.doubleToRawLongBits(k));
/*     */       int base, displ;
/* 725 */       if (Double.doubleToLongBits(key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0L)
/*     */         while (true) {
/*     */           
/* 728 */           if (Double.doubleToLongBits(key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0L)
/*     */             continue;  break;
/* 730 */         }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */