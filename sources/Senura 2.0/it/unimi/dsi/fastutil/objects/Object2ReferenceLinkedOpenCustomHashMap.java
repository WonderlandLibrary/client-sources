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
/*      */ 
/*      */ public class Object2ReferenceLinkedOpenCustomHashMap<K, V>
/*      */   extends AbstractObject2ReferenceSortedMap<K, V>
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
/*      */   protected transient Object2ReferenceSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*  167 */     this.value = (V[])new Object[this.n + 1];
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Map<? extends K, ? extends V> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Map<? extends K, ? extends V> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Object2ReferenceMap<K, V> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(Object2ReferenceMap<K, V> m, Hash.Strategy<? super K> strategy) {
/*  246 */     this(m, 0.75F, strategy);
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(K[] k, V[] v, float f, Hash.Strategy<? super K> strategy) {
/*  264 */     this(k.length, f, strategy);
/*  265 */     if (k.length != v.length) {
/*  266 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  268 */     for (int i = 0; i < k.length; i++) {
/*  269 */       put(k[i], v[i]);
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap(K[] k, V[] v, Hash.Strategy<? super K> strategy) {
/*  285 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  293 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  296 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  299 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  300 */     if (needed > this.n)
/*  301 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  304 */     int needed = (int)Math.min(1073741824L, 
/*  305 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  306 */     if (needed > this.n)
/*  307 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  310 */     V oldValue = this.value[pos];
/*  311 */     this.value[pos] = null;
/*  312 */     this.size--;
/*  313 */     fixPointers(pos);
/*  314 */     shiftKeys(pos);
/*  315 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  316 */       rehash(this.n / 2); 
/*  317 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  320 */     this.containsNullKey = false;
/*  321 */     this.key[this.n] = null;
/*  322 */     V oldValue = this.value[this.n];
/*  323 */     this.value[this.n] = null;
/*  324 */     this.size--;
/*  325 */     fixPointers(this.n);
/*  326 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  327 */       rehash(this.n / 2); 
/*  328 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
/*  332 */     if (this.f <= 0.5D) {
/*  333 */       ensureCapacity(m.size());
/*      */     } else {
/*  335 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  337 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  341 */     if (this.strategy.equals(k, null)) {
/*  342 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  344 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  347 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  348 */       return -(pos + 1); 
/*  349 */     if (this.strategy.equals(k, curr)) {
/*  350 */       return pos;
/*      */     }
/*      */     while (true) {
/*  353 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  354 */         return -(pos + 1); 
/*  355 */       if (this.strategy.equals(k, curr))
/*  356 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, V v) {
/*  360 */     if (pos == this.n)
/*  361 */       this.containsNullKey = true; 
/*  362 */     this.key[pos] = k;
/*  363 */     this.value[pos] = v;
/*  364 */     if (this.size == 0) {
/*  365 */       this.first = this.last = pos;
/*      */       
/*  367 */       this.link[pos] = -1L;
/*      */     } else {
/*  369 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  370 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  371 */       this.last = pos;
/*      */     } 
/*  373 */     if (this.size++ >= this.maxFill) {
/*  374 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(K k, V v) {
/*  380 */     int pos = find(k);
/*  381 */     if (pos < 0) {
/*  382 */       insert(-pos - 1, k, v);
/*  383 */       return this.defRetValue;
/*      */     } 
/*  385 */     V oldValue = this.value[pos];
/*  386 */     this.value[pos] = v;
/*  387 */     return oldValue;
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
/*  400 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  402 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  404 */         if ((curr = key[pos]) == null) {
/*  405 */           key[last] = null;
/*  406 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  409 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  410 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  412 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  414 */       key[last] = curr;
/*  415 */       this.value[last] = this.value[pos];
/*  416 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  422 */     if (this.strategy.equals(k, null)) {
/*  423 */       if (this.containsNullKey)
/*  424 */         return removeNullEntry(); 
/*  425 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  428 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  431 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  432 */       return this.defRetValue; 
/*  433 */     if (this.strategy.equals(k, curr))
/*  434 */       return removeEntry(pos); 
/*      */     while (true) {
/*  436 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  437 */         return this.defRetValue; 
/*  438 */       if (this.strategy.equals(k, curr))
/*  439 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private V setValue(int pos, V v) {
/*  443 */     V oldValue = this.value[pos];
/*  444 */     this.value[pos] = v;
/*  445 */     return oldValue;
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
/*  456 */     if (this.size == 0)
/*  457 */       throw new NoSuchElementException(); 
/*  458 */     int pos = this.first;
/*      */     
/*  460 */     this.first = (int)this.link[pos];
/*  461 */     if (0 <= this.first)
/*      */     {
/*  463 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  465 */     this.size--;
/*  466 */     V v = this.value[pos];
/*  467 */     if (pos == this.n) {
/*  468 */       this.containsNullKey = false;
/*  469 */       this.key[this.n] = null;
/*  470 */       this.value[this.n] = null;
/*      */     } else {
/*  472 */       shiftKeys(pos);
/*  473 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  474 */       rehash(this.n / 2); 
/*  475 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  485 */     if (this.size == 0)
/*  486 */       throw new NoSuchElementException(); 
/*  487 */     int pos = this.last;
/*      */     
/*  489 */     this.last = (int)(this.link[pos] >>> 32L);
/*  490 */     if (0 <= this.last)
/*      */     {
/*  492 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  494 */     this.size--;
/*  495 */     V v = this.value[pos];
/*  496 */     if (pos == this.n) {
/*  497 */       this.containsNullKey = false;
/*  498 */       this.key[this.n] = null;
/*  499 */       this.value[this.n] = null;
/*      */     } else {
/*  501 */       shiftKeys(pos);
/*  502 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  503 */       rehash(this.n / 2); 
/*  504 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  507 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  509 */     if (this.last == i) {
/*  510 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  512 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  514 */       long linki = this.link[i];
/*  515 */       int prev = (int)(linki >>> 32L);
/*  516 */       int next = (int)linki;
/*  517 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  518 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  520 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  521 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  522 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  525 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  527 */     if (this.first == i) {
/*  528 */       this.first = (int)this.link[i];
/*      */       
/*  530 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  532 */       long linki = this.link[i];
/*  533 */       int prev = (int)(linki >>> 32L);
/*  534 */       int next = (int)linki;
/*  535 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  536 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  538 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  539 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  540 */     this.last = i;
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
/*  552 */     if (this.strategy.equals(k, null)) {
/*  553 */       if (this.containsNullKey) {
/*  554 */         moveIndexToFirst(this.n);
/*  555 */         return this.value[this.n];
/*      */       } 
/*  557 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  560 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  563 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  564 */       return this.defRetValue; 
/*  565 */     if (this.strategy.equals(k, curr)) {
/*  566 */       moveIndexToFirst(pos);
/*  567 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  571 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  572 */         return this.defRetValue; 
/*  573 */       if (this.strategy.equals(k, curr)) {
/*  574 */         moveIndexToFirst(pos);
/*  575 */         return this.value[pos];
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
/*  589 */     if (this.strategy.equals(k, null)) {
/*  590 */       if (this.containsNullKey) {
/*  591 */         moveIndexToLast(this.n);
/*  592 */         return this.value[this.n];
/*      */       } 
/*  594 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  597 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  600 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  601 */       return this.defRetValue; 
/*  602 */     if (this.strategy.equals(k, curr)) {
/*  603 */       moveIndexToLast(pos);
/*  604 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  608 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  609 */         return this.defRetValue; 
/*  610 */       if (this.strategy.equals(k, curr)) {
/*  611 */         moveIndexToLast(pos);
/*  612 */         return this.value[pos];
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
/*  629 */     if (this.strategy.equals(k, null)) {
/*  630 */       if (this.containsNullKey) {
/*  631 */         moveIndexToFirst(this.n);
/*  632 */         return setValue(this.n, v);
/*      */       } 
/*  634 */       this.containsNullKey = true;
/*  635 */       pos = this.n;
/*      */     } else {
/*      */       
/*  638 */       K[] key = this.key;
/*      */       K curr;
/*  640 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  641 */         if (this.strategy.equals(curr, k)) {
/*  642 */           moveIndexToFirst(pos);
/*  643 */           return setValue(pos, v);
/*      */         } 
/*  645 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  646 */           if (this.strategy.equals(curr, k)) {
/*  647 */             moveIndexToFirst(pos);
/*  648 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  652 */     }  this.key[pos] = k;
/*  653 */     this.value[pos] = v;
/*  654 */     if (this.size == 0) {
/*  655 */       this.first = this.last = pos;
/*      */       
/*  657 */       this.link[pos] = -1L;
/*      */     } else {
/*  659 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  660 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  661 */       this.first = pos;
/*      */     } 
/*  663 */     if (this.size++ >= this.maxFill) {
/*  664 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  667 */     return this.defRetValue;
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
/*  682 */     if (this.strategy.equals(k, null)) {
/*  683 */       if (this.containsNullKey) {
/*  684 */         moveIndexToLast(this.n);
/*  685 */         return setValue(this.n, v);
/*      */       } 
/*  687 */       this.containsNullKey = true;
/*  688 */       pos = this.n;
/*      */     } else {
/*      */       
/*  691 */       K[] key = this.key;
/*      */       K curr;
/*  693 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  694 */         if (this.strategy.equals(curr, k)) {
/*  695 */           moveIndexToLast(pos);
/*  696 */           return setValue(pos, v);
/*      */         } 
/*  698 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  699 */           if (this.strategy.equals(curr, k)) {
/*  700 */             moveIndexToLast(pos);
/*  701 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  705 */     }  this.key[pos] = k;
/*  706 */     this.value[pos] = v;
/*  707 */     if (this.size == 0) {
/*  708 */       this.first = this.last = pos;
/*      */       
/*  710 */       this.link[pos] = -1L;
/*      */     } else {
/*  712 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  713 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  714 */       this.last = pos;
/*      */     } 
/*  716 */     if (this.size++ >= this.maxFill) {
/*  717 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  720 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  725 */     if (this.strategy.equals(k, null)) {
/*  726 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  728 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  731 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  732 */       return this.defRetValue; 
/*  733 */     if (this.strategy.equals(k, curr)) {
/*  734 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  737 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  738 */         return this.defRetValue; 
/*  739 */       if (this.strategy.equals(k, curr)) {
/*  740 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  746 */     if (this.strategy.equals(k, null)) {
/*  747 */       return this.containsNullKey;
/*      */     }
/*  749 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  752 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  753 */       return false; 
/*  754 */     if (this.strategy.equals(k, curr)) {
/*  755 */       return true;
/*      */     }
/*      */     while (true) {
/*  758 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  759 */         return false; 
/*  760 */       if (this.strategy.equals(k, curr))
/*  761 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  766 */     V[] value = this.value;
/*  767 */     K[] key = this.key;
/*  768 */     if (this.containsNullKey && value[this.n] == v)
/*  769 */       return true; 
/*  770 */     for (int i = this.n; i-- != 0;) {
/*  771 */       if (key[i] != null && value[i] == v)
/*  772 */         return true; 
/*  773 */     }  return false;
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
/*  784 */     if (this.size == 0)
/*      */       return; 
/*  786 */     this.size = 0;
/*  787 */     this.containsNullKey = false;
/*  788 */     Arrays.fill((Object[])this.key, (Object)null);
/*  789 */     Arrays.fill((Object[])this.value, (Object)null);
/*  790 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  794 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  798 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ReferenceMap.Entry<K, V>, Map.Entry<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  810 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  816 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  820 */       return Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  824 */       V oldValue = Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index];
/*  825 */       Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  826 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  831 */       if (!(o instanceof Map.Entry))
/*  832 */         return false; 
/*  833 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  834 */       return (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  838 */       return Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index]) ^ (
/*  839 */         (Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  843 */       return (new StringBuilder()).append(Object2ReferenceLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2ReferenceLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/*  854 */     if (this.size == 0) {
/*  855 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  858 */     if (this.first == i) {
/*  859 */       this.first = (int)this.link[i];
/*  860 */       if (0 <= this.first)
/*      */       {
/*  862 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  866 */     if (this.last == i) {
/*  867 */       this.last = (int)(this.link[i] >>> 32L);
/*  868 */       if (0 <= this.last)
/*      */       {
/*  870 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  874 */     long linki = this.link[i];
/*  875 */     int prev = (int)(linki >>> 32L);
/*  876 */     int next = (int)linki;
/*  877 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  878 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  891 */     if (this.size == 1) {
/*  892 */       this.first = this.last = d;
/*      */       
/*  894 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  897 */     if (this.first == s) {
/*  898 */       this.first = d;
/*  899 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  900 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  903 */     if (this.last == s) {
/*  904 */       this.last = d;
/*  905 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  906 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  909 */     long links = this.link[s];
/*  910 */     int prev = (int)(links >>> 32L);
/*  911 */     int next = (int)links;
/*  912 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  913 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  914 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  923 */     if (this.size == 0)
/*  924 */       throw new NoSuchElementException(); 
/*  925 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  934 */     if (this.size == 0)
/*  935 */       throw new NoSuchElementException(); 
/*  936 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/*  945 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> headMap(K to) {
/*  954 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/*  963 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  972 */     return null;
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
/*  987 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  993 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1004 */     int index = -1;
/*      */     protected MapIterator() {
/* 1006 */       this.next = Object2ReferenceLinkedOpenCustomHashMap.this.first;
/* 1007 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1010 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1011 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1012 */           this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[Object2ReferenceLinkedOpenCustomHashMap.this.n];
/* 1013 */           this.prev = Object2ReferenceLinkedOpenCustomHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1016 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1018 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.last], from)) {
/* 1019 */         this.prev = Object2ReferenceLinkedOpenCustomHashMap.this.last;
/* 1020 */         this.index = Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1024 */       int pos = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1026 */       while (Object2ReferenceLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1027 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(Object2ReferenceLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1029 */           this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[pos];
/* 1030 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1033 */         pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1035 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1038 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1041 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1044 */       if (this.index >= 0)
/*      */         return; 
/* 1046 */       if (this.prev == -1) {
/* 1047 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1050 */       if (this.next == -1) {
/* 1051 */         this.index = Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1054 */       int pos = Object2ReferenceLinkedOpenCustomHashMap.this.first;
/* 1055 */       this.index = 1;
/* 1056 */       while (pos != this.prev) {
/* 1057 */         pos = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[pos];
/* 1058 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1062 */       ensureIndexKnown();
/* 1063 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1066 */       ensureIndexKnown();
/* 1067 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1070 */       if (!hasNext())
/* 1071 */         throw new NoSuchElementException(); 
/* 1072 */       this.curr = this.next;
/* 1073 */       this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr];
/* 1074 */       this.prev = this.curr;
/* 1075 */       if (this.index >= 0)
/* 1076 */         this.index++; 
/* 1077 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1080 */       if (!hasPrevious())
/* 1081 */         throw new NoSuchElementException(); 
/* 1082 */       this.curr = this.prev;
/* 1083 */       this.prev = (int)(Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1084 */       this.next = this.curr;
/* 1085 */       if (this.index >= 0)
/* 1086 */         this.index--; 
/* 1087 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1090 */       ensureIndexKnown();
/* 1091 */       if (this.curr == -1)
/* 1092 */         throw new IllegalStateException(); 
/* 1093 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1098 */         this.index--;
/* 1099 */         this.prev = (int)(Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1101 */         this.next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[this.curr];
/* 1102 */       }  Object2ReferenceLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1107 */       if (this.prev == -1) {
/* 1108 */         Object2ReferenceLinkedOpenCustomHashMap.this.first = this.next;
/*      */       } else {
/* 1110 */         Object2ReferenceLinkedOpenCustomHashMap.this.link[this.prev] = Object2ReferenceLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2ReferenceLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1111 */       }  if (this.next == -1) {
/* 1112 */         Object2ReferenceLinkedOpenCustomHashMap.this.last = this.prev;
/*      */       } else {
/* 1114 */         Object2ReferenceLinkedOpenCustomHashMap.this.link[this.next] = Object2ReferenceLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2ReferenceLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1115 */       }  int pos = this.curr;
/* 1116 */       this.curr = -1;
/* 1117 */       if (pos == Object2ReferenceLinkedOpenCustomHashMap.this.n) {
/* 1118 */         Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1119 */         Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.n] = null;
/* 1120 */         Object2ReferenceLinkedOpenCustomHashMap.this.value[Object2ReferenceLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1123 */         K[] key = Object2ReferenceLinkedOpenCustomHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1127 */           pos = (last = pos) + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1129 */             if ((curr = key[pos]) == null) {
/* 1130 */               key[last] = null;
/* 1131 */               Object2ReferenceLinkedOpenCustomHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1134 */             int slot = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/* 1135 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1137 */             pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1139 */           key[last] = curr;
/* 1140 */           Object2ReferenceLinkedOpenCustomHashMap.this.value[last] = Object2ReferenceLinkedOpenCustomHashMap.this.value[pos];
/* 1141 */           if (this.next == pos)
/* 1142 */             this.next = last; 
/* 1143 */           if (this.prev == pos)
/* 1144 */             this.prev = last; 
/* 1145 */           Object2ReferenceLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1150 */       int i = n;
/* 1151 */       while (i-- != 0 && hasNext())
/* 1152 */         nextEntry(); 
/* 1153 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1156 */       int i = n;
/* 1157 */       while (i-- != 0 && hasPrevious())
/* 1158 */         previousEntry(); 
/* 1159 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2ReferenceMap.Entry<K, V> ok) {
/* 1162 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2ReferenceMap.Entry<K, V> ok) {
/* 1165 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> { private Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1173 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry next() {
/* 1177 */       return this.entry = new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry previous() {
/* 1181 */       return this.entry = new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1185 */       super.remove();
/* 1186 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1190 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> { final Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry entry = new Object2ReferenceLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1194 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry next() {
/* 1198 */       this.entry.index = nextEntry();
/* 1199 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenCustomHashMap<K, V>.MapEntry previous() {
/* 1203 */       this.entry.index = previousEntry();
/* 1204 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ReferenceMap.Entry<K, V>> implements Object2ReferenceSortedMap.FastSortedEntrySet<K, V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1212 */       return new Object2ReferenceLinkedOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1216 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> fromElement, Object2ReferenceMap.Entry<K, V> toElement) {
/* 1221 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> toElement) {
/* 1225 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> fromElement) {
/* 1229 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> first() {
/* 1233 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0)
/* 1234 */         throw new NoSuchElementException(); 
/* 1235 */       return new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(Object2ReferenceLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> last() {
/* 1239 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0)
/* 1240 */         throw new NoSuchElementException(); 
/* 1241 */       return new Object2ReferenceLinkedOpenCustomHashMap.MapEntry(Object2ReferenceLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1246 */       if (!(o instanceof Map.Entry))
/* 1247 */         return false; 
/* 1248 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1249 */       K k = (K)e.getKey();
/* 1250 */       V v = (V)e.getValue();
/* 1251 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1252 */         return (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey && Object2ReferenceLinkedOpenCustomHashMap.this.value[Object2ReferenceLinkedOpenCustomHashMap.this.n] == v);
/*      */       }
/* 1254 */       K[] key = Object2ReferenceLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1257 */       if ((curr = key[pos = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null)
/* 1258 */         return false; 
/* 1259 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1260 */         return (Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1263 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null)
/* 1264 */           return false; 
/* 1265 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1266 */           return (Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1272 */       if (!(o instanceof Map.Entry))
/* 1273 */         return false; 
/* 1274 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1275 */       K k = (K)e.getKey();
/* 1276 */       V v = (V)e.getValue();
/* 1277 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1278 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey && Object2ReferenceLinkedOpenCustomHashMap.this.value[Object2ReferenceLinkedOpenCustomHashMap.this.n] == v) {
/* 1279 */           Object2ReferenceLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1280 */           return true;
/*      */         } 
/* 1282 */         return false;
/*      */       } 
/*      */       
/* 1285 */       K[] key = Object2ReferenceLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1288 */       if ((curr = key[pos = HashCommon.mix(Object2ReferenceLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null)
/* 1289 */         return false; 
/* 1290 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1291 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1292 */           Object2ReferenceLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1293 */           return true;
/*      */         } 
/* 1295 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1298 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenCustomHashMap.this.mask]) == null)
/* 1299 */           return false; 
/* 1300 */         if (Object2ReferenceLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1301 */           Object2ReferenceLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1302 */           Object2ReferenceLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1303 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1310 */       return Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1314 */       Object2ReferenceLinkedOpenCustomHashMap.this.clear();
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
/*      */     
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1330 */       return new Object2ReferenceLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator() {
/* 1341 */       return new Object2ReferenceLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1357 */       return new Object2ReferenceLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1362 */       for (int i = Object2ReferenceLinkedOpenCustomHashMap.this.size, next = Object2ReferenceLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1363 */         int curr = next;
/* 1364 */         next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[curr];
/* 1365 */         consumer.accept(new AbstractObject2ReferenceMap.BasicEntry<>(Object2ReferenceLinkedOpenCustomHashMap.this.key[curr], Object2ReferenceLinkedOpenCustomHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1371 */       AbstractObject2ReferenceMap.BasicEntry<K, V> entry = new AbstractObject2ReferenceMap.BasicEntry<>();
/* 1372 */       for (int i = Object2ReferenceLinkedOpenCustomHashMap.this.size, next = Object2ReferenceLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1373 */         int curr = next;
/* 1374 */         next = (int)Object2ReferenceLinkedOpenCustomHashMap.this.link[curr];
/* 1375 */         entry.key = Object2ReferenceLinkedOpenCustomHashMap.this.key[curr];
/* 1376 */         entry.value = Object2ReferenceLinkedOpenCustomHashMap.this.value[curr];
/* 1377 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap.FastSortedEntrySet<K, V> object2ReferenceEntrySet() {
/* 1383 */     if (this.entries == null)
/* 1384 */       this.entries = new MapEntrySet(); 
/* 1385 */     return this.entries;
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
/* 1398 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1402 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1409 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1415 */       return new Object2ReferenceLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1419 */       return new Object2ReferenceLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1424 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey)
/* 1425 */         consumer.accept(Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.n]); 
/* 1426 */       for (int pos = Object2ReferenceLinkedOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1427 */         K k = Object2ReferenceLinkedOpenCustomHashMap.this.key[pos];
/* 1428 */         if (k != null)
/* 1429 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1434 */       return Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1438 */       return Object2ReferenceLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1442 */       int oldSize = Object2ReferenceLinkedOpenCustomHashMap.this.size;
/* 1443 */       Object2ReferenceLinkedOpenCustomHashMap.this.remove(k);
/* 1444 */       return (Object2ReferenceLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1448 */       Object2ReferenceLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1452 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0)
/* 1453 */         throw new NoSuchElementException(); 
/* 1454 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1458 */       if (Object2ReferenceLinkedOpenCustomHashMap.this.size == 0)
/* 1459 */         throw new NoSuchElementException(); 
/* 1460 */       return Object2ReferenceLinkedOpenCustomHashMap.this.key[Object2ReferenceLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1464 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1468 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1472 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1476 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1481 */     if (this.keys == null)
/* 1482 */       this.keys = new KeySet(); 
/* 1483 */     return this.keys;
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
/* 1497 */       return Object2ReferenceLinkedOpenCustomHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1504 */       return Object2ReferenceLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1509 */     if (this.values == null)
/* 1510 */       this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1513 */             return new Object2ReferenceLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1517 */             return Object2ReferenceLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1521 */             return Object2ReferenceLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1525 */             Object2ReferenceLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1530 */             if (Object2ReferenceLinkedOpenCustomHashMap.this.containsNullKey)
/* 1531 */               consumer.accept(Object2ReferenceLinkedOpenCustomHashMap.this.value[Object2ReferenceLinkedOpenCustomHashMap.this.n]); 
/* 1532 */             for (int pos = Object2ReferenceLinkedOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1533 */               if (Object2ReferenceLinkedOpenCustomHashMap.this.key[pos] != null)
/* 1534 */                 consumer.accept(Object2ReferenceLinkedOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1537 */     return this.values;
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
/* 1554 */     return trim(this.size);
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
/* 1578 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1579 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1580 */       return true; 
/*      */     try {
/* 1582 */       rehash(l);
/* 1583 */     } catch (OutOfMemoryError cantDoIt) {
/* 1584 */       return false;
/*      */     } 
/* 1586 */     return true;
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
/* 1602 */     K[] key = this.key;
/* 1603 */     V[] value = this.value;
/* 1604 */     int mask = newN - 1;
/* 1605 */     K[] newKey = (K[])new Object[newN + 1];
/* 1606 */     V[] newValue = (V[])new Object[newN + 1];
/* 1607 */     int i = this.first, prev = -1, newPrev = -1;
/* 1608 */     long[] link = this.link;
/* 1609 */     long[] newLink = new long[newN + 1];
/* 1610 */     this.first = -1;
/* 1611 */     for (int j = this.size; j-- != 0; ) {
/* 1612 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1613 */         pos = newN;
/*      */       } else {
/* 1615 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1616 */         while (newKey[pos] != null)
/* 1617 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1619 */       newKey[pos] = key[i];
/* 1620 */       newValue[pos] = value[i];
/* 1621 */       if (prev != -1) {
/* 1622 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1623 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1624 */         newPrev = pos;
/*      */       } else {
/* 1626 */         newPrev = this.first = pos;
/*      */         
/* 1628 */         newLink[pos] = -1L;
/*      */       } 
/* 1630 */       int t = i;
/* 1631 */       i = (int)link[i];
/* 1632 */       prev = t;
/*      */     } 
/* 1634 */     this.link = newLink;
/* 1635 */     this.last = newPrev;
/* 1636 */     if (newPrev != -1)
/*      */     {
/* 1638 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1639 */     this.n = newN;
/* 1640 */     this.mask = mask;
/* 1641 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1642 */     this.key = newKey;
/* 1643 */     this.value = newValue;
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
/*      */   public Object2ReferenceLinkedOpenCustomHashMap<K, V> clone() {
/*      */     Object2ReferenceLinkedOpenCustomHashMap<K, V> c;
/*      */     try {
/* 1660 */       c = (Object2ReferenceLinkedOpenCustomHashMap<K, V>)super.clone();
/* 1661 */     } catch (CloneNotSupportedException cantHappen) {
/* 1662 */       throw new InternalError();
/*      */     } 
/* 1664 */     c.keys = null;
/* 1665 */     c.values = null;
/* 1666 */     c.entries = null;
/* 1667 */     c.containsNullKey = this.containsNullKey;
/* 1668 */     c.key = (K[])this.key.clone();
/* 1669 */     c.value = (V[])this.value.clone();
/* 1670 */     c.link = (long[])this.link.clone();
/* 1671 */     c.strategy = this.strategy;
/* 1672 */     return c;
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
/* 1685 */     int h = 0;
/* 1686 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1687 */       while (this.key[i] == null)
/* 1688 */         i++; 
/* 1689 */       if (this != this.key[i])
/* 1690 */         t = this.strategy.hashCode(this.key[i]); 
/* 1691 */       if (this != this.value[i])
/* 1692 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1693 */       h += t;
/* 1694 */       i++;
/*      */     } 
/*      */     
/* 1697 */     if (this.containsNullKey)
/* 1698 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1699 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1702 */     K[] key = this.key;
/* 1703 */     V[] value = this.value;
/* 1704 */     MapIterator i = new MapIterator();
/* 1705 */     s.defaultWriteObject();
/* 1706 */     for (int j = this.size; j-- != 0; ) {
/* 1707 */       int e = i.nextEntry();
/* 1708 */       s.writeObject(key[e]);
/* 1709 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1714 */     s.defaultReadObject();
/* 1715 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1716 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1717 */     this.mask = this.n - 1;
/* 1718 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1719 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1720 */     long[] link = this.link = new long[this.n + 1];
/* 1721 */     int prev = -1;
/* 1722 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1725 */     for (int i = this.size; i-- != 0; ) {
/* 1726 */       int pos; K k = (K)s.readObject();
/* 1727 */       V v = (V)s.readObject();
/* 1728 */       if (this.strategy.equals(k, null)) {
/* 1729 */         pos = this.n;
/* 1730 */         this.containsNullKey = true;
/*      */       } else {
/* 1732 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1733 */         while (key[pos] != null)
/* 1734 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1736 */       key[pos] = k;
/* 1737 */       value[pos] = v;
/* 1738 */       if (this.first != -1) {
/* 1739 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1740 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1741 */         prev = pos; continue;
/*      */       } 
/* 1743 */       prev = this.first = pos;
/*      */       
/* 1745 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1748 */     this.last = prev;
/* 1749 */     if (prev != -1)
/*      */     {
/* 1751 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */