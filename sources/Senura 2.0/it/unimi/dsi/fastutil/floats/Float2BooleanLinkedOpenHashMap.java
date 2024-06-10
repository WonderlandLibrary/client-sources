/*      */ package it.unimi.dsi.fastutil.floats;
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
/*      */ public class Float2BooleanLinkedOpenHashMap
/*      */   extends AbstractFloat2BooleanSortedMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient float[] key;
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
/*      */   protected transient Float2BooleanSortedMap.FastSortedEntrySet entries;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient FloatSortedSet keys;
/*      */ 
/*      */ 
/*      */   
/*      */   protected transient BooleanCollection values;
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanLinkedOpenHashMap(int expected, float f) {
/*  154 */     if (f <= 0.0F || f > 1.0F)
/*  155 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  156 */     if (expected < 0)
/*  157 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  158 */     this.f = f;
/*  159 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  160 */     this.mask = this.n - 1;
/*  161 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  162 */     this.key = new float[this.n + 1];
/*  163 */     this.value = new boolean[this.n + 1];
/*  164 */     this.link = new long[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanLinkedOpenHashMap(int expected) {
/*  173 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanLinkedOpenHashMap() {
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
/*      */   public Float2BooleanLinkedOpenHashMap(Map<? extends Float, ? extends Boolean> m, float f) {
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
/*      */   public Float2BooleanLinkedOpenHashMap(Map<? extends Float, ? extends Boolean> m) {
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
/*      */   public Float2BooleanLinkedOpenHashMap(Float2BooleanMap m, float f) {
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
/*      */   public Float2BooleanLinkedOpenHashMap(Float2BooleanMap m) {
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
/*      */   public Float2BooleanLinkedOpenHashMap(float[] k, boolean[] v, float f) {
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
/*      */   public Float2BooleanLinkedOpenHashMap(float[] k, boolean[] v) {
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
/*      */   public void putAll(Map<? extends Float, ? extends Boolean> m) {
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
/*      */   private void insert(int pos, float k, boolean v) {
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
/*      */   public boolean put(float k, boolean v) {
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
/*  365 */     float[] key = this.key; while (true) {
/*      */       float curr; int last;
/*  367 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  369 */         if (Float.floatToIntBits(curr = key[pos]) == 0) {
/*  370 */           key[last] = 0.0F;
/*      */           return;
/*      */         } 
/*  373 */         int slot = HashCommon.mix(HashCommon.float2int(curr)) & this.mask;
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
/*      */   public boolean remove(float k) {
/*  386 */     if (Float.floatToIntBits(k) == 0) {
/*  387 */       if (this.containsNullKey)
/*  388 */         return removeNullEntry(); 
/*  389 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  392 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  395 */     if (Float.floatToIntBits(
/*  396 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  398 */       return this.defRetValue; } 
/*  399 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  400 */       return removeEntry(pos); 
/*      */     while (true) {
/*  402 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  403 */         return this.defRetValue; 
/*  404 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
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
/*      */   public boolean getAndMoveToFirst(float k) {
/*  514 */     if (Float.floatToIntBits(k) == 0) {
/*  515 */       if (this.containsNullKey) {
/*  516 */         moveIndexToFirst(this.n);
/*  517 */         return this.value[this.n];
/*      */       } 
/*  519 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  522 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  525 */     if (Float.floatToIntBits(
/*  526 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  528 */       return this.defRetValue; } 
/*  529 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  530 */       moveIndexToFirst(pos);
/*  531 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  535 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  536 */         return this.defRetValue; 
/*  537 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public boolean getAndMoveToLast(float k) {
/*  553 */     if (Float.floatToIntBits(k) == 0) {
/*  554 */       if (this.containsNullKey) {
/*  555 */         moveIndexToLast(this.n);
/*  556 */         return this.value[this.n];
/*      */       } 
/*  558 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  561 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  564 */     if (Float.floatToIntBits(
/*  565 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  567 */       return this.defRetValue; } 
/*  568 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  569 */       moveIndexToLast(pos);
/*  570 */       return this.value[pos];
/*      */     } 
/*      */     
/*      */     while (true) {
/*  574 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  575 */         return this.defRetValue; 
/*  576 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
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
/*      */   public boolean putAndMoveToFirst(float k, boolean v) {
/*      */     int pos;
/*  595 */     if (Float.floatToIntBits(k) == 0) {
/*  596 */       if (this.containsNullKey) {
/*  597 */         moveIndexToFirst(this.n);
/*  598 */         return setValue(this.n, v);
/*      */       } 
/*  600 */       this.containsNullKey = true;
/*  601 */       pos = this.n;
/*      */     } else {
/*      */       
/*  604 */       float[] key = this.key;
/*      */       float curr;
/*  606 */       if (Float.floatToIntBits(
/*  607 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  609 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  610 */           moveIndexToFirst(pos);
/*  611 */           return setValue(pos, v);
/*      */         } 
/*  613 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  614 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public boolean putAndMoveToLast(float k, boolean v) {
/*      */     int pos;
/*  650 */     if (Float.floatToIntBits(k) == 0) {
/*  651 */       if (this.containsNullKey) {
/*  652 */         moveIndexToLast(this.n);
/*  653 */         return setValue(this.n, v);
/*      */       } 
/*  655 */       this.containsNullKey = true;
/*  656 */       pos = this.n;
/*      */     } else {
/*      */       
/*  659 */       float[] key = this.key;
/*      */       float curr;
/*  661 */       if (Float.floatToIntBits(
/*  662 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) != 0) {
/*      */         
/*  664 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/*  665 */           moveIndexToLast(pos);
/*  666 */           return setValue(pos, v);
/*      */         } 
/*  668 */         while (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) != 0) {
/*  669 */           if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
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
/*      */   public boolean get(float k) {
/*  695 */     if (Float.floatToIntBits(k) == 0) {
/*  696 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  698 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  701 */     if (Float.floatToIntBits(
/*  702 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  704 */       return this.defRetValue; } 
/*  705 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  706 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  709 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  710 */         return this.defRetValue; 
/*  711 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  712 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(float k) {
/*  718 */     if (Float.floatToIntBits(k) == 0) {
/*  719 */       return this.containsNullKey;
/*      */     }
/*  721 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  724 */     if (Float.floatToIntBits(
/*  725 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  727 */       return false; } 
/*  728 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  729 */       return true;
/*      */     }
/*      */     while (true) {
/*  732 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  733 */         return false; 
/*  734 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr))
/*  735 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(boolean v) {
/*  740 */     boolean[] value = this.value;
/*  741 */     float[] key = this.key;
/*  742 */     if (this.containsNullKey && value[this.n] == v)
/*  743 */       return true; 
/*  744 */     for (int i = this.n; i-- != 0;) {
/*  745 */       if (Float.floatToIntBits(key[i]) != 0 && value[i] == v)
/*  746 */         return true; 
/*  747 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOrDefault(float k, boolean defaultValue) {
/*  753 */     if (Float.floatToIntBits(k) == 0) {
/*  754 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  756 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  759 */     if (Float.floatToIntBits(
/*  760 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  762 */       return defaultValue; } 
/*  763 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  764 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  767 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  768 */         return defaultValue; 
/*  769 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/*  770 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean putIfAbsent(float k, boolean v) {
/*  776 */     int pos = find(k);
/*  777 */     if (pos >= 0)
/*  778 */       return this.value[pos]; 
/*  779 */     insert(-pos - 1, k, v);
/*  780 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(float k, boolean v) {
/*  786 */     if (Float.floatToIntBits(k) == 0) {
/*  787 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  788 */         removeNullEntry();
/*  789 */         return true;
/*      */       } 
/*  791 */       return false;
/*      */     } 
/*      */     
/*  794 */     float[] key = this.key;
/*      */     float curr;
/*      */     int pos;
/*  797 */     if (Float.floatToIntBits(
/*  798 */         curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask]) == 0)
/*      */     {
/*  800 */       return false; } 
/*  801 */     if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  802 */       removeEntry(pos);
/*  803 */       return true;
/*      */     } 
/*      */     while (true) {
/*  806 */       if (Float.floatToIntBits(curr = key[pos = pos + 1 & this.mask]) == 0)
/*  807 */         return false; 
/*  808 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr) && v == this.value[pos]) {
/*  809 */         removeEntry(pos);
/*  810 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, boolean oldValue, boolean v) {
/*  817 */     int pos = find(k);
/*  818 */     if (pos < 0 || oldValue != this.value[pos])
/*  819 */       return false; 
/*  820 */     this.value[pos] = v;
/*  821 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(float k, boolean v) {
/*  826 */     int pos = find(k);
/*  827 */     if (pos < 0)
/*  828 */       return this.defRetValue; 
/*  829 */     boolean oldValue = this.value[pos];
/*  830 */     this.value[pos] = v;
/*  831 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean computeIfAbsent(float k, DoublePredicate mappingFunction) {
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
/*      */   public boolean computeIfAbsentNullable(float k, DoubleFunction<? extends Boolean> mappingFunction) {
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
/*      */   public boolean computeIfPresent(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  863 */     Objects.requireNonNull(remappingFunction);
/*  864 */     int pos = find(k);
/*  865 */     if (pos < 0)
/*  866 */       return this.defRetValue; 
/*  867 */     Boolean newValue = remappingFunction.apply(Float.valueOf(k), Boolean.valueOf(this.value[pos]));
/*  868 */     if (newValue == null) {
/*  869 */       if (Float.floatToIntBits(k) == 0) {
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
/*      */   public boolean compute(float k, BiFunction<? super Float, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  881 */     Objects.requireNonNull(remappingFunction);
/*  882 */     int pos = find(k);
/*  883 */     Boolean newValue = remappingFunction.apply(Float.valueOf(k), 
/*  884 */         (pos >= 0) ? Boolean.valueOf(this.value[pos]) : null);
/*  885 */     if (newValue == null) {
/*  886 */       if (pos >= 0)
/*  887 */         if (Float.floatToIntBits(k) == 0) {
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
/*      */   public boolean merge(float k, boolean v, BiFunction<? super Boolean, ? super Boolean, ? extends Boolean> remappingFunction) {
/*  905 */     Objects.requireNonNull(remappingFunction);
/*  906 */     int pos = find(k);
/*  907 */     if (pos < 0) {
/*  908 */       insert(-pos - 1, k, v);
/*  909 */       return v;
/*      */     } 
/*  911 */     Boolean newValue = remappingFunction.apply(Boolean.valueOf(this.value[pos]), Boolean.valueOf(v));
/*  912 */     if (newValue == null) {
/*  913 */       if (Float.floatToIntBits(k) == 0) {
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
/*  934 */     Arrays.fill(this.key, 0.0F);
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
/*      */     implements Float2BooleanMap.Entry, Map.Entry<Float, Boolean>
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
/*      */     public float getFloatKey() {
/*  961 */       return Float2BooleanLinkedOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public boolean getBooleanValue() {
/*  965 */       return Float2BooleanLinkedOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public boolean setValue(boolean v) {
/*  969 */       boolean oldValue = Float2BooleanLinkedOpenHashMap.this.value[this.index];
/*  970 */       Float2BooleanLinkedOpenHashMap.this.value[this.index] = v;
/*  971 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Float getKey() {
/*  981 */       return Float.valueOf(Float2BooleanLinkedOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Boolean getValue() {
/*  991 */       return Boolean.valueOf(Float2BooleanLinkedOpenHashMap.this.value[this.index]);
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
/* 1008 */       Map.Entry<Float, Boolean> e = (Map.Entry<Float, Boolean>)o;
/* 1009 */       return (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[this.index]) == Float.floatToIntBits(((Float)e.getKey()).floatValue()) && Float2BooleanLinkedOpenHashMap.this.value[this.index] == ((Boolean)e
/* 1010 */         .getValue()).booleanValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1014 */       return HashCommon.float2int(Float2BooleanLinkedOpenHashMap.this.key[this.index]) ^ (Float2BooleanLinkedOpenHashMap.this.value[this.index] ? 1231 : 1237);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 1018 */       return Float2BooleanLinkedOpenHashMap.this.key[this.index] + "=>" + Float2BooleanLinkedOpenHashMap.this.value[this.index];
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
/*      */   public float firstFloatKey() {
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
/*      */   public float lastFloatKey() {
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
/*      */   public Float2BooleanSortedMap tailMap(float from) {
/* 1120 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanSortedMap headMap(float to) {
/* 1129 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Float2BooleanSortedMap subMap(float from, float to) {
/* 1138 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FloatComparator comparator() {
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
/* 1181 */       this.next = Float2BooleanLinkedOpenHashMap.this.first;
/* 1182 */       this.index = 0;
/*      */     }
/*      */     private MapIterator(float from) {
/* 1185 */       if (Float.floatToIntBits(from) == 0) {
/* 1186 */         if (Float2BooleanLinkedOpenHashMap.this.containsNullKey) {
/* 1187 */           this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[Float2BooleanLinkedOpenHashMap.this.n];
/* 1188 */           this.prev = Float2BooleanLinkedOpenHashMap.this.n;
/*      */           return;
/*      */         } 
/* 1191 */         throw new NoSuchElementException("The key " + from + " does not belong to this map.");
/*      */       } 
/* 1193 */       if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.last]) == Float.floatToIntBits(from)) {
/* 1194 */         this.prev = Float2BooleanLinkedOpenHashMap.this.last;
/* 1195 */         this.index = Float2BooleanLinkedOpenHashMap.this.size;
/*      */         
/*      */         return;
/*      */       } 
/* 1199 */       int pos = HashCommon.mix(HashCommon.float2int(from)) & Float2BooleanLinkedOpenHashMap.this.mask;
/*      */       
/* 1201 */       while (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) != 0) {
/* 1202 */         if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) == Float.floatToIntBits(from)) {
/*      */           
/* 1204 */           this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[pos];
/* 1205 */           this.prev = pos;
/*      */           return;
/*      */         } 
/* 1208 */         pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask;
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
/* 1226 */         this.index = Float2BooleanLinkedOpenHashMap.this.size;
/*      */         return;
/*      */       } 
/* 1229 */       int pos = Float2BooleanLinkedOpenHashMap.this.first;
/* 1230 */       this.index = 1;
/* 1231 */       while (pos != this.prev) {
/* 1232 */         pos = (int)Float2BooleanLinkedOpenHashMap.this.link[pos];
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
/* 1248 */       this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1249 */       this.prev = this.curr;
/* 1250 */       if (this.index >= 0)
/* 1251 */         this.index++; 
/* 1252 */       return this.curr;
/*      */     }
/*      */     public int previousEntry() {
/* 1255 */       if (!hasPrevious())
/* 1256 */         throw new NoSuchElementException(); 
/* 1257 */       this.curr = this.prev;
/* 1258 */       this.prev = (int)(Float2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
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
/* 1274 */         this.prev = (int)(Float2BooleanLinkedOpenHashMap.this.link[this.curr] >>> 32L);
/*      */       } else {
/* 1276 */         this.next = (int)Float2BooleanLinkedOpenHashMap.this.link[this.curr];
/* 1277 */       }  Float2BooleanLinkedOpenHashMap.this.size--;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1282 */       if (this.prev == -1) {
/* 1283 */         Float2BooleanLinkedOpenHashMap.this.first = this.next;
/*      */       } else {
/* 1285 */         Float2BooleanLinkedOpenHashMap.this.link[this.prev] = Float2BooleanLinkedOpenHashMap.this.link[this.prev] ^ (Float2BooleanLinkedOpenHashMap.this.link[this.prev] ^ this.next & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1286 */       }  if (this.next == -1) {
/* 1287 */         Float2BooleanLinkedOpenHashMap.this.last = this.prev;
/*      */       } else {
/* 1289 */         Float2BooleanLinkedOpenHashMap.this.link[this.next] = Float2BooleanLinkedOpenHashMap.this.link[this.next] ^ (Float2BooleanLinkedOpenHashMap.this.link[this.next] ^ (this.prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1290 */       }  int pos = this.curr;
/* 1291 */       this.curr = -1;
/* 1292 */       if (pos == Float2BooleanLinkedOpenHashMap.this.n) {
/* 1293 */         Float2BooleanLinkedOpenHashMap.this.containsNullKey = false;
/*      */       } else {
/*      */         
/* 1296 */         float[] key = Float2BooleanLinkedOpenHashMap.this.key;
/*      */         while (true) {
/*      */           float curr;
/*      */           int last;
/* 1300 */           pos = (last = pos) + 1 & Float2BooleanLinkedOpenHashMap.this.mask;
/*      */           while (true) {
/* 1302 */             if (Float.floatToIntBits(curr = key[pos]) == 0) {
/* 1303 */               key[last] = 0.0F;
/*      */               return;
/*      */             } 
/* 1306 */             int slot = HashCommon.mix(HashCommon.float2int(curr)) & Float2BooleanLinkedOpenHashMap.this.mask;
/*      */             
/* 1308 */             if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */               break; 
/* 1310 */             pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask;
/*      */           } 
/* 1312 */           key[last] = curr;
/* 1313 */           Float2BooleanLinkedOpenHashMap.this.value[last] = Float2BooleanLinkedOpenHashMap.this.value[pos];
/* 1314 */           if (this.next == pos)
/* 1315 */             this.next = last; 
/* 1316 */           if (this.prev == pos)
/* 1317 */             this.prev = last; 
/* 1318 */           Float2BooleanLinkedOpenHashMap.this.fixPointers(pos, last);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     public int skip(int n) {
/* 1323 */       int i = n;
/* 1324 */       while (i-- != 0 && hasNext())
/* 1325 */         nextEntry(); 
/* 1326 */       return n - i - 1;
/*      */     }
/*      */     public int back(int n) {
/* 1329 */       int i = n;
/* 1330 */       while (i-- != 0 && hasPrevious())
/* 1331 */         previousEntry(); 
/* 1332 */       return n - i - 1;
/*      */     }
/*      */     public void set(Float2BooleanMap.Entry ok) {
/* 1335 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     public void add(Float2BooleanMap.Entry ok) {
/* 1338 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectListIterator<Float2BooleanMap.Entry> { private Float2BooleanLinkedOpenHashMap.MapEntry entry;
/*      */     
/*      */     public EntryIterator() {}
/*      */     
/*      */     public EntryIterator(float from) {
/* 1346 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1350 */       return this.entry = new Float2BooleanLinkedOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     
/*      */     public Float2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1354 */       return this.entry = new Float2BooleanLinkedOpenHashMap.MapEntry(previousEntry());
/*      */     }
/*      */     
/*      */     public void remove() {
/* 1358 */       super.remove();
/* 1359 */       this.entry.index = -1;
/*      */     } }
/*      */ 
/*      */   
/* 1363 */   private class FastEntryIterator extends MapIterator implements ObjectListIterator<Float2BooleanMap.Entry> { final Float2BooleanLinkedOpenHashMap.MapEntry entry = new Float2BooleanLinkedOpenHashMap.MapEntry();
/*      */ 
/*      */     
/*      */     public FastEntryIterator(float from) {
/* 1367 */       super(from);
/*      */     }
/*      */     
/*      */     public Float2BooleanLinkedOpenHashMap.MapEntry next() {
/* 1371 */       this.entry.index = nextEntry();
/* 1372 */       return this.entry;
/*      */     }
/*      */     
/*      */     public Float2BooleanLinkedOpenHashMap.MapEntry previous() {
/* 1376 */       this.entry.index = previousEntry();
/* 1377 */       return this.entry;
/*      */     }
/*      */     
/*      */     public FastEntryIterator() {} }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSortedSet<Float2BooleanMap.Entry> implements Float2BooleanSortedMap.FastSortedEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectBidirectionalIterator<Float2BooleanMap.Entry> iterator() {
/* 1385 */       return (ObjectBidirectionalIterator<Float2BooleanMap.Entry>)new Float2BooleanLinkedOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public Comparator<? super Float2BooleanMap.Entry> comparator() {
/* 1389 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public ObjectSortedSet<Float2BooleanMap.Entry> subSet(Float2BooleanMap.Entry fromElement, Float2BooleanMap.Entry toElement) {
/* 1394 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2BooleanMap.Entry> headSet(Float2BooleanMap.Entry toElement) {
/* 1398 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public ObjectSortedSet<Float2BooleanMap.Entry> tailSet(Float2BooleanMap.Entry fromElement) {
/* 1402 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public Float2BooleanMap.Entry first() {
/* 1406 */       if (Float2BooleanLinkedOpenHashMap.this.size == 0)
/* 1407 */         throw new NoSuchElementException(); 
/* 1408 */       return new Float2BooleanLinkedOpenHashMap.MapEntry(Float2BooleanLinkedOpenHashMap.this.first);
/*      */     }
/*      */     
/*      */     public Float2BooleanMap.Entry last() {
/* 1412 */       if (Float2BooleanLinkedOpenHashMap.this.size == 0)
/* 1413 */         throw new NoSuchElementException(); 
/* 1414 */       return new Float2BooleanLinkedOpenHashMap.MapEntry(Float2BooleanLinkedOpenHashMap.this.last);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/* 1419 */       if (!(o instanceof Map.Entry))
/* 1420 */         return false; 
/* 1421 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1422 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1423 */         return false; 
/* 1424 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1425 */         return false; 
/* 1426 */       float k = ((Float)e.getKey()).floatValue();
/* 1427 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1428 */       if (Float.floatToIntBits(k) == 0) {
/* 1429 */         return (Float2BooleanLinkedOpenHashMap.this.containsNullKey && Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n] == v);
/*      */       }
/* 1431 */       float[] key = Float2BooleanLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1434 */       if (Float.floatToIntBits(
/* 1435 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1437 */         return false; } 
/* 1438 */       if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1439 */         return (Float2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/* 1442 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask]) == 0)
/* 1443 */           return false; 
/* 1444 */         if (Float.floatToIntBits(k) == Float.floatToIntBits(curr)) {
/* 1445 */           return (Float2BooleanLinkedOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/* 1451 */       if (!(o instanceof Map.Entry))
/* 1452 */         return false; 
/* 1453 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/* 1454 */       if (e.getKey() == null || !(e.getKey() instanceof Float))
/* 1455 */         return false; 
/* 1456 */       if (e.getValue() == null || !(e.getValue() instanceof Boolean))
/* 1457 */         return false; 
/* 1458 */       float k = ((Float)e.getKey()).floatValue();
/* 1459 */       boolean v = ((Boolean)e.getValue()).booleanValue();
/* 1460 */       if (Float.floatToIntBits(k) == 0) {
/* 1461 */         if (Float2BooleanLinkedOpenHashMap.this.containsNullKey && Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n] == v) {
/* 1462 */           Float2BooleanLinkedOpenHashMap.this.removeNullEntry();
/* 1463 */           return true;
/*      */         } 
/* 1465 */         return false;
/*      */       } 
/*      */       
/* 1468 */       float[] key = Float2BooleanLinkedOpenHashMap.this.key;
/*      */       float curr;
/*      */       int pos;
/* 1471 */       if (Float.floatToIntBits(
/* 1472 */           curr = key[pos = HashCommon.mix(HashCommon.float2int(k)) & Float2BooleanLinkedOpenHashMap.this.mask]) == 0)
/*      */       {
/* 1474 */         return false; } 
/* 1475 */       if (Float.floatToIntBits(curr) == Float.floatToIntBits(k)) {
/* 1476 */         if (Float2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1477 */           Float2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1478 */           return true;
/*      */         } 
/* 1480 */         return false;
/*      */       } 
/*      */       while (true) {
/* 1483 */         if (Float.floatToIntBits(curr = key[pos = pos + 1 & Float2BooleanLinkedOpenHashMap.this.mask]) == 0)
/* 1484 */           return false; 
/* 1485 */         if (Float.floatToIntBits(curr) == Float.floatToIntBits(k) && 
/* 1486 */           Float2BooleanLinkedOpenHashMap.this.value[pos] == v) {
/* 1487 */           Float2BooleanLinkedOpenHashMap.this.removeEntry(pos);
/* 1488 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/* 1495 */       return Float2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1499 */       Float2BooleanLinkedOpenHashMap.this.clear();
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
/*      */     public ObjectListIterator<Float2BooleanMap.Entry> iterator(Float2BooleanMap.Entry from) {
/* 1514 */       return new Float2BooleanLinkedOpenHashMap.EntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectListIterator<Float2BooleanMap.Entry> fastIterator() {
/* 1525 */       return new Float2BooleanLinkedOpenHashMap.FastEntryIterator();
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
/*      */     public ObjectListIterator<Float2BooleanMap.Entry> fastIterator(Float2BooleanMap.Entry from) {
/* 1540 */       return new Float2BooleanLinkedOpenHashMap.FastEntryIterator(from.getFloatKey());
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Float2BooleanMap.Entry> consumer) {
/* 1545 */       for (int i = Float2BooleanLinkedOpenHashMap.this.size, next = Float2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1546 */         int curr = next;
/* 1547 */         next = (int)Float2BooleanLinkedOpenHashMap.this.link[curr];
/* 1548 */         consumer.accept(new AbstractFloat2BooleanMap.BasicEntry(Float2BooleanLinkedOpenHashMap.this.key[curr], Float2BooleanLinkedOpenHashMap.this.value[curr]));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void fastForEach(Consumer<? super Float2BooleanMap.Entry> consumer) {
/* 1554 */       AbstractFloat2BooleanMap.BasicEntry entry = new AbstractFloat2BooleanMap.BasicEntry();
/* 1555 */       for (int i = Float2BooleanLinkedOpenHashMap.this.size, next = Float2BooleanLinkedOpenHashMap.this.first; i-- != 0; ) {
/* 1556 */         int curr = next;
/* 1557 */         next = (int)Float2BooleanLinkedOpenHashMap.this.link[curr];
/* 1558 */         entry.key = Float2BooleanLinkedOpenHashMap.this.key[curr];
/* 1559 */         entry.value = Float2BooleanLinkedOpenHashMap.this.value[curr];
/* 1560 */         consumer.accept(entry);
/*      */       } 
/*      */     } }
/*      */ 
/*      */   
/*      */   public Float2BooleanSortedMap.FastSortedEntrySet float2BooleanEntrySet() {
/* 1566 */     if (this.entries == null)
/* 1567 */       this.entries = new MapEntrySet(); 
/* 1568 */     return this.entries;
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
/* 1581 */       super(k);
/*      */     }
/*      */     
/*      */     public float previousFloat() {
/* 1585 */       return Float2BooleanLinkedOpenHashMap.this.key[previousEntry()];
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyIterator() {}
/*      */     
/*      */     public float nextFloat() {
/* 1592 */       return Float2BooleanLinkedOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractFloatSortedSet { private KeySet() {}
/*      */     
/*      */     public FloatListIterator iterator(float from) {
/* 1598 */       return new Float2BooleanLinkedOpenHashMap.KeyIterator(from);
/*      */     }
/*      */     
/*      */     public FloatListIterator iterator() {
/* 1602 */       return new Float2BooleanLinkedOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1607 */       if (Float2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1608 */         consumer.accept(Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.n]); 
/* 1609 */       for (int pos = Float2BooleanLinkedOpenHashMap.this.n; pos-- != 0; ) {
/* 1610 */         float k = Float2BooleanLinkedOpenHashMap.this.key[pos];
/* 1611 */         if (Float.floatToIntBits(k) != 0)
/* 1612 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1617 */       return Float2BooleanLinkedOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(float k) {
/* 1621 */       return Float2BooleanLinkedOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(float k) {
/* 1625 */       int oldSize = Float2BooleanLinkedOpenHashMap.this.size;
/* 1626 */       Float2BooleanLinkedOpenHashMap.this.remove(k);
/* 1627 */       return (Float2BooleanLinkedOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1631 */       Float2BooleanLinkedOpenHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public float firstFloat() {
/* 1635 */       if (Float2BooleanLinkedOpenHashMap.this.size == 0)
/* 1636 */         throw new NoSuchElementException(); 
/* 1637 */       return Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.first];
/*      */     }
/*      */     
/*      */     public float lastFloat() {
/* 1641 */       if (Float2BooleanLinkedOpenHashMap.this.size == 0)
/* 1642 */         throw new NoSuchElementException(); 
/* 1643 */       return Float2BooleanLinkedOpenHashMap.this.key[Float2BooleanLinkedOpenHashMap.this.last];
/*      */     }
/*      */     
/*      */     public FloatComparator comparator() {
/* 1647 */       return null;
/*      */     }
/*      */     
/*      */     public FloatSortedSet tailSet(float from) {
/* 1651 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet headSet(float to) {
/* 1655 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public FloatSortedSet subSet(float from, float to) {
/* 1659 */       throw new UnsupportedOperationException();
/*      */     } }
/*      */ 
/*      */   
/*      */   public FloatSortedSet keySet() {
/* 1664 */     if (this.keys == null)
/* 1665 */       this.keys = new KeySet(); 
/* 1666 */     return this.keys;
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
/* 1680 */       return Float2BooleanLinkedOpenHashMap.this.value[previousEntry()];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean nextBoolean() {
/* 1687 */       return Float2BooleanLinkedOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public BooleanCollection values() {
/* 1692 */     if (this.values == null)
/* 1693 */       this.values = (BooleanCollection)new AbstractBooleanCollection()
/*      */         {
/*      */           public BooleanIterator iterator() {
/* 1696 */             return (BooleanIterator)new Float2BooleanLinkedOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1700 */             return Float2BooleanLinkedOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(boolean v) {
/* 1704 */             return Float2BooleanLinkedOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1708 */             Float2BooleanLinkedOpenHashMap.this.clear();
/*      */           }
/*      */ 
/*      */           
/*      */           public void forEach(BooleanConsumer consumer) {
/* 1713 */             if (Float2BooleanLinkedOpenHashMap.this.containsNullKey)
/* 1714 */               consumer.accept(Float2BooleanLinkedOpenHashMap.this.value[Float2BooleanLinkedOpenHashMap.this.n]); 
/* 1715 */             for (int pos = Float2BooleanLinkedOpenHashMap.this.n; pos-- != 0;) {
/* 1716 */               if (Float.floatToIntBits(Float2BooleanLinkedOpenHashMap.this.key[pos]) != 0)
/* 1717 */                 consumer.accept(Float2BooleanLinkedOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1720 */     return this.values;
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
/* 1737 */     return trim(this.size);
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
/* 1761 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1762 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1763 */       return true; 
/*      */     try {
/* 1765 */       rehash(l);
/* 1766 */     } catch (OutOfMemoryError cantDoIt) {
/* 1767 */       return false;
/*      */     } 
/* 1769 */     return true;
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
/* 1785 */     float[] key = this.key;
/* 1786 */     boolean[] value = this.value;
/* 1787 */     int mask = newN - 1;
/* 1788 */     float[] newKey = new float[newN + 1];
/* 1789 */     boolean[] newValue = new boolean[newN + 1];
/* 1790 */     int i = this.first, prev = -1, newPrev = -1;
/* 1791 */     long[] link = this.link;
/* 1792 */     long[] newLink = new long[newN + 1];
/* 1793 */     this.first = -1;
/* 1794 */     for (int j = this.size; j-- != 0; ) {
/* 1795 */       int pos; if (Float.floatToIntBits(key[i]) == 0) {
/* 1796 */         pos = newN;
/*      */       } else {
/* 1798 */         pos = HashCommon.mix(HashCommon.float2int(key[i])) & mask;
/* 1799 */         while (Float.floatToIntBits(newKey[pos]) != 0)
/* 1800 */           pos = pos + 1 & mask; 
/*      */       } 
/* 1802 */       newKey[pos] = key[i];
/* 1803 */       newValue[pos] = value[i];
/* 1804 */       if (prev != -1) {
/* 1805 */         newLink[newPrev] = newLink[newPrev] ^ (newLink[newPrev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1806 */         newLink[pos] = newLink[pos] ^ (newLink[pos] ^ (newPrev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1807 */         newPrev = pos;
/*      */       } else {
/* 1809 */         newPrev = this.first = pos;
/*      */         
/* 1811 */         newLink[pos] = -1L;
/*      */       } 
/* 1813 */       int t = i;
/* 1814 */       i = (int)link[i];
/* 1815 */       prev = t;
/*      */     } 
/* 1817 */     this.link = newLink;
/* 1818 */     this.last = newPrev;
/* 1819 */     if (newPrev != -1)
/*      */     {
/* 1821 */       newLink[newPrev] = newLink[newPrev] | 0xFFFFFFFFL; } 
/* 1822 */     this.n = newN;
/* 1823 */     this.mask = mask;
/* 1824 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1825 */     this.key = newKey;
/* 1826 */     this.value = newValue;
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
/*      */   public Float2BooleanLinkedOpenHashMap clone() {
/*      */     Float2BooleanLinkedOpenHashMap c;
/*      */     try {
/* 1843 */       c = (Float2BooleanLinkedOpenHashMap)super.clone();
/* 1844 */     } catch (CloneNotSupportedException cantHappen) {
/* 1845 */       throw new InternalError();
/*      */     } 
/* 1847 */     c.keys = null;
/* 1848 */     c.values = null;
/* 1849 */     c.entries = null;
/* 1850 */     c.containsNullKey = this.containsNullKey;
/* 1851 */     c.key = (float[])this.key.clone();
/* 1852 */     c.value = (boolean[])this.value.clone();
/* 1853 */     c.link = (long[])this.link.clone();
/* 1854 */     return c;
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
/* 1867 */     int h = 0;
/* 1868 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1869 */       while (Float.floatToIntBits(this.key[i]) == 0)
/* 1870 */         i++; 
/* 1871 */       t = HashCommon.float2int(this.key[i]);
/* 1872 */       t ^= this.value[i] ? 1231 : 1237;
/* 1873 */       h += t;
/* 1874 */       i++;
/*      */     } 
/*      */     
/* 1877 */     if (this.containsNullKey)
/* 1878 */       h += this.value[this.n] ? 1231 : 1237; 
/* 1879 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1882 */     float[] key = this.key;
/* 1883 */     boolean[] value = this.value;
/* 1884 */     MapIterator i = new MapIterator();
/* 1885 */     s.defaultWriteObject();
/* 1886 */     for (int j = this.size; j-- != 0; ) {
/* 1887 */       int e = i.nextEntry();
/* 1888 */       s.writeFloat(key[e]);
/* 1889 */       s.writeBoolean(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1894 */     s.defaultReadObject();
/* 1895 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1896 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1897 */     this.mask = this.n - 1;
/* 1898 */     float[] key = this.key = new float[this.n + 1];
/* 1899 */     boolean[] value = this.value = new boolean[this.n + 1];
/* 1900 */     long[] link = this.link = new long[this.n + 1];
/* 1901 */     int prev = -1;
/* 1902 */     this.first = this.last = -1;
/*      */ 
/*      */     
/* 1905 */     for (int i = this.size; i-- != 0; ) {
/* 1906 */       int pos; float k = s.readFloat();
/* 1907 */       boolean v = s.readBoolean();
/* 1908 */       if (Float.floatToIntBits(k) == 0) {
/* 1909 */         pos = this.n;
/* 1910 */         this.containsNullKey = true;
/*      */       } else {
/* 1912 */         pos = HashCommon.mix(HashCommon.float2int(k)) & this.mask;
/* 1913 */         while (Float.floatToIntBits(key[pos]) != 0)
/* 1914 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1916 */       key[pos] = k;
/* 1917 */       value[pos] = v;
/* 1918 */       if (this.first != -1) {
/* 1919 */         link[prev] = link[prev] ^ (link[prev] ^ pos & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/* 1920 */         link[pos] = link[pos] ^ (link[pos] ^ (prev & 0xFFFFFFFFL) << 32L) & 0xFFFFFFFF00000000L;
/* 1921 */         prev = pos; continue;
/*      */       } 
/* 1923 */       prev = this.first = pos;
/*      */       
/* 1925 */       link[pos] = link[pos] | 0xFFFFFFFF00000000L;
/*      */     } 
/*      */     
/* 1928 */     this.last = prev;
/* 1929 */     if (prev != -1)
/*      */     {
/* 1931 */       link[prev] = link[prev] | 0xFFFFFFFFL;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\floats\Float2BooleanLinkedOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */