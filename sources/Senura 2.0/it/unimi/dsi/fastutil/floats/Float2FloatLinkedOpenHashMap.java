/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
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
/*      */ public class Float2FloatLinkedOpenHashMap
/*      */   extends AbstractFloat2FloatSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
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
/*      */   protected transient Float2FloatSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new float[this.n + 1];
/*  162 */     this.value = new float[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatLinkedOpenHashMap() {
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
/*      */   public Float2FloatLinkedOpenHashMap(Map<? extends Float, ? extends Float> m, float f) {
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
/*      */   public Float2FloatLinkedOpenHashMap(Map<? extends Float, ? extends Float> m) {
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
/*      */   public Float2FloatLinkedOpenHashMap(Float2FloatMap m, float f) {
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
/*      */   public Float2FloatLinkedOpenHashMap(Float2FloatMap m) {
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
/*      */   public Float2FloatLinkedOpenHashMap(float[] k, float[] v, float f) {
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
/*      */   public Float2FloatLinkedOpenHashMap(float[] k, float[] v) {
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
/*      */   public void putAll(Map<? extends Float, ? extends Float> m) {
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
/*      */   private void insert(int pos, float k, float v) {
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
/*      */   public float put(float k, float v) {
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
/*      */   public float addTo(float k, float incr) {
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
/*      */   public float remove(float k) {
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
/*      */   public float getAndMoveToFirst(float k) {
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
/*      */   public float getAndMoveToLast(float k) {
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
/*      */   public float putAndMoveToFirst(float k, float v) {
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
/*      */   public float putAndMoveToLast(float k, float v) {
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
/*      */   public float get(float k) {
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
/*      */   public boolean containsValue(float v) {
/*  798 */     float[] value = this.value;
/*  799 */     float[] key = this.key;
/*  800 */     if (this.containsNullKey && Float.floatToIntBits(value[this.n]) == Float.floatToIntBits(v))
/*  801 */       return true; 
/*  802 */     for (int i = this.n; i-- != 0;) {
/*  803 */       if (Float.floatToIntBits(key[i]) != 0 && Float.floatToIntBits(value[i]) == Float.floatToIntBits(v))
/*  804 */         return true; 
/*  805 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOrDefault(float k, float defaultValue) {
/*  811 */     if (Float.floatToIntBits(k) == 0) {
/*  812 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  814 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  817 */     if (Float.floatToIntBits(
/*  818 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  820 */       return defaultValue; } 
/*  821 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  822 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  825 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  826 */         return defaultValue; 
/*  827 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  828 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public float putIfAbsent(float k, float v) {
/*  834 */     int pos = find(k);
/*  835 */     if (pos >= 0)
/*  836 */       return this.value[pos]; 
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, float v) {
/*  844 */     if (Float.floatToIntBits(k) == 0) {
/*  845 */       if (this.containsNullKey && Float.floatToIntBits(v) == Float.floatToIntBits(this.value[this.n])) {
/*  846 */         removeNullEntry();
/*  847 */         return true;
/*      */       } 
/*  849 */       return false;
/*      */     } 
/*      */     
/*  852 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  855 */     if (Float.floatToIntBits(
/*  856 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  858 */       return false; } 
/*  859 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  860 */       Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  861 */       removeEntry(pos);
/*  862 */       return true;
/*      */     } 
/*      */     while (true) {
/*  865 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  866 */         return false; 
/*  867 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && 
/*  868 */         Float.floatToIntBits(v) == Float.floatToIntBits(this.value[pos])) {
/*  869 */         removeEntry(pos);
/*  870 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, float oldValue, float v) {
/*  877 */     int pos = find(k);
/*  878 */     if (pos < 0 || Float.floatToIntBits(oldValue) != Float.floatToIntBits(this.value[pos]))
/*  879 */       return false; 
/*  880 */     this.value[pos] = v;
/*  881 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public float replace(float k, float v) {
/*  886 */     int pos = find(k);
/*  887 */     if (pos < 0)
/*  888 */       return this.defRetValue; 
/*  889 */     float oldValue = this.value[pos];
/*  890 */     this.value[pos] = v;
/*  891 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public float computeIfAbsent(float k, DoubleUnaryOperator mappingFunction) {
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
/*      */   public float computeIfAbsentNullable(float k, DoubleFunction<? extends Float> mappingFunction) {
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
/*      */   public float computeIfPresent(float k, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  923 */     Objects.requireNonNull(remappingFunction);
/*  924 */     int pos = find(k);
/*  925 */     if (pos < 0)
/*  926 */       return this.defRetValue; 
/*  927 */     Float newValue = remappingFunction.apply(Float.valueOf(k), Float.valueOf(this.value[pos]));
/*  928 */     if (newValue == null) {
/*  929 */       if (Float.floatToIntBits(k) == 0) {
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
/*      */   public float compute(float k, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  941 */     Objects.requireNonNull(remappingFunction);
/*  942 */     int pos = find(k);
/*  943 */     Float newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Float.valueOf(this.value[pos]) : null);
/*  944 */     if (newValue == null) {
/*  945 */       if (pos >= 0)
/*  946 */         if (Float.floatToIntBits(k) == 0) {
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
/*      */   public float merge(float k, float v, BiFunction<? super Float, ? super Float, ? extends Float> remappingFunction) {
/*  964 */     Objects.requireNonNull(remappingFunction);
/*  965 */     int pos = find(k);
/*  966 */     if (pos < 0) {
/*  967 */       insert(-pos - 1, k, v);
/*  968 */       return v;
/*      */     } 
/*  970 */     Float newValue = remappingFunction.apply(Float.valueOf(this.value[pos]), Float.valueOf(v));
/*  971 */     if (newValue == null) {
/*  972 */       if (Float.floatToIntBits(k) == 0) {
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
/*  993 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2FloatMap.Entry, Map.Entry<Float, Float>
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
/*      */     public float getFloatKey() {
/* 1020 */       return Float2FloatLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public float getFloatValue() {
/* 1024 */       return Float2FloatLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public float setValue(float v) {
/* 1028 */       float oldValue = Float2FloatLinkedOpenHashMap.this.value[this.index];
/* 1029 */       Float2FloatLinkedOpenHashMap.this.value[this.index] = v;
/* 1030 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/* 1040 */       return Float.valueOf(Float2FloatLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getValue() {
/* 1050 */       return Float.valueOf(Float2FloatLinkedOpenHashMap.this.value[this.index]);
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
/* 1067 */       Map.Entry<Float, Float> e = (Map.Entry<Float, Float>)o;
/* 1068 */       return (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && 
/* 1069 */         Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.value[this.index]) == Float.floatToIntBits(((Float)e.getValue()).floatValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1073 */       return HashCommon.float2int(Float2FloatLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1074 */         HashCommon.float2int(Float2FloatLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1078 */       return Float2FloatLinkedOpenHashMap.this.key[this.index] + "=>" + Float2FloatLinkedOpenHashMap.this.value[this.index];
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
/*      */   public float firstFloatKey() {
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
/*      */   public float lastFloatKey() {
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
/*      */   public Float2FloatSortedMap tailMap(float from) {
/* 1180 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatSortedMap headMap(float to) {
/* 1189 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2FloatSortedMap subMap(float from, float to) {
/* 1198 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
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
/* 1241 */       this.next = Float2FloatLinkedOpenHashMap.this.first;
/* 1242 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(float from) {
/* 1245 */       if (Float.floatToIntBits(from) == 0) {
/* 1246 */         if (Float2FloatLinkedOpenHashMap.this.containsNullKey) {
/* 1247 */           this.next = (int)Float2FloatLinkedOpenHashMap.this.link[Float2FloatLinkedOpenHashMap.this.n];
/* 1248 */           this.prev = Float2FloatLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1251 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1253 */       if (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.key[Float2FloatLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1254 */         this.prev = Float2FloatLinkedOpenHashMap.this.last;
/* 1255 */         this.index = Float2FloatLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1259 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2FloatLinkedOpenHashMap.this.mask;
/*      */       
/* 1261 */       while (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1262 */         if (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1264 */           this.next = (int)Float2FloatLinkedOpenHashMap.this.link[pos];
/* 1265 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1268 */         pos = pos + 1 & Float2FloatLinkedOpenHashMap.this.mask;
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
/* 1286 */         this.index = Float2FloatLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1289 */       int pos = Float2FloatLinkedOpenHashMap.this.first;
/* 1290 */       this.index = 1;
/* 1291 */       while (pos != this.prev) {
/* 1292 */         pos = (int)Float2FloatLinkedOpenHashMap.this.link[pos];
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
/* 1308 */       this.next = (int)Float2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1309 */       this.prev = this.curr;
/* 1310 */       if (this.index >= 0)
/* 1311 */         this.index++; 
/* 1312 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1315 */       if (!hasPrevious())
/* 1316 */         throw new NoSuchElementException(); 
/* 1317 */       this.curr = this.prev;
/* 1318 */       this.prev = (int)(Float2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1334 */         this.prev = (int)(Float2FloatLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1336 */         this.next = (int)Float2FloatLinkedOpenHashMap.this.link[this.curr];
/* 1337 */       }  Float2FloatLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1342 */       if (this.prev == -1) {
/* 1343 */         Float2FloatLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1345 */         Float2FloatLinkedOpenHashMap.this.link[this.prev] = Float2FloatLinkedOpenHashMap.this.link[this.prev] ^ (Float2FloatLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1346 */       }  if (this.next == -1) {
/* 1347 */         Float2FloatLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1349 */         Float2FloatLinkedOpenHashMap.this.link[this.next] = Float2FloatLinkedOpenHashMap.this.link[this.next] ^ (Float2FloatLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1350 */       }  int pos = this.curr;
/* 1351 */       this.curr = -1;
/* 1352 */       if (pos == Float2FloatLinkedOpenHashMap.this.n) {
/* 1353 */         Float2FloatLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1356 */         float[] key = Float2FloatLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           float curr;
/*      */           int last;
/* 1360 */           pos = (last = pos) + 1 & Float2FloatLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1362 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1363 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1366 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2FloatLinkedOpenHashMap.this.mask;
/*      */             
/* 1368 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1370 */             pos = pos + 1 & Float2FloatLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1372 */           key[last] = curr;
/* 1373 */           Float2FloatLinkedOpenHashMap.this.value[last] = Float2FloatLinkedOpenHashMap.this.value[pos];
/* 1374 */           if (this.next == pos)
/* 1375 */             this.next = last; 
/* 1376 */           if (this.prev == pos)
/* 1377 */             this.prev = last; 
/* 1378 */           Float2FloatLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1383 */       int i = n;
/* 1384 */       while (i-- != 0 && hasNext())
/* 1385 */         nextEntry(); 
/* 1386 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1389 */       int i = n;
/* 1390 */       while (i-- != 0 && hasPrevious())
/* 1391 */         previousEntry(); 
/* 1392 */       return n - i - 1;
/*      */     }
/*      */     public void set(Float2FloatMap.Entry ok) {
/* 1395 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Float2FloatMap.Entry ok) {
/* 1398 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Float2FloatMap.Entry> { private Float2FloatLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1406 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2FloatLinkedOpenHashMap.MapEntry next() {
/* 1410 */       return this.entry = new Float2FloatLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Float2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1414 */       return this.entry = new Float2FloatLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1418 */       super.remove();
/* 1419 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1423 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Float2FloatMap.Entry> { final Float2FloatLinkedOpenHashMap.MapEntry entry = new Float2FloatLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1427 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2FloatLinkedOpenHashMap.MapEntry next() {
/* 1431 */       this.entry.index = nextEntry();
/* 1432 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Float2FloatLinkedOpenHashMap.MapEntry previous() {
/* 1436 */       this.entry.index = previousEntry();
/* 1437 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2FloatMap.Entry> implements Float2FloatSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Float2FloatMap.Entry> iterator() {
/* 1445 */       return (ObjectBidirectionalIterator<Float2FloatMap.Entry>)new Float2FloatLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Float2FloatMap.Entry> comparator() {
/* 1449 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2FloatMap.Entry> subSet(Float2FloatMap.Entry fromElement, Float2FloatMap.Entry toElement) {
/* 1454 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2FloatMap.Entry> headSet(Float2FloatMap.Entry toElement) {
/* 1458 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2FloatMap.Entry> tailSet(Float2FloatMap.Entry fromElement) {
/* 1462 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Float2FloatMap.Entry first() {
/* 1466 */       if (Float2FloatLinkedOpenHashMap.this.size == 0)
/* 1467 */         throw new NoSuchElementException(); 
/* 1468 */       return new Float2FloatLinkedOpenHashMap.MapEntry(Float2FloatLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Float2FloatMap.Entry last() {
/* 1472 */       if (Float2FloatLinkedOpenHashMap.this.size == 0)
/* 1473 */         throw new NoSuchElementException(); 
/* 1474 */       return new Float2FloatLinkedOpenHashMap.MapEntry(Float2FloatLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1479 */       if (!(o instanceof Map.Entry))
/* 1480 */         return false; 
/* 1481 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1482 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1483 */         return false; 
/* 1484 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1485 */         return false; 
/* 1486 */       float k = ((Float)e.getKey()).floatValue();
/* 1487 */       float v = ((Float)e.getValue()).floatValue();
/* 1488 */       if (Float.floatToIntBits(k) == 0) {
/* 1489 */         return (Float2FloatLinkedOpenHashMap.this.containsNullKey && 
/* 1490 */           Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.value[Float2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v));
/*      */       }
/* 1492 */       float[] key = Float2FloatLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1495 */       if (Float.floatToIntBits(
/* 1496 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1498 */         return false; } 
/* 1499 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1500 */         return (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */       }
/*      */       while (true) {
/* 1503 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1504 */           return false; 
/* 1505 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1506 */           return (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1512 */       if (!(o instanceof Map.Entry))
/* 1513 */         return false; 
/* 1514 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1515 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1516 */         return false; 
/* 1517 */       if (e.getValue() == null || !(e.getValue() instanceof Float))
/* 1518 */         return false; 
/* 1519 */       float k = ((Float)e.getKey()).floatValue();
/* 1520 */       float v = ((Float)e.getValue()).floatValue();
/* 1521 */       if (Float.floatToIntBits(k) == 0) {
/* 1522 */         if (Float2FloatLinkedOpenHashMap.this.containsNullKey && Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.value[Float2FloatLinkedOpenHashMap.this.n]) == Float.floatToIntBits(v)) {
/* 1523 */           Float2FloatLinkedOpenHashMap.this.removeNullEntry();
/* 1524 */           return true;
/*      */         } 
/* 1526 */         return false;
/*      */       } 
/*      */       
/* 1529 */       float[] key = Float2FloatLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1532 */       if (Float.floatToIntBits(
/* 1533 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2FloatLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1535 */         return false; } 
/* 1536 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1537 */         if (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1538 */           Float2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1539 */           return true;
/*      */         } 
/* 1541 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1544 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2FloatLinkedOpenHashMap.this.mask]) == 0)
/* 1545 */           return false; 
/* 1546 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1547 */           Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.value[pos]) == Float.floatToIntBits(v)) {
/* 1548 */           Float2FloatLinkedOpenHashMap.this.removeEntry(pos);
/* 1549 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1556 */       return Float2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1560 */       Float2FloatLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2FloatMap.Entry> iterator(Float2FloatMap.Entry from) {
/* 1575 */       return new Float2FloatLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2FloatMap.Entry> fastIterator() {
/* 1586 */       return new Float2FloatLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2FloatMap.Entry> fastIterator(Float2FloatMap.Entry from) {
/* 1601 */       return new Float2FloatLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2FloatMap.Entry> consumer) {
/* 1606 */       for (int i = Float2FloatLinkedOpenHashMap.this.size, next = Float2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1607 */         int curr = next;
/* 1608 */         next = (int)Float2FloatLinkedOpenHashMap.this.link[curr];
/* 1609 */         consumer.accept(new AbstractFloat2FloatMap.BasicEntry(Float2FloatLinkedOpenHashMap.this.key[curr], Float2FloatLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2FloatMap.Entry> consumer) {
/* 1615 */       AbstractFloat2FloatMap.BasicEntry entry = new AbstractFloat2FloatMap.BasicEntry();
/* 1616 */       for (int i = Float2FloatLinkedOpenHashMap.this.size, next = Float2FloatLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1617 */         int curr = next;
/* 1618 */         next = (int)Float2FloatLinkedOpenHashMap.this.link[curr];
/* 1619 */         entry.key = Float2FloatLinkedOpenHashMap.this.key[curr];
/* 1620 */         entry.value = Float2FloatLinkedOpenHashMap.this.value[curr];
/* 1621 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Float2FloatSortedMap.FastSortedEntrySet float2FloatEntrySet() {
/* 1627 */     if (this.entries == null)
/* 1628 */       this.entries = new MapEntrySet(); 
/* 1629 */     return this.entries;
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
/* 1642 */       super(k);
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1646 */       return Float2FloatLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public float nextFloat() {
/* 1653 */       return Float2FloatLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet { private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1659 */       return new Float2FloatLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1663 */       return new Float2FloatLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1668 */       if (Float2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1669 */         consumer.accept(Float2FloatLinkedOpenHashMap.this.key[Float2FloatLinkedOpenHashMap.this.n]); 
/* 1670 */       for (int pos = Float2FloatLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1671 */         float k = Float2FloatLinkedOpenHashMap.this.key[pos];
/* 1672 */         if (Float.floatToIntBits(k) != 0)
/* 1673 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1678 */       return Float2FloatLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1682 */       return Float2FloatLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1686 */       int oldSize = Float2FloatLinkedOpenHashMap.this.size;
/* 1687 */       Float2FloatLinkedOpenHashMap.this.remove(k);
/* 1688 */       return (Float2FloatLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1692 */       Float2FloatLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public float firstFloat() {
/* 1696 */       if (Float2FloatLinkedOpenHashMap.this.size == 0)
/* 1697 */         throw new NoSuchElementException(); 
/* 1698 */       return Float2FloatLinkedOpenHashMap.this.key[Float2FloatLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public float lastFloat() {
/* 1702 */       if (Float2FloatLinkedOpenHashMap.this.size == 0)
/* 1703 */         throw new NoSuchElementException(); 
/* 1704 */       return Float2FloatLinkedOpenHashMap.this.key[Float2FloatLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1708 */       return null;
/*      */     }
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1712 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1716 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1720 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
/* 1725 */     if (this.keys == null)
/* 1726 */       this.keys = new KeySet(); 
/* 1727 */     return this.keys;
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
/* 1741 */       return Float2FloatLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float nextFloat() {
/* 1748 */       return Float2FloatLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public FloatCollection values() {
/* 1753 */     if (this.values == null)
/* 1754 */       this.values = new AbstractFloatCollection()
/*      */         {
/*      */           public FloatIterator iterator() {
/* 1757 */             return new Float2FloatLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1761 */             return Float2FloatLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(float v) {
/* 1765 */             return Float2FloatLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1769 */             Float2FloatLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(DoubleConsumer consumer) {
/* 1774 */             if (Float2FloatLinkedOpenHashMap.this.containsNullKey)
/* 1775 */               consumer.accept(Float2FloatLinkedOpenHashMap.this.value[Float2FloatLinkedOpenHashMap.this.n]); 
/* 1776 */             for (int pos = Float2FloatLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1777 */               if (Float.floatToIntBits(Float2FloatLinkedOpenHashMap.this.key[pos]) != 0)
/* 1778 */                 consumer.accept(Float2FloatLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1781 */     return this.values;
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
/* 1798 */     return trim(this.size);
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
/* 1822 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1823 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1824 */       return true; 
/*      */     try {
/* 1826 */       rehash(l);
/* 1827 */     } catch (OutOfMemoryError cantDoIt) {
/* 1828 */       return false;
/*      */     } 
/* 1830 */     return true;
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
/* 1846 */     float[] key = this.key;
/* 1847 */     float[] value = this.value;
/* 1848 */     int mask = newN - 1;
/* 1849 */     float[] newKey = new float[newN + 1];
/* 1850 */     float[] newValue = new float[newN + 1];
/* 1851 */     int i = this.first, prev = -1, newPrev = -1;
/* 1852 */     long[] link = this.link;
/* 1853 */     long[] newLink = new long[newN + 1];
/* 1854 */     this.first = -1;
/* 1855 */     for (int j = this.size; j-- != 0; ) {
/* 1856 */       int pos; if (Float.floatToIntBits(key[i]) == 0) {
/* 1857 */         pos = newN;
/*      */       } else {
/* 1859 */         pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1860 */         while (Float.floatToIntBits(newKey[pos]) != 0)
/* 1861 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1863 */       newKey[pos] = key[i];
/* 1864 */       newValue[pos] = value[i];
/* 1865 */       if (prev != -1) {
/* 1866 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1867 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1868 */         newPrev = pos;
/*      */       } else {
/* 1870 */         newPrev = this.first = pos;
/*      */         
/* 1872 */         newLink[pos] = -1L;
/*      */       } 
/* 1874 */       int t = i;
/* 1875 */       i = (int)link[i];
/* 1876 */       prev = t;
/*      */     } 
/* 1878 */     this.link = newLink;
/* 1879 */     this.last = newPrev;
/* 1880 */     if (newPrev != -1)
/*      */     {
/* 1882 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1883 */     this.n = newN;
/* 1884 */     this.mask = mask;
/* 1885 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1886 */     this.key = newKey;
/* 1887 */     this.value = newValue;
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
/*      */   public Float2FloatLinkedOpenHashMap clone() {
/*      */     Float2FloatLinkedOpenHashMap c;
/*      */     try {
/* 1904 */       c = (Float2FloatLinkedOpenHashMap)super.clone();
/* 1905 */     } catch (CloneNotSupportedException cantHappen) {
/* 1906 */       throw new InternalError();
/*      */     } 
/* 1908 */     c.keys = null;
/* 1909 */     c.values = null;
/* 1910 */     c.entries = null;
/* 1911 */     c.containsNullKey = this.containsNullKey;
/* 1912 */     c.key = (float[])this.key.clone();
/* 1913 */     c.value = (float[])this.value.clone();
/* 1914 */     c.link = (long[])this.link.clone();
/* 1915 */     return c;
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
/* 1928 */     int h = 0;
/* 1929 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1930 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1931 */         i++; 
/* 1932 */       t = HashCommon.float2int(this.key[i]);
/* 1933 */       t ^= HashCommon.float2int(this.value[i]);
/* 1934 */       h += t;
/* 1935 */       i++;
/*      */     } 
/*      */     
/* 1938 */     if (this.containsNullKey)
/* 1939 */       h += HashCommon.float2int(this.value[this.n]); 
/* 1940 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1943 */     float[] key = this.key;
/* 1944 */     float[] value = this.value;
/* 1945 */     MapIterator i = new MapIterator();
/* 1946 */     s.defaultWriteObject();
/* 1947 */     for (int j = this.size; j-- != 0; ) {
/* 1948 */       int e = i.nextEntry();
/* 1949 */       s.writeFloat(key[e]);
/* 1950 */       s.writeFloat(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1955 */     s.defaultReadObject();
/* 1956 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1957 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1958 */     this.mask = this.n - 1;
/* 1959 */     float[] key = this.key = new float[this.n + 1];
/* 1960 */     float[] value = this.value = new float[this.n + 1];
/* 1961 */     long[] link = this.link = new long[this.n + 1];
/* 1962 */     int prev = -1;
/* 1963 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1966 */     for (int i = this.size; i-- != 0; ) {
/* 1967 */       int pos; float k = s.readFloat();
/* 1968 */       float v = s.readFloat();
/* 1969 */       if (Float.floatToIntBits(k) == 0) {
/* 1970 */         pos = this.n;
/* 1971 */         this.containsNullKey = true;
/*      */       } else {
/* 1973 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1974 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1975 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1977 */       key[pos] = k;
/* 1978 */       value[pos] = v;
/* 1979 */       if (this.first != -1) {
/* 1980 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1981 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1982 */         prev = pos; continue;
/*      */       } 
/* 1984 */       prev = this.first = pos;
/*      */       
/* 1986 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1989 */     this.last = prev;
/* 1990 */     if (prev != -1)
/*      */     {
/* 1992 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2FloatLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */