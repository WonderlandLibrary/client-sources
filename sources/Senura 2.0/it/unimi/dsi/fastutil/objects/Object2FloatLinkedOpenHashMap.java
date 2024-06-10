/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ import java.util.function.DoubleConsumer;
/*      */ import java.util.function.ToDoubleFunction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Object2FloatLinkedOpenHashMap<K>
/*      */   extends AbstractObject2FloatSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
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
/*      */   protected transient Object2FloatSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = (K[])new Object[this.n + 1];
/*  162 */     this.value = new float[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatLinkedOpenHashMap() {
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
/*      */   public Object2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> m, float f) {
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
/*      */   public Object2FloatLinkedOpenHashMap(Map<? extends K, ? extends Float> m) {
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
/*      */   public Object2FloatLinkedOpenHashMap(Object2FloatMap<K> m, float f) {
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
/*      */   public Object2FloatLinkedOpenHashMap(Object2FloatMap<K> m) {
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
/*      */   public Object2FloatLinkedOpenHashMap(K[] k, float[] v, float f) {
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
/*      */   public Object2FloatLinkedOpenHashMap(K[] k, float[] v) {
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
/*  285 */     this.key[this.n] = null;
/*  286 */     float oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Float> m) {
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
/*      */   private void insert(int pos, K k, float v) {
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
/*      */   public float put(K k, float v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     float oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  353 */     float oldValue = this.value[pos];
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
/*      */   public float addTo(K k, float incr) {
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
/*      */   public float removeFloat(Object k) {
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
/*      */   private float setValue(int pos, float v) {
/*  462 */     float oldValue = this.value[pos];
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
/*      */   public float removeFirstFloat() {
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
/*  485 */     float v = this.value[pos];
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
/*      */   public float removeLastFloat() {
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
/*  513 */     float v = this.value[pos];
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
/*      */   public float getAndMoveToFirst(K k) {
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
/*      */   public float getAndMoveToLast(K k) {
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
/*      */   public float putAndMoveToFirst(K k, float v) {
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
/*      */   public float putAndMoveToLast(K k, float v) {
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
/*      */   public float getFloat(Object k) {
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
/*      */   public boolean containsValue(float v) {
/*  783 */     float[] value = this.value;
/*  784 */     K[] key = this.key;
/*  785 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  786 */       return true; 
/*  787 */     for (int i = this.n; i-- != 0;) {
/*  788 */       if (key[i] != null && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  789 */         return true; 
/*  790 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(Object k, float defaultValue) {
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
/*      */   public float putIfAbsent(K k, float v) {
/*  817 */     int pos = find(k);
/*  818 */     if (pos >= 0)
/*  819 */       return this.value[pos]; 
/*  820 */     insert(-pos - 1, k, v);
/*  821 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, float v) {
/*  827 */     if (k == null) {
/*  828 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
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
/*  840 */     if (k.equals(curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  841 */       removeEntry(pos);
/*  842 */       return true;
/*      */     } 
/*      */     while (true) {
/*  845 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  846 */         return false; 
/*  847 */       if (k.equals(curr) && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  848 */         removeEntry(pos);
/*  849 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, float oldValue, float v) {
/*  856 */     int pos = find(k);
/*  857 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  858 */       return false; 
/*  859 */     this.value[pos] = v;
/*  860 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(K k, float v) {
/*  865 */     int pos = find(k);
/*  866 */     if (pos < 0)
/*  867 */       return this.defRetValue; 
/*  868 */     float oldValue = this.value[pos];
/*  869 */     this.value[pos] = v;
/*  870 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeFloatIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  875 */     Objects.requireNonNull(mappingFunction);
/*  876 */     int pos = find(k);
/*  877 */     if (pos >= 0)
/*  878 */       return this.value[pos]; 
/*  879 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  880 */     insert(-pos - 1, k, newValue);
/*  881 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloatIfPresent(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  887 */     Objects.requireNonNull(remappingFunction);
/*  888 */     int pos = find(k);
/*  889 */     if (pos < 0)
/*  890 */       return this.defRetValue; 
/*  891 */     Float newValue = remappingFunction.apply(k, Float.valueOf(this.value[pos]));
/*  892 */     if (newValue == null) {
/*  893 */       if (k == null) {
/*  894 */         removeNullEntry();
/*      */       } else {
/*  896 */         removeEntry(pos);
/*  897 */       }  return this.defRetValue;
/*      */     } 
/*  899 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeFloat(K k, BiFunction<? super K, ? super Float, ? extends Float> remappingFunction) {
/*  905 */     Objects.requireNonNull(remappingFunction);
/*  906 */     int pos = find(k);
/*  907 */     Float newValue = remappingFunction.apply(k, (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  908 */     if (newValue == null) {
/*  909 */       if (pos >= 0)
/*  910 */         if (k == null) {
/*  911 */           removeNullEntry();
/*      */         } else {
/*  913 */           removeEntry(pos);
/*      */         }  
/*  915 */       return this.defRetValue;
/*      */     } 
/*  917 */     float newVal = newValue.floatValue();
/*  918 */     if (pos < 0) {
/*  919 */       insert(-pos - 1, k, newVal);
/*  920 */       return newVal;
/*      */     } 
/*  922 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float mergeFloat(K k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  928 */     Objects.requireNonNull(remappingFunction);
/*  929 */     int pos = find(k);
/*  930 */     if (pos < 0) {
/*  931 */       insert(-pos - 1, k, v);
/*  932 */       return v;
/*      */     } 
/*  934 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  935 */     if (newValue == null) {
/*  936 */       if (k == null) {
/*  937 */         removeNullEntry();
/*      */       } else {
/*  939 */         removeEntry(pos);
/*  940 */       }  return this.defRetValue;
/*      */     } 
/*  942 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*      */     implements Object2FloatMap.Entry<K>, Map.Entry<K, Float>
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
/*  984 */       return Object2FloatLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/*  988 */       return Object2FloatLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/*  992 */       float oldValue = Object2FloatLinkedOpenHashMap.this.value[this.index];
/*  993 */       Object2FloatLinkedOpenHashMap.this.value[this.index] = v;
/*  994 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1004 */       return Float.valueOf(Object2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/* 1014 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1019 */       if (!(o instanceof Map.Entry))
/* 1020 */         return false; 
/* 1021 */       Map.Entry<K, Float> e = (Map.Entry<K, Float>)o;
/* 1022 */       return (Objects.equals(Object2FloatLinkedOpenHashMap.this.key[this.index], e.getKey()) && 
/* 1023 */         Float.floatToIntBits(Object2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1027 */       return ((Object2FloatLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2FloatLinkedOpenHashMap.this.key[this.index].hashCode()) ^ 
/* 1028 */         HashCommon.float2int(Object2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1032 */       return (new StringBuilder()).append(Object2FloatLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2FloatLinkedOpenHashMap.this.value[this.index]).toString();
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
/*      */   public Object2FloatSortedMap<K> tailMap(K from) {
/* 1134 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatSortedMap<K> headMap(K to) {
/* 1143 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2FloatSortedMap<K> subMap(K from, K to) {
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
/* 1195 */       this.next = Object2FloatLinkedOpenHashMap.this.first;
/* 1196 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1199 */       if (from == null) {
/* 1200 */         if (Object2FloatLinkedOpenHashMap.this.containsNullKey) {
/* 1201 */           this.next = (int)Object2FloatLinkedOpenHashMap.this.link[Object2FloatLinkedOpenHashMap.this.n];
/* 1202 */           this.prev = Object2FloatLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1205 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1207 */       if (Objects.equals(Object2FloatLinkedOpenHashMap.this.key[Object2FloatLinkedOpenHashMap.this.last], from)) {
/* 1208 */         this.prev = Object2FloatLinkedOpenHashMap.this.last;
/* 1209 */         this.index = Object2FloatLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1213 */       int pos = HashCommon.mix(from.hashCode()) & Object2FloatLinkedOpenHashMap.this.mask;
/*      */       
/* 1215 */       while (Object2FloatLinkedOpenHashMap.this.key[pos] != null) {
/* 1216 */         if (Object2FloatLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1218 */           this.next = (int)Object2FloatLinkedOpenHashMap.this.link[pos];
/* 1219 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1222 */         pos = pos + 1 & Object2FloatLinkedOpenHashMap.this.mask;
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
/* 1240 */         this.index = Object2FloatLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1243 */       int pos = Object2FloatLinkedOpenHashMap.this.first;
/* 1244 */       this.index = 1;
/* 1245 */       while (pos != this.prev) {
/* 1246 */         pos = (int)Object2FloatLinkedOpenHashMap.this.link[pos];
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
/* 1262 */       this.next = (int)Object2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1263 */       this.prev = this.curr;
/* 1264 */       if (this.index >= 0)
/* 1265 */         this.index++; 
/* 1266 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1269 */       if (!hasPrevious())
/* 1270 */         throw new NoSuchElementException(); 
/* 1271 */       this.curr = this.prev;
/* 1272 */       this.prev = (int)(Object2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1288 */         this.prev = (int)(Object2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1290 */         this.next = (int)Object2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1291 */       }  Object2FloatLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1296 */       if (this.prev == -1) {
/* 1297 */         Object2FloatLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1299 */         Object2FloatLinkedOpenHashMap.this.link[this.prev] = Object2FloatLinkedOpenHashMap.this.link[this.prev] ^ (Object2FloatLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1300 */       }  if (this.next == -1) {
/* 1301 */         Object2FloatLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1303 */         Object2FloatLinkedOpenHashMap.this.link[this.next] = Object2FloatLinkedOpenHashMap.this.link[this.next] ^ (Object2FloatLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1304 */       }  int pos = this.curr;
/* 1305 */       this.curr = -1;
/* 1306 */       if (pos == Object2FloatLinkedOpenHashMap.this.n) {
/* 1307 */         Object2FloatLinkedOpenHashMap.this.containsNullKey = false;
/* 1308 */         Object2FloatLinkedOpenHashMap.this.key[Object2FloatLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1311 */         K[] key = Object2FloatLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1315 */           pos = (last = pos) + 1 & Object2FloatLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1317 */             if ((curr = key[pos]) == null) {
/* 1318 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1321 */             int slot = HashCommon.mix(curr.hashCode()) & Object2FloatLinkedOpenHashMap.this.mask;
/* 1322 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1324 */             pos = pos + 1 & Object2FloatLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1326 */           key[last] = curr;
/* 1327 */           Object2FloatLinkedOpenHashMap.this.value[last] = Object2FloatLinkedOpenHashMap.this.value[pos];
/* 1328 */           if (this.next == pos)
/* 1329 */             this.next = last; 
/* 1330 */           if (this.prev == pos)
/* 1331 */             this.prev = last; 
/* 1332 */           Object2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Object2FloatMap.Entry<K> ok) {
/* 1349 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2FloatMap.Entry<K> ok) {
/* 1352 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2FloatMap.Entry<K>> { private Object2FloatLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1360 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenHashMap<K>.MapEntry next() {
/* 1364 */       return this.entry = new Object2FloatLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenHashMap<K>.MapEntry previous() {
/* 1368 */       return this.entry = new Object2FloatLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1372 */       super.remove();
/* 1373 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1377 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2FloatMap.Entry<K>> { final Object2FloatLinkedOpenHashMap<K>.MapEntry entry = new Object2FloatLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1381 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenHashMap<K>.MapEntry next() {
/* 1385 */       this.entry.index = nextEntry();
/* 1386 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2FloatLinkedOpenHashMap<K>.MapEntry previous() {
/* 1390 */       this.entry.index = previousEntry();
/* 1391 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2FloatMap.Entry<K>> implements Object2FloatSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2FloatMap.Entry<K>> iterator() {
/* 1399 */       return new Object2FloatLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2FloatMap.Entry<K>> comparator() {
/* 1403 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> subSet(Object2FloatMap.Entry<K> fromElement, Object2FloatMap.Entry<K> toElement) {
/* 1408 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> headSet(Object2FloatMap.Entry<K> toElement) {
/* 1412 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2FloatMap.Entry<K>> tailSet(Object2FloatMap.Entry<K> fromElement) {
/* 1416 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> first() {
/* 1420 */       if (Object2FloatLinkedOpenHashMap.this.size == 0)
/* 1421 */         throw new NoSuchElementException(); 
/* 1422 */       return new Object2FloatLinkedOpenHashMap.MapEntry(Object2FloatLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2FloatMap.Entry<K> last() {
/* 1426 */       if (Object2FloatLinkedOpenHashMap.this.size == 0)
/* 1427 */         throw new NoSuchElementException(); 
/* 1428 */       return new Object2FloatLinkedOpenHashMap.MapEntry(Object2FloatLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1433 */       if (!(o instanceof Map.Entry))
/* 1434 */         return false; 
/* 1435 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1436 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1437 */         return false; 
/* 1438 */       K k = (K)e.getKey();
/* 1439 */       float v = ((Float)e.getValue()).floatValue();
/* 1440 */       if (k == null) {
/* 1441 */         return (Object2FloatLinkedOpenHashMap.this.containsNullKey && 
/* 1442 */           Float.floatToIntBits(Object2FloatLinkedOpenHashMap.this.value[Object2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1444 */       K[] key = Object2FloatLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1447 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2FloatLinkedOpenHashMap.this.mask]) == null)
/* 1448 */         return false; 
/* 1449 */       if (k.equals(curr)) {
/* 1450 */         return (Float.floatToIntBits(Object2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1453 */         if ((curr = key[pos = pos + 1 & Object2FloatLinkedOpenHashMap.this.mask]) == null)
/* 1454 */           return false; 
/* 1455 */         if (k.equals(curr)) {
/* 1456 */           return (Float.floatToIntBits(Object2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1462 */       if (!(o instanceof Map.Entry))
/* 1463 */         return false; 
/* 1464 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1465 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1466 */         return false; 
/* 1467 */       K k = (K)e.getKey();
/* 1468 */       float v = ((Float)e.getValue()).floatValue();
/* 1469 */       if (k == null) {
/* 1470 */         if (Object2FloatLinkedOpenHashMap.this.containsNullKey && Float.floatToIntBits(Object2FloatLinkedOpenHashMap.this.value[Object2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1471 */           Object2FloatLinkedOpenHashMap.this.removeNullEntry();
/* 1472 */           return true;
/*      */         } 
/* 1474 */         return false;
/*      */       } 
/*      */       
/* 1477 */       K[] key = Object2FloatLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1480 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2FloatLinkedOpenHashMap.this.mask]) == null)
/* 1481 */         return false; 
/* 1482 */       if (curr.equals(k)) {
/* 1483 */         if (Float.floatToIntBits(Object2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1484 */           Object2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1485 */           return true;
/*      */         } 
/* 1487 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1490 */         if ((curr = key[pos = pos + 1 & Object2FloatLinkedOpenHashMap.this.mask]) == null)
/* 1491 */           return false; 
/* 1492 */         if (curr.equals(k) && 
/* 1493 */           Float.floatToIntBits(Object2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1494 */           Object2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1495 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1502 */       return Object2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1506 */       Object2FloatLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2FloatMap.Entry<K>> iterator(Object2FloatMap.Entry<K> from) {
/* 1521 */       return new Object2FloatLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2FloatMap.Entry<K>> fastIterator() {
/* 1532 */       return new Object2FloatLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2FloatMap.Entry<K>> fastIterator(Object2FloatMap.Entry<K> from) {
/* 1547 */       return new Object2FloatLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/* 1552 */       for (int i = Object2FloatLinkedOpenHashMap.this.size, next = Object2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1553 */         int curr = next;
/* 1554 */         next = (int)Object2FloatLinkedOpenHashMap.this.link[curr];
/* 1555 */         consumer.accept(new AbstractObject2FloatMap.BasicEntry<>(Object2FloatLinkedOpenHashMap.this.key[curr], Object2FloatLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2FloatMap.Entry<K>> consumer) {
/* 1561 */       AbstractObject2FloatMap.BasicEntry<K> entry = new AbstractObject2FloatMap.BasicEntry<>();
/* 1562 */       for (int i = Object2FloatLinkedOpenHashMap.this.size, next = Object2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1563 */         int curr = next;
/* 1564 */         next = (int)Object2FloatLinkedOpenHashMap.this.link[curr];
/* 1565 */         entry.key = Object2FloatLinkedOpenHashMap.this.key[curr];
/* 1566 */         entry.value = Object2FloatLinkedOpenHashMap.this.value[curr];
/* 1567 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2FloatSortedMap.FastSortedEntrySet<K> object2FloatEntrySet() {
/* 1573 */     if (this.entries == null)
/* 1574 */       this.entries = new MapEntrySet(); 
/* 1575 */     return this.entries;
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
/* 1588 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1592 */       return Object2FloatLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1599 */       return Object2FloatLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1605 */       return new Object2FloatLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1609 */       return new Object2FloatLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1614 */       if (Object2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1615 */         consumer.accept(Object2FloatLinkedOpenHashMap.this.key[Object2FloatLinkedOpenHashMap.this.n]); 
/* 1616 */       for (int pos = Object2FloatLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1617 */         K k = Object2FloatLinkedOpenHashMap.this.key[pos];
/* 1618 */         if (k != null)
/* 1619 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1624 */       return Object2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1628 */       return Object2FloatLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1632 */       int oldSize = Object2FloatLinkedOpenHashMap.this.size;
/* 1633 */       Object2FloatLinkedOpenHashMap.this.removeFloat(k);
/* 1634 */       return (Object2FloatLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1638 */       Object2FloatLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1642 */       if (Object2FloatLinkedOpenHashMap.this.size == 0)
/* 1643 */         throw new NoSuchElementException(); 
/* 1644 */       return Object2FloatLinkedOpenHashMap.this.key[Object2FloatLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1648 */       if (Object2FloatLinkedOpenHashMap.this.size == 0)
/* 1649 */         throw new NoSuchElementException(); 
/* 1650 */       return Object2FloatLinkedOpenHashMap.this.key[Object2FloatLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1654 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1658 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1662 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1666 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1671 */     if (this.keys == null)
/* 1672 */       this.keys = new KeySet(); 
/* 1673 */     return this.keys;
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
/* 1687 */       return Object2FloatLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1694 */       return Object2FloatLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1699 */     if (this.values == null)
/* 1700 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1703 */             return (FloatIterator)new Object2FloatLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1707 */             return Object2FloatLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1711 */             return Object2FloatLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1715 */             Object2FloatLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1720 */             if (Object2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1721 */               consumer.accept(Object2FloatLinkedOpenHashMap.this.value[Object2FloatLinkedOpenHashMap.this.n]); 
/* 1722 */             for (int pos = Object2FloatLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1723 */               if (Object2FloatLinkedOpenHashMap.this.key[pos] != null)
/* 1724 */                 consumer.accept(Object2FloatLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1727 */     return this.values;
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
/* 1744 */     return trim(this.size);
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
/* 1768 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1769 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1770 */       return true; 
/*      */     try {
/* 1772 */       rehash(l);
/* 1773 */     } catch (OutOfMemoryError cantDoIt) {
/* 1774 */       return false;
/*      */     } 
/* 1776 */     return true;
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
/* 1792 */     K[] key = this.key;
/* 1793 */     float[] value = this.value;
/* 1794 */     int mask = newN - 1;
/* 1795 */     K[] newKey = (K[])new Object[newN + 1];
/* 1796 */     float[] newValue = new float[newN + 1];
/* 1797 */     int i = this.first, prev = -1, newPrev = -1;
/* 1798 */     long[] link = this.link;
/* 1799 */     long[] newLink = new long[newN + 1];
/* 1800 */     this.first = -1;
/* 1801 */     for (int j = this.size; j-- != 0; ) {
/* 1802 */       int pos; if (key[i] == null) {
/* 1803 */         pos = newN;
/*      */       } else {
/* 1805 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1806 */         while (newKey[pos] != null)
/* 1807 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1809 */       newKey[pos] = key[i];
/* 1810 */       newValue[pos] = value[i];
/* 1811 */       if (prev != -1) {
/* 1812 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1813 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1814 */         newPrev = pos;
/*      */       } else {
/* 1816 */         newPrev = this.first = pos;
/*      */         
/* 1818 */         newLink[pos] = -1L;
/*      */       } 
/* 1820 */       int t = i;
/* 1821 */       i = (int)link[i];
/* 1822 */       prev = t;
/*      */     } 
/* 1824 */     this.link = newLink;
/* 1825 */     this.last = newPrev;
/* 1826 */     if (newPrev != -1)
/*      */     {
/* 1828 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1829 */     this.n = newN;
/* 1830 */     this.mask = mask;
/* 1831 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1832 */     this.key = newKey;
/* 1833 */     this.value = newValue;
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
/*      */   public Object2FloatLinkedOpenHashMap<K> clone() {
/*      */     Object2FloatLinkedOpenHashMap<K> c;
/*      */     try {
/* 1850 */       c = (Object2FloatLinkedOpenHashMap<K>)super.clone();
/* 1851 */     } catch (CloneNotSupportedException cantHappen) {
/* 1852 */       throw new InternalError();
/*      */     } 
/* 1854 */     c.keys = null;
/* 1855 */     c.values = null;
/* 1856 */     c.entries = null;
/* 1857 */     c.containsNullKey = this.containsNullKey;
/* 1858 */     c.key = (K[])this.key.clone();
/* 1859 */     c.value = (float[])this.value.clone();
/* 1860 */     c.link = (long[])this.link.clone();
/* 1861 */     return c;
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
/* 1874 */     int h = 0;
/* 1875 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1876 */       while (this.key[i] == null)
/* 1877 */         i++; 
/* 1878 */       if (this != this.key[i])
/* 1879 */         t = this.key[i].hashCode(); 
/* 1880 */       t ^= HashCommon.float2int(this.value[i]);
/* 1881 */       h += t;
/* 1882 */       i++;
/*      */     } 
/*      */     
/* 1885 */     if (this.containsNullKey)
/* 1886 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1887 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1890 */     K[] key = this.key;
/* 1891 */     float[] value = this.value;
/* 1892 */     MapIterator i = new MapIterator();
/* 1893 */     s.defaultWriteObject();
/* 1894 */     for (int j = this.size; j-- != 0; ) {
/* 1895 */       int e = i.nextEntry();
/* 1896 */       s.writeObject(key[e]);
/* 1897 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1902 */     s.defaultReadObject();
/* 1903 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1904 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1905 */     this.mask = this.n - 1;
/* 1906 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1907 */     float[] value = this.value = new float[this.n + 1];
/* 1908 */     long[] link = this.link = new long[this.n + 1];
/* 1909 */     int prev = -1;
/* 1910 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1913 */     for (int i = this.size; i-- != 0; ) {
/* 1914 */       int pos; K k = (K)s.readObject();
/* 1915 */       float v = s.readFloat();
/* 1916 */       if (k == null) {
/* 1917 */         pos = this.n;
/* 1918 */         this.containsNullKey = true;
/*      */       } else {
/* 1920 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1921 */         while (key[pos] != null)
/* 1922 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1924 */       key[pos] = k;
/* 1925 */       value[pos] = v;
/* 1926 */       if (this.first != -1) {
/* 1927 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1928 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1929 */         prev = pos; continue;
/*      */       } 
/* 1931 */       prev = this.first = pos;
/*      */       
/* 1933 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1936 */     this.last = prev;
/* 1937 */     if (prev != -1)
/*      */     {
/* 1939 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2FloatLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */