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
/*      */ public class Reference2DoubleLinkedOpenHashMap<K>
/*      */   extends AbstractReference2DoubleSortedMap<K>
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
/*      */   protected transient Reference2DoubleSortedMap.FastSortedEntrySet<K> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceSortedSet<K> keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleLinkedOpenHashMap(int expected, float f) {
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
/*      */   public Reference2DoubleLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleLinkedOpenHashMap() {
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
/*      */   public Reference2DoubleLinkedOpenHashMap(Map<? extends K, ? extends Double> m, float f) {
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
/*      */   public Reference2DoubleLinkedOpenHashMap(Map<? extends K, ? extends Double> m) {
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
/*      */   public Reference2DoubleLinkedOpenHashMap(Reference2DoubleMap<K> m, float f) {
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
/*      */   public Reference2DoubleLinkedOpenHashMap(Reference2DoubleMap<K> m) {
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
/*      */   public Reference2DoubleLinkedOpenHashMap(K[] k, double[] v, float f) {
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
/*      */   public Reference2DoubleLinkedOpenHashMap(K[] k, double[] v) {
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
/*  310 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  311 */       return -(pos + 1); 
/*  312 */     if (k == curr) {
/*  313 */       return pos;
/*      */     }
/*      */     while (true) {
/*  316 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  317 */         return -(pos + 1); 
/*  318 */       if (k == curr)
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
/*  384 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  386 */         if (curr == k)
/*  387 */           return addToValue(pos, incr); 
/*  388 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  389 */           if (curr == k)
/*  390 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  393 */     }  this.key[pos] = k;
/*  394 */     this.value[pos] = this.defRetValue + incr;
/*  395 */     if (this.size == 0) {
/*  396 */       this.first = this.last = pos;
/*      */       
/*  398 */       this.link[pos] = -1L;
/*      */     } else {
/*  400 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  401 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  402 */       this.last = pos;
/*      */     } 
/*  404 */     if (this.size++ >= this.maxFill) {
/*  405 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  408 */     return this.defRetValue;
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
/*  421 */     K[] key = this.key; while (true) {
/*      */       K curr; int last;
/*  423 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  425 */         if ((curr = key[pos]) == null) {
/*  426 */           key[last] = null;
/*      */           return;
/*      */         } 
/*  429 */         int slot = HashCommon.mix(System.identityHashCode(curr)) & this.mask;
/*  430 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  432 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  434 */       key[last] = curr;
/*  435 */       this.value[last] = this.value[pos];
/*  436 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double removeDouble(Object k) {
/*  442 */     if (k == null) {
/*  443 */       if (this.containsNullKey)
/*  444 */         return removeNullEntry(); 
/*  445 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  448 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  451 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  452 */       return this.defRetValue; 
/*  453 */     if (k == curr)
/*  454 */       return removeEntry(pos); 
/*      */     while (true) {
/*  456 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  457 */         return this.defRetValue; 
/*  458 */       if (k == curr)
/*  459 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private double setValue(int pos, double v) {
/*  463 */     double oldValue = this.value[pos];
/*  464 */     this.value[pos] = v;
/*  465 */     return oldValue;
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
/*  476 */     if (this.size == 0)
/*  477 */       throw new NoSuchElementException(); 
/*  478 */     int pos = this.first;
/*      */     
/*  480 */     this.first = (int)this.link[pos];
/*  481 */     if (0 <= this.first)
/*      */     {
/*  483 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  485 */     this.size--;
/*  486 */     double v = this.value[pos];
/*  487 */     if (pos == this.n) {
/*  488 */       this.containsNullKey = false;
/*  489 */       this.key[this.n] = null;
/*      */     } else {
/*  491 */       shiftKeys(pos);
/*  492 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  493 */       rehash(this.n / 2); 
/*  494 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeLastDouble() {
/*  504 */     if (this.size == 0)
/*  505 */       throw new NoSuchElementException(); 
/*  506 */     int pos = this.last;
/*      */     
/*  508 */     this.last = (int)(this.link[pos] >>> 32L);
/*  509 */     if (0 <= this.last)
/*      */     {
/*  511 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  513 */     this.size--;
/*  514 */     double v = this.value[pos];
/*  515 */     if (pos == this.n) {
/*  516 */       this.containsNullKey = false;
/*  517 */       this.key[this.n] = null;
/*      */     } else {
/*  519 */       shiftKeys(pos);
/*  520 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  521 */       rehash(this.n / 2); 
/*  522 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  525 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  527 */     if (this.last == i) {
/*  528 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  530 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  532 */       long linki = this.link[i];
/*  533 */       int prev = (int)(linki >>> 32L);
/*  534 */       int next = (int)linki;
/*  535 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  536 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  538 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  539 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  540 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  543 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  545 */     if (this.first == i) {
/*  546 */       this.first = (int)this.link[i];
/*      */       
/*  548 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  550 */       long linki = this.link[i];
/*  551 */       int prev = (int)(linki >>> 32L);
/*  552 */       int next = (int)linki;
/*  553 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  554 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  556 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  557 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  558 */     this.last = i;
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
/*  570 */     if (k == null) {
/*  571 */       if (this.containsNullKey) {
/*  572 */         moveIndexToFirst(this.n);
/*  573 */         return this.value[this.n];
/*      */       } 
/*  575 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  578 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  581 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  582 */       return this.defRetValue; 
/*  583 */     if (k == curr) {
/*  584 */       moveIndexToFirst(pos);
/*  585 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  589 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  590 */         return this.defRetValue; 
/*  591 */       if (k == curr) {
/*  592 */         moveIndexToFirst(pos);
/*  593 */         return this.value[pos];
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
/*  607 */     if (k == null) {
/*  608 */       if (this.containsNullKey) {
/*  609 */         moveIndexToLast(this.n);
/*  610 */         return this.value[this.n];
/*      */       } 
/*  612 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  615 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  618 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  619 */       return this.defRetValue; 
/*  620 */     if (k == curr) {
/*  621 */       moveIndexToLast(pos);
/*  622 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  626 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  627 */         return this.defRetValue; 
/*  628 */       if (k == curr) {
/*  629 */         moveIndexToLast(pos);
/*  630 */         return this.value[pos];
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
/*  647 */     if (k == null) {
/*  648 */       if (this.containsNullKey) {
/*  649 */         moveIndexToFirst(this.n);
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
/*  661 */           moveIndexToFirst(pos);
/*  662 */           return setValue(pos, v);
/*      */         } 
/*  664 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  665 */           if (curr == k) {
/*  666 */             moveIndexToFirst(pos);
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
/*  678 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  679 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  680 */       this.first = pos;
/*      */     } 
/*  682 */     if (this.size++ >= this.maxFill) {
/*  683 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  686 */     return this.defRetValue;
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
/*  701 */     if (k == null) {
/*  702 */       if (this.containsNullKey) {
/*  703 */         moveIndexToLast(this.n);
/*  704 */         return setValue(this.n, v);
/*      */       } 
/*  706 */       this.containsNullKey = true;
/*  707 */       pos = this.n;
/*      */     } else {
/*      */       
/*  710 */       K[] key = this.key;
/*      */       K curr;
/*  712 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) != null) {
/*      */         
/*  714 */         if (curr == k) {
/*  715 */           moveIndexToLast(pos);
/*  716 */           return setValue(pos, v);
/*      */         } 
/*  718 */         while ((curr = key[pos = pos + 1 & this.mask]) != null) {
/*  719 */           if (curr == k) {
/*  720 */             moveIndexToLast(pos);
/*  721 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  725 */     }  this.key[pos] = k;
/*  726 */     this.value[pos] = v;
/*  727 */     if (this.size == 0) {
/*  728 */       this.first = this.last = pos;
/*      */       
/*  730 */       this.link[pos] = -1L;
/*      */     } else {
/*  732 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  733 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  734 */       this.last = pos;
/*      */     } 
/*  736 */     if (this.size++ >= this.maxFill) {
/*  737 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  740 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(Object k) {
/*  745 */     if (k == null) {
/*  746 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  748 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  751 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  752 */       return this.defRetValue; 
/*  753 */     if (k == curr) {
/*  754 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  757 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  758 */         return this.defRetValue; 
/*  759 */       if (k == curr) {
/*  760 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(Object k) {
/*  766 */     if (k == null) {
/*  767 */       return this.containsNullKey;
/*      */     }
/*  769 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  772 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  773 */       return false; 
/*  774 */     if (k == curr) {
/*  775 */       return true;
/*      */     }
/*      */     while (true) {
/*  778 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  779 */         return false; 
/*  780 */       if (k == curr)
/*  781 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  786 */     double[] value = this.value;
/*  787 */     K[] key = this.key;
/*  788 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  789 */       return true; 
/*  790 */     for (int i = this.n; i-- != 0;) {
/*  791 */       if (key[i] != null && Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  792 */         return true; 
/*  793 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(Object k, double defaultValue) {
/*  799 */     if (k == null) {
/*  800 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  802 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  805 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  806 */       return defaultValue; 
/*  807 */     if (k == curr) {
/*  808 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  811 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  812 */         return defaultValue; 
/*  813 */       if (k == curr) {
/*  814 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(K k, double v) {
/*  820 */     int pos = find(k);
/*  821 */     if (pos >= 0)
/*  822 */       return this.value[pos]; 
/*  823 */     insert(-pos - 1, k, v);
/*  824 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object k, double v) {
/*  830 */     if (k == null) {
/*  831 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  832 */         removeNullEntry();
/*  833 */         return true;
/*      */       } 
/*  835 */       return false;
/*      */     } 
/*      */     
/*  838 */     K[] key = this.key;
/*      */     K curr;
/*      */     int pos;
/*  841 */     if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & this.mask]) == null)
/*  842 */       return false; 
/*  843 */     if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  844 */       removeEntry(pos);
/*  845 */       return true;
/*      */     } 
/*      */     while (true) {
/*  848 */       if ((curr = key[pos = pos + 1 & this.mask]) == null)
/*  849 */         return false; 
/*  850 */       if (k == curr && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  851 */         removeEntry(pos);
/*  852 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(K k, double oldValue, double v) {
/*  859 */     int pos = find(k);
/*  860 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  861 */       return false; 
/*  862 */     this.value[pos] = v;
/*  863 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(K k, double v) {
/*  868 */     int pos = find(k);
/*  869 */     if (pos < 0)
/*  870 */       return this.defRetValue; 
/*  871 */     double oldValue = this.value[pos];
/*  872 */     this.value[pos] = v;
/*  873 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfAbsent(K k, ToDoubleFunction<? super K> mappingFunction) {
/*  879 */     Objects.requireNonNull(mappingFunction);
/*  880 */     int pos = find(k);
/*  881 */     if (pos >= 0)
/*  882 */       return this.value[pos]; 
/*  883 */     double newValue = mappingFunction.applyAsDouble(k);
/*  884 */     insert(-pos - 1, k, newValue);
/*  885 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDoubleIfPresent(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  891 */     Objects.requireNonNull(remappingFunction);
/*  892 */     int pos = find(k);
/*  893 */     if (pos < 0)
/*  894 */       return this.defRetValue; 
/*  895 */     Double newValue = remappingFunction.apply(k, Double.valueOf(this.value[pos]));
/*  896 */     if (newValue == null) {
/*  897 */       if (k == null) {
/*  898 */         removeNullEntry();
/*      */       } else {
/*  900 */         removeEntry(pos);
/*  901 */       }  return this.defRetValue;
/*      */     } 
/*  903 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeDouble(K k, BiFunction<? super K, ? super Double, ? extends Double> remappingFunction) {
/*  909 */     Objects.requireNonNull(remappingFunction);
/*  910 */     int pos = find(k);
/*  911 */     Double newValue = remappingFunction.apply(k, (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  912 */     if (newValue == null) {
/*  913 */       if (pos >= 0)
/*  914 */         if (k == null) {
/*  915 */           removeNullEntry();
/*      */         } else {
/*  917 */           removeEntry(pos);
/*      */         }  
/*  919 */       return this.defRetValue;
/*      */     } 
/*  921 */     double newVal = newValue.doubleValue();
/*  922 */     if (pos < 0) {
/*  923 */       insert(-pos - 1, k, newVal);
/*  924 */       return newVal;
/*      */     } 
/*  926 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double mergeDouble(K k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  932 */     Objects.requireNonNull(remappingFunction);
/*  933 */     int pos = find(k);
/*  934 */     if (pos < 0) {
/*  935 */       insert(-pos - 1, k, v);
/*  936 */       return v;
/*      */     } 
/*  938 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  939 */     if (newValue == null) {
/*  940 */       if (k == null) {
/*  941 */         removeNullEntry();
/*      */       } else {
/*  943 */         removeEntry(pos);
/*  944 */       }  return this.defRetValue;
/*      */     } 
/*  946 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  957 */     if (this.size == 0)
/*      */       return; 
/*  959 */     this.size = 0;
/*  960 */     this.containsNullKey = false;
/*  961 */     Arrays.fill((Object[])this.key, (Object)null);
/*  962 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  966 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  970 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Reference2DoubleMap.Entry<K>, Map.Entry<K, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  982 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public K getKey() {
/*  988 */       return Reference2DoubleLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  992 */       return Reference2DoubleLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  996 */       double oldValue = Reference2DoubleLinkedOpenHashMap.this.value[this.index];
/*  997 */       Reference2DoubleLinkedOpenHashMap.this.value[this.index] = v;
/*  998 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/* 1008 */       return Double.valueOf(Reference2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/* 1018 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1023 */       if (!(o instanceof Map.Entry))
/* 1024 */         return false; 
/* 1025 */       Map.Entry<K, Double> e = (Map.Entry<K, Double>)o;
/* 1026 */       return (Reference2DoubleLinkedOpenHashMap.this.key[this.index] == e.getKey() && 
/* 1027 */         Double.doubleToLongBits(Reference2DoubleLinkedOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1031 */       return System.identityHashCode(Reference2DoubleLinkedOpenHashMap.this.key[this.index]) ^ HashCommon.double2int(Reference2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1035 */       return (new StringBuilder()).append(Reference2DoubleLinkedOpenHashMap.this.key[this.index]).append("=>").append(Reference2DoubleLinkedOpenHashMap.this.value[this.index]).toString();
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
/* 1046 */     if (this.size == 0) {
/* 1047 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1050 */     if (this.first == i) {
/* 1051 */       this.first = (int)this.link[i];
/* 1052 */       if (0 <= this.first)
/*      */       {
/* 1054 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1058 */     if (this.last == i) {
/* 1059 */       this.last = (int)(this.link[i] >>> 32L);
/* 1060 */       if (0 <= this.last)
/*      */       {
/* 1062 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1066 */     long linki = this.link[i];
/* 1067 */     int prev = (int)(linki >>> 32L);
/* 1068 */     int next = (int)linki;
/* 1069 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1070 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1083 */     if (this.size == 1) {
/* 1084 */       this.first = this.last = d;
/*      */       
/* 1086 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1089 */     if (this.first == s) {
/* 1090 */       this.first = d;
/* 1091 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1092 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1095 */     if (this.last == s) {
/* 1096 */       this.last = d;
/* 1097 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1098 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1101 */     long links = this.link[s];
/* 1102 */     int prev = (int)(links >>> 32L);
/* 1103 */     int next = (int)links;
/* 1104 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1105 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1106 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K firstKey() {
/* 1115 */     if (this.size == 0)
/* 1116 */       throw new NoSuchElementException(); 
/* 1117 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public K lastKey() {
/* 1126 */     if (this.size == 0)
/* 1127 */       throw new NoSuchElementException(); 
/* 1128 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleSortedMap<K> tailMap(K from) {
/* 1137 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleSortedMap<K> headMap(K to) {
/* 1146 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference2DoubleSortedMap<K> subMap(K from, K to) {
/* 1155 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Comparator<? super K> comparator() {
/* 1164 */     return null;
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
/* 1179 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1185 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1190 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1196 */     int index = -1;
/*      */     protected MapIterator() {
/* 1198 */       this.next = Reference2DoubleLinkedOpenHashMap.this.first;
/* 1199 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(K from) {
/* 1202 */       if (from == null) {
/* 1203 */         if (Reference2DoubleLinkedOpenHashMap.this.containsNullKey) {
/* 1204 */           this.next = (int)Reference2DoubleLinkedOpenHashMap.this.link[Reference2DoubleLinkedOpenHashMap.this.n];
/* 1205 */           this.prev = Reference2DoubleLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1208 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1210 */       if (Reference2DoubleLinkedOpenHashMap.this.key[Reference2DoubleLinkedOpenHashMap.this.last] == from) {
/* 1211 */         this.prev = Reference2DoubleLinkedOpenHashMap.this.last;
/* 1212 */         this.index = Reference2DoubleLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1216 */       int pos = HashCommon.mix(System.identityHashCode(from)) & Reference2DoubleLinkedOpenHashMap.this.mask;
/*      */       
/* 1218 */       while (Reference2DoubleLinkedOpenHashMap.this.key[pos] != null) {
/* 1219 */         if (Reference2DoubleLinkedOpenHashMap.this.key[pos] == from) {
/*      */           
/* 1221 */           this.next = (int)Reference2DoubleLinkedOpenHashMap.this.link[pos];
/* 1222 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1225 */         pos = pos + 1 & Reference2DoubleLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1227 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1230 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1233 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1236 */       if (this.index >= 0)
/*      */         return; 
/* 1238 */       if (this.prev == -1) {
/* 1239 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1242 */       if (this.next == -1) {
/* 1243 */         this.index = Reference2DoubleLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1246 */       int pos = Reference2DoubleLinkedOpenHashMap.this.first;
/* 1247 */       this.index = 1;
/* 1248 */       while (pos != this.prev) {
/* 1249 */         pos = (int)Reference2DoubleLinkedOpenHashMap.this.link[pos];
/* 1250 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1254 */       ensureIndexKnown();
/* 1255 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1258 */       ensureIndexKnown();
/* 1259 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1262 */       if (!hasNext())
/* 1263 */         throw new NoSuchElementException(); 
/* 1264 */       this.curr = this.next;
/* 1265 */       this.next = (int)Reference2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1266 */       this.prev = this.curr;
/* 1267 */       if (this.index >= 0)
/* 1268 */         this.index++; 
/* 1269 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1272 */       if (!hasPrevious())
/* 1273 */         throw new NoSuchElementException(); 
/* 1274 */       this.curr = this.prev;
/* 1275 */       this.prev = (int)(Reference2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1276 */       this.next = this.curr;
/* 1277 */       if (this.index >= 0)
/* 1278 */         this.index--; 
/* 1279 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1282 */       ensureIndexKnown();
/* 1283 */       if (this.curr == -1)
/* 1284 */         throw new IllegalStateException(); 
/* 1285 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1290 */         this.index--;
/* 1291 */         this.prev = (int)(Reference2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1293 */         this.next = (int)Reference2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1294 */       }  Reference2DoubleLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1299 */       if (this.prev == -1) {
/* 1300 */         Reference2DoubleLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1302 */         Reference2DoubleLinkedOpenHashMap.this.link[this.prev] = Reference2DoubleLinkedOpenHashMap.this.link[this.prev] ^ (Reference2DoubleLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1303 */       }  if (this.next == -1) {
/* 1304 */         Reference2DoubleLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1306 */         Reference2DoubleLinkedOpenHashMap.this.link[this.next] = Reference2DoubleLinkedOpenHashMap.this.link[this.next] ^ (Reference2DoubleLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1307 */       }  int pos = this.curr;
/* 1308 */       this.curr = -1;
/* 1309 */       if (pos == Reference2DoubleLinkedOpenHashMap.this.n) {
/* 1310 */         Reference2DoubleLinkedOpenHashMap.this.containsNullKey = false;
/* 1311 */         Reference2DoubleLinkedOpenHashMap.this.key[Reference2DoubleLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1314 */         K[] key = Reference2DoubleLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           K curr;
/*      */           int last;
/* 1318 */           pos = (last = pos) + 1 & Reference2DoubleLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1320 */             if ((curr = key[pos]) == null) {
/* 1321 */               key[last] = null;
/*      */               return;
/*      */             } 
/* 1324 */             int slot = HashCommon.mix(System.identityHashCode(curr)) & Reference2DoubleLinkedOpenHashMap.this.mask;
/* 1325 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1327 */             pos = pos + 1 & Reference2DoubleLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1329 */           key[last] = curr;
/* 1330 */           Reference2DoubleLinkedOpenHashMap.this.value[last] = Reference2DoubleLinkedOpenHashMap.this.value[pos];
/* 1331 */           if (this.next == pos)
/* 1332 */             this.next = last; 
/* 1333 */           if (this.prev == pos)
/* 1334 */             this.prev = last; 
/* 1335 */           Reference2DoubleLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1340 */       int i = n;
/* 1341 */       while (i-- != 0 && hasNext())
/* 1342 */         nextEntry(); 
/* 1343 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1346 */       int i = n;
/* 1347 */       while (i-- != 0 && hasPrevious())
/* 1348 */         previousEntry(); 
/* 1349 */       return n - i - 1;
/*      */     }
/*      */     public void set(Reference2DoubleMap.Entry<K> ok) {
/* 1352 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Reference2DoubleMap.Entry<K> ok) {
/* 1355 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Reference2DoubleMap.Entry<K>> { private Reference2DoubleLinkedOpenHashMap<K>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(K from) {
/* 1363 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2DoubleLinkedOpenHashMap<K>.MapEntry next() {
/* 1367 */       return this.entry = new Reference2DoubleLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Reference2DoubleLinkedOpenHashMap<K>.MapEntry previous() {
/* 1371 */       return this.entry = new Reference2DoubleLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1375 */       super.remove();
/* 1376 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1380 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Reference2DoubleMap.Entry<K>> { final Reference2DoubleLinkedOpenHashMap<K>.MapEntry entry = new Reference2DoubleLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(K from) {
/* 1384 */       super(from);
/*      */     }
/*      */     
/*      */     public Reference2DoubleLinkedOpenHashMap<K>.MapEntry next() {
/* 1388 */       this.entry.index = nextEntry();
/* 1389 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Reference2DoubleLinkedOpenHashMap<K>.MapEntry previous() {
/* 1393 */       this.entry.index = previousEntry();
/* 1394 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Reference2DoubleMap.Entry<K>> implements Reference2DoubleSortedMap.FastSortedEntrySet<K> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Reference2DoubleMap.Entry<K>> iterator() {
/* 1402 */       return new Reference2DoubleLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Reference2DoubleMap.Entry<K>> comparator() {
/* 1406 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Reference2DoubleMap.Entry<K>> subSet(Reference2DoubleMap.Entry<K> fromElement, Reference2DoubleMap.Entry<K> toElement) {
/* 1411 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2DoubleMap.Entry<K>> headSet(Reference2DoubleMap.Entry<K> toElement) {
/* 1415 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Reference2DoubleMap.Entry<K>> tailSet(Reference2DoubleMap.Entry<K> fromElement) {
/* 1419 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Reference2DoubleMap.Entry<K> first() {
/* 1423 */       if (Reference2DoubleLinkedOpenHashMap.this.size == 0)
/* 1424 */         throw new NoSuchElementException(); 
/* 1425 */       return new Reference2DoubleLinkedOpenHashMap.MapEntry(Reference2DoubleLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Reference2DoubleMap.Entry<K> last() {
/* 1429 */       if (Reference2DoubleLinkedOpenHashMap.this.size == 0)
/* 1430 */         throw new NoSuchElementException(); 
/* 1431 */       return new Reference2DoubleLinkedOpenHashMap.MapEntry(Reference2DoubleLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1436 */       if (!(o instanceof Map.Entry))
/* 1437 */         return false; 
/* 1438 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1439 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1440 */         return false; 
/* 1441 */       K k = (K)e.getKey();
/* 1442 */       double v = ((Double)e.getValue()).doubleValue();
/* 1443 */       if (k == null) {
/* 1444 */         return (Reference2DoubleLinkedOpenHashMap.this.containsNullKey && 
/* 1445 */           Double.doubleToLongBits(Reference2DoubleLinkedOpenHashMap.this.value[Reference2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/* 1447 */       K[] key = Reference2DoubleLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1450 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1452 */         return false; } 
/* 1453 */       if (k == curr) {
/* 1454 */         return (Double.doubleToLongBits(Reference2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/* 1457 */         if ((curr = key[pos = pos + 1 & Reference2DoubleLinkedOpenHashMap.this.mask]) == null)
/* 1458 */           return false; 
/* 1459 */         if (k == curr) {
/* 1460 */           return (Double.doubleToLongBits(Reference2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1466 */       if (!(o instanceof Map.Entry))
/* 1467 */         return false; 
/* 1468 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1469 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1470 */         return false; 
/* 1471 */       K k = (K)e.getKey();
/* 1472 */       double v = ((Double)e.getValue()).doubleValue();
/* 1473 */       if (k == null) {
/* 1474 */         if (Reference2DoubleLinkedOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Reference2DoubleLinkedOpenHashMap.this.value[Reference2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1475 */           Reference2DoubleLinkedOpenHashMap.this.removeNullEntry();
/* 1476 */           return true;
/*      */         } 
/* 1478 */         return false;
/*      */       } 
/*      */       
/* 1481 */       K[] key = Reference2DoubleLinkedOpenHashMap.this.key;
/*      */       K curr;
/*      */       int pos;
/* 1484 */       if ((curr = key[pos = HashCommon.mix(System.identityHashCode(k)) & Reference2DoubleLinkedOpenHashMap.this.mask]) == null)
/*      */       {
/* 1486 */         return false; } 
/* 1487 */       if (curr == k) {
/* 1488 */         if (Double.doubleToLongBits(Reference2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1489 */           Reference2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1490 */           return true;
/*      */         } 
/* 1492 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1495 */         if ((curr = key[pos = pos + 1 & Reference2DoubleLinkedOpenHashMap.this.mask]) == null)
/* 1496 */           return false; 
/* 1497 */         if (curr == k && 
/* 1498 */           Double.doubleToLongBits(Reference2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1499 */           Reference2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1500 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1507 */       return Reference2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1511 */       Reference2DoubleLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Reference2DoubleMap.Entry<K>> iterator(Reference2DoubleMap.Entry<K> from) {
/* 1526 */       return new Reference2DoubleLinkedOpenHashMap.EntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Reference2DoubleMap.Entry<K>> fastIterator() {
/* 1537 */       return new Reference2DoubleLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Reference2DoubleMap.Entry<K>> fastIterator(Reference2DoubleMap.Entry<K> from) {
/* 1552 */       return new Reference2DoubleLinkedOpenHashMap.FastEntryIterator(from.getKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/* 1557 */       for (int i = Reference2DoubleLinkedOpenHashMap.this.size, next = Reference2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1558 */         int curr = next;
/* 1559 */         next = (int)Reference2DoubleLinkedOpenHashMap.this.link[curr];
/* 1560 */         consumer.accept(new AbstractReference2DoubleMap.BasicEntry<>(Reference2DoubleLinkedOpenHashMap.this.key[curr], Reference2DoubleLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Reference2DoubleMap.Entry<K>> consumer) {
/* 1566 */       AbstractReference2DoubleMap.BasicEntry<K> entry = new AbstractReference2DoubleMap.BasicEntry<>();
/* 1567 */       for (int i = Reference2DoubleLinkedOpenHashMap.this.size, next = Reference2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1568 */         int curr = next;
/* 1569 */         next = (int)Reference2DoubleLinkedOpenHashMap.this.link[curr];
/* 1570 */         entry.key = Reference2DoubleLinkedOpenHashMap.this.key[curr];
/* 1571 */         entry.value = Reference2DoubleLinkedOpenHashMap.this.value[curr];
/* 1572 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Reference2DoubleSortedMap.FastSortedEntrySet<K> reference2DoubleEntrySet() {
/* 1578 */     if (this.entries == null)
/* 1579 */       this.entries = new MapEntrySet(); 
/* 1580 */     return this.entries;
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
/* 1593 */       super(k);
/*      */     }
/*      */     
/*      */     public K previous() {
/* 1597 */       return Reference2DoubleLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public K next() {
/* 1604 */       return Reference2DoubleLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractReferenceSortedSet<K> { private KeySet() {}
/*      */     
/*      */     public ObjectListIterator<K> iterator(K from) {
/* 1610 */       return new Reference2DoubleLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public ObjectListIterator<K> iterator() {
/* 1614 */       return new Reference2DoubleLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super K> consumer) {
/* 1619 */       if (Reference2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1620 */         consumer.accept(Reference2DoubleLinkedOpenHashMap.this.key[Reference2DoubleLinkedOpenHashMap.this.n]); 
/* 1621 */       for (int pos = Reference2DoubleLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1622 */         K k = Reference2DoubleLinkedOpenHashMap.this.key[pos];
/* 1623 */         if (k != null)
/* 1624 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1629 */       return Reference2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(Object k) {
/* 1633 */       return Reference2DoubleLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(Object k) {
/* 1637 */       int oldSize = Reference2DoubleLinkedOpenHashMap.this.size;
/* 1638 */       Reference2DoubleLinkedOpenHashMap.this.removeDouble(k);
/* 1639 */       return (Reference2DoubleLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1643 */       Reference2DoubleLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public K first() {
/* 1647 */       if (Reference2DoubleLinkedOpenHashMap.this.size == 0)
/* 1648 */         throw new NoSuchElementException(); 
/* 1649 */       return Reference2DoubleLinkedOpenHashMap.this.key[Reference2DoubleLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public K last() {
/* 1653 */       if (Reference2DoubleLinkedOpenHashMap.this.size == 0)
/* 1654 */         throw new NoSuchElementException(); 
/* 1655 */       return Reference2DoubleLinkedOpenHashMap.this.key[Reference2DoubleLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public Comparator<? super K> comparator() {
/* 1659 */       return null;
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> tailSet(K from) {
/* 1663 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> headSet(K to) {
/* 1667 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ReferenceSortedSet<K> subSet(K from, K to) {
/* 1671 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public ReferenceSortedSet<K> keySet() {
/* 1676 */     if (this.keys == null)
/* 1677 */       this.keys = new KeySet(); 
/* 1678 */     return this.keys;
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
/* 1692 */       return Reference2DoubleLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1699 */       return Reference2DoubleLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1704 */     if (this.values == null)
/* 1705 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1708 */             return (DoubleIterator)new Reference2DoubleLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1712 */             return Reference2DoubleLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1716 */             return Reference2DoubleLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1720 */             Reference2DoubleLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1725 */             if (Reference2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1726 */               consumer.accept(Reference2DoubleLinkedOpenHashMap.this.value[Reference2DoubleLinkedOpenHashMap.this.n]); 
/* 1727 */             for (int pos = Reference2DoubleLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1728 */               if (Reference2DoubleLinkedOpenHashMap.this.key[pos] != null)
/* 1729 */                 consumer.accept(Reference2DoubleLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1732 */     return this.values;
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
/* 1749 */     return trim(this.size);
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
/* 1773 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1774 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1775 */       return true; 
/*      */     try {
/* 1777 */       rehash(l);
/* 1778 */     } catch (OutOfMemoryError cantDoIt) {
/* 1779 */       return false;
/*      */     } 
/* 1781 */     return true;
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
/* 1797 */     K[] key = this.key;
/* 1798 */     double[] value = this.value;
/* 1799 */     int mask = newN - 1;
/* 1800 */     K[] newKey = (K[])new Object[newN + 1];
/* 1801 */     double[] newValue = new double[newN + 1];
/* 1802 */     int i = this.first, prev = -1, newPrev = -1;
/* 1803 */     long[] link = this.link;
/* 1804 */     long[] newLink = new long[newN + 1];
/* 1805 */     this.first = -1;
/* 1806 */     for (int j = this.size; j-- != 0; ) {
/* 1807 */       int pos; if (key[i] == null) {
/* 1808 */         pos = newN;
/*      */       } else {
/* 1810 */         pos = HashCommon.mix(System.identityHashCode(key[i])) & mask;
/* 1811 */         while (newKey[pos] != null)
/* 1812 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1814 */       newKey[pos] = key[i];
/* 1815 */       newValue[pos] = value[i];
/* 1816 */       if (prev != -1) {
/* 1817 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1818 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1819 */         newPrev = pos;
/*      */       } else {
/* 1821 */         newPrev = this.first = pos;
/*      */         
/* 1823 */         newLink[pos] = -1L;
/*      */       } 
/* 1825 */       int t = i;
/* 1826 */       i = (int)link[i];
/* 1827 */       prev = t;
/*      */     } 
/* 1829 */     this.link = newLink;
/* 1830 */     this.last = newPrev;
/* 1831 */     if (newPrev != -1)
/*      */     {
/* 1833 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1834 */     this.n = newN;
/* 1835 */     this.mask = mask;
/* 1836 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1837 */     this.key = newKey;
/* 1838 */     this.value = newValue;
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
/*      */   public Reference2DoubleLinkedOpenHashMap<K> clone() {
/*      */     Reference2DoubleLinkedOpenHashMap<K> c;
/*      */     try {
/* 1855 */       c = (Reference2DoubleLinkedOpenHashMap<K>)super.clone();
/* 1856 */     } catch (CloneNotSupportedException cantHappen) {
/* 1857 */       throw new InternalError();
/*      */     } 
/* 1859 */     c.keys = null;
/* 1860 */     c.values = null;
/* 1861 */     c.entries = null;
/* 1862 */     c.containsNullKey = this.containsNullKey;
/* 1863 */     c.key = (K[])this.key.clone();
/* 1864 */     c.value = (double[])this.value.clone();
/* 1865 */     c.link = (long[])this.link.clone();
/* 1866 */     return c;
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
/* 1879 */     int h = 0;
/* 1880 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1881 */       while (this.key[i] == null)
/* 1882 */         i++; 
/* 1883 */       if (this != this.key[i])
/* 1884 */         t = System.identityHashCode(this.key[i]); 
/* 1885 */       t ^= HashCommon.double2int(this.value[i]);
/* 1886 */       h += t;
/* 1887 */       i++;
/*      */     } 
/*      */     
/* 1890 */     if (this.containsNullKey)
/* 1891 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1892 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1895 */     K[] key = this.key;
/* 1896 */     double[] value = this.value;
/* 1897 */     MapIterator i = new MapIterator();
/* 1898 */     s.defaultWriteObject();
/* 1899 */     for (int j = this.size; j-- != 0; ) {
/* 1900 */       int e = i.nextEntry();
/* 1901 */       s.writeObject(key[e]);
/* 1902 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1907 */     s.defaultReadObject();
/* 1908 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1909 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1910 */     this.mask = this.n - 1;
/* 1911 */     K[] key = this.key = (K[])new Object[this.n + 1];
/* 1912 */     double[] value = this.value = new double[this.n + 1];
/* 1913 */     long[] link = this.link = new long[this.n + 1];
/* 1914 */     int prev = -1;
/* 1915 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1918 */     for (int i = this.size; i-- != 0; ) {
/* 1919 */       int pos; K k = (K)s.readObject();
/* 1920 */       double v = s.readDouble();
/* 1921 */       if (k == null) {
/* 1922 */         pos = this.n;
/* 1923 */         this.containsNullKey = true;
/*      */       } else {
/* 1925 */         pos = HashCommon.mix(System.identityHashCode(k)) & this.mask;
/* 1926 */         while (key[pos] != null)
/* 1927 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1929 */       key[pos] = k;
/* 1930 */       value[pos] = v;
/* 1931 */       if (this.first != -1) {
/* 1932 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1933 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1934 */         prev = pos; continue;
/*      */       } 
/* 1936 */       prev = this.first = pos;
/*      */       
/* 1938 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1941 */     this.last = prev;
/* 1942 */     if (prev != -1)
/*      */     {
/* 1944 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\objects\Reference2DoubleLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */