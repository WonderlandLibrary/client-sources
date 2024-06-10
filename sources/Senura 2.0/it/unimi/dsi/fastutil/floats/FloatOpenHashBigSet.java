/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public class FloatOpenHashBigSet
/*     */   extends AbstractFloatSet
/*     */   implements Serializable, Cloneable, Hash, Size64
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient float[][] key;
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
/*     */   public FloatOpenHashBigSet(long expected, float f) {
/* 109 */     if (f <= 0.0F || f > 1.0F)
/* 110 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 111 */     if (this.n < 0L)
/* 112 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 113 */     this.f = f;
/* 114 */     this.minN = this.n = HashCommon.bigArraySize(expected, f);
/* 115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 116 */     this.key = FloatBigArrays.newBigArray(this.n);
/* 117 */     initMasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(long expected) {
/* 127 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet() {
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
/*     */   public FloatOpenHashBigSet(Collection<? extends Float> c, float f) {
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
/*     */   public FloatOpenHashBigSet(Collection<? extends Float> c) {
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
/*     */   public FloatOpenHashBigSet(FloatCollection c, float f) {
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
/*     */   public FloatOpenHashBigSet(FloatCollection c) {
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
/*     */   public FloatOpenHashBigSet(FloatIterator i, float f) {
/* 192 */     this(16L, f);
/* 193 */     while (i.hasNext()) {
/* 194 */       add(i.nextFloat());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(FloatIterator i) {
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
/*     */   public FloatOpenHashBigSet(Iterator<?> i, float f) {
/* 216 */     this(FloatIterators.asFloatIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(Iterator<?> i) {
/* 226 */     this(FloatIterators.asFloatIterator(i));
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
/*     */   public FloatOpenHashBigSet(float[] a, int offset, int length, float f) {
/* 241 */     this((length < 0) ? 0L : length, f);
/* 242 */     FloatArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public FloatOpenHashBigSet(float[] a, int offset, int length) {
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
/*     */   public FloatOpenHashBigSet(float[] a, float f) {
/* 269 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashBigSet(float[] a) {
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
/*     */   public boolean addAll(Collection<? extends Float> c) {
/* 291 */     long size = (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/*     */     
/* 293 */     if (this.f <= 0.5D) {
/* 294 */       ensureCapacity(size);
/*     */     } else {
/* 296 */       ensureCapacity(size64() + size);
/* 297 */     }  return super.addAll(c);
/*     */   }
/*     */   
/*     */   public boolean addAll(FloatCollection c) {
/* 301 */     long size = (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/* 302 */     if (this.f <= 0.5D) {
/* 303 */       ensureCapacity(size);
/*     */     } else {
/* 305 */       ensureCapacity(size64() + size);
/* 306 */     }  return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(float k) {
/* 311 */     if (Float.floatToIntBits(k) == 0) {
/* 312 */       if (this.containsNull)
/* 313 */         return false; 
/* 314 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 317 */       float[][] key = this.key;
/* 318 */       long h = HashCommon.mix(HashCommon.float2int(k));
/*     */       int displ, base;
/*     */       float curr;
/* 321 */       if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0) {
/*     */         
/* 323 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/* 324 */           return false; 
/*     */         while (true) {
/* 326 */           if (Float.floatToIntBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0)
/*     */           
/* 328 */           { if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
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
/* 349 */     float[][] key = this.key; while (true) {
/*     */       long last;
/* 351 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 353 */         if (Float.floatToIntBits(BigArrays.get(key, pos)) == 0) {
/* 354 */           BigArrays.set(key, last, 0.0F);
/*     */           
/*     */           return;
/*     */         } 
/* 358 */         long slot = HashCommon.mix(HashCommon.float2int(BigArrays.get(key, pos))) & this.mask;
/* 359 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 361 */         pos = pos + 1L & this.mask;
/*     */       } 
/* 363 */       BigArrays.set(key, last, BigArrays.get(key, pos));
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int base, int displ) {
/* 367 */     this.size--;
/* 368 */     shiftKeys(base * 134217728L + displ);
/* 369 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 370 */       rehash(this.n / 2L); 
/* 371 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 374 */     this.containsNull = false;
/* 375 */     this.size--;
/* 376 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 377 */       rehash(this.n / 2L); 
/* 378 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(float k) {
/* 382 */     if (Float.floatToIntBits(k) == 0) {
/* 383 */       if (this.containsNull)
/* 384 */         return removeNullEntry(); 
/* 385 */       return false;
/*     */     } 
/*     */     
/* 388 */     float[][] key = this.key;
/* 389 */     long h = HashCommon.mix(HashCommon.float2int(k));
/*     */     float curr;
/*     */     int displ, base;
/* 392 */     if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0)
/*     */     {
/* 394 */       return false; } 
/* 395 */     if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/* 396 */       return removeEntry(base, displ); 
/*     */     while (true) {
/* 398 */       if (Float.floatToIntBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0)
/*     */       {
/* 400 */         return false; } 
/* 401 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/* 402 */         return removeEntry(base, displ); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(float k) {
/* 407 */     if (Float.floatToIntBits(k) == 0) {
/* 408 */       return this.containsNull;
/*     */     }
/* 410 */     float[][] key = this.key;
/* 411 */     long h = HashCommon.mix(HashCommon.float2int(k));
/*     */     float curr;
/*     */     int displ, base;
/* 414 */     if (Float.floatToIntBits(curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == 0)
/*     */     {
/* 416 */       return false; } 
/* 417 */     if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/* 418 */       return true; 
/*     */     while (true) {
/* 420 */       if (Float.floatToIntBits(curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == 0)
/*     */       {
/* 422 */         return false; } 
/* 423 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 424 */         return true;
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
/* 440 */     if (this.size == 0L)
/*     */       return; 
/* 442 */     this.size = 0L;
/* 443 */     this.containsNull = false;
/* 444 */     BigArrays.fill(this.key, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements FloatIterator
/*     */   {
/* 453 */     int base = FloatOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 461 */     long last = -1L;
/*     */     
/* 463 */     long c = FloatOpenHashBigSet.this.size;
/*     */     
/* 465 */     boolean mustReturnNull = FloatOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     FloatArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 473 */       return (this.c != 0L);
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 477 */       if (!hasNext())
/* 478 */         throw new NoSuchElementException(); 
/* 479 */       this.c--;
/* 480 */       if (this.mustReturnNull) {
/* 481 */         this.mustReturnNull = false;
/* 482 */         this.last = FloatOpenHashBigSet.this.n;
/* 483 */         return 0.0F;
/*     */       } 
/* 485 */       float[][] key = FloatOpenHashBigSet.this.key;
/*     */       while (true) {
/* 487 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 489 */           this.last = Long.MIN_VALUE;
/* 490 */           return this.wrapped.getFloat(---this.base - 1);
/*     */         } 
/* 492 */         if (this.displ-- == 0)
/* 493 */           this.displ = (key[--this.base]).length - 1; 
/* 494 */         float k = key[this.base][this.displ];
/* 495 */         if (Float.floatToIntBits(k) != 0) {
/* 496 */           this.last = this.base * 134217728L + this.displ;
/* 497 */           return k;
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
/* 512 */       float[][] key = FloatOpenHashBigSet.this.key; while (true) {
/*     */         float curr; long last;
/* 514 */         pos = (last = pos) + 1L & FloatOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 516 */           if (Float.floatToIntBits(curr = BigArrays.get(key, pos)) == 0) {
/* 517 */             BigArrays.set(key, last, 0.0F);
/*     */             
/*     */             return;
/*     */           } 
/* 521 */           long slot = HashCommon.mix(HashCommon.float2int(curr)) & FloatOpenHashBigSet.this.mask;
/* 522 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 524 */           pos = pos + 1L & FloatOpenHashBigSet.this.mask;
/*     */         } 
/* 526 */         if (pos < last) {
/* 527 */           if (this.wrapped == null)
/* 528 */             this.wrapped = new FloatArrayList(); 
/* 529 */           this.wrapped.add(BigArrays.get(key, pos));
/*     */         } 
/* 531 */         BigArrays.set(key, last, curr);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 536 */       if (this.last == -1L)
/* 537 */         throw new IllegalStateException(); 
/* 538 */       if (this.last == FloatOpenHashBigSet.this.n) {
/* 539 */         FloatOpenHashBigSet.this.containsNull = false;
/* 540 */       } else if (this.base >= 0) {
/* 541 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 544 */         FloatOpenHashBigSet.this.remove(this.wrapped.getFloat(-this.base - 1));
/* 545 */         this.last = -1L;
/*     */         return;
/*     */       } 
/* 548 */       FloatOpenHashBigSet.this.size--;
/* 549 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public FloatIterator iterator() {
/* 556 */     return new SetIterator();
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
/* 573 */     return trim(this.size);
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
/* 597 */     long l = HashCommon.bigArraySize(n, this.f);
/* 598 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 599 */       return true; 
/*     */     try {
/* 601 */       rehash(l);
/* 602 */     } catch (OutOfMemoryError cantDoIt) {
/* 603 */       return false;
/*     */     } 
/* 605 */     return true;
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
/* 621 */     float[][] key = this.key;
/* 622 */     float[][] newKey = FloatBigArrays.newBigArray(newN);
/* 623 */     long mask = newN - 1L;
/* 624 */     int newSegmentMask = (newKey[0]).length - 1;
/* 625 */     int newBaseMask = newKey.length - 1;
/* 626 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 629 */     for (long i = realSize(); i-- != 0L; ) {
/* 630 */       while (Float.floatToIntBits(key[base][displ]) == 0)
/* 631 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 632 */       float k = key[base][displ];
/* 633 */       long h = HashCommon.mix(HashCommon.float2int(k));
/*     */       int b, d;
/* 635 */       if (Float.floatToIntBits(newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)]) != 0)
/*     */         while (true)
/* 637 */         { if (Float.floatToIntBits(newKey[
/* 638 */                 b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d]) != 0)
/* 639 */             continue;  break; }   newKey[b][d] = k;
/* 640 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 642 */     this.n = newN;
/* 643 */     this.key = newKey;
/* 644 */     initMasks();
/* 645 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 650 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 654 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 658 */     return (this.size == 0L);
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
/*     */   public FloatOpenHashBigSet clone() {
/*     */     FloatOpenHashBigSet c;
/*     */     try {
/* 675 */       c = (FloatOpenHashBigSet)super.clone();
/* 676 */     } catch (CloneNotSupportedException cantHappen) {
/* 677 */       throw new InternalError();
/*     */     } 
/* 679 */     c.key = BigArrays.copy(this.key);
/* 680 */     c.containsNull = this.containsNull;
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
/* 694 */     float[][] key = this.key;
/* 695 */     int h = 0, base = 0, displ = 0;
/* 696 */     for (long j = realSize(); j-- != 0L; ) {
/* 697 */       while (Float.floatToIntBits(key[base][displ]) == 0)
/* 698 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 699 */       h += HashCommon.float2int(key[base][displ]);
/* 700 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 702 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 705 */     FloatIterator i = iterator();
/* 706 */     s.defaultWriteObject();
/* 707 */     for (long j = this.size; j-- != 0L;)
/* 708 */       s.writeFloat(i.nextFloat()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 712 */     s.defaultReadObject();
/* 713 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 714 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 715 */     float[][] key = this.key = FloatBigArrays.newBigArray(this.n);
/* 716 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 720 */     for (long i = this.size; i-- != 0L; ) {
/* 721 */       float k = s.readFloat();
/* 722 */       if (Float.floatToIntBits(k) == 0) {
/* 723 */         this.containsNull = true; continue;
/*     */       } 
/* 725 */       long h = HashCommon.mix(HashCommon.float2int(k)); int base, displ;
/* 726 */       if (Float.floatToIntBits(key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != 0)
/*     */         while (true) {
/* 728 */           if (Float.floatToIntBits(key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != 0)
/*     */             continue;  break;
/* 730 */         }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */