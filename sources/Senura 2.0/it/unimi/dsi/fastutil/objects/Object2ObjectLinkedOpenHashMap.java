/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ import java.util.function.Consumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2ObjectLinkedOpenHashMap<K, V>
/*      */   extends AbstractObject2ObjectSortedMap<K, V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
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
/*      */   protected transient Object2ObjectSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectLinkedOpenHashMap(int expected, float f) {
/*  152 */     if (f <= 0.0F || f > 1.0F)
/*  153 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  154 */     if (expected < 0)
/*  155 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  156 */     this.f = f;
/*  157 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  158 */     this.mask = this.n - 1;
/*  159 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  160 */     this.key = (K[])new Object[this.n + 1];
/*  161 */     this.value = (V[])new Object[this.n + 1];
/*  162 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectLinkedOpenHashMap() {
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
/*      */   public Object2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
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
/*      */   public Object2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
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
/*      */   public Object2ObjectLinkedOpenHashMap(Object2ObjectMap<K, V> m, float f) {
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
/*      */   public Object2ObjectLinkedOpenHashMap(Object2ObjectMap<K, V> m) {
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
/*      */   public Object2ObjectLinkedOpenHashMap(K[] k, V[] v, float f) {
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
/*      */   public Object2ObjectLinkedOpenHashMap(K[] k, V[] v) {
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
/*  285 */     this.key[this.n] = null;
/*  286 */     V oldValue = this.value[this.n];
/*  287 */     this.value[this.n] = null;
/*  288 */     this.size--;
/*  289 */     fixPointers(this.n);
/*  290 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  291 */       rehash(this.n / 2); 
/*  292 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends V> m) {
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
/*      */   private void insert(int pos, K k, V v) {
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
/*      */   public V put(K k, V v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     V oldValue = this.value[pos];
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
/*  370 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  373 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  374 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  376 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  378 */       key[last] = curr;
/*  379 */       this.value[last] = this.value[pos];
/*  380 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(Object k) {
/*  386 */     if (k == null) {
/*  387 */       if (this.containsNullKey)
/*  388 */         return removeNullEntry(); 
/*  389 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  392 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  395 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  396 */       return this.defRetValue; 
/*  397 */     if (k.equals(curr))
/*  398 */       return removeEntry(pos); 
/*      */     while (true) {
/*  400 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  401 */         return this.defRetValue; 
/*  402 */       if (k.equals(curr))
/*  403 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private V setValue(int pos, V v) {
/*  407 */     V oldValue = this.value[pos];
/*  408 */     this.value[pos] = v;
/*  409 */     return oldValue;
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
/*  420 */     if (this.size == 0)
/*  421 */       throw new NoSuchElementException(); 
/*  422 */     int pos = this.first;
/*      */     
/*  424 */     this.first = (int)this.link[pos];
/*  425 */     if (0 <= this.first)
/*      */     {
/*  427 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  429 */     this.size--;
/*  430 */     V v = this.value[pos];
/*  431 */     if (pos == this.n) {
/*  432 */       this.containsNullKey = false;
/*  433 */       this.key[this.n] = null;
/*  434 */       this.value[this.n] = null;
/*      */     } else {
/*  436 */       shiftKeys(pos);
/*  437 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  438 */       rehash(this.n / 2); 
/*  439 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  449 */     if (this.size == 0)
/*  450 */       throw new NoSuchElementException(); 
/*  451 */     int pos = this.last;
/*      */     
/*  453 */     this.last = (int)(this.link[pos] >>> 32L);
/*  454 */     if (0 <= this.last)
/*      */     {
/*  456 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  458 */     this.size--;
/*  459 */     V v = this.value[pos];
/*  460 */     if (pos == this.n) {
/*  461 */       this.containsNullKey = false;
/*  462 */       this.key[this.n] = null;
/*  463 */       this.value[this.n] = null;
/*      */     } else {
/*  465 */       shiftKeys(pos);
/*  466 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  467 */       rehash(this.n / 2); 
/*  468 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  471 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  473 */     if (this.last == i) {
/*  474 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  476 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  478 */       long linki = this.link[i];
/*  479 */       int prev = (int)(linki >>> 32L);
/*  480 */       int next = (int)linki;
/*  481 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  482 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  484 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  485 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  486 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  489 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  491 */     if (this.first == i) {
/*  492 */       this.first = (int)this.link[i];
/*      */       
/*  494 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  496 */       long linki = this.link[i];
/*  497 */       int prev = (int)(linki >>> 32L);
/*  498 */       int next = (int)linki;
/*  499 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  500 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  502 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  503 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  504 */     this.last = i;
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
/*      */   public V getAndMoveToFirst(K k) {
/*  516 */     if (k == null) {
/*  517 */       if (this.containsNullKey) {
/*  518 */         moveIndexToFirst(this.n);
/*  519 */         return this.value[this.n];
/*      */       } 
/*  521 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  524 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  527 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  528 */       return this.defRetValue; 
/*  529 */     if (k.equals(curr)) {
/*  530 */       moveIndexToFirst(pos);
/*  531 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  535 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  536 */         return this.defRetValue; 
/*  537 */       if (k.equals(curr)) {
/*  538 */         moveIndexToFirst(pos);
/*  539 */         return this.value[pos];
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
/*      */   public V getAndMoveToLast(K k) {
/*  553 */     if (k == null) {
/*  554 */       if (this.containsNullKey) {
/*  555 */         moveIndexToLast(this.n);
/*  556 */         return this.value[this.n];
/*      */       } 
/*  558 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  561 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  564 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  565 */       return this.defRetValue; 
/*  566 */     if (k.equals(curr)) {
/*  567 */       moveIndexToLast(pos);
/*  568 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  572 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  573 */         return this.defRetValue; 
/*  574 */       if (k.equals(curr)) {
/*  575 */         moveIndexToLast(pos);
/*  576 */         return this.value[pos];
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
/*      */   public V putAndMoveToFirst(K k, V v) {
/*      */     int pos;
/*  593 */     if (k == null) {
/*  594 */       if (this.containsNullKey) {
/*  595 */         moveIndexToFirst(this.n);
/*  596 */         return setValue(this.n, v);
/*      */       } 
/*  598 */       this.containsNullKey = true;
/*  599 */       pos = this.n;
/*      */     } else {
/*      */       
/*  602 */       K[] key = this.key;
/*      */       K curr;
/*  604 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  605 */         if (curr.equals(k)) {
/*  606 */           moveIndexToFirst(pos);
/*  607 */           return setValue(pos, v);
/*      */         } 
/*  609 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  610 */           if (curr.equals(k)) {
/*  611 */             moveIndexToFirst(pos);
/*  612 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  616 */     }  this.key[pos] = k;
/*  617 */     this.value[pos] = v;
/*  618 */     if (this.size == 0) {
/*  619 */       this.first = this.last = pos;
/*      */       
/*  621 */       this.link[pos] = -1L;
/*      */     } else {
/*  623 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  624 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  625 */       this.first = pos;
/*      */     } 
/*  627 */     if (this.size++ >= this.maxFill) {
/*  628 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  631 */     return this.defRetValue;
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
/*      */   public V putAndMoveToLast(K k, V v) {
/*      */     int pos;
/*  646 */     if (k == null) {
/*  647 */       if (this.containsNullKey) {
/*  648 */         moveIndexToLast(this.n);
/*  649 */         return setValue(this.n, v);
/*      */       } 
/*  651 */       this.containsNullKey = true;
/*  652 */       pos = this.n;
/*      */     } else {
/*      */       
/*  655 */       K[] key = this.key;
/*      */       K curr;
/*  657 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  658 */         if (curr.equals(k)) {
/*  659 */           moveIndexToLast(pos);
/*  660 */           return setValue(pos, v);
/*      */         } 
/*  662 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  663 */           if (curr.equals(k)) {
/*  664 */             moveIndexToLast(pos);
/*  665 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  669 */     }  this.key[pos] = k;
/*  670 */     this.value[pos] = v;
/*  671 */     if (this.size == 0) {
/*  672 */       this.first = this.last = pos;
/*      */       
/*  674 */       this.link[pos] = -1L;
/*      */     } else {
/*  676 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  677 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  678 */       this.last = pos;
/*      */     } 
/*  680 */     if (this.size++ >= this.maxFill) {
/*  681 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  684 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  689 */     if (k == null) {
/*  690 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  692 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  695 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  696 */       return this.defRetValue; 
/*  697 */     if (k.equals(curr)) {
/*  698 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  701 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  702 */         return this.defRetValue; 
/*  703 */       if (k.equals(curr)) {
/*  704 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  710 */     if (k == null) {
/*  711 */       return this.containsNullKey;
/*      */     }
/*  713 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  716 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  717 */       return false; 
/*  718 */     if (k.equals(curr)) {
/*  719 */       return true;
/*      */     }
/*      */     while (true) {
/*  722 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  723 */         return false; 
/*  724 */       if (k.equals(curr))
/*  725 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  730 */     V[] value = this.value;
/*  731 */     K[] key = this.key;
/*  732 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  733 */       return true; 
/*  734 */     for (int i = this.n; i-- != 0;) {
/*  735 */       if (key[i] != null && Objects.equals(value[i], v))
/*  736 */         return true; 
/*  737 */     }  return false;
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
/*  748 */     if (this.size == 0)
/*      */       return; 
/*  750 */     this.size = 0;
/*  751 */     this.containsNullKey = false;
/*  752 */     Arrays.fill((Object[])this.key, (Object)null);
/*  753 */     Arrays.fill((Object[])this.value, (Object)null);
/*  754 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  758 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  762 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2ObjectMap.Entry<K, V>, Map.Entry<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  774 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  780 */       return Object2ObjectLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  784 */       return Object2ObjectLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  788 */       V oldValue = Object2ObjectLinkedOpenHashMap.this.value[this.index];
/*  789 */       Object2ObjectLinkedOpenHashMap.this.value[this.index] = v;
/*  790 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  795 */       if (!(o instanceof Map.Entry))
/*  796 */         return false; 
/*  797 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  798 */       return (Objects.equals(Object2ObjectLinkedOpenHashMap.this.key[this.index], e.getKey()) && 
/*  799 */         Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  803 */       return ((Object2ObjectLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2ObjectLinkedOpenHashMap.this.key[this.index].hashCode()) ^ (
/*  804 */         (Object2ObjectLinkedOpenHashMap.this.value[this.index] == null) ? 0 : Object2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  808 */       return (new StringBuilder()).append(Object2ObjectLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2ObjectLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  819 */     if (this.size == 0) {
/*  820 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  823 */     if (this.first == i) {
/*  824 */       this.first = (int)this.link[i];
/*  825 */       if (0 <= this.first)
/*      */       {
/*  827 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  831 */     if (this.last == i) {
/*  832 */       this.last = (int)(this.link[i] >>> 32L);
/*  833 */       if (0 <= this.last)
/*      */       {
/*  835 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  839 */     long linki = this.link[i];
/*  840 */     int prev = (int)(linki >>> 32L);
/*  841 */     int next = (int)linki;
/*  842 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  843 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  856 */     if (this.size == 1) {
/*  857 */       this.first = this.last = d;
/*      */       
/*  859 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  862 */     if (this.first == s) {
/*  863 */       this.first = d;
/*  864 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  865 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  868 */     if (this.last == s) {
/*  869 */       this.last = d;
/*  870 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  871 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  874 */     long links = this.link[s];
/*  875 */     int prev = (int)(links >>> 32L);
/*  876 */     int next = (int)links;
/*  877 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  878 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  879 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  888 */     if (this.size == 0)
/*  889 */       throw new NoSuchElementException(); 
/*  890 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  899 */     if (this.size == 0)
/*  900 */       throw new NoSuchElementException(); 
/*  901 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> tailMap(K from) {
/*  910 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> headMap(K to) {
/*  919 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap<K, V> subMap(K from, K to) {
/*  928 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  937 */     return null;
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
/*  952 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  958 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  963 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  969 */     int index = -1;
/*      */     protected MapIterator() {
/*  971 */       this.next = Object2ObjectLinkedOpenHashMap.this.first;
/*  972 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/*  975 */       if (from == null) {
/*  976 */         if (Object2ObjectLinkedOpenHashMap.this.containsNullKey) {
/*  977 */           this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[Object2ObjectLinkedOpenHashMap.this.n];
/*  978 */           this.prev = Object2ObjectLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/*  981 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/*  983 */       if (Objects.equals(Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.last], from)) {
/*  984 */         this.prev = Object2ObjectLinkedOpenHashMap.this.last;
/*  985 */         this.index = Object2ObjectLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  989 */       int pos = HashCommon.mix(from.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask;
/*      */       
/*  991 */       while (Object2ObjectLinkedOpenHashMap.this.key[pos] != null) {
/*  992 */         if (Object2ObjectLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/*  994 */           this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[pos];
/*  995 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  998 */         pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1000 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1003 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1006 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1009 */       if (this.index >= 0)
/*      */         return; 
/* 1011 */       if (this.prev == -1) {
/* 1012 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1015 */       if (this.next == -1) {
/* 1016 */         this.index = Object2ObjectLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1019 */       int pos = Object2ObjectLinkedOpenHashMap.this.first;
/* 1020 */       this.index = 1;
/* 1021 */       while (pos != this.prev) {
/* 1022 */         pos = (int)Object2ObjectLinkedOpenHashMap.this.link[pos];
/* 1023 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1027 */       ensureIndexKnown();
/* 1028 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1031 */       ensureIndexKnown();
/* 1032 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1035 */       if (!hasNext())
/* 1036 */         throw new NoSuchElementException(); 
/* 1037 */       this.curr = this.next;
/* 1038 */       this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1039 */       this.prev = this.curr;
/* 1040 */       if (this.index >= 0)
/* 1041 */         this.index++; 
/* 1042 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1045 */       if (!hasPrevious())
/* 1046 */         throw new NoSuchElementException(); 
/* 1047 */       this.curr = this.prev;
/* 1048 */       this.prev = (int)(Object2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1049 */       this.next = this.curr;
/* 1050 */       if (this.index >= 0)
/* 1051 */         this.index--; 
/* 1052 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1055 */       ensureIndexKnown();
/* 1056 */       if (this.curr == -1)
/* 1057 */         throw new IllegalStateException(); 
/* 1058 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1063 */         this.index--;
/* 1064 */         this.prev = (int)(Object2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1066 */         this.next = (int)Object2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1067 */       }  Object2ObjectLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1072 */       if (this.prev == -1) {
/* 1073 */         Object2ObjectLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1075 */         Object2ObjectLinkedOpenHashMap.this.link[this.prev] = Object2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (Object2ObjectLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1076 */       }  if (this.next == -1) {
/* 1077 */         Object2ObjectLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1079 */         Object2ObjectLinkedOpenHashMap.this.link[this.next] = Object2ObjectLinkedOpenHashMap.this.link[this.next] ^ (Object2ObjectLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1080 */       }  int pos = this.curr;
/* 1081 */       this.curr = -1;
/* 1082 */       if (pos == Object2ObjectLinkedOpenHashMap.this.n) {
/* 1083 */         Object2ObjectLinkedOpenHashMap.this.containsNullKey = false;
/* 1084 */         Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.n] = null;
/* 1085 */         Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1088 */         K[] key = Object2ObjectLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1092 */           pos = (last = pos) + 1 & Object2ObjectLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1094 */             if ((curr = key[pos]) == null) {
/* 1095 */               key[last] = null;
/* 1096 */               Object2ObjectLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1099 */             int slot = HashCommon.mix(curr.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask;
/* 1100 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1102 */             pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1104 */           key[last] = curr;
/* 1105 */           Object2ObjectLinkedOpenHashMap.this.value[last] = Object2ObjectLinkedOpenHashMap.this.value[pos];
/* 1106 */           if (this.next == pos)
/* 1107 */             this.next = last; 
/* 1108 */           if (this.prev == pos)
/* 1109 */             this.prev = last; 
/* 1110 */           Object2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1115 */       int i = n;
/* 1116 */       while (i-- != 0 && hasNext())
/* 1117 */         nextEntry(); 
/* 1118 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1121 */       int i = n;
/* 1122 */       while (i-- != 0 && hasPrevious())
/* 1123 */         previousEntry(); 
/* 1124 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2ObjectMap.Entry<K, V> ok) {
/* 1127 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2ObjectMap.Entry<K, V> ok) {
/* 1130 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> { private Object2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1138 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1142 */       return this.entry = new Object2ObjectLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1146 */       return this.entry = new Object2ObjectLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1150 */       super.remove();
/* 1151 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1155 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2ObjectMap.Entry<K, V>> { final Object2ObjectLinkedOpenHashMap<K, V>.MapEntry entry = new Object2ObjectLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1159 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1163 */       this.entry.index = nextEntry();
/* 1164 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1168 */       this.entry.index = previousEntry();
/* 1169 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ObjectMap.Entry<K, V>> implements Object2ObjectSortedMap.FastSortedEntrySet<K, V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ObjectMap.Entry<K, V>> iterator() {
/* 1177 */       return new Object2ObjectLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2ObjectMap.Entry<K, V>> comparator() {
/* 1181 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> subSet(Object2ObjectMap.Entry<K, V> fromElement, Object2ObjectMap.Entry<K, V> toElement) {
/* 1186 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> headSet(Object2ObjectMap.Entry<K, V> toElement) {
/* 1190 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ObjectMap.Entry<K, V>> tailSet(Object2ObjectMap.Entry<K, V> fromElement) {
/* 1194 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> first() {
/* 1198 */       if (Object2ObjectLinkedOpenHashMap.this.size == 0)
/* 1199 */         throw new NoSuchElementException(); 
/* 1200 */       return new Object2ObjectLinkedOpenHashMap.MapEntry(Object2ObjectLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2ObjectMap.Entry<K, V> last() {
/* 1204 */       if (Object2ObjectLinkedOpenHashMap.this.size == 0)
/* 1205 */         throw new NoSuchElementException(); 
/* 1206 */       return new Object2ObjectLinkedOpenHashMap.MapEntry(Object2ObjectLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1211 */       if (!(o instanceof Map.Entry))
/* 1212 */         return false; 
/* 1213 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1214 */       K k = (K)e.getKey();
/* 1215 */       V v = (V)e.getValue();
/* 1216 */       if (k == null) {
/* 1217 */         return (Object2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.n], v));
/*      */       }
/* 1219 */       K[] key = Object2ObjectLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1222 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask]) == null)
/* 1223 */         return false; 
/* 1224 */       if (k.equals(curr)) {
/* 1225 */         return Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/* 1228 */         if ((curr = key[pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask]) == null)
/* 1229 */           return false; 
/* 1230 */         if (k.equals(curr)) {
/* 1231 */           return Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1237 */       if (!(o instanceof Map.Entry))
/* 1238 */         return false; 
/* 1239 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1240 */       K k = (K)e.getKey();
/* 1241 */       V v = (V)e.getValue();
/* 1242 */       if (k == null) {
/* 1243 */         if (Object2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.n], v)) {
/* 1244 */           Object2ObjectLinkedOpenHashMap.this.removeNullEntry();
/* 1245 */           return true;
/*      */         } 
/* 1247 */         return false;
/*      */       } 
/*      */       
/* 1250 */       K[] key = Object2ObjectLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1253 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ObjectLinkedOpenHashMap.this.mask]) == null)
/* 1254 */         return false; 
/* 1255 */       if (curr.equals(k)) {
/* 1256 */         if (Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1257 */           Object2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1258 */           return true;
/*      */         } 
/* 1260 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1263 */         if ((curr = key[pos = pos + 1 & Object2ObjectLinkedOpenHashMap.this.mask]) == null)
/* 1264 */           return false; 
/* 1265 */         if (curr.equals(k) && 
/* 1266 */           Objects.equals(Object2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1267 */           Object2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1268 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1275 */       return Object2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1279 */       Object2ObjectLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ObjectMap.Entry<K, V>> iterator(Object2ObjectMap.Entry<K, V> from) {
/* 1294 */       return new Object2ObjectLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator() {
/* 1305 */       return new Object2ObjectLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ObjectMap.Entry<K, V>> fastIterator(Object2ObjectMap.Entry<K, V> from) {
/* 1320 */       return new Object2ObjectLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/* 1325 */       for (int i = Object2ObjectLinkedOpenHashMap.this.size, next = Object2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1326 */         int curr = next;
/* 1327 */         next = (int)Object2ObjectLinkedOpenHashMap.this.link[curr];
/* 1328 */         consumer.accept(new AbstractObject2ObjectMap.BasicEntry<>(Object2ObjectLinkedOpenHashMap.this.key[curr], Object2ObjectLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ObjectMap.Entry<K, V>> consumer) {
/* 1334 */       AbstractObject2ObjectMap.BasicEntry<K, V> entry = new AbstractObject2ObjectMap.BasicEntry<>();
/* 1335 */       for (int i = Object2ObjectLinkedOpenHashMap.this.size, next = Object2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1336 */         int curr = next;
/* 1337 */         next = (int)Object2ObjectLinkedOpenHashMap.this.link[curr];
/* 1338 */         entry.key = Object2ObjectLinkedOpenHashMap.this.key[curr];
/* 1339 */         entry.value = Object2ObjectLinkedOpenHashMap.this.value[curr];
/* 1340 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2ObjectSortedMap.FastSortedEntrySet<K, V> object2ObjectEntrySet() {
/* 1346 */     if (this.entries == null)
/* 1347 */       this.entries = new MapEntrySet(); 
/* 1348 */     return this.entries;
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
/* 1361 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1365 */       return Object2ObjectLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1372 */       return Object2ObjectLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1378 */       return new Object2ObjectLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1382 */       return new Object2ObjectLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1387 */       if (Object2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1388 */         consumer.accept(Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.n]); 
/* 1389 */       for (int pos = Object2ObjectLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1390 */         K k = Object2ObjectLinkedOpenHashMap.this.key[pos];
/* 1391 */         if (k != null)
/* 1392 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1397 */       return Object2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1401 */       return Object2ObjectLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1405 */       int oldSize = Object2ObjectLinkedOpenHashMap.this.size;
/* 1406 */       Object2ObjectLinkedOpenHashMap.this.remove(k);
/* 1407 */       return (Object2ObjectLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1411 */       Object2ObjectLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1415 */       if (Object2ObjectLinkedOpenHashMap.this.size == 0)
/* 1416 */         throw new NoSuchElementException(); 
/* 1417 */       return Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1421 */       if (Object2ObjectLinkedOpenHashMap.this.size == 0)
/* 1422 */         throw new NoSuchElementException(); 
/* 1423 */       return Object2ObjectLinkedOpenHashMap.this.key[Object2ObjectLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1427 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1431 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1435 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1439 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1444 */     if (this.keys == null)
/* 1445 */       this.keys = new KeySet(); 
/* 1446 */     return this.keys;
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
/* 1460 */       return Object2ObjectLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1467 */       return Object2ObjectLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1472 */     if (this.values == null)
/* 1473 */       this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1476 */             return new Object2ObjectLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1480 */             return Object2ObjectLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1484 */             return Object2ObjectLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1488 */             Object2ObjectLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1493 */             if (Object2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1494 */               consumer.accept(Object2ObjectLinkedOpenHashMap.this.value[Object2ObjectLinkedOpenHashMap.this.n]); 
/* 1495 */             for (int pos = Object2ObjectLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1496 */               if (Object2ObjectLinkedOpenHashMap.this.key[pos] != null)
/* 1497 */                 consumer.accept(Object2ObjectLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1500 */     return this.values;
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
/* 1517 */     return trim(this.size);
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
/* 1541 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1542 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1543 */       return true; 
/*      */     try {
/* 1545 */       rehash(l);
/* 1546 */     } catch (OutOfMemoryError cantDoIt) {
/* 1547 */       return false;
/*      */     } 
/* 1549 */     return true;
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
/* 1565 */     K[] key = this.key;
/* 1566 */     V[] value = this.value;
/* 1567 */     int mask = newN - 1;
/* 1568 */     K[] newKey = (K[])new Object[newN + 1];
/* 1569 */     V[] newValue = (V[])new Object[newN + 1];
/* 1570 */     int i = this.first, prev = -1, newPrev = -1;
/* 1571 */     long[] link = this.link;
/* 1572 */     long[] newLink = new long[newN + 1];
/* 1573 */     this.first = -1;
/* 1574 */     for (int j = this.size; j-- != 0; ) {
/* 1575 */       int pos; if (key[i] == null) {
/* 1576 */         pos = newN;
/*      */       } else {
/* 1578 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1579 */         while (newKey[pos] != null)
/* 1580 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1582 */       newKey[pos] = key[i];
/* 1583 */       newValue[pos] = value[i];
/* 1584 */       if (prev != -1) {
/* 1585 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1586 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1587 */         newPrev = pos;
/*      */       } else {
/* 1589 */         newPrev = this.first = pos;
/*      */         
/* 1591 */         newLink[pos] = -1L;
/*      */       } 
/* 1593 */       int t = i;
/* 1594 */       i = (int)link[i];
/* 1595 */       prev = t;
/*      */     } 
/* 1597 */     this.link = newLink;
/* 1598 */     this.last = newPrev;
/* 1599 */     if (newPrev != -1)
/*      */     {
/* 1601 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1602 */     this.n = newN;
/* 1603 */     this.mask = mask;
/* 1604 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1605 */     this.key = newKey;
/* 1606 */     this.value = newValue;
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
/*      */   public Object2ObjectLinkedOpenHashMap<K, V> clone() {
/*      */     Object2ObjectLinkedOpenHashMap<K, V> c;
/*      */     try {
/* 1623 */       c = (Object2ObjectLinkedOpenHashMap<K, V>)super.clone();
/* 1624 */     } catch (CloneNotSupportedException cantHappen) {
/* 1625 */       throw new InternalError();
/*      */     } 
/* 1627 */     c.keys = null;
/* 1628 */     c.values = null;
/* 1629 */     c.entries = null;
/* 1630 */     c.containsNullKey = this.containsNullKey;
/* 1631 */     c.key = (K[])this.key.clone();
/* 1632 */     c.value = (V[])this.value.clone();
/* 1633 */     c.link = (long[])this.link.clone();
/* 1634 */     return c;
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
/* 1647 */     int h = 0;
/* 1648 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1649 */       while (this.key[i] == null)
/* 1650 */         i++; 
/* 1651 */       if (this != this.key[i])
/* 1652 */         t = this.key[i].hashCode(); 
/* 1653 */       if (this != this.value[i])
/* 1654 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1655 */       h += t;
/* 1656 */       i++;
/*      */     } 
/*      */     
/* 1659 */     if (this.containsNullKey)
/* 1660 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1661 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1664 */     K[] key = this.key;
/* 1665 */     V[] value = this.value;
/* 1666 */     MapIterator i = new MapIterator();
/* 1667 */     s.defaultWriteObject();
/* 1668 */     for (int j = this.size; j-- != 0; ) {
/* 1669 */       int e = i.nextEntry();
/* 1670 */       s.writeObject(key[e]);
/* 1671 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1676 */     s.defaultReadObject();
/* 1677 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1678 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1679 */     this.mask = this.n - 1;
/* 1680 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1681 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1682 */     long[] link = this.link = new long[this.n + 1];
/* 1683 */     int prev = -1;
/* 1684 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1687 */     for (int i = this.size; i-- != 0; ) {
/* 1688 */       int pos; K k = (K)s.readObject();
/* 1689 */       V v = (V)s.readObject();
/* 1690 */       if (k == null) {
/* 1691 */         pos = this.n;
/* 1692 */         this.containsNullKey = true;
/*      */       } else {
/* 1694 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1695 */         while (key[pos] != null)
/* 1696 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1698 */       key[pos] = k;
/* 1699 */       value[pos] = v;
/* 1700 */       if (this.first != -1) {
/* 1701 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1702 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1703 */         prev = pos; continue;
/*      */       } 
/* 1705 */       prev = this.first = pos;
/*      */       
/* 1707 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1710 */     this.last = prev;
/* 1711 */     if (prev != -1)
/*      */     {
/* 1713 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ObjectLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */