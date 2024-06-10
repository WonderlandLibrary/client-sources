/*     */ package it.unimi.dsi.fastutil.doubles;
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
/*     */ public class DoubleOpenHashSet
/*     */   extends AbstractDoubleSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient double[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public DoubleOpenHashSet(int expected, float f) {
/*  86 */     if (f <= 0.0F || f > 1.0F)
/*  87 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  88 */     if (expected < 0)
/*  89 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  90 */     this.f = f;
/*  91 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  92 */     this.mask = this.n - 1;
/*  93 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  94 */     this.key = new double[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashSet(int expected) {
/* 103 */     this(expected, 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashSet() {
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
/*     */   public DoubleOpenHashSet(Collection<? extends Double> c, float f) {
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
/*     */   public DoubleOpenHashSet(Collection<? extends Double> c) {
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
/*     */   public DoubleOpenHashSet(DoubleCollection c, float f) {
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
/*     */   public DoubleOpenHashSet(DoubleCollection c) {
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
/*     */   public DoubleOpenHashSet(DoubleIterator i, float f) {
/* 166 */     this(16, f);
/* 167 */     while (i.hasNext()) {
/* 168 */       add(i.nextDouble());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashSet(DoubleIterator i) {
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
/*     */   public DoubleOpenHashSet(Iterator<?> i, float f) {
/* 189 */     this(DoubleIterators.asDoubleIterator(i), f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashSet(Iterator<?> i) {
/* 199 */     this(DoubleIterators.asDoubleIterator(i));
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
/*     */   public DoubleOpenHashSet(double[] a, int offset, int length, float f) {
/* 214 */     this((length < 0) ? 0 : length, f);
/* 215 */     DoubleArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public DoubleOpenHashSet(double[] a, int offset, int length) {
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
/*     */   public DoubleOpenHashSet(double[] a, float f) {
/* 242 */     this(a, 0, a.length, f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleOpenHashSet(double[] a) {
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
/*     */   public boolean addAll(DoubleCollection c) {
/* 270 */     if (this.f <= 0.5D) {
/* 271 */       ensureCapacity(c.size());
/*     */     } else {
/* 273 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 275 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Double> c) {
/* 280 */     if (this.f <= 0.5D) {
/* 281 */       ensureCapacity(c.size());
/*     */     } else {
/* 283 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 285 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(double k) {
/* 290 */     if (Double.doubleToLongBits(k) == 0L) {
/* 291 */       if (this.containsNull)
/* 292 */         return false; 
/* 293 */       this.containsNull = true;
/*     */     } else {
/*     */       
/* 296 */       double[] key = this.key; int pos;
/*     */       double curr;
/* 298 */       if (Double.doubleToLongBits(
/* 299 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*     */         
/* 301 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/* 302 */           return false; 
/* 303 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/* 304 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
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
/* 326 */     double[] key = this.key; while (true) {
/*     */       double curr; int last;
/* 328 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 330 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 331 */           key[last] = 0.0D;
/*     */           return;
/*     */         } 
/* 334 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
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
/* 351 */     this.key[this.n] = 0.0D;
/* 352 */     this.size--;
/* 353 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 354 */       rehash(this.n / 2); 
/* 355 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(double k) {
/* 360 */     if (Double.doubleToLongBits(k) == 0L) {
/* 361 */       if (this.containsNull)
/* 362 */         return removeNullEntry(); 
/* 363 */       return false;
/*     */     } 
/*     */     
/* 366 */     double[] key = this.key;
/*     */     double curr;
/*     */     int pos;
/* 369 */     if (Double.doubleToLongBits(
/* 370 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*     */     {
/* 372 */       return false; } 
/* 373 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/* 374 */       return removeEntry(pos); 
/*     */     while (true) {
/* 376 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/* 377 */         return false; 
/* 378 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 379 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(double k) {
/* 385 */     if (Double.doubleToLongBits(k) == 0L) {
/* 386 */       return this.containsNull;
/*     */     }
/* 388 */     double[] key = this.key;
/*     */     double curr;
/*     */     int pos;
/* 391 */     if (Double.doubleToLongBits(
/* 392 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*     */     {
/* 394 */       return false; } 
/* 395 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/* 396 */       return true; 
/*     */     while (true) {
/* 398 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/* 399 */         return false; 
/* 400 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
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
/* 417 */     Arrays.fill(this.key, 0.0D);
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
/*     */     implements DoubleIterator
/*     */   {
/* 434 */     int pos = DoubleOpenHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 441 */     int last = -1;
/*     */     
/* 443 */     int c = DoubleOpenHashSet.this.size;
/*     */     
/* 445 */     boolean mustReturnNull = DoubleOpenHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     DoubleArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 453 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public double nextDouble() {
/* 457 */       if (!hasNext())
/* 458 */         throw new NoSuchElementException(); 
/* 459 */       this.c--;
/* 460 */       if (this.mustReturnNull) {
/* 461 */         this.mustReturnNull = false;
/* 462 */         this.last = DoubleOpenHashSet.this.n;
/* 463 */         return DoubleOpenHashSet.this.key[DoubleOpenHashSet.this.n];
/*     */       } 
/* 465 */       double[] key = DoubleOpenHashSet.this.key;
/*     */       while (true) {
/* 467 */         if (--this.pos < 0) {
/*     */           
/* 469 */           this.last = Integer.MIN_VALUE;
/* 470 */           return this.wrapped.getDouble(-this.pos - 1);
/*     */         } 
/* 472 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
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
/* 487 */       double[] key = DoubleOpenHashSet.this.key; while (true) {
/*     */         double curr; int last;
/* 489 */         pos = (last = pos) + 1 & DoubleOpenHashSet.this.mask;
/*     */         while (true) {
/* 491 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 492 */             key[last] = 0.0D;
/*     */             return;
/*     */           } 
/* 495 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & DoubleOpenHashSet.this.mask;
/* 496 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 498 */           pos = pos + 1 & DoubleOpenHashSet.this.mask;
/*     */         } 
/* 500 */         if (pos < last) {
/* 501 */           if (this.wrapped == null)
/* 502 */             this.wrapped = new DoubleArrayList(2); 
/* 503 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 505 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 510 */       if (this.last == -1)
/* 511 */         throw new IllegalStateException(); 
/* 512 */       if (this.last == DoubleOpenHashSet.this.n) {
/* 513 */         DoubleOpenHashSet.this.containsNull = false;
/* 514 */         DoubleOpenHashSet.this.key[DoubleOpenHashSet.this.n] = 0.0D;
/* 515 */       } else if (this.pos >= 0) {
/* 516 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 519 */         DoubleOpenHashSet.this.remove(this.wrapped.getDouble(-this.pos - 1));
/* 520 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 523 */       DoubleOpenHashSet.this.size--;
/* 524 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public DoubleIterator iterator() {
/* 531 */     return new SetIterator();
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
/* 548 */     return trim(this.size);
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
/* 572 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 573 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 574 */       return true; 
/*     */     try {
/* 576 */       rehash(l);
/* 577 */     } catch (OutOfMemoryError cantDoIt) {
/* 578 */       return false;
/*     */     } 
/* 580 */     return true;
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
/* 596 */     double[] key = this.key;
/* 597 */     int mask = newN - 1;
/* 598 */     double[] newKey = new double[newN + 1];
/* 599 */     int i = this.n;
/* 600 */     for (int j = realSize(); j-- != 0; ) {
/* 601 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 602 */       if (Double.doubleToLongBits(newKey[
/* 603 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*     */       {
/* 605 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
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
/*     */   public DoubleOpenHashSet clone() {
/*     */     DoubleOpenHashSet c;
/*     */     try {
/* 628 */       c = (DoubleOpenHashSet)super.clone();
/* 629 */     } catch (CloneNotSupportedException cantHappen) {
/* 630 */       throw new InternalError();
/*     */     } 
/* 632 */     c.key = (double[])this.key.clone();
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
/* 649 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 650 */         i++; 
/* 651 */       h += HashCommon.double2int(this.key[i]);
/* 652 */       i++;
/*     */     } 
/*     */     
/* 655 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 658 */     DoubleIterator i = iterator();
/* 659 */     s.defaultWriteObject();
/* 660 */     for (int j = this.size; j-- != 0;)
/* 661 */       s.writeDouble(i.nextDouble()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 665 */     s.defaultReadObject();
/* 666 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 667 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 668 */     this.mask = this.n - 1;
/* 669 */     double[] key = this.key = new double[this.n + 1];
/*     */     
/* 671 */     for (int i = this.size; i-- != 0; ) {
/* 672 */       int pos; double k = s.readDouble();
/* 673 */       if (Double.doubleToLongBits(k) == 0L) {
/* 674 */         pos = this.n;
/* 675 */         this.containsNull = true;
/*     */       }
/* 677 */       else if (Double.doubleToLongBits(key[
/* 678 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*     */         
/* 680 */         while (Double.doubleToLongBits(key[pos = pos + 1 & this.mask]) != 0L);
/*     */       } 
/* 682 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */