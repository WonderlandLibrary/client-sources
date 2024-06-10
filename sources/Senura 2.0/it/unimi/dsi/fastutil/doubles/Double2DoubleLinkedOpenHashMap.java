/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ import java.util.function.DoubleUnaryOperator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2DoubleLinkedOpenHashMap
/*      */   extends AbstractDouble2DoubleSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
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
/*      */   protected transient Double2DoubleSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new double[this.n + 1];
/*  162 */     this.value = new double[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleLinkedOpenHashMap() {
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
/*      */   public Double2DoubleLinkedOpenHashMap(Map<? extends Double, ? extends Double> m, float f) {
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
/*      */   public Double2DoubleLinkedOpenHashMap(Map<? extends Double, ? extends Double> m) {
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
/*      */   public Double2DoubleLinkedOpenHashMap(Double2DoubleMap m, float f) {
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
/*      */   public Double2DoubleLinkedOpenHashMap(Double2DoubleMap m) {
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
/*      */   public Double2DoubleLinkedOpenHashMap(double[] k, double[] v, float f) {
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
/*      */   public Double2DoubleLinkedOpenHashMap(double[] k, double[] v) {
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
/*  285 */     double oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Double> m) {
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
/*      */   private void insert(int pos, double k, double v) {
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
/*      */   public double put(double k, double v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     double oldValue = this.value[pos];
/*  350 */     this.value[pos] = v;
/*  351 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  354 */     double oldValue = this.value[pos];
/*  355 */     this.value[pos] = oldValue + incr;
/*  356 */     return oldValue;
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
/*      */   public double addTo(double k, double incr) {
/*      */     int pos;
/*  376 */     if (Double.doubleToLongBits(k) == 0L) {
/*  377 */       if (this.containsNullKey)
/*  378 */         return addToValue(this.n, incr); 
/*  379 */       pos = this.n;
/*  380 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  383 */       double[] key = this.key;
/*      */       double curr;
/*  385 */       if (Double.doubleToLongBits(
/*  386 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  388 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  389 */           return addToValue(pos, incr); 
/*  390 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  391 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  392 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  395 */     }  this.key[pos] = k;
/*  396 */     this.value[pos] = this.defRetValue + incr;
/*  397 */     if (this.size == 0) {
/*  398 */       this.first = this.last = pos;
/*      */       
/*  400 */       this.link[pos] = -1L;
/*      */     } else {
/*  402 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  403 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  404 */       this.last = pos;
/*      */     } 
/*  406 */     if (this.size++ >= this.maxFill) {
/*  407 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  410 */     return this.defRetValue;
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
/*  423 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  425 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  427 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  428 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  431 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  432 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  434 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  436 */       key[last] = curr;
/*  437 */       this.value[last] = this.value[pos];
/*  438 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public double remove(double k) {
/*  444 */     if (Double.doubleToLongBits(k) == 0L) {
/*  445 */       if (this.containsNullKey)
/*  446 */         return removeNullEntry(); 
/*  447 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  450 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  453 */     if (Double.doubleToLongBits(
/*  454 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  456 */       return this.defRetValue; } 
/*  457 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  458 */       return removeEntry(pos); 
/*      */     while (true) {
/*  460 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  461 */         return this.defRetValue; 
/*  462 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  463 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private double setValue(int pos, double v) {
/*  467 */     double oldValue = this.value[pos];
/*  468 */     this.value[pos] = v;
/*  469 */     return oldValue;
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
/*  480 */     if (this.size == 0)
/*  481 */       throw new NoSuchElementException(); 
/*  482 */     int pos = this.first;
/*      */     
/*  484 */     this.first = (int)this.link[pos];
/*  485 */     if (0 <= this.first)
/*      */     {
/*  487 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  489 */     this.size--;
/*  490 */     double v = this.value[pos];
/*  491 */     if (pos == this.n) {
/*  492 */       this.containsNullKey = false;
/*      */     } else {
/*  494 */       shiftKeys(pos);
/*  495 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  496 */       rehash(this.n / 2); 
/*  497 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double removeLastDouble() {
/*  507 */     if (this.size == 0)
/*  508 */       throw new NoSuchElementException(); 
/*  509 */     int pos = this.last;
/*      */     
/*  511 */     this.last = (int)(this.link[pos] >>> 32L);
/*  512 */     if (0 <= this.last)
/*      */     {
/*  514 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  516 */     this.size--;
/*  517 */     double v = this.value[pos];
/*  518 */     if (pos == this.n) {
/*  519 */       this.containsNullKey = false;
/*      */     } else {
/*  521 */       shiftKeys(pos);
/*  522 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  523 */       rehash(this.n / 2); 
/*  524 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  527 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  529 */     if (this.last == i) {
/*  530 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  532 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  534 */       long linki = this.link[i];
/*  535 */       int prev = (int)(linki >>> 32L);
/*  536 */       int next = (int)linki;
/*  537 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  538 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  540 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  541 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  542 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  545 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  547 */     if (this.first == i) {
/*  548 */       this.first = (int)this.link[i];
/*      */       
/*  550 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  552 */       long linki = this.link[i];
/*  553 */       int prev = (int)(linki >>> 32L);
/*  554 */       int next = (int)linki;
/*  555 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  556 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  558 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  559 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  560 */     this.last = i;
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
/*      */   public double getAndMoveToFirst(double k) {
/*  572 */     if (Double.doubleToLongBits(k) == 0L) {
/*  573 */       if (this.containsNullKey) {
/*  574 */         moveIndexToFirst(this.n);
/*  575 */         return this.value[this.n];
/*      */       } 
/*  577 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  580 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  583 */     if (Double.doubleToLongBits(
/*  584 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  586 */       return this.defRetValue; } 
/*  587 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  588 */       moveIndexToFirst(pos);
/*  589 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  593 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  594 */         return this.defRetValue; 
/*  595 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  596 */         moveIndexToFirst(pos);
/*  597 */         return this.value[pos];
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
/*      */   public double getAndMoveToLast(double k) {
/*  611 */     if (Double.doubleToLongBits(k) == 0L) {
/*  612 */       if (this.containsNullKey) {
/*  613 */         moveIndexToLast(this.n);
/*  614 */         return this.value[this.n];
/*      */       } 
/*  616 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  619 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  622 */     if (Double.doubleToLongBits(
/*  623 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  625 */       return this.defRetValue; } 
/*  626 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  627 */       moveIndexToLast(pos);
/*  628 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  632 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  633 */         return this.defRetValue; 
/*  634 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  635 */         moveIndexToLast(pos);
/*  636 */         return this.value[pos];
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
/*      */   public double putAndMoveToFirst(double k, double v) {
/*      */     int pos;
/*  653 */     if (Double.doubleToLongBits(k) == 0L) {
/*  654 */       if (this.containsNullKey) {
/*  655 */         moveIndexToFirst(this.n);
/*  656 */         return setValue(this.n, v);
/*      */       } 
/*  658 */       this.containsNullKey = true;
/*  659 */       pos = this.n;
/*      */     } else {
/*      */       
/*  662 */       double[] key = this.key;
/*      */       double curr;
/*  664 */       if (Double.doubleToLongBits(
/*  665 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  667 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  668 */           moveIndexToFirst(pos);
/*  669 */           return setValue(pos, v);
/*      */         } 
/*  671 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  672 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  673 */             moveIndexToFirst(pos);
/*  674 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  678 */     }  this.key[pos] = k;
/*  679 */     this.value[pos] = v;
/*  680 */     if (this.size == 0) {
/*  681 */       this.first = this.last = pos;
/*      */       
/*  683 */       this.link[pos] = -1L;
/*      */     } else {
/*  685 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  686 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  687 */       this.first = pos;
/*      */     } 
/*  689 */     if (this.size++ >= this.maxFill) {
/*  690 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  693 */     return this.defRetValue;
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
/*      */   public double putAndMoveToLast(double k, double v) {
/*      */     int pos;
/*  708 */     if (Double.doubleToLongBits(k) == 0L) {
/*  709 */       if (this.containsNullKey) {
/*  710 */         moveIndexToLast(this.n);
/*  711 */         return setValue(this.n, v);
/*      */       } 
/*  713 */       this.containsNullKey = true;
/*  714 */       pos = this.n;
/*      */     } else {
/*      */       
/*  717 */       double[] key = this.key;
/*      */       double curr;
/*  719 */       if (Double.doubleToLongBits(
/*  720 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  722 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  723 */           moveIndexToLast(pos);
/*  724 */           return setValue(pos, v);
/*      */         } 
/*  726 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  727 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  728 */             moveIndexToLast(pos);
/*  729 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  733 */     }  this.key[pos] = k;
/*  734 */     this.value[pos] = v;
/*  735 */     if (this.size == 0) {
/*  736 */       this.first = this.last = pos;
/*      */       
/*  738 */       this.link[pos] = -1L;
/*      */     } else {
/*  740 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  741 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  742 */       this.last = pos;
/*      */     } 
/*  744 */     if (this.size++ >= this.maxFill) {
/*  745 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  748 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(double k) {
/*  753 */     if (Double.doubleToLongBits(k) == 0L) {
/*  754 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  756 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  759 */     if (Double.doubleToLongBits(
/*  760 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  762 */       return this.defRetValue; } 
/*  763 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  764 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  767 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  768 */         return this.defRetValue; 
/*  769 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  770 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  776 */     if (Double.doubleToLongBits(k) == 0L) {
/*  777 */       return this.containsNullKey;
/*      */     }
/*  779 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  782 */     if (Double.doubleToLongBits(
/*  783 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  785 */       return false; } 
/*  786 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  787 */       return true;
/*      */     }
/*      */     while (true) {
/*  790 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  791 */         return false; 
/*  792 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  793 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  798 */     double[] value = this.value;
/*  799 */     double[] key = this.key;
/*  800 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  801 */       return true; 
/*  802 */     for (int i = this.n; i-- != 0;) {
/*  803 */       if (Double.doubleToLongBits(key[i]) != 0L && 
/*  804 */         Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  805 */         return true; 
/*  806 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(double k, double defaultValue) {
/*  812 */     if (Double.doubleToLongBits(k) == 0L) {
/*  813 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  815 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  818 */     if (Double.doubleToLongBits(
/*  819 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  821 */       return defaultValue; } 
/*  822 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  823 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  826 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  827 */         return defaultValue; 
/*  828 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  829 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(double k, double v) {
/*  835 */     int pos = find(k);
/*  836 */     if (pos >= 0)
/*  837 */       return this.value[pos]; 
/*  838 */     insert(-pos - 1, k, v);
/*  839 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, double v) {
/*  845 */     if (Double.doubleToLongBits(k) == 0L) {
/*  846 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  847 */         removeNullEntry();
/*  848 */         return true;
/*      */       } 
/*  850 */       return false;
/*      */     } 
/*      */     
/*  853 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  856 */     if (Double.doubleToLongBits(
/*  857 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  859 */       return false; } 
/*  860 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  861 */       Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  862 */       removeEntry(pos);
/*  863 */       return true;
/*      */     } 
/*      */     while (true) {
/*  866 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  867 */         return false; 
/*  868 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  869 */         Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  870 */         removeEntry(pos);
/*  871 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, double oldValue, double v) {
/*  878 */     int pos = find(k);
/*  879 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  880 */       return false; 
/*  881 */     this.value[pos] = v;
/*  882 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(double k, double v) {
/*  887 */     int pos = find(k);
/*  888 */     if (pos < 0)
/*  889 */       return this.defRetValue; 
/*  890 */     double oldValue = this.value[pos];
/*  891 */     this.value[pos] = v;
/*  892 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
/*  897 */     Objects.requireNonNull(mappingFunction);
/*  898 */     int pos = find(k);
/*  899 */     if (pos >= 0)
/*  900 */       return this.value[pos]; 
/*  901 */     double newValue = mappingFunction.applyAsDouble(k);
/*  902 */     insert(-pos - 1, k, newValue);
/*  903 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(double k, DoubleFunction<? extends Double> mappingFunction) {
/*  909 */     Objects.requireNonNull(mappingFunction);
/*  910 */     int pos = find(k);
/*  911 */     if (pos >= 0)
/*  912 */       return this.value[pos]; 
/*  913 */     Double newValue = mappingFunction.apply(k);
/*  914 */     if (newValue == null)
/*  915 */       return this.defRetValue; 
/*  916 */     double v = newValue.doubleValue();
/*  917 */     insert(-pos - 1, k, v);
/*  918 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  924 */     Objects.requireNonNull(remappingFunction);
/*  925 */     int pos = find(k);
/*  926 */     if (pos < 0)
/*  927 */       return this.defRetValue; 
/*  928 */     Double newValue = remappingFunction.apply(Double.valueOf(k), Double.valueOf(this.value[pos]));
/*  929 */     if (newValue == null) {
/*  930 */       if (Double.doubleToLongBits(k) == 0L) {
/*  931 */         removeNullEntry();
/*      */       } else {
/*  933 */         removeEntry(pos);
/*  934 */       }  return this.defRetValue;
/*      */     } 
/*  936 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  942 */     Objects.requireNonNull(remappingFunction);
/*  943 */     int pos = find(k);
/*  944 */     Double newValue = remappingFunction.apply(Double.valueOf(k), 
/*  945 */         (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  946 */     if (newValue == null) {
/*  947 */       if (pos >= 0)
/*  948 */         if (Double.doubleToLongBits(k) == 0L) {
/*  949 */           removeNullEntry();
/*      */         } else {
/*  951 */           removeEntry(pos);
/*      */         }  
/*  953 */       return this.defRetValue;
/*      */     } 
/*  955 */     double newVal = newValue.doubleValue();
/*  956 */     if (pos < 0) {
/*  957 */       insert(-pos - 1, k, newVal);
/*  958 */       return newVal;
/*      */     } 
/*  960 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(double k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  966 */     Objects.requireNonNull(remappingFunction);
/*  967 */     int pos = find(k);
/*  968 */     if (pos < 0) {
/*  969 */       insert(-pos - 1, k, v);
/*  970 */       return v;
/*      */     } 
/*  972 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  973 */     if (newValue == null) {
/*  974 */       if (Double.doubleToLongBits(k) == 0L) {
/*  975 */         removeNullEntry();
/*      */       } else {
/*  977 */         removeEntry(pos);
/*  978 */       }  return this.defRetValue;
/*      */     } 
/*  980 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  991 */     if (this.size == 0)
/*      */       return; 
/*  993 */     this.size = 0;
/*  994 */     this.containsNullKey = false;
/*  995 */     Arrays.fill(this.key, 0.0D);
/*  996 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/* 1000 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1004 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2DoubleMap.Entry, Map.Entry<Double, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1016 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/* 1022 */       return Double2DoubleLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/* 1026 */       return Double2DoubleLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/* 1030 */       double oldValue = Double2DoubleLinkedOpenHashMap.this.value[this.index];
/* 1031 */       Double2DoubleLinkedOpenHashMap.this.value[this.index] = v;
/* 1032 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/* 1042 */       return Double.valueOf(Double2DoubleLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/* 1052 */       return Double.valueOf(Double2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/* 1062 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1067 */       if (!(o instanceof Map.Entry))
/* 1068 */         return false; 
/* 1069 */       Map.Entry<Double, Double> e = (Map.Entry<Double, Double>)o;
/* 1070 */       return (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/* 1071 */         Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1075 */       return HashCommon.double2int(Double2DoubleLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1076 */         HashCommon.double2int(Double2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1080 */       return Double2DoubleLinkedOpenHashMap.this.key[this.index] + "=>" + Double2DoubleLinkedOpenHashMap.this.value[this.index];
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
/* 1091 */     if (this.size == 0) {
/* 1092 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1095 */     if (this.first == i) {
/* 1096 */       this.first = (int)this.link[i];
/* 1097 */       if (0 <= this.first)
/*      */       {
/* 1099 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1103 */     if (this.last == i) {
/* 1104 */       this.last = (int)(this.link[i] >>> 32L);
/* 1105 */       if (0 <= this.last)
/*      */       {
/* 1107 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1111 */     long linki = this.link[i];
/* 1112 */     int prev = (int)(linki >>> 32L);
/* 1113 */     int next = (int)linki;
/* 1114 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1115 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1128 */     if (this.size == 1) {
/* 1129 */       this.first = this.last = d;
/*      */       
/* 1131 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1134 */     if (this.first == s) {
/* 1135 */       this.first = d;
/* 1136 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1137 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1140 */     if (this.last == s) {
/* 1141 */       this.last = d;
/* 1142 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1143 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1146 */     long links = this.link[s];
/* 1147 */     int prev = (int)(links >>> 32L);
/* 1148 */     int next = (int)links;
/* 1149 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1150 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1151 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/* 1160 */     if (this.size == 0)
/* 1161 */       throw new NoSuchElementException(); 
/* 1162 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/* 1171 */     if (this.size == 0)
/* 1172 */       throw new NoSuchElementException(); 
/* 1173 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleSortedMap tailMap(double from) {
/* 1182 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleSortedMap headMap(double to) {
/* 1191 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2DoubleSortedMap subMap(double from, double to) {
/* 1200 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1209 */     return null;
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
/* 1224 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1230 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1235 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1241 */     int index = -1;
/*      */     protected MapIterator() {
/* 1243 */       this.next = Double2DoubleLinkedOpenHashMap.this.first;
/* 1244 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1247 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1248 */         if (Double2DoubleLinkedOpenHashMap.this.containsNullKey) {
/* 1249 */           this.next = (int)Double2DoubleLinkedOpenHashMap.this.link[Double2DoubleLinkedOpenHashMap.this.n];
/* 1250 */           this.prev = Double2DoubleLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1253 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1255 */       if (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.key[Double2DoubleLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1256 */         this.prev = Double2DoubleLinkedOpenHashMap.this.last;
/* 1257 */         this.index = Double2DoubleLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1261 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2DoubleLinkedOpenHashMap.this.mask;
/*      */       
/* 1263 */       while (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1264 */         if (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1266 */           this.next = (int)Double2DoubleLinkedOpenHashMap.this.link[pos];
/* 1267 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1270 */         pos = pos + 1 & Double2DoubleLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1272 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1275 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1278 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1281 */       if (this.index >= 0)
/*      */         return; 
/* 1283 */       if (this.prev == -1) {
/* 1284 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1287 */       if (this.next == -1) {
/* 1288 */         this.index = Double2DoubleLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1291 */       int pos = Double2DoubleLinkedOpenHashMap.this.first;
/* 1292 */       this.index = 1;
/* 1293 */       while (pos != this.prev) {
/* 1294 */         pos = (int)Double2DoubleLinkedOpenHashMap.this.link[pos];
/* 1295 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1299 */       ensureIndexKnown();
/* 1300 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1303 */       ensureIndexKnown();
/* 1304 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1307 */       if (!hasNext())
/* 1308 */         throw new NoSuchElementException(); 
/* 1309 */       this.curr = this.next;
/* 1310 */       this.next = (int)Double2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1311 */       this.prev = this.curr;
/* 1312 */       if (this.index >= 0)
/* 1313 */         this.index++; 
/* 1314 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1317 */       if (!hasPrevious())
/* 1318 */         throw new NoSuchElementException(); 
/* 1319 */       this.curr = this.prev;
/* 1320 */       this.prev = (int)(Double2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1321 */       this.next = this.curr;
/* 1322 */       if (this.index >= 0)
/* 1323 */         this.index--; 
/* 1324 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1327 */       ensureIndexKnown();
/* 1328 */       if (this.curr == -1)
/* 1329 */         throw new IllegalStateException(); 
/* 1330 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1335 */         this.index--;
/* 1336 */         this.prev = (int)(Double2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1338 */         this.next = (int)Double2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1339 */       }  Double2DoubleLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1344 */       if (this.prev == -1) {
/* 1345 */         Double2DoubleLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1347 */         Double2DoubleLinkedOpenHashMap.this.link[this.prev] = Double2DoubleLinkedOpenHashMap.this.link[this.prev] ^ (Double2DoubleLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1348 */       }  if (this.next == -1) {
/* 1349 */         Double2DoubleLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1351 */         Double2DoubleLinkedOpenHashMap.this.link[this.next] = Double2DoubleLinkedOpenHashMap.this.link[this.next] ^ (Double2DoubleLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1352 */       }  int pos = this.curr;
/* 1353 */       this.curr = -1;
/* 1354 */       if (pos == Double2DoubleLinkedOpenHashMap.this.n) {
/* 1355 */         Double2DoubleLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1358 */         double[] key = Double2DoubleLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1362 */           pos = (last = pos) + 1 & Double2DoubleLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1364 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1365 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1368 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2DoubleLinkedOpenHashMap.this.mask;
/* 1369 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1371 */             pos = pos + 1 & Double2DoubleLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1373 */           key[last] = curr;
/* 1374 */           Double2DoubleLinkedOpenHashMap.this.value[last] = Double2DoubleLinkedOpenHashMap.this.value[pos];
/* 1375 */           if (this.next == pos)
/* 1376 */             this.next = last; 
/* 1377 */           if (this.prev == pos)
/* 1378 */             this.prev = last; 
/* 1379 */           Double2DoubleLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1384 */       int i = n;
/* 1385 */       while (i-- != 0 && hasNext())
/* 1386 */         nextEntry(); 
/* 1387 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1390 */       int i = n;
/* 1391 */       while (i-- != 0 && hasPrevious())
/* 1392 */         previousEntry(); 
/* 1393 */       return n - i - 1;
/*      */     }
/*      */     public void set(Double2DoubleMap.Entry ok) {
/* 1396 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2DoubleMap.Entry ok) {
/* 1399 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2DoubleMap.Entry> { private Double2DoubleLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1407 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2DoubleLinkedOpenHashMap.MapEntry next() {
/* 1411 */       return this.entry = new Double2DoubleLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2DoubleLinkedOpenHashMap.MapEntry previous() {
/* 1415 */       return this.entry = new Double2DoubleLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1419 */       super.remove();
/* 1420 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1424 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2DoubleMap.Entry> { final Double2DoubleLinkedOpenHashMap.MapEntry entry = new Double2DoubleLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1428 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2DoubleLinkedOpenHashMap.MapEntry next() {
/* 1432 */       this.entry.index = nextEntry();
/* 1433 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2DoubleLinkedOpenHashMap.MapEntry previous() {
/* 1437 */       this.entry.index = previousEntry();
/* 1438 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2DoubleMap.Entry> implements Double2DoubleSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2DoubleMap.Entry> iterator() {
/* 1446 */       return (ObjectBidirectionalIterator<Double2DoubleMap.Entry>)new Double2DoubleLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2DoubleMap.Entry> comparator() {
/* 1450 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2DoubleMap.Entry> subSet(Double2DoubleMap.Entry fromElement, Double2DoubleMap.Entry toElement) {
/* 1455 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2DoubleMap.Entry> headSet(Double2DoubleMap.Entry toElement) {
/* 1459 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2DoubleMap.Entry> tailSet(Double2DoubleMap.Entry fromElement) {
/* 1463 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2DoubleMap.Entry first() {
/* 1467 */       if (Double2DoubleLinkedOpenHashMap.this.size == 0)
/* 1468 */         throw new NoSuchElementException(); 
/* 1469 */       return new Double2DoubleLinkedOpenHashMap.MapEntry(Double2DoubleLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2DoubleMap.Entry last() {
/* 1473 */       if (Double2DoubleLinkedOpenHashMap.this.size == 0)
/* 1474 */         throw new NoSuchElementException(); 
/* 1475 */       return new Double2DoubleLinkedOpenHashMap.MapEntry(Double2DoubleLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1480 */       if (!(o instanceof Map.Entry))
/* 1481 */         return false; 
/* 1482 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1483 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1484 */         return false; 
/* 1485 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1486 */         return false; 
/* 1487 */       double k = ((Double)e.getKey()).doubleValue();
/* 1488 */       double v = ((Double)e.getValue()).doubleValue();
/* 1489 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1490 */         return (Double2DoubleLinkedOpenHashMap.this.containsNullKey && 
/* 1491 */           Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.value[Double2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/* 1493 */       double[] key = Double2DoubleLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1496 */       if (Double.doubleToLongBits(
/* 1497 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2DoubleLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1499 */         return false; } 
/* 1500 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1501 */         return (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/* 1504 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleLinkedOpenHashMap.this.mask]) == 0L)
/* 1505 */           return false; 
/* 1506 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1507 */           return (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1513 */       if (!(o instanceof Map.Entry))
/* 1514 */         return false; 
/* 1515 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1516 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1517 */         return false; 
/* 1518 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1519 */         return false; 
/* 1520 */       double k = ((Double)e.getKey()).doubleValue();
/* 1521 */       double v = ((Double)e.getValue()).doubleValue();
/* 1522 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1523 */         if (Double2DoubleLinkedOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.value[Double2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1524 */           Double2DoubleLinkedOpenHashMap.this.removeNullEntry();
/* 1525 */           return true;
/*      */         } 
/* 1527 */         return false;
/*      */       } 
/*      */       
/* 1530 */       double[] key = Double2DoubleLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1533 */       if (Double.doubleToLongBits(
/* 1534 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2DoubleLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1536 */         return false; } 
/* 1537 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1538 */         if (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1539 */           Double2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1540 */           return true;
/*      */         } 
/* 1542 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1545 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleLinkedOpenHashMap.this.mask]) == 0L)
/* 1546 */           return false; 
/* 1547 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1548 */           Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1549 */           Double2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1550 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1557 */       return Double2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1561 */       Double2DoubleLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2DoubleMap.Entry> iterator(Double2DoubleMap.Entry from) {
/* 1576 */       return new Double2DoubleLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2DoubleMap.Entry> fastIterator() {
/* 1587 */       return new Double2DoubleLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2DoubleMap.Entry> fastIterator(Double2DoubleMap.Entry from) {
/* 1602 */       return new Double2DoubleLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/* 1607 */       for (int i = Double2DoubleLinkedOpenHashMap.this.size, next = Double2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1608 */         int curr = next;
/* 1609 */         next = (int)Double2DoubleLinkedOpenHashMap.this.link[curr];
/* 1610 */         consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleLinkedOpenHashMap.this.key[curr], Double2DoubleLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/* 1616 */       AbstractDouble2DoubleMap.BasicEntry entry = new AbstractDouble2DoubleMap.BasicEntry();
/* 1617 */       for (int i = Double2DoubleLinkedOpenHashMap.this.size, next = Double2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1618 */         int curr = next;
/* 1619 */         next = (int)Double2DoubleLinkedOpenHashMap.this.link[curr];
/* 1620 */         entry.key = Double2DoubleLinkedOpenHashMap.this.key[curr];
/* 1621 */         entry.value = Double2DoubleLinkedOpenHashMap.this.value[curr];
/* 1622 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2DoubleSortedMap.FastSortedEntrySet double2DoubleEntrySet() {
/* 1628 */     if (this.entries == null)
/* 1629 */       this.entries = new MapEntrySet(); 
/* 1630 */     return this.entries;
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
/* 1643 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1647 */       return Double2DoubleLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1654 */       return Double2DoubleLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1660 */       return new Double2DoubleLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1664 */       return new Double2DoubleLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1669 */       if (Double2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1670 */         consumer.accept(Double2DoubleLinkedOpenHashMap.this.key[Double2DoubleLinkedOpenHashMap.this.n]); 
/* 1671 */       for (int pos = Double2DoubleLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1672 */         double k = Double2DoubleLinkedOpenHashMap.this.key[pos];
/* 1673 */         if (Double.doubleToLongBits(k) != 0L)
/* 1674 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1679 */       return Double2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1683 */       return Double2DoubleLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1687 */       int oldSize = Double2DoubleLinkedOpenHashMap.this.size;
/* 1688 */       Double2DoubleLinkedOpenHashMap.this.remove(k);
/* 1689 */       return (Double2DoubleLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1693 */       Double2DoubleLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1697 */       if (Double2DoubleLinkedOpenHashMap.this.size == 0)
/* 1698 */         throw new NoSuchElementException(); 
/* 1699 */       return Double2DoubleLinkedOpenHashMap.this.key[Double2DoubleLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1703 */       if (Double2DoubleLinkedOpenHashMap.this.size == 0)
/* 1704 */         throw new NoSuchElementException(); 
/* 1705 */       return Double2DoubleLinkedOpenHashMap.this.key[Double2DoubleLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1709 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1713 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1717 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1721 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1726 */     if (this.keys == null)
/* 1727 */       this.keys = new KeySet(); 
/* 1728 */     return this.keys;
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
/* 1742 */       return Double2DoubleLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1749 */       return Double2DoubleLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1754 */     if (this.values == null)
/* 1755 */       this.values = new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1758 */             return new Double2DoubleLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1762 */             return Double2DoubleLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1766 */             return Double2DoubleLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1770 */             Double2DoubleLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1775 */             if (Double2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1776 */               consumer.accept(Double2DoubleLinkedOpenHashMap.this.value[Double2DoubleLinkedOpenHashMap.this.n]); 
/* 1777 */             for (int pos = Double2DoubleLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1778 */               if (Double.doubleToLongBits(Double2DoubleLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1779 */                 consumer.accept(Double2DoubleLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1782 */     return this.values;
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
/* 1799 */     return trim(this.size);
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
/* 1823 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1824 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1825 */       return true; 
/*      */     try {
/* 1827 */       rehash(l);
/* 1828 */     } catch (OutOfMemoryError cantDoIt) {
/* 1829 */       return false;
/*      */     } 
/* 1831 */     return true;
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
/* 1847 */     double[] key = this.key;
/* 1848 */     double[] value = this.value;
/* 1849 */     int mask = newN - 1;
/* 1850 */     double[] newKey = new double[newN + 1];
/* 1851 */     double[] newValue = new double[newN + 1];
/* 1852 */     int i = this.first, prev = -1, newPrev = -1;
/* 1853 */     long[] link = this.link;
/* 1854 */     long[] newLink = new long[newN + 1];
/* 1855 */     this.first = -1;
/* 1856 */     for (int j = this.size; j-- != 0; ) {
/* 1857 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1858 */         pos = newN;
/*      */       } else {
/* 1860 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1861 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1862 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1864 */       newKey[pos] = key[i];
/* 1865 */       newValue[pos] = value[i];
/* 1866 */       if (prev != -1) {
/* 1867 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1868 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1869 */         newPrev = pos;
/*      */       } else {
/* 1871 */         newPrev = this.first = pos;
/*      */         
/* 1873 */         newLink[pos] = -1L;
/*      */       } 
/* 1875 */       int t = i;
/* 1876 */       i = (int)link[i];
/* 1877 */       prev = t;
/*      */     } 
/* 1879 */     this.link = newLink;
/* 1880 */     this.last = newPrev;
/* 1881 */     if (newPrev != -1)
/*      */     {
/* 1883 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1884 */     this.n = newN;
/* 1885 */     this.mask = mask;
/* 1886 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1887 */     this.key = newKey;
/* 1888 */     this.value = newValue;
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
/*      */   public Double2DoubleLinkedOpenHashMap clone() {
/*      */     Double2DoubleLinkedOpenHashMap c;
/*      */     try {
/* 1905 */       c = (Double2DoubleLinkedOpenHashMap)super.clone();
/* 1906 */     } catch (CloneNotSupportedException cantHappen) {
/* 1907 */       throw new InternalError();
/*      */     } 
/* 1909 */     c.keys = null;
/* 1910 */     c.values = null;
/* 1911 */     c.entries = null;
/* 1912 */     c.containsNullKey = this.containsNullKey;
/* 1913 */     c.key = (double[])this.key.clone();
/* 1914 */     c.value = (double[])this.value.clone();
/* 1915 */     c.link = (long[])this.link.clone();
/* 1916 */     return c;
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
/* 1929 */     int h = 0;
/* 1930 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1931 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1932 */         i++; 
/* 1933 */       t = HashCommon.double2int(this.key[i]);
/* 1934 */       t ^= HashCommon.double2int(this.value[i]);
/* 1935 */       h += t;
/* 1936 */       i++;
/*      */     } 
/*      */     
/* 1939 */     if (this.containsNullKey)
/* 1940 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1941 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1944 */     double[] key = this.key;
/* 1945 */     double[] value = this.value;
/* 1946 */     MapIterator i = new MapIterator();
/* 1947 */     s.defaultWriteObject();
/* 1948 */     for (int j = this.size; j-- != 0; ) {
/* 1949 */       int e = i.nextEntry();
/* 1950 */       s.writeDouble(key[e]);
/* 1951 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1956 */     s.defaultReadObject();
/* 1957 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1958 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1959 */     this.mask = this.n - 1;
/* 1960 */     double[] key = this.key = new double[this.n + 1];
/* 1961 */     double[] value = this.value = new double[this.n + 1];
/* 1962 */     long[] link = this.link = new long[this.n + 1];
/* 1963 */     int prev = -1;
/* 1964 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1967 */     for (int i = this.size; i-- != 0; ) {
/* 1968 */       int pos; double k = s.readDouble();
/* 1969 */       double v = s.readDouble();
/* 1970 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1971 */         pos = this.n;
/* 1972 */         this.containsNullKey = true;
/*      */       } else {
/* 1974 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1975 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1976 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1978 */       key[pos] = k;
/* 1979 */       value[pos] = v;
/* 1980 */       if (this.first != -1) {
/* 1981 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1982 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1983 */         prev = pos; continue;
/*      */       } 
/* 1985 */       prev = this.first = pos;
/*      */       
/* 1987 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1990 */     this.last = prev;
/* 1991 */     if (prev != -1)
/*      */     {
/* 1993 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */