/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ public class Object2FloatLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2FloatSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient float[] value;
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
/*      */   protected transient Object2FloatSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*  167 */     this.value = new float[this.n + 1];
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(Map<? extends K, ? extends Float> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(Map<? extends K, ? extends Float> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(Object2FloatMap<K> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(Object2FloatMap<K> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(K[] k, float[] v, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2FloatLinkedOpenCustomHashMap(K[] k, float[] v, Hash.Strategy<? super K> strategy) {
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
/*      */   private float removeEntry(int pos) {
/*  309 */     float oldValue = this.value[pos];
/*  310 */     this.size--;
/*  311 */     fixPointers(pos);
/*  312 */     shiftKeys(pos);
/*  313 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  314 */       rehash(this.n / 2); 
/*  315 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  318 */     this.containsNullKey = false;
/*  319 */     this.key[this.n] = null;
/*  320 */     float oldValue = this.value[this.n];
/*  321 */     this.size--;
/*  322 */     fixPointers(this.n);
/*  323 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  324 */       rehash(this.n / 2); 
/*  325 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> m) {
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
/*      */   private void insert(int pos, K k, float v) {
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
/*      */   public float put(K k, float v) {
/*  377 */     int pos = find(k);
/*  378 */     if (pos < 0) {
/*  379 */       insert(-pos - 1, k, v);
/*  380 */       return this.defRetValue;
/*      */     } 
/*  382 */     float oldValue = this.value[pos];
/*  383 */     this.value[pos] = v;
/*  384 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  387 */     float oldValue = this.value[pos];
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
/*      */   public float addTo(K k, float incr) {
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
/*      */   public float removeFloat(Object k) {
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
/*      */   private float setValue(int pos, float v) {
/*  496 */     float oldValue = this.value[pos];
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
/*      */   public float removeFirstFloat() {
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
/*  519 */     float v = this.value[pos];
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
/*      */   public float removeLastFloat() {
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
/*  547 */     float v = this.value[pos];
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
/*      */   public float getAndMoveToFirst(K k) {
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
/*      */   public float getAndMoveToLast(K k) {
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
/*      */   public float putAndMoveToFirst(K k, float v) {
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
/*      */   public float putAndMoveToLast(K k, float v) {
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
/*      */   public float getFloat(Object k) {
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
/*      */   public boolean containsValue(float v) {
/*  817 */     float[] value = this.value;
/*  818 */     K[] key = this.key;
/*  819 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  820 */       return true; 
/*  821 */     for (int i = this.n; i-- != 0;) {
/*  822 */       if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  823 */         return true; 
/*  824 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
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
/*      */   public float putIfAbsent(K k, float v) {
/*  851 */     int pos = find(k);
/*  852 */     if (pos >= 0)
/*  853 */       return this.value[pos]; 
/*  854 */     insert(-pos - 1, k, v);
/*  855 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  861 */     if (this.strategy.equals(k, null)) {
/*  862 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
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
/*  874 */     if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  875 */       removeEntry(pos);
/*  876 */       return true;
/*      */     } 
/*      */     while (true) {
/*  879 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  880 */         return false; 
/*  881 */       if (this.strategy.equals(k, curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  882 */         removeEntry(pos);
/*  883 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  890 */     int pos = find(k);
/*  891 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  892 */       return false; 
/*  893 */     this.value[pos] = v;
/*  894 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  899 */     int pos = find(k);
/*  900 */     if (pos < 0)
/*  901 */       return this.defRetValue; 
/*  902 */     float oldValue = this.value[pos];
/*  903 */     this.value[pos] = v;
/*  904 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  909 */     Objects.requireNonNull(mappingFunction);
/*  910 */     int pos = find(k);
/*  911 */     if (pos >= 0)
/*  912 */       return this.value[pos]; 
/*  913 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  914 */     insert(-pos - 1, k, newValue);
/*  915 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  921 */     Objects.requireNonNull(remappingFunction);
/*  922 */     int pos = find(k);
/*  923 */     if (pos < 0)
/*  924 */       return this.defRetValue; 
/*  925 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  926 */     if (newValue == null) {
/*  927 */       if (this.strategy.equals(k, null)) {
/*  928 */         removeNullEntry();
/*      */       } else {
/*  930 */         removeEntry(pos);
/*  931 */       }  return this.defRetValue;
/*      */     } 
/*  933 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  939 */     Objects.requireNonNull(remappingFunction);
/*  940 */     int pos = find(k);
/*  941 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  942 */     if (newValue == null) {
/*  943 */       if (pos >= 0)
/*  944 */         if (this.strategy.equals(k, null)) {
/*  945 */           removeNullEntry();
/*      */         } else {
/*  947 */           removeEntry(pos);
/*      */         }  
/*  949 */       return this.defRetValue;
/*      */     } 
/*  951 */     float newVal = newValue.floatValue();
/*  952 */     if (pos < 0) {
/*  953 */       insert(-pos - 1, k, newVal);
/*  954 */       return newVal;
/*      */     } 
/*  956 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float mergeFloat(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  962 */     Objects.requireNonNull(remappingFunction);
/*  963 */     int pos = find(k);
/*  964 */     if (pos < 0) {
/*  965 */       insert(-pos - 1, k, v);
/*  966 */       return v;
/*      */     } 
/*  968 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  969 */     if (newValue == null) {
/*  970 */       if (this.strategy.equals(k, null)) {
/*  971 */         removeNullEntry();
/*      */       } else {
/*  973 */         removeEntry(pos);
/*  974 */       }  return this.defRetValue;
/*      */     } 
/*  976 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*      */     implements Object2FloatMap.Entry<K>, Map.Entry<K, Float>
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
/* 1018 */       return Object2FloatLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/* 1022 */       return Object2FloatLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/* 1026 */       float oldValue = Object2FloatLinkedOpenCustomHashMap.this.value[this.index];
/* 1027 */       Object2FloatLinkedOpenCustomHashMap.this.value[this.index] = v;
/* 1028 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1038 */       return Float.valueOf(Object2FloatLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/* 1048 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1053 */       if (!(o instanceof Map.Entry))
/* 1054 */         return false; 
/* 1055 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/* 1056 */       return (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(Object2FloatLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && 
/* 1057 */         Float.floatToIntBits(Object2FloatLinkedOpenCustomHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1061 */       return Object2FloatLinkedOpenCustomHashMap.this.strategy.hashCode(Object2FloatLinkedOpenCustomHashMap.this.key[this.index]) ^ HashCommon.float2int(Object2FloatLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1065 */       return (new StringBuilder()).append(Object2FloatLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2FloatLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/* 1076 */     if (this.size == 0) {
/* 1077 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1080 */     if (this.first == i) {
/* 1081 */       this.first = (int)this.link[i];
/* 1082 */       if (0 <= this.first)
/*      */       {
/* 1084 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1088 */     if (this.last == i) {
/* 1089 */       this.last = (int)(this.link[i] >>> 32L);
/* 1090 */       if (0 <= this.last)
/*      */       {
/* 1092 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1096 */     long linki = this.link[i];
/* 1097 */     int prev = (int)(linki >>> 32L);
/* 1098 */     int next = (int)linki;
/* 1099 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1100 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1113 */     if (this.size == 1) {
/* 1114 */       this.first = this.last = d;
/*      */       
/* 1116 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1119 */     if (this.first == s) {
/* 1120 */       this.first = d;
/* 1121 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1122 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1125 */     if (this.last == s) {
/* 1126 */       this.last = d;
/* 1127 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1128 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1131 */     long links = this.link[s];
/* 1132 */     int prev = (int)(links >>> 32L);
/* 1133 */     int next = (int)links;
/* 1134 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1135 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1136 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1145 */     if (this.size == 0)
/* 1146 */       throw new NoSuchElementException(); 
/* 1147 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1156 */     if (this.size == 0)
/* 1157 */       throw new NoSuchElementException(); 
/* 1158 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatSortedMap<K> tailMap(K from) {
/* 1167 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatSortedMap<K> headMap(K to) {
/* 1176 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatSortedMap<K> subMap(K from, K to) {
/* 1185 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1194 */     return null;
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
/* 1209 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1215 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1220 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1226 */     int index = -1;
/*      */     protected MapIterator() {
/* 1228 */       this.next = Object2FloatLinkedOpenCustomHashMap.this.first;
/* 1229 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1232 */       if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1233 */         if (Object2FloatLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1234 */           this.next = (int)Object2FloatLinkedOpenCustomHashMap.this.link[Object2FloatLinkedOpenCustomHashMap.this.n];
/* 1235 */           this.prev = Object2FloatLinkedOpenCustomHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1238 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1240 */       if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(Object2FloatLinkedOpenCustomHashMap.this.key[Object2FloatLinkedOpenCustomHashMap.this.last], from)) {
/* 1241 */         this.prev = Object2FloatLinkedOpenCustomHashMap.this.last;
/* 1242 */         this.index = Object2FloatLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1246 */       int pos = HashCommon.mix(Object2FloatLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2FloatLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1248 */       while (Object2FloatLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1249 */         if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(Object2FloatLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1251 */           this.next = (int)Object2FloatLinkedOpenCustomHashMap.this.link[pos];
/* 1252 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1255 */         pos = pos + 1 & Object2FloatLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1257 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1260 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1263 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1266 */       if (this.index >= 0)
/*      */         return; 
/* 1268 */       if (this.prev == -1) {
/* 1269 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1272 */       if (this.next == -1) {
/* 1273 */         this.index = Object2FloatLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1276 */       int pos = Object2FloatLinkedOpenCustomHashMap.this.first;
/* 1277 */       this.index = 1;
/* 1278 */       while (pos != this.prev) {
/* 1279 */         pos = (int)Object2FloatLinkedOpenCustomHashMap.this.link[pos];
/* 1280 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1284 */       ensureIndexKnown();
/* 1285 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1288 */       ensureIndexKnown();
/* 1289 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1292 */       if (!hasNext())
/* 1293 */         throw new NoSuchElementException(); 
/* 1294 */       this.curr = this.next;
/* 1295 */       this.next = (int)Object2FloatLinkedOpenCustomHashMap.this.link[this.curr];
/* 1296 */       this.prev = this.curr;
/* 1297 */       if (this.index >= 0)
/* 1298 */         this.index++; 
/* 1299 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1302 */       if (!hasPrevious())
/* 1303 */         throw new NoSuchElementException(); 
/* 1304 */       this.curr = this.prev;
/* 1305 */       this.prev = (int)(Object2FloatLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1306 */       this.next = this.curr;
/* 1307 */       if (this.index >= 0)
/* 1308 */         this.index--; 
/* 1309 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1312 */       ensureIndexKnown();
/* 1313 */       if (this.curr == -1)
/* 1314 */         throw new IllegalStateException(); 
/* 1315 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1320 */         this.index--;
/* 1321 */         this.prev = (int)(Object2FloatLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1323 */         this.next = (int)Object2FloatLinkedOpenCustomHashMap.this.link[this.curr];
/* 1324 */       }  Object2FloatLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1329 */       if (this.prev == -1) {
/* 1330 */         Object2FloatLinkedOpenCustomHashMap.this.first = this.next;
/*      */       } else {
/* 1332 */         Object2FloatLinkedOpenCustomHashMap.this.link[this.prev] = Object2FloatLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2FloatLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1333 */       }  if (this.next == -1) {
/* 1334 */         Object2FloatLinkedOpenCustomHashMap.this.last = this.prev;
/*      */       } else {
/* 1336 */         Object2FloatLinkedOpenCustomHashMap.this.link[this.next] = Object2FloatLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2FloatLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1337 */       }  int pos = this.curr;
/* 1338 */       this.curr = -1;
/* 1339 */       if (pos == Object2FloatLinkedOpenCustomHashMap.this.n) {
/* 1340 */         Object2FloatLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1341 */         Object2FloatLinkedOpenCustomHashMap.this.key[Object2FloatLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1344 */         K[] key = Object2FloatLinkedOpenCustomHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1348 */           pos = (last = pos) + 1 & Object2FloatLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1350 */             if ((curr = key[pos]) == null) {
/* 1351 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1354 */             int slot = HashCommon.mix(Object2FloatLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2FloatLinkedOpenCustomHashMap.this.mask;
/* 1355 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1357 */             pos = pos + 1 & Object2FloatLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1359 */           key[last] = curr;
/* 1360 */           Object2FloatLinkedOpenCustomHashMap.this.value[last] = Object2FloatLinkedOpenCustomHashMap.this.value[pos];
/* 1361 */           if (this.next == pos)
/* 1362 */             this.next = last; 
/* 1363 */           if (this.prev == pos)
/* 1364 */             this.prev = last; 
/* 1365 */           Object2FloatLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1370 */       int i = n;
/* 1371 */       while (i-- != 0 && hasNext())
/* 1372 */         nextEntry(); 
/* 1373 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1376 */       int i = n;
/* 1377 */       while (i-- != 0 && hasPrevious())
/* 1378 */         previousEntry(); 
/* 1379 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2FloatMap.Entry<K> ok) {
/* 1382 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2FloatMap.Entry<K> ok) {
/* 1385 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2FloatMap.Entry<K>> { private Object2FloatLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1393 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1397 */       return this.entry = new Object2FloatLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1401 */       return this.entry = new Object2FloatLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1405 */       super.remove();
/* 1406 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1410 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2FloatMap.Entry<K>> { final Object2FloatLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2FloatLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1414 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1418 */       this.entry.index = nextEntry();
/* 1419 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1423 */       this.entry.index = previousEntry();
/* 1424 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2FloatMap.Entry<K>> implements Object2FloatSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
/* 1432 */       return new Object2FloatLinkedOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
/* 1436 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> fromElement, Object2FloatMap.Entry<K> toElement) {
/* 1441 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> toElement) {
/* 1445 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> fromElement) {
/* 1449 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> first() {
/* 1453 */       if (Object2FloatLinkedOpenCustomHashMap.this.size == 0)
/* 1454 */         throw new NoSuchElementException(); 
/* 1455 */       return new Object2FloatLinkedOpenCustomHashMap.MapEntry(Object2FloatLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> last() {
/* 1459 */       if (Object2FloatLinkedOpenCustomHashMap.this.size == 0)
/* 1460 */         throw new NoSuchElementException(); 
/* 1461 */       return new Object2FloatLinkedOpenCustomHashMap.MapEntry(Object2FloatLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1466 */       if (!(o instanceof Map.Entry))
/* 1467 */         return false; 
/* 1468 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1469 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1470 */         return false; 
/* 1471 */       K k = (K)e.getKey();
/* 1472 */       float v = ((Float)e.getValue()).floatValue();
/* 1473 */       if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1474 */         return (Object2FloatLinkedOpenCustomHashMap.this.containsNullKey && 
/* 1475 */           Float.floatToIntBits(Object2FloatLinkedOpenCustomHashMap.this.value[Object2FloatLinkedOpenCustomHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1477 */       K[] key = Object2FloatLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1480 */       if ((curr = key[pos = HashCommon.mix(Object2FloatLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2FloatLinkedOpenCustomHashMap.this.mask]) == null)
/* 1481 */         return false; 
/* 1482 */       if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1483 */         return (Float.floatToIntBits(Object2FloatLinkedOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1486 */         if ((curr = key[pos = pos + 1 & Object2FloatLinkedOpenCustomHashMap.this.mask]) == null)
/* 1487 */           return false; 
/* 1488 */         if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1489 */           return (Float.floatToIntBits(Object2FloatLinkedOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1495 */       if (!(o instanceof Map.Entry))
/* 1496 */         return false; 
/* 1497 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1498 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1499 */         return false; 
/* 1500 */       K k = (K)e.getKey();
/* 1501 */       float v = ((Float)e.getValue()).floatValue();
/* 1502 */       if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1503 */         if (Object2FloatLinkedOpenCustomHashMap.this.containsNullKey && Float.floatToIntBits(Object2FloatLinkedOpenCustomHashMap.this.value[Object2FloatLinkedOpenCustomHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1504 */           Object2FloatLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1505 */           return true;
/*      */         } 
/* 1507 */         return false;
/*      */       } 
/*      */       
/* 1510 */       K[] key = Object2FloatLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1513 */       if ((curr = key[pos = HashCommon.mix(Object2FloatLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2FloatLinkedOpenCustomHashMap.this.mask]) == null)
/* 1514 */         return false; 
/* 1515 */       if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1516 */         if (Float.floatToIntBits(Object2FloatLinkedOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1517 */           Object2FloatLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1518 */           return true;
/*      */         } 
/* 1520 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1523 */         if ((curr = key[pos = pos + 1 & Object2FloatLinkedOpenCustomHashMap.this.mask]) == null)
/* 1524 */           return false; 
/* 1525 */         if (Object2FloatLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1526 */           Float.floatToIntBits(Object2FloatLinkedOpenCustomHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1527 */           Object2FloatLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1528 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1535 */       return Object2FloatLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1539 */       Object2FloatLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> from) {
/* 1554 */       return new Object2FloatLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2FloatMap.Entry<K>> fastIterator() {
/* 1565 */       return new Object2FloatLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2FloatMap.Entry<K>> fastIterator(Object2FloatMap.Entry<K> from) {
/* 1580 */       return new Object2FloatLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/* 1585 */       for (int i = Object2FloatLinkedOpenCustomHashMap.this.size, next = Object2FloatLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1586 */         int curr = next;
/* 1587 */         next = (int)Object2FloatLinkedOpenCustomHashMap.this.link[curr];
/* 1588 */         consumer.accept(new AbstractObject2FloatMap.BasicEntry<>(Object2FloatLinkedOpenCustomHashMap.this.key[curr], Object2FloatLinkedOpenCustomHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/* 1594 */       AbstractObject2FloatMap.BasicEntry<K> entry = new AbstractObject2FloatMap.BasicEntry<>();
/* 1595 */       for (int i = Object2FloatLinkedOpenCustomHashMap.this.size, next = Object2FloatLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1596 */         int curr = next;
/* 1597 */         next = (int)Object2FloatLinkedOpenCustomHashMap.this.link[curr];
/* 1598 */         entry.key = Object2FloatLinkedOpenCustomHashMap.this.key[curr];
/* 1599 */         entry.value = Object2FloatLinkedOpenCustomHashMap.this.value[curr];
/* 1600 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2FloatSortedMap.FastSortedEntrySet<K> object2FloatEntrySet() {
/* 1606 */     if (this.entries == null)
/* 1607 */       this.entries = new MapEntrySet(); 
/* 1608 */     return this.entries;
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
/* 1621 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1625 */       return Object2FloatLinkedOpenCustomHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1632 */       return Object2FloatLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1638 */       return new Object2FloatLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1642 */       return new Object2FloatLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1647 */       if (Object2FloatLinkedOpenCustomHashMap.this.containsNullKey)
/* 1648 */         consumer.accept(Object2FloatLinkedOpenCustomHashMap.this.key[Object2FloatLinkedOpenCustomHashMap.this.n]); 
/* 1649 */       for (int pos = Object2FloatLinkedOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1650 */         K k = Object2FloatLinkedOpenCustomHashMap.this.key[pos];
/* 1651 */         if (k != null)
/* 1652 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1657 */       return Object2FloatLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1661 */       return Object2FloatLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1665 */       int oldSize = Object2FloatLinkedOpenCustomHashMap.this.size;
/* 1666 */       Object2FloatLinkedOpenCustomHashMap.this.removeFloat(k);
/* 1667 */       return (Object2FloatLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1671 */       Object2FloatLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1675 */       if (Object2FloatLinkedOpenCustomHashMap.this.size == 0)
/* 1676 */         throw new NoSuchElementException(); 
/* 1677 */       return Object2FloatLinkedOpenCustomHashMap.this.key[Object2FloatLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1681 */       if (Object2FloatLinkedOpenCustomHashMap.this.size == 0)
/* 1682 */         throw new NoSuchElementException(); 
/* 1683 */       return Object2FloatLinkedOpenCustomHashMap.this.key[Object2FloatLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1687 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1691 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1695 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1699 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1704 */     if (this.keys == null)
/* 1705 */       this.keys = new KeySet(); 
/* 1706 */     return this.keys;
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public float previousFloat() {
/* 1720 */       return Object2FloatLinkedOpenCustomHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1727 */       return Object2FloatLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1732 */     if (this.values == null)
/* 1733 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1736 */             return (FloatIterator)new Object2FloatLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1740 */             return Object2FloatLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1744 */             return Object2FloatLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1748 */             Object2FloatLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1753 */             if (Object2FloatLinkedOpenCustomHashMap.this.containsNullKey)
/* 1754 */               consumer.accept(Object2FloatLinkedOpenCustomHashMap.this.value[Object2FloatLinkedOpenCustomHashMap.this.n]); 
/* 1755 */             for (int pos = Object2FloatLinkedOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1756 */               if (Object2FloatLinkedOpenCustomHashMap.this.key[pos] != null)
/* 1757 */                 consumer.accept(Object2FloatLinkedOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1760 */     return this.values;
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
/* 1777 */     return trim(this.size);
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
/* 1801 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1802 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1803 */       return true; 
/*      */     try {
/* 1805 */       rehash(l);
/* 1806 */     } catch (OutOfMemoryError cantDoIt) {
/* 1807 */       return false;
/*      */     } 
/* 1809 */     return true;
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
/* 1825 */     K[] key = this.key;
/* 1826 */     float[] value = this.value;
/* 1827 */     int mask = newN - 1;
/* 1828 */     K[] newKey = (K[])new Object[newN + 1];
/* 1829 */     float[] newValue = new float[newN + 1];
/* 1830 */     int i = this.first, prev = -1, newPrev = -1;
/* 1831 */     long[] link = this.link;
/* 1832 */     long[] newLink = new long[newN + 1];
/* 1833 */     this.first = -1;
/* 1834 */     for (int j = this.size; j-- != 0; ) {
/* 1835 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1836 */         pos = newN;
/*      */       } else {
/* 1838 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1839 */         while (newKey[pos] != null)
/* 1840 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1842 */       newKey[pos] = key[i];
/* 1843 */       newValue[pos] = value[i];
/* 1844 */       if (prev != -1) {
/* 1845 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1846 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1847 */         newPrev = pos;
/*      */       } else {
/* 1849 */         newPrev = this.first = pos;
/*      */         
/* 1851 */         newLink[pos] = -1L;
/*      */       } 
/* 1853 */       int t = i;
/* 1854 */       i = (int)link[i];
/* 1855 */       prev = t;
/*      */     } 
/* 1857 */     this.link = newLink;
/* 1858 */     this.last = newPrev;
/* 1859 */     if (newPrev != -1)
/*      */     {
/* 1861 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1862 */     this.n = newN;
/* 1863 */     this.mask = mask;
/* 1864 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1865 */     this.key = newKey;
/* 1866 */     this.value = newValue;
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
/*      */   public Object2FloatLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2FloatLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1883 */       c = (Object2FloatLinkedOpenCustomHashMap<K>)super.clone();
/* 1884 */     } catch (CloneNotSupportedException cantHappen) {
/* 1885 */       throw new InternalError();
/*      */     } 
/* 1887 */     c.keys = null;
/* 1888 */     c.values = null;
/* 1889 */     c.entries = null;
/* 1890 */     c.containsNullKey = this.containsNullKey;
/* 1891 */     c.key = (K[])this.key.clone();
/* 1892 */     c.value = (float[])this.value.clone();
/* 1893 */     c.link = (long[])this.link.clone();
/* 1894 */     c.strategy = this.strategy;
/* 1895 */     return c;
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
/* 1908 */     int h = 0;
/* 1909 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1910 */       while (this.key[i] == null)
/* 1911 */         i++; 
/* 1912 */       if (this != this.key[i])
/* 1913 */         t = this.strategy.hashCode(this.key[i]); 
/* 1914 */       t ^= HashCommon.float2int(this.value[i]);
/* 1915 */       h += t;
/* 1916 */       i++;
/*      */     } 
/*      */     
/* 1919 */     if (this.containsNullKey)
/* 1920 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1921 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1924 */     K[] key = this.key;
/* 1925 */     float[] value = this.value;
/* 1926 */     MapIterator i = new MapIterator();
/* 1927 */     s.defaultWriteObject();
/* 1928 */     for (int j = this.size; j-- != 0; ) {
/* 1929 */       int e = i.nextEntry();
/* 1930 */       s.writeObject(key[e]);
/* 1931 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1936 */     s.defaultReadObject();
/* 1937 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1938 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1939 */     this.mask = this.n - 1;
/* 1940 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1941 */     float[] value = this.value = new float[this.n + 1];
/* 1942 */     long[] link = this.link = new long[this.n + 1];
/* 1943 */     int prev = -1;
/* 1944 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1947 */     for (int i = this.size; i-- != 0; ) {
/* 1948 */       int pos; K k = (K)s.readObject();
/* 1949 */       float v = s.readFloat();
/* 1950 */       if (this.strategy.equals(k, null)) {
/* 1951 */         pos = this.n;
/* 1952 */         this.containsNullKey = true;
/*      */       } else {
/* 1954 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1955 */         while (key[pos] != null)
/* 1956 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1958 */       key[pos] = k;
/* 1959 */       value[pos] = v;
/* 1960 */       if (this.first != -1) {
/* 1961 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1962 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1963 */         prev = pos; continue;
/*      */       } 
/* 1965 */       prev = this.first = pos;
/*      */       
/* 1967 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1970 */     this.last = prev;
/* 1971 */     if (prev != -1)
/*      */     {
/* 1973 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */