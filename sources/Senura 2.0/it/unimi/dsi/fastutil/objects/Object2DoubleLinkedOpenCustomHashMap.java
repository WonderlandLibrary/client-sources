/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.ToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2DoubleLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2DoubleSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient double[] value;
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
/*      */   protected transient Object2DoubleSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*  167 */     this.value = new double[this.n + 1];
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(Map<? extends K, ? extends Double> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(Map<? extends K, ? extends Double> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(Object2DoubleMap<K> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(Object2DoubleMap<K> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(K[] k, double[] v, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap(K[] k, double[] v, Hash.Strategy<? super K> strategy) {
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
/*      */   private double removeEntry(int pos) {
/*  309 */     double oldValue = this.value[pos];
/*  310 */     this.size--;
/*  311 */     fixPointers(pos);
/*  312 */     shiftKeys(pos);
/*  313 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  314 */       rehash(this.n / 2); 
/*  315 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  318 */     this.containsNullKey = false;
/*  319 */     this.key[this.n] = null;
/*  320 */     double oldValue = this.value[this.n];
/*  321 */     this.size--;
/*  322 */     fixPointers(this.n);
/*  323 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  324 */       rehash(this.n / 2); 
/*  325 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Double> m) {
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
/*      */   private void insert(int pos, K k, double v) {
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
/*      */   public double put(K k, double v) {
/*  377 */     int pos = find(k);
/*  378 */     if (pos < 0) {
/*  379 */       insert(-pos - 1, k, v);
/*  380 */       return this.defRetValue;
/*      */     } 
/*  382 */     double oldValue = this.value[pos];
/*  383 */     this.value[pos] = v;
/*  384 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  387 */     double oldValue = this.value[pos];
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
/*      */   public double addTo(K k, double incr) {
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
/*      */   public double removeDouble(Object k) {
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
/*      */   private double setValue(int pos, double v) {
/*  496 */     double oldValue = this.value[pos];
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
/*      */   public double removeFirstDouble() {
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
/*  519 */     double v = this.value[pos];
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
/*      */   public double removeLastDouble() {
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
/*  547 */     double v = this.value[pos];
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
/*      */   public double getAndMoveToFirst(K k) {
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
/*      */   public double getAndMoveToLast(K k) {
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
/*      */   public double putAndMoveToFirst(K k, double v) {
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
/*      */   public double putAndMoveToLast(K k, double v) {
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
/*      */   public double getDouble(Object k) {
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
/*      */   public boolean containsValue(double v) {
/*  817 */     double[] value = this.value;
/*  818 */     K[] key = this.key;
/*  819 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  820 */       return true; 
/*  821 */     for (int i = this.n; i-- != 0;) {
/*  822 */       if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  823 */         return true; 
/*  824 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
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
/*      */   public double putIfAbsent(K k, double v) {
/*  851 */     int pos = find(k);
/*  852 */     if (pos >= 0)
/*  853 */       return this.value[pos]; 
/*  854 */     insert(-pos - 1, k, v);
/*  855 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  861 */     if (this.strategy.equals(k, null)) {
/*  862 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
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
/*  874 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  875 */       removeEntry(pos);
/*  876 */       return true;
/*      */     } 
/*      */     while (true) {
/*  879 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  880 */         return false; 
/*  881 */       if (this.strategy.equals(k, curr) && 
/*  882 */         Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  883 */         removeEntry(pos);
/*  884 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  891 */     int pos = find(k);
/*  892 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  893 */       return false; 
/*  894 */     this.value[pos] = v;
/*  895 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     double oldValue = this.value[pos];
/*  904 */     this.value[pos] = v;
/*  905 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  911 */     Objects.requireNonNull(mappingFunction);
/*  912 */     int pos = find(k);
/*  913 */     if (pos >= 0)
/*  914 */       return this.value[pos]; 
/*  915 */     double newValue = mappingFunction.applyAsDouble(k);
/*  916 */     insert(-pos - 1, k, newValue);
/*  917 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  923 */     Objects.requireNonNull(remappingFunction);
/*  924 */     int pos = find(k);
/*  925 */     if (pos < 0)
/*  926 */       return this.defRetValue; 
/*  927 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  928 */     if (newValue == null) {
/*  929 */       if (this.strategy.equals(k, null)) {
/*  930 */         removeNullEntry();
/*      */       } else {
/*  932 */         removeEntry(pos);
/*  933 */       }  return this.defRetValue;
/*      */     } 
/*  935 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  941 */     Objects.requireNonNull(remappingFunction);
/*  942 */     int pos = find(k);
/*  943 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  944 */     if (newValue == null) {
/*  945 */       if (pos >= 0)
/*  946 */         if (this.strategy.equals(k, null)) {
/*  947 */           removeNullEntry();
/*      */         } else {
/*  949 */           removeEntry(pos);
/*      */         }  
/*  951 */       return this.defRetValue;
/*      */     } 
/*  953 */     double newVal = newValue.doubleValue();
/*  954 */     if (pos < 0) {
/*  955 */       insert(-pos - 1, k, newVal);
/*  956 */       return newVal;
/*      */     } 
/*  958 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double mergeDouble(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  964 */     Objects.requireNonNull(remappingFunction);
/*  965 */     int pos = find(k);
/*  966 */     if (pos < 0) {
/*  967 */       insert(-pos - 1, k, v);
/*  968 */       return v;
/*      */     } 
/*  970 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  971 */     if (newValue == null) {
/*  972 */       if (this.strategy.equals(k, null)) {
/*  973 */         removeNullEntry();
/*      */       } else {
/*  975 */         removeEntry(pos);
/*  976 */       }  return this.defRetValue;
/*      */     } 
/*  978 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  989 */     if (this.size == 0)
/*      */       return; 
/*  991 */     this.size = 0;
/*  992 */     this.containsNullKey = false;
/*  993 */     Arrays.fill((Object[])this.key, (Object)null);
/*  994 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  998 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1002 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2DoubleMap.Entry<K>, Map.Entry<K, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1014 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/* 1020 */       return Object2DoubleLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/* 1024 */       return Object2DoubleLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/* 1028 */       double oldValue = Object2DoubleLinkedOpenCustomHashMap.this.value[this.index];
/* 1029 */       Object2DoubleLinkedOpenCustomHashMap.this.value[this.index] = v;
/* 1030 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/* 1040 */       return Double.valueOf(Object2DoubleLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/* 1050 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1055 */       if (!(o instanceof Map.Entry))
/* 1056 */         return false; 
/* 1057 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/* 1058 */       return (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(Object2DoubleLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && 
/* 1059 */         Double.doubleToLongBits(Object2DoubleLinkedOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1063 */       return Object2DoubleLinkedOpenCustomHashMap.this.strategy.hashCode(Object2DoubleLinkedOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Object2DoubleLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1067 */       return (new StringBuilder()).append(Object2DoubleLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2DoubleLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/* 1078 */     if (this.size == 0) {
/* 1079 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1082 */     if (this.first == i) {
/* 1083 */       this.first = (int)this.link[i];
/* 1084 */       if (0 <= this.first)
/*      */       {
/* 1086 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1090 */     if (this.last == i) {
/* 1091 */       this.last = (int)(this.link[i] >>> 32L);
/* 1092 */       if (0 <= this.last)
/*      */       {
/* 1094 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1098 */     long linki = this.link[i];
/* 1099 */     int prev = (int)(linki >>> 32L);
/* 1100 */     int next = (int)linki;
/* 1101 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1102 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1115 */     if (this.size == 1) {
/* 1116 */       this.first = this.last = d;
/*      */       
/* 1118 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1121 */     if (this.first == s) {
/* 1122 */       this.first = d;
/* 1123 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1124 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1127 */     if (this.last == s) {
/* 1128 */       this.last = d;
/* 1129 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1130 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1133 */     long links = this.link[s];
/* 1134 */     int prev = (int)(links >>> 32L);
/* 1135 */     int next = (int)links;
/* 1136 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1137 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1138 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1147 */     if (this.size == 0)
/* 1148 */       throw new NoSuchElementException(); 
/* 1149 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1158 */     if (this.size == 0)
/* 1159 */       throw new NoSuchElementException(); 
/* 1160 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> tailMap(K from) {
/* 1169 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> headMap(K to) {
/* 1178 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 1187 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1196 */     return null;
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
/* 1211 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1217 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1222 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1228 */     int index = -1;
/*      */     protected MapIterator() {
/* 1230 */       this.next = Object2DoubleLinkedOpenCustomHashMap.this.first;
/* 1231 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1234 */       if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1235 */         if (Object2DoubleLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1236 */           this.next = (int)Object2DoubleLinkedOpenCustomHashMap.this.link[Object2DoubleLinkedOpenCustomHashMap.this.n];
/* 1237 */           this.prev = Object2DoubleLinkedOpenCustomHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1240 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1242 */       if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(Object2DoubleLinkedOpenCustomHashMap.this.key[Object2DoubleLinkedOpenCustomHashMap.this.last], from)) {
/* 1243 */         this.prev = Object2DoubleLinkedOpenCustomHashMap.this.last;
/* 1244 */         this.index = Object2DoubleLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1248 */       int pos = HashCommon.mix(Object2DoubleLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2DoubleLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1250 */       while (Object2DoubleLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1251 */         if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(Object2DoubleLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1253 */           this.next = (int)Object2DoubleLinkedOpenCustomHashMap.this.link[pos];
/* 1254 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1257 */         pos = pos + 1 & Object2DoubleLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1259 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1262 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1265 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1268 */       if (this.index >= 0)
/*      */         return; 
/* 1270 */       if (this.prev == -1) {
/* 1271 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1274 */       if (this.next == -1) {
/* 1275 */         this.index = Object2DoubleLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1278 */       int pos = Object2DoubleLinkedOpenCustomHashMap.this.first;
/* 1279 */       this.index = 1;
/* 1280 */       while (pos != this.prev) {
/* 1281 */         pos = (int)Object2DoubleLinkedOpenCustomHashMap.this.link[pos];
/* 1282 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1286 */       ensureIndexKnown();
/* 1287 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1290 */       ensureIndexKnown();
/* 1291 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1294 */       if (!hasNext())
/* 1295 */         throw new NoSuchElementException(); 
/* 1296 */       this.curr = this.next;
/* 1297 */       this.next = (int)Object2DoubleLinkedOpenCustomHashMap.this.link[this.curr];
/* 1298 */       this.prev = this.curr;
/* 1299 */       if (this.index >= 0)
/* 1300 */         this.index++; 
/* 1301 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1304 */       if (!hasPrevious())
/* 1305 */         throw new NoSuchElementException(); 
/* 1306 */       this.curr = this.prev;
/* 1307 */       this.prev = (int)(Object2DoubleLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1308 */       this.next = this.curr;
/* 1309 */       if (this.index >= 0)
/* 1310 */         this.index--; 
/* 1311 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1314 */       ensureIndexKnown();
/* 1315 */       if (this.curr == -1)
/* 1316 */         throw new IllegalStateException(); 
/* 1317 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1322 */         this.index--;
/* 1323 */         this.prev = (int)(Object2DoubleLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1325 */         this.next = (int)Object2DoubleLinkedOpenCustomHashMap.this.link[this.curr];
/* 1326 */       }  Object2DoubleLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1331 */       if (this.prev == -1) {
/* 1332 */         Object2DoubleLinkedOpenCustomHashMap.this.first = this.next;
/*      */       } else {
/* 1334 */         Object2DoubleLinkedOpenCustomHashMap.this.link[this.prev] = Object2DoubleLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2DoubleLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1335 */       }  if (this.next == -1) {
/* 1336 */         Object2DoubleLinkedOpenCustomHashMap.this.last = this.prev;
/*      */       } else {
/* 1338 */         Object2DoubleLinkedOpenCustomHashMap.this.link[this.next] = Object2DoubleLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2DoubleLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1339 */       }  int pos = this.curr;
/* 1340 */       this.curr = -1;
/* 1341 */       if (pos == Object2DoubleLinkedOpenCustomHashMap.this.n) {
/* 1342 */         Object2DoubleLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1343 */         Object2DoubleLinkedOpenCustomHashMap.this.key[Object2DoubleLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1346 */         K[] key = Object2DoubleLinkedOpenCustomHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1350 */           pos = (last = pos) + 1 & Object2DoubleLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1352 */             if ((curr = key[pos]) == null) {
/* 1353 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1356 */             int slot = HashCommon.mix(Object2DoubleLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2DoubleLinkedOpenCustomHashMap.this.mask;
/* 1357 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1359 */             pos = pos + 1 & Object2DoubleLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1361 */           key[last] = curr;
/* 1362 */           Object2DoubleLinkedOpenCustomHashMap.this.value[last] = Object2DoubleLinkedOpenCustomHashMap.this.value[pos];
/* 1363 */           if (this.next == pos)
/* 1364 */             this.next = last; 
/* 1365 */           if (this.prev == pos)
/* 1366 */             this.prev = last; 
/* 1367 */           Object2DoubleLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1372 */       int i = n;
/* 1373 */       while (i-- != 0 && hasNext())
/* 1374 */         nextEntry(); 
/* 1375 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1378 */       int i = n;
/* 1379 */       while (i-- != 0 && hasPrevious())
/* 1380 */         previousEntry(); 
/* 1381 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2DoubleMap.Entry<K> ok) {
/* 1384 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2DoubleMap.Entry<K> ok) {
/* 1387 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2DoubleMap.Entry<K>> { private Object2DoubleLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1395 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1399 */       return this.entry = new Object2DoubleLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1403 */       return this.entry = new Object2DoubleLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1407 */       super.remove();
/* 1408 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1412 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2DoubleMap.Entry<K>> { final Object2DoubleLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2DoubleLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1416 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1420 */       this.entry.index = nextEntry();
/* 1421 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1425 */       this.entry.index = previousEntry();
/* 1426 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2DoubleMap.Entry<K>> implements Object2DoubleSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> iterator() {
/* 1434 */       return new Object2DoubleLinkedOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2DoubleMap.Entry<K>> comparator() {
/* 1438 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> subSet(Object2DoubleMap.Entry<K> fromElement, Object2DoubleMap.Entry<K> toElement) {
/* 1443 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> headSet(Object2DoubleMap.Entry<K> toElement) {
/* 1447 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> tailSet(Object2DoubleMap.Entry<K> fromElement) {
/* 1451 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2DoubleMap.Entry<K> first() {
/* 1455 */       if (Object2DoubleLinkedOpenCustomHashMap.this.size == 0)
/* 1456 */         throw new NoSuchElementException(); 
/* 1457 */       return new Object2DoubleLinkedOpenCustomHashMap.MapEntry(Object2DoubleLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2DoubleMap.Entry<K> last() {
/* 1461 */       if (Object2DoubleLinkedOpenCustomHashMap.this.size == 0)
/* 1462 */         throw new NoSuchElementException(); 
/* 1463 */       return new Object2DoubleLinkedOpenCustomHashMap.MapEntry(Object2DoubleLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1468 */       if (!(o instanceof Map.Entry))
/* 1469 */         return false; 
/* 1470 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1471 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1472 */         return false; 
/* 1473 */       K k = (K)e.getKey();
/* 1474 */       double v = ((Double)e.getValue()).doubleValue();
/* 1475 */       if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1476 */         return (Object2DoubleLinkedOpenCustomHashMap.this.containsNullKey && 
/* 1477 */           Double.doubleToLongBits(Object2DoubleLinkedOpenCustomHashMap.this.value[Object2DoubleLinkedOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/* 1479 */       K[] key = Object2DoubleLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1482 */       if ((curr = key[pos = HashCommon.mix(Object2DoubleLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleLinkedOpenCustomHashMap.this.mask]) == null)
/* 1483 */         return false; 
/* 1484 */       if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1485 */         return (Double.doubleToLongBits(Object2DoubleLinkedOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/* 1488 */         if ((curr = key[pos = pos + 1 & Object2DoubleLinkedOpenCustomHashMap.this.mask]) == null)
/* 1489 */           return false; 
/* 1490 */         if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1491 */           return (Double.doubleToLongBits(Object2DoubleLinkedOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1497 */       if (!(o instanceof Map.Entry))
/* 1498 */         return false; 
/* 1499 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1500 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1501 */         return false; 
/* 1502 */       K k = (K)e.getKey();
/* 1503 */       double v = ((Double)e.getValue()).doubleValue();
/* 1504 */       if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1505 */         if (Object2DoubleLinkedOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Object2DoubleLinkedOpenCustomHashMap.this.value[Object2DoubleLinkedOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1506 */           Object2DoubleLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1507 */           return true;
/*      */         } 
/* 1509 */         return false;
/*      */       } 
/*      */       
/* 1512 */       K[] key = Object2DoubleLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1515 */       if ((curr = key[pos = HashCommon.mix(Object2DoubleLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2DoubleLinkedOpenCustomHashMap.this.mask]) == null)
/* 1516 */         return false; 
/* 1517 */       if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1518 */         if (Double.doubleToLongBits(Object2DoubleLinkedOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1519 */           Object2DoubleLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1520 */           return true;
/*      */         } 
/* 1522 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1525 */         if ((curr = key[pos = pos + 1 & Object2DoubleLinkedOpenCustomHashMap.this.mask]) == null)
/* 1526 */           return false; 
/* 1527 */         if (Object2DoubleLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1528 */           Double.doubleToLongBits(Object2DoubleLinkedOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1529 */           Object2DoubleLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1530 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1537 */       return Object2DoubleLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1541 */       Object2DoubleLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2DoubleMap.Entry<K>> iterator(Object2DoubleMap.Entry<K> from) {
/* 1556 */       return new Object2DoubleLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2DoubleMap.Entry<K>> fastIterator() {
/* 1567 */       return new Object2DoubleLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2DoubleMap.Entry<K>> fastIterator(Object2DoubleMap.Entry<K> from) {
/* 1582 */       return new Object2DoubleLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/* 1587 */       for (int i = Object2DoubleLinkedOpenCustomHashMap.this.size, next = Object2DoubleLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1588 */         int curr = next;
/* 1589 */         next = (int)Object2DoubleLinkedOpenCustomHashMap.this.link[curr];
/* 1590 */         consumer.accept(new AbstractObject2DoubleMap.BasicEntry<>(Object2DoubleLinkedOpenCustomHashMap.this.key[curr], Object2DoubleLinkedOpenCustomHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/* 1596 */       AbstractObject2DoubleMap.BasicEntry<K> entry = new AbstractObject2DoubleMap.BasicEntry<>();
/* 1597 */       for (int i = Object2DoubleLinkedOpenCustomHashMap.this.size, next = Object2DoubleLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1598 */         int curr = next;
/* 1599 */         next = (int)Object2DoubleLinkedOpenCustomHashMap.this.link[curr];
/* 1600 */         entry.key = Object2DoubleLinkedOpenCustomHashMap.this.key[curr];
/* 1601 */         entry.value = Object2DoubleLinkedOpenCustomHashMap.this.value[curr];
/* 1602 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap.FastSortedEntrySet<K> object2DoubleEntrySet() {
/* 1608 */     if (this.entries == null)
/* 1609 */       this.entries = new MapEntrySet(); 
/* 1610 */     return this.entries;
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
/* 1623 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1627 */       return Object2DoubleLinkedOpenCustomHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1634 */       return Object2DoubleLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1640 */       return new Object2DoubleLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1644 */       return new Object2DoubleLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1649 */       if (Object2DoubleLinkedOpenCustomHashMap.this.containsNullKey)
/* 1650 */         consumer.accept(Object2DoubleLinkedOpenCustomHashMap.this.key[Object2DoubleLinkedOpenCustomHashMap.this.n]); 
/* 1651 */       for (int pos = Object2DoubleLinkedOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1652 */         K k = Object2DoubleLinkedOpenCustomHashMap.this.key[pos];
/* 1653 */         if (k != null)
/* 1654 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1659 */       return Object2DoubleLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1663 */       return Object2DoubleLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1667 */       int oldSize = Object2DoubleLinkedOpenCustomHashMap.this.size;
/* 1668 */       Object2DoubleLinkedOpenCustomHashMap.this.removeDouble(k);
/* 1669 */       return (Object2DoubleLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1673 */       Object2DoubleLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1677 */       if (Object2DoubleLinkedOpenCustomHashMap.this.size == 0)
/* 1678 */         throw new NoSuchElementException(); 
/* 1679 */       return Object2DoubleLinkedOpenCustomHashMap.this.key[Object2DoubleLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1683 */       if (Object2DoubleLinkedOpenCustomHashMap.this.size == 0)
/* 1684 */         throw new NoSuchElementException(); 
/* 1685 */       return Object2DoubleLinkedOpenCustomHashMap.this.key[Object2DoubleLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1689 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1693 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1697 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1701 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1706 */     if (this.keys == null)
/* 1707 */       this.keys = new KeySet(); 
/* 1708 */     return this.keys;
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
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public double previousDouble() {
/* 1722 */       return Object2DoubleLinkedOpenCustomHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1729 */       return Object2DoubleLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1734 */     if (this.values == null)
/* 1735 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1738 */             return (DoubleIterator)new Object2DoubleLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1742 */             return Object2DoubleLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1746 */             return Object2DoubleLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1750 */             Object2DoubleLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1755 */             if (Object2DoubleLinkedOpenCustomHashMap.this.containsNullKey)
/* 1756 */               consumer.accept(Object2DoubleLinkedOpenCustomHashMap.this.value[Object2DoubleLinkedOpenCustomHashMap.this.n]); 
/* 1757 */             for (int pos = Object2DoubleLinkedOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1758 */               if (Object2DoubleLinkedOpenCustomHashMap.this.key[pos] != null)
/* 1759 */                 consumer.accept(Object2DoubleLinkedOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1762 */     return this.values;
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
/* 1779 */     return trim(this.size);
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
/* 1803 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1804 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1805 */       return true; 
/*      */     try {
/* 1807 */       rehash(l);
/* 1808 */     } catch (OutOfMemoryError cantDoIt) {
/* 1809 */       return false;
/*      */     } 
/* 1811 */     return true;
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
/* 1827 */     K[] key = this.key;
/* 1828 */     double[] value = this.value;
/* 1829 */     int mask = newN - 1;
/* 1830 */     K[] newKey = (K[])new Object[newN + 1];
/* 1831 */     double[] newValue = new double[newN + 1];
/* 1832 */     int i = this.first, prev = -1, newPrev = -1;
/* 1833 */     long[] link = this.link;
/* 1834 */     long[] newLink = new long[newN + 1];
/* 1835 */     this.first = -1;
/* 1836 */     for (int j = this.size; j-- != 0; ) {
/* 1837 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1838 */         pos = newN;
/*      */       } else {
/* 1840 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1841 */         while (newKey[pos] != null)
/* 1842 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1844 */       newKey[pos] = key[i];
/* 1845 */       newValue[pos] = value[i];
/* 1846 */       if (prev != -1) {
/* 1847 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1848 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1849 */         newPrev = pos;
/*      */       } else {
/* 1851 */         newPrev = this.first = pos;
/*      */         
/* 1853 */         newLink[pos] = -1L;
/*      */       } 
/* 1855 */       int t = i;
/* 1856 */       i = (int)link[i];
/* 1857 */       prev = t;
/*      */     } 
/* 1859 */     this.link = newLink;
/* 1860 */     this.last = newPrev;
/* 1861 */     if (newPrev != -1)
/*      */     {
/* 1863 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1864 */     this.n = newN;
/* 1865 */     this.mask = mask;
/* 1866 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1867 */     this.key = newKey;
/* 1868 */     this.value = newValue;
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
/*      */   public Object2DoubleLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2DoubleLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1885 */       c = (Object2DoubleLinkedOpenCustomHashMap<K>)super.clone();
/* 1886 */     } catch (CloneNotSupportedException cantHappen) {
/* 1887 */       throw new InternalError();
/*      */     } 
/* 1889 */     c.keys = null;
/* 1890 */     c.values = null;
/* 1891 */     c.entries = null;
/* 1892 */     c.containsNullKey = this.containsNullKey;
/* 1893 */     c.key = (K[])this.key.clone();
/* 1894 */     c.value = (double[])this.value.clone();
/* 1895 */     c.link = (long[])this.link.clone();
/* 1896 */     c.strategy = this.strategy;
/* 1897 */     return c;
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
/* 1910 */     int h = 0;
/* 1911 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1912 */       while (this.key[i] == null)
/* 1913 */         i++; 
/* 1914 */       if (this != this.key[i])
/* 1915 */         t = this.strategy.hashCode(this.key[i]); 
/* 1916 */       t ^= HashCommon.double2int(this.value[i]);
/* 1917 */       h += t;
/* 1918 */       i++;
/*      */     } 
/*      */     
/* 1921 */     if (this.containsNullKey)
/* 1922 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1923 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1926 */     K[] key = this.key;
/* 1927 */     double[] value = this.value;
/* 1928 */     MapIterator i = new MapIterator();
/* 1929 */     s.defaultWriteObject();
/* 1930 */     for (int j = this.size; j-- != 0; ) {
/* 1931 */       int e = i.nextEntry();
/* 1932 */       s.writeObject(key[e]);
/* 1933 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1938 */     s.defaultReadObject();
/* 1939 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1940 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1941 */     this.mask = this.n - 1;
/* 1942 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1943 */     double[] value = this.value = new double[this.n + 1];
/* 1944 */     long[] link = this.link = new long[this.n + 1];
/* 1945 */     int prev = -1;
/* 1946 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1949 */     for (int i = this.size; i-- != 0; ) {
/* 1950 */       int pos; K k = (K)s.readObject();
/* 1951 */       double v = s.readDouble();
/* 1952 */       if (this.strategy.equals(k, null)) {
/* 1953 */         pos = this.n;
/* 1954 */         this.containsNullKey = true;
/*      */       } else {
/* 1956 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1957 */         while (key[pos] != null)
/* 1958 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1960 */       key[pos] = k;
/* 1961 */       value[pos] = v;
/* 1962 */       if (this.first != -1) {
/* 1963 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1964 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1965 */         prev = pos; continue;
/*      */       } 
/* 1967 */       prev = this.first = pos;
/*      */       
/* 1969 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1972 */     this.last = prev;
/* 1973 */     if (prev != -1)
/*      */     {
/* 1975 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */