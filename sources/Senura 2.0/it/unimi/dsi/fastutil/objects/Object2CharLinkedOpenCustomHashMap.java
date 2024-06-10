/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharListIterator;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.ToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2CharLinkedOpenCustomHashMap<K>
/*      */   extends AbstractObject2CharSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient char[] value;
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
/*      */   protected transient Object2CharSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharLinkedOpenCustomHashMap(int expected, float f, Hash.Strategy<? super K> strategy) {
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
/*  167 */     this.value = new char[this.n + 1];
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
/*      */   public Object2CharLinkedOpenCustomHashMap(int expected, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharLinkedOpenCustomHashMap(Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharLinkedOpenCustomHashMap(Map<? extends K, ? extends Character> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharLinkedOpenCustomHashMap(Map<? extends K, ? extends Character> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharLinkedOpenCustomHashMap(Object2CharMap<K> m, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharLinkedOpenCustomHashMap(Object2CharMap<K> m, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharLinkedOpenCustomHashMap(K[] k, char[] v, float f, Hash.Strategy<? super K> strategy) {
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
/*      */   public Object2CharLinkedOpenCustomHashMap(K[] k, char[] v, Hash.Strategy<? super K> strategy) {
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
/*      */   private char removeEntry(int pos) {
/*  309 */     char oldValue = this.value[pos];
/*  310 */     this.size--;
/*  311 */     fixPointers(pos);
/*  312 */     shiftKeys(pos);
/*  313 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  314 */       rehash(this.n / 2); 
/*  315 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  318 */     this.containsNullKey = false;
/*  319 */     this.key[this.n] = null;
/*  320 */     char oldValue = this.value[this.n];
/*  321 */     this.size--;
/*  322 */     fixPointers(this.n);
/*  323 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  324 */       rehash(this.n / 2); 
/*  325 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Character> m) {
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
/*      */   private void insert(int pos, K k, char v) {
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
/*      */   public char put(K k, char v) {
/*  377 */     int pos = find(k);
/*  378 */     if (pos < 0) {
/*  379 */       insert(-pos - 1, k, v);
/*  380 */       return this.defRetValue;
/*      */     } 
/*  382 */     char oldValue = this.value[pos];
/*  383 */     this.value[pos] = v;
/*  384 */     return oldValue;
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
/*  397 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  399 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  401 */         if ((curr = key[pos]) == null) {
/*  402 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  405 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  406 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  408 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  410 */       key[last] = curr;
/*  411 */       this.value[last] = this.value[pos];
/*  412 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char removeChar(Object k) {
/*  418 */     if (this.strategy.equals(k, null)) {
/*  419 */       if (this.containsNullKey)
/*  420 */         return removeNullEntry(); 
/*  421 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  424 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  427 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  428 */       return this.defRetValue; 
/*  429 */     if (this.strategy.equals(k, curr))
/*  430 */       return removeEntry(pos); 
/*      */     while (true) {
/*  432 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  433 */         return this.defRetValue; 
/*  434 */       if (this.strategy.equals(k, curr))
/*  435 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private char setValue(int pos, char v) {
/*  439 */     char oldValue = this.value[pos];
/*  440 */     this.value[pos] = v;
/*  441 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeFirstChar() {
/*  452 */     if (this.size == 0)
/*  453 */       throw new NoSuchElementException(); 
/*  454 */     int pos = this.first;
/*      */     
/*  456 */     this.first = (int)this.link[pos];
/*  457 */     if (0 <= this.first)
/*      */     {
/*  459 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  461 */     this.size--;
/*  462 */     char v = this.value[pos];
/*  463 */     if (pos == this.n) {
/*  464 */       this.containsNullKey = false;
/*  465 */       this.key[this.n] = null;
/*      */     } else {
/*  467 */       shiftKeys(pos);
/*  468 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  469 */       rehash(this.n / 2); 
/*  470 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeLastChar() {
/*  480 */     if (this.size == 0)
/*  481 */       throw new NoSuchElementException(); 
/*  482 */     int pos = this.last;
/*      */     
/*  484 */     this.last = (int)(this.link[pos] >>> 32L);
/*  485 */     if (0 <= this.last)
/*      */     {
/*  487 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  489 */     this.size--;
/*  490 */     char v = this.value[pos];
/*  491 */     if (pos == this.n) {
/*  492 */       this.containsNullKey = false;
/*  493 */       this.key[this.n] = null;
/*      */     } else {
/*  495 */       shiftKeys(pos);
/*  496 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  497 */       rehash(this.n / 2); 
/*  498 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  501 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  503 */     if (this.last == i) {
/*  504 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  506 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  508 */       long linki = this.link[i];
/*  509 */       int prev = (int)(linki >>> 32L);
/*  510 */       int next = (int)linki;
/*  511 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  512 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  514 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  515 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  516 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  519 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  521 */     if (this.first == i) {
/*  522 */       this.first = (int)this.link[i];
/*      */       
/*  524 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  526 */       long linki = this.link[i];
/*  527 */       int prev = (int)(linki >>> 32L);
/*  528 */       int next = (int)linki;
/*  529 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  530 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  532 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  533 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  534 */     this.last = i;
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
/*      */   public char getAndMoveToFirst(K k) {
/*  546 */     if (this.strategy.equals(k, null)) {
/*  547 */       if (this.containsNullKey) {
/*  548 */         moveIndexToFirst(this.n);
/*  549 */         return this.value[this.n];
/*      */       } 
/*  551 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  554 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  557 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  558 */       return this.defRetValue; 
/*  559 */     if (this.strategy.equals(k, curr)) {
/*  560 */       moveIndexToFirst(pos);
/*  561 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  565 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  566 */         return this.defRetValue; 
/*  567 */       if (this.strategy.equals(k, curr)) {
/*  568 */         moveIndexToFirst(pos);
/*  569 */         return this.value[pos];
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
/*      */   public char getAndMoveToLast(K k) {
/*  583 */     if (this.strategy.equals(k, null)) {
/*  584 */       if (this.containsNullKey) {
/*  585 */         moveIndexToLast(this.n);
/*  586 */         return this.value[this.n];
/*      */       } 
/*  588 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  591 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  594 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  595 */       return this.defRetValue; 
/*  596 */     if (this.strategy.equals(k, curr)) {
/*  597 */       moveIndexToLast(pos);
/*  598 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  602 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  603 */         return this.defRetValue; 
/*  604 */       if (this.strategy.equals(k, curr)) {
/*  605 */         moveIndexToLast(pos);
/*  606 */         return this.value[pos];
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
/*      */   public char putAndMoveToFirst(K k, char v) {
/*      */     int pos;
/*  623 */     if (this.strategy.equals(k, null)) {
/*  624 */       if (this.containsNullKey) {
/*  625 */         moveIndexToFirst(this.n);
/*  626 */         return setValue(this.n, v);
/*      */       } 
/*  628 */       this.containsNullKey = true;
/*  629 */       pos = this.n;
/*      */     } else {
/*      */       
/*  632 */       K[] key = this.key;
/*      */       K curr;
/*  634 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  635 */         if (this.strategy.equals(curr, k)) {
/*  636 */           moveIndexToFirst(pos);
/*  637 */           return setValue(pos, v);
/*      */         } 
/*  639 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  640 */           if (this.strategy.equals(curr, k)) {
/*  641 */             moveIndexToFirst(pos);
/*  642 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  646 */     }  this.key[pos] = k;
/*  647 */     this.value[pos] = v;
/*  648 */     if (this.size == 0) {
/*  649 */       this.first = this.last = pos;
/*      */       
/*  651 */       this.link[pos] = -1L;
/*      */     } else {
/*  653 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  654 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  655 */       this.first = pos;
/*      */     } 
/*  657 */     if (this.size++ >= this.maxFill) {
/*  658 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  661 */     return this.defRetValue;
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
/*      */   public char putAndMoveToLast(K k, char v) {
/*      */     int pos;
/*  676 */     if (this.strategy.equals(k, null)) {
/*  677 */       if (this.containsNullKey) {
/*  678 */         moveIndexToLast(this.n);
/*  679 */         return setValue(this.n, v);
/*      */       } 
/*  681 */       this.containsNullKey = true;
/*  682 */       pos = this.n;
/*      */     } else {
/*      */       
/*  685 */       K[] key = this.key;
/*      */       K curr;
/*  687 */       if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != null) {
/*  688 */         if (this.strategy.equals(curr, k)) {
/*  689 */           moveIndexToLast(pos);
/*  690 */           return setValue(pos, v);
/*      */         } 
/*  692 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  693 */           if (this.strategy.equals(curr, k)) {
/*  694 */             moveIndexToLast(pos);
/*  695 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  699 */     }  this.key[pos] = k;
/*  700 */     this.value[pos] = v;
/*  701 */     if (this.size == 0) {
/*  702 */       this.first = this.last = pos;
/*      */       
/*  704 */       this.link[pos] = -1L;
/*      */     } else {
/*  706 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  707 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  708 */       this.last = pos;
/*      */     } 
/*  710 */     if (this.size++ >= this.maxFill) {
/*  711 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  714 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(Object k) {
/*  719 */     if (this.strategy.equals(k, null)) {
/*  720 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  722 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  725 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  726 */       return this.defRetValue; 
/*  727 */     if (this.strategy.equals(k, curr)) {
/*  728 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  731 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  732 */         return this.defRetValue; 
/*  733 */       if (this.strategy.equals(k, curr)) {
/*  734 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  740 */     if (this.strategy.equals(k, null)) {
/*  741 */       return this.containsNullKey;
/*      */     }
/*  743 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  746 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  747 */       return false; 
/*  748 */     if (this.strategy.equals(k, curr)) {
/*  749 */       return true;
/*      */     }
/*      */     while (true) {
/*  752 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  753 */         return false; 
/*  754 */       if (this.strategy.equals(k, curr))
/*  755 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  760 */     char[] value = this.value;
/*  761 */     K[] key = this.key;
/*  762 */     if (this.containsNullKey && value[this.n] == v)
/*  763 */       return true; 
/*  764 */     for (int i = this.n; i-- != 0;) {
/*  765 */       if (key[i] != null && value[i] == v)
/*  766 */         return true; 
/*  767 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(Object k, char defaultValue) {
/*  773 */     if (this.strategy.equals(k, null)) {
/*  774 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  776 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  779 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  780 */       return defaultValue; 
/*  781 */     if (this.strategy.equals(k, curr)) {
/*  782 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  785 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  786 */         return defaultValue; 
/*  787 */       if (this.strategy.equals(k, curr)) {
/*  788 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(K k, char v) {
/*  794 */     int pos = find(k);
/*  795 */     if (pos >= 0)
/*  796 */       return this.value[pos]; 
/*  797 */     insert(-pos - 1, k, v);
/*  798 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, char v) {
/*  804 */     if (this.strategy.equals(k, null)) {
/*  805 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  806 */         removeNullEntry();
/*  807 */         return true;
/*      */       } 
/*  809 */       return false;
/*      */     } 
/*      */     
/*  812 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  815 */     if ((curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == null)
/*  816 */       return false; 
/*  817 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  818 */       removeEntry(pos);
/*  819 */       return true;
/*      */     } 
/*      */     while (true) {
/*  822 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  823 */         return false; 
/*  824 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  825 */         removeEntry(pos);
/*  826 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, char oldValue, char v) {
/*  833 */     int pos = find(k);
/*  834 */     if (pos < 0 || oldValue != this.value[pos])
/*  835 */       return false; 
/*  836 */     this.value[pos] = v;
/*  837 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(K k, char v) {
/*  842 */     int pos = find(k);
/*  843 */     if (pos < 0)
/*  844 */       return this.defRetValue; 
/*  845 */     char oldValue = this.value[pos];
/*  846 */     this.value[pos] = v;
/*  847 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeCharIfAbsent(K k, ToIntFunction<? super K> mappingFunction) {
/*  852 */     Objects.requireNonNull(mappingFunction);
/*  853 */     int pos = find(k);
/*  854 */     if (pos >= 0)
/*  855 */       return this.value[pos]; 
/*  856 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  857 */     insert(-pos - 1, k, newValue);
/*  858 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeCharIfPresent(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  864 */     Objects.requireNonNull(remappingFunction);
/*  865 */     int pos = find(k);
/*  866 */     if (pos < 0)
/*  867 */       return this.defRetValue; 
/*  868 */     Character newValue = remappingFunction.apply(k, Character.valueOf(this.value[pos]));
/*  869 */     if (newValue == null) {
/*  870 */       if (this.strategy.equals(k, null)) {
/*  871 */         removeNullEntry();
/*      */       } else {
/*  873 */         removeEntry(pos);
/*  874 */       }  return this.defRetValue;
/*      */     } 
/*  876 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeChar(K k, BiFunction<? super K, ? super Character, ? extends Character> remappingFunction) {
/*  882 */     Objects.requireNonNull(remappingFunction);
/*  883 */     int pos = find(k);
/*  884 */     Character newValue = remappingFunction.apply(k, (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  885 */     if (newValue == null) {
/*  886 */       if (pos >= 0)
/*  887 */         if (this.strategy.equals(k, null)) {
/*  888 */           removeNullEntry();
/*      */         } else {
/*  890 */           removeEntry(pos);
/*      */         }  
/*  892 */       return this.defRetValue;
/*      */     } 
/*  894 */     char newVal = newValue.charValue();
/*  895 */     if (pos < 0) {
/*  896 */       insert(-pos - 1, k, newVal);
/*  897 */       return newVal;
/*      */     } 
/*  899 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char mergeChar(K k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  905 */     Objects.requireNonNull(remappingFunction);
/*  906 */     int pos = find(k);
/*  907 */     if (pos < 0) {
/*  908 */       insert(-pos - 1, k, v);
/*  909 */       return v;
/*      */     } 
/*  911 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  912 */     if (newValue == null) {
/*  913 */       if (this.strategy.equals(k, null)) {
/*  914 */         removeNullEntry();
/*      */       } else {
/*  916 */         removeEntry(pos);
/*  917 */       }  return this.defRetValue;
/*      */     } 
/*  919 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  930 */     if (this.size == 0)
/*      */       return; 
/*  932 */     this.size = 0;
/*  933 */     this.containsNullKey = false;
/*  934 */     Arrays.fill((Object[])this.key, (Object)null);
/*  935 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  939 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  943 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2CharMap.Entry<K>, Map.Entry<K, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  955 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  961 */       return Object2CharLinkedOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  965 */       return Object2CharLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  969 */       char oldValue = Object2CharLinkedOpenCustomHashMap.this.value[this.index];
/*  970 */       Object2CharLinkedOpenCustomHashMap.this.value[this.index] = v;
/*  971 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  981 */       return Character.valueOf(Object2CharLinkedOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  991 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  996 */       if (!(o instanceof Map.Entry))
/*  997 */         return false; 
/*  998 */       Map.Entry<K, Character> e = (Map.Entry<K, Character>)o;
/*  999 */       return (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(Object2CharLinkedOpenCustomHashMap.this.key[this.index], e.getKey()) && Object2CharLinkedOpenCustomHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1003 */       return Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(Object2CharLinkedOpenCustomHashMap.this.key[this.index]) ^ Object2CharLinkedOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1007 */       return (new StringBuilder()).append(Object2CharLinkedOpenCustomHashMap.this.key[this.index]).append("=>").append(Object2CharLinkedOpenCustomHashMap.this.value[this.index]).toString();
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
/* 1018 */     if (this.size == 0) {
/* 1019 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1022 */     if (this.first == i) {
/* 1023 */       this.first = (int)this.link[i];
/* 1024 */       if (0 <= this.first)
/*      */       {
/* 1026 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1030 */     if (this.last == i) {
/* 1031 */       this.last = (int)(this.link[i] >>> 32L);
/* 1032 */       if (0 <= this.last)
/*      */       {
/* 1034 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1038 */     long linki = this.link[i];
/* 1039 */     int prev = (int)(linki >>> 32L);
/* 1040 */     int next = (int)linki;
/* 1041 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1042 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1055 */     if (this.size == 1) {
/* 1056 */       this.first = this.last = d;
/*      */       
/* 1058 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1061 */     if (this.first == s) {
/* 1062 */       this.first = d;
/* 1063 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1064 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1067 */     if (this.last == s) {
/* 1068 */       this.last = d;
/* 1069 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1070 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1073 */     long links = this.link[s];
/* 1074 */     int prev = (int)(links >>> 32L);
/* 1075 */     int next = (int)links;
/* 1076 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1077 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1078 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1087 */     if (this.size == 0)
/* 1088 */       throw new NoSuchElementException(); 
/* 1089 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1098 */     if (this.size == 0)
/* 1099 */       throw new NoSuchElementException(); 
/* 1100 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap<K> tailMap(K from) {
/* 1109 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap<K> headMap(K to) {
/* 1118 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap<K> subMap(K from, K to) {
/* 1127 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1136 */     return null;
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
/* 1151 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1162 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1168 */     int index = -1;
/*      */     protected MapIterator() {
/* 1170 */       this.next = Object2CharLinkedOpenCustomHashMap.this.first;
/* 1171 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1174 */       if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(from, null)) {
/* 1175 */         if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey) {
/* 1176 */           this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[Object2CharLinkedOpenCustomHashMap.this.n];
/* 1177 */           this.prev = Object2CharLinkedOpenCustomHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1180 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1182 */       if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.last], from)) {
/* 1183 */         this.prev = Object2CharLinkedOpenCustomHashMap.this.last;
/* 1184 */         this.index = Object2CharLinkedOpenCustomHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1188 */       int pos = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(from)) & Object2CharLinkedOpenCustomHashMap.this.mask;
/*      */       
/* 1190 */       while (Object2CharLinkedOpenCustomHashMap.this.key[pos] != null) {
/* 1191 */         if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(Object2CharLinkedOpenCustomHashMap.this.key[pos], from)) {
/*      */           
/* 1193 */           this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[pos];
/* 1194 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1197 */         pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask;
/*      */       } 
/* 1199 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1202 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1205 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1208 */       if (this.index >= 0)
/*      */         return; 
/* 1210 */       if (this.prev == -1) {
/* 1211 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1214 */       if (this.next == -1) {
/* 1215 */         this.index = Object2CharLinkedOpenCustomHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1218 */       int pos = Object2CharLinkedOpenCustomHashMap.this.first;
/* 1219 */       this.index = 1;
/* 1220 */       while (pos != this.prev) {
/* 1221 */         pos = (int)Object2CharLinkedOpenCustomHashMap.this.link[pos];
/* 1222 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1226 */       ensureIndexKnown();
/* 1227 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1230 */       ensureIndexKnown();
/* 1231 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1234 */       if (!hasNext())
/* 1235 */         throw new NoSuchElementException(); 
/* 1236 */       this.curr = this.next;
/* 1237 */       this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[this.curr];
/* 1238 */       this.prev = this.curr;
/* 1239 */       if (this.index >= 0)
/* 1240 */         this.index++; 
/* 1241 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1244 */       if (!hasPrevious())
/* 1245 */         throw new NoSuchElementException(); 
/* 1246 */       this.curr = this.prev;
/* 1247 */       this.prev = (int)(Object2CharLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/* 1248 */       this.next = this.curr;
/* 1249 */       if (this.index >= 0)
/* 1250 */         this.index--; 
/* 1251 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1254 */       ensureIndexKnown();
/* 1255 */       if (this.curr == -1)
/* 1256 */         throw new IllegalStateException(); 
/* 1257 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1262 */         this.index--;
/* 1263 */         this.prev = (int)(Object2CharLinkedOpenCustomHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1265 */         this.next = (int)Object2CharLinkedOpenCustomHashMap.this.link[this.curr];
/* 1266 */       }  Object2CharLinkedOpenCustomHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1271 */       if (this.prev == -1) {
/* 1272 */         Object2CharLinkedOpenCustomHashMap.this.first = this.next;
/*      */       } else {
/* 1274 */         Object2CharLinkedOpenCustomHashMap.this.link[this.prev] = Object2CharLinkedOpenCustomHashMap.this.link[this.prev] ^ (Object2CharLinkedOpenCustomHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1275 */       }  if (this.next == -1) {
/* 1276 */         Object2CharLinkedOpenCustomHashMap.this.last = this.prev;
/*      */       } else {
/* 1278 */         Object2CharLinkedOpenCustomHashMap.this.link[this.next] = Object2CharLinkedOpenCustomHashMap.this.link[this.next] ^ (Object2CharLinkedOpenCustomHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1279 */       }  int pos = this.curr;
/* 1280 */       this.curr = -1;
/* 1281 */       if (pos == Object2CharLinkedOpenCustomHashMap.this.n) {
/* 1282 */         Object2CharLinkedOpenCustomHashMap.this.containsNullKey = false;
/* 1283 */         Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1286 */         K[] key = Object2CharLinkedOpenCustomHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1290 */           pos = (last = pos) + 1 & Object2CharLinkedOpenCustomHashMap.this.mask;
/*      */           while (true) {
/* 1292 */             if ((curr = key[pos]) == null) {
/* 1293 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1296 */             int slot = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(curr)) & Object2CharLinkedOpenCustomHashMap.this.mask;
/* 1297 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1299 */             pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask;
/*      */           } 
/* 1301 */           key[last] = curr;
/* 1302 */           Object2CharLinkedOpenCustomHashMap.this.value[last] = Object2CharLinkedOpenCustomHashMap.this.value[pos];
/* 1303 */           if (this.next == pos)
/* 1304 */             this.next = last; 
/* 1305 */           if (this.prev == pos)
/* 1306 */             this.prev = last; 
/* 1307 */           Object2CharLinkedOpenCustomHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1312 */       int i = n;
/* 1313 */       while (i-- != 0 && hasNext())
/* 1314 */         nextEntry(); 
/* 1315 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1318 */       int i = n;
/* 1319 */       while (i-- != 0 && hasPrevious())
/* 1320 */         previousEntry(); 
/* 1321 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2CharMap.Entry<K> ok) {
/* 1324 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2CharMap.Entry<K> ok) {
/* 1327 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2CharMap.Entry<K>> { private Object2CharLinkedOpenCustomHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1335 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1339 */       return this.entry = new Object2CharLinkedOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1343 */       return this.entry = new Object2CharLinkedOpenCustomHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1347 */       super.remove();
/* 1348 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1352 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2CharMap.Entry<K>> { final Object2CharLinkedOpenCustomHashMap<K>.MapEntry entry = new Object2CharLinkedOpenCustomHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1356 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenCustomHashMap<K>.MapEntry next() {
/* 1360 */       this.entry.index = nextEntry();
/* 1361 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2CharLinkedOpenCustomHashMap<K>.MapEntry previous() {
/* 1365 */       this.entry.index = previousEntry();
/* 1366 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2CharMap.Entry<K>> implements Object2CharSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2CharMap.Entry<K>> iterator() {
/* 1374 */       return new Object2CharLinkedOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2CharMap.Entry<K>> comparator() {
/* 1378 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2CharMap.Entry<K>> subSet(Object2CharMap.Entry<K> fromElement, Object2CharMap.Entry<K> toElement) {
/* 1383 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2CharMap.Entry<K>> headSet(Object2CharMap.Entry<K> toElement) {
/* 1387 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2CharMap.Entry<K>> tailSet(Object2CharMap.Entry<K> fromElement) {
/* 1391 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2CharMap.Entry<K> first() {
/* 1395 */       if (Object2CharLinkedOpenCustomHashMap.this.size == 0)
/* 1396 */         throw new NoSuchElementException(); 
/* 1397 */       return new Object2CharLinkedOpenCustomHashMap.MapEntry(Object2CharLinkedOpenCustomHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2CharMap.Entry<K> last() {
/* 1401 */       if (Object2CharLinkedOpenCustomHashMap.this.size == 0)
/* 1402 */         throw new NoSuchElementException(); 
/* 1403 */       return new Object2CharLinkedOpenCustomHashMap.MapEntry(Object2CharLinkedOpenCustomHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1408 */       if (!(o instanceof Map.Entry))
/* 1409 */         return false; 
/* 1410 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1411 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1412 */         return false; 
/* 1413 */       K k = (K)e.getKey();
/* 1414 */       char v = ((Character)e.getValue()).charValue();
/* 1415 */       if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1416 */         return (Object2CharLinkedOpenCustomHashMap.this.containsNullKey && Object2CharLinkedOpenCustomHashMap.this.value[Object2CharLinkedOpenCustomHashMap.this.n] == v);
/*      */       }
/* 1418 */       K[] key = Object2CharLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1421 */       if ((curr = key[pos = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2CharLinkedOpenCustomHashMap.this.mask]) == null)
/* 1422 */         return false; 
/* 1423 */       if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1424 */         return (Object2CharLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1427 */         if ((curr = key[pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask]) == null)
/* 1428 */           return false; 
/* 1429 */         if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, curr)) {
/* 1430 */           return (Object2CharLinkedOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1436 */       if (!(o instanceof Map.Entry))
/* 1437 */         return false; 
/* 1438 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1439 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1440 */         return false; 
/* 1441 */       K k = (K)e.getKey();
/* 1442 */       char v = ((Character)e.getValue()).charValue();
/* 1443 */       if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(k, null)) {
/* 1444 */         if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey && Object2CharLinkedOpenCustomHashMap.this.value[Object2CharLinkedOpenCustomHashMap.this.n] == v) {
/* 1445 */           Object2CharLinkedOpenCustomHashMap.this.removeNullEntry();
/* 1446 */           return true;
/*      */         } 
/* 1448 */         return false;
/*      */       } 
/*      */       
/* 1451 */       K[] key = Object2CharLinkedOpenCustomHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1454 */       if ((curr = key[pos = HashCommon.mix(Object2CharLinkedOpenCustomHashMap.this.strategy.hashCode(k)) & Object2CharLinkedOpenCustomHashMap.this.mask]) == null)
/* 1455 */         return false; 
/* 1456 */       if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(curr, k)) {
/* 1457 */         if (Object2CharLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1458 */           Object2CharLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1459 */           return true;
/*      */         } 
/* 1461 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1464 */         if ((curr = key[pos = pos + 1 & Object2CharLinkedOpenCustomHashMap.this.mask]) == null)
/* 1465 */           return false; 
/* 1466 */         if (Object2CharLinkedOpenCustomHashMap.this.strategy.equals(curr, k) && 
/* 1467 */           Object2CharLinkedOpenCustomHashMap.this.value[pos] == v) {
/* 1468 */           Object2CharLinkedOpenCustomHashMap.this.removeEntry(pos);
/* 1469 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1476 */       return Object2CharLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1480 */       Object2CharLinkedOpenCustomHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2CharMap.Entry<K>> iterator(Object2CharMap.Entry<K> from) {
/* 1495 */       return new Object2CharLinkedOpenCustomHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2CharMap.Entry<K>> fastIterator() {
/* 1506 */       return new Object2CharLinkedOpenCustomHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2CharMap.Entry<K>> fastIterator(Object2CharMap.Entry<K> from) {
/* 1521 */       return new Object2CharLinkedOpenCustomHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/* 1526 */       for (int i = Object2CharLinkedOpenCustomHashMap.this.size, next = Object2CharLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1527 */         int curr = next;
/* 1528 */         next = (int)Object2CharLinkedOpenCustomHashMap.this.link[curr];
/* 1529 */         consumer.accept(new AbstractObject2CharMap.BasicEntry<>(Object2CharLinkedOpenCustomHashMap.this.key[curr], Object2CharLinkedOpenCustomHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2CharMap.Entry<K>> consumer) {
/* 1535 */       AbstractObject2CharMap.BasicEntry<K> entry = new AbstractObject2CharMap.BasicEntry<>();
/* 1536 */       for (int i = Object2CharLinkedOpenCustomHashMap.this.size, next = Object2CharLinkedOpenCustomHashMap.this.first; i-- != 0; ) {
/* 1537 */         int curr = next;
/* 1538 */         next = (int)Object2CharLinkedOpenCustomHashMap.this.link[curr];
/* 1539 */         entry.key = Object2CharLinkedOpenCustomHashMap.this.key[curr];
/* 1540 */         entry.value = Object2CharLinkedOpenCustomHashMap.this.value[curr];
/* 1541 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2CharSortedMap.FastSortedEntrySet<K> object2CharEntrySet() {
/* 1547 */     if (this.entries == null)
/* 1548 */       this.entries = new MapEntrySet(); 
/* 1549 */     return this.entries;
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
/* 1562 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1566 */       return Object2CharLinkedOpenCustomHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1573 */       return Object2CharLinkedOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1579 */       return new Object2CharLinkedOpenCustomHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1583 */       return new Object2CharLinkedOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1588 */       if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey)
/* 1589 */         consumer.accept(Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.n]); 
/* 1590 */       for (int pos = Object2CharLinkedOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1591 */         K k = Object2CharLinkedOpenCustomHashMap.this.key[pos];
/* 1592 */         if (k != null)
/* 1593 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1598 */       return Object2CharLinkedOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1602 */       return Object2CharLinkedOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1606 */       int oldSize = Object2CharLinkedOpenCustomHashMap.this.size;
/* 1607 */       Object2CharLinkedOpenCustomHashMap.this.removeChar(k);
/* 1608 */       return (Object2CharLinkedOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1612 */       Object2CharLinkedOpenCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1616 */       if (Object2CharLinkedOpenCustomHashMap.this.size == 0)
/* 1617 */         throw new NoSuchElementException(); 
/* 1618 */       return Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1622 */       if (Object2CharLinkedOpenCustomHashMap.this.size == 0)
/* 1623 */         throw new NoSuchElementException(); 
/* 1624 */       return Object2CharLinkedOpenCustomHashMap.this.key[Object2CharLinkedOpenCustomHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1628 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1632 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1636 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1640 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1645 */     if (this.keys == null)
/* 1646 */       this.keys = new KeySet(); 
/* 1647 */     return this.keys;
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
/*      */     implements CharListIterator
/*      */   {
/*      */     public char previousChar() {
/* 1661 */       return Object2CharLinkedOpenCustomHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1668 */       return Object2CharLinkedOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1673 */     if (this.values == null)
/* 1674 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1677 */             return (CharIterator)new Object2CharLinkedOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1681 */             return Object2CharLinkedOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1685 */             return Object2CharLinkedOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1689 */             Object2CharLinkedOpenCustomHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1694 */             if (Object2CharLinkedOpenCustomHashMap.this.containsNullKey)
/* 1695 */               consumer.accept(Object2CharLinkedOpenCustomHashMap.this.value[Object2CharLinkedOpenCustomHashMap.this.n]); 
/* 1696 */             for (int pos = Object2CharLinkedOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1697 */               if (Object2CharLinkedOpenCustomHashMap.this.key[pos] != null)
/* 1698 */                 consumer.accept(Object2CharLinkedOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1701 */     return this.values;
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
/* 1718 */     return trim(this.size);
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
/* 1742 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1743 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1744 */       return true; 
/*      */     try {
/* 1746 */       rehash(l);
/* 1747 */     } catch (OutOfMemoryError cantDoIt) {
/* 1748 */       return false;
/*      */     } 
/* 1750 */     return true;
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
/* 1766 */     K[] key = this.key;
/* 1767 */     char[] value = this.value;
/* 1768 */     int mask = newN - 1;
/* 1769 */     K[] newKey = (K[])new Object[newN + 1];
/* 1770 */     char[] newValue = new char[newN + 1];
/* 1771 */     int i = this.first, prev = -1, newPrev = -1;
/* 1772 */     long[] link = this.link;
/* 1773 */     long[] newLink = new long[newN + 1];
/* 1774 */     this.first = -1;
/* 1775 */     for (int j = this.size; j-- != 0; ) {
/* 1776 */       int pos; if (this.strategy.equals(key[i], null)) {
/* 1777 */         pos = newN;
/*      */       } else {
/* 1779 */         pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask;
/* 1780 */         while (newKey[pos] != null)
/* 1781 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1783 */       newKey[pos] = key[i];
/* 1784 */       newValue[pos] = value[i];
/* 1785 */       if (prev != -1) {
/* 1786 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1787 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1788 */         newPrev = pos;
/*      */       } else {
/* 1790 */         newPrev = this.first = pos;
/*      */         
/* 1792 */         newLink[pos] = -1L;
/*      */       } 
/* 1794 */       int t = i;
/* 1795 */       i = (int)link[i];
/* 1796 */       prev = t;
/*      */     } 
/* 1798 */     this.link = newLink;
/* 1799 */     this.last = newPrev;
/* 1800 */     if (newPrev != -1)
/*      */     {
/* 1802 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1803 */     this.n = newN;
/* 1804 */     this.mask = mask;
/* 1805 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1806 */     this.key = newKey;
/* 1807 */     this.value = newValue;
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
/*      */   public Object2CharLinkedOpenCustomHashMap<K> clone() {
/*      */     Object2CharLinkedOpenCustomHashMap<K> c;
/*      */     try {
/* 1824 */       c = (Object2CharLinkedOpenCustomHashMap<K>)super.clone();
/* 1825 */     } catch (CloneNotSupportedException cantHappen) {
/* 1826 */       throw new InternalError();
/*      */     } 
/* 1828 */     c.keys = null;
/* 1829 */     c.values = null;
/* 1830 */     c.entries = null;
/* 1831 */     c.containsNullKey = this.containsNullKey;
/* 1832 */     c.key = (K[])this.key.clone();
/* 1833 */     c.value = (char[])this.value.clone();
/* 1834 */     c.link = (long[])this.link.clone();
/* 1835 */     c.strategy = this.strategy;
/* 1836 */     return c;
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
/* 1849 */     int h = 0;
/* 1850 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1851 */       while (this.key[i] == null)
/* 1852 */         i++; 
/* 1853 */       if (this != this.key[i])
/* 1854 */         t = this.strategy.hashCode(this.key[i]); 
/* 1855 */       t ^= this.value[i];
/* 1856 */       h += t;
/* 1857 */       i++;
/*      */     } 
/*      */     
/* 1860 */     if (this.containsNullKey)
/* 1861 */       h += this.value[this.n]; 
/* 1862 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1865 */     K[] key = this.key;
/* 1866 */     char[] value = this.value;
/* 1867 */     MapIterator i = new MapIterator();
/* 1868 */     s.defaultWriteObject();
/* 1869 */     for (int j = this.size; j-- != 0; ) {
/* 1870 */       int e = i.nextEntry();
/* 1871 */       s.writeObject(key[e]);
/* 1872 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1877 */     s.defaultReadObject();
/* 1878 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1879 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1880 */     this.mask = this.n - 1;
/* 1881 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1882 */     char[] value = this.value = new char[this.n + 1];
/* 1883 */     long[] link = this.link = new long[this.n + 1];
/* 1884 */     int prev = -1;
/* 1885 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1888 */     for (int i = this.size; i-- != 0; ) {
/* 1889 */       int pos; K k = (K)s.readObject();
/* 1890 */       char v = s.readChar();
/* 1891 */       if (this.strategy.equals(k, null)) {
/* 1892 */         pos = this.n;
/* 1893 */         this.containsNullKey = true;
/*      */       } else {
/* 1895 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1896 */         while (key[pos] != null)
/* 1897 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1899 */       key[pos] = k;
/* 1900 */       value[pos] = v;
/* 1901 */       if (this.first != -1) {
/* 1902 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1903 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1904 */         prev = pos; continue;
/*      */       } 
/* 1906 */       prev = this.first = pos;
/*      */       
/* 1908 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1911 */     this.last = prev;
/* 1912 */     if (prev != -1)
/*      */     {
/* 1914 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2CharLinkedOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */