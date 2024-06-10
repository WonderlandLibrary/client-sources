/*     */ package it.unimi.dsi.fastutil.objects;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceOpenHashBigSet<K>
/*     */   extends AbstractReferenceSet<K>
/*     */   implements Serializable, Cloneable, Hash, Size64
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient K[][] key;
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
/*  91 */     this.mask = this.n - 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     this.segmentMask = (this.key[0]).length - 1;
/*  98 */     this.baseMask = this.key.length - 1;
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
/*     */   public ReferenceOpenHashBigSet(long expected, float f) {
/* 114 */     if (f <= 0.0F || f > 1.0F)
/* 115 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/* 116 */     if (this.n < 0L)
/* 117 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/* 118 */     this.f = f;
/* 119 */     this.minN = this.n = HashCommon.bigArraySize(expected, f);
/* 120 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 121 */     this.key = (K[][])ObjectBigArrays.newBigArray(this.n);
/* 122 */     initMasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(long expected) {
/* 132 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet() {
/* 140 */     this(16L, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(Collection<? extends K> c, float f) {
/* 151 */     this(c.size(), f);
/* 152 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(Collection<? extends K> c) {
/* 162 */     this(c, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(ReferenceCollection<? extends K> c, float f) {
/* 173 */     this(c.size(), f);
/* 174 */     addAll(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(ReferenceCollection<? extends K> c) {
/* 184 */     this(c, 0.75F);
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
/*     */   public ReferenceOpenHashBigSet(Iterator<? extends K> i, float f) {
/* 197 */     this(16L, f);
/* 198 */     while (i.hasNext()) {
/* 199 */       add(i.next());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(Iterator<? extends K> i) {
/* 210 */     this(i, 0.75F);
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
/*     */   public ReferenceOpenHashBigSet(K[] a, int offset, int length, float f) {
/* 225 */     this((length < 0) ? 0L : length, f);
/* 226 */     ObjectArrays.ensureOffsetLength(a, offset, length);
/* 227 */     for (int i = 0; i < length; i++) {
/* 228 */       add(a[offset + i]);
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
/*     */   public ReferenceOpenHashBigSet(K[] a, int offset, int length) {
/* 242 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(K[] a, float f) {
/* 253 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceOpenHashBigSet(K[] a) {
/* 263 */     this(a, 0.75F);
/*     */   }
/*     */   private long realSize() {
/* 266 */     return this.containsNull ? (this.size - 1L) : this.size;
/*     */   }
/*     */   private void ensureCapacity(long capacity) {
/* 269 */     long needed = HashCommon.bigArraySize(capacity, this.f);
/* 270 */     if (needed > this.n)
/* 271 */       rehash(needed); 
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends K> c) {
/* 275 */     long size = (c instanceof Size64) ? ((Size64)c).size64() : c.size();
/*     */     
/* 277 */     if (this.f <= 0.5D) {
/* 278 */       ensureCapacity(size);
/*     */     } else {
/* 280 */       ensureCapacity(size64() + size);
/* 281 */     }  return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(K k) {
/* 286 */     if (k == null) {
/* 287 */       if (this.containsNull)
/* 288 */         return false; 
/* 289 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 292 */       K[][] key = this.key;
/* 293 */       long h = HashCommon.mix(System.identityHashCode(k)); int displ, base;
/*     */       K curr;
/* 295 */       if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != null) {
/*     */         
/* 297 */         if (curr == k)
/* 298 */           return false;  while (true) {
/* 299 */           if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != null)
/*     */           
/* 301 */           { if (curr == k)
/* 302 */               return false;  continue; }  break;
/*     */         } 
/* 304 */       }  key[base][displ] = k;
/*     */     } 
/* 306 */     if (this.size++ >= this.maxFill) {
/* 307 */       rehash(2L * this.n);
/*     */     }
/*     */     
/* 310 */     return true;
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
/* 322 */     K[][] key = this.key; while (true) {
/*     */       long last;
/* 324 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 326 */         if (BigArrays.get((Object[][])key, pos) == null) {
/* 327 */           BigArrays.set((Object[][])key, last, null);
/*     */           return;
/*     */         } 
/* 330 */         long slot = HashCommon.mix(System.identityHashCode(BigArrays.get((Object[][])key, pos))) & this.mask;
/*     */         
/* 332 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 334 */         pos = pos + 1L & this.mask;
/*     */       } 
/* 336 */       BigArrays.set((Object[][])key, last, BigArrays.get((Object[][])key, pos));
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int base, int displ) {
/* 340 */     this.size--;
/* 341 */     shiftKeys(base * 134217728L + displ);
/* 342 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 343 */       rehash(this.n / 2L); 
/* 344 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 347 */     this.containsNull = false;
/* 348 */     this.size--;
/* 349 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 350 */       rehash(this.n / 2L); 
/* 351 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(Object k) {
/* 355 */     if (k == null) {
/* 356 */       if (this.containsNull)
/* 357 */         return removeNullEntry(); 
/* 358 */       return false;
/*     */     } 
/*     */     
/* 361 */     K[][] key = this.key;
/* 362 */     long h = HashCommon.mix(System.identityHashCode(k));
/*     */     K curr;
/*     */     int displ, base;
/* 365 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null)
/*     */     {
/* 367 */       return false; } 
/* 368 */     if (curr == k)
/* 369 */       return removeEntry(base, displ); 
/*     */     while (true) {
/* 371 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null)
/*     */       {
/* 373 */         return false; } 
/* 374 */       if (curr == k)
/* 375 */         return removeEntry(base, displ); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(Object k) {
/* 380 */     if (k == null) {
/* 381 */       return this.containsNull;
/*     */     }
/* 383 */     K[][] key = this.key;
/* 384 */     long h = HashCommon.mix(System.identityHashCode(k));
/*     */     K curr;
/*     */     int displ, base;
/* 387 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null)
/*     */     {
/* 389 */       return false; } 
/* 390 */     if (curr == k)
/* 391 */       return true; 
/*     */     while (true) {
/* 393 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null)
/*     */       {
/* 395 */         return false; } 
/* 396 */       if (curr == k) {
/* 397 */         return true;
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
/* 413 */     if (this.size == 0L)
/*     */       return; 
/* 415 */     this.size = 0L;
/* 416 */     this.containsNull = false;
/* 417 */     BigArrays.fill((Object[][])this.key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 426 */     int base = ReferenceOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 434 */     long last = -1L;
/*     */     
/* 436 */     long c = ReferenceOpenHashBigSet.this.size;
/*     */     
/* 438 */     boolean mustReturnNull = ReferenceOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     ReferenceArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 446 */       return (this.c != 0L);
/*     */     }
/*     */     
/*     */     public K next() {
/* 450 */       if (!hasNext())
/* 451 */         throw new NoSuchElementException(); 
/* 452 */       this.c--;
/* 453 */       if (this.mustReturnNull) {
/* 454 */         this.mustReturnNull = false;
/* 455 */         this.last = ReferenceOpenHashBigSet.this.n;
/* 456 */         return null;
/*     */       } 
/* 458 */       K[][] key = ReferenceOpenHashBigSet.this.key;
/*     */       while (true) {
/* 460 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 462 */           this.last = Long.MIN_VALUE;
/* 463 */           return this.wrapped.get(---this.base - 1);
/*     */         } 
/* 465 */         if (this.displ-- == 0)
/* 466 */           this.displ = (key[--this.base]).length - 1; 
/* 467 */         K k = key[this.base][this.displ];
/* 468 */         if (k != null) {
/* 469 */           this.last = this.base * 134217728L + this.displ;
/* 470 */           return k;
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
/* 485 */       K[][] key = ReferenceOpenHashBigSet.this.key; while (true) {
/*     */         K curr; long last;
/* 487 */         pos = (last = pos) + 1L & ReferenceOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 489 */           if ((curr = (K)BigArrays.get((Object[][])key, pos)) == null) {
/* 490 */             BigArrays.set((Object[][])key, last, null);
/*     */             return;
/*     */           } 
/* 493 */           long slot = HashCommon.mix(System.identityHashCode(curr)) & ReferenceOpenHashBigSet.this.mask;
/* 494 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 496 */           pos = pos + 1L & ReferenceOpenHashBigSet.this.mask;
/*     */         } 
/* 498 */         if (pos < last) {
/* 499 */           if (this.wrapped == null)
/* 500 */             this.wrapped = new ReferenceArrayList<>(); 
/* 501 */           this.wrapped.add((K)BigArrays.get((Object[][])key, pos));
/*     */         } 
/* 503 */         BigArrays.set((Object[][])key, last, curr);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 508 */       if (this.last == -1L)
/* 509 */         throw new IllegalStateException(); 
/* 510 */       if (this.last == ReferenceOpenHashBigSet.this.n) {
/* 511 */         ReferenceOpenHashBigSet.this.containsNull = false;
/* 512 */       } else if (this.base >= 0) {
/* 513 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 516 */         ReferenceOpenHashBigSet.this.remove(this.wrapped.set(-this.base - 1, null));
/* 517 */         this.last = -1L;
/*     */         return;
/*     */       } 
/* 520 */       ReferenceOpenHashBigSet.this.size--;
/* 521 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 528 */     return new SetIterator();
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
/* 545 */     return trim(this.size);
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
/* 569 */     long l = HashCommon.bigArraySize(n, this.f);
/* 570 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 571 */       return true; 
/*     */     try {
/* 573 */       rehash(l);
/* 574 */     } catch (OutOfMemoryError cantDoIt) {
/* 575 */       return false;
/*     */     } 
/* 577 */     return true;
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
/* 593 */     K[][] key = this.key;
/* 594 */     K[][] newKey = (K[][])ObjectBigArrays.newBigArray(newN);
/* 595 */     long mask = newN - 1L;
/* 596 */     int newSegmentMask = (newKey[0]).length - 1;
/* 597 */     int newBaseMask = newKey.length - 1;
/* 598 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 601 */     for (long i = realSize(); i-- != 0L; ) {
/* 602 */       while (key[base][displ] == null)
/* 603 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 604 */       K k = key[base][displ];
/* 605 */       long h = HashCommon.mix(System.identityHashCode(k));
/*     */       int b, d;
/* 607 */       if (newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)] != null)
/*     */         while (true)
/* 609 */         { if (newKey[b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d] != null)
/* 610 */             continue;  break; }   newKey[b][d] = k;
/* 611 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 613 */     this.n = newN;
/* 614 */     this.key = newKey;
/* 615 */     initMasks();
/* 616 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 621 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 625 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 629 */     return (this.size == 0L);
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
/*     */   public ReferenceOpenHashBigSet<K> clone() {
/*     */     ReferenceOpenHashBigSet<K> c;
/*     */     try {
/* 646 */       c = (ReferenceOpenHashBigSet<K>)super.clone();
/* 647 */     } catch (CloneNotSupportedException cantHappen) {
/* 648 */       throw new InternalError();
/*     */     } 
/* 650 */     c.key = (K[][])BigArrays.copy((Object[][])this.key);
/* 651 */     c.containsNull = this.containsNull;
/* 652 */     return c;
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
/* 665 */     K[][] key = this.key;
/* 666 */     int h = 0, base = 0, displ = 0;
/* 667 */     for (long j = realSize(); j-- != 0L; ) {
/* 668 */       while (key[base][displ] == null)
/* 669 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 670 */       if (this != key[base][displ])
/* 671 */         h += System.identityHashCode(key[base][displ]); 
/* 672 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 674 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 677 */     ObjectIterator<K> i = iterator();
/* 678 */     s.defaultWriteObject();
/* 679 */     for (long j = this.size; j-- != 0L;)
/* 680 */       s.writeObject(i.next()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 684 */     s.defaultReadObject();
/* 685 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 686 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 687 */     K[][] key = this.key = (K[][])ObjectBigArrays.newBigArray(this.n);
/* 688 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 692 */     for (long i = this.size; i-- != 0L; ) {
/* 693 */       K k = (K)s.readObject();
/* 694 */       if (k == null) {
/* 695 */         this.containsNull = true; continue;
/*     */       } 
/* 697 */       long h = HashCommon.mix(System.identityHashCode(k)); int base, displ;
/* 698 */       if (key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)] != null)
/*     */         while (true) {
/* 700 */           if (key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ] != null)
/*     */             continue;  break;
/* 702 */         }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ReferenceOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */