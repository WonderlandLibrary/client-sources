/*     */ package it.unimi.dsi.fastutil.chars;
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
/*     */ public class CharOpenCustomHashSet
/*     */   extends AbstractCharSet
/*     */   implements Serializable, Cloneable, Hash
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private static final boolean ASSERTS = false;
/*     */   protected transient char[] key;
/*     */   protected transient int mask;
/*     */   protected transient boolean containsNull;
/*     */   protected CharHash.Strategy strategy;
/*     */   protected transient int n;
/*     */   protected transient int maxFill;
/*     */   protected final transient int minN;
/*     */   protected int size;
/*     */   protected final float f;
/*     */   
/*     */   public CharOpenCustomHashSet(int expected, float f, CharHash.Strategy strategy) {
/*  93 */     this.strategy = strategy;
/*  94 */     if (f <= 0.0F || f > 1.0F)
/*  95 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  96 */     if (expected < 0)
/*  97 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  98 */     this.f = f;
/*  99 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/* 100 */     this.mask = this.n - 1;
/* 101 */     this.maxFill = HashCommon.maxFill(this.n, f);
/* 102 */     this.key = new char[this.n + 1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharOpenCustomHashSet(int expected, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(Collection<? extends Character> c, float f, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(Collection<? extends Character> c, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(CharCollection c, float f, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(CharCollection c, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(CharIterator i, float f, CharHash.Strategy strategy) {
/* 193 */     this(16, f, strategy);
/* 194 */     while (i.hasNext()) {
/* 195 */       add(i.nextChar());
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
/*     */   public CharOpenCustomHashSet(CharIterator i, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(Iterator<?> i, float f, CharHash.Strategy strategy) {
/* 221 */     this(CharIterators.asCharIterator(i), f, strategy);
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
/*     */   public CharOpenCustomHashSet(Iterator<?> i, CharHash.Strategy strategy) {
/* 233 */     this(CharIterators.asCharIterator(i), strategy);
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
/*     */   public CharOpenCustomHashSet(char[] a, int offset, int length, float f, CharHash.Strategy strategy) {
/* 251 */     this((length < 0) ? 0 : length, f, strategy);
/* 252 */     CharArrays.ensureOffsetLength(a, offset, length);
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
/*     */   public CharOpenCustomHashSet(char[] a, int offset, int length, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(char[] a, float f, CharHash.Strategy strategy) {
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
/*     */   public CharOpenCustomHashSet(char[] a, CharHash.Strategy strategy) {
/* 297 */     this(a, 0.75F, strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharHash.Strategy strategy() {
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
/*     */   public boolean addAll(CharCollection c) {
/* 323 */     if (this.f <= 0.5D) {
/* 324 */       ensureCapacity(c.size());
/*     */     } else {
/* 326 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 328 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends Character> c) {
/* 333 */     if (this.f <= 0.5D) {
/* 334 */       ensureCapacity(c.size());
/*     */     } else {
/* 336 */       tryCapacity((size() + c.size()));
/*     */     } 
/* 338 */     return super.addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(char k) {
/* 343 */     if (this.strategy.equals(k, false)) {
/* 344 */       if (this.containsNull)
/* 345 */         return false; 
/* 346 */       this.containsNull = true;
/* 347 */       this.key[this.n] = k;
/*     */     } else {
/*     */       
/* 350 */       char[] key = this.key; int pos;
/*     */       char curr;
/* 352 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*     */         
/* 354 */         if (this.strategy.equals(curr, k))
/* 355 */           return false; 
/* 356 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
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
/* 379 */     char[] key = this.key; while (true) {
/*     */       char curr; int last;
/* 381 */       pos = (last = pos) + 1 & this.mask;
/*     */       while (true) {
/* 383 */         if ((curr = key[pos]) == '\000') {
/* 384 */           key[last] = Character.MIN_VALUE;
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
/* 404 */     this.key[this.n] = Character.MIN_VALUE;
/* 405 */     this.size--;
/* 406 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/* 407 */       rehash(this.n / 2); 
/* 408 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(char k) {
/* 413 */     if (this.strategy.equals(k, false)) {
/* 414 */       if (this.containsNull)
/* 415 */         return removeNullEntry(); 
/* 416 */       return false;
/*     */     } 
/*     */     
/* 419 */     char[] key = this.key;
/*     */     char curr;
/*     */     int pos;
/* 422 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/* 423 */       return false; 
/* 424 */     if (this.strategy.equals(k, curr))
/* 425 */       return removeEntry(pos); 
/*     */     while (true) {
/* 427 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/* 428 */         return false; 
/* 429 */       if (this.strategy.equals(k, curr)) {
/* 430 */         return removeEntry(pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(char k) {
/* 436 */     if (this.strategy.equals(k, false)) {
/* 437 */       return this.containsNull;
/*     */     }
/* 439 */     char[] key = this.key;
/*     */     char curr;
/*     */     int pos;
/* 442 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/* 443 */       return false; 
/* 444 */     if (this.strategy.equals(k, curr))
/* 445 */       return true; 
/*     */     while (true) {
/* 447 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
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
/* 466 */     Arrays.fill(this.key, false);
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
/*     */     implements CharIterator
/*     */   {
/* 483 */     int pos = CharOpenCustomHashSet.this.n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     int last = -1;
/*     */     
/* 492 */     int c = CharOpenCustomHashSet.this.size;
/*     */     
/* 494 */     boolean mustReturnNull = CharOpenCustomHashSet.this.containsNull;
/*     */ 
/*     */     
/*     */     CharArrayList wrapped;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 502 */       return (this.c != 0);
/*     */     }
/*     */     
/*     */     public char nextChar() {
/* 506 */       if (!hasNext())
/* 507 */         throw new NoSuchElementException(); 
/* 508 */       this.c--;
/* 509 */       if (this.mustReturnNull) {
/* 510 */         this.mustReturnNull = false;
/* 511 */         this.last = CharOpenCustomHashSet.this.n;
/* 512 */         return CharOpenCustomHashSet.this.key[CharOpenCustomHashSet.this.n];
/*     */       } 
/* 514 */       char[] key = CharOpenCustomHashSet.this.key;
/*     */       while (true) {
/* 516 */         if (--this.pos < 0) {
/*     */           
/* 518 */           this.last = Integer.MIN_VALUE;
/* 519 */           return this.wrapped.getChar(-this.pos - 1);
/*     */         } 
/* 521 */         if (key[this.pos] != '\000') {
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
/* 536 */       char[] key = CharOpenCustomHashSet.this.key; while (true) {
/*     */         char curr; int last;
/* 538 */         pos = (last = pos) + 1 & CharOpenCustomHashSet.this.mask;
/*     */         while (true) {
/* 540 */           if ((curr = key[pos]) == '\000') {
/* 541 */             key[last] = Character.MIN_VALUE;
/*     */             return;
/*     */           } 
/* 544 */           int slot = HashCommon.mix(CharOpenCustomHashSet.this.strategy.hashCode(curr)) & CharOpenCustomHashSet.this.mask;
/* 545 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*     */             break; 
/* 547 */           pos = pos + 1 & CharOpenCustomHashSet.this.mask;
/*     */         } 
/* 549 */         if (pos < last) {
/* 550 */           if (this.wrapped == null)
/* 551 */             this.wrapped = new CharArrayList(2); 
/* 552 */           this.wrapped.add(key[pos]);
/*     */         } 
/* 554 */         key[last] = curr;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 559 */       if (this.last == -1)
/* 560 */         throw new IllegalStateException(); 
/* 561 */       if (this.last == CharOpenCustomHashSet.this.n) {
/* 562 */         CharOpenCustomHashSet.this.containsNull = false;
/* 563 */         CharOpenCustomHashSet.this.key[CharOpenCustomHashSet.this.n] = Character.MIN_VALUE;
/* 564 */       } else if (this.pos >= 0) {
/* 565 */         shiftKeys(this.last);
/*     */       } else {
/*     */         
/* 568 */         CharOpenCustomHashSet.this.remove(this.wrapped.getChar(-this.pos - 1));
/* 569 */         this.last = -1;
/*     */         return;
/*     */       } 
/* 572 */       CharOpenCustomHashSet.this.size--;
/* 573 */       this.last = -1;
/*     */     }
/*     */     
/*     */     private SetIterator() {}
/*     */   }
/*     */   
/*     */   public CharIterator iterator() {
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
/* 645 */     char[] key = this.key;
/* 646 */     int mask = newN - 1;
/* 647 */     char[] newKey = new char[newN + 1];
/* 648 */     int i = this.n;
/* 649 */     for (int j = realSize(); j-- != 0; ) {
/* 650 */       while (key[--i] == '\000'); int pos;
/* 651 */       if (newKey[pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask] != '\000')
/*     */       {
/* 653 */         while (newKey[pos = pos + 1 & mask] != '\000'); } 
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
/*     */   public CharOpenCustomHashSet clone() {
/*     */     CharOpenCustomHashSet c;
/*     */     try {
/* 676 */       c = (CharOpenCustomHashSet)super.clone();
/* 677 */     } catch (CloneNotSupportedException cantHappen) {
/* 678 */       throw new InternalError();
/*     */     } 
/* 680 */     c.key = (char[])this.key.clone();
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
/* 698 */       while (this.key[i] == '\000')
/* 699 */         i++; 
/* 700 */       h += this.strategy.hashCode(this.key[i]);
/* 701 */       i++;
/*     */     } 
/*     */     
/* 704 */     return h;
/*     */   }
/*     */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 707 */     CharIterator i = iterator();
/* 708 */     s.defaultWriteObject();
/* 709 */     for (int j = this.size; j-- != 0;)
/* 710 */       s.writeChar(i.nextChar()); 
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 714 */     s.defaultReadObject();
/* 715 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 716 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 717 */     this.mask = this.n - 1;
/* 718 */     char[] key = this.key = new char[this.n + 1];
/*     */     
/* 720 */     for (int i = this.size; i-- != 0; ) {
/* 721 */       int pos; char k = s.readChar();
/* 722 */       if (this.strategy.equals(k, false)) {
/* 723 */         pos = this.n;
/* 724 */         this.containsNull = true;
/*     */       }
/* 726 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != '\000') {
/* 727 */         while (key[pos = pos + 1 & this.mask] != '\000');
/*     */       } 
/* 729 */       key[pos] = k;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkTable() {}
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */