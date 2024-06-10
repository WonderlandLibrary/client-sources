/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongListIterator;
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
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.ToLongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2LongLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2LongSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*  109 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  114 */   protected transient int last = -1;
/*      */ 
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
/*      */   protected transient Object2LongSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  157 */     this.strategy = strategy;
/*  158 */     if (f <= 0.0F || f > 1.0F)
/*  159 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  160 */     if (expected < 0)
/*  161 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  162 */     this.f = f;
/*  163 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  164 */     this.mask = this.n - 1;
/*  165 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  166 */     this.key = (K[])new Object[this.n + 1];
/*  167 */     this.value = new long[this.n + 1];
/*  168 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  179 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  190 */     this(16, 0.75F, strategy);
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
/*      */   public Object2LongLinkedOpenCustomHashMap(Map<? extends K, ? extends Long> m, float f, Hash.Strategy<? super K> strategy) {
/*  204 */     this(m.size(), f, strategy);
/*  205 */     putAll(m);
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
/*      */   public Object2LongLinkedOpenCustomHashMap(Map<? extends K, ? extends Long> m, Hash.Strategy<? super K> strategy) {
/*  218 */     this(m, 0.75F, strategy);
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
/*      */   public Object2LongLinkedOpenCustomHashMap(Object2LongMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  232 */     this(m.size(), f, strategy);
/*  233 */     putAll(m);
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
/*      */   public Object2LongLinkedOpenCustomHashMap(Object2LongMap<K> m, Hash.Strategy<? super K> strategy) {
/*  245 */     this(m, 0.75F, strategy);
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
/*      */   public Object2LongLinkedOpenCustomHashMap(K[] k, long[] v, float f, Hash.Strategy<? super K> strategy) {
/*  263 */     this(k.length, f, strategy);
/*  264 */     if (k.length != v.length) {
/*  265 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  267 */     for (int i = 0; i < k.length; i++) {
/*  268 */       put(k[i], v[i]);
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
/*      */   public Object2LongLinkedOpenCustomHashMap(K[] k, long[] v, Hash.Strategy<? super K> strategy) {
/*  284 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  292 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  295 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  298 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  299 */     if (needed > this.n)
/*  300 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  303 */     int needed = (int)Math.min(1073741824L, 
/*  304 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  305 */     if (needed > this.n)
/*  306 */       rehash(needed); 
/*      */   }
/*      */   private long removeEntry(int pos) {
/*  309 */     long oldValue = this.value[pos];
/*  310 */     this.size--;
/*  311 */     fixPointers(pos);
/*  312 */     shiftKeys(pos);
/*  313 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  314 */       rehash(this.n / 2); 
/*  315 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  318 */     this.containsNullKey = false;
/*  319 */     this.key[this.n] = null;
/*  320 */     long oldValue = this.value[this.n];
/*  321 */     this.size--;
/*  322 */     fixPointers(this.n);
/*  323 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  324 */       rehash(this.n / 2); 
/*  325 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Long> m) {
/*  329 */     if (this.f <= 0.5D) {
/*  330 */       ensureCapacity(m.size());
/*      */     } else {
/*  332 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  334 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  338 */     if (this.strategy.equals(k, null)) {
/*  339 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  341 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  344 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  345 */       return -(pos + 1); 
/*  346 */     if (this.strategy.equals(k, curr)) {
/*  347 */       return pos;
/*      */     }
/*      */     while (true) {
/*  350 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  351 */         return -(pos + 1); 
/*  352 */       if (this.strategy.equals(k, curr))
/*  353 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, long v) {
/*  357 */     if (pos == this.n)
/*  358 */       this.containsNullKey = true; 
/*  359 */     this.key[pos] = k;
/*  360 */     this.value[pos] = v;
/*  361 */     if (this.size == 0) {
/*  362 */       this.first = this.last = pos;
/*      */       
/*  364 */       this.link[pos] = -1L;
/*      */     } else {
/*  366 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  367 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  368 */       this.last = pos;
/*      */     } 
/*  370 */     if (this.size++ >= this.maxFill) {
/*  371 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(K k, long v) {
/*  377 */     int pos = find(k);
/*  378 */     if (pos < 0) {
/*  379 */       insert(-pos - 1, k, v);
/*  380 */       return this.defRetValue;
/*      */     } 
/*  382 */     long oldValue = this.value[pos];
/*  383 */     this.value[pos] = v;
/*  384 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  387 */     long oldValue = this.value[pos];
/*  388 */     this.value[pos] = oldValue + incr;
/*  389 */     return oldValue;
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
/*      */   public long addTo(K k, long incr) {
/*      */     int pos;
/*  409 */     if (this.strategy.equals(k, null)) {
/*  410 */       if (this.containsNullKey)
/*  411 */         return addToValue(this.n, incr); 
/*  412 */       pos = this.n;
/*  413 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  416 */       K[] key = this.key;
/*      */       K curr;
/*  418 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  419 */         if (this.strategy.equals(curr, k))
/*  420 */           return addToValue(pos, incr); 
/*  421 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  422 */           if (this.strategy.equals(curr, k))
/*  423 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  426 */     }  this.key[pos] = k;
/*  427 */     this.value[pos] = this.defRetValue + incr;
/*  428 */     if (this.size == 0) {
/*  429 */       this.first = this.last = pos;
/*      */       
/*  431 */       this.link[pos] = -1L;
/*      */     } else {
/*  433 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  434 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  435 */       this.last = pos;
/*      */     } 
/*  437 */     if (this.size++ >= this.maxFill) {
/*  438 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  441 */     return this.defRetValue;
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
/*  454 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  456 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  458 */         if ((curr = key[pos]) == null) {
/*  459 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  462 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  463 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  465 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  467 */       key[last] = curr;
/*  468 */       this.value[last] = this.value[pos];
/*  469 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long removeLong(Object k) {
/*  475 */     if (this.strategy.equals(k, null)) {
/*  476 */       if (this.containsNullKey)
/*  477 */         return removeNullEntry(); 
/*  478 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  481 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  484 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  485 */       return this.defRetValue; 
/*  486 */     if (this.strategy.equals(k, curr))
/*  487 */       return removeEntry(pos); 
/*      */     while (true) {
/*  489 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  490 */         return this.defRetValue; 
/*  491 */       if (this.strategy.equals(k, curr))
/*  492 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private long setValue(int pos, long v) {
/*  496 */     long oldValue = this.value[pos];
/*  497 */     this.value[pos] = v;
/*  498 */     return oldValue;
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
/*  509 */     if (this.size == 0)
/*  510 */       throw new NoSuchElementException(); 
/*  511 */     int pos = this.first;
/*      */     
/*  513 */     this.first = (int)this.link[pos];
/*  514 */     if (0 <= this.first)
/*      */     {
/*  516 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  518 */     this.size--;
/*  519 */     long v = this.value[pos];
/*  520 */     if (pos == this.n) {
/*  521 */       this.containsNullKey = false;
/*  522 */       this.key[this.n] = null;
/*      */     } else {
/*  524 */       shiftKeys(pos);
/*  525 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  526 */       rehash(this.n / 2); 
/*  527 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  537 */     if (this.size == 0)
/*  538 */       throw new NoSuchElementException(); 
/*  539 */     int pos = this.last;
/*      */     
/*  541 */     this.last = (int)(this.link[pos] >>> 32L);
/*  542 */     if (0 <= this.last)
/*      */     {
/*  544 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  546 */     this.size--;
/*  547 */     long v = this.value[pos];
/*  548 */     if (pos == this.n) {
/*  549 */       this.containsNullKey = false;
/*  550 */       this.key[this.n] = null;
/*      */     } else {
/*  552 */       shiftKeys(pos);
/*  553 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  554 */       rehash(this.n / 2); 
/*  555 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  558 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  560 */     if (this.last == i) {
/*  561 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  563 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  565 */       long linki = this.link[i];
/*  566 */       int prev = (int)(linki >>> 32L);
/*  567 */       int next = (int)linki;
/*  568 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  569 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  571 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  572 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  573 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  576 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  578 */     if (this.first == i) {
/*  579 */       this.first = (int)this.link[i];
/*      */       
/*  581 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  583 */       long linki = this.link[i];
/*  584 */       int prev = (int)(linki >>> 32L);
/*  585 */       int next = (int)linki;
/*  586 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  587 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  589 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  590 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  591 */     this.last = i;
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
/*      */   public long getAndMoveToFirst(K k) {
/*  603 */     if (this.strategy.equals(k, null)) {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToFirst(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  614 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  615 */       return this.defRetValue; 
/*  616 */     if (this.strategy.equals(k, curr)) {
/*  617 */       moveIndexToFirst(pos);
/*  618 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  622 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  623 */         return this.defRetValue; 
/*  624 */       if (this.strategy.equals(k, curr)) {
/*  625 */         moveIndexToFirst(pos);
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
/*      */   public long getAndMoveToLast(K k) {
/*  640 */     if (this.strategy.equals(k, null)) {
/*  641 */       if (this.containsNullKey) {
/*  642 */         moveIndexToLast(this.n);
/*  643 */         return this.value[this.n];
/*      */       } 
/*  645 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  648 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  651 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  652 */       return this.defRetValue; 
/*  653 */     if (this.strategy.equals(k, curr)) {
/*  654 */       moveIndexToLast(pos);
/*  655 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  659 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  660 */         return this.defRetValue; 
/*  661 */       if (this.strategy.equals(k, curr)) {
/*  662 */         moveIndexToLast(pos);
/*  663 */         return this.value[pos];
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
/*      */   public long putAndMoveToFirst(K k, long v) {
/*      */     int pos;
/*  680 */     if (this.strategy.equals(k, null)) {
/*  681 */       if (this.containsNullKey) {
/*  682 */         moveIndexToFirst(this.n);
/*  683 */         return setValue(this.n, v);
/*      */       } 
/*  685 */       this.containsNullKey = true;
/*  686 */       pos = this.n;
/*      */     } else {
/*      */       
/*  689 */       K[] key = this.key;
/*      */       K curr;
/*  691 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  692 */         if (this.strategy.equals(curr, k)) {
/*  693 */           moveIndexToFirst(pos);
/*  694 */           return setValue(pos, v);
/*      */         } 
/*  696 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  697 */           if (this.strategy.equals(curr, k)) {
/*  698 */             moveIndexToFirst(pos);
/*  699 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  703 */     }  this.key[pos] = k;
/*  704 */     this.value[pos] = v;
/*  705 */     if (this.size == 0) {
/*  706 */       this.first = this.last = pos;
/*      */       
/*  708 */       this.link[pos] = -1L;
/*      */     } else {
/*  710 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  711 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  712 */       this.first = pos;
/*      */     } 
/*  714 */     if (this.size++ >= this.maxFill) {
/*  715 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  718 */     return this.defRetValue;
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
/*      */   public long putAndMoveToLast(K k, long v) {
/*      */     int pos;
/*  733 */     if (this.strategy.equals(k, null)) {
/*  734 */       if (this.containsNullKey) {
/*  735 */         moveIndexToLast(this.n);
/*  736 */         return setValue(this.n, v);
/*      */       } 
/*  738 */       this.containsNullKey = true;
/*  739 */       pos = this.n;
/*      */     } else {
/*      */       
/*  742 */       K[] key = this.key;
/*      */       K curr;
/*  744 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  745 */         if (this.strategy.equals(curr, k)) {
/*  746 */           moveIndexToLast(pos);
/*  747 */           return setValue(pos, v);
/*      */         } 
/*  749 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  750 */           if (this.strategy.equals(curr, k)) {
/*  751 */             moveIndexToLast(pos);
/*  752 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  756 */     }  this.key[pos] = k;
/*  757 */     this.value[pos] = v;
/*  758 */     if (this.size == 0) {
/*  759 */       this.first = this.last = pos;
/*      */       
/*  761 */       this.link[pos] = -1L;
/*      */     } else {
/*  763 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  764 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  765 */       this.last = pos;
/*      */     } 
/*  767 */     if (this.size++ >= this.maxFill) {
/*  768 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  771 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(Object k) {
/*  776 */     if (this.strategy.equals(k, null)) {
/*  777 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  779 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  782 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  783 */       return this.defRetValue; 
/*  784 */     if (this.strategy.equals(k, curr)) {
/*  785 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  788 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  789 */         return this.defRetValue; 
/*  790 */       if (this.strategy.equals(k, curr)) {
/*  791 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  797 */     if (this.strategy.equals(k, null)) {
/*  798 */       return this.containsNullKey;
/*      */     }
/*  800 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  803 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  804 */       return false; 
/*  805 */     if (this.strategy.equals(k, curr)) {
/*  806 */       return true;
/*      */     }
/*      */     while (true) {
/*  809 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  810 */         return false; 
/*  811 */       if (this.strategy.equals(k, curr))
/*  812 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  817 */     long[] value = this.value;
/*  818 */     K[] key = this.key;
/*  819 */     if (this.containsNullKey && value[this.n] == v)
/*  820 */       return true; 
/*  821 */     for (int i = this.n; i-- != 0;) {
/*  822 */       if (key[i] != null && value[i] == v)
/*  823 */         return true; 
/*  824 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(Object k, long defaultValue) {
/*  830 */     if (this.strategy.equals(k, null)) {
/*  831 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  833 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  836 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  837 */       return defaultValue; 
/*  838 */     if (this.strategy.equals(k, curr)) {
/*  839 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  843 */         return defaultValue; 
/*  844 */       if (this.strategy.equals(k, curr)) {
/*  845 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(K k, long v) {
/*  851 */     int pos = find(k);
/*  852 */     if (pos >= 0)
/*  853 */       return this.value[pos]; 
/*  854 */     insert(-pos - 1, k, v);
/*  855 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, long v) {
/*  861 */     if (this.strategy.equals(k, null)) {
/*  862 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  863 */         removeNullEntry();
/*  864 */         return true;
/*      */       } 
/*  866 */       return false;
/*      */     } 
/*      */     
/*  869 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  872 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  873 */       return false; 
/*  874 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  875 */       removeEntry(pos);
/*  876 */       return true;
/*      */     } 
/*      */     while (true) {
/*  879 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  880 */         return false; 
/*  881 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  882 */         removeEntry(pos);
/*  883 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, long oldValue, long v) {
/*  890 */     int pos = find(k);
/*  891 */     if (pos < 0 || oldValue != this.value[pos])
/*  892 */       return false; 
/*  893 */     this.value[pos] = v;
/*  894 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(K k, long v) {
/*  899 */     int pos = find(k);
/*  900 */     if (pos < 0)
/*  901 */       return this.defRetValue; 
/*  902 */     long oldValue = this.value[pos];
/*  903 */     this.value[pos] = v;
/*  904 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeLongIfAbsent(K k, ToLongFunction<? super K> mappingFunction) {
/*  909 */     Objects.requireNonNull(mappingFunction);
/*  910 */     int pos = find(k);
/*  911 */     if (pos >= 0)
/*  912 */       return this.value[pos]; 
/*  913 */     long newValue = mappingFunction.applyAsLong(k);
/*  914 */     insert(-pos - 1, k, newValue);
/*  915 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  921 */     Objects.requireNonNull(remappingFunction);
/*  922 */     int pos = find(k);
/*  923 */     if (pos < 0)
/*  924 */       return this.defRetValue; 
/*  925 */     Long newValue = remappingFunction.apply(k, Long.valueOf(this.value[pos]));
/*  926 */     if (newValue == null) {
/*  927 */       if (this.strategy.equals(k, null)) {
/*  928 */         removeNullEntry();
/*      */       } else {
/*  930 */         removeEntry(pos);
/*  931 */       }  return this.defRetValue;
/*      */     } 
/*  933 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  939 */     Objects.requireNonNull(remappingFunction);
/*  940 */     int pos = find(k);
/*  941 */     Long newValue = remappingFunction.apply(k, (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  942 */     if (newValue == null) {
/*  943 */       if (pos >= 0)
/*  944 */         if (this.strategy.equals(k, null)) {
/*  945 */           removeNullEntry();
/*      */         } else {
/*  947 */           removeEntry(pos);
/*      */         }  
/*  949 */       return this.defRetValue;
/*      */     } 
/*  951 */     long newVal = newValue.longValue();
/*  952 */     if (pos < 0) {
/*  953 */       insert(-pos - 1, k, newVal);
/*  954 */       return newVal;
/*      */     } 
/*  956 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long mergeLong(K k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  962 */     Objects.requireNonNull(remappingFunction);
/*  963 */     int pos = find(k);
/*  964 */     if (pos < 0) {
/*  965 */       insert(-pos - 1, k, v);
/*  966 */       return v;
/*      */     } 
/*  968 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  969 */     if (newValue == null) {
/*  970 */       if (this.strategy.equals(k, null)) {
/*  971 */         removeNullEntry();
/*      */       } else {
/*  973 */         removeEntry(pos);
/*  974 */       }  return this.defRetValue;
/*      */     } 
/*  976 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  987 */     if (this.size == 0)
/*      */       return; 
/*  989 */     this.size = 0;
/*  990 */     this.containsNullKey = false;
/*  991 */     Arrays.fill((Object[])this.key, (Object)null);
/*  992 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  996 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1000 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2LongMap.Entry<K>, Map.Entry<K, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1012 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/* 1018 */       return Object2LongLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/* 1022 */       return Object2LongLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/* 1026 */       long oldValue = Object2LongLinkedOpenCustomHashMap.this.value[this.index];
/* 1027 */       Object2LongLinkedOpenCustomHashMap.this.value[this.index] = v;
/* 1028 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/* 1038 */       return Long.valueOf(Object2LongLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/* 1048 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1053 */       if (!(o instanceof Map.Entry))
/* 1054 */         return false; 
/* 1055 */       Map.Entry<K, Long> e = (Map.Entry<K, Long>)o;
/* 1056 */       return (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(Object2LongLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2LongLinkedOpenCustomHashMap.this.value[this.index] == ((Long)e.getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1060 */       return Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(Object2LongLinkedOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Object2LongLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1064 */       return (new StringBuilder()).append(Object2LongLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2LongLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/* 1075 */     if (this.size == 0) {
/* 1076 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1079 */     if (this.first == i) {
/* 1080 */       this.first = (int)this.link[i];
/* 1081 */       if (0 <= this.first)
/*      */       {
/* 1083 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1087 */     if (this.last == i) {
/* 1088 */       this.last = (int)(this.link[i] >>> 32L);
/* 1089 */       if (0 <= this.last)
/*      */       {
/* 1091 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1095 */     long linki = this.link[i];
/* 1096 */     int prev = (int)(linki >>> 32L);
/* 1097 */     int next = (int)linki;
/* 1098 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1099 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1112 */     if (this.size == 1) {
/* 1113 */       this.first = this.last = d;
/*      */       
/* 1115 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1118 */     if (this.first == s) {
/* 1119 */       this.first = d;
/* 1120 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1121 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1124 */     if (this.last == s) {
/* 1125 */       this.last = d;
/* 1126 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1127 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1130 */     long links = this.link[s];
/* 1131 */     int prev = (int)(links >>> 32L);
/* 1132 */     int next = (int)links;
/* 1133 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1134 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1135 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1144 */     if (this.size == 0)
/* 1145 */       throw new NoSuchElementException(); 
/* 1146 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1155 */     if (this.size == 0)
/* 1156 */       throw new NoSuchElementException(); 
/* 1157 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> tailMap(K from) {
/* 1166 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> headMap(K to) {
/* 1175 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> subMap(K from, K to) {
/* 1184 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1193 */     return null;
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
/* 1208 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1214 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1219 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1225 */     int index = -1;
/*      */     protected MapIterator() {
/* 1227 */       this.next = Object2LongLinkedOpenCustomHashMap.this.first;
/* 1228 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1231 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1232 */         if (Object2LongLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1233 */           this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[Object2LongLinkedOpenCustomHashMap.this.n];
/* 1234 */           this.prev = Object2LongLinkedOpenCustomHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1237 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1239 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.last], from)) {
/* 1240 */         this.prev = Object2LongLinkedOpenCustomHashMap.this.last;
/* 1241 */         this.index = Object2LongLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1245 */       int pos = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1247 */       while (Object2LongLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1248 */         if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(Object2LongLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1250 */           this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[pos];
/* 1251 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1254 */         pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1256 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1259 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1262 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1265 */       if (this.index >= 0)
/*      */         return; 
/* 1267 */       if (this.prev == -1) {
/* 1268 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1271 */       if (this.next == -1) {
/* 1272 */         this.index = Object2LongLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1275 */       int pos = Object2LongLinkedOpenCustomHashMap.this.first;
/* 1276 */       this.index = 1;
/* 1277 */       while (pos != this.prev) {
/* 1278 */         pos = (int)Object2LongLinkedOpenCustomHashMap.this.link[pos];
/* 1279 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1283 */       ensureIndexKnown();
/* 1284 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1287 */       ensureIndexKnown();
/* 1288 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1291 */       if (!hasNext())
/* 1292 */         throw new NoSuchElementException(); 
/* 1293 */       this.curr = this.next;
/* 1294 */       this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[this.curr];
/* 1295 */       this.prev = this.curr;
/* 1296 */       if (this.index >= 0)
/* 1297 */         this.index++; 
/* 1298 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1301 */       if (!hasPrevious())
/* 1302 */         throw new NoSuchElementException(); 
/* 1303 */       this.curr = this.prev;
/* 1304 */       this.prev = (int)(Object2LongLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1305 */       this.next = this.curr;
/* 1306 */       if (this.index >= 0)
/* 1307 */         this.index--; 
/* 1308 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1311 */       ensureIndexKnown();
/* 1312 */       if (this.curr == -1)
/* 1313 */         throw new IllegalStateException(); 
/* 1314 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1319 */         this.index--;
/* 1320 */         this.prev = (int)(Object2LongLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1322 */         this.next = (int)Object2LongLinkedOpenCustomHashMap.this.link[this.curr];
/* 1323 */       }  Object2LongLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1328 */       if (this.prev == -1) {
/* 1329 */         Object2LongLinkedOpenCustomHashMap.this.first = this.next;
/*      */       } else {
/* 1331 */         Object2LongLinkedOpenCustomHashMap.this.link[this.prev] = Object2LongLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2LongLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1332 */       }  if (this.next == -1) {
/* 1333 */         Object2LongLinkedOpenCustomHashMap.this.last = this.prev;
/*      */       } else {
/* 1335 */         Object2LongLinkedOpenCustomHashMap.this.link[this.next] = Object2LongLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2LongLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1336 */       }  int pos = this.curr;
/* 1337 */       this.curr = -1;
/* 1338 */       if (pos == Object2LongLinkedOpenCustomHashMap.this.n) {
/* 1339 */         Object2LongLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1340 */         Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1343 */         K[] key = Object2LongLinkedOpenCustomHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1347 */           pos = (last = pos) + 1 & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1349 */             if ((curr = key[pos]) == null) {
/* 1350 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1353 */             int slot = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2LongLinkedOpenCustomHashMap.this.mask;
/* 1354 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1356 */             pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1358 */           key[last] = curr;
/* 1359 */           Object2LongLinkedOpenCustomHashMap.this.value[last] = Object2LongLinkedOpenCustomHashMap.this.value[pos];
/* 1360 */           if (this.next == pos)
/* 1361 */             this.next = last; 
/* 1362 */           if (this.prev == pos)
/* 1363 */             this.prev = last; 
/* 1364 */           Object2LongLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1369 */       int i = n;
/* 1370 */       while (i-- != 0 && hasNext())
/* 1371 */         nextEntry(); 
/* 1372 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1375 */       int i = n;
/* 1376 */       while (i-- != 0 && hasPrevious())
/* 1377 */         previousEntry(); 
/* 1378 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2LongMap.Entry<K> ok) {
/* 1381 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2LongMap.Entry<K> ok) {
/* 1384 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2LongMap.Entry<K>> { private Object2LongLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1392 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1396 */       return this.entry = new Object2LongLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1400 */       return this.entry = new Object2LongLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1404 */       super.remove();
/* 1405 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1409 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2LongMap.Entry<K>> { final Object2LongLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2LongLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1413 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1417 */       this.entry.index = nextEntry();
/* 1418 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1422 */       this.entry.index = previousEntry();
/* 1423 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2LongMap.Entry<K>> implements Object2LongSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> iterator() {
/* 1431 */       return new Object2LongLinkedOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2LongMap.Entry<K>> comparator() {
/* 1435 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> subSet(Object2LongMap.Entry<K> fromElement, Object2LongMap.Entry<K> toElement) {
/* 1440 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> headSet(Object2LongMap.Entry<K> toElement) {
/* 1444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> tailSet(Object2LongMap.Entry<K> fromElement) {
/* 1448 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2LongMap.Entry<K> first() {
/* 1452 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0)
/* 1453 */         throw new NoSuchElementException(); 
/* 1454 */       return new Object2LongLinkedOpenCustomHashMap.MapEntry(Object2LongLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2LongMap.Entry<K> last() {
/* 1458 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0)
/* 1459 */         throw new NoSuchElementException(); 
/* 1460 */       return new Object2LongLinkedOpenCustomHashMap.MapEntry(Object2LongLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1465 */       if (!(o instanceof Map.Entry))
/* 1466 */         return false; 
/* 1467 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1468 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1469 */         return false; 
/* 1470 */       K k = (K)e.getKey();
/* 1471 */       long v = ((Long)e.getValue()).longValue();
/* 1472 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1473 */         return (Object2LongLinkedOpenCustomHashMap.this.containsNullKey && Object2LongLinkedOpenCustomHashMap.this.value[Object2LongLinkedOpenCustomHashMap.this.n] == v);
/*      */       }
/* 1475 */       K[] key = Object2LongLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1478 */       if ((curr = key[pos = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2LongLinkedOpenCustomHashMap.this.mask]) == null)
/* 1479 */         return false; 
/* 1480 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1481 */         return (Object2LongLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1484 */         if ((curr = key[pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask]) == null)
/* 1485 */           return false; 
/* 1486 */         if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1487 */           return (Object2LongLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1493 */       if (!(o instanceof Map.Entry))
/* 1494 */         return false; 
/* 1495 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1496 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1497 */         return false; 
/* 1498 */       K k = (K)e.getKey();
/* 1499 */       long v = ((Long)e.getValue()).longValue();
/* 1500 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1501 */         if (Object2LongLinkedOpenCustomHashMap.this.containsNullKey && Object2LongLinkedOpenCustomHashMap.this.value[Object2LongLinkedOpenCustomHashMap.this.n] == v) {
/* 1502 */           Object2LongLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1503 */           return true;
/*      */         } 
/* 1505 */         return false;
/*      */       } 
/*      */       
/* 1508 */       K[] key = Object2LongLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1511 */       if ((curr = key[pos = HashCommon.mix(Object2LongLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2LongLinkedOpenCustomHashMap.this.mask]) == null)
/* 1512 */         return false; 
/* 1513 */       if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1514 */         if (Object2LongLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1515 */           Object2LongLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1516 */           return true;
/*      */         } 
/* 1518 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1521 */         if ((curr = key[pos = pos + 1 & Object2LongLinkedOpenCustomHashMap.this.mask]) == null)
/* 1522 */           return false; 
/* 1523 */         if (Object2LongLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1524 */           Object2LongLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1525 */           Object2LongLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1526 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1533 */       return Object2LongLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1537 */       Object2LongLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> iterator(Object2LongMap.Entry<K> from) {
/* 1552 */       return new Object2LongLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> fastIterator() {
/* 1563 */       return new Object2LongLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap.Entry<K> from) {
/* 1578 */       return new Object2LongLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/* 1583 */       for (int i = Object2LongLinkedOpenCustomHashMap.this.size, next = Object2LongLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1584 */         int curr = next;
/* 1585 */         next = (int)Object2LongLinkedOpenCustomHashMap.this.link[curr];
/* 1586 */         consumer.accept(new AbstractObject2LongMap.BasicEntry<>(Object2LongLinkedOpenCustomHashMap.this.key[curr], Object2LongLinkedOpenCustomHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/* 1592 */       AbstractObject2LongMap.BasicEntry<K> entry = new AbstractObject2LongMap.BasicEntry<>();
/* 1593 */       for (int i = Object2LongLinkedOpenCustomHashMap.this.size, next = Object2LongLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1594 */         int curr = next;
/* 1595 */         next = (int)Object2LongLinkedOpenCustomHashMap.this.link[curr];
/* 1596 */         entry.key = Object2LongLinkedOpenCustomHashMap.this.key[curr];
/* 1597 */         entry.value = Object2LongLinkedOpenCustomHashMap.this.value[curr];
/* 1598 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap.FastSortedEntrySet<K> object2LongEntrySet() {
/* 1604 */     if (this.entries == null)
/* 1605 */       this.entries = new MapEntrySet(); 
/* 1606 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements ObjectListIterator<K>
/*      */   {
/*      */     public KeyIterator(K k) {
/* 1619 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1623 */       return Object2LongLinkedOpenCustomHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1630 */       return Object2LongLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1636 */       return new Object2LongLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1640 */       return new Object2LongLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1645 */       if (Object2LongLinkedOpenCustomHashMap.this.containsNullKey)
/* 1646 */         consumer.accept(Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.n]); 
/* 1647 */       for (int pos = Object2LongLinkedOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1648 */         K k = Object2LongLinkedOpenCustomHashMap.this.key[pos];
/* 1649 */         if (k != null)
/* 1650 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1655 */       return Object2LongLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1659 */       return Object2LongLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1663 */       int oldSize = Object2LongLinkedOpenCustomHashMap.this.size;
/* 1664 */       Object2LongLinkedOpenCustomHashMap.this.removeLong(k);
/* 1665 */       return (Object2LongLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1669 */       Object2LongLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1673 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0)
/* 1674 */         throw new NoSuchElementException(); 
/* 1675 */       return Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1679 */       if (Object2LongLinkedOpenCustomHashMap.this.size == 0)
/* 1680 */         throw new NoSuchElementException(); 
/* 1681 */       return Object2LongLinkedOpenCustomHashMap.this.key[Object2LongLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1685 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1689 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1693 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1697 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1702 */     if (this.keys == null)
/* 1703 */       this.keys = new KeySet(); 
/* 1704 */     return this.keys;
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
/* 1718 */       return Object2LongLinkedOpenCustomHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1725 */       return Object2LongLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1730 */     if (this.values == null)
/* 1731 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1734 */             return (LongIterator)new Object2LongLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1738 */             return Object2LongLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1742 */             return Object2LongLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1746 */             Object2LongLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1751 */             if (Object2LongLinkedOpenCustomHashMap.this.containsNullKey)
/* 1752 */               consumer.accept(Object2LongLinkedOpenCustomHashMap.this.value[Object2LongLinkedOpenCustomHashMap.this.n]); 
/* 1753 */             for (int pos = Object2LongLinkedOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1754 */               if (Object2LongLinkedOpenCustomHashMap.this.key[pos] != null)
/* 1755 */                 consumer.accept(Object2LongLinkedOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1758 */     return this.values;
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
/* 1775 */     return trim(this.size);
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
/* 1799 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1800 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1801 */       return true; 
/*      */     try {
/* 1803 */       rehash(l);
/* 1804 */     } catch (OutOfMemoryError cantDoIt) {
/* 1805 */       return false;
/*      */     } 
/* 1807 */     return true;
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
/* 1823 */     K[] key = this.key;
/* 1824 */     long[] value = this.value;
/* 1825 */     int mask = newN - 1;
/* 1826 */     K[] newKey = (K[])new Object[newN + 1];
/* 1827 */     long[] newValue = new long[newN + 1];
/* 1828 */     int i = this.first, prev = -1, newPrev = -1;
/* 1829 */     long[] link = this.link;
/* 1830 */     long[] newLink = new long[newN + 1];
/* 1831 */     this.first = -1;
/* 1832 */     for (int j = this.size; j-- != 0; ) {
/* 1833 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1834 */         pos = newN;
/*      */       } else {
/* 1836 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1837 */         while (newKey[pos] != null)
/* 1838 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1840 */       newKey[pos] = key[i];
/* 1841 */       newValue[pos] = value[i];
/* 1842 */       if (prev != -1) {
/* 1843 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1844 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1845 */         newPrev = pos;
/*      */       } else {
/* 1847 */         newPrev = this.first = pos;
/*      */         
/* 1849 */         newLink[pos] = -1L;
/*      */       } 
/* 1851 */       int t = i;
/* 1852 */       i = (int)link[i];
/* 1853 */       prev = t;
/*      */     } 
/* 1855 */     this.link = newLink;
/* 1856 */     this.last = newPrev;
/* 1857 */     if (newPrev != -1)
/*      */     {
/* 1859 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1860 */     this.n = newN;
/* 1861 */     this.mask = mask;
/* 1862 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1863 */     this.key = newKey;
/* 1864 */     this.value = newValue;
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
/*      */   public Object2LongLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2LongLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1881 */       c = (Object2LongLinkedOpenCustomHashMap<K>)super.clone();
/* 1882 */     } catch (CloneNotSupportedException cantHappen) {
/* 1883 */       throw new InternalError();
/*      */     } 
/* 1885 */     c.keys = null;
/* 1886 */     c.values = null;
/* 1887 */     c.entries = null;
/* 1888 */     c.containsNullKey = this.containsNullKey;
/* 1889 */     c.key = (K[])this.key.clone();
/* 1890 */     c.value = (long[])this.value.clone();
/* 1891 */     c.link = (long[])this.link.clone();
/* 1892 */     c.strategy = this.strategy;
/* 1893 */     return c;
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
/* 1906 */     int h = 0;
/* 1907 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1908 */       while (this.key[i] == null)
/* 1909 */         i++; 
/* 1910 */       if (this != this.key[i])
/* 1911 */         t = this.strategy.hashCode(this.key[i]); 
/* 1912 */       t ^= HashCommon.long2int(this.value[i]);
/* 1913 */       h += t;
/* 1914 */       i++;
/*      */     } 
/*      */     
/* 1917 */     if (this.containsNullKey)
/* 1918 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1919 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1922 */     K[] key = this.key;
/* 1923 */     long[] value = this.value;
/* 1924 */     MapIterator i = new MapIterator();
/* 1925 */     s.defaultWriteObject();
/* 1926 */     for (int j = this.size; j-- != 0; ) {
/* 1927 */       int e = i.nextEntry();
/* 1928 */       s.writeObject(key[e]);
/* 1929 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1934 */     s.defaultReadObject();
/* 1935 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1936 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1937 */     this.mask = this.n - 1;
/* 1938 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1939 */     long[] value = this.value = new long[this.n + 1];
/* 1940 */     long[] link = this.link = new long[this.n + 1];
/* 1941 */     int prev = -1;
/* 1942 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1945 */     for (int i = this.size; i-- != 0; ) {
/* 1946 */       int pos; K k = (K)s.readObject();
/* 1947 */       long v = s.readLong();
/* 1948 */       if (this.strategy.equals(k, null)) {
/* 1949 */         pos = this.n;
/* 1950 */         this.containsNullKey = true;
/*      */       } else {
/* 1952 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1953 */         while (key[pos] != null)
/* 1954 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1956 */       key[pos] = k;
/* 1957 */       value[pos] = v;
/* 1958 */       if (this.first != -1) {
/* 1959 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1960 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1961 */         prev = pos; continue;
/*      */       } 
/* 1963 */       prev = this.first = pos;
/*      */       
/* 1965 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1968 */     this.last = prev;
/* 1969 */     if (prev != -1)
/*      */     {
/* 1971 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */