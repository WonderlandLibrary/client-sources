/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2CharLinkedOpenHashMap
/*      */   extends AbstractFloat2CharSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
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
/*      */   protected transient Float2CharSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient CharCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharLinkedOpenHashMap(int expected, float f) {
/*  153 */     if (f <= 0.0F || f > 1.0F)
/*  154 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  155 */     if (expected < 0)
/*  156 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  157 */     this.f = f;
/*  158 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  159 */     this.mask = this.n - 1;
/*  160 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  161 */     this.key = new float[this.n + 1];
/*  162 */     this.value = new char[this.n + 1];
/*  163 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharLinkedOpenHashMap(int expected) {
/*  172 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharLinkedOpenHashMap() {
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
/*      */   public Float2CharLinkedOpenHashMap(Map<? extends Float, ? extends Character> m, float f) {
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
/*      */   public Float2CharLinkedOpenHashMap(Map<? extends Float, ? extends Character> m) {
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
/*      */   public Float2CharLinkedOpenHashMap(Float2CharMap m, float f) {
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
/*      */   public Float2CharLinkedOpenHashMap(Float2CharMap m) {
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
/*      */   public Float2CharLinkedOpenHashMap(float[] k, char[] v, float f) {
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
/*      */   public Float2CharLinkedOpenHashMap(float[] k, char[] v) {
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
/*      */   public void putAll(Map<? extends Float, ? extends Character> m) {
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
/*      */   private void insert(int pos, float k, char v) {
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
/*      */   public char put(float k, char v) {
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
/*  364 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  366 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  368 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  369 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  372 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
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
/*      */   public char remove(float k) {
/*  385 */     if (Float.floatToIntBits(k) == 0) {
/*  386 */       if (this.containsNullKey)
/*  387 */         return removeNullEntry(); 
/*  388 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  391 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  394 */     if (Float.floatToIntBits(
/*  395 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  397 */       return this.defRetValue; } 
/*  398 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  399 */       return removeEntry(pos); 
/*      */     while (true) {
/*  401 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  402 */         return this.defRetValue; 
/*  403 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
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
/*      */   public char getAndMoveToFirst(float k) {
/*  513 */     if (Float.floatToIntBits(k) == 0) {
/*  514 */       if (this.containsNullKey) {
/*  515 */         moveIndexToFirst(this.n);
/*  516 */         return this.value[this.n];
/*      */       } 
/*  518 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  521 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  524 */     if (Float.floatToIntBits(
/*  525 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  527 */       return this.defRetValue; } 
/*  528 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  529 */       moveIndexToFirst(pos);
/*  530 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  534 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  535 */         return this.defRetValue; 
/*  536 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public char getAndMoveToLast(float k) {
/*  552 */     if (Float.floatToIntBits(k) == 0) {
/*  553 */       if (this.containsNullKey) {
/*  554 */         moveIndexToLast(this.n);
/*  555 */         return this.value[this.n];
/*      */       } 
/*  557 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  560 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  563 */     if (Float.floatToIntBits(
/*  564 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  566 */       return this.defRetValue; } 
/*  567 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  568 */       moveIndexToLast(pos);
/*  569 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  573 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  574 */         return this.defRetValue; 
/*  575 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public char putAndMoveToFirst(float k, char v) {
/*      */     int pos;
/*  594 */     if (Float.floatToIntBits(k) == 0) {
/*  595 */       if (this.containsNullKey) {
/*  596 */         moveIndexToFirst(this.n);
/*  597 */         return setValue(this.n, v);
/*      */       } 
/*  599 */       this.containsNullKey = true;
/*  600 */       pos = this.n;
/*      */     } else {
/*      */       
/*  603 */       float[] key = this.key;
/*      */       float curr;
/*  605 */       if (Float.floatToIntBits(
/*  606 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  608 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  609 */           moveIndexToFirst(pos);
/*  610 */           return setValue(pos, v);
/*      */         } 
/*  612 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  613 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public char putAndMoveToLast(float k, char v) {
/*      */     int pos;
/*  649 */     if (Float.floatToIntBits(k) == 0) {
/*  650 */       if (this.containsNullKey) {
/*  651 */         moveIndexToLast(this.n);
/*  652 */         return setValue(this.n, v);
/*      */       } 
/*  654 */       this.containsNullKey = true;
/*  655 */       pos = this.n;
/*      */     } else {
/*      */       
/*  658 */       float[] key = this.key;
/*      */       float curr;
/*  660 */       if (Float.floatToIntBits(
/*  661 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  663 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  664 */           moveIndexToLast(pos);
/*  665 */           return setValue(pos, v);
/*      */         } 
/*  667 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  668 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public char get(float k) {
/*  694 */     if (Float.floatToIntBits(k) == 0) {
/*  695 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  697 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  700 */     if (Float.floatToIntBits(
/*  701 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  703 */       return this.defRetValue; } 
/*  704 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  705 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  708 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  709 */         return this.defRetValue; 
/*  710 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  711 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  717 */     if (Float.floatToIntBits(k) == 0) {
/*  718 */       return this.containsNullKey;
/*      */     }
/*  720 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  723 */     if (Float.floatToIntBits(
/*  724 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  726 */       return false; } 
/*  727 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  728 */       return true;
/*      */     }
/*      */     while (true) {
/*  731 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  732 */         return false; 
/*  733 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  734 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(char v) {
/*  739 */     char[] value = this.value;
/*  740 */     float[] key = this.key;
/*  741 */     if (this.containsNullKey && value[this.n] == v)
/*  742 */       return true; 
/*  743 */     for (int i = this.n; i-- != 0;) {
/*  744 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  745 */         return true; 
/*  746 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public char getOrDefault(float k, char defaultValue) {
/*  752 */     if (Float.floatToIntBits(k) == 0) {
/*  753 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  755 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  758 */     if (Float.floatToIntBits(
/*  759 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  761 */       return defaultValue; } 
/*  762 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  763 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  766 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  767 */         return defaultValue; 
/*  768 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  769 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public char putIfAbsent(float k, char v) {
/*  775 */     int pos = find(k);
/*  776 */     if (pos >= 0)
/*  777 */       return this.value[pos]; 
/*  778 */     insert(-pos - 1, k, v);
/*  779 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, char v) {
/*  785 */     if (Float.floatToIntBits(k) == 0) {
/*  786 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  787 */         removeNullEntry();
/*  788 */         return true;
/*      */       } 
/*  790 */       return false;
/*      */     } 
/*      */     
/*  793 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  796 */     if (Float.floatToIntBits(
/*  797 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  799 */       return false; } 
/*  800 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  801 */       removeEntry(pos);
/*  802 */       return true;
/*      */     } 
/*      */     while (true) {
/*  805 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  806 */         return false; 
/*  807 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  808 */         removeEntry(pos);
/*  809 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, char oldValue, char v) {
/*  816 */     int pos = find(k);
/*  817 */     if (pos < 0 || oldValue != this.value[pos])
/*  818 */       return false; 
/*  819 */     this.value[pos] = v;
/*  820 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public char replace(float k, char v) {
/*  825 */     int pos = find(k);
/*  826 */     if (pos < 0)
/*  827 */       return this.defRetValue; 
/*  828 */     char oldValue = this.value[pos];
/*  829 */     this.value[pos] = v;
/*  830 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public char computeIfAbsent(float k, DoubleToIntFunction mappingFunction) {
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
/*      */   public char computeIfAbsentNullable(float k, DoubleFunction<? extends Character> mappingFunction) {
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
/*      */   public char computeIfPresent(float k, BiFunction<? super Float, ? super Character, ? extends Character> remappingFunction) {
/*  862 */     Objects.requireNonNull(remappingFunction);
/*  863 */     int pos = find(k);
/*  864 */     if (pos < 0)
/*  865 */       return this.defRetValue; 
/*  866 */     Character newValue = remappingFunction.apply(Float.valueOf(k), Character.valueOf(this.value[pos]));
/*  867 */     if (newValue == null) {
/*  868 */       if (Float.floatToIntBits(k) == 0) {
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
/*      */   public char compute(float k, BiFunction<? super Float, ? super Character, ? extends Character> remappingFunction) {
/*  880 */     Objects.requireNonNull(remappingFunction);
/*  881 */     int pos = find(k);
/*  882 */     Character newValue = remappingFunction.apply(Float.valueOf(k), 
/*  883 */         (pos >= 0) ? Character.valueOf(this.value[pos]) : null);
/*  884 */     if (newValue == null) {
/*  885 */       if (pos >= 0)
/*  886 */         if (Float.floatToIntBits(k) == 0) {
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
/*      */   public char merge(float k, char v, BiFunction<? super Character, ? super Character, ? extends Character> remappingFunction) {
/*  904 */     Objects.requireNonNull(remappingFunction);
/*  905 */     int pos = find(k);
/*  906 */     if (pos < 0) {
/*  907 */       insert(-pos - 1, k, v);
/*  908 */       return v;
/*      */     } 
/*  910 */     Character newValue = remappingFunction.apply(Character.valueOf(this.value[pos]), Character.valueOf(v));
/*  911 */     if (newValue == null) {
/*  912 */       if (Float.floatToIntBits(k) == 0) {
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
/*  933 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2CharMap.Entry, Map.Entry<Float, Character>
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
/*      */     public float getFloatKey() {
/*  960 */       return Float2CharLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public char getCharValue() {
/*  964 */       return Float2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public char setValue(char v) {
/*  968 */       char oldValue = Float2CharLinkedOpenHashMap.this.value[this.index];
/*  969 */       Float2CharLinkedOpenHashMap.this.value[this.index] = v;
/*  970 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  980 */       return Float.valueOf(Float2CharLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Character getValue() {
/*  990 */       return Character.valueOf(Float2CharLinkedOpenHashMap.this.value[this.index]);
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
/* 1007 */       Map.Entry<Float, Character> e = (Map.Entry<Float, Character>)o;
/* 1008 */       return (Float.floatToIntBits(Float2CharLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2CharLinkedOpenHashMap.this.value[this.index] == ((Character)e
/* 1009 */         .getValue()).charValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1013 */       return HashCommon.float2int(Float2CharLinkedOpenHashMap.this.key[this.index]) ^ Float2CharLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1017 */       return Float2CharLinkedOpenHashMap.this.key[this.index] + "=>" + Float2CharLinkedOpenHashMap.this.value[this.index];
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
/*      */   public float firstFloatKey() {
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
/*      */   public float lastFloatKey() {
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
/*      */   public Float2CharSortedMap tailMap(float from) {
/* 1119 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharSortedMap headMap(float to) {
/* 1128 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2CharSortedMap subMap(float from, float to) {
/* 1137 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
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
/* 1180 */       this.next = Float2CharLinkedOpenHashMap.this.first;
/* 1181 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(float from) {
/* 1184 */       if (Float.floatToIntBits(from) == 0) {
/* 1185 */         if (Float2CharLinkedOpenHashMap.this.containsNullKey) {
/* 1186 */           this.next = (int)Float2CharLinkedOpenHashMap.this.link[Float2CharLinkedOpenHashMap.this.n];
/* 1187 */           this.prev = Float2CharLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1190 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1192 */       if (Float.floatToIntBits(Float2CharLinkedOpenHashMap.this.key[Float2CharLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1193 */         this.prev = Float2CharLinkedOpenHashMap.this.last;
/* 1194 */         this.index = Float2CharLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1198 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2CharLinkedOpenHashMap.this.mask;
/*      */       
/* 1200 */       while (Float.floatToIntBits(Float2CharLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1201 */         if (Float.floatToIntBits(Float2CharLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1203 */           this.next = (int)Float2CharLinkedOpenHashMap.this.link[pos];
/* 1204 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1207 */         pos = pos + 1 & Float2CharLinkedOpenHashMap.this.mask;
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
/* 1225 */         this.index = Float2CharLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1228 */       int pos = Float2CharLinkedOpenHashMap.this.first;
/* 1229 */       this.index = 1;
/* 1230 */       while (pos != this.prev) {
/* 1231 */         pos = (int)Float2CharLinkedOpenHashMap.this.link[pos];
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
/* 1247 */       this.next = (int)Float2CharLinkedOpenHashMap.this.link[this.curr];
/* 1248 */       this.prev = this.curr;
/* 1249 */       if (this.index >= 0)
/* 1250 */         this.index++; 
/* 1251 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1254 */       if (!hasPrevious())
/* 1255 */         throw new NoSuchElementException(); 
/* 1256 */       this.curr = this.prev;
/* 1257 */       this.prev = (int)(Float2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1273 */         this.prev = (int)(Float2CharLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1275 */         this.next = (int)Float2CharLinkedOpenHashMap.this.link[this.curr];
/* 1276 */       }  Float2CharLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1281 */       if (this.prev == -1) {
/* 1282 */         Float2CharLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1284 */         Float2CharLinkedOpenHashMap.this.link[this.prev] = Float2CharLinkedOpenHashMap.this.link[this.prev] ^ (Float2CharLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1285 */       }  if (this.next == -1) {
/* 1286 */         Float2CharLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1288 */         Float2CharLinkedOpenHashMap.this.link[this.next] = Float2CharLinkedOpenHashMap.this.link[this.next] ^ (Float2CharLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1289 */       }  int pos = this.curr;
/* 1290 */       this.curr = -1;
/* 1291 */       if (pos == Float2CharLinkedOpenHashMap.this.n) {
/* 1292 */         Float2CharLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1295 */         float[] key = Float2CharLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           float curr;
/*      */           int last;
/* 1299 */           pos = (last = pos) + 1 & Float2CharLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1301 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1302 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1305 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2CharLinkedOpenHashMap.this.mask;
/*      */             
/* 1307 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1309 */             pos = pos + 1 & Float2CharLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1311 */           key[last] = curr;
/* 1312 */           Float2CharLinkedOpenHashMap.this.value[last] = Float2CharLinkedOpenHashMap.this.value[pos];
/* 1313 */           if (this.next == pos)
/* 1314 */             this.next = last; 
/* 1315 */           if (this.prev == pos)
/* 1316 */             this.prev = last; 
/* 1317 */           Float2CharLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1322 */       int i = n;
/* 1323 */       while (i-- != 0 && hasNext())
/* 1324 */         nextEntry(); 
/* 1325 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1328 */       int i = n;
/* 1329 */       while (i-- != 0 && hasPrevious())
/* 1330 */         previousEntry(); 
/* 1331 */       return n - i - 1;
/*      */     }
/*      */     public void set(Float2CharMap.Entry ok) {
/* 1334 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Float2CharMap.Entry ok) {
/* 1337 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Float2CharMap.Entry> { private Float2CharLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1345 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2CharLinkedOpenHashMap.MapEntry next() {
/* 1349 */       return this.entry = new Float2CharLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Float2CharLinkedOpenHashMap.MapEntry previous() {
/* 1353 */       return this.entry = new Float2CharLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1357 */       super.remove();
/* 1358 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1362 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Float2CharMap.Entry> { final Float2CharLinkedOpenHashMap.MapEntry entry = new Float2CharLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1366 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2CharLinkedOpenHashMap.MapEntry next() {
/* 1370 */       this.entry.index = nextEntry();
/* 1371 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Float2CharLinkedOpenHashMap.MapEntry previous() {
/* 1375 */       this.entry.index = previousEntry();
/* 1376 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2CharMap.Entry> implements Float2CharSortedMap.FastSortedEntrySet { public ObjectBidirectionalIterator<Float2CharMap.Entry> iterator() {
/* 1382 */       return (ObjectBidirectionalIterator<Float2CharMap.Entry>)new Float2CharLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     private MapEntrySet() {}
/*      */     public Comparator<? super Float2CharMap.Entry> comparator() {
/* 1386 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2CharMap.Entry> subSet(Float2CharMap.Entry fromElement, Float2CharMap.Entry toElement) {
/* 1391 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2CharMap.Entry> headSet(Float2CharMap.Entry toElement) {
/* 1395 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2CharMap.Entry> tailSet(Float2CharMap.Entry fromElement) {
/* 1399 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Float2CharMap.Entry first() {
/* 1403 */       if (Float2CharLinkedOpenHashMap.this.size == 0)
/* 1404 */         throw new NoSuchElementException(); 
/* 1405 */       return new Float2CharLinkedOpenHashMap.MapEntry(Float2CharLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Float2CharMap.Entry last() {
/* 1409 */       if (Float2CharLinkedOpenHashMap.this.size == 0)
/* 1410 */         throw new NoSuchElementException(); 
/* 1411 */       return new Float2CharLinkedOpenHashMap.MapEntry(Float2CharLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1416 */       if (!(o instanceof Map.Entry))
/* 1417 */         return false; 
/* 1418 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1419 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1420 */         return false; 
/* 1421 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1422 */         return false; 
/* 1423 */       float k = ((Float)e.getKey()).floatValue();
/* 1424 */       char v = ((Character)e.getValue()).charValue();
/* 1425 */       if (Float.floatToIntBits(k) == 0) {
/* 1426 */         return (Float2CharLinkedOpenHashMap.this.containsNullKey && Float2CharLinkedOpenHashMap.this.value[Float2CharLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1428 */       float[] key = Float2CharLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1431 */       if (Float.floatToIntBits(
/* 1432 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2CharLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1434 */         return false; } 
/* 1435 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1436 */         return (Float2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1439 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2CharLinkedOpenHashMap.this.mask]) == 0)
/* 1440 */           return false; 
/* 1441 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1442 */           return (Float2CharLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1448 */       if (!(o instanceof Map.Entry))
/* 1449 */         return false; 
/* 1450 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1451 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1452 */         return false; 
/* 1453 */       if (e.getValue() == null || !(e.getValue() instanceof Character))
/* 1454 */         return false; 
/* 1455 */       float k = ((Float)e.getKey()).floatValue();
/* 1456 */       char v = ((Character)e.getValue()).charValue();
/* 1457 */       if (Float.floatToIntBits(k) == 0) {
/* 1458 */         if (Float2CharLinkedOpenHashMap.this.containsNullKey && Float2CharLinkedOpenHashMap.this.value[Float2CharLinkedOpenHashMap.this.n] == v) {
/* 1459 */           Float2CharLinkedOpenHashMap.this.removeNullEntry();
/* 1460 */           return true;
/*      */         } 
/* 1462 */         return false;
/*      */       } 
/*      */       
/* 1465 */       float[] key = Float2CharLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1468 */       if (Float.floatToIntBits(
/* 1469 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2CharLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1471 */         return false; } 
/* 1472 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1473 */         if (Float2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1474 */           Float2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1475 */           return true;
/*      */         } 
/* 1477 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1480 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2CharLinkedOpenHashMap.this.mask]) == 0)
/* 1481 */           return false; 
/* 1482 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1483 */           Float2CharLinkedOpenHashMap.this.value[pos] == v) {
/* 1484 */           Float2CharLinkedOpenHashMap.this.removeEntry(pos);
/* 1485 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1492 */       return Float2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1496 */       Float2CharLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2CharMap.Entry> iterator(Float2CharMap.Entry from) {
/* 1511 */       return new Float2CharLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2CharMap.Entry> fastIterator() {
/* 1522 */       return new Float2CharLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2CharMap.Entry> fastIterator(Float2CharMap.Entry from) {
/* 1537 */       return new Float2CharLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2CharMap.Entry> consumer) {
/* 1542 */       for (int i = Float2CharLinkedOpenHashMap.this.size, next = Float2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1543 */         int curr = next;
/* 1544 */         next = (int)Float2CharLinkedOpenHashMap.this.link[curr];
/* 1545 */         consumer.accept(new AbstractFloat2CharMap.BasicEntry(Float2CharLinkedOpenHashMap.this.key[curr], Float2CharLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2CharMap.Entry> consumer) {
/* 1551 */       AbstractFloat2CharMap.BasicEntry entry = new AbstractFloat2CharMap.BasicEntry();
/* 1552 */       for (int i = Float2CharLinkedOpenHashMap.this.size, next = Float2CharLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1553 */         int curr = next;
/* 1554 */         next = (int)Float2CharLinkedOpenHashMap.this.link[curr];
/* 1555 */         entry.key = Float2CharLinkedOpenHashMap.this.key[curr];
/* 1556 */         entry.value = Float2CharLinkedOpenHashMap.this.value[curr];
/* 1557 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Float2CharSortedMap.FastSortedEntrySet float2CharEntrySet() {
/* 1563 */     if (this.entries == null)
/* 1564 */       this.entries = new MapEntrySet(); 
/* 1565 */     return this.entries;
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
/* 1578 */       super(k);
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1582 */       return Float2CharLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public float nextFloat() {
/* 1589 */       return Float2CharLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet { private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1595 */       return new Float2CharLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1599 */       return new Float2CharLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1604 */       if (Float2CharLinkedOpenHashMap.this.containsNullKey)
/* 1605 */         consumer.accept(Float2CharLinkedOpenHashMap.this.key[Float2CharLinkedOpenHashMap.this.n]); 
/* 1606 */       for (int pos = Float2CharLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1607 */         float k = Float2CharLinkedOpenHashMap.this.key[pos];
/* 1608 */         if (Float.floatToIntBits(k) != 0)
/* 1609 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1614 */       return Float2CharLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1618 */       return Float2CharLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1622 */       int oldSize = Float2CharLinkedOpenHashMap.this.size;
/* 1623 */       Float2CharLinkedOpenHashMap.this.remove(k);
/* 1624 */       return (Float2CharLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1628 */       Float2CharLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public float firstFloat() {
/* 1632 */       if (Float2CharLinkedOpenHashMap.this.size == 0)
/* 1633 */         throw new NoSuchElementException(); 
/* 1634 */       return Float2CharLinkedOpenHashMap.this.key[Float2CharLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public float lastFloat() {
/* 1638 */       if (Float2CharLinkedOpenHashMap.this.size == 0)
/* 1639 */         throw new NoSuchElementException(); 
/* 1640 */       return Float2CharLinkedOpenHashMap.this.key[Float2CharLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1644 */       return null;
/*      */     }
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1648 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1652 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1656 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
/* 1661 */     if (this.keys == null)
/* 1662 */       this.keys = new KeySet(); 
/* 1663 */     return this.keys;
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
/* 1677 */       return Float2CharLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public char nextChar() {
/* 1684 */       return Float2CharLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public CharCollection values() {
/* 1689 */     if (this.values == null)
/* 1690 */       this.values = (CharCollection)new AbstractCharCollection()
/*      */         {
/*      */           public CharIterator iterator() {
/* 1693 */             return (CharIterator)new Float2CharLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1697 */             return Float2CharLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(char v) {
/* 1701 */             return Float2CharLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1705 */             Float2CharLinkedOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1710 */             if (Float2CharLinkedOpenHashMap.this.containsNullKey)
/* 1711 */               consumer.accept(Float2CharLinkedOpenHashMap.this.value[Float2CharLinkedOpenHashMap.this.n]); 
/* 1712 */             for (int pos = Float2CharLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1713 */               if (Float.floatToIntBits(Float2CharLinkedOpenHashMap.this.key[pos]) != 0)
/* 1714 */                 consumer.accept(Float2CharLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1717 */     return this.values;
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
/* 1734 */     return trim(this.size);
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
/* 1758 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1759 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1760 */       return true; 
/*      */     try {
/* 1762 */       rehash(l);
/* 1763 */     } catch (OutOfMemoryError cantDoIt) {
/* 1764 */       return false;
/*      */     } 
/* 1766 */     return true;
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
/* 1782 */     float[] key = this.key;
/* 1783 */     char[] value = this.value;
/* 1784 */     int mask = newN - 1;
/* 1785 */     float[] newKey = new float[newN + 1];
/* 1786 */     char[] newValue = new char[newN + 1];
/* 1787 */     int i = this.first, prev = -1, newPrev = -1;
/* 1788 */     long[] link = this.link;
/* 1789 */     long[] newLink = new long[newN + 1];
/* 1790 */     this.first = -1;
/* 1791 */     for (int j = this.size; j-- != 0; ) {
/* 1792 */       int pos; if (Float.floatToIntBits(key[i]) == 0) {
/* 1793 */         pos = newN;
/*      */       } else {
/* 1795 */         pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1796 */         while (Float.floatToIntBits(newKey[pos]) != 0)
/* 1797 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1799 */       newKey[pos] = key[i];
/* 1800 */       newValue[pos] = value[i];
/* 1801 */       if (prev != -1) {
/* 1802 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1803 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1804 */         newPrev = pos;
/*      */       } else {
/* 1806 */         newPrev = this.first = pos;
/*      */         
/* 1808 */         newLink[pos] = -1L;
/*      */       } 
/* 1810 */       int t = i;
/* 1811 */       i = (int)link[i];
/* 1812 */       prev = t;
/*      */     } 
/* 1814 */     this.link = newLink;
/* 1815 */     this.last = newPrev;
/* 1816 */     if (newPrev != -1)
/*      */     {
/* 1818 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1819 */     this.n = newN;
/* 1820 */     this.mask = mask;
/* 1821 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1822 */     this.key = newKey;
/* 1823 */     this.value = newValue;
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
/*      */   public Float2CharLinkedOpenHashMap clone() {
/*      */     Float2CharLinkedOpenHashMap c;
/*      */     try {
/* 1840 */       c = (Float2CharLinkedOpenHashMap)super.clone();
/* 1841 */     } catch (CloneNotSupportedException cantHappen) {
/* 1842 */       throw new InternalError();
/*      */     } 
/* 1844 */     c.keys = null;
/* 1845 */     c.values = null;
/* 1846 */     c.entries = null;
/* 1847 */     c.containsNullKey = this.containsNullKey;
/* 1848 */     c.key = (float[])this.key.clone();
/* 1849 */     c.value = (char[])this.value.clone();
/* 1850 */     c.link = (long[])this.link.clone();
/* 1851 */     return c;
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
/* 1864 */     int h = 0;
/* 1865 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1866 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1867 */         i++; 
/* 1868 */       t = HashCommon.float2int(this.key[i]);
/* 1869 */       t ^= this.value[i];
/* 1870 */       h += t;
/* 1871 */       i++;
/*      */     } 
/*      */     
/* 1874 */     if (this.containsNullKey)
/* 1875 */       h += this.value[this.n]; 
/* 1876 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1879 */     float[] key = this.key;
/* 1880 */     char[] value = this.value;
/* 1881 */     MapIterator i = new MapIterator();
/* 1882 */     s.defaultWriteObject();
/* 1883 */     for (int j = this.size; j-- != 0; ) {
/* 1884 */       int e = i.nextEntry();
/* 1885 */       s.writeFloat(key[e]);
/* 1886 */       s.writeChar(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1891 */     s.defaultReadObject();
/* 1892 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1893 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1894 */     this.mask = this.n - 1;
/* 1895 */     float[] key = this.key = new float[this.n + 1];
/* 1896 */     char[] value = this.value = new char[this.n + 1];
/* 1897 */     long[] link = this.link = new long[this.n + 1];
/* 1898 */     int prev = -1;
/* 1899 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1902 */     for (int i = this.size; i-- != 0; ) {
/* 1903 */       int pos; float k = s.readFloat();
/* 1904 */       char v = s.readChar();
/* 1905 */       if (Float.floatToIntBits(k) == 0) {
/* 1906 */         pos = this.n;
/* 1907 */         this.containsNullKey = true;
/*      */       } else {
/* 1909 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1910 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1911 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1913 */       key[pos] = k;
/* 1914 */       value[pos] = v;
/* 1915 */       if (this.first != -1) {
/* 1916 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1917 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1918 */         prev = pos; continue;
/*      */       } 
/* 1920 */       prev = this.first = pos;
/*      */       
/* 1922 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1925 */     this.last = prev;
/* 1926 */     if (prev != -1)
/*      */     {
/* 1928 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2CharLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */