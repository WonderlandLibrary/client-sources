/*      */ package it.unimi.dsi.fastutil.doubles;
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
/*      */ public class Double2ReferenceLinkedOpenHashMap<V>
/*      */   extends AbstractDouble2ReferenceSortedMap<V>
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
/*      */   protected transient Double2ReferenceSortedMap.FastSortedEntrySet<V> entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient ReferenceCollection<V> values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceLinkedOpenHashMap(int expected, float f) {
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
/*      */   public Double2ReferenceLinkedOpenHashMap(int expected) {
/*  171 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceLinkedOpenHashMap() {
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
/*      */   public Double2ReferenceLinkedOpenHashMap(Map<? extends Double, ? extends V> m, float f) {
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
/*      */   public Double2ReferenceLinkedOpenHashMap(Map<? extends Double, ? extends V> m) {
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
/*      */   public Double2ReferenceLinkedOpenHashMap(Double2ReferenceMap<V> m, float f) {
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
/*      */   public Double2ReferenceLinkedOpenHashMap(Double2ReferenceMap<V> m) {
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
/*      */   public Double2ReferenceLinkedOpenHashMap(double[] k, V[] v, float f) {
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
/*      */   public Double2ReferenceLinkedOpenHashMap(double[] k, V[] v) {
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
/*  745 */     if (this.containsNullKey && value[this.n] == v)
/*  746 */       return true; 
/*  747 */     for (int i = this.n; i-- != 0;) {
/*  748 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
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
/*  790 */       if (this.containsNullKey && v == this.value[this.n]) {
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
/*  804 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  805 */       removeEntry(pos);
/*  806 */       return true;
/*      */     } 
/*      */     while (true) {
/*  809 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  810 */         return false; 
/*  811 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  812 */         removeEntry(pos);
/*  813 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, V oldValue, V v) {
/*  820 */     int pos = find(k);
/*  821 */     if (pos < 0 || oldValue != this.value[pos])
/*  822 */       return false; 
/*  823 */     this.value[pos] = v;
/*  824 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public V replace(double k, V v) {
/*  829 */     int pos = find(k);
/*  830 */     if (pos < 0)
/*  831 */       return this.defRetValue; 
/*  832 */     V oldValue = this.value[pos];
/*  833 */     this.value[pos] = v;
/*  834 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public V computeIfAbsent(double k, DoubleFunction<? extends V> mappingFunction) {
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
/*      */   public V computeIfPresent(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  851 */     Objects.requireNonNull(remappingFunction);
/*  852 */     int pos = find(k);
/*  853 */     if (pos < 0)
/*  854 */       return this.defRetValue; 
/*  855 */     V newValue = remappingFunction.apply(Double.valueOf(k), this.value[pos]);
/*  856 */     if (newValue == null) {
/*  857 */       if (Double.doubleToLongBits(k) == 0L) {
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
/*      */   public V compute(double k, BiFunction<? super Double, ? super V, ? extends V> remappingFunction) {
/*  869 */     Objects.requireNonNull(remappingFunction);
/*  870 */     int pos = find(k);
/*  871 */     V newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? this.value[pos] : null);
/*  872 */     if (newValue == null) {
/*  873 */       if (pos >= 0)
/*  874 */         if (Double.doubleToLongBits(k) == 0L) {
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
/*      */   public V merge(double k, V v, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
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
/*  902 */       if (Double.doubleToLongBits(k) == 0L) {
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
/*  923 */     Arrays.fill(this.key, 0.0D);
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
/*      */     implements Double2ReferenceMap.Entry<V>, Map.Entry<Double, V>
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
/*      */     public double getDoubleKey() {
/*  951 */       return Double2ReferenceLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public V getValue() {
/*  955 */       return Double2ReferenceLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public V setValue(V v) {
/*  959 */       V oldValue = Double2ReferenceLinkedOpenHashMap.this.value[this.index];
/*  960 */       Double2ReferenceLinkedOpenHashMap.this.value[this.index] = v;
/*  961 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  971 */       return Double.valueOf(Double2ReferenceLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  976 */       if (!(o instanceof Map.Entry))
/*  977 */         return false; 
/*  978 */       Map.Entry<Double, V> e = (Map.Entry<Double, V>)o;
/*  979 */       return (Double.doubleToLongBits(Double2ReferenceLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2ReferenceLinkedOpenHashMap.this.value[this.index] == e
/*  980 */         .getValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  984 */       return HashCommon.double2int(Double2ReferenceLinkedOpenHashMap.this.key[this.index]) ^ (
/*  985 */         (Double2ReferenceLinkedOpenHashMap.this.value[this.index] == null) ? 0 : System.identityHashCode(Double2ReferenceLinkedOpenHashMap.this.value[this.index]));
/*      */     }
/*      */     
/*      */     public String toString() {
/*  989 */       return Double2ReferenceLinkedOpenHashMap.this.key[this.index] + "=>" + Double2ReferenceLinkedOpenHashMap.this.value[this.index];
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
/*      */   public double firstDoubleKey() {
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
/*      */   public double lastDoubleKey() {
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
/*      */   public Double2ReferenceSortedMap<V> tailMap(double from) {
/* 1091 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceSortedMap<V> headMap(double to) {
/* 1100 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ReferenceSortedMap<V> subMap(double from, double to) {
/* 1109 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
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
/* 1152 */       this.next = Double2ReferenceLinkedOpenHashMap.this.first;
/* 1153 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1156 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1157 */         if (Double2ReferenceLinkedOpenHashMap.this.containsNullKey) {
/* 1158 */           this.next = (int)Double2ReferenceLinkedOpenHashMap.this.link[Double2ReferenceLinkedOpenHashMap.this.n];
/* 1159 */           this.prev = Double2ReferenceLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1162 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1164 */       if (Double.doubleToLongBits(Double2ReferenceLinkedOpenHashMap.this.key[Double2ReferenceLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1165 */         this.prev = Double2ReferenceLinkedOpenHashMap.this.last;
/* 1166 */         this.index = Double2ReferenceLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1170 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2ReferenceLinkedOpenHashMap.this.mask;
/*      */       
/* 1172 */       while (Double.doubleToLongBits(Double2ReferenceLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1173 */         if (Double.doubleToLongBits(Double2ReferenceLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1175 */           this.next = (int)Double2ReferenceLinkedOpenHashMap.this.link[pos];
/* 1176 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1179 */         pos = pos + 1 & Double2ReferenceLinkedOpenHashMap.this.mask;
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
/* 1197 */         this.index = Double2ReferenceLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1200 */       int pos = Double2ReferenceLinkedOpenHashMap.this.first;
/* 1201 */       this.index = 1;
/* 1202 */       while (pos != this.prev) {
/* 1203 */         pos = (int)Double2ReferenceLinkedOpenHashMap.this.link[pos];
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
/* 1219 */       this.next = (int)Double2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1220 */       this.prev = this.curr;
/* 1221 */       if (this.index >= 0)
/* 1222 */         this.index++; 
/* 1223 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1226 */       if (!hasPrevious())
/* 1227 */         throw new NoSuchElementException(); 
/* 1228 */       this.curr = this.prev;
/* 1229 */       this.prev = (int)(Double2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1245 */         this.prev = (int)(Double2ReferenceLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1247 */         this.next = (int)Double2ReferenceLinkedOpenHashMap.this.link[this.curr];
/* 1248 */       }  Double2ReferenceLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1253 */       if (this.prev == -1) {
/* 1254 */         Double2ReferenceLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1256 */         Double2ReferenceLinkedOpenHashMap.this.link[this.prev] = Double2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ (Double2ReferenceLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1257 */       }  if (this.next == -1) {
/* 1258 */         Double2ReferenceLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1260 */         Double2ReferenceLinkedOpenHashMap.this.link[this.next] = Double2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (Double2ReferenceLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1261 */       }  int pos = this.curr;
/* 1262 */       this.curr = -1;
/* 1263 */       if (pos == Double2ReferenceLinkedOpenHashMap.this.n) {
/* 1264 */         Double2ReferenceLinkedOpenHashMap.this.containsNullKey = false;
/* 1265 */         Double2ReferenceLinkedOpenHashMap.this.value[Double2ReferenceLinkedOpenHashMap.this.n] = null;
/*      */       } else {
/*      */         
/* 1268 */         double[] key = Double2ReferenceLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1272 */           pos = (last = pos) + 1 & Double2ReferenceLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1274 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1275 */               key[last] = 0.0D;
/* 1276 */               Double2ReferenceLinkedOpenHashMap.this.value[last] = null;
/*      */               return;
/*      */             } 
/* 1279 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ReferenceLinkedOpenHashMap.this.mask;
/* 1280 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1282 */             pos = pos + 1 & Double2ReferenceLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1284 */           key[last] = curr;
/* 1285 */           Double2ReferenceLinkedOpenHashMap.this.value[last] = Double2ReferenceLinkedOpenHashMap.this.value[pos];
/* 1286 */           if (this.next == pos)
/* 1287 */             this.next = last; 
/* 1288 */           if (this.prev == pos)
/* 1289 */             this.prev = last; 
/* 1290 */           Double2ReferenceLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1295 */       int i = n;
/* 1296 */       while (i-- != 0 && hasNext())
/* 1297 */         nextEntry(); 
/* 1298 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1301 */       int i = n;
/* 1302 */       while (i-- != 0 && hasPrevious())
/* 1303 */         previousEntry(); 
/* 1304 */       return n - i - 1;
/*      */     }
/*      */     public void set(Double2ReferenceMap.Entry<V> ok) {
/* 1307 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2ReferenceMap.Entry<V> ok) {
/* 1310 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2ReferenceMap.Entry<V>> { private Double2ReferenceLinkedOpenHashMap<V>.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1318 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
/* 1322 */       return this.entry = new Double2ReferenceLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
/* 1326 */       return this.entry = new Double2ReferenceLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1330 */       super.remove();
/* 1331 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1335 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2ReferenceMap.Entry<V>> { final Double2ReferenceLinkedOpenHashMap<V>.MapEntry entry = new Double2ReferenceLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1339 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2ReferenceLinkedOpenHashMap<V>.MapEntry next() {
/* 1343 */       this.entry.index = nextEntry();
/* 1344 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2ReferenceLinkedOpenHashMap<V>.MapEntry previous() {
/* 1348 */       this.entry.index = previousEntry();
/* 1349 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2ReferenceMap.Entry<V>> implements Double2ReferenceSortedMap.FastSortedEntrySet<V> { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>> iterator() {
/* 1357 */       return (ObjectBidirectionalIterator<Double2ReferenceMap.Entry<V>>)new Double2ReferenceLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2ReferenceMap.Entry<V>> comparator() {
/* 1361 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> subSet(Double2ReferenceMap.Entry<V> fromElement, Double2ReferenceMap.Entry<V> toElement) {
/* 1366 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> headSet(Double2ReferenceMap.Entry<V> toElement) {
/* 1370 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2ReferenceMap.Entry<V>> tailSet(Double2ReferenceMap.Entry<V> fromElement) {
/* 1374 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> first() {
/* 1378 */       if (Double2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1379 */         throw new NoSuchElementException(); 
/* 1380 */       return new Double2ReferenceLinkedOpenHashMap.MapEntry(Double2ReferenceLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2ReferenceMap.Entry<V> last() {
/* 1384 */       if (Double2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1385 */         throw new NoSuchElementException(); 
/* 1386 */       return new Double2ReferenceLinkedOpenHashMap.MapEntry(Double2ReferenceLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1391 */       if (!(o instanceof Map.Entry))
/* 1392 */         return false; 
/* 1393 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1394 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1395 */         return false; 
/* 1396 */       double k = ((Double)e.getKey()).doubleValue();
/* 1397 */       V v = (V)e.getValue();
/* 1398 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1399 */         return (Double2ReferenceLinkedOpenHashMap.this.containsNullKey && Double2ReferenceLinkedOpenHashMap.this.value[Double2ReferenceLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1401 */       double[] key = Double2ReferenceLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1404 */       if (Double.doubleToLongBits(
/* 1405 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ReferenceLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1407 */         return false; } 
/* 1408 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1409 */         return (Double2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1412 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ReferenceLinkedOpenHashMap.this.mask]) == 0L)
/* 1413 */           return false; 
/* 1414 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1415 */           return (Double2ReferenceLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1421 */       if (!(o instanceof Map.Entry))
/* 1422 */         return false; 
/* 1423 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1424 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1425 */         return false; 
/* 1426 */       double k = ((Double)e.getKey()).doubleValue();
/* 1427 */       V v = (V)e.getValue();
/* 1428 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1429 */         if (Double2ReferenceLinkedOpenHashMap.this.containsNullKey && Double2ReferenceLinkedOpenHashMap.this.value[Double2ReferenceLinkedOpenHashMap.this.n] == v) {
/* 1430 */           Double2ReferenceLinkedOpenHashMap.this.removeNullEntry();
/* 1431 */           return true;
/*      */         } 
/* 1433 */         return false;
/*      */       } 
/*      */       
/* 1436 */       double[] key = Double2ReferenceLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1439 */       if (Double.doubleToLongBits(
/* 1440 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ReferenceLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1442 */         return false; } 
/* 1443 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1444 */         if (Double2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1445 */           Double2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1446 */           return true;
/*      */         } 
/* 1448 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1451 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ReferenceLinkedOpenHashMap.this.mask]) == 0L)
/* 1452 */           return false; 
/* 1453 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1454 */           Double2ReferenceLinkedOpenHashMap.this.value[pos] == v) {
/* 1455 */           Double2ReferenceLinkedOpenHashMap.this.removeEntry(pos);
/* 1456 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1463 */       return Double2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1467 */       Double2ReferenceLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2ReferenceMap.Entry<V>> iterator(Double2ReferenceMap.Entry<V> from) {
/* 1482 */       return new Double2ReferenceLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2ReferenceMap.Entry<V>> fastIterator() {
/* 1493 */       return new Double2ReferenceLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2ReferenceMap.Entry<V>> fastIterator(Double2ReferenceMap.Entry<V> from) {
/* 1508 */       return new Double2ReferenceLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/* 1513 */       for (int i = Double2ReferenceLinkedOpenHashMap.this.size, next = Double2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1514 */         int curr = next;
/* 1515 */         next = (int)Double2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1516 */         consumer.accept(new AbstractDouble2ReferenceMap.BasicEntry<>(Double2ReferenceLinkedOpenHashMap.this.key[curr], Double2ReferenceLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ReferenceMap.Entry<V>> consumer) {
/* 1522 */       AbstractDouble2ReferenceMap.BasicEntry<V> entry = new AbstractDouble2ReferenceMap.BasicEntry<>();
/* 1523 */       for (int i = Double2ReferenceLinkedOpenHashMap.this.size, next = Double2ReferenceLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1524 */         int curr = next;
/* 1525 */         next = (int)Double2ReferenceLinkedOpenHashMap.this.link[curr];
/* 1526 */         entry.key = Double2ReferenceLinkedOpenHashMap.this.key[curr];
/* 1527 */         entry.value = Double2ReferenceLinkedOpenHashMap.this.value[curr];
/* 1528 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2ReferenceSortedMap.FastSortedEntrySet<V> double2ReferenceEntrySet() {
/* 1534 */     if (this.entries == null)
/* 1535 */       this.entries = new MapEntrySet(); 
/* 1536 */     return this.entries;
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
/* 1549 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1553 */       return Double2ReferenceLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1560 */       return Double2ReferenceLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1566 */       return new Double2ReferenceLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1570 */       return new Double2ReferenceLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1575 */       if (Double2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1576 */         consumer.accept(Double2ReferenceLinkedOpenHashMap.this.key[Double2ReferenceLinkedOpenHashMap.this.n]); 
/* 1577 */       for (int pos = Double2ReferenceLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1578 */         double k = Double2ReferenceLinkedOpenHashMap.this.key[pos];
/* 1579 */         if (Double.doubleToLongBits(k) != 0L)
/* 1580 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1585 */       return Double2ReferenceLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1589 */       return Double2ReferenceLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1593 */       int oldSize = Double2ReferenceLinkedOpenHashMap.this.size;
/* 1594 */       Double2ReferenceLinkedOpenHashMap.this.remove(k);
/* 1595 */       return (Double2ReferenceLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1599 */       Double2ReferenceLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1603 */       if (Double2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1604 */         throw new NoSuchElementException(); 
/* 1605 */       return Double2ReferenceLinkedOpenHashMap.this.key[Double2ReferenceLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1609 */       if (Double2ReferenceLinkedOpenHashMap.this.size == 0)
/* 1610 */         throw new NoSuchElementException(); 
/* 1611 */       return Double2ReferenceLinkedOpenHashMap.this.key[Double2ReferenceLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1615 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1619 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1623 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1627 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1632 */     if (this.keys == null)
/* 1633 */       this.keys = new KeySet(); 
/* 1634 */     return this.keys;
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
/* 1648 */       return Double2ReferenceLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V next() {
/* 1655 */       return Double2ReferenceLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ReferenceCollection<V> values() {
/* 1660 */     if (this.values == null)
/* 1661 */       this.values = (ReferenceCollection<V>)new AbstractReferenceCollection<V>()
/*      */         {
/*      */           public ObjectIterator<V> iterator() {
/* 1664 */             return (ObjectIterator<V>)new Double2ReferenceLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1668 */             return Double2ReferenceLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(Object v) {
/* 1672 */             return Double2ReferenceLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1676 */             Double2ReferenceLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(Consumer<? super V> consumer) {
/* 1681 */             if (Double2ReferenceLinkedOpenHashMap.this.containsNullKey)
/* 1682 */               consumer.accept(Double2ReferenceLinkedOpenHashMap.this.value[Double2ReferenceLinkedOpenHashMap.this.n]); 
/* 1683 */             for (int pos = Double2ReferenceLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1684 */               if (Double.doubleToLongBits(Double2ReferenceLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1685 */                 consumer.accept(Double2ReferenceLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1688 */     return this.values;
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
/* 1705 */     return trim(this.size);
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
/* 1729 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1730 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1731 */       return true; 
/*      */     try {
/* 1733 */       rehash(l);
/* 1734 */     } catch (OutOfMemoryError cantDoIt) {
/* 1735 */       return false;
/*      */     } 
/* 1737 */     return true;
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
/* 1753 */     double[] key = this.key;
/* 1754 */     V[] value = this.value;
/* 1755 */     int mask = newN - 1;
/* 1756 */     double[] newKey = new double[newN + 1];
/* 1757 */     V[] newValue = (V[])new Object[newN + 1];
/* 1758 */     int i = this.first, prev = -1, newPrev = -1;
/* 1759 */     long[] link = this.link;
/* 1760 */     long[] newLink = new long[newN + 1];
/* 1761 */     this.first = -1;
/* 1762 */     for (int j = this.size; j-- != 0; ) {
/* 1763 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1764 */         pos = newN;
/*      */       } else {
/* 1766 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1767 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1768 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1770 */       newKey[pos] = key[i];
/* 1771 */       newValue[pos] = value[i];
/* 1772 */       if (prev != -1) {
/* 1773 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1774 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1775 */         newPrev = pos;
/*      */       } else {
/* 1777 */         newPrev = this.first = pos;
/*      */         
/* 1779 */         newLink[pos] = -1L;
/*      */       } 
/* 1781 */       int t = i;
/* 1782 */       i = (int)link[i];
/* 1783 */       prev = t;
/*      */     } 
/* 1785 */     this.link = newLink;
/* 1786 */     this.last = newPrev;
/* 1787 */     if (newPrev != -1)
/*      */     {
/* 1789 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1790 */     this.n = newN;
/* 1791 */     this.mask = mask;
/* 1792 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1793 */     this.key = newKey;
/* 1794 */     this.value = newValue;
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
/*      */   public Double2ReferenceLinkedOpenHashMap<V> clone() {
/*      */     Double2ReferenceLinkedOpenHashMap<V> c;
/*      */     try {
/* 1811 */       c = (Double2ReferenceLinkedOpenHashMap<V>)super.clone();
/* 1812 */     } catch (CloneNotSupportedException cantHappen) {
/* 1813 */       throw new InternalError();
/*      */     } 
/* 1815 */     c.keys = null;
/* 1816 */     c.values = null;
/* 1817 */     c.entries = null;
/* 1818 */     c.containsNullKey = this.containsNullKey;
/* 1819 */     c.key = (double[])this.key.clone();
/* 1820 */     c.value = (V[])this.value.clone();
/* 1821 */     c.link = (long[])this.link.clone();
/* 1822 */     return c;
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
/* 1835 */     int h = 0;
/* 1836 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1837 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1838 */         i++; 
/* 1839 */       t = HashCommon.double2int(this.key[i]);
/* 1840 */       if (this != this.value[i])
/* 1841 */         t ^= (this.value[i] == null) ? 0 : System.identityHashCode(this.value[i]); 
/* 1842 */       h += t;
/* 1843 */       i++;
/*      */     } 
/*      */     
/* 1846 */     if (this.containsNullKey)
/* 1847 */       h += (this.value[this.n] == null) ? 0 : System.identityHashCode(this.value[this.n]); 
/* 1848 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1851 */     double[] key = this.key;
/* 1852 */     V[] value = this.value;
/* 1853 */     MapIterator i = new MapIterator();
/* 1854 */     s.defaultWriteObject();
/* 1855 */     for (int j = this.size; j-- != 0; ) {
/* 1856 */       int e = i.nextEntry();
/* 1857 */       s.writeDouble(key[e]);
/* 1858 */       s.writeObject(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1863 */     s.defaultReadObject();
/* 1864 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1865 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1866 */     this.mask = this.n - 1;
/* 1867 */     double[] key = this.key = new double[this.n + 1];
/* 1868 */     V[] value = this.value = (V[])new Object[this.n + 1];
/* 1869 */     long[] link = this.link = new long[this.n + 1];
/* 1870 */     int prev = -1;
/* 1871 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1874 */     for (int i = this.size; i-- != 0; ) {
/* 1875 */       int pos; double k = s.readDouble();
/* 1876 */       V v = (V)s.readObject();
/* 1877 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1878 */         pos = this.n;
/* 1879 */         this.containsNullKey = true;
/*      */       } else {
/* 1881 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1882 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1883 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1885 */       key[pos] = k;
/* 1886 */       value[pos] = v;
/* 1887 */       if (this.first != -1) {
/* 1888 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1889 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1890 */         prev = pos; continue;
/*      */       } 
/* 1892 */       prev = this.first = pos;
/*      */       
/* 1894 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1897 */     this.last = prev;
/* 1898 */     if (prev != -1)
/*      */     {
/* 1900 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ReferenceLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */