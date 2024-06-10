/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Comparator;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2LongLinkedOpenHashMap
/*      */   extends AbstractLong2LongSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  107 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  112 */   protected transient int last = -1;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient long[] link;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int n;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient int maxFill;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final transient int minN;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int size;
/*      */ 
/*      */ 
/*      */   
/*      */   protected final float f;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient Long2LongSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new long[this.n + 1];
/*  162 */     this.value = new long[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap() {
/*  180 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Map<? extends Long, ? extends Long> m, float f) {
/*  191 */     this(m.size(), f);
/*  192 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Map<? extends Long, ? extends Long> m) {
/*  202 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Long2LongMap m, float f) {
/*  213 */     this(m.size(), f);
/*  214 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongLinkedOpenHashMap(Long2LongMap m) {
/*  224 */     this(m, 0.75F);
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
/*      */   public Long2LongLinkedOpenHashMap(long[] k, long[] v, float f) {
/*  239 */     this(k.length, f);
/*  240 */     if (k.length != v.length) {
/*  241 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  243 */     for (int i = 0; i < k.length; i++) {
/*  244 */       put(k[i], v[i]);
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
/*      */   public Long2LongLinkedOpenHashMap(long[] k, long[] v) {
/*  258 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  261 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  264 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  265 */     if (needed > this.n)
/*  266 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  269 */     int needed = (int)Math.min(1073741824L, 
/*  270 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  271 */     if (needed > this.n)
/*  272 */       rehash(needed); 
/*      */   }
/*      */   private long removeEntry(int pos) {
/*  275 */     long oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     long oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Long> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  303 */     if (k == 0L) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  309 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  310 */       return -(pos + 1); 
/*  311 */     if (k == curr) {
/*  312 */       return pos;
/*      */     }
/*      */     while (true) {
/*  315 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  316 */         return -(pos + 1); 
/*  317 */       if (k == curr)
/*  318 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, long v) {
/*  322 */     if (pos == this.n)
/*  323 */       this.containsNullKey = true; 
/*  324 */     this.key[pos] = k;
/*  325 */     this.value[pos] = v;
/*  326 */     if (this.size == 0) {
/*  327 */       this.first = this.last = pos;
/*      */       
/*  329 */       this.link[pos] = -1L;
/*      */     } else {
/*  331 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  332 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  333 */       this.last = pos;
/*      */     } 
/*  335 */     if (this.size++ >= this.maxFill) {
/*  336 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(long k, long v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     long oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  352 */     long oldValue = this.value[pos];
/*  353 */     this.value[pos] = oldValue + incr;
/*  354 */     return oldValue;
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
/*      */   public long addTo(long k, long incr) {
/*      */     int pos;
/*  374 */     if (k == 0L) {
/*  375 */       if (this.containsNullKey)
/*  376 */         return addToValue(this.n, incr); 
/*  377 */       pos = this.n;
/*  378 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  381 */       long[] key = this.key;
/*      */       long curr;
/*  383 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  384 */         if (curr == k)
/*  385 */           return addToValue(pos, incr); 
/*  386 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  387 */           if (curr == k)
/*  388 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  391 */     }  this.key[pos] = k;
/*  392 */     this.value[pos] = this.defRetValue + incr;
/*  393 */     if (this.size == 0) {
/*  394 */       this.first = this.last = pos;
/*      */       
/*  396 */       this.link[pos] = -1L;
/*      */     } else {
/*  398 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  399 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  400 */       this.last = pos;
/*      */     } 
/*  402 */     if (this.size++ >= this.maxFill) {
/*  403 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  406 */     return this.defRetValue;
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
/*  419 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  421 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  423 */         if ((curr = key[pos]) == 0L) {
/*  424 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  427 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  428 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  430 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  432 */       key[last] = curr;
/*  433 */       this.value[last] = this.value[pos];
/*  434 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long remove(long k) {
/*  440 */     if (k == 0L) {
/*  441 */       if (this.containsNullKey)
/*  442 */         return removeNullEntry(); 
/*  443 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  446 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  449 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  450 */       return this.defRetValue; 
/*  451 */     if (k == curr)
/*  452 */       return removeEntry(pos); 
/*      */     while (true) {
/*  454 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  455 */         return this.defRetValue; 
/*  456 */       if (k == curr)
/*  457 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private long setValue(int pos, long v) {
/*  461 */     long oldValue = this.value[pos];
/*  462 */     this.value[pos] = v;
/*  463 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeFirstLong() {
/*  474 */     if (this.size == 0)
/*  475 */       throw new NoSuchElementException(); 
/*  476 */     int pos = this.first;
/*      */     
/*  478 */     this.first = (int)this.link[pos];
/*  479 */     if (0 <= this.first)
/*      */     {
/*  481 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  483 */     this.size--;
/*  484 */     long v = this.value[pos];
/*  485 */     if (pos == this.n) {
/*  486 */       this.containsNullKey = false;
/*      */     } else {
/*  488 */       shiftKeys(pos);
/*  489 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  490 */       rehash(this.n / 2); 
/*  491 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  501 */     if (this.size == 0)
/*  502 */       throw new NoSuchElementException(); 
/*  503 */     int pos = this.last;
/*      */     
/*  505 */     this.last = (int)(this.link[pos] >>> 32L);
/*  506 */     if (0 <= this.last)
/*      */     {
/*  508 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  510 */     this.size--;
/*  511 */     long v = this.value[pos];
/*  512 */     if (pos == this.n) {
/*  513 */       this.containsNullKey = false;
/*      */     } else {
/*  515 */       shiftKeys(pos);
/*  516 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  517 */       rehash(this.n / 2); 
/*  518 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  521 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  523 */     if (this.last == i) {
/*  524 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  526 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  528 */       long linki = this.link[i];
/*  529 */       int prev = (int)(linki >>> 32L);
/*  530 */       int next = (int)linki;
/*  531 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  532 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  534 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  535 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  536 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  539 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  541 */     if (this.first == i) {
/*  542 */       this.first = (int)this.link[i];
/*      */       
/*  544 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  546 */       long linki = this.link[i];
/*  547 */       int prev = (int)(linki >>> 32L);
/*  548 */       int next = (int)linki;
/*  549 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  550 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  552 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  553 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  554 */     this.last = i;
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
/*      */   public long getAndMoveToFirst(long k) {
/*  566 */     if (k == 0L) {
/*  567 */       if (this.containsNullKey) {
/*  568 */         moveIndexToFirst(this.n);
/*  569 */         return this.value[this.n];
/*      */       } 
/*  571 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  574 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  577 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  578 */       return this.defRetValue; 
/*  579 */     if (k == curr) {
/*  580 */       moveIndexToFirst(pos);
/*  581 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  585 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  586 */         return this.defRetValue; 
/*  587 */       if (k == curr) {
/*  588 */         moveIndexToFirst(pos);
/*  589 */         return this.value[pos];
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
/*      */   public long getAndMoveToLast(long k) {
/*  603 */     if (k == 0L) {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToLast(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  614 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  615 */       return this.defRetValue; 
/*  616 */     if (k == curr) {
/*  617 */       moveIndexToLast(pos);
/*  618 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  622 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  623 */         return this.defRetValue; 
/*  624 */       if (k == curr) {
/*  625 */         moveIndexToLast(pos);
/*  626 */         return this.value[pos];
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
/*      */   public long putAndMoveToFirst(long k, long v) {
/*      */     int pos;
/*  643 */     if (k == 0L) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToFirst(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       long[] key = this.key;
/*      */       long curr;
/*  654 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  655 */         if (curr == k) {
/*  656 */           moveIndexToFirst(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  660 */           if (curr == k) {
/*  661 */             moveIndexToFirst(pos);
/*  662 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  666 */     }  this.key[pos] = k;
/*  667 */     this.value[pos] = v;
/*  668 */     if (this.size == 0) {
/*  669 */       this.first = this.last = pos;
/*      */       
/*  671 */       this.link[pos] = -1L;
/*      */     } else {
/*  673 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  674 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  675 */       this.first = pos;
/*      */     } 
/*  677 */     if (this.size++ >= this.maxFill) {
/*  678 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  681 */     return this.defRetValue;
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
/*      */   public long putAndMoveToLast(long k, long v) {
/*      */     int pos;
/*  696 */     if (k == 0L) {
/*  697 */       if (this.containsNullKey) {
/*  698 */         moveIndexToLast(this.n);
/*  699 */         return setValue(this.n, v);
/*      */       } 
/*  701 */       this.containsNullKey = true;
/*  702 */       pos = this.n;
/*      */     } else {
/*      */       
/*  705 */       long[] key = this.key;
/*      */       long curr;
/*  707 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  708 */         if (curr == k) {
/*  709 */           moveIndexToLast(pos);
/*  710 */           return setValue(pos, v);
/*      */         } 
/*  712 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  713 */           if (curr == k) {
/*  714 */             moveIndexToLast(pos);
/*  715 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  719 */     }  this.key[pos] = k;
/*  720 */     this.value[pos] = v;
/*  721 */     if (this.size == 0) {
/*  722 */       this.first = this.last = pos;
/*      */       
/*  724 */       this.link[pos] = -1L;
/*      */     } else {
/*  726 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  727 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  728 */       this.last = pos;
/*      */     } 
/*  730 */     if (this.size++ >= this.maxFill) {
/*  731 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  734 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long get(long k) {
/*  739 */     if (k == 0L) {
/*  740 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  742 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  745 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  746 */       return this.defRetValue; 
/*  747 */     if (k == curr) {
/*  748 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  751 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  752 */         return this.defRetValue; 
/*  753 */       if (k == curr) {
/*  754 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  760 */     if (k == 0L) {
/*  761 */       return this.containsNullKey;
/*      */     }
/*  763 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  766 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  767 */       return false; 
/*  768 */     if (k == curr) {
/*  769 */       return true;
/*      */     }
/*      */     while (true) {
/*  772 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  773 */         return false; 
/*  774 */       if (k == curr)
/*  775 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  780 */     long[] value = this.value;
/*  781 */     long[] key = this.key;
/*  782 */     if (this.containsNullKey && value[this.n] == v)
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != 0L && value[i] == v)
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(long k, long defaultValue) {
/*  793 */     if (k == 0L) {
/*  794 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  796 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  799 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  800 */       return defaultValue; 
/*  801 */     if (k == curr) {
/*  802 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  805 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  806 */         return defaultValue; 
/*  807 */       if (k == curr) {
/*  808 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(long k, long v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, long v) {
/*  824 */     if (k == 0L) {
/*  825 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  826 */         removeNullEntry();
/*  827 */         return true;
/*      */       } 
/*  829 */       return false;
/*      */     } 
/*      */     
/*  832 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  835 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  836 */       return false; 
/*  837 */     if (k == curr && v == this.value[pos]) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  843 */         return false; 
/*  844 */       if (k == curr && v == this.value[pos]) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, long oldValue, long v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || oldValue != this.value[pos])
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(long k, long v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     long oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(long k, LongUnaryOperator mappingFunction) {
/*  872 */     Objects.requireNonNull(mappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos >= 0)
/*  875 */       return this.value[pos]; 
/*  876 */     long newValue = mappingFunction.applyAsLong(k);
/*  877 */     insert(-pos - 1, k, newValue);
/*  878 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(long k, LongFunction<? extends Long> mappingFunction) {
/*  884 */     Objects.requireNonNull(mappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos >= 0)
/*  887 */       return this.value[pos]; 
/*  888 */     Long newValue = mappingFunction.apply(k);
/*  889 */     if (newValue == null)
/*  890 */       return this.defRetValue; 
/*  891 */     long v = newValue.longValue();
/*  892 */     insert(-pos - 1, k, v);
/*  893 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Long newValue = remappingFunction.apply(Long.valueOf(k), Long.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == 0L) {
/*  906 */         removeNullEntry();
/*      */       } else {
/*  908 */         removeEntry(pos);
/*  909 */       }  return this.defRetValue;
/*      */     } 
/*  911 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(long k, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Long newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  920 */     if (newValue == null) {
/*  921 */       if (pos >= 0)
/*  922 */         if (k == 0L) {
/*  923 */           removeNullEntry();
/*      */         } else {
/*  925 */           removeEntry(pos);
/*      */         }  
/*  927 */       return this.defRetValue;
/*      */     } 
/*  929 */     long newVal = newValue.longValue();
/*  930 */     if (pos < 0) {
/*  931 */       insert(-pos - 1, k, newVal);
/*  932 */       return newVal;
/*      */     } 
/*  934 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(long k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  940 */     Objects.requireNonNull(remappingFunction);
/*  941 */     int pos = find(k);
/*  942 */     if (pos < 0) {
/*  943 */       insert(-pos - 1, k, v);
/*  944 */       return v;
/*      */     } 
/*  946 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  947 */     if (newValue == null) {
/*  948 */       if (k == 0L) {
/*  949 */         removeNullEntry();
/*      */       } else {
/*  951 */         removeEntry(pos);
/*  952 */       }  return this.defRetValue;
/*      */     } 
/*  954 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  965 */     if (this.size == 0)
/*      */       return; 
/*  967 */     this.size = 0;
/*  968 */     this.containsNullKey = false;
/*  969 */     Arrays.fill(this.key, 0L);
/*  970 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  974 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  978 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2LongMap.Entry, Map.Entry<Long, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  990 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  996 */       return Long2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/* 1000 */       return Long2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/* 1004 */       long oldValue = Long2LongLinkedOpenHashMap.this.value[this.index];
/* 1005 */       Long2LongLinkedOpenHashMap.this.value[this.index] = v;
/* 1006 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/* 1016 */       return Long.valueOf(Long2LongLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/* 1026 */       return Long.valueOf(Long2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/* 1036 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1041 */       if (!(o instanceof Map.Entry))
/* 1042 */         return false; 
/* 1043 */       Map.Entry<Long, Long> e = (Map.Entry<Long, Long>)o;
/* 1044 */       return (Long2LongLinkedOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2LongLinkedOpenHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1048 */       return HashCommon.long2int(Long2LongLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1049 */         HashCommon.long2int(Long2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1053 */       return Long2LongLinkedOpenHashMap.this.key[this.index] + "=>" + Long2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fixPointers(int i) {
/* 1064 */     if (this.size == 0) {
/* 1065 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1068 */     if (this.first == i) {
/* 1069 */       this.first = (int)this.link[i];
/* 1070 */       if (0 <= this.first)
/*      */       {
/* 1072 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1076 */     if (this.last == i) {
/* 1077 */       this.last = (int)(this.link[i] >>> 32L);
/* 1078 */       if (0 <= this.last)
/*      */       {
/* 1080 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1084 */     long linki = this.link[i];
/* 1085 */     int prev = (int)(linki >>> 32L);
/* 1086 */     int next = (int)linki;
/* 1087 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1088 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*      */   protected void fixPointers(int s, int d) {
/* 1101 */     if (this.size == 1) {
/* 1102 */       this.first = this.last = d;
/*      */       
/* 1104 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1107 */     if (this.first == s) {
/* 1108 */       this.first = d;
/* 1109 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1110 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1113 */     if (this.last == s) {
/* 1114 */       this.last = d;
/* 1115 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1116 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1119 */     long links = this.link[s];
/* 1120 */     int prev = (int)(links >>> 32L);
/* 1121 */     int next = (int)links;
/* 1122 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1123 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1124 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLongKey() {
/* 1133 */     if (this.size == 0)
/* 1134 */       throw new NoSuchElementException(); 
/* 1135 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLongKey() {
/* 1144 */     if (this.size == 0)
/* 1145 */       throw new NoSuchElementException(); 
/* 1146 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap tailMap(long from) {
/* 1155 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap headMap(long to) {
/* 1164 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap subMap(long from, long to) {
/* 1173 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/* 1182 */     return null;
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
/*      */   private class MapIterator
/*      */   {
/* 1197 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1203 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1208 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1214 */     int index = -1;
/*      */     protected MapIterator() {
/* 1216 */       this.next = Long2LongLinkedOpenHashMap.this.first;
/* 1217 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(long from) {
/* 1220 */       if (from == 0L) {
/* 1221 */         if (Long2LongLinkedOpenHashMap.this.containsNullKey) {
/* 1222 */           this.next = (int)Long2LongLinkedOpenHashMap.this.link[Long2LongLinkedOpenHashMap.this.n];
/* 1223 */           this.prev = Long2LongLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1226 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1228 */       if (Long2LongLinkedOpenHashMap.this.key[Long2LongLinkedOpenHashMap.this.last] == from) {
/* 1229 */         this.prev = Long2LongLinkedOpenHashMap.this.last;
/* 1230 */         this.index = Long2LongLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1234 */       int pos = (int)HashCommon.mix(from) & Long2LongLinkedOpenHashMap.this.mask;
/*      */       
/* 1236 */       while (Long2LongLinkedOpenHashMap.this.key[pos] != 0L) {
/* 1237 */         if (Long2LongLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1239 */           this.next = (int)Long2LongLinkedOpenHashMap.this.link[pos];
/* 1240 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1243 */         pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1245 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1248 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1251 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1254 */       if (this.index >= 0)
/*      */         return; 
/* 1256 */       if (this.prev == -1) {
/* 1257 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1260 */       if (this.next == -1) {
/* 1261 */         this.index = Long2LongLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1264 */       int pos = Long2LongLinkedOpenHashMap.this.first;
/* 1265 */       this.index = 1;
/* 1266 */       while (pos != this.prev) {
/* 1267 */         pos = (int)Long2LongLinkedOpenHashMap.this.link[pos];
/* 1268 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1272 */       ensureIndexKnown();
/* 1273 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1276 */       ensureIndexKnown();
/* 1277 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1280 */       if (!hasNext())
/* 1281 */         throw new NoSuchElementException(); 
/* 1282 */       this.curr = this.next;
/* 1283 */       this.next = (int)Long2LongLinkedOpenHashMap.this.link[this.curr];
/* 1284 */       this.prev = this.curr;
/* 1285 */       if (this.index >= 0)
/* 1286 */         this.index++; 
/* 1287 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1290 */       if (!hasPrevious())
/* 1291 */         throw new NoSuchElementException(); 
/* 1292 */       this.curr = this.prev;
/* 1293 */       this.prev = (int)(Long2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1294 */       this.next = this.curr;
/* 1295 */       if (this.index >= 0)
/* 1296 */         this.index--; 
/* 1297 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1300 */       ensureIndexKnown();
/* 1301 */       if (this.curr == -1)
/* 1302 */         throw new IllegalStateException(); 
/* 1303 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1308 */         this.index--;
/* 1309 */         this.prev = (int)(Long2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1311 */         this.next = (int)Long2LongLinkedOpenHashMap.this.link[this.curr];
/* 1312 */       }  Long2LongLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       if (this.prev == -1) {
/* 1318 */         Long2LongLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1320 */         Long2LongLinkedOpenHashMap.this.link[this.prev] = Long2LongLinkedOpenHashMap.this.link[this.prev] ^ (Long2LongLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1321 */       }  if (this.next == -1) {
/* 1322 */         Long2LongLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1324 */         Long2LongLinkedOpenHashMap.this.link[this.next] = Long2LongLinkedOpenHashMap.this.link[this.next] ^ (Long2LongLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1325 */       }  int pos = this.curr;
/* 1326 */       this.curr = -1;
/* 1327 */       if (pos == Long2LongLinkedOpenHashMap.this.n) {
/* 1328 */         Long2LongLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1331 */         long[] key = Long2LongLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           long curr;
/*      */           int last;
/* 1335 */           pos = (last = pos) + 1 & Long2LongLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1337 */             if ((curr = key[pos]) == 0L) {
/* 1338 */               key[last] = 0L;
/*      */               return;
/*      */             } 
/* 1341 */             int slot = (int)HashCommon.mix(curr) & Long2LongLinkedOpenHashMap.this.mask;
/* 1342 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1344 */             pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1346 */           key[last] = curr;
/* 1347 */           Long2LongLinkedOpenHashMap.this.value[last] = Long2LongLinkedOpenHashMap.this.value[pos];
/* 1348 */           if (this.next == pos)
/* 1349 */             this.next = last; 
/* 1350 */           if (this.prev == pos)
/* 1351 */             this.prev = last; 
/* 1352 */           Long2LongLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1357 */       int i = n;
/* 1358 */       while (i-- != 0 && hasNext())
/* 1359 */         nextEntry(); 
/* 1360 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1363 */       int i = n;
/* 1364 */       while (i-- != 0 && hasPrevious())
/* 1365 */         previousEntry(); 
/* 1366 */       return n - i - 1;
/*      */     }
/*      */     public void set(Long2LongMap.Entry ok) {
/* 1369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Long2LongMap.Entry ok) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Long2LongMap.Entry> { private Long2LongLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(long from) {
/* 1380 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry next() {
/* 1384 */       return this.entry = new Long2LongLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry previous() {
/* 1388 */       return this.entry = new Long2LongLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1392 */       super.remove();
/* 1393 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1397 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Long2LongMap.Entry> { final Long2LongLinkedOpenHashMap.MapEntry entry = new Long2LongLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(long from) {
/* 1401 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry next() {
/* 1405 */       this.entry.index = nextEntry();
/* 1406 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Long2LongLinkedOpenHashMap.MapEntry previous() {
/* 1410 */       this.entry.index = previousEntry();
/* 1411 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Long2LongMap.Entry> implements Long2LongSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Long2LongMap.Entry> iterator() {
/* 1417 */       return (ObjectBidirectionalIterator<Long2LongMap.Entry>)new Long2LongLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Long2LongMap.Entry> comparator() {
/* 1421 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2LongMap.Entry> subSet(Long2LongMap.Entry fromElement, Long2LongMap.Entry toElement) {
/* 1426 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2LongMap.Entry> headSet(Long2LongMap.Entry toElement) {
/* 1430 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2LongMap.Entry> tailSet(Long2LongMap.Entry fromElement) {
/* 1434 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Long2LongMap.Entry first() {
/* 1438 */       if (Long2LongLinkedOpenHashMap.this.size == 0)
/* 1439 */         throw new NoSuchElementException(); 
/* 1440 */       return new Long2LongLinkedOpenHashMap.MapEntry(Long2LongLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Long2LongMap.Entry last() {
/* 1444 */       if (Long2LongLinkedOpenHashMap.this.size == 0)
/* 1445 */         throw new NoSuchElementException(); 
/* 1446 */       return new Long2LongLinkedOpenHashMap.MapEntry(Long2LongLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1451 */       if (!(o instanceof Map.Entry))
/* 1452 */         return false; 
/* 1453 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1454 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1455 */         return false; 
/* 1456 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1457 */         return false; 
/* 1458 */       long k = ((Long)e.getKey()).longValue();
/* 1459 */       long v = ((Long)e.getValue()).longValue();
/* 1460 */       if (k == 0L) {
/* 1461 */         return (Long2LongLinkedOpenHashMap.this.containsNullKey && Long2LongLinkedOpenHashMap.this.value[Long2LongLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1463 */       long[] key = Long2LongLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1466 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2LongLinkedOpenHashMap.this.mask]) == 0L)
/* 1467 */         return false; 
/* 1468 */       if (k == curr) {
/* 1469 */         return (Long2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1472 */         if ((curr = key[pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask]) == 0L)
/* 1473 */           return false; 
/* 1474 */         if (k == curr) {
/* 1475 */           return (Long2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1481 */       if (!(o instanceof Map.Entry))
/* 1482 */         return false; 
/* 1483 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1484 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1485 */         return false; 
/* 1486 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1487 */         return false; 
/* 1488 */       long k = ((Long)e.getKey()).longValue();
/* 1489 */       long v = ((Long)e.getValue()).longValue();
/* 1490 */       if (k == 0L) {
/* 1491 */         if (Long2LongLinkedOpenHashMap.this.containsNullKey && Long2LongLinkedOpenHashMap.this.value[Long2LongLinkedOpenHashMap.this.n] == v) {
/* 1492 */           Long2LongLinkedOpenHashMap.this.removeNullEntry();
/* 1493 */           return true;
/*      */         } 
/* 1495 */         return false;
/*      */       } 
/*      */       
/* 1498 */       long[] key = Long2LongLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1501 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2LongLinkedOpenHashMap.this.mask]) == 0L)
/* 1502 */         return false; 
/* 1503 */       if (curr == k) {
/* 1504 */         if (Long2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1505 */           Long2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1506 */           return true;
/*      */         } 
/* 1508 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1511 */         if ((curr = key[pos = pos + 1 & Long2LongLinkedOpenHashMap.this.mask]) == 0L)
/* 1512 */           return false; 
/* 1513 */         if (curr == k && 
/* 1514 */           Long2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1515 */           Long2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1516 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1523 */       return Long2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1527 */       Long2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2LongMap.Entry> iterator(Long2LongMap.Entry from) {
/* 1542 */       return new Long2LongLinkedOpenHashMap.EntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2LongMap.Entry> fastIterator() {
/* 1553 */       return new Long2LongLinkedOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2LongMap.Entry> fastIterator(Long2LongMap.Entry from) {
/* 1568 */       return new Long2LongLinkedOpenHashMap.FastEntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1573 */       for (int i = Long2LongLinkedOpenHashMap.this.size, next = Long2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1574 */         int curr = next;
/* 1575 */         next = (int)Long2LongLinkedOpenHashMap.this.link[curr];
/* 1576 */         consumer.accept(new AbstractLong2LongMap.BasicEntry(Long2LongLinkedOpenHashMap.this.key[curr], Long2LongLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2LongMap.Entry> consumer) {
/* 1582 */       AbstractLong2LongMap.BasicEntry entry = new AbstractLong2LongMap.BasicEntry();
/* 1583 */       for (int i = Long2LongLinkedOpenHashMap.this.size, next = Long2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1584 */         int curr = next;
/* 1585 */         next = (int)Long2LongLinkedOpenHashMap.this.link[curr];
/* 1586 */         entry.key = Long2LongLinkedOpenHashMap.this.key[curr];
/* 1587 */         entry.value = Long2LongLinkedOpenHashMap.this.value[curr];
/* 1588 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Long2LongSortedMap.FastSortedEntrySet long2LongEntrySet() {
/* 1594 */     if (this.entries == null)
/* 1595 */       this.entries = new MapEntrySet(); 
/* 1596 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements LongListIterator
/*      */   {
/*      */     public KeyIterator(long k) {
/* 1609 */       super(k);
/*      */     }
/*      */     
/*      */     public long previousLong() {
/* 1613 */       return Long2LongLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public long nextLong() {
/* 1620 */       return Long2LongLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSortedSet { private KeySet() {}
/*      */     
/*      */     public LongListIterator iterator(long from) {
/* 1626 */       return new Long2LongLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public LongListIterator iterator() {
/* 1630 */       return new Long2LongLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1635 */       if (Long2LongLinkedOpenHashMap.this.containsNullKey)
/* 1636 */         consumer.accept(Long2LongLinkedOpenHashMap.this.key[Long2LongLinkedOpenHashMap.this.n]); 
/* 1637 */       for (int pos = Long2LongLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1638 */         long k = Long2LongLinkedOpenHashMap.this.key[pos];
/* 1639 */         if (k != 0L)
/* 1640 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1645 */       return Long2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1649 */       return Long2LongLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1653 */       int oldSize = Long2LongLinkedOpenHashMap.this.size;
/* 1654 */       Long2LongLinkedOpenHashMap.this.remove(k);
/* 1655 */       return (Long2LongLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1659 */       Long2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public long firstLong() {
/* 1663 */       if (Long2LongLinkedOpenHashMap.this.size == 0)
/* 1664 */         throw new NoSuchElementException(); 
/* 1665 */       return Long2LongLinkedOpenHashMap.this.key[Long2LongLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public long lastLong() {
/* 1669 */       if (Long2LongLinkedOpenHashMap.this.size == 0)
/* 1670 */         throw new NoSuchElementException(); 
/* 1671 */       return Long2LongLinkedOpenHashMap.this.key[Long2LongLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public LongComparator comparator() {
/* 1675 */       return null;
/*      */     }
/*      */     
/*      */     public LongSortedSet tailSet(long from) {
/* 1679 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet headSet(long to) {
/* 1683 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet subSet(long from, long to) {
/* 1687 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSortedSet keySet() {
/* 1692 */     if (this.keys == null)
/* 1693 */       this.keys = new KeySet(); 
/* 1694 */     return this.keys;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class ValueIterator
/*      */     extends MapIterator
/*      */     implements LongListIterator
/*      */   {
/*      */     public long previousLong() {
/* 1708 */       return Long2LongLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1715 */       return Long2LongLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1720 */     if (this.values == null)
/* 1721 */       this.values = new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1724 */             return new Long2LongLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1728 */             return Long2LongLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1732 */             return Long2LongLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1736 */             Long2LongLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1741 */             if (Long2LongLinkedOpenHashMap.this.containsNullKey)
/* 1742 */               consumer.accept(Long2LongLinkedOpenHashMap.this.value[Long2LongLinkedOpenHashMap.this.n]); 
/* 1743 */             for (int pos = Long2LongLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1744 */               if (Long2LongLinkedOpenHashMap.this.key[pos] != 0L)
/* 1745 */                 consumer.accept(Long2LongLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1748 */     return this.values;
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
/* 1765 */     return trim(this.size);
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
/* 1789 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1790 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1791 */       return true; 
/*      */     try {
/* 1793 */       rehash(l);
/* 1794 */     } catch (OutOfMemoryError cantDoIt) {
/* 1795 */       return false;
/*      */     } 
/* 1797 */     return true;
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
/* 1813 */     long[] key = this.key;
/* 1814 */     long[] value = this.value;
/* 1815 */     int mask = newN - 1;
/* 1816 */     long[] newKey = new long[newN + 1];
/* 1817 */     long[] newValue = new long[newN + 1];
/* 1818 */     int i = this.first, prev = -1, newPrev = -1;
/* 1819 */     long[] link = this.link;
/* 1820 */     long[] newLink = new long[newN + 1];
/* 1821 */     this.first = -1;
/* 1822 */     for (int j = this.size; j-- != 0; ) {
/* 1823 */       int pos; if (key[i] == 0L) {
/* 1824 */         pos = newN;
/*      */       } else {
/* 1826 */         pos = (int)HashCommon.mix(key[i]) & mask;
/* 1827 */         while (newKey[pos] != 0L)
/* 1828 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1830 */       newKey[pos] = key[i];
/* 1831 */       newValue[pos] = value[i];
/* 1832 */       if (prev != -1) {
/* 1833 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1834 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1835 */         newPrev = pos;
/*      */       } else {
/* 1837 */         newPrev = this.first = pos;
/*      */         
/* 1839 */         newLink[pos] = -1L;
/*      */       } 
/* 1841 */       int t = i;
/* 1842 */       i = (int)link[i];
/* 1843 */       prev = t;
/*      */     } 
/* 1845 */     this.link = newLink;
/* 1846 */     this.last = newPrev;
/* 1847 */     if (newPrev != -1)
/*      */     {
/* 1849 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1850 */     this.n = newN;
/* 1851 */     this.mask = mask;
/* 1852 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1853 */     this.key = newKey;
/* 1854 */     this.value = newValue;
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
/*      */   public Long2LongLinkedOpenHashMap clone() {
/*      */     Long2LongLinkedOpenHashMap c;
/*      */     try {
/* 1871 */       c = (Long2LongLinkedOpenHashMap)super.clone();
/* 1872 */     } catch (CloneNotSupportedException cantHappen) {
/* 1873 */       throw new InternalError();
/*      */     } 
/* 1875 */     c.keys = null;
/* 1876 */     c.values = null;
/* 1877 */     c.entries = null;
/* 1878 */     c.containsNullKey = this.containsNullKey;
/* 1879 */     c.key = (long[])this.key.clone();
/* 1880 */     c.value = (long[])this.value.clone();
/* 1881 */     c.link = (long[])this.link.clone();
/* 1882 */     return c;
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
/* 1895 */     int h = 0;
/* 1896 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1897 */       while (this.key[i] == 0L)
/* 1898 */         i++; 
/* 1899 */       t = HashCommon.long2int(this.key[i]);
/* 1900 */       t ^= HashCommon.long2int(this.value[i]);
/* 1901 */       h += t;
/* 1902 */       i++;
/*      */     } 
/*      */     
/* 1905 */     if (this.containsNullKey)
/* 1906 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1907 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1910 */     long[] key = this.key;
/* 1911 */     long[] value = this.value;
/* 1912 */     MapIterator i = new MapIterator();
/* 1913 */     s.defaultWriteObject();
/* 1914 */     for (int j = this.size; j-- != 0; ) {
/* 1915 */       int e = i.nextEntry();
/* 1916 */       s.writeLong(key[e]);
/* 1917 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1922 */     s.defaultReadObject();
/* 1923 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1924 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1925 */     this.mask = this.n - 1;
/* 1926 */     long[] key = this.key = new long[this.n + 1];
/* 1927 */     long[] value = this.value = new long[this.n + 1];
/* 1928 */     long[] link = this.link = new long[this.n + 1];
/* 1929 */     int prev = -1;
/* 1930 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1933 */     for (int i = this.size; i-- != 0; ) {
/* 1934 */       int pos; long k = s.readLong();
/* 1935 */       long v = s.readLong();
/* 1936 */       if (k == 0L) {
/* 1937 */         pos = this.n;
/* 1938 */         this.containsNullKey = true;
/*      */       } else {
/* 1940 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1941 */         while (key[pos] != 0L)
/* 1942 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1944 */       key[pos] = k;
/* 1945 */       value[pos] = v;
/* 1946 */       if (this.first != -1) {
/* 1947 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1948 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1949 */         prev = pos; continue;
/*      */       } 
/* 1951 */       prev = this.first = pos;
/*      */       
/* 1953 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1956 */     this.last = prev;
/* 1957 */     if (prev != -1)
/*      */     {
/* 1959 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2LongLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */