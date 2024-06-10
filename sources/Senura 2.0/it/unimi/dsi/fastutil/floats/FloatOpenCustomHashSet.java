/*     */ package it.unimi.dsi.fastutil.floats;
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
/*     */ public class FloatOpenCustomHashSet
/*     */   extends AbstractFloatSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient float[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected FloatHash.Strategy strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public FloatOpenCustomHashSet(int expected, float f, FloatHash.Strategy strategy) {
/*  93 */     this.strategy = strategy;
/*  94 */     if (f <= 0.0F || f > 1.0F)
/*  95 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  96 */     if (expected < 0)
/*  97 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  98 */     this.f = f;
/*  99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/* 100 */     this.mask = this.n - 1;
/* 101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 102 */     this.key = new float[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenCustomHashSet(int expected, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(Collection<? extends Float> c, float f, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(Collection<? extends Float> c, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(FloatCollection c, float f, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(FloatCollection c, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(FloatIterator i, float f, FloatHash.Strategy strategy) {
/* 194 */     this(16, f, strategy);
/* 195 */     while (i.hasNext()) {
/* 196 */       add(i.nextFloat());
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
/*     */   public FloatOpenCustomHashSet(FloatIterator i, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(Iterator<?> i, float f, FloatHash.Strategy strategy) {
/* 223 */     this(FloatIterators.asFloatIterator(i), f, strategy);
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
/*     */   public FloatOpenCustomHashSet(Iterator<?> i, FloatHash.Strategy strategy) {
/* 235 */     this(FloatIterators.asFloatIterator(i), strategy);
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
/*     */   public FloatOpenCustomHashSet(float[] a, int offset, int length, float f, FloatHash.Strategy strategy) {
/* 253 */     this((length < 0) ? 0 : length, f, strategy);
/* 254 */     FloatArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public FloatOpenCustomHashSet(float[] a, int offset, int length, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(float[] a, float f, FloatHash.Strategy strategy) {
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
/*     */   public FloatOpenCustomHashSet(float[] a, FloatHash.Strategy strategy) {
/* 299 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatHash.Strategy strategy() {
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
/*     */   public boolean addAll(FloatCollection c) {
/* 325 */     if (this.f <= 0.5D) {
/* 326 */       ensureCapacity(c.size());
/*     */     } else {
/* 328 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 330 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Float> c) {
/* 335 */     if (this.f <= 0.5D) {
/* 336 */       ensureCapacity(c.size());
/*     */     } else {
/* 338 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 340 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(float k) {
/* 345 */     if (this.strategy.equals(k, 0.0F)) {
/* 346 */       if (this.containsNull)
/* 347 */         return false; 
/* 348 */       this.containsNull = true;
/* 349 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 352 */       float[] key = this.key; int pos;
/*     */       float curr;
/* 354 */       if (Float.floatToIntBits(
/* 355 */           curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/* 356 */         if (this.strategy.equals(curr, k))
/* 357 */           return false; 
/* 358 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/* 381 */     float[] key = this.key; while (true) {
/*     */       float curr; int last;
/* 383 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 385 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 386 */           key[last] = 0.0F;
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
/* 406 */     this.key[this.n] = 0.0F;
/* 407 */     this.size--;
/* 408 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 409 */       rehash(this.n / 2); 
/* 410 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(float k) {
/* 415 */     if (this.strategy.equals(k, 0.0F)) {
/* 416 */       if (this.containsNull)
/* 417 */         return removeNullEntry(); 
/* 418 */       return false;
/*     */     } 
/*     */     
/* 421 */     float[] key = this.key;
/*     */     float curr;
/*     */     int pos;
/* 424 */     if (Float.floatToIntBits(
/* 425 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 426 */       return false; 
/* 427 */     if (this.strategy.equals(k, curr))
/* 428 */       return removeEntry(pos); 
/*     */     while (true) {
/* 430 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/* 431 */         return false; 
/* 432 */       if (this.strategy.equals(k, curr)) {
/* 433 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(float k) {
/* 439 */     if (this.strategy.equals(k, 0.0F)) {
/* 440 */       return this.containsNull;
/*     */     }
/* 442 */     float[] key = this.key;
/*     */     float curr;
/*     */     int pos;
/* 445 */     if (Float.floatToIntBits(
/* 446 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/* 447 */       return false; 
/* 448 */     if (this.strategy.equals(k, curr))
/* 449 */       return true; 
/*     */     while (true) {
/* 451 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/* 452 */         return false; 
/* 453 */       if (this.strategy.equals(k, curr)) {
/* 454 */         return true;
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
/* 466 */     if (this.size == 0)
/*     */       return; 
/* 468 */     this.size = 0;
/* 469 */     this.containsNull = false;
/* 470 */     Arrays.fill(this.key, 0.0F);
/*     */   }
/*     */   
/*     */   public int size() {
/* 474 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 478 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements FloatIterator
/*     */   {
/* 487 */     int pos = FloatOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     int last = -1;
/*     */     
/* 496 */     int c = FloatOpenCustomHashSet.this.size;
/*     */     
/* 498 */     boolean mustReturnNull = FloatOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     FloatArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 506 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 510 */       if (!hasNext())
/* 511 */         throw new NoSuchElementException(); 
/* 512 */       this.c--;
/* 513 */       if (this.mustReturnNull) {
/* 514 */         this.mustReturnNull = false;
/* 515 */         this.last = FloatOpenCustomHashSet.this.n;
/* 516 */         return FloatOpenCustomHashSet.this.key[FloatOpenCustomHashSet.this.n];
/*     */       } 
/* 518 */       float[] key = FloatOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 520 */         if (--this.pos < 0) {
/*     */           
/* 522 */           this.last = Integer.MIN_VALUE;
/* 523 */           return this.wrapped.getFloat(-this.pos - 1);
/*     */         } 
/* 525 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/* 526 */           return key[this.last = this.pos];
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
/* 540 */       float[] key = FloatOpenCustomHashSet.this.key; while (true) {
/*     */         float curr; int last;
/* 542 */         pos = (last = pos) + 1 & FloatOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 544 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 545 */             key[last] = 0.0F;
/*     */             return;
/*     */           } 
/* 548 */           int slot = HashCommon.mix(FloatOpenCustomHashSet.this.strategy.hashCode(curr)) & FloatOpenCustomHashSet.this.mask;
/* 549 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 551 */           pos = pos + 1 & FloatOpenCustomHashSet.this.mask;
/*     */         } 
/* 553 */         if (pos < last) {
/* 554 */           if (this.wrapped == null)
/* 555 */             this.wrapped = new FloatArrayList(2); 
/* 556 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 558 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 563 */       if (this.last == -1)
/* 564 */         throw new IllegalStateException(); 
/* 565 */       if (this.last == FloatOpenCustomHashSet.this.n) {
/* 566 */         FloatOpenCustomHashSet.this.containsNull = false;
/* 567 */         FloatOpenCustomHashSet.this.key[FloatOpenCustomHashSet.this.n] = 0.0F;
/* 568 */       } else if (this.pos >= 0) {
/* 569 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 572 */         FloatOpenCustomHashSet.this.remove(this.wrapped.getFloat(-this.pos - 1));
/* 573 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 576 */       FloatOpenCustomHashSet.this.size--;
/* 577 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public FloatIterator iterator() {
/* 584 */     return new SetIterator();
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
/* 601 */     return trim(this.size);
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
/* 625 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 626 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 627 */       return true; 
/*     */     try {
/* 629 */       rehash(l);
/* 630 */     } catch (OutOfMemoryError cantDoIt) {
/* 631 */       return false;
/*     */     } 
/* 633 */     return true;
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
/* 649 */     float[] key = this.key;
/* 650 */     int mask = newN - 1;
/* 651 */     float[] newKey = new float[newN + 1];
/* 652 */     int i = this.n;
/* 653 */     for (int j = realSize(); j-- != 0; ) {
/* 654 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 655 */       if (Float.floatToIntBits(newKey[
/* 656 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0)
/* 657 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 658 */       newKey[pos] = key[i];
/*     */     } 
/* 660 */     this.n = newN;
/* 661 */     this.mask = mask;
/* 662 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 663 */     this.key = newKey;
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
/*     */   public FloatOpenCustomHashSet clone() {
/*     */     FloatOpenCustomHashSet c;
/*     */     try {
/* 680 */       c = (FloatOpenCustomHashSet)super.clone();
/* 681 */     } catch (CloneNotSupportedException cantHappen) {
/* 682 */       throw new InternalError();
/*     */     } 
/* 684 */     c.key = (float[])this.key.clone();
/* 685 */     c.containsNull = this.containsNull;
/* 686 */     c.strategy = this.strategy;
/* 687 */     return c;
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
/* 700 */     int h = 0;
/* 701 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 702 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 703 */         i++; 
/* 704 */       h += this.strategy.hashCode(this.key[i]);
/* 705 */       i++;
/*     */     } 
/*     */     
/* 708 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 711 */     FloatIterator i = iterator();
/* 712 */     s.defaultWriteObject();
/* 713 */     for (int j = this.size; j-- != 0;)
/* 714 */       s.writeFloat(i.nextFloat()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 718 */     s.defaultReadObject();
/* 719 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 720 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 721 */     this.mask = this.n - 1;
/* 722 */     float[] key = this.key = new float[this.n + 1];
/*     */     
/* 724 */     for (int i = this.size; i-- != 0; ) {
/* 725 */       int pos; float k = s.readFloat();
/* 726 */       if (this.strategy.equals(k, 0.0F)) {
/* 727 */         pos = this.n;
/* 728 */         this.containsNull = true;
/*     */       }
/* 730 */       else if (Float.floatToIntBits(key[
/* 731 */             pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/* 732 */         while (Float.floatToIntBits(key[pos = pos + 1 & this.mask]) != 0);
/*     */       } 
/* 734 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */