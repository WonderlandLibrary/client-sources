/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.ints.AbstractIntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*      */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*      */ import it.unimi.dsi.fastutil.ints.IntListIterator;
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
/*      */ 
/*      */ public class Double2IntLinkedOpenHashMap
/*      */   extends AbstractDouble2IntSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient int[] value;
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
/*      */   protected transient Double2IntSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient IntCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new double[this.n + 1];
/*  162 */     this.value = new int[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntLinkedOpenHashMap() {
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
/*      */   public Double2IntLinkedOpenHashMap(Map<? extends Double, ? extends Integer> m, float f) {
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
/*      */   public Double2IntLinkedOpenHashMap(Map<? extends Double, ? extends Integer> m) {
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
/*      */   public Double2IntLinkedOpenHashMap(Double2IntMap m, float f) {
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
/*      */   public Double2IntLinkedOpenHashMap(Double2IntMap m) {
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
/*      */   public Double2IntLinkedOpenHashMap(double[] k, int[] v, float f) {
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
/*      */   public Double2IntLinkedOpenHashMap(double[] k, int[] v) {
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
/*      */   private int removeEntry(int pos) {
/*  275 */     int oldValue = this.value[pos];
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private int removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     int oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Integer> m) {
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
/*      */   private void insert(int pos, double k, int v) {
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
/*      */   public int put(double k, int v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     int oldValue = this.value[pos];
/*  350 */     this.value[pos] = v;
/*  351 */     return oldValue;
/*      */   }
/*      */   private int addToValue(int pos, int incr) {
/*  354 */     int oldValue = this.value[pos];
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
/*      */   public int addTo(double k, int incr) {
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
/*      */   public int remove(double k) {
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
/*      */   private int setValue(int pos, int v) {
/*  467 */     int oldValue = this.value[pos];
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
/*      */   public int removeFirstInt() {
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
/*  490 */     int v = this.value[pos];
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
/*      */   public int removeLastInt() {
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
/*  517 */     int v = this.value[pos];
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
/*      */   public int getAndMoveToFirst(double k) {
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
/*      */   public int getAndMoveToLast(double k) {
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
/*      */   public int putAndMoveToFirst(double k, int v) {
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
/*      */   public int putAndMoveToLast(double k, int v) {
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
/*      */   public int get(double k) {
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
/*      */   public boolean containsValue(int v) {
/*  798 */     int[] value = this.value;
/*  799 */     double[] key = this.key;
/*  800 */     if (this.containsNullKey && value[this.n] == v)
/*  801 */       return true; 
/*  802 */     for (int i = this.n; i-- != 0;) {
/*  803 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  804 */         return true; 
/*  805 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getOrDefault(double k, int defaultValue) {
/*  811 */     if (Double.doubleToLongBits(k) == 0L) {
/*  812 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  814 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  817 */     if (Double.doubleToLongBits(
/*  818 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  820 */       return defaultValue; } 
/*  821 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  822 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  825 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  826 */         return defaultValue; 
/*  827 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  828 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public int putIfAbsent(double k, int v) {
/*  834 */     int pos = find(k);
/*  835 */     if (pos >= 0)
/*  836 */       return this.value[pos]; 
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, int v) {
/*  844 */     if (Double.doubleToLongBits(k) == 0L) {
/*  845 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  846 */         removeNullEntry();
/*  847 */         return true;
/*      */       } 
/*  849 */       return false;
/*      */     } 
/*      */     
/*  852 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  855 */     if (Double.doubleToLongBits(
/*  856 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  858 */       return false; } 
/*  859 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  860 */       removeEntry(pos);
/*  861 */       return true;
/*      */     } 
/*      */     while (true) {
/*  864 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  865 */         return false; 
/*  866 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  867 */         removeEntry(pos);
/*  868 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, int oldValue, int v) {
/*  875 */     int pos = find(k);
/*  876 */     if (pos < 0 || oldValue != this.value[pos])
/*  877 */       return false; 
/*  878 */     this.value[pos] = v;
/*  879 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int replace(double k, int v) {
/*  884 */     int pos = find(k);
/*  885 */     if (pos < 0)
/*  886 */       return this.defRetValue; 
/*  887 */     int oldValue = this.value[pos];
/*  888 */     this.value[pos] = v;
/*  889 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public int computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  894 */     Objects.requireNonNull(mappingFunction);
/*  895 */     int pos = find(k);
/*  896 */     if (pos >= 0)
/*  897 */       return this.value[pos]; 
/*  898 */     int newValue = mappingFunction.applyAsInt(k);
/*  899 */     insert(-pos - 1, k, newValue);
/*  900 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfAbsentNullable(double k, DoubleFunction<? extends Integer> mappingFunction) {
/*  906 */     Objects.requireNonNull(mappingFunction);
/*  907 */     int pos = find(k);
/*  908 */     if (pos >= 0)
/*  909 */       return this.value[pos]; 
/*  910 */     Integer newValue = mappingFunction.apply(k);
/*  911 */     if (newValue == null)
/*  912 */       return this.defRetValue; 
/*  913 */     int v = newValue.intValue();
/*  914 */     insert(-pos - 1, k, v);
/*  915 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int computeIfPresent(double k, BiFunction<? super Double, ? super Integer, ? extends Integer> remappingFunction) {
/*  921 */     Objects.requireNonNull(remappingFunction);
/*  922 */     int pos = find(k);
/*  923 */     if (pos < 0)
/*  924 */       return this.defRetValue; 
/*  925 */     Integer newValue = remappingFunction.apply(Double.valueOf(k), Integer.valueOf(this.value[pos]));
/*  926 */     if (newValue == null) {
/*  927 */       if (Double.doubleToLongBits(k) == 0L) {
/*  928 */         removeNullEntry();
/*      */       } else {
/*  930 */         removeEntry(pos);
/*  931 */       }  return this.defRetValue;
/*      */     } 
/*  933 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int compute(double k, BiFunction<? super Double, ? super Integer, ? extends Integer> remappingFunction) {
/*  939 */     Objects.requireNonNull(remappingFunction);
/*  940 */     int pos = find(k);
/*  941 */     Integer newValue = remappingFunction.apply(Double.valueOf(k), 
/*  942 */         (pos >= 0) ? Integer.valueOf(this.value[pos]) : null);
/*  943 */     if (newValue == null) {
/*  944 */       if (pos >= 0)
/*  945 */         if (Double.doubleToLongBits(k) == 0L) {
/*  946 */           removeNullEntry();
/*      */         } else {
/*  948 */           removeEntry(pos);
/*      */         }  
/*  950 */       return this.defRetValue;
/*      */     } 
/*  952 */     int newVal = newValue.intValue();
/*  953 */     if (pos < 0) {
/*  954 */       insert(-pos - 1, k, newVal);
/*  955 */       return newVal;
/*      */     } 
/*  957 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int merge(double k, int v, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
/*  963 */     Objects.requireNonNull(remappingFunction);
/*  964 */     int pos = find(k);
/*  965 */     if (pos < 0) {
/*  966 */       insert(-pos - 1, k, v);
/*  967 */       return v;
/*      */     } 
/*  969 */     Integer newValue = remappingFunction.apply(Integer.valueOf(this.value[pos]), Integer.valueOf(v));
/*  970 */     if (newValue == null) {
/*  971 */       if (Double.doubleToLongBits(k) == 0L) {
/*  972 */         removeNullEntry();
/*      */       } else {
/*  974 */         removeEntry(pos);
/*  975 */       }  return this.defRetValue;
/*      */     } 
/*  977 */     this.value[pos] = newValue.intValue(); return newValue.intValue();
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
/*  988 */     if (this.size == 0)
/*      */       return; 
/*  990 */     this.size = 0;
/*  991 */     this.containsNullKey = false;
/*  992 */     Arrays.fill(this.key, 0.0D);
/*  993 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  997 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1001 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2IntMap.Entry, Map.Entry<Double, Integer>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1013 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/* 1019 */       return Double2IntLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public int getIntValue() {
/* 1023 */       return Double2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public int setValue(int v) {
/* 1027 */       int oldValue = Double2IntLinkedOpenHashMap.this.value[this.index];
/* 1028 */       Double2IntLinkedOpenHashMap.this.value[this.index] = v;
/* 1029 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/* 1039 */       return Double.valueOf(Double2IntLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer getValue() {
/* 1049 */       return Integer.valueOf(Double2IntLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Integer setValue(Integer v) {
/* 1059 */       return Integer.valueOf(setValue(v.intValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1064 */       if (!(o instanceof Map.Entry))
/* 1065 */         return false; 
/* 1066 */       Map.Entry<Double, Integer> e = (Map.Entry<Double, Integer>)o;
/* 1067 */       return (Double.doubleToLongBits(Double2IntLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2IntLinkedOpenHashMap.this.value[this.index] == ((Integer)e
/* 1068 */         .getValue()).intValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1072 */       return HashCommon.double2int(Double2IntLinkedOpenHashMap.this.key[this.index]) ^ Double2IntLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1076 */       return Double2IntLinkedOpenHashMap.this.key[this.index] + "=>" + Double2IntLinkedOpenHashMap.this.value[this.index];
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
/* 1087 */     if (this.size == 0) {
/* 1088 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1091 */     if (this.first == i) {
/* 1092 */       this.first = (int)this.link[i];
/* 1093 */       if (0 <= this.first)
/*      */       {
/* 1095 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1099 */     if (this.last == i) {
/* 1100 */       this.last = (int)(this.link[i] >>> 32L);
/* 1101 */       if (0 <= this.last)
/*      */       {
/* 1103 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1107 */     long linki = this.link[i];
/* 1108 */     int prev = (int)(linki >>> 32L);
/* 1109 */     int next = (int)linki;
/* 1110 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1111 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1124 */     if (this.size == 1) {
/* 1125 */       this.first = this.last = d;
/*      */       
/* 1127 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1130 */     if (this.first == s) {
/* 1131 */       this.first = d;
/* 1132 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1133 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1136 */     if (this.last == s) {
/* 1137 */       this.last = d;
/* 1138 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1139 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1142 */     long links = this.link[s];
/* 1143 */     int prev = (int)(links >>> 32L);
/* 1144 */     int next = (int)links;
/* 1145 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1146 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1147 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/* 1156 */     if (this.size == 0)
/* 1157 */       throw new NoSuchElementException(); 
/* 1158 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/* 1167 */     if (this.size == 0)
/* 1168 */       throw new NoSuchElementException(); 
/* 1169 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntSortedMap tailMap(double from) {
/* 1178 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntSortedMap headMap(double to) {
/* 1187 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2IntSortedMap subMap(double from, double to) {
/* 1196 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1205 */     return null;
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
/* 1220 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1226 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1231 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1237 */     int index = -1;
/*      */     protected MapIterator() {
/* 1239 */       this.next = Double2IntLinkedOpenHashMap.this.first;
/* 1240 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1243 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1244 */         if (Double2IntLinkedOpenHashMap.this.containsNullKey) {
/* 1245 */           this.next = (int)Double2IntLinkedOpenHashMap.this.link[Double2IntLinkedOpenHashMap.this.n];
/* 1246 */           this.prev = Double2IntLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1249 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1251 */       if (Double.doubleToLongBits(Double2IntLinkedOpenHashMap.this.key[Double2IntLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1252 */         this.prev = Double2IntLinkedOpenHashMap.this.last;
/* 1253 */         this.index = Double2IntLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1257 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2IntLinkedOpenHashMap.this.mask;
/*      */       
/* 1259 */       while (Double.doubleToLongBits(Double2IntLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1260 */         if (Double.doubleToLongBits(Double2IntLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1262 */           this.next = (int)Double2IntLinkedOpenHashMap.this.link[pos];
/* 1263 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1266 */         pos = pos + 1 & Double2IntLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1268 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1271 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1274 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1277 */       if (this.index >= 0)
/*      */         return; 
/* 1279 */       if (this.prev == -1) {
/* 1280 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1283 */       if (this.next == -1) {
/* 1284 */         this.index = Double2IntLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1287 */       int pos = Double2IntLinkedOpenHashMap.this.first;
/* 1288 */       this.index = 1;
/* 1289 */       while (pos != this.prev) {
/* 1290 */         pos = (int)Double2IntLinkedOpenHashMap.this.link[pos];
/* 1291 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1295 */       ensureIndexKnown();
/* 1296 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1299 */       ensureIndexKnown();
/* 1300 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1303 */       if (!hasNext())
/* 1304 */         throw new NoSuchElementException(); 
/* 1305 */       this.curr = this.next;
/* 1306 */       this.next = (int)Double2IntLinkedOpenHashMap.this.link[this.curr];
/* 1307 */       this.prev = this.curr;
/* 1308 */       if (this.index >= 0)
/* 1309 */         this.index++; 
/* 1310 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1313 */       if (!hasPrevious())
/* 1314 */         throw new NoSuchElementException(); 
/* 1315 */       this.curr = this.prev;
/* 1316 */       this.prev = (int)(Double2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1317 */       this.next = this.curr;
/* 1318 */       if (this.index >= 0)
/* 1319 */         this.index--; 
/* 1320 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1323 */       ensureIndexKnown();
/* 1324 */       if (this.curr == -1)
/* 1325 */         throw new IllegalStateException(); 
/* 1326 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1331 */         this.index--;
/* 1332 */         this.prev = (int)(Double2IntLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1334 */         this.next = (int)Double2IntLinkedOpenHashMap.this.link[this.curr];
/* 1335 */       }  Double2IntLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       if (this.prev == -1) {
/* 1341 */         Double2IntLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1343 */         Double2IntLinkedOpenHashMap.this.link[this.prev] = Double2IntLinkedOpenHashMap.this.link[this.prev] ^ (Double2IntLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1344 */       }  if (this.next == -1) {
/* 1345 */         Double2IntLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1347 */         Double2IntLinkedOpenHashMap.this.link[this.next] = Double2IntLinkedOpenHashMap.this.link[this.next] ^ (Double2IntLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1348 */       }  int pos = this.curr;
/* 1349 */       this.curr = -1;
/* 1350 */       if (pos == Double2IntLinkedOpenHashMap.this.n) {
/* 1351 */         Double2IntLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1354 */         double[] key = Double2IntLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1358 */           pos = (last = pos) + 1 & Double2IntLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1360 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1361 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1364 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2IntLinkedOpenHashMap.this.mask;
/* 1365 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1367 */             pos = pos + 1 & Double2IntLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1369 */           key[last] = curr;
/* 1370 */           Double2IntLinkedOpenHashMap.this.value[last] = Double2IntLinkedOpenHashMap.this.value[pos];
/* 1371 */           if (this.next == pos)
/* 1372 */             this.next = last; 
/* 1373 */           if (this.prev == pos)
/* 1374 */             this.prev = last; 
/* 1375 */           Double2IntLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1380 */       int i = n;
/* 1381 */       while (i-- != 0 && hasNext())
/* 1382 */         nextEntry(); 
/* 1383 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1386 */       int i = n;
/* 1387 */       while (i-- != 0 && hasPrevious())
/* 1388 */         previousEntry(); 
/* 1389 */       return n - i - 1;
/*      */     }
/*      */     public void set(Double2IntMap.Entry ok) {
/* 1392 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2IntMap.Entry ok) {
/* 1395 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2IntMap.Entry> { private Double2IntLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1403 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2IntLinkedOpenHashMap.MapEntry next() {
/* 1407 */       return this.entry = new Double2IntLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2IntLinkedOpenHashMap.MapEntry previous() {
/* 1411 */       return this.entry = new Double2IntLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1415 */       super.remove();
/* 1416 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1420 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2IntMap.Entry> { final Double2IntLinkedOpenHashMap.MapEntry entry = new Double2IntLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1424 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2IntLinkedOpenHashMap.MapEntry next() {
/* 1428 */       this.entry.index = nextEntry();
/* 1429 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2IntLinkedOpenHashMap.MapEntry previous() {
/* 1433 */       this.entry.index = previousEntry();
/* 1434 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2IntMap.Entry> implements Double2IntSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Double2IntMap.Entry> iterator() {
/* 1440 */       return (ObjectBidirectionalIterator<Double2IntMap.Entry>)new Double2IntLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Double2IntMap.Entry> comparator() {
/* 1444 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2IntMap.Entry> subSet(Double2IntMap.Entry fromElement, Double2IntMap.Entry toElement) {
/* 1449 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2IntMap.Entry> headSet(Double2IntMap.Entry toElement) {
/* 1453 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2IntMap.Entry> tailSet(Double2IntMap.Entry fromElement) {
/* 1457 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2IntMap.Entry first() {
/* 1461 */       if (Double2IntLinkedOpenHashMap.this.size == 0)
/* 1462 */         throw new NoSuchElementException(); 
/* 1463 */       return new Double2IntLinkedOpenHashMap.MapEntry(Double2IntLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2IntMap.Entry last() {
/* 1467 */       if (Double2IntLinkedOpenHashMap.this.size == 0)
/* 1468 */         throw new NoSuchElementException(); 
/* 1469 */       return new Double2IntLinkedOpenHashMap.MapEntry(Double2IntLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1474 */       if (!(o instanceof Map.Entry))
/* 1475 */         return false; 
/* 1476 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1477 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1478 */         return false; 
/* 1479 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1480 */         return false; 
/* 1481 */       double k = ((Double)e.getKey()).doubleValue();
/* 1482 */       int v = ((Integer)e.getValue()).intValue();
/* 1483 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1484 */         return (Double2IntLinkedOpenHashMap.this.containsNullKey && Double2IntLinkedOpenHashMap.this.value[Double2IntLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1486 */       double[] key = Double2IntLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1489 */       if (Double.doubleToLongBits(
/* 1490 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2IntLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1492 */         return false; } 
/* 1493 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1494 */         return (Double2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1497 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2IntLinkedOpenHashMap.this.mask]) == 0L)
/* 1498 */           return false; 
/* 1499 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1500 */           return (Double2IntLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1506 */       if (!(o instanceof Map.Entry))
/* 1507 */         return false; 
/* 1508 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1509 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1510 */         return false; 
/* 1511 */       if (e.getValue() == null || !(e.getValue() instanceof Integer))
/* 1512 */         return false; 
/* 1513 */       double k = ((Double)e.getKey()).doubleValue();
/* 1514 */       int v = ((Integer)e.getValue()).intValue();
/* 1515 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1516 */         if (Double2IntLinkedOpenHashMap.this.containsNullKey && Double2IntLinkedOpenHashMap.this.value[Double2IntLinkedOpenHashMap.this.n] == v) {
/* 1517 */           Double2IntLinkedOpenHashMap.this.removeNullEntry();
/* 1518 */           return true;
/*      */         } 
/* 1520 */         return false;
/*      */       } 
/*      */       
/* 1523 */       double[] key = Double2IntLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1526 */       if (Double.doubleToLongBits(
/* 1527 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2IntLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1529 */         return false; } 
/* 1530 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1531 */         if (Double2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1532 */           Double2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1533 */           return true;
/*      */         } 
/* 1535 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1538 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2IntLinkedOpenHashMap.this.mask]) == 0L)
/* 1539 */           return false; 
/* 1540 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1541 */           Double2IntLinkedOpenHashMap.this.value[pos] == v) {
/* 1542 */           Double2IntLinkedOpenHashMap.this.removeEntry(pos);
/* 1543 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1550 */       return Double2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1554 */       Double2IntLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2IntMap.Entry> iterator(Double2IntMap.Entry from) {
/* 1569 */       return new Double2IntLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2IntMap.Entry> fastIterator() {
/* 1580 */       return new Double2IntLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2IntMap.Entry> fastIterator(Double2IntMap.Entry from) {
/* 1595 */       return new Double2IntLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2IntMap.Entry> consumer) {
/* 1600 */       for (int i = Double2IntLinkedOpenHashMap.this.size, next = Double2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1601 */         int curr = next;
/* 1602 */         next = (int)Double2IntLinkedOpenHashMap.this.link[curr];
/* 1603 */         consumer.accept(new AbstractDouble2IntMap.BasicEntry(Double2IntLinkedOpenHashMap.this.key[curr], Double2IntLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2IntMap.Entry> consumer) {
/* 1609 */       AbstractDouble2IntMap.BasicEntry entry = new AbstractDouble2IntMap.BasicEntry();
/* 1610 */       for (int i = Double2IntLinkedOpenHashMap.this.size, next = Double2IntLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1611 */         int curr = next;
/* 1612 */         next = (int)Double2IntLinkedOpenHashMap.this.link[curr];
/* 1613 */         entry.key = Double2IntLinkedOpenHashMap.this.key[curr];
/* 1614 */         entry.value = Double2IntLinkedOpenHashMap.this.value[curr];
/* 1615 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2IntSortedMap.FastSortedEntrySet double2IntEntrySet() {
/* 1621 */     if (this.entries == null)
/* 1622 */       this.entries = new MapEntrySet(); 
/* 1623 */     return this.entries;
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
/* 1636 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1640 */       return Double2IntLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1647 */       return Double2IntLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1653 */       return new Double2IntLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1657 */       return new Double2IntLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1662 */       if (Double2IntLinkedOpenHashMap.this.containsNullKey)
/* 1663 */         consumer.accept(Double2IntLinkedOpenHashMap.this.key[Double2IntLinkedOpenHashMap.this.n]); 
/* 1664 */       for (int pos = Double2IntLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1665 */         double k = Double2IntLinkedOpenHashMap.this.key[pos];
/* 1666 */         if (Double.doubleToLongBits(k) != 0L)
/* 1667 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1672 */       return Double2IntLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1676 */       return Double2IntLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1680 */       int oldSize = Double2IntLinkedOpenHashMap.this.size;
/* 1681 */       Double2IntLinkedOpenHashMap.this.remove(k);
/* 1682 */       return (Double2IntLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1686 */       Double2IntLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1690 */       if (Double2IntLinkedOpenHashMap.this.size == 0)
/* 1691 */         throw new NoSuchElementException(); 
/* 1692 */       return Double2IntLinkedOpenHashMap.this.key[Double2IntLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1696 */       if (Double2IntLinkedOpenHashMap.this.size == 0)
/* 1697 */         throw new NoSuchElementException(); 
/* 1698 */       return Double2IntLinkedOpenHashMap.this.key[Double2IntLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1702 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1706 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1710 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1714 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1719 */     if (this.keys == null)
/* 1720 */       this.keys = new KeySet(); 
/* 1721 */     return this.keys;
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
/*      */     implements IntListIterator
/*      */   {
/*      */     public int previousInt() {
/* 1735 */       return Double2IntLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextInt() {
/* 1742 */       return Double2IntLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public IntCollection values() {
/* 1747 */     if (this.values == null)
/* 1748 */       this.values = (IntCollection)new AbstractIntCollection()
/*      */         {
/*      */           public IntIterator iterator() {
/* 1751 */             return (IntIterator)new Double2IntLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1755 */             return Double2IntLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(int v) {
/* 1759 */             return Double2IntLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1763 */             Double2IntLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1768 */             if (Double2IntLinkedOpenHashMap.this.containsNullKey)
/* 1769 */               consumer.accept(Double2IntLinkedOpenHashMap.this.value[Double2IntLinkedOpenHashMap.this.n]); 
/* 1770 */             for (int pos = Double2IntLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1771 */               if (Double.doubleToLongBits(Double2IntLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1772 */                 consumer.accept(Double2IntLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1775 */     return this.values;
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
/* 1792 */     return trim(this.size);
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
/* 1816 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1817 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1818 */       return true; 
/*      */     try {
/* 1820 */       rehash(l);
/* 1821 */     } catch (OutOfMemoryError cantDoIt) {
/* 1822 */       return false;
/*      */     } 
/* 1824 */     return true;
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
/* 1840 */     double[] key = this.key;
/* 1841 */     int[] value = this.value;
/* 1842 */     int mask = newN - 1;
/* 1843 */     double[] newKey = new double[newN + 1];
/* 1844 */     int[] newValue = new int[newN + 1];
/* 1845 */     int i = this.first, prev = -1, newPrev = -1;
/* 1846 */     long[] link = this.link;
/* 1847 */     long[] newLink = new long[newN + 1];
/* 1848 */     this.first = -1;
/* 1849 */     for (int j = this.size; j-- != 0; ) {
/* 1850 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1851 */         pos = newN;
/*      */       } else {
/* 1853 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1854 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1855 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1857 */       newKey[pos] = key[i];
/* 1858 */       newValue[pos] = value[i];
/* 1859 */       if (prev != -1) {
/* 1860 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1861 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1862 */         newPrev = pos;
/*      */       } else {
/* 1864 */         newPrev = this.first = pos;
/*      */         
/* 1866 */         newLink[pos] = -1L;
/*      */       } 
/* 1868 */       int t = i;
/* 1869 */       i = (int)link[i];
/* 1870 */       prev = t;
/*      */     } 
/* 1872 */     this.link = newLink;
/* 1873 */     this.last = newPrev;
/* 1874 */     if (newPrev != -1)
/*      */     {
/* 1876 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1877 */     this.n = newN;
/* 1878 */     this.mask = mask;
/* 1879 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1880 */     this.key = newKey;
/* 1881 */     this.value = newValue;
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
/*      */   public Double2IntLinkedOpenHashMap clone() {
/*      */     Double2IntLinkedOpenHashMap c;
/*      */     try {
/* 1898 */       c = (Double2IntLinkedOpenHashMap)super.clone();
/* 1899 */     } catch (CloneNotSupportedException cantHappen) {
/* 1900 */       throw new InternalError();
/*      */     } 
/* 1902 */     c.keys = null;
/* 1903 */     c.values = null;
/* 1904 */     c.entries = null;
/* 1905 */     c.containsNullKey = this.containsNullKey;
/* 1906 */     c.key = (double[])this.key.clone();
/* 1907 */     c.value = (int[])this.value.clone();
/* 1908 */     c.link = (long[])this.link.clone();
/* 1909 */     return c;
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
/* 1922 */     int h = 0;
/* 1923 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1924 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1925 */         i++; 
/* 1926 */       t = HashCommon.double2int(this.key[i]);
/* 1927 */       t ^= this.value[i];
/* 1928 */       h += t;
/* 1929 */       i++;
/*      */     } 
/*      */     
/* 1932 */     if (this.containsNullKey)
/* 1933 */       h += this.value[this.n]; 
/* 1934 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1937 */     double[] key = this.key;
/* 1938 */     int[] value = this.value;
/* 1939 */     MapIterator i = new MapIterator();
/* 1940 */     s.defaultWriteObject();
/* 1941 */     for (int j = this.size; j-- != 0; ) {
/* 1942 */       int e = i.nextEntry();
/* 1943 */       s.writeDouble(key[e]);
/* 1944 */       s.writeInt(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1949 */     s.defaultReadObject();
/* 1950 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1951 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1952 */     this.mask = this.n - 1;
/* 1953 */     double[] key = this.key = new double[this.n + 1];
/* 1954 */     int[] value = this.value = new int[this.n + 1];
/* 1955 */     long[] link = this.link = new long[this.n + 1];
/* 1956 */     int prev = -1;
/* 1957 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1960 */     for (int i = this.size; i-- != 0; ) {
/* 1961 */       int pos; double k = s.readDouble();
/* 1962 */       int v = s.readInt();
/* 1963 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1964 */         pos = this.n;
/* 1965 */         this.containsNullKey = true;
/*      */       } else {
/* 1967 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1968 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1969 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1971 */       key[pos] = k;
/* 1972 */       value[pos] = v;
/* 1973 */       if (this.first != -1) {
/* 1974 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1975 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1976 */         prev = pos; continue;
/*      */       } 
/* 1978 */       prev = this.first = pos;
/*      */       
/* 1980 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1983 */     this.last = prev;
/* 1984 */     if (prev != -1)
/*      */     {
/* 1986 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2IntLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */