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
/*      */ public class Reference2BooleanLinkedOpenHashMap<K>
/*      */   extends AbstractReference2BooleanSortedMap<K>
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
/*      */   protected transient Reference2BooleanSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap(int expected, float f) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(int expected) {
/*  173 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanLinkedOpenHashMap() {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(Map<? extends K, ? extends Boolean> m, float f) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(Map<? extends K, ? extends Boolean> m) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(Reference2BooleanMap<K> m, float f) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(Reference2BooleanMap<K> m) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(K[] k, boolean[] v, float f) {
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
/*      */   public Reference2BooleanLinkedOpenHashMap(K[] k, boolean[] v) {
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
/*  311 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  312 */       return -(pos + 1); 
/*  313 */     if (k == curr) {
/*  314 */       return pos;
/*      */     }
/*      */     while (true) {
/*  317 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  318 */         return -(pos + 1); 
/*  319 */       if (k == curr)
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
/*  372 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
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
/*  394 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  395 */       return this.defRetValue; 
/*  396 */     if (k == curr)
/*  397 */       return removeEntry(pos); 
/*      */     while (true) {
/*  399 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  400 */         return this.defRetValue; 
/*  401 */       if (k == curr)
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
/*  524 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  525 */       return this.defRetValue; 
/*  526 */     if (k == curr) {
/*  527 */       moveIndexToFirst(pos);
/*  528 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  533 */         return this.defRetValue; 
/*  534 */       if (k == curr) {
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
/*  561 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  562 */       return this.defRetValue; 
/*  563 */     if (k == curr) {
/*  564 */       moveIndexToLast(pos);
/*  565 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  569 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  570 */         return this.defRetValue; 
/*  571 */       if (k == curr) {
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
/*  601 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  603 */         if (curr == k) {
/*  604 */           moveIndexToFirst(pos);
/*  605 */           return setValue(pos, v);
/*      */         } 
/*  607 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  608 */           if (curr == k) {
/*  609 */             moveIndexToFirst(pos);
/*  610 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  614 */     }  this.key[pos] = k;
/*  615 */     this.value[pos] = v;
/*  616 */     if (this.size == 0) {
/*  617 */       this.first = this.last = pos;
/*      */       
/*  619 */       this.link[pos] = -1L;
/*      */     } else {
/*  621 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  622 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  623 */       this.first = pos;
/*      */     } 
/*  625 */     if (this.size++ >= this.maxFill) {
/*  626 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  629 */     return this.defRetValue;
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
/*  644 */     if (k == null) {
/*  645 */       if (this.containsNullKey) {
/*  646 */         moveIndexToLast(this.n);
/*  647 */         return setValue(this.n, v);
/*      */       } 
/*  649 */       this.containsNullKey = true;
/*  650 */       pos = this.n;
/*      */     } else {
/*      */       
/*  653 */       K[] key = this.key;
/*      */       K curr;
/*  655 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  657 */         if (curr == k) {
/*  658 */           moveIndexToLast(pos);
/*  659 */           return setValue(pos, v);
/*      */         } 
/*  661 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  662 */           if (curr == k) {
/*  663 */             moveIndexToLast(pos);
/*  664 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  668 */     }  this.key[pos] = k;
/*  669 */     this.value[pos] = v;
/*  670 */     if (this.size == 0) {
/*  671 */       this.first = this.last = pos;
/*      */       
/*  673 */       this.link[pos] = -1L;
/*      */     } else {
/*  675 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  676 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  677 */       this.last = pos;
/*      */     } 
/*  679 */     if (this.size++ >= this.maxFill) {
/*  680 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  683 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(Object k) {
/*  688 */     if (k == null) {
/*  689 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  691 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  694 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  695 */       return this.defRetValue; 
/*  696 */     if (k == curr) {
/*  697 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  700 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  701 */         return this.defRetValue; 
/*  702 */       if (k == curr) {
/*  703 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  709 */     if (k == null) {
/*  710 */       return this.containsNullKey;
/*      */     }
/*  712 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  715 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  716 */       return false; 
/*  717 */     if (k == curr) {
/*  718 */       return true;
/*      */     }
/*      */     while (true) {
/*  721 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  722 */         return false; 
/*  723 */       if (k == curr)
/*  724 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  729 */     boolean[] value = this.value;
/*  730 */     K[] key = this.key;
/*  731 */     if (this.containsNullKey && value[this.n] == v)
/*  732 */       return true; 
/*  733 */     for (int i = this.n; i-- != 0;) {
/*  734 */       if (key[i] != null && value[i] == v)
/*  735 */         return true; 
/*  736 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(Object k, boolean defaultValue) {
/*  742 */     if (k == null) {
/*  743 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  745 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  748 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  749 */       return defaultValue; 
/*  750 */     if (k == curr) {
/*  751 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  754 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  755 */         return defaultValue; 
/*  756 */       if (k == curr) {
/*  757 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(K k, boolean v) {
/*  763 */     int pos = find(k);
/*  764 */     if (pos >= 0)
/*  765 */       return this.value[pos]; 
/*  766 */     insert(-pos - 1, k, v);
/*  767 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, boolean v) {
/*  773 */     if (k == null) {
/*  774 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  775 */         removeNullEntry();
/*  776 */         return true;
/*      */       } 
/*  778 */       return false;
/*      */     } 
/*      */     
/*  781 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  784 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  785 */       return false; 
/*  786 */     if (k == curr && v == this.value[pos]) {
/*  787 */       removeEntry(pos);
/*  788 */       return true;
/*      */     } 
/*      */     while (true) {
/*  791 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  792 */         return false; 
/*  793 */       if (k == curr && v == this.value[pos]) {
/*  794 */         removeEntry(pos);
/*  795 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean oldValue, boolean v) {
/*  802 */     int pos = find(k);
/*  803 */     if (pos < 0 || oldValue != this.value[pos])
/*  804 */       return false; 
/*  805 */     this.value[pos] = v;
/*  806 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, boolean v) {
/*  811 */     int pos = find(k);
/*  812 */     if (pos < 0)
/*  813 */       return this.defRetValue; 
/*  814 */     boolean oldValue = this.value[pos];
/*  815 */     this.value[pos] = v;
/*  816 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfAbsent(K k, Predicate<? super K> mappingFunction) {
/*  821 */     Objects.requireNonNull(mappingFunction);
/*  822 */     int pos = find(k);
/*  823 */     if (pos >= 0)
/*  824 */       return this.value[pos]; 
/*  825 */     boolean newValue = mappingFunction.test(k);
/*  826 */     insert(-pos - 1, k, newValue);
/*  827 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBooleanIfPresent(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  833 */     Objects.requireNonNull(remappingFunction);
/*  834 */     int pos = find(k);
/*  835 */     if (pos < 0)
/*  836 */       return this.defRetValue; 
/*  837 */     Boolean newValue = remappingFunction.apply(k, Boolean.valueOf(this.value[pos]));
/*  838 */     if (newValue == null) {
/*  839 */       if (k == null) {
/*  840 */         removeNullEntry();
/*      */       } else {
/*  842 */         removeEntry(pos);
/*  843 */       }  return this.defRetValue;
/*      */     } 
/*  845 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeBoolean(K k, BiFunction<? super K, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  851 */     Objects.requireNonNull(remappingFunction);
/*  852 */     int pos = find(k);
/*  853 */     Boolean newValue = remappingFunction.apply(k, (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  854 */     if (newValue == null) {
/*  855 */       if (pos >= 0)
/*  856 */         if (k == null) {
/*  857 */           removeNullEntry();
/*      */         } else {
/*  859 */           removeEntry(pos);
/*      */         }  
/*  861 */       return this.defRetValue;
/*      */     } 
/*  863 */     boolean newVal = newValue.booleanValue();
/*  864 */     if (pos < 0) {
/*  865 */       insert(-pos - 1, k, newVal);
/*  866 */       return newVal;
/*      */     } 
/*  868 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mergeBoolean(K k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  874 */     Objects.requireNonNull(remappingFunction);
/*  875 */     int pos = find(k);
/*  876 */     if (pos < 0) {
/*  877 */       insert(-pos - 1, k, v);
/*  878 */       return v;
/*      */     } 
/*  880 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  881 */     if (newValue == null) {
/*  882 */       if (k == null) {
/*  883 */         removeNullEntry();
/*      */       } else {
/*  885 */         removeEntry(pos);
/*  886 */       }  return this.defRetValue;
/*      */     } 
/*  888 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  899 */     if (this.size == 0)
/*      */       return; 
/*  901 */     this.size = 0;
/*  902 */     this.containsNullKey = false;
/*  903 */     Arrays.fill((Object[])this.key, (Object)null);
/*  904 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  908 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  912 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2BooleanMap.Entry<K>, Map.Entry<K, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  924 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  930 */       return Reference2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  934 */       return Reference2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  938 */       boolean oldValue = Reference2BooleanLinkedOpenHashMap.this.value[this.index];
/*  939 */       Reference2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  940 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  950 */       return Boolean.valueOf(Reference2BooleanLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/*  960 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  965 */       if (!(o instanceof Map.Entry))
/*  966 */         return false; 
/*  967 */       Map.Entry<K, Boolean> e = (Map.Entry<K, Boolean>)o;
/*  968 */       return (Reference2BooleanLinkedOpenHashMap.this.key[this.index] == e.getKey() && Reference2BooleanLinkedOpenHashMap.this.value[this.index] == ((Boolean)e.getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  972 */       return System.identityHashCode(Reference2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Reference2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  976 */       return (new StringBuilder()).append(Reference2BooleanLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2BooleanLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  987 */     if (this.size == 0) {
/*  988 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  991 */     if (this.first == i) {
/*  992 */       this.first = (int)this.link[i];
/*  993 */       if (0 <= this.first)
/*      */       {
/*  995 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  999 */     if (this.last == i) {
/* 1000 */       this.last = (int)(this.link[i] >>> 32L);
/* 1001 */       if (0 <= this.last)
/*      */       {
/* 1003 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1007 */     long linki = this.link[i];
/* 1008 */     int prev = (int)(linki >>> 32L);
/* 1009 */     int next = (int)linki;
/* 1010 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1011 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1024 */     if (this.size == 1) {
/* 1025 */       this.first = this.last = d;
/*      */       
/* 1027 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1030 */     if (this.first == s) {
/* 1031 */       this.first = d;
/* 1032 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1033 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1036 */     if (this.last == s) {
/* 1037 */       this.last = d;
/* 1038 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1039 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1042 */     long links = this.link[s];
/* 1043 */     int prev = (int)(links >>> 32L);
/* 1044 */     int next = (int)links;
/* 1045 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1046 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1047 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1056 */     if (this.size == 0)
/* 1057 */       throw new NoSuchElementException(); 
/* 1058 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1067 */     if (this.size == 0)
/* 1068 */       throw new NoSuchElementException(); 
/* 1069 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap<K> tailMap(K from) {
/* 1078 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap<K> headMap(K to) {
/* 1087 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap<K> subMap(K from, K to) {
/* 1096 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1105 */     return null;
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
/* 1120 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1126 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1131 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1137 */     int index = -1;
/*      */     protected MapIterator() {
/* 1139 */       this.next = Reference2BooleanLinkedOpenHashMap.this.first;
/* 1140 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1143 */       if (from == null) {
/* 1144 */         if (Reference2BooleanLinkedOpenHashMap.this.containsNullKey) {
/* 1145 */           this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[Reference2BooleanLinkedOpenHashMap.this.n];
/* 1146 */           this.prev = Reference2BooleanLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1149 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1151 */       if (Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.last] == from) {
/* 1152 */         this.prev = Reference2BooleanLinkedOpenHashMap.this.last;
/* 1153 */         this.index = Reference2BooleanLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1157 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */       
/* 1159 */       while (Reference2BooleanLinkedOpenHashMap.this.key[pos] != null) {
/* 1160 */         if (Reference2BooleanLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1162 */           this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[pos];
/* 1163 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1166 */         pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1168 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1171 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1174 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1177 */       if (this.index >= 0)
/*      */         return; 
/* 1179 */       if (this.prev == -1) {
/* 1180 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1183 */       if (this.next == -1) {
/* 1184 */         this.index = Reference2BooleanLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1187 */       int pos = Reference2BooleanLinkedOpenHashMap.this.first;
/* 1188 */       this.index = 1;
/* 1189 */       while (pos != this.prev) {
/* 1190 */         pos = (int)Reference2BooleanLinkedOpenHashMap.this.link[pos];
/* 1191 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1195 */       ensureIndexKnown();
/* 1196 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1199 */       ensureIndexKnown();
/* 1200 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1203 */       if (!hasNext())
/* 1204 */         throw new NoSuchElementException(); 
/* 1205 */       this.curr = this.next;
/* 1206 */       this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1207 */       this.prev = this.curr;
/* 1208 */       if (this.index >= 0)
/* 1209 */         this.index++; 
/* 1210 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1213 */       if (!hasPrevious())
/* 1214 */         throw new NoSuchElementException(); 
/* 1215 */       this.curr = this.prev;
/* 1216 */       this.prev = (int)(Reference2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1217 */       this.next = this.curr;
/* 1218 */       if (this.index >= 0)
/* 1219 */         this.index--; 
/* 1220 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1223 */       ensureIndexKnown();
/* 1224 */       if (this.curr == -1)
/* 1225 */         throw new IllegalStateException(); 
/* 1226 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1231 */         this.index--;
/* 1232 */         this.prev = (int)(Reference2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1234 */         this.next = (int)Reference2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1235 */       }  Reference2BooleanLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1240 */       if (this.prev == -1) {
/* 1241 */         Reference2BooleanLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1243 */         Reference2BooleanLinkedOpenHashMap.this.link[this.prev] = Reference2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (Reference2BooleanLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1244 */       }  if (this.next == -1) {
/* 1245 */         Reference2BooleanLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1247 */         Reference2BooleanLinkedOpenHashMap.this.link[this.next] = Reference2BooleanLinkedOpenHashMap.this.link[this.next] ^ (Reference2BooleanLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1248 */       }  int pos = this.curr;
/* 1249 */       this.curr = -1;
/* 1250 */       if (pos == Reference2BooleanLinkedOpenHashMap.this.n) {
/* 1251 */         Reference2BooleanLinkedOpenHashMap.this.containsNullKey = false;
/* 1252 */         Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1255 */         K[] key = Reference2BooleanLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1259 */           pos = (last = pos) + 1 & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1261 */             if ((curr = key[pos]) == null) {
/* 1262 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1265 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2BooleanLinkedOpenHashMap.this.mask;
/* 1266 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1268 */             pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1270 */           key[last] = curr;
/* 1271 */           Reference2BooleanLinkedOpenHashMap.this.value[last] = Reference2BooleanLinkedOpenHashMap.this.value[pos];
/* 1272 */           if (this.next == pos)
/* 1273 */             this.next = last; 
/* 1274 */           if (this.prev == pos)
/* 1275 */             this.prev = last; 
/* 1276 */           Reference2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1281 */       int i = n;
/* 1282 */       while (i-- != 0 && hasNext())
/* 1283 */         nextEntry(); 
/* 1284 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1287 */       int i = n;
/* 1288 */       while (i-- != 0 && hasPrevious())
/* 1289 */         previousEntry(); 
/* 1290 */       return n - i - 1;
/*      */     }
/*      */     public void set(Reference2BooleanMap.Entry<K> ok) {
/* 1293 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Reference2BooleanMap.Entry<K> ok) {
/* 1296 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Reference2BooleanMap.Entry<K>> { private Reference2BooleanLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1304 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry next() {
/* 1308 */       return this.entry = new Reference2BooleanLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry previous() {
/* 1312 */       return this.entry = new Reference2BooleanLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1316 */       super.remove();
/* 1317 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1321 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Reference2BooleanMap.Entry<K>> { final Reference2BooleanLinkedOpenHashMap<K>.MapEntry entry = new Reference2BooleanLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1325 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry next() {
/* 1329 */       this.entry.index = nextEntry();
/* 1330 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Reference2BooleanLinkedOpenHashMap<K>.MapEntry previous() {
/* 1334 */       this.entry.index = previousEntry();
/* 1335 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2BooleanMap.Entry<K>> implements Reference2BooleanSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2BooleanMap.Entry<K>> iterator() {
/* 1343 */       return new Reference2BooleanLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Reference2BooleanMap.Entry<K>> comparator() {
/* 1347 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2BooleanMap.Entry<K>> subSet(Reference2BooleanMap.Entry<K> fromElement, Reference2BooleanMap.Entry<K> toElement) {
/* 1352 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2BooleanMap.Entry<K>> headSet(Reference2BooleanMap.Entry<K> toElement) {
/* 1356 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2BooleanMap.Entry<K>> tailSet(Reference2BooleanMap.Entry<K> fromElement) {
/* 1360 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Reference2BooleanMap.Entry<K> first() {
/* 1364 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0)
/* 1365 */         throw new NoSuchElementException(); 
/* 1366 */       return new Reference2BooleanLinkedOpenHashMap.MapEntry(Reference2BooleanLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Reference2BooleanMap.Entry<K> last() {
/* 1370 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0)
/* 1371 */         throw new NoSuchElementException(); 
/* 1372 */       return new Reference2BooleanLinkedOpenHashMap.MapEntry(Reference2BooleanLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1377 */       if (!(o instanceof Map.Entry))
/* 1378 */         return false; 
/* 1379 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1380 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1381 */         return false; 
/* 1382 */       K k = (K)e.getKey();
/* 1383 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1384 */       if (k == null) {
/* 1385 */         return (Reference2BooleanLinkedOpenHashMap.this.containsNullKey && Reference2BooleanLinkedOpenHashMap.this.value[Reference2BooleanLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1387 */       K[] key = Reference2BooleanLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1390 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1392 */         return false; } 
/* 1393 */       if (k == curr) {
/* 1394 */         return (Reference2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1397 */         if ((curr = key[pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask]) == null)
/* 1398 */           return false; 
/* 1399 */         if (k == curr) {
/* 1400 */           return (Reference2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1406 */       if (!(o instanceof Map.Entry))
/* 1407 */         return false; 
/* 1408 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1409 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1410 */         return false; 
/* 1411 */       K k = (K)e.getKey();
/* 1412 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1413 */       if (k == null) {
/* 1414 */         if (Reference2BooleanLinkedOpenHashMap.this.containsNullKey && Reference2BooleanLinkedOpenHashMap.this.value[Reference2BooleanLinkedOpenHashMap.this.n] == v) {
/* 1415 */           Reference2BooleanLinkedOpenHashMap.this.removeNullEntry();
/* 1416 */           return true;
/*      */         } 
/* 1418 */         return false;
/*      */       } 
/*      */       
/* 1421 */       K[] key = Reference2BooleanLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1424 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2BooleanLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1426 */         return false; } 
/* 1427 */       if (curr == k) {
/* 1428 */         if (Reference2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1429 */           Reference2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1430 */           return true;
/*      */         } 
/* 1432 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1435 */         if ((curr = key[pos = pos + 1 & Reference2BooleanLinkedOpenHashMap.this.mask]) == null)
/* 1436 */           return false; 
/* 1437 */         if (curr == k && 
/* 1438 */           Reference2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1439 */           Reference2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1440 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1447 */       return Reference2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1451 */       Reference2BooleanLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2BooleanMap.Entry<K>> iterator(Reference2BooleanMap.Entry<K> from) {
/* 1466 */       return new Reference2BooleanLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2BooleanMap.Entry<K>> fastIterator() {
/* 1477 */       return new Reference2BooleanLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2BooleanMap.Entry<K>> fastIterator(Reference2BooleanMap.Entry<K> from) {
/* 1493 */       return new Reference2BooleanLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1498 */       for (int i = Reference2BooleanLinkedOpenHashMap.this.size, next = Reference2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1499 */         int curr = next;
/* 1500 */         next = (int)Reference2BooleanLinkedOpenHashMap.this.link[curr];
/* 1501 */         consumer.accept(new AbstractReference2BooleanMap.BasicEntry<>(Reference2BooleanLinkedOpenHashMap.this.key[curr], Reference2BooleanLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2BooleanMap.Entry<K>> consumer) {
/* 1507 */       AbstractReference2BooleanMap.BasicEntry<K> entry = new AbstractReference2BooleanMap.BasicEntry<>();
/* 1508 */       for (int i = Reference2BooleanLinkedOpenHashMap.this.size, next = Reference2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1509 */         int curr = next;
/* 1510 */         next = (int)Reference2BooleanLinkedOpenHashMap.this.link[curr];
/* 1511 */         entry.key = Reference2BooleanLinkedOpenHashMap.this.key[curr];
/* 1512 */         entry.value = Reference2BooleanLinkedOpenHashMap.this.value[curr];
/* 1513 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Reference2BooleanSortedMap.FastSortedEntrySet<K> reference2BooleanEntrySet() {
/* 1519 */     if (this.entries == null)
/* 1520 */       this.entries = new MapEntrySet(); 
/* 1521 */     return this.entries;
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
/* 1534 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1538 */       return Reference2BooleanLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1545 */       return Reference2BooleanLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1551 */       return new Reference2BooleanLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1555 */       return new Reference2BooleanLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1560 */       if (Reference2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1561 */         consumer.accept(Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.n]); 
/* 1562 */       for (int pos = Reference2BooleanLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1563 */         K k = Reference2BooleanLinkedOpenHashMap.this.key[pos];
/* 1564 */         if (k != null)
/* 1565 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1570 */       return Reference2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1574 */       return Reference2BooleanLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1578 */       int oldSize = Reference2BooleanLinkedOpenHashMap.this.size;
/* 1579 */       Reference2BooleanLinkedOpenHashMap.this.removeBoolean(k);
/* 1580 */       return (Reference2BooleanLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1584 */       Reference2BooleanLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1588 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0)
/* 1589 */         throw new NoSuchElementException(); 
/* 1590 */       return Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1594 */       if (Reference2BooleanLinkedOpenHashMap.this.size == 0)
/* 1595 */         throw new NoSuchElementException(); 
/* 1596 */       return Reference2BooleanLinkedOpenHashMap.this.key[Reference2BooleanLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1600 */       return null;
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1604 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1608 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1612 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1617 */     if (this.keys == null)
/* 1618 */       this.keys = new KeySet(); 
/* 1619 */     return this.keys;
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
/* 1633 */       return Reference2BooleanLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1640 */       return Reference2BooleanLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1645 */     if (this.values == null)
/* 1646 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1649 */             return (BooleanIterator)new Reference2BooleanLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1653 */             return Reference2BooleanLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1657 */             return Reference2BooleanLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1661 */             Reference2BooleanLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1666 */             if (Reference2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1667 */               consumer.accept(Reference2BooleanLinkedOpenHashMap.this.value[Reference2BooleanLinkedOpenHashMap.this.n]); 
/* 1668 */             for (int pos = Reference2BooleanLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1669 */               if (Reference2BooleanLinkedOpenHashMap.this.key[pos] != null)
/* 1670 */                 consumer.accept(Reference2BooleanLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1673 */     return this.values;
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
/* 1690 */     return trim(this.size);
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
/* 1714 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1715 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1716 */       return true; 
/*      */     try {
/* 1718 */       rehash(l);
/* 1719 */     } catch (OutOfMemoryError cantDoIt) {
/* 1720 */       return false;
/*      */     } 
/* 1722 */     return true;
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
/* 1738 */     K[] key = this.key;
/* 1739 */     boolean[] value = this.value;
/* 1740 */     int mask = newN - 1;
/* 1741 */     K[] newKey = (K[])new Object[newN + 1];
/* 1742 */     boolean[] newValue = new boolean[newN + 1];
/* 1743 */     int i = this.first, prev = -1, newPrev = -1;
/* 1744 */     long[] link = this.link;
/* 1745 */     long[] newLink = new long[newN + 1];
/* 1746 */     this.first = -1;
/* 1747 */     for (int j = this.size; j-- != 0; ) {
/* 1748 */       int pos; if (key[i] == null) {
/* 1749 */         pos = newN;
/*      */       } else {
/* 1751 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1752 */         while (newKey[pos] != null)
/* 1753 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1755 */       newKey[pos] = key[i];
/* 1756 */       newValue[pos] = value[i];
/* 1757 */       if (prev != -1) {
/* 1758 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1759 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1760 */         newPrev = pos;
/*      */       } else {
/* 1762 */         newPrev = this.first = pos;
/*      */         
/* 1764 */         newLink[pos] = -1L;
/*      */       } 
/* 1766 */       int t = i;
/* 1767 */       i = (int)link[i];
/* 1768 */       prev = t;
/*      */     } 
/* 1770 */     this.link = newLink;
/* 1771 */     this.last = newPrev;
/* 1772 */     if (newPrev != -1)
/*      */     {
/* 1774 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1775 */     this.n = newN;
/* 1776 */     this.mask = mask;
/* 1777 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1778 */     this.key = newKey;
/* 1779 */     this.value = newValue;
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
/*      */   public Reference2BooleanLinkedOpenHashMap<K> clone() {
/*      */     Reference2BooleanLinkedOpenHashMap<K> c;
/*      */     try {
/* 1796 */       c = (Reference2BooleanLinkedOpenHashMap<K>)super.clone();
/* 1797 */     } catch (CloneNotSupportedException cantHappen) {
/* 1798 */       throw new InternalError();
/*      */     } 
/* 1800 */     c.keys = null;
/* 1801 */     c.values = null;
/* 1802 */     c.entries = null;
/* 1803 */     c.containsNullKey = this.containsNullKey;
/* 1804 */     c.key = (K[])this.key.clone();
/* 1805 */     c.value = (boolean[])this.value.clone();
/* 1806 */     c.link = (long[])this.link.clone();
/* 1807 */     return c;
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
/* 1820 */     int h = 0;
/* 1821 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1822 */       while (this.key[i] == null)
/* 1823 */         i++; 
/* 1824 */       if (this != this.key[i])
/* 1825 */         t = System.identityHashCode(this.key[i]); 
/* 1826 */       t ^= this.value[i] ? 1231 : 1237;
/* 1827 */       h += t;
/* 1828 */       i++;
/*      */     } 
/*      */     
/* 1831 */     if (this.containsNullKey)
/* 1832 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1833 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1836 */     K[] key = this.key;
/* 1837 */     boolean[] value = this.value;
/* 1838 */     MapIterator i = new MapIterator();
/* 1839 */     s.defaultWriteObject();
/* 1840 */     for (int j = this.size; j-- != 0; ) {
/* 1841 */       int e = i.nextEntry();
/* 1842 */       s.writeObject(key[e]);
/* 1843 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1848 */     s.defaultReadObject();
/* 1849 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1850 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1851 */     this.mask = this.n - 1;
/* 1852 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1853 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1854 */     long[] link = this.link = new long[this.n + 1];
/* 1855 */     int prev = -1;
/* 1856 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1859 */     for (int i = this.size; i-- != 0; ) {
/* 1860 */       int pos; K k = (K)s.readObject();
/* 1861 */       boolean v = s.readBoolean();
/* 1862 */       if (k == null) {
/* 1863 */         pos = this.n;
/* 1864 */         this.containsNullKey = true;
/*      */       } else {
/* 1866 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1867 */         while (key[pos] != null)
/* 1868 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1870 */       key[pos] = k;
/* 1871 */       value[pos] = v;
/* 1872 */       if (this.first != -1) {
/* 1873 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1874 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1875 */         prev = pos; continue;
/*      */       } 
/* 1877 */       prev = this.first = pos;
/*      */       
/* 1879 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1882 */     this.last = prev;
/* 1883 */     if (prev != -1)
/*      */     {
/* 1885 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2BooleanLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */