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
/*     */ public class FloatOpenHashSet
/*     */   extends AbstractFloatSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient float[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public FloatOpenHashSet(int expected, float f) {
/*  86 */     if (f <= 0.0F || f > 1.0F)
/*  87 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  88 */     if (expected < 0)
/*  89 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  90 */     this.f = f;
/*  91 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  92 */     this.mask = this.n - 1;
/*  93 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  94 */     this.key = new float[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(int expected) {
/* 103 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet() {
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
/*     */   public FloatOpenHashSet(Collection<? extends Float> c, float f) {
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
/*     */   public FloatOpenHashSet(Collection<? extends Float> c) {
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
/*     */   public FloatOpenHashSet(FloatCollection c, float f) {
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
/*     */   public FloatOpenHashSet(FloatCollection c) {
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
/*     */   public FloatOpenHashSet(FloatIterator i, float f) {
/* 166 */     this(16, f);
/* 167 */     while (i.hasNext()) {
/* 168 */       add(i.nextFloat());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(FloatIterator i) {
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
/*     */   public FloatOpenHashSet(Iterator<?> i, float f) {
/* 189 */     this(FloatIterators.asFloatIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(Iterator<?> i) {
/* 199 */     this(FloatIterators.asFloatIterator(i));
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
/*     */   public FloatOpenHashSet(float[] a, int offset, int length, float f) {
/* 214 */     this((length < 0) ? 0 : length, f);
/* 215 */     FloatArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public FloatOpenHashSet(float[] a, int offset, int length) {
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
/*     */   public FloatOpenHashSet(float[] a, float f) {
/* 242 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FloatOpenHashSet(float[] a) {
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
/*     */   public boolean addAll(FloatCollection c) {
/* 270 */     if (this.f <= 0.5D) {
/* 271 */       ensureCapacity(c.size());
/*     */     } else {
/* 273 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 275 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Float> c) {
/* 280 */     if (this.f <= 0.5D) {
/* 281 */       ensureCapacity(c.size());
/*     */     } else {
/* 283 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 285 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(float k) {
/* 290 */     if (Float.floatToIntBits(k) == 0) {
/* 291 */       if (this.containsNull)
/* 292 */         return false; 
/* 293 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 296 */       float[] key = this.key; int pos;
/*     */       float curr;
/* 298 */       if (Float.floatToIntBits(
/* 299 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*     */         
/* 301 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/* 302 */           return false; 
/* 303 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/* 304 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/* 305 */             return false; 
/*     */         } 
/* 307 */       }  key[pos] = k;
/*     */     } 
/* 309 */     if (this.size++ >= this.maxFill) {
/* 310 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*     */     
/* 313 */     return true;
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
/* 326 */     float[] key = this.key; while (true) {
/*     */       float curr; int last;
/* 328 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 330 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 331 */           key[last] = 0.0F;
/*     */           return;
/*     */         } 
/* 334 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/* 335 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */           break; 
/* 337 */         pos = pos + 1 & this.mask;
/*     */       } 
/* 339 */       key[last] = curr;
/*     */     } 
/*     */   }
/*     */   private boolean removeEntry(int pos) {
/* 343 */     this.size--;
/* 344 */     shiftKeys(pos);
/* 345 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 346 */       rehash(this.n / 2); 
/* 347 */     return true;
/*     */   }
/*     */   private boolean removeNullEntry() {
/* 350 */     this.containsNull = false;
/* 351 */     this.key[this.n] = 0.0F;
/* 352 */     this.size--;
/* 353 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 354 */       rehash(this.n / 2); 
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(float k) {
/* 360 */     if (Float.floatToIntBits(k) == 0) {
/* 361 */       if (this.containsNull)
/* 362 */         return removeNullEntry(); 
/* 363 */       return false;
/*     */     } 
/*     */     
/* 366 */     float[] key = this.key;
/*     */     float curr;
/*     */     int pos;
/* 369 */     if (Float.floatToIntBits(
/* 370 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*     */     {
/* 372 */       return false; } 
/* 373 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/* 374 */       return removeEntry(pos); 
/*     */     while (true) {
/* 376 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/* 377 */         return false; 
/* 378 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 379 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(float k) {
/* 385 */     if (Float.floatToIntBits(k) == 0) {
/* 386 */       return this.containsNull;
/*     */     }
/* 388 */     float[] key = this.key;
/*     */     float curr;
/*     */     int pos;
/* 391 */     if (Float.floatToIntBits(
/* 392 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*     */     {
/* 394 */       return false; } 
/* 395 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/* 396 */       return true; 
/*     */     while (true) {
/* 398 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/* 399 */         return false; 
/* 400 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 401 */         return true;
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
/* 413 */     if (this.size == 0)
/*     */       return; 
/* 415 */     this.size = 0;
/* 416 */     this.containsNull = false;
/* 417 */     Arrays.fill(this.key, 0.0F);
/*     */   }
/*     */   
/*     */   public int size() {
/* 421 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 425 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class SetIterator
/*     */     implements FloatIterator
/*     */   {
/* 434 */     int pos = FloatOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     int last = -1;
/*     */     
/* 443 */     int c = FloatOpenHashSet.this.size;
/*     */     
/* 445 */     boolean mustReturnNull = FloatOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     FloatArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 453 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public float nextFloat() {
/* 457 */       if (!hasNext())
/* 458 */         throw new NoSuchElementException(); 
/* 459 */       this.c--;
/* 460 */       if (this.mustReturnNull) {
/* 461 */         this.mustReturnNull = false;
/* 462 */         this.last = FloatOpenHashSet.this.n;
/* 463 */         return FloatOpenHashSet.this.key[FloatOpenHashSet.this.n];
/*     */       } 
/* 465 */       float[] key = FloatOpenHashSet.this.key;
/*     */       while (true) {
/* 467 */         if (--this.pos < 0) {
/*     */           
/* 469 */           this.last = Integer.MIN_VALUE;
/* 470 */           return this.wrapped.getFloat(-this.pos - 1);
/*     */         } 
/* 472 */         if (Float.floatToIntBits(key[this.pos]) != 0) {
/* 473 */           return key[this.last = this.pos];
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
/* 487 */       float[] key = FloatOpenHashSet.this.key; while (true) {
/*     */         float curr; int last;
/* 489 */         pos = (last = pos) + 1 & FloatOpenHashSet.this.mask;
/*     */         while (true) {
/* 491 */           if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 492 */             key[last] = 0.0F;
/*     */             return;
/*     */           } 
/* 495 */           int slot = HashCommon.mix(HashCommon.float2int(curr)) & FloatOpenHashSet.this.mask;
/*     */           
/* 497 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 499 */           pos = pos + 1 & FloatOpenHashSet.this.mask;
/*     */         } 
/* 501 */         if (pos < last) {
/* 502 */           if (this.wrapped == null)
/* 503 */             this.wrapped = new FloatArrayList(2); 
/* 504 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 506 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 511 */       if (this.last == -1)
/* 512 */         throw new IllegalStateException(); 
/* 513 */       if (this.last == FloatOpenHashSet.this.n) {
/* 514 */         FloatOpenHashSet.this.containsNull = false;
/* 515 */         FloatOpenHashSet.this.key[FloatOpenHashSet.this.n] = 0.0F;
/* 516 */       } else if (this.pos >= 0) {
/* 517 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 520 */         FloatOpenHashSet.this.remove(this.wrapped.getFloat(-this.pos - 1));
/* 521 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 524 */       FloatOpenHashSet.this.size--;
/* 525 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public FloatIterator iterator() {
/* 532 */     return new SetIterator();
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
/* 549 */     return trim(this.size);
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
/* 573 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 574 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 575 */       return true; 
/*     */     try {
/* 577 */       rehash(l);
/* 578 */     } catch (OutOfMemoryError cantDoIt) {
/* 579 */       return false;
/*     */     } 
/* 581 */     return true;
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
/* 597 */     float[] key = this.key;
/* 598 */     int mask = newN - 1;
/* 599 */     float[] newKey = new float[newN + 1];
/* 600 */     int i = this.n;
/* 601 */     for (int j = realSize(); j-- != 0; ) {
/* 602 */       while (Float.floatToIntBits(key[--i]) == 0); int pos;
/* 603 */       if (Float.floatToIntBits(newKey[
/* 604 */             pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask]) != 0)
/* 605 */         while (Float.floatToIntBits(newKey[pos = pos + 1 & mask]) != 0); 
/* 606 */       newKey[pos] = key[i];
/*     */     } 
/* 608 */     this.n = newN;
/* 609 */     this.mask = mask;
/* 610 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 611 */     this.key = newKey;
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
/*     */   public FloatOpenHashSet clone() {
/*     */     FloatOpenHashSet c;
/*     */     try {
/* 628 */       c = (FloatOpenHashSet)super.clone();
/* 629 */     } catch (CloneNotSupportedException cantHappen) {
/* 630 */       throw new InternalError();
/*     */     } 
/* 632 */     c.key = (float[])this.key.clone();
/* 633 */     c.containsNull = this.containsNull;
/* 634 */     return c;
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
/* 647 */     int h = 0;
/* 648 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 649 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 650 */         i++; 
/* 651 */       h += HashCommon.float2int(this.key[i]);
/* 652 */       i++;
/*     */     } 
/*     */     
/* 655 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 658 */     FloatIterator i = iterator();
/* 659 */     s.defaultWriteObject();
/* 660 */     for (int j = this.size; j-- != 0;)
/* 661 */       s.writeFloat(i.nextFloat()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 665 */     s.defaultReadObject();
/* 666 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 667 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 668 */     this.mask = this.n - 1;
/* 669 */     float[] key = this.key = new float[this.n + 1];
/*     */     
/* 671 */     for (int i = this.size; i-- != 0; ) {
/* 672 */       int pos; float k = s.readFloat();
/* 673 */       if (Float.floatToIntBits(k) == 0) {
/* 674 */         pos = this.n;
/* 675 */         this.containsNull = true;
/*     */       }
/* 677 */       else if (Float.floatToIntBits(key[
/* 678 */             pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*     */         
/* 680 */         while (Float.floatToIntBits(key[pos = pos + 1 & this.mask]) != 0);
/*     */       } 
/* 682 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\FloatOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */