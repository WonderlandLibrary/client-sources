/*      */ package it.unimi.dsi.fastutil.longs;
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
/*      */ public class LongLinkedOpenCustomHashSet
/*      */   extends AbstractLongSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected LongHash.Strategy strategy;
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
/*      */   public LongLinkedOpenCustomHashSet(int expected, float f, LongHash.Strategy strategy) {
/*  144 */     this.strategy = strategy;
/*  145 */     if (f <= 0.0F || f > 1.0F)
/*  146 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  147 */     if (expected < 0)
/*  148 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  149 */     this.f = f;
/*  150 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  151 */     this.mask = this.n - 1;
/*  152 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  153 */     this.key = new long[this.n + 1];
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
/*      */   public LongLinkedOpenCustomHashSet(int expected, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(Collection<? extends Long> c, float f, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(Collection<? extends Long> c, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(LongCollection c, float f, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(LongCollection c, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(LongIterator i, float f, LongHash.Strategy strategy) {
/*  247 */     this(16, f, strategy);
/*  248 */     while (i.hasNext()) {
/*  249 */       add(i.nextLong());
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
/*      */   public LongLinkedOpenCustomHashSet(LongIterator i, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(Iterator<?> i, float f, LongHash.Strategy strategy) {
/*  276 */     this(LongIterators.asLongIterator(i), f, strategy);
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
/*      */   public LongLinkedOpenCustomHashSet(Iterator<?> i, LongHash.Strategy strategy) {
/*  289 */     this(LongIterators.asLongIterator(i), strategy);
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
/*      */   public LongLinkedOpenCustomHashSet(long[] a, int offset, int length, float f, LongHash.Strategy strategy) {
/*  307 */     this((length < 0) ? 0 : length, f, strategy);
/*  308 */     LongArrays.ensureOffsetLength(a, offset, length);
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
/*      */   public LongLinkedOpenCustomHashSet(long[] a, int offset, int length, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(long[] a, float f, LongHash.Strategy strategy) {
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
/*      */   public LongLinkedOpenCustomHashSet(long[] a, LongHash.Strategy strategy) {
/*  353 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongHash.Strategy strategy() {
/*  361 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  364 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  367 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  368 */     if (needed > this.n)
/*  369 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  372 */     int needed = (int)Math.min(1073741824L, 
/*  373 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  374 */     if (needed > this.n)
/*  375 */       rehash(needed); 
/*      */   }
/*      */   
/*      */   public boolean addAll(LongCollection c) {
/*  379 */     if (this.f <= 0.5D) {
/*  380 */       ensureCapacity(c.size());
/*      */     } else {
/*  382 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  384 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Long> c) {
/*  389 */     if (this.f <= 0.5D) {
/*  390 */       ensureCapacity(c.size());
/*      */     } else {
/*  392 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  394 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(long k) {
/*      */     int pos;
/*  399 */     if (this.strategy.equals(k, 0L)) {
/*  400 */       if (this.containsNull)
/*  401 */         return false; 
/*  402 */       pos = this.n;
/*  403 */       this.containsNull = true;
/*  404 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  407 */       long[] key = this.key;
/*      */       long curr;
/*  409 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  410 */         if (this.strategy.equals(curr, k))
/*  411 */           return false; 
/*  412 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  413 */           if (this.strategy.equals(curr, k))
/*  414 */             return false; 
/*      */         } 
/*  416 */       }  key[pos] = k;
/*      */     } 
/*  418 */     if (this.size == 0) {
/*  419 */       this.first = this.last = pos;
/*      */       
/*  421 */       this.link[pos] = -1L;
/*      */     } else {
/*  423 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  424 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  425 */       this.last = pos;
/*      */     } 
/*  427 */     if (this.size++ >= this.maxFill) {
/*  428 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  431 */     return true;
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
/*  444 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  446 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  448 */         if ((curr = key[pos]) == 0L) {
/*  449 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  452 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  453 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  455 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  457 */       key[last] = curr;
/*  458 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  462 */     this.size--;
/*  463 */     fixPointers(pos);
/*  464 */     shiftKeys(pos);
/*  465 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  466 */       rehash(this.n / 2); 
/*  467 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  470 */     this.containsNull = false;
/*  471 */     this.key[this.n] = 0L;
/*  472 */     this.size--;
/*  473 */     fixPointers(this.n);
/*  474 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  475 */       rehash(this.n / 2); 
/*  476 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(long k) {
/*  481 */     if (this.strategy.equals(k, 0L)) {
/*  482 */       if (this.containsNull)
/*  483 */         return removeNullEntry(); 
/*  484 */       return false;
/*      */     } 
/*      */     
/*  487 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  490 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  491 */       return false; 
/*  492 */     if (this.strategy.equals(k, curr))
/*  493 */       return removeEntry(pos); 
/*      */     while (true) {
/*  495 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  496 */         return false; 
/*  497 */       if (this.strategy.equals(k, curr)) {
/*  498 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(long k) {
/*  504 */     if (this.strategy.equals(k, 0L)) {
/*  505 */       return this.containsNull;
/*      */     }
/*  507 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  510 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  511 */       return false; 
/*  512 */     if (this.strategy.equals(k, curr))
/*  513 */       return true; 
/*      */     while (true) {
/*  515 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  516 */         return false; 
/*  517 */       if (this.strategy.equals(k, curr)) {
/*  518 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeFirstLong() {
/*  529 */     if (this.size == 0)
/*  530 */       throw new NoSuchElementException(); 
/*  531 */     int pos = this.first;
/*      */     
/*  533 */     this.first = (int)this.link[pos];
/*  534 */     if (0 <= this.first)
/*      */     {
/*  536 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  538 */     long k = this.key[pos];
/*  539 */     this.size--;
/*  540 */     if (this.strategy.equals(k, 0L)) {
/*  541 */       this.containsNull = false;
/*  542 */       this.key[this.n] = 0L;
/*      */     } else {
/*  544 */       shiftKeys(pos);
/*  545 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  546 */       rehash(this.n / 2); 
/*  547 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  557 */     if (this.size == 0)
/*  558 */       throw new NoSuchElementException(); 
/*  559 */     int pos = this.last;
/*      */     
/*  561 */     this.last = (int)(this.link[pos] >>> 32L);
/*  562 */     if (0 <= this.last)
/*      */     {
/*  564 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  566 */     long k = this.key[pos];
/*  567 */     this.size--;
/*  568 */     if (this.strategy.equals(k, 0L)) {
/*  569 */       this.containsNull = false;
/*  570 */       this.key[this.n] = 0L;
/*      */     } else {
/*  572 */       shiftKeys(pos);
/*  573 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  574 */       rehash(this.n / 2); 
/*  575 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  578 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  580 */     if (this.last == i) {
/*  581 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  583 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  585 */       long linki = this.link[i];
/*  586 */       int prev = (int)(linki >>> 32L);
/*  587 */       int next = (int)linki;
/*  588 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  589 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  591 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  592 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  593 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  596 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  598 */     if (this.first == i) {
/*  599 */       this.first = (int)this.link[i];
/*      */       
/*  601 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  603 */       long linki = this.link[i];
/*  604 */       int prev = (int)(linki >>> 32L);
/*  605 */       int next = (int)linki;
/*  606 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  607 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  609 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  610 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  611 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(long k) {
/*      */     int pos;
/*  623 */     if (this.strategy.equals(k, 0L)) {
/*  624 */       if (this.containsNull) {
/*  625 */         moveIndexToFirst(this.n);
/*  626 */         return false;
/*      */       } 
/*  628 */       this.containsNull = true;
/*  629 */       pos = this.n;
/*      */     } else {
/*      */       
/*  632 */       long[] key = this.key;
/*  633 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  635 */       while (key[pos] != 0L) {
/*  636 */         if (this.strategy.equals(k, key[pos])) {
/*  637 */           moveIndexToFirst(pos);
/*  638 */           return false;
/*      */         } 
/*  640 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  643 */     this.key[pos] = k;
/*  644 */     if (this.size == 0) {
/*  645 */       this.first = this.last = pos;
/*      */       
/*  647 */       this.link[pos] = -1L;
/*      */     } else {
/*  649 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  650 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  651 */       this.first = pos;
/*      */     } 
/*  653 */     if (this.size++ >= this.maxFill) {
/*  654 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  657 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(long k) {
/*      */     int pos;
/*  669 */     if (this.strategy.equals(k, 0L)) {
/*  670 */       if (this.containsNull) {
/*  671 */         moveIndexToLast(this.n);
/*  672 */         return false;
/*      */       } 
/*  674 */       this.containsNull = true;
/*  675 */       pos = this.n;
/*      */     } else {
/*      */       
/*  678 */       long[] key = this.key;
/*  679 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  681 */       while (key[pos] != 0L) {
/*  682 */         if (this.strategy.equals(k, key[pos])) {
/*  683 */           moveIndexToLast(pos);
/*  684 */           return false;
/*      */         } 
/*  686 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  689 */     this.key[pos] = k;
/*  690 */     if (this.size == 0) {
/*  691 */       this.first = this.last = pos;
/*      */       
/*  693 */       this.link[pos] = -1L;
/*      */     } else {
/*  695 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  696 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  697 */       this.last = pos;
/*      */     } 
/*  699 */     if (this.size++ >= this.maxFill) {
/*  700 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  703 */     return true;
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
/*  714 */     if (this.size == 0)
/*      */       return; 
/*  716 */     this.size = 0;
/*  717 */     this.containsNull = false;
/*  718 */     Arrays.fill(this.key, 0L);
/*  719 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  723 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  727 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  737 */     if (this.size == 0) {
/*  738 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  741 */     if (this.first == i) {
/*  742 */       this.first = (int)this.link[i];
/*  743 */       if (0 <= this.first)
/*      */       {
/*  745 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  749 */     if (this.last == i) {
/*  750 */       this.last = (int)(this.link[i] >>> 32L);
/*  751 */       if (0 <= this.last)
/*      */       {
/*  753 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  757 */     long linki = this.link[i];
/*  758 */     int prev = (int)(linki >>> 32L);
/*  759 */     int next = (int)linki;
/*  760 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  761 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  773 */     if (this.size == 1) {
/*  774 */       this.first = this.last = d;
/*      */       
/*  776 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  779 */     if (this.first == s) {
/*  780 */       this.first = d;
/*  781 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  782 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  785 */     if (this.last == s) {
/*  786 */       this.last = d;
/*  787 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  788 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  791 */     long links = this.link[s];
/*  792 */     int prev = (int)(links >>> 32L);
/*  793 */     int next = (int)links;
/*  794 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  795 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  796 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLong() {
/*  805 */     if (this.size == 0)
/*  806 */       throw new NoSuchElementException(); 
/*  807 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLong() {
/*  816 */     if (this.size == 0)
/*  817 */       throw new NoSuchElementException(); 
/*  818 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongSortedSet tailSet(long from) {
/*  827 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongSortedSet headSet(long to) {
/*  836 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongSortedSet subSet(long from, long to) {
/*  845 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/*  854 */     return null;
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
/*      */     implements LongListIterator
/*      */   {
/*  869 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  880 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  885 */     int index = -1;
/*      */     SetIterator() {
/*  887 */       this.next = LongLinkedOpenCustomHashSet.this.first;
/*  888 */       this.index = 0;
/*      */     }
/*      */     SetIterator(long from) {
/*  891 */       if (LongLinkedOpenCustomHashSet.this.strategy.equals(from, 0L)) {
/*  892 */         if (LongLinkedOpenCustomHashSet.this.containsNull) {
/*  893 */           this.next = (int)LongLinkedOpenCustomHashSet.this.link[LongLinkedOpenCustomHashSet.this.n];
/*  894 */           this.prev = LongLinkedOpenCustomHashSet.this.n;
/*      */           return;
/*      */         } 
/*  897 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  899 */       if (LongLinkedOpenCustomHashSet.this.strategy.equals(LongLinkedOpenCustomHashSet.this.key[LongLinkedOpenCustomHashSet.this.last], from)) {
/*  900 */         this.prev = LongLinkedOpenCustomHashSet.this.last;
/*  901 */         this.index = LongLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  905 */       long[] key = LongLinkedOpenCustomHashSet.this.key;
/*  906 */       int pos = HashCommon.mix(LongLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & LongLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  908 */       while (key[pos] != 0L) {
/*  909 */         if (LongLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  911 */           this.next = (int)LongLinkedOpenCustomHashSet.this.link[pos];
/*  912 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  915 */         pos = pos + 1 & LongLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  917 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  921 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  925 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  929 */       if (!hasNext())
/*  930 */         throw new NoSuchElementException(); 
/*  931 */       this.curr = this.next;
/*  932 */       this.next = (int)LongLinkedOpenCustomHashSet.this.link[this.curr];
/*  933 */       this.prev = this.curr;
/*  934 */       if (this.index >= 0) {
/*  935 */         this.index++;
/*      */       }
/*      */       
/*  938 */       return LongLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public long previousLong() {
/*  942 */       if (!hasPrevious())
/*  943 */         throw new NoSuchElementException(); 
/*  944 */       this.curr = this.prev;
/*  945 */       this.prev = (int)(LongLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  946 */       this.next = this.curr;
/*  947 */       if (this.index >= 0)
/*  948 */         this.index--; 
/*  949 */       return LongLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  952 */       if (this.index >= 0)
/*      */         return; 
/*  954 */       if (this.prev == -1) {
/*  955 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  958 */       if (this.next == -1) {
/*  959 */         this.index = LongLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  962 */       int pos = LongLinkedOpenCustomHashSet.this.first;
/*  963 */       this.index = 1;
/*  964 */       while (pos != this.prev) {
/*  965 */         pos = (int)LongLinkedOpenCustomHashSet.this.link[pos];
/*  966 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  971 */       ensureIndexKnown();
/*  972 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  976 */       ensureIndexKnown();
/*  977 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  981 */       ensureIndexKnown();
/*  982 */       if (this.curr == -1)
/*  983 */         throw new IllegalStateException(); 
/*  984 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  989 */         this.index--;
/*  990 */         this.prev = (int)(LongLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  992 */         this.next = (int)LongLinkedOpenCustomHashSet.this.link[this.curr];
/*  993 */       }  LongLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  998 */       if (this.prev == -1) {
/*  999 */         LongLinkedOpenCustomHashSet.this.first = this.next;
/*      */       } else {
/* 1001 */         LongLinkedOpenCustomHashSet.this.link[this.prev] = LongLinkedOpenCustomHashSet.this.link[this.prev] ^ (LongLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1002 */       }  if (this.next == -1) {
/* 1003 */         LongLinkedOpenCustomHashSet.this.last = this.prev;
/*      */       } else {
/* 1005 */         LongLinkedOpenCustomHashSet.this.link[this.next] = LongLinkedOpenCustomHashSet.this.link[this.next] ^ (LongLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1006 */       }  int pos = this.curr;
/* 1007 */       this.curr = -1;
/* 1008 */       if (pos == LongLinkedOpenCustomHashSet.this.n) {
/* 1009 */         LongLinkedOpenCustomHashSet.this.containsNull = false;
/* 1010 */         LongLinkedOpenCustomHashSet.this.key[LongLinkedOpenCustomHashSet.this.n] = 0L;
/*      */       } else {
/*      */         
/* 1013 */         long[] key = LongLinkedOpenCustomHashSet.this.key;
/*      */         while (true) {
/*      */           long curr;
/*      */           int last;
/* 1017 */           pos = (last = pos) + 1 & LongLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/* 1019 */             if ((curr = key[pos]) == 0L) {
/* 1020 */               key[last] = 0L;
/*      */               return;
/*      */             } 
/* 1023 */             int slot = HashCommon.mix(LongLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & LongLinkedOpenCustomHashSet.this.mask;
/* 1024 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1026 */             pos = pos + 1 & LongLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/* 1028 */           key[last] = curr;
/* 1029 */           if (this.next == pos)
/* 1030 */             this.next = last; 
/* 1031 */           if (this.prev == pos)
/* 1032 */             this.prev = last; 
/* 1033 */           LongLinkedOpenCustomHashSet.this.fixPointers(pos, last);
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
/*      */   public LongListIterator iterator(long from) {
/* 1051 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongListIterator iterator() {
/* 1062 */     return new SetIterator();
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
/* 1079 */     return trim(this.size);
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
/* 1103 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1104 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1105 */       return true; 
/*      */     try {
/* 1107 */       rehash(l);
/* 1108 */     } catch (OutOfMemoryError cantDoIt) {
/* 1109 */       return false;
/*      */     } 
/* 1111 */     return true;
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
/* 1127 */     long[] key = this.key;
/* 1128 */     int mask = newN - 1;
/* 1129 */     long[] newKey = new long[newN + 1];
/* 1130 */     int i = this.first, prev = -1, newPrev = -1;
/* 1131 */     long[] link = this.link;
/* 1132 */     long[] newLink = new long[newN + 1];
/* 1133 */     this.first = -1;
/* 1134 */     for (int j = this.size; j-- != 0; ) {
/* 1135 */       int pos; if (this.strategy.equals(key[i], 0L)) {
/* 1136 */         pos = newN;
/*      */       } else {
/* 1138 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1139 */         while (newKey[pos] != 0L)
/* 1140 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1142 */       newKey[pos] = key[i];
/* 1143 */       if (prev != -1) {
/* 1144 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1145 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1146 */         newPrev = pos;
/*      */       } else {
/* 1148 */         newPrev = this.first = pos;
/*      */         
/* 1150 */         newLink[pos] = -1L;
/*      */       } 
/* 1152 */       int t = i;
/* 1153 */       i = (int)link[i];
/* 1154 */       prev = t;
/*      */     } 
/* 1156 */     this.link = newLink;
/* 1157 */     this.last = newPrev;
/* 1158 */     if (newPrev != -1)
/*      */     {
/* 1160 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1161 */     this.n = newN;
/* 1162 */     this.mask = mask;
/* 1163 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1164 */     this.key = newKey;
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
/*      */   public LongLinkedOpenCustomHashSet clone() {
/*      */     LongLinkedOpenCustomHashSet c;
/*      */     try {
/* 1181 */       c = (LongLinkedOpenCustomHashSet)super.clone();
/* 1182 */     } catch (CloneNotSupportedException cantHappen) {
/* 1183 */       throw new InternalError();
/*      */     } 
/* 1185 */     c.key = (long[])this.key.clone();
/* 1186 */     c.containsNull = this.containsNull;
/* 1187 */     c.link = (long[])this.link.clone();
/* 1188 */     c.strategy = this.strategy;
/* 1189 */     return c;
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
/* 1202 */     int h = 0;
/* 1203 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1204 */       while (this.key[i] == 0L)
/* 1205 */         i++; 
/* 1206 */       h += this.strategy.hashCode(this.key[i]);
/* 1207 */       i++;
/*      */     } 
/*      */     
/* 1210 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1213 */     LongIterator i = iterator();
/* 1214 */     s.defaultWriteObject();
/* 1215 */     for (int j = this.size; j-- != 0;)
/* 1216 */       s.writeLong(i.nextLong()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1220 */     s.defaultReadObject();
/* 1221 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1222 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1223 */     this.mask = this.n - 1;
/* 1224 */     long[] key = this.key = new long[this.n + 1];
/* 1225 */     long[] link = this.link = new long[this.n + 1];
/* 1226 */     int prev = -1;
/* 1227 */     this.first = this.last = -1;
/*      */     
/* 1229 */     for (int i = this.size; i-- != 0; ) {
/* 1230 */       int pos; long k = s.readLong();
/* 1231 */       if (this.strategy.equals(k, 0L)) {
/* 1232 */         pos = this.n;
/* 1233 */         this.containsNull = true;
/*      */       }
/* 1235 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != 0L) {
/* 1236 */         while (key[pos = pos + 1 & this.mask] != 0L);
/*      */       } 
/* 1238 */       key[pos] = k;
/* 1239 */       if (this.first != -1) {
/* 1240 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1241 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1242 */         prev = pos; continue;
/*      */       } 
/* 1244 */       prev = this.first = pos;
/*      */       
/* 1246 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1249 */     this.last = prev;
/* 1250 */     if (prev != -1)
/*      */     {
/* 1252 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */