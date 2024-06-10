/*      */ package it.unimi.dsi.fastutil.shorts;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2ShortLinkedOpenHashMap
/*      */   extends AbstractShort2ShortSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
/*      */   protected transient short[] value;
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
/*      */   protected transient Short2ShortSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ShortSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ShortCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new short[this.n + 1];
/*  162 */     this.value = new short[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortLinkedOpenHashMap() {
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
/*      */   public Short2ShortLinkedOpenHashMap(Map<? extends Short, ? extends Short> m, float f) {
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
/*      */   public Short2ShortLinkedOpenHashMap(Map<? extends Short, ? extends Short> m) {
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
/*      */   public Short2ShortLinkedOpenHashMap(Short2ShortMap m, float f) {
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
/*      */   public Short2ShortLinkedOpenHashMap(Short2ShortMap m) {
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
/*      */   public Short2ShortLinkedOpenHashMap(short[] k, short[] v, float f) {
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
/*      */   public Short2ShortLinkedOpenHashMap(short[] k, short[] v) {
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
/*      */   private short removeEntry(int pos) {
/*  275 */     short oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     short oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Short, ? extends Short> m) {
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
/*      */   private void insert(int pos, short k, short v) {
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
/*      */   public short put(short k, short v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     short oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  352 */     short oldValue = this.value[pos];
/*  353 */     this.value[pos] = (short)(oldValue + incr);
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
/*      */   public short addTo(short k, short incr) {
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
/*  392 */     this.value[pos] = (short)(this.defRetValue + incr);
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
/*      */   public short remove(short k) {
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
/*      */   private short setValue(int pos, short v) {
/*  461 */     short oldValue = this.value[pos];
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
/*      */   public short removeFirstShort() {
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
/*  484 */     short v = this.value[pos];
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
/*      */   public short removeLastShort() {
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
/*  511 */     short v = this.value[pos];
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
/*      */   public short getAndMoveToFirst(short k) {
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
/*      */   public short getAndMoveToLast(short k) {
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
/*      */   public short putAndMoveToFirst(short k, short v) {
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
/*      */   public short putAndMoveToLast(short k, short v) {
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
/*      */   public short get(short k) {
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
/*      */   public boolean containsValue(short v) {
/*  780 */     short[] value = this.value;
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
/*      */   public short getOrDefault(short k, short defaultValue) {
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
/*      */   public short putIfAbsent(short k, short v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, short v) {
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
/*      */   public boolean replace(short k, short oldValue, short v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || oldValue != this.value[pos])
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(short k, short v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     short oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(short k, IntUnaryOperator mappingFunction) {
/*  872 */     Objects.requireNonNull(mappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos >= 0)
/*  875 */       return this.value[pos]; 
/*  876 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  877 */     insert(-pos - 1, k, newValue);
/*  878 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(short k, IntFunction<? extends Short> mappingFunction) {
/*  884 */     Objects.requireNonNull(mappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos >= 0)
/*  887 */       return this.value[pos]; 
/*  888 */     Short newValue = mappingFunction.apply(k);
/*  889 */     if (newValue == null)
/*  890 */       return this.defRetValue; 
/*  891 */     short v = newValue.shortValue();
/*  892 */     insert(-pos - 1, k, v);
/*  893 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(short k, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Short newValue = remappingFunction.apply(Short.valueOf(k), Short.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == 0) {
/*  906 */         removeNullEntry();
/*      */       } else {
/*  908 */         removeEntry(pos);
/*  909 */       }  return this.defRetValue;
/*      */     } 
/*  911 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(short k, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Short newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  920 */     if (newValue == null) {
/*  921 */       if (pos >= 0)
/*  922 */         if (k == 0) {
/*  923 */           removeNullEntry();
/*      */         } else {
/*  925 */           removeEntry(pos);
/*      */         }  
/*  927 */       return this.defRetValue;
/*      */     } 
/*  929 */     short newVal = newValue.shortValue();
/*  930 */     if (pos < 0) {
/*  931 */       insert(-pos - 1, k, newVal);
/*  932 */       return newVal;
/*      */     } 
/*  934 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(short k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  940 */     Objects.requireNonNull(remappingFunction);
/*  941 */     int pos = find(k);
/*  942 */     if (pos < 0) {
/*  943 */       insert(-pos - 1, k, v);
/*  944 */       return v;
/*      */     } 
/*  946 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  947 */     if (newValue == null) {
/*  948 */       if (k == 0) {
/*  949 */         removeNullEntry();
/*      */       } else {
/*  951 */         removeEntry(pos);
/*  952 */       }  return this.defRetValue;
/*      */     } 
/*  954 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  965 */     if (this.size == 0)
/*      */       return; 
/*  967 */     this.size = 0;
/*  968 */     this.containsNullKey = false;
/*  969 */     Arrays.fill(this.key, (short)0);
/*  970 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  974 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  978 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Short2ShortMap.Entry, Map.Entry<Short, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  990 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public short getShortKey() {
/*  996 */       return Short2ShortLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/* 1000 */       return Short2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/* 1004 */       short oldValue = Short2ShortLinkedOpenHashMap.this.value[this.index];
/* 1005 */       Short2ShortLinkedOpenHashMap.this.value[this.index] = v;
/* 1006 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getKey() {
/* 1016 */       return Short.valueOf(Short2ShortLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/* 1026 */       return Short.valueOf(Short2ShortLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/* 1036 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1041 */       if (!(o instanceof Map.Entry))
/* 1042 */         return false; 
/* 1043 */       Map.Entry<Short, Short> e = (Map.Entry<Short, Short>)o;
/* 1044 */       return (Short2ShortLinkedOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && Short2ShortLinkedOpenHashMap.this.value[this.index] == ((Short)e.getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1048 */       return Short2ShortLinkedOpenHashMap.this.key[this.index] ^ Short2ShortLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1052 */       return Short2ShortLinkedOpenHashMap.this.key[this.index] + "=>" + Short2ShortLinkedOpenHashMap.this.value[this.index];
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
/* 1063 */     if (this.size == 0) {
/* 1064 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1067 */     if (this.first == i) {
/* 1068 */       this.first = (int)this.link[i];
/* 1069 */       if (0 <= this.first)
/*      */       {
/* 1071 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1075 */     if (this.last == i) {
/* 1076 */       this.last = (int)(this.link[i] >>> 32L);
/* 1077 */       if (0 <= this.last)
/*      */       {
/* 1079 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1083 */     long linki = this.link[i];
/* 1084 */     int prev = (int)(linki >>> 32L);
/* 1085 */     int next = (int)linki;
/* 1086 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1087 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1100 */     if (this.size == 1) {
/* 1101 */       this.first = this.last = d;
/*      */       
/* 1103 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1106 */     if (this.first == s) {
/* 1107 */       this.first = d;
/* 1108 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1109 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1112 */     if (this.last == s) {
/* 1113 */       this.last = d;
/* 1114 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1115 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1118 */     long links = this.link[s];
/* 1119 */     int prev = (int)(links >>> 32L);
/* 1120 */     int next = (int)links;
/* 1121 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1122 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1123 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short firstShortKey() {
/* 1132 */     if (this.size == 0)
/* 1133 */       throw new NoSuchElementException(); 
/* 1134 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short lastShortKey() {
/* 1143 */     if (this.size == 0)
/* 1144 */       throw new NoSuchElementException(); 
/* 1145 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortSortedMap tailMap(short from) {
/* 1154 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortSortedMap headMap(short to) {
/* 1163 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2ShortSortedMap subMap(short from, short to) {
/* 1172 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ShortComparator comparator() {
/* 1181 */     return null;
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
/* 1196 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1202 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1207 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1213 */     int index = -1;
/*      */     protected MapIterator() {
/* 1215 */       this.next = Short2ShortLinkedOpenHashMap.this.first;
/* 1216 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(short from) {
/* 1219 */       if (from == 0) {
/* 1220 */         if (Short2ShortLinkedOpenHashMap.this.containsNullKey) {
/* 1221 */           this.next = (int)Short2ShortLinkedOpenHashMap.this.link[Short2ShortLinkedOpenHashMap.this.n];
/* 1222 */           this.prev = Short2ShortLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1225 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1227 */       if (Short2ShortLinkedOpenHashMap.this.key[Short2ShortLinkedOpenHashMap.this.last] == from) {
/* 1228 */         this.prev = Short2ShortLinkedOpenHashMap.this.last;
/* 1229 */         this.index = Short2ShortLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1233 */       int pos = HashCommon.mix(from) & Short2ShortLinkedOpenHashMap.this.mask;
/*      */       
/* 1235 */       while (Short2ShortLinkedOpenHashMap.this.key[pos] != 0) {
/* 1236 */         if (Short2ShortLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1238 */           this.next = (int)Short2ShortLinkedOpenHashMap.this.link[pos];
/* 1239 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1242 */         pos = pos + 1 & Short2ShortLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1244 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1247 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1250 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1253 */       if (this.index >= 0)
/*      */         return; 
/* 1255 */       if (this.prev == -1) {
/* 1256 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1259 */       if (this.next == -1) {
/* 1260 */         this.index = Short2ShortLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1263 */       int pos = Short2ShortLinkedOpenHashMap.this.first;
/* 1264 */       this.index = 1;
/* 1265 */       while (pos != this.prev) {
/* 1266 */         pos = (int)Short2ShortLinkedOpenHashMap.this.link[pos];
/* 1267 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1271 */       ensureIndexKnown();
/* 1272 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1275 */       ensureIndexKnown();
/* 1276 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1279 */       if (!hasNext())
/* 1280 */         throw new NoSuchElementException(); 
/* 1281 */       this.curr = this.next;
/* 1282 */       this.next = (int)Short2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1283 */       this.prev = this.curr;
/* 1284 */       if (this.index >= 0)
/* 1285 */         this.index++; 
/* 1286 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1289 */       if (!hasPrevious())
/* 1290 */         throw new NoSuchElementException(); 
/* 1291 */       this.curr = this.prev;
/* 1292 */       this.prev = (int)(Short2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1293 */       this.next = this.curr;
/* 1294 */       if (this.index >= 0)
/* 1295 */         this.index--; 
/* 1296 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1299 */       ensureIndexKnown();
/* 1300 */       if (this.curr == -1)
/* 1301 */         throw new IllegalStateException(); 
/* 1302 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1307 */         this.index--;
/* 1308 */         this.prev = (int)(Short2ShortLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1310 */         this.next = (int)Short2ShortLinkedOpenHashMap.this.link[this.curr];
/* 1311 */       }  Short2ShortLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1316 */       if (this.prev == -1) {
/* 1317 */         Short2ShortLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1319 */         Short2ShortLinkedOpenHashMap.this.link[this.prev] = Short2ShortLinkedOpenHashMap.this.link[this.prev] ^ (Short2ShortLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1320 */       }  if (this.next == -1) {
/* 1321 */         Short2ShortLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1323 */         Short2ShortLinkedOpenHashMap.this.link[this.next] = Short2ShortLinkedOpenHashMap.this.link[this.next] ^ (Short2ShortLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1324 */       }  int pos = this.curr;
/* 1325 */       this.curr = -1;
/* 1326 */       if (pos == Short2ShortLinkedOpenHashMap.this.n) {
/* 1327 */         Short2ShortLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1330 */         short[] key = Short2ShortLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           short curr;
/*      */           int last;
/* 1334 */           pos = (last = pos) + 1 & Short2ShortLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1336 */             if ((curr = key[pos]) == 0) {
/* 1337 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1340 */             int slot = HashCommon.mix(curr) & Short2ShortLinkedOpenHashMap.this.mask;
/* 1341 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1343 */             pos = pos + 1 & Short2ShortLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1345 */           key[last] = curr;
/* 1346 */           Short2ShortLinkedOpenHashMap.this.value[last] = Short2ShortLinkedOpenHashMap.this.value[pos];
/* 1347 */           if (this.next == pos)
/* 1348 */             this.next = last; 
/* 1349 */           if (this.prev == pos)
/* 1350 */             this.prev = last; 
/* 1351 */           Short2ShortLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1356 */       int i = n;
/* 1357 */       while (i-- != 0 && hasNext())
/* 1358 */         nextEntry(); 
/* 1359 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1362 */       int i = n;
/* 1363 */       while (i-- != 0 && hasPrevious())
/* 1364 */         previousEntry(); 
/* 1365 */       return n - i - 1;
/*      */     }
/*      */     public void set(Short2ShortMap.Entry ok) {
/* 1368 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Short2ShortMap.Entry ok) {
/* 1371 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Short2ShortMap.Entry> { private Short2ShortLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(short from) {
/* 1379 */       super(from);
/*      */     }
/*      */     
/*      */     public Short2ShortLinkedOpenHashMap.MapEntry next() {
/* 1383 */       return this.entry = new Short2ShortLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Short2ShortLinkedOpenHashMap.MapEntry previous() {
/* 1387 */       return this.entry = new Short2ShortLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1391 */       super.remove();
/* 1392 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1396 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Short2ShortMap.Entry> { final Short2ShortLinkedOpenHashMap.MapEntry entry = new Short2ShortLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(short from) {
/* 1400 */       super(from);
/*      */     }
/*      */     
/*      */     public Short2ShortLinkedOpenHashMap.MapEntry next() {
/* 1404 */       this.entry.index = nextEntry();
/* 1405 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Short2ShortLinkedOpenHashMap.MapEntry previous() {
/* 1409 */       this.entry.index = previousEntry();
/* 1410 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Short2ShortMap.Entry> implements Short2ShortSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Short2ShortMap.Entry> iterator() {
/* 1418 */       return (ObjectBidirectionalIterator<Short2ShortMap.Entry>)new Short2ShortLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Short2ShortMap.Entry> comparator() {
/* 1422 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Short2ShortMap.Entry> subSet(Short2ShortMap.Entry fromElement, Short2ShortMap.Entry toElement) {
/* 1427 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2ShortMap.Entry> headSet(Short2ShortMap.Entry toElement) {
/* 1431 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2ShortMap.Entry> tailSet(Short2ShortMap.Entry fromElement) {
/* 1435 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Short2ShortMap.Entry first() {
/* 1439 */       if (Short2ShortLinkedOpenHashMap.this.size == 0)
/* 1440 */         throw new NoSuchElementException(); 
/* 1441 */       return new Short2ShortLinkedOpenHashMap.MapEntry(Short2ShortLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Short2ShortMap.Entry last() {
/* 1445 */       if (Short2ShortLinkedOpenHashMap.this.size == 0)
/* 1446 */         throw new NoSuchElementException(); 
/* 1447 */       return new Short2ShortLinkedOpenHashMap.MapEntry(Short2ShortLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1452 */       if (!(o instanceof Map.Entry))
/* 1453 */         return false; 
/* 1454 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1455 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1456 */         return false; 
/* 1457 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1458 */         return false; 
/* 1459 */       short k = ((Short)e.getKey()).shortValue();
/* 1460 */       short v = ((Short)e.getValue()).shortValue();
/* 1461 */       if (k == 0) {
/* 1462 */         return (Short2ShortLinkedOpenHashMap.this.containsNullKey && Short2ShortLinkedOpenHashMap.this.value[Short2ShortLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1464 */       short[] key = Short2ShortLinkedOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1467 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ShortLinkedOpenHashMap.this.mask]) == 0)
/* 1468 */         return false; 
/* 1469 */       if (k == curr) {
/* 1470 */         return (Short2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1473 */         if ((curr = key[pos = pos + 1 & Short2ShortLinkedOpenHashMap.this.mask]) == 0)
/* 1474 */           return false; 
/* 1475 */         if (k == curr) {
/* 1476 */           return (Short2ShortLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1482 */       if (!(o instanceof Map.Entry))
/* 1483 */         return false; 
/* 1484 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1485 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1486 */         return false; 
/* 1487 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/* 1488 */         return false; 
/* 1489 */       short k = ((Short)e.getKey()).shortValue();
/* 1490 */       short v = ((Short)e.getValue()).shortValue();
/* 1491 */       if (k == 0) {
/* 1492 */         if (Short2ShortLinkedOpenHashMap.this.containsNullKey && Short2ShortLinkedOpenHashMap.this.value[Short2ShortLinkedOpenHashMap.this.n] == v) {
/* 1493 */           Short2ShortLinkedOpenHashMap.this.removeNullEntry();
/* 1494 */           return true;
/*      */         } 
/* 1496 */         return false;
/*      */       } 
/*      */       
/* 1499 */       short[] key = Short2ShortLinkedOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1502 */       if ((curr = key[pos = HashCommon.mix(k) & Short2ShortLinkedOpenHashMap.this.mask]) == 0)
/* 1503 */         return false; 
/* 1504 */       if (curr == k) {
/* 1505 */         if (Short2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1506 */           Short2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1507 */           return true;
/*      */         } 
/* 1509 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1512 */         if ((curr = key[pos = pos + 1 & Short2ShortLinkedOpenHashMap.this.mask]) == 0)
/* 1513 */           return false; 
/* 1514 */         if (curr == k && 
/* 1515 */           Short2ShortLinkedOpenHashMap.this.value[pos] == v) {
/* 1516 */           Short2ShortLinkedOpenHashMap.this.removeEntry(pos);
/* 1517 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1524 */       return Short2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1528 */       Short2ShortLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Short2ShortMap.Entry> iterator(Short2ShortMap.Entry from) {
/* 1543 */       return new Short2ShortLinkedOpenHashMap.EntryIterator(from.getShortKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Short2ShortMap.Entry> fastIterator() {
/* 1554 */       return new Short2ShortLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Short2ShortMap.Entry> fastIterator(Short2ShortMap.Entry from) {
/* 1569 */       return new Short2ShortLinkedOpenHashMap.FastEntryIterator(from.getShortKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2ShortMap.Entry> consumer) {
/* 1574 */       for (int i = Short2ShortLinkedOpenHashMap.this.size, next = Short2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1575 */         int curr = next;
/* 1576 */         next = (int)Short2ShortLinkedOpenHashMap.this.link[curr];
/* 1577 */         consumer.accept(new AbstractShort2ShortMap.BasicEntry(Short2ShortLinkedOpenHashMap.this.key[curr], Short2ShortLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2ShortMap.Entry> consumer) {
/* 1583 */       AbstractShort2ShortMap.BasicEntry entry = new AbstractShort2ShortMap.BasicEntry();
/* 1584 */       for (int i = Short2ShortLinkedOpenHashMap.this.size, next = Short2ShortLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1585 */         int curr = next;
/* 1586 */         next = (int)Short2ShortLinkedOpenHashMap.this.link[curr];
/* 1587 */         entry.key = Short2ShortLinkedOpenHashMap.this.key[curr];
/* 1588 */         entry.value = Short2ShortLinkedOpenHashMap.this.value[curr];
/* 1589 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Short2ShortSortedMap.FastSortedEntrySet short2ShortEntrySet() {
/* 1595 */     if (this.entries == null)
/* 1596 */       this.entries = new MapEntrySet(); 
/* 1597 */     return this.entries;
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
/* 1610 */       super(k);
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1614 */       return Short2ShortLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public short nextShort() {
/* 1621 */       return Short2ShortLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSortedSet { private KeySet() {}
/*      */     
/*      */     public ShortListIterator iterator(short from) {
/* 1627 */       return new Short2ShortLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ShortListIterator iterator() {
/* 1631 */       return new Short2ShortLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1636 */       if (Short2ShortLinkedOpenHashMap.this.containsNullKey)
/* 1637 */         consumer.accept(Short2ShortLinkedOpenHashMap.this.key[Short2ShortLinkedOpenHashMap.this.n]); 
/* 1638 */       for (int pos = Short2ShortLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1639 */         short k = Short2ShortLinkedOpenHashMap.this.key[pos];
/* 1640 */         if (k != 0)
/* 1641 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1646 */       return Short2ShortLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/* 1650 */       return Short2ShortLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/* 1654 */       int oldSize = Short2ShortLinkedOpenHashMap.this.size;
/* 1655 */       Short2ShortLinkedOpenHashMap.this.remove(k);
/* 1656 */       return (Short2ShortLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1660 */       Short2ShortLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public short firstShort() {
/* 1664 */       if (Short2ShortLinkedOpenHashMap.this.size == 0)
/* 1665 */         throw new NoSuchElementException(); 
/* 1666 */       return Short2ShortLinkedOpenHashMap.this.key[Short2ShortLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public short lastShort() {
/* 1670 */       if (Short2ShortLinkedOpenHashMap.this.size == 0)
/* 1671 */         throw new NoSuchElementException(); 
/* 1672 */       return Short2ShortLinkedOpenHashMap.this.key[Short2ShortLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public ShortComparator comparator() {
/* 1676 */       return null;
/*      */     }
/*      */     
/*      */     public ShortSortedSet tailSet(short from) {
/* 1680 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ShortSortedSet headSet(short to) {
/* 1684 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ShortSortedSet subSet(short from, short to) {
/* 1688 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSortedSet keySet() {
/* 1693 */     if (this.keys == null)
/* 1694 */       this.keys = new KeySet(); 
/* 1695 */     return this.keys;
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
/*      */     implements ShortListIterator
/*      */   {
/*      */     public short previousShort() {
/* 1709 */       return Short2ShortLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public short nextShort() {
/* 1716 */       return Short2ShortLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1721 */     if (this.values == null)
/* 1722 */       this.values = new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1725 */             return new Short2ShortLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1729 */             return Short2ShortLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1733 */             return Short2ShortLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1737 */             Short2ShortLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1742 */             if (Short2ShortLinkedOpenHashMap.this.containsNullKey)
/* 1743 */               consumer.accept(Short2ShortLinkedOpenHashMap.this.value[Short2ShortLinkedOpenHashMap.this.n]); 
/* 1744 */             for (int pos = Short2ShortLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1745 */               if (Short2ShortLinkedOpenHashMap.this.key[pos] != 0)
/* 1746 */                 consumer.accept(Short2ShortLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1749 */     return this.values;
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
/* 1766 */     return trim(this.size);
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
/* 1790 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1791 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1792 */       return true; 
/*      */     try {
/* 1794 */       rehash(l);
/* 1795 */     } catch (OutOfMemoryError cantDoIt) {
/* 1796 */       return false;
/*      */     } 
/* 1798 */     return true;
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
/* 1814 */     short[] key = this.key;
/* 1815 */     short[] value = this.value;
/* 1816 */     int mask = newN - 1;
/* 1817 */     short[] newKey = new short[newN + 1];
/* 1818 */     short[] newValue = new short[newN + 1];
/* 1819 */     int i = this.first, prev = -1, newPrev = -1;
/* 1820 */     long[] link = this.link;
/* 1821 */     long[] newLink = new long[newN + 1];
/* 1822 */     this.first = -1;
/* 1823 */     for (int j = this.size; j-- != 0; ) {
/* 1824 */       int pos; if (key[i] == 0) {
/* 1825 */         pos = newN;
/*      */       } else {
/* 1827 */         pos = HashCommon.mix(key[i]) & mask;
/* 1828 */         while (newKey[pos] != 0)
/* 1829 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1831 */       newKey[pos] = key[i];
/* 1832 */       newValue[pos] = value[i];
/* 1833 */       if (prev != -1) {
/* 1834 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1835 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1836 */         newPrev = pos;
/*      */       } else {
/* 1838 */         newPrev = this.first = pos;
/*      */         
/* 1840 */         newLink[pos] = -1L;
/*      */       } 
/* 1842 */       int t = i;
/* 1843 */       i = (int)link[i];
/* 1844 */       prev = t;
/*      */     } 
/* 1846 */     this.link = newLink;
/* 1847 */     this.last = newPrev;
/* 1848 */     if (newPrev != -1)
/*      */     {
/* 1850 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1851 */     this.n = newN;
/* 1852 */     this.mask = mask;
/* 1853 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1854 */     this.key = newKey;
/* 1855 */     this.value = newValue;
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
/*      */   public Short2ShortLinkedOpenHashMap clone() {
/*      */     Short2ShortLinkedOpenHashMap c;
/*      */     try {
/* 1872 */       c = (Short2ShortLinkedOpenHashMap)super.clone();
/* 1873 */     } catch (CloneNotSupportedException cantHappen) {
/* 1874 */       throw new InternalError();
/*      */     } 
/* 1876 */     c.keys = null;
/* 1877 */     c.values = null;
/* 1878 */     c.entries = null;
/* 1879 */     c.containsNullKey = this.containsNullKey;
/* 1880 */     c.key = (short[])this.key.clone();
/* 1881 */     c.value = (short[])this.value.clone();
/* 1882 */     c.link = (long[])this.link.clone();
/* 1883 */     return c;
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
/* 1896 */     int h = 0;
/* 1897 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1898 */       while (this.key[i] == 0)
/* 1899 */         i++; 
/* 1900 */       t = this.key[i];
/* 1901 */       t ^= this.value[i];
/* 1902 */       h += t;
/* 1903 */       i++;
/*      */     } 
/*      */     
/* 1906 */     if (this.containsNullKey)
/* 1907 */       h += this.value[this.n]; 
/* 1908 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1911 */     short[] key = this.key;
/* 1912 */     short[] value = this.value;
/* 1913 */     MapIterator i = new MapIterator();
/* 1914 */     s.defaultWriteObject();
/* 1915 */     for (int j = this.size; j-- != 0; ) {
/* 1916 */       int e = i.nextEntry();
/* 1917 */       s.writeShort(key[e]);
/* 1918 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1923 */     s.defaultReadObject();
/* 1924 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1925 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1926 */     this.mask = this.n - 1;
/* 1927 */     short[] key = this.key = new short[this.n + 1];
/* 1928 */     short[] value = this.value = new short[this.n + 1];
/* 1929 */     long[] link = this.link = new long[this.n + 1];
/* 1930 */     int prev = -1;
/* 1931 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1934 */     for (int i = this.size; i-- != 0; ) {
/* 1935 */       int pos; short k = s.readShort();
/* 1936 */       short v = s.readShort();
/* 1937 */       if (k == 0) {
/* 1938 */         pos = this.n;
/* 1939 */         this.containsNullKey = true;
/*      */       } else {
/* 1941 */         pos = HashCommon.mix(k) & this.mask;
/* 1942 */         while (key[pos] != 0)
/* 1943 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1945 */       key[pos] = k;
/* 1946 */       value[pos] = v;
/* 1947 */       if (this.first != -1) {
/* 1948 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1949 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1950 */         prev = pos; continue;
/*      */       } 
/* 1952 */       prev = this.first = pos;
/*      */       
/* 1954 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1957 */     this.last = prev;
/* 1958 */     if (prev != -1)
/*      */     {
/* 1960 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2ShortLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */