/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharListIterator;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToIntFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2CharLinkedOpenHashMap
/*      */   extends AbstractLong2CharSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient char[] value;
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
/*      */   protected transient Long2CharSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new long[this.n + 1];
/*  162 */     this.value = new char[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharLinkedOpenHashMap() {
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
/*      */   public Long2CharLinkedOpenHashMap(Map<? extends Long, ? extends Character> m, float f) {
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
/*      */   public Long2CharLinkedOpenHashMap(Map<? extends Long, ? extends Character> m) {
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
/*      */   public Long2CharLinkedOpenHashMap(Long2CharMap m, float f) {
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
/*      */   public Long2CharLinkedOpenHashMap(Long2CharMap m) {
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
/*      */   public Long2CharLinkedOpenHashMap(long[] k, char[] v, float f) {
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
/*      */   public Long2CharLinkedOpenHashMap(long[] k, char[] v) {
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
/*      */   private char removeEntry(int pos) {
/*  275 */     char oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     char oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Character> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  303 */     if (k == 0L) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  309 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  310 */       return -(pos + 1); 
/*  311 */     if (k == curr) {
/*  312 */       return pos;
/*      */     }
/*      */     while (true) {
/*  315 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  316 */         return -(pos + 1); 
/*  317 */       if (k == curr)
/*  318 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, char v) {
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
/*      */   public char put(long k, char v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     char oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
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
/*  362 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  364 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  366 */         if ((curr = key[pos]) == 0L) {
/*  367 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  370 */         int slot = (int)HashCommon.mix(curr) & this.mask;
/*  371 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  373 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  375 */       key[last] = curr;
/*  376 */       this.value[last] = this.value[pos];
/*  377 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public char remove(long k) {
/*  383 */     if (k == 0L) {
/*  384 */       if (this.containsNullKey)
/*  385 */         return removeNullEntry(); 
/*  386 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  389 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  392 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  393 */       return this.defRetValue; 
/*  394 */     if (k == curr)
/*  395 */       return removeEntry(pos); 
/*      */     while (true) {
/*  397 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  398 */         return this.defRetValue; 
/*  399 */       if (k == curr)
/*  400 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private char setValue(int pos, char v) {
/*  404 */     char oldValue = this.value[pos];
/*  405 */     this.value[pos] = v;
/*  406 */     return oldValue;
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
/*  417 */     if (this.size == 0)
/*  418 */       throw new NoSuchElementException(); 
/*  419 */     int pos = this.first;
/*      */     
/*  421 */     this.first = (int)this.link[pos];
/*  422 */     if (0 <= this.first)
/*      */     {
/*  424 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  426 */     this.size--;
/*  427 */     char v = this.value[pos];
/*  428 */     if (pos == this.n) {
/*  429 */       this.containsNullKey = false;
/*      */     } else {
/*  431 */       shiftKeys(pos);
/*  432 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  433 */       rehash(this.n / 2); 
/*  434 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeLastChar() {
/*  444 */     if (this.size == 0)
/*  445 */       throw new NoSuchElementException(); 
/*  446 */     int pos = this.last;
/*      */     
/*  448 */     this.last = (int)(this.link[pos] >>> 32L);
/*  449 */     if (0 <= this.last)
/*      */     {
/*  451 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  453 */     this.size--;
/*  454 */     char v = this.value[pos];
/*  455 */     if (pos == this.n) {
/*  456 */       this.containsNullKey = false;
/*      */     } else {
/*  458 */       shiftKeys(pos);
/*  459 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  460 */       rehash(this.n / 2); 
/*  461 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  464 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  466 */     if (this.last == i) {
/*  467 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  469 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  471 */       long linki = this.link[i];
/*  472 */       int prev = (int)(linki >>> 32L);
/*  473 */       int next = (int)linki;
/*  474 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  475 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  477 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  478 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  479 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  482 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  484 */     if (this.first == i) {
/*  485 */       this.first = (int)this.link[i];
/*      */       
/*  487 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  489 */       long linki = this.link[i];
/*  490 */       int prev = (int)(linki >>> 32L);
/*  491 */       int next = (int)linki;
/*  492 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  493 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  495 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  496 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  497 */     this.last = i;
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
/*      */   public char getAndMoveToFirst(long k) {
/*  509 */     if (k == 0L) {
/*  510 */       if (this.containsNullKey) {
/*  511 */         moveIndexToFirst(this.n);
/*  512 */         return this.value[this.n];
/*      */       } 
/*  514 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  517 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  520 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  521 */       return this.defRetValue; 
/*  522 */     if (k == curr) {
/*  523 */       moveIndexToFirst(pos);
/*  524 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  528 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  529 */         return this.defRetValue; 
/*  530 */       if (k == curr) {
/*  531 */         moveIndexToFirst(pos);
/*  532 */         return this.value[pos];
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
/*      */   public char getAndMoveToLast(long k) {
/*  546 */     if (k == 0L) {
/*  547 */       if (this.containsNullKey) {
/*  548 */         moveIndexToLast(this.n);
/*  549 */         return this.value[this.n];
/*      */       } 
/*  551 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  554 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  557 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  558 */       return this.defRetValue; 
/*  559 */     if (k == curr) {
/*  560 */       moveIndexToLast(pos);
/*  561 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  565 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  566 */         return this.defRetValue; 
/*  567 */       if (k == curr) {
/*  568 */         moveIndexToLast(pos);
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
/*      */ 
/*      */   
/*      */   public char putAndMoveToFirst(long k, char v) {
/*      */     int pos;
/*  586 */     if (k == 0L) {
/*  587 */       if (this.containsNullKey) {
/*  588 */         moveIndexToFirst(this.n);
/*  589 */         return setValue(this.n, v);
/*      */       } 
/*  591 */       this.containsNullKey = true;
/*  592 */       pos = this.n;
/*      */     } else {
/*      */       
/*  595 */       long[] key = this.key;
/*      */       long curr;
/*  597 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  598 */         if (curr == k) {
/*  599 */           moveIndexToFirst(pos);
/*  600 */           return setValue(pos, v);
/*      */         } 
/*  602 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  603 */           if (curr == k) {
/*  604 */             moveIndexToFirst(pos);
/*  605 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  609 */     }  this.key[pos] = k;
/*  610 */     this.value[pos] = v;
/*  611 */     if (this.size == 0) {
/*  612 */       this.first = this.last = pos;
/*      */       
/*  614 */       this.link[pos] = -1L;
/*      */     } else {
/*  616 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  617 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  618 */       this.first = pos;
/*      */     } 
/*  620 */     if (this.size++ >= this.maxFill) {
/*  621 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  624 */     return this.defRetValue;
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
/*      */   public char putAndMoveToLast(long k, char v) {
/*      */     int pos;
/*  639 */     if (k == 0L) {
/*  640 */       if (this.containsNullKey) {
/*  641 */         moveIndexToLast(this.n);
/*  642 */         return setValue(this.n, v);
/*      */       } 
/*  644 */       this.containsNullKey = true;
/*  645 */       pos = this.n;
/*      */     } else {
/*      */       
/*  648 */       long[] key = this.key;
/*      */       long curr;
/*  650 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  651 */         if (curr == k) {
/*  652 */           moveIndexToLast(pos);
/*  653 */           return setValue(pos, v);
/*      */         } 
/*  655 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  656 */           if (curr == k) {
/*  657 */             moveIndexToLast(pos);
/*  658 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  662 */     }  this.key[pos] = k;
/*  663 */     this.value[pos] = v;
/*  664 */     if (this.size == 0) {
/*  665 */       this.first = this.last = pos;
/*      */       
/*  667 */       this.link[pos] = -1L;
/*      */     } else {
/*  669 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  670 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  671 */       this.last = pos;
/*      */     } 
/*  673 */     if (this.size++ >= this.maxFill) {
/*  674 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  677 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char get(long k) {
/*  682 */     if (k == 0L) {
/*  683 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  685 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  688 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  689 */       return this.defRetValue; 
/*  690 */     if (k == curr) {
/*  691 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  694 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  695 */         return this.defRetValue; 
/*  696 */       if (k == curr) {
/*  697 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  703 */     if (k == 0L) {
/*  704 */       return this.containsNullKey;
/*      */     }
/*  706 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  709 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  710 */       return false; 
/*  711 */     if (k == curr) {
/*  712 */       return true;
/*      */     }
/*      */     while (true) {
/*  715 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  716 */         return false; 
/*  717 */       if (k == curr)
/*  718 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  723 */     char[] value = this.value;
/*  724 */     long[] key = this.key;
/*  725 */     if (this.containsNullKey && value[this.n] == v)
/*  726 */       return true; 
/*  727 */     for (int i = this.n; i-- != 0;) {
/*  728 */       if (key[i] != 0L && value[i] == v)
/*  729 */         return true; 
/*  730 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(long k, char defaultValue) {
/*  736 */     if (k == 0L) {
/*  737 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  739 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  742 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  743 */       return defaultValue; 
/*  744 */     if (k == curr) {
/*  745 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  748 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  749 */         return defaultValue; 
/*  750 */       if (k == curr) {
/*  751 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(long k, char v) {
/*  757 */     int pos = find(k);
/*  758 */     if (pos >= 0)
/*  759 */       return this.value[pos]; 
/*  760 */     insert(-pos - 1, k, v);
/*  761 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, char v) {
/*  767 */     if (k == 0L) {
/*  768 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  769 */         removeNullEntry();
/*  770 */         return true;
/*      */       } 
/*  772 */       return false;
/*      */     } 
/*      */     
/*  775 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  778 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  779 */       return false; 
/*  780 */     if (k == curr && v == this.value[pos]) {
/*  781 */       removeEntry(pos);
/*  782 */       return true;
/*      */     } 
/*      */     while (true) {
/*  785 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  786 */         return false; 
/*  787 */       if (k == curr && v == this.value[pos]) {
/*  788 */         removeEntry(pos);
/*  789 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, char oldValue, char v) {
/*  796 */     int pos = find(k);
/*  797 */     if (pos < 0 || oldValue != this.value[pos])
/*  798 */       return false; 
/*  799 */     this.value[pos] = v;
/*  800 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(long k, char v) {
/*  805 */     int pos = find(k);
/*  806 */     if (pos < 0)
/*  807 */       return this.defRetValue; 
/*  808 */     char oldValue = this.value[pos];
/*  809 */     this.value[pos] = v;
/*  810 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(long k, LongToIntFunction mappingFunction) {
/*  815 */     Objects.requireNonNull(mappingFunction);
/*  816 */     int pos = find(k);
/*  817 */     if (pos >= 0)
/*  818 */       return this.value[pos]; 
/*  819 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  820 */     insert(-pos - 1, k, newValue);
/*  821 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(long k, LongFunction<? extends Character> mappingFunction) {
/*  827 */     Objects.requireNonNull(mappingFunction);
/*  828 */     int pos = find(k);
/*  829 */     if (pos >= 0)
/*  830 */       return this.value[pos]; 
/*  831 */     Character newValue = mappingFunction.apply(k);
/*  832 */     if (newValue == null)
/*  833 */       return this.defRetValue; 
/*  834 */     char v = newValue.charValue();
/*  835 */     insert(-pos - 1, k, v);
/*  836 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(long k, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
/*  842 */     Objects.requireNonNull(remappingFunction);
/*  843 */     int pos = find(k);
/*  844 */     if (pos < 0)
/*  845 */       return this.defRetValue; 
/*  846 */     Character newValue = remappingFunction.apply(Long.valueOf(k), Character.valueOf(this.value[pos]));
/*  847 */     if (newValue == null) {
/*  848 */       if (k == 0L) {
/*  849 */         removeNullEntry();
/*      */       } else {
/*  851 */         removeEntry(pos);
/*  852 */       }  return this.defRetValue;
/*      */     } 
/*  854 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(long k, BiFunction<? super Long, ? super Character, ? extends Character> remappingFunction) {
/*  860 */     Objects.requireNonNull(remappingFunction);
/*  861 */     int pos = find(k);
/*  862 */     Character newValue = remappingFunction.apply(Long.valueOf(k), 
/*  863 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  864 */     if (newValue == null) {
/*  865 */       if (pos >= 0)
/*  866 */         if (k == 0L) {
/*  867 */           removeNullEntry();
/*      */         } else {
/*  869 */           removeEntry(pos);
/*      */         }  
/*  871 */       return this.defRetValue;
/*      */     } 
/*  873 */     char newVal = newValue.charValue();
/*  874 */     if (pos < 0) {
/*  875 */       insert(-pos - 1, k, newVal);
/*  876 */       return newVal;
/*      */     } 
/*  878 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(long k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  884 */     Objects.requireNonNull(remappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos < 0) {
/*  887 */       insert(-pos - 1, k, v);
/*  888 */       return v;
/*      */     } 
/*  890 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  891 */     if (newValue == null) {
/*  892 */       if (k == 0L) {
/*  893 */         removeNullEntry();
/*      */       } else {
/*  895 */         removeEntry(pos);
/*  896 */       }  return this.defRetValue;
/*      */     } 
/*  898 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  909 */     if (this.size == 0)
/*      */       return; 
/*  911 */     this.size = 0;
/*  912 */     this.containsNullKey = false;
/*  913 */     Arrays.fill(this.key, 0L);
/*  914 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  918 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  922 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2CharMap.Entry, Map.Entry<Long, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  934 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  940 */       return Long2CharLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  944 */       return Long2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  948 */       char oldValue = Long2CharLinkedOpenHashMap.this.value[this.index];
/*  949 */       Long2CharLinkedOpenHashMap.this.value[this.index] = v;
/*  950 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  960 */       return Long.valueOf(Long2CharLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  970 */       return Character.valueOf(Long2CharLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/*  980 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  985 */       if (!(o instanceof Map.Entry))
/*  986 */         return false; 
/*  987 */       Map.Entry<Long, Character> e = (Map.Entry<Long, Character>)o;
/*  988 */       return (Long2CharLinkedOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && Long2CharLinkedOpenHashMap.this.value[this.index] == ((Character)e.getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  992 */       return HashCommon.long2int(Long2CharLinkedOpenHashMap.this.key[this.index]) ^ Long2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  996 */       return Long2CharLinkedOpenHashMap.this.key[this.index] + "=>" + Long2CharLinkedOpenHashMap.this.value[this.index];
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
/* 1007 */     if (this.size == 0) {
/* 1008 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1011 */     if (this.first == i) {
/* 1012 */       this.first = (int)this.link[i];
/* 1013 */       if (0 <= this.first)
/*      */       {
/* 1015 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1019 */     if (this.last == i) {
/* 1020 */       this.last = (int)(this.link[i] >>> 32L);
/* 1021 */       if (0 <= this.last)
/*      */       {
/* 1023 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1027 */     long linki = this.link[i];
/* 1028 */     int prev = (int)(linki >>> 32L);
/* 1029 */     int next = (int)linki;
/* 1030 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1031 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1044 */     if (this.size == 1) {
/* 1045 */       this.first = this.last = d;
/*      */       
/* 1047 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1050 */     if (this.first == s) {
/* 1051 */       this.first = d;
/* 1052 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1053 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1056 */     if (this.last == s) {
/* 1057 */       this.last = d;
/* 1058 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1059 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1062 */     long links = this.link[s];
/* 1063 */     int prev = (int)(links >>> 32L);
/* 1064 */     int next = (int)links;
/* 1065 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1066 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1067 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLongKey() {
/* 1076 */     if (this.size == 0)
/* 1077 */       throw new NoSuchElementException(); 
/* 1078 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLongKey() {
/* 1087 */     if (this.size == 0)
/* 1088 */       throw new NoSuchElementException(); 
/* 1089 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharSortedMap tailMap(long from) {
/* 1098 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharSortedMap headMap(long to) {
/* 1107 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2CharSortedMap subMap(long from, long to) {
/* 1116 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/* 1125 */     return null;
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
/* 1140 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1146 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1151 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1157 */     int index = -1;
/*      */     protected MapIterator() {
/* 1159 */       this.next = Long2CharLinkedOpenHashMap.this.first;
/* 1160 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(long from) {
/* 1163 */       if (from == 0L) {
/* 1164 */         if (Long2CharLinkedOpenHashMap.this.containsNullKey) {
/* 1165 */           this.next = (int)Long2CharLinkedOpenHashMap.this.link[Long2CharLinkedOpenHashMap.this.n];
/* 1166 */           this.prev = Long2CharLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1169 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1171 */       if (Long2CharLinkedOpenHashMap.this.key[Long2CharLinkedOpenHashMap.this.last] == from) {
/* 1172 */         this.prev = Long2CharLinkedOpenHashMap.this.last;
/* 1173 */         this.index = Long2CharLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1177 */       int pos = (int)HashCommon.mix(from) & Long2CharLinkedOpenHashMap.this.mask;
/*      */       
/* 1179 */       while (Long2CharLinkedOpenHashMap.this.key[pos] != 0L) {
/* 1180 */         if (Long2CharLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1182 */           this.next = (int)Long2CharLinkedOpenHashMap.this.link[pos];
/* 1183 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1186 */         pos = pos + 1 & Long2CharLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1188 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1191 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1194 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1197 */       if (this.index >= 0)
/*      */         return; 
/* 1199 */       if (this.prev == -1) {
/* 1200 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1203 */       if (this.next == -1) {
/* 1204 */         this.index = Long2CharLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1207 */       int pos = Long2CharLinkedOpenHashMap.this.first;
/* 1208 */       this.index = 1;
/* 1209 */       while (pos != this.prev) {
/* 1210 */         pos = (int)Long2CharLinkedOpenHashMap.this.link[pos];
/* 1211 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1215 */       ensureIndexKnown();
/* 1216 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1219 */       ensureIndexKnown();
/* 1220 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1223 */       if (!hasNext())
/* 1224 */         throw new NoSuchElementException(); 
/* 1225 */       this.curr = this.next;
/* 1226 */       this.next = (int)Long2CharLinkedOpenHashMap.this.link[this.curr];
/* 1227 */       this.prev = this.curr;
/* 1228 */       if (this.index >= 0)
/* 1229 */         this.index++; 
/* 1230 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1233 */       if (!hasPrevious())
/* 1234 */         throw new NoSuchElementException(); 
/* 1235 */       this.curr = this.prev;
/* 1236 */       this.prev = (int)(Long2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1237 */       this.next = this.curr;
/* 1238 */       if (this.index >= 0)
/* 1239 */         this.index--; 
/* 1240 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1243 */       ensureIndexKnown();
/* 1244 */       if (this.curr == -1)
/* 1245 */         throw new IllegalStateException(); 
/* 1246 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1251 */         this.index--;
/* 1252 */         this.prev = (int)(Long2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1254 */         this.next = (int)Long2CharLinkedOpenHashMap.this.link[this.curr];
/* 1255 */       }  Long2CharLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1260 */       if (this.prev == -1) {
/* 1261 */         Long2CharLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1263 */         Long2CharLinkedOpenHashMap.this.link[this.prev] = Long2CharLinkedOpenHashMap.this.link[this.prev] ^ (Long2CharLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1264 */       }  if (this.next == -1) {
/* 1265 */         Long2CharLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1267 */         Long2CharLinkedOpenHashMap.this.link[this.next] = Long2CharLinkedOpenHashMap.this.link[this.next] ^ (Long2CharLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1268 */       }  int pos = this.curr;
/* 1269 */       this.curr = -1;
/* 1270 */       if (pos == Long2CharLinkedOpenHashMap.this.n) {
/* 1271 */         Long2CharLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1274 */         long[] key = Long2CharLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           long curr;
/*      */           int last;
/* 1278 */           pos = (last = pos) + 1 & Long2CharLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1280 */             if ((curr = key[pos]) == 0L) {
/* 1281 */               key[last] = 0L;
/*      */               return;
/*      */             } 
/* 1284 */             int slot = (int)HashCommon.mix(curr) & Long2CharLinkedOpenHashMap.this.mask;
/* 1285 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1287 */             pos = pos + 1 & Long2CharLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1289 */           key[last] = curr;
/* 1290 */           Long2CharLinkedOpenHashMap.this.value[last] = Long2CharLinkedOpenHashMap.this.value[pos];
/* 1291 */           if (this.next == pos)
/* 1292 */             this.next = last; 
/* 1293 */           if (this.prev == pos)
/* 1294 */             this.prev = last; 
/* 1295 */           Long2CharLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1300 */       int i = n;
/* 1301 */       while (i-- != 0 && hasNext())
/* 1302 */         nextEntry(); 
/* 1303 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1306 */       int i = n;
/* 1307 */       while (i-- != 0 && hasPrevious())
/* 1308 */         previousEntry(); 
/* 1309 */       return n - i - 1;
/*      */     }
/*      */     public void set(Long2CharMap.Entry ok) {
/* 1312 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Long2CharMap.Entry ok) {
/* 1315 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Long2CharMap.Entry> { private Long2CharLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(long from) {
/* 1323 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2CharLinkedOpenHashMap.MapEntry next() {
/* 1327 */       return this.entry = new Long2CharLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Long2CharLinkedOpenHashMap.MapEntry previous() {
/* 1331 */       return this.entry = new Long2CharLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1335 */       super.remove();
/* 1336 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1340 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Long2CharMap.Entry> { final Long2CharLinkedOpenHashMap.MapEntry entry = new Long2CharLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(long from) {
/* 1344 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2CharLinkedOpenHashMap.MapEntry next() {
/* 1348 */       this.entry.index = nextEntry();
/* 1349 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Long2CharLinkedOpenHashMap.MapEntry previous() {
/* 1353 */       this.entry.index = previousEntry();
/* 1354 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Long2CharMap.Entry> implements Long2CharSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Long2CharMap.Entry> iterator() {
/* 1360 */       return (ObjectBidirectionalIterator<Long2CharMap.Entry>)new Long2CharLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Long2CharMap.Entry> comparator() {
/* 1364 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2CharMap.Entry> subSet(Long2CharMap.Entry fromElement, Long2CharMap.Entry toElement) {
/* 1369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2CharMap.Entry> headSet(Long2CharMap.Entry toElement) {
/* 1373 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2CharMap.Entry> tailSet(Long2CharMap.Entry fromElement) {
/* 1377 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Long2CharMap.Entry first() {
/* 1381 */       if (Long2CharLinkedOpenHashMap.this.size == 0)
/* 1382 */         throw new NoSuchElementException(); 
/* 1383 */       return new Long2CharLinkedOpenHashMap.MapEntry(Long2CharLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Long2CharMap.Entry last() {
/* 1387 */       if (Long2CharLinkedOpenHashMap.this.size == 0)
/* 1388 */         throw new NoSuchElementException(); 
/* 1389 */       return new Long2CharLinkedOpenHashMap.MapEntry(Long2CharLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1394 */       if (!(o instanceof Map.Entry))
/* 1395 */         return false; 
/* 1396 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1397 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1398 */         return false; 
/* 1399 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1400 */         return false; 
/* 1401 */       long k = ((Long)e.getKey()).longValue();
/* 1402 */       char v = ((Character)e.getValue()).charValue();
/* 1403 */       if (k == 0L) {
/* 1404 */         return (Long2CharLinkedOpenHashMap.this.containsNullKey && Long2CharLinkedOpenHashMap.this.value[Long2CharLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1406 */       long[] key = Long2CharLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1409 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2CharLinkedOpenHashMap.this.mask]) == 0L)
/* 1410 */         return false; 
/* 1411 */       if (k == curr) {
/* 1412 */         return (Long2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1415 */         if ((curr = key[pos = pos + 1 & Long2CharLinkedOpenHashMap.this.mask]) == 0L)
/* 1416 */           return false; 
/* 1417 */         if (k == curr) {
/* 1418 */           return (Long2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1424 */       if (!(o instanceof Map.Entry))
/* 1425 */         return false; 
/* 1426 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1427 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1428 */         return false; 
/* 1429 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1430 */         return false; 
/* 1431 */       long k = ((Long)e.getKey()).longValue();
/* 1432 */       char v = ((Character)e.getValue()).charValue();
/* 1433 */       if (k == 0L) {
/* 1434 */         if (Long2CharLinkedOpenHashMap.this.containsNullKey && Long2CharLinkedOpenHashMap.this.value[Long2CharLinkedOpenHashMap.this.n] == v) {
/* 1435 */           Long2CharLinkedOpenHashMap.this.removeNullEntry();
/* 1436 */           return true;
/*      */         } 
/* 1438 */         return false;
/*      */       } 
/*      */       
/* 1441 */       long[] key = Long2CharLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1444 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2CharLinkedOpenHashMap.this.mask]) == 0L)
/* 1445 */         return false; 
/* 1446 */       if (curr == k) {
/* 1447 */         if (Long2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1448 */           Long2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1449 */           return true;
/*      */         } 
/* 1451 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1454 */         if ((curr = key[pos = pos + 1 & Long2CharLinkedOpenHashMap.this.mask]) == 0L)
/* 1455 */           return false; 
/* 1456 */         if (curr == k && 
/* 1457 */           Long2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1458 */           Long2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1459 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1466 */       return Long2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1470 */       Long2CharLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Long2CharMap.Entry> iterator(Long2CharMap.Entry from) {
/* 1485 */       return new Long2CharLinkedOpenHashMap.EntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2CharMap.Entry> fastIterator() {
/* 1496 */       return new Long2CharLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Long2CharMap.Entry> fastIterator(Long2CharMap.Entry from) {
/* 1511 */       return new Long2CharLinkedOpenHashMap.FastEntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2CharMap.Entry> consumer) {
/* 1516 */       for (int i = Long2CharLinkedOpenHashMap.this.size, next = Long2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1517 */         int curr = next;
/* 1518 */         next = (int)Long2CharLinkedOpenHashMap.this.link[curr];
/* 1519 */         consumer.accept(new AbstractLong2CharMap.BasicEntry(Long2CharLinkedOpenHashMap.this.key[curr], Long2CharLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2CharMap.Entry> consumer) {
/* 1525 */       AbstractLong2CharMap.BasicEntry entry = new AbstractLong2CharMap.BasicEntry();
/* 1526 */       for (int i = Long2CharLinkedOpenHashMap.this.size, next = Long2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1527 */         int curr = next;
/* 1528 */         next = (int)Long2CharLinkedOpenHashMap.this.link[curr];
/* 1529 */         entry.key = Long2CharLinkedOpenHashMap.this.key[curr];
/* 1530 */         entry.value = Long2CharLinkedOpenHashMap.this.value[curr];
/* 1531 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Long2CharSortedMap.FastSortedEntrySet long2CharEntrySet() {
/* 1537 */     if (this.entries == null)
/* 1538 */       this.entries = new MapEntrySet(); 
/* 1539 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements LongListIterator
/*      */   {
/*      */     public KeyIterator(long k) {
/* 1552 */       super(k);
/*      */     }
/*      */     
/*      */     public long previousLong() {
/* 1556 */       return Long2CharLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public long nextLong() {
/* 1563 */       return Long2CharLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSortedSet { private KeySet() {}
/*      */     
/*      */     public LongListIterator iterator(long from) {
/* 1569 */       return new Long2CharLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public LongListIterator iterator() {
/* 1573 */       return new Long2CharLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1578 */       if (Long2CharLinkedOpenHashMap.this.containsNullKey)
/* 1579 */         consumer.accept(Long2CharLinkedOpenHashMap.this.key[Long2CharLinkedOpenHashMap.this.n]); 
/* 1580 */       for (int pos = Long2CharLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1581 */         long k = Long2CharLinkedOpenHashMap.this.key[pos];
/* 1582 */         if (k != 0L)
/* 1583 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1588 */       return Long2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1592 */       return Long2CharLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1596 */       int oldSize = Long2CharLinkedOpenHashMap.this.size;
/* 1597 */       Long2CharLinkedOpenHashMap.this.remove(k);
/* 1598 */       return (Long2CharLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1602 */       Long2CharLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public long firstLong() {
/* 1606 */       if (Long2CharLinkedOpenHashMap.this.size == 0)
/* 1607 */         throw new NoSuchElementException(); 
/* 1608 */       return Long2CharLinkedOpenHashMap.this.key[Long2CharLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public long lastLong() {
/* 1612 */       if (Long2CharLinkedOpenHashMap.this.size == 0)
/* 1613 */         throw new NoSuchElementException(); 
/* 1614 */       return Long2CharLinkedOpenHashMap.this.key[Long2CharLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public LongComparator comparator() {
/* 1618 */       return null;
/*      */     }
/*      */     
/*      */     public LongSortedSet tailSet(long from) {
/* 1622 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet headSet(long to) {
/* 1626 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet subSet(long from, long to) {
/* 1630 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSortedSet keySet() {
/* 1635 */     if (this.keys == null)
/* 1636 */       this.keys = new KeySet(); 
/* 1637 */     return this.keys;
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
/* 1651 */       return Long2CharLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1658 */       return Long2CharLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1663 */     if (this.values == null)
/* 1664 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1667 */             return (CharIterator)new Long2CharLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1671 */             return Long2CharLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1675 */             return Long2CharLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1679 */             Long2CharLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1684 */             if (Long2CharLinkedOpenHashMap.this.containsNullKey)
/* 1685 */               consumer.accept(Long2CharLinkedOpenHashMap.this.value[Long2CharLinkedOpenHashMap.this.n]); 
/* 1686 */             for (int pos = Long2CharLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1687 */               if (Long2CharLinkedOpenHashMap.this.key[pos] != 0L)
/* 1688 */                 consumer.accept(Long2CharLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1691 */     return this.values;
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
/* 1708 */     return trim(this.size);
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
/* 1732 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1733 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1734 */       return true; 
/*      */     try {
/* 1736 */       rehash(l);
/* 1737 */     } catch (OutOfMemoryError cantDoIt) {
/* 1738 */       return false;
/*      */     } 
/* 1740 */     return true;
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
/* 1756 */     long[] key = this.key;
/* 1757 */     char[] value = this.value;
/* 1758 */     int mask = newN - 1;
/* 1759 */     long[] newKey = new long[newN + 1];
/* 1760 */     char[] newValue = new char[newN + 1];
/* 1761 */     int i = this.first, prev = -1, newPrev = -1;
/* 1762 */     long[] link = this.link;
/* 1763 */     long[] newLink = new long[newN + 1];
/* 1764 */     this.first = -1;
/* 1765 */     for (int j = this.size; j-- != 0; ) {
/* 1766 */       int pos; if (key[i] == 0L) {
/* 1767 */         pos = newN;
/*      */       } else {
/* 1769 */         pos = (int)HashCommon.mix(key[i]) & mask;
/* 1770 */         while (newKey[pos] != 0L)
/* 1771 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1773 */       newKey[pos] = key[i];
/* 1774 */       newValue[pos] = value[i];
/* 1775 */       if (prev != -1) {
/* 1776 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1777 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1778 */         newPrev = pos;
/*      */       } else {
/* 1780 */         newPrev = this.first = pos;
/*      */         
/* 1782 */         newLink[pos] = -1L;
/*      */       } 
/* 1784 */       int t = i;
/* 1785 */       i = (int)link[i];
/* 1786 */       prev = t;
/*      */     } 
/* 1788 */     this.link = newLink;
/* 1789 */     this.last = newPrev;
/* 1790 */     if (newPrev != -1)
/*      */     {
/* 1792 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1793 */     this.n = newN;
/* 1794 */     this.mask = mask;
/* 1795 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1796 */     this.key = newKey;
/* 1797 */     this.value = newValue;
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
/*      */   public Long2CharLinkedOpenHashMap clone() {
/*      */     Long2CharLinkedOpenHashMap c;
/*      */     try {
/* 1814 */       c = (Long2CharLinkedOpenHashMap)super.clone();
/* 1815 */     } catch (CloneNotSupportedException cantHappen) {
/* 1816 */       throw new InternalError();
/*      */     } 
/* 1818 */     c.keys = null;
/* 1819 */     c.values = null;
/* 1820 */     c.entries = null;
/* 1821 */     c.containsNullKey = this.containsNullKey;
/* 1822 */     c.key = (long[])this.key.clone();
/* 1823 */     c.value = (char[])this.value.clone();
/* 1824 */     c.link = (long[])this.link.clone();
/* 1825 */     return c;
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
/* 1838 */     int h = 0;
/* 1839 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1840 */       while (this.key[i] == 0L)
/* 1841 */         i++; 
/* 1842 */       t = HashCommon.long2int(this.key[i]);
/* 1843 */       t ^= this.value[i];
/* 1844 */       h += t;
/* 1845 */       i++;
/*      */     } 
/*      */     
/* 1848 */     if (this.containsNullKey)
/* 1849 */       h += this.value[this.n]; 
/* 1850 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1853 */     long[] key = this.key;
/* 1854 */     char[] value = this.value;
/* 1855 */     MapIterator i = new MapIterator();
/* 1856 */     s.defaultWriteObject();
/* 1857 */     for (int j = this.size; j-- != 0; ) {
/* 1858 */       int e = i.nextEntry();
/* 1859 */       s.writeLong(key[e]);
/* 1860 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1865 */     s.defaultReadObject();
/* 1866 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1867 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1868 */     this.mask = this.n - 1;
/* 1869 */     long[] key = this.key = new long[this.n + 1];
/* 1870 */     char[] value = this.value = new char[this.n + 1];
/* 1871 */     long[] link = this.link = new long[this.n + 1];
/* 1872 */     int prev = -1;
/* 1873 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1876 */     for (int i = this.size; i-- != 0; ) {
/* 1877 */       int pos; long k = s.readLong();
/* 1878 */       char v = s.readChar();
/* 1879 */       if (k == 0L) {
/* 1880 */         pos = this.n;
/* 1881 */         this.containsNullKey = true;
/*      */       } else {
/* 1883 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1884 */         while (key[pos] != 0L)
/* 1885 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1887 */       key[pos] = k;
/* 1888 */       value[pos] = v;
/* 1889 */       if (this.first != -1) {
/* 1890 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1891 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1892 */         prev = pos; continue;
/*      */       } 
/* 1894 */       prev = this.first = pos;
/*      */       
/* 1896 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1899 */     this.last = prev;
/* 1900 */     if (prev != -1)
/*      */     {
/* 1902 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2CharLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */