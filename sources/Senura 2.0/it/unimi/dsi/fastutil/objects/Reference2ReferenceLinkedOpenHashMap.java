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
/*      */ 
/*      */ public class Reference2ReferenceLinkedOpenHashMap<K, V>
/*      */   extends AbstractReference2ReferenceSortedMap<K, V>
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
/*      */   protected transient Reference2ReferenceSortedMap.FastSortedEntrySet<K, V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceLinkedOpenHashMap(int expected, float f) {
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
/*      */   public Reference2ReferenceLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceLinkedOpenHashMap() {
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
/*      */   public Reference2ReferenceLinkedOpenHashMap(Map<? extends K, ? extends V> m, float f) {
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
/*      */   public Reference2ReferenceLinkedOpenHashMap(Map<? extends K, ? extends V> m) {
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
/*      */   public Reference2ReferenceLinkedOpenHashMap(Reference2ReferenceMap<K, V> m, float f) {
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
/*      */   public Reference2ReferenceLinkedOpenHashMap(Reference2ReferenceMap<K, V> m) {
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
/*      */   public Reference2ReferenceLinkedOpenHashMap(K[] k, V[] v, float f) {
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
/*      */   public Reference2ReferenceLinkedOpenHashMap(K[] k, V[] v) {
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
/*  734 */     if (this.containsNullKey && value[this.n] == v)
/*  735 */       return true; 
/*  736 */     for (int i = this.n; i-- != 0;) {
/*  737 */       if (key[i] != null && value[i] == v)
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
/*      */     implements Reference2ReferenceMap.Entry<K, V>, Map.Entry<K, V>
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
/*  782 */       return Reference2ReferenceLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  786 */       return Reference2ReferenceLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  790 */       V oldValue = Reference2ReferenceLinkedOpenHashMap.this.value[this.index];
/*  791 */       Reference2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
/*  792 */       return oldValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  797 */       if (!(o instanceof Map.Entry))
/*  798 */         return false; 
/*  799 */       Map.Entry<K, V> e = (Map.Entry<K, V>)o;
/*  800 */       return (Reference2ReferenceLinkedOpenHashMap.this.key[this.index] == e.getKey() && Reference2ReferenceLinkedOpenHashMap.this.value[this.index] == e.getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  804 */       return System.identityHashCode(Reference2ReferenceLinkedOpenHashMap.this.key[this.index]) ^ (
/*  805 */         (Reference2ReferenceLinkedOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Reference2ReferenceLinkedOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  809 */       return (new StringBuilder()).append(Reference2ReferenceLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2ReferenceLinkedOpenHashMap.this.value[this.index]).toString();
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
/*  820 */     if (this.size == 0) {
/*  821 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/*  824 */     if (this.first == i) {
/*  825 */       this.first = (int)this.link[i];
/*  826 */       if (0 <= this.first)
/*      */       {
/*  828 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/*  832 */     if (this.last == i) {
/*  833 */       this.last = (int)(this.link[i] >>> 32L);
/*  834 */       if (0 <= this.last)
/*      */       {
/*  836 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/*  840 */     long linki = this.link[i];
/*  841 */     int prev = (int)(linki >>> 32L);
/*  842 */     int next = (int)linki;
/*  843 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  844 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/*  857 */     if (this.size == 1) {
/*  858 */       this.first = this.last = d;
/*      */       
/*  860 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/*  863 */     if (this.first == s) {
/*  864 */       this.first = d;
/*  865 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  866 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  869 */     if (this.last == s) {
/*  870 */       this.last = d;
/*  871 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  872 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/*  875 */     long links = this.link[s];
/*  876 */     int prev = (int)(links >>> 32L);
/*  877 */     int next = (int)links;
/*  878 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  879 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  880 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/*  889 */     if (this.size == 0)
/*  890 */       throw new NoSuchElementException(); 
/*  891 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/*  900 */     if (this.size == 0)
/*  901 */       throw new NoSuchElementException(); 
/*  902 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceSortedMap<K, V> tailMap(K from) {
/*  911 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceSortedMap<K, V> headMap(K to) {
/*  920 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2ReferenceSortedMap<K, V> subMap(K from, K to) {
/*  929 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/*  938 */     return null;
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
/*  953 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  959 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  964 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  970 */     int index = -1;
/*      */     protected MapIterator() {
/*  972 */       this.next = Reference2ReferenceLinkedOpenHashMap.this.first;
/*  973 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/*  976 */       if (from == null) {
/*  977 */         if (Reference2ReferenceLinkedOpenHashMap.this.containsNullKey) {
/*  978 */           this.next = (int)Reference2ReferenceLinkedOpenHashMap.this.link[Reference2ReferenceLinkedOpenHashMap.this.n];
/*  979 */           this.prev = Reference2ReferenceLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/*  982 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/*  984 */       if (Reference2ReferenceLinkedOpenHashMap.this.key[Reference2ReferenceLinkedOpenHashMap.this.last] == from) {
/*  985 */         this.prev = Reference2ReferenceLinkedOpenHashMap.this.last;
/*  986 */         this.index = Reference2ReferenceLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/*  990 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2ReferenceLinkedOpenHashMap.this.mask;
/*      */       
/*  992 */       while (Reference2ReferenceLinkedOpenHashMap.this.key[pos] != null) {
/*  993 */         if (Reference2ReferenceLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/*  995 */           this.next = (int)Reference2ReferenceLinkedOpenHashMap.this.link[pos];
/*  996 */           this.prev = pos;
/*      */           return;
/*      */         } 
/*  999 */         pos = pos + 1 & Reference2ReferenceLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1001 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1004 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1007 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1010 */       if (this.index >= 0)
/*      */         return; 
/* 1012 */       if (this.prev == -1) {
/* 1013 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1016 */       if (this.next == -1) {
/* 1017 */         this.index = Reference2ReferenceLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1020 */       int pos = Reference2ReferenceLinkedOpenHashMap.this.first;
/* 1021 */       this.index = 1;
/* 1022 */       while (pos != this.prev) {
/* 1023 */         pos = (int)Reference2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1024 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1028 */       ensureIndexKnown();
/* 1029 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1032 */       ensureIndexKnown();
/* 1033 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1036 */       if (!hasNext())
/* 1037 */         throw new NoSuchElementException(); 
/* 1038 */       this.curr = this.next;
/* 1039 */       this.next = (int)Reference2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1040 */       this.prev = this.curr;
/* 1041 */       if (this.index >= 0)
/* 1042 */         this.index++; 
/* 1043 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1046 */       if (!hasPrevious())
/* 1047 */         throw new NoSuchElementException(); 
/* 1048 */       this.curr = this.prev;
/* 1049 */       this.prev = (int)(Reference2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1050 */       this.next = this.curr;
/* 1051 */       if (this.index >= 0)
/* 1052 */         this.index--; 
/* 1053 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1056 */       ensureIndexKnown();
/* 1057 */       if (this.curr == -1)
/* 1058 */         throw new IllegalStateException(); 
/* 1059 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1064 */         this.index--;
/* 1065 */         this.prev = (int)(Reference2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1067 */         this.next = (int)Reference2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1068 */       }  Reference2ReferenceLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1073 */       if (this.prev == -1) {
/* 1074 */         Reference2ReferenceLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1076 */         Reference2ReferenceLinkedOpenHashMap.this.link[this.prev] = Reference2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ (Reference2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1077 */       }  if (this.next == -1) {
/* 1078 */         Reference2ReferenceLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1080 */         Reference2ReferenceLinkedOpenHashMap.this.link[this.next] = Reference2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (Reference2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1081 */       }  int pos = this.curr;
/* 1082 */       this.curr = -1;
/* 1083 */       if (pos == Reference2ReferenceLinkedOpenHashMap.this.n) {
/* 1084 */         Reference2ReferenceLinkedOpenHashMap.this.containsNullKey = false;
/* 1085 */         Reference2ReferenceLinkedOpenHashMap.this.key[Reference2ReferenceLinkedOpenHashMap.this.n] = null;
/* 1086 */         Reference2ReferenceLinkedOpenHashMap.this.value[Reference2ReferenceLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1089 */         K[] key = Reference2ReferenceLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1093 */           pos = (last = pos) + 1 & Reference2ReferenceLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1095 */             if ((curr = key[pos]) == null) {
/* 1096 */               key[last] = null;
/* 1097 */               Reference2ReferenceLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1100 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2ReferenceLinkedOpenHashMap.this.mask;
/* 1101 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1103 */             pos = pos + 1 & Reference2ReferenceLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1105 */           key[last] = curr;
/* 1106 */           Reference2ReferenceLinkedOpenHashMap.this.value[last] = Reference2ReferenceLinkedOpenHashMap.this.value[pos];
/* 1107 */           if (this.next == pos)
/* 1108 */             this.next = last; 
/* 1109 */           if (this.prev == pos)
/* 1110 */             this.prev = last; 
/* 1111 */           Reference2ReferenceLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1116 */       int i = n;
/* 1117 */       while (i-- != 0 && hasNext())
/* 1118 */         nextEntry(); 
/* 1119 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1122 */       int i = n;
/* 1123 */       while (i-- != 0 && hasPrevious())
/* 1124 */         previousEntry(); 
/* 1125 */       return n - i - 1;
/*      */     }
/*      */     public void set(Reference2ReferenceMap.Entry<K, V> ok) {
/* 1128 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Reference2ReferenceMap.Entry<K, V> ok) {
/* 1131 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Reference2ReferenceMap.Entry<K, V>> { private Reference2ReferenceLinkedOpenHashMap<K, V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1139 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2ReferenceLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1143 */       return this.entry = new Reference2ReferenceLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Reference2ReferenceLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1147 */       return this.entry = new Reference2ReferenceLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1151 */       super.remove();
/* 1152 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/*      */   private class FastEntryIterator
/*      */     extends MapIterator implements ObjectListIterator<Reference2ReferenceMap.Entry<K, V>> {
/* 1158 */     final Reference2ReferenceLinkedOpenHashMap<K, V>.MapEntry entry = new Reference2ReferenceLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1162 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2ReferenceLinkedOpenHashMap<K, V>.MapEntry next() {
/* 1166 */       this.entry.index = nextEntry();
/* 1167 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Reference2ReferenceLinkedOpenHashMap<K, V>.MapEntry previous() {
/* 1171 */       this.entry.index = previousEntry();
/* 1172 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> implements Reference2ReferenceSortedMap.FastSortedEntrySet<K, V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2ReferenceMap.Entry<K, V>> iterator() {
/* 1180 */       return new Reference2ReferenceLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Reference2ReferenceMap.Entry<K, V>> comparator() {
/* 1184 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> subSet(Reference2ReferenceMap.Entry<K, V> fromElement, Reference2ReferenceMap.Entry<K, V> toElement) {
/* 1189 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> headSet(Reference2ReferenceMap.Entry<K, V> toElement) {
/* 1194 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2ReferenceMap.Entry<K, V>> tailSet(Reference2ReferenceMap.Entry<K, V> fromElement) {
/* 1199 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Reference2ReferenceMap.Entry<K, V> first() {
/* 1203 */       if (Reference2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1204 */         throw new NoSuchElementException(); 
/* 1205 */       return new Reference2ReferenceLinkedOpenHashMap.MapEntry(Reference2ReferenceLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Reference2ReferenceMap.Entry<K, V> last() {
/* 1209 */       if (Reference2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1210 */         throw new NoSuchElementException(); 
/* 1211 */       return new Reference2ReferenceLinkedOpenHashMap.MapEntry(Reference2ReferenceLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1216 */       if (!(o instanceof Map.Entry))
/* 1217 */         return false; 
/* 1218 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1219 */       K k = (K)e.getKey();
/* 1220 */       V v = (V)e.getValue();
/* 1221 */       if (k == null) {
/* 1222 */         return (Reference2ReferenceLinkedOpenHashMap.this.containsNullKey && Reference2ReferenceLinkedOpenHashMap.this.value[Reference2ReferenceLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1224 */       K[] key = Reference2ReferenceLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1227 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1229 */         return false; } 
/* 1230 */       if (k == curr) {
/* 1231 */         return (Reference2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1234 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceLinkedOpenHashMap.this.mask]) == null)
/* 1235 */           return false; 
/* 1236 */         if (k == curr) {
/* 1237 */           return (Reference2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1243 */       if (!(o instanceof Map.Entry))
/* 1244 */         return false; 
/* 1245 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1246 */       K k = (K)e.getKey();
/* 1247 */       V v = (V)e.getValue();
/* 1248 */       if (k == null) {
/* 1249 */         if (Reference2ReferenceLinkedOpenHashMap.this.containsNullKey && Reference2ReferenceLinkedOpenHashMap.this.value[Reference2ReferenceLinkedOpenHashMap.this.n] == v) {
/* 1250 */           Reference2ReferenceLinkedOpenHashMap.this.removeNullEntry();
/* 1251 */           return true;
/*      */         } 
/* 1253 */         return false;
/*      */       } 
/*      */       
/* 1256 */       K[] key = Reference2ReferenceLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1259 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2ReferenceLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1261 */         return false; } 
/* 1262 */       if (curr == k) {
/* 1263 */         if (Reference2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1264 */           Reference2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1265 */           return true;
/*      */         } 
/* 1267 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1270 */         if ((curr = key[pos = pos + 1 & Reference2ReferenceLinkedOpenHashMap.this.mask]) == null)
/* 1271 */           return false; 
/* 1272 */         if (curr == k && 
/* 1273 */           Reference2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1274 */           Reference2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1275 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1282 */       return Reference2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1286 */       Reference2ReferenceLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2ReferenceMap.Entry<K, V>> iterator(Reference2ReferenceMap.Entry<K, V> from) {
/* 1302 */       return new Reference2ReferenceLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator() {
/* 1313 */       return new Reference2ReferenceLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2ReferenceMap.Entry<K, V>> fastIterator(Reference2ReferenceMap.Entry<K, V> from) {
/* 1329 */       return new Reference2ReferenceLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/* 1334 */       for (int i = Reference2ReferenceLinkedOpenHashMap.this.size, next = Reference2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1335 */         int curr = next;
/* 1336 */         next = (int)Reference2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1337 */         consumer.accept(new AbstractReference2ReferenceMap.BasicEntry<>(Reference2ReferenceLinkedOpenHashMap.this.key[curr], Reference2ReferenceLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2ReferenceMap.Entry<K, V>> consumer) {
/* 1343 */       AbstractReference2ReferenceMap.BasicEntry<K, V> entry = new AbstractReference2ReferenceMap.BasicEntry<>();
/* 1344 */       for (int i = Reference2ReferenceLinkedOpenHashMap.this.size, next = Reference2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1345 */         int curr = next;
/* 1346 */         next = (int)Reference2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1347 */         entry.key = Reference2ReferenceLinkedOpenHashMap.this.key[curr];
/* 1348 */         entry.value = Reference2ReferenceLinkedOpenHashMap.this.value[curr];
/* 1349 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Reference2ReferenceSortedMap.FastSortedEntrySet<K, V> reference2ReferenceEntrySet() {
/* 1355 */     if (this.entries == null)
/* 1356 */       this.entries = new MapEntrySet(); 
/* 1357 */     return this.entries;
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
/* 1370 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1374 */       return Reference2ReferenceLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1381 */       return Reference2ReferenceLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1387 */       return new Reference2ReferenceLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1391 */       return new Reference2ReferenceLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1396 */       if (Reference2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1397 */         consumer.accept(Reference2ReferenceLinkedOpenHashMap.this.key[Reference2ReferenceLinkedOpenHashMap.this.n]); 
/* 1398 */       for (int pos = Reference2ReferenceLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1399 */         K k = Reference2ReferenceLinkedOpenHashMap.this.key[pos];
/* 1400 */         if (k != null)
/* 1401 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1406 */       return Reference2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1410 */       return Reference2ReferenceLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1414 */       int oldSize = Reference2ReferenceLinkedOpenHashMap.this.size;
/* 1415 */       Reference2ReferenceLinkedOpenHashMap.this.remove(k);
/* 1416 */       return (Reference2ReferenceLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1420 */       Reference2ReferenceLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1424 */       if (Reference2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1425 */         throw new NoSuchElementException(); 
/* 1426 */       return Reference2ReferenceLinkedOpenHashMap.this.key[Reference2ReferenceLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1430 */       if (Reference2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1431 */         throw new NoSuchElementException(); 
/* 1432 */       return Reference2ReferenceLinkedOpenHashMap.this.key[Reference2ReferenceLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1436 */       return null;
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1440 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1448 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1453 */     if (this.keys == null)
/* 1454 */       this.keys = new KeySet(); 
/* 1455 */     return this.keys;
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
/* 1469 */       return Reference2ReferenceLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1476 */       return Reference2ReferenceLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1481 */     if (this.values == null)
/* 1482 */       this.values = new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1485 */             return new Reference2ReferenceLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1489 */             return Reference2ReferenceLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1493 */             return Reference2ReferenceLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1497 */             Reference2ReferenceLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1502 */             if (Reference2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1503 */               consumer.accept(Reference2ReferenceLinkedOpenHashMap.this.value[Reference2ReferenceLinkedOpenHashMap.this.n]); 
/* 1504 */             for (int pos = Reference2ReferenceLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1505 */               if (Reference2ReferenceLinkedOpenHashMap.this.key[pos] != null)
/* 1506 */                 consumer.accept(Reference2ReferenceLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1509 */     return this.values;
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
/* 1526 */     return trim(this.size);
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
/* 1550 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1551 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1552 */       return true; 
/*      */     try {
/* 1554 */       rehash(l);
/* 1555 */     } catch (OutOfMemoryError cantDoIt) {
/* 1556 */       return false;
/*      */     } 
/* 1558 */     return true;
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
/* 1574 */     K[] key = this.key;
/* 1575 */     V[] value = this.value;
/* 1576 */     int mask = newN - 1;
/* 1577 */     K[] newKey = (K[])new Object[newN + 1];
/* 1578 */     V[] newValue = (V[])new Object[newN + 1];
/* 1579 */     int i = this.first, prev = -1, newPrev = -1;
/* 1580 */     long[] link = this.link;
/* 1581 */     long[] newLink = new long[newN + 1];
/* 1582 */     this.first = -1;
/* 1583 */     for (int j = this.size; j-- != 0; ) {
/* 1584 */       int pos; if (key[i] == null) {
/* 1585 */         pos = newN;
/*      */       } else {
/* 1587 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1588 */         while (newKey[pos] != null)
/* 1589 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1591 */       newKey[pos] = key[i];
/* 1592 */       newValue[pos] = value[i];
/* 1593 */       if (prev != -1) {
/* 1594 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1595 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1596 */         newPrev = pos;
/*      */       } else {
/* 1598 */         newPrev = this.first = pos;
/*      */         
/* 1600 */         newLink[pos] = -1L;
/*      */       } 
/* 1602 */       int t = i;
/* 1603 */       i = (int)link[i];
/* 1604 */       prev = t;
/*      */     } 
/* 1606 */     this.link = newLink;
/* 1607 */     this.last = newPrev;
/* 1608 */     if (newPrev != -1)
/*      */     {
/* 1610 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1611 */     this.n = newN;
/* 1612 */     this.mask = mask;
/* 1613 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1614 */     this.key = newKey;
/* 1615 */     this.value = newValue;
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
/*      */   public Reference2ReferenceLinkedOpenHashMap<K, V> clone() {
/*      */     Reference2ReferenceLinkedOpenHashMap<K, V> c;
/*      */     try {
/* 1632 */       c = (Reference2ReferenceLinkedOpenHashMap<K, V>)super.clone();
/* 1633 */     } catch (CloneNotSupportedException cantHappen) {
/* 1634 */       throw new InternalError();
/*      */     } 
/* 1636 */     c.keys = null;
/* 1637 */     c.values = null;
/* 1638 */     c.entries = null;
/* 1639 */     c.containsNullKey = this.containsNullKey;
/* 1640 */     c.key = (K[])this.key.clone();
/* 1641 */     c.value = (V[])this.value.clone();
/* 1642 */     c.link = (long[])this.link.clone();
/* 1643 */     return c;
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
/* 1656 */     int h = 0;
/* 1657 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1658 */       while (this.key[i] == null)
/* 1659 */         i++; 
/* 1660 */       if (this != this.key[i])
/* 1661 */         t = System.identityHashCode(this.key[i]); 
/* 1662 */       if (this != this.value[i])
/* 1663 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1664 */       h += t;
/* 1665 */       i++;
/*      */     } 
/*      */     
/* 1668 */     if (this.containsNullKey)
/* 1669 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1670 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1673 */     K[] key = this.key;
/* 1674 */     V[] value = this.value;
/* 1675 */     MapIterator i = new MapIterator();
/* 1676 */     s.defaultWriteObject();
/* 1677 */     for (int j = this.size; j-- != 0; ) {
/* 1678 */       int e = i.nextEntry();
/* 1679 */       s.writeObject(key[e]);
/* 1680 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1685 */     s.defaultReadObject();
/* 1686 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1687 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1688 */     this.mask = this.n - 1;
/* 1689 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1690 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1691 */     long[] link = this.link = new long[this.n + 1];
/* 1692 */     int prev = -1;
/* 1693 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1696 */     for (int i = this.size; i-- != 0; ) {
/* 1697 */       int pos; K k = (K)s.readObject();
/* 1698 */       V v = (V)s.readObject();
/* 1699 */       if (k == null) {
/* 1700 */         pos = this.n;
/* 1701 */         this.containsNullKey = true;
/*      */       } else {
/* 1703 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1704 */         while (key[pos] != null)
/* 1705 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1707 */       key[pos] = k;
/* 1708 */       value[pos] = v;
/* 1709 */       if (this.first != -1) {
/* 1710 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1711 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1712 */         prev = pos; continue;
/*      */       } 
/* 1714 */       prev = this.first = pos;
/*      */       
/* 1716 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1719 */     this.last = prev;
/* 1720 */     if (prev != -1)
/*      */     {
/* 1722 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2ReferenceLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */