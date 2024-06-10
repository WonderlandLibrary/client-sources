/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2IntLinkedOpenHashMap
/*      */   extends AbstractShort2IntSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient int[] value;
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
/*      */   protected transient Short2IntSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ShortSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new short[this.n + 1];
/*  162 */     this.value = new int[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntLinkedOpenHashMap() {
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
/*      */   public Short2IntLinkedOpenHashMap(Map<? extends Short, ? extends Integer> m, float f) {
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
/*      */   public Short2IntLinkedOpenHashMap(Map<? extends Short, ? extends Integer> m) {
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
/*      */   public Short2IntLinkedOpenHashMap(Short2IntMap m, float f) {
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
/*      */   public Short2IntLinkedOpenHashMap(Short2IntMap m) {
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
/*      */   public Short2IntLinkedOpenHashMap(short[] k, int[] v, float f) {
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
/*      */   public Short2IntLinkedOpenHashMap(short[] k, int[] v) {
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
/*      */   private int removeEntry(int pos) {
/*  275 */     int oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     int oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Integer> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(short k) {
/*  303 */     if (k == 0) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  309 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  310 */       return -(pos + 1); 
/*  311 */     if (k == curr) {
/*  312 */       return pos;
/*      */     }
/*      */     while (true) {
/*  315 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  316 */         return -(pos + 1); 
/*  317 */       if (k == curr)
/*  318 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, short k, int v) {
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
/*      */   public int put(short k, int v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     int oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  352 */     int oldValue = this.value[pos];
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
/*      */   public int addTo(short k, int incr) {
/*      */     int pos;
/*  374 */     if (k == 0) {
/*  375 */       if (this.containsNullKey)
/*  376 */         return addToValue(this.n, incr); 
/*  377 */       pos = this.n;
/*  378 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  381 */       short[] key = this.key;
/*      */       short curr;
/*  383 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  384 */         if (curr == k)
/*  385 */           return addToValue(pos, incr); 
/*  386 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*  419 */     short[] key = this.key; while (true) {
/*      */       short curr; int last;
/*  421 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  423 */         if ((curr = key[pos]) == 0) {
/*  424 */           key[last] = 0;
/*      */           return;
/*      */         } 
/*  427 */         int slot = HashCommon.mix(curr) & this.mask;
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
/*      */   public int remove(short k) {
/*  440 */     if (k == 0) {
/*  441 */       if (this.containsNullKey)
/*  442 */         return removeNullEntry(); 
/*  443 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  446 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  449 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  450 */       return this.defRetValue; 
/*  451 */     if (k == curr)
/*  452 */       return removeEntry(pos); 
/*      */     while (true) {
/*  454 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  455 */         return this.defRetValue; 
/*  456 */       if (k == curr)
/*  457 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private int setValue(int pos, int v) {
/*  461 */     int oldValue = this.value[pos];
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
/*      */   public int removeFirstInt() {
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
/*  484 */     int v = this.value[pos];
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
/*      */   public int removeLastInt() {
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
/*  511 */     int v = this.value[pos];
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
/*      */   public int getAndMoveToFirst(short k) {
/*  566 */     if (k == 0) {
/*  567 */       if (this.containsNullKey) {
/*  568 */         moveIndexToFirst(this.n);
/*  569 */         return this.value[this.n];
/*      */       } 
/*  571 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  574 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  577 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  578 */       return this.defRetValue; 
/*  579 */     if (k == curr) {
/*  580 */       moveIndexToFirst(pos);
/*  581 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  585 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
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
/*      */   public int getAndMoveToLast(short k) {
/*  603 */     if (k == 0) {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToLast(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  614 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  615 */       return this.defRetValue; 
/*  616 */     if (k == curr) {
/*  617 */       moveIndexToLast(pos);
/*  618 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  622 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
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
/*      */   public int putAndMoveToFirst(short k, int v) {
/*      */     int pos;
/*  643 */     if (k == 0) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToFirst(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       short[] key = this.key;
/*      */       short curr;
/*  654 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  655 */         if (curr == k) {
/*  656 */           moveIndexToFirst(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*      */   public int putAndMoveToLast(short k, int v) {
/*      */     int pos;
/*  696 */     if (k == 0) {
/*  697 */       if (this.containsNullKey) {
/*  698 */         moveIndexToLast(this.n);
/*  699 */         return setValue(this.n, v);
/*      */       } 
/*  701 */       this.containsNullKey = true;
/*  702 */       pos = this.n;
/*      */     } else {
/*      */       
/*  705 */       short[] key = this.key;
/*      */       short curr;
/*  707 */       if ((curr = key[pos = HashCommon.mix(k) & this.mask]) != 0) {
/*  708 */         if (curr == k) {
/*  709 */           moveIndexToLast(pos);
/*  710 */           return setValue(pos, v);
/*      */         } 
/*  712 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0) {
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
/*      */   public int get(short k) {
/*  739 */     if (k == 0) {
/*  740 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  742 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  745 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  746 */       return this.defRetValue; 
/*  747 */     if (k == curr) {
/*  748 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  751 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  752 */         return this.defRetValue; 
/*  753 */       if (k == curr) {
/*  754 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(short k) {
/*  760 */     if (k == 0) {
/*  761 */       return this.containsNullKey;
/*      */     }
/*  763 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  766 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  767 */       return false; 
/*  768 */     if (k == curr) {
/*  769 */       return true;
/*      */     }
/*      */     while (true) {
/*  772 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  773 */         return false; 
/*  774 */       if (k == curr)
/*  775 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(int v) {
/*  780 */     int[] value = this.value;
/*  781 */     short[] key = this.key;
/*  782 */     if (this.containsNullKey && value[this.n] == v)
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != 0 && value[i] == v)
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(short k, int defaultValue) {
/*  793 */     if (k == 0) {
/*  794 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  796 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  799 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  800 */       return defaultValue; 
/*  801 */     if (k == curr) {
/*  802 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  805 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  806 */         return defaultValue; 
/*  807 */       if (k == curr) {
/*  808 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(short k, int v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, int v) {
/*  824 */     if (k == 0) {
/*  825 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  826 */         removeNullEntry();
/*  827 */         return true;
/*      */       } 
/*  829 */       return false;
/*      */     } 
/*      */     
/*  832 */     short[] key = this.key;
/*      */     short curr;
/*      */     int pos;
/*  835 */     if ((curr = key[pos = HashCommon.mix(k) & this.mask]) == 0)
/*  836 */       return false; 
/*  837 */     if (k == curr && v == this.value[pos]) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  843 */         return false; 
/*  844 */       if (k == curr && v == this.value[pos]) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, int oldValue, int v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || oldValue != this.value[pos])
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(short k, int v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     int oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  872 */     Objects.requireNonNull(mappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos >= 0)
/*  875 */       return this.value[pos]; 
/*  876 */     int newValue = mappingFunction.applyAsInt(k);
/*  877 */     insert(-pos - 1, k, newValue);
/*  878 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(short k, IntFunction<? extends Integer> mappingFunction) {
/*  884 */     Objects.requireNonNull(mappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos >= 0)
/*  887 */       return this.value[pos]; 
/*  888 */     Integer newValue = mappingFunction.apply(k);
/*  889 */     if (newValue == null)
/*  890 */       return this.defRetValue; 
/*  891 */     int v = newValue.intValue();
/*  892 */     insert(-pos - 1, k, v);
/*  893 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(short k, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Integer newValue = remappingFunction.apply(Short.valueOf(k), Integer.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == 0) {
/*  906 */         removeNullEntry();
/*      */       } else {
/*  908 */         removeEntry(pos);
/*  909 */       }  return this.defRetValue;
/*      */     } 
/*  911 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(short k, BiFunction<? super Short, ? super Integer, ? extends Integer> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Integer newValue = remappingFunction.apply(Short.valueOf(k), 
/*  920 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  921 */     if (newValue == null) {
/*  922 */       if (pos >= 0)
/*  923 */         if (k == 0) {
/*  924 */           removeNullEntry();
/*      */         } else {
/*  926 */           removeEntry(pos);
/*      */         }  
/*  928 */       return this.defRetValue;
/*      */     } 
/*  930 */     int newVal = newValue.intValue();
/*  931 */     if (pos < 0) {
/*  932 */       insert(-pos - 1, k, newVal);
/*  933 */       return newVal;
/*      */     } 
/*  935 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(short k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  941 */     Objects.requireNonNull(remappingFunction);
/*  942 */     int pos = find(k);
/*  943 */     if (pos < 0) {
/*  944 */       insert(-pos - 1, k, v);
/*  945 */       return v;
/*      */     } 
/*  947 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  948 */     if (newValue == null) {
/*  949 */       if (k == 0) {
/*  950 */         removeNullEntry();
/*      */       } else {
/*  952 */         removeEntry(pos);
/*  953 */       }  return this.defRetValue;
/*      */     } 
/*  955 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  966 */     if (this.size == 0)
/*      */       return; 
/*  968 */     this.size = 0;
/*  969 */     this.containsNullKey = false;
/*  970 */     Arrays.fill(this.key, (short)0);
/*  971 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  975 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  979 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2IntMap.Entry, Map.Entry<Short, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  991 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public short getShortKey() {
/*  997 */       return Short2IntLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/* 1001 */       return Short2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/* 1005 */       int oldValue = Short2IntLinkedOpenHashMap.this.value[this.index];
/* 1006 */       Short2IntLinkedOpenHashMap.this.value[this.index] = v;
/* 1007 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/* 1017 */       return Short.valueOf(Short2IntLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/* 1027 */       return Integer.valueOf(Short2IntLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/* 1037 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1042 */       if (!(o instanceof Map.Entry))
/* 1043 */         return false; 
/* 1044 */       Map.Entry<Short, Integer> e = (Map.Entry<Short, Integer>)o;
/* 1045 */       return (Short2IntLinkedOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && Short2IntLinkedOpenHashMap.this.value[this.index] == ((Integer)e.getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1049 */       return Short2IntLinkedOpenHashMap.this.key[this.index] ^ Short2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1053 */       return Short2IntLinkedOpenHashMap.this.key[this.index] + "=>" + Short2IntLinkedOpenHashMap.this.value[this.index];
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
/*      */   public short firstShortKey() {
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
/*      */   public short lastShortKey() {
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
/*      */   public Short2IntSortedMap tailMap(short from) {
/* 1155 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntSortedMap headMap(short to) {
/* 1164 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2IntSortedMap subMap(short from, short to) {
/* 1173 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortComparator comparator() {
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
/* 1216 */       this.next = Short2IntLinkedOpenHashMap.this.first;
/* 1217 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(short from) {
/* 1220 */       if (from == 0) {
/* 1221 */         if (Short2IntLinkedOpenHashMap.this.containsNullKey) {
/* 1222 */           this.next = (int)Short2IntLinkedOpenHashMap.this.link[Short2IntLinkedOpenHashMap.this.n];
/* 1223 */           this.prev = Short2IntLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1226 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1228 */       if (Short2IntLinkedOpenHashMap.this.key[Short2IntLinkedOpenHashMap.this.last] == from) {
/* 1229 */         this.prev = Short2IntLinkedOpenHashMap.this.last;
/* 1230 */         this.index = Short2IntLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1234 */       int pos = HashCommon.mix(from) & Short2IntLinkedOpenHashMap.this.mask;
/*      */       
/* 1236 */       while (Short2IntLinkedOpenHashMap.this.key[pos] != 0) {
/* 1237 */         if (Short2IntLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1239 */           this.next = (int)Short2IntLinkedOpenHashMap.this.link[pos];
/* 1240 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1243 */         pos = pos + 1 & Short2IntLinkedOpenHashMap.this.mask;
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
/* 1261 */         this.index = Short2IntLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1264 */       int pos = Short2IntLinkedOpenHashMap.this.first;
/* 1265 */       this.index = 1;
/* 1266 */       while (pos != this.prev) {
/* 1267 */         pos = (int)Short2IntLinkedOpenHashMap.this.link[pos];
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
/* 1283 */       this.next = (int)Short2IntLinkedOpenHashMap.this.link[this.curr];
/* 1284 */       this.prev = this.curr;
/* 1285 */       if (this.index >= 0)
/* 1286 */         this.index++; 
/* 1287 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1290 */       if (!hasPrevious())
/* 1291 */         throw new NoSuchElementException(); 
/* 1292 */       this.curr = this.prev;
/* 1293 */       this.prev = (int)(Short2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1309 */         this.prev = (int)(Short2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1311 */         this.next = (int)Short2IntLinkedOpenHashMap.this.link[this.curr];
/* 1312 */       }  Short2IntLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       if (this.prev == -1) {
/* 1318 */         Short2IntLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1320 */         Short2IntLinkedOpenHashMap.this.link[this.prev] = Short2IntLinkedOpenHashMap.this.link[this.prev] ^ (Short2IntLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1321 */       }  if (this.next == -1) {
/* 1322 */         Short2IntLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1324 */         Short2IntLinkedOpenHashMap.this.link[this.next] = Short2IntLinkedOpenHashMap.this.link[this.next] ^ (Short2IntLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1325 */       }  int pos = this.curr;
/* 1326 */       this.curr = -1;
/* 1327 */       if (pos == Short2IntLinkedOpenHashMap.this.n) {
/* 1328 */         Short2IntLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1331 */         short[] key = Short2IntLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           short curr;
/*      */           int last;
/* 1335 */           pos = (last = pos) + 1 & Short2IntLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1337 */             if ((curr = key[pos]) == 0) {
/* 1338 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1341 */             int slot = HashCommon.mix(curr) & Short2IntLinkedOpenHashMap.this.mask;
/* 1342 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1344 */             pos = pos + 1 & Short2IntLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1346 */           key[last] = curr;
/* 1347 */           Short2IntLinkedOpenHashMap.this.value[last] = Short2IntLinkedOpenHashMap.this.value[pos];
/* 1348 */           if (this.next == pos)
/* 1349 */             this.next = last; 
/* 1350 */           if (this.prev == pos)
/* 1351 */             this.prev = last; 
/* 1352 */           Short2IntLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Short2IntMap.Entry ok) {
/* 1369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Short2IntMap.Entry ok) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Short2IntMap.Entry> { private Short2IntLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(short from) {
/* 1380 */       super(from);
/*      */     }
/*      */     
/*      */     public Short2IntLinkedOpenHashMap.MapEntry next() {
/* 1384 */       return this.entry = new Short2IntLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Short2IntLinkedOpenHashMap.MapEntry previous() {
/* 1388 */       return this.entry = new Short2IntLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1392 */       super.remove();
/* 1393 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1397 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Short2IntMap.Entry> { final Short2IntLinkedOpenHashMap.MapEntry entry = new Short2IntLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(short from) {
/* 1401 */       super(from);
/*      */     }
/*      */     
/*      */     public Short2IntLinkedOpenHashMap.MapEntry next() {
/* 1405 */       this.entry.index = nextEntry();
/* 1406 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Short2IntLinkedOpenHashMap.MapEntry previous() {
/* 1410 */       this.entry.index = previousEntry();
/* 1411 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Short2IntMap.Entry> implements Short2IntSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Short2IntMap.Entry> iterator() {
/* 1417 */       return (ObjectBidirectionalIterator<Short2IntMap.Entry>)new Short2IntLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Short2IntMap.Entry> comparator() {
/* 1421 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Short2IntMap.Entry> subSet(Short2IntMap.Entry fromElement, Short2IntMap.Entry toElement) {
/* 1426 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2IntMap.Entry> headSet(Short2IntMap.Entry toElement) {
/* 1430 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2IntMap.Entry> tailSet(Short2IntMap.Entry fromElement) {
/* 1434 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Short2IntMap.Entry first() {
/* 1438 */       if (Short2IntLinkedOpenHashMap.this.size == 0)
/* 1439 */         throw new NoSuchElementException(); 
/* 1440 */       return new Short2IntLinkedOpenHashMap.MapEntry(Short2IntLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Short2IntMap.Entry last() {
/* 1444 */       if (Short2IntLinkedOpenHashMap.this.size == 0)
/* 1445 */         throw new NoSuchElementException(); 
/* 1446 */       return new Short2IntLinkedOpenHashMap.MapEntry(Short2IntLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1451 */       if (!(o instanceof Map.Entry))
/* 1452 */         return false; 
/* 1453 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1454 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1455 */         return false; 
/* 1456 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1457 */         return false; 
/* 1458 */       short k = ((Short)e.getKey()).shortValue();
/* 1459 */       int v = ((Integer)e.getValue()).intValue();
/* 1460 */       if (k == 0) {
/* 1461 */         return (Short2IntLinkedOpenHashMap.this.containsNullKey && Short2IntLinkedOpenHashMap.this.value[Short2IntLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1463 */       short[] key = Short2IntLinkedOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1466 */       if ((curr = key[pos = HashCommon.mix(k) & Short2IntLinkedOpenHashMap.this.mask]) == 0)
/* 1467 */         return false; 
/* 1468 */       if (k == curr) {
/* 1469 */         return (Short2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1472 */         if ((curr = key[pos = pos + 1 & Short2IntLinkedOpenHashMap.this.mask]) == 0)
/* 1473 */           return false; 
/* 1474 */         if (k == curr) {
/* 1475 */           return (Short2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1481 */       if (!(o instanceof Map.Entry))
/* 1482 */         return false; 
/* 1483 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1484 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1485 */         return false; 
/* 1486 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1487 */         return false; 
/* 1488 */       short k = ((Short)e.getKey()).shortValue();
/* 1489 */       int v = ((Integer)e.getValue()).intValue();
/* 1490 */       if (k == 0) {
/* 1491 */         if (Short2IntLinkedOpenHashMap.this.containsNullKey && Short2IntLinkedOpenHashMap.this.value[Short2IntLinkedOpenHashMap.this.n] == v) {
/* 1492 */           Short2IntLinkedOpenHashMap.this.removeNullEntry();
/* 1493 */           return true;
/*      */         } 
/* 1495 */         return false;
/*      */       } 
/*      */       
/* 1498 */       short[] key = Short2IntLinkedOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1501 */       if ((curr = key[pos = HashCommon.mix(k) & Short2IntLinkedOpenHashMap.this.mask]) == 0)
/* 1502 */         return false; 
/* 1503 */       if (curr == k) {
/* 1504 */         if (Short2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1505 */           Short2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1506 */           return true;
/*      */         } 
/* 1508 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1511 */         if ((curr = key[pos = pos + 1 & Short2IntLinkedOpenHashMap.this.mask]) == 0)
/* 1512 */           return false; 
/* 1513 */         if (curr == k && 
/* 1514 */           Short2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1515 */           Short2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1516 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1523 */       return Short2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1527 */       Short2IntLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Short2IntMap.Entry> iterator(Short2IntMap.Entry from) {
/* 1542 */       return new Short2IntLinkedOpenHashMap.EntryIterator(from.getShortKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Short2IntMap.Entry> fastIterator() {
/* 1553 */       return new Short2IntLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Short2IntMap.Entry> fastIterator(Short2IntMap.Entry from) {
/* 1568 */       return new Short2IntLinkedOpenHashMap.FastEntryIterator(from.getShortKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2IntMap.Entry> consumer) {
/* 1573 */       for (int i = Short2IntLinkedOpenHashMap.this.size, next = Short2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1574 */         int curr = next;
/* 1575 */         next = (int)Short2IntLinkedOpenHashMap.this.link[curr];
/* 1576 */         consumer.accept(new AbstractShort2IntMap.BasicEntry(Short2IntLinkedOpenHashMap.this.key[curr], Short2IntLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2IntMap.Entry> consumer) {
/* 1582 */       AbstractShort2IntMap.BasicEntry entry = new AbstractShort2IntMap.BasicEntry();
/* 1583 */       for (int i = Short2IntLinkedOpenHashMap.this.size, next = Short2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1584 */         int curr = next;
/* 1585 */         next = (int)Short2IntLinkedOpenHashMap.this.link[curr];
/* 1586 */         entry.key = Short2IntLinkedOpenHashMap.this.key[curr];
/* 1587 */         entry.value = Short2IntLinkedOpenHashMap.this.value[curr];
/* 1588 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Short2IntSortedMap.FastSortedEntrySet short2IntEntrySet() {
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
/*      */     implements ShortListIterator
/*      */   {
/*      */     public KeyIterator(short k) {
/* 1609 */       super(k);
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1613 */       return Short2IntLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public short nextShort() {
/* 1620 */       return Short2IntLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSortedSet { private KeySet() {}
/*      */     
/*      */     public ShortListIterator iterator(short from) {
/* 1626 */       return new Short2IntLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ShortListIterator iterator() {
/* 1630 */       return new Short2IntLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1635 */       if (Short2IntLinkedOpenHashMap.this.containsNullKey)
/* 1636 */         consumer.accept(Short2IntLinkedOpenHashMap.this.key[Short2IntLinkedOpenHashMap.this.n]); 
/* 1637 */       for (int pos = Short2IntLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1638 */         short k = Short2IntLinkedOpenHashMap.this.key[pos];
/* 1639 */         if (k != 0)
/* 1640 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1645 */       return Short2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/* 1649 */       return Short2IntLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/* 1653 */       int oldSize = Short2IntLinkedOpenHashMap.this.size;
/* 1654 */       Short2IntLinkedOpenHashMap.this.remove(k);
/* 1655 */       return (Short2IntLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1659 */       Short2IntLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public short firstShort() {
/* 1663 */       if (Short2IntLinkedOpenHashMap.this.size == 0)
/* 1664 */         throw new NoSuchElementException(); 
/* 1665 */       return Short2IntLinkedOpenHashMap.this.key[Short2IntLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public short lastShort() {
/* 1669 */       if (Short2IntLinkedOpenHashMap.this.size == 0)
/* 1670 */         throw new NoSuchElementException(); 
/* 1671 */       return Short2IntLinkedOpenHashMap.this.key[Short2IntLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public ShortComparator comparator() {
/* 1675 */       return null;
/*      */     }
/*      */     
/*      */     public ShortSortedSet tailSet(short from) {
/* 1679 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ShortSortedSet headSet(short to) {
/* 1683 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ShortSortedSet subSet(short from, short to) {
/* 1687 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSortedSet keySet() {
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
/*      */     implements IntListIterator
/*      */   {
/*      */     public int previousInt() {
/* 1708 */       return Short2IntLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1715 */       return Short2IntLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1720 */     if (this.values == null)
/* 1721 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1724 */             return (IntIterator)new Short2IntLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1728 */             return Short2IntLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1732 */             return Short2IntLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1736 */             Short2IntLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1741 */             if (Short2IntLinkedOpenHashMap.this.containsNullKey)
/* 1742 */               consumer.accept(Short2IntLinkedOpenHashMap.this.value[Short2IntLinkedOpenHashMap.this.n]); 
/* 1743 */             for (int pos = Short2IntLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1744 */               if (Short2IntLinkedOpenHashMap.this.key[pos] != 0)
/* 1745 */                 consumer.accept(Short2IntLinkedOpenHashMap.this.value[pos]); 
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
/* 1813 */     short[] key = this.key;
/* 1814 */     int[] value = this.value;
/* 1815 */     int mask = newN - 1;
/* 1816 */     short[] newKey = new short[newN + 1];
/* 1817 */     int[] newValue = new int[newN + 1];
/* 1818 */     int i = this.first, prev = -1, newPrev = -1;
/* 1819 */     long[] link = this.link;
/* 1820 */     long[] newLink = new long[newN + 1];
/* 1821 */     this.first = -1;
/* 1822 */     for (int j = this.size; j-- != 0; ) {
/* 1823 */       int pos; if (key[i] == 0) {
/* 1824 */         pos = newN;
/*      */       } else {
/* 1826 */         pos = HashCommon.mix(key[i]) & mask;
/* 1827 */         while (newKey[pos] != 0)
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
/*      */   public Short2IntLinkedOpenHashMap clone() {
/*      */     Short2IntLinkedOpenHashMap c;
/*      */     try {
/* 1871 */       c = (Short2IntLinkedOpenHashMap)super.clone();
/* 1872 */     } catch (CloneNotSupportedException cantHappen) {
/* 1873 */       throw new InternalError();
/*      */     } 
/* 1875 */     c.keys = null;
/* 1876 */     c.values = null;
/* 1877 */     c.entries = null;
/* 1878 */     c.containsNullKey = this.containsNullKey;
/* 1879 */     c.key = (short[])this.key.clone();
/* 1880 */     c.value = (int[])this.value.clone();
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
/* 1897 */       while (this.key[i] == 0)
/* 1898 */         i++; 
/* 1899 */       t = this.key[i];
/* 1900 */       t ^= this.value[i];
/* 1901 */       h += t;
/* 1902 */       i++;
/*      */     } 
/*      */     
/* 1905 */     if (this.containsNullKey)
/* 1906 */       h += this.value[this.n]; 
/* 1907 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1910 */     short[] key = this.key;
/* 1911 */     int[] value = this.value;
/* 1912 */     MapIterator i = new MapIterator();
/* 1913 */     s.defaultWriteObject();
/* 1914 */     for (int j = this.size; j-- != 0; ) {
/* 1915 */       int e = i.nextEntry();
/* 1916 */       s.writeShort(key[e]);
/* 1917 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1922 */     s.defaultReadObject();
/* 1923 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1924 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1925 */     this.mask = this.n - 1;
/* 1926 */     short[] key = this.key = new short[this.n + 1];
/* 1927 */     int[] value = this.value = new int[this.n + 1];
/* 1928 */     long[] link = this.link = new long[this.n + 1];
/* 1929 */     int prev = -1;
/* 1930 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1933 */     for (int i = this.size; i-- != 0; ) {
/* 1934 */       int pos; short k = s.readShort();
/* 1935 */       int v = s.readInt();
/* 1936 */       if (k == 0) {
/* 1937 */         pos = this.n;
/* 1938 */         this.containsNullKey = true;
/*      */       } else {
/* 1940 */         pos = HashCommon.mix(k) & this.mask;
/* 1941 */         while (key[pos] != 0)
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2IntLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */