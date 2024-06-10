/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.SafeMath;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*      */ import it.unimi.dsi.fastutil.shorts.AbstractShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortCollection;
/*      */ import it.unimi.dsi.fastutil.shorts.ShortIterator;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
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
/*      */ public class Double2ShortOpenHashMap
/*      */   extends AbstractDouble2ShortMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient short[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2ShortMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient ShortCollection values;
/*      */   
/*      */   public Double2ShortOpenHashMap(int expected, float f) {
/*   96 */     if (f <= 0.0F || f > 1.0F)
/*   97 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*   98 */     if (expected < 0)
/*   99 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  100 */     this.f = f;
/*  101 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  102 */     this.mask = this.n - 1;
/*  103 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  104 */     this.key = new double[this.n + 1];
/*  105 */     this.value = new short[this.n + 1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ShortOpenHashMap(int expected) {
/*  114 */     this(expected, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ShortOpenHashMap() {
/*  122 */     this(16, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ShortOpenHashMap(Map<? extends Double, ? extends Short> m, float f) {
/*  133 */     this(m.size(), f);
/*  134 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ShortOpenHashMap(Map<? extends Double, ? extends Short> m) {
/*  144 */     this(m, 0.75F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ShortOpenHashMap(Double2ShortMap m, float f) {
/*  155 */     this(m.size(), f);
/*  156 */     putAll(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2ShortOpenHashMap(Double2ShortMap m) {
/*  166 */     this(m, 0.75F);
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
/*      */   public Double2ShortOpenHashMap(double[] k, short[] v, float f) {
/*  181 */     this(k.length, f);
/*  182 */     if (k.length != v.length) {
/*  183 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  185 */     for (int i = 0; i < k.length; i++) {
/*  186 */       put(k[i], v[i]);
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
/*      */   public Double2ShortOpenHashMap(double[] k, short[] v) {
/*  200 */     this(k, v, 0.75F);
/*      */   }
/*      */   private int realSize() {
/*  203 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  206 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  207 */     if (needed > this.n)
/*  208 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  211 */     int needed = (int)Math.min(1073741824L, 
/*  212 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  213 */     if (needed > this.n)
/*  214 */       rehash(needed); 
/*      */   }
/*      */   private short removeEntry(int pos) {
/*  217 */     short oldValue = this.value[pos];
/*  218 */     this.size--;
/*  219 */     shiftKeys(pos);
/*  220 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  221 */       rehash(this.n / 2); 
/*  222 */     return oldValue;
/*      */   }
/*      */   private short removeNullEntry() {
/*  225 */     this.containsNullKey = false;
/*  226 */     short oldValue = this.value[this.n];
/*  227 */     this.size--;
/*  228 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  229 */       rehash(this.n / 2); 
/*  230 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Short> m) {
/*  234 */     if (this.f <= 0.5D) {
/*  235 */       ensureCapacity(m.size());
/*      */     } else {
/*  237 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  239 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  243 */     if (Double.doubleToLongBits(k) == 0L) {
/*  244 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  246 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  249 */     if (Double.doubleToLongBits(
/*  250 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  252 */       return -(pos + 1); } 
/*  253 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  254 */       return pos;
/*      */     }
/*      */     while (true) {
/*  257 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  258 */         return -(pos + 1); 
/*  259 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  260 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, short v) {
/*  264 */     if (pos == this.n)
/*  265 */       this.containsNullKey = true; 
/*  266 */     this.key[pos] = k;
/*  267 */     this.value[pos] = v;
/*  268 */     if (this.size++ >= this.maxFill) {
/*  269 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public short put(double k, short v) {
/*  275 */     int pos = find(k);
/*  276 */     if (pos < 0) {
/*  277 */       insert(-pos - 1, k, v);
/*  278 */       return this.defRetValue;
/*      */     } 
/*  280 */     short oldValue = this.value[pos];
/*  281 */     this.value[pos] = v;
/*  282 */     return oldValue;
/*      */   }
/*      */   private short addToValue(int pos, short incr) {
/*  285 */     short oldValue = this.value[pos];
/*  286 */     this.value[pos] = (short)(oldValue + incr);
/*  287 */     return oldValue;
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
/*      */   public short addTo(double k, short incr) {
/*      */     int pos;
/*  307 */     if (Double.doubleToLongBits(k) == 0L) {
/*  308 */       if (this.containsNullKey)
/*  309 */         return addToValue(this.n, incr); 
/*  310 */       pos = this.n;
/*  311 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  314 */       double[] key = this.key;
/*      */       double curr;
/*  316 */       if (Double.doubleToLongBits(
/*  317 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) != 0L) {
/*      */         
/*  319 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  320 */           return addToValue(pos, incr); 
/*  321 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  322 */           if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k))
/*  323 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  326 */     }  this.key[pos] = k;
/*  327 */     this.value[pos] = (short)(this.defRetValue + incr);
/*  328 */     if (this.size++ >= this.maxFill) {
/*  329 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  332 */     return this.defRetValue;
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
/*  345 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  347 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  349 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  350 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  353 */         int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & this.mask;
/*  354 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  356 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  358 */       key[last] = curr;
/*  359 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public short remove(double k) {
/*  365 */     if (Double.doubleToLongBits(k) == 0L) {
/*  366 */       if (this.containsNullKey)
/*  367 */         return removeNullEntry(); 
/*  368 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  371 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  374 */     if (Double.doubleToLongBits(
/*  375 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  377 */       return this.defRetValue; } 
/*  378 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  379 */       return removeEntry(pos); 
/*      */     while (true) {
/*  381 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  382 */         return this.defRetValue; 
/*  383 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  384 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short get(double k) {
/*  390 */     if (Double.doubleToLongBits(k) == 0L) {
/*  391 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  393 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  396 */     if (Double.doubleToLongBits(
/*  397 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  399 */       return this.defRetValue; } 
/*  400 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  401 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  404 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  405 */         return this.defRetValue; 
/*  406 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  407 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  413 */     if (Double.doubleToLongBits(k) == 0L) {
/*  414 */       return this.containsNullKey;
/*      */     }
/*  416 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  419 */     if (Double.doubleToLongBits(
/*  420 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  422 */       return false; } 
/*  423 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  424 */       return true;
/*      */     }
/*      */     while (true) {
/*  427 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  428 */         return false; 
/*  429 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr))
/*  430 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(short v) {
/*  435 */     short[] value = this.value;
/*  436 */     double[] key = this.key;
/*  437 */     if (this.containsNullKey && value[this.n] == v)
/*  438 */       return true; 
/*  439 */     for (int i = this.n; i-- != 0;) {
/*  440 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  441 */         return true; 
/*  442 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short getOrDefault(double k, short defaultValue) {
/*  448 */     if (Double.doubleToLongBits(k) == 0L) {
/*  449 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  451 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  454 */     if (Double.doubleToLongBits(
/*  455 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  457 */       return defaultValue; } 
/*  458 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  459 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  462 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  463 */         return defaultValue; 
/*  464 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  465 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public short putIfAbsent(double k, short v) {
/*  471 */     int pos = find(k);
/*  472 */     if (pos >= 0)
/*  473 */       return this.value[pos]; 
/*  474 */     insert(-pos - 1, k, v);
/*  475 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, short v) {
/*  481 */     if (Double.doubleToLongBits(k) == 0L) {
/*  482 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  483 */         removeNullEntry();
/*  484 */         return true;
/*      */       } 
/*  486 */       return false;
/*      */     } 
/*      */     
/*  489 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  492 */     if (Double.doubleToLongBits(
/*  493 */         curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask]) == 0L)
/*      */     {
/*  495 */       return false; } 
/*  496 */     if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  497 */       removeEntry(pos);
/*  498 */       return true;
/*      */     } 
/*      */     while (true) {
/*  501 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  502 */         return false; 
/*  503 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr) && v == this.value[pos]) {
/*  504 */         removeEntry(pos);
/*  505 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, short oldValue, short v) {
/*  512 */     int pos = find(k);
/*  513 */     if (pos < 0 || oldValue != this.value[pos])
/*  514 */       return false; 
/*  515 */     this.value[pos] = v;
/*  516 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public short replace(double k, short v) {
/*  521 */     int pos = find(k);
/*  522 */     if (pos < 0)
/*  523 */       return this.defRetValue; 
/*  524 */     short oldValue = this.value[pos];
/*  525 */     this.value[pos] = v;
/*  526 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public short computeIfAbsent(double k, DoubleToIntFunction mappingFunction) {
/*  531 */     Objects.requireNonNull(mappingFunction);
/*  532 */     int pos = find(k);
/*  533 */     if (pos >= 0)
/*  534 */       return this.value[pos]; 
/*  535 */     short newValue = SafeMath.safeIntToShort(mappingFunction.applyAsInt(k));
/*  536 */     insert(-pos - 1, k, newValue);
/*  537 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfAbsentNullable(double k, DoubleFunction<? extends Short> mappingFunction) {
/*  543 */     Objects.requireNonNull(mappingFunction);
/*  544 */     int pos = find(k);
/*  545 */     if (pos >= 0)
/*  546 */       return this.value[pos]; 
/*  547 */     Short newValue = mappingFunction.apply(k);
/*  548 */     if (newValue == null)
/*  549 */       return this.defRetValue; 
/*  550 */     short v = newValue.shortValue();
/*  551 */     insert(-pos - 1, k, v);
/*  552 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short computeIfPresent(double k, BiFunction<? super Double, ? super Short, ? extends Short> remappingFunction) {
/*  558 */     Objects.requireNonNull(remappingFunction);
/*  559 */     int pos = find(k);
/*  560 */     if (pos < 0)
/*  561 */       return this.defRetValue; 
/*  562 */     Short newValue = remappingFunction.apply(Double.valueOf(k), Short.valueOf(this.value[pos]));
/*  563 */     if (newValue == null) {
/*  564 */       if (Double.doubleToLongBits(k) == 0L) {
/*  565 */         removeNullEntry();
/*      */       } else {
/*  567 */         removeEntry(pos);
/*  568 */       }  return this.defRetValue;
/*      */     } 
/*  570 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short compute(double k, BiFunction<? super Double, ? super Short, ? extends Short> remappingFunction) {
/*  576 */     Objects.requireNonNull(remappingFunction);
/*  577 */     int pos = find(k);
/*  578 */     Short newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Short.valueOf(this.value[pos]) : null);
/*  579 */     if (newValue == null) {
/*  580 */       if (pos >= 0)
/*  581 */         if (Double.doubleToLongBits(k) == 0L) {
/*  582 */           removeNullEntry();
/*      */         } else {
/*  584 */           removeEntry(pos);
/*      */         }  
/*  586 */       return this.defRetValue;
/*      */     } 
/*  588 */     short newVal = newValue.shortValue();
/*  589 */     if (pos < 0) {
/*  590 */       insert(-pos - 1, k, newVal);
/*  591 */       return newVal;
/*      */     } 
/*  593 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public short merge(double k, short v, BiFunction<? super Short, ? super Short, ? extends Short> remappingFunction) {
/*  599 */     Objects.requireNonNull(remappingFunction);
/*  600 */     int pos = find(k);
/*  601 */     if (pos < 0) {
/*  602 */       insert(-pos - 1, k, v);
/*  603 */       return v;
/*      */     } 
/*  605 */     Short newValue = remappingFunction.apply(Short.valueOf(this.value[pos]), Short.valueOf(v));
/*  606 */     if (newValue == null) {
/*  607 */       if (Double.doubleToLongBits(k) == 0L) {
/*  608 */         removeNullEntry();
/*      */       } else {
/*  610 */         removeEntry(pos);
/*  611 */       }  return this.defRetValue;
/*      */     } 
/*  613 */     this.value[pos] = newValue.shortValue(); return newValue.shortValue();
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
/*  624 */     if (this.size == 0)
/*      */       return; 
/*  626 */     this.size = 0;
/*  627 */     this.containsNullKey = false;
/*  628 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  632 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  636 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2ShortMap.Entry, Map.Entry<Double, Short>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  648 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  654 */       return Double2ShortOpenHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public short getShortValue() {
/*  658 */       return Double2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public short setValue(short v) {
/*  662 */       short oldValue = Double2ShortOpenHashMap.this.value[this.index];
/*  663 */       Double2ShortOpenHashMap.this.value[this.index] = v;
/*  664 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  674 */       return Double.valueOf(Double2ShortOpenHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short getValue() {
/*  684 */       return Short.valueOf(Double2ShortOpenHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Short setValue(Short v) {
/*  694 */       return Short.valueOf(setValue(v.shortValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  699 */       if (!(o instanceof Map.Entry))
/*  700 */         return false; 
/*  701 */       Map.Entry<Double, Short> e = (Map.Entry<Double, Short>)o;
/*  702 */       return (Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[this.index]) == Double.doubleToLongBits(((Double)e.getKey()).doubleValue()) && Double2ShortOpenHashMap.this.value[this.index] == ((Short)e
/*  703 */         .getValue()).shortValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  707 */       return HashCommon.double2int(Double2ShortOpenHashMap.this.key[this.index]) ^ Double2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public String toString() {
/*  711 */       return Double2ShortOpenHashMap.this.key[this.index] + "=>" + Double2ShortOpenHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  721 */     int pos = Double2ShortOpenHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  728 */     int last = -1;
/*      */     
/*  730 */     int c = Double2ShortOpenHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  734 */     boolean mustReturnNullKey = Double2ShortOpenHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  741 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  744 */       if (!hasNext())
/*  745 */         throw new NoSuchElementException(); 
/*  746 */       this.c--;
/*  747 */       if (this.mustReturnNullKey) {
/*  748 */         this.mustReturnNullKey = false;
/*  749 */         return this.last = Double2ShortOpenHashMap.this.n;
/*      */       } 
/*  751 */       double[] key = Double2ShortOpenHashMap.this.key;
/*      */       while (true) {
/*  753 */         if (--this.pos < 0) {
/*      */           
/*  755 */           this.last = Integer.MIN_VALUE;
/*  756 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  757 */           int p = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ShortOpenHashMap.this.mask;
/*  758 */           while (Double.doubleToLongBits(k) != Double.doubleToLongBits(key[p]))
/*  759 */             p = p + 1 & Double2ShortOpenHashMap.this.mask; 
/*  760 */           return p;
/*      */         } 
/*  762 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  763 */           return this.last = this.pos;
/*      */         }
/*      */       } 
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
/*      */     private void shiftKeys(int pos) {
/*  777 */       double[] key = Double2ShortOpenHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  779 */         pos = (last = pos) + 1 & Double2ShortOpenHashMap.this.mask;
/*      */         while (true) {
/*  781 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  782 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  785 */           int slot = (int)HashCommon.mix(Double.doubleToRawLongBits(curr)) & Double2ShortOpenHashMap.this.mask;
/*  786 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  788 */           pos = pos + 1 & Double2ShortOpenHashMap.this.mask;
/*      */         } 
/*  790 */         if (pos < last) {
/*  791 */           if (this.wrapped == null)
/*  792 */             this.wrapped = new DoubleArrayList(2); 
/*  793 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  795 */         key[last] = curr;
/*  796 */         Double2ShortOpenHashMap.this.value[last] = Double2ShortOpenHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  800 */       if (this.last == -1)
/*  801 */         throw new IllegalStateException(); 
/*  802 */       if (this.last == Double2ShortOpenHashMap.this.n) {
/*  803 */         Double2ShortOpenHashMap.this.containsNullKey = false;
/*  804 */       } else if (this.pos >= 0) {
/*  805 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  808 */         Double2ShortOpenHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  809 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  812 */       Double2ShortOpenHashMap.this.size--;
/*  813 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  818 */       int i = n;
/*  819 */       while (i-- != 0 && hasNext())
/*  820 */         nextEntry(); 
/*  821 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2ShortMap.Entry> { private Double2ShortOpenHashMap.MapEntry entry;
/*      */     
/*      */     public Double2ShortOpenHashMap.MapEntry next() {
/*  828 */       return this.entry = new Double2ShortOpenHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  832 */       super.remove();
/*  833 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2ShortMap.Entry> { private FastEntryIterator() {
/*  837 */       this.entry = new Double2ShortOpenHashMap.MapEntry();
/*      */     } private final Double2ShortOpenHashMap.MapEntry entry;
/*      */     public Double2ShortOpenHashMap.MapEntry next() {
/*  840 */       this.entry.index = nextEntry();
/*  841 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2ShortMap.Entry> implements Double2ShortMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2ShortMap.Entry> iterator() {
/*  847 */       return new Double2ShortOpenHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2ShortMap.Entry> fastIterator() {
/*  851 */       return new Double2ShortOpenHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  856 */       if (!(o instanceof Map.Entry))
/*  857 */         return false; 
/*  858 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  859 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  860 */         return false; 
/*  861 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  862 */         return false; 
/*  863 */       double k = ((Double)e.getKey()).doubleValue();
/*  864 */       short v = ((Short)e.getValue()).shortValue();
/*  865 */       if (Double.doubleToLongBits(k) == 0L) {
/*  866 */         return (Double2ShortOpenHashMap.this.containsNullKey && Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n] == v);
/*      */       }
/*  868 */       double[] key = Double2ShortOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  871 */       if (Double.doubleToLongBits(
/*  872 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ShortOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  874 */         return false; } 
/*  875 */       if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  876 */         return (Double2ShortOpenHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  879 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ShortOpenHashMap.this.mask]) == 0L)
/*  880 */           return false; 
/*  881 */         if (Double.doubleToLongBits(k) == Double.doubleToLongBits(curr)) {
/*  882 */           return (Double2ShortOpenHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  888 */       if (!(o instanceof Map.Entry))
/*  889 */         return false; 
/*  890 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  891 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  892 */         return false; 
/*  893 */       if (e.getValue() == null || !(e.getValue() instanceof Short))
/*  894 */         return false; 
/*  895 */       double k = ((Double)e.getKey()).doubleValue();
/*  896 */       short v = ((Short)e.getValue()).shortValue();
/*  897 */       if (Double.doubleToLongBits(k) == 0L) {
/*  898 */         if (Double2ShortOpenHashMap.this.containsNullKey && Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n] == v) {
/*  899 */           Double2ShortOpenHashMap.this.removeNullEntry();
/*  900 */           return true;
/*      */         } 
/*  902 */         return false;
/*      */       } 
/*      */       
/*  905 */       double[] key = Double2ShortOpenHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  908 */       if (Double.doubleToLongBits(
/*  909 */           curr = key[pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & Double2ShortOpenHashMap.this.mask]) == 0L)
/*      */       {
/*  911 */         return false; } 
/*  912 */       if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k)) {
/*  913 */         if (Double2ShortOpenHashMap.this.value[pos] == v) {
/*  914 */           Double2ShortOpenHashMap.this.removeEntry(pos);
/*  915 */           return true;
/*      */         } 
/*  917 */         return false;
/*      */       } 
/*      */       while (true) {
/*  920 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2ShortOpenHashMap.this.mask]) == 0L)
/*  921 */           return false; 
/*  922 */         if (Double.doubleToLongBits(curr) == Double.doubleToLongBits(k) && 
/*  923 */           Double2ShortOpenHashMap.this.value[pos] == v) {
/*  924 */           Double2ShortOpenHashMap.this.removeEntry(pos);
/*  925 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  932 */       return Double2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  936 */       Double2ShortOpenHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2ShortMap.Entry> consumer) {
/*  941 */       if (Double2ShortOpenHashMap.this.containsNullKey)
/*  942 */         consumer.accept(new AbstractDouble2ShortMap.BasicEntry(Double2ShortOpenHashMap.this.key[Double2ShortOpenHashMap.this.n], Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n])); 
/*  943 */       for (int pos = Double2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  944 */         if (Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[pos]) != 0L)
/*  945 */           consumer.accept(new AbstractDouble2ShortMap.BasicEntry(Double2ShortOpenHashMap.this.key[pos], Double2ShortOpenHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2ShortMap.Entry> consumer) {
/*  950 */       AbstractDouble2ShortMap.BasicEntry entry = new AbstractDouble2ShortMap.BasicEntry();
/*  951 */       if (Double2ShortOpenHashMap.this.containsNullKey) {
/*  952 */         entry.key = Double2ShortOpenHashMap.this.key[Double2ShortOpenHashMap.this.n];
/*  953 */         entry.value = Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n];
/*  954 */         consumer.accept(entry);
/*      */       } 
/*  956 */       for (int pos = Double2ShortOpenHashMap.this.n; pos-- != 0;) {
/*  957 */         if (Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[pos]) != 0L) {
/*  958 */           entry.key = Double2ShortOpenHashMap.this.key[pos];
/*  959 */           entry.value = Double2ShortOpenHashMap.this.value[pos];
/*  960 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2ShortMap.FastEntrySet double2ShortEntrySet() {
/*  966 */     if (this.entries == null)
/*  967 */       this.entries = new MapEntrySet(); 
/*  968 */     return this.entries;
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
/*      */   private final class KeyIterator
/*      */     extends MapIterator
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/*  985 */       return Double2ShortOpenHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/*  991 */       return new Double2ShortOpenHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/*  996 */       if (Double2ShortOpenHashMap.this.containsNullKey)
/*  997 */         consumer.accept(Double2ShortOpenHashMap.this.key[Double2ShortOpenHashMap.this.n]); 
/*  998 */       for (int pos = Double2ShortOpenHashMap.this.n; pos-- != 0; ) {
/*  999 */         double k = Double2ShortOpenHashMap.this.key[pos];
/* 1000 */         if (Double.doubleToLongBits(k) != 0L)
/* 1001 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1006 */       return Double2ShortOpenHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1010 */       return Double2ShortOpenHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1014 */       int oldSize = Double2ShortOpenHashMap.this.size;
/* 1015 */       Double2ShortOpenHashMap.this.remove(k);
/* 1016 */       return (Double2ShortOpenHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1020 */       Double2ShortOpenHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1025 */     if (this.keys == null)
/* 1026 */       this.keys = new KeySet(); 
/* 1027 */     return this.keys;
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
/*      */   private final class ValueIterator
/*      */     extends MapIterator
/*      */     implements ShortIterator
/*      */   {
/*      */     public short nextShort() {
/* 1044 */       return Double2ShortOpenHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public ShortCollection values() {
/* 1049 */     if (this.values == null)
/* 1050 */       this.values = (ShortCollection)new AbstractShortCollection()
/*      */         {
/*      */           public ShortIterator iterator() {
/* 1053 */             return new Double2ShortOpenHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1057 */             return Double2ShortOpenHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(short v) {
/* 1061 */             return Double2ShortOpenHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1065 */             Double2ShortOpenHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(IntConsumer consumer)
/*      */           {
/* 1070 */             if (Double2ShortOpenHashMap.this.containsNullKey)
/* 1071 */               consumer.accept(Double2ShortOpenHashMap.this.value[Double2ShortOpenHashMap.this.n]); 
/* 1072 */             for (int pos = Double2ShortOpenHashMap.this.n; pos-- != 0;) {
/* 1073 */               if (Double.doubleToLongBits(Double2ShortOpenHashMap.this.key[pos]) != 0L)
/* 1074 */                 consumer.accept(Double2ShortOpenHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1077 */     return this.values;
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
/* 1094 */     return trim(this.size);
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
/* 1118 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1119 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1120 */       return true; 
/*      */     try {
/* 1122 */       rehash(l);
/* 1123 */     } catch (OutOfMemoryError cantDoIt) {
/* 1124 */       return false;
/*      */     } 
/* 1126 */     return true;
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
/* 1142 */     double[] key = this.key;
/* 1143 */     short[] value = this.value;
/* 1144 */     int mask = newN - 1;
/* 1145 */     double[] newKey = new double[newN + 1];
/* 1146 */     short[] newValue = new short[newN + 1];
/* 1147 */     int i = this.n;
/* 1148 */     for (int j = realSize(); j-- != 0; ) {
/* 1149 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1150 */       if (Double.doubleToLongBits(newKey[
/* 1151 */             pos = (int)HashCommon.mix(Double.doubleToRawLongBits(key[i])) & mask]) != 0L)
/*      */       {
/* 1153 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); } 
/* 1154 */       newKey[pos] = key[i];
/* 1155 */       newValue[pos] = value[i];
/*      */     } 
/* 1157 */     newValue[newN] = value[this.n];
/* 1158 */     this.n = newN;
/* 1159 */     this.mask = mask;
/* 1160 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1161 */     this.key = newKey;
/* 1162 */     this.value = newValue;
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
/*      */   public Double2ShortOpenHashMap clone() {
/*      */     Double2ShortOpenHashMap c;
/*      */     try {
/* 1179 */       c = (Double2ShortOpenHashMap)super.clone();
/* 1180 */     } catch (CloneNotSupportedException cantHappen) {
/* 1181 */       throw new InternalError();
/*      */     } 
/* 1183 */     c.keys = null;
/* 1184 */     c.values = null;
/* 1185 */     c.entries = null;
/* 1186 */     c.containsNullKey = this.containsNullKey;
/* 1187 */     c.key = (double[])this.key.clone();
/* 1188 */     c.value = (short[])this.value.clone();
/* 1189 */     return c;
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
/* 1202 */     int h = 0;
/* 1203 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1204 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1205 */         i++; 
/* 1206 */       t = HashCommon.double2int(this.key[i]);
/* 1207 */       t ^= this.value[i];
/* 1208 */       h += t;
/* 1209 */       i++;
/*      */     } 
/*      */     
/* 1212 */     if (this.containsNullKey)
/* 1213 */       h += this.value[this.n]; 
/* 1214 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1217 */     double[] key = this.key;
/* 1218 */     short[] value = this.value;
/* 1219 */     MapIterator i = new MapIterator();
/* 1220 */     s.defaultWriteObject();
/* 1221 */     for (int j = this.size; j-- != 0; ) {
/* 1222 */       int e = i.nextEntry();
/* 1223 */       s.writeDouble(key[e]);
/* 1224 */       s.writeShort(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1229 */     s.defaultReadObject();
/* 1230 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1231 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1232 */     this.mask = this.n - 1;
/* 1233 */     double[] key = this.key = new double[this.n + 1];
/* 1234 */     short[] value = this.value = new short[this.n + 1];
/*      */ 
/*      */     
/* 1237 */     for (int i = this.size; i-- != 0; ) {
/* 1238 */       int pos; double k = s.readDouble();
/* 1239 */       short v = s.readShort();
/* 1240 */       if (Double.doubleToLongBits(k) == 0L) {
/* 1241 */         pos = this.n;
/* 1242 */         this.containsNullKey = true;
/*      */       } else {
/* 1244 */         pos = (int)HashCommon.mix(Double.doubleToRawLongBits(k)) & this.mask;
/* 1245 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1246 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1248 */       key[pos] = k;
/* 1249 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2ShortOpenHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */