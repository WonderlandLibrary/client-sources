/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongListIterator;
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
/*      */ import java.util.function.DoubleToLongFunction;
/*      */ import java.util.function.LongConsumer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2LongLinkedOpenHashMap
/*      */   extends AbstractDouble2LongSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
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
/*      */   protected transient Double2LongSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2LongLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new double[this.n + 1];
/*  162 */     this.value = new long[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2LongLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2LongLinkedOpenHashMap() {
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
/*      */   public Double2LongLinkedOpenHashMap(Map<? extends Double, ? extends Long> m, float f) {
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
/*      */   public Double2LongLinkedOpenHashMap(Map<? extends Double, ? extends Long> m) {
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
/*      */   public Double2LongLinkedOpenHashMap(Double2LongMap m, float f) {
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
/*      */   public Double2LongLinkedOpenHashMap(Double2LongMap m) {
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
/*      */   public Double2LongLinkedOpenHashMap(double[] k, long[] v, float f) {
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
/*      */   public Double2LongLinkedOpenHashMap(double[] k, long[] v) {
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
/*  285 */     long oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Long> m) {
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
/*      */   private void insert(int pos, double k, long v) {
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
/*      */   public long put(double k, long v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     long oldValue = this.value[pos];
/*  350 */     this.value[pos] = v;
/*  351 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  354 */     long oldValue = this.value[pos];
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
/*      */   public long addTo(double k, long incr) {
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
/*      */   public long remove(double k) {
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
/*      */   private long setValue(int pos, long v) {
/*  467 */     long oldValue = this.value[pos];
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
/*      */   public long removeFirstLong() {
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
/*  490 */     long v = this.value[pos];
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
/*      */   public long removeLastLong() {
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
/*  517 */     long v = this.value[pos];
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
/*      */   public long getAndMoveToFirst(double k) {
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
/*      */   public long getAndMoveToLast(double k) {
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
/*      */   public long putAndMoveToFirst(double k, long v) {
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
/*      */   public long putAndMoveToLast(double k, long v) {
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
/*      */   public long get(double k) {
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
/*      */   public boolean containsValue(long v) {
/*  798 */     long[] value = this.value;
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
/*      */   public long getOrDefault(double k, long defaultValue) {
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
/*      */   public long putIfAbsent(double k, long v) {
/*  834 */     int pos = find(k);
/*  835 */     if (pos >= 0)
/*  836 */       return this.value[pos]; 
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, long v) {
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
/*      */   public boolean replace(double k, long oldValue, long v) {
/*  875 */     int pos = find(k);
/*  876 */     if (pos < 0 || oldValue != this.value[pos])
/*  877 */       return false; 
/*  878 */     this.value[pos] = v;
/*  879 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(double k, long v) {
/*  884 */     int pos = find(k);
/*  885 */     if (pos < 0)
/*  886 */       return this.defRetValue; 
/*  887 */     long oldValue = this.value[pos];
/*  888 */     this.value[pos] = v;
/*  889 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(double k, DoubleToLongFunction mappingFunction) {
/*  894 */     Objects.requireNonNull(mappingFunction);
/*  895 */     int pos = find(k);
/*  896 */     if (pos >= 0)
/*  897 */       return this.value[pos]; 
/*  898 */     long newValue = mappingFunction.applyAsLong(k);
/*  899 */     insert(-pos - 1, k, newValue);
/*  900 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(double k, DoubleFunction<? extends Long> mappingFunction) {
/*  906 */     Objects.requireNonNull(mappingFunction);
/*  907 */     int pos = find(k);
/*  908 */     if (pos >= 0)
/*  909 */       return this.value[pos]; 
/*  910 */     Long newValue = mappingFunction.apply(k);
/*  911 */     if (newValue == null)
/*  912 */       return this.defRetValue; 
/*  913 */     long v = newValue.longValue();
/*  914 */     insert(-pos - 1, k, v);
/*  915 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(double k, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
/*  921 */     Objects.requireNonNull(remappingFunction);
/*  922 */     int pos = find(k);
/*  923 */     if (pos < 0)
/*  924 */       return this.defRetValue; 
/*  925 */     Long newValue = remappingFunction.apply(Double.valueOf(k), Long.valueOf(this.value[pos]));
/*  926 */     if (newValue == null) {
/*  927 */       if (Double.doubleToLongBits(k) == 0L) {
/*  928 */         removeNullEntry();
/*      */       } else {
/*  930 */         removeEntry(pos);
/*  931 */       }  return this.defRetValue;
/*      */     } 
/*  933 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(double k, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
/*  939 */     Objects.requireNonNull(remappingFunction);
/*  940 */     int pos = find(k);
/*  941 */     Long newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  942 */     if (newValue == null) {
/*  943 */       if (pos >= 0)
/*  944 */         if (Double.doubleToLongBits(k) == 0L) {
/*  945 */           removeNullEntry();
/*      */         } else {
/*  947 */           removeEntry(pos);
/*      */         }  
/*  949 */       return this.defRetValue;
/*      */     } 
/*  951 */     long newVal = newValue.longValue();
/*  952 */     if (pos < 0) {
/*  953 */       insert(-pos - 1, k, newVal);
/*  954 */       return newVal;
/*      */     } 
/*  956 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(double k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  962 */     Objects.requireNonNull(remappingFunction);
/*  963 */     int pos = find(k);
/*  964 */     if (pos < 0) {
/*  965 */       insert(-pos - 1, k, v);
/*  966 */       return v;
/*      */     } 
/*  968 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  969 */     if (newValue == null) {
/*  970 */       if (Double.doubleToLongBits(k) == 0L) {
/*  971 */         removeNullEntry();
/*      */       } else {
/*  973 */         removeEntry(pos);
/*  974 */       }  return this.defRetValue;
/*      */     } 
/*  976 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  987 */     if (this.size == 0)
/*      */       return; 
/*  989 */     this.size = 0;
/*  990 */     this.containsNullKey = false;
/*  991 */     Arrays.fill(this.key, 0.0D);
/*  992 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  996 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1000 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2LongMap.Entry, Map.Entry<Double, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1012 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/* 1018 */       return Double2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/* 1022 */       return Double2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/* 1026 */       long oldValue = Double2LongLinkedOpenHashMap.this.value[this.index];
/* 1027 */       Double2LongLinkedOpenHashMap.this.value[this.index] = v;
/* 1028 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/* 1038 */       return Double.valueOf(Double2LongLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/* 1048 */       return Long.valueOf(Double2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/* 1058 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1063 */       if (!(o instanceof Map.Entry))
/* 1064 */         return false; 
/* 1065 */       Map.Entry<Double, Long> e = (Map.Entry<Double, Long>)o;
/* 1066 */       return (Double.doubleToLongBits(Double2LongLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2LongLinkedOpenHashMap.this.value[this.index] == ((Long)e
/* 1067 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1071 */       return HashCommon.double2int(Double2LongLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1072 */         HashCommon.long2int(Double2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1076 */       return Double2LongLinkedOpenHashMap.this.key[this.index] + "=>" + Double2LongLinkedOpenHashMap.this.value[this.index];
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
/*      */   public Double2LongSortedMap tailMap(double from) {
/* 1178 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2LongSortedMap headMap(double to) {
/* 1187 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2LongSortedMap subMap(double from, double to) {
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
/* 1239 */       this.next = Double2LongLinkedOpenHashMap.this.first;
/* 1240 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1243 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1244 */         if (Double2LongLinkedOpenHashMap.this.containsNullKey) {
/* 1245 */           this.next = (int)Double2LongLinkedOpenHashMap.this.link[Double2LongLinkedOpenHashMap.this.n];
/* 1246 */           this.prev = Double2LongLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1249 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1251 */       if (Double.doubleToLongBits(Double2LongLinkedOpenHashMap.this.key[Double2LongLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1252 */         this.prev = Double2LongLinkedOpenHashMap.this.last;
/* 1253 */         this.index = Double2LongLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1257 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2LongLinkedOpenHashMap.this.mask;
/*      */       
/* 1259 */       while (Double.doubleToLongBits(Double2LongLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1260 */         if (Double.doubleToLongBits(Double2LongLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1262 */           this.next = (int)Double2LongLinkedOpenHashMap.this.link[pos];
/* 1263 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1266 */         pos = pos + 1 & Double2LongLinkedOpenHashMap.this.mask;
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
/* 1284 */         this.index = Double2LongLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1287 */       int pos = Double2LongLinkedOpenHashMap.this.first;
/* 1288 */       this.index = 1;
/* 1289 */       while (pos != this.prev) {
/* 1290 */         pos = (int)Double2LongLinkedOpenHashMap.this.link[pos];
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
/* 1306 */       this.next = (int)Double2LongLinkedOpenHashMap.this.link[this.curr];
/* 1307 */       this.prev = this.curr;
/* 1308 */       if (this.index >= 0)
/* 1309 */         this.index++; 
/* 1310 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1313 */       if (!hasPrevious())
/* 1314 */         throw new NoSuchElementException(); 
/* 1315 */       this.curr = this.prev;
/* 1316 */       this.prev = (int)(Double2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1332 */         this.prev = (int)(Double2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1334 */         this.next = (int)Double2LongLinkedOpenHashMap.this.link[this.curr];
/* 1335 */       }  Double2LongLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       if (this.prev == -1) {
/* 1341 */         Double2LongLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1343 */         Double2LongLinkedOpenHashMap.this.link[this.prev] = Double2LongLinkedOpenHashMap.this.link[this.prev] ^ (Double2LongLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1344 */       }  if (this.next == -1) {
/* 1345 */         Double2LongLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1347 */         Double2LongLinkedOpenHashMap.this.link[this.next] = Double2LongLinkedOpenHashMap.this.link[this.next] ^ (Double2LongLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1348 */       }  int pos = this.curr;
/* 1349 */       this.curr = -1;
/* 1350 */       if (pos == Double2LongLinkedOpenHashMap.this.n) {
/* 1351 */         Double2LongLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1354 */         double[] key = Double2LongLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1358 */           pos = (last = pos) + 1 & Double2LongLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1360 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1361 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1364 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2LongLinkedOpenHashMap.this.mask;
/* 1365 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1367 */             pos = pos + 1 & Double2LongLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1369 */           key[last] = curr;
/* 1370 */           Double2LongLinkedOpenHashMap.this.value[last] = Double2LongLinkedOpenHashMap.this.value[pos];
/* 1371 */           if (this.next == pos)
/* 1372 */             this.next = last; 
/* 1373 */           if (this.prev == pos)
/* 1374 */             this.prev = last; 
/* 1375 */           Double2LongLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Double2LongMap.Entry ok) {
/* 1392 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2LongMap.Entry ok) {
/* 1395 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2LongMap.Entry> { private Double2LongLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1403 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2LongLinkedOpenHashMap.MapEntry next() {
/* 1407 */       return this.entry = new Double2LongLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2LongLinkedOpenHashMap.MapEntry previous() {
/* 1411 */       return this.entry = new Double2LongLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1415 */       super.remove();
/* 1416 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1420 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2LongMap.Entry> { final Double2LongLinkedOpenHashMap.MapEntry entry = new Double2LongLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1424 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2LongLinkedOpenHashMap.MapEntry next() {
/* 1428 */       this.entry.index = nextEntry();
/* 1429 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2LongLinkedOpenHashMap.MapEntry previous() {
/* 1433 */       this.entry.index = previousEntry();
/* 1434 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2LongMap.Entry> implements Double2LongSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2LongMap.Entry> iterator() {
/* 1442 */       return (ObjectBidirectionalIterator<Double2LongMap.Entry>)new Double2LongLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2LongMap.Entry> comparator() {
/* 1446 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2LongMap.Entry> subSet(Double2LongMap.Entry fromElement, Double2LongMap.Entry toElement) {
/* 1451 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2LongMap.Entry> headSet(Double2LongMap.Entry toElement) {
/* 1455 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2LongMap.Entry> tailSet(Double2LongMap.Entry fromElement) {
/* 1459 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2LongMap.Entry first() {
/* 1463 */       if (Double2LongLinkedOpenHashMap.this.size == 0)
/* 1464 */         throw new NoSuchElementException(); 
/* 1465 */       return new Double2LongLinkedOpenHashMap.MapEntry(Double2LongLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2LongMap.Entry last() {
/* 1469 */       if (Double2LongLinkedOpenHashMap.this.size == 0)
/* 1470 */         throw new NoSuchElementException(); 
/* 1471 */       return new Double2LongLinkedOpenHashMap.MapEntry(Double2LongLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1476 */       if (!(o instanceof Map.Entry))
/* 1477 */         return false; 
/* 1478 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1479 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1480 */         return false; 
/* 1481 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1482 */         return false; 
/* 1483 */       double k = ((Double)e.getKey()).doubleValue();
/* 1484 */       long v = ((Long)e.getValue()).longValue();
/* 1485 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1486 */         return (Double2LongLinkedOpenHashMap.this.containsNullKey && Double2LongLinkedOpenHashMap.this.value[Double2LongLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1488 */       double[] key = Double2LongLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1491 */       if (Double.doubleToLongBits(
/* 1492 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2LongLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1494 */         return false; } 
/* 1495 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1496 */         return (Double2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1499 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2LongLinkedOpenHashMap.this.mask]) == 0L)
/* 1500 */           return false; 
/* 1501 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1502 */           return (Double2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1508 */       if (!(o instanceof Map.Entry))
/* 1509 */         return false; 
/* 1510 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1511 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1512 */         return false; 
/* 1513 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1514 */         return false; 
/* 1515 */       double k = ((Double)e.getKey()).doubleValue();
/* 1516 */       long v = ((Long)e.getValue()).longValue();
/* 1517 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1518 */         if (Double2LongLinkedOpenHashMap.this.containsNullKey && Double2LongLinkedOpenHashMap.this.value[Double2LongLinkedOpenHashMap.this.n] == v) {
/* 1519 */           Double2LongLinkedOpenHashMap.this.removeNullEntry();
/* 1520 */           return true;
/*      */         } 
/* 1522 */         return false;
/*      */       } 
/*      */       
/* 1525 */       double[] key = Double2LongLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1528 */       if (Double.doubleToLongBits(
/* 1529 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2LongLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1531 */         return false; } 
/* 1532 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1533 */         if (Double2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1534 */           Double2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1535 */           return true;
/*      */         } 
/* 1537 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1540 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2LongLinkedOpenHashMap.this.mask]) == 0L)
/* 1541 */           return false; 
/* 1542 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1543 */           Double2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1544 */           Double2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1545 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1552 */       return Double2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1556 */       Double2LongLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2LongMap.Entry> iterator(Double2LongMap.Entry from) {
/* 1571 */       return new Double2LongLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2LongMap.Entry> fastIterator() {
/* 1582 */       return new Double2LongLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2LongMap.Entry> fastIterator(Double2LongMap.Entry from) {
/* 1597 */       return new Double2LongLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2LongMap.Entry> consumer) {
/* 1602 */       for (int i = Double2LongLinkedOpenHashMap.this.size, next = Double2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1603 */         int curr = next;
/* 1604 */         next = (int)Double2LongLinkedOpenHashMap.this.link[curr];
/* 1605 */         consumer.accept(new AbstractDouble2LongMap.BasicEntry(Double2LongLinkedOpenHashMap.this.key[curr], Double2LongLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2LongMap.Entry> consumer) {
/* 1611 */       AbstractDouble2LongMap.BasicEntry entry = new AbstractDouble2LongMap.BasicEntry();
/* 1612 */       for (int i = Double2LongLinkedOpenHashMap.this.size, next = Double2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1613 */         int curr = next;
/* 1614 */         next = (int)Double2LongLinkedOpenHashMap.this.link[curr];
/* 1615 */         entry.key = Double2LongLinkedOpenHashMap.this.key[curr];
/* 1616 */         entry.value = Double2LongLinkedOpenHashMap.this.value[curr];
/* 1617 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2LongSortedMap.FastSortedEntrySet double2LongEntrySet() {
/* 1623 */     if (this.entries == null)
/* 1624 */       this.entries = new MapEntrySet(); 
/* 1625 */     return this.entries;
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
/* 1638 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1642 */       return Double2LongLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1649 */       return Double2LongLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1655 */       return new Double2LongLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1659 */       return new Double2LongLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1664 */       if (Double2LongLinkedOpenHashMap.this.containsNullKey)
/* 1665 */         consumer.accept(Double2LongLinkedOpenHashMap.this.key[Double2LongLinkedOpenHashMap.this.n]); 
/* 1666 */       for (int pos = Double2LongLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1667 */         double k = Double2LongLinkedOpenHashMap.this.key[pos];
/* 1668 */         if (Double.doubleToLongBits(k) != 0L)
/* 1669 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1674 */       return Double2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1678 */       return Double2LongLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1682 */       int oldSize = Double2LongLinkedOpenHashMap.this.size;
/* 1683 */       Double2LongLinkedOpenHashMap.this.remove(k);
/* 1684 */       return (Double2LongLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1688 */       Double2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1692 */       if (Double2LongLinkedOpenHashMap.this.size == 0)
/* 1693 */         throw new NoSuchElementException(); 
/* 1694 */       return Double2LongLinkedOpenHashMap.this.key[Double2LongLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1698 */       if (Double2LongLinkedOpenHashMap.this.size == 0)
/* 1699 */         throw new NoSuchElementException(); 
/* 1700 */       return Double2LongLinkedOpenHashMap.this.key[Double2LongLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1704 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1708 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1712 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1716 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1721 */     if (this.keys == null)
/* 1722 */       this.keys = new KeySet(); 
/* 1723 */     return this.keys;
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
/* 1737 */       return Double2LongLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1744 */       return Double2LongLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1749 */     if (this.values == null)
/* 1750 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1753 */             return (LongIterator)new Double2LongLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1757 */             return Double2LongLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1761 */             return Double2LongLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1765 */             Double2LongLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(LongConsumer consumer) {
/* 1770 */             if (Double2LongLinkedOpenHashMap.this.containsNullKey)
/* 1771 */               consumer.accept(Double2LongLinkedOpenHashMap.this.value[Double2LongLinkedOpenHashMap.this.n]); 
/* 1772 */             for (int pos = Double2LongLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1773 */               if (Double.doubleToLongBits(Double2LongLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1774 */                 consumer.accept(Double2LongLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1777 */     return this.values;
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
/* 1794 */     return trim(this.size);
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
/* 1818 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1819 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1820 */       return true; 
/*      */     try {
/* 1822 */       rehash(l);
/* 1823 */     } catch (OutOfMemoryError cantDoIt) {
/* 1824 */       return false;
/*      */     } 
/* 1826 */     return true;
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
/* 1842 */     double[] key = this.key;
/* 1843 */     long[] value = this.value;
/* 1844 */     int mask = newN - 1;
/* 1845 */     double[] newKey = new double[newN + 1];
/* 1846 */     long[] newValue = new long[newN + 1];
/* 1847 */     int i = this.first, prev = -1, newPrev = -1;
/* 1848 */     long[] link = this.link;
/* 1849 */     long[] newLink = new long[newN + 1];
/* 1850 */     this.first = -1;
/* 1851 */     for (int j = this.size; j-- != 0; ) {
/* 1852 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1853 */         pos = newN;
/*      */       } else {
/* 1855 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1856 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1857 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1859 */       newKey[pos] = key[i];
/* 1860 */       newValue[pos] = value[i];
/* 1861 */       if (prev != -1) {
/* 1862 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1863 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1864 */         newPrev = pos;
/*      */       } else {
/* 1866 */         newPrev = this.first = pos;
/*      */         
/* 1868 */         newLink[pos] = -1L;
/*      */       } 
/* 1870 */       int t = i;
/* 1871 */       i = (int)link[i];
/* 1872 */       prev = t;
/*      */     } 
/* 1874 */     this.link = newLink;
/* 1875 */     this.last = newPrev;
/* 1876 */     if (newPrev != -1)
/*      */     {
/* 1878 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1879 */     this.n = newN;
/* 1880 */     this.mask = mask;
/* 1881 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1882 */     this.key = newKey;
/* 1883 */     this.value = newValue;
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
/*      */   public Double2LongLinkedOpenHashMap clone() {
/*      */     Double2LongLinkedOpenHashMap c;
/*      */     try {
/* 1900 */       c = (Double2LongLinkedOpenHashMap)super.clone();
/* 1901 */     } catch (CloneNotSupportedException cantHappen) {
/* 1902 */       throw new InternalError();
/*      */     } 
/* 1904 */     c.keys = null;
/* 1905 */     c.values = null;
/* 1906 */     c.entries = null;
/* 1907 */     c.containsNullKey = this.containsNullKey;
/* 1908 */     c.key = (double[])this.key.clone();
/* 1909 */     c.value = (long[])this.value.clone();
/* 1910 */     c.link = (long[])this.link.clone();
/* 1911 */     return c;
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
/* 1924 */     int h = 0;
/* 1925 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1926 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1927 */         i++; 
/* 1928 */       t = HashCommon.double2int(this.key[i]);
/* 1929 */       t ^= HashCommon.long2int(this.value[i]);
/* 1930 */       h += t;
/* 1931 */       i++;
/*      */     } 
/*      */     
/* 1934 */     if (this.containsNullKey)
/* 1935 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1936 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1939 */     double[] key = this.key;
/* 1940 */     long[] value = this.value;
/* 1941 */     MapIterator i = new MapIterator();
/* 1942 */     s.defaultWriteObject();
/* 1943 */     for (int j = this.size; j-- != 0; ) {
/* 1944 */       int e = i.nextEntry();
/* 1945 */       s.writeDouble(key[e]);
/* 1946 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1951 */     s.defaultReadObject();
/* 1952 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1953 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1954 */     this.mask = this.n - 1;
/* 1955 */     double[] key = this.key = new double[this.n + 1];
/* 1956 */     long[] value = this.value = new long[this.n + 1];
/* 1957 */     long[] link = this.link = new long[this.n + 1];
/* 1958 */     int prev = -1;
/* 1959 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1962 */     for (int i = this.size; i-- != 0; ) {
/* 1963 */       int pos; double k = s.readDouble();
/* 1964 */       long v = s.readLong();
/* 1965 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1966 */         pos = this.n;
/* 1967 */         this.containsNullKey = true;
/*      */       } else {
/* 1969 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1970 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1971 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1973 */       key[pos] = k;
/* 1974 */       value[pos] = v;
/* 1975 */       if (this.first != -1) {
/* 1976 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1977 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1978 */         prev = pos; continue;
/*      */       } 
/* 1980 */       prev = this.first = pos;
/*      */       
/* 1982 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1985 */     this.last = prev;
/* 1986 */     if (prev != -1)
/*      */     {
/* 1988 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2LongLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */