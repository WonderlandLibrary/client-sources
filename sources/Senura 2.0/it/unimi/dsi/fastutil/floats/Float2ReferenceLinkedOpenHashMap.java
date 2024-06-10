/*      */ package it.unimi.dsi.fastutil.floats;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractReferenceCollection;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Float2ReferenceLinkedOpenHashMap<V>
/*      */   extends AbstractFloat2ReferenceSortedMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
/*      */   protected transient V[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  106 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  111 */   protected transient int last = -1;
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
/*      */   protected transient Float2ReferenceSortedMap.FastSortedEntrySet<V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceLinkedOpenHashMap(int expected, float f) {
/*  152 */     if (f <= 0.0F || f > 1.0F)
/*  153 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  154 */     if (expected < 0)
/*  155 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  156 */     this.f = f;
/*  157 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  158 */     this.mask = this.n - 1;
/*  159 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  160 */     this.key = new float[this.n + 1];
/*  161 */     this.value = (V[])new Object[this.n + 1];
/*  162 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceLinkedOpenHashMap() {
/*  179 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceLinkedOpenHashMap(Map<? extends Float, ? extends V> m, float f) {
/*  190 */     this(m.size(), f);
/*  191 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceLinkedOpenHashMap(Map<? extends Float, ? extends V> m) {
/*  201 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceLinkedOpenHashMap(Float2ReferenceMap<V> m, float f) {
/*  212 */     this(m.size(), f);
/*  213 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceLinkedOpenHashMap(Float2ReferenceMap<V> m) {
/*  223 */     this(m, 0.75F);
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
/*      */   public Float2ReferenceLinkedOpenHashMap(float[] k, V[] v, float f) {
/*  238 */     this(k.length, f);
/*  239 */     if (k.length != v.length) {
/*  240 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  242 */     for (int i = 0; i < k.length; i++) {
/*  243 */       put(k[i], v[i]);
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
/*      */   public Float2ReferenceLinkedOpenHashMap(float[] k, V[] v) {
/*  257 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  260 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  263 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  264 */     if (needed > this.n)
/*  265 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  268 */     int needed = (int)Math.min(1073741824L, 
/*  269 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  270 */     if (needed > this.n)
/*  271 */       rehash(needed); 
/*      */   }
/*      */   private V removeEntry(int pos) {
/*  274 */     V oldValue = this.value[pos];
/*  275 */     this.value[pos] = null;
/*  276 */     this.size--;
/*  277 */     fixPointers(pos);
/*  278 */     shiftKeys(pos);
/*  279 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  280 */       rehash(this.n / 2); 
/*  281 */     return oldValue;
/*      */   }
/*      */   private V removeNullEntry() {
/*  284 */     this.containsNullKey = false;
/*  285 */     V oldValue = this.value[this.n];
/*  286 */     this.value[this.n] = null;
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Float, ? extends V> m) {
/*  295 */     if (this.f <= 0.5D) {
/*  296 */       ensureCapacity(m.size());
/*      */     } else {
/*  298 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  300 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(float k) {
/*  304 */     if (Float.floatToIntBits(k) == 0) {
/*  305 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  307 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  310 */     if (Float.floatToIntBits(
/*  311 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  313 */       return -(pos + 1); } 
/*  314 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  315 */       return pos;
/*      */     }
/*      */     while (true) {
/*  318 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  319 */         return -(pos + 1); 
/*  320 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  321 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, float k, V v) {
/*  325 */     if (pos == this.n)
/*  326 */       this.containsNullKey = true; 
/*  327 */     this.key[pos] = k;
/*  328 */     this.value[pos] = v;
/*  329 */     if (this.size == 0) {
/*  330 */       this.first = this.last = pos;
/*      */       
/*  332 */       this.link[pos] = -1L;
/*      */     } else {
/*  334 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  335 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  336 */       this.last = pos;
/*      */     } 
/*  338 */     if (this.size++ >= this.maxFill) {
/*  339 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public V put(float k, V v) {
/*  345 */     int pos = find(k);
/*  346 */     if (pos < 0) {
/*  347 */       insert(-pos - 1, k, v);
/*  348 */       return this.defRetValue;
/*      */     } 
/*  350 */     V oldValue = this.value[pos];
/*  351 */     this.value[pos] = v;
/*  352 */     return oldValue;
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
/*  365 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  367 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  369 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  370 */           key[last] = 0.0F;
/*  371 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  374 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
/*  375 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  377 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  379 */       key[last] = curr;
/*  380 */       this.value[last] = this.value[pos];
/*  381 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public V remove(float k) {
/*  387 */     if (Float.floatToIntBits(k) == 0) {
/*  388 */       if (this.containsNullKey)
/*  389 */         return removeNullEntry(); 
/*  390 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  393 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  396 */     if (Float.floatToIntBits(
/*  397 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  399 */       return this.defRetValue; } 
/*  400 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  401 */       return removeEntry(pos); 
/*      */     while (true) {
/*  403 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  404 */         return this.defRetValue; 
/*  405 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  406 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private V setValue(int pos, V v) {
/*  410 */     V oldValue = this.value[pos];
/*  411 */     this.value[pos] = v;
/*  412 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeFirst() {
/*  423 */     if (this.size == 0)
/*  424 */       throw new NoSuchElementException(); 
/*  425 */     int pos = this.first;
/*      */     
/*  427 */     this.first = (int)this.link[pos];
/*  428 */     if (0 <= this.first)
/*      */     {
/*  430 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  432 */     this.size--;
/*  433 */     V v = this.value[pos];
/*  434 */     if (pos == this.n) {
/*  435 */       this.containsNullKey = false;
/*  436 */       this.value[this.n] = null;
/*      */     } else {
/*  438 */       shiftKeys(pos);
/*  439 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  440 */       rehash(this.n / 2); 
/*  441 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public V removeLast() {
/*  451 */     if (this.size == 0)
/*  452 */       throw new NoSuchElementException(); 
/*  453 */     int pos = this.last;
/*      */     
/*  455 */     this.last = (int)(this.link[pos] >>> 32L);
/*  456 */     if (0 <= this.last)
/*      */     {
/*  458 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  460 */     this.size--;
/*  461 */     V v = this.value[pos];
/*  462 */     if (pos == this.n) {
/*  463 */       this.containsNullKey = false;
/*  464 */       this.value[this.n] = null;
/*      */     } else {
/*  466 */       shiftKeys(pos);
/*  467 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  468 */       rehash(this.n / 2); 
/*  469 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  472 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  474 */     if (this.last == i) {
/*  475 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  477 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  479 */       long linki = this.link[i];
/*  480 */       int prev = (int)(linki >>> 32L);
/*  481 */       int next = (int)linki;
/*  482 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  483 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  485 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  486 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  487 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  490 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  492 */     if (this.first == i) {
/*  493 */       this.first = (int)this.link[i];
/*      */       
/*  495 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  497 */       long linki = this.link[i];
/*  498 */       int prev = (int)(linki >>> 32L);
/*  499 */       int next = (int)linki;
/*  500 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  501 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  503 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  504 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  505 */     this.last = i;
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
/*      */   public V getAndMoveToFirst(float k) {
/*  517 */     if (Float.floatToIntBits(k) == 0) {
/*  518 */       if (this.containsNullKey) {
/*  519 */         moveIndexToFirst(this.n);
/*  520 */         return this.value[this.n];
/*      */       } 
/*  522 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  525 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  528 */     if (Float.floatToIntBits(
/*  529 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  531 */       return this.defRetValue; } 
/*  532 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  533 */       moveIndexToFirst(pos);
/*  534 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  538 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  539 */         return this.defRetValue; 
/*  540 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  541 */         moveIndexToFirst(pos);
/*  542 */         return this.value[pos];
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
/*      */   public V getAndMoveToLast(float k) {
/*  556 */     if (Float.floatToIntBits(k) == 0) {
/*  557 */       if (this.containsNullKey) {
/*  558 */         moveIndexToLast(this.n);
/*  559 */         return this.value[this.n];
/*      */       } 
/*  561 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  564 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  567 */     if (Float.floatToIntBits(
/*  568 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  570 */       return this.defRetValue; } 
/*  571 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  572 */       moveIndexToLast(pos);
/*  573 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  577 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  578 */         return this.defRetValue; 
/*  579 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  580 */         moveIndexToLast(pos);
/*  581 */         return this.value[pos];
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
/*      */   public V putAndMoveToFirst(float k, V v) {
/*      */     int pos;
/*  598 */     if (Float.floatToIntBits(k) == 0) {
/*  599 */       if (this.containsNullKey) {
/*  600 */         moveIndexToFirst(this.n);
/*  601 */         return setValue(this.n, v);
/*      */       } 
/*  603 */       this.containsNullKey = true;
/*  604 */       pos = this.n;
/*      */     } else {
/*      */       
/*  607 */       float[] key = this.key;
/*      */       float curr;
/*  609 */       if (Float.floatToIntBits(
/*  610 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  612 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  613 */           moveIndexToFirst(pos);
/*  614 */           return setValue(pos, v);
/*      */         } 
/*  616 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  617 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  618 */             moveIndexToFirst(pos);
/*  619 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  623 */     }  this.key[pos] = k;
/*  624 */     this.value[pos] = v;
/*  625 */     if (this.size == 0) {
/*  626 */       this.first = this.last = pos;
/*      */       
/*  628 */       this.link[pos] = -1L;
/*      */     } else {
/*  630 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  631 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  632 */       this.first = pos;
/*      */     } 
/*  634 */     if (this.size++ >= this.maxFill) {
/*  635 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  638 */     return this.defRetValue;
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
/*      */   public V putAndMoveToLast(float k, V v) {
/*      */     int pos;
/*  653 */     if (Float.floatToIntBits(k) == 0) {
/*  654 */       if (this.containsNullKey) {
/*  655 */         moveIndexToLast(this.n);
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
/*  668 */           moveIndexToLast(pos);
/*  669 */           return setValue(pos, v);
/*      */         } 
/*  671 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  672 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  673 */             moveIndexToLast(pos);
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
/*  685 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  686 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  687 */       this.last = pos;
/*      */     } 
/*  689 */     if (this.size++ >= this.maxFill) {
/*  690 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  693 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V get(float k) {
/*  698 */     if (Float.floatToIntBits(k) == 0) {
/*  699 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  701 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  704 */     if (Float.floatToIntBits(
/*  705 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  707 */       return this.defRetValue; } 
/*  708 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  709 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  712 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  713 */         return this.defRetValue; 
/*  714 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  715 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  721 */     if (Float.floatToIntBits(k) == 0) {
/*  722 */       return this.containsNullKey;
/*      */     }
/*  724 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  727 */     if (Float.floatToIntBits(
/*  728 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  730 */       return false; } 
/*  731 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  732 */       return true;
/*      */     }
/*      */     while (true) {
/*  735 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  736 */         return false; 
/*  737 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  738 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  743 */     V[] value = this.value;
/*  744 */     float[] key = this.key;
/*  745 */     if (this.containsNullKey && value[this.n] == v)
/*  746 */       return true; 
/*  747 */     for (int i = this.n; i-- != 0;) {
/*  748 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  749 */         return true; 
/*  750 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(float k, V defaultValue) {
/*  756 */     if (Float.floatToIntBits(k) == 0) {
/*  757 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  759 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  762 */     if (Float.floatToIntBits(
/*  763 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  765 */       return defaultValue; } 
/*  766 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  767 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  770 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  771 */         return defaultValue; 
/*  772 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  773 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(float k, V v) {
/*  779 */     int pos = find(k);
/*  780 */     if (pos >= 0)
/*  781 */       return this.value[pos]; 
/*  782 */     insert(-pos - 1, k, v);
/*  783 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, Object v) {
/*  789 */     if (Float.floatToIntBits(k) == 0) {
/*  790 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  791 */         removeNullEntry();
/*  792 */         return true;
/*      */       } 
/*  794 */       return false;
/*      */     } 
/*      */     
/*  797 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  800 */     if (Float.floatToIntBits(
/*  801 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  803 */       return false; } 
/*  804 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  805 */       removeEntry(pos);
/*  806 */       return true;
/*      */     } 
/*      */     while (true) {
/*  809 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  810 */         return false; 
/*  811 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  812 */         removeEntry(pos);
/*  813 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, V oldValue, V v) {
/*  820 */     int pos = find(k);
/*  821 */     if (pos < 0 || oldValue != this.value[pos])
/*  822 */       return false; 
/*  823 */     this.value[pos] = v;
/*  824 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(float k, V v) {
/*  829 */     int pos = find(k);
/*  830 */     if (pos < 0)
/*  831 */       return this.defRetValue; 
/*  832 */     V oldValue = this.value[pos];
/*  833 */     this.value[pos] = v;
/*  834 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(float k, DoubleFunction<? extends V> mappingFunction) {
/*  839 */     Objects.requireNonNull(mappingFunction);
/*  840 */     int pos = find(k);
/*  841 */     if (pos >= 0)
/*  842 */       return this.value[pos]; 
/*  843 */     V newValue = mappingFunction.apply(k);
/*  844 */     insert(-pos - 1, k, newValue);
/*  845 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  851 */     Objects.requireNonNull(remappingFunction);
/*  852 */     int pos = find(k);
/*  853 */     if (pos < 0)
/*  854 */       return this.defRetValue; 
/*  855 */     V newValue = remappingFunction.apply(Float.valueOf(k), this.value[pos]);
/*  856 */     if (newValue == null) {
/*  857 */       if (Float.floatToIntBits(k) == 0) {
/*  858 */         removeNullEntry();
/*      */       } else {
/*  860 */         removeEntry(pos);
/*  861 */       }  return this.defRetValue;
/*      */     } 
/*  863 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(float k, BiFunction<? super Float, ? super V, ? extends V> remappingFunction) {
/*  869 */     Objects.requireNonNull(remappingFunction);
/*  870 */     int pos = find(k);
/*  871 */     V newValue = remappingFunction.apply(Float.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  872 */     if (newValue == null) {
/*  873 */       if (pos >= 0)
/*  874 */         if (Float.floatToIntBits(k) == 0) {
/*  875 */           removeNullEntry();
/*      */         } else {
/*  877 */           removeEntry(pos);
/*      */         }  
/*  879 */       return this.defRetValue;
/*      */     } 
/*  881 */     V newVal = newValue;
/*  882 */     if (pos < 0) {
/*  883 */       insert(-pos - 1, k, newVal);
/*  884 */       return newVal;
/*      */     } 
/*  886 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(float k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  892 */     Objects.requireNonNull(remappingFunction);
/*  893 */     int pos = find(k);
/*  894 */     if (pos < 0 || this.value[pos] == null) {
/*  895 */       if (v == null)
/*  896 */         return this.defRetValue; 
/*  897 */       insert(-pos - 1, k, v);
/*  898 */       return v;
/*      */     } 
/*  900 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  901 */     if (newValue == null) {
/*  902 */       if (Float.floatToIntBits(k) == 0) {
/*  903 */         removeNullEntry();
/*      */       } else {
/*  905 */         removeEntry(pos);
/*  906 */       }  return this.defRetValue;
/*      */     } 
/*  908 */     this.value[pos] = newValue; return newValue;
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
/*  919 */     if (this.size == 0)
/*      */       return; 
/*  921 */     this.size = 0;
/*  922 */     this.containsNullKey = false;
/*  923 */     Arrays.fill(this.key, 0.0F);
/*  924 */     Arrays.fill((Object[])this.value, (Object)null);
/*  925 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  929 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  933 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Float2ReferenceMap.Entry<V>, Map.Entry<Float, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  945 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public float getFloatKey() {
/*  951 */       return Float2ReferenceLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  955 */       return Float2ReferenceLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  959 */       V oldValue = Float2ReferenceLinkedOpenHashMap.this.value[this.index];
/*  960 */       Float2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
/*  961 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  971 */       return Float.valueOf(Float2ReferenceLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  976 */       if (!(o instanceof Map.Entry))
/*  977 */         return false; 
/*  978 */       Map.Entry<Float, V> e = (Map.Entry<Float, V>)o;
/*  979 */       return (Float.floatToIntBits(Float2ReferenceLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2ReferenceLinkedOpenHashMap.this.value[this.index] == e
/*  980 */         .getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  984 */       return HashCommon.float2int(Float2ReferenceLinkedOpenHashMap.this.key[this.index]) ^ (
/*  985 */         (Float2ReferenceLinkedOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Float2ReferenceLinkedOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  989 */       return Float2ReferenceLinkedOpenHashMap.this.key[this.index] + "=>" + Float2ReferenceLinkedOpenHashMap.this.value[this.index];
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
/* 1000 */     if (this.size == 0) {
/* 1001 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1004 */     if (this.first == i) {
/* 1005 */       this.first = (int)this.link[i];
/* 1006 */       if (0 <= this.first)
/*      */       {
/* 1008 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1012 */     if (this.last == i) {
/* 1013 */       this.last = (int)(this.link[i] >>> 32L);
/* 1014 */       if (0 <= this.last)
/*      */       {
/* 1016 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1020 */     long linki = this.link[i];
/* 1021 */     int prev = (int)(linki >>> 32L);
/* 1022 */     int next = (int)linki;
/* 1023 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1024 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1037 */     if (this.size == 1) {
/* 1038 */       this.first = this.last = d;
/*      */       
/* 1040 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1043 */     if (this.first == s) {
/* 1044 */       this.first = d;
/* 1045 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1046 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1049 */     if (this.last == s) {
/* 1050 */       this.last = d;
/* 1051 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1052 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1055 */     long links = this.link[s];
/* 1056 */     int prev = (int)(links >>> 32L);
/* 1057 */     int next = (int)links;
/* 1058 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1059 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1060 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float firstFloatKey() {
/* 1069 */     if (this.size == 0)
/* 1070 */       throw new NoSuchElementException(); 
/* 1071 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float lastFloatKey() {
/* 1080 */     if (this.size == 0)
/* 1081 */       throw new NoSuchElementException(); 
/* 1082 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceSortedMap<V> tailMap(float from) {
/* 1091 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceSortedMap<V> headMap(float to) {
/* 1100 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2ReferenceSortedMap<V> subMap(float from, float to) {
/* 1109 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
/* 1118 */     return null;
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
/* 1133 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1139 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1144 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1150 */     int index = -1;
/*      */     protected MapIterator() {
/* 1152 */       this.next = Float2ReferenceLinkedOpenHashMap.this.first;
/* 1153 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(float from) {
/* 1156 */       if (Float.floatToIntBits(from) == 0) {
/* 1157 */         if (Float2ReferenceLinkedOpenHashMap.this.containsNullKey) {
/* 1158 */           this.next = (int)Float2ReferenceLinkedOpenHashMap.this.link[Float2ReferenceLinkedOpenHashMap.this.n];
/* 1159 */           this.prev = Float2ReferenceLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1162 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1164 */       if (Float.floatToIntBits(Float2ReferenceLinkedOpenHashMap.this.key[Float2ReferenceLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1165 */         this.prev = Float2ReferenceLinkedOpenHashMap.this.last;
/* 1166 */         this.index = Float2ReferenceLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1170 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2ReferenceLinkedOpenHashMap.this.mask;
/*      */       
/* 1172 */       while (Float.floatToIntBits(Float2ReferenceLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1173 */         if (Float.floatToIntBits(Float2ReferenceLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1175 */           this.next = (int)Float2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1176 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1179 */         pos = pos + 1 & Float2ReferenceLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1181 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1184 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1187 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1190 */       if (this.index >= 0)
/*      */         return; 
/* 1192 */       if (this.prev == -1) {
/* 1193 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1196 */       if (this.next == -1) {
/* 1197 */         this.index = Float2ReferenceLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1200 */       int pos = Float2ReferenceLinkedOpenHashMap.this.first;
/* 1201 */       this.index = 1;
/* 1202 */       while (pos != this.prev) {
/* 1203 */         pos = (int)Float2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1204 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1208 */       ensureIndexKnown();
/* 1209 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1212 */       ensureIndexKnown();
/* 1213 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1216 */       if (!hasNext())
/* 1217 */         throw new NoSuchElementException(); 
/* 1218 */       this.curr = this.next;
/* 1219 */       this.next = (int)Float2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1220 */       this.prev = this.curr;
/* 1221 */       if (this.index >= 0)
/* 1222 */         this.index++; 
/* 1223 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1226 */       if (!hasPrevious())
/* 1227 */         throw new NoSuchElementException(); 
/* 1228 */       this.curr = this.prev;
/* 1229 */       this.prev = (int)(Float2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1230 */       this.next = this.curr;
/* 1231 */       if (this.index >= 0)
/* 1232 */         this.index--; 
/* 1233 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1236 */       ensureIndexKnown();
/* 1237 */       if (this.curr == -1)
/* 1238 */         throw new IllegalStateException(); 
/* 1239 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1244 */         this.index--;
/* 1245 */         this.prev = (int)(Float2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1247 */         this.next = (int)Float2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1248 */       }  Float2ReferenceLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1253 */       if (this.prev == -1) {
/* 1254 */         Float2ReferenceLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1256 */         Float2ReferenceLinkedOpenHashMap.this.link[this.prev] = Float2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ (Float2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1257 */       }  if (this.next == -1) {
/* 1258 */         Float2ReferenceLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1260 */         Float2ReferenceLinkedOpenHashMap.this.link[this.next] = Float2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (Float2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1261 */       }  int pos = this.curr;
/* 1262 */       this.curr = -1;
/* 1263 */       if (pos == Float2ReferenceLinkedOpenHashMap.this.n) {
/* 1264 */         Float2ReferenceLinkedOpenHashMap.this.containsNullKey = false;
/* 1265 */         Float2ReferenceLinkedOpenHashMap.this.value[Float2ReferenceLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1268 */         float[] key = Float2ReferenceLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           float curr;
/*      */           int last;
/* 1272 */           pos = (last = pos) + 1 & Float2ReferenceLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1274 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1275 */               key[last] = 0.0F;
/* 1276 */               Float2ReferenceLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1279 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2ReferenceLinkedOpenHashMap.this.mask;
/*      */             
/* 1281 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1283 */             pos = pos + 1 & Float2ReferenceLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1285 */           key[last] = curr;
/* 1286 */           Float2ReferenceLinkedOpenHashMap.this.value[last] = Float2ReferenceLinkedOpenHashMap.this.value[pos];
/* 1287 */           if (this.next == pos)
/* 1288 */             this.next = last; 
/* 1289 */           if (this.prev == pos)
/* 1290 */             this.prev = last; 
/* 1291 */           Float2ReferenceLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1296 */       int i = n;
/* 1297 */       while (i-- != 0 && hasNext())
/* 1298 */         nextEntry(); 
/* 1299 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1302 */       int i = n;
/* 1303 */       while (i-- != 0 && hasPrevious())
/* 1304 */         previousEntry(); 
/* 1305 */       return n - i - 1;
/*      */     }
/*      */     public void set(Float2ReferenceMap.Entry<V> ok) {
/* 1308 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Float2ReferenceMap.Entry<V> ok) {
/* 1311 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Float2ReferenceMap.Entry<V>> { private Float2ReferenceLinkedOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1319 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
/* 1323 */       return this.entry = new Float2ReferenceLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Float2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
/* 1327 */       return this.entry = new Float2ReferenceLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1331 */       super.remove();
/* 1332 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1336 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Float2ReferenceMap.Entry<V>> { final Float2ReferenceLinkedOpenHashMap<V>.MapEntry entry = new Float2ReferenceLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1340 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
/* 1344 */       this.entry.index = nextEntry();
/* 1345 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Float2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
/* 1349 */       this.entry.index = previousEntry();
/* 1350 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2ReferenceMap.Entry<V>> implements Float2ReferenceSortedMap.FastSortedEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Float2ReferenceMap.Entry<V>> iterator() {
/* 1358 */       return (ObjectBidirectionalIterator<Float2ReferenceMap.Entry<V>>)new Float2ReferenceLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Float2ReferenceMap.Entry<V>> comparator() {
/* 1362 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2ReferenceMap.Entry<V>> subSet(Float2ReferenceMap.Entry<V> fromElement, Float2ReferenceMap.Entry<V> toElement) {
/* 1367 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2ReferenceMap.Entry<V>> headSet(Float2ReferenceMap.Entry<V> toElement) {
/* 1371 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2ReferenceMap.Entry<V>> tailSet(Float2ReferenceMap.Entry<V> fromElement) {
/* 1375 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Float2ReferenceMap.Entry<V> first() {
/* 1379 */       if (Float2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1380 */         throw new NoSuchElementException(); 
/* 1381 */       return new Float2ReferenceLinkedOpenHashMap.MapEntry(Float2ReferenceLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Float2ReferenceMap.Entry<V> last() {
/* 1385 */       if (Float2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1386 */         throw new NoSuchElementException(); 
/* 1387 */       return new Float2ReferenceLinkedOpenHashMap.MapEntry(Float2ReferenceLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1392 */       if (!(o instanceof Map.Entry))
/* 1393 */         return false; 
/* 1394 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1395 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1396 */         return false; 
/* 1397 */       float k = ((Float)e.getKey()).floatValue();
/* 1398 */       V v = (V)e.getValue();
/* 1399 */       if (Float.floatToIntBits(k) == 0) {
/* 1400 */         return (Float2ReferenceLinkedOpenHashMap.this.containsNullKey && Float2ReferenceLinkedOpenHashMap.this.value[Float2ReferenceLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1402 */       float[] key = Float2ReferenceLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1405 */       if (Float.floatToIntBits(
/* 1406 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ReferenceLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1408 */         return false; } 
/* 1409 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1410 */         return (Float2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1413 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceLinkedOpenHashMap.this.mask]) == 0)
/* 1414 */           return false; 
/* 1415 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1416 */           return (Float2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1422 */       if (!(o instanceof Map.Entry))
/* 1423 */         return false; 
/* 1424 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1425 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1426 */         return false; 
/* 1427 */       float k = ((Float)e.getKey()).floatValue();
/* 1428 */       V v = (V)e.getValue();
/* 1429 */       if (Float.floatToIntBits(k) == 0) {
/* 1430 */         if (Float2ReferenceLinkedOpenHashMap.this.containsNullKey && Float2ReferenceLinkedOpenHashMap.this.value[Float2ReferenceLinkedOpenHashMap.this.n] == v) {
/* 1431 */           Float2ReferenceLinkedOpenHashMap.this.removeNullEntry();
/* 1432 */           return true;
/*      */         } 
/* 1434 */         return false;
/*      */       } 
/*      */       
/* 1437 */       float[] key = Float2ReferenceLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1440 */       if (Float.floatToIntBits(
/* 1441 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2ReferenceLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1443 */         return false; } 
/* 1444 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1445 */         if (Float2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1446 */           Float2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1447 */           return true;
/*      */         } 
/* 1449 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1452 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2ReferenceLinkedOpenHashMap.this.mask]) == 0)
/* 1453 */           return false; 
/* 1454 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1455 */           Float2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1456 */           Float2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1457 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1464 */       return Float2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1468 */       Float2ReferenceLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2ReferenceMap.Entry<V>> iterator(Float2ReferenceMap.Entry<V> from) {
/* 1483 */       return new Float2ReferenceLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2ReferenceMap.Entry<V>> fastIterator() {
/* 1494 */       return new Float2ReferenceLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2ReferenceMap.Entry<V>> fastIterator(Float2ReferenceMap.Entry<V> from) {
/* 1509 */       return new Float2ReferenceLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/* 1514 */       for (int i = Float2ReferenceLinkedOpenHashMap.this.size, next = Float2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1515 */         int curr = next;
/* 1516 */         next = (int)Float2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1517 */         consumer.accept(new AbstractFloat2ReferenceMap.BasicEntry<>(Float2ReferenceLinkedOpenHashMap.this.key[curr], Float2ReferenceLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2ReferenceMap.Entry<V>> consumer) {
/* 1523 */       AbstractFloat2ReferenceMap.BasicEntry<V> entry = new AbstractFloat2ReferenceMap.BasicEntry<>();
/* 1524 */       for (int i = Float2ReferenceLinkedOpenHashMap.this.size, next = Float2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1525 */         int curr = next;
/* 1526 */         next = (int)Float2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1527 */         entry.key = Float2ReferenceLinkedOpenHashMap.this.key[curr];
/* 1528 */         entry.value = Float2ReferenceLinkedOpenHashMap.this.value[curr];
/* 1529 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Float2ReferenceSortedMap.FastSortedEntrySet<V> float2ReferenceEntrySet() {
/* 1535 */     if (this.entries == null)
/* 1536 */       this.entries = new MapEntrySet(); 
/* 1537 */     return this.entries;
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
/* 1550 */       super(k);
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1554 */       return Float2ReferenceLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public float nextFloat() {
/* 1561 */       return Float2ReferenceLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet { private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1567 */       return new Float2ReferenceLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1571 */       return new Float2ReferenceLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1576 */       if (Float2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1577 */         consumer.accept(Float2ReferenceLinkedOpenHashMap.this.key[Float2ReferenceLinkedOpenHashMap.this.n]); 
/* 1578 */       for (int pos = Float2ReferenceLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1579 */         float k = Float2ReferenceLinkedOpenHashMap.this.key[pos];
/* 1580 */         if (Float.floatToIntBits(k) != 0)
/* 1581 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1586 */       return Float2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1590 */       return Float2ReferenceLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1594 */       int oldSize = Float2ReferenceLinkedOpenHashMap.this.size;
/* 1595 */       Float2ReferenceLinkedOpenHashMap.this.remove(k);
/* 1596 */       return (Float2ReferenceLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1600 */       Float2ReferenceLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public float firstFloat() {
/* 1604 */       if (Float2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1605 */         throw new NoSuchElementException(); 
/* 1606 */       return Float2ReferenceLinkedOpenHashMap.this.key[Float2ReferenceLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public float lastFloat() {
/* 1610 */       if (Float2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1611 */         throw new NoSuchElementException(); 
/* 1612 */       return Float2ReferenceLinkedOpenHashMap.this.key[Float2ReferenceLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1616 */       return null;
/*      */     }
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1620 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1624 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1628 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
/* 1633 */     if (this.keys == null)
/* 1634 */       this.keys = new KeySet(); 
/* 1635 */     return this.keys;
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
/*      */     implements ObjectListIterator<V>
/*      */   {
/*      */     public V previous() {
/* 1649 */       return Float2ReferenceLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1656 */       return Float2ReferenceLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1661 */     if (this.values == null)
/* 1662 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1665 */             return (ObjectIterator<V>)new Float2ReferenceLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1669 */             return Float2ReferenceLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1673 */             return Float2ReferenceLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1677 */             Float2ReferenceLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1682 */             if (Float2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1683 */               consumer.accept(Float2ReferenceLinkedOpenHashMap.this.value[Float2ReferenceLinkedOpenHashMap.this.n]); 
/* 1684 */             for (int pos = Float2ReferenceLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1685 */               if (Float.floatToIntBits(Float2ReferenceLinkedOpenHashMap.this.key[pos]) != 0)
/* 1686 */                 consumer.accept(Float2ReferenceLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1689 */     return this.values;
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
/* 1706 */     return trim(this.size);
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
/* 1730 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1731 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1732 */       return true; 
/*      */     try {
/* 1734 */       rehash(l);
/* 1735 */     } catch (OutOfMemoryError cantDoIt) {
/* 1736 */       return false;
/*      */     } 
/* 1738 */     return true;
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
/* 1754 */     float[] key = this.key;
/* 1755 */     V[] value = this.value;
/* 1756 */     int mask = newN - 1;
/* 1757 */     float[] newKey = new float[newN + 1];
/* 1758 */     V[] newValue = (V[])new Object[newN + 1];
/* 1759 */     int i = this.first, prev = -1, newPrev = -1;
/* 1760 */     long[] link = this.link;
/* 1761 */     long[] newLink = new long[newN + 1];
/* 1762 */     this.first = -1;
/* 1763 */     for (int j = this.size; j-- != 0; ) {
/* 1764 */       int pos; if (Float.floatToIntBits(key[i]) == 0) {
/* 1765 */         pos = newN;
/*      */       } else {
/* 1767 */         pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1768 */         while (Float.floatToIntBits(newKey[pos]) != 0)
/* 1769 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1771 */       newKey[pos] = key[i];
/* 1772 */       newValue[pos] = value[i];
/* 1773 */       if (prev != -1) {
/* 1774 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1775 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1776 */         newPrev = pos;
/*      */       } else {
/* 1778 */         newPrev = this.first = pos;
/*      */         
/* 1780 */         newLink[pos] = -1L;
/*      */       } 
/* 1782 */       int t = i;
/* 1783 */       i = (int)link[i];
/* 1784 */       prev = t;
/*      */     } 
/* 1786 */     this.link = newLink;
/* 1787 */     this.last = newPrev;
/* 1788 */     if (newPrev != -1)
/*      */     {
/* 1790 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1791 */     this.n = newN;
/* 1792 */     this.mask = mask;
/* 1793 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1794 */     this.key = newKey;
/* 1795 */     this.value = newValue;
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
/*      */   public Float2ReferenceLinkedOpenHashMap<V> clone() {
/*      */     Float2ReferenceLinkedOpenHashMap<V> c;
/*      */     try {
/* 1812 */       c = (Float2ReferenceLinkedOpenHashMap<V>)super.clone();
/* 1813 */     } catch (CloneNotSupportedException cantHappen) {
/* 1814 */       throw new InternalError();
/*      */     } 
/* 1816 */     c.keys = null;
/* 1817 */     c.values = null;
/* 1818 */     c.entries = null;
/* 1819 */     c.containsNullKey = this.containsNullKey;
/* 1820 */     c.key = (float[])this.key.clone();
/* 1821 */     c.value = (V[])this.value.clone();
/* 1822 */     c.link = (long[])this.link.clone();
/* 1823 */     return c;
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
/* 1836 */     int h = 0;
/* 1837 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1838 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1839 */         i++; 
/* 1840 */       t = HashCommon.float2int(this.key[i]);
/* 1841 */       if (this != this.value[i])
/* 1842 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1843 */       h += t;
/* 1844 */       i++;
/*      */     } 
/*      */     
/* 1847 */     if (this.containsNullKey)
/* 1848 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1849 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1852 */     float[] key = this.key;
/* 1853 */     V[] value = this.value;
/* 1854 */     MapIterator i = new MapIterator();
/* 1855 */     s.defaultWriteObject();
/* 1856 */     for (int j = this.size; j-- != 0; ) {
/* 1857 */       int e = i.nextEntry();
/* 1858 */       s.writeFloat(key[e]);
/* 1859 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1864 */     s.defaultReadObject();
/* 1865 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1866 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1867 */     this.mask = this.n - 1;
/* 1868 */     float[] key = this.key = new float[this.n + 1];
/* 1869 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1870 */     long[] link = this.link = new long[this.n + 1];
/* 1871 */     int prev = -1;
/* 1872 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1875 */     for (int i = this.size; i-- != 0; ) {
/* 1876 */       int pos; float k = s.readFloat();
/* 1877 */       V v = (V)s.readObject();
/* 1878 */       if (Float.floatToIntBits(k) == 0) {
/* 1879 */         pos = this.n;
/* 1880 */         this.containsNullKey = true;
/*      */       } else {
/* 1882 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1883 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1884 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1886 */       key[pos] = k;
/* 1887 */       value[pos] = v;
/* 1888 */       if (this.first != -1) {
/* 1889 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1890 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1891 */         prev = pos; continue;
/*      */       } 
/* 1893 */       prev = this.first = pos;
/*      */       
/* 1895 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1898 */     this.last = prev;
/* 1899 */     if (prev != -1)
/*      */     {
/* 1901 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2ReferenceLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */