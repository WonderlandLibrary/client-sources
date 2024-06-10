/*      */ package it.unimi.dsi.fastutil.objects;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
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
/*      */ 
/*      */ public class Object2DoubleLinkedOpenHashMap<K>
/*      */   extends AbstractObject2DoubleSortedMap<K>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient K[] key;
/*      */   protected transient double[] value;
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
/*      */   protected transient Object2DoubleSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = (K[])new Object[this.n + 1];
/*  162 */     this.value = new double[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleLinkedOpenHashMap() {
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
/*      */   public Object2DoubleLinkedOpenHashMap(Map<? extends K, ? extends Double> m, float f) {
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
/*      */   public Object2DoubleLinkedOpenHashMap(Map<? extends K, ? extends Double> m) {
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
/*      */   public Object2DoubleLinkedOpenHashMap(Object2DoubleMap<K> m, float f) {
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
/*      */   public Object2DoubleLinkedOpenHashMap(Object2DoubleMap<K> m) {
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
/*      */   public Object2DoubleLinkedOpenHashMap(K[] k, double[] v, float f) {
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
/*      */   public Object2DoubleLinkedOpenHashMap(K[] k, double[] v) {
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
/*      */   private double removeEntry(int pos) {
/*  275 */     double oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     this.key[this.n] = null;
/*  286 */     double oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends K, ? extends Double> m) {
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
/*      */   private void insert(int pos, K k, double v) {
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
/*      */   public double put(K k, double v) {
/*  343 */     int pos = find(k);
/*  344 */     if (pos < 0) {
/*  345 */       insert(-pos - 1, k, v);
/*  346 */       return this.defRetValue;
/*      */     } 
/*  348 */     double oldValue = this.value[pos];
/*  349 */     this.value[pos] = v;
/*  350 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  353 */     double oldValue = this.value[pos];
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
/*      */   public double addTo(K k, double incr) {
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
/*      */   public double removeDouble(Object k) {
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
/*      */   private double setValue(int pos, double v) {
/*  462 */     double oldValue = this.value[pos];
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
/*      */   public double removeFirstDouble() {
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
/*  485 */     double v = this.value[pos];
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
/*      */   public double removeLastDouble() {
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
/*  513 */     double v = this.value[pos];
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
/*      */   public double getAndMoveToFirst(K k) {
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
/*      */   public double getAndMoveToLast(K k) {
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
/*      */   public double putAndMoveToFirst(K k, double v) {
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
/*      */   public double putAndMoveToLast(K k, double v) {
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
/*      */   public double getDouble(Object k) {
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
/*      */   public boolean containsValue(double v) {
/*  783 */     double[] value = this.value;
/*  784 */     K[] key = this.key;
/*  785 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  786 */       return true; 
/*  787 */     for (int i = this.n; i-- != 0;) {
/*  788 */       if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  789 */         return true; 
/*  790 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
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
/*      */   public double putIfAbsent(K k, double v) {
/*  817 */     int pos = find(k);
/*  818 */     if (pos >= 0)
/*  819 */       return this.value[pos]; 
/*  820 */     insert(-pos - 1, k, v);
/*  821 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  827 */     if (k == null) {
/*  828 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
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
/*  840 */     if (k.equals(curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  841 */       removeEntry(pos);
/*  842 */       return true;
/*      */     } 
/*      */     while (true) {
/*  845 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  846 */         return false; 
/*  847 */       if (k.equals(curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  848 */         removeEntry(pos);
/*  849 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  856 */     int pos = find(k);
/*  857 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  858 */       return false; 
/*  859 */     this.value[pos] = v;
/*  860 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  865 */     int pos = find(k);
/*  866 */     if (pos < 0)
/*  867 */       return this.defRetValue; 
/*  868 */     double oldValue = this.value[pos];
/*  869 */     this.value[pos] = v;
/*  870 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  876 */     Objects.requireNonNull(mappingFunction);
/*  877 */     int pos = find(k);
/*  878 */     if (pos >= 0)
/*  879 */       return this.value[pos]; 
/*  880 */     double newValue = mappingFunction.applyAsDouble(k);
/*  881 */     insert(-pos - 1, k, newValue);
/*  882 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  888 */     Objects.requireNonNull(remappingFunction);
/*  889 */     int pos = find(k);
/*  890 */     if (pos < 0)
/*  891 */       return this.defRetValue; 
/*  892 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  893 */     if (newValue == null) {
/*  894 */       if (k == null) {
/*  895 */         removeNullEntry();
/*      */       } else {
/*  897 */         removeEntry(pos);
/*  898 */       }  return this.defRetValue;
/*      */     } 
/*  900 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  906 */     Objects.requireNonNull(remappingFunction);
/*  907 */     int pos = find(k);
/*  908 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  909 */     if (newValue == null) {
/*  910 */       if (pos >= 0)
/*  911 */         if (k == null) {
/*  912 */           removeNullEntry();
/*      */         } else {
/*  914 */           removeEntry(pos);
/*      */         }  
/*  916 */       return this.defRetValue;
/*      */     } 
/*  918 */     double newVal = newValue.doubleValue();
/*  919 */     if (pos < 0) {
/*  920 */       insert(-pos - 1, k, newVal);
/*  921 */       return newVal;
/*      */     } 
/*  923 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double mergeDouble(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  929 */     Objects.requireNonNull(remappingFunction);
/*  930 */     int pos = find(k);
/*  931 */     if (pos < 0) {
/*  932 */       insert(-pos - 1, k, v);
/*  933 */       return v;
/*      */     } 
/*  935 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  936 */     if (newValue == null) {
/*  937 */       if (k == null) {
/*  938 */         removeNullEntry();
/*      */       } else {
/*  940 */         removeEntry(pos);
/*  941 */       }  return this.defRetValue;
/*      */     } 
/*  943 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  954 */     if (this.size == 0)
/*      */       return; 
/*  956 */     this.size = 0;
/*  957 */     this.containsNullKey = false;
/*  958 */     Arrays.fill((Object[])this.key, (Object)null);
/*  959 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  963 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  967 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Object2DoubleMap.Entry<K>, Map.Entry<K, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  979 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  985 */       return Object2DoubleLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  989 */       return Object2DoubleLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  993 */       double oldValue = Object2DoubleLinkedOpenHashMap.this.value[this.index];
/*  994 */       Object2DoubleLinkedOpenHashMap.this.value[this.index] = v;
/*  995 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/* 1005 */       return Double.valueOf(Object2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/* 1015 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1020 */       if (!(o instanceof Map.Entry))
/* 1021 */         return false; 
/* 1022 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/* 1023 */       return (Objects.equals(Object2DoubleLinkedOpenHashMap.this.key[this.index], e.getKey()) && 
/* 1024 */         Double.doubleToLongBits(Object2DoubleLinkedOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1028 */       return ((Object2DoubleLinkedOpenHashMap.this.key[this.index] == null) ? 0 : Object2DoubleLinkedOpenHashMap.this.key[this.index].hashCode()) ^ 
/* 1029 */         HashCommon.double2int(Object2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1033 */       return (new StringBuilder()).append(Object2DoubleLinkedOpenHashMap.this.key[this.index]).append("=>").append(Object2DoubleLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1044 */     if (this.size == 0) {
/* 1045 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1048 */     if (this.first == i) {
/* 1049 */       this.first = (int)this.link[i];
/* 1050 */       if (0 <= this.first)
/*      */       {
/* 1052 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1056 */     if (this.last == i) {
/* 1057 */       this.last = (int)(this.link[i] >>> 32L);
/* 1058 */       if (0 <= this.last)
/*      */       {
/* 1060 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1064 */     long linki = this.link[i];
/* 1065 */     int prev = (int)(linki >>> 32L);
/* 1066 */     int next = (int)linki;
/* 1067 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1068 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1081 */     if (this.size == 1) {
/* 1082 */       this.first = this.last = d;
/*      */       
/* 1084 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1087 */     if (this.first == s) {
/* 1088 */       this.first = d;
/* 1089 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1090 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1093 */     if (this.last == s) {
/* 1094 */       this.last = d;
/* 1095 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1096 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1099 */     long links = this.link[s];
/* 1100 */     int prev = (int)(links >>> 32L);
/* 1101 */     int next = (int)links;
/* 1102 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1103 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1104 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1113 */     if (this.size == 0)
/* 1114 */       throw new NoSuchElementException(); 
/* 1115 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1124 */     if (this.size == 0)
/* 1125 */       throw new NoSuchElementException(); 
/* 1126 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> tailMap(K from) {
/* 1135 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> headMap(K to) {
/* 1144 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap<K> subMap(K from, K to) {
/* 1153 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1162 */     return null;
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
/* 1177 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1188 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     int index = -1;
/*      */     protected MapIterator() {
/* 1196 */       this.next = Object2DoubleLinkedOpenHashMap.this.first;
/* 1197 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1200 */       if (from == null) {
/* 1201 */         if (Object2DoubleLinkedOpenHashMap.this.containsNullKey) {
/* 1202 */           this.next = (int)Object2DoubleLinkedOpenHashMap.this.link[Object2DoubleLinkedOpenHashMap.this.n];
/* 1203 */           this.prev = Object2DoubleLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1206 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1208 */       if (Objects.equals(Object2DoubleLinkedOpenHashMap.this.key[Object2DoubleLinkedOpenHashMap.this.last], from)) {
/* 1209 */         this.prev = Object2DoubleLinkedOpenHashMap.this.last;
/* 1210 */         this.index = Object2DoubleLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1214 */       int pos = HashCommon.mix(from.hashCode()) & Object2DoubleLinkedOpenHashMap.this.mask;
/*      */       
/* 1216 */       while (Object2DoubleLinkedOpenHashMap.this.key[pos] != null) {
/* 1217 */         if (Object2DoubleLinkedOpenHashMap.this.key[pos].equals(from)) {
/*      */           
/* 1219 */           this.next = (int)Object2DoubleLinkedOpenHashMap.this.link[pos];
/* 1220 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1223 */         pos = pos + 1 & Object2DoubleLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1225 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1228 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1231 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1234 */       if (this.index >= 0)
/*      */         return; 
/* 1236 */       if (this.prev == -1) {
/* 1237 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1240 */       if (this.next == -1) {
/* 1241 */         this.index = Object2DoubleLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1244 */       int pos = Object2DoubleLinkedOpenHashMap.this.first;
/* 1245 */       this.index = 1;
/* 1246 */       while (pos != this.prev) {
/* 1247 */         pos = (int)Object2DoubleLinkedOpenHashMap.this.link[pos];
/* 1248 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1252 */       ensureIndexKnown();
/* 1253 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1256 */       ensureIndexKnown();
/* 1257 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1260 */       if (!hasNext())
/* 1261 */         throw new NoSuchElementException(); 
/* 1262 */       this.curr = this.next;
/* 1263 */       this.next = (int)Object2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1264 */       this.prev = this.curr;
/* 1265 */       if (this.index >= 0)
/* 1266 */         this.index++; 
/* 1267 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1270 */       if (!hasPrevious())
/* 1271 */         throw new NoSuchElementException(); 
/* 1272 */       this.curr = this.prev;
/* 1273 */       this.prev = (int)(Object2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1274 */       this.next = this.curr;
/* 1275 */       if (this.index >= 0)
/* 1276 */         this.index--; 
/* 1277 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1280 */       ensureIndexKnown();
/* 1281 */       if (this.curr == -1)
/* 1282 */         throw new IllegalStateException(); 
/* 1283 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1288 */         this.index--;
/* 1289 */         this.prev = (int)(Object2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1291 */         this.next = (int)Object2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1292 */       }  Object2DoubleLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1297 */       if (this.prev == -1) {
/* 1298 */         Object2DoubleLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1300 */         Object2DoubleLinkedOpenHashMap.this.link[this.prev] = Object2DoubleLinkedOpenHashMap.this.link[this.prev] ^ (Object2DoubleLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1301 */       }  if (this.next == -1) {
/* 1302 */         Object2DoubleLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1304 */         Object2DoubleLinkedOpenHashMap.this.link[this.next] = Object2DoubleLinkedOpenHashMap.this.link[this.next] ^ (Object2DoubleLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1305 */       }  int pos = this.curr;
/* 1306 */       this.curr = -1;
/* 1307 */       if (pos == Object2DoubleLinkedOpenHashMap.this.n) {
/* 1308 */         Object2DoubleLinkedOpenHashMap.this.containsNullKey = false;
/* 1309 */         Object2DoubleLinkedOpenHashMap.this.key[Object2DoubleLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1312 */         K[] key = Object2DoubleLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1316 */           pos = (last = pos) + 1 & Object2DoubleLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1318 */             if ((curr = key[pos]) == null) {
/* 1319 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1322 */             int slot = HashCommon.mix(curr.hashCode()) & Object2DoubleLinkedOpenHashMap.this.mask;
/* 1323 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1325 */             pos = pos + 1 & Object2DoubleLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1327 */           key[last] = curr;
/* 1328 */           Object2DoubleLinkedOpenHashMap.this.value[last] = Object2DoubleLinkedOpenHashMap.this.value[pos];
/* 1329 */           if (this.next == pos)
/* 1330 */             this.next = last; 
/* 1331 */           if (this.prev == pos)
/* 1332 */             this.prev = last; 
/* 1333 */           Object2DoubleLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1338 */       int i = n;
/* 1339 */       while (i-- != 0 && hasNext())
/* 1340 */         nextEntry(); 
/* 1341 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1344 */       int i = n;
/* 1345 */       while (i-- != 0 && hasPrevious())
/* 1346 */         previousEntry(); 
/* 1347 */       return n - i - 1;
/*      */     }
/*      */     public void set(Object2DoubleMap.Entry<K> ok) {
/* 1350 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Object2DoubleMap.Entry<K> ok) {
/* 1353 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Object2DoubleMap.Entry<K>> { private Object2DoubleLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1361 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenHashMap<K>.MapEntry next() {
/* 1365 */       return this.entry = new Object2DoubleLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenHashMap<K>.MapEntry previous() {
/* 1369 */       return this.entry = new Object2DoubleLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1373 */       super.remove();
/* 1374 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1378 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Object2DoubleMap.Entry<K>> { final Object2DoubleLinkedOpenHashMap<K>.MapEntry entry = new Object2DoubleLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1382 */       super(from);
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenHashMap<K>.MapEntry next() {
/* 1386 */       this.entry.index = nextEntry();
/* 1387 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Object2DoubleLinkedOpenHashMap<K>.MapEntry previous() {
/* 1391 */       this.entry.index = previousEntry();
/* 1392 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Object2DoubleMap.Entry<K>> implements Object2DoubleSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Object2DoubleMap.Entry<K>> iterator() {
/* 1400 */       return new Object2DoubleLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Object2DoubleMap.Entry<K>> comparator() {
/* 1404 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> subSet(Object2DoubleMap.Entry<K> fromElement, Object2DoubleMap.Entry<K> toElement) {
/* 1409 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> headSet(Object2DoubleMap.Entry<K> toElement) {
/* 1413 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Object2DoubleMap.Entry<K>> tailSet(Object2DoubleMap.Entry<K> fromElement) {
/* 1417 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Object2DoubleMap.Entry<K> first() {
/* 1421 */       if (Object2DoubleLinkedOpenHashMap.this.size == 0)
/* 1422 */         throw new NoSuchElementException(); 
/* 1423 */       return new Object2DoubleLinkedOpenHashMap.MapEntry(Object2DoubleLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Object2DoubleMap.Entry<K> last() {
/* 1427 */       if (Object2DoubleLinkedOpenHashMap.this.size == 0)
/* 1428 */         throw new NoSuchElementException(); 
/* 1429 */       return new Object2DoubleLinkedOpenHashMap.MapEntry(Object2DoubleLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1434 */       if (!(o instanceof Map.Entry))
/* 1435 */         return false; 
/* 1436 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1437 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1438 */         return false; 
/* 1439 */       K k = (K)e.getKey();
/* 1440 */       double v = ((Double)e.getValue()).doubleValue();
/* 1441 */       if (k == null) {
/* 1442 */         return (Object2DoubleLinkedOpenHashMap.this.containsNullKey && 
/* 1443 */           Double.doubleToLongBits(Object2DoubleLinkedOpenHashMap.this.value[Object2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/* 1445 */       K[] key = Object2DoubleLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1448 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2DoubleLinkedOpenHashMap.this.mask]) == null)
/* 1449 */         return false; 
/* 1450 */       if (k.equals(curr)) {
/* 1451 */         return (Double.doubleToLongBits(Object2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/* 1454 */         if ((curr = key[pos = pos + 1 & Object2DoubleLinkedOpenHashMap.this.mask]) == null)
/* 1455 */           return false; 
/* 1456 */         if (k.equals(curr)) {
/* 1457 */           return (Double.doubleToLongBits(Object2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1463 */       if (!(o instanceof Map.Entry))
/* 1464 */         return false; 
/* 1465 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1466 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1467 */         return false; 
/* 1468 */       K k = (K)e.getKey();
/* 1469 */       double v = ((Double)e.getValue()).doubleValue();
/* 1470 */       if (k == null) {
/* 1471 */         if (Object2DoubleLinkedOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Object2DoubleLinkedOpenHashMap.this.value[Object2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1472 */           Object2DoubleLinkedOpenHashMap.this.removeNullEntry();
/* 1473 */           return true;
/*      */         } 
/* 1475 */         return false;
/*      */       } 
/*      */       
/* 1478 */       K[] key = Object2DoubleLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1481 */       if ((curr = key[pos = HashCommon.mix(k.hashCode()) & Object2DoubleLinkedOpenHashMap.this.mask]) == null)
/* 1482 */         return false; 
/* 1483 */       if (curr.equals(k)) {
/* 1484 */         if (Double.doubleToLongBits(Object2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1485 */           Object2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1486 */           return true;
/*      */         } 
/* 1488 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1491 */         if ((curr = key[pos = pos + 1 & Object2DoubleLinkedOpenHashMap.this.mask]) == null)
/* 1492 */           return false; 
/* 1493 */         if (curr.equals(k) && 
/* 1494 */           Double.doubleToLongBits(Object2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1495 */           Object2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1496 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1503 */       return Object2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1507 */       Object2DoubleLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Object2DoubleMap.Entry<K>> iterator(Object2DoubleMap.Entry<K> from) {
/* 1522 */       return new Object2DoubleLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Object2DoubleMap.Entry<K>> fastIterator() {
/* 1533 */       return new Object2DoubleLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Object2DoubleMap.Entry<K>> fastIterator(Object2DoubleMap.Entry<K> from) {
/* 1548 */       return new Object2DoubleLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/* 1553 */       for (int i = Object2DoubleLinkedOpenHashMap.this.size, next = Object2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1554 */         int curr = next;
/* 1555 */         next = (int)Object2DoubleLinkedOpenHashMap.this.link[curr];
/* 1556 */         consumer.accept(new AbstractObject2DoubleMap.BasicEntry<>(Object2DoubleLinkedOpenHashMap.this.key[curr], Object2DoubleLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Object2DoubleMap.Entry<K>> consumer) {
/* 1562 */       AbstractObject2DoubleMap.BasicEntry<K> entry = new AbstractObject2DoubleMap.BasicEntry<>();
/* 1563 */       for (int i = Object2DoubleLinkedOpenHashMap.this.size, next = Object2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1564 */         int curr = next;
/* 1565 */         next = (int)Object2DoubleLinkedOpenHashMap.this.link[curr];
/* 1566 */         entry.key = Object2DoubleLinkedOpenHashMap.this.key[curr];
/* 1567 */         entry.value = Object2DoubleLinkedOpenHashMap.this.value[curr];
/* 1568 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Object2DoubleSortedMap.FastSortedEntrySet<K> object2DoubleEntrySet() {
/* 1574 */     if (this.entries == null)
/* 1575 */       this.entries = new MapEntrySet(); 
/* 1576 */     return this.entries;
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
/* 1589 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1593 */       return Object2DoubleLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1600 */       return Object2DoubleLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractObjectSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1606 */       return new Object2DoubleLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1610 */       return new Object2DoubleLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1615 */       if (Object2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1616 */         consumer.accept(Object2DoubleLinkedOpenHashMap.this.key[Object2DoubleLinkedOpenHashMap.this.n]); 
/* 1617 */       for (int pos = Object2DoubleLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1618 */         K k = Object2DoubleLinkedOpenHashMap.this.key[pos];
/* 1619 */         if (k != null)
/* 1620 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1625 */       return Object2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1629 */       return Object2DoubleLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1633 */       int oldSize = Object2DoubleLinkedOpenHashMap.this.size;
/* 1634 */       Object2DoubleLinkedOpenHashMap.this.removeDouble(k);
/* 1635 */       return (Object2DoubleLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1639 */       Object2DoubleLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1643 */       if (Object2DoubleLinkedOpenHashMap.this.size == 0)
/* 1644 */         throw new NoSuchElementException(); 
/* 1645 */       return Object2DoubleLinkedOpenHashMap.this.key[Object2DoubleLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1649 */       if (Object2DoubleLinkedOpenHashMap.this.size == 0)
/* 1650 */         throw new NoSuchElementException(); 
/* 1651 */       return Object2DoubleLinkedOpenHashMap.this.key[Object2DoubleLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1655 */       return null;
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> tailSet(K from) {
/* 1659 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> headSet(K to) {
/* 1663 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<K> subSet(K from, K to) {
/* 1667 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ObjectSortedSet<K> keySet() {
/* 1672 */     if (this.keys == null)
/* 1673 */       this.keys = new KeySet(); 
/* 1674 */     return this.keys;
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
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public double previousDouble() {
/* 1688 */       return Object2DoubleLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1695 */       return Object2DoubleLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1700 */     if (this.values == null)
/* 1701 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1704 */             return (DoubleIterator)new Object2DoubleLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1708 */             return Object2DoubleLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1712 */             return Object2DoubleLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1716 */             Object2DoubleLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1721 */             if (Object2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1722 */               consumer.accept(Object2DoubleLinkedOpenHashMap.this.value[Object2DoubleLinkedOpenHashMap.this.n]); 
/* 1723 */             for (int pos = Object2DoubleLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1724 */               if (Object2DoubleLinkedOpenHashMap.this.key[pos] != null)
/* 1725 */                 consumer.accept(Object2DoubleLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1728 */     return this.values;
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
/* 1745 */     return trim(this.size);
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
/* 1769 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1770 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1771 */       return true; 
/*      */     try {
/* 1773 */       rehash(l);
/* 1774 */     } catch (OutOfMemoryError cantDoIt) {
/* 1775 */       return false;
/*      */     } 
/* 1777 */     return true;
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
/* 1793 */     K[] key = this.key;
/* 1794 */     double[] value = this.value;
/* 1795 */     int mask = newN - 1;
/* 1796 */     K[] newKey = (K[])new Object[newN + 1];
/* 1797 */     double[] newValue = new double[newN + 1];
/* 1798 */     int i = this.first, prev = -1, newPrev = -1;
/* 1799 */     long[] link = this.link;
/* 1800 */     long[] newLink = new long[newN + 1];
/* 1801 */     this.first = -1;
/* 1802 */     for (int j = this.size; j-- != 0; ) {
/* 1803 */       int pos; if (key[i] == null) {
/* 1804 */         pos = newN;
/*      */       } else {
/* 1806 */         pos = HashCommon.mix(key[i].hashCode()) & mask;
/* 1807 */         while (newKey[pos] != null)
/* 1808 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1810 */       newKey[pos] = key[i];
/* 1811 */       newValue[pos] = value[i];
/* 1812 */       if (prev != -1) {
/* 1813 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1814 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1815 */         newPrev = pos;
/*      */       } else {
/* 1817 */         newPrev = this.first = pos;
/*      */         
/* 1819 */         newLink[pos] = -1L;
/*      */       } 
/* 1821 */       int t = i;
/* 1822 */       i = (int)link[i];
/* 1823 */       prev = t;
/*      */     } 
/* 1825 */     this.link = newLink;
/* 1826 */     this.last = newPrev;
/* 1827 */     if (newPrev != -1)
/*      */     {
/* 1829 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1830 */     this.n = newN;
/* 1831 */     this.mask = mask;
/* 1832 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1833 */     this.key = newKey;
/* 1834 */     this.value = newValue;
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
/*      */   public Object2DoubleLinkedOpenHashMap<K> clone() {
/*      */     Object2DoubleLinkedOpenHashMap<K> c;
/*      */     try {
/* 1851 */       c = (Object2DoubleLinkedOpenHashMap<K>)super.clone();
/* 1852 */     } catch (CloneNotSupportedException cantHappen) {
/* 1853 */       throw new InternalError();
/*      */     } 
/* 1855 */     c.keys = null;
/* 1856 */     c.values = null;
/* 1857 */     c.entries = null;
/* 1858 */     c.containsNullKey = this.containsNullKey;
/* 1859 */     c.key = (K[])this.key.clone();
/* 1860 */     c.value = (double[])this.value.clone();
/* 1861 */     c.link = (long[])this.link.clone();
/* 1862 */     return c;
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
/* 1875 */     int h = 0;
/* 1876 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1877 */       while (this.key[i] == null)
/* 1878 */         i++; 
/* 1879 */       if (this != this.key[i])
/* 1880 */         t = this.key[i].hashCode(); 
/* 1881 */       t ^= HashCommon.double2int(this.value[i]);
/* 1882 */       h += t;
/* 1883 */       i++;
/*      */     } 
/*      */     
/* 1886 */     if (this.containsNullKey)
/* 1887 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1888 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1891 */     K[] key = this.key;
/* 1892 */     double[] value = this.value;
/* 1893 */     MapIterator i = new MapIterator();
/* 1894 */     s.defaultWriteObject();
/* 1895 */     for (int j = this.size; j-- != 0; ) {
/* 1896 */       int e = i.nextEntry();
/* 1897 */       s.writeObject(key[e]);
/* 1898 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1903 */     s.defaultReadObject();
/* 1904 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1905 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1906 */     this.mask = this.n - 1;
/* 1907 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1908 */     double[] value = this.value = new double[this.n + 1];
/* 1909 */     long[] link = this.link = new long[this.n + 1];
/* 1910 */     int prev = -1;
/* 1911 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1914 */     for (int i = this.size; i-- != 0; ) {
/* 1915 */       int pos; K k = (K)s.readObject();
/* 1916 */       double v = s.readDouble();
/* 1917 */       if (k == null) {
/* 1918 */         pos = this.n;
/* 1919 */         this.containsNullKey = true;
/*      */       } else {
/* 1921 */         pos = HashCommon.mix(k.hashCode()) & this.mask;
/* 1922 */         while (key[pos] != null)
/* 1923 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1925 */       key[pos] = k;
/* 1926 */       value[pos] = v;
/* 1927 */       if (this.first != -1) {
/* 1928 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1929 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1930 */         prev = pos; continue;
/*      */       } 
/* 1932 */       prev = this.first = pos;
/*      */       
/* 1934 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1937 */     this.last = prev;
/* 1938 */     if (prev != -1)
/*      */     {
/* 1940 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Object2DoubleLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */