/*      */ package it.unimi.dsi.fastutil.shorts;
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
/*      */ import java.util.function.IntConsumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Short2FloatLinkedOpenHashMap
/*      */   extends AbstractShort2FloatSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient short[] key;
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
/*      */   protected transient Short2FloatSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ShortSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2FloatLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new short[this.n + 1];
/*  162 */     this.value = new float[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2FloatLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2FloatLinkedOpenHashMap() {
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
/*      */   public Short2FloatLinkedOpenHashMap(Map<? extends Short, ? extends Float> m, float f) {
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
/*      */   public Short2FloatLinkedOpenHashMap(Map<? extends Short, ? extends Float> m) {
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
/*      */   public Short2FloatLinkedOpenHashMap(Short2FloatMap m, float f) {
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
/*      */   public Short2FloatLinkedOpenHashMap(Short2FloatMap m) {
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
/*      */   public Short2FloatLinkedOpenHashMap(short[] k, float[] v, float f) {
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
/*      */   public Short2FloatLinkedOpenHashMap(short[] k, float[] v) {
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
/*      */   public void putAll(Map<? extends Short, ? extends Float> m) {
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
/*      */   private void insert(int pos, short k, float v) {
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
/*      */   public float put(short k, float v) {
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
/*      */   public float addTo(short k, float incr) {
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
/*      */   public float remove(short k) {
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
/*      */   public float getAndMoveToFirst(short k) {
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
/*      */   public float getAndMoveToLast(short k) {
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
/*      */   public float putAndMoveToFirst(short k, float v) {
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
/*      */   public float putAndMoveToLast(short k, float v) {
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
/*      */   public float get(short k) {
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
/*      */   public boolean containsValue(float v) {
/*  780 */     float[] value = this.value;
/*  781 */     short[] key = this.key;
/*  782 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  783 */       return true; 
/*  784 */     for (int i = this.n; i-- != 0;) {
/*  785 */       if (key[i] != 0 && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  786 */         return true; 
/*  787 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(short k, float defaultValue) {
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
/*      */   public float putIfAbsent(short k, float v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(short k, float v) {
/*  824 */     if (k == 0) {
/*  825 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
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
/*  837 */     if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  838 */       removeEntry(pos);
/*  839 */       return true;
/*      */     } 
/*      */     while (true) {
/*  842 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0)
/*  843 */         return false; 
/*  844 */       if (k == curr && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  845 */         removeEntry(pos);
/*  846 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(short k, float oldValue, float v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(short k, float v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     float oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(short k, IntToDoubleFunction mappingFunction) {
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
/*      */   public float computeIfAbsentNullable(short k, IntFunction<? extends Float> mappingFunction) {
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
/*      */   public float computeIfPresent(short k, BiFunction<? super Short, ? super Float, ? extends Float> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Float newValue = remappingFunction.apply(Short.valueOf(k), Float.valueOf(this.value[pos]));
/*  904 */     if (newValue == null) {
/*  905 */       if (k == 0) {
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
/*      */   public float compute(short k, BiFunction<? super Short, ? super Float, ? extends Float> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Float newValue = remappingFunction.apply(Short.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  920 */     if (newValue == null) {
/*  921 */       if (pos >= 0)
/*  922 */         if (k == 0) {
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
/*      */   public float merge(short k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  940 */     Objects.requireNonNull(remappingFunction);
/*  941 */     int pos = find(k);
/*  942 */     if (pos < 0) {
/*  943 */       insert(-pos - 1, k, v);
/*  944 */       return v;
/*      */     } 
/*  946 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  947 */     if (newValue == null) {
/*  948 */       if (k == 0) {
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
/*      */     implements Short2FloatMap.Entry, Map.Entry<Short, Float>
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
/*  996 */       return Short2FloatLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/* 1000 */       return Short2FloatLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/* 1004 */       float oldValue = Short2FloatLinkedOpenHashMap.this.value[this.index];
/* 1005 */       Short2FloatLinkedOpenHashMap.this.value[this.index] = v;
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
/* 1016 */       return Short.valueOf(Short2FloatLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1026 */       return Float.valueOf(Short2FloatLinkedOpenHashMap.this.value[this.index]);
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
/* 1043 */       Map.Entry<Short, Float> e = (Map.Entry<Short, Float>)o;
/* 1044 */       return (Short2FloatLinkedOpenHashMap.this.key[this.index] == ((Short)e.getKey()).shortValue() && 
/* 1045 */         Float.floatToIntBits(Short2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1049 */       return Short2FloatLinkedOpenHashMap.this.key[this.index] ^ HashCommon.float2int(Short2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1053 */       return Short2FloatLinkedOpenHashMap.this.key[this.index] + "=>" + Short2FloatLinkedOpenHashMap.this.value[this.index];
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
/*      */   public Short2FloatSortedMap tailMap(short from) {
/* 1155 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2FloatSortedMap headMap(short to) {
/* 1164 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Short2FloatSortedMap subMap(short from, short to) {
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
/* 1216 */       this.next = Short2FloatLinkedOpenHashMap.this.first;
/* 1217 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(short from) {
/* 1220 */       if (from == 0) {
/* 1221 */         if (Short2FloatLinkedOpenHashMap.this.containsNullKey) {
/* 1222 */           this.next = (int)Short2FloatLinkedOpenHashMap.this.link[Short2FloatLinkedOpenHashMap.this.n];
/* 1223 */           this.prev = Short2FloatLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1226 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1228 */       if (Short2FloatLinkedOpenHashMap.this.key[Short2FloatLinkedOpenHashMap.this.last] == from) {
/* 1229 */         this.prev = Short2FloatLinkedOpenHashMap.this.last;
/* 1230 */         this.index = Short2FloatLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1234 */       int pos = HashCommon.mix(from) & Short2FloatLinkedOpenHashMap.this.mask;
/*      */       
/* 1236 */       while (Short2FloatLinkedOpenHashMap.this.key[pos] != 0) {
/* 1237 */         if (Short2FloatLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1239 */           this.next = (int)Short2FloatLinkedOpenHashMap.this.link[pos];
/* 1240 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1243 */         pos = pos + 1 & Short2FloatLinkedOpenHashMap.this.mask;
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
/* 1261 */         this.index = Short2FloatLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1264 */       int pos = Short2FloatLinkedOpenHashMap.this.first;
/* 1265 */       this.index = 1;
/* 1266 */       while (pos != this.prev) {
/* 1267 */         pos = (int)Short2FloatLinkedOpenHashMap.this.link[pos];
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
/* 1283 */       this.next = (int)Short2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1284 */       this.prev = this.curr;
/* 1285 */       if (this.index >= 0)
/* 1286 */         this.index++; 
/* 1287 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1290 */       if (!hasPrevious())
/* 1291 */         throw new NoSuchElementException(); 
/* 1292 */       this.curr = this.prev;
/* 1293 */       this.prev = (int)(Short2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1309 */         this.prev = (int)(Short2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1311 */         this.next = (int)Short2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1312 */       }  Short2FloatLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       if (this.prev == -1) {
/* 1318 */         Short2FloatLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1320 */         Short2FloatLinkedOpenHashMap.this.link[this.prev] = Short2FloatLinkedOpenHashMap.this.link[this.prev] ^ (Short2FloatLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1321 */       }  if (this.next == -1) {
/* 1322 */         Short2FloatLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1324 */         Short2FloatLinkedOpenHashMap.this.link[this.next] = Short2FloatLinkedOpenHashMap.this.link[this.next] ^ (Short2FloatLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1325 */       }  int pos = this.curr;
/* 1326 */       this.curr = -1;
/* 1327 */       if (pos == Short2FloatLinkedOpenHashMap.this.n) {
/* 1328 */         Short2FloatLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1331 */         short[] key = Short2FloatLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           short curr;
/*      */           int last;
/* 1335 */           pos = (last = pos) + 1 & Short2FloatLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1337 */             if ((curr = key[pos]) == 0) {
/* 1338 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1341 */             int slot = HashCommon.mix(curr) & Short2FloatLinkedOpenHashMap.this.mask;
/* 1342 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1344 */             pos = pos + 1 & Short2FloatLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1346 */           key[last] = curr;
/* 1347 */           Short2FloatLinkedOpenHashMap.this.value[last] = Short2FloatLinkedOpenHashMap.this.value[pos];
/* 1348 */           if (this.next == pos)
/* 1349 */             this.next = last; 
/* 1350 */           if (this.prev == pos)
/* 1351 */             this.prev = last; 
/* 1352 */           Short2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Short2FloatMap.Entry ok) {
/* 1369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Short2FloatMap.Entry ok) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Short2FloatMap.Entry> { private Short2FloatLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(short from) {
/* 1380 */       super(from);
/*      */     }
/*      */     
/*      */     public Short2FloatLinkedOpenHashMap.MapEntry next() {
/* 1384 */       return this.entry = new Short2FloatLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Short2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1388 */       return this.entry = new Short2FloatLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1392 */       super.remove();
/* 1393 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1397 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Short2FloatMap.Entry> { final Short2FloatLinkedOpenHashMap.MapEntry entry = new Short2FloatLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(short from) {
/* 1401 */       super(from);
/*      */     }
/*      */     
/*      */     public Short2FloatLinkedOpenHashMap.MapEntry next() {
/* 1405 */       this.entry.index = nextEntry();
/* 1406 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Short2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1410 */       this.entry.index = previousEntry();
/* 1411 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Short2FloatMap.Entry> implements Short2FloatSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Short2FloatMap.Entry> iterator() {
/* 1419 */       return (ObjectBidirectionalIterator<Short2FloatMap.Entry>)new Short2FloatLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Short2FloatMap.Entry> comparator() {
/* 1423 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Short2FloatMap.Entry> subSet(Short2FloatMap.Entry fromElement, Short2FloatMap.Entry toElement) {
/* 1428 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2FloatMap.Entry> headSet(Short2FloatMap.Entry toElement) {
/* 1432 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Short2FloatMap.Entry> tailSet(Short2FloatMap.Entry fromElement) {
/* 1436 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Short2FloatMap.Entry first() {
/* 1440 */       if (Short2FloatLinkedOpenHashMap.this.size == 0)
/* 1441 */         throw new NoSuchElementException(); 
/* 1442 */       return new Short2FloatLinkedOpenHashMap.MapEntry(Short2FloatLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Short2FloatMap.Entry last() {
/* 1446 */       if (Short2FloatLinkedOpenHashMap.this.size == 0)
/* 1447 */         throw new NoSuchElementException(); 
/* 1448 */       return new Short2FloatLinkedOpenHashMap.MapEntry(Short2FloatLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1453 */       if (!(o instanceof Map.Entry))
/* 1454 */         return false; 
/* 1455 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1456 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1457 */         return false; 
/* 1458 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1459 */         return false; 
/* 1460 */       short k = ((Short)e.getKey()).shortValue();
/* 1461 */       float v = ((Float)e.getValue()).floatValue();
/* 1462 */       if (k == 0) {
/* 1463 */         return (Short2FloatLinkedOpenHashMap.this.containsNullKey && 
/* 1464 */           Float.floatToIntBits(Short2FloatLinkedOpenHashMap.this.value[Short2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1466 */       short[] key = Short2FloatLinkedOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1469 */       if ((curr = key[pos = HashCommon.mix(k) & Short2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1470 */         return false; 
/* 1471 */       if (k == curr) {
/* 1472 */         return (Float.floatToIntBits(Short2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1475 */         if ((curr = key[pos = pos + 1 & Short2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1476 */           return false; 
/* 1477 */         if (k == curr) {
/* 1478 */           return (Float.floatToIntBits(Short2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1484 */       if (!(o instanceof Map.Entry))
/* 1485 */         return false; 
/* 1486 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1487 */       if (e.getKey() == null || !(e.getKey() instanceof Short))
/* 1488 */         return false; 
/* 1489 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1490 */         return false; 
/* 1491 */       short k = ((Short)e.getKey()).shortValue();
/* 1492 */       float v = ((Float)e.getValue()).floatValue();
/* 1493 */       if (k == 0) {
/* 1494 */         if (Short2FloatLinkedOpenHashMap.this.containsNullKey && Float.floatToIntBits(Short2FloatLinkedOpenHashMap.this.value[Short2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1495 */           Short2FloatLinkedOpenHashMap.this.removeNullEntry();
/* 1496 */           return true;
/*      */         } 
/* 1498 */         return false;
/*      */       } 
/*      */       
/* 1501 */       short[] key = Short2FloatLinkedOpenHashMap.this.key;
/*      */       short curr;
/*      */       int pos;
/* 1504 */       if ((curr = key[pos = HashCommon.mix(k) & Short2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1505 */         return false; 
/* 1506 */       if (curr == k) {
/* 1507 */         if (Float.floatToIntBits(Short2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1508 */           Short2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1509 */           return true;
/*      */         } 
/* 1511 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1514 */         if ((curr = key[pos = pos + 1 & Short2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1515 */           return false; 
/* 1516 */         if (curr == k && 
/* 1517 */           Float.floatToIntBits(Short2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1518 */           Short2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1519 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1526 */       return Short2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1530 */       Short2FloatLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Short2FloatMap.Entry> iterator(Short2FloatMap.Entry from) {
/* 1545 */       return new Short2FloatLinkedOpenHashMap.EntryIterator(from.getShortKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Short2FloatMap.Entry> fastIterator() {
/* 1556 */       return new Short2FloatLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Short2FloatMap.Entry> fastIterator(Short2FloatMap.Entry from) {
/* 1571 */       return new Short2FloatLinkedOpenHashMap.FastEntryIterator(from.getShortKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Short2FloatMap.Entry> consumer) {
/* 1576 */       for (int i = Short2FloatLinkedOpenHashMap.this.size, next = Short2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1577 */         int curr = next;
/* 1578 */         next = (int)Short2FloatLinkedOpenHashMap.this.link[curr];
/* 1579 */         consumer.accept(new AbstractShort2FloatMap.BasicEntry(Short2FloatLinkedOpenHashMap.this.key[curr], Short2FloatLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Short2FloatMap.Entry> consumer) {
/* 1585 */       AbstractShort2FloatMap.BasicEntry entry = new AbstractShort2FloatMap.BasicEntry();
/* 1586 */       for (int i = Short2FloatLinkedOpenHashMap.this.size, next = Short2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1587 */         int curr = next;
/* 1588 */         next = (int)Short2FloatLinkedOpenHashMap.this.link[curr];
/* 1589 */         entry.key = Short2FloatLinkedOpenHashMap.this.key[curr];
/* 1590 */         entry.value = Short2FloatLinkedOpenHashMap.this.value[curr];
/* 1591 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Short2FloatSortedMap.FastSortedEntrySet short2FloatEntrySet() {
/* 1597 */     if (this.entries == null)
/* 1598 */       this.entries = new MapEntrySet(); 
/* 1599 */     return this.entries;
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
/* 1612 */       super(k);
/*      */     }
/*      */     
/*      */     public short previousShort() {
/* 1616 */       return Short2FloatLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public short nextShort() {
/* 1623 */       return Short2FloatLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractShortSortedSet { private KeySet() {}
/*      */     
/*      */     public ShortListIterator iterator(short from) {
/* 1629 */       return new Short2FloatLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ShortListIterator iterator() {
/* 1633 */       return new Short2FloatLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1638 */       if (Short2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1639 */         consumer.accept(Short2FloatLinkedOpenHashMap.this.key[Short2FloatLinkedOpenHashMap.this.n]); 
/* 1640 */       for (int pos = Short2FloatLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1641 */         short k = Short2FloatLinkedOpenHashMap.this.key[pos];
/* 1642 */         if (k != 0)
/* 1643 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1648 */       return Short2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(short k) {
/* 1652 */       return Short2FloatLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(short k) {
/* 1656 */       int oldSize = Short2FloatLinkedOpenHashMap.this.size;
/* 1657 */       Short2FloatLinkedOpenHashMap.this.remove(k);
/* 1658 */       return (Short2FloatLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1662 */       Short2FloatLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public short firstShort() {
/* 1666 */       if (Short2FloatLinkedOpenHashMap.this.size == 0)
/* 1667 */         throw new NoSuchElementException(); 
/* 1668 */       return Short2FloatLinkedOpenHashMap.this.key[Short2FloatLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public short lastShort() {
/* 1672 */       if (Short2FloatLinkedOpenHashMap.this.size == 0)
/* 1673 */         throw new NoSuchElementException(); 
/* 1674 */       return Short2FloatLinkedOpenHashMap.this.key[Short2FloatLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public ShortComparator comparator() {
/* 1678 */       return null;
/*      */     }
/*      */     
/*      */     public ShortSortedSet tailSet(short from) {
/* 1682 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ShortSortedSet headSet(short to) {
/* 1686 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ShortSortedSet subSet(short from, short to) {
/* 1690 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ShortSortedSet keySet() {
/* 1695 */     if (this.keys == null)
/* 1696 */       this.keys = new KeySet(); 
/* 1697 */     return this.keys;
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
/* 1711 */       return Short2FloatLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1718 */       return Short2FloatLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1723 */     if (this.values == null)
/* 1724 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1727 */             return (FloatIterator)new Short2FloatLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1731 */             return Short2FloatLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1735 */             return Short2FloatLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1739 */             Short2FloatLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1744 */             if (Short2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1745 */               consumer.accept(Short2FloatLinkedOpenHashMap.this.value[Short2FloatLinkedOpenHashMap.this.n]); 
/* 1746 */             for (int pos = Short2FloatLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1747 */               if (Short2FloatLinkedOpenHashMap.this.key[pos] != 0)
/* 1748 */                 consumer.accept(Short2FloatLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1751 */     return this.values;
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
/* 1768 */     return trim(this.size);
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
/* 1792 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1793 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1794 */       return true; 
/*      */     try {
/* 1796 */       rehash(l);
/* 1797 */     } catch (OutOfMemoryError cantDoIt) {
/* 1798 */       return false;
/*      */     } 
/* 1800 */     return true;
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
/* 1816 */     short[] key = this.key;
/* 1817 */     float[] value = this.value;
/* 1818 */     int mask = newN - 1;
/* 1819 */     short[] newKey = new short[newN + 1];
/* 1820 */     float[] newValue = new float[newN + 1];
/* 1821 */     int i = this.first, prev = -1, newPrev = -1;
/* 1822 */     long[] link = this.link;
/* 1823 */     long[] newLink = new long[newN + 1];
/* 1824 */     this.first = -1;
/* 1825 */     for (int j = this.size; j-- != 0; ) {
/* 1826 */       int pos; if (key[i] == 0) {
/* 1827 */         pos = newN;
/*      */       } else {
/* 1829 */         pos = HashCommon.mix(key[i]) & mask;
/* 1830 */         while (newKey[pos] != 0)
/* 1831 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1833 */       newKey[pos] = key[i];
/* 1834 */       newValue[pos] = value[i];
/* 1835 */       if (prev != -1) {
/* 1836 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1837 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1838 */         newPrev = pos;
/*      */       } else {
/* 1840 */         newPrev = this.first = pos;
/*      */         
/* 1842 */         newLink[pos] = -1L;
/*      */       } 
/* 1844 */       int t = i;
/* 1845 */       i = (int)link[i];
/* 1846 */       prev = t;
/*      */     } 
/* 1848 */     this.link = newLink;
/* 1849 */     this.last = newPrev;
/* 1850 */     if (newPrev != -1)
/*      */     {
/* 1852 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1853 */     this.n = newN;
/* 1854 */     this.mask = mask;
/* 1855 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1856 */     this.key = newKey;
/* 1857 */     this.value = newValue;
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
/*      */   public Short2FloatLinkedOpenHashMap clone() {
/*      */     Short2FloatLinkedOpenHashMap c;
/*      */     try {
/* 1874 */       c = (Short2FloatLinkedOpenHashMap)super.clone();
/* 1875 */     } catch (CloneNotSupportedException cantHappen) {
/* 1876 */       throw new InternalError();
/*      */     } 
/* 1878 */     c.keys = null;
/* 1879 */     c.values = null;
/* 1880 */     c.entries = null;
/* 1881 */     c.containsNullKey = this.containsNullKey;
/* 1882 */     c.key = (short[])this.key.clone();
/* 1883 */     c.value = (float[])this.value.clone();
/* 1884 */     c.link = (long[])this.link.clone();
/* 1885 */     return c;
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
/* 1898 */     int h = 0;
/* 1899 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1900 */       while (this.key[i] == 0)
/* 1901 */         i++; 
/* 1902 */       t = this.key[i];
/* 1903 */       t ^= HashCommon.float2int(this.value[i]);
/* 1904 */       h += t;
/* 1905 */       i++;
/*      */     } 
/*      */     
/* 1908 */     if (this.containsNullKey)
/* 1909 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1910 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1913 */     short[] key = this.key;
/* 1914 */     float[] value = this.value;
/* 1915 */     MapIterator i = new MapIterator();
/* 1916 */     s.defaultWriteObject();
/* 1917 */     for (int j = this.size; j-- != 0; ) {
/* 1918 */       int e = i.nextEntry();
/* 1919 */       s.writeShort(key[e]);
/* 1920 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1925 */     s.defaultReadObject();
/* 1926 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1927 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1928 */     this.mask = this.n - 1;
/* 1929 */     short[] key = this.key = new short[this.n + 1];
/* 1930 */     float[] value = this.value = new float[this.n + 1];
/* 1931 */     long[] link = this.link = new long[this.n + 1];
/* 1932 */     int prev = -1;
/* 1933 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1936 */     for (int i = this.size; i-- != 0; ) {
/* 1937 */       int pos; short k = s.readShort();
/* 1938 */       float v = s.readFloat();
/* 1939 */       if (k == 0) {
/* 1940 */         pos = this.n;
/* 1941 */         this.containsNullKey = true;
/*      */       } else {
/* 1943 */         pos = HashCommon.mix(k) & this.mask;
/* 1944 */         while (key[pos] != 0)
/* 1945 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1947 */       key[pos] = k;
/* 1948 */       value[pos] = v;
/* 1949 */       if (this.first != -1) {
/* 1950 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1951 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1952 */         prev = pos; continue;
/*      */       } 
/* 1954 */       prev = this.first = pos;
/*      */       
/* 1956 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1959 */     this.last = prev;
/* 1960 */     if (prev != -1)
/*      */     {
/* 1962 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\shorts\Short2FloatLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */