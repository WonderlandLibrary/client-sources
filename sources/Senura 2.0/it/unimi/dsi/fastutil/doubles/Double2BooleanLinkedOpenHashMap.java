/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanCollection;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanIterator;
/*      */ import it.unimi.dsi.fastutil.booleans.BooleanListIterator;
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
/*      */ import java.util.function.DoublePredicate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Double2BooleanLinkedOpenHashMap
/*      */   extends AbstractDouble2BooleanSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient boolean[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*  108 */   protected transient int first = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   protected transient int last = -1;
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
/*      */   protected transient Double2BooleanSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient DoubleSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanLinkedOpenHashMap(int expected, float f) {
/*  154 */     if (f <= 0.0F || f > 1.0F)
/*  155 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  156 */     if (expected < 0)
/*  157 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  158 */     this.f = f;
/*  159 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  160 */     this.mask = this.n - 1;
/*  161 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  162 */     this.key = new double[this.n + 1];
/*  163 */     this.value = new boolean[this.n + 1];
/*  164 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanLinkedOpenHashMap(int expected) {
/*  173 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanLinkedOpenHashMap() {
/*  181 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanLinkedOpenHashMap(Map<? extends Double, ? extends Boolean> m, float f) {
/*  192 */     this(m.size(), f);
/*  193 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanLinkedOpenHashMap(Map<? extends Double, ? extends Boolean> m) {
/*  203 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanLinkedOpenHashMap(Double2BooleanMap m, float f) {
/*  214 */     this(m.size(), f);
/*  215 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanLinkedOpenHashMap(Double2BooleanMap m) {
/*  225 */     this(m, 0.75F);
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
/*      */   public Double2BooleanLinkedOpenHashMap(double[] k, boolean[] v, float f) {
/*  240 */     this(k.length, f);
/*  241 */     if (k.length != v.length) {
/*  242 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  244 */     for (int i = 0; i < k.length; i++) {
/*  245 */       put(k[i], v[i]);
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
/*      */   public Double2BooleanLinkedOpenHashMap(double[] k, boolean[] v) {
/*  259 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  262 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  265 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  266 */     if (needed > this.n)
/*  267 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  270 */     int needed = (int)Math.min(1073741824L, 
/*  271 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  272 */     if (needed > this.n)
/*  273 */       rehash(needed); 
/*      */   }
/*      */   private boolean removeEntry(int pos) {
/*  276 */     boolean oldValue = this.value[pos];
/*  277 */     this.size--;
/*  278 */     fixPointers(pos);
/*  279 */     shiftKeys(pos);
/*  280 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  281 */       rehash(this.n / 2); 
/*  282 */     return oldValue;
/*      */   }
/*      */   private boolean removeNullEntry() {
/*  285 */     this.containsNullKey = false;
/*  286 */     boolean oldValue = this.value[this.n];
/*  287 */     this.size--;
/*  288 */     fixPointers(this.n);
/*  289 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  290 */       rehash(this.n / 2); 
/*  291 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Boolean> m) {
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
/*      */   private void insert(int pos, double k, boolean v) {
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
/*      */   public boolean put(double k, boolean v) {
/*  345 */     int pos = find(k);
/*  346 */     if (pos < 0) {
/*  347 */       insert(-pos - 1, k, v);
/*  348 */       return this.defRetValue;
/*      */     } 
/*  350 */     boolean oldValue = this.value[pos];
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
/*      */           return;
/*      */         } 
/*  373 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  374 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  376 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  378 */       key[last] = curr;
/*  379 */       this.value[last] = this.value[pos];
/*  380 */       fixPointers(pos, last);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean remove(double k) {
/*  386 */     if (Double.doubleToLongBits(k) == 0L) {
/*  387 */       if (this.containsNullKey)
/*  388 */         return removeNullEntry(); 
/*  389 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  392 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  395 */     if (Double.doubleToLongBits(
/*  396 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  398 */       return this.defRetValue; } 
/*  399 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  400 */       return removeEntry(pos); 
/*      */     while (true) {
/*  402 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  403 */         return this.defRetValue; 
/*  404 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  405 */         return removeEntry(pos); 
/*      */     } 
/*      */   }
/*      */   private boolean setValue(int pos, boolean v) {
/*  409 */     boolean oldValue = this.value[pos];
/*  410 */     this.value[pos] = v;
/*  411 */     return oldValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeFirstBoolean() {
/*  422 */     if (this.size == 0)
/*  423 */       throw new NoSuchElementException(); 
/*  424 */     int pos = this.first;
/*      */     
/*  426 */     this.first = (int)this.link[pos];
/*  427 */     if (0 <= this.first)
/*      */     {
/*  429 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     }
/*  431 */     this.size--;
/*  432 */     boolean v = this.value[pos];
/*  433 */     if (pos == this.n) {
/*  434 */       this.containsNullKey = false;
/*      */     } else {
/*  436 */       shiftKeys(pos);
/*  437 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  438 */       rehash(this.n / 2); 
/*  439 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeLastBoolean() {
/*  449 */     if (this.size == 0)
/*  450 */       throw new NoSuchElementException(); 
/*  451 */     int pos = this.last;
/*      */     
/*  453 */     this.last = (int)(this.link[pos] >>> 32L);
/*  454 */     if (0 <= this.last)
/*      */     {
/*  456 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     }
/*  458 */     this.size--;
/*  459 */     boolean v = this.value[pos];
/*  460 */     if (pos == this.n) {
/*  461 */       this.containsNullKey = false;
/*      */     } else {
/*  463 */       shiftKeys(pos);
/*  464 */     }  if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  465 */       rehash(this.n / 2); 
/*  466 */     return v;
/*      */   }
/*      */   private void moveIndexToFirst(int i) {
/*  469 */     if (this.size == 1 || this.first == i)
/*      */       return; 
/*  471 */     if (this.last == i) {
/*  472 */       this.last = (int)(this.link[i] >>> 32L);
/*      */       
/*  474 */       this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */     } else {
/*  476 */       long linki = this.link[i];
/*  477 */       int prev = (int)(linki >>> 32L);
/*  478 */       int next = (int)linki;
/*  479 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  480 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  482 */     this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (i & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  483 */     this.link[i] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  484 */     this.first = i;
/*      */   }
/*      */   private void moveIndexToLast(int i) {
/*  487 */     if (this.size == 1 || this.last == i)
/*      */       return; 
/*  489 */     if (this.first == i) {
/*  490 */       this.first = (int)this.link[i];
/*      */       
/*  492 */       this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */     } else {
/*  494 */       long linki = this.link[i];
/*  495 */       int prev = (int)(linki >>> 32L);
/*  496 */       int next = (int)linki;
/*  497 */       this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  498 */       this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
/*      */     } 
/*  500 */     this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ i & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  501 */     this.link[i] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  502 */     this.last = i;
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
/*      */   public boolean getAndMoveToFirst(double k) {
/*  514 */     if (Double.doubleToLongBits(k) == 0L) {
/*  515 */       if (this.containsNullKey) {
/*  516 */         moveIndexToFirst(this.n);
/*  517 */         return this.value[this.n];
/*      */       } 
/*  519 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  522 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  525 */     if (Double.doubleToLongBits(
/*  526 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  528 */       return this.defRetValue; } 
/*  529 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  530 */       moveIndexToFirst(pos);
/*  531 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  535 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  536 */         return this.defRetValue; 
/*  537 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  538 */         moveIndexToFirst(pos);
/*  539 */         return this.value[pos];
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
/*      */   public boolean getAndMoveToLast(double k) {
/*  553 */     if (Double.doubleToLongBits(k) == 0L) {
/*  554 */       if (this.containsNullKey) {
/*  555 */         moveIndexToLast(this.n);
/*  556 */         return this.value[this.n];
/*      */       } 
/*  558 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  561 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  564 */     if (Double.doubleToLongBits(
/*  565 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  567 */       return this.defRetValue; } 
/*  568 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  569 */       moveIndexToLast(pos);
/*  570 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  574 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  575 */         return this.defRetValue; 
/*  576 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  577 */         moveIndexToLast(pos);
/*  578 */         return this.value[pos];
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
/*      */   public boolean putAndMoveToFirst(double k, boolean v) {
/*      */     int pos;
/*  595 */     if (Double.doubleToLongBits(k) == 0L) {
/*  596 */       if (this.containsNullKey) {
/*  597 */         moveIndexToFirst(this.n);
/*  598 */         return setValue(this.n, v);
/*      */       } 
/*  600 */       this.containsNullKey = true;
/*  601 */       pos = this.n;
/*      */     } else {
/*      */       
/*  604 */       double[] key = this.key;
/*      */       double curr;
/*  606 */       if (Double.doubleToLongBits(
/*  607 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  609 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  610 */           moveIndexToFirst(pos);
/*  611 */           return setValue(pos, v);
/*      */         } 
/*  613 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  614 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  615 */             moveIndexToFirst(pos);
/*  616 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  620 */     }  this.key[pos] = k;
/*  621 */     this.value[pos] = v;
/*  622 */     if (this.size == 0) {
/*  623 */       this.first = this.last = pos;
/*      */       
/*  625 */       this.link[pos] = -1L;
/*      */     } else {
/*  627 */       this.link[this.first] = this.link[this.first] ^ (this.link[this.first] ^ (pos & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/*  628 */       this.link[pos] = 0xFFFFFFFF00000000L | this.first & 0xFFFFFFFFL;
/*  629 */       this.first = pos;
/*      */     } 
/*  631 */     if (this.size++ >= this.maxFill) {
/*  632 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  635 */     return this.defRetValue;
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
/*      */   public boolean putAndMoveToLast(double k, boolean v) {
/*      */     int pos;
/*  650 */     if (Double.doubleToLongBits(k) == 0L) {
/*  651 */       if (this.containsNullKey) {
/*  652 */         moveIndexToLast(this.n);
/*  653 */         return setValue(this.n, v);
/*      */       } 
/*  655 */       this.containsNullKey = true;
/*  656 */       pos = this.n;
/*      */     } else {
/*      */       
/*  659 */       double[] key = this.key;
/*      */       double curr;
/*  661 */       if (Double.doubleToLongBits(
/*  662 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  664 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  665 */           moveIndexToLast(pos);
/*  666 */           return setValue(pos, v);
/*      */         } 
/*  668 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  669 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  670 */             moveIndexToLast(pos);
/*  671 */             return setValue(pos, v);
/*      */           } 
/*      */         } 
/*      */       } 
/*  675 */     }  this.key[pos] = k;
/*  676 */     this.value[pos] = v;
/*  677 */     if (this.size == 0) {
/*  678 */       this.first = this.last = pos;
/*      */       
/*  680 */       this.link[pos] = -1L;
/*      */     } else {
/*  682 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  683 */       this.link[pos] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  684 */       this.last = pos;
/*      */     } 
/*  686 */     if (this.size++ >= this.maxFill) {
/*  687 */       rehash(HashCommon.arraySize(this.size, this.f));
/*      */     }
/*      */     
/*  690 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean get(double k) {
/*  695 */     if (Double.doubleToLongBits(k) == 0L) {
/*  696 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  698 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  701 */     if (Double.doubleToLongBits(
/*  702 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  704 */       return this.defRetValue; } 
/*  705 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  706 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  709 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  710 */         return this.defRetValue; 
/*  711 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  712 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  718 */     if (Double.doubleToLongBits(k) == 0L) {
/*  719 */       return this.containsNullKey;
/*      */     }
/*  721 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  724 */     if (Double.doubleToLongBits(
/*  725 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  727 */       return false; } 
/*  728 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  729 */       return true;
/*      */     }
/*      */     while (true) {
/*  732 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  733 */         return false; 
/*  734 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  735 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  740 */     boolean[] value = this.value;
/*  741 */     double[] key = this.key;
/*  742 */     if (this.containsNullKey && value[this.n] == v)
/*  743 */       return true; 
/*  744 */     for (int i = this.n; i-- != 0;) {
/*  745 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  746 */         return true; 
/*  747 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(double k, boolean defaultValue) {
/*  753 */     if (Double.doubleToLongBits(k) == 0L) {
/*  754 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  756 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  759 */     if (Double.doubleToLongBits(
/*  760 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  762 */       return defaultValue; } 
/*  763 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  764 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  767 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  768 */         return defaultValue; 
/*  769 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  770 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(double k, boolean v) {
/*  776 */     int pos = find(k);
/*  777 */     if (pos >= 0)
/*  778 */       return this.value[pos]; 
/*  779 */     insert(-pos - 1, k, v);
/*  780 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, boolean v) {
/*  786 */     if (Double.doubleToLongBits(k) == 0L) {
/*  787 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  788 */         removeNullEntry();
/*  789 */         return true;
/*      */       } 
/*  791 */       return false;
/*      */     } 
/*      */     
/*  794 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  797 */     if (Double.doubleToLongBits(
/*  798 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  800 */       return false; } 
/*  801 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  802 */       removeEntry(pos);
/*  803 */       return true;
/*      */     } 
/*      */     while (true) {
/*  806 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  807 */         return false; 
/*  808 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  809 */         removeEntry(pos);
/*  810 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean oldValue, boolean v) {
/*  817 */     int pos = find(k);
/*  818 */     if (pos < 0 || oldValue != this.value[pos])
/*  819 */       return false; 
/*  820 */     this.value[pos] = v;
/*  821 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, boolean v) {
/*  826 */     int pos = find(k);
/*  827 */     if (pos < 0)
/*  828 */       return this.defRetValue; 
/*  829 */     boolean oldValue = this.value[pos];
/*  830 */     this.value[pos] = v;
/*  831 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(double k, DoublePredicate mappingFunction) {
/*  836 */     Objects.requireNonNull(mappingFunction);
/*  837 */     int pos = find(k);
/*  838 */     if (pos >= 0)
/*  839 */       return this.value[pos]; 
/*  840 */     boolean newValue = mappingFunction.test(k);
/*  841 */     insert(-pos - 1, k, newValue);
/*  842 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsentNullable(double k, DoubleFunction<? extends Boolean> mappingFunction) {
/*  848 */     Objects.requireNonNull(mappingFunction);
/*  849 */     int pos = find(k);
/*  850 */     if (pos >= 0)
/*  851 */       return this.value[pos]; 
/*  852 */     Boolean newValue = mappingFunction.apply(k);
/*  853 */     if (newValue == null)
/*  854 */       return this.defRetValue; 
/*  855 */     boolean v = newValue.booleanValue();
/*  856 */     insert(-pos - 1, k, v);
/*  857 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean computeIfPresent(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  863 */     Objects.requireNonNull(remappingFunction);
/*  864 */     int pos = find(k);
/*  865 */     if (pos < 0)
/*  866 */       return this.defRetValue; 
/*  867 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  868 */     if (newValue == null) {
/*  869 */       if (Double.doubleToLongBits(k) == 0L) {
/*  870 */         removeNullEntry();
/*      */       } else {
/*  872 */         removeEntry(pos);
/*  873 */       }  return this.defRetValue;
/*      */     } 
/*  875 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean compute(double k, BiFunction<? super Double, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  881 */     Objects.requireNonNull(remappingFunction);
/*  882 */     int pos = find(k);
/*  883 */     Boolean newValue = remappingFunction.apply(Double.valueOf(k), 
/*  884 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  885 */     if (newValue == null) {
/*  886 */       if (pos >= 0)
/*  887 */         if (Double.doubleToLongBits(k) == 0L) {
/*  888 */           removeNullEntry();
/*      */         } else {
/*  890 */           removeEntry(pos);
/*      */         }  
/*  892 */       return this.defRetValue;
/*      */     } 
/*  894 */     boolean newVal = newValue.booleanValue();
/*  895 */     if (pos < 0) {
/*  896 */       insert(-pos - 1, k, newVal);
/*  897 */       return newVal;
/*      */     } 
/*  899 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean merge(double k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  905 */     Objects.requireNonNull(remappingFunction);
/*  906 */     int pos = find(k);
/*  907 */     if (pos < 0) {
/*  908 */       insert(-pos - 1, k, v);
/*  909 */       return v;
/*      */     } 
/*  911 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  912 */     if (newValue == null) {
/*  913 */       if (Double.doubleToLongBits(k) == 0L) {
/*  914 */         removeNullEntry();
/*      */       } else {
/*  916 */         removeEntry(pos);
/*  917 */       }  return this.defRetValue;
/*      */     } 
/*  919 */     this.value[pos] = newValue.booleanValue(); return newValue.booleanValue();
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
/*  930 */     if (this.size == 0)
/*      */       return; 
/*  932 */     this.size = 0;
/*  933 */     this.containsNullKey = false;
/*  934 */     Arrays.fill(this.key, 0.0D);
/*  935 */     this.first = this.last = -1;
/*      */   }
/*      */   
/*      */   public int size() {
/*  939 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  943 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2BooleanMap.Entry, Map.Entry<Double, Boolean>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  955 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  961 */       return Double2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  965 */       return Double2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  969 */       boolean oldValue = Double2BooleanLinkedOpenHashMap.this.value[this.index];
/*  970 */       Double2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  971 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  981 */       return Double.valueOf(Double2BooleanLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  991 */       return Boolean.valueOf(Double2BooleanLinkedOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean setValue(Boolean v) {
/* 1001 */       return Boolean.valueOf(setValue(v.booleanValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1006 */       if (!(o instanceof Map.Entry))
/* 1007 */         return false; 
/* 1008 */       Map.Entry<Double, Boolean> e = (Map.Entry<Double, Boolean>)o;
/* 1009 */       return (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2BooleanLinkedOpenHashMap.this.value[this.index] == ((Boolean)e
/* 1010 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1014 */       return HashCommon.double2int(Double2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Double2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1018 */       return Double2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Double2BooleanLinkedOpenHashMap.this.value[this.index];
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
/* 1029 */     if (this.size == 0) {
/* 1030 */       this.first = this.last = -1;
/*      */       return;
/*      */     } 
/* 1033 */     if (this.first == i) {
/* 1034 */       this.first = (int)this.link[i];
/* 1035 */       if (0 <= this.first)
/*      */       {
/* 1037 */         this.link[this.first] = this.link[this.first] | 0xFFFFFFFF00000000L;
/*      */       }
/*      */       return;
/*      */     } 
/* 1041 */     if (this.last == i) {
/* 1042 */       this.last = (int)(this.link[i] >>> 32L);
/* 1043 */       if (0 <= this.last)
/*      */       {
/* 1045 */         this.link[this.last] = this.link[this.last] | 0xFFFFFFFFL;
/*      */       }
/*      */       return;
/*      */     } 
/* 1049 */     long linki = this.link[i];
/* 1050 */     int prev = (int)(linki >>> 32L);
/* 1051 */     int next = (int)linki;
/* 1052 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ linki & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1053 */     this.link[next] = this.link[next] ^ (this.link[next] ^ linki & 0xFFFFFFFF00000000L) & 0xFFFFFFFF00000000L;
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
/* 1066 */     if (this.size == 1) {
/* 1067 */       this.first = this.last = d;
/*      */       
/* 1069 */       this.link[d] = -1L;
/*      */       return;
/*      */     } 
/* 1072 */     if (this.first == s) {
/* 1073 */       this.first = d;
/* 1074 */       this.link[(int)this.link[s]] = this.link[(int)this.link[s]] ^ (this.link[(int)this.link[s]] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1075 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1078 */     if (this.last == s) {
/* 1079 */       this.last = d;
/* 1080 */       this.link[(int)(this.link[s] >>> 32L)] = this.link[(int)(this.link[s] >>> 32L)] ^ (this.link[(int)(this.link[s] >>> 32L)] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1081 */       this.link[d] = this.link[s];
/*      */       return;
/*      */     } 
/* 1084 */     long links = this.link[s];
/* 1085 */     int prev = (int)(links >>> 32L);
/* 1086 */     int next = (int)links;
/* 1087 */     this.link[prev] = this.link[prev] ^ (this.link[prev] ^ d & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1088 */     this.link[next] = this.link[next] ^ (this.link[next] ^ (d & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1089 */     this.link[d] = links;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double firstDoubleKey() {
/* 1098 */     if (this.size == 0)
/* 1099 */       throw new NoSuchElementException(); 
/* 1100 */     return this.key[this.first];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double lastDoubleKey() {
/* 1109 */     if (this.size == 0)
/* 1110 */       throw new NoSuchElementException(); 
/* 1111 */     return this.key[this.last];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanSortedMap tailMap(double from) {
/* 1120 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanSortedMap headMap(double to) {
/* 1129 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2BooleanSortedMap subMap(double from, double to) {
/* 1138 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleComparator comparator() {
/* 1147 */     return null;
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
/* 1162 */     int prev = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1168 */     int next = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1173 */     int curr = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1179 */     int index = -1;
/*      */     protected MapIterator() {
/* 1181 */       this.next = Double2BooleanLinkedOpenHashMap.this.first;
/* 1182 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(double from) {
/* 1185 */       if (Double.doubleToLongBits(from) == 0L) {
/* 1186 */         if (Double2BooleanLinkedOpenHashMap.this.containsNullKey) {
/* 1187 */           this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[Double2BooleanLinkedOpenHashMap.this.n];
/* 1188 */           this.prev = Double2BooleanLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1191 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1193 */       if (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.last]) == Double.doubleToLongBits(from)) {
/* 1194 */         this.prev = Double2BooleanLinkedOpenHashMap.this.last;
/* 1195 */         this.index = Double2BooleanLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1199 */       int pos = (int)HashCommon.mix(Double.doubleToRawLongBits(from)) & Double2BooleanLinkedOpenHashMap.this.mask;
/*      */       
/* 1201 */       while (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[pos]) != 0L) {
/* 1202 */         if (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[pos]) == Double.doubleToLongBits(from)) {
/*      */           
/* 1204 */           this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[pos];
/* 1205 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1208 */         pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask;
/*      */       } 
/* 1210 */       throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */     }
/*      */     public boolean hasNext() {
/* 1213 */       return (this.next != -1);
/*      */     }
/*      */     public boolean hasPrevious() {
/* 1216 */       return (this.prev != -1);
/*      */     }
/*      */     private final void ensureIndexKnown() {
/* 1219 */       if (this.index >= 0)
/*      */         return; 
/* 1221 */       if (this.prev == -1) {
/* 1222 */         this.index = 0;
/*      */         return;
/*      */       } 
/* 1225 */       if (this.next == -1) {
/* 1226 */         this.index = Double2BooleanLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1229 */       int pos = Double2BooleanLinkedOpenHashMap.this.first;
/* 1230 */       this.index = 1;
/* 1231 */       while (pos != this.prev) {
/* 1232 */         pos = (int)Double2BooleanLinkedOpenHashMap.this.link[pos];
/* 1233 */         this.index++;
/*      */       } 
/*      */     }
/*      */     public int nextIndex() {
/* 1237 */       ensureIndexKnown();
/* 1238 */       return this.index;
/*      */     }
/*      */     public int previousIndex() {
/* 1241 */       ensureIndexKnown();
/* 1242 */       return this.index - 1;
/*      */     }
/*      */     public int nextEntry() {
/* 1245 */       if (!hasNext())
/* 1246 */         throw new NoSuchElementException(); 
/* 1247 */       this.curr = this.next;
/* 1248 */       this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1249 */       this.prev = this.curr;
/* 1250 */       if (this.index >= 0)
/* 1251 */         this.index++; 
/* 1252 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1255 */       if (!hasPrevious())
/* 1256 */         throw new NoSuchElementException(); 
/* 1257 */       this.curr = this.prev;
/* 1258 */       this.prev = (int)(Double2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/* 1259 */       this.next = this.curr;
/* 1260 */       if (this.index >= 0)
/* 1261 */         this.index--; 
/* 1262 */       return this.curr;
/*      */     }
/*      */     public void remove() {
/* 1265 */       ensureIndexKnown();
/* 1266 */       if (this.curr == -1)
/* 1267 */         throw new IllegalStateException(); 
/* 1268 */       if (this.curr == this.prev) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1273 */         this.index--;
/* 1274 */         this.prev = (int)(Double2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1276 */         this.next = (int)Double2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1277 */       }  Double2BooleanLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1282 */       if (this.prev == -1) {
/* 1283 */         Double2BooleanLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1285 */         Double2BooleanLinkedOpenHashMap.this.link[this.prev] = Double2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (Double2BooleanLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1286 */       }  if (this.next == -1) {
/* 1287 */         Double2BooleanLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1289 */         Double2BooleanLinkedOpenHashMap.this.link[this.next] = Double2BooleanLinkedOpenHashMap.this.link[this.next] ^ (Double2BooleanLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1290 */       }  int pos = this.curr;
/* 1291 */       this.curr = -1;
/* 1292 */       if (pos == Double2BooleanLinkedOpenHashMap.this.n) {
/* 1293 */         Double2BooleanLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1296 */         double[] key = Double2BooleanLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           double curr;
/*      */           int last;
/* 1300 */           pos = (last = pos) + 1 & Double2BooleanLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1302 */             if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/* 1303 */               key[last] = 0.0D;
/*      */               return;
/*      */             } 
/* 1306 */             int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2BooleanLinkedOpenHashMap.this.mask;
/* 1307 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1309 */             pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1311 */           key[last] = curr;
/* 1312 */           Double2BooleanLinkedOpenHashMap.this.value[last] = Double2BooleanLinkedOpenHashMap.this.value[pos];
/* 1313 */           if (this.next == pos)
/* 1314 */             this.next = last; 
/* 1315 */           if (this.prev == pos)
/* 1316 */             this.prev = last; 
/* 1317 */           Double2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
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
/*      */     public void set(Double2BooleanMap.Entry ok) {
/* 1334 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Double2BooleanMap.Entry ok) {
/* 1337 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Double2BooleanMap.Entry> { private Double2BooleanLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(double from) {
/* 1345 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1349 */       return this.entry = new Double2BooleanLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Double2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1353 */       return this.entry = new Double2BooleanLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1357 */       super.remove();
/* 1358 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1362 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Double2BooleanMap.Entry> { final Double2BooleanLinkedOpenHashMap.MapEntry entry = new Double2BooleanLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(double from) {
/* 1366 */       super(from);
/*      */     }
/*      */     
/*      */     public Double2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1370 */       this.entry.index = nextEntry();
/* 1371 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Double2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1375 */       this.entry.index = previousEntry();
/* 1376 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Double2BooleanMap.Entry> implements Double2BooleanSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Double2BooleanMap.Entry> iterator() {
/* 1384 */       return (ObjectBidirectionalIterator<Double2BooleanMap.Entry>)new Double2BooleanLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Double2BooleanMap.Entry> comparator() {
/* 1388 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Double2BooleanMap.Entry> subSet(Double2BooleanMap.Entry fromElement, Double2BooleanMap.Entry toElement) {
/* 1393 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2BooleanMap.Entry> headSet(Double2BooleanMap.Entry toElement) {
/* 1397 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Double2BooleanMap.Entry> tailSet(Double2BooleanMap.Entry fromElement) {
/* 1401 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Double2BooleanMap.Entry first() {
/* 1405 */       if (Double2BooleanLinkedOpenHashMap.this.size == 0)
/* 1406 */         throw new NoSuchElementException(); 
/* 1407 */       return new Double2BooleanLinkedOpenHashMap.MapEntry(Double2BooleanLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Double2BooleanMap.Entry last() {
/* 1411 */       if (Double2BooleanLinkedOpenHashMap.this.size == 0)
/* 1412 */         throw new NoSuchElementException(); 
/* 1413 */       return new Double2BooleanLinkedOpenHashMap.MapEntry(Double2BooleanLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1418 */       if (!(o instanceof Map.Entry))
/* 1419 */         return false; 
/* 1420 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1421 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1422 */         return false; 
/* 1423 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1424 */         return false; 
/* 1425 */       double k = ((Double)e.getKey()).doubleValue();
/* 1426 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1427 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1428 */         return (Double2BooleanLinkedOpenHashMap.this.containsNullKey && Double2BooleanLinkedOpenHashMap.this.value[Double2BooleanLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1430 */       double[] key = Double2BooleanLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1433 */       if (Double.doubleToLongBits(
/* 1434 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2BooleanLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1436 */         return false; } 
/* 1437 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1438 */         return (Double2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1441 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask]) == 0L)
/* 1442 */           return false; 
/* 1443 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/* 1444 */           return (Double2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1450 */       if (!(o instanceof Map.Entry))
/* 1451 */         return false; 
/* 1452 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1453 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/* 1454 */         return false; 
/* 1455 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1456 */         return false; 
/* 1457 */       double k = ((Double)e.getKey()).doubleValue();
/* 1458 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1459 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1460 */         if (Double2BooleanLinkedOpenHashMap.this.containsNullKey && Double2BooleanLinkedOpenHashMap.this.value[Double2BooleanLinkedOpenHashMap.this.n] == v) {
/* 1461 */           Double2BooleanLinkedOpenHashMap.this.removeNullEntry();
/* 1462 */           return true;
/*      */         } 
/* 1464 */         return false;
/*      */       } 
/*      */       
/* 1467 */       double[] key = Double2BooleanLinkedOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/* 1470 */       if (Double.doubleToLongBits(
/* 1471 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2BooleanLinkedOpenHashMap.this.mask]) == 0L)
/*      */       {
/* 1473 */         return false; } 
/* 1474 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/* 1475 */         if (Double2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1476 */           Double2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1477 */           return true;
/*      */         } 
/* 1479 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1482 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2BooleanLinkedOpenHashMap.this.mask]) == 0L)
/* 1483 */           return false; 
/* 1484 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/* 1485 */           Double2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1486 */           Double2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1487 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1494 */       return Double2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1498 */       Double2BooleanLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Double2BooleanMap.Entry> iterator(Double2BooleanMap.Entry from) {
/* 1513 */       return new Double2BooleanLinkedOpenHashMap.EntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Double2BooleanMap.Entry> fastIterator() {
/* 1524 */       return new Double2BooleanLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Double2BooleanMap.Entry> fastIterator(Double2BooleanMap.Entry from) {
/* 1539 */       return new Double2BooleanLinkedOpenHashMap.FastEntryIterator(from.getDoubleKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/* 1544 */       for (int i = Double2BooleanLinkedOpenHashMap.this.size, next = Double2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1545 */         int curr = next;
/* 1546 */         next = (int)Double2BooleanLinkedOpenHashMap.this.link[curr];
/* 1547 */         consumer.accept(new AbstractDouble2BooleanMap.BasicEntry(Double2BooleanLinkedOpenHashMap.this.key[curr], Double2BooleanLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2BooleanMap.Entry> consumer) {
/* 1553 */       AbstractDouble2BooleanMap.BasicEntry entry = new AbstractDouble2BooleanMap.BasicEntry();
/* 1554 */       for (int i = Double2BooleanLinkedOpenHashMap.this.size, next = Double2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1555 */         int curr = next;
/* 1556 */         next = (int)Double2BooleanLinkedOpenHashMap.this.link[curr];
/* 1557 */         entry.key = Double2BooleanLinkedOpenHashMap.this.key[curr];
/* 1558 */         entry.value = Double2BooleanLinkedOpenHashMap.this.value[curr];
/* 1559 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Double2BooleanSortedMap.FastSortedEntrySet double2BooleanEntrySet() {
/* 1565 */     if (this.entries == null)
/* 1566 */       this.entries = new MapEntrySet(); 
/* 1567 */     return this.entries;
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
/* 1580 */       super(k);
/*      */     }
/*      */     
/*      */     public double previousDouble() {
/* 1584 */       return Double2BooleanLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public double nextDouble() {
/* 1591 */       return Double2BooleanLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSortedSet { private KeySet() {}
/*      */     
/*      */     public DoubleListIterator iterator(double from) {
/* 1597 */       return new Double2BooleanLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public DoubleListIterator iterator() {
/* 1601 */       return new Double2BooleanLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1606 */       if (Double2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1607 */         consumer.accept(Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.n]); 
/* 1608 */       for (int pos = Double2BooleanLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1609 */         double k = Double2BooleanLinkedOpenHashMap.this.key[pos];
/* 1610 */         if (Double.doubleToLongBits(k) != 0L)
/* 1611 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1616 */       return Double2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1620 */       return Double2BooleanLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1624 */       int oldSize = Double2BooleanLinkedOpenHashMap.this.size;
/* 1625 */       Double2BooleanLinkedOpenHashMap.this.remove(k);
/* 1626 */       return (Double2BooleanLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1630 */       Double2BooleanLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public double firstDouble() {
/* 1634 */       if (Double2BooleanLinkedOpenHashMap.this.size == 0)
/* 1635 */         throw new NoSuchElementException(); 
/* 1636 */       return Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public double lastDouble() {
/* 1640 */       if (Double2BooleanLinkedOpenHashMap.this.size == 0)
/* 1641 */         throw new NoSuchElementException(); 
/* 1642 */       return Double2BooleanLinkedOpenHashMap.this.key[Double2BooleanLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public DoubleComparator comparator() {
/* 1646 */       return null;
/*      */     }
/*      */     
/*      */     public DoubleSortedSet tailSet(double from) {
/* 1650 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet headSet(double to) {
/* 1654 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public DoubleSortedSet subSet(double from, double to) {
/* 1658 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSortedSet keySet() {
/* 1663 */     if (this.keys == null)
/* 1664 */       this.keys = new KeySet(); 
/* 1665 */     return this.keys;
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
/*      */     implements BooleanListIterator
/*      */   {
/*      */     public boolean previousBoolean() {
/* 1679 */       return Double2BooleanLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1686 */       return Double2BooleanLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1691 */     if (this.values == null)
/* 1692 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1695 */             return (BooleanIterator)new Double2BooleanLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1699 */             return Double2BooleanLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1703 */             return Double2BooleanLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1707 */             Double2BooleanLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1712 */             if (Double2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1713 */               consumer.accept(Double2BooleanLinkedOpenHashMap.this.value[Double2BooleanLinkedOpenHashMap.this.n]); 
/* 1714 */             for (int pos = Double2BooleanLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1715 */               if (Double.doubleToLongBits(Double2BooleanLinkedOpenHashMap.this.key[pos]) != 0L)
/* 1716 */                 consumer.accept(Double2BooleanLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1719 */     return this.values;
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
/* 1736 */     return trim(this.size);
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
/* 1760 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1761 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1762 */       return true; 
/*      */     try {
/* 1764 */       rehash(l);
/* 1765 */     } catch (OutOfMemoryError cantDoIt) {
/* 1766 */       return false;
/*      */     } 
/* 1768 */     return true;
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
/* 1784 */     double[] key = this.key;
/* 1785 */     boolean[] value = this.value;
/* 1786 */     int mask = newN - 1;
/* 1787 */     double[] newKey = new double[newN + 1];
/* 1788 */     boolean[] newValue = new boolean[newN + 1];
/* 1789 */     int i = this.first, prev = -1, newPrev = -1;
/* 1790 */     long[] link = this.link;
/* 1791 */     long[] newLink = new long[newN + 1];
/* 1792 */     this.first = -1;
/* 1793 */     for (int j = this.size; j-- != 0; ) {
/* 1794 */       int pos; if (Double.doubleToLongBits(key[i]) == 0L) {
/* 1795 */         pos = newN;
/*      */       } else {
/* 1797 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask;
/* 1798 */         while (Double.doubleToLongBits(newKey[pos]) != 0L)
/* 1799 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1801 */       newKey[pos] = key[i];
/* 1802 */       newValue[pos] = value[i];
/* 1803 */       if (prev != -1) {
/* 1804 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1805 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1806 */         newPrev = pos;
/*      */       } else {
/* 1808 */         newPrev = this.first = pos;
/*      */         
/* 1810 */         newLink[pos] = -1L;
/*      */       } 
/* 1812 */       int t = i;
/* 1813 */       i = (int)link[i];
/* 1814 */       prev = t;
/*      */     } 
/* 1816 */     this.link = newLink;
/* 1817 */     this.last = newPrev;
/* 1818 */     if (newPrev != -1)
/*      */     {
/* 1820 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1821 */     this.n = newN;
/* 1822 */     this.mask = mask;
/* 1823 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1824 */     this.key = newKey;
/* 1825 */     this.value = newValue;
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
/*      */   public Double2BooleanLinkedOpenHashMap clone() {
/*      */     Double2BooleanLinkedOpenHashMap c;
/*      */     try {
/* 1842 */       c = (Double2BooleanLinkedOpenHashMap)super.clone();
/* 1843 */     } catch (CloneNotSupportedException cantHappen) {
/* 1844 */       throw new InternalError();
/*      */     } 
/* 1846 */     c.keys = null;
/* 1847 */     c.values = null;
/* 1848 */     c.entries = null;
/* 1849 */     c.containsNullKey = this.containsNullKey;
/* 1850 */     c.key = (double[])this.key.clone();
/* 1851 */     c.value = (boolean[])this.value.clone();
/* 1852 */     c.link = (long[])this.link.clone();
/* 1853 */     return c;
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
/* 1866 */     int h = 0;
/* 1867 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1868 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1869 */         i++; 
/* 1870 */       t = HashCommon.double2int(this.key[i]);
/* 1871 */       t ^= this.value[i] ? 1231 : 1237;
/* 1872 */       h += t;
/* 1873 */       i++;
/*      */     } 
/*      */     
/* 1876 */     if (this.containsNullKey)
/* 1877 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1878 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1881 */     double[] key = this.key;
/* 1882 */     boolean[] value = this.value;
/* 1883 */     MapIterator i = new MapIterator();
/* 1884 */     s.defaultWriteObject();
/* 1885 */     for (int j = this.size; j-- != 0; ) {
/* 1886 */       int e = i.nextEntry();
/* 1887 */       s.writeDouble(key[e]);
/* 1888 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1893 */     s.defaultReadObject();
/* 1894 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1895 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1896 */     this.mask = this.n - 1;
/* 1897 */     double[] key = this.key = new double[this.n + 1];
/* 1898 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1899 */     long[] link = this.link = new long[this.n + 1];
/* 1900 */     int prev = -1;
/* 1901 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1904 */     for (int i = this.size; i-- != 0; ) {
/* 1905 */       int pos; double k = s.readDouble();
/* 1906 */       boolean v = s.readBoolean();
/* 1907 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1908 */         pos = this.n;
/* 1909 */         this.containsNullKey = true;
/*      */       } else {
/* 1911 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1912 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1913 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1915 */       key[pos] = k;
/* 1916 */       value[pos] = v;
/* 1917 */       if (this.first != -1) {
/* 1918 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1919 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1920 */         prev = pos; continue;
/*      */       } 
/* 1922 */       prev = this.first = pos;
/*      */       
/* 1924 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1927 */     this.last = prev;
/* 1928 */     if (prev != -1)
/*      */     {
/* 1930 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2BooleanLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */