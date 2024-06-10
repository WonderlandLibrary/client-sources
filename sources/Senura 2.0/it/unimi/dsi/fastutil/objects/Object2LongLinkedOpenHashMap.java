/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongListIterator;
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
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.LongConsumer;
/*      */ import java.util.function.ToLongFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2LongLinkedOpenHashMap<K>
/*      */   extends AbstractObject2LongSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient long[] value;
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
/*      */   protected transient Object2LongSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = (K[])new Object[this.n + 1];
/*  162 */     this.value = new long[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongLinkedOpenHashMap() {
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
/*      */   public Object2LongLinkedOpenHashMap(Map<? extends K, ? extends Long> m, float f) {
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
/*      */   public Object2LongLinkedOpenHashMap(Map<? extends K, ? extends Long> m) {
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
/*      */   public Object2LongLinkedOpenHashMap(Object2LongMap<K> m, float f) {
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
/*      */   public Object2LongLinkedOpenHashMap(Object2LongMap<K> m) {
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
/*      */   public Object2LongLinkedOpenHashMap(K[] k, long[] v, float f) {
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
/*      */   public Object2LongLinkedOpenHashMap(K[] k, long[] v) {
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
/*      */   private long removeEntry(int pos) {
/*  275 */     long oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     this.key[this.n] = null;
/*  286 */     long oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Long> m) {
/*  295 */     if (this.f <= 0.5D) {
/*  296 */       ensureCapacity(m.size());
/*      */     } else {
/*  298 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  300 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(K k) {
/*  304 */     if (k == null) {
/*  305 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  307 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  310 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  311 */       return -(pos + 1); 
/*  312 */     if (k.equals(curr)) {
/*  313 */       return pos;
/*      */     }
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  317 */         return -(pos + 1); 
/*  318 */       if (k.equals(curr))
/*  319 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, K k, long v) {
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
/*      */   public long put(K k, long v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     long oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  353 */     long oldValue = this.value[pos];
/*  354 */     this.value[pos] = oldValue + incr;
/*  355 */     return oldValue;
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
/*      */   public long addTo(K k, long incr) {
/*      */     int pos;
/*  375 */     if (k == null) {
/*  376 */       if (this.containsNullKey)
/*  377 */         return addToValue(this.n, incr); 
/*  378 */       pos = this.n;
/*  379 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  382 */       K[] key = this.key;
/*      */       K curr;
/*  384 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  385 */         if (curr.equals(k))
/*  386 */           return addToValue(pos, incr); 
/*  387 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  388 */           if (curr.equals(k))
/*  389 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  392 */     }  this.key[pos] = k;
/*  393 */     this.value[pos] = this.defRetValue + incr;
/*  394 */     if (this.size == 0) {
/*  395 */       this.first = this.last = pos;
/*      */       
/*  397 */       this.link[pos] = -1L;
/*      */     } else {
/*  399 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  400 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  401 */       this.last = pos;
/*      */     } 
/*  403 */     if (this.size++ >= this.maxFill) {
/*  404 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  407 */     return this.defRetValue;
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
/*  420 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  422 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  424 */         if ((curr = key[pos]) == null) {
/*  425 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  428 */         int slot = HashCommon.mix(curr.hashCode()) & this.mask;
/*  429 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  431 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  433 */       key[last] = curr;
/*  434 */       this.value[last] = this.value[pos];
/*  435 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long removeLong(Object k) {
/*  441 */     if (k == null) {
/*  442 */       if (this.containsNullKey)
/*  443 */         return removeNullEntry(); 
/*  444 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  447 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  450 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  451 */       return this.defRetValue; 
/*  452 */     if (k.equals(curr))
/*  453 */       return removeEntry(pos); 
/*      */     while (true) {
/*  455 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  456 */         return this.defRetValue; 
/*  457 */       if (k.equals(curr))
/*  458 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private long setValue(int pos, long v) {
/*  462 */     long oldValue = this.value[pos];
/*  463 */     this.value[pos] = v;
/*  464 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeFirstLong() {
/*  475 */     if (this.size == 0)
/*  476 */       throw new NoSuchElementException(); 
/*  477 */     int pos = this.first;
/*      */     
/*  479 */     this.first = (int)this.link[pos];
/*  480 */     if (0 <= this.first)
/*      */     {
/*  482 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  484 */     this.size--;
/*  485 */     long v = this.value[pos];
/*  486 */     if (pos == this.n) {
/*  487 */       this.containsNullKey = false;
/*  488 */       this.key[this.n] = null;
/*      */     } else {
/*  490 */       shiftKeys(pos);
/*  491 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  492 */       rehash(this.n / 2); 
/*  493 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long removeLastLong() {
/*  503 */     if (this.size == 0)
/*  504 */       throw new NoSuchElementException(); 
/*  505 */     int pos = this.last;
/*      */     
/*  507 */     this.last = (int)(this.link[pos] >>> 32L);
/*  508 */     if (0 <= this.last)
/*      */     {
/*  510 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  512 */     this.size--;
/*  513 */     long v = this.value[pos];
/*  514 */     if (pos == this.n) {
/*  515 */       this.containsNullKey = false;
/*  516 */       this.key[this.n] = null;
/*      */     } else {
/*  518 */       shiftKeys(pos);
/*  519 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  520 */       rehash(this.n / 2); 
/*  521 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  524 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  526 */     if (this.last == i) {
/*  527 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  529 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  531 */       long linki = this.link[i];
/*  532 */       int prev = (int)(linki >>> 32L);
/*  533 */       int next = (int)linki;
/*  534 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  535 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  537 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  538 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  539 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  542 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  544 */     if (this.first == i) {
/*  545 */       this.first = (int)this.link[i];
/*      */       
/*  547 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  549 */       long linki = this.link[i];
/*  550 */       int prev = (int)(linki >>> 32L);
/*  551 */       int next = (int)linki;
/*  552 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  553 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  555 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  556 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  557 */     this.last = i;
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
/*      */   public long getAndMoveToFirst(K k) {
/*  569 */     if (k == null) {
/*  570 */       if (this.containsNullKey) {
/*  571 */         moveIndexToFirst(this.n);
/*  572 */         return this.value[this.n];
/*      */       } 
/*  574 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  577 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  580 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  581 */       return this.defRetValue; 
/*  582 */     if (k.equals(curr)) {
/*  583 */       moveIndexToFirst(pos);
/*  584 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  588 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  589 */         return this.defRetValue; 
/*  590 */       if (k.equals(curr)) {
/*  591 */         moveIndexToFirst(pos);
/*  592 */         return this.value[pos];
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
/*      */   public long getAndMoveToLast(K k) {
/*  606 */     if (k == null) {
/*  607 */       if (this.containsNullKey) {
/*  608 */         moveIndexToLast(this.n);
/*  609 */         return this.value[this.n];
/*      */       } 
/*  611 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  614 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  617 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  618 */       return this.defRetValue; 
/*  619 */     if (k.equals(curr)) {
/*  620 */       moveIndexToLast(pos);
/*  621 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  625 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  626 */         return this.defRetValue; 
/*  627 */       if (k.equals(curr)) {
/*  628 */         moveIndexToLast(pos);
/*  629 */         return this.value[pos];
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
/*      */   public long putAndMoveToFirst(K k, long v) {
/*      */     int pos;
/*  646 */     if (k == null) {
/*  647 */       if (this.containsNullKey) {
/*  648 */         moveIndexToFirst(this.n);
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
/*  659 */           moveIndexToFirst(pos);
/*  660 */           return setValue(pos, v);
/*      */         } 
/*  662 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  663 */           if (curr.equals(k)) {
/*  664 */             moveIndexToFirst(pos);
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
/*  676 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  677 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  678 */       this.first = pos;
/*      */     } 
/*  680 */     if (this.size++ >= this.maxFill) {
/*  681 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  684 */     return this.defRetValue;
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
/*      */   public long putAndMoveToLast(K k, long v) {
/*      */     int pos;
/*  699 */     if (k == null) {
/*  700 */       if (this.containsNullKey) {
/*  701 */         moveIndexToLast(this.n);
/*  702 */         return setValue(this.n, v);
/*      */       } 
/*  704 */       this.containsNullKey = true;
/*  705 */       pos = this.n;
/*      */     } else {
/*      */       
/*  708 */       K[] key = this.key;
/*      */       K curr;
/*  710 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) != null) {
/*  711 */         if (curr.equals(k)) {
/*  712 */           moveIndexToLast(pos);
/*  713 */           return setValue(pos, v);
/*      */         } 
/*  715 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  716 */           if (curr.equals(k)) {
/*  717 */             moveIndexToLast(pos);
/*  718 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  722 */     }  this.key[pos] = k;
/*  723 */     this.value[pos] = v;
/*  724 */     if (this.size == 0) {
/*  725 */       this.first = this.last = pos;
/*      */       
/*  727 */       this.link[pos] = -1L;
/*      */     } else {
/*  729 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  730 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  731 */       this.last = pos;
/*      */     } 
/*  733 */     if (this.size++ >= this.maxFill) {
/*  734 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  737 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(Object k) {
/*  742 */     if (k == null) {
/*  743 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  745 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  748 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  749 */       return this.defRetValue; 
/*  750 */     if (k.equals(curr)) {
/*  751 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  754 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  755 */         return this.defRetValue; 
/*  756 */       if (k.equals(curr)) {
/*  757 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  763 */     if (k == null) {
/*  764 */       return this.containsNullKey;
/*      */     }
/*  766 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  769 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  770 */       return false; 
/*  771 */     if (k.equals(curr)) {
/*  772 */       return true;
/*      */     }
/*      */     while (true) {
/*  775 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  776 */         return false; 
/*  777 */       if (k.equals(curr))
/*  778 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  783 */     long[] value = this.value;
/*  784 */     K[] key = this.key;
/*  785 */     if (this.containsNullKey && value[this.n] == v)
/*  786 */       return true; 
/*  787 */     for (int i = this.n; i-- != 0;) {
/*  788 */       if (key[i] != null && value[i] == v)
/*  789 */         return true; 
/*  790 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(Object k, long defaultValue) {
/*  796 */     if (k == null) {
/*  797 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  799 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  802 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  803 */       return defaultValue; 
/*  804 */     if (k.equals(curr)) {
/*  805 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  808 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  809 */         return defaultValue; 
/*  810 */       if (k.equals(curr)) {
/*  811 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(K k, long v) {
/*  817 */     int pos = find(k);
/*  818 */     if (pos >= 0)
/*  819 */       return this.value[pos]; 
/*  820 */     insert(-pos - 1, k, v);
/*  821 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, long v) {
/*  827 */     if (k == null) {
/*  828 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  829 */         removeNullEntry();
/*  830 */         return true;
/*      */       } 
/*  832 */       return false;
/*      */     } 
/*      */     
/*  835 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  838 */     if ((curr = key[pos = HashCommon.mix(k.hashCode()) & this.mask]) == null)
/*  839 */       return false; 
/*  840 */     if (k.equals(curr) && v == this.value[pos]) {
/*  841 */       removeEntry(pos);
/*  842 */       return true;
/*      */     } 
/*      */     while (true) {
/*  845 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  846 */         return false; 
/*  847 */       if (k.equals(curr) && v == this.value[pos]) {
/*  848 */         removeEntry(pos);
/*  849 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, long oldValue, long v) {
/*  856 */     int pos = find(k);
/*  857 */     if (pos < 0 || oldValue != this.value[pos])
/*  858 */       return false; 
/*  859 */     this.value[pos] = v;
/*  860 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(K k, long v) {
/*  865 */     int pos = find(k);
/*  866 */     if (pos < 0)
/*  867 */       return this.defRetValue; 
/*  868 */     long oldValue = this.value[pos];
/*  869 */     this.value[pos] = v;
/*  870 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeLongIfAbsent(K k, ToLongFunction<? super K> mappingFunction) {
/*  875 */     Objects.requireNonNull(mappingFunction);
/*  876 */     int pos = find(k);
/*  877 */     if (pos >= 0)
/*  878 */       return this.value[pos]; 
/*  879 */     long newValue = mappingFunction.applyAsLong(k);
/*  880 */     insert(-pos - 1, k, newValue);
/*  881 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLongIfPresent(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  887 */     Objects.requireNonNull(remappingFunction);
/*  888 */     int pos = find(k);
/*  889 */     if (pos < 0)
/*  890 */       return this.defRetValue; 
/*  891 */     Long newValue = remappingFunction.apply(k, Long.valueOf(this.value[pos]));
/*  892 */     if (newValue == null) {
/*  893 */       if (k == null) {
/*  894 */         removeNullEntry();
/*      */       } else {
/*  896 */         removeEntry(pos);
/*  897 */       }  return this.defRetValue;
/*      */     } 
/*  899 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeLong(K k, BiFunction<? super K, ? super Long, ? extends Long> remappingFunction) {
/*  905 */     Objects.requireNonNull(remappingFunction);
/*  906 */     int pos = find(k);
/*  907 */     Long newValue = remappingFunction.apply(k, (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  908 */     if (newValue == null) {
/*  909 */       if (pos >= 0)
/*  910 */         if (k == null) {
/*  911 */           removeNullEntry();
/*      */         } else {
/*  913 */           removeEntry(pos);
/*      */         }  
/*  915 */       return this.defRetValue;
/*      */     } 
/*  917 */     long newVal = newValue.longValue();
/*  918 */     if (pos < 0) {
/*  919 */       insert(-pos - 1, k, newVal);
/*  920 */       return newVal;
/*      */     } 
/*  922 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long mergeLong(K k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  928 */     Objects.requireNonNull(remappingFunction);
/*  929 */     int pos = find(k);
/*  930 */     if (pos < 0) {
/*  931 */       insert(-pos - 1, k, v);
/*  932 */       return v;
/*      */     } 
/*  934 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  935 */     if (newValue == null) {
/*  936 */       if (k == null) {
/*  937 */         removeNullEntry();
/*      */       } else {
/*  939 */         removeEntry(pos);
/*  940 */       }  return this.defRetValue;
/*      */     } 
/*  942 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  953 */     if (this.size == 0)
/*      */       return; 
/*  955 */     this.size = 0;
/*  956 */     this.containsNullKey = false;
/*  957 */     Arrays.fill((Object[])this.key, (Object)null);
/*  958 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  962 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  966 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2LongMap.Entry<K>, Map.Entry<K, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  978 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  984 */       return Object2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  988 */       return Object2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  992 */       long oldValue = Object2LongLinkedOpenHashMap.this.value[this.index];
/*  993 */       Object2LongLinkedOpenHashMap.this.value[this.index] = v;
/*  994 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/* 1004 */       return Long.valueOf(Object2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/* 1014 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1019 */       if (!(o instanceof Map.Entry))
/* 1020 */         return false; 
/* 1021 */       Map.Entry<K, Long> e = (Map.Entry<K, Long>)o;
/* 1022 */       return (Objects.equals(Object2LongLinkedOpenHashMap.this.key[this.index], e.getKey()) && Object2LongLinkedOpenHashMap.this.value[this.index] == ((Long)e
/* 1023 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1027 */       return ((Object2LongLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2LongLinkedOpenHashMap.this.key[this.index].hashCode()) ^ 
/* 1028 */         HashCommon.long2int(Object2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1032 */       return (new StringBuilder()).append(Object2LongLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2LongLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1043 */     if (this.size == 0) {
/* 1044 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1047 */     if (this.first == i) {
/* 1048 */       this.first = (int)this.link[i];
/* 1049 */       if (0 <= this.first)
/*      */       {
/* 1051 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1055 */     if (this.last == i) {
/* 1056 */       this.last = (int)(this.link[i] >>> 32L);
/* 1057 */       if (0 <= this.last)
/*      */       {
/* 1059 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1063 */     long linki = this.link[i];
/* 1064 */     int prev = (int)(linki >>> 32L);
/* 1065 */     int next = (int)linki;
/* 1066 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1067 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1080 */     if (this.size == 1) {
/* 1081 */       this.first = this.last = d;
/*      */       
/* 1083 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1086 */     if (this.first == s) {
/* 1087 */       this.first = d;
/* 1088 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1089 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1092 */     if (this.last == s) {
/* 1093 */       this.last = d;
/* 1094 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1095 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1098 */     long links = this.link[s];
/* 1099 */     int prev = (int)(links >>> 32L);
/* 1100 */     int next = (int)links;
/* 1101 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1102 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1103 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1112 */     if (this.size == 0)
/* 1113 */       throw new NoSuchElementException(); 
/* 1114 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1123 */     if (this.size == 0)
/* 1124 */       throw new NoSuchElementException(); 
/* 1125 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> tailMap(K from) {
/* 1134 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> headMap(K to) {
/* 1143 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap<K> subMap(K from, K to) {
/* 1152 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1161 */     return null;
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
/* 1176 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1182 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1187 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1193 */     int index = -1;
/*      */     protected MapIterator() {
/* 1195 */       this.next = Object2LongLinkedOpenHashMap.this.first;
/* 1196 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1199 */       if (from == null) {
/* 1200 */         if (Object2LongLinkedOpenHashMap.this.containsNullKey) {
/* 1201 */           this.next = (int)Object2LongLinkedOpenHashMap.this.link[Object2LongLinkedOpenHashMap.this.n];
/* 1202 */           this.prev = Object2LongLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1205 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1207 */       if (Objects.equals(Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.last], from)) {
/* 1208 */         this.prev = Object2LongLinkedOpenHashMap.this.last;
/* 1209 */         this.index = Object2LongLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1213 */       int pos = HashCommon.mix(from.hashCode()) & Object2LongLinkedOpenHashMap.this.mask;
/*      */       
/* 1215 */       while (Object2LongLinkedOpenHashMap.this.key[pos] != null) {
/* 1216 */         if (Object2LongLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1218 */           this.next = (int)Object2LongLinkedOpenHashMap.this.link[pos];
/* 1219 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1222 */         pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1224 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1227 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1230 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1233 */       if (this.index >= 0)
/*      */         return; 
/* 1235 */       if (this.prev == -1) {
/* 1236 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1239 */       if (this.next == -1) {
/* 1240 */         this.index = Object2LongLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1243 */       int pos = Object2LongLinkedOpenHashMap.this.first;
/* 1244 */       this.index = 1;
/* 1245 */       while (pos != this.prev) {
/* 1246 */         pos = (int)Object2LongLinkedOpenHashMap.this.link[pos];
/* 1247 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1251 */       ensureIndexKnown();
/* 1252 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1255 */       ensureIndexKnown();
/* 1256 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1259 */       if (!hasNext())
/* 1260 */         throw new NoSuchElementException(); 
/* 1261 */       this.curr = this.next;
/* 1262 */       this.next = (int)Object2LongLinkedOpenHashMap.this.link[this.curr];
/* 1263 */       this.prev = this.curr;
/* 1264 */       if (this.index >= 0)
/* 1265 */         this.index++; 
/* 1266 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1269 */       if (!hasPrevious())
/* 1270 */         throw new NoSuchElementException(); 
/* 1271 */       this.curr = this.prev;
/* 1272 */       this.prev = (int)(Object2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1273 */       this.next = this.curr;
/* 1274 */       if (this.index >= 0)
/* 1275 */         this.index--; 
/* 1276 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1279 */       ensureIndexKnown();
/* 1280 */       if (this.curr == -1)
/* 1281 */         throw new IllegalStateException(); 
/* 1282 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1287 */         this.index--;
/* 1288 */         this.prev = (int)(Object2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1290 */         this.next = (int)Object2LongLinkedOpenHashMap.this.link[this.curr];
/* 1291 */       }  Object2LongLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1296 */       if (this.prev == -1) {
/* 1297 */         Object2LongLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1299 */         Object2LongLinkedOpenHashMap.this.link[this.prev] = Object2LongLinkedOpenHashMap.this.link[this.prev] ^ (Object2LongLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1300 */       }  if (this.next == -1) {
/* 1301 */         Object2LongLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1303 */         Object2LongLinkedOpenHashMap.this.link[this.next] = Object2LongLinkedOpenHashMap.this.link[this.next] ^ (Object2LongLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1304 */       }  int pos = this.curr;
/* 1305 */       this.curr = -1;
/* 1306 */       if (pos == Object2LongLinkedOpenHashMap.this.n) {
/* 1307 */         Object2LongLinkedOpenHashMap.this.containsNullKey = false;
/* 1308 */         Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1311 */         K[] key = Object2LongLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1315 */           pos = (last = pos) + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1317 */             if ((curr = key[pos]) == null) {
/* 1318 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1321 */             int slot = HashCommon.mix(curr.hashCode()) & Object2LongLinkedOpenHashMap.this.mask;
/* 1322 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1324 */             pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1326 */           key[last] = curr;
/* 1327 */           Object2LongLinkedOpenHashMap.this.value[last] = Object2LongLinkedOpenHashMap.this.value[pos];
/* 1328 */           if (this.next == pos)
/* 1329 */             this.next = last; 
/* 1330 */           if (this.prev == pos)
/* 1331 */             this.prev = last; 
/* 1332 */           Object2LongLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1337 */       int i = n;
/* 1338 */       while (i-- != 0 && hasNext())
/* 1339 */         nextEntry(); 
/* 1340 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1343 */       int i = n;
/* 1344 */       while (i-- != 0 && hasPrevious())
/* 1345 */         previousEntry(); 
/* 1346 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2LongMap.Entry<K> ok) {
/* 1349 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2LongMap.Entry<K> ok) {
/* 1352 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2LongMap.Entry<K>> { private Object2LongLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1360 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenHashMap<K>.MapEntry next() {
/* 1364 */       return this.entry = new Object2LongLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenHashMap<K>.MapEntry previous() {
/* 1368 */       return this.entry = new Object2LongLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1372 */       super.remove();
/* 1373 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1377 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2LongMap.Entry<K>> { final Object2LongLinkedOpenHashMap<K>.MapEntry entry = new Object2LongLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1381 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenHashMap<K>.MapEntry next() {
/* 1385 */       this.entry.index = nextEntry();
/* 1386 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2LongLinkedOpenHashMap<K>.MapEntry previous() {
/* 1390 */       this.entry.index = previousEntry();
/* 1391 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2LongMap.Entry<K>> implements Object2LongSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2LongMap.Entry<K>> iterator() {
/* 1399 */       return new Object2LongLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2LongMap.Entry<K>> comparator() {
/* 1403 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> subSet(Object2LongMap.Entry<K> fromElement, Object2LongMap.Entry<K> toElement) {
/* 1408 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> headSet(Object2LongMap.Entry<K> toElement) {
/* 1412 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2LongMap.Entry<K>> tailSet(Object2LongMap.Entry<K> fromElement) {
/* 1416 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2LongMap.Entry<K> first() {
/* 1420 */       if (Object2LongLinkedOpenHashMap.this.size == 0)
/* 1421 */         throw new NoSuchElementException(); 
/* 1422 */       return new Object2LongLinkedOpenHashMap.MapEntry(Object2LongLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2LongMap.Entry<K> last() {
/* 1426 */       if (Object2LongLinkedOpenHashMap.this.size == 0)
/* 1427 */         throw new NoSuchElementException(); 
/* 1428 */       return new Object2LongLinkedOpenHashMap.MapEntry(Object2LongLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1433 */       if (!(o instanceof Map.Entry))
/* 1434 */         return false; 
/* 1435 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1436 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1437 */         return false; 
/* 1438 */       K k = (K)e.getKey();
/* 1439 */       long v = ((Long)e.getValue()).longValue();
/* 1440 */       if (k == null) {
/* 1441 */         return (Object2LongLinkedOpenHashMap.this.containsNullKey && Object2LongLinkedOpenHashMap.this.value[Object2LongLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1443 */       K[] key = Object2LongLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1446 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2LongLinkedOpenHashMap.this.mask]) == null)
/* 1447 */         return false; 
/* 1448 */       if (k.equals(curr)) {
/* 1449 */         return (Object2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1452 */         if ((curr = key[pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask]) == null)
/* 1453 */           return false; 
/* 1454 */         if (k.equals(curr)) {
/* 1455 */           return (Object2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1461 */       if (!(o instanceof Map.Entry))
/* 1462 */         return false; 
/* 1463 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1464 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1465 */         return false; 
/* 1466 */       K k = (K)e.getKey();
/* 1467 */       long v = ((Long)e.getValue()).longValue();
/* 1468 */       if (k == null) {
/* 1469 */         if (Object2LongLinkedOpenHashMap.this.containsNullKey && Object2LongLinkedOpenHashMap.this.value[Object2LongLinkedOpenHashMap.this.n] == v) {
/* 1470 */           Object2LongLinkedOpenHashMap.this.removeNullEntry();
/* 1471 */           return true;
/*      */         } 
/* 1473 */         return false;
/*      */       } 
/*      */       
/* 1476 */       K[] key = Object2LongLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1479 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2LongLinkedOpenHashMap.this.mask]) == null)
/* 1480 */         return false; 
/* 1481 */       if (curr.equals(k)) {
/* 1482 */         if (Object2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1483 */           Object2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1484 */           return true;
/*      */         } 
/* 1486 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1489 */         if ((curr = key[pos = pos + 1 & Object2LongLinkedOpenHashMap.this.mask]) == null)
/* 1490 */           return false; 
/* 1491 */         if (curr.equals(k) && 
/* 1492 */           Object2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1493 */           Object2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1494 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1501 */       return Object2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1505 */       Object2LongLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> iterator(Object2LongMap.Entry<K> from) {
/* 1520 */       return new Object2LongLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> fastIterator() {
/* 1531 */       return new Object2LongLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2LongMap.Entry<K>> fastIterator(Object2LongMap.Entry<K> from) {
/* 1546 */       return new Object2LongLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/* 1551 */       for (int i = Object2LongLinkedOpenHashMap.this.size, next = Object2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1552 */         int curr = next;
/* 1553 */         next = (int)Object2LongLinkedOpenHashMap.this.link[curr];
/* 1554 */         consumer.accept(new AbstractObject2LongMap.BasicEntry<>(Object2LongLinkedOpenHashMap.this.key[curr], Object2LongLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2LongMap.Entry<K>> consumer) {
/* 1560 */       AbstractObject2LongMap.BasicEntry<K> entry = new AbstractObject2LongMap.BasicEntry<>();
/* 1561 */       for (int i = Object2LongLinkedOpenHashMap.this.size, next = Object2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1562 */         int curr = next;
/* 1563 */         next = (int)Object2LongLinkedOpenHashMap.this.link[curr];
/* 1564 */         entry.key = Object2LongLinkedOpenHashMap.this.key[curr];
/* 1565 */         entry.value = Object2LongLinkedOpenHashMap.this.value[curr];
/* 1566 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2LongSortedMap.FastSortedEntrySet<K> object2LongEntrySet() {
/* 1572 */     if (this.entries == null)
/* 1573 */       this.entries = new MapEntrySet(); 
/* 1574 */     return this.entries;
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
/* 1587 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1591 */       return Object2LongLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1598 */       return Object2LongLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1604 */       return new Object2LongLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1608 */       return new Object2LongLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1613 */       if (Object2LongLinkedOpenHashMap.this.containsNullKey)
/* 1614 */         consumer.accept(Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.n]); 
/* 1615 */       for (int pos = Object2LongLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1616 */         K k = Object2LongLinkedOpenHashMap.this.key[pos];
/* 1617 */         if (k != null)
/* 1618 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1623 */       return Object2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1627 */       return Object2LongLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1631 */       int oldSize = Object2LongLinkedOpenHashMap.this.size;
/* 1632 */       Object2LongLinkedOpenHashMap.this.removeLong(k);
/* 1633 */       return (Object2LongLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1637 */       Object2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1641 */       if (Object2LongLinkedOpenHashMap.this.size == 0)
/* 1642 */         throw new NoSuchElementException(); 
/* 1643 */       return Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1647 */       if (Object2LongLinkedOpenHashMap.this.size == 0)
/* 1648 */         throw new NoSuchElementException(); 
/* 1649 */       return Object2LongLinkedOpenHashMap.this.key[Object2LongLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1653 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1657 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1661 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1665 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1670 */     if (this.keys == null)
/* 1671 */       this.keys = new KeySet(); 
/* 1672 */     return this.keys;
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
/*      */     implements LongListIterator
/*      */   {
/*      */     public long previousLong() {
/* 1686 */       return Object2LongLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1693 */       return Object2LongLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1698 */     if (this.values == null)
/* 1699 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1702 */             return (LongIterator)new Object2LongLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1706 */             return Object2LongLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1710 */             return Object2LongLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1714 */             Object2LongLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1719 */             if (Object2LongLinkedOpenHashMap.this.containsNullKey)
/* 1720 */               consumer.accept(Object2LongLinkedOpenHashMap.this.value[Object2LongLinkedOpenHashMap.this.n]); 
/* 1721 */             for (int pos = Object2LongLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1722 */               if (Object2LongLinkedOpenHashMap.this.key[pos] != null)
/* 1723 */                 consumer.accept(Object2LongLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1726 */     return this.values;
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
/* 1743 */     return trim(this.size);
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
/* 1767 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1768 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1769 */       return true; 
/*      */     try {
/* 1771 */       rehash(l);
/* 1772 */     } catch (OutOfMemoryError cantDoIt) {
/* 1773 */       return false;
/*      */     } 
/* 1775 */     return true;
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
/* 1791 */     K[] key = this.key;
/* 1792 */     long[] value = this.value;
/* 1793 */     int mask = newN - 1;
/* 1794 */     K[] newKey = (K[])new Object[newN + 1];
/* 1795 */     long[] newValue = new long[newN + 1];
/* 1796 */     int i = this.first, prev = -1, newPrev = -1;
/* 1797 */     long[] link = this.link;
/* 1798 */     long[] newLink = new long[newN + 1];
/* 1799 */     this.first = -1;
/* 1800 */     for (int j = this.size; j-- != 0; ) {
/* 1801 */       int pos; if (key[i] == null) {
/* 1802 */         pos = newN;
/*      */       } else {
/* 1804 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1805 */         while (newKey[pos] != null)
/* 1806 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1808 */       newKey[pos] = key[i];
/* 1809 */       newValue[pos] = value[i];
/* 1810 */       if (prev != -1) {
/* 1811 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1812 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1813 */         newPrev = pos;
/*      */       } else {
/* 1815 */         newPrev = this.first = pos;
/*      */         
/* 1817 */         newLink[pos] = -1L;
/*      */       } 
/* 1819 */       int t = i;
/* 1820 */       i = (int)link[i];
/* 1821 */       prev = t;
/*      */     } 
/* 1823 */     this.link = newLink;
/* 1824 */     this.last = newPrev;
/* 1825 */     if (newPrev != -1)
/*      */     {
/* 1827 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1828 */     this.n = newN;
/* 1829 */     this.mask = mask;
/* 1830 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1831 */     this.key = newKey;
/* 1832 */     this.value = newValue;
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
/*      */   public Object2LongLinkedOpenHashMap<K> clone() {
/*      */     Object2LongLinkedOpenHashMap<K> c;
/*      */     try {
/* 1849 */       c = (Object2LongLinkedOpenHashMap<K>)super.clone();
/* 1850 */     } catch (CloneNotSupportedException cantHappen) {
/* 1851 */       throw new InternalError();
/*      */     } 
/* 1853 */     c.keys = null;
/* 1854 */     c.values = null;
/* 1855 */     c.entries = null;
/* 1856 */     c.containsNullKey = this.containsNullKey;
/* 1857 */     c.key = (K[])this.key.clone();
/* 1858 */     c.value = (long[])this.value.clone();
/* 1859 */     c.link = (long[])this.link.clone();
/* 1860 */     return c;
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
/* 1873 */     int h = 0;
/* 1874 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1875 */       while (this.key[i] == null)
/* 1876 */         i++; 
/* 1877 */       if (this != this.key[i])
/* 1878 */         t = this.key[i].hashCode(); 
/* 1879 */       t ^= HashCommon.long2int(this.value[i]);
/* 1880 */       h += t;
/* 1881 */       i++;
/*      */     } 
/*      */     
/* 1884 */     if (this.containsNullKey)
/* 1885 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1886 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1889 */     K[] key = this.key;
/* 1890 */     long[] value = this.value;
/* 1891 */     MapIterator i = new MapIterator();
/* 1892 */     s.defaultWriteObject();
/* 1893 */     for (int j = this.size; j-- != 0; ) {
/* 1894 */       int e = i.nextEntry();
/* 1895 */       s.writeObject(key[e]);
/* 1896 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1901 */     s.defaultReadObject();
/* 1902 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1903 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1904 */     this.mask = this.n - 1;
/* 1905 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1906 */     long[] value = this.value = new long[this.n + 1];
/* 1907 */     long[] link = this.link = new long[this.n + 1];
/* 1908 */     int prev = -1;
/* 1909 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1912 */     for (int i = this.size; i-- != 0; ) {
/* 1913 */       int pos; K k = (K)s.readObject();
/* 1914 */       long v = s.readLong();
/* 1915 */       if (k == null) {
/* 1916 */         pos = this.n;
/* 1917 */         this.containsNullKey = true;
/*      */       } else {
/* 1919 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1920 */         while (key[pos] != null)
/* 1921 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1923 */       key[pos] = k;
/* 1924 */       value[pos] = v;
/* 1925 */       if (this.first != -1) {
/* 1926 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1927 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1928 */         prev = pos; continue;
/*      */       } 
/* 1930 */       prev = this.first = pos;
/*      */       
/* 1932 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1935 */     this.last = prev;
/* 1936 */     if (prev != -1)
/*      */     {
/* 1938 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2LongLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */