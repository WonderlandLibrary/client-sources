/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharCollection;
/*      */ import it.unimi.dsi.fastutil.chars.CharIterator;
/*      */ import it.unimi.dsi.fastutil.chars.CharListIterator;
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
/*      */ import java.util.function.DoubleFunction;
/*      */ import java.util.function.DoubleToIntFunction;
/*      */ import java.util.function.IntConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2CharLinkedOpenHashMap
/*      */   extends AbstractDouble2CharSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient char[] value;
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
/*      */   protected transient Double2CharSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new double[this.n + 1];
/*  162 */     this.value = new char[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharLinkedOpenHashMap() {
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
/*      */   public Double2CharLinkedOpenHashMap(Map<? extends Double, ? extends Character> m, float f) {
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
/*      */   public Double2CharLinkedOpenHashMap(Map<? extends Double, ? extends Character> m) {
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
/*      */   public Double2CharLinkedOpenHashMap(Double2CharMap m, float f) {
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
/*      */   public Double2CharLinkedOpenHashMap(Double2CharMap m) {
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
/*      */   public Double2CharLinkedOpenHashMap(double[] k, char[] v, float f) {
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
/*      */   public Double2CharLinkedOpenHashMap(double[] k, char[] v) {
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
/*      */   private char removeEntry(int pos) {
/*  275 */     char oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private char removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     char oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Character> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  303 */     if (Double.doubleToLongBits(k) == 0L) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  309 */     if (Double.doubleToLongBits(
/*  310 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  312 */       return -(pos + 1); } 
/*  313 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  314 */       return pos;
/*      */     }
/*      */     while (true) {
/*  317 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  318 */         return -(pos + 1); 
/*  319 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  320 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, char v) {
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
/*      */   public char put(double k, char v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     char oldValue = this.value[pos];
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
/*  364 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  366 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  368 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  369 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  372 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
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
/*      */   public char remove(double k) {
/*  385 */     if (Double.doubleToLongBits(k) == 0L) {
/*  386 */       if (this.containsNullKey)
/*  387 */         return removeNullEntry(); 
/*  388 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  391 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  394 */     if (Double.doubleToLongBits(
/*  395 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  397 */       return this.defRetValue; } 
/*  398 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  399 */       return removeEntry(pos); 
/*      */     while (true) {
/*  401 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  402 */         return this.defRetValue; 
/*  403 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  404 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private char setValue(int pos, char v) {
/*  408 */     char oldValue = this.value[pos];
/*  409 */     this.value[pos] = v;
/*  410 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeFirstChar() {
/*  421 */     if (this.size == 0)
/*  422 */       throw new NoSuchElementException(); 
/*  423 */     int pos = this.first;
/*      */     
/*  425 */     this.first = (int)this.link[pos];
/*  426 */     if (0 <= this.first)
/*      */     {
/*  428 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  430 */     this.size--;
/*  431 */     char v = this.value[pos];
/*  432 */     if (pos == this.n) {
/*  433 */       this.containsNullKey = false;
/*      */     } else {
/*  435 */       shiftKeys(pos);
/*  436 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  437 */       rehash(this.n / 2); 
/*  438 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char removeLastChar() {
/*  448 */     if (this.size == 0)
/*  449 */       throw new NoSuchElementException(); 
/*  450 */     int pos = this.last;
/*      */     
/*  452 */     this.last = (int)(this.link[pos] >>> 32L);
/*  453 */     if (0 <= this.last)
/*      */     {
/*  455 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  457 */     this.size--;
/*  458 */     char v = this.value[pos];
/*  459 */     if (pos == this.n) {
/*  460 */       this.containsNullKey = false;
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
/*      */   public char getAndMoveToFirst(double k) {
/*  513 */     if (Double.doubleToLongBits(k) == 0L) {
/*  514 */       if (this.containsNullKey) {
/*  515 */         moveIndexToFirst(this.n);
/*  516 */         return this.value[this.n];
/*      */       } 
/*  518 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  521 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  524 */     if (Double.doubleToLongBits(
/*  525 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  527 */       return this.defRetValue; } 
/*  528 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  529 */       moveIndexToFirst(pos);
/*  530 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  534 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  535 */         return this.defRetValue; 
/*  536 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  537 */         moveIndexToFirst(pos);
/*  538 */         return this.value[pos];
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
/*      */   public char getAndMoveToLast(double k) {
/*  552 */     if (Double.doubleToLongBits(k) == 0L) {
/*  553 */       if (this.containsNullKey) {
/*  554 */         moveIndexToLast(this.n);
/*  555 */         return this.value[this.n];
/*      */       } 
/*  557 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  560 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  563 */     if (Double.doubleToLongBits(
/*  564 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  566 */       return this.defRetValue; } 
/*  567 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  568 */       moveIndexToLast(pos);
/*  569 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  573 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  574 */         return this.defRetValue; 
/*  575 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  576 */         moveIndexToLast(pos);
/*  577 */         return this.value[pos];
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
/*      */   public char putAndMoveToFirst(double k, char v) {
/*      */     int pos;
/*  594 */     if (Double.doubleToLongBits(k) == 0L) {
/*  595 */       if (this.containsNullKey) {
/*  596 */         moveIndexToFirst(this.n);
/*  597 */         return setValue(this.n, v);
/*      */       } 
/*  599 */       this.containsNullKey = true;
/*  600 */       pos = this.n;
/*      */     } else {
/*      */       
/*  603 */       double[] key = this.key;
/*      */       double curr;
/*  605 */       if (Double.doubleToLongBits(
/*  606 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  608 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  609 */           moveIndexToFirst(pos);
/*  610 */           return setValue(pos, v);
/*      */         } 
/*  612 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  613 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  614 */             moveIndexToFirst(pos);
/*  615 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  619 */     }  this.key[pos] = k;
/*  620 */     this.value[pos] = v;
/*  621 */     if (this.size == 0) {
/*  622 */       this.first = this.last = pos;
/*      */       
/*  624 */       this.link[pos] = -1L;
/*      */     } else {
/*  626 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  627 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  628 */       this.first = pos;
/*      */     } 
/*  630 */     if (this.size++ >= this.maxFill) {
/*  631 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  634 */     return this.defRetValue;
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
/*      */   public char putAndMoveToLast(double k, char v) {
/*      */     int pos;
/*  649 */     if (Double.doubleToLongBits(k) == 0L) {
/*  650 */       if (this.containsNullKey) {
/*  651 */         moveIndexToLast(this.n);
/*  652 */         return setValue(this.n, v);
/*      */       } 
/*  654 */       this.containsNullKey = true;
/*  655 */       pos = this.n;
/*      */     } else {
/*      */       
/*  658 */       double[] key = this.key;
/*      */       double curr;
/*  660 */       if (Double.doubleToLongBits(
/*  661 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  663 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  664 */           moveIndexToLast(pos);
/*  665 */           return setValue(pos, v);
/*      */         } 
/*  667 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  668 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  669 */             moveIndexToLast(pos);
/*  670 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  674 */     }  this.key[pos] = k;
/*  675 */     this.value[pos] = v;
/*  676 */     if (this.size == 0) {
/*  677 */       this.first = this.last = pos;
/*      */       
/*  679 */       this.link[pos] = -1L;
/*      */     } else {
/*  681 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  682 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  683 */       this.last = pos;
/*      */     } 
/*  685 */     if (this.size++ >= this.maxFill) {
/*  686 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  689 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char get(double k) {
/*  694 */     if (Double.doubleToLongBits(k) == 0L) {
/*  695 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  697 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  700 */     if (Double.doubleToLongBits(
/*  701 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  703 */       return this.defRetValue; } 
/*  704 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  705 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  708 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  709 */         return this.defRetValue; 
/*  710 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  711 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  717 */     if (Double.doubleToLongBits(k) == 0L) {
/*  718 */       return this.containsNullKey;
/*      */     }
/*  720 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  723 */     if (Double.doubleToLongBits(
/*  724 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  726 */       return false; } 
/*  727 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  728 */       return true;
/*      */     }
/*      */     while (true) {
/*  731 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  732 */         return false; 
/*  733 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  734 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  739 */     char[] value = this.value;
/*  740 */     double[] key = this.key;
/*  741 */     if (this.containsNullKey && value[this.n] == v)
/*  742 */       return true; 
/*  743 */     for (int i = this.n; i-- != 0;) {
/*  744 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  745 */         return true; 
/*  746 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(double k, char defaultValue) {
/*  752 */     if (Double.doubleToLongBits(k) == 0L) {
/*  753 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  755 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  758 */     if (Double.doubleToLongBits(
/*  759 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  761 */       return defaultValue; } 
/*  762 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  763 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  766 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  767 */         return defaultValue; 
/*  768 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  769 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(double k, char v) {
/*  775 */     int pos = find(k);
/*  776 */     if (pos >= 0)
/*  777 */       return this.value[pos]; 
/*  778 */     insert(-pos - 1, k, v);
/*  779 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, char v) {
/*  785 */     if (Double.doubleToLongBits(k) == 0L) {
/*  786 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  787 */         removeNullEntry();
/*  788 */         return true;
/*      */       } 
/*  790 */       return false;
/*      */     } 
/*      */     
/*  793 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  796 */     if (Double.doubleToLongBits(
/*  797 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  799 */       return false; } 
/*  800 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  801 */       removeEntry(pos);
/*  802 */       return true;
/*      */     } 
/*      */     while (true) {
/*  805 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  806 */         return false; 
/*  807 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  808 */         removeEntry(pos);
/*  809 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, char oldValue, char v) {
/*  816 */     int pos = find(k);
/*  817 */     if (pos < 0 || oldValue != this.value[pos])
/*  818 */       return false; 
/*  819 */     this.value[pos] = v;
/*  820 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(double k, char v) {
/*  825 */     int pos = find(k);
/*  826 */     if (pos < 0)
/*  827 */       return this.defRetValue; 
/*  828 */     char oldValue = this.value[pos];
/*  829 */     this.value[pos] = v;
/*  830 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  835 */     Objects.requireNonNull(mappingFunction);
/*  836 */     int pos = find(k);
/*  837 */     if (pos >= 0)
/*  838 */       return this.value[pos]; 
/*  839 */     char newValue = SafeMath.safeIntToChar(mappingFunction.applyAsInt(k));
/*  840 */     insert(-pos - 1, k, newValue);
/*  841 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfAbsentNullable(double k, DoubleFunction<? extends Character> mappingFunction) {
/*  847 */     Objects.requireNonNull(mappingFunction);
/*  848 */     int pos = find(k);
/*  849 */     if (pos >= 0)
/*  850 */       return this.value[pos]; 
/*  851 */     Character newValue = mappingFunction.apply(k);
/*  852 */     if (newValue == null)
/*  853 */       return this.defRetValue; 
/*  854 */     char v = newValue.charValue();
/*  855 */     insert(-pos - 1, k, v);
/*  856 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char computeIfPresent(double k, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/*  862 */     Objects.requireNonNull(remappingFunction);
/*  863 */     int pos = find(k);
/*  864 */     if (pos < 0)
/*  865 */       return this.defRetValue; 
/*  866 */     Character newValue = remappingFunction.apply(Double.valueOf(k), Character.valueOf(this.value[pos]));
/*  867 */     if (newValue == null) {
/*  868 */       if (Double.doubleToLongBits(k) == 0L) {
/*  869 */         removeNullEntry();
/*      */       } else {
/*  871 */         removeEntry(pos);
/*  872 */       }  return this.defRetValue;
/*      */     } 
/*  874 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char compute(double k, BiFunction<? super Double, ? super Character, ? extends Character> remappingFunction) {
/*  880 */     Objects.requireNonNull(remappingFunction);
/*  881 */     int pos = find(k);
/*  882 */     Character newValue = remappingFunction.apply(Double.valueOf(k), 
/*  883 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  884 */     if (newValue == null) {
/*  885 */       if (pos >= 0)
/*  886 */         if (Double.doubleToLongBits(k) == 0L) {
/*  887 */           removeNullEntry();
/*      */         } else {
/*  889 */           removeEntry(pos);
/*      */         }  
/*  891 */       return this.defRetValue;
/*      */     } 
/*  893 */     char newVal = newValue.charValue();
/*  894 */     if (pos < 0) {
/*  895 */       insert(-pos - 1, k, newVal);
/*  896 */       return newVal;
/*      */     } 
/*  898 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char merge(double k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  904 */     Objects.requireNonNull(remappingFunction);
/*  905 */     int pos = find(k);
/*  906 */     if (pos < 0) {
/*  907 */       insert(-pos - 1, k, v);
/*  908 */       return v;
/*      */     } 
/*  910 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  911 */     if (newValue == null) {
/*  912 */       if (Double.doubleToLongBits(k) == 0L) {
/*  913 */         removeNullEntry();
/*      */       } else {
/*  915 */         removeEntry(pos);
/*  916 */       }  return this.defRetValue;
/*      */     } 
/*  918 */     this.value[pos] = newValue.charValue(); return newValue.charValue();
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
/*  929 */     if (this.size == 0)
/*      */       return; 
/*  931 */     this.size = 0;
/*  932 */     this.containsNullKey = false;
/*  933 */     Arrays.fill(this.key, 0.0D);
/*  934 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  938 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  942 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2CharMap.Entry, Map.Entry<Double, Character>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  954 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  960 */       return Double2CharLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  964 */       return Double2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  968 */       char oldValue = Double2CharLinkedOpenHashMap.this.value[this.index];
/*  969 */       Double2CharLinkedOpenHashMap.this.value[this.index] = v;
/*  970 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  980 */       return Double.valueOf(Double2CharLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  990 */       return Character.valueOf(Double2CharLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character setValue(Character v) {
/* 1000 */       return Character.valueOf(setValue(v.charValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1005 */       if (!(o instanceof Map.Entry))
/* 1006 */         return false; 
/* 1007 */       Map.Entry<Double, Character> e = (Map.Entry<Double, Character>)o;
/* 1008 */       return (Double.doubleToLongBits(Double2CharLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2CharLinkedOpenHashMap.this.value[this.index] == ((Character)e
/* 1009 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1013 */       return HashCommon.double2int(Double2CharLinkedOpenHashMap.this.key[this.index]) ^ Double2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1017 */       return Double2CharLinkedOpenHashMap.this.key[this.index] + "=>" + Double2CharLinkedOpenHashMap.this.value[this.index];
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
/* 1028 */     if (this.size == 0) {
/* 1029 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1032 */     if (this.first == i) {
/* 1033 */       this.first = (int)this.link[i];
/* 1034 */       if (0 <= this.first)
/*      */       {
/* 1036 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1040 */     if (this.last == i) {
/* 1041 */       this.last = (int)(this.link[i] >>> 32L);
/* 1042 */       if (0 <= this.last)
/*      */       {
/* 1044 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1048 */     long linki = this.link[i];
/* 1049 */     int prev = (int)(linki >>> 32L);
/* 1050 */     int next = (int)linki;
/* 1051 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1052 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1065 */     if (this.size == 1) {
/* 1066 */       this.first = this.last = d;
/*      */       
/* 1068 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1071 */     if (this.first == s) {
/* 1072 */       this.first = d;
/* 1073 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1074 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1077 */     if (this.last == s) {
/* 1078 */       this.last = d;
/* 1079 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1080 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1083 */     long links = this.link[s];
/* 1084 */     int prev = (int)(links >>> 32L);
/* 1085 */     int next = (int)links;
/* 1086 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1087 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1088 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/* 1097 */     if (this.size == 0)
/* 1098 */       throw new NoSuchElementException(); 
/* 1099 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/* 1108 */     if (this.size == 0)
/* 1109 */       throw new NoSuchElementException(); 
/* 1110 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharSortedMap tailMap(double from) {
/* 1119 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharSortedMap headMap(double to) {
/* 1128 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2CharSortedMap subMap(double from, double to) {
/* 1137 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1146 */     return null;
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
/* 1161 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1167 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1172 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1178 */     int index = -1;
/*      */     protected MapIterator() {
/* 1180 */       this.next = Double2CharLinkedOpenHashMap.this.first;
/* 1181 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1184 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1185 */         if (Double2CharLinkedOpenHashMap.this.containsNullKey) {
/* 1186 */           this.next = (int)Double2CharLinkedOpenHashMap.this.link[Double2CharLinkedOpenHashMap.this.n];
/* 1187 */           this.prev = Double2CharLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1190 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1192 */       if (Double.doubleToLongBits(Double2CharLinkedOpenHashMap.this.key[Double2CharLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1193 */         this.prev = Double2CharLinkedOpenHashMap.this.last;
/* 1194 */         this.index = Double2CharLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1198 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2CharLinkedOpenHashMap.this.mask;
/*      */       
/* 1200 */       while (Double.doubleToLongBits(Double2CharLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1201 */         if (Double.doubleToLongBits(Double2CharLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1203 */           this.next = (int)Double2CharLinkedOpenHashMap.this.link[pos];
/* 1204 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1207 */         pos = pos + 1 & Double2CharLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1209 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1212 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1215 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1218 */       if (this.index >= 0)
/*      */         return; 
/* 1220 */       if (this.prev == -1) {
/* 1221 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1224 */       if (this.next == -1) {
/* 1225 */         this.index = Double2CharLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1228 */       int pos = Double2CharLinkedOpenHashMap.this.first;
/* 1229 */       this.index = 1;
/* 1230 */       while (pos != this.prev) {
/* 1231 */         pos = (int)Double2CharLinkedOpenHashMap.this.link[pos];
/* 1232 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1236 */       ensureIndexKnown();
/* 1237 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1240 */       ensureIndexKnown();
/* 1241 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1244 */       if (!hasNext())
/* 1245 */         throw new NoSuchElementException(); 
/* 1246 */       this.curr = this.next;
/* 1247 */       this.next = (int)Double2CharLinkedOpenHashMap.this.link[this.curr];
/* 1248 */       this.prev = this.curr;
/* 1249 */       if (this.index >= 0)
/* 1250 */         this.index++; 
/* 1251 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1254 */       if (!hasPrevious())
/* 1255 */         throw new NoSuchElementException(); 
/* 1256 */       this.curr = this.prev;
/* 1257 */       this.prev = (int)(Double2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1258 */       this.next = this.curr;
/* 1259 */       if (this.index >= 0)
/* 1260 */         this.index--; 
/* 1261 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1264 */       ensureIndexKnown();
/* 1265 */       if (this.curr == -1)
/* 1266 */         throw new IllegalStateException(); 
/* 1267 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1272 */         this.index--;
/* 1273 */         this.prev = (int)(Double2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1275 */         this.next = (int)Double2CharLinkedOpenHashMap.this.link[this.curr];
/* 1276 */       }  Double2CharLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1281 */       if (this.prev == -1) {
/* 1282 */         Double2CharLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1284 */         Double2CharLinkedOpenHashMap.this.link[this.prev] = Double2CharLinkedOpenHashMap.this.link[this.prev] ^ (Double2CharLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1285 */       }  if (this.next == -1) {
/* 1286 */         Double2CharLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1288 */         Double2CharLinkedOpenHashMap.this.link[this.next] = Double2CharLinkedOpenHashMap.this.link[this.next] ^ (Double2CharLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1289 */       }  int pos = this.curr;
/* 1290 */       this.curr = -1;
/* 1291 */       if (pos == Double2CharLinkedOpenHashMap.this.n) {
/* 1292 */         Double2CharLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1295 */         double[] key = Double2CharLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1299 */           pos = (last = pos) + 1 & Double2CharLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1301 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1302 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1305 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2CharLinkedOpenHashMap.this.mask;
/* 1306 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1308 */             pos = pos + 1 & Double2CharLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1310 */           key[last] = curr;
/* 1311 */           Double2CharLinkedOpenHashMap.this.value[last] = Double2CharLinkedOpenHashMap.this.value[pos];
/* 1312 */           if (this.next == pos)
/* 1313 */             this.next = last; 
/* 1314 */           if (this.prev == pos)
/* 1315 */             this.prev = last; 
/* 1316 */           Double2CharLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1321 */       int i = n;
/* 1322 */       while (i-- != 0 && hasNext())
/* 1323 */         nextEntry(); 
/* 1324 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1327 */       int i = n;
/* 1328 */       while (i-- != 0 && hasPrevious())
/* 1329 */         previousEntry(); 
/* 1330 */       return n - i - 1;
/*      */     }
/*      */     public void set(Double2CharMap.Entry ok) {
/* 1333 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2CharMap.Entry ok) {
/* 1336 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2CharMap.Entry> { private Double2CharLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1344 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2CharLinkedOpenHashMap.MapEntry next() {
/* 1348 */       return this.entry = new Double2CharLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2CharLinkedOpenHashMap.MapEntry previous() {
/* 1352 */       return this.entry = new Double2CharLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1356 */       super.remove();
/* 1357 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1361 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2CharMap.Entry> { final Double2CharLinkedOpenHashMap.MapEntry entry = new Double2CharLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1365 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2CharLinkedOpenHashMap.MapEntry next() {
/* 1369 */       this.entry.index = nextEntry();
/* 1370 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2CharLinkedOpenHashMap.MapEntry previous() {
/* 1374 */       this.entry.index = previousEntry();
/* 1375 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2CharMap.Entry> implements Double2CharSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2CharMap.Entry> iterator() {
/* 1383 */       return (ObjectBidirectionalIterator<Double2CharMap.Entry>)new Double2CharLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2CharMap.Entry> comparator() {
/* 1387 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2CharMap.Entry> subSet(Double2CharMap.Entry fromElement, Double2CharMap.Entry toElement) {
/* 1392 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2CharMap.Entry> headSet(Double2CharMap.Entry toElement) {
/* 1396 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2CharMap.Entry> tailSet(Double2CharMap.Entry fromElement) {
/* 1400 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2CharMap.Entry first() {
/* 1404 */       if (Double2CharLinkedOpenHashMap.this.size == 0)
/* 1405 */         throw new NoSuchElementException(); 
/* 1406 */       return new Double2CharLinkedOpenHashMap.MapEntry(Double2CharLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2CharMap.Entry last() {
/* 1410 */       if (Double2CharLinkedOpenHashMap.this.size == 0)
/* 1411 */         throw new NoSuchElementException(); 
/* 1412 */       return new Double2CharLinkedOpenHashMap.MapEntry(Double2CharLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1417 */       if (!(o instanceof Map.Entry))
/* 1418 */         return false; 
/* 1419 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1420 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1421 */         return false; 
/* 1422 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1423 */         return false; 
/* 1424 */       double k = ((Double)e.getKey()).doubleValue();
/* 1425 */       char v = ((Character)e.getValue()).charValue();
/* 1426 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1427 */         return (Double2CharLinkedOpenHashMap.this.containsNullKey && Double2CharLinkedOpenHashMap.this.value[Double2CharLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1429 */       double[] key = Double2CharLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1432 */       if (Double.doubleToLongBits(
/* 1433 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2CharLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1435 */         return false; } 
/* 1436 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1437 */         return (Double2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1440 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2CharLinkedOpenHashMap.this.mask]) == 0L)
/* 1441 */           return false; 
/* 1442 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1443 */           return (Double2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1449 */       if (!(o instanceof Map.Entry))
/* 1450 */         return false; 
/* 1451 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1452 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1453 */         return false; 
/* 1454 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1455 */         return false; 
/* 1456 */       double k = ((Double)e.getKey()).doubleValue();
/* 1457 */       char v = ((Character)e.getValue()).charValue();
/* 1458 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1459 */         if (Double2CharLinkedOpenHashMap.this.containsNullKey && Double2CharLinkedOpenHashMap.this.value[Double2CharLinkedOpenHashMap.this.n] == v) {
/* 1460 */           Double2CharLinkedOpenHashMap.this.removeNullEntry();
/* 1461 */           return true;
/*      */         } 
/* 1463 */         return false;
/*      */       } 
/*      */       
/* 1466 */       double[] key = Double2CharLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1469 */       if (Double.doubleToLongBits(
/* 1470 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2CharLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1472 */         return false; } 
/* 1473 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1474 */         if (Double2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1475 */           Double2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1476 */           return true;
/*      */         } 
/* 1478 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1481 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2CharLinkedOpenHashMap.this.mask]) == 0L)
/* 1482 */           return false; 
/* 1483 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1484 */           Double2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1485 */           Double2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1486 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1493 */       return Double2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1497 */       Double2CharLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2CharMap.Entry> iterator(Double2CharMap.Entry from) {
/* 1512 */       return new Double2CharLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2CharMap.Entry> fastIterator() {
/* 1523 */       return new Double2CharLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2CharMap.Entry> fastIterator(Double2CharMap.Entry from) {
/* 1538 */       return new Double2CharLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2CharMap.Entry> consumer) {
/* 1543 */       for (int i = Double2CharLinkedOpenHashMap.this.size, next = Double2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1544 */         int curr = next;
/* 1545 */         next = (int)Double2CharLinkedOpenHashMap.this.link[curr];
/* 1546 */         consumer.accept(new AbstractDouble2CharMap.BasicEntry(Double2CharLinkedOpenHashMap.this.key[curr], Double2CharLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2CharMap.Entry> consumer) {
/* 1552 */       AbstractDouble2CharMap.BasicEntry entry = new AbstractDouble2CharMap.BasicEntry();
/* 1553 */       for (int i = Double2CharLinkedOpenHashMap.this.size, next = Double2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1554 */         int curr = next;
/* 1555 */         next = (int)Double2CharLinkedOpenHashMap.this.link[curr];
/* 1556 */         entry.key = Double2CharLinkedOpenHashMap.this.key[curr];
/* 1557 */         entry.value = Double2CharLinkedOpenHashMap.this.value[curr];
/* 1558 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2CharSortedMap.FastSortedEntrySet double2CharEntrySet() {
/* 1564 */     if (this.entries == null)
/* 1565 */       this.entries = new MapEntrySet(); 
/* 1566 */     return this.entries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public KeyIterator(double k) {
/* 1579 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1583 */       return Double2CharLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1590 */       return Double2CharLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1596 */       return new Double2CharLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1600 */       return new Double2CharLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1605 */       if (Double2CharLinkedOpenHashMap.this.containsNullKey)
/* 1606 */         consumer.accept(Double2CharLinkedOpenHashMap.this.key[Double2CharLinkedOpenHashMap.this.n]); 
/* 1607 */       for (int pos = Double2CharLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1608 */         double k = Double2CharLinkedOpenHashMap.this.key[pos];
/* 1609 */         if (Double.doubleToLongBits(k) != 0L)
/* 1610 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1615 */       return Double2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1619 */       return Double2CharLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1623 */       int oldSize = Double2CharLinkedOpenHashMap.this.size;
/* 1624 */       Double2CharLinkedOpenHashMap.this.remove(k);
/* 1625 */       return (Double2CharLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1629 */       Double2CharLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1633 */       if (Double2CharLinkedOpenHashMap.this.size == 0)
/* 1634 */         throw new NoSuchElementException(); 
/* 1635 */       return Double2CharLinkedOpenHashMap.this.key[Double2CharLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1639 */       if (Double2CharLinkedOpenHashMap.this.size == 0)
/* 1640 */         throw new NoSuchElementException(); 
/* 1641 */       return Double2CharLinkedOpenHashMap.this.key[Double2CharLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1645 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1649 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1653 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1657 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1662 */     if (this.keys == null)
/* 1663 */       this.keys = new KeySet(); 
/* 1664 */     return this.keys;
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
/*      */     implements CharListIterator
/*      */   {
/*      */     public char previousChar() {
/* 1678 */       return Double2CharLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1685 */       return Double2CharLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1690 */     if (this.values == null)
/* 1691 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1694 */             return (CharIterator)new Double2CharLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1698 */             return Double2CharLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1702 */             return Double2CharLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1706 */             Double2CharLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(IntConsumer consumer) {
/* 1711 */             if (Double2CharLinkedOpenHashMap.this.containsNullKey)
/* 1712 */               consumer.accept(Double2CharLinkedOpenHashMap.this.value[Double2CharLinkedOpenHashMap.this.n]); 
/* 1713 */             for (int pos = Double2CharLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1714 */               if (Double.doubleToLongBits(Double2CharLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1715 */                 consumer.accept(Double2CharLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1718 */     return this.values;
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
/* 1735 */     return trim(this.size);
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
/* 1759 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1760 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1761 */       return true; 
/*      */     try {
/* 1763 */       rehash(l);
/* 1764 */     } catch (OutOfMemoryError cantDoIt) {
/* 1765 */       return false;
/*      */     } 
/* 1767 */     return true;
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
/* 1783 */     double[] key = this.key;
/* 1784 */     char[] value = this.value;
/* 1785 */     int mask = newN - 1;
/* 1786 */     double[] newKey = new double[newN + 1];
/* 1787 */     char[] newValue = new char[newN + 1];
/* 1788 */     int i = this.first, prev = -1, newPrev = -1;
/* 1789 */     long[] link = this.link;
/* 1790 */     long[] newLink = new long[newN + 1];
/* 1791 */     this.first = -1;
/* 1792 */     for (int j = this.size; j-- != 0; ) {
/* 1793 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1794 */         pos = newN;
/*      */       } else {
/* 1796 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1797 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1798 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1800 */       newKey[pos] = key[i];
/* 1801 */       newValue[pos] = value[i];
/* 1802 */       if (prev != -1) {
/* 1803 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1804 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1805 */         newPrev = pos;
/*      */       } else {
/* 1807 */         newPrev = this.first = pos;
/*      */         
/* 1809 */         newLink[pos] = -1L;
/*      */       } 
/* 1811 */       int t = i;
/* 1812 */       i = (int)link[i];
/* 1813 */       prev = t;
/*      */     } 
/* 1815 */     this.link = newLink;
/* 1816 */     this.last = newPrev;
/* 1817 */     if (newPrev != -1)
/*      */     {
/* 1819 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1820 */     this.n = newN;
/* 1821 */     this.mask = mask;
/* 1822 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1823 */     this.key = newKey;
/* 1824 */     this.value = newValue;
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
/*      */   public Double2CharLinkedOpenHashMap clone() {
/*      */     Double2CharLinkedOpenHashMap c;
/*      */     try {
/* 1841 */       c = (Double2CharLinkedOpenHashMap)super.clone();
/* 1842 */     } catch (CloneNotSupportedException cantHappen) {
/* 1843 */       throw new InternalError();
/*      */     } 
/* 1845 */     c.keys = null;
/* 1846 */     c.values = null;
/* 1847 */     c.entries = null;
/* 1848 */     c.containsNullKey = this.containsNullKey;
/* 1849 */     c.key = (double[])this.key.clone();
/* 1850 */     c.value = (char[])this.value.clone();
/* 1851 */     c.link = (long[])this.link.clone();
/* 1852 */     return c;
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
/* 1865 */     int h = 0;
/* 1866 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1867 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1868 */         i++; 
/* 1869 */       t = HashCommon.double2int(this.key[i]);
/* 1870 */       t ^= this.value[i];
/* 1871 */       h += t;
/* 1872 */       i++;
/*      */     } 
/*      */     
/* 1875 */     if (this.containsNullKey)
/* 1876 */       h += this.value[this.n]; 
/* 1877 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1880 */     double[] key = this.key;
/* 1881 */     char[] value = this.value;
/* 1882 */     MapIterator i = new MapIterator();
/* 1883 */     s.defaultWriteObject();
/* 1884 */     for (int j = this.size; j-- != 0; ) {
/* 1885 */       int e = i.nextEntry();
/* 1886 */       s.writeDouble(key[e]);
/* 1887 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1892 */     s.defaultReadObject();
/* 1893 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1894 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1895 */     this.mask = this.n - 1;
/* 1896 */     double[] key = this.key = new double[this.n + 1];
/* 1897 */     char[] value = this.value = new char[this.n + 1];
/* 1898 */     long[] link = this.link = new long[this.n + 1];
/* 1899 */     int prev = -1;
/* 1900 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1903 */     for (int i = this.size; i-- != 0; ) {
/* 1904 */       int pos; double k = s.readDouble();
/* 1905 */       char v = s.readChar();
/* 1906 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1907 */         pos = this.n;
/* 1908 */         this.containsNullKey = true;
/*      */       } else {
/* 1910 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1911 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1912 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1914 */       key[pos] = k;
/* 1915 */       value[pos] = v;
/* 1916 */       if (this.first != -1) {
/* 1917 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1918 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1919 */         prev = pos; continue;
/*      */       } 
/* 1921 */       prev = this.first = pos;
/*      */       
/* 1923 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1926 */     this.last = prev;
/* 1927 */     if (prev != -1)
/*      */     {
/* 1929 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2CharLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */