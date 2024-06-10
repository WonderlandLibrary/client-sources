/*      */ package it.unimi.dsi.fastutil.longs;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.LongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Long2ObjectLinkedOpenHashMap<V>
/*      */   extends AbstractLong2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient long[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  106 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   protected transient int last = -1;
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
/*      */   protected transient Long2ObjectSortedMap.FastSortedEntrySet<V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(int expected, float f) {
/*  152 */     if (f <= 0.0F || f > 1.0F)
/*  153 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  154 */     if (expected < 0)
/*  155 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  156 */     this.f = f;
/*  157 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  158 */     this.mask = this.n - 1;
/*  159 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  160 */     this.key = new long[this.n + 1];
/*  161 */     this.value = (V[])new Object[this.n + 1];
/*  162 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap() {
/*  179 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m, float f) {
/*  190 */     this(m.size(), f);
/*  191 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Map<? extends Long, ? extends V> m) {
/*  201 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m, float f) {
/*  212 */     this(m.size(), f);
/*  213 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectLinkedOpenHashMap(Long2ObjectMap<V> m) {
/*  223 */     this(m, 0.75F);
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
/*      */   public Long2ObjectLinkedOpenHashMap(long[] k, V[] v, float f) {
/*  238 */     this(k.length, f);
/*  239 */     if (k.length != v.length) {
/*  240 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  242 */     for (int i = 0; i < k.length; i++) {
/*  243 */       put(k[i], v[i]);
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
/*      */   public Long2ObjectLinkedOpenHashMap(long[] k, V[] v) {
/*  257 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  260 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  263 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  264 */     if (needed > this.n)
/*  265 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  268 */     int needed = (int)Math.min(1073741824L, 
/*  269 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  270 */     if (needed > this.n)
/*  271 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  274 */     V oldValue = this.value[pos];
/*  275 */     this.value[pos] = null;
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     V oldValue = this.value[this.n];
/*  286 */     this.value[this.n] = null;
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Long, ? extends V> m) {
/*  295 */     if (this.f <= 0.5D) {
/*  296 */       ensureCapacity(m.size());
/*      */     } else {
/*  298 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  300 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(long k) {
/*  304 */     if (k == 0L) {
/*  305 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  307 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  310 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  311 */       return -(pos + 1); 
/*  312 */     if (k == curr) {
/*  313 */       return pos;
/*      */     }
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  317 */         return -(pos + 1); 
/*  318 */       if (k == curr)
/*  319 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, long k, V v) {
/*  323 */     if (pos == this.n)
/*  324 */       this.containsNullKey = true; 
/*  325 */     this.key[pos] = k;
/*  326 */     this.value[pos] = v;
/*  327 */     if (this.size == 0) {
/*  328 */       this.first = this.last = pos;
/*      */       
/*  330 */       this.link[pos] = -1L;
/*      */     } else {
/*  332 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  333 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  334 */       this.last = pos;
/*      */     } 
/*  336 */     if (this.size++ >= this.maxFill) {
/*  337 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(long k, V v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     V oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
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
/*  363 */     long[] key = this.key; while (true) {
/*      */       long curr; int last;
/*  365 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  367 */         if ((curr = key[pos]) == 0L) {
/*  368 */           key[last] = 0L;
/*  369 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  372 */         int slot = (int)HashCommon.mix(curr) & this.mask;
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
/*      */   public V remove(long k) {
/*  385 */     if (k == 0L) {
/*  386 */       if (this.containsNullKey)
/*  387 */         return removeNullEntry(); 
/*  388 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  391 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  394 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  395 */       return this.defRetValue; 
/*  396 */     if (k == curr)
/*  397 */       return removeEntry(pos); 
/*      */     while (true) {
/*  399 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  400 */         return this.defRetValue; 
/*  401 */       if (k == curr)
/*  402 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private V setValue(int pos, V v) {
/*  406 */     V oldValue = this.value[pos];
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
/*      */   public V removeFirst() {
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
/*  429 */     V v = this.value[pos];
/*  430 */     if (pos == this.n) {
/*  431 */       this.containsNullKey = false;
/*  432 */       this.value[this.n] = null;
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
/*      */   public V removeLast() {
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
/*  457 */     V v = this.value[pos];
/*  458 */     if (pos == this.n) {
/*  459 */       this.containsNullKey = false;
/*  460 */       this.value[this.n] = null;
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
/*      */   public V getAndMoveToFirst(long k) {
/*  513 */     if (k == 0L) {
/*  514 */       if (this.containsNullKey) {
/*  515 */         moveIndexToFirst(this.n);
/*  516 */         return this.value[this.n];
/*      */       } 
/*  518 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  521 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  524 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  525 */       return this.defRetValue; 
/*  526 */     if (k == curr) {
/*  527 */       moveIndexToFirst(pos);
/*  528 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  532 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
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
/*      */   public V getAndMoveToLast(long k) {
/*  550 */     if (k == 0L) {
/*  551 */       if (this.containsNullKey) {
/*  552 */         moveIndexToLast(this.n);
/*  553 */         return this.value[this.n];
/*      */       } 
/*  555 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  558 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  561 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  562 */       return this.defRetValue; 
/*  563 */     if (k == curr) {
/*  564 */       moveIndexToLast(pos);
/*  565 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  569 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
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
/*      */   public V putAndMoveToFirst(long k, V v) {
/*      */     int pos;
/*  590 */     if (k == 0L) {
/*  591 */       if (this.containsNullKey) {
/*  592 */         moveIndexToFirst(this.n);
/*  593 */         return setValue(this.n, v);
/*      */       } 
/*  595 */       this.containsNullKey = true;
/*  596 */       pos = this.n;
/*      */     } else {
/*      */       
/*  599 */       long[] key = this.key;
/*      */       long curr;
/*  601 */       if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) != 0L) {
/*  602 */         if (curr == k) {
/*  603 */           moveIndexToFirst(pos);
/*  604 */           return setValue(pos, v);
/*      */         } 
/*  606 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  607 */           if (curr == k) {
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
/*      */   public V putAndMoveToLast(long k, V v) {
/*      */     int pos;
/*  643 */     if (k == 0L) {
/*  644 */       if (this.containsNullKey) {
/*  645 */         moveIndexToLast(this.n);
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
/*  656 */           moveIndexToLast(pos);
/*  657 */           return setValue(pos, v);
/*      */         } 
/*  659 */         while ((curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  660 */           if (curr == k) {
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
/*      */   public V get(long k) {
/*  686 */     if (k == 0L) {
/*  687 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  689 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  692 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  693 */       return this.defRetValue; 
/*  694 */     if (k == curr) {
/*  695 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  698 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  699 */         return this.defRetValue; 
/*  700 */       if (k == curr) {
/*  701 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(long k) {
/*  707 */     if (k == 0L) {
/*  708 */       return this.containsNullKey;
/*      */     }
/*  710 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  713 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  714 */       return false; 
/*  715 */     if (k == curr) {
/*  716 */       return true;
/*      */     }
/*      */     while (true) {
/*  719 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  720 */         return false; 
/*  721 */       if (k == curr)
/*  722 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  727 */     V[] value = this.value;
/*  728 */     long[] key = this.key;
/*  729 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  730 */       return true; 
/*  731 */     for (int i = this.n; i-- != 0;) {
/*  732 */       if (key[i] != 0L && Objects.equals(value[i], v))
/*  733 */         return true; 
/*  734 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(long k, V defaultValue) {
/*  740 */     if (k == 0L) {
/*  741 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  743 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  746 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  747 */       return defaultValue; 
/*  748 */     if (k == curr) {
/*  749 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  752 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  753 */         return defaultValue; 
/*  754 */       if (k == curr) {
/*  755 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(long k, V v) {
/*  761 */     int pos = find(k);
/*  762 */     if (pos >= 0)
/*  763 */       return this.value[pos]; 
/*  764 */     insert(-pos - 1, k, v);
/*  765 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(long k, Object v) {
/*  771 */     if (k == 0L) {
/*  772 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
/*  773 */         removeNullEntry();
/*  774 */         return true;
/*      */       } 
/*  776 */       return false;
/*      */     } 
/*      */     
/*  779 */     long[] key = this.key;
/*      */     long curr;
/*      */     int pos;
/*  782 */     if ((curr = key[pos = (int)HashCommon.mix(k) & this.mask]) == 0L)
/*  783 */       return false; 
/*  784 */     if (k == curr && Objects.equals(v, this.value[pos])) {
/*  785 */       removeEntry(pos);
/*  786 */       return true;
/*      */     } 
/*      */     while (true) {
/*  789 */       if ((curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  790 */         return false; 
/*  791 */       if (k == curr && Objects.equals(v, this.value[pos])) {
/*  792 */         removeEntry(pos);
/*  793 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(long k, V oldValue, V v) {
/*  800 */     int pos = find(k);
/*  801 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
/*  802 */       return false; 
/*  803 */     this.value[pos] = v;
/*  804 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(long k, V v) {
/*  809 */     int pos = find(k);
/*  810 */     if (pos < 0)
/*  811 */       return this.defRetValue; 
/*  812 */     V oldValue = this.value[pos];
/*  813 */     this.value[pos] = v;
/*  814 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(long k, LongFunction<? extends V> mappingFunction) {
/*  819 */     Objects.requireNonNull(mappingFunction);
/*  820 */     int pos = find(k);
/*  821 */     if (pos >= 0)
/*  822 */       return this.value[pos]; 
/*  823 */     V newValue = mappingFunction.apply(k);
/*  824 */     insert(-pos - 1, k, newValue);
/*  825 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/*  831 */     Objects.requireNonNull(remappingFunction);
/*  832 */     int pos = find(k);
/*  833 */     if (pos < 0)
/*  834 */       return this.defRetValue; 
/*  835 */     V newValue = remappingFunction.apply(Long.valueOf(k), this.value[pos]);
/*  836 */     if (newValue == null) {
/*  837 */       if (k == 0L) {
/*  838 */         removeNullEntry();
/*      */       } else {
/*  840 */         removeEntry(pos);
/*  841 */       }  return this.defRetValue;
/*      */     } 
/*  843 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(long k, BiFunction<? super Long, ? super V, ? extends V> remappingFunction) {
/*  849 */     Objects.requireNonNull(remappingFunction);
/*  850 */     int pos = find(k);
/*  851 */     V newValue = remappingFunction.apply(Long.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  852 */     if (newValue == null) {
/*  853 */       if (pos >= 0)
/*  854 */         if (k == 0L) {
/*  855 */           removeNullEntry();
/*      */         } else {
/*  857 */           removeEntry(pos);
/*      */         }  
/*  859 */       return this.defRetValue;
/*      */     } 
/*  861 */     V newVal = newValue;
/*  862 */     if (pos < 0) {
/*  863 */       insert(-pos - 1, k, newVal);
/*  864 */       return newVal;
/*      */     } 
/*  866 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(long k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  872 */     Objects.requireNonNull(remappingFunction);
/*  873 */     int pos = find(k);
/*  874 */     if (pos < 0 || this.value[pos] == null) {
/*  875 */       if (v == null)
/*  876 */         return this.defRetValue; 
/*  877 */       insert(-pos - 1, k, v);
/*  878 */       return v;
/*      */     } 
/*  880 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  881 */     if (newValue == null) {
/*  882 */       if (k == 0L) {
/*  883 */         removeNullEntry();
/*      */       } else {
/*  885 */         removeEntry(pos);
/*  886 */       }  return this.defRetValue;
/*      */     } 
/*  888 */     this.value[pos] = newValue; return newValue;
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
/*  903 */     Arrays.fill(this.key, 0L);
/*  904 */     Arrays.fill((Object[])this.value, (Object)null);
/*  905 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  909 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  913 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Long2ObjectMap.Entry<V>, Map.Entry<Long, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  925 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public long getLongKey() {
/*  931 */       return Long2ObjectLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  935 */       return Long2ObjectLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  939 */       V oldValue = Long2ObjectLinkedOpenHashMap.this.value[this.index];
/*  940 */       Long2ObjectLinkedOpenHashMap.this.value[this.index] = v;
/*  941 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getKey() {
/*  951 */       return Long.valueOf(Long2ObjectLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  956 */       if (!(o instanceof Map.Entry))
/*  957 */         return false; 
/*  958 */       Map.Entry<Long, V> e = (Map.Entry<Long, V>)o;
/*  959 */       return (Long2ObjectLinkedOpenHashMap.this.key[this.index] == ((Long)e.getKey()).longValue() && 
/*  960 */         Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  964 */       return HashCommon.long2int(Long2ObjectLinkedOpenHashMap.this.key[this.index]) ^ (
/*  965 */         (Long2ObjectLinkedOpenHashMap.this.value[this.index] == null) ? 0 : Long2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  969 */       return Long2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Long2ObjectLinkedOpenHashMap.this.value[this.index];
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
/*  980 */     if (this.size == 0) {
/*  981 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  984 */     if (this.first == i) {
/*  985 */       this.first = (int)this.link[i];
/*  986 */       if (0 <= this.first)
/*      */       {
/*  988 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  992 */     if (this.last == i) {
/*  993 */       this.last = (int)(this.link[i] >>> 32L);
/*  994 */       if (0 <= this.last)
/*      */       {
/*  996 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1000 */     long linki = this.link[i];
/* 1001 */     int prev = (int)(linki >>> 32L);
/* 1002 */     int next = (int)linki;
/* 1003 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1004 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1017 */     if (this.size == 1) {
/* 1018 */       this.first = this.last = d;
/*      */       
/* 1020 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1023 */     if (this.first == s) {
/* 1024 */       this.first = d;
/* 1025 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1026 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1029 */     if (this.last == s) {
/* 1030 */       this.last = d;
/* 1031 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1032 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1035 */     long links = this.link[s];
/* 1036 */     int prev = (int)(links >>> 32L);
/* 1037 */     int next = (int)links;
/* 1038 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1039 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1040 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long firstLongKey() {
/* 1049 */     if (this.size == 0)
/* 1050 */       throw new NoSuchElementException(); 
/* 1051 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long lastLongKey() {
/* 1060 */     if (this.size == 0)
/* 1061 */       throw new NoSuchElementException(); 
/* 1062 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap<V> tailMap(long from) {
/* 1071 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap<V> headMap(long to) {
/* 1080 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap<V> subMap(long from, long to) {
/* 1089 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LongComparator comparator() {
/* 1098 */     return null;
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
/* 1113 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1119 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1124 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1130 */     int index = -1;
/*      */     protected MapIterator() {
/* 1132 */       this.next = Long2ObjectLinkedOpenHashMap.this.first;
/* 1133 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(long from) {
/* 1136 */       if (from == 0L) {
/* 1137 */         if (Long2ObjectLinkedOpenHashMap.this.containsNullKey) {
/* 1138 */           this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[Long2ObjectLinkedOpenHashMap.this.n];
/* 1139 */           this.prev = Long2ObjectLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1142 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1144 */       if (Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.last] == from) {
/* 1145 */         this.prev = Long2ObjectLinkedOpenHashMap.this.last;
/* 1146 */         this.index = Long2ObjectLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1150 */       int pos = (int)HashCommon.mix(from) & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */       
/* 1152 */       while (Long2ObjectLinkedOpenHashMap.this.key[pos] != 0L) {
/* 1153 */         if (Long2ObjectLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1155 */           this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[pos];
/* 1156 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1159 */         pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1161 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1164 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1167 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1170 */       if (this.index >= 0)
/*      */         return; 
/* 1172 */       if (this.prev == -1) {
/* 1173 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1176 */       if (this.next == -1) {
/* 1177 */         this.index = Long2ObjectLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1180 */       int pos = Long2ObjectLinkedOpenHashMap.this.first;
/* 1181 */       this.index = 1;
/* 1182 */       while (pos != this.prev) {
/* 1183 */         pos = (int)Long2ObjectLinkedOpenHashMap.this.link[pos];
/* 1184 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1188 */       ensureIndexKnown();
/* 1189 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1192 */       ensureIndexKnown();
/* 1193 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1196 */       if (!hasNext())
/* 1197 */         throw new NoSuchElementException(); 
/* 1198 */       this.curr = this.next;
/* 1199 */       this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1200 */       this.prev = this.curr;
/* 1201 */       if (this.index >= 0)
/* 1202 */         this.index++; 
/* 1203 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1206 */       if (!hasPrevious())
/* 1207 */         throw new NoSuchElementException(); 
/* 1208 */       this.curr = this.prev;
/* 1209 */       this.prev = (int)(Long2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1210 */       this.next = this.curr;
/* 1211 */       if (this.index >= 0)
/* 1212 */         this.index--; 
/* 1213 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1216 */       ensureIndexKnown();
/* 1217 */       if (this.curr == -1)
/* 1218 */         throw new IllegalStateException(); 
/* 1219 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1224 */         this.index--;
/* 1225 */         this.prev = (int)(Long2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1227 */         this.next = (int)Long2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1228 */       }  Long2ObjectLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1233 */       if (this.prev == -1) {
/* 1234 */         Long2ObjectLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1236 */         Long2ObjectLinkedOpenHashMap.this.link[this.prev] = Long2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (Long2ObjectLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1237 */       }  if (this.next == -1) {
/* 1238 */         Long2ObjectLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1240 */         Long2ObjectLinkedOpenHashMap.this.link[this.next] = Long2ObjectLinkedOpenHashMap.this.link[this.next] ^ (Long2ObjectLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1241 */       }  int pos = this.curr;
/* 1242 */       this.curr = -1;
/* 1243 */       if (pos == Long2ObjectLinkedOpenHashMap.this.n) {
/* 1244 */         Long2ObjectLinkedOpenHashMap.this.containsNullKey = false;
/* 1245 */         Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1248 */         long[] key = Long2ObjectLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           long curr;
/*      */           int last;
/* 1252 */           pos = (last = pos) + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1254 */             if ((curr = key[pos]) == 0L) {
/* 1255 */               key[last] = 0L;
/* 1256 */               Long2ObjectLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1259 */             int slot = (int)HashCommon.mix(curr) & Long2ObjectLinkedOpenHashMap.this.mask;
/* 1260 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1262 */             pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1264 */           key[last] = curr;
/* 1265 */           Long2ObjectLinkedOpenHashMap.this.value[last] = Long2ObjectLinkedOpenHashMap.this.value[pos];
/* 1266 */           if (this.next == pos)
/* 1267 */             this.next = last; 
/* 1268 */           if (this.prev == pos)
/* 1269 */             this.prev = last; 
/* 1270 */           Long2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1275 */       int i = n;
/* 1276 */       while (i-- != 0 && hasNext())
/* 1277 */         nextEntry(); 
/* 1278 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1281 */       int i = n;
/* 1282 */       while (i-- != 0 && hasPrevious())
/* 1283 */         previousEntry(); 
/* 1284 */       return n - i - 1;
/*      */     }
/*      */     public void set(Long2ObjectMap.Entry<V> ok) {
/* 1287 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Long2ObjectMap.Entry<V> ok) {
/* 1290 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Long2ObjectMap.Entry<V>> { private Long2ObjectLinkedOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(long from) {
/* 1298 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry next() {
/* 1302 */       return this.entry = new Long2ObjectLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
/* 1306 */       return this.entry = new Long2ObjectLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1310 */       super.remove();
/* 1311 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1315 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Long2ObjectMap.Entry<V>> { final Long2ObjectLinkedOpenHashMap<V>.MapEntry entry = new Long2ObjectLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(long from) {
/* 1319 */       super(from);
/*      */     }
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry next() {
/* 1323 */       this.entry.index = nextEntry();
/* 1324 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Long2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
/* 1328 */       this.entry.index = previousEntry();
/* 1329 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Long2ObjectMap.Entry<V>> implements Long2ObjectSortedMap.FastSortedEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>> iterator() {
/* 1337 */       return (ObjectBidirectionalIterator<Long2ObjectMap.Entry<V>>)new Long2ObjectLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Long2ObjectMap.Entry<V>> comparator() {
/* 1341 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> subSet(Long2ObjectMap.Entry<V> fromElement, Long2ObjectMap.Entry<V> toElement) {
/* 1346 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> headSet(Long2ObjectMap.Entry<V> toElement) {
/* 1350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Long2ObjectMap.Entry<V>> tailSet(Long2ObjectMap.Entry<V> fromElement) {
/* 1354 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Long2ObjectMap.Entry<V> first() {
/* 1358 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0)
/* 1359 */         throw new NoSuchElementException(); 
/* 1360 */       return new Long2ObjectLinkedOpenHashMap.MapEntry(Long2ObjectLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Long2ObjectMap.Entry<V> last() {
/* 1364 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0)
/* 1365 */         throw new NoSuchElementException(); 
/* 1366 */       return new Long2ObjectLinkedOpenHashMap.MapEntry(Long2ObjectLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1371 */       if (!(o instanceof Map.Entry))
/* 1372 */         return false; 
/* 1373 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1374 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1375 */         return false; 
/* 1376 */       long k = ((Long)e.getKey()).longValue();
/* 1377 */       V v = (V)e.getValue();
/* 1378 */       if (k == 0L) {
/* 1379 */         return (Long2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n], v));
/*      */       }
/* 1381 */       long[] key = Long2ObjectLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1384 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/* 1385 */         return false; 
/* 1386 */       if (k == curr) {
/* 1387 */         return Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/* 1390 */         if ((curr = key[pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/* 1391 */           return false; 
/* 1392 */         if (k == curr) {
/* 1393 */           return Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1399 */       if (!(o instanceof Map.Entry))
/* 1400 */         return false; 
/* 1401 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1402 */       if (e.getKey() == null || !(e.getKey() instanceof Long))
/* 1403 */         return false; 
/* 1404 */       long k = ((Long)e.getKey()).longValue();
/* 1405 */       V v = (V)e.getValue();
/* 1406 */       if (k == 0L) {
/* 1407 */         if (Long2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n], v)) {
/* 1408 */           Long2ObjectLinkedOpenHashMap.this.removeNullEntry();
/* 1409 */           return true;
/*      */         } 
/* 1411 */         return false;
/*      */       } 
/*      */       
/* 1414 */       long[] key = Long2ObjectLinkedOpenHashMap.this.key;
/*      */       long curr;
/*      */       int pos;
/* 1417 */       if ((curr = key[pos = (int)HashCommon.mix(k) & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/* 1418 */         return false; 
/* 1419 */       if (curr == k) {
/* 1420 */         if (Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1421 */           Long2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1422 */           return true;
/*      */         } 
/* 1424 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1427 */         if ((curr = key[pos = pos + 1 & Long2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/* 1428 */           return false; 
/* 1429 */         if (curr == k && 
/* 1430 */           Objects.equals(Long2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1431 */           Long2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1432 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1439 */       return Long2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1443 */       Long2ObjectLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Long2ObjectMap.Entry<V>> iterator(Long2ObjectMap.Entry<V> from) {
/* 1458 */       return new Long2ObjectLinkedOpenHashMap.EntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Long2ObjectMap.Entry<V>> fastIterator() {
/* 1469 */       return new Long2ObjectLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Long2ObjectMap.Entry<V>> fastIterator(Long2ObjectMap.Entry<V> from) {
/* 1484 */       return new Long2ObjectLinkedOpenHashMap.FastEntryIterator(from.getLongKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Long2ObjectMap.Entry<V>> consumer) {
/* 1489 */       for (int i = Long2ObjectLinkedOpenHashMap.this.size, next = Long2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1490 */         int curr = next;
/* 1491 */         next = (int)Long2ObjectLinkedOpenHashMap.this.link[curr];
/* 1492 */         consumer.accept(new AbstractLong2ObjectMap.BasicEntry<>(Long2ObjectLinkedOpenHashMap.this.key[curr], Long2ObjectLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Long2ObjectMap.Entry<V>> consumer) {
/* 1498 */       AbstractLong2ObjectMap.BasicEntry<V> entry = new AbstractLong2ObjectMap.BasicEntry<>();
/* 1499 */       for (int i = Long2ObjectLinkedOpenHashMap.this.size, next = Long2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1500 */         int curr = next;
/* 1501 */         next = (int)Long2ObjectLinkedOpenHashMap.this.link[curr];
/* 1502 */         entry.key = Long2ObjectLinkedOpenHashMap.this.key[curr];
/* 1503 */         entry.value = Long2ObjectLinkedOpenHashMap.this.value[curr];
/* 1504 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Long2ObjectSortedMap.FastSortedEntrySet<V> long2ObjectEntrySet() {
/* 1510 */     if (this.entries == null)
/* 1511 */       this.entries = new MapEntrySet(); 
/* 1512 */     return this.entries;
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
/* 1525 */       super(k);
/*      */     }
/*      */     
/*      */     public long previousLong() {
/* 1529 */       return Long2ObjectLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public long nextLong() {
/* 1536 */       return Long2ObjectLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractLongSortedSet { private KeySet() {}
/*      */     
/*      */     public LongListIterator iterator(long from) {
/* 1542 */       return new Long2ObjectLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public LongListIterator iterator() {
/* 1546 */       return new Long2ObjectLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(LongConsumer consumer) {
/* 1551 */       if (Long2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1552 */         consumer.accept(Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.n]); 
/* 1553 */       for (int pos = Long2ObjectLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1554 */         long k = Long2ObjectLinkedOpenHashMap.this.key[pos];
/* 1555 */         if (k != 0L)
/* 1556 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1561 */       return Long2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(long k) {
/* 1565 */       return Long2ObjectLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(long k) {
/* 1569 */       int oldSize = Long2ObjectLinkedOpenHashMap.this.size;
/* 1570 */       Long2ObjectLinkedOpenHashMap.this.remove(k);
/* 1571 */       return (Long2ObjectLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1575 */       Long2ObjectLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public long firstLong() {
/* 1579 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0)
/* 1580 */         throw new NoSuchElementException(); 
/* 1581 */       return Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public long lastLong() {
/* 1585 */       if (Long2ObjectLinkedOpenHashMap.this.size == 0)
/* 1586 */         throw new NoSuchElementException(); 
/* 1587 */       return Long2ObjectLinkedOpenHashMap.this.key[Long2ObjectLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public LongComparator comparator() {
/* 1591 */       return null;
/*      */     }
/*      */     
/*      */     public LongSortedSet tailSet(long from) {
/* 1595 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet headSet(long to) {
/* 1599 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public LongSortedSet subSet(long from, long to) {
/* 1603 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public LongSortedSet keySet() {
/* 1608 */     if (this.keys == null)
/* 1609 */       this.keys = new KeySet(); 
/* 1610 */     return this.keys;
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
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     public V previous() {
/* 1624 */       return Long2ObjectLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1631 */       return Long2ObjectLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1636 */     if (this.values == null)
/* 1637 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1640 */             return (ObjectIterator<V>)new Long2ObjectLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1644 */             return Long2ObjectLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1648 */             return Long2ObjectLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1652 */             Long2ObjectLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1657 */             if (Long2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1658 */               consumer.accept(Long2ObjectLinkedOpenHashMap.this.value[Long2ObjectLinkedOpenHashMap.this.n]); 
/* 1659 */             for (int pos = Long2ObjectLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1660 */               if (Long2ObjectLinkedOpenHashMap.this.key[pos] != 0L)
/* 1661 */                 consumer.accept(Long2ObjectLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1664 */     return this.values;
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
/* 1681 */     return trim(this.size);
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
/* 1705 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1706 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1707 */       return true; 
/*      */     try {
/* 1709 */       rehash(l);
/* 1710 */     } catch (OutOfMemoryError cantDoIt) {
/* 1711 */       return false;
/*      */     } 
/* 1713 */     return true;
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
/* 1729 */     long[] key = this.key;
/* 1730 */     V[] value = this.value;
/* 1731 */     int mask = newN - 1;
/* 1732 */     long[] newKey = new long[newN + 1];
/* 1733 */     V[] newValue = (V[])new Object[newN + 1];
/* 1734 */     int i = this.first, prev = -1, newPrev = -1;
/* 1735 */     long[] link = this.link;
/* 1736 */     long[] newLink = new long[newN + 1];
/* 1737 */     this.first = -1;
/* 1738 */     for (int j = this.size; j-- != 0; ) {
/* 1739 */       int pos; if (key[i] == 0L) {
/* 1740 */         pos = newN;
/*      */       } else {
/* 1742 */         pos = (int)HashCommon.mix(key[i]) & mask;
/* 1743 */         while (newKey[pos] != 0L)
/* 1744 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1746 */       newKey[pos] = key[i];
/* 1747 */       newValue[pos] = value[i];
/* 1748 */       if (prev != -1) {
/* 1749 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1750 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1751 */         newPrev = pos;
/*      */       } else {
/* 1753 */         newPrev = this.first = pos;
/*      */         
/* 1755 */         newLink[pos] = -1L;
/*      */       } 
/* 1757 */       int t = i;
/* 1758 */       i = (int)link[i];
/* 1759 */       prev = t;
/*      */     } 
/* 1761 */     this.link = newLink;
/* 1762 */     this.last = newPrev;
/* 1763 */     if (newPrev != -1)
/*      */     {
/* 1765 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1766 */     this.n = newN;
/* 1767 */     this.mask = mask;
/* 1768 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1769 */     this.key = newKey;
/* 1770 */     this.value = newValue;
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
/*      */   public Long2ObjectLinkedOpenHashMap<V> clone() {
/*      */     Long2ObjectLinkedOpenHashMap<V> c;
/*      */     try {
/* 1787 */       c = (Long2ObjectLinkedOpenHashMap<V>)super.clone();
/* 1788 */     } catch (CloneNotSupportedException cantHappen) {
/* 1789 */       throw new InternalError();
/*      */     } 
/* 1791 */     c.keys = null;
/* 1792 */     c.values = null;
/* 1793 */     c.entries = null;
/* 1794 */     c.containsNullKey = this.containsNullKey;
/* 1795 */     c.key = (long[])this.key.clone();
/* 1796 */     c.value = (V[])this.value.clone();
/* 1797 */     c.link = (long[])this.link.clone();
/* 1798 */     return c;
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
/* 1811 */     int h = 0;
/* 1812 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1813 */       while (this.key[i] == 0L)
/* 1814 */         i++; 
/* 1815 */       t = HashCommon.long2int(this.key[i]);
/* 1816 */       if (this != this.value[i])
/* 1817 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1818 */       h += t;
/* 1819 */       i++;
/*      */     } 
/*      */     
/* 1822 */     if (this.containsNullKey)
/* 1823 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1824 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1827 */     long[] key = this.key;
/* 1828 */     V[] value = this.value;
/* 1829 */     MapIterator i = new MapIterator();
/* 1830 */     s.defaultWriteObject();
/* 1831 */     for (int j = this.size; j-- != 0; ) {
/* 1832 */       int e = i.nextEntry();
/* 1833 */       s.writeLong(key[e]);
/* 1834 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1839 */     s.defaultReadObject();
/* 1840 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1841 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1842 */     this.mask = this.n - 1;
/* 1843 */     long[] key = this.key = new long[this.n + 1];
/* 1844 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1845 */     long[] link = this.link = new long[this.n + 1];
/* 1846 */     int prev = -1;
/* 1847 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1850 */     for (int i = this.size; i-- != 0; ) {
/* 1851 */       int pos; long k = s.readLong();
/* 1852 */       V v = (V)s.readObject();
/* 1853 */       if (k == 0L) {
/* 1854 */         pos = this.n;
/* 1855 */         this.containsNullKey = true;
/*      */       } else {
/* 1857 */         pos = (int)HashCommon.mix(k) & this.mask;
/* 1858 */         while (key[pos] != 0L)
/* 1859 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1861 */       key[pos] = k;
/* 1862 */       value[pos] = v;
/* 1863 */       if (this.first != -1) {
/* 1864 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1865 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1866 */         prev = pos; continue;
/*      */       } 
/* 1868 */       prev = this.first = pos;
/*      */       
/* 1870 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1873 */     this.last = prev;
/* 1874 */     if (prev != -1)
/*      */     {
/* 1876 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\longs\Long2ObjectLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */