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
/*     */ public class ObjectOpenHashBigSet<K>
/*     */   extends AbstractObjectSet<K>
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
/*     */   public ObjectOpenHashBigSet(long expected, float f) {
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
/*     */   public ObjectOpenHashBigSet(long expected) {
/* 132 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet() {
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
/*     */   public ObjectOpenHashBigSet(Collection<? extends K> c, float f) {
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
/*     */   public ObjectOpenHashBigSet(Collection<? extends K> c) {
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
/*     */   public ObjectOpenHashBigSet(ObjectCollection<? extends K> c, float f) {
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
/*     */   public ObjectOpenHashBigSet(ObjectCollection<? extends K> c) {
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
/*     */   public ObjectOpenHashBigSet(Iterator<? extends K> i, float f) {
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
/*     */   public ObjectOpenHashBigSet(Iterator<? extends K> i) {
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
/*     */   public ObjectOpenHashBigSet(K[] a, int offset, int length, float f) {
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
/*     */   public ObjectOpenHashBigSet(K[] a, int offset, int length) {
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
/*     */   public ObjectOpenHashBigSet(K[] a, float f) {
/* 253 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectOpenHashBigSet(K[] a) {
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
/* 293 */       long h = HashCommon.mix(k.hashCode()); int displ, base;
/*     */       K curr;
/* 295 */       if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != null) {
/*     */         
/* 297 */         if (curr.equals(k))
/* 298 */           return false;  while (true) {
/* 299 */           if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != null)
/*     */           
/* 301 */           { if (curr.equals(k))
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K addOrGet(K k) {
/* 328 */     if (k == null) {
/* 329 */       if (this.containsNull)
/* 330 */         return null; 
/* 331 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 334 */       K[][] key = this.key;
/* 335 */       long h = HashCommon.mix(k.hashCode()); int displ, base;
/*     */       K curr;
/* 337 */       if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) != null) {
/*     */         
/* 339 */         if (curr.equals(k))
/* 340 */           return curr;  while (true) {
/* 341 */           if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) != null)
/*     */           
/* 343 */           { if (curr.equals(k))
/* 344 */               return curr;  continue; }  break;
/*     */         } 
/* 346 */       }  key[base][displ] = k;
/*     */     } 
/* 348 */     if (this.size++ >= this.maxFill) {
/* 349 */       rehash(2L * this.n);
/*     */     }
/*     */     
/* 352 */     return k;
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
/* 364 */     K[][] key = this.key; while (true) {
/*     */       long last;
/* 366 */       pos = (last = pos) + 1L & this.mask;
/*     */       while (true) {
/* 368 */         if (BigArrays.get((Object[][])key, pos) == null) {
/* 369 */           BigArrays.set((Object[][])key, last, null);
/*     */           return;
/*     */         } 
/* 372 */         long slot = HashCommon.mix(BigArrays.get((Object[][])key, pos).hashCode()) & this.mask;
/* 373 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 375 */         pos = pos + 1L & this.mask;
/*     */       } 
/* 377 */       BigArrays.set((Object[][])key, last, BigArrays.get((Object[][])key, pos));
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int base, int displ) {
/* 381 */     this.size--;
/* 382 */     shiftKeys(base * 134217728L + displ);
/* 383 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 384 */       rehash(this.n / 2L); 
/* 385 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 388 */     this.containsNull = false;
/* 389 */     this.size--;
/* 390 */     if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L)
/* 391 */       rehash(this.n / 2L); 
/* 392 */     return true;
/*     */   }
/*     */   
/*     */   public boolean remove(Object k) {
/* 396 */     if (k == null) {
/* 397 */       if (this.containsNull)
/* 398 */         return removeNullEntry(); 
/* 399 */       return false;
/*     */     } 
/*     */     
/* 402 */     K[][] key = this.key;
/* 403 */     long h = HashCommon.mix(k.hashCode());
/*     */     K curr;
/*     */     int displ, base;
/* 406 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null)
/*     */     {
/* 408 */       return false; } 
/* 409 */     if (curr.equals(k))
/* 410 */       return removeEntry(base, displ); 
/*     */     while (true) {
/* 412 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null)
/*     */       {
/* 414 */         return false; } 
/* 415 */       if (curr.equals(k))
/* 416 */         return removeEntry(base, displ); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(Object k) {
/* 421 */     if (k == null) {
/* 422 */       return this.containsNull;
/*     */     }
/* 424 */     K[][] key = this.key;
/* 425 */     long h = HashCommon.mix(k.hashCode());
/*     */     K curr;
/*     */     int displ, base;
/* 428 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null)
/*     */     {
/* 430 */       return false; } 
/* 431 */     if (curr.equals(k))
/* 432 */       return true; 
/*     */     while (true) {
/* 434 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null)
/*     */       {
/* 436 */         return false; } 
/* 437 */       if (curr.equals(k)) {
/* 438 */         return true;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public K get(Object k) {
/* 449 */     if (k == null) {
/* 450 */       return null;
/*     */     }
/* 452 */     K[][] key = this.key;
/* 453 */     long h = HashCommon.mix(k.hashCode());
/*     */     K curr;
/*     */     int displ, base;
/* 456 */     if ((curr = key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)]) == null)
/*     */     {
/* 458 */       return null; } 
/* 459 */     if (curr.equals(k))
/* 460 */       return curr; 
/*     */     while (true) {
/* 462 */       if ((curr = key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ]) == null)
/*     */       {
/* 464 */         return null; } 
/* 465 */       if (curr.equals(k)) {
/* 466 */         return curr;
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
/* 482 */     if (this.size == 0L)
/*     */       return; 
/* 484 */     this.size = 0L;
/* 485 */     this.containsNull = false;
/* 486 */     BigArrays.fill((Object[][])this.key, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ObjectIterator<K>
/*     */   {
/* 495 */     int base = ObjectOpenHashBigSet.this.key.length;
/*     */ 
/*     */ 
/*     */     
/*     */     int displ;
/*     */ 
/*     */ 
/*     */     
/* 503 */     long last = -1L;
/*     */     
/* 505 */     long c = ObjectOpenHashBigSet.this.size;
/*     */     
/* 507 */     boolean mustReturnNull = ObjectOpenHashBigSet.this.containsNull;
/*     */ 
/*     */     
/*     */     ObjectArrayList<K> wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 515 */       return (this.c != 0L);
/*     */     }
/*     */     
/*     */     public K next() {
/* 519 */       if (!hasNext())
/* 520 */         throw new NoSuchElementException(); 
/* 521 */       this.c--;
/* 522 */       if (this.mustReturnNull) {
/* 523 */         this.mustReturnNull = false;
/* 524 */         this.last = ObjectOpenHashBigSet.this.n;
/* 525 */         return null;
/*     */       } 
/* 527 */       K[][] key = ObjectOpenHashBigSet.this.key;
/*     */       while (true) {
/* 529 */         if (this.displ == 0 && this.base <= 0) {
/*     */           
/* 531 */           this.last = Long.MIN_VALUE;
/* 532 */           return this.wrapped.get(---this.base - 1);
/*     */         } 
/* 534 */         if (this.displ-- == 0)
/* 535 */           this.displ = (key[--this.base]).length - 1; 
/* 536 */         K k = key[this.base][this.displ];
/* 537 */         if (k != null) {
/* 538 */           this.last = this.base * 134217728L + this.displ;
/* 539 */           return k;
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
/* 554 */       K[][] key = ObjectOpenHashBigSet.this.key; while (true) {
/*     */         K curr; long last;
/* 556 */         pos = (last = pos) + 1L & ObjectOpenHashBigSet.this.mask;
/*     */         while (true) {
/* 558 */           if ((curr = (K)BigArrays.get((Object[][])key, pos)) == null) {
/* 559 */             BigArrays.set((Object[][])key, last, null);
/*     */             return;
/*     */           } 
/* 562 */           long slot = HashCommon.mix(curr.hashCode()) & ObjectOpenHashBigSet.this.mask;
/* 563 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 565 */           pos = pos + 1L & ObjectOpenHashBigSet.this.mask;
/*     */         } 
/* 567 */         if (pos < last) {
/* 568 */           if (this.wrapped == null)
/* 569 */             this.wrapped = new ObjectArrayList<>(); 
/* 570 */           this.wrapped.add((K)BigArrays.get((Object[][])key, pos));
/*     */         } 
/* 572 */         BigArrays.set((Object[][])key, last, curr);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 577 */       if (this.last == -1L)
/* 578 */         throw new IllegalStateException(); 
/* 579 */       if (this.last == ObjectOpenHashBigSet.this.n) {
/* 580 */         ObjectOpenHashBigSet.this.containsNull = false;
/* 581 */       } else if (this.base >= 0) {
/* 582 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 585 */         ObjectOpenHashBigSet.this.remove(this.wrapped.set(-this.base - 1, null));
/* 586 */         this.last = -1L;
/*     */         return;
/*     */       } 
/* 589 */       ObjectOpenHashBigSet.this.size--;
/* 590 */       this.last = -1L;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ObjectIterator<K> iterator() {
/* 597 */     return new SetIterator();
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
/* 614 */     return trim(this.size);
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
/* 638 */     long l = HashCommon.bigArraySize(n, this.f);
/* 639 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 640 */       return true; 
/*     */     try {
/* 642 */       rehash(l);
/* 643 */     } catch (OutOfMemoryError cantDoIt) {
/* 644 */       return false;
/*     */     } 
/* 646 */     return true;
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
/* 662 */     K[][] key = this.key;
/* 663 */     K[][] newKey = (K[][])ObjectBigArrays.newBigArray(newN);
/* 664 */     long mask = newN - 1L;
/* 665 */     int newSegmentMask = (newKey[0]).length - 1;
/* 666 */     int newBaseMask = newKey.length - 1;
/* 667 */     int base = 0, displ = 0;
/*     */ 
/*     */     
/* 670 */     for (long i = realSize(); i-- != 0L; ) {
/* 671 */       while (key[base][displ] == null)
/* 672 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 673 */       K k = key[base][displ];
/* 674 */       long h = HashCommon.mix(k.hashCode());
/*     */       int b, d;
/* 676 */       if (newKey[b = (int)((h & mask) >>> 27L)][d = (int)(h & newSegmentMask)] != null)
/* 677 */         while (true) { if (newKey[b = b + (((d = d + 1 & newSegmentMask) == 0) ? 1 : 0) & newBaseMask][d] != null)
/* 678 */             continue;  break; }   newKey[b][d] = k;
/* 679 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 681 */     this.n = newN;
/* 682 */     this.key = newKey;
/* 683 */     initMasks();
/* 684 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int size() {
/* 689 */     return (int)Math.min(2147483647L, this.size);
/*     */   }
/*     */   
/*     */   public long size64() {
/* 693 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 697 */     return (this.size == 0L);
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
/*     */   public ObjectOpenHashBigSet<K> clone() {
/*     */     ObjectOpenHashBigSet<K> c;
/*     */     try {
/* 714 */       c = (ObjectOpenHashBigSet<K>)super.clone();
/* 715 */     } catch (CloneNotSupportedException cantHappen) {
/* 716 */       throw new InternalError();
/*     */     } 
/* 718 */     c.key = (K[][])BigArrays.copy((Object[][])this.key);
/* 719 */     c.containsNull = this.containsNull;
/* 720 */     return c;
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
/* 733 */     K[][] key = this.key;
/* 734 */     int h = 0, base = 0, displ = 0;
/* 735 */     for (long j = realSize(); j-- != 0L; ) {
/* 736 */       while (key[base][displ] == null)
/* 737 */         base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0; 
/* 738 */       if (this != key[base][displ])
/* 739 */         h += key[base][displ].hashCode(); 
/* 740 */       base += ((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0;
/*     */     } 
/* 742 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 745 */     ObjectIterator<K> i = iterator();
/* 746 */     s.defaultWriteObject();
/* 747 */     for (long j = this.size; j-- != 0L;)
/* 748 */       s.writeObject(i.next()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 752 */     s.defaultReadObject();
/* 753 */     this.n = HashCommon.bigArraySize(this.size, this.f);
/* 754 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 755 */     K[][] key = this.key = (K[][])ObjectBigArrays.newBigArray(this.n);
/* 756 */     initMasks();
/*     */ 
/*     */ 
/*     */     
/* 760 */     for (long i = this.size; i-- != 0L; ) {
/* 761 */       K k = (K)s.readObject();
/* 762 */       if (k == null) {
/* 763 */         this.containsNull = true; continue;
/*     */       } 
/* 765 */       long h = HashCommon.mix(k.hashCode()); int base, displ;
/* 766 */       if (key[base = (int)((h & this.mask) >>> 27L)][displ = (int)(h & this.segmentMask)] != null)
/*     */         while (true) {
/* 768 */           if (key[base = base + (((displ = displ + 1 & this.segmentMask) == 0) ? 1 : 0) & this.baseMask][displ] != null)
/*     */             continue;  break;
/* 770 */         }   key[base][displ] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\ObjectOpenHashBigSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */