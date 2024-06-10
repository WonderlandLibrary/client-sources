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
/*      */ public class Object2ReferenceLinkedOpenHashMap<K, V>
/*      */   extends AbstractObject2ReferenceSortedMap<K, V>
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
/*      */   protected transient Object2ReferenceSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap(int expected, float f) {
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
/*      */   public Object2ReferenceLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceLinkedOpenHashMap() {
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
/*      */   public Object2ReferenceLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
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
/*      */   public Object2ReferenceLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
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
/*      */   public Object2ReferenceLinkedOpenHashMap(Object2ReferenceMap<K, V> m, float f) {
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
/*      */   public Object2ReferenceLinkedOpenHashMap(Object2ReferenceMap<K, V> m) {
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
/*      */   public Object2ReferenceLinkedOpenHashMap(K[] k, V[] v, float f) {
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
/*      */   public Object2ReferenceLinkedOpenHashMap(K[] k, V[] v) {
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
/*  732 */     if (this.containsNullKey && value[this.n] == v)
/*  733 */       return true; 
/*  734 */     for (int i = this.n; i-- != 0;) {
/*  735 */       if (key[i] != null && value[i] == v)
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
/*      */     implements Object2ReferenceMap.Entry<K, V>, Map.Entry<K, V>
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
/*  780 */       return Object2ReferenceLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  784 */       return Object2ReferenceLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  788 */       V oldValue = Object2ReferenceLinkedOpenHashMap.this.value[this.index];
/*  789 */       Object2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
/*  790 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  795 */       if (!(o instanceof Map.Entry))
/*  796 */         return false; 
/*  797 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  798 */       return (Objects.equals(Object2ReferenceLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2ReferenceLinkedOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  802 */       return ((Object2ReferenceLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2ReferenceLinkedOpenHashMap.this.key[this.index].hashCode()) ^ (
/*  803 */         (Object2ReferenceLinkedOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Object2ReferenceLinkedOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  807 */       return (new StringBuilder()).append(Object2ReferenceLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2ReferenceLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  818 */     if (this.size == 0) {
/*  819 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  822 */     if (this.first == i) {
/*  823 */       this.first = (int)this.link[i];
/*  824 */       if (0 <= this.first)
/*      */       {
/*  826 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  830 */     if (this.last == i) {
/*  831 */       this.last = (int)(this.link[i] >>> 32L);
/*  832 */       if (0 <= this.last)
/*      */       {
/*  834 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  838 */     long linki = this.link[i];
/*  839 */     int prev = (int)(linki >>> 32L);
/*  840 */     int next = (int)linki;
/*  841 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  842 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  855 */     if (this.size == 1) {
/*  856 */       this.first = this.last = d;
/*      */       
/*  858 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  861 */     if (this.first == s) {
/*  862 */       this.first = d;
/*  863 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  864 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  867 */     if (this.last == s) {
/*  868 */       this.last = d;
/*  869 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  870 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  873 */     long links = this.link[s];
/*  874 */     int prev = (int)(links >>> 32L);
/*  875 */     int next = (int)links;
/*  876 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  877 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  878 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  887 */     if (this.size == 0)
/*  888 */       throw new NoSuchElementException(); 
/*  889 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  898 */     if (this.size == 0)
/*  899 */       throw new NoSuchElementException(); 
/*  900 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> tailMap(K from) {
/*  909 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> headMap(K to) {
/*  918 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap<K, V> subMap(K from, K to) {
/*  927 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  936 */     return null;
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
/*  951 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  957 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  962 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  968 */     int index = -1;
/*      */     protected MapIterator() {
/*  970 */       this.next = Object2ReferenceLinkedOpenHashMap.this.first;
/*  971 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/*  974 */       if (from == null) {
/*  975 */         if (Object2ReferenceLinkedOpenHashMap.this.containsNullKey) {
/*  976 */           this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[Object2ReferenceLinkedOpenHashMap.this.n];
/*  977 */           this.prev = Object2ReferenceLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/*  980 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/*  982 */       if (Objects.equals(Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.last], from)) {
/*  983 */         this.prev = Object2ReferenceLinkedOpenHashMap.this.last;
/*  984 */         this.index = Object2ReferenceLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  988 */       int pos = HashCommon.mix(from.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */       
/*  990 */       while (Object2ReferenceLinkedOpenHashMap.this.key[pos] != null) {
/*  991 */         if (Object2ReferenceLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/*  993 */           this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[pos];
/*  994 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  997 */         pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */       } 
/*  999 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1002 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1005 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1008 */       if (this.index >= 0)
/*      */         return; 
/* 1010 */       if (this.prev == -1) {
/* 1011 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1014 */       if (this.next == -1) {
/* 1015 */         this.index = Object2ReferenceLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1018 */       int pos = Object2ReferenceLinkedOpenHashMap.this.first;
/* 1019 */       this.index = 1;
/* 1020 */       while (pos != this.prev) {
/* 1021 */         pos = (int)Object2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1022 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1026 */       ensureIndexKnown();
/* 1027 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1030 */       ensureIndexKnown();
/* 1031 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1034 */       if (!hasNext())
/* 1035 */         throw new NoSuchElementException(); 
/* 1036 */       this.curr = this.next;
/* 1037 */       this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1038 */       this.prev = this.curr;
/* 1039 */       if (this.index >= 0)
/* 1040 */         this.index++; 
/* 1041 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1044 */       if (!hasPrevious())
/* 1045 */         throw new NoSuchElementException(); 
/* 1046 */       this.curr = this.prev;
/* 1047 */       this.prev = (int)(Object2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1048 */       this.next = this.curr;
/* 1049 */       if (this.index >= 0)
/* 1050 */         this.index--; 
/* 1051 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1054 */       ensureIndexKnown();
/* 1055 */       if (this.curr == -1)
/* 1056 */         throw new IllegalStateException(); 
/* 1057 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1062 */         this.index--;
/* 1063 */         this.prev = (int)(Object2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1065 */         this.next = (int)Object2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1066 */       }  Object2ReferenceLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1071 */       if (this.prev == -1) {
/* 1072 */         Object2ReferenceLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1074 */         Object2ReferenceLinkedOpenHashMap.this.link[this.prev] = Object2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ (Object2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1075 */       }  if (this.next == -1) {
/* 1076 */         Object2ReferenceLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1078 */         Object2ReferenceLinkedOpenHashMap.this.link[this.next] = Object2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (Object2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1079 */       }  int pos = this.curr;
/* 1080 */       this.curr = -1;
/* 1081 */       if (pos == Object2ReferenceLinkedOpenHashMap.this.n) {
/* 1082 */         Object2ReferenceLinkedOpenHashMap.this.containsNullKey = false;
/* 1083 */         Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.n] = null;
/* 1084 */         Object2ReferenceLinkedOpenHashMap.this.value[Object2ReferenceLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1087 */         K[] key = Object2ReferenceLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1091 */           pos = (last = pos) + 1 & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1093 */             if ((curr = key[pos]) == null) {
/* 1094 */               key[last] = null;
/* 1095 */               Object2ReferenceLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1098 */             int slot = HashCommon.mix(curr.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask;
/* 1099 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1101 */             pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1103 */           key[last] = curr;
/* 1104 */           Object2ReferenceLinkedOpenHashMap.this.value[last] = Object2ReferenceLinkedOpenHashMap.this.value[pos];
/* 1105 */           if (this.next == pos)
/* 1106 */             this.next = last; 
/* 1107 */           if (this.prev == pos)
/* 1108 */             this.prev = last; 
/* 1109 */           Object2ReferenceLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1114 */       int i = n;
/* 1115 */       while (i-- != 0 && hasNext())
/* 1116 */         nextEntry(); 
/* 1117 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1120 */       int i = n;
/* 1121 */       while (i-- != 0 && hasPrevious())
/* 1122 */         previousEntry(); 
/* 1123 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2ReferenceMap.Entry<K, V> ok) {
/* 1126 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2ReferenceMap.Entry<K, V> ok) {
/* 1129 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> { private Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1137 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1141 */       return this.entry = new Object2ReferenceLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1145 */       return this.entry = new Object2ReferenceLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1149 */       super.remove();
/* 1150 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1154 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2ReferenceMap.Entry<K, V>> { final Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry entry = new Object2ReferenceLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1158 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1162 */       this.entry.index = nextEntry();
/* 1163 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2ReferenceLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1167 */       this.entry.index = previousEntry();
/* 1168 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2ReferenceMap.Entry<K, V>> implements Object2ReferenceSortedMap.FastSortedEntrySet<K, V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2ReferenceMap.Entry<K, V>> iterator() {
/* 1176 */       return new Object2ReferenceLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2ReferenceMap.Entry<K, V>> comparator() {
/* 1180 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> subSet(Object2ReferenceMap.Entry<K, V> fromElement, Object2ReferenceMap.Entry<K, V> toElement) {
/* 1185 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> headSet(Object2ReferenceMap.Entry<K, V> toElement) {
/* 1189 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2ReferenceMap.Entry<K, V>> tailSet(Object2ReferenceMap.Entry<K, V> fromElement) {
/* 1193 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> first() {
/* 1197 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1198 */         throw new NoSuchElementException(); 
/* 1199 */       return new Object2ReferenceLinkedOpenHashMap.MapEntry(Object2ReferenceLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2ReferenceMap.Entry<K, V> last() {
/* 1203 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1204 */         throw new NoSuchElementException(); 
/* 1205 */       return new Object2ReferenceLinkedOpenHashMap.MapEntry(Object2ReferenceLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1210 */       if (!(o instanceof Map.Entry))
/* 1211 */         return false; 
/* 1212 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1213 */       K k = (K)e.getKey();
/* 1214 */       V v = (V)e.getValue();
/* 1215 */       if (k == null) {
/* 1216 */         return (Object2ReferenceLinkedOpenHashMap.this.containsNullKey && Object2ReferenceLinkedOpenHashMap.this.value[Object2ReferenceLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1218 */       K[] key = Object2ReferenceLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1221 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask]) == null)
/* 1222 */         return false; 
/* 1223 */       if (k.equals(curr)) {
/* 1224 */         return (Object2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1227 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask]) == null)
/* 1228 */           return false; 
/* 1229 */         if (k.equals(curr)) {
/* 1230 */           return (Object2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1236 */       if (!(o instanceof Map.Entry))
/* 1237 */         return false; 
/* 1238 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1239 */       K k = (K)e.getKey();
/* 1240 */       V v = (V)e.getValue();
/* 1241 */       if (k == null) {
/* 1242 */         if (Object2ReferenceLinkedOpenHashMap.this.containsNullKey && Object2ReferenceLinkedOpenHashMap.this.value[Object2ReferenceLinkedOpenHashMap.this.n] == v) {
/* 1243 */           Object2ReferenceLinkedOpenHashMap.this.removeNullEntry();
/* 1244 */           return true;
/*      */         } 
/* 1246 */         return false;
/*      */       } 
/*      */       
/* 1249 */       K[] key = Object2ReferenceLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1252 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2ReferenceLinkedOpenHashMap.this.mask]) == null)
/* 1253 */         return false; 
/* 1254 */       if (curr.equals(k)) {
/* 1255 */         if (Object2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1256 */           Object2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1257 */           return true;
/*      */         } 
/* 1259 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1262 */         if ((curr = key[pos = pos + 1 & Object2ReferenceLinkedOpenHashMap.this.mask]) == null)
/* 1263 */           return false; 
/* 1264 */         if (curr.equals(k) && 
/* 1265 */           Object2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1266 */           Object2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1267 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1274 */       return Object2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1278 */       Object2ReferenceLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> iterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1294 */       return new Object2ReferenceLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator() {
/* 1305 */       return new Object2ReferenceLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2ReferenceMap.Entry<K, V>> fastIterator(Object2ReferenceMap.Entry<K, V> from) {
/* 1321 */       return new Object2ReferenceLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1326 */       for (int i = Object2ReferenceLinkedOpenHashMap.this.size, next = Object2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1327 */         int curr = next;
/* 1328 */         next = (int)Object2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1329 */         consumer.accept(new AbstractObject2ReferenceMap.BasicEntry<>(Object2ReferenceLinkedOpenHashMap.this.key[curr], Object2ReferenceLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2ReferenceMap.Entry<K, V>> consumer) {
/* 1335 */       AbstractObject2ReferenceMap.BasicEntry<K, V> entry = new AbstractObject2ReferenceMap.BasicEntry<>();
/* 1336 */       for (int i = Object2ReferenceLinkedOpenHashMap.this.size, next = Object2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1337 */         int curr = next;
/* 1338 */         next = (int)Object2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1339 */         entry.key = Object2ReferenceLinkedOpenHashMap.this.key[curr];
/* 1340 */         entry.value = Object2ReferenceLinkedOpenHashMap.this.value[curr];
/* 1341 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2ReferenceSortedMap.FastSortedEntrySet<K, V> object2ReferenceEntrySet() {
/* 1347 */     if (this.entries == null)
/* 1348 */       this.entries = new MapEntrySet(); 
/* 1349 */     return this.entries;
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
/* 1362 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1366 */       return Object2ReferenceLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1373 */       return Object2ReferenceLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1379 */       return new Object2ReferenceLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1383 */       return new Object2ReferenceLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1388 */       if (Object2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1389 */         consumer.accept(Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.n]); 
/* 1390 */       for (int pos = Object2ReferenceLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1391 */         K k = Object2ReferenceLinkedOpenHashMap.this.key[pos];
/* 1392 */         if (k != null)
/* 1393 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1398 */       return Object2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1402 */       return Object2ReferenceLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1406 */       int oldSize = Object2ReferenceLinkedOpenHashMap.this.size;
/* 1407 */       Object2ReferenceLinkedOpenHashMap.this.remove(k);
/* 1408 */       return (Object2ReferenceLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1412 */       Object2ReferenceLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1416 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1417 */         throw new NoSuchElementException(); 
/* 1418 */       return Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1422 */       if (Object2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1423 */         throw new NoSuchElementException(); 
/* 1424 */       return Object2ReferenceLinkedOpenHashMap.this.key[Object2ReferenceLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1428 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1432 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1436 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1440 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1445 */     if (this.keys == null)
/* 1446 */       this.keys = new KeySet(); 
/* 1447 */     return this.keys;
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
/* 1461 */       return Object2ReferenceLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1468 */       return Object2ReferenceLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1473 */     if (this.values == null)
/* 1474 */       this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1477 */             return new Object2ReferenceLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1481 */             return Object2ReferenceLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1485 */             return Object2ReferenceLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1489 */             Object2ReferenceLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1494 */             if (Object2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1495 */               consumer.accept(Object2ReferenceLinkedOpenHashMap.this.value[Object2ReferenceLinkedOpenHashMap.this.n]); 
/* 1496 */             for (int pos = Object2ReferenceLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1497 */               if (Object2ReferenceLinkedOpenHashMap.this.key[pos] != null)
/* 1498 */                 consumer.accept(Object2ReferenceLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1501 */     return this.values;
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
/* 1518 */     return trim(this.size);
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
/* 1542 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1543 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1544 */       return true; 
/*      */     try {
/* 1546 */       rehash(l);
/* 1547 */     } catch (OutOfMemoryError cantDoIt) {
/* 1548 */       return false;
/*      */     } 
/* 1550 */     return true;
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
/* 1566 */     K[] key = this.key;
/* 1567 */     V[] value = this.value;
/* 1568 */     int mask = newN - 1;
/* 1569 */     K[] newKey = (K[])new Object[newN + 1];
/* 1570 */     V[] newValue = (V[])new Object[newN + 1];
/* 1571 */     int i = this.first, prev = -1, newPrev = -1;
/* 1572 */     long[] link = this.link;
/* 1573 */     long[] newLink = new long[newN + 1];
/* 1574 */     this.first = -1;
/* 1575 */     for (int j = this.size; j-- != 0; ) {
/* 1576 */       int pos; if (key[i] == null) {
/* 1577 */         pos = newN;
/*      */       } else {
/* 1579 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1580 */         while (newKey[pos] != null)
/* 1581 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1583 */       newKey[pos] = key[i];
/* 1584 */       newValue[pos] = value[i];
/* 1585 */       if (prev != -1) {
/* 1586 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1587 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1588 */         newPrev = pos;
/*      */       } else {
/* 1590 */         newPrev = this.first = pos;
/*      */         
/* 1592 */         newLink[pos] = -1L;
/*      */       } 
/* 1594 */       int t = i;
/* 1595 */       i = (int)link[i];
/* 1596 */       prev = t;
/*      */     } 
/* 1598 */     this.link = newLink;
/* 1599 */     this.last = newPrev;
/* 1600 */     if (newPrev != -1)
/*      */     {
/* 1602 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1603 */     this.n = newN;
/* 1604 */     this.mask = mask;
/* 1605 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1606 */     this.key = newKey;
/* 1607 */     this.value = newValue;
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
/*      */   public Object2ReferenceLinkedOpenHashMap<K, V> clone() {
/*      */     Object2ReferenceLinkedOpenHashMap<K, V> c;
/*      */     try {
/* 1624 */       c = (Object2ReferenceLinkedOpenHashMap<K, V>)super.clone();
/* 1625 */     } catch (CloneNotSupportedException cantHappen) {
/* 1626 */       throw new InternalError();
/*      */     } 
/* 1628 */     c.keys = null;
/* 1629 */     c.values = null;
/* 1630 */     c.entries = null;
/* 1631 */     c.containsNullKey = this.containsNullKey;
/* 1632 */     c.key = (K[])this.key.clone();
/* 1633 */     c.value = (V[])this.value.clone();
/* 1634 */     c.link = (long[])this.link.clone();
/* 1635 */     return c;
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
/* 1648 */     int h = 0;
/* 1649 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1650 */       while (this.key[i] == null)
/* 1651 */         i++; 
/* 1652 */       if (this != this.key[i])
/* 1653 */         t = this.key[i].hashCode(); 
/* 1654 */       if (this != this.value[i])
/* 1655 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1656 */       h += t;
/* 1657 */       i++;
/*      */     } 
/*      */     
/* 1660 */     if (this.containsNullKey)
/* 1661 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1662 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1665 */     K[] key = this.key;
/* 1666 */     V[] value = this.value;
/* 1667 */     MapIterator i = new MapIterator();
/* 1668 */     s.defaultWriteObject();
/* 1669 */     for (int j = this.size; j-- != 0; ) {
/* 1670 */       int e = i.nextEntry();
/* 1671 */       s.writeObject(key[e]);
/* 1672 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1677 */     s.defaultReadObject();
/* 1678 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1679 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1680 */     this.mask = this.n - 1;
/* 1681 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1682 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1683 */     long[] link = this.link = new long[this.n + 1];
/* 1684 */     int prev = -1;
/* 1685 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1688 */     for (int i = this.size; i-- != 0; ) {
/* 1689 */       int pos; K k = (K)s.readObject();
/* 1690 */       V v = (V)s.readObject();
/* 1691 */       if (k == null) {
/* 1692 */         pos = this.n;
/* 1693 */         this.containsNullKey = true;
/*      */       } else {
/* 1695 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1696 */         while (key[pos] != null)
/* 1697 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1699 */       key[pos] = k;
/* 1700 */       value[pos] = v;
/* 1701 */       if (this.first != -1) {
/* 1702 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1703 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1704 */         prev = pos; continue;
/*      */       } 
/* 1706 */       prev = this.first = pos;
/*      */       
/* 1708 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1711 */     this.last = prev;
/* 1712 */     if (prev != -1)
/*      */     {
/* 1714 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2ReferenceLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */