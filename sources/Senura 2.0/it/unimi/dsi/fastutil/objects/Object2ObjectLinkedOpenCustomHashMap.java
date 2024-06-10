/*      */ package it.unimi.dsi.fastutil.objects;
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
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.SortedMap;
/*      */ import java.util.SortedSet;
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ObjectLinkedOpenCustomHashMap<K, V>
/*      */   extends AbstractObject2ObjectSortedMap<K, V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*  108 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   protected transient int last = -1;
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
/*      */   protected transient Object2ObjectSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  156 */     this.strategy = strategy;
/*  157 */     if (f <= 0.0F || f > 1.0F)
/*  158 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  159 */     if (expected < 0)
/*  160 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  161 */     this.f = f;
/*  162 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  163 */     this.mask = this.n - 1;
/*  164 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  165 */     this.key = (K[])new Object[this.n + 1];
/*  166 */     this.value = (V[])new Object[this.n + 1];
/*  167 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  178 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  189 */     this(16, 0.75F, strategy);
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
/*      */   public Object2ObjectLinkedOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Hash.Strategy<? super K> strategy) {
/*  203 */     this(m.size(), f, strategy);
/*  204 */     putAll(m);
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
/*      */   public Object2ObjectLinkedOpenCustomHashMap(Map<? extends K, ? extends V> m, Hash.Strategy<? super K> strategy) {
/*  217 */     this(m, 0.75F, strategy);
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
/*      */   public Object2ObjectLinkedOpenCustomHashMap(Object2ObjectMap<K, V> m, float f, Hash.Strategy<? super K> strategy) {
/*  231 */     this(m.size(), f, strategy);
/*  232 */     putAll(m);
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
/*      */   public Object2ObjectLinkedOpenCustomHashMap(Object2ObjectMap<K, V> m, Hash.Strategy<? super K> strategy) {
/*  244 */     this(m, 0.75F, strategy);
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
/*      */   public Object2ObjectLinkedOpenCustomHashMap(K[] k, V[] v, float f, Hash.Strategy<? super K> strategy) {
/*  262 */     this(k.length, f, strategy);
/*  263 */     if (k.length != v.length) {
/*  264 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  266 */     for (int i = 0; i < k.length; i++) {
/*  267 */       put(k[i], v[i]);
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
/*      */   public Object2ObjectLinkedOpenCustomHashMap(K[] k, V[] v, Hash.Strategy<? super K> strategy) {
/*  283 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  291 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  294 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  297 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  298 */     if (needed > this.n)
/*  299 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  302 */     int needed = (int)Math.min(1073741824L, 
/*  303 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  304 */     if (needed > this.n)
/*  305 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  308 */     V oldValue = this.value[pos];
/*  309 */     this.value[pos] = null;
/*  310 */     this.size--;
/*  311 */     fixPointers(pos);
/*  312 */     shiftKeys(pos);
/*  313 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  314 */       rehash(this.n / 2); 
/*  315 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  318 */     this.containsNullKey = false;
/*  319 */     this.key[this.n] = null;
/*  320 */     V oldValue = this.value[this.n];
/*  321 */     this.value[this.n] = null;
/*  322 */     this.size--;
/*  323 */     fixPointers(this.n);
/*  324 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  325 */       rehash(this.n / 2); 
/*  326 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  330 */     if (this.f <= 0.5D) {
/*  331 */       ensureCapacity(m.size());
/*      */     } else {
/*  333 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  335 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  339 */     if (this.strategy.equals(k, null)) {
/*  340 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  342 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  345 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  346 */       return -(pos + 1); 
/*  347 */     if (this.strategy.equals(k, curr)) {
/*  348 */       return pos;
/*      */     }
/*      */     while (true) {
/*  351 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  352 */         return -(pos + 1); 
/*  353 */       if (this.strategy.equals(k, curr))
/*  354 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, V v) {
/*  358 */     if (pos == this.n)
/*  359 */       this.containsNullKey = true; 
/*  360 */     this.key[pos] = k;
/*  361 */     this.value[pos] = v;
/*  362 */     if (this.size == 0) {
/*  363 */       this.first = this.last = pos;
/*      */       
/*  365 */       this.link[pos] = -1L;
/*      */     } else {
/*  367 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  368 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  369 */       this.last = pos;
/*      */     } 
/*  371 */     if (this.size++ >= this.maxFill) {
/*  372 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  378 */     int pos = find(k);
/*  379 */     if (pos < 0) {
/*  380 */       insert(-pos - 1, k, v);
/*  381 */       return this.defRetValue;
/*      */     } 
/*  383 */     V oldValue = this.value[pos];
/*  384 */     this.value[pos] = v;
/*  385 */     return oldValue;
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
/*  398 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  400 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  402 */         if ((curr = key[pos]) == null) {
/*  403 */           key[last] = null;
/*  404 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  407 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  408 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  410 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  412 */       key[last] = curr;
/*  413 */       this.value[last] = this.value[pos];
/*  414 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  420 */     if (this.strategy.equals(k, null)) {
/*  421 */       if (this.containsNullKey)
/*  422 */         return removeNullEntry(); 
/*  423 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  426 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  429 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  430 */       return this.defRetValue; 
/*  431 */     if (this.strategy.equals(k, curr))
/*  432 */       return removeEntry(pos); 
/*      */     while (true) {
/*  434 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  435 */         return this.defRetValue; 
/*  436 */       if (this.strategy.equals(k, curr))
/*  437 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private V setValue(int pos, V v) {
/*  441 */     V oldValue = this.value[pos];
/*  442 */     this.value[pos] = v;
/*  443 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeFirst() {
/*  454 */     if (this.size == 0)
/*  455 */       throw new NoSuchElementException(); 
/*  456 */     int pos = this.first;
/*      */     
/*  458 */     this.first = (int)this.link[pos];
/*  459 */     if (0 <= this.first)
/*      */     {
/*  461 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  463 */     this.size--;
/*  464 */     V v = this.value[pos];
/*  465 */     if (pos == this.n) {
/*  466 */       this.containsNullKey = false;
/*  467 */       this.key[this.n] = null;
/*  468 */       this.value[this.n] = null;
/*      */     } else {
/*  470 */       shiftKeys(pos);
/*  471 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  472 */       rehash(this.n / 2); 
/*  473 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  483 */     if (this.size == 0)
/*  484 */       throw new NoSuchElementException(); 
/*  485 */     int pos = this.last;
/*      */     
/*  487 */     this.last = (int)(this.link[pos] >>> 32L);
/*  488 */     if (0 <= this.last)
/*      */     {
/*  490 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  492 */     this.size--;
/*  493 */     V v = this.value[pos];
/*  494 */     if (pos == this.n) {
/*  495 */       this.containsNullKey = false;
/*  496 */       this.key[this.n] = null;
/*  497 */       this.value[this.n] = null;
/*      */     } else {
/*  499 */       shiftKeys(pos);
/*  500 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  501 */       rehash(this.n / 2); 
/*  502 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  505 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  507 */     if (this.last == i) {
/*  508 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  510 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  512 */       long linki = this.link[i];
/*  513 */       int prev = (int)(linki >>> 32L);
/*  514 */       int next = (int)linki;
/*  515 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  516 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  518 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  519 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  520 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  523 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  525 */     if (this.first == i) {
/*  526 */       this.first = (int)this.link[i];
/*      */       
/*  528 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  530 */       long linki = this.link[i];
/*  531 */       int prev = (int)(linki >>> 32L);
/*  532 */       int next = (int)linki;
/*  533 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  534 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  536 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  537 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  538 */     this.last = i;
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
/*      */   public V getAndMoveToFirst(K k) {
/*  550 */     if (this.strategy.equals(k, null)) {
/*  551 */       if (this.containsNullKey) {
/*  552 */         moveIndexToFirst(this.n);
/*  553 */         return this.value[this.n];
/*      */       } 
/*  555 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  558 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  561 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  562 */       return this.defRetValue; 
/*  563 */     if (this.strategy.equals(k, curr)) {
/*  564 */       moveIndexToFirst(pos);
/*  565 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  569 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  570 */         return this.defRetValue; 
/*  571 */       if (this.strategy.equals(k, curr)) {
/*  572 */         moveIndexToFirst(pos);
/*  573 */         return this.value[pos];
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
/*      */   public V getAndMoveToLast(K k) {
/*  587 */     if (this.strategy.equals(k, null)) {
/*  588 */       if (this.containsNullKey) {
/*  589 */         moveIndexToLast(this.n);
/*  590 */         return this.value[this.n];
/*      */       } 
/*  592 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  595 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  598 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  599 */       return this.defRetValue; 
/*  600 */     if (this.strategy.equals(k, curr)) {
/*  601 */       moveIndexToLast(pos);
/*  602 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  606 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  607 */         return this.defRetValue; 
/*  608 */       if (this.strategy.equals(k, curr)) {
/*  609 */         moveIndexToLast(pos);
/*  610 */         return this.value[pos];
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
/*      */   public V putAndMoveToFirst(K k, V v) {
/*      */     int pos;
/*  627 */     if (this.strategy.equals(k, null)) {
/*  628 */       if (this.containsNullKey) {
/*  629 */         moveIndexToFirst(this.n);
/*  630 */         return setValue(this.n, v);
/*      */       } 
/*  632 */       this.containsNullKey = true;
/*  633 */       pos = this.n;
/*      */     } else {
/*      */       
/*  636 */       K[] key = this.key;
/*      */       K curr;
/*  638 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  639 */         if (this.strategy.equals(curr, k)) {
/*  640 */           moveIndexToFirst(pos);
/*  641 */           return setValue(pos, v);
/*      */         } 
/*  643 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  644 */           if (this.strategy.equals(curr, k)) {
/*  645 */             moveIndexToFirst(pos);
/*  646 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  650 */     }  this.key[pos] = k;
/*  651 */     this.value[pos] = v;
/*  652 */     if (this.size == 0) {
/*  653 */       this.first = this.last = pos;
/*      */       
/*  655 */       this.link[pos] = -1L;
/*      */     } else {
/*  657 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  658 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  659 */       this.first = pos;
/*      */     } 
/*  661 */     if (this.size++ >= this.maxFill) {
/*  662 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  665 */     return this.defRetValue;
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
/*      */   public V putAndMoveToLast(K k, V v) {
/*      */     int pos;
/*  680 */     if (this.strategy.equals(k, null)) {
/*  681 */       if (this.containsNullKey) {
/*  682 */         moveIndexToLast(this.n);
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
/*  693 */           moveIndexToLast(pos);
/*  694 */           return setValue(pos, v);
/*      */         } 
/*  696 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  697 */           if (this.strategy.equals(curr, k)) {
/*  698 */             moveIndexToLast(pos);
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
/*  710 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  711 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  712 */       this.last = pos;
/*      */     } 
/*  714 */     if (this.size++ >= this.maxFill) {
/*  715 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  718 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  723 */     if (this.strategy.equals(k, null)) {
/*  724 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  726 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  729 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  730 */       return this.defRetValue; 
/*  731 */     if (this.strategy.equals(k, curr)) {
/*  732 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  735 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  736 */         return this.defRetValue; 
/*  737 */       if (this.strategy.equals(k, curr)) {
/*  738 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  744 */     if (this.strategy.equals(k, null)) {
/*  745 */       return this.containsNullKey;
/*      */     }
/*  747 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  750 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  751 */       return false; 
/*  752 */     if (this.strategy.equals(k, curr)) {
/*  753 */       return true;
/*      */     }
/*      */     while (true) {
/*  756 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  757 */         return false; 
/*  758 */       if (this.strategy.equals(k, curr))
/*  759 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  764 */     V[] value = this.value;
/*  765 */     K[] key = this.key;
/*  766 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  767 */       return true; 
/*  768 */     for (int i = this.n; i-- != 0;) {
/*  769 */       if (key[i] != null && Objects.equals(value[i], v))
/*  770 */         return true; 
/*  771 */     }  return false;
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
/*  782 */     if (this.size == 0)
/*      */       return; 
/*  784 */     this.size = 0;
/*  785 */     this.containsNullKey = false;
/*  786 */     Arrays.fill((Object[])this.key, (Object)null);
/*  787 */     Arrays.fill((Object[])this.value, (Object)null);
/*  788 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  792 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  796 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ObjectMap.Entry<K, V>, Map.Entry<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  808 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  814 */       return Object2ObjectLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  818 */       return Object2ObjectLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  822 */       V oldValue = Object2ObjectLinkedOpenCustomHashMap.this.value[this.index];
/*  823 */       Object2ObjectLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  824 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  829 */       if (!(o instanceof Map.Entry))
/*  830 */         return false; 
/*  831 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  832 */       return (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(Object2ObjectLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && 
/*  833 */         Objects.equals(Object2ObjectLinkedOpenCustomHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  837 */       return Object2ObjectLinkedOpenCustomHashMap.this.strategy.hashCode(Object2ObjectLinkedOpenCustomHashMap.this.key[this.index]) ^ ((Object2ObjectLinkedOpenCustomHashMap.this.value[this.index] == null) ? 0 : Object2ObjectLinkedOpenCustomHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  841 */       return (new StringBuilder()).append(Object2ObjectLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2ObjectLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/*  852 */     if (this.size == 0) {
/*  853 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  856 */     if (this.first == i) {
/*  857 */       this.first = (int)this.link[i];
/*  858 */       if (0 <= this.first)
/*      */       {
/*  860 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  864 */     if (this.last == i) {
/*  865 */       this.last = (int)(this.link[i] >>> 32L);
/*  866 */       if (0 <= this.last)
/*      */       {
/*  868 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  872 */     long linki = this.link[i];
/*  873 */     int prev = (int)(linki >>> 32L);
/*  874 */     int next = (int)linki;
/*  875 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  876 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  889 */     if (this.size == 1) {
/*  890 */       this.first = this.last = d;
/*      */       
/*  892 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  895 */     if (this.first == s) {
/*  896 */       this.first = d;
/*  897 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  898 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  901 */     if (this.last == s) {
/*  902 */       this.last = d;
/*  903 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  904 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  907 */     long links = this.link[s];
/*  908 */     int prev = (int)(links >>> 32L);
/*  909 */     int next = (int)links;
/*  910 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  911 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  912 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  921 */     if (this.size == 0)
/*  922 */       throw new NoSuchElementException(); 
/*  923 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  932 */     if (this.size == 0)
/*  933 */       throw new NoSuchElementException(); 
/*  934 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> tailMap(K from) {
/*  943 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> headMap(K to) {
/*  952 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/*  961 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  970 */     return null;
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
/*  985 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  996 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1002 */     int index = -1;
/*      */     protected MapIterator() {
/* 1004 */       this.next = Object2ObjectLinkedOpenCustomHashMap.this.first;
/* 1005 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1008 */       if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1009 */         if (Object2ObjectLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1010 */           this.next = (int)Object2ObjectLinkedOpenCustomHashMap.this.link[Object2ObjectLinkedOpenCustomHashMap.this.n];
/* 1011 */           this.prev = Object2ObjectLinkedOpenCustomHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1014 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1016 */       if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(Object2ObjectLinkedOpenCustomHashMap.this.key[Object2ObjectLinkedOpenCustomHashMap.this.last], from)) {
/* 1017 */         this.prev = Object2ObjectLinkedOpenCustomHashMap.this.last;
/* 1018 */         this.index = Object2ObjectLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1022 */       int pos = HashCommon.mix(Object2ObjectLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2ObjectLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1024 */       while (Object2ObjectLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1025 */         if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(Object2ObjectLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1027 */           this.next = (int)Object2ObjectLinkedOpenCustomHashMap.this.link[pos];
/* 1028 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1031 */         pos = pos + 1 & Object2ObjectLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1033 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1036 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1039 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1042 */       if (this.index >= 0)
/*      */         return; 
/* 1044 */       if (this.prev == -1) {
/* 1045 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1048 */       if (this.next == -1) {
/* 1049 */         this.index = Object2ObjectLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1052 */       int pos = Object2ObjectLinkedOpenCustomHashMap.this.first;
/* 1053 */       this.index = 1;
/* 1054 */       while (pos != this.prev) {
/* 1055 */         pos = (int)Object2ObjectLinkedOpenCustomHashMap.this.link[pos];
/* 1056 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1060 */       ensureIndexKnown();
/* 1061 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1064 */       ensureIndexKnown();
/* 1065 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1068 */       if (!hasNext())
/* 1069 */         throw new NoSuchElementException(); 
/* 1070 */       this.curr = this.next;
/* 1071 */       this.next = (int)Object2ObjectLinkedOpenCustomHashMap.this.link[this.curr];
/* 1072 */       this.prev = this.curr;
/* 1073 */       if (this.index >= 0)
/* 1074 */         this.index++; 
/* 1075 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1078 */       if (!hasPrevious())
/* 1079 */         throw new NoSuchElementException(); 
/* 1080 */       this.curr = this.prev;
/* 1081 */       this.prev = (int)(Object2ObjectLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1082 */       this.next = this.curr;
/* 1083 */       if (this.index >= 0)
/* 1084 */         this.index--; 
/* 1085 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1088 */       ensureIndexKnown();
/* 1089 */       if (this.curr == -1)
/* 1090 */         throw new IllegalStateException(); 
/* 1091 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1096 */         this.index--;
/* 1097 */         this.prev = (int)(Object2ObjectLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1099 */         this.next = (int)Object2ObjectLinkedOpenCustomHashMap.this.link[this.curr];
/* 1100 */       }  Object2ObjectLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1105 */       if (this.prev == -1) {
/* 1106 */         Object2ObjectLinkedOpenCustomHashMap.this.first = this.next;
/*      */       } else {
/* 1108 */         Object2ObjectLinkedOpenCustomHashMap.this.link[this.prev] = Object2ObjectLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2ObjectLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1109 */       }  if (this.next == -1) {
/* 1110 */         Object2ObjectLinkedOpenCustomHashMap.this.last = this.prev;
/*      */       } else {
/* 1112 */         Object2ObjectLinkedOpenCustomHashMap.this.link[this.next] = Object2ObjectLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2ObjectLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1113 */       }  int pos = this.curr;
/* 1114 */       this.curr = -1;
/* 1115 */       if (pos == Object2ObjectLinkedOpenCustomHashMap.this.n) {
/* 1116 */         Object2ObjectLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1117 */         Object2ObjectLinkedOpenCustomHashMap.this.key[Object2ObjectLinkedOpenCustomHashMap.this.n] = null;
/* 1118 */         Object2ObjectLinkedOpenCustomHashMap.this.value[Object2ObjectLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1121 */         K[] key = Object2ObjectLinkedOpenCustomHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1125 */           pos = (last = pos) + 1 & Object2ObjectLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1127 */             if ((curr = key[pos]) == null) {
/* 1128 */               key[last] = null;
/* 1129 */               Object2ObjectLinkedOpenCustomHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1132 */             int slot = HashCommon.mix(Object2ObjectLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2ObjectLinkedOpenCustomHashMap.this.mask;
/* 1133 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1135 */             pos = pos + 1 & Object2ObjectLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1137 */           key[last] = curr;
/* 1138 */           Object2ObjectLinkedOpenCustomHashMap.this.value[last] = Object2ObjectLinkedOpenCustomHashMap.this.value[pos];
/* 1139 */           if (this.next == pos)
/* 1140 */             this.next = last; 
/* 1141 */           if (this.prev == pos)
/* 1142 */             this.prev = last; 
/* 1143 */           Object2ObjectLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1148 */       int i = n;
/* 1149 */       while (i-- != 0 && hasNext())
/* 1150 */         nextEntry(); 
/* 1151 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1154 */       int i = n;
/* 1155 */       while (i-- != 0 && hasPrevious())
/* 1156 */         previousEntry(); 
/* 1157 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2ObjectMap.Entry<K, V> ok) {
/* 1160 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2ObjectMap.Entry<K, V> ok) {
/* 1163 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> { private Object2ObjectLinkedOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1171 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenCustomHashMap<K, V>.MapEntry next() {
/* 1175 */       return this.entry = new Object2ObjectLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenCustomHashMap<K, V>.MapEntry previous() {
/* 1179 */       return this.entry = new Object2ObjectLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1183 */       super.remove();
/* 1184 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1188 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> { final Object2ObjectLinkedOpenCustomHashMap<K, V>.MapEntry entry = new Object2ObjectLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1192 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenCustomHashMap<K, V>.MapEntry next() {
/* 1196 */       this.entry.index = nextEntry();
/* 1197 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenCustomHashMap<K, V>.MapEntry previous() {
/* 1201 */       this.entry.index = previousEntry();
/* 1202 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectSortedMap.FastSortedEntrySet<K, V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1210 */       return new Object2ObjectLinkedOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1214 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> fromElement, Object2ObjectMap.Entry<K, V> toElement) {
/* 1219 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> toElement) {
/* 1223 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> fromElement) {
/* 1227 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> first() {
/* 1231 */       if (Object2ObjectLinkedOpenCustomHashMap.this.size == 0)
/* 1232 */         throw new NoSuchElementException(); 
/* 1233 */       return new Object2ObjectLinkedOpenCustomHashMap.MapEntry(Object2ObjectLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> last() {
/* 1237 */       if (Object2ObjectLinkedOpenCustomHashMap.this.size == 0)
/* 1238 */         throw new NoSuchElementException(); 
/* 1239 */       return new Object2ObjectLinkedOpenCustomHashMap.MapEntry(Object2ObjectLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1244 */       if (!(o instanceof Map.Entry))
/* 1245 */         return false; 
/* 1246 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1247 */       K k = (K)e.getKey();
/* 1248 */       V v = (V)e.getValue();
/* 1249 */       if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1250 */         return (Object2ObjectLinkedOpenCustomHashMap.this.containsNullKey && 
/* 1251 */           Objects.equals(Object2ObjectLinkedOpenCustomHashMap.this.value[Object2ObjectLinkedOpenCustomHashMap.this.n], v));
/*      */       }
/* 1253 */       K[] key = Object2ObjectLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1256 */       if ((curr = key[pos = HashCommon.mix(Object2ObjectLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ObjectLinkedOpenCustomHashMap.this.mask]) == null)
/* 1257 */         return false; 
/* 1258 */       if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1259 */         return Objects.equals(Object2ObjectLinkedOpenCustomHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/* 1262 */         if ((curr = key[pos = pos + 1 & Object2ObjectLinkedOpenCustomHashMap.this.mask]) == null)
/* 1263 */           return false; 
/* 1264 */         if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1265 */           return Objects.equals(Object2ObjectLinkedOpenCustomHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1271 */       if (!(o instanceof Map.Entry))
/* 1272 */         return false; 
/* 1273 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1274 */       K k = (K)e.getKey();
/* 1275 */       V v = (V)e.getValue();
/* 1276 */       if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1277 */         if (Object2ObjectLinkedOpenCustomHashMap.this.containsNullKey && Objects.equals(Object2ObjectLinkedOpenCustomHashMap.this.value[Object2ObjectLinkedOpenCustomHashMap.this.n], v)) {
/* 1278 */           Object2ObjectLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1279 */           return true;
/*      */         } 
/* 1281 */         return false;
/*      */       } 
/*      */       
/* 1284 */       K[] key = Object2ObjectLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1287 */       if ((curr = key[pos = HashCommon.mix(Object2ObjectLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ObjectLinkedOpenCustomHashMap.this.mask]) == null)
/* 1288 */         return false; 
/* 1289 */       if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1290 */         if (Objects.equals(Object2ObjectLinkedOpenCustomHashMap.this.value[pos], v)) {
/* 1291 */           Object2ObjectLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1292 */           return true;
/*      */         } 
/* 1294 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1297 */         if ((curr = key[pos = pos + 1 & Object2ObjectLinkedOpenCustomHashMap.this.mask]) == null)
/* 1298 */           return false; 
/* 1299 */         if (Object2ObjectLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1300 */           Objects.equals(Object2ObjectLinkedOpenCustomHashMap.this.value[pos], v)) {
/* 1301 */           Object2ObjectLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1302 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1309 */       return Object2ObjectLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1313 */       Object2ObjectLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1328 */       return new Object2ObjectLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
/* 1339 */       return new Object2ObjectLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap.Entry<K, V> from) {
/* 1354 */       return new Object2ObjectLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/* 1359 */       for (int i = Object2ObjectLinkedOpenCustomHashMap.this.size, next = Object2ObjectLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1360 */         int curr = next;
/* 1361 */         next = (int)Object2ObjectLinkedOpenCustomHashMap.this.link[curr];
/* 1362 */         consumer.accept(new AbstractObject2ObjectMap.BasicEntry<>(Object2ObjectLinkedOpenCustomHashMap.this.key[curr], Object2ObjectLinkedOpenCustomHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/* 1368 */       AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
/* 1369 */       for (int i = Object2ObjectLinkedOpenCustomHashMap.this.size, next = Object2ObjectLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1370 */         int curr = next;
/* 1371 */         next = (int)Object2ObjectLinkedOpenCustomHashMap.this.link[curr];
/* 1372 */         entry.key = Object2ObjectLinkedOpenCustomHashMap.this.key[curr];
/* 1373 */         entry.value = Object2ObjectLinkedOpenCustomHashMap.this.value[curr];
/* 1374 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap.FastSortedEntrySet<K, V> object2ObjectEntrySet() {
/* 1380 */     if (this.entries == null)
/* 1381 */       this.entries = new MapEntrySet(); 
/* 1382 */     return this.entries;
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
/* 1395 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1399 */       return Object2ObjectLinkedOpenCustomHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1406 */       return Object2ObjectLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1412 */       return new Object2ObjectLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1416 */       return new Object2ObjectLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1421 */       if (Object2ObjectLinkedOpenCustomHashMap.this.containsNullKey)
/* 1422 */         consumer.accept(Object2ObjectLinkedOpenCustomHashMap.this.key[Object2ObjectLinkedOpenCustomHashMap.this.n]); 
/* 1423 */       for (int pos = Object2ObjectLinkedOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1424 */         K k = Object2ObjectLinkedOpenCustomHashMap.this.key[pos];
/* 1425 */         if (k != null)
/* 1426 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1431 */       return Object2ObjectLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1435 */       return Object2ObjectLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1439 */       int oldSize = Object2ObjectLinkedOpenCustomHashMap.this.size;
/* 1440 */       Object2ObjectLinkedOpenCustomHashMap.this.remove(k);
/* 1441 */       return (Object2ObjectLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1445 */       Object2ObjectLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1449 */       if (Object2ObjectLinkedOpenCustomHashMap.this.size == 0)
/* 1450 */         throw new NoSuchElementException(); 
/* 1451 */       return Object2ObjectLinkedOpenCustomHashMap.this.key[Object2ObjectLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1455 */       if (Object2ObjectLinkedOpenCustomHashMap.this.size == 0)
/* 1456 */         throw new NoSuchElementException(); 
/* 1457 */       return Object2ObjectLinkedOpenCustomHashMap.this.key[Object2ObjectLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1461 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1465 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1469 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1473 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1478 */     if (this.keys == null)
/* 1479 */       this.keys = new KeySet(); 
/* 1480 */     return this.keys;
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
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     public V previous() {
/* 1494 */       return Object2ObjectLinkedOpenCustomHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1501 */       return Object2ObjectLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1506 */     if (this.values == null)
/* 1507 */       this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1510 */             return new Object2ObjectLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1514 */             return Object2ObjectLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1518 */             return Object2ObjectLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1522 */             Object2ObjectLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1527 */             if (Object2ObjectLinkedOpenCustomHashMap.this.containsNullKey)
/* 1528 */               consumer.accept(Object2ObjectLinkedOpenCustomHashMap.this.value[Object2ObjectLinkedOpenCustomHashMap.this.n]); 
/* 1529 */             for (int pos = Object2ObjectLinkedOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1530 */               if (Object2ObjectLinkedOpenCustomHashMap.this.key[pos] != null)
/* 1531 */                 consumer.accept(Object2ObjectLinkedOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1534 */     return this.values;
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
/* 1551 */     return trim(this.size);
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
/* 1575 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1576 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1577 */       return true; 
/*      */     try {
/* 1579 */       rehash(l);
/* 1580 */     } catch (OutOfMemoryError cantDoIt) {
/* 1581 */       return false;
/*      */     } 
/* 1583 */     return true;
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
/* 1599 */     K[] key = this.key;
/* 1600 */     V[] value = this.value;
/* 1601 */     int mask = newN - 1;
/* 1602 */     K[] newKey = (K[])new Object[newN + 1];
/* 1603 */     V[] newValue = (V[])new Object[newN + 1];
/* 1604 */     int i = this.first, prev = -1, newPrev = -1;
/* 1605 */     long[] link = this.link;
/* 1606 */     long[] newLink = new long[newN + 1];
/* 1607 */     this.first = -1;
/* 1608 */     for (int j = this.size; j-- != 0; ) {
/* 1609 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1610 */         pos = newN;
/*      */       } else {
/* 1612 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1613 */         while (newKey[pos] != null)
/* 1614 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1616 */       newKey[pos] = key[i];
/* 1617 */       newValue[pos] = value[i];
/* 1618 */       if (prev != -1) {
/* 1619 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1620 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1621 */         newPrev = pos;
/*      */       } else {
/* 1623 */         newPrev = this.first = pos;
/*      */         
/* 1625 */         newLink[pos] = -1L;
/*      */       } 
/* 1627 */       int t = i;
/* 1628 */       i = (int)link[i];
/* 1629 */       prev = t;
/*      */     } 
/* 1631 */     this.link = newLink;
/* 1632 */     this.last = newPrev;
/* 1633 */     if (newPrev != -1)
/*      */     {
/* 1635 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1636 */     this.n = newN;
/* 1637 */     this.mask = mask;
/* 1638 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1639 */     this.key = newKey;
/* 1640 */     this.value = newValue;
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
/*      */   public Object2ObjectLinkedOpenCustomHashMap<K, V> clone() {
/*      */     Object2ObjectLinkedOpenCustomHashMap<K, V> c;
/*      */     try {
/* 1657 */       c = (Object2ObjectLinkedOpenCustomHashMap<K, V>)super.clone();
/* 1658 */     } catch (CloneNotSupportedException cantHappen) {
/* 1659 */       throw new InternalError();
/*      */     } 
/* 1661 */     c.keys = null;
/* 1662 */     c.values = null;
/* 1663 */     c.entries = null;
/* 1664 */     c.containsNullKey = this.containsNullKey;
/* 1665 */     c.key = (K[])this.key.clone();
/* 1666 */     c.value = (V[])this.value.clone();
/* 1667 */     c.link = (long[])this.link.clone();
/* 1668 */     c.strategy = this.strategy;
/* 1669 */     return c;
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
/* 1682 */     int h = 0;
/* 1683 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1684 */       while (this.key[i] == null)
/* 1685 */         i++; 
/* 1686 */       if (this != this.key[i])
/* 1687 */         t = this.strategy.hashCode(this.key[i]); 
/* 1688 */       if (this != this.value[i])
/* 1689 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1690 */       h += t;
/* 1691 */       i++;
/*      */     } 
/*      */     
/* 1694 */     if (this.containsNullKey)
/* 1695 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1696 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1699 */     K[] key = this.key;
/* 1700 */     V[] value = this.value;
/* 1701 */     MapIterator i = new MapIterator();
/* 1702 */     s.defaultWriteObject();
/* 1703 */     for (int j = this.size; j-- != 0; ) {
/* 1704 */       int e = i.nextEntry();
/* 1705 */       s.writeObject(key[e]);
/* 1706 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1711 */     s.defaultReadObject();
/* 1712 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1713 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1714 */     this.mask = this.n - 1;
/* 1715 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1716 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1717 */     long[] link = this.link = new long[this.n + 1];
/* 1718 */     int prev = -1;
/* 1719 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1722 */     for (int i = this.size; i-- != 0; ) {
/* 1723 */       int pos; K k = (K)s.readObject();
/* 1724 */       V v = (V)s.readObject();
/* 1725 */       if (this.strategy.equals(k, null)) {
/* 1726 */         pos = this.n;
/* 1727 */         this.containsNullKey = true;
/*      */       } else {
/* 1729 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1730 */         while (key[pos] != null)
/* 1731 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1733 */       key[pos] = k;
/* 1734 */       value[pos] = v;
/* 1735 */       if (this.first != -1) {
/* 1736 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1737 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1738 */         prev = pos; continue;
/*      */       } 
/* 1740 */       prev = this.first = pos;
/*      */       
/* 1742 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1745 */     this.last = prev;
/* 1746 */     if (prev != -1)
/*      */     {
/* 1748 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */