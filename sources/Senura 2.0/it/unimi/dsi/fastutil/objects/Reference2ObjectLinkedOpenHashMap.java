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
/*      */ public class Reference2ObjectLinkedOpenHashMap<K, V>
/*      */   extends AbstractReference2ObjectSortedMap<K, V>
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
/*      */   protected transient Reference2ObjectSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectLinkedOpenHashMap(int expected, float f) {
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
/*      */   public Reference2ObjectLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectLinkedOpenHashMap() {
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
/*      */   public Reference2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
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
/*      */   public Reference2ObjectLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
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
/*      */   public Reference2ObjectLinkedOpenHashMap(Reference2ObjectMap<K, V> m, float f) {
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
/*      */   public Reference2ObjectLinkedOpenHashMap(Reference2ObjectMap<K, V> m) {
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
/*      */   public Reference2ObjectLinkedOpenHashMap(K[] k, V[] v, float f) {
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
/*      */   public Reference2ObjectLinkedOpenHashMap(K[] k, V[] v) {
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
/*  373 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
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
/*  395 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  396 */       return this.defRetValue; 
/*  397 */     if (k == curr)
/*  398 */       return removeEntry(pos); 
/*      */     while (true) {
/*  400 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  401 */         return this.defRetValue; 
/*  402 */       if (k == curr)
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
/*  527 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  528 */       return this.defRetValue; 
/*  529 */     if (k == curr) {
/*  530 */       moveIndexToFirst(pos);
/*  531 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  535 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  536 */         return this.defRetValue; 
/*  537 */       if (k == curr) {
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
/*  564 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  565 */       return this.defRetValue; 
/*  566 */     if (k == curr) {
/*  567 */       moveIndexToLast(pos);
/*  568 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  572 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  573 */         return this.defRetValue; 
/*  574 */       if (k == curr) {
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
/*  604 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  606 */         if (curr == k) {
/*  607 */           moveIndexToFirst(pos);
/*  608 */           return setValue(pos, v);
/*      */         } 
/*  610 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  611 */           if (curr == k) {
/*  612 */             moveIndexToFirst(pos);
/*  613 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  617 */     }  this.key[pos] = k;
/*  618 */     this.value[pos] = v;
/*  619 */     if (this.size == 0) {
/*  620 */       this.first = this.last = pos;
/*      */       
/*  622 */       this.link[pos] = -1L;
/*      */     } else {
/*  624 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  625 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  626 */       this.first = pos;
/*      */     } 
/*  628 */     if (this.size++ >= this.maxFill) {
/*  629 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  632 */     return this.defRetValue;
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
/*  647 */     if (k == null) {
/*  648 */       if (this.containsNullKey) {
/*  649 */         moveIndexToLast(this.n);
/*  650 */         return setValue(this.n, v);
/*      */       } 
/*  652 */       this.containsNullKey = true;
/*  653 */       pos = this.n;
/*      */     } else {
/*      */       
/*  656 */       K[] key = this.key;
/*      */       K curr;
/*  658 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  660 */         if (curr == k) {
/*  661 */           moveIndexToLast(pos);
/*  662 */           return setValue(pos, v);
/*      */         } 
/*  664 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  665 */           if (curr == k) {
/*  666 */             moveIndexToLast(pos);
/*  667 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  671 */     }  this.key[pos] = k;
/*  672 */     this.value[pos] = v;
/*  673 */     if (this.size == 0) {
/*  674 */       this.first = this.last = pos;
/*      */       
/*  676 */       this.link[pos] = -1L;
/*      */     } else {
/*  678 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  679 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  680 */       this.last = pos;
/*      */     } 
/*  682 */     if (this.size++ >= this.maxFill) {
/*  683 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  686 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(Object k) {
/*  691 */     if (k == null) {
/*  692 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  694 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  697 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  698 */       return this.defRetValue; 
/*  699 */     if (k == curr) {
/*  700 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  703 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  704 */         return this.defRetValue; 
/*  705 */       if (k == curr) {
/*  706 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  712 */     if (k == null) {
/*  713 */       return this.containsNullKey;
/*      */     }
/*  715 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  718 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  719 */       return false; 
/*  720 */     if (k == curr) {
/*  721 */       return true;
/*      */     }
/*      */     while (true) {
/*  724 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  725 */         return false; 
/*  726 */       if (k == curr)
/*  727 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  732 */     V[] value = this.value;
/*  733 */     K[] key = this.key;
/*  734 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  735 */       return true; 
/*  736 */     for (int i = this.n; i-- != 0;) {
/*  737 */       if (key[i] != null && Objects.equals(value[i], v))
/*  738 */         return true; 
/*  739 */     }  return false;
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
/*  750 */     if (this.size == 0)
/*      */       return; 
/*  752 */     this.size = 0;
/*  753 */     this.containsNullKey = false;
/*  754 */     Arrays.fill((Object[])this.key, (Object)null);
/*  755 */     Arrays.fill((Object[])this.value, (Object)null);
/*  756 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  760 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  764 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2ObjectMap.Entry<K, V>, Map.Entry<K, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  776 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  782 */       return Reference2ObjectLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  786 */       return Reference2ObjectLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  790 */       V oldValue = Reference2ObjectLinkedOpenHashMap.this.value[this.index];
/*  791 */       Reference2ObjectLinkedOpenHashMap.this.value[this.index] = v;
/*  792 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  797 */       if (!(o instanceof Map.Entry))
/*  798 */         return false; 
/*  799 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  800 */       return (Reference2ObjectLinkedOpenHashMap.this.key[this.index] == e.getKey() && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  804 */       return System.identityHashCode(Reference2ObjectLinkedOpenHashMap.this.key[this.index]) ^ ((Reference2ObjectLinkedOpenHashMap.this.value[this.index] == null) ? 0 : Reference2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  808 */       return (new StringBuilder()).append(Reference2ObjectLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2ObjectLinkedOpenHashMap.this.value[this.index]).toString();
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
/*      */   public Reference2ObjectSortedMap<K, V> tailMap(K from) {
/*  910 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectSortedMap<K, V> headMap(K to) {
/*  919 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ObjectSortedMap<K, V> subMap(K from, K to) {
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
/*  971 */       this.next = Reference2ObjectLinkedOpenHashMap.this.first;
/*  972 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/*  975 */       if (from == null) {
/*  976 */         if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey) {
/*  977 */           this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[Reference2ObjectLinkedOpenHashMap.this.n];
/*  978 */           this.prev = Reference2ObjectLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/*  981 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/*  983 */       if (Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.last] == from) {
/*  984 */         this.prev = Reference2ObjectLinkedOpenHashMap.this.last;
/*  985 */         this.index = Reference2ObjectLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  989 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2ObjectLinkedOpenHashMap.this.mask;
/*      */       
/*  991 */       while (Reference2ObjectLinkedOpenHashMap.this.key[pos] != null) {
/*  992 */         if (Reference2ObjectLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/*  994 */           this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[pos];
/*  995 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  998 */         pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask;
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
/* 1016 */         this.index = Reference2ObjectLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1019 */       int pos = Reference2ObjectLinkedOpenHashMap.this.first;
/* 1020 */       this.index = 1;
/* 1021 */       while (pos != this.prev) {
/* 1022 */         pos = (int)Reference2ObjectLinkedOpenHashMap.this.link[pos];
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
/* 1038 */       this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1039 */       this.prev = this.curr;
/* 1040 */       if (this.index >= 0)
/* 1041 */         this.index++; 
/* 1042 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1045 */       if (!hasPrevious())
/* 1046 */         throw new NoSuchElementException(); 
/* 1047 */       this.curr = this.prev;
/* 1048 */       this.prev = (int)(Reference2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1064 */         this.prev = (int)(Reference2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1066 */         this.next = (int)Reference2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1067 */       }  Reference2ObjectLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1072 */       if (this.prev == -1) {
/* 1073 */         Reference2ObjectLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1075 */         Reference2ObjectLinkedOpenHashMap.this.link[this.prev] = Reference2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (Reference2ObjectLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1076 */       }  if (this.next == -1) {
/* 1077 */         Reference2ObjectLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1079 */         Reference2ObjectLinkedOpenHashMap.this.link[this.next] = Reference2ObjectLinkedOpenHashMap.this.link[this.next] ^ (Reference2ObjectLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1080 */       }  int pos = this.curr;
/* 1081 */       this.curr = -1;
/* 1082 */       if (pos == Reference2ObjectLinkedOpenHashMap.this.n) {
/* 1083 */         Reference2ObjectLinkedOpenHashMap.this.containsNullKey = false;
/* 1084 */         Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.n] = null;
/* 1085 */         Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1088 */         K[] key = Reference2ObjectLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1092 */           pos = (last = pos) + 1 & Reference2ObjectLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1094 */             if ((curr = key[pos]) == null) {
/* 1095 */               key[last] = null;
/* 1096 */               Reference2ObjectLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1099 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ObjectLinkedOpenHashMap.this.mask;
/* 1100 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1102 */             pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1104 */           key[last] = curr;
/* 1105 */           Reference2ObjectLinkedOpenHashMap.this.value[last] = Reference2ObjectLinkedOpenHashMap.this.value[pos];
/* 1106 */           if (this.next == pos)
/* 1107 */             this.next = last; 
/* 1108 */           if (this.prev == pos)
/* 1109 */             this.prev = last; 
/* 1110 */           Reference2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Reference2ObjectMap.Entry<K, V> ok) {
/* 1127 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Reference2ObjectMap.Entry<K, V> ok) {
/* 1130 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Reference2ObjectMap.Entry<K, V>> { private Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1138 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1142 */       return this.entry = new Reference2ObjectLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1146 */       return this.entry = new Reference2ObjectLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1150 */       super.remove();
/* 1151 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1155 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Reference2ObjectMap.Entry<K, V>> { final Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry entry = new Reference2ObjectLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1159 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1163 */       this.entry.index = nextEntry();
/* 1164 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Reference2ObjectLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1168 */       this.entry.index = previousEntry();
/* 1169 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2ObjectMap.Entry<K, V>> implements Reference2ObjectSortedMap.FastSortedEntrySet<K, V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2ObjectMap.Entry<K, V>> iterator() {
/* 1177 */       return new Reference2ObjectLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Reference2ObjectMap.Entry<K, V>> comparator() {
/* 1181 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> subSet(Reference2ObjectMap.Entry<K, V> fromElement, Reference2ObjectMap.Entry<K, V> toElement) {
/* 1186 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> headSet(Reference2ObjectMap.Entry<K, V> toElement) {
/* 1190 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2ObjectMap.Entry<K, V>> tailSet(Reference2ObjectMap.Entry<K, V> fromElement) {
/* 1194 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Reference2ObjectMap.Entry<K, V> first() {
/* 1198 */       if (Reference2ObjectLinkedOpenHashMap.this.size == 0)
/* 1199 */         throw new NoSuchElementException(); 
/* 1200 */       return new Reference2ObjectLinkedOpenHashMap.MapEntry(Reference2ObjectLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Reference2ObjectMap.Entry<K, V> last() {
/* 1204 */       if (Reference2ObjectLinkedOpenHashMap.this.size == 0)
/* 1205 */         throw new NoSuchElementException(); 
/* 1206 */       return new Reference2ObjectLinkedOpenHashMap.MapEntry(Reference2ObjectLinkedOpenHashMap.this.last);
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
/* 1217 */         return (Reference2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n], v));
/*      */       }
/* 1219 */       K[] key = Reference2ObjectLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1222 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1224 */         return false; } 
/* 1225 */       if (k == curr) {
/* 1226 */         return Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/* 1229 */         if ((curr = key[pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask]) == null)
/* 1230 */           return false; 
/* 1231 */         if (k == curr) {
/* 1232 */           return Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1238 */       if (!(o instanceof Map.Entry))
/* 1239 */         return false; 
/* 1240 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1241 */       K k = (K)e.getKey();
/* 1242 */       V v = (V)e.getValue();
/* 1243 */       if (k == null) {
/* 1244 */         if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n], v)) {
/* 1245 */           Reference2ObjectLinkedOpenHashMap.this.removeNullEntry();
/* 1246 */           return true;
/*      */         } 
/* 1248 */         return false;
/*      */       } 
/*      */       
/* 1251 */       K[] key = Reference2ObjectLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1254 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ObjectLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1256 */         return false; } 
/* 1257 */       if (curr == k) {
/* 1258 */         if (Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1259 */           Reference2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1260 */           return true;
/*      */         } 
/* 1262 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1265 */         if ((curr = key[pos = pos + 1 & Reference2ObjectLinkedOpenHashMap.this.mask]) == null)
/* 1266 */           return false; 
/* 1267 */         if (curr == k && 
/* 1268 */           Objects.equals(Reference2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1269 */           Reference2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1270 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1277 */       return Reference2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1281 */       Reference2ObjectLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2ObjectMap.Entry<K, V>> iterator(Reference2ObjectMap.Entry<K, V> from) {
/* 1297 */       return new Reference2ObjectLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2ObjectMap.Entry<K, V>> fastIterator() {
/* 1308 */       return new Reference2ObjectLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2ObjectMap.Entry<K, V>> fastIterator(Reference2ObjectMap.Entry<K, V> from) {
/* 1324 */       return new Reference2ObjectLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/* 1329 */       for (int i = Reference2ObjectLinkedOpenHashMap.this.size, next = Reference2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1330 */         int curr = next;
/* 1331 */         next = (int)Reference2ObjectLinkedOpenHashMap.this.link[curr];
/* 1332 */         consumer.accept(new AbstractReference2ObjectMap.BasicEntry<>(Reference2ObjectLinkedOpenHashMap.this.key[curr], Reference2ObjectLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2ObjectMap.Entry<K, V>> consumer) {
/* 1338 */       AbstractReference2ObjectMap.BasicEntry<K, V> entry = new AbstractReference2ObjectMap.BasicEntry<>();
/* 1339 */       for (int i = Reference2ObjectLinkedOpenHashMap.this.size, next = Reference2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1340 */         int curr = next;
/* 1341 */         next = (int)Reference2ObjectLinkedOpenHashMap.this.link[curr];
/* 1342 */         entry.key = Reference2ObjectLinkedOpenHashMap.this.key[curr];
/* 1343 */         entry.value = Reference2ObjectLinkedOpenHashMap.this.value[curr];
/* 1344 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Reference2ObjectSortedMap.FastSortedEntrySet<K, V> reference2ObjectEntrySet() {
/* 1350 */     if (this.entries == null)
/* 1351 */       this.entries = new MapEntrySet(); 
/* 1352 */     return this.entries;
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
/* 1365 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1369 */       return Reference2ObjectLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1376 */       return Reference2ObjectLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1382 */       return new Reference2ObjectLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1386 */       return new Reference2ObjectLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1391 */       if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1392 */         consumer.accept(Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.n]); 
/* 1393 */       for (int pos = Reference2ObjectLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1394 */         K k = Reference2ObjectLinkedOpenHashMap.this.key[pos];
/* 1395 */         if (k != null)
/* 1396 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1401 */       return Reference2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1405 */       return Reference2ObjectLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1409 */       int oldSize = Reference2ObjectLinkedOpenHashMap.this.size;
/* 1410 */       Reference2ObjectLinkedOpenHashMap.this.remove(k);
/* 1411 */       return (Reference2ObjectLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1415 */       Reference2ObjectLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1419 */       if (Reference2ObjectLinkedOpenHashMap.this.size == 0)
/* 1420 */         throw new NoSuchElementException(); 
/* 1421 */       return Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1425 */       if (Reference2ObjectLinkedOpenHashMap.this.size == 0)
/* 1426 */         throw new NoSuchElementException(); 
/* 1427 */       return Reference2ObjectLinkedOpenHashMap.this.key[Reference2ObjectLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1431 */       return null;
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1435 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1439 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1443 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1448 */     if (this.keys == null)
/* 1449 */       this.keys = new KeySet(); 
/* 1450 */     return this.keys;
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
/* 1464 */       return Reference2ObjectLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1471 */       return Reference2ObjectLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1476 */     if (this.values == null)
/* 1477 */       this.values = new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1480 */             return new Reference2ObjectLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1484 */             return Reference2ObjectLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1488 */             return Reference2ObjectLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1492 */             Reference2ObjectLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1497 */             if (Reference2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1498 */               consumer.accept(Reference2ObjectLinkedOpenHashMap.this.value[Reference2ObjectLinkedOpenHashMap.this.n]); 
/* 1499 */             for (int pos = Reference2ObjectLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1500 */               if (Reference2ObjectLinkedOpenHashMap.this.key[pos] != null)
/* 1501 */                 consumer.accept(Reference2ObjectLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1504 */     return this.values;
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
/* 1521 */     return trim(this.size);
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
/* 1545 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1546 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1547 */       return true; 
/*      */     try {
/* 1549 */       rehash(l);
/* 1550 */     } catch (OutOfMemoryError cantDoIt) {
/* 1551 */       return false;
/*      */     } 
/* 1553 */     return true;
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
/* 1569 */     K[] key = this.key;
/* 1570 */     V[] value = this.value;
/* 1571 */     int mask = newN - 1;
/* 1572 */     K[] newKey = (K[])new Object[newN + 1];
/* 1573 */     V[] newValue = (V[])new Object[newN + 1];
/* 1574 */     int i = this.first, prev = -1, newPrev = -1;
/* 1575 */     long[] link = this.link;
/* 1576 */     long[] newLink = new long[newN + 1];
/* 1577 */     this.first = -1;
/* 1578 */     for (int j = this.size; j-- != 0; ) {
/* 1579 */       int pos; if (key[i] == null) {
/* 1580 */         pos = newN;
/*      */       } else {
/* 1582 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1583 */         while (newKey[pos] != null)
/* 1584 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1586 */       newKey[pos] = key[i];
/* 1587 */       newValue[pos] = value[i];
/* 1588 */       if (prev != -1) {
/* 1589 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1590 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1591 */         newPrev = pos;
/*      */       } else {
/* 1593 */         newPrev = this.first = pos;
/*      */         
/* 1595 */         newLink[pos] = -1L;
/*      */       } 
/* 1597 */       int t = i;
/* 1598 */       i = (int)link[i];
/* 1599 */       prev = t;
/*      */     } 
/* 1601 */     this.link = newLink;
/* 1602 */     this.last = newPrev;
/* 1603 */     if (newPrev != -1)
/*      */     {
/* 1605 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1606 */     this.n = newN;
/* 1607 */     this.mask = mask;
/* 1608 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1609 */     this.key = newKey;
/* 1610 */     this.value = newValue;
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
/*      */   public Reference2ObjectLinkedOpenHashMap<K, V> clone() {
/*      */     Reference2ObjectLinkedOpenHashMap<K, V> c;
/*      */     try {
/* 1627 */       c = (Reference2ObjectLinkedOpenHashMap<K, V>)super.clone();
/* 1628 */     } catch (CloneNotSupportedException cantHappen) {
/* 1629 */       throw new InternalError();
/*      */     } 
/* 1631 */     c.keys = null;
/* 1632 */     c.values = null;
/* 1633 */     c.entries = null;
/* 1634 */     c.containsNullKey = this.containsNullKey;
/* 1635 */     c.key = (K[])this.key.clone();
/* 1636 */     c.value = (V[])this.value.clone();
/* 1637 */     c.link = (long[])this.link.clone();
/* 1638 */     return c;
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
/* 1651 */     int h = 0;
/* 1652 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1653 */       while (this.key[i] == null)
/* 1654 */         i++; 
/* 1655 */       if (this != this.key[i])
/* 1656 */         t = System.identityHashCode(this.key[i]); 
/* 1657 */       if (this != this.value[i])
/* 1658 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1659 */       h += t;
/* 1660 */       i++;
/*      */     } 
/*      */     
/* 1663 */     if (this.containsNullKey)
/* 1664 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1665 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1668 */     K[] key = this.key;
/* 1669 */     V[] value = this.value;
/* 1670 */     MapIterator i = new MapIterator();
/* 1671 */     s.defaultWriteObject();
/* 1672 */     for (int j = this.size; j-- != 0; ) {
/* 1673 */       int e = i.nextEntry();
/* 1674 */       s.writeObject(key[e]);
/* 1675 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1680 */     s.defaultReadObject();
/* 1681 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1682 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1683 */     this.mask = this.n - 1;
/* 1684 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1685 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1686 */     long[] link = this.link = new long[this.n + 1];
/* 1687 */     int prev = -1;
/* 1688 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1691 */     for (int i = this.size; i-- != 0; ) {
/* 1692 */       int pos; K k = (K)s.readObject();
/* 1693 */       V v = (V)s.readObject();
/* 1694 */       if (k == null) {
/* 1695 */         pos = this.n;
/* 1696 */         this.containsNullKey = true;
/*      */       } else {
/* 1698 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1699 */         while (key[pos] != null)
/* 1700 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1702 */       key[pos] = k;
/* 1703 */       value[pos] = v;
/* 1704 */       if (this.first != -1) {
/* 1705 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1706 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1707 */         prev = pos; continue;
/*      */       } 
/* 1709 */       prev = this.first = pos;
/*      */       
/* 1711 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1714 */     this.last = prev;
/* 1715 */     if (prev != -1)
/*      */     {
/* 1717 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ObjectLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */