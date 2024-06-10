/*      */ package it.unimi.dsi.fastutil.doubles;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.Hash;
/*      */ import it.unimi.dsi.fastutil.HashCommon;
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
/*      */ public class Double2DoubleOpenCustomHashMap
/*      */   extends AbstractDouble2DoubleMap
/*      */   implements Serializable, Cloneable, Hash
/*      */ {
/*      */   private static final long serialVersionUID = 0L;
/*      */   private static final boolean ASSERTS = false;
/*      */   protected transient double[] key;
/*      */   protected transient double[] value;
/*      */   protected transient int mask;
/*      */   protected transient boolean containsNullKey;
/*      */   protected DoubleHash.Strategy strategy;
/*      */   protected transient int n;
/*      */   protected transient int maxFill;
/*      */   protected final transient int minN;
/*      */   protected int size;
/*      */   protected final float f;
/*      */   protected transient Double2DoubleMap.FastEntrySet entries;
/*      */   protected transient DoubleSet keys;
/*      */   protected transient DoubleCollection values;
/*      */   
/*      */   public Double2DoubleOpenCustomHashMap(int expected, float f, DoubleHash.Strategy strategy) {
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
/*  117 */     this.value = new double[this.n + 1];
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
/*      */   public Double2DoubleOpenCustomHashMap(int expected, DoubleHash.Strategy strategy) {
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
/*      */   public Double2DoubleOpenCustomHashMap(DoubleHash.Strategy strategy) {
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
/*      */   public Double2DoubleOpenCustomHashMap(Map<? extends Double, ? extends Double> m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2DoubleOpenCustomHashMap(Map<? extends Double, ? extends Double> m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2DoubleOpenCustomHashMap(Double2DoubleMap m, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2DoubleOpenCustomHashMap(Double2DoubleMap m, DoubleHash.Strategy strategy) {
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
/*      */   public Double2DoubleOpenCustomHashMap(double[] k, double[] v, float f, DoubleHash.Strategy strategy) {
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
/*      */   public Double2DoubleOpenCustomHashMap(double[] k, double[] v, DoubleHash.Strategy strategy) {
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
/*      */   private double removeEntry(int pos) {
/*  261 */     double oldValue = this.value[pos];
/*  262 */     this.size--;
/*  263 */     shiftKeys(pos);
/*  264 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  265 */       rehash(this.n / 2); 
/*  266 */     return oldValue;
/*      */   }
/*      */   private double removeNullEntry() {
/*  269 */     this.containsNullKey = false;
/*  270 */     double oldValue = this.value[this.n];
/*  271 */     this.size--;
/*  272 */     if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16)
/*  273 */       rehash(this.n / 2); 
/*  274 */     return oldValue;
/*      */   }
/*      */   
/*      */   public void putAll(Map<? extends Double, ? extends Double> m) {
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
/*      */   private void insert(int pos, double k, double v) {
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
/*      */   public double put(double k, double v) {
/*  318 */     int pos = find(k);
/*  319 */     if (pos < 0) {
/*  320 */       insert(-pos - 1, k, v);
/*  321 */       return this.defRetValue;
/*      */     } 
/*  323 */     double oldValue = this.value[pos];
/*  324 */     this.value[pos] = v;
/*  325 */     return oldValue;
/*      */   }
/*      */   private double addToValue(int pos, double incr) {
/*  328 */     double oldValue = this.value[pos];
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
/*      */   public double addTo(double k, double incr) {
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
/*      */   public double remove(double k) {
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
/*      */   public double get(double k) {
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
/*      */   public boolean containsValue(double v) {
/*  474 */     double[] value = this.value;
/*  475 */     double[] key = this.key;
/*  476 */     if (this.containsNullKey && Double.doubleToLongBits(value[this.n]) == Double.doubleToLongBits(v))
/*  477 */       return true; 
/*  478 */     for (int i = this.n; i-- != 0;) {
/*  479 */       if (Double.doubleToLongBits(key[i]) != 0L && 
/*  480 */         Double.doubleToLongBits(value[i]) == Double.doubleToLongBits(v))
/*  481 */         return true; 
/*  482 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double getOrDefault(double k, double defaultValue) {
/*  488 */     if (this.strategy.equals(k, 0.0D)) {
/*  489 */       return this.containsNullKey ? this.value[this.n] : defaultValue;
/*      */     }
/*  491 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  494 */     if (Double.doubleToLongBits(
/*  495 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  496 */       return defaultValue; 
/*  497 */     if (this.strategy.equals(k, curr)) {
/*  498 */       return this.value[pos];
/*      */     }
/*      */     while (true) {
/*  501 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  502 */         return defaultValue; 
/*  503 */       if (this.strategy.equals(k, curr)) {
/*  504 */         return this.value[pos];
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public double putIfAbsent(double k, double v) {
/*  510 */     int pos = find(k);
/*  511 */     if (pos >= 0)
/*  512 */       return this.value[pos]; 
/*  513 */     insert(-pos - 1, k, v);
/*  514 */     return this.defRetValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(double k, double v) {
/*  520 */     if (this.strategy.equals(k, 0.0D)) {
/*  521 */       if (this.containsNullKey && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[this.n])) {
/*  522 */         removeNullEntry();
/*  523 */         return true;
/*      */       } 
/*  525 */       return false;
/*      */     } 
/*      */     
/*  528 */     double[] key = this.key;
/*      */     double curr;
/*      */     int pos;
/*  531 */     if (Double.doubleToLongBits(
/*  532 */         curr = key[pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask]) == 0L)
/*  533 */       return false; 
/*  534 */     if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  535 */       removeEntry(pos);
/*  536 */       return true;
/*      */     } 
/*      */     while (true) {
/*  539 */       if (Double.doubleToLongBits(curr = key[pos = pos + 1 & this.mask]) == 0L)
/*  540 */         return false; 
/*  541 */       if (this.strategy.equals(k, curr) && Double.doubleToLongBits(v) == Double.doubleToLongBits(this.value[pos])) {
/*  542 */         removeEntry(pos);
/*  543 */         return true;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replace(double k, double oldValue, double v) {
/*  550 */     int pos = find(k);
/*  551 */     if (pos < 0 || Double.doubleToLongBits(oldValue) != Double.doubleToLongBits(this.value[pos]))
/*  552 */       return false; 
/*  553 */     this.value[pos] = v;
/*  554 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public double replace(double k, double v) {
/*  559 */     int pos = find(k);
/*  560 */     if (pos < 0)
/*  561 */       return this.defRetValue; 
/*  562 */     double oldValue = this.value[pos];
/*  563 */     this.value[pos] = v;
/*  564 */     return oldValue;
/*      */   }
/*      */ 
/*      */   
/*      */   public double computeIfAbsent(double k, DoubleUnaryOperator mappingFunction) {
/*  569 */     Objects.requireNonNull(mappingFunction);
/*  570 */     int pos = find(k);
/*  571 */     if (pos >= 0)
/*  572 */       return this.value[pos]; 
/*  573 */     double newValue = mappingFunction.applyAsDouble(k);
/*  574 */     insert(-pos - 1, k, newValue);
/*  575 */     return newValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfAbsentNullable(double k, DoubleFunction<? extends Double> mappingFunction) {
/*  581 */     Objects.requireNonNull(mappingFunction);
/*  582 */     int pos = find(k);
/*  583 */     if (pos >= 0)
/*  584 */       return this.value[pos]; 
/*  585 */     Double newValue = mappingFunction.apply(k);
/*  586 */     if (newValue == null)
/*  587 */       return this.defRetValue; 
/*  588 */     double v = newValue.doubleValue();
/*  589 */     insert(-pos - 1, k, v);
/*  590 */     return v;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double computeIfPresent(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  596 */     Objects.requireNonNull(remappingFunction);
/*  597 */     int pos = find(k);
/*  598 */     if (pos < 0)
/*  599 */       return this.defRetValue; 
/*  600 */     Double newValue = remappingFunction.apply(Double.valueOf(k), Double.valueOf(this.value[pos]));
/*  601 */     if (newValue == null) {
/*  602 */       if (this.strategy.equals(k, 0.0D)) {
/*  603 */         removeNullEntry();
/*      */       } else {
/*  605 */         removeEntry(pos);
/*  606 */       }  return this.defRetValue;
/*      */     } 
/*  608 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double compute(double k, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  614 */     Objects.requireNonNull(remappingFunction);
/*  615 */     int pos = find(k);
/*  616 */     Double newValue = remappingFunction.apply(Double.valueOf(k), 
/*  617 */         (pos >= 0) ? Double.valueOf(this.value[pos]) : null);
/*  618 */     if (newValue == null) {
/*  619 */       if (pos >= 0)
/*  620 */         if (this.strategy.equals(k, 0.0D)) {
/*  621 */           removeNullEntry();
/*      */         } else {
/*  623 */           removeEntry(pos);
/*      */         }  
/*  625 */       return this.defRetValue;
/*      */     } 
/*  627 */     double newVal = newValue.doubleValue();
/*  628 */     if (pos < 0) {
/*  629 */       insert(-pos - 1, k, newVal);
/*  630 */       return newVal;
/*      */     } 
/*  632 */     this.value[pos] = newVal; return newVal;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public double merge(double k, double v, BiFunction<? super Double, ? super Double, ? extends Double> remappingFunction) {
/*  638 */     Objects.requireNonNull(remappingFunction);
/*  639 */     int pos = find(k);
/*  640 */     if (pos < 0) {
/*  641 */       insert(-pos - 1, k, v);
/*  642 */       return v;
/*      */     } 
/*  644 */     Double newValue = remappingFunction.apply(Double.valueOf(this.value[pos]), Double.valueOf(v));
/*  645 */     if (newValue == null) {
/*  646 */       if (this.strategy.equals(k, 0.0D)) {
/*  647 */         removeNullEntry();
/*      */       } else {
/*  649 */         removeEntry(pos);
/*  650 */       }  return this.defRetValue;
/*      */     } 
/*  652 */     this.value[pos] = newValue.doubleValue(); return newValue.doubleValue();
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
/*  663 */     if (this.size == 0)
/*      */       return; 
/*  665 */     this.size = 0;
/*  666 */     this.containsNullKey = false;
/*  667 */     Arrays.fill(this.key, 0.0D);
/*      */   }
/*      */   
/*      */   public int size() {
/*  671 */     return this.size;
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  675 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   final class MapEntry
/*      */     implements Double2DoubleMap.Entry, Map.Entry<Double, Double>
/*      */   {
/*      */     int index;
/*      */ 
/*      */     
/*      */     MapEntry(int index) {
/*  687 */       this.index = index;
/*      */     }
/*      */     
/*      */     MapEntry() {}
/*      */     
/*      */     public double getDoubleKey() {
/*  693 */       return Double2DoubleOpenCustomHashMap.this.key[this.index];
/*      */     }
/*      */     
/*      */     public double getDoubleValue() {
/*  697 */       return Double2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */     
/*      */     public double setValue(double v) {
/*  701 */       double oldValue = Double2DoubleOpenCustomHashMap.this.value[this.index];
/*  702 */       Double2DoubleOpenCustomHashMap.this.value[this.index] = v;
/*  703 */       return oldValue;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getKey() {
/*  713 */       return Double.valueOf(Double2DoubleOpenCustomHashMap.this.key[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double getValue() {
/*  723 */       return Double.valueOf(Double2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @Deprecated
/*      */     public Double setValue(Double v) {
/*  733 */       return Double.valueOf(setValue(v.doubleValue()));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/*  738 */       if (!(o instanceof Map.Entry))
/*  739 */         return false; 
/*  740 */       Map.Entry<Double, Double> e = (Map.Entry<Double, Double>)o;
/*  741 */       return (Double2DoubleOpenCustomHashMap.this.strategy.equals(Double2DoubleOpenCustomHashMap.this.key[this.index], ((Double)e.getKey()).doubleValue()) && 
/*  742 */         Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[this.index]) == Double.doubleToLongBits(((Double)e.getValue()).doubleValue()));
/*      */     }
/*      */     
/*      */     public int hashCode() {
/*  746 */       return Double2DoubleOpenCustomHashMap.this.strategy.hashCode(Double2DoubleOpenCustomHashMap.this.key[this.index]) ^ HashCommon.double2int(Double2DoubleOpenCustomHashMap.this.value[this.index]);
/*      */     }
/*      */     
/*      */     public String toString() {
/*  750 */       return Double2DoubleOpenCustomHashMap.this.key[this.index] + "=>" + Double2DoubleOpenCustomHashMap.this.value[this.index];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class MapIterator
/*      */   {
/*  760 */     int pos = Double2DoubleOpenCustomHashMap.this.n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  767 */     int last = -1;
/*      */     
/*  769 */     int c = Double2DoubleOpenCustomHashMap.this.size;
/*      */ 
/*      */ 
/*      */     
/*  773 */     boolean mustReturnNullKey = Double2DoubleOpenCustomHashMap.this.containsNullKey;
/*      */ 
/*      */     
/*      */     DoubleArrayList wrapped;
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*  780 */       return (this.c != 0);
/*      */     }
/*      */     public int nextEntry() {
/*  783 */       if (!hasNext())
/*  784 */         throw new NoSuchElementException(); 
/*  785 */       this.c--;
/*  786 */       if (this.mustReturnNullKey) {
/*  787 */         this.mustReturnNullKey = false;
/*  788 */         return this.last = Double2DoubleOpenCustomHashMap.this.n;
/*      */       } 
/*  790 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*      */       while (true) {
/*  792 */         if (--this.pos < 0) {
/*      */           
/*  794 */           this.last = Integer.MIN_VALUE;
/*  795 */           double k = this.wrapped.getDouble(-this.pos - 1);
/*  796 */           int p = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask;
/*  797 */           while (!Double2DoubleOpenCustomHashMap.this.strategy.equals(k, key[p]))
/*  798 */             p = p + 1 & Double2DoubleOpenCustomHashMap.this.mask; 
/*  799 */           return p;
/*      */         } 
/*  801 */         if (Double.doubleToLongBits(key[this.pos]) != 0L) {
/*  802 */           return this.last = this.pos;
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
/*  816 */       double[] key = Double2DoubleOpenCustomHashMap.this.key; while (true) {
/*      */         double curr; int last;
/*  818 */         pos = (last = pos) + 1 & Double2DoubleOpenCustomHashMap.this.mask;
/*      */         while (true) {
/*  820 */           if (Double.doubleToLongBits(curr = key[pos]) == 0L) {
/*  821 */             key[last] = 0.0D;
/*      */             return;
/*      */           } 
/*  824 */           int slot = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(curr)) & Double2DoubleOpenCustomHashMap.this.mask;
/*  825 */           if ((last <= pos) ? (last >= slot || slot > pos) : (last >= slot && slot > pos))
/*      */             break; 
/*  827 */           pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask;
/*      */         } 
/*  829 */         if (pos < last) {
/*  830 */           if (this.wrapped == null)
/*  831 */             this.wrapped = new DoubleArrayList(2); 
/*  832 */           this.wrapped.add(key[pos]);
/*      */         } 
/*  834 */         key[last] = curr;
/*  835 */         Double2DoubleOpenCustomHashMap.this.value[last] = Double2DoubleOpenCustomHashMap.this.value[pos];
/*      */       } 
/*      */     }
/*      */     public void remove() {
/*  839 */       if (this.last == -1)
/*  840 */         throw new IllegalStateException(); 
/*  841 */       if (this.last == Double2DoubleOpenCustomHashMap.this.n) {
/*  842 */         Double2DoubleOpenCustomHashMap.this.containsNullKey = false;
/*  843 */       } else if (this.pos >= 0) {
/*  844 */         shiftKeys(this.last);
/*      */       } else {
/*      */         
/*  847 */         Double2DoubleOpenCustomHashMap.this.remove(this.wrapped.getDouble(-this.pos - 1));
/*  848 */         this.last = -1;
/*      */         return;
/*      */       } 
/*  851 */       Double2DoubleOpenCustomHashMap.this.size--;
/*  852 */       this.last = -1;
/*      */     }
/*      */ 
/*      */     
/*      */     public int skip(int n) {
/*  857 */       int i = n;
/*  858 */       while (i-- != 0 && hasNext())
/*  859 */         nextEntry(); 
/*  860 */       return n - i - 1;
/*      */     }
/*      */     private MapIterator() {} }
/*      */   
/*      */   private class EntryIterator extends MapIterator implements ObjectIterator<Double2DoubleMap.Entry> { private Double2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     
/*      */     public Double2DoubleOpenCustomHashMap.MapEntry next() {
/*  867 */       return this.entry = new Double2DoubleOpenCustomHashMap.MapEntry(nextEntry());
/*      */     }
/*      */     private EntryIterator() {}
/*      */     public void remove() {
/*  871 */       super.remove();
/*  872 */       this.entry.index = -1;
/*      */     } }
/*      */   
/*      */   private class FastEntryIterator extends MapIterator implements ObjectIterator<Double2DoubleMap.Entry> { private FastEntryIterator() {
/*  876 */       this.entry = new Double2DoubleOpenCustomHashMap.MapEntry();
/*      */     } private final Double2DoubleOpenCustomHashMap.MapEntry entry;
/*      */     public Double2DoubleOpenCustomHashMap.MapEntry next() {
/*  879 */       this.entry.index = nextEntry();
/*  880 */       return this.entry;
/*      */     } }
/*      */   
/*      */   private final class MapEntrySet extends AbstractObjectSet<Double2DoubleMap.Entry> implements Double2DoubleMap.FastEntrySet { private MapEntrySet() {}
/*      */     
/*      */     public ObjectIterator<Double2DoubleMap.Entry> iterator() {
/*  886 */       return new Double2DoubleOpenCustomHashMap.EntryIterator();
/*      */     }
/*      */     
/*      */     public ObjectIterator<Double2DoubleMap.Entry> fastIterator() {
/*  890 */       return new Double2DoubleOpenCustomHashMap.FastEntryIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean contains(Object o) {
/*  895 */       if (!(o instanceof Map.Entry))
/*  896 */         return false; 
/*  897 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  898 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  899 */         return false; 
/*  900 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  901 */         return false; 
/*  902 */       double k = ((Double)e.getKey()).doubleValue();
/*  903 */       double v = ((Double)e.getValue()).doubleValue();
/*  904 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  905 */         return (Double2DoubleOpenCustomHashMap.this.containsNullKey && 
/*  906 */           Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v));
/*      */       }
/*  908 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  911 */       if (Double.doubleToLongBits(
/*  912 */           curr = key[pos = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  913 */         return false; 
/*  914 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  915 */         return (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */       }
/*      */       while (true) {
/*  918 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  919 */           return false; 
/*  920 */         if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, curr)) {
/*  921 */           return (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v));
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean remove(Object o) {
/*  927 */       if (!(o instanceof Map.Entry))
/*  928 */         return false; 
/*  929 */       Map.Entry<?, ?> e = (Map.Entry<?, ?>)o;
/*  930 */       if (e.getKey() == null || !(e.getKey() instanceof Double))
/*  931 */         return false; 
/*  932 */       if (e.getValue() == null || !(e.getValue() instanceof Double))
/*  933 */         return false; 
/*  934 */       double k = ((Double)e.getKey()).doubleValue();
/*  935 */       double v = ((Double)e.getValue()).doubleValue();
/*  936 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(k, 0.0D)) {
/*  937 */         if (Double2DoubleOpenCustomHashMap.this.containsNullKey && Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]) == Double.doubleToLongBits(v)) {
/*  938 */           Double2DoubleOpenCustomHashMap.this.removeNullEntry();
/*  939 */           return true;
/*      */         } 
/*  941 */         return false;
/*      */       } 
/*      */       
/*  944 */       double[] key = Double2DoubleOpenCustomHashMap.this.key;
/*      */       double curr;
/*      */       int pos;
/*  947 */       if (Double.doubleToLongBits(
/*  948 */           curr = key[pos = HashCommon.mix(Double2DoubleOpenCustomHashMap.this.strategy.hashCode(k)) & Double2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  949 */         return false; 
/*  950 */       if (Double2DoubleOpenCustomHashMap.this.strategy.equals(curr, k)) {
/*  951 */         if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  952 */           Double2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  953 */           return true;
/*      */         } 
/*  955 */         return false;
/*      */       } 
/*      */       while (true) {
/*  958 */         if (Double.doubleToLongBits(curr = key[pos = pos + 1 & Double2DoubleOpenCustomHashMap.this.mask]) == 0L)
/*  959 */           return false; 
/*  960 */         if (Double2DoubleOpenCustomHashMap.this.strategy.equals(curr, k) && 
/*  961 */           Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.value[pos]) == Double.doubleToLongBits(v)) {
/*  962 */           Double2DoubleOpenCustomHashMap.this.removeEntry(pos);
/*  963 */           return true;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public int size() {
/*  970 */       return Double2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public void clear() {
/*  974 */       Double2DoubleOpenCustomHashMap.this.clear();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  979 */       if (Double2DoubleOpenCustomHashMap.this.containsNullKey)
/*  980 */         consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleOpenCustomHashMap.this.key[Double2DoubleOpenCustomHashMap.this.n], Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n])); 
/*  981 */       for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  982 */         if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L)
/*  983 */           consumer.accept(new AbstractDouble2DoubleMap.BasicEntry(Double2DoubleOpenCustomHashMap.this.key[pos], Double2DoubleOpenCustomHashMap.this.value[pos])); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public void fastForEach(Consumer<? super Double2DoubleMap.Entry> consumer) {
/*  988 */       AbstractDouble2DoubleMap.BasicEntry entry = new AbstractDouble2DoubleMap.BasicEntry();
/*  989 */       if (Double2DoubleOpenCustomHashMap.this.containsNullKey) {
/*  990 */         entry.key = Double2DoubleOpenCustomHashMap.this.key[Double2DoubleOpenCustomHashMap.this.n];
/*  991 */         entry.value = Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n];
/*  992 */         consumer.accept(entry);
/*      */       } 
/*  994 */       for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/*  995 */         if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L) {
/*  996 */           entry.key = Double2DoubleOpenCustomHashMap.this.key[pos];
/*  997 */           entry.value = Double2DoubleOpenCustomHashMap.this.value[pos];
/*  998 */           consumer.accept(entry);
/*      */         } 
/*      */       } 
/*      */     } }
/*      */   
/*      */   public Double2DoubleMap.FastEntrySet double2DoubleEntrySet() {
/* 1004 */     if (this.entries == null)
/* 1005 */       this.entries = new MapEntrySet(); 
/* 1006 */     return this.entries;
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
/* 1023 */       return Double2DoubleOpenCustomHashMap.this.key[nextEntry()];
/*      */     } }
/*      */   
/*      */   private final class KeySet extends AbstractDoubleSet { private KeySet() {}
/*      */     
/*      */     public DoubleIterator iterator() {
/* 1029 */       return new Double2DoubleOpenCustomHashMap.KeyIterator();
/*      */     }
/*      */ 
/*      */     
/*      */     public void forEach(DoubleConsumer consumer) {
/* 1034 */       if (Double2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1035 */         consumer.accept(Double2DoubleOpenCustomHashMap.this.key[Double2DoubleOpenCustomHashMap.this.n]); 
/* 1036 */       for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0; ) {
/* 1037 */         double k = Double2DoubleOpenCustomHashMap.this.key[pos];
/* 1038 */         if (Double.doubleToLongBits(k) != 0L)
/* 1039 */           consumer.accept(k); 
/*      */       } 
/*      */     }
/*      */     
/*      */     public int size() {
/* 1044 */       return Double2DoubleOpenCustomHashMap.this.size;
/*      */     }
/*      */     
/*      */     public boolean contains(double k) {
/* 1048 */       return Double2DoubleOpenCustomHashMap.this.containsKey(k);
/*      */     }
/*      */     
/*      */     public boolean remove(double k) {
/* 1052 */       int oldSize = Double2DoubleOpenCustomHashMap.this.size;
/* 1053 */       Double2DoubleOpenCustomHashMap.this.remove(k);
/* 1054 */       return (Double2DoubleOpenCustomHashMap.this.size != oldSize);
/*      */     }
/*      */     
/*      */     public void clear() {
/* 1058 */       Double2DoubleOpenCustomHashMap.this.clear();
/*      */     } }
/*      */ 
/*      */   
/*      */   public DoubleSet keySet() {
/* 1063 */     if (this.keys == null)
/* 1064 */       this.keys = new KeySet(); 
/* 1065 */     return this.keys;
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
/*      */     implements DoubleIterator
/*      */   {
/*      */     public double nextDouble() {
/* 1082 */       return Double2DoubleOpenCustomHashMap.this.value[nextEntry()];
/*      */     }
/*      */   }
/*      */   
/*      */   public DoubleCollection values() {
/* 1087 */     if (this.values == null)
/* 1088 */       this.values = new AbstractDoubleCollection()
/*      */         {
/*      */           public DoubleIterator iterator() {
/* 1091 */             return new Double2DoubleOpenCustomHashMap.ValueIterator();
/*      */           }
/*      */           
/*      */           public int size() {
/* 1095 */             return Double2DoubleOpenCustomHashMap.this.size;
/*      */           }
/*      */           
/*      */           public boolean contains(double v) {
/* 1099 */             return Double2DoubleOpenCustomHashMap.this.containsValue(v);
/*      */           }
/*      */           
/*      */           public void clear() {
/* 1103 */             Double2DoubleOpenCustomHashMap.this.clear();
/*      */           }
/*      */           
/*      */           public void forEach(DoubleConsumer consumer)
/*      */           {
/* 1108 */             if (Double2DoubleOpenCustomHashMap.this.containsNullKey)
/* 1109 */               consumer.accept(Double2DoubleOpenCustomHashMap.this.value[Double2DoubleOpenCustomHashMap.this.n]); 
/* 1110 */             for (int pos = Double2DoubleOpenCustomHashMap.this.n; pos-- != 0;) {
/* 1111 */               if (Double.doubleToLongBits(Double2DoubleOpenCustomHashMap.this.key[pos]) != 0L)
/* 1112 */                 consumer.accept(Double2DoubleOpenCustomHashMap.this.value[pos]); 
/*      */             }  }
/*      */         }; 
/* 1115 */     return this.values;
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
/* 1132 */     return trim(this.size);
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
/* 1156 */     int l = HashCommon.nextPowerOfTwo((int)Math.ceil((n / this.f)));
/* 1157 */     if (l >= this.n || this.size > HashCommon.maxFill(l, this.f))
/* 1158 */       return true; 
/*      */     try {
/* 1160 */       rehash(l);
/* 1161 */     } catch (OutOfMemoryError cantDoIt) {
/* 1162 */       return false;
/*      */     } 
/* 1164 */     return true;
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
/* 1180 */     double[] key = this.key;
/* 1181 */     double[] value = this.value;
/* 1182 */     int mask = newN - 1;
/* 1183 */     double[] newKey = new double[newN + 1];
/* 1184 */     double[] newValue = new double[newN + 1];
/* 1185 */     int i = this.n;
/* 1186 */     for (int j = realSize(); j-- != 0; ) {
/* 1187 */       while (Double.doubleToLongBits(key[--i]) == 0L); int pos;
/* 1188 */       if (Double.doubleToLongBits(newKey[
/* 1189 */             pos = HashCommon.mix(this.strategy.hashCode(key[i])) & mask]) != 0L)
/* 1190 */         while (Double.doubleToLongBits(newKey[pos = pos + 1 & mask]) != 0L); 
/* 1191 */       newKey[pos] = key[i];
/* 1192 */       newValue[pos] = value[i];
/*      */     } 
/* 1194 */     newValue[newN] = value[this.n];
/* 1195 */     this.n = newN;
/* 1196 */     this.mask = mask;
/* 1197 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1198 */     this.key = newKey;
/* 1199 */     this.value = newValue;
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
/*      */   public Double2DoubleOpenCustomHashMap clone() {
/*      */     Double2DoubleOpenCustomHashMap c;
/*      */     try {
/* 1216 */       c = (Double2DoubleOpenCustomHashMap)super.clone();
/* 1217 */     } catch (CloneNotSupportedException cantHappen) {
/* 1218 */       throw new InternalError();
/*      */     } 
/* 1220 */     c.keys = null;
/* 1221 */     c.values = null;
/* 1222 */     c.entries = null;
/* 1223 */     c.containsNullKey = this.containsNullKey;
/* 1224 */     c.key = (double[])this.key.clone();
/* 1225 */     c.value = (double[])this.value.clone();
/* 1226 */     c.strategy = this.strategy;
/* 1227 */     return c;
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
/* 1240 */     int h = 0;
/* 1241 */     for (int j = realSize(), i = 0, t = 0; j-- != 0; ) {
/* 1242 */       while (Double.doubleToLongBits(this.key[i]) == 0L)
/* 1243 */         i++; 
/* 1244 */       t = this.strategy.hashCode(this.key[i]);
/* 1245 */       t ^= HashCommon.double2int(this.value[i]);
/* 1246 */       h += t;
/* 1247 */       i++;
/*      */     } 
/*      */     
/* 1250 */     if (this.containsNullKey)
/* 1251 */       h += HashCommon.double2int(this.value[this.n]); 
/* 1252 */     return h;
/*      */   }
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1255 */     double[] key = this.key;
/* 1256 */     double[] value = this.value;
/* 1257 */     MapIterator i = new MapIterator();
/* 1258 */     s.defaultWriteObject();
/* 1259 */     for (int j = this.size; j-- != 0; ) {
/* 1260 */       int e = i.nextEntry();
/* 1261 */       s.writeDouble(key[e]);
/* 1262 */       s.writeDouble(value[e]);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1267 */     s.defaultReadObject();
/* 1268 */     this.n = HashCommon.arraySize(this.size, this.f);
/* 1269 */     this.maxFill = HashCommon.maxFill(this.n, this.f);
/* 1270 */     this.mask = this.n - 1;
/* 1271 */     double[] key = this.key = new double[this.n + 1];
/* 1272 */     double[] value = this.value = new double[this.n + 1];
/*      */ 
/*      */     
/* 1275 */     for (int i = this.size; i-- != 0; ) {
/* 1276 */       int pos; double k = s.readDouble();
/* 1277 */       double v = s.readDouble();
/* 1278 */       if (this.strategy.equals(k, 0.0D)) {
/* 1279 */         pos = this.n;
/* 1280 */         this.containsNullKey = true;
/*      */       } else {
/* 1282 */         pos = HashCommon.mix(this.strategy.hashCode(k)) & this.mask;
/* 1283 */         while (Double.doubleToLongBits(key[pos]) != 0L)
/* 1284 */           pos = pos + 1 & this.mask; 
/*      */       } 
/* 1286 */       key[pos] = k;
/* 1287 */       value[pos] = v;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkTable() {}
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\doubles\Double2DoubleOpenCustomHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */