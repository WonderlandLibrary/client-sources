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
/*      */ public class LongLinkedOpenHashSet
/*      */   extends AbstractLongSortedSet
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNull;
/*   92 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   97 */   protected transient int last = -1;
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
/*      */   protected transient int n;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int maxFill;
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
/*      */   public LongLinkedOpenHashSet(int expected, float f) {
/*  135 */     if (f <= 0.0F || f > 1.0F)
/*  136 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  137 */     if (expected < 0)
/*  138 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  139 */     this.f = f;
/*  140 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  141 */     this.mask = this.n - 1;
/*  142 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  143 */     this.key = new long[this.n + 1];
/*  144 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(int expected) {
/*  153 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet() {
/*  161 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(Collection<? extends Long> c, float f) {
/*  172 */     this(c.size(), f);
/*  173 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(Collection<? extends Long> c) {
/*  183 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(LongCollection c, float f) {
/*  194 */     this(c.size(), f);
/*  195 */     addAll(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(LongCollection c) {
/*  205 */     this(c, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(LongIterator i, float f) {
/*  216 */     this(16, f);
/*  217 */     while (i.hasNext()) {
/*  218 */       add(i.nextLong());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(LongIterator i) {
/*  228 */     this(i, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(Iterator<?> i, float f) {
/*  239 */     this(LongIterators.asLongIterator(i), f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(Iterator<?> i) {
/*  249 */     this(LongIterators.asLongIterator(i));
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
/*      */   public LongLinkedOpenHashSet(long[] a, int offset, int length, float f) {
/*  264 */     this((length < 0) ? 0 : length, f);
/*  265 */     LongArrays.ensureOffsetLength(a, offset, length);
/*  266 */     for (int i = 0; i < length; i++) {
/*  267 */       add(a[offset + i]);
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
/*      */   public LongLinkedOpenHashSet(long[] a, int offset, int length) {
/*  281 */     this(a, offset, length, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(long[] a, float f) {
/*  292 */     this(a, 0, a.length, f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongLinkedOpenHashSet(long[] a) {
/*  302 */     this(a, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  305 */     return this.containsNull ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  308 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  309 */     if (needed > this.n)
/*  310 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  313 */     int needed = (int)Math.min(1073741824L, 
/*  314 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  315 */     if (needed > this.n)
/*  316 */       rehash(needed); 
/*      */   }
/*      */   
/*      */   public boolean addAll(LongCollection c) {
/*  320 */     if (this.f <= 0.5D) {
/*  321 */       ensureCapacity(c.size());
/*      */     } else {
/*  323 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  325 */     return super.addAll(c);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection<? extends Long> c) {
/*  330 */     if (this.f <= 0.5D) {
/*  331 */       ensureCapacity(c.size());
/*      */     } else {
/*  333 */       tryCapacity((size() + c.size()));
/*      */     } 
/*  335 */     return super.addAll(c);
/*      */   }
/*      */   
/*      */   public boolean add(long k) {
/*      */     int pos;
/*  340 */     if (k == 0L) {
/*  341 */       if (this.containsNull)
/*  342 */         return false; 
/*  343 */       pos = this.n;
/*  344 */       this.containsNull = true;
/*      */     } else {
/*      */       
/*  347 */       long[] key = this.key;
/*      */       long curr;
/*  349 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  350 */         if (curr == k)
/*  351 */           return false; 
/*  352 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  353 */           if (curr == k)
/*  354 */             return false; 
/*      */         } 
/*  356 */       }  key[pos] = k;
/*      */     } 
/*  358 */     if (this.size == 0) {
/*  359 */       this.first = this.last = pos;
/*      */       
/*  361 */       this.link[pos] = -1L;
/*      */     } else {
/*  363 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  364 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  365 */       this.last = pos;
/*      */     } 
/*  367 */     if (this.size++ >= this.maxFill) {
/*  368 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  371 */     return true;
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
/*  384 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  386 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  388 */         if ((curr = key[pos]) == 0L) {
/*  389 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  392 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  393 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  395 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  397 */       key[last] = curr;
/*  398 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  402 */     this.size--;
/*  403 */     fixPointers(pos);
/*  404 */     shiftKeys(pos);
/*  405 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  406 */       rehash(this.n / 2); 
/*  407 */     return true;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  410 */     this.containsNull = false;
/*  411 */     this.key[this.n] = 0L;
/*  412 */     this.size--;
/*  413 */     fixPointers(this.n);
/*  414 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  415 */       rehash(this.n / 2); 
/*  416 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(long k) {
/*  421 */     if (k == 0L) {
/*  422 */       if (this.containsNull)
/*  423 */         return removeNullEntry(); 
/*  424 */       return false;
/*      */     } 
/*      */     
/*  427 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  430 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  431 */       return false; 
/*  432 */     if (k == curr)
/*  433 */       return removeEntry(pos); 
/*      */     while (true) {
/*  435 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  436 */         return false; 
/*  437 */       if (k == curr) {
/*  438 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean contains(long k) {
/*  444 */     if (k == 0L) {
/*  445 */       return this.containsNull;
/*      */     }
/*  447 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  450 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  451 */       return false; 
/*  452 */     if (k == curr)
/*  453 */       return true; 
/*      */     while (true) {
/*  455 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  456 */         return false; 
/*  457 */       if (k == curr) {
/*  458 */         return true;
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
/*  469 */     if (this.size == 0)
/*  470 */       throw new NoSuchElementException(); 
/*  471 */     int pos = this.first;
/*      */     
/*  473 */     this.first = (int)this.link[pos];
/*  474 */     if (0 <= this.first)
/*      */     {
/*  476 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  478 */     long k = this.key[pos];
/*  479 */     this.size--;
/*  480 */     if (k == 0L) {
/*  481 */       this.containsNull = false;
/*  482 */       this.key[this.n] = 0L;
/*      */     } else {
/*  484 */       shiftKeys(pos);
/*  485 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  486 */       rehash(this.n / 2); 
/*  487 */     return k;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  497 */     if (this.size == 0)
/*  498 */       throw new NoSuchElementException(); 
/*  499 */     int pos = this.last;
/*      */     
/*  501 */     this.last = (int)(this.link[pos] >>> 32L);
/*  502 */     if (0 <= this.last)
/*      */     {
/*  504 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  506 */     long k = this.key[pos];
/*  507 */     this.size--;
/*  508 */     if (k == 0L) {
/*  509 */       this.containsNull = false;
/*  510 */       this.key[this.n] = 0L;
/*      */     } else {
/*  512 */       shiftKeys(pos);
/*  513 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  514 */       rehash(this.n / 2); 
/*  515 */     return k;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  518 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  520 */     if (this.last == i) {
/*  521 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  523 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  525 */       long linki = this.link[i];
/*  526 */       int prev = (int)(linki >>> 32L);
/*  527 */       int next = (int)linki;
/*  528 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  529 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  531 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  532 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  533 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  536 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  538 */     if (this.first == i) {
/*  539 */       this.first = (int)this.link[i];
/*      */       
/*  541 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  543 */       long linki = this.link[i];
/*  544 */       int prev = (int)(linki >>> 32L);
/*  545 */       int next = (int)linki;
/*  546 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  547 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  549 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  550 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  551 */     this.last = i;
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
/*  563 */     if (k == 0L) {
/*  564 */       if (this.containsNull) {
/*  565 */         moveIndexToFirst(this.n);
/*  566 */         return false;
/*      */       } 
/*  568 */       this.containsNull = true;
/*  569 */       pos = this.n;
/*      */     } else {
/*      */       
/*  572 */       long[] key = this.key;
/*  573 */       pos = (int)HashCommon.mix(k) & this.mask;
/*      */       
/*  575 */       while (key[pos] != 0L) {
/*  576 */         if (k == key[pos]) {
/*  577 */           moveIndexToFirst(pos);
/*  578 */           return false;
/*      */         } 
/*  580 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  583 */     this.key[pos] = k;
/*  584 */     if (this.size == 0) {
/*  585 */       this.first = this.last = pos;
/*      */       
/*  587 */       this.link[pos] = -1L;
/*      */     } else {
/*  589 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  590 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  591 */       this.first = pos;
/*      */     } 
/*  593 */     if (this.size++ >= this.maxFill) {
/*  594 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  597 */     return true;
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
/*  609 */     if (k == 0L) {
/*  610 */       if (this.containsNull) {
/*  611 */         moveIndexToLast(this.n);
/*  612 */         return false;
/*      */       } 
/*  614 */       this.containsNull = true;
/*  615 */       pos = this.n;
/*      */     } else {
/*      */       
/*  618 */       long[] key = this.key;
/*  619 */       pos = (int)HashCommon.mix(k) & this.mask;
/*      */       
/*  621 */       while (key[pos] != 0L) {
/*  622 */         if (k == key[pos]) {
/*  623 */           moveIndexToLast(pos);
/*  624 */           return false;
/*      */         } 
/*  626 */         pos = pos + 1 & this.mask;
/*      */       } 
/*      */     } 
/*  629 */     this.key[pos] = k;
/*  630 */     if (this.size == 0) {
/*  631 */       this.first = this.last = pos;
/*      */       
/*  633 */       this.link[pos] = -1L;
/*      */     } else {
/*  635 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  636 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  637 */       this.last = pos;
/*      */     } 
/*  639 */     if (this.size++ >= this.maxFill) {
/*  640 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  643 */     return true;
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
/*  654 */     if (this.size == 0)
/*      */       return; 
/*  656 */     this.size = 0;
/*  657 */     this.containsNull = false;
/*  658 */     Arrays.fill(this.key, 0L);
/*  659 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  663 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  667 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/*  677 */     if (this.size == 0) {
/*  678 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  681 */     if (this.first == i) {
/*  682 */       this.first = (int)this.link[i];
/*  683 */       if (0 <= this.first)
/*      */       {
/*  685 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  689 */     if (this.last == i) {
/*  690 */       this.last = (int)(this.link[i] >>> 32L);
/*  691 */       if (0 <= this.last)
/*      */       {
/*  693 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  697 */     long linki = this.link[i];
/*  698 */     int prev = (int)(linki >>> 32L);
/*  699 */     int next = (int)linki;
/*  700 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  701 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  713 */     if (this.size == 1) {
/*  714 */       this.first = this.last = d;
/*      */       
/*  716 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  719 */     if (this.first == s) {
/*  720 */       this.first = d;
/*  721 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  722 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  725 */     if (this.last == s) {
/*  726 */       this.last = d;
/*  727 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  728 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  731 */     long links = this.link[s];
/*  732 */     int prev = (int)(links >>> 32L);
/*  733 */     int next = (int)links;
/*  734 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  735 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  736 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLong() {
/*  745 */     if (this.size == 0)
/*  746 */       throw new NoSuchElementException(); 
/*  747 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLong() {
/*  756 */     if (this.size == 0)
/*  757 */       throw new NoSuchElementException(); 
/*  758 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongSortedSet tailSet(long from) {
/*  767 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongSortedSet headSet(long to) {
/*  776 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongSortedSet subSet(long from, long to) {
/*  785 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/*  794 */     return null;
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
/*  809 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  815 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  820 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  825 */     int index = -1;
/*      */     SetIterator() {
/*  827 */       this.next = LongLinkedOpenHashSet.this.first;
/*  828 */       this.index = 0;
/*      */     }
/*      */     SetIterator(long from) {
/*  831 */       if (from == 0L) {
/*  832 */         if (LongLinkedOpenHashSet.this.containsNull) {
/*  833 */           this.next = (int)LongLinkedOpenHashSet.this.link[LongLinkedOpenHashSet.this.n];
/*  834 */           this.prev = LongLinkedOpenHashSet.this.n;
/*      */           return;
/*      */         } 
/*  837 */         throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */       } 
/*  839 */       if (LongLinkedOpenHashSet.this.key[LongLinkedOpenHashSet.this.last] == from) {
/*  840 */         this.prev = LongLinkedOpenHashSet.this.last;
/*  841 */         this.index = LongLinkedOpenHashSet.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  845 */       long[] key = LongLinkedOpenHashSet.this.key;
/*  846 */       int pos = (int)HashCommon.mix(from) & LongLinkedOpenHashSet.this.mask;
/*      */       
/*  848 */       while (key[pos] != 0L) {
/*  849 */         if (key[pos] == from) {
/*      */           
/*  851 */           this.next = (int)LongLinkedOpenHashSet.this.link[pos];
/*  852 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  855 */         pos = pos + 1 & LongLinkedOpenHashSet.this.mask;
/*      */       } 
/*  857 */       throw new NoSuchElementException("The key " + from + " does not belong to this set.");
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  861 */       return (this.next != -1);
/*      */     }
/*      */     
/*      */     public boolean hasPrevious() {
/*  865 */       return (this.prev != -1);
/*      */     }
/*      */     
/*      */     public long nextLong() {
/*  869 */       if (!hasNext())
/*  870 */         throw new NoSuchElementException(); 
/*  871 */       this.curr = this.next;
/*  872 */       this.next = (int)LongLinkedOpenHashSet.this.link[this.curr];
/*  873 */       this.prev = this.curr;
/*  874 */       if (this.index >= 0) {
/*  875 */         this.index++;
/*      */       }
/*      */       
/*  878 */       return LongLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */     
/*      */     public long previousLong() {
/*  882 */       if (!hasPrevious())
/*  883 */         throw new NoSuchElementException(); 
/*  884 */       this.curr = this.prev;
/*  885 */       this.prev = (int)(LongLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*  886 */       this.next = this.curr;
/*  887 */       if (this.index >= 0)
/*  888 */         this.index--; 
/*  889 */       return LongLinkedOpenHashSet.this.key[this.curr];
/*      */     }
/*      */     private final void ensureIndexKnown() {
/*  892 */       if (this.index >= 0)
/*      */         return; 
/*  894 */       if (this.prev == -1) {
/*  895 */         this.index = 0;
/*      */         return;
/*      */       } 
/*  898 */       if (this.next == -1) {
/*  899 */         this.index = LongLinkedOpenHashSet.this.size;
/*      */         return;
/*      */       } 
/*  902 */       int pos = LongLinkedOpenHashSet.this.first;
/*  903 */       this.index = 1;
/*  904 */       while (pos != this.prev) {
/*  905 */         pos = (int)LongLinkedOpenHashSet.this.link[pos];
/*  906 */         this.index++;
/*      */       } 
/*      */     }
/*      */     
/*      */     public int nextIndex() {
/*  911 */       ensureIndexKnown();
/*  912 */       return this.index;
/*      */     }
/*      */     
/*      */     public int previousIndex() {
/*  916 */       ensureIndexKnown();
/*  917 */       return this.index - 1;
/*      */     }
/*      */     
/*      */     public void remove() {
/*  921 */       ensureIndexKnown();
/*  922 */       if (this.curr == -1)
/*  923 */         throw new IllegalStateException(); 
/*  924 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  929 */         this.index--;
/*  930 */         this.prev = (int)(LongLinkedOpenHashSet.this.link[this.curr] >>> 32L);
/*      */       } else {
/*  932 */         this.next = (int)LongLinkedOpenHashSet.this.link[this.curr];
/*  933 */       }  LongLinkedOpenHashSet.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  938 */       if (this.prev == -1) {
/*  939 */         LongLinkedOpenHashSet.this.first = this.next;
/*      */       } else {
/*  941 */         LongLinkedOpenHashSet.this.link[this.prev] = LongLinkedOpenHashSet.this.link[this.prev] ^ (LongLinkedOpenHashSet.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  942 */       }  if (this.next == -1) {
/*  943 */         LongLinkedOpenHashSet.this.last = this.prev;
/*      */       } else {
/*  945 */         LongLinkedOpenHashSet.this.link[this.next] = LongLinkedOpenHashSet.this.link[this.next] ^ (LongLinkedOpenHashSet.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  946 */       }  int pos = this.curr;
/*  947 */       this.curr = -1;
/*  948 */       if (pos == LongLinkedOpenHashSet.this.n) {
/*  949 */         LongLinkedOpenHashSet.this.containsNull = false;
/*  950 */         LongLinkedOpenHashSet.this.key[LongLinkedOpenHashSet.this.n] = 0L;
/*      */       } else {
/*      */         
/*  953 */         long[] key = LongLinkedOpenHashSet.this.key;
/*      */         while (true) {
/*      */           long curr;
/*      */           int last;
/*  957 */           pos = (last = pos) + 1 & LongLinkedOpenHashSet.this.mask;
/*      */           while (true) {
/*  959 */             if ((curr = key[pos]) == 0L) {
/*  960 */               key[last] = 0L;
/*      */               return;
/*      */             } 
/*  963 */             int slot = (int)HashCommon.mix(curr) & LongLinkedOpenHashSet.this.mask;
/*  964 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/*  966 */             pos = pos + 1 & LongLinkedOpenHashSet.this.mask;
/*      */           } 
/*  968 */           key[last] = curr;
/*  969 */           if (this.next == pos)
/*  970 */             this.next = last; 
/*  971 */           if (this.prev == pos)
/*  972 */             this.prev = last; 
/*  973 */           LongLinkedOpenHashSet.this.fixPointers(pos, last);
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
/*  991 */     return new SetIterator(from);
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
/* 1002 */     return new SetIterator();
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
/* 1019 */     return trim(this.size);
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
/* 1043 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1044 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1045 */       return true; 
/*      */     try {
/* 1047 */       rehash(l);
/* 1048 */     } catch (OutOfMemoryError cantDoIt) {
/* 1049 */       return false;
/*      */     } 
/* 1051 */     return true;
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
/* 1067 */     long[] key = this.key;
/* 1068 */     int mask = newN - 1;
/* 1069 */     long[] newKey = new long[newN + 1];
/* 1070 */     int i = this.first, prev = -1, newPrev = -1;
/* 1071 */     long[] link = this.link;
/* 1072 */     long[] newLink = new long[newN + 1];
/* 1073 */     this.first = -1;
/* 1074 */     for (int j = this.size; j-- != 0; ) {
/* 1075 */       int pos; if (key[i] == 0L) {
/* 1076 */         pos = newN;
/*      */       } else {
/* 1078 */         pos = (int)HashCommon.mix(key[i]) & mask;
/* 1079 */         while (newKey[pos] != 0L)
/* 1080 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1082 */       newKey[pos] = key[i];
/* 1083 */       if (prev != -1) {
/* 1084 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1085 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1086 */         newPrev = pos;
/*      */       } else {
/* 1088 */         newPrev = this.first = pos;
/*      */         
/* 1090 */         newLink[pos] = -1L;
/*      */       } 
/* 1092 */       int t = i;
/* 1093 */       i = (int)link[i];
/* 1094 */       prev = t;
/*      */     } 
/* 1096 */     this.link = newLink;
/* 1097 */     this.last = newPrev;
/* 1098 */     if (newPrev != -1)
/*      */     {
/* 1100 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1101 */     this.n = newN;
/* 1102 */     this.mask = mask;
/* 1103 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1104 */     this.key = newKey;
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
/*      */   public LongLinkedOpenHashSet clone() {
/*      */     LongLinkedOpenHashSet c;
/*      */     try {
/* 1121 */       c = (LongLinkedOpenHashSet)super.clone();
/* 1122 */     } catch (CloneNotSupportedException cantHappen) {
/* 1123 */       throw new InternalError();
/*      */     } 
/* 1125 */     c.key = (long[])this.key.clone();
/* 1126 */     c.containsNull = this.containsNull;
/* 1127 */     c.link = (long[])this.link.clone();
/* 1128 */     return c;
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
/* 1141 */     int h = 0;
/* 1142 */     for (int j = realSize(), i = 0; j-- != 0; ) {
/* 1143 */       while (this.key[i] == 0L)
/* 1144 */         i++; 
/* 1145 */       h += HashCommon.long2int(this.key[i]);
/* 1146 */       i++;
/*      */     } 
/*      */     
/* 1149 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1152 */     LongIterator i = iterator();
/* 1153 */     s.defaultWriteObject();
/* 1154 */     for (int j = this.size; j-- != 0;)
/* 1155 */       s.writeLong(i.nextLong()); 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1159 */     s.defaultReadObject();
/* 1160 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1161 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1162 */     this.mask = this.n - 1;
/* 1163 */     long[] key = this.key = new long[this.n + 1];
/* 1164 */     long[] link = this.link = new long[this.n + 1];
/* 1165 */     int prev = -1;
/* 1166 */     this.first = this.last = -1;
/*      */     
/* 1168 */     for (int i = this.size; i-- != 0; ) {
/* 1169 */       int pos; long k = s.readLong();
/* 1170 */       if (k == 0L) {
/* 1171 */         pos = this.n;
/* 1172 */         this.containsNull = true;
/*      */       }
/* 1174 */       else if (key[pos = (int)HashCommon.mix(k) & this.mask] != 0L) {
/* 1175 */         while (key[pos = pos + 1 & this.mask] != 0L);
/*      */       } 
/* 1177 */       key[pos] = k;
/* 1178 */       if (this.first != -1) {
/* 1179 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1180 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1181 */         prev = pos; continue;
/*      */       } 
/* 1183 */       prev = this.first = pos;
/*      */       
/* 1185 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1188 */     this.last = prev;
/* 1189 */     if (prev != -1)
/*      */     {
/* 1191 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\LongLinkedOpenHashSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */