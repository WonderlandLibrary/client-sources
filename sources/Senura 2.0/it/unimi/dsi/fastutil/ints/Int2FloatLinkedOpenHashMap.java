/*      */ package it.unimi.dsi.fastutil.ints;
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
/*      */ public class Int2FloatLinkedOpenHashMap
/*      */   extends AbstractInt2FloatSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient int[] key;
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
/*      */   protected transient Int2FloatSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient IntSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new int[this.n + 1];
/*  162 */     this.value = new float[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatLinkedOpenHashMap() {
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
/*      */   public Int2FloatLinkedOpenHashMap(Map<? extends Integer, ? extends Float> m, float f) {
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
/*      */   public Int2FloatLinkedOpenHashMap(Map<? extends Integer, ? extends Float> m) {
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
/*      */   public Int2FloatLinkedOpenHashMap(Int2FloatMap m, float f) {
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
/*      */   public Int2FloatLinkedOpenHashMap(Int2FloatMap m) {
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
/*      */   public Int2FloatLinkedOpenHashMap(int[] k, float[] v, float f) {
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
/*      */   public Int2FloatLinkedOpenHashMap(int[] k, float[] v) {
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
/*      */   public void putAll(Map<? extends Integer, ? extends Float> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(int k) {
/*  303 */     if (k == 0) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   private void insert(int pos, int k, float v) {
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
/*      */   public float put(int k, float v) {
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
/*      */   public float addTo(int k, float incr) {
/*      */     int pos;
/*  374 */     if (k == 0) {
/*  375 */       if (this.containsNullKey)
/*  376 */         return addToValue(this.n, incr); 
/*  377 */       pos = this.n;
/*  378 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  381 */       int[] key = this.key;
/*      */       int curr;
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
/*  419 */     int[] key = this.key; while (true) {
/*      */       int curr, last;
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
/*      */   public float remove(int k) {
/*  440 */     if (k == 0) {
/*  441 */       if (this.containsNullKey)
/*  442 */         return removeNullEntry(); 
/*  443 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  446 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public float getAndMoveToFirst(int k) {
/*  566 */     if (k == 0) {
/*  567 */       if (this.containsNullKey) {
/*  568 */         moveIndexToFirst(this.n);
/*  569 */         return this.value[this.n];
/*      */       } 
/*  571 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  574 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public float getAndMoveToLast(int k) {
/*  603 */     if (k == 0) {
/*  604 */       if (this.containsNullKey) {
/*  605 */         moveIndexToLast(this.n);
/*  606 */         return this.value[this.n];
/*      */       } 
/*  608 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  611 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public float putAndMoveToFirst(int k, float v) {
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
/*  652 */       int[] key = this.key;
/*      */       int curr;
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
/*      */   public float putAndMoveToLast(int k, float v) {
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
/*  705 */       int[] key = this.key;
/*      */       int curr;
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
/*      */   public float get(int k) {
/*  739 */     if (k == 0) {
/*  740 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  742 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public boolean containsKey(int k) {
/*  760 */     if (k == 0) {
/*  761 */       return this.containsNullKey;
/*      */     }
/*  763 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*  781 */     int[] key = this.key;
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
/*      */   public float getOrDefault(int k, float defaultValue) {
/*  793 */     if (k == 0) {
/*  794 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  796 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public float putIfAbsent(int k, float v) {
/*  814 */     int pos = find(k);
/*  815 */     if (pos >= 0)
/*  816 */       return this.value[pos]; 
/*  817 */     insert(-pos - 1, k, v);
/*  818 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(int k, float v) {
/*  824 */     if (k == 0) {
/*  825 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  826 */         removeNullEntry();
/*  827 */         return true;
/*      */       } 
/*  829 */       return false;
/*      */     } 
/*      */     
/*  832 */     int[] key = this.key;
/*      */     
/*      */     int curr, pos;
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
/*      */   public boolean replace(int k, float oldValue, float v) {
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  855 */       return false; 
/*  856 */     this.value[pos] = v;
/*  857 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(int k, float v) {
/*  862 */     int pos = find(k);
/*  863 */     if (pos < 0)
/*  864 */       return this.defRetValue; 
/*  865 */     float oldValue = this.value[pos];
/*  866 */     this.value[pos] = v;
/*  867 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(int k, IntToDoubleFunction mappingFunction) {
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
/*      */   public float computeIfAbsentNullable(int k, IntFunction<? extends Float> mappingFunction) {
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
/*      */   public float computeIfPresent(int k, BiFunction<? super Integer, ? super Float, ? extends Float> remappingFunction) {
/*  899 */     Objects.requireNonNull(remappingFunction);
/*  900 */     int pos = find(k);
/*  901 */     if (pos < 0)
/*  902 */       return this.defRetValue; 
/*  903 */     Float newValue = remappingFunction.apply(Integer.valueOf(k), Float.valueOf(this.value[pos]));
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
/*      */   public float compute(int k, BiFunction<? super Integer, ? super Float, ? extends Float> remappingFunction) {
/*  917 */     Objects.requireNonNull(remappingFunction);
/*  918 */     int pos = find(k);
/*  919 */     Float newValue = remappingFunction.apply(Integer.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
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
/*      */   public float merge(int k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
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
/*  969 */     Arrays.fill(this.key, 0);
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
/*      */     implements Int2FloatMap.Entry, Map.Entry<Integer, Float>
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
/*      */     public int getIntKey() {
/*  996 */       return Int2FloatLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/* 1000 */       return Int2FloatLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/* 1004 */       float oldValue = Int2FloatLinkedOpenHashMap.this.value[this.index];
/* 1005 */       Int2FloatLinkedOpenHashMap.this.value[this.index] = v;
/* 1006 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getKey() {
/* 1016 */       return Integer.valueOf(Int2FloatLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1026 */       return Float.valueOf(Int2FloatLinkedOpenHashMap.this.value[this.index]);
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
/* 1043 */       Map.Entry<Integer, Float> e = (Map.Entry<Integer, Float>)o;
/* 1044 */       return (Int2FloatLinkedOpenHashMap.this.key[this.index] == ((Integer)e.getKey()).intValue() && 
/* 1045 */         Float.floatToIntBits(Int2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1049 */       return Int2FloatLinkedOpenHashMap.this.key[this.index] ^ HashCommon.float2int(Int2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1053 */       return Int2FloatLinkedOpenHashMap.this.key[this.index] + "=>" + Int2FloatLinkedOpenHashMap.this.value[this.index];
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
/*      */   public int firstIntKey() {
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
/*      */   public int lastIntKey() {
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
/*      */   public Int2FloatSortedMap tailMap(int from) {
/* 1155 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatSortedMap headMap(int to) {
/* 1164 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Int2FloatSortedMap subMap(int from, int to) {
/* 1173 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntComparator comparator() {
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
/* 1216 */       this.next = Int2FloatLinkedOpenHashMap.this.first;
/* 1217 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(int from) {
/* 1220 */       if (from == 0) {
/* 1221 */         if (Int2FloatLinkedOpenHashMap.this.containsNullKey) {
/* 1222 */           this.next = (int)Int2FloatLinkedOpenHashMap.this.link[Int2FloatLinkedOpenHashMap.this.n];
/* 1223 */           this.prev = Int2FloatLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1226 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1228 */       if (Int2FloatLinkedOpenHashMap.this.key[Int2FloatLinkedOpenHashMap.this.last] == from) {
/* 1229 */         this.prev = Int2FloatLinkedOpenHashMap.this.last;
/* 1230 */         this.index = Int2FloatLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1234 */       int pos = HashCommon.mix(from) & Int2FloatLinkedOpenHashMap.this.mask;
/*      */       
/* 1236 */       while (Int2FloatLinkedOpenHashMap.this.key[pos] != 0) {
/* 1237 */         if (Int2FloatLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1239 */           this.next = (int)Int2FloatLinkedOpenHashMap.this.link[pos];
/* 1240 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1243 */         pos = pos + 1 & Int2FloatLinkedOpenHashMap.this.mask;
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
/* 1261 */         this.index = Int2FloatLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1264 */       int pos = Int2FloatLinkedOpenHashMap.this.first;
/* 1265 */       this.index = 1;
/* 1266 */       while (pos != this.prev) {
/* 1267 */         pos = (int)Int2FloatLinkedOpenHashMap.this.link[pos];
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
/* 1283 */       this.next = (int)Int2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1284 */       this.prev = this.curr;
/* 1285 */       if (this.index >= 0)
/* 1286 */         this.index++; 
/* 1287 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1290 */       if (!hasPrevious())
/* 1291 */         throw new NoSuchElementException(); 
/* 1292 */       this.curr = this.prev;
/* 1293 */       this.prev = (int)(Int2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1309 */         this.prev = (int)(Int2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1311 */         this.next = (int)Int2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1312 */       }  Int2FloatLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1317 */       if (this.prev == -1) {
/* 1318 */         Int2FloatLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1320 */         Int2FloatLinkedOpenHashMap.this.link[this.prev] = Int2FloatLinkedOpenHashMap.this.link[this.prev] ^ (Int2FloatLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1321 */       }  if (this.next == -1) {
/* 1322 */         Int2FloatLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1324 */         Int2FloatLinkedOpenHashMap.this.link[this.next] = Int2FloatLinkedOpenHashMap.this.link[this.next] ^ (Int2FloatLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1325 */       }  int pos = this.curr;
/* 1326 */       this.curr = -1;
/* 1327 */       if (pos == Int2FloatLinkedOpenHashMap.this.n) {
/* 1328 */         Int2FloatLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1331 */         int[] key = Int2FloatLinkedOpenHashMap.this.key;
/*      */         
/*      */         while (true) {
/*      */           int curr, last;
/* 1335 */           pos = (last = pos) + 1 & Int2FloatLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1337 */             if ((curr = key[pos]) == 0) {
/* 1338 */               key[last] = 0;
/*      */               return;
/*      */             } 
/* 1341 */             int slot = HashCommon.mix(curr) & Int2FloatLinkedOpenHashMap.this.mask;
/* 1342 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1344 */             pos = pos + 1 & Int2FloatLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1346 */           key[last] = curr;
/* 1347 */           Int2FloatLinkedOpenHashMap.this.value[last] = Int2FloatLinkedOpenHashMap.this.value[pos];
/* 1348 */           if (this.next == pos)
/* 1349 */             this.next = last; 
/* 1350 */           if (this.prev == pos)
/* 1351 */             this.prev = last; 
/* 1352 */           Int2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Int2FloatMap.Entry ok) {
/* 1369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Int2FloatMap.Entry ok) {
/* 1372 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Int2FloatMap.Entry> {
/*      */     private Int2FloatLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator(int from) {
/* 1380 */       super(from);
/*      */     }
/*      */     
/*      */     public Int2FloatLinkedOpenHashMap.MapEntry next() {
/* 1384 */       return this.entry = new Int2FloatLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     public EntryIterator() {}
/*      */     public Int2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1388 */       return this.entry = new Int2FloatLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1392 */       super.remove();
/* 1393 */       this.entry.index = -1;
/*      */     }
/*      */   }
/*      */   
/* 1397 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Int2FloatMap.Entry> { final Int2FloatLinkedOpenHashMap.MapEntry entry = new Int2FloatLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(int from) {
/* 1401 */       super(from);
/*      */     }
/*      */     
/*      */     public Int2FloatLinkedOpenHashMap.MapEntry next() {
/* 1405 */       this.entry.index = nextEntry();
/* 1406 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Int2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1410 */       this.entry.index = previousEntry();
/* 1411 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Int2FloatMap.Entry> implements Int2FloatSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Int2FloatMap.Entry> iterator() {
/* 1417 */       return (ObjectBidirectionalIterator<Int2FloatMap.Entry>)new Int2FloatLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Int2FloatMap.Entry> comparator() {
/* 1421 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Int2FloatMap.Entry> subSet(Int2FloatMap.Entry fromElement, Int2FloatMap.Entry toElement) {
/* 1426 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2FloatMap.Entry> headSet(Int2FloatMap.Entry toElement) {
/* 1430 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Int2FloatMap.Entry> tailSet(Int2FloatMap.Entry fromElement) {
/* 1434 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Int2FloatMap.Entry first() {
/* 1438 */       if (Int2FloatLinkedOpenHashMap.this.size == 0)
/* 1439 */         throw new NoSuchElementException(); 
/* 1440 */       return new Int2FloatLinkedOpenHashMap.MapEntry(Int2FloatLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Int2FloatMap.Entry last() {
/* 1444 */       if (Int2FloatLinkedOpenHashMap.this.size == 0)
/* 1445 */         throw new NoSuchElementException(); 
/* 1446 */       return new Int2FloatLinkedOpenHashMap.MapEntry(Int2FloatLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1451 */       if (!(o instanceof Map.Entry))
/* 1452 */         return false; 
/* 1453 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1454 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1455 */         return false; 
/* 1456 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1457 */         return false; 
/* 1458 */       int k = ((Integer)e.getKey()).intValue();
/* 1459 */       float v = ((Float)e.getValue()).floatValue();
/* 1460 */       if (k == 0) {
/* 1461 */         return (Int2FloatLinkedOpenHashMap.this.containsNullKey && 
/* 1462 */           Float.floatToIntBits(Int2FloatLinkedOpenHashMap.this.value[Int2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1464 */       int[] key = Int2FloatLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1467 */       if ((curr = key[pos = HashCommon.mix(k) & Int2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1468 */         return false; 
/* 1469 */       if (k == curr) {
/* 1470 */         return (Float.floatToIntBits(Int2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1473 */         if ((curr = key[pos = pos + 1 & Int2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1474 */           return false; 
/* 1475 */         if (k == curr) {
/* 1476 */           return (Float.floatToIntBits(Int2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1482 */       if (!(o instanceof Map.Entry))
/* 1483 */         return false; 
/* 1484 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1485 */       if (e.getKey() == null || !(e.getKey() instanceof Integer))
/* 1486 */         return false; 
/* 1487 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1488 */         return false; 
/* 1489 */       int k = ((Integer)e.getKey()).intValue();
/* 1490 */       float v = ((Float)e.getValue()).floatValue();
/* 1491 */       if (k == 0) {
/* 1492 */         if (Int2FloatLinkedOpenHashMap.this.containsNullKey && Float.floatToIntBits(Int2FloatLinkedOpenHashMap.this.value[Int2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1493 */           Int2FloatLinkedOpenHashMap.this.removeNullEntry();
/* 1494 */           return true;
/*      */         } 
/* 1496 */         return false;
/*      */       } 
/*      */       
/* 1499 */       int[] key = Int2FloatLinkedOpenHashMap.this.key;
/*      */       
/*      */       int curr, pos;
/* 1502 */       if ((curr = key[pos = HashCommon.mix(k) & Int2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1503 */         return false; 
/* 1504 */       if (curr == k) {
/* 1505 */         if (Float.floatToIntBits(Int2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1506 */           Int2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1507 */           return true;
/*      */         } 
/* 1509 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1512 */         if ((curr = key[pos = pos + 1 & Int2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1513 */           return false; 
/* 1514 */         if (curr == k && 
/* 1515 */           Float.floatToIntBits(Int2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1516 */           Int2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1517 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1524 */       return Int2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1528 */       Int2FloatLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Int2FloatMap.Entry> iterator(Int2FloatMap.Entry from) {
/* 1543 */       return new Int2FloatLinkedOpenHashMap.EntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Int2FloatMap.Entry> fastIterator() {
/* 1554 */       return new Int2FloatLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Int2FloatMap.Entry> fastIterator(Int2FloatMap.Entry from) {
/* 1569 */       return new Int2FloatLinkedOpenHashMap.FastEntryIterator(from.getIntKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Int2FloatMap.Entry> consumer) {
/* 1574 */       for (int i = Int2FloatLinkedOpenHashMap.this.size, next = Int2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1575 */         int curr = next;
/* 1576 */         next = (int)Int2FloatLinkedOpenHashMap.this.link[curr];
/* 1577 */         consumer.accept(new AbstractInt2FloatMap.BasicEntry(Int2FloatLinkedOpenHashMap.this.key[curr], Int2FloatLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Int2FloatMap.Entry> consumer) {
/* 1583 */       AbstractInt2FloatMap.BasicEntry entry = new AbstractInt2FloatMap.BasicEntry();
/* 1584 */       for (int i = Int2FloatLinkedOpenHashMap.this.size, next = Int2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1585 */         int curr = next;
/* 1586 */         next = (int)Int2FloatLinkedOpenHashMap.this.link[curr];
/* 1587 */         entry.key = Int2FloatLinkedOpenHashMap.this.key[curr];
/* 1588 */         entry.value = Int2FloatLinkedOpenHashMap.this.value[curr];
/* 1589 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Int2FloatSortedMap.FastSortedEntrySet int2FloatEntrySet() {
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
/*      */     implements IntListIterator
/*      */   {
/*      */     public KeyIterator(int k) {
/* 1610 */       super(k);
/*      */     }
/*      */     
/*      */     public int previousInt() {
/* 1614 */       return Int2FloatLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public int nextInt() {
/* 1621 */       return Int2FloatLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractIntSortedSet { private KeySet() {}
/*      */     
/*      */     public IntListIterator iterator(int from) {
/* 1627 */       return new Int2FloatLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public IntListIterator iterator() {
/* 1631 */       return new Int2FloatLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(IntConsumer consumer) {
/* 1636 */       if (Int2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1637 */         consumer.accept(Int2FloatLinkedOpenHashMap.this.key[Int2FloatLinkedOpenHashMap.this.n]); 
/* 1638 */       for (int pos = Int2FloatLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1639 */         int k = Int2FloatLinkedOpenHashMap.this.key[pos];
/* 1640 */         if (k != 0)
/* 1641 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1646 */       return Int2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(int k) {
/* 1650 */       return Int2FloatLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(int k) {
/* 1654 */       int oldSize = Int2FloatLinkedOpenHashMap.this.size;
/* 1655 */       Int2FloatLinkedOpenHashMap.this.remove(k);
/* 1656 */       return (Int2FloatLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1660 */       Int2FloatLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public int firstInt() {
/* 1664 */       if (Int2FloatLinkedOpenHashMap.this.size == 0)
/* 1665 */         throw new NoSuchElementException(); 
/* 1666 */       return Int2FloatLinkedOpenHashMap.this.key[Int2FloatLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public int lastInt() {
/* 1670 */       if (Int2FloatLinkedOpenHashMap.this.size == 0)
/* 1671 */         throw new NoSuchElementException(); 
/* 1672 */       return Int2FloatLinkedOpenHashMap.this.key[Int2FloatLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public IntComparator comparator() {
/* 1676 */       return null;
/*      */     }
/*      */     
/*      */     public IntSortedSet tailSet(int from) {
/* 1680 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public IntSortedSet headSet(int to) {
/* 1684 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public IntSortedSet subSet(int from, int to) {
/* 1688 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public IntSortedSet keySet() {
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public float previousFloat() {
/* 1709 */       return Int2FloatLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1716 */       return Int2FloatLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1721 */     if (this.values == null)
/* 1722 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1725 */             return (FloatIterator)new Int2FloatLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1729 */             return Int2FloatLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1733 */             return Int2FloatLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1737 */             Int2FloatLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1742 */             if (Int2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1743 */               consumer.accept(Int2FloatLinkedOpenHashMap.this.value[Int2FloatLinkedOpenHashMap.this.n]); 
/* 1744 */             for (int pos = Int2FloatLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1745 */               if (Int2FloatLinkedOpenHashMap.this.key[pos] != 0)
/* 1746 */                 consumer.accept(Int2FloatLinkedOpenHashMap.this.value[pos]); 
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
/* 1814 */     int[] key = this.key;
/* 1815 */     float[] value = this.value;
/* 1816 */     int mask = newN - 1;
/* 1817 */     int[] newKey = new int[newN + 1];
/* 1818 */     float[] newValue = new float[newN + 1];
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
/*      */   public Int2FloatLinkedOpenHashMap clone() {
/*      */     Int2FloatLinkedOpenHashMap c;
/*      */     try {
/* 1872 */       c = (Int2FloatLinkedOpenHashMap)super.clone();
/* 1873 */     } catch (CloneNotSupportedException cantHappen) {
/* 1874 */       throw new InternalError();
/*      */     } 
/* 1876 */     c.keys = null;
/* 1877 */     c.values = null;
/* 1878 */     c.entries = null;
/* 1879 */     c.containsNullKey = this.containsNullKey;
/* 1880 */     c.key = (int[])this.key.clone();
/* 1881 */     c.value = (float[])this.value.clone();
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
/* 1901 */       t ^= HashCommon.float2int(this.value[i]);
/* 1902 */       h += t;
/* 1903 */       i++;
/*      */     } 
/*      */     
/* 1906 */     if (this.containsNullKey)
/* 1907 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1908 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1911 */     int[] key = this.key;
/* 1912 */     float[] value = this.value;
/* 1913 */     MapIterator i = new MapIterator();
/* 1914 */     s.defaultWriteObject();
/* 1915 */     for (int j = this.size; j-- != 0; ) {
/* 1916 */       int e = i.nextEntry();
/* 1917 */       s.writeInt(key[e]);
/* 1918 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1923 */     s.defaultReadObject();
/* 1924 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1925 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1926 */     this.mask = this.n - 1;
/* 1927 */     int[] key = this.key = new int[this.n + 1];
/* 1928 */     float[] value = this.value = new float[this.n + 1];
/* 1929 */     long[] link = this.link = new long[this.n + 1];
/* 1930 */     int prev = -1;
/* 1931 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1934 */     for (int i = this.size; i-- != 0; ) {
/* 1935 */       int pos, k = s.readInt();
/* 1936 */       float v = s.readFloat();
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\ints\Int2FloatLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */