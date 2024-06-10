/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ import java.util.function.Predicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2BooleanLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2BooleanSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected Hash.Strategy<? super K> strategy;
/*  110 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  115 */   protected transient int last = -1;
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
/*      */   protected transient Object2BooleanSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
/*  159 */     this.strategy = strategy;
/*  160 */     if (f <= 0.0F || f > 1.0F)
/*  161 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  162 */     if (expected < 0)
/*  163 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  164 */     this.f = f;
/*  165 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  166 */     this.mask = this.n - 1;
/*  167 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  168 */     this.key = (K[])new Object[this.n + 1];
/*  169 */     this.value = new boolean[this.n + 1];
/*  170 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
/*  181 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
/*  192 */     this(16, 0.75F, strategy);
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, float f, Hash.Strategy<? super K> strategy) {
/*  206 */     this(m.size(), f, strategy);
/*  207 */     putAll(m);
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Map<? extends K, ? extends Boolean> m, Hash.Strategy<? super K> strategy) {
/*  220 */     this(m, 0.75F, strategy);
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Object2BooleanMap<K> m, float f, Hash.Strategy<? super K> strategy) {
/*  234 */     this(m.size(), f, strategy);
/*  235 */     putAll(m);
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(Object2BooleanMap<K> m, Hash.Strategy<? super K> strategy) {
/*  247 */     this(m, 0.75F, strategy);
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(K[] k, boolean[] v, float f, Hash.Strategy<? super K> strategy) {
/*  265 */     this(k.length, f, strategy);
/*  266 */     if (k.length != v.length) {
/*  267 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  269 */     for (int i = 0; i < k.length; i++) {
/*  270 */       put(k[i], v[i]);
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap(K[] k, boolean[] v, Hash.Strategy<? super K> strategy) {
/*  286 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Hash.Strategy<? super K> strategy() {
/*  294 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  297 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  300 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  301 */     if (needed > this.n)
/*  302 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  305 */     int needed = (int)Math.min(1073741824L, 
/*  306 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  307 */     if (needed > this.n)
/*  308 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  311 */     boolean oldValue = this.value[pos];
/*  312 */     this.size--;
/*  313 */     fixPointers(pos);
/*  314 */     shiftKeys(pos);
/*  315 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  316 */       rehash(this.n / 2); 
/*  317 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  320 */     this.containsNullKey = false;
/*  321 */     this.key[this.n] = null;
/*  322 */     boolean oldValue = this.value[this.n];
/*  323 */     this.size--;
/*  324 */     fixPointers(this.n);
/*  325 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  326 */       rehash(this.n / 2); 
/*  327 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  331 */     if (this.f <= 0.5D) {
/*  332 */       ensureCapacity(m.size());
/*      */     } else {
/*  334 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  336 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  340 */     if (this.strategy.equals(k, null)) {
/*  341 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  343 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  346 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  347 */       return -(pos + 1); 
/*  348 */     if (this.strategy.equals(k, curr)) {
/*  349 */       return pos;
/*      */     }
/*      */     while (true) {
/*  352 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  353 */         return -(pos + 1); 
/*  354 */       if (this.strategy.equals(k, curr))
/*  355 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, boolean v) {
/*  359 */     if (pos == this.n)
/*  360 */       this.containsNullKey = true; 
/*  361 */     this.key[pos] = k;
/*  362 */     this.value[pos] = v;
/*  363 */     if (this.size == 0) {
/*  364 */       this.first = this.last = pos;
/*      */       
/*  366 */       this.link[pos] = -1L;
/*      */     } else {
/*  368 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  369 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  370 */       this.last = pos;
/*      */     } 
/*  372 */     if (this.size++ >= this.maxFill) {
/*  373 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  379 */     int pos = find(k);
/*  380 */     if (pos < 0) {
/*  381 */       insert(-pos - 1, k, v);
/*  382 */       return this.defRetValue;
/*      */     } 
/*  384 */     boolean oldValue = this.value[pos];
/*  385 */     this.value[pos] = v;
/*  386 */     return oldValue;
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
/*  399 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  401 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  403 */         if ((curr = key[pos]) == null) {
/*  404 */           key[last] = null;
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
/*      */   public boolean removeBoolean(Object k) {
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
/*      */   private boolean setValue(int pos, boolean v) {
/*  441 */     boolean oldValue = this.value[pos];
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
/*      */   public boolean removeFirstBoolean() {
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
/*  464 */     boolean v = this.value[pos];
/*  465 */     if (pos == this.n) {
/*  466 */       this.containsNullKey = false;
/*  467 */       this.key[this.n] = null;
/*      */     } else {
/*  469 */       shiftKeys(pos);
/*  470 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  471 */       rehash(this.n / 2); 
/*  472 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeLastBoolean() {
/*  482 */     if (this.size == 0)
/*  483 */       throw new NoSuchElementException(); 
/*  484 */     int pos = this.last;
/*      */     
/*  486 */     this.last = (int)(this.link[pos] >>> 32L);
/*  487 */     if (0 <= this.last)
/*      */     {
/*  489 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  491 */     this.size--;
/*  492 */     boolean v = this.value[pos];
/*  493 */     if (pos == this.n) {
/*  494 */       this.containsNullKey = false;
/*  495 */       this.key[this.n] = null;
/*      */     } else {
/*  497 */       shiftKeys(pos);
/*  498 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  499 */       rehash(this.n / 2); 
/*  500 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  503 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  505 */     if (this.last == i) {
/*  506 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  508 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  510 */       long linki = this.link[i];
/*  511 */       int prev = (int)(linki >>> 32L);
/*  512 */       int next = (int)linki;
/*  513 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  514 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  516 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  517 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  518 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  521 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  523 */     if (this.first == i) {
/*  524 */       this.first = (int)this.link[i];
/*      */       
/*  526 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  528 */       long linki = this.link[i];
/*  529 */       int prev = (int)(linki >>> 32L);
/*  530 */       int next = (int)linki;
/*  531 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  532 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  534 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  535 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  536 */     this.last = i;
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
/*      */   public boolean getAndMoveToFirst(K k) {
/*  548 */     if (this.strategy.equals(k, null)) {
/*  549 */       if (this.containsNullKey) {
/*  550 */         moveIndexToFirst(this.n);
/*  551 */         return this.value[this.n];
/*      */       } 
/*  553 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  556 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  559 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  560 */       return this.defRetValue; 
/*  561 */     if (this.strategy.equals(k, curr)) {
/*  562 */       moveIndexToFirst(pos);
/*  563 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  567 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  568 */         return this.defRetValue; 
/*  569 */       if (this.strategy.equals(k, curr)) {
/*  570 */         moveIndexToFirst(pos);
/*  571 */         return this.value[pos];
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
/*      */   public boolean getAndMoveToLast(K k) {
/*  585 */     if (this.strategy.equals(k, null)) {
/*  586 */       if (this.containsNullKey) {
/*  587 */         moveIndexToLast(this.n);
/*  588 */         return this.value[this.n];
/*      */       } 
/*  590 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  593 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  596 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  597 */       return this.defRetValue; 
/*  598 */     if (this.strategy.equals(k, curr)) {
/*  599 */       moveIndexToLast(pos);
/*  600 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  604 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  605 */         return this.defRetValue; 
/*  606 */       if (this.strategy.equals(k, curr)) {
/*  607 */         moveIndexToLast(pos);
/*  608 */         return this.value[pos];
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
/*      */   public boolean putAndMoveToFirst(K k, boolean v) {
/*      */     int pos;
/*  625 */     if (this.strategy.equals(k, null)) {
/*  626 */       if (this.containsNullKey) {
/*  627 */         moveIndexToFirst(this.n);
/*  628 */         return setValue(this.n, v);
/*      */       } 
/*  630 */       this.containsNullKey = true;
/*  631 */       pos = this.n;
/*      */     } else {
/*      */       
/*  634 */       K[] key = this.key;
/*      */       K curr;
/*  636 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  637 */         if (this.strategy.equals(curr, k)) {
/*  638 */           moveIndexToFirst(pos);
/*  639 */           return setValue(pos, v);
/*      */         } 
/*  641 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  642 */           if (this.strategy.equals(curr, k)) {
/*  643 */             moveIndexToFirst(pos);
/*  644 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  648 */     }  this.key[pos] = k;
/*  649 */     this.value[pos] = v;
/*  650 */     if (this.size == 0) {
/*  651 */       this.first = this.last = pos;
/*      */       
/*  653 */       this.link[pos] = -1L;
/*      */     } else {
/*  655 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  656 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  657 */       this.first = pos;
/*      */     } 
/*  659 */     if (this.size++ >= this.maxFill) {
/*  660 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  663 */     return this.defRetValue;
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
/*      */   public boolean putAndMoveToLast(K k, boolean v) {
/*      */     int pos;
/*  678 */     if (this.strategy.equals(k, null)) {
/*  679 */       if (this.containsNullKey) {
/*  680 */         moveIndexToLast(this.n);
/*  681 */         return setValue(this.n, v);
/*      */       } 
/*  683 */       this.containsNullKey = true;
/*  684 */       pos = this.n;
/*      */     } else {
/*      */       
/*  687 */       K[] key = this.key;
/*      */       K curr;
/*  689 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  690 */         if (this.strategy.equals(curr, k)) {
/*  691 */           moveIndexToLast(pos);
/*  692 */           return setValue(pos, v);
/*      */         } 
/*  694 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  695 */           if (this.strategy.equals(curr, k)) {
/*  696 */             moveIndexToLast(pos);
/*  697 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  701 */     }  this.key[pos] = k;
/*  702 */     this.value[pos] = v;
/*  703 */     if (this.size == 0) {
/*  704 */       this.first = this.last = pos;
/*      */       
/*  706 */       this.link[pos] = -1L;
/*      */     } else {
/*  708 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  709 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  710 */       this.last = pos;
/*      */     } 
/*  712 */     if (this.size++ >= this.maxFill) {
/*  713 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  716 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  721 */     if (this.strategy.equals(k, null)) {
/*  722 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  724 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  727 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  728 */       return this.defRetValue; 
/*  729 */     if (this.strategy.equals(k, curr)) {
/*  730 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  733 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  734 */         return this.defRetValue; 
/*  735 */       if (this.strategy.equals(k, curr)) {
/*  736 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  742 */     if (this.strategy.equals(k, null)) {
/*  743 */       return this.containsNullKey;
/*      */     }
/*  745 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  748 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  749 */       return false; 
/*  750 */     if (this.strategy.equals(k, curr)) {
/*  751 */       return true;
/*      */     }
/*      */     while (true) {
/*  754 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  755 */         return false; 
/*  756 */       if (this.strategy.equals(k, curr))
/*  757 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  762 */     boolean[] value = this.value;
/*  763 */     K[] key = this.key;
/*  764 */     if (this.containsNullKey && value[this.n] == v)
/*  765 */       return true; 
/*  766 */     for (int i = this.n; i-- != 0;) {
/*  767 */       if (key[i] != null && value[i] == v)
/*  768 */         return true; 
/*  769 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  775 */     if (this.strategy.equals(k, null)) {
/*  776 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  778 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  781 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  782 */       return defaultValue; 
/*  783 */     if (this.strategy.equals(k, curr)) {
/*  784 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  787 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  788 */         return defaultValue; 
/*  789 */       if (this.strategy.equals(k, curr)) {
/*  790 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  796 */     int pos = find(k);
/*  797 */     if (pos >= 0)
/*  798 */       return this.value[pos]; 
/*  799 */     insert(-pos - 1, k, v);
/*  800 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  806 */     if (this.strategy.equals(k, null)) {
/*  807 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  808 */         removeNullEntry();
/*  809 */         return true;
/*      */       } 
/*  811 */       return false;
/*      */     } 
/*      */     
/*  814 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  817 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  818 */       return false; 
/*  819 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  820 */       removeEntry(pos);
/*  821 */       return true;
/*      */     } 
/*      */     while (true) {
/*  824 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  825 */         return false; 
/*  826 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  827 */         removeEntry(pos);
/*  828 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  835 */     int pos = find(k);
/*  836 */     if (pos < 0 || oldValue != this.value[pos])
/*  837 */       return false; 
/*  838 */     this.value[pos] = v;
/*  839 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  844 */     int pos = find(k);
/*  845 */     if (pos < 0)
/*  846 */       return this.defRetValue; 
/*  847 */     boolean oldValue = this.value[pos];
/*  848 */     this.value[pos] = v;
/*  849 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  854 */     Objects.requireNonNull(mappingFunction);
/*  855 */     int pos = find(k);
/*  856 */     if (pos >= 0)
/*  857 */       return this.value[pos]; 
/*  858 */     boolean newValue = mappingFunction.test(k);
/*  859 */     insert(-pos - 1, k, newValue);
/*  860 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  866 */     Objects.requireNonNull(remappingFunction);
/*  867 */     int pos = find(k);
/*  868 */     if (pos < 0)
/*  869 */       return this.defRetValue; 
/*  870 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  871 */     if (newValue == null) {
/*  872 */       if (this.strategy.equals(k, null)) {
/*  873 */         removeNullEntry();
/*      */       } else {
/*  875 */         removeEntry(pos);
/*  876 */       }  return this.defRetValue;
/*      */     } 
/*  878 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  884 */     Objects.requireNonNull(remappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  887 */     if (newValue == null) {
/*  888 */       if (pos >= 0)
/*  889 */         if (this.strategy.equals(k, null)) {
/*  890 */           removeNullEntry();
/*      */         } else {
/*  892 */           removeEntry(pos);
/*      */         }  
/*  894 */       return this.defRetValue;
/*      */     } 
/*  896 */     boolean newVal = newValue.booleanValue();
/*  897 */     if (pos < 0) {
/*  898 */       insert(-pos - 1, k, newVal);
/*  899 */       return newVal;
/*      */     } 
/*  901 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mergeBoolean(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  907 */     Objects.requireNonNull(remappingFunction);
/*  908 */     int pos = find(k);
/*  909 */     if (pos < 0) {
/*  910 */       insert(-pos - 1, k, v);
/*  911 */       return v;
/*      */     } 
/*  913 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  914 */     if (newValue == null) {
/*  915 */       if (this.strategy.equals(k, null)) {
/*  916 */         removeNullEntry();
/*      */       } else {
/*  918 */         removeEntry(pos);
/*  919 */       }  return this.defRetValue;
/*      */     } 
/*  921 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  932 */     if (this.size == 0)
/*      */       return; 
/*  934 */     this.size = 0;
/*  935 */     this.containsNullKey = false;
/*  936 */     Arrays.fill((Object[])this.key, (Object)null);
/*  937 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  941 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  945 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2BooleanMap.Entry<K>, Map.Entry<K, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  957 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  963 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  967 */       return Object2BooleanLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  971 */       boolean oldValue = Object2BooleanLinkedOpenCustomHashMap.this.value[this.index];
/*  972 */       Object2BooleanLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  973 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  983 */       return Boolean.valueOf(Object2BooleanLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  993 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  998 */       if (!(o instanceof Map.Entry))
/*  999 */         return false; 
/* 1000 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/* 1001 */       return (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(Object2BooleanLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2BooleanLinkedOpenCustomHashMap.this.value[this.index] == ((Boolean)e
/* 1002 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1006 */       return Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(Object2BooleanLinkedOpenCustomHashMap.this.key[this.index]) ^ (Object2BooleanLinkedOpenCustomHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1010 */       return (new StringBuilder()).append(Object2BooleanLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2BooleanLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/* 1021 */     if (this.size == 0) {
/* 1022 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1025 */     if (this.first == i) {
/* 1026 */       this.first = (int)this.link[i];
/* 1027 */       if (0 <= this.first)
/*      */       {
/* 1029 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1033 */     if (this.last == i) {
/* 1034 */       this.last = (int)(this.link[i] >>> 32L);
/* 1035 */       if (0 <= this.last)
/*      */       {
/* 1037 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1041 */     long linki = this.link[i];
/* 1042 */     int prev = (int)(linki >>> 32L);
/* 1043 */     int next = (int)linki;
/* 1044 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1045 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1058 */     if (this.size == 1) {
/* 1059 */       this.first = this.last = d;
/*      */       
/* 1061 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1064 */     if (this.first == s) {
/* 1065 */       this.first = d;
/* 1066 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1067 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1070 */     if (this.last == s) {
/* 1071 */       this.last = d;
/* 1072 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1073 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1076 */     long links = this.link[s];
/* 1077 */     int prev = (int)(links >>> 32L);
/* 1078 */     int next = (int)links;
/* 1079 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1080 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1081 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1090 */     if (this.size == 0)
/* 1091 */       throw new NoSuchElementException(); 
/* 1092 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1101 */     if (this.size == 0)
/* 1102 */       throw new NoSuchElementException(); 
/* 1103 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1112 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> headMap(K to) {
/* 1121 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1130 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1139 */     return null;
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
/* 1154 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1160 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1165 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1171 */     int index = -1;
/*      */     protected MapIterator() {
/* 1173 */       this.next = Object2BooleanLinkedOpenCustomHashMap.this.first;
/* 1174 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1177 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1178 */         if (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1179 */           this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[Object2BooleanLinkedOpenCustomHashMap.this.n];
/* 1180 */           this.prev = Object2BooleanLinkedOpenCustomHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1183 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1185 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.last], from)) {
/* 1186 */         this.prev = Object2BooleanLinkedOpenCustomHashMap.this.last;
/* 1187 */         this.index = Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1191 */       int pos = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1193 */       while (Object2BooleanLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1194 */         if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(Object2BooleanLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1196 */           this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[pos];
/* 1197 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1200 */         pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1202 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1205 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1208 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1211 */       if (this.index >= 0)
/*      */         return; 
/* 1213 */       if (this.prev == -1) {
/* 1214 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1217 */       if (this.next == -1) {
/* 1218 */         this.index = Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1221 */       int pos = Object2BooleanLinkedOpenCustomHashMap.this.first;
/* 1222 */       this.index = 1;
/* 1223 */       while (pos != this.prev) {
/* 1224 */         pos = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[pos];
/* 1225 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1229 */       ensureIndexKnown();
/* 1230 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1233 */       ensureIndexKnown();
/* 1234 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1237 */       if (!hasNext())
/* 1238 */         throw new NoSuchElementException(); 
/* 1239 */       this.curr = this.next;
/* 1240 */       this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr];
/* 1241 */       this.prev = this.curr;
/* 1242 */       if (this.index >= 0)
/* 1243 */         this.index++; 
/* 1244 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1247 */       if (!hasPrevious())
/* 1248 */         throw new NoSuchElementException(); 
/* 1249 */       this.curr = this.prev;
/* 1250 */       this.prev = (int)(Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1251 */       this.next = this.curr;
/* 1252 */       if (this.index >= 0)
/* 1253 */         this.index--; 
/* 1254 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1257 */       ensureIndexKnown();
/* 1258 */       if (this.curr == -1)
/* 1259 */         throw new IllegalStateException(); 
/* 1260 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1265 */         this.index--;
/* 1266 */         this.prev = (int)(Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1268 */         this.next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[this.curr];
/* 1269 */       }  Object2BooleanLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1274 */       if (this.prev == -1) {
/* 1275 */         Object2BooleanLinkedOpenCustomHashMap.this.first = this.next;
/*      */       } else {
/* 1277 */         Object2BooleanLinkedOpenCustomHashMap.this.link[this.prev] = Object2BooleanLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2BooleanLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1278 */       }  if (this.next == -1) {
/* 1279 */         Object2BooleanLinkedOpenCustomHashMap.this.last = this.prev;
/*      */       } else {
/* 1281 */         Object2BooleanLinkedOpenCustomHashMap.this.link[this.next] = Object2BooleanLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2BooleanLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1282 */       }  int pos = this.curr;
/* 1283 */       this.curr = -1;
/* 1284 */       if (pos == Object2BooleanLinkedOpenCustomHashMap.this.n) {
/* 1285 */         Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1286 */         Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1289 */         K[] key = Object2BooleanLinkedOpenCustomHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1293 */           pos = (last = pos) + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1295 */             if ((curr = key[pos]) == null) {
/* 1296 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1299 */             int slot = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/* 1300 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1302 */             pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1304 */           key[last] = curr;
/* 1305 */           Object2BooleanLinkedOpenCustomHashMap.this.value[last] = Object2BooleanLinkedOpenCustomHashMap.this.value[pos];
/* 1306 */           if (this.next == pos)
/* 1307 */             this.next = last; 
/* 1308 */           if (this.prev == pos)
/* 1309 */             this.prev = last; 
/* 1310 */           Object2BooleanLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1315 */       int i = n;
/* 1316 */       while (i-- != 0 && hasNext())
/* 1317 */         nextEntry(); 
/* 1318 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1321 */       int i = n;
/* 1322 */       while (i-- != 0 && hasPrevious())
/* 1323 */         previousEntry(); 
/* 1324 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2BooleanMap.Entry<K> ok) {
/* 1327 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2BooleanMap.Entry<K> ok) {
/* 1330 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> { private Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1338 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1342 */       return this.entry = new Object2BooleanLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1346 */       return this.entry = new Object2BooleanLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1350 */       super.remove();
/* 1351 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1355 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> { final Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2BooleanLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1359 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1363 */       this.entry.index = nextEntry();
/* 1364 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1368 */       this.entry.index = previousEntry();
/* 1369 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2BooleanMap.Entry<K>> implements Object2BooleanSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1377 */       return new Object2BooleanLinkedOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1381 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> fromElement, Object2BooleanMap.Entry<K> toElement) {
/* 1386 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> toElement) {
/* 1390 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> fromElement) {
/* 1394 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> first() {
/* 1398 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0)
/* 1399 */         throw new NoSuchElementException(); 
/* 1400 */       return new Object2BooleanLinkedOpenCustomHashMap.MapEntry(Object2BooleanLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> last() {
/* 1404 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0)
/* 1405 */         throw new NoSuchElementException(); 
/* 1406 */       return new Object2BooleanLinkedOpenCustomHashMap.MapEntry(Object2BooleanLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1411 */       if (!(o instanceof Map.Entry))
/* 1412 */         return false; 
/* 1413 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1414 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1415 */         return false; 
/* 1416 */       K k = (K)e.getKey();
/* 1417 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1418 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1419 */         return (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey && Object2BooleanLinkedOpenCustomHashMap.this.value[Object2BooleanLinkedOpenCustomHashMap.this.n] == v);
/*      */       }
/* 1421 */       K[] key = Object2BooleanLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1424 */       if ((curr = key[pos = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null)
/* 1425 */         return false; 
/* 1426 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1427 */         return (Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1430 */         if ((curr = key[pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null)
/* 1431 */           return false; 
/* 1432 */         if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1433 */           return (Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1439 */       if (!(o instanceof Map.Entry))
/* 1440 */         return false; 
/* 1441 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1442 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1443 */         return false; 
/* 1444 */       K k = (K)e.getKey();
/* 1445 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1446 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1447 */         if (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey && Object2BooleanLinkedOpenCustomHashMap.this.value[Object2BooleanLinkedOpenCustomHashMap.this.n] == v) {
/* 1448 */           Object2BooleanLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1449 */           return true;
/*      */         } 
/* 1451 */         return false;
/*      */       } 
/*      */       
/* 1454 */       K[] key = Object2BooleanLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1457 */       if ((curr = key[pos = HashCommon.mix(Object2BooleanLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null)
/* 1458 */         return false; 
/* 1459 */       if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1460 */         if (Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1461 */           Object2BooleanLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1462 */           return true;
/*      */         } 
/* 1464 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1467 */         if ((curr = key[pos = pos + 1 & Object2BooleanLinkedOpenCustomHashMap.this.mask]) == null)
/* 1468 */           return false; 
/* 1469 */         if (Object2BooleanLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1470 */           Object2BooleanLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1471 */           Object2BooleanLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1472 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1479 */       return Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1483 */       Object2BooleanLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2BooleanMap.Entry<K>> iterator(Object2BooleanMap.Entry<K> from) {
/* 1498 */       return new Object2BooleanLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2BooleanMap.Entry<K>> fastIterator() {
/* 1509 */       return new Object2BooleanLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2BooleanMap.Entry<K>> fastIterator(Object2BooleanMap.Entry<K> from) {
/* 1524 */       return new Object2BooleanLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/* 1529 */       for (int i = Object2BooleanLinkedOpenCustomHashMap.this.size, next = Object2BooleanLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1530 */         int curr = next;
/* 1531 */         next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[curr];
/* 1532 */         consumer.accept(new AbstractObject2BooleanMap.BasicEntry<>(Object2BooleanLinkedOpenCustomHashMap.this.key[curr], Object2BooleanLinkedOpenCustomHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/* 1538 */       AbstractObject2BooleanMap.BasicEntry<K> entry = new AbstractObject2BooleanMap.BasicEntry<>();
/* 1539 */       for (int i = Object2BooleanLinkedOpenCustomHashMap.this.size, next = Object2BooleanLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1540 */         int curr = next;
/* 1541 */         next = (int)Object2BooleanLinkedOpenCustomHashMap.this.link[curr];
/* 1542 */         entry.key = Object2BooleanLinkedOpenCustomHashMap.this.key[curr];
/* 1543 */         entry.value = Object2BooleanLinkedOpenCustomHashMap.this.value[curr];
/* 1544 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap.FastSortedEntrySet<K> object2BooleanEntrySet() {
/* 1550 */     if (this.entries == null)
/* 1551 */       this.entries = new MapEntrySet(); 
/* 1552 */     return this.entries;
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
/* 1565 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1569 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1576 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1582 */       return new Object2BooleanLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1586 */       return new Object2BooleanLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1591 */       if (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey)
/* 1592 */         consumer.accept(Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.n]); 
/* 1593 */       for (int pos = Object2BooleanLinkedOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1594 */         K k = Object2BooleanLinkedOpenCustomHashMap.this.key[pos];
/* 1595 */         if (k != null)
/* 1596 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1601 */       return Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1605 */       return Object2BooleanLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1609 */       int oldSize = Object2BooleanLinkedOpenCustomHashMap.this.size;
/* 1610 */       Object2BooleanLinkedOpenCustomHashMap.this.removeBoolean(k);
/* 1611 */       return (Object2BooleanLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1615 */       Object2BooleanLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1619 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0)
/* 1620 */         throw new NoSuchElementException(); 
/* 1621 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1625 */       if (Object2BooleanLinkedOpenCustomHashMap.this.size == 0)
/* 1626 */         throw new NoSuchElementException(); 
/* 1627 */       return Object2BooleanLinkedOpenCustomHashMap.this.key[Object2BooleanLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1631 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1635 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1639 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1643 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1648 */     if (this.keys == null)
/* 1649 */       this.keys = new KeySet(); 
/* 1650 */     return this.keys;
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
/*      */     implements BooleanListIterator
/*      */   {
/*      */     public boolean previousBoolean() {
/* 1664 */       return Object2BooleanLinkedOpenCustomHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1671 */       return Object2BooleanLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1676 */     if (this.values == null)
/* 1677 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1680 */             return (BooleanIterator)new Object2BooleanLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1684 */             return Object2BooleanLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1688 */             return Object2BooleanLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1692 */             Object2BooleanLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1697 */             if (Object2BooleanLinkedOpenCustomHashMap.this.containsNullKey)
/* 1698 */               consumer.accept(Object2BooleanLinkedOpenCustomHashMap.this.value[Object2BooleanLinkedOpenCustomHashMap.this.n]); 
/* 1699 */             for (int pos = Object2BooleanLinkedOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1700 */               if (Object2BooleanLinkedOpenCustomHashMap.this.key[pos] != null)
/* 1701 */                 consumer.accept(Object2BooleanLinkedOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1704 */     return this.values;
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
/* 1721 */     return trim(this.size);
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
/* 1745 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1746 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1747 */       return true; 
/*      */     try {
/* 1749 */       rehash(l);
/* 1750 */     } catch (OutOfMemoryError cantDoIt) {
/* 1751 */       return false;
/*      */     } 
/* 1753 */     return true;
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
/* 1769 */     K[] key = this.key;
/* 1770 */     boolean[] value = this.value;
/* 1771 */     int mask = newN - 1;
/* 1772 */     K[] newKey = (K[])new Object[newN + 1];
/* 1773 */     boolean[] newValue = new boolean[newN + 1];
/* 1774 */     int i = this.first, prev = -1, newPrev = -1;
/* 1775 */     long[] link = this.link;
/* 1776 */     long[] newLink = new long[newN + 1];
/* 1777 */     this.first = -1;
/* 1778 */     for (int j = this.size; j-- != 0; ) {
/* 1779 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1780 */         pos = newN;
/*      */       } else {
/* 1782 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1783 */         while (newKey[pos] != null)
/* 1784 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1786 */       newKey[pos] = key[i];
/* 1787 */       newValue[pos] = value[i];
/* 1788 */       if (prev != -1) {
/* 1789 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1790 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1791 */         newPrev = pos;
/*      */       } else {
/* 1793 */         newPrev = this.first = pos;
/*      */         
/* 1795 */         newLink[pos] = -1L;
/*      */       } 
/* 1797 */       int t = i;
/* 1798 */       i = (int)link[i];
/* 1799 */       prev = t;
/*      */     } 
/* 1801 */     this.link = newLink;
/* 1802 */     this.last = newPrev;
/* 1803 */     if (newPrev != -1)
/*      */     {
/* 1805 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1806 */     this.n = newN;
/* 1807 */     this.mask = mask;
/* 1808 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1809 */     this.key = newKey;
/* 1810 */     this.value = newValue;
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
/*      */   public Object2BooleanLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2BooleanLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1827 */       c = (Object2BooleanLinkedOpenCustomHashMap<K>)super.clone();
/* 1828 */     } catch (CloneNotSupportedException cantHappen) {
/* 1829 */       throw new InternalError();
/*      */     } 
/* 1831 */     c.keys = null;
/* 1832 */     c.values = null;
/* 1833 */     c.entries = null;
/* 1834 */     c.containsNullKey = this.containsNullKey;
/* 1835 */     c.key = (K[])this.key.clone();
/* 1836 */     c.value = (boolean[])this.value.clone();
/* 1837 */     c.link = (long[])this.link.clone();
/* 1838 */     c.strategy = this.strategy;
/* 1839 */     return c;
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
/* 1852 */     int h = 0;
/* 1853 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1854 */       while (this.key[i] == null)
/* 1855 */         i++; 
/* 1856 */       if (this != this.key[i])
/* 1857 */         t = this.strategy.hashCode(this.key[i]); 
/* 1858 */       t ^= this.value[i] ? 1231 : 1237;
/* 1859 */       h += t;
/* 1860 */       i++;
/*      */     } 
/*      */     
/* 1863 */     if (this.containsNullKey)
/* 1864 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1865 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1868 */     K[] key = this.key;
/* 1869 */     boolean[] value = this.value;
/* 1870 */     MapIterator i = new MapIterator();
/* 1871 */     s.defaultWriteObject();
/* 1872 */     for (int j = this.size; j-- != 0; ) {
/* 1873 */       int e = i.nextEntry();
/* 1874 */       s.writeObject(key[e]);
/* 1875 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1880 */     s.defaultReadObject();
/* 1881 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1882 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1883 */     this.mask = this.n - 1;
/* 1884 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1885 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1886 */     long[] link = this.link = new long[this.n + 1];
/* 1887 */     int prev = -1;
/* 1888 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1891 */     for (int i = this.size; i-- != 0; ) {
/* 1892 */       int pos; K k = (K)s.readObject();
/* 1893 */       boolean v = s.readBoolean();
/* 1894 */       if (this.strategy.equals(k, null)) {
/* 1895 */         pos = this.n;
/* 1896 */         this.containsNullKey = true;
/*      */       } else {
/* 1898 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1899 */         while (key[pos] != null)
/* 1900 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1902 */       key[pos] = k;
/* 1903 */       value[pos] = v;
/* 1904 */       if (this.first != -1) {
/* 1905 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1906 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1907 */         prev = pos; continue;
/*      */       } 
/* 1909 */       prev = this.first = pos;
/*      */       
/* 1911 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1914 */     this.last = prev;
/* 1915 */     if (prev != -1)
/*      */     {
/* 1917 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */