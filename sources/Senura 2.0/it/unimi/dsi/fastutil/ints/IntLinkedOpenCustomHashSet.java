/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class IntLinkedOpenCustomHashSet
/*      */   extends AbstractIntSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected IntHash.Strategy strategy;
/*   94 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   protected transient int last = -1;
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
/*      */   public IntLinkedOpenCustomHashSet(int expected, float f, IntHash.Strategy strategy) {
/*  140 */     this.strategy = strategy;
/*  141 */     if (f <= 0.0F || f > 1.0F)
/*  142 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  143 */     if (expected < 0)
/*  144 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  145 */     this.f = f;
/*  146 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  147 */     this.mask = this.n - 1;
/*  148 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  149 */     this.key = new int[this.n + 1];
/*  150 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(int expected, IntHash.Strategy strategy) {
/*  161 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntLinkedOpenCustomHashSet(IntHash.Strategy strategy) {
/*  172 */     this(16, 0.75F, strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(Collection<? extends Integer> c, float f, IntHash.Strategy strategy) {
/*  186 */     this(c.size(), f, strategy);
/*  187 */     addAll(c);
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
/*      */   public IntLinkedOpenCustomHashSet(Collection<? extends Integer> c, IntHash.Strategy strategy) {
/*  200 */     this(c, 0.75F, strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(IntCollection c, float f, IntHash.Strategy strategy) {
/*  214 */     this(c.size(), f, strategy);
/*  215 */     addAll(c);
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
/*      */   public IntLinkedOpenCustomHashSet(IntCollection c, IntHash.Strategy strategy) {
/*  228 */     this(c, 0.75F, strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(IntIterator i, float f, IntHash.Strategy strategy) {
/*  242 */     this(16, f, strategy);
/*  243 */     while (i.hasNext()) {
/*  244 */       add(i.nextInt());
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
/*      */   public IntLinkedOpenCustomHashSet(IntIterator i, IntHash.Strategy strategy) {
/*  256 */     this(i, 0.75F, strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(Iterator<?> i, float f, IntHash.Strategy strategy) {
/*  270 */     this(IntIterators.asIntIterator(i), f, strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(Iterator<?> i, IntHash.Strategy strategy) {
/*  282 */     this(IntIterators.asIntIterator(i), strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(int[] a, int offset, int length, float f, IntHash.Strategy strategy) {
/*  300 */     this((length < 0) ? 0 : length, f, strategy);
/*  301 */     IntArrays.ensureOffsetLength(a, offset, length);
/*  302 */     for (int i = 0; i < length; i++) {
/*  303 */       add(a[offset + i]);
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
/*      */   public IntLinkedOpenCustomHashSet(int[] a, int offset, int length, IntHash.Strategy strategy) {
/*  320 */     this(a, offset, length, 0.75F, strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(int[] a, float f, IntHash.Strategy strategy) {
/*  334 */     this(a, 0, a.length, f, strategy);
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
/*      */   public IntLinkedOpenCustomHashSet(int[] a, IntHash.Strategy strategy) {
/*  346 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntHash.Strategy strategy() {
/*  354 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  357 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  360 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  361 */     if (needed > this.n)
/*  362 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  365 */     int needed = (int)Math.min(1073741824L, 
/*  366 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  367 */     if (needed > this.n)
/*  368 */       rehash(needed); 
/*      */   }
/*      */   
/*      */   public boolean addAll(IntCollection c) {
/*  372 */     if (this.f <= 0.5D) {
/*  373 */       ensureCapacity(c.size());
/*      */     } else {
/*  375 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  377 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Integer> c) {
/*  382 */     if (this.f <= 0.5D) {
/*  383 */       ensureCapacity(c.size());
/*      */     } else {
/*  385 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  387 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(int k) {
/*      */     int pos;
/*  392 */     if (this.strategy.equals(k, 0)) {
/*  393 */       if (this.containsNull)
/*  394 */         return false; 
/*  395 */       pos = this.n;
/*  396 */       this.containsNull = true;
/*  397 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  400 */       int[] key = this.key;
/*      */       int curr;
/*  402 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0) {
/*  403 */         if (this.strategy.equals(curr, k))
/*  404 */           return false; 
/*  405 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  406 */           if (this.strategy.equals(curr, k))
/*  407 */             return false; 
/*      */         } 
/*  409 */       }  key[pos] = k;
/*      */     } 
/*  411 */     if (this.size == 0) {
/*  412 */       this.first = this.last = pos;
/*      */       
/*  414 */       this.link[pos] = -1L;
/*      */     } else {
/*  416 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  417 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  418 */       this.last = pos;
/*      */     } 
/*  420 */     if (this.size++ >= this.maxFill) {
/*  421 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  424 */     return true;
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
/*  437 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
/*  439 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  441 */         if ((curr = key[pos]) == 0) {
/*  442 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  445 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  446 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  448 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  450 */       key[last] = curr;
/*  451 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  455 */     this.size--;
/*  456 */     fixPointers(pos);
/*  457 */     shiftKeys(pos);
/*  458 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  459 */       rehash(this.n / 2); 
/*  460 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  463 */     this.containsNull = false;
/*  464 */     this.key[this.n] = 0;
/*  465 */     this.size--;
/*  466 */     fixPointers(this.n);
/*  467 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  468 */       rehash(this.n / 2); 
/*  469 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(int k) {
/*  474 */     if (this.strategy.equals(k, 0)) {
/*  475 */       if (this.containsNull)
/*  476 */         return removeNullEntry(); 
/*  477 */       return false;
/*      */     } 
/*      */     
/*  480 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  483 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  484 */       return false; 
/*  485 */     if (this.strategy.equals(k, curr))
/*  486 */       return removeEntry(pos); 
/*      */     while (true) {
/*  488 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  489 */         return false; 
/*  490 */       if (this.strategy.equals(k, curr)) {
/*  491 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(int k) {
/*  497 */     if (this.strategy.equals(k, 0)) {
/*  498 */       return this.containsNull;
/*      */     }
/*  500 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
/*  503 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0)
/*  504 */       return false; 
/*  505 */     if (this.strategy.equals(k, curr))
/*  506 */       return true; 
/*      */     while (true) {
/*  508 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  509 */         return false; 
/*  510 */       if (this.strategy.equals(k, curr)) {
/*  511 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeFirstInt() {
/*  522 */     if (this.size == 0)
/*  523 */       throw new NoSuchElementException(); 
/*  524 */     int pos = this.first;
/*      */     
/*  526 */     this.first = (int)this.link[pos];
/*  527 */     if (0 <= this.first)
/*      */     {
/*  529 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  531 */     int k = this.key[pos];
/*  532 */     this.size--;
/*  533 */     if (this.strategy.equals(k, 0)) {
/*  534 */       this.containsNull = false;
/*  535 */       this.key[this.n] = 0;
/*      */     } else {
/*  537 */       shiftKeys(pos);
/*  538 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  539 */       rehash(this.n / 2); 
/*  540 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int removeLastInt() {
/*  550 */     if (this.size == 0)
/*  551 */       throw new NoSuchElementException(); 
/*  552 */     int pos = this.last;
/*      */     
/*  554 */     this.last = (int)(this.link[pos] >>> 32L);
/*  555 */     if (0 <= this.last)
/*      */     {
/*  557 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  559 */     int k = this.key[pos];
/*  560 */     this.size--;
/*  561 */     if (this.strategy.equals(k, 0)) {
/*  562 */       this.containsNull = false;
/*  563 */       this.key[this.n] = 0;
/*      */     } else {
/*  565 */       shiftKeys(pos);
/*  566 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  567 */       rehash(this.n / 2); 
/*  568 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  571 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  573 */     if (this.last == i) {
/*  574 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  576 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  578 */       long linki = this.link[i];
/*  579 */       int prev = (int)(linki >>> 32L);
/*  580 */       int next = (int)linki;
/*  581 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  582 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  584 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  585 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  586 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  589 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  591 */     if (this.first == i) {
/*  592 */       this.first = (int)this.link[i];
/*      */       
/*  594 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  596 */       long linki = this.link[i];
/*  597 */       int prev = (int)(linki >>> 32L);
/*  598 */       int next = (int)linki;
/*  599 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  600 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  602 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  603 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  604 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(int k) {
/*      */     int pos;
/*  616 */     if (this.strategy.equals(k, 0)) {
/*  617 */       if (this.containsNull) {
/*  618 */         moveIndexToFirst(this.n);
/*  619 */         return false;
/*      */       } 
/*  621 */       this.containsNull = true;
/*  622 */       pos = this.n;
/*      */     } else {
/*      */       
/*  625 */       int[] key = this.key;
/*  626 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  628 */       while (key[pos] != 0) {
/*  629 */         if (this.strategy.equals(k, key[pos])) {
/*  630 */           moveIndexToFirst(pos);
/*  631 */           return false;
/*      */         } 
/*  633 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  636 */     this.key[pos] = k;
/*  637 */     if (this.size == 0) {
/*  638 */       this.first = this.last = pos;
/*      */       
/*  640 */       this.link[pos] = -1L;
/*      */     } else {
/*  642 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  643 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  644 */       this.first = pos;
/*      */     } 
/*  646 */     if (this.size++ >= this.maxFill) {
/*  647 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  650 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(int k) {
/*      */     int pos;
/*  662 */     if (this.strategy.equals(k, 0)) {
/*  663 */       if (this.containsNull) {
/*  664 */         moveIndexToLast(this.n);
/*  665 */         return false;
/*      */       } 
/*  667 */       this.containsNull = true;
/*  668 */       pos = this.n;
/*      */     } else {
/*      */       
/*  671 */       int[] key = this.key;
/*  672 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  674 */       while (key[pos] != 0) {
/*  675 */         if (this.strategy.equals(k, key[pos])) {
/*  676 */           moveIndexToLast(pos);
/*  677 */           return false;
/*      */         } 
/*  679 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  682 */     this.key[pos] = k;
/*  683 */     if (this.size == 0) {
/*  684 */       this.first = this.last = pos;
/*      */       
/*  686 */       this.link[pos] = -1L;
/*      */     } else {
/*  688 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  689 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  690 */       this.last = pos;
/*      */     } 
/*  692 */     if (this.size++ >= this.maxFill) {
/*  693 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  696 */     return true;
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
/*  707 */     if (this.size == 0)
/*      */       return; 
/*  709 */     this.size = 0;
/*  710 */     this.containsNull = false;
/*  711 */     Arrays.fill(this.key, 0);
/*  712 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  716 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  720 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  730 */     if (this.size == 0) {
/*  731 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  734 */     if (this.first == i) {
/*  735 */       this.first = (int)this.link[i];
/*  736 */       if (0 <= this.first)
/*      */       {
/*  738 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  742 */     if (this.last == i) {
/*  743 */       this.last = (int)(this.link[i] >>> 32L);
/*  744 */       if (0 <= this.last)
/*      */       {
/*  746 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  750 */     long linki = this.link[i];
/*  751 */     int prev = (int)(linki >>> 32L);
/*  752 */     int next = (int)linki;
/*  753 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  754 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  766 */     if (this.size == 1) {
/*  767 */       this.first = this.last = d;
/*      */       
/*  769 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  772 */     if (this.first == s) {
/*  773 */       this.first = d;
/*  774 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  775 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  778 */     if (this.last == s) {
/*  779 */       this.last = d;
/*  780 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  781 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  784 */     long links = this.link[s];
/*  785 */     int prev = (int)(links >>> 32L);
/*  786 */     int next = (int)links;
/*  787 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  788 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  789 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int firstInt() {
/*  798 */     if (this.size == 0)
/*  799 */       throw new NoSuchElementException(); 
/*  800 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastInt() {
/*  809 */     if (this.size == 0)
/*  810 */       throw new NoSuchElementException(); 
/*  811 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet tailSet(int from) {
/*  820 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet headSet(int to) {
/*  829 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntSortedSet subSet(int from, int to) {
/*  838 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
/*  847 */     return null;
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
/*      */     implements IntListIterator
/*      */   {
/*  862 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  873 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  878 */     int index = -1;
/*      */     SetIterator() {
/*  880 */       this.next = IntLinkedOpenCustomHashSet.this.first;
/*  881 */       this.index = 0;
/*      */     }
/*      */     SetIterator(int from) {
/*  884 */       if (IntLinkedOpenCustomHashSet.this.strategy.equals(from, 0)) {
/*  885 */         if (IntLinkedOpenCustomHashSet.this.containsNull) {
/*  886 */           this.next = (int)IntLinkedOpenCustomHashSet.this.link[IntLinkedOpenCustomHashSet.this.n];
/*  887 */           this.prev = IntLinkedOpenCustomHashSet.this.n;
/*      */           return;
/*      */         } 
/*  890 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  892 */       if (IntLinkedOpenCustomHashSet.this.strategy.equals(IntLinkedOpenCustomHashSet.this.key[IntLinkedOpenCustomHashSet.this.last], from)) {
/*  893 */         this.prev = IntLinkedOpenCustomHashSet.this.last;
/*  894 */         this.index = IntLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  898 */       int[] key = IntLinkedOpenCustomHashSet.this.key;
/*  899 */       int pos = HashCommon.mix(IntLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & IntLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  901 */       while (key[pos] != 0) {
/*  902 */         if (IntLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  904 */           this.next = (int)IntLinkedOpenCustomHashSet.this.link[pos];
/*  905 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  908 */         pos = pos + 1 & IntLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  910 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  914 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  918 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public int nextInt() {
/*  922 */       if (!hasNext())
/*  923 */         throw new NoSuchElementException(); 
/*  924 */       this.curr = this.next;
/*  925 */       this.next = (int)IntLinkedOpenCustomHashSet.this.link[this.curr];
/*  926 */       this.prev = this.curr;
/*  927 */       if (this.index >= 0) {
/*  928 */         this.index++;
/*      */       }
/*      */       
/*  931 */       return IntLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public int previousInt() {
/*  935 */       if (!hasPrevious())
/*  936 */         throw new NoSuchElementException(); 
/*  937 */       this.curr = this.prev;
/*  938 */       this.prev = (int)(IntLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  939 */       this.next = this.curr;
/*  940 */       if (this.index >= 0)
/*  941 */         this.index--; 
/*  942 */       return IntLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  945 */       if (this.index >= 0)
/*      */         return; 
/*  947 */       if (this.prev == -1) {
/*  948 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  951 */       if (this.next == -1) {
/*  952 */         this.index = IntLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  955 */       int pos = IntLinkedOpenCustomHashSet.this.first;
/*  956 */       this.index = 1;
/*  957 */       while (pos != this.prev) {
/*  958 */         pos = (int)IntLinkedOpenCustomHashSet.this.link[pos];
/*  959 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  964 */       ensureIndexKnown();
/*  965 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  969 */       ensureIndexKnown();
/*  970 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  974 */       ensureIndexKnown();
/*  975 */       if (this.curr == -1)
/*  976 */         throw new IllegalStateException(); 
/*  977 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  982 */         this.index--;
/*  983 */         this.prev = (int)(IntLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  985 */         this.next = (int)IntLinkedOpenCustomHashSet.this.link[this.curr];
/*  986 */       }  IntLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  991 */       if (this.prev == -1) {
/*  992 */         IntLinkedOpenCustomHashSet.this.first = this.next;
/*      */       } else {
/*  994 */         IntLinkedOpenCustomHashSet.this.link[this.prev] = IntLinkedOpenCustomHashSet.this.link[this.prev] ^ (IntLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  995 */       }  if (this.next == -1) {
/*  996 */         IntLinkedOpenCustomHashSet.this.last = this.prev;
/*      */       } else {
/*  998 */         IntLinkedOpenCustomHashSet.this.link[this.next] = IntLinkedOpenCustomHashSet.this.link[this.next] ^ (IntLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  999 */       }  int pos = this.curr;
/* 1000 */       this.curr = -1;
/* 1001 */       if (pos == IntLinkedOpenCustomHashSet.this.n) {
/* 1002 */         IntLinkedOpenCustomHashSet.this.containsNull = false;
/* 1003 */         IntLinkedOpenCustomHashSet.this.key[IntLinkedOpenCustomHashSet.this.n] = 0;
/*      */       } else {
/*      */         
/* 1006 */         int[] key = IntLinkedOpenCustomHashSet.this.key;
/*      */         
/*      */         while (true) {
/*      */           int curr, last;
/* 1010 */           pos = (last = pos) + 1 & IntLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/* 1012 */             if ((curr = key[pos]) == 0) {
/* 1013 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1016 */             int slot = HashCommon.mix(IntLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & IntLinkedOpenCustomHashSet.this.mask;
/* 1017 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1019 */             pos = pos + 1 & IntLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/* 1021 */           key[last] = curr;
/* 1022 */           if (this.next == pos)
/* 1023 */             this.next = last; 
/* 1024 */           if (this.prev == pos)
/* 1025 */             this.prev = last; 
/* 1026 */           IntLinkedOpenCustomHashSet.this.fixPointers(pos, last);
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
/*      */   public IntListIterator iterator(int from) {
/* 1044 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntListIterator iterator() {
/* 1055 */     return new SetIterator();
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
/* 1072 */     return trim(this.size);
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
/* 1096 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1097 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1098 */       return true; 
/*      */     try {
/* 1100 */       rehash(l);
/* 1101 */     } catch (OutOfMemoryError cantDoIt) {
/* 1102 */       return false;
/*      */     } 
/* 1104 */     return true;
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
/* 1120 */     int[] key = this.key;
/* 1121 */     int mask = newN - 1;
/* 1122 */     int[] newKey = new int[newN + 1];
/* 1123 */     int i = this.first, prev = -1, newPrev = -1;
/* 1124 */     long[] link = this.link;
/* 1125 */     long[] newLink = new long[newN + 1];
/* 1126 */     this.first = -1;
/* 1127 */     for (int j = this.size; j-- != 0; ) {
/* 1128 */       int pos; if (this.strategy.equals(key[i], 0)) {
/* 1129 */         pos = newN;
/*      */       } else {
/* 1131 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1132 */         while (newKey[pos] != 0)
/* 1133 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1135 */       newKey[pos] = key[i];
/* 1136 */       if (prev != -1) {
/* 1137 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1138 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1139 */         newPrev = pos;
/*      */       } else {
/* 1141 */         newPrev = this.first = pos;
/*      */         
/* 1143 */         newLink[pos] = -1L;
/*      */       } 
/* 1145 */       int t = i;
/* 1146 */       i = (int)link[i];
/* 1147 */       prev = t;
/*      */     } 
/* 1149 */     this.link = newLink;
/* 1150 */     this.last = newPrev;
/* 1151 */     if (newPrev != -1)
/*      */     {
/* 1153 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1154 */     this.n = newN;
/* 1155 */     this.mask = mask;
/* 1156 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1157 */     this.key = newKey;
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
/*      */   public IntLinkedOpenCustomHashSet clone() {
/*      */     IntLinkedOpenCustomHashSet c;
/*      */     try {
/* 1174 */       c = (IntLinkedOpenCustomHashSet)super.clone();
/* 1175 */     } catch (CloneNotSupportedException cantHappen) {
/* 1176 */       throw new InternalError();
/*      */     } 
/* 1178 */     c.key = (int[])this.key.clone();
/* 1179 */     c.containsNull = this.containsNull;
/* 1180 */     c.link = (long[])this.link.clone();
/* 1181 */     c.strategy = this.strategy;
/* 1182 */     return c;
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
/* 1195 */     int h = 0;
/* 1196 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1197 */       while (this.key[i] == 0)
/* 1198 */         i++; 
/* 1199 */       h += this.strategy.hashCode(this.key[i]);
/* 1200 */       i++;
/*      */     } 
/*      */     
/* 1203 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1206 */     IntIterator i = iterator();
/* 1207 */     s.defaultWriteObject();
/* 1208 */     for (int j = this.size; j-- != 0;)
/* 1209 */       s.writeInt(i.nextInt()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1213 */     s.defaultReadObject();
/* 1214 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1215 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1216 */     this.mask = this.n - 1;
/* 1217 */     int[] key = this.key = new int[this.n + 1];
/* 1218 */     long[] link = this.link = new long[this.n + 1];
/* 1219 */     int prev = -1;
/* 1220 */     this.first = this.last = -1;
/*      */     
/* 1222 */     for (int i = this.size; i-- != 0; ) {
/* 1223 */       int pos, k = s.readInt();
/* 1224 */       if (this.strategy.equals(k, 0)) {
/* 1225 */         pos = this.n;
/* 1226 */         this.containsNull = true;
/*      */       }
/* 1228 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0) {
/* 1229 */         while (key[pos = pos + 1 & this.mask] != 0);
/*      */       } 
/* 1231 */       key[pos] = k;
/* 1232 */       if (this.first != -1) {
/* 1233 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1234 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1235 */         prev = pos; continue;
/*      */       } 
/* 1237 */       prev = this.first = pos;
/*      */       
/* 1239 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1242 */     this.last = prev;
/* 1243 */     if (prev != -1)
/*      */     {
/* 1245 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\IntLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */