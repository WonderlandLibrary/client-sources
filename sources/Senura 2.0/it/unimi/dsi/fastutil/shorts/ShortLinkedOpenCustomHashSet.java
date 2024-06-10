/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ public class ShortLinkedOpenCustomHashSet
/*      */   extends AbstractShortSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected ShortHash.Strategy strategy;
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
/*      */   public ShortLinkedOpenCustomHashSet(int expected, float f, ShortHash.Strategy strategy) {
/*  144 */     this.strategy = strategy;
/*  145 */     if (f <= 0.0F || f > 1.0F)
/*  146 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  147 */     if (expected < 0)
/*  148 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  149 */     this.f = f;
/*  150 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  151 */     this.mask = this.n - 1;
/*  152 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  153 */     this.key = new short[this.n + 1];
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
/*      */   public ShortLinkedOpenCustomHashSet(int expected, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(Collection<? extends Short> c, float f, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(Collection<? extends Short> c, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(ShortCollection c, float f, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(ShortCollection c, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(ShortIterator i, float f, ShortHash.Strategy strategy) {
/*  247 */     this(16, f, strategy);
/*  248 */     while (i.hasNext()) {
/*  249 */       add(i.nextShort());
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
/*      */   public ShortLinkedOpenCustomHashSet(ShortIterator i, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(Iterator<?> i, float f, ShortHash.Strategy strategy) {
/*  276 */     this(ShortIterators.asShortIterator(i), f, strategy);
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
/*      */   public ShortLinkedOpenCustomHashSet(Iterator<?> i, ShortHash.Strategy strategy) {
/*  289 */     this(ShortIterators.asShortIterator(i), strategy);
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
/*      */   public ShortLinkedOpenCustomHashSet(short[] a, int offset, int length, float f, ShortHash.Strategy strategy) {
/*  307 */     this((length < 0) ? 0 : length, f, strategy);
/*  308 */     ShortArrays.ensureOffsetLength(a, offset, length);
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
/*      */   public ShortLinkedOpenCustomHashSet(short[] a, int offset, int length, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(short[] a, float f, ShortHash.Strategy strategy) {
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
/*      */   public ShortLinkedOpenCustomHashSet(short[] a, ShortHash.Strategy strategy) {
/*  354 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortHash.Strategy strategy() {
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
/*      */   public boolean addAll(ShortCollection c) {
/*  380 */     if (this.f <= 0.5D) {
/*  381 */       ensureCapacity(c.size());
/*      */     } else {
/*  383 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  385 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Short> c) {
/*  390 */     if (this.f <= 0.5D) {
/*  391 */       ensureCapacity(c.size());
/*      */     } else {
/*  393 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  395 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(short k) {
/*      */     int pos;
/*  400 */     if (this.strategy.equals(k, (short)0)) {
/*  401 */       if (this.containsNull)
/*  402 */         return false; 
/*  403 */       pos = this.n;
/*  404 */       this.containsNull = true;
/*  405 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  408 */       short[] key = this.key;
/*      */       short curr;
/*  410 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*      */         
/*  412 */         if (this.strategy.equals(curr, k))
/*  413 */           return false; 
/*  414 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*  446 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  448 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  450 */         if ((curr = key[pos]) == 0) {
/*  451 */           key[last] = 0;
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
/*  473 */     this.key[this.n] = 0;
/*  474 */     this.size--;
/*  475 */     fixPointers(this.n);
/*  476 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  477 */       rehash(this.n / 2); 
/*  478 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(short k) {
/*  483 */     if (this.strategy.equals(k, (short)0)) {
/*  484 */       if (this.containsNull)
/*  485 */         return removeNullEntry(); 
/*  486 */       return false;
/*      */     } 
/*      */     
/*  489 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  492 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  493 */       return false; 
/*  494 */     if (this.strategy.equals(k, curr))
/*  495 */       return removeEntry(pos); 
/*      */     while (true) {
/*  497 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  498 */         return false; 
/*  499 */       if (this.strategy.equals(k, curr)) {
/*  500 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(short k) {
/*  506 */     if (this.strategy.equals(k, (short)0)) {
/*  507 */       return this.containsNull;
/*      */     }
/*  509 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  512 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  513 */       return false; 
/*  514 */     if (this.strategy.equals(k, curr))
/*  515 */       return true; 
/*      */     while (true) {
/*  517 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  518 */         return false; 
/*  519 */       if (this.strategy.equals(k, curr)) {
/*  520 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeFirstShort() {
/*  531 */     if (this.size == 0)
/*  532 */       throw new NoSuchElementException(); 
/*  533 */     int pos = this.first;
/*      */     
/*  535 */     this.first = (int)this.link[pos];
/*  536 */     if (0 <= this.first)
/*      */     {
/*  538 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  540 */     short k = this.key[pos];
/*  541 */     this.size--;
/*  542 */     if (this.strategy.equals(k, (short)0)) {
/*  543 */       this.containsNull = false;
/*  544 */       this.key[this.n] = 0;
/*      */     } else {
/*  546 */       shiftKeys(pos);
/*  547 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  548 */       rehash(this.n / 2); 
/*  549 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short removeLastShort() {
/*  559 */     if (this.size == 0)
/*  560 */       throw new NoSuchElementException(); 
/*  561 */     int pos = this.last;
/*      */     
/*  563 */     this.last = (int)(this.link[pos] >>> 32L);
/*  564 */     if (0 <= this.last)
/*      */     {
/*  566 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  568 */     short k = this.key[pos];
/*  569 */     this.size--;
/*  570 */     if (this.strategy.equals(k, (short)0)) {
/*  571 */       this.containsNull = false;
/*  572 */       this.key[this.n] = 0;
/*      */     } else {
/*  574 */       shiftKeys(pos);
/*  575 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  576 */       rehash(this.n / 2); 
/*  577 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  580 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  582 */     if (this.last == i) {
/*  583 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  585 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  587 */       long linki = this.link[i];
/*  588 */       int prev = (int)(linki >>> 32L);
/*  589 */       int next = (int)linki;
/*  590 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  591 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  593 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  594 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  595 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  598 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  600 */     if (this.first == i) {
/*  601 */       this.first = (int)this.link[i];
/*      */       
/*  603 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  605 */       long linki = this.link[i];
/*  606 */       int prev = (int)(linki >>> 32L);
/*  607 */       int next = (int)linki;
/*  608 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  609 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  611 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  612 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  613 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(short k) {
/*      */     int pos;
/*  625 */     if (this.strategy.equals(k, (short)0)) {
/*  626 */       if (this.containsNull) {
/*  627 */         moveIndexToFirst(this.n);
/*  628 */         return false;
/*      */       } 
/*  630 */       this.containsNull = true;
/*  631 */       pos = this.n;
/*      */     } else {
/*      */       
/*  634 */       short[] key = this.key;
/*  635 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  637 */       while (key[pos] != 0) {
/*  638 */         if (this.strategy.equals(k, key[pos])) {
/*  639 */           moveIndexToFirst(pos);
/*  640 */           return false;
/*      */         } 
/*  642 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  645 */     this.key[pos] = k;
/*  646 */     if (this.size == 0) {
/*  647 */       this.first = this.last = pos;
/*      */       
/*  649 */       this.link[pos] = -1L;
/*      */     } else {
/*  651 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  652 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  653 */       this.first = pos;
/*      */     } 
/*  655 */     if (this.size++ >= this.maxFill) {
/*  656 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  659 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(short k) {
/*      */     int pos;
/*  671 */     if (this.strategy.equals(k, (short)0)) {
/*  672 */       if (this.containsNull) {
/*  673 */         moveIndexToLast(this.n);
/*  674 */         return false;
/*      */       } 
/*  676 */       this.containsNull = true;
/*  677 */       pos = this.n;
/*      */     } else {
/*      */       
/*  680 */       short[] key = this.key;
/*  681 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  683 */       while (key[pos] != 0) {
/*  684 */         if (this.strategy.equals(k, key[pos])) {
/*  685 */           moveIndexToLast(pos);
/*  686 */           return false;
/*      */         } 
/*  688 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  691 */     this.key[pos] = k;
/*  692 */     if (this.size == 0) {
/*  693 */       this.first = this.last = pos;
/*      */       
/*  695 */       this.link[pos] = -1L;
/*      */     } else {
/*  697 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  698 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  699 */       this.last = pos;
/*      */     } 
/*  701 */     if (this.size++ >= this.maxFill) {
/*  702 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  705 */     return true;
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
/*  716 */     if (this.size == 0)
/*      */       return; 
/*  718 */     this.size = 0;
/*  719 */     this.containsNull = false;
/*  720 */     Arrays.fill(this.key, (short)0);
/*  721 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  725 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  729 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  739 */     if (this.size == 0) {
/*  740 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  743 */     if (this.first == i) {
/*  744 */       this.first = (int)this.link[i];
/*  745 */       if (0 <= this.first)
/*      */       {
/*  747 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  751 */     if (this.last == i) {
/*  752 */       this.last = (int)(this.link[i] >>> 32L);
/*  753 */       if (0 <= this.last)
/*      */       {
/*  755 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  759 */     long linki = this.link[i];
/*  760 */     int prev = (int)(linki >>> 32L);
/*  761 */     int next = (int)linki;
/*  762 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  763 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  775 */     if (this.size == 1) {
/*  776 */       this.first = this.last = d;
/*      */       
/*  778 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  781 */     if (this.first == s) {
/*  782 */       this.first = d;
/*  783 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  784 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  787 */     if (this.last == s) {
/*  788 */       this.last = d;
/*  789 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  790 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  793 */     long links = this.link[s];
/*  794 */     int prev = (int)(links >>> 32L);
/*  795 */     int next = (int)links;
/*  796 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  797 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  798 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short firstShort() {
/*  807 */     if (this.size == 0)
/*  808 */       throw new NoSuchElementException(); 
/*  809 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short lastShort() {
/*  818 */     if (this.size == 0)
/*  819 */       throw new NoSuchElementException(); 
/*  820 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortSortedSet tailSet(short from) {
/*  829 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortSortedSet headSet(short to) {
/*  838 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortSortedSet subSet(short from, short to) {
/*  847 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortComparator comparator() {
/*  856 */     return null;
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
/*      */     implements ShortListIterator
/*      */   {
/*  871 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  882 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  887 */     int index = -1;
/*      */     SetIterator() {
/*  889 */       this.next = ShortLinkedOpenCustomHashSet.this.first;
/*  890 */       this.index = 0;
/*      */     }
/*      */     SetIterator(short from) {
/*  893 */       if (ShortLinkedOpenCustomHashSet.this.strategy.equals(from, (short)0)) {
/*  894 */         if (ShortLinkedOpenCustomHashSet.this.containsNull) {
/*  895 */           this.next = (int)ShortLinkedOpenCustomHashSet.this.link[ShortLinkedOpenCustomHashSet.this.n];
/*  896 */           this.prev = ShortLinkedOpenCustomHashSet.this.n;
/*      */           return;
/*      */         } 
/*  899 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  901 */       if (ShortLinkedOpenCustomHashSet.this.strategy.equals(ShortLinkedOpenCustomHashSet.this.key[ShortLinkedOpenCustomHashSet.this.last], from)) {
/*  902 */         this.prev = ShortLinkedOpenCustomHashSet.this.last;
/*  903 */         this.index = ShortLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  907 */       short[] key = ShortLinkedOpenCustomHashSet.this.key;
/*  908 */       int pos = HashCommon.mix(ShortLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & ShortLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  910 */       while (key[pos] != 0) {
/*  911 */         if (ShortLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  913 */           this.next = (int)ShortLinkedOpenCustomHashSet.this.link[pos];
/*  914 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  917 */         pos = pos + 1 & ShortLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  919 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  923 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  927 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public short nextShort() {
/*  931 */       if (!hasNext())
/*  932 */         throw new NoSuchElementException(); 
/*  933 */       this.curr = this.next;
/*  934 */       this.next = (int)ShortLinkedOpenCustomHashSet.this.link[this.curr];
/*  935 */       this.prev = this.curr;
/*  936 */       if (this.index >= 0) {
/*  937 */         this.index++;
/*      */       }
/*      */       
/*  940 */       return ShortLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public short previousShort() {
/*  944 */       if (!hasPrevious())
/*  945 */         throw new NoSuchElementException(); 
/*  946 */       this.curr = this.prev;
/*  947 */       this.prev = (int)(ShortLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  948 */       this.next = this.curr;
/*  949 */       if (this.index >= 0)
/*  950 */         this.index--; 
/*  951 */       return ShortLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  954 */       if (this.index >= 0)
/*      */         return; 
/*  956 */       if (this.prev == -1) {
/*  957 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  960 */       if (this.next == -1) {
/*  961 */         this.index = ShortLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  964 */       int pos = ShortLinkedOpenCustomHashSet.this.first;
/*  965 */       this.index = 1;
/*  966 */       while (pos != this.prev) {
/*  967 */         pos = (int)ShortLinkedOpenCustomHashSet.this.link[pos];
/*  968 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  973 */       ensureIndexKnown();
/*  974 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  978 */       ensureIndexKnown();
/*  979 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  983 */       ensureIndexKnown();
/*  984 */       if (this.curr == -1)
/*  985 */         throw new IllegalStateException(); 
/*  986 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  991 */         this.index--;
/*  992 */         this.prev = (int)(ShortLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  994 */         this.next = (int)ShortLinkedOpenCustomHashSet.this.link[this.curr];
/*  995 */       }  ShortLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1000 */       if (this.prev == -1) {
/* 1001 */         ShortLinkedOpenCustomHashSet.this.first = this.next;
/*      */       } else {
/* 1003 */         ShortLinkedOpenCustomHashSet.this.link[this.prev] = ShortLinkedOpenCustomHashSet.this.link[this.prev] ^ (ShortLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1004 */       }  if (this.next == -1) {
/* 1005 */         ShortLinkedOpenCustomHashSet.this.last = this.prev;
/*      */       } else {
/* 1007 */         ShortLinkedOpenCustomHashSet.this.link[this.next] = ShortLinkedOpenCustomHashSet.this.link[this.next] ^ (ShortLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1008 */       }  int pos = this.curr;
/* 1009 */       this.curr = -1;
/* 1010 */       if (pos == ShortLinkedOpenCustomHashSet.this.n) {
/* 1011 */         ShortLinkedOpenCustomHashSet.this.containsNull = false;
/* 1012 */         ShortLinkedOpenCustomHashSet.this.key[ShortLinkedOpenCustomHashSet.this.n] = 0;
/*      */       } else {
/*      */         
/* 1015 */         short[] key = ShortLinkedOpenCustomHashSet.this.key;
/*      */         while (true) {
/*      */           short curr;
/*      */           int last;
/* 1019 */           pos = (last = pos) + 1 & ShortLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/* 1021 */             if ((curr = key[pos]) == 0) {
/* 1022 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1025 */             int slot = HashCommon.mix(ShortLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & ShortLinkedOpenCustomHashSet.this.mask;
/* 1026 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1028 */             pos = pos + 1 & ShortLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/* 1030 */           key[last] = curr;
/* 1031 */           if (this.next == pos)
/* 1032 */             this.next = last; 
/* 1033 */           if (this.prev == pos)
/* 1034 */             this.prev = last; 
/* 1035 */           ShortLinkedOpenCustomHashSet.this.fixPointers(pos, last);
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
/*      */   public ShortListIterator iterator(short from) {
/* 1053 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortListIterator iterator() {
/* 1064 */     return new SetIterator();
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
/* 1081 */     return trim(this.size);
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
/* 1105 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1106 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1107 */       return true; 
/*      */     try {
/* 1109 */       rehash(l);
/* 1110 */     } catch (OutOfMemoryError cantDoIt) {
/* 1111 */       return false;
/*      */     } 
/* 1113 */     return true;
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
/* 1129 */     short[] key = this.key;
/* 1130 */     int mask = newN - 1;
/* 1131 */     short[] newKey = new short[newN + 1];
/* 1132 */     int i = this.first, prev = -1, newPrev = -1;
/* 1133 */     long[] link = this.link;
/* 1134 */     long[] newLink = new long[newN + 1];
/* 1135 */     this.first = -1;
/* 1136 */     for (int j = this.size; j-- != 0; ) {
/* 1137 */       int pos; if (this.strategy.equals(key[i], (short)0)) {
/* 1138 */         pos = newN;
/*      */       } else {
/* 1140 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1141 */         while (newKey[pos] != 0)
/* 1142 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1144 */       newKey[pos] = key[i];
/* 1145 */       if (prev != -1) {
/* 1146 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1147 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1148 */         newPrev = pos;
/*      */       } else {
/* 1150 */         newPrev = this.first = pos;
/*      */         
/* 1152 */         newLink[pos] = -1L;
/*      */       } 
/* 1154 */       int t = i;
/* 1155 */       i = (int)link[i];
/* 1156 */       prev = t;
/*      */     } 
/* 1158 */     this.link = newLink;
/* 1159 */     this.last = newPrev;
/* 1160 */     if (newPrev != -1)
/*      */     {
/* 1162 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1163 */     this.n = newN;
/* 1164 */     this.mask = mask;
/* 1165 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1166 */     this.key = newKey;
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
/*      */   public ShortLinkedOpenCustomHashSet clone() {
/*      */     ShortLinkedOpenCustomHashSet c;
/*      */     try {
/* 1183 */       c = (ShortLinkedOpenCustomHashSet)super.clone();
/* 1184 */     } catch (CloneNotSupportedException cantHappen) {
/* 1185 */       throw new InternalError();
/*      */     } 
/* 1187 */     c.key = (short[])this.key.clone();
/* 1188 */     c.containsNull = this.containsNull;
/* 1189 */     c.link = (long[])this.link.clone();
/* 1190 */     c.strategy = this.strategy;
/* 1191 */     return c;
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
/* 1204 */     int h = 0;
/* 1205 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1206 */       while (this.key[i] == 0)
/* 1207 */         i++; 
/* 1208 */       h += this.strategy.hashCode(this.key[i]);
/* 1209 */       i++;
/*      */     } 
/*      */     
/* 1212 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1215 */     ShortIterator i = iterator();
/* 1216 */     s.defaultWriteObject();
/* 1217 */     for (int j = this.size; j-- != 0;)
/* 1218 */       s.writeShort(i.nextShort()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1222 */     s.defaultReadObject();
/* 1223 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1224 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1225 */     this.mask = this.n - 1;
/* 1226 */     short[] key = this.key = new short[this.n + 1];
/* 1227 */     long[] link = this.link = new long[this.n + 1];
/* 1228 */     int prev = -1;
/* 1229 */     this.first = this.last = -1;
/*      */     
/* 1231 */     for (int i = this.size; i-- != 0; ) {
/* 1232 */       int pos; short k = s.readShort();
/* 1233 */       if (this.strategy.equals(k, (short)0)) {
/* 1234 */         pos = this.n;
/* 1235 */         this.containsNull = true;
/*      */       }
/* 1237 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) {
/* 1238 */         while (key[pos = pos + 1 & this.mask] != 0);
/*      */       } 
/* 1240 */       key[pos] = k;
/* 1241 */       if (this.first != -1) {
/* 1242 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1243 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1244 */         prev = pos; continue;
/*      */       } 
/* 1246 */       prev = this.first = pos;
/*      */       
/* 1248 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1251 */     this.last = prev;
/* 1252 */     if (prev != -1)
/*      */     {
/* 1254 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\ShortLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */