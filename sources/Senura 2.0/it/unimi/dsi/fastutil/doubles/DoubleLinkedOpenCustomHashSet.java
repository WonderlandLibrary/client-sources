/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DoubleLinkedOpenCustomHashSet
/*      */   extends AbstractDoubleSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected DoubleHash.Strategy strategy;
/*   98 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  103 */   protected transient int last = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient long[] link;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int maxFill;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final transient int minN;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int size;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(int expected, float f, DoubleHash.Strategy strategy) {
/*  144 */     this.strategy = strategy;
/*  145 */     if (f <= 0.0F || f > 1.0F)
/*  146 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  147 */     if (expected < 0)
/*  148 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  149 */     this.f = f;
/*  150 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  151 */     this.mask = this.n - 1;
/*  152 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  153 */     this.key = new double[this.n + 1];
/*  154 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(int expected, DoubleHash.Strategy strategy) {
/*  166 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(DoubleHash.Strategy strategy) {
/*  177 */     this(16, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(Collection<? extends Double> c, float f, DoubleHash.Strategy strategy) {
/*  191 */     this(c.size(), f, strategy);
/*  192 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(Collection<? extends Double> c, DoubleHash.Strategy strategy) {
/*  205 */     this(c, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(DoubleCollection c, float f, DoubleHash.Strategy strategy) {
/*  219 */     this(c.size(), f, strategy);
/*  220 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(DoubleCollection c, DoubleHash.Strategy strategy) {
/*  233 */     this(c, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(DoubleIterator i, float f, DoubleHash.Strategy strategy) {
/*  247 */     this(16, f, strategy);
/*  248 */     while (i.hasNext()) {
/*  249 */       add(i.nextDouble());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(DoubleIterator i, DoubleHash.Strategy strategy) {
/*  262 */     this(i, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(Iterator<?> i, float f, DoubleHash.Strategy strategy) {
/*  276 */     this(DoubleIterators.asDoubleIterator(i), f, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(Iterator<?> i, DoubleHash.Strategy strategy) {
/*  289 */     this(DoubleIterators.asDoubleIterator(i), strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(double[] a, int offset, int length, float f, DoubleHash.Strategy strategy) {
/*  307 */     this((length < 0) ? 0 : length, f, strategy);
/*  308 */     DoubleArrays.ensureOffsetLength(a, offset, length);
/*  309 */     for (int i = 0; i < length; i++) {
/*  310 */       add(a[offset + i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(double[] a, int offset, int length, DoubleHash.Strategy strategy) {
/*  327 */     this(a, offset, length, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(double[] a, float f, DoubleHash.Strategy strategy) {
/*  341 */     this(a, 0, a.length, f, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet(double[] a, DoubleHash.Strategy strategy) {
/*  354 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleHash.Strategy strategy() {
/*  362 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  365 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  368 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  369 */     if (needed > this.n)
/*  370 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  373 */     int needed = (int)Math.min(1073741824L, 
/*  374 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  375 */     if (needed > this.n)
/*  376 */       rehash(needed); 
/*      */   }
/*      */   
/*      */   public boolean addAll(DoubleCollection c) {
/*  380 */     if (this.f <= 0.5D) {
/*  381 */       ensureCapacity(c.size());
/*      */     } else {
/*  383 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  385 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Double> c) {
/*  390 */     if (this.f <= 0.5D) {
/*  391 */       ensureCapacity(c.size());
/*      */     } else {
/*  393 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  395 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(double k) {
/*      */     int pos;
/*  400 */     if (this.strategy.equals(k, 0.0D)) {
/*  401 */       if (this.containsNull)
/*  402 */         return false; 
/*  403 */       pos = this.n;
/*  404 */       this.containsNull = true;
/*  405 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  408 */       double[] key = this.key;
/*      */       double curr;
/*  410 */       if (Double.doubleToLongBits(
/*  411 */           curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  412 */         if (this.strategy.equals(curr, k))
/*  413 */           return false; 
/*  414 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  415 */           if (this.strategy.equals(curr, k))
/*  416 */             return false; 
/*      */         } 
/*  418 */       }  key[pos] = k;
/*      */     } 
/*  420 */     if (this.size == 0) {
/*  421 */       this.first = this.last = pos;
/*      */       
/*  423 */       this.link[pos] = -1L;
/*      */     } else {
/*  425 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  426 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  427 */       this.last = pos;
/*      */     } 
/*  429 */     if (this.size++ >= this.maxFill) {
/*  430 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  433 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void shiftKeys(int pos) {
/*  446 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  448 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  450 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  451 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  454 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  455 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  457 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  459 */       key[last] = curr;
/*  460 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  464 */     this.size--;
/*  465 */     fixPointers(pos);
/*  466 */     shiftKeys(pos);
/*  467 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  468 */       rehash(this.n / 2); 
/*  469 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  472 */     this.containsNull = false;
/*  473 */     this.key[this.n] = 0.0D;
/*  474 */     this.size--;
/*  475 */     fixPointers(this.n);
/*  476 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  477 */       rehash(this.n / 2); 
/*  478 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  483 */     if (this.strategy.equals(k, 0.0D)) {
/*  484 */       if (this.containsNull)
/*  485 */         return removeNullEntry(); 
/*  486 */       return false;
/*      */     } 
/*      */     
/*  489 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  492 */     if (Double.doubleToLongBits(
/*  493 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  494 */       return false; 
/*  495 */     if (this.strategy.equals(k, curr))
/*  496 */       return removeEntry(pos); 
/*      */     while (true) {
/*  498 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  499 */         return false; 
/*  500 */       if (this.strategy.equals(k, curr)) {
/*  501 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(double k) {
/*  507 */     if (this.strategy.equals(k, 0.0D)) {
/*  508 */       return this.containsNull;
/*      */     }
/*  510 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  513 */     if (Double.doubleToLongBits(
/*  514 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  515 */       return false; 
/*  516 */     if (this.strategy.equals(k, curr))
/*  517 */       return true; 
/*      */     while (true) {
/*  519 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  520 */         return false; 
/*  521 */       if (this.strategy.equals(k, curr)) {
/*  522 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeFirstDouble() {
/*  533 */     if (this.size == 0)
/*  534 */       throw new NoSuchElementException(); 
/*  535 */     int pos = this.first;
/*      */     
/*  537 */     this.first = (int)this.link[pos];
/*  538 */     if (0 <= this.first)
/*      */     {
/*  540 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  542 */     double k = this.key[pos];
/*  543 */     this.size--;
/*  544 */     if (this.strategy.equals(k, 0.0D)) {
/*  545 */       this.containsNull = false;
/*  546 */       this.key[this.n] = 0.0D;
/*      */     } else {
/*  548 */       shiftKeys(pos);
/*  549 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  550 */       rehash(this.n / 2); 
/*  551 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeLastDouble() {
/*  561 */     if (this.size == 0)
/*  562 */       throw new NoSuchElementException(); 
/*  563 */     int pos = this.last;
/*      */     
/*  565 */     this.last = (int)(this.link[pos] >>> 32L);
/*  566 */     if (0 <= this.last)
/*      */     {
/*  568 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  570 */     double k = this.key[pos];
/*  571 */     this.size--;
/*  572 */     if (this.strategy.equals(k, 0.0D)) {
/*  573 */       this.containsNull = false;
/*  574 */       this.key[this.n] = 0.0D;
/*      */     } else {
/*  576 */       shiftKeys(pos);
/*  577 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  578 */       rehash(this.n / 2); 
/*  579 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  582 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  584 */     if (this.last == i) {
/*  585 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  587 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  589 */       long linki = this.link[i];
/*  590 */       int prev = (int)(linki >>> 32L);
/*  591 */       int next = (int)linki;
/*  592 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  593 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  595 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  596 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  597 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  600 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  602 */     if (this.first == i) {
/*  603 */       this.first = (int)this.link[i];
/*      */       
/*  605 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  607 */       long linki = this.link[i];
/*  608 */       int prev = (int)(linki >>> 32L);
/*  609 */       int next = (int)linki;
/*  610 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  611 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  613 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  614 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  615 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(double k) {
/*      */     int pos;
/*  627 */     if (this.strategy.equals(k, 0.0D)) {
/*  628 */       if (this.containsNull) {
/*  629 */         moveIndexToFirst(this.n);
/*  630 */         return false;
/*      */       } 
/*  632 */       this.containsNull = true;
/*  633 */       pos = this.n;
/*      */     } else {
/*      */       
/*  636 */       double[] key = this.key;
/*  637 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  639 */       while (Double.doubleToLongBits(key[pos]) != 0L) {
/*  640 */         if (this.strategy.equals(k, key[pos])) {
/*  641 */           moveIndexToFirst(pos);
/*  642 */           return false;
/*      */         } 
/*  644 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  647 */     this.key[pos] = k;
/*  648 */     if (this.size == 0) {
/*  649 */       this.first = this.last = pos;
/*      */       
/*  651 */       this.link[pos] = -1L;
/*      */     } else {
/*  653 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  654 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  655 */       this.first = pos;
/*      */     } 
/*  657 */     if (this.size++ >= this.maxFill) {
/*  658 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  661 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(double k) {
/*      */     int pos;
/*  673 */     if (this.strategy.equals(k, 0.0D)) {
/*  674 */       if (this.containsNull) {
/*  675 */         moveIndexToLast(this.n);
/*  676 */         return false;
/*      */       } 
/*  678 */       this.containsNull = true;
/*  679 */       pos = this.n;
/*      */     } else {
/*      */       
/*  682 */       double[] key = this.key;
/*  683 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  685 */       while (Double.doubleToLongBits(key[pos]) != 0L) {
/*  686 */         if (this.strategy.equals(k, key[pos])) {
/*  687 */           moveIndexToLast(pos);
/*  688 */           return false;
/*      */         } 
/*  690 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  693 */     this.key[pos] = k;
/*  694 */     if (this.size == 0) {
/*  695 */       this.first = this.last = pos;
/*      */       
/*  697 */       this.link[pos] = -1L;
/*      */     } else {
/*  699 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  700 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  701 */       this.last = pos;
/*      */     } 
/*  703 */     if (this.size++ >= this.maxFill) {
/*  704 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  707 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  718 */     if (this.size == 0)
/*      */       return; 
/*  720 */     this.size = 0;
/*  721 */     this.containsNull = false;
/*  722 */     Arrays.fill(this.key, 0.0D);
/*  723 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  727 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  731 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  741 */     if (this.size == 0) {
/*  742 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  745 */     if (this.first == i) {
/*  746 */       this.first = (int)this.link[i];
/*  747 */       if (0 <= this.first)
/*      */       {
/*  749 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  753 */     if (this.last == i) {
/*  754 */       this.last = (int)(this.link[i] >>> 32L);
/*  755 */       if (0 <= this.last)
/*      */       {
/*  757 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  761 */     long linki = this.link[i];
/*  762 */     int prev = (int)(linki >>> 32L);
/*  763 */     int next = (int)linki;
/*  764 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  765 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int s, int d) {
/*  777 */     if (this.size == 1) {
/*  778 */       this.first = this.last = d;
/*      */       
/*  780 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  783 */     if (this.first == s) {
/*  784 */       this.first = d;
/*  785 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  786 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  789 */     if (this.last == s) {
/*  790 */       this.last = d;
/*  791 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  792 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  795 */     long links = this.link[s];
/*  796 */     int prev = (int)(links >>> 32L);
/*  797 */     int next = (int)links;
/*  798 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  799 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  800 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDouble() {
/*  809 */     if (this.size == 0)
/*  810 */       throw new NoSuchElementException(); 
/*  811 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDouble() {
/*  820 */     if (this.size == 0)
/*  821 */       throw new NoSuchElementException(); 
/*  822 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleSortedSet tailSet(double from) {
/*  831 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleSortedSet headSet(double to) {
/*  840 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleSortedSet subSet(double from, double to) {
/*  849 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/*  858 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class SetIterator
/*      */     implements DoubleListIterator
/*      */   {
/*  873 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  879 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  884 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  889 */     int index = -1;
/*      */     SetIterator() {
/*  891 */       this.next = DoubleLinkedOpenCustomHashSet.this.first;
/*  892 */       this.index = 0;
/*      */     }
/*      */     SetIterator(double from) {
/*  895 */       if (DoubleLinkedOpenCustomHashSet.this.strategy.equals(from, 0.0D)) {
/*  896 */         if (DoubleLinkedOpenCustomHashSet.this.containsNull) {
/*  897 */           this.next = (int)DoubleLinkedOpenCustomHashSet.this.link[DoubleLinkedOpenCustomHashSet.this.n];
/*  898 */           this.prev = DoubleLinkedOpenCustomHashSet.this.n;
/*      */           return;
/*      */         } 
/*  901 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  903 */       if (DoubleLinkedOpenCustomHashSet.this.strategy.equals(DoubleLinkedOpenCustomHashSet.this.key[DoubleLinkedOpenCustomHashSet.this.last], from)) {
/*  904 */         this.prev = DoubleLinkedOpenCustomHashSet.this.last;
/*  905 */         this.index = DoubleLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  909 */       double[] key = DoubleLinkedOpenCustomHashSet.this.key;
/*  910 */       int pos = HashCommon.mix(DoubleLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & DoubleLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  912 */       while (Double.doubleToLongBits(key[pos]) != 0L) {
/*  913 */         if (DoubleLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  915 */           this.next = (int)DoubleLinkedOpenCustomHashSet.this.link[pos];
/*  916 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  919 */         pos = pos + 1 & DoubleLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  921 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  925 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  929 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public double nextDouble() {
/*  933 */       if (!hasNext())
/*  934 */         throw new NoSuchElementException(); 
/*  935 */       this.curr = this.next;
/*  936 */       this.next = (int)DoubleLinkedOpenCustomHashSet.this.link[this.curr];
/*  937 */       this.prev = this.curr;
/*  938 */       if (this.index >= 0) {
/*  939 */         this.index++;
/*      */       }
/*      */       
/*  942 */       return DoubleLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/*  946 */       if (!hasPrevious())
/*  947 */         throw new NoSuchElementException(); 
/*  948 */       this.curr = this.prev;
/*  949 */       this.prev = (int)(DoubleLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  950 */       this.next = this.curr;
/*  951 */       if (this.index >= 0)
/*  952 */         this.index--; 
/*  953 */       return DoubleLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  956 */       if (this.index >= 0)
/*      */         return; 
/*  958 */       if (this.prev == -1) {
/*  959 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  962 */       if (this.next == -1) {
/*  963 */         this.index = DoubleLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  966 */       int pos = DoubleLinkedOpenCustomHashSet.this.first;
/*  967 */       this.index = 1;
/*  968 */       while (pos != this.prev) {
/*  969 */         pos = (int)DoubleLinkedOpenCustomHashSet.this.link[pos];
/*  970 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  975 */       ensureIndexKnown();
/*  976 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  980 */       ensureIndexKnown();
/*  981 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  985 */       ensureIndexKnown();
/*  986 */       if (this.curr == -1)
/*  987 */         throw new IllegalStateException(); 
/*  988 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  993 */         this.index--;
/*  994 */         this.prev = (int)(DoubleLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  996 */         this.next = (int)DoubleLinkedOpenCustomHashSet.this.link[this.curr];
/*  997 */       }  DoubleLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1002 */       if (this.prev == -1) {
/* 1003 */         DoubleLinkedOpenCustomHashSet.this.first = this.next;
/*      */       } else {
/* 1005 */         DoubleLinkedOpenCustomHashSet.this.link[this.prev] = DoubleLinkedOpenCustomHashSet.this.link[this.prev] ^ (DoubleLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1006 */       }  if (this.next == -1) {
/* 1007 */         DoubleLinkedOpenCustomHashSet.this.last = this.prev;
/*      */       } else {
/* 1009 */         DoubleLinkedOpenCustomHashSet.this.link[this.next] = DoubleLinkedOpenCustomHashSet.this.link[this.next] ^ (DoubleLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1010 */       }  int pos = this.curr;
/* 1011 */       this.curr = -1;
/* 1012 */       if (pos == DoubleLinkedOpenCustomHashSet.this.n) {
/* 1013 */         DoubleLinkedOpenCustomHashSet.this.containsNull = false;
/* 1014 */         DoubleLinkedOpenCustomHashSet.this.key[DoubleLinkedOpenCustomHashSet.this.n] = 0.0D;
/*      */       } else {
/*      */         
/* 1017 */         double[] key = DoubleLinkedOpenCustomHashSet.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1021 */           pos = (last = pos) + 1 & DoubleLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/* 1023 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1024 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1027 */             int slot = HashCommon.mix(DoubleLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & DoubleLinkedOpenCustomHashSet.this.mask;
/* 1028 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1030 */             pos = pos + 1 & DoubleLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/* 1032 */           key[last] = curr;
/* 1033 */           if (this.next == pos)
/* 1034 */             this.next = last; 
/* 1035 */           if (this.prev == pos)
/* 1036 */             this.prev = last; 
/* 1037 */           DoubleLinkedOpenCustomHashSet.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleListIterator iterator(double from) {
/* 1055 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleListIterator iterator() {
/* 1066 */     return new SetIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean trim() {
/* 1083 */     return trim(this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean trim(int n) {
/* 1107 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1108 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1109 */       return true; 
/*      */     try {
/* 1111 */       rehash(l);
/* 1112 */     } catch (OutOfMemoryError cantDoIt) {
/* 1113 */       return false;
/*      */     } 
/* 1115 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void rehash(int newN) {
/* 1131 */     double[] key = this.key;
/* 1132 */     int mask = newN - 1;
/* 1133 */     double[] newKey = new double[newN + 1];
/* 1134 */     int i = this.first, prev = -1, newPrev = -1;
/* 1135 */     long[] link = this.link;
/* 1136 */     long[] newLink = new long[newN + 1];
/* 1137 */     this.first = -1;
/* 1138 */     for (int j = this.size; j-- != 0; ) {
/* 1139 */       int pos; if (this.strategy.equals(key[i], 0.0D)) {
/* 1140 */         pos = newN;
/*      */       } else {
/* 1142 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1143 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1144 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1146 */       newKey[pos] = key[i];
/* 1147 */       if (prev != -1) {
/* 1148 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1149 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1150 */         newPrev = pos;
/*      */       } else {
/* 1152 */         newPrev = this.first = pos;
/*      */         
/* 1154 */         newLink[pos] = -1L;
/*      */       } 
/* 1156 */       int t = i;
/* 1157 */       i = (int)link[i];
/* 1158 */       prev = t;
/*      */     } 
/* 1160 */     this.link = newLink;
/* 1161 */     this.last = newPrev;
/* 1162 */     if (newPrev != -1)
/*      */     {
/* 1164 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1165 */     this.n = newN;
/* 1166 */     this.mask = mask;
/* 1167 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1168 */     this.key = newKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleLinkedOpenCustomHashSet clone() {
/*      */     DoubleLinkedOpenCustomHashSet c;
/*      */     try {
/* 1185 */       c = (DoubleLinkedOpenCustomHashSet)super.clone();
/* 1186 */     } catch (CloneNotSupportedException cantHappen) {
/* 1187 */       throw new InternalError();
/*      */     } 
/* 1189 */     c.key = (double[])this.key.clone();
/* 1190 */     c.containsNull = this.containsNull;
/* 1191 */     c.link = (long[])this.link.clone();
/* 1192 */     c.strategy = this.strategy;
/* 1193 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1206 */     int h = 0;
/* 1207 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1208 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1209 */         i++; 
/* 1210 */       h += this.strategy.hashCode(this.key[i]);
/* 1211 */       i++;
/*      */     } 
/*      */     
/* 1214 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1217 */     DoubleIterator i = iterator();
/* 1218 */     s.defaultWriteObject();
/* 1219 */     for (int j = this.size; j-- != 0;)
/* 1220 */       s.writeDouble(i.nextDouble()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1224 */     s.defaultReadObject();
/* 1225 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1226 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1227 */     this.mask = this.n - 1;
/* 1228 */     double[] key = this.key = new double[this.n + 1];
/* 1229 */     long[] link = this.link = new long[this.n + 1];
/* 1230 */     int prev = -1;
/* 1231 */     this.first = this.last = -1;
/*      */     
/* 1233 */     for (int i = this.size; i-- != 0; ) {
/* 1234 */       int pos; double k = s.readDouble();
/* 1235 */       if (this.strategy.equals(k, 0.0D)) {
/* 1236 */         pos = this.n;
/* 1237 */         this.containsNull = true;
/*      */       }
/* 1239 */       else if (Double.doubleToLongBits(key[
/* 1240 */             pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/* 1241 */         while (Double.doubleToLongBits(key[pos = pos + 1 & this.mask]) != 0L);
/*      */       } 
/* 1243 */       key[pos] = k;
/* 1244 */       if (this.first != -1) {
/* 1245 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1246 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1247 */         prev = pos; continue;
/*      */       } 
/* 1249 */       prev = this.first = pos;
/*      */       
/* 1251 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1254 */     this.last = prev;
/* 1255 */     if (prev != -1)
/*      */     {
/* 1257 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\DoubleLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */