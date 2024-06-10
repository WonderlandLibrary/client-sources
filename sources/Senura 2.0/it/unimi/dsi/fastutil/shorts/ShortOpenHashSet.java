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
/*     */ public class ShortOpenHashSet
/*     */   extends AbstractShortSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient short[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public ShortOpenHashSet(int expected, float f) {
/*  86 */     if (f <= 0.0F || f > 1.0F)
/*  87 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  88 */     if (expected < 0)
/*  89 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  90 */     this.f = f;
/*  91 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  92 */     this.mask = this.n - 1;
/*  93 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  94 */     this.key = new short[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenHashSet(int expected) {
/* 103 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenHashSet() {
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
/*     */   public ShortOpenHashSet(Collection<? extends Short> c, float f) {
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
/*     */   public ShortOpenHashSet(Collection<? extends Short> c) {
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
/*     */   public ShortOpenHashSet(ShortCollection c, float f) {
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
/*     */   public ShortOpenHashSet(ShortCollection c) {
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
/*     */   public ShortOpenHashSet(ShortIterator i, float f) {
/* 166 */     this(16, f);
/* 167 */     while (i.hasNext()) {
/* 168 */       add(i.nextShort());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenHashSet(ShortIterator i) {
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
/*     */   public ShortOpenHashSet(Iterator<?> i, float f) {
/* 189 */     this(ShortIterators.asShortIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenHashSet(Iterator<?> i) {
/* 199 */     this(ShortIterators.asShortIterator(i));
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
/*     */   public ShortOpenHashSet(short[] a, int offset, int length, float f) {
/* 214 */     this((length < 0) ? 0 : length, f);
/* 215 */     ShortArrays.ensureOffsetLength(a, offset, length);
/* 216 */     for (int i = 0; i < length; i++) {
/* 217 */       add(a[offset + i]);
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
/*     */   public ShortOpenHashSet(short[] a, int offset, int length) {
/* 231 */     this(a, offset, length, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenHashSet(short[] a, float f) {
/* 242 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShortOpenHashSet(short[] a) {
/* 252 */     this(a, 0.75F);
/*     */   }
/*     */   private int realSize() {
/* 255 */     return this.containsNull ? (this.size - 1) : this.size;
/*     */   }
/*     */   private void ensureCapacity(int capacity) {
/* 258 */     int needed = HashCommon.arraySize(capacity, this.f);
/* 259 */     if (needed > this.n)
/* 260 */       rehash(needed); 
/*     */   }
/*     */   private void tryCapacity(long capacity) {
/* 263 */     int needed = (int)Math.min(1073741824L, 
/* 264 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/* 265 */     if (needed > this.n)
/* 266 */       rehash(needed); 
/*     */   }
/*     */   
/*     */   public boolean addAll(ShortCollection c) {
/* 270 */     if (this.f <= 0.5D) {
/* 271 */       ensureCapacity(c.size());
/*     */     } else {
/* 273 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 275 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Short> c) {
/* 280 */     if (this.f <= 0.5D) {
/* 281 */       ensureCapacity(c.size());
/*     */     } else {
/* 283 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 285 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(short k) {
/* 290 */     if (k == 0) {
/* 291 */       if (this.containsNull)
/* 292 */         return false; 
/* 293 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 296 */       short[] key = this.key; int pos;
/*     */       short curr;
/* 298 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/* 299 */         if (curr == k)
/* 300 */           return false; 
/* 301 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/* 302 */           if (curr == k)
/* 303 */             return false; 
/*     */         } 
/* 305 */       }  key[pos] = k;
/*     */     } 
/* 307 */     if (this.size++ >= this.maxFill) {
/* 308 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 311 */     return true;
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
/* 324 */     short[] key = this.key; while (true) {
/*     */       short curr; int last;
/* 326 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 328 */         if ((curr = key[pos]) == 0) {
/* 329 */           key[last] = 0;
/*     */           return;
/*     */         } 
/* 332 */         int slot = HashCommon.mix(curr) & this.mask;
/* 333 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 335 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 337 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int pos) {
/* 341 */     this.size--;
/* 342 */     shiftKeys(pos);
/* 343 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 344 */       rehash(this.n / 2); 
/* 345 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 348 */     this.containsNull = false;
/* 349 */     this.key[this.n] = 0;
/* 350 */     this.size--;
/* 351 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 352 */       rehash(this.n / 2); 
/* 353 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(short k) {
/* 358 */     if (k == 0) {
/* 359 */       if (this.containsNull)
/* 360 */         return removeNullEntry(); 
/* 361 */       return false;
/*     */     } 
/*     */     
/* 364 */     short[] key = this.key;
/*     */     short curr;
/*     */     int pos;
/* 367 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/* 368 */       return false; 
/* 369 */     if (k == curr)
/* 370 */       return removeEntry(pos); 
/*     */     while (true) {
/* 372 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 373 */         return false; 
/* 374 */       if (k == curr) {
/* 375 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(short k) {
/* 381 */     if (k == 0) {
/* 382 */       return this.containsNull;
/*     */     }
/* 384 */     short[] key = this.key;
/*     */     short curr;
/*     */     int pos;
/* 387 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/* 388 */       return false; 
/* 389 */     if (k == curr)
/* 390 */       return true; 
/*     */     while (true) {
/* 392 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/* 393 */         return false; 
/* 394 */       if (k == curr) {
/* 395 */         return true;
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
/* 407 */     if (this.size == 0)
/*     */       return; 
/* 409 */     this.size = 0;
/* 410 */     this.containsNull = false;
/* 411 */     Arrays.fill(this.key, (short)0);
/*     */   }
/*     */   
/*     */   public int size() {
/* 415 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 419 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements ShortIterator
/*     */   {
/* 428 */     int pos = ShortOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 435 */     int last = -1;
/*     */     
/* 437 */     int c = ShortOpenHashSet.this.size;
/*     */     
/* 439 */     boolean mustReturnNull = ShortOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     ShortArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 447 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public short nextShort() {
/* 451 */       if (!hasNext())
/* 452 */         throw new NoSuchElementException(); 
/* 453 */       this.c--;
/* 454 */       if (this.mustReturnNull) {
/* 455 */         this.mustReturnNull = false;
/* 456 */         this.last = ShortOpenHashSet.this.n;
/* 457 */         return ShortOpenHashSet.this.key[ShortOpenHashSet.this.n];
/*     */       } 
/* 459 */       short[] key = ShortOpenHashSet.this.key;
/*     */       while (true) {
/* 461 */         if (--this.pos < 0) {
/*     */           
/* 463 */           this.last = Integer.MIN_VALUE;
/* 464 */           return this.wrapped.getShort(-this.pos - 1);
/*     */         } 
/* 466 */         if (key[this.pos] != 0) {
/* 467 */           return key[this.last = this.pos];
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
/* 481 */       short[] key = ShortOpenHashSet.this.key; while (true) {
/*     */         short curr; int last;
/* 483 */         pos = (last = pos) + 1 & ShortOpenHashSet.this.mask;
/*     */         while (true) {
/* 485 */           if ((curr = key[pos]) == 0) {
/* 486 */             key[last] = 0;
/*     */             return;
/*     */           } 
/* 489 */           int slot = HashCommon.mix(curr) & ShortOpenHashSet.this.mask;
/* 490 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 492 */           pos = pos + 1 & ShortOpenHashSet.this.mask;
/*     */         } 
/* 494 */         if (pos < last) {
/* 495 */           if (this.wrapped == null)
/* 496 */             this.wrapped = new ShortArrayList(2); 
/* 497 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 499 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 504 */       if (this.last == -1)
/* 505 */         throw new IllegalStateException(); 
/* 506 */       if (this.last == ShortOpenHashSet.this.n) {
/* 507 */         ShortOpenHashSet.this.containsNull = false;
/* 508 */         ShortOpenHashSet.this.key[ShortOpenHashSet.this.n] = 0;
/* 509 */       } else if (this.pos >= 0) {
/* 510 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 513 */         ShortOpenHashSet.this.remove(this.wrapped.getShort(-this.pos - 1));
/* 514 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 517 */       ShortOpenHashSet.this.size--;
/* 518 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public ShortIterator iterator() {
/* 525 */     return new SetIterator();
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
/* 542 */     return trim(this.size);
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
/* 566 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 567 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 568 */       return true; 
/*     */     try {
/* 570 */       rehash(l);
/* 571 */     } catch (OutOfMemoryError cantDoIt) {
/* 572 */       return false;
/*     */     } 
/* 574 */     return true;
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
/* 590 */     short[] key = this.key;
/* 591 */     int mask = newN - 1;
/* 592 */     short[] newKey = new short[newN + 1];
/* 593 */     int i = this.n;
/* 594 */     for (int j = realSize(); j-- != 0; ) {
/* 595 */       while (key[--i] == 0); int pos;
/* 596 */       if (newKey[pos = HashCommon.mix(key[i]) & mask] != 0)
/* 597 */         while (newKey[pos = pos + 1 & mask] != 0); 
/* 598 */       newKey[pos] = key[i];
/*     */     } 
/* 600 */     this.n = newN;
/* 601 */     this.mask = mask;
/* 602 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 603 */     this.key = newKey;
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
/*     */   public ShortOpenHashSet clone() {
/*     */     ShortOpenHashSet c;
/*     */     try {
/* 620 */       c = (ShortOpenHashSet)super.clone();
/* 621 */     } catch (CloneNotSupportedException cantHappen) {
/* 622 */       throw new InternalError();
/*     */     } 
/* 624 */     c.key = (short[])this.key.clone();
/* 625 */     c.containsNull = this.containsNull;
/* 626 */     return c;
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
/* 639 */     int h = 0;
/* 640 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 641 */       while (this.key[i] == 0)
/* 642 */         i++; 
/* 643 */       h += this.key[i];
/* 644 */       i++;
/*     */     } 
/*     */     
/* 647 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 650 */     ShortIterator i = iterator();
/* 651 */     s.defaultWriteObject();
/* 652 */     for (int j = this.size; j-- != 0;)
/* 653 */       s.writeShort(i.nextShort()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 657 */     s.defaultReadObject();
/* 658 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 659 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 660 */     this.mask = this.n - 1;
/* 661 */     short[] key = this.key = new short[this.n + 1];
/*     */     
/* 663 */     for (int i = this.size; i-- != 0; ) {
/* 664 */       int pos; short k = s.readShort();
/* 665 */       if (k == 0) {
/* 666 */         pos = this.n;
/* 667 */         this.containsNull = true;
/*     */       }
/* 669 */       else if (key[pos = HashCommon.mix(k) & this.mask] != 0) {
/* 670 */         while (key[pos = pos + 1 & this.mask] != 0);
/*     */       } 
/* 672 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */