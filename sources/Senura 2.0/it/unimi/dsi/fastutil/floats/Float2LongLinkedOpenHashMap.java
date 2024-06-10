/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2LongLinkedOpenHashMap
/*      */   extends AbstractFloat2LongSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
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
/*      */   protected transient Float2LongSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient LongCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new float[this.n + 1];
/*  162 */     this.value = new long[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongLinkedOpenHashMap() {
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
/*      */   public Float2LongLinkedOpenHashMap(Map<? extends Float, ? extends Long> m, float f) {
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
/*      */   public Float2LongLinkedOpenHashMap(Map<? extends Float, ? extends Long> m) {
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
/*      */   public Float2LongLinkedOpenHashMap(Float2LongMap m, float f) {
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
/*      */   public Float2LongLinkedOpenHashMap(Float2LongMap m) {
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
/*      */   public Float2LongLinkedOpenHashMap(float[] k, long[] v, float f) {
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
/*      */   public Float2LongLinkedOpenHashMap(float[] k, long[] v) {
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
/*      */   public void putAll(Map<? extends Float, ? extends Long> m) {
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
/*      */   private void insert(int pos, float k, long v) {
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
/*      */   public long put(float k, long v) {
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
/*      */   public long addTo(float k, long incr) {
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
/*      */   public long remove(float k) {
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
/*      */   public long getAndMoveToFirst(float k) {
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
/*      */   public long getAndMoveToLast(float k) {
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
/*      */   public long putAndMoveToFirst(float k, long v) {
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
/*      */   public long putAndMoveToLast(float k, long v) {
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
/*      */   public long get(float k) {
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
/*      */   public boolean containsValue(long v) {
/*  798 */     long[] value = this.value;
/*  799 */     float[] key = this.key;
/*  800 */     if (this.containsNullKey && value[this.n] == v)
/*  801 */       return true; 
/*  802 */     for (int i = this.n; i-- != 0;) {
/*  803 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  804 */         return true; 
/*  805 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(float k, long defaultValue) {
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
/*      */   public long putIfAbsent(float k, long v) {
/*  834 */     int pos = find(k);
/*  835 */     if (pos >= 0)
/*  836 */       return this.value[pos]; 
/*  837 */     insert(-pos - 1, k, v);
/*  838 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, long v) {
/*  844 */     if (Float.floatToIntBits(k) == 0) {
/*  845 */       if (this.containsNullKey && v == this.value[this.n]) {
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
/*  859 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  860 */       removeEntry(pos);
/*  861 */       return true;
/*      */     } 
/*      */     while (true) {
/*  864 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  865 */         return false; 
/*  866 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  867 */         removeEntry(pos);
/*  868 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, long oldValue, long v) {
/*  875 */     int pos = find(k);
/*  876 */     if (pos < 0 || oldValue != this.value[pos])
/*  877 */       return false; 
/*  878 */     this.value[pos] = v;
/*  879 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(float k, long v) {
/*  884 */     int pos = find(k);
/*  885 */     if (pos < 0)
/*  886 */       return this.defRetValue; 
/*  887 */     long oldValue = this.value[pos];
/*  888 */     this.value[pos] = v;
/*  889 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(float k, DoubleToLongFunction mappingFunction) {
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
/*      */   public long computeIfAbsentNullable(float k, DoubleFunction<? extends Long> mappingFunction) {
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
/*      */   public long computeIfPresent(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  921 */     Objects.requireNonNull(remappingFunction);
/*  922 */     int pos = find(k);
/*  923 */     if (pos < 0)
/*  924 */       return this.defRetValue; 
/*  925 */     Long newValue = remappingFunction.apply(Float.valueOf(k), Long.valueOf(this.value[pos]));
/*  926 */     if (newValue == null) {
/*  927 */       if (Float.floatToIntBits(k) == 0) {
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
/*      */   public long compute(float k, BiFunction<? super Float, ? super Long, ? extends Long> remappingFunction) {
/*  939 */     Objects.requireNonNull(remappingFunction);
/*  940 */     int pos = find(k);
/*  941 */     Long newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  942 */     if (newValue == null) {
/*  943 */       if (pos >= 0)
/*  944 */         if (Float.floatToIntBits(k) == 0) {
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
/*      */   public long merge(float k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  962 */     Objects.requireNonNull(remappingFunction);
/*  963 */     int pos = find(k);
/*  964 */     if (pos < 0) {
/*  965 */       insert(-pos - 1, k, v);
/*  966 */       return v;
/*      */     } 
/*  968 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  969 */     if (newValue == null) {
/*  970 */       if (Float.floatToIntBits(k) == 0) {
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
/*  991 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2LongMap.Entry, Map.Entry<Float, Long>
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
/*      */     public float getFloatKey() {
/* 1018 */       return Float2LongLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/* 1022 */       return Float2LongLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/* 1026 */       long oldValue = Float2LongLinkedOpenHashMap.this.value[this.index];
/* 1027 */       Float2LongLinkedOpenHashMap.this.value[this.index] = v;
/* 1028 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/* 1038 */       return Float.valueOf(Float2LongLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/* 1048 */       return Long.valueOf(Float2LongLinkedOpenHashMap.this.value[this.index]);
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
/* 1065 */       Map.Entry<Float, Long> e = (Map.Entry<Float, Long>)o;
/* 1066 */       return (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2LongLinkedOpenHashMap.this.value[this.index] == ((Long)e
/* 1067 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1071 */       return HashCommon.float2int(Float2LongLinkedOpenHashMap.this.key[this.index]) ^ 
/* 1072 */         HashCommon.long2int(Float2LongLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1076 */       return Float2LongLinkedOpenHashMap.this.key[this.index] + "=>" + Float2LongLinkedOpenHashMap.this.value[this.index];
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
/*      */   public float firstFloatKey() {
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
/*      */   public float lastFloatKey() {
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
/*      */   public Float2LongSortedMap tailMap(float from) {
/* 1178 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongSortedMap headMap(float to) {
/* 1187 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2LongSortedMap subMap(float from, float to) {
/* 1196 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
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
/* 1239 */       this.next = Float2LongLinkedOpenHashMap.this.first;
/* 1240 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(float from) {
/* 1243 */       if (Float.floatToIntBits(from) == 0) {
/* 1244 */         if (Float2LongLinkedOpenHashMap.this.containsNullKey) {
/* 1245 */           this.next = (int)Float2LongLinkedOpenHashMap.this.link[Float2LongLinkedOpenHashMap.this.n];
/* 1246 */           this.prev = Float2LongLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1249 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1251 */       if (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[Float2LongLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1252 */         this.prev = Float2LongLinkedOpenHashMap.this.last;
/* 1253 */         this.index = Float2LongLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1257 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2LongLinkedOpenHashMap.this.mask;
/*      */       
/* 1259 */       while (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1260 */         if (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1262 */           this.next = (int)Float2LongLinkedOpenHashMap.this.link[pos];
/* 1263 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1266 */         pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask;
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
/* 1284 */         this.index = Float2LongLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1287 */       int pos = Float2LongLinkedOpenHashMap.this.first;
/* 1288 */       this.index = 1;
/* 1289 */       while (pos != this.prev) {
/* 1290 */         pos = (int)Float2LongLinkedOpenHashMap.this.link[pos];
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
/* 1306 */       this.next = (int)Float2LongLinkedOpenHashMap.this.link[this.curr];
/* 1307 */       this.prev = this.curr;
/* 1308 */       if (this.index >= 0)
/* 1309 */         this.index++; 
/* 1310 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1313 */       if (!hasPrevious())
/* 1314 */         throw new NoSuchElementException(); 
/* 1315 */       this.curr = this.prev;
/* 1316 */       this.prev = (int)(Float2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1332 */         this.prev = (int)(Float2LongLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1334 */         this.next = (int)Float2LongLinkedOpenHashMap.this.link[this.curr];
/* 1335 */       }  Float2LongLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       if (this.prev == -1) {
/* 1341 */         Float2LongLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1343 */         Float2LongLinkedOpenHashMap.this.link[this.prev] = Float2LongLinkedOpenHashMap.this.link[this.prev] ^ (Float2LongLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1344 */       }  if (this.next == -1) {
/* 1345 */         Float2LongLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1347 */         Float2LongLinkedOpenHashMap.this.link[this.next] = Float2LongLinkedOpenHashMap.this.link[this.next] ^ (Float2LongLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1348 */       }  int pos = this.curr;
/* 1349 */       this.curr = -1;
/* 1350 */       if (pos == Float2LongLinkedOpenHashMap.this.n) {
/* 1351 */         Float2LongLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1354 */         float[] key = Float2LongLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           float curr;
/*      */           int last;
/* 1358 */           pos = (last = pos) + 1 & Float2LongLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1360 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1361 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1364 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2LongLinkedOpenHashMap.this.mask;
/*      */             
/* 1366 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1368 */             pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1370 */           key[last] = curr;
/* 1371 */           Float2LongLinkedOpenHashMap.this.value[last] = Float2LongLinkedOpenHashMap.this.value[pos];
/* 1372 */           if (this.next == pos)
/* 1373 */             this.next = last; 
/* 1374 */           if (this.prev == pos)
/* 1375 */             this.prev = last; 
/* 1376 */           Float2LongLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1381 */       int i = n;
/* 1382 */       while (i-- != 0 && hasNext())
/* 1383 */         nextEntry(); 
/* 1384 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1387 */       int i = n;
/* 1388 */       while (i-- != 0 && hasPrevious())
/* 1389 */         previousEntry(); 
/* 1390 */       return n - i - 1;
/*      */     }
/*      */     public void set(Float2LongMap.Entry ok) {
/* 1393 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Float2LongMap.Entry ok) {
/* 1396 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Float2LongMap.Entry> { private Float2LongLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1404 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry next() {
/* 1408 */       return this.entry = new Float2LongLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry previous() {
/* 1412 */       return this.entry = new Float2LongLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1416 */       super.remove();
/* 1417 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1421 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Float2LongMap.Entry> { final Float2LongLinkedOpenHashMap.MapEntry entry = new Float2LongLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1425 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry next() {
/* 1429 */       this.entry.index = nextEntry();
/* 1430 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Float2LongLinkedOpenHashMap.MapEntry previous() {
/* 1434 */       this.entry.index = previousEntry();
/* 1435 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2LongMap.Entry> implements Float2LongSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Float2LongMap.Entry> iterator() {
/* 1441 */       return (ObjectBidirectionalIterator<Float2LongMap.Entry>)new Float2LongLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Float2LongMap.Entry> comparator() {
/* 1445 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2LongMap.Entry> subSet(Float2LongMap.Entry fromElement, Float2LongMap.Entry toElement) {
/* 1450 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2LongMap.Entry> headSet(Float2LongMap.Entry toElement) {
/* 1454 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2LongMap.Entry> tailSet(Float2LongMap.Entry fromElement) {
/* 1458 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Float2LongMap.Entry first() {
/* 1462 */       if (Float2LongLinkedOpenHashMap.this.size == 0)
/* 1463 */         throw new NoSuchElementException(); 
/* 1464 */       return new Float2LongLinkedOpenHashMap.MapEntry(Float2LongLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Float2LongMap.Entry last() {
/* 1468 */       if (Float2LongLinkedOpenHashMap.this.size == 0)
/* 1469 */         throw new NoSuchElementException(); 
/* 1470 */       return new Float2LongLinkedOpenHashMap.MapEntry(Float2LongLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1475 */       if (!(o instanceof Map.Entry))
/* 1476 */         return false; 
/* 1477 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1478 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1479 */         return false; 
/* 1480 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1481 */         return false; 
/* 1482 */       float k = ((Float)e.getKey()).floatValue();
/* 1483 */       long v = ((Long)e.getValue()).longValue();
/* 1484 */       if (Float.floatToIntBits(k) == 0) {
/* 1485 */         return (Float2LongLinkedOpenHashMap.this.containsNullKey && Float2LongLinkedOpenHashMap.this.value[Float2LongLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1487 */       float[] key = Float2LongLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1490 */       if (Float.floatToIntBits(
/* 1491 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1493 */         return false; } 
/* 1494 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1495 */         return (Float2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1498 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask]) == 0)
/* 1499 */           return false; 
/* 1500 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1501 */           return (Float2LongLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1507 */       if (!(o instanceof Map.Entry))
/* 1508 */         return false; 
/* 1509 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1510 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1511 */         return false; 
/* 1512 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/* 1513 */         return false; 
/* 1514 */       float k = ((Float)e.getKey()).floatValue();
/* 1515 */       long v = ((Long)e.getValue()).longValue();
/* 1516 */       if (Float.floatToIntBits(k) == 0) {
/* 1517 */         if (Float2LongLinkedOpenHashMap.this.containsNullKey && Float2LongLinkedOpenHashMap.this.value[Float2LongLinkedOpenHashMap.this.n] == v) {
/* 1518 */           Float2LongLinkedOpenHashMap.this.removeNullEntry();
/* 1519 */           return true;
/*      */         } 
/* 1521 */         return false;
/*      */       } 
/*      */       
/* 1524 */       float[] key = Float2LongLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1527 */       if (Float.floatToIntBits(
/* 1528 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2LongLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1530 */         return false; } 
/* 1531 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1532 */         if (Float2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1533 */           Float2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1534 */           return true;
/*      */         } 
/* 1536 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1539 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2LongLinkedOpenHashMap.this.mask]) == 0)
/* 1540 */           return false; 
/* 1541 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1542 */           Float2LongLinkedOpenHashMap.this.value[pos] == v) {
/* 1543 */           Float2LongLinkedOpenHashMap.this.removeEntry(pos);
/* 1544 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1551 */       return Float2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1555 */       Float2LongLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2LongMap.Entry> iterator(Float2LongMap.Entry from) {
/* 1570 */       return new Float2LongLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2LongMap.Entry> fastIterator() {
/* 1581 */       return new Float2LongLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2LongMap.Entry> fastIterator(Float2LongMap.Entry from) {
/* 1596 */       return new Float2LongLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2LongMap.Entry> consumer) {
/* 1601 */       for (int i = Float2LongLinkedOpenHashMap.this.size, next = Float2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1602 */         int curr = next;
/* 1603 */         next = (int)Float2LongLinkedOpenHashMap.this.link[curr];
/* 1604 */         consumer.accept(new AbstractFloat2LongMap.BasicEntry(Float2LongLinkedOpenHashMap.this.key[curr], Float2LongLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2LongMap.Entry> consumer) {
/* 1610 */       AbstractFloat2LongMap.BasicEntry entry = new AbstractFloat2LongMap.BasicEntry();
/* 1611 */       for (int i = Float2LongLinkedOpenHashMap.this.size, next = Float2LongLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1612 */         int curr = next;
/* 1613 */         next = (int)Float2LongLinkedOpenHashMap.this.link[curr];
/* 1614 */         entry.key = Float2LongLinkedOpenHashMap.this.key[curr];
/* 1615 */         entry.value = Float2LongLinkedOpenHashMap.this.value[curr];
/* 1616 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Float2LongSortedMap.FastSortedEntrySet float2LongEntrySet() {
/* 1622 */     if (this.entries == null)
/* 1623 */       this.entries = new MapEntrySet(); 
/* 1624 */     return this.entries;
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
/* 1637 */       super(k);
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1641 */       return Float2LongLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public float nextFloat() {
/* 1648 */       return Float2LongLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet { private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1654 */       return new Float2LongLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1658 */       return new Float2LongLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1663 */       if (Float2LongLinkedOpenHashMap.this.containsNullKey)
/* 1664 */         consumer.accept(Float2LongLinkedOpenHashMap.this.key[Float2LongLinkedOpenHashMap.this.n]); 
/* 1665 */       for (int pos = Float2LongLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1666 */         float k = Float2LongLinkedOpenHashMap.this.key[pos];
/* 1667 */         if (Float.floatToIntBits(k) != 0)
/* 1668 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1673 */       return Float2LongLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1677 */       return Float2LongLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1681 */       int oldSize = Float2LongLinkedOpenHashMap.this.size;
/* 1682 */       Float2LongLinkedOpenHashMap.this.remove(k);
/* 1683 */       return (Float2LongLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1687 */       Float2LongLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public float firstFloat() {
/* 1691 */       if (Float2LongLinkedOpenHashMap.this.size == 0)
/* 1692 */         throw new NoSuchElementException(); 
/* 1693 */       return Float2LongLinkedOpenHashMap.this.key[Float2LongLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public float lastFloat() {
/* 1697 */       if (Float2LongLinkedOpenHashMap.this.size == 0)
/* 1698 */         throw new NoSuchElementException(); 
/* 1699 */       return Float2LongLinkedOpenHashMap.this.key[Float2LongLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1703 */       return null;
/*      */     }
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1707 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1711 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1715 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
/* 1720 */     if (this.keys == null)
/* 1721 */       this.keys = new KeySet(); 
/* 1722 */     return this.keys;
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
/* 1736 */       return Float2LongLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long nextLong() {
/* 1743 */       return Float2LongLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1748 */     if (this.values == null)
/* 1749 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1752 */             return (LongIterator)new Float2LongLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1756 */             return Float2LongLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1760 */             return Float2LongLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1764 */             Float2LongLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1769 */             if (Float2LongLinkedOpenHashMap.this.containsNullKey)
/* 1770 */               consumer.accept(Float2LongLinkedOpenHashMap.this.value[Float2LongLinkedOpenHashMap.this.n]); 
/* 1771 */             for (int pos = Float2LongLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1772 */               if (Float.floatToIntBits(Float2LongLinkedOpenHashMap.this.key[pos]) != 0)
/* 1773 */                 consumer.accept(Float2LongLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1776 */     return this.values;
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
/* 1793 */     return trim(this.size);
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
/* 1817 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1818 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1819 */       return true; 
/*      */     try {
/* 1821 */       rehash(l);
/* 1822 */     } catch (OutOfMemoryError cantDoIt) {
/* 1823 */       return false;
/*      */     } 
/* 1825 */     return true;
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
/* 1841 */     float[] key = this.key;
/* 1842 */     long[] value = this.value;
/* 1843 */     int mask = newN - 1;
/* 1844 */     float[] newKey = new float[newN + 1];
/* 1845 */     long[] newValue = new long[newN + 1];
/* 1846 */     int i = this.first, prev = -1, newPrev = -1;
/* 1847 */     long[] link = this.link;
/* 1848 */     long[] newLink = new long[newN + 1];
/* 1849 */     this.first = -1;
/* 1850 */     for (int j = this.size; j-- != 0; ) {
/* 1851 */       int pos; if (Float.floatToIntBits(key[i]) == 0) {
/* 1852 */         pos = newN;
/*      */       } else {
/* 1854 */         pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1855 */         while (Float.floatToIntBits(newKey[pos]) != 0)
/* 1856 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1858 */       newKey[pos] = key[i];
/* 1859 */       newValue[pos] = value[i];
/* 1860 */       if (prev != -1) {
/* 1861 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1862 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1863 */         newPrev = pos;
/*      */       } else {
/* 1865 */         newPrev = this.first = pos;
/*      */         
/* 1867 */         newLink[pos] = -1L;
/*      */       } 
/* 1869 */       int t = i;
/* 1870 */       i = (int)link[i];
/* 1871 */       prev = t;
/*      */     } 
/* 1873 */     this.link = newLink;
/* 1874 */     this.last = newPrev;
/* 1875 */     if (newPrev != -1)
/*      */     {
/* 1877 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1878 */     this.n = newN;
/* 1879 */     this.mask = mask;
/* 1880 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1881 */     this.key = newKey;
/* 1882 */     this.value = newValue;
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
/*      */   public Float2LongLinkedOpenHashMap clone() {
/*      */     Float2LongLinkedOpenHashMap c;
/*      */     try {
/* 1899 */       c = (Float2LongLinkedOpenHashMap)super.clone();
/* 1900 */     } catch (CloneNotSupportedException cantHappen) {
/* 1901 */       throw new InternalError();
/*      */     } 
/* 1903 */     c.keys = null;
/* 1904 */     c.values = null;
/* 1905 */     c.entries = null;
/* 1906 */     c.containsNullKey = this.containsNullKey;
/* 1907 */     c.key = (float[])this.key.clone();
/* 1908 */     c.value = (long[])this.value.clone();
/* 1909 */     c.link = (long[])this.link.clone();
/* 1910 */     return c;
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
/* 1923 */     int h = 0;
/* 1924 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1925 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1926 */         i++; 
/* 1927 */       t = HashCommon.float2int(this.key[i]);
/* 1928 */       t ^= HashCommon.long2int(this.value[i]);
/* 1929 */       h += t;
/* 1930 */       i++;
/*      */     } 
/*      */     
/* 1933 */     if (this.containsNullKey)
/* 1934 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1935 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1938 */     float[] key = this.key;
/* 1939 */     long[] value = this.value;
/* 1940 */     MapIterator i = new MapIterator();
/* 1941 */     s.defaultWriteObject();
/* 1942 */     for (int j = this.size; j-- != 0; ) {
/* 1943 */       int e = i.nextEntry();
/* 1944 */       s.writeFloat(key[e]);
/* 1945 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1950 */     s.defaultReadObject();
/* 1951 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1952 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1953 */     this.mask = this.n - 1;
/* 1954 */     float[] key = this.key = new float[this.n + 1];
/* 1955 */     long[] value = this.value = new long[this.n + 1];
/* 1956 */     long[] link = this.link = new long[this.n + 1];
/* 1957 */     int prev = -1;
/* 1958 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1961 */     for (int i = this.size; i-- != 0; ) {
/* 1962 */       int pos; float k = s.readFloat();
/* 1963 */       long v = s.readLong();
/* 1964 */       if (Float.floatToIntBits(k) == 0) {
/* 1965 */         pos = this.n;
/* 1966 */         this.containsNullKey = true;
/*      */       } else {
/* 1968 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1969 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1970 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1972 */       key[pos] = k;
/* 1973 */       value[pos] = v;
/* 1974 */       if (this.first != -1) {
/* 1975 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1976 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1977 */         prev = pos; continue;
/*      */       } 
/* 1979 */       prev = this.first = pos;
/*      */       
/* 1981 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1984 */     this.last = prev;
/* 1985 */     if (prev != -1)
/*      */     {
/* 1987 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2LongLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */