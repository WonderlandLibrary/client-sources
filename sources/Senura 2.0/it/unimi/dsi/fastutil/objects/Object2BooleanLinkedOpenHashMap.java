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
/*      */ public class Object2BooleanLinkedOpenHashMap<K>
/*      */   extends AbstractObject2BooleanSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  108 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   protected transient int last = -1;
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
/*      */   public Object2BooleanLinkedOpenHashMap(int expected, float f) {
/*  154 */     if (f <= 0.0F || f > 1.0F)
/*  155 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  156 */     if (expected < 0)
/*  157 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  158 */     this.f = f;
/*  159 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  160 */     this.mask = this.n - 1;
/*  161 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  162 */     this.key = (K[])new Object[this.n + 1];
/*  163 */     this.value = new boolean[this.n + 1];
/*  164 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenHashMap(int expected) {
/*  173 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenHashMap() {
/*  181 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenHashMap(Map<? extends K, ? extends Boolean> m, float f) {
/*  192 */     this(m.size(), f);
/*  193 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenHashMap(Map<? extends K, ? extends Boolean> m) {
/*  203 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenHashMap(Object2BooleanMap<K> m, float f) {
/*  214 */     this(m.size(), f);
/*  215 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanLinkedOpenHashMap(Object2BooleanMap<K> m) {
/*  225 */     this(m, 0.75F);
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
/*      */   public Object2BooleanLinkedOpenHashMap(K[] k, boolean[] v, float f) {
/*  240 */     this(k.length, f);
/*  241 */     if (k.length != v.length) {
/*  242 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  244 */     for (int i = 0; i < k.length; i++) {
/*  245 */       put(k[i], v[i]);
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
/*      */   public Object2BooleanLinkedOpenHashMap(K[] k, boolean[] v) {
/*  259 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  262 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  265 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  266 */     if (needed > this.n)
/*  267 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  270 */     int needed = (int)Math.min(1073741824L, 
/*  271 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  272 */     if (needed > this.n)
/*  273 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  276 */     boolean oldValue = this.value[pos];
/*  277 */     this.size--;
/*  278 */     fixPointers(pos);
/*  279 */     shiftKeys(pos);
/*  280 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  281 */       rehash(this.n / 2); 
/*  282 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  285 */     this.containsNullKey = false;
/*  286 */     this.key[this.n] = null;
/*  287 */     boolean oldValue = this.value[this.n];
/*  288 */     this.size--;
/*  289 */     fixPointers(this.n);
/*  290 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  291 */       rehash(this.n / 2); 
/*  292 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Boolean> m) {
/*  296 */     if (this.f <= 0.5D) {
/*  297 */       ensureCapacity(m.size());
/*      */     } else {
/*  299 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  301 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  305 */     if (k == null) {
/*  306 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  308 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  311 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  312 */       return -(pos + 1); 
/*  313 */     if (k.equals(curr)) {
/*  314 */       return pos;
/*      */     }
/*      */     while (true) {
/*  317 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  318 */         return -(pos + 1); 
/*  319 */       if (k.equals(curr))
/*  320 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, boolean v) {
/*  324 */     if (pos == this.n)
/*  325 */       this.containsNullKey = true; 
/*  326 */     this.key[pos] = k;
/*  327 */     this.value[pos] = v;
/*  328 */     if (this.size == 0) {
/*  329 */       this.first = this.last = pos;
/*      */       
/*  331 */       this.link[pos] = -1L;
/*      */     } else {
/*  333 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  334 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  335 */       this.last = pos;
/*      */     } 
/*  337 */     if (this.size++ >= this.maxFill) {
/*  338 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean put(K k, boolean v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     boolean oldValue = this.value[pos];
/*  350 */     this.value[pos] = v;
/*  351 */     return oldValue;
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
/*  364 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  366 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  368 */         if ((curr = key[pos]) == null) {
/*  369 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  372 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  373 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  375 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  377 */       key[last] = curr;
/*  378 */       this.value[last] = this.value[pos];
/*  379 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeBoolean(Object k) {
/*  385 */     if (k == null) {
/*  386 */       if (this.containsNullKey)
/*  387 */         return removeNullEntry(); 
/*  388 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  391 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  394 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  395 */       return this.defRetValue; 
/*  396 */     if (k.equals(curr))
/*  397 */       return removeEntry(pos); 
/*      */     while (true) {
/*  399 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  400 */         return this.defRetValue; 
/*  401 */       if (k.equals(curr))
/*  402 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private boolean setValue(int pos, boolean v) {
/*  406 */     boolean oldValue = this.value[pos];
/*  407 */     this.value[pos] = v;
/*  408 */     return oldValue;
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
/*  419 */     if (this.size == 0)
/*  420 */       throw new NoSuchElementException(); 
/*  421 */     int pos = this.first;
/*      */     
/*  423 */     this.first = (int)this.link[pos];
/*  424 */     if (0 <= this.first)
/*      */     {
/*  426 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  428 */     this.size--;
/*  429 */     boolean v = this.value[pos];
/*  430 */     if (pos == this.n) {
/*  431 */       this.containsNullKey = false;
/*  432 */       this.key[this.n] = null;
/*      */     } else {
/*  434 */       shiftKeys(pos);
/*  435 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  436 */       rehash(this.n / 2); 
/*  437 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeLastBoolean() {
/*  447 */     if (this.size == 0)
/*  448 */       throw new NoSuchElementException(); 
/*  449 */     int pos = this.last;
/*      */     
/*  451 */     this.last = (int)(this.link[pos] >>> 32L);
/*  452 */     if (0 <= this.last)
/*      */     {
/*  454 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  456 */     this.size--;
/*  457 */     boolean v = this.value[pos];
/*  458 */     if (pos == this.n) {
/*  459 */       this.containsNullKey = false;
/*  460 */       this.key[this.n] = null;
/*      */     } else {
/*  462 */       shiftKeys(pos);
/*  463 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  464 */       rehash(this.n / 2); 
/*  465 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  468 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  470 */     if (this.last == i) {
/*  471 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  473 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  475 */       long linki = this.link[i];
/*  476 */       int prev = (int)(linki >>> 32L);
/*  477 */       int next = (int)linki;
/*  478 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  479 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  481 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  482 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  483 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  486 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  488 */     if (this.first == i) {
/*  489 */       this.first = (int)this.link[i];
/*      */       
/*  491 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  493 */       long linki = this.link[i];
/*  494 */       int prev = (int)(linki >>> 32L);
/*  495 */       int next = (int)linki;
/*  496 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  497 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  499 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  500 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  501 */     this.last = i;
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
/*  513 */     if (k == null) {
/*  514 */       if (this.containsNullKey) {
/*  515 */         moveIndexToFirst(this.n);
/*  516 */         return this.value[this.n];
/*      */       } 
/*  518 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  521 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  524 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  525 */       return this.defRetValue; 
/*  526 */     if (k.equals(curr)) {
/*  527 */       moveIndexToFirst(pos);
/*  528 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  533 */         return this.defRetValue; 
/*  534 */       if (k.equals(curr)) {
/*  535 */         moveIndexToFirst(pos);
/*  536 */         return this.value[pos];
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
/*  550 */     if (k == null) {
/*  551 */       if (this.containsNullKey) {
/*  552 */         moveIndexToLast(this.n);
/*  553 */         return this.value[this.n];
/*      */       } 
/*  555 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  558 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  561 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  562 */       return this.defRetValue; 
/*  563 */     if (k.equals(curr)) {
/*  564 */       moveIndexToLast(pos);
/*  565 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  569 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  570 */         return this.defRetValue; 
/*  571 */       if (k.equals(curr)) {
/*  572 */         moveIndexToLast(pos);
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
/*      */ 
/*      */   
/*      */   public boolean putAndMoveToFirst(K k, boolean v) {
/*      */     int pos;
/*  590 */     if (k == null) {
/*  591 */       if (this.containsNullKey) {
/*  592 */         moveIndexToFirst(this.n);
/*  593 */         return setValue(this.n, v);
/*      */       } 
/*  595 */       this.containsNullKey = true;
/*  596 */       pos = this.n;
/*      */     } else {
/*      */       
/*  599 */       K[] key = this.key;
/*      */       K curr;
/*  601 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  602 */         if (curr.equals(k)) {
/*  603 */           moveIndexToFirst(pos);
/*  604 */           return setValue(pos, v);
/*      */         } 
/*  606 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  607 */           if (curr.equals(k)) {
/*  608 */             moveIndexToFirst(pos);
/*  609 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  613 */     }  this.key[pos] = k;
/*  614 */     this.value[pos] = v;
/*  615 */     if (this.size == 0) {
/*  616 */       this.first = this.last = pos;
/*      */       
/*  618 */       this.link[pos] = -1L;
/*      */     } else {
/*  620 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  621 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  622 */       this.first = pos;
/*      */     } 
/*  624 */     if (this.size++ >= this.maxFill) {
/*  625 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  628 */     return this.defRetValue;
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
/*  643 */     if (k == null) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToLast(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       K[] key = this.key;
/*      */       K curr;
/*  654 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  655 */         if (curr.equals(k)) {
/*  656 */           moveIndexToLast(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  660 */           if (curr.equals(k)) {
/*  661 */             moveIndexToLast(pos);
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
/*  673 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  674 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  675 */       this.last = pos;
/*      */     } 
/*  677 */     if (this.size++ >= this.maxFill) {
/*  678 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  681 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  686 */     if (k == null) {
/*  687 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  689 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  692 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  693 */       return this.defRetValue; 
/*  694 */     if (k.equals(curr)) {
/*  695 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  698 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  699 */         return this.defRetValue; 
/*  700 */       if (k.equals(curr)) {
/*  701 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  707 */     if (k == null) {
/*  708 */       return this.containsNullKey;
/*      */     }
/*  710 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  713 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  714 */       return false; 
/*  715 */     if (k.equals(curr)) {
/*  716 */       return true;
/*      */     }
/*      */     while (true) {
/*  719 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  720 */         return false; 
/*  721 */       if (k.equals(curr))
/*  722 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  727 */     boolean[] value = this.value;
/*  728 */     K[] key = this.key;
/*  729 */     if (this.containsNullKey && value[this.n] == v)
/*  730 */       return true; 
/*  731 */     for (int i = this.n; i-- != 0;) {
/*  732 */       if (key[i] != null && value[i] == v)
/*  733 */         return true; 
/*  734 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  740 */     if (k == null) {
/*  741 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  743 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  746 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  747 */       return defaultValue; 
/*  748 */     if (k.equals(curr)) {
/*  749 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  752 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  753 */         return defaultValue; 
/*  754 */       if (k.equals(curr)) {
/*  755 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  761 */     int pos = find(k);
/*  762 */     if (pos >= 0)
/*  763 */       return this.value[pos]; 
/*  764 */     insert(-pos - 1, k, v);
/*  765 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  771 */     if (k == null) {
/*  772 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  773 */         removeNullEntry();
/*  774 */         return true;
/*      */       } 
/*  776 */       return false;
/*      */     } 
/*      */     
/*  779 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  782 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  783 */       return false; 
/*  784 */     if (k.equals(curr) && v == this.value[pos]) {
/*  785 */       removeEntry(pos);
/*  786 */       return true;
/*      */     } 
/*      */     while (true) {
/*  789 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  790 */         return false; 
/*  791 */       if (k.equals(curr) && v == this.value[pos]) {
/*  792 */         removeEntry(pos);
/*  793 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  800 */     int pos = find(k);
/*  801 */     if (pos < 0 || oldValue != this.value[pos])
/*  802 */       return false; 
/*  803 */     this.value[pos] = v;
/*  804 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  809 */     int pos = find(k);
/*  810 */     if (pos < 0)
/*  811 */       return this.defRetValue; 
/*  812 */     boolean oldValue = this.value[pos];
/*  813 */     this.value[pos] = v;
/*  814 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  819 */     Objects.requireNonNull(mappingFunction);
/*  820 */     int pos = find(k);
/*  821 */     if (pos >= 0)
/*  822 */       return this.value[pos]; 
/*  823 */     boolean newValue = mappingFunction.test(k);
/*  824 */     insert(-pos - 1, k, newValue);
/*  825 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  831 */     Objects.requireNonNull(remappingFunction);
/*  832 */     int pos = find(k);
/*  833 */     if (pos < 0)
/*  834 */       return this.defRetValue; 
/*  835 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  836 */     if (newValue == null) {
/*  837 */       if (k == null) {
/*  838 */         removeNullEntry();
/*      */       } else {
/*  840 */         removeEntry(pos);
/*  841 */       }  return this.defRetValue;
/*      */     } 
/*  843 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  849 */     Objects.requireNonNull(remappingFunction);
/*  850 */     int pos = find(k);
/*  851 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  852 */     if (newValue == null) {
/*  853 */       if (pos >= 0)
/*  854 */         if (k == null) {
/*  855 */           removeNullEntry();
/*      */         } else {
/*  857 */           removeEntry(pos);
/*      */         }  
/*  859 */       return this.defRetValue;
/*      */     } 
/*  861 */     boolean newVal = newValue.booleanValue();
/*  862 */     if (pos < 0) {
/*  863 */       insert(-pos - 1, k, newVal);
/*  864 */       return newVal;
/*      */     } 
/*  866 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mergeBoolean(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  872 */     Objects.requireNonNull(remappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos < 0) {
/*  875 */       insert(-pos - 1, k, v);
/*  876 */       return v;
/*      */     } 
/*  878 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  879 */     if (newValue == null) {
/*  880 */       if (k == null) {
/*  881 */         removeNullEntry();
/*      */       } else {
/*  883 */         removeEntry(pos);
/*  884 */       }  return this.defRetValue;
/*      */     } 
/*  886 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  897 */     if (this.size == 0)
/*      */       return; 
/*  899 */     this.size = 0;
/*  900 */     this.containsNullKey = false;
/*  901 */     Arrays.fill((Object[])this.key, (Object)null);
/*  902 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  906 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  910 */     return (this.size == 0);
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
/*  922 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  928 */       return Object2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  932 */       return Object2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  936 */       boolean oldValue = Object2BooleanLinkedOpenHashMap.this.value[this.index];
/*  937 */       Object2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  938 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  948 */       return Boolean.valueOf(Object2BooleanLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  958 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  963 */       if (!(o instanceof Map.Entry))
/*  964 */         return false; 
/*  965 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  966 */       return (Objects.equals(Object2BooleanLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2BooleanLinkedOpenHashMap.this.value[this.index] == ((Boolean)e
/*  967 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  971 */       return ((Object2BooleanLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2BooleanLinkedOpenHashMap.this.key[this.index].hashCode()) ^ (Object2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  975 */       return (new StringBuilder()).append(Object2BooleanLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2BooleanLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  986 */     if (this.size == 0) {
/*  987 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  990 */     if (this.first == i) {
/*  991 */       this.first = (int)this.link[i];
/*  992 */       if (0 <= this.first)
/*      */       {
/*  994 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  998 */     if (this.last == i) {
/*  999 */       this.last = (int)(this.link[i] >>> 32L);
/* 1000 */       if (0 <= this.last)
/*      */       {
/* 1002 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1006 */     long linki = this.link[i];
/* 1007 */     int prev = (int)(linki >>> 32L);
/* 1008 */     int next = (int)linki;
/* 1009 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1010 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1023 */     if (this.size == 1) {
/* 1024 */       this.first = this.last = d;
/*      */       
/* 1026 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1029 */     if (this.first == s) {
/* 1030 */       this.first = d;
/* 1031 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1032 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1035 */     if (this.last == s) {
/* 1036 */       this.last = d;
/* 1037 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1038 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1041 */     long links = this.link[s];
/* 1042 */     int prev = (int)(links >>> 32L);
/* 1043 */     int next = (int)links;
/* 1044 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1045 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1046 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1055 */     if (this.size == 0)
/* 1056 */       throw new NoSuchElementException(); 
/* 1057 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1066 */     if (this.size == 0)
/* 1067 */       throw new NoSuchElementException(); 
/* 1068 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> tailMap(K from) {
/* 1077 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> headMap(K to) {
/* 1086 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap<K> subMap(K from, K to) {
/* 1095 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1104 */     return null;
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
/* 1119 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1125 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1136 */     int index = -1;
/*      */     protected MapIterator() {
/* 1138 */       this.next = Object2BooleanLinkedOpenHashMap.this.first;
/* 1139 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1142 */       if (from == null) {
/* 1143 */         if (Object2BooleanLinkedOpenHashMap.this.containsNullKey) {
/* 1144 */           this.next = (int)Object2BooleanLinkedOpenHashMap.this.link[Object2BooleanLinkedOpenHashMap.this.n];
/* 1145 */           this.prev = Object2BooleanLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1148 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1150 */       if (Objects.equals(Object2BooleanLinkedOpenHashMap.this.key[Object2BooleanLinkedOpenHashMap.this.last], from)) {
/* 1151 */         this.prev = Object2BooleanLinkedOpenHashMap.this.last;
/* 1152 */         this.index = Object2BooleanLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1156 */       int pos = HashCommon.mix(from.hashCode()) & Object2BooleanLinkedOpenHashMap.this.mask;
/*      */       
/* 1158 */       while (Object2BooleanLinkedOpenHashMap.this.key[pos] != null) {
/* 1159 */         if (Object2BooleanLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1161 */           this.next = (int)Object2BooleanLinkedOpenHashMap.this.link[pos];
/* 1162 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1165 */         pos = pos + 1 & Object2BooleanLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1167 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1170 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1173 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1176 */       if (this.index >= 0)
/*      */         return; 
/* 1178 */       if (this.prev == -1) {
/* 1179 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1182 */       if (this.next == -1) {
/* 1183 */         this.index = Object2BooleanLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1186 */       int pos = Object2BooleanLinkedOpenHashMap.this.first;
/* 1187 */       this.index = 1;
/* 1188 */       while (pos != this.prev) {
/* 1189 */         pos = (int)Object2BooleanLinkedOpenHashMap.this.link[pos];
/* 1190 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1194 */       ensureIndexKnown();
/* 1195 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1198 */       ensureIndexKnown();
/* 1199 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1202 */       if (!hasNext())
/* 1203 */         throw new NoSuchElementException(); 
/* 1204 */       this.curr = this.next;
/* 1205 */       this.next = (int)Object2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1206 */       this.prev = this.curr;
/* 1207 */       if (this.index >= 0)
/* 1208 */         this.index++; 
/* 1209 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1212 */       if (!hasPrevious())
/* 1213 */         throw new NoSuchElementException(); 
/* 1214 */       this.curr = this.prev;
/* 1215 */       this.prev = (int)(Object2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1216 */       this.next = this.curr;
/* 1217 */       if (this.index >= 0)
/* 1218 */         this.index--; 
/* 1219 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1222 */       ensureIndexKnown();
/* 1223 */       if (this.curr == -1)
/* 1224 */         throw new IllegalStateException(); 
/* 1225 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1230 */         this.index--;
/* 1231 */         this.prev = (int)(Object2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1233 */         this.next = (int)Object2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1234 */       }  Object2BooleanLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1239 */       if (this.prev == -1) {
/* 1240 */         Object2BooleanLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1242 */         Object2BooleanLinkedOpenHashMap.this.link[this.prev] = Object2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (Object2BooleanLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1243 */       }  if (this.next == -1) {
/* 1244 */         Object2BooleanLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1246 */         Object2BooleanLinkedOpenHashMap.this.link[this.next] = Object2BooleanLinkedOpenHashMap.this.link[this.next] ^ (Object2BooleanLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1247 */       }  int pos = this.curr;
/* 1248 */       this.curr = -1;
/* 1249 */       if (pos == Object2BooleanLinkedOpenHashMap.this.n) {
/* 1250 */         Object2BooleanLinkedOpenHashMap.this.containsNullKey = false;
/* 1251 */         Object2BooleanLinkedOpenHashMap.this.key[Object2BooleanLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1254 */         K[] key = Object2BooleanLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1258 */           pos = (last = pos) + 1 & Object2BooleanLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1260 */             if ((curr = key[pos]) == null) {
/* 1261 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1264 */             int slot = HashCommon.mix(curr.hashCode()) & Object2BooleanLinkedOpenHashMap.this.mask;
/* 1265 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1267 */             pos = pos + 1 & Object2BooleanLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1269 */           key[last] = curr;
/* 1270 */           Object2BooleanLinkedOpenHashMap.this.value[last] = Object2BooleanLinkedOpenHashMap.this.value[pos];
/* 1271 */           if (this.next == pos)
/* 1272 */             this.next = last; 
/* 1273 */           if (this.prev == pos)
/* 1274 */             this.prev = last; 
/* 1275 */           Object2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1280 */       int i = n;
/* 1281 */       while (i-- != 0 && hasNext())
/* 1282 */         nextEntry(); 
/* 1283 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1286 */       int i = n;
/* 1287 */       while (i-- != 0 && hasPrevious())
/* 1288 */         previousEntry(); 
/* 1289 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2BooleanMap.Entry<K> ok) {
/* 1292 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2BooleanMap.Entry<K> ok) {
/* 1295 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> { private Object2BooleanLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1303 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenHashMap<K>.MapEntry next() {
/* 1307 */       return this.entry = new Object2BooleanLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenHashMap<K>.MapEntry previous() {
/* 1311 */       return this.entry = new Object2BooleanLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1315 */       super.remove();
/* 1316 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1320 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2BooleanMap.Entry<K>> { final Object2BooleanLinkedOpenHashMap<K>.MapEntry entry = new Object2BooleanLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1324 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenHashMap<K>.MapEntry next() {
/* 1328 */       this.entry.index = nextEntry();
/* 1329 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2BooleanLinkedOpenHashMap<K>.MapEntry previous() {
/* 1333 */       this.entry.index = previousEntry();
/* 1334 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2BooleanMap.Entry<K>> implements Object2BooleanSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2BooleanMap.Entry<K>> iterator() {
/* 1342 */       return new Object2BooleanLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2BooleanMap.Entry<K>> comparator() {
/* 1346 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> subSet(Object2BooleanMap.Entry<K> fromElement, Object2BooleanMap.Entry<K> toElement) {
/* 1351 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> headSet(Object2BooleanMap.Entry<K> toElement) {
/* 1355 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2BooleanMap.Entry<K>> tailSet(Object2BooleanMap.Entry<K> fromElement) {
/* 1359 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> first() {
/* 1363 */       if (Object2BooleanLinkedOpenHashMap.this.size == 0)
/* 1364 */         throw new NoSuchElementException(); 
/* 1365 */       return new Object2BooleanLinkedOpenHashMap.MapEntry(Object2BooleanLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2BooleanMap.Entry<K> last() {
/* 1369 */       if (Object2BooleanLinkedOpenHashMap.this.size == 0)
/* 1370 */         throw new NoSuchElementException(); 
/* 1371 */       return new Object2BooleanLinkedOpenHashMap.MapEntry(Object2BooleanLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1376 */       if (!(o instanceof Map.Entry))
/* 1377 */         return false; 
/* 1378 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1379 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1380 */         return false; 
/* 1381 */       K k = (K)e.getKey();
/* 1382 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1383 */       if (k == null) {
/* 1384 */         return (Object2BooleanLinkedOpenHashMap.this.containsNullKey && Object2BooleanLinkedOpenHashMap.this.value[Object2BooleanLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1386 */       K[] key = Object2BooleanLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1389 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2BooleanLinkedOpenHashMap.this.mask]) == null)
/* 1390 */         return false; 
/* 1391 */       if (k.equals(curr)) {
/* 1392 */         return (Object2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1395 */         if ((curr = key[pos = pos + 1 & Object2BooleanLinkedOpenHashMap.this.mask]) == null)
/* 1396 */           return false; 
/* 1397 */         if (k.equals(curr)) {
/* 1398 */           return (Object2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1404 */       if (!(o instanceof Map.Entry))
/* 1405 */         return false; 
/* 1406 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1407 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1408 */         return false; 
/* 1409 */       K k = (K)e.getKey();
/* 1410 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1411 */       if (k == null) {
/* 1412 */         if (Object2BooleanLinkedOpenHashMap.this.containsNullKey && Object2BooleanLinkedOpenHashMap.this.value[Object2BooleanLinkedOpenHashMap.this.n] == v) {
/* 1413 */           Object2BooleanLinkedOpenHashMap.this.removeNullEntry();
/* 1414 */           return true;
/*      */         } 
/* 1416 */         return false;
/*      */       } 
/*      */       
/* 1419 */       K[] key = Object2BooleanLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1422 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2BooleanLinkedOpenHashMap.this.mask]) == null)
/* 1423 */         return false; 
/* 1424 */       if (curr.equals(k)) {
/* 1425 */         if (Object2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1426 */           Object2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1427 */           return true;
/*      */         } 
/* 1429 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1432 */         if ((curr = key[pos = pos + 1 & Object2BooleanLinkedOpenHashMap.this.mask]) == null)
/* 1433 */           return false; 
/* 1434 */         if (curr.equals(k) && 
/* 1435 */           Object2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1436 */           Object2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1437 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1444 */       return Object2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1448 */       Object2BooleanLinkedOpenHashMap.this.clear();
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
/* 1463 */       return new Object2BooleanLinkedOpenHashMap.EntryIterator(from.getKey());
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
/* 1474 */       return new Object2BooleanLinkedOpenHashMap.FastEntryIterator();
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
/* 1489 */       return new Object2BooleanLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/* 1494 */       for (int i = Object2BooleanLinkedOpenHashMap.this.size, next = Object2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1495 */         int curr = next;
/* 1496 */         next = (int)Object2BooleanLinkedOpenHashMap.this.link[curr];
/* 1497 */         consumer.accept(new AbstractObject2BooleanMap.BasicEntry<>(Object2BooleanLinkedOpenHashMap.this.key[curr], Object2BooleanLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2BooleanMap.Entry<K>> consumer) {
/* 1503 */       AbstractObject2BooleanMap.BasicEntry<K> entry = new AbstractObject2BooleanMap.BasicEntry<>();
/* 1504 */       for (int i = Object2BooleanLinkedOpenHashMap.this.size, next = Object2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1505 */         int curr = next;
/* 1506 */         next = (int)Object2BooleanLinkedOpenHashMap.this.link[curr];
/* 1507 */         entry.key = Object2BooleanLinkedOpenHashMap.this.key[curr];
/* 1508 */         entry.value = Object2BooleanLinkedOpenHashMap.this.value[curr];
/* 1509 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2BooleanSortedMap.FastSortedEntrySet<K> object2BooleanEntrySet() {
/* 1515 */     if (this.entries == null)
/* 1516 */       this.entries = new MapEntrySet(); 
/* 1517 */     return this.entries;
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
/* 1530 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1534 */       return Object2BooleanLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1541 */       return Object2BooleanLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1547 */       return new Object2BooleanLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1551 */       return new Object2BooleanLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1556 */       if (Object2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1557 */         consumer.accept(Object2BooleanLinkedOpenHashMap.this.key[Object2BooleanLinkedOpenHashMap.this.n]); 
/* 1558 */       for (int pos = Object2BooleanLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1559 */         K k = Object2BooleanLinkedOpenHashMap.this.key[pos];
/* 1560 */         if (k != null)
/* 1561 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1566 */       return Object2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1570 */       return Object2BooleanLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1574 */       int oldSize = Object2BooleanLinkedOpenHashMap.this.size;
/* 1575 */       Object2BooleanLinkedOpenHashMap.this.removeBoolean(k);
/* 1576 */       return (Object2BooleanLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1580 */       Object2BooleanLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1584 */       if (Object2BooleanLinkedOpenHashMap.this.size == 0)
/* 1585 */         throw new NoSuchElementException(); 
/* 1586 */       return Object2BooleanLinkedOpenHashMap.this.key[Object2BooleanLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1590 */       if (Object2BooleanLinkedOpenHashMap.this.size == 0)
/* 1591 */         throw new NoSuchElementException(); 
/* 1592 */       return Object2BooleanLinkedOpenHashMap.this.key[Object2BooleanLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1596 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1600 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1604 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1608 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1613 */     if (this.keys == null)
/* 1614 */       this.keys = new KeySet(); 
/* 1615 */     return this.keys;
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
/* 1629 */       return Object2BooleanLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1636 */       return Object2BooleanLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1641 */     if (this.values == null)
/* 1642 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1645 */             return (BooleanIterator)new Object2BooleanLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1649 */             return Object2BooleanLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1653 */             return Object2BooleanLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1657 */             Object2BooleanLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1662 */             if (Object2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1663 */               consumer.accept(Object2BooleanLinkedOpenHashMap.this.value[Object2BooleanLinkedOpenHashMap.this.n]); 
/* 1664 */             for (int pos = Object2BooleanLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1665 */               if (Object2BooleanLinkedOpenHashMap.this.key[pos] != null)
/* 1666 */                 consumer.accept(Object2BooleanLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1669 */     return this.values;
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
/* 1686 */     return trim(this.size);
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
/* 1710 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1711 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1712 */       return true; 
/*      */     try {
/* 1714 */       rehash(l);
/* 1715 */     } catch (OutOfMemoryError cantDoIt) {
/* 1716 */       return false;
/*      */     } 
/* 1718 */     return true;
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
/* 1734 */     K[] key = this.key;
/* 1735 */     boolean[] value = this.value;
/* 1736 */     int mask = newN - 1;
/* 1737 */     K[] newKey = (K[])new Object[newN + 1];
/* 1738 */     boolean[] newValue = new boolean[newN + 1];
/* 1739 */     int i = this.first, prev = -1, newPrev = -1;
/* 1740 */     long[] link = this.link;
/* 1741 */     long[] newLink = new long[newN + 1];
/* 1742 */     this.first = -1;
/* 1743 */     for (int j = this.size; j-- != 0; ) {
/* 1744 */       int pos; if (key[i] == null) {
/* 1745 */         pos = newN;
/*      */       } else {
/* 1747 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1748 */         while (newKey[pos] != null)
/* 1749 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1751 */       newKey[pos] = key[i];
/* 1752 */       newValue[pos] = value[i];
/* 1753 */       if (prev != -1) {
/* 1754 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1755 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1756 */         newPrev = pos;
/*      */       } else {
/* 1758 */         newPrev = this.first = pos;
/*      */         
/* 1760 */         newLink[pos] = -1L;
/*      */       } 
/* 1762 */       int t = i;
/* 1763 */       i = (int)link[i];
/* 1764 */       prev = t;
/*      */     } 
/* 1766 */     this.link = newLink;
/* 1767 */     this.last = newPrev;
/* 1768 */     if (newPrev != -1)
/*      */     {
/* 1770 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1771 */     this.n = newN;
/* 1772 */     this.mask = mask;
/* 1773 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1774 */     this.key = newKey;
/* 1775 */     this.value = newValue;
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
/*      */   public Object2BooleanLinkedOpenHashMap<K> clone() {
/*      */     Object2BooleanLinkedOpenHashMap<K> c;
/*      */     try {
/* 1792 */       c = (Object2BooleanLinkedOpenHashMap<K>)super.clone();
/* 1793 */     } catch (CloneNotSupportedException cantHappen) {
/* 1794 */       throw new InternalError();
/*      */     } 
/* 1796 */     c.keys = null;
/* 1797 */     c.values = null;
/* 1798 */     c.entries = null;
/* 1799 */     c.containsNullKey = this.containsNullKey;
/* 1800 */     c.key = (K[])this.key.clone();
/* 1801 */     c.value = (boolean[])this.value.clone();
/* 1802 */     c.link = (long[])this.link.clone();
/* 1803 */     return c;
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
/* 1816 */     int h = 0;
/* 1817 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1818 */       while (this.key[i] == null)
/* 1819 */         i++; 
/* 1820 */       if (this != this.key[i])
/* 1821 */         t = this.key[i].hashCode(); 
/* 1822 */       t ^= this.value[i] ? 1231 : 1237;
/* 1823 */       h += t;
/* 1824 */       i++;
/*      */     } 
/*      */     
/* 1827 */     if (this.containsNullKey)
/* 1828 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1829 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1832 */     K[] key = this.key;
/* 1833 */     boolean[] value = this.value;
/* 1834 */     MapIterator i = new MapIterator();
/* 1835 */     s.defaultWriteObject();
/* 1836 */     for (int j = this.size; j-- != 0; ) {
/* 1837 */       int e = i.nextEntry();
/* 1838 */       s.writeObject(key[e]);
/* 1839 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1844 */     s.defaultReadObject();
/* 1845 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1846 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1847 */     this.mask = this.n - 1;
/* 1848 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1849 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1850 */     long[] link = this.link = new long[this.n + 1];
/* 1851 */     int prev = -1;
/* 1852 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1855 */     for (int i = this.size; i-- != 0; ) {
/* 1856 */       int pos; K k = (K)s.readObject();
/* 1857 */       boolean v = s.readBoolean();
/* 1858 */       if (k == null) {
/* 1859 */         pos = this.n;
/* 1860 */         this.containsNullKey = true;
/*      */       } else {
/* 1862 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1863 */         while (key[pos] != null)
/* 1864 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1866 */       key[pos] = k;
/* 1867 */       value[pos] = v;
/* 1868 */       if (this.first != -1) {
/* 1869 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1870 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1871 */         prev = pos; continue;
/*      */       } 
/* 1873 */       prev = this.first = pos;
/*      */       
/* 1875 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1878 */     this.last = prev;
/* 1879 */     if (prev != -1)
/*      */     {
/* 1881 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2BooleanLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */