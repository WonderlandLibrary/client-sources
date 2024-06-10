/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectCollection;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSortedSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectCollection;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2ObjectLinkedOpenHashMap<V>
/*      */   extends AbstractDouble2ObjectSortedMap<V>
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
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
/*      */   protected transient Double2ObjectSortedMap.FastSortedEntrySet<V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ObjectCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectLinkedOpenHashMap(int expected, float f) {
/*  152 */     if (f <= 0.0F || f > 1.0F)
/*  153 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  154 */     if (expected < 0)
/*  155 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  156 */     this.f = f;
/*  157 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  158 */     this.mask = this.n - 1;
/*  159 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  160 */     this.key = new double[this.n + 1];
/*  161 */     this.value = (V[])new Object[this.n + 1];
/*  162 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectLinkedOpenHashMap() {
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
/*      */   public Double2ObjectLinkedOpenHashMap(Map<? extends Double, ? extends V> m, float f) {
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
/*      */   public Double2ObjectLinkedOpenHashMap(Map<? extends Double, ? extends V> m) {
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
/*      */   public Double2ObjectLinkedOpenHashMap(Double2ObjectMap<V> m, float f) {
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
/*      */   public Double2ObjectLinkedOpenHashMap(Double2ObjectMap<V> m) {
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
/*      */   public Double2ObjectLinkedOpenHashMap(double[] k, V[] v, float f) {
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
/*      */   public Double2ObjectLinkedOpenHashMap(double[] k, V[] v) {
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
/*      */   public void putAll(Map<? extends Double, ? extends V> m) {
/*  295 */     if (this.f <= 0.5D) {
/*  296 */       ensureCapacity(m.size());
/*      */     } else {
/*  298 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  300 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  304 */     if (Double.doubleToLongBits(k) == 0L) {
/*  305 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  307 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  310 */     if (Double.doubleToLongBits(
/*  311 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  313 */       return -(pos + 1); } 
/*  314 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  315 */       return pos;
/*      */     }
/*      */     while (true) {
/*  318 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  319 */         return -(pos + 1); 
/*  320 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  321 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, V v) {
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
/*      */   public V put(double k, V v) {
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
/*  365 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  367 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  369 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  370 */           key[last] = 0.0D;
/*  371 */           this.value[last] = null;
/*      */           return;
/*      */         } 
/*  374 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
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
/*      */   public V remove(double k) {
/*  387 */     if (Double.doubleToLongBits(k) == 0L) {
/*  388 */       if (this.containsNullKey)
/*  389 */         return removeNullEntry(); 
/*  390 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  393 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  396 */     if (Double.doubleToLongBits(
/*  397 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  399 */       return this.defRetValue; } 
/*  400 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  401 */       return removeEntry(pos); 
/*      */     while (true) {
/*  403 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  404 */         return this.defRetValue; 
/*  405 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
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
/*      */   public V getAndMoveToFirst(double k) {
/*  517 */     if (Double.doubleToLongBits(k) == 0L) {
/*  518 */       if (this.containsNullKey) {
/*  519 */         moveIndexToFirst(this.n);
/*  520 */         return this.value[this.n];
/*      */       } 
/*  522 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  525 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  528 */     if (Double.doubleToLongBits(
/*  529 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  531 */       return this.defRetValue; } 
/*  532 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  533 */       moveIndexToFirst(pos);
/*  534 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  538 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  539 */         return this.defRetValue; 
/*  540 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
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
/*      */   public V getAndMoveToLast(double k) {
/*  556 */     if (Double.doubleToLongBits(k) == 0L) {
/*  557 */       if (this.containsNullKey) {
/*  558 */         moveIndexToLast(this.n);
/*  559 */         return this.value[this.n];
/*      */       } 
/*  561 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  564 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  567 */     if (Double.doubleToLongBits(
/*  568 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  570 */       return this.defRetValue; } 
/*  571 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  572 */       moveIndexToLast(pos);
/*  573 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  577 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  578 */         return this.defRetValue; 
/*  579 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
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
/*      */   public V putAndMoveToFirst(double k, V v) {
/*      */     int pos;
/*  598 */     if (Double.doubleToLongBits(k) == 0L) {
/*  599 */       if (this.containsNullKey) {
/*  600 */         moveIndexToFirst(this.n);
/*  601 */         return setValue(this.n, v);
/*      */       } 
/*  603 */       this.containsNullKey = true;
/*  604 */       pos = this.n;
/*      */     } else {
/*      */       
/*  607 */       double[] key = this.key;
/*      */       double curr;
/*  609 */       if (Double.doubleToLongBits(
/*  610 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  612 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  613 */           moveIndexToFirst(pos);
/*  614 */           return setValue(pos, v);
/*      */         } 
/*  616 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  617 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
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
/*      */   public V putAndMoveToLast(double k, V v) {
/*      */     int pos;
/*  653 */     if (Double.doubleToLongBits(k) == 0L) {
/*  654 */       if (this.containsNullKey) {
/*  655 */         moveIndexToLast(this.n);
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
/*  668 */           moveIndexToLast(pos);
/*  669 */           return setValue(pos, v);
/*      */         } 
/*  671 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  672 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
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
/*      */   public V get(double k) {
/*  698 */     if (Double.doubleToLongBits(k) == 0L) {
/*  699 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  701 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  704 */     if (Double.doubleToLongBits(
/*  705 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  707 */       return this.defRetValue; } 
/*  708 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  709 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  712 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  713 */         return this.defRetValue; 
/*  714 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  715 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  721 */     if (Double.doubleToLongBits(k) == 0L) {
/*  722 */       return this.containsNullKey;
/*      */     }
/*  724 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  727 */     if (Double.doubleToLongBits(
/*  728 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  730 */       return false; } 
/*  731 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  732 */       return true;
/*      */     }
/*      */     while (true) {
/*  735 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  736 */         return false; 
/*  737 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  738 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(Object v) {
/*  743 */     V[] value = this.value;
/*  744 */     double[] key = this.key;
/*  745 */     if (this.containsNullKey && Objects.equals(value[this.n], v))
/*  746 */       return true; 
/*  747 */     for (int i = this.n; i-- != 0;) {
/*  748 */       if (Double.doubleToLongBits(key[i]) != 0L && Objects.equals(value[i], v))
/*  749 */         return true; 
/*  750 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V getOrDefault(double k, V defaultValue) {
/*  756 */     if (Double.doubleToLongBits(k) == 0L) {
/*  757 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  759 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  762 */     if (Double.doubleToLongBits(
/*  763 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  765 */       return defaultValue; } 
/*  766 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  767 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  770 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  771 */         return defaultValue; 
/*  772 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  773 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public V putIfAbsent(double k, V v) {
/*  779 */     int pos = find(k);
/*  780 */     if (pos >= 0)
/*  781 */       return this.value[pos]; 
/*  782 */     insert(-pos - 1, k, v);
/*  783 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, Object v) {
/*  789 */     if (Double.doubleToLongBits(k) == 0L) {
/*  790 */       if (this.containsNullKey && Objects.equals(v, this.value[this.n])) {
/*  791 */         removeNullEntry();
/*  792 */         return true;
/*      */       } 
/*  794 */       return false;
/*      */     } 
/*      */     
/*  797 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  800 */     if (Double.doubleToLongBits(
/*  801 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  803 */       return false; } 
/*  804 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && Objects.equals(v, this.value[pos])) {
/*  805 */       removeEntry(pos);
/*  806 */       return true;
/*      */     } 
/*      */     while (true) {
/*  809 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  810 */         return false; 
/*  811 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && 
/*  812 */         Objects.equals(v, this.value[pos])) {
/*  813 */         removeEntry(pos);
/*  814 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, V oldValue, V v) {
/*  821 */     int pos = find(k);
/*  822 */     if (pos < 0 || !Objects.equals(oldValue, this.value[pos]))
/*  823 */       return false; 
/*  824 */     this.value[pos] = v;
/*  825 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(double k, V v) {
/*  830 */     int pos = find(k);
/*  831 */     if (pos < 0)
/*  832 */       return this.defRetValue; 
/*  833 */     V oldValue = this.value[pos];
/*  834 */     this.value[pos] = v;
/*  835 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(double k, DoubleFunction<? extends V> mappingFunction) {
/*  840 */     Objects.requireNonNull(mappingFunction);
/*  841 */     int pos = find(k);
/*  842 */     if (pos >= 0)
/*  843 */       return this.value[pos]; 
/*  844 */     V newValue = mappingFunction.apply(k);
/*  845 */     insert(-pos - 1, k, newValue);
/*  846 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V computeIfPresent(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  852 */     Objects.requireNonNull(remappingFunction);
/*  853 */     int pos = find(k);
/*  854 */     if (pos < 0)
/*  855 */       return this.defRetValue; 
/*  856 */     V newValue = remappingFunction.apply(Double.valueOf(k), this.value[pos]);
/*  857 */     if (newValue == null) {
/*  858 */       if (Double.doubleToLongBits(k) == 0L) {
/*  859 */         removeNullEntry();
/*      */       } else {
/*  861 */         removeEntry(pos);
/*  862 */       }  return this.defRetValue;
/*      */     } 
/*  864 */     this.value[pos] = newValue; return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V compute(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  870 */     Objects.requireNonNull(remappingFunction);
/*  871 */     int pos = find(k);
/*  872 */     V newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  873 */     if (newValue == null) {
/*  874 */       if (pos >= 0)
/*  875 */         if (Double.doubleToLongBits(k) == 0L) {
/*  876 */           removeNullEntry();
/*      */         } else {
/*  878 */           removeEntry(pos);
/*      */         }  
/*  880 */       return this.defRetValue;
/*      */     } 
/*  882 */     V newVal = newValue;
/*  883 */     if (pos < 0) {
/*  884 */       insert(-pos - 1, k, newVal);
/*  885 */       return newVal;
/*      */     } 
/*  887 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public V merge(double k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
/*  893 */     Objects.requireNonNull(remappingFunction);
/*  894 */     int pos = find(k);
/*  895 */     if (pos < 0 || this.value[pos] == null) {
/*  896 */       if (v == null)
/*  897 */         return this.defRetValue; 
/*  898 */       insert(-pos - 1, k, v);
/*  899 */       return v;
/*      */     } 
/*  901 */     V newValue = remappingFunction.apply(this.value[pos], v);
/*  902 */     if (newValue == null) {
/*  903 */       if (Double.doubleToLongBits(k) == 0L) {
/*  904 */         removeNullEntry();
/*      */       } else {
/*  906 */         removeEntry(pos);
/*  907 */       }  return this.defRetValue;
/*      */     } 
/*  909 */     this.value[pos] = newValue; return newValue;
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
/*  920 */     if (this.size == 0)
/*      */       return; 
/*  922 */     this.size = 0;
/*  923 */     this.containsNullKey = false;
/*  924 */     Arrays.fill(this.key, 0.0D);
/*  925 */     Arrays.fill((Object[])this.value, (Object)null);
/*  926 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  930 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  934 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2ObjectMap.Entry<V>, Map.Entry<Double, V>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  946 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  952 */       return Double2ObjectLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  956 */       return Double2ObjectLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  960 */       V oldValue = Double2ObjectLinkedOpenHashMap.this.value[this.index];
/*  961 */       Double2ObjectLinkedOpenHashMap.this.value[this.index] = v;
/*  962 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  972 */       return Double.valueOf(Double2ObjectLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  977 */       if (!(o instanceof Map.Entry))
/*  978 */         return false; 
/*  979 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  980 */       return (Double.doubleToLongBits(Double2ObjectLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && 
/*  981 */         Objects.equals(Double2ObjectLinkedOpenHashMap.this.value[this.index], e.getValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  985 */       return HashCommon.double2int(Double2ObjectLinkedOpenHashMap.this.key[this.index]) ^ (
/*  986 */         (Double2ObjectLinkedOpenHashMap.this.value[this.index] == null) ? 0 : Double2ObjectLinkedOpenHashMap.this.value[this.index].hashCode());
/*      */     }
/*      */     
/*      */     public String toString() {
/*  990 */       return Double2ObjectLinkedOpenHashMap.this.key[this.index] + "=>" + Double2ObjectLinkedOpenHashMap.this.value[this.index];
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
/* 1001 */     if (this.size == 0) {
/* 1002 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1005 */     if (this.first == i) {
/* 1006 */       this.first = (int)this.link[i];
/* 1007 */       if (0 <= this.first)
/*      */       {
/* 1009 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1013 */     if (this.last == i) {
/* 1014 */       this.last = (int)(this.link[i] >>> 32L);
/* 1015 */       if (0 <= this.last)
/*      */       {
/* 1017 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1021 */     long linki = this.link[i];
/* 1022 */     int prev = (int)(linki >>> 32L);
/* 1023 */     int next = (int)linki;
/* 1024 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1025 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1038 */     if (this.size == 1) {
/* 1039 */       this.first = this.last = d;
/*      */       
/* 1041 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1044 */     if (this.first == s) {
/* 1045 */       this.first = d;
/* 1046 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1047 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1050 */     if (this.last == s) {
/* 1051 */       this.last = d;
/* 1052 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1053 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1056 */     long links = this.link[s];
/* 1057 */     int prev = (int)(links >>> 32L);
/* 1058 */     int next = (int)links;
/* 1059 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1060 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1061 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/* 1070 */     if (this.size == 0)
/* 1071 */       throw new NoSuchElementException(); 
/* 1072 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/* 1081 */     if (this.size == 0)
/* 1082 */       throw new NoSuchElementException(); 
/* 1083 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectSortedMap<V> tailMap(double from) {
/* 1092 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectSortedMap<V> headMap(double to) {
/* 1101 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ObjectSortedMap<V> subMap(double from, double to) {
/* 1110 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1119 */     return null;
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
/* 1134 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1140 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1145 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1151 */     int index = -1;
/*      */     protected MapIterator() {
/* 1153 */       this.next = Double2ObjectLinkedOpenHashMap.this.first;
/* 1154 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1157 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1158 */         if (Double2ObjectLinkedOpenHashMap.this.containsNullKey) {
/* 1159 */           this.next = (int)Double2ObjectLinkedOpenHashMap.this.link[Double2ObjectLinkedOpenHashMap.this.n];
/* 1160 */           this.prev = Double2ObjectLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1163 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1165 */       if (Double.doubleToLongBits(Double2ObjectLinkedOpenHashMap.this.key[Double2ObjectLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1166 */         this.prev = Double2ObjectLinkedOpenHashMap.this.last;
/* 1167 */         this.index = Double2ObjectLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1171 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2ObjectLinkedOpenHashMap.this.mask;
/*      */       
/* 1173 */       while (Double.doubleToLongBits(Double2ObjectLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1174 */         if (Double.doubleToLongBits(Double2ObjectLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1176 */           this.next = (int)Double2ObjectLinkedOpenHashMap.this.link[pos];
/* 1177 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1180 */         pos = pos + 1 & Double2ObjectLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1182 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1185 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1188 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1191 */       if (this.index >= 0)
/*      */         return; 
/* 1193 */       if (this.prev == -1) {
/* 1194 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1197 */       if (this.next == -1) {
/* 1198 */         this.index = Double2ObjectLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1201 */       int pos = Double2ObjectLinkedOpenHashMap.this.first;
/* 1202 */       this.index = 1;
/* 1203 */       while (pos != this.prev) {
/* 1204 */         pos = (int)Double2ObjectLinkedOpenHashMap.this.link[pos];
/* 1205 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1209 */       ensureIndexKnown();
/* 1210 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1213 */       ensureIndexKnown();
/* 1214 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1217 */       if (!hasNext())
/* 1218 */         throw new NoSuchElementException(); 
/* 1219 */       this.curr = this.next;
/* 1220 */       this.next = (int)Double2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1221 */       this.prev = this.curr;
/* 1222 */       if (this.index >= 0)
/* 1223 */         this.index++; 
/* 1224 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1227 */       if (!hasPrevious())
/* 1228 */         throw new NoSuchElementException(); 
/* 1229 */       this.curr = this.prev;
/* 1230 */       this.prev = (int)(Double2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1231 */       this.next = this.curr;
/* 1232 */       if (this.index >= 0)
/* 1233 */         this.index--; 
/* 1234 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1237 */       ensureIndexKnown();
/* 1238 */       if (this.curr == -1)
/* 1239 */         throw new IllegalStateException(); 
/* 1240 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1245 */         this.index--;
/* 1246 */         this.prev = (int)(Double2ObjectLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1248 */         this.next = (int)Double2ObjectLinkedOpenHashMap.this.link[this.curr];
/* 1249 */       }  Double2ObjectLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1254 */       if (this.prev == -1) {
/* 1255 */         Double2ObjectLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1257 */         Double2ObjectLinkedOpenHashMap.this.link[this.prev] = Double2ObjectLinkedOpenHashMap.this.link[this.prev] ^ (Double2ObjectLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1258 */       }  if (this.next == -1) {
/* 1259 */         Double2ObjectLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1261 */         Double2ObjectLinkedOpenHashMap.this.link[this.next] = Double2ObjectLinkedOpenHashMap.this.link[this.next] ^ (Double2ObjectLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1262 */       }  int pos = this.curr;
/* 1263 */       this.curr = -1;
/* 1264 */       if (pos == Double2ObjectLinkedOpenHashMap.this.n) {
/* 1265 */         Double2ObjectLinkedOpenHashMap.this.containsNullKey = false;
/* 1266 */         Double2ObjectLinkedOpenHashMap.this.value[Double2ObjectLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1269 */         double[] key = Double2ObjectLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1273 */           pos = (last = pos) + 1 & Double2ObjectLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1275 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1276 */               key[last] = 0.0D;
/* 1277 */               Double2ObjectLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1280 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ObjectLinkedOpenHashMap.this.mask;
/* 1281 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1283 */             pos = pos + 1 & Double2ObjectLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1285 */           key[last] = curr;
/* 1286 */           Double2ObjectLinkedOpenHashMap.this.value[last] = Double2ObjectLinkedOpenHashMap.this.value[pos];
/* 1287 */           if (this.next == pos)
/* 1288 */             this.next = last; 
/* 1289 */           if (this.prev == pos)
/* 1290 */             this.prev = last; 
/* 1291 */           Double2ObjectLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Double2ObjectMap.Entry<V> ok) {
/* 1308 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2ObjectMap.Entry<V> ok) {
/* 1311 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2ObjectMap.Entry<V>> { private Double2ObjectLinkedOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1319 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2ObjectLinkedOpenHashMap<V>.MapEntry next() {
/* 1323 */       return this.entry = new Double2ObjectLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
/* 1327 */       return this.entry = new Double2ObjectLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1331 */       super.remove();
/* 1332 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1336 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2ObjectMap.Entry<V>> { final Double2ObjectLinkedOpenHashMap<V>.MapEntry entry = new Double2ObjectLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1340 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2ObjectLinkedOpenHashMap<V>.MapEntry next() {
/* 1344 */       this.entry.index = nextEntry();
/* 1345 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2ObjectLinkedOpenHashMap<V>.MapEntry previous() {
/* 1349 */       this.entry.index = previousEntry();
/* 1350 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2ObjectMap.Entry<V>> implements Double2ObjectSortedMap.FastSortedEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>> iterator() {
/* 1358 */       return (ObjectBidirectionalIterator<Double2ObjectMap.Entry<V>>)new Double2ObjectLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2ObjectMap.Entry<V>> comparator() {
/* 1362 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2ObjectMap.Entry<V>> subSet(Double2ObjectMap.Entry<V> fromElement, Double2ObjectMap.Entry<V> toElement) {
/* 1367 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ObjectMap.Entry<V>> headSet(Double2ObjectMap.Entry<V> toElement) {
/* 1371 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ObjectMap.Entry<V>> tailSet(Double2ObjectMap.Entry<V> fromElement) {
/* 1375 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2ObjectMap.Entry<V> first() {
/* 1379 */       if (Double2ObjectLinkedOpenHashMap.this.size == 0)
/* 1380 */         throw new NoSuchElementException(); 
/* 1381 */       return new Double2ObjectLinkedOpenHashMap.MapEntry(Double2ObjectLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2ObjectMap.Entry<V> last() {
/* 1385 */       if (Double2ObjectLinkedOpenHashMap.this.size == 0)
/* 1386 */         throw new NoSuchElementException(); 
/* 1387 */       return new Double2ObjectLinkedOpenHashMap.MapEntry(Double2ObjectLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1392 */       if (!(o instanceof Map.Entry))
/* 1393 */         return false; 
/* 1394 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1395 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1396 */         return false; 
/* 1397 */       double k = ((Double)e.getKey()).doubleValue();
/* 1398 */       V v = (V)e.getValue();
/* 1399 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1400 */         return (Double2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Double2ObjectLinkedOpenHashMap.this.value[Double2ObjectLinkedOpenHashMap.this.n], v));
/*      */       }
/* 1402 */       double[] key = Double2ObjectLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1405 */       if (Double.doubleToLongBits(
/* 1406 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1408 */         return false; } 
/* 1409 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1410 */         return Objects.equals(Double2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */       }
/*      */       while (true) {
/* 1413 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/* 1414 */           return false; 
/* 1415 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1416 */           return Objects.equals(Double2ObjectLinkedOpenHashMap.this.value[pos], v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1422 */       if (!(o instanceof Map.Entry))
/* 1423 */         return false; 
/* 1424 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1425 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1426 */         return false; 
/* 1427 */       double k = ((Double)e.getKey()).doubleValue();
/* 1428 */       V v = (V)e.getValue();
/* 1429 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1430 */         if (Double2ObjectLinkedOpenHashMap.this.containsNullKey && Objects.equals(Double2ObjectLinkedOpenHashMap.this.value[Double2ObjectLinkedOpenHashMap.this.n], v)) {
/* 1431 */           Double2ObjectLinkedOpenHashMap.this.removeNullEntry();
/* 1432 */           return true;
/*      */         } 
/* 1434 */         return false;
/*      */       } 
/*      */       
/* 1437 */       double[] key = Double2ObjectLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1440 */       if (Double.doubleToLongBits(
/* 1441 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1443 */         return false; } 
/* 1444 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1445 */         if (Objects.equals(Double2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1446 */           Double2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1447 */           return true;
/*      */         } 
/* 1449 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1452 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ObjectLinkedOpenHashMap.this.mask]) == 0L)
/* 1453 */           return false; 
/* 1454 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1455 */           Objects.equals(Double2ObjectLinkedOpenHashMap.this.value[pos], v)) {
/* 1456 */           Double2ObjectLinkedOpenHashMap.this.removeEntry(pos);
/* 1457 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1464 */       return Double2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1468 */       Double2ObjectLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2ObjectMap.Entry<V>> iterator(Double2ObjectMap.Entry<V> from) {
/* 1483 */       return new Double2ObjectLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2ObjectMap.Entry<V>> fastIterator() {
/* 1494 */       return new Double2ObjectLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2ObjectMap.Entry<V>> fastIterator(Double2ObjectMap.Entry<V> from) {
/* 1509 */       return new Double2ObjectLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/* 1514 */       for (int i = Double2ObjectLinkedOpenHashMap.this.size, next = Double2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1515 */         int curr = next;
/* 1516 */         next = (int)Double2ObjectLinkedOpenHashMap.this.link[curr];
/* 1517 */         consumer.accept(new AbstractDouble2ObjectMap.BasicEntry<>(Double2ObjectLinkedOpenHashMap.this.key[curr], Double2ObjectLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ObjectMap.Entry<V>> consumer) {
/* 1523 */       AbstractDouble2ObjectMap.BasicEntry<V> entry = new AbstractDouble2ObjectMap.BasicEntry<>();
/* 1524 */       for (int i = Double2ObjectLinkedOpenHashMap.this.size, next = Double2ObjectLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1525 */         int curr = next;
/* 1526 */         next = (int)Double2ObjectLinkedOpenHashMap.this.link[curr];
/* 1527 */         entry.key = Double2ObjectLinkedOpenHashMap.this.key[curr];
/* 1528 */         entry.value = Double2ObjectLinkedOpenHashMap.this.value[curr];
/* 1529 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2ObjectSortedMap.FastSortedEntrySet<V> double2ObjectEntrySet() {
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
/*      */     implements DoubleListIterator
/*      */   {
/*      */     public KeyIterator(double k) {
/* 1550 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1554 */       return Double2ObjectLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1561 */       return Double2ObjectLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1567 */       return new Double2ObjectLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1571 */       return new Double2ObjectLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1576 */       if (Double2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1577 */         consumer.accept(Double2ObjectLinkedOpenHashMap.this.key[Double2ObjectLinkedOpenHashMap.this.n]); 
/* 1578 */       for (int pos = Double2ObjectLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1579 */         double k = Double2ObjectLinkedOpenHashMap.this.key[pos];
/* 1580 */         if (Double.doubleToLongBits(k) != 0L)
/* 1581 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1586 */       return Double2ObjectLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1590 */       return Double2ObjectLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1594 */       int oldSize = Double2ObjectLinkedOpenHashMap.this.size;
/* 1595 */       Double2ObjectLinkedOpenHashMap.this.remove(k);
/* 1596 */       return (Double2ObjectLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1600 */       Double2ObjectLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1604 */       if (Double2ObjectLinkedOpenHashMap.this.size == 0)
/* 1605 */         throw new NoSuchElementException(); 
/* 1606 */       return Double2ObjectLinkedOpenHashMap.this.key[Double2ObjectLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1610 */       if (Double2ObjectLinkedOpenHashMap.this.size == 0)
/* 1611 */         throw new NoSuchElementException(); 
/* 1612 */       return Double2ObjectLinkedOpenHashMap.this.key[Double2ObjectLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1616 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1620 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1624 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1628 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
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
/* 1649 */       return Double2ObjectLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1656 */       return Double2ObjectLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ObjectCollection<V> values() {
/* 1661 */     if (this.values == null)
/* 1662 */       this.values = (ObjectCollection<V>)new AbstractObjectCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1665 */             return (ObjectIterator<V>)new Double2ObjectLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1669 */             return Double2ObjectLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1673 */             return Double2ObjectLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1677 */             Double2ObjectLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1682 */             if (Double2ObjectLinkedOpenHashMap.this.containsNullKey)
/* 1683 */               consumer.accept(Double2ObjectLinkedOpenHashMap.this.value[Double2ObjectLinkedOpenHashMap.this.n]); 
/* 1684 */             for (int pos = Double2ObjectLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1685 */               if (Double.doubleToLongBits(Double2ObjectLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1686 */                 consumer.accept(Double2ObjectLinkedOpenHashMap.this.value[pos]); 
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
/* 1754 */     double[] key = this.key;
/* 1755 */     V[] value = this.value;
/* 1756 */     int mask = newN - 1;
/* 1757 */     double[] newKey = new double[newN + 1];
/* 1758 */     V[] newValue = (V[])new Object[newN + 1];
/* 1759 */     int i = this.first, prev = -1, newPrev = -1;
/* 1760 */     long[] link = this.link;
/* 1761 */     long[] newLink = new long[newN + 1];
/* 1762 */     this.first = -1;
/* 1763 */     for (int j = this.size; j-- != 0; ) {
/* 1764 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1765 */         pos = newN;
/*      */       } else {
/* 1767 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1768 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
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
/*      */   public Double2ObjectLinkedOpenHashMap<V> clone() {
/*      */     Double2ObjectLinkedOpenHashMap<V> c;
/*      */     try {
/* 1812 */       c = (Double2ObjectLinkedOpenHashMap<V>)super.clone();
/* 1813 */     } catch (CloneNotSupportedException cantHappen) {
/* 1814 */       throw new InternalError();
/*      */     } 
/* 1816 */     c.keys = null;
/* 1817 */     c.values = null;
/* 1818 */     c.entries = null;
/* 1819 */     c.containsNullKey = this.containsNullKey;
/* 1820 */     c.key = (double[])this.key.clone();
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
/* 1838 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1839 */         i++; 
/* 1840 */       t = HashCommon.double2int(this.key[i]);
/* 1841 */       if (this != this.value[i])
/* 1842 */         t ^= (this.value[i] == null) ? 0 : this.value[i].hashCode(); 
/* 1843 */       h += t;
/* 1844 */       i++;
/*      */     } 
/*      */     
/* 1847 */     if (this.containsNullKey)
/* 1848 */       h += (this.value[this.n] == null) ? 0 : this.value[this.n].hashCode(); 
/* 1849 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1852 */     double[] key = this.key;
/* 1853 */     V[] value = this.value;
/* 1854 */     MapIterator i = new MapIterator();
/* 1855 */     s.defaultWriteObject();
/* 1856 */     for (int j = this.size; j-- != 0; ) {
/* 1857 */       int e = i.nextEntry();
/* 1858 */       s.writeDouble(key[e]);
/* 1859 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1864 */     s.defaultReadObject();
/* 1865 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1866 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1867 */     this.mask = this.n - 1;
/* 1868 */     double[] key = this.key = new double[this.n + 1];
/* 1869 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1870 */     long[] link = this.link = new long[this.n + 1];
/* 1871 */     int prev = -1;
/* 1872 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1875 */     for (int i = this.size; i-- != 0; ) {
/* 1876 */       int pos; double k = s.readDouble();
/* 1877 */       V v = (V)s.readObject();
/* 1878 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1879 */         pos = this.n;
/* 1880 */         this.containsNullKey = true;
/*      */       } else {
/* 1882 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1883 */         while (Double.doubleToLongBits(key[pos]) != 0L)
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


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ObjectLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */