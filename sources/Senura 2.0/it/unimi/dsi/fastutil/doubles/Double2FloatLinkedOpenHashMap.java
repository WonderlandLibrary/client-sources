/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatCollection;
/*      */ import it.unimi.dsi.fastutil.floats.FloatIterator;
/*      */ import it.unimi.dsi.fastutil.floats.FloatListIterator;
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
/*      */ public class Double2FloatLinkedOpenHashMap
/*      */   extends AbstractDouble2FloatSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
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
/*      */   protected transient Double2FloatSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new double[this.n + 1];
/*  162 */     this.value = new float[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatLinkedOpenHashMap() {
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
/*      */   public Double2FloatLinkedOpenHashMap(Map<? extends Double, ? extends Float> m, float f) {
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
/*      */   public Double2FloatLinkedOpenHashMap(Map<? extends Double, ? extends Float> m) {
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
/*      */   public Double2FloatLinkedOpenHashMap(Double2FloatMap m, float f) {
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
/*      */   public Double2FloatLinkedOpenHashMap(Double2FloatMap m) {
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
/*      */   public Double2FloatLinkedOpenHashMap(double[] k, float[] v, float f) {
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
/*      */   public Double2FloatLinkedOpenHashMap(double[] k, float[] v) {
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
/*  285 */     float oldValue = this.value[this.n];
/*  286 */     this.size--;
/*  287 */     fixPointers(this.n);
/*  288 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  289 */       rehash(this.n / 2); 
/*  290 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Float> m) {
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
/*      */   private void insert(int pos, double k, float v) {
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
/*      */   public float put(double k, float v) {
/*  344 */     int pos = find(k);
/*  345 */     if (pos < 0) {
/*  346 */       insert(-pos - 1, k, v);
/*  347 */       return this.defRetValue;
/*      */     } 
/*  349 */     float oldValue = this.value[pos];
/*  350 */     this.value[pos] = v;
/*  351 */     return oldValue;
/*      */   }
/*      */   private float addToValue(int pos, float incr) {
/*  354 */     float oldValue = this.value[pos];
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
/*      */   public float addTo(double k, float incr) {
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
/*      */   public float remove(double k) {
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
/*      */   private float setValue(int pos, float v) {
/*  467 */     float oldValue = this.value[pos];
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
/*      */   public float removeFirstFloat() {
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
/*  490 */     float v = this.value[pos];
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
/*      */   public float removeLastFloat() {
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
/*  517 */     float v = this.value[pos];
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
/*      */   public float getAndMoveToFirst(double k) {
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
/*      */   public float getAndMoveToLast(double k) {
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
/*      */   public float putAndMoveToFirst(double k, float v) {
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
/*      */   public float putAndMoveToLast(double k, float v) {
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
/*      */   public float get(double k) {
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
/*      */   public boolean containsValue(float v) {
/*  798 */     float[] value = this.value;
/*  799 */     double[] key = this.key;
/*  800 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  801 */       return true; 
/*  802 */     for (int i = this.n; i-- != 0;) {
/*  803 */       if (Double.doubleToLongBits(key[i]) != 0L && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  804 */         return true; 
/*  805 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(double k, float defaultValue) {
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
/*      */   public float putIfAbsent(double k, float v) {
/*  834 */     int pos = find(k);
/*  835 */     if (pos >= 0)
/*  836 */       return this.value[pos]; 
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, float v) {
/*  844 */     if (Double.doubleToLongBits(k) == 0L) {
/*  845 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
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
/*  859 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  860 */       Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  861 */       removeEntry(pos);
/*  862 */       return true;
/*      */     } 
/*      */     while (true) {
/*  865 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  866 */         return false; 
/*  867 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  868 */         Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  869 */         removeEntry(pos);
/*  870 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, float oldValue, float v) {
/*  877 */     int pos = find(k);
/*  878 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  879 */       return false; 
/*  880 */     this.value[pos] = v;
/*  881 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(double k, float v) {
/*  886 */     int pos = find(k);
/*  887 */     if (pos < 0)
/*  888 */       return this.defRetValue; 
/*  889 */     float oldValue = this.value[pos];
/*  890 */     this.value[pos] = v;
/*  891 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
/*  896 */     Objects.requireNonNull(mappingFunction);
/*  897 */     int pos = find(k);
/*  898 */     if (pos >= 0)
/*  899 */       return this.value[pos]; 
/*  900 */     float newValue = SafeMath.safeDoubleToFloat(mappingFunction.applyAsDouble(k));
/*  901 */     insert(-pos - 1, k, newValue);
/*  902 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfAbsentNullable(double k, DoubleFunction<? extends Float> mappingFunction) {
/*  908 */     Objects.requireNonNull(mappingFunction);
/*  909 */     int pos = find(k);
/*  910 */     if (pos >= 0)
/*  911 */       return this.value[pos]; 
/*  912 */     Float newValue = mappingFunction.apply(k);
/*  913 */     if (newValue == null)
/*  914 */       return this.defRetValue; 
/*  915 */     float v = newValue.floatValue();
/*  916 */     insert(-pos - 1, k, v);
/*  917 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float computeIfPresent(double k, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
/*  923 */     Objects.requireNonNull(remappingFunction);
/*  924 */     int pos = find(k);
/*  925 */     if (pos < 0)
/*  926 */       return this.defRetValue; 
/*  927 */     Float newValue = remappingFunction.apply(Double.valueOf(k), Float.valueOf(this.value[pos]));
/*  928 */     if (newValue == null) {
/*  929 */       if (Double.doubleToLongBits(k) == 0L) {
/*  930 */         removeNullEntry();
/*      */       } else {
/*  932 */         removeEntry(pos);
/*  933 */       }  return this.defRetValue;
/*      */     } 
/*  935 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float compute(double k, BiFunction<? super Double, ? super Float, ? extends Float> remappingFunction) {
/*  941 */     Objects.requireNonNull(remappingFunction);
/*  942 */     int pos = find(k);
/*  943 */     Float newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  944 */     if (newValue == null) {
/*  945 */       if (pos >= 0)
/*  946 */         if (Double.doubleToLongBits(k) == 0L) {
/*  947 */           removeNullEntry();
/*      */         } else {
/*  949 */           removeEntry(pos);
/*      */         }  
/*  951 */       return this.defRetValue;
/*      */     } 
/*  953 */     float newVal = newValue.floatValue();
/*  954 */     if (pos < 0) {
/*  955 */       insert(-pos - 1, k, newVal);
/*  956 */       return newVal;
/*      */     } 
/*  958 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float merge(double k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  964 */     Objects.requireNonNull(remappingFunction);
/*  965 */     int pos = find(k);
/*  966 */     if (pos < 0) {
/*  967 */       insert(-pos - 1, k, v);
/*  968 */       return v;
/*      */     } 
/*  970 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  971 */     if (newValue == null) {
/*  972 */       if (Double.doubleToLongBits(k) == 0L) {
/*  973 */         removeNullEntry();
/*      */       } else {
/*  975 */         removeEntry(pos);
/*  976 */       }  return this.defRetValue;
/*      */     } 
/*  978 */     this.value[pos] = newValue.floatValue(); return newValue.floatValue();
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
/*  989 */     if (this.size == 0)
/*      */       return; 
/*  991 */     this.size = 0;
/*  992 */     this.containsNullKey = false;
/*  993 */     Arrays.fill(this.key, 0.0D);
/*  994 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  998 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1002 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2FloatMap.Entry, Map.Entry<Double, Float>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1014 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/* 1020 */       return Double2FloatLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/* 1024 */       return Double2FloatLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/* 1028 */       float oldValue = Double2FloatLinkedOpenHashMap.this.value[this.index];
/* 1029 */       Double2FloatLinkedOpenHashMap.this.value[this.index] = v;
/* 1030 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/* 1040 */       return Double.valueOf(Double2FloatLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1050 */       return Float.valueOf(Double2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float setValue(Float v) {
/* 1060 */       return Float.valueOf(setValue(v.floatValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1065 */       if (!(o instanceof Map.Entry))
/* 1066 */         return false; 
/* 1067 */       Map.Entry<Double, Float> e = (Map.Entry<Double, Float>)o;
/* 1068 */       return (Double.doubleToLongBits(Double2FloatLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/* 1069 */         Float.floatToIntBits(Double2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1073 */       return HashCommon.double2int(Double2FloatLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1074 */         HashCommon.float2int(Double2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1078 */       return Double2FloatLinkedOpenHashMap.this.key[this.index] + "=>" + Double2FloatLinkedOpenHashMap.this.value[this.index];
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
/* 1089 */     if (this.size == 0) {
/* 1090 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1093 */     if (this.first == i) {
/* 1094 */       this.first = (int)this.link[i];
/* 1095 */       if (0 <= this.first)
/*      */       {
/* 1097 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1101 */     if (this.last == i) {
/* 1102 */       this.last = (int)(this.link[i] >>> 32L);
/* 1103 */       if (0 <= this.last)
/*      */       {
/* 1105 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1109 */     long linki = this.link[i];
/* 1110 */     int prev = (int)(linki >>> 32L);
/* 1111 */     int next = (int)linki;
/* 1112 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1113 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1126 */     if (this.size == 1) {
/* 1127 */       this.first = this.last = d;
/*      */       
/* 1129 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1132 */     if (this.first == s) {
/* 1133 */       this.first = d;
/* 1134 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1135 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1138 */     if (this.last == s) {
/* 1139 */       this.last = d;
/* 1140 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1141 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1144 */     long links = this.link[s];
/* 1145 */     int prev = (int)(links >>> 32L);
/* 1146 */     int next = (int)links;
/* 1147 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1148 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1149 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/* 1158 */     if (this.size == 0)
/* 1159 */       throw new NoSuchElementException(); 
/* 1160 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/* 1169 */     if (this.size == 0)
/* 1170 */       throw new NoSuchElementException(); 
/* 1171 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatSortedMap tailMap(double from) {
/* 1180 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatSortedMap headMap(double to) {
/* 1189 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2FloatSortedMap subMap(double from, double to) {
/* 1198 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1207 */     return null;
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
/* 1222 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1228 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1233 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     int index = -1;
/*      */     protected MapIterator() {
/* 1241 */       this.next = Double2FloatLinkedOpenHashMap.this.first;
/* 1242 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1245 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1246 */         if (Double2FloatLinkedOpenHashMap.this.containsNullKey) {
/* 1247 */           this.next = (int)Double2FloatLinkedOpenHashMap.this.link[Double2FloatLinkedOpenHashMap.this.n];
/* 1248 */           this.prev = Double2FloatLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1251 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1253 */       if (Double.doubleToLongBits(Double2FloatLinkedOpenHashMap.this.key[Double2FloatLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1254 */         this.prev = Double2FloatLinkedOpenHashMap.this.last;
/* 1255 */         this.index = Double2FloatLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1259 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2FloatLinkedOpenHashMap.this.mask;
/*      */       
/* 1261 */       while (Double.doubleToLongBits(Double2FloatLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1262 */         if (Double.doubleToLongBits(Double2FloatLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1264 */           this.next = (int)Double2FloatLinkedOpenHashMap.this.link[pos];
/* 1265 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1268 */         pos = pos + 1 & Double2FloatLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1270 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1273 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1276 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1279 */       if (this.index >= 0)
/*      */         return; 
/* 1281 */       if (this.prev == -1) {
/* 1282 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1285 */       if (this.next == -1) {
/* 1286 */         this.index = Double2FloatLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1289 */       int pos = Double2FloatLinkedOpenHashMap.this.first;
/* 1290 */       this.index = 1;
/* 1291 */       while (pos != this.prev) {
/* 1292 */         pos = (int)Double2FloatLinkedOpenHashMap.this.link[pos];
/* 1293 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1297 */       ensureIndexKnown();
/* 1298 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1301 */       ensureIndexKnown();
/* 1302 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1305 */       if (!hasNext())
/* 1306 */         throw new NoSuchElementException(); 
/* 1307 */       this.curr = this.next;
/* 1308 */       this.next = (int)Double2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1309 */       this.prev = this.curr;
/* 1310 */       if (this.index >= 0)
/* 1311 */         this.index++; 
/* 1312 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1315 */       if (!hasPrevious())
/* 1316 */         throw new NoSuchElementException(); 
/* 1317 */       this.curr = this.prev;
/* 1318 */       this.prev = (int)(Double2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1319 */       this.next = this.curr;
/* 1320 */       if (this.index >= 0)
/* 1321 */         this.index--; 
/* 1322 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1325 */       ensureIndexKnown();
/* 1326 */       if (this.curr == -1)
/* 1327 */         throw new IllegalStateException(); 
/* 1328 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1333 */         this.index--;
/* 1334 */         this.prev = (int)(Double2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1336 */         this.next = (int)Double2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1337 */       }  Double2FloatLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1342 */       if (this.prev == -1) {
/* 1343 */         Double2FloatLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1345 */         Double2FloatLinkedOpenHashMap.this.link[this.prev] = Double2FloatLinkedOpenHashMap.this.link[this.prev] ^ (Double2FloatLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1346 */       }  if (this.next == -1) {
/* 1347 */         Double2FloatLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1349 */         Double2FloatLinkedOpenHashMap.this.link[this.next] = Double2FloatLinkedOpenHashMap.this.link[this.next] ^ (Double2FloatLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1350 */       }  int pos = this.curr;
/* 1351 */       this.curr = -1;
/* 1352 */       if (pos == Double2FloatLinkedOpenHashMap.this.n) {
/* 1353 */         Double2FloatLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1356 */         double[] key = Double2FloatLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1360 */           pos = (last = pos) + 1 & Double2FloatLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1362 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1363 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1366 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2FloatLinkedOpenHashMap.this.mask;
/* 1367 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1369 */             pos = pos + 1 & Double2FloatLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1371 */           key[last] = curr;
/* 1372 */           Double2FloatLinkedOpenHashMap.this.value[last] = Double2FloatLinkedOpenHashMap.this.value[pos];
/* 1373 */           if (this.next == pos)
/* 1374 */             this.next = last; 
/* 1375 */           if (this.prev == pos)
/* 1376 */             this.prev = last; 
/* 1377 */           Double2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1382 */       int i = n;
/* 1383 */       while (i-- != 0 && hasNext())
/* 1384 */         nextEntry(); 
/* 1385 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1388 */       int i = n;
/* 1389 */       while (i-- != 0 && hasPrevious())
/* 1390 */         previousEntry(); 
/* 1391 */       return n - i - 1;
/*      */     }
/*      */     public void set(Double2FloatMap.Entry ok) {
/* 1394 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2FloatMap.Entry ok) {
/* 1397 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2FloatMap.Entry> { private Double2FloatLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1405 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2FloatLinkedOpenHashMap.MapEntry next() {
/* 1409 */       return this.entry = new Double2FloatLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1413 */       return this.entry = new Double2FloatLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1417 */       super.remove();
/* 1418 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1422 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2FloatMap.Entry> { final Double2FloatLinkedOpenHashMap.MapEntry entry = new Double2FloatLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1426 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2FloatLinkedOpenHashMap.MapEntry next() {
/* 1430 */       this.entry.index = nextEntry();
/* 1431 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1435 */       this.entry.index = previousEntry();
/* 1436 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2FloatMap.Entry> implements Double2FloatSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2FloatMap.Entry> iterator() {
/* 1444 */       return (ObjectBidirectionalIterator<Double2FloatMap.Entry>)new Double2FloatLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2FloatMap.Entry> comparator() {
/* 1448 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2FloatMap.Entry> subSet(Double2FloatMap.Entry fromElement, Double2FloatMap.Entry toElement) {
/* 1453 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2FloatMap.Entry> headSet(Double2FloatMap.Entry toElement) {
/* 1457 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2FloatMap.Entry> tailSet(Double2FloatMap.Entry fromElement) {
/* 1461 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2FloatMap.Entry first() {
/* 1465 */       if (Double2FloatLinkedOpenHashMap.this.size == 0)
/* 1466 */         throw new NoSuchElementException(); 
/* 1467 */       return new Double2FloatLinkedOpenHashMap.MapEntry(Double2FloatLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2FloatMap.Entry last() {
/* 1471 */       if (Double2FloatLinkedOpenHashMap.this.size == 0)
/* 1472 */         throw new NoSuchElementException(); 
/* 1473 */       return new Double2FloatLinkedOpenHashMap.MapEntry(Double2FloatLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1478 */       if (!(o instanceof Map.Entry))
/* 1479 */         return false; 
/* 1480 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1481 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1482 */         return false; 
/* 1483 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1484 */         return false; 
/* 1485 */       double k = ((Double)e.getKey()).doubleValue();
/* 1486 */       float v = ((Float)e.getValue()).floatValue();
/* 1487 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1488 */         return (Double2FloatLinkedOpenHashMap.this.containsNullKey && 
/* 1489 */           Float.floatToIntBits(Double2FloatLinkedOpenHashMap.this.value[Double2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1491 */       double[] key = Double2FloatLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1494 */       if (Double.doubleToLongBits(
/* 1495 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2FloatLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1497 */         return false; } 
/* 1498 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1499 */         return (Float.floatToIntBits(Double2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1502 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2FloatLinkedOpenHashMap.this.mask]) == 0L)
/* 1503 */           return false; 
/* 1504 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1505 */           return (Float.floatToIntBits(Double2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1511 */       if (!(o instanceof Map.Entry))
/* 1512 */         return false; 
/* 1513 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1514 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1515 */         return false; 
/* 1516 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1517 */         return false; 
/* 1518 */       double k = ((Double)e.getKey()).doubleValue();
/* 1519 */       float v = ((Float)e.getValue()).floatValue();
/* 1520 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1521 */         if (Double2FloatLinkedOpenHashMap.this.containsNullKey && Float.floatToIntBits(Double2FloatLinkedOpenHashMap.this.value[Double2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1522 */           Double2FloatLinkedOpenHashMap.this.removeNullEntry();
/* 1523 */           return true;
/*      */         } 
/* 1525 */         return false;
/*      */       } 
/*      */       
/* 1528 */       double[] key = Double2FloatLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1531 */       if (Double.doubleToLongBits(
/* 1532 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2FloatLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1534 */         return false; } 
/* 1535 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1536 */         if (Float.floatToIntBits(Double2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1537 */           Double2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1538 */           return true;
/*      */         } 
/* 1540 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1543 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2FloatLinkedOpenHashMap.this.mask]) == 0L)
/* 1544 */           return false; 
/* 1545 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1546 */           Float.floatToIntBits(Double2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1547 */           Double2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1548 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1555 */       return Double2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1559 */       Double2FloatLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2FloatMap.Entry> iterator(Double2FloatMap.Entry from) {
/* 1574 */       return new Double2FloatLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2FloatMap.Entry> fastIterator() {
/* 1585 */       return new Double2FloatLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2FloatMap.Entry> fastIterator(Double2FloatMap.Entry from) {
/* 1600 */       return new Double2FloatLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2FloatMap.Entry> consumer) {
/* 1605 */       for (int i = Double2FloatLinkedOpenHashMap.this.size, next = Double2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1606 */         int curr = next;
/* 1607 */         next = (int)Double2FloatLinkedOpenHashMap.this.link[curr];
/* 1608 */         consumer.accept(new AbstractDouble2FloatMap.BasicEntry(Double2FloatLinkedOpenHashMap.this.key[curr], Double2FloatLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2FloatMap.Entry> consumer) {
/* 1614 */       AbstractDouble2FloatMap.BasicEntry entry = new AbstractDouble2FloatMap.BasicEntry();
/* 1615 */       for (int i = Double2FloatLinkedOpenHashMap.this.size, next = Double2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1616 */         int curr = next;
/* 1617 */         next = (int)Double2FloatLinkedOpenHashMap.this.link[curr];
/* 1618 */         entry.key = Double2FloatLinkedOpenHashMap.this.key[curr];
/* 1619 */         entry.value = Double2FloatLinkedOpenHashMap.this.value[curr];
/* 1620 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2FloatSortedMap.FastSortedEntrySet double2FloatEntrySet() {
/* 1626 */     if (this.entries == null)
/* 1627 */       this.entries = new MapEntrySet(); 
/* 1628 */     return this.entries;
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
/* 1641 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1645 */       return Double2FloatLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1652 */       return Double2FloatLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1658 */       return new Double2FloatLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1662 */       return new Double2FloatLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1667 */       if (Double2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1668 */         consumer.accept(Double2FloatLinkedOpenHashMap.this.key[Double2FloatLinkedOpenHashMap.this.n]); 
/* 1669 */       for (int pos = Double2FloatLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1670 */         double k = Double2FloatLinkedOpenHashMap.this.key[pos];
/* 1671 */         if (Double.doubleToLongBits(k) != 0L)
/* 1672 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1677 */       return Double2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1681 */       return Double2FloatLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1685 */       int oldSize = Double2FloatLinkedOpenHashMap.this.size;
/* 1686 */       Double2FloatLinkedOpenHashMap.this.remove(k);
/* 1687 */       return (Double2FloatLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1691 */       Double2FloatLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1695 */       if (Double2FloatLinkedOpenHashMap.this.size == 0)
/* 1696 */         throw new NoSuchElementException(); 
/* 1697 */       return Double2FloatLinkedOpenHashMap.this.key[Double2FloatLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1701 */       if (Double2FloatLinkedOpenHashMap.this.size == 0)
/* 1702 */         throw new NoSuchElementException(); 
/* 1703 */       return Double2FloatLinkedOpenHashMap.this.key[Double2FloatLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1707 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1711 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1715 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1719 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1724 */     if (this.keys == null)
/* 1725 */       this.keys = new KeySet(); 
/* 1726 */     return this.keys;
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
/* 1740 */       return Double2FloatLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1747 */       return Double2FloatLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1752 */     if (this.values == null)
/* 1753 */       this.values = (FloatCollection)new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1756 */             return (FloatIterator)new Double2FloatLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1760 */             return Double2FloatLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1764 */             return Double2FloatLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1768 */             Double2FloatLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1773 */             if (Double2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1774 */               consumer.accept(Double2FloatLinkedOpenHashMap.this.value[Double2FloatLinkedOpenHashMap.this.n]); 
/* 1775 */             for (int pos = Double2FloatLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1776 */               if (Double.doubleToLongBits(Double2FloatLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1777 */                 consumer.accept(Double2FloatLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1780 */     return this.values;
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
/* 1797 */     return trim(this.size);
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
/* 1821 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1822 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1823 */       return true; 
/*      */     try {
/* 1825 */       rehash(l);
/* 1826 */     } catch (OutOfMemoryError cantDoIt) {
/* 1827 */       return false;
/*      */     } 
/* 1829 */     return true;
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
/* 1845 */     double[] key = this.key;
/* 1846 */     float[] value = this.value;
/* 1847 */     int mask = newN - 1;
/* 1848 */     double[] newKey = new double[newN + 1];
/* 1849 */     float[] newValue = new float[newN + 1];
/* 1850 */     int i = this.first, prev = -1, newPrev = -1;
/* 1851 */     long[] link = this.link;
/* 1852 */     long[] newLink = new long[newN + 1];
/* 1853 */     this.first = -1;
/* 1854 */     for (int j = this.size; j-- != 0; ) {
/* 1855 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1856 */         pos = newN;
/*      */       } else {
/* 1858 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1859 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1860 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1862 */       newKey[pos] = key[i];
/* 1863 */       newValue[pos] = value[i];
/* 1864 */       if (prev != -1) {
/* 1865 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1866 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1867 */         newPrev = pos;
/*      */       } else {
/* 1869 */         newPrev = this.first = pos;
/*      */         
/* 1871 */         newLink[pos] = -1L;
/*      */       } 
/* 1873 */       int t = i;
/* 1874 */       i = (int)link[i];
/* 1875 */       prev = t;
/*      */     } 
/* 1877 */     this.link = newLink;
/* 1878 */     this.last = newPrev;
/* 1879 */     if (newPrev != -1)
/*      */     {
/* 1881 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1882 */     this.n = newN;
/* 1883 */     this.mask = mask;
/* 1884 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1885 */     this.key = newKey;
/* 1886 */     this.value = newValue;
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
/*      */   public Double2FloatLinkedOpenHashMap clone() {
/*      */     Double2FloatLinkedOpenHashMap c;
/*      */     try {
/* 1903 */       c = (Double2FloatLinkedOpenHashMap)super.clone();
/* 1904 */     } catch (CloneNotSupportedException cantHappen) {
/* 1905 */       throw new InternalError();
/*      */     } 
/* 1907 */     c.keys = null;
/* 1908 */     c.values = null;
/* 1909 */     c.entries = null;
/* 1910 */     c.containsNullKey = this.containsNullKey;
/* 1911 */     c.key = (double[])this.key.clone();
/* 1912 */     c.value = (float[])this.value.clone();
/* 1913 */     c.link = (long[])this.link.clone();
/* 1914 */     return c;
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
/* 1927 */     int h = 0;
/* 1928 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1929 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1930 */         i++; 
/* 1931 */       t = HashCommon.double2int(this.key[i]);
/* 1932 */       t ^= HashCommon.float2int(this.value[i]);
/* 1933 */       h += t;
/* 1934 */       i++;
/*      */     } 
/*      */     
/* 1937 */     if (this.containsNullKey)
/* 1938 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1939 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1942 */     double[] key = this.key;
/* 1943 */     float[] value = this.value;
/* 1944 */     MapIterator i = new MapIterator();
/* 1945 */     s.defaultWriteObject();
/* 1946 */     for (int j = this.size; j-- != 0; ) {
/* 1947 */       int e = i.nextEntry();
/* 1948 */       s.writeDouble(key[e]);
/* 1949 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1954 */     s.defaultReadObject();
/* 1955 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1956 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1957 */     this.mask = this.n - 1;
/* 1958 */     double[] key = this.key = new double[this.n + 1];
/* 1959 */     float[] value = this.value = new float[this.n + 1];
/* 1960 */     long[] link = this.link = new long[this.n + 1];
/* 1961 */     int prev = -1;
/* 1962 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1965 */     for (int i = this.size; i-- != 0; ) {
/* 1966 */       int pos; double k = s.readDouble();
/* 1967 */       float v = s.readFloat();
/* 1968 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1969 */         pos = this.n;
/* 1970 */         this.containsNullKey = true;
/*      */       } else {
/* 1972 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1973 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1974 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1976 */       key[pos] = k;
/* 1977 */       value[pos] = v;
/* 1978 */       if (this.first != -1) {
/* 1979 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1980 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1981 */         prev = pos; continue;
/*      */       } 
/* 1983 */       prev = this.first = pos;
/*      */       
/* 1985 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1988 */     this.last = prev;
/* 1989 */     if (prev != -1)
/*      */     {
/* 1991 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2FloatLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */