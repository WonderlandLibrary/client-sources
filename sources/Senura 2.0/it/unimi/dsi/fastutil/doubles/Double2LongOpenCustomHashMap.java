/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
/*      */ import it.unimi.dsi.fastutil.longs.AbstractLongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectSet;
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
/*      */ public class Double2LongOpenCustomHashMap
/*      */   extends AbstractDouble2LongMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient long[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2LongMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient LongCollection values;
/*      */   
/*      */   public Double2LongOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
/*  107 */     this.strategy = strategy;
/*  108 */     if (f <= 0.0F || f > 1.0F)
/*  109 */       throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1"); 
/*  110 */     if (expected < 0)
/*  111 */       throw new IllegalArgumentException("The expected number of elements must be nonnegative"); 
/*  112 */     this.f = f;
/*  113 */     this.minN = this.n = HashCommon.arraySize(expected, f);
/*  114 */     this.mask = this.n - 1;
/*  115 */     this.maxFill = HashCommon.maxFill(this.n, f);
/*  116 */     this.key = new double[this.n + 1];
/*  117 */     this.value = new long[this.n + 1];
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
/*      */   public Double2LongOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
/*  129 */     this(expected, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Double2LongOpenCustomHashMap(DoubleHash.Strategy strategy) {
/*  140 */     this(16, 0.75F, strategy);
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
/*      */   public Double2LongOpenCustomHashMap(Map<? extends Double, ? extends Long> m, float f, DoubleHash.Strategy strategy) {
/*  154 */     this(m.size(), f, strategy);
/*  155 */     putAll(m);
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
/*      */   public Double2LongOpenCustomHashMap(Map<? extends Double, ? extends Long> m, DoubleHash.Strategy strategy) {
/*  168 */     this(m, 0.75F, strategy);
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
/*      */   public Double2LongOpenCustomHashMap(Double2LongMap m, float f, DoubleHash.Strategy strategy) {
/*  182 */     this(m.size(), f, strategy);
/*  183 */     putAll(m);
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
/*      */   public Double2LongOpenCustomHashMap(Double2LongMap m, DoubleHash.Strategy strategy) {
/*  196 */     this(m, 0.75F, strategy);
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
/*      */   public Double2LongOpenCustomHashMap(double[] k, long[] v, float f, DoubleHash.Strategy strategy) {
/*  214 */     this(k.length, f, strategy);
/*  215 */     if (k.length != v.length) {
/*  216 */       throw new IllegalArgumentException("The key array and the value array have different lengths (" + k.length + " and " + v.length + ")");
/*      */     }
/*  218 */     for (int i = 0; i < k.length; i++) {
/*  219 */       put(k[i], v[i]);
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
/*      */ 
/*      */   
/*      */   public Double2LongOpenCustomHashMap(double[] k, long[] v, DoubleHash.Strategy strategy) {
/*  236 */     this(k, v, 0.75F, strategy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoubleHash.Strategy strategy() {
/*  244 */     return this.strategy;
/*      */   }
/*      */   private int realSize() {
/*  247 */     return this.containsNullKey ? (this.size - 1) : this.size;
/*      */   }
/*      */   private void ensureCapacity(int capacity) {
/*  250 */     int needed = HashCommon.arraySize(capacity, this.f);
/*  251 */     if (needed > this.n)
/*  252 */       rehash(needed); 
/*      */   }
/*      */   private void tryCapacity(long capacity) {
/*  255 */     int needed = (int)Math.min(1073741824L, 
/*  256 */         Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(((float)capacity / this.f)))));
/*  257 */     if (needed > this.n)
/*  258 */       rehash(needed); 
/*      */   }
/*      */   private long removeEntry(int pos) {
/*  261 */     long oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private long removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     long oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Long> m) {
/*  278 */     if (this.f <= 0.5D) {
/*  279 */       ensureCapacity(m.size());
/*      */     } else {
/*  281 */       tryCapacity((size() + m.size()));
/*      */     } 
/*  283 */     super.putAll(m);
/*      */   }
/*      */   
/*      */   private int find(double k) {
/*  287 */     if (this.strategy.equals(k, 0.0D)) {
/*  288 */       return this.containsNullKey ? this.n : -(this.n + 1);
/*      */     }
/*  290 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  293 */     if (Double.doubleToLongBits(
/*  294 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  295 */       return -(pos + 1); 
/*  296 */     if (this.strategy.equals(k, curr)) {
/*  297 */       return pos;
/*      */     }
/*      */     while (true) {
/*  300 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  301 */         return -(pos + 1); 
/*  302 */       if (this.strategy.equals(k, curr))
/*  303 */         return pos; 
/*      */     } 
/*      */   }
/*      */   private void insert(int pos, double k, long v) {
/*  307 */     if (pos == this.n)
/*  308 */       this.containsNullKey = true; 
/*  309 */     this.key[pos] = k;
/*  310 */     this.value[pos] = v;
/*  311 */     if (this.size++ >= this.maxFill) {
/*  312 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public long put(double k, long v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     long oldValue = this.value[pos];
/*  324 */     this.value[pos] = v;
/*  325 */     return oldValue;
/*      */   }
/*      */   private long addToValue(int pos, long incr) {
/*  328 */     long oldValue = this.value[pos];
/*  329 */     this.value[pos] = oldValue + incr;
/*  330 */     return oldValue;
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
/*      */   public long addTo(double k, long incr) {
/*      */     int pos;
/*  350 */     if (this.strategy.equals(k, 0.0D)) {
/*  351 */       if (this.containsNullKey)
/*  352 */         return addToValue(this.n, incr); 
/*  353 */       pos = this.n;
/*  354 */       this.containsNullKey = true;
/*      */     } else {
/*      */       
/*  357 */       double[] key = this.key;
/*      */       double curr;
/*  359 */       if (Double.doubleToLongBits(
/*  360 */           curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) != 0L) {
/*  361 */         if (this.strategy.equals(curr, k))
/*  362 */           return addToValue(pos, incr); 
/*  363 */         while (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) != 0L) {
/*  364 */           if (this.strategy.equals(curr, k))
/*  365 */             return addToValue(pos, incr); 
/*      */         } 
/*      */       } 
/*  368 */     }  this.key[pos] = k;
/*  369 */     this.value[pos] = this.defRetValue + incr;
/*  370 */     if (this.size++ >= this.maxFill) {
/*  371 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*      */     }
/*      */     
/*  374 */     return this.defRetValue;
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
/*  387 */     double[] key = this.key; while (true) {
/*      */       double curr; int last;
/*  389 */       pos = (last = pos) + 1 & this.mask;
/*      */       while (true) {
/*  391 */         if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  392 */           key[last] = 0.0D;
/*      */           return;
/*      */         } 
/*  395 */         int slot = HashCommon.mix(this.strategy.hashCode(curr)) & this.mask;
/*  396 */         if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */           break; 
/*  398 */         pos = pos + 1 & this.mask;
/*      */       } 
/*  400 */       key[last] = curr;
/*  401 */       this.value[last] = this.value[pos];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long remove(double k) {
/*  407 */     if (this.strategy.equals(k, 0.0D)) {
/*  408 */       if (this.containsNullKey)
/*  409 */         return removeNullEntry(); 
/*  410 */       return this.defRetValue;
/*      */     } 
/*      */     
/*  413 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  416 */     if (Double.doubleToLongBits(
/*  417 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  418 */       return this.defRetValue; 
/*  419 */     if (this.strategy.equals(k, curr))
/*  420 */       return removeEntry(pos); 
/*      */     while (true) {
/*  422 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  423 */         return this.defRetValue; 
/*  424 */       if (this.strategy.equals(k, curr)) {
/*  425 */         return removeEntry(pos);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long get(double k) {
/*  431 */     if (this.strategy.equals(k, 0.0D)) {
/*  432 */       return this.containsNullKey ? this.value[this.n] : this.defRetValue;
/*      */     }
/*  434 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  437 */     if (Double.doubleToLongBits(
/*  438 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  439 */       return this.defRetValue; 
/*  440 */     if (this.strategy.equals(k, curr)) {
/*  441 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  444 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  445 */         return this.defRetValue; 
/*  446 */       if (this.strategy.equals(k, curr)) {
/*  447 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsKey(double k) {
/*  453 */     if (this.strategy.equals(k, 0.0D)) {
/*  454 */       return this.containsNullKey;
/*      */     }
/*  456 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  459 */     if (Double.doubleToLongBits(
/*  460 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  461 */       return false; 
/*  462 */     if (this.strategy.equals(k, curr)) {
/*  463 */       return true;
/*      */     }
/*      */     while (true) {
/*  466 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  467 */         return false; 
/*  468 */       if (this.strategy.equals(k, curr))
/*  469 */         return true; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean containsValue(long v) {
/*  474 */     long[] value = this.value;
/*  475 */     double[] key = this.key;
/*  476 */     if (this.containsNullKey && value[this.n] == v)
/*  477 */       return true; 
/*  478 */     for (int i = this.n; i-- != 0;) {
/*  479 */       if (Double.doubleToLongBits(key[i]) != 0L && value[i] == v)
/*  480 */         return true; 
/*  481 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long getOrDefault(double k, long defaultValue) {
/*  487 */     if (this.strategy.equals(k, 0.0D)) {
/*  488 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  490 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  493 */     if (Double.doubleToLongBits(
/*  494 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  495 */       return defaultValue; 
/*  496 */     if (this.strategy.equals(k, curr)) {
/*  497 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  500 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  501 */         return defaultValue; 
/*  502 */       if (this.strategy.equals(k, curr)) {
/*  503 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public long putIfAbsent(double k, long v) {
/*  509 */     int pos = find(k);
/*  510 */     if (pos >= 0)
/*  511 */       return this.value[pos]; 
/*  512 */     insert(-pos - 1, k, v);
/*  513 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, long v) {
/*  519 */     if (this.strategy.equals(k, 0.0D)) {
/*  520 */       if (this.containsNullKey && v == this.value[this.n]) {
/*  521 */         removeNullEntry();
/*  522 */         return true;
/*      */       } 
/*  524 */       return false;
/*      */     } 
/*      */     
/*  527 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  530 */     if (Double.doubleToLongBits(
/*  531 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  532 */       return false; 
/*  533 */     if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  534 */       removeEntry(pos);
/*  535 */       return true;
/*      */     } 
/*      */     while (true) {
/*  538 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  539 */         return false; 
/*  540 */       if (this.strategy.equals(k, curr) && v == this.value[pos]) {
/*  541 */         removeEntry(pos);
/*  542 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, long oldValue, long v) {
/*  549 */     int pos = find(k);
/*  550 */     if (pos < 0 || oldValue != this.value[pos])
/*  551 */       return false; 
/*  552 */     this.value[pos] = v;
/*  553 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public long replace(double k, long v) {
/*  558 */     int pos = find(k);
/*  559 */     if (pos < 0)
/*  560 */       return this.defRetValue; 
/*  561 */     long oldValue = this.value[pos];
/*  562 */     this.value[pos] = v;
/*  563 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public long computeIfAbsent(double k, DoubleToLongFunction mappingFunction) {
/*  568 */     Objects.requireNonNull(mappingFunction);
/*  569 */     int pos = find(k);
/*  570 */     if (pos >= 0)
/*  571 */       return this.value[pos]; 
/*  572 */     long newValue = mappingFunction.applyAsLong(k);
/*  573 */     insert(-pos - 1, k, newValue);
/*  574 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfAbsentNullable(double k, DoubleFunction<? extends Long> mappingFunction) {
/*  580 */     Objects.requireNonNull(mappingFunction);
/*  581 */     int pos = find(k);
/*  582 */     if (pos >= 0)
/*  583 */       return this.value[pos]; 
/*  584 */     Long newValue = mappingFunction.apply(k);
/*  585 */     if (newValue == null)
/*  586 */       return this.defRetValue; 
/*  587 */     long v = newValue.longValue();
/*  588 */     insert(-pos - 1, k, v);
/*  589 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long computeIfPresent(double k, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
/*  595 */     Objects.requireNonNull(remappingFunction);
/*  596 */     int pos = find(k);
/*  597 */     if (pos < 0)
/*  598 */       return this.defRetValue; 
/*  599 */     Long newValue = remappingFunction.apply(Double.valueOf(k), Long.valueOf(this.value[pos]));
/*  600 */     if (newValue == null) {
/*  601 */       if (this.strategy.equals(k, 0.0D)) {
/*  602 */         removeNullEntry();
/*      */       } else {
/*  604 */         removeEntry(pos);
/*  605 */       }  return this.defRetValue;
/*      */     } 
/*  607 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long compute(double k, BiFunction<? super Double, ? super Long, ? extends Long> remappingFunction) {
/*  613 */     Objects.requireNonNull(remappingFunction);
/*  614 */     int pos = find(k);
/*  615 */     Long newValue = remappingFunction.apply(Double.valueOf(k), (pos >= 0) ? Long.valueOf(this.value[pos]) : null);
/*  616 */     if (newValue == null) {
/*  617 */       if (pos >= 0)
/*  618 */         if (this.strategy.equals(k, 0.0D)) {
/*  619 */           removeNullEntry();
/*      */         } else {
/*  621 */           removeEntry(pos);
/*      */         }  
/*  623 */       return this.defRetValue;
/*      */     } 
/*  625 */     long newVal = newValue.longValue();
/*  626 */     if (pos < 0) {
/*  627 */       insert(-pos - 1, k, newVal);
/*  628 */       return newVal;
/*      */     } 
/*  630 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public long merge(double k, long v, BiFunction<? super Long, ? super Long, ? extends Long> remappingFunction) {
/*  636 */     Objects.requireNonNull(remappingFunction);
/*  637 */     int pos = find(k);
/*  638 */     if (pos < 0) {
/*  639 */       insert(-pos - 1, k, v);
/*  640 */       return v;
/*      */     } 
/*  642 */     Long newValue = remappingFunction.apply(Long.valueOf(this.value[pos]), Long.valueOf(v));
/*  643 */     if (newValue == null) {
/*  644 */       if (this.strategy.equals(k, 0.0D)) {
/*  645 */         removeNullEntry();
/*      */       } else {
/*  647 */         removeEntry(pos);
/*  648 */       }  return this.defRetValue;
/*      */     } 
/*  650 */     this.value[pos] = newValue.longValue(); return newValue.longValue();
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
/*  661 */     if (this.size == 0)
/*      */       return; 
/*  663 */     this.size = 0;
/*  664 */     this.containsNullKey = false;
/*  665 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  669 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  673 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2LongMap.Entry, Map.Entry<Double, Long>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  685 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  691 */       return Double2LongOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public long getLongValue() {
/*  695 */       return Double2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public long setValue(long v) {
/*  699 */       long oldValue = Double2LongOpenCustomHashMap.this.value[this.index];
/*  700 */       Double2LongOpenCustomHashMap.this.value[this.index] = v;
/*  701 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  711 */       return Double.valueOf(Double2LongOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long getValue() {
/*  721 */       return Long.valueOf(Double2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Long setValue(Long v) {
/*  731 */       return Long.valueOf(setValue(v.longValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  736 */       if (!(o instanceof Map.Entry))
/*  737 */         return false; 
/*  738 */       Map.Entry<Double, Long> e = (Map.Entry<Double, Long>)o;
/*  739 */       return (Double2LongOpenCustomHashMap.this.strategy.equals(Double2LongOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && Double2LongOpenCustomHashMap.this.value[this.index] == ((Long)e
/*  740 */         .getValue()).longValue());
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  744 */       return Double2LongOpenCustomHashMap.this.strategy.hashCode(Double2LongOpenCustomHashMap.this.key[this.index]) ^ HashCommon.long2int(Double2LongOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  748 */       return Double2LongOpenCustomHashMap.this.key[this.index] + "=>" + Double2LongOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  758 */     int pos = Double2LongOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  765 */     int last = -1;
/*      */     
/*  767 */     int c = Double2LongOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  771 */     boolean mustReturnNullKey = Double2LongOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  778 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  781 */       if (!hasNext())
/*  782 */         throw new NoSuchElementException(); 
/*  783 */       this.c--;
/*  784 */       if (this.mustReturnNullKey) {
/*  785 */         this.mustReturnNullKey = false;
/*  786 */         return this.last = Double2LongOpenCustomHashMap.this.n;
/*      */       } 
/*  788 */       double[] key = Double2LongOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  790 */         if (--this.pos < 0) {
/*      */           
/*  792 */           this.last = Integer.MIN_VALUE;
/*  793 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  794 */           int p = HashCommon.mix(Double2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Double2LongOpenCustomHashMap.this.mask;
/*  795 */           while (!Double2LongOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  796 */             p = p + 1 & Double2LongOpenCustomHashMap.this.mask; 
/*  797 */           return p;
/*      */         } 
/*  799 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  800 */           return this.last = this.pos;
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
/*  814 */       double[] key = Double2LongOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  816 */         pos = (last = pos) + 1 & Double2LongOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  818 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  819 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  822 */           int slot = HashCommon.mix(Double2LongOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2LongOpenCustomHashMap.this.mask;
/*  823 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  825 */           pos = pos + 1 & Double2LongOpenCustomHashMap.this.mask;
/*      */         } 
/*  827 */         if (pos < last) {
/*  828 */           if (this.wrapped == null)
/*  829 */             this.wrapped = new DoubleArrayList(2); 
/*  830 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  832 */         key[last] = curr;
/*  833 */         Double2LongOpenCustomHashMap.this.value[last] = Double2LongOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  837 */       if (this.last == -1)
/*  838 */         throw new IllegalStateException(); 
/*  839 */       if (this.last == Double2LongOpenCustomHashMap.this.n) {
/*  840 */         Double2LongOpenCustomHashMap.this.containsNullKey = false;
/*  841 */       } else if (this.pos >= 0) {
/*  842 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  845 */         Double2LongOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  846 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  849 */       Double2LongOpenCustomHashMap.this.size--;
/*  850 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  855 */       int i = n;
/*  856 */       while (i-- != 0 && hasNext())
/*  857 */         nextEntry(); 
/*  858 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2LongMap.Entry> { private Double2LongOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Double2LongOpenCustomHashMap.MapEntry next() {
/*  865 */       return this.entry = new Double2LongOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  869 */       super.remove();
/*  870 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2LongMap.Entry> { private FastEntryIterator() {
/*  874 */       this.entry = new Double2LongOpenCustomHashMap.MapEntry();
/*      */     } private final Double2LongOpenCustomHashMap.MapEntry entry;
/*      */     public Double2LongOpenCustomHashMap.MapEntry next() {
/*  877 */       this.entry.index = nextEntry();
/*  878 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2LongMap.Entry> implements Double2LongMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2LongMap.Entry> iterator() {
/*  884 */       return new Double2LongOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2LongMap.Entry> fastIterator() {
/*  888 */       return new Double2LongOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  893 */       if (!(o instanceof Map.Entry))
/*  894 */         return false; 
/*  895 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  896 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  897 */         return false; 
/*  898 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  899 */         return false; 
/*  900 */       double k = ((Double)e.getKey()).doubleValue();
/*  901 */       long v = ((Long)e.getValue()).longValue();
/*  902 */       if (Double2LongOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  903 */         return (Double2LongOpenCustomHashMap.this.containsNullKey && Double2LongOpenCustomHashMap.this.value[Double2LongOpenCustomHashMap.this.n] == v);
/*      */       }
/*  905 */       double[] key = Double2LongOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  908 */       if (Double.doubleToLongBits(
/*  909 */           curr = key[pos = HashCommon.mix(Double2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Double2LongOpenCustomHashMap.this.mask]) == 0L)
/*  910 */         return false; 
/*  911 */       if (Double2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  912 */         return (Double2LongOpenCustomHashMap.this.value[pos] == v);
/*      */       }
/*      */       while (true) {
/*  915 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2LongOpenCustomHashMap.this.mask]) == 0L)
/*  916 */           return false; 
/*  917 */         if (Double2LongOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  918 */           return (Double2LongOpenCustomHashMap.this.value[pos] == v);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  924 */       if (!(o instanceof Map.Entry))
/*  925 */         return false; 
/*  926 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  927 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  928 */         return false; 
/*  929 */       if (e.getValue() == null || !(e.getValue() instanceof Long))
/*  930 */         return false; 
/*  931 */       double k = ((Double)e.getKey()).doubleValue();
/*  932 */       long v = ((Long)e.getValue()).longValue();
/*  933 */       if (Double2LongOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  934 */         if (Double2LongOpenCustomHashMap.this.containsNullKey && Double2LongOpenCustomHashMap.this.value[Double2LongOpenCustomHashMap.this.n] == v) {
/*  935 */           Double2LongOpenCustomHashMap.this.removeNullEntry();
/*  936 */           return true;
/*      */         } 
/*  938 */         return false;
/*      */       } 
/*      */       
/*  941 */       double[] key = Double2LongOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  944 */       if (Double.doubleToLongBits(
/*  945 */           curr = key[pos = HashCommon.mix(Double2LongOpenCustomHashMap.this.strategy.hashCode(k)) & Double2LongOpenCustomHashMap.this.mask]) == 0L)
/*  946 */         return false; 
/*  947 */       if (Double2LongOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  948 */         if (Double2LongOpenCustomHashMap.this.value[pos] == v) {
/*  949 */           Double2LongOpenCustomHashMap.this.removeEntry(pos);
/*  950 */           return true;
/*      */         } 
/*  952 */         return false;
/*      */       } 
/*      */       while (true) {
/*  955 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2LongOpenCustomHashMap.this.mask]) == 0L)
/*  956 */           return false; 
/*  957 */         if (Double2LongOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  958 */           Double2LongOpenCustomHashMap.this.value[pos] == v) {
/*  959 */           Double2LongOpenCustomHashMap.this.removeEntry(pos);
/*  960 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  967 */       return Double2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  971 */       Double2LongOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2LongMap.Entry> consumer) {
/*  976 */       if (Double2LongOpenCustomHashMap.this.containsNullKey)
/*  977 */         consumer.accept(new AbstractDouble2LongMap.BasicEntry(Double2LongOpenCustomHashMap.this.key[Double2LongOpenCustomHashMap.this.n], Double2LongOpenCustomHashMap.this.value[Double2LongOpenCustomHashMap.this.n])); 
/*  978 */       for (int pos = Double2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  979 */         if (Double.doubleToLongBits(Double2LongOpenCustomHashMap.this.key[pos]) != 0L)
/*  980 */           consumer.accept(new AbstractDouble2LongMap.BasicEntry(Double2LongOpenCustomHashMap.this.key[pos], Double2LongOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2LongMap.Entry> consumer) {
/*  985 */       AbstractDouble2LongMap.BasicEntry entry = new AbstractDouble2LongMap.BasicEntry();
/*  986 */       if (Double2LongOpenCustomHashMap.this.containsNullKey) {
/*  987 */         entry.key = Double2LongOpenCustomHashMap.this.key[Double2LongOpenCustomHashMap.this.n];
/*  988 */         entry.value = Double2LongOpenCustomHashMap.this.value[Double2LongOpenCustomHashMap.this.n];
/*  989 */         consumer.accept(entry);
/*      */       } 
/*  991 */       for (int pos = Double2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/*  992 */         if (Double.doubleToLongBits(Double2LongOpenCustomHashMap.this.key[pos]) != 0L) {
/*  993 */           entry.key = Double2LongOpenCustomHashMap.this.key[pos];
/*  994 */           entry.value = Double2LongOpenCustomHashMap.this.value[pos];
/*  995 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2LongMap.FastEntrySet double2LongEntrySet() {
/* 1001 */     if (this.entries == null)
/* 1002 */       this.entries = new MapEntrySet(); 
/* 1003 */     return this.entries;
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
/* 1020 */       return Double2LongOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1026 */       return new Double2LongOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1031 */       if (Double2LongOpenCustomHashMap.this.containsNullKey)
/* 1032 */         consumer.accept(Double2LongOpenCustomHashMap.this.key[Double2LongOpenCustomHashMap.this.n]); 
/* 1033 */       for (int pos = Double2LongOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1034 */         double k = Double2LongOpenCustomHashMap.this.key[pos];
/* 1035 */         if (Double.doubleToLongBits(k) != 0L)
/* 1036 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1041 */       return Double2LongOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1045 */       return Double2LongOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1049 */       int oldSize = Double2LongOpenCustomHashMap.this.size;
/* 1050 */       Double2LongOpenCustomHashMap.this.remove(k);
/* 1051 */       return (Double2LongOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1055 */       Double2LongOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1060 */     if (this.keys == null)
/* 1061 */       this.keys = new KeySet(); 
/* 1062 */     return this.keys;
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
/*      */     implements LongIterator
/*      */   {
/*      */     public long nextLong() {
/* 1079 */       return Double2LongOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public LongCollection values() {
/* 1084 */     if (this.values == null)
/* 1085 */       this.values = (LongCollection)new AbstractLongCollection()
/*      */         {
/*      */           public LongIterator iterator() {
/* 1088 */             return new Double2LongOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1092 */             return Double2LongOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(long v) {
/* 1096 */             return Double2LongOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1100 */             Double2LongOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(LongConsumer consumer)
/*      */           {
/* 1105 */             if (Double2LongOpenCustomHashMap.this.containsNullKey)
/* 1106 */               consumer.accept(Double2LongOpenCustomHashMap.this.value[Double2LongOpenCustomHashMap.this.n]); 
/* 1107 */             for (int pos = Double2LongOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1108 */               if (Double.doubleToLongBits(Double2LongOpenCustomHashMap.this.key[pos]) != 0L)
/* 1109 */                 consumer.accept(Double2LongOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1112 */     return this.values;
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
/* 1129 */     return trim(this.size);
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
/* 1153 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1154 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1155 */       return true; 
/*      */     try {
/* 1157 */       rehash(l);
/* 1158 */     } catch (OutOfMemoryError cantDoIt) {
/* 1159 */       return false;
/*      */     } 
/* 1161 */     return true;
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
/* 1177 */     double[] key = this.key;
/* 1178 */     long[] value = this.value;
/* 1179 */     int mask = newN - 1;
/* 1180 */     double[] newKey = new double[newN + 1];
/* 1181 */     long[] newValue = new long[newN + 1];
/* 1182 */     int i = this.n;
/* 1183 */     for (int j = realSize(); j-- != 0; ) {
/* 1184 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1185 */       if (Double.doubleToLongBits(newKey[
/* 1186 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L)
/* 1187 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1188 */       newKey[pos] = key[i];
/* 1189 */       newValue[pos] = value[i];
/*      */     } 
/* 1191 */     newValue[newN] = value[this.n];
/* 1192 */     this.n = newN;
/* 1193 */     this.mask = mask;
/* 1194 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1195 */     this.key = newKey;
/* 1196 */     this.value = newValue;
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
/*      */   public Double2LongOpenCustomHashMap clone() {
/*      */     Double2LongOpenCustomHashMap c;
/*      */     try {
/* 1213 */       c = (Double2LongOpenCustomHashMap)super.clone();
/* 1214 */     } catch (CloneNotSupportedException cantHappen) {
/* 1215 */       throw new InternalError();
/*      */     } 
/* 1217 */     c.keys = null;
/* 1218 */     c.values = null;
/* 1219 */     c.entries = null;
/* 1220 */     c.containsNullKey = this.containsNullKey;
/* 1221 */     c.key = (double[])this.key.clone();
/* 1222 */     c.value = (long[])this.value.clone();
/* 1223 */     c.strategy = this.strategy;
/* 1224 */     return c;
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
/* 1237 */     int h = 0;
/* 1238 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1239 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1240 */         i++; 
/* 1241 */       t = this.strategy.hashCode(this.key[i]);
/* 1242 */       t ^= HashCommon.long2int(this.value[i]);
/* 1243 */       h += t;
/* 1244 */       i++;
/*      */     } 
/*      */     
/* 1247 */     if (this.containsNullKey)
/* 1248 */       h += HashCommon.long2int(this.value[this.n]); 
/* 1249 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1252 */     double[] key = this.key;
/* 1253 */     long[] value = this.value;
/* 1254 */     MapIterator i = new MapIterator();
/* 1255 */     s.defaultWriteObject();
/* 1256 */     for (int j = this.size; j-- != 0; ) {
/* 1257 */       int e = i.nextEntry();
/* 1258 */       s.writeDouble(key[e]);
/* 1259 */       s.writeLong(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1264 */     s.defaultReadObject();
/* 1265 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1266 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1267 */     this.mask = this.n - 1;
/* 1268 */     double[] key = this.key = new double[this.n + 1];
/* 1269 */     long[] value = this.value = new long[this.n + 1];
/*      */ 
/*      */     
/* 1272 */     for (int i = this.size; i-- != 0; ) {
/* 1273 */       int pos; double k = s.readDouble();
/* 1274 */       long v = s.readLong();
/* 1275 */       if (this.strategy.equals(k, 0.0D)) {
/* 1276 */         pos = this.n;
/* 1277 */         this.containsNullKey = true;
/*      */       } else {
/* 1279 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1280 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1281 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1283 */       key[pos] = k;
/* 1284 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2LongOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */