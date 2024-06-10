/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleCollection;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleIterator;
/*      */ import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
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
/*      */ public class Float2DoubleLinkedOpenHashMap
/*      */   extends AbstractFloat2DoubleSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
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
/*      */   protected transient Float2DoubleSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new float[this.n + 1];
/*  162 */     this.value = new double[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleLinkedOpenHashMap() {
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
/*      */   public Float2DoubleLinkedOpenHashMap(Map<? extends Float, ? extends Double> m, float f) {
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
/*      */   public Float2DoubleLinkedOpenHashMap(Map<? extends Float, ? extends Double> m) {
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
/*      */   public Float2DoubleLinkedOpenHashMap(Float2DoubleMap m, float f) {
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
/*      */   public Float2DoubleLinkedOpenHashMap(Float2DoubleMap m) {
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
/*      */   public Float2DoubleLinkedOpenHashMap(float[] k, double[] v, float f) {
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
/*      */   public Float2DoubleLinkedOpenHashMap(float[] k, double[] v) {
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
/*      */   public void putAll(Map<? extends Float, ? extends Double> m) {
/*  294 */     if (this.f <= 0.5D) {
/*  295 */       ensureCapacity(m.size());
/*      */     } else {
/*  297 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  299 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  303 */     if (Float.floatToIntBits(k) == 0) {
/*  304 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  306 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  309 */     if (Float.floatToIntBits(
/*  310 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  312 */       return -(pos + 1); } 
/*  313 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  314 */       return pos;
/*      */     }
/*      */     while (true) {
/*  317 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  318 */         return -(pos + 1); 
/*  319 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  320 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, double v) {
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
/*      */   public double put(float k, double v) {
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
/*      */   public double addTo(float k, double incr) {
/*      */     int pos;
/*  376 */     if (Float.floatToIntBits(k) == 0) {
/*  377 */       if (this.containsNullKey)
/*  378 */         return addToValue(this.n, incr); 
/*  379 */       pos = this.n;
/*  380 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  383 */       float[] key = this.key;
/*      */       float curr;
/*  385 */       if (Float.floatToIntBits(
/*  386 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  388 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
/*  389 */           return addToValue(pos, incr); 
/*  390 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  391 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k))
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
/*  423 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  425 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  427 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  428 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  431 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
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
/*      */   public double remove(float k) {
/*  444 */     if (Float.floatToIntBits(k) == 0) {
/*  445 */       if (this.containsNullKey)
/*  446 */         return removeNullEntry(); 
/*  447 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  450 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  453 */     if (Float.floatToIntBits(
/*  454 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  456 */       return this.defRetValue; } 
/*  457 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  458 */       return removeEntry(pos); 
/*      */     while (true) {
/*  460 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  461 */         return this.defRetValue; 
/*  462 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
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
/*      */   public double getAndMoveToFirst(float k) {
/*  572 */     if (Float.floatToIntBits(k) == 0) {
/*  573 */       if (this.containsNullKey) {
/*  574 */         moveIndexToFirst(this.n);
/*  575 */         return this.value[this.n];
/*      */       } 
/*  577 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  580 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  583 */     if (Float.floatToIntBits(
/*  584 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  586 */       return this.defRetValue; } 
/*  587 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  588 */       moveIndexToFirst(pos);
/*  589 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  593 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  594 */         return this.defRetValue; 
/*  595 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public double getAndMoveToLast(float k) {
/*  611 */     if (Float.floatToIntBits(k) == 0) {
/*  612 */       if (this.containsNullKey) {
/*  613 */         moveIndexToLast(this.n);
/*  614 */         return this.value[this.n];
/*      */       } 
/*  616 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  619 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  622 */     if (Float.floatToIntBits(
/*  623 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  625 */       return this.defRetValue; } 
/*  626 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  627 */       moveIndexToLast(pos);
/*  628 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  632 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  633 */         return this.defRetValue; 
/*  634 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public double putAndMoveToFirst(float k, double v) {
/*      */     int pos;
/*  653 */     if (Float.floatToIntBits(k) == 0) {
/*  654 */       if (this.containsNullKey) {
/*  655 */         moveIndexToFirst(this.n);
/*  656 */         return setValue(this.n, v);
/*      */       } 
/*  658 */       this.containsNullKey = true;
/*  659 */       pos = this.n;
/*      */     } else {
/*      */       
/*  662 */       float[] key = this.key;
/*      */       float curr;
/*  664 */       if (Float.floatToIntBits(
/*  665 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  667 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  668 */           moveIndexToFirst(pos);
/*  669 */           return setValue(pos, v);
/*      */         } 
/*  671 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  672 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public double putAndMoveToLast(float k, double v) {
/*      */     int pos;
/*  708 */     if (Float.floatToIntBits(k) == 0) {
/*  709 */       if (this.containsNullKey) {
/*  710 */         moveIndexToLast(this.n);
/*  711 */         return setValue(this.n, v);
/*      */       } 
/*  713 */       this.containsNullKey = true;
/*  714 */       pos = this.n;
/*      */     } else {
/*      */       
/*  717 */       float[] key = this.key;
/*      */       float curr;
/*  719 */       if (Float.floatToIntBits(
/*  720 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  722 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  723 */           moveIndexToLast(pos);
/*  724 */           return setValue(pos, v);
/*      */         } 
/*  726 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  727 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public double get(float k) {
/*  753 */     if (Float.floatToIntBits(k) == 0) {
/*  754 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  756 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  759 */     if (Float.floatToIntBits(
/*  760 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  762 */       return this.defRetValue; } 
/*  763 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  764 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  767 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  768 */         return this.defRetValue; 
/*  769 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  770 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  776 */     if (Float.floatToIntBits(k) == 0) {
/*  777 */       return this.containsNullKey;
/*      */     }
/*  779 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  782 */     if (Float.floatToIntBits(
/*  783 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  785 */       return false; } 
/*  786 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  787 */       return true;
/*      */     }
/*      */     while (true) {
/*  790 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  791 */         return false; 
/*  792 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  793 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(double v) {
/*  798 */     double[] value = this.value;
/*  799 */     float[] key = this.key;
/*  800 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  801 */       return true; 
/*  802 */     for (int i = this.n; i-- != 0;) {
/*  803 */       if (Float.floatToIntBits(key[i]) != 0 && 
/*  804 */         Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  805 */         return true; 
/*  806 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(float k, double defaultValue) {
/*  812 */     if (Float.floatToIntBits(k) == 0) {
/*  813 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  815 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  818 */     if (Float.floatToIntBits(
/*  819 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  821 */       return defaultValue; } 
/*  822 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  823 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  826 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  827 */         return defaultValue; 
/*  828 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  829 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(float k, double v) {
/*  835 */     int pos = find(k);
/*  836 */     if (pos >= 0)
/*  837 */       return this.value[pos]; 
/*  838 */     insert(-pos - 1, k, v);
/*  839 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, double v) {
/*  845 */     if (Float.floatToIntBits(k) == 0) {
/*  846 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  847 */         removeNullEntry();
/*  848 */         return true;
/*      */       } 
/*  850 */       return false;
/*      */     } 
/*      */     
/*  853 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  856 */     if (Float.floatToIntBits(
/*  857 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  859 */       return false; } 
/*  860 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  861 */       Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  862 */       removeEntry(pos);
/*  863 */       return true;
/*      */     } 
/*      */     while (true) {
/*  866 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  867 */         return false; 
/*  868 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  869 */         Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  870 */         removeEntry(pos);
/*  871 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, double oldValue, double v) {
/*  878 */     int pos = find(k);
/*  879 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  880 */       return false; 
/*  881 */     this.value[pos] = v;
/*  882 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(float k, double v) {
/*  887 */     int pos = find(k);
/*  888 */     if (pos < 0)
/*  889 */       return this.defRetValue; 
/*  890 */     double oldValue = this.value[pos];
/*  891 */     this.value[pos] = v;
/*  892 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(float k, DoubleUnaryOperator mappingFunction) {
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
/*      */   public double computeIfAbsentNullable(float k, DoubleFunction<? extends Double> mappingFunction) {
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
/*      */   public double computeIfPresent(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  924 */     Objects.requireNonNull(remappingFunction);
/*  925 */     int pos = find(k);
/*  926 */     if (pos < 0)
/*  927 */       return this.defRetValue; 
/*  928 */     Double newValue = remappingFunction.apply(Float.valueOf(k), Double.valueOf(this.value[pos]));
/*  929 */     if (newValue == null) {
/*  930 */       if (Float.floatToIntBits(k) == 0) {
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
/*      */   public double compute(float k, BiFunction<? super Float, ? super Double, ? extends Double> remappingFunction) {
/*  942 */     Objects.requireNonNull(remappingFunction);
/*  943 */     int pos = find(k);
/*  944 */     Double newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  945 */     if (newValue == null) {
/*  946 */       if (pos >= 0)
/*  947 */         if (Float.floatToIntBits(k) == 0) {
/*  948 */           removeNullEntry();
/*      */         } else {
/*  950 */           removeEntry(pos);
/*      */         }  
/*  952 */       return this.defRetValue;
/*      */     } 
/*  954 */     double newVal = newValue.doubleValue();
/*  955 */     if (pos < 0) {
/*  956 */       insert(-pos - 1, k, newVal);
/*  957 */       return newVal;
/*      */     } 
/*  959 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(float k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  965 */     Objects.requireNonNull(remappingFunction);
/*  966 */     int pos = find(k);
/*  967 */     if (pos < 0) {
/*  968 */       insert(-pos - 1, k, v);
/*  969 */       return v;
/*      */     } 
/*  971 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  972 */     if (newValue == null) {
/*  973 */       if (Float.floatToIntBits(k) == 0) {
/*  974 */         removeNullEntry();
/*      */       } else {
/*  976 */         removeEntry(pos);
/*  977 */       }  return this.defRetValue;
/*      */     } 
/*  979 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  990 */     if (this.size == 0)
/*      */       return; 
/*  992 */     this.size = 0;
/*  993 */     this.containsNullKey = false;
/*  994 */     Arrays.fill(this.key, 0.0F);
/*  995 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  999 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/* 1003 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2DoubleMap.Entry, Map.Entry<Float, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/* 1015 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/* 1021 */       return Float2DoubleLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/* 1025 */       return Float2DoubleLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/* 1029 */       double oldValue = Float2DoubleLinkedOpenHashMap.this.value[this.index];
/* 1030 */       Float2DoubleLinkedOpenHashMap.this.value[this.index] = v;
/* 1031 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/* 1041 */       return Float.valueOf(Float2DoubleLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/* 1051 */       return Double.valueOf(Float2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/* 1061 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1066 */       if (!(o instanceof Map.Entry))
/* 1067 */         return false; 
/* 1068 */       Map.Entry<Float, Double> e = (Map.Entry<Float, Double>)o;
/* 1069 */       return (Float.floatToIntBits(Float2DoubleLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && 
/* 1070 */         Double.doubleToLongBits(Float2DoubleLinkedOpenHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1074 */       return HashCommon.float2int(Float2DoubleLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1075 */         HashCommon.double2int(Float2DoubleLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1079 */       return Float2DoubleLinkedOpenHashMap.this.key[this.index] + "=>" + Float2DoubleLinkedOpenHashMap.this.value[this.index];
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
/* 1090 */     if (this.size == 0) {
/* 1091 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1094 */     if (this.first == i) {
/* 1095 */       this.first = (int)this.link[i];
/* 1096 */       if (0 <= this.first)
/*      */       {
/* 1098 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1102 */     if (this.last == i) {
/* 1103 */       this.last = (int)(this.link[i] >>> 32L);
/* 1104 */       if (0 <= this.last)
/*      */       {
/* 1106 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1110 */     long linki = this.link[i];
/* 1111 */     int prev = (int)(linki >>> 32L);
/* 1112 */     int next = (int)linki;
/* 1113 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1114 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1127 */     if (this.size == 1) {
/* 1128 */       this.first = this.last = d;
/*      */       
/* 1130 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1133 */     if (this.first == s) {
/* 1134 */       this.first = d;
/* 1135 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1136 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1139 */     if (this.last == s) {
/* 1140 */       this.last = d;
/* 1141 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1142 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1145 */     long links = this.link[s];
/* 1146 */     int prev = (int)(links >>> 32L);
/* 1147 */     int next = (int)links;
/* 1148 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1149 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1150 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float firstFloatKey() {
/* 1159 */     if (this.size == 0)
/* 1160 */       throw new NoSuchElementException(); 
/* 1161 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float lastFloatKey() {
/* 1170 */     if (this.size == 0)
/* 1171 */       throw new NoSuchElementException(); 
/* 1172 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleSortedMap tailMap(float from) {
/* 1181 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleSortedMap headMap(float to) {
/* 1190 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2DoubleSortedMap subMap(float from, float to) {
/* 1199 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
/* 1208 */     return null;
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
/* 1223 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1229 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1234 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1240 */     int index = -1;
/*      */     protected MapIterator() {
/* 1242 */       this.next = Float2DoubleLinkedOpenHashMap.this.first;
/* 1243 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(float from) {
/* 1246 */       if (Float.floatToIntBits(from) == 0) {
/* 1247 */         if (Float2DoubleLinkedOpenHashMap.this.containsNullKey) {
/* 1248 */           this.next = (int)Float2DoubleLinkedOpenHashMap.this.link[Float2DoubleLinkedOpenHashMap.this.n];
/* 1249 */           this.prev = Float2DoubleLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1252 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1254 */       if (Float.floatToIntBits(Float2DoubleLinkedOpenHashMap.this.key[Float2DoubleLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1255 */         this.prev = Float2DoubleLinkedOpenHashMap.this.last;
/* 1256 */         this.index = Float2DoubleLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1260 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2DoubleLinkedOpenHashMap.this.mask;
/*      */       
/* 1262 */       while (Float.floatToIntBits(Float2DoubleLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1263 */         if (Float.floatToIntBits(Float2DoubleLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1265 */           this.next = (int)Float2DoubleLinkedOpenHashMap.this.link[pos];
/* 1266 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1269 */         pos = pos + 1 & Float2DoubleLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1271 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1274 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1277 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1280 */       if (this.index >= 0)
/*      */         return; 
/* 1282 */       if (this.prev == -1) {
/* 1283 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1286 */       if (this.next == -1) {
/* 1287 */         this.index = Float2DoubleLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1290 */       int pos = Float2DoubleLinkedOpenHashMap.this.first;
/* 1291 */       this.index = 1;
/* 1292 */       while (pos != this.prev) {
/* 1293 */         pos = (int)Float2DoubleLinkedOpenHashMap.this.link[pos];
/* 1294 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1298 */       ensureIndexKnown();
/* 1299 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1302 */       ensureIndexKnown();
/* 1303 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1306 */       if (!hasNext())
/* 1307 */         throw new NoSuchElementException(); 
/* 1308 */       this.curr = this.next;
/* 1309 */       this.next = (int)Float2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1310 */       this.prev = this.curr;
/* 1311 */       if (this.index >= 0)
/* 1312 */         this.index++; 
/* 1313 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1316 */       if (!hasPrevious())
/* 1317 */         throw new NoSuchElementException(); 
/* 1318 */       this.curr = this.prev;
/* 1319 */       this.prev = (int)(Float2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1320 */       this.next = this.curr;
/* 1321 */       if (this.index >= 0)
/* 1322 */         this.index--; 
/* 1323 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1326 */       ensureIndexKnown();
/* 1327 */       if (this.curr == -1)
/* 1328 */         throw new IllegalStateException(); 
/* 1329 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1334 */         this.index--;
/* 1335 */         this.prev = (int)(Float2DoubleLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1337 */         this.next = (int)Float2DoubleLinkedOpenHashMap.this.link[this.curr];
/* 1338 */       }  Float2DoubleLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1343 */       if (this.prev == -1) {
/* 1344 */         Float2DoubleLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1346 */         Float2DoubleLinkedOpenHashMap.this.link[this.prev] = Float2DoubleLinkedOpenHashMap.this.link[this.prev] ^ (Float2DoubleLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1347 */       }  if (this.next == -1) {
/* 1348 */         Float2DoubleLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1350 */         Float2DoubleLinkedOpenHashMap.this.link[this.next] = Float2DoubleLinkedOpenHashMap.this.link[this.next] ^ (Float2DoubleLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1351 */       }  int pos = this.curr;
/* 1352 */       this.curr = -1;
/* 1353 */       if (pos == Float2DoubleLinkedOpenHashMap.this.n) {
/* 1354 */         Float2DoubleLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1357 */         float[] key = Float2DoubleLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           float curr;
/*      */           int last;
/* 1361 */           pos = (last = pos) + 1 & Float2DoubleLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1363 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1364 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1367 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2DoubleLinkedOpenHashMap.this.mask;
/*      */             
/* 1369 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1371 */             pos = pos + 1 & Float2DoubleLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1373 */           key[last] = curr;
/* 1374 */           Float2DoubleLinkedOpenHashMap.this.value[last] = Float2DoubleLinkedOpenHashMap.this.value[pos];
/* 1375 */           if (this.next == pos)
/* 1376 */             this.next = last; 
/* 1377 */           if (this.prev == pos)
/* 1378 */             this.prev = last; 
/* 1379 */           Float2DoubleLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Float2DoubleMap.Entry ok) {
/* 1396 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Float2DoubleMap.Entry ok) {
/* 1399 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Float2DoubleMap.Entry> { private Float2DoubleLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1407 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2DoubleLinkedOpenHashMap.MapEntry next() {
/* 1411 */       return this.entry = new Float2DoubleLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Float2DoubleLinkedOpenHashMap.MapEntry previous() {
/* 1415 */       return this.entry = new Float2DoubleLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1419 */       super.remove();
/* 1420 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1424 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Float2DoubleMap.Entry> { final Float2DoubleLinkedOpenHashMap.MapEntry entry = new Float2DoubleLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1428 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2DoubleLinkedOpenHashMap.MapEntry next() {
/* 1432 */       this.entry.index = nextEntry();
/* 1433 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Float2DoubleLinkedOpenHashMap.MapEntry previous() {
/* 1437 */       this.entry.index = previousEntry();
/* 1438 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2DoubleMap.Entry> implements Float2DoubleSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Float2DoubleMap.Entry> iterator() {
/* 1446 */       return (ObjectBidirectionalIterator<Float2DoubleMap.Entry>)new Float2DoubleLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Float2DoubleMap.Entry> comparator() {
/* 1450 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2DoubleMap.Entry> subSet(Float2DoubleMap.Entry fromElement, Float2DoubleMap.Entry toElement) {
/* 1455 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2DoubleMap.Entry> headSet(Float2DoubleMap.Entry toElement) {
/* 1459 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2DoubleMap.Entry> tailSet(Float2DoubleMap.Entry fromElement) {
/* 1463 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Float2DoubleMap.Entry first() {
/* 1467 */       if (Float2DoubleLinkedOpenHashMap.this.size == 0)
/* 1468 */         throw new NoSuchElementException(); 
/* 1469 */       return new Float2DoubleLinkedOpenHashMap.MapEntry(Float2DoubleLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Float2DoubleMap.Entry last() {
/* 1473 */       if (Float2DoubleLinkedOpenHashMap.this.size == 0)
/* 1474 */         throw new NoSuchElementException(); 
/* 1475 */       return new Float2DoubleLinkedOpenHashMap.MapEntry(Float2DoubleLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1480 */       if (!(o instanceof Map.Entry))
/* 1481 */         return false; 
/* 1482 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1483 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1484 */         return false; 
/* 1485 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1486 */         return false; 
/* 1487 */       float k = ((Float)e.getKey()).floatValue();
/* 1488 */       double v = ((Double)e.getValue()).doubleValue();
/* 1489 */       if (Float.floatToIntBits(k) == 0) {
/* 1490 */         return (Float2DoubleLinkedOpenHashMap.this.containsNullKey && 
/* 1491 */           Double.doubleToLongBits(Float2DoubleLinkedOpenHashMap.this.value[Float2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/* 1493 */       float[] key = Float2DoubleLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1496 */       if (Float.floatToIntBits(
/* 1497 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2DoubleLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1499 */         return false; } 
/* 1500 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1501 */         return (Double.doubleToLongBits(Float2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/* 1504 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleLinkedOpenHashMap.this.mask]) == 0)
/* 1505 */           return false; 
/* 1506 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1507 */           return (Double.doubleToLongBits(Float2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1513 */       if (!(o instanceof Map.Entry))
/* 1514 */         return false; 
/* 1515 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1516 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1517 */         return false; 
/* 1518 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/* 1519 */         return false; 
/* 1520 */       float k = ((Float)e.getKey()).floatValue();
/* 1521 */       double v = ((Double)e.getValue()).doubleValue();
/* 1522 */       if (Float.floatToIntBits(k) == 0) {
/* 1523 */         if (Float2DoubleLinkedOpenHashMap.this.containsNullKey && Double.doubleToLongBits(Float2DoubleLinkedOpenHashMap.this.value[Float2DoubleLinkedOpenHashMap.this.n]) == Double.doubleToLongBits(v)) {
/* 1524 */           Float2DoubleLinkedOpenHashMap.this.removeNullEntry();
/* 1525 */           return true;
/*      */         } 
/* 1527 */         return false;
/*      */       } 
/*      */       
/* 1530 */       float[] key = Float2DoubleLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1533 */       if (Float.floatToIntBits(
/* 1534 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2DoubleLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1536 */         return false; } 
/* 1537 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1538 */         if (Double.doubleToLongBits(Float2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1539 */           Float2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1540 */           return true;
/*      */         } 
/* 1542 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1545 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2DoubleLinkedOpenHashMap.this.mask]) == 0)
/* 1546 */           return false; 
/* 1547 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1548 */           Double.doubleToLongBits(Float2DoubleLinkedOpenHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/* 1549 */           Float2DoubleLinkedOpenHashMap.this.removeEntry(pos);
/* 1550 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1557 */       return Float2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1561 */       Float2DoubleLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2DoubleMap.Entry> iterator(Float2DoubleMap.Entry from) {
/* 1576 */       return new Float2DoubleLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2DoubleMap.Entry> fastIterator() {
/* 1587 */       return new Float2DoubleLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2DoubleMap.Entry> fastIterator(Float2DoubleMap.Entry from) {
/* 1602 */       return new Float2DoubleLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/* 1607 */       for (int i = Float2DoubleLinkedOpenHashMap.this.size, next = Float2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1608 */         int curr = next;
/* 1609 */         next = (int)Float2DoubleLinkedOpenHashMap.this.link[curr];
/* 1610 */         consumer.accept(new AbstractFloat2DoubleMap.BasicEntry(Float2DoubleLinkedOpenHashMap.this.key[curr], Float2DoubleLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2DoubleMap.Entry> consumer) {
/* 1616 */       AbstractFloat2DoubleMap.BasicEntry entry = new AbstractFloat2DoubleMap.BasicEntry();
/* 1617 */       for (int i = Float2DoubleLinkedOpenHashMap.this.size, next = Float2DoubleLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1618 */         int curr = next;
/* 1619 */         next = (int)Float2DoubleLinkedOpenHashMap.this.link[curr];
/* 1620 */         entry.key = Float2DoubleLinkedOpenHashMap.this.key[curr];
/* 1621 */         entry.value = Float2DoubleLinkedOpenHashMap.this.value[curr];
/* 1622 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Float2DoubleSortedMap.FastSortedEntrySet float2DoubleEntrySet() {
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
/*      */     implements FloatListIterator
/*      */   {
/*      */     public KeyIterator(float k) {
/* 1643 */       super(k);
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1647 */       return Float2DoubleLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public float nextFloat() {
/* 1654 */       return Float2DoubleLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet { private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1660 */       return new Float2DoubleLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1664 */       return new Float2DoubleLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1669 */       if (Float2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1670 */         consumer.accept(Float2DoubleLinkedOpenHashMap.this.key[Float2DoubleLinkedOpenHashMap.this.n]); 
/* 1671 */       for (int pos = Float2DoubleLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1672 */         float k = Float2DoubleLinkedOpenHashMap.this.key[pos];
/* 1673 */         if (Float.floatToIntBits(k) != 0)
/* 1674 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1679 */       return Float2DoubleLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1683 */       return Float2DoubleLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1687 */       int oldSize = Float2DoubleLinkedOpenHashMap.this.size;
/* 1688 */       Float2DoubleLinkedOpenHashMap.this.remove(k);
/* 1689 */       return (Float2DoubleLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1693 */       Float2DoubleLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public float firstFloat() {
/* 1697 */       if (Float2DoubleLinkedOpenHashMap.this.size == 0)
/* 1698 */         throw new NoSuchElementException(); 
/* 1699 */       return Float2DoubleLinkedOpenHashMap.this.key[Float2DoubleLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public float lastFloat() {
/* 1703 */       if (Float2DoubleLinkedOpenHashMap.this.size == 0)
/* 1704 */         throw new NoSuchElementException(); 
/* 1705 */       return Float2DoubleLinkedOpenHashMap.this.key[Float2DoubleLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1709 */       return null;
/*      */     }
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1713 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1717 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1721 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
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
/* 1742 */       return Float2DoubleLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double nextDouble() {
/* 1749 */       return Float2DoubleLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1754 */     if (this.values == null)
/* 1755 */       this.values = (DoubleCollection)new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1758 */             return (DoubleIterator)new Float2DoubleLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1762 */             return Float2DoubleLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1766 */             return Float2DoubleLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1770 */             Float2DoubleLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1775 */             if (Float2DoubleLinkedOpenHashMap.this.containsNullKey)
/* 1776 */               consumer.accept(Float2DoubleLinkedOpenHashMap.this.value[Float2DoubleLinkedOpenHashMap.this.n]); 
/* 1777 */             for (int pos = Float2DoubleLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1778 */               if (Float.floatToIntBits(Float2DoubleLinkedOpenHashMap.this.key[pos]) != 0)
/* 1779 */                 consumer.accept(Float2DoubleLinkedOpenHashMap.this.value[pos]); 
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
/* 1847 */     float[] key = this.key;
/* 1848 */     double[] value = this.value;
/* 1849 */     int mask = newN - 1;
/* 1850 */     float[] newKey = new float[newN + 1];
/* 1851 */     double[] newValue = new double[newN + 1];
/* 1852 */     int i = this.first, prev = -1, newPrev = -1;
/* 1853 */     long[] link = this.link;
/* 1854 */     long[] newLink = new long[newN + 1];
/* 1855 */     this.first = -1;
/* 1856 */     for (int j = this.size; j-- != 0; ) {
/* 1857 */       int pos; if (Float.floatToIntBits(key[i]) == 0) {
/* 1858 */         pos = newN;
/*      */       } else {
/* 1860 */         pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1861 */         while (Float.floatToIntBits(newKey[pos]) != 0)
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
/*      */   public Float2DoubleLinkedOpenHashMap clone() {
/*      */     Float2DoubleLinkedOpenHashMap c;
/*      */     try {
/* 1905 */       c = (Float2DoubleLinkedOpenHashMap)super.clone();
/* 1906 */     } catch (CloneNotSupportedException cantHappen) {
/* 1907 */       throw new InternalError();
/*      */     } 
/* 1909 */     c.keys = null;
/* 1910 */     c.values = null;
/* 1911 */     c.entries = null;
/* 1912 */     c.containsNullKey = this.containsNullKey;
/* 1913 */     c.key = (float[])this.key.clone();
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
/* 1931 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1932 */         i++; 
/* 1933 */       t = HashCommon.float2int(this.key[i]);
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
/* 1944 */     float[] key = this.key;
/* 1945 */     double[] value = this.value;
/* 1946 */     MapIterator i = new MapIterator();
/* 1947 */     s.defaultWriteObject();
/* 1948 */     for (int j = this.size; j-- != 0; ) {
/* 1949 */       int e = i.nextEntry();
/* 1950 */       s.writeFloat(key[e]);
/* 1951 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1956 */     s.defaultReadObject();
/* 1957 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1958 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1959 */     this.mask = this.n - 1;
/* 1960 */     float[] key = this.key = new float[this.n + 1];
/* 1961 */     double[] value = this.value = new double[this.n + 1];
/* 1962 */     long[] link = this.link = new long[this.n + 1];
/* 1963 */     int prev = -1;
/* 1964 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1967 */     for (int i = this.size; i-- != 0; ) {
/* 1968 */       int pos; float k = s.readFloat();
/* 1969 */       double v = s.readDouble();
/* 1970 */       if (Float.floatToIntBits(k) == 0) {
/* 1971 */         pos = this.n;
/* 1972 */         this.containsNullKey = true;
/*      */       } else {
/* 1974 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1975 */         while (Float.floatToIntBits(key[pos]) != 0)
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2DoubleLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */