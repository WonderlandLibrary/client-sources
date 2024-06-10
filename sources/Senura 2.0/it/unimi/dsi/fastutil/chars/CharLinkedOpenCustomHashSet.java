/*      */ package it.unimi.dsi.fastutil.chars;
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
/*      */ public class CharLinkedOpenCustomHashSet
/*      */   extends AbstractCharSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient char[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*      */   protected CharHash.Strategy strategy;
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
/*      */   public CharLinkedOpenCustomHashSet(int expected, float f, CharHash.Strategy strategy) {
/*  144 */     this.strategy = strategy;
/*  145 */     if (f <= 0.0F || f > 1.0F)
/*  146 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  147 */     if (expected < 0)
/*  148 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  149 */     this.f = f;
/*  150 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  151 */     this.mask = this.n - 1;
/*  152 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  153 */     this.key = new char[this.n + 1];
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
/*      */   public CharLinkedOpenCustomHashSet(int expected, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(Collection<? extends Character> c, float f, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(Collection<? extends Character> c, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(CharCollection c, float f, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(CharCollection c, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(CharIterator i, float f, CharHash.Strategy strategy) {
/*  247 */     this(16, f, strategy);
/*  248 */     while (i.hasNext()) {
/*  249 */       add(i.nextChar());
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
/*      */   public CharLinkedOpenCustomHashSet(CharIterator i, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(Iterator<?> i, float f, CharHash.Strategy strategy) {
/*  276 */     this(CharIterators.asCharIterator(i), f, strategy);
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
/*      */   public CharLinkedOpenCustomHashSet(Iterator<?> i, CharHash.Strategy strategy) {
/*  289 */     this(CharIterators.asCharIterator(i), strategy);
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
/*      */   public CharLinkedOpenCustomHashSet(char[] a, int offset, int length, float f, CharHash.Strategy strategy) {
/*  307 */     this((length < 0) ? 0 : length, f, strategy);
/*  308 */     CharArrays.ensureOffsetLength(a, offset, length);
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
/*      */   public CharLinkedOpenCustomHashSet(char[] a, int offset, int length, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(char[] a, float f, CharHash.Strategy strategy) {
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
/*      */   public CharLinkedOpenCustomHashSet(char[] a, CharHash.Strategy strategy) {
/*  353 */     this(a, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharHash.Strategy strategy() {
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
/*      */   public boolean addAll(CharCollection c) {
/*  379 */     if (this.f <= 0.5D) {
/*  380 */       ensureCapacity(c.size());
/*      */     } else {
/*  382 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  384 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Character> c) {
/*  389 */     if (this.f <= 0.5D) {
/*  390 */       ensureCapacity(c.size());
/*      */     } else {
/*  392 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  394 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(char k) {
/*      */     int pos;
/*  399 */     if (this.strategy.equals(k, false)) {
/*  400 */       if (this.containsNull)
/*  401 */         return false; 
/*  402 */       pos = this.n;
/*  403 */       this.containsNull = true;
/*  404 */       this.key[this.n] = k;
/*      */     } else {
/*      */       
/*  407 */       char[] key = this.key;
/*      */       char curr;
/*  409 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != '\000') {
/*      */         
/*  411 */         if (this.strategy.equals(curr, k))
/*  412 */           return false; 
/*  413 */         while ((curr = key[pos = pos + 1 & this.mask]) != '\000') {
/*  414 */           if (this.strategy.equals(curr, k))
/*  415 */             return false; 
/*      */         } 
/*  417 */       }  key[pos] = k;
/*      */     } 
/*  419 */     if (this.size == 0) {
/*  420 */       this.first = this.last = pos;
/*      */       
/*  422 */       this.link[pos] = -1L;
/*      */     } else {
/*  424 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  425 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  426 */       this.last = pos;
/*      */     } 
/*  428 */     if (this.size++ >= this.maxFill) {
/*  429 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  432 */     return true;
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
/*  445 */     char[] key = this.key; while (true) {
/*      */       char curr; int last;
/*  447 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  449 */         if ((curr = key[pos]) == '\000') {
/*  450 */           key[last] = Character.MIN_VALUE;
/*      */           return;
/*      */         } 
/*  453 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  454 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  456 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  458 */       key[last] = curr;
/*  459 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  463 */     this.size--;
/*  464 */     fixPointers(pos);
/*  465 */     shiftKeys(pos);
/*  466 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  467 */       rehash(this.n / 2); 
/*  468 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  471 */     this.containsNull = false;
/*  472 */     this.key[this.n] = Character.MIN_VALUE;
/*  473 */     this.size--;
/*  474 */     fixPointers(this.n);
/*  475 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  476 */       rehash(this.n / 2); 
/*  477 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(char k) {
/*  482 */     if (this.strategy.equals(k, false)) {
/*  483 */       if (this.containsNull)
/*  484 */         return removeNullEntry(); 
/*  485 */       return false;
/*      */     } 
/*      */     
/*  488 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  491 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  492 */       return false; 
/*  493 */     if (this.strategy.equals(k, curr))
/*  494 */       return removeEntry(pos); 
/*      */     while (true) {
/*  496 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  497 */         return false; 
/*  498 */       if (this.strategy.equals(k, curr)) {
/*  499 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(char k) {
/*  505 */     if (this.strategy.equals(k, false)) {
/*  506 */       return this.containsNull;
/*      */     }
/*  508 */     char[] key = this.key;
/*      */     char curr;
/*      */     int pos;
/*  511 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == '\000')
/*  512 */       return false; 
/*  513 */     if (this.strategy.equals(k, curr))
/*  514 */       return true; 
/*      */     while (true) {
/*  516 */       if ((curr = key[pos = pos + 1 & this.mask]) == '\000')
/*  517 */         return false; 
/*  518 */       if (this.strategy.equals(k, curr)) {
/*  519 */         return true;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeFirstChar() {
/*  530 */     if (this.size == 0)
/*  531 */       throw new NoSuchElementException(); 
/*  532 */     int pos = this.first;
/*      */     
/*  534 */     this.first = (int)this.link[pos];
/*  535 */     if (0 <= this.first)
/*      */     {
/*  537 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  539 */     char k = this.key[pos];
/*  540 */     this.size--;
/*  541 */     if (this.strategy.equals(k, false)) {
/*  542 */       this.containsNull = false;
/*  543 */       this.key[this.n] = Character.MIN_VALUE;
/*      */     } else {
/*  545 */       shiftKeys(pos);
/*  546 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  547 */       rehash(this.n / 2); 
/*  548 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeLastChar() {
/*  558 */     if (this.size == 0)
/*  559 */       throw new NoSuchElementException(); 
/*  560 */     int pos = this.last;
/*      */     
/*  562 */     this.last = (int)(this.link[pos] >>> 32L);
/*  563 */     if (0 <= this.last)
/*      */     {
/*  565 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  567 */     char k = this.key[pos];
/*  568 */     this.size--;
/*  569 */     if (this.strategy.equals(k, false)) {
/*  570 */       this.containsNull = false;
/*  571 */       this.key[this.n] = Character.MIN_VALUE;
/*      */     } else {
/*  573 */       shiftKeys(pos);
/*  574 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  575 */       rehash(this.n / 2); 
/*  576 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  579 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  581 */     if (this.last == i) {
/*  582 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  584 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  586 */       long linki = this.link[i];
/*  587 */       int prev = (int)(linki >>> 32L);
/*  588 */       int next = (int)linki;
/*  589 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  590 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  592 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  593 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  594 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  597 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  599 */     if (this.first == i) {
/*  600 */       this.first = (int)this.link[i];
/*      */       
/*  602 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  604 */       long linki = this.link[i];
/*  605 */       int prev = (int)(linki >>> 32L);
/*  606 */       int next = (int)linki;
/*  607 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  608 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  610 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  611 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  612 */     this.last = i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToFirst(char k) {
/*      */     int pos;
/*  624 */     if (this.strategy.equals(k, false)) {
/*  625 */       if (this.containsNull) {
/*  626 */         moveIndexToFirst(this.n);
/*  627 */         return false;
/*      */       } 
/*  629 */       this.containsNull = true;
/*  630 */       pos = this.n;
/*      */     } else {
/*      */       
/*  633 */       char[] key = this.key;
/*  634 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  636 */       while (key[pos] != '\000') {
/*  637 */         if (this.strategy.equals(k, key[pos])) {
/*  638 */           moveIndexToFirst(pos);
/*  639 */           return false;
/*      */         } 
/*  641 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  644 */     this.key[pos] = k;
/*  645 */     if (this.size == 0) {
/*  646 */       this.first = this.last = pos;
/*      */       
/*  648 */       this.link[pos] = -1L;
/*      */     } else {
/*  650 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  651 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  652 */       this.first = pos;
/*      */     } 
/*  654 */     if (this.size++ >= this.maxFill) {
/*  655 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  658 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAndMoveToLast(char k) {
/*      */     int pos;
/*  670 */     if (this.strategy.equals(k, false)) {
/*  671 */       if (this.containsNull) {
/*  672 */         moveIndexToLast(this.n);
/*  673 */         return false;
/*      */       } 
/*  675 */       this.containsNull = true;
/*  676 */       pos = this.n;
/*      */     } else {
/*      */       
/*  679 */       char[] key = this.key;
/*  680 */       pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/*      */       
/*  682 */       while (key[pos] != '\000') {
/*  683 */         if (this.strategy.equals(k, key[pos])) {
/*  684 */           moveIndexToLast(pos);
/*  685 */           return false;
/*      */         } 
/*  687 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  690 */     this.key[pos] = k;
/*  691 */     if (this.size == 0) {
/*  692 */       this.first = this.last = pos;
/*      */       
/*  694 */       this.link[pos] = -1L;
/*      */     } else {
/*  696 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  697 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  698 */       this.last = pos;
/*      */     } 
/*  700 */     if (this.size++ >= this.maxFill) {
/*  701 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  704 */     return true;
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
/*  715 */     if (this.size == 0)
/*      */       return; 
/*  717 */     this.size = 0;
/*  718 */     this.containsNull = false;
/*  719 */     Arrays.fill(this.key, false);
/*  720 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  724 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  728 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  738 */     if (this.size == 0) {
/*  739 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  742 */     if (this.first == i) {
/*  743 */       this.first = (int)this.link[i];
/*  744 */       if (0 <= this.first)
/*      */       {
/*  746 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  750 */     if (this.last == i) {
/*  751 */       this.last = (int)(this.link[i] >>> 32L);
/*  752 */       if (0 <= this.last)
/*      */       {
/*  754 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  758 */     long linki = this.link[i];
/*  759 */     int prev = (int)(linki >>> 32L);
/*  760 */     int next = (int)linki;
/*  761 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  762 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  774 */     if (this.size == 1) {
/*  775 */       this.first = this.last = d;
/*      */       
/*  777 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  780 */     if (this.first == s) {
/*  781 */       this.first = d;
/*  782 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  783 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  786 */     if (this.last == s) {
/*  787 */       this.last = d;
/*  788 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  789 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  792 */     long links = this.link[s];
/*  793 */     int prev = (int)(links >>> 32L);
/*  794 */     int next = (int)links;
/*  795 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  796 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  797 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char firstChar() {
/*  806 */     if (this.size == 0)
/*  807 */       throw new NoSuchElementException(); 
/*  808 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char lastChar() {
/*  817 */     if (this.size == 0)
/*  818 */       throw new NoSuchElementException(); 
/*  819 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharSortedSet tailSet(char from) {
/*  828 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharSortedSet headSet(char to) {
/*  837 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharSortedSet subSet(char from, char to) {
/*  846 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharComparator comparator() {
/*  855 */     return null;
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
/*      */     implements CharListIterator
/*      */   {
/*  870 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  876 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  881 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  886 */     int index = -1;
/*      */     SetIterator() {
/*  888 */       this.next = CharLinkedOpenCustomHashSet.this.first;
/*  889 */       this.index = 0;
/*      */     }
/*      */     SetIterator(char from) {
/*  892 */       if (CharLinkedOpenCustomHashSet.this.strategy.equals(from, false)) {
/*  893 */         if (CharLinkedOpenCustomHashSet.this.containsNull) {
/*  894 */           this.next = (int)CharLinkedOpenCustomHashSet.this.link[CharLinkedOpenCustomHashSet.this.n];
/*  895 */           this.prev = CharLinkedOpenCustomHashSet.this.n;
/*      */           return;
/*      */         } 
/*  898 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  900 */       if (CharLinkedOpenCustomHashSet.this.strategy.equals(CharLinkedOpenCustomHashSet.this.key[CharLinkedOpenCustomHashSet.this.last], from)) {
/*  901 */         this.prev = CharLinkedOpenCustomHashSet.this.last;
/*  902 */         this.index = CharLinkedOpenCustomHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  906 */       char[] key = CharLinkedOpenCustomHashSet.this.key;
/*  907 */       int pos = HashCommon.mix(CharLinkedOpenCustomHashSet.this.strategy.hashCode(from)) & CharLinkedOpenCustomHashSet.this.mask;
/*      */       
/*  909 */       while (key[pos] != '\000') {
/*  910 */         if (CharLinkedOpenCustomHashSet.this.strategy.equals(key[pos], from)) {
/*      */           
/*  912 */           this.next = (int)CharLinkedOpenCustomHashSet.this.link[pos];
/*  913 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  916 */         pos = pos + 1 & CharLinkedOpenCustomHashSet.this.mask;
/*      */       } 
/*  918 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  922 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  926 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public char nextChar() {
/*  930 */       if (!hasNext())
/*  931 */         throw new NoSuchElementException(); 
/*  932 */       this.curr = this.next;
/*  933 */       this.next = (int)CharLinkedOpenCustomHashSet.this.link[this.curr];
/*  934 */       this.prev = this.curr;
/*  935 */       if (this.index >= 0) {
/*  936 */         this.index++;
/*      */       }
/*      */       
/*  939 */       return CharLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public char previousChar() {
/*  943 */       if (!hasPrevious())
/*  944 */         throw new NoSuchElementException(); 
/*  945 */       this.curr = this.prev;
/*  946 */       this.prev = (int)(CharLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*  947 */       this.next = this.curr;
/*  948 */       if (this.index >= 0)
/*  949 */         this.index--; 
/*  950 */       return CharLinkedOpenCustomHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  953 */       if (this.index >= 0)
/*      */         return; 
/*  955 */       if (this.prev == -1) {
/*  956 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  959 */       if (this.next == -1) {
/*  960 */         this.index = CharLinkedOpenCustomHashSet.this.size;
/*      */         return;
/*      */       } 
/*  963 */       int pos = CharLinkedOpenCustomHashSet.this.first;
/*  964 */       this.index = 1;
/*  965 */       while (pos != this.prev) {
/*  966 */         pos = (int)CharLinkedOpenCustomHashSet.this.link[pos];
/*  967 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  972 */       ensureIndexKnown();
/*  973 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  977 */       ensureIndexKnown();
/*  978 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  982 */       ensureIndexKnown();
/*  983 */       if (this.curr == -1)
/*  984 */         throw new IllegalStateException(); 
/*  985 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  990 */         this.index--;
/*  991 */         this.prev = (int)(CharLinkedOpenCustomHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  993 */         this.next = (int)CharLinkedOpenCustomHashSet.this.link[this.curr];
/*  994 */       }  CharLinkedOpenCustomHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  999 */       if (this.prev == -1) {
/* 1000 */         CharLinkedOpenCustomHashSet.this.first = this.next;
/*      */       } else {
/* 1002 */         CharLinkedOpenCustomHashSet.this.link[this.prev] = CharLinkedOpenCustomHashSet.this.link[this.prev] ^ (CharLinkedOpenCustomHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1003 */       }  if (this.next == -1) {
/* 1004 */         CharLinkedOpenCustomHashSet.this.last = this.prev;
/*      */       } else {
/* 1006 */         CharLinkedOpenCustomHashSet.this.link[this.next] = CharLinkedOpenCustomHashSet.this.link[this.next] ^ (CharLinkedOpenCustomHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1007 */       }  int pos = this.curr;
/* 1008 */       this.curr = -1;
/* 1009 */       if (pos == CharLinkedOpenCustomHashSet.this.n) {
/* 1010 */         CharLinkedOpenCustomHashSet.this.containsNull = false;
/* 1011 */         CharLinkedOpenCustomHashSet.this.key[CharLinkedOpenCustomHashSet.this.n] = Character.MIN_VALUE;
/*      */       } else {
/*      */         
/* 1014 */         char[] key = CharLinkedOpenCustomHashSet.this.key;
/*      */         while (true) {
/*      */           char curr;
/*      */           int last;
/* 1018 */           pos = (last = pos) + 1 & CharLinkedOpenCustomHashSet.this.mask;
/*      */           while (true) {
/* 1020 */             if ((curr = key[pos]) == '\000') {
/* 1021 */               key[last] = Character.MIN_VALUE;
/*      */               return;
/*      */             } 
/* 1024 */             int slot = HashCommon.mix(CharLinkedOpenCustomHashSet.this.strategy.hashCode(curr)) & CharLinkedOpenCustomHashSet.this.mask;
/* 1025 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1027 */             pos = pos + 1 & CharLinkedOpenCustomHashSet.this.mask;
/*      */           } 
/* 1029 */           key[last] = curr;
/* 1030 */           if (this.next == pos)
/* 1031 */             this.next = last; 
/* 1032 */           if (this.prev == pos)
/* 1033 */             this.prev = last; 
/* 1034 */           CharLinkedOpenCustomHashSet.this.fixPointers(pos, last);
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
/*      */   public CharListIterator iterator(char from) {
/* 1052 */     return new SetIterator(from);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharListIterator iterator() {
/* 1063 */     return new SetIterator();
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
/* 1080 */     return trim(this.size);
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
/* 1104 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1105 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1106 */       return true; 
/*      */     try {
/* 1108 */       rehash(l);
/* 1109 */     } catch (OutOfMemoryError cantDoIt) {
/* 1110 */       return false;
/*      */     } 
/* 1112 */     return true;
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
/* 1128 */     char[] key = this.key;
/* 1129 */     int mask = newN - 1;
/* 1130 */     char[] newKey = new char[newN + 1];
/* 1131 */     int i = this.first, prev = -1, newPrev = -1;
/* 1132 */     long[] link = this.link;
/* 1133 */     long[] newLink = new long[newN + 1];
/* 1134 */     this.first = -1;
/* 1135 */     for (int j = this.size; j-- != 0; ) {
/* 1136 */       int pos; if (this.strategy.equals(key[i], false)) {
/* 1137 */         pos = newN;
/*      */       } else {
/* 1139 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1140 */         while (newKey[pos] != '\000')
/* 1141 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1143 */       newKey[pos] = key[i];
/* 1144 */       if (prev != -1) {
/* 1145 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1146 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1147 */         newPrev = pos;
/*      */       } else {
/* 1149 */         newPrev = this.first = pos;
/*      */         
/* 1151 */         newLink[pos] = -1L;
/*      */       } 
/* 1153 */       int t = i;
/* 1154 */       i = (int)link[i];
/* 1155 */       prev = t;
/*      */     } 
/* 1157 */     this.link = newLink;
/* 1158 */     this.last = newPrev;
/* 1159 */     if (newPrev != -1)
/*      */     {
/* 1161 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1162 */     this.n = newN;
/* 1163 */     this.mask = mask;
/* 1164 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1165 */     this.key = newKey;
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
/*      */   public CharLinkedOpenCustomHashSet clone() {
/*      */     CharLinkedOpenCustomHashSet c;
/*      */     try {
/* 1182 */       c = (CharLinkedOpenCustomHashSet)super.clone();
/* 1183 */     } catch (CloneNotSupportedException cantHappen) {
/* 1184 */       throw new InternalError();
/*      */     } 
/* 1186 */     c.key = (char[])this.key.clone();
/* 1187 */     c.containsNull = this.containsNull;
/* 1188 */     c.link = (long[])this.link.clone();
/* 1189 */     c.strategy = this.strategy;
/* 1190 */     return c;
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
/* 1203 */     int h = 0;
/* 1204 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1205 */       while (this.key[i] == '\000')
/* 1206 */         i++; 
/* 1207 */       h += this.strategy.hashCode(this.key[i]);
/* 1208 */       i++;
/*      */     } 
/*      */     
/* 1211 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1214 */     CharIterator i = iterator();
/* 1215 */     s.defaultWriteObject();
/* 1216 */     for (int j = this.size; j-- != 0;)
/* 1217 */       s.writeChar(i.nextChar()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1221 */     s.defaultReadObject();
/* 1222 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1223 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1224 */     this.mask = this.n - 1;
/* 1225 */     char[] key = this.key = new char[this.n + 1];
/* 1226 */     long[] link = this.link = new long[this.n + 1];
/* 1227 */     int prev = -1;
/* 1228 */     this.first = this.last = -1;
/*      */     
/* 1230 */     for (int i = this.size; i-- != 0; ) {
/* 1231 */       int pos; char k = s.readChar();
/* 1232 */       if (this.strategy.equals(k, false)) {
/* 1233 */         pos = this.n;
/* 1234 */         this.containsNull = true;
/*      */       }
/* 1236 */       else if (key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask] != '\000') {
/* 1237 */         while (key[pos = pos + 1 & this.mask] != '\000');
/*      */       } 
/* 1239 */       key[pos] = k;
/* 1240 */       if (this.first != -1) {
/* 1241 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1242 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1243 */         prev = pos; continue;
/*      */       } 
/* 1245 */       prev = this.first = pos;
/*      */       
/* 1247 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1250 */     this.last = prev;
/* 1251 */     if (prev != -1)
/*      */     {
/* 1253 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\chars\CharLinkedOpenCustomHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */