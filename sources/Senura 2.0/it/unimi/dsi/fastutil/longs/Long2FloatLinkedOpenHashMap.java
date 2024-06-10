/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ import java.util.function.LongToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2FloatLinkedOpenHashMap
/*      */   extends AbstractLong2FloatSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient float[] value;
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
/*      */   protected transient Long2FloatSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new long[this.n + 1];
/*  162 */     this.value = new float[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatLinkedOpenHashMap() {
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
/*      */   public Long2FloatLinkedOpenHashMap(Map<? extends Long, ? extends Float> m, float f) {
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
/*      */   public Long2FloatLinkedOpenHashMap(Map<? extends Long, ? extends Float> m) {
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
/*      */   public Long2FloatLinkedOpenHashMap(Long2FloatMap m, float f) {
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
/*      */   public Long2FloatLinkedOpenHashMap(Long2FloatMap m) {
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
/*      */   public Long2FloatLinkedOpenHashMap(long[] k, float[] v, float f) {
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
/*      */   public Long2FloatLinkedOpenHashMap(long[] k, float[] v) {
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
/*      */   private float removeEntry(int pos) {
/*  275 */     float oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private float removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     float oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends Float> m) {
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
/*      */   private void insert(int pos, long k, float v) {
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
/*      */   public float put(long k, float v) {
/*  342 */     int pos = find(k);
/*  343 */     if (pos < 0) {
/*  344 */       insert(-pos - 1, k, v);
/*  345 */       return this.defRetValue;
/*      */     } 
/*  347 */     float oldValue = this.value[pos];
/*  348 */     this.value[pos] = v;
/*  349 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  352 */     float oldValue = this.value[pos];
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
/*      */   public float addTo(long k, float incr) {
/*      */     int pos;
/*  374 */     if (k == 0L) {
/*  375 */       if (this.containsNullKey)
/*  376 */         return addToValue(this.n, incr); 
/*  377 */       pos = this.n;
/*  378 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  381 */       long[] key = this.key;
/*      */       long curr;
/*  383 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  384 */         if (curr == k)
/*  385 */           return addToValue(pos, incr); 
/*  386 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
/*  419 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  421 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  423 */         if ((curr = key[pos]) == 0L) {
/*  424 */           key[last] = 0L;
/*      */           return;
/*      */         } 
/*  427 */         int slot = (int)HashCommon.mix(curr) & this.mask;
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
/*      */   public float remove(long k) {
/*  440 */     if (k == 0L) {
/*  441 */       if (this.containsNullKey)
/*  442 */         return removeNullEntry(); 
/*  443 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  446 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  449 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  450 */       return this.defRetValue; 
/*  451 */     if (k == curr)
/*  452 */       return removeEntry(pos); 
/*      */     while (true) {
/*  454 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  455 */         return this.defRetValue; 
/*  456 */       if (k == curr)
/*  457 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private float setValue(int pos, float v) {
/*  461 */     float oldValue = this.value[pos];
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
/*      */   public float removeFirstFloat() {
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
/*  484 */     float v = this.value[pos];
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
/*      */   public float removeLastFloat() {
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
/*  511 */     float v = this.value[pos];
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
/*      */   public float getAndMoveToFirst(long k) {
/*  566 */     if (k == 0L) {
/*  567 */       if (this.containsNullKey) {
/*  568 */         moveIndexToFirst(this.n);
/*  569 */         return this.value[this.n];
/*      */       } 
/*  571 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  574 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  577 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  578 */       return this.defRetValue; 
/*  579 */     if (k == curr) {
/*  580 */       moveIndexToFirst(pos);
/*  581 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  585 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
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
/*      */   public float getAndMoveToLast(long k) {
/*  603 */     if (k == 0L) {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToLast(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  614 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  615 */       return this.defRetValue; 
/*  616 */     if (k == curr) {
/*  617 */       moveIndexToLast(pos);
/*  618 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  622 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
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
/*      */   public float putAndMoveToFirst(long k, float v) {
/*      */     int pos;
/*  643 */     if (k == 0L) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToFirst(this.n);
/*  646 */         return setValue(this.n, v);
/*      */       } 
/*  648 */       this.containsNullKey = true;
/*  649 */       pos = this.n;
/*      */     } else {
/*      */       
/*  652 */       long[] key = this.key;
/*      */       long curr;
/*  654 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  655 */         if (curr == k) {
/*  656 */           moveIndexToFirst(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
/*      */   public float putAndMoveToLast(long k, float v) {
/*      */     int pos;
/*  696 */     if (k == 0L) {
/*  697 */       if (this.containsNullKey) {
/*  698 */         moveIndexToLast(this.n);
/*  699 */         return setValue(this.n, v);
/*      */       } 
/*  701 */       this.containsNullKey = true;
/*  702 */       pos = this.n;
/*      */     } else {
/*      */       
/*  705 */       long[] key = this.key;
/*      */       long curr;
/*  707 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  708 */         if (curr == k) {
/*  709 */           moveIndexToLast(pos);
/*  710 */           return setValue(pos, v);
/*      */         } 
/*  712 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
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
/*      */   public float get(long k) {
/*  739 */     if (k == 0L) {
/*  740 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  742 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  745 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  746 */       return this.defRetValue; 
/*  747 */     if (k == curr) {
/*  748 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  751 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  752 */         return this.defRetValue; 
/*  753 */       if (k == curr) {
/*  754 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  760 */     if (k == 0L) {
/*  761 */       return this.containsNullKey;
/*      */     }
/*  763 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  766 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  767 */       return false; 
/*  768 */     if (k == curr) {
/*  769 */       return true;
/*      */     }
/*      */     while (true) {
/*  772 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  773 */         return false; 
/*  774 */       if (k == curr)
/*  775 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(float v) {
/*  780 */     float[] value = this.value;
/*  781 */     long[] key = this.key;
/*  782 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != 0L && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(long k, float defaultValue) {
/*  793 */     if (k == 0L) {
/*  794 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  796 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  799 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  800 */       return defaultValue; 
/*  801 */     if (k == curr) {
/*  802 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  805 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  806 */         return defaultValue; 
/*  807 */       if (k == curr) {
/*  808 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(long k, float v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, float v) {
/*  824 */     if (k == 0L) {
/*  825 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  826 */         removeNullEntry();
/*  827 */         return true;
/*      */       } 
/*  829 */       return false;
/*      */     } 
/*      */     
/*  832 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  835 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  836 */       return false; 
/*  837 */     if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  843 */         return false; 
/*  844 */       if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, float oldValue, float v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(long k, float v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     float oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(long k, LongToDoubleFunction mappingFunction) {
/*  872 */     Objects.requireNonNull(mappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos >= 0)
/*  875 */       return this.value[pos]; 
/*  876 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  877 */     insert(-pos - 1, k, newValue);
/*  878 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(long k, LongFunction<? extends Float> mappingFunction) {
/*  884 */     Objects.requireNonNull(mappingFunction);
/*  885 */     int pos = find(k);
/*  886 */     if (pos >= 0)
/*  887 */       return this.value[pos]; 
/*  888 */     Float newValue = mappingFunction.apply(k);
/*  889 */     if (newValue == null)
/*  890 */       return this.defRetValue; 
/*  891 */     float v = newValue.floatValue();
/*  892 */     insert(-pos - 1, k, v);
/*  893 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(long k, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Float newValue = remappingFunction.apply(Long.valueOf(k), Float.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == 0L) {
/*  906 */         removeNullEntry();
/*      */       } else {
/*  908 */         removeEntry(pos);
/*  909 */       }  return this.defRetValue;
/*      */     } 
/*  911 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(long k, BiFunction<? super Long, ? super Float, ? extends Float> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Float newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  920 */     if (newValue == null) {
/*  921 */       if (pos >= 0)
/*  922 */         if (k == 0L) {
/*  923 */           removeNullEntry();
/*      */         } else {
/*  925 */           removeEntry(pos);
/*      */         }  
/*  927 */       return this.defRetValue;
/*      */     } 
/*  929 */     float newVal = newValue.floatValue();
/*  930 */     if (pos < 0) {
/*  931 */       insert(-pos - 1, k, newVal);
/*  932 */       return newVal;
/*      */     } 
/*  934 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(long k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  940 */     Objects.requireNonNull(remappingFunction);
/*  941 */     int pos = find(k);
/*  942 */     if (pos < 0) {
/*  943 */       insert(-pos - 1, k, v);
/*  944 */       return v;
/*      */     } 
/*  946 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  947 */     if (newValue == null) {
/*  948 */       if (k == 0L) {
/*  949 */         removeNullEntry();
/*      */       } else {
/*  951 */         removeEntry(pos);
/*  952 */       }  return this.defRetValue;
/*      */     } 
/*  954 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  969 */     Arrays.fill(this.key, 0L);
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
/*      */     implements Long2FloatMap.Entry, Map.Entry<Long, Float>
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
/*      */     public long getLongKey() {
/*  996 */       return Long2FloatLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/* 1000 */       return Long2FloatLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/* 1004 */       float oldValue = Long2FloatLinkedOpenHashMap.this.value[this.index];
/* 1005 */       Long2FloatLinkedOpenHashMap.this.value[this.index] = v;
/* 1006 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/* 1016 */       return Long.valueOf(Long2FloatLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1026 */       return Float.valueOf(Long2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/* 1036 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1041 */       if (!(o instanceof Map.Entry))
/* 1042 */         return false; 
/* 1043 */       Map.Entry<Long, Float> e = (Map.Entry<Long, Float>)o;
/* 1044 */       return (Long2FloatLinkedOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && 
/* 1045 */         Float.floatToIntBits(Long2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1049 */       return HashCommon.long2int(Long2FloatLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1050 */         HashCommon.float2int(Long2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1054 */       return Long2FloatLinkedOpenHashMap.this.key[this.index] + "=>" + Long2FloatLinkedOpenHashMap.this.value[this.index];
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
/* 1065 */     if (this.size == 0) {
/* 1066 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1069 */     if (this.first == i) {
/* 1070 */       this.first = (int)this.link[i];
/* 1071 */       if (0 <= this.first)
/*      */       {
/* 1073 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1077 */     if (this.last == i) {
/* 1078 */       this.last = (int)(this.link[i] >>> 32L);
/* 1079 */       if (0 <= this.last)
/*      */       {
/* 1081 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1085 */     long linki = this.link[i];
/* 1086 */     int prev = (int)(linki >>> 32L);
/* 1087 */     int next = (int)linki;
/* 1088 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1089 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1102 */     if (this.size == 1) {
/* 1103 */       this.first = this.last = d;
/*      */       
/* 1105 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1108 */     if (this.first == s) {
/* 1109 */       this.first = d;
/* 1110 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1111 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1114 */     if (this.last == s) {
/* 1115 */       this.last = d;
/* 1116 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1117 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1120 */     long links = this.link[s];
/* 1121 */     int prev = (int)(links >>> 32L);
/* 1122 */     int next = (int)links;
/* 1123 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1124 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1125 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLongKey() {
/* 1134 */     if (this.size == 0)
/* 1135 */       throw new NoSuchElementException(); 
/* 1136 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLongKey() {
/* 1145 */     if (this.size == 0)
/* 1146 */       throw new NoSuchElementException(); 
/* 1147 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatSortedMap tailMap(long from) {
/* 1156 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatSortedMap headMap(long to) {
/* 1165 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2FloatSortedMap subMap(long from, long to) {
/* 1174 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/* 1183 */     return null;
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
/* 1198 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1204 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1209 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1215 */     int index = -1;
/*      */     protected MapIterator() {
/* 1217 */       this.next = Long2FloatLinkedOpenHashMap.this.first;
/* 1218 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(long from) {
/* 1221 */       if (from == 0L) {
/* 1222 */         if (Long2FloatLinkedOpenHashMap.this.containsNullKey) {
/* 1223 */           this.next = (int)Long2FloatLinkedOpenHashMap.this.link[Long2FloatLinkedOpenHashMap.this.n];
/* 1224 */           this.prev = Long2FloatLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1227 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1229 */       if (Long2FloatLinkedOpenHashMap.this.key[Long2FloatLinkedOpenHashMap.this.last] == from) {
/* 1230 */         this.prev = Long2FloatLinkedOpenHashMap.this.last;
/* 1231 */         this.index = Long2FloatLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1235 */       int pos = (int)HashCommon.mix(from) & Long2FloatLinkedOpenHashMap.this.mask;
/*      */       
/* 1237 */       while (Long2FloatLinkedOpenHashMap.this.key[pos] != 0L) {
/* 1238 */         if (Long2FloatLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1240 */           this.next = (int)Long2FloatLinkedOpenHashMap.this.link[pos];
/* 1241 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1244 */         pos = pos + 1 & Long2FloatLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1246 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1249 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1252 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1255 */       if (this.index >= 0)
/*      */         return; 
/* 1257 */       if (this.prev == -1) {
/* 1258 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1261 */       if (this.next == -1) {
/* 1262 */         this.index = Long2FloatLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1265 */       int pos = Long2FloatLinkedOpenHashMap.this.first;
/* 1266 */       this.index = 1;
/* 1267 */       while (pos != this.prev) {
/* 1268 */         pos = (int)Long2FloatLinkedOpenHashMap.this.link[pos];
/* 1269 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1273 */       ensureIndexKnown();
/* 1274 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1277 */       ensureIndexKnown();
/* 1278 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1281 */       if (!hasNext())
/* 1282 */         throw new NoSuchElementException(); 
/* 1283 */       this.curr = this.next;
/* 1284 */       this.next = (int)Long2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1285 */       this.prev = this.curr;
/* 1286 */       if (this.index >= 0)
/* 1287 */         this.index++; 
/* 1288 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1291 */       if (!hasPrevious())
/* 1292 */         throw new NoSuchElementException(); 
/* 1293 */       this.curr = this.prev;
/* 1294 */       this.prev = (int)(Long2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1295 */       this.next = this.curr;
/* 1296 */       if (this.index >= 0)
/* 1297 */         this.index--; 
/* 1298 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1301 */       ensureIndexKnown();
/* 1302 */       if (this.curr == -1)
/* 1303 */         throw new IllegalStateException(); 
/* 1304 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1309 */         this.index--;
/* 1310 */         this.prev = (int)(Long2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1312 */         this.next = (int)Long2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1313 */       }  Long2FloatLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1318 */       if (this.prev == -1) {
/* 1319 */         Long2FloatLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1321 */         Long2FloatLinkedOpenHashMap.this.link[this.prev] = Long2FloatLinkedOpenHashMap.this.link[this.prev] ^ (Long2FloatLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1322 */       }  if (this.next == -1) {
/* 1323 */         Long2FloatLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1325 */         Long2FloatLinkedOpenHashMap.this.link[this.next] = Long2FloatLinkedOpenHashMap.this.link[this.next] ^ (Long2FloatLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1326 */       }  int pos = this.curr;
/* 1327 */       this.curr = -1;
/* 1328 */       if (pos == Long2FloatLinkedOpenHashMap.this.n) {
/* 1329 */         Long2FloatLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1332 */         long[] key = Long2FloatLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           long curr;
/*      */           int last;
/* 1336 */           pos = (last = pos) + 1 & Long2FloatLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1338 */             if ((curr = key[pos]) == 0L) {
/* 1339 */               key[last] = 0L;
/*      */               return;
/*      */             } 
/* 1342 */             int slot = (int)HashCommon.mix(curr) & Long2FloatLinkedOpenHashMap.this.mask;
/* 1343 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1345 */             pos = pos + 1 & Long2FloatLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1347 */           key[last] = curr;
/* 1348 */           Long2FloatLinkedOpenHashMap.this.value[last] = Long2FloatLinkedOpenHashMap.this.value[pos];
/* 1349 */           if (this.next == pos)
/* 1350 */             this.next = last; 
/* 1351 */           if (this.prev == pos)
/* 1352 */             this.prev = last; 
/* 1353 */           Long2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1358 */       int i = n;
/* 1359 */       while (i-- != 0 && hasNext())
/* 1360 */         nextEntry(); 
/* 1361 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1364 */       int i = n;
/* 1365 */       while (i-- != 0 && hasPrevious())
/* 1366 */         previousEntry(); 
/* 1367 */       return n - i - 1;
/*      */     }
/*      */     public void set(Long2FloatMap.Entry ok) {
/* 1370 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Long2FloatMap.Entry ok) {
/* 1373 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Long2FloatMap.Entry> { private Long2FloatLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(long from) {
/* 1381 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2FloatLinkedOpenHashMap.MapEntry next() {
/* 1385 */       return this.entry = new Long2FloatLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Long2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1389 */       return this.entry = new Long2FloatLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1393 */       super.remove();
/* 1394 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1398 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Long2FloatMap.Entry> { final Long2FloatLinkedOpenHashMap.MapEntry entry = new Long2FloatLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(long from) {
/* 1402 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2FloatLinkedOpenHashMap.MapEntry next() {
/* 1406 */       this.entry.index = nextEntry();
/* 1407 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Long2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1411 */       this.entry.index = previousEntry();
/* 1412 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Long2FloatMap.Entry> implements Long2FloatSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Long2FloatMap.Entry> iterator() {
/* 1418 */       return (ObjectBidirectionalIterator<Long2FloatMap.Entry>)new Long2FloatLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Long2FloatMap.Entry> comparator() {
/* 1422 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2FloatMap.Entry> subSet(Long2FloatMap.Entry fromElement, Long2FloatMap.Entry toElement) {
/* 1427 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2FloatMap.Entry> headSet(Long2FloatMap.Entry toElement) {
/* 1431 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2FloatMap.Entry> tailSet(Long2FloatMap.Entry fromElement) {
/* 1435 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Long2FloatMap.Entry first() {
/* 1439 */       if (Long2FloatLinkedOpenHashMap.this.size == 0)
/* 1440 */         throw new NoSuchElementException(); 
/* 1441 */       return new Long2FloatLinkedOpenHashMap.MapEntry(Long2FloatLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Long2FloatMap.Entry last() {
/* 1445 */       if (Long2FloatLinkedOpenHashMap.this.size == 0)
/* 1446 */         throw new NoSuchElementException(); 
/* 1447 */       return new Long2FloatLinkedOpenHashMap.MapEntry(Long2FloatLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1452 */       if (!(o instanceof Map.Entry))
/* 1453 */         return false; 
/* 1454 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1455 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1456 */         return false; 
/* 1457 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1458 */         return false; 
/* 1459 */       long k = ((Long)e.getKey()).longValue();
/* 1460 */       float v = ((Float)e.getValue()).floatValue();
/* 1461 */       if (k == 0L) {
/* 1462 */         return (Long2FloatLinkedOpenHashMap.this.containsNullKey && 
/* 1463 */           Float.floatToIntBits(Long2FloatLinkedOpenHashMap.this.value[Long2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1465 */       long[] key = Long2FloatLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1468 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2FloatLinkedOpenHashMap.this.mask]) == 0L)
/* 1469 */         return false; 
/* 1470 */       if (k == curr) {
/* 1471 */         return (Float.floatToIntBits(Long2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1474 */         if ((curr = key[pos = pos + 1 & Long2FloatLinkedOpenHashMap.this.mask]) == 0L)
/* 1475 */           return false; 
/* 1476 */         if (k == curr) {
/* 1477 */           return (Float.floatToIntBits(Long2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1483 */       if (!(o instanceof Map.Entry))
/* 1484 */         return false; 
/* 1485 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1486 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1487 */         return false; 
/* 1488 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1489 */         return false; 
/* 1490 */       long k = ((Long)e.getKey()).longValue();
/* 1491 */       float v = ((Float)e.getValue()).floatValue();
/* 1492 */       if (k == 0L) {
/* 1493 */         if (Long2FloatLinkedOpenHashMap.this.containsNullKey && Float.floatToIntBits(Long2FloatLinkedOpenHashMap.this.value[Long2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1494 */           Long2FloatLinkedOpenHashMap.this.removeNullEntry();
/* 1495 */           return true;
/*      */         } 
/* 1497 */         return false;
/*      */       } 
/*      */       
/* 1500 */       long[] key = Long2FloatLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1503 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2FloatLinkedOpenHashMap.this.mask]) == 0L)
/* 1504 */         return false; 
/* 1505 */       if (curr == k) {
/* 1506 */         if (Float.floatToIntBits(Long2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1507 */           Long2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1508 */           return true;
/*      */         } 
/* 1510 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1513 */         if ((curr = key[pos = pos + 1 & Long2FloatLinkedOpenHashMap.this.mask]) == 0L)
/* 1514 */           return false; 
/* 1515 */         if (curr == k && 
/* 1516 */           Float.floatToIntBits(Long2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1517 */           Long2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1518 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1525 */       return Long2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1529 */       Long2FloatLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Long2FloatMap.Entry> iterator(Long2FloatMap.Entry from) {
/* 1544 */       return new Long2FloatLinkedOpenHashMap.EntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2FloatMap.Entry> fastIterator() {
/* 1555 */       return new Long2FloatLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Long2FloatMap.Entry> fastIterator(Long2FloatMap.Entry from) {
/* 1570 */       return new Long2FloatLinkedOpenHashMap.FastEntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2FloatMap.Entry> consumer) {
/* 1575 */       for (int i = Long2FloatLinkedOpenHashMap.this.size, next = Long2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1576 */         int curr = next;
/* 1577 */         next = (int)Long2FloatLinkedOpenHashMap.this.link[curr];
/* 1578 */         consumer.accept(new AbstractLong2FloatMap.BasicEntry(Long2FloatLinkedOpenHashMap.this.key[curr], Long2FloatLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2FloatMap.Entry> consumer) {
/* 1584 */       AbstractLong2FloatMap.BasicEntry entry = new AbstractLong2FloatMap.BasicEntry();
/* 1585 */       for (int i = Long2FloatLinkedOpenHashMap.this.size, next = Long2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1586 */         int curr = next;
/* 1587 */         next = (int)Long2FloatLinkedOpenHashMap.this.link[curr];
/* 1588 */         entry.key = Long2FloatLinkedOpenHashMap.this.key[curr];
/* 1589 */         entry.value = Long2FloatLinkedOpenHashMap.this.value[curr];
/* 1590 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Long2FloatSortedMap.FastSortedEntrySet long2FloatEntrySet() {
/* 1596 */     if (this.entries == null)
/* 1597 */       this.entries = new MapEntrySet(); 
/* 1598 */     return this.entries;
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
/* 1611 */       super(k);
/*      */     }
/*      */     
/*      */     public long previousLong() {
/* 1615 */       return Long2FloatLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public long nextLong() {
/* 1622 */       return Long2FloatLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSortedSet { private KeySet() {}
/*      */     
/*      */     public LongListIterator iterator(long from) {
/* 1628 */       return new Long2FloatLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public LongListIterator iterator() {
/* 1632 */       return new Long2FloatLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1637 */       if (Long2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1638 */         consumer.accept(Long2FloatLinkedOpenHashMap.this.key[Long2FloatLinkedOpenHashMap.this.n]); 
/* 1639 */       for (int pos = Long2FloatLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1640 */         long k = Long2FloatLinkedOpenHashMap.this.key[pos];
/* 1641 */         if (k != 0L)
/* 1642 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1647 */       return Long2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1651 */       return Long2FloatLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1655 */       int oldSize = Long2FloatLinkedOpenHashMap.this.size;
/* 1656 */       Long2FloatLinkedOpenHashMap.this.remove(k);
/* 1657 */       return (Long2FloatLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1661 */       Long2FloatLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public long firstLong() {
/* 1665 */       if (Long2FloatLinkedOpenHashMap.this.size == 0)
/* 1666 */         throw new NoSuchElementException(); 
/* 1667 */       return Long2FloatLinkedOpenHashMap.this.key[Long2FloatLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public long lastLong() {
/* 1671 */       if (Long2FloatLinkedOpenHashMap.this.size == 0)
/* 1672 */         throw new NoSuchElementException(); 
/* 1673 */       return Long2FloatLinkedOpenHashMap.this.key[Long2FloatLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public LongComparator comparator() {
/* 1677 */       return null;
/*      */     }
/*      */     
/*      */     public LongSortedSet tailSet(long from) {
/* 1681 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet headSet(long to) {
/* 1685 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet subSet(long from, long to) {
/* 1689 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSortedSet keySet() {
/* 1694 */     if (this.keys == null)
/* 1695 */       this.keys = new KeySet(); 
/* 1696 */     return this.keys;
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
/* 1710 */       return Long2FloatLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1717 */       return Long2FloatLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1722 */     if (this.values == null)
/* 1723 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1726 */             return (FloatIterator)new Long2FloatLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1730 */             return Long2FloatLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1734 */             return Long2FloatLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1738 */             Long2FloatLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1743 */             if (Long2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1744 */               consumer.accept(Long2FloatLinkedOpenHashMap.this.value[Long2FloatLinkedOpenHashMap.this.n]); 
/* 1745 */             for (int pos = Long2FloatLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1746 */               if (Long2FloatLinkedOpenHashMap.this.key[pos] != 0L)
/* 1747 */                 consumer.accept(Long2FloatLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1750 */     return this.values;
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
/* 1767 */     return trim(this.size);
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
/* 1791 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1792 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1793 */       return true; 
/*      */     try {
/* 1795 */       rehash(l);
/* 1796 */     } catch (OutOfMemoryError cantDoIt) {
/* 1797 */       return false;
/*      */     } 
/* 1799 */     return true;
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
/* 1815 */     long[] key = this.key;
/* 1816 */     float[] value = this.value;
/* 1817 */     int mask = newN - 1;
/* 1818 */     long[] newKey = new long[newN + 1];
/* 1819 */     float[] newValue = new float[newN + 1];
/* 1820 */     int i = this.first, prev = -1, newPrev = -1;
/* 1821 */     long[] link = this.link;
/* 1822 */     long[] newLink = new long[newN + 1];
/* 1823 */     this.first = -1;
/* 1824 */     for (int j = this.size; j-- != 0; ) {
/* 1825 */       int pos; if (key[i] == 0L) {
/* 1826 */         pos = newN;
/*      */       } else {
/* 1828 */         pos = (int)HashCommon.mix(key[i]) & mask;
/* 1829 */         while (newKey[pos] != 0L)
/* 1830 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1832 */       newKey[pos] = key[i];
/* 1833 */       newValue[pos] = value[i];
/* 1834 */       if (prev != -1) {
/* 1835 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1836 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1837 */         newPrev = pos;
/*      */       } else {
/* 1839 */         newPrev = this.first = pos;
/*      */         
/* 1841 */         newLink[pos] = -1L;
/*      */       } 
/* 1843 */       int t = i;
/* 1844 */       i = (int)link[i];
/* 1845 */       prev = t;
/*      */     } 
/* 1847 */     this.link = newLink;
/* 1848 */     this.last = newPrev;
/* 1849 */     if (newPrev != -1)
/*      */     {
/* 1851 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1852 */     this.n = newN;
/* 1853 */     this.mask = mask;
/* 1854 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1855 */     this.key = newKey;
/* 1856 */     this.value = newValue;
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
/*      */   public Long2FloatLinkedOpenHashMap clone() {
/*      */     Long2FloatLinkedOpenHashMap c;
/*      */     try {
/* 1873 */       c = (Long2FloatLinkedOpenHashMap)super.clone();
/* 1874 */     } catch (CloneNotSupportedException cantHappen) {
/* 1875 */       throw new InternalError();
/*      */     } 
/* 1877 */     c.keys = null;
/* 1878 */     c.values = null;
/* 1879 */     c.entries = null;
/* 1880 */     c.containsNullKey = this.containsNullKey;
/* 1881 */     c.key = (long[])this.key.clone();
/* 1882 */     c.value = (float[])this.value.clone();
/* 1883 */     c.link = (long[])this.link.clone();
/* 1884 */     return c;
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
/* 1897 */     int h = 0;
/* 1898 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1899 */       while (this.key[i] == 0L)
/* 1900 */         i++; 
/* 1901 */       t = HashCommon.long2int(this.key[i]);
/* 1902 */       t ^= HashCommon.float2int(this.value[i]);
/* 1903 */       h += t;
/* 1904 */       i++;
/*      */     } 
/*      */     
/* 1907 */     if (this.containsNullKey)
/* 1908 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1909 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1912 */     long[] key = this.key;
/* 1913 */     float[] value = this.value;
/* 1914 */     MapIterator i = new MapIterator();
/* 1915 */     s.defaultWriteObject();
/* 1916 */     for (int j = this.size; j-- != 0; ) {
/* 1917 */       int e = i.nextEntry();
/* 1918 */       s.writeLong(key[e]);
/* 1919 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1924 */     s.defaultReadObject();
/* 1925 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1926 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1927 */     this.mask = this.n - 1;
/* 1928 */     long[] key = this.key = new long[this.n + 1];
/* 1929 */     float[] value = this.value = new float[this.n + 1];
/* 1930 */     long[] link = this.link = new long[this.n + 1];
/* 1931 */     int prev = -1;
/* 1932 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1935 */     for (int i = this.size; i-- != 0; ) {
/* 1936 */       int pos; long k = s.readLong();
/* 1937 */       float v = s.readFloat();
/* 1938 */       if (k == 0L) {
/* 1939 */         pos = this.n;
/* 1940 */         this.containsNullKey = true;
/*      */       } else {
/* 1942 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1943 */         while (key[pos] != 0L)
/* 1944 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1946 */       key[pos] = k;
/* 1947 */       value[pos] = v;
/* 1948 */       if (this.first != -1) {
/* 1949 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1950 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1951 */         prev = pos; continue;
/*      */       } 
/* 1953 */       prev = this.first = pos;
/*      */       
/* 1955 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1958 */     this.last = prev;
/* 1959 */     if (prev != -1)
/*      */     {
/* 1961 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2FloatLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */